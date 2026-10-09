package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalPackageLoader;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateChapter12TrainingEvidenceTest {
    @Test
    void persistsRealExecutionsAndSourceContinuousChainsWithoutClaimingWholeSpecCoverage(@TempDir Path temp) throws Exception {
        Path packageFile = temp.resolve("package.json");
        Path reportFile = temp.resolve("report.json");
        var report = new GenerateChapter12TrainingEvidence().generate(packageFile, reportFile);
        assertThat(report.path("completeSpecCoverage").asBoolean()).isFalse();
        assertThat(report.path("fullMessageIntakeWired").asBoolean()).isFalse();
        assertThat(report.path("summary").path("businessRequirements").asInt()).isEqualTo(42);
        assertThat(report.path("summary").path("executedObservations").asInt()).isEqualTo(66);
        assertThat(report.path("summary").path("approvedRequirements").asInt()).isZero();
        assertThat(report.path("sourceSha256").asText()).hasSize(64);
        assertThat(report.path("executions")).anySatisfy(execution -> {
            assertThat(execution.path("ruleId").asText()).isEqualTo("SEG119-R-008");
            assertThat(execution.path("existingTestArtifactAnchorCandidates")).isNotEmpty();
            assertThat(execution.path("candidateMatchStatus").asText()).isEqualTo("REVIEW_REQUIRED");
        });
        assertThat(report.path("executions")).anySatisfy(execution -> {
            assertThat(execution.path("ruleId").asText()).isEqualTo("SEG115-R-003");
            assertThat(execution.path("existingTestArtifactAnchorCandidates")).isNotEmpty();
            assertThat(execution.path("candidateMatchStatus").asText()).isEqualTo("REVIEW_REQUIRED");
        });
        var loaded = new CanonicalPackageLoader().load(packageFile);
        assertThat(new CanonicalTraceabilityValidator().validate(loaded).errors()).isEmpty();
        assertThat(new CanonicalTraceabilityValidator().validateForExecution(loaded).errors()).isNotEmpty();
        var validator = new Chapter12SegmentPayloadValidator();
        for (var data : loaded.getTestData()) {
            assertThat(validator.validate(data.getPayload()).status().name()).isIn("INVALID", "REVIEW_REQUIRED", "CHECKS_PASSED");
        }
        int predicates = 0;
        for (var execution : report.path("executions")) {
            if (execution.has("expectedTargetStatus")) {
                predicates++;
                assertThat(execution.path("actualTargetStatus")).isEqualTo(execution.path("expectedTargetStatus"));
            }
        }
        assertThat(predicates).isEqualTo(18);
        assertThat(new ObjectMapper().readTree(reportFile.toFile())).isEqualTo(report);
    }
}
