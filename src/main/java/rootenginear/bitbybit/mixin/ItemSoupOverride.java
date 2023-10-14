package rootenginear.bitbybit.mixin;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemSoup;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = {ItemSoup.class}, remap = false)
public class ItemSoupOverride extends ItemFoodOverride {
    /**
     * @author rootEnginear.bitbybit
     * @reason Make food eat bit by bit
     */
    @Overwrite
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        super.onItemRightClick(itemstack, world, entityplayer);
        if (itemstack.stackSize == 0) return new ItemStack(Item.bowl);
        return itemstack;
    }
}
