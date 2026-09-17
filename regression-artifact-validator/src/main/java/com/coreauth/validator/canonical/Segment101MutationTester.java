package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Item 5 (Mutation Framework Definition) for Segment 101.
 *
 * <p>Mirrors {@link Segment100MutationTester} but operates on the AI JSON payload structure
 * consumed by {@link Segment101PayloadValidator} (rooted at {@code "Financial Request" ->
 * "Fleet Data Segment"}).
 *
 * <p>Each mutation targets a specific SEG101-R-### rule and must be detected by the
 * validator. Standard 10 mutations cover the highest-severity rule classes.
 */
public final class Segment101MutationTester {

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
        public enum Kind { FIELD_SET, FIELD_REMOVE, SEGMENT_ADD_145, SEGMENT_REMOVE_STANDARD }
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
                "MUT-101-001", "SEG101-R-004", "Segment Type is 101",
                "field-value", "Change Fleet Data Segment.SegmentType from 101 to 999",
                "Fleet Data Segment.SegmentType", "101", "999", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-101-002", "SEG101-R-005", "Segment Length is 3 digits",
                "field-format", "Non-numeric Segment Length",
                "Fleet Data Segment.SegmentLength", "041", "AB1", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-101-003", "SEG101-R-006", "Base Segment 101 max length is 61",
                "field-value", "Segment Length exceeds base max 61",
                "Fleet Data Segment.SegmentLength", "041", "099", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-101-004", "SEG101-R-009", "Odometer is numeric max 8",
                "field-format", "Odometer contains non-numeric characters",
                "Fleet Data Segment.Odometer", "00087652", "ABCDEFGH", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-101-005", "SEG101-R-018", "User ID must be non-zero",
                "field-value", "User ID is all zeros",
                "Fleet Data Segment.UserId", "USR000042", "000000000000", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-101-006", "SEG101-R-020", "Fleet Tag fields only in Auth Completion",
                "field-value", "Fleet Tag in original authorization",
                "Fleet Data Segment.FleetTag1", "", "PONWO-1", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-101-007", "SEG101-R-023", "Fleet Tag code must be valid",
                "field-value", "Unknown Fleet Tag code",
                "Fleet Data Segment.FleetTag1", "PONWO-1", "XYZbadpayload", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-101-008", "SEG101-R-024", "Fleet Tag data format per code",
                "field-format", "TLH tag with non-numeric payload",
                "Fleet Data Segment.FleetTag2", "TLH000123", "TLHabcdef", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-101-009", "SEG101-R-003", "Mutual exclusion with Segment 145",
                "structural", "Add Enhanced Fleet Data Segment (145) alongside Segment 101",
                "Enhanced Fleet Data Segment", "", "{\"SegmentType\":\"145\",\"SegmentLength\":\"030\"}",
                true, MutationCase.Kind.SEGMENT_ADD_145
            ),
            new MutationCase(
                "MUT-101-010", "SEG101-R-001", "Segment 101 requires Segment 100 sibling",
                "structural", "Remove Standard Segment (100) from the request",
                "Standard Segment", "present", "absent", true, MutationCase.Kind.SEGMENT_REMOVE_STANDARD
            )
        );
    }

    public static MutationResult testMutation(MutationCase mutation, JsonNode basePayload) {
        Objects.requireNonNull(basePayload, "basePayload");
        ObjectNode mutated = applyMutation(basePayload.deepCopy(), mutation);
        ValidationResult result = new Segment101PayloadValidator().validatePayload(mutated);
        boolean detected = result.errors().stream()
            .anyMatch(err -> err.reason() != null && err.reason().contains(mutation.ruleId));
        String reason = detected
            ? result.errors().stream()
                .filter(err -> err.reason() != null && err.reason().contains(mutation.ruleId))
                .map(err -> err.reason())
                .findFirst().orElse("detected")
            : "validator did not raise " + mutation.ruleId;
        return new MutationResult(mutation, detected, reason);
    }

    public static MutationReport runMutationSuite(JsonNode basePayload, List<MutationCase> mutations) {
        String sessionId = "seg101-mutation-session-" + System.currentTimeMillis();
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
        ObjectNode request = (ObjectNode) root.path("Financial Request");
        switch (mutation.kind) {
            case FIELD_SET -> {
                String[] parts = mutation.jsonPath.split("\\.", 2);
                if (parts.length != 2) {
                    return root;
                }
                ObjectNode segment = (ObjectNode) request.path(parts[0]);
                if (segment.isMissingNode() || !segment.isObject()) {
                    return root;
                }
                segment.put(parts[1], mutation.mutatedValue);
            }
            case FIELD_REMOVE -> {
                String[] parts = mutation.jsonPath.split("\\.", 2);
                if (parts.length != 2) {
                    return root;
                }
                ObjectNode segment = (ObjectNode) request.path(parts[0]);
                if (segment.isObject()) {
                    segment.remove(parts[1]);
                }
            }
            case SEGMENT_ADD_145 -> {
                ObjectNode enhanced = request.putObject("Enhanced Fleet Data Segment");
                enhanced.put("SegmentType", "145");
                enhanced.put("SegmentLength", "030");
            }
            case SEGMENT_REMOVE_STANDARD -> request.remove("Standard Segment");
        }
        return root;
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

    /** Convenience: give me all mutation IDs. */
    public static List<String> allMutationIds() {
        return generateStandardMutations().stream().map(MutationCase::mutationId).toList();
    }

    /** Convenience: filter mutations by rule class group. */
    public static List<MutationCase> mutationsForRule(String ruleId) {
        return Arrays.stream(generateStandardMutations().toArray(new MutationCase[0]))
            .filter(m -> ruleId.equals(m.ruleId()))
            .toList();
    }
}
