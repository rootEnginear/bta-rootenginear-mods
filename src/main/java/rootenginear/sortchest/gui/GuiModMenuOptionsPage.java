package rootenginear.sortchest.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.options.ScreenOptions;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.input.Keyboard;
import turniplabs.halplibe.util.ClientStartEntrypoint;

@Environment(EnvType.CLIENT)
public class GuiModMenuOptionsPage implements ClientStartEntrypoint {
	public static OptionsPage sortOptions;

	public static KeyBinding keySort = (new KeyBinding("sortchest.sort")).setDefault(InputDevice.keyboard, Keyboard.KEY_S);
	public static KeyBinding keyRefill = (new KeyBinding("sortchest.refill")).setDefault(InputDevice.keyboard, Keyboard.KEY_R);
	public static KeyBinding keyFill = (new KeyBinding("sortchest.fill")).setDefault(InputDevice.keyboard, Keyboard.KEY_F);
	public static KeyBinding keyDump = (new KeyBinding("sortchest.dump")).setDefault(InputDevice.keyboard, Keyboard.KEY_D);

	public static ScreenOptions createGui(Screen parent) {
		return new ScreenOptions(parent, sortOptions);
	}

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {
		GameSettings.keys.add(keySort);
		GameSettings.keys.add(keyRefill);
		GameSettings.keys.add(keyFill);
		GameSettings.keys.add(keyDump);

		sortOptions = new OptionsPage("options.category.sortchest", new ItemStack(Blocks.CHEST_PLANKS_OAK))
			.withComponent((new OptionsCategory("options.category.sortchest"))
				.withComponent(new KeyBindingComponent(keySort))
				.withComponent(new KeyBindingComponent(keyRefill))
				.withComponent(new KeyBindingComponent(keyFill))
				.withComponent(new KeyBindingComponent(keyDump))
			);

		OptionsPages.register(sortOptions);
	}
}
