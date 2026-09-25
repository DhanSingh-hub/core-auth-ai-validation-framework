package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class AutomatedTestValidationRegisterTest {
    private static final Path REGISTER = Path.of(
            "specifications/ATL105/test-output/ai-solution-independent-review/"
                    + "automated-test-validation-register.json");

    @Test
    void automatedStatusRequiresCompleteNonPlaceholderFourLevelEvidence() throws Exception {
        JsonNode report = new ObjectMapper().readTree(REGISTER.toFile());

        assertThat(report.path("statusDefinition").asText()).contains("non-empty BR, TS, TC, and TD");
        assertThat(report.path("smeApprovalRequiredForAutomatedStatus").asBoolean()).isFalse();
        assertThat(report.path("smeApprovalRequiredForUnresolvedMappings").asBoolean()).isTrue();
        assertThat(report.path("confirmedMappingCount").asInt()).isEqualTo(30);
        assertThat(report.path("automatedTestValidatedMappingCount").asInt()).isEqualTo(12);
        assertThat(report.path("automatedTestValidatedDistinctAiBrCount").asInt()).isEqualTo(147);
        assertThat(report.path("statusCounts").path("AutomatedChainIncomplete").asInt()).isEqualTo(18);
        assertThat(report.path("statusCounts").path("SME_REQUIRED").asInt()).isEqualTo(70);
        assertThat(report.path("validation").path("allValidatedMappingsHaveFourLevels").asBoolean()).isTrue();
    }
}