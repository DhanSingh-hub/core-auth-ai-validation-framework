package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GenerateSegmentDl2AiArtifactCoverageReportTest {
    private final GenerateSegmentDl2AiArtifactCoverageReport generator =
            new GenerateSegmentDl2AiArtifactCoverageReport();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void calculatesCandidateCoverageSeparatelyFromExecution(@TempDir Path tempDir) throws Exception {
        JsonNode report = generate(tempDir);

        assertThat(report.path("aiArtifactInventory").path("dl2Requirements").asInt()).isEqualTo(1);
        assertThat(report.path("aiArtifactInventory").path("dl2Scenarios").asInt()).isEqualTo(1);
        assertThat(report.path("aiArtifactInventory").path("dl2TestCases").asInt()).isEqualTo(1);
        assertThat(report.path("aiArtifactInventory").path("suppliedAiCatalogDl2Requirements").asInt()).isEqualTo(16);
        assertThat(report.path("aiChainIntegrity").path("traceabilityStatus").asText()).isEqualTo("COMPLETE");
        assertThat(report.path("aiChainIntegrity").path("transactionType").asText())
                .isEqualTo("Date & Time Load Response");

        assertThat(report.path("testSolutionMetrics").path("oracleRuleCount").asInt()).isEqualTo(9);
        assertThat(report.path("testSolutionMetrics").path("rulesWithCompleteCandidateChains").asInt()).isEqualTo(9);
        assertThat(report.path("testSolutionMetrics").path("rulesWithPositiveAndNegativeCandidates").asInt()).isEqualTo(9);
        assertThat(report.path("testSolutionMetrics").path("candidateTestCases").asInt()).isEqualTo(29);
        assertThat(report.path("testSolutionMetrics").path("candidateRequestResponsePairs").asInt()).isEqualTo(29);
        assertThat(report.path("testSolutionMetrics").path("executionCoveragePercent").asDouble()).isZero();
        assertThat(report.path("aiCoverageOfTestSolutionOracle")
                .path("rulesWithCandidateAiMappingsPendingReview").asInt()).isEqualTo(5);
        assertThat(report.path("aiCoverageOfTestSolutionOracle").path("rulesWithoutAiEvidence").asInt()).isEqualTo(4);
        assertThat(report.path("suppliedAiDl2Requirements").size()).isEqualTo(16);
        assertThat(report.path("testSolutionMetrics").path("approvedTestDataPairs").asInt()).isZero();
        assertThat(report.path("testSolutionMetrics").path("executedTestCases").asInt()).isZero();
        assertThat(report.path("testSolutionMetrics").path("certifiedCoveredRules").asInt()).isZero();
    }

    @Test
    void independentlyRejectsInvalidSuppliedAiData(@TempDir Path tempDir) throws Exception {
        JsonNode report = generate(tempDir);

        assertThat(finding(report, "SEGDL2-R-001").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL2-R-002").path("status").asText()).isEqualTo("PASS");
        assertThat(finding(report, "SEGDL2-R-003").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL2-R-004").path("status").asText()).isEqualTo("NOT_ASSERTABLE");
        assertThat(finding(report, "SEGDL2-R-005").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL2-R-006").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL2-R-007").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL2-R-008").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL2-R-009").path("status").asText()).isEqualTo("NOT_ASSERTABLE");
        assertThat(report.path("aiTestDataValidationSummary").path("fail").asInt()).isEqualTo(6);
        assertThat(report.path("aiTestDataValidationSummary").path("notAssertable").asInt()).isEqualTo(2);
        assertThat(report.path("aiTestDataValidationSummary").path("validForExecution").asBoolean()).isFalse();
    }

    @Test
    void rejectsWrongMarkerValuesAndMalformedSecondaryBlock() {
        ObjectNode aiData = mapper.createObjectNode();
        ObjectNode segment = aiData.putObject("Phone Load Response").putObject("Dial String Data Segment");
        segment.put("DataTypeIndicator", "!");
        segment.put("DialStringType", "1");
        segment.put("RedialCount", "2");
        segment.put("PhoneNumber", "5555550100");
        segment.put("DialStringTerminator", "A");
        segment.put("EndofDataIndicator", "X");
        ObjectNode secondary = segment.putObject("SecondaryPhoneNumberBlock");
        secondary.put("RedialCount", "4");
        secondary.put("PhoneNumber", "555PHONE");
        secondary.put("DialStringTerminator", "A");
        ObjectNode report = mapper.createObjectNode();

        generator.addAiDataValidation(report, aiData, mapper.createObjectNode());

        assertThat(finding(report, "SEGDL2-R-001").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL2-R-003").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL2-R-006").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL2-R-007").path("status").asText()).isEqualTo("FAIL");
    }

    @Test
    void rejectsNonBPauseIndicators() {
        ObjectNode aiData = mapper.createObjectNode();
        ObjectNode segment = aiData.putObject("Phone Load Response").putObject("Dial String Data Segment");
        segment.put("DataTypeIndicator", "!");
        segment.put("EndofDataIndicator", "~");
        segment.put("AccessCode", "1234");
        segment.put("PauseIndicator", "C");
        segment.put("DialStringTerminator", "A");
        ObjectNode report = mapper.createObjectNode();

        generator.addAiDataValidation(report, aiData, mapper.createObjectNode());

        assertThat(finding(report, "SEGDL2-R-005").path("status").asText()).isEqualTo("FAIL");
    }

    @Test
    void packageCoversEveryCatalogRuleWithLinkedCasesAndDataPairs() throws Exception {
        JsonNode pkg = mapper.readTree(Atl105Paths.testJson("segment-DL2-coverage-package.json").toFile());
        JsonNode catalog = mapper.readTree(Atl105Paths.ruleCatalog("DL2").toFile());

        assertThat(pkg.path("businessRequirements").size()).isEqualTo(9);
        assertThat(pkg.path("testScenarios").size()).isEqualTo(9);
        assertThat(pkg.path("testCases").size()).isEqualTo(29);
        assertThat(pkg.path("testDataRecords").size()).isEqualTo(29);
        assertUniqueIds(pkg.path("businessRequirements"));
        assertUniqueIds(pkg.path("testScenarios"));
        assertUniqueIds(pkg.path("testCases"));
        assertUniqueIds(pkg.path("testDataRecords"));
        Set<String> caseIds = new HashSet<>();
        Set<String> linkedDataCaseIds = new HashSet<>();
        for (JsonNode testCase : pkg.path("testCases")) caseIds.add(testCase.path("id").asText());
        for (JsonNode data : pkg.path("testDataRecords")) {
            String testCaseId = data.path("testCaseId").asText();
            assertThat(caseIds).contains(testCaseId);
            assertThat(linkedDataCaseIds.add(testCaseId)).as(testCaseId + " has one data pair").isTrue();
        }
        assertThat(linkedDataCaseIds).containsExactlyInAnyOrderElementsOf(caseIds);
        for (JsonNode rule : catalog.path("rules")) {
            String ruleId = rule.path("ruleId").asText();
            JsonNode br = find(pkg.path("businessRequirements"), "ruleId", ruleId);
            assertThat(br.isMissingNode()).as(ruleId + " BR mapping").isFalse();
            if (!br.path("provisionalItem").isMissingNode()) {
                JsonNode provisional = find(catalog.path("provisionalItems"), "id",
                        br.path("provisionalItem").asText());
                assertThat(provisional.path("status").asText()).isEqualTo("OPEN");
                assertThat(br.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
            }
            String brId = br.path("id").asText();
            JsonNode scenario = find(pkg.path("testScenarios"), "requirementIds", brId);
            assertThat(scenario.isMissingNode()).as(ruleId + " scenario mapping").isFalse();
            String scenarioId = scenario.path("id").asText();
            boolean hasPositive = false;
            boolean hasNegative = false;
            for (JsonNode testCase : pkg.path("testCases")) {
                if (!brId.equals(testCase.path("requirementId").asText())
                        || !scenarioId.equals(testCase.path("scenarioId").asText())) continue;
                hasPositive |= testCase.path("expectedOutcome").asText().startsWith("PASS");
                hasNegative |= testCase.path("expectedOutcome").asText().startsWith("FAIL");
                assertThat(find(pkg.path("testDataRecords"), "testCaseId", testCase.path("id").asText())
                        .isMissingNode()).as(testCase.path("id").asText() + " test-data pair").isFalse();
            }
            assertThat(hasPositive).as(ruleId + " positive candidate").isTrue();
            assertThat(hasNegative).as(ruleId + " negative candidate").isTrue();
        }
    }

    @Test
    void generatesConsolidatedReportAndValidatesFieldAliasCrosswalk(@TempDir Path tempDir) throws Exception {
        JsonNode report = generate(tempDir);
        Path dataFile = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg",
                "test_data", "TC-000002.json"));
        String phone = mapper.readTree(dataFile.toFile()).findValue("PhoneNumber").asText();
        Path consolidated = tempDir.resolve("SEGMENT-DL2-CONSOLIDATED-REPORT.txt");
        String consolidatedText = Files.readString(consolidated);

        assertThat(report.path("fieldAliasCrosswalkPath").asText())
                .isEqualTo("specifications/ATL105/contract/segment-DL2-field-alias-crosswalk.json");
        assertThat(report.path("aiFieldAliasCrosswalk").size()).isEqualTo(6);
        assertThat(report.path("aiFieldAliasCrosswalkGovernance").asText())
                .contains("Comparison-only vocabulary crosswalk");
        assertThat(consolidatedText).contains("SEGMENT DL2 CONSOLIDATED TEST SOLUTION READINESS");
        assertThat(consolidatedText).contains("Candidate BR-TS-TC-TD chains: 9/9");
        assertThat(consolidatedText).contains("Approved data pairs / executed cases / certified rules: 0 / 0 / 0");
        assertThat(consolidatedText).contains("Independent sample validation: 1 PASS, 6 FAIL, 0 REVIEW_REQUIRED, 2 NOT_ASSERTABLE");
        assertThat(mapper.writeValueAsString(report)).doesNotContain(phone);
        assertThat(consolidatedText).doesNotContain(phone);
    }

    @Test
    void correctedCandidateFixesKnownLogicalDefectsWithoutGuessingOpenRules() throws Exception {
        JsonNode candidate = mapper.readTree(
                Atl105Paths.testJson("segment-DL2-ai-corrected-candidate.json").toFile());
        JsonNode response = candidate.path("Phone Load Response");
        JsonNode dl2 = response.path("dialStringDataSegment");
        JsonNode primary = dl2.path("primary");
        JsonNode secondary = dl2.path("secondary");

        assertThat(candidate.path("_meta").path("status").asText())
                .isEqualTo("CANDIDATE_NOT_APPROVED_OR_EXECUTED");
        assertThat(candidate.path("context").path("merchantProfileLoadFlag").asText()).isEqualTo("PHON");
        assertThat(response.path("framingStatus").asText()).isEqualTo("REVIEW_REQUIRED_SEGDL2_SME_003");
        assertThat(dl2.path("dataTypeIndicator").asText()).isEqualTo("!");
        assertThat(dl2.path("endOfDataIndicator").asText()).isEqualTo("~");
        assertThat(primary.path("terminator").asText()).isEqualTo("A");
        assertThat(secondary.path("terminator").asText()).isEqualTo("F");
        assertThat(primary.path("accessCode").isNull()).isTrue();
        assertThat(primary.path("pauseIndicator").isNull()).isTrue();
        assertThat(secondary.path("accessCode").isNull()).isTrue();
        assertThat(secondary.path("pauseIndicator").isNull()).isTrue();
        assertThat(response.path("representation").asText()).contains("not serialized");
    }

    @Test
    void failsExplicitlyWhenRequiredInputIsMissing(@TempDir Path tempDir) {
        assertThatThrownBy(() -> generator.generate(tempDir, Path.of("missing-package.json"),
                Path.of("missing-catalog.json"), Path.of("missing-ai-catalog.json"),
                Path.of("missing-crosswalk.json"), Path.of("missing-candidate.json"),
                tempDir.resolve("report.json"), tempDir.resolve("report.md"),
                tempDir.resolve("readiness.txt")))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Required DL2 coverage input is missing");
    }

    private JsonNode generate(Path tempDir) throws Exception {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        Path reportJson = tempDir.resolve("segment-DL2-report.json");
        Path reportMarkdown = tempDir.resolve("segment-DL2-report.md");
        Path consolidated = tempDir.resolve("SEGMENT-DL2-CONSOLIDATED-REPORT.txt");
        Path suppliedAiCatalog = Atl105Paths.testOutput().resolve(Path.of(
                "ai-artifacts", "coverage-reports", "supplied-ai-catalog", "supplied-ai-catalog-coverage.json"));
        Path crosswalk = Atl105Paths.root().resolve(Path.of(
                "contract", "segment-DL2-field-alias-crosswalk.json"));
        JsonNode report = generator.generate(runRoot, Atl105Paths.testJson("segment-DL2-coverage-package.json"),
                Atl105Paths.ruleCatalog("DL2"), suppliedAiCatalog, crosswalk,
                Atl105Paths.testJson("segment-DL2-ai-corrected-candidate.json"),
                reportJson, reportMarkdown, consolidated);

        assertThat(Files.isRegularFile(reportJson)).isTrue();
        assertThat(Files.isRegularFile(reportMarkdown)).isTrue();
        assertThat(Files.isRegularFile(consolidated)).isTrue();
        assertThat(mapper.readTree(reportJson.toFile()).path("artifact").asText())
                .isEqualTo("segment-dl2-ai-artifact-coverage");
        return report;
    }

    private static JsonNode finding(JsonNode report, String ruleId) {
        for (JsonNode row : report.path("aiTestDataRuleValidation")) {
            if (ruleId.equals(row.path("ruleId").asText())) return row;
        }
        return com.fasterxml.jackson.databind.node.MissingNode.getInstance();
    }

    private static JsonNode find(JsonNode rows, String field, String value) {
        for (JsonNode row : rows) {
            JsonNode candidate = row.path(field);
            if (candidate.isArray()) {
                for (JsonNode item : candidate) if (value.equals(item.asText())) return row;
            } else if (value.equals(candidate.asText())) return row;
        }
        return com.fasterxml.jackson.databind.node.MissingNode.getInstance();
    }

    private static void assertUniqueIds(JsonNode rows) {
        Set<String> ids = new HashSet<>();
        for (JsonNode row : rows) {
            assertThat(ids.add(row.path("id").asText())).isTrue();
        }
    }
}
