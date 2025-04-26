package rootenginear.leavesalwaysfalling.mixin;

import net.minecraft.client.entity.particle.ParticleLeaf;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.leavesalwaysfalling.mixin.accessors.ParticleAccessor;

@Mixin(value = {ParticleLeaf.class}, remap = false)
public class ParticleLeafMixin {
	@Inject(method = "<init>", at = @At("TAIL"))
	private void checkRetroLeaves(World world, double x, double y, double z, double xa, double ya, double za, CallbackInfo ci) {
		Block<?> block = world.getBlock((int) x, (int) y, (int) z);
		if (block != null && block.namespaceId().toString().equals("minecraft:block/leaves_oak_retro")) {
			((ParticleAccessor) this).setTex(TextureRegistry.getTexture("minecraft:block/leaves/oak_retro_fancy"));
		}
	}
}
