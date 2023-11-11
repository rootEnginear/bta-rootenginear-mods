package rootenginear.leavesalwaysfalling.mixin;

import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.Option;
import net.minecraft.client.option.RangeOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.leavesalwaysfalling.interfaces.ILeavesSettings;

@Mixin(value = {GameSettings.class}, remap = false)
public class GameSettingsMixin implements ILeavesSettings {
	@Unique
	private final GameSettings self = (GameSettings) (Object) this;

	@Unique
	public RangeOption leavesFrequency = new RangeOption(self, "leavesalwaysfalling.frequency", 3, 101);

	@Inject(method = "getDisplayString", at = @At("HEAD"), cancellable = true)
	private void customDisplayString(Option<?> option, CallbackInfoReturnable<String> cir) {
		if (option == leavesFrequency) {
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
		}
	}

	@Unique
	public RangeOption bta_rootenginear_mods$getFrequency() {
		return leavesFrequency;
	}
}
