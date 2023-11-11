package rootenginear.leavesalwaysfalling.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLeavesBase;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.leavesalwaysfalling.LeavesAlwaysFalling;

import java.util.Random;

@Mixin(value = {Block.class}, remap = false)
public class GenericLeavesParticleMixin {
	@Inject(method = "randomDisplayTick", at = @At("HEAD"), cancellable = true)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand, CallbackInfo ci) {
		Block block = world.getBlock(x, y, z);
		if (block instanceof BlockLeavesBase) {
			int randomBound = LeavesAlwaysFalling.getLeavesRandomBound();
			if (randomBound == 0) {
				ci.cancel();
				return;
			}
			if (rand.nextInt(100) < randomBound) {
				world.spawnParticle("fallingleaf", x, (float) y - 0.1f, z, 0.0, 0.0, 0.0);
			}
			ci.cancel();
		}
	}
}
