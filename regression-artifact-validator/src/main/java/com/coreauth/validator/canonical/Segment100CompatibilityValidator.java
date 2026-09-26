package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Validates Segment 100 companion-segment expectations encoded in canonical test data. */
public final class Segment100CompatibilityValidator {

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        ValidationResult result = new ValidationResult(packageId(artifactPackage));
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("Segment100Compatibility", "test data is required");
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
        JsonNode payload = data.getPayload();
        JsonNode request = payload.path("request");
        JsonNode controls = payload.path("testControls");
        String id = data.getId() == null ? "unknown-test-data" : data.getId();

        List<String> actualSegments = actualSegments(request, controls);
        validateSegment100Cardinality(controls, actualSegments, id, result);
        List<String> expectedSegments = strings(controls.path("expectedSegments"));
        List<String> requiredSegments = strings(controls.path("requiredSegments"));

        if (!expectedSegments.isEmpty() && !expectedSegments.equals(actualSegments)) {
            result.addError("Segment100Compatibility", id + " expected segments "
                    + expectedSegments + " but request contains " + actualSegments);
        }
        for (String required : requiredSegments) {
            if (!actualSegments.contains(required)) {
                result.addError("Segment100Compatibility", id + " is missing required segment: " + required);
            }
        }

        int declaredCount = request.path("dataSection1").path("numberOfSegments").asInt(-1);
        if (declaredCount >= 0 && declaredCount != actualSegments.size()) {
            result.addError("Segment100Compatibility", id + " declares " + declaredCount
                    + " segments but contains " + actualSegments.size());
        }

        Set<String> seen = new HashSet<>();
        for (String segment : actualSegments) {
            if (!seen.add(segment)) {
                result.addError("Segment100Compatibility", id + " contains duplicate segment: " + segment);
            }
        }

        List<String> expectedOrder = strings(controls.path("expectedOrder"));
        if (!expectedOrder.isEmpty() && !expectedOrder.equals(actualSegments)) {
            result.addError("Segment100Compatibility", id + " segment order is " + actualSegments
                    + "; expected " + expectedOrder);
        }

        String compatibilityStatus = controls.path("compatibilityStatus").asText("");
        if ("UNKNOWN_REQUIRES_REVIEW".equalsIgnoreCase(compatibilityStatus)) {
            result.addWarning(id + " has an unknown segment combination and requires review");
        }
        if ("REVIEW".equalsIgnoreCase(data.getExpectedValidation())
                || "PASS_WITH_REVIEW".equalsIgnoreCase(data.getExpectedValidation())) {
            result.addWarning(id + " expected outcome includes review");
        }
    }

    private static void validateSegment100Cardinality(JsonNode controls, List<String> actualSegments,
                                                      String id, ValidationResult result) {
        String category = controls.path("messageCategory")
                .asText("STANDARD_FINANCIAL_REQUEST");
        if (!"STANDARD_FINANCIAL_REQUEST".equalsIgnoreCase(category)) {
            result.addWarning(id + " Segment 100 cardinality delegated to category-specific rules for message category: " + category);
            return;
        }
        long count = actualSegments.stream().filter("100"::equals).count();
        if (count == 0) {
            result.addError("Segment100Compatibility", id
                    + " standard financial request must contain exactly one Segment 100; found none");
        } else if (count > 1) {
            result.addError("Segment100Compatibility", id
                    + " standard financial request must contain exactly one Segment 100; found " + count);
        }
    }

    private static List<String> actualSegments(JsonNode request, JsonNode controls) {
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
        if (!segments.isEmpty()) {
            return segments;
        }
        return strings(controls.path("actualSegments"));
    }

    private static List<String> strings(JsonNode node) {
        if (node == null || !node.isArray()) {
            return Collections.emptyList();
        }
        List<String> values = new ArrayList<>();
        for (JsonNode value : node) {
            values.add(value.asText());
        }
        return values;
    }

    private static String packageId(CanonicalArtifactPackage artifactPackage) {
        if (artifactPackage == null || artifactPackage.getManifest() == null
                || artifactPackage.getManifest().getPackageId() == null) {
            return "segment-100-compatibility";
        }
        return artifactPackage.getManifest().getPackageId();
    }
}