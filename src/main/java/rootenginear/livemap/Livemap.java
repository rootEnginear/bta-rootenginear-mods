package rootenginear.livemap;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Livemap implements ModInitializer {
	public static final String MOD_ID = "livemap";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Livemap initialized.");
	}
}
