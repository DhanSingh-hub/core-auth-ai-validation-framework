package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

/**
 * Extracts the AI's own per-field vocabulary per segment, as evidence for field-alias crosswalks.
 * Prefers test_data.key_values[].element (explicit AI field labels); falls back to the resolvable
 * qe_shaped_test_data payload's own flattened leaf JSON key names when a test case has no key_values,
 * so every segment with any resolvable AI test data gets real, observed evidence, not a guess.
 */
public final class GenerateAiElementNameCatalog {

    private GenerateAiElementNameCatalog() { }

    public static void main(String[] args) throws Exception {
        Path runRoot = args.length > 1 ? Path.of(args[1])
                : Atl105Paths.testInput().resolve(Path.of("ai-solution", "runs", "2026-09-23", "Run2"));
        Path traceability = args.length > 0 ? Path.of(args[0]) : runRoot.resolve("traceability_matrix_full.json");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Map<String, Long>> keyValuesFrequencyBySegment = new TreeMap<>();
        Map<String, Map<String, Long>> rawPayloadFrequencyBySegment = new TreeMap<>();
        long testCasesWithKeyValues = 0;
        long testCasesWithRawPayloadFallback = 0;

        try (JsonParser parser = mapper.getFactory().createParser(traceability.toFile())) {
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
                    String requirementSegment = requirement.path("segment_id").asText("UNKNOWN")
                            .replaceFirst("^ENT-SEG-", "");
                    for (JsonNode scenario : requirement.path("scenarios")) {
                        for (JsonNode testCase : scenario.path("test_cases")) {
                            JsonNode testData = testCase.path("test_data");
                            JsonNode keyValues = testData.path("key_values");
                            if (keyValues.isArray() && !keyValues.isEmpty()) {
                                testCasesWithKeyValues++;
                                for (JsonNode keyValue : keyValues) {
                                    String segment = keyValue.path("segment").asText(requirementSegment);
                                    String element = keyValue.path("element").asText(null);
                                    if (element == null) continue;
                                    keyValuesFrequencyBySegment.computeIfAbsent(segment, ignored -> new TreeMap<>())
                                            .merge(element, 1L, Long::sum);
                                }
                                continue;
                            }
                            if (!testData.path("file_exists").asBoolean(false)) continue;
                            JsonNode payload = loadPayload(runRoot, testData.path("test_data_file").asText(null), mapper);
                            if (payload == null) continue;
                            testCasesWithRawPayloadFallback++;
                            for (String leafKey : leafKeyNames(payload)) {
                                rawPayloadFrequencyBySegment.computeIfAbsent(requirementSegment, ignored -> new TreeMap<>())
                                        .merge(leafKey, 1L, Long::sum);
                            }
                        }
                    }
                }
            }
        }

        ObjectNode root = mapper.createObjectNode();
        root.put("artifact", "run2-ai-element-name-catalog");
        root.put("source", "traceability_matrix_full.json: test_data.key_values[].element where present, "
                + "else the resolvable qe_shaped_test_data payload's own flattened leaf JSON key names");
        root.put("purpose", "Ground-truth evidence of the AI's own field-naming vocabulary per segment, for building field-alias crosswalks against Test Solution rule catalogs.");
        root.put("testCasesWithKeyValues", testCasesWithKeyValues);
        root.put("testCasesWithRawPayloadFallback", testCasesWithRawPayloadFallback);
        ArrayNode segments = root.putArray("segments");
        Map<String, Map<String, Long>> combinedSegments = new TreeMap<>();
        keyValuesFrequencyBySegment.keySet().forEach(segment -> combinedSegments.putIfAbsent(segment, Map.of()));
        rawPayloadFrequencyBySegment.keySet().forEach(segment -> combinedSegments.putIfAbsent(segment, Map.of()));
        for (String segment : combinedSegments.keySet()) {
            ObjectNode segmentNode = segments.addObject();
            segmentNode.put("segment", segment);
            ArrayNode elements = segmentNode.putArray("elements");
            keyValuesFrequencyBySegment.getOrDefault(segment, Map.of()).entrySet().stream()
                    .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                    .forEach(entry -> addElement(elements, entry.getKey(), entry.getValue(), "key_values"));
            rawPayloadFrequencyBySegment.getOrDefault(segment, Map.of()).entrySet().stream()
                    .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                    .forEach(entry -> addElement(elements, entry.getKey(), entry.getValue(), "raw_payload_keys"));
        }

        Path output = Atl105Paths.testOutput().resolve(Path.of(
                "ai-solution-independent-review", "run2-ai-element-name-catalog.json"));
        Files.createDirectories(output.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), root);
        System.out.printf("segments=%d testCasesWithKeyValues=%d testCasesWithRawPayloadFallback=%d%n",
                combinedSegments.size(), testCasesWithKeyValues, testCasesWithRawPayloadFallback);
    }

    private static void addElement(ArrayNode elements, String name, long occurrences, String source) {
        ObjectNode elementNode = elements.addObject();
        elementNode.put("elementName", name);
        elementNode.put("occurrences", occurrences);
        elementNode.put("evidenceSource", source);
    }

    private static JsonNode loadPayload(Path runRoot, String fileName, ObjectMapper mapper) {
        if (fileName == null || fileName.isBlank()) return null;
        Path file = runRoot.resolve(fileName);
        if (!Files.isRegularFile(file)) return null;
        try {
            return mapper.readTree(file.toFile());
        } catch (Exception e) {
            return null;
        }
    }

    private static java.util.Set<String> leafKeyNames(JsonNode node) {
        java.util.Set<String> names = new java.util.LinkedHashSet<>();
        collectLeafKeyNames(node, names);
        return names;
    }

    private static void collectLeafKeyNames(JsonNode node, java.util.Set<String> names) {
        if (node.isObject()) {
            node.fieldNames().forEachRemaining(name -> {
                names.add(name);
                collectLeafKeyNames(node.get(name), names);
            });
        } else if (node.isArray()) {
            for (JsonNode item : node) collectLeafKeyNames(item, names);
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }
}
