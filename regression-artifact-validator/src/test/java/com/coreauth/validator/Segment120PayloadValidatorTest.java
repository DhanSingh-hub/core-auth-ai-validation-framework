package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment120PayloadValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 1 (Coverage Closure baseline) tests for {@link Segment120PayloadValidator}.
 *
 * <p>Segment 120 (Print Data 2 Segment) is a RESPONSE-side companion segment: it must appear
 * only under a Financial Transaction Response / EMV Financial Transaction Response, in Data
 * Section 3.
 */
class Segment120PayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String BASELINE_PRINT_DATA = "THANK YOU FOR SHOPPING WITH US TODAY"; // 36 chars -> computed 0041

    private ObjectNode payload() {
        ObjectNode root = MAPPER.createObjectNode();
        root.put("messageType", "FINANCIAL_TRANSACTION_RESPONSE");
        ObjectNode response = root.putObject("response");
        ObjectNode section3 = response.putObject("dataSection3");
        ObjectNode segment = section3.putObject("printData2Segment");
        segment.put("segmentType", "120");
        segment.put("segmentLength", "0041");
        segment.put("printData", BASELINE_PRINT_DATA);
        return root;
    }

    private ObjectNode segment(ObjectNode root) {
        return (ObjectNode) root.path("response").path("dataSection3").path("printData2Segment");
    }

    @Test void acceptsValidResponsePayload() {
        assertThat(new Segment120PayloadValidator().validatePayload(payload()).errors()).isEmpty();
    }

    @Test void acceptsPayloadWithoutSegment120AtAll() {
        ObjectNode root = MAPPER.createObjectNode();
        root.putObject("response").putObject("dataSection3");
        assertThat(new Segment120PayloadValidator().validatePayload(root).errors()).isEmpty();
    }

    @Test void rejectsWrongSegmentType() {
        ObjectNode p = payload();
        segment(p).put("segmentType", "999");
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG120-R-001"));
    }

    @Test void rejectsNonTextualSegmentLength() {
        ObjectNode p = payload();
        segment(p).put("segmentLength", 41);
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG120-R-002"));
    }

    @Test void rejectsSegmentLengthWrongWidth() {
        ObjectNode p = payload();
        segment(p).put("segmentLength", "041");
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG120-R-002"));
    }

    @Test void rejectsSegmentLengthValueMismatch() {
        ObjectNode p = payload();
        segment(p).put("segmentLength", "0099");
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG120-R-002"));
    }

    @Test void rejectsMissingPrintData() {
        ObjectNode p = payload();
        segment(p).remove("printData");
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG120-R-003"));
    }

    @Test void rejectsPrintDataOver999Chars() {
        ObjectNode p = payload();
        String oversized = "A".repeat(1000);
        segment(p).put("printData", oversized);
        segment(p).put("segmentLength", String.format("%04d", 5 + oversized.length()));
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG120-R-004"));
    }

    @Test void acceptsPrintDataAtExactly999Chars() {
        ObjectNode p = payload();
        String maxSize = "B".repeat(999);
        segment(p).put("printData", maxSize);
        segment(p).put("segmentLength", String.format("%04d", 5 + maxSize.length()));
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).isEmpty();
    }

    @Test void rejectsWireFormatWithWrongSeparatorCount() {
        ObjectNode p = payload();
        segment(p).put("wireFormat", "120\u001c0041" + BASELINE_PRINT_DATA);
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG120-R-005"));
    }

    @Test void rejectsWireFormatWithTrailingSeparator() {
        ObjectNode p = payload();
        segment(p).put("wireFormat", "120\u001c0041\u001c" + BASELINE_PRINT_DATA + "\u001c");
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG120-R-005"));
    }

    @Test void acceptsWireFormatWithNoTrailingSeparator() {
        ObjectNode p = payload();
        // Unlike Segment 111 (which requires a trailing separator), Segment 120 must NOT have one.
        segment(p).put("wireFormat", "120\u001c0041\u001c" + BASELINE_PRINT_DATA);
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).isEmpty();
    }

    @Test void rejectsSegment120UnderRequestContainer() {
        ObjectNode root = MAPPER.createObjectNode();
        root.put("messageType", "FINANCIAL_TRANSACTION_RESPONSE");
        ObjectNode request = root.putObject("request");
        ObjectNode section3 = request.putObject("dataSection3");
        ObjectNode segment = section3.putObject("printData2Segment");
        segment.put("segmentType", "120");
        segment.put("segmentLength", "0041");
        segment.put("printData", BASELINE_PRINT_DATA);
        assertThat(new Segment120PayloadValidator().validatePayload(root).errors()).anyMatch(e -> e.reason().contains("SEG120-R-006"));
    }

    @Test void rejectsInvalidMessageFamily() {
        ObjectNode p = payload();
        ((ObjectNode) p).put("messageType", "AUTHORIZATION_REQUEST");
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG120-R-006"));
    }

    @Test void acceptsWhenMessageTypeAbsent() {
        ObjectNode p = payload();
        p.remove("messageType");
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).isEmpty();
    }

    @Test void acceptsEmvMessageFamily() {
        ObjectNode p = payload();
        p.put("messageType", "emv_financial_transaction_response");
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).isEmpty();
    }

    @Test void acceptsMessageFamilyFromTestControls() {
        ObjectNode p = payload();
        p.remove("messageType");
        p.putObject("testControls").put("messageFamily", "FINANCIAL_TRANSACTION_RESPONSE");
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).isEmpty();
    }

    @Test void rejectsWhenSegment120NotLastInDataSection3() {
        ObjectNode p = payload();
        ObjectNode section3 = (ObjectNode) p.path("response").path("dataSection3");
        section3.putObject("segment134").put("segmentType", "134");
        // printData2Segment was inserted before segment134 above, so it is no longer last.
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG120-R-007"));
    }

    @Test void acceptsWhenSegment120IsLastAmongMultipleCompanionSegments() {
        ObjectNode root = MAPPER.createObjectNode();
        root.put("messageType", "EMV_FINANCIAL_TRANSACTION_RESPONSE");
        ObjectNode response = root.putObject("response");
        ObjectNode section3 = response.putObject("dataSection3");
        section3.putObject("emvResponseDataSegment").put("segmentType", "131");
        ObjectNode segment = section3.putObject("printData2Segment");
        segment.put("segmentType", "120");
        segment.put("segmentLength", "0041");
        segment.put("printData", BASELINE_PRINT_DATA);
        assertThat(new Segment120PayloadValidator().validatePayload(root).errors()).isEmpty();
    }

    @Test void acceptsBlackhawkDelimitedPrintData() {
        ObjectNode p = payload();
        String receipt = "BLACKHAWK ACTIVATION\\CARD VALUE 25.00\\PHONE 5551234567\\THANK YOU";
        segment(p).put("printData", receipt);
        segment(p).put("segmentLength", String.format("%04d", 5 + receipt.length()));
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).isEmpty();
    }

    @Test void acceptsExplicitSegmentOrderWithSegment120Last() {
        ObjectNode p = payload();
        ObjectNode section3 = (ObjectNode) p.path("response").path("dataSection3");
        section3.putArray("segmentOrder").add("112").add("115").add("120");
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).isEmpty();
    }

    @Test void rejectsExplicitSegmentOrderWithSegment120NotLast() {
        ObjectNode p = payload();
        ObjectNode section3 = (ObjectNode) p.path("response").path("dataSection3");
        section3.putArray("segmentOrder").add("112").add("120").add("134").add("136");
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG120-R-007"));
    }

    @Test void explicitSegmentOrderTakesPrecedenceOverKeyOrder() {
        // Key order alone would flag this (printData2Segment is declared before segment134 below),
        // but the explicit segmentOrder array says 120 is last, so it must be trusted instead.
        ObjectNode p = payload();
        ObjectNode section3 = (ObjectNode) p.path("response").path("dataSection3");
        section3.putArray("segmentOrder").add("112").add("134").add("120");
        section3.putObject("segment134").put("segmentType", "134");
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).isEmpty();
    }

    @Test void acceptsLegacyPascalCaseFieldNames() throws Exception {
        JsonNode p = MAPPER.readTree("""
            {"messageType":"FINANCIAL_TRANSACTION_RESPONSE","response":{"dataSection3":{"printData2Segment":{"SegmentType":"120","SegmentLength":"0041","PrintData":"THANK YOU FOR SHOPPING WITH US TODAY"}}}}
            """);
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).isEmpty();
    }

    @Test void acceptsAlternateFinancialTransactionResponseContainerName() throws Exception {
        JsonNode p = MAPPER.readTree("""
            {"messageType":"FINANCIAL_TRANSACTION_RESPONSE","Financial Transaction Response":{"dataSection3":{"printData2Segment":{"segmentType":"120","segmentLength":"0041","printData":"THANK YOU FOR SHOPPING WITH US TODAY"}}}}
            """);
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).isEmpty();
    }

    @Test void rejectsNonObjectRoot() throws Exception {
        JsonNode p = MAPPER.readTree("[]");
        assertThat(new Segment120PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("must be an object"));
    }

    @Test void validateFileRejectsNullFile() {
        assertThat(new Segment120PayloadValidator().validateFile(null).errors()).anyMatch(e -> e.reason().contains("required"));
    }
}
