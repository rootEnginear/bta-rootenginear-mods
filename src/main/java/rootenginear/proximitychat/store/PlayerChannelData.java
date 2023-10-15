package rootenginear.proximitychat.store;

import net.minecraft.core.net.command.CommandError;
import rootenginear.proximitychat.ProximityChat;
import rootenginear.proximitychat.struct.PlayerChannelConfig;

import java.util.HashMap;

public class PlayerChannelData {
	public static HashMap<String, PlayerChannelConfig> playerProxData = new HashMap<>();

	public static PlayerChannelConfig getPlayerChannelData(String rawPlayerName) {
		return playerProxData.computeIfAbsent(rawPlayerName, s -> new PlayerChannelConfig(true, ProximityChat.DEFAULT_RADIUS));
	}

	public static void setPlayerChannel(String rawPlayerName, String channel) {
		if (!channel.equals("global") && !channel.equals("proximity") && !channel.equals("prox"))
			throw new CommandError("Invalid channel name: " + channel);

		getPlayerChannelData(rawPlayerName).isGlobal = channel.equals("global");
	}

	public static void setPlayerRadius(String rawPlayerName, int radius) {
		getPlayerChannelData(rawPlayerName).radius = radius;
	}
}
