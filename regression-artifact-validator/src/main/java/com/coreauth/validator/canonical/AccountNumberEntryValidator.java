package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

/** Validates Account Number dependencies on POS entry mode and account representation. */
public final class AccountNumberEntryValidator {
    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String id = artifactPackage == null || artifactPackage.getManifest() == null
                ? "account-number-entry" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(id == null ? "account-number-entry" : id);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("AccountNumber", "test data is required");
            return result;
        }
        for (CanonicalTestData data : artifactPackage.getTestData()) validate(data, result);
        return result;
    }

    private void validate(CanonicalTestData data, ValidationResult result) {
        if (data == null || data.getPayload() == null) return;
        JsonNode controls = data.getPayload().path("testControls");
        if (!controls.path("validateAccountNumberEntry").asBoolean(false)) return;
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        JsonNode segment = data.getPayload().path("request").path("dataSection2").path("standardSegment");
        String account = segment.path("accountNumber").asText(null);
        String mode = controls.path("entryMode").asText(null);
        if (account == null || account.isEmpty()) {
            result.addError("AccountNumber", id + " Account Number is required for the declared entry mode");
            return;
        }
        if ("manual".equalsIgnoreCase(mode) && !account.matches("^[A-Za-z0-9]+$")) {
            result.addError("AccountNumber", id + " manual Account Number must be alphanumeric");
        }
        if (("track1".equalsIgnoreCase(mode) || "track2".equalsIgnoreCase(mode))
                && !account.contains("=")) {
            result.addError("AccountNumber", id + " " + mode + " data must contain the track separator");
        }
        if ("emv".equalsIgnoreCase(mode) && controls.path("emvDataPresent").asBoolean(false)
                && !segment.path("cardDiscretionaryBlockData").asText("").isEmpty()) {
            result.addError("AccountNumber", id + " EMV account context must not use track discretionary data as a substitute for chip data");
        }
        if (("token".equalsIgnoreCase(mode) || "transarmor".equalsIgnoreCase(mode))
                && !controls.path("tokenOrTransarmorRepresentation").asBoolean(false)) {
            result.addError("AccountNumber", id + " tokenized mode requires explicit token/TransArmor representation");
        }
    }
}