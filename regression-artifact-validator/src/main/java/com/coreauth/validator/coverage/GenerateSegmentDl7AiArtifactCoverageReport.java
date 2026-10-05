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

public final class GenerateSegmentDl7AiArtifactCoverageReport {
    private static final Pattern NUMERIC_3 = Pattern.compile("[0-9]{3}");
    private static final Pattern TLV_PREFIX = Pattern.compile("[0-9]{3}[0-9]{3}.+");
    private static final Pattern ALPHANUMERIC_SPACE = Pattern.compile("[A-Za-z0-9 ]*");
    private final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        Path docs = Atl105Paths.docs().resolve(Path.of("specs", "kb", "segment-DL7", "coverage"));
        Path aiCatalog = Atl105Paths.testOutput().resolve(Path.of(
                "ai-artifacts", "coverage-reports", "supplied-ai-catalog", "supplied-ai-catalog-coverage.json"));
        Path aliasCrosswalk = Atl105Paths.root().resolve(Path.of(
                "contract", "segment-DL7-field-alias-crosswalk.json"));
        new GenerateSegmentDl7AiArtifactCoverageReport().generate(runRoot,
                Atl105Paths.testJson("segment-DL7-coverage-package.json"),
                Atl105Paths.ruleCatalog("DL7"), aiCatalog, aliasCrosswalk,
                docs.resolve("segment-DL7-ai-artifact-coverage-report.json"),
                docs.resolve("segment-DL7-ai-artifact-coverage-report.md"),
                Atl105Paths.consolidatedReport("DL7"));
    }

    JsonNode generate(Path runRoot, Path packageFile, Path catalogFile, Path aiCatalogFile,
                      Path aliasCrosswalkFile, Path reportJson, Path reportMarkdown,
                      Path consolidatedReport) throws IOException {
        Path chainFile = runRoot.resolve(Path.of("chains", "segment_DL7.json"));
        for (Path required : List.of(chainFile, packageFile, catalogFile, aiCatalogFile, aliasCrosswalkFile)) {
            if (!Files.isRegularFile(required)) {
                throw new IOException("Required DL7 coverage input is missing: " + required);
            }
        }

        JsonNode chain = read(chainFile);
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
                .filter(row -> "DL7".equalsIgnoreCase(text(row.path("segment")))).toList();
        List<Path> matchingMetadata = findMatchingMetadata(runRoot.resolve("test_data"), chain);

        validatePackage(testPackage, rules, brs, scenarios, cases, testData, ruleCatalog);
        validateChainGap(chain, matchingMetadata);
        Map<String, Integer> observedAliases = observeAliases(runRoot.resolve("test_data"));
        validateAliases(aliases, observedAliases, rules);

        ObjectNode report = mapper.createObjectNode();
        report.put("artifact", "segment-dl7-ai-artifact-coverage");
        report.put("specification", "ATL105");
        report.put("specificationVersion", "2026-3");
        report.put("segment", "DL7");
        report.put("status", "INCOMPLETE_REVIEW_REQUIRED");
        report.put("authority", "INDEPENDENT_TEST_SOLUTION");
        report.put("aiInputScope", "Supplied AI catalog has no DL7-tagged records; phase-one DL7 chain is a gap record without generated TC/meta payload.");
        report.put("smeStatus", "P-01 through P-05 OPEN");
        report.put("testSolutionPackagePath", "specifications/ATL105/test-output/test-json/segment-DL7-coverage-package.json");
        report.put("correctedCandidatePath", "specifications/ATL105/test-output/test-json/segment-DL7-ai-corrected-candidate.json");
        report.put("suppliedAiCatalogPath", "specifications/ATL105/test-output/ai-artifacts/coverage-reports/supplied-ai-catalog/supplied-ai-catalog-coverage.json");
        report.put("fieldAliasCrosswalkPath", "specifications/ATL105/contract/segment-DL7-field-alias-crosswalk.json");

        ObjectNode inventory = report.putObject("artifactInventory");
        inventory.put("testSolutionRules", rules.size());
        inventory.put("testSolutionBusinessRequirements", brs.size());
        inventory.put("testSolutionScenarios", scenarios.size());
        inventory.put("testSolutionTestCases", cases.size());
        inventory.put("testSolutionTestDataRecords", testData.size());
        inventory.put("suppliedAiCatalogDl7Requirements", aiRecords.size());
        inventory.put("phaseOneRepresentativeDl7Chains", 1);
        inventory.put("phaseOneMatchingDl7MetadataFiles", matchingMetadata.size());
        inventory.put("phaseOneTestDataFilesScannedForFieldNames", countTestDataFiles(runRoot.resolve("test_data")));

        addChainSummary(report, chain, matchingMetadata);
        addCandidateMetrics(report, rules, brs, scenarios, cases, testData);
        addAiCatalogCrosswalk(report, rules, aiRecords);
        report.set("aiFieldAliasCrosswalk", aliases.path("fieldAliases").deepCopy());
        report.put("aiFieldAliasCrosswalkGovernance", text(aliases.path("governance")));
        addAiDataValidation(report, mapper.createObjectNode(), mapper.createObjectNode());
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
        Map<String, Set<String>> polaritiesByRule = new HashMap<>();
        Set<String> linkedCases = new HashSet<>();
        Set<String> linkedData = new HashSet<>();

        for (JsonNode br : brs) {
            String ruleId = text(br.path("ruleId"));
            JsonNode rule = ruleById.get(ruleId);
            if (rule == null) throw invalid("Unknown rule in BR: " + ruleId);
            if (!sameSourceAnchor(rule.path("sourceAnchor"), br.path("sourceAnchor"))) {
                throw invalid("BR source anchor does not match catalog rule: " + text(br.path("id")));
            }
            if (!mappedRules.add(ruleId)) throw invalid("Duplicate BR mapping for rule: " + ruleId);
            String provisionalItem = text(br.path("provisionalItem"));
            if (provisionalItem.isBlank() || provisionalById.get(provisionalItem) == null
                    || !"OPEN".equals(text(provisionalById.get(provisionalItem).path("status")))) {
                throw invalid("DL7 BR must reference an open provisional item: " + text(br.path("id")));
            }
            if (!"REVIEW_REQUIRED".equals(text(br.path("status")))) {
                throw invalid("DL7 candidates must remain REVIEW_REQUIRED: " + text(br.path("id")));
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
                throw invalid("Test case requires explicit candidatePolarity: " + id);
            }
            JsonNode br = brById.get(brId);
            if (br == null) throw invalid("Test case references an unknown BR: " + id);
            polaritiesByRule.computeIfAbsent(text(br.path("ruleId")), ignored -> new HashSet<>()).add(polarity);
            for (JsonNode scenarioId : array(testCase, "scenarioIds")) {
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
        for (JsonNode rule : rules) {
            Set<String> polarities = polaritiesByRule.getOrDefault(text(rule.path("ruleId")), Set.of());
            if (!polarities.containsAll(Set.of("POSITIVE", "NEGATIVE"))) {
                throw invalid("Rule lacks positive and negative candidates: " + text(rule.path("ruleId")));
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

    private static void validateChainGap(JsonNode chain, List<Path> matchingMetadata) {
        if (!"DL7".equals(text(chain.path("segment_number")))) throw invalid("Representative chain is not DL7");
        if (!"REQ-SRC-ATL105-PDF-001:0810".equals(text(chain.path("requirement").path("id")))) {
            throw invalid("Unexpected DL7 representative requirement");
        }
        if (!"GAP_RESOLVER_FALLBACK_UNRELIABLE".equals(text(chain.path("gap").path("status")))) {
            throw invalid("DL7 phase-one chain should remain a gap until a dedicated fixture exists");
        }
        if (!matchingMetadata.isEmpty()) {
            throw invalid("DL7 gap chain unexpectedly has generated metadata: " + matchingMetadata);
        }
    }

    private static void addChainSummary(ObjectNode report, JsonNode chain, List<Path> matchingMetadata) {
        ObjectNode summary = report.putObject("phaseOneAiChain");
        summary.put("requirementId", text(chain.path("requirement").path("id")));
        summary.put("scenarioId", text(chain.path("gap").path("scenario_id")));
        summary.put("testCaseId", "NONE_GAP_CHAIN");
        summary.put("sourceRuleId", text(chain.path("requirement").path("source_rule_id")));
        summary.put("sourceEvidencePage", chain.path("requirement").path("source_page").asInt());
        summary.put("gapStatus", text(chain.path("gap").path("status")));
        summary.put("matchingMetadataFiles", matchingMetadata.size());
        summary.put("validForExecution", false);
    }

    private static void addCandidateMetrics(ObjectNode report, List<JsonNode> rules, List<JsonNode> brs,
                                            List<JsonNode> scenarios, List<JsonNode> cases,
                                            List<JsonNode> testData) {
        ObjectNode metrics = report.putObject("testSolutionMetrics");
        metrics.put("rulesWithBusinessRequirement", brs.size());
        metrics.put("rulesWithScenario", scenarios.size());
        metrics.put("candidateTestCases", cases.size());
        metrics.put("candidateTestDataRecords", testData.size());
        int rulesWithBoth = 0;
        for (JsonNode rule : rules) {
            String ruleId = text(rule.path("ruleId"));
            Set<String> polarities = new HashSet<>();
            for (JsonNode br : brs) {
                if (!ruleId.equals(text(br.path("ruleId")))) continue;
                for (JsonNode testCase : cases) {
                    if (text(br.path("id")).equals(text(testCase.path("requirementId")))) {
                        polarities.add(text(testCase.path("candidatePolarity")));
                    }
                }
            }
            if (polarities.containsAll(Set.of("POSITIVE", "NEGATIVE"))) rulesWithBoth++;
        }
        metrics.put("rulesWithPositiveAndNegativeCandidates", rulesWithBoth);
    }

    private void addAiCatalogCrosswalk(ObjectNode report, List<JsonNode> rules, List<JsonNode> aiRecords) {
        ObjectNode summary = report.putObject("aiCoverageOfTestSolutionOracle");
        summary.put("status", "CANDIDATE_MAPPING_REVIEW_REQUIRED");
        summary.put("dl7AiCatalogRecords", aiRecords.size());
        summary.put("confirmedSemanticMatches", 0);
        summary.put("mappingBasis", "The supplied catalog has no rows where segment == DL7; no semantic match is confirmed.");
        ArrayNode rows = summary.putArray("ruleRows");
        for (JsonNode rule : rules) {
            ObjectNode row = rows.addObject();
            row.put("ruleId", text(rule.path("ruleId")));
            row.put("aiCatalogRecords", 0);
            row.put("status", "CANDIDATE_MAPPING_REVIEW_REQUIRED");
            row.put("confirmedSemanticMatch", false);
        }
    }

    void addAiDataValidation(ObjectNode report, JsonNode aiData, JsonNode metadata) {
        JsonNode dl7 = findNamedObject(aiData, "Supplemental Terminal Data Segment");
        ArrayNode findings = report.putArray("aiTestDataRuleValidation");
        if (dl7.isMissingNode()) {
            addFinding(findings, "SEGDL7-R-001", "NOT_ASSERTABLE",
                    "No representative DL7 payload exists; message placement remains open under P-04.");
            addFinding(findings, "SEGDL7-R-002", "NOT_ASSERTABLE",
                    "No Segment Length value exists and P-05 is open.");
            addFinding(findings, "SEGDL7-R-003", "NOT_ASSERTABLE",
                    "No Download Data TLV payload exists and Appendix W scope remains open under P-01.");
            addFinding(findings, "SEGDL7-R-004", "NOT_ASSERTABLE",
                    "No Appendix W table payload exists and table-data length semantics remain open under P-03.");
            addFinding(findings, "SEGDL7-R-005", "NOT_ASSERTABLE",
                    "No Download Data payload exists; spanning and message context remain open under P-04.");
            addFinding(findings, "SEGDL7-R-006", "NOT_ASSERTABLE",
                    "No three-digit Segment Length payload exists and P-05 is open.");
        } else {
            String indicator = firstField(dl7, "DataTypeIndicator", "dataTypeIndicator");
            String length = firstField(dl7, "SegmentLengthIndicator", "segmentLengthIndicator");
            String download = firstField(dl7, "DownloadData", "downloadData");
            boolean hasEnd = dl7.has("EndofDataIndicator") || dl7.has("endOfDataIndicator");
            addFinding(findings, "SEGDL7-R-001",
                    "^".equals(indicator) && !hasEnd ? "REVIEW_REQUIRED" : "FAIL",
                    "Checks only marker shape and absence of an End-of-Data field; message placement remains open under P-04.");
            addFinding(findings, "SEGDL7-R-002", "NOT_ASSERTABLE",
                    "P-05 must decide whether the three length digits are included in the count.");
            addFinding(findings, "SEGDL7-R-003",
                    TLV_PREFIX.matcher(download).matches() ? "REVIEW_REQUIRED" : "FAIL",
                    "Checks TLV shape only; Appendix W scope remains open under P-01.");
            addFinding(findings, "SEGDL7-R-004", appendixWShape(download) ? "REVIEW_REQUIRED" : "FAIL",
                    "Checks only table-id and declared-length shape; P-03 controls final parser semantics.");
            addFinding(findings, "SEGDL7-R-005",
                    download.length() <= 100 && ALPHANUMERIC_SPACE.matcher(download).matches()
                            ? "REVIEW_REQUIRED" : "FAIL",
                    "Checks Download Data character class and maximum only; DL7 spanning remains open under P-04.");
            addFinding(findings, "SEGDL7-R-006",
                    NUMERIC_3.matcher(length).matches() ? "REVIEW_REQUIRED" : "FAIL",
                    "Checks only three-digit shape; equality semantics remain open under P-05.");
        }
        ObjectNode summary = report.putObject("aiTestDataValidationSummary");
        summary.put("ruleCount", findings.size());
        summary.put("pass", count(findings, "PASS"));
        summary.put("fail", count(findings, "FAIL"));
        summary.put("reviewRequired", count(findings, "REVIEW_REQUIRED"));
        summary.put("notAssertable", count(findings, "NOT_ASSERTABLE"));
        summary.put("validForExecution", false);
        summary.put("sensitivePayloadValuesIncluded", false);
    }

    private static boolean appendixWShape(String download) {
        if (download.startsWith("001003") && download.length() >= 9) return true;
        return download.startsWith("002013") && download.length() >= 19;
    }

    private static void addFinding(ArrayNode findings, String ruleId, String status, String rationale) {
        ObjectNode finding = findings.addObject();
        finding.put("ruleId", ruleId);
        finding.put("status", status);
        finding.put("rationale", rationale);
    }

    private static int count(ArrayNode findings, String status) {
        int count = 0;
        for (JsonNode finding : findings) {
            if (status.equals(text(finding.path("status")))) count++;
        }
        return count;
    }

    private String markdown(JsonNode report) {
        StringBuilder out = new StringBuilder();
        out.append("# Segment DL7 AI Artifact Coverage Report\n\n");
        out.append("Status: **").append(text(report.path("status"))).append("**\n");
        out.append("Authority: **").append(text(report.path("authority"))).append("**\n");
        out.append("SME status: ").append(text(report.path("smeStatus"))).append("\n\n");
        JsonNode inventory = report.path("artifactInventory");
        out.append("## Inventory\n\n");
        out.append("- Rules: ").append(inventory.path("testSolutionRules").asInt()).append('\n');
        out.append("- BR/TS/TC/TD: ").append(inventory.path("testSolutionBusinessRequirements").asInt())
                .append('/').append(inventory.path("testSolutionScenarios").asInt())
                .append('/').append(inventory.path("testSolutionTestCases").asInt())
                .append('/').append(inventory.path("testSolutionTestDataRecords").asInt()).append('\n');
        out.append("- Supplied AI catalog DL7 records: ")
                .append(inventory.path("suppliedAiCatalogDl7Requirements").asInt()).append("\n\n");
        out.append("## AI catalog crosswalk\n\n");
        out.append("Status: **").append(text(report.path("aiCoverageOfTestSolutionOracle").path("status")))
                .append("**; confirmed semantic matches: ")
                .append(report.path("aiCoverageOfTestSolutionOracle").path("confirmedSemanticMatches").asInt())
                .append(".\n\n");
        out.append("## Sample validation\n\n");
        for (JsonNode finding : array(report, "aiTestDataRuleValidation")) {
            out.append("- ").append(text(finding.path("ruleId"))).append(": ")
                    .append(text(finding.path("status"))).append(" — ")
                    .append(text(finding.path("rationale"))).append('\n');
        }
        return out.toString();
    }

    private String consolidatedText(JsonNode report) {
        JsonNode inventory = report.path("artifactInventory");
        JsonNode validation = report.path("aiTestDataValidationSummary");
        return """
                SEGMENT DL7 CONSOLIDATED REPORT
                =================================
                Status: %s
                Authority: %s
                Rules/BR/TS/TC/TD: %d/%d/%d/%d/%d
                Supplied AI catalog DL7 records: %d
                AI mapping status: %s
                Sample validation PASS/FAIL/REVIEW/NOT_ASSERTABLE: %d/%d/%d/%d
                Open SME items: P-01, P-02, P-03, P-04, P-05
                Execution: NOT_EXECUTED; approved pairs: 0; certified rules: 0
                """.formatted(
                text(report.path("status")),
                text(report.path("authority")),
                inventory.path("testSolutionRules").asInt(),
                inventory.path("testSolutionBusinessRequirements").asInt(),
                inventory.path("testSolutionScenarios").asInt(),
                inventory.path("testSolutionTestCases").asInt(),
                inventory.path("testSolutionTestDataRecords").asInt(),
                inventory.path("suppliedAiCatalogDl7Requirements").asInt(),
                text(report.path("aiCoverageOfTestSolutionOracle").path("status")),
                validation.path("pass").asInt(),
                validation.path("fail").asInt(),
                validation.path("reviewRequired").asInt(),
                validation.path("notAssertable").asInt());
    }

    private static List<Path> findMatchingMetadata(Path testDataDirectory, JsonNode chain) throws IOException {
        String requirement = text(chain.path("requirement").path("id"));
        String scenario = text(chain.path("gap").path("scenario_id"));
        List<Path> matches = new ArrayList<>();
        try (var files = Files.list(testDataDirectory)) {
            for (Path file : files.filter(path -> path.getFileName().toString().endsWith(".meta.json")).toList()) {
                JsonNode meta = new ObjectMapper().readTree(file.toFile());
                if (requirement.equals(text(meta.path("requirementId")))
                        || scenario.equals(text(meta.path("scenarioId")))
                        || "ENT-FIELD-DL7-1".equals(text(meta.path("sourceRuleId")))) {
                    matches.add(file);
                }
            }
        }
        return matches;
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
            JsonNode dl7 = node.get("Supplemental Terminal Data Segment");
            if (dl7 != null && dl7.isObject()) {
                dl7.fieldNames().forEachRemaining(name -> counts.merge(name, 1, Integer::sum));
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

    private static JsonNode findNamedObject(JsonNode node, String name) {
        if (node.isObject()) {
            JsonNode direct = node.get(name);
            if (direct != null && direct.isObject()) return direct;
            for (JsonNode child : node) {
                JsonNode found = findNamedObject(child, name);
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

    private static String firstField(JsonNode node, String... names) {
        for (String name : names) {
            if (node.has(name)) return text(node.path(name));
        }
        return "";
    }

    private static boolean containsText(JsonNode array, String expected) {
        for (JsonNode value : array(array)) {
            if (expected.equals(text(value))) return true;
        }
        return false;
    }

    private static JsonNode read(Path path) throws IOException {
        return new ObjectMapper().readTree(path.toFile());
    }

    private static List<JsonNode> array(JsonNode node, String field) {
        return array(node.path(field));
    }

    private static List<JsonNode> array(JsonNode node) {
        List<JsonNode> result = new ArrayList<>();
        if (node.isArray()) node.forEach(result::add);
        return result;
    }

    private static String text(JsonNode node) {
        return node.isMissingNode() || node.isNull() ? "" : node.asText();
    }

    private static IllegalArgumentException invalid(String message) {
        return new IllegalArgumentException("Invalid DL7 coverage package: " + message);
    }
}
