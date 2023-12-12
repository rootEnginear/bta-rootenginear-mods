package rootenginear.livemap;

import net.minecraft.core.world.chunk.Chunk;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChunkProcessor {
	public static void readAndDumpChunkData(int chunkX, int chunkZ, Chunk chunk) throws IOException {
		try (FileOutputStream chunkData = new FileOutputStream(String.format("livemap/chunks/%d.%d", chunkX, chunkZ))) {
			for (int shiftZ = 0; shiftZ < 16; shiftZ++) {
				for (int shiftX = 0; shiftX < 16; shiftX++) {
					short blockData = 0;
					for (int y = chunk.getHeightValue(shiftX, shiftZ); y > -1; y--) {
						int blockId = chunk.getBlockID(shiftX, y, shiftZ);
						if (blockId != 0) {
							int metadata = chunk.getBlockMetadata(shiftX, y, shiftZ);
							blockData = (short) ((blockId & 0x3FF) | ((metadata & 0xF) << 10));
							break;
						}
					}
					chunkData.write(blockData & 0xFF);
					chunkData.write((blockData >> 8) & 0xFF);
				}
			}
		}
	}

	public static void updateChunkFile() throws IOException {
		try (DirectoryStream<Path> dp = Files.newDirectoryStream(Paths.get("livemap", "chunks"))) {
			StringBuilder chunkList = new StringBuilder("[");
			boolean firstPassed = false;
			for (Path path : dp) {
				if (firstPassed) {
					chunkList.append(",");
				} else {
					firstPassed = true;
				}
				chunkList.append("\"").append(path.getFileName()).append("\"");
			}
			chunkList.append("]");
			try (FileWriter chunkData = new FileWriter("livemap/chunks.json")) {
				chunkData.write(chunkList.toString());
			}
		}
	}
}
