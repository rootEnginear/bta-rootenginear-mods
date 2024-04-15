package rootenginear.sortchest.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.GuiOptions;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import rootenginear.sortchest.interfaces.ISortChestSettings;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class GuiModMenuOptionsPage implements ClientStartEntrypoint {
	public static ISortChestSettings modSettings;
	public static OptionsPage sortOptions;

	public static GuiOptions getOptionsScreen(GuiScreen parent) {
		return new GuiOptions(parent, sortOptions);
	}

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {
		modSettings = (ISortChestSettings) Minecraft.getMinecraft(Minecraft.class).gameSettings;
		sortOptions = new OptionsPage("options.category.sortchest", new ItemStack(Block.chestPlanksOak))
			.withComponent(new KeyBindingComponent(modSettings.bta_rootenginear_mods$getKeyDump()))
			.withComponent(new KeyBindingComponent(modSettings.bta_rootenginear_mods$getKeyFill()))
			.withComponent(new KeyBindingComponent(modSettings.bta_rootenginear_mods$getKeyRefill()))
			.withComponent(new KeyBindingComponent(modSettings.bta_rootenginear_mods$getKeySort()));

		OptionsPages.register(sortOptions);
	}
}
