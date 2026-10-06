package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/** Validates Data Section No. 1 Element 63 and its required field separators. */
public final class DataSection1StructureValidator {

    // Element 63 is "variable length of up to two digits" (ATL105 Ch.13); zero-padding is optional.
    static final String ELEMENT_63_PATTERN = "^[0-9]{1,2}$";
    // Segment 100 + Data Section 3 Field Nos. 4-9 (standard, §11.1.1) or 4-8 (EMV, §11.8.1).
    static final int MAX_SEGMENTS_STANDARD = 7;
    static final int MAX_SEGMENTS_EMV = 6;

    /** Returns a range violation for a syntactically valid Element 63 value, or null when in range. */
    static String element63RangeError(int declared, boolean emv) {
        int max = emv ? MAX_SEGMENTS_EMV : MAX_SEGMENTS_STANDARD;
        if (declared < 1 || declared > max) {
            return "Element 63 declares " + declared + " but must be 1-" + max
                    + (emv ? " for an EMV request" : " for a standard request");
        }
        return null;
    }

    static boolean isEmv(JsonNode request) {
        for (JsonNode segment : request.path("dataSection3")) {
            if ("130".equals(segment.path("segmentType").asText(null))) {
                return true;
            }
        }
        return false;
    }

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
        if (declaredText == null || !declaredText.matches(ELEMENT_63_PATTERN)) {
            result.addError("DataSection1", id + " Element 63 must be one or two digits");
        } else {
            int declared = Integer.parseInt(declaredText);
            int actual = actualSegmentCount(request);
            String rangeError = element63RangeError(declared, isEmv(request));
            if (rangeError != null) {
                result.addError("DataSection1", id + " " + rangeError);
            } else if (declared != actual) {
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