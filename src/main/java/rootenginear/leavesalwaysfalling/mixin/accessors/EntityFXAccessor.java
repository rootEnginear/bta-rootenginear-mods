package rootenginear.leavesalwaysfalling.mixin.accessors;

import net.minecraft.client.entity.fx.EntityFX;
import net.minecraft.client.render.stitcher.IconCoordinate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = {EntityFX.class}, remap = false)
public interface EntityFXAccessor {
	@Accessor("particleTexture")
	void setParticleTexture(IconCoordinate particleTextureIndex);
}
