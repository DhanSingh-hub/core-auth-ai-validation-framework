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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Finds AI business requirements that are not referenced by any Test Solution crosswalk mapping, in any status,
 * and assigns each one an explicit review category instead of leaving it invisible to every other report.
 */
public final class AnalyzeAiOnlyRequirements {
    private static final List<String> SEGMENTS = List.of(
            "100", "101", "102", "103", "104", "105", "108", "109", "111", "113");

    private AnalyzeAiOnlyRequirements() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        Path deliveryRoot = Atl105Paths.testInput().resolve(Path.of("ai-solution", "runs", "2026-09-23"));
        Path traceability = deliveryRoot.resolve(Path.of("Run2", "traceability_matrix_full.json"));

        Set<String> referencedAiIds = new HashSet<>();
        for (String segment : SEGMENTS) {
            Path file = reviewRoot.resolve(Path.of("run2-crosswalks", "segment-" + segment + "-run2-crosswalk.json"));
            JsonNode crosswalk = mapper.readTree(file.toFile());
            for (JsonNode mapping : crosswalk.path("mappings")) {
                for (JsonNode id : mapping.path("aiRequirementIds")) referencedAiIds.add(id.asText());
            }
        }

        long totalRequirements = 0;
        Map<String, Long> unreferencedBySegment = new TreeMap<>();
        Map<String, Long> totalBySegment = new TreeMap<>();
        StringBuilder csv = new StringBuilder("requirement_id,segment,trained_segment,category,statement,source_page\n");
        long unclassifiedTrained = 0;
        long unclassifiedUntrained = 0;

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
                    totalRequirements++;
                    String requirementId = requirement.path("requirement_id").asText();
                    String segment = requirement.path("segment_id").asText("UNKNOWN").replaceFirst("^ENT-SEG-", "");
                    totalBySegment.merge(segment, 1L, Long::sum);
                    if (referencedAiIds.contains(requirementId)) continue;

                    unreferencedBySegment.merge(segment, 1L, Long::sum);
                    boolean trained = SEGMENTS.contains(segment);
                    String category = trained ? "AI_ONLY_UNCLASSIFIED_TRAINED_SEGMENT" : "AI_ONLY_UNCLASSIFIED_UNTRAINED_SEGMENT";
                    if (trained) unclassifiedTrained++; else unclassifiedUntrained++;
                    csv.append(csv(requirementId)).append(',').append(csv(segment)).append(',')
                            .append(trained).append(',').append(category).append(',')
                            .append(csv(requirement.path("statement").asText(""))).append(',')
                            .append(requirement.path("source_page").asInt(-1)).append('\n');
                }
            }
        }

        ObjectNode result = mapper.createObjectNode();
        result.put("totalAiRequirements", totalRequirements);
        result.put("referencedByAnyCrosswalkMapping", referencedAiIds.size());
        result.put("aiOnlyUnreferenced", totalRequirements - referencedAiIds.size());
        result.put("aiOnlyUnclassifiedTrainedSegment", unclassifiedTrained);
        result.put("aiOnlyUnclassifiedUntrainedSegment", unclassifiedUntrained);
        ArrayNode bySegment = result.putArray("unreferencedBySegment");
        for (Map.Entry<String, Long> entry : unreferencedBySegment.entrySet()) {
            ObjectNode row = bySegment.addObject();
            row.put("segment", entry.getKey());
            row.put("totalInSegment", totalBySegment.getOrDefault(entry.getKey(), 0L));
            row.put("unreferenced", entry.getValue());
            row.put("trainedSegment", SEGMENTS.contains(entry.getKey()));
        }

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        Path output = reviewRoot.resolve("ai-only-requirements-analysis.json");
        Files.createDirectories(output.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), result);
        Files.writeString(reviewRoot.resolve("ai-only-requirements-register.csv"), csv);
        System.out.printf("unclassifiedTrained=%d unclassifiedUntrained=%d%n", unclassifiedTrained, unclassifiedUntrained);
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"").replace("\r", " ").replace("\n", " ") + "\"";
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }
}
