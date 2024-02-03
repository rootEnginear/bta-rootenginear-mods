package rootenginear.sortchest;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rootenginear.sortchest.gui.GuiModMenuOptionsPage;

public class SortChest implements  ModInitializer {
	public static final String MOD_ID = "sortchest";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Sort Chest initialized.");
	}
}
