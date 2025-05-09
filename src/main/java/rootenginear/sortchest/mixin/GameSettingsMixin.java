package rootenginear.sortchest.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import rootenginear.sortchest.interfaces.ISortChestSettings;

@Environment(EnvType.CLIENT)
@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin implements ISortChestSettings {
	@Unique
	private static final KeyBinding keySort = new KeyBinding("sortchest.sort").setDefault(InputDevice.keyboard, Keyboard.KEY_S);

	@Unique
	private static final KeyBinding keyRefill = new KeyBinding("sortchest.refill").setDefault(InputDevice.keyboard, Keyboard.KEY_R);

	@Unique
	private static final KeyBinding keyFill = new KeyBinding("sortchest.fill").setDefault(InputDevice.keyboard, Keyboard.KEY_F);

	@Unique
	private static final KeyBinding keyDump = new KeyBinding("sortchest.dump").setDefault(InputDevice.keyboard, Keyboard.KEY_D);

	public KeyBinding bta_rootenginear_mods$getKeySort() {
		return keySort;
	}

	public KeyBinding bta_rootenginear_mods$getKeyRefill() {
		return keyRefill;
	}

	public KeyBinding bta_rootenginear_mods$getKeyFill() {
		return keyFill;
	}

	public KeyBinding bta_rootenginear_mods$getKeyDump() {
		return keyDump;
	}
}
