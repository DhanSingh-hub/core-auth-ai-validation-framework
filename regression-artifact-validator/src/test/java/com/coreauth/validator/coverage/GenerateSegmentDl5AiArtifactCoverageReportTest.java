package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateSegmentDl5AiArtifactCoverageReportTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void generatesIndependentDl5CoverageWithoutDisclosingSensitiveValues(@TempDir Path tempDir) throws IOException {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        Path dataFile = runRoot.resolve(Path.of("test_data", "TC-000005.json"));
        JsonNode payload = mapper.readTree(dataFile.toFile());
        String phone = payload.findValue("SoftwareLoadPhoneNumber").asText();
        String password = payload.findValue("Password").asText();
        Path reportJson = tempDir.resolve("coverage.json");
        Path reportMarkdown = tempDir.resolve("coverage.md");
        Path consolidated = tempDir.resolve("readiness.txt");
        Path aiCatalog = Atl105Paths.testOutput().resolve(Path.of(
                "ai-artifacts", "coverage-reports", "supplied-ai-catalog", "supplied-ai-catalog-coverage.json"));
        Path crosswalk = Atl105Paths.root().resolve(Path.of(
                "contract", "segment-DL5-field-alias-crosswalk.json"));

        JsonNode report = new GenerateSegmentDl5AiArtifactCoverageReport().generate(
                runRoot,
                Atl105Paths.testJson("segment-DL5-coverage-package.json"),
                Atl105Paths.ruleCatalog("DL5"),
                aiCatalog,
                crosswalk,
                reportJson,
                reportMarkdown,
                consolidated);

        assertThat(report.path("artifactInventory").path("testSolutionRules").asInt()).isEqualTo(7);
        assertThat(report.path("artifactInventory").path("testSolutionScenarios").asInt()).isEqualTo(7);
        assertThat(report.path("artifactInventory").path("testSolutionTestCases").asInt()).isEqualTo(14);
        assertThat(report.path("artifactInventory").path("testSolutionTestDataRecords").asInt()).isEqualTo(14);
        assertThat(report.path("artifactInventory").path("suppliedAiCatalogDl5Requirements").asInt()).isEqualTo(10);
        assertThat(report.path("testSolutionMetrics").path("rulesWithPositiveAndNegativeCandidates").asInt())
                .isEqualTo(7);
        assertThat(report.path("aiCoverageOfTestSolutionOracle").path("confirmedSemanticMatches").asInt())
                .isZero();
        assertThat(report.path("aiTestDataValidationSummary").path("validForExecution").asBoolean()).isFalse();
        assertThat(report.path("aiTestDataValidationSummary").path("sensitiveValueIncluded").asBoolean()).isFalse();
        assertThat(report.path("aiTestDataRuleValidation").get(1).path("status").asText())
                .isEqualTo("REVIEW_REQUIRED");
        assertThat(mapper.writeValueAsString(report)).doesNotContain(phone, password);
        assertThat(java.nio.file.Files.readString(reportMarkdown)).doesNotContain(phone, password);
        assertThat(java.nio.file.Files.readString(consolidated)).doesNotContain(phone, password);
    }

    @Test
    void reportsInvalidLogicalFieldsWithoutEchoingPayloadValues() throws IOException {
        ObjectNode sample = mapper.createObjectNode();
        ObjectNode dl5 = sample.putObject("Software IP Load Data Segment");
        dl5.put("DataTypeIndicator", "$");
        dl5.put("NewSoftwareVersion", "BAD");
        dl5.put("SoftwareTerminalRecordID", "SHORT");
        dl5.put("SoftwareLoadIPURLAddress", "synthetic-secret-value");
        dl5.put("SoftwareLoadRequestDate", "notdate");
        dl5.put("SoftwareLoadRequestTime", "noon");
        dl5.put("SoftwareLoadType", "X");
        dl5.put("EndofDataIndicator", "!");
        ObjectNode report = mapper.createObjectNode();

        new GenerateSegmentDl5AiArtifactCoverageReport()
                .addAiDataValidation(report, sample, mapper.createObjectNode().put("messageFamily", "Wrong Family"));

        assertThat(report.path("aiTestDataValidationSummary").path("fail").asInt()).isEqualTo(3);
        assertThat(report.path("aiTestDataValidationSummary").path("reviewRequired").asInt()).isEqualTo(0);
        assertThat(report.path("aiTestDataValidationSummary").path("notAssertable").asInt()).isEqualTo(3);
        assertThat(mapper.writeValueAsString(report)).doesNotContain("synthetic-secret-value");
        assertThat(report.path("aiTestDataValidationSummary").path("sensitiveValueIncluded").asBoolean()).isFalse();
    }
}
