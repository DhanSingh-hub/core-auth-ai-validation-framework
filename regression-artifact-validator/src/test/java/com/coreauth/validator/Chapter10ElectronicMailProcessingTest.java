package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter11MessageLayoutValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.canonical.Segment109PayloadValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter10ElectronicMailProcessingTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Segment109PayloadValidator validator = new Segment109PayloadValidator();

    @Test
    void retrievalUsesItsOwnPromptAndExactByteBoundary() {
        for (String operation : new String[]{"RETRIEVAL", "PROPRIETARY_RETRIEVAL"}) {
            String prompt = "RETRIEVAL".equals(operation) ? "981" : "996";
            ObjectNode input = observation(operation, prompt).put("printData", "A".repeat(750));
            assertThat(validator.validateProcessingObservation(input).errors()).isEmpty();
            input.put("printData", "A".repeat(751));
            assertThat(validator.validateProcessingObservation(input).errors())
                    .anyMatch(error -> error.reason().contains("SEG109-R-019"));
            input.put("promptCode", "995");
            assertThat(validator.validateProcessingObservation(input).errors()).hasSize(2);
        }
    }

    @Test
    void submissionUsesItsOwnPromptAndExactByteBoundary() {
        ObjectNode input = observation("SUBMISSION", "995").put("submissionData", "A".repeat(217));
        assertThat(validator.validateProcessingObservation(input).errors()).isEmpty();
        input.put("submissionData", "A".repeat(218));
        assertThat(validator.validateProcessingObservation(input).errors())
                .anyMatch(error -> error.reason().contains("SEG109-R-020"));
        input.put("promptCode", "981");
        assertThat(validator.validateProcessingObservation(input).errors()).hasSize(2);
    }

    @Test
    void malformedObservationsAndNumericPromptCoercionAreRejected() {
        assertThat(validator.validateProcessingObservation(null).errors()).isNotEmpty();
        assertThat(validator.validateProcessingObservation(mapper.createArrayNode()).errors()).isNotEmpty();
        assertThat(validator.validateProcessingObservation(observation("UNKNOWN", "981")).errors()).isNotEmpty();
        ObjectNode input = observation("RETRIEVAL", "981");
        input.put("promptCode", 981);
        input.put("printData", 750);
        assertThat(validator.validateProcessingObservation(input).errors()).hasSize(2);
    }

    @Test
    void unobservedAndUnknownEncodingStayReviewRequired() {
        assertThat(validator.validateProcessingObservation(observation("RETRIEVAL", "981")).warnings())
                .anyMatch(warning -> warning.contains("not observed"));
        ObjectNode input = observation("RETRIEVAL", "981").put("printData", "\u00e9".repeat(751));
        var result = validator.validateProcessingObservation(input);
        assertThat(result.errors()).isEmpty();
        assertThat(result.warnings()).anyMatch(warning -> warning.contains("transport-encoding"));
    }

    @Test
    void chapterElevenDispatchesProcessingAndRejectsContradictoryPrompt() throws Exception {
        ObjectNode envelope = mapper.createObjectNode().put("specificationVersion", "2026-3")
                .put("messageFamily", "Electronic Mail Request").put("completeness", "PARTIAL");
        envelope.putObject("elements").put("55", "ATL105").put("63", "1");
        ObjectNode segment = envelope.putArray("segments").addObject().put("segment", "109").put("dataSection", 2);
        segment.set("processingObservation", observation("SUBMISSION", "981"));
        assertThat(new Chapter11MessageLayoutValidator().validate(envelope).status()).isEqualTo(Status.INVALID);
        segment.set("processingObservation", observation("SUBMISSION", "995").put("submissionData", "A".repeat(217)));
        assertThat(new Chapter11MessageLayoutValidator().validate(envelope).status()).isEqualTo(Status.REVIEW_REQUIRED);
        segment.putObject("observation").putObject("elements").put("78", "981");
        assertThat(new Chapter11MessageLayoutValidator().validate(envelope).findings())
                .anyMatch(finding -> finding.reason().contains("contradicts"));
    }

    private ObjectNode observation(String operation, String prompt) {
        return mapper.createObjectNode().put("operation", operation).put("promptCode", prompt);
    }
}
