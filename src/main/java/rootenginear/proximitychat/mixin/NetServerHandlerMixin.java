package rootenginear.proximitychat.mixin;

import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.ServerConfigurationManager;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rootenginear.proximitychat.struct.PlayerChannelConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static rootenginear.proximitychat.store.PlayerChannelData.getPlayerChannelData;

@Mixin(value = {NetServerHandler.class}, remap = false)
public class NetServerHandlerMixin {
	@Shadow
	private EntityPlayerMP playerEntity;

	@Redirect(
		method = "handleChat(Lnet/minecraft/core/net/packet/Packet3Chat;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/net/ServerConfigurationManager;sendEncryptedChatToAllPlayers(Ljava/lang/String;)V"
		)
	)
	private void proximityChat(ServerConfigurationManager instance, String s) {
		Pattern playerChatPattern = Pattern.compile("<(.+?)> §0(.+)");
		Matcher result = playerChatPattern.matcher(s);

		if (!result.matches()) {
			instance.sendEncryptedChatToAllPlayers(s);
			return;
		}

		String colorfulName = result.group(1);
		String rawPlayerName = colorfulName.substring(0, colorfulName.length() - 2).substring(2);
		String msg = result.group(2);

		PlayerChannelConfig playerData = getPlayerChannelData(rawPlayerName);
		boolean isGlobal = playerData.isGlobal;

		if (msg.startsWith("# ")) {
			msg = msg.substring(2);
			isGlobal = !isGlobal;
		}

		if (isGlobal) {
			instance.sendEncryptedChatToAllPlayers("<" + colorfulName + "> §0" + msg);
			return;
		}
		instance.sendPacketToPlayersAroundPoint(
			this.playerEntity.x,
			this.playerEntity.y,
			this.playerEntity.z,
			playerData.radius,
			this.playerEntity.dimension,
			new Packet3Chat("<⧈ " + colorfulName + "> §0" + msg)
		);
	}
}
