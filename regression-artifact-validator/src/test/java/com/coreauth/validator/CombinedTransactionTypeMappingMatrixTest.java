package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CombinedTransactionTypeMappingMatrixTest {
    private static final Path MATRIX = Path.of(
            "specifications/ATL105/test-output/test-json/combined-23-transaction-type-br-ts-tc-td-matrix.json");

    @Test
    void combinesAllCodesWithoutOverstatingTestDataOwnershipOrReadiness() throws Exception {
        JsonNode matrix = new ObjectMapper().readTree(MATRIX.toFile());
        JsonNode summary = matrix.path("summary");

        assertThat(matrix.path("transactionTypes")).hasSize(23);
        assertThat(summary.path("transactionTypeCodes").asInt()).isEqualTo(23);
        assertThat(summary.path("businessRequirements").asInt()).isEqualTo(47);
        assertThat(summary.path("testScenarios").asInt()).isEqualTo(45);
        assertThat(summary.path("testCases").asInt()).isEqualTo(65);
        assertThat(summary.path("mappingEdgeCount").asInt()).isEqualTo(65);
        assertThat(summary.path("aiInputFixtureReferences").asInt()).isEqualTo(13);
        assertThat(summary.path("sharedTestSolutionLifecycleCatalogReferences").asInt()).isEqualTo(12);
        assertThat(summary.path("specialTestDataDraftStubs").asInt()).isEqualTo(32);
        assertThat(summary.path("sharedLifecycleCatalogTestDataRecords").asInt()).isEqualTo(9);
        assertThat(summary.path("executableIndependentTestDataFixtures").asInt()).isZero();
        assertThat(summary.path("specialBusinessRequirementsBlocked").asInt()).isEqualTo(2);
        assertThat(summary.path("executionReady").asBoolean()).isFalse();
        JsonNode single = matrix.path("flowViews").path("singleStep");
        assertThat(single.path("eligibleCodeCount").asInt()).isEqualTo(18);
        assertThat(single.path("totalBusinessRequirements").asInt()).isEqualTo(42);
        assertThat(single.path("totalTestScenarios").asInt()).isEqualTo(40);
        assertThat(single.path("totalTestCases").asInt()).isEqualTo(48);
        assertThat(single.path("executableIndependentTestDataFixtures").asInt()).isZero();
        JsonNode multi = matrix.path("flowViews").path("multiStep");
        assertThat(multi.path("eligibleCodeCount").asInt()).isEqualTo(10);
        assertThat(multi.path("lifecycleParticipantCodeCount").asInt()).isEqualTo(12);
        assertThat(multi.path("lifecycleScenarioFamilyCount").asInt()).isEqualTo(5);
        assertThat(multi.path("lifecycleCatalogBusinessRequirements").asInt()).isEqualTo(5);
        assertThat(multi.path("lifecycleCatalogTestScenarios").asInt()).isEqualTo(5);
        assertThat(multi.path("lifecycleCatalogTestCases").asInt()).isEqualTo(9);
        assertThat(multi.path("lifecycleCatalogTestData").asInt()).isEqualTo(9);
        assertThat(matrix.path("validation").path("allAppendixGCodesPresent").asBoolean()).isTrue();
        assertThat(matrix.path("validation").path("noDuplicateTransactionCodes").asBoolean()).isTrue();
        assertThat(matrix.path("validation").path("allDraftSpecialChainsHaveBR_TS_TC_TD").asBoolean()).isTrue();
        assertThat(matrix.path("validation").path("allSpecialTdDraftFilesExist").asBoolean()).isTrue();
        int mappingEdges = 0;
        for (JsonNode row : matrix.path("transactionTypes")) {
            assertThat(row.path("status").asText()).isNotBlank();
            for (JsonNode mapping : row.path("brTsTcTdMappings")) {
                if (!mapping.path("testScenarioId").asText().isBlank()) mappingEdges++;
            }
        }
        assertThat(mappingEdges).isEqualTo(65);

        Set<String> codes = new HashSet<>();
        matrix.path("transactionTypes").forEach(row -> codes.add(row.path("transactionTypeCode").asText()));
        assertThat(codes).containsExactlyInAnyOrder("0", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "K", "L", "M", "N", "Q", "S", "T", "U", "V", "Z");
    }
}
