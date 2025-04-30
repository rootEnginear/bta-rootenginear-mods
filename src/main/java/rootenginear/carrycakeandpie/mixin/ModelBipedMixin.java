package rootenginear.carrycakeandpie.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.motion.CarriedBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = ModelBiped.class, remap = false)
public class ModelBipedMixin {
	@Shadow
	public Cube armRight;

	@Shadow
	public Cube armLeft;

	@Inject(method = "setupAnimation", at = @At("TAIL"))
	public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale, CallbackInfo ci) {
		PlayerLocal player = Minecraft.getMinecraft().thePlayer;
		CarriedBlock carriedBlock = (CarriedBlock) player.getHeldObject();
		if (carriedBlock == null) return;

		Cube armRight = this.armRight;
		Cube armLeft = this.armLeft;
		if (carriedBlock.blockId == Blocks.CAKE.id()) {
			armRight.xRot -= 0.3f;
			armLeft.xRot -= 0.3f;
			return;
		}
		if (carriedBlock.blockId == Blocks.PUMPKIN_PIE.id()) {
			armRight.xRot -= 0.5f;
			armLeft.xRot -= 0.5f;
		}
	}
}
