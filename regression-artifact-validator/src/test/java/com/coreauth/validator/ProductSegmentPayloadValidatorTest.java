package com.coreauth.validator;

import com.coreauth.validator.canonical.ProductSegmentPayloadValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductSegmentPayloadValidatorTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final ProductSegmentPayloadValidator validator = new ProductSegmentPayloadValidator();

    private ObjectNode adjusted() {
        ObjectNode payload = mapper.createObjectNode();
        payload.putObject("Standard Segment").put("FuelPurchaseAmount", "200").put("NonfuelAmount", "")
                .put("TaxAmount", "10").put("CashAmount", "").putObject("PromptCode").put("CardType", "094");
        ObjectNode segment = payload.putObject("Adjusted Product Code Data Segment");
        segment.put("SegmentType", "157").put("SegmentLength", "050").put("ServiceLevel", "S").put("NumberOfProducts", "01")
                .putArray("Products").addObject().put("ProductCode", "001").put("UnitOfMeasure", "G")
                .put("Quantity", "205").put("UnitPrice", "3005").put("ProductAmount", "210");
        return payload;
    }

    @Test
    void acceptsComdata094AndItsAdjustedTaxInclusiveAmount() {
        assertThat(validator.validate(adjusted(), "157").errors()).isEmpty();
    }

    @Test
    void rejectsWexOtr093AndOtherCardTypesInsteadOfTreatingThemAsComdata() {
        ObjectNode payload = adjusted();
        ((ObjectNode) payload.path("Standard Segment").path("PromptCode")).put("CardType", "093");
        assertThat(validator.validate(payload, "157").errors()).anyMatch(e -> e.reason().contains("SEG157-R-001"));
    }

    @Test
    void rejectsAllCodesAbove899Except955AndSeparatelyRejectsDiscountCodes() {
        for (String code : new String[]{"900", "950", "951", "952", "999"}) {
            ObjectNode payload = adjusted();
            ((ObjectNode) payload.path("Adjusted Product Code Data Segment").path("Products").get(0)).put("ProductCode", code);
            assertThat(validator.validate(payload, "157").errors()).anyMatch(e -> e.reason().contains("SEG157-R-003"));
        }
        ObjectNode payload = adjusted();
        ((ObjectNode) payload.path("Adjusted Product Code Data Segment").path("Products").get(0)).put("ProductCode", "955");
        assertThat(validator.validate(payload, "157").errors()).isEmpty();
    }

    @Test
    void rejectsAdjustedReconciliationMismatchAndMutuallyExclusiveCompanion() {
        ObjectNode payload = adjusted();
        ((ObjectNode) payload.path("Standard Segment")).put("TaxAmount", "11");
        assertThat(validator.validate(payload, "157").errors()).anyMatch(e -> e.reason().contains("SEG157-R-005"));
        payload = adjusted();
        payload.putObject("Product Code Data Segment");
        assertThat(validator.validate(payload, "157").errors()).anyMatch(e -> e.reason().contains("SEG157-R-002"));
    }
}
