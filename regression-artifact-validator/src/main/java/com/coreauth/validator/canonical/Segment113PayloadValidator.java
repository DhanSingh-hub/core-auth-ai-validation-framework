package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Validates a Segment 113 (ECA/TeleCheck® Data Segment) payload against ATL105 Section 12.12 rules.
 *
 * <p>Rule anchors correspond to {@code SEG113-R-###} in
 * {@code docs/specs/kb/segment-113/coverage/segment-113-rule-catalog.json}.
 *
 * <p>Item 1 baseline validator (Coverage Closure) per SEGMENT-100-TRAINING-METHODOLOGY.md.
 * Like Segment 108, Segment 113 belongs exclusively to its own dedicated message family
 * (the ECA/TeleCheck® Service Transaction Request), so the payload root key is
 * {@code "ECA/TeleCheck Service Transaction Request"}, not {@code "Financial Request"}.
 * Unlike Segment 108, Segment 113 is a CONDITIONAL member of its message family's Data
 * Section 3 (it need not always be present), and it has no field-order reversal quirk.
 * Wire-serialization-only rules (SEG113-R-006, 007), metadata (SEG113-R-016), and
 * cross-message/cross-segment/compatibility/lifecycle rules (SEG113-R-011's Void condition,
 * SEG113-R-014's cross-segment condition, SEG113-R-017, SEG113-R-018) are cataloged but not
 * enforced here per SME direction; see the KB README for the full list.
 */
public final class Segment113PayloadValidator {
    private static final String SOURCE = "Segment113Payload";

    private static final Pattern SEGMENT_LENGTH_PATTERN = Pattern.compile("^[0-9]{3}$");
    private static final Pattern ALPHANUMERIC_MAX_6 = Pattern.compile("^[A-Za-z0-9]{1,6}$");
    private static final Pattern NUMERIC_MAX_10 = Pattern.compile("^[0-9]{1,10}$");
    private static final Pattern ALPHANUMERIC_MAX_22 = Pattern.compile("^[A-Za-z0-9]{1,22}$");
    private static final Pattern ALPHANUMERIC_MAX_25 = Pattern.compile("^[A-Za-z0-9]{1,25}$");
    private static final Pattern ALPHANUMERIC_MAX_7 = Pattern.compile("^[A-Za-z0-9]{1,7}$");
    private static final Pattern ALPHANUMERIC_MAX_65 = Pattern.compile("^[A-Za-z0-9]{1,65}$");

    private static final int MAX_SEGMENT_LENGTH = 156;

    private static final String ECA_SEGMENT_KEY = "ECA/TeleCheck Data Segment";
    private static final String STANDARD_SEGMENT_KEY = "Standard Segment";
    private static final String REQUEST_ROOT_KEY = "ECA/TeleCheck Service Transaction Request";

    private final ObjectMapper objectMapper;

    public Segment113PayloadValidator() {
        this(new ObjectMapper());
    }

    Segment113PayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path aiJsonFile) {
        String documentId = aiJsonFile == null ? "segment-113-payload" : aiJsonFile.getFileName().toString();
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
        ValidationResult result = new ValidationResult("segment-113-payload");
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
            result.addError(SOURCE, "ECA/TeleCheck Service Transaction Request object is required");
            return;
        }

        // SEG113-R-002: Segment 113 is conditional; if absent, no further field checks apply
        JsonNode ecaSegment = request.path(ECA_SEGMENT_KEY);
        if (!ecaSegment.isObject()) {
            return;
        }

        // SEG113-R-001: ECA/TeleCheck Data Segment requires the Standard Segment (Segment 100) sibling
        if (!request.path(STANDARD_SEGMENT_KEY).isObject()) {
            result.addError(SOURCE, "Standard Segment (Segment 100) is required when ECA/TeleCheck Data Segment is present (SEG113-R-001)");
        }

        // SEG113-R-015: at most one Segment 113 per message
        int seg113Count = 0;
        Iterator<Map.Entry<String, JsonNode>> topFields = request.fields();
        while (topFields.hasNext()) {
            Map.Entry<String, JsonNode> entry = topFields.next();
            JsonNode value = entry.getValue();
            if (value.isObject() && "113".equals(value.path("SegmentType").asText(null))) {
                seg113Count++;
            }
        }
        if (seg113Count > 1) {
            result.addError(SOURCE, "Only one ECA/TeleCheck Data Segment (113) is allowed per message (SEG113-R-015); found " + seg113Count);
        }

        validateEcaSegment(ecaSegment, result);
    }

    private void validateEcaSegment(JsonNode segment, ValidationResult result) {
        // SEG113-R-003: Segment Type is 113
        String segmentType = segment.path("SegmentType").asText(null);
        if (!"113".equals(segmentType)) {
            result.addError(SOURCE, "ECA/TeleCheck Data Segment.SegmentType must be 113 (SEG113-R-003); found " + segmentType);
        }

        // SEG113-R-004 and SEG113-R-005: Segment Length is 3 digits and within overall max
        String segmentLength = segment.path("SegmentLength").asText(null);
        if (segmentLength == null || !SEGMENT_LENGTH_PATTERN.matcher(segmentLength).matches()) {
            result.addError(SOURCE, "ECA/TeleCheck Data Segment.SegmentLength must be 3 digits (SEG113-R-004)");
        } else {
            int declared = Integer.parseInt(segmentLength);
            if (declared > MAX_SEGMENT_LENGTH) {
                result.addError(SOURCE, "ECA/TeleCheck Data Segment.SegmentLength " + declared
                    + " exceeds maximum " + MAX_SEGMENT_LENGTH + " (SEG113-R-005)");
            }
        }

        // SEG113-R-008: ECA/TeleCheck Clerk ID is required, alphanumeric max 6
        String clerkId = segment.path("EcaClerkId").asText(null);
        if (clerkId == null || clerkId.isBlank()) {
            result.addError(SOURCE, "ECA/TeleCheck Data Segment.EcaClerkId is required (SEG113-R-008)");
        } else if (!ALPHANUMERIC_MAX_6.matcher(clerkId).matches()) {
            result.addError(SOURCE, "ECA/TeleCheck Data Segment.EcaClerkId must be alphanumeric max 6 characters (SEG113-R-008)");
        }

        checkOptionalPattern(segment, "EcaProductCode", ALPHANUMERIC_MAX_6, "alphanumeric max 6 characters", "SEG113-R-009", result);
        checkOptionalPattern(segment, "EcaPhoneNumber", NUMERIC_MAX_10, "numeric max 10 digits", "SEG113-R-010", result);
        checkOptionalPattern(segment, "EcaTraceId", ALPHANUMERIC_MAX_22, "alphanumeric max 22 characters", "SEG113-R-011", result);
        checkOptionalPattern(segment, "MerchantTraceId", ALPHANUMERIC_MAX_25, "alphanumeric max 25 characters", "SEG113-R-012", result);
        checkOptionalPattern(segment, "DenialRecordNumber", ALPHANUMERIC_MAX_7, "alphanumeric max 7 characters", "SEG113-R-013", result);
        checkOptionalPattern(segment, "ExtendedMicrData", ALPHANUMERIC_MAX_65, "alphanumeric max 65 characters", "SEG113-R-014", result);
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
            result.addError(SOURCE, "ECA/TeleCheck Data Segment." + field + " must be " + description
                + " (" + ruleId + ")");
        }
    }
}
