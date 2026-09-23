package com.coreauth.validator;

import com.coreauth.validator.paths.Atl105Paths;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Atl105PathsTest {

    @Test
    void resolvesSpecificationPackLocationsConsistently() {
        assertThat(Atl105Paths.root()).isEqualTo(Path.of("specifications", "ATL105"));
        assertThat(Atl105Paths.docs()).isEqualTo(Path.of("specifications", "ATL105", "docs"));
        assertThat(Atl105Paths.schemas()).isEqualTo(Path.of("specifications", "ATL105", "schemas"));
        assertThat(Atl105Paths.testInput()).isEqualTo(Path.of("specifications", "ATL105", "test-input"));
        assertThat(Atl105Paths.testOutput()).isEqualTo(Path.of("specifications", "ATL105", "test-output"));
    }

    @Test
    void resolvesSegmentOwnedArtifacts() {
        assertThat(Atl105Paths.ruleCatalog("103")).isEqualTo(Path.of("specifications", "ATL105", "docs",
                "specs", "kb", "segment-103", "coverage", "segment-103-rule-catalog.json"));
        assertThat(Atl105Paths.aiTestData("DL1")).isEqualTo(Path.of("specifications", "ATL105", "test-input",
                "ai-solution", "test-data", "segment-dl1"));
        assertThat(Atl105Paths.consolidatedReport("111")).isEqualTo(Path.of("specifications", "ATL105",
                "test-output", "consolidated-reports", "SEGMENT-111-CONSOLIDATED-REPORT.txt"));
    }

    @Test
    void rejectsBlankSegmentIdentifiers() {
        assertThatThrownBy(() -> Atl105Paths.ruleCatalog(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("segment is required");
    }
}
