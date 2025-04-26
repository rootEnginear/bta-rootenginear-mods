package rootenginear.leavesalwaysfalling.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.options.ScreenOptions;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import rootenginear.leavesalwaysfalling.interfaces.ILeavesSettings;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class GuiOptionsPageLeaves implements ClientStartEntrypoint {
	public static ILeavesSettings modSettings;
	public static OptionsPage leavesOptions;

	public static ScreenOptions createGui(Screen parent) {
		return new ScreenOptions(parent, leavesOptions);
	}

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {
		modSettings = (ILeavesSettings) Minecraft.getMinecraft().gameSettings;
		leavesOptions = new OptionsPage("options.category.leavesalwaysfalling", new ItemStack(Blocks.LEAVES_OAK_RETRO))
			.withComponent(new OptionsCategory("options.category.leavesalwaysfalling")
				.withComponent(new ToggleableOptionComponent<>(modSettings.bta_rootenginear_mods$getFrequency()))
			);

		OptionsPages.register(leavesOptions);
	}
}
