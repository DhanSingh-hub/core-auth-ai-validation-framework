package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixUVisaDigitalWalletOracle;
import com.coreauth.validator.canonical.AppendixUVisaDigitalWalletOracle.Assessment;
import com.coreauth.validator.canonical.AppendixUVisaDigitalWalletOracle.Status;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Golden fixtures are shaped per the Wallet Method Indicator field (Data Segment 111, Table ID 049,
 * Sub Table ID "10", {@code extracted_text.txt} around lines 28543-28565), the sole structural anchor
 * between the authorization message and Appendix U-1's narrative Pass-through/Staged Digital Wallet
 * categories ({@code extracted_text.txt} lines ~35624-35653).
 */
class AppendixUVisaDigitalWalletOracleTest {
    private final AppendixUVisaDigitalWalletOracle oracle = new AppendixUVisaDigitalWalletOracle();

    private Map<String, Assessment> byId(List<Assessment> assessments) {
        return assessments.stream().collect(Collectors.toMap(Assessment::businessRequirementId, a -> a));
    }

    @Test
    void assessSectionAlwaysReturnsAllEightBusinessRequirements() {
        List<Assessment> assessments = oracle.assessSection("10", "001", "S");

        assertThat(byId(assessments).keySet()).containsExactlyInAnyOrder(
                AppendixUVisaDigitalWalletOracle.BR_WALLET_INDICATOR_FORMAT,
                AppendixUVisaDigitalWalletOracle.BR_CATEGORY_CLASSIFICATION,
                AppendixUVisaDigitalWalletOracle.BR_PASS_THROUGH,
                AppendixUVisaDigitalWalletOracle.BR_DWO_CONSENT,
                AppendixUVisaDigitalWalletOracle.BR_DWO_EXCLUSIVITY,
                AppendixUVisaDigitalWalletOracle.BR_STAGED_PURCHASE,
                AppendixUVisaDigitalWalletOracle.BR_STAGED_FUNDING,
                AppendixUVisaDigitalWalletOracle.BR_STAGED_CORRELATION);
    }

    // --- BR_WALLET_INDICATOR_FORMAT ---

    @Test
    void acceptsStagedWalletIndicator() {
        Assessment assessment = byId(oracle.assessSection("10", "001", "S"))
                .get(AppendixUVisaDigitalWalletOracle.BR_WALLET_INDICATOR_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void acceptsPassThroughWalletIndicator() {
        Assessment assessment = byId(oracle.assessSection("10", "001", "P"))
                .get(AppendixUVisaDigitalWalletOracle.BR_WALLET_INDICATOR_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void rejectsWrongSubTableId() {
        Assessment assessment = byId(oracle.assessSection("11", "001", "S"))
                .get(AppendixUVisaDigitalWalletOracle.BR_WALLET_INDICATOR_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
        assertThat(assessment.reason()).contains("Sub-Table ID");
    }

    @Test
    void rejectsWrongSubTableLength() {
        Assessment assessment = byId(oracle.assessSection("10", "002", "S"))
                .get(AppendixUVisaDigitalWalletOracle.BR_WALLET_INDICATOR_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
        assertThat(assessment.reason()).contains("Sub-Table Length");
    }

    @Test
    void rejectsUnrecognizedWalletIndicatorValue() {
        Assessment assessment = byId(oracle.assessSection("10", "001", "X"))
                .get(AppendixUVisaDigitalWalletOracle.BR_WALLET_INDICATOR_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
        assertThat(assessment.reason()).contains("'S' or 'P'");
    }

    @Test
    void rejectsMultiCharacterWalletIndicatorValue() {
        Assessment assessment = byId(oracle.assessSection("10", "001", "SP"))
                .get(AppendixUVisaDigitalWalletOracle.BR_WALLET_INDICATOR_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsNullWalletIndicatorValue() {
        Assessment assessment = byId(oracle.assessSection("10", "001", null))
                .get(AppendixUVisaDigitalWalletOracle.BR_WALLET_INDICATOR_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    // --- BR_CATEGORY_CLASSIFICATION ---

    @Test
    void classifiesStagedDigitalWalletCategory() {
        Assessment assessment = byId(oracle.assessSection("10", "001", "S"))
                .get(AppendixUVisaDigitalWalletOracle.BR_CATEGORY_CLASSIFICATION);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
        assertThat(assessment.reason()).contains("Staged Digital Wallet");
    }

    @Test
    void classifiesPassThroughDigitalWalletCategory() {
        Assessment assessment = byId(oracle.assessSection("10", "001", "P"))
                .get(AppendixUVisaDigitalWalletOracle.BR_CATEGORY_CLASSIFICATION);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
        assertThat(assessment.reason()).contains("Pass-through Digital Wallet");
    }

    @Test
    void failsClassificationForUnrecognizedValue() {
        Assessment assessment = byId(oracle.assessSection("10", "001", "X"))
                .get(AppendixUVisaDigitalWalletOracle.BR_CATEGORY_CLASSIFICATION);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void failsClassificationForNullValue() {
        Assessment assessment = byId(oracle.assessSection("10", "001", null))
                .get(AppendixUVisaDigitalWalletOracle.BR_CATEGORY_CLASSIFICATION);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    // --- Always-REVIEW_REQUIRED business-process BRs ---

    @Test
    void passThroughCredentialPreservationIsAlwaysReviewRequired() {
        Assessment assessment = byId(oracle.assessSection("10", "001", "P"))
                .get(AppendixUVisaDigitalWalletOracle.BR_PASS_THROUGH);
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void dwoWrittenConsentIsAlwaysReviewRequired() {
        Assessment assessment = byId(oracle.assessSection("10", "001", "P"))
                .get(AppendixUVisaDigitalWalletOracle.BR_DWO_CONSENT);
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void dwoExclusivityIsAlwaysReviewRequired() {
        Assessment assessment = byId(oracle.assessSection("10", "001", "P"))
                .get(AppendixUVisaDigitalWalletOracle.BR_DWO_EXCLUSIVITY);
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void stagedPurchaseIsAlwaysReviewRequired() {
        Assessment assessment = byId(oracle.assessSection("10", "001", "S"))
                .get(AppendixUVisaDigitalWalletOracle.BR_STAGED_PURCHASE);
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void stagedFundingIsAlwaysReviewRequired() {
        Assessment assessment = byId(oracle.assessSection("10", "001", "S"))
                .get(AppendixUVisaDigitalWalletOracle.BR_STAGED_FUNDING);
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void stagedCorrelationIsAlwaysReviewRequired() {
        Assessment assessment = byId(oracle.assessSection("10", "001", "S"))
                .get(AppendixUVisaDigitalWalletOracle.BR_STAGED_CORRELATION);
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void alwaysReviewRequiredBrsAreIndependentOfWalletIndicatorValue() {
        Map<String, Assessment> staged = byId(oracle.assessSection("10", "001", "S"));
        Map<String, Assessment> passThrough = byId(oracle.assessSection("10", "001", "P"));
        Map<String, Assessment> malformed = byId(oracle.assessSection("99", "999", "Z"));

        for (String reviewRequiredBr : List.of(
                AppendixUVisaDigitalWalletOracle.BR_PASS_THROUGH,
                AppendixUVisaDigitalWalletOracle.BR_DWO_CONSENT,
                AppendixUVisaDigitalWalletOracle.BR_DWO_EXCLUSIVITY,
                AppendixUVisaDigitalWalletOracle.BR_STAGED_PURCHASE,
                AppendixUVisaDigitalWalletOracle.BR_STAGED_FUNDING,
                AppendixUVisaDigitalWalletOracle.BR_STAGED_CORRELATION)) {
            assertThat(staged.get(reviewRequiredBr).status()).isEqualTo(Status.REVIEW_REQUIRED);
            assertThat(passThrough.get(reviewRequiredBr).status()).isEqualTo(Status.REVIEW_REQUIRED);
            assertThat(malformed.get(reviewRequiredBr).status()).isEqualTo(Status.REVIEW_REQUIRED);
        }
    }
}
