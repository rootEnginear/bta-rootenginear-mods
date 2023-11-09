package rootenginear.leavesalwaysfalling;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeavesAlwaysFalling implements ModInitializer {
	public static final String MOD_ID = "leavesalwaysfalling";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int getLeaveFrequency() {
		return ((ILeavesSettings) ((Minecraft) FabricLoader.getInstance().getGameInstance()).gameSettings).bta_rootenginear_mods$getLeaveFrequency().value + 1;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Leaves Always Falling initialized.");
	}
}
