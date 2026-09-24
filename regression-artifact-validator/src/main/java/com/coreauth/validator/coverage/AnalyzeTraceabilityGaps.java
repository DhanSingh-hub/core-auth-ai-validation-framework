package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/** Independently recomputes BR-to-TS-to-TC-to-TD link gaps from Run2's traceability_matrix_full.json. */
public final class AnalyzeTraceabilityGaps {

    private AnalyzeTraceabilityGaps() { }

    public static void main(String[] args) throws Exception {
        Path traceability = args.length == 0
                ? Atl105Paths.testInput().resolve(Path.of("ai-solution", "runs", "2026-09-23",
                        "Run2", "traceability_matrix_full.json"))
                : Path.of(args[0]);

        ObjectMapper mapper = new ObjectMapper();
        long totalRequirements = 0;
        long requirementsMissingScenario = 0;       // BR -> TS link missing
        long requirementsMissingTestCase = 0;        // BR has TS but no TS has a TC (TS -> TC link missing)
        long requirementsMissingTestData = 0;        // BR has TC but none resolves data (TC -> TD link missing)
        long requirementsFullyTraced = 0;

        // A scenario/test-case ID can be listed under more than one requirement (multi-parameter scenarios),
        // so its "has downstream link" status must be OR-combined across every occurrence, not counted per-occurrence.
        Map<String, Boolean> scenarioHasTestCase = new HashMap<>();
        Map<String, Boolean> testCaseHasData = new HashMap<>();

        try (JsonParser parser = mapper.getFactory().createParser(traceability.toFile())) {
            require(parser.nextToken() == JsonToken.START_OBJECT, "root must be an object");
            long orphanScenarios = 0;
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String field = parser.currentName();
                parser.nextToken();
                if ("requirements".equals(field)) {
                    require(parser.currentToken() == JsonToken.START_ARRAY, "requirements must be an array");
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        JsonNode requirement = mapper.readTree(parser);
                        totalRequirements++;

                        JsonNode scenarios = requirement.path("scenarios");
                        if (!scenarios.isArray() || scenarios.isEmpty()) {
                            requirementsMissingScenario++;
                            continue;
                        }

                        boolean requirementHasTestCase = false;
                        boolean requirementHasData = false;
                        for (JsonNode scenario : scenarios) {
                            String scenarioId = scenario.path("scenario_id").asText(null);
                            JsonNode testCases = scenario.path("test_cases");
                            boolean scenarioHasAnyTestCase = testCases.isArray() && !testCases.isEmpty();
                            if (scenarioId != null) {
                                scenarioHasTestCase.merge(scenarioId, scenarioHasAnyTestCase, Boolean::logicalOr);
                            }
                            if (!scenarioHasAnyTestCase) continue;
                            requirementHasTestCase = true;
                            for (JsonNode testCase : testCases) {
                                String testCaseId = testCase.path("test_case_id").asText(null);
                                boolean hasData = testCase.path("test_data").path("file_exists").asBoolean(false);
                                if (testCaseId != null) testCaseHasData.merge(testCaseId, hasData, Boolean::logicalOr);
                                if (hasData) requirementHasData = true;
                            }
                        }
                        if (!requirementHasTestCase) requirementsMissingTestCase++;
                        else if (!requirementHasData) requirementsMissingTestData++;
                        else requirementsFullyTraced++;
                    }
                } else if ("orphans".equals(field)) {
                    JsonNode orphans = mapper.readTree(parser);
                    orphanScenarios = orphans.path("scenarios_without_requirement").size();
                } else {
                    parser.skipChildren();
                }
            }

            long totalScenarios = scenarioHasTestCase.size();
            long scenariosMissingTestCase = scenarioHasTestCase.values().stream().filter(value -> !value).count();
            long totalTestCases = testCaseHasData.size();
            long testCasesMissingTestData = testCaseHasData.values().stream().filter(value -> !value).count();

            ObjectNode result = mapper.createObjectNode();
            result.put("totalRequirements", totalRequirements);
            result.put("requirementsMissingScenario_BR_TS_gap", requirementsMissingScenario);
            result.put("requirementsMissingTestCase_TS_TC_gap", requirementsMissingTestCase);
            result.put("requirementsMissingTestData_TC_TD_gap", requirementsMissingTestData);
            result.put("requirementsFullyTraced", requirementsFullyTraced);
            result.put("totalScenarios_linked_to_a_requirement", totalScenarios);
            result.put("scenariosMissingTestCase_TS_TC_gap", scenariosMissingTestCase);
            result.put("totalTestCases", totalTestCases);
            result.put("testCasesMissingTestData_TC_TD_gap", testCasesMissingTestData);
            result.put("orphanScenarios_TS_with_no_BR", orphanScenarios);
            result.put("totalScenarios_including_orphans", totalScenarios + orphanScenarios);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));

            Path outputFile = Atl105Paths.testOutput().resolve(Path.of(
                    "ai-solution-independent-review", "run2-traceability-gap-analysis.json"));
            Files.createDirectories(outputFile.getParent());
            mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile.toFile(), result);
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }
}
