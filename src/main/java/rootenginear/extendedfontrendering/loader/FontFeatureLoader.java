package rootenginear.extendedfontrendering.loader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import rootenginear.extendedfontrendering.struct.Feature;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class FontFeatureLoader {
	public static Feature loadFeatureFromFile(String path) throws IOException {
		InputStream stream = Minecraft.getMinecraft().texturePackList.getResourceAsStream(path);
		Gson gson = new GsonBuilder().create();
		try (Reader reader = new InputStreamReader(stream)) {
			return gson.fromJson(reader, Feature.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
