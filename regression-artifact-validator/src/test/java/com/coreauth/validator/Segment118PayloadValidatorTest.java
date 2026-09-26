package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment118PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** Item 1 baseline tests for source-confirmed Segment 118 (Proprietary Data Load Segment) rules. */
class Segment118PayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void acceptsSiteConfigurationRequest() {
        ObjectNode payload = validRequest("903");
        segment(payload).put("SiteConfigurationData", "SYNTHETIC-SITE-CONFIG-DATA");

        ValidationResult result = new Segment118PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsFuelVolumeRequest() {
        ObjectNode payload = validRequest("905");
        segment(payload).put("FuelVolumeData", "SYNTHETIC-FUEL-VOLUME-DATA");

        ValidationResult result = new Segment118PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsCustomReceiptTextResponse() {
        ObjectNode payload = validRequest("901");
        segment(payload).put("NumberOfReceiptTextLines", "01");

        ValidationResult result = new Segment118PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsDynamicCardTableResponse() {
        ObjectNode payload = validRequest("902");
        segment(payload).put("CardTableData", "SYNTHETIC-CARD-TABLE-DATA");

        ValidationResult result = new Segment118PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsHostDiscountResponse() {
        ObjectNode payload = validRequest("904");
        segment(payload).put("NumberOfDiscounts", "01");

        ValidationResult result = new Segment118PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsWrongSegmentType() {
        ObjectNode payload = validRequest("903");
        segment(payload).put("SegmentType", "100");

        assertRule(payload, "SEG118-R-006");
    }

    @Test
    void rejectsSegmentLengthNotFourDigits() {
        ObjectNode payload = validRequest("903");
        segment(payload).put("SegmentLength", "100");

        assertRule(payload, "SEG118-R-007");
    }

    @Test
    void rejectsSegmentLengthAboveMaximum() {
        ObjectNode payload = validRequest("903");
        segment(payload).put("SegmentLength", "3801");

        assertRule(payload, "SEG118-R-002");
    }

    @Test
    void rejectsInvalidPromptCode() {
        ObjectNode payload = validRequest("903");
        segment(payload).put("PromptCode", "999");

        assertRule(payload, "SEG118-R-011");
    }

    @Test
    void rejectsInvalidPromptCodePending() {
        ObjectNode payload = validRequest("903");
        segment(payload).put("PromptCodePending", "0999");

        assertRule(payload, "SEG118-R-012");
    }

    @Test
    void rejectsInvalidCardTableType() {
        ObjectNode payload = validRequest("903");
        segment(payload).put("CardTableType", "0099");

        assertRule(payload, "SEG118-R-015");
    }

    @Test
    void rejectsMalformedDeviceCardTableVersion() {
        ObjectNode payload = validRequest("903");
        segment(payload).put("DeviceCardTableVersion", "12345");

        assertRule(payload, "SEG118-R-013");
    }

    @Test
    void rejectsMalformedHostDiscountTimestamp() {
        ObjectNode payload = validRequest("903");
        segment(payload).put("HostDiscountTimestamp", "2026");

        assertRule(payload, "SEG118-R-017");
    }

    @Test
    void rejectsMissingSiteConfigurationDataForPromptCode903() {
        ObjectNode payload = validRequest("903");

        assertRule(payload, "SEG118-R-021");
    }

    @Test
    void rejectsSegment100Present() {
        ObjectNode payload = validRequest("903");
        segment(payload).put("SiteConfigurationData", "SYNTHETIC");
        ((ObjectNode) payload.path("Proprietary Data Load Request")).putObject("Standard Message Data Segment");

        assertRule(payload, "SEG118-R-024");
    }

    @Test
    void rejectsMissingProprietaryDataLoadSegment() {
        ObjectNode payload = validRequest("903");
        ((ObjectNode) payload.path("Proprietary Data Load Request")).remove("Proprietary Data Load Segment");

        assertRule(payload, "SEG118-R-024");
    }

    private static void assertRule(ObjectNode payload, String ruleId) {
        ValidationResult result = new Segment118PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains(ruleId));
    }

    private static ObjectNode validRequest(String promptCode) {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("Proprietary Data Load Request");
        request.put("MessageFormatVersionIdentifier", "ATL105");
        request.put("NumberOfSegments", "01");
        ObjectNode segment = request.putObject("Proprietary Data Load Segment");
        segment.put("SegmentType", "118");
        segment.put("SegmentLength", "0100");
        segment.put("SequenceNumber", "000001");
        segment.put("InformationByte", "0");
        segment.put("TerminalIdentifier", "TERM001A12345");
        segment.put("PromptCode", promptCode);
        segment.put("PromptCodePending", "0902");
        segment.put("DeviceCardTableVersion", "00001000020000300004000050000600007");
        segment.put("CardTableLoadVersion", "00000000000000000000000000000000000");
        segment.put("CardTableType", "0002");
        segment.put("LoadControlKey", "SYNTHETICLOADCONTROLKEY");
        segment.put("HostDiscountTimestamp", "202609261200");
        return payload;
    }

    private static ObjectNode segment(ObjectNode payload) {
        return (ObjectNode) payload.path("Proprietary Data Load Request").path("Proprietary Data Load Segment");
    }
}
