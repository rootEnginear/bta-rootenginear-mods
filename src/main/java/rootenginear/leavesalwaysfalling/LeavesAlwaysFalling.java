package rootenginear.leavesalwaysfalling;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.OptionFloat;
import net.minecraft.client.option.OptionRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rootenginear.leavesalwaysfalling.interfaces.ILeavesSettings;

@Environment(EnvType.CLIENT)
public class LeavesAlwaysFalling implements ModInitializer {
	public static final String MOD_ID = "leavesalwaysfalling";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static OptionRange getFrequencyOption() {
		return ((ILeavesSettings) Minecraft.getMinecraft().gameSettings).bta_rootenginear_mods$getFrequencyOption();
	}

	public static OptionFloat getGravityMultiplierOption() {
		return ((ILeavesSettings) Minecraft.getMinecraft().gameSettings).bta_rootenginear_mods$getGravityMultiplierOption();
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Leaves Always Falling initialized.");
	}
}
