package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixXOnlineRefundOracle;
import com.coreauth.validator.canonical.AppendixXOnlineRefundOracle.Assessment;
import com.coreauth.validator.canonical.AppendixXOnlineRefundOracle.Scenario;
import com.coreauth.validator.canonical.AppendixXOnlineRefundOracle.Status;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixXOnlineRefundOracleTest {
    private final AppendixXOnlineRefundOracle oracle = new AppendixXOnlineRefundOracle();

    @Test
    void acceptsValidIndicatorForParticipatingMerchantRequestingOnlineApproval() {
        Scenario scenario = new Scenario("32", "11", "001", "Y", "7", true, true, "VISA",
                AppendixXOnlineRefundOracle.LifecycleStage.MERCHANDISE_RETURN, null, null, null);

        Assessment result = oracle.assess(scenario).get(0);

        assertThat(result.businessRequirementId()).isEqualTo(AppendixXOnlineRefundOracle.BR_INDICATOR);
        assertThat(result.status()).isEqualTo(Status.PASS);
    }

    @Test
    void rejectsMissingIndicatorWhenOnlineApprovalIsWanted() {
        Scenario scenario = new Scenario(null, null, null, null, "7", true, true, "VISA",
                AppendixXOnlineRefundOracle.LifecycleStage.MERCHANDISE_RETURN, null, null, null);

        assertThat(oracle.assess(scenario).get(0).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void carriesIndicatorForwardWhenOriginalReturnHadIt() {
        Scenario scenario = new Scenario("32", "11", "001", "Y", "7", true, true, "VISA",
                AppendixXOnlineRefundOracle.LifecycleStage.REVERSAL, true, false, false);

        assertThat(oracle.assess(scenario).get(1).status()).isEqualTo(Status.PASS);
    }

    @Test
    void requiresOnlineVisaAuthorizationBeforeSettlementRefundWhenConfigured() {
        Scenario scenario = new Scenario(null, null, null, null, "7", true, true, "VISA",
                AppendixXOnlineRefundOracle.LifecycleStage.FOLLOW_UP, null, true, true);

        assertThat(oracle.assess(scenario).get(2).status()).isEqualTo(Status.FAIL);
    }
}
