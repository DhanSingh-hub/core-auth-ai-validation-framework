package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.coreauth.validator.validation.Atl105CalendarDate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Validates the Totals Request (ATL105 Section 11.4.1.1, Segment 105) and the Totals with
 * Proprietary Data Load Request (Section 11.4.1.2, Segment 119): message envelope (both
 * segments) and field-level Segment 105 rules (added during the Chapter 12 segment
 * cross-check; see {@link #checkSegment105Fields}). Field-level Segment 119 rules are not
 * checked here.
 */
public final class TotalsRequestPayloadValidator {
    public static final String TOTALS_REQUEST = "Totals Request";
    public static final String TOTALS_WITH_PDL_REQUEST = "Totals with Proprietary Data Load Request";
    private static final String SOURCE = "TotalsRequestPayload";
    private static final Pattern NUMERIC_3 = Pattern.compile("^[0-9]{3}$");
    private static final Set<String> TOTALS_DATE_SPECIAL_CODES = Set.of(
            "111111", "222222", "333333", "444444", "555555", "999999");
    private static final Pattern ALPHANUMERIC_1_22 = Pattern.compile("^[A-Za-z0-9]{1,22}$");
    private static final Pattern SEQUENCE_NUMBER = Pattern.compile("^[0-9]{6}$");
    private static final Pattern GRAND_TOTAL = Pattern.compile("^[0-9]{8}$");
    private static final Pattern CARD_TYPE_TOTAL_COUNT = Pattern.compile("^[0-9]{5}$");
    private static final Pattern CARD_TYPE_TOTAL_AMOUNT = Pattern.compile("^[0-9]{8}$");
    /** ATL105 Chapter 13.2 Element 13: Card Label valid values. */
    private static final List<String> CARD_LABEL_ORDER = List.of(
            "CC", "TE", "DS", "AO", "DB", "FL", "CS", "PR", "CK", "EF", "EC",
            "SV1", "SV2", "SV3", "SV4", "ECA", "EWIC", "EK");
    private static final Set<String> VALID_CARD_LABELS = Set.copyOf(CARD_LABEL_ORDER);
    private static final int MAX_CATEGORY_TOTALS = 18;
    // Section 11.4.1.2 gives Segment 119 a length of 389; Section 12.17 gives 493 (SEG119-R-038).
    private static final int SEGMENT_119_TABLE_LENGTH = 389;

    private record Layout(String segmentType, String noSection2Rule, String soleSegmentRule, String presenceRule,
                          String countRule, int maxLength, String lengthRule) {
    }

    private static final Map<String, Layout> LAYOUTS = Map.of(
            TOTALS_REQUEST, new Layout("105", "SEG105-R-018", "SEG105-R-019", "SEG105-R-022",
                    "SEG105-R-020", 409, "SEG105-R-021"),
            TOTALS_WITH_PDL_REQUEST, new Layout("119", "SEG119-R-001", "SEG119-R-037", "SEG119-R-037",
                    "SEG119-R-037", 493, "SEG119-R-004"));

    private final ObjectMapper objectMapper;

    public TotalsRequestPayloadValidator() {
        this(new ObjectMapper());
    }

    TotalsRequestPayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path aiJsonFile) {
        String documentId = aiJsonFile == null ? "totals-request-payload" : aiJsonFile.getFileName().toString();
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
        ValidationResult result = new ValidationResult("totals-request-payload");
        validatePayload(payload, result);
        return result;
    }

    private void validatePayload(JsonNode payload, ValidationResult result) {
        if (payload == null || !payload.isObject()) {
            result.addError(SOURCE, "AI JSON root must be an object");
            return;
        }
        List<String> roots = new ArrayList<>();
        for (String name : List.of(TOTALS_REQUEST, TOTALS_WITH_PDL_REQUEST)) {
            if (payload.path(name).isObject()) {
                roots.add(name);
            }
        }
        if (roots.size() != 1) {
            result.addError(SOURCE, "AI JSON root must hold exactly one of '" + TOTALS_REQUEST + "' or '"
                    + TOTALS_WITH_PDL_REQUEST + "' (found " + (roots.isEmpty() ? fieldNames(payload) : roots)
                    + ") (SEG105-R-018, SEG119-R-001)");
            return;
        }
        String root = roots.get(0);
        validateMessage(root, payload.path(root), LAYOUTS.get(root), result);
    }

    private static void validateMessage(String root, JsonNode message, Layout layout, ValidationResult result) {
        String messageType = text(message, "MessageType");
        if (messageType == null || messageType.isBlank()) {
            result.addError(SOURCE, root + ".MessageType (Element 55) is required in Data Section 1 (" + layout.noSection2Rule() + ")");
        }

        int segmentCount = 0;
        JsonNode totalsSegment = null;
        Iterator<Map.Entry<String, JsonNode>> fields = message.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String type = entry.getValue().isObject() ? text(entry.getValue(), "SegmentType") : null;
            if (type == null) {
                continue;
            }
            segmentCount++;
            if ("100".equals(type)) {
                result.addError(SOURCE, "Segment 100 ('" + entry.getKey() + "') is not allowed: a " + root
                        + " has no Data Section 2 (" + layout.noSection2Rule() + ")");
            } else if (!layout.segmentType().equals(type)) {
                result.addError(SOURCE, "Segment " + type + " ('" + entry.getKey() + "') is not allowed: Segment "
                        + layout.segmentType() + " is the only segment in a " + root + " (" + layout.soleSegmentRule() + ")");
            } else if (totalsSegment != null) {
                result.addError(SOURCE, "Segment " + type + " appears more than once in a " + root + " (" + layout.soleSegmentRule() + ")");
            } else {
                totalsSegment = entry.getValue();
            }
        }
        if (totalsSegment == null) {
            result.addError(SOURCE, "Segment " + layout.segmentType() + " is required in Data Section 3 Field No. 3 of a "
                    + root + " (" + layout.presenceRule() + ")");
        } else {
            checkSegmentLength(totalsSegment, layout, result);
            if ("105".equals(layout.segmentType())) {
                checkSegment105Fields(totalsSegment, result);
            }
        }
        checkNumberOfSegments(root, message, segmentCount, layout, result);
    }

    /**
     * Field-level Segment 105 rules (SEG105-R-001, 003-005, 008, 010-015), added during the
     * Chapter 12 segment cross-check: previously only the message-envelope rules (018-022)
     * were enforced. SEG105-R-006/007 (Employee Number/Password business policy),
     * SEG105-R-009 (activity-date window, needs per-merchant history), SEG105-R-016/017
     * (lifecycle/Segment 119 selection policy) are REVIEW_REQUIRED by the catalog's own
     * design and are not enforced here; fabricating that business logic would be worse
     * than leaving it open.
     */
    private static void checkSegment105Fields(JsonNode segment, ValidationResult result) {
        String segmentType = text(segment, "SegmentType");
        if (!"105".equals(segmentType)) {
            result.addError(SOURCE, "Segment 105.SegmentType must be '105', got: " + segmentType + " (SEG105-R-001)");
        }
        String informationByte = text(segment, "InformationByte");
        if (!"0".equals(informationByte) && !"1".equals(informationByte)) {
            result.addError(SOURCE, "Segment 105.InformationByte must be 0 or 1 for a transaction request (SEG105-R-003)");
        }
        String terminalId = text(segment, "TerminalID");
        if (terminalId == null || !ALPHANUMERIC_1_22.matcher(terminalId).matches()) {
            result.addError(SOURCE, "Segment 105.TerminalID (Element 102) must be 1-22 alphanumeric characters (SEG105-R-004)");
        }
        String promptCode = text(segment, "PromptCode");
        if (!"990".equals(promptCode)) {
            result.addError(SOURCE, "Segment 105.PromptCode (Element 78) must be '990', got: " + promptCode + " (SEG105-R-005)");
        }
        String employeeNumber = text(segment, "EmployeeNumber");
        if (employeeNumber != null && !employeeNumber.isBlank() && !employeeNumber.matches("^[0-9]{4}$")) {
            result.addError(SOURCE, "Segment 105.EmployeeNumber (Element 32), if present, is 4 digits per source format; "
                    + "authorization policy for its use remains REVIEW_REQUIRED (SEG105-R-006)");
        }
        String password = text(segment, "Password");
        if (password != null && !password.isEmpty() && !password.matches(" *[0-9]{1,6}")) {
            result.addError(SOURCE, "Segment 105.Password (Element 65), if present, is right-aligned numeric data; "
                    + "authorization policy for its use remains REVIEW_REQUIRED (SEG105-R-007)");
        }
        if (password != null && password.length() > 6) {
            result.addError(SOURCE, "Segment 105.Password exceeds 6 characters (SEG105-R-007)");
        }
        String totalsDate = text(segment, "TotalsDate");
        if (totalsDate == null || !(TOTALS_DATE_SPECIAL_CODES.contains(totalsDate)
                || validTotalsDate(totalsDate))) {
            result.addError(SOURCE, "Segment 105.TotalsDate (Element 105) must be MMDDYY or one of "
                    + TOTALS_DATE_SPECIAL_CODES + ", got: " + totalsDate + " (SEG105-R-008)");
        }
        version(segment, "HardwareVersion", "[A-Za-z0-9 ]{4}|[A-Za-z0-9 ]{8}", "SEG105-R-010", result);
        version(segment, "SoftwareVersion", "[A-Za-z0-9 ]{8}", "SEG105-R-011", result);
        version(segment, "FirmwareVersion", "[A-Za-z0-9 ]{8}", "SEG105-R-012", result);
        String sequenceNumber = text(segment, "SequenceNumber");
        if (sequenceNumber == null || !SEQUENCE_NUMBER.matcher(sequenceNumber).matches() || "000000".equals(sequenceNumber)) {
            result.addError(SOURCE, "Segment 105.SequenceNumber (Element 86) must be 6 digits, got: "
                    + sequenceNumber + " (SEG105-R-013)");
        }
        String currencyCode = text(segment, "CurrencyCode");
        if (currencyCode != null && !currencyCode.isBlank() && !NUMERIC_3.matcher(currencyCode).matches()) {
            result.addError(SOURCE, "Segment 105.CurrencyCode (Element 20), if present, must be 3 digits "
                    + "(see Appendix L for value validity), got: " + currencyCode + " (SEG105-R-014)");
        }
        checkGrandTotalAndCategoryTotals(segment, result);
        checkSegment105Wire(segment, result);
        result.addWarning("REVIEW_REQUIRED: employee/password authorization, settlement activity window and lifecycle "
                + "need independently supplied merchant history (SEG105-R-006/007/009/016/017)");
    }

    private static void checkGrandTotalAndCategoryTotals(JsonNode segment, ValidationResult result) {
        String grandTotal = text(segment, "GrandTotal");
        if (grandTotal == null || !GRAND_TOTAL.matcher(grandTotal).matches()) {
            result.addError(SOURCE, "Segment 105.GrandTotal (Element 42) must be 8 digits, got: "
                    + grandTotal + " (SEG105-R-015)");
        }
        JsonNode categoryTotals = segment.path("CategoryTotals");
        if (!categoryTotals.isArray()) {
            result.addError(SOURCE, "Segment 105.CategoryTotals must be an array of Card Label/Count/Amount entries (SEG105-R-015)");
            return;
        }
        if (categoryTotals.size() > MAX_CATEGORY_TOTALS) {
            result.addError(SOURCE, "Segment 105.CategoryTotals has " + categoryTotals.size()
                    + " entries; Section 12.6 allows at most " + MAX_CATEGORY_TOTALS + " (SEG105-R-015)");
        }
        Set<String> seenLabels = new java.util.HashSet<>();
        int previousLabel = -1;
        for (JsonNode entry : categoryTotals) {
            String label = text(entry, "CardLabel");
            if (label != null && (label.length() > 4 || !label.matches("[A-Z0-9 ]{2,4}"))) {
                result.addError(SOURCE, "Segment 105.CardLabel must fit four ASCII characters with space padding (SEG105-R-015)");
            }
            if (label != null) label = label.stripTrailing();
            if (label == null || !VALID_CARD_LABELS.contains(label)) {
                result.addError(SOURCE, "Segment 105.CategoryTotals entry has an invalid CardLabel: " + label + " (SEG105-R-015)");
            } else if (!seenLabels.add(label)) {
                result.addError(SOURCE, "Segment 105.CategoryTotals CardLabel " + label
                        + " appears more than once; Section 12.6 sends each card type at most once (SEG105-R-015)");
            }
            if (label != null && VALID_CARD_LABELS.contains(label)) {
                int position = CARD_LABEL_ORDER.indexOf(label);
                if (position < previousLabel) result.addError(SOURCE, "Segment 105 card buckets are not in source order (SEG105-R-015)");
                previousLabel = position;
            }
            String count = text(entry, "CardTypeTotalCount");
            if (count == null || !CARD_TYPE_TOTAL_COUNT.matcher(count).matches() || "00000".equals(count)) {
                result.addError(SOURCE, "Segment 105.CategoryTotals[" + label
                        + "].CardTypeTotalCount (Element 16) must be 5 digits, got: " + count + " (SEG105-R-015)");
            }

            String amount = text(entry, "CardTypeTotalAmount");
            if (amount == null || !CARD_TYPE_TOTAL_AMOUNT.matcher(amount).matches()) {
                result.addError(SOURCE, "Segment 105.CategoryTotals[" + label
                        + "].CardTypeTotalAmount (Element 15) must be 8 digits, got: " + amount + " (SEG105-R-015)");
            }
        }
    }

    private static boolean validTotalsDate(String value) {
        return Atl105CalendarDate.isValidMmddyy(value);
    }

    private static void version(JsonNode segment, String field, String pattern, String ruleId, ValidationResult result) {
        String value = text(segment, field);
        if (value == null || value.isBlank() || !value.matches(pattern)) {
            result.addError(SOURCE, "Segment 105." + field + " has incorrect required version width (" + ruleId + ")");
        }
    }

    private static void checkSegment105Wire(JsonNode segment, ValidationResult result) {
        StringBuilder encoded = new StringBuilder();
        for (String field : List.of("SegmentType", "SegmentLength", "InformationByte", "TerminalID", "PromptCode",
                "EmployeeNumber", "Password", "TotalsDate", "HardwareVersion", "SoftwareVersion", "FirmwareVersion",
                "SequenceNumber", "CurrencyCode", "GrandTotal")) {
            String value = text(segment, field);
            if (value == null) value = "";
            if ("Password".equals(field) && !value.isEmpty() && value.length() <= 6) value = String.format("%6s", value);
            encoded.append(value).append('\u001c');
        }
        for (JsonNode bucket : segment.path("CategoryTotals")) {
            String label = text(bucket, "CardLabel");
            if (label != null) encoded.append(String.format("%-4s", label.stripTrailing()));
            String count = text(bucket, "CardTypeTotalCount");
            String amount = text(bucket, "CardTypeTotalAmount");
            if (count != null) encoded.append(count);
            if (amount != null) encoded.append(amount);
        }
        String declared = text(segment, "SegmentLength");
        if (declared != null && NUMERIC_3.matcher(declared).matches() && Integer.parseInt(declared) != encoded.length()) {
            result.addError(SOURCE, "Segment 105 SegmentLength does not equal encoded field/separator/bucket length (SEG105-R-002)");
        }
        if (segment.has("serializedSegment") && !encoded.toString().equals(text(segment, "serializedSegment"))) {
            result.addError(SOURCE, "Segment 105 wire order, separators or padded card labels disagree with fields (SEG105-R-002)");
        }
    }

    private static void checkNumberOfSegments(String root, JsonNode message, int segmentCount, Layout layout,
                                              ValidationResult result) {
        String declared = text(message, "NumSegments");
        if (declared == null || declared.isBlank()) {
            result.addError(SOURCE, root + ".NumSegments (Element 63) is required (" + layout.countRule() + ")");
            return;
        }
        if (!declared.matches(DataSection1StructureValidator.ELEMENT_63_PATTERN) || Integer.parseInt(declared) != 1) {
            result.addError(SOURCE, root + ".NumSegments (Element 63) is '" + declared + "' but must be 01 ("
                    + layout.countRule() + ")");
        } else if (segmentCount != 1) {
            result.addError(SOURCE, root + ".NumSegments (Element 63) declares 1 but the message contains "
                    + segmentCount + " segments (" + layout.countRule() + ")");
        }
    }

    private static void checkSegmentLength(JsonNode segment, Layout layout, ValidationResult result) {
        String length = text(segment, "SegmentLength");
        String name = "Segment " + layout.segmentType() + ".SegmentLength";
        if (length == null || !NUMERIC_3.matcher(length).matches()) {
            result.addError(SOURCE, name + " must be exactly 3 digits (" + layout.lengthRule() + ")");
            return;
        }
        int value = Integer.parseInt(length);
        if (value < 1 || value > layout.maxLength()) {
            result.addError(SOURCE, name + " is " + value + " but must be 001-" + layout.maxLength() + " (" + layout.lengthRule() + ")");
        } else if ("119".equals(layout.segmentType()) && value > SEGMENT_119_TABLE_LENGTH) {
            result.addWarning(name + " is " + value + ", above the 389 given in the Section 11.4.1.2 table "
                    + "(SEG119-R-038, PROVISIONAL pending SEG119-SME-008)");
        }
    }

    private static List<String> fieldNames(JsonNode node) {
        List<String> names = new ArrayList<>();
        node.fieldNames().forEachRemaining(names::add);
        return names;
    }

    private static String text(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return value != null && value.isTextual() ? value.textValue() : null;
    }
}
