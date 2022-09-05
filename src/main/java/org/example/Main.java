package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Injector injector = Guice.createInjector(new PetStoreModule());

        // load file and read data
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(readFile());

        // parse data
        List<String> requests = parseJsonData(jsonElement);

        PetStoreClient client = injector.getInstance(PetStoreClient.class);
        // make requests and handle responses
        for (String request : requests) {
            client.synchronousRequest(request);
//            PetStoreClient.asynchronousRequest(request);
        }
    }

    /** https://github.com/mjg123/json-in-java/blob/main/src/main/java/lol/gilliard/jsonparse/SourceData.java */
    private static String readFile() {
        try {
//            Path path = Paths.get(ClassLoader.getSystemResource("source.json").toURI());
            Path path = Paths.get("/Users/lily/Downloads/source_copy.json");
            Stream<String> lines = Files.lines(path);
            String data = lines.collect(Collectors.joining());
            lines.close();
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> parseJsonData(JsonElement jsonElement) {
        List<String> petData = new ArrayList<>();
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (int i = 0; i < 10; i++) {
            petData.add(jsonArray.get(i).toString());
        }
        return petData;
    }
}