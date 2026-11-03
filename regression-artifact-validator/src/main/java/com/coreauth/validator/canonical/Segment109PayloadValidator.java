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

        checkRequiredPattern(request, "MessageType", Pattern.compile("^.{6}$"),
            "exactly 6 characters", "SEG109-R-002", result);
        String declaredSegments = text(request, "NumSegments");
        if (!matches(declaredSegments, Pattern.compile("^[0-9]{1,2}$"))) {
            result.addError(SOURCE, "Electronic Mail Request.NumSegments (Element 63) must be one or two numeric characters (SEG109-R-002)");
        } else if (Integer.parseInt(declaredSegments) != 1) {
            result.addError(SOURCE, "Electronic Mail Request.NumSegments (Element 63) must be 1 (SEG109-R-002)");
        }

        JsonNode segment = request.path("Electronic Mail Data Segment");
        if (!segment.isObject()) {
            result.addError(SOURCE, "Electronic Mail Data Segment object is required (SEG109-R-003)");
        }
        int segmentCount = 0;
        java.util.Iterator<java.util.Map.Entry<String, JsonNode>> fields = request.fields();
        while (fields.hasNext()) {
            java.util.Map.Entry<String, JsonNode> entry = fields.next();
            if (!entry.getValue().isObject() || !entry.getValue().has("SegmentType")) {
                continue;
            }
            segmentCount++;
            if (!"109".equals(text(entry.getValue(), "SegmentType"))) {
                result.addError(SOURCE, "Segment " + text(entry.getValue(), "SegmentType") + " ('" + entry.getKey()
                    + "') is not allowed in an Electronic Mail Request (SEG109-R-001)");
            }
        }
        if (segmentCount != 1) {
            result.addError(SOURCE, "Electronic Mail Request must contain exactly one segment (SEG109-R-001)");
        }
        if (!segment.isObject()) {
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

    public ValidationResult validateProcessingObservation(JsonNode observation) {
        ValidationResult result = new ValidationResult("chapter-10-electronic-mail-observation");
        if (observation == null || !observation.isObject()) {
            result.addError(SOURCE, "Chapter 10.11 processing observation must be an object");
            return result;
        }
        String operation = text(observation, "operation");
        String expectedPrompt;
        String field;
        int maximum;
        String rule;
        if ("RETRIEVAL".equals(operation) || "PROPRIETARY_RETRIEVAL".equals(operation)) {
            expectedPrompt = "RETRIEVAL".equals(operation) ? "981" : "996";
            field = "printData";
            maximum = 750;
            rule = "SEG109-R-019";
        } else if ("SUBMISSION".equals(operation)) {
            expectedPrompt = "995";
            field = "submissionData";
            maximum = 217;
            rule = "SEG109-R-020";
        } else {
            result.addError(SOURCE, "Operation must be RETRIEVAL, PROPRIETARY_RETRIEVAL or SUBMISSION (10.11)");
            return result;
        }
        JsonNode prompt = observation.get("promptCode");
        if (prompt == null || !prompt.isTextual() || !expectedPrompt.equals(prompt.textValue())) {
            result.addError(SOURCE, "Prompt Code for " + operation + " must be textual " + expectedPrompt + " (" + rule + ")");
        }
        JsonNode data = observation.get(field);
        if (data == null) {
            result.addWarning("REVIEW_REQUIRED: " + field + " not observed; Chapter 10 byte limit is not asserted (" + rule + ")");
        } else if (!data.isTextual()) {
            result.addError(SOURCE, field + " must be a text-valued logical observation (" + rule + ")");
        } else if (data.textValue().chars().anyMatch(character -> character > 127)) {
            result.addWarning("REVIEW_REQUIRED: non-ASCII " + field + " needs transport-encoding evidence before counting bytes (" + rule + ")");
        } else if (data.textValue().length() > maximum) {
            result.addError(SOURCE, field + " exceeds " + maximum + " ASCII bytes per request (" + rule + ")");
        }
        result.addWarning("REVIEW_REQUIRED: logical Chapter 10.11 observation only; complete wire, retrieval timing, "
                + "submission confirmation and host storage are not asserted");
        return result;
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
