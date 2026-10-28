package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment112PayloadValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Segment112PayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Segment112PayloadValidator validator = new Segment112PayloadValidator();

    @Test
    void acceptsResponseFlagAndTwoWellFormedTriadsWithoutCertifyingTableLayouts() {
        ObjectNode response = response("1");
        ObjectNode segment = segment("032");
        addTriad(segment, "001", "006", "123456");
        addTriad(segment, "003", "005", "AVS+A");
        ((ObjectNode) response.path("Financial Transaction Response").path("dataSection2"))
            .set("additionalInformationDataSegment", segment);

        var result = validator.validatePayload(response);

        assertThat(result.errors()).isEmpty();
        assertThat(result.warnings()).anyMatch(warning -> warning.contains("AppendixKLayoutReview"));
    }

    @Test
    void requiresSegment112PresenceToAgreeWithElement115() {
        ObjectNode response = response("0");
        ((ObjectNode) response.path("Financial Transaction Response").path("dataSection2"))
            .set("additionalInformationDataSegment", segment("016"));

        assertThat(validator.validatePayload(response).errors())
                .anyMatch(error -> error.reason().contains("Element 115 = 0 forbids Segment 112"));

        assertThat(validator.validatePayload(response("1")).errors())
                .anyMatch(error -> error.reason().contains("Element 115 = 1 requires Segment 112"));
    }

    @Test
    void enforcesElement118SectionAndOverallSegmentCaps() {
        ObjectNode response = response("1");
        ObjectNode segment = segment("999");
        addTriad(segment, "001", "985", "A".repeat(985));
        ((ObjectNode) response.path("Financial Transaction Response").path("dataSection2"))
            .set("additionalInformationDataSegment", segment);

        var result = validator.validatePayload(response);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("exceeds 984 characters"));
        assertThat(result.errors()).anyMatch(error -> error.reason().contains("exceeds 990 characters"));
        assertThat(result.errors()).anyMatch(error -> error.reason().contains("exceeds 999 characters"));
    }

    @Test
    void reviewGatesLengthsAboveTheConflictingFinancialResponseFieldMaximum() {
        ObjectNode response = response("1");
        ObjectNode segment = segment("360");
        addTriad(segment, "020", "345", "A".repeat(345));
        ((ObjectNode) response.path("Financial Transaction Response").path("dataSection2"))
                .set("additionalInformationDataSegment", segment);

        var result = validator.validatePayload(response);

        assertThat(result.errors()).isEmpty();
        assertThat(result.warnings()).anyMatch(warning -> warning.contains("SEG112-SME-008"));
    }

    @Test
    void rejectsResolvedReservedCodesAndKeepsUnlistedFutureUseReviewGated() {
        ObjectNode reservedResponse = response("1");
        ObjectNode reserved = segment("016");
        addTriad(reserved, "002", "001", "X");
        ((ObjectNode) reservedResponse.path("Financial Transaction Response").path("dataSection2"))
            .set("additionalInformationDataSegment", reserved);
        assertThat(validator.validatePayload(reservedResponse).errors())
                .anyMatch(error -> error.reason().contains("002 is reserved/invalid"));

        ObjectNode unlistedResponse = response("1");
        ObjectNode unlisted = segment("016");
        addTriad(unlisted, "999", "001", "X");
        ((ObjectNode) unlistedResponse.path("Financial Transaction Response").path("dataSection2"))
            .set("additionalInformationDataSegment", unlisted);
        var result = validator.validatePayload(unlistedResponse);
        assertThat(result.errors()).isEmpty();
        assertThat(result.warnings()).anyMatch(warning -> warning.contains("selector 999") && warning.contains("REVIEW_REQUIRED"));
    }

    private static ObjectNode response(String flag) {
        ObjectNode root = MAPPER.createObjectNode();
        ObjectNode response = root.putObject("Financial Transaction Response");
        response.put("additionalInformationDataSegmentFlag", flag);
        response.putObject("dataSection2");
        return root;
    }

    private static ObjectNode segment(String length) {
        ObjectNode segment = MAPPER.createObjectNode();
        segment.put("segmentType", "112").put("segmentLength", length);
        segment.putArray("additionalInformationSections");
        return segment;
    }

    private static void addTriad(ObjectNode segment, String indicator, String length, String value) {
        ObjectNode triad = ((com.fasterxml.jackson.databind.node.ArrayNode) segment.path("additionalInformationSections")).addObject();
        triad.put("additionalInformationIndicator", indicator);
        triad.put("additionalInformationLength", length);
        triad.put("additionalInformation", value);
    }
}