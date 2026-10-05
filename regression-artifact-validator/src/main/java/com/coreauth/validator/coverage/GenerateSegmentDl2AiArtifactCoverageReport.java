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

public final class GenerateSegmentDl2AiArtifactCoverageReport {
    private static final String DL2_ENTITY = "ENT-SEG-DL2";
    private static final String VERSION = "2026-3";
    private static final Pattern REDIAL = Pattern.compile("[1-3]");
    private static final Pattern PHONE = Pattern.compile("[0-9]{1,18}");
    private final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        Path docs = Atl105Paths.docs().resolve(Path.of("specs", "kb", "segment-DL2", "coverage"));
        GenerateSegmentDl2AiArtifactCoverageReport generator =
                new GenerateSegmentDl2AiArtifactCoverageReport();
        Path suppliedAiCatalog = Atl105Paths.testOutput().resolve(Path.of(
                "ai-artifacts", "coverage-reports", "supplied-ai-catalog", "supplied-ai-catalog-coverage.json"));
        Path aliasCrosswalk = Atl105Paths.root().resolve(Path.of(
                "contract", "segment-DL2-field-alias-crosswalk.json"));
        generator.generate(runRoot, Atl105Paths.testJson("segment-DL2-coverage-package.json"),
                Atl105Paths.ruleCatalog("DL2"), suppliedAiCatalog, aliasCrosswalk,
                Atl105Paths.testJson("segment-DL2-ai-corrected-candidate.json"),
                docs.resolve("segment-DL2-ai-artifact-coverage-report.json"),
                docs.resolve("segment-DL2-ai-artifact-coverage-report.md"),
                Atl105Paths.consolidatedReport("DL2"));
    }

    JsonNode generate(Path runRoot, Path packageFile, Path catalogFile, Path suppliedAiCatalogFile,
                      Path aliasCrosswalkFile, Path correctedCandidate,
                      Path reportJson, Path reportMarkdown, Path consolidatedReport) throws IOException {
        Path chainFile = runRoot.resolve(Path.of("chains", "segment_DL2.json"));
        Path dataFile = runRoot.resolve(Path.of("test_data", "TC-000002.json"));
        Path metadataFile = runRoot.resolve(Path.of("test_data", "TC-000002.meta.json"));
        Path requirementsFile = runRoot.resolve(Path.of("requirements", "requirement_candidates.json"));
        Path scenariosFile = runRoot.resolve(Path.of("scenarios", "candidate_scenarios.json"));
        Path casesFile = runRoot.resolve(Path.of("test_cases", "test_case_candidates.json"));
        Path indexFile = runRoot.resolve("INDEX.json");
        for (Path required : List.of(chainFile, dataFile, metadataFile, requirementsFile,
                scenariosFile, casesFile, indexFile, packageFile, catalogFile,
                suppliedAiCatalogFile, aliasCrosswalkFile, correctedCandidate)) {
            if (!Files.isRegularFile(required)) {
                throw new IOException("Required DL2 coverage input is missing: " + required);
            }
        }

        JsonNode chain = read(chainFile);
        JsonNode aiData = read(dataFile);
        JsonNode metadata = read(metadataFile);
        JsonNode requirementsRoot = read(requirementsFile);
        JsonNode scenariosRoot = read(scenariosFile);
        JsonNode casesRoot = read(casesFile);
        JsonNode index = read(indexFile);
        JsonNode testSolution = read(packageFile);
        JsonNode catalog = read(catalogFile);
        JsonNode suppliedAiCatalog = read(suppliedAiCatalogFile);
        JsonNode aliases = read(aliasCrosswalkFile);
        List<JsonNode> suppliedAiRecords = array(suppliedAiCatalog, "records");
        List<JsonNode> dl2AiCatalogRequirements = suppliedAiRecords.stream()
                .filter(row -> "DL2".equalsIgnoreCase(text(row.path("segment")))).toList();

        List<JsonNode> allRequirements = array(requirementsRoot, "requirements");
        List<JsonNode> allScenarios = array(scenariosRoot, "scenarios");
        List<JsonNode> allCases = array(casesRoot, "test_cases");
        List<JsonNode> dl2Requirements = allRequirements.stream()
                .filter(row -> "DL2".equalsIgnoreCase(text(row.path("segment_number")))).toList();
        List<JsonNode> dl2Scenarios = allScenarios.stream()
                .filter(row -> containsText(row.path("related_entity_ids"), DL2_ENTITY)).toList();
        Set<String> dl2RequirementIds = new HashSet<>();
        dl2Requirements.forEach(row -> dl2RequirementIds.add(text(row.path("id"))));
        List<JsonNode> dl2Cases = allCases.stream()
                .filter(row -> dl2RequirementIds.contains(text(row.path("requirement_id")))).toList();
        List<JsonNode> rules = array(catalog, "rules");
        List<JsonNode> brs = array(testSolution, "businessRequirements");
        List<JsonNode> scenarios = array(testSolution, "testScenarios");
        List<JsonNode> cases = array(testSolution, "testCases");
        List<JsonNode> dataRecords = array(testSolution, "testDataRecords");
        validateAliases(aliases, observeAliases(runRoot.resolve("test_data")), rules);

        ObjectNode report = mapper.createObjectNode();
        report.put("artifact", "segment-dl2-ai-artifact-coverage");
        report.put("specification", "ATL105");
        report.put("specificationVersion", VERSION);
        report.put("segment", "DL2");
        report.put("status", "INCOMPLETE_REVIEW_REQUIRED");
        report.put("authority", "INDEPENDENT_TEST_SOLUTION");
        report.put("aiInputScope", "Supplied AI DL2 catalog crosswalk plus one representative phase-one DL2 chain and payload.");
        report.put("smeStatus", "P-01 through P-04 OPEN");
        report.put("testSolutionPackagePath", "specifications/ATL105/test-output/test-json/segment-DL2-coverage-package.json");
        report.put("correctedCandidatePath", "specifications/ATL105/test-output/test-json/segment-DL2-ai-corrected-candidate.json");
        report.put("suppliedAiCatalogPath",
                "specifications/ATL105/test-output/ai-artifacts/coverage-reports/supplied-ai-catalog/supplied-ai-catalog-coverage.json");
        report.put("fieldAliasCrosswalkPath", "specifications/ATL105/contract/segment-DL2-field-alias-crosswalk.json");
        addInventory(report, allRequirements, allScenarios, allCases, dl2Requirements,
                dl2Scenarios, dl2Cases, dl2AiCatalogRequirements.size(), index,
                countTestDataFiles(runRoot.resolve("test_data")));
        addChainIntegrity(report, chain, metadata, dataFile, metadataFile);
        addCandidateMetrics(report, rules, brs, scenarios, cases, dataRecords);
        addAiEvidence(report, rules, dl2Requirements, dl2AiCatalogRequirements);
        report.set("aiFieldAliasCrosswalk", aliases.path("fieldAliases").deepCopy());
        report.put("aiFieldAliasCrosswalkGovernance", text(aliases.path("governance")));
        addAiDataValidation(report, aiData, metadata);
        report.set("implementationItemProgress", catalog.path("itemProgress").deepCopy());
        Files.createDirectories(reportJson.toAbsolutePath().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(reportJson.toFile(), report);
        Files.writeString(reportMarkdown, markdown(report));
        Files.createDirectories(consolidatedReport.toAbsolutePath().getParent());
        Files.writeString(consolidatedReport, consolidatedText(report));
        return report;
    }

    private void addInventory(ObjectNode report, List<JsonNode> allRequirements,
                              List<JsonNode> allScenarios, List<JsonNode> allCases,
                              List<JsonNode> dl2Requirements, List<JsonNode> dl2Scenarios,
                              List<JsonNode> dl2Cases, int dl2AiCatalogRequirements, JsonNode index,
                              int testDataFilesScanned) {
        ObjectNode inventory = report.putObject("aiArtifactInventory");
        inventory.put("phaseOneRequirements", allRequirements.size());
        inventory.put("phaseOneScenarios", allScenarios.size());
        inventory.put("phaseOneTestCases", allCases.size());
        inventory.put("dl2Requirements", dl2Requirements.size());
        inventory.put("dl2Scenarios", dl2Scenarios.size());
        inventory.put("dl2TestCases", dl2Cases.size());
        inventory.put("suppliedAiCatalogDl2Requirements", dl2AiCatalogRequirements);
        inventory.put("dl2TestDataFiles", 1);
        inventory.put("dl2MetadataFiles", 1);
        inventory.put("phaseOneTestDataFilesScannedForFieldNames", testDataFilesScanned);
        inventory.put("indexStatus", segmentStatus(index));
        inventory.put("scope", "Phase-one generator intentionally produces one representative requirement per segment.");
    }

    private void addChainIntegrity(ObjectNode report, JsonNode chain, JsonNode metadata,
                                   Path dataFile, Path metadataFile) {
        JsonNode requirement = chain.path("requirement");
        JsonNode evidence = requirement.path("evidence_ref");
        String requirementId = text(requirement.path("id"));
        String scenarioId = text(chain.path("scenario").path("id"));
        String testCaseId = text(chain.path("test_case").path("id"));
        boolean idsMatch = !requirementId.isBlank()
                && requirementId.equals(text(chain.path("scenario").path("requirement_id")))
                && requirementId.equals(text(chain.path("test_case").path("requirement_id")))
                && scenarioId.equals(text(chain.path("test_case").path("scenario_id")))
                && requirementId.equals(text(metadata.path("requirementId")))
                && scenarioId.equals(text(metadata.path("scenarioId")))
                && testCaseId.equals(text(metadata.path("testCaseId")));
        boolean evidenceMatches = !text(requirement.path("source_rule_id")).isBlank()
                && text(requirement.path("source_rule_id")).equals(text(metadata.path("sourceRuleId")))
                && text(requirement.path("source_rule_id")).equals(text(evidence.path("entity_id")))
                && requirement.path("source_page").asInt(-1) == evidence.path("page").asInt(-2)
                && requirement.path("source_page").asInt(-1) == metadata.path("sourcePage").asInt(-2)
                && containsText(requirement.path("related_entity_ids"), DL2_ENTITY);
        ObjectNode integrity = report.putObject("aiChainIntegrity");
        integrity.put("requirementId", requirementId);
        integrity.put("scenarioId", scenarioId);
        integrity.put("testCaseId", testCaseId);
        integrity.put("testDataAndMetadataPresent", Files.isRegularFile(dataFile) && Files.isRegularFile(metadataFile));
        integrity.put("idsConsistentAcrossChainAndMetadata", idsMatch);
        integrity.put("sourceEvidenceConsistent", evidenceMatches);
        integrity.put("traceabilityStatus", idsMatch && evidenceMatches ? "COMPLETE" : "GAP");
        integrity.put("producerCompletenessStatus", text(chain.path("test_case").path("completeness_status")));
        integrity.put("independentValidationNote", "Producer completeness describes construction; DL2 behavior must pass independent validation.");
        integrity.put("transactionType", text(chain.path("test_case").path("transaction_type")));
        ArrayNode issues = integrity.putArray("issues");
        if (!idsMatch) issues.add("Requirement, scenario, test case, and metadata identifiers do not form one consistent chain.");
        if (!evidenceMatches) issues.add("Requirement evidence reference, segment attribution, or source metadata is inconsistent.");
    }

    private void addCandidateMetrics(ObjectNode report, List<JsonNode> rules, List<JsonNode> brs,
                                     List<JsonNode> scenarios, List<JsonNode> cases, List<JsonNode> records) {
        Map<String, JsonNode> dataByCase = new HashMap<>();
        records.forEach(row -> dataByCase.put(text(row.path("testCaseId")), row));
        ArrayNode coverage = report.putArray("testSolutionRuleCoverage");
        int completeRules = 0;
        int positiveNegativeRules = 0;
        int brokenLinks = 0;
        int totalCaseCount = 0;
        int totalPairCount = 0;
        for (JsonNode rule : rules) {
            String ruleId = text(rule.path("ruleId"));
            List<JsonNode> linkedBrs = brs.stream().filter(br -> ruleId.equals(text(br.path("ruleId")))).toList();
            Set<String> brIds = new HashSet<>();
            linkedBrs.forEach(br -> brIds.add(text(br.path("id"))));
            List<JsonNode> linkedScenarios = scenarios.stream()
                    .filter(scenario -> containsAny(scenario.path("requirementIds"), brIds)).toList();
            Set<String> scenarioIds = new HashSet<>();
            linkedScenarios.forEach(scenario -> scenarioIds.add(text(scenario.path("id"))));
            List<JsonNode> linkedCases = cases.stream()
                    .filter(testCase -> brIds.contains(text(testCase.path("requirementId")))
                            && scenarioIds.contains(text(testCase.path("scenarioId")))).toList();
            List<JsonNode> linkedData = linkedCases.stream()
                    .filter(testCase -> hasRequestResponse(dataByCase.get(text(testCase.path("id"))))).toList();
            long positives = linkedCases.stream().filter(row -> text(row.path("expectedOutcome")).startsWith("PASS")).count();
            long negatives = linkedCases.stream().filter(row -> text(row.path("expectedOutcome")).startsWith("FAIL")).count();
            boolean complete = !linkedBrs.isEmpty() && !linkedScenarios.isEmpty()
                    && !linkedCases.isEmpty() && linkedData.size() == linkedCases.size();
            if (complete) completeRules++;
            if (complete && positives > 0 && negatives > 0) positiveNegativeRules++;
            if (linkedData.size() != linkedCases.size()) brokenLinks++;
            totalCaseCount += linkedCases.size();
            totalPairCount += linkedData.size();
            ObjectNode row = coverage.addObject();
            row.put("ruleId", ruleId);
            row.put("requirementCount", linkedBrs.size());
            row.put("scenarioCount", linkedScenarios.size());
            row.put("testCaseCount", linkedCases.size());
            row.put("testDataPairCount", linkedData.size());
            row.put("positiveTestCaseCount", positives);
            row.put("negativeTestCaseCount", negatives);
            row.put("candidateChainStatus", complete ? "COMPLETE_NOT_EXECUTED" : "INCOMPLETE");
            row.put("requirementStatus", linkedBrs.isEmpty() ? "MISSING"
                    : text(linkedBrs.get(0).path("status")));
        }
        ObjectNode metrics = report.putObject("testSolutionMetrics");
        metrics.put("oracleRuleCount", rules.size());
        metrics.put("rulesWithCompleteCandidateChains", completeRules);
        metrics.put("completeCandidateChainPercent", percent(completeRules, rules.size()));
        metrics.put("rulesWithPositiveAndNegativeCandidates", positiveNegativeRules);
        metrics.put("positiveNegativeCandidatePercent", percent(positiveNegativeRules, rules.size()));
        metrics.put("candidateTestCases", totalCaseCount);
        metrics.put("candidateRequestResponsePairs", totalPairCount);
        metrics.put("brokenDataLinks", brokenLinks);
        metrics.put("approvedTestDataPairs", 0);
        metrics.put("executedTestCases", 0);
        metrics.put("executionCoveragePercent", 0.0);
        metrics.put("certifiedCoveredRules", 0);
        metrics.put("interpretation", "Candidate definitions are traceability evidence only; they have not been approved or executed.");
    }

    private void addAiEvidence(ObjectNode report, List<JsonNode> rules, List<JsonNode> phaseOneRequirements,
                               List<JsonNode> aiCatalogRequirements) {
        Map<String, JsonNode> catalogByRule = new HashMap<>();
        rules.forEach(rule -> catalogByRule.put(text(rule.path("ruleId")), rule));
        ArrayNode aiCatalog = report.putArray("suppliedAiDl2Requirements");
        Map<String, List<JsonNode>> aiRowsByRule = new HashMap<>();
        for (JsonNode aiRequirement : aiCatalogRequirements) {
            String sourceRuleId = text(aiRequirement.path("sourceRuleId"));
            List<String> candidateRuleIds = candidateRulesForAiSource(sourceRuleId);
            ObjectNode row = aiCatalog.addObject();
            row.put("aiRequirementId", text(aiRequirement.path("aiRequirementId")));
            row.put("sourceRuleId", sourceRuleId);
            row.put("matchStatus", text(aiRequirement.path("matchStatus")));
            row.put("heuristicTestRequirementId", text(aiRequirement.path("testRequirementId")));
            row.put("candidateDisposition", candidateRuleIds.isEmpty()
                    ? "NO_CANDIDATE_ORACLE_RULE" : "CANDIDATE_MAPPING_REVIEW_REQUIRED");
            ArrayNode candidateIds = row.putArray("candidateOracleRuleIds");
            for (String ruleId : candidateRuleIds) {
                if (catalogByRule.containsKey(ruleId)) {
                    candidateIds.add(ruleId);
                    aiRowsByRule.computeIfAbsent(ruleId, ignored -> new ArrayList<>()).add(aiRequirement);
                }
            }
        }

        ArrayNode crosswalk = report.putArray("aiEvidenceByOracleRule");
        int candidateMappedRules = 0;
        for (JsonNode rule : rules) {
            String id = text(rule.path("ruleId"));
            List<JsonNode> aiRows = aiRowsByRule.getOrDefault(id, List.of());
            boolean phaseOneIndicator = phaseOneRequirements.stream()
                    .anyMatch(req -> text(req.path("source_rule_id")).equals("ENT-FIELD-DL2-1"))
                    && "SEGDL2-R-001".equals(id);
            ObjectNode row = crosswalk.addObject();
            row.put("ruleId", id);
            row.put("aiEvidenceStatus", aiRows.isEmpty() ? "MISSING" : "PARTIAL_REVIEW_REQUIRED");
            row.put("phaseOneSampleHasIndicatorRequirement", phaseOneIndicator);
            ArrayNode ids = row.putArray("aiCatalogRequirementIds");
            aiRows.forEach(ai -> ids.add(text(ai.path("aiRequirementId"))));
            if (!aiRows.isEmpty()) candidateMappedRules++;
        }
        ObjectNode metrics = report.putObject("aiCoverageOfTestSolutionOracle");
        int missing = rules.size() - candidateMappedRules;
        metrics.put("oracleRuleCount", rules.size());
        metrics.put("suppliedAiDl2RequirementCount", aiCatalogRequirements.size());
        metrics.put("rulesWithDirectFullAiEvidence", 0);
        metrics.put("rulesWithCandidateAiMappingsPendingReview", candidateMappedRules);
        metrics.put("rulesWithoutAiEvidence", missing);
        metrics.put("candidateAiMappingPercent", percent(candidateMappedRules, rules.size()));
        metrics.put("noAiEvidencePercent", percent(missing, rules.size()));
        metrics.put("phaseOneSampleRequirementCount", phaseOneRequirements.size());
        metrics.put("phaseOneSampleScope", "One representative requirement per segment.");
        metrics.put("interpretation", "Catalog crosswalks are candidate mappings only. Heuristic match statuses and shared source element IDs do not prove semantic equivalence; all AI mappings remain SME/source review required. No AI requirement fully covers a composite DL2 oracle rule.");
    }

    private static List<String> candidateRulesForAiSource(String sourceRuleId) {
        return switch (sourceRuleId) {
            case "ENT-ELEM-1", "ENT-ELEM-66", "ENT-FIELD-DL2-4", "ENT-FIELD-DL2-5" ->
                    List.of("SEGDL2-R-005");
            case "ENT-ELEM-27", "ENT-ELEM-75", "ENT-FIELD-DL2-6", "ENT-FIELD-DL2-7" ->
                    List.of("SEGDL2-R-007");
            case "ENT-ELEM-28", "ENT-FIELD-DL2-2" -> List.of("SEGDL2-R-002");
            case "ENT-ELEM-82", "ENT-FIELD-DL2-3" -> List.of("SEGDL2-R-006");
            case "ENT-FIELD-DL2-1" -> List.of("SEGDL2-R-001");
            default -> List.of();
        };
    }

    private static Map<String, Integer> observeAliases(Path testDataDirectory) throws IOException {
        Map<String, Integer> counts = new HashMap<>();
        try (var files = Files.list(testDataDirectory)) {
            for (Path file : files.filter(path -> path.getFileName().toString().matches("TC-[0-9]+\\.json"))
                    .toList()) {
                countDl2Fields(read(file), counts);
            }
        }
        return counts;
    }

    private static void countDl2Fields(JsonNode node, Map<String, Integer> counts) {
        if (node.isObject()) {
            JsonNode dl2 = node.get("Dial String Data Segment");
            if (dl2 != null && dl2.isObject()) {
                dl2.fieldNames().forEachRemaining(name -> counts.merge(name, 1, Integer::sum));
            }
            node.elements().forEachRemaining(child -> countDl2Fields(child, counts));
        } else if (node.isArray()) {
            node.elements().forEachRemaining(child -> countDl2Fields(child, counts));
        }
    }

    private static int countTestDataFiles(Path testDataDirectory) throws IOException {
        try (var files = Files.list(testDataDirectory)) {
            return (int) files.filter(path -> path.getFileName().toString().matches("TC-[0-9]+\\.json"))
                    .count();
        }
    }

    private static void validateAliases(JsonNode aliases, Map<String, Integer> observed, List<JsonNode> rules) {
        Set<String> seen = new HashSet<>();
        for (JsonNode alias : array(aliases, "fieldAliases")) {
            String name = text(alias.path("aiElementName"));
            String ruleId = text(alias.path("testRuleId"));
            if (!seen.add(name)) throw new IllegalArgumentException("Duplicate AI field alias: " + name);
            if (!observed.containsKey(name) || observed.get(name) != alias.path("occurrencesObserved").asInt()) {
                throw new IllegalArgumentException("AI field alias occurrence count does not match delivered data: " + name);
            }
            JsonNode rule = rules.stream().filter(row -> ruleId.equals(text(row.path("ruleId"))))
                    .findFirst().orElse(null);
            if (rule == null) {
                throw new IllegalArgumentException("Field alias references unknown Test Solution rule: " + ruleId);
            }
            String elementNumbers = text(rule.path("sourceAnchor").path("element"));
            String expectedElement = text(alias.path("testElementNumber"));
            if (!List.of(elementNumbers.split(",")).contains(expectedElement)) {
                throw new IllegalArgumentException("Field alias element is not anchored by its Test Solution rule: " + name);
            }
        }
        if (!seen.equals(observed.keySet())) {
            throw new IllegalArgumentException("Field alias crosswalk omits or invents an observed AI field name");
        }
    }

    void addAiDataValidation(ObjectNode report, JsonNode aiData, JsonNode metadata) {
        String messageType = aiData.fieldNames().hasNext() ? aiData.fieldNames().next() : "";
        JsonNode response = aiData.path(messageType);
        JsonNode segment = response.path("Dial String Data Segment");
        JsonNode primary = firstObject(segment, "primary", "PrimaryPhoneNumberBlock", "PrimaryDialString");
        JsonNode secondary = firstObject(segment, "secondary", "SecondaryPhoneNumberBlock", "SecondaryDialString");
        if (primary.isMissingNode()) primary = segment;
        String indicator = firstText(segment, "DataTypeIndicator", "dataTypeIndicator");
        String dialType = firstText(segment, "DialStringType", "dialStringType");
        String primaryRedial = firstText(primary, "RedialCount", "redialCount");
        String accessCode = firstText(primary, "AccessCode", "accessCode");
        String pause = firstText(primary, "PauseIndicator", "pauseIndicator");
        String phone = firstText(primary, "PhoneNumber", "phoneNumber");
        String primaryTerminator = firstText(primary, "DialStringTerminator", "terminator");
        String secondaryRedial = firstText(secondary, "RedialCount", "redialCount");
        String secondaryAccess = firstText(secondary, "AccessCode", "accessCode");
        String secondaryPause = firstText(secondary, "PauseIndicator", "pauseIndicator");
        String secondaryPhone = firstText(secondary, "PhoneNumber", "phoneNumber");
        String secondaryTerminator = firstText(secondary, "DialStringTerminator", "terminator");
        boolean secondaryExists = !secondary.isMissingNode()
                || hasAny(segment, "SecondaryPhoneNumber", "SecondaryDialString", "SecondaryPhoneNumberBlock");
        String endMarker = firstText(segment, "EndofDataIndicator", "EndOfDataIndicator", "endOfDataIndicator");
        boolean markersValid = "!".equals(indicator) && "~".equals(endMarker);
        boolean primaryPairValid = accessCode.isBlank() == pause.isBlank()
                && (pause.isBlank() || "B".equals(pause));
        boolean secondaryPairValid = !secondaryExists || (secondaryAccess.isBlank() == secondaryPause.isBlank()
                && (secondaryPause.isBlank() || "B".equals(secondaryPause)));
        boolean primaryRedialValid = REDIAL.matcher(primaryRedial).matches();
        boolean secondaryRedialValid = REDIAL.matcher(secondaryRedial).matches();
        boolean primaryPhoneValid = PHONE.matcher(phone).matches();
        boolean secondaryPhoneValid = PHONE.matcher(secondaryPhone).matches();
        boolean secondaryStructureValid = secondaryExists && secondaryRedialValid
                && secondaryPhoneValid && "F".equals(secondaryTerminator);
        ArrayNode findings = report.putArray("aiTestDataRuleValidation");
        addFinding(findings, "SEGDL2-R-001", !markersValid ? "FAIL" : "REVIEW_REQUIRED",
                "Required '!' marker " + ("!".equals(indicator) ? "is present" : "is missing or incorrect")
                        + "; End-of-Data '~' " + ("~".equals(endMarker) ? "is correct" : "is missing or incorrect")
                        + "; total serialized length remains not assertable from structured fields.");
        addFinding(findings, "SEGDL2-R-002", "1".equals(dialType)
                        && !primaryRedial.isBlank() && !phone.isBlank() && "A".equals(primaryTerminator) ? "PASS" : "FAIL",
                "Dial String Type, required primary fields, and primary A terminator are "
                        + ("1".equals(dialType) && !primaryRedial.isBlank() && !phone.isBlank()
                        && "A".equals(primaryTerminator) ? "present" : "missing or invalid") + ".");
        addFinding(findings, "SEGDL2-R-003", !secondaryExists || !secondaryStructureValid
                        ? "FAIL" : "REVIEW_REQUIRED",
                !secondaryExists ? "Secondary phone block and F terminator are absent."
                        : !secondaryStructureValid ? "Secondary block is present but its redial count, phone number, or F terminator is invalid."
                        : "Secondary block structure is valid; detailed fallback timing remains open under P-01.");
        addFinding(findings, "SEGDL2-R-004", "NOT_ASSERTABLE",
                "The AI artifact is structured JSON, not serialized bytes; Field Separator placement cannot be verified.");
        addFinding(findings, "SEGDL2-R-005", primaryPairValid && secondaryPairValid ? "PASS" : "FAIL",
                "Primary Access Code/Pause pairing valid=" + primaryPairValid
                        + "; secondary pairing valid=" + secondaryPairValid + ".");
        addFinding(findings, "SEGDL2-R-006", primaryRedialValid && secondaryRedialValid ? "PASS" : "FAIL",
                "Primary Redial Count valid=" + primaryRedialValid + "; secondary Redial Count valid="
                        + secondaryRedialValid + ".");
        addFinding(findings, "SEGDL2-R-007", primaryPhoneValid && "A".equals(primaryTerminator)
                        && secondaryPhoneValid && "F".equals(secondaryTerminator) ? "PASS" : "FAIL",
                "Primary number numeric/1-18 digits=" + primaryPhoneValid
                        + "; A terminator valid=" + "A".equals(primaryTerminator)
                        + "; secondary number numeric/1-18 digits=" + secondaryPhoneValid
                        + "; F terminator valid=" + "F".equals(secondaryTerminator) + ".");
        boolean allowedResponse = "Phone Load Response".equals(messageType)
                || "Table Load Response".equals(messageType);
        addFinding(findings, "SEGDL2-R-008", allowedResponse ? "PASS" : "FAIL",
                "AI response family is '" + messageType + "'; DL2 is allowed only in Phone Load or Table Load Response.");
        addFinding(findings, "SEGDL2-R-009", "NOT_ASSERTABLE",
                "No merchant-profile PHON load-flag context is present in the supplied AI data.");
        ObjectNode summary = report.putObject("aiTestDataValidationSummary");
        summary.put("ruleCount", findings.size());
        summary.put("pass", count(findings, "PASS"));
        summary.put("fail", count(findings, "FAIL"));
        summary.put("reviewRequired", count(findings, "REVIEW_REQUIRED"));
        summary.put("notAssertable", count(findings, "NOT_ASSERTABLE"));
        summary.put("validForExecution", false);
        summary.put("sourceMessageFamily", text(metadata.path("messageFamily")));
        summary.put("reason", "The sample contains concrete DL2 violations, unresolved serialization evidence, and no load-flag context.");
    }

    private static String markdown(JsonNode report) {
        JsonNode inventory = report.path("aiArtifactInventory");
        JsonNode metrics = report.path("testSolutionMetrics");
        JsonNode aiCoverage = report.path("aiCoverageOfTestSolutionOracle");
        JsonNode validation = report.path("aiTestDataValidationSummary");
        StringBuilder out = new StringBuilder("# DL2 AI Artifact Coverage and Validation\n\n")
                .append("**Status:** ").append(report.path("status").asText()).append("\n\n")
                .append("**AI input scope:** ").append(report.path("aiInputScope").asText()).append("\n\n")
                .append("## Artifact inventory\n\n| Artifact | Phase-one run | DL2 |\n|---|---:|---:|\n")
                .append("| Requirements | ").append(inventory.path("phaseOneRequirements").asInt()).append(" | ").append(inventory.path("dl2Requirements").asInt()).append(" |\n")
                .append("| Scenarios | ").append(inventory.path("phaseOneScenarios").asInt()).append(" | ").append(inventory.path("dl2Scenarios").asInt()).append(" |\n")
                .append("| Test cases | ").append(inventory.path("phaseOneTestCases").asInt()).append(" | ").append(inventory.path("dl2TestCases").asInt()).append(" |\n")
                .append("| Test data / metadata files | — | ").append(inventory.path("dl2TestDataFiles").asInt()).append(" / ").append(inventory.path("dl2MetadataFiles").asInt()).append(" |\n")
                .append("| Supplied AI catalog DL2 requirements | — | ").append(inventory.path("suppliedAiCatalogDl2Requirements").asInt()).append(" |\n\n")
                .append("## Coverage measures\n\n| Measure | Result |\n|---|---:|\n")
                .append("| Complete candidate rule chains | ").append(metrics.path("rulesWithCompleteCandidateChains").asInt()).append(" / ").append(metrics.path("oracleRuleCount").asInt()).append(" (").append(metrics.path("completeCandidateChainPercent").asText()).append("%) |\n")
                .append("| Rules with positive and negative candidates | ").append(metrics.path("rulesWithPositiveAndNegativeCandidates").asInt()).append(" / ").append(metrics.path("oracleRuleCount").asInt()).append(" (").append(metrics.path("positiveNegativeCandidatePercent").asText()).append("%) |\n")
                .append("| Candidate test cases / request-response pairs | ").append(metrics.path("candidateTestCases").asInt()).append(" / ").append(metrics.path("candidateRequestResponsePairs").asInt()).append(" |\n")
                .append("| Approved data pairs / executed cases / certified rules | ")
                .append(metrics.path("approvedTestDataPairs").asInt()).append(" / ")
                .append(metrics.path("executedTestCases").asInt()).append(" / ")
                .append(metrics.path("certifiedCoveredRules").asInt()).append(" |\n")
                .append("| Oracle rules with candidate AI mappings pending review | ").append(aiCoverage.path("rulesWithCandidateAiMappingsPendingReview").asInt()).append(" / ").append(aiCoverage.path("oracleRuleCount").asInt()).append(" |\n")
                .append("| Oracle rules without a candidate supplied-catalog mapping | ").append(aiCoverage.path("rulesWithoutAiEvidence").asInt()).append(" / ").append(aiCoverage.path("oracleRuleCount").asInt()).append(" |\n\n")
                .append("Candidate mappings are not confirmed semantic equivalence or execution evidence. Phase-one chain data remains one representative DL2 sample; the supplied AI catalog is separately crosswalked below.\n\n")
                .append("## Supplied AI catalog crosswalk\n\n| AI requirement | Source rule | Heuristic match | Candidate oracle rules | Review status |\n|---|---|---|---|---|\n");
        for (JsonNode row : report.path("suppliedAiDl2Requirements")) {
            List<String> ruleIds = new ArrayList<>();
            row.path("candidateOracleRuleIds").forEach(id -> ruleIds.add(text(id)));
            out.append("| ").append(row.path("aiRequirementId").asText()).append(" | ")
                    .append(row.path("sourceRuleId").asText()).append(" | ")
                    .append(row.path("matchStatus").asText()).append(" | ")
                    .append(ruleIds.isEmpty() ? "—" : String.join(", ", ruleIds)).append(" | ")
                    .append(row.path("candidateDisposition").asText()).append(" |\n");
        }
        out.append("\nAI catalog links are source-element candidate mappings only. The AI-vs-Test comparison and SME decisions remain authoritative for disposition; heuristic matches are not requirement coverage.\n\n")
                .append("## Observed AI field-name crosswalk\n\n| AI field | Element | Rule | Occurrences |\n|---|---:|---|---:|\n");
        for (JsonNode alias : report.path("aiFieldAliasCrosswalk")) {
            out.append("| ").append(alias.path("aiElementName").asText()).append(" | ")
                    .append(alias.path("testElementNumber").asText()).append(" | ")
                    .append(alias.path("testRuleId").asText()).append(" | ")
                    .append(alias.path("occurrencesObserved").asInt()).append(" |\n");
        }
        out.append("\nCrosswalk is comparison-only and must not influence the oracle.\n\n")
                .append("## Candidate rule coverage\n\n| Rule | Cases | Positive | Negative | Status |\n|---|---:|---:|---:|---|\n");
        for (JsonNode row : report.path("testSolutionRuleCoverage")) {
            out.append("| ").append(row.path("ruleId").asText()).append(" | ")
                    .append(row.path("testCaseCount").asInt()).append(" | ")
                    .append(row.path("positiveTestCaseCount").asInt()).append(" | ")
                    .append(row.path("negativeTestCaseCount").asInt()).append(" | ")
                    .append(row.path("candidateChainStatus").asText()).append(" |\n");
        }
        out.append("\n## Supplied AI test data validation\n\n| Rule | Status | Observation |\n|---|---|---|\n");
        for (JsonNode row : report.path("aiTestDataRuleValidation")) {
            out.append("| ").append(row.path("ruleId").asText()).append(" | ")
                    .append(row.path("status").asText()).append(" | ")
                    .append(row.path("observation").asText().replace("|", "\\|")).append(" |\n");
        }
        out.append("\nValidation totals: **").append(validation.path("pass").asInt()).append(" PASS**, **")
                .append(validation.path("fail").asInt()).append(" FAIL**, **")
                .append(validation.path("reviewRequired").asInt()).append(" REVIEW_REQUIRED**, **")
                .append(validation.path("notAssertable").asInt()).append(" NOT_ASSERTABLE**.\n\n")
                .append("## Corrected candidate and unresolved gates\n\n")
                .append("The separate [corrected AI sample candidate](../../../../../test-output/test-json/segment-DL2-ai-corrected-candidate.json) fixes the known response-family, primary/secondary block, terminator, access/pause pairing, and end-marker defects. ")
                .append("It remains synthetic, unapproved, unexecuted, and logical-only. Do not infer Phone Load framing (P-03), fallback timing (P-01), or serialized field boundaries (P-04).");
        return out.toString();
    }

    private static String consolidatedText(JsonNode report) {
        JsonNode metrics = report.path("testSolutionMetrics");
        JsonNode validation = report.path("aiTestDataValidationSummary");
        JsonNode progress = report.path("implementationItemProgress");
        StringBuilder out = new StringBuilder("SEGMENT DL2 CONSOLIDATED TEST SOLUTION READINESS\n")
                .append("Status: INCOMPLETE_REVIEW_REQUIRED\n")
                .append("Specification: ATL105 2026-3; Dial String Data Segment (DL2)\n")
                .append("Authority: Independent Test Solution; AI evidence is comparison input only.\n\n")
                .append("CANDIDATE COVERAGE\n")
                .append("Oracle rules: ").append(metrics.path("oracleRuleCount").asInt()).append('\n')
                .append("Candidate BR-TS-TC-TD chains: ")
                .append(metrics.path("rulesWithCompleteCandidateChains").asInt()).append('/')
                .append(metrics.path("oracleRuleCount").asInt()).append('\n')
                .append("Rules with positive and negative candidates: ")
                .append(metrics.path("rulesWithPositiveAndNegativeCandidates").asInt()).append('/')
                .append(metrics.path("oracleRuleCount").asInt()).append('\n')
                .append("Candidate cases / symbolic data pairs: ")
                .append(metrics.path("candidateTestCases").asInt()).append(" / ")
                .append(metrics.path("candidateRequestResponsePairs").asInt()).append('\n')
                .append("Approved data pairs / executed cases / certified rules: 0 / 0 / 0\n\n")
                .append("AI EVIDENCE\n")
                .append("Supplied AI catalog DL2 requirements: ")
                .append(report.path("aiCoverageOfTestSolutionOracle").path("suppliedAiDl2RequirementCount").asInt()).append('\n')
                .append("Representative phase-one chain: ")
                .append(text(report.path("aiChainIntegrity").path("testCaseId")))
                .append(" (non-exhaustive phase-one scope)\n")
                .append("Independent sample validation: ").append(validation.path("pass").asInt()).append(" PASS, ")
                .append(validation.path("fail").asInt()).append(" FAIL, ")
                .append(validation.path("reviewRequired").asInt()).append(" REVIEW_REQUIRED, ")
                .append(validation.path("notAssertable").asInt()).append(" NOT_ASSERTABLE\n")
                .append("Phone numbers are excluded from this report.\n\n")
                .append("OPEN SME GATES\n")
                .append("P-01 / SEGDL2-SME-001: Secondary-phone fallback timing and recovery behavior.\n")
                .append("P-02 / SEGDL2-SME-002: AI/Test fixture package or isolated synthetic fixture approval.\n")
                .append("P-03 / SEGDL2-SME-003: Phone Load Response field-1 framing.\n")
                .append("P-04 / SEGDL2-SME-004: Separator-free variable field boundaries and access-code parsing.\n\n")
                .append("EIGHT-ITEM IMPLEMENTATION STATUS\n");
        progress.fields().forEachRemaining(entry -> out.append(entry.getKey()).append(": ")
                .append(entry.getValue().asText()).append('\n'));
        return out.append("\nNEXT: Resolve SME decisions, approve isolated synthetic data, then implement serializer-aware DL2 parsing and execute mutation/lifecycle checks.")
                .toString();
    }

    private static JsonNode read(Path path) throws IOException {
        return new ObjectMapper().readTree(path.toFile());
    }

    private static List<JsonNode> array(JsonNode root, String field) {
        JsonNode value = root.path(field);
        if (!value.isArray()) throw new IllegalArgumentException(field + " must be a JSON array");
        List<JsonNode> rows = new ArrayList<>();
        value.forEach(rows::add);
        return rows;
    }

    private static String segmentStatus(JsonNode index) {
        for (JsonNode segment : index.path("segments")) {
            if ("DL2".equalsIgnoreCase(text(segment.path("segment_number")))) return text(segment.path("status"));
        }
        return "MISSING";
    }

    private static boolean containsText(JsonNode array, String expected) {
        for (JsonNode value : array) if (expected.equals(text(value))) return true;
        return false;
    }

    private static boolean containsAny(JsonNode array, Set<String> values) {
        for (JsonNode value : array) if (values.contains(text(value))) return true;
        return false;
    }

    private static boolean hasRequestResponse(JsonNode row) {
        return row != null && row.path("request").isObject() && row.path("response").isObject();
    }

    private static boolean hasAny(JsonNode node, String... fields) {
        for (String field : fields) if (node.has(field) && !node.path(field).isNull()) return true;
        return false;
    }

    private static JsonNode firstObject(JsonNode node, String... fields) {
        for (String field : fields) if (node.path(field).isObject()) return node.path(field);
        return com.fasterxml.jackson.databind.node.MissingNode.getInstance();
    }

    private static String firstText(JsonNode node, String... fields) {
        for (String field : fields) {
            JsonNode value = node.path(field);
            if (!value.isMissingNode() && !value.isNull()) return value.asText();
        }
        return "";
    }

    private static String text(JsonNode value) {
        return value == null || value.isNull() || value.isMissingNode() ? "" : value.asText();
    }

    private static void addFinding(ArrayNode findings, String ruleId, String status, String observation) {
        ObjectNode finding = findings.addObject();
        finding.put("ruleId", ruleId);
        finding.put("status", status);
        finding.put("observation", observation);
    }

    private static int count(ArrayNode findings, String status) {
        int total = 0;
        for (JsonNode finding : findings) if (status.equals(text(finding.path("status")))) total++;
        return total;
    }

    private static double percent(int numerator, int denominator) {
        return denominator == 0 ? 0.0 : Math.round(1000.0 * numerator / denominator) / 10.0;
    }
}
