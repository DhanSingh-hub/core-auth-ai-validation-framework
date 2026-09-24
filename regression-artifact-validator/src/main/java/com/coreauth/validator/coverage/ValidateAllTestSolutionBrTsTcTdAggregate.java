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
        Path reviewRoot = Atl105Paths.testOutput().resolve("test-solution-independent-review");
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
        Set<String> duplicateIds = new HashSet<>();
        Map<String, Integer> segmentCounts = new TreeMap<>();

        for (JsonNode scenario : root.path("testScenarios")) {
            for (JsonNode id : scenario.path("requirementIds")) {
                String requirementId = id.asText();
                if (requirements.containsKey(requirementId)) {
                    usedRequirements.add(requirementId);
                    requirementsWithoutScenario.remove(requirementId);
                }
            }
        }
        for (JsonNode testCase : root.path("testCases")) {
            for (JsonNode id : testCase.path("scenarioIds")) {
                String scenarioId = id.asText();
                if (scenarios.containsKey(scenarioId)) {
                    usedScenarios.add(scenarioId);
                    scenariosWithoutTestCase.remove(scenarioId);
                }
            }
        }
        for (JsonNode data : root.path("testData")) {
            for (JsonNode id : data.path("testCaseIds")) {
                String testCaseId = id.asText();
                if (testCases.containsKey(testCaseId)) {
                    usedTestCases.add(testCaseId);
                    testCasesWithoutData.remove(testCaseId);
                }
            }
        }
        duplicateIds.addAll(findDuplicateIds(root.path("businessRequirements")));
        duplicateIds.addAll(findDuplicateIds(root.path("testScenarios")));
        duplicateIds.addAll(findDuplicateIds(root.path("testCases")));
        duplicateIds.addAll(findDuplicateIds(root.path("testData")));

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
        result.put("duplicateArtifactIds", duplicateIds.size());
        result.put("fullyLinkedRequirements", requirements.size() - requirementsWithoutScenario.size());
        result.put("fullyLinkedTestCases", testCases.size() - testCasesWithoutData.size());
        result.put("executionReady", requirementsWithoutScenario.isEmpty() && scenariosWithoutTestCase.isEmpty()
                && testCasesWithoutData.isEmpty() && duplicateIds.isEmpty());
        result.set("unlinkedRequirementIds", mapper.valueToTree(requirementsWithoutScenario));
        result.set("unlinkedScenarioIds", mapper.valueToTree(scenariosWithoutTestCase));
        result.set("unlinkedTestCaseIds", mapper.valueToTree(testCasesWithoutData));
        result.set("duplicateArtifactIdsList", mapper.valueToTree(duplicateIds));

        Path output = reviewRoot.resolve("all-test-solution-chain-validation.json");
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
                + "| Duplicate IDs | " + result.path("duplicateArtifactIds").asInt() + " |\n\n"
                + "Execution ready: **" + result.path("executionReady").asBoolean() + "**\n";
    }
}
