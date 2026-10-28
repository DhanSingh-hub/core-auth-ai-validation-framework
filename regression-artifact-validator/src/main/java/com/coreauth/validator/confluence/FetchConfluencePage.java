package com.coreauth.validator.confluence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;

public final class FetchConfluencePage {
    private static final String DEFAULT_BASE_URL = "https://enterprise-confluence.onefiserv.net/";
    private static final String DEFAULT_PAGE_ID = "117474046";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private FetchConfluencePage() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: FetchConfluencePage <snapshot-output-directory>");
        }
        String token = System.getenv("CONFLUENCE_PAT");
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("Set CONFLUENCE_PAT in the environment before running the fetcher");
        }

        URI baseUri = URI.create(environmentOrDefault("CONFLUENCE_BASE_URL", DEFAULT_BASE_URL));
        String pageId = environmentOrDefault("CONFLUENCE_PAGE_ID", DEFAULT_PAGE_ID);
        JsonNode page = new ConfluencePageClient(baseUri).fetchPage(pageId, token);
        String body = page.path("body").path("storage").path("value").asText();
        ObjectNode snapshot = MAPPER.createObjectNode();
        snapshot.put("sourceUrl", baseUri.resolve("rest/api/content/" + pageId
                + "?expand=body.storage,version").toString());
        snapshot.put("retrievedAtUtc", Instant.now().toString());
        snapshot.put("contentSha256", sha256(body));
        snapshot.set("page", page);

        int version = page.path("version").path("number").asInt();
        Path outputDirectory = Path.of(args[0]);
        Files.createDirectories(outputDirectory);
        Path outputFile = outputDirectory.resolve("BUYPASS-Development-JP-page-" + pageId
                + "-v" + version + ".json");
        Files.writeString(outputFile, MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(snapshot),
                StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
        System.out.println("Saved Confluence page snapshot: " + outputFile.toAbsolutePath());
    }

    private static String environmentOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    private static String sha256(String value) throws Exception {
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(digest);
    }
}