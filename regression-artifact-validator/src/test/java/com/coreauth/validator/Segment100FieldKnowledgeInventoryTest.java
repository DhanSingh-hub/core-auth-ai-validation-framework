package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100FieldKnowledgeInventoryTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void inventoryContainsAllSeventeenOrderedSegment100Fields() throws Exception {
        JsonNode inventory = MAPPER.readTree(Path.of("test-output/test-json/knowledge/segment-100-field-knowledge-inventory.json").toFile());

        assertThat(inventory.path("fieldCount").asInt()).isEqualTo(17);
        assertThat(inventory.path("fields")).hasSize(17);
        assertThat(inventory.path("fields").get(0).path("element").asText()).isEqualTo("85");
        assertThat(inventory.path("fields").get(16).path("element").asText()).isEqualTo("121");
        assertThat(inventory.path("globalRules")).hasSize(5);
    }

    @Test
    void fieldInventoryContainsRequiredAndConditionalKnowledge() throws Exception {
        JsonNode fields = MAPPER.readTree(Path.of("test-output/test-json/knowledge/segment-100-field-knowledge-inventory.json").toFile()).path("fields");

        assertThat(fields.findValuesAsText("requiredness")).contains("REQUIRED", "CONDITIONAL");
        assertThat(fields.findValuesAsText("rule")).contains(
                "segment-type", "prompt-code", "account-number", "sequence-number", "partial-approval-indicator");
    }
}
