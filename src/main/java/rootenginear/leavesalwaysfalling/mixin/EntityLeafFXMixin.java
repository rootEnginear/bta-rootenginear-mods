package rootenginear.leavesalwaysfalling.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLeavesRetro;
import net.minecraft.core.entity.fx.EntityLeafFX;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.leavesalwaysfalling.mixin.accessors.EntityFXAccessor;

@Mixin(value = {EntityLeafFX.class}, remap = false)
public class EntityLeafFXMixin {
	@Inject(method = "<init>", at = @At("TAIL"))
	private void checkRetroLeaves(World world, double d, double d1, double d2, double d3, double d4, double d5, CallbackInfo ci) {
		if (world.getBlock((int) d, (int) d1, (int) d2) instanceof BlockLeavesRetro) {
			((EntityFXAccessor) this).setParticleTextureIndex(Block.texCoordToIndex(2, 22));
		}
	}
}
