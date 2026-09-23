package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment103ApplicabilityValidator;
import com.coreauth.validator.canonical.Segment103ApplicabilityValidator.Applicability;
import com.coreauth.validator.validation.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Segment103ApplicabilityValidator}, resolving SEG103-R-002/SEG103-R-024
 * (Segment 103 applicability matrix) directly from ATL105 Sections 10.5.3, 10.5.4.8, 10.5.5,
 * and 10.5.6 — no SME input required beyond the specification text.
 */
class Segment103ApplicabilityValidatorTest {
    private final Segment103ApplicabilityValidator validator = new Segment103ApplicabilityValidator();

    @Test
    void classifiesFoodStampElectronicVoucherAsRequired() {
        assertThat(validator.classify("FOOD_STAMP_ELECTRONIC_VOUCHER")).isEqualTo(Applicability.REQUIRED);
    }

    @Test
    void classifiesEwicPurchaseCompletionAndVoucherClearAsRequired() {
        assertThat(validator.classify("EWIC_PURCHASE_COMPLETION")).isEqualTo(Applicability.REQUIRED);
        assertThat(validator.classify("EWIC_VOUCHER_CLEAR")).isEqualTo(Applicability.REQUIRED);
    }

    @Test
    void classifiesPlainFoodStampAndCashBenefitFlowsAsOptional() {
        assertThat(validator.classify("FOOD_STAMP_PURCHASE")).isEqualTo(Applicability.OPTIONAL);
        assertThat(validator.classify("FOOD_STAMP_RETURN")).isEqualTo(Applicability.OPTIONAL);
        assertThat(validator.classify("CASH_BENEFIT_PURCHASE")).isEqualTo(Applicability.OPTIONAL);
        assertThat(validator.classify("CASH_BENEFIT_PURCHASE_WITH_CASH_BACK")).isEqualTo(Applicability.OPTIONAL);
        assertThat(validator.classify("FOOD_STAMP_BALANCE_INQUIRY")).isEqualTo(Applicability.OPTIONAL);
        assertThat(validator.classify("CASH_BENEFIT_BALANCE_INQUIRY")).isEqualTo(Applicability.OPTIONAL);
        assertThat(validator.classify("TIME_OUT_REVERSAL")).isEqualTo(Applicability.OPTIONAL);
    }

    @Test
    void classifiesEwicQueryStyleFlowsAsOptionalOnTheRequestSide() {
        assertThat(validator.classify("EWIC_AUTHORIZATION")).isEqualTo(Applicability.OPTIONAL);
        assertThat(validator.classify("EWIC_BALANCE_INQUIRY")).isEqualTo(Applicability.OPTIONAL);
        assertThat(validator.classify("EWIC_AUTHORIZATION_CANCELLATION")).isEqualTo(Applicability.OPTIONAL);
        assertThat(validator.classify("EWIC_PURCHASE_REVERSAL_VOID")).isEqualTo(Applicability.OPTIONAL);
    }

    @Test
    void classifiesEwicReturnAsProhibited() {
        assertThat(validator.classify("EWIC_RETURN")).isEqualTo(Applicability.PROHIBITED);
    }

    @Test
    void unknownLifecycleRoleDefaultsToOptional() {
        assertThat(validator.classify("SOMETHING_UNDOCUMENTED")).isEqualTo(Applicability.OPTIONAL);
    }

    @Test
    void rejectsRequiredFlowMissingSegment103() {
        ValidationResult result = validator.validate("FOOD_STAMP_ELECTRONIC_VOUCHER", false, false, false);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SEG103-R-002"));
    }

    @Test
    void rejectsElectronicVoucherMissingVoucherId() {
        ValidationResult result = validator.validate("FOOD_STAMP_ELECTRONIC_VOUCHER", true, false, false);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Voucher ID"));
    }

    @Test
    void acceptsElectronicVoucherWithVoucherIdAndSegment103() {
        ValidationResult result = validator.validate("FOOD_STAMP_ELECTRONIC_VOUCHER", true, true, false);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsEwicPurchaseCompletionMissingWicData() {
        ValidationResult result = validator.validate("EWIC_PURCHASE_COMPLETION", true, false, false);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("WIC Discount Amount"));
    }

    @Test
    void acceptsEwicPurchaseCompletionWithWicData() {
        ValidationResult result = validator.validate("EWIC_PURCHASE_COMPLETION", true, false, true);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsOptionalFlowWithOrWithoutSegment103() {
        assertThat(validator.validate("FOOD_STAMP_PURCHASE", false, false, false).errors()).isEmpty();
        assertThat(validator.validate("FOOD_STAMP_PURCHASE", true, false, false).errors()).isEmpty();
    }

    @Test
    void rejectsEwicReturnRegardlessOfSegment103Presence() {
        ValidationResult result = validator.validate("EWIC_RETURN", true, false, false);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SEG103-R-020"));
    }
}
