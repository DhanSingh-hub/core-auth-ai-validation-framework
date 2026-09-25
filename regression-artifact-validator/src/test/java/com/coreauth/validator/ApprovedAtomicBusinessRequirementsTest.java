package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ApprovedAtomicBusinessRequirementsTest {
    private static final Path PACKAGE = Path.of(
            "specifications/ATL105/test-output/test-solution-independent-review/"
                    + "approved-atomic-test-solution-business-requirements.json");

    @Test
    void onlyReviewedNewRuleDecisionsCanCreateTestSolutionBusinessRequirements() throws Exception {
        JsonNode output = new ObjectMapper().readTree(PACKAGE.toFile());

        assertThat(output.path("authority").asText()).isEqualTo("INDEPENDENT_TEST_SOLUTION");
        assertThat(output.path("businessRequirements")).isEmpty();
        assertThat(output.path("summary").path("approvedNewRuleDecisions").asInt()).isZero();
        assertThat(output.path("summary").path("pendingDecisions").asInt()).isEqualTo(6473);
        assertThat(output.path("summary").path("executionReady").asBoolean()).isFalse();
        assertThat(output.path("summary").path("nextGate").asText())
                .isEqualTo("TS_TC_TD_DERIVATION_AND_CHAIN_VALIDATION");
        assertThat(output.path("policy").asText()).contains("Only reviewed NEW_RULE decisions");
    }
}