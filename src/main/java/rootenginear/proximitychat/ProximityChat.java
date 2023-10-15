package rootenginear.proximitychat;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.core.net.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ProximityChat implements DedicatedServerModInitializer {
	public static final String MOD_ID = "proximitychat";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int DEFAULT_RADIUS;
	public static int MAX_RADIUS;

	@Override
	public void onInitializeServer() {
		PropertyManager propertyManagerObj = new PropertyManager(new File("server.properties"));

		DEFAULT_RADIUS = propertyManagerObj.getIntProperty("default-proximity-radius", 50);
		MAX_RADIUS = propertyManagerObj.getIntProperty("max-proximity-radius", 50);

		if (DEFAULT_RADIUS > MAX_RADIUS) {
			MAX_RADIUS = DEFAULT_RADIUS;
			propertyManagerObj.setProperty("max-proximity-radius", DEFAULT_RADIUS);
		}

		LOGGER.info("Proximity Chat initialized.");
	}
}
