package rootenginear.carrycakeandpie.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicCake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.carrycakeandpie.entity.TileEntityCake;

@Mixin(value = BlockLogicCake.class, remap = false)
public class BlockLogicCakeMixin {
	@Inject(method = "<init>", at = @At("TAIL"))
	public void BlockLogicCake(Block<?> block, CallbackInfo ci) {
		block.withEntity(TileEntityCake::new);
	}
}
