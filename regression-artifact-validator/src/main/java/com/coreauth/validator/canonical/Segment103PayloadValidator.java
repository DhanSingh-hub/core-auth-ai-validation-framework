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
 * {@code specifications/ATL105/docs/specs/kb/segment-103/coverage/segment-103-rule-catalog.json}.
 *
 * <p>Item 1 baseline validator (Coverage Closure) per COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md.
 * Wire-serialization-only rules (SEG103-R-006, 007, 008) are cataloged but enforced by
 * {@link Segment103WireFormatValidator} instead, since they are observable only on the
 * serialized message, not on JSON structure. SEG103-R-002/019/020/021 (applicability, origin,
 * eWIC lifecycle, prompt-code) are enforced by {@link Segment103ApplicabilityValidator} and
 * {@link Segment103WireFormatValidator}. Element 154/164 full positional layouts (Appendix M,
 * Appendix L) are validated here when the granular subelement fields are present; the legacy
 * flat {@code data} blob remains supported for backward compatibility with earlier fixtures.
 */
public final class Segment103PayloadValidator {
    private static final String SOURCE = "Segment103Payload";

    private static final Pattern SEGMENT_LENGTH_PATTERN = Pattern.compile("^[0-9]{3,4}$");
    private static final Pattern NUMERIC_MAX_10 = Pattern.compile("^[0-9]{1,10}$");
    private static final Pattern WIC_DISCOUNT_BLOCK =
        Pattern.compile("^9752([0-9]{3})([0CD])([0-9]{12})$");
    private static final Pattern WIC_PRODUCT_TOTAL_LENGTH = Pattern.compile("^[0-9]{4}$");
    private static final Pattern EBT_PROGRAM_TOTAL_LENGTH = Pattern.compile("^[0-9]{3}$");
    private static final Pattern NUMERIC_2 = Pattern.compile("^[0-9]{2}$");
    private static final Pattern DETAIL_12_DIGITS = Pattern.compile("^[0-9]{12}$");

    private static final int MAX_SEGMENT_LENGTH = 3334;
    private static final int WIC_DISCOUNT_MAX_LENGTH = 40;
    private static final int WIC_DISCOUNT_BLOCK_LENGTH = 20;
    private static final int WIC_PRODUCT_DATA_MAX = 3001;
    private static final int WIC_PRODUCT_TOTAL_LENGTH_MAX_VALUE = 2997;
    private static final int EBT_PROGRAM_TOTAL_LENGTH_MAX_VALUE = 264;
    private static final int EBT_PROGRAM_SUBELEMENT_MAX_BYTES = 44;
    // Appendix M worked examples decode to ACCOUNT TYPE(2)+AMOUNT TYPE(2)+CURRENCY CODE(3)
    // +AMOUNT DESCRIPTOR(1)+DETAIL(12) = 20 bytes of LEN-counted data for TAG 50/51/52.
    private static final int EBT_PROGRAM_LEN_50_51_52 = 20;
    private static final String EBT_PROGRAM_FIXED_ACCOUNT_TYPE = "98";
    private static final String EBT_PROGRAM_FIXED_CURRENCY_CODE = "840";
    private static final Set<String> EBT_PROGRAM_AMOUNT_DESCRIPTORS = Set.of("0", "C", "D");
    private static final Set<String> APPENDIX_M_TAG_50_AMOUNT_TYPES = Set.of("40", "50");
    // TAG=IT detail is a fixed 28-byte address + 9-byte zip = 37 bytes of LEN-counted data.
    private static final int EBT_PROGRAM_IT_ADDRESS_LENGTH = 28;
    private static final int EBT_PROGRAM_IT_ZIP_LENGTH = 9;
    private static final int EBT_PROGRAM_LEN_IT = EBT_PROGRAM_IT_ADDRESS_LENGTH + EBT_PROGRAM_IT_ZIP_LENGTH;
    // Element 154 subelement data-portion maximum lengths (excluding the 2-char tag identifier).
    private static final int WIC_PRODUCT_EF_MAX = 8;
    private static final int WIC_PRODUCT_EA_MAX = 14;
    private static final int WIC_PRODUCT_PS_MAX = 47;
    private static final Set<String> WIC_PRODUCT_TAGS = Set.of("EF", "EA", "PS");
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

    public ValidationResult validateElement(String element, JsonNode value) {
        ValidationResult result = new ValidationResult("chapter13-element-" + element);
        var segment = objectMapper.createObjectNode();
        if ("153".equals(element)) {
            if (value == null || !value.isTextual() || value.textValue().isEmpty()) {
                result.addError(SOURCE, "Element 153 requires nonempty textual discount blocks");
            } else {
                segment.set("WicDiscountAmount", value);
                validateWicDiscountAmount(segment, result);
            }
        } else if ("164".equals(element)) {
            if (value == null || !value.isObject() || !value.path("subelements").isArray() || !value.path("totalLength").isTextual()) {
                result.addError(SOURCE, "Element 164 requires explicit totalLength and subelements");
            } else {
                segment.set("EbtProgramData", value);
                validateEbtProgramData(segment, result);
            }
        } else result.addError(SOURCE, "Unsupported isolated Segment 103 element: " + element);
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
            java.util.regex.Matcher matcher = WIC_DISCOUNT_BLOCK.matcher(block);
            if (!matcher.matches()) {
                result.addError(SOURCE, "EBT Data Segment.WicDiscountAmount block '" + block
                    + "' must match Account Type 97 + Amount Type 52 + Currency Code + signed amount (SEG103-R-011)");
                continue;
            }
            String currencyCode = matcher.group(1);
            if (!AppendixLCurrencyCodes.isValid(currencyCode)) {
                result.addError(SOURCE, "EBT Data Segment.WicDiscountAmount currency code '" + currencyCode
                    + "' is not a valid Appendix L currency code (SEG103-R-011)");
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
        validateWicProductSubelements(node, result);
    }

    /**
     * Element 154 subelement catalog (Appendix descriptor tables): EF (Earliest WIC Benefit
     * Expiration Date, data max 8 bytes), EA (WIC Prescription Balance Information, data max 14
     * bytes), PS (WIC UPC Exception/Denial or Purchase Information, data max 47 bytes; the two
     * PS variants share one tag and are disambiguated only by an internal bit-map, which is out
     * of scope for structural JSON validation). Additive to the legacy totalLength/data check;
     * only runs when the fixture supplies the granular {@code subelements} array (SEG103-R-023).
     */
    private static void validateWicProductSubelements(JsonNode wicProductData, ValidationResult result) {
        JsonNode subelements = wicProductData.path("subelements");
        if (!subelements.isArray()) {
            return;
        }
        for (JsonNode sub : subelements) {
            String tag = sub.path("tag").asText(null);
            String data = sub.path("data").asText("");
            if (tag == null || !WIC_PRODUCT_TAGS.contains(tag)) {
                result.addError(SOURCE, "EBT Data Segment.WicProductData subelement TAG '" + tag
                    + "' is not one of the documented values EF, EA, PS (SEG103-R-023)");
                continue;
            }
            int maxLength = switch (tag) {
                case "EF" -> WIC_PRODUCT_EF_MAX;
                case "EA" -> WIC_PRODUCT_EA_MAX;
                default -> WIC_PRODUCT_PS_MAX;
            };
            if (data.length() > maxLength) {
                result.addError(SOURCE, "EBT Data Segment.WicProductData subelement TAG " + tag
                    + " data exceeds " + maxLength + "-byte maximum (SEG103-R-023)");
            }
            if ("EF".equals(tag) && data.length() != WIC_PRODUCT_EF_MAX) {
                result.addError(SOURCE, "EBT Data Segment.WicProductData subelement TAG EF must be exactly "
                    + WIC_PRODUCT_EF_MAX + " bytes (CCYYMMDD) (SEG103-R-023)");
            }
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
        if (totalLength != null && EBT_PROGRAM_TOTAL_LENGTH.matcher(totalLength).matches()) {
            validateEbtProgramTotalLength(Integer.parseInt(totalLength), subelements, result);
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
                if (!EBT_PROGRAM_FIXED_ACCOUNT_TYPE.equals(accountType)) {
                    result.addError(SOURCE, "EBT Data Segment.EbtProgramData subelement with TAG 50 must set accountType to "
                        + EBT_PROGRAM_FIXED_ACCOUNT_TYPE + " (SEG103-R-017)");
                }
            }
            validateEbtProgramDataDetailLayout(tag, sub, result);
        }
    }

    private static void validateEbtProgramTotalLength(int declared, JsonNode subelements, ValidationResult result) {
        int encodedLength = 0;
        for (JsonNode sub : subelements) {
            String length = sub.path("len").asText(null);
            if (length == null || !NUMERIC_2.matcher(length).matches()) {
                return;
            }
            encodedLength += 2 + 2 + Integer.parseInt(length);
        }
        if (declared != encodedLength) {
            result.addError(SOURCE, "EBT Data Segment.EbtProgramData.totalLength " + declared
                + " does not match encoded TAG/LEN/detail character count " + encodedLength + " (SEG103-R-014)");
        }
    }

    /**
     * Element 164 / Appendix M full positional layout, validated only when a fixture supplies
     * the granular fields (amountType/currencyCode/amountDescriptor/detail for TAG 50/51/52, or
     * address/zip for TAG IT). The legacy flat {@code data} blob (bound-checked above) remains
     * valid for fixtures that do not populate these fields (SEG103-R-022).
     */
    private static void validateEbtProgramDataDetailLayout(String tag, JsonNode sub, ValidationResult result) {
        boolean hasGranularFields = sub.has("amountType") || sub.has("currencyCode")
            || sub.has("amountDescriptor") || sub.has("detail") || sub.has("address") || sub.has("zip");
        if (!hasGranularFields) {
            return;
        }
        if ("IT".equals(tag)) {
            String address = sub.path("address").asText("");
            String zip = sub.path("zip").asText("");
            if (address.length() != EBT_PROGRAM_IT_ADDRESS_LENGTH) {
                result.addError(SOURCE, "EBT Data Segment.EbtProgramData subelement TAG IT address must be exactly "
                    + EBT_PROGRAM_IT_ADDRESS_LENGTH + " bytes (SEG103-R-022)");
            }
            if (zip.length() > EBT_PROGRAM_IT_ZIP_LENGTH || !zip.matches("[0-9]{0,9}")) {
                result.addError(SOURCE, "EBT Data Segment.EbtProgramData subelement TAG IT zip must be at most "
                    + EBT_PROGRAM_IT_ZIP_LENGTH + " digits (SEG103-R-022)");
            }
            checkLen(sub, EBT_PROGRAM_LEN_IT, result);
            return;
        }
        if (!Set.of("50", "51", "52").contains(tag)) {
            return;
        }
        String amountType = sub.path("amountType").asText(null);
        if ("50".equals(tag) && APPENDIX_M_TAG_50_AMOUNT_TYPES.contains(amountType)) {
            result.addWarning("REVIEW_REQUIRED (SEG103-SME-009): Appendix M describes TAG 50 AMOUNT TYPE as 40, while Section 13.2 and the worked strings use 50; observed value "
                + amountType + " is retained pending SME/TBA resolution.");
        } else if (amountType == null || !amountType.equals(tag)) {
            result.addError(SOURCE, "EBT Data Segment.EbtProgramData subelement TAG " + tag
                + " must set amountType to " + tag + " (SEG103-R-022)");
        }
        String currencyCode = sub.path("currencyCode").asText(null);
        if (!EBT_PROGRAM_FIXED_CURRENCY_CODE.equals(currencyCode)) {
            result.addError(SOURCE, "EBT Data Segment.EbtProgramData subelement TAG " + tag
                + " must set currencyCode to " + EBT_PROGRAM_FIXED_CURRENCY_CODE + " (US Dollars) (SEG103-R-022)");
        }
        String amountDescriptor = sub.path("amountDescriptor").asText(null);
        if (amountDescriptor == null || !EBT_PROGRAM_AMOUNT_DESCRIPTORS.contains(amountDescriptor)) {
            result.addError(SOURCE, "EBT Data Segment.EbtProgramData subelement TAG " + tag
                + " amountDescriptor must be one of 0, C, D (SEG103-R-022)");
        }
        String detail = sub.path("detail").asText(null);
        if (detail == null || !DETAIL_12_DIGITS.matcher(detail).matches()) {
            result.addError(SOURCE, "EBT Data Segment.EbtProgramData subelement TAG " + tag
                + " detail must be a 12-digit numeric amount (SEG103-R-022)");
        }
        checkLen(sub, EBT_PROGRAM_LEN_50_51_52, result);
    }

    private static void checkLen(JsonNode sub, int expected, ValidationResult result) {
        String len = sub.path("len").asText(null);
        if (len == null || !NUMERIC_2.matcher(len).matches()) {
            result.addError(SOURCE, "EBT Data Segment.EbtProgramData subelement len must be a 2-digit numeric value (SEG103-R-022)");
            return;
        }
        if (Integer.parseInt(len) != expected) {
            result.addError(SOURCE, "EBT Data Segment.EbtProgramData subelement len " + len
                + " does not match the expected " + expected + " bytes of detail data (SEG103-R-022)");
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
