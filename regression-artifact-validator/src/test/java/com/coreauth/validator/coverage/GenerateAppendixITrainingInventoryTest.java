package com.coreauth.validator.coverage;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GenerateAppendixITrainingInventoryTest {
    @Test
    void locatesActualSourceTablesWithoutClaimingTrainingOrAiCoverage() throws IOException {
        var inventory = GenerateAppendixITrainingInventory.generate(Path.of("specifications", "ATL105"));
        assertThat(inventory.path("tableCount").asInt()).isEqualTo(78);
        TreeSet<String> ids = new TreeSet<>();
        inventory.path("tables").forEach(row -> {
            ids.add(row.path("tableId").asText());
            assertThat(row.path("sourcePage").asText()).startsWith("Appendix I-");
            assertThat(row.path("pdfPage").asInt()).isPositive();
            assertThat(row.path("navigationEndLine").asInt()).isGreaterThan(row.path("headerLine").asInt());
            assertThat(row.path("sourceAnchor").path("element").asText()).isEqualTo("111");
        });
        assertThat(ids).hasSize(78).contains("001", "009", "049", "056", "078", "079", "081")
                .doesNotContain("023", "061", "074");
        assertThat(inventory.path("coverage").path("semanticallyTrainedTables").asInt()).isZero();
        assertThat(inventory.path("coverage").path("aiCoveragePercent").isNull()).isTrue();
        assertThat(inventory.path("source").path("textSha256").asText()).matches("[0-9a-f]{64}");
        assertThat(inventory.path("source").path("pdfSha256").asText()).matches("[0-9a-f]{64}");
    }

    @Test
    void distinguishesWrappedParentIdsFromTwoDigitNestedSelectorsAndLengthValues() {
        var out = GenerateAppendixITrainingInventory.scan(List.of(
                "Specification Release Version:    2026-3",
                "--- PAGE 543 ---",
                "Appendix I-1",
                "Appendix I.  Variable Information Data Layouts",
                "Table", "ID", "n3 Fixed value: 001",
                "Table Length n3 Fixed value: 030",
                "Sub Table ID \u201c01\u201d",
                "Table ID", "n2 Fixed Value: 02",
                "Table ID n2 Fixed value: 058",
                "Table Length n3 Fixed value: 010",
                "Appendix J.  Valid Point-of-Service Entry Mode Codes"));
        assertThat(out.path("tableCount").asInt()).isEqualTo(2);
        assertThat(out.path("tables").get(0).path("pdfPage").asInt()).isEqualTo(543);
        var nested = out.path("tables").get(0).path("nestedSelectorOccurrences");
        assertThat(nested).hasSize(2);
        assertThat(nested.get(0).path("subTableId").asText()).isEqualTo("01");
        assertThat(nested.get(1).path("subTableId").asText()).isEqualTo("02");
    }

    @Test
    void rejectsMissingDuplicateOrReversedBoundariesAndDuplicateIds() {
        assertThatThrownBy(() -> GenerateAppendixITrainingInventory.scan(List.of()))
                .isInstanceOf(IllegalArgumentException.class);
        var prefix = List.of("Specification Release Version:    2026-3",
                "Appendix I.  Variable Information Data Layouts",
                "Table ID n3 Fixed value: 001", "Table ID n3 Fixed value: 001",
                "Appendix J.  Valid Point-of-Service Entry Mode Codes");
        assertThatThrownBy(() -> GenerateAppendixITrainingInventory.scan(prefix))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Duplicate top-level");
        assertThatThrownBy(() -> GenerateAppendixITrainingInventory.scan(List.of(
                "Appendix J.  Valid Point-of-Service Entry Mode Codes",
                "Appendix I.  Variable Information Data Layouts")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
