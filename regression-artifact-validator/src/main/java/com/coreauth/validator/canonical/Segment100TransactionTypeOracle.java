package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** Independent oracle for standard financial transaction types carried by Segment 100. */
public final class Segment100TransactionTypeOracle {
    private static final Map<String, String> EXPECTED_CODES;
    private static final Set<String> MULTI_STEP_CODES = Set.of("0", "3", "5", "7", "8", "B", "C", "S", "U", "Z");

    static {
        Map<String, String> codes = new LinkedHashMap<>();
        codes.put("0", "POS Purchase/Capture or preauthorized completion");
        codes.put("3", "POS Authorization Only");
        codes.put("4", "Customer-activated Purchase/Capture");
        codes.put("5", "Customer-activated Authorization Only");
        codes.put("6", "Mail/Phone Purchase");
        codes.put("7", "Merchandise Return/Refund");
        codes.put("8", "Purchase Reversal/Void");
        codes.put("A", "Account Verification");
        codes.put("B", "Mail/Phone Authorization Only");
        codes.put("C", "Mail/Phone Reversal/Void");
        codes.put("S", "Cancellation");
        codes.put("U", "Void of Merchandise Return");
        codes.put("Z", "Time-out Reversal");
        EXPECTED_CODES = Collections.unmodifiableMap(codes);
    }

    public Map<String, String> expectedCodeMatrix() {
        return EXPECTED_CODES;
    }

    public ValidationResult validateAiPayload(String documentId, JsonNode aiPayload) {
        ValidationResult result = new ValidationResult(documentId == null ? "segment-100-transaction-type" : documentId);
        if (aiPayload == null || !aiPayload.isObject()) {
            result.addError("Segment100TransactionType", "AI artifact must be a JSON object");
            return result;
        }

        JsonNode request = aiPayload.path("Financial Request");
        if (!request.isObject()) {
            result.addError("Segment100TransactionType", "Financial Request object is required");
            return result;
        }
        if (!"ATL105".equals(request.path("MessageType").asText(null))) {
            result.addError("Segment100TransactionType", "Financial Request.MessageType must be ATL105");
        }
        String segmentCount = request.path("NumSegments").asText(null);
        if (segmentCount == null || !segmentCount.matches("^[0-9]{1,2}$")) {
            result.addError("Segment100TransactionType", "Financial Request.NumSegments must be one or two digits");
        }

        JsonNode standardSegment = request.path("Standard Segment");
        if (!standardSegment.isObject()) {
            result.addError("Segment100TransactionType", "Financial Request.Standard Segment object is required");
            return result;
        }
        if (!"100".equals(standardSegment.path("SegmentType").asText(null))) {
            result.addError("Segment100TransactionType", "Financial Request.Standard Segment.SegmentType must be 100");
        }

        JsonNode promptCode = standardSegment.path("PromptCode");
        if (!promptCode.isObject()) {
            result.addError("Segment100TransactionType", "Financial Request.Standard Segment.PromptCode object is required");
            return result;
        }
        String code = promptCode.path("TransactionType").asText(null);
        if (code == null || code.length() != 1) {
            result.addError("Segment100TransactionType", "PromptCode.TransactionType must be one character");
        } else if (!EXPECTED_CODES.containsKey(code)) {
            result.addError("Segment100TransactionType", "Unsupported standard financial transaction type: " + code);
        }
        return result;
    }

    public boolean isMultiStepCode(String code) {
        return MULTI_STEP_CODES.contains(code);
    }

    public boolean isSingleStepEligibleCode(String code) {
        return EXPECTED_CODES.containsKey(code);
    }
}
