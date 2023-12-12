package rootenginear.livemap.mixin;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.livemap.ChunkProcessor;
import rootenginear.livemap.Livemap;

import java.io.FileWriter;

@Mixin(value = MinecraftServer.class, remap = false)
public class MinecraftServerMixin {
	@Shadow
	public WorldServer[] worldMngr;

	@Unique
	final int TPS = 20;

	@Unique
	final int CHUNK_DUMP_TIME = 30 * TPS;

	@Unique
	final int PLAYER_DUMP_TIME = 5 * TPS;

	@Unique
	int currentTick = 0;

	@Inject(method = "doTick", at = @At("TAIL"))
	void addTime(CallbackInfo ci) {
		currentTick++;
	}

	@Inject(method = "doTick", at = @At("TAIL"))
	void saveChunk(CallbackInfo ci) {
		if (currentTick % CHUNK_DUMP_TIME != 0) return;
		try {
			WorldServer overworld = this.worldMngr[0];
			for (EntityPlayer player : overworld.players) {
				int chunkX = (int) player.x / 16;
				int chunkZ = (int) player.z / 16;
				for (int chunkShiftX = -1; chunkShiftX < 2; chunkShiftX++) {
					for (int chunkShiftZ = -1; chunkShiftZ < 2; chunkShiftZ++) {
						int targetChunkX = chunkX + chunkShiftX;
						int targetChunkZ = chunkZ + chunkShiftZ;
						Chunk chunk = overworld.chunkProviderServer.provideChunk(targetChunkX, targetChunkZ);
						ChunkProcessor.readAndDumpChunkData(targetChunkX, targetChunkZ, chunk);
						Livemap.LOGGER.info("Chunk " + targetChunkX + "," + targetChunkZ + " dumped!");
					}
				}
			}
			ChunkProcessor.updateChunkFile();
		} catch (Exception ignored) {
		}
	}

	@Inject(method = "doTick", at = @At("TAIL"))
	void savePlayer(CallbackInfo ci) {
		if (currentTick % PLAYER_DUMP_TIME != 0) return;
		try {
			WorldServer overworld = this.worldMngr[0];
			StringBuilder playerListJSON = new StringBuilder("{");
			for (int i = 0; i < overworld.players.size(); i++) {
				if (i > 0) playerListJSON.append(",");
				EntityPlayer player = overworld.players.get(i);
				playerListJSON.append(String.format("\"%s\":[%g,%g]", player.username, player.x, player.z));
			}
			playerListJSON.append("}");
			try (FileWriter chunkData = new FileWriter("livemap/players.json")) {
				chunkData.write(playerListJSON.toString());
			}
		} catch (Exception ignored) {
		}
	}
}
