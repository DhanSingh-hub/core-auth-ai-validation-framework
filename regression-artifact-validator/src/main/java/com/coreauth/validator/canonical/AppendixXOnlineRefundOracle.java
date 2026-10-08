package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Bounded checks for Appendix X's Online Refund/Refund Authorization Indicator.
 *
 * <p>The indicator is carried in Table 32, Sub-Table 11 of Segment 111, not in the Segment 100
 * payload. This oracle checks the stated scope, field representation, supported card brands, and
 * lifecycle carry-forward. Merchant availability, historical rollout cohorts, and issuer decline
 * decisions require external evidence and remain review-gated.
 */
public final class AppendixXOnlineRefundOracle {

    public enum Status {
        PASS,
        FAIL,
        REVIEW_REQUIRED
    }

    public enum LifecycleStage {
        MERCHANDISE_RETURN,
        REVERSAL,
        FOLLOW_UP,
        TIMEOUT_REVERSAL
    }

    public record Scenario(
            String tableId,
            String subTableId,
            String subTableLength,
            String value,
            String transactionType,
            boolean merchantParticipating,
            boolean onlineApprovalWanted,
            String cardBrand,
            LifecycleStage lifecycleStage,
            Boolean originalReturnHadIndicator,
            Boolean visaPreSettlementAuthorizationRequired,
            Boolean settlementRefundSubmittedBeforeAuthorization) {
    }

    public record Assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
    }

    public static final String BR_TRIGGER = "BR-SEG100-APPX-INDICATOR-TRIGGER";
    public static final String BR_FORMAT_VALUE = "BR-SEG100-APPX-VALID-VALUE";
    public static final String BR_LIFECYCLE = "BR-SEG100-APPX-LIFECYCLE-PERSISTENCE";
    public static final String BR_AVAILABILITY = "BR-SEG100-APPX-FUNCTIONALITY-AVAILABILITY";
    public static final String BR_ROLLOUT = "BR-SEG100-APPX-ROLLOUT-CONTEXT";
    public static final String BR_DECLINE = "BR-SEG100-APPX-DECLINE-DISCRETION";
    public static final String BR_REFUND_NOTIFICATION = "BR-SEG100-APPX-REFUND-NOTIFICATION";
    public static final String BR_VISA_SETTLEMENT_SEQUENCE = "BR-SEG100-APPX-VISA-SETTLEMENT-SEQUENCE";

    private static final String TABLE_ID = "32";
    private static final String SUB_TABLE_ID = "11";
    private static final String SUB_TABLE_LENGTH = "001";
    private static final String INDICATOR_VALUE = "Y";
    private static final String MERCHANDISE_RETURN_TYPE = "7";
    private static final Set<String> SUPPORTED_BRANDS = Set.of("VISA", "MASTERCARD", "AMEX", "DISCOVER");

    public List<Assessment> assess(Scenario scenario) {
        boolean present = scenario.value() != null;
        List<Assessment> assessments = new ArrayList<>();
        assessments.add(assessTrigger(scenario, present));
        assessments.add(assessField(scenario, present));
        assessments.add(assessLifecycle(scenario, present));
        assessments.add(assessVisaSettlementSequence(scenario, present));
        assessments.add(new Assessment(BR_AVAILABILITY, Status.REVIEW_REQUIRED, "merchant-program-availability",
                "Appendix X directs merchants to contact their First Data representative for availability. "
                        + "Program enrollment and merchant eligibility cannot be inferred from the message."));
        assessments.add(new Assessment(BR_ROLLOUT, Status.REVIEW_REQUIRED, "historical-rollout-cohorts",
                "Appendix X gives historical Visa rollout dates (October 2018 for certain previously notified "
                        + "merchants and April 2020 for others), but does not identify a merchant's cohort or "
                        + "current configuration. Do not enforce these dates without external cohort evidence."));
        assessments.add(new Assessment(BR_DECLINE, Status.REVIEW_REQUIRED, "issuer-decline-policy",
                "Appendix X describes issuer-unreachable or invalid-account declines as expected, but explicitly "
                        + "leaves declines for any reason to issuer discretion. No deterministic decline rule "
                        + "can be enforced from this appendix."));
        assessments.add(new Assessment(BR_REFUND_NOTIFICATION, Status.REVIEW_REQUIRED, "refund-pending-notification",
                "Appendix X describes the online authorization as real-time information that a refund is pending "
                        + "for the issuer and/or cardholder. Whether and how that notification is delivered "
                        + "requires issuer/channel behavior outside this indicator payload."));
        return List.copyOf(assessments);
    }

    private static Assessment assessVisaSettlementSequence(Scenario scenario, boolean present) {
        String brand = scenario.cardBrand() == null ? "" : scenario.cardBrand().toUpperCase(Locale.ROOT);
        if (!"VISA".equals(brand)) {
            return result(BR_VISA_SETTLEMENT_SEQUENCE, Status.PASS, "visa-pre-settlement-sequence",
                    "The prior-to-Settlement-System-Refund requirement is Visa-specific; it does not apply "
                            + "to this card brand.");
        }
        if (scenario.lifecycleStage() != LifecycleStage.MERCHANDISE_RETURN) {
            return result(BR_VISA_SETTLEMENT_SEQUENCE, Status.PASS, "visa-pre-settlement-sequence",
                    "The pre-Settlement-System-Refund sequence applies to the original Visa Merchandise Return, "
                            + "not its later lifecycle messages.");
        }
        if (scenario.visaPreSettlementAuthorizationRequired() == null
                || scenario.settlementRefundSubmittedBeforeAuthorization() == null) {
            return result(BR_VISA_SETTLEMENT_SEQUENCE, Status.REVIEW_REQUIRED, "visa-pre-settlement-sequence",
                    "Appendix X describes a prior-to-refund Visa authorization requirement for certain "
                            + "merchants and an April 2020 effective expectation for others. The merchant's "
                            + "applicable cohort and transaction ordering evidence are not supplied.");
        }
        if (!scenario.visaPreSettlementAuthorizationRequired()) {
            return result(BR_VISA_SETTLEMENT_SEQUENCE, Status.REVIEW_REQUIRED, "visa-pre-settlement-sequence",
                    "No pre-settlement requirement was asserted for this merchant, but Appendix X also "
                            + "describes a broader Visa online-authorization expectation effective April 2020. "
                            + "Confirm the current merchant configuration before concluding the rule is inapplicable.");
        }
        if (!present) {
            return result(BR_VISA_SETTLEMENT_SEQUENCE, Status.FAIL, "visa-pre-settlement-sequence",
                    "A merchant subject to the Visa pre-settlement authorization requirement must send the "
                            + "online-refund indicator/authorization before the Settlement System Refund.");
        }
        if (scenario.settlementRefundSubmittedBeforeAuthorization()) {
            return result(BR_VISA_SETTLEMENT_SEQUENCE, Status.FAIL, "visa-pre-settlement-sequence",
                    "Visa online authorization must precede the existing Settlement System Refund for a "
                            + "merchant subject to this requirement.");
        }
        return result(BR_VISA_SETTLEMENT_SEQUENCE, Status.PASS, "visa-pre-settlement-sequence",
                "Visa online authorization precedes the Settlement System Refund for a merchant subject to "
                        + "this requirement.");
    }

    private static Assessment assessTrigger(Scenario scenario, boolean present) {
        if (scenario.lifecycleStage() != LifecycleStage.MERCHANDISE_RETURN) {
            return result(BR_TRIGGER, Status.PASS, "indicator-trigger",
                    "The Appendix X trigger applies to the original Merchandise Return transaction; "
                            + "follow-up presence is assessed separately.");
        }
        if (!MERCHANDISE_RETURN_TYPE.equals(scenario.transactionType())) {
            return result(BR_TRIGGER, present ? Status.REVIEW_REQUIRED : Status.PASS, "indicator-trigger",
                    present
                            ? "The indicator is present outside transaction type 7; Appendix X does not define "
                                    + "behavior for this context."
                            : "No Appendix X indicator is present outside Merchandise Return transaction type 7.");
        }
        if (scenario.merchantParticipating() && scenario.onlineApprovalWanted() && !present) {
            return result(BR_TRIGGER, Status.FAIL, "indicator-trigger",
                    "A participating merchant requesting online approval must include Sub-Table 11 on a "
                            + "Merchandise Return transaction.");
        }
        if (!scenario.onlineApprovalWanted() && present) {
            return result(BR_TRIGGER, Status.FAIL, "indicator-trigger",
                    "Merchants that do not want Merchandise Return approved online must not send the indicator.");
        }
        if (scenario.onlineApprovalWanted() && !scenario.merchantParticipating()) {
            return result(BR_TRIGGER, Status.REVIEW_REQUIRED, "indicator-trigger",
                    "Online approval is requested but merchant participation is false; program eligibility "
                            + "must be confirmed externally.");
        }
        return result(BR_TRIGGER, Status.PASS, "indicator-trigger",
                present
                        ? "The indicator is present for a participating merchant requesting online approval."
                        : "The indicator is absent where online approval is not requested.");
    }

    private static Assessment assessField(Scenario scenario, boolean present) {
        if (!present) {
            return result(BR_FORMAT_VALUE, Status.PASS, "table-32-sub-table-11",
                    "No indicator occurrence was supplied; absence requirements are assessed by the trigger "
                            + "and lifecycle checks.");
        }
        if (!TABLE_ID.equals(scenario.tableId())) {
            return result(BR_FORMAT_VALUE, Status.FAIL, "table-32-sub-table-11",
                    "Appendix X locates the indicator in Table ID 32; found '" + scenario.tableId() + "'.");
        }
        if (!SUB_TABLE_ID.equals(scenario.subTableId())) {
            return result(BR_FORMAT_VALUE, Status.FAIL, "table-32-sub-table-11",
                    "Online Refund/Refund Authorization Indicator Sub-Table ID must be 11.");
        }
        if (!SUB_TABLE_LENGTH.equals(scenario.subTableLength())) {
            return result(BR_FORMAT_VALUE, Status.FAIL, "table-32-sub-table-11",
                    "Sub-Table 11 has fixed length 001; found '" + scenario.subTableLength() + "'.");
        }
        if (!INDICATOR_VALUE.equals(scenario.value())) {
            return result(BR_FORMAT_VALUE, Status.FAIL, "table-32-sub-table-11",
                    "The valid indicator value is exactly 'Y'; found '" + scenario.value() + "'.");
        }
        String brand = scenario.cardBrand() == null ? "" : scenario.cardBrand().toUpperCase(Locale.ROOT);
        if (!SUPPORTED_BRANDS.contains(brand)) {
            return result(BR_FORMAT_VALUE, Status.REVIEW_REQUIRED, "table-32-sub-table-11",
                    "Value 'Y' is structurally valid, but Appendix X only names Visa, MasterCard, Amex, and "
                            + "Discover; applicability for brand '" + scenario.cardBrand()
                            + "' requires external confirmation.");
        }
        return result(BR_FORMAT_VALUE, Status.PASS, "table-32-sub-table-11",
                "Table 32, Sub-Table 11, fixed length 001, value 'Y', and the named card brand are consistent.");
    }

    private static Assessment assessLifecycle(Scenario scenario, boolean present) {
        if (scenario.lifecycleStage() == LifecycleStage.MERCHANDISE_RETURN) {
            return result(BR_LIFECYCLE, Status.PASS, "lifecycle-persistence",
                    "The original Merchandise Return establishes whether the indicator is to be carried forward.");
        }
        if (scenario.originalReturnHadIndicator() == null) {
            return result(BR_LIFECYCLE, Status.REVIEW_REQUIRED, "lifecycle-persistence",
                    "The original Merchandise Return's indicator presence is unknown; carry-forward cannot be "
                            + "assessed.");
        }
        if (scenario.originalReturnHadIndicator() && !present) {
            return result(BR_LIFECYCLE, Status.FAIL, "lifecycle-persistence",
                    "Appendix X says the indicator included on the Merchandise Return should also be included "
                            + "in subsequent reversals/follow-ups and Timeout Reversals.");
        }
        if (scenario.originalReturnHadIndicator() && present) {
            return result(BR_LIFECYCLE, Status.PASS, "lifecycle-persistence",
                    "The indicator is carried into the subsequent reversal/follow-up transaction.");
        }
        return result(BR_LIFECYCLE, Status.PASS, "lifecycle-persistence",
                "The original Merchandise Return did not include the indicator, so Appendix X's carry-forward "
                        + "condition is not triggered.");
    }

    private static Assessment result(String id, Status status, String scope, String reason) {
        return new Assessment(id, status, scope, reason);
    }
}
