package rootenginear.sortchest.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.render.FontRenderer;
import org.lwjgl.opengl.GL11;

public class GuiSortChestButton extends GuiButton {
	private final int leftPadding;
	private final String tooltipText;

	public GuiSortChestButton(int id, int xPosition, int yPosition, int width, int height, String text, int leftPadding, String tooltipText) {
		super(id, xPosition, yPosition, width, height, text);
		this.leftPadding = leftPadding;
		this.tooltipText = tooltipText;
	}

	@Override
	public void drawButton(Minecraft minecraft, int i, int j) {
		if (!this.visible) {
			return;
		}
		FontRenderer fontrenderer = minecraft.fontRenderer;
		GL11.glBindTexture(3553, minecraft.renderEngine.getTexture("/gui/gui.png"));
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		boolean flag = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
		int k = this.getButtonState(flag);

		// Edited — Draw from 4 corners
		this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height / 2);
		this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height / 2);
		this.drawTexturedModalRect(this.xPosition, this.yPosition + this.height / 2, 0, (46 + (k + 1) * 20) - this.height / 2, this.width / 2, this.height / 2);
		this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition + this.height / 2, 200 - this.width / 2, (46 + (k + 1) * 20) - this.height / 2, this.width / 2, this.height / 2);
		// End

		this.mouseDragged(minecraft, i, j);

		// Edited — Using `leftPadding` on x position
		if (!this.enabled) {
			this.drawStringCentered(fontrenderer, this.displayString, this.xPosition + this.leftPadding, this.yPosition + (this.height - 8) / 2, -6250336);
		} else if (flag) {
			this.drawStringCentered(fontrenderer, this.displayString, this.xPosition + this.leftPadding, this.yPosition + (this.height - 8) / 2, 0xFFFFA0);
		} else {
			this.drawStringCentered(fontrenderer, this.displayString, this.xPosition + this.leftPadding, this.yPosition + (this.height - 8) / 2, 0xE0E0E0);
		}
		// End
	}

	public String getTooltipText() {
		return this.tooltipText;
	}
}
