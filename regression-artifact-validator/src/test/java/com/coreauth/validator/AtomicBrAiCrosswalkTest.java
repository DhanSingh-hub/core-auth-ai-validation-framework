package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class AtomicBrAiCrosswalkTest {
    private static final Path CROSSWALK = Path.of(
            "specifications/ATL105/test-output/test-solution-independent-review/"
                    + "atomic-br-ai-crosswalk.json");

    @Test
    void crosswalkPreservesEveryAiRequirementWithoutClaimingCoverage() throws Exception {
        JsonNode report = new ObjectMapper().readTree(CROSSWALK.toFile());

        assertThat(report.path("authority").asText()).isEqualTo("INDEPENDENT_TEST_SOLUTION");
        assertThat(report.path("mappings")).hasSize(6473);
        assertThat(report.path("validation").path("oneEntryPerAiRequirement").asBoolean()).isTrue();
        assertThat(report.path("matchingPolicy").asText()).contains("exact normalized segment plus inferred numeric source-rule element");
        assertThat(report.path("coveragePolicy").asText()).contains("not confirmed BR coverage");
        assertThat(report.path("counts").path("EXACT_ATOMIC_CANDIDATE").asInt()).isEqualTo(522);
        assertThat(report.path("counts").path("REVIEW_REQUIRED_COMPOSITE_OR_AMBIGUOUS").asInt()).isEqualTo(226);
        assertThat(report.path("counts").path("NO_EXACT_ATOMIC_MATCH").asInt()).isEqualTo(5725);
    }
}