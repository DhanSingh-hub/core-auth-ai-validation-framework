package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105FixedSegmentTypeGate;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class FixedSegmentTypeGateTest {
    @TempDir Path pack;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void keepsAbsentSourceValuesInReview() throws Exception {
        copyInputs();
        JsonNode result = generate();
        assertThat(result.path("summary").path("catalogRuleCount").asInt()).isEqualTo(601);
        assertThat(result.path("summary").path("candidateRuleCount").asInt()).isEqualTo(24);
        assertThat(result.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(22);
        assertThat(result.path("summary").path("sourceMismatchCount").asInt()).isEqualTo(2);
        assertThat(result.path("summary").path("draftCaseCount").asInt()).isEqualTo(44);
        assertThat(result.path("summary").path("smeApprovedRuleCount").asInt()).isZero();
        assertThat(assertion(result, "SEG114-R-003").path("status").asText()).isEqualTo("SOURCE_MISMATCH_REVIEW_REQUIRED");
        assertThat(assertion(result, "SEG101-R-004").path("sourceLine").asInt()).isGreaterThan(11000);
        assertThat(result.path("draftCases").get(0).path("executionReady").asBoolean()).isFalse();
    }

    @Test
    void changingTheCitedFixedValueWithdrawsOnlyThatRule() throws Exception {
        copyInputs();
        replaceSource("Fixed value:  101", "Fixed value:  199");
        JsonNode result = generate();
        assertThat(result.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(21);
        assertThat(result.path("summary").path("draftCaseCount").asInt()).isEqualTo(42);
        assertThat(assertion(result, "SEG101-R-004").path("status").asText()).isEqualTo("SOURCE_MISMATCH_REVIEW_REQUIRED");
    }

    @Test
    void conflictingValuesInFieldOneCannotBePromoted() throws Exception {
        copyInputs();
        replaceSource("Fixed value:  101", "Fixed value:  101\nFixed value:  199");
        JsonNode result = generate();
        assertThat(result.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(21);
        assertThat(assertion(result, "SEG101-R-004").path("status").asText()).isEqualTo("SOURCE_MISMATCH_REVIEW_REQUIRED");
    }

    @Test
    void nonFixedCatalogTitleIsNotAutomaticallyCurated() throws Exception {
        copyInputs();
        Path coverage = pack.resolve("test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json");
        String original = Files.readString(coverage);
        assertThat(original).contains("Segment Type is 101");
        Files.writeString(coverage, original.replace("Segment Type is 101", "Segment Type may be 101"));
        JsonNode result = generate();
        assertThat(result.path("summary").path("candidateRuleCount").asInt()).isEqualTo(23);
        assertThat(result.path("summary").path("draftCaseCount").asInt()).isEqualTo(42);
    }

    private void replaceSource(String original, String replacement) throws Exception {
        Path path = pack.resolve("docs/specs/extracted_text.txt");
        String source = Files.readString(path);
        assertThat(source).contains(original);
        Files.writeString(path, source.replace(original, replacement));
    }

    private JsonNode generate() throws Exception {
        GenerateAtl105FixedSegmentTypeGate.main(new String[]{pack.toString()});
        return mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-fixed-segment-type-gate.json").toFile());
    }

    private static JsonNode assertion(JsonNode report, String ruleId) {
        for (JsonNode candidate : report.path("assertions")) {
            if (ruleId.equals(candidate.path("ruleId").asText())) return candidate;
        }
        throw new IllegalStateException("Missing rule " + ruleId);
    }

    private void copyInputs() throws Exception {
        for (String relative : new String[]{"docs/specs/extracted_text.txt",
            "test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json",
            "test-output/test-solution-independent-review/atl105-br-approval-matrix.json"}) {
            Path output = pack.resolve(relative);
            Files.createDirectories(output.getParent());
            Files.copy(Atl105Paths.root().resolve(relative), output);
        }
    }
}