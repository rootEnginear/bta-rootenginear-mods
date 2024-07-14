package rootenginear.sortchest.utils;

import net.minecraft.client.gui.GuiChest;

public class Utils {
	public static boolean isNotChest(Object object) {
	    String name = object.getClass().getSimpleName();
		return !(object instanceof GuiChest) && !name.equals("ContainerWideChest") && !name.equals("GuiDiamondChest") && !name.equals("GuiIronChest");
	}
}
