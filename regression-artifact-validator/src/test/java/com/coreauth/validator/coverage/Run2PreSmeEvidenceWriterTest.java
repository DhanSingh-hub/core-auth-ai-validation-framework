package com.coreauth.validator.coverage;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class Run2PreSmeEvidenceWriterTest {
    @TempDir Path directory;

    @Test
    void passingTechnicalChecksStillRequireSmeReview() throws Exception {
        JsonNode result = write(strategies(true), 1);
        assertThat(result.path("assessment").path("decision").asText())
            .isEqualTo("PRE_SME_TECHNICAL_CHECKS_PASSED_REVIEW_REQUIRED");
        assertThat(result.path("assessment").path("technicalChecksPassed").asBoolean()).isTrue();
        assertThat(result.path("assessment").path("executionCertified").asBoolean()).isFalse();
        assertThat(result.path("assessment").path("smeApprovalStatus").asText()).isEqualTo("NOT_ESTABLISHED_BY_THIS_REPORT");
    }

    @Test
    void failingTechnicalChecksStayReviewRequired() throws Exception {
        JsonNode result = write(strategies(false), 0);
        assertThat(result.path("assessment").path("decision").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(result.path("assessment").path("technicalChecksPassed").asBoolean()).isFalse();
        assertThat(result.path("assessment").path("executionCertified").asBoolean()).isFalse();
    }

    @Test
    void abbreviatedStrategiesCannotClaimTechnicalReadiness() throws Exception {
        JsonNode result = write(Map.of("TRACEABILITY", true, "PAYLOAD", true), 1);
        assertThat(result.path("assessment").path("technicalChecksPassed").asBoolean()).isFalse();
        assertThat(result.path("assessment").path("decision").asText()).isEqualTo("REVIEW_REQUIRED");
    }

    private Map<String, Boolean> strategies(boolean payloadReady) {
        return Map.of("CANONICAL_ANCHORS", true, "INDEPENDENT_BR_BASELINE", true,
                "TEST_CASE_QUALITY", true, "PAYLOAD_COMPLIANCE", payloadReady,
                "COVERAGE_DENOMINATOR", true, "INDEPENDENT_TRACEABILITY", true);
    }

    private JsonNode write(Map<String, Boolean> strategies, int fullChain) throws Exception {
        ValidationResult valid = new ValidationResult("synthetic-evidence");
        AiCoverageAssessmentReport report = new AiCoverageAssessmentReport(1, 1, fullChain, 0, 0, 0,
            100.0, fullChain * 100.0, strategies, valid, valid, valid, valid);
        Path output = directory.resolve("run2-validation.json");
        new Run2SegmentValidationEvidenceWriter().write(
            new Run2SegmentValidationEvidence("100", "synthetic", "version-reviewed", 1, 1, 1, 1, report, null), output);
        return new ObjectMapper().readTree(output.toFile());
    }
}