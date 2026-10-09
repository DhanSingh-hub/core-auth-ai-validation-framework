package com.coreauth.validator.canonical;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Parsed-field updater checks, not a First Data eligibility decision or full message parser. */
public final class AppendixADAccountUpdaterOracle {
    public enum Status { PASS, FAIL, REVIEW_REQUIRED }
    public enum Brand { VISA, MASTERCARD, OTHER }
    public enum Operation { AUTH, SALE, REFUND, REVERSAL, COMPLETION, CANCELLATION, VOID_RETURN, TIMEOUT_REVERSAL, OTHER }
    public enum Phase { ORIGINAL, FOLLOW_ON, RETRY }
    public record Credential(String account, String expirationMmyy) { }
    public record Scenario(Brand brand, Operation operation, Phase phase, boolean transArmor,
                           Boolean cardNotPresent, Boolean credentialOnFile, Boolean installment,
                           Boolean recurring, Boolean acquirerParticipating, Boolean merchantRegistered,
                           Boolean merchantCertified, Boolean securelyStored,
                           Map<String, String> requestData, Map<String, String> responseData,
                           Boolean optimizerQualified, Boolean optimizerUsed,
                           Boolean networkDeclined, Boolean networkReattempted,
                           Credential originalCredential, Credential sentCredential,
                           Credential initialAttemptCredential, Operation updaterRequestOperation) {
        public Scenario {
            Objects.requireNonNull(brand, "brand");
            Objects.requireNonNull(operation, "operation");
            Objects.requireNonNull(phase, "phase");
            requestData = Map.copyOf(Objects.requireNonNull(requestData, "requestData"));
            responseData = Map.copyOf(Objects.requireNonNull(responseData, "responseData"));
        }
    }
    public record Assessment(String businessRequirementId, Status status, String reason) { }
    public static final List<String> RULES = List.of("PARTICIPATION-SCOPE", "TRIGGER-INDICATOR",
            "REQUEST-LAYOUT", "OVERRIDE", "RESPONSE-LAYOUT", "RESPONSE-GATES",
            "RESULT-CODE", "ACCOUNT-DEPENDENCY", "RETRY-CREDENTIALS", "EXTERNAL-EVIDENCE");
    public static final Map<String, String> RESULT_CODES = Map.ofEntries(
            Map.entry("VAU001", "Transaction contains token"),
            Map.entry("VAU002", "Supported only for Visa branded PAN"),
            Map.entry("VAU003", "Not supported for this network"),
            Map.entry("VAU004", "Not original purchase, return, bill payment, account funding or original credit"),
            Map.entry("VAU005", "Transaction contains CVV2"),
            Map.entry("VAU006", "Not a qualifying transaction type"),
            Map.entry("VAU007", "Not supported for this MCC"),
            Map.entry("VAU008", "Acquirer or processor not activated"),
            Map.entry("VAU009", "Issuer does not support Real Time VAU"),
            Map.entry("VAU010", "Issuer or Visa blocked merchant"),
            Map.entry("VAU011", "PPCS stop-payment order"),
            Map.entry("VAU012", "Request credentials are latest VAU data"),
            Map.entry("VAU013", "Request expiry later than VAU data"),
            Map.entry("VAU014", "PAN opted out of VAU"),
            Map.entry("VAU016", "PAN has stop advice"));
    private static final Set<String> CARD_STATUSES = Set.of("A", "E", "Q", "C", "U");

    public List<Assessment> assess(Scenario s) {
        Objects.requireNonNull(s, "scenario");
        return List.of(participation(s), trigger(s), requestLayout(s), override(s), responseLayout(s),
                responseGates(s), resultCode(s), credentials(s), retry(s),
                result("EXTERNAL-EVIDENCE", Status.REVIEW_REQUIRED,
                        "Enrollment/certification, secure storage, actual First Data qualification and issuer "
                                + "updates require authoritative evidence. Synthetic fields are not full wire, "
                                + "cryptographic token, production lifecycle or AI-artifact certification."));
    }
    private static boolean network(Scenario s) {
        return s.brand() == Brand.VISA || s.brand() == Brand.MASTERCARD;
    }
    private static boolean requestOperation(Scenario s) {
        Operation operation = s.phase() == Phase.ORIGINAL ? s.operation() : s.updaterRequestOperation();
        return operation != null && Set.of(Operation.AUTH, Operation.SALE, Operation.REFUND).contains(operation);
    }
    private static boolean qualifyingContext(Scenario s) {
        return Boolean.TRUE.equals(s.credentialOnFile()) || Boolean.TRUE.equals(s.installment())
                || Boolean.TRUE.equals(s.recurring());
    }
    private static boolean unknownContext(Scenario s) {
        return s.credentialOnFile() == null || s.installment() == null || s.recurring() == null;
    }
    private static Assessment participation(Scenario s) {
        if (!s.requestData().containsKey("01") && s.responseData().isEmpty()) return na("PARTICIPATION-SCOPE");
        if (Boolean.FALSE.equals(s.cardNotPresent()) || Boolean.FALSE.equals(s.acquirerParticipating())
                || Boolean.FALSE.equals(s.merchantRegistered()) || Boolean.FALSE.equals(s.merchantCertified())
                || Boolean.FALSE.equals(s.securelyStored())) {
            return result("PARTICIPATION-SCOPE", Status.FAIL,
                    "Updater requires CNP, participating acquirer, First Data-registered/certified merchant and secure storage.");
        }
        if (!qualifyingContext(s) && !unknownContext(s)) return result("PARTICIPATION-SCOPE", Status.FAIL,
                "Appendix AD requires COF, installment or recurring context.");
        if (s.cardNotPresent() == null || s.acquirerParticipating() == null || s.merchantRegistered() == null
                || s.merchantCertified() == null || !qualifyingContext(s) || s.securelyStored() == null) {
            return result("PARTICIPATION-SCOPE", Status.REVIEW_REQUIRED,
                    "Participation, transaction context or secure account-storage evidence is incomplete.");
        }
        return result("PARTICIPATION-SCOPE", Status.PASS, "Supplied context matches Appendix AD; actual enrollment is external.");
    }
    private static Assessment trigger(Scenario s) {
        if (s.phase() != Phase.ORIGINAL || !requestOperation(s)) return na("TRIGGER-INDICATOR");
        if (s.requestData().containsKey("02")) {
            if (s.requestData().containsKey("01")) return result("TRIGGER-INDICATOR", Status.FAIL,
                    "Request indicators 01 and 02 are mutually exclusive.");
            return result("TRIGGER-INDICATOR", Status.REVIEW_REQUIRED,
                    "Explicit override opts out of optimization; reconcile Appendix AD all-request guidance with the chosen override.");
        }
        if (Boolean.FALSE.equals(s.merchantRegistered()) || Boolean.FALSE.equals(s.merchantCertified())) {
            return na("TRIGGER-INDICATOR");
        }
        if (!qualifyingContext(s) && !unknownContext(s)) return na("TRIGGER-INDICATOR");
        if (s.merchantRegistered() == null || s.merchantCertified() == null || !qualifyingContext(s)) {
            return result("TRIGGER-INDICATOR", Status.REVIEW_REQUIRED,
                    "Merchant participation and COF/installment/recurring evidence determine trigger applicability.");
        }
        return condition("TRIGGER-INDICATOR", Set.of("Y", "I").contains(s.requestData().getOrDefault("01", "")),
                "Participating request includes Y (all available updates) or I (indicators only).",
                "Participating COF/installment/recurring request needs Table 060 sub-table 01 Y or I.");
    }
    private static Assessment requestLayout(Scenario s) {
        if (s.requestData().isEmpty()) return na("REQUEST-LAYOUT");
        int bytes = 0;
        boolean unknown = false;
        for (var entry : s.requestData().entrySet()) {
            String id = entry.getKey();
            String value = entry.getValue();
            if (!id.matches("[0-9]{2}") || Integer.parseInt(id) < 1 || Integer.parseInt(id) > 25
                    || value.isEmpty() || value.length() > 25 || !value.matches("[A-Za-z0-9]+")) {
                return result("REQUEST-LAYOUT", Status.FAIL, "Sub-table ID is 01-25; data is 1-25 ASCII alphanumeric bytes.");
            }
            bytes += 4 + value.length();
            if ("01".equals(id) && !Set.of("Y", "I").contains(value)
                    || "02".equals(id) && !"O".equals(value)) return result("REQUEST-LAYOUT", Status.FAIL,
                    "Sub-table 01 length 01 uses Y/I; sub-table 02 length 01 uses O.");
            unknown |= !"01".equals(id) && !"02".equals(id);
        }
        if (bytes > 100) return result("REQUEST-LAYOUT", Status.FAIL,
                "Table 060 data exceeds 100 bytes including two-digit ID/length headers.");
        if (s.requestData().containsKey("01") && s.requestData().containsKey("02")) {
            return result("REQUEST-LAYOUT", Status.FAIL, "Sub-tables 01 and 02 cannot coexist.");
        }
        if ((s.requestData().containsKey("01") || s.requestData().containsKey("02"))
                && (!network(s) || !requestOperation(s)
                && (s.phase() == Phase.ORIGINAL || s.updaterRequestOperation() != null)
                || Boolean.FALSE.equals(s.cardNotPresent()))) {
            return result("REQUEST-LAYOUT", Status.FAIL, "Indicators are Visa/MasterCard CNP Auth/Sale/Refund only.");
        }
        if (unknown || s.cardNotPresent() == null
                || s.phase() != Phase.ORIGINAL && s.updaterRequestOperation() == null) {
            return result("REQUEST-LAYOUT", Status.REVIEW_REQUIRED,
                    "Unknown sub-table semantics or missing CNP/original request-operation evidence; map size is not full wire validation.");
        }
        return result("REQUEST-LAYOUT", Status.PASS, "Known request fields and reconstructed byte bound match.");
    }
    private static Assessment override(Scenario s) {
        if (!s.requestData().containsKey("02")) return na("OVERRIDE");
        if (!"O".equals(s.requestData().get("02")) || s.requestData().containsKey("01")) return result("OVERRIDE",
                Status.FAIL, "Override uses O without indicator 01.");
        if (Boolean.TRUE.equals(s.optimizerUsed()) || !s.responseData().isEmpty()) return result("OVERRIDE",
                Status.FAIL, "O means do not perform Auth Optimization; Segment 155 requires the service to be used.");
        if (s.optimizerUsed() == null) return result("OVERRIDE", Status.REVIEW_REQUIRED,
                "Actual optimizer behavior is not evidenced.");
        return result("OVERRIDE", Status.PASS, "Supplied behavior records no optimization after override.");
    }
    private static Assessment responseLayout(Scenario s) {
        return assessResponseLayout(s.responseData());
    }

    public static Assessment assessResponseLayout(Map<String, String> responseData) {
        Objects.requireNonNull(responseData, "responseData");
        if (responseData.isEmpty()) return na("RESPONSE-LAYOUT");
        boolean review = false;
        for (var entry : responseData.entrySet()) {
            String id = entry.getKey();
            String value = entry.getValue();
            if (id == null || value == null) return result("RESPONSE-LAYOUT", Status.FAIL, "Table ID and data are required.");
            if (!id.matches("[0-9]{3}")) return result("RESPONSE-LAYOUT", Status.FAIL, "Segment 155 Table ID has three digits.");
            if (!value.matches("[A-Za-z0-9]+")) return result("RESPONSE-LAYOUT", Status.FAIL,
                    "Segment 155 logical data must be nonempty ASCII alphanumeric.");
            switch (id) {
                case "001", "002" -> {
                    if (value.length() > 19) return result("RESPONSE-LAYOUT", Status.FAIL, "Updated PAN/token maximum is 19 bytes.");
                }
                case "003" -> {
                    if (!value.matches("[0-9]{2}(0[1-9]|1[0-2])")) return result("RESPONSE-LAYOUT", Status.FAIL,
                            "Updated expiration is four digits in YYMM, not MMYY.");
                }
                case "004" -> {
                    if (!CARD_STATUSES.contains(value)) return result("RESPONSE-LAYOUT", Status.FAIL,
                            "Card status is A/E/Q/C/U.");
                }
                case "005" -> {
                    if (value.length() > 4) return result("RESPONSE-LAYOUT", Status.FAIL,
                            "Original Association Response Code exceeds Table Data maximum 4.");
                    review = true;
                }
                case "006" -> {
                    if (value.length() != 6) return result("RESPONSE-LAYOUT", Status.FAIL, "Result code is six alphanumeric bytes.");
                }
                default -> review = true;
            }
        }
        if (review) return result("RESPONSE-LAYOUT", Status.REVIEW_REQUIRED,
                "Table 005 says variable length 3 but data maximum 4; unknown table layouts also need a contract. "
                        + "Fields are optional; source does not define mandatory status-to-field combinations.");
        return result("RESPONSE-LAYOUT", Status.PASS, "Known logical field widths/values match; concatenated TLV and segment length remain separate.");
    }
    private static Assessment responseGates(Scenario s) {
        if (s.responseData().isEmpty()) return na("RESPONSE-GATES");
        String indicator = s.requestData().get("01");
        if (s.requestData().containsKey("02") || !Set.of("Y", "I").contains(indicator == null ? "" : indicator)
                || Boolean.FALSE.equals(s.optimizerQualified()) || Boolean.FALSE.equals(s.optimizerUsed())) {
            return result("RESPONSE-GATES", Status.FAIL, "Segment 155 requires qualified/used optimizer service and request opt-in Y/I.");
        }
        for (String id : s.responseData().keySet()) {
            if (Set.of("001", "002", "003").contains(id) && !"Y".equals(indicator)) return result("RESPONSE-GATES",
                    Status.FAIL, "Actual account/token/expiry updates require Y; I returns indicators only.");
            if (Set.of("001", "002", "003", "004", "005").contains(id) && !network(s)
                    || "006".equals(id) && s.brand() != Brand.VISA) return result("RESPONSE-GATES", Status.FAIL,
                    "Tables 001-005 apply to Visa/MasterCard; Table 006 is Visa-only.");
        }
        if (s.responseData().containsKey("001") && s.transArmor() || s.responseData().containsKey("002") && !s.transArmor()) {
            return result("RESPONSE-GATES", Status.FAIL, "001 is not returned for TransArmor; 002 is not returned for PAN transactions.");
        }
        if (s.responseData().containsKey("005")
                && (Boolean.FALSE.equals(s.networkDeclined()) || Boolean.FALSE.equals(s.networkReattempted()))) {
            return result("RESPONSE-GATES", Status.FAIL, "005 requires network decline followed by optimizer-directed network reattempt.");
        }
        if (s.optimizerQualified() == null || s.optimizerUsed() == null
                || s.responseData().containsKey("005") && (s.networkDeclined() == null || s.networkReattempted() == null)
                || s.responseData().keySet().stream().anyMatch(id -> !Set.of("001", "002", "003", "004", "005", "006").contains(id))) {
            return result("RESPONSE-GATES", Status.REVIEW_REQUIRED, "Service/provenance evidence or unknown-table gate is incomplete.");
        }
        return result("RESPONSE-GATES", Status.PASS, "Supplied service, indicator, network and credential-type gates match.");
    }
    private static Assessment resultCode(Scenario s) {
        String value = s.responseData().get("006");
        if (value == null) return na("RESULT-CODE");
        if (!RESULT_CODES.containsKey(value)) return result("RESULT-CODE", Status.FAIL,
                "Result is not in the 15 listed VAU codes; VAU015 is not assigned in this release.");
        return result("RESULT-CODE", Status.PASS, RESULT_CODES.get(value)
                + "; recognizing this reason does not infer transaction approval or independently reproduce issuer eligibility.");
    }
    private static Assessment credentials(Scenario s) {
        if (s.phase() == Phase.ORIGINAL) return na("ACCOUNT-DEPENDENCY");
        Credential sent = s.sentCredential();
        if (sent == null) return result("ACCOUNT-DEPENDENCY", Status.REVIEW_REQUIRED,
                "Parsed original and sent credentials are needed; Element 12 application layouts and TAP framing are separate.");
        if (!validCredential(sent)) return result("ACCOUNT-DEPENDENCY", Status.FAIL,
                "Sent account/expiration evidence must be nonblank with valid MMYY.");
        Credential original = s.originalCredential();
        if (original == null || !validCredential(original)) return result("ACCOUNT-DEPENDENCY", Status.REVIEW_REQUIRED,
                "Original credential evidence is incomplete or malformed.");
        if (sent.equals(original)) return result("ACCOUNT-DEPENDENCY", Status.PASS, "Original account and matching original expiry are reused.");
        String newAccount = s.responseData().get(s.transArmor() ? "002" : "001");
        String newExpiry = s.responseData().get("003");
        if (newAccount == null && newExpiry == null) return result("ACCOUNT-DEPENDENCY", Status.FAIL,
                "Sent credentials differ from original with no supplied account-update evidence.");
        if (newExpiry != null && !newExpiry.matches("[0-9]{2}(0[1-9]|1[0-2])")) return result("ACCOUNT-DEPENDENCY",
                Status.FAIL, "Response expiration evidence must use YYMM.");
        String account = newAccount == null ? original.account() : newAccount;
        if (!account.equals(sent.account())) return result("ACCOUNT-DEPENDENCY", Status.FAIL,
                "Follow-on must use original or supplied updated PAN/token.");
        if (newExpiry == null) return result("ACCOUNT-DEPENDENCY", Status.REVIEW_REQUIRED,
                "Updated account's corresponding expiry is not evidenced; do not assume the original expiry applies.");
        String mmyy = newExpiry.substring(2) + newExpiry.substring(0, 2);
        return condition("ACCOUNT-DEPENDENCY", mmyy.equals(sent.expirationMmyy()),
                "Updated account/expiry evidence is paired, with YYMM converted to logical Element 12 MMYY.",
                "Sent expiration does not correspond to the chosen updated credential evidence.");
    }
    private static boolean validCredential(Credential c) {
        return c.account() != null && !c.account().isBlank() && c.expirationMmyy() != null
                && c.expirationMmyy().matches("(0[1-9]|1[0-2])[0-9]{2}");
    }
    private static Assessment retry(Scenario s) {
        if (s.phase() != Phase.RETRY) return na("RETRY-CREDENTIALS");
        if (s.sentCredential() == null || s.initialAttemptCredential() == null) return result("RETRY-CREDENTIALS",
                Status.REVIEW_REQUIRED, "Initial follow-on attempt and retry credential evidence is absent.");
        if (!validCredential(s.sentCredential()) || !validCredential(s.initialAttemptCredential())) return result("RETRY-CREDENTIALS",
                Status.FAIL, "Retry comparison requires complete account/expiry evidence.");
        return condition("RETRY-CREDENTIALS", s.sentCredential().equals(s.initialAttemptCredential()),
                "Retry retains the same credentials used in the initial follow-on attempt.",
                "Retry must not switch PAN/token or expiration from the initial follow-on attempt.");
    }
    private static Assessment condition(String rule, boolean valid, String pass, String fail) {
        return result(rule, valid ? Status.PASS : Status.FAIL, valid ? pass : fail);
    }
    private static Assessment na(String rule) {
        return result(rule, Status.PASS, "Not applicable/absent in this supplied context; not transaction approval or updater certification.");
    }
    private static Assessment result(String rule, Status status, String reason) {
        return new Assessment("BR-SEG100-APPAD-" + rule, status, reason);
    }
}
