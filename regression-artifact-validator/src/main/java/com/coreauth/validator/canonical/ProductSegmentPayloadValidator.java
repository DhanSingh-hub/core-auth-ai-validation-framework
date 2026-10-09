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
        if (comdata.isBoolean()) isComdata = comdata.booleanValue();
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
        Set<String> fuelCodes = new HashSet<>();
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
                    if (!fuel) nonfuelSeen = true;
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
                if ("950".equals(code) || "951".equals(code)) tax = tax.add(number);
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
            if (!adjusted && tax.signum() > 0 && digits(text(standard, "TaxAmount"), 1, 12)
                    && !tax.equals(new BigInteger(text(standard, "TaxAmount")))) {
                error(result, stage, "016", "Tax product total differs from Segment 100 TaxAmount");
            }
        }
        result.addWarning("REVIEW_REQUIRED: physical delimiter/length checks require a serialized product segment; "
                + "primary fuel/EV/merchant applicability needs independent transaction context");
        return result;
    }

    private static void decimal(JsonNode product, String field, String location, String stage, String rule, ValidationResult result) {
        String value = text(product, field);
        if (!Atl105DecimalPrefix.isValid(value)) {
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
