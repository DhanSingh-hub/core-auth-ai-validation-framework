package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100ContextModelTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void contextModelDefinesRequiredAndConditionalDimensions() throws Exception {
        JsonNode model = MAPPER.readTree(Path.of("test-output/test-json/knowledge/segment-100-context-model.json").toFile());

        assertThat(model.path("requiredContext").has("transactionType")).isTrue();
        assertThat(model.path("requiredContext").has("cardType")).isTrue();
        assertThat(model.path("requiredContext").has("paymentNetworkType")).isTrue();
        assertThat(model.path("requiredContext").has("lifecycleRole")).isTrue();
        assertThat(model.path("requiredContext").has("messageFamily")).isTrue();
        assertThat(model.path("conditionalContext").path("specializedDomain")).isNotEmpty();
        assertThat(model.path("validationRules")).hasSize(6);
        assertThat(model.path("status").asText()).isEqualTo("READY_FOR_AI_ARTIFACT_INTAKE");
    }
}
