package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.regex.Pattern;

/** Validates the structured core fields of an opted-in Segment 100 request payload. */
public final class Segment100PayloadValidator {
    private static final Pattern ALPHANUMERIC = Pattern.compile("^[A-Za-z0-9]+$");
    private static final Pattern SIX_DIGITS = Pattern.compile("^[0-9]{6}$");
    private static final Pattern PROMPT_CODE = Pattern.compile("^[A-Za-z0-9]{3,4}$");

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String packageId = artifactPackage == null || artifactPackage.getManifest() == null
                ? "segment-100-payload" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(packageId == null ? "segment-100-payload" : packageId);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("Segment100Payload", "test data is required");
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
        JsonNode controls = payload.path("testControls");
        if (!controls.path("validateCoreFields").asBoolean(false)) {
            return;
        }
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        JsonNode request = payload.path("request");
        JsonNode section1 = request.path("dataSection1");
        JsonNode segment = request.path("dataSection2").path("standardSegment");

        requireObject(request, "request", id, result);
        requireObject(segment, "request.dataSection2.standardSegment", id, result);
        if (!segment.isObject()) {
            return;
        }

        checkEquals(segment, "segmentType", "100", id, result);
        checkPattern(segment, "terminalIdentifier", ALPHANUMERIC, "1-22 alphanumeric characters", id, result);
        checkPattern(segment, "promptCode", PROMPT_CODE, "3-4 alphanumeric characters", id, result);
        checkPattern(segment, "sequenceNumber", SIX_DIGITS, "6 digits", id, result);
        checkEnum(segment, "partialApprovalIndicator", new String[]{"0", "1", "5"}, id, result);

        // MUT-010: messageFormatVersionIdentifier is required
        checkEquals(section1, "messageFormatVersionIdentifier", "ATL105", id, result);
        
        // MUT-009: numberOfSegments must be present and valid
        String declaredSegments = section1.path("numberOfSegments").asText(null);
        String rangeError = declaredSegments != null
                && declaredSegments.matches(DataSection1StructureValidator.ELEMENT_63_PATTERN)
                ? DataSection1StructureValidator.element63RangeError(Integer.parseInt(declaredSegments),
                        DataSection1StructureValidator.isEmv(request))
                : null;
        if (declaredSegments == null || declaredSegments.isEmpty()) {
            result.addError("Segment100Payload", id + " numberOfSegments is required");
        } else if (!declaredSegments.matches(DataSection1StructureValidator.ELEMENT_63_PATTERN)) {
            result.addError("Segment100Payload", id + " numberOfSegments must be one or two digits");
        } else if (rangeError != null) {
            result.addError("Segment100Payload", id + " numberOfSegments '" + declaredSegments + "': " + rangeError);
        } else {
            // Count actual segments in dataSection2
            JsonNode dataSection2 = request.path("dataSection2");
            int actualCount = 0;
            if (dataSection2.isObject()) {
                actualCount = dataSection2.size(); // Count all fields/segments
            }
            
            int declared = Integer.parseInt(declaredSegments);
            if (declared != actualCount) {
                result.addError("Segment100Payload", id + " numberOfSegments '" + declaredSegments + "' does not match actual segment count: " + actualCount);
            }
        }
    }

    private static void requireObject(JsonNode node, String path, String id, ValidationResult result) {
        if (node == null || !node.isObject()) {
            result.addError("Segment100Payload", id + " " + path + " must be an object");
        }
    }

    private static void checkEquals(JsonNode node, String field, String expected, String id, ValidationResult result) {
        String actual = node.path(field).asText(null);
        if (!expected.equals(actual)) {
            result.addError("Segment100Payload", id + " " + field + " must be '" + expected + "', got: " + actual);
        }
    }

    private static void checkPattern(JsonNode node, String field, Pattern pattern, String description,
                                     String id, ValidationResult result) {
        String actual = node.path(field).asText(null);
        if (actual == null || !pattern.matcher(actual).matches()
                || ("terminalIdentifier".equals(field) && actual.length() > 22)) {
            result.addError("Segment100Payload", id + " " + field + " must contain " + description);
        }
    }

    private static void checkEnum(JsonNode node, String field, String[] allowed, String id, ValidationResult result) {
        String actual = node.path(field).asText(null);
        for (String value : allowed) {
            if (value.equals(actual)) {
                return;
            }
        }
        result.addError("Segment100Payload", id + " " + field + " has invalid value: " + actual);
    }
}