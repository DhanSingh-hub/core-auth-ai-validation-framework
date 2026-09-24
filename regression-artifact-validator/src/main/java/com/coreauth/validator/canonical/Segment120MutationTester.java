package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Item 5 (Mutation Framework Definition) for Segment 120.
 *
 * <p>Defines the 10 standard mutations covering all 8 catalog rules: SEG120-R-001 through
 * SEG120-R-008. SEG120-R-007 (final-segment ordering) is now hard-enforced in
 * {@link Segment120PayloadValidator} following the 2026-09-23 resolution of P-01 (literal spec
 * text BR-263-2 "always appears at the end" is authoritative over
 * {@code atl105_complete_templates.json}'s numeric-sort ordering artifact -- see
 * {@code docs/specs/kb/segment-120/coverage/segment-120-coverage-sme-tba-note.md}), so MUT-009
 * below reorders Segment 120 away from the final position via an explicit
 * {@code dataSection3.segmentOrder} array and asserts it is detected.
 *
 * <p>MUT-010 (SEG120-R-008, Blackhawk '\' delimiter) is a deliberate NEGATIVE-of-a-mutation case:
 * {@code shouldDetect} is {@code false}. Per PROVISIONAL P-03 (open) and the catalog's own
 * {@code "severity": "info"} tag on SEG120-R-008, the envelope validator must NOT reject
 * backslash-delimited Print Data; this case verifies that pass-through behavior rather than a
 * detected defect.
 */
public final class Segment120MutationTester {

    public record MutationCase(
        String mutationId,
        String ruleId,
        String ruleTitle,
        String mutationType,
        String description,
        String jsonPath,
        String originalValue,
        String mutatedValue,
        boolean shouldDetect,
        Kind kind
    ) {
        public enum Kind {
            FIELD_SET,
            FIELD_SET_NUMBER,
            FIELD_REMOVE,
            OVERSIZE_PRINT_DATA,
            WIRE_FORMAT_MISSING_SEPARATOR,
            WIRE_FORMAT_TRAILING_SEPARATOR,
            MOVE_TO_REQUEST_CONTEXT,
            INSERT_BLACKHAWK_DELIMITER,
            REORDER_SEGMENT_120
        }
    }

    public record MutationResult(MutationCase mutation, boolean detected, String detectionReason) {}
    public record MutationReport(String testSessionId, List<MutationResult> results, MutationMetrics metrics) {}
    public record MutationMetrics(
        int totalMutations, int detectedMutations, int missedMutations,
        double detectionRate, List<String> undetectedMutations
    ) {}

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final char SEP = '\u001c';
    private static final String BASELINE_PRINT_DATA = "THANK YOU FOR SHOPPING WITH US TODAY";
    private static final String BLACKHAWK_PRINT_DATA = "BLACKHAWK ACTIVATION\\CARD VALUE 25.00\\PHONE 5551234567\\THANK YOU";

    public static List<MutationCase> generateStandardMutations() {
        return List.of(
            new MutationCase(
                "MUT-001", "SEG120-R-001", "Segment Type is fixed 120",
                "field-value", "Change Segment 120 type from 120 to 999",
                "segmentType", "120", "999", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-002", "SEG120-R-002", "SegmentLength is a textual 4-digit field",
                "field-format", "Set segmentLength to a JSON number instead of a textual 4-digit value",
                "segmentLength", "0041", "41", true, MutationCase.Kind.FIELD_SET_NUMBER
            ),
            new MutationCase(
                "MUT-003", "SEG120-R-002", "SegmentLength is always exactly 4 digits (P-02-WIDTH resolved)",
                "field-format", "Set segmentLength to a three-digit textual value",
                "segmentLength", "0041", "041", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-004", "SEG120-R-003", "Print Data is required",
                "field-omission", "Remove printData entirely",
                "printData", BASELINE_PRINT_DATA, "<omitted>", true, MutationCase.Kind.FIELD_REMOVE
            ),
            new MutationCase(
                "MUT-005", "SEG120-R-004", "Print Data must not exceed 999 characters",
                "boundary", "Set printData to a 1000-character value (one over the field cap)",
                "printData", "36-character value", "1000-character value", true, MutationCase.Kind.OVERSIZE_PRINT_DATA
            ),
            new MutationCase(
                "MUT-006", "SEG120-R-005", "Exactly one separator between each field pair",
                "separator-omission", "Serialize wireFormat with only one field separator instead of two",
                "wireFormat", "120\\u001c0041\\u001cTHANK...", "120\\u001c0041THANK...", true, MutationCase.Kind.WIRE_FORMAT_MISSING_SEPARATOR
            ),
            new MutationCase(
                "MUT-007", "SEG120-R-005", "No trailing separator after Print Data (unlike Segment 111)",
                "separator-omission", "Serialize wireFormat with a trailing field separator after Print Data",
                "wireFormat", "...TODAY", "...TODAY\\u001c", true, MutationCase.Kind.WIRE_FORMAT_TRAILING_SEPARATOR
            ),
            new MutationCase(
                "MUT-008", "SEG120-R-006", "Segment 120 is response-only (never on a request)",
                "structural", "Move the Print Data 2 Segment from the response container into the request container",
                "response.dataSection3.printData2Segment", "response", "request", true, MutationCase.Kind.MOVE_TO_REQUEST_CONTEXT
            ),
            new MutationCase(
                "MUT-009", "SEG120-R-007", "Segment 120 is always the final segment of Data Section 3 (P-01 resolved)",
                "structural", "Declare an explicit dataSection3.segmentOrder with Segment 120 ahead of another companion segment",
                "dataSection3.segmentOrder", "[\"112\",\"115\",\"120\"]", "[\"112\",\"120\",\"134\"]", true, MutationCase.Kind.REORDER_SEGMENT_120
            ),
            new MutationCase(
                "MUT-010", "SEG120-R-008", "Blackhawk '\\' line delimiter is opaque content (P-03 open, info severity)",
                "content", "Insert Blackhawk '\\'-delimited receipt text into printData; must NOT be rejected",
                "printData", BASELINE_PRINT_DATA, BLACKHAWK_PRINT_DATA, false, MutationCase.Kind.INSERT_BLACKHAWK_DELIMITER
            )
        );
    }

    public static MutationResult testMutation(MutationCase mutation, JsonNode basePayload) {
        Objects.requireNonNull(basePayload, "basePayload");
        ObjectNode mutated = applyMutation(basePayload.deepCopy(), mutation);
        ValidationResult result = new Segment120PayloadValidator().validatePayload(mutated);
        boolean flaggedForRule = result.errors().stream()
            .anyMatch(err -> err.reason() != null && err.reason().contains(mutation.ruleId));
        boolean detected = mutation.shouldDetect() == flaggedForRule;
        String reason;
        if (flaggedForRule) {
            reason = result.errors().stream()
                .filter(err -> err.reason() != null && err.reason().contains(mutation.ruleId))
                .map(err -> err.reason())
                .findFirst().orElse("detected");
        } else {
            reason = mutation.shouldDetect()
                ? "validator did not raise " + mutation.ruleId + "; errors=" + result.errors()
                : "validator correctly passed through content without raising " + mutation.ruleId;
        }
        return new MutationResult(mutation, detected, reason);
    }

    public static MutationReport runMutationSuite(JsonNode basePayload, List<MutationCase> mutations) {
        String sessionId = "seg120-mutation-session-" + System.currentTimeMillis();
        List<MutationResult> results = new ArrayList<>();
        for (MutationCase mutation : mutations) {
            results.add(testMutation(mutation, basePayload));
        }
        return new MutationReport(sessionId, results, computeMetrics(results));
    }

    public JsonNode loadPayload(Path aiJsonFile) throws IOException {
        return MAPPER.readTree(aiJsonFile.toFile());
    }

    private static ObjectNode applyMutation(JsonNode base, MutationCase mutation) {
        ObjectNode root = (ObjectNode) base;
        if (mutation.kind() == MutationCase.Kind.MOVE_TO_REQUEST_CONTEXT) {
            moveToRequestContext(root);
            return root;
        }
        if (mutation.kind() == MutationCase.Kind.REORDER_SEGMENT_120) {
            reorderSegment120(root);
            return root;
        }
        ObjectNode segment = mutableSegment(root);
        if (segment == null) {
            return root;
        }
        switch (mutation.kind()) {
            case FIELD_SET -> setField(segment, mutation.jsonPath(), mutation.mutatedValue());
            case FIELD_SET_NUMBER -> setNumberField(segment, mutation.jsonPath(), Integer.parseInt(mutation.mutatedValue()));
            case FIELD_REMOVE -> removeField(segment, mutation.jsonPath());
            case OVERSIZE_PRINT_DATA -> oversizePrintData(segment);
            case WIRE_FORMAT_MISSING_SEPARATOR -> setWireFormatMissingSeparator(segment);
            case WIRE_FORMAT_TRAILING_SEPARATOR -> setWireFormatTrailingSeparator(segment);
            case INSERT_BLACKHAWK_DELIMITER -> insertBlackhawkDelimiter(segment);
            default -> { }
        }
        return root;
    }

    private static ObjectNode mutableSegment(ObjectNode root) {
        JsonNode responseNode = root.path("response").isObject() ? root.path("response")
            : root.path("Financial Transaction Response").isObject() ? root.path("Financial Transaction Response")
            : root.path("request");
        if (!responseNode.isObject()) {
            return null;
        }
        JsonNode section3 = responseNode.path("dataSection3");
        JsonNode segment = firstObject(section3, "printData2Segment", "Print Data 2 Segment", "segment120");
        return segment.isObject() ? (ObjectNode) segment : null;
    }

    private static JsonNode firstObject(JsonNode node, String... names) {
        for (String name : names) {
            JsonNode child = node.path(name);
            if (child.isObject()) {
                return child;
            }
        }
        return node.path("__missing");
    }

    private static void setField(ObjectNode segment, String path, String value) {
        String field = fieldNameOf(path);
        String preferred = preferredField(segment, field);
        segment.put(preferred, value);
    }

    private static void setNumberField(ObjectNode segment, String path, int value) {
        String field = fieldNameOf(path);
        String preferred = preferredField(segment, field);
        segment.put(preferred, value);
    }

    private static void removeField(ObjectNode segment, String path) {
        String field = fieldNameOf(path);
        segment.remove(preferredField(segment, field));
    }

    private static String fieldNameOf(String path) {
        int dot = path.lastIndexOf('.');
        return dot >= 0 ? path.substring(dot + 1) : path;
    }

    private static String preferredField(ObjectNode node, String canonicalName) {
        String pascal = switch (canonicalName) {
            case "segmentType" -> "SegmentType";
            case "segmentLength" -> "SegmentLength";
            case "printData" -> "PrintData";
            default -> canonicalName;
        };
        if (node.has(canonicalName)) {
            return canonicalName;
        }
        if (node.has(pascal)) {
            return pascal;
        }
        return canonicalName;
    }

    private static void oversizePrintData(ObjectNode segment) {
        String oversized = "A".repeat(1000);
        segment.put(preferredField(segment, "printData"), oversized);
        segment.put(preferredField(segment, "segmentLength"), zeroPad(5 + oversized.length()));
    }

    private static void setWireFormatMissingSeparator(ObjectNode segment) {
        String printData = textOrDefault(segment, "printData", BASELINE_PRINT_DATA);
        String length = textOrDefault(segment, "segmentLength", "0041");
        // Only one separator (between Segment Type and Segment Length); none before Print Data.
        segment.put("wireFormat", "120" + SEP + length + printData);
    }

    private static void setWireFormatTrailingSeparator(ObjectNode segment) {
        String printData = textOrDefault(segment, "printData", BASELINE_PRINT_DATA);
        String length = textOrDefault(segment, "segmentLength", "0041");
        segment.put("wireFormat", "120" + SEP + length + SEP + printData + SEP);
    }

    private static void insertBlackhawkDelimiter(ObjectNode segment) {
        segment.put(preferredField(segment, "printData"), BLACKHAWK_PRINT_DATA);
        segment.put(preferredField(segment, "segmentLength"), zeroPad(5 + BLACKHAWK_PRINT_DATA.length()));
    }

    private static void moveToRequestContext(ObjectNode root) {
        JsonNode response = root.path("response");
        if (response.isObject()) {
            root.set("request", response);
            root.remove("response");
        }
    }

    /**
     * SEG120-R-007 mutation: declares an explicit {@code dataSection3.segmentOrder} array with
     * Segment 120 ("120") placed before another companion segment number instead of last,
     * directly violating the P-01-resolved "always appears at the end" rule.
     */
    private static void reorderSegment120(ObjectNode root) {
        JsonNode responseNode = root.path("response").isObject() ? root.path("response") : root.path("request");
        if (!responseNode.isObject()) {
            return;
        }
        JsonNode section3Node = responseNode.path("dataSection3");
        if (!section3Node.isObject()) {
            return;
        }
        ObjectNode section3 = (ObjectNode) section3Node;
        section3.putArray("segmentOrder").add("112").add("120").add("134");
    }

    private static String textOrDefault(ObjectNode segment, String canonicalName, String fallback) {
        JsonNode value = segment.path(preferredField(segment, canonicalName));
        return value.isTextual() ? value.asText() : fallback;
    }

    private static String zeroPad(int value) {
        return String.format("%04d", value);
    }

    private static MutationMetrics computeMetrics(List<MutationResult> results) {
        long detected = results.stream().filter(MutationResult::detected).count();
        long missed = results.stream().filter(r -> !r.detected()).count();
        double rate = results.isEmpty() ? 0 : (detected * 100.0) / results.size();
        List<String> undetected = results.stream()
            .filter(r -> !r.detected())
            .map(r -> r.mutation.mutationId() + " (" + r.mutation.ruleId() + ")")
            .toList();
        return new MutationMetrics(results.size(), (int) detected, (int) missed, rate, undetected);
    }

    public static List<String> allMutationIds() {
        return generateStandardMutations().stream().map(MutationCase::mutationId).toList();
    }

    public static List<MutationCase> mutationsForRule(String ruleId) {
        return generateStandardMutations().stream()
            .filter(m -> ruleId.equals(m.ruleId()))
            .toList();
    }
}
