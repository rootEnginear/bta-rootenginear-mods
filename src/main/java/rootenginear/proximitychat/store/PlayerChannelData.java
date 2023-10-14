package rootenginear.proximitychat.store;

import rootenginear.proximitychat.ProximityChat;
import rootenginear.proximitychat.struct.PlayerChannelConfig;

import java.util.HashMap;

public class PlayerChannelData {
	public static HashMap<String, PlayerChannelConfig> playerProxData = new HashMap<>();

	public static PlayerChannelConfig getPlayerChannelData(String playerName) {
		if (playerProxData.containsKey(playerName)) return playerProxData.get(playerName);

		PlayerChannelConfig t = new PlayerChannelConfig(true, ProximityChat.RADIUS);
		playerProxData.put(playerName, t);
		return t;
	}

	public static void setPlayerChannelData(String playerName, String mode) {
		if (!mode.equals("global") && !mode.equals("proximity") && !mode.equals("prox")) return;

		if (!playerProxData.containsKey(playerName)) {
			playerProxData.put(playerName, new PlayerChannelConfig(mode.equals("global"), ProximityChat.RADIUS));
			return;
		}

		PlayerChannelConfig t = playerProxData.get(playerName);
		t.isGlobal = mode.equals("global");
		playerProxData.replace(playerName, t);
	}
}
