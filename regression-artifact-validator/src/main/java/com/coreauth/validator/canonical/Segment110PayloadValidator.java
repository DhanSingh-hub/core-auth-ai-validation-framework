package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Validates source-confirmed Check Data Segment 110 rules.
 *
 * <p>Rules are anchored to {@code SEG110-R-001} through {@code SEG110-R-021} in
 * {@code specifications/ATL105/docs/specs/kb/segment-110/coverage/segment-110-rule-catalog.json}.
 * Rules requiring SME decisions (the manually-entered/manually-keyed trigger, the Element 239
 * identity conflict, Extended MICR Data hosting-segment placement, and generic Financial
 * Transaction Request applicability) remain outside of deterministic pass/fail checks; only
 * their format-level constraints are enforced here.
 */
public final class Segment110PayloadValidator {
    private static final String SOURCE = "Segment110Payload";
    private static final Pattern NUMERIC_3 = Pattern.compile("^[0-9]{3}$");
    private static final Pattern NUMERIC_8 = Pattern.compile("^[0-9]{8}$");
    private static final Pattern NUMERIC_MAX_10 = Pattern.compile("^[0-9]{1,10}$");
    private static final Pattern ALPHANUMERIC_MAX_50 = Pattern.compile("^[A-Za-z0-9]{1,50}$");
    private static final Pattern ALPHANUMERIC_MAX_40 = Pattern.compile("^[A-Za-z0-9]{1,40}$");
    private static final Pattern ALPHANUMERIC_MAX_24 = Pattern.compile("^[A-Za-z0-9 ]{1,24}$");
    private static final Pattern ALPHANUMERIC_MAX_8 = Pattern.compile("^[A-Za-z0-9]{1,8}$");
    private static final Pattern STATE_CODE = Pattern.compile("^[A-Za-z]{2}$");

    private static final Set<String> VALID_STATE_CODES = Set.of(
        "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN",
        "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH",
        "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT",
        "VT", "VA", "WA", "WV", "WI", "WY", "GU", "PR", "VI", "AA", "AE", "AP", "XX",
        "AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT", "NA");
    private static final Set<String> VALID_CHECK_TYPES = Set.of("P", "C");
    private static final String REQUEST_ROOT_KEY = "ECA/TeleCheck Service Transaction Request";
    // Segment 100 + Data Section 3 Field Nos. 4-6 (110, 111, conditional 113) per Section 11.3.1.
    private static final Set<String> ECA_REQUEST_SEGMENT_TYPES = Set.of("100", "110", "111", "113");
    private static final int ECA_MIN_SEGMENTS = 3;
    private static final int ECA_MAX_SEGMENTS = 4;

    private final ObjectMapper objectMapper;

    public Segment110PayloadValidator() {
        this(new ObjectMapper());
    }

    Segment110PayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path aiJsonFile) {
        String documentId = aiJsonFile == null ? "segment-110-payload" : aiJsonFile.getFileName().toString();
        ValidationResult result = new ValidationResult(documentId);
        if (aiJsonFile == null) {
            result.addError(SOURCE, "AI JSON file is required");
            return result;
        }
        try {
            validatePayload(objectMapper.readTree(aiJsonFile.toFile()), result);
        } catch (IOException exception) {
            result.addError(SOURCE, "AI JSON is not readable: " + exception.getMessage());
        }
        return result;
    }

    public ValidationResult validatePayload(JsonNode payload) {
        ValidationResult result = new ValidationResult("segment-110-payload");
        validatePayload(payload, result);
        return result;
    }

    private void validatePayload(JsonNode payload, ValidationResult result) {
        if (payload == null || !payload.isObject()) {
            result.addError(SOURCE, "AI JSON root must be an object");
            return;
        }
        JsonNode request = payload.path(REQUEST_ROOT_KEY);
        if (!request.isObject()) {
            result.addError(SOURCE, REQUEST_ROOT_KEY + " object is required (SEG110-R-001)");
            return;
        }

        checkRequiredPresent(request, "MessageType", "SEG110-R-001", result);
        checkEcaComposition(request, result);

        JsonNode segment = request.path("Check Data Segment");
        if (!segment.isObject()) {
            result.addError(SOURCE, "Check Data Segment object is required (SEG110-R-001)");
            return;
        }
        validateSegment(segment, result);
    }

    private static void validateSegment(JsonNode segment, ValidationResult result) {
        checkRequiredValue(segment, "SegmentType", "110", "SEG110-R-004", result);

        String segmentLength = text(segment, "SegmentLength");
        if (!matches(segmentLength, NUMERIC_3)) {
            result.addError(SOURCE, "Check Data Segment.SegmentLength must be exactly 3 digits (SEG110-R-005)");
        } else if (Integer.parseInt(segmentLength) > 168) {
            result.addError(SOURCE, "Check Data Segment.SegmentLength exceeds 168 (SEG110-R-002)");
        }

        checkRequiredPattern(segment, "MICRData", ALPHANUMERIC_MAX_50,
            "alphanumeric with maximum length 50", "SEG110-R-008", result);
        checkOptionalPattern(segment, "DriversLicense", ALPHANUMERIC_MAX_40,
            "alphanumeric with maximum length 40", "SEG110-R-009", result);
        checkOptionalPattern(segment, "DateOfBirth", NUMERIC_8,
            "exactly 8 numeric characters (MMDDYYYY)", "SEG110-R-011", result);
        checkOptionalPattern(segment, "CheckNumber", ALPHANUMERIC_MAX_8,
            "alphanumeric with maximum length 8", "SEG110-R-013", result);
        checkOptionalPattern(segment, "CustomerPhoneNumber", NUMERIC_MAX_10,
            "numeric with maximum length 10", "SEG110-R-014", result);
        checkOptionalPattern(segment, "CustomerLastName", ALPHANUMERIC_MAX_24,
            "alphanumeric with maximum length 24", "SEG110-R-015", result);
        checkOptionalPattern(segment, "CheckIssueDate", NUMERIC_8,
            "exactly 8 numeric characters (MMDDYYYY)", "SEG110-R-016", result);
        checkOptionalPattern(segment, "AlternateMicrIndicator", Pattern.compile("^Y$"),
            "the fixed value Y when present", "SEG110-R-017", result);

        checkCheckType(segment, result);
        checkStateCode(segment, result);
    }

    private static void checkCheckType(JsonNode segment, ValidationResult result) {
        String checkType = text(segment, "CheckType");
        if (checkType == null || !VALID_CHECK_TYPES.contains(checkType)) {
            result.addError(SOURCE, "Check Data Segment.CheckType must be P (Personal) or C (Company) (SEG110-R-012)");
        }
    }

    private static void checkStateCode(JsonNode segment, ValidationResult result) {
        String driversLicense = text(segment, "DriversLicense");
        String stateCode = text(segment, "StateCode");
        if (driversLicense != null && !driversLicense.isBlank() && (stateCode == null || stateCode.isBlank())) {
            result.addError(SOURCE, "Check Data Segment.StateCode is required when DriversLicense is present (SEG110-R-010)");
            return;
        }
        if (stateCode == null || stateCode.isBlank()) {
            return;
        }
        if (!matches(stateCode, STATE_CODE) || !VALID_STATE_CODES.contains(stateCode.toUpperCase())) {
            result.addError(SOURCE, "Check Data Segment.StateCode must be a valid Appendix D state code (SEG110-R-010)");
        }
    }

    private static void checkRequiredValue(JsonNode node, String field, String expected, String ruleId,
                                           ValidationResult result) {
        if (!expected.equals(text(node, field))) {
            result.addError(SOURCE, "Check Data Segment." + field + " must be " + expected + " (" + ruleId + ")");
        }
    }

    private static void checkRequiredPresent(JsonNode node, String field, String ruleId, ValidationResult result) {
        String value = text(node, field);
        if (value == null || value.isBlank()) {
            result.addError(SOURCE, REQUEST_ROOT_KEY + "." + field + " is required (" + ruleId + ")");
        }
    }

    private static void checkEcaComposition(JsonNode request, ValidationResult result) {
        int segmentCount = 0;
        boolean hasVariableInformation = false;
        Iterator<Map.Entry<String, JsonNode>> fields = request.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String type = entry.getValue().isObject() ? text(entry.getValue(), "SegmentType") : null;
            if (type == null) {
                continue;
            }
            segmentCount++;
            hasVariableInformation |= "111".equals(type);
            if (!ECA_REQUEST_SEGMENT_TYPES.contains(type)) {
                result.addError(SOURCE, "Segment " + type + " ('" + entry.getKey() + "') is not allowed in an " + REQUEST_ROOT_KEY
                        + "; Data Section 3 carries only Segments 110, 111 and 113 (SEG110-R-021)");
            }
        }
        if (!hasVariableInformation) {
            result.addError(SOURCE, "Variable Information Data Segment (111) is required in Data Section 3 Field No. 5 (SEG110-R-021)");
        }

        String declared = text(request, "NumSegments");
        if (declared == null || declared.isBlank()) {
            result.addError(SOURCE, REQUEST_ROOT_KEY + ".NumSegments (Element 63) is required (SEG110-R-021)");
            return;
        }
        if (!declared.matches(DataSection1StructureValidator.ELEMENT_63_PATTERN)) {
            result.addError(SOURCE, REQUEST_ROOT_KEY + ".NumSegments (Element 63) must be one or two digits (SEG110-R-021)");
            return;
        }
        int count = Integer.parseInt(declared);
        if (count < ECA_MIN_SEGMENTS || count > ECA_MAX_SEGMENTS) {
            result.addError(SOURCE, REQUEST_ROOT_KEY + ".NumSegments (Element 63) declares " + count
                    + " but must be " + ECA_MIN_SEGMENTS + "-" + ECA_MAX_SEGMENTS + " (SEG110-R-021)");
        } else if (count != segmentCount) {
            result.addError(SOURCE, REQUEST_ROOT_KEY + ".NumSegments (Element 63) declares " + count
                    + " but the message contains " + segmentCount + " segments (SEG110-R-021)");
        }
    }

    private static void checkRequiredPattern(JsonNode node, String field, Pattern pattern, String description,
                                             String ruleId, ValidationResult result) {
        if (!matches(text(node, field), pattern)) {
            result.addError(SOURCE, "Check Data Segment." + field + " must be " + description + " (" + ruleId + ")");
        }
    }

    private static void checkOptionalPattern(JsonNode node, String field, Pattern pattern, String description,
                                             String ruleId, ValidationResult result) {
        String value = text(node, field);
        if (value != null && !value.isBlank() && !matches(value, pattern)) {
            result.addError(SOURCE, "Check Data Segment." + field + " must be " + description + " (" + ruleId + ")");
        }
    }

    private static String text(JsonNode node, String field) {
        return node.has(field) && !node.path(field).isNull() ? node.path(field).asText() : null;
    }

    private static boolean matches(String value, Pattern pattern) {
        return value != null && pattern.matcher(value).matches();
    }
}
