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
 * Item 5 (Mutation Framework Definition) for Segment 113.
 *
 * <p>Mirrors {@link Segment108MutationTester} but operates on the AI JSON payload structure
 * consumed by {@link Segment113PayloadValidator} (rooted at {@code "ECA/TeleCheck Service
 * Transaction Request" -> "ECA/TeleCheck Data Segment"}).
 *
 * <p>Segment 113's layout is flat (no nested subelements), so every FIELD_SET mutation is
 * self-sufficient: {@code ObjectNode.put()} creates the field if it is absent from the base
 * fixture, so detection is consistent regardless of which optional fields a given synthetic
 * package happens to populate. Only the duplicate-occurrence mutation needs a dedicated kind.
 */
public final class Segment113MutationTester {

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
                "MUT-113-001", "SEG113-R-003", "Segment Type is 113",
                "field-value", "Change ECA/TeleCheck Data Segment.SegmentType from 113 to 999",
                "ECA/TeleCheck Data Segment.SegmentType", "113", "999", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-113-002", "SEG113-R-004", "Segment Length is 3 digits",
                "field-format", "Non-numeric Segment Length",
                "ECA/TeleCheck Data Segment.SegmentLength", "030", "AB1", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-113-003", "SEG113-R-005", "Segment 113 max length is 156",
                "field-value", "Segment Length exceeds maximum 156",
                "ECA/TeleCheck Data Segment.SegmentLength", "030", "157", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-113-004", "SEG113-R-008", "ECA/TeleCheck Clerk ID is alphanumeric max 6",
                "field-format", "Clerk ID contains an invalid special character",
                "ECA/TeleCheck Data Segment.EcaClerkId", "123456", "CL#1", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-113-005", "SEG113-R-009", "ECA/TeleCheck Product Code is alphanumeric max 6",
                "field-format", "Product Code contains an invalid special character",
                "ECA/TeleCheck Data Segment.EcaProductCode", "(existing or absent)", "PC#01", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-113-006", "SEG113-R-010", "ECA/TeleCheck Phone Number is numeric max 10",
                "field-format", "Phone Number contains non-numeric characters",
                "ECA/TeleCheck Data Segment.EcaPhoneNumber", "(existing or absent)", "555PHONE1", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-113-007", "SEG113-R-011", "ECA/TeleCheck Trace ID max length 22",
                "field-format", "Trace ID exceeds the 22-character maximum",
                "ECA/TeleCheck Data Segment.EcaTraceId", "(existing or absent)", "1234567890123456789012X", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-113-008", "SEG113-R-012", "Merchant Trace ID max length 25",
                "field-format", "Merchant Trace ID exceeds the 25-character maximum",
                "ECA/TeleCheck Data Segment.MerchantTraceId", "(existing or absent)", "12345678901234567890123456", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-113-009", "SEG113-R-014", "Extended MICR Data max length 65",
                "field-format", "Extended MICR Data exceeds the 65-character maximum",
                "ECA/TeleCheck Data Segment.ExtendedMicrData", "(existing or absent)",
                "1".repeat(66), true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-113-010", "SEG113-R-015", "Segment 113 appears at most once",
                "structural", "Duplicate ECA/TeleCheck Data Segment (113) added alongside the original",
                "ECA/TeleCheck Data Segment 2", "(absent)",
                "{\"SegmentType\":\"113\",\"SegmentLength\":\"030\"}", true, MutationCase.Kind.SEGMENT_DUPLICATE
            )
        );
    }

    public static MutationResult testMutation(MutationCase mutation, JsonNode basePayload) {
        Objects.requireNonNull(basePayload, "basePayload");
        ObjectNode mutated = applyMutation(basePayload.deepCopy(), mutation);
        ValidationResult result = new Segment113PayloadValidator().validatePayload(mutated);
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
        String sessionId = "seg113-mutation-session-" + System.currentTimeMillis();
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
        ObjectNode request = (ObjectNode) root.path("ECA/TeleCheck Service Transaction Request");
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
                ObjectNode duplicate = request.putObject("ECA/TeleCheck Data Segment 2");
                duplicate.put("SegmentType", "113");
                duplicate.put("SegmentLength", "030");
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
