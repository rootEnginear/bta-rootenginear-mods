package rootenginear.playground.mixin;

import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.server.world.WorldServer;
import net.minecraft.server.world.chunk.provider.ChunkProviderServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rootenginear.playground.mixin.accessor.ChunkProviderServerInvoker;

import java.io.FileOutputStream;
import java.io.IOException;

@Mixin(value = ChunkProviderServer.class, remap = false)
public abstract class ChunkProviderMixin {
	@Shadow
	@Final
	private WorldServer world;

	@Redirect(method = "prepareChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/chunk/provider/ChunkProviderServer;loadChunkFromFile(II)Lnet/minecraft/core/world/chunk/Chunk;"))
	private Chunk readTopOpaqueBlockData(ChunkProviderServer cps, int chunkX, int chunkZ) throws IOException {
		Chunk chunk = ((ChunkProviderServerInvoker) cps).invokeLoadChunkFromFile(chunkX, chunkZ);
		if (chunk == null) return null;
		if (this.world.dimension.id == 0) {
			try (FileOutputStream chunkData = new FileOutputStream(String.format("livemap/chunks/%d.%d.txt", chunkX, chunkZ))) {
				for (int shiftZ = 0; shiftZ < 16; shiftZ++) {
					for (int shiftX = 0; shiftX < 16; shiftX++) {
						short blockData = 0;
						for (int y = chunk.getHeightValue(shiftX, shiftZ); y > -1; y--) {
							int blockId = chunk.getBlockID(shiftX, y, shiftZ);
							if (blockId != 0) {
								int metadata = chunk.getBlockMetadata(shiftX, y, shiftZ);
								blockData = (short) ((blockId & 0x3FF) | ((metadata & 0xF) << 10));
								break;
							}
						}
						chunkData.write(blockData & 0xFF);
						chunkData.write((blockData >> 8) & 0xFF);
					}
				}
			}
		}
		return chunk;
	}
}
