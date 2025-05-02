package rootenginear.leavesalwaysfalling;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class LeavesAlwaysFalling implements ModInitializer {
	public static final String MOD_ID = "leavesalwaysfalling";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Leaves Always Falling initialized.");
	}
}
