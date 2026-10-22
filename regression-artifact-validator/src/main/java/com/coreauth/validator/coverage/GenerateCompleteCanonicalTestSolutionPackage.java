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

        List<JsonNode> catalogRules = loadRules(mapper);
        Map<String, JsonNode> rulesById = new LinkedHashMap<>();
        for (JsonNode rule : catalogRules) rulesById.put(rule.path("ruleId").asText(), rule);
        Map<String, JsonNode> requirementsById = ruleRequirements(catalogRules, mapper);
        Map<String, JsonNode> scenariosById = index(existing.path("testScenarios"));
        Map<String, JsonNode> testCasesById = index(existing.path("testCases"));
        List<JsonNode> unlinkedTestDataCandidates = new ArrayList<>(
                list(existing.path("provenance").path("unlinkedTestDataCandidates")));
        List<JsonNode> canonicalTestData = new ArrayList<>();
        for (JsonNode data : list(existing.path("testData"))) {
            if (!CanonicalTestDataLinkPolicy.hasCanonicalTestCaseLinks(data)) {
                unlinkedTestDataCandidates.add(CanonicalTestDataLinkPolicy.unlinkedCandidate(
                        data, "all-test-solution-br-ts-tc-td-training-package.json", mapper));
                continue;
            }
            if (data instanceof ObjectNode objectData) {
                CanonicalTestDataLinkPolicy.requireExplicitReadiness(objectData);
            }
            canonicalTestData.add(data);
        }
        List<JsonNode> scenarios = new ArrayList<>(list(existing.path("testScenarios")));
        List<JsonNode> testCases = new ArrayList<>(list(existing.path("testCases")));
        List<JsonNode> testData = canonicalTestData;
        List<JsonNode> preservedRequirements = new ArrayList<>();
        int placeholderScenarios = 0;
        int placeholderTestCases = 0;
        int placeholderTestData = 0;

        int independentRuleCount = requirementsById.size();
        Map<String, JsonNode> existingRequirements = index(existing.path("businessRequirements"));
        for (Map.Entry<String, JsonNode> entry : existingRequirements.entrySet()) {
            requirementsById.putIfAbsent(entry.getKey(), entry.getValue());
        }

        Path lifecyclePackageFile = Atl105Paths.testJson().resolve("segment-100-multistep-flow-catalog.json");
        if (Files.isRegularFile(lifecyclePackageFile)) {
            JsonNode lifecyclePackage = mapper.readTree(lifecyclePackageFile.toFile());
            for (JsonNode requirement : list(lifecyclePackage.path("businessRequirements"))) {
                String requirementId = requirement.path("id").asText();
                JsonNode existingRequirement = requirementsById.putIfAbsent(requirementId, requirement);
                if (existingRequirement == null) {
                    preservedRequirements.add(requirement);
                } else if (!existingRequirement.equals(requirement)) {
                    throw new IllegalStateException("Conflicting lifecycle business requirement " + requirementId);
                }
            }
            for (JsonNode scenario : lifecyclePackage.path("testScenarios")) {
                scenariosById.putIfAbsent(scenario.path("id").asText(), scenario);
                if (!containsId(scenarios, scenario.path("id").asText())) scenarios.add(scenario);
            }
            for (JsonNode testCase : lifecyclePackage.path("testCases")) {
                testCasesById.putIfAbsent(testCase.path("id").asText(), testCase);
                if (!containsId(testCases, testCase.path("id").asText())) testCases.add(testCase);
            }
            for (JsonNode data : lifecyclePackage.path("testData")) {
                if (!CanonicalTestDataLinkPolicy.hasCanonicalTestCaseLinks(data)) {
                    unlinkedTestDataCandidates.add(CanonicalTestDataLinkPolicy.unlinkedCandidate(
                            data, "segment-100-multistep-flow-catalog.json", mapper));
                    continue;
                }
                if (data instanceof ObjectNode objectData) {
                    CanonicalTestDataLinkPolicy.requireExplicitReadiness(objectData);
                }
                if (!containsId(testData, data.path("id").asText())) testData.add(data);
            }
        }

        Path accountPackageFile = Atl105Paths.testJson().resolve(GenerateSegment100AccountEntryPackage.FILE_NAME);
        JsonNode accountPackage = mapper.readTree(accountPackageFile.toFile());
        var accountReadiness = new com.coreauth.validator.canonical.CanonicalTraceabilityValidator()
                .validateForExecution(mapper.treeToValue(accountPackage,
                        com.coreauth.validator.canonical.CanonicalArtifactPackage.class),
                        Atl105Paths.root());
        if (!accountReadiness.isValid()) {
            throw new IllegalStateException("Element 2/12 package is not execution-ready: " + accountReadiness.errors());
        }
        for (JsonNode requirement : accountPackage.path("businessRequirements")) {
            String id = requirement.path("id").asText();
            JsonNode previous = requirementsById.putIfAbsent(id, requirement);
            if (previous != null && !previous.equals(requirement)) {
                throw new IllegalStateException("Conflicting Element 2/12 business requirement " + id);
            }
        }
        mergeExecutionArtifacts(scenarios, accountPackage.path("testScenarios"));
        mergeExecutionArtifacts(testCases, accountPackage.path("testCases"));
        mergeExecutionArtifacts(testData, accountPackage.path("testData"));

        List<JsonNode> completeRequirements = new ArrayList<>(requirementsById.values());
        for (JsonNode br : completeRequirements) {
            String brId = br.path("id").asText();
            List<JsonNode> linkedScenarios = scenariosForRequirement(scenarios, brId);
            if (linkedScenarios.isEmpty()) {
                ObjectNode scenario = draftScenarioProposal(br, mapper);
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
                        data.put("readiness", "REVIEW_REQUIRED");
                        data.put("expectedValidation", "REVIEW_REQUIRED");
                        data.put("trainingStatus", "TEST_DATA_DERIVATION_REQUIRED");
                        data.putObject("payload").put("fixtureStatus", "SME_FIXTURE_REQUIRED");
                        testData.add(data);
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
            data.put("readiness", "REVIEW_REQUIRED");
            data.put("expectedValidation", "REVIEW_REQUIRED");
            data.put("trainingStatus", "TEST_DATA_DERIVATION_REQUIRED");
            data.putObject("payload").put("fixtureStatus", "SME_FIXTURE_REQUIRED");
            testData.add(data);
            placeholderTestData++;
        }

        ObjectNode draftPackage = mapper.createObjectNode();
        ObjectNode draftManifest = draftPackage.putObject("manifest");
        draftManifest.put("packageId", "ATL105-INDEPENDENT-TEST-SOLUTION-PLACEHOLDER-DRAFTS");
        draftManifest.put("specification", "ATL105");
        draftManifest.put("specificationVersion", "2026-3");
        draftManifest.put("authority", "INDEPENDENT_TEST_SOLUTION");
        draftManifest.put("status", "SOURCE_ANCHORED_DRAFTS_REVIEW_REQUIRED");
        draftManifest.put("aiArtifactsIncluded", false);
        draftManifest.put("executionReady", false);
        draftManifest.put("sourcePackage", "complete-all-test-solution-br-ts-tc-td-package.json");
        ArrayNode testCaseDrafts = draftPackage.putArray("testCaseDrafts");
        ArrayNode testDataDrafts = draftPackage.putArray("testDataDrafts");
        Map<String, JsonNode> scenarioIndex = index(array(scenarios, mapper));
        for (JsonNode value : testCases) {
            if (!(value instanceof ObjectNode testCase)
                    || !"TC_DERIVATION_REQUIRED".equals(testCase.path("trainingStatus").asText())) {
                continue;
            }
            JsonNode scenario = firstLinked(scenarioIndex, testCase.path("scenarioIds"));
            JsonNode requirement = firstLinked(requirementsById, scenario.path("requirementIds"));
            JsonNode rule = sourceRule(testCase, requirement, rulesById);
            ObjectNode draft = testCaseDraftDefinition(testCase, scenario, requirement, rule, mapper);
            testCaseDrafts.add(draft);
        }

        Map<String, JsonNode> testCaseIndex = index(array(testCases, mapper));
        for (JsonNode value : testData) {
            if (!(value instanceof ObjectNode data)
                    || !"TEST_DATA_DERIVATION_REQUIRED".equals(data.path("trainingStatus").asText())) {
                continue;
            }
            JsonNode testCase = firstLinked(testCaseIndex, data.path("testCaseIds"));
            JsonNode scenario = firstLinked(scenarioIndex, testCase.path("scenarioIds"));
            JsonNode requirement = firstLinked(requirementsById, scenario.path("requirementIds"));
            JsonNode rule = sourceRule(data, requirement, rulesById);
            ObjectNode draft = testDataDraftDefinition(data, testCase, requirement, rule, mapper);
            testDataDrafts.add(draft);
        }
        if (testCaseDrafts.size() != placeholderTestCases || testDataDrafts.size() != placeholderTestData) {
            throw new IllegalStateException("Every generated placeholder must receive a source-anchored draft definition.");
        }
        ObjectNode draftSummary = draftPackage.putObject("summary");
        draftSummary.put("sourceAnchoredDraftTestCases", testCaseDrafts.size());
        draftSummary.put("sourceAnchoredDraftTestData", testDataDrafts.size());
        draftSummary.put("executionReady", false);
        draftSummary.put("reason", "These are review drafts only; exact applicability, expected outcomes and fixture values require source or SME confirmation.");

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
        ObjectNode provenance = output.putObject("provenance");
        provenance.set("unlinkedTestDataCandidates", array(unlinkedTestDataCandidates, mapper));
        provenance.putArray("localExecutionPackages").addObject()
            .put("file", GenerateSegment100AccountEntryPackage.FILE_NAME)
            .put("scope", "Element 2/12 source-bound local predicates; not complete wire, host or semantic certification");
        ObjectNode summary = output.putObject("summary");
        summary.put("independentRuleDenominator", independentRuleCount);
        summary.put("businessRequirements", completeRequirements.size());
        summary.put("preservedLifecycleBusinessRequirements", preservedRequirements.size());
        summary.put("testScenarios", scenarios.size());
        summary.put("testCases", testCases.size());
        summary.put("testData", testData.size());
        summary.put("unlinkedTestDataCandidates", unlinkedTestDataCandidates.size());
        summary.put("syntheticReviewFixtureDrafts", unlinkedTestDataCandidates.stream()
            .filter(candidate -> candidate.path("fixtureDraftCreated").asBoolean()).count());
        summary.put("placeholderScenarios", placeholderScenarios);
        summary.put("reviewRequiredScenarioProposals", placeholderScenarios);
        summary.put("placeholderTestCases", placeholderTestCases);
        summary.put("placeholderTestData", placeholderTestData);
        summary.put("sourceAnchoredDraftTestCases", testCaseDrafts.size());
        summary.put("sourceAnchoredDraftTestData", testDataDrafts.size());
        summary.put("executionReadyElement2And12Requirements", accountPackage.path("businessRequirements").size());
        summary.put("executionReadyElement2And12TestCases", accountPackage.path("testCases").size());
        summary.put("executableElement2And12TestData", accountPackage.path("testData").size());
        summary.put("executionReady", false);
        summary.put("reason", "Every independent BR has a structural BR->TS->TC->TD chain; placeholders remain REVIEW_REQUIRED until independently derived, reviewed, and fixture-backed.");

        Path outputFile = outputRoot.resolve("complete-all-test-solution-br-ts-tc-td-package.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile.toFile(), output);
        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("test-solution-placeholder-drafts.json").toFile(), draftPackage);
        Files.writeString(outputRoot.resolve("complete-all-test-solution-br-ts-tc-td-summary.md"), markdown(summary));
        System.out.printf("BR=%d TS=%d TC=%d TD=%d placeholders=%d/%d/%d output=%s%n",
                independentRuleCount, scenarios.size(), testCases.size(), testData.size(),
                placeholderScenarios, placeholderTestCases, placeholderTestData, outputFile);
    }

    static void mergeExecutionArtifacts(List<JsonNode> target, JsonNode additions) {
        Map<String, JsonNode> indexed = new LinkedHashMap<>();
        for (JsonNode artifact : target) indexed.put(artifact.path("id").asText(), artifact);
        for (JsonNode artifact : additions) {
            String id = artifact.path("id").asText();
            JsonNode previous = indexed.putIfAbsent(id, artifact);
            if (previous != null && !previous.equals(artifact)) {
                throw new IllegalStateException("Conflicting execution artifact " + id);
            }
            if (previous == null) target.add(artifact);
        }
    }

    static Map<String, JsonNode> ruleRequirements(List<JsonNode> rules, ObjectMapper mapper) {
        Map<String, JsonNode> requirements = new LinkedHashMap<>();
        for (JsonNode rule : rules) {
            JsonNode anchor = rule.path("sourceAnchor");
            String ruleId = rule.path("ruleId").asText();
            if (ruleId.isBlank() || !anchor.isObject()) {
                throw new IllegalStateException("Catalog rule needs a ruleId and sourceAnchor: " + rule);
            }
            ObjectNode requirement = mapper.createObjectNode();
            String requirementId = "BR-RULE-" + ruleId;
            requirement.put("id", requirementId);
            requirement.put("title", rule.path("title").asText());
            requirement.put("status", "DRAFT_REVIEW_REQUIRED");
            requirement.set("sourceAnchors", arrayWith(anchor, mapper));
            requirement.put("testSolutionEvidence", "INDEPENDENT_RULE_CATALOG");
            if (requirements.putIfAbsent(requirementId, requirement) != null) {
                throw new IllegalStateException("Duplicate rule ID: " + ruleId);
            }
        }
        return requirements;
    }

    static ObjectNode draftScenarioProposal(JsonNode requirement, ObjectMapper mapper) {
        String requirementId = requirement.path("id").asText();
        String title = requirement.path("title").asText("").trim();
        ObjectNode scenario = mapper.createObjectNode();
        scenario.put("id", "TS-REVIEW-" + safe(requirementId));
        scenario.put("intent", title.isBlank()
                ? "Derive a scenario for independent Test BR " + requirementId
                : "Draft scenario proposal to exercise: " + title);
        scenario.putArray("requirementIds").add(requirementId);
        JsonNode anchors = requirement.path("sourceAnchors");
        if (anchors.isArray() && !anchors.isEmpty()) {
            scenario.set("sourceAnchors", anchors.deepCopy());
        } else if (requirement.path("sourceAnchor").isObject()) {
            scenario.set("sourceAnchors", arrayWith(requirement.path("sourceAnchor"), mapper));
        } else {
            scenario.set("sourceAnchors", mapper.createArrayNode());
            scenario.put("scenarioDefinitionStatus", "DRAFT_REVIEW_REQUIRED_MISSING_SOURCE_ANCHOR");
        }
        scenario.put("status", "REVIEW_REQUIRED");
        scenario.put("trainingStatus", "TS_DERIVATION_REQUIRED");
        if (!scenario.has("scenarioDefinitionStatus")) {
            scenario.put("scenarioDefinitionStatus", "DRAFT_FROM_TEST_BR_TITLE_AND_SOURCE_ANCHOR");
        }
        scenario.putArray("scenarioDefinitionGaps")
                .add("APPLICABILITY_AND_PRECONDITIONS_NOT_DERIVED")
                .add("TRANSACTION_OR_OPERATION_CONTEXT_NOT_DERIVED")
                .add("POSITIVE_NEGATIVE_AND_BOUNDARY_CASES_NOT_DERIVED")
                .add("EXPECTED_OUTCOME_NOT_DERIVED");
        return scenario;
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

    private static Map<String, JsonNode> index(JsonNode values) {
        Map<String, JsonNode> result = new LinkedHashMap<>();
        for (JsonNode value : list(values)) result.putIfAbsent(value.path("id").asText(), value);
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

    private static String safe(String value) {
        return value.replaceAll("[^A-Za-z0-9]+", "-");
    }

    private static String markdown(ObjectNode summary) {
        return "# Complete Canonical Test Solution Package\n\n"
                + "Every independent rule-catalog BR has a structural BR -> TS -> TC -> TD chain. "
                + "Scenario proposals are derived only from independent BR titles and source anchors. "
                + "Source-anchored TC and TD draft definitions are in `test-solution-placeholder-drafts.json`; "
                + "they remain REVIEW_REQUIRED until applicability, expected outcomes, and fixture values are independently derived and reviewed. They do not imply execution readiness.\n\n"
                + "| Metric | Count |\n|---|---:|\n"
                + "| Independent BR denominator | " + summary.path("independentRuleDenominator").asInt() + " |\n"
                + "| BR | " + summary.path("businessRequirements").asInt() + " |\n"
                + "| TS | " + summary.path("testScenarios").asInt() + " |\n"
                + "| TC | " + summary.path("testCases").asInt() + " |\n"
                + "| TD | " + summary.path("testData").asInt() + " |\n"
                + "| Unlinked TD candidates (excluded from canonical chain) | " + summary.path("unlinkedTestDataCandidates").asInt() + " |\n"
                + "| Synthetic review fixture drafts | " + summary.path("syntheticReviewFixtureDrafts").asInt() + " |\n"
                + "| Review-required scenario proposals | " + summary.path("reviewRequiredScenarioProposals").asInt() + " |\n"
                + "| Placeholder TC | " + summary.path("placeholderTestCases").asInt() + " |\n"
                + "| Placeholder TD | " + summary.path("placeholderTestData").asInt() + " |\n"
                + "| Source-anchored TC draft definitions | " + summary.path("sourceAnchoredDraftTestCases").asInt() + " |\n"
                + "| Source-anchored TD fixture designs | " + summary.path("sourceAnchoredDraftTestData").asInt() + " |\n"
                + "| Element 2/12 execution-ready local BRs | " + summary.path("executionReadyElement2And12Requirements").asInt() + " |\n"
                + "| Element 2/12 executable local cases | " + summary.path("executionReadyElement2And12TestCases").asInt() + " |\n"
                + "| Element 2/12 executable synthetic fixtures | " + summary.path("executableElement2And12TestData").asInt() + " |\n"
                + "| Execution ready | " + summary.path("executionReady").asBoolean() + " |\n";
    }

    private static JsonNode firstLinked(Map<String, JsonNode> records, JsonNode links) {
        if (links.isArray()) {
            for (JsonNode link : links) {
                JsonNode record = records.get(link.asText());
                if (record != null) return record;
            }
        }
        return com.fasterxml.jackson.databind.node.MissingNode.getInstance();
    }

    private static JsonNode sourceRule(JsonNode artifact, JsonNode requirement, Map<String, JsonNode> rulesById) {
        for (JsonNode anchor : list(artifact.path("sourceAnchors"))) {
            JsonNode rule = rulesById.get(anchor.path("rule").asText());
            if (rule != null) return rule;
        }
        String requirementId = requirement.path("id").asText();
        if (requirementId.startsWith("BR-RULE-")) {
            return rulesById.getOrDefault(requirementId.substring("BR-RULE-".length()),
                    com.fasterxml.jackson.databind.node.MissingNode.getInstance());
        }
        return com.fasterxml.jackson.databind.node.MissingNode.getInstance();
    }

    static ObjectNode testCaseDraftDefinition(
            JsonNode testCase, JsonNode scenario, JsonNode requirement, JsonNode rule, ObjectMapper mapper) {
        ObjectNode definition = mapper.createObjectNode();
        definition.put("testCaseId", testCase.path("id").asText());
        definition.set("scenarioIds", testCase.path("scenarioIds").deepCopy());
        definition.set("requirementIds", scenario.path("requirementIds").deepCopy());
        definition.put("status", "DRAFT_SOURCE_ANCHORED_REVIEW_REQUIRED");
        definition.put("objective", firstText(rule.path("title"), requirement.path("title"), scenario.path("intent")));
        if (rule.isObject()) {
            definition.put("ruleClass", rule.path("class").asText("UNCLASSIFIED"));
            definition.put("severity", rule.path("severity").asText("REVIEW_REQUIRED"));
        }
        definition.set("sourceAnchors", testCase.path("sourceAnchors").deepCopy());
        definition.putArray("procedure")
                .add("Establish a baseline satisfying all independently confirmed applicable requirements.")
                .add("Vary only the condition identified by the cited source rule.")
                .add("Observe and compare the result with the normative rule and approved Test Solution oracle.");
        definition.putArray("reviewGaps")
                .add("APPLICABILITY_AND_TRANSACTION_CONTEXT_REQUIRE_CONFIRMATION")
                .add("EXACT_PASS_FAIL_ORACLE_REQUIRES_SOURCE_OR_SME_CONFIRMATION")
                .add("TEST_STEPS_AND_BOUNDARY_VARIANTS_REQUIRE_INDEPENDENT_REVIEW");
        return definition;
    }

    static ObjectNode testDataDraftDefinition(
            JsonNode data, JsonNode testCase, JsonNode requirement, JsonNode rule, ObjectMapper mapper) {
        ObjectNode definition = mapper.createObjectNode();
        definition.put("testDataId", data.path("id").asText());
        definition.set("testCaseIds", data.path("testCaseIds").deepCopy());
        definition.put("status", "DRAFT_SOURCE_ANCHORED_REVIEW_REQUIRED");
        definition.put("purpose", firstText(rule.path("title"), requirement.path("title"),
                "Provide fixture data for " + testCase.path("id").asText()));
        if (rule.isObject()) {
            definition.put("ruleClass", rule.path("class").asText("UNCLASSIFIED"));
            definition.put("severity", rule.path("severity").asText("REVIEW_REQUIRED"));
        }
        JsonNode anchors = data.path("sourceAnchors");
        definition.set("sourceAnchors", anchors.deepCopy());
        ObjectNode target = definition.putObject("sourceTarget");
        if (anchors.isArray() && !anchors.isEmpty()) {
            JsonNode anchor = anchors.get(0);
            for (String field : List.of("specification", "version", "section", "segment", "element", "rule")) {
                if (anchor.hasNonNull(field)) target.set(field, anchor.get(field).deepCopy());
            }
        }
        definition.putArray("construction")
                .add("Use a baseline satisfying all independently confirmed applicable requirements.")
                .add("Represent the source-anchored condition without changing unrelated inputs.");
        definition.put("fixtureValuesStatus", "SOURCE_OR_SME_DERIVATION_REQUIRED");
        definition.putArray("reviewGaps")
                .add("EXACT_FIELD_MAPPING_AND_ENCODING_REQUIRE_SOURCE_CONFIRMATION")
                .add("BASELINE_AND_CONTROLLED_VALUES_REQUIRE_INDEPENDENT_DERIVATION")
                .add("EXPECTED_VALIDATION_ORACLE_REQUIRES_SOURCE_OR_SME_CONFIRMATION");
        return definition;
    }

    private static String firstText(JsonNode... values) {
        for (JsonNode value : values) {
            String text = value.asText("").trim();
            if (!text.isEmpty()) return text;
        }
        return "Source-anchored test definition requires review.";
    }
}
