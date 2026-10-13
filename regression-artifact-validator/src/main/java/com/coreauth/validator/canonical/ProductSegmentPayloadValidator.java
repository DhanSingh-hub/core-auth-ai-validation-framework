package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.Atl105DecimalPrefix;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/** Sections 12.3/12.41; classification comes from Appendix F, not producer-supplied flags. */
public final class ProductSegmentPayloadValidator {
    private static final Set<String> UNITS = Set.of("C", "G", "H", "I", "K", "L", "M", "P",
            "Q", "U", "W", "Z", "O", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    private static final Set<String> FUEL_ROLES = Set.of("MOTOR_FUEL", "AVIATION_FUEL", "MARINE_FUEL", "OTHER_FUEL");

    public ValidationResult validate(JsonNode payload, String segmentId) {
        if (!Set.of("102", "157").contains(segmentId)) {
            throw new IllegalArgumentException("Product segment must be 102 or 157");
        }
        String name = "102".equals(segmentId) ? "Product Code Data Segment" : "Adjusted Product Code Data Segment";
        String other = "102".equals(segmentId) ? "Adjusted Product Code Data Segment" : "Product Code Data Segment";
        String stage = "Segment" + segmentId + "Payload";
        ValidationResult result = new ValidationResult(stage);
        if (payload == null || !payload.isObject()) {
            result.addError(stage, "Payload root must be an object");
            return result;
        }
        JsonNode segment = payload.get(name);
        if (segment == null) return result;
        if (!segment.isObject()) {
            result.addError(stage, name + " must be an object");
            return result;
        }
        boolean adjusted = "157".equals(segmentId);
        if (!segmentId.equals(text(segment, "SegmentType"))) error(result, stage, adjusted ? "009" : "001", "Incorrect SegmentType");
        String length = text(segment, "SegmentLength");
        if (!digits(length, 3, 3) || Integer.parseInt(length) < 1 || Integer.parseInt(length) > 381) {
            error(result, stage, adjusted ? "009" : "023", "SegmentLength must be 001-381");
        }
        String service = text(segment, "ServiceLevel");
        if (service == null || !service.matches("[FSNXHO0-9]")) {
            error(result, stage, adjusted ? "009" : "003", "Invalid ServiceLevel");
        }
        JsonNode products = segment.path("Products");
        if (!products.isArray() || products.isEmpty() || products.size() > 10) {
            error(result, stage, adjusted ? "004" : "006", "Products must be an array of 1-10 entries");
            return result;
        }
        String count = text(segment, "NumberOfProducts");
        if (count == null || !count.matches("0[1-9]|10")) {
            error(result, stage, adjusted ? "009" : "004", "NumberOfProducts must be zero-padded 01-10");
        } else if (Integer.parseInt(count) != products.size()) {
            error(result, stage, adjusted ? "009" : "005", "NumberOfProducts does not match Products");
        }
        if (payload.has(other)) error(result, stage, adjusted ? "002" : "018", "Segments 102 and 157 are mutually exclusive");
        String card = text(payload.path("Standard Segment").path("PromptCode"), "CardType");
        JsonNode comdata = payload.path("context").path("comdata");
        Boolean isComdata = null;
        if (payload.has("context") && !payload.path("context").isObject()) result.addError(stage, "Product context must be an object");
        if (payload.path("context").has("comdata") && !comdata.isBoolean()) result.addError(stage, "Comdata context must be boolean");
        if (comdata.isBoolean()) {
            isComdata = comdata.booleanValue();
            if (card != null && AppendixETableLoadCardTypeCodes.cardTypeCodes().contains(card)
                    && isComdata != Set.of("094", "095", "096").contains(card)) {
                error(result, stage, adjusted ? "001" : "019", "Comdata evidence contradicts actual card type");
            }
        }
        else if (card != null && AppendixETableLoadCardTypeCodes.cardTypeCodes().contains(card)) {
            isComdata = Set.of("094", "095", "096").contains(card);
        }
        if (isComdata == null) {
            result.addWarning("REVIEW_REQUIRED: Comdata applicability needs independently supplied context; CardType=" + card);
        } else if (isComdata != adjusted) {
            error(result, stage, adjusted ? "001" : "019", "Segment is incompatible with declared Comdata context");
        }

        AppendixFProductCodeOracle codes;
        try {
            codes = new AppendixFProductCodeOracle();
        } catch (IOException exception) {
            result.addError(stage, "Appendix F oracle is unavailable: " + exception.getMessage());
            return result;
        }
        BigInteger total = BigInteger.ZERO;
        BigInteger tax = BigInteger.ZERO;
        BigInteger discounts = BigInteger.ZERO;
        boolean discountProductsPresent = false;
        boolean taxProductsPresent = false;
        Set<String> fuelCodes = new HashSet<>();
        Set<String> knownNonfuelCodes = new HashSet<>();
        boolean nonfuelSeen = false;
        boolean amountsValid = true;
        int index = 0;
        for (JsonNode product : products) {
            String location = "Products[" + index++ + "]";
            if (!product.isObject()) {
                error(result, stage, adjusted ? "009" : "014", location + " must be an object");
                amountsValid = false;
                continue;
            }
            String code = text(product, "ProductCode");
            if (!digits(code, 3, 3)) {
                error(result, stage, adjusted ? "009" : "010", location + " ProductCode must be exactly three digits");
            } else {
                JsonNode classification = codes.evaluate(JsonNodeFactory.instance.objectNode()
                        .put("messageRole", "TRANSACTION_PRODUCT_CODE").put("mode", "CLASSIFICATION").put("ProductCode", code));
                boolean known = "ASSIGNED".equals(classification.path("assignment").asText())
                        && !"REVIEW_REQUIRED".equals(classification.path("outcome").asText());
                if (!known) {
                    result.addWarning("REVIEW_REQUIRED: Appendix F classification/assignment of " + code
                            + "; reserved/private codes are not automatically rejected or approved");
                } else {
                    boolean fuel = FUEL_ROLES.contains(classification.path("role").asText());
                    if (fuel && nonfuelSeen) error(result, stage, adjusted ? "004" : "007", location + " fuel must precede nonfuel");
                    if (fuel && !fuelCodes.add(code)) error(result, stage, adjusted ? "004" : "009", location + " repeats a fuel code");
                    if (!fuel) {
                        nonfuelSeen = true;
                        knownNonfuelCodes.add(code);
                    }
                    if (product.has("isFuel") && (!product.path("isFuel").isBoolean()
                            || product.path("isFuel").booleanValue() != fuel)) {
                        error(result, stage, adjusted ? "004" : "007", location + " isFuel contradicts Appendix F");
                    }
                }
                if (adjusted && Integer.parseInt(code) > 899 && !"955".equals(code)) {
                    error(result, stage, "003", location + " codes above 899 are forbidden except 955");
                }
                if (adjusted && ("NEGATIVE".equals(classification.path("role").asText())
                        || classification.path("description").asText().startsWith("Tax "))) {
                    error(result, stage, "006", location + " separate tax/discount/coupon codes are forbidden");
                }
                if (!adjusted && Set.of("941", "991").contains(code)) {
                    String timing = text(payload.path("context"), "paymentTiming");
                    if (timing == null && !payload.path("context").has("paymentTiming"))
                        result.addWarning("REVIEW_REQUIRED: SEG102-R-010 observed PREPAY/POSTPAY timing required for host discount Product Code " + code);
                    else if (!("941".equals(code) ? "POSTPAY" : "PREPAY").equals(timing))
                        error(result, stage, "010", location + " host discount code 941 is post-pay; administrative code 991 is pre-pay (Section 12.16.4)");
                }
            }
            String unit = text(product, "UnitOfMeasure");
            if (unit == null || !UNITS.contains(unit)) error(result, stage, adjusted ? "009" : "011", location + " invalid UnitOfMeasure");
            decimal(product, "Quantity", location, stage, adjusted ? "009" : "012", result);
            decimal(product, "UnitPrice", location, stage, adjusted ? "009" : "013", result);
            String amount = text(product, "ProductAmount");
            if (!digits(amount, 1, 12)) {
                error(result, stage, adjusted ? "009" : "014", location + " ProductAmount must be 1-12 digits");
                amountsValid = false;
            } else {
                BigInteger number = new BigInteger(amount);
                total = total.add(number);
                if (!adjusted && code != null && code.matches("90[0-9]")) {
                    discountProductsPresent = true;
                    discounts = discounts.add(number);
                }
                if (Set.of("950", "951", "952", "953", "954", "962", "963", "964", "966", "967", "968", "969", "970").contains(code)) {
                    taxProductsPresent = true;
                    tax = tax.add(number);
                } else if ("965".equals(code)) result.addWarning("REVIEW_REQUIRED: Appendix F labels 965 as SWT Rate, not a tax dollar amount; no amount summation is inferred");
            }
        }
        JsonNode standard = payload.path("Standard Segment");
        if (!standard.isObject()) {
            result.addWarning("REVIEW_REQUIRED: Segment 100 amount reconciliation requires Standard Segment");
        } else if (amountsValid) {
            BigInteger expected = BigInteger.ZERO;
            boolean valid = true;
            for (String field : new String[]{"FuelPurchaseAmount", "NonfuelAmount", "TaxAmount", "CashAmount"}) {
                String value = text(standard, field);
                if (value == null) {
                    result.addWarning("REVIEW_REQUIRED: missing reconciliation field " + field);
                    valid = false;
                } else if (!value.isEmpty() && !digits(value, 1, 12)) {
                    result.addError(stage, "Invalid Segment 100 reconciliation amount " + field);
                    valid = false;
                } else if (!value.isEmpty()) expected = expected.add(new BigInteger(value));
            }
            if (valid && !expected.equals(total)) {
                if (adjusted) error(result, stage, "005", "Adjusted product total does not match Segment 100");
                else result.addWarning("REVIEW_REQUIRED: SEG102-R-015 total mismatch " + total + " vs " + expected
                        + "; source-example conflict SEG102-SME-006 prevents an unconditional rejection");
            }
            if (!adjusted && taxProductsPresent) {
                String observedTax = text(standard, "TaxAmount");
                if (observedTax == null) result.addWarning("REVIEW_REQUIRED: SEG102-R-016 tax product reconciliation requires actual Segment 100 TaxAmount");
                else if (!digits(observedTax, 1, 12) || !tax.equals(new BigInteger(observedTax)))
                    error(result, stage, "016", "Tax product total differs from populated Segment 100 TaxAmount, including zero tax products");
            }
        }
        physical(segment, products, adjusted, stage, result);
        JsonNode context = payload.path("context");
        if (context.has("primaryFuelCode")) {
            String primary = text(context, "primaryFuelCode");
            if (primary == null || !primary.matches("[0-9]{3}")
                    || !primary.equals(text(products.get(0), "ProductCode")) || knownNonfuelCodes.contains(primary)) {
                error(result, stage, adjusted ? "007" : "024", "Observed primary fuel must be the first product");
            }
        }
        if (context.has("evCharging")) {
            if (!context.path("evCharging").isBoolean()) result.addError(stage, "evCharging must be boolean");
            else if (context.path("evCharging").booleanValue()
                    && !Set.of("308", "309", "310").contains(text(products.get(0), "ProductCode") == null ? "" : text(products.get(0), "ProductCode"))) {
                error(result, stage, adjusted ? "007" : "008", "EV charging fuel code must be first");
            }
        }
        merchantProducts(payload.path("context"), products, adjusted, stage, result);
        if (!adjusted && discountProductsPresent && amountsValid) discountAllocation(payload.path("context"), discounts, stage, result);
        result.addWarning("REVIEW_REQUIRED: supplied merchant and primary-fuel provenance is not authenticated");
        return result;
    }

    private static void discountAllocation(JsonNode context, BigInteger discounts, String stage, ValidationResult result) {
        JsonNode allocation = context.get("discountAllocation");
        if (allocation == null) {
            result.addWarning("REVIEW_REQUIRED: SEG102-R-026 Appendix F nonfuel-first discount/coupon allocation needs observed pre-discount amounts and allocation");
            return;
        }
        if (!allocation.isObject()) { error(result, stage, "026", "Observed discount allocation must be an object"); return; }
        for (String field : new String[]{"nonfuelBeforeDiscount", "fuelBeforeDiscount", "appliedToNonfuel", "appliedToFuel"}) {
            if (!allocation.has(field)) {
                result.addWarning("REVIEW_REQUIRED: SEG102-R-026 missing observed discount allocation field " + field);
                return;
            }
            if (!digits(text(allocation, field), 1, 12)) { error(result, stage, "026", "Observed allocation amounts must be unsigned integer text"); return; }
        }
        BigInteger nonfuel = new BigInteger(text(allocation, "nonfuelBeforeDiscount")), fuel = new BigInteger(text(allocation, "fuelBeforeDiscount"));
        BigInteger toNonfuel = new BigInteger(text(allocation, "appliedToNonfuel")), toFuel = new BigInteger(text(allocation, "appliedToFuel"));
        if (!toNonfuel.add(toFuel).equals(discounts) || toNonfuel.compareTo(nonfuel) > 0 || toFuel.compareTo(fuel) > 0)
            error(result, stage, "026", "Observed allocation must account for actual900-909 discount/coupon amounts without exceeding observed purchase amounts");
        if (toFuel.signum() > 0 && toNonfuel.compareTo(nonfuel) < 0)
            error(result, stage, "026", "Appendix F27164: discounts/coupons apply first to nonfuel before any fuel allocation");
        result.addWarning("REVIEW_REQUIRED: SEG102-R-026 allocation checks supplied current observations only; no universal negative-code or aggregate-conflict formula is inferred");
    }

    public static java.util.List<Chapter12SegmentPayloadValidator.Finding> observedStandardTotals(
            JsonNode elements, JsonNode components) {
        var findings = new java.util.ArrayList<Chapter12SegmentPayloadValidator.Finding>();
        var categories = java.util.Map.of("41", "fuel", "58", "nonfuel", "99", "tax", "17", "cash");
        for (var category : categories.entrySet()) {
            var status = Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED;
            String reason = "12.1 lines11322-11337: separately emitted net/category total needs complete matched observed components; aggregate product equality is not proof";
            if (components != null) {
                JsonNode observedCategory = components.get(category.getValue());
                boolean malformed = !components.isObject() || components.has("complete") && !components.path("complete").isBoolean()
                        || components.has("terminalIdentifier") && !components.path("terminalIdentifier").isTextual()
                        || components.has("sequenceNumber") && !components.path("sequenceNumber").isTextual()
                        || observedCategory != null && !observedCategory.isObject();
                if (observedCategory != null && observedCategory.isObject()) {
                    for (String list : java.util.List.of("amounts", "discounts")) {
                        JsonNode entries = observedCategory.get(list);
                        if (entries != null) {
                            if (!entries.isArray()) malformed = true;
                            else for (JsonNode entry : entries)
                                if (!entry.isTextual() || !entry.textValue().matches("[0-9]{1,12}")) malformed = true;
                        }
                    }
                    if (!java.util.Set.of("41", "58").contains(category.getKey()) && observedCategory.has("discounts")) malformed = true;
                }
                if (malformed) {
                    status = Chapter12SegmentPayloadValidator.Status.INVALID;
                    reason = "Observed component inventory, binding and supplied monetary lists must retain explicit typed source representations, even when completeness evidence is missing";
                } else if (components.path("complete").isBoolean() && components.path("complete").booleanValue()) {
                    JsonNode values = components.get(category.getValue());
                    if (!components.has("terminalIdentifier") || !components.has("sequenceNumber") || values == null
                            || !elements.has("102") || !elements.has("86")) {
                        reason = "Matched complete component inventory lacks terminal, sequence or category evidence";
                    } else if (!components.path("terminalIdentifier").isTextual() || !components.path("sequenceNumber").isTextual()
                            || !components.path("terminalIdentifier").equals(elements.path("102"))
                            || !components.path("sequenceNumber").equals(elements.path("86")) || !values.isObject()) {
                        status = Chapter12SegmentPayloadValidator.Status.INVALID;
                        reason = "Observed category inventory is malformed or contradicts actual terminal/sequence; no matching values are supplied by metadata";
                    } else if (values.has("amounts") && !values.path("amounts").isArray()
                            || values.has("discounts") && !values.path("discounts").isArray()) {
                        status = Chapter12SegmentPayloadValidator.Status.INVALID;
                        reason = "Observed category amounts and applicable deductions must be arrays";
                    } else if (!values.has("amounts") || !elements.has(category.getKey())
                            || java.util.Set.of("41", "58").contains(category.getKey()) && !values.has("discounts")) {
                        reason = "Observed total requires actual amount field, all category entries and explicit applicable fuel/nonfuel deduction list";
                    } else {
                        BigInteger total = BigInteger.ZERO;
                        boolean valid = true;
                        for (JsonNode amount : values.path("amounts")) {
                            if (!amount.isTextual() || !amount.textValue().matches("[0-9]{1,12}")) valid = false;
                            else total = total.add(new BigInteger(amount.textValue()));
                        }
                        if (java.util.Set.of("41", "58").contains(category.getKey())) {
                            for (JsonNode discount : values.path("discounts")) {
                                if (!discount.isTextual() || !discount.textValue().matches("[0-9]{1,12}")) valid = false;
                                else total = total.subtract(new BigInteger(discount.textValue()));
                            }
                        } else if (values.has("discounts")) valid = false;
                        JsonNode emitted = elements.path(category.getKey());
                        valid &= emitted.isTextual() && emitted.textValue().matches("[0-9]{1,8}");
                        status = valid && total.signum() >= 0 && total.equals(new BigInteger(emitted.textValue()))
                                ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED : Chapter12SegmentPayloadValidator.Status.INVALID;
                        reason = "Bounded observed " + category.getValue() + " total compared independently"
                                + "; fuel/nonfuel deduct only observed applicable coupons/discounts. Category assignment, completeness and authenticity remain unverified; no tax rate, allocation, rounding or aggregate-conflict interpretation is inferred";
                    }
                }
            }
            findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG100-R-015", "standard-total-" + category.getValue(), status, reason));
        }
        return java.util.List.copyOf(findings);
    }

    private static void merchantProducts(JsonNode context, JsonNode products, boolean adjusted, String stage, ValidationResult result) {
        String rule = adjusted ? "007" : "017";
        if (!context.has("fuelMerchant")) {
            result.addWarning("REVIEW_REQUIRED: merchant applicability requires observed fuelMerchant evidence");
            return;
        }
        if (!context.path("fuelMerchant").isBoolean()) {
            error(result, stage, rule, "fuelMerchant evidence must be boolean");
            return;
        }
        if (!context.path("fuelMerchant").booleanValue()) return;
        JsonNode purchased = context.get("purchasedProductCodes"), complete = context.get("purchasedProductsComplete");
        if (complete != null && !complete.isBoolean() || purchased != null && !purchased.isArray()) {
            error(result, stage, rule, "Purchased product codes must be an array and completeness must be boolean");
            return;
        }
        if (purchased == null || complete == null || !complete.booleanValue()) {
            result.addWarning("REVIEW_REQUIRED: complete observed purchased-product inventory required; no fuel/nonfuel purchase is inferred");
            return;
        }
        Set<String> reported = new HashSet<>();
        for (JsonNode product : products) if (product.path("ProductCode").isTextual()) reported.add(product.path("ProductCode").textValue());
        for (JsonNode code : purchased) {
            if (!code.isTextual() || !code.textValue().matches("[0-9]{3}")) error(result, stage, rule, "Observed purchased code must be N3 text");
            else if (!reported.contains(code.textValue())) error(result, stage, rule,
                    "Fuel merchant omitted an applicable observed purchased fuel/nonfuel product code");
        }
    }

    private static void physical(JsonNode segment, JsonNode products, boolean adjusted, String stage, ValidationResult result) {
        if (!segment.has("SerializedSegment")) {
            result.addWarning("REVIEW_REQUIRED: physical delimiter/length checks require an actual SerializedSegment");
            return;
        }
        char fs = '\u001c';
        StringBuilder body = new StringBuilder();
        for (int i = 0; i < products.size(); i++) {
            JsonNode product = products.get(i);
            for (String field : new String[]{"ProductCode", "UnitOfMeasure", "Quantity", "UnitPrice", "ProductAmount"}) {
                if (text(product, field) == null) return;
            }
            body.append(text(product, "ProductCode")).append(text(product, "UnitOfMeasure")).append(text(product, "Quantity"))
                    .append('\\').append(text(product, "UnitPrice")).append('\\').append(text(product, "ProductAmount"))
                    .append(i + 1 == products.size() ? fs : '\\');
        }
        String expected = text(segment, "SegmentType") + fs + text(segment, "SegmentLength") + fs
                + text(segment, "ServiceLevel") + text(segment, "NumberOfProducts") + body;
        String actual = text(segment, "SerializedSegment"), declared = text(segment, "SegmentLength");
        if (actual == null || !expected.equals(actual)) error(result, stage, adjusted ? "008" : "020", "Serialized product fields/order/delimiters differ from actual values");
        if (actual == null || !actual.chars().allMatch(c -> c <= 127) || !digits(declared, 3, 3)
                || actual.length() != Integer.parseInt(declared) || body.length() > 370) {
            error(result, stage, adjusted ? "009" : "023", "Measured ASCII product segment length/body cap differs from declared length");
        }
    }

    private static void decimal(JsonNode product, String field, String location, String stage, String rule, ValidationResult result) {
        String value = text(product, field);
        if (!Atl105DecimalPrefix.isValid(value) || value.length() > 9) {
            error(result, stage, rule, location + " " + field + " must encode 0-3 decimal places with all fractional digits");
        }
    }

    private static boolean digits(String value, int min, int max) {
        return value != null && value.length() >= min && value.length() <= max && value.matches("[0-9]+");
    }

    private static String text(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return value != null && value.isTextual() ? value.textValue() : null;
    }

    private static void error(ValidationResult result, String stage, String rule, String message) {
        result.addError(stage, message + " (SEG" + stage.substring(7, 10) + "-R-" + rule + ")");
    }
}
