package rootenginear.sortchest.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.GuiOptionsPageControls;
import net.minecraft.client.option.GameSettings;
import rootenginear.sortchest.interfaces.ISortChestSettings;
import rootenginear.sortchest.mixin.accessor.GuiOptionsPageControlsAccessor;
import rootenginear.sortchest.mixin.accessor.GuiOptionsPageOptionBaseAccessor;

import java.util.ArrayList;

public class GuiModMenuOptionsPage extends GuiOptionsPageControls {
	public GuiModMenuOptionsPage(GuiScreen parent, GameSettings settings) {
		super(parent, settings);

		GuiOptionsPageOptionBaseAccessor opobThis = (GuiOptionsPageOptionBaseAccessor) this;
		opobThis.setCategoryKeys(new ArrayList<>());
		opobThis.setOptions(new ArrayList<>());
		opobThis.setButtons(new ArrayList<>());

		GuiOptionsPageControlsAccessor opcThis = (GuiOptionsPageControlsAccessor) this;
		opcThis.setKeyBindings(new ArrayList<>());

		ISortChestSettings iSortChestSettings = (ISortChestSettings) settings;
		this.addKeyBindingsCategory(
			"options.category.sortchest",
			iSortChestSettings.bta_rootenginear_mods$getKeySort(),
			iSortChestSettings.bta_rootenginear_mods$getKeyRefill(),
			iSortChestSettings.bta_rootenginear_mods$getKeyFill(),
			iSortChestSettings.bta_rootenginear_mods$getKeyDump()
		);
	}
}
