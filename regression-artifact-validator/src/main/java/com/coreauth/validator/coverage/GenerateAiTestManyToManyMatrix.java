package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Builds both directions of the AI BR to independent Test Rule mapping matrix. */
public final class GenerateAiTestManyToManyMatrix {
    private GenerateAiTestManyToManyMatrix() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path outputRoot = Atl105Paths.testOutput().resolve("test-solution-independent-review");
        JsonNode crosswalk = mapper.readTree(outputRoot.resolve("atomic-br-ai-crosswalk.json").toFile());
        Map<String, ObjectNode> aiToTest = new LinkedHashMap<>();
        Map<String, ObjectNode> testToAi = new TreeMap<>();
        Map<String, Integer> statuses = new TreeMap<>();

        for (JsonNode mapping : crosswalk.path("mappings")) {
            String aiId = mapping.path("aiRequirementId").asText();
            String status = matrixStatus(mapping.path("status").asText());
            ObjectNode aiEntry = aiToTest.computeIfAbsent(aiId, ignored -> {
                ObjectNode value = mapper.createObjectNode();
                value.put("aiRequirementId", aiId);
                value.put("aiSegment", mapping.path("aiSegment").asText());
                value.put("statement", mapping.path("statement").asText());
                value.put("sourceRuleId", mapping.path("sourceRuleId").asText());
                value.put("sourcePage", mapping.path("sourcePage").asInt(-1));
                value.putArray("testRuleCandidates");
                return value;
            });
            aiEntry.put("mappingStatus", status);
            statuses.merge(status, 1, Integer::sum);
            for (JsonNode candidate : mapping.path("candidateTestRules")) {
                String testId = candidate.path("testRuleId").asText();
                aiEntry.withArray("testRuleCandidates").add(candidate);
                ObjectNode testEntry = testToAi.computeIfAbsent(testId, ignored -> {
                    ObjectNode value = mapper.createObjectNode();
                    value.put("testRuleId", testId);
                    value.put("title", candidate.path("title").asText());
                    value.put("atomicity", candidate.path("atomicity").asText());
                    value.put("canonicalAnchor", candidate.path("canonicalAnchor").asText());
                    value.putArray("aiRequirementIds");
                    return value;
                });
                testEntry.withArray("aiRequirementIds").add(aiId);
            }
        }

        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "atl105-ai-test-many-to-many-coverage-matrix");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("aiArtifactsIncluded", true);
        output.put("sourceCrosswalk", "atomic-br-ai-crosswalk.json");
        output.put("mappingPolicy", "One AI BR may map to many Test Rules and one Test Rule may map to many AI BRs; mappings remain candidates until semantic review.");
        output.put("confirmationPolicy", "No matrix status is confirmed coverage. FULL, PARTIAL, DUPLICATE, and UNSUPPORTED require source and statement-level review.");
        output.set("statusCounts", counts(statuses, mapper));
        output.put("aiRequirementCount", aiToTest.size());
        output.put("testRuleCountWithCandidates", testToAi.size());
        output.set("aiToTest", array(new ArrayList<>(aiToTest.values()), mapper));
        output.set("testToAi", array(new ArrayList<>(testToAi.values()), mapper));
        ObjectNode validation = output.putObject("validation");
        validation.put("oneEntryPerAiRequirement", aiToTest.size() == crosswalk.path("validation").path("aiRequirementCount").asInt());
        validation.put("preservesCandidateCount", totalCandidateLinks(aiToTest) == exactCandidateLinks(crosswalk));
        validation.put("nextPhase", "SEMANTIC_SOURCE_REVIEW_AND_BR_DECISION");

        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("ai-test-many-to-many-coverage-matrix.json").toFile(), output);
        Files.writeString(outputRoot.resolve("ai-test-many-to-many-coverage-matrix.md"), markdown(output));
        System.out.printf("aiRequirements=%d testRules=%d exactReview=%d compositeReview=%d missing=%d%n",
                aiToTest.size(), testToAi.size(), statuses.getOrDefault("REVIEW_REQUIRED_EXACT_CANDIDATE", 0),
                statuses.getOrDefault("REVIEW_REQUIRED_COMPOSITE_OR_AMBIGUOUS", 0),
                statuses.getOrDefault("MISSING_TEST_RULE_CANDIDATE", 0));
    }

    private static String matrixStatus(String crosswalkStatus) {
        return switch (crosswalkStatus) {
            case "EXACT_ATOMIC_CANDIDATE" -> "REVIEW_REQUIRED_EXACT_CANDIDATE";
            case "REVIEW_REQUIRED_COMPOSITE_OR_AMBIGUOUS" -> "REVIEW_REQUIRED_COMPOSITE_OR_AMBIGUOUS";
            default -> "MISSING_TEST_RULE_CANDIDATE";
        };
    }

    private static int exactCandidateLinks(JsonNode crosswalk) {
        int total = 0;
        for (JsonNode mapping : crosswalk.path("mappings")) total += mapping.path("candidateTestRules").size();
        return total;
    }

    private static int totalCandidateLinks(Map<String, ObjectNode> aiToTest) {
        return aiToTest.values().stream().mapToInt(value -> value.path("testRuleCandidates").size()).sum();
    }

    private static ObjectNode counts(Map<String, Integer> values, ObjectMapper mapper) {
        ObjectNode output = mapper.createObjectNode();
        values.forEach(output::put);
        return output;
    }

    private static ArrayNode array(List<ObjectNode> values, ObjectMapper mapper) {
        ArrayNode output = mapper.createArrayNode();
        values.forEach(output::add);
        return output;
    }

    private static String markdown(JsonNode output) {
        JsonNode statuses = output.path("statusCounts");
        return "# AI to Test Solution Many-to-Many Coverage Matrix\n\n"
                + "The matrix retains both mapping directions and keeps all statuses review-required or missing until semantic source review is complete.\n\n"
                + "| Status | Count |\n|---|---:|\n"
                + "| Exact candidate, review required | " + statuses.path("REVIEW_REQUIRED_EXACT_CANDIDATE").asInt() + " |\n"
                + "| Composite or ambiguous, review required | " + statuses.path("REVIEW_REQUIRED_COMPOSITE_OR_AMBIGUOUS").asInt() + " |\n"
                + "| Missing Test Rule candidate | " + statuses.path("MISSING_TEST_RULE_CANDIDATE").asInt() + " |\n\n"
                + "No mapping is confirmed coverage. The matrix is the input to semantic source review and BR decisioning.\n";
    }
}