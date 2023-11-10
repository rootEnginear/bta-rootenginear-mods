package rootenginear.sortchest.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.Container;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.sortchest.gui.GuiSortChestButton;
import rootenginear.sortchest.mixin.accessor.GuiChestAccessor;
import rootenginear.sortchest.mixin.accessor.GuiContainerAccessor;
import rootenginear.sortchest.mixin.accessor.GuiScreenAccessor;
import rootenginear.sortchest.utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(value = {GuiContainer.class}, remap = false)
public class GuiContainerMixin {
	@Inject(method = "keyTyped", at = @At("HEAD"), cancellable = true)
	private void doSort(char c, int _i, int mouseX, int mouseY, CallbackInfo ci) {
		if (Utils.isNotChest(this)) return;

		char key = Character.toLowerCase(c);
		if (key != 's' && key != 'd' && key != 'f') return;

		GuiContainer containerThis = (GuiContainer) (Object) this;
		GuiScreenAccessor screenAccessorThis = (GuiScreenAccessor) this;

		Minecraft mc = screenAccessorThis.getMc();
		PlayerController playerController = mc.playerController;
		EntityPlayer entityPlayer = mc.thePlayer;
		Container inventorySlots = containerThis.inventorySlots;
		int windowId = inventorySlots.windowId;

		// "D" - Dump chest to inv
		if (key == 'd') {
			dumpItemFromChest(playerController, entityPlayer, windowId);

			ci.cancel();
			return;
		}

		int countInvSlots = ((GuiChestAccessor) containerThis).getInventoryRows() * 9;

		// "F" - Fill chest
		if (key == 'f') {
			dumpItemToChest(playerController, entityPlayer, windowId, countInvSlots);

			ci.cancel();
			return;
		}

		// "S" - Sort
		mergeItemsInChest(playerController, entityPlayer, windowId, countInvSlots, inventorySlots);
		sortItemsInChest(playerController, entityPlayer, windowId, countInvSlots, inventorySlots);

		ci.cancel();
	}

	@Unique
	private void sortItemsInChest(PlayerController playerController, EntityPlayer entityPlayer, int windowId, int countInvSlots, Container inventorySlots) {
		List<ItemStack> inv = new ArrayList<>(countInvSlots);
		for (int i = 0; i < countInvSlots; i++) {
			inv.add(inventorySlots.getSlot(i).getStack());
		}

		List<ItemStack> sorted = inv.stream().sorted(compareItemStacks()).collect(Collectors.toList());

		for (int i = 0; i < countInvSlots; i++) {
			ItemStack sortedItem = sorted.get(i);
			if (sortedItem == null) break;

			ItemStack invItem = inv.get(i);
			if (sortedItem == invItem) continue;

			int x = inv.indexOf(sortedItem);
			inv.set(i, inv.get(x));
			inv.set(x, invItem);

			swap(playerController, entityPlayer, windowId, x, i);
		}
	}

	@Unique
	@NotNull
	private static Comparator<ItemStack> compareItemStacks() {
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

	@Unique
	private static void swap(PlayerController playerController, EntityPlayer entityPlayer, int windowId, int x, int i) {
		playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{x, 0}, entityPlayer);
		playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{i, 0}, entityPlayer);
		playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{x, 0}, entityPlayer);
	}

	@Unique
	private void mergeItemsInChest(PlayerController playerController, EntityPlayer entityPlayer, int windowId, int countInvSlots, Container inventorySlots) {
		List<String> invData = new ArrayList<>(countInvSlots);
		for (int i = 0; i < countInvSlots; i++) {
			ItemStack item = inventorySlots.getSlot(i).getStack();
			invData.add(item != null ? item.itemID + ":" + item.getMetadata() : null);
		}

		for (int firstSlotIndex = 0; firstSlotIndex < countInvSlots; firstSlotIndex++) {
			if (invData.get(firstSlotIndex) == null) continue;

			String itemData = invData.get(firstSlotIndex);
			invData.set(firstSlotIndex, null);

			int nextSlot = invData.indexOf(itemData);
			if (nextSlot == -1) continue;

			playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{firstSlotIndex, 0}, entityPlayer);

			do {
				playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{nextSlot, 0}, entityPlayer);
				playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{nextSlot, 0}, entityPlayer);
				invData.set(nextSlot, null);
			} while ((nextSlot = invData.indexOf(itemData)) != -1);

			playerController.doInventoryAction(windowId, InventoryAction.CLICK_LEFT, new int[]{firstSlotIndex, 0}, entityPlayer);
		}
	}

	@Unique
	private void dumpItemFromChest(PlayerController playerController, EntityPlayer entityPlayer, int windowId) {
		playerController.doInventoryAction(windowId, InventoryAction.MOVE_ALL, new int[]{0, 0}, entityPlayer);
	}

	@Unique
	private void dumpItemToChest(PlayerController playerController, EntityPlayer entityPlayer, int windowId, int countInvSlots) {
		// Dump inventory (not hotbar) content
		playerController.doInventoryAction(windowId, InventoryAction.MOVE_ALL, new int[]{countInvSlots, 0}, entityPlayer);

		// Dump hotbar content
		playerController.doInventoryAction(windowId, InventoryAction.MOVE_ALL, new int[]{countInvSlots + (9 * 3), 0}, entityPlayer);
	}

	@Inject(method = "initGui", at = @At("TAIL"))
	private void addChestButtons(CallbackInfo ci) {
		I18n i18n = I18n.getInstance();
		if (Utils.isNotChest(this)) return;

		GuiContainer containerThis = (GuiContainer) (Object) this;
		GuiScreen screenThis = (GuiScreen) (Object) this;

		screenThis.controlList.clear();
		int centerX = (screenThis.width - containerThis.xSize) / 2;
		int centerY = (screenThis.height - containerThis.ySize) / 2;
		screenThis.controlList.add(new GuiSortChestButton(0, centerX + containerThis.xSize - 8 - 12 - 12 - 2, centerY + 4, 12, 12, "⇵", 3, i18n.translateKey("sortchest.sort")));
		screenThis.controlList.add(new GuiSortChestButton(1, centerX + containerThis.xSize - 8 - 12, centerY + 4, 12, 12, "⊼", 3, i18n.translateKey("sortchest.fill")));
		screenThis.controlList.add(new GuiSortChestButton(2, centerX + containerThis.xSize - 8 - 12, centerY + containerThis.ySize - 96 - 1, 12, 12, "⊻", 3, i18n.translateKey("sortchest.dump")));
	}

	@Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glEnable(I)V"))
	private void renderChestButtonTooltip(int x, int y, float renderPartialTicks, CallbackInfo ci) {
		if (Utils.isNotChest(this)) return;

		GuiScreen screenThis = (GuiScreen) (Object) this;
		for (int i = 0; i < screenThis.controlList.size(); ++i) {
			GuiSortChestButton button = (GuiSortChestButton) screenThis.controlList.get(i);
			if (!button.isHovered(x, y)) continue;
			GuiContainerAccessor containerAccessorThis = (GuiContainerAccessor) this;
			containerAccessorThis.getGuiTooltip().render(button.getTooltipText(), x, y, 8, -8);
		}
	}
}
