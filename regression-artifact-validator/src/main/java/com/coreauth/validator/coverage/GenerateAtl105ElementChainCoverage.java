package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.SourceAnchor;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Measures per-element BR -> TS -> TC -> TD structural and non-placeholder execution coverage. */
public final class GenerateAtl105ElementChainCoverage {
    private GenerateAtl105ElementChainCoverage() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode reference = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-all-elements-reference.json").toFile());
        JsonNode packageRoot = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json").toFile());
        Map<String, JsonNode> requirements = indexById(packageRoot.path("businessRequirements"));
        Map<String, List<JsonNode>> scenarios = indexByLink(packageRoot.path("testScenarios"), "requirementIds");
        Map<String, List<JsonNode>> cases = indexByLink(packageRoot.path("testCases"), "scenarioIds");
        Map<String, List<JsonNode>> data = indexByLink(packageRoot.path("testData"), "testCaseIds");
        ArrayNode rows = mapper.createArrayNode();
        int totalRules = 0;
        int structuralComplete = 0;
        int executableComplete = 0;
        for (JsonNode element : reference.path("elements")) {
            int rules = 0;
            int structural = 0;
            int executable = 0;
            for (JsonNode rule : element.path("businessRules")) {
                String ruleId = rule.path("ruleId").asText();
                String brId = "BR-RULE-" + ruleId;
                JsonNode br = requirements.get(brId);
                List<JsonNode> linkedScenarios = scenarios.getOrDefault(brId, List.of());
                boolean hasScenario = !linkedScenarios.isEmpty();
                boolean hasCase = false;
                boolean hasData = false;
                boolean executableRule = false;
                for (JsonNode scenario : linkedScenarios) {
                    for (JsonNode testCase : cases.getOrDefault(scenario.path("id").asText(), List.of())) {
                        hasCase = true;
                        List<JsonNode> linkedData = data.getOrDefault(testCase.path("id").asText(), List.of());
                        if (!linkedData.isEmpty()) hasData = true;
                        for (JsonNode testData : linkedData) {
                            if (!isPlaceholder(br) && !isPlaceholder(scenario) && !isPlaceholder(testCase)
                                && !isPlaceholder(testData) && hasUsableData(testData)
                                && sharesSourceAnchor(mapper, br, scenario, testCase, testData)) executableRule = true;
                        }
                    }
                }
                boolean structuralCompleteRule = br != null && hasScenario && hasCase && hasData;
                rules++;
                if (structuralCompleteRule) structural++;
                if (executableRule) executable++;
                totalRules++;
            }
            ObjectNode row = rows.addObject();
            row.put("element", element.path("element").asText());
            row.put("sourceNames", String.join("; ", values(element.path("sourceNames"))));
            row.put("ruleCount", rules);
            row.put("structuralCompleteRules", structural);
            row.put("structuralCoveragePercent", percent(structural, rules));
            row.put("executableCompleteRules", executable);
            row.put("executableCoveragePercent", percent(executable, rules));
            row.put("placeholderOrReviewRules", rules - executable);
            row.put("status", executable == rules && rules > 0 ? "EXECUTABLE_REVIEW_REQUIRED" : structural == rules && rules > 0 ? "STRUCTURAL_ONLY_REVIEW_REQUIRED" : "INCOMPLETE_CHAIN_REVIEW_REQUIRED");
            if (rules > 0) { structuralComplete += structural; executableComplete += executable; }
        }
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "ATL105-ELEMENT-BR-TS-TC-TD-CHAIN-COVERAGE");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("status", "REVIEW_REQUIRED_NOT_EXECUTION_CERTIFIED");
        output.put("policy", "Structural completion includes generated REVIEW_REQUIRED placeholder chains and is not authored or executable coverage. Executable coverage requires a connected non-placeholder BR, TS, TC and TD path, a common canonical source anchor and usable payload; no element is execution-certified by this report alone.");
        output.put("elementCount", rows.size());
        output.put("oracleRulesWithElementAnchors", totalRules);
        output.put("structuralCompleteRules", structuralComplete);
        output.put("structuralCoveragePercent", percent(structuralComplete, totalRules));
        output.put("executableCompleteRules", executableComplete);
        output.put("executableCoveragePercent", percent(executableComplete, totalRules));
        output.put("placeholderOrReviewRules", totalRules - executableComplete);
        output.set("rows", rows);
        Path json = pack.resolve("test-output/test-solution-independent-review/atl105-element-chain-coverage.json");
        Path csv = pack.resolve("test-output/test-solution-independent-review/atl105-element-chain-coverage.csv");
        Path md = pack.resolve("test-output/test-solution-independent-review/atl105-element-chain-coverage.md");
        Files.createDirectories(json.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(json.toFile(), output);
        writeCsv(csv, rows);
        Files.writeString(md, markdown(output));
        System.out.printf("elements=%d rules=%d structural=%d (%.1f%%) executable=%d (%.1f%%) placeholders=%d%n",
            rows.size(), totalRules, structuralComplete, percent(structuralComplete, totalRules), executableComplete, percent(executableComplete, totalRules), totalRules - executableComplete);
    }

    private static Map<String, JsonNode> indexById(JsonNode values) {
        Map<String, JsonNode> result = new HashMap<>();
        if (values.isArray()) for (JsonNode value : values) result.put(value.path("id").asText(), value);
        return result;
    }

    static Map<String, List<JsonNode>> indexByLink(JsonNode values, String field) {
        Map<String, List<JsonNode>> result = new HashMap<>();
        if (values.isArray()) for (JsonNode value : values) {
            JsonNode ids = value.path(field);
            if (ids.isArray()) for (JsonNode id : ids) {
                if (id.isTextual()) result.computeIfAbsent(id.asText(), ignored -> new ArrayList<>()).add(value);
            }
        }
        return result;
    }

    private static boolean isPlaceholder(JsonNode value) {
        if (value == null || value.isMissingNode()) return true;
        String id = value.path("id").asText();
        String status = value.path("status").asText();
        String training = value.path("trainingStatus").asText();
        String readiness = value.path("readiness").asText();
        return id.startsWith("BR-RULE-") || id.startsWith("TS-REVIEW-") || id.startsWith("TC-REVIEW-") || id.startsWith("TD-REVIEW-")
            || status.contains("REVIEW") || training.contains("DERIVATION_REQUIRED") || readiness.contains("REVIEW");
    }

    private static boolean hasUsableData(JsonNode testData) {
        JsonNode payload = testData.path("payload");
        return payload.isObject() && !payload.isEmpty() && !"SME_FIXTURE_REQUIRED".equals(payload.path("fixtureStatus").asText());
    }

    static boolean sharesSourceAnchor(ObjectMapper mapper, JsonNode... artifacts) {
        Set<String> common = null;
        for (JsonNode artifact : artifacts) {
            JsonNode anchors = artifact.path("sourceAnchors");
            if (!anchors.isArray() || anchors.isEmpty()) return false;
            Set<String> keys = new HashSet<>();
            for (JsonNode anchor : anchors) {
                SourceAnchor source = mapper.convertValue(anchor, SourceAnchor.class);
                if (!source.canonicalKey().replace("|", "").isBlank()) keys.add(source.canonicalKey());
            }
            if (common == null) common = keys;
            else common.retainAll(keys);
            if (common.isEmpty()) return false;
        }
        return common != null && !common.isEmpty();
    }

    private static double percent(int numerator, int denominator) {
        return denominator == 0 ? 0 : Math.round(1000.0 * numerator / denominator) / 10.0;
    }

    private static List<String> values(JsonNode array) { List<String> result = new ArrayList<>(); array.forEach(value -> result.add(value.asText())); return result; }

    private static void writeCsv(Path path, ArrayNode rows) throws Exception {
        List<String> lines = new ArrayList<>();
        lines.add("element,sourceNames,ruleCount,structuralCompleteRules,structuralCoveragePercent,executableCompleteRules,executableCoveragePercent,placeholderOrReviewRules,status");
        for (JsonNode row : rows) {
            List<String> fields = List.of(row.path("element").asText(), row.path("sourceNames").asText(), row.path("ruleCount").asText(),
                row.path("structuralCompleteRules").asText(), row.path("structuralCoveragePercent").asText(), row.path("executableCompleteRules").asText(),
                row.path("executableCoveragePercent").asText(), row.path("placeholderOrReviewRules").asText(), row.path("status").asText());
            lines.add(fields.stream().map(GenerateAtl105ElementChainCoverage::csv).reduce((a, b) -> a + "," + b).orElse(""));
        }
        Files.write(path, lines);
    }

    private static String csv(String value) { return "\"" + value.replace("\"", "\"\"") + "\""; }

    private static String markdown(ObjectNode output) {
        return "# ATL105 Element BR -> TS -> TC -> TD Chain Coverage\n\n"
            + "| Metric | Count |\n|---|---:|\n"
            + "| Element count | " + output.path("elementCount").asInt() + " |\n"
            + "| Oracle rules with element anchors | " + output.path("oracleRulesWithElementAnchors").asInt() + " |\n"
            + "| Structurally complete rules | " + output.path("structuralCompleteRules").asInt() + " |\n"
            + "| Structural coverage | " + output.path("structuralCoveragePercent").asDouble() + "% |\n"
            + "| Executable non-placeholder chains | " + output.path("executableCompleteRules").asInt() + " |\n"
            + "| Executable coverage | " + output.path("executableCoveragePercent").asDouble() + "% |\n"
            + "| Placeholder/review rules | " + output.path("placeholderOrReviewRules").asInt() + " |\n\n"
            + "Structural completion includes generated REVIEW_REQUIRED placeholders, not authored test coverage. A chain needs non-placeholder BR, TS, TC and TD evidence, one common source anchor and usable payload data before it can count as executable.\n";
    }
}
