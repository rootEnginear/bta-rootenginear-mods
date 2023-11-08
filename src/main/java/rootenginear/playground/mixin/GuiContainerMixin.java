package rootenginear.playground.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChest;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.playground.Playground;
import rootenginear.playground.mixin.accessor.GuiChestAccessor;
import rootenginear.playground.mixin.accessor.GuiScreenAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Mixin(value = {GuiContainer.class}, remap = false)
public class GuiContainerMixin {
	@Inject(method = "keyTyped", at = @At("HEAD"), cancellable = true)
	private void doSort(char c, int _i, int mouseX, int mouseY, CallbackInfo ci) {
		GuiContainer containerThis = (GuiContainer) (Object) this;

		// S - Sort
		if (c == 's' && containerThis instanceof GuiChest) {
			mergeItems(containerThis);
			sortItems(containerThis);

			ci.cancel();
		}

		// D - Disorganize (Shuffle)
		if (c == 'd' && containerThis instanceof GuiChest) {
			GuiScreenAccessor screenThis = (GuiScreenAccessor) containerThis;
			Minecraft mc = screenThis.getMc();
			PlayerController playerController = mc.playerController;
			EntityPlayer entityPlayer = mc.thePlayer;
			Container inventorySlots = containerThis.inventorySlots;
			int windowId = inventorySlots.windowId;
			int countInvSlots = ((GuiChestAccessor) containerThis).getInventoryRows() * 9;

			Random rand = new Random();

			playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{0, 0}, entityPlayer);
			for (int i = 0; i < 100; i++) {
				if (rand.nextInt(2) == 0) {
					playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{rand.nextInt(countInvSlots - 1) + 1, 0}, entityPlayer);
				} else {
					playerController.doInventoryAction(windowId, InventoryAction.CLICK_RIGHT, new int[]{rand.nextInt(countInvSlots - 1) + 1, 0}, entityPlayer);
				}
			}
			playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{0, 0}, entityPlayer);

			ci.cancel();
		}

		// M - Merge
		if (c == 'm' && containerThis instanceof GuiChest) {
			mergeItems(containerThis);

			ci.cancel();
		}
	}

	@Unique
	private void sortItems(GuiContainer containerThis) {
		GuiScreenAccessor screenThis = (GuiScreenAccessor) containerThis;
		Minecraft mc = screenThis.getMc();
		PlayerController playerController = mc.playerController;
		EntityPlayer entityPlayer = mc.thePlayer;
		Container inventorySlots = containerThis.inventorySlots;
		int windowId = inventorySlots.windowId;
		int countInvSlots = ((GuiChestAccessor) containerThis).getInventoryRows() * 9;

		List<ItemStack> inv = new ArrayList<>(countInvSlots);
		for (int i = 0; i < countInvSlots; i++) {
			inv.add(inventorySlots.getSlot(i).getStack());
		}

		List<ItemStack> sorted = inv.stream().sorted((a, z) -> {
			if (a == null && z == null) return 0;
			if (a == null) return 1;
			if (z == null) return -1;

			int aId = a.itemID;
			int zId = z.itemID;
			if (aId != zId) return aId - zId;

			int aMeta = a.getMetadata();
			int zMeta = z.getMetadata();
			Playground.LOGGER.info("Comparing " + a + " to " + z);
			Playground.LOGGER.info("A meta: " + aMeta + ", Z meta: " + zMeta);
			if (aMeta != zMeta) return aMeta - zMeta;

			Playground.LOGGER.info("Comparing " + a + " to " + z);
			Playground.LOGGER.info("A size: " + a.stackSize + ", Z size: " + z.stackSize);
			return z.stackSize - a.stackSize;
		}).collect(Collectors.toList());

		for (int i = 0; i < countInvSlots; i++) {
			ItemStack sortedItem = sorted.get(i);
			if (sortedItem == null) break;

			Playground.LOGGER.info(String.valueOf(sortedItem.stackSize));
		}
		for (int i = 0; i < countInvSlots; i++) {
			ItemStack sortedItem = sorted.get(i);
			if (sortedItem == null) break;

			ItemStack invItem = inv.get(i);
			if (sortedItem == invItem) continue;

			int x = inv.indexOf(sortedItem);
			inv.set(i, inv.get(x));
			inv.set(x, invItem);

			playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{x, 0}, entityPlayer);
			playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{i, 0}, entityPlayer);
			playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{x, 0}, entityPlayer);
		}
	}

	@Unique
	private void mergeItems(GuiContainer containerThis) {
		GuiScreenAccessor screenThis = (GuiScreenAccessor) containerThis;
		Minecraft mc = screenThis.getMc();
		PlayerController playerController = mc.playerController;
		EntityPlayer entityPlayer = mc.thePlayer;
		Container inventorySlots = containerThis.inventorySlots;
		int windowId = inventorySlots.windowId;
		int countInvSlots = ((GuiChestAccessor) containerThis).getInventoryRows() * 9;

		List<Integer> inv = new ArrayList<>(countInvSlots);
		for (int i = 0; i < countInvSlots; i++) {
			ItemStack item = inventorySlots.getSlot(i).getStack();
			inv.add(item != null ? item.itemID : null);
		}

		for (int firstSlotIndex = 0; firstSlotIndex < countInvSlots; firstSlotIndex++) {
			if (inv.get(firstSlotIndex) == null) continue;

			int itemId = inv.get(firstSlotIndex);
			playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{firstSlotIndex, 0}, entityPlayer);
			inv.set(firstSlotIndex, null);

			int nextSlot;
			while ((nextSlot = inv.indexOf(itemId)) != -1) {
				playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{nextSlot, 0}, entityPlayer);
				playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{nextSlot, 0}, entityPlayer);
				inv.set(nextSlot, null);
			}

			playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{firstSlotIndex, 0}, entityPlayer);
		}
	}
}
