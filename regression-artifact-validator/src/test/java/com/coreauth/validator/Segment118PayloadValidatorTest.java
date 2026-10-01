package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment118PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/** Item 1 tests for Segment 118 core fields, envelopes, and Prompt Code payload rules. */
class Segment118PayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path FIXTURE_DIRECTORY = Path.of(
        "specifications", "ATL105", "test-input", "ai-solution", "test-data", "proprietary-data-load");

    @Test
    void acceptsEverySyntheticPromptFixture() throws Exception {
        List<Path> fixtures;
        try (var files = Files.list(FIXTURE_DIRECTORY)) {
            fixtures = files.filter(path -> path.getFileName().toString().endsWith(".valid.json")).sorted().toList();
        }
        assertThat(fixtures).hasSize(6);
        for (Path fixture : fixtures) {
            JsonNode payload = MAPPER.readTree(fixture.toFile());
            assertThat(new Segment118PayloadValidator().validatePayload(payload).errors())
                .as(fixture.toString()).isEmpty();
        }
    }

    @Test
    void requiresEachProprietaryLoadResponseEnvelopeField() {
        for (String field : new String[]{"ResponseCode", "DownloadIndicator", "InitiationDate", "InitiationTime", "SequenceNumber"}) {
            ObjectNode payload = validResponse("902");
            ((ObjectNode) payload.path("Proprietary Data Load Response")).remove(field);
            assertRule(payload, "SEG118-R-025");
        }
    }

    @Test
    void requiresRequestMessageTypeAndExactlyOneSegment() {
        ObjectNode missingMessageType = validRequest("903");
        ((ObjectNode) missingMessageType.path("Proprietary Data Load Request")).remove("MessageType");
        assertRule(missingMessageType, "SEG118-R-024");

        ObjectNode incorrectSegmentCount = validRequest("903");
        ((ObjectNode) incorrectSegmentCount.path("Proprietary Data Load Request")).put("NumSegments", "2");
        assertRule(incorrectSegmentCount, "SEG118-R-024");
    }

    @Test
    void requiresEveryUnconditionalSegment118CoreField() {
        String[][] requiredFieldsAndRules = {
            {"SegmentType", "SEG118-R-006"}, {"SegmentLength", "SEG118-R-007"},
            {"SequenceNumber", "SEG118-R-008"}, {"InformationByte", "SEG118-R-009"},
            {"TerminalIdentifier", "SEG118-R-010"}, {"PromptCode", "SEG118-R-011"},
            {"PromptCodePending", "SEG118-R-012"}, {"DeviceCardTableVersion", "SEG118-R-013"},
            {"CardTableLoadVersion", "SEG118-R-014"}, {"CardTableType", "SEG118-R-015"},
            {"LoadControlKey", "SEG118-R-016"}, {"HostDiscountTimestamp", "SEG118-R-017"}
        };
        for (String[] fieldAndRule : requiredFieldsAndRules) {
            ObjectNode payload = validRequest("903");
            segment(payload).remove(fieldAndRule[0]);
            assertRule(payload, fieldAndRule[1]);
        }
    }

    @Test
    void acceptsDocumentedProprietaryLoadResponseCodes() {
        for (String responseCode : new String[]{"H", "O", "T", "U", "X", "Y"}) {
            ObjectNode payload = validResponse("902");
            ((ObjectNode) payload.path("Proprietary Data Load Response")).put("ResponseCode", responseCode);
            segment(payload).put("CardTableData", "DATA");
            assertNoErrors(payload);
        }
    }

    @Test
    void rejectsTotalsPendingCodesAsProprietaryLoadResponseCodes() {
        for (String responseCode : new String[]{"V", "W"}) {
            ObjectNode payload = validResponse("901");
            ((ObjectNode) payload.path("Proprietary Data Load Response")).put("ResponseCode", responseCode);
            ObjectNode segment = segment(payload);
            segment.put("StartDate", "20260101"); segment.put("StartTime", "0000");
            segment.put("EndDate", "20261231"); segment.put("EndTime", "2359");
            segment.put("NumberOfReceiptTextLines", "01");
            segment.putArray("ReceiptTextLines").addObject().put("ReceiptTextDataLength", "01").put("ReceiptTextData", "X");
            assertRule(payload, "SEG118-R-026");
        }
    }

    @Test
    void rejectsUndocumentedProprietaryLoadResponseCode() {
        ObjectNode payload = validResponse("902");
        ((ObjectNode) payload.path("Proprietary Data Load Response")).put("ResponseCode", "Z");
        segment(payload).put("CardTableData", "DATA");
        assertRule(payload, "SEG118-R-026");
    }

    @Test
    void acceptsSiteConfigurationRequest() {
        ObjectNode payload = validRequest("903");
        segment(payload).put("SiteConfigurationData", "SYNTHETIC-SITE-CONFIG-DATA");
        assertNoErrors(payload);
    }

    @Test
    void acceptsFinalSiteConfigurationRequestWithoutData() {
        ObjectNode payload = validRequest("903");
        segment(payload).put("BlockNumber", "000");
        assertNoErrors(payload);
    }

    @Test
    void acceptsFuelVolumeRequest() {
        ObjectNode payload = validRequest("905");
        segment(payload).put("FuelVolumeData", "SYNTHETIC-FUEL-VOLUME-DATA");
        assertNoErrors(payload);
    }

    @Test
    void acceptsCustomReceiptTextResponse() {
        ObjectNode payload = validResponse("901");
        ObjectNode segment = segment(payload);
        segment.put("StartDate", "20260101");
        segment.put("StartTime", "0000");
        segment.put("EndDate", "20261231");
        segment.put("EndTime", "2359");
        segment.put("NumberOfReceiptTextLines", "01");
        segment.putArray("ReceiptTextLines").addObject()
            .put("ReceiptTextDataLength", "05").put("ReceiptTextData", "HELLO");
        assertNoErrors(payload);
    }

    @Test
    void warnsRatherThanRejectingReceiptTextLengthMismatchPendingSmeDecision() {
        ObjectNode payload = validResponse("901");
        ObjectNode segment = segment(payload);
        segment.put("StartDate", "20260101"); segment.put("StartTime", "0000");
        segment.put("EndDate", "20261231"); segment.put("EndTime", "2359");
        segment.put("NumberOfReceiptTextLines", "01");
        segment.putArray("ReceiptTextLines").addObject()
            .put("ReceiptTextDataLength", "20").put("ReceiptTextData", "HELLO");

        ValidationResult result = new Segment118PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).isEmpty();
        assertThat(result.warnings()).anyMatch(warning -> warning.contains("SEG118-SME-002"));
    }

    @Test
    void acceptsCustomReceiptTextAt220ByteLimit() {
        ObjectNode payload = validReceiptText(18, 18, 18, 18, 18, 18, 18, 18, 18, 12);

        assertNoErrors(payload);
    }

    @Test
    void rejectsCustomReceiptTextAt221Bytes() {
        ObjectNode payload = validReceiptText(18, 18, 18, 18, 18, 18, 18, 18, 18, 13);

        assertRule(payload, "SEG118-R-019");
    }

    @Test
    void acceptsDynamicCardTableResponse() {
        ObjectNode payload = validResponse("902");
        segment(payload).put("CardTableData", "SYNTHETIC-CARD-TABLE-DATA");
        assertNoErrors(payload);
    }

    @Test
    void enforces3600ByteMaximumForPayloadCodes902903And905() {
        for (String promptCode : new String[]{"902", "903", "905"}) {
            boolean request = "903".equals(promptCode) || "905".equals(promptCode);
            String field = switch (promptCode) {
                case "902" -> "CardTableData";
                case "903" -> "SiteConfigurationData";
                default -> "FuelVolumeData";
            };
            ObjectNode atLimit = request ? validRequest(promptCode) : validResponse(promptCode);
            segment(atLimit).put(field, "X".repeat(3600));
            assertNoErrors(atLimit);

            ObjectNode overLimit = request ? validRequest(promptCode) : validResponse(promptCode);
            segment(overLimit).put(field, "X".repeat(3601));
            assertRule(overLimit, promptCode.equals("902") ? "SEG118-R-020" : promptCode.equals("903") ? "SEG118-R-021" : "SEG118-R-023");
        }
    }

    @Test
    void acceptsHostDiscountResponse() {
        ObjectNode payload = validResponse("904");
        segment(payload).put("NumberOfDiscounts", "01");
        addDiscount(segment(payload).putArray("Discounts").addObject());
        assertNoErrors(payload);
    }

    @Test
    void acceptsHostDiscountRepeatedBlockAt3600ByteBoundary() {
        ObjectNode payload = validResponse("904");
        ObjectNode segment = segment(payload);
        segment.put("NumberOfDiscounts", "44");
        var discounts = segment.putArray("Discounts");
        for (int index = 0; index < 44; index++) addDiscount(discounts.addObject());
        assertNoErrors(payload);
    }

    @Test
    void rejectsHostDiscountRepeatedBlockAbove3600ByteLimit() {
        ObjectNode payload = validResponse("904");
        ObjectNode segment = segment(payload);
        segment.put("NumberOfDiscounts", "45");
        var discounts = segment.putArray("Discounts");
        for (int index = 0; index < 45; index++) addDiscount(discounts.addObject());
        ValidationResult result = new Segment118PayloadValidator().validatePayload(payload);
        assertThat(result.errors()).anyMatch(error -> error.reason().contains("exceeds 3,600 bytes"));
    }

    @Test
    void acceptsEachPromptCodeOnlyInItsDocumentedDirection() {
        for (String code : new String[]{"903", "905"}) {
            ObjectNode payload = validRequest(code);
            segment(payload).put(code.equals("903") ? "SiteConfigurationData" : "FuelVolumeData", "DATA");
            assertNoErrors(payload);
        }
        for (String code : new String[]{"901", "902", "904"}) {
            ObjectNode payload = validResponse(code);
            ObjectNode segment = segment(payload);
            if ("901".equals(code)) {
                segment.put("StartDate", "20260101"); segment.put("StartTime", "0000");
                segment.put("EndDate", "20261231"); segment.put("EndTime", "2359");
                segment.put("NumberOfReceiptTextLines", "01");
                segment.putArray("ReceiptTextLines").addObject().put("ReceiptTextDataLength", "01").put("ReceiptTextData", "X");
            } else if ("902".equals(code)) segment.put("CardTableData", "DATA");
            else { segment.put("NumberOfDiscounts", "01"); addDiscount(segment.putArray("Discounts").addObject()); }
            assertNoErrors(payload);
        }
    }

    @Test
    void rejectsResponsePromptCodeInRequest() { assertRule(validRequest("901"), "SEG118-R-019"); }

    @Test
    void rejectsRequestPromptCodeInResponse() { assertRule(validResponse("903"), "SEG118-R-021"); }

    @Test
    void rejectsReceiptTextCountMismatch() {
        ObjectNode payload = validResponse("901");
        ObjectNode segment = segment(payload);
        segment.put("StartDate", "20260101"); segment.put("StartTime", "0000");
        segment.put("EndDate", "20261231"); segment.put("EndTime", "2359");
        segment.put("NumberOfReceiptTextLines", "02");
        segment.putArray("ReceiptTextLines").addObject().put("ReceiptTextDataLength", "01").put("ReceiptTextData", "X");
        assertRule(payload, "SEG118-R-019");
    }

    @Test
    void rejectsHostDiscountCountMismatch() {
        ObjectNode payload = validResponse("904");
        segment(payload).put("NumberOfDiscounts", "01");
        segment(payload).putArray("Discounts");
        assertRule(payload, "SEG118-R-022");
    }

    @Test void rejectsWrongSegmentType() { ObjectNode p=validRequest("903"); segment(p).put("SegmentType","100"); assertRule(p,"SEG118-R-006"); }
    @Test void rejectsSegmentLengthNotFourDigits() { ObjectNode p=validRequest("903"); segment(p).put("SegmentLength","100"); assertRule(p,"SEG118-R-007"); }
    @Test void rejectsSegmentLengthAboveMaximum() { ObjectNode p=validRequest("903"); segment(p).put("SegmentLength","3801"); assertRule(p,"SEG118-R-002"); }
    @Test void rejectsInvalidPromptCode() { ObjectNode p=validRequest("903"); segment(p).put("PromptCode","999"); assertRule(p,"SEG118-R-011"); }
    @Test void rejectsInvalidPromptCodePending() { ObjectNode p=validRequest("903"); segment(p).put("PromptCodePending","0999"); assertRule(p,"SEG118-R-012"); }
    @Test void rejectsInvalidCardTableType() { ObjectNode p=validRequest("903"); segment(p).put("CardTableType","0099"); assertRule(p,"SEG118-R-015"); }
    @Test void rejectsMalformedDeviceCardTableVersion() { ObjectNode p=validRequest("903"); segment(p).put("DeviceCardTableVersion","12345"); assertRule(p,"SEG118-R-013"); }
    @Test void rejectsMalformedHostDiscountTimestamp() { ObjectNode p=validRequest("903"); segment(p).put("HostDiscountTimestamp","2026"); assertRule(p,"SEG118-R-017"); }
    @Test void rejectsMissingSiteConfigurationData() { assertRule(validRequest("903"),"SEG118-R-021"); }
    @Test void rejectsSegment100Present() { ObjectNode p=validRequest("903"); segment(p).put("SiteConfigurationData","DATA"); ((ObjectNode)p.path("Proprietary Data Load Request")).putObject("Standard Message Data Segment"); assertRule(p,"SEG118-R-024"); }
    @Test void rejectsMissingProprietaryDataLoadSegment() { ObjectNode p=validRequest("903"); ((ObjectNode)p.path("Proprietary Data Load Request")).remove("Proprietary Data Load Segment"); assertRule(p,"SEG118-R-024"); }

    private static void assertNoErrors(ObjectNode payload) { assertThat(new Segment118PayloadValidator().validatePayload(payload).errors()).isEmpty(); }
    private static void assertRule(ObjectNode payload, String rule) { ValidationResult result=new Segment118PayloadValidator().validatePayload(payload); assertThat(result.errors()).anyMatch(e->e.reason().contains(rule)); }

    private static ObjectNode validRequest(String promptCode) {
        ObjectNode payload=MAPPER.createObjectNode(); ObjectNode request=payload.putObject("Proprietary Data Load Request");
        request.put("MessageType","ATL105"); request.put("NumSegments","1");
        addCore(request.putObject("Proprietary Data Load Segment"),promptCode);
        return payload;
    }

    private static ObjectNode validResponse(String promptCode) {
        ObjectNode payload=MAPPER.createObjectNode(); ObjectNode response=payload.putObject("Proprietary Data Load Response");
        response.put("ResponseCode","T"); response.put("DownloadIndicator","0"); response.put("InitiationDate","260930"); response.put("InitiationTime","1530"); response.put("SequenceNumber","000001");
        addCore(response.putObject("Proprietary Data Load Segment"),promptCode);
        return payload;
    }

    private static ObjectNode validReceiptText(int... lineLengths) {
        ObjectNode payload = validResponse("901");
        ObjectNode segment = segment(payload);
        segment.put("StartDate", "20260101"); segment.put("StartTime", "0000");
        segment.put("EndDate", "20261231"); segment.put("EndTime", "2359");
        segment.put("NumberOfReceiptTextLines", String.format("%02d", lineLengths.length));
        var lines = segment.putArray("ReceiptTextLines");
        for (int lineLength : lineLengths) {
            lines.addObject().put("ReceiptTextDataLength", String.format("%02d", lineLength))
                .put("ReceiptTextData", "X".repeat(lineLength));
        }
        return payload;
    }

    private static void addCore(ObjectNode segment,String promptCode) {
        segment.put("SegmentType","118"); segment.put("SegmentLength","0100"); segment.put("SequenceNumber","000001"); segment.put("InformationByte","0");
        segment.put("TerminalIdentifier","TERM001A12345"); segment.put("PromptCode",promptCode); segment.put("PromptCodePending","0902");
        segment.put("DeviceCardTableVersion","00001000020000300004000050000600007"); segment.put("CardTableLoadVersion","00000000000000000000000000000000000");
        segment.put("CardTableType","0002"); segment.put("LoadControlKey","SYNTHETICLOADCONTROLKEY"); segment.put("HostDiscountTimestamp","202609261200");
    }

    private static void addDiscount(ObjectNode discount) {
        discount.put("StartDate","20260101"); discount.put("StartTime","0000"); discount.put("EndDate","20261231"); discount.put("EndTime","2359");
        discount.put("BuyPassCardType","0001"); discount.put("CardBinRangeBeginning","000000000001"); discount.put("CardBinRangeEnding","000000000999");
        discount.put("ProductCode","001"); discount.put("ProductDiscountAmount","00100"); discount.put("DiscountProductCode","941");
        discount.put("DiscountQuantityLimit","001"); discount.put("DiscountProgramDescription","SYNTHETIC DISC!");
    }

    private static ObjectNode segment(ObjectNode payload) {
        JsonNode message=payload.has("Proprietary Data Load Request")?payload.path("Proprietary Data Load Request"):payload.path("Proprietary Data Load Response");
        return (ObjectNode)message.path("Proprietary Data Load Segment");
    }
}
