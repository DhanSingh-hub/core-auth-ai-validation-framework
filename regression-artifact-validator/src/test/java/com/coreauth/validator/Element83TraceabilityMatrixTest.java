package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Element83TraceabilityMatrixTest {
    private static final Path MATRIX = Path.of("specifications", "ATL105", "test-output",
        "test-solution-independent-review", "element-83-traceability-matrix.json");

    @Test
    void reportsEachValidCodeFamilyBrAndDoesNotInventTotalsResponseChain() throws Exception {
        com.coreauth.validator.coverage.GenerateElement83TraceabilityMatrix.main(new String[0]);
        JsonNode report = new ObjectMapper().readTree(MATRIX.toFile());
        assertThat(report.path("rows")).hasSize(35);
        assertThat(report.path("summary").path("validCodeFamilyBrs").asInt()).isEqualTo(35);
        assertThat(report.path("summary").path("rowsWithExactCodeTestData").asInt()).isEqualTo(4);
        assertThat(report.path("summary").path("completeBrTsTcTdChains").asInt()).isEqualTo(4);
        assertThat(report.path("summary").path("confirmedAiMappings").asInt()).isZero();

        JsonNode codeFive = null;
        for (JsonNode row : report.path("rows"))
            if ("5".equals(row.path("expectedResponseCode").asText())) codeFive = row;
        assertThat(codeFive).isNotNull();
        assertThat(codeFive.path("responseFamily").asText()).isEqualTo("totals");
        assertThat(codeFive.path("element83BusinessRequirementId").asText()).isEqualTo("BR-E83-TOT-5");
        assertThat(codeFive.path("testScenarioStatus").asText()).isEqualTo("MISSING");
        assertThat(codeFive.path("testCaseStatus").asText()).isEqualTo("MISSING");
        assertThat(codeFive.path("testDataStatus").asText()).isEqualTo("MISSING_EXACT_RESPONSE_CODE_DATA");
        assertThat(codeFive.path("aiMappingStatus").asText()).isEqualTo("NO_CONFIRMED_MAPPING");
        assertThat(codeFive.path("chainCompleteThroughTestData").asBoolean()).isFalse();
    }
}
