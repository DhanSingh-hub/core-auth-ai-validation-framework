package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AggregateEvidenceMergeTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void identicalDuplicatesAreCountedOnlyOnce() throws Exception {
        JsonNode values = mapper.readTree("[{\"id\":\"BR-1\",\"status\":\"REVIEW_REQUIRED\"}]");
        Map<String, JsonNode> target = new LinkedHashMap<>();
        Map<String, String> sources = new LinkedHashMap<>();
        int[] firstCounts = new int[4];
        int[] secondCounts = new int[4];
        GenerateAllTestSolutionBrTsTcTdPackage.merge(values, target, sources, "first.json", firstCounts, 0);
        GenerateAllTestSolutionBrTsTcTdPackage.merge(values, target, sources, "second.json", secondCounts, 0);
        assertThat(target).hasSize(1);
        assertThat(firstCounts[0]).isEqualTo(1);
        assertThat(secondCounts[0]).isZero();
    }

    @Test
    void conflictingDuplicatesNameBothSources() throws Exception {
        Map<String, JsonNode> target = new LinkedHashMap<>();
        Map<String, String> sources = new LinkedHashMap<>();
        int[] counts = new int[4];
        GenerateAllTestSolutionBrTsTcTdPackage.merge(mapper.readTree("[{\"id\":\"BR-1\",\"status\":\"DRAFT\"}]"),
            target, sources, "first.json", counts, 0);
        JsonNode conflicting = mapper.readTree("[{\"id\":\"BR-1\",\"status\":\"APPROVED\"}]");
        assertThatThrownBy(() -> GenerateAllTestSolutionBrTsTcTdPackage.merge(conflicting, target, sources, "second.json", counts, 0))
            .isInstanceOf(IllegalStateException.class).hasMessageContaining("BR-1").hasMessageContaining("first.json").hasMessageContaining("second.json");
        assertThat(counts[0]).isEqualTo(1);
    }
}