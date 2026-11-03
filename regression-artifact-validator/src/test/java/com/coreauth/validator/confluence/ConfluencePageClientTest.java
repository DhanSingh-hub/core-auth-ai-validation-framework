package com.coreauth.validator.confluence;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfluencePageClientTest {
    private HttpServer server;
    private URI baseUri;

    @BeforeEach
    void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.start();
        baseUri = URI.create("http://127.0.0.1:" + server.getAddress().getPort() + "/");
    }

    @AfterEach
    void stopServer() {
        server.stop(0);
    }

    @Test
    void fetchesPageUsingBearerTokenAndStorageExpansion() throws Exception {
        AtomicReference<String> authorization = new AtomicReference<>();
        AtomicReference<String> requestPath = new AtomicReference<>();
        server.createContext("/rest/api/content/117474046", exchange -> {
            authorization.set(exchange.getRequestHeaders().getFirst("Authorization"));
            requestPath.set(exchange.getRequestURI().toString());
            respond(exchange, 200, "{\"id\":\"117474046\",\"title\":\"BUYPASS Development JP\","
                    + "\"version\":{\"number\":7},\"body\":{\"storage\":{\"value\":\"<p>Rule</p>\"}}}");
        });

        JsonNode page = new ConfluencePageClient(baseUri).fetchPage("117474046", "test-token");

        assertThat(authorization.get()).isEqualTo("Bearer test-token");
        assertThat(requestPath.get()).isEqualTo("/rest/api/content/117474046?expand=body.storage,version");
        assertThat(page.path("version").path("number").asInt()).isEqualTo(7);
        assertThat(page.path("body").path("storage").path("value").asText()).isEqualTo("<p>Rule</p>");
    }

    @Test
    void reportsHttpStatusWithoutIncludingResponseBody() {
        server.createContext("/rest/api/content/117474046", exchange ->
                respond(exchange, 401, "private error detail"));

        assertThatThrownBy(() -> new ConfluencePageClient(baseUri).fetchPage("117474046", "test-token"))
                .isInstanceOf(IOException.class)
                .hasMessage("Confluence API returned HTTP 401")
                .hasMessageNotContaining("private error detail");
    }

    @Test
    void rejectsInvalidPageIdBeforeMakingRequest() {
        assertThatThrownBy(() -> new ConfluencePageClient(baseUri).fetchPage("../content", "test-token"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("page ID");
    }

    @Test
    void rejectsNonLocalHttpBaseUrl() {
        assertThatThrownBy(() -> new ConfluencePageClient(URI.create("http://confluence.example.com")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Confluence base URL must use HTTPS");
    }

    private static void respond(com.sun.net.httpserver.HttpExchange exchange, int status, String body)
            throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (var output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }
}