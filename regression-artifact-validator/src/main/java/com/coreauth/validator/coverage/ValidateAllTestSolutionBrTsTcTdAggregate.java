package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/** Validates BR -> TS -> TC -> TD links in the aggregate independent Test Solution package. */
public final class ValidateAllTestSolutionBrTsTcTdAggregate {
    private ValidateAllTestSolutionBrTsTcTdAggregate() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path reviewRoot = args.length > 1 ? Path.of(args[1]) : Atl105Paths.testOutput().resolve("test-solution-independent-review");
        Path input = args.length == 0
                ? reviewRoot.resolve("all-test-solution-br-ts-tc-td-training-package.json") : Path.of(args[0]);
        JsonNode root = mapper.readTree(input.toFile());

        Map<String, JsonNode> requirements = index(root.path("businessRequirements"));
        Map<String, JsonNode> scenarios = index(root.path("testScenarios"));
        Map<String, JsonNode> testCases = index(root.path("testCases"));
        Map<String, JsonNode> testData = index(root.path("testData"));
        Set<String> usedRequirements = new HashSet<>();
        Set<String> usedScenarios = new HashSet<>();
        Set<String> usedTestCases = new HashSet<>();
        Set<String> requirementsWithoutScenario = new HashSet<>(requirements.keySet());
        Set<String> scenariosWithoutTestCase = new HashSet<>(scenarios.keySet());
        Set<String> testCasesWithoutData = new HashSet<>(testCases.keySet());
        Set<String> scenarioLinksToMissingRequirements = new HashSet<>();
        Set<String> testCaseLinksToMissingScenarios = new HashSet<>();
        Set<String> testDataLinksToMissingTestCases = new HashSet<>();
        Set<String> testCasesWithExecutableReadinessData = new HashSet<>();
        Set<String> requirementsWithExecutableReadinessDataChain = new HashSet<>();
        Set<String> duplicateIds = new HashSet<>();
        boolean hasReviewPlaceholders = false;
        Map<String, Integer> testDataReadinessCounts = new TreeMap<>();

        for (JsonNode scenario : root.path("testScenarios")) {
            for (JsonNode id : scenario.path("requirementIds")) {
                String requirementId = id.asText();
                if (requirements.containsKey(requirementId)) {
                    usedRequirements.add(requirementId);
                    requirementsWithoutScenario.remove(requirementId);
                } else {
                    scenarioLinksToMissingRequirements.add(scenario.path("id").asText() + " -> " + requirementId);
                }
            }
        }
        for (JsonNode testCase : root.path("testCases")) {
            for (JsonNode id : testCase.path("scenarioIds")) {
                String scenarioId = id.asText();
                if (scenarios.containsKey(scenarioId)) {
                    usedScenarios.add(scenarioId);
                    scenariosWithoutTestCase.remove(scenarioId);
                } else {
                    testCaseLinksToMissingScenarios.add(testCase.path("id").asText() + " -> " + scenarioId);
                }
            }
        }
        for (JsonNode data : root.path("testData")) {
            String readiness = data.path("readiness").asText("READINESS_NOT_DECLARED");
            testDataReadinessCounts.merge(readiness, 1, Integer::sum);
            for (JsonNode id : data.path("testCaseIds")) {
                String testCaseId = id.asText();
                if (testCases.containsKey(testCaseId)) {
                    usedTestCases.add(testCaseId);
                    testCasesWithoutData.remove(testCaseId);
                    if ("EXECUTABLE".equals(readiness)) {
                        testCasesWithExecutableReadinessData.add(testCaseId);
                    }
                } else {
                    testDataLinksToMissingTestCases.add(data.path("id").asText() + " -> " + testCaseId);
                }
            }
        }
        for (String testCaseId : testCasesWithExecutableReadinessData) {
            JsonNode testCase = testCases.get(testCaseId);
            for (JsonNode scenarioId : testCase.path("scenarioIds")) {
                JsonNode scenario = scenarios.get(scenarioId.asText());
                if (scenario == null) continue;
                for (JsonNode requirementId : scenario.path("requirementIds")) {
                    String id = requirementId.asText();
                    if (requirements.containsKey(id)) requirementsWithExecutableReadinessDataChain.add(id);
                }
            }
        }
        duplicateIds.addAll(findDuplicateIds(root.path("businessRequirements")));
        duplicateIds.addAll(findDuplicateIds(root.path("testScenarios")));
        duplicateIds.addAll(findDuplicateIds(root.path("testCases")));
        duplicateIds.addAll(findDuplicateIds(root.path("testData")));
        hasReviewPlaceholders = hasReviewPlaceholder(root)
            || root.path("provenance").path("unlinkedTestDataCandidates").size() > 0;

        ObjectNode result = mapper.createObjectNode();
        result.put("artifact", "all-test-solution-br-ts-tc-td-chain-validation");
        result.put("input", input.toString());
        result.put("businessRequirements", requirements.size());
        result.put("testScenarios", scenarios.size());
        result.put("testCases", testCases.size());
        result.put("testData", testData.size());
        result.put("requirementsWithoutScenario", requirementsWithoutScenario.size());
        result.put("scenariosWithoutTestCase", scenariosWithoutTestCase.size());
        result.put("testCasesWithoutData", testCasesWithoutData.size());
        result.put("scenarioLinksToMissingRequirements", scenarioLinksToMissingRequirements.size());
        result.put("testCaseLinksToMissingScenarios", testCaseLinksToMissingScenarios.size());
        result.put("testDataLinksToMissingTestCases", testDataLinksToMissingTestCases.size());
        result.put("unlinkedTestDataCandidates",
            root.path("provenance").path("unlinkedTestDataCandidates").size());
        result.put("testCasesWithExecutableReadinessData", testCasesWithExecutableReadinessData.size());
        result.put("requirementsWithExecutableReadinessDataChain", requirementsWithExecutableReadinessDataChain.size());
        result.set("testDataReadinessCounts", mapper.valueToTree(testDataReadinessCounts));
        result.put("duplicateArtifactIds", duplicateIds.size());
        result.put("fullyLinkedRequirements", requirements.size() - requirementsWithoutScenario.size());
        result.put("fullyLinkedTestCases", testCases.size() - testCasesWithoutData.size());
        boolean structurallyComplete = requirementsWithoutScenario.isEmpty() && scenariosWithoutTestCase.isEmpty()
            && testCasesWithoutData.isEmpty() && scenarioLinksToMissingRequirements.isEmpty()
            && testCaseLinksToMissingScenarios.isEmpty() && testDataLinksToMissingTestCases.isEmpty()
            && duplicateIds.isEmpty();
        result.put("structurallyComplete", structurallyComplete);
        result.put("hasReviewPlaceholders", hasReviewPlaceholders);
        result.put("executionReady", false);
        result.put("executionReadinessReason", "Link integrity alone cannot certify canonical anchor continuity, validated payloads or SME approval.");
        result.set("unlinkedRequirementIds", mapper.valueToTree(requirementsWithoutScenario));
        result.set("unlinkedScenarioIds", mapper.valueToTree(scenariosWithoutTestCase));
        result.set("unlinkedTestCaseIds", mapper.valueToTree(testCasesWithoutData));
        result.set("scenarioLinksToMissingRequirementsList", mapper.valueToTree(scenarioLinksToMissingRequirements));
        result.set("testCaseLinksToMissingScenariosList", mapper.valueToTree(testCaseLinksToMissingScenarios));
        result.set("testDataLinksToMissingTestCasesList", mapper.valueToTree(testDataLinksToMissingTestCases));
        result.set("duplicateArtifactIdsList", mapper.valueToTree(duplicateIds));

        Path output = reviewRoot.resolve("all-test-solution-chain-validation.json");
        Files.createDirectories(reviewRoot);
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), result);
        Files.writeString(reviewRoot.resolve("all-test-solution-chain-validation.md"), markdown(result));
        System.out.printf("BR=%d TS=%d TC=%d TD=%d BR_TS_gaps=%d TS_TC_gaps=%d TC_TD_gaps=%d executionReady=%s%n",
                requirements.size(), scenarios.size(), testCases.size(), testData.size(),
                requirementsWithoutScenario.size(), scenariosWithoutTestCase.size(), testCasesWithoutData.size(),
                result.path("executionReady").asBoolean());
    }

    private static Map<String, JsonNode> index(JsonNode values) {
        Map<String, JsonNode> result = new LinkedHashMap<>();
        if (!values.isArray()) return result;
        for (JsonNode value : values) result.putIfAbsent(value.path("id").asText(), value);
        return result;
    }

    private static Set<String> findDuplicateIds(JsonNode values) {
        Set<String> seen = new HashSet<>();
        Set<String> duplicate = new HashSet<>();
        if (!values.isArray()) return duplicate;
        for (JsonNode value : values) {
            String id = value.path("id").asText();
            if (!seen.add(id)) duplicate.add(id);
        }
        return duplicate;
    }

    private static boolean hasReviewPlaceholder(JsonNode root) {
        for (String collection : new String[]{"businessRequirements", "testScenarios", "testCases", "testData"}) {
            for (JsonNode value : root.path(collection)) {
                if ("REVIEW_REQUIRED".equals(value.path("status").asText())
                        || value.path("trainingStatus").asText().endsWith("_REQUIRED")
                    || "REVIEW_REQUIRED".equals(value.path("expectedValidation").asText())
                    || "REVIEW_REQUIRED".equals(value.path("readiness").asText())) return true;
            }
        }
        return false;
    }

    private static String markdown(JsonNode result) {
        return "# Aggregate Test Solution Chain Validation\n\n"
                + "| Metric | Count |\n|---|---:|\n"
                + "| BR | " + result.path("businessRequirements").asInt() + " |\n"
                + "| TS | " + result.path("testScenarios").asInt() + " |\n"
                + "| TC | " + result.path("testCases").asInt() + " |\n"
                + "| TD | " + result.path("testData").asInt() + " |\n"
                + "| BR -> TS gaps | " + result.path("requirementsWithoutScenario").asInt() + " |\n"
                + "| TS -> TC gaps | " + result.path("scenariosWithoutTestCase").asInt() + " |\n"
                + "| TC -> TD gaps | " + result.path("testCasesWithoutData").asInt() + " |\n"
                + "| TS -> missing BR references | " + result.path("scenarioLinksToMissingRequirements").asInt() + " |\n"
                + "| TC -> missing TS references | " + result.path("testCaseLinksToMissingScenarios").asInt() + " |\n"
                + "| TD -> missing TC references | " + result.path("testDataLinksToMissingTestCases").asInt() + " |\n"
                + "| Unlinked TD candidates (outside canonical chain) | " + result.path("unlinkedTestDataCandidates").asInt() + " |\n"
                + "| TD readiness counts | " + result.path("testDataReadinessCounts") + " |\n"
                + "| Test cases linked to EXECUTABLE-readiness TDs | " + result.path("testCasesWithExecutableReadinessData").asInt() + " |\n"
                + "| BRs with linked EXECUTABLE-readiness TD chain | " + result.path("requirementsWithExecutableReadinessDataChain").asInt() + " |\n"
                + "| Duplicate IDs | " + result.path("duplicateArtifactIds").asInt() + " |\n"
                + "| Structural complete | " + result.path("structurallyComplete").asBoolean() + " |\n"
                + "| Review placeholders | " + result.path("hasReviewPlaceholders").asBoolean() + " |\n\n"
                + "Execution ready: **" + result.path("executionReady").asBoolean() + "**. "
                + result.path("executionReadinessReason").asText() + "\n";
    }
}
