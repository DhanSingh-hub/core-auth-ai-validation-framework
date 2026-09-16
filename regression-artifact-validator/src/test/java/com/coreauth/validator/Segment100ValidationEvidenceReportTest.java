package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100ValidationEvidenceReport;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100ValidationEvidenceReportTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void generatesEvidenceForCodesFlowsAndPendingGates() throws Exception {
        Path output = Files.createTempDirectory("segment-100-evidence-");
        Segment100ValidationEvidenceReport.ReportFiles files = new Segment100ValidationEvidenceReport().generate(
                Path.of("test-input/ai-solution/test-data/segment-100/transaction-types"),
                Path.of("test-input/ai-solution/test-data/segment-100/lifecycle"),
                Path.of("test-output/test-json/segment-100-code-flow-br-ts-tc-testdata.json"),
                output);

        JsonNode report = MAPPER.readTree(files.json().toFile());

        assertThat(report.path("transactionTypeCoverage").path("expectedCodes").asInt()).isEqualTo(13);
        assertThat(report.path("transactionTypeCoverage").path("singleStepStatus").asText()).isEqualTo("PASS");
        assertThat(report.path("converterPreflight").path("status").asText()).isEqualTo("PASS");
        assertThat(report.path("multiStepCoverage").path("flowCount").asInt()).isEqualTo(8);
        assertThat(report.path("multiStepCoverage").path("status").asText()).isEqualTo("PASS");
        assertThat(report.path("brTsTcTestData").path("status").asText()).isEqualTo("PASS");
        assertThat(report.path("overallStatus").asText()).isEqualTo("READY_FOR_AI_ARTIFACT_INTAKE");
        assertThat(files.markdown()).exists();
    }
}
