package com.coreauth.validator.canonical;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Logical Appendix AB checks; program enrollment and message lifecycle require external evidence. */
public final class AppendixABPurchaseRepaymentOracle {
    public enum Status { PASS, FAIL, REVIEW_REQUIRED }
    public enum Brand { VISA, MASTERCARD, DISCOVER, AMEX, OTHER }
    public record Scenario(Brand brand, boolean purchaseRepayment, Boolean installment,
                           Boolean thirdPartyProvider, Boolean paymentFacilitator,
                           Boolean providerDistinctFromRetailer, String businessPaymentType,
                           String installmentIndicator, String merchantName, String providerName,
                           String retailerName, String adviceAcceptance, boolean responseObserved,
                           Boolean declined, Boolean programRegistered, String merchantAdviceCode,
                           Boolean retryAttempted) {
        public Scenario {
            Objects.requireNonNull(brand, "brand");
        }
    }
    public record Assessment(String businessRequirementId, Status status, String reason) { }
    public static final List<String> RULES = List.of("DEFINITION", "VALUE-SCOPE",
            "MASTERCARD-INSTALLMENT-INDICATOR", "MASTERCARD-MERCHANT-NAME", "ADVICE-CODE",
            "NO-RETRY", "EXTERNAL-EVIDENCE");
    private static final Set<String> NO_RETRY = Set.of("03", "21", "22");

    public List<Assessment> assess(Scenario s) {
        Objects.requireNonNull(s, "scenario");
        return List.of(definition(s), scope(s), installment(s), descriptor(s), advice(s), retry(s),
                result("EXTERNAL-EVIDENCE", Status.REVIEW_REQUIRED,
                        "Synthetic fields do not certify third-party identity, program enrollment, authorizer "
                                + "outcome, actual retry behavior, full wire messages or AI artifacts."));
    }
    private static Assessment definition(Scenario s) {
        if (!s.purchaseRepayment()) return na("DEFINITION");
        if (Boolean.FALSE.equals(s.installment())) return result("DEFINITION", Status.FAIL,
                "Appendix AB defines Purchase Repayment as installment payments.");
        if (Boolean.TRUE.equals(s.thirdPartyProvider()) && Boolean.FALSE.equals(s.providerDistinctFromRetailer())) {
            return result("DEFINITION", Status.FAIL, "A third-party installment provider is different from the retailer.");
        }
        if (Boolean.FALSE.equals(s.thirdPartyProvider()) && Boolean.FALSE.equals(s.paymentFacilitator())) {
            return result("DEFINITION", Status.FAIL, "Repayments are submitted by third-party providers or payment facilitators.");
        }
        boolean provider = Boolean.TRUE.equals(s.thirdPartyProvider())
                && Boolean.TRUE.equals(s.providerDistinctFromRetailer());
        if (!Boolean.TRUE.equals(s.installment()) || !(provider || Boolean.TRUE.equals(s.paymentFacilitator()))) {
            return result("DEFINITION", Status.REVIEW_REQUIRED, "Installment and submitter identity evidence is incomplete.");
        }
        return result("DEFINITION", Status.PASS, "Supplied logical context matches the definition, including post-purchase/BNPL use.");
    }
    private static Assessment scope(Scenario s) {
        String value = s.businessPaymentType();
        if (value != null) {
            if (!"CB".equals(value) && !"IR".equals(value)) return result("VALUE-SCOPE", Status.FAIL,
                    "Table 049 sub-table 04 lists CB (Consumer Bill Payment) and IR (Purchase Repayment).");
            if ("CB".equals(value) && s.brand() != Brand.VISA || "IR".equals(value) && s.brand() != Brand.MASTERCARD) {
                return result("VALUE-SCOPE", Status.FAIL, "CB is Visa-only; IR is MasterCard-only.");
            }
        }
        if (s.purchaseRepayment() && s.brand() == Brand.MASTERCARD) {
            return condition("VALUE-SCOPE", "IR".equals(value), "MasterCard repayment is identified as IR.",
                    "MasterCard Purchase Repayment requires the IR business payment type.");
        }
        if (s.purchaseRepayment()) return result("VALUE-SCOPE", Status.REVIEW_REQUIRED,
                "Appendix AB is a definition, not a network allowlist. CB denotes Consumer Bill Payment; "
                        + "do not invent a Visa repayment mapping or extend IR to other brands.");
        if (value == null) return na("VALUE-SCOPE");
        return result("VALUE-SCOPE", Status.PASS, "Supplied business payment type matches its network scope.");
    }
    private static boolean masterRepayment(Scenario s) {
        return s.purchaseRepayment() && s.brand() == Brand.MASTERCARD;
    }
    private static Assessment installment(Scenario s) {
        if (!masterRepayment(s)) return na("MASTERCARD-INSTALLMENT-INDICATOR");
        return condition("MASTERCARD-INSTALLMENT-INDICATOR", "I".equals(s.installmentIndicator()),
                "Table 013 contains I.", "MasterCard Purchase Repayment must submit Table 013 value I.");
    }
    private static Assessment descriptor(Scenario s) {
        if (!masterRepayment(s)) return na("MASTERCARD-MERCHANT-NAME");
        String name = s.merchantName();
        if (name == null || name.isBlank()) return result("MASTERCARD-MERCHANT-NAME", Status.FAIL,
                "Table 047 merchant name is required.");
        if (name.chars().anyMatch(c -> c > 127)) return result("MASTERCARD-MERCHANT-NAME", Status.REVIEW_REQUIRED,
                "Non-ASCII Table 047 needs a specified wire encoding to enforce 38 bytes.");
        if (name.length() > 38 || name.chars().anyMatch(c -> c < 32 || c == 127)) {
            return result("MASTERCARD-MERCHANT-NAME", Status.FAIL, "Table 047 requires printable data up to 38 bytes.");
        }
        int separator = name.indexOf('*');
        if (separator < 1 || name.substring(0, separator).isBlank() || name.substring(separator + 1).isBlank()) {
            return result("MASTERCARD-MERCHANT-NAME", Status.FAIL, "Required format is Installment Provider name*Merchant name.");
        }
        if (s.providerName() != null && (s.providerName().isBlank() || !name.startsWith(s.providerName() + "*"))
                || s.retailerName() != null && (s.retailerName().isBlank() || !name.endsWith("*" + s.retailerName()))) {
            return result("MASTERCARD-MERCHANT-NAME", Status.FAIL,
                    "Descriptor conflicts with supplied provider or retailer evidence.");
        }
        if (s.providerName() != null && s.retailerName() != null) {
            return condition("MASTERCARD-MERCHANT-NAME",
                    name.equals(s.providerName() + "*" + s.retailerName()),
                    "Descriptor matches supplied provider and retailer evidence.",
                    "Descriptor differs from supplied provider/retailer names or their order.");
        }
        return result("MASTERCARD-MERCHANT-NAME", Status.REVIEW_REQUIRED,
                "Descriptor shape matches, but provider/retailer identity evidence is incomplete.");
    }
    private static Assessment advice(Scenario s) {
        String code = s.merchantAdviceCode();
        if (s.adviceAcceptance() != null && !"Y".equals(s.adviceAcceptance())) return result("ADVICE-CODE",
                Status.FAIL, "Table 049 sub-table 07 lists only Y; opt out by omitting it.");
        if (!s.responseObserved()) {
            if (code != null) return result("ADVICE-CODE", Status.FAIL, "Advice code requires response evidence.");
            return result("ADVICE-CODE", Status.REVIEW_REQUIRED, "Request alone cannot certify the authorizer's response.");
        }
        if (code != null) {
            if (!code.matches("[A-Za-z0-9]{2}")) return result("ADVICE-CODE", Status.FAIL,
                    "Segment 112 Table 030 advice code must be two alphanumeric bytes.");
            if (s.brand() != Brand.MASTERCARD || !"Y".equals(s.adviceAcceptance())) return result("ADVICE-CODE",
                    Status.FAIL, "Merchant Advice Code is MasterCard-only and requires request acceptance Y.");
        }
        if (masterRepayment(s) && "Y".equals(s.adviceAcceptance())) {
            if (s.declined() == null || Boolean.TRUE.equals(s.declined()) && s.programRegistered() == null) {
                return result("ADVICE-CODE", Status.REVIEW_REQUIRED, "Decline/enrollment evidence is incomplete.");
            }
            if (Boolean.TRUE.equals(s.declined()) && Boolean.FALSE.equals(s.programRegistered())) {
                return condition("ADVICE-CODE", "22".equals(code), "Unregistered repayment decline returns 22.",
                        "An unregistered repayment decline with acceptance Y must return advice 22.");
            }
        }
        if (code != null) return result("ADVICE-CODE", Status.REVIEW_REQUIRED,
                "Advice framing/acceptance passed; other and future codes require authorizer-specific evidence. "
                        + "Code 22 does not independently prove lack of registration.");
        return result("ADVICE-CODE", Status.PASS,
                "No advice was supplied and no evidenced unregistered opt-in repayment decline requires 22.");
    }
    private static Assessment retry(Scenario s) {
        if (s.merchantAdviceCode() == null) return na("NO-RETRY");
        if (!s.responseObserved() || s.brand() != Brand.MASTERCARD || !"Y".equals(s.adviceAcceptance())) {
            return result("NO-RETRY", Status.REVIEW_REQUIRED, "Advice provenance/acceptance must be established first.");
        }
        if (!NO_RETRY.contains(s.merchantAdviceCode())) return result("NO-RETRY", Status.REVIEW_REQUIRED,
                "Other/future advice codes must not be assumed retryable; Appendix K has separate retry policies.");
        if (s.retryAttempted() == null) return result("NO-RETRY", Status.REVIEW_REQUIRED, "Actual retry behavior evidence is absent.");
        return condition("NO-RETRY", !s.retryAttempted(), "No retry after advice 03/21/22.",
                "Merchant must not retry after advice 03, 21 or 22.");
    }
    private static Assessment condition(String rule, boolean valid, String pass, String fail) {
        return result(rule, valid ? Status.PASS : Status.FAIL, valid ? pass : fail);
    }
    private static Assessment na(String rule) {
        return result(rule, Status.PASS, "Not applicable to this supplied context; not evidence of transaction approval.");
    }
    private static Assessment result(String rule, Status status, String reason) {
        return new Assessment("BR-SEG100-APPAB-" + rule, status, reason);
    }
}
