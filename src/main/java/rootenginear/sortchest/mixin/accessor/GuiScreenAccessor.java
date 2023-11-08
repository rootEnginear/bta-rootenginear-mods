package rootenginear.sortchest.mixin.accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.render.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = {GuiScreen.class}, remap = false)
public interface GuiScreenAccessor {
	@Accessor
	Minecraft getMc();

	@Accessor
	FontRenderer getFontRenderer();
}
