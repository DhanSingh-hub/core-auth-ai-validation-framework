package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment116PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 1 partial-baseline tests for Segment 116 (TransArmor Load Data Segment).
 *
 * <p>Segment 116's own specification sections (12.15, 11.7.5) are stubs redirecting to an
 * external TransArmor document not present in this workspace. These tests therefore only cover
 * what is independently confirmed or safely pattern-derived: the request envelope, Segment Type,
 * Segment Length format/maximum, and the three known TransArmor Load Response fields.
 */
class Segment116PayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void acceptsMinimalValidKeyLoadRequest() {
        ValidationResult result = new Segment116PayloadValidator().validatePayload(validRequest());

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsRequestWithApprovedResponse() {
        ObjectNode payload = validRequest();
        ObjectNode response = payload.putObject("TransArmor Load Response");
        response.put("KeyId", "SYNTHETIC01");
        response.put("KeyDataLength", "016");
        response.put("KeyData", "SYNTHETICKEYDATA");

        ValidationResult result = new Segment116PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsWrongSegmentType() {
        ObjectNode payload = validRequest();
        segment(payload).put("SegmentType", "100");

        assertRule(payload, "SEG116-R-003");
    }

    @Test
    void rejectsSegmentLengthAboveMaximum() {
        ObjectNode payload = validRequest();
        segment(payload).put("SegmentLength", "051");

        assertRule(payload, "SEG116-R-002");
    }

    @Test
    void rejectsMalformedSegmentLength() {
        ObjectNode payload = validRequest();
        segment(payload).put("SegmentLength", "5");

        assertRule(payload, "SEG116-R-004");
    }

    @Test
    void rejectsMissingNumberOfSegments() {
        ObjectNode payload = validRequest();
        ((ObjectNode) payload.path("TransArmor PKI Encryption and Tokenization Load Request")).remove("NumberOfSegments");

        assertRule(payload, "SEG116-R-005");
    }

    @Test
    void rejectsMissingTransArmorLoadDataSegment() {
        ObjectNode payload = validRequest();
        ((ObjectNode) payload.path("TransArmor PKI Encryption and Tokenization Load Request")).remove("TransArmor Load Data Segment");

        assertRule(payload, "SEG116-R-001");
    }

    @Test
    void rejectsInvalidKeyIdLength() {
        ObjectNode payload = validRequest();
        ObjectNode response = payload.putObject("TransArmor Load Response");
        response.put("KeyId", "TOOSHORT");
        response.put("KeyDataLength", "010");
        response.put("KeyData", "SYNTHETIC");

        assertRule(payload, "SEG116-R-007");
    }

    @Test
    void rejectsKeyDataLengthAboveThreeDigits() {
        ObjectNode payload = validRequest();
        ObjectNode response = payload.putObject("TransArmor Load Response");
        response.put("KeyId", "SYNTHETIC01");
        response.put("KeyDataLength", "1000");
        response.put("KeyData", "SYNTHETIC");

        assertRule(payload, "SEG116-R-007");
    }

    private static void assertRule(ObjectNode payload, String ruleId) {
        ValidationResult result = new Segment116PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains(ruleId));
    }

    private static ObjectNode validRequest() {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("TransArmor PKI Encryption and Tokenization Load Request");
        request.put("MessageFormatVersionIdentifier", "ATL105");
        request.put("NumberOfSegments", "01");
        ObjectNode segment = request.putObject("TransArmor Load Data Segment");
        segment.put("SegmentType", "116");
        segment.put("SegmentLength", "050");
        return payload;
    }

    private static ObjectNode segment(ObjectNode payload) {
        return (ObjectNode) payload.path("TransArmor PKI Encryption and Tokenization Load Request").path("TransArmor Load Data Segment");
    }
}
