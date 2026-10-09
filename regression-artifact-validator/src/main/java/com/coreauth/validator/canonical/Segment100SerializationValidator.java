package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/** Validates the structured representation of Segment 100 serialization rules. */
public final class Segment100SerializationValidator {
    private static final Pattern LENGTH = Pattern.compile("^[0-9]{3,4}$");
    private static final Pattern SEGMENT_LENGTH = Pattern.compile("^[0-9]{3}$");
    /** ATL105 Chapter 13.2 Element 84: Segment 100's Segment Length ranges 001-218. */
    private static final int SEGMENT_100_MIN_LENGTH = 1;
    private static final int SEGMENT_100_MAX_LENGTH = 218;
    private static final List<String> SEGMENT_100_ORDER = List.of(
            "segmentType", "segmentLength", "informationByte", "terminalIdentifier", "promptCode",
            "accountNumber", "cardDiscretionaryBlockData", "encryptedPinBlockData", "pumpLaneNumber",
            "fuelPurchaseAmount", "nonfuelAmount", "taxAmount", "cashAmount", "sequenceNumber",
            "approvalNumber", "localDateTime", "partialApprovalIndicator");

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String packageId = artifactPackage == null || artifactPackage.getManifest() == null
                ? "segment-100-serialization" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(packageId == null ? "segment-100-serialization" : packageId);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("Segment100Serialization", "test data is required");
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
        if (!controls.path("validateSerialization").asBoolean(false)) {
            return;
        }
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        JsonNode request = payload.path("request");
        JsonNode header = request.path("tcpIpHeader");
        JsonNode section1 = request.path("dataSection1");
        JsonNode segment = request.path("dataSection2").path("standardSegment");

        checkEquals(header, "byteOrder", "network-big-endian", id, "tcpIpHeader", result);
        checkEquals(header, "tpduProtocolId", "60", id, "tcpIpHeader", result);
        checkEquals(header, "tpduDestinationAddress", "0000", id, "tcpIpHeader", result);
        checkEquals(header, "tpduSourceAddress", "0000", id, "tcpIpHeader", result);
        String messageLength = header.path("messageLength").asText(null);
        if (messageLength == null || !("CALCULATE_FROM_FOLLOWING_PAYLOAD".equals(messageLength)
                || LENGTH.matcher(messageLength).matches())) {
            result.addError("Segment100Serialization", id + " tcpIpHeader.messageLength is invalid");
        }

        checkEquals(section1.path("fieldSeparators"), "betweenElements55And63", "true", id,
                "dataSection1.fieldSeparators", result);
        checkEquals(section1.path("fieldSeparators"), "afterElement63", "true", id,
                "dataSection1.fieldSeparators", result);
        String segmentLength = segment.path("segmentLength").asText(null);
        if (segmentLength == null || !("CALCULATE_FROM_SEGMENT_CONTENT".equals(segmentLength)
                || SEGMENT_LENGTH.matcher(segmentLength).matches())) {
            result.addError("Segment100Serialization", id + " standardSegment.segmentLength is invalid");
        } else if (LENGTH.matcher(segmentLength).matches()) {
            int value = Integer.parseInt(segmentLength);
            if (value < SEGMENT_100_MIN_LENGTH || value > SEGMENT_100_MAX_LENGTH) {
                result.addError("Segment100Serialization", id
                        + " standardSegment.segmentLength " + segmentLength
                        + " is outside ATL105 Chapter 13.2 Element 84's Segment 100 range 001-218");
            }
        }
        if ("CALCULATE_FROM_SEGMENT_CONTENT".equals(segmentLength)) {
            result.addWarning("REVIEW_REQUIRED: " + id + " Segment 100 length is a generation instruction, not verified encoded-length evidence");
        }

        List<String> actualOrder = fieldNames(segment);
        if (!isValidFieldOrder(actualOrder)) {
            result.addError("Segment100Serialization", id + " Segment 100 field order is "
                    + actualOrder + "; expected " + SEGMENT_100_ORDER);
        }

        if (controlBoolean(controls, "serialization.trailingOptionalFieldsOmitted")) {
            validateTrailingFields(segment, id, result);
            JsonNode omitted = controls.path("serialization").path("omittedTrailingFields");
            if (omitted.isArray()) {
                for (JsonNode field : omitted) {
                    if (segment.has(field.asText())) {
                        result.addError("Segment100Serialization", id
                                + " trailing optional field was not omitted: " + field.asText());
                    }
                }
            }
        }
        if (controlBoolean(controls, "serialization.nonTrailingEmptyFieldSeparator")
                && !section1.path("fieldSeparators").path("afterElement63").asBoolean(false)) {
            result.addError("Segment100Serialization", id + " non-trailing empty fields require separators");
        }
    }

    private static boolean isValidFieldOrder(List<String> actualOrder) {
        if (actualOrder.size() > SEGMENT_100_ORDER.size()) return false;
        for (int index = 0; index < actualOrder.size(); index++) {
            if (!SEGMENT_100_ORDER.get(index).equals(actualOrder.get(index))) return false;
        }
        return true;
    }

    private static List<String> fieldNames(JsonNode segment) {
        List<String> names = new ArrayList<>();
        Iterator<String> fields = segment.fieldNames();
        while (fields.hasNext()) {
            names.add(fields.next());
        }
        return names;
    }

    private static void validateTrailingFields(JsonNode segment, String id, ValidationResult result) {
        List<String> names = fieldNames(segment);
        for (int index = names.size() - 1; index >= 0; index--) {
            String name = names.get(index);
            if (segment.path(name).asText("").isEmpty()) {
                result.addError("Segment100Serialization", id
                        + " contains an empty trailing field: " + name);
            } else {
                break;
            }
        }
    }

    private static void checkEquals(JsonNode node, String field, String expected, String id,
                                    String path, ValidationResult result) {
        String actual = node.path(field).asText(null);
        if (!expected.equals(actual)) {
            result.addError("Segment100Serialization", id + " " + path + "." + field
                    + " must be '" + expected + "', got: " + actual);
        }
    }

    private static boolean controlBoolean(JsonNode controls, String dottedName) {
        if (controls.path(dottedName).isBoolean()) {
            return controls.path(dottedName).asBoolean();
        }
        int separator = dottedName.indexOf('.');
        if (separator > 0) {
            return controls.path(dottedName.substring(0, separator))
                    .path(dottedName.substring(separator + 1)).asBoolean(false);
        }
        return false;
    }
}