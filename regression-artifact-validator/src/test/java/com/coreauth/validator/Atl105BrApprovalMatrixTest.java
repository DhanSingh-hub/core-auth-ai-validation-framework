package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105BrApprovalMatrix;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105BrApprovalMatrixTest {
    private static final Path PACK = Path.of("specifications", "ATL105");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void separatesAuthoredLinksFromSmeApproval() throws Exception {
        GenerateAtl105BrApprovalMatrix.main(new String[]{PACK.toString()});
        Path output = PACK.resolve("test-output/test-solution-independent-review/atl105-br-approval-matrix.json");
        JsonNode matrix = MAPPER.readTree(output.toFile());
        assertThat(matrix.path("oracleRuleCount").asInt()).isEqualTo(601);
        assertThat(matrix.path("authoredBrLinkedRuleCount").asInt()).isEqualTo(601);
        assertThat(matrix.path("smeApprovedRuleCount").asInt()).isZero();
        assertThat(matrix.path("rulesPendingSmeApproval").asInt()).isEqualTo(601);
        assertThat(matrix.path("approvedNewRuleDecisions").asInt()).isZero();
        assertThat(matrix.path("chainDerivationAllowed").asBoolean()).isFalse();
        assertThat(matrix.path("rows")).hasSize(601);
        for (JsonNode row : matrix.path("rows")) {
            assertThat(row.path("semanticApprovalStatus").asText()).isEqualTo("SME_APPROVAL_NOT_RECORDED");
            assertThat(row.path("businessRuleStatus").asText()).isEqualTo("DRAFT_REVIEW_REQUIRED");
            assertThat(row.path("chainDerivationStatus").asText()).isEqualTo("NOT_APPROVED_FOR_CHAIN_DERIVATION");
        }
    }
}
