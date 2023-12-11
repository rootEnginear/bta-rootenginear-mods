package rootenginear.playground.mixin;

import net.minecraft.client.world.chunk.provider.ChunkProviderClient;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.chunk.ChunkCoordIntPair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.playground.ChunkProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(value = ChunkProviderClient.class, remap = false)
public abstract class ChunkProviderClientMixin {
	@Shadow
	private Map chunkMapping;

	@Unique
	List<ChunkCoordIntPair> dumpedChunks = new ArrayList<>();

	@Inject(method = "provideChunk", at = @At("TAIL"))
	void joopChunk(int chunkX, int chunkZ, CallbackInfoReturnable<Chunk> cir) {
		ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(chunkX, chunkZ);
		Chunk chunk = (Chunk) this.chunkMapping.get(chunkcoordintpair);
		if (dumpedChunks.contains(chunkcoordintpair) || chunk == null || !chunk.receivedFromServer) return;
		System.out.println("Dumping " + chunkX + " " + chunkZ);
		try {
			dumpedChunks.add(chunkcoordintpair);
			ChunkProcessor.readAndDumpChunkData(chunkX, chunkZ, chunk);
		} catch (Exception ignored) {
		}
	}
}
