package rootenginear.sortchest;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.KeyBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rootenginear.sortchest.interfaces.ISortChestSettings;

@Environment(EnvType.CLIENT)
public class SortChest implements ModInitializer {
	public static final String MOD_ID = "sortchest";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static KeyBinding getKeySort() {
		return ((ISortChestSettings) Minecraft.getMinecraft().gameSettings).bta_rootenginear_mods$getKeySort();
	}

	public static KeyBinding getKeyRefill() {
		return ((ISortChestSettings) Minecraft.getMinecraft().gameSettings).bta_rootenginear_mods$getKeyRefill();
	}

	public static KeyBinding getKeyFill() {
		return ((ISortChestSettings) Minecraft.getMinecraft().gameSettings).bta_rootenginear_mods$getKeyFill();
	}

	public static KeyBinding getKeyDump() {
		return ((ISortChestSettings) Minecraft.getMinecraft().gameSettings).bta_rootenginear_mods$getKeyDump();
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Sort Chest initialized.");
	}
}
