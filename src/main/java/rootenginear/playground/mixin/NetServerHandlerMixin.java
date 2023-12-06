package rootenginear.playground.mixin;

import net.minecraft.core.net.packet.Packet10Flying;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.FileWriter;
import java.io.IOException;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixin {
	@Shadow
	private EntityPlayerMP playerEntity;

	@Inject(method = "handleFlying", at = @At("HEAD"))
	private void trackLocation(Packet10Flying packet, CallbackInfo ci) throws IOException {
		if (packet.moving) {
			try (FileWriter chunkData = new FileWriter(String.format("livemap/players/%s.txt", this.playerEntity.username))) {
				chunkData.write(String.format("%f %f", this.playerEntity.x, this.playerEntity.z));
			}
		}
	}
}
