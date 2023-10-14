package rootenginear.bitbybit.utils;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import java.util.Random;

public class ItemFoodEatEffect {
    public static void playEatEffect(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        final Random rand = new Random();

        final double yRotRad = entityplayer.yRot * Math.PI / 180.0F;
        final double xRotRad = entityplayer.xRot * Math.PI / 180.0F;

        final double mouthDistConst = 0.3F;
        final double mouthShift = Math.cos(xRotRad) * mouthDistConst;
        final double xShift = Math.sin(yRotRad) * mouthShift;
        final double zShift = Math.cos(yRotRad) * mouthShift;
        final double yShift = Math.sin(xRotRad) * 0.3F;

        for (int i = 0; i < 2; i++) {
            world.spawnParticle("bitbybit." + itemstack.getItem().id, entityplayer.x - xShift, entityplayer.y - 0.2F - yShift, entityplayer.z + zShift, 0.0, 0.0, 0.0);
        }

        final float volume = ((float) (rand.nextInt(3) + 1)) / 0.5F;
        final float pitch = ((float) rand.nextInt(5)) / 10.0F + 0.8F;

        world.playSoundAtEntity(entityplayer, "bitbybit.eat", volume, pitch);
    }
}
