package rootenginear.carrycakeandpie.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.motion.CarriedBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererPlayer.class, remap = false)
public class MobRendererPlayerMixin {
	@ModifyArgs(method = "drawHeldObject", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V"))
	public void changeY(Args args) {
		PlayerLocal player = Minecraft.getMinecraft().thePlayer;
		CarriedBlock carriedBlock = (CarriedBlock) player.getHeldObject();
		if (carriedBlock == null) return;

		if (carriedBlock.blockId == Blocks.CAKE.id()) {
			args.set(1, -0.25f);
			args.set(2, -1.0f);
			return;
		}
		if (carriedBlock.blockId == Blocks.PUMPKIN_PIE.id()) {
			args.set(1, -0.13f);
			args.set(2, -1.0f);
		}
	}
}
