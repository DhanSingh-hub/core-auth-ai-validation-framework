package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixXOnlineRefundOracle;
import com.coreauth.validator.canonical.AppendixXOnlineRefundOracle.Assessment;
import com.coreauth.validator.canonical.AppendixXOnlineRefundOracle.LifecycleStage;
import com.coreauth.validator.canonical.AppendixXOnlineRefundOracle.Scenario;
import com.coreauth.validator.canonical.AppendixXOnlineRefundOracle.Status;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixXOnlineRefundOracleTest {
    private final AppendixXOnlineRefundOracle oracle = new AppendixXOnlineRefundOracle();

    private Map<String, Assessment> assess(Scenario scenario) {
        return oracle.assess(scenario).stream()
                .collect(Collectors.toMap(Assessment::businessRequirementId, assessment -> assessment));
    }

    private static Scenario scenario(
            String transactionType,
            boolean participating,
            boolean wantsOnlineApproval,
            String brand,
            LifecycleStage stage,
            Boolean originalHadIndicator,
            String tableId,
            String subTableId,
            String subTableLength,
            String value) {
        return new Scenario(tableId, subTableId, subTableLength, value, transactionType, participating,
                wantsOnlineApproval, brand, stage, originalHadIndicator, null, null);
    }

    private static Scenario visaSequenceScenario(
            boolean preSettlementRequired, boolean settlementRefundSubmittedBeforeAuthorization, String value) {
        return new Scenario("32", "11", "001", value, "7", true, true, "Visa",
                LifecycleStage.MERCHANDISE_RETURN, null, preSettlementRequired,
                settlementRefundSubmittedBeforeAuthorization);
    }

    private static Scenario validReturn(String brand) {
        return scenario("7", true, true, brand, LifecycleStage.MERCHANDISE_RETURN, null,
                "32", "11", "001", "Y");
    }

    @Test
    void returnsAllAppendixXChecksAndKeepsExternalProgramAndIssuerDecisionsReviewGated() {
        Map<String, Assessment> results = assess(validReturn("Visa"));
        assertThat(results.keySet()).containsExactlyInAnyOrder(
                AppendixXOnlineRefundOracle.BR_TRIGGER,
                AppendixXOnlineRefundOracle.BR_FORMAT_VALUE,
                AppendixXOnlineRefundOracle.BR_LIFECYCLE,
                AppendixXOnlineRefundOracle.BR_AVAILABILITY,
                AppendixXOnlineRefundOracle.BR_ROLLOUT,
                AppendixXOnlineRefundOracle.BR_DECLINE,
                AppendixXOnlineRefundOracle.BR_REFUND_NOTIFICATION,
                AppendixXOnlineRefundOracle.BR_VISA_SETTLEMENT_SEQUENCE);
        assertThat(results.get(AppendixXOnlineRefundOracle.BR_TRIGGER).status()).isEqualTo(Status.PASS);
        assertThat(results.get(AppendixXOnlineRefundOracle.BR_FORMAT_VALUE).status()).isEqualTo(Status.PASS);
        assertThat(results.get(AppendixXOnlineRefundOracle.BR_AVAILABILITY).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(results.get(AppendixXOnlineRefundOracle.BR_ROLLOUT).reason()).contains("2018").contains("2020");
        assertThat(results.get(AppendixXOnlineRefundOracle.BR_DECLINE).reason())
                .contains("issuer discretion");
    }

    @Test
    void requiresIndicatorForParticipatingMerchantRequestingMerchandiseReturnApproval() {
        Scenario omitted = scenario("7", true, true, "VISA", LifecycleStage.MERCHANDISE_RETURN, null,
                "32", "11", "001", null);
        assertThat(assess(omitted).get(AppendixXOnlineRefundOracle.BR_TRIGGER).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void prohibitsIndicatorWhenMerchantDoesNotWantOnlineApproval() {
        Scenario notWanted = scenario("7", true, false, "VISA", LifecycleStage.MERCHANDISE_RETURN, null,
                "32", "11", "001", "Y");
        assertThat(assess(notWanted).get(AppendixXOnlineRefundOracle.BR_TRIGGER).status()).isEqualTo(Status.FAIL);

        Scenario absent = scenario("7", false, false, "VISA", LifecycleStage.MERCHANDISE_RETURN, null,
                null, null, null, null);
        assertThat(assess(absent).get(AppendixXOnlineRefundOracle.BR_TRIGGER).status()).isEqualTo(Status.PASS);
    }

    @Test
    void doesNotInferParticipationWhenOnlineApprovalRequestedByNonparticipant() {
        Scenario inconsistent = scenario("7", false, true, "VISA", LifecycleStage.MERCHANDISE_RETURN, null,
                "32", "11", "001", "Y");
        assertThat(assess(inconsistent).get(AppendixXOnlineRefundOracle.BR_TRIGGER).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void enforcesTableAndSubtableIdentifiersLengthAndYValueForNamedBrands() {
        List<Scenario> invalid = List.of(
                scenario("7", true, true, "VISA", LifecycleStage.MERCHANDISE_RETURN, null,
                        "31", "11", "001", "Y"),
                scenario("7", true, true, "VISA", LifecycleStage.MERCHANDISE_RETURN, null,
                        "32", "10", "001", "Y"),
                scenario("7", true, true, "VISA", LifecycleStage.MERCHANDISE_RETURN, null,
                        "32", "11", "002", "Y"),
                scenario("7", true, true, "VISA", LifecycleStage.MERCHANDISE_RETURN, null,
                        "32", "11", "001", "N"));
        for (Scenario candidate : invalid) {
            assertThat(assess(candidate).get(AppendixXOnlineRefundOracle.BR_FORMAT_VALUE).status())
                    .isEqualTo(Status.FAIL);
        }
    }

    @Test
    void acceptsYForVisaMastercardAmexAndDiscoverOnly() {
        for (String brand : List.of("Visa", "MasterCard", "Amex", "Discover")) {
            assertThat(assess(validReturn(brand)).get(AppendixXOnlineRefundOracle.BR_FORMAT_VALUE).status())
                    .as("brand %s", brand)
                    .isEqualTo(Status.PASS);
        }
    }

    @Test
    void leavesUnlistedBrandApplicabilityReviewGated() {
        assertThat(assess(validReturn("Other"))
                .get(AppendixXOnlineRefundOracle.BR_FORMAT_VALUE).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void enforcesPriorVisaAuthorizationOnlyWhenMerchantRequirementAndOrderAreKnown() {
        assertThat(assess(visaSequenceScenario(true, false, "Y"))
                .get(AppendixXOnlineRefundOracle.BR_VISA_SETTLEMENT_SEQUENCE).status()).isEqualTo(Status.PASS);
        assertThat(assess(visaSequenceScenario(true, true, "Y"))
                .get(AppendixXOnlineRefundOracle.BR_VISA_SETTLEMENT_SEQUENCE).status()).isEqualTo(Status.FAIL);
        assertThat(assess(visaSequenceScenario(true, false, null))
                .get(AppendixXOnlineRefundOracle.BR_VISA_SETTLEMENT_SEQUENCE).status()).isEqualTo(Status.FAIL);
        assertThat(assess(validReturn("Visa"))
                .get(AppendixXOnlineRefundOracle.BR_VISA_SETTLEMENT_SEQUENCE).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(assess(validReturn("Discover"))
                .get(AppendixXOnlineRefundOracle.BR_VISA_SETTLEMENT_SEQUENCE).status()).isEqualTo(Status.PASS);
    }

    @Test
    void requiresIndicatorOnReversalFollowUpAndTimeoutReversalWhenOriginalHadIt() {
        for (LifecycleStage stage : List.of(
                LifecycleStage.REVERSAL, LifecycleStage.FOLLOW_UP, LifecycleStage.TIMEOUT_REVERSAL)) {
            Scenario absent = scenario("8", true, true, "VISA", stage, true,
                    null, null, null, null);
            assertThat(assess(absent).get(AppendixXOnlineRefundOracle.BR_LIFECYCLE).status())
                    .as("stage %s", stage)
                    .isEqualTo(Status.FAIL);

            Scenario included = scenario("8", true, true, "VISA", stage, true,
                    "32", "11", "001", "Y");
            assertThat(assess(included).get(AppendixXOnlineRefundOracle.BR_LIFECYCLE).status())
                    .as("stage %s", stage)
                    .isEqualTo(Status.PASS);
        }
    }

    @Test
    void doesNotRequireCarryForwardWhenOriginalHadNoIndicatorAndReviewsUnknownHistory() {
        Scenario absentOriginally = scenario("8", true, true, "VISA", LifecycleStage.REVERSAL, false,
                null, null, null, null);
        assertThat(assess(absentOriginally).get(AppendixXOnlineRefundOracle.BR_LIFECYCLE).status())
                .isEqualTo(Status.PASS);

        Scenario unknown = scenario("Z", true, true, "VISA", LifecycleStage.TIMEOUT_REVERSAL, null,
                null, null, null, null);
        assertThat(assess(unknown).get(AppendixXOnlineRefundOracle.BR_LIFECYCLE).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
    }
}
