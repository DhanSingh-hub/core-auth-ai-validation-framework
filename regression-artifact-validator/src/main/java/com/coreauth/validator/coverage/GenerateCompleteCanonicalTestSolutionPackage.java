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
import java.util.stream.Stream;

/** Completes the independent Test Solution BR denominator with explicit review-required TS/TC/TD placeholders. */
public final class GenerateCompleteCanonicalTestSolutionPackage {
    private GenerateCompleteCanonicalTestSolutionPackage() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path outputRoot = Atl105Paths.testOutput().resolve("test-solution-independent-review");
        Path aggregateFile = outputRoot.resolve("all-test-solution-br-ts-tc-td-training-package.json");
        JsonNode existing = mapper.readTree(aggregateFile.toFile());

        Map<String, JsonNode> requirementsByAnchor = new LinkedHashMap<>();
        Map<String, JsonNode> scenariosById = index(existing.path("testScenarios"));
        Map<String, JsonNode> testCasesById = index(existing.path("testCases"));
        Map<String, JsonNode> testDataById = index(existing.path("testData"));
        List<JsonNode> scenarios = new ArrayList<>(list(existing.path("testScenarios")));
        List<JsonNode> testCases = new ArrayList<>(list(existing.path("testCases")));
        List<JsonNode> testData = new ArrayList<>(list(existing.path("testData")));
        List<JsonNode> preservedRequirements = new ArrayList<>();
        int placeholderScenarios = 0;
        int placeholderTestCases = 0;
        int placeholderTestData = 0;

        List<JsonNode> rules = loadRules(mapper);
        for (JsonNode rule : rules) {
            JsonNode anchor = rule.path("sourceAnchor");
            String anchorKey = anchorKey(anchor);
            ObjectNode br = objectForAnchor(requirementsByAnchor.get(anchorKey), mapper);
            if (br == null) {
                br = mapper.createObjectNode();
                br.put("id", "BR-RULE-" + rule.path("ruleId").asText());
                br.put("title", rule.path("title").asText());
                br.put("status", "DRAFT_REVIEW_REQUIRED");
                br.set("sourceAnchors", arrayWith(anchor, mapper));
                br.put("testSolutionEvidence", "INDEPENDENT_RULE_CATALOG");
                requirementsByAnchor.put(anchorKey, br);
            }
        }

        Map<String, JsonNode> existingRequirements = indexByAnchor(existing.path("businessRequirements"));
        for (Map.Entry<String, JsonNode> entry : existingRequirements.entrySet()) {
            requirementsByAnchor.putIfAbsent(entry.getKey(), entry.getValue());
        }

        Path lifecyclePackageFile = Atl105Paths.testJson().resolve("segment-100-multistep-flow-catalog.json");
        if (Files.isRegularFile(lifecyclePackageFile)) {
            JsonNode lifecyclePackage = mapper.readTree(lifecyclePackageFile.toFile());
            preservedRequirements.addAll(list(lifecyclePackage.path("businessRequirements")));
            for (JsonNode scenario : lifecyclePackage.path("testScenarios")) {
                scenariosById.putIfAbsent(scenario.path("id").asText(), scenario);
                if (!containsId(scenarios, scenario.path("id").asText())) scenarios.add(scenario);
            }
            for (JsonNode testCase : lifecyclePackage.path("testCases")) {
                testCasesById.putIfAbsent(testCase.path("id").asText(), testCase);
                if (!containsId(testCases, testCase.path("id").asText())) testCases.add(testCase);
            }
            for (JsonNode data : lifecyclePackage.path("testData")) {
                testDataById.putIfAbsent(data.path("id").asText(), data);
                if (!containsId(testData, data.path("id").asText())) testData.add(data);
            }
        }

        List<JsonNode> completeRequirements = new ArrayList<>(requirementsByAnchor.values());
        completeRequirements.addAll(preservedRequirements);
        for (JsonNode br : completeRequirements) {
            String brId = br.path("id").asText();
            List<JsonNode> linkedScenarios = scenariosForRequirement(scenarios, brId);
            if (linkedScenarios.isEmpty()) {
                ObjectNode scenario = mapper.createObjectNode();
                scenario.put("id", "TS-REVIEW-" + safe(brId));
                scenario.putArray("requirementIds").add(brId);
                scenario.set("sourceAnchors", br.path("sourceAnchors"));
                scenario.put("status", "REVIEW_REQUIRED");
                scenario.put("trainingStatus", "TS_DERIVATION_REQUIRED");
                scenarios.add(scenario);
                scenariosById.put(scenario.path("id").asText(), scenario);
                linkedScenarios = List.of(scenario);
                placeholderScenarios++;
            }
            for (JsonNode scenario : linkedScenarios) {
                String scenarioId = scenario.path("id").asText();
                List<JsonNode> linkedCases = casesForScenario(testCases, scenarioId);
                if (linkedCases.isEmpty()) {
                    ObjectNode testCase = mapper.createObjectNode();
                    testCase.put("id", "TC-REVIEW-" + safe(scenarioId));
                    testCase.putArray("scenarioIds").add(scenarioId);
                    testCase.set("sourceAnchors", scenario.path("sourceAnchors"));
                    testCase.put("expectedOutcome", "REVIEW_REQUIRED");
                    testCase.put("status", "REVIEW_REQUIRED");
                    testCase.put("trainingStatus", "TC_DERIVATION_REQUIRED");
                    testCases.add(testCase);
                    testCasesById.put(testCase.path("id").asText(), testCase);
                    linkedCases = List.of(testCase);
                    placeholderTestCases++;
                }
                for (JsonNode testCase : linkedCases) {
                    String testCaseId = testCase.path("id").asText();
                    if (dataForCase(testData, testCaseId).isEmpty()) {
                        ObjectNode data = mapper.createObjectNode();
                        data.put("id", "TD-REVIEW-" + safe(testCaseId));
                        data.putArray("testCaseIds").add(testCaseId);
                        data.set("sourceAnchors", testCase.path("sourceAnchors"));
                        data.put("expectedValidation", "REVIEW_REQUIRED");
                        data.put("trainingStatus", "TEST_DATA_DERIVATION_REQUIRED");
                        data.putObject("payload").put("fixtureStatus", "SME_FIXTURE_REQUIRED");
                        testData.add(data);
                        testDataById.put(data.path("id").asText(), data);
                        placeholderTestData++;
                    }
                }
            }
        }

        for (JsonNode scenario : new ArrayList<>(scenarios)) {
            if (!casesForScenario(testCases, scenario.path("id").asText()).isEmpty()) continue;
            ObjectNode testCase = mapper.createObjectNode();
            String scenarioId = scenario.path("id").asText();
            testCase.put("id", "TC-REVIEW-" + safe(scenarioId));
            testCase.putArray("scenarioIds").add(scenarioId);
            testCase.set("sourceAnchors", scenario.path("sourceAnchors"));
            testCase.put("expectedOutcome", "REVIEW_REQUIRED");
            testCase.put("status", "REVIEW_REQUIRED");
            testCase.put("trainingStatus", "TC_DERIVATION_REQUIRED");
            testCases.add(testCase);
            placeholderTestCases++;
        }
        for (JsonNode testCase : new ArrayList<>(testCases)) {
            if (!dataForCase(testData, testCase.path("id").asText()).isEmpty()) continue;
            ObjectNode data = mapper.createObjectNode();
            data.put("id", "TD-REVIEW-" + safe(testCase.path("id").asText()));
            data.putArray("testCaseIds").add(testCase.path("id").asText());
            data.set("sourceAnchors", testCase.path("sourceAnchors"));
            data.put("expectedValidation", "REVIEW_REQUIRED");
            data.put("trainingStatus", "TEST_DATA_DERIVATION_REQUIRED");
            data.putObject("payload").put("fixtureStatus", "SME_FIXTURE_REQUIRED");
            testData.add(data);
            placeholderTestData++;
        }

        ObjectNode output = mapper.createObjectNode();
        ObjectNode manifest = output.putObject("manifest");
        manifest.put("packageId", "ATL105-COMPLETE-INDEPENDENT-TEST-SOLUTION-BR-TS-TC-TD");
        manifest.put("specification", "ATL105");
        manifest.put("specificationVersion", "2026-3");
        manifest.put("authority", "INDEPENDENT_TEST_SOLUTION");
        manifest.put("status", "COMPLETE_STRUCTURE_REVIEW_REQUIRED");
        manifest.put("aiArtifactsIncluded", false);
        manifest.put("canonicalChain", "businessRequirements -> testScenarios -> testCases -> testData");
        output.set("businessRequirements", array(completeRequirements, mapper));
        output.set("testScenarios", array(scenarios, mapper));
        output.set("testCases", array(testCases, mapper));
        output.set("testData", array(testData, mapper));
        output.putArray("requirementCrosswalk");
        ObjectNode summary = output.putObject("summary");
        summary.put("independentRuleDenominator", requirementsByAnchor.size());
        summary.put("businessRequirements", completeRequirements.size());
        summary.put("preservedLifecycleBusinessRequirements", preservedRequirements.size());
        summary.put("testScenarios", scenarios.size());
        summary.put("testCases", testCases.size());
        summary.put("testData", testData.size());
        summary.put("placeholderScenarios", placeholderScenarios);
        summary.put("placeholderTestCases", placeholderTestCases);
        summary.put("placeholderTestData", placeholderTestData);
        summary.put("executionReady", false);
        summary.put("reason", "Every independent BR has a structural BR->TS->TC->TD chain; placeholders remain REVIEW_REQUIRED until independently derived, reviewed, and fixture-backed.");

        Path outputFile = outputRoot.resolve("complete-all-test-solution-br-ts-tc-td-package.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile.toFile(), output);
        Files.writeString(outputRoot.resolve("complete-all-test-solution-br-ts-tc-td-summary.md"), markdown(summary));
        System.out.printf("BR=%d TS=%d TC=%d TD=%d placeholders=%d/%d/%d output=%s%n",
                requirementsByAnchor.size(), scenarios.size(), testCases.size(), testData.size(),
                placeholderScenarios, placeholderTestCases, placeholderTestData, outputFile);
    }

    private static List<JsonNode> loadRules(ObjectMapper mapper) throws Exception {
        List<JsonNode> rules = new ArrayList<>();
        Path root = Atl105Paths.docs().resolve(Path.of("specs", "kb"));
        try (Stream<Path> files = Files.walk(root)) {
            for (Path file : files.filter(value -> value.getFileName().toString().matches("segment-.*-rule-catalog\\.json")).sorted().toList()) {
                for (JsonNode rule : mapper.readTree(file.toFile()).path("rules")) rules.add(rule);
            }
        }
        return rules;
    }

    private static ObjectNode objectForAnchor(JsonNode value, ObjectMapper mapper) {
        return value != null && value.isObject() ? (ObjectNode) value : null;
    }

    private static Map<String, JsonNode> index(JsonNode values) {
        Map<String, JsonNode> result = new LinkedHashMap<>();
        for (JsonNode value : list(values)) result.putIfAbsent(value.path("id").asText(), value);
        return result;
    }

    private static Map<String, JsonNode> indexByAnchor(JsonNode values) {
        Map<String, JsonNode> result = new LinkedHashMap<>();
        for (JsonNode value : list(values)) {
            JsonNode anchor = value.path("sourceAnchors").isArray()
                    ? value.path("sourceAnchors").path(0) : value.path("sourceAnchors");
            result.putIfAbsent(anchorKey(anchor), value);
        }
        return result;
    }

    private static List<JsonNode> list(JsonNode values) {
        List<JsonNode> result = new ArrayList<>();
        if (values.isArray()) values.forEach(result::add);
        return result;
    }

    private static List<JsonNode> scenariosForRequirement(List<JsonNode> scenarios, String requirementId) {
        return scenarios.stream().filter(value -> contains(value.path("requirementIds"), requirementId)).toList();
    }

    private static List<JsonNode> casesForScenario(List<JsonNode> cases, String scenarioId) {
        return cases.stream().filter(value -> contains(value.path("scenarioIds"), scenarioId)).toList();
    }

    private static List<JsonNode> dataForCase(List<JsonNode> data, String testCaseId) {
        return data.stream().filter(value -> contains(value.path("testCaseIds"), testCaseId)).toList();
    }

    private static boolean containsId(List<JsonNode> values, String id) {
        return values.stream().anyMatch(value -> id.equals(value.path("id").asText()));
    }

    private static boolean contains(JsonNode values, String wanted) {
        if (!values.isArray()) return false;
        for (JsonNode value : values) if (wanted.equals(value.asText())) return true;
        return false;
    }

    private static ArrayNode array(Iterable<JsonNode> values, ObjectMapper mapper) {
        ArrayNode result = mapper.createArrayNode();
        values.forEach(result::add);
        return result;
    }

    private static ArrayNode arrayWith(JsonNode value, ObjectMapper mapper) {
        ArrayNode result = mapper.createArrayNode();
        result.add(value);
        return result;
    }

    private static String anchorKey(JsonNode anchor) {
        if (!anchor.isObject()) return "";
        return String.join("|", anchor.path("specification").asText(), anchor.path("version").asText(),
                anchor.path("section").asText(), anchor.path("segment").asText(),
                anchor.path("element").asText(), anchor.path("rule").asText());
    }

    private static String safe(String value) {
        return value.replaceAll("[^A-Za-z0-9]+", "-");
    }

    private static String markdown(ObjectNode summary) {
        return "# Complete Canonical Test Solution Package\n\n"
                + "Every independent rule-catalog BR has a structural BR -> TS -> TC -> TD chain. "
                + "Placeholder links remain REVIEW_REQUIRED and do not imply execution readiness.\n\n"
                + "| Metric | Count |\n|---|---:|\n"
                + "| Independent BR denominator | " + summary.path("independentRuleDenominator").asInt() + " |\n"
                + "| BR | " + summary.path("businessRequirements").asInt() + " |\n"
                + "| TS | " + summary.path("testScenarios").asInt() + " |\n"
                + "| TC | " + summary.path("testCases").asInt() + " |\n"
                + "| TD | " + summary.path("testData").asInt() + " |\n"
                + "| Placeholder TS | " + summary.path("placeholderScenarios").asInt() + " |\n"
                + "| Placeholder TC | " + summary.path("placeholderTestCases").asInt() + " |\n"
                + "| Placeholder TD | " + summary.path("placeholderTestData").asInt() + " |\n"
                + "| Execution ready | " + summary.path("executionReady").asBoolean() + " |\n";
    }
}
