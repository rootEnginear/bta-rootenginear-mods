package rootenginear.leavesalwaysfalling;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.function.Function;

public class ModMenuModule implements ModMenuApi {
	@Override
	public String getModId() {
		return LeavesAlwaysFalling.MOD_ID;
	}

	@Override
	public Function<GuiScreen, ? extends GuiScreen> getConfigScreenFactory() {
		return (screenBase -> new GuiOptionsPageLeaves(screenBase, ((Minecraft) FabricLoader.getInstance().getGameInstance()).gameSettings));
	}
}
