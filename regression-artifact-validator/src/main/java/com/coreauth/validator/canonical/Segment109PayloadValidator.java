package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * Validates source-confirmed Electronic Mail Request Segment 109 rules.
 *
 * <p>Rules are anchored to {@code SEG109-R-001} through {@code SEG109-R-018} in
 * {@code specifications/ATL105/docs/specs/kb/segment-109/coverage/segment-109-rule-catalog.json}.
 * Rules requiring SME decisions remain outside of deterministic pass/fail checks.
 */
public final class Segment109PayloadValidator {
    private static final String SOURCE = "Segment109Payload";
    private static final Pattern NUMERIC_1 = Pattern.compile("^[0-9]$");
    private static final Pattern NUMERIC_3 = Pattern.compile("^[0-9]{3}$");
    private static final Pattern NUMERIC_4 = Pattern.compile("^[0-9]{4}$");
    private static final Pattern NUMERIC_6 = Pattern.compile("^[0-9]{6}$");
    private static final Pattern ALPHANUMERIC_MAX_6 = Pattern.compile("^[A-Za-z0-9]{1,6}$");
    private static final Pattern ALPHANUMERIC_MAX_150 = Pattern.compile("^[A-Za-z0-9 ]{1,150}$");

    private final ObjectMapper objectMapper;

    public Segment109PayloadValidator() {
        this(new ObjectMapper());
    }

    Segment109PayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path aiJsonFile) {
        String documentId = aiJsonFile == null ? "segment-109-payload" : aiJsonFile.getFileName().toString();
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
        ValidationResult result = new ValidationResult("segment-109-payload");
        validatePayload(payload, result);
        return result;
    }

    private void validatePayload(JsonNode payload, ValidationResult result) {
        if (payload == null || !payload.isObject()) {
            result.addError(SOURCE, "AI JSON root must be an object");
            return;
        }
        JsonNode request = payload.path("Electronic Mail Request");
        if (!request.isObject()) {
            result.addError(SOURCE, "Electronic Mail Request object is required (SEG109-R-001)");
            return;
        }

        checkRequiredPattern(request, "MessageFormatVersionIdentifier", Pattern.compile("^.{6}$"),
            "exactly 6 characters", "SEG109-R-002", result);
        checkRequiredPattern(request, "NumberOfSegments", Pattern.compile("^[0-9]{2}$"),
            "exactly 2 numeric characters", "SEG109-R-002", result);

        JsonNode segment = request.path("Electronic Mail Data Segment");
        if (!segment.isObject()) {
            result.addError(SOURCE, "Electronic Mail Data Segment object is required (SEG109-R-003)");
            return;
        }
        validateSegment(segment, result);
    }

    private static void validateSegment(JsonNode segment, ValidationResult result) {
        checkRequiredValue(segment, "SegmentType", "109", "SEG109-R-004", result);

        String segmentLength = text(segment, "SegmentLength");
        if (!matches(segmentLength, NUMERIC_3)) {
            result.addError(SOURCE, "Electronic Mail Data Segment.SegmentLength must be exactly 3 digits (SEG109-R-005)");
        } else if (Integer.parseInt(segmentLength) > 232) {
            result.addError(SOURCE, "Electronic Mail Data Segment.SegmentLength exceeds 232 (SEG109-R-006)");
        }

        checkRequiredPattern(segment, "InformationByte", NUMERIC_1,
            "one numeric character", "SEG109-R-009", result);
        checkRequiredPresent(segment, "TerminalIdentifier", "SEG109-R-010", result);

        String promptCode = text(segment, "PromptCode");
        if (!"981".equals(promptCode) && !"995".equals(promptCode) && !"996".equals(promptCode)) {
            result.addError(SOURCE, "Electronic Mail Data Segment.PromptCode must be 981, 995, or 996 (SEG109-R-011)");
        }
        checkRequiredPattern(segment, "BlockNumber", NUMERIC_3,
            "exactly 3 numeric characters", "SEG109-R-012", result);
        checkOptionalPattern(segment, "EmployeeNumber", NUMERIC_4,
            "exactly 4 numeric characters", "SEG109-R-013", result);
        checkOptionalPattern(segment, "Password", ALPHANUMERIC_MAX_6,
            "alphanumeric with maximum length 6", "SEG109-R-014", result);
        checkRequiredPattern(segment, "SequenceNumber", NUMERIC_6,
            "exactly 6 numeric characters", "SEG109-R-015", result);
        checkOptionalPattern(segment, "LocalTime", NUMERIC_4,
            "exactly 4 numeric characters", "SEG109-R-016", result);
        checkOptionalPattern(segment, "ExtractDate", NUMERIC_6,
            "exactly 6 numeric characters", "SEG109-R-016", result);
        checkOptionalPattern(segment, "ExtractTime", NUMERIC_4,
            "exactly 4 numeric characters", "SEG109-R-016", result);
        checkTextData(segment, result);
    }

    private static void checkTextData(JsonNode segment, ValidationResult result) {
        String textDataLength = text(segment, "TextDataLength");
        String textData = text(segment, "TextData");
        if (textDataLength != null && !matches(textDataLength, NUMERIC_3)) {
            result.addError(SOURCE, "Electronic Mail Data Segment.TextDataLength must be exactly 3 digits (SEG109-R-017)");
        }
        if (textData != null && !matches(textData, ALPHANUMERIC_MAX_150)) {
            result.addError(SOURCE, "Electronic Mail Data Segment.TextData must be alphanumeric/space with maximum length 150 (SEG109-R-018)");
        }
        if (textDataLength != null && textData != null && matches(textDataLength, NUMERIC_3)
            && Integer.parseInt(textDataLength) != textData.length()) {
            result.addError(SOURCE, "Electronic Mail Data Segment.TextDataLength must equal TextData length (SEG109-R-018)");
        }
        if ((textDataLength == null) != (textData == null)) {
            result.addError(SOURCE, "TextDataLength and TextData must be populated together (SEG109-R-017)");
        }
    }

    private static void checkRequiredValue(JsonNode node, String field, String expected, String ruleId,
                                           ValidationResult result) {
        if (!expected.equals(text(node, field))) {
            result.addError(SOURCE, "Electronic Mail Data Segment." + field + " must be " + expected + " (" + ruleId + ")");
        }
    }

    private static void checkRequiredPresent(JsonNode node, String field, String ruleId, ValidationResult result) {
        String value = text(node, field);
        if (value == null || value.isBlank()) {
            result.addError(SOURCE, "Electronic Mail Data Segment." + field + " is required (" + ruleId + ")");
        }
    }

    private static void checkRequiredPattern(JsonNode node, String field, Pattern pattern, String description,
                                             String ruleId, ValidationResult result) {
        if (!matches(text(node, field), pattern)) {
            result.addError(SOURCE, "Electronic Mail Data Segment." + field + " must be " + description + " (" + ruleId + ")");
        }
    }

    private static void checkOptionalPattern(JsonNode node, String field, Pattern pattern, String description,
                                             String ruleId, ValidationResult result) {
        String value = text(node, field);
        if (value != null && !value.isBlank() && !matches(value, pattern)) {
            result.addError(SOURCE, "Electronic Mail Data Segment." + field + " must be " + description + " (" + ruleId + ")");
        }
    }

    private static String text(JsonNode node, String field) {
        return node.has(field) && !node.path(field).isNull() ? node.path(field).asText() : null;
    }

    private static boolean matches(String value, Pattern pattern) {
        return value != null && pattern.matcher(value).matches();
    }
}
