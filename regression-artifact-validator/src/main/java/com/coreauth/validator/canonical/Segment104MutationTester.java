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
 * Item 5 (Mutation Framework Definition) for Segment 104.
 *
 * <p>Mirrors {@link Segment101MutationTester} but operates on the AI JSON payload structure
 * consumed by {@link Segment104PayloadValidator} (rooted at {@code "Financial Request" ->
 * "Purchase Card Data Segment"}).
 *
 * <p>Each mutation targets a specific SEG104-R-### rule and must be detected by the
 * validator. Standard 10 mutations cover the highest-severity rule classes (value, format,
 * omission, dependency).
 */
public final class Segment104MutationTester {

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
        public enum Kind { FIELD_SET, SEGMENT_ADD_DUPLICATE, SEGMENT_REMOVE_STANDARD }
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
                "MUT-104-001", "SEG104-R-004", "Segment Type is 104",
                "field-value", "Change Purchase Card Data Segment.SegmentType from 104 to 999",
                "Purchase Card Data Segment.SegmentType", "104", "999", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-104-002", "SEG104-R-005", "Segment Length is 3 digits",
                "field-format", "Non-numeric Segment Length",
                "Purchase Card Data Segment.SegmentLength", "045", "AB1", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-104-003", "SEG104-R-006", "Segment 104 max length is 86",
                "field-value", "Segment Length exceeds maximum 86",
                "Purchase Card Data Segment.SegmentLength", "045", "099", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-104-004", "SEG104-R-007", "Purchase Code is alphanumeric max 16",
                "field-format", "Purchase Code exceeds 16 characters",
                "Purchase Card Data Segment.PurchaseCode", "PC1234567890", "PC123456789012345X", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-104-005", "SEG104-R-008", "PC Tax Amount is numeric max 7",
                "field-format", "PC Tax Amount contains non-numeric characters",
                "Purchase Card Data Segment.PcTaxAmount", "0000225", "ABCDEFG", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-104-006", "SEG104-R-009", "PC Freight Amount is numeric max 7",
                "field-format", "PC Freight Amount exceeds 7 digits",
                "Purchase Card Data Segment.PcFreightAmount", "0000500", "123456789", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-104-007", "SEG104-R-010", "PC Duty Amount is numeric max 7",
                "field-format", "PC Duty Amount contains non-numeric characters",
                "Purchase Card Data Segment.PcDutyAmount", "0000000", "XYZDUTY", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-104-008", "SEG104-R-011", "Ship-to Country Code is exactly 3 digits",
                "field-format", "Ship-to Country Code is not exactly 3 digits",
                "Purchase Card Data Segment.ShipToCountryCode", "840", "12", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-104-009", "SEG104-R-020", "At most one Segment 104 per message",
                "structural", "Add a second Purchase Card Data Segment (104) alongside the primary one",
                "Additional Purchase Card Data Segment", "", "{\"SegmentType\":\"104\",\"SegmentLength\":\"010\"}",
                true, MutationCase.Kind.SEGMENT_ADD_DUPLICATE
            ),
            new MutationCase(
                "MUT-104-010", "SEG104-R-001", "Segment 104 requires Segment 100 sibling",
                "structural", "Remove Standard Segment (100) from the request",
                "Standard Segment", "present", "absent", true, MutationCase.Kind.SEGMENT_REMOVE_STANDARD
            )
        );
    }

    public static MutationResult testMutation(MutationCase mutation, JsonNode basePayload) {
        Objects.requireNonNull(basePayload, "basePayload");
        ObjectNode mutated = applyMutation(basePayload.deepCopy(), mutation);
        ValidationResult result = new Segment104PayloadValidator().validatePayload(mutated);
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
        String sessionId = "seg104-mutation-session-" + System.currentTimeMillis();
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
            case SEGMENT_ADD_DUPLICATE -> {
                ObjectNode duplicate = request.putObject("Additional Purchase Card Data Segment");
                duplicate.put("SegmentType", "104");
                duplicate.put("SegmentLength", "010");
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
