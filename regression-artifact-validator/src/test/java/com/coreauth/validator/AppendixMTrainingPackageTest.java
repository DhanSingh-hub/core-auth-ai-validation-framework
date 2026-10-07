package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment103PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixMTrainingPackageTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path PACKAGE = Path.of("specifications", "ATL105", "test-output", "test-json",
            "appendices", "appendix-m-segment-100-coverage.json");

    @Test
    void validatesBothAppendixMWorkedExamplesAndKeepsTag50ReviewRequired() throws Exception {
        JsonNode packageNode = MAPPER.readTree(PACKAGE.toFile());
        assertThat(packageNode.path("testData")).hasSize(4);
        Segment103PayloadValidator validator = new Segment103PayloadValidator();

        for (int index = 0; index < 2; index++) {
            JsonNode testData = packageNode.path("testData").get(index);
            ValidationResult result = validator.validatePayload(testData.path("payload"));
            assertThat(result.errors()).as(testData.path("id").asText()).isEmpty();
            assertThat(result.warnings()).anyMatch(warning -> warning.contains("SEG103-SME-009"));
            assertThat(testData.path("readiness").asText()).isEqualTo("REVIEW_REQUIRED");
        }
    }

    @Test
    void rejectsAppendixMTotalAndDetailLengthMutations() throws Exception {
        JsonNode testData = MAPPER.readTree(PACKAGE.toFile()).path("testData");
        Segment103PayloadValidator validator = new Segment103PayloadValidator();

        for (int index = 2; index < testData.size(); index++) {
            JsonNode entry = testData.get(index);
            ValidationResult result = validator.validatePayload(entry.path("payload"));
            assertThat(result.errors()).as(entry.path("id").asText())
                    .anyMatch(error -> error.reason().contains(entry.path("expectedRule").asText()));
        }
    }
}