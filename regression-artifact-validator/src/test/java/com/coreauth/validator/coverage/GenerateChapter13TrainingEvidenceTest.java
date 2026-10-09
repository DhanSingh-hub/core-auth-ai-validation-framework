package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateChapter13TrainingEvidenceTest {
    @TempDir Path directory;

    @Test
    void persistsSourceReconciliationAndExecutedCanonicalProbesWithoutCertification() throws Exception {
        Path packageFile = directory.resolve("package.json");
        Path evidenceFile = directory.resolve("evidence.json");
        var report = new GenerateChapter13TrainingEvidence().generate(packageFile, evidenceFile);
        var mapper = new ObjectMapper();
        assertThat(mapper.readTree(evidenceFile.toFile())).isEqualTo(report);
        assertThat(report.path("summary").path("businessRequirements").asInt()).isEqualTo(12);
        assertThat(report.path("summary").path("scenarios").asInt()).isEqualTo(12);
        assertThat(report.path("summary").path("executedContextualProbes").asInt()).isEqualTo(110);
        assertThat(report.path("summary").path("executedBaselineProbes").asInt()).isEqualTo(440);
        assertThat(report.path("summary").path("sourceElementIdentities").asInt()).isEqualTo(231);
        assertThat(report.path("summary").path("sourceDefinitions").asInt()).isEqualTo(232);
        assertThat(report.path("summary").path("contextualProfiles").asInt()).isEqualTo(79);
        assertThat(report.path("sourceElementReconciliation"))
                .hasSize(report.path("summary").path("sourceElementIdentities").asInt());
        for (var execution : report.path("executions")) {
            assertThat(execution.path("actualTargetStatus")).isEqualTo(execution.path("expectedTargetStatus"));
            assertThat(execution.path("existingTestArtifactAnchorCandidates")).isNotEmpty();
            assertThat(execution.path("sourceLine").asInt()).isGreaterThan(19000);
            assertThat(execution.path("sourceAnchors")).hasSize(2);
        }
        for (var row : report.path("sourceElementReconciliation")) {
            assertThat(row.path("completeSemanticCoverage").asBoolean()).isFalse();
        }
        assertThat(report.path("completeChapterCoverage").asBoolean()).isFalse();
        assertThat(report.path("executionReady").asBoolean()).isFalse();
        var artifacts = mapper.readValue(packageFile.toFile(), CanonicalArtifactPackage.class);
        assertThat(new CanonicalTraceabilityValidator().validate(artifacts).isValid()).isTrue();
        assertThat(new CanonicalTraceabilityValidator().validateForExecution(artifacts).isValid()).isFalse();
        assertThat(report.path("summary").path("testCases").asInt()).isEqualTo(report.path("executions").size());
        assertThat(report.path("summary").path("testDataRecords").asInt()).isEqualTo(report.path("executions").size());
    }
}
