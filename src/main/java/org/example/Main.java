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
        PetStoreClient client = injector.getInstance(PetStoreClient.class);

        // load file and read data
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(DataLoader.readFile());

        // parse data
        List<String> requests = org.example.JsonParser.parseJsonData(jsonElement);

        // make requests and handle responses
        for (String request : requests) {
            client.synchronousRequest(request);
//            PetStoreClient.asynchronousRequest(request);
        }
    }
}