package com.coreauth.validator.canonical;

import java.util.List;
import java.util.Objects;

/** Validates the ATL105 Appendix X online refund indicator. */
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

    public static final String BR_INDICATOR = "BR-SEG100-APPX-ONLINE-REFUND-INDICATOR";
    public static final String BR_LIFECYCLE = "BR-SEG100-APPX-ONLINE-REFUND-LIFECYCLE";
    public static final String BR_VISA_ORDER = "BR-SEG100-APPX-VISA-REFUND-AUTHORIZATION-ORDER";

    private static final String TABLE_ID = "32";
    private static final String SUB_TABLE_ID = "11";

    public List<Assessment> assess(Scenario scenario) {
        Objects.requireNonNull(scenario, "scenario");
        boolean indicatorPresent = TABLE_ID.equals(scenario.tableId())
                && SUB_TABLE_ID.equals(scenario.subTableId());
        boolean valuePresent = scenario.value() != null;
        boolean applicable = scenario.merchantParticipating() && scenario.onlineApprovalWanted();
        Status indicatorStatus;
        String indicatorReason;

        if (indicatorPresent && !valuePresent) {
            indicatorStatus = Status.FAIL;
            indicatorReason = "Table 032/Sub-Table 11 was supplied without its indicator value.";
        } else if (valuePresent && !indicatorPresent) {
            indicatorStatus = Status.REVIEW_REQUIRED;
            indicatorReason = "The indicator value was supplied without its Table 032/Sub-Table 11 identifiers.";
        } else if (indicatorPresent && (!"001".equals(scenario.subTableLength()) || !"Y".equals(scenario.value()))) {
            indicatorStatus = Status.FAIL;
            indicatorReason = "The Online Refund / Refund Authorization Indicator must be length 001 with value Y.";
        } else if (applicable && !indicatorPresent) {
            indicatorStatus = Status.FAIL;
            indicatorReason = "A participating merchant requesting online approval must send the indicator.";
        } else if (indicatorPresent && !applicable) {
            indicatorStatus = Status.FAIL;
            indicatorReason = "Do not send the indicator when online approval is not requested or the merchant is not participating.";
        } else {
            indicatorStatus = Status.PASS;
            indicatorReason = indicatorPresent
                    ? "The applicable online refund indicator is present with the required format."
                    : "The online refund indicator is not applicable to this scenario.";
        }

        Assessment indicator = new Assessment(BR_INDICATOR, indicatorStatus, "indicator-applicability-and-format",
                indicatorReason);
        if (scenario.lifecycleStage() == LifecycleStage.MERCHANDISE_RETURN) {
            return List.of(indicator);
        }

        Status lifecycleStatus = Boolean.TRUE.equals(scenario.originalReturnHadIndicator())
                ? (indicatorPresent && "Y".equals(scenario.value()) ? Status.PASS : Status.FAIL)
                : Status.REVIEW_REQUIRED;
        Assessment lifecycle = new Assessment(BR_LIFECYCLE, lifecycleStatus, "follow-up-indicator-carry-forward",
                lifecycleStatus == Status.PASS
                        ? "The indicator is carried forward from the original Merchandise Return."
                        : lifecycleStatus == Status.FAIL
                                ? "The indicator must be carried forward when it appeared on the original Merchandise Return."
                                : "The original Merchandise Return indicator state was not supplied.");

        if (scenario.lifecycleStage() != LifecycleStage.MERCHANDISE_RETURN
                && "VISA".equalsIgnoreCase(scenario.cardBrand())
                && Boolean.TRUE.equals(scenario.visaPreSettlementAuthorizationRequired())) {
            Status orderStatus = Boolean.TRUE.equals(scenario.settlementRefundSubmittedBeforeAuthorization())
                    ? Status.FAIL : scenario.settlementRefundSubmittedBeforeAuthorization() == null
                            ? Status.REVIEW_REQUIRED : Status.PASS;
            Assessment order = new Assessment(BR_VISA_ORDER, orderStatus, "visa-refund-authorization-order",
                    orderStatus == Status.FAIL
                            ? "Submit the online authorization before the Settlement System Refund."
                            : orderStatus == Status.PASS
                                    ? "The online authorization precedes the Settlement System Refund."
                                    : "The refund submission order was not supplied.");
            return List.of(indicator, lifecycle, order);
        }
        return List.of(indicator, lifecycle);
    }
}
