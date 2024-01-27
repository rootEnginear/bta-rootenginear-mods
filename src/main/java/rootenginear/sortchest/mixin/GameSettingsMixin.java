package rootenginear.sortchest.mixin;

import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import rootenginear.sortchest.interfaces.ISortChestSettings;

@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin implements ISortChestSettings {

	public KeyBinding keyRefill = new KeyBinding("sortchest.refill").bindKeyboard(0x13); //R
	public KeyBinding keyFill = new KeyBinding("sortchest.fill").bindKeyboard(0x21); //F
	public KeyBinding keyDump = new KeyBinding("sortchest.dump").bindKeyboard(0x20); //D
	public KeyBinding keySort = new KeyBinding("sortchest.sort").bindKeyboard( 0x1F); //S

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
