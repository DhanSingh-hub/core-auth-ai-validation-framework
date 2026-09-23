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
 * Validates a Segment 101 (Fleet Data Segment) payload against ATL105 Section 12.2 rules.
 *
 * <p>Rule anchors correspond to {@code SEG101-R-###} in
 * {@code specifications/ATL105/docs/specs/kb/segment-101/coverage/segment-101-rule-catalog.json}.
 *
 * <p>Item 1 baseline validator (Coverage Closure) per SEGMENT-100-TRAINING-METHODOLOGY.md.
 * PROVISIONAL items P-01, P-02, P-03, P-05, P-06, P-07 are called out inline; those checks
 * will tighten once SME input arrives.
 */
public final class Segment101PayloadValidator {
    private static final String SOURCE = "Segment101Payload";

    private static final Pattern SEGMENT_LENGTH_PATTERN = Pattern.compile("^[0-9]{3}$");
    private static final Pattern NUMERIC_MAX_8 = Pattern.compile("^[0-9]{1,8}$");
    private static final Pattern ALNUM_MAX_8 = Pattern.compile("^[A-Za-z0-9]{1,8}$");
    private static final Pattern ALNUM_MAX_10 = Pattern.compile("^[A-Za-z0-9]{1,10}$");
    private static final Pattern ALNUM_MAX_12 = Pattern.compile("^[A-Za-z0-9]{1,12}$");

    private static final int BASE_MAX_SEGMENT_LENGTH = 61;

    private static final Set<String> VALID_FLEET_TAG_CODES = Set.of(
        "DLS", "DLN", "PON", "INV", "TRP", "UNT", "TLH", "DOB", "ZIP",
        "END", "MID", "VIN", "TRA", "HUB", "TLR", "CBA", "VHT"
    );

    private static final Map<String, Pattern> FLEET_TAG_DATA_PATTERNS = Map.ofEntries(
        Map.entry("DLS", Pattern.compile("^[A-Za-z0-9]{1,3}$")),
        Map.entry("DLN", Pattern.compile("^[A-Za-z0-9]{1,22}$")),
        Map.entry("PON", Pattern.compile("^[A-Za-z0-9]{1,31}$")),
        Map.entry("INV", Pattern.compile("^[A-Za-z0-9]{1,31}$")),
        Map.entry("TRP", Pattern.compile("^[A-Za-z0-9]{1,15}$")),
        Map.entry("UNT", Pattern.compile("^[A-Za-z0-9]{1,31}$")),
        Map.entry("TLH", Pattern.compile("^[0-9]{1,6}$")),
        Map.entry("DOB", Pattern.compile("^[0-9]{8}$")),
        Map.entry("ZIP", Pattern.compile("^[A-Za-z0-9]{1,9}$")),
        Map.entry("END", Pattern.compile("^[A-Za-z0-9]{1,31}$")),
        Map.entry("MID", Pattern.compile("^[A-Za-z0-9]{1,31}$")),
        Map.entry("VIN", Pattern.compile("^[A-Za-z0-9]{1,17}$")),
        Map.entry("TRA", Pattern.compile("^[A-Za-z0-9]{1,15}$")),
        Map.entry("HUB", Pattern.compile("^[0-9]{1,9}$")),
        Map.entry("TLR", Pattern.compile("^[A-Za-z0-9]{1,15}$")),
        Map.entry("CBA", Pattern.compile("^[0-9]{1,6}$")),
        Map.entry("VHT", Pattern.compile("^[A-Za-z0-9]{1,10}$"))
    );

    private static final String FLEET_SEGMENT_KEY = "Fleet Data Segment";
    private static final String ENHANCED_FLEET_SEGMENT_KEY = "Enhanced Fleet Data Segment";
    private static final String STANDARD_SEGMENT_KEY = "Standard Segment";
    private static final Set<String> COMPLETION_TRANSACTION_TYPES = Set.of("S");

    private final ObjectMapper objectMapper;

    public Segment101PayloadValidator() {
        this(new ObjectMapper());
    }

    Segment101PayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path aiJsonFile) {
        String documentId = aiJsonFile == null ? "segment-101-payload" : aiJsonFile.getFileName().toString();
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
        ValidationResult result = new ValidationResult("segment-101-payload");
        validatePayload(payload, result);
        return result;
    }

    private void validatePayload(JsonNode payload, ValidationResult result) {
        if (payload == null || !payload.isObject()) {
            result.addError(SOURCE, "AI JSON root must be an object");
            return;
        }
        JsonNode request = payload.path("Financial Request");
        if (!request.isObject()) {
            result.addError(SOURCE, "Financial Request object is required");
            return;
        }

        JsonNode fleetSegment = request.path(FLEET_SEGMENT_KEY);
        if (!fleetSegment.isObject()) {
            result.addError(SOURCE, "Fleet Data Segment object is required (SEG101-R-002)");
            return;
        }

        // SEG101-R-001: Fleet Data Segment requires the Standard Segment (Segment 100) sibling
        if (!request.path(STANDARD_SEGMENT_KEY).isObject()) {
            result.addError(SOURCE, "Standard Segment (Segment 100) is required when Fleet Data Segment is present (SEG101-R-001)");
        }

        // SEG101-R-003: Mutual exclusion with Segment 145 (Enhanced Fleet)
        if (request.has(ENHANCED_FLEET_SEGMENT_KEY)) {
            result.addError(SOURCE, "Segment 101 (Fleet Data Segment) must not coexist with Segment 145 (Enhanced Fleet Data Segment) (SEG101-R-003)");
        }
        for (JsonNode sibling : request) {
            if (sibling.isObject() && "145".equals(sibling.path("SegmentType").asText(null))) {
                result.addError(SOURCE, "Segment 101 (Fleet Data Segment) must not coexist with Segment 145 (Enhanced Fleet Data Segment) (SEG101-R-003)");
            }
        }

        // SEG101-R-026: at most one Segment 101 per message
        int seg101Count = 0;
        Iterator<Map.Entry<String, JsonNode>> topFields = request.fields();
        while (topFields.hasNext()) {
            Map.Entry<String, JsonNode> entry = topFields.next();
            JsonNode value = entry.getValue();
            if (value.isObject() && "101".equals(value.path("SegmentType").asText(null))) {
                seg101Count++;
            }
        }
        if (seg101Count > 1) {
            result.addError(SOURCE, "Only one Segment 101 (Fleet Data Segment) is allowed per message (SEG101-R-026); found " + seg101Count);
        }

        validateFleetSegment(fleetSegment, request, result);
    }

    private void validateFleetSegment(JsonNode segment, JsonNode request, ValidationResult result) {
        // SEG101-R-004: Segment Type is 101
        String segmentType = segment.path("SegmentType").asText(null);
        if (!"101".equals(segmentType)) {
            result.addError(SOURCE, "Fleet Data Segment.SegmentType must be 101 (SEG101-R-004); found " + segmentType);
        }

        // SEG101-R-005 and SEG101-R-006: Segment Length is 3 digits and within base max
        String segmentLength = segment.path("SegmentLength").asText(null);
        if (segmentLength == null || !SEGMENT_LENGTH_PATTERN.matcher(segmentLength).matches()) {
            result.addError(SOURCE, "Fleet Data Segment.SegmentLength must be exactly 3 digits (SEG101-R-005)");
        } else {
            int declared = Integer.parseInt(segmentLength);
            boolean tagsPresent = hasAnyFleetTag(segment);
            // Base cap enforced only when no Fleet Tags are present; PROVISIONAL P-01 covers the Auth Completion case.
            if (!tagsPresent && declared > BASE_MAX_SEGMENT_LENGTH) {
                result.addError(SOURCE, "Fleet Data Segment.SegmentLength " + declared
                    + " exceeds base maximum " + BASE_MAX_SEGMENT_LENGTH + " (SEG101-R-006)");
            }
        }

        // Base fields (fields 3-13): conditional, but when present must match type/length rules.
        // Rule anchors SEG101-R-009 through SEG101-R-019.
        checkOptionalPattern(segment, "Odometer", NUMERIC_MAX_8, "numeric max 8 digits", "SEG101-R-009", result);
        checkOptionalPattern(segment, "VehicleNumber", ALNUM_MAX_10, "alphanumeric max 10", "SEG101-R-010", result);
        checkOptionalPattern(segment, "JobNumber", ALNUM_MAX_10, "alphanumeric max 10", "SEG101-R-011", result);
        checkOptionalPattern(segment, "DriverIdentificationNumber", ALNUM_MAX_10, "alphanumeric max 10", "SEG101-R-012", result);
        checkOptionalPattern(segment, "FleetEmployeeNumber", ALNUM_MAX_10, "alphanumeric max 10", "SEG101-R-013", result);
        checkOptionalPattern(segment, "LicenseNumber", ALNUM_MAX_10, "alphanumeric max 10", "SEG101-R-014", result);
        checkOptionalPattern(segment, "JobId", ALNUM_MAX_12, "alphanumeric max 12", "SEG101-R-015", result);
        checkOptionalPattern(segment, "DepartmentNumber", ALNUM_MAX_12, "alphanumeric max 12", "SEG101-R-016", result);
        checkOptionalPattern(segment, "CustomerData", ALNUM_MAX_12, "alphanumeric max 12", "SEG101-R-017", result);
        checkUserId(segment, result);
        checkOptionalPattern(segment, "VehicleIdNumber", ALNUM_MAX_8, "alphanumeric max 8", "SEG101-R-019", result);

        // Fleet Tags 1-5 (fields 14-18): SEG101-R-020 through SEG101-R-024
        boolean tagsPresent = hasAnyFleetTag(segment);
        if (tagsPresent) {
            boolean isCompletion = isAuthCompletion(request);
            if (!isCompletion) {
                result.addError(SOURCE, "Fleet Tag fields are only allowed in Auth Completion (0220) messages (SEG101-R-020)");
            }
            validateFleetTag(segment, "FleetTag1", result);
            validateFleetTag(segment, "FleetTag2", result);
            validateFleetTag(segment, "FleetTag3", result);
            validateFleetTag(segment, "FleetTag4", result);
            validateFleetTag(segment, "FleetTag5", result);
        }
    }

    private static boolean hasAnyFleetTag(JsonNode segment) {
        return hasNonBlank(segment, "FleetTag1")
            || hasNonBlank(segment, "FleetTag2")
            || hasNonBlank(segment, "FleetTag3")
            || hasNonBlank(segment, "FleetTag4")
            || hasNonBlank(segment, "FleetTag5");
    }

    private static boolean isAuthCompletion(JsonNode request) {
        JsonNode standard = request.path(STANDARD_SEGMENT_KEY);
        String tt = standard.path("PromptCode").path("TransactionType").asText(null);
        return tt != null && COMPLETION_TRANSACTION_TYPES.contains(tt);
    }

    private static void validateFleetTag(JsonNode segment, String tagField, ValidationResult result) {
        String value = segment.path(tagField).asText(null);
        if (value == null || value.isBlank()) {
            return;
        }
        if (value.length() > 34) {
            result.addError(SOURCE, "Fleet Data Segment." + tagField
                + " exceeds 34-byte maximum (SEG101-R-022)");
            return;
        }
        if (value.length() < 3) {
            result.addError(SOURCE, "Fleet Data Segment." + tagField
                + " must contain a 3-byte tag code followed by data (SEG101-R-022)");
            return;
        }
        String code = value.substring(0, 3);
        String data = value.substring(3);
        if (!VALID_FLEET_TAG_CODES.contains(code)) {
            result.addError(SOURCE, "Fleet Data Segment." + tagField + " code '" + code
                + "' is not one of the valid fleet-tag codes (SEG101-R-023)");
            return;
        }
        Pattern pattern = FLEET_TAG_DATA_PATTERNS.get(code);
        if (pattern != null && !pattern.matcher(data).matches()) {
            result.addError(SOURCE, "Fleet Data Segment." + tagField + " data '" + data
                + "' does not match the format required for code " + code + " (SEG101-R-024)");
        }
    }

    private static void checkOptionalPattern(JsonNode segment, String field, Pattern pattern,
                                             String description, String ruleId, ValidationResult result) {
        if (!segment.has(field)) {
            return;
        }
        String value = segment.path(field).asText(null);
        if (value == null || value.isBlank()) {
            return;
        }
        if (!pattern.matcher(value).matches()) {
            result.addError(SOURCE, "Fleet Data Segment." + field + " must be " + description
                + " (" + ruleId + ")");
        }
    }

    private static void checkUserId(JsonNode segment, ValidationResult result) {
        if (!segment.has("UserId")) {
            return;
        }
        String value = segment.path("UserId").asText(null);
        if (value == null || value.isBlank()) {
            return;
        }
        if (!ALNUM_MAX_12.matcher(value).matches()) {
            result.addError(SOURCE, "Fleet Data Segment.UserId must be alphanumeric max 12 (SEG101-R-018)");
            return;
        }
        if (value.chars().allMatch(ch -> ch == '0')) {
            result.addError(SOURCE, "Fleet Data Segment.UserId must not be all zeros (SEG101-R-018)");
        }
    }

    private static boolean hasNonBlank(JsonNode node, String field) {
        String v = node.path(field).asText(null);
        return v != null && !v.isBlank();
    }
}
