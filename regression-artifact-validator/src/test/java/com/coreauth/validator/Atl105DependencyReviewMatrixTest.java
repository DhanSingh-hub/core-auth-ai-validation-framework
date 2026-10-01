package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105DependencyReviewMatrix;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105DependencyReviewMatrixTest {
    private static final Path PACK = Path.of("specifications", "ATL105");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void keepsDependencyAndAbsenceCandidatesReviewOnly() throws Exception {
        GenerateAtl105DependencyReviewMatrix.main(new String[]{PACK.toString()});
        Path output = PACK.resolve("test-output/test-solution-independent-review/atl105-dependency-absence-review-matrix.json");
        JsonNode matrix = MAPPER.readTree(output.toFile());
        assertThat(matrix.path("candidateCount").asInt()).isEqualTo(235);
        assertThat(matrix.path("dependencyCandidateCount").asInt()).isEqualTo(227);
        assertThat(matrix.path("explicitAbsenceOrConditionCandidateCount").asInt()).isEqualTo(8);
        assertThat(matrix.path("semanticApprovedCount").asInt()).isZero();
        assertThat(matrix.path("enforcedCount").asInt()).isZero();
        assertThat(matrix.path("reviewRequiredCount").asInt()).isEqualTo(235);
        for (JsonNode row : matrix.path("rows")) {
            assertThat(row.path("semanticReviewStatus").asText()).isEqualTo("REVIEW_REQUIRED");
            assertThat(row.path("approvalStatus").asText()).isEqualTo("NOT_SME_APPROVED");
            assertThat(row.path("enforcementStatus").asText()).isEqualTo("NOT_ENFORCED");
        }
    }
}
