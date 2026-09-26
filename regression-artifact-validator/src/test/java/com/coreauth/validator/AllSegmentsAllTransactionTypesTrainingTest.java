package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/** Protects the independent 23-code by segment Test Solution training matrix. */
class AllSegmentsAllTransactionTypesTrainingTest {
    private static final Path BASELINE = Path.of(
            "specifications", "ATL105", "test-output", "test-json",
            "all-segments-all-23-transaction-type-training-baseline.json");

    @Test
    void coversAllInventorySegmentsAndAllAppendixGCodes() throws Exception {
        JsonNode root = new ObjectMapper().readTree(BASELINE.toFile());
        JsonNode entries = root.path("segmentTransactionTypes");

        assertThat(root.path("manifest").path("specification").asText()).isEqualTo("ATL105");
        assertThat(root.path("manifest").path("specificationVersion").asText()).isEqualTo("2026-3");
        assertThat(root.path("summary").path("segmentCount").asInt()).isEqualTo(48);
        assertThat(root.path("summary").path("transactionTypeCount").asInt()).isEqualTo(23);
        assertThat(entries).hasSize(48 * 23);

        Set<String> segments = new HashSet<>();
        Set<String> types = new HashSet<>();
        entries.forEach(entry -> {
            segments.add(entry.path("segment").asText());
            types.add(entry.path("transactionType").asText());
            assertThat(entry.path("sourceAnchor").asText())
                    .startsWith("ATL105|2026-3|Appendix G|100|78|transaction-type-");
            assertThat(entry.path("trainingStatus").asText()).isNotBlank();
            assertThat(entry.path("applicabilityStatus").asText()).isNotBlank();
        });

        assertThat(segments).hasSize(48);
        assertThat(types).containsExactlyInAnyOrder("0", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "K", "L", "M", "N", "Q", "S", "T", "U", "V", "Z");
    }
}
