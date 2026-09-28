package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Validates source-confirmed Proprietary Data Load Segment 118 rules.
 *
 * <p>Rules are anchored to {@code SEG118-R-001} through {@code SEG118-R-030} in
 * {@code specifications/ATL105/docs/specs/kb/segment-118/coverage/segment-118-rule-catalog.json}.
 * This covers the 13 core fields and the header fields of all five conditional Prompt-Code
 * payloads (901, 902, 903, 904, 905). Repeat-block internals (e.g., the full Custom Receipt
 * Text or Host Discount item lists) and the Information Byte value catalog remain outside
 * deterministic pass/fail checks pending SME decisions.
 */
public final class Segment118PayloadValidator {
    private static final String SOURCE = "Segment118Payload";
    private static final Pattern NUMERIC_4 = Pattern.compile("^[0-9]{4}$");
    private static final Pattern NUMERIC_6 = Pattern.compile("^[0-9]{6}$");
    private static final Pattern NUMERIC_1 = Pattern.compile("^[0-9]$");
    private static final Pattern ALPHANUMERIC_13 = Pattern.compile("^[A-Za-z0-9]{13}$");
    private static final Pattern NUMERIC_3 = Pattern.compile("^[0-9]{3}$");
    private static final Pattern NUMERIC_4_PENDING = Pattern.compile("^[0-9]{4}$");
    private static final Pattern NUMERIC_35 = Pattern.compile("^[0-9]{35}$");
    private static final Pattern ALPHANUMERIC_MAX_60 = Pattern.compile("^[A-Za-z0-9]{1,60}$");
    private static final Pattern NUMERIC_12 = Pattern.compile("^[0-9]{12}$");

    private static final Set<String> VALID_PROMPT_CODES = Set.of("901", "902", "903", "904", "905");
    private static final Set<String> VALID_PROMPT_CODE_PENDING = Set.of("0901", "0902", "0904", "0981");
    private static final Set<String> VALID_CARD_TABLE_TYPES = Set.of("0001", "0002", "0003", "0004", "0005", "0006");

    private final ObjectMapper objectMapper;

    public Segment118PayloadValidator() {
        this(new ObjectMapper());
    }

    Segment118PayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path aiJsonFile) {
        String documentId = aiJsonFile == null ? "segment-118-payload" : aiJsonFile.getFileName().toString();
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
        ValidationResult result = new ValidationResult("segment-118-payload");
        validatePayload(payload, result);
        return result;
    }

    private void validatePayload(JsonNode payload, ValidationResult result) {
        if (payload == null || !payload.isObject()) {
            result.addError(SOURCE, "AI JSON root must be an object");
            return;
        }
        JsonNode request = payload.path("Proprietary Data Load Request");
        if (!request.isObject()) {
            result.addError(SOURCE, "Proprietary Data Load Request object is required (SEG118-R-024)");
            return;
        }
        if (request.has("Standard Message Data Segment")) {
            result.addError(SOURCE, "Proprietary Data Load Request must not contain Segment 100 (SEG118-R-024)");
        }

        checkRequiredPresent(request, "MessageFormatVersionIdentifier", "SEG118-R-024", result);
        checkRequiredPresent(request, "NumberOfSegments", "SEG118-R-024", result);

        JsonNode segment = request.path("Proprietary Data Load Segment");
        if (!segment.isObject()) {
            result.addError(SOURCE, "Proprietary Data Load Segment object is required (SEG118-R-024)");
            return;
        }
        validateSegment(segment, result);
    }

    private static void validateSegment(JsonNode segment, ValidationResult result) {
        checkRequiredValue(segment, "SegmentType", "118", "SEG118-R-006", result);

        String segmentLength = text(segment, "SegmentLength");
        if (!matches(segmentLength, NUMERIC_4)) {
            result.addError(SOURCE, "Proprietary Data Load Segment.SegmentLength must be exactly 4 digits (SEG118-R-007)");
        } else if (Integer.parseInt(segmentLength) > 3800) {
            result.addError(SOURCE, "Proprietary Data Load Segment.SegmentLength exceeds 3800 (SEG118-R-002)");
        }

        checkRequiredPattern(segment, "SequenceNumber", NUMERIC_6, "exactly 6 numeric characters", "SEG118-R-008", result);
        checkRequiredPattern(segment, "InformationByte", NUMERIC_1, "one numeric character", "SEG118-R-009", result);
        checkRequiredPattern(segment, "TerminalIdentifier", ALPHANUMERIC_13, "exactly 13 alphanumeric characters", "SEG118-R-010", result);

        String promptCode = text(segment, "PromptCode");
        if (promptCode == null || !VALID_PROMPT_CODES.contains(promptCode)) {
            result.addError(SOURCE, "Proprietary Data Load Segment.PromptCode must be one of 901, 902, 903, 904, 905 (SEG118-R-011)");
        }

        String promptCodePending = text(segment, "PromptCodePending");
        if (promptCodePending == null || !matches(promptCodePending, NUMERIC_4_PENDING)
            || !VALID_PROMPT_CODE_PENDING.contains(promptCodePending)) {
            result.addError(SOURCE, "Proprietary Data Load Segment.PromptCodePending must be one of 0901, 0902, 0904, 0981 (SEG118-R-012)");
        }

        checkRequiredPattern(segment, "DeviceCardTableVersion", NUMERIC_35, "exactly 35 numeric digits", "SEG118-R-013", result);
        checkRequiredPattern(segment, "CardTableLoadVersion", NUMERIC_35, "exactly 35 numeric digits", "SEG118-R-014", result);

        String cardTableType = text(segment, "CardTableType");
        if (cardTableType == null || !VALID_CARD_TABLE_TYPES.contains(cardTableType)) {
            result.addError(SOURCE, "Proprietary Data Load Segment.CardTableType must be one of 0001-0006 (SEG118-R-015)");
        }

        checkRequiredPattern(segment, "LoadControlKey", ALPHANUMERIC_MAX_60, "alphanumeric with maximum length 60", "SEG118-R-016", result);
        checkRequiredPattern(segment, "HostDiscountTimestamp", NUMERIC_12, "exactly 12 numeric characters (CCYYMMDDHHMM)", "SEG118-R-017", result);
        checkOptionalPattern(segment, "BlockNumber", NUMERIC_3, "exactly 3 numeric characters", "SEG118-R-018", result);

        checkPromptCodePayload(segment, promptCode, result);
    }

    private static void checkPromptCodePayload(JsonNode segment, String promptCode, ValidationResult result) {
        if (promptCode == null) {
            return;
        }
        switch (promptCode) {
            case "901" -> checkRequiredPresent(segment, "NumberOfReceiptTextLines", "SEG118-R-019", result);
            case "902" -> checkRequiredPresent(segment, "CardTableData", "SEG118-R-020", result);
            case "903" -> checkRequiredMaxLength(segment, "SiteConfigurationData", 3600, "SEG118-R-021", result);
            case "904" -> checkRequiredPresent(segment, "NumberOfDiscounts", "SEG118-R-022", result);
            case "905" -> checkRequiredMaxLength(segment, "FuelVolumeData", 3600, "SEG118-R-023", result);
            default -> { }
        }
    }

    private static void checkRequiredValue(JsonNode node, String field, String expected, String ruleId,
                                           ValidationResult result) {
        if (!expected.equals(text(node, field))) {
            result.addError(SOURCE, "Proprietary Data Load Segment." + field + " must be " + expected + " (" + ruleId + ")");
        }
    }

    private static void checkRequiredPresent(JsonNode node, String field, String ruleId, ValidationResult result) {
        String value = text(node, field);
        if (value == null || value.isBlank()) {
            result.addError(SOURCE, field + " is required (" + ruleId + ")");
        }
    }

    private static void checkRequiredMaxLength(JsonNode node, String field, int maxLength, String ruleId,
                                               ValidationResult result) {
        String value = text(node, field);
        if (value == null || value.isBlank()) {
            result.addError(SOURCE, field + " is required (" + ruleId + ")");
        } else if (value.length() > maxLength) {
            result.addError(SOURCE, field + " exceeds maximum length " + maxLength + " (" + ruleId + ")");
        }
    }

    private static void checkRequiredPattern(JsonNode node, String field, Pattern pattern, String description,
                                             String ruleId, ValidationResult result) {
        if (!matches(text(node, field), pattern)) {
            result.addError(SOURCE, "Proprietary Data Load Segment." + field + " must be " + description + " (" + ruleId + ")");
        }
    }

    private static void checkOptionalPattern(JsonNode node, String field, Pattern pattern, String description,
                                             String ruleId, ValidationResult result) {
        String value = text(node, field);
        if (value != null && !value.isBlank() && !matches(value, pattern)) {
            result.addError(SOURCE, "Proprietary Data Load Segment." + field + " must be " + description + " (" + ruleId + ")");
        }
    }

    private static String text(JsonNode node, String field) {
        return node.has(field) && !node.path(field).isNull() ? node.path(field).asText() : null;
    }

    private static boolean matches(String value, Pattern pattern) {
        return value != null && pattern.matcher(value).matches();
    }
}
