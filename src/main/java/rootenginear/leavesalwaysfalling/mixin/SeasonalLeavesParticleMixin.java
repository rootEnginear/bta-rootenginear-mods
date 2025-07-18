package rootenginear.leavesalwaysfalling.mixin;

import net.minecraft.core.block.BlockLeavesCherry;
import net.minecraft.core.block.BlockLeavesEucalyptus;
import net.minecraft.core.block.BlockLeavesOak;
import net.minecraft.core.block.BlockLeavesShrub;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.leavesalwaysfalling.LeavesAlwaysFalling;

import java.util.Random;

@Mixin(value = {BlockLeavesCherry.class, BlockLeavesEucalyptus.class, BlockLeavesOak.class, BlockLeavesShrub.class}, remap = false)
public class SeasonalLeavesParticleMixin {
	@Inject(method = "randomDisplayTick", at = @At("HEAD"), cancellable = true)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand, CallbackInfo ci) {
		int randomBound = LeavesAlwaysFalling.getLeavesRandomBound();
		if (randomBound == 0) {
			ci.cancel();
			return;
		}
		if (rand.nextInt(100) < randomBound) {
			world.spawnParticle("fallingleaf", x, (double) y - (double) 0.1F, z, 0.0F, 0.0F, 0.0F, 0);
		}
		ci.cancel();
	}
}
