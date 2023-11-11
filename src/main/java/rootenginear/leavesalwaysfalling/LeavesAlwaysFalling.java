package rootenginear.leavesalwaysfalling;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rootenginear.leavesalwaysfalling.interfaces.ILeavesSettings;

public class LeavesAlwaysFalling implements ModInitializer {
	public static final String MOD_ID = "leavesalwaysfalling";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int getLeavesRandomBound() {
		int freq = ((ILeavesSettings) Minecraft.getMinecraft(Minecraft.class).gameSettings).bta_rootenginear_mods$getFrequency().value;
		if (freq == 0) return 0;
		return (int) Math.round(1.0 / freq * 100);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Leaves Always Falling initialized.");
	}
}
