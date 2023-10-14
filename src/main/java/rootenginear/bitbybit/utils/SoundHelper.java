/**
 * SoundHelper by @UselessBullets/PrismaticLibe
 *
 * @see https://github.com/UselessBullets/BTA_Babric_PrismaticLibe/blob/2.X.X/src/main/java/useless/prismaticlibe/helper/SoundHelper.java
 */
package rootenginear.bitbybit.utils;

import net.minecraft.client.Minecraft;
import rootenginear.bitbybit.BitByBit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Hashtable;

public class SoundHelper {
    private static final Hashtable<String, String> fileCache = new Hashtable<>();
    public static final File appDirectory = Minecraft.getAppDir("minecraft-bta");
    public static final File soundDirectory = new File(appDirectory.getAbsolutePath() + "/resources/mod/sound");

    public static void addSound(String MOD_ID, String soundSource) {
        String destination = soundDirectory + ("/" + MOD_ID + "/").replace("//", "/");
        String source = ("/assets/" + MOD_ID + "/sound/" + soundSource).replace("//", "/").trim();
        BitByBit.LOGGER.info("File source: " + source);
        BitByBit.LOGGER.info("File destination: " + destination);

        BitByBit.LOGGER.info(extract(source, destination, soundSource) + " Added to sound directory");
    }

    private static String extract(String jarFilePath, String destination, String soundSource) {
        if (jarFilePath == null) return null;

        // See if we already have the file
        if (fileCache.contains(jarFilePath)) return fileCache.get(jarFilePath);

        // Alright, we don't have the file, let's extract it
        try {
            // Read the file we're looking for
            InputStream fileStream = SoundHelper.class.getResourceAsStream(jarFilePath);

            // Was the resource found?
            if (fileStream == null) return null;

            File tempFile = new File(new File(destination), soundSource);
            tempFile.getParentFile().mkdirs();
            tempFile.delete();
            Files.createFile(tempFile.toPath());

            // Set this file to be deleted on VM exit
            tempFile.deleteOnExit();

            // Create an output stream to barf to the temp file
            OutputStream out = Files.newOutputStream(tempFile.toPath());

            // Write the file to the temp file
            byte[] buffer = new byte[1024];
            int len = fileStream.read(buffer);
            while (len != -1) {
                out.write(buffer, 0, len);
                len = fileStream.read(buffer);
            }

            // Store this file in the cache list
            fileCache.put(jarFilePath, tempFile.getAbsolutePath());

            // Close the streams
            fileStream.close();
            out.close();

            // Return the path of this sweet new file
            return tempFile.getAbsolutePath();

        } catch (IOException e) {
            BitByBit.LOGGER.warn(e.toString());
            for (StackTraceElement element : e.getStackTrace()) {
                BitByBit.LOGGER.debug(element.toString());
            }
            return null;
        }
    }
}
