package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixITableCatalog;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixITableCatalogTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final AppendixITableCatalog catalog = new AppendixITableCatalog();

    @Test
    void matchesTheSourceLocatedInventoryAndPreservesUnlocatedGaps() throws Exception {
        JsonNode inventory = MAPPER.readTree(Path.of(
                "specifications/ATL105/test-output/test-json/knowledge/appendix-i-inventory.json").toFile());
        Set<String> sourceIds = new HashSet<>();
        for (JsonNode table : inventory.path("tables")) sourceIds.add(table.path("tableId").asText());
        Set<String> sourceGaps = new HashSet<>();
        for (JsonNode gap : inventory.path("unlocatedIdsWithinAllocatedRange")) {
            sourceGaps.add("%03d".formatted(gap.asInt()));
        }

        assertThat(sourceIds).hasSize(78).isEqualTo(catalog.sourceLocatedIds());
        assertThat(sourceGaps).containsExactlyInAnyOrder("023", "061", "074")
                .isEqualTo(catalog.sourceUnlocatedIds());
    }

    @Test
    void distinguishesLocatedUnlocatedUnknownAndMalformedSelectors() {
        assertThat(catalog.classify("005")).isEqualTo(AppendixITableCatalog.Status.SOURCE_LOCATED);
        assertThat(catalog.classify("023")).isEqualTo(AppendixITableCatalog.Status.SOURCE_UNLOCATED);
        assertThat(catalog.classify("999")).isEqualTo(AppendixITableCatalog.Status.UNLISTED);
        assertThat(catalog.classify("23")).isEqualTo(AppendixITableCatalog.Status.INVALID_FORMAT);
    }
}