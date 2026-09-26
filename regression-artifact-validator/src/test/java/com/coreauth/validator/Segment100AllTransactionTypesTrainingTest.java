package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/** Protects the independent Test Solution training baseline for all 23 Appendix G codes. */
class Segment100AllTransactionTypesTrainingTest {
    private static final Path BASELINE = Path.of(
            "specifications", "ATL105", "test-output", "test-json",
            "segment-100-all-23-transaction-type-training-baseline.json");

    @Test
    void trainsAllTwentyThreeAppendixGTransactionTypes() throws Exception {
        JsonNode root = new ObjectMapper().readTree(BASELINE.toFile());
        JsonNode transactionTypes = root.path("transactionTypes");

        assertThat(root.path("manifest").path("specification").asText()).isEqualTo("ATL105");
        assertThat(root.path("manifest").path("specificationVersion").asText()).isEqualTo("2026-3");
        assertThat(transactionTypes).hasSize(23);
        assertThat(transactionTypes.findValuesAsText("code"))
                .containsExactlyInAnyOrder("0", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "K", "L", "M", "N", "Q", "S", "T", "U", "V", "Z");
        assertThat(transactionTypes.findValuesAsText("class").stream().filter("STANDARD_FINANCIAL"::equals).count()).isEqualTo(13);
        assertThat(transactionTypes.findValuesAsText("class").stream().filter("SPECIAL_NONFINANCIAL"::equals).count()).isEqualTo(10);

        Set<String> anchors = new HashSet<>();
        transactionTypes.forEach(type -> {
            assertThat(type.path("name").asText()).isNotBlank();
            assertThat(type.path("sourceAnchor").asText()).startsWith("ATL105|2026-3|Appendix G|100|78|");
            anchors.add(type.path("sourceAnchor").asText());
        });
        assertThat(anchors).hasSize(23);
        assertThat(root.path("lifecycleScenarios")).hasSize(5);
    }
}
