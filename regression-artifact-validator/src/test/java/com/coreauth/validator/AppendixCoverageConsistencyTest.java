package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixCoverageConsistency;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixCoverageConsistencyTest {
    @Test
    void allAppendixRecordsMatchTheCanonicalInventory() throws Exception {
        var report = new AppendixCoverageConsistency().validate(
                Path.of("test-output/test-json/knowledge/segment-100-canonical-appendix-inventory.json"),
                Path.of("test-output/test-json/appendices"));

        assertThat(report.appendixCount()).isEqualTo(22);
        assertThat(report.missingFiles()).isEmpty();
        assertThat(report.countMismatches()).isEmpty();
        assertThat(report.incompleteEvidence()).contains("appendix-e-segment-100-coverage.json does not embed testScenarios and testCases arrays");
        assertThat(report.isConsistent()).isTrue();
        assertThat(report.statusCounts()).containsKeys("COVERED", "PARTIALLY_COVERED", "REVIEW_REQUIRED");
    }
}
