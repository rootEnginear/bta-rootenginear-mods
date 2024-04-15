package rootenginear.leavesalwaysfalling.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.GuiOptions;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import rootenginear.leavesalwaysfalling.interfaces.ILeavesSettings;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class GuiOptionsPageLeaves implements ClientStartEntrypoint {
	public static ILeavesSettings modSettings;
	public static OptionsPage leavesOptions;

	public static GuiOptions createGui(GuiScreen parent) {
		return new GuiOptions(parent, leavesOptions);
	}

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {
		modSettings = (ILeavesSettings) Minecraft.getMinecraft(Minecraft.class).gameSettings;
		leavesOptions = new OptionsPage("options.category.leavesalwaysfalling", new ItemStack(Block.leavesOak))
			.withComponent(new OptionsCategory("options.category.leavesalwaysfalling")
				.withComponent(new ToggleableOptionComponent<>(modSettings.bta_rootenginear_mods$getFrequency()))
			);

		OptionsPages.register(leavesOptions);
	}
}
