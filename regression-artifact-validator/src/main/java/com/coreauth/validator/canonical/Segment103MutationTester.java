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

/**
 * Item 5 (Mutation Framework Definition) for Segment 103.
 *
 * <p>Mirrors {@link Segment101MutationTester} but operates on the AI JSON payload structure
 * consumed by {@link Segment103PayloadValidator} (rooted at {@code "Financial Request" ->
 * "EBT Data Segment"}).
 *
 * <p>Each mutation targets a specific SEG103-R-### rule and is self-sufficient: it creates any
 * nested structure (WicProductData, EbtProgramData.subelements) it needs rather than relying on
 * the base fixture already containing it, so detection is consistent across every synthetic
 * package regardless of which optional fields that package happens to populate.
 */
public final class Segment103MutationTester {

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
        public enum Kind { FIELD_SET, WIC_PRODUCT_TOTAL_LENGTH_MISMATCH, EBT_PROGRAM_TAG_INVALID,
            EBT_PROGRAM_ACCOUNT_TYPE_MISSING, SEGMENT_DUPLICATE }
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
                "MUT-103-001", "SEG103-R-003", "Segment Type is 103",
                "field-value", "Change EBT Data Segment.SegmentType from 103 to 999",
                "EBT Data Segment.SegmentType", "103", "999", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-103-002", "SEG103-R-004", "Segment Length is 3 or 4 digits",
                "field-format", "Non-numeric Segment Length",
                "EBT Data Segment.SegmentLength", "018", "AB1", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-103-003", "SEG103-R-005", "Segment 103 max length is 3334",
                "field-value", "Segment Length exceeds maximum 3334",
                "EBT Data Segment.SegmentLength", "018", "3335", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-103-004", "SEG103-R-009", "Clerk ID is numeric max 10",
                "field-format", "Clerk ID contains non-numeric characters",
                "EBT Data Segment.ClerkId", "0000012345", "CLERK12A", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-103-005", "SEG103-R-010", "Voucher ID is numeric max 10",
                "field-format", "Voucher ID contains non-numeric characters",
                "EBT Data Segment.VoucherId", "0000098765", "VOUCH1XY", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-103-006", "SEG103-R-011", "WIC Discount Amount positional format",
                "field-format", "WIC Discount Amount block does not match Account Type/Amount Type prefix",
                "EBT Data Segment.WicDiscountAmount", "9752840D000000000500", "00000000000000000000", true, MutationCase.Kind.FIELD_SET
            ),
            new MutationCase(
                "MUT-103-007", "SEG103-R-013", "WIC Product Data Total Length bound",
                "structural", "WIC Product Data totalLength mismatched against actual data length",
                "EBT Data Segment.WicProductData.totalLength", "(existing or absent)", "0999", true,
                MutationCase.Kind.WIC_PRODUCT_TOTAL_LENGTH_MISMATCH
            ),
            new MutationCase(
                "MUT-103-008", "SEG103-R-016", "EBT Program Data TAG enumeration",
                "structural", "EBT Program Data subelement TAG set to an undocumented value",
                "EBT Data Segment.EbtProgramData.subelements[0].tag", "(existing or absent)", "99", true,
                MutationCase.Kind.EBT_PROGRAM_TAG_INVALID
            ),
            new MutationCase(
                "MUT-103-009", "SEG103-R-017", "TAG 50 requires ACCOUNT TYPE 98",
                "structural", "EBT Program Data subelement TAG 50 missing ACCOUNT TYPE 98",
                "EBT Data Segment.EbtProgramData.subelements[0].accountType", "98", "(removed)", true,
                MutationCase.Kind.EBT_PROGRAM_ACCOUNT_TYPE_MISSING
            ),
            new MutationCase(
                "MUT-103-010", "SEG103-R-018", "Segment 103 appears exactly once",
                "structural", "Duplicate EBT Data Segment (103) added alongside the original",
                "EBT Data Segment 2", "(absent)", "{\"SegmentType\":\"103\",\"SegmentLength\":\"018\"}",
                true, MutationCase.Kind.SEGMENT_DUPLICATE
            )
        );
    }

    public static MutationResult testMutation(MutationCase mutation, JsonNode basePayload) {
        Objects.requireNonNull(basePayload, "basePayload");
        ObjectNode mutated = applyMutation(basePayload.deepCopy(), mutation);
        ValidationResult result = new Segment103PayloadValidator().validatePayload(mutated);
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
        String sessionId = "seg103-mutation-session-" + System.currentTimeMillis();
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
            case WIC_PRODUCT_TOTAL_LENGTH_MISMATCH -> {
                ObjectNode segment = (ObjectNode) request.path("EBT Data Segment");
                if (!segment.isObject()) {
                    return root;
                }
                ObjectNode wicProductData = objectField(segment, "WicProductData");
                wicProductData.put("totalLength", mutation.mutatedValue);
            }
            case EBT_PROGRAM_TAG_INVALID -> {
                ObjectNode segment = (ObjectNode) request.path("EBT Data Segment");
                if (!segment.isObject()) {
                    return root;
                }
                ObjectNode ebtProgramData = objectField(segment, "EbtProgramData");
                if (!ebtProgramData.has("totalLength")) {
                    ebtProgramData.put("totalLength", "044");
                }
                ArrayNode subelements = ebtProgramData.withArray("subelements");
                ObjectNode subelement = subelements.size() > 0
                    ? (ObjectNode) subelements.get(0)
                    : subelements.addObject();
                subelement.put("tag", mutation.mutatedValue);
            }
            case EBT_PROGRAM_ACCOUNT_TYPE_MISSING -> {
                ObjectNode segment = (ObjectNode) request.path("EBT Data Segment");
                if (!segment.isObject()) {
                    return root;
                }
                ObjectNode ebtProgramData = objectField(segment, "EbtProgramData");
                if (!ebtProgramData.has("totalLength")) {
                    ebtProgramData.put("totalLength", "044");
                }
                ArrayNode subelements = ebtProgramData.withArray("subelements");
                ObjectNode subelement = subelements.size() > 0
                    ? (ObjectNode) subelements.get(0)
                    : subelements.addObject();
                subelement.put("tag", "50");
                subelement.remove("accountType");
            }
            case SEGMENT_DUPLICATE -> {
                ObjectNode duplicate = request.putObject("EBT Data Segment 2");
                duplicate.put("SegmentType", "103");
                duplicate.put("SegmentLength", "018");
            }
        }
        return root;
    }

    private static ObjectNode objectField(ObjectNode parent, String field) {
        JsonNode existing = parent.get(field);
        return existing instanceof ObjectNode object ? object : parent.putObject(field);
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
