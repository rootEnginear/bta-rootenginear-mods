package rootenginear.sortchest.mixin.accessor;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.options.GuiOptionsPageOptionBase;
import net.minecraft.client.option.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = {GuiOptionsPageOptionBase.class})
public interface GuiOptionsPageOptionBaseAccessor {
	@Accessor("categoryKeys")
	void setCategoryKeys(List<String> categoryKeys);

	@Accessor("options")
	void setOptions(List<Option<?>[]> options);

	@Accessor("buttons")
	void setButtons(List<GuiButton[]> buttons);
}
