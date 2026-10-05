package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateSegmentDl8AiArtifactCoverageReportTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void generatesIndependentDl8CoverageWithoutDisclosingSensitiveValues(@TempDir Path tempDir) throws IOException {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        Path reportJson = tempDir.resolve("coverage.json");
        Path reportMarkdown = tempDir.resolve("coverage.md");
        Path consolidated = tempDir.resolve("readiness.txt");
        Path aiCatalog = Atl105Paths.testOutput().resolve(Path.of(
                "ai-artifacts", "coverage-reports", "supplied-ai-catalog", "supplied-ai-catalog-coverage.json"));
        Path crosswalk = Atl105Paths.root().resolve(Path.of(
                "contract", "segment-DL8-field-alias-crosswalk.json"));

        JsonNode report = new GenerateSegmentDl8AiArtifactCoverageReport().generate(
                runRoot,
                Atl105Paths.testJson("segment-DL8-coverage-package.json"),
                Atl105Paths.ruleCatalog("DL8"),
                aiCatalog,
                crosswalk,
                reportJson,
                reportMarkdown,
                consolidated);

        assertThat(report.path("artifactInventory").path("testSolutionRules").asInt()).isEqualTo(5);
        assertThat(report.path("artifactInventory").path("testSolutionBusinessRequirements").asInt()).isEqualTo(5);
        assertThat(report.path("artifactInventory").path("testSolutionScenarios").asInt()).isEqualTo(5);
        assertThat(report.path("artifactInventory").path("testSolutionTestCases").asInt()).isEqualTo(10);
        assertThat(report.path("artifactInventory").path("testSolutionTestDataRecords").asInt()).isEqualTo(10);
        assertThat(report.path("artifactInventory").path("suppliedAiCatalogDl8Requirements").asInt()).isZero();
        assertThat(report.path("testSolutionMetrics").path("rulesWithPositiveAndNegativeCandidates").asInt())
                .isEqualTo(5);
        assertThat(report.path("aiCoverageOfTestSolutionOracle").path("confirmedSemanticMatches").asInt())
                .isZero();
        assertThat(report.path("phaseOneAiChain").path("chainIdentityStatus").asText())
                .isEqualTo("GAP_CHAIN_CONSISTENT");
        assertThat(report.path("aiFieldAliasCrosswalk")).isEmpty();
        assertThat(report.path("aiTestDataValidationSummary").path("validForExecution").asBoolean()).isFalse();
        assertThat(report.path("aiTestDataValidationSummary").path("notAssertable").asInt()).isEqualTo(5);
        assertThat(mapper.writeValueAsString(report))
                .doesNotContain("secret", "password", "555-1212", "123 Main");
        assertThat(Files.readString(reportMarkdown))
                .doesNotContain("secret", "password", "555-1212", "123 Main");
        assertThat(Files.readString(consolidated))
                .doesNotContain("secret", "password", "555-1212", "123 Main");
    }

    @Test
    void validatesDl8LogicalFieldsWithoutEchoingPayloadValues() throws IOException {
        ObjectNode sample = mapper.createObjectNode();
        sample.put("apiKey", "secret-key-value");
        sample.put("password", "secret-password-value");
        sample.put("phoneNumber", "555-1212");
        sample.put("address", "123 Main Street");
        ObjectNode dl8 = sample.putObject("EMV Terminal Floor Limits Data Segment");
        dl8.put("DataTypeIndicator", "%");
        dl8.put("SegmentLengthIndicator", "026");
        ArrayNode groups = dl8.putArray("FloorLimitData");
        ObjectNode group = groups.addObject();
        group.put("RID", "A000000003");
        group.put("StandInIndicator", "2");
        group.put("FloorLimit", "000000005000");
        group.put("BUYPASSRIDCardType", "020");
        ObjectNode report = mapper.createObjectNode();

        new GenerateSegmentDl8AiArtifactCoverageReport()
                .addAiDataValidation(report, sample, mapper.createObjectNode().put("messageFamily", "Table Load Response"));

        assertThat(report.path("aiTestDataValidationSummary").path("pass").asInt()).isEqualTo(2);
        assertThat(report.path("aiTestDataValidationSummary").path("fail").asInt()).isZero();
        assertThat(report.path("aiTestDataValidationSummary").path("reviewRequired").asInt()).isEqualTo(2);
        assertThat(report.path("aiTestDataValidationSummary").path("notAssertable").asInt()).isEqualTo(1);
        assertThat(mapper.writeValueAsString(report))
                .doesNotContain("secret-key-value", "secret-password-value", "555-1212",
                        "123 Main Street", "A000000003", "000000005000");
        assertThat(report.path("aiTestDataValidationSummary").path("sensitiveValueIncluded").asBoolean()).isFalse();
    }
}
