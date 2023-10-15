package rootenginear.proximitychat.store;

import rootenginear.proximitychat.ProximityChat;
import rootenginear.proximitychat.struct.PlayerChannelConfig;

import java.util.HashMap;

public class PlayerChannelData {
	public static HashMap<String, PlayerChannelConfig> playerProxData = new HashMap<>();

	public static PlayerChannelConfig getPlayerChannelData(String rawPlayerName) {
		if (playerProxData.containsKey(rawPlayerName)) {
			return playerProxData.get(rawPlayerName);
		}

		PlayerChannelConfig t = new PlayerChannelConfig(true, ProximityChat.DEFAULT_RADIUS);
		playerProxData.put(rawPlayerName, t);
		return t;
	}

	public static void setPlayerChannelData(String rawPlayerName, String mode) {
		if (!mode.equals("global") && !mode.equals("proximity") && !mode.equals("prox")) return;

		if (!playerProxData.containsKey(rawPlayerName)) {
			playerProxData.put(rawPlayerName, new PlayerChannelConfig(mode.equals("global"), ProximityChat.DEFAULT_RADIUS));
			return;
		}

		PlayerChannelConfig t = playerProxData.get(rawPlayerName);
		t.isGlobal = mode.equals("global");
		playerProxData.replace(rawPlayerName, t);
	}

	public static void setPlayerRadius(String rawPlayerName, int radius) {
		if (!playerProxData.containsKey(rawPlayerName)) {
			playerProxData.put(rawPlayerName, new PlayerChannelConfig(true, radius));
			return;
		}

		PlayerChannelConfig t = playerProxData.get(rawPlayerName);
		t.radius = radius;
		playerProxData.replace(rawPlayerName, t);
	}
}
