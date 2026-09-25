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

/** Calculates source-rule coverage independently from AI BR volume. */
public final class GenerateSourceBehaviorCoverageMetrics {
    private GenerateSourceBehaviorCoverageMetrics() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path root = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        JsonNode inventory = mapper.readTree(Atl105Paths.testOutput().resolve(Path.of(
                "test-solution-independent-review", "source-derived-requirement-inventory.json")).toFile());
        JsonNode report = mapper.readTree(root.resolve(Path.of(
                "four-level-traceability-matches", "four-level-traceability-matches.json")).toFile());
        Set<String> confirmedRules = new HashSet<>();
        Set<String> validatedRules = new HashSet<>();
        Set<String> behaviorTypes = new HashSet<>();
        Set<String> validatedBehaviorTypes = new HashSet<>();
        for (JsonNode match : report.path("businessRequirements")) {
            String ruleId = match.path("testRequirementId").asText();
            if (!"CONFIRMED".equals(match.path("matchStatus").asText())) continue;
            confirmedRules.add(ruleId);
            JsonNode chain = match.path("traceability").path("testSolution");
            boolean complete = chain.path("businessRequirements").size() > 0
                    && chain.path("testScenarios").size() > 0
                    && chain.path("testCases").size() > 0
                    && chain.path("testData").size() > 0;
            String type = behaviorType(match);
            behaviorTypes.add(type);
            if (complete && !containsReviewMarker(chain)) {
                validatedRules.add(ruleId);
                validatedBehaviorTypes.add(type);
            }
        }
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "atl105-source-behavior-coverage-metrics");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("aiArtifactsIncluded", true);
        output.put("sourceBehaviorDenominator", inventory.path("ruleCount").asInt());
        output.put("sourceBehaviorsWithConfirmedMapping", confirmedRules.size());
        output.put("sourceBehaviorsWithAutomatedValidation", validatedRules.size());
        output.put("confirmedSourceCoveragePercent", percent(confirmedRules.size(), inventory.path("ruleCount").asInt()));
        output.put("automatedSourceCoveragePercent", percent(validatedRules.size(), inventory.path("ruleCount").asInt()));
        output.put("aiBrCoverageMetric", "AI BRs fully explained by source rules / total AI BRs");
        output.put("automatedValidatedAiBrCount", readAutomatedAiCount(root, mapper));
        output.put("totalAiBrCount", 6473);
        output.put("behaviorTypesObserved", array(behaviorTypes, mapper));
        output.put("behaviorTypesAutomatedValidated", array(validatedBehaviorTypes, mapper));
        output.put("scenarioTypePolicy", "Positive, negative, boundary, conditional applicability, and lifecycle evidence are required where applicable; missing types route to SME_REQUIRED.");
        output.putObject("nextGate").put("name", "SCENARIO_TYPE_AND_EXECUTION_EVIDENCE");
        mapper.writerWithDefaultPrettyPrinter().writeValue(root.resolve("source-behavior-coverage-metrics.json").toFile(), output);
        Files.writeString(root.resolve("source-behavior-coverage-metrics.md"), markdown(output));
        System.out.printf("sourceRules=%d confirmed=%d automated=%d%n", inventory.path("ruleCount").asInt(), confirmedRules.size(), validatedRules.size());
    }

    private static int readAutomatedAiCount(Path root, ObjectMapper mapper) throws Exception {
        Path register = root.resolve("automated-test-validation-register.json");
        return Files.isRegularFile(register) ? mapper.readTree(register.toFile()).path("automatedTestValidatedDistinctAiBrCount").asInt() : 0;
    }

    private static String behaviorType(JsonNode match) {
        String title = match.path("testBusinessRequirements").path(0).path("title").asText("").toLowerCase();
        if (title.contains("lifecycle") || title.contains("reversal") || title.contains("completion")) return "LIFECYCLE";
        if (title.contains("required") || title.contains("companion") || title.contains("applicability")) return "APPLICABILITY";
        if (title.contains("length") || title.contains("format") || title.contains("value")) return "BOUNDARY_OR_FIELD_CONSTRAINT";
        return "POSITIVE_OR_STRUCTURE";
    }

    private static boolean containsReviewMarker(JsonNode node) {
        if (node.isObject()) {
            if ("REVIEW_REQUIRED".equals(node.path("status").asText())
                    || node.path("trainingStatus").asText().endsWith("_REQUIRED")
                    || "REVIEW_REQUIRED".equals(node.path("expectedValidation").asText())) return true;
            var fields = node.fields();
            while (fields.hasNext()) if (containsReviewMarker(fields.next().getValue())) return true;
        } else if (node.isArray()) for (JsonNode value : node) if (containsReviewMarker(value)) return true;
        return false;
    }

    private static String percent(int numerator, int denominator) {
        return denominator == 0 ? "0.00" : String.format(java.util.Locale.ROOT, "%.2f", numerator * 100.0 / denominator);
    }

    private static ArrayNode array(Set<String> values, ObjectMapper mapper) {
        ArrayNode output = mapper.createArrayNode();
        values.stream().sorted().forEach(output::add);
        return output;
    }

    private static String markdown(JsonNode output) {
        return "# Source Behavior Coverage Metrics\n\n"
                + "| Metric | Count |\n|---|---:|\n"
                + "| Source behavior denominator | " + output.path("sourceBehaviorDenominator").asInt() + " |\n"
                + "| Confirmed source behaviors | " + output.path("sourceBehaviorsWithConfirmedMapping").asInt() + " |\n"
                + "| Automated validated source behaviors | " + output.path("sourceBehaviorsWithAutomatedValidation").asInt() + " |\n"
                + "| Automated source coverage | " + output.path("automatedSourceCoveragePercent").asText() + "% |\n"
                + "| Automated validated AI BRs | " + output.path("automatedValidatedAiBrCount").asInt() + " / " + output.path("totalAiBrCount").asInt() + " |\n\n"
                + output.path("scenarioTypePolicy").asText() + "\n";
    }
}