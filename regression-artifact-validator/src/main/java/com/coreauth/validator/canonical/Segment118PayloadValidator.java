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
 * This covers the 13 core fields and the source-confirmed structures of all five conditional
 * Prompt-Code payloads (901, 902, 903, 904, 905), including repeat counts and record fields.
 * The Information Byte value catalog and unresolved receipt-text encoding semantics remain
 * outside deterministic pass/fail checks pending SME decisions.
 */
public final class Segment118PayloadValidator {
    private static final String SOURCE = "Segment118Payload";
    private static final String REQUEST_ROOT = "Proprietary Data Load Request";
    private static final String RESPONSE_ROOT = "Proprietary Data Load Response";
    private static final Pattern NUMERIC_4 = Pattern.compile("^[0-9]{4}$");
    private static final Pattern NUMERIC_6 = Pattern.compile("^[0-9]{6}$");
    private static final Pattern NUMERIC_1 = Pattern.compile("^[0-9]$");
    private static final Pattern ALPHANUMERIC_13 = Pattern.compile("^[A-Za-z0-9]{13}$");
    private static final Pattern NUMERIC_3 = Pattern.compile("^[0-9]{3}$");
    private static final Pattern NUMERIC_4_PENDING = Pattern.compile("^[0-9]{4}$");
    private static final Pattern NUMERIC_35 = Pattern.compile("^[0-9]{35}$");
    private static final Pattern ALPHANUMERIC_MAX_60 = Pattern.compile("^[A-Za-z0-9]{1,60}$");
    private static final Pattern NUMERIC_12 = Pattern.compile("^[0-9]{12}$");
    private static final Pattern NUMERIC_2 = Pattern.compile("^[0-9]{2}$");
    private static final Pattern NUMERIC_8 = Pattern.compile("^[0-9]{8}$");
    private static final Pattern NUMERIC_5 = Pattern.compile("^[0-9]{5}$");
    private static final Pattern ALPHANUMERIC_MAX_20 = Pattern.compile("^[A-Za-z0-9 ]{1,20}$");

    private static final Set<String> VALID_PROMPT_CODES = Set.of("901", "902", "903", "904", "905");
    private static final Set<String> REQUEST_PROMPT_CODES = Set.of("903", "905");
    private static final Set<String> RESPONSE_PROMPT_CODES = Set.of("901", "902", "904");
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
        boolean isRequest = payload.path(REQUEST_ROOT).isObject();
        boolean isResponse = payload.path(RESPONSE_ROOT).isObject();
        if (isRequest == isResponse) {
            result.addError(SOURCE, "Exactly one Proprietary Data Load Request or Response object is required");
            return;
        }
        JsonNode message = payload.path(isRequest ? REQUEST_ROOT : RESPONSE_ROOT);
        if (isRequest) {
            validateRequest(message, result);
        } else {
            validateResponse(message, result);
        }
    }

    private static void validateRequest(JsonNode request, ValidationResult result) {
        if (request.has("Standard Message Data Segment")) {
            result.addError(SOURCE, "Proprietary Data Load Request must not contain Segment 100 (SEG118-R-024)");
        }
        checkRequiredPresent(request, "MessageType", "SEG118-R-024", result);
        checkRequiredValue(request, "NumSegments", "1", "SEG118-R-024", result);
        validateEnvelopeSegment(request, true, result);
    }

    private static void validateResponse(JsonNode response, ValidationResult result) {
        checkRequiredPresent(response, "ResponseCode", "SEG118-R-025", result);
        String responseCode = text(response, "ResponseCode");
        if (responseCode != null && !Set.of("H", "O", "T", "U", "X", "Y").contains(responseCode)) {
            result.addError(SOURCE, "Proprietary Data Load Response.ResponseCode is not in the documented PDL response catalog (SEG118-R-026, SEG118-R-030)");
        }
        checkRequiredPresent(response, "DownloadIndicator", "SEG118-R-025", result);
        checkRequiredPresent(response, "InitiationDate", "SEG118-R-025", result);
        checkRequiredPresent(response, "InitiationTime", "SEG118-R-025", result);
        checkRequiredPresent(response, "SequenceNumber", "SEG118-R-025", result);
        validateEnvelopeSegment(response, false, result);
    }

    private static void validateEnvelopeSegment(JsonNode message, boolean request, ValidationResult result) {
        JsonNode segment = message.path("Proprietary Data Load Segment");
        if (!segment.isObject()) {
            result.addError(SOURCE, "Proprietary Data Load Segment object is required (" + (request ? "SEG118-R-024" : "SEG118-R-025") + ")");
            return;
        }
        validateSegment(segment, request, result);
    }

    private static void validateSegment(JsonNode segment, boolean request, ValidationResult result) {
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
        } else if (request && !REQUEST_PROMPT_CODES.contains(promptCode)) {
            result.addError(SOURCE, "Request PromptCode must be 903 (Site Configuration) or 905 (Fuel Volume) (SEG118-R-021, SEG118-R-023)");
        } else if (!request && !RESPONSE_PROMPT_CODES.contains(promptCode)) {
            result.addError(SOURCE, "Response PromptCode must be 901 (Custom Receipt Text), 902 (Dynamic Card Table), or 904 (Host Discount) (SEG118-R-019, SEG118-R-020, SEG118-R-022)");
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
            case "901" -> validateReceiptText(segment, result);
            case "902" -> checkRequiredPresent(segment, "CardTableData", "SEG118-R-020", result);
            case "903" -> validateSiteConfiguration(segment, result);
            case "904" -> validateHostDiscounts(segment, result);
            case "905" -> checkRequiredMaxLength(segment, "FuelVolumeData", 3600, "SEG118-R-023", result);
            default -> { }
        }
        if ("902".equals(promptCode)) checkMaxLength(segment, "CardTableData", 3600, "SEG118-R-020", result);
    }

    private static void validateReceiptText(JsonNode segment, ValidationResult result) {
        String countText = text(segment, "NumberOfReceiptTextLines");
        if (countText == null || !matches(countText, NUMERIC_2)) {
            result.addError(SOURCE, "NumberOfReceiptTextLines must be two digits (SEG118-R-019)");
            return;
        }
        int count = Integer.parseInt(countText);
        if (count < 1 || count > 10) {
            result.addError(SOURCE, "NumberOfReceiptTextLines must be 01-10 (SEG118-R-019)");
            return;
        }
        checkRequiredPattern(segment, "StartDate", NUMERIC_8, "8 numeric digits (CCYYMMDD)", "SEG118-R-019", result);
        checkRequiredPattern(segment, "StartTime", NUMERIC_4, "4 numeric digits (HHMM)", "SEG118-R-019", result);
        checkRequiredPattern(segment, "EndDate", NUMERIC_8, "8 numeric digits (CCYYMMDD)", "SEG118-R-019", result);
        checkRequiredPattern(segment, "EndTime", NUMERIC_4, "4 numeric digits (HHMM)", "SEG118-R-019", result);
        JsonNode lines = segment.path("ReceiptTextLines");
        if (!lines.isArray() || lines.size() != count) {
            result.addError(SOURCE, "ReceiptTextLines count must equal NumberOfReceiptTextLines (SEG118-R-019)");
            return;
        }
        int bytes = 26;
        for (JsonNode line : lines) {
            String lengthText = text(line, "ReceiptTextDataLength");
            String value = text(line, "ReceiptTextData");
            if (lengthText == null || !matches(lengthText, NUMERIC_2) || value == null || !matches(value, ALPHANUMERIC_MAX_20)) {
                result.addError(SOURCE, "Each receipt text line needs a two-digit length and 1-20 alphanumeric characters (SEG118-R-019)");
                continue;
            }
            int declaredLength = Integer.parseInt(lengthText);
            if (declaredLength < 1 || declaredLength > 40) {
                result.addError(SOURCE, "ReceiptTextDataLength must be 01-40 (SEG118-R-019)");
            }
            if (declaredLength != value.length()) {
                result.addWarning("SEG118-R-019: ReceiptTextDataLength differs from actual text length; retain review under SEG118-SME-002");
            }
            bytes += 2 + value.length();
        }
        if (bytes > 220) result.addError(SOURCE, "Custom Receipt Text payload exceeds 220 bytes (SEG118-R-019)");
    }

    private static void validateSiteConfiguration(JsonNode segment, ValidationResult result) {
        String blockText = text(segment, "BlockNumber");
        String data = text(segment, "SiteConfigurationData");
        if ("000".equals(blockText)) {
            if (data != null && !data.isBlank()) result.addError(SOURCE, "BlockNumber 000 is the final Site Configuration message and carries no SiteConfigurationData (SEG118-R-027)");
            return;
        }
        checkRequiredMaxLength(segment, "SiteConfigurationData", 3600, "SEG118-R-021", result);
    }

    private static void validateHostDiscounts(JsonNode segment, ValidationResult result) {
        String countText = text(segment, "NumberOfDiscounts");
        if (countText == null || !matches(countText, NUMERIC_2)) {
            result.addError(SOURCE, "NumberOfDiscounts must be two digits (SEG118-R-022)");
            return;
        }
        int count = Integer.parseInt(countText);
        JsonNode discounts = segment.path("Discounts");
        if (!discounts.isArray() || discounts.size() != count) {
            result.addError(SOURCE, "Discounts count must equal NumberOfDiscounts (SEG118-R-022)");
            return;
        }
        for (JsonNode discount : discounts) {
            requirePattern(discount, "StartDate", NUMERIC_8, "8 numeric digits", result);
            requirePattern(discount, "StartTime", NUMERIC_4, "4 numeric digits", result);
            requirePattern(discount, "EndDate", NUMERIC_8, "8 numeric digits", result);
            requirePattern(discount, "EndTime", NUMERIC_4, "4 numeric digits", result);
            requirePattern(discount, "BuyPassCardType", NUMERIC_4, "4 numeric digits", result);
            requirePattern(discount, "CardBinRangeBeginning", NUMERIC_12, "12 numeric digits", result);
            requirePattern(discount, "CardBinRangeEnding", NUMERIC_12, "12 numeric digits", result);
            requirePattern(discount, "ProductCode", NUMERIC_3, "3 numeric digits", result);
            requirePattern(discount, "ProductDiscountAmount", NUMERIC_5, "5 numeric digits", result);
            requirePattern(discount, "DiscountProductCode", NUMERIC_3, "3 numeric digits", result);
            requirePattern(discount, "DiscountQuantityLimit", NUMERIC_3, "3 numeric digits", result);
            requireExactLength(discount, "DiscountProgramDescription", 15, result);
        }
        if (count * 81 > 3600) result.addError(SOURCE, "Host Discount Data exceeds 3,600 bytes (SEG118-R-022)");
    }

    private static void requirePattern(JsonNode node, String field, Pattern pattern, String description, ValidationResult result) {
        if (!matches(text(node, field), pattern)) result.addError(SOURCE, field + " must be " + description + " (SEG118-R-022)");
    }

    private static void requireExactLength(JsonNode node, String field, int length, ValidationResult result) {
        String value = text(node, field);
        if (value == null || value.length() != length) result.addError(SOURCE, field + " must be exactly " + length + " characters (SEG118-R-022)");
    }

    private static void checkMaxLength(JsonNode node, String field, int maxLength, String ruleId, ValidationResult result) {
        String value = text(node, field);
        if (value != null && value.length() > maxLength) result.addError(SOURCE, field + " exceeds maximum length " + maxLength + " (" + ruleId + ")");
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
