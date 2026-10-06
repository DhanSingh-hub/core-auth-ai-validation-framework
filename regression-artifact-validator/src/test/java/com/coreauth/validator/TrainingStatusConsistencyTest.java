package com.coreauth.validator;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TrainingStatusConsistencyTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void trainingCountsAndCurrentReportAgreeWithCatalogs() throws Exception {
        JsonNode status = mapper.readTree(Atl105Paths.root().resolve("training-status.json").toFile());
        JsonNode segments = status.path("segments");
        int totalRules = 0;
        Set<String> catalogSegments = new HashSet<>();
        Path kb = Atl105Paths.docs().resolve("specs/kb");
        try (Stream<Path> files = Files.walk(kb)) {
            for (Path catalog : files.filter(path -> path.getFileName().toString().matches("segment-.*-rule-catalog\\.json")).toList()) {
                String segment = catalog.getParent().getParent().getFileName().toString().substring("segment-".length()).toUpperCase();
                JsonNode rules = mapper.readTree(catalog.toFile()).path("rules");
                assertThat(segments.has(segment)).as("training status for %s", segment).isTrue();
                assertThat(segments.path(segment).path("ruleCount").asInt()).as("rule count for %s", segment).isEqualTo(rules.size());
                assertThat(catalogSegments.add(segment)).as("duplicate catalog for %s", segment).isTrue();
                totalRules += rules.size();
            }
        }
        assertThat(catalogSegments).hasSize(segments.size());
        assertThat(status.path("summary").path("totalSegments").asInt()).isEqualTo(segments.size());
        assertThat(status.path("summary").path("totalCataloguedRules").asInt()).isEqualTo(totalRules);
        String report = Files.readString(kb.resolve("SEGMENT-TRAINING-EXECUTION-REPORT.md"));
        assertThat(report).contains("| Catalogued rules | " + totalRules + " |");
        assertThat(report).contains("| Segments | " + segments.size() + " |");
        assertThat(report).contains("| Trained for independent intake | " + status.path("summary").path("trainedForIntake").asInt() + " |");
        assertThat(report).contains("[training-status.json](../../../training-status.json)");
        assertThat(report).doesNotContain("Total rules cataloged across all 48 segments: 484");
    }
}