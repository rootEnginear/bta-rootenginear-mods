package rootenginear.proximitychat;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.core.net.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ProximityChat implements DedicatedServerModInitializer {
	public static final String MOD_ID = "proximitychat";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int RADIUS;

	@Override
	public void onInitializeServer() {
		PropertyManager propertyManagerObj = new PropertyManager(new File("server.properties"));

		RADIUS = propertyManagerObj.getIntProperty("default-proximity-radius", 50);

		LOGGER.info("Proximity Chat initialized.");
	}
}
