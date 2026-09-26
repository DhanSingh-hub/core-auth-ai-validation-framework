package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

/** Validates ATL105 Element 55 in Data Section No. 1. */
public final class MessageFormatVersionValidator {

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String packageId = artifactPackage == null || artifactPackage.getManifest() == null
                ? "message-format-version" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(packageId == null ? "message-format-version" : packageId);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("MessageFormatVersion", "test data is required");
            return result;
        }
        for (CanonicalTestData data : artifactPackage.getTestData()) {
            validateTestData(data, result);
        }
        return result;
    }

    private void validateTestData(CanonicalTestData data, ValidationResult result) {
        if (data == null || data.getPayload() == null) {
            return;
        }
        JsonNode controls = data.getPayload().path("testControls");
        if (!controls.path("validateMessageFormatVersion").asBoolean(false)) {
            return;
        }
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        String actual = data.getPayload().path("request").path("dataSection1")
                .path("messageFormatVersionIdentifier").asText(null);
        if (!"ATL105".equals(actual)) {
            result.addError("MessageFormatVersion", id
                    + " Element 55 must be 'ATL105', got: " + actual);
        }
    }
}