package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;

import java.util.List;
import java.util.Set;

/**
 * Item 1/3 validator for Segment 103 rules that are observable only at the
 * serialized message boundary.
 */
public final class Segment103WireFormatValidator {
    private static final String SOURCE = "Segment103WireFormat";
    private static final char FIELD_SEPARATOR = 0x1c;
    private static final List<String> FIELD_ORDER = List.of(
        "SegmentType", "SegmentLength", "ClerkId", "VoucherId",
        "WicDiscountAmount", "WicProductData", "EbtProgramData"
    );
    private static final Set<String> EWIC_PROMPT_CODES =
        Set.of("3086", "S086", "E086", "0086", "8086");

    public ValidationResult validateRequest(List<String> fields, String serializedSegment) {
        ValidationResult result = new ValidationResult("segment-103-request-wire-format");
        validateFieldCount(fields, result);
        if (serializedSegment == null) {
            result.addError(SOURCE, "serialized Segment 103 request is required (SEG103-R-007)");
            return result;
        }
        String expected = String.join(String.valueOf(FIELD_SEPARATOR), safe(fields));
        if (!expected.equals(serializedSegment)) {
            result.addError(SOURCE,
                "request must preserve a Field Separator after every Segment 103 field, including empty fields (SEG103-R-007)");
        }
        return result;
    }

    public ValidationResult validateResponse(List<String> fields, String serializedSegment) {
        ValidationResult result = new ValidationResult("segment-103-response-wire-format");
        validateFieldCount(fields, result);
        if (serializedSegment == null) {
            result.addError(SOURCE, "serialized Segment 103 response is required (SEG103-R-008)");
            return result;
        }
        if (serializedSegment.indexOf(FIELD_SEPARATOR) >= 0) {
            result.addError(SOURCE,
                "response must not contain a Field Separator between Segment 103 fields (SEG103-R-008)");
        }
        String expected = String.join("", safe(fields));
        if (!expected.equals(serializedSegment)) {
            result.addError(SOURCE,
                "response fields must be serialized in Section 12.4 order without separators (SEG103-R-006)");
        }
        return result;
    }

    public ValidationResult validateFieldOrder(List<String> fieldNames) {
        ValidationResult result = new ValidationResult("segment-103-field-order");
        if (!FIELD_ORDER.equals(fieldNames)) {
            result.addError(SOURCE,
                "Segment 103 fields must follow Section 12.4 order: " + FIELD_ORDER + " (SEG103-R-006)");
        }
        return result;
    }

    public ValidationResult validateContext(String origin, String promptCode, boolean returnTransaction) {
        ValidationResult result = new ValidationResult("segment-103-context");
        if (!"device".equalsIgnoreCase(origin)) {
            result.addError(SOURCE, "Segment 103 origin must be device (SEG103-R-019)");
        }
        if (promptCode != null && !EWIC_PROMPT_CODES.contains(promptCode)) {
            result.addError(SOURCE, "Unknown eWIC Prompt Code " + promptCode + " (SEG103-R-021)");
        }
        if (returnTransaction) {
            // Section 10.5.5.1: "These specifications do not support Return transactions."
            result.addError(SOURCE, "eWIC does not support Return transactions (SEG103-R-020)");
        }
        return result;
    }

    public static List<String> fieldOrder() {
        return FIELD_ORDER;
    }

    private static void validateFieldCount(List<String> fields, ValidationResult result) {
        if (fields == null || fields.size() != FIELD_ORDER.size()) {
            result.addError(SOURCE, "Segment 103 must contain exactly seven ordered fields (SEG103-R-006)");
        }
    }

    private static List<String> safe(List<String> fields) {
        return fields == null ? List.of() : fields.stream().map(value -> value == null ? "" : value).toList();
    }
}