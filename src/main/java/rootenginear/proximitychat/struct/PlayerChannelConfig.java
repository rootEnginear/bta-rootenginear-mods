package rootenginear.proximitychat.struct;

public class PlayerChannelConfig {
	public boolean isGlobal;
    public int radius;

    public PlayerChannelConfig(boolean isGlobal, int radius) {
        this.isGlobal = isGlobal;
		this.radius = radius;
    }
}
