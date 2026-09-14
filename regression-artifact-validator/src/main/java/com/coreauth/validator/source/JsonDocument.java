package com.coreauth.validator.source;

import com.fasterxml.jackson.databind.JsonNode;

/** One BUYPASS ATL105 JSON test-data document, tagged with an id for reporting. */
public final class JsonDocument {
    private final String id;
    private final JsonNode content;

    public JsonDocument(String id, JsonNode content) {
        this.id = id;
        this.content = content;
    }

    public String id() { return id; }
    public JsonNode content() { return content; }
}
