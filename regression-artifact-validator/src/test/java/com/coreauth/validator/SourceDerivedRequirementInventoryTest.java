package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class SourceDerivedRequirementInventoryTest {
    private static final Path INVENTORY = Path.of(
            "specifications/ATL105/test-output/test-solution-independent-review/"
                    + "source-derived-requirement-inventory.json");

    @Test
    void inventoryIsIndependentCompleteAndAnchorValidated() throws Exception {
        JsonNode inventory = new ObjectMapper().readTree(INVENTORY.toFile());

        assertThat(inventory.path("authority").asText()).isEqualTo("INDEPENDENT_TEST_SOLUTION");
        assertThat(inventory.path("aiArtifactsIncluded").asBoolean()).isFalse();
        assertThat(inventory.path("catalogCount").asInt()).isEqualTo(13);
        assertThat(inventory.path("ruleCount").asInt()).isEqualTo(289);
        assertThat(inventory.path("duplicateRuleIdCount").asInt()).isZero();
        assertThat(inventory.path("duplicateAnchorCount").asInt()).isZero();
        assertThat(inventory.path("incompleteAnchorCount").asInt()).isZero();
        assertThat(inventory.path("validation").path("valid").asBoolean()).isTrue();
        assertThat(inventory.path("businessRequirements")).hasSize(289);
        assertThat(inventory.path("countsBySegment").path("100").asInt()).isEqualTo(36);
        assertThat(inventory.path("countsBySegment").path("111").asInt()).isEqualTo(8);
    }
}