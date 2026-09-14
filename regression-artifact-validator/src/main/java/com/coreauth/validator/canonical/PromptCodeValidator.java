package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Set;

/** Validates Prompt Code transaction/card structure and declared flow dependencies. */
public final class PromptCodeValidator {
    private static final Set<String> TRANSACTION_TYPES = Set.of(
            "0", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "K", "L", "M", "N", "Q", "S", "T", "U", "V", "Z");

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String packageId = artifactPackage == null || artifactPackage.getManifest() == null
                ? "prompt-code" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(packageId == null ? "prompt-code" : packageId);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("PromptCode", "test data is required");
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
        if (!controls.path("validatePromptCode").asBoolean(false)) {
            return;
        }
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        String promptCode = data.getPayload().path("request").path("dataSection2")
                .path("standardSegment").path("promptCode").asText(null);
        if (promptCode == null || !promptCode.matches("^[A-Za-z0-9]{3,4}$")) {
            result.addError("PromptCode", id + " Element 78 must be 3-4 alphanumeric characters");
            return;
        }
        String transactionType = promptCode.substring(0, 1);
        if (!TRANSACTION_TYPES.contains(transactionType)) {
            result.addError("PromptCode", id + " has unsupported transaction type: " + transactionType);
        }
        String expectedTransactionType = controls.path("expectedTransactionType").asText(null);
        if (expectedTransactionType != null && !expectedTransactionType.equals(transactionType)) {
            result.addError("PromptCode", id + " transaction type is " + transactionType
                    + "; expected " + expectedTransactionType);
        }
        String expectedCardType = controls.path("expectedCardType").asText(null);
        if (expectedCardType != null && !promptCode.substring(1).equals(expectedCardType)) {
            result.addError("PromptCode", id + " card type is " + promptCode.substring(1)
                    + "; expected " + expectedCardType);
        }
        if (controls.path("requiresFinancialTransaction").asBoolean(false) && "9".equals(transactionType)) {
            result.addError("PromptCode", id + " transaction type 9 is special, not a financial transaction type");
        }
    }
}