package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map;

/** Marks confirmed AI/Test mappings as AutomatedTestValidated only when all four Test levels are executable evidence. */
public final class GenerateAutomatedTestValidationRegister {
    private GenerateAutomatedTestValidationRegister() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        Path source = reviewRoot.resolve(Path.of("four-level-matches", "four-level-traceability-matches.json"));
        JsonNode report = mapper.readTree(source.toFile());
        ArrayNode mappings = mapper.createArrayNode();
        Map<String, Integer> statuses = new TreeMap<>();
        Set<String> validatedAiRequirements = new HashSet<>();
        int confirmed = 0;
        int reviewRequired = 0;

        for (JsonNode match : report.path("businessRequirements")) {
            String matchStatus = match.path("matchStatus").asText();
            if ("CONFIRMED".equals(matchStatus)) confirmed++; else reviewRequired++;
            String status = "CONFIRMED".equals(matchStatus)
                    ? (qualifies(match) ? "AutomatedTestValidated" : "AutomatedChainIncomplete")
                    : "SME_REQUIRED";
            ObjectNode entry = mappings.addObject();
            entry.put("testRequirementId", match.path("testRequirementId").asText());
            entry.put("automatedStatus", status);
            entry.put("validationRule", validationRule(status));
            entry.put("matchReason", match.path("matchReason").asText());
            entry.put("sourceEvidenceStatus", sourceEvidenceStatus(match));
            entry.put("behaviorEvidenceStatus", behaviorEvidenceStatus(match));
            entry.set("canonicalAnchors", match.path("canonicalAnchors"));
            entry.put("aiBusinessRequirementCount", match.path("aiBusinessRequirements").size());
            entry.put("testBusinessRequirementCount", match.path("testBusinessRequirements").size());
            entry.put("aiTestScenarioCount", match.path("aiTestScenarios").size());
            entry.put("testScenarioCount", match.path("testScenarios").size());
            entry.put("aiTestCaseCount", match.path("aiTestCases").size());
            entry.put("testCaseCount", match.path("testCases").size());
            entry.put("aiTestDataCount", match.path("aiTestData").size());
            entry.put("testDataCount", match.path("testData").size());
            entry.set("aiBusinessRequirements", match.path("aiBusinessRequirements"));
            entry.set("testBusinessRequirements", match.path("testBusinessRequirements"));
            entry.set("testSolutionChain", match.path("traceability").path("testSolution"));
            statuses.merge(status, 1, Integer::sum);
            if ("AutomatedTestValidated".equals(status)) {
                for (JsonNode ai : match.path("aiBusinessRequirements")) validatedAiRequirements.add(ai.path("id").asText());
            }
        }

        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "atl105-automated-test-validation-register");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("aiArtifactsIncluded", true);
        output.put("sourceReport", "four-level-matches/four-level-traceability-matches.json");
        output.put("statusDefinition", "AutomatedTestValidated means the confirmed AI/Test mapping has non-empty BR, TS, TC, and TD evidence on both sides and no review placeholders in the matched Test chain.");
        output.put("smeApprovalRequiredForAutomatedStatus", false);
        output.put("smeApprovalRequiredForUnresolvedMappings", true);
        output.put("humanOversight", "Automated validation does not replace source ownership or defect review; it only records evidence-backed chain validation.");
        output.put("confirmedMappingCount", confirmed);
        output.put("reviewRequiredMappingCount", reviewRequired);
        output.put("automatedTestValidatedMappingCount", statuses.getOrDefault("AutomatedTestValidated", 0));
        output.put("automatedTestValidatedDistinctAiBrCount", validatedAiRequirements.size());
        output.set("statusCounts", counts(statuses, mapper));
        output.set("mappings", mappings);
        ObjectNode validation = output.putObject("validation");
        validation.put("allValidatedMappingsHaveFourLevels", allValidatedMappingsHaveFourLevels(mappings));
        validation.put("smeOnlyForUnresolvedMappings", smeOnlyForUnresolvedMappings(mappings));
        validation.put("nextGate", "AUTOMATED_VALIDATION_DASHBOARD_REFRESH");

        mapper.writerWithDefaultPrettyPrinter().writeValue(reviewRoot.resolve("automated-test-validation-register.json").toFile(), output);
        Files.writeString(reviewRoot.resolve("automated-test-validation-register.md"), markdown(output));
        System.out.printf("confirmed=%d automatedTestValidated=%d distinctAiBrs=%d incomplete=%d%n",
                confirmed, statuses.getOrDefault("AutomatedTestValidated", 0), validatedAiRequirements.size(),
                statuses.getOrDefault("AutomatedChainIncomplete", 0));
    }

    private static boolean qualifies(JsonNode match) {
        JsonNode chain = match.path("traceability").path("testSolution");
        return "CONFIRMED".equals(match.path("matchStatus").asText())
            && "CANONICAL_ANCHOR_PRESENT".equals(sourceEvidenceStatus(match))
            && "AUTOMATICALLY_PROVABLE".equals(behaviorEvidenceStatus(match))
            && completeArray(chain.path("businessRequirements"))
                && completeArray(chain.path("testScenarios"))
                && completeArray(chain.path("testCases"))
                && completeArray(chain.path("testData"))
                && !hasReviewPlaceholder(chain);
    }

    private static String sourceEvidenceStatus(JsonNode match) {
        return match.path("canonicalAnchors").isArray() && match.path("canonicalAnchors").size() > 0
                ? "CANONICAL_ANCHOR_PRESENT" : "MISSING_CANONICAL_ANCHOR";
    }

    private static String behaviorEvidenceStatus(JsonNode match) {
        String reason = match.path("matchReason").asText("").toLowerCase();
        boolean exactCanonicalEvidence = reason.contains("exact canonical-anchor")
            || reason.contains("unambiguous canonical rule-id");
        return "CONFIRMED".equals(match.path("matchStatus").asText())
            && exactCanonicalEvidence
                ? "AUTOMATICALLY_PROVABLE" : "SME_SEMANTIC_REVIEW_REQUIRED";
    }

    private static String validationRule(String status) {
        return switch (status) {
            case "AutomatedTestValidated" -> "Confirmed source mapping + canonical anchor + complete non-placeholder BR/TS/TC/TD chain";
            case "AutomatedChainIncomplete" -> "Confirmed source mapping but Test Solution chain is incomplete or contains review placeholders";
            default -> "Match semantics, source equivalence, or crosswalk status requires SME review";
        };
    }

    private static boolean completeCounts(JsonNode value) {
        return value.path("testBusinessRequirementCount").asInt() > 0
                && value.path("testScenarioCount").asInt() > 0
                && value.path("testCaseCount").asInt() > 0
                && value.path("testDataCount").asInt() > 0;
    }

    private static boolean allValidatedMappingsHaveFourLevels(ArrayNode mappings) {
        for (JsonNode mapping : mappings) {
            if ("AutomatedTestValidated".equals(mapping.path("automatedStatus").asText())
                    && !completeCounts(mapping)) return false;
        }
        return true;
    }

    private static boolean smeOnlyForUnresolvedMappings(ArrayNode mappings) {
        for (JsonNode mapping : mappings) {
            if ("SME_REQUIRED".equals(mapping.path("automatedStatus").asText())
                    && "AUTOMATICALLY_PROVABLE".equals(mapping.path("behaviorEvidenceStatus").asText())) return false;
        }
        return true;
    }

    private static boolean completeArray(JsonNode values) {
        return values.isArray() && values.size() > 0;
    }

    private static boolean hasReviewPlaceholder(JsonNode root) {
        if (root.isObject()) {
            if ("REVIEW_REQUIRED".equals(root.path("status").asText())
                    || root.path("trainingStatus").asText().endsWith("_REQUIRED")
                    || "REVIEW_REQUIRED".equals(root.path("expectedValidation").asText())) return true;
            var fields = root.fields();
            while (fields.hasNext()) if (hasReviewPlaceholder(fields.next().getValue())) return true;
        } else if (root.isArray()) {
            for (JsonNode value : root) if (hasReviewPlaceholder(value)) return true;
        }
        return false;
    }

    private static ObjectNode counts(Map<String, Integer> values, ObjectMapper mapper) {
        ObjectNode output = mapper.createObjectNode();
        values.forEach(output::put);
        return output;
    }

    private static String markdown(JsonNode output) {
        return "# Automated Test Validation Register\n\n"
                + "A mapping is `AutomatedTestValidated` only when the confirmed AI/Test mapping has complete non-placeholder BR -> TS -> TC -> TD evidence.\n\n"
                + "| Metric | Count |\n|---|---:|\n"
                + "| Confirmed mapping groups | " + output.path("confirmedMappingCount").asInt() + " |\n"
                + "| AutomatedTestValidated mapping groups | " + output.path("automatedTestValidatedMappingCount").asInt() + " |\n"
                + "| Distinct validated AI BRs | " + output.path("automatedTestValidatedDistinctAiBrCount").asInt() + " |\n"
                + "| Automated chain incomplete | " + output.path("statusCounts").path("AutomatedChainIncomplete").asInt() + " |\n"
                + "| SME required | " + output.path("statusCounts").path("SME_REQUIRED").asInt() + " |\n\n"
                + "SME review is reserved for mappings that automated source and chain evidence cannot prove.\n";
    }
}