package org.example;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/** Square OkHttp MockWebServer: https://github.com/square/okhttp/tree/master/mockwebserver */
class PetStoreClientTest {

    private PetStoreClient client;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() {
        this.mockWebServer = new MockWebServer();
    }

    @AfterEach
    void finish() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void shouldReturnDefaultQuoteOnFailure() throws IOException, InterruptedException {
        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("")
                .setResponseCode(500));
        mockWebServer.start();

        HttpUrl baseUrl = mockWebServer.url("/v2/pet");
        this.client = new PetStoreClient(baseUrl.toString());
        client.synchronousRequest("");
//        assertEquals("Lorem ipsum dolor sit amet.", result);
    }

    @Test
    void shouldReturnRandomQuoteOnSuccessfulResponse() throws IOException, InterruptedException {
        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("")
                .setResponseCode(200));

        HttpUrl baseUrl = mockWebServer.url("/v2/pet");
        this.client = new PetStoreClient(baseUrl.toString());
        client.synchronousRequest("");
//        assertEquals("Vision without action is daydream. Action without vision is nightmare..", result);
    }
}