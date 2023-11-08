package rootenginear.sortchest.mixin.accessor;

import net.minecraft.client.gui.GuiChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = {GuiChest.class}, remap = false)
public interface GuiChestAccessor {
	@Accessor
	int getInventoryRows();
}
