package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;

import java.util.List;

/** Validates the common Segment 118 wire boundary for request and response core fields. */
public final class Segment118WireFormatValidator {
    private static final String SOURCE = "Segment118WireFormat";
    private static final char FIELD_SEPARATOR = 0x1c;
    private static final List<String> CORE_FIELD_ORDER = List.of(
        "SegmentType", "SegmentLength", "SequenceNumber", "InformationByte",
        "TerminalIdentifier", "PromptCode", "PromptCodePending", "DeviceCardTableVersion",
        "CardTableLoadVersion", "CardTableType", "LoadControlKey", "HostDiscountTimestamp", "BlockNumber"
    );

    public ValidationResult validateRequest(List<String> coreFields, String serializedSegment) {
        ValidationResult result = new ValidationResult("segment-118-request-wire-format");
        validateCoreFields(coreFields, serializedSegment, result);
        if (serializedSegment == null) {
            result.addError(SOURCE, "serialized Segment 118 request is required (SEG118-R-005)");
            return result;
        }
        String expectedPrefix = String.join(String.valueOf(FIELD_SEPARATOR), safe(coreFields)) + FIELD_SEPARATOR;
        if (!serializedSegment.startsWith(expectedPrefix)) {
            result.addError(SOURCE,
                "request core fields 1-13 must preserve every Field Separator, including after field 13 (SEG118-R-005)");
        }
        return result;
    }

    public ValidationResult validateResponse(List<String> coreFields, String serializedSegment) {
        ValidationResult result = new ValidationResult("segment-118-response-wire-format");
        validateCoreFields(coreFields, serializedSegment, result);
        if (serializedSegment == null) {
            result.addError(SOURCE, "serialized Segment 118 response is required (SEG118-R-005)");
            return result;
        }
        if (serializedSegment.indexOf(FIELD_SEPARATOR) >= 0) {
            result.addError(SOURCE, "response core fields must be positional without Field Separators (SEG118-R-005)");
        }
        String expectedPrefix = String.join("", safe(coreFields));
        if (!serializedSegment.startsWith(expectedPrefix)) {
            result.addError(SOURCE, "response core fields 1-13 must be serialized in specification order (SEG118-R-005)");
        }
        return result;
    }

    public ValidationResult validateFieldOrder(List<String> fieldNames) {
        ValidationResult result = new ValidationResult("segment-118-field-order");
        if (!CORE_FIELD_ORDER.equals(fieldNames)) {
            result.addError(SOURCE, "Segment 118 core fields must follow Section 12.16 order (SEG118-R-005)");
        }
        return result;
    }

    public static List<String> coreFieldOrder() {
        return CORE_FIELD_ORDER;
    }

    private static void validateCoreFields(List<String> coreFields, String serializedSegment, ValidationResult result) {
        if (coreFields == null || coreFields.size() != CORE_FIELD_ORDER.size()) {
            result.addError(SOURCE, "Segment 118 serialization requires exactly 13 ordered core fields (SEG118-R-005)");
            return;
        }
        if (coreFields.stream().filter(value -> value != null).anyMatch(value -> value.indexOf(FIELD_SEPARATOR) >= 0)) {
            result.addError(SOURCE, "core field values must not contain the Field Separator character (SEG118-R-005)");
        }
        if (serializedSegment != null && serializedSegment.isEmpty()) {
            result.addError(SOURCE, "serialized Segment 118 core fields must not be empty (SEG118-R-005)");
        }
    }

    private static List<String> safe(List<String> fields) {
        return fields == null ? List.of() : fields.stream().map(value -> value == null ? "" : value).toList();
    }
}