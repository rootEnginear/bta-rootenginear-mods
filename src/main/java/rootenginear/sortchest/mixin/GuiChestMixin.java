package rootenginear.sortchest.mixin;

import net.minecraft.client.gui.GuiChest;
import net.minecraft.client.gui.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.sortchest.mixin.accessor.GuiScreenAccessor;

@Mixin(value = {GuiChest.class}, remap = false)
public class GuiChestMixin {
	@Inject(method = "drawGuiContainerForegroundLayer", at = @At("TAIL"))
	private void writeInstruction(CallbackInfo ci) {
		GuiScreenAccessor screenThis = (GuiScreenAccessor) this;
		GuiContainer containerThis = (GuiContainer) (Object) this;
		screenThis.getFontRenderer().drawString("S: Sort ⇵, F: Fill ⊼", containerThis.xSize - 8 - 88 + 2, 6, 0x404040);
		screenThis.getFontRenderer().drawString("D: Dump ⊻", containerThis.xSize - 8 - 48 + 2, containerThis.ySize - 96 + 2, 0x404040);
//		screenThis.getFontRenderer().drawStringWithShadow("S: Sort ⇵", containerThis.xSize + 4, 6, 0xffffff);
//		screenThis.getFontRenderer().drawStringWithShadow("F: Fill ⊼", containerThis.xSize + 4, 6 + 12, 0xffffff);
//		screenThis.getFontRenderer().drawStringWithShadow("D: Dump ⊻", containerThis.xSize + 4, containerThis.ySize - 96 + 2, 0xffffff);
	}
}
