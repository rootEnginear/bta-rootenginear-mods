package rootenginear.bitbybit.mixin;

import net.minecraft.core.item.ItemFoodStackable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.bitbybit.mixin.adapter.ItemAccessor;

@Mixin(value = {ItemFoodStackable.class}, remap = false)
public abstract class ItemFoodStackableOverride {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        ((ItemAccessor) this).setMaxStackSize(1);
    }
}
