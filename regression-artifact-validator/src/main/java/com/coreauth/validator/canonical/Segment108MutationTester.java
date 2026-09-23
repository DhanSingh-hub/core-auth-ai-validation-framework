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
 * Item 5 (Mutation Framework Definition) for Segment 108.
 *
 * <p>Mirrors {@link Segment103MutationTester} but operates on the AI JSON payload structure
 * consumed by {@link Segment108PayloadValidator} (rooted at {@code "Loyalty Card Transaction
 * Request" -> "Loyalty Card Data Segment"}).
 *
 * <p>Segment 108's layout is flat (no nested WIC/EBT-Program-style subelements), so every
 * FIELD_SET mutation is self-sufficient: {@code ObjectNode.put()} creates the field if it is
 * absent from the base fixture, so detection is consistent regardless of which optional fields
 * a given synthetic package happens to populate. Only the duplicate-occurrence mutation needs
 * a dedicated kind.
 */
public final class Segment108MutationTester {

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
        public enum Kind { FIELD_SET, SEGMENT_DUPLICATE }
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
                "MUT-108-001", "SEG108-R-003", "Segment Type is 108",
                "field-value", "Change Loyalty Card Data Segment.SegmentType from 108 to 999",
                "Loyalty Card Data Segment.SegmentType", "108", "999", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-108-002", "SEG108-R-004", "Segment Length is 3 digits",
                "field-format", "Non-numeric Segment Length",
                "Loyalty Card Data Segment.SegmentLength", "042", "AB1", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-108-003", "SEG108-R-005", "Segment 108 max length is 142",
                "field-value", "Segment Length exceeds maximum 142",
                "Loyalty Card Data Segment.SegmentLength", "042", "143", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-108-004", "SEG108-R-008", "Loyalty Program ID is numeric max 6",
                "field-format", "Loyalty Program ID contains non-numeric characters",
                "Loyalty Card Data Segment.LoyaltyProgramId", "123456", "PRG1AB", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-108-005", "SEG108-R-009", "Loyalty Account Number is numeric max 24",
                "field-format", "Loyalty Account Number contains non-numeric characters",
                "Loyalty Card Data Segment.LoyaltyAccountNumber", "(existing or absent)", "ACCT123XYZ", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-108-006", "SEG108-R-013", "Update Code enumeration",
                "field-value", "Update Code set to an undocumented value",
                "Loyalty Card Data Segment.UpdateCode", "(existing or absent)", "Z", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-108-007", "SEG108-R-017", "Payment Tender Type enumeration",
                "field-value", "Payment Tender Type set to an undocumented value",
                "Loyalty Card Data Segment.PaymentTenderType", "(existing or absent)", "ZZ", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-108-008", "SEG108-R-018", "Loyalty Track 2 Data max length 38",
                "field-format", "Loyalty Track 2 Data exceeds 38-character maximum",
                "Loyalty Card Data Segment.LoyaltyTrack2Data", "(existing or absent)",
                "123456789012345678901234567890123456789", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-108-009", "SEG108-R-019", "Loyalty Information Version is 1 or 2",
                "field-value", "Loyalty Information Version set to an undocumented value",
                "Loyalty Card Data Segment.LoyaltyInformationVersion", "(existing or absent)", "9", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-108-010", "SEG108-R-021", "Segment 108 appears exactly once",
                "structural", "Duplicate Loyalty Card Data Segment (108) added alongside the original",
                "Loyalty Card Data Segment 2", "(absent)",
                "{\"SegmentType\":\"108\",\"SegmentLength\":\"042\"}", true, MutationCase.Kind.SEGMENT_DUPLICATE
            )
        );
    }

    public static MutationResult testMutation(MutationCase mutation, JsonNode basePayload) {
        Objects.requireNonNull(basePayload, "basePayload");
        ObjectNode mutated = applyMutation(basePayload.deepCopy(), mutation);
        ValidationResult result = new Segment108PayloadValidator().validatePayload(mutated);
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
        String sessionId = "seg108-mutation-session-" + System.currentTimeMillis();
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
        ObjectNode request = (ObjectNode) root.path("Loyalty Card Transaction Request");
        if (request == null || request.isMissingNode()) {
            return root;
        }
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
            case SEGMENT_DUPLICATE -> {
                ObjectNode duplicate = request.putObject("Loyalty Card Data Segment 2");
                duplicate.put("SegmentType", "108");
                duplicate.put("SegmentLength", "042");
            }
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
