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

class GenerateSegmentDl6AiArtifactCoverageReportTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void generatesIndependentDl6CoverageWithoutDisclosingSensitiveValues(@TempDir Path tempDir) throws IOException {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        JsonNode source = mapper.readTree(runRoot.resolve(Path.of("test_data", "TC-000006.json")).toFile());
        String phone = source.findValue("PhoneNumber").asText();
        String password = source.findValue("Password").asText();
        Path aiCatalog = Atl105Paths.testOutput().resolve(Path.of(
                "ai-artifacts", "coverage-reports", "supplied-ai-catalog", "supplied-ai-catalog-coverage.json"));
        Path crosswalk = Atl105Paths.root().resolve(Path.of(
                "contract", "segment-DL6-field-alias-crosswalk.json"));

        Path reportMarkdown = tempDir.resolve("coverage.md");
        Path consolidated = tempDir.resolve("readiness.txt");
        JsonNode report = new GenerateSegmentDl6AiArtifactCoverageReport().generate(
                runRoot,
                Atl105Paths.testJson("segment-DL6-coverage-package.json"),
                Atl105Paths.ruleCatalog("DL6"),
                aiCatalog,
                crosswalk,
                tempDir.resolve("coverage.json"),
                reportMarkdown,
                consolidated);

        assertThat(report.path("artifactInventory").path("testSolutionRules").asInt()).isEqualTo(7);
        assertThat(report.path("artifactInventory").path("testSolutionBusinessRequirements").asInt()).isEqualTo(7);
        assertThat(report.path("artifactInventory").path("testSolutionScenarios").asInt()).isEqualTo(7);
        assertThat(report.path("artifactInventory").path("testSolutionTestCases").asInt()).isEqualTo(14);
        assertThat(report.path("artifactInventory").path("testSolutionTestDataRecords").asInt()).isEqualTo(14);
        assertThat(report.path("artifactInventory").path("suppliedAiCatalogDl6Requirements").asInt()).isZero();
        assertThat(report.path("testSolutionMetrics").path("rulesWithPositiveAndNegativeCandidates").asInt()).isEqualTo(7);
        assertThat(report.path("aiCoverageOfTestSolutionOracle").path("confirmedSemanticMatches").asInt()).isZero();
        assertThat(report.path("aiTestDataValidationSummary").path("pass").asInt()).isEqualTo(1);
        assertThat(report.path("aiTestDataValidationSummary").path("fail").asInt()).isEqualTo(1);
        assertThat(report.path("aiTestDataValidationSummary").path("reviewRequired").asInt()).isEqualTo(2);
        assertThat(report.path("aiTestDataValidationSummary").path("notAssertable").asInt()).isEqualTo(3);
        assertThat(report.path("aiTestDataValidationSummary").path("validForExecution").asBoolean()).isFalse();
        assertThat(report.path("aiTestDataValidationSummary").path("sensitiveValueIncluded").asBoolean()).isFalse();
        assertThat(mapper.writeValueAsString(report)).doesNotContain(phone, password, "273755727837925838", "123456");
        assertThat(java.nio.file.Files.readString(reportMarkdown)).doesNotContain(phone, password, "273755727837925838", "123456");
        assertThat(java.nio.file.Files.readString(consolidated)).doesNotContain(phone, password, "273755727837925838", "123456");
    }

    @Test
    void reportsInvalidDl6FieldsWithoutEchoingPayloadValues() throws IOException {
        ObjectNode sample = mapper.createObjectNode();
        ObjectNode dl6 = sample.putObject("Store and Forward Data Segment");
        dl6.put("DataTypeIndicator", "$");
        dl6.put("StartTime", "secret-url-http-example");
        dl6.put("EndTime", "2500");
        dl6.put("EndofDataIndicator", "!");
        ObjectNode report = mapper.createObjectNode();

        new GenerateSegmentDl6AiArtifactCoverageReport()
                .addAiDataValidation(report, sample, mapper.createObjectNode().put("messageFamily", "Wrong Family"));

        assertThat(report.path("aiTestDataValidationSummary").path("fail").asInt()).isEqualTo(3);
        assertThat(report.path("aiTestDataValidationSummary").path("reviewRequired").asInt()).isEqualTo(1);
        assertThat(report.path("aiTestDataValidationSummary").path("notAssertable").asInt()).isEqualTo(3);
        assertThat(mapper.writeValueAsString(report)).doesNotContain("secret-url-http-example");
        assertThat(report.path("aiTestDataValidationSummary").path("sensitiveValueIncluded").asBoolean()).isFalse();
    }
}
