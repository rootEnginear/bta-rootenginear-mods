package rootenginear.leavesalwaysfalling.interfaces;

import net.minecraft.client.option.OptionFloat;
import net.minecraft.client.option.OptionRange;

public interface ILeavesSettings {
	OptionRange bta_rootenginear_mods$getFrequencyOption();

	OptionFloat bta_rootenginear_mods$getGravityMultiplierOption();

	OptionFloat bta_rootenginear_mods$getXMultiplierOption();

	OptionFloat bta_rootenginear_mods$getZMultiplierOption();
}
