package org.example;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class PetStoreClientTest {

    private MockWebServer mockWebServer;
    private PetStoreClient client;

    private static String VALID_RESPONSE;

    static {
        VALID_RESPONSE = "";
    }

    @BeforeEach
    void init() {
        this.mockWebServer = new MockWebServer();
        this.client = new PetStoreClient(mockWebServer.url("/").toString());
    }

    @Test
    void shouldReturnDefaultQuoteOnFailure() throws IOException, InterruptedException {
        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setResponseCode(500));

        client.synchronousRequest("");
//        assertEquals("Lorem ipsum dolor sit amet.", result);
    }

    @Test
    void shouldReturnRandomQuoteOnSuccessfulResponse() throws IOException, InterruptedException {
        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody(VALID_RESPONSE)
                .setResponseCode(200));

        client.synchronousRequest("");
//        assertEquals("Vision without action is daydream. Action without vision is nightmare..", result);
    }
}