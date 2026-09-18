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
 * Validates a Segment 103 (EBT Data Segment) payload against ATL105 Section 12.4 rules.
 *
 * <p>Rule anchors correspond to {@code SEG103-R-###} in
 * {@code docs/specs/kb/segment-103/coverage/segment-103-rule-catalog.json}.
 *
 * <p>Item 1 baseline validator (Coverage Closure) per SEGMENT-100-TRAINING-METHODOLOGY.md.
 * Wire-serialization-only rules (SEG103-R-002, 006, 007, 008, 019, 020, 021) are cataloged
 * but not enforced here because they are not representable as JSON structural checks or
 * because they remain PROVISIONAL pending SME input; see the KB README for the full list.
 */
public final class Segment103PayloadValidator {
    private static final String SOURCE = "Segment103Payload";

    private static final Pattern SEGMENT_LENGTH_PATTERN = Pattern.compile("^[0-9]{3,4}$");
    private static final Pattern NUMERIC_MAX_10 = Pattern.compile("^[0-9]{1,10}$");
    private static final Pattern WIC_DISCOUNT_BLOCK = Pattern.compile("^9752[0-9]{3}[0CD][0-9]{12}$");
    private static final Pattern WIC_PRODUCT_TOTAL_LENGTH = Pattern.compile("^[0-9]{4}$");
    private static final Pattern EBT_PROGRAM_TOTAL_LENGTH = Pattern.compile("^[0-9]{3}$");

    private static final int MAX_SEGMENT_LENGTH = 3334;
    private static final int WIC_DISCOUNT_MAX_LENGTH = 40;
    private static final int WIC_DISCOUNT_BLOCK_LENGTH = 20;
    private static final int WIC_PRODUCT_DATA_MAX = 3001;
    private static final int WIC_PRODUCT_TOTAL_LENGTH_MAX_VALUE = 2997;
    private static final int EBT_PROGRAM_TOTAL_LENGTH_MAX_VALUE = 264;
    private static final int EBT_PROGRAM_SUBELEMENT_MAX_BYTES = 44;
    private static final int EBT_PROGRAM_SUBELEMENT_MIN_COUNT = 1;
    private static final int EBT_PROGRAM_SUBELEMENT_MAX_COUNT = 6;
    private static final Set<String> VALID_EBT_PROGRAM_TAGS = Set.of("50", "IT", "51", "52");

    private static final String EBT_SEGMENT_KEY = "EBT Data Segment";
    private static final String STANDARD_SEGMENT_KEY = "Standard Segment";

    private final ObjectMapper objectMapper;

    public Segment103PayloadValidator() {
        this(new ObjectMapper());
    }

    Segment103PayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path aiJsonFile) {
        String documentId = aiJsonFile == null ? "segment-103-payload" : aiJsonFile.getFileName().toString();
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
        ValidationResult result = new ValidationResult("segment-103-payload");
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

        JsonNode ebtSegment = request.path(EBT_SEGMENT_KEY);
        if (!ebtSegment.isObject()) {
            result.addError(SOURCE, "EBT Data Segment object is required (SEG103-R-002)");
            return;
        }

        // SEG103-R-001: EBT Data Segment requires the Standard Segment (Segment 100) sibling
        if (!request.path(STANDARD_SEGMENT_KEY).isObject()) {
            result.addError(SOURCE, "Standard Segment (Segment 100) is required when EBT Data Segment is present (SEG103-R-001)");
        }

        // SEG103-R-018: at most one Segment 103 per message
        int seg103Count = 0;
        Iterator<Map.Entry<String, JsonNode>> topFields = request.fields();
        while (topFields.hasNext()) {
            Map.Entry<String, JsonNode> entry = topFields.next();
            JsonNode value = entry.getValue();
            if (value.isObject() && "103".equals(value.path("SegmentType").asText(null))) {
                seg103Count++;
            }
        }
        if (seg103Count > 1) {
            result.addError(SOURCE, "Only one EBT Data Segment (103) is allowed per message (SEG103-R-018); found " + seg103Count);
        }

        validateEbtSegment(ebtSegment, result);
    }

    private void validateEbtSegment(JsonNode segment, ValidationResult result) {
        // SEG103-R-003: Segment Type is 103
        String segmentType = segment.path("SegmentType").asText(null);
        if (!"103".equals(segmentType)) {
            result.addError(SOURCE, "EBT Data Segment.SegmentType must be 103 (SEG103-R-003); found " + segmentType);
        }

        // SEG103-R-004 and SEG103-R-005: Segment Length is 3 or 4 digits and within overall max
        String segmentLength = segment.path("SegmentLength").asText(null);
        if (segmentLength == null || !SEGMENT_LENGTH_PATTERN.matcher(segmentLength).matches()) {
            result.addError(SOURCE, "EBT Data Segment.SegmentLength must be 3 or 4 digits (SEG103-R-004)");
        } else {
            int declared = Integer.parseInt(segmentLength);
            if (declared > MAX_SEGMENT_LENGTH) {
                result.addError(SOURCE, "EBT Data Segment.SegmentLength " + declared
                    + " exceeds maximum " + MAX_SEGMENT_LENGTH + " (SEG103-R-005)");
            }
        }

        checkOptionalPattern(segment, "ClerkId", NUMERIC_MAX_10, "numeric max 10 digits", "SEG103-R-009", result);
        checkOptionalPattern(segment, "VoucherId", NUMERIC_MAX_10, "numeric max 10 digits", "SEG103-R-010", result);

        validateWicDiscountAmount(segment, result);
        validateWicProductData(segment, result);
        validateEbtProgramData(segment, result);
    }

    private static void validateWicDiscountAmount(JsonNode segment, ValidationResult result) {
        if (!segment.has("WicDiscountAmount")) {
            return;
        }
        String value = segment.path("WicDiscountAmount").asText(null);
        if (value == null || value.isBlank()) {
            return;
        }
        if (value.length() > WIC_DISCOUNT_MAX_LENGTH || value.length() % WIC_DISCOUNT_BLOCK_LENGTH != 0) {
            result.addError(SOURCE, "EBT Data Segment.WicDiscountAmount must be composed of 20-byte blocks up to 40 bytes total (SEG103-R-012)");
            return;
        }
        for (int i = 0; i < value.length(); i += WIC_DISCOUNT_BLOCK_LENGTH) {
            String block = value.substring(i, i + WIC_DISCOUNT_BLOCK_LENGTH);
            if (!WIC_DISCOUNT_BLOCK.matcher(block).matches()) {
                result.addError(SOURCE, "EBT Data Segment.WicDiscountAmount block '" + block
                    + "' must match Account Type 97 + Amount Type 52 + Currency Code + signed amount (SEG103-R-011)");
            }
        }
    }

    private static void validateWicProductData(JsonNode segment, ValidationResult result) {
        JsonNode node = segment.path("WicProductData");
        if (!node.isObject()) {
            return;
        }
        String totalLength = node.path("totalLength").asText(null);
        String data = node.path("data").asText("");
        if (totalLength == null || !WIC_PRODUCT_TOTAL_LENGTH.matcher(totalLength).matches()) {
            result.addError(SOURCE, "EBT Data Segment.WicProductData.totalLength must be a 4-digit numeric value (SEG103-R-013)");
            return;
        }
        int declared = Integer.parseInt(totalLength);
        if (declared > WIC_PRODUCT_TOTAL_LENGTH_MAX_VALUE) {
            result.addError(SOURCE, "EBT Data Segment.WicProductData.totalLength " + declared
                + " exceeds maximum " + WIC_PRODUCT_TOTAL_LENGTH_MAX_VALUE + " (SEG103-R-013)");
        }
        if (declared != data.length()) {
            result.addError(SOURCE, "EBT Data Segment.WicProductData.totalLength " + declared
                + " does not match actual data length " + data.length() + " (SEG103-R-013)");
        }
        if (4 + data.length() > WIC_PRODUCT_DATA_MAX) {
            result.addError(SOURCE, "EBT Data Segment.WicProductData exceeds maximum length "
                + WIC_PRODUCT_DATA_MAX + " bytes (SEG103-R-013)");
        }
    }

    private static void validateEbtProgramData(JsonNode segment, ValidationResult result) {
        JsonNode node = segment.path("EbtProgramData");
        if (!node.isObject()) {
            return;
        }
        String totalLength = node.path("totalLength").asText(null);
        if (totalLength == null || !EBT_PROGRAM_TOTAL_LENGTH.matcher(totalLength).matches()) {
            result.addError(SOURCE, "EBT Data Segment.EbtProgramData.totalLength must be a 3-digit numeric value (SEG103-R-014)");
        } else {
            int declared = Integer.parseInt(totalLength);
            if (declared > EBT_PROGRAM_TOTAL_LENGTH_MAX_VALUE) {
                result.addError(SOURCE, "EBT Data Segment.EbtProgramData.totalLength " + declared
                    + " exceeds maximum " + EBT_PROGRAM_TOTAL_LENGTH_MAX_VALUE + " (SEG103-R-014)");
            }
        }

        JsonNode subelements = node.path("subelements");
        if (!subelements.isArray()) {
            return;
        }
        int count = subelements.size();
        if (count < EBT_PROGRAM_SUBELEMENT_MIN_COUNT || count > EBT_PROGRAM_SUBELEMENT_MAX_COUNT) {
            result.addError(SOURCE, "EBT Data Segment.EbtProgramData must contain 1-6 Program Data subelements (SEG103-R-015); found " + count);
        }
        for (JsonNode sub : subelements) {
            String tag = sub.path("tag").asText(null);
            String data = sub.path("data").asText("");
            if (tag == null || !VALID_EBT_PROGRAM_TAGS.contains(tag)) {
                result.addError(SOURCE, "EBT Data Segment.EbtProgramData subelement TAG '" + tag
                    + "' is not one of the documented values 50, IT, 51, 52 (SEG103-R-016)");
            }
            if (data.length() > EBT_PROGRAM_SUBELEMENT_MAX_BYTES) {
                result.addError(SOURCE, "EBT Data Segment.EbtProgramData subelement data exceeds "
                    + EBT_PROGRAM_SUBELEMENT_MAX_BYTES + "-byte maximum (SEG103-R-015)");
            }
            if ("50".equals(tag)) {
                String accountType = sub.path("accountType").asText(null);
                if (!"98".equals(accountType)) {
                    result.addError(SOURCE, "EBT Data Segment.EbtProgramData subelement with TAG 50 must set accountType to 98 (SEG103-R-017)");
                }
            }
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
            result.addError(SOURCE, "EBT Data Segment." + field + " must be " + description
                + " (" + ruleId + ")");
        }
    }
}
