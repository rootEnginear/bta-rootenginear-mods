package rootenginear.bitbybit;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rootenginear.bitbybit.utils.SoundHelper;

public class BitByBit implements ModInitializer {
    public static final String MOD_ID = "bitbybit";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        SoundHelper.addSound(MOD_ID, "eat1.ogg");
        SoundHelper.addSound(MOD_ID, "eat2.ogg");
        SoundHelper.addSound(MOD_ID, "eat3.ogg");

        LOGGER.info("Bit by Bit initialized.");
    }
}
