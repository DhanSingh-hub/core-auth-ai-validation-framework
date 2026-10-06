package com.coreauth.validator.canonical;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

/** Bounded Appendix K representation checks; unsupported layout semantics stay explicit. */
public final class AppendixKDataLayoutValidator {
    public enum Status {
        PASS,
        FAIL,
        REVIEW_REQUIRED,
        NOT_ASSERTABLE
    }

    public record Assessment(String tableId, Status status, String assertionScope, String reason) {
    }

    private static final Map<String, Set<String>> TABLE_004_CODES = Map.of(
            "AMEX", Set.of("Y", "N", "U"),
            "DISCOVER", Set.of("M", "N", "P", "S", "U"),
            "MASTERCARD", Set.of("M", "N", "P", "U"),
            "VISA", Set.of("M", "N", "P", "S", "U"),
            "STAR", Set.of("M", "N", "P", "S", "U"),
            "PULSE", Set.of("M", "N", "P", "S", "U"),
            "ACCEL", Set.of(" ", "N", "Y"),
            "NYCE", Set.of("M", "N", "P", "S", "U"));

    public Assessment validate(String tableId, String value, String network) {
        if (tableId == null || !tableId.matches("[0-9]{3}")) {
            return assessment(tableId, Status.FAIL, "SELECTOR_FORMAT", "Appendix K selector must be three digits.");
        }
        if ("002".equals(tableId)) {
            return assessment(tableId, Status.FAIL, "RESERVED_SELECTOR", "Appendix K Table 002 is reserved.");
        }
        if (!new AppendixKTableCatalog().entries().containsKey(tableId)) {
            return assessment(tableId, Status.NOT_ASSERTABLE, "UNLISTED_SELECTOR",
                    "No Appendix K table layout is defined for this selector.");
        }
        if (value == null) {
            return assessment(tableId, Status.FAIL, "DATA_PRESENCE", "Element 118 data is required for an assigned table.");
        }

        return switch (tableId) {
            case "001" -> fixedNumeric(tableId, value, 6);
            case "003" -> avsNvsShape(value);
            case "004" -> cardVerification(value, network);
            default -> assessment(tableId, Status.NOT_ASSERTABLE, "LAYOUT_NOT_IMPLEMENTED",
                    "Table ID is recognized, but its Element 118 layout has not yet been independently implemented.");
        };
    }

    private static Assessment fixedNumeric(String tableId, String value, int maxLength) {
        if (!value.matches("[0-9]{1," + maxLength + "}")) {
            return assessment(tableId, Status.FAIL, "REPRESENTATION",
                    "Balance data must be numeric and no more than " + maxLength + " characters.");
        }
        return assessment(tableId, Status.PASS, "REPRESENTATION_ONLY",
                "Numeric maximum length is valid; balance eligibility and value meaning are not evaluated.");
    }

    private static Assessment avsNvsShape(String value) {
        if (value.length() > 10) {
            return assessment("003", Status.FAIL, "REPRESENTATION", "AVS/NVS data exceeds the source maximum of 10 characters.");
        }
        if (value.matches("AVS[+-][A-Za-z0-9]"
                + "|NVS[+-][A-Za-z0-9]"
                + "|AVS[+-][A-Za-z0-9]NVS[+-][A-Za-z0-9]")) {
            return assessment("003", Status.PASS, "REPRESENTATION_ONLY",
                    "AVS/NVS signed response framing matches the documented shape; issuer/network response-code meaning is not evaluated.");
        }
        return assessment("003", Status.FAIL, "REPRESENTATION", "AVS/NVS data must use AVScr, NVScr, or AVScrNVScr framing.");
    }

    private static Assessment cardVerification(String value, String network) {
        if (value.length() != 1) {
            return assessment("004", Status.FAIL, "REPRESENTATION", "Card-verification result must be one character.");
        }
        if (network == null || network.isBlank()) {
            if (Set.of("M", "N", "P", "S", "U", "Y", " ").contains(value)) {
                return assessment("004", Status.REVIEW_REQUIRED, "NETWORK_CODE_REVIEW",
                        "The one-character representation is plausible, but issuer/network context is required to validate the code.");
            }
            return assessment("004", Status.FAIL, "REPRESENTATION", "Card-verification result is not in the documented code families.");
        }
        Set<String> allowed = TABLE_004_CODES.get(network.strip().toUpperCase(Locale.ROOT));
        if (allowed == null) {
            return assessment("004", Status.REVIEW_REQUIRED, "NETWORK_CODE_REVIEW",
                    "No Appendix K Table 004 response-code mapping is configured for network " + network + ".");
        }
        if (!allowed.contains(value)) {
            return assessment("004", Status.FAIL, "NETWORK_CODE", "Card-verification result is not listed for " + network + ".");
        }
        return assessment("004", Status.PASS, "NETWORK_CODE",
                "Card-verification result is listed for " + network + "; authorization outcome is not inferred.");
    }

    private static Assessment assessment(String tableId, Status status, String scope, String reason) {
        return new Assessment(tableId, status, scope, reason);
    }
}