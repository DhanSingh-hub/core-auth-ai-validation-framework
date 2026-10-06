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

public final class GenerateSegmentDl6AiArtifactCoverageReport {
    private static final Pattern HHMM = Pattern.compile("([01][0-9]|2[0-3])[0-5][0-9]");
    private final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        Path docs = Atl105Paths.docs().resolve(Path.of("specs", "kb", "segment-DL6", "coverage"));
        Path aiCatalog = Atl105Paths.testOutput().resolve(Path.of(
                "ai-artifacts", "coverage-reports", "supplied-ai-catalog", "supplied-ai-catalog-coverage.json"));
        Path aliasCrosswalk = Atl105Paths.root().resolve(Path.of(
                "contract", "segment-DL6-field-alias-crosswalk.json"));
        new GenerateSegmentDl6AiArtifactCoverageReport().generate(runRoot,
                Atl105Paths.testJson("segment-DL6-coverage-package.json"),
                Atl105Paths.ruleCatalog("DL6"), aiCatalog, aliasCrosswalk,
                docs.resolve("segment-DL6-ai-artifact-coverage-report.json"),
                docs.resolve("segment-DL6-ai-artifact-coverage-report.md"),
                Atl105Paths.consolidatedReport("DL6"));
    }

    JsonNode generate(Path runRoot, Path packageFile, Path catalogFile, Path aiCatalogFile,
                      Path aliasCrosswalkFile, Path reportJson, Path reportMarkdown,
                      Path consolidatedReport) throws IOException {
        Path chainFile = runRoot.resolve(Path.of("chains", "segment_DL6.json"));
        Path dataFile = runRoot.resolve(Path.of("test_data", "TC-000006.json"));
        Path metadataFile = runRoot.resolve(Path.of("test_data", "TC-000006.meta.json"));
        for (Path required : List.of(chainFile, dataFile, metadataFile, packageFile, catalogFile,
                aiCatalogFile, aliasCrosswalkFile)) {
            if (!Files.isRegularFile(required)) throw new IOException("Required DL6 coverage input is missing: " + required);
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
                .filter(row -> "DL6".equals(text(row.path("segment")))).toList();

        validatePackage(testPackage, rules, brs, scenarios, cases, testData, ruleCatalog);
        validateChain(chain, metadata);
        validateAliases(aliases, observeAliases(runRoot.resolve("test_data")), rules);

        ObjectNode report = mapper.createObjectNode();
        report.put("artifact", "segment-dl6-ai-artifact-coverage");
        report.put("specification", "ATL105");
        report.put("specificationVersion", "2026-3");
        report.put("segment", "DL6");
        report.put("status", "INCOMPLETE_REVIEW_REQUIRED");
        report.put("authority", "INDEPENDENT_TEST_SOLUTION");
        report.put("aiInputScope", "Supplied AI DL6 catalog filter plus one representative phase-one chain and payload.");
        report.put("smeStatus", "P-01 through P-06 OPEN, including SEGDL1-SME-003 for block-boundary placement.");
        report.put("testSolutionPackagePath", "specifications/ATL105/test-output/test-json/segment-DL6-coverage-package.json");
        report.put("correctedCandidatePath", "specifications/ATL105/test-output/test-json/segment-DL6-ai-corrected-candidate.json");
        report.put("suppliedAiCatalogPath", "specifications/ATL105/test-output/ai-artifacts/coverage-reports/supplied-ai-catalog/supplied-ai-catalog-coverage.json");
        report.put("fieldAliasCrosswalkPath", "specifications/ATL105/contract/segment-DL6-field-alias-crosswalk.json");
        ObjectNode inventory = report.putObject("artifactInventory");
        inventory.put("testSolutionRules", rules.size());
        inventory.put("testSolutionBusinessRequirements", brs.size());
        inventory.put("testSolutionScenarios", scenarios.size());
        inventory.put("testSolutionTestCases", cases.size());
        inventory.put("testSolutionTestDataRecords", testData.size());
        inventory.put("suppliedAiCatalogDl6Requirements", aiRecords.size());
        inventory.put("phaseOneRepresentativeDl6Chains", 1);
        inventory.put("phaseOneTestDataFilesScannedForFieldNames", countTestDataFiles(runRoot.resolve("test_data")));

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

    private static void validatePackage(JsonNode pkg, List<JsonNode> rules, List<JsonNode> brs,
                                        List<JsonNode> scenarios, List<JsonNode> cases,
                                        List<JsonNode> data, JsonNode catalog) {
        requireUniqueIds(brs, "businessRequirements");
        requireUniqueIds(scenarios, "testScenarios");
        requireUniqueIds(cases, "testCases");
        requireUniqueIds(data, "testData");
        Map<String, JsonNode> ruleById = index(rules, "ruleId");
        Map<String, JsonNode> brById = index(brs, "id");
        Map<String, JsonNode> scenarioById = index(scenarios, "id");
        Map<String, JsonNode> caseById = index(cases, "id");
        Map<String, JsonNode> dataById = index(data, "id");
        Map<String, JsonNode> provisionalById = index(array(catalog, "provisionalItems"), "id");
        Set<String> mappedRules = new HashSet<>();
        Set<String> linkedCases = new HashSet<>();
        Set<String> linkedData = new HashSet<>();
        Map<String, Set<String>> polarityByBr = new HashMap<>();

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
                if (!brById.containsKey(text(requirementId))) throw invalid("Scenario references an unknown BR: " + text(requirementId));
            }
        }
        for (JsonNode testCase : cases) {
            String id = text(testCase.path("id"));
            String brId = text(testCase.path("requirementId"));
            String polarity = text(testCase.path("candidatePolarity"));
            if (!"POSITIVE".equals(polarity) && !"NEGATIVE".equals(polarity)) {
                throw invalid("Test case requires explicit POSITIVE or NEGATIVE candidatePolarity: " + id);
            }
            polarityByBr.computeIfAbsent(brId, ignored -> new HashSet<>()).add(polarity);
            if (!brById.containsKey(brId)) throw invalid("Test case references an unknown BR: " + id);
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
            Set<String> polarities = polarityByBr.getOrDefault(text(br.path("id")), Set.of());
            if (!polarities.contains("POSITIVE") || !polarities.contains("NEGATIVE")) {
                throw invalid("Every rule requires POSITIVE and NEGATIVE candidates: " + text(br.path("id")));
            }
        }
        for (JsonNode record : data) {
            String id = text(record.path("id"));
            JsonNode payload = record.path("payload");
            if (!payload.isObject() || !payload.path("request").isObject() || !payload.path("response").isObject()) {
                throw invalid("Test data requires request and response objects: " + id);
            }
            for (JsonNode caseId : array(record, "testCaseIds")) {
                JsonNode testCase = caseById.get(text(caseId));
                if (testCase == null || !containsText(testCase.path("testDataIds"), id)) {
                    throw invalid("Test-data and test-case links disagree: " + id);
                }
            }
        }
        if (linkedCases.size() != cases.size() || linkedData.size() != data.size()) throw invalid("Test case and test-data traceability is incomplete");
        if (pkg.path("coverageSummary").path("approvedTestDataPairs").asInt() != 0
                || pkg.path("coverageSummary").path("executedTestCases").asInt() != 0
                || pkg.path("coverageSummary").path("certifiedCoveredRules").asInt() != 0) {
            throw invalid("Unapproved candidates cannot count as approved, executed, or certified coverage");
        }
    }

    private static void validateChain(JsonNode chain, JsonNode metadata) {
        for (String key : List.of("requirement", "scenario", "test_case")) {
            if (!chain.path(key).isObject() || text(chain.path(key).path("id")).isBlank()) throw invalid("Representative AI chain is missing " + key);
        }
        if (!text(chain.path("requirement").path("id")).equals(text(metadata.path("requirementId")))
                || !text(chain.path("scenario").path("id")).equals(text(metadata.path("scenarioId")))
                || !text(chain.path("test_case").path("id")).equals(text(metadata.path("testCaseId")))) {
            throw invalid("AI chain IDs do not agree with the test-data metadata");
        }
        if (!"TC-000006".equals(text(chain.path("test_case").path("id")))) throw invalid("Representative DL6 chain must point to TC-000006");
        if (!"2026-3".equals(text(chain.path("scenario").path("spec_version")))) throw invalid("Representative AI chain is not ATL105 2026-3");
    }

    private static void addChainSummary(ObjectNode report, JsonNode chain, JsonNode aiData, JsonNode metadata) {
        ObjectNode summary = report.putObject("phaseOneAiChain");
        summary.put("requirementId", text(chain.path("requirement").path("id")));
        summary.put("scenarioId", text(chain.path("scenario").path("id")));
        summary.put("testCaseId", text(chain.path("test_case").path("id")));
        summary.put("sourceRuleId", text(chain.path("requirement").path("source_rule_id")));
        summary.put("sourceEvidencePage", chain.path("requirement").path("source_page").asInt());
        summary.put("metadataMessageFamily", text(metadata.path("messageFamily")));
        summary.put("chainIdentityStatus", "ID_CONSISTENT");
        summary.put("scope", "One representative AI chain, not exhaustive DL6 package traceability.");
        summary.put("dl6DataPresent", !findNamedObject(aiData, "Store and Forward Data Segment").isMissingNode());
        summary.put("tableLoadResponse", "Table Load Response".equals(text(metadata.path("messageFamily"))));
    }

    private static void addCandidateMetrics(ObjectNode report, List<JsonNode> rules, List<JsonNode> brs,
                                            List<JsonNode> scenarios, List<JsonNode> cases, List<JsonNode> data) {
        ObjectNode metrics = report.putObject("testSolutionMetrics");
        ArrayNode byRule = report.putArray("testSolutionRuleCoverage");
        int complete = 0;
        int positiveNegative = 0;
        for (JsonNode rule : rules) {
            String ruleId = text(rule.path("ruleId"));
            JsonNode br = brs.stream().filter(row -> ruleId.equals(text(row.path("ruleId")))).findFirst()
                    .orElseThrow(() -> invalid("Missing BR for catalog rule " + ruleId));
            String brId = text(br.path("id"));
            boolean hasScenario = scenarios.stream().anyMatch(row -> containsText(row.path("requirementIds"), brId));
            List<JsonNode> ruleCases = cases.stream().filter(row -> brId.equals(text(row.path("requirementId")))).toList();
            boolean linksValid = !ruleCases.isEmpty() && ruleCases.stream().allMatch(testCase ->
                    array(testCase, "testDataIds").stream().allMatch(dataId -> data.stream()
                            .anyMatch(td -> text(td.path("id")).equals(text(dataId)))));
            if (hasScenario && linksValid) complete++;
            boolean positive = ruleCases.stream().anyMatch(row -> "POSITIVE".equals(text(row.path("candidatePolarity"))));
            boolean negative = ruleCases.stream().anyMatch(row -> "NEGATIVE".equals(text(row.path("candidatePolarity"))));
            if (positive && negative) positiveNegative++;
            ObjectNode row = byRule.addObject();
            row.put("ruleId", ruleId);
            row.put("businessRequirementId", brId);
            row.put("testCaseCount", ruleCases.size());
            row.put("positiveCandidateCount", (int) ruleCases.stream().filter(testCase -> "POSITIVE".equals(text(testCase.path("candidatePolarity")))).count());
            row.put("negativeCandidateCount", (int) ruleCases.stream().filter(testCase -> "NEGATIVE".equals(text(testCase.path("candidatePolarity")))).count());
            boolean review = br.has("provisionalItem") || ruleCases.stream().anyMatch(testCase -> "REVIEW_REQUIRED".equals(text(testCase.path("status"))));
            row.put("candidateStatus", review ? "CANDIDATE_CHAIN_REVIEW_REQUIRED" : "CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED");
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
        metrics.put("interpretation", "Candidate definitions are traceability evidence only; SME approval, execution, and certification remain separate gates.");
    }

    private static void addAiCatalogCrosswalk(ObjectNode report, List<JsonNode> rules, List<JsonNode> records) {
        ArrayNode crosswalk = report.putArray("suppliedAiDl6Requirements");
        Set<String> mappedRules = new HashSet<>();
        for (JsonNode record : records) {
            List<String> candidates = candidateRulesForAiSource(text(record.path("sourceRuleId")));
            ObjectNode row = crosswalk.addObject();
            row.put("aiRequirementId", text(record.path("aiRequirementId")));
            row.put("sourceRuleId", text(record.path("sourceRuleId")));
            row.put("matchStatus", text(record.path("matchStatus")));
            row.put("candidateDisposition", candidates.isEmpty() ? "NO_CANDIDATE_ORACLE_RULE" : "CANDIDATE_MAPPING_REVIEW_REQUIRED");
            ArrayNode ids = row.putArray("candidateOracleRuleIds");
            for (String id : candidates) if (rules.stream().anyMatch(rule -> id.equals(text(rule.path("ruleId"))))) { ids.add(id); mappedRules.add(id); }
        }
        ObjectNode metrics = report.putObject("aiCoverageOfTestSolutionOracle");
        metrics.put("oracleRuleCount", rules.size());
        metrics.put("suppliedAiDl6RequirementCount", records.size());
        metrics.put("rulesWithCandidateAiMappingsPendingReview", mappedRules.size());
        metrics.put("rulesWithoutCandidateAiCatalogMapping", rules.size() - mappedRules.size());
        metrics.put("confirmedSemanticMatches", 0);
        metrics.put("interpretation", "Source element links and heuristic match statuses do not establish semantic equivalence. AI records do not define the oracle denominator.");
    }

    private static List<String> candidateRulesForAiSource(String sourceRule) {
        if ("ENT-ELEM-24".equals(sourceRule) || "ENT-ELEM-34".equals(sourceRule)
                || "ENT-FIELD-DL6-1".equals(sourceRule) || "ENT-FIELD-DL6-4".equals(sourceRule)) return List.of("SEGDL6-R-002");
        if ("ENT-ELEM-166".equals(sourceRule) || "ENT-FIELD-DL6-2".equals(sourceRule)
                || "ENT-FIELD-DL6-3".equals(sourceRule)) return List.of("SEGDL6-R-003", "SEGDL6-R-005", "SEGDL6-R-007");
        return List.of();
    }

    private static Map<String, Integer> observeAliases(Path testDataDirectory) throws IOException {
        Map<String, Integer> counts = new HashMap<>();
        try (var paths = Files.list(testDataDirectory)) {
            for (Path path : paths.filter(file -> file.getFileName().toString().matches("TC-[0-9]+\\.json")).toList()) countFields(read(path), counts);
        }
        return counts;
    }

    private static void countFields(JsonNode node, Map<String, Integer> counts) {
        if (node.isObject()) {
            JsonNode dl6 = node.get("Store and Forward Data Segment");
            if (dl6 != null && dl6.isObject()) dl6.fieldNames().forEachRemaining(name -> counts.merge(name, 1, Integer::sum));
            node.elements().forEachRemaining(child -> countFields(child, counts));
        } else if (node.isArray()) node.elements().forEachRemaining(child -> countFields(child, counts));
    }

    private static int countTestDataFiles(Path directory) throws IOException {
        try (var files = Files.list(directory)) {
            return (int) files.filter(file -> file.getFileName().toString().matches("TC-[0-9]+\\.json")).count();
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
            JsonNode rule = rules.stream().filter(row -> ruleId.equals(text(row.path("ruleId")))).findFirst().orElse(null);
            if (rule == null) throw invalid("Unknown Test Solution rule in AI alias: " + ruleId);
            if (!List.of(text(rule.path("sourceAnchor").path("element")).split(",")).contains(text(alias.path("testElementNumber")))) {
                throw invalid("AI alias element is not anchored by its Test Solution rule: " + name);
            }
        }
        if (!names.equals(observed.keySet())) throw invalid("Field crosswalk must exactly match observed AI field names");
    }

    void addAiDataValidation(ObjectNode report, JsonNode aiData, JsonNode metadata) {
        JsonNode dl6 = findNamedObject(aiData, "Store and Forward Data Segment");
        String indicator = field(dl6, "DataTypeIndicator");
        String start = field(dl6, "StartTime");
        String end = field(dl6, "EndTime");
        String terminator = field(dl6, "EndofDataIndicator");
        boolean fieldsPresent = !dl6.isMissingNode() && List.of("DataTypeIndicator", "StartTime", "EndTime", "EndofDataIndicator").stream().allMatch(dl6::has);
        boolean markersCorrect = "\\".equals(indicator) && "~".equals(terminator);
        int logicalLength = indicator.length() + start.length() + end.length() + terminator.length();
        boolean hhmm = HHMM.matcher(start).matches() && HHMM.matcher(end).matches();
        boolean softwareLoadPhoneResponse = "Software Load Phone Response".equals(text(metadata.path("messageFamily")));
        boolean tableLoadResponse = "Table Load Response".equals(text(metadata.path("messageFamily")));

        ArrayNode findings = report.putArray("aiTestDataRuleValidation");
        addFinding(findings, "SEGDL6-R-001", "NOT_ASSERTABLE", "The supplied payload has DL1-shaped data but no Card Type 173 field or Table Load Response applicability context.");
        addFinding(findings, "SEGDL6-R-002", !markersCorrect ? "FAIL" : "REVIEW_REQUIRED",
                "DL6 marker fields are present=" + fieldsPresent + ", marker correctness=" + markersCorrect
                        + ", logical field length=" + logicalLength + "; the stated 9-versus-10 length issue remains open.");
        addFinding(findings, "SEGDL6-R-003", fieldsPresent ? "REVIEW_REQUIRED" : "FAIL",
                "Start and End Time slots are present=" + fieldsPresent + "; Element 166 reuse remains open under P-01.");
        addFinding(findings, "SEGDL6-R-004", "NOT_ASSERTABLE", "The AI artifact is structured JSON; Field Separator placement cannot be checked on serialized bytes.");
        addFinding(findings, "SEGDL6-R-005", hhmm ? "PASS" : "FAIL", "Start Time and End Time satisfy HHMM 0000-2359 shape=" + hhmm + ".");
        addFinding(findings, "SEGDL6-R-006", tableLoadResponse ? "REVIEW_REQUIRED" : "FAIL",
                "Metadata message family is Software Load Phone Response=" + softwareLoadPhoneResponse + "; DL6 Data Block 4 Table Load placement is therefore not demonstrated.");
        addFinding(findings, "SEGDL6-R-007", "NOT_ASSERTABLE", "No device store-and-forward processing observation, local time basis, or across-midnight behavior is supplied.");

        ObjectNode summary = report.putObject("aiTestDataValidationSummary");
        summary.put("ruleCount", findings.size());
        summary.put("pass", count(findings, "PASS"));
        summary.put("fail", count(findings, "FAIL"));
        summary.put("reviewRequired", count(findings, "REVIEW_REQUIRED"));
        summary.put("notAssertable", count(findings, "NOT_ASSERTABLE"));
        summary.put("validForExecution", false);
        summary.put("sourceMessageFamily", text(metadata.path("messageFamily")));
        summary.put("sensitiveValueIncluded", false);
    }

    private static String markdown(JsonNode report) {
        JsonNode inventory = report.path("artifactInventory");
        JsonNode metrics = report.path("testSolutionMetrics");
        JsonNode validation = report.path("aiTestDataValidationSummary");
        StringBuilder out = new StringBuilder("# DL6 AI Artifact Coverage and Validation\n\n")
                .append("**Status:** ").append(text(report.path("status"))).append("\n\n")
                .append("**AI scope:** ").append(text(report.path("aiInputScope"))).append("\n\n")
                .append("**Open decisions:** ").append(text(report.path("smeStatus"))).append("\n\n")
                .append("## Artifact inventory\n\n| Evidence | Count |\n|---|---:|\n")
                .append("| Oracle rules | ").append(inventory.path("testSolutionRules").asInt()).append(" |\n")
                .append("| Candidate BR / TS / TC / TD | ").append(inventory.path("testSolutionBusinessRequirements").asInt()).append(" / ")
                .append(inventory.path("testSolutionScenarios").asInt()).append(" / ").append(inventory.path("testSolutionTestCases").asInt()).append(" / ")
                .append(inventory.path("testSolutionTestDataRecords").asInt()).append(" |\n")
                .append("| Supplied AI catalog DL6 requirements | ").append(inventory.path("suppliedAiCatalogDl6Requirements").asInt()).append(" |\n")
                .append("| Representative phase-one AI chains | ").append(inventory.path("phaseOneRepresentativeDl6Chains").asInt()).append(" |\n\n")
                .append("## Candidate coverage (not execution coverage)\n\n| Measure | Result |\n|---|---:|\n")
                .append("| Rules with candidate BR → TS → TC → TD links | ").append(metrics.path("rulesWithCandidateBrTsTcTdChains").asInt()).append(" / ")
                .append(metrics.path("oracleRuleCount").asInt()).append(" (").append(metrics.path("candidateChainPercent").asText()).append("%) |\n")
                .append("| Rules with positive and negative candidates | ").append(metrics.path("rulesWithPositiveAndNegativeCandidates").asInt()).append(" / ")
                .append(metrics.path("oracleRuleCount").asInt()).append(" (").append(metrics.path("positiveNegativeCandidatePercent").asText()).append("%) |\n")
                .append("| Approved pairs / executed cases / certified rules | 0 / 0 / 0 |\n\n")
                .append("### Per-rule traceability\n\n| Rule | Cases | Positive | Negative | Status |\n|---|---:|---:|---:|---|\n");
        for (JsonNode row : report.path("testSolutionRuleCoverage")) out.append("| ").append(text(row.path("ruleId"))).append(" | ")
                .append(row.path("testCaseCount").asInt()).append(" | ").append(row.path("positiveCandidateCount").asInt()).append(" | ")
                .append(row.path("negativeCandidateCount").asInt()).append(" | ").append(text(row.path("candidateStatus"))).append(" |\n");
        out.append("\n## Supplied AI catalog crosswalk\n\n| AI requirement | Source rule | AI match status | Candidate oracle rule | Disposition |\n|---|---|---|---|---|\n");
        for (JsonNode row : report.path("suppliedAiDl6Requirements")) {
            List<String> ids = new ArrayList<>();
            row.path("candidateOracleRuleIds").forEach(id -> ids.add(text(id)));
            out.append("| ").append(text(row.path("aiRequirementId"))).append(" | ").append(text(row.path("sourceRuleId"))).append(" | ")
                    .append(text(row.path("matchStatus"))).append(" | ").append(ids.isEmpty() ? "—" : String.join(", ", ids)).append(" | ")
                    .append(text(row.path("candidateDisposition"))).append(" |\n");
        }
        out.append("\nAll mappings remain unconfirmed candidate source-element links. The supplied catalog currently has ")
                .append(report.path("aiCoverageOfTestSolutionOracle").path("suppliedAiDl6RequirementCount").asInt())
                .append(" records where segment == DL6.\n\n## Observed AI field-name crosswalk\n\n| AI field | Element | Rule | Occurrences |\n|---|---:|---|---:|\n");
        for (JsonNode alias : report.path("aiFieldAliasCrosswalk")) out.append("| ").append(text(alias.path("aiElementName"))).append(" | ")
                .append(text(alias.path("testElementNumber"))).append(" | ").append(text(alias.path("testRuleId"))).append(" | ")
                .append(alias.path("occurrencesObserved").asInt()).append(" |\n");
        out.append("\n## Independent sample validation\n\n| Rule | Result | Observation |\n|---|---|---|\n");
        for (JsonNode row : report.path("aiTestDataRuleValidation")) out.append("| ").append(text(row.path("ruleId"))).append(" | ")
                .append(text(row.path("status"))).append(" | ").append(text(row.path("observation")).replace("|", "\\|")).append(" |\n");
        return out.append("\nTotals: **").append(validation.path("pass").asInt()).append(" PASS**, **")
                .append(validation.path("fail").asInt()).append(" FAIL**, **").append(validation.path("reviewRequired").asInt())
                .append(" REVIEW_REQUIRED**, **").append(validation.path("notAssertable").asInt()).append(" NOT_ASSERTABLE**.\n\n")
                .append("The AI sample is one representative chain, not an exhaustive package. Sensitive phone, password, IP/URL, and key values are excluded from this report. P-01 through P-06 remain open; no data is approved or executed.")
                .toString();
    }

    private static String consolidatedText(JsonNode report) {
        JsonNode metrics = report.path("testSolutionMetrics");
        JsonNode validation = report.path("aiTestDataValidationSummary");
        StringBuilder out = new StringBuilder("SEGMENT DL6 CONSOLIDATED TEST SOLUTION READINESS\n")
                .append("Status: INCOMPLETE_REVIEW_REQUIRED\n")
                .append("Specification: ATL105 2026-3; Store and Forward Data Segment (DL6)\n")
                .append("Authority: Independent Test Solution; AI evidence is comparison input only.\n\n")
                .append("CANDIDATE COVERAGE\n")
                .append("Oracle rules: ").append(metrics.path("oracleRuleCount").asInt()).append('\n')
                .append("Candidate BR-TS-TC-TD chains: ").append(metrics.path("rulesWithCandidateBrTsTcTdChains").asInt()).append('/')
                .append(metrics.path("oracleRuleCount").asInt()).append('\n')
                .append("Rules with positive and negative candidates: ").append(metrics.path("rulesWithPositiveAndNegativeCandidates").asInt()).append('/')
                .append(metrics.path("oracleRuleCount").asInt()).append('\n')
                .append("Candidate cases / symbolic data pairs: ").append(metrics.path("candidateTestCases").asInt()).append(" / ")
                .append(metrics.path("candidateRequestResponsePairs").asInt()).append('\n')
                .append("Approved data pairs / executed cases / certified rules: 0 / 0 / 0\n\n")
                .append("AI EVIDENCE\n")
                .append("Supplied AI catalog DL6 requirements: ").append(report.path("aiCoverageOfTestSolutionOracle").path("suppliedAiDl6RequirementCount").asInt()).append('\n')
                .append("Representative chain: ").append(text(report.path("phaseOneAiChain").path("testCaseId"))).append(" (non-exhaustive phase-one scope)\n")
                .append("Sample validation: ").append(validation.path("pass").asInt()).append(" PASS, ").append(validation.path("fail").asInt()).append(" FAIL, ")
                .append(validation.path("reviewRequired").asInt()).append(" REVIEW_REQUIRED, ").append(validation.path("notAssertable").asInt()).append(" NOT_ASSERTABLE\n")
                .append("Sensitive phone, password, IP/URL, and key values are excluded from all reports.\n\n")
                .append("OPEN SME GATES\n")
                .append("P-01 / SEGDL6-SME-001: Element 166 reuse for Start and End Time.\n")
                .append("P-02 / SEGDL6-SME-002: Fixture and AI/Test package approval.\n")
                .append("P-03 / SEGDL6-SME-003: Maximum length 9 vs 10.\n")
                .append("P-04 / SEGDL6-SME-004: Field 1, Element 34 list, and End Time source inconsistencies.\n")
                .append("P-05 / SEGDL6-SME-005: Daily window behavior semantics.\n")
                .append("P-06 / SEGDL1-SME-003: Table Load Data Block 4 delimiter placement.\n\nEIGHT-ITEM STATUS\n");
        report.path("implementationItemProgress").fields().forEachRemaining(entry -> out.append(entry.getKey()).append(": ").append(entry.getValue().asText()).append('\n'));
        return out.append("\nNEXT: Resolve SME decisions, approve isolated synthetic data, then execute serializer-aware and behavior-level validations.").toString();
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
        return new IllegalArgumentException("Invalid DL6 coverage package: " + message);
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
