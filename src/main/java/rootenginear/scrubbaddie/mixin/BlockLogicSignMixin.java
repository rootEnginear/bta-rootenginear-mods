package rootenginear.scrubbaddie.mixin;

import net.minecraft.core.block.BlockLogicSign;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.scrubbaddie.ScrubBaddie;

@Mixin(value = BlockLogicSign.class, remap = false)
public class BlockLogicSignMixin {
	@Inject(method = "onBlockRightClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;displaySignEditorScreen(Lnet/minecraft/core/block/entity/TileEntitySign;)V"), cancellable = true)
	private void allowScrubbing(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir) {
		if (player.getHeldItem() != null && player.getHeldItem().itemID == ScrubBaddie.scrubBaddie.id)
			cir.setReturnValue(false);
	}
}
