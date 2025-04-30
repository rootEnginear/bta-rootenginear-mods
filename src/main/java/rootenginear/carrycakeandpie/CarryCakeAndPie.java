package rootenginear.carrycakeandpie;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.entity.TileEntityDispatcher;
import net.minecraft.core.util.collection.NamespaceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rootenginear.carrycakeandpie.entity.TileEntityCake;
import rootenginear.carrycakeandpie.entity.TileEntityPiePumpkin;

public class CarryCakeAndPie implements ModInitializer {
	public static final String MOD_ID = "carrycakeandpie";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		TileEntityDispatcher.addMapping(TileEntityCake.class, NamespaceID.getPermanent("minecraft", "cake"));
		TileEntityDispatcher.addMapping(TileEntityPiePumpkin.class, NamespaceID.getPermanent("minecraft", "pumpkin_pie"));

		LOGGER.info("Carry Cake and Pie initialized.");
	}
}
