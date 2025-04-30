package rootenginear.carrycakeandpie.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicPiePumpkin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.carrycakeandpie.entity.TileEntityPiePumpkin;

@Mixin(value = BlockLogicPiePumpkin.class, remap = false)
public class BlockLogicPiePumpkinMixin {
	@Inject(method = "<init>", at = @At("TAIL"))
	public void BlockLogicPiePumpkin(Block<?> block, CallbackInfo ci) {
		block.withEntity(TileEntityPiePumpkin::new);
	}
}
