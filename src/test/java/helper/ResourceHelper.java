package helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceHelper {

    public static String generateStringFromResource(String fileName) throws IOException {
        String path = "src/test/resources/" + fileName + ".json";
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
