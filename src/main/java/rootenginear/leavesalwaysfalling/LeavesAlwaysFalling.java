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
		return ((ILeavesSettings) Minecraft.getMinecraft().gameSettings).bta_rootenginear_mods$getFrequency().value;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Leaves Always Falling initialized.");
	}
}
