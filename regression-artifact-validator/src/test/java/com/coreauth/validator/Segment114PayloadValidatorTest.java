package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment114PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers {@link Segment114PayloadValidator}, built during the Chapter 12 segment cross-check
 * after discovering Segment 114 had a 13-rule catalog but no independent executable validator.
 * Source: ATL105 Section 12.13.
 */
class Segment114PayloadValidatorTest {
    private final ObjectMapper json = new ObjectMapper();
    private final Segment114PayloadValidator validator = new Segment114PayloadValidator();

    private ObjectNode validPayload() {
        ObjectNode payload = json.createObjectNode();
        ObjectNode segment = payload.putObject("SKU Data Segment");
        segment.put("SegmentType", "114");
        segment.put("SegmentLength", "0022");
        segment.put("SKUData", "012345678901");
        return payload;
    }

    private ValidationResult validate(ObjectNode payload) {
        return validator.validatePayload(payload);
    }

    @Test
    void acceptsAWellFormedSegment() {
        assertThat(validate(validPayload()).errors()).isEmpty();
    }

    @Test
    void absentSegmentIsNotAnErrorBecauseItIsOptional() {
        assertThat(validate(json.createObjectNode()).errors()).isEmpty();
    }

    @Test
    void rejectsWrongSegmentType() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("SKU Data Segment")).put("SegmentType", "108");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG114-R-003"));
    }

    @Test
    void enforcesTheFourDigitLengthPatternAndThe1010Maximum() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("SKU Data Segment")).put("SegmentLength", "012");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG114-R-004"));

        payload = validPayload();
        ((ObjectNode) payload.path("SKU Data Segment")).put("SegmentLength", "1011");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG114-R-005"));

        payload = validPayload();
        ((ObjectNode) payload.path("SKU Data Segment")).put("SegmentLength", "1010");
        ((ObjectNode) payload.path("SKU Data Segment")).put("SKUData", "A".repeat(1000));
        assertThat(validate(payload).errors()).isEmpty();
    }

    @Test
    void requiresSkuDataWithinLengthBound() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("SKU Data Segment")).put("SKUData", "A".repeat(1001));
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG114-R-008"));

        payload = validPayload();
        ((ObjectNode) payload.path("SKU Data Segment")).putNull("SKUData");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG114-R-008"));
    }

    @Test
    void rejectsSegment114InALoyaltyCardTransactionResponse() {
        ObjectNode payload = json.createObjectNode();
        payload.putObject("Loyalty Card Transaction Response").putObject("SKU Data Segment")
                .put("SegmentType", "114").put("SegmentLength", "0012").put("SKUData", "012345678901");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG114-R-013"));
    }

    @Test
    void validatesNestedRepeatedSkuSegmentsAndTheirWireRepresentation() {
        ObjectNode payload = json.createObjectNode();
        var entries = payload.putObject("Loyalty Card Transaction").putArray("SKU Data Segment");
        entries.add(validPayload().path("SKU Data Segment"));
        entries.add(validPayload().path("SKU Data Segment"));
        ObjectNode first = (ObjectNode) entries.get(0);
        first.put("serializedSegment", "114\u001c0022\u001c012345678901\u001c");
        assertThat(validate(payload).errors()).isEmpty();
        first.put("serializedSegment", "1140022012345678901");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG114-R-006"));
        ((ObjectNode) entries.get(1)).put("SegmentLength", "0012");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG114-R-004"));
    }
}
