package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105Element84WidthGate;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Element84WidthGateTest {
    @TempDir Path pack;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void draftsOnlySourceBackedWidths() throws Exception {
        copyInputs();
        GenerateAtl105Element84WidthGate.main(new String[]{pack.toString()});
        JsonNode result = report();
        assertThat(result.path("shapeVerified").asBoolean()).isTrue();
        assertThat(result.path("exceptionListVerified").asBoolean()).isTrue();
        assertThat(result.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(11);
        assertThat(result.path("summary").path("draftCaseCount").asInt()).isEqualTo(22);
        assertThat(result.path("summary").path("smeApprovedRuleCount").asInt()).isZero();
        assertThat(result.path("assertions").get(0).path("sourceLine").asInt()).isGreaterThan(14000);
        assertThat(result.path("draftCases").get(0).path("executionReady").asBoolean()).isFalse();
        assertThat(result.path("draftCases").get(0).path("expectedOutcome").asText()).isEqualTo("VALID_WIDTH_ONLY");
    }

    @Test
    void missingLayoutRowBlocksOnlyThatSegment() throws Exception {
        copyInputs();
        Path spec = pack.resolve("docs/specs/extracted_text.txt");
        Files.writeString(spec, Files.readString(spec).replace("2 84 Segment Length 3 R", "2 84 Segment Length unknown R"));
        GenerateAtl105Element84WidthGate.main(new String[]{pack.toString()});
        JsonNode result = report();
        assertThat(result.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(10);
        assertThat(result.path("summary").path("draftCaseCount").asInt()).isEqualTo(20);
    }

    @Test
    void segment131LayoutMutationBlocksOnlyThatRule() throws Exception {
        copyInputs();
        Path spec = pack.resolve("docs/specs/extracted_text.txt");
        String source = Files.readString(spec);
        var responseHeading = Pattern.compile("(?m)^12\\.21 EMV Response Data Segment[ \\t]*\\r?$").matcher(source);
        assertThat(responseHeading.find()).isTrue();
        int start = responseHeading.start();
        var nextHeading = Pattern.compile("(?m)^12\\.22 CA Public Key File Segment[ \\t]*\\r?$").matcher(source);
        assertThat(nextHeading.find(start + 1)).isTrue();
        int end = nextHeading.start();
        assertThat(start).isPositive();
        assertThat(end).isGreaterThan(start);
        String section = source.substring(start, end);
        assertThat(section).contains("2 84 Segment Length 4 R");
        Files.writeString(spec, source.substring(0, start) + section.replace("2 84 Segment Length 4 R", "2 84 Segment Length unknown R") + source.substring(end));
        GenerateAtl105Element84WidthGate.main(new String[]{pack.toString()});
        assertThat(report().path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(10);
        assertThat(report().path("summary").path("draftCaseCount").asInt()).isEqualTo(20);
    }

    @Test
    void changedExceptionListBlocksBothSegments() throws Exception {
        copyInputs();
        Path spec = pack.resolve("docs/specs/extracted_text.txt");
        String source = Files.readString(spec);
        int start = source.indexOf("Number: 84 Name: Segment Length");
        int end = source.indexOf("Purpose:", start);
        String list = source.substring(start, end);
        assertThat(list).contains("(No.130)");
        Files.writeString(spec, source.substring(0, start) + list.replace("(No.130)", "(No.132)") + source.substring(end));
        GenerateAtl105Element84WidthGate.main(new String[]{pack.toString()});
        JsonNode result = report();
        assertThat(result.path("exceptionListVerified").asBoolean()).isFalse();
        assertThat(result.path("summary").path("draftCaseCount").asInt()).isZero();
    }

    @Test
    void conflictingProcessingRulesListBlocksBothSegments() throws Exception {
        copyInputs();
        Path spec = pack.resolve("docs/specs/extracted_text.txt");
        String source = Files.readString(spec);
        var restriction = Pattern.compile("It cannot have\\s+a length of 4 digits when sending any other segment\\.")
            .matcher(source);
        assertThat(restriction.find(source.indexOf("Number: 84 Name: Segment Length"))).isTrue();
        int start = restriction.end();
        int end = source.indexOf("Valid Codes/Values:", start);
        String processing = source.substring(start, end);
        assertThat(processing).contains("(No.130)");
        Files.writeString(spec, source.substring(0, start) + processing.replace("(No.130)", "(No.132)") + source.substring(end));
        GenerateAtl105Element84WidthGate.main(new String[]{pack.toString()});
        assertThat(report().path("exceptionListVerified").asBoolean()).isFalse();
        assertThat(report().path("summary").path("draftCaseCount").asInt()).isZero();
    }

    @Test
    void versionMismatchFailsBeforePublishing() throws Exception {
        copyInputs();
        Path manifest = pack.resolve("contract/element-84-width-assertions.json");
        String content = Files.readString(manifest).replace("2026-3", "unsupported-version");
        Files.writeString(manifest, content);
        assertThatThrownBy(() -> GenerateAtl105Element84WidthGate.main(new String[]{pack.toString()}))
            .isInstanceOf(IllegalStateException.class).hasMessageContaining("version");
        assertThat(Files.exists(pack.resolve("test-output/test-solution-independent-review/atl105-element-84-width-gate.json"))).isFalse();
    }

    private void copyInputs() throws Exception {
        for (String name : new String[]{"contract/element-84-width-assertions.json", "docs/specs/extracted_text.txt",
            "test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json",
            "test-output/test-solution-independent-review/atl105-br-approval-matrix.json"}) {
            Path target = pack.resolve(name);
            Files.createDirectories(target.getParent());
            Files.copy(Atl105Paths.root().resolve(name), target);
        }
    }

    private JsonNode report() throws Exception {
        return mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-element-84-width-gate.json").toFile());
    }
}