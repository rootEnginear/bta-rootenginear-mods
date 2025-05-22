package rootenginear.extendedfontrendering.mixin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.TextureManager;
import net.minecraft.core.net.command.TextFormatting;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.extendedfontrendering.ExtendedFontRendering;
import rootenginear.extendedfontrendering.interfaces.FontAccessor;
import rootenginear.extendedfontrendering.struct.Feature;
import rootenginear.extendedfontrendering.struct.PositionOptions;

import java.io.IOException;
import java.util.Map;

import static rootenginear.extendedfontrendering.ExtendedFontRendering.FEATURE_PATH;
import static rootenginear.extendedfontrendering.loader.FontFeatureLoader.loadFeatureFromFile;

@Environment(EnvType.CLIENT)
@Mixin(value = Font.class, remap = false)
public abstract class FontMixin implements FontAccessor {
	@Unique
	public Feature feature;
	@Shadow
	@Final
	private byte[] glyphWidth;
	@Shadow
	@Final
	private int[] glyphTextureNames;
	@Shadow
	private int boundTextureName;
	@Shadow
	private float posX;
	@Shadow
	private float posY;
	@Unique
	private char prevChar;

	@Unique
	public Feature bta_rootenginear_mods$getFeature() {
		return feature;
	}

	@Shadow
	protected abstract void loadGlyphTexture(int id);

	@Shadow
	public abstract int getCharWidth(char c);

	@Inject(method = "<init>", at = @At(value = "TAIL"))
	private void init(Minecraft minecraft, GameSettings gameSettings, TextureManager textureManager, CallbackInfo ci) {
		try {
			feature = loadFeatureFromFile(FEATURE_PATH);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ExtendedFontRendering.LOGGER.info("Loaded feature: {}", gson.toJson(feature));
	}

	@Unique
	private boolean isFeatureWhitelisted(char c) {
		return feature.characters.indexOf(c) >= 0;
	}

	@Unique
	public PositionOptions getCharRuleSet(char currChar, char prevChar) {
		PositionOptions defaultRuleSet = new PositionOptions();
		String currCharClassName = feature.classes.get(currChar + "");
		if (currCharClassName == null) return defaultRuleSet;
		String prevCharClassName = feature.classes.get(prevChar + "");
		try {
			// Find compound rule
			Map<String, PositionOptions> prevCharRuleSet = feature.positioning.get(prevCharClassName);
			if (prevCharRuleSet != null) {
				// there might be a compound rule
				PositionOptions prevCharHasCurrCharRuleSet = prevCharRuleSet.get(currCharClassName);
				// if rule found, then use that rule
				if (prevCharHasCurrCharRuleSet != null) return prevCharHasCurrCharRuleSet;
				// else fallthru
			}
			// Find direct rule
			Map<String, PositionOptions> currCharRuleSet = feature.positioning.get(currCharClassName);
			if (currCharRuleSet == null) return defaultRuleSet;
			PositionOptions currCharDefaultRuleSet = currCharRuleSet.get("DEFAULT");
			if (currCharDefaultRuleSet == null) return defaultRuleSet;
			return currCharDefaultRuleSet;
		} catch (Exception e) {
			// Safeguard
			return defaultRuleSet;
		}
	}

	@Inject(method = "renderCharAtPos", at = @At("TAIL"))
	private void savePrevCharForRenderCharAtPos(int formatCode, char c, boolean italic, CallbackInfoReturnable<Float> cir) {
		this.prevChar = c;
	}

	@Inject(method = "renderUnicodeChar", at = @At("HEAD"), cancellable = true)
	private void renderCharacterWithFeature(char c, boolean italic, CallbackInfoReturnable<Float> cir) {
		if (this.isFeatureWhitelisted(c)) {
			int charWrapped = c / 256;

			if (this.glyphTextureNames[charWrapped] == 0) {
				this.loadGlyphTexture(charWrapped);
			}

			if (this.boundTextureName != this.glyphTextureNames[charWrapped]) {
				GL11.glBindTexture(3553, this.glyphTextureNames[charWrapped]);
				this.boundTextureName = this.glyphTextureNames[charWrapped];
			}

			int leftBound = this.glyphWidth[c] >>> 4;
			int rightBound = this.glyphWidth[c] & 15;
			float fLeftBound = (float) leftBound;
			float fRightBound = (float) (rightBound + 1);
			float x = (float) (c % 16 * 16) + fLeftBound;
			float y = (float) ((c & 255) / 16 * 16);
			float width = fRightBound - fLeftBound - 0.02F;
			float italicOffset = italic ? 1.0F : 0.0F;

			PositionOptions ruleset = getCharRuleSet(c, this.prevChar);

			float xOffset = (ruleset.floating ? -(rightBound + 1F) / 2.0F - 1F : 0F) + ruleset.x + feature.global.x;
			float yOffset = ruleset.y + feature.global.y;
			float charWidth = ruleset.floating ? 0f : (fRightBound - fLeftBound) / 2.0F + 1.0F + ruleset.x;

			GL11.glBegin(5);
			GL11.glTexCoord2f(x / 256.0F, y / 256.0F);
			GL11.glVertex3f(this.posX + xOffset + italicOffset, this.posY + yOffset, 0.0F);
			GL11.glTexCoord2f(x / 256.0F, (y + 15.98F) / 256.0F);
			GL11.glVertex3f(this.posX + xOffset - italicOffset, this.posY + yOffset + 7.99F, 0.0F);
			GL11.glTexCoord2f((x + width) / 256.0F, y / 256.0F);
			GL11.glVertex3f(this.posX + xOffset + width / 2.0F + italicOffset, this.posY + yOffset, 0.0F);
			GL11.glTexCoord2f((x + width) / 256.0F, (y + 15.98F) / 256.0F);
			GL11.glVertex3f(this.posX + xOffset + width / 2.0F - italicOffset, this.posY + yOffset + 7.99F, 0.0F);
			GL11.glEnd();

			cir.setReturnValue(charWidth);
		}
	}

	@Unique
	public String substituteGroups(String text) {
		for (Map.Entry<String, String> entry : feature.replacements.entrySet()) {
			text = text.replaceAll(entry.getKey(), entry.getValue());
		}
		return text;
	}

	@ModifyArg(method = "renderStringInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Font;renderStringAtPos(Ljava/lang/String;Z)V"), index = 0)
	private String renderStringInternal(String text) {
		return substituteGroups(text);
	}

	/**
	 * @author rootEnginear
	 * @reason need more local variables to perform lookahead/lookbehind operations
	 */
	@Overwrite
	public int getStringWidth(String text) {
		String string = substituteGroups(text);
		if (string == null) {
			return 0;
		} else {
			string = TextFormatting.removeAllFormatting(string);
			int stringWidth = 0;
			boolean addSpacerPixel = false;

			char prevChar = '\0';
			for (int i = 0; i < string.length(); i++) {
				char c = string.charAt(i);
				int charWidth = getThaiCharWidth(c, prevChar);
				prevChar = c;
				if (charWidth < 0 && i < string.length() - 1) {
					c = string.charAt(++i);
					if (c == 'l' || c == 'L') {
						addSpacerPixel = true;
					} else if (c == 'r' || c == 'R') {
						addSpacerPixel = false;
					}

					charWidth = 0;
				}

				stringWidth += charWidth;
				if (addSpacerPixel) {
					stringWidth++;
				}
			}

			return stringWidth;
		}
	}

	@Unique
	private int getThaiCharWidth(char c, char prevChar) {
		if (this.isFeatureWhitelisted(c)) {
			PositionOptions ruleset = getCharRuleSet(c, prevChar);

			if (ruleset.floating) return 0;

			int leftBound = this.glyphWidth[c] >>> 4;
			int rightBound = this.glyphWidth[c] & 15;
			float fLeftBound = (float) leftBound;
			float fRightBound = (float) (rightBound + 1);

			return (int) ((fRightBound - fLeftBound) / 2.0F + 1.0F + ruleset.x);
		}
		return this.getCharWidth(c);
	}
}
