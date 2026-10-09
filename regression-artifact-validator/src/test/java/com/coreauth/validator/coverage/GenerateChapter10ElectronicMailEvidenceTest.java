package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateChapter10ElectronicMailEvidenceTest {
    @TempDir Path directory;

    @Test
    void persistsEightRealExecutionsAndCrossCheckedCanonicalChains() throws Exception {
        Path packageFile = directory.resolve("package.json");
        Path reportFile = directory.resolve("evidence.json");
        var report = new GenerateChapter10ElectronicMailEvidence().generate(packageFile, reportFile);
        var mapper = new ObjectMapper();
        assertThat(mapper.readTree(reportFile.toFile())).isEqualTo(report);
        assertThat(report.path("summary").path("businessRequirements").asInt()).isEqualTo(2);
        assertThat(report.path("summary").path("executedObservations").asInt()).isEqualTo(8);
        for (var execution : report.path("executions")) {
            assertThat(execution.path("actualStatus")).isEqualTo(execution.path("expectedStatus"));
            assertThat(execution.path("existingTestArtifactAnchorCandidates")).isNotEmpty();
            if ("INVALID".equals(execution.path("actualStatus").asText())) {
                assertThat(execution.path("errors")).isNotEmpty();
                assertThat(execution.path("errors").get(0).path("reason").isTextual()).isTrue();
            } else {
                assertThat(execution.path("errors")).isEmpty();
                assertThat(execution.path("warnings")).isNotEmpty();
            }
        }
        var artifacts = mapper.readValue(packageFile.toFile(), CanonicalArtifactPackage.class);
        assertThat(new CanonicalTraceabilityValidator().validate(artifacts).isValid()).isTrue();
        assertThat(new CanonicalTraceabilityValidator().validateForExecution(artifacts).isValid()).isFalse();
    }
}
