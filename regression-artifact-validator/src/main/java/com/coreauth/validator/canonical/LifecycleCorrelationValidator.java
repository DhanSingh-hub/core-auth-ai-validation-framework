package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

/** Validates explicit original/follow-up lifecycle message correlation evidence. */
public final class LifecycleCorrelationValidator {
    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String id = artifactPackage == null || artifactPackage.getManifest() == null
                ? "lifecycle-correlation" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(id == null ? "lifecycle-correlation" : id);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("LifecycleCorrelation", "test data is required");
            return result;
        }
        for (CanonicalTestData data : artifactPackage.getTestData()) validate(data, result);
        return result;
    }

    private void validate(CanonicalTestData data, ValidationResult result) {
        if (data == null || data.getPayload() == null) return;
        JsonNode controls = data.getPayload().path("testControls");
        if (!controls.path("validateLifecycleCorrelation").asBoolean(false)) return;
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        JsonNode original = data.getPayload().path("lifecycleMessages").path("original");
        JsonNode followUp = data.getPayload().path("lifecycleMessages").path("followUp");
        String originalSequence = original.path("sequenceNumber").asText(null);
        String followUpSequence = followUp.path("sequenceNumber").asText(null);
        if (originalSequence == null || !originalSequence.matches("^[0-9]{6}$")) {
            result.addError("LifecycleCorrelation", id + " original Sequence Number must be six digits");
        }
        if (followUpSequence == null || !followUpSequence.matches("^[0-9]{6}$")) {
            result.addError("LifecycleCorrelation", id + " follow-up Sequence Number must be six digits");
        }
        if (originalSequence != null && !originalSequence.equals(followUpSequence)) {
            result.addError("LifecycleCorrelation", id + " follow-up must reuse original Sequence Number");
        }
        if (controls.path("requiresApprovalNumber").asBoolean(false)
                && followUp.path("approvalNumber").asText("").isEmpty()) {
            result.addError("LifecycleCorrelation", id + " follow-up requires Approval Number");
        }
    }
}