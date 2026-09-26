package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment110PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** Item 1 baseline tests for source-confirmed Segment 110 (Check Data Segment) request rules. */
class Segment110PayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void acceptsMicrReadPersonalCheckRequest() {
        ObjectNode payload = validRequest();
        segment(payload).put("CheckType", "P");

        ValidationResult result = new Segment110PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsManuallyKeyedCompanyCheckRequest() {
        ObjectNode payload = validRequest();
        ObjectNode segment = segment(payload);
        segment.put("CheckType", "C");
        segment.put("DriversLicense", "SYN12345");
        segment.put("StateCode", "CA");
        segment.put("DateOfBirth", "01011990");
        segment.put("CheckNumber", "1024");

        ValidationResult result = new Segment110PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsAlternateMicrFormatRequest() {
        ObjectNode payload = validRequest();
        segment(payload).put("AlternateMicrIndicator", "Y");

        ValidationResult result = new Segment110PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsWrongSegmentType() {
        ObjectNode payload = validRequest();
        segment(payload).put("SegmentType", "100");

        assertRule(payload, "SEG110-R-004");
    }

    @Test
    void rejectsSegmentLengthAboveMaximum() {
        ObjectNode payload = validRequest();
        segment(payload).put("SegmentLength", "169");

        assertRule(payload, "SEG110-R-002");
    }

    @Test
    void rejectsMissingMicrData() {
        ObjectNode payload = validRequest();
        segment(payload).remove("MICRData");

        assertRule(payload, "SEG110-R-008");
    }

    @Test
    void rejectsInvalidCheckType() {
        ObjectNode payload = validRequest();
        segment(payload).put("CheckType", "X");

        assertRule(payload, "SEG110-R-012");
    }

    @Test
    void rejectsInvalidStateCode() {
        ObjectNode payload = validRequest();
        ObjectNode segment = segment(payload);
        segment.put("DriversLicense", "SYN12345");
        segment.put("StateCode", "ZZ");

        assertRule(payload, "SEG110-R-010");
    }

    @Test
    void rejectsDriversLicenseWithoutStateCode() {
        ObjectNode payload = validRequest();
        segment(payload).put("DriversLicense", "SYN12345");

        assertRule(payload, "SEG110-R-010");
    }

    @Test
    void rejectsInvalidAlternateMicrIndicatorValue() {
        ObjectNode payload = validRequest();
        segment(payload).put("AlternateMicrIndicator", "N");

        assertRule(payload, "SEG110-R-017");
    }

    @Test
    void rejectsMissingCheckDataSegment() {
        ObjectNode payload = validRequest();
        ((ObjectNode) payload.path("ECA TeleCheck Service Transaction Request")).remove("Check Data Segment");

        assertRule(payload, "SEG110-R-001");
    }

    private static void assertRule(ObjectNode payload, String ruleId) {
        ValidationResult result = new Segment110PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains(ruleId));
    }

    private static ObjectNode validRequest() {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("ECA TeleCheck Service Transaction Request");
        request.put("MessageFormatVersionIdentifier", "ATL105");
        request.put("NumberOfSegments", "03");
        request.putObject("Standard Message Data Segment");
        ObjectNode segment = request.putObject("Check Data Segment");
        segment.put("SegmentType", "110");
        segment.put("SegmentLength", "060");
        segment.put("MICRData", "T123456780T0301123D456D7O");
        segment.put("CheckType", "P");
        return payload;
    }

    private static ObjectNode segment(ObjectNode payload) {
        return (ObjectNode) payload.path("ECA TeleCheck Service Transaction Request").path("Check Data Segment");
    }
}
