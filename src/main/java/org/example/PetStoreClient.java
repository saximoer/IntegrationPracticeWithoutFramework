package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Inject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PetStoreClient {

//    private static final String BASE_URL = "https://petstore.swagger.io/v2/pet";
    private static final String DEFAULT_RESPONSE = "default response";

    private final String baseUrl;

    @Inject
    public PetStoreClient(@PetStoreModule.PetStoreBaseUrl String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void synchronousRequest(String requestBody) throws IOException, InterruptedException {
        // create client
        HttpClient client = HttpClient.newHttpClient();

        // create request
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(baseUrl))
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            // send request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println(DEFAULT_RESPONSE);
            }

            // handle response
            System.out.println(response.body());
            System.out.println(("response is " + (checkResponse(response.body(), requestBody) ? "expected" : "unexpected")));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void asynchronousRequest(String requestBody) throws InterruptedException, ExecutionException {
        // create client
        HttpClient client = HttpClient.newHttpClient();

        // create request
        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY"))
                .header("accept", "application/json")
                .build();

        // send request
        CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(
                request, HttpResponse.BodyHandlers.ofString());

        // do other things here while the request is in-flight

        // This blocks until the request is complete
        HttpResponse<String> response = responseFuture.get();

        // handle response
        System.out.println(response.body());
    }

    private boolean checkResponse(String response, String expectedResponse) {
        JsonElement responseJE = new JsonParser().parse(response);
        JsonElement expectedResponseJE = new JsonParser().parse(expectedResponse);
        return responseJE.equals(expectedResponseJE);
    }
}
