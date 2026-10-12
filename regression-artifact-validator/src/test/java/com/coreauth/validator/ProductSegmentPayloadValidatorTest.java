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

    private ObjectNode withWire(String id, boolean multiple) {
        ObjectNode payload = adjusted();
        ObjectNode segment = (ObjectNode) payload.path("Adjusted Product Code Data Segment");
        if ("102".equals(id)) {
            payload.remove("Adjusted Product Code Data Segment");
            payload.set("Product Code Data Segment", segment);
            segment.put("SegmentType", "102");
            ((ObjectNode) payload.path("Standard Segment").path("PromptCode")).put("CardType", "001");
        }
        if (multiple) {
            segment.put("NumberOfProducts", "02");
            segment.withArray("Products").addObject().put("ProductCode", "500").put("UnitOfMeasure", "U")
                    .put("Quantity", "01").put("UnitPrice", "010").put("ProductAmount", "10");
            ((ObjectNode) payload.path("Standard Segment")).put("NonfuelAmount", "10");
        }
        String body = "001G205\\3005\\210" + (multiple ? "\\500U01\\010\\10" : "") + "\u001c";
        String length = String.format(java.util.Locale.ROOT, "%03d", 11 + body.length());
        segment.put("SegmentLength", length).put("SerializedSegment", id + "\u001c" + length + "\u001cS" + (multiple ? "02" : "01") + body);
        return payload;
    }

    @Test
    void measuresBothProductWireLayoutsForOneAndMultipleProductsWithoutMutatingInputs() {
        for (String id : new String[]{"102", "157"}) for (boolean multiple : new boolean[]{false, true}) {
            var payload = withWire(id, multiple);
            var before = payload.deepCopy();
            assertThat(validator.validate(payload, id).errors()).as(id + " multiple=" + multiple).isEmpty();
            assertThat(payload).isEqualTo(before);
            assertThat(validator.validate(payload, id).warnings()).noneMatch(w -> w.contains("require an actual SerializedSegment"));
        }
    }

    @Test
    void rejectsWrongProductDelimiterPositionsAndMeasuredLengthEvenWhenLogicalValuesPass() {
        for (String id : new String[]{"102", "157"}) {
            String name = "102".equals(id) ? "Product Code Data Segment" : "Adjusted Product Code Data Segment";
            var payload = withWire(id, true);
            var segment = (ObjectNode) payload.path(name);
            String raw = segment.path("SerializedSegment").asText();
            for (String broken : new String[]{raw.replace('\\', '\u001c'), raw.substring(0, raw.length() - 1),
                    raw.replace("500U", "500G"), raw + "\u001c", raw + "\u00e9"}) {
                segment.put("SerializedSegment", broken);
                assertThat(validator.validate(payload, id).errors()).as(id + " malformed wire").isNotEmpty();
            }
            segment.put("SerializedSegment", raw).put("SegmentLength", "001");
            assertThat(validator.validate(payload, id).errors()).anyMatch(e -> e.reason().contains("Measured ASCII"));
            segment.put("SerializedSegment", 1);
            assertThat(validator.validate(payload, id).errors()).isNotEmpty();
        }
    }

    @Test
    void enforcesNineCharacterDecimalFieldsAndExplicitPrimaryAndEvOrdering() {
        for (String id : new String[]{"102", "157"}) {
            String name = "102".equals(id) ? "Product Code Data Segment" : "Adjusted Product Code Data Segment";
            var payload = withWire(id, false);
            var segment = (ObjectNode) payload.path(name);
            segment.remove("SerializedSegment");
            var first = (ObjectNode) segment.path("Products").get(0);
            for (String field : new String[]{"Quantity", "UnitPrice"}) {
                String original = first.path(field).asText();
                first.put(field, "0123456789");
                assertThat(validator.validate(payload, id).errors()).isNotEmpty();
                first.put(field, original);
            }
            var context = payload.putObject("context").put("primaryFuelCode", "001").put("evCharging", false);
            assertThat(validator.validate(payload, id).errors()).isEmpty();
            context.put("primaryFuelCode", "002");
            assertThat(validator.validate(payload, id).errors()).anyMatch(e -> e.reason().contains("primary fuel"));
            context.remove("primaryFuelCode");
            context.put("evCharging", true);
            assertThat(validator.validate(payload, id).errors()).anyMatch(e -> e.reason().contains("EV charging"));
            first.put("ProductCode", "308");
            assertThat(validator.validate(payload, id).errors()).isEmpty();
            context.put("evCharging", "true");
            assertThat(validator.validate(payload, id).errors()).anyMatch(e -> e.reason().contains("must be boolean"));
            context.put("evCharging", false).put("primaryFuelCode", "500");
            first.put("ProductCode", "500");
            assertThat(validator.validate(payload, id).errors()).anyMatch(e -> e.reason().contains("primary fuel"));
        }
    }
}
