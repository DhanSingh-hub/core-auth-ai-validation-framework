package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100KnowledgeConsistencyChecker;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100KnowledgeConsistencyCheckerTest {
    @Test
    void currentKnowledgeArtifactsHaveNoStructuralConflicts() throws Exception {
        var report = new Segment100KnowledgeConsistencyChecker().check(
                Path.of("specifications/ATL105/test-output/test-json/knowledge/segment-100-br-baseline-index.json"),
                Path.of("specifications/ATL105/test-output/test-json/knowledge/segment-100-context-model.json"),
                Path.of("specifications/ATL105/test-output/test-json"),
                Path.of("specifications/ATL105/docs/specs/kb/segment-100/coverage/segment-100-rule-catalog.json"));

        assertThat(report.duplicateIds()).isEmpty();
        assertThat(report.duplicateRecords()).isEmpty();
        assertThat(report.invalidCategories()).isEmpty();
        assertThat(report.missingRecords()).isEmpty();
        assertThat(report.conflictingStatuses()).isEmpty();
        assertThat(report.isConsistent()).isTrue();
    }
}
