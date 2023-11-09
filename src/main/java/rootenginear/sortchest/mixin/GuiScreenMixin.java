package rootenginear.sortchest.mixin;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.sortchest.utils.Utils;

@Mixin(value = {GuiScreen.class}, remap = false)
public class GuiScreenMixin {
	@Inject(method = "buttonPressed", at = @At(value = "HEAD"), cancellable = true)
	private void chestButtonsAction(GuiButton guibutton, CallbackInfo ci) {
		if (Utils.isNotChest(this)) return;

		if (!guibutton.enabled) {
			ci.cancel();
			return;
		}

		GuiScreen screenThis = (GuiScreen) (Object) this;
		switch (guibutton.id) {
			case 0:
				screenThis.keyTyped('s', 0, 0, 0);
				ci.cancel();
				return;
			case 1:
				screenThis.keyTyped('f', 0, 0, 0);
				ci.cancel();
				return;
			case 2:
				screenThis.keyTyped('d', 0, 0, 0);
				ci.cancel();
		}
	}
}
