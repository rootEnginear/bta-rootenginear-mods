package rootenginear.extendedfontrendering.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.text.ITextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.extendedfontrendering.interfaces.FontAccessor;
import rootenginear.extendedfontrendering.struct.Feature;

@Environment(EnvType.CLIENT)
@Mixin(value = ITextField.class, remap = false)
public interface ITextFieldMixin {
	@Inject(method = "isCharacterAllowed(C)Z", at = @At("RETURN"), cancellable = true)
	default void injected(char c, CallbackInfoReturnable<Boolean> cir) {
		Feature f = ((FontAccessor) Minecraft.getMinecraft().font).bta_rootenginear_mods$getFeature();
		cir.setReturnValue(cir.getReturnValue() || f.characters.indexOf(c) >= 0);
	}
}
