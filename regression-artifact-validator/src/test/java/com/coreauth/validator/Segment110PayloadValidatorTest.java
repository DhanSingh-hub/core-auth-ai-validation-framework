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
        request(payload).remove("Check Data Segment");

        assertRule(payload, "SEG110-R-001");
    }

    @Test
    void acceptsEcaRequestWithConditionalSegment113() {
        for (String value : new String[] {"4", "04"}) {
            ObjectNode payload = validRequest();
            request(payload).put("NumSegments", value);
            request(payload).putObject("ECA/TeleCheck Data Segment").put("SegmentType", "113");

            assertThat(new Segment110PayloadValidator().validatePayload(payload).errors()).as(value).isEmpty();
        }
    }

    @Test
    void rejectsNumSegmentsOutsideEcaRange() {
        for (String value : new String[] {"2", "05"}) {
            ObjectNode payload = validRequest();
            request(payload).put("NumSegments", value);

            assertThat(new Segment110PayloadValidator().validatePayload(payload).errors())
                    .as(value)
                    .anyMatch(error -> error.reason().contains("SEG110-R-021") && error.reason().contains("must be 3-4"));
        }
    }

    @Test
    void rejectsNumSegmentsThatDoNotMatchSegmentsPresent() {
        ObjectNode payload = validRequest();
        request(payload).put("NumSegments", "04");

        assertThat(new Segment110PayloadValidator().validatePayload(payload).errors())
                .anyMatch(error -> error.reason().contains("SEG110-R-021") && error.reason().contains("contains 3 segments"));
    }

    @Test
    void rejectsMissingOrNonNumericNumSegments() {
        ObjectNode missing = validRequest();
        request(missing).remove("NumSegments");
        ObjectNode nonNumeric = validRequest();
        request(nonNumeric).put("NumSegments", "123");

        assertThat(new Segment110PayloadValidator().validatePayload(missing).errors())
                .anyMatch(error -> error.reason().contains("SEG110-R-021") && error.reason().contains("is required"));
        assertThat(new Segment110PayloadValidator().validatePayload(nonNumeric).errors())
                .anyMatch(error -> error.reason().contains("one or two digits"));
    }

    @Test
    void rejectsMissingVariableInformationSegment() {
        ObjectNode payload = validRequest();
        request(payload).remove("Variable Information Data Segment");
        request(payload).put("NumSegments", "02");

        assertThat(new Segment110PayloadValidator().validatePayload(payload).errors())
                .anyMatch(error -> error.reason().contains("SEG110-R-021") && error.reason().contains("(111) is required"));
    }

    @Test
    void rejectsSegmentNotListedForEcaRequest() {
        ObjectNode payload = validRequest();
        request(payload).put("NumSegments", "04");
        request(payload).putObject("Loyalty Card Data Segment").put("SegmentType", "108");

        assertThat(new Segment110PayloadValidator().validatePayload(payload).errors())
                .anyMatch(error -> error.reason().contains("SEG110-R-021") && error.reason().contains("Segment 108"));
    }

    private static ObjectNode request(ObjectNode payload) {
        return (ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request");
    }

    private static void assertRule(ObjectNode payload, String ruleId) {
        ValidationResult result = new Segment110PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains(ruleId));
    }

    private static ObjectNode validRequest() {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("ECA/TeleCheck Service Transaction Request");
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "03");
        request.putObject("Standard Segment").put("SegmentType", "100");
        ObjectNode segment = request.putObject("Check Data Segment");
        segment.put("SegmentType", "110");
        segment.put("SegmentLength", "060");
        segment.put("MICRData", "T123456780T0301123D456D7O");
        segment.put("CheckType", "P");
        request.putObject("Variable Information Data Segment").put("SegmentType", "111");
        return payload;
    }

    private static ObjectNode segment(ObjectNode payload) {
        return (ObjectNode) request(payload).path("Check Data Segment");
    }
}
