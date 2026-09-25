package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class AiTestManyToManyMatrixTest {
    private static final Path MATRIX = Path.of(
            "specifications/ATL105/test-output/test-solution-independent-review/"
                    + "ai-test-many-to-many-coverage-matrix.json");

    @Test
    void matrixPreservesBothMappingDirectionsAndKeepsCoverageReviewRequired() throws Exception {
        JsonNode report = new ObjectMapper().readTree(MATRIX.toFile());

        assertThat(report.path("authority").asText()).isEqualTo("INDEPENDENT_TEST_SOLUTION");
        assertThat(report.path("aiToTest")).hasSize(6473);
        assertThat(report.path("testToAi")).hasSize(112);
        assertThat(report.path("validation").path("oneEntryPerAiRequirement").asBoolean()).isTrue();
        assertThat(report.path("validation").path("preservesCandidateCount").asBoolean()).isTrue();
        assertThat(report.path("confirmationPolicy").asText()).contains("No matrix status is confirmed coverage");
        assertThat(report.path("statusCounts").path("REVIEW_REQUIRED_EXACT_CANDIDATE").asInt()).isEqualTo(522);
        assertThat(report.path("statusCounts").path("REVIEW_REQUIRED_COMPOSITE_OR_AMBIGUOUS").asInt()).isEqualTo(226);
        assertThat(report.path("statusCounts").path("MISSING_TEST_RULE_CANDIDATE").asInt()).isEqualTo(5725);
    }
}