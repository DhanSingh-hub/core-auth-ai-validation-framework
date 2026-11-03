package com.coreauth.validator;

import com.coreauth.validator.paths.Atl105Paths;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

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
        assertThat(Path.of("SEGMENT-103-CONSOLIDATED-REPORT.txt")).doesNotExist();
        assertThat(Atl105Paths.root().resolve("reports/legacy/module-root/SEGMENT-103-CONSOLIDATED-REPORT.txt"))
            .isRegularFile();
    }

    @Test
    void producerNeutralContractFilesExistAtStableLocations() {
        assertThat(Path.of("contract", "vocabulary.json")).isRegularFile();
        assertThat(Path.of("contract", "canonical-anchor.schema.json")).isRegularFile();
        assertThat(Path.of("contract", "artifact-package.schema.json")).isRegularFile();
        assertThat(Atl105Paths.root().resolve(Path.of("contract", "semantic-aliases.json"))).isRegularFile();
    }

    @Test
    void taskManifestAndPowerShellUtilitiesUseCanonicalLocations() throws Exception {
        assertThat(Path.of("framework.ps1")).isRegularFile();
        var manifest = new com.fasterxml.jackson.databind.ObjectMapper().readTree(Path.of("framework-tasks.json").toFile());
        assertThat(manifest.path("schemaVersion").asInt()).isEqualTo(1);
        Set<String> names = new HashSet<>();
        for (var task : manifest.path("tasks")) {
            assertThat(names.add(task.path("name").asText())).as("unique task name").isTrue();
            assertThat(task.path("arguments").isArray()).isTrue();
            if (task.has("script")) {
                Path script = Path.of(task.path("script").asText()).normalize();
                assertThat(script.isAbsolute()).isFalse();
                assertThat(script.startsWith("scripts")).isTrue();
                assertThat(script).isRegularFile();
            }
        }
        assertThat(names).contains("test", "structure", "training-ae", "semantic-self-test", "semantic-view-check");
        for (String scriptName : new String[]{"export-atl105-element-reference-to-excel.ps1", "report-all-segments-br-ratio.ps1"}) {
            Path script = Path.of("scripts/powershell", scriptName);
            assertThat(script).isRegularFile();
            assertThat(Path.of("scripts", scriptName)).doesNotExist();
            assertThat(Files.readString(script)).contains("..\\..\\specifications\\ATL105");
        }
    }
}
