package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public final class GenerateSegmentDl8AiArtifactCoverageReport {
    private static final Pattern ALPHANUMERIC = Pattern.compile("[A-Za-z0-9]{1,}");
    private static final Pattern NUMERIC = Pattern.compile("[0-9]{1,}");
    private static final Pattern THREE_DIGITS = Pattern.compile("[0-9]{3}");
    private final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        Path docs = Atl105Paths.docs().resolve(Path.of("specs", "kb", "segment-DL8", "coverage"));
        Path aiCatalog = Atl105Paths.testOutput().resolve(Path.of(
                "ai-artifacts", "coverage-reports", "supplied-ai-catalog", "supplied-ai-catalog-coverage.json"));
        Path aliasCrosswalk = Atl105Paths.root().resolve(Path.of(
                "contract", "segment-DL8-field-alias-crosswalk.json"));
        new GenerateSegmentDl8AiArtifactCoverageReport().generate(runRoot,
                Atl105Paths.testJson("segment-DL8-coverage-package.json"),
                Atl105Paths.ruleCatalog("DL8"), aiCatalog, aliasCrosswalk,
                docs.resolve("segment-DL8-ai-artifact-coverage-report.json"),
                docs.resolve("segment-DL8-ai-artifact-coverage-report.md"),
                Atl105Paths.consolidatedReport("DL8"));
    }

    JsonNode generate(Path runRoot, Path packageFile, Path catalogFile, Path aiCatalogFile,
                      Path aliasCrosswalkFile, Path reportJson, Path reportMarkdown,
                      Path consolidatedReport) throws IOException {
        Path chainFile = runRoot.resolve(Path.of("chains", "segment_DL8.json"));
        for (Path required : List.of(chainFile, packageFile, catalogFile, aiCatalogFile, aliasCrosswalkFile)) {
            if (!Files.isRegularFile(required)) {
                throw new IOException("Required DL8 coverage input is missing: " + required);
            }
        }

        JsonNode chain = read(chainFile);
        JsonNode aiData = representativeAiData(runRoot, chain);
        JsonNode metadata = representativeMetadata(runRoot, chain);
        JsonNode testPackage = read(packageFile);
        JsonNode ruleCatalog = read(catalogFile);
        JsonNode aiCatalog = read(aiCatalogFile);
        JsonNode aliases = read(aliasCrosswalkFile);
        List<JsonNode> rules = array(ruleCatalog, "rules");
        List<JsonNode> brs = array(testPackage, "businessRequirements");
        List<JsonNode> scenarios = array(testPackage, "testScenarios");
        List<JsonNode> cases = array(testPackage, "testCases");
        List<JsonNode> testData = array(testPackage, "testData");
        List<JsonNode> aiRecords = array(aiCatalog, "records").stream()
                .filter(row -> "DL8".equalsIgnoreCase(text(row.path("segment")))).toList();

        validatePackage(testPackage, rules, brs, scenarios, cases, testData, ruleCatalog);
        validateChain(chain, metadata);
        Map<String, Integer> observedAliases = observeAliases(runRoot.resolve("test_data"));
        validateAliases(aliases, observedAliases, rules);

        ObjectNode report = mapper.createObjectNode();
        report.put("artifact", "segment-dl8-ai-artifact-coverage");
        report.put("specification", "ATL105");
        report.put("specificationVersion", "2026-3");
        report.put("segment", "DL8");
        report.put("status", "INCOMPLETE_REVIEW_REQUIRED");
        report.put("authority", "INDEPENDENT_TEST_SOLUTION");
        report.put("aiInputScope", "Supplied AI DL8 catalog filter plus one representative phase-one gap chain. The chain produced no executable DL8 payload.");
        report.put("smeStatus", "P-01 through P-03 OPEN");
        report.put("testSolutionPackagePath", "specifications/ATL105/test-output/test-json/segment-DL8-coverage-package.json");
        report.put("correctedCandidatePath", "specifications/ATL105/test-output/test-json/segment-DL8-ai-corrected-candidate.json");
        report.put("suppliedAiCatalogPath", "specifications/ATL105/test-output/ai-artifacts/coverage-reports/supplied-ai-catalog/supplied-ai-catalog-coverage.json");
        report.put("fieldAliasCrosswalkPath", "specifications/ATL105/contract/segment-DL8-field-alias-crosswalk.json");
        ObjectNode inventory = report.putObject("artifactInventory");
        inventory.put("testSolutionRules", rules.size());
        inventory.put("testSolutionBusinessRequirements", brs.size());
        inventory.put("testSolutionScenarios", scenarios.size());
        inventory.put("testSolutionTestCases", cases.size());
        inventory.put("testSolutionTestDataRecords", testData.size());
        inventory.put("suppliedAiCatalogDl8Requirements", aiRecords.size());
        inventory.put("phaseOneRepresentativeDl8Chains", 1);
        inventory.put("phaseOneTestDataFilesScannedForFieldNames",
                countTestDataFiles(runRoot.resolve("test_data")));

        addChainSummary(report, chain, aiData, metadata);
        addCandidateMetrics(report, rules, brs, scenarios, cases, testData);
        addAiCatalogCrosswalk(report, rules, aiRecords);
        report.set("aiFieldAliasCrosswalk", aliases.path("fieldAliases").deepCopy());
        report.put("aiFieldAliasCrosswalkGovernance", text(aliases.path("governance")));
        addAiDataValidation(report, aiData, metadata);
        report.set("implementationItemProgress", ruleCatalog.path("itemProgress").deepCopy());

        Files.createDirectories(reportJson.toAbsolutePath().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(reportJson.toFile(), report);
        Files.writeString(reportMarkdown, markdown(report));
        Files.createDirectories(consolidatedReport.toAbsolutePath().getParent());
        Files.writeString(consolidatedReport, consolidatedText(report));
        return report;
    }

    private JsonNode representativeAiData(Path runRoot, JsonNode chain) throws IOException {
        String id = text(chain.path("test_case").path("id"));
        if (!id.isBlank()) {
            Path dataFile = runRoot.resolve(Path.of("test_data", id + ".json"));
            if (Files.isRegularFile(dataFile)) return read(dataFile);
        }
        return mapper.createObjectNode();
    }

    private JsonNode representativeMetadata(Path runRoot, JsonNode chain) throws IOException {
        String id = text(chain.path("test_case").path("id"));
        if (!id.isBlank()) {
            Path metadataFile = runRoot.resolve(Path.of("test_data", id + ".meta.json"));
            if (Files.isRegularFile(metadataFile)) return read(metadataFile);
        }
        ObjectNode metadata = mapper.createObjectNode();
        metadata.put("requirementId", text(chain.path("gap").path("requirement_id")));
        metadata.put("scenarioId", text(chain.path("gap").path("scenario_id")));
        metadata.put("testCaseId", "NO_TEST_CASE_PRODUCED");
        metadata.put("messageFamily", "NO_DL8_PAYLOAD_PRODUCED");
        metadata.put("gapStatus", text(chain.path("gap").path("status")));
        return metadata;
    }

    private static void validatePackage(JsonNode testPackage, List<JsonNode> rules, List<JsonNode> brs,
                                        List<JsonNode> scenarios, List<JsonNode> cases,
                                        List<JsonNode> testData, JsonNode catalog) {
        requireUniqueIds(brs, "businessRequirements");
        requireUniqueIds(scenarios, "testScenarios");
        requireUniqueIds(cases, "testCases");
        requireUniqueIds(testData, "testData");
        Map<String, JsonNode> ruleById = index(rules, "ruleId");
        Map<String, JsonNode> brById = index(brs, "id");
        Map<String, JsonNode> scenarioById = index(scenarios, "id");
        Map<String, JsonNode> caseById = index(cases, "id");
        Map<String, JsonNode> dataById = index(testData, "id");
        Map<String, JsonNode> provisionalById = index(array(catalog, "provisionalItems"), "id");
        Set<String> mappedRules = new HashSet<>();
        Set<String> linkedCases = new HashSet<>();
        Set<String> linkedData = new HashSet<>();
        Map<String, Set<String>> polaritiesByRequirement = new HashMap<>();

        for (JsonNode br : brs) {
            String ruleId = text(br.path("ruleId"));
            JsonNode rule = ruleById.get(ruleId);
            if (rule == null) throw invalid("Unknown rule in BR: " + ruleId);
            if (!sameSourceAnchor(rule.path("sourceAnchor"), br.path("sourceAnchor"))) {
                throw invalid("BR source anchor does not match catalog rule: " + text(br.path("id")));
            }
            if (!mappedRules.add(ruleId)) throw invalid("Duplicate BR mapping for rule: " + ruleId);
            if (br.has("provisionalItem")) {
                JsonNode provisional = provisionalById.get(text(br.path("provisionalItem")));
                if (provisional == null || !"OPEN".equals(text(provisional.path("status")))) {
                    throw invalid("BR references a non-open provisional item: " + text(br.path("provisionalItem")));
                }
                if (!"REVIEW_REQUIRED".equals(text(br.path("status")))) {
                    throw invalid("BR with an open SME item must remain REVIEW_REQUIRED: " + text(br.path("id")));
                }
            }
        }
        if (mappedRules.size() != rules.size()) throw invalid("Not every catalog rule maps to a BR");

        for (JsonNode scenario : scenarios) {
            for (JsonNode requirementId : array(scenario, "requirementIds")) {
                if (!brById.containsKey(text(requirementId))) {
                    throw invalid("Scenario references an unknown BR: " + text(requirementId));
                }
            }
        }
        for (JsonNode testCase : cases) {
            String id = text(testCase.path("id"));
            String brId = text(testCase.path("requirementId"));
            String polarity = text(testCase.path("candidatePolarity"));
            if (!"POSITIVE".equals(polarity) && !"NEGATIVE".equals(polarity)) {
                throw invalid("Test case requires explicit POSITIVE or NEGATIVE candidatePolarity: " + id);
            }
            if (!brById.containsKey(brId)) throw invalid("Test case references an unknown BR: " + id);
            polaritiesByRequirement.computeIfAbsent(brId, unused -> new HashSet<>()).add(polarity);
            List<JsonNode> scenarioIds = array(testCase, "scenarioIds");
            if (scenarioIds.isEmpty()) throw invalid("Test case has no scenario: " + id);
            for (JsonNode scenarioId : scenarioIds) {
                JsonNode scenario = scenarioById.get(text(scenarioId));
                if (scenario == null || !containsText(scenario.path("requirementIds"), brId)) {
                    throw invalid("Test case and scenario BR links disagree: " + id);
                }
            }
            for (String step : List.of("given", "when", "then")) {
                if (text(testCase.path(step)).isBlank()) throw invalid("Test case missing " + step + ": " + id);
            }
            List<JsonNode> dataIds = array(testCase, "testDataIds");
            if (dataIds.size() != 1) throw invalid("Test case must link to exactly one test-data record: " + id);
            String dataId = text(dataIds.get(0));
            JsonNode record = dataById.get(dataId);
            if (record == null || !containsText(record.path("testCaseIds"), id)) {
                throw invalid("Test case and test-data links disagree: " + id);
            }
            linkedCases.add(id);
            linkedData.add(dataId);
        }
        for (JsonNode br : brs) {
            Set<String> polarities = polaritiesByRequirement.getOrDefault(text(br.path("id")), Set.of());
            if (!polarities.contains("POSITIVE") || !polarities.contains("NEGATIVE")) {
                throw invalid("Every catalog rule requires positive and negative candidate cases: " + text(br.path("id")));
            }
        }
        for (JsonNode record : testData) {
            String id = text(record.path("id"));
            JsonNode payload = record.path("payload");
            if (!payload.isObject() || !payload.path("request").isObject()
                    || !payload.path("response").isObject()) {
                throw invalid("Test data requires request and response objects: " + id);
            }
            for (JsonNode caseId : array(record, "testCaseIds")) {
                JsonNode testCase = caseById.get(text(caseId));
                if (testCase == null || !containsText(testCase.path("testDataIds"), id)) {
                    throw invalid("Test-data and test-case links disagree: " + id);
                }
            }
        }
        if (linkedCases.size() != cases.size() || linkedData.size() != testData.size()) {
            throw invalid("Test case and test-data traceability is incomplete");
        }
        if (testPackage.path("coverageSummary").path("approvedTestDataPairs").asInt() != 0
                || testPackage.path("coverageSummary").path("executedTestCases").asInt() != 0
                || testPackage.path("coverageSummary").path("certifiedCoveredRules").asInt() != 0) {
            throw invalid("Unapproved candidates cannot count as approved, executed, or certified coverage");
        }
    }

    private static void requireUniqueIds(List<JsonNode> rows, String name) {
        Set<String> ids = new HashSet<>();
        for (JsonNode row : rows) {
            String id = text(row.path("id"));
            if (id.isBlank() || !ids.add(id)) throw invalid("Missing or duplicate " + name + " ID: " + id);
        }
    }

    private static Map<String, JsonNode> index(List<JsonNode> rows, String field) {
        Map<String, JsonNode> result = new HashMap<>();
        for (JsonNode row : rows) result.put(text(row.path(field)), row);
        return result;
    }

    private static boolean sameSourceAnchor(JsonNode ruleAnchor, JsonNode brAnchor) {
        for (String field : List.of("specification", "version", "section", "segment", "rule")) {
            if (!text(ruleAnchor.path(field)).equals(text(brAnchor.path(field)))) return false;
        }
        String element = text(ruleAnchor.path("element"));
        return element.isBlank() || element.equals(text(brAnchor.path("element")));
    }

    private static IllegalArgumentException invalid(String message) {
        return new IllegalArgumentException("Invalid DL8 coverage package: " + message);
    }

    private static void validateChain(JsonNode chain, JsonNode metadata) {
        if (!"DL8".equals(text(chain.path("segment_number")))) {
            throw invalid("Representative AI chain is not DL8");
        }
        String chainRequirement = text(chain.path("requirement").path("id"));
        if (chainRequirement.isBlank()) {
            throw invalid("Representative AI chain is missing requirement ID");
        }
        String metadataRequirement = text(metadata.path("requirementId"));
        if (!metadataRequirement.isBlank() && !chainRequirement.equals(metadataRequirement)) {
            throw invalid("AI chain requirement ID does not agree with metadata");
        }
        String testCaseId = text(chain.path("test_case").path("id"));
        if (testCaseId.isBlank()) {
            if (!"GAP_RESOLVER_FALLBACK_UNRELIABLE".equals(text(chain.path("gap").path("status")))) {
                throw invalid("DL8 chain has no test case without a documented gap status");
            }
            if (!text(chain.path("gap").path("requirement_id")).equals(chainRequirement)) {
                throw invalid("DL8 gap requirement does not agree with the chain requirement");
            }
        } else if (!testCaseId.equals(text(metadata.path("testCaseId")))) {
            throw invalid("AI chain test case ID does not agree with metadata");
        }
    }

    private static void addChainSummary(ObjectNode report, JsonNode chain, JsonNode aiData, JsonNode metadata) {
        ObjectNode summary = report.putObject("phaseOneAiChain");
        summary.put("requirementId", text(chain.path("requirement").path("id")));
        summary.put("scenarioId", text(metadata.path("scenarioId")));
        summary.put("testCaseId", text(metadata.path("testCaseId")));
        summary.put("sourceRuleId", text(chain.path("requirement").path("source_rule_id")));
        summary.put("sourceEvidencePage", chain.path("requirement").path("source_page").asInt());
        summary.put("metadataMessageFamily", text(metadata.path("messageFamily")));
        summary.put("chainIdentityStatus", "GAP_CHAIN_CONSISTENT");
        summary.put("gapStatus", text(chain.path("gap").path("status")));
        summary.put("scope", "One representative AI gap chain; no DL8 phase-one test-data payload was produced.");
        summary.put("dl8DataPresent", !findNamedObject(aiData, "EMV Terminal Floor Limits Data Segment").isMissingNode());
    }

    private static void addCandidateMetrics(ObjectNode report, List<JsonNode> rules, List<JsonNode> brs,
                                            List<JsonNode> scenarios, List<JsonNode> cases,
                                            List<JsonNode> data) {
        ObjectNode metrics = report.putObject("testSolutionMetrics");
        ArrayNode byRule = report.putArray("testSolutionRuleCoverage");
        int complete = 0;
        int positiveNegative = 0;
        for (JsonNode rule : rules) {
            String ruleId = text(rule.path("ruleId"));
            JsonNode br = brs.stream().filter(row -> ruleId.equals(text(row.path("ruleId"))))
                    .findFirst().orElseThrow(() -> invalid("Missing BR for catalog rule " + ruleId));
            String brId = text(br.path("id"));
            boolean hasScenario = scenarios.stream().anyMatch(row -> containsText(row.path("requirementIds"), brId));
            List<JsonNode> ruleCases = cases.stream()
                    .filter(row -> brId.equals(text(row.path("requirementId")))).toList();
            boolean linksValid = !ruleCases.isEmpty() && ruleCases.stream().allMatch(testCase ->
                    array(testCase, "testDataIds").stream().allMatch(dataId -> data.stream()
                            .anyMatch(td -> text(td.path("id")).equals(text(dataId)))));
            if (hasScenario && linksValid) complete++;
            boolean positive = ruleCases.stream().anyMatch(row ->
                    "POSITIVE".equals(text(row.path("candidatePolarity"))));
            boolean negative = ruleCases.stream().anyMatch(row ->
                    "NEGATIVE".equals(text(row.path("candidatePolarity"))));
            if (positive && negative) positiveNegative++;

            ObjectNode row = byRule.addObject();
            row.put("ruleId", ruleId);
            row.put("businessRequirementId", brId);
            row.put("testCaseCount", ruleCases.size());
            row.put("positiveCandidateCount", (int) ruleCases.stream()
                    .filter(testCase -> "POSITIVE".equals(text(testCase.path("candidatePolarity")))).count());
            row.put("negativeCandidateCount", (int) ruleCases.stream()
                    .filter(testCase -> "NEGATIVE".equals(text(testCase.path("candidatePolarity")))).count());
            boolean review = br.has("provisionalItem")
                    || "review".equalsIgnoreCase(text(rule.path("severity")))
                    || ruleCases.stream().anyMatch(testCase -> "REVIEW_REQUIRED".equals(text(testCase.path("status"))));
            row.put("candidateStatus", review ? "CANDIDATE_CHAIN_REVIEW_REQUIRED"
                    : "CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED");
            row.put("requirementStatus", text(br.path("status")));
        }
        metrics.put("oracleRuleCount", rules.size());
        metrics.put("rulesWithCandidateBrTsTcTdChains", complete);
        metrics.put("candidateChainPercent", percent(complete, rules.size()));
        metrics.put("rulesWithPositiveAndNegativeCandidates", positiveNegative);
        metrics.put("positiveNegativeCandidatePercent", percent(positiveNegative, rules.size()));
        metrics.put("candidateTestCases", cases.size());
        metrics.put("candidateRequestResponsePairs", data.size());
        metrics.put("approvedTestDataPairs", 0);
        metrics.put("executedTestCases", 0);
        metrics.put("executionCoveragePercent", 0.0);
        metrics.put("certifiedCoveredRules", 0);
        metrics.put("polarityInterpretation", "Positive/negative counts describe candidate test intent only, not an asserted expected disposition or a test result.");
        metrics.put("interpretation", "Candidate definitions are traceability evidence only; SME approval, approved fixtures, execution, and certification remain separate gates.");
    }

    private static void addAiCatalogCrosswalk(ObjectNode report, List<JsonNode> rules, List<JsonNode> records) {
        ArrayNode crosswalk = report.putArray("suppliedAiDl8Requirements");
        Set<String> mappedRules = new HashSet<>();
        for (JsonNode record : records) {
            String sourceRule = text(record.path("sourceRuleId"));
            List<String> candidates = candidateRulesForAiSource(sourceRule);
            ObjectNode row = crosswalk.addObject();
            row.put("aiRequirementId", text(record.path("aiRequirementId")));
            row.put("sourceRuleId", sourceRule);
            row.put("matchStatus", text(record.path("matchStatus")));
            row.put("candidateDisposition", candidates.isEmpty()
                    ? "NO_CANDIDATE_ORACLE_RULE" : "CANDIDATE_MAPPING_REVIEW_REQUIRED");
            ArrayNode ids = row.putArray("candidateOracleRuleIds");
            for (String id : candidates) {
                if (rules.stream().anyMatch(rule -> id.equals(text(rule.path("ruleId"))))) {
                    ids.add(id);
                    mappedRules.add(id);
                }
            }
        }
        ObjectNode metrics = report.putObject("aiCoverageOfTestSolutionOracle");
        metrics.put("oracleRuleCount", rules.size());
        metrics.put("suppliedAiDl8RequirementCount", records.size());
        metrics.put("rulesWithCandidateAiMappingsPendingReview", mappedRules.size());
        metrics.put("rulesWithoutCandidateAiCatalogMapping", rules.size() - mappedRules.size());
        metrics.put("confirmedSemanticMatches", 0);
        metrics.put("interpretation", "Source element links and heuristic match statuses do not establish semantic equivalence. AI records do not define the oracle denominator.");
    }

    private static List<String> candidateRulesForAiSource(String sourceRule) {
        if ("ENT-ELEM-24".equals(sourceRule) || "ENT-FIELD-DL8-1".equals(sourceRule)) return List.of("SEGDL8-R-002");
        if ("ENT-ELEM-84".equals(sourceRule) || "ENT-FIELD-DL8-2".equals(sourceRule)) return List.of("SEGDL8-R-002", "SEGDL8-R-005");
        if ("ENT-ELEM-233".equals(sourceRule) || "ENT-ELEM-234".equals(sourceRule)
                || "ENT-ELEM-235".equals(sourceRule) || "ENT-ELEM-236".equals(sourceRule)) return List.of("SEGDL8-R-003", "SEGDL8-R-004");
        if (sourceRule.startsWith("ENT-FIELD-DL8-")) return List.of("SEGDL8-R-003", "SEGDL8-R-004");
        return List.of();
    }

    private static Map<String, Integer> observeAliases(Path testDataDirectory) throws IOException {
        Map<String, Integer> counts = new HashMap<>();
        try (var paths = Files.list(testDataDirectory)) {
            for (Path path : paths.filter(file ->
                    file.getFileName().toString().matches("TC-[0-9]+\\.json")).toList()) {
                countFields(read(path), counts);
            }
        }
        return counts;
    }

    private static void countFields(JsonNode node, Map<String, Integer> counts) {
        if (node.isObject()) {
            JsonNode dl8 = node.get("EMV Terminal Floor Limits Data Segment");
            if (dl8 != null && dl8.isObject()) {
                dl8.fieldNames().forEachRemaining(name -> counts.merge(name, 1, Integer::sum));
            }
            node.elements().forEachRemaining(child -> countFields(child, counts));
        } else if (node.isArray()) {
            node.elements().forEachRemaining(child -> countFields(child, counts));
        }
    }

    private static int countTestDataFiles(Path directory) throws IOException {
        try (var files = Files.list(directory)) {
            return (int) files.filter(file -> file.getFileName().toString().matches("TC-[0-9]+\\.json"))
                    .count();
        }
    }

    private static void validateAliases(JsonNode aliases, Map<String, Integer> observed, List<JsonNode> rules) {
        Set<String> names = new HashSet<>();
        for (JsonNode alias : array(aliases, "fieldAliases")) {
            String name = text(alias.path("aiElementName"));
            String ruleId = text(alias.path("testRuleId"));
            if (!names.add(name)) throw invalid("Duplicate observed AI field name: " + name);
            if (!observed.containsKey(name) || observed.get(name) != alias.path("occurrencesObserved").asInt()) {
                throw invalid("AI alias occurrence count differs from delivered payloads: " + name);
            }
            JsonNode rule = rules.stream().filter(row -> ruleId.equals(text(row.path("ruleId"))))
                    .findFirst().orElse(null);
            if (rule == null) throw invalid("Unknown Test Solution rule in AI alias: " + ruleId);
            List<String> elements = List.of(text(rule.path("sourceAnchor").path("element")).split(","));
            if (!elements.contains(text(alias.path("testElementNumber")))) {
                throw invalid("AI alias element is not anchored by its Test Solution rule: " + name);
            }
        }
        if (!names.equals(observed.keySet())) throw invalid("Field crosswalk must exactly match observed AI field names");
    }

    void addAiDataValidation(ObjectNode report, JsonNode aiData, JsonNode metadata) {
        JsonNode dl8 = findNamedObject(aiData, "EMV Terminal Floor Limits Data Segment");
        boolean hasPayload = !dl8.isMissingNode();
        String indicator = field(dl8, "DataTypeIndicator");
        String length = field(dl8, "SegmentLengthIndicator");
        JsonNode groups = dl8.path("FloorLimitData");
        int groupCount = groups.isArray() ? groups.size() : (hasPayload && dl8.has("RID") ? 1 : 0);
        boolean noEndIndicator = !hasPayload || !dl8.has("EndofDataIndicator");
        boolean framing = "%".equals(indicator) && THREE_DIGITS.matcher(length).matches() && noEndIndicator;
        boolean groupBounds = groupCount >= 1 && groupCount <= 24;
        int declaredLength = THREE_DIGITS.matcher(length).matches() ? Integer.parseInt(length) : -1;
        boolean wholeGroups = declaredLength == groupCount * 26;
        boolean groupFormats = groupCount > 0 && eachGroupValid(groups.isArray() ? groups : singletonGroup(dl8));
        String family = text(metadata.path("messageFamily"));

        ArrayNode findings = report.putArray("aiTestDataRuleValidation");
        addFinding(findings, "SEGDL8-R-001", "NOT_ASSERTABLE",
                "The supplied AI evidence has no confirmed terminal Special, table-load placement, or host data-change context.");
        addFinding(findings, "SEGDL8-R-002", !hasPayload ? "NOT_ASSERTABLE" : (!framing ? "FAIL" : "REVIEW_REQUIRED"),
                hasPayload ? "Data Type Indicator shape=" + "%".equals(indicator)
                        + "; three-digit length=" + THREE_DIGITS.matcher(length).matches()
                        + "; no end marker=" + noEndIndicator
                        + "; placement and Element 84 source remain open under P-02."
                        : "No DL8 payload was generated by the representative AI chain.");
        addFinding(findings, "SEGDL8-R-003", !hasPayload ? "NOT_ASSERTABLE" : (groupBounds ? "PASS" : "FAIL"),
                hasPayload ? "Complete Floor Limit Data group count=" + groupCount + " (allowed 1-24)."
                        : "No repeated Floor Limit Data payload exists to count.");
        addFinding(findings, "SEGDL8-R-004", !hasPayload ? "NOT_ASSERTABLE" : (!groupFormats ? "FAIL" : "REVIEW_REQUIRED"),
                hasPayload ? "Group widths and primitive formats valid=" + groupFormats
                        + "; RID representation and card-type values remain open under P-03."
                        : "No RID, Stand-in Indicator, Floor Limit, or card-type values were generated.");
        addFinding(findings, "SEGDL8-R-005", !hasPayload ? "NOT_ASSERTABLE" : (wholeGroups ? "PASS" : "FAIL"),
                hasPayload ? "Segment Length whole-group calculation valid=" + wholeGroups
                        + " for " + groupCount + " groups."
                        : "No Segment Length value exists to reconcile to 26-byte groups.");

        ObjectNode summary = report.putObject("aiTestDataValidationSummary");
        summary.put("ruleCount", findings.size());
        summary.put("pass", count(findings, "PASS"));
        summary.put("fail", count(findings, "FAIL"));
        summary.put("reviewRequired", count(findings, "REVIEW_REQUIRED"));
        summary.put("notAssertable", count(findings, "NOT_ASSERTABLE"));
        summary.put("validForExecution", false);
        summary.put("sourceMessageFamily", family);
        summary.put("sensitiveValueIncluded", false);
    }

    private ArrayNode singletonGroup(JsonNode dl8) {
        ArrayNode array = mapper.createArrayNode();
        ObjectNode group = mapper.createObjectNode();
        for (String field : List.of("RID", "StandInIndicator", "FloorLimit", "BUYPASSRIDCardType")) {
            group.set(field, dl8.path(field));
        }
        array.add(group);
        return array;
    }

    private static boolean eachGroupValid(JsonNode groups) {
        for (JsonNode group : groups) {
            String rid = field(group, "RID");
            String standIn = field(group, "StandInIndicator");
            String floor = field(group, "FloorLimit");
            String card = field(group, "BUYPASSRIDCardType");
            if (rid.length() != 10 || !ALPHANUMERIC.matcher(rid).matches()) return false;
            if (!List.of("1", "2", "3").contains(standIn)) return false;
            if (floor.length() != 12 || !NUMERIC.matcher(floor).matches()) return false;
            if (card.length() != 3 || !ALPHANUMERIC.matcher(card).matches()) return false;
        }
        return true;
    }

    private static JsonNode findNamedObject(JsonNode node, String name) {
        if (node.isObject()) {
            JsonNode child = node.get(name);
            if (child != null && child.isObject()) return child;
            var fields = node.fields();
            while (fields.hasNext()) {
                JsonNode found = findNamedObject(fields.next().getValue(), name);
                if (!found.isMissingNode()) return found;
            }
        } else if (node.isArray()) {
            for (JsonNode child : node) {
                JsonNode found = findNamedObject(child, name);
                if (!found.isMissingNode()) return found;
            }
        }
        return com.fasterxml.jackson.databind.node.MissingNode.getInstance();
    }

    private static String field(JsonNode object, String name) {
        JsonNode value = object.path(name);
        return value.isTextual() ? value.asText() : "";
    }

    private static void addFinding(ArrayNode findings, String ruleId, String status, String observation) {
        ObjectNode row = findings.addObject();
        row.put("ruleId", ruleId);
        row.put("status", status);
        row.put("observation", observation);
    }

    private static int count(JsonNode rows, String status) {
        int result = 0;
        for (JsonNode row : rows) if (status.equals(text(row.path("status")))) result++;
        return result;
    }

    private static String markdown(JsonNode report) {
        JsonNode inventory = report.path("artifactInventory");
        JsonNode metrics = report.path("testSolutionMetrics");
        JsonNode validation = report.path("aiTestDataValidationSummary");
        StringBuilder out = new StringBuilder("# DL8 AI Artifact Coverage and Validation\n\n")
                .append("**Status:** ").append(text(report.path("status"))).append("\n\n")
                .append("**AI scope:** ").append(text(report.path("aiInputScope"))).append("\n\n")
                .append("**Open decisions:** ").append(text(report.path("smeStatus"))).append("\n\n")
                .append("## Artifact inventory\n\n| Evidence | Count |\n|---|---:|\n")
                .append("| Oracle rules | ").append(inventory.path("testSolutionRules").asInt()).append(" |\n")
                .append("| Candidate BR / TS / TC / TD | ")
                .append(inventory.path("testSolutionBusinessRequirements").asInt()).append(" / ")
                .append(inventory.path("testSolutionScenarios").asInt()).append(" / ")
                .append(inventory.path("testSolutionTestCases").asInt()).append(" / ")
                .append(inventory.path("testSolutionTestDataRecords").asInt()).append(" |\n")
                .append("| Supplied AI catalog DL8 requirements | ")
                .append(inventory.path("suppliedAiCatalogDl8Requirements").asInt()).append(" |\n")
                .append("| Representative phase-one AI chains | ")
                .append(inventory.path("phaseOneRepresentativeDl8Chains").asInt()).append(" |\n\n")
                .append("## Candidate coverage (not execution coverage)\n\n| Measure | Result |\n|---|---:|\n")
                .append("| Rules with candidate BR → TS → TC → TD links | ")
                .append(metrics.path("rulesWithCandidateBrTsTcTdChains").asInt()).append(" / ")
                .append(metrics.path("oracleRuleCount").asInt()).append(" (")
                .append(metrics.path("candidateChainPercent").asText()).append("%) |\n")
                .append("| Rules with positive and negative candidates | ")
                .append(metrics.path("rulesWithPositiveAndNegativeCandidates").asInt()).append(" / ")
                .append(metrics.path("oracleRuleCount").asInt()).append(" (")
                .append(metrics.path("positiveNegativeCandidatePercent").asText()).append("%) |\n")
                .append("| Approved pairs / executed cases / certified rules | ")
                .append(metrics.path("approvedTestDataPairs").asInt()).append(" / ")
                .append(metrics.path("executedTestCases").asInt()).append(" / ")
                .append(metrics.path("certifiedCoveredRules").asInt()).append(" |\n\n")
                .append("### Per-rule traceability\n\n| Rule | Cases | Positive | Negative | Status |\n|---|---:|---:|---:|---|\n");
        for (JsonNode row : report.path("testSolutionRuleCoverage")) {
            out.append("| ").append(text(row.path("ruleId"))).append(" | ")
                    .append(row.path("testCaseCount").asInt()).append(" | ")
                    .append(row.path("positiveCandidateCount").asInt()).append(" | ")
                    .append(row.path("negativeCandidateCount").asInt()).append(" | ")
                    .append(text(row.path("candidateStatus"))).append(" |\n");
        }
        out.append("\nPositive/negative polarity is candidate test intent only. Mappings and candidate percentages are not SME approval, execution, or certification.\n\n")
                .append("## Supplied AI catalog crosswalk\n\n| AI requirement | Source rule | AI match status | Candidate oracle rule | Disposition |\n|---|---|---|---|---|\n");
        for (JsonNode row : report.path("suppliedAiDl8Requirements")) {
            List<String> ids = new ArrayList<>();
            row.path("candidateOracleRuleIds").forEach(id -> ids.add(text(id)));
            out.append("| ").append(text(row.path("aiRequirementId"))).append(" | ")
                    .append(text(row.path("sourceRuleId"))).append(" | ")
                    .append(text(row.path("matchStatus"))).append(" | ")
                    .append(ids.isEmpty() ? "—" : String.join(", ", ids)).append(" | ")
                    .append(text(row.path("candidateDisposition"))).append(" |\n");
        }
        out.append("\nAll mappings remain unconfirmed candidate source-element links.\n\n")
                .append("## Observed AI field-name crosswalk\n\n| AI field | Element | Rule | Occurrences |\n|---|---:|---|---:|\n");
        for (JsonNode alias : report.path("aiFieldAliasCrosswalk")) {
            out.append("| ").append(text(alias.path("aiElementName"))).append(" | ")
                    .append(text(alias.path("testElementNumber"))).append(" | ")
                    .append(text(alias.path("testRuleId"))).append(" | ")
                    .append(alias.path("occurrencesObserved").asInt()).append(" |\n");
        }
        out.append("\nCrosswalk is comparison-only and must not influence the oracle.\n\n")
                .append("## Independent sample validation\n\n| Rule | Result | Observation |\n|---|---|---|\n");
        for (JsonNode row : report.path("aiTestDataRuleValidation")) {
            out.append("| ").append(text(row.path("ruleId"))).append(" | ")
                    .append(text(row.path("status"))).append(" | ")
                    .append(text(row.path("observation")).replace("|", "\\|")).append(" |\n");
        }
        return out.append("\nTotals: **").append(validation.path("pass").asInt()).append(" PASS**, **")
                .append(validation.path("fail").asInt()).append(" FAIL**, **")
                .append(validation.path("reviewRequired").asInt()).append(" REVIEW_REQUIRED**, **")
                .append(validation.path("notAssertable").asInt()).append(" NOT_ASSERTABLE**.\n\n")
                .append("The AI sample is one representative gap chain, not an executable DL8 package. Sensitive values are excluded from this report. P-01 through P-03 remain open; no data is approved or executed.")
                .toString();
    }

    private static String consolidatedText(JsonNode report) {
        JsonNode metrics = report.path("testSolutionMetrics");
        JsonNode validation = report.path("aiTestDataValidationSummary");
        StringBuilder out = new StringBuilder("SEGMENT DL8 CONSOLIDATED TEST SOLUTION READINESS\n")
                .append("Status: INCOMPLETE_REVIEW_REQUIRED\n")
                .append("Specification: ATL105 2026-3; EMV Terminal Floor Limits Data Segment (DL8)\n")
                .append("Authority: Independent Test Solution; AI evidence is comparison input only.\n\n")
                .append("CANDIDATE COVERAGE\n")
                .append("Oracle rules: ").append(metrics.path("oracleRuleCount").asInt()).append('\n')
                .append("Candidate BR-TS-TC-TD chains: ")
                .append(metrics.path("rulesWithCandidateBrTsTcTdChains").asInt()).append('/')
                .append(metrics.path("oracleRuleCount").asInt()).append('\n')
                .append("Rules with positive and negative candidates: ")
                .append(metrics.path("rulesWithPositiveAndNegativeCandidates").asInt()).append('/')
                .append(metrics.path("oracleRuleCount").asInt()).append('\n')
                .append("Candidate cases / symbolic data pairs: ")
                .append(metrics.path("candidateTestCases").asInt()).append(" / ")
                .append(metrics.path("candidateRequestResponsePairs").asInt()).append('\n')
                .append("Approved data pairs / executed cases / certified rules: 0 / 0 / 0\n\n")
                .append("AI EVIDENCE\n")
                .append("Supplied AI catalog DL8 requirements: ")
                .append(report.path("aiCoverageOfTestSolutionOracle").path("suppliedAiDl8RequirementCount").asInt()).append('\n')
                .append("Representative chain: ").append(text(report.path("phaseOneAiChain").path("testCaseId")))
                .append(" (phase-one gap scope)\n")
                .append("Sample validation: ").append(validation.path("pass").asInt()).append(" PASS, ")
                .append(validation.path("fail").asInt()).append(" FAIL, ")
                .append(validation.path("reviewRequired").asInt()).append(" REVIEW_REQUIRED, ")
                .append(validation.path("notAssertable").asInt()).append(" NOT_ASSERTABLE\n")
                .append("Sensitive values are excluded from all reports.\n\n")
                .append("OPEN SME GATES\n")
                .append("P-01 / SEGDL8-SME-001: Fixture approval.\n")
                .append("P-02 / SEGDL8-SME-002: Table Load Response placement, terminal Special name, and Element 84 source handling.\n")
                .append("P-03 / SEGDL8-SME-003: RID representation, BUYPASS RID Card Type values, and Stand-in/Floor-Limit combinations.\n");
        JsonNode progress = report.path("implementationItemProgress");
        out.append("\nEIGHT-ITEM STATUS\n");
        progress.fields().forEachRemaining(entry -> out.append(entry.getKey()).append(": ")
                .append(entry.getValue().asText()).append('\n'));
        return out.append("\nNEXT: Resolve SME decisions, approve isolated synthetic data, then implement the serializer-aware validator and execute mutation/device-lifecycle checks.")
                .toString();
    }

    private static JsonNode read(Path path) throws IOException {
        return new ObjectMapper().readTree(path.toFile());
    }

    private static List<JsonNode> array(JsonNode root, String field) {
        JsonNode value = root.path(field);
        if (!value.isArray()) throw invalid(field + " must be a JSON array");
        List<JsonNode> rows = new ArrayList<>();
        value.forEach(rows::add);
        return rows;
    }

    private static boolean containsText(JsonNode values, String expected) {
        if (!values.isArray()) return false;
        for (JsonNode value : values) if (expected.equals(text(value))) return true;
        return false;
    }

    private static String text(JsonNode node) {
        return node.isMissingNode() || node.isNull() ? "" : node.asText();
    }

    private static double percent(int numerator, int denominator) {
        return denominator == 0 ? 0.0 : Math.round(numerator * 1000.0 / denominator) / 10.0;
    }
}
