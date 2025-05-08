package rootenginear.leavesalwaysfalling.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.Option;
import net.minecraft.client.option.OptionFloat;
import net.minecraft.client.option.OptionRange;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.leavesalwaysfalling.interfaces.ILeavesSettings;
import rootenginear.leavesalwaysfalling.utils.Utils;

@Environment(EnvType.CLIENT)
@Mixin(value = {GameSettings.class}, remap = false)
public class GameSettingsMixin implements ILeavesSettings {
	@Unique
	private final GameSettings self = (GameSettings) (Object) this;

	@Unique
	private final OptionRange frequency = new OptionRange(self, "leavesalwaysfalling.frequency", 3, 0, 100);

	@Unique
	private final OptionFloat gravityMultiplier = new OptionFloat(self, "leavesalwaysfalling.gravityMultiplier", 0.09F);

	@Inject(method = "getDisplayString", at = @At("HEAD"), cancellable = true)
	private void customDisplayString(Option<?> option, CallbackInfoReturnable<String> cir) {
		if (option == frequency) {
			int value = (int) option.value;
			if (value == 0) {
				cir.setReturnValue("Never");
				return;
			}
			if (value == 100) {
				cir.setReturnValue("GPU goes brrr");
				return;
			}
			cir.setReturnValue(value + "%");
		} else if (option == gravityMultiplier) {
			float value = (float) option.value;
			float normalizedValue = Utils.normalizeGravityMultiplier(value);
			cir.setReturnValue(Utils.formatFloatOneDecimal(normalizedValue) + "x");
		}
	}

	public OptionRange bta_rootenginear_mods$getFrequencyOption() {
		return frequency;
	}

	public OptionFloat bta_rootenginear_mods$getGravityMultiplierOption() {
		return gravityMultiplier;
	}
}
