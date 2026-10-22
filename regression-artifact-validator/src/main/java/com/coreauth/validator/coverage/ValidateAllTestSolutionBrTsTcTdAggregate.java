package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
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
        if (root == null) root = mapper.createObjectNode();
        List<String> inputErrors = validateCollections(root);

        Map<String, JsonNode> requirements = index(root.path("businessRequirements"));
        Map<String, JsonNode> scenarios = index(root.path("testScenarios"));
        Map<String, JsonNode> testCases = index(root.path("testCases"));
        Map<String, JsonNode> testData = index(root.path("testData"));
        Set<String> usedRequirements = new HashSet<>();
        Set<String> usedScenarios = new HashSet<>();
        Set<String> usedTestCases = new HashSet<>();
        Set<String> requirementsWithoutScenario = new HashSet<>(requirements.keySet());
        Set<String> scenariosWithoutTestCase = new HashSet<>(scenarios.keySet());
        Set<String> scenariosWithoutRequirement = new HashSet<>();
        Set<String> reviewOnlyScenariosWithoutRequirement = new HashSet<>();
        Set<String> testCasesWithoutData = new HashSet<>(testCases.keySet());
        Set<String> scenarioLinksToMissingRequirements = new HashSet<>();
        Set<String> testCaseLinksToMissingScenarios = new HashSet<>();
        Set<String> testCaseLinksToMissingTestData = new HashSet<>();
        Set<String> testDataLinksToMissingTestCases = new HashSet<>();
        Set<String> testCaseDataLinkMismatches = new HashSet<>();
        Set<String> testCasesWithExecutableReadinessData = new HashSet<>();
        Set<String> requirementsWithExecutableReadinessDataChain = new HashSet<>();
        Set<String> duplicateIds = findDuplicateIds(root);
        Map<String, Integer> reviewRequiredArtifactsByType = new TreeMap<>();
        Map<String, Map<String, Map<String, Integer>>> artifactStatusCountsByType = new TreeMap<>();
        int reviewRequiredArtifactCount = 0;
        Map<String, Integer> testDataReadinessCounts = new TreeMap<>();

        for (JsonNode scenario : root.path("testScenarios")) {
            if (!scenario.path("requirementIds").isArray() || scenario.path("requirementIds").isEmpty()) {
                String scenarioId = scenario.path("id").asText();
                scenariosWithoutRequirement.add(scenarioId);
                if (isReviewRequired(scenario)) reviewOnlyScenariosWithoutRequirement.add(scenarioId);
            }
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
            String testCaseId = testCase.path("id").asText();
            for (JsonNode id : testCase.path("testDataIds")) {
                String testDataId = id.asText();
                JsonNode data = testData.get(testDataId);
                if (data == null) {
                    testCaseLinksToMissingTestData.add(testCaseId + " -> " + testDataId);
                } else if (!containsText(data.path("testCaseIds"), testCaseId)) {
                    testCaseDataLinkMismatches.add(testCaseId + " -> " + testDataId);
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
                    JsonNode linkedDataIds = testCases.get(testCaseId).path("testDataIds");
                    if (linkedDataIds.isArray()
                            && !containsText(linkedDataIds, data.path("id").asText())) {
                        testCaseDataLinkMismatches.add(testCaseId + " -> " + data.path("id").asText());
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
        for (String collection : List.of("businessRequirements", "testScenarios", "testCases", "testData")) {
            Map<String, Map<String, Integer>> statusCountsByField = new TreeMap<>();
            int reviewRequiredCount = 0;
            for (JsonNode artifact : root.path(collection)) {
                for (String field : List.of("status", "trainingStatus", "expectedValidation", "readiness")) {
                    String value = artifact.path(field).asText();
                    if (!value.isBlank()) {
                        statusCountsByField.computeIfAbsent(field, ignored -> new TreeMap<>())
                                .merge(value, 1, Integer::sum);
                    }
                }
                if (isReviewRequired(artifact)) reviewRequiredCount++;
            }
            artifactStatusCountsByType.put(collection, statusCountsByField);
            reviewRequiredArtifactsByType.put(collection, reviewRequiredCount);
            reviewRequiredArtifactCount += reviewRequiredCount;
        }

        ObjectNode result = mapper.createObjectNode();
        result.put("artifact", "all-test-solution-br-ts-tc-td-chain-validation");
        result.put("input", input.toString());
        result.put("inputValid", inputErrors.isEmpty());
        result.set("inputErrors", mapper.valueToTree(inputErrors));
        result.put("businessRequirements", requirements.size());
        result.put("testScenarios", scenarios.size());
        result.put("testCases", testCases.size());
        result.put("testData", testData.size());
        result.put("requirementsWithoutScenario", requirementsWithoutScenario.size());
        result.put("scenariosWithoutRequirement", scenariosWithoutRequirement.size());
        result.put("reviewOnlyScenariosWithoutRequirement", reviewOnlyScenariosWithoutRequirement.size());
        result.put("scenariosWithoutTestCase", scenariosWithoutTestCase.size());
        result.put("testCasesWithoutData", testCasesWithoutData.size());
        result.put("scenarioLinksToMissingRequirements", scenarioLinksToMissingRequirements.size());
        result.put("testCaseLinksToMissingScenarios", testCaseLinksToMissingScenarios.size());
        result.put("testCaseLinksToMissingTestData", testCaseLinksToMissingTestData.size());
        result.put("testDataLinksToMissingTestCases", testDataLinksToMissingTestCases.size());
        result.put("testCaseDataLinkMismatches", testCaseDataLinkMismatches.size());
        result.put("unlinkedTestDataCandidates",
            root.path("provenance").path("unlinkedTestDataCandidates").size());
        result.put("testCasesWithExecutableReadinessData", testCasesWithExecutableReadinessData.size());
        result.put("requirementsWithExecutableReadinessDataChain", requirementsWithExecutableReadinessDataChain.size());
        result.set("testDataReadinessCounts", mapper.valueToTree(testDataReadinessCounts));
        result.put("reviewRequiredArtifactCount", reviewRequiredArtifactCount);
        result.set("reviewRequiredArtifactsByType", mapper.valueToTree(reviewRequiredArtifactsByType));
        result.set("artifactStatusCountsByType", mapper.valueToTree(artifactStatusCountsByType));
        result.put("duplicateArtifactIds", duplicateIds.size());
        result.put("fullyLinkedRequirements", requirements.size() - requirementsWithoutScenario.size());
        result.put("fullyLinkedTestCases", testCases.size() - testCasesWithoutData.size());
        boolean structurallyComplete = inputErrors.isEmpty()
            && requirementsWithoutScenario.isEmpty() && scenariosWithoutTestCase.isEmpty()
            && testCasesWithoutData.isEmpty() && scenarioLinksToMissingRequirements.isEmpty()
            && testCaseLinksToMissingScenarios.isEmpty() && testCaseLinksToMissingTestData.isEmpty()
            && testDataLinksToMissingTestCases.isEmpty() && testCaseDataLinkMismatches.isEmpty()
            && duplicateIds.isEmpty();
        result.put("structurallyComplete", structurallyComplete);
        result.put("hasReviewPlaceholders", reviewRequiredArtifactCount > 0
            || root.path("provenance").path("unlinkedTestDataCandidates").size() > 0);
        result.put("executionReady", false);
        result.put("executionReadinessReason", "Link integrity alone cannot certify canonical anchor continuity, validated payloads or SME approval.");
        result.set("unlinkedRequirementIds", mapper.valueToTree(requirementsWithoutScenario));
        result.set("scenariosWithoutRequirementIds", mapper.valueToTree(scenariosWithoutRequirement));
        result.set("reviewOnlyScenariosWithoutRequirementIds",
            mapper.valueToTree(reviewOnlyScenariosWithoutRequirement));
        result.set("unlinkedScenarioIds", mapper.valueToTree(scenariosWithoutTestCase));
        result.set("unlinkedTestCaseIds", mapper.valueToTree(testCasesWithoutData));
        result.set("scenarioLinksToMissingRequirementsList", mapper.valueToTree(scenarioLinksToMissingRequirements));
        result.set("testCaseLinksToMissingScenariosList", mapper.valueToTree(testCaseLinksToMissingScenarios));
        result.set("testCaseLinksToMissingTestDataList", mapper.valueToTree(testCaseLinksToMissingTestData));
        result.set("testDataLinksToMissingTestCasesList", mapper.valueToTree(testDataLinksToMissingTestCases));
        result.set("testCaseDataLinkMismatchesList", mapper.valueToTree(testCaseDataLinkMismatches));
        result.set("duplicateArtifactIdsList", mapper.valueToTree(duplicateIds));

        Path output = reviewRoot.resolve("all-test-solution-chain-validation.json");
        Files.createDirectories(reviewRoot);
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), result);
        Files.writeString(reviewRoot.resolve("all-test-solution-chain-validation.md"), markdown(result));
        System.out.printf("BR=%d TS=%d TC=%d TD=%d BR_TS_gaps=%d TS_TC_gaps=%d TC_TD_gaps=%d "
                        + "reviewRequired=%d structurallyComplete=%s executionReady=%s%n",
                requirements.size(), scenarios.size(), testCases.size(), testData.size(),
                requirementsWithoutScenario.size(), scenariosWithoutTestCase.size(), testCasesWithoutData.size(),
                reviewRequiredArtifactCount, structurallyComplete,
                result.path("executionReady").asBoolean());
    }

    private static Map<String, JsonNode> index(JsonNode values) {
        Map<String, JsonNode> result = new LinkedHashMap<>();
        if (!values.isArray()) return result;
        for (JsonNode value : values) result.putIfAbsent(value.path("id").asText(), value);
        return result;
    }

    private static Set<String> findDuplicateIds(JsonNode root) {
        Set<String> seen = new HashSet<>();
        Set<String> duplicate = new HashSet<>();
        for (String collection : List.of("businessRequirements", "testScenarios", "testCases", "testData")) {
            JsonNode values = root.path(collection);
            if (!values.isArray()) continue;
            for (JsonNode value : values) {
                String id = value.path("id").asText();
                if (!id.isBlank() && !seen.add(id)) duplicate.add(id);
            }
        }
        return duplicate;
    }

    private static List<String> validateCollections(JsonNode root) {
        List<String> errors = new ArrayList<>();
        if (!root.isObject()) {
            errors.add("package root must be a JSON object");
            return errors;
        }
        validateCollection(root, "businessRequirements", List.of(), false, errors);
        validateCollection(root, "testScenarios", List.of("requirementIds"), true, errors);
        validateCollection(root, "testCases", List.of("scenarioIds"), false, errors);
        validateCollection(root, "testData", List.of("testCaseIds"), false, errors);
        return errors;
    }

    private static void validateCollection(
            JsonNode root, String collection, List<String> requiredLinks,
            boolean allowReviewOnly, List<String> errors) {
        JsonNode artifacts = root.path(collection);
        if (!artifacts.isArray()) {
            errors.add(collection + " must be an array");
            return;
        }
        if (artifacts.isEmpty()) errors.add(collection + " must not be empty");
        for (int index = 0; index < artifacts.size(); index++) {
            JsonNode artifact = artifacts.get(index);
            String location = collection + "[" + index + "]";
            if (!artifact.isObject()) {
                errors.add(location + " must be an object");
                continue;
            }
            if (!artifact.path("id").isTextual() || artifact.path("id").asText().isBlank()) {
                errors.add(location + ".id must be a non-empty string");
            }
            String artifactLocation = location + " (" + artifact.path("id").asText() + ")";
            for (String linkField : requiredLinks) {
                JsonNode links = artifact.path(linkField);
                if (!links.isArray()) {
                    errors.add(location + "." + linkField + " must be a non-empty array");
                    continue;
                }
                if (links.isEmpty() && !(allowReviewOnly && isReviewRequired(artifact))) {
                    errors.add(artifactLocation + "." + linkField
                        + " must be non-empty unless the artifact is explicitly review-required");
                }
                for (int linkIndex = 0; linkIndex < links.size(); linkIndex++) {
                    if (!links.get(linkIndex).isTextual() || links.get(linkIndex).asText().isBlank()) {
                        errors.add(location + "." + linkField + "[" + linkIndex + "] must be a non-empty string");
                    }
                }
            }
        }
    }

    private static boolean containsText(JsonNode values, String expected) {
        if (!values.isArray()) return false;
        for (JsonNode value : values) {
            if (value.isTextual() && expected.equals(value.asText())) return true;
        }
        return false;
    }

    private static boolean isReviewRequired(JsonNode artifact) {
        for (String field : List.of("status", "trainingStatus", "expectedValidation", "readiness")) {
            String value = artifact.path(field).asText();
            if (value.contains("REVIEW_REQUIRED")
                    || ("trainingStatus".equals(field) && value.endsWith("_REQUIRED"))) return true;
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
                + "| TS without BR links | " + result.path("scenariosWithoutRequirement").asInt() + " |\n"
                + "| Review-only TS without BR links | "
                + result.path("reviewOnlyScenariosWithoutRequirement").asInt() + " |\n"
                + "| TS -> TC gaps | " + result.path("scenariosWithoutTestCase").asInt() + " |\n"
                + "| TC -> TD gaps | " + result.path("testCasesWithoutData").asInt() + " |\n"
                + "| TS -> missing BR references | " + result.path("scenarioLinksToMissingRequirements").asInt() + " |\n"
                + "| TC -> missing TS references | " + result.path("testCaseLinksToMissingScenarios").asInt() + " |\n"
                + "| TC -> missing TD references | " + result.path("testCaseLinksToMissingTestData").asInt() + " |\n"
                + "| TD -> missing TC references | " + result.path("testDataLinksToMissingTestCases").asInt() + " |\n"
                + "| TC/TD link mismatches | " + result.path("testCaseDataLinkMismatches").asInt() + " |\n"
                + "| Unlinked TD candidates (outside canonical chain) | " + result.path("unlinkedTestDataCandidates").asInt() + " |\n"
                + "| TD readiness counts | " + result.path("testDataReadinessCounts") + " |\n"
                + "| Review-required artifacts | " + result.path("reviewRequiredArtifactCount").asInt() + " |\n"
                + "| Review-required artifacts by type | " + result.path("reviewRequiredArtifactsByType") + " |\n"
                + "| Lifecycle statuses by type and field | "
                + result.path("artifactStatusCountsByType") + " |\n"
                + "| Test cases linked to EXECUTABLE-readiness TDs | " + result.path("testCasesWithExecutableReadinessData").asInt() + " |\n"
                + "| BRs with linked EXECUTABLE-readiness TD chain | " + result.path("requirementsWithExecutableReadinessDataChain").asInt() + " |\n"
                + "| Duplicate IDs | " + result.path("duplicateArtifactIds").asInt() + " |\n"
                + "| Input valid | " + result.path("inputValid").asBoolean() + " |\n"
                + "| Input errors | " + result.path("inputErrors").size() + " |\n"
                + "| Structural complete | " + result.path("structurallyComplete").asBoolean() + " |\n"
                + "| Review placeholders | " + result.path("hasReviewPlaceholders").asBoolean() + " |\n\n"
                + "Execution ready: **" + result.path("executionReady").asBoolean() + "**. "
                + result.path("executionReadinessReason").asText() + "\n";
    }
}
