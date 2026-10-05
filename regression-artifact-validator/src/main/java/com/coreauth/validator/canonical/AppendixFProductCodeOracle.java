package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class AppendixFProductCodeOracle {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Map<String, JsonNode> rows;
    private final JsonNode conflict;

    public AppendixFProductCodeOracle() throws IOException {
        try (var stream = AppendixFProductCodeOracle.class.getResourceAsStream("/atl105/appendix-f-product-code-oracle.json")) {
            if (stream == null) throw new IOException("Missing Appendix F source oracle");
            JsonNode source = MAPPER.readTree(stream);
            Map<String, JsonNode> indexed = new HashMap<>();
            for (JsonNode row : source.path("rows")) {
                int start = Integer.parseInt(row.path("startCode").asText());
                int end = Integer.parseInt(row.path("endCode").asText());
                if (start < 0 || end > 999 || end < start) throw new IOException("Invalid Appendix F source range");
                for (int value = start; value <= end; value++) {
                    String code = String.format(Locale.ROOT, "%03d", value);
                    if (indexed.put(code, row) != null) throw new IOException("Overlapping Appendix F code " + code);
                }
            }
            if (indexed.size() != 1000) throw new IOException("Incomplete Appendix F source oracle");
            rows = Map.copyOf(indexed);
            conflict = source.path("sourceConflict");
        }
    }

    public ObjectNode evaluate(JsonNode payload) {
        ObjectNode result = MAPPER.createObjectNode();
        result.put("outcome", "FAIL");
        result.put("transactionEligibility", "NOT_EVALUATED");
        if (payload == null || !payload.isObject()
                || !"TRANSACTION_PRODUCT_CODE".equals(payload.path("messageRole").asText())) {
            return result.put("reason", "Only transaction Product Code classification/format is supported; Prompt 904 patterns are outside this scope");
        }
        String mode = payload.path("mode").asText();
        if (!"FORMAT".equals(mode) && !"CLASSIFICATION".equals(mode)) {
            return result.put("reason", "Unsupported assertion mode");
        }
        JsonNode input = payload.path("ProductCode");
        if (!input.isTextual() || !input.textValue().matches("[0-9]{3}")) {
            return result.put("reason", "ProductCode must be a string of exactly three ASCII digits");
        }
        result.put("outcome", "PASS");
        if ("FORMAT".equals(mode)) return result;
        JsonNode row = rows.get(input.textValue());
        for (String field : new String[]{"table", "assignment", "role", "description", "sourceRange", "sourceAnchor"}) {
            result.set(field, row.path(field).deepCopy());
        }
        boolean disputed = false;
        for (JsonNode code : conflict.path("codes")) {
            if (input.textValue().equals(code.asText())) disputed = true;
        }
        result.put("sourceConflict", disputed);
        if (disputed) {
            result.put("outcome", "REVIEW_REQUIRED");
            result.set("conflictingClaims", conflict.deepCopy());
        }
        return result;
    }
}