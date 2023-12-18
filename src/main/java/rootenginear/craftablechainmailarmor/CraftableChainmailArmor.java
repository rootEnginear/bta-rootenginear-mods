package rootenginear.craftablechainmailarmor;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CraftableChainmailArmor implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("craftablechainmailarmor");

	@Override
	public void onInitialize() {
		LOGGER.info("Craftable Chainmail Armor initialized.");
	}
}
