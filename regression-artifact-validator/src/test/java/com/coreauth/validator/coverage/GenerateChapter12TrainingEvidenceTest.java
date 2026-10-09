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
        assertThat(report.path("summary").path("businessRequirements").asInt()).isEqualTo(22);
        assertThat(report.path("summary").path("executedObservations").asInt()).isEqualTo(44);
        assertThat(report.path("summary").path("approvedRequirements").asInt()).isZero();
        assertThat(report.path("sourceSha256").asText()).hasSize(64);
        var loaded = new CanonicalPackageLoader().load(packageFile);
        assertThat(new CanonicalTraceabilityValidator().validate(loaded).errors()).isEmpty();
        assertThat(new CanonicalTraceabilityValidator().validateForExecution(loaded).errors()).isNotEmpty();
        var validator = new Chapter12SegmentPayloadValidator();
        for (var data : loaded.getTestData()) {
            assertThat(validator.validate(data.getPayload()).status().name()).isIn("INVALID", "REVIEW_REQUIRED", "CHECKS_PASSED");
        }
        assertThat(new ObjectMapper().readTree(reportFile.toFile())).isEqualTo(report);
    }
}
