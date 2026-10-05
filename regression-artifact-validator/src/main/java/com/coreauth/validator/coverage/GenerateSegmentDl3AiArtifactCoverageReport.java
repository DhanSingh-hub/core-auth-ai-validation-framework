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

public final class GenerateSegmentDl3AiArtifactCoverageReport {
    private static final Pattern DAY = Pattern.compile("[0-6]");
    private static final Pattern SIX_DIGIT_DATE = Pattern.compile("[0-9]{6}");
    private static final Pattern FOUR_DIGIT_TIME = Pattern.compile("[0-9]{4}");
    private final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        Path docs = Atl105Paths.docs().resolve(Path.of("specs", "kb", "segment-DL3", "coverage"));
        Path aiCatalog = Atl105Paths.testOutput().resolve(Path.of(
                "ai-artifacts", "coverage-reports", "supplied-ai-catalog", "supplied-ai-catalog-coverage.json"));
        Path aliasCrosswalk = Atl105Paths.root().resolve(Path.of(
                "contract", "segment-DL3-field-alias-crosswalk.json"));
        new GenerateSegmentDl3AiArtifactCoverageReport().generate(runRoot,
                Atl105Paths.testJson("segment-DL3-coverage-package.json"),
                Atl105Paths.ruleCatalog("DL3"), aiCatalog, aliasCrosswalk,
                docs.resolve("segment-DL3-ai-artifact-coverage-report.json"),
                docs.resolve("segment-DL3-ai-artifact-coverage-report.md"),
                Atl105Paths.consolidatedReport("DL3"));
    }

    private static void validateChain(JsonNode chain, JsonNode metadata) {
        for (String field : List.of("requirement", "scenario", "test_case")) {
            if (!chain.path(field).isObject() || text(chain.path(field).path("id")).isBlank()) {
                throw invalid("Representative AI chain is missing its " + field + " identity");
            }
        }
        if (!text(chain.path("test_case").path("id")).equals(text(metadata.path("testCaseId")))
                || !text(chain.path("scenario").path("id")).equals(text(metadata.path("scenarioId")))
                || !text(chain.path("requirement").path("id")).equals(text(metadata.path("requirementId")))) {
            throw invalid("Representative AI chain IDs do not agree with test-data metadata");
        }
        if (!"2026-3".equals(text(chain.path("scenario").path("spec_version")))) {
            throw invalid("Representative AI chain ATL105 version is not 2026-3");
        }
    }

    JsonNode generate(Path runRoot, Path packageFile, Path catalogFile, Path aiCatalogFile,
                      Path aliasCrosswalkFile, Path reportJson, Path reportMarkdown,
                      Path consolidatedReport) throws IOException {
        Path chainFile = runRoot.resolve(Path.of("chains", "segment_DL3.json"));
        Path dataFile = runRoot.resolve(Path.of("test_data", "TC-000003.json"));
        Path metadataFile = runRoot.resolve(Path.of("test_data", "TC-000003.meta.json"));
        for (Path required : List.of(chainFile, dataFile, metadataFile, packageFile, catalogFile,
                aiCatalogFile, aliasCrosswalkFile)) {
            if (!Files.isRegularFile(required)) {
                throw new IOException("Required DL3 coverage input is missing: " + required);
            }
        }

        JsonNode chain = read(chainFile);
        JsonNode aiData = read(dataFile);
        JsonNode metadata = read(metadataFile);
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
                .filter(row -> "DL3".equalsIgnoreCase(text(row.path("segment")))).toList();

        validatePackage(testPackage, rules, brs, scenarios, cases, testData, ruleCatalog);
        validateChain(chain, metadata);
        Map<String, Integer> observedAliases = observeAliases(runRoot.resolve("test_data"));
        validateAliases(aliases, observedAliases, rules);

        ObjectNode report = mapper.createObjectNode();
        report.put("artifact", "segment-dl3-ai-artifact-coverage");
        report.put("specification", "ATL105");
        report.put("specificationVersion", "2026-3");
        report.put("segment", "DL3");
        report.put("status", "INCOMPLETE_REVIEW_REQUIRED");
        report.put("authority", "INDEPENDENT_TEST_SOLUTION");
        report.put("aiInputScope", "Supplied AI DL3 catalog crosswalk plus one representative phase-one chain and payload.");
        report.put("smeStatus", "P-01 through P-04 OPEN");
        report.put("testSolutionPackagePath", "specifications/ATL105/test-output/test-json/segment-DL3-coverage-package.json");
        report.put("correctedCandidatePath", "specifications/ATL105/test-output/test-json/segment-DL3-ai-corrected-candidate.json");
        report.put("suppliedAiCatalogPath", "specifications/ATL105/test-output/ai-artifacts/coverage-reports/supplied-ai-catalog/supplied-ai-catalog-coverage.json");
        report.put("fieldAliasCrosswalkPath", "specifications/ATL105/contract/segment-DL3-field-alias-crosswalk.json");
        ObjectNode inventory = report.putObject("artifactInventory");
        inventory.put("testSolutionRules", rules.size());
        inventory.put("testSolutionBusinessRequirements", brs.size());
        inventory.put("testSolutionScenarios", scenarios.size());
        inventory.put("testSolutionTestCases", cases.size());
        inventory.put("testSolutionTestDataRecords", testData.size());
        inventory.put("suppliedAiCatalogDl3Requirements", aiRecords.size());
        inventory.put("phaseOneRepresentativeDl3Chains", 1);
        inventory.put("phaseOneDl3TestDataFiles", 1);
        inventory.put("phaseOneTestDataFilesScannedForFieldNames",
                countTestDataFiles(runRoot.resolve("test_data")));

        addChainSummary(report, chain, aiData, metadata);
        addCandidateMetrics(report, rules, brs, scenarios, cases, testData);
        addAiCatalogCrosswalk(report, rules, aiRecords);
        addAliasEvidence(report, aliases);
        addAiDataValidation(report, aiData, metadata);
        report.set("implementationItemProgress", ruleCatalog.path("itemProgress").deepCopy());

        Files.createDirectories(reportJson.toAbsolutePath().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(reportJson.toFile(), report);
        Files.writeString(reportMarkdown, markdown(report));
        Files.createDirectories(consolidatedReport.toAbsolutePath().getParent());
        Files.writeString(consolidatedReport, consolidatedText(report));
        return report;
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
        Set<String> linkedCaseIds = new HashSet<>();
        Set<String> linkedDataIds = new HashSet<>();
        for (JsonNode br : brs) {
            String ruleId = text(br.path("ruleId"));
            JsonNode rule = ruleById.get(ruleId);
            if (rule == null) throw invalid("Unknown rule in BR: " + ruleId);
            if (!text(rule.path("sourceAnchor").path("rule")).equals(
                    text(br.path("sourceAnchor").path("rule")))) {
                throw invalid("BR source anchor does not match catalog rule: " + text(br.path("id")));
            }
            if (!mappedRules.add(ruleId)) throw invalid("More than one BR mapped to rule: " + ruleId);
            JsonNode provisionalId = br.path("provisionalItem");
            if (!provisionalId.isMissingNode()) {
                JsonNode provisional = provisionalById.get(text(provisionalId));
                if (provisional == null || !"OPEN".equals(text(provisional.path("status")))) {
                    throw invalid("BR references a non-open provisional item: " + text(provisionalId));
                }
                if (!"REVIEW_REQUIRED".equals(text(br.path("status")))) {
                    throw invalid("BR linked to an open SME item must stay REVIEW_REQUIRED: " + text(br.path("id")));
                }
            }
        }
        if (mappedRules.size() != rules.size()) throw invalid("Not every catalog rule maps to a BR");

        for (JsonNode scenario : scenarios) {
            for (JsonNode requirementId : array(scenario, "requirementIds")) {
                if (!brById.containsKey(text(requirementId))) {
                    throw invalid("Scenario references unknown BR: " + text(requirementId));
                }
            }
        }
        for (JsonNode testCase : cases) {
            String id = text(testCase.path("id"));
            String brId = text(testCase.path("requirementId"));
            if (!brById.containsKey(brId)) {
                throw invalid("Test case references unknown BR: " + id);
            }
            List<JsonNode> scenarioIds = array(testCase, "scenarioIds");
            if (scenarioIds.isEmpty()) throw invalid("Test case has no scenario link: " + id);
            for (JsonNode scenarioId : scenarioIds) {
                JsonNode scenario = scenarioById.get(text(scenarioId));
                if (scenario == null || !containsText(scenario.path("requirementIds"), brId)) {
                    throw invalid("Scenario does not link to the test case BR: " + id);
                }
            }
            List<JsonNode> dataIds = array(testCase, "testDataIds");
            if (dataIds.size() != 1) throw invalid("Test case must link to one symbolic test-data pair: " + id);
            for (JsonNode dataId : dataIds) {
                JsonNode record = dataById.get(text(dataId));
                if (record == null || !containsText(record.path("testCaseIds"), id)) {
                    throw invalid("Test case and test-data links disagree: " + id);
                }
                linkedDataIds.add(text(dataId));
            }
            linkedCaseIds.add(id);
        }
        for (JsonNode record : testData) {
            JsonNode payload = record.path("payload");
            if (!payload.isObject() || !payload.path("request").isObject() || !payload.path("response").isObject()) {
                throw invalid("Test data must contain an object payload with request and response: " + text(record.path("id")));
            }
            for (JsonNode caseId : array(record, "testCaseIds")) {
                JsonNode testCase = caseById.get(text(caseId));
                if (testCase == null || !containsText(testCase.path("testDataIds"), text(record.path("id")))) {
                    throw invalid("Test-data and test-case links disagree: " + text(record.path("id")));
                }
            }
        }
        if (linkedCaseIds.size() != cases.size() || linkedDataIds.size() != testData.size()) {
            throw invalid("Test case and test-data traceability is incomplete");
        }
        if (testPackage.path("coverageSummary").path("approvedTestDataPairs").asInt() != 0
                || testPackage.path("coverageSummary").path("executedTestCases").asInt() != 0
                || testPackage.path("coverageSummary").path("certifiedCoveredRules").asInt() != 0) {
            throw invalid("Unapproved candidate data cannot count as executed or certified coverage");
        }
    }

    private static void requireUniqueIds(List<JsonNode> rows, String label) {
        Set<String> ids = new HashSet<>();
        for (JsonNode row : rows) {
            String id = text(row.path("id"));
            if (id.isBlank() || !ids.add(id)) throw invalid("Missing or duplicate " + label + " ID: " + id);
        }
    }

    private static Map<String, JsonNode> index(List<JsonNode> rows, String key) {
        Map<String, JsonNode> result = new HashMap<>();
        for (JsonNode row : rows) result.put(text(row.path(key)), row);
        return result;
    }

    private static IllegalArgumentException invalid(String message) {
        return new IllegalArgumentException("Invalid DL3 coverage package: " + message);
    }

    private static void addChainSummary(ObjectNode report, JsonNode chain, JsonNode aiData, JsonNode metadata) {
        ObjectNode summary = report.putObject("phaseOneAiChain");
        summary.put("requirementId", text(chain.path("requirement").path("id")));
        summary.put("scenarioId", text(chain.path("scenario").path("id")));
        summary.put("testCaseId", text(chain.path("test_case").path("id")));
        summary.put("sourceRuleId", text(chain.path("requirement").path("source_rule_id")));
        summary.put("sourceEvidencePage", chain.path("requirement").path("source_page").asInt());
        summary.put("metadataMessageFamily", text(metadata.path("messageFamily")));
        summary.put("dataHasRequest", containsNamedObject(aiData, "request"));
        summary.put("dataHasResponseMessageObject", aiData.isObject());
        summary.put("chainIdentityStatus", "ID_CONSISTENT");
        summary.put("scope", "One representative AI chain for DL3; this is not exhaustive package traceability.");
    }

    private static void addCandidateMetrics(ObjectNode report, List<JsonNode> rules, List<JsonNode> brs,
                                            List<JsonNode> scenarios, List<JsonNode> cases,
                                            List<JsonNode> data) {
        ObjectNode metrics = report.putObject("testSolutionMetrics");
        ArrayNode ruleCoverage = report.putArray("testSolutionRuleCoverage");
        int fullyLinkedRules = 0;
        int positiveNegativeRules = 0;
        int provisionalRules = 0;
        for (JsonNode rule : rules) {
            String ruleId = text(rule.path("ruleId"));
            JsonNode br = brs.stream().filter(row -> ruleId.equals(text(row.path("ruleId"))))
                    .findFirst().orElse(null);
            if (br == null) continue;
            String brId = text(br.path("id"));
            boolean hasScenario = scenarios.stream().anyMatch(row -> containsText(row.path("requirementIds"), brId));
            List<JsonNode> ruleCases = cases.stream()
                    .filter(row -> brId.equals(text(row.path("requirementId")))).toList();
            boolean allHaveData = ruleCases.stream().allMatch(row -> array(row, "testDataIds").stream()
                    .allMatch(id -> data.stream().anyMatch(td -> text(td.path("id")).equals(text(id)))));
            if (hasScenario && !ruleCases.isEmpty() && allHaveData) fullyLinkedRules++;
            boolean hasPositive = ruleCases.stream().anyMatch(row -> text(row.path("expectedOutcome")).startsWith("PASS"));
            boolean hasNegative = ruleCases.stream().anyMatch(row -> text(row.path("expectedOutcome")).startsWith("FAIL"));
            if (hasPositive && hasNegative) positiveNegativeRules++;
            if (!br.path("provisionalItem").isMissingNode()) provisionalRules++;
            ObjectNode row = ruleCoverage.addObject();
            row.put("ruleId", ruleId);
            row.put("businessRequirementId", brId);
            row.put("testCaseCount", ruleCases.size());
            row.put("positiveCandidateCount", (int) ruleCases.stream()
                    .filter(testCase -> text(testCase.path("expectedOutcome")).startsWith("PASS")).count());
            row.put("negativeCandidateCount", (int) ruleCases.stream()
                    .filter(testCase -> text(testCase.path("expectedOutcome")).startsWith("FAIL")).count());
            boolean requiresReview = !br.path("provisionalItem").isMissingNode()
                    || "review".equalsIgnoreCase(text(rule.path("severity")))
                    || ruleCases.stream().anyMatch(testCase -> "REVIEW_REQUIRED".equals(text(testCase.path("status"))));
            row.put("candidateStatus", hasScenario && !ruleCases.isEmpty() && allHaveData
                    ? requiresReview ? "CANDIDATE_CHAIN_REVIEW_REQUIRED" : "CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED"
                    : "TRACEABILITY_GAP");
            row.put("requirementStatus", text(br.path("status")));
        }
        metrics.put("oracleRuleCount", rules.size());
        metrics.put("rulesWithCandidateBrTsTcTdChains", fullyLinkedRules);
        metrics.put("candidateChainPercent", percent(fullyLinkedRules, rules.size()));
        metrics.put("rulesWithPositiveAndNegativeCandidates", positiveNegativeRules);
        metrics.put("positiveNegativeCandidatePercent", percent(positiveNegativeRules, rules.size()));
        metrics.put("rulesWithOpenSmeItems", provisionalRules);
        metrics.put("candidateTestCases", cases.size());
        metrics.put("candidateRequestResponsePairs", data.size());
        metrics.put("approvedTestDataPairs", 0);
        metrics.put("executedTestCases", 0);
        metrics.put("executionCoveragePercent", 0.0);
        metrics.put("certifiedCoveredRules", 0);
        metrics.put("interpretation", "Candidate links are traceability evidence only. Open SME items, synthetic data, and unexecuted cases cannot count as approved coverage.");
    }

    private static void addAiCatalogCrosswalk(ObjectNode report, List<JsonNode> rules, List<JsonNode> records) {
        ArrayNode crosswalk = report.putArray("suppliedAiDl3Requirements");
        Map<String, List<String>> aiIdsByRule = new HashMap<>();
        for (JsonNode record : records) {
            String sourceRule = text(record.path("sourceRuleId"));
            List<String> candidateRules = candidateRulesForAiSource(sourceRule);
            ObjectNode row = crosswalk.addObject();
            row.put("aiRequirementId", text(record.path("aiRequirementId")));
            row.put("sourceRuleId", sourceRule);
            row.put("matchStatus", text(record.path("matchStatus")));
            row.put("candidateDisposition", candidateRules.isEmpty()
                    ? "NO_CANDIDATE_ORACLE_RULE" : "CANDIDATE_MAPPING_REVIEW_REQUIRED");
            ArrayNode candidates = row.putArray("candidateOracleRuleIds");
            candidateRules.forEach(id -> {
                if (rules.stream().anyMatch(rule -> id.equals(text(rule.path("ruleId"))))) {
                    candidates.add(id);
                    aiIdsByRule.computeIfAbsent(id, ignored -> new ArrayList<>())
                            .add(text(record.path("aiRequirementId")));
                }
            });
        }
        int mapped = aiIdsByRule.size();
        ObjectNode metrics = report.putObject("aiCoverageOfTestSolutionOracle");
        metrics.put("oracleRuleCount", rules.size());
        metrics.put("suppliedAiDl3RequirementCount", records.size());
        metrics.put("rulesWithCandidateAiMappingsPendingReview", mapped);
        metrics.put("rulesWithoutCandidateAiCatalogMapping", rules.size() - mapped);
        metrics.put("confirmedSemanticMatches", 0);
        metrics.put("interpretation", "AI catalog and shared element IDs are comparison evidence only. All mappings remain unconfirmed; AI requirements do not define the Test Solution denominator.");
    }

    private static List<String> candidateRulesForAiSource(String sourceRuleId) {
        return switch (sourceRuleId) {
            case "ENT-ELEM-21", "ENT-ELEM-22", "ENT-ELEM-23", "ENT-ELEM-25",
                    "ENT-FIELD-DL3-2", "ENT-FIELD-DL3-3", "ENT-FIELD-DL3-4",
                    "ENT-FIELD-DL3-5" -> List.of("SEGDL3-R-005");
            case "ENT-ELEM-34", "ENT-FIELD-DL3-1", "ENT-FIELD-DL3-7" -> List.of("SEGDL3-R-001");
            case "ENT-ELEM-65", "ENT-FIELD-DL3-6" -> List.of("SEGDL3-R-003", "SEGDL3-R-006");
            default -> List.of();
        };
    }

    private static Map<String, Integer> observeAliases(Path testDataDirectory) throws IOException {
        Map<String, Integer> counts = new HashMap<>();
        try (var files = Files.list(testDataDirectory)) {
            for (Path file : files.filter(path -> path.getFileName().toString().matches("TC-[0-9]+\\.json"))
                    .toList()) {
                countDl3Fields(read(file), counts);
            }
        }
        return counts;
    }

    private static int countTestDataFiles(Path testDataDirectory) throws IOException {
        try (var files = Files.list(testDataDirectory)) {
            return (int) files.filter(path -> path.getFileName().toString().matches("TC-[0-9]+\\.json"))
                    .count();
        }
    }

    private static void countDl3Fields(JsonNode node, Map<String, Integer> counts) {
        if (node.isObject()) {
            JsonNode dl3 = node.get("Date and Time Data Segment");
            if (dl3 != null && dl3.isObject()) {
                dl3.fieldNames().forEachRemaining(name -> counts.merge(name, 1, Integer::sum));
            }
            node.elements().forEachRemaining(child -> countDl3Fields(child, counts));
        } else if (node.isArray()) {
            node.elements().forEachRemaining(child -> countDl3Fields(child, counts));
        }
    }

    private static void validateAliases(JsonNode aliases, Map<String, Integer> observed,
                                        List<JsonNode> rules) {
        Set<String> seen = new HashSet<>();
        for (JsonNode alias : array(aliases, "fieldAliases")) {
            String name = text(alias.path("aiElementName"));
            String ruleId = text(alias.path("testRuleId"));
            if (!seen.add(name)) throw invalid("Duplicate AI field alias: " + name);
            if (!observed.containsKey(name) || observed.get(name) != alias.path("occurrencesObserved").asInt()) {
                throw invalid("AI field alias occurrence count does not match delivered data: " + name);
            }
            JsonNode rule = rules.stream().filter(row -> ruleId.equals(text(row.path("ruleId")))
                    ).findFirst().orElse(null);
            if (rule == null) {
                throw invalid("Field alias references unknown Test Solution rule: " + ruleId);
            }
            String elementNumbers = text(rule.path("sourceAnchor").path("element"));
            String expectedElement = text(alias.path("testElementNumber"));
            if (!List.of(elementNumbers.split(",")).contains(expectedElement)) {
                throw invalid("Field alias element is not anchored by its Test Solution rule: " + name);
            }
        }
        if (!seen.equals(observed.keySet())) throw invalid("Field alias crosswalk omits or invents an observed AI field name");
    }

    private static void addAliasEvidence(ObjectNode report, JsonNode aliases) {
        report.set("aiFieldAliasCrosswalk", aliases.path("fieldAliases").deepCopy());
        report.put("aiFieldAliasCrosswalkGovernance", text(aliases.path("governance")));
    }

    void addAiDataValidation(ObjectNode report, JsonNode aiData, JsonNode metadata) {
        JsonNode segment = findNamedObject(aiData, "Date and Time Data Segment");
        String indicator = field(segment, "DataTypeIndicator");
        String day = field(segment, "DayoftheWeek");
        String date = field(segment, "CurrentDate");
        String currentTime = field(segment, "CurrentTime");
        String cutTime = field(segment, "CutTime");
        String end = field(segment, "EndofDataIndicator");
        boolean requiredFieldsPresent = !day.isBlank() && !date.isBlank()
                && !currentTime.isBlank() && !cutTime.isBlank();
        boolean markerAndWidthsValid = ":".equals(indicator) && "~".equals(end)
                && day.length() == 1 && date.length() == 6 && currentTime.length() == 4
                && cutTime.length() == 4 && fieldLength(segment, "Password") == 6;
        boolean dayValid = DAY.matcher(day).matches();
        boolean dateShapeValid = SIX_DIGIT_DATE.matcher(date).matches();
        boolean clockShapeValid = FOUR_DIGIT_TIME.matcher(currentTime).matches()
                && FOUR_DIGIT_TIME.matcher(cutTime).matches();
        String family = text(metadata.path("messageFamily"));
        boolean allowedFamily = "Table Load Response".equals(family)
                || "Date and Time Load Response".equals(family);

        ArrayNode findings = report.putArray("aiTestDataRuleValidation");
        addFinding(findings, "SEGDL3-R-001", !markerAndWidthsValid ? "FAIL" : "REVIEW_REQUIRED",
                "Logical marker/width shape valid=" + markerAndWidthsValid
                        + "; serialized length and Date and Time Load Response end framing are not independently assertable.");
        addFinding(findings, "SEGDL3-R-002", requiredFieldsPresent ? "PASS" : "FAIL",
                "All required logical field slots are " + (requiredFieldsPresent ? "present" : "not present") + ".");
        addFinding(findings, "SEGDL3-R-003", "REVIEW_REQUIRED",
                "Password source conflicts between Sections 12.44 and 11.7.3.2; field value is intentionally not included in this report.");
        addFinding(findings, "SEGDL3-R-004", "NOT_ASSERTABLE",
                "The supplied artifact is structured JSON, not a serialized DL3 byte string.");
        addFinding(findings, "SEGDL3-R-005", !dayValid || !dateShapeValid || !clockShapeValid ? "FAIL"
                        : "REVIEW_REQUIRED",
                "Day valid=" + dayValid + "; Current Date has six-digit MMDDYY shape=" + dateShapeValid
                        + "; Current Time/Cut Time have four-digit HHMM shape=" + clockShapeValid
                        + "; HHMM boundary interpretation remains open under P-04.");
        addFinding(findings, "SEGDL3-R-006", "REVIEW_REQUIRED",
                "Password source/ownership remains open under P-02; no Password value is emitted or used for comparison.");
        addFinding(findings, "SEGDL3-R-007", !allowedFamily ? "FAIL"
                        : "Date and Time Load Response".equals(family) ? "REVIEW_REQUIRED" : "PASS",
                "DL3 sample message family is '" + family
                        + "'; Date and Time Load Response framing remains open under P-03.");
        addFinding(findings, "SEGDL3-R-008", "NOT_ASSERTABLE",
                "The AI sample contains no request object, Load Type, or merchant-load-flag context.");
        addFinding(findings, "SEGDL3-R-009", "NOT_ASSERTABLE",
                "No device settlement observation or automatic-cut-time lifecycle context is supplied.");
        ObjectNode totals = report.putObject("aiTestDataValidationSummary");
        totals.put("ruleCount", findings.size());
        totals.put("pass", count(findings, "PASS"));
        totals.put("fail", count(findings, "FAIL"));
        totals.put("reviewRequired", count(findings, "REVIEW_REQUIRED"));
        totals.put("notAssertable", count(findings, "NOT_ASSERTABLE"));
        totals.put("validForExecution", false);
        totals.put("sourceMessageFamily", family);
        totals.put("passwordValueIncluded", false);
    }

    private static int fieldLength(JsonNode segment, String fieldName) {
        JsonNode value = segment.path(fieldName);
        return value.isTextual() ? value.asText().length() : -1;
    }

    private static String field(JsonNode segment, String name) {
        JsonNode value = segment.path(name);
        return value.isTextual() ? value.asText() : "";
    }

    private static JsonNode findNamedObject(JsonNode node, String name) {
        if (node.isObject()) {
            JsonNode candidate = node.get(name);
            if (candidate != null && candidate.isObject()) return candidate;
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

    private static boolean containsNamedObject(JsonNode node, String name) {
        return !findNamedObject(node, name).isMissingNode();
    }

    private static void addFinding(ArrayNode findings, String ruleId, String status, String observation) {
        ObjectNode finding = findings.addObject();
        finding.put("ruleId", ruleId);
        finding.put("status", status);
        finding.put("observation", observation);
    }

    private static int count(JsonNode rows, String status) {
        int total = 0;
        for (JsonNode row : rows) if (status.equals(text(row.path("status")))) total++;
        return total;
    }

    private static boolean containsText(JsonNode values, String expected) {
        if (!values.isArray()) return false;
        for (JsonNode value : values) if (expected.equals(text(value))) return true;
        return false;
    }

    private static String markdown(JsonNode report) {
        JsonNode inventory = report.path("artifactInventory");
        JsonNode metrics = report.path("testSolutionMetrics");
        JsonNode aiCoverage = report.path("aiCoverageOfTestSolutionOracle");
        JsonNode validation = report.path("aiTestDataValidationSummary");
        StringBuilder out = new StringBuilder("# DL3 AI Artifact Coverage and Validation\n\n")
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
                .append("| Supplied AI catalog DL3 requirements | ")
                .append(inventory.path("suppliedAiCatalogDl3Requirements").asInt()).append(" |\n")
                .append("| Representative phase-one AI chains | ")
                .append(inventory.path("phaseOneRepresentativeDl3Chains").asInt()).append(" |\n\n")
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
                .append("### Per-rule candidate traceability\n\n| Rule | Cases | Positive | Negative | Status |\n|---|---:|---:|---:|---|\n");
        for (JsonNode row : report.path("testSolutionRuleCoverage")) {
            out.append("| ").append(text(row.path("ruleId"))).append(" | ")
                    .append(row.path("testCaseCount").asInt()).append(" | ")
                    .append(row.path("positiveCandidateCount").asInt()).append(" | ")
                    .append(row.path("negativeCandidateCount").asInt()).append(" | ")
                    .append(text(row.path("candidateStatus"))).append(" |\n");
        }
        out.append("\nNo candidate mappings or counts constitute SME approval, accepted AI equivalence, execution, or certification.\n\n")
                .append("## Supplied AI catalog crosswalk\n\n| AI requirement | Source rule | AI match status | Candidate Test Solution rule(s) | Disposition |\n|---|---|---|---|---|\n");
        for (JsonNode row : report.path("suppliedAiDl3Requirements")) {
            List<String> ids = new ArrayList<>();
            row.path("candidateOracleRuleIds").forEach(id -> ids.add(text(id)));
            out.append("| ").append(text(row.path("aiRequirementId"))).append(" | ")
                    .append(text(row.path("sourceRuleId"))).append(" | ")
                    .append(text(row.path("matchStatus"))).append(" | ")
                    .append(ids.isEmpty() ? "—" : String.join(", ", ids)).append(" | ")
                    .append(text(row.path("candidateDisposition"))).append(" |\n");
        }
        out.append("\nAI source-element links are candidate comparisons only; all mappings remain unconfirmed.\n\n")
                .append("## AI field-name crosswalk\n\n| AI field name | ATL105 element | Rule | Observed occurrences |\n|---|---:|---|---:|\n");
        for (JsonNode alias : report.path("aiFieldAliasCrosswalk")) {
            out.append("| ").append(text(alias.path("aiElementName"))).append(" | ")
                    .append(text(alias.path("testElementNumber"))).append(" | ")
                    .append(text(alias.path("testRuleId"))).append(" | ")
                    .append(alias.path("occurrencesObserved").asInt()).append(" |\n");
        }
        out.append("\nThe field-name crosswalk is comparison-only and does not influence oracle rules or validators.\n\n")
                .append("## Independent validation of the representative AI sample\n\n| Rule | Result | Observation |\n|---|---|---|\n");
        for (JsonNode finding : report.path("aiTestDataRuleValidation")) {
            out.append("| ").append(text(finding.path("ruleId"))).append(" | ")
                    .append(text(finding.path("status"))).append(" | ")
                    .append(text(finding.path("observation")).replace("|", "\\|")).append(" |\n");
        }
        out.append("\nTotals: **").append(validation.path("pass").asInt()).append(" PASS**, **")
                .append(validation.path("fail").asInt()).append(" FAIL**, **")
                .append(validation.path("reviewRequired").asInt()).append(" REVIEW_REQUIRED**, **")
                .append(validation.path("notAssertable").asInt()).append(" NOT_ASSERTABLE**.\n\n")
                .append("The representative sample is one chain, not an exhaustive DL3 AI package. Password values are deliberately excluded from this report. P-01 through P-04 remain open; no data is approved or executed.");
        return out.toString();
    }

    private static String consolidatedText(JsonNode report) {
        JsonNode metrics = report.path("testSolutionMetrics");
        JsonNode validation = report.path("aiTestDataValidationSummary");
        JsonNode progress = report.path("implementationItemProgress");
        StringBuilder out = new StringBuilder("SEGMENT DL3 CONSOLIDATED TEST SOLUTION READINESS\n")
                .append("Status: INCOMPLETE_REVIEW_REQUIRED\n")
                .append("Specification: ATL105 2026-3; Date and Time Data Segment (DL3)\n")
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
                .append("Supplied AI catalog DL3 requirements: ")
                .append(report.path("aiCoverageOfTestSolutionOracle").path("suppliedAiDl3RequirementCount").asInt()).append('\n')
                .append("Representative phase-one chain: ")
                .append(text(report.path("phaseOneAiChain").path("testCaseId")))
                .append(" (non-exhaustive; no request in its test-data payload)\n")
                .append("Independent sample validation: ").append(validation.path("pass").asInt()).append(" PASS, ")
                .append(validation.path("fail").asInt()).append(" FAIL, ")
                .append(validation.path("reviewRequired").asInt()).append(" REVIEW_REQUIRED, ")
                .append(validation.path("notAssertable").asInt()).append(" NOT_ASSERTABLE\n")
                .append("Password values are excluded from this report.\n\n")
                .append("OPEN SME GATES\n")
                .append("P-01 / SEGDL3-SME-001: AI/Test fixture package or synthetic fixture approval.\n")
                .append("P-02 / SEGDL3-SME-002: Password source, role, ownership, and safe isolated fixture.\n")
                .append("P-03 / SEGDL3-SME-003: '~' and length in Date and Time Load Response.\n")
                .append("P-04 / SEGDL3-SME-004: HHMM boundary values.\n\n")
                .append("EIGHT-ITEM IMPLEMENTATION STATUS\n");
        progress.fields().forEachRemaining(entry -> out.append(entry.getKey()).append(": ")
                .append(entry.getValue().asText()).append('\n'));
        return out.append("\nNEXT: Resolve the SME gates, approve isolated synthetic data, then execute source-critical mutations and device lifecycle checks.").toString();
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

    private static String text(JsonNode node) {
        return node.isMissingNode() || node.isNull() ? "" : node.asText();
    }

    private static double percent(int numerator, int denominator) {
        return denominator == 0 ? 0.0 : Math.round(numerator * 1000.0 / denominator) / 10.0;
    }
}
