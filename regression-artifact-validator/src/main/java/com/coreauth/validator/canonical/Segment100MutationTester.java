package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

/**
 * Executable mutation testing for Segment 100 rules.
 *
 * Mutation testing verifies that validators catch intentional rule violations.
 * Each mutation case represents a broken rule; the test confirms the validator detects it.
 *
 * Mutation categories:
 * - Field value mutations: change required field to invalid value
 * - Field omission mutations: remove required non-empty field
 * - Field format mutations: use wrong format (e.g., 5 digits instead of 6)
 * - Structural mutations: change segment count, remove segments, reorder
 */
public final class Segment100MutationTester {

    public record MutationCase(
        String mutationId,
        String ruleId,
        String ruleTitle,
        String mutationType,
        String description,
        String fieldPath,
        String originalValue,
        String mutatedValue,
        boolean shouldDetect
    ) {}

    public record MutationResult(
        MutationCase mutation,
        boolean detected,
        String detectionReason
    ) {}

    public record MutationReport(
        String testSessionId,
        List<MutationResult> results,
        MutationMetrics metrics
    ) {}

    public record MutationMetrics(
        int totalMutations,
        int detectedMutations,
        int missedMutations,
        double detectionRate,
        List<String> undetectedMutations
    ) {}

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Generates standard mutation cases for Segment 100 rules.
     */
    public static List<MutationCase> generateStandardMutations() {
        List<MutationCase> mutations = new ArrayList<>();

        // SEG100-R-015: Segment Type is 100
        mutations.add(new MutationCase(
            "MUT-001", "SEG100-R-015", "Segment Type is 100",
            "field-value", "Change segment type from 100 to 101",
            "dataSection2.standardSegment.segmentType", "100", "101", true
        ));

        // SEG100-R-019: Sequence Number is six digits
        mutations.add(new MutationCase(
            "MUT-002", "SEG100-R-019", "Sequence Number is six digits",
            "field-format", "Change sequence number from 6 digits to 5",
            "dataSection2.standardSegment.sequenceNumber", "100001", "10001", true
        ));

        mutations.add(new MutationCase(
            "MUT-003", "SEG100-R-019", "Sequence Number is six digits",
            "field-format", "Change sequence number to non-numeric",
            "dataSection2.standardSegment.sequenceNumber", "100001", "ABCDEF", true
        ));

        // SEG100-R-013: Message format identifier is ATL105
        mutations.add(new MutationCase(
            "MUT-004", "SEG100-R-013", "Message format identifier is ATL105",
            "field-value", "Change message format from ATL105 to ATL104",
            "dataSection1.messageFormatVersionIdentifier", "ATL105", "ATL104", true
        ));

        // SEG100-R-017: Terminal Identifier has valid format
        mutations.add(new MutationCase(
            "MUT-005", "SEG100-R-017", "Terminal Identifier has valid format",
            "field-omission", "Remove terminal identifier",
            "dataSection2.standardSegment.terminalIdentifier", "TERM123", "", true
        ));

        mutations.add(new MutationCase(
            "MUT-006", "SEG100-R-017", "Terminal Identifier has valid format",
            "field-format", "Invalid terminal identifier with special chars",
            "dataSection2.standardSegment.terminalIdentifier", "TERM123", "TERM@#$", true
        ));

        // SEG100-R-018: Prompt Code represents transaction and card context
        mutations.add(new MutationCase(
            "MUT-007", "SEG100-R-018", "Prompt Code represents transaction and card context",
            "field-format", "Prompt code too long (5 chars instead of 3-4)",
            "dataSection2.standardSegment.promptCode", "POS", "POSXX", true
        ));

        // SEG100-R-020: Partial Approval Indicator uses allowed value
        mutations.add(new MutationCase(
            "MUT-008", "SEG100-R-020", "Partial Approval Indicator uses allowed value",
            "field-value", "Invalid partial approval indicator value",
            "dataSection2.standardSegment.partialApprovalIndicator", "0", "9", true
        ));

        // SEG100-R-007: Element 63 (numberOfSegments) equals serialized segment count
        mutations.add(new MutationCase(
            "MUT-009", "SEG100-R-007", "Element 63 equals serialized segment count",
            "field-value", "Mismatch: declare 2 segments but have 3",
            "dataSection1.numberOfSegments", "03", "02", true
        ));

        // SEG100-R-014: Data Section 1 separators are present
        mutations.add(new MutationCase(
            "MUT-010", "SEG100-R-014", "Data Section 1 separators are present",
            "field-omission", "Remove required messageFormatVersionIdentifier",
            "dataSection1.messageFormatVersionIdentifier", "ATL105", null, true
        ));

        return mutations;
    }

    /**
     * Applies a mutation to a test data payload and validates the result.
     */
    public static MutationResult testMutation(MutationCase mutation, CanonicalArtifactPackage basePackage) {
        // Create mutated test data
        CanonicalTestData mutatedData = createMutatedTestData(basePackage, mutation);

        // Run validator on mutated data
        ValidationResult validationResult = new Segment100PayloadValidator().validate(createPackageWithTestData(mutatedData));

        // Check if error was detected
        boolean detected = !validationResult.errors().isEmpty();
        String detectionReason = detected
            ? String.join("; ", validationResult.errors().stream().map(e -> e.reason()).toList())
            : "No validation errors detected";

        return new MutationResult(mutation, detected, detectionReason);
    }

    /**
     * Runs a full mutation test suite against a package.
     */
    public static MutationReport runMutationSuite(CanonicalArtifactPackage basePackage, List<MutationCase> mutations) {
        String sessionId = "mutation-session-" + System.currentTimeMillis();
        List<MutationResult> results = new ArrayList<>();

        for (MutationCase mutation : mutations) {
            MutationResult result = testMutation(mutation, basePackage);
            results.add(result);
        }

        MutationMetrics metrics = computeMetrics(results);
        return new MutationReport(sessionId, results, metrics);
    }

    private static MutationMetrics computeMetrics(List<MutationResult> results) {
        long detected = results.stream().filter(r -> r.detected).count();
        long missed = results.stream().filter(r -> !r.detected).count();
        double detectionRate = results.isEmpty() ? 0 : (detected * 100.0) / results.size();

        List<String> undetected = results.stream()
            .filter(r -> !r.detected)
            .map(r -> r.mutation.mutationId + " (" + r.mutation.ruleId + ")")
            .toList();

        return new MutationMetrics(
            results.size(),
            (int) detected,
            (int) missed,
            detectionRate,
            undetected
        );
    }

    private static CanonicalTestData createMutatedTestData(CanonicalArtifactPackage basePackage, MutationCase mutation) {
        if (basePackage == null || basePackage.getTestData() == null || basePackage.getTestData().isEmpty()) {
            return createDefaultMutatedTestData(mutation);
        }

        CanonicalTestData baseTD = basePackage.getTestData().get(0);
        CanonicalTestData mutated = new CanonicalTestData();
        mutated.setId("MUTATED-" + mutation.mutationId);
        mutated.setTestCaseIds(baseTD.getTestCaseIds());
        mutated.setSourceAnchors(baseTD.getSourceAnchors());
        mutated.setExpectedValidation("FAIL"); // Mutation should fail

        ObjectNode mutatedPayload = mutatePayload(baseTD.getPayload(), mutation);
        mutated.setPayload(mutatedPayload);

        return mutated;
    }

    private static CanonicalTestData createDefaultMutatedTestData(MutationCase mutation) {
        CanonicalTestData data = new CanonicalTestData();
        data.setId("MUTATED-" + mutation.mutationId);
        data.setExpectedValidation("FAIL");

        ObjectNode payload = createMutatedPayload(mutation);
        data.setPayload(payload);

        return data;
    }

    private static ObjectNode mutatePayload(JsonNode original, MutationCase mutation) {
        ObjectNode mutated = mapper.createObjectNode();

        // First, preserve original payload structure
        if (original != null && original.isObject()) {
            original.fields().forEachRemaining(entry -> {
                mutated.set(entry.getKey(), mapper.convertValue(entry.getValue(), JsonNode.class));
            });
        }

        // Ensure Segment 100 core structure exists
        ensureSegment100Structure(mutated);

        // Then apply the mutation
        applyMutation(mutated, mutation);
        return mutated;
    }

    private static void ensureSegment100Structure(ObjectNode payload) {
        // Ensure request exists
        if (!payload.has("request")) {
            payload.set("request", mapper.createObjectNode());
        }
        ObjectNode request = (ObjectNode) payload.get("request");

        // Ensure dataSection1 exists with defaults
        if (!request.has("dataSection1")) {
            ObjectNode ds1 = mapper.createObjectNode();
            ds1.put("messageFormatVersionIdentifier", "ATL105");
            ds1.put("numberOfSegments", "01"); // 1 segment
            request.set("dataSection1", ds1);
        } else {
            ObjectNode ds1 = (ObjectNode) request.get("dataSection1");
            if (!ds1.has("messageFormatVersionIdentifier")) {
                ds1.put("messageFormatVersionIdentifier", "ATL105");
            }
            if (!ds1.has("numberOfSegments")) {
                ds1.put("numberOfSegments", "01"); // 1 segment
            }
        }

        // Ensure dataSection2 with standardSegment exists
        if (!request.has("dataSection2")) {
            ObjectNode ds2 = mapper.createObjectNode();
            ObjectNode segment = mapper.createObjectNode();
            segment.put("segmentType", "100");
            segment.put("terminalIdentifier", "TERM123");
            segment.put("promptCode", "POS");
            segment.put("sequenceNumber", "100001");
            segment.put("partialApprovalIndicator", "0");
            ds2.set("standardSegment", segment);
            request.set("dataSection2", ds2);
        } else {
            ObjectNode ds2 = (ObjectNode) request.get("dataSection2");
            if (!ds2.has("standardSegment")) {
                ObjectNode segment = mapper.createObjectNode();
                segment.put("segmentType", "100");
                segment.put("terminalIdentifier", "TERM123");
                segment.put("promptCode", "POS");
                segment.put("sequenceNumber", "100001");
                segment.put("partialApprovalIndicator", "0");
                ds2.set("standardSegment", segment);
            } else {
                ObjectNode segment = (ObjectNode) ds2.get("standardSegment");
                if (!segment.has("segmentType")) segment.put("segmentType", "100");
                if (!segment.has("terminalIdentifier")) segment.put("terminalIdentifier", "TERM123");
                if (!segment.has("promptCode")) segment.put("promptCode", "POS");
                if (!segment.has("sequenceNumber")) segment.put("sequenceNumber", "100001");
                if (!segment.has("partialApprovalIndicator")) segment.put("partialApprovalIndicator", "0");
            }
        }

        // Ensure testControls exists with validateCoreFields
        if (!payload.has("testControls")) {
            ObjectNode controls = mapper.createObjectNode();
            controls.put("validateCoreFields", true);
            payload.set("testControls", controls);
        } else {
            ObjectNode controls = (ObjectNode) payload.get("testControls");
            if (!controls.has("validateCoreFields")) {
                controls.put("validateCoreFields", true);
            }
        }
    }

    private static ObjectNode createMutatedPayload(MutationCase mutation) {
        ObjectNode payload = mapper.createObjectNode();
        ObjectNode request = mapper.createObjectNode();
        ObjectNode dataSection1 = mapper.createObjectNode();
        ObjectNode dataSection2 = mapper.createObjectNode();
        ObjectNode segment = mapper.createObjectNode();

        // Set defaults
        dataSection1.put("messageFormatVersionIdentifier", "ATL105");
        dataSection1.put("numberOfSegments", "01"); // 1 segment in dataSection2
        segment.put("segmentType", "100");
        segment.put("terminalIdentifier", "TERM123");
        segment.put("promptCode", "POS");
        segment.put("sequenceNumber", "100001");
        segment.put("partialApprovalIndicator", "0");
        dataSection2.set("standardSegment", segment);

        request.set("dataSection1", dataSection1);
        request.set("dataSection2", dataSection2);
        payload.set("request", request);

        // Add test controls
        ObjectNode controls = mapper.createObjectNode();
        controls.put("validateCoreFields", true);
        payload.set("testControls", controls);

        // Apply the mutation
        applyMutation(payload, mutation);

        return payload;
    }

    private static void applyMutation(ObjectNode payload, MutationCase mutation) {
        String[] pathParts = mutation.fieldPath.split("\\.");
        
        // Start from request node (mutations are within request structure)
        if (!payload.has("request")) {
            payload.set("request", mapper.createObjectNode());
        }
        ObjectNode current = (ObjectNode) payload.get("request");

        // Navigate to parent
        for (int i = 0; i < pathParts.length - 1; i++) {
            if (!current.has(pathParts[i])) {
                current.set(pathParts[i], mapper.createObjectNode());
            }
            current = (ObjectNode) current.get(pathParts[i]);
        }

        // Apply mutation
        String fieldName = pathParts[pathParts.length - 1];
        if (mutation.mutatedValue == null || mutation.mutatedValue.isEmpty()) {
            current.remove(fieldName);
        } else {
            current.put(fieldName, mutation.mutatedValue);
        }
    }

    private static CanonicalArtifactPackage createPackageWithTestData(CanonicalTestData testData) {
        CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
        pkg.setTestData(List.of(testData));
        return pkg;
    }
}
