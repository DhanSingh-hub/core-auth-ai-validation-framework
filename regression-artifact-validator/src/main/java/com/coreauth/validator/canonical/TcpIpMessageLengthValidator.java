package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

/** Validates a declared TCP/IP message length against an explicit hex payload. */
public final class TcpIpMessageLengthValidator {

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String packageId = artifactPackage == null || artifactPackage.getManifest() == null
                ? "tcp-ip-message-length" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(packageId == null ? "tcp-ip-message-length" : packageId);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("TcpIpMessageLength", "test data is required");
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
        if (!controls.path("validateMessageLength").asBoolean(false)) {
            return;
        }
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        JsonNode header = data.getPayload().path("request").path("tcpIpHeader");
        String declared = header.path("messageLength").asText(null);
        String payloadHex = controls.path("wirePayloadHex").asText(null);
        String byteOrder = header.path("byteOrder").asText(null);

        if (payloadHex == null || !payloadHex.matches("(?i)^[0-9a-f]*$") || payloadHex.length() % 2 != 0) {
            result.addError("TcpIpMessageLength", id + " wirePayloadHex must contain an even number of hex digits");
            return;
        }
        if (!"network-big-endian".equals(byteOrder)) {
            result.addError("TcpIpMessageLength", id + " byteOrder must be network-big-endian");
        }
        if (declared == null || !declared.matches("(?i)^[0-9a-f]{4}$")) {
            result.addError("TcpIpMessageLength", id + " messageLength must be four hexadecimal digits");
            return;
        }

        int declaredLength = Integer.parseInt(declared, 16);
        int actualLength = payloadHex.length() / 2;
        if (declaredLength != actualLength) {
            result.addError("TcpIpMessageLength", id + " declares " + declaredLength
                    + " payload bytes but contains " + actualLength);
        }
    }
}