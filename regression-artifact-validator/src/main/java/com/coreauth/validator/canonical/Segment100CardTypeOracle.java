package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** Independent oracle for the 36 financial Prompt Code card types in Segment 100. */
public final class Segment100CardTypeOracle {
    private static final Map<String, String> EXPECTED_CARD_TYPES;
    private static final Set<String> VALID_TRANSACTION_TYPES = Set.of(
            "0", "3", "4", "5", "6", "7", "8", "A", "B", "C", "S", "U", "Z");

    static {
        Map<String, String> codes = new LinkedHashMap<>();
        codes.put("001", "BUYPASS Fleet");
        codes.put("011", "Debit Checking");
        codes.put("012", "Debit Saving");
        codes.put("013", "Debit ACH");
        codes.put("020", "Credit");
        codes.put("040", "Loyalty");
        codes.put("041", "Certegy / Telecredit");
        codes.put("045", "Generic Check");
        codes.put("046", "ECA / TeleCheck");
        codes.put("050", "Valero Energy");
        codes.put("051", "Shell");
        codes.put("052", "ExxonMobil");
        codes.put("053", "ExxonMobil Fleet");
        codes.put("055", "Valero UCC");
        codes.put("056", "Generic Proprietary");
        codes.put("057", "Sohio / Gulf");
        codes.put("058", "Cenex");
        codes.put("059", "Wright Express");
        codes.put("060", "Voyager");
        codes.put("061", "Car Care One");
        codes.put("064", "Tesoro Petroleum");
        codes.put("070", "Valero Fleet");
        codes.put("071", "Fleet One");
        codes.put("072", "Tesoro UCC");
        codes.put("073", "EBT Food Stamps");
        codes.put("074", "EBT Cash Benefits");
        codes.put("075", "Citgo Fleet");
        codes.put("077", "Unocal");
        codes.put("078", "Phone");
        codes.put("079", "Stored Value");
        codes.put("080", "MasterCard Fleet");
        codes.put("081", "Fuelman / Gascard");
        codes.put("083", "Cardlock Fuel System");
        codes.put("084", "Sinclair");
        codes.put("085", "Sunoco");
        codes.put("090", "Conoco");
        EXPECTED_CARD_TYPES = Collections.unmodifiableMap(codes);
    }

    public Map<String, String> expectedCardTypes() {
        return EXPECTED_CARD_TYPES;
    }

    public ValidationResult validateAiPayload(String documentId, JsonNode aiPayload) {
        ValidationResult result = new ValidationResult(documentId == null ? "segment-100-card-type" : documentId);
        JsonNode request = aiPayload == null ? null : aiPayload.path("Financial Request");
        JsonNode segment = request == null ? null : request.path("Standard Segment");
        if (request == null || !request.isObject() || segment == null || !segment.isObject()) {
            result.addError("Segment100CardType", "Financial Request.Standard Segment is required");
            return result;
        }
        JsonNode promptCode = segment.path("PromptCode");
        if (!promptCode.isObject()) {
            result.addError("Segment100CardType", "Standard Segment.PromptCode object is required");
            return result;
        }
        String transactionType = promptCode.path("TransactionType").asText(null);
        String cardType = promptCode.path("CardType").asText(null);
        if (transactionType == null || !VALID_TRANSACTION_TYPES.contains(transactionType)) {
            result.addError("Segment100CardType", "Unsupported financial transaction type: " + transactionType);
        }
        if (cardType == null || !cardType.matches("^[0-9]{3}$")) {
            result.addError("Segment100CardType", "CardType must be exactly three digits");
        } else if (!EXPECTED_CARD_TYPES.containsKey(cardType)) {
            result.addError("Segment100CardType", "Unsupported financial card type: " + cardType);
        }
        return result;
    }
}
