package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;

/** Preflight validation for the full AI JSON contract consumed by the ATL105 converter. */
public final class Segment100AiJsonCompatibilityValidator {
    private final ObjectMapper objectMapper;

    public Segment100AiJsonCompatibilityValidator() {
        this(new ObjectMapper());
    }

    Segment100AiJsonCompatibilityValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path aiJsonFile) {
        String documentId = aiJsonFile == null ? "segment-100-ai-json" : aiJsonFile.getFileName().toString();
        ValidationResult result = new ValidationResult(documentId);
        if (aiJsonFile == null) {
            result.addError("Segment100AiJsonCompatibility", "AI JSON file is required");
            return result;
        }
        try {
            validatePayload(objectMapper.readTree(aiJsonFile.toFile()), result);
        } catch (IOException exception) {
            result.addError("Segment100AiJsonCompatibility", "AI JSON is not readable: " + exception.getMessage());
        }
        return result;
    }

    public ValidationResult validatePayload(JsonNode payload) {
        ValidationResult result = new ValidationResult("segment-100-ai-json");
        validatePayload(payload, result);
        return result;
    }

    private void validatePayload(JsonNode payload, ValidationResult result) {
        if (payload == null || !payload.isObject()) {
            result.addError("Segment100AiJsonCompatibility", "AI JSON root must be an object");
            return;
        }
        JsonNode request = payload.path("Financial Request");
        if (!request.isObject()) {
            result.addError("Segment100AiJsonCompatibility", "Financial Request object is required");
            return;
        }
        if (!"ATL105".equals(request.path("MessageType").asText(null))) {
            result.addError("Segment100AiJsonCompatibility", "MessageType must be ATL105");
        }
        String declaredCount = request.path("NumSegments").asText(null);
        if (declaredCount == null || !declaredCount.matches("^[0-9]{1,2}$")) {
            result.addError("Segment100AiJsonCompatibility", "NumSegments must be one or two digits");
        }

        int actualCount = 0;
        JsonNode standardSegment = request.path("Standard Segment");
        Iterator<Map.Entry<String, JsonNode>> fields = request.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            if (field.getKey().endsWith("Segment") && field.getValue().isObject()) {
                actualCount++;
                validateSegmentHeader(field.getKey(), field.getValue(), result);
            }
        }
        if (declaredCount != null && declaredCount.matches("^[0-9]{1,2}$")
                && Integer.parseInt(declaredCount) != actualCount) {
            result.addError("Segment100AiJsonCompatibility", "NumSegments " + declaredCount
                    + " does not match segment objects found: " + actualCount);
        }

        if (!standardSegment.isObject()) {
            result.addError("Segment100AiJsonCompatibility", "Standard Segment object is required");
            return;
        }
        validateStandardSegment(standardSegment, result);
    }

    private static void validateSegmentHeader(String segmentName, JsonNode segment, ValidationResult result) {
        String segmentType = segment.path("SegmentType").asText(null);
        if (segmentType == null || segmentType.isBlank()) {
            result.addError("Segment100AiJsonCompatibility", segmentName + ".SegmentType is required");
        }
        String segmentLength = segment.path("SegmentLength").asText(null);
        if (segmentLength == null || !segmentLength.matches("^[0-9]{3,4}$")) {
            result.addError("Segment100AiJsonCompatibility", segmentName + ".SegmentLength must be 3-4 digits");
        }
    }

    private static void validateStandardSegment(JsonNode segment, ValidationResult result) {
        if (!"100".equals(segment.path("SegmentType").asText(null))) {
            result.addError("Segment100AiJsonCompatibility", "Standard Segment.SegmentType must be 100");
        }
        String informationByte = segment.path("InformationByte").asText(null);
        if (informationByte == null || !SetValues.INFORMATION_BYTES.contains(informationByte)) {
            result.addError("Segment100AiJsonCompatibility", "Standard Segment.InformationByte must be ?, 0, or 1");
        }
        if (segment.path("TerminalID").asText(null) == null) {
            result.addError("Segment100AiJsonCompatibility", "Standard Segment.TerminalID is required");
        }
        JsonNode promptCode = segment.path("PromptCode");
        if (!promptCode.isObject()) {
            result.addError("Segment100AiJsonCompatibility", "Standard Segment.PromptCode object is required");
        } else {
            String transactionType = promptCode.path("TransactionType").asText(null);
            String cardType = promptCode.path("CardType").asText(null);
            if (transactionType == null || transactionType.length() != 1) {
                result.addError("Segment100AiJsonCompatibility", "PromptCode.TransactionType must be one character");
            }
            if (cardType == null || !cardType.matches("^[A-Za-z0-9]{2,3}$")) {
                result.addError("Segment100AiJsonCompatibility", "PromptCode.CardType must be 2-3 alphanumeric characters");
            }
        }
        String sequenceNumber = segment.path("SequenceNumber").asText(null);
        if (sequenceNumber == null || !sequenceNumber.matches("^[0-9]{6}$")) {
            result.addError("Segment100AiJsonCompatibility", "Standard Segment.SequenceNumber must be six digits");
        }
        if (!segment.has("PartialApprovalIndicator")) {
            result.addError("Segment100AiJsonCompatibility", "Standard Segment.PartialApprovalIndicator is required");
        }
    }

    private static final class SetValues {
        private static final java.util.Set<String> INFORMATION_BYTES = java.util.Set.of("?", "0", "1");
    }
}
