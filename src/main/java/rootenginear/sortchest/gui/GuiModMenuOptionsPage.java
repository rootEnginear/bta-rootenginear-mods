package rootenginear.sortchest.gui;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.GuiOptions;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import rootenginear.sortchest.interfaces.ISortChestSettings;
import turniplabs.halplibe.helper.ModVersionHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import static rootenginear.sortchest.SortChest.LOGGER;


public class GuiModMenuOptionsPage implements ClientStartEntrypoint {
	public static ISortChestSettings modSettings;
	public static OptionsPage sortOptions;

	public static GuiOptions getOptionsScreen(GuiScreen parent){
		return new GuiOptions(parent,  Minecraft.getMinecraft(Minecraft.class).gameSettings, sortOptions);
	}

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {
		modSettings = (ISortChestSettings) Minecraft.getMinecraft(Minecraft.class).gameSettings;
		sortOptions = new OptionsPage("options.category.sortchest")
			.withComponent(new KeyBindingComponent(modSettings.bta_rootenginear_mods$getKeyDump()))
			.withComponent(new KeyBindingComponent(modSettings.bta_rootenginear_mods$getKeyFill()))
			.withComponent(new KeyBindingComponent(modSettings.bta_rootenginear_mods$getKeyRefill()))
			.withComponent(new KeyBindingComponent(modSettings.bta_rootenginear_mods$getKeySort()));


		if(ModVersionHelper.isModPresent("modmenu")){
			OptionsPages.register(sortOptions);
		}
		//OptionsPages.CONTROLS.withComponent(keybindCategory);

	}
}
