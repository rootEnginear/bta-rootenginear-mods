package rootenginear.leavesalwaysfalling.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.particle.ParticleLeaf;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.leavesalwaysfalling.LeavesAlwaysFalling;
import rootenginear.leavesalwaysfalling.mixin.accessors.ParticleAccessor;
import rootenginear.leavesalwaysfalling.utils.Utils;

@Environment(EnvType.CLIENT)
@Mixin(value = {ParticleLeaf.class}, remap = false)
public class ParticleLeafMixin {
	@Inject(method = "<init>", at = @At("TAIL"))
	private void checkRetroLeaves(World world, double x, double y, double z, double xa, double ya, double za, CallbackInfo ci) {
		Block<?> block = world.getBlock((int) x, (int) y, (int) z);
		if (block != null && block.namespaceId().toString().equals("minecraft:block/leaves_oak_retro")) {
			((ParticleAccessor) this).setTex(TextureRegistry.getTexture("minecraft:block/leaves/oak_retro_fancy"));
		}
	}

	@Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/particle/ParticleLeaf;yd:D", opcode = Opcodes.PUTFIELD))
	public void modifyYd(ParticleLeaf particleLeaf, double yd) {
		particleLeaf.yd -= (particleLeaf.yd - yd) * ((double) Utils.normalizeGravityMultiplier(LeavesAlwaysFalling.getGravityMultiplierOption().value));
	}

	@Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/particle/ParticleLeaf;xd:D", opcode = Opcodes.PUTFIELD, ordinal = 0))
	public void modifyXd(ParticleLeaf particleLeaf, double xd) {
		particleLeaf.xd += (xd - particleLeaf.xd) * ((double) Utils.normalizeGravityMultiplier(LeavesAlwaysFalling.getXMultiplierOption().value));
	}

	@Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/particle/ParticleLeaf;zd:D", opcode = Opcodes.PUTFIELD, ordinal = 0))
	public void modifyZd(ParticleLeaf particleLeaf, double zd) {
		particleLeaf.zd += (zd - particleLeaf.zd) * ((double) Utils.normalizeGravityMultiplier(LeavesAlwaysFalling.getZMultiplierOption().value));
	}
}
