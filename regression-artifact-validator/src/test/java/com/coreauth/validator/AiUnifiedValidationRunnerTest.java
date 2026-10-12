package com.coreauth.validator;

import com.coreauth.validator.canonical.AiUnifiedValidationRunner;
import com.coreauth.validator.canonical.AiCaseOutcomeOracleRegister;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AiUnifiedValidationRunnerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path PACK = Path.of("specifications", "ATL105");

    @TempDir Path directory;

    @Test
    void joinsIntakeResponseAndDefectEvidenceWithoutPromotingFamilyAgreement() throws Exception {
        Path run = Files.createDirectory(directory.resolve("run"));
        writePayload(run, "TC-AGREE", "{\"Financial Transaction Request\":{\"NumberofSegments\":\"1\","
                + "\"Standard\":{\"SegmentType\":\"100\",\"SequenceNumber\":\"000001\"}}}");
        writePayload(run, "TC-DISAGREE", "{\"Financial Transaction Request\":{\"ResponseCode\":\"0\"}}");
        writePayload(run, "TC-GAP", "{\"Financial Transaction Request\":{\"ResponseCode\":\"2\"}}");
        writePayload(run, "TC-GAP-2", "{\"Financial Transaction Request\":{\"ResponseCode\":\"2\"}}");
        writePayload(run, "TC-DEFECT", "{\"Financial Transaction Request\":{\"Request\":{}}}");
        writeMetadata(run, "TC-AGREE");
        var negativeMetadata = MAPPER.createObjectNode()
            .put("testCaseId", "TC-GAP")
            .put("messageFamily", "Financial Transaction Request")
            .put("scenarioType", "negative")
            .put("negativeClass", "charset_invalid")
            .put("violatedElementName", "Sequence Number");
        negativeMetadata.putArray("fields").addObject()
            .put("segment", "100").put("element", "SequenceNumber")
            .put("specElementName", "Sequence Number").put("value", "ABC123");
        MAPPER.writeValue(run.resolve("TC-GAP.meta.json").toFile(), negativeMetadata);

        Path scenarios = directory.resolve("scenario-catalog.json");
        MAPPER.writeValue(scenarios.toFile(), MAPPER.readTree("""
                        {"approvedScenarios":[
                            {"scenarioId":"SC-AGREE","scenarioType":"positive","requirementIds":["BR-1"],
                             "targetTransaction":"Financial Transaction Request","expectedResponseCode":"2"},
                            {"id":"SC-DISAGREE","type":"positive","requirement_ids":["BR-2"],
                             "target_transaction":"Financial Transaction Request","expected_response_code":"8"},
                            {"id":"SC-GAP","type":"positive","requirement_ids":["BR-3"],
                             "target_transaction":"Auth Completion (0220)"},
                              {"id":"SC-GAP-2","type":"positive","requirement_ids":["BR-3B"],
                               "target_transaction":"Auth Completion (0220)"},
                            {"id":"SC-DEFECT","type":"positive","requirement_ids":["BR-SCENARIO"],
                             "target_transaction":"Financial Transaction Request","expected_response_code":"2"},
                            {"id":"SC-FLOW","type":"flow","requirement_ids":["BR-FLOW"],"name":"Lifecycle"}
            ]}
            """));

        Path testCases = directory.resolve("case-catalog.json");
        MAPPER.writeValue(testCases.toFile(), MAPPER.readTree("""
                        {"testCases":[
                            {"testCaseId":"TC-AGREE","scenarioId":"SC-AGREE","requirementIds":["BR-1"],
                             "testDataFile":"TC-AGREE.json","expectedResponse":{"responseCode":{"value":"2"}}},
                            {"id":"TC-DISAGREE","scenario_id":"SC-DISAGREE","requirement_ids":["BR-2"],
                             "test_data_file":"TC-DISAGREE.json","expected_response":{"response_code":{"value":"8"}}},
                            {"id":"TC-GAP","scenario_id":"SC-GAP","requirement_ids":["BR-3"],
                               "test_data_file":"TC-GAP.json","expected_response":{"response_code":{"value":"2"}}},
                              {"id":"TC-GAP-2","scenario_id":"SC-GAP-2","requirement_ids":["BR-3B"],
                               "test_data_file":"TC-GAP-2.json","expected_response":{"response_code":{"value":"2"}}},
                            {"id":"TC-DEFECT","scenario_id":"SC-DEFECT","requirement_ids":["BR-CASE"],
                             "test_data_file":"TC-DEFECT.json","expected_response":{"response_code":{"value":"2"}}}
            ]}
            """));

                Path suiteCatalog = directory.resolve("supplemental-suite.json");
                MAPPER.writeValue(suiteCatalog.toFile(), MAPPER.readTree("""
                        {"manifest":{"counts":{"flowsDeclared":2}},
                         "testScenarios":[
                             {"id":"SCN-LINKED","flowId":"FLOW-LINKED","type":"flow","name":"Linked flow"},
                             {"id":"SCN-MISSING","flowId":"FLOW-MISSING","type":"flow","name":"Missing flow"}],
                         "testCases":[{"id":"TC-SUITE-LINKED","flowId":"FLOW-LINKED","scenarioIds":["SCN-LINKED"]}]}
                        """));

                JsonNode report = AiUnifiedValidationRunner.assess(PACK, run, scenarios, testCases, suiteCatalog, false);
        assertThat(report.path("schemaVersion").asInt()).isEqualTo(1);
        assertThat(report.path("testCaseCount").asInt()).isEqualTo(5);
        assertThat(report.path("scenarioCatalogProfile").asText()).isEqualTo("/approvedScenarios");
        assertThat(report.path("testCaseCatalogProfile").asText()).isEqualTo("/testCases");
        assertThat(report.path("caseVerdictCounts").path("ORACLE_GAP").asInt())
            .as("verdictCounts=%s cases=%s", report.path("caseVerdictCounts"), report.path("cases"))
            .isEqualTo(3);
        assertThat(report.path("caseVerdictCounts").path("DISAGREE").asInt()).isEqualTo(1);
        assertThat(report.path("caseVerdictCounts").path("AI_DEFECT").asInt()).isEqualTo(1);
        assertThat(report.path("flowDefectCandidates")).hasSize(2);
        assertThat(report.path("supplementalFlowCatalog").path("manifestDeclaredFlowCount").asInt()).isEqualTo(2);
        assertThat(report.path("supplementalFlowCatalog").path("flowIdsWithCases").asInt()).isEqualTo(1);
        assertThat(report.path("supplementalFlowCatalog").path("flowIdsWithoutCases").asInt()).isEqualTo(1);
        assertThat(report.path("flowDefectCandidates").toString()).contains("FLOW-MISSING")
            .doesNotContain("FLOW-LINKED");
        assertThat(report.path("oracleGapQueueSummary").path("distinctGapCount").asInt()).isEqualTo(2);
        assertThat(report.path("oracleGapQueueSummary").path("casesQueued").asInt()).isEqualTo(3);
        assertThat(report.path("oracleGapQueue")).allSatisfy(gap -> assertThat(gap.path("sampleCaseIds").size()).isLessThanOrEqualTo(10));
        assertThat(report.path("oracleGapQueue")).anySatisfy(gap -> {
            assertThat(gap.path("gapType").asText()).isEqualTo("RESPONSE_FAMILY_UNMAPPED");
            assertThat(gap.path("caseCount").asInt()).isEqualTo(2);
            assertThat(gap.path("sampleCaseIds").toString()).contains("TC-GAP", "TC-GAP-2");
        });

        JsonNode agreeCase = envelope(report, "TC-AGREE");
        assertThat(agreeCase.path("verdict").asText()).isEqualTo("ORACLE_GAP");
        assertThat(agreeCase.path("oracleGapId").asText()).startsWith("OG-");
        assertThat(agreeCase.path("responseComparison").path("status").asText()).isEqualTo("AGREE");
        assertThat(agreeCase.path("verdictReason").asText()).contains("transaction-outcome oracle");
        assertThat(agreeCase.path("sourceArtifacts").get(0).path("formatProfileId").asText()).isEqualTo("qe-meta-sibling-v1");
        assertThat(agreeCase.path("sourceArtifacts").get(0).path("metadataSha256").asText()).hasSize(64);

        JsonNode disagreeCase = envelope(report, "TC-DISAGREE");
        assertThat(disagreeCase.path("verdict").asText()).isEqualTo("DISAGREE");
        assertThat(disagreeCase.path("responseComparison").path("status").asText()).isEqualTo("DISAGREE");

        JsonNode gapCase = envelope(report, "TC-GAP");
        assertThat(gapCase.path("verdict").asText()).isEqualTo("ORACLE_GAP");
        assertThat(gapCase.path("responseComparison").path("status").asText()).isEqualTo("ORACLE_GAP");
        assertThat(gapCase.path("independentValidations").get(0).path("ruleResults")).isNotEmpty();
        assertThat(gapCase.path("independentValidations").get(0).path("negativeTargetDisposition").asText())
            .isEqualTo("TARGET_DETECTED");
        assertThat(report.path("independentRuleCoverage")).isNotEmpty();
        assertThat(report.path("negativeTargetDispositionCounts").get(0).path("caseCount").asInt()).isEqualTo(1);

        JsonNode defectCase = envelope(report, "TC-DEFECT");
        assertThat(defectCase.path("verdict").asText()).isEqualTo("AI_DEFECT");
        assertThat(defectCase.path("defectCandidates").toString()).contains("BR_ATTRIBUTION_NO_SHARED_LINK");
    }

    @Test
    void cliWritesCompactSummaryAndHashedGzipEnvelopeSidecar() throws Exception {
        Path run = Files.createDirectory(directory.resolve("persisted-run"));
        writePayload(run, "TC-AGREE", "{\"Financial Transaction Request\":{\"ResponseCode\":\"0\"}}");
        writeMetadata(run, "TC-AGREE");
        Path scenarios = directory.resolve("persisted-scenarios.json");
        MAPPER.writeValue(scenarios.toFile(), MAPPER.readTree("""
            {"scenarios":[{"id":"SC-AGREE","type":"positive","requirement_ids":["BR-1"],
              "target_transaction":"Financial Transaction Request","expected_response_code":"2"}]}
            """));
        Path cases = directory.resolve("persisted-cases.json");
        MAPPER.writeValue(cases.toFile(), MAPPER.readTree("""
            {"test_cases":[{"id":"TC-AGREE","scenario_id":"SC-AGREE","requirement_ids":["BR-1"],
              "test_data_file":"TC-AGREE.json","expected_response":{"response_code":{"value":"2"}}}]}
            """));
        Path reportFile = directory.resolve("unified-report.json");

        AiUnifiedValidationRunner.main(new String[]{PACK.toString(), run.toString(), scenarios.toString(),
                cases.toString(), reportFile.toString()});

        JsonNode report = MAPPER.readTree(reportFile.toFile());
        assertThat(report.has("cases")).isFalse();
        assertThat(report.path("caseEnvelopeCount").asInt()).isEqualTo(1);
        Path sidecar = reportFile.resolveSibling(report.path("caseEnvelopeFile").asText());
        assertThat(Files.isRegularFile(sidecar)).isTrue();
        String hash = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(sidecar)));
        assertThat(report.path("caseEnvelopeSha256").asText()).isEqualTo(hash);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new java.util.zip.GZIPInputStream(Files.newInputStream(sidecar)), StandardCharsets.UTF_8))) {
            JsonNode envelope = MAPPER.readTree(reader.readLine());
            assertThat(envelope.path("testCaseId").asText()).isEqualTo("TC-AGREE");
            assertThat(reader.readLine()).isNull();
        }
    }

    @Test
    void adjudicatedOracleMatchesValuesWithoutTreatingProducerConfidenceAsOutcome() throws Exception {
        OracleFixture fixture = oracleFixture();
        JsonNode report = assessOracle(fixture);
        JsonNode result = envelope(report, "TC-AGREE");
        assertThat(result.path("verdict").asText()).isEqualTo("AGREE");
        assertThat(result.path("verdictScope").asText()).isEqualTo("APPROVED_COMPLETE_CASE_OUTCOME_ORACLE");
        assertThat(result.path("oracleGapId").asText()).isEmpty();
        assertThat(result.path("caseOutcomeOracle").path("status").asText()).isEqualTo("MATCH");
        assertThat(result.path("caseOutcomeOracle").path("reviewer").asText()).isEqualTo("Fixture reviewer");
        assertThat(result.path("caseOutcomeOracle").path("evidence")).hasSize(1);
        assertThat(report.path("caseOutcomeOracleStatusCounts").path("MATCH").asInt()).isEqualTo(1);
        assertThat(report.path("inputSha256").path("caseOutcomeOracleRegister").asText()).hasSize(64);
    }

    @Test
    void independentOutcomeMismatchDoesNotRequireAResponseFamilyDisagreement() throws Exception {
        OracleFixture fixture = oracleFixture();
        ObjectNode register = (ObjectNode) MAPPER.readTree(fixture.register().toFile());
        ((ObjectNode) register.path("entries").get(0).path("expectedResponse")).put("response_code", "0");
        MAPPER.writeValue(fixture.register().toFile(), register);
        JsonNode result = envelope(assessOracle(fixture), "TC-AGREE");
        assertThat(result.path("responseComparison").path("status").asText()).isEqualTo("AGREE");
        assertThat(result.path("caseOutcomeOracle").path("status").asText()).isEqualTo("MISMATCH");
        assertThat(result.path("verdict").asText()).isEqualTo("DISAGREE");
        assertThat(result.path("verdictScope").asText()).isEqualTo("APPROVED_CASE_OUTCOME_ORACLE");
    }

    @Test
    void pendingOrRejectedOracleDoesNotGrantAgreement() throws Exception {
        OracleFixture fixture = oracleFixture();
        ObjectNode register = (ObjectNode) MAPPER.readTree(fixture.register().toFile());
        ObjectNode entry = (ObjectNode) register.path("entries").get(0);
        entry.put("status", "PENDING");
        MAPPER.writeValue(fixture.register().toFile(), register);
        JsonNode pending = envelope(assessOracle(fixture), "TC-AGREE");
        assertThat(pending.path("verdict").asText()).isEqualTo("ORACLE_GAP");
        assertThat(pending.path("caseOutcomeOracle").path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        entry.put("status", "REJECTED");
        MAPPER.writeValue(fixture.register().toFile(), register);
        JsonNode rejected = envelope(assessOracle(fixture), "TC-AGREE");
        assertThat(rejected.path("verdict").asText()).isEqualTo("ORACLE_GAP");
        assertThat(rejected.path("caseOutcomeOracle").path("status").asText()).isEqualTo("REJECTED");
    }

    @Test
    void stalePhysicalBindingAndIndependentOracleConflictStayUnresolved() throws Exception {
        OracleFixture fixture = oracleFixture();
        ObjectNode register = (ObjectNode) MAPPER.readTree(fixture.register().toFile());
        ObjectNode entry = (ObjectNode) register.path("entries").get(0);
        ((ObjectNode) entry.path("expectedResponse")).put("response_code", "8");
        MAPPER.writeValue(fixture.register().toFile(), register);
        JsonNode conflict = envelope(assessOracle(fixture), "TC-AGREE");
        assertThat(conflict.path("verdict").asText()).isEqualTo("ORACLE_GAP");
        assertThat(conflict.path("caseOutcomeOracle").path("status").asText()).isEqualTo("ORACLE_CONFLICT");
        assertThat(assessOracle(fixture).path("oracleGapQueue").get(0).path("gapType").asText())
            .isEqualTo("CASE_AND_FAMILY_ORACLE_CONFLICT");
        ((ObjectNode) entry.path("expectedResponse")).put("response_code", "2");
        ((ObjectNode) entry.path("sourceArtifacts").get(0)).put("payloadSha256", "0".repeat(64));
        MAPPER.writeValue(fixture.register().toFile(), register);
        JsonNode stale = envelope(assessOracle(fixture), "TC-AGREE");
        assertThat(stale.path("verdict").asText()).isEqualTo("ORACLE_GAP");
        assertThat(stale.path("caseOutcomeOracle").path("status").asText()).isEqualTo("STALE_BINDING");
        assertThat(assessOracle(fixture).path("oracleGapQueue").get(0).path("gapType").asText())
            .isEqualTo("CASE_OUTCOME_ORACLE_STALE_BINDING");
    }

    @Test
    void refusesApprovalWithoutIndependentEvidenceOrCompleteness() throws Exception {
        OracleFixture fixture = oracleFixture();
        ObjectNode register = (ObjectNode) MAPPER.readTree(fixture.register().toFile());
        ObjectNode entry = (ObjectNode) register.path("entries").get(0);
        entry.put("completeness", "CODE_FAMILY_ONLY");
        MAPPER.writeValue(fixture.register().toFile(), register);
        assertThatThrownBy(() -> assessOracle(fixture)).hasMessageContaining("COMPLETE_CASE_OUTCOME");
        entry.put("completeness", "COMPLETE_CASE_OUTCOME");
        entry.put("independenceAttestation", "AI_GENERATED");
        MAPPER.writeValue(fixture.register().toFile(), register);
        assertThatThrownBy(() -> assessOracle(fixture)).hasMessageContaining("independent-source attestation");
        entry.put("independenceAttestation", "I_REVIEWED_SOURCE_INDEPENDENTLY_OF_AI_OUTPUT");
        ObjectNode evidence = (ObjectNode) entry.path("evidence").get(0);
        evidence.put("sha256", "0".repeat(64));
        MAPPER.writeValue(fixture.register().toFile(), register);
        assertThatThrownBy(() -> assessOracle(fixture)).hasMessageContaining("evidence SHA-256 mismatch");
        evidence.put("sourcePath", fixture.run().resolve("TC-AGREE.json").toString());
        evidence.put("sha256", hash(fixture.run().resolve("TC-AGREE.json")));
        MAPPER.writeValue(fixture.register().toFile(), register);
        assertThatThrownBy(() -> assessOracle(fixture)).hasMessageContaining("not producer input");
    }

    @Test
    void refusesCatalogDriftDuplicateEntriesAndUnknownCases() throws Exception {
        OracleFixture fixture = oracleFixture();
        ObjectNode register = (ObjectNode) MAPPER.readTree(fixture.register().toFile());
        ((ObjectNode) register.path("inputSha256")).put("testCaseCatalog", "0".repeat(64));
        MAPPER.writeValue(fixture.register().toFile(), register);
        assertThatThrownBy(() -> assessOracle(fixture)).hasMessageContaining("input hashes");
        ((ObjectNode) register.path("inputSha256")).put("testCaseCatalog", hash(fixture.cases()));
        ObjectNode entry = (ObjectNode) register.path("entries").get(0);
        entry.put("status", "PENDING");
        ((com.fasterxml.jackson.databind.node.ArrayNode) register.path("entries")).add(entry.deepCopy());
        MAPPER.writeValue(fixture.register().toFile(), register);
        assertThatThrownBy(() -> assessOracle(fixture)).hasMessageContaining("Duplicate case");
        ((com.fasterxml.jackson.databind.node.ArrayNode) register.path("entries")).remove(1);
        entry.put("testCaseId", "TC-UNKNOWN");
        MAPPER.writeValue(fixture.register().toFile(), register);
        assertThatThrownBy(() -> assessOracle(fixture)).hasMessageContaining("unknown test case");
    }

    @Test
    void preparesPendingCasesAndRecordsReviewedVersionsWithoutChangingPriorRegister() throws Exception {
        OracleFixture fixture = oracleFixture();
        Path temporaryPack = Files.createDirectory(directory.resolve("workflow-pack"));
        Path outputRoot = Files.createDirectory(temporaryPack.resolve("test-output"));
        Path summaryFile = directory.resolve("workflow-summary.json");
        ObjectNode baseline = AiUnifiedValidationRunner.assess(PACK, fixture.run(), fixture.scenarios(), fixture.cases(), false);
        MAPPER.writeValue(summaryFile.toFile(), baseline);
        Path pending = outputRoot.resolve("pending.json");
        AiCaseOutcomeOracleRegister.main(new String[]{"--prepare", temporaryPack.toString(), summaryFile.toString(), pending.toString()});
        JsonNode prepared = MAPPER.readTree(pending.toFile());
        assertThat(prepared.path("summary").path("pendingCount").asInt()).isEqualTo(1);
        assertThat(prepared.path("entries").get(0).path("expectedResponse").isNull()).isTrue();
        assertThat(prepared.path("entries").get(0).path("reviewHistory")).isEmpty();
        String pendingHash = hash(pending);
        ObjectNode decision = MAPPER.readTree(fixture.register().toFile()).path("entries").get(0).deepCopy();
        decision.put("previousRegisterSha256", pendingHash);
        Path decisionFile = directory.resolve("reviewer-decision.json");
        MAPPER.writeValue(decisionFile.toFile(), decision);
        Path reviewed = outputRoot.resolve("reviewed.json");
        String[] recordArgs = {"--record", temporaryPack.toString(), fixture.run().toString(),
                fixture.scenarios().toString(), fixture.cases().toString(), pending.toString(),
                decisionFile.toString(), "--new-register", reviewed.toString()};
        AiCaseOutcomeOracleRegister.main(recordArgs);
        assertThat(hash(pending)).isEqualTo(pendingHash);
        JsonNode reviewedRegister = MAPPER.readTree(reviewed.toFile());
        assertThat(reviewedRegister.path("previousRegisterSha256").asText()).isEqualTo(pendingHash);
        assertThat(reviewedRegister.path("entries").get(0).path("reviewHistory").get(0).path("status").asText()).isEqualTo("PENDING");
        assertThat(reviewedRegister.path("summary").path("approvedCount").asInt()).isEqualTo(1);
        JsonNode assessed = AiUnifiedValidationRunner.assess(PACK, fixture.run(), fixture.scenarios(), fixture.cases(), null, reviewed, false);
        assertThat(envelope(assessed, "TC-AGREE").path("verdict").asText()).isEqualTo("AGREE");
        Path assessedSummary = directory.resolve("reviewed-assessment.json");
        AiUnifiedValidationRunner.main(new String[]{PACK.toString(), fixture.run().toString(), fixture.scenarios().toString(),
            fixture.cases().toString(), assessedSummary.toString(), "--case-outcome-oracle-register", reviewed.toString()});
        JsonNode persistedAssessment = MAPPER.readTree(assessedSummary.toFile());
        assertThat(persistedAssessment.path("caseVerdictCounts").path("AGREE").asInt()).isEqualTo(1);
        assertThat(persistedAssessment.path("inputSha256").path("caseOutcomeOracleRegister").asText()).isEqualTo(hash(reviewed));
        assertThatThrownBy(() -> AiCaseOutcomeOracleRegister.main(recordArgs)).hasMessageContaining("Refusing to overwrite");
        decision.put("previousRegisterSha256", "0".repeat(64));
        MAPPER.writeValue(decisionFile.toFile(), decision);
        recordArgs[8] = outputRoot.resolve("stale-review.json").toString();
        assertThatThrownBy(() -> AiCaseOutcomeOracleRegister.main(recordArgs)).hasMessageContaining("exact prior register SHA-256");
        assertThat(Files.exists(Path.of(recordArgs[8]))).isFalse();
        String[] outsideArgs = {"--prepare", temporaryPack.toString(), summaryFile.toString(), directory.resolve("outside.json").toString()};
        assertThatThrownBy(() -> AiCaseOutcomeOracleRegister.main(outsideArgs)).hasMessageContaining("beneath ATL105 test-output");
    }

    @Test
    void preparesFromHashedCompactSidecarAndRejectsHashOrPopulationMismatch() throws Exception {
        OracleFixture fixture = oracleFixture();
        Path summaryFile = directory.resolve("compact-oracle-summary.json");
        AiUnifiedValidationRunner.main(new String[]{PACK.toString(), fixture.run().toString(),
                fixture.scenarios().toString(), fixture.cases().toString(), summaryFile.toString()});
        ObjectNode register = AiCaseOutcomeOracleRegister.prepare(summaryFile);
        assertThat(register.path("entries")).hasSize(1);
        assertThat(register.path("entries").get(0).path("sourceArtifacts")).hasSize(1);
        ObjectNode summary = (ObjectNode) MAPPER.readTree(summaryFile.toFile());
        summary.put("caseEnvelopeSha256", "0".repeat(64));
        MAPPER.writeValue(summaryFile.toFile(), summary);
        assertThatThrownBy(() -> AiCaseOutcomeOracleRegister.prepare(summaryFile)).hasMessageContaining("SHA-256 mismatch");
        summary.put("caseEnvelopeSha256", hash(summaryFile.resolveSibling(summary.path("caseEnvelopeFile").asText())));
        summary.put("testCaseCount", 2);
        MAPPER.writeValue(summaryFile.toFile(), summary);
        assertThatThrownBy(() -> AiCaseOutcomeOracleRegister.prepare(summaryFile)).hasMessageContaining("population");
    }

    @Test
    void caseOracleNeverOverridesAnArtifactDefect() throws Exception {
        OracleFixture fixture = oracleFixture();
        writePayload(fixture.run(), "TC-AGREE", "{\"Financial Transaction Request\":{\"Request\":{}}}");
        JsonNode result = envelope(assessOracle(fixture), "TC-AGREE");
        assertThat(result.path("verdict").asText()).isEqualTo("AI_DEFECT");
        assertThat(result.path("caseOutcomeOracle").path("status").asText()).isEqualTo("STALE_BINDING");
        assertThat(result.path("defectCandidates").toString()).contains("EMPTY_PHYSICAL_PAYLOAD");
    }

    private record OracleFixture(Path run, Path scenarios, Path cases, Path register) { }

    private OracleFixture oracleFixture() throws Exception {
        Path run = Files.createDirectory(directory.resolve("oracle-run"));
        writePayload(run, "TC-AGREE", "{\"Financial Transaction Request\":{\"ResponseCode\":\"0\"}}");
        writeMetadata(run, "TC-AGREE");
        Path scenarios = directory.resolve("oracle-scenarios.json");
        Path cases = directory.resolve("oracle-cases.json");
        MAPPER.writeValue(scenarios.toFile(), MAPPER.readTree("""
            {"scenarios":[{"id":"SC-AGREE","type":"positive","requirement_ids":["BR-1"],
              "target_transaction":"Financial Transaction Request","expected_response_code":"2"}]}
            """));
        MAPPER.writeValue(cases.toFile(), MAPPER.readTree("""
            {"test_cases":[{"id":"TC-AGREE","scenario_id":"SC-AGREE","requirement_ids":["BR-1"],
              "test_data_file":"TC-AGREE.json","expected_response":{"response_code":{"value":"2","tier":"C"}}}]}
            """));
        JsonNode baseline = AiUnifiedValidationRunner.assess(PACK, run, scenarios, cases, false);
        Path source = directory.resolve("independent-oracle-evidence.json");
        MAPPER.writeValue(source.toFile(), MAPPER.readTree("{\"syntheticTestEvidence\":true}"));
        ObjectNode register = MAPPER.createObjectNode();
        register.put("artifact", "ATL105-CASE-LEVEL-OUTCOME-ORACLE-REGISTER").put("schemaVersion", 1)
                .put("specification", "ATL105").put("specificationVersion", "2026-3").put("runId", "oracle-run");
        register.set("inputSha256", baseline.path("inputSha256"));
        ObjectNode entry = register.putArray("entries").addObject();
        entry.put("testCaseId", "TC-AGREE").put("scenarioId", "SC-AGREE").put("status", "APPROVED")
                .put("oracleId", "OR-FIXTURE-1").put("reviewer", "Fixture reviewer")
                .put("reviewedAt", "2026-10-12T10:00:00Z").put("rationale", "Synthetic review fixture only")
                .put("completeness", "COMPLETE_CASE_OUTCOME")
                .put("independenceAttestation", "I_REVIEWED_SOURCE_INDEPENDENTLY_OF_AI_OUTPUT");
        entry.set("sourceArtifacts", envelope(baseline, "TC-AGREE").path("sourceArtifacts"));
        entry.putObject("expectedResponse").put("response_code", "2");
        entry.putArray("evidence").addObject().put("type", "AUTHORIZED_REVIEW_RECORD")
                .put("sourcePath", source.toString()).put("sha256", hash(source)).put("locator", "syntheticTestEvidence");
        Path registerFile = directory.resolve("oracle-register.json");
        MAPPER.writeValue(registerFile.toFile(), register);
        return new OracleFixture(run, scenarios, cases, registerFile);
    }

    private JsonNode assessOracle(OracleFixture fixture) throws Exception {
        return AiUnifiedValidationRunner.assess(PACK, fixture.run(), fixture.scenarios(), fixture.cases(),
                null, fixture.register(), false);
    }

    private static String hash(Path file) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(file)));
    }

    private static void writeMetadata(Path run, String testCaseId) throws Exception {
        MAPPER.writeValue(run.resolve(testCaseId + ".meta.json").toFile(), MAPPER.readTree("""
            {"testCaseId":"TC-AGREE","messageFamily":"Financial Transaction Request","scenarioType":"positive","fields":[]}
            """));
    }

    private static void writePayload(Path run, String testCaseId, String payload) throws Exception {
        MAPPER.writeValue(run.resolve(testCaseId + ".json").toFile(), MAPPER.readTree(payload));
    }

    private static JsonNode envelope(JsonNode report, String id) {
        for (JsonNode candidate : report.path("cases")) {
            if (id.equals(candidate.path("testCaseId").asText())) return candidate;
        }
        throw new AssertionError("No canonical envelope for " + id);
    }
}
