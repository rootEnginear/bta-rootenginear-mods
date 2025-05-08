package rootenginear.sortchest;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.options.ScreenOptions;
import rootenginear.sortchest.gui.GuiModMenuOptionsPage;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ModMenuModule implements ModMenuApi {
	@Override
	public String getModId() {
		return SortChest.MOD_ID;
	}

	@Override
	public Function<Screen, ScreenOptions> getConfigScreenFactory() {
		return (GuiModMenuOptionsPage::createGui);
	}
}
