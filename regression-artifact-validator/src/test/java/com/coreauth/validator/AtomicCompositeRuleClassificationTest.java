package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class AtomicCompositeRuleClassificationTest {
    private static final Path CLASSIFICATION = Path.of(
            "specifications/ATL105/test-output/test-solution-independent-review/"
                    + "atomic-composite-rule-classification.json");

    @Test
    void classificationPreservesEveryIndependentSourceRule() throws Exception {
        JsonNode report = new ObjectMapper().readTree(CLASSIFICATION.toFile());

        assertThat(report.path("authority").asText()).isEqualTo("INDEPENDENT_TEST_SOLUTION");
        assertThat(report.path("aiArtifactsIncluded").asBoolean()).isFalse();
        assertThat(report.path("rules")).hasSize(601);
        assertThat(report.path("counts").path("ATOMIC").asInt()).isEqualTo(324);
        assertThat(report.path("counts").path("COMPOSITE_CANDIDATE").asInt()).isEqualTo(108);
        assertThat(report.path("counts").path("REVIEW_REQUIRED").asInt()).isEqualTo(169);
        assertThat(report.path("validation").path("preservesSourceRuleCount").asBoolean()).isTrue();
        assertThat(report.path("reviewQueue")).hasSize(169);
        assertThat(report.path("rules").get(0).path("verificationDimensions")).isNotEmpty();
    }
}