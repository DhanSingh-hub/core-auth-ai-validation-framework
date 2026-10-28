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

    public static List<Chapter12SegmentPayloadValidator.Finding> observedFindings(
            ValidationResult result, String segmentId, JsonNode actual, JsonNode standard, JsonNode context) {
        var combined = new ArrayList<>(findings(result));
        if ("102".equals(segmentId)) combined.addAll(new ProductActualPayloadAdapter().validateHostDiscountContext(actual, standard, context));
        return List.copyOf(combined);
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
        if ("102".equals(segmentId)) for (var finding : validateHostDiscountContext(segment, standard, context)) {
            if (finding.status() == Chapter12SegmentPayloadValidator.Status.INVALID)
                result.addError(stage, finding.ruleId() + ": " + finding.reason());
            else if (finding.status() == Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED)
                result.addWarning("REVIEW_REQUIRED: " + finding.ruleId() + ": " + finding.reason());
        }
        return result;
    }

    public List<Chapter12SegmentPayloadValidator.Finding> validateHostDiscountContext(JsonNode actual, JsonNode standard, JsonNode context) {
        var output = new ArrayList<Chapter12SegmentPayloadValidator.Finding>();
        var pass = Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED;
        var review = Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED;
        var invalid = Chapter12SegmentPayloadValidator.Status.INVALID;
        JsonNode products = actual == null ? null : actual.get("Products");
        if (products == null && actual != null && actual.has("ProductCode"))
            products = JsonNodeFactory.instance.arrayNode().add(actual);
        boolean trigger = false;
        if (products != null && products.isArray()) for (JsonNode p : products)
            if (p.path("ProductCode").isTextual() && Set.of("941", "991").contains(p.path("ProductCode").textValue())) trigger = true;
        if (!trigger) return List.of();
        JsonNode history = context == null ? com.fasterxml.jackson.databind.node.MissingNode.getInstance() : context.path("hostDiscount");
        JsonNode load = history.path("receivedLoad");
        JsonNode transaction = history.path("currentTransaction");
        var binding = Chapter12ObservedSessionValidator.combine(objectStatus(history), objectStatus(load));
        binding = Chapter12ObservedSessionValidator.combine(binding, objectStatus(transaction));
        binding = Chapter12ObservedSessionValidator.combine(binding, objectStatus(standard));
        if (standard != null && standard.isObject() && transaction.isObject()) {
            for (String field : List.of("TerminalIdentifier", "SequenceNumber")) {
                JsonNode current = standard.get(field), captured = transaction.get(field);
                if (current == null || captured == null) { if (binding != invalid) binding = review; }
                else if (!current.isTextual() || !captured.isTextual() || !current.equals(captured)
                        || !current.textValue().matches("SequenceNumber".equals(field) ? "(?!000000)[0-9]{6}" : "[ -~]{1,22}")) binding = invalid;
            }
        }
        if (load.isObject()) {
            JsonNode terminal = load.get("TerminalIdentifier");
            if (terminal == null) { if (binding != invalid) binding = review; }
            else if (!terminal.isTextual() || standard != null && standard.has("TerminalIdentifier") && !terminal.equals(standard.get("TerminalIdentifier"))) binding = invalid;
            binding = Chapter12ObservedSessionValidator.combine(binding, textStatus(load.get("SegmentType"), "118"));
            binding = Chapter12ObservedSessionValidator.combine(binding, textStatus(load.get("PromptCode"), "904"));
            binding = Chapter12ObservedSessionValidator.combine(binding, textStatus(load.get("NewHostDiscountTimestamp"), "[0-9]{12}"));
            binding = Chapter12ObservedSessionValidator.combine(binding, textStatus(history.get("storedTimestampAfter"), "[0-9]{12}"));
            if (!history.has("successfulLoad")) binding = Chapter12ObservedSessionValidator.combine(binding, review);
            else if (!history.path("successfulLoad").isBoolean() || !history.path("successfulLoad").booleanValue()) binding = invalid;
            if (load.has("NewHostDiscountTimestamp") && history.has("storedTimestampAfter")
                    && !load.path("NewHostDiscountTimestamp").equals(history.path("storedTimestampAfter"))) binding = invalid;
            for (var finding : new Segment118PayloadValidator().validateActualWire(load, false)) {
                if ("actual-legacy-payload-boundary".equals(finding.scope())) continue;
                binding = Chapter12ObservedSessionValidator.combine(binding, finding.status());
            }
        }
        JsonNode index = history.get("discountRecordIndex");
        JsonNode discounts = load.get("Discounts");
        JsonNode record = null;
        if (index == null || discounts == null) { if (binding != invalid) binding = review; }
        if (index != null && (!index.isIntegralNumber() || !index.canConvertToInt())
                || discounts != null && !discounts.isArray()) binding = invalid;
        else if (index != null && discounts != null && (!index.isIntegralNumber() || !index.canConvertToInt() || !discounts.isArray()
                || index.intValue() < 0 || index.intValue() >= discounts.size())) binding = invalid;
        else if (index != null && discounts != null) {
            record = discounts.get(index.intValue());
            if (!record.isObject()) binding = invalid;
        }
        output.add(new Chapter12SegmentPayloadValidator.Finding("SEG102-R-010", "actual-host-discount-load", binding,
                "Source13348-13358/13383-13392: actual941/991 use binds to captured successful904 and retained new timestamp on actual current device/sequence; no authenticated latest load is inferred"));
        var productStatus = binding;
        if (record != null && record.isObject()) {
            JsonNode code = record.get("ProductCode"), discount = record.get("DiscountProductCode");
            productStatus = Chapter12ObservedSessionValidator.combine(textStatus(code, "[0-9]{3}"), textStatus(discount, "941|991"));
            if (productStatus == pass) {
                boolean purchased = false, emitted = false;
                for (JsonNode product : products) {
                    purchased |= code.equals(product.get("ProductCode"));
                    emitted |= discount.equals(product.get("ProductCode"));
                }
                if (!purchased || !emitted) productStatus = invalid;
            }
            productStatus = Chapter12ObservedSessionValidator.combine(binding, productStatus);
        }
        output.add(new Chapter12SegmentPayloadValidator.Finding("SEG102-R-010", "actual-host-discount-product", productStatus,
                "Source13455-13476: received eligible product and received discount code bind to actual102 entries; no formula for discount unit price, tax or quantity is invented"));
        var windowStatus = binding;
        if (record != null && record.isObject()) {
            if (!transaction.has("localDateTime") || !record.has("StartDate") || !record.has("StartTime")
                    || !record.has("EndDate") || !record.has("EndTime")) windowStatus = review;
            else windowStatus = discountWindow(transaction.path("localDateTime"), record);
            windowStatus = Chapter12ObservedSessionValidator.combine(binding, windowStatus);
        }
        output.add(new Chapter12SegmentPayloadValidator.Finding("SEG102-R-010", "actual-host-discount-window", windowStatus,
                "Source13348-13354/13410-13436: matched observed local transaction clock lies within received date/time interval; exact endpoints remain review, timezone conversion and capture authenticity are not inferred"));
        var cardStatus = binding;
        if (record != null && record.isObject() && standard != null && standard.isObject()) {
            JsonNode bin = history.path("matchedBinTableEntry");
            JsonNode account = standard.get("AccountNumber"), captured = bin.get("AccountNumber"), card = bin.get("CardType");
            if (!bin.isObject() && !bin.isMissingNode()) cardStatus = invalid;
            else if (account == null || captured == null || card == null || !record.has("BuyPassCardType")) cardStatus = review;
            else if (!account.isTextual() || !captured.isTextual() || !account.equals(captured)
                    || !account.textValue().matches("[0-9]{1,19}") || !card.isTextual() || !card.textValue().matches("[0-9]{4}")
                    || !record.path("BuyPassCardType").isTextual() || !card.equals(record.path("BuyPassCardType"))) cardStatus = invalid;
            cardStatus = Chapter12ObservedSessionValidator.combine(binding, cardStatus);
        }
        output.add(new Chapter12SegmentPayloadValidator.Finding("SEG102-R-010", "actual-host-discount-card", cardStatus,
                "Source13436-13448: discount173 equals the observed matched BIN-table CardType bound to actual account; this does not decode/authenticate a BIN table or infer a twelve-byte BIN range formula"));
        if (record != null && record.isObject()) {
            JsonNode application = history.path("application");
            var applied = Chapter12ObservedSessionValidator.combine(binding, objectStatus(application));
            applied = Chapter12ObservedSessionValidator.combine(applied, Chapter12LoadLifecycleValidator.bindNamed(record, application,
                    List.of("ProductCode", "ProductDiscountAmount", "DiscountProductCode", "DiscountQuantityLimit")));
            JsonNode purchased = null;
            for (JsonNode product : products)
                if (record.path("ProductCode").equals(product.get("ProductCode"))) purchased = product;
            if (purchased == null) applied = Chapter12ObservedSessionValidator.combine(applied, review);
            else applied = Chapter12ObservedSessionValidator.combine(applied, Chapter12LoadLifecycleValidator.bindNamed(purchased, application,
                    List.of("UnitOfMeasure", "Quantity", "UnitPrice")));
            var quantityStatus = applied;
            JsonNode used = application.get("appliedQuantity");
            JsonNode unit = application.get("quantityLimitUnit");
            JsonNode limit = record.get("DiscountQuantityLimit");
            if (used != null && (!used.isTextual() || !used.textValue().matches("[0-9]+(?:\\.[0-9]+)?"))
                    || unit != null && (!unit.isTextual() || unit.textValue().isBlank())
                    || limit != null && (!limit.isTextual() || !limit.textValue().matches("[0-9]{3}"))) quantityStatus = invalid;
            if (used == null || unit == null || limit == null || !application.has("UnitOfMeasure")) quantityStatus = Chapter12ObservedSessionValidator.combine(quantityStatus, review);
            else if (quantityStatus != invalid) {
                if (!unit.equals(application.get("UnitOfMeasure"))) quantityStatus = review;
                else {
                    var usedQuantity = new java.math.BigDecimal(used.textValue());
                    if (usedQuantity.compareTo(new java.math.BigDecimal(limit.textValue())) > 0) quantityStatus = invalid;
                    JsonNode purchasedQuantity = application.get("Quantity");
                    if (purchasedQuantity != null && purchasedQuantity.isTextual()
                            && com.coreauth.validator.validation.Atl105DecimalPrefix.isValid(purchasedQuantity.textValue())) {
                        var available = new java.math.BigDecimal(new java.math.BigInteger(purchasedQuantity.textValue().substring(1)),
                                purchasedQuantity.textValue().charAt(0) - '0');
                        if (usedQuantity.compareTo(available) > 0) quantityStatus = invalid;
                    } else quantityStatus = Chapter12ObservedSessionValidator.combine(quantityStatus,
                            purchasedQuantity == null ? review : invalid);
                }
            }
            output.add(new Chapter12SegmentPayloadValidator.Finding("SEG102-R-010", "actual-host-discount-quantity", quantityStatus,
                    "Source13471-13473/24285-24291: received limit bounds independently observed applied quantity only when supplied source-unit observation matches actual purchased UnitOfMeasure; no quantity conversion or limit-unit authenticity inferred"));
            if ("991".equals(record.path("DiscountProductCode").asText())) {
                var priceStatus = applied;
                JsonNode before = application.get("beforeUnitPrice"), after = application.get("UnitPrice"), amount = record.get("ProductDiscountAmount");
                for (JsonNode value : new JsonNode[]{before, after})
                    if (value != null && (!value.isTextual() || !com.coreauth.validator.validation.Atl105DecimalPrefix.isValid(value.textValue())
                            || value.textValue().length() > 9)) priceStatus = invalid;
                if (amount != null && (!amount.isTextual() || !amount.textValue().matches("[0-9]{5}"))) priceStatus = invalid;
                if (before == null || after == null || amount == null) priceStatus = Chapter12ObservedSessionValidator.combine(priceStatus, review);
                else if (priceStatus != invalid) {
                    var first = new java.math.BigDecimal(new java.math.BigInteger(before.textValue().substring(1)), before.textValue().charAt(0) - '0');
                    var second = new java.math.BigDecimal(new java.math.BigInteger(after.textValue().substring(1)), after.textValue().charAt(0) - '0');
                    var discount = new java.math.BigDecimal(new java.math.BigInteger(amount.textValue()), 3);
                    if (first.subtract(second).compareTo(discount) != 0) priceStatus = invalid;
                }
                output.add(new Chapter12SegmentPayloadValidator.Finding("SEG102-R-010", "actual-host-discount-prepay-price", priceStatus,
                        "Source13358-13359/24055-24062: actual991 prepay unit price includes received flat99v999 discount, bound to independently observed before price and actual after price using existing ATL105 decimal-prefix validity; exact decimal delta only, no tax/rounding or total multiplication formula"));
            }
        }
        return List.copyOf(output);
    }

    private static Chapter12SegmentPayloadValidator.Status objectStatus(JsonNode node) {
        return node == null || node.isMissingNode() ? Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED
                : node.isObject() ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED : Chapter12SegmentPayloadValidator.Status.INVALID;
    }

    private static Chapter12SegmentPayloadValidator.Status textStatus(JsonNode node, String pattern) {
        return node == null ? Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED
                : node.isTextual() && node.textValue().matches(pattern) ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED
                : Chapter12SegmentPayloadValidator.Status.INVALID;
    }

    static Chapter12SegmentPayloadValidator.Status discountWindow(JsonNode captured, JsonNode record) {
        var invalid = Chapter12SegmentPayloadValidator.Status.INVALID;
        boolean missing = captured == null || captured.isMissingNode();
        for (String field : List.of("StartDate", "StartTime", "EndDate", "EndTime")) {
            JsonNode value = record.get(field);
            if (value == null) { missing = true; continue; }
            if (!value.isTextual() || !value.textValue().matches(field.endsWith("Date") ? "[0-9]{8}" : "[0-9]{4}")) return invalid;
            try {
                if (field.endsWith("Date")) java.time.LocalDate.parse(value.textValue(),
                        java.time.format.DateTimeFormatter.ofPattern("uuuuMMdd").withResolverStyle(java.time.format.ResolverStyle.STRICT));
                else if (!"2400".equals(value.textValue())) java.time.LocalTime.parse(value.textValue(),
                        java.time.format.DateTimeFormatter.ofPattern("HHmm").withResolverStyle(java.time.format.ResolverStyle.STRICT));
            } catch (java.time.format.DateTimeParseException exception) {
                return invalid;
            }
        }
        if (captured != null && !captured.isMissingNode() && !captured.isTextual()) return invalid;
        if (captured != null && captured.isTextual()) {
            try { java.time.LocalDateTime.parse(captured.textValue()); }
            catch (java.time.format.DateTimeParseException exception) { return invalid; }
        }
        if (missing) return Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED;
        if (Set.of("2400").contains(record.path("StartTime").textValue()) || Set.of("2400").contains(record.path("EndTime").textValue()))
            return Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED;
        try {
            var formatter = java.time.format.DateTimeFormatter.ofPattern("uuuuMMddHHmm").withResolverStyle(java.time.format.ResolverStyle.STRICT);
            var start = java.time.LocalDateTime.parse(record.path("StartDate").textValue() + record.path("StartTime").textValue(), formatter);
            var end = java.time.LocalDateTime.parse(record.path("EndDate").textValue() + record.path("EndTime").textValue(), formatter);
            var clock = java.time.LocalDateTime.parse(captured.textValue());
            if (start.isAfter(end) || clock.isBefore(start) || clock.isAfter(end)) return invalid;
            return clock.equals(start) || clock.equals(end) ? Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED
                    : Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED;
        } catch (java.time.format.DateTimeParseException exception) {
            return invalid;
        }
    }

    public static com.fasterxml.jackson.databind.node.ArrayNode observedProductCodes(JsonNode actual) {
        if (actual == null || !actual.isObject() || !actual.path("SegmentType").isTextual() || !"102".equals(actual.path("SegmentType").textValue())) return null;
        boolean flat = PRODUCT_FIELDS.stream().anyMatch(actual::has);
        if (actual.has("Products") && flat) return null;
        var codes = JsonNodeFactory.instance.arrayNode();
        if (actual.has("Products")) {
            if (!actual.path("Products").isArray() || actual.path("Products").isEmpty()) return null;
            for (JsonNode product : actual.path("Products")) {
                if (!product.isObject() || !product.path("ProductCode").isTextual() || !product.path("ProductCode").textValue().matches("[0-9]{3}")) return null;
                codes.add(product.path("ProductCode").deepCopy());
            }
        } else if (flat && actual.path("ProductCode").isTextual() && actual.path("ProductCode").textValue().matches("[0-9]{3}"))
            codes.add(actual.path("ProductCode").deepCopy());
        else return null;
        return codes;
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
