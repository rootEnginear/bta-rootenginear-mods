package rootenginear.sortchest.interfaces;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;

@Environment(EnvType.CLIENT)
public interface ISortChestSettings {
	KeyBinding bta_rootenginear_mods$getKeySort();

	KeyBinding bta_rootenginear_mods$getKeyRefill();

	KeyBinding bta_rootenginear_mods$getKeyFill();

	KeyBinding bta_rootenginear_mods$getKeyDump();
}
