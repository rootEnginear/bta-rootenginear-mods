package rootenginear.sortchest.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.TooltipElement;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.sortchest.gui.GuiModMenuOptionsPage;
import rootenginear.sortchest.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
@Mixin(value = {ScreenContainerAbstract.class}, remap = false)
public class ScreenContainerAbstractMixin {
	@Shadow
	public MenuAbstract inventorySlots;

	@Shadow
	public int xSize;

	@Shadow
	public int ySize;

	@Shadow
	TooltipElement tooltipElement;

	@Unique
	private void mergeItemsInChest(Minecraft mc, int countInvSlots) {
		List<String> invData = new ArrayList<>(countInvSlots);
		// List chest items
		for (int i = 0; i < countInvSlots; i++) {
			ItemStack item = this.inventorySlots.getSlot(i).getItemStack();
			invData.add(item != null ? item.itemID + ":" + item.getMetadata() : null);
		}

		for (int firstSlotIndex = 0; firstSlotIndex < countInvSlots; firstSlotIndex++) {
			if (invData.get(firstSlotIndex) == null) continue;

			String itemData = invData.get(firstSlotIndex);
			invData.set(firstSlotIndex, null);

			int nextSlot = invData.indexOf(itemData);
			if (nextSlot == -1) continue;

			mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.CLICK_LEFT, new int[]{firstSlotIndex, 0}, mc.thePlayer);

			do {
				mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.CLICK_LEFT, new int[]{nextSlot, 0}, mc.thePlayer);
				mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.CLICK_LEFT, new int[]{nextSlot, 0}, mc.thePlayer);
				invData.set(nextSlot, null);
			} while ((nextSlot = invData.indexOf(itemData)) != -1);

			mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.CLICK_LEFT, new int[]{firstSlotIndex, 0}, mc.thePlayer);
		}
	}

	@Unique
	private void sortItemsInChest(Minecraft mc, int countInvSlots) {
		List<ItemStack> inv = new ArrayList<>(countInvSlots);
		for (int i = 0; i < countInvSlots; i++) {
			inv.add(this.inventorySlots.getSlot(i).getItemStack());
		}

		List<ItemStack> sorted = inv.stream().sorted(Utils.compareItemStacks()).collect(Collectors.toList());

		for (int i = 0; i < countInvSlots; i++) {
			ItemStack sortedItem = sorted.get(i);
			if (sortedItem == null) break;

			ItemStack invItem = inv.get(i);
			if (sortedItem == invItem) continue;

			int x = inv.indexOf(sortedItem);
			inv.set(i, inv.get(x));
			inv.set(x, invItem);

			Utils.swap(mc, this.inventorySlots.containerId, x, i);
		}
	}

	@Unique
	private void refillChest(Minecraft mc, int countInvSlots) {
		List<String> chestItems = new ArrayList<>(countInvSlots);
		// List chest items
		for (int i = 0; i < countInvSlots; i++) {
			ItemStack item = this.inventorySlots.getSlot(i).getItemStack();
			if (item == null) continue;

			String itemStr = item.itemID + ":" + item.getMetadata();
			if (!chestItems.contains(itemStr)) chestItems.add(itemStr);
		}
		// Go thru inventory slots
		for (int i = countInvSlots; i < countInvSlots + (9 * 4); i++) {
			ItemStack invItem = this.inventorySlots.getSlot(i).getItemStack();
			if (invItem == null) continue;

			String invItemStr = invItem.itemID + ":" + invItem.getMetadata();
			if (!chestItems.contains(invItemStr)) continue; // If this item is not in the chest just skip

			// Found this item in the chest, try to move it into the chest
			mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.MOVE_SIMILAR, new int[]{i, 0}, mc.thePlayer);
			chestItems.remove(invItemStr);
		}
	}

	@Unique
	private void dumpItemFromChest(Minecraft mc) {
		mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.MOVE_ALL, new int[]{0, 0}, mc.thePlayer);
	}

	@Unique
	private void dumpItemToChest(Minecraft mc, int countInvSlots) {
		// Dump inventory (not hotbar) content
		mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.MOVE_ALL, new int[]{countInvSlots, 0}, mc.thePlayer);

		// Dump hotbar content
		mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.MOVE_ALL, new int[]{countInvSlots + (9 * 3), 0}, mc.thePlayer);
	}

	@Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
	private void sortOnKeyPressed(char eventCharacter, int eventKey, int mx, int my, CallbackInfo ci) {
		if (Utils.isNotChest(this)) return;

		Minecraft mc = Minecraft.getMinecraft();

		if (GuiModMenuOptionsPage.keyDump.isKeyboardKey(eventKey)) {
			dumpItemFromChest(mc);

			ci.cancel();
			return;
		}

		int countInvSlots = this.inventorySlots.slots.size() - 36;

		if (GuiModMenuOptionsPage.keySort.isKeyboardKey(eventKey)) {
			mergeItemsInChest(mc, countInvSlots);
			sortItemsInChest(mc, countInvSlots);

			ci.cancel();
			return;
		}

		if (GuiModMenuOptionsPage.keyRefill.isKeyboardKey(eventKey)) {
			refillChest(mc, countInvSlots);

			ci.cancel();
			return;
		}

		if (GuiModMenuOptionsPage.keyFill.isKeyboardKey(eventKey)) {
			dumpItemToChest(mc, countInvSlots);

			ci.cancel();
			return;
		}

		// Fall thru
	}

	@Inject(method = "init", at = @At("TAIL"))
	private void addChestButtons(CallbackInfo ci) {
		if (Utils.isNotChest(this)) return;

		Screen screenThis = (Screen) (Object) this;
		int centerX = (screenThis.width - xSize) / 2;
		int centerY = (screenThis.height - ySize) / 2;

		screenThis.buttons.clear();

		Minecraft mc = Minecraft.getMinecraft();
		int countInvSlots = this.inventorySlots.slots.size() - 36;

		ButtonElement sortButton = new ButtonElement(0, centerX + xSize - 8 - 12 - 12 - 2, centerY + 4, 12, 12, "⇵");
		sortButton.setListener((button) -> {
			mergeItemsInChest(mc, countInvSlots);
			sortItemsInChest(mc, countInvSlots);
		});

		ButtonElement refillButton = new ButtonElement(1, centerX + xSize - 8 - 12, centerY + 4, 12, 12, "∑");
		refillButton.setListener((button) -> refillChest(mc, countInvSlots));

		ButtonElement fillButton = new ButtonElement(2, centerX + xSize - 8 - 12 - 12 - 2, centerY + ySize - 96, 12, 12, "⊼");
		fillButton.setListener((button) -> dumpItemToChest(mc, countInvSlots));

		ButtonElement dumpButton = new ButtonElement(3, centerX + xSize - 8 - 12, centerY + ySize - 96, 12, 12, "⊻");
		dumpButton.setListener((button) -> dumpItemFromChest(mc));

		screenThis.buttons.add(sortButton);
		screenThis.buttons.add(refillButton);
		screenThis.buttons.add(fillButton);
		screenThis.buttons.add(dumpButton);
	}

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glEnable(I)V"))
	private void renderChestButtonsTooltip(int x, int y, float renderPartialTicks, CallbackInfo ci) {
		if (Utils.isNotChest(this)) return;

		I18n i18n = I18n.getInstance();
		Screen screenThis = (Screen) (Object) this;
		for (int i = 0; i < screenThis.buttons.size(); ++i) {
			ButtonElement button = screenThis.buttons.get(i);
			if (!button.isHovered(x, y)) continue;


			if (i == 0)
				this.tooltipElement.render(i18n.translateKey("sortchest.sort") + " [" + GuiModMenuOptionsPage.keySort.getKeyName() + "]", x, y, 8, -8);
			if (i == 1)
				this.tooltipElement.render(i18n.translateKey("sortchest.refill") + " [" + GuiModMenuOptionsPage.keyRefill.getKeyName() + "]", x, y, 8, -8);
			if (i == 2)
				this.tooltipElement.render(i18n.translateKey("sortchest.fill") + " [" + GuiModMenuOptionsPage.keyFill.getKeyName() + "]", x, y, 8, -8);
			if (i == 3)
				this.tooltipElement.render(i18n.translateKey("sortchest.dump") + " [" + GuiModMenuOptionsPage.keyDump.getKeyName() + "]", x, y, 8, -8);
		}
	}
}
