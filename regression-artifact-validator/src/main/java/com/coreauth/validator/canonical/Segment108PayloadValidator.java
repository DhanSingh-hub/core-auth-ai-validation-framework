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
 * Validates a Segment 108 (Loyalty Card Data Segment) payload against ATL105 Section 12.7 rules.
 *
 * <p>Rule anchors correspond to {@code SEG108-R-###} in
 * {@code docs/specs/kb/segment-108/coverage/segment-108-rule-catalog.json}.
 *
 * <p>Item 1 baseline validator (Coverage Closure) per SEGMENT-100-TRAINING-METHODOLOGY.md.
 * Unlike Segment 103 (an optional Financial Transaction Request companion), Segment 108
 * belongs exclusively to the dedicated Loyalty Card Transaction Request, so the payload root
 * key is {@code "Loyalty Card Transaction Request"}, not {@code "Financial Request"}.
 * Wire-serialization-only rules (SEG108-R-002, 006, 007), metadata rules (SEG108-R-022), and
 * cross-message/compatibility/lifecycle rules (SEG108-R-023, 024) are cataloged but not
 * enforced here; see the KB README for the full list.
 */
public final class Segment108PayloadValidator {
    private static final String SOURCE = "Segment108Payload";

    private static final Pattern SEGMENT_LENGTH_PATTERN = Pattern.compile("^[0-9]{3}$");
    private static final Pattern NUMERIC_MAX_6 = Pattern.compile("^[0-9]{1,6}$");
    private static final Pattern NUMERIC_MAX_24 = Pattern.compile("^[0-9]{1,24}$");
    private static final Pattern NUMERIC_MAX_19 = Pattern.compile("^[0-9]{1,19}$");
    private static final Pattern NUMERIC_MAX_8 = Pattern.compile("^[0-9]{1,8}$");
    private static final Pattern NUMERIC_MAX_5 = Pattern.compile("^[0-9]{1,5}$");
    private static final Pattern NUMERIC_MAX_10 = Pattern.compile("^[0-9]{1,10}$");
    private static final Pattern ALPHANUMERIC_MAX_38 = Pattern.compile("^[A-Za-z0-9]{1,38}$");
    private static final Pattern NUMERIC_FIXED_19 = Pattern.compile("^[0-9]{19}$");
    private static final Pattern EXPIRATION_DATE_MMYY = Pattern.compile("^(0[1-9]|1[0-2])[0-9]{2}$");

    private static final int MAX_SEGMENT_LENGTH = 142;

    private static final Set<String> VALID_UPDATE_CODES = Set.of("A", "C", "E", "I", "P", "S", "T", "U");
    private static final Set<String> VALID_PAYMENT_TENDER_TYPES = Set.of(
        "AX", "CK", "CS", "DB", "DN", "DS", "EB", "EC", "FL", "GC", "JC", "MC", "PC", "PR", "VS");
    private static final Set<String> VALID_LOYALTY_INFO_VERSIONS = Set.of("1", "2");

    private static final String LOYALTY_SEGMENT_KEY = "Loyalty Card Data Segment";
    private static final String STANDARD_SEGMENT_KEY = "Standard Segment";
    private static final String REQUEST_ROOT_KEY = "Loyalty Card Transaction Request";

    private final ObjectMapper objectMapper;

    public Segment108PayloadValidator() {
        this(new ObjectMapper());
    }

    Segment108PayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path aiJsonFile) {
        String documentId = aiJsonFile == null ? "segment-108-payload" : aiJsonFile.getFileName().toString();
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
        ValidationResult result = new ValidationResult("segment-108-payload");
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
            result.addError(SOURCE, "Loyalty Card Transaction Request object is required");
            return;
        }

        JsonNode loyaltySegment = request.path(LOYALTY_SEGMENT_KEY);
        if (!loyaltySegment.isObject()) {
            result.addError(SOURCE, "Loyalty Card Data Segment object is required (SEG108-R-002)");
            return;
        }

        // SEG108-R-001: Loyalty Card Data Segment requires the Standard Segment (Segment 100) sibling
        if (!request.path(STANDARD_SEGMENT_KEY).isObject()) {
            result.addError(SOURCE, "Standard Segment (Segment 100) is required when Loyalty Card Data Segment is present (SEG108-R-001)");
        }

        // SEG108-R-021: at most one Segment 108 per message
        int seg108Count = 0;
        Iterator<Map.Entry<String, JsonNode>> topFields = request.fields();
        while (topFields.hasNext()) {
            Map.Entry<String, JsonNode> entry = topFields.next();
            JsonNode value = entry.getValue();
            if (value.isObject() && "108".equals(value.path("SegmentType").asText(null))) {
                seg108Count++;
            }
        }
        if (seg108Count > 1) {
            result.addError(SOURCE, "Only one Loyalty Card Data Segment (108) is allowed per message (SEG108-R-021); found " + seg108Count);
        }

        validateLoyaltySegment(loyaltySegment, result);
    }

    private void validateLoyaltySegment(JsonNode segment, ValidationResult result) {
        // SEG108-R-003: Segment Type is 108
        String segmentType = segment.path("SegmentType").asText(null);
        if (!"108".equals(segmentType)) {
            result.addError(SOURCE, "Loyalty Card Data Segment.SegmentType must be 108 (SEG108-R-003); found " + segmentType);
        }

        // SEG108-R-004 and SEG108-R-005: Segment Length is 3 digits and within overall max
        String segmentLength = segment.path("SegmentLength").asText(null);
        if (segmentLength == null || !SEGMENT_LENGTH_PATTERN.matcher(segmentLength).matches()) {
            result.addError(SOURCE, "Loyalty Card Data Segment.SegmentLength must be 3 digits (SEG108-R-004)");
        } else {
            int declared = Integer.parseInt(segmentLength);
            if (declared > MAX_SEGMENT_LENGTH) {
                result.addError(SOURCE, "Loyalty Card Data Segment.SegmentLength " + declared
                    + " exceeds maximum " + MAX_SEGMENT_LENGTH + " (SEG108-R-005)");
            }
        }

        // SEG108-R-008: Loyalty Program ID is required, numeric max 6
        String loyaltyProgramId = segment.path("LoyaltyProgramId").asText(null);
        if (loyaltyProgramId == null || loyaltyProgramId.isBlank()) {
            result.addError(SOURCE, "Loyalty Card Data Segment.LoyaltyProgramId is required (SEG108-R-008)");
        } else if (!NUMERIC_MAX_6.matcher(loyaltyProgramId).matches()) {
            result.addError(SOURCE, "Loyalty Card Data Segment.LoyaltyProgramId must be numeric max 6 digits (SEG108-R-008)");
        }

        checkOptionalPattern(segment, "LoyaltyAccountNumber", NUMERIC_MAX_24, "numeric max 24 digits", "SEG108-R-009", result);
        checkOptionalPattern(segment, "PointsToRedeem", NUMERIC_MAX_6, "numeric max 6 digits", "SEG108-R-010", result);
        checkOptionalPattern(segment, "CouponId", NUMERIC_MAX_19, "numeric max 19 digits", "SEG108-R-011", result);
        checkOptionalPattern(segment, "CouponAmount", NUMERIC_MAX_8, "numeric max 8 digits", "SEG108-R-012", result);
        checkOptionalPattern(segment, "StreetAddress", NUMERIC_MAX_5, "numeric max 5 digits", "SEG108-R-014", result);
        checkOptionalPattern(segment, "PhoneNumberLoyalty", NUMERIC_MAX_10, "numeric max 10 digits", "SEG108-R-015", result);
        checkOptionalPattern(segment, "LoyaltyTrack2Data", ALPHANUMERIC_MAX_38, "alphanumeric max 38 characters", "SEG108-R-018", result);
        checkOptionalPattern(segment, "UnitOfWork", NUMERIC_FIXED_19, "numeric fixed length 19", "SEG108-R-020", result);
        checkOptionalPattern(segment, "ExpirationDate", EXPIRATION_DATE_MMYY, "numeric MMYY (month 01-12)", "SEG108-R-016", result);

        // SEG108-R-013: Update Code enumeration
        if (segment.has("UpdateCode")) {
            String updateCode = segment.path("UpdateCode").asText(null);
            if (updateCode != null && !updateCode.isBlank() && !VALID_UPDATE_CODES.contains(updateCode)) {
                result.addError(SOURCE, "Loyalty Card Data Segment.UpdateCode '" + updateCode
                    + "' is not one of the documented values A, C, E, I, P, S, T, U (SEG108-R-013)");
            }
        }

        // SEG108-R-017: Payment Tender Type is required and in the documented enumeration
        String paymentTenderType = segment.path("PaymentTenderType").asText(null);
        if (paymentTenderType == null || paymentTenderType.isBlank()) {
            result.addError(SOURCE, "Loyalty Card Data Segment.PaymentTenderType is required (SEG108-R-017)");
        } else if (!VALID_PAYMENT_TENDER_TYPES.contains(paymentTenderType)) {
            result.addError(SOURCE, "Loyalty Card Data Segment.PaymentTenderType '" + paymentTenderType
                + "' is not one of the documented values (SEG108-R-017)");
        }

        // SEG108-R-019: Loyalty Information Version, when populated, is 1 or 2
        if (segment.has("LoyaltyInformationVersion")) {
            String version = segment.path("LoyaltyInformationVersion").asText(null);
            if (version != null && !version.isBlank() && !VALID_LOYALTY_INFO_VERSIONS.contains(version)) {
                result.addError(SOURCE, "Loyalty Card Data Segment.LoyaltyInformationVersion '" + version
                    + "' must be 1 or 2 (SEG108-R-019)");
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
            result.addError(SOURCE, "Loyalty Card Data Segment." + field + " must be " + description
                + " (" + ruleId + ")");
        }
    }
}
