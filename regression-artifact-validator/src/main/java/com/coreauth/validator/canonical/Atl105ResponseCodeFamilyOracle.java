package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;

import java.util.Map;
import java.util.Set;

/** Context-aware Element 83 code membership derived from the ATL105 response-code family matrix. */
public final class Atl105ResponseCodeFamilyOracle {
    private static final Map<String, Set<String>> FAMILY_CODES = Map.of(
        "financial", Set.of("0", "1", "2", "3", "4", "F", "S"),
        "totals", Set.of("5", "6", "7", "B", "C", "D", "E", "M", "N", "V", "W"),
        "electronic-mail", Set.of("8", "9", "G", "J"),
        "communications-test", Set.of("1"),
        "proprietary-load", Set.of("H", "O", "T", "U", "X", "Y"),
        "transarmor", Set.of("K", "L", "P"),
        "emv-key-load", Set.of("L", "M", "X")
    );
    private static final Set<String> CONTEXT_DEPENDENT_CODES = Set.of("1", "L", "M", "X");

    public ValidationResult validate(String messageFamily, String responseCode) {
        String family = messageFamily == null ? "" : messageFamily.trim().toLowerCase();
        ValidationResult result = new ValidationResult("atl105-response-code");
        Set<String> allowedCodes = FAMILY_CODES.get(family);
        if (allowedCodes == null) {
            result.addError("ResponseCode", "Known message-family context is required for Element 83 validation");
            return result;
        }
        if (responseCode == null || responseCode.length() != 1 || !allowedCodes.contains(responseCode)) {
            result.addError("ResponseCode", "Response Code " + responseCode + " is not documented for family " + family);
            return result;
        }
        if (CONTEXT_DEPENDENT_CODES.contains(responseCode)) {
            result.addWarning("Element 83 code " + responseCode + " has multiple source-defined meanings; interpretation is scoped to family " + family);
        }
        return result;
    }

    public static Set<String> expectedCodes(String messageFamily) {
        if (messageFamily == null) return Set.of();
        return FAMILY_CODES.getOrDefault(messageFamily.trim().toLowerCase(), Set.of());
    }

    public static Set<String> familiesForCode(String responseCode) {
        if (responseCode == null) return Set.of();
        return FAMILY_CODES.entrySet().stream()
            .filter(entry -> entry.getValue().contains(responseCode))
            .map(Map.Entry::getKey)
            .collect(java.util.stream.Collectors.toUnmodifiableSet());
    }

    public static Set<String> contextDependentCodes() {
        return CONTEXT_DEPENDENT_CODES;
    }
}