package rootenginear.scrubbaddie;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rootenginear.scrubbaddie.item.ItemScrubBaddie;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.ItemInitEntrypoint;
import turniplabs.halplibe.util.ModelEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class ScrubBaddie implements ModInitializer, RecipeEntrypoint, ItemInitEntrypoint, ModelEntrypoint {
	public static final String MOD_ID = "scrubbaddie";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ItemScrubBaddie scrubBaddie;

	@Override
	public void onInitialize() {
		LOGGER.info("Scrub Baddie initialized.");
	}

	@Override
	public void onRecipesReady() {
		RecipeBuilder.Shaped(MOD_ID)
			.setShape("C", "S")
			.addInput('C', Items.CLOTH)
			.addInput('S', Blocks.SPONGE_WET)
			.create("scrub_baddie", new ItemStack(scrubBaddie, 1));
		RecipeBuilder.Shaped(MOD_ID)
			.setShape("C", "S")
			.addInput('C', Items.CLOTH)
			.addInput('S', Blocks.SPONGE_DRY)
			.create("scrub_baddie", new ItemStack(scrubBaddie, 1, scrubBaddie.getMaxDamage()));
	}

	@Override
	public void initNamespaces() {
	}

	@Override
	public void afterItemInit() {
		scrubBaddie = new ItemBuilder(MOD_ID).build(new ItemScrubBaddie("scrub_baddie", MOD_ID + ":item/scrub_baddie", 29999));
	}

	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {
	}

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {
		ModelHelper.setItemModel(scrubBaddie, () -> {
			ItemModelStandard model = new ItemModelStandard(scrubBaddie, MOD_ID);
			model.icon = TextureRegistry.getTexture(NamespaceID.getTemp(MOD_ID, "item/scrub_baddie"));
			return model;
		});
	}

	@Override
	public void initEntityModels(EntityRenderDispatcher dispatcher) {
	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {
	}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {
	}
}
