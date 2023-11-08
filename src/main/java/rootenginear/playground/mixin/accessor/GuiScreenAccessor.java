package rootenginear.playground.mixin.accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = {GuiScreen.class}, remap = false)
public interface GuiScreenAccessor {
	@Accessor
	Minecraft getMc();
}
