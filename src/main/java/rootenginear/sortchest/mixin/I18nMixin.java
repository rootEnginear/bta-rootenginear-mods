package rootenginear.sortchest.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.lang.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.sortchest.SortChest;
import rootenginear.sortchest.mixin.accessor.LanguageAccessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

@Mixin(value = I18n.class, remap = false)
public abstract class I18nMixin {
	@Shadow
	private Language currentLanguage;

	@Unique
	private static String[] filesInDir(String directory) {
		List<String> paths = new ArrayList<>();
		if (!directory.endsWith("/")) {
			directory = directory + "/";
		}

		try {
			URI uri = Objects.requireNonNull(I18n.class.getResource(directory)).toURI();
			FileSystem fileSystem = null;
			Path myPath;
			if (uri.getScheme().equals("jar")) {
				try {
					fileSystem = FileSystems.getFileSystem(uri);
				} catch (Exception var9) {
					fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
				}

				myPath = fileSystem.getPath(directory);
			} else {
				myPath = Paths.get(uri);
			}

			Stream<Path> walk = Files.walk(myPath, 1);

			try {
				Iterator<Path> it = walk.iterator();
				it.next();

				while (it.hasNext()) {
					paths.add(directory + it.next().getFileName().toString());
				}
			} catch (Throwable var10) {
				if (walk != null) {
					try {
						walk.close();
					} catch (Throwable var8) {
						var10.addSuppressed(var8);
					}
				}

				throw var10;
			}

			walk.close();

			if (fileSystem != null) {
				fileSystem.close();
			}
		} catch (Exception ignored) {

		}

		return paths.toArray(new String[0]);
	}

	@Shadow
	public static InputStream getResourceAsStream(String path) {
		return null;
	}

	@Inject(
		method = "reload(Ljava/lang/String;Z)V",
		at = @At("TAIL")
	)
	public void addHalplibeModLangFiles(String languageCode, boolean save, CallbackInfo ci) {
		Properties entries = ((LanguageAccessor) currentLanguage).getEntries();
		Language defaultLanguage = Language.Default.INSTANCE;
		Properties defaultEntries = ((LanguageAccessor) defaultLanguage).getEntries(); //if you see a ClassCastException warning here, it is wrong, nothing happens
		String defaultLangId = defaultLanguage.getId();
		String currentLangId = currentLanguage.getId();
		SortChest.LOGGER.debug("Current lang: " + currentLangId);
		for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
			String[] langs = filesInDir("/lang/" + mod.getMetadata().getId() + "/");
			SortChest.LOGGER.debug(mod.getMetadata().getId() + " contains " + langs.length + " language files.");
			SortChest.LOGGER.debug(Arrays.toString(langs));
			for (String lang : langs) {
				if (lang.contains(currentLangId)) {
					try (InputStream stream = getResourceAsStream(lang)) {
						if (stream != null) {
							InputStreamReader r = new InputStreamReader(stream, StandardCharsets.UTF_8);
							entries.load(r);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (lang.contains(defaultLangId)) {
					try (InputStream stream = getResourceAsStream(lang)) {
						if (stream != null) {
							InputStreamReader r = new InputStreamReader(stream, StandardCharsets.UTF_8);
							defaultEntries.load(r);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}