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

class GenerateSegmentDl4AiArtifactCoverageReportTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void generatesIndependentDl4CoverageWithoutDisclosingPhoneValue(@TempDir Path tempDir) throws IOException {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        Path dataFile = runRoot.resolve(Path.of("test_data", "TC-000004.json"));
        String phone = mapper.readTree(dataFile.toFile()).findValue("SoftwareLoadPhoneNumber").asText();
        Path reportJson = tempDir.resolve("coverage.json");
        Path reportMarkdown = tempDir.resolve("coverage.md");
        Path consolidated = tempDir.resolve("readiness.txt");
        Path aiCatalog = Atl105Paths.testOutput().resolve(Path.of(
                "ai-artifacts", "coverage-reports", "supplied-ai-catalog", "supplied-ai-catalog-coverage.json"));
        Path crosswalk = Atl105Paths.root().resolve(Path.of(
                "contract", "segment-DL4-field-alias-crosswalk.json"));

        JsonNode report = new GenerateSegmentDl4AiArtifactCoverageReport().generate(
                runRoot,
                Atl105Paths.testJson("segment-DL4-coverage-package.json"),
                Atl105Paths.ruleCatalog("DL4"),
                aiCatalog,
                crosswalk,
                reportJson,
                reportMarkdown,
                consolidated);

        assertThat(report.path("artifactInventory").path("testSolutionRules").asInt()).isEqualTo(8);
        assertThat(report.path("artifactInventory").path("testSolutionScenarios").asInt()).isEqualTo(8);
        assertThat(report.path("artifactInventory").path("testSolutionTestCases").asInt()).isEqualTo(18);
        assertThat(report.path("artifactInventory").path("testSolutionTestDataRecords").asInt()).isEqualTo(18);
        assertThat(report.path("artifactInventory").path("suppliedAiCatalogDl4Requirements").asInt()).isEqualTo(15);
        assertThat(report.path("testSolutionMetrics").path("rulesWithPositiveAndNegativeCandidates").asInt())
                .isEqualTo(8);
        assertThat(report.path("aiCoverageOfTestSolutionOracle").path("confirmedSemanticMatches").asInt())
                .isZero();
        assertThat(report.path("aiTestDataValidationSummary").path("validForExecution").asBoolean()).isFalse();
        assertThat(report.path("aiTestDataValidationSummary").path("phoneValueIncluded").asBoolean()).isFalse();
        assertThat(report.path("aiTestDataRuleValidation").get(1).path("status").asText())
                .isEqualTo("REVIEW_REQUIRED");
        assertThat(mapper.writeValueAsString(report)).doesNotContain(phone);
        assertThat(java.nio.file.Files.readString(reportMarkdown)).doesNotContain(phone);
        assertThat(java.nio.file.Files.readString(consolidated)).doesNotContain(phone);
    }

    @Test
    void reportsInvalidLogicalFieldsWithoutEchoingPayloadValues() throws IOException {
        ObjectNode sample = mapper.createObjectNode();
        ObjectNode dl4 = sample.putObject("Software Dial Load Data Segment");
        dl4.put("DataTypeIndicator", "$");
        dl4.put("NewSoftwareVersion", "BAD");
        dl4.put("SoftwareTerminalRecordID", "SHORT");
        dl4.put("SoftwareLoadPhoneNumber", "synthetic-secret-value");
        dl4.put("SoftwareLoadRequestDate", "notdate");
        dl4.put("SoftwareLoadRequestTime", "noon");
        dl4.put("SoftwareLoadType", "X");
        dl4.put("EndofDataIndicator", "!");
        ObjectNode report = mapper.createObjectNode();

        new GenerateSegmentDl4AiArtifactCoverageReport()
                .addAiDataValidation(report, sample, mapper.createObjectNode().put("messageFamily", "Wrong Family"));

        assertThat(report.path("aiTestDataValidationSummary").path("fail").asInt()).isEqualTo(3);
        assertThat(report.path("aiTestDataValidationSummary").path("reviewRequired").asInt()).isEqualTo(0);
        assertThat(report.path("aiTestDataValidationSummary").path("notAssertable").asInt()).isEqualTo(4);
        assertThat(mapper.writeValueAsString(report)).doesNotContain("synthetic-secret-value");
        assertThat(report.path("aiTestDataValidationSummary").path("phoneValueIncluded").asBoolean()).isFalse();
    }
}
