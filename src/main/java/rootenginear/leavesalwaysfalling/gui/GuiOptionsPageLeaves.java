package rootenginear.leavesalwaysfalling.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.GuiOptions;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.option.GameSettings;
import rootenginear.leavesalwaysfalling.interfaces.ILeavesSettings;

public class GuiOptionsPageLeaves extends GuiScreen {
	public static GameSettings gameSettings = Minecraft.getMinecraft(Minecraft.class).gameSettings;

	public static final OptionsPage leavesOptions = new OptionsPage("options.category.leavesalwaysfalling")
		.withComponent(new OptionsCategory("options.category.leavesalwaysfalling")
			.withComponent(new ToggleableOptionComponent<>(((ILeavesSettings) gameSettings).bta_rootenginear_mods$getFrequency()))
		);

	public static GuiOptions createGui(GuiScreen parent) {
		return new GuiOptions(parent, gameSettings, leavesOptions);
	}
}
