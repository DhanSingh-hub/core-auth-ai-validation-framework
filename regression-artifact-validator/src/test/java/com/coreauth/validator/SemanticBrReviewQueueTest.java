package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class SemanticBrReviewQueueTest {
    private static final Path QUEUE = Path.of(
            "specifications/ATL105/test-output/test-solution-independent-review/semantic-br-review-queue.json");
    private static final Path REGISTER = Path.of(
            "specifications/ATL105/test-output/test-solution-independent-review/semantic-br-decision-register.json");

    @Test
    void semanticReviewKeepsEveryMappingPendingUntilSourceBackedDecision() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode queue = mapper.readTree(QUEUE.toFile());
        JsonNode register = mapper.readTree(REGISTER.toFile());

        assertThat(queue.path("reviewItems")).hasSize(6473);
        assertThat(queue.path("reviewItemCount").asInt()).isEqualTo(6473);
        assertThat(queue.path("nextGate").path("name").asText()).isEqualTo("SOURCE_BACKED_BR_DECISION");
        assertThat(register.path("decisions")).hasSize(6473);
        assertThat(register.path("decisions")).allMatch(value ->
                "PENDING".equals(value.path("decision").asText())
                        && "UNREVIEWED".equals(value.path("reviewer").asText())
                        && "AI_TO_TEST_CROSSWALK".equals(value.path("subjectType").asText()));
    }
}