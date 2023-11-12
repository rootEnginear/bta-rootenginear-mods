package rootenginear.sortchest.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.sortchest.interfaces.ISortChestSettings;
import rootenginear.sortchest.utils.Utils;

@Mixin(value = {GuiScreen.class}, remap = false)
public abstract class GuiScreenMixin {
	@Shadow
	public abstract void keyTyped(char c, int key, int mouseX, int mouseY);

	@Inject(method = "buttonPressed", at = @At(value = "HEAD"), cancellable = true)
	private void chestButtonsAction(GuiButton guibutton, CallbackInfo ci) {
		if (Utils.isNotChest(this)) return;

		if (!guibutton.enabled) {
			ci.cancel();
			return;
		}

		ISortChestSettings gameSettings = ((ISortChestSettings) Minecraft.getMinecraft(Minecraft.class).gameSettings);
		switch (guibutton.id) {
			case 0:
				keyTyped(gameSettings.bta_rootenginear_mods$getKeySort().getKeyName().charAt(0), 0, 0, 0);
				ci.cancel();
				return;
			case 1:
				keyTyped(gameSettings.bta_rootenginear_mods$getKeyRefill().getKeyName().charAt(0), 0, 0, 0);
				ci.cancel();
				return;
			case 2:
				keyTyped(gameSettings.bta_rootenginear_mods$getKeyFill().getKeyName().charAt(0), 0, 0, 0);
				ci.cancel();
				return;
			case 3:
				keyTyped(gameSettings.bta_rootenginear_mods$getKeyDump().getKeyName().charAt(0), 0, 0, 0);
				ci.cancel();
		}
	}
}
