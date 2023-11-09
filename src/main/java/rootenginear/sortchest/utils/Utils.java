package rootenginear.sortchest.utils;

import net.minecraft.client.gui.GuiChest;

public class Utils {
	public static boolean isNotChest(Object object) {
		return !(object instanceof GuiChest);
	}
}
