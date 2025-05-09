package rootenginear.sortchest.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.options.ScreenOptions;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import rootenginear.sortchest.SortChest;
import turniplabs.halplibe.util.ClientStartEntrypoint;

@Environment(EnvType.CLIENT)
public class GuiModMenuOptionsPage implements ClientStartEntrypoint {
	public static OptionsPage sortOptions;

	public static ScreenOptions createGui(Screen parent) {
		return new ScreenOptions(parent, sortOptions);
	}

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {
		sortOptions = OptionsPages.register(
			new OptionsPage("options.category.sortchest", new ItemStack(Blocks.CHEST_PLANKS_OAK))
				.withComponent(
					new OptionsCategory("options.category.sortchest")
						.withComponent(new KeyBindingComponent(SortChest.getKeySort()))
						.withComponent(new KeyBindingComponent(SortChest.getKeyRefill()))
						.withComponent(new KeyBindingComponent(SortChest.getKeyFill()))
						.withComponent(new KeyBindingComponent(SortChest.getKeyDump()))
				)
		);
	}
}
