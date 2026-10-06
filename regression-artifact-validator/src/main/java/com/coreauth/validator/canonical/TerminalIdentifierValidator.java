package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.regex.Pattern;

/** Validates Element 102 and its declared terminal-identifier dependencies. */
public final class TerminalIdentifierValidator {
    private static final Pattern ALPHANUMERIC = Pattern.compile("^[A-Za-z0-9]+$");
    private static final Pattern ANSI_STATE_CODE = Pattern.compile("^[0-9]{2}$");

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String packageId = artifactPackage == null || artifactPackage.getManifest() == null
                ? "terminal-identifier" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(packageId == null ? "terminal-identifier" : packageId);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("TerminalIdentifier", "test data is required");
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
        if (!controls.path("validateTerminalIdentifier").asBoolean(false)) {
            return;
        }
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        String terminalId = data.getPayload().path("request").path("dataSection2")
                .path("standardSegment").path("terminalIdentifier").asText(null);
        if (terminalId == null || !ALPHANUMERIC.matcher(terminalId).matches() || terminalId.length() > 22) {
            result.addError("TerminalIdentifier", id + " Element 102 must be 1-22 alphanumeric characters");
            return;
        }
        if (terminalId.length() < 4) {
            result.addError("TerminalIdentifier", id + " Element 102 must contain a two-digit ANSI state code at positions 3-4");
            return;
        }
        String ansiStateCode = terminalId.substring(2, 4);
        if (!ANSI_STATE_CODE.matcher(ansiStateCode).matches()
                || !AppendixDStateCodes.isValidAnsiCode(ansiStateCode)) {
            result.addError("TerminalIdentifier", id + " Element 102 positions 3-4 must contain a valid Appendix D ANSI state code");
        }

        String mode = controls.path("terminalIdentifierMode").asText("financial");
        if (isLoadMode(mode) && terminalId.length() > 13) {
            result.addError("TerminalIdentifier", id + " Element 102 must be at most 13 characters for " + mode);
        }

        JsonNode components = controls.path("terminalIdentifierComponents");
        if (components.isObject()) {
            validateComponents(components, terminalId, id, result);
        }
    }

    private static void validateComponents(JsonNode components, String terminalId, String id,
                                           ValidationResult result) {
        String deviceType = components.path("deviceType").asText(null);
        String stateCode = components.path("stateCode").asText(null);
        String merchantNumber = components.path("merchantNumber").asText(null);
        String deviceNumber = components.path("deviceNumber").asText(null);
        if (deviceType == null || deviceType.length() != 2 || !ALPHANUMERIC.matcher(deviceType).matches()) {
            result.addError("TerminalIdentifier", id + " deviceType must be 2 alphanumeric characters");
        }
        if (stateCode == null || !ANSI_STATE_CODE.matcher(stateCode).matches()
                || !AppendixDStateCodes.isValidAnsiCode(stateCode)) {
            result.addError("TerminalIdentifier", id + " stateCode must be a valid two-digit Appendix D ANSI code");
        } else if (terminalId.length() >= 4 && !stateCode.equals(terminalId.substring(2, 4))) {
            result.addError("TerminalIdentifier", id + " declared stateCode does not match Element 102 positions 3-4");
        }
        if (merchantNumber == null || merchantNumber.length() < 6 || merchantNumber.length() > 15
                || !ALPHANUMERIC.matcher(merchantNumber).matches()) {
            result.addError("TerminalIdentifier", id + " merchantNumber must be 6-15 alphanumeric characters");
        }
        if (deviceNumber == null || deviceNumber.length() != 3 || !ALPHANUMERIC.matcher(deviceNumber).matches()) {
            result.addError("TerminalIdentifier", id + " deviceNumber must be 3 alphanumeric characters");
        }
        if (deviceType != null && stateCode != null && merchantNumber != null && deviceNumber != null
                && !terminalId.equals(deviceType + stateCode + merchantNumber + deviceNumber)) {
            result.addError("TerminalIdentifier", id + " Element 102 does not match its declared components");
        }
    }

    private static boolean isLoadMode(String mode) {
        return "table-load".equalsIgnoreCase(mode) || "phone-load".equalsIgnoreCase(mode)
                || "date-time-load".equalsIgnoreCase(mode) || "software-load".equalsIgnoreCase(mode);
    }
}