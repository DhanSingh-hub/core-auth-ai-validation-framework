package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ApprovedBrChainPackageTest {
    private static final Path PACKAGE = Path.of(
            "specifications/ATL105/test-output/test-solution-independent-review/"
                    + "approved-br-ts-tc-td-chain-package.json");

    @Test
    void chainPackageRequiresApprovedBrsAndIsNotExecutionReady() throws Exception {
        JsonNode output = new ObjectMapper().readTree(PACKAGE.toFile());

        assertThat(output.path("manifest").path("authority").asText())
                .isEqualTo("INDEPENDENT_TEST_SOLUTION");
        assertThat(output.path("manifest").path("aiArtifactsIncluded").asBoolean()).isFalse();
        assertThat(output.path("businessRequirements")).isEmpty();
        assertThat(output.path("testScenarios")).isEmpty();
        assertThat(output.path("testCases")).isEmpty();
        assertThat(output.path("testData")).isEmpty();
        assertThat(output.path("summary").path("executionReady").asBoolean()).isFalse();
        assertThat(output.path("summary").path("nextGate").asText())
                .isEqualTo("CHAIN_VALIDATION_AFTER_TS_TC_TD_DERIVATION");
    }
}