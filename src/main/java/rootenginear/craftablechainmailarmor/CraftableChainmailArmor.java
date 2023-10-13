package rootenginear.craftablechainmailarmor;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.crafting.CraftingManager;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CraftableChainmailArmor implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("craftablechainmailarmor");

	static {
		// Prevent Java compiler to remove unused import
		Block $ = Block.blockOlivine;
	}

	public static final CraftingManager craftingManager = CraftingManager.getInstance();

    @Override
    public void onInitialize() {
		craftingManager.addRecipe(new ItemStack(Item.armorHelmetChainmail, 1, 217), "AAA",
			"A A",
			'A', Item.chainlink);
		craftingManager.addRecipe(new ItemStack(Item.armorChestplateChainmail, 1, 239), "A A",
			"AAA",
			"AAA",
			'A', Item.chainlink);
		craftingManager.addRecipe(new ItemStack(Item.armorLeggingsChainmail, 1, 231), "AAA",
			"A A",
			"A A",
			'A', Item.chainlink);
		craftingManager.addRecipe(new ItemStack(Item.armorBootsChainmail, 1, 224), "A A",
			"A A",
			'A', Item.chainlink);

		LOGGER.info("Craftable Chainmail Armor initialized.");
    }
}
