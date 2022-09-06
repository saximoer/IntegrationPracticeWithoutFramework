package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://github.com/mjg123/json-in-java/blob/main/src/main/java/lol/gilliard/jsonparse/SourceData.java
 */
public class DataLoader {

    private static final String FILE_PATH = "/Users/lily/Downloads/source_copy.json";

    public static String readFile() {
        try {
//            Path path = Paths.get(ClassLoader.getSystemResource("source.json").toURI());
            Path path = Paths.get(FILE_PATH);
            Stream<String> lines = Files.lines(path);
            String data = lines.collect(Collectors.joining());
            lines.close();
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
