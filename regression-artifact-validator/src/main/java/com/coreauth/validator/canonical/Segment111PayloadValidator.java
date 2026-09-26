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

/** Envelope-only validator for ATL105 Segment 111 (Variable Information). */
public final class Segment111PayloadValidator {
    private static final String SOURCE = "Segment111Payload";
    private static final Pattern THREE_DIGITS = Pattern.compile("^[0-9]{3}$");
    private static final int MAX_PER_REPETITION_VALUE_LENGTH = 985;
    private static final int MAX_REPEATED_SECTION = 991;
    private static final int MAX_TOTAL = 999;

    private final ObjectMapper mapper;

    public Segment111PayloadValidator() {
        this(new ObjectMapper());
    }

    Segment111PayloadValidator(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public ValidationResult validateFile(Path file) {
        ValidationResult r = new ValidationResult(file == null ? "segment-111-payload" : file.getFileName().toString());
        if (file == null) {
            r.addError(SOURCE, "AI JSON file is required");
            return r;
        }
        try {
            validatePayload(mapper.readTree(file.toFile()), r);
        } catch (IOException e) {
            r.addError(SOURCE, "AI JSON is not readable: " + e.getMessage());
        }
        return r;
    }

    public ValidationResult validatePayload(JsonNode payload) {
        ValidationResult r = new ValidationResult("segment-111-payload");
        validatePayload(payload, r);
        return r;
    }

    private void validatePayload(JsonNode payload, ValidationResult r) {
        if (payload == null || !payload.isObject()) {
            r.addError(SOURCE, "AI JSON root must be an object");
            return;
        }

        JsonNode request = request(payload);
        if (!request.isObject()) {
            r.addError(SOURCE, "Financial Request object is required");
            return;
        }

        JsonNode segment = segment111(request);
        if (!segment.isObject()) {
            r.addError(SOURCE, "Variable Information Data Segment object is required (SEG111-R-002)");
            return;
        }

        validateSegmentOrder(segment, r);

        String type = textual(segment, "segmentType", "SegmentType");
        if (!"111".equals(type)) {
            r.addError(SOURCE, "Variable Information Data Segment.SegmentType must be 111 (SEG111-R-001); found " + type);
        }

        JsonNode declaredNode = first(segment, "segmentLength", "SegmentLength");
        String declared = declaredNode != null && declaredNode.isTextual() ? declaredNode.asText() : null;
        if (declared == null || !THREE_DIGITS.matcher(declared).matches()) {
            r.addError(SOURCE, "Segment 111 SegmentLength must be a textual value with exactly 3 digits (SEG111-R-002)");
        }

        List<JsonNode> reps = repetitions(segment);
        int repeatedLength = 0;
        for (int i = 0; i < reps.size(); i++) {
            JsonNode rep = reps.get(i);
            validateRepetitionOrder(rep, i, r);

            String indicator = textual(rep, "variableInformationIndicator", "VariableInformationIndicator", "indicator");
            JsonNode lenNode = first(rep, "variableInformationLength", "VariableInformationLength", "length");
            String len = lenNode != null && lenNode.isTextual() ? lenNode.asText() : null;
            String value = textual(rep, "variableInformation", "VariableInformation", "value");

            if (indicator == null || !THREE_DIGITS.matcher(indicator).matches()) {
                r.addError(SOURCE, "SEG111-R-003 repetition " + i + " indicator must be a present 3-digit textual field");
            }
            if (len == null || !THREE_DIGITS.matcher(len).matches()) {
                r.addError(SOURCE, "SEG111-R-004 repetition " + i + " length must be a present 3-digit textual field");
            }
            if (value == null) {
                r.addError(SOURCE, "SEG111-R-004 repetition " + i + " variable information value is required");
            }

            int actual = value == null ? 0 : value.length();
            if (actual > MAX_PER_REPETITION_VALUE_LENGTH) {
                r.addError(SOURCE, "SEG111-R-004 repetition " + i + " variable information length exceeds per-repetition bound " + MAX_PER_REPETITION_VALUE_LENGTH);
            }
            if (len != null && THREE_DIGITS.matcher(len).matches() && Integer.parseInt(len) != actual) {
                r.addError(SOURCE, "SEG111-R-004 repetition " + i + " length does not equal variable information value length");
            }
            repeatedLength += 6 + actual;
        }

        if (repeatedLength > MAX_REPEATED_SECTION) {
            r.addError(SOURCE, "SEG111-R-005 repeated Variable Information Section exceeds 991 characters");
        }

        int computed = 3 + 3 + repeatedLength + 3;
        if (computed > MAX_TOTAL) {
            r.addError(SOURCE, "SEG111-R-006 Segment 111 total length exceeds 999 characters");
        }
        if (declared != null && THREE_DIGITS.matcher(declared).matches() && Integer.parseInt(declared) != computed) {
            r.addError(SOURCE, "SEG111-R-002 SegmentLength must equal computed structural length " + computed);
        }
    }

    private static JsonNode request(JsonNode payload) {
        JsonNode request = payload.path("request");
        if (request.isObject()) {
            return request;
        }
        return payload.path("Financial Request");
    }

    private static JsonNode segment111(JsonNode request) {
        JsonNode section3 = request.path("dataSection3");
        if (section3.isObject()) {
            JsonNode canonical = firstObject(section3, "variableInfoSegment", "Variable Information Data Segment", "variableInformationSegment");
            if (canonical.isObject()) {
                return canonical;
            }
        }
        return firstObject(request, "Variable Information Data Segment", "variableInformationSegment", "variableInfoSegment");
    }

    private static void validateSegmentOrder(JsonNode segment, ValidationResult r) {
        int type = fieldIndex(segment, "segmentType", "SegmentType");
        int length = fieldIndex(segment, "segmentLength", "SegmentLength");
        int repetitions = fieldIndex(segment, "variableInformationSection", "variableInformationSections", "VariableInformationSections", "repetitions", "sections");
        if ((type >= 0 && length >= 0 && type > length) || (length >= 0 && repetitions >= 0 && length > repetitions)) {
            r.addError(SOURCE, "SEG111-R-010 Segment 111 fields must appear in structural order: SegmentType, SegmentLength, repetitions");
        }
    }

    private static void validateRepetitionOrder(JsonNode rep, int index, ValidationResult r) {
        int indicator = fieldIndex(rep, "variableInformationIndicator", "VariableInformationIndicator", "indicator");
        int length = fieldIndex(rep, "variableInformationLength", "VariableInformationLength", "length");
        int value = fieldIndex(rep, "variableInformation", "VariableInformation", "value");
        if ((indicator >= 0 && length >= 0 && indicator > length) || (length >= 0 && value >= 0 && length > value)) {
            r.addError(SOURCE, "SEG111-R-010 repetition " + index + " fields must appear in structural order: indicator, length, value");
        }
    }

    private static JsonNode firstObject(JsonNode n, String... names) {
        for (String s : names) {
            JsonNode value = n.path(s);
            if (value.isObject()) {
                return value;
            }
        }
        return n.path("__missing");
    }

    private static JsonNode first(JsonNode n, String... names) {
        for (String s : names) {
            if (n.has(s)) {
                return n.path(s);
            }
        }
        return null;
    }

    private static String textual(JsonNode n, String... names) {
        JsonNode value = first(n, names);
        return value != null && value.isTextual() ? value.asText() : null;
    }

    private static int fieldIndex(JsonNode node, String... names) {
        if (!node.isObject()) {
            return -1;
        }
        int index = 0;
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            String name = fields.next().getKey();
            for (String expected : names) {
                if (expected.equals(name)) {
                    return index;
                }
            }
            index++;
        }
        return -1;
    }

    private static List<JsonNode> repetitions(JsonNode segment) {
        for (String key : new String[]{"variableInformationSection", "variableInformationSections", "VariableInformationSections", "repetitions", "sections"}) {
            if (segment.path(key).isArray()) {
                List<JsonNode> out = new ArrayList<>();
                segment.path(key).forEach(out::add);
                return out;
            }
        }
        if (segment.has("VariableInformationIndicator") || segment.has("variableInformationIndicator") || segment.has("indicator")
            || segment.has("VariableInformationLength") || segment.has("variableInformationLength") || segment.has("length")
            || segment.has("VariableInformation") || segment.has("variableInformation") || segment.has("value")) {
            return List.of(segment);
        }
        return List.of();
    }
}

