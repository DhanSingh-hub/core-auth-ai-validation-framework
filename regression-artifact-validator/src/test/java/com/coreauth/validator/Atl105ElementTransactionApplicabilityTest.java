package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105ElementReference;
import com.coreauth.validator.coverage.GenerateAtl105ElementTransactionApplicability;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105ElementTransactionApplicabilityTest {
    private static final Path PACK = Path.of("specifications", "ATL105");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void generatesCompleteReviewMatrixWithoutInventingApplicability() throws Exception {
        GenerateAtl105ElementReference.main(new String[]{PACK.toString()});
        GenerateAtl105ElementTransactionApplicability.main(new String[]{PACK.toString()});
        Path output = PACK.resolve("test-output/test-solution-independent-review/atl105-element-transaction-applicability.json");
        JsonNode matrix = MAPPER.readTree(output.toFile());
        assertThat(matrix.path("elementCount").asInt()).isEqualTo(231);
        assertThat(matrix.path("transactionTypeCount").asInt()).isEqualTo(23);
        assertThat(matrix.path("pairCount").asInt()).isEqualTo(5313);
        assertThat(matrix.path("sourceDefinedCodeDomainRows").asInt()).isEqualTo(23);
        assertThat(matrix.path("conditionalFlowReviewRows").asInt()).isEqualTo(391);
        assertThat(matrix.path("unmappedReviewRows").asInt()).isEqualTo(4899);
        assertThat(matrix.path("rows")).hasSize(5313);
        assertThat(matrix.path("status").asText()).isEqualTo("EXHAUSTIVE_REVIEW_MATRIX_NOT_EXHAUSTIVE_SEMANTIC_PROOF");
        for (JsonNode row : matrix.path("rows")) {
            assertThat(row.path("validityDecision").asText()).isEqualTo("REVIEW_REQUIRED_UNLESS_STATUS_IS_SOURCE_DEFINED_CODE_DOMAIN");
        }
    }
}
