package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixQNationalPosConditionCodeOracle;
import com.coreauth.validator.canonical.AppendixQNationalPosConditionCodeOracle.Assessment;
import com.coreauth.validator.canonical.AppendixQNationalPosConditionCodeOracle.Status;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixQNationalPosConditionCodeOracleTest {
    private final AppendixQNationalPosConditionCodeOracle oracle = new AppendixQNationalPosConditionCodeOracle();

    /**
     * Terminal Class "020" (Attended, Administrative, On premise) + Presentation Type "0000" (Customer
     * present, Card present, no retention capability, Original presentment) + Security Condition "0"
     * (No security concern) + Terminal Type "00" (Administrative terminal). Mirrors the Appendix Q-4
     * worked example "020 00 Attended on premise administrative terminal."
     */
    private static final String FULLY_ASSIGNED_CODE = "0200000000";

    @Test
    void acceptsAllBoundedChecksForAFullyAssignedCode() {
        List<Assessment> assessments = oracle.assess(FULLY_ASSIGNED_CODE);

        assertThat(assessments).extracting(Assessment::businessRequirementId).containsExactlyInAnyOrder(
                AppendixQNationalPosConditionCodeOracle.BR_COMPOSITION,
                AppendixQNationalPosConditionCodeOracle.BR_TERMINAL,
                AppendixQNationalPosConditionCodeOracle.BR_PRESENTATION,
                AppendixQNationalPosConditionCodeOracle.BR_SECURITY,
                AppendixQNationalPosConditionCodeOracle.BR_FLOW_CONSISTENCY);

        for (Assessment assessment : assessments) {
            if (assessment.businessRequirementId().equals(AppendixQNationalPosConditionCodeOracle.BR_FLOW_CONSISTENCY)) {
                assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
            } else {
                assertThat(assessment.status()).isEqualTo(Status.PASS);
            }
        }
    }

    @Test
    void rejectsCompositionWithWrongLength() {
        Assessment assessment = oracle.assessComposition("020000000");
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsCompositionWithNonNumericCharacters() {
        Assessment assessment = oracle.assessComposition("02A0000000");
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsNullComposition() {
        Assessment assessment = oracle.assessComposition(null);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void flagsTerminalClassReservedNationalUseRangeAsReviewRequired() {
        // Position 2 ("Customer/card acceptor/administrative operator") assigned values are only 0-2;
        // '5' is explicitly "Reserved for national use" per Appendix Q-1.
        Assessment assessment = oracle.assessTerminal("0500000000");
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void rejectsUnrecognizedTerminalTypeCode() {
        // '99' is not one of Appendix Q's listed Terminal Type entries.
        Assessment assessment = oracle.assessTerminal("0000000099");
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void terminalAssessmentFailsWhenCompositionIsInvalid() {
        Assessment assessment = oracle.assessTerminal("not-ten-digits");
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void flagsPresentationTypeReservedNationalUseRangeAsReviewRequired() {
        // Position 4 ("customer presence") assigned values are 0,1,2,8; '5' is reserved for national use.
        Assessment assessment = oracle.assessPresentation("0005000000");
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void presentationAssessmentDoesNotFlagAnUnrelatedTerminalClassReservedDigit() {
        // Terminal Class position 2 is reserved ('5'), but Presentation Type positions are all assigned;
        // the Presentation BR must stay scoped to its own positions.
        Assessment assessment = oracle.assessPresentation("0500000000");
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void flagsSecurityConditionReservedPrivateUseRangeAsReviewRequired() {
        // Position 8 assigned values are 0-3; '9' is explicitly "Reserved for private use."
        Assessment assessment = oracle.assessSecurity("0000000900");
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void securityAssessmentFailsWhenCompositionIsInvalid() {
        Assessment assessment = oracle.assessSecurity("0000000090000");
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void flowConsistencyIsAlwaysOutOfAppendixQsIndependentScope() {
        assertThat(oracle.assessFlowConsistency().status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(oracle.assessFlowConsistency().assertionScope()).isEqualTo("CROSS_FIELD_NOT_IN_APPENDIX_Q");
    }

    @Test
    void terminalTypeCatalogMatchesAppendixQsCompleteListedEntries() {
        assertThat(oracle.terminalTypes()).hasSize(21);
        assertThat(oracle.terminalTypes()).containsKeys(
                "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "19", "20", "21", "24", "25", "26", "27", "28", "29");
    }

    // --- BR-SEG100-APPQ-REQUIREDNESS (Appendix I-19 Table ID 030 requiredness note) ---

    @Test
    void requirednessReviewsWhenContextIsNull() {
        assertThat(oracle.assessRequiredness(null).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void requirednessReviewsWhenApplicabilityIsUnknown() {
        AppendixQNationalPosConditionCodeOracle.TransactionContext context =
                AppendixQNationalPosConditionCodeOracle.TransactionContext.unknown();
        assertThat(oracle.assessRequiredness(context).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void requirednessFailsWhenEmvTransactionOmitsTheField() {
        AppendixQNationalPosConditionCodeOracle.TransactionContext context =
                new AppendixQNationalPosConditionCodeOracle.TransactionContext(
                        true, false, false, false, null, null, null, null);
        Assessment assessment = oracle.assessRequiredness(context);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void requirednessPassesWhenCredentialOnFileTransactionIncludesTheField() {
        AppendixQNationalPosConditionCodeOracle.TransactionContext context =
                new AppendixQNationalPosConditionCodeOracle.TransactionContext(
                        false, false, true, true, null, null, null, null);
        Assessment assessment = oracle.assessRequiredness(context);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void requirednessPassesWhenNoTriggerAppliesRegardlessOfPresence() {
        AppendixQNationalPosConditionCodeOracle.TransactionContext context =
                new AppendixQNationalPosConditionCodeOracle.TransactionContext(
                        false, false, false, false, null, null, null, null);
        Assessment assessment = oracle.assessRequiredness(context);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    // --- BR-SEG100-APPQ-VISA-CAVV (Appendix I-19 Visa CAVV trigger for Terminal Type 25/26) ---

    @Test
    void visaCavvNotApplicableOutsideTerminalType25Or26() {
        Assessment assessment = oracle.assessVisaCavv(FULLY_ASSIGNED_CODE, null);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void visaCavvReviewsWhenCardBrandIsUnknownForTerminalType25() {
        Assessment assessment = oracle.assessVisaCavv("0000000025", null);
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void visaCavvPassesForNonVisaTransactionWithTerminalType26() {
        AppendixQNationalPosConditionCodeOracle.TransactionContext context =
                new AppendixQNationalPosConditionCodeOracle.TransactionContext(
                        null, null, null, null, false, null, null, null);
        Assessment assessment = oracle.assessVisaCavv("0000000026", context);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void visaCavvFailsWhenVisaTerminalType25TransactionOmitsCavvData() {
        AppendixQNationalPosConditionCodeOracle.TransactionContext context =
                new AppendixQNationalPosConditionCodeOracle.TransactionContext(
                        null, null, null, null, true, false, null, null);
        Assessment assessment = oracle.assessVisaCavv("0000000025", context);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void visaCavvPassesWhenVisaTerminalType26TransactionIncludesCavvData() {
        AppendixQNationalPosConditionCodeOracle.TransactionContext context =
                new AppendixQNationalPosConditionCodeOracle.TransactionContext(
                        null, null, null, null, true, true, null, null);
        Assessment assessment = oracle.assessVisaCavv("0000000026", context);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    // --- BR-SEG100-APPQ-FLOW-CONSISTENCY eWIC self-checkout case (section 10.5.5.6) ---

    @Test
    void flowConsistencyFallsBackToGeneralReviewWhenContextDoesNotConfirmEwicSelfCheckout() {
        Assessment assessment = oracle.assessFlowConsistency(FULLY_ASSIGNED_CODE, null);
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(assessment.assertionScope()).isEqualTo("CROSS_FIELD_NOT_IN_APPENDIX_Q");
    }

    @Test
    void flowConsistencyFailsWhenEwicSelfCheckoutLaneIsCodedAttended() {
        AppendixQNationalPosConditionCodeOracle.TransactionContext context =
                new AppendixQNationalPosConditionCodeOracle.TransactionContext(
                        null, null, null, null, null, null, true, true);
        // Position 1 = '0' (Attended), which section 10.5.5.6 forbids for eWIC self-checkout.
        Assessment assessment = oracle.assessFlowConsistency(FULLY_ASSIGNED_CODE, context);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
        assertThat(assessment.assertionScope()).isEqualTo("EWIC_SELF_CHECKOUT");
    }

    @Test
    void flowConsistencyPassesWhenEwicSelfCheckoutLaneIsCodedUnattended() {
        AppendixQNationalPosConditionCodeOracle.TransactionContext context =
                new AppendixQNationalPosConditionCodeOracle.TransactionContext(
                        null, null, null, null, null, null, true, true);
        // Position 1 = '1' (Unattended), matching section 10.5.5.6.
        Assessment assessment = oracle.assessFlowConsistency("1200000000", context);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
        assertThat(assessment.assertionScope()).isEqualTo("EWIC_SELF_CHECKOUT");
    }

    @Test
    void contextAwareAssessIncludesAllSevenBusinessRequirements() {
        AppendixQNationalPosConditionCodeOracle.TransactionContext context =
                AppendixQNationalPosConditionCodeOracle.TransactionContext.unknown();
        List<Assessment> assessments = oracle.assess(FULLY_ASSIGNED_CODE, context);

        assertThat(assessments).extracting(Assessment::businessRequirementId).containsExactlyInAnyOrder(
                AppendixQNationalPosConditionCodeOracle.BR_COMPOSITION,
                AppendixQNationalPosConditionCodeOracle.BR_TERMINAL,
                AppendixQNationalPosConditionCodeOracle.BR_PRESENTATION,
                AppendixQNationalPosConditionCodeOracle.BR_SECURITY,
                AppendixQNationalPosConditionCodeOracle.BR_FLOW_CONSISTENCY,
                AppendixQNationalPosConditionCodeOracle.BR_REQUIREDNESS,
                AppendixQNationalPosConditionCodeOracle.BR_VISA_CAVV);
    }
}
