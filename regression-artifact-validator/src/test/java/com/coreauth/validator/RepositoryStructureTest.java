package com.coreauth.validator;

import com.coreauth.validator.paths.Atl105Paths;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryStructureTest {

    @Test
    void atl105SpecificationPackContainsRequiredBoundaries() {
        assertThat(Atl105Paths.root()).isDirectory();
        assertThat(Atl105Paths.docs()).isDirectory();
        assertThat(Atl105Paths.schemas()).isDirectory();
        assertThat(Atl105Paths.testInput()).isDirectory();
        assertThat(Atl105Paths.testOutput()).isDirectory();
        assertThat(Atl105Paths.root().resolve("contract")).isDirectory();
        assertThat(Atl105Paths.root().resolve("reports")).isDirectory();
        assertThat(Atl105Paths.root().resolve("scripts")).isDirectory();
        assertThat(Atl105Paths.root().resolve("segment-profiles.json")).isRegularFile();
        assertThat(Atl105Paths.root().resolve("training-status.json")).isRegularFile();
    }

    @Test
    void generatedMirrorsAndDuplicateArtifactRootsAreAbsent() {
        assertThat(Path.of("bin")).doesNotExist();
        assertThat(Atl105Paths.testInput().resolve("test-input")).doesNotExist();
        assertThat(Atl105Paths.testOutput().resolve("test-output")).doesNotExist();
        assertThat(Path.of("SEGMENT-111-CONSOLIDATED-REPORT.txt")).doesNotExist();
    }

    @Test
    void producerNeutralContractFilesExistAtStableLocations() {
        assertThat(Path.of("contract", "vocabulary.json")).isRegularFile();
        assertThat(Path.of("contract", "canonical-anchor.schema.json")).isRegularFile();
        assertThat(Path.of("contract", "artifact-package.schema.json")).isRegularFile();
        assertThat(Atl105Paths.root().resolve(Path.of("contract", "semantic-aliases.json"))).isRegularFile();
    }
}
