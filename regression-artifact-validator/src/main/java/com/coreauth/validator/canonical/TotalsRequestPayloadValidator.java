package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Validates the message structure of the two Totals requests: the Totals Request (ATL105 Section 11.4.1.1,
 * Segment 105) and the Totals with Proprietary Data Load Request (Section 11.4.1.2, Segment 119).
 * Field-level Segment 105 and 119 rules are not checked here.
 */
public final class TotalsRequestPayloadValidator {
    public static final String TOTALS_REQUEST = "Totals Request";
    public static final String TOTALS_WITH_PDL_REQUEST = "Totals with Proprietary Data Load Request";
    private static final String SOURCE = "TotalsRequestPayload";
    private static final Pattern NUMERIC_3 = Pattern.compile("^[0-9]{3}$");
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
        }
        checkNumberOfSegments(root, message, segmentCount, layout, result);
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
        return node.has(field) && !node.path(field).isNull() ? node.path(field).asText() : null;
    }
}
