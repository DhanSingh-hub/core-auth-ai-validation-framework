package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/** Validates Data Section No. 1 Element 63 and its required field separators. */
public final class DataSection1StructureValidator {

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String packageId = artifactPackage == null || artifactPackage.getManifest() == null
                ? "data-section-1" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(packageId == null ? "data-section-1" : packageId);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("DataSection1", "test data is required");
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
        if (!controls.path("validateDataSection1").asBoolean(false)) {
            return;
        }
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        JsonNode request = data.getPayload().path("request");
        JsonNode section1 = request.path("dataSection1");
        String declaredText = section1.path("numberOfSegments").asText(null);
        if (declaredText == null || !declaredText.matches("^[0-9]{2}$")) {
            result.addError("DataSection1", id + " Element 63 must be two digits");
        } else {
            int declared = Integer.parseInt(declaredText);
            int actual = actualSegmentCount(request);
            if (declared != actual) {
                result.addError("DataSection1", id + " Element 63 declares " + declared
                        + " segments but request contains " + actual);
            }
        }

        JsonNode separators = section1.path("fieldSeparators");
        if (!separators.path("betweenElements55And63").asBoolean(false)) {
            result.addError("DataSection1", id + " is missing the separator between Elements 55 and 63");
        }
        if (!separators.path("afterElement63").asBoolean(false)) {
            result.addError("DataSection1", id + " is missing the separator after Element 63");
        }
    }

    private static int actualSegmentCount(JsonNode request) {
        List<String> segments = new ArrayList<>();
        if (request.path("dataSection2").path("standardSegment").isObject()) {
            segments.add(request.path("dataSection2").path("standardSegment")
                    .path("segmentType").asText("100"));
        }
        JsonNode section3 = request.path("dataSection3");
        if (section3.isArray()) {
            for (JsonNode segment : section3) {
                if (segment.isObject()) {
                    segments.add(segment.path("segmentType").asText(""));
                }
            }
        }
        return segments.size();
    }
}