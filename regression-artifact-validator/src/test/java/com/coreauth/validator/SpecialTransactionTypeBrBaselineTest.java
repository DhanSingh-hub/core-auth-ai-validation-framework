package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SpecialTransactionTypeBrBaselineTest {
    private static final Path BASELINE = Path.of(
            "specifications/ATL105/test-output/test-json/special-transaction-type-br-baseline.json");

    @Test
    void createsSourceAnchoredDraftBrsForAllSpecialCodesWithoutInventingChains() throws Exception {
        JsonNode report = new ObjectMapper().readTree(BASELINE.toFile());

        assertThat(report.path("manifest").path("authority").asText()).isEqualTo("INDEPENDENT_TEST_SOLUTION");
        assertThat(report.path("manifest").path("aiArtifactsIncluded").asBoolean()).isFalse();
        assertThat(report.path("summary").path("specialTransactionCodes").asInt()).isEqualTo(10);
        assertThat(report.path("businessRequirements")).hasSize(34);
        assertThat(report.path("summary").path("testScenarios").asInt()).isZero();
        assertThat(report.path("summary").path("testCases").asInt()).isZero();
        assertThat(report.path("summary").path("testDataJsonFixtures").asInt()).isZero();
        assertThat(report.path("summary").path("automaticallyApproved").asBoolean()).isFalse();

        Set<String> ids = new HashSet<>();
        Set<String> codes = new HashSet<>();
        for (JsonNode br : report.path("businessRequirements")) {
            assertThat(ids.add(br.path("id").asText())).isTrue();
            assertThat(br.path("status").asText()).isEqualTo("DRAFT_REVIEW_REQUIRED");
            assertThat(br.path("sourceAnchors")).isNotEmpty();
            codes.add(br.path("transactionTypeCode").asText());
        }
        assertThat(codes).containsExactlyInAnyOrder("9", "D", "E", "K", "L", "M", "N", "Q", "T", "V");
        assertThat(report.path("businessRequirements").findValuesAsText("blocker"))
                .anyMatch(value -> value.contains("external TransArmor"));
    }
}
