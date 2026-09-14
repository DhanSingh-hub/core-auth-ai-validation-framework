package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.regex.Pattern;

/** Validates Segment 100 identity and Segment Length representation. */
public final class Segment100IdentityValidator {
    private static final Pattern SEGMENT_LENGTH = Pattern.compile("^[0-9]{3,4}$");

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String packageId = artifactPackage == null || artifactPackage.getManifest() == null
                ? "segment-100-identity" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(packageId == null ? "segment-100-identity" : packageId);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("Segment100Identity", "test data is required");
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
        if (!controls.path("validateSegment100Identity").asBoolean(false)) {
            return;
        }
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        JsonNode segment = data.getPayload().path("request").path("dataSection2").path("standardSegment");
        String type = segment.path("segmentType").asText(null);
        if (!"100".equals(type)) {
            result.addError("Segment100Identity", id + " Segment Type must be '100', got: " + type);
        }
        String length = segment.path("segmentLength").asText(null);
        if (length == null || !("CALCULATE_FROM_SEGMENT_CONTENT".equals(length)
                || SEGMENT_LENGTH.matcher(length).matches())) {
            result.addError("Segment100Identity", id
                    + " Segment Length must be calculated or contain 3-4 digits, got: " + length);
        }
    }
}