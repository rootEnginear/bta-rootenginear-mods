package rootenginear.leavesalwaysfalling.mixin;

import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.Option;
import net.minecraft.client.option.RangeOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.leavesalwaysfalling.ILeavesSettings;

@Mixin(value = {GameSettings.class}, remap = false)
public class GameSettingsMixin implements ILeavesSettings {
	@Unique
	private final GameSettings self = (GameSettings) (Object) this;

	@Unique
	public RangeOption leaveFrequency = new RangeOption(self, "Leave Frequency", 39, 40);

	@Inject(method = "getDisplayString", at = @At("HEAD"), cancellable = true)
	private void customDisplayString(Option<?> option, CallbackInfoReturnable<String> cir) {
		if (option == leaveFrequency) {
			cir.setReturnValue(((int) option.value + 1) + "");
		}
	}

	@Unique
	public RangeOption bta_rootenginear_mods$getLeaveFrequency() {
		return leaveFrequency;
	}
}
