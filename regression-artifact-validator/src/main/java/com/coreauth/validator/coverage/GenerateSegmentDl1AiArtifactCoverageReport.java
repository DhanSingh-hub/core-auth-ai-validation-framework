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
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/** Validates the available DL1 AI chain and reports its evidence against the independent oracle. */
public final class GenerateSegmentDl1AiArtifactCoverageReport {
    private static final String VERSION = "2026-3";
    private static final String DL1_ENTITY = "ENT-SEG-DL1";
    private final ObjectMapper mapper = new ObjectMapper();

    public GenerateSegmentDl1AiArtifactCoverageReport() { }

    public static void main(String[] args) throws Exception {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        Path packageFile = Atl105Paths.testJson("segment-DL1-coverage-package.json");
        Path catalogFile = Atl105Paths.ruleCatalog("DL1");
        Path reportRoot = Atl105Paths.docs().resolve(Path.of(
                "specs", "kb", "segment-DL1", "coverage"));
        new GenerateSegmentDl1AiArtifactCoverageReport().generate(
                runRoot, packageFile, catalogFile,
                reportRoot.resolve("segment-DL1-ai-artifact-coverage-report.json"),
                reportRoot.resolve("segment-DL1-ai-artifact-coverage-report.md"));
    }

    public ObjectNode generate(Path runRoot, Path testSolutionPackageFile, Path ruleCatalogFile,
                               Path jsonOutput, Path markdownOutput) throws IOException {
        JsonNode requirements = readRequired(runRoot.resolve(Path.of(
                "requirements", "requirement_candidates.json")));
        JsonNode scenarios = readRequired(runRoot.resolve(Path.of(
                "scenarios", "candidate_scenarios.json")));
        JsonNode testCases = readRequired(runRoot.resolve(Path.of(
                "test_cases", "test_case_candidates.json")));
        JsonNode chain = readRequired(runRoot.resolve(Path.of("chains", "segment_DL1.json")));
        JsonNode index = readRequired(runRoot.resolve("INDEX.json"));
        JsonNode testSolution = readRequired(testSolutionPackageFile);
        JsonNode catalog = readRequired(ruleCatalogFile);
        String testCaseId = requiredText(chain.path("test_case").path("id"), "AI chain test_case.id");
        Path testDataFile = runRoot.resolve(Path.of("test_data", testCaseId + ".json"));
        Path metadataFile = runRoot.resolve(Path.of("test_data", testCaseId + ".meta.json"));
        JsonNode testData = readRequired(testDataFile);
        JsonNode metadata = readRequired(metadataFile);

        ObjectNode report = analyze(requirements, scenarios, testCases, chain, index,
                testData, metadata, testSolution, catalog, testCaseId, testDataFile, metadataFile);
        Path jsonParent = jsonOutput.toAbsolutePath().normalize().getParent();
        Path markdownParent = markdownOutput.toAbsolutePath().normalize().getParent();
        if (jsonParent != null) Files.createDirectories(jsonParent);
        if (markdownParent != null) Files.createDirectories(markdownParent);
        mapper.writerWithDefaultPrettyPrinter().writeValue(jsonOutput.toFile(), report);
        Files.writeString(markdownOutput, markdown(report));
        return report;
    }

    private ObjectNode analyze(JsonNode requirementsRoot, JsonNode scenariosRoot, JsonNode testCasesRoot,
                               JsonNode chain, JsonNode index, JsonNode aiTestData, JsonNode metadata,
                               JsonNode testSolution, JsonNode catalog, String testCaseId,
                               Path testDataFile, Path metadataFile) {
        List<JsonNode> aiRequirements = array(requirementsRoot, "requirements");
        List<JsonNode> aiScenarios = array(scenariosRoot, "scenarios");
        List<JsonNode> aiCases = array(testCasesRoot, "test_cases");
        List<JsonNode> dl1Requirements = aiRequirements.stream()
                .filter(node -> "DL1".equalsIgnoreCase(node.path("segment_number").asText()))
                .toList();
        Set<String> aiRequirementIds = new HashSet<>();
        dl1Requirements.forEach(node -> aiRequirementIds.add(node.path("id").asText()));
        List<JsonNode> dl1Scenarios = aiScenarios.stream()
                .filter(node -> containsText(node.path("related_entity_ids"), DL1_ENTITY))
                .toList();
        List<JsonNode> dl1Cases = aiCases.stream()
                .filter(node -> aiRequirementIds.contains(node.path("requirement_id").asText()))
                .toList();
        List<JsonNode> rules = array(catalog, "rules");
        List<JsonNode> testSolutionBrs = array(testSolution, "businessRequirements");
        List<JsonNode> testSolutionScenarios = array(testSolution, "testScenarios");
        List<JsonNode> testSolutionCases = array(testSolution, "testCases");
        List<JsonNode> testSolutionData = array(testSolution, "testDataRecords");

        ObjectNode report = mapper.createObjectNode();
        report.put("artifact", "segment-dl1-ai-artifact-coverage");
        report.put("specification", "ATL105");
        report.put("specificationVersion", VERSION);
        report.put("segment", "DL1");
        report.put("status", "INCOMPLETE_REVIEW_REQUIRED");
        report.put("authority", "INDEPENDENT_TEST_SOLUTION");
        report.put("aiInputScope", "One representative DL1 chain from the 2026-09-29 phase_1_single_leg run; not the complete AI DL1 catalog.");
        report.put("aiTestDataPath", runRootRelative(testDataFile));
        report.put("aiMetadataPath", runRootRelative(metadataFile));
        addInventory(report, aiRequirements, aiScenarios, aiCases, dl1Requirements,
                dl1Scenarios, dl1Cases, testCaseId, testDataFile, metadataFile, index, chain);
        addChainIntegrity(report, chain, metadata, testCaseId, testDataFile, metadataFile);
        addTestSolutionMetrics(report, testSolution, rules, testSolutionBrs,
                testSolutionScenarios, testSolutionCases, testSolutionData);
        addAiRequirementCrosswalk(report, dl1Requirements, testSolutionBrs,
                testSolutionScenarios, testSolutionCases, testSolutionData);
        addRuleCoverageMatrix(report, rules, dl1Requirements, testSolutionBrs,
                testSolutionScenarios, testSolutionCases, testSolutionData);
        addAiTestDataValidation(report, aiTestData);
        report.with("aiChainIntegrity").put("behaviorValidationStatus",
                report.path("aiTestDataValidationSummary").path("overallStatus").asText());
        return report;
    }

    private void addInventory(ObjectNode report, List<JsonNode> allRequirements,
                              List<JsonNode> allScenarios, List<JsonNode> allCases,
                              List<JsonNode> dl1Requirements, List<JsonNode> dl1Scenarios,
                              List<JsonNode> dl1Cases, String testCaseId,
                              Path testDataFile, Path metadataFile, JsonNode index, JsonNode chain) {
        JsonNode dl1Index = null;
        for (JsonNode segment : index.path("segments")) {
            if ("DL1".equalsIgnoreCase(text(segment.path("segment_number")))) {
                dl1Index = segment;
                break;
            }
        }
        ObjectNode inventory = report.putObject("aiArtifactInventory");
        inventory.put("phaseOneRequirements", allRequirements.size());
        inventory.put("phaseOneScenarios", allScenarios.size());
        inventory.put("phaseOneTestCases", allCases.size());
        inventory.put("dl1Requirements", dl1Requirements.size());
        inventory.put("dl1Scenarios", dl1Scenarios.size());
        inventory.put("dl1TestCases", dl1Cases.size());
        inventory.put("dl1TestDataFiles", Files.isRegularFile(testDataFile) ? 1 : 0);
        inventory.put("dl1MetadataFiles", Files.isRegularFile(metadataFile) ? 1 : 0);
        inventory.put("dl1TestDataCaseId", testCaseId);
        inventory.put("indexStatus", text(dl1Index == null ? null : dl1Index.path("status")));
        inventory.put("representativeRequirementId", text(chain.path("requirement").path("id")));
        inventory.put("representativeScenarioId", text(chain.path("scenario").path("id")));
        inventory.put("scope", "Phase-one generator intentionally produces one representative requirement per segment.");
    }

    private void addChainIntegrity(ObjectNode report, JsonNode chain, JsonNode metadata,
                                   String testCaseId, Path testDataFile, Path metadataFile) {
        JsonNode requirement = chain.path("requirement");
        JsonNode evidence = requirement.path("evidence_ref");
        String requirementId = text(chain.path("requirement").path("id"));
        String scenarioId = text(chain.path("scenario").path("id"));
        String testCaseRequirementId = text(chain.path("test_case").path("requirement_id"));
        String testCaseScenarioId = text(chain.path("test_case").path("scenario_id"));
        boolean idsLink = !requirementId.isBlank() && requirementId.equals(text(chain.path("scenario").path("requirement_id")))
                && requirementId.equals(testCaseRequirementId)
                && !scenarioId.isBlank() && scenarioId.equals(testCaseScenarioId)
                && testCaseId.equals(text(metadata.path("testCaseId")))
                && requirementId.equals(text(metadata.path("requirementId")))
                && scenarioId.equals(text(metadata.path("scenarioId")));
        boolean artifactsPresent = Files.isRegularFile(testDataFile) && Files.isRegularFile(metadataFile);
        boolean sourceEvidenceConsistent = !text(requirement.path("source_rule_id")).isBlank()
                && text(requirement.path("source_rule_id")).equals(text(metadata.path("sourceRuleId")))
                && text(requirement.path("source_rule_id")).equals(text(evidence.path("entity_id")))
                && requirement.path("source_page").asInt(-1) == evidence.path("page").asInt(-2)
                && requirement.path("source_page").asInt(-1) == metadata.path("sourcePage").asInt(-2)
                && containsText(requirement.path("related_entity_ids"), DL1_ENTITY);
        ObjectNode integrity = report.putObject("aiChainIntegrity");
        integrity.put("requirementId", requirementId);
        integrity.put("scenarioId", scenarioId);
        integrity.put("testCaseId", testCaseId);
        integrity.put("testDataAndMetadataPresent", artifactsPresent);
        integrity.put("idsConsistentAcrossChainAndMetadata", idsLink);
        integrity.put("sourceEvidenceConsistent", sourceEvidenceConsistent);
        integrity.put("traceabilityStatus", artifactsPresent && idsLink && sourceEvidenceConsistent
                ? "COMPLETE" : "GAP");
        integrity.put("scenarioOracleAuthority", text(chain.path("scenario").path("oracle_authority")));
        integrity.put("scenarioRequiresReview", chain.path("scenario").path("requires_review").asBoolean(true));
        ArrayNode issues = integrity.putArray("issues");
        if (!artifactsPresent) issues.add("AI test-data file or metadata file is missing.");
        if (!idsLink) issues.add("AI requirement/scenario/test-case/test-data metadata identifiers do not form one consistent chain.");
        if (!sourceEvidenceConsistent) issues.add("AI requirement evidence reference, segment attribution, or source metadata is inconsistent.");
        if (text(chain.path("test_case").path("completeness_status")).equalsIgnoreCase("COMPLETE")) {
            integrity.put("producerCompletenessStatus", "COMPLETE");
            integrity.put("independentValidationNote", "Producer completeness confirms artifact construction only; it does not establish DL1 rule compliance.");
        } else {
            integrity.put("producerCompletenessStatus", text(chain.path("test_case").path("completeness_status")));
        }
    }

    private void addTestSolutionMetrics(ObjectNode report, JsonNode testSolution, List<JsonNode> rules,
                                        List<JsonNode> brs, List<JsonNode> scenarios,
                                        List<JsonNode> cases, List<JsonNode> data) {
        Map<String, JsonNode> brById = index(brs, "id");
        Map<String, JsonNode> scenarioById = index(scenarios, "id");
        Map<String, JsonNode> dataByCaseId = new HashMap<>();
        data.forEach(row -> dataByCaseId.put(text(row.path("testCaseId")), row));
        int completeRuleChains = 0;
        int completePositiveNegative = 0;
        int brokenLinks = 0;
        ArrayNode matrix = report.putArray("testSolutionRuleCoverage");
        for (JsonNode rule : rules) {
            String ruleId = text(rule.path("ruleId"));
            List<JsonNode> linkedBrs = brs.stream()
                    .filter(br -> ruleId.equals(text(br.path("ruleId")))).toList();
            Set<String> brIds = new HashSet<>();
            linkedBrs.forEach(br -> brIds.add(text(br.path("id"))));
            List<JsonNode> linkedScenarios = scenarios.stream()
                    .filter(ts -> containsAny(ts.path("requirementIds"), brIds)).toList();
            Set<String> tsIds = new HashSet<>();
            linkedScenarios.forEach(ts -> tsIds.add(text(ts.path("id"))));
            List<JsonNode> linkedCases = cases.stream()
                    .filter(tc -> brIds.contains(text(tc.path("requirementId")))
                            && tsIds.contains(text(tc.path("scenarioId")))).toList();
            List<JsonNode> linkedData = linkedCases.stream()
                    .filter(tc -> dataByCaseId.containsKey(text(tc.path("id")))
                            && hasRequestResponse(dataByCaseId.get(text(tc.path("id"))))).toList();
            long positives = linkedCases.stream().filter(tc -> "PASS".equals(text(tc.path("expectedOutcome")))).count();
            long negatives = linkedCases.stream().filter(tc -> text(tc.path("expectedOutcome")).startsWith("FAIL")).count();
            boolean complete = !linkedBrs.isEmpty() && !linkedScenarios.isEmpty()
                    && !linkedCases.isEmpty() && linkedData.size() == linkedCases.size();
            if (complete) completeRuleChains++;
            if (complete && positives > 0 && negatives > 0) completePositiveNegative++;
            if (linkedCases.stream().anyMatch(tc -> !scenarioById.containsKey(text(tc.path("scenarioId")))
                    || !brById.containsKey(text(tc.path("requirementId"))))
                    || linkedData.size() != linkedCases.size()) brokenLinks++;
            ObjectNode row = matrix.addObject();
            row.put("ruleId", ruleId);
            row.put("testSolutionRequirementCount", linkedBrs.size());
            row.put("scenarioCount", linkedScenarios.size());
            row.put("testCaseCount", linkedCases.size());
            row.put("testDataPairCount", linkedData.size());
            row.put("positiveTestCaseCount", positives);
            row.put("negativeTestCaseCount", negatives);
            row.put("candidateChainStatus", complete ? "COMPLETE_NOT_EXECUTED" : "INCOMPLETE");
            row.put("requirementStatus", linkedBrs.isEmpty() ? "MISSING"
                    : linkedBrs.getFirst().path("status").asText("DRAFT"));
        }
        ObjectNode metrics = report.putObject("testSolutionMetrics");
        metrics.put("oracleRuleCount", rules.size());
        metrics.put("rulesWithCompleteCandidateChains", completeRuleChains);
        metrics.put("completeCandidateChainPercent", percent(completeRuleChains, rules.size()));
        metrics.put("rulesWithPositiveAndNegativeCandidates", completePositiveNegative);
        metrics.put("positiveNegativeCandidateCoveragePercent", percent(completePositiveNegative, rules.size()));
        metrics.put("candidateTestCases", cases.size());
        metrics.put("candidateRequestResponsePairs", data.size());
        metrics.put("brokenTraceLinks", brokenLinks);
        metrics.put("executedTestCases", testSolution.path("coverageSummary").path("executedTestCases").asInt());
        metrics.put("certifiedCoveredRules", testSolution.path("coverageSummary").path("certifiedCoveredRules").asInt());
        metrics.put("executionCoveragePercent", percent(
                testSolution.path("coverageSummary").path("executedTestCases").asInt(), cases.size()));
        metrics.put("interpretation", "Candidate-chain coverage measures authored traceability only; fixtures are symbolic, unapproved, and unexecuted.");
    }

    private void addAiRequirementCrosswalk(ObjectNode report, List<JsonNode> aiRequirements,
                                           List<JsonNode> brs, List<JsonNode> scenarios,
                                           List<JsonNode> cases, List<JsonNode> data) {
        ArrayNode crosswalk = report.putArray("aiRequirementCrosswalk");
        int mapped = 0;
        int fullyCandidateCovered = 0;
        int executed = 0;
        for (JsonNode aiRequirement : aiRequirements) {
            String aiId = text(aiRequirement.path("id"));
            String aiSourceRule = text(aiRequirement.path("source_rule_id"));
            boolean mapsToMarkerRequired = "ENT-FIELD-DL1-1".equals(aiSourceRule);
            String testRuleId = mapsToMarkerRequired ? "SEGDL1-R-002" : "";
            String testBrId = mapsToMarkerRequired ? "BR-SEGDL1-002" : "";
            JsonNode testBr = brs.stream().filter(br -> testBrId.equals(text(br.path("id"))))
                    .findFirst().orElse(null);
            List<JsonNode> linkedScenarios = scenarios.stream()
                    .filter(ts -> containsText(ts.path("requirementIds"), testBrId)).toList();
            Set<String> scenarioIds = new HashSet<>();
            linkedScenarios.forEach(ts -> scenarioIds.add(text(ts.path("id"))));
            List<JsonNode> linkedCases = cases.stream()
                    .filter(tc -> testBrId.equals(text(tc.path("requirementId")))
                            && scenarioIds.contains(text(tc.path("scenarioId")))).toList();
            List<JsonNode> linkedData = data.stream()
                    .filter(td -> linkedCases.stream().anyMatch(tc -> text(tc.path("id"))
                            .equals(text(td.path("testCaseId"))))).toList();
            boolean positive = linkedCases.stream().anyMatch(tc -> "PASS".equals(text(tc.path("expectedOutcome"))));
            boolean negative = linkedCases.stream().anyMatch(tc -> text(tc.path("mutation")).toLowerCase(Locale.ROOT)
                    .contains("omit") && text(tc.path("expectedOutcome")).startsWith("FAIL"));
            boolean candidateCovered = testBr != null && positive && negative && !linkedData.isEmpty();
            if (mapsToMarkerRequired) mapped++;
            if (candidateCovered) fullyCandidateCovered++;
            if (linkedCases.stream().anyMatch(tc -> text(tc.path("status")).startsWith("EXECUTED"))) executed++;
            ObjectNode row = crosswalk.addObject();
            row.put("aiRequirementId", aiId);
            row.put("aiSourceRuleId", aiSourceRule);
            row.put("aiStatement", text(aiRequirement.path("statement")));
            if (mapsToMarkerRequired) {
                row.put("testSolutionRuleId", testRuleId);
                row.put("testSolutionBusinessRequirementId", testBrId);
                row.put("ruleScopeMatch", "PARTIAL");
                row.put("testSolutionCandidateCoverage", candidateCovered ? "POSITIVE_AND_REQUIRED_FIELD_OMISSION_DEFINED" : "INCOMPLETE");
                row.put("testSolutionExecutionCoverage", "NOT_EXECUTED");
                row.put("assessment", "The Test Solution now has positive marker presence and a negative omitted-marker case; its broader oracle rule also specifies the End-of-Data marker, so the AI BR maps to only part of that rule.");
            } else {
                row.put("testSolutionRuleId", "");
                row.put("testSolutionBusinessRequirementId", "");
                row.put("ruleScopeMatch", "UNMAPPED");
                row.put("testSolutionCandidateCoverage", "MISSING");
                row.put("testSolutionExecutionCoverage", "NOT_EXECUTED");
                row.put("assessment", "No adjudicated DL1 source-rule crosswalk exists for this AI requirement.");
            }
        }
        ObjectNode metrics = report.putObject("aiRequirementCoverageMetrics");
        metrics.put("aiRequirementDenominator", aiRequirements.size());
        metrics.put("aiRequirementsMappedToTestSolution", mapped);
        metrics.put("aiRequirementsWithPositiveAndNegativeCandidateCoverage", fullyCandidateCovered);
        metrics.put("aiRequirementsExecuted", executed);
        metrics.put("candidateCoveragePercent", percent(fullyCandidateCovered, aiRequirements.size()));
        metrics.put("executionCoveragePercent", percent(executed, aiRequirements.size()));
        metrics.put("testSolutionOracleRulesWithAnyAiEvidence", mapped);
        metrics.put("testSolutionOracleRulesWithAnyAiEvidencePercent", percent(mapped, report.path("testSolutionMetrics").path("oracleRuleCount").asInt()));
        metrics.put("testSolutionOracleRulesWithoutAiEvidence",
                Math.max(0, report.path("testSolutionMetrics").path("oracleRuleCount").asInt() - mapped));
    }

    private void addRuleCoverageMatrix(ObjectNode report, List<JsonNode> rules,
                                       List<JsonNode> aiRequirements, List<JsonNode> brs,
                                       List<JsonNode> scenarios, List<JsonNode> cases, List<JsonNode> data) {
        ArrayNode rows = report.putArray("aiEvidenceByOracleRule");
        int partial = 0;
        int missing = 0;
        for (JsonNode rule : rules) {
            String ruleId = text(rule.path("ruleId"));
            List<JsonNode> evidence = "SEGDL1-R-002".equals(ruleId)
                    ? aiRequirements.stream().filter(ai -> "ENT-FIELD-DL1-1".equals(text(ai.path("source_rule_id")))).toList()
                    : List.of();
            if (evidence.isEmpty()) missing++; else partial++;
            ObjectNode row = rows.addObject();
            row.put("ruleId", ruleId);
            row.put("title", text(rule.path("title")));
            ArrayNode ids = row.putArray("aiRequirementIds");
            evidence.forEach(ai -> ids.add(text(ai.path("id"))));
            row.put("aiEvidenceStatus", evidence.isEmpty() ? "MISSING" : "PARTIAL");
            if (!evidence.isEmpty()) {
                row.put("testSolutionRequirementId", "BR-SEGDL1-002");
                row.put("testSolutionScenarioId", "TS-SEGDL1-002");
                row.put("testSolutionPositiveTestCaseId", "TC-SEGDL1-002-P");
                row.put("testSolutionNegativeTestCaseId", "TC-SEGDL1-002-N-OMIT");
                row.put("candidateTestDataStatus", "SYNTHETIC_CANDIDATE_NOT_APPROVED");
                row.put("executionStatus", "NOT_EXECUTED");
            }
        }
        ObjectNode metrics = report.putObject("aiCoverageOfTestSolutionOracle");
        metrics.put("oracleRuleDenominator", rules.size());
        metrics.put("rulesWithDirectFullAiEvidence", 0);
        metrics.put("rulesWithPartialAiEvidence", partial);
        metrics.put("rulesWithoutAiEvidence", missing);
        metrics.put("directFullAiEvidencePercent", percent(0, rules.size()));
        metrics.put("partialAiEvidencePercent", percent(partial, rules.size()));
        metrics.put("noAiEvidencePercent", percent(missing, rules.size()));
        metrics.put("interpretation", "The AI run is a one-requirement-per-segment sample; missing evidence means absent from this supplied sample, not proof that no other AI artifact exists.");
    }

    private void addAiTestDataValidation(ObjectNode report, JsonNode aiTestData) {
        String messageType = aiTestData.fieldNames().hasNext() ? aiTestData.fieldNames().next() : "";
        JsonNode response = aiTestData.path(messageType);
        JsonNode dl1 = response.path("Merchant Data Segment");
        String indicator = field(dl1, "DataTypeIndicator");
        String merchantName = field(dl1, "MerchantName");
        String storeNumber = field(dl1, "StoreNumber");
        String addressLine2 = field(dl1, "AddressLine2");
        String phone = field(dl1, "MerchantPhoneNumber");
        String count = field(dl1, "NumberofCardTypes");
        boolean cardTypesPresent = hasField(dl1, "CardType") || hasField(dl1, "CardTypes");
        boolean dl6Present = hasSegment(response, "Store and Forward Data Segment");
        boolean type173Present = textArray(dl1.path("CardTypes")).contains("173");
        ArrayNode findings = report.putArray("aiTestDataRuleValidation");
        addFinding(findings, "SEGDL1-R-001", "NOT_ASSERTABLE",
                "The AI artifact is a structured object, not serialized DL1 bytes; a character-length assertion cannot be made.");
        addFinding(findings, "SEGDL1-R-002", "#".equals(indicator) && hasEndDataMarker(dl1) ? "PASS" : "FAIL",
                "# marker " + ("#".equals(indicator) ? "is present" : "is missing/incorrect")
                        + "; End-of-Data '~' " + (hasEndDataMarker(dl1) ? "is present" : "is absent"));
        addFinding(findings, "SEGDL1-R-003", allPresent(merchantName, storeNumber,
                        field(dl1, "AddressLine1"), addressLine2, phone) ? "REVIEW_REQUIRED" : "FAIL",
                "Logical required-field presence is checked; exact widths/padding remain unresolved under P-02.");
        boolean countMatches = count.matches("\\d{2}") && cardTypesPresent
                && Integer.parseInt(count) == textArray(dl1.path("CardTypes")).size();
        addFinding(findings, "SEGDL1-R-004", countMatches ? "PASS" : "FAIL",
                "Declared Card Type count is " + display(count) + "; repeated Card Type values "
                        + (cardTypesPresent ? "are present" : "are absent"));
        addFinding(findings, "SEGDL1-R-005", type173Present == dl6Present ? "PASS" : "FAIL",
                "Card Type 173 present=" + type173Present + "; DL6 present=" + dl6Present);
        addFinding(findings, "SEGDL1-R-006", "NOT_ASSERTABLE",
                "The structured AI data has no serialized DL1 bytes, so field-separator placement cannot be verified.");
        addFinding(findings, "SEGDL1-R-007", "Table Load Response".equals(messageType) ? "PASS" : "FAIL",
                "AI data response type is '" + display(messageType) + "'; DL1 is specified for Table Load Response only.");
        addFinding(findings, "SEGDL1-R-008", "NOT_ASSERTABLE",
                "The AI test data does not include a merchant-profile load-flag context.");
        addFinding(findings, "SEGDL1-R-009", cardTypesPresent ? "REVIEW_REQUIRED" : "NOT_ASSERTABLE",
                cardTypesPresent ? "Card Type values exist, but complete valid-set confirmation is pending P-04."
                        : "No repeated Card Type value is present to validate; valid-set confirmation is pending P-04.");
        boolean addressLineValid = addressLine2.matches(".{12} [A-Za-z]{2} .{5}");
        addFinding(findings, "SEGDL1-R-010", addressLineValid ? "PASS" : "FAIL",
                "Address Line 2 value '" + display(addressLine2) + "' "
                        + (addressLineValid ? "matches" : "does not match")
                        + " the specified positional city/state/ZIP layout.");
        boolean storeValid = storeNumber.matches("[0-9]{16}") && !storeNumber.matches("0{16}");
        boolean phoneValid = phone.matches("\\([0-9]{3}\\)[0-9]{3}-[0-9]{4}");
        addFinding(findings, "SEGDL1-R-011", storeValid && phoneValid ? "PASS" : "FAIL",
                "Store Number format/range valid=" + storeValid + "; Merchant Phone format valid=" + phoneValid);
        addFinding(findings, "SEGDL1-R-012", "REVIEW_REQUIRED",
                "The structured response contains DL1/DL2/DL3/DL6 blocks, but serialized End-of-Load framing is absent and the DL6 end-marker count remains open under P-03.");
        ObjectNode summary = report.putObject("aiTestDataValidationSummary");
        summary.put("ruleCount", findings.size());
        summary.put("pass", countStatus(findings, "PASS"));
        summary.put("fail", countStatus(findings, "FAIL"));
        summary.put("reviewRequired", countStatus(findings, "REVIEW_REQUIRED"));
        summary.put("notAssertable", countStatus(findings, "NOT_ASSERTABLE"));
        String overallStatus = summary.path("fail").asInt() > 0 ? "FAIL"
                : summary.path("reviewRequired").asInt() > 0 ? "REVIEW_REQUIRED"
                : summary.path("notAssertable").asInt() > 0 ? "INCOMPLETE" : "PASS";
        summary.put("overallStatus", overallStatus);
        summary.put("validForExecution", "PASS".equals(overallStatus));
        summary.put("reason", switch (overallStatus) {
            case "FAIL" -> "The AI test data contains one or more concrete rule violations.";
            case "REVIEW_REQUIRED" -> "The AI test data has no confirmed failures, but unresolved oracle questions remain.";
            case "INCOMPLETE" -> "The AI test data has no confirmed failures, but one or more rules cannot be asserted from its representation.";
            default -> "All in-scope AI test data checks passed.";
        });
    }

    private static void addFinding(ArrayNode findings, String ruleId, String status, String observation) {
        ObjectNode row = findings.addObject();
        row.put("ruleId", ruleId);
        row.put("status", status);
        row.put("observation", observation);
    }

    private int countStatus(ArrayNode findings, String status) {
        int count = 0;
        for (JsonNode finding : findings) if (status.equals(text(finding.path("status")))) count++;
        return count;
    }

    private static String markdown(JsonNode report) {
        StringBuilder output = new StringBuilder("# DL1 AI Artifact Coverage and Validation\n\n");
        output.append("**Status:** ").append(report.path("status").asText()).append("\n\n")
                .append("**AI input scope:** ").append(report.path("aiInputScope").asText()).append("\n\n");
        JsonNode inventory = report.path("aiArtifactInventory");
        output.append("## AI artifact inventory\n\n")
                .append("| Artifact | Phase-one run | DL1 |\n|---|---:|---:|\n")
                .append("| Requirements | ").append(inventory.path("phaseOneRequirements").asInt()).append(" | ").append(inventory.path("dl1Requirements").asInt()).append(" |\n")
                .append("| Scenarios | ").append(inventory.path("phaseOneScenarios").asInt()).append(" | ").append(inventory.path("dl1Scenarios").asInt()).append(" |\n")
                .append("| Test cases | ").append(inventory.path("phaseOneTestCases").asInt()).append(" | ").append(inventory.path("dl1TestCases").asInt()).append(" |\n")
                .append("| Test data / metadata files | — | ").append(inventory.path("dl1TestDataFiles").asInt()).append(" / ").append(inventory.path("dl1MetadataFiles").asInt()).append(" |\n\n");
        JsonNode testSolution = report.path("testSolutionMetrics");
        JsonNode aiCoverage = report.path("aiCoverageOfTestSolutionOracle");
        JsonNode aiReqCoverage = report.path("aiRequirementCoverageMetrics");
        output.append("## Coverage measures\n\n")
                .append("| Measure | Result |\n|---|---:|\n")
                .append("| Test Solution rules with complete candidate chains | ").append(testSolution.path("rulesWithCompleteCandidateChains").asInt()).append(" / ").append(testSolution.path("oracleRuleCount").asInt()).append(" (").append(testSolution.path("completeCandidateChainPercent").asText()).append("%) |\n")
                .append("| Test Solution rules with positive and negative candidates | ").append(testSolution.path("rulesWithPositiveAndNegativeCandidates").asInt()).append(" / ").append(testSolution.path("oracleRuleCount").asInt()).append(" (").append(testSolution.path("positiveNegativeCandidateCoveragePercent").asText()).append("%) |\n")
                .append("| AI requirements with positive/negative Test Solution candidates | ").append(aiReqCoverage.path("aiRequirementsWithPositiveAndNegativeCandidateCoverage").asInt()).append(" / ").append(aiReqCoverage.path("aiRequirementDenominator").asInt()).append(" (").append(aiReqCoverage.path("candidateCoveragePercent").asText()).append("%) |\n")
                .append("| AI requirements executed by the Test Solution | ").append(aiReqCoverage.path("aiRequirementsExecuted").asInt()).append(" / ").append(aiReqCoverage.path("aiRequirementDenominator").asInt()).append(" (").append(aiReqCoverage.path("executionCoveragePercent").asText()).append("%) |\n")
                .append("| Oracle rules with direct/full AI requirement evidence | ").append(aiCoverage.path("rulesWithDirectFullAiEvidence").asInt()).append(" / ").append(aiCoverage.path("oracleRuleDenominator").asInt()).append(" (").append(aiCoverage.path("directFullAiEvidencePercent").asText()).append("%) |\n")
                .append("| Oracle rules with partial AI evidence | ").append(aiCoverage.path("rulesWithPartialAiEvidence").asInt()).append(" / ").append(aiCoverage.path("oracleRuleDenominator").asInt()).append(" (").append(aiCoverage.path("partialAiEvidencePercent").asText()).append("%) |\n")
                .append("| Oracle rules with no AI evidence in this sample | ").append(aiCoverage.path("rulesWithoutAiEvidence").asInt()).append(" / ").append(aiCoverage.path("oracleRuleDenominator").asInt()).append(" (").append(aiCoverage.path("noAiEvidencePercent").asText()).append("%) |\n\n")
                .append("Candidate definitions are not executed coverage. AI input is one representative DL1 requirement, not the complete AI catalog.\n\n");
        output.append("## Oracle rule crosswalk\n\n")
                .append("| Rule | AI BR evidence | AI evidence | Test Solution candidates |\n|---|---|---|---|\n");
        for (JsonNode row : report.path("aiEvidenceByOracleRule")) {
            String aiIds = String.join(", ", textList(row.path("aiRequirementIds")));
            JsonNode tsRow = find(report.path("testSolutionRuleCoverage"), "ruleId", row.path("ruleId").asText());
            output.append("| ").append(row.path("ruleId").asText()).append(" | ")
                    .append(aiIds.isBlank() ? "—" : aiIds).append(" | ")
                    .append(row.path("aiEvidenceStatus").asText()).append(" | ")
                    .append(tsRow.path("candidateChainStatus").asText()).append(" (")
                    .append(tsRow.path("testCaseCount").asInt()).append(" TCs / ")
                    .append(tsRow.path("testDataPairCount").asInt()).append(" data pairs) |\n");
        }
        output.append("\n## Validation of supplied AI test data\n\n")
                .append("| Rule | Status | Observation |\n|---|---|---|\n");
        for (JsonNode finding : report.path("aiTestDataRuleValidation")) {
            output.append("| ").append(finding.path("ruleId").asText()).append(" | ")
                    .append(finding.path("status").asText()).append(" | ")
                    .append(finding.path("observation").asText().replace("|", "\\|")).append(" |\n");
        }
        JsonNode summary = report.path("aiTestDataValidationSummary");
        output.append("\nValidation totals: **").append(summary.path("pass").asInt()).append(" PASS**, **")
                .append(summary.path("fail").asInt()).append(" FAIL**, **")
                .append(summary.path("reviewRequired").asInt()).append(" REVIEW_REQUIRED**, **")
                .append(summary.path("notAssertable").asInt()).append(" NOT_ASSERTABLE**. ")
                .append("The AI sample is not valid for execution against the DL1 oracle.\n");
        return output.toString();
    }

    private static JsonNode find(JsonNode rows, String field, String value) {
        for (JsonNode row : rows) if (value.equals(text(row.path(field)))) return row;
        return nullNode();
    }

    private static JsonNode nullNode() {
        return com.fasterxml.jackson.databind.node.MissingNode.getInstance();
    }

    private static List<JsonNode> array(JsonNode root, String field) {
        JsonNode value = root.path(field);
        if (!value.isArray()) throw new IllegalArgumentException(field + " must be a JSON array");
        List<JsonNode> rows = new ArrayList<>();
        value.forEach(rows::add);
        return rows;
    }

    private static Map<String, JsonNode> index(List<JsonNode> rows, String field) {
        Map<String, JsonNode> indexed = new HashMap<>();
        rows.forEach(row -> indexed.put(text(row.path(field)), row));
        return indexed;
    }

    private static boolean containsAny(JsonNode array, Set<String> values) {
        for (JsonNode item : array) if (values.contains(text(item))) return true;
        return false;
    }

    private static boolean containsText(JsonNode array, String expected) {
        for (JsonNode item : array) if (expected.equals(text(item))) return true;
        return false;
    }

    private static boolean hasRequestResponse(JsonNode record) {
        return record != null && record.path("request").isObject() && record.path("response").isObject();
    }

    private static List<String> textArray(JsonNode array) {
        List<String> values = new ArrayList<>();
        if (array.isArray()) array.forEach(item -> values.add(text(item)));
        else if (array.isTextual() && !array.asText().isBlank()) values.add(array.asText());
        return values;
    }

    private static List<String> textList(JsonNode array) {
        return textArray(array);
    }

    private static boolean allPresent(String... values) {
        for (String value : values) if (value.isBlank()) return false;
        return true;
    }

    private static boolean hasField(JsonNode node, String expected) {
        return node.isObject() && node.fieldNames().hasNext()
                && java.util.stream.StreamSupport.stream(
                        java.util.Spliterators.spliteratorUnknownSize(node.fieldNames(), 0), false)
                .anyMatch(name -> normalize(name).equals(normalize(expected)));
    }

    private static String field(JsonNode node, String expected) {
        if (!node.isObject()) return "";
        var fields = node.fields();
        while (fields.hasNext()) {
            var field = fields.next();
            if (normalize(field.getKey()).equals(normalize(expected))) return text(field.getValue());
        }
        return "";
    }

    private static boolean hasEndDataMarker(JsonNode node) {
        return node.isObject() && field(node, "EndofDataIndicator").equals("~");
    }

    private static boolean hasSegment(JsonNode node, String expected) {
        if (!node.isObject()) return false;
        var fields = node.fieldNames();
        while (fields.hasNext()) if (normalize(fields.next()).equals(normalize(expected))) return true;
        return false;
    }

    private static String normalize(String value) {
        return value.replaceAll("[^A-Za-z0-9]", "").toLowerCase(Locale.ROOT);
    }

    private static String requiredText(JsonNode node, String description) {
        String value = text(node);
        if (value.isBlank()) throw new IllegalArgumentException(description + " is required");
        return value;
    }

    private static String text(JsonNode node) {
        return node == null || node.isMissingNode() || node.isNull() ? "" : node.asText("");
    }

    private static String display(String value) {
        return value.isBlank() ? "<missing>" : value;
    }

    private static double percent(int numerator, int denominator) {
        return denominator == 0 ? 0.0 : Math.round(1000.0 * numerator / denominator) / 10.0;
    }

    private static String runRootRelative(Path file) {
        return file.toString().replace('\\', '/');
    }

    private JsonNode readRequired(Path path) throws IOException {
        if (!Files.isRegularFile(path)) throw new IOException("Required DL1 coverage input is missing: " + path);
        return mapper.readTree(path.toFile());
    }
}
