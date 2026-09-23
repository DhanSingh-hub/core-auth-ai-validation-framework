package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100KnowledgeBoundaryTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void separatesRequestResponseAndLifecycleKnowledge() throws Exception {
        JsonNode model = MAPPER.readTree(Path.of("specifications/ATL105/test-output/test-json/knowledge/segment-100-request-response-lifecycle-knowledge.json").toFile());

        assertThat(model.path("domains").has("request")).isTrue();
        assertThat(model.path("domains").has("response")).isTrue();
        assertThat(model.path("domains").has("lifecycle")).isTrue();
        assertThat(model.path("domains").path("request").path("primaryFields").toString()).doesNotContain("ResponseCode");
        assertThat(model.path("domains").path("response").path("primaryFields").toString()).contains("ResponseCode");
        assertThat(model.path("domains").path("lifecycle").path("identityFields")).hasSize(6);
        assertThat(model.path("boundaryRules")).hasSize(5);
    }
}
