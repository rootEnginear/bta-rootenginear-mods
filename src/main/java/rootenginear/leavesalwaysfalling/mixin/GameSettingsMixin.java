package rootenginear.leavesalwaysfalling.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.Option;
import net.minecraft.client.option.OptionRange;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.leavesalwaysfalling.gui.GuiOptionsPageLeaves;

import java.io.File;

@Environment(EnvType.CLIENT)
@Mixin(value = {GameSettings.class}, remap = false)
public class GameSettingsMixin {
	@Inject(method = "<init>", at = @At("TAIL"))
	public void init(Minecraft minecraft, File file, CallbackInfo ci) {
		GameSettings self = (GameSettings) (Object) this;

		GuiOptionsPageLeaves.leavesFrequency = new OptionRange(self, "leavesalwaysfalling.frequency", 3, 0, 100);
	}

	@Inject(method = "getDisplayString", at = @At("HEAD"), cancellable = true)
	private void customDisplayString(Option<?> option, CallbackInfoReturnable<String> cir) {
		if (option == GuiOptionsPageLeaves.leavesFrequency) {
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
}
