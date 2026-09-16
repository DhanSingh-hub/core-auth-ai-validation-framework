package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100TransactionTypeOracle;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100TransactionTypeOracleTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String[] EXPECTED_CODES = {"0", "3", "4", "5", "6", "7", "8", "A", "B", "C", "S", "U", "Z"};

    @Test
    void expectedCodeMatrixContainsAllThirteenStandardFinancialCodes() {
        Map<String, String> matrix = new Segment100TransactionTypeOracle().expectedCodeMatrix();

        assertThat(matrix.keySet()).containsExactly(EXPECTED_CODES);
        assertThat(matrix.values()).allMatch(description -> !description.isBlank());
    }

    @Test
    void everyExpectedCodeIsAcceptedInFullAiPayloadShape() {
        Segment100TransactionTypeOracle oracle = new Segment100TransactionTypeOracle();

        for (String code : EXPECTED_CODES) {
            ValidationResult result = oracle.validateAiPayload("AI-TX-" + code, aiPayload(code));
            assertThat(result.errors()).as("transaction code %s", code).isEmpty();
        }
    }

    @Test
    void rejectsMissingFinancialRequest() {
        ObjectNode payload = MAPPER.createObjectNode();

        ValidationResult result = new Segment100TransactionTypeOracle().validateAiPayload("MISSING-REQUEST", payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Financial Request object is required"));
    }

    @Test
    void rejectsMissingTransactionType() {
        ObjectNode payload = aiPayload("0");
        ((ObjectNode) payload.path("Financial Request").path("Standard Segment").path("PromptCode")).remove("TransactionType");

        ValidationResult result = new Segment100TransactionTypeOracle().validateAiPayload("MISSING-CODE", payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("must be one character"));
    }

    @Test
    void rejectsUnknownTransactionType() {
        ValidationResult result = new Segment100TransactionTypeOracle().validateAiPayload("UNKNOWN-CODE", aiPayload("X"));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Unsupported standard financial transaction type: X"));
    }

    @Test
    void rejectsSpecialTransactionCodeWhenStandardFinancialCodeIsExpected() {
        ValidationResult result = new Segment100TransactionTypeOracle().validateAiPayload("SPECIAL-CODE", aiPayload("9"));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Unsupported standard financial transaction type: 9"));
    }

    @Test
    void rejectsWrongSegmentType() {
        ObjectNode payload = aiPayload("0");
        ((ObjectNode) payload.path("Financial Request").path("Standard Segment")).put("SegmentType", "101");

        ValidationResult result = new Segment100TransactionTypeOracle().validateAiPayload("WRONG-SEGMENT", payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SegmentType must be 100"));
    }

    @Test
    void identifiesMultiStepCodesWithoutChangingTheOracle() {
        Segment100TransactionTypeOracle oracle = new Segment100TransactionTypeOracle();

        assertThat(oracle.isMultiStepCode("0")).isTrue();
        assertThat(oracle.isMultiStepCode("4")).isFalse();
        assertThat(oracle.isMultiStepCode("Z")).isTrue();
    }

    private static ObjectNode aiPayload(String transactionType) {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("Financial Request");
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "03");
        ObjectNode standardSegment = request.putObject("Standard Segment");
        standardSegment.put("SegmentType", "100");
        standardSegment.putObject("PromptCode").put("TransactionType", transactionType);
        return payload;
    }
}
