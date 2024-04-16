package rootenginear.craftablechainmailarmor.recipe;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class ChainmailRecipes implements RecipeEntrypoint {
	public final RecipeNamespace CHAINMAILMOD = new RecipeNamespace();
	public final RecipeGroup<RecipeEntryCrafting<?, ?>> WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.workbench)));

	@Override
	public void onRecipesReady() {
		CHAINMAILMOD.register("workbench", WORKBENCH);
		Registries.RECIPES.register("chainmail", this.CHAINMAILMOD);
		DataLoader.loadRecipesFromFile("/assets/craftablechainmailarmor/recipe/workbench.json");
	}
}
