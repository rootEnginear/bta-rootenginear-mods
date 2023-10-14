package rootenginear.bitbybit.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucketIceCream;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.bitbybit.mixin.adapter.ItemAccessor;
import rootenginear.bitbybit.utils.ItemFoodEatEffect;

@Mixin(value = {ItemBucketIceCream.class}, remap = false)
public class ItemBucketIceCreamOverride {
    @Shadow
    protected int healAmount;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        ((ItemAccessor) this).setFoodMaxDamage(healAmount);
    }

    @Unique
    public boolean consumeFood(ItemStack itemstack, EntityPlayer entityplayer) {
        if (itemstack.stackSize <= 0) return false;

        if (entityplayer.getGamemode().consumeBlocks) {
            itemstack.damageItem(1, entityplayer);
            if (itemstack.getItemDamageForDisplay() == healAmount) {
                --itemstack.stackSize;
            }
        }
        return true;
    }

    /**
     * @author rootEnginear.bitbybit
     * @reason Make food eat bit by bit
     */
    @Overwrite
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (entityplayer.health < 20 && consumeFood(itemstack, entityplayer)) {
            ItemFoodEatEffect.playEatEffect(itemstack, world, entityplayer);
            entityplayer.heal(1);
        }
        if (itemstack.stackSize == 0) return new ItemStack(Item.bucket);
        return itemstack;
    }

    /**
     * @author rootEnginear.bitbybit
     * @reason Show how much leftover heals
     * @implNote Somewhat buggy in multiplayer
     */
    @Overwrite
    public int getHealAmount() {
        ItemStack currentItem = Minecraft.getMinecraft(Minecraft.class).thePlayer.inventory.getCurrentItem();
        int damage = 0;
        if (currentItem != null) damage = currentItem.getItemDamageForDisplay();
        return healAmount - damage;
    }
}
