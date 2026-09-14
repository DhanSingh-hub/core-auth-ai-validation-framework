package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

/** Validates fixed TPDU header values required when sending an ATL105 request to BUYPASS. */
public final class TcpIpHeaderValidator {

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String packageId = artifactPackage == null || artifactPackage.getManifest() == null
                ? "tcp-ip-header" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(packageId == null ? "tcp-ip-header" : packageId);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("TcpIpHeader", "test data is required");
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
        if (!controls.path("validateTpduHeader").asBoolean(false)) {
            return;
        }
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        JsonNode header = data.getPayload().path("request").path("tcpIpHeader");
        check(header, "tpduProtocolId", "60", id, result);
        check(header, "tpduDestinationAddress", "0000", id, result);
        check(header, "tpduSourceAddress", "0000", id, result);
    }

    private static void check(JsonNode header, String field, String expected, String id,
                              ValidationResult result) {
        String actual = header.path(field).asText(null);
        if (!expected.equals(actual)) {
            result.addError("TcpIpHeader", id + " " + field + " must be '" + expected + "', got: " + actual);
        }
    }
}