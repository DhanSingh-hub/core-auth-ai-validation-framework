package com.coreauth.validator.canonical;

import com.coreauth.validator.canonical.AiCanonicalCaseEnvelope.IndependentValidation;
import com.coreauth.validator.canonical.AiCanonicalCaseEnvelope.CaseOutcomeEvidence;
import com.coreauth.validator.canonical.AiCanonicalCaseEnvelope.CaseOutcomeOracleComparison;
import com.coreauth.validator.canonical.AiCanonicalCaseEnvelope.RuleEvidence;
import com.coreauth.validator.canonical.AiCanonicalCaseEnvelope.ResponseComparison;
import com.coreauth.validator.canonical.AiCanonicalCaseEnvelope.SourceArtifact;
import com.coreauth.validator.canonical.AiCanonicalCaseEnvelope.Verdict;
import com.coreauth.validator.validation.Atl105AiElementIntake;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Finding;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.zip.GZIPOutputStream;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/** Joins profile-driven intake, independent response checks, and artifact defect candidates per AI test case. */
public final class AiUnifiedValidationRunner {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final List<String> SCENARIO_ARRAY_PATHS = List.of(
            "/scenarios", "/approved_scenarios", "/approvedScenarios", "/test_scenarios", "/testScenarios");
    private static final List<String> CASE_ARRAY_PATHS = List.of(
            "/test_cases", "/testCases", "/cases", "/testCaseCandidates");
    private static final Set<String> DEFECT_CODES = Set.of(
            "CASE_SCENARIO_REFERENCE_MISSING", "BR_ATTRIBUTION_MISSING_BOTH", "BR_ATTRIBUTION_MISSING_CASE",
            "BR_ATTRIBUTION_MISSING_SCENARIO", "BR_ATTRIBUTION_NO_SHARED_LINK", "BR_ATTRIBUTION_LINK_SET_DIVERGENCE",
            "EXPECTED_RESPONSE_CONFLICT", "MALFORMED_EXPECTED_RESPONSE", "DECLARED_PAYLOAD_MISSING",
            "EMPTY_PHYSICAL_PAYLOAD", "UNSAFE_OR_UNSUPPORTED_PAYLOAD_PATH", "FLOW_SCENARIO_WITHOUT_TEST_CASE",
            "MALFORMED_DECLARED_PAYLOAD_JSON");

    private AiUnifiedValidationRunner() { }

    @FunctionalInterface
    private interface TestCaseConsumer {
        void accept(JsonNode testCase) throws Exception;
    }

    public static ObjectNode assess(Path pack, Path runDirectory, Path scenarioCatalogFile,
                                    Path testCaseCatalogFile, boolean chapter13Semantics) throws Exception {
        return assess(pack, runDirectory, scenarioCatalogFile, testCaseCatalogFile, null, null, chapter13Semantics);
    }

    public static ObjectNode assess(Path pack, Path runDirectory, Path scenarioCatalogFile,
                                    Path testCaseCatalogFile, Path supplementalFlowCatalogFile,
                                    boolean chapter13Semantics) throws Exception {
        return assess(pack, runDirectory, scenarioCatalogFile, testCaseCatalogFile,
            supplementalFlowCatalogFile, null, chapter13Semantics);
        }

        public static ObjectNode assess(Path pack, Path runDirectory, Path scenarioCatalogFile,
                        Path testCaseCatalogFile, Path supplementalFlowCatalogFile,
                        Path caseOutcomeOracleRegisterFile, boolean chapter13Semantics) throws Exception {
        Path runRoot = runDirectory.toRealPath();
        if (!Files.isDirectory(runRoot) || !Files.isRegularFile(scenarioCatalogFile)
                || !Files.isRegularFile(testCaseCatalogFile)) {
            throw new IOException("Run directory and both scenario/test-case catalogs are required");
        }

        JsonNode scenarioRoot = MAPPER.readTree(scenarioCatalogFile.toFile());
        CatalogArray scenarioArray = catalogArray(scenarioRoot, SCENARIO_ARRAY_PATHS, "scenario");
        Map<String, JsonNode> scenarios = new LinkedHashMap<>();
        for (JsonNode rawScenario : scenarioArray.values()) {
            ObjectNode scenario = normalizeScenario(rawScenario);
            String id = scenario.path("id").asText("");
            if (id.isBlank() || scenarios.putIfAbsent(id, scenario) != null) {
                throw new IOException("Scenario ID missing or duplicated: " + id);
            }
        }
        if (scenarios.isEmpty()) throw new IOException("Scenario catalog contains no scenarios");

        Atl105AiElementIntake intake = new Atl105AiElementIntake(pack, chapter13Semantics);
        Map<String, List<Atl105AiElementIntake.AssessedRunMessage>> intakeByCase = new TreeMap<>();
        for (Atl105AiElementIntake.AssessedRunMessage message : intake.assessRun(runRoot)) {
            intakeByCase.computeIfAbsent(message.testCaseId(), ignored -> new ArrayList<>()).add(message);
        }
        AiExpectedResponseComparator responseComparator = new AiExpectedResponseComparator();
        ArrayNode cases = MAPPER.createArrayNode();
        ArrayNode flowFindings = MAPPER.createArrayNode();
        Map<String, Integer> casesPerScenario = new TreeMap<>();
        Map<String, Long> verdictCounts = new TreeMap<>();
        Map<String, Long> responseCounts = new TreeMap<>();
        Map<String, Long> defectCounts = new TreeMap<>();
        Map<String, RuleCoverageAccumulator> ruleCoverage = new TreeMap<>();
        Map<String, NegativeTargetAccumulator> negativeTargetCounts = new TreeMap<>();
        Map<String, OracleGapAccumulator> oracleGapGroups = new TreeMap<>();
        Map<String, Long> caseOutcomeOracleCounts = new TreeMap<>();
        Set<String> catalogCaseIds = new LinkedHashSet<>();
        int[] processedCases = {0};
        AiCaseOutcomeOracleRegister.Register caseOutcomeOracles = caseOutcomeOracleRegisterFile == null
            ? null : AiCaseOutcomeOracleRegister.load(caseOutcomeOracleRegisterFile, pack, runRoot,
                runRoot.getFileName().toString(), scenarioCatalogFile, testCaseCatalogFile);

        String testCaseCatalogProfile = streamTestCases(testCaseCatalogFile, rawCase -> {
            ObjectNode testCase = normalizeTestCase(rawCase);
            String testCaseId = testCase.path("id").asText("");
            String scenarioId = testCase.path("scenario_id").asText("");
            if (testCaseId.isBlank() || !catalogCaseIds.add(testCaseId)) {
                throw new IOException("Test case ID missing or duplicated: " + testCaseId);
            }
            casesPerScenario.merge(scenarioId, 1, Integer::sum);
            JsonNode scenario = scenarios.get(scenarioId);
            AiExpectedResponseComparator.Comparison response = responseComparator.compareTestCase(testCase, scenario);
            List<ObjectNode> defects = new ArrayList<>();
            try {
                defects.addAll(AiArtifactDefectRegister.inspectTestCase(testCase, scenario, runRoot, responseComparator));
            } catch (IOException exception) {
                ObjectNode finding = MAPPER.createObjectNode();
                finding.put("issueCode", "MALFORMED_DECLARED_PAYLOAD_JSON");
                finding.put("scope", "TEST_CASE");
                finding.put("subjectId", testCaseId);
                finding.put("scenarioId", scenarioId);
                finding.put("status", "CANDIDATE_REVIEW_REQUIRED");
                finding.put("reason", exception.getMessage());
                defects.add(finding);
            }
            defects.forEach(finding -> defectCounts.merge(finding.path("issueCode").asText(""), 1L, Long::sum));
            responseCounts.merge(response.status().name(), 1L, Long::sum);
                AiCaseOutcomeOracleRegister.Entry oracleEntry = caseOutcomeOracles == null
                    ? null : caseOutcomeOracles.entryFor(testCaseId);
                    List<Atl105AiElementIntake.AssessedRunMessage> inputs = intakeByCase.getOrDefault(testCaseId, List.of());
                    List<SourceArtifact> oracleBindings = inputs.stream().map(input -> new SourceArtifact(
                        input.metadataPath(), input.payloadPath(), input.formatProfileId(),
                        input.metadataSha256(), input.payloadSha256())).toList();
                    AiExpectedResponseComparator.Status oracleFamilyStatus = AiExpectedResponseComparator.Status.ORACLE_GAP;
                    if (oracleEntry != null && "APPROVED".equals(oracleEntry.status())) {
                    ObjectNode oracleCase = testCase.deepCopy();
                    oracleCase.set("expected_response", oracleEntry.expectedResponse());
                    ObjectNode oracleScenario = scenario == null ? MAPPER.createObjectNode() : scenario.deepCopy();
                    oracleScenario.remove("expected_response_code");
                    oracleFamilyStatus = responseComparator.compareTestCase(normalizeTestCase(oracleCase), oracleScenario).status();
                    }
                AiCaseOutcomeOracleRegister.Decision outcomeOracle = AiCaseOutcomeOracleRegister.compare(
                        oracleEntry, testCase.get("expected_response"), scenarioId,
                        MAPPER.valueToTree(oracleBindings), oracleFamilyStatus);
                caseOutcomeOracleCounts.merge(outcomeOracle.status(), 1L, Long::sum);
            for (Atl105AiElementIntake.AssessedRunMessage input : inputs) {
                for (Atl105AiElementIntake.RuleFindingSummary rule : input.ruleFindings()) {
                    String key = String.join("|", rule.segment(), rule.element(), rule.ruleId());
                    RuleCoverageAccumulator coverage = ruleCoverage.computeIfAbsent(key,
                            ignored -> new RuleCoverageAccumulator(rule.segment(), rule.element(), rule.ruleId()));
                    coverage.caseIds.add(testCaseId);
                    coverage.findingCounts.merge(rule.status(), rule.findingCount(), Long::sum);
                }
                if ("negative".equals(input.scenarioType())) {
                    String key = String.join("|", input.negativeClass(), input.violatedElement(), input.negativeTargetDisposition());
                    NegativeTargetAccumulator target = negativeTargetCounts.computeIfAbsent(key,
                            ignored -> new NegativeTargetAccumulator(input.negativeClass(), input.violatedElement(),
                                    input.negativeTargetDisposition()));
                    target.caseIds.add(testCaseId);
                }
            }
            AiCanonicalCaseEnvelope envelope = envelope(runRoot.getFileName().toString(), testCase, scenario,
                    inputs, response, outcomeOracle, defects);
            verdictCounts.merge(envelope.verdict().name(), 1L, Long::sum);
            if (envelope.verdict() == Verdict.ORACLE_GAP) {
                OracleGapAccumulator gap = oracleGapGroups.computeIfAbsent(envelope.oracleGapId(), ignored ->
                    new OracleGapAccumulator(envelope.oracleGapId(), gapType(response, outcomeOracle), response.oracleFamily(),
                        response.status().name(), gapReason(response, outcomeOracle)));
                gap.caseCount++;
                if (gap.sampleCaseIds.size() < 10) gap.sampleCaseIds.add(testCaseId);
            }
            cases.add(MAPPER.valueToTree(envelope));
            processedCases[0]++;
            if (processedCases[0] % 1000 == 0) System.out.println("unifiedCasesProcessed=" + processedCases[0]);
        });
        if (caseOutcomeOracles != null) caseOutcomeOracles.validateCaseIds(catalogCaseIds);

        AiArtifactDefectRegister.missingFlowScenarios(scenarios.values(), casesPerScenario).forEach(finding -> {
            flowFindings.add(finding);
            defectCounts.merge(finding.path("issueCode").asText(""), 1L, Long::sum);
        });
        ObjectNode supplementalFlowSummary = null;
        if (supplementalFlowCatalogFile != null) {
            SupplementalFlowAudit audit = auditSupplementalFlows(supplementalFlowCatalogFile);
            supplementalFlowSummary = audit.summary();
            audit.findings().forEach(finding -> {
                flowFindings.add(finding);
                defectCounts.merge(finding.path("issueCode").asText(""), 1L, Long::sum);
            });
        }

        long unmatchedIntake = intakeByCase.keySet().stream().filter(id -> !catalogCaseIds.contains(id)).count();
        ObjectNode report = MAPPER.createObjectNode();
        report.put("artifact", "ATL105-UNIFIED-AI-VALIDATION-RUN");
        report.put("schemaVersion", 1);
        report.put("specification", "ATL105");
        report.put("specificationVersion", "2026-3");
        report.put("runId", runRoot.getFileName().toString());
        report.put("status", oracleGapGroups.isEmpty() ? "ASSESSMENT_COMPLETE" : "ASSESSMENT_COMPLETE_WITH_ORACLE_GAPS");
        report.put("policy", "Per-case envelope joins structural defect candidates, profile-driven independent intake, and response-code-family checks. AGREE is reserved for complete expected-outcome agreement; code-family permission alone is not case-level agreement. No semantic approval or execution certification is inferred.");
        report.put("chapter13Semantics", chapter13Semantics);
        report.put("scenarioCatalogProfile", scenarioArray.path());
        report.put("testCaseCatalogProfile", testCaseCatalogProfile);
        report.put("scenarioCount", scenarios.size());
        report.put("testCaseCount", cases.size());
        report.put("intakeRecordCount", intakeByCase.values().stream().mapToLong(List::size).sum());
        report.put("testCasesWithoutIntakeRecord", casesWithoutIntake(cases));
        report.put("unmatchedIntakeRecordCount", unmatchedIntake);
        report.set("caseVerdictCounts", MAPPER.valueToTree(verdictCounts));
        report.set("responseComparisonCounts", MAPPER.valueToTree(responseCounts));
        report.set("caseOutcomeOracleStatusCounts", MAPPER.valueToTree(caseOutcomeOracleCounts));
        report.set("defectCandidateCounts", MAPPER.valueToTree(defectCounts));
        report.put("negativeTargetPolicy", "Declared-target detection is diagnostic only; it is not isolated mutation-effectiveness certification.");
        ArrayNode negativeTargetCoverage = report.putArray("negativeTargetDispositionCounts");
        negativeTargetCounts.values().forEach(target -> {
            ObjectNode row = negativeTargetCoverage.addObject();
            row.put("negativeClass", target.negativeClass);
            row.put("violatedElement", target.violatedElement);
            row.put("disposition", target.disposition);
            row.put("caseCount", target.caseIds.size());
        });
        ArrayNode oracleGapQueue = report.putArray("oracleGapQueue");
        oracleGapGroups.values().forEach(gap -> {
            ObjectNode row = oracleGapQueue.addObject();
            row.put("gapId", gap.gapId);
            row.put("gapType", gap.gapType);
            row.put("oracleFamily", gap.oracleFamily);
            row.put("responseComparisonStatus", gap.responseComparisonStatus);
            row.put("reason", gap.reason);
            row.put("caseCount", gap.caseCount);
            row.set("sampleCaseIds", MAPPER.valueToTree(gap.sampleCaseIds));
        });
        report.putObject("oracleGapQueueSummary").put("distinctGapCount", oracleGapGroups.size())
                .put("casesQueued", oracleGapGroups.values().stream().mapToLong(gap -> gap.caseCount).sum())
                .put("sampleLimitPerGap", 10);
        ArrayNode ruleCoverageRows = report.putArray("independentRuleCoverage");
        ruleCoverage.values().forEach(coverage -> {
            ObjectNode row = ruleCoverageRows.addObject();
            row.put("segment", coverage.segment);
            row.put("element", coverage.element);
            row.put("ruleId", coverage.ruleId);
            row.put("casesExercised", coverage.caseIds.size());
            row.set("findingCountsByStatus", MAPPER.valueToTree(coverage.findingCounts));
            row.put("isolationStatus", "NOT_ASSESSED");
        });
        ObjectNode hashes = report.putObject("inputSha256");
        hashes.put("scenarioCatalog", sha256(scenarioCatalogFile));
        hashes.put("testCaseCatalog", sha256(testCaseCatalogFile));
        if (caseOutcomeOracleRegisterFile != null) {
            hashes.put("caseOutcomeOracleRegister", sha256(caseOutcomeOracleRegisterFile));
        }
        hashes.put("formatProfiles", sha256(pack.resolve("docs/specs/kb/elements/ai-intake-format-profiles.json")));
        report.set("flowDefectCandidates", flowFindings);
        if (supplementalFlowSummary != null) report.set("supplementalFlowCatalog", supplementalFlowSummary);
        report.set("cases", cases);
        return report;
    }

    private static final class RuleCoverageAccumulator {
        private final String segment;
        private final String element;
        private final String ruleId;
        private final Set<String> caseIds = new LinkedHashSet<>();
        private final Map<String, Long> findingCounts = new TreeMap<>();

        private RuleCoverageAccumulator(String segment, String element, String ruleId) {
            this.segment = segment;
            this.element = element;
            this.ruleId = ruleId;
        }
    }

    private static final class NegativeTargetAccumulator {
        private final String negativeClass;
        private final String violatedElement;
        private final String disposition;
        private final Set<String> caseIds = new LinkedHashSet<>();

        private NegativeTargetAccumulator(String negativeClass, String violatedElement, String disposition) {
            this.negativeClass = negativeClass;
            this.violatedElement = violatedElement;
            this.disposition = disposition;
        }
    }

    private static final class OracleGapAccumulator {
        private final String gapId;
        private final String gapType;
        private final String oracleFamily;
        private final String responseComparisonStatus;
        private final String reason;
        private long caseCount;
        private final List<String> sampleCaseIds = new ArrayList<>();

        private OracleGapAccumulator(String gapId, String gapType, String oracleFamily,
                                     String responseComparisonStatus, String reason) {
            this.gapId = gapId;
            this.gapType = gapType;
            this.oracleFamily = oracleFamily;
            this.responseComparisonStatus = responseComparisonStatus;
            this.reason = reason;
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 5) {
            throw new IllegalArgumentException("Usage: AiUnifiedValidationRunner <ATL105-pack> <run-directory> "
                    + "<scenario-catalog.json> <test-case-catalog.json> <new-report.json> "
                    + "[--chapter13-semantics] [--supplemental-flow-catalog suite-catalog.json] "
                    + "[--case-outcome-oracle-register register.json]");
        }
        Path pack = Path.of(args[0]);
        Path run = Path.of(args[1]);
        Path scenarioCatalog = Path.of(args[2]);
        Path testCaseCatalog = Path.of(args[3]);
        Path output = Path.of(args[4]);
        boolean chapter13Semantics = false;
        Path supplementalFlowCatalog = null;
        Path caseOutcomeOracleRegister = null;
        for (int index = 5; index < args.length; index++) {
            if ("--chapter13-semantics".equals(args[index])) {
                chapter13Semantics = true;
            } else if ("--supplemental-flow-catalog".equals(args[index]) && index + 1 < args.length) {
                supplementalFlowCatalog = Path.of(args[++index]);
            } else if ("--case-outcome-oracle-register".equals(args[index]) && index + 1 < args.length) {
                caseOutcomeOracleRegister = Path.of(args[++index]);
            } else {
                throw new IllegalArgumentException("Unknown or incomplete unified-runner option: " + args[index]);
            }
        }
        if (output.toAbsolutePath().normalize().startsWith(run.toAbsolutePath().normalize())) {
            throw new IllegalArgumentException("Unified report must be written outside the producer input folder");
        }
        if (Files.exists(output)) throw new IOException("Refusing to overwrite existing report: " + output);
        ObjectNode report = assess(pack, run, scenarioCatalog, testCaseCatalog, supplementalFlowCatalog,
            caseOutcomeOracleRegister, chapter13Semantics);
        Files.createDirectories(output.toAbsolutePath().getParent());
        ArrayNode caseRecords = (ArrayNode) report.remove("cases");
        Path caseDetails = output.resolveSibling(output.getFileName().toString().replaceFirst("\\.json$", "-cases.jsonl.gz"));
        if (Files.exists(caseDetails)) throw new IOException("Refusing to overwrite existing case details: " + caseDetails);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new GZIPOutputStream(Files.newOutputStream(caseDetails)), StandardCharsets.UTF_8))) {
            for (JsonNode envelope : caseRecords) {
                writer.write(MAPPER.writeValueAsString(envelope));
                writer.newLine();
            }
        }
        report.put("caseEnvelopeFormat", "JSONL_GZIP");
        report.put("caseEnvelopeFile", caseDetails.getFileName().toString());
        report.put("caseEnvelopeCount", caseRecords.size());
        report.put("caseEnvelopeSha256", sha256(caseDetails));
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), report);
        System.out.println("run=" + report.path("runId").asText() + " scenarios=" + report.path("scenarioCount").asInt()
                + " cases=" + report.path("testCaseCount").asInt() + " verdicts=" + report.path("caseVerdictCounts")
                + " responseChecks=" + report.path("responseComparisonCounts")
                + " defectCandidates=" + report.path("defectCandidateCounts"));
    }

    private record SupplementalFlowAudit(ObjectNode summary, ArrayNode findings) { }

    private static SupplementalFlowAudit auditSupplementalFlows(Path catalogFile) throws Exception {
        JsonNode catalog = MAPPER.readTree(catalogFile.toFile());
        JsonNode declaredFlows = catalog.path("testScenarios");
        JsonNode testCases = catalog.path("testCases");
        if (!declaredFlows.isArray() || !testCases.isArray()) {
            throw new IOException("Supplemental flow catalog must contain testScenarios and testCases arrays");
        }
        Map<String, Set<String>> scenarioIdsByFlow = new TreeMap<>();
        Map<String, Set<String>> namesByFlow = new TreeMap<>();
        for (JsonNode scenario : declaredFlows) {
            if (!"flow".equalsIgnoreCase(scenario.path("type").asText(""))) continue;
            String flowId = scenario.path("flowId").asText("");
            String scenarioId = scenario.path("id").asText("");
            if (flowId.isBlank() || scenarioId.isBlank()) continue;
            scenarioIdsByFlow.computeIfAbsent(flowId, ignored -> new LinkedHashSet<>()).add(scenarioId);
            String name = scenario.path("name").asText("");
            if (!name.isBlank()) namesByFlow.computeIfAbsent(flowId, ignored -> new LinkedHashSet<>()).add(name);
        }

        Map<String, Set<String>> caseIdsByFlow = new TreeMap<>();
        for (JsonNode testCase : testCases) {
            String caseId = testCase.path("id").asText("");
            String flowId = testCase.path("flowId").asText("");
            if (!flowId.isBlank() && scenarioIdsByFlow.containsKey(flowId)) {
                caseIdsByFlow.computeIfAbsent(flowId, ignored -> new LinkedHashSet<>()).add(caseId);
            }
            JsonNode scenarioIds = testCase.path("scenarioIds");
            if (scenarioIds.isArray()) {
                for (String candidateFlowId : scenarioIdsByFlow.keySet()) {
                    if (scenarioIdsByFlow.get(candidateFlowId).stream()
                            .anyMatch(id -> containsText(scenarioIds, id))) {
                        caseIdsByFlow.computeIfAbsent(candidateFlowId, ignored -> new LinkedHashSet<>()).add(caseId);
                    }
                }
            }
        }

        ArrayNode findings = MAPPER.createArrayNode();
        for (String flowId : scenarioIdsByFlow.keySet()) {
            if (!caseIdsByFlow.getOrDefault(flowId, Set.of()).isEmpty()) continue;
            ObjectNode finding = findings.addObject();
            finding.put("issueCode", "FLOW_DECLARED_WITHOUT_TEST_CASE");
            finding.put("scope", "SUPPLEMENTAL_FLOW");
            finding.put("subjectId", flowId);
            finding.put("status", "CANDIDATE_REVIEW_REQUIRED");
            ObjectNode evidence = finding.putObject("evidence");
            evidence.set("scenarioIds", MAPPER.valueToTree(scenarioIdsByFlow.get(flowId)));
            evidence.set("flowNames", MAPPER.valueToTree(namesByFlow.getOrDefault(flowId, Set.of())));
            evidence.put("testCaseCount", 0);
        }

        ObjectNode summary = MAPPER.createObjectNode();
        summary.put("catalogPath", catalogFile.toString());
        summary.put("catalogSha256", sha256(catalogFile));
        summary.put("manifestDeclaredFlowCount", catalog.path("manifest").path("counts").path("flowsDeclared").asInt(-1));
        summary.put("uniqueFlowIdCount", scenarioIdsByFlow.size());
        summary.put("flowIdsWithCases", caseIdsByFlow.size());
        summary.put("flowIdsWithoutCases", findings.size());
        summary.set("candidateCountsByIssue", MAPPER.createObjectNode().put("FLOW_DECLARED_WITHOUT_TEST_CASE", findings.size()));
        return new SupplementalFlowAudit(summary, findings);
    }

    private static boolean containsText(JsonNode values, String expected) {
        for (JsonNode value : values) if (expected.equals(value.asText())) return true;
        return false;
    }

    private static AiCanonicalCaseEnvelope envelope(String runId, ObjectNode testCase, JsonNode scenario,
            List<Atl105AiElementIntake.AssessedRunMessage> inputs,
            AiExpectedResponseComparator.Comparison response,
            AiCaseOutcomeOracleRegister.Decision outcomeOracle, List<ObjectNode> defects) {
        String caseId = testCase.path("id").asText("");
        String scenarioId = testCase.path("scenario_id").asText("");
        List<SourceArtifact> sourceArtifacts = new ArrayList<>();
        List<IndependentValidation> independent = new ArrayList<>();
        for (Atl105AiElementIntake.AssessedRunMessage input : inputs) {
            sourceArtifacts.add(new SourceArtifact(input.metadataPath(), input.payloadPath(), input.formatProfileId(),
                    input.metadataSha256(), input.payloadSha256()));
                List<RuleEvidence> rules = input.ruleFindings().stream().map(rule -> new RuleEvidence(
                    rule.segment(), rule.element(), rule.ruleId(), rule.status(), rule.findingCount())).toList();
                independent.add(new IndependentValidation(input.scenarioType(), input.assessmentStatus(),
                    input.observationCount(), input.observationsByStatus(), rules, input.intakeFindingCount(),
                    input.invalidFindingCount(), input.reviewRequiredFindingCount(), input.negativeClass(),
                    input.violatedElement(), input.negativeTargetDisposition(), input.inputError()));
        }

        Verdict verdict;
        String scope;
        String reason;
        String issueCode = defects.stream().map(finding -> finding.path("issueCode").asText("")
                ).filter(DEFECT_CODES::contains).findFirst().orElse("");
        if (!issueCode.isBlank() || response.status() == AiExpectedResponseComparator.Status.AI_DEFECT) {
            verdict = Verdict.AI_DEFECT;
            scope = "STRUCTURAL_OR_DECLARATION_DEFECT";
            reason = !issueCode.isBlank() ? "Automated artifact defect candidate: " + issueCode
                    : response.reason();
        } else if (Set.of("ORACLE_CONFLICT", "STALE_BINDING").contains(outcomeOracle.status())) {
            verdict = Verdict.ORACLE_GAP;
            scope = "ORACLE_CONFLICT".equals(outcomeOracle.status())
                    ? "CASE_AND_FAMILY_ORACLE_CONFLICT_REVIEW_REQUIRED" : "CASE_OUTCOME_ORACLE_BINDING_REVIEW_REQUIRED";
            reason = outcomeOracle.reason();
        } else if ("MISMATCH".equals(outcomeOracle.status())) {
            verdict = Verdict.DISAGREE;
            scope = "APPROVED_CASE_OUTCOME_ORACLE";
            reason = outcomeOracle.reason();
        } else if ("MATCH".equals(outcomeOracle.status())) {
            verdict = Verdict.AGREE;
            scope = "APPROVED_COMPLETE_CASE_OUTCOME_ORACLE";
            reason = outcomeOracle.reason();
        } else if (response.status() == AiExpectedResponseComparator.Status.DISAGREE) {
            verdict = Verdict.DISAGREE;
            scope = "RESPONSE_CODE_FAMILY_PERMISSION_ONLY";
            reason = response.reason();
        } else {
            verdict = Verdict.ORACLE_GAP;
            scope = "COMPLETE_TRANSACTION_OUTCOME_NOT_ESTABLISHED";
            reason = inputs.isEmpty() ? "No intake record matched this test case ID"
                    : "Independent payload checks are recorded, but no complete expected transaction-outcome oracle is available";
        }
        String gapId = verdict == Verdict.ORACLE_GAP ? oracleGapId(response, outcomeOracle) : "";
        List<CaseOutcomeEvidence> oracleEvidence = outcomeOracle.evidence().stream()
            .map(item -> new CaseOutcomeEvidence(item.type(), item.sourcePath(), item.sha256(), item.locator())).toList();

        return new AiCanonicalCaseEnvelope(1, "ATL105", "2026-3", runId, caseId, scenarioId,
            List.copyOf(AiArtifactDefectRegister.requirementIds(testCase)), verdict, scope, reason, gapId,
                List.copyOf(sourceArtifacts), List.copyOf(independent),
                new ResponseComparison(response.status().name(), response.oracleStatus().name(), response.oracleFamily(),
                        response.declarationConflict(), response.reason(),
                    "Family permission alone does not establish the exact transaction outcome"),
                new CaseOutcomeOracleComparison(outcomeOracle.status(), outcomeOracle.oracleId(),
                    outcomeOracle.reviewer(), outcomeOracle.reason(), oracleEvidence),
                defects.stream().map(finding -> (JsonNode) finding.deepCopy()).toList());
    }

    private static String gapType(AiExpectedResponseComparator.Comparison response) {
        if (response.status() == AiExpectedResponseComparator.Status.AGREE) return "TRANSACTION_OUTCOME_ORACLE_REQUIRED";
        String reason = response.reason().toLowerCase(java.util.Locale.ROOT);
        if (reason.contains("family")) return "RESPONSE_FAMILY_UNMAPPED";
        if (reason.contains("expected response")) return "EXPECTED_RESPONSE_MISSING";
        return "EXPECTED_OUTCOME_ORACLE_INCOMPLETE";
    }

    private static String gapType(AiExpectedResponseComparator.Comparison response,
                                  AiCaseOutcomeOracleRegister.Decision outcomeOracle) {
        return switch (outcomeOracle.status()) {
            case "ORACLE_CONFLICT" -> "CASE_AND_FAMILY_ORACLE_CONFLICT";
            case "STALE_BINDING" -> "CASE_OUTCOME_ORACLE_STALE_BINDING";
            default -> gapType(response);
        };
    }

    private static String gapReason(AiExpectedResponseComparator.Comparison response,
                                    AiCaseOutcomeOracleRegister.Decision outcomeOracle) {
        return Set.of("ORACLE_CONFLICT", "STALE_BINDING").contains(outcomeOracle.status())
                ? outcomeOracle.reason() : response.reason();
    }

    private static String oracleGapId(AiExpectedResponseComparator.Comparison response,
                                      AiCaseOutcomeOracleRegister.Decision outcomeOracle) {
        String type = gapType(response, outcomeOracle);
        String key = String.join("|", type, response.oracleFamily(), gapReason(response, outcomeOracle));
        try {
            String digest = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                    .digest(key.getBytes(StandardCharsets.UTF_8)));
            return "OG-" + digest.substring(0, 16).toUpperCase(java.util.Locale.ROOT);
        } catch (java.security.NoSuchAlgorithmException exception) {
            throw new IllegalStateException("Required SHA-256 algorithm unavailable", exception);
        }
    }

    private record CatalogArray(String path, List<JsonNode> values) { }

    private static CatalogArray catalogArray(JsonNode root, List<String> paths, String label) throws IOException {
        List<String> matches = paths.stream().filter(path -> root.at(path).isArray()).toList();
        if (matches.size() != 1) throw new IOException("Expected exactly one supported " + label + " array; found " + matches);
        List<JsonNode> values = new ArrayList<>();
        root.at(matches.getFirst()).forEach(values::add);
        return new CatalogArray(matches.getFirst(), List.copyOf(values));
    }

    private static String streamTestCases(Path file, TestCaseConsumer consumer) throws Exception {
        int matchedArrays = 0;
        String selectedPath = "";
        try (JsonParser parser = MAPPER.getFactory().createParser(file.toFile())) {
            JsonToken first = parser.nextToken();
            if (first == JsonToken.START_ARRAY) {
                matchedArrays++;
                selectedPath = "/";
                while (parser.nextToken() != JsonToken.END_ARRAY) consumer.accept(MAPPER.readTree(parser));
            } else if (first == JsonToken.START_OBJECT) {
                while (parser.nextToken() != JsonToken.END_OBJECT) {
                    String field = parser.currentName();
                    JsonToken token = parser.nextToken();
                    if (CASE_ARRAY_PATHS.stream().anyMatch(path -> ("/" + field).equals(path))) {
                        if (token != JsonToken.START_ARRAY) throw new IOException("Test-case catalog array is not an array: " + field);
                        matchedArrays++;
                        selectedPath = "/" + field;
                        while (parser.nextToken() != JsonToken.END_ARRAY) consumer.accept(MAPPER.readTree(parser));
                    } else {
                        parser.skipChildren();
                    }
                }
            } else {
                throw new IOException("Test-case catalog must be an object or array");
            }
        }
        if (matchedArrays != 1) throw new IOException("Expected exactly one supported test-case array; found " + matchedArrays);
        return selectedPath;
    }

    private static ObjectNode normalizeScenario(JsonNode source) throws IOException {
        if (!source.isObject()) throw new IOException("Scenario catalog entries must be objects");
        ObjectNode result = source.deepCopy();
        copyAlias(result, source, "id", "scenarioId", "scenario_id");
        copyAlias(result, source, "requirement_ids", "requirementIds", "requirement_ids");
        copyAlias(result, source, "target_transaction", "targetTransaction", "transactionType", "transaction_type");
        copyAlias(result, source, "expected_response_code", "expectedResponseCode", "expected_response_code");
        copyAlias(result, source, "type", "scenarioType", "scenario_type");
        return result;
    }

    private static ObjectNode normalizeTestCase(JsonNode source) throws IOException {
        if (!source.isObject()) throw new IOException("Test-case catalog entries must be objects");
        ObjectNode result = source.deepCopy();
        copyAlias(result, source, "id", "testCaseId", "test_case_id");
        copyAlias(result, source, "scenario_id", "scenarioId", "scenario_id");
        copyAlias(result, source, "requirement_ids", "requirementIds", "requirement_ids");
        copyAlias(result, source, "requirement_id", "requirementId", "requirement_id");
        copyAlias(result, source, "test_data_file", "testDataFile", "payloadFile", "payload_file", "requestFile", "request_file");
        copyAlias(result, source, "test_data_leg_files", "testDataLegFiles", "test_data_leg_files");
        copyAlias(result, source, "is_flow", "isFlow", "is_flow");
        JsonNode expected = first(source, "expected_response", "expectedResponse");
        if (expected.isObject()) {
            ObjectNode normalizedExpected = expected.deepCopy();
            JsonNode responseCode = first(expected, "response_code", "responseCode");
            if (!responseCode.isMissingNode()) normalizedExpected.set("response_code", responseCode.deepCopy());
            result.set("expected_response", normalizedExpected);
        }
        return result;
    }

    private static void copyAlias(ObjectNode destination, JsonNode source, String canonical, String... aliases) {
        if (destination.has(canonical) && !destination.path(canonical).isNull()) return;
        JsonNode value = first(source, aliases);
        if (!value.isMissingNode() && !value.isNull()) destination.set(canonical, value.deepCopy());
    }

    private static JsonNode first(JsonNode source, String... names) {
        for (String name : names) {
            JsonNode candidate = source.path(name);
            if (!candidate.isMissingNode() && !candidate.isNull()) return candidate;
        }
        return MAPPER.nullNode();
    }

    private static int casesWithoutIntake(JsonNode cases) {
        int count = 0;
        for (JsonNode envelope : cases) if (envelope.path("sourceArtifacts").isEmpty()) count++;
        return count;
    }

    private static String sha256(Path file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (var input = Files.newInputStream(file)) {
            byte[] buffer = new byte[1024 * 1024];
            int count;
            while ((count = input.read(buffer)) != -1) digest.update(buffer, 0, count);
        }
        return HexFormat.of().formatHex(digest.digest());
    }
}
