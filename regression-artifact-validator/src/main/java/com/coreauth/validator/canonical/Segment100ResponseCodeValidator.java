package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Set;

/** Validates standard Segment 100 financial response outcomes carried by Element 83. */
public final class Segment100ResponseCodeValidator {
    private static final Set<String> RESPONSE_CODES = Atl105ResponseCodeFamilyOracle.expectedCodes("financial");
    private static final Set<String> PURCHASE_TYPES = Set.of("0", "4", "6");
    private static final Set<String> AUTHORIZATION_TYPES = Set.of("3", "5", "B");

    public ValidationResult validate(String documentId, JsonNode responseArtifact) {
        ValidationResult result = new ValidationResult(documentId == null ? "segment-100-response" : documentId);
        if (responseArtifact == null || !responseArtifact.isObject()) {
            result.addError("Segment100Response", "AI response artifact must be a JSON object");
            return result;
        }
        JsonNode response = responseArtifact.path("Financial Response");
        if (!response.isObject()) {
            result.addError("Segment100Response", "Financial Response object is required");
            return result;
        }
        if (!"ATL105".equals(response.path("MessageType").asText(null))) {
            result.addError("Segment100Response", "Financial Response.MessageType must be ATL105");
        }
        String responseCode = response.path("ResponseCode").asText(null);
        if (responseCode == null || !RESPONSE_CODES.contains(responseCode)) {
            result.addError("Segment100Response", "Unsupported standard financial ResponseCode: " + responseCode);
            return result;
        }
        String sequence = response.path("SequenceNumber").asText(null);
        if (sequence == null || !sequence.matches("^[0-9]{6}$")) {
            result.addError("Segment100Response", "Response SequenceNumber must be six digits");
        }
        String expectedTransactionType = responseArtifact.path("metadata").path("expectedTransactionType").asText(null);
        if (expectedTransactionType != null) {
            validateCompatibility(responseCode, expectedTransactionType, result);
        }
        boolean approvalNumberRequired = responseArtifact.path("metadata").path("requiresApprovalNumber").asBoolean(false);
        if (approvalNumberRequired && response.path("ApprovalNumber").asText("").isBlank()) {
            result.addError("Segment100Response", "Lifecycle context requires ApprovalNumber");
        }
        if ("F".equals(responseCode)) {
            String approvedAmount = response.path("ApprovedAmount").asText(null);
            if (approvedAmount == null || !approvedAmount.matches("^[0-9]{9}$")) {
                result.addError("Segment100Response", "Partial approval requires nine-digit ApprovedAmount");
            }
        }
        if ("1".equals(responseCode) || "S".equals(responseCode)) {
            String declineCode = response.path("DeclineCode").asText(null);
            if (declineCode == null || !declineCode.matches("^[A-Za-z0-9]{2}$")) {
                result.addError("Segment100Response", "Declined response requires two-character DeclineCode");
            }
        }
        if (response.path("AuthorizerResponseCode").asText(null) == null) {
            result.addError("Segment100Response", "AuthorizerResponseCode is required");
        }
        return result;
    }

    private static void validateCompatibility(String responseCode, String transactionType, ValidationResult result) {
        boolean purchase = PURCHASE_TYPES.contains(transactionType);
        boolean authorization = AUTHORIZATION_TYPES.contains(transactionType);
        if (("0".equals(responseCode) || "4".equals(responseCode) || "F".equals(responseCode)) && !purchase) {
            result.addError("Segment100Response", "ResponseCode " + responseCode + " is incompatible with transaction type " + transactionType);
        }
        if (("2".equals(responseCode) || "3".equals(responseCode)) && !authorization) {
            result.addError("Segment100Response", "ResponseCode " + responseCode + " is incompatible with transaction type " + transactionType);
        }
    }

    public Set<String> expectedResponseCodes() {
        return RESPONSE_CODES;
    }
}
