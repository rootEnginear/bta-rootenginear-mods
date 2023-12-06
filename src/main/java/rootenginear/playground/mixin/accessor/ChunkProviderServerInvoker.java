package rootenginear.playground.mixin.accessor;

import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.server.world.chunk.provider.ChunkProviderServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = ChunkProviderServer.class, remap = false)
public interface ChunkProviderServerInvoker {
	@Invoker("loadChunkFromFile")
	Chunk invokeLoadChunkFromFile(int chunkX, int chunkZ);
}
