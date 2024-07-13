package rootenginear.sortchest.mixin;

import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.input.InputDevice;
import org.spongepowered.asm.mixin.Mixin;
import rootenginear.sortchest.interfaces.ISortChestSettings;

@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin implements ISortChestSettings {
	
	public KeyBinding keyRefill = new KeyBinding("sortchest.refill").setDefault(InputDevice.keyboard, 0x13); //R
	public KeyBinding keyFill = new KeyBinding("sortchest.fill").setDefault(InputDevice.keyboard, 0x21); //F
	public KeyBinding keyDump = new KeyBinding("sortchest.dump").setDefault(InputDevice.keyboard, 0x20); //D
	public KeyBinding keySort = new KeyBinding("sortchest.sort").setDefault(InputDevice.keyboard, 0x1F); //S

	@Override
	public KeyBinding bta_rootenginear_mods$getKeySort() {
		return this.keySort;
	}

	@Override
	public KeyBinding bta_rootenginear_mods$getKeyRefill() {
		return this.keyRefill;
	}

	@Override
	public KeyBinding bta_rootenginear_mods$getKeyFill() {
		return this.keyFill;
	}

	@Override
	public KeyBinding bta_rootenginear_mods$getKeyDump() {
		return this.keyDump;
	}
}
