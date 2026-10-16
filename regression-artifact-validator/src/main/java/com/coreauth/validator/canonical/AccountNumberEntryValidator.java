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

    public ValidationResult validateTestData(CanonicalTestData data) {
        String id = data == null || data.getId() == null ? "account-number-entry" : data.getId();
        ValidationResult result = new ValidationResult(id);
        if (data == null || data.getPayload() == null) {
            result.addError("AccountNumber", id + " test data payload is required");
            return result;
        }
        if (!data.getPayload().path("request").path("dataSection2").path("standardSegment").isObject()) {
            result.addError("AccountNumber", id + " an observed standardSegment object is required");
            return result;
        }
        JsonNode controls = data.getPayload().path("testControls");
        if (!controls.path("validateAccountNumberEntry").asBoolean(false)
                && !controls.path("validateCardDiscretionaryBlockData").asBoolean(false)
                && !controls.path("validateNonTransArmorRequestAccountLength").asBoolean(false)) {
            result.addError("AccountNumber", id + " no Element 2/12 validation was requested");
            return result;
        }
        validate(data, result);
        return result;
    }

    private void validate(CanonicalTestData data, ValidationResult result) {
        if (data == null || data.getPayload() == null) return;
        JsonNode controls = data.getPayload().path("testControls");
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        JsonNode segment = data.getPayload().path("request").path("dataSection2").path("standardSegment");
        String account = segment.path("accountNumber").asText(null);
        String discretionary = segment.path("cardDiscretionaryBlockData").asText("");
        if ((controls.path("validateAccountNumberEntry").asBoolean(false)
                || controls.path("validateNonTransArmorRequestAccountLength").asBoolean(false))
                && segment.has("accountNumber") && !segment.path("accountNumber").isTextual()) {
            result.addError("AccountNumber", id + " Element 2 must be a string");
            return;
        }
        if (controls.path("validateCardDiscretionaryBlockData").asBoolean(false)) {
            if (segment.has("cardDiscretionaryBlockData") && !segment.path("cardDiscretionaryBlockData").isTextual()) {
                result.addError("CardDiscretionaryBlockData", id + " Element 12 must be a string");
                return;
            }
            validateDiscretionaryContext(segment, controls, discretionary, id, result);
        }
        if (controls.path("validateCardDiscretionaryBlockData").asBoolean(false)
                && discretionary.getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 51) {
            result.addError("CardDiscretionaryBlockData", id + " exceeds the 51-byte Element 12 maximum");
        }
        if (controls.path("validateNonTransArmorRequestAccountLength").asBoolean(false)
                && account != null
                && account.getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 24) {
            result.addError("AccountNumber", id + " exceeds the 24-byte non-TransArmor request maximum");
        }
        if (!controls.path("validateAccountNumberEntry").asBoolean(false)) return;
        String mode = controls.path("entryMode").asText(null);
        if (mode == null || !java.util.Set.of("manual", "track1", "track2", "token", "transarmor")
                .contains(mode.toLowerCase(java.util.Locale.ROOT))) {
            result.addError("AccountNumber", id + " an explicit supported entry mode is required");
            return;
        }
        if (account == null || account.isBlank()) {
            result.addError("AccountNumber", id + " Account Number is required for the declared entry mode");
            return;
        }
        if ("manual".equalsIgnoreCase(mode) && !account.matches("^[A-Za-z0-9]+$")) {
            result.addError("AccountNumber", id + " manual Account Number must be alphanumeric");
        }
        if ("track1".equalsIgnoreCase(mode) || "track2".equalsIgnoreCase(mode)) {
            String separator = "track1".equalsIgnoreCase(mode) ? "^" : "=";
            String sourceTrack = controls.path("rawTrackData").asText(null);
            int separatorIndex = sourceTrack == null ? -1 : sourceTrack.indexOf(separator);
            if (separatorIndex < 0) {
                result.addError("AccountNumber", id + " " + mode + " source data must contain its declared separator");
            } else {
                String expectedAccount = sourceTrack.substring(0, separatorIndex);
                String expectedDiscretionary = sourceTrack.substring(separatorIndex + 1);
                if (!account.equals(expectedAccount)) {
                    result.addError("AccountNumber", id + " Element 2 must contain only the track data before " + separator);
                }
                if (!discretionary.equals(expectedDiscretionary)) {
                    result.addError("CardDiscretionaryBlockData", id + " Element 12 must contain the track data after " + separator);
                }
            }
        }
        if (("token".equalsIgnoreCase(mode) || "transarmor".equalsIgnoreCase(mode))
                && !controls.path("tokenOrTransarmorRepresentation").asBoolean(false)) {
            result.addError("AccountNumber", id + " tokenized mode requires explicit token/TransArmor representation");
        }
    }

    private void validateDiscretionaryContext(JsonNode segment, JsonNode controls, String discretionary,
                                              String id, ValidationResult result) {
        String cardType = segment.path("promptCode").path("cardType").asText("");
        if (java.util.Set.of("093", "095", "096").contains(cardType)) {
            if (!discretionary.isEmpty()) {
                result.addError("CardDiscretionaryBlockData", id + " Element 12 must be skipped for card type " + cardType);
            }
            return;
        }
        if (controls.path("manualCardExpiration").asBoolean(false)
                && !discretionary.matches("(?:0[1-9]|1[0-2])[0-9]{2}.*")) {
            result.addError("CardDiscretionaryBlockData", id + " manually entered card expiration must begin with MMYY");
        }
        if ("transarmor".equalsIgnoreCase(controls.path("entryMode").asText())
                && discretionary.isBlank()) {
            result.addError("CardDiscretionaryBlockData", id + " TransArmor processing requires expiration data in Element 12");
        }
        String network = controls.path("network").asText("");
        String paymentContext = controls.path("paymentContext").asText("");
        if (("ACCEL".equals(network) || "STAR".equals(network))
                && ("ECOMMERCE".equals(paymentContext) || "ONE_TIME_BILL_PAYMENT".equals(paymentContext))
                && discretionary.isBlank()) {
            result.addError("CardDiscretionaryBlockData", id + " " + network + " " + paymentContext
                    + " requires expiration data in Element 12");
        }
    }
}