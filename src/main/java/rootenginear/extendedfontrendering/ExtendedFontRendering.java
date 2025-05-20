package rootenginear.extendedfontrendering;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtendedFontRendering implements ModInitializer {
	public static final String MOD_ID = "extendedfontrendering";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Collect all const that should be configurable in the future
	public static final String FEATURE_PATH = "/font/glyph_0E_features.json";

	@Override
	public void onInitialize() {
		LOGGER.info("ExtendedFontRendering initialized.");
	}
}
