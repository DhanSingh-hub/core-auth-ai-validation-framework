package com.coreauth.validator;

import com.coreauth.validator.canonical.AdditionalInformationTransactionFlowValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AdditionalInformationTransactionFlowValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final AdditionalInformationTransactionFlowValidator validator = new AdditionalInformationTransactionFlowValidator();

    @Test
    void coordinatesRequestSegment111AndResponseSegment112WithoutConflatingDirections() {
        ObjectNode transaction = MAPPER.createObjectNode();
        ObjectNode request = transaction.putObject("request");
        ObjectNode segment111 = request.putObject("dataSection3").putObject("variableInfoSegment");
        segment111.put("segmentType", "111").put("segmentLength", "018");
        segment111.putArray("variableInformationSections").addObject()
                .put("variableInformationIndicator", "005")
                .put("variableInformationLength", "003")
                .put("variableInformation", "900");

        ObjectNode response = transaction.putObject("response").putObject("Financial Transaction Response");
        response.put("additionalInformationDataSegmentFlag", "1");
        ObjectNode segment112 = response.putObject("dataSection2").putObject("additionalInformationDataSegment");
        segment112.put("segmentType", "112").put("segmentLength", "021");
        segment112.putArray("additionalInformationSections").addObject()
                .put("additionalInformationIndicator", "001")
                .put("additionalInformationLength", "006")
                .put("additionalInformation", "123456");

        var result = validator.validatePayload(transaction);

        assertThat(result.errors()).isEmpty();
        assertThat(result.warnings()).anyMatch(warning -> warning.contains("AppendixKLayoutReview"));
    }

    @Test
    void rejectsSegmentsOnTheWrongTransactionSide() {
        ObjectNode transaction = MAPPER.createObjectNode();
        ObjectNode request = transaction.putObject("request");
        request.putObject("additionalInformationDataSegment").put("segmentType", "112");
        ObjectNode response = transaction.putObject("response");
        response.put("additionalInformationDataSegmentFlag", "0");
        response.putObject("variableInfoSegment").put("segmentType", "111");

        var result = validator.validatePayload(transaction);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Segment 112 is response-only"));
        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Segment 111 is request-side"));
    }

    @Test
    void appliesImplementedAppendixIPredicatesAndLeavesOtherTablesUnassertable() {
        ObjectNode invalidKnown = requestWithVariableInfo("005", "02X");
        var knownResult = validator.validateRequestPayload(invalidKnown);
        assertThat(knownResult.errors()).anyMatch(error -> error.reason().contains("APPI-T005-R001"));

        ObjectNode unknownPredicate = requestWithVariableInfo("010", "ABC");
        var unknownResult = validator.validateRequestPayload(unknownPredicate);
        assertThat(unknownResult.errors()).isEmpty();
        assertThat(unknownResult.warnings()).anyMatch(warning -> warning.contains("APPI-T010 NOT_ASSERTABLE"));
    }

    @Test
    void rejectsUnknownAppendixISelectorsButReviewGatesSourceUnlocatedGaps() {
        var unknownResult = validator.validateRequestPayload(requestWithVariableInfo("999", "ABC"));
        assertThat(unknownResult.errors()).anyMatch(error -> error.reason().contains("Table ID 999 is not listed"));

        var unlocatedResult = validator.validateRequestPayload(requestWithVariableInfo("023", "ABC"));
        assertThat(unlocatedResult.errors()).isEmpty();
        assertThat(unlocatedResult.warnings()).anyMatch(warning -> warning.contains("023 REVIEW_REQUIRED"));
    }

    private static ObjectNode requestWithVariableInfo(String tableId, String data) {
        ObjectNode root = MAPPER.createObjectNode();
        ObjectNode segment = root.putObject("dataSection3").putObject("variableInfoSegment");
        segment.put("segmentType", "111").put("segmentLength", "018");
        segment.putArray("variableInformationSections").addObject()
                .put("variableInformationIndicator", tableId)
                .put("variableInformationLength", String.format("%03d", data.length()))
                .put("variableInformation", data);
        return root;
    }
}