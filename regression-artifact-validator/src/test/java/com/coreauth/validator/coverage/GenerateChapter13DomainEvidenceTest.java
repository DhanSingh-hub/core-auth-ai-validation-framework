package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateChapter13DomainEvidenceTest {
    @TempDir Path directory;

    @Test void persistsEveryProbeAndCrosschecksExistingTestArtifactsWithoutClosingUnassessedRules() throws Exception {
        var mapper = new ObjectMapper();
        Path packageFile = directory.resolve("package.json"), evidenceFile = directory.resolve("evidence.json");
        var report = new GenerateChapter13DomainEvidence().generate(packageFile, evidenceFile);
        assertThat(mapper.readTree(evidenceFile.toFile())).isEqualTo(report);
        assertThat(report.path("summary").path("sourceElementIdentities").asInt()).isEqualTo(231);
        assertThat(report.path("summary").path("sourceDefinitions").asInt()).isEqualTo(232);
        assertThat(report.path("summary").path("scalarDomainExecutions").asInt()).isEqualTo(368);
        assertThat(report.path("summary").path("semanticExecutions").asInt()).isEqualTo(
                mapper.readTree(Atl105Paths.testJson("chapter-13-semantic-probes.json").toFile()).path("probes").size());
        assertThat(report.path("summary").path("executions").asInt()).isEqualTo(report.path("executions").size());
        assertThat(report.path("summary").path("testCases").asInt()).isEqualTo(report.path("executions").size());
        assertThat(report.path("summary").path("testDataRecords").asInt()).isEqualTo(report.path("executions").size());
        var ids = new HashSet<String>();
        for (var execution : report.path("executions")) {
            assertThat(ids.add(execution.path("testCaseId").asText())).isTrue();
            assertThat(execution.path("actualTargetStatus")).isEqualTo(execution.path("expectedTargetStatus"));
            assertThat(execution.path("sourceLine").asInt()).isGreaterThan(19000);
            assertThat(execution.path("existingCandidateStatus").asText()).isIn("MISSING", "REVIEW_REQUIRED");
            assertThat(execution.path("sourceAnchors")).hasSize(1);
        }
        assertThat(report.path("sourceElementReconciliation")).hasSize(231);
        assertThat(report.path("completeChapterCoverage").asBoolean()).isFalse();
        assertThat(report.path("executionReady").asBoolean()).isFalse();
        assertThat(report.path("summary").path("fullSemanticElementClosures").asInt()).isZero();
        var packageData = mapper.readValue(packageFile.toFile(), CanonicalArtifactPackage.class);
        var validator = new CanonicalTraceabilityValidator();
        assertThat(validator.validate(packageData).isValid()).isTrue();
        assertThat(validator.validateForExecution(packageData).isValid()).isFalse();
    }
}
