package rootenginear.bitbybit.mixin.adapter;

import net.minecraft.core.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = {Item.class}, remap = false)
public interface ItemAccessor {
    @Invoker("setMaxDamage")
    Item setFoodMaxDamage(int i);

    @Accessor("maxStackSize")
    void setMaxStackSize(int maxStackSize);
}
