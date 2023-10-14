package rootenginear.bitbybit.mixin;

import net.minecraft.core.entity.animal.EntityWolf;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = {EntityWolf.class}, remap = false)
public class EntityWolfMixin {
    @Redirect(
            method = "interact(Lnet/minecraft/core/entity/player/EntityPlayer;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/entity/player/EntityPlayer;getGamemode()Lnet/minecraft/core/player/gamemode/Gamemode;"
            )
    )
    private Gamemode nibbleFood(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if (entityplayer.getGamemode().consumeBlocks) {
            itemstack.damageItem(1, entityplayer);
            if (((ItemFood) itemstack.getItem()).getHealAmount() == 0) {
                return Gamemode.survival;
            }
        }
        return Gamemode.creative;
    }

    @Redirect(
            method = "interact(Lnet/minecraft/core/entity/player/EntityPlayer;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/item/ItemFood;getHealAmount()I"
            )
    )
    public int healFromNibble(ItemFood item) {
        return 1;
    }
}
