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

class GenerateSegmentDl7AiArtifactCoverageReportTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void generatesIndependentDl7CoverageWithoutSensitivePayloadDisclosure(@TempDir Path tempDir)
            throws IOException {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        Path reportJson = tempDir.resolve("coverage.json");
        Path reportMarkdown = tempDir.resolve("coverage.md");
        Path consolidated = tempDir.resolve("readiness.txt");
        Path aiCatalog = Atl105Paths.testOutput().resolve(Path.of(
                "ai-artifacts", "coverage-reports", "supplied-ai-catalog", "supplied-ai-catalog-coverage.json"));
        Path crosswalk = Atl105Paths.root().resolve(Path.of(
                "contract", "segment-DL7-field-alias-crosswalk.json"));

        JsonNode report = new GenerateSegmentDl7AiArtifactCoverageReport().generate(
                runRoot,
                Atl105Paths.testJson("segment-DL7-coverage-package.json"),
                Atl105Paths.ruleCatalog("DL7"),
                aiCatalog,
                crosswalk,
                reportJson,
                reportMarkdown,
                consolidated);

        assertThat(report.path("artifactInventory").path("testSolutionRules").asInt()).isEqualTo(6);
        assertThat(report.path("artifactInventory").path("testSolutionBusinessRequirements").asInt()).isEqualTo(6);
        assertThat(report.path("artifactInventory").path("testSolutionScenarios").asInt()).isEqualTo(6);
        assertThat(report.path("artifactInventory").path("testSolutionTestCases").asInt()).isEqualTo(12);
        assertThat(report.path("artifactInventory").path("testSolutionTestDataRecords").asInt()).isEqualTo(12);
        assertThat(report.path("artifactInventory").path("suppliedAiCatalogDl7Requirements").asInt()).isZero();
        assertThat(report.path("artifactInventory").path("phaseOneMatchingDl7MetadataFiles").asInt()).isZero();
        assertThat(report.path("testSolutionMetrics").path("rulesWithPositiveAndNegativeCandidates").asInt())
                .isEqualTo(6);
        assertThat(report.path("aiCoverageOfTestSolutionOracle").path("status").asText())
                .isEqualTo("CANDIDATE_MAPPING_REVIEW_REQUIRED");
        assertThat(report.path("aiCoverageOfTestSolutionOracle").path("confirmedSemanticMatches").asInt())
                .isZero();
        assertThat(report.path("aiFieldAliasCrosswalk").size()).isZero();
        assertThat(report.path("aiTestDataValidationSummary").path("notAssertable").asInt()).isEqualTo(6);
        assertThat(report.path("aiTestDataValidationSummary").path("validForExecution").asBoolean()).isFalse();
        assertThat(mapper.writeValueAsString(report))
                .doesNotContain("555-123-4567", "10.1.2.3", "https://", "password", "secret-key");
        assertThat(java.nio.file.Files.readString(reportMarkdown))
                .doesNotContain("555-123-4567", "10.1.2.3", "https://", "password", "secret-key");
        assertThat(java.nio.file.Files.readString(consolidated))
                .contains("PASS/FAIL/REVIEW/NOT_ASSERTABLE: 0/0/0/6")
                .doesNotContain("555-123-4567", "10.1.2.3", "https://", "password", "secret-key");
    }

    @Test
    void reportsDl7LogicalShapeWithoutEchoingSensitiveValues() throws IOException {
        ObjectNode sample = mapper.createObjectNode();
        ObjectNode message = sample.putObject("Synthetic Host Download");
        ObjectNode dl7 = message.putObject("Supplemental Terminal Data Segment");
        dl7.put("DataTypeIndicator", "$");
        dl7.put("SegmentLengthIndicator", "28");
        dl7.put("DownloadData", "001004eng password=secret-key phone=555-123-4567 url=https://example.test ip=10.1.2.3");
        dl7.put("EndofDataIndicator", "~");
        ObjectNode report = mapper.createObjectNode();

        new GenerateSegmentDl7AiArtifactCoverageReport()
                .addAiDataValidation(report, sample, mapper.createObjectNode());

        assertThat(report.path("aiTestDataValidationSummary").path("fail").asInt()).isEqualTo(4);
        assertThat(report.path("aiTestDataValidationSummary").path("notAssertable").asInt()).isEqualTo(1);
        assertThat(mapper.writeValueAsString(report))
                .doesNotContain("555-123-4567", "10.1.2.3", "https://example.test", "password=secret-key");
        assertThat(report.path("aiTestDataValidationSummary").path("sensitivePayloadValuesIncluded").asBoolean())
                .isFalse();
    }
}
