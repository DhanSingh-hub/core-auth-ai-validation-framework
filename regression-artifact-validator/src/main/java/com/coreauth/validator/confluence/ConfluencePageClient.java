package com.coreauth.validator.confluence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public final class ConfluencePageClient {
    private final URI baseUri;
    private final HttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public ConfluencePageClient(URI baseUri) {
        this(baseUri, HttpClient.newHttpClient());
    }

    ConfluencePageClient(URI baseUri, HttpClient httpClient) {
        this.baseUri = normalizeBaseUri(baseUri);
        this.httpClient = httpClient;
    }

    public JsonNode fetchPage(String pageId, String personalAccessToken) throws IOException, InterruptedException {
        if (pageId == null || !pageId.matches("\\d+")) {
            throw new IllegalArgumentException("Confluence page ID must contain only digits");
        }
        if (personalAccessToken == null || personalAccessToken.isBlank()) {
            throw new IllegalArgumentException("Confluence personal access token is required");
        }

        URI pageUri = baseUri.resolve("rest/api/content/" + pageId + "?expand=body.storage,version");
        HttpRequest request = HttpRequest.newBuilder(pageUri)
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + personalAccessToken)
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString(java.nio.charset.StandardCharsets.UTF_8));
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Confluence API returned HTTP " + response.statusCode());
        }

        JsonNode page = mapper.readTree(response.body());
        if (page.path("id").asText().isBlank()
                || page.path("version").path("number").isMissingNode()
                || page.path("body").path("storage").path("value").isMissingNode()) {
            throw new IOException("Confluence API response is missing page ID, version, or storage body");
        }
        return page;
    }

    private static URI normalizeBaseUri(URI uri) {
        if (uri == null || uri.getHost() == null
                || !("https".equalsIgnoreCase(uri.getScheme()) || "http".equalsIgnoreCase(uri.getScheme()))) {
            throw new IllegalArgumentException("Confluence base URL must be an absolute HTTP(S) URL");
        }
        boolean localHttp = "http".equalsIgnoreCase(uri.getScheme())
                && ("localhost".equalsIgnoreCase(uri.getHost()) || "127.0.0.1".equals(uri.getHost()));
        if ("http".equalsIgnoreCase(uri.getScheme()) && !localHttp) {
            throw new IllegalArgumentException("Confluence base URL must use HTTPS");
        }
        String value = uri.toString();
        return URI.create(value.endsWith("/") ? value : value + "/");
    }
}