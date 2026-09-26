package com.coreauth.validator.coverage;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Streams Run2's traceability_matrix_full.json once to index each test case's AI element-name vocabulary. */
public final class AiElementNamesLoader {
    private final ObjectMapper mapper = new ObjectMapper();

    public Map<String, List<String>> load(Path traceabilityFile) throws IOException {
        Map<String, List<String>> elementNamesByTestCase = new LinkedHashMap<>();
        try (JsonParser parser = mapper.getFactory().createParser(traceabilityFile.toFile())) {
            require(parser.nextToken() == JsonToken.START_OBJECT, "root must be an object");
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String field = parser.currentName();
                parser.nextToken();
                if (!"requirements".equals(field)) {
                    parser.skipChildren();
                    continue;
                }
                require(parser.currentToken() == JsonToken.START_ARRAY, "requirements must be an array");
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    JsonNode requirement = mapper.readTree(parser);
                    for (JsonNode scenario : requirement.path("scenarios")) {
                        for (JsonNode testCase : scenario.path("test_cases")) {
                            String testCaseId = testCase.path("test_case_id").asText(null);
                            if (testCaseId == null) continue;
                            List<String> names = new ArrayList<>();
                            for (JsonNode keyValue : testCase.path("test_data").path("key_values")) {
                                String element = keyValue.path("element").asText(null);
                                if (element != null) names.add(element);
                            }
                            if (!names.isEmpty()) elementNamesByTestCase.put(testCaseId, names);
                        }
                    }
                }
            }
        }
        return elementNamesByTestCase;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }
}
