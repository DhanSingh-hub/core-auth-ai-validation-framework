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
 * Validates a Segment 104 (Purchase Card Data Segment) payload against ATL105 Section 12.5
 * rules.
 *
 * <p>Rule anchors correspond to {@code SEG104-R-###} in
 * {@code specifications/ATL105/docs/specs/kb/segment-104/coverage/segment-104-rule-catalog.json}.
 *
 * <p>Item 1 baseline validator (Coverage Closure) per SEGMENT-100-TRAINING-METHODOLOGY.md,
 * replicating the {@link Segment101PayloadValidator} pattern. PROVISIONAL items P-01
 * (Ship-to/Ship-from Postal Code format) and P-02 (Segment 104 cardinality) are called out
 * inline; those checks will tighten once SME input arrives.
 */
public final class Segment104PayloadValidator {
    private static final String SOURCE = "Segment104Payload";

    private static final Pattern SEGMENT_LENGTH_PATTERN = Pattern.compile("^[0-9]{3}$");
    private static final Pattern NUMERIC_MAX_7 = Pattern.compile("^[0-9]{1,7}$");
    private static final Pattern FIXED_3_DIGITS = Pattern.compile("^[0-9]{3}$");
    private static final Pattern ALNUM_MAX_16 = Pattern.compile("^[A-Za-z0-9]{1,16}$");
    private static final Pattern ALNUM_MAX_10 = Pattern.compile("^[A-Za-z0-9]{1,10}$");
    private static final Pattern ZIP_PLUS_4 = Pattern.compile("^[0-9]{5}-[0-9]{4}$");

    private static final int MAX_SEGMENT_LENGTH = 86;

    private static final String PURCHASE_CARD_SEGMENT_KEY = "Purchase Card Data Segment";
    private static final String STANDARD_SEGMENT_KEY = "Standard Segment";

    private final ObjectMapper objectMapper;

    public Segment104PayloadValidator() {
        this(new ObjectMapper());
    }

    Segment104PayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path aiJsonFile) {
        String documentId = aiJsonFile == null ? "segment-104-payload" : aiJsonFile.getFileName().toString();
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
        ValidationResult result = new ValidationResult("segment-104-payload");
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

        JsonNode purchaseCardSegment = request.path(PURCHASE_CARD_SEGMENT_KEY);
        if (!purchaseCardSegment.isObject()) {
            result.addError(SOURCE, "Purchase Card Data Segment object is required (SEG104-R-002)");
            return;
        }

        // SEG104-R-001: Purchase Card Data Segment requires the Standard Segment (Segment 100) sibling
        if (!request.path(STANDARD_SEGMENT_KEY).isObject()) {
            result.addError(SOURCE, "Standard Segment (Segment 100) is required when Purchase Card Data Segment is present (SEG104-R-001)");
        }

        // SEG104-R-020 (PROVISIONAL P-02): at most one Segment 104 per message
        int seg104Count = 0;
        Iterator<Map.Entry<String, JsonNode>> topFields = request.fields();
        while (topFields.hasNext()) {
            Map.Entry<String, JsonNode> entry = topFields.next();
            JsonNode value = entry.getValue();
            if (value.isObject() && "104".equals(value.path("SegmentType").asText(null))) {
                seg104Count++;
            }
        }
        if (seg104Count > 1) {
            result.addError(SOURCE, "Only one Segment 104 (Purchase Card Data Segment) is allowed per message (SEG104-R-020); found " + seg104Count);
        }

        validatePurchaseCardSegment(purchaseCardSegment, result);
    }

    private void validatePurchaseCardSegment(JsonNode segment, ValidationResult result) {
        // SEG104-R-004: Segment Type is 104
        String segmentType = segment.path("SegmentType").asText(null);
        if (!"104".equals(segmentType)) {
            result.addError(SOURCE, "Purchase Card Data Segment.SegmentType must be 104 (SEG104-R-004); found " + segmentType);
        }

        // SEG104-R-005 and SEG104-R-006: Segment Length is 3 digits and within the 86-char ceiling
        String segmentLength = segment.path("SegmentLength").asText(null);
        if (segmentLength == null || !SEGMENT_LENGTH_PATTERN.matcher(segmentLength).matches()) {
            result.addError(SOURCE, "Purchase Card Data Segment.SegmentLength must be exactly 3 digits (SEG104-R-005)");
        } else {
            int declared = Integer.parseInt(segmentLength);
            if (declared > MAX_SEGMENT_LENGTH) {
                result.addError(SOURCE, "Purchase Card Data Segment.SegmentLength " + declared
                    + " exceeds maximum " + MAX_SEGMENT_LENGTH + " (SEG104-R-006)");
            }
        }

        // Conditional fields 3-10: independently conditional, no interdependency stated in Section 12.5.
        checkOptionalPattern(segment, "PurchaseCode", ALNUM_MAX_16, "alphanumeric max 16", "SEG104-R-007", result);
        checkOptionalPattern(segment, "PcTaxAmount", NUMERIC_MAX_7, "numeric max 7 digits", "SEG104-R-008", result);
        checkOptionalPattern(segment, "PcFreightAmount", NUMERIC_MAX_7, "numeric max 7 digits", "SEG104-R-009", result);
        checkOptionalPattern(segment, "PcDutyAmount", NUMERIC_MAX_7, "numeric max 7 digits", "SEG104-R-010", result);
        checkOptionalPattern(segment, "ShipToCountryCode", FIXED_3_DIGITS, "exactly 3 digits", "SEG104-R-011", result);
        checkPostalCode(segment, "ShipToPostalCode", "SEG104-R-012", result);
        checkPostalCode(segment, "ShipFromPostalCode", "SEG104-R-013", result);
        checkOptionalPattern(segment, "DirectMarketingInvoiceNumber", ALNUM_MAX_10, "alphanumeric max 10", "SEG104-R-014", result);
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
            result.addError(SOURCE, "Purchase Card Data Segment." + field + " must be " + description
                + " (" + ruleId + ")");
        }
    }

    /**
     * PROVISIONAL P-01: the spec's "Representation" text (nnnnn-nnnn) conflicts with its
     * "Valid Codes/Values" text ("Any valid postal code") for this AN(10) field. Accept
     * either the strict US ZIP+4 shape or a general alphanumeric value up to 10 characters;
     * anything else is a non-blocking warning, not a hard error, pending SME confirmation.
     */
    private static void checkPostalCode(JsonNode segment, String field, String ruleId, ValidationResult result) {
        if (!segment.has(field)) {
            return;
        }
        String value = segment.path(field).asText(null);
        if (value == null || value.isBlank()) {
            return;
        }
        boolean matchesZipPlus4 = ZIP_PLUS_4.matcher(value).matches();
        boolean matchesGeneralAlnum = ALNUM_MAX_10.matcher(value).matches();
        if (!matchesZipPlus4 && !matchesGeneralAlnum) {
            result.addWarning("Purchase Card Data Segment." + field + " value '" + value
                + "' matches neither the strict US ZIP+4 pattern nor a general alphanumeric-10 pattern; "
                + "flagged for SME review (" + ruleId + ", PROVISIONAL P-01)");
        }
    }
}
