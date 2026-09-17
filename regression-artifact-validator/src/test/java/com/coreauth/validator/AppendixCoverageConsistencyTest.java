package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixCoverageConsistency;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixCoverageConsistencyTest {
    @Test
    void allAppendixRecordsMatchTheCanonicalInventory() throws Exception {
        var report = new AppendixCoverageConsistency().validate(
                Path.of("test-output/test-json/knowledge/segment-100-canonical-appendix-inventory.json"),
                Path.of("test-output/test-json/appendices"));

        assertThat(report.appendixCount()).isEqualTo(31);
        assertThat(report.missingFiles()).isEmpty();
        assertThat(report.countMismatches()).isEmpty();
        assertThat(report.incompleteEvidence()).contains("appendix-e-segment-100-coverage.json does not embed testScenarios and testCases arrays");
        assertThat(report.invalidTestDataReadiness()).isEmpty();
        assertThat(report.danglingTestDataReferences()).isEmpty();
        assertThat(report.isConsistent()).isTrue();
        assertThat(report.statusCounts()).containsKeys("COVERED", "PARTIALLY_COVERED", "REVIEW_REQUIRED");
        assertThat(report.readinessCounts()).containsKeys("EXECUTABLE", "EXTERNAL_FIXTURE_REQUIRED");
        assertThat(report.readinessCounts().get("EXTERNAL_FIXTURE_REQUIRED")).isEqualTo(4);
    }

    @Test
    void rejectsInvalidReadinessAndDanglingTestDataReferences(@org.junit.jupiter.api.io.TempDir Path tempDir) throws Exception {
        Path evidenceDir = tempDir.resolve("appendices");
        Files.createDirectories(evidenceDir);
        Files.writeString(evidenceDir.resolve("appendix-z-segment-100-coverage.json"), """
                {
                  "businessRequirements": [{"id":"BR-1","status":"COVERED"}],
                  "testScenarios": [],
                  "testCases": [],
                  "testData": [
                    {"id":"TD-1","coversBr":"BR-1","readiness":"NOT_A_REAL_VALUE"},
                    {"id":"TD-2","coversBr":"BR-MISSING","readiness":"EXECUTABLE"}
                  ]
                }
                """);
        Files.writeString(tempDir.resolve("inventory.json"), """
                {"appendices": [{"id":"Z","brRecords":1,"sourceRecord":"appendix-z-segment-100-coverage.json"}]}
                """);

        var report = new AppendixCoverageConsistency().validate(tempDir.resolve("inventory.json"), evidenceDir);

        assertThat(report.invalidTestDataReadiness()).anyMatch(message -> message.contains("TD-1") && message.contains("NOT_A_REAL_VALUE"));
        assertThat(report.danglingTestDataReferences()).anyMatch(message -> message.contains("TD-2") && message.contains("BR-MISSING"));
        assertThat(report.isConsistent()).isFalse();
    }
}
