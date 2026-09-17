package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Independent oracle for ATL105 Section 5 payment-network families. */
public final class Segment100PaymentNetworkOracle {
    private static final Map<String, String> NETWORKS;

    static {
        Map<String, String> networks = new LinkedHashMap<>();
        networks.put("ACH_PREAUTHORIZED_DEBIT", "Automated clearing house preauthorized debit cards");
        networks.put("ATM_PIN_DEBIT", "ATM PIN debit cards");
        networks.put("ATM_PINLESS_DEBIT", "ATM PINless debit cards");
        networks.put("CHECK_GUARANTEE", "Check guarantee services");
        networks.put("CHECK_VERIFICATION_BUYPASS", "Check verification services provided by BUYPASS");
        networks.put("CHECK_VERIFICATION_EXTERNAL", "Check verification services provided by others");
        networks.put("CREDIT_NONPROPRIETARY", "Nonproprietary credit cards");
        networks.put("CREDIT_PROPRIETARY", "Proprietary credit cards");
        networks.put("EBT_FOOD", "EBT food stamp benefits");
        networks.put("EBT_CASH", "EBT cash benefits");
        networks.put("EBT_EWIC", "EBT eWIC");
        networks.put("PROPRIETARY_CARD", "Proprietary cards");
        networks.put("PETROLEUM_PROPRIETARY", "Petroleum proprietary cards");
        networks.put("FLEET", "Fleet cards");
        networks.put("STORED_VALUE", "Stored value cards");
        networks.put("LOYALTY", "Loyalty cards");
        NETWORKS = Collections.unmodifiableMap(networks);
    }

    public Map<String, String> expectedNetworks() {
        return NETWORKS;
    }

    public ValidationResult validateArtifact(String documentId, JsonNode artifact) {
        ValidationResult result = new ValidationResult(documentId == null ? "payment-network" : documentId);
        String network = artifact == null ? null : artifact.path("metadata").path("paymentNetworkType").asText(null);
        if (network == null || !NETWORKS.containsKey(network)) {
            result.addError("PaymentNetwork", "Unsupported payment network type: " + network);
            return result;
        }
        JsonNode request = artifact.path("Financial Request");
        if (!request.isObject()) {
            result.addError("PaymentNetwork", "Financial Request is required for Segment 100 network context");
            return result;
        }
        JsonNode segment = request.path("Standard Segment");
        if (!segment.isObject() || !"100".equals(segment.path("SegmentType").asText(null))) {
            result.addError("PaymentNetwork", "Standard Segment Type 100 is required");
        }
        return result;
    }
}
