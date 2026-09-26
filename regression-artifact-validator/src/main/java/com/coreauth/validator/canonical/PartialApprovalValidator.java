package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

/** Validates Partial Approval Indicator values and declared card-flow dependencies. */
public final class PartialApprovalValidator {
    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String id = artifactPackage == null || artifactPackage.getManifest() == null
                ? "partial-approval" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(id == null ? "partial-approval" : id);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("PartialApproval", "test data is required");
            return result;
        }
        for (CanonicalTestData data : artifactPackage.getTestData()) validate(data, result);
        return result;
    }

    private void validate(CanonicalTestData data, ValidationResult result) {
        if (data == null || data.getPayload() == null) return;
        JsonNode controls = data.getPayload().path("testControls");
        if (!controls.path("validatePartialApproval").asBoolean(false)) return;
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        JsonNode segment = data.getPayload().path("request").path("dataSection2").path("standardSegment");
        String value = segment.path("partialApprovalIndicator").asText(null);
        if (!("0".equals(value) || "1".equals(value) || "5".equals(value))) {
            result.addError("PartialApproval", id + " Partial Approval Indicator must be 0, 1, or 5");
            return;
        }
        if (controls.path("cardPresent").asBoolean(false) && value == null) {
            result.addError("PartialApproval", id + " card-present flow requires Partial Approval Indicator");
        }
        if ("5".equals(value) && !controls.path("amexPrepaidBalanceReceipt").asBoolean(false)) {
            result.addError("PartialApproval", id + " value 5 requires Amex prepaid balance-receipt context");
        }
        String expected = controls.path("expectedIndicator").asText(null);
        if (expected != null && !expected.equals(value)) {
            result.addError("PartialApproval", id + " indicator is " + value + "; expected " + expected);
        }
    }
}