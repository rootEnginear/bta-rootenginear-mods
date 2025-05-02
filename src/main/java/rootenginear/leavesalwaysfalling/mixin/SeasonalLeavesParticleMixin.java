package rootenginear.leavesalwaysfalling.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.block.BlockLogicLeavesCherry;
import net.minecraft.core.block.BlockLogicLeavesEucalyptus;
import net.minecraft.core.block.BlockLogicLeavesOak;
import net.minecraft.core.block.BlockLogicLeavesShrub;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.leavesalwaysfalling.gui.GuiOptionsPageLeaves;

import java.util.Random;

@Environment(EnvType.CLIENT)
@Mixin(value = {BlockLogicLeavesCherry.class, BlockLogicLeavesEucalyptus.class, BlockLogicLeavesOak.class, BlockLogicLeavesShrub.class}, remap = false)
public class SeasonalLeavesParticleMixin {
	@Inject(method = "animationTick", at = @At("HEAD"), cancellable = true)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand, CallbackInfo ci) {
		int randomBound = GuiOptionsPageLeaves.leavesFrequency.value;
		if (randomBound == 0) {
			ci.cancel();
			return;
		}
		if (rand.nextInt(100) < randomBound) {
			world.spawnParticle("fallingleaf", x, (double) y - 0.1, z, 0.0, 0.0, 0.0, 0);
		}
		ci.cancel();
	}
}
