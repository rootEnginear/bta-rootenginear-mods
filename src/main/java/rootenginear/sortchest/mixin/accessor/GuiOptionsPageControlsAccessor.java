package rootenginear.sortchest.mixin.accessor;

import net.minecraft.client.gui.options.GuiOptionsPageControls;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = {GuiOptionsPageControls.class}, remap = false)
public interface GuiOptionsPageControlsAccessor {
	@Accessor("keyBindings")
	void setKeyBindings(List<KeyBinding[]> keyBindings);
}
