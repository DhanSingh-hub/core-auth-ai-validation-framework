package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/** Item 5 (Mutation Framework Definition) for Segment 111. */
public final class Segment111MutationTester {

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
            REPLACE_REPETITIONS,
            STRUCTURAL_ORDER_VIOLATION
        }
    }

    public record MutationResult(MutationCase mutation, boolean detected, String detectionReason) {}
    public record MutationReport(String testSessionId, List<MutationResult> results, MutationMetrics metrics) {}
    public record MutationMetrics(
        int totalMutations, int detectedMutations, int missedMutations,
        double detectionRate, List<String> undetectedMutations
    ) {}

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static List<MutationCase> generateStandardMutations() {
        return List.of(
            new MutationCase(
                "MUT-001", "SEG111-R-001", "Segment Type is fixed 111",
                "field-value", "Change Segment 111 type from 111 to 999",
                "segmentType", "111", "999", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-002", "SEG111-R-002", "SegmentLength is a textual 3-digit field",
                "field-format", "Set SegmentLength to a JSON number instead of a textual 3-digit value",
                "segmentLength", "016", "16", true, MutationCase.Kind.FIELD_SET_NUMBER
            ),
            new MutationCase(
                "MUT-003", "SEG111-R-002", "SegmentLength is exactly three digits",
                "field-format", "Set SegmentLength to a two-digit textual value",
                "segmentLength", "016", "16", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-004", "SEG111-R-003", "Variable information indicator is present and 3 digits",
                "field-omission", "Remove the first repetition indicator",
                "variableInformationSections[0].variableInformationIndicator", "001", "<omitted>", true, MutationCase.Kind.FIELD_REMOVE
            ),
            new MutationCase(
                "MUT-005", "SEG111-R-004", "Variable information length is present and 3 digits",
                "field-omission", "Remove the first repetition length",
                "variableInformationSections[0].variableInformationLength", "001", "<omitted>", true, MutationCase.Kind.FIELD_REMOVE
            ),
            new MutationCase(
                "MUT-006", "SEG111-R-004", "Variable information length matches value length",
                "field-value", "Declare length 002 for a one-character variable information value",
                "variableInformationSections[0].variableInformationLength", "001", "002", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-007", "SEG111-R-004", "Per-repetition value length stays within bounds",
                "boundary", "Make one repetition value longer than the per-repetition bound",
                "variableInformationSections", "one bounded repetition", "one 986-character value", true, MutationCase.Kind.REPLACE_REPETITIONS
            ),
            new MutationCase(
                "MUT-008", "SEG111-R-006", "Segment 111 total length is at most 999",
                "boundary", "Create a 991-character repeated section so computed total length is 1000",
                "variableInformationSections", "computed total 016", "computed total 1000", true, MutationCase.Kind.REPLACE_REPETITIONS
            ),
            new MutationCase(
                "MUT-009", "SEG111-R-005", "Repeated Variable Information Section is at most 991",
                "boundary", "Create repeated sections whose combined repeated payload exceeds 991",
                "variableInformationSections", "repeated sum 7", "repeated sum 998", true, MutationCase.Kind.REPLACE_REPETITIONS
            ),
            new MutationCase(
                "MUT-010", "SEG111-R-010", "Segment 111 structural field order is preserved",
                "structural", "Move SegmentLength before SegmentType",
                "variableInfoSegment field order", "SegmentType before SegmentLength", "SegmentLength before SegmentType", true, MutationCase.Kind.STRUCTURAL_ORDER_VIOLATION
            )
        );
    }

    public static MutationResult testMutation(MutationCase mutation, JsonNode basePayload) {
        Objects.requireNonNull(basePayload, "basePayload");
        ObjectNode mutated = applyMutation(basePayload.deepCopy(), mutation);
        ValidationResult result = new Segment111PayloadValidator().validatePayload(mutated);
        boolean detected = result.errors().stream()
            .anyMatch(err -> err.reason() != null && err.reason().contains(mutation.ruleId));
        String reason = detected
            ? result.errors().stream()
                .filter(err -> err.reason() != null && err.reason().contains(mutation.ruleId))
                .map(err -> err.reason())
                .findFirst().orElse("detected")
            : "validator did not raise " + mutation.ruleId + "; errors=" + result.errors();
        return new MutationResult(mutation, detected, reason);
    }

    public static MutationReport runMutationSuite(JsonNode basePayload, List<MutationCase> mutations) {
        String sessionId = "seg111-mutation-session-" + System.currentTimeMillis();
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
        ObjectNode segment = mutableSegment(root);
        if (segment == null) {
            return root;
        }

        switch (mutation.kind) {
            case FIELD_SET -> setField(segment, mutation.jsonPath, mutation.mutatedValue);
            case FIELD_SET_NUMBER -> setNumberField(segment, mutation.jsonPath, Integer.parseInt(mutation.mutatedValue));
            case FIELD_REMOVE -> removeField(segment, mutation.jsonPath);
            case REPLACE_REPETITIONS -> replaceRepetitions(segment, mutation.mutationId());
            case STRUCTURAL_ORDER_VIOLATION -> reorderSegmentLengthBeforeType(segment);
        }
        return root;
    }

    private static ObjectNode mutableSegment(ObjectNode root) {
        JsonNode requestNode = root.path("request").isObject() ? root.path("request") : root.path("Financial Request");
        if (!requestNode.isObject()) {
            return null;
        }
        ObjectNode request = (ObjectNode) requestNode;
        JsonNode section3 = request.path("dataSection3");
        if (section3.isObject()) {
            JsonNode canonical = firstObject(section3, "variableInfoSegment", "Variable Information Data Segment", "variableInformationSegment");
            if (canonical.isObject()) {
                return (ObjectNode) canonical;
            }
        }
        JsonNode legacy = firstObject(request, "Variable Information Data Segment", "variableInformationSegment", "variableInfoSegment");
        return legacy.isObject() ? (ObjectNode) legacy : null;
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
        Target target = target(segment, path);
        if (target.node != null) {
            target.node.put(target.field, value);
        }
    }

    private static void setNumberField(ObjectNode segment, String path, int value) {
        Target target = target(segment, path);
        if (target.node != null) {
            target.node.put(target.field, value);
        }
    }

    private static void removeField(ObjectNode segment, String path) {
        Target target = target(segment, path);
        if (target.node != null) {
            target.node.remove(target.field);
        }
    }

    private static Target target(ObjectNode segment, String path) {
        if (path.startsWith("variableInformationSections[0].")) {
            ObjectNode repetition = firstRepetition(segment);
            return new Target(repetition, preferredField(repetition, path.substring("variableInformationSections[0].".length())));
        }
        return new Target(segment, preferredField(segment, path));
    }

    private static ObjectNode firstRepetition(ObjectNode segment) {
        for (String key : new String[]{"variableInformationSection", "variableInformationSections", "VariableInformationSections", "repetitions", "sections"}) {
            JsonNode reps = segment.path(key);
            if (reps.isArray() && !reps.isEmpty() && reps.get(0).isObject()) {
                return (ObjectNode) reps.get(0);
            }
        }
        return segment;
    }

    private static String preferredField(ObjectNode node, String canonicalName) {
        String pascal = switch (canonicalName) {
            case "segmentType" -> "SegmentType";
            case "segmentLength" -> "SegmentLength";
            case "variableInformationIndicator" -> "VariableInformationIndicator";
            case "variableInformationLength" -> "VariableInformationLength";
            case "variableInformation" -> "VariableInformation";
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

    private static void replaceRepetitions(ObjectNode segment, String mutationId) {
        String key = repetitionArrayKey(segment);
        ArrayNode reps = MAPPER.createArrayNode();
        if ("MUT-007".equals(mutationId)) {
            reps.add(repetition("001", repeat('A', 986)));
            putSegmentLength(segment, "995");
        } else if ("MUT-008".equals(mutationId)) {
            reps.add(repetition("985", repeat('A', 985)));
            putSegmentLength(segment, "999");
        } else if ("MUT-009".equals(mutationId)) {
            reps.add(repetition("493", repeat('A', 493)));
            reps.add(repetition("493", repeat('B', 493)));
            putSegmentLength(segment, "999");
        }
        segment.set(key, reps);
        removeLegacyRepetitionFields(segment);
    }

    private static ObjectNode repetition(String length, String value) {
        ObjectNode rep = MAPPER.createObjectNode();
        rep.put("variableInformationIndicator", "001");
        rep.put("variableInformationLength", length);
        rep.put("variableInformation", value);
        return rep;
    }

    private static String repetitionArrayKey(ObjectNode segment) {
        for (String key : new String[]{"variableInformationSection", "variableInformationSections", "VariableInformationSections", "repetitions", "sections"}) {
            if (segment.has(key)) {
                return key;
            }
        }
        return segment.has("SegmentType") ? "repetitions" : "variableInformationSections";
    }

    private static void putSegmentLength(ObjectNode segment, String length) {
        segment.put(preferredField(segment, "segmentLength"), length);
    }

    private static void removeLegacyRepetitionFields(ObjectNode segment) {
        segment.remove(List.of("VariableInformationIndicator", "VariableInformationLength", "VariableInformation", "indicator", "length", "value"));
    }

    private static void reorderSegmentLengthBeforeType(ObjectNode segment) {
        ObjectNode reordered = MAPPER.createObjectNode();
        String lengthField = preferredField(segment, "segmentLength");
        String typeField = preferredField(segment, "segmentType");
        JsonNode length = segment.get(lengthField);
        JsonNode type = segment.get(typeField);
        if (length != null) {
            reordered.set(lengthField, length);
        }
        if (type != null) {
            reordered.set(typeField, type);
        }
        segment.fields().forEachRemaining(entry -> {
            if (!entry.getKey().equals(lengthField) && !entry.getKey().equals(typeField)) {
                reordered.set(entry.getKey(), entry.getValue());
            }
        });
        segment.removeAll();
        reordered.fields().forEachRemaining(entry -> segment.set(entry.getKey(), entry.getValue()));
    }

    private static String repeat(char c, int count) {
        return String.valueOf(c).repeat(count);
    }

    private record Target(ObjectNode node, String field) {}

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
        return Arrays.stream(generateStandardMutations().toArray(new MutationCase[0]))
            .filter(m -> ruleId.equals(m.ruleId()))
            .toList();
    }
}

