package rootenginear.proximitychat.mixin;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.core.util.helper.AES;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = {PlayerList.class}, remap = false)
public class ServerConfigurationManagerMixin {
	@Shadow
	public List<EntityPlayerMP> playerEntities;

	@Inject(method = "sendPacketToOtherPlayersAroundPoint", at = @At("HEAD"), cancellable = true)
	public void sendEncryptedChatToPlayersAroundPoint(EntityPlayer player, double x, double y, double z, double radius, int dimension, Packet packet, CallbackInfo ci) {
		if (packet instanceof Packet3Chat) {
			for (EntityPlayerMP playerEntity : this.playerEntities) {
				double dz;
				double dy;
				double dx;
				if (playerEntity == player || playerEntity.dimension != dimension || !((dx = x - playerEntity.x) * dx + (dy = y - playerEntity.y) * dy + (dz = z - playerEntity.z) * dz < radius * radius))
					continue;
				playerEntity.playerNetServerHandler.sendPacket(new Packet3Chat(((Packet3Chat) packet).message, AES.keyChain.get(playerEntity.username)));
			}
			ci.cancel();
		}
	}
}
