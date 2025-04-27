package rootenginear.sortchest.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.container.ScreenContainer;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.item.ItemStack;

import java.util.Comparator;

public class Utils {
	public static boolean isNotChest(Object object) {
		return !(object instanceof ScreenContainer);
	}

	public static Comparator<ItemStack> compareItemStacks() {
		return (a, z) -> {
			if (a == null && z == null) return 0;
			if (a == null) return 1;
			if (z == null) return -1;

			int aId = a.itemID;
			int zId = z.itemID;
			if (aId != zId) return aId - zId;

			int aMeta = a.getMetadata();
			int zMeta = z.getMetadata();
			if (aMeta != zMeta) return aMeta - zMeta;

			return z.stackSize - a.stackSize;
		};
	}

	public static void swap(Minecraft mc, int windowId, int x, int i) {
		mc.playerController.handleInventoryMouseClick(windowId, InventoryAction.CLICK_LEFT, new int[]{x, 0}, mc.thePlayer);
		mc.playerController.handleInventoryMouseClick(windowId, InventoryAction.CLICK_LEFT, new int[]{i, 0}, mc.thePlayer);
		mc.playerController.handleInventoryMouseClick(windowId, InventoryAction.CLICK_LEFT, new int[]{x, 0}, mc.thePlayer);
	}
}
