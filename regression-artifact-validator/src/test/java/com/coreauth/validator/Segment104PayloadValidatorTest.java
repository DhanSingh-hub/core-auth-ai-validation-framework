package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment104PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 1 (Coverage Closure) baseline tests for {@link Segment104PayloadValidator}.
 *
 * <p>Anchored to {@code docs/specs/kb/segment-104/coverage/segment-104-rule-catalog.json}
 * and the synthetic fixtures under
 * {@code test-input/ai-solution/test-data/segment-104/}.
 */
class Segment104PayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path SEG104_ROOT = Paths.get("test-input", "ai-solution", "test-data", "segment-104");

    @Test
    void acceptsPurchaseCardAuthOriginalHappyPath() {
        ValidationResult result = new Segment104PayloadValidator()
            .validateFile(SEG104_ROOT.resolve(Path.of("lifecycle", "purchase-card-auth-original.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsMinimalPayloadWithAllConditionalFieldsBlank() {
        ValidationResult result = new Segment104PayloadValidator()
            .validateFile(SEG104_ROOT.resolve(Path.of("lifecycle", "purchase-card-minimal.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsDuplicateSegment104PerMessage() {
        ValidationResult result = new Segment104PayloadValidator()
            .validateFile(SEG104_ROOT.resolve(Path.of("rejection", "purchase-card-duplicate-segment.synthetic.json")));

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG104-R-020"));
    }

    @Test
    void rejectsWrongSegmentType() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request").path("Purchase Card Data Segment"))
            .put("SegmentType", "999");

        ValidationResult result = new Segment104PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG104-R-004"));
    }

    @Test
    void rejectsSegmentLengthWithNonNumericValue() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request").path("Purchase Card Data Segment"))
            .put("SegmentLength", "AB1");

        ValidationResult result = new Segment104PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG104-R-005"));
    }

    @Test
    void rejectsSegmentLengthOverEightySix() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request").path("Purchase Card Data Segment"))
            .put("SegmentLength", "099");

        ValidationResult result = new Segment104PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG104-R-006"));
    }

    @Test
    void rejectsPurchaseCodeOverSixteenCharacters() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request").path("Purchase Card Data Segment"))
            .put("PurchaseCode", "THISVALUEISWAYTOOLONGFORFIELD");

        ValidationResult result = new Segment104PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG104-R-007"));
    }

    @Test
    void rejectsPcTaxAmountWithNonNumericContent() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request").path("Purchase Card Data Segment"))
            .put("PcTaxAmount", "ABCDEFG");

        ValidationResult result = new Segment104PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG104-R-008"));
    }

    @Test
    void rejectsPcFreightAmountOverSevenDigits() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request").path("Purchase Card Data Segment"))
            .put("PcFreightAmount", "123456789");

        ValidationResult result = new Segment104PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG104-R-009"));
    }

    @Test
    void rejectsPcDutyAmountWithNonNumericContent() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request").path("Purchase Card Data Segment"))
            .put("PcDutyAmount", "XYZ");

        ValidationResult result = new Segment104PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG104-R-010"));
    }

    @Test
    void rejectsShipToCountryCodeNotThreeDigits() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request").path("Purchase Card Data Segment"))
            .put("ShipToCountryCode", "12");

        ValidationResult result = new Segment104PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG104-R-011"));
    }

    @Test
    void acceptsShipToPostalCodeInZipPlusFourFormat() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request").path("Purchase Card Data Segment"))
            .put("ShipToPostalCode", "94105-1234");

        ValidationResult result = new Segment104PayloadValidator().validatePayload(payload);

        assertThat(result.warnings()).isEmpty();
    }

    @Test
    void warnsButDoesNotHardFailOnNonConformingPostalCode() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request").path("Purchase Card Data Segment"))
            .put("ShipToPostalCode", "!!!not-a-postal-code-at-all!!!");

        ValidationResult result = new Segment104PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .noneMatch(error -> error.reason().contains("SEG104-R-012"));
        assertThat(result.warnings())
            .anyMatch(w -> w.contains("SEG104-R-012") && w.contains("PROVISIONAL P-01"));
    }

    @Test
    void rejectsDirectMarketingInvoiceNumberOverTenCharacters() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request").path("Purchase Card Data Segment"))
            .put("DirectMarketingInvoiceNumber", "TOOLONGINVOICENUMBER");

        ValidationResult result = new Segment104PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG104-R-014"));
    }

    @Test
    void rejectsMissingPurchaseCardDataSegment() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request")).remove("Purchase Card Data Segment");

        ValidationResult result = new Segment104PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG104-R-002"));
    }

    @Test
    void rejectsPurchaseCardSegmentWithoutStandardSegment() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request")).remove("Standard Segment");

        ValidationResult result = new Segment104PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG104-R-001"));
    }

    private static ObjectNode validPayload() {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("Financial Request");
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "02");

        ObjectNode standard = request.putObject("Standard Segment");
        standard.put("SegmentType", "100");
        standard.put("SegmentLength", "086");
        standard.put("InformationByte", "0");
        standard.put("TerminalID", "PC500001");
        standard.putObject("PromptCode").put("TransactionType", "3").put("CardType", "070");
        standard.put("SequenceNumber", "500201");
        standard.put("PartialApprovalIndicator", "0");

        ObjectNode purchaseCard = request.putObject("Purchase Card Data Segment");
        purchaseCard.put("SegmentType", "104");
        purchaseCard.put("SegmentLength", "045");
        purchaseCard.put("PurchaseCode", "PC1234567890");
        purchaseCard.put("PcTaxAmount", "0000225");
        purchaseCard.put("PcFreightAmount", "0000500");
        purchaseCard.put("PcDutyAmount", "0000000");
        purchaseCard.put("ShipToCountryCode", "840");
        purchaseCard.put("ShipToPostalCode", "94105-1234");
        purchaseCard.put("ShipFromPostalCode", "60601");
        purchaseCard.put("DirectMarketingInvoiceNumber", "INV000123");
        return payload;
    }
}
