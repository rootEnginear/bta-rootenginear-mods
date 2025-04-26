package rootenginear.leavesalwaysfalling;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.options.ScreenOptions;
import rootenginear.leavesalwaysfalling.gui.GuiOptionsPageLeaves;

import java.util.function.Function;

public class ModMenuModule implements ModMenuApi {
	@Override
	public String getModId() {
		return LeavesAlwaysFalling.MOD_ID;
	}

	@Override
	public Function<Screen, ScreenOptions> getConfigScreenFactory() {
		return GuiOptionsPageLeaves::createGui;
	}
}
