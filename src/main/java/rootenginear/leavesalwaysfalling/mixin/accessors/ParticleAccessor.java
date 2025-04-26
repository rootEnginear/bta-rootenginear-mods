package rootenginear.leavesalwaysfalling.mixin.accessors;

import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = {Particle.class}, remap = false)
public interface ParticleAccessor {
	@Accessor("tex")
	void setTex(IconCoordinate tex);
}
