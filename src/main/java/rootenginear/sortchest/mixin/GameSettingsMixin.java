package rootenginear.sortchest.mixin;

import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import rootenginear.sortchest.interfaces.ISortChestSettings;

@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin implements ISortChestSettings {
	@Unique
	public KeyBinding keySort = new KeyBinding("sortchest.sort", 0x1F);
	@Unique
	public KeyBinding keyFill = new KeyBinding("sortchest.fill", 0x21);
	@Unique
	public KeyBinding keyDump = new KeyBinding("sortchest.dump", 0x20);

	@Override
	public KeyBinding bta_rootenginear_mods$getKeySort() {
		return this.keySort;
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
