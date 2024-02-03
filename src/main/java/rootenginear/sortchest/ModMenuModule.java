package rootenginear.sortchest;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.GuiScreen;
import rootenginear.sortchest.gui.GuiModMenuOptionsPage;

import java.util.function.Function;

public class ModMenuModule implements ModMenuApi {
	@Override
	public String getModId() {
		return SortChest.MOD_ID;
	}

	@Override
	public Function<GuiScreen, ? extends GuiScreen> getConfigScreenFactory() {
		return (GuiModMenuOptionsPage::getOptionsScreen);
	}
}
