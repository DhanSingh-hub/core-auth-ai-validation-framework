package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/** Maps actual product fields only, including the observed single flat-product representation. */
public final class ProductActualPayloadAdapter {
    private static final Set<String> PRODUCT_FIELDS = Set.of("ProductCode", "UnitOfMeasure", "UnitofMeasure", "Quantity", "UnitPrice", "ProductAmount");
    private static final Pattern RULE = Pattern.compile("SEG(?:102|157)-R-[0-9]{3}");

    public static List<Chapter12SegmentPayloadValidator.Finding> findings(ValidationResult result) {
        var findings = new ArrayList<Chapter12SegmentPayloadValidator.Finding>();
        for (var error : result.errors()) {
            var matcher = RULE.matcher(error.reason());
            findings.add(new Chapter12SegmentPayloadValidator.Finding(matcher.find() ? matcher.group() : "CH12-PRODUCT-ACTUAL-INPUT",
                    "actual-product", Chapter12SegmentPayloadValidator.Status.INVALID, error.reason()));
        }
        for (String warning : result.warnings()) {
            var matcher = RULE.matcher(warning);
            findings.add(new Chapter12SegmentPayloadValidator.Finding(matcher.find() ? matcher.group() : "CH12-PRODUCT-EVIDENCE",
                    "actual-product", Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED, warning));
        }
        findings.add(new Chapter12SegmentPayloadValidator.Finding("CH12-PRODUCT-COVERAGE", "coverage-boundary",
                Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED, "Actual product execution is bounded; not full message/catalog certification"));
        return List.copyOf(findings);
    }

    public ValidationResult validate(JsonNode actual, String segmentId, JsonNode standard, boolean companionPresent, JsonNode context) {
        if (!Set.of("102", "157").contains(segmentId)) throw new IllegalArgumentException("Product segment must be 102 or 157");
        String stage = "Segment" + segmentId + "ActualPayload";
        ValidationResult result = new ValidationResult(stage);
        if (actual == null || !actual.isObject()) {
            result.addError(stage, "Actual product segment must be an object");
            return result;
        }
        ObjectNode payload = JsonNodeFactory.instance.objectNode();
        String name = "102".equals(segmentId) ? "Product Code Data Segment" : "Adjusted Product Code Data Segment";
        String other = "102".equals(segmentId) ? "Adjusted Product Code Data Segment" : "Product Code Data Segment";
        ObjectNode segment = actual.deepCopy();
        alias(segment, "NumberofProducts", "NumberOfProducts", stage, result);
        boolean flat = PRODUCT_FIELDS.stream().anyMatch(actual::has);
        if (actual.has("Products") && flat) result.addError(stage, "Both flat and array product representations are ambiguous");
        if (!actual.has("Products") && flat) {
            ObjectNode product = segment.putArray("Products").addObject();
            for (String field : PRODUCT_FIELDS) {
                if (actual.has(field)) product.set(field, actual.path(field).deepCopy());
                segment.remove(field);
            }
        }
        for (JsonNode product : segment.path("Products")) {
            if (product.isObject()) {
                alias((ObjectNode) product, "UnitofMeasure", "UnitOfMeasure", stage, result);
                var fields = product.fieldNames();
                while (fields.hasNext()) {
                    String field = fields.next();
                    if (!PRODUCT_FIELDS.contains(field)) result.addWarning("REVIEW_REQUIRED: unmapped actual product-entry field " + field);
                }
            }
        }
        Set<String> supported = Set.of("SegmentType", "SegmentLength", "ServiceLevel", "NumberOfProducts", "Products", "SerializedSegment");
        var names = segment.fieldNames();
        while (names.hasNext()) {
            String field = names.next();
            if (!supported.contains(field)) result.addWarning("REVIEW_REQUIRED: unmapped actual product field " + field);
        }
        if (standard != null) {
            if (!standard.isObject()) result.addError(stage, "Actual companion Standard Segment must be an object");
            else {
                ObjectNode normalized = standard.deepCopy();
                alias(normalized, "NonFuelAmount", "NonfuelAmount", stage, result);
                payload.set("Standard Segment", normalized);
            }
        }
        if (context != null) {
            if (!context.isObject()) result.addError(stage, "Product evidence context must be an object");
            else payload.set("context", context.deepCopy());
        }
        payload.set(name, segment);
        if (companionPresent) payload.putObject(other);
        result.merge(new ProductSegmentPayloadValidator().validate(payload, segmentId));
        return result;
    }

    public ValidationResult validatePayload(JsonNode payload, String segmentId) {
        String name = "102".equals(segmentId) ? "Product Code Data Segment" : "Adjusted Product Code Data Segment";
        String other = "102".equals(segmentId) ? "Adjusted Product Code Data Segment" : "Product Code Data Segment";
        if (payload == null || !payload.isObject()) {
            ValidationResult result = new ValidationResult("ProductActualPayload");
            result.addError("ProductActualPayload", "Actual product envelope must be an object");
            return result;
        }
        return validate(payload.get(name), segmentId, payload.get("Standard Segment"), payload.has(other), payload.get("context"));
    }

    private static void alias(ObjectNode node, String from, String to, String stage, ValidationResult result) {
        if (!node.has(from)) return;
        if (node.has(to) && !node.path(to).equals(node.path(from))) {
            result.addError(stage, "Conflicting actual aliases " + from + "/" + to);
        } else node.set(to, node.path(from).deepCopy());
        node.remove(from);
    }
}
