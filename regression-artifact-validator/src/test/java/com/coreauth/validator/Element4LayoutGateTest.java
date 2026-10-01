package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105Element4LayoutGate;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Element4LayoutGateTest {
    @TempDir Path pack;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void backsOnlyStructuralLayoutAndKeepsDraftsReviewOnly() throws Exception {
        copyInputs();
        GenerateAtl105Element4LayoutGate.main(new String[]{pack.toString()});
        JsonNode report = report();
        assertThat(report.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(1);
        assertThat(report.path("assertions")).hasSize(5);
        assertThat(report.path("assertions").get(4).path("sourceLine").asInt()).isGreaterThan(19587);
        assertThat(report.path("draftCases")).hasSize(2);
        assertThat(report.path("draftCases").get(0).path("value").asText()).hasSize(21);
        assertThat(report.path("draftCases").get(0).path("executionReady").asBoolean()).isFalse();
        assertThat(report.path("summary").path("smeApprovedRuleCount").asInt()).isZero();
    }

    @Test
    void missingPositionRowBlocksAllDrafts() throws Exception {
        copyInputs();
        Path source = pack.resolve("docs/specs/extracted_text.txt");
        String specification = Files.readString(source);
        int start = specification.indexOf("Number: 4 Name: Address Line 2");
        int end = specification.indexOf("Number: 5 Name: Approval Number", start);
        String definition = specification.substring(start, end);
        assertThat(definition).contains(" 16 Space");
        Files.writeString(source, specification.substring(0, start) + definition.replace(" 16 Space", " 16 Letter") + specification.substring(end));
        GenerateAtl105Element4LayoutGate.main(new String[]{pack.toString()});
        assertThat(report().path("summary").path("sourceMismatchCount").asInt()).isEqualTo(1);
        assertThat(report().path("draftCases")).isEmpty();
    }

    @Test
    void changedShapeBlocksAllDrafts() throws Exception {
        copyInputs();
        Path source = pack.resolve("docs/specs/extracted_text.txt");
        String specification = Files.readString(source);
        int start = specification.indexOf("Number: 4 Name: Address Line 2");
        int end = specification.indexOf("Number: 5 Name: Approval Number", start);
        String definition = specification.substring(start, end);
        assertThat(definition).contains("Maximum Length: 21 bytes");
        Files.writeString(source, specification.substring(0, start)
            + definition.replace("Maximum Length: 21 bytes", "Maximum Length: 22 bytes") + specification.substring(end));
        GenerateAtl105Element4LayoutGate.main(new String[]{pack.toString()});
        assertThat(report().path("summary").path("partiallyBackedRuleCount").asInt()).isZero();
        assertThat(report().path("draftCases")).isEmpty();
    }

    private void copyInputs() throws Exception {
        for (String name : new String[]{"contract/element-4-layout-assertions.json", "docs/specs/extracted_text.txt",
            "test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json",
            "test-output/test-solution-independent-review/atl105-br-approval-matrix.json"}) {
            Path target = pack.resolve(name);
            Files.createDirectories(target.getParent());
            Files.copy(Atl105Paths.root().resolve(name), target);
        }
    }

    private JsonNode report() throws Exception {
        return mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-element-4-layout-gate.json").toFile());
    }
}