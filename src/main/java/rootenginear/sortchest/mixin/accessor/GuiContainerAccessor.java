package rootenginear.sortchest.mixin.accessor;

import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.gui.GuiTooltip;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = {GuiContainer.class}, remap = false)
public interface GuiContainerAccessor {
	@Accessor
	GuiTooltip getGuiTooltip();
}
