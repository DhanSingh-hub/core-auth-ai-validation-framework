package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * Validates the source-confirmed subset of TransArmor Load Data Segment 116 rules.
 *
 * <p>Rules are anchored to {@code SEG116-R-001} through {@code SEG116-R-009} in
 * {@code specifications/ATL105/docs/specs/kb/segment-116/coverage/segment-116-rule-catalog.json}.
 *
 * <p><b>Important:</b> Segment 116's own ATL105 sections (12.15, 11.7.5) are stub references to an
 * external "BUYPASS Platform ATL105 Specification Updates for TransArmor Processing" document that
 * is not part of this workspace's source material. This validator therefore only checks what is
 * independently confirmed or safely pattern-derived: the request envelope shape, Segment Type,
 * Segment Length format/maximum, and the three known TransArmor Load Response fields. It does not
 * and cannot validate Segment 116's remaining request fields, because their layout is unknown
 * (see {@code SEG116-SME-001}).
 */
public final class Segment116PayloadValidator {
    private static final String SOURCE = "Segment116Payload";
    private static final Pattern NUMERIC_3 = Pattern.compile("^[0-9]{3}$");
    private static final Pattern KEY_ID = Pattern.compile("^[A-Za-z0-9]{11}$");
    private static final Pattern KEY_DATA_LENGTH = Pattern.compile("^[0-9]{1,3}$");
    private static final Pattern KEY_DATA = Pattern.compile("^[A-Za-z0-9]{1,999}$");

    private final ObjectMapper objectMapper;

    public Segment116PayloadValidator() {
        this(new ObjectMapper());
    }

    Segment116PayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path aiJsonFile) {
        String documentId = aiJsonFile == null ? "segment-116-payload" : aiJsonFile.getFileName().toString();
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
        ValidationResult result = new ValidationResult("segment-116-payload");
        validatePayload(payload, result);
        return result;
    }

    private void validatePayload(JsonNode payload, ValidationResult result) {
        if (payload == null || !payload.isObject()) {
            result.addError(SOURCE, "AI JSON root must be an object");
            return;
        }
        JsonNode request = payload.path("TransArmor PKI Encryption and Tokenization Load Request");
        if (!request.isObject()) {
            result.addError(SOURCE, "TransArmor PKI Encryption and Tokenization Load Request object is required (SEG116-R-005)");
            return;
        }

        checkRequiredPresent(request, "MessageFormatVersionIdentifier", "SEG116-R-005", result);
        checkRequiredPresent(request, "NumberOfSegments", "SEG116-R-005", result);

        JsonNode segment = request.path("TransArmor Load Data Segment");
        if (!segment.isObject()) {
            result.addError(SOURCE, "TransArmor Load Data Segment object is required (SEG116-R-001)");
            return;
        }
        validateSegment(segment, result);

        JsonNode response = payload.path("TransArmor Load Response");
        if (response.isObject()) {
            validateResponse(response, result);
        }
    }

    private static void validateSegment(JsonNode segment, ValidationResult result) {
        checkRequiredValue(segment, "SegmentType", "116", "SEG116-R-003", result);

        String segmentLength = text(segment, "SegmentLength");
        if (!matches(segmentLength, NUMERIC_3)) {
            result.addError(SOURCE, "TransArmor Load Data Segment.SegmentLength must be exactly 3 digits (SEG116-R-004)");
        } else if (Integer.parseInt(segmentLength) > 50) {
            result.addError(SOURCE, "TransArmor Load Data Segment.SegmentLength exceeds 50 (SEG116-R-002)");
        }
    }

    private static void validateResponse(JsonNode response, ValidationResult result) {
        checkRequiredPattern(response, "KeyId", KEY_ID, "exactly 11 alphanumeric characters", "SEG116-R-007", result);
        checkRequiredPattern(response, "KeyDataLength", KEY_DATA_LENGTH, "numeric with maximum length 3 (000-999)", "SEG116-R-007", result);
        checkRequiredPattern(response, "KeyData", KEY_DATA, "alphanumeric with maximum length 999", "SEG116-R-007", result);
    }

    private static void checkRequiredValue(JsonNode node, String field, String expected, String ruleId,
                                           ValidationResult result) {
        if (!expected.equals(text(node, field))) {
            result.addError(SOURCE, "TransArmor Load Data Segment." + field + " must be " + expected + " (" + ruleId + ")");
        }
    }

    private static void checkRequiredPresent(JsonNode node, String field, String ruleId, ValidationResult result) {
        String value = text(node, field);
        if (value == null || value.isBlank()) {
            result.addError(SOURCE, "TransArmor PKI Encryption and Tokenization Load Request." + field + " is required (" + ruleId + ")");
        }
    }

    private static void checkRequiredPattern(JsonNode node, String field, Pattern pattern, String description,
                                             String ruleId, ValidationResult result) {
        if (!matches(text(node, field), pattern)) {
            result.addError(SOURCE, "TransArmor Load Response." + field + " must be " + description + " (" + ruleId + ")");
        }
    }

    private static String text(JsonNode node, String field) {
        return node.has(field) && !node.path(field).isNull() ? node.path(field).asText() : null;
    }

    private static boolean matches(String value, Pattern pattern) {
        return value != null && pattern.matcher(value).matches();
    }
}
