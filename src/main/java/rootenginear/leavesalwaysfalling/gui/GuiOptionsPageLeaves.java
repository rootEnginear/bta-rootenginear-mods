package rootenginear.leavesalwaysfalling.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.options.ScreenOptions;
import net.minecraft.client.gui.options.components.FloatOptionComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import rootenginear.leavesalwaysfalling.LeavesAlwaysFalling;
import turniplabs.halplibe.util.ClientStartEntrypoint;

@Environment(EnvType.CLIENT)
public class GuiOptionsPageLeaves implements ClientStartEntrypoint {
	public static OptionsPage leavesOptions;

	public static ScreenOptions createGui(Screen parent) {
		return new ScreenOptions(parent, leavesOptions);
	}

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {
		leavesOptions = new OptionsPage("options.category.leavesalwaysfalling", new ItemStack(Blocks.LEAVES_OAK_RETRO))
			.withComponent(new OptionsCategory("options.category.leavesalwaysfalling")
				.withComponent(new ToggleableOptionComponent<>(LeavesAlwaysFalling.getFrequencyOption()))
				.withComponent(new FloatOptionComponent(LeavesAlwaysFalling.getGravityMultiplierOption()))
				.withComponent(new FloatOptionComponent(LeavesAlwaysFalling.getXMultiplierOption()))
				.withComponent(new FloatOptionComponent(LeavesAlwaysFalling.getZMultiplierOption()))
			);

		OptionsPages.register(leavesOptions);
	}
}
