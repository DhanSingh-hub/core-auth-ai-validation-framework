package com.coreauth.validator.canonical;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.MonthDay;
import java.util.List;
import java.util.Objects;

/** Source-bounded logical series checks; not a Visa merchant-eligibility or full message engine. */
public final class AppendixAEIncrementalAuthorizationOracle {
    public enum Status { PASS, FAIL, REVIEW_REQUIRED }
    public enum Stage { INITIAL, INCREMENTAL, COMPLETION }
    public enum MerchantCategory { CRUISE, LODGING, VEHICLE_RENTAL, OTHER_RENTAL, OTHER }
    public record Scenario(String brand, String mcc, MerchantCategory category, Stage stage,
                           Boolean merchantPermitted, Boolean cardNotPresent, Boolean evTransaction,
                           Boolean finalAmountUnknown, String estimatedIndicator,
                           String sequenceNumber, String transactionIdentifier, String table063,
                           Boolean precedingInitialAuthorization, String initialSequenceNumber,
                           String initialTransactionIdentifier, String initialTable063,
                           String approvalCode, String originalApprovalCode, Boolean hostIncrementalIndicatorSent,
                           String finalAmount, String completionAmount,
                           BigDecimal elapsedDaysFromInitial, BigDecimal completedAtDays,
                           Boolean stayExceedsWindow, BigDecimal nextInitialAtDays,
                           Boolean terminalNumberUnique, Boolean sameMerchantAndCardholder,
                           String initialSettlementType, String pinlessDebitAcceptance) {
        public Scenario {
            Objects.requireNonNull(category, "category");
            Objects.requireNonNull(stage, "stage");
        }
    }
    public record Assessment(String businessRequirementId, Status status, String reason) { }
    public static final List<String> RULES = List.of("MCC-SCOPE", "INITIAL-INDICATOR",
            "INCREMENTAL-CONTINUITY", "HOST-INDICATOR", "LODGING-DATA", "FINAL-AMOUNT",
            "EXPIRATION-WINDOW", "LONG-STAY-RESTART", "SIGNATURE-DEBIT", "EXTERNAL-EVIDENCE");

    public List<Assessment> assess(Scenario s) {
        Objects.requireNonNull(s, "scenario");
        return List.of(scope(s), indicator(s), continuity(s), host(s), lodging(s), amount(s),
                window(s), restart(s), debit(s), result("EXTERNAL-EVIDENCE", Status.REVIEW_REQUIRED,
                        "Merchant permission/MCC classification, original approvals, series identity, host-to-Visa "
                                + "indicator, actual completion and chargeback outcomes need authoritative evidence. "
                                + "AE omits the Transaction Identifier segment number; wire placement, calendar cutoff "
                                + "policy and full envelopes remain reviewed."));
    }
    private static Assessment scope(Scenario s) {
        if (!"VISA".equals(s.brand())) return result("MCC-SCOPE", Status.FAIL, "This is the Visa Appendix AE profile.");
        if (s.mcc() == null || !s.mcc().matches("[0-9]{4}")) return result("MCC-SCOPE", Status.FAIL,
                "Supplied MCC evidence requires four ASCII digits.");
        if (Boolean.FALSE.equals(s.merchantPermitted())) return result("MCC-SCOPE", Status.FAIL, "Merchant is explicitly not permitted.");
        if (("4121".equals(s.mcc()) || "5411".equals(s.mcc())) && Boolean.FALSE.equals(s.cardNotPresent())) {
            return result("MCC-SCOPE", Status.FAIL, "MCC 4121 and 5411 are CNP-only.");
        }
        if ("5552".equals(s.mcc()) && Boolean.FALSE.equals(s.evTransaction())) return result("MCC-SCOPE", Status.FAIL,
                "MCC 5552 rule is for EV transactions.");
        if (s.merchantPermitted() == null
                || ("4121".equals(s.mcc()) || "5411".equals(s.mcc())) && s.cardNotPresent() == null
                || "5552".equals(s.mcc()) && s.evTransaction() == null) {
            return result("MCC-SCOPE", Status.REVIEW_REQUIRED, "Merchant permission or special MCC context evidence is absent.");
        }
        return result("MCC-SCOPE", Status.PASS,
                "Supplied permission and special restrictions match; AE does not provide an exhaustive MCC allowlist.");
    }
    private static Assessment indicator(Scenario s) {
        if (s.stage() == Stage.INCREMENTAL) return condition("INITIAL-INDICATOR", s.estimatedIndicator() == null,
                "Incremental request omits sub-table 09.", "Incremental must not resend the estimated indicator, including blank data.");
        if (s.stage() == Stage.COMPLETION) return na("INITIAL-INDICATOR");
        if (s.estimatedIndicator() != null && !"1".equals(s.estimatedIndicator())) return result("INITIAL-INDICATOR",
                Status.FAIL, "Acquirer Trace Data sub-table 09 length 001 has only value 1.");
        if (Boolean.TRUE.equals(s.finalAmountUnknown())) return condition("INITIAL-INDICATOR", "1".equals(s.estimatedIndicator()),
                "Unknown final amount uses estimated indicator 1.", "Unknown final amount requires estimated indicator 1.");
        if (s.finalAmountUnknown() == null) return result("INITIAL-INDICATOR", Status.REVIEW_REQUIRED, "Final-amount knowledge is not evidenced.");
        return result("INITIAL-INDICATOR", Status.REVIEW_REQUIRED,
                "Source distinguishes estimated/initial authorization but does not fully specify known-final-amount initial variants.");
    }
    private static boolean sequence(String value) {
        return value != null && value.matches("[0-9]{6}");
    }
    private static boolean identifier(String value) {
        return value != null && value.matches("[A-Za-z0-9]{15}");
    }
    private static Assessment continuity(Scenario s) {
        if (s.stage() != Stage.INCREMENTAL) return na("INCREMENTAL-CONTINUITY");
        if (Boolean.FALSE.equals(s.precedingInitialAuthorization()) || Boolean.FALSE.equals(s.sameMerchantAndCardholder())) {
            return result("INCREMENTAL-CONTINUITY", Status.FAIL, "Incremental requires a preceding initial authorization in the same series.");
        }
        if (!sequence(s.sequenceNumber()) || !identifier(s.transactionIdentifier()) || !valid063(s.table063())) {
            return result("INCREMENTAL-CONTINUITY", Status.FAIL, "Incremental sequence, logical identifier and Table 063 must be present/well formed.");
        }
        if (s.initialSequenceNumber() != null && !s.sequenceNumber().equals(s.initialSequenceNumber())
                || s.initialTransactionIdentifier() != null && !s.transactionIdentifier().equals(s.initialTransactionIdentifier())
                || s.initialTable063() != null && valid063(s.initialTable063())
                && !terminal(s.table063()).equals(terminal(s.initialTable063()))) {
            return result("INCREMENTAL-CONTINUITY", Status.FAIL, "Sequence, Transaction Identifier and terminal number must equal initial values.");
        }
        if (s.precedingInitialAuthorization() == null || s.sameMerchantAndCardholder() == null
                || !sequence(s.initialSequenceNumber()) || !identifier(s.initialTransactionIdentifier())
                || !valid063(s.initialTable063())) return result("INCREMENTAL-CONTINUITY", Status.REVIEW_REQUIRED,
                "Original-series evidence is incomplete; exact Transaction Identifier wire mapping is unresolved.");
        return result("INCREMENTAL-CONTINUITY", Status.PASS, "Three logical series identifiers match the initial authorization.");
    }
    private static Assessment host(Scenario s) {
        if (s.stage() != Stage.INCREMENTAL) return na("HOST-INDICATOR");
        if (s.approvalCode() == null || s.approvalCode().isBlank()) return result("HOST-INDICATOR", Status.FAIL,
                "Merchant sends returned approval code in Authorization Identification Response; host supplies Visa incremental indicator.");
        if (s.originalApprovalCode() != null && !s.approvalCode().equals(s.originalApprovalCode())) return result("HOST-INDICATOR",
                Status.FAIL, "Sent approval code differs from supplied returned approval evidence.");
        if (Boolean.FALSE.equals(s.hostIncrementalIndicatorSent())) return result("HOST-INDICATOR", Status.FAIL,
                "Host must send incremental indicator to Visa after identifying the incremental request.");
        if (s.originalApprovalCode() == null || s.hostIncrementalIndicatorSent() == null) return result("HOST-INDICATOR",
                Status.REVIEW_REQUIRED, "Returned approval and actual host behavior require evidence; no merchant-side incremental tag is invented.");
        return result("HOST-INDICATOR", Status.PASS, "Approval reuse and host incremental-indicator behavior match supplied evidence.");
    }
    private static String terminal(String value) {
        return value.substring(6, 10);
    }
    private static boolean valid063(String value) {
        if (value == null || value.length() != 11) return false;
        String arrival = value.substring(0, 4);
        if (!"    ".equals(arrival)) {
            if (!arrival.matches("[0-9]{4}")) return false;
            try {
                MonthDay.of(Integer.parseInt(arrival.substring(0, 2)), Integer.parseInt(arrival.substring(2)));
            } catch (DateTimeException e) {
                return false;
            }
        }
        return (value.substring(4, 6).equals("  ") || value.substring(4, 6).matches("[0-9]{2}"))
                && terminal(value).matches("[0-9]{4}") && (value.charAt(10) == 'P' || value.charAt(10) == ' ');
    }
    private static Assessment lodging(Scenario s) {
        boolean required = s.category() == MerchantCategory.LODGING || s.stage() == Stage.INCREMENTAL
                || Boolean.TRUE.equals(s.evTransaction());
        if (s.table063() == null && !required) return na("LODGING-DATA");
        if (!valid063(s.table063())) return result("LODGING-DATA", Status.FAIL,
                "Table 063 data is 11 bytes: MMDD/spaces(4), duration/spaces(2), terminal number(4), P/space(1).");
        if (Boolean.FALSE.equals(s.terminalNumberUnique()) && s.stage() == Stage.INITIAL) return result("LODGING-DATA",
                Status.FAIL, "New series terminal transaction number should be unique.");
        if (s.stage() != Stage.INITIAL) {
            if (s.initialTable063() == null || !valid063(s.initialTable063())) return result("LODGING-DATA",
                    Status.REVIEW_REQUIRED, "Initial terminal number evidence is required for series continuity.");
            if (!terminal(s.table063()).equals(terminal(s.initialTable063()))) return result("LODGING-DATA", Status.FAIL,
                    "Terminal transaction number remains unchanged until the incremental series completes.");
        } else if (s.terminalNumberUnique() == null) return result("LODGING-DATA", Status.REVIEW_REQUIRED,
                "Uniqueness across series requires merchant history.");
        return result("LODGING-DATA", Status.PASS, "Table 063 logical layout and supplied uniqueness/series evidence match.");
    }
    private static Assessment amount(Scenario s) {
        if (s.stage() != Stage.COMPLETION) return na("FINAL-AMOUNT");
        if (s.completionAmount() == null || !s.completionAmount().matches("[0-9]+")) return result("FINAL-AMOUNT",
                Status.FAIL, "Completion must send the final amount; logical evidence here uses nonnegative minor-unit digits.");
        if (s.finalAmount() == null || !s.finalAmount().matches("[0-9]+")) return result("FINAL-AMOUNT",
                Status.REVIEW_REQUIRED, "Actual final amount evidence is absent or malformed.");
        return condition("FINAL-AMOUNT", new BigDecimal(s.finalAmount()).compareTo(new BigDecimal(s.completionAmount())) == 0,
                "Completion amount equals supplied final amount.", "Completion differs from final amount.");
    }
    private static Integer days(Scenario s) {
        return switch (s.category()) {
            case CRUISE, LODGING, VEHICLE_RENTAL -> 14;
            case OTHER_RENTAL -> 7;
            case OTHER -> null;
        };
    }
    private static Assessment window(Scenario s) {
        if (s.stage() == Stage.INITIAL) return na("EXPIRATION-WINDOW");
        Integer limit = days(s);
        if (limit == null) return result("EXPIRATION-WINDOW", Status.REVIEW_REQUIRED,
                "AE does not give a window for this merchant category; do not generalize seven days to all other merchants.");
        BigDecimal age = s.elapsedDaysFromInitial();
        if (age == null) return result("EXPIRATION-WINDOW", Status.REVIEW_REQUIRED, "Elapsed days from INITIAL authorization are absent.");
        if (age.signum() < 0) return result("EXPIRATION-WINDOW", Status.FAIL, "Follow-on time cannot precede initial authorization.");
        return condition("EXPIRATION-WINDOW", age.compareTo(BigDecimal.valueOf(limit)) <= 0,
                "Within the source's on-or-before day " + limit + " logical boundary; calendar cutoff still needs policy.",
                "Approval is outside " + limit + " days from initial; incrementals do not restart the clock. Not a prediction of actual chargeback.");
    }
    private static Assessment restart(Scenario s) {
        if (Boolean.FALSE.equals(s.stayExceedsWindow())) return na("LONG-STAY-RESTART");
        if (s.stayExceedsWindow() == null || days(s) == null) return result("LONG-STAY-RESTART",
                Status.REVIEW_REQUIRED, "Long-stay applicability and merchant category need evidence.");
        BigDecimal completion = s.completedAtDays(), next = s.nextInitialAtDays();
        if (completion != null && completion.signum() < 0 || next != null && next.signum() < 0) return result("LONG-STAY-RESTART",
                Status.FAIL, "Completion/new initial chronology cannot precede the first initial authorization.");
        BigDecimal limit = BigDecimal.valueOf(days(s));
        if (completion != null && completion.compareTo(limit) > 0 || next != null && next.compareTo(limit) > 0
                || completion != null && next != null && next.compareTo(completion) < 0) {
            return result("LONG-STAY-RESTART", Status.FAIL, "Complete first transaction then initiate second, both on/before the applicable 7/14-day boundary.");
        }
        if (completion == null || next == null) return result("LONG-STAY-RESTART", Status.REVIEW_REQUIRED,
                "Evidence of both first completion and second initiation is required for extended stays/rentals.");
        return result("LONG-STAY-RESTART", Status.PASS, "Supplied completion then new initial are timely; actual settlement is external.");
    }
    private static Assessment debit(Scenario s) {
        if (s.stage() == Stage.INITIAL) return na("SIGNATURE-DEBIT");
        if (s.initialSettlementType() == null) return result("SIGNATURE-DEBIT", Status.REVIEW_REQUIRED,
                "Original Segment 134 Element 198 settlement-type evidence is absent.");
        if (!"X".equals(s.initialSettlementType())) return na("SIGNATURE-DEBIT");
        return condition("SIGNATURE-DEBIT", "X".equals(s.pinlessDebitAcceptance()),
                "Table 021 PINless Debit Acceptance X retained for non-traditional signature debit.",
                "Associated completion/incremental with initial Settlement Type X must carry Table 021 acceptance X.");
    }
    private static Assessment condition(String rule, boolean valid, String pass, String fail) {
        return result(rule, valid ? Status.PASS : Status.FAIL, valid ? pass : fail);
    }
    private static Assessment na(String rule) {
        return result(rule, Status.PASS, "Not applicable to supplied context; not authorization approval or external certification.");
    }
    private static Assessment result(String rule, Status status, String reason) {
        return new Assessment("BR-SEG100-APPAE-" + rule, status, reason);
    }
}
