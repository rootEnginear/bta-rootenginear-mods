package rootenginear.bitbybit.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.core.entity.fx.EntitySlimeFX;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(value = {RenderGlobal.class}, remap = false)
public class RenderGlobalMixin {
    @Shadow
    private Minecraft mc;

    @Shadow
    private World worldObj;

    @Inject(method = "addParticle(Ljava/lang/String;DDDDDDD)V", at = @At("HEAD"), cancellable = true)
    public void addFoodParticle(String particleId, double x, double y, double z, double motionX, double motionY, double motionZ, double maxDistance, CallbackInfo ci) {
        Pattern foodItemPattern = Pattern.compile("bitbybit\\.(.+)");
        Matcher foodMatch = foodItemPattern.matcher(particleId);
        if (foodMatch.matches() && this.mc != null && this.mc.activeCamera != null && this.mc.effectRenderer != null) {
            double d6 = this.mc.activeCamera.getX() - x;
            double d7 = this.mc.activeCamera.getY() - y;
            double d8 = this.mc.activeCamera.getZ() - z;
            if (!(d6 * d6 + d7 * d7 + d8 * d8 > maxDistance * maxDistance)) {
                this.mc.effectRenderer.addEffect(new EntitySlimeFX(this.worldObj, x, y, z, Item.itemsList[Integer.parseInt(foodMatch.group(1))]));
            }
            ci.cancel();
        }
    }
}
