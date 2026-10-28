package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * Validates a Segment 114 (SKU Data Segment) payload against ATL105 Section 12.13 rules.
 *
 * <p>Rule anchors correspond to {@code SEG114-R-###} in
 * {@code specifications/ATL105/docs/specs/kb/segment-114/coverage/segment-114-rule-catalog.json}.
 *
 * <p>Checks every named SKU observation, including nested/repeated containers, encoded
 * length and optional supplied wire. Financial-request eligibility remains unresolved
 * under SEG114-SME-002; this validator does not invent a decision or certify a full message.
 */
public final class Segment114PayloadValidator {
    private static final String SOURCE = "Segment114Payload";
    private static final Pattern SEGMENT_LENGTH = Pattern.compile("^[0-9]{4}$");
    private static final Pattern SKU_DATA = Pattern.compile("^[A-Za-z0-9]{1,1000}$");
    private static final int MAX_SEGMENT_LENGTH = 1010;

    private final ObjectMapper objectMapper;

    public Segment114PayloadValidator() {
        this(new ObjectMapper());
    }

    Segment114PayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path aiJsonFile) {
        String documentId = aiJsonFile == null ? "segment-114-payload" : aiJsonFile.getFileName().toString();
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
        ValidationResult result = new ValidationResult("segment-114-payload");
        validatePayload(payload, result);
        return result;
    }

    private void validatePayload(JsonNode payload, ValidationResult result) {
        if (payload == null || !payload.isObject()) {
            result.addError(SOURCE, "AI JSON root must be an object");
            return;
        }

        inspect(payload, false, result);
    }

    private static void inspect(JsonNode node, boolean response, ValidationResult result) {
        if (node.isArray()) {
            for (JsonNode item : node) inspect(item, response, result);
        } else if (node.isObject()) {
            var fields = node.fields();
            while (fields.hasNext()) {
                var field = fields.next();
                boolean inResponse = response || "Loyalty Card Transaction Response".equals(field.getKey());
                if ("SKU Data Segment".equals(field.getKey())) {
                    if (inResponse) result.addError(SOURCE, "SKU Data Segment must not appear in a Loyalty Card Transaction Response (SEG114-R-013)");
                    if (field.getValue().isArray()) {
                        for (JsonNode segment : field.getValue()) checkSegment(segment, result);
                    } else checkSegment(field.getValue(), result);
                } else inspect(field.getValue(), inResponse, result);
            }
        }
    }

    private static void checkSegment(JsonNode segment, ValidationResult result) {
        if (!segment.isObject()) {
            result.addError(SOURCE, "SKU Data Segment must be an object (SEG114-R-008)");
            return;
        }
        String segmentType = segment.path("SegmentType").asText(null);
        if (!"114".equals(segmentType)) {
            result.addError(SOURCE, "SegmentType must be '114', got: " + segmentType + " (SEG114-R-003)");
        }

        String segmentLength = segment.path("SegmentLength").asText(null);
        if (segmentLength == null || !SEGMENT_LENGTH.matcher(segmentLength).matches()) {
            result.addError(SOURCE, "SegmentLength must be 4 numeric digits (0001-" + MAX_SEGMENT_LENGTH
                    + "), got: " + segmentLength + " (SEG114-R-004)");
        } else {
            int value = Integer.parseInt(segmentLength);
            if (value < 1 || value > MAX_SEGMENT_LENGTH) {
                result.addError(SOURCE, "SegmentLength " + value + " is outside 0001-" + MAX_SEGMENT_LENGTH
                        + " (SEG114-R-005; 1010 is authoritative over the Section 11.2.1 table's 1009 per SEG114-SME-001)");
            }
        }

        String skuData = segment.path("SKUData").asText(null);
        if (skuData == null || !SKU_DATA.matcher(skuData).matches()) {
            result.addError(SOURCE, "SKUData (Element 149) is required, alphanumeric, max 1000 characters, got: "
                    + skuData + " (SEG114-R-008)");
        }
        if (segmentLength != null && SEGMENT_LENGTH.matcher(segmentLength).matches() && skuData != null
                && Integer.parseInt(segmentLength) != 10 + skuData.length()) {
            result.addError(SOURCE, "SegmentLength must include the 3-byte type, 4-byte length and three separators "
                    + "as well as SKUData (SEG114-R-004)");
        }
        if (segment.has("serializedSegment")) {
            String expected = "114\u001c" + segmentLength + "\u001c" + skuData + "\u001c";
            if (!segment.path("serializedSegment").isTextual() || !expected.equals(segment.path("serializedSegment").textValue())) {
                result.addError(SOURCE, "Encoded SKU segment does not match field order/separators (SEG114-R-006)");
            }
        }
    }
}
