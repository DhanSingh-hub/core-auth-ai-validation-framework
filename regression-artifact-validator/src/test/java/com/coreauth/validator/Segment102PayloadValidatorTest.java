package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment102PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers {@link Segment102PayloadValidator}, built during the Chapter 12 segment cross-check
 * after discovering Segment 102 had a 25-rule catalog but no independent executable validator.
 * Source: ATL105 Section 12.3 ({@code extracted_text.txt} lines 11562-11720).
 */
class Segment102PayloadValidatorTest {
    private final ObjectMapper json = new ObjectMapper();
    private final Segment102PayloadValidator validator = new Segment102PayloadValidator();

    private ObjectNode validPayload() {
        ObjectNode payload = json.createObjectNode();
        ObjectNode standard = payload.putObject("Standard Segment");
        standard.put("FuelPurchaseAmount", "00002000");
        standard.put("NonfuelAmount", "00001000");
        standard.put("TaxAmount", "");
        standard.put("CashAmount", "");
        ObjectNode segment = payload.putObject("Product Code Data Segment");
        segment.put("SegmentType", "102");
        segment.put("SegmentLength", "100");
        segment.put("ServiceLevel", "S");
        segment.put("NumberOfProducts", "02");
        ArrayNode products = segment.putArray("Products");
        ObjectNode fuel = products.addObject();
        fuel.put("ProductCode", "001").put("isFuel", true).put("UnitOfMeasure", "G")
                .put("Quantity", "200000500").put("UnitPrice", "300002999").put("ProductAmount", "00002000");
        ObjectNode nonfuel = products.addObject();
        nonfuel.put("ProductCode", "500").put("isFuel", false).put("UnitOfMeasure", "U")
                .put("Quantity", "000000001").put("UnitPrice", "200100000").put("ProductAmount", "00001000");
        return payload;
    }

    private ValidationResult validate(ObjectNode payload) {
        return validator.validatePayload(payload);
    }

    @Test
    void acceptsAWellFormedTwoProductPayloadReconciledWithSegment100() {
        assertThat(validate(validPayload()).errors()).isEmpty();
    }

    @Test
    void absentSegmentIsNotAnErrorBecauseItIsAnOptionalCompanionSegment() {
        ObjectNode payload = json.createObjectNode();
        assertThat(validate(payload).errors()).isEmpty();
    }

    @Test
    void rejectsWrongSegmentTypeAndOutOfRangeSegmentLength() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Product Code Data Segment")).put("SegmentType", "999");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG102-R-001"));

        payload = validPayload();
        ((ObjectNode) payload.path("Product Code Data Segment")).put("SegmentLength", "382");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG102-R-023"));
    }

    @Test
    void rejectsMoreThanTenProductsAndMismatchedCount() {
        ObjectNode payload = validPayload();
        ObjectNode segment = (ObjectNode) payload.path("Product Code Data Segment");
        ArrayNode products = (ArrayNode) segment.path("Products");
        for (int i = 0; i < 9; i++) {
            products.addObject().put("ProductCode", String.format("%03d", 600 + i)).put("isFuel", false)
                    .put("UnitOfMeasure", "U").put("Quantity", "000000001")
                    .put("UnitPrice", "200100000").put("ProductAmount", "00000100");
        }
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG102-R-006"));

        payload = validPayload();
        ((ObjectNode) payload.path("Product Code Data Segment")).put("NumberOfProducts", "05");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG102-R-005"));
    }

    @Test
    void rejectsFieldLevelFormatViolationsForEveryProductField() {
        for (String field : new String[]{"UnitOfMeasure", "Quantity", "UnitPrice", "ProductAmount"}) {
            ObjectNode payload = validPayload();
            ArrayNode products = (ArrayNode) payload.path("Product Code Data Segment").path("Products");
            ((ObjectNode) products.get(0)).put(field, "###");
            assertThat(validate(payload).errors()).as(field).isNotEmpty();
        }
    }

    @Test
    void enforcesFuelProductsFirstWhenFlagged() {
        ObjectNode payload = validPayload();
        ArrayNode products = (ArrayNode) payload.path("Product Code Data Segment").path("Products");
        ((ObjectNode) products.get(0)).put("isFuel", false);
        ((ObjectNode) products.get(1)).put("isFuel", true);
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG102-R-007"));
    }

    @Test
    void retainsReviewForTheUnresolvedSourceReconciliationConflict() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Standard Segment")).put("NonfuelAmount", "00009999");
        assertThat(validate(payload).warnings()).anyMatch(e -> e.contains("SEG102-R-015") && e.contains("SEG102-SME-006"));
    }

    @Test
    void acceptsVariableDecimalEncodingsButRejectsMissingFractionalDigits() {
        ObjectNode payload = validPayload();
        ObjectNode product = (ObjectNode) payload.path("Product Code Data Segment").path("Products").get(0);
        product.put("Quantity", "205").put("UnitPrice", "30");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG102-R-013"));
        product.put("UnitPrice", "3005");
        assertThat(validate(payload).errors()).isEmpty();
        product.put("Quantity", "25");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG102-R-012"));
    }

    @Test
    void derivesFuelOrderingFromAppendixFWithoutProducerFlags() {
        ObjectNode payload = validPayload();
        ArrayNode products = (ArrayNode) payload.path("Product Code Data Segment").path("Products");
        ((ObjectNode) products.get(0)).put("ProductCode", "500").remove("isFuel");
        ((ObjectNode) products.get(1)).put("ProductCode", "001").remove("isFuel");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG102-R-007"));
    }

    @Test
    void rejectsMalformedStandardAmountsRatherThanSilentlyIgnoringThem() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Standard Segment")).put("FuelPurchaseAmount", "not-an-amount");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("reconciliation amount"));
    }

    @Test
    void rejectsSignedAndShortProductCodesAndMalformedSegmentObjects() {
        ObjectNode payload = validPayload();
        ObjectNode product = (ObjectNode) payload.path("Product Code Data Segment").path("Products").get(0);
        for (String value : new String[]{"-1", "1", "01"}) {
            product.put("ProductCode", value);
            assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG102-R-010"));
        }
        payload.put("Product Code Data Segment", "invalid");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("must be an object"));
    }

    @Test
    void rejectsMutualExclusivityWithSegment157AndComdataCardType() {
        ObjectNode payload = validPayload();
        payload.putObject("Adjusted Product Code Data Segment");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG102-R-018"));

        payload = validPayload();
        payload.putObject("Standard Segment").putObject("PromptCode").put("CardType", "095");
        assertThat(validate(payload).errors()).anyMatch(e -> e.reason().contains("SEG102-R-019"));
    }
}
