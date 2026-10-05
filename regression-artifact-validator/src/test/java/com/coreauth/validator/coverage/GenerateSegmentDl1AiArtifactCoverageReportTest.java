package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GenerateSegmentDl1AiArtifactCoverageReportTest {
    private final GenerateSegmentDl1AiArtifactCoverageReport generator =
            new GenerateSegmentDl1AiArtifactCoverageReport();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void calculatesCandidateCoverageAndKeepsExecutionCoverageSeparate(@TempDir Path tempDir) throws Exception {
        JsonNode report = generate(tempDir);

        assertThat(report.path("aiArtifactInventory").path("dl1Requirements").asInt()).isEqualTo(1);
        assertThat(report.path("aiArtifactInventory").path("dl1Scenarios").asInt()).isEqualTo(1);
        assertThat(report.path("aiArtifactInventory").path("dl1TestCases").asInt()).isEqualTo(1);
        assertThat(report.path("aiChainIntegrity").path("traceabilityStatus").asText()).isEqualTo("COMPLETE");
        assertThat(report.path("aiChainIntegrity").path("sourceEvidenceConsistent").asBoolean()).isTrue();

        assertThat(report.path("testSolutionMetrics").path("oracleRuleCount").asInt()).isEqualTo(12);
        assertThat(report.path("testSolutionMetrics").path("rulesWithCompleteCandidateChains").asInt()).isEqualTo(12);
        assertThat(report.path("testSolutionMetrics").path("rulesWithPositiveAndNegativeCandidates").asInt()).isEqualTo(12);
        assertThat(report.path("testSolutionMetrics").path("candidateTestCases").asInt()).isEqualTo(27);
        assertThat(report.path("testSolutionMetrics").path("candidateRequestResponsePairs").asInt()).isEqualTo(27);
        assertThat(report.path("testSolutionMetrics").path("executionCoveragePercent").asDouble()).isZero();

        assertThat(report.path("aiRequirementCoverageMetrics").path("aiRequirementDenominator").asInt()).isEqualTo(1);
        assertThat(report.path("aiRequirementCoverageMetrics")
                .path("aiRequirementsWithPositiveAndNegativeCandidateCoverage").asInt()).isEqualTo(1);
        assertThat(report.path("aiRequirementCoverageMetrics").path("candidateCoveragePercent").asDouble()).isEqualTo(100.0);
        assertThat(report.path("aiCoverageOfTestSolutionOracle").path("rulesWithPartialAiEvidence").asInt()).isEqualTo(1);
        assertThat(report.path("aiCoverageOfTestSolutionOracle").path("rulesWithoutAiEvidence").asInt()).isEqualTo(11);
        assertThat(report.path("aiCoverageOfTestSolutionOracle").path("partialAiEvidencePercent").asDouble()).isEqualTo(8.3);
    }

    @Test
    void independentlyRejectsInvalidAiTestDataEvenWhenTheProducerMarksItsChainComplete(@TempDir Path tempDir)
            throws Exception {
        JsonNode report = generate(tempDir);

        assertThat(report.path("aiChainIntegrity").path("producerCompletenessStatus").asText()).isEqualTo("COMPLETE");
        assertThat(report.path("aiChainIntegrity").path("behaviorValidationStatus").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL1-R-002").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL1-R-004").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL1-R-005").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL1-R-007").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL1-R-010").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL1-R-011").path("status").asText()).isEqualTo("FAIL");
        assertThat(report.path("aiTestDataValidationSummary").path("validForExecution").asBoolean()).isFalse();
        assertThat(report.path("aiTestDataValidationSummary").path("fail").asInt()).isEqualTo(6);
        assertThat(report.path("aiTestDataValidationSummary").path("notAssertable").asInt()).isEqualTo(4);
        assertThat(report.path("aiTestDataValidationSummary").path("overallStatus").asText()).isEqualTo("FAIL");
    }

    @Test
    void failsExplicitlyWhenAnInputArtifactIsMissing(@TempDir Path tempDir) {
        assertThatThrownBy(() -> generator.generate(tempDir, Path.of("missing-package.json"),
                Path.of("missing-rule-catalog.json"), tempDir.resolve("report.json"), tempDir.resolve("report.md")))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Required DL1 coverage input is missing");
    }

    private JsonNode generate(Path tempDir) throws Exception {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        Path json = tempDir.resolve("segment-DL1-ai-artifact-coverage-report.json");
        Path markdown = tempDir.resolve("segment-DL1-ai-artifact-coverage-report.md");
        JsonNode report = generator.generate(runRoot, Atl105Paths.testJson("segment-DL1-coverage-package.json"),
                Atl105Paths.ruleCatalog("DL1"), json, markdown);

        assertThat(Files.isRegularFile(json)).isTrue();
        assertThat(Files.isRegularFile(markdown)).isTrue();
        assertThat(mapper.readTree(json.toFile()).path("artifact").asText())
                .isEqualTo("segment-dl1-ai-artifact-coverage");
        return report;
    }

    private static JsonNode finding(JsonNode report, String ruleId) {
        for (JsonNode finding : report.path("aiTestDataRuleValidation")) {
            if (ruleId.equals(finding.path("ruleId").asText())) return finding;
        }
        return com.fasterxml.jackson.databind.node.MissingNode.getInstance();
    }
}
