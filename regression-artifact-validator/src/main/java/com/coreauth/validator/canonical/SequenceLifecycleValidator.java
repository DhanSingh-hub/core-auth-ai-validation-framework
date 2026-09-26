package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

/** Validates Sequence Number format and declared lifecycle correlation. */
public final class SequenceLifecycleValidator {
    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String id = artifactPackage == null || artifactPackage.getManifest() == null
                ? "sequence-lifecycle" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(id == null ? "sequence-lifecycle" : id);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("SequenceLifecycle", "test data is required");
            return result;
        }
        Map<String, String> sequences = new HashMap<>();
        for (CanonicalTestData data : artifactPackage.getTestData()) validate(data, sequences, result);
        return result;
    }

    private void validate(CanonicalTestData data, Map<String, String> sequences, ValidationResult result) {
        if (data == null || data.getPayload() == null) return;
        JsonNode controls = data.getPayload().path("testControls");
        if (!controls.path("validateSequenceLifecycle").asBoolean(false)) return;
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        JsonNode segment = data.getPayload().path("request").path("dataSection2").path("standardSegment");
        String sequence = segment.path("sequenceNumber").asText(null);
        if (sequence == null || !sequence.matches("^[0-9]{6}$")) {
            result.addError("SequenceLifecycle", id + " Sequence Number must be six digits");
        }
        String lifecycleKey = controls.path("lifecycleKey").asText(null);
        String expectedOriginalSequence = controls.path("expectedOriginalSequence").asText(null);
        if (expectedOriginalSequence != null && !expectedOriginalSequence.equals(sequence)) {
            result.addError("SequenceLifecycle", id + " must reuse original Sequence Number " + expectedOriginalSequence);
        }
        if (lifecycleKey != null && sequence != null) {
            String previous = sequences.putIfAbsent(lifecycleKey, sequence);
            if (previous != null && !previous.equals(sequence)) {
                result.addError("SequenceLifecycle", id + " Sequence Number " + sequence
                        + " does not match lifecycle original " + previous);
            }
        }
        if (controls.path("requiresApprovalNumber").asBoolean(false)
                && segment.path("approvalNumber").asText("").isEmpty()) {
            result.addError("SequenceLifecycle", id + " requires Approval Number for this lifecycle flow");
        }
    }
}