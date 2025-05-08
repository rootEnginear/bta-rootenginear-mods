package rootenginear.leavesalwaysfalling.interfaces;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.OptionFloat;
import net.minecraft.client.option.OptionRange;

@Environment(EnvType.CLIENT)
public interface ILeavesSettings {
	OptionRange bta_rootenginear_mods$getFrequencyOption();

	OptionFloat bta_rootenginear_mods$getGravityMultiplierOption();

	OptionFloat bta_rootenginear_mods$getXMultiplierOption();

	OptionFloat bta_rootenginear_mods$getZMultiplierOption();
}
