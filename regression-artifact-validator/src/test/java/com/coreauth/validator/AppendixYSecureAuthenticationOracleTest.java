package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixYSecureAuthenticationOracle;
import com.coreauth.validator.canonical.AppendixYSecureAuthenticationOracle.Assessment;
import com.coreauth.validator.canonical.AppendixYSecureAuthenticationOracle.Scenario;
import com.coreauth.validator.canonical.AppendixYSecureAuthenticationOracle.Status;
import com.coreauth.validator.canonical.AppendixYSecureAuthenticationOracle.VisaScenario;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixYSecureAuthenticationOracleTest {
    private final AppendixYSecureAuthenticationOracle oracle = new AppendixYSecureAuthenticationOracle();

    private static Scenario scenario(
            String brand,
            String eci,
            String responseCryptogram,
            VisaScenario visaScenario,
            Integer table035Bytes,
            String revisedCavv,
            String tavv,
            String amexSafeKey,
            Boolean aevvApplicable,
            Boolean aeskApplicable,
            Boolean optBlue,
            String ucafTableId,
            String ucafLength,
            String ucafData,
            boolean aav,
            boolean dsrp,
            String mpiVersion) {
        return new Scenario(brand, eci, responseCryptogram, visaScenario, table035Bytes, revisedCavv, tavv,
                amexSafeKey, aevvApplicable, aeskApplicable, optBlue, ucafTableId, ucafLength, ucafData,
                aav, dsrp, mpiVersion);
    }

    private Map<String, Assessment> assess(Scenario scenario) {
        return oracle.assess(scenario).stream().collect(Collectors.toMap(
                Assessment::businessRequirementId, assessment -> assessment));
    }

    private static Scenario visa(VisaScenario visaScenario, Integer table035, String revisedCavv, String tavv) {
        return scenario("Visa", "25", "CAVV", visaScenario, table035, revisedCavv, tavv,
                null, null, null, null, null, null, null, false, false, null);
    }

    private static Scenario amex(String data, Boolean aevv, Boolean aesk, Boolean optBlue) {
        return scenario("Amex", "27", "AEVV", VisaScenario.NOT_APPLICABLE, null, null, null,
                data, aevv, aesk, optBlue, null, null, null, false, false, null);
    }

    private static Scenario mastercard(
            String ucafId, String ucafLength, String ucafData, boolean aav, boolean dsrp, String tavv, String mpi) {
        return scenario("MasterCard", "26", "AAV", VisaScenario.NOT_APPLICABLE, null, null, tavv,
                null, null, null, null, ucafId, ucafLength, ucafData, aav, dsrp, mpi);
    }

    @Test
    void returnsChecksForEveryCapturedRequirementAndReviewGatesExternalCryptographicClaims() {
        Map<String, Assessment> results = assess(visa(VisaScenario.VERIFIED_BY_VISA_ONLY, 40, null, null));
        assertThat(results).hasSize(11);
        assertThat(results).containsKeys(
                AppendixYSecureAuthenticationOracle.BR_ECI_AND_BRAND_CRYPTOGRAM,
                AppendixYSecureAuthenticationOracle.BR_VISA_SCENARIO,
                AppendixYSecureAuthenticationOracle.BR_AMEX_SAFEKEY_FORMAT,
                AppendixYSecureAuthenticationOracle.BR_MASTERCARD_UCAF_DSRP,
                AppendixYSecureAuthenticationOracle.BR_MPI_VERSION,
                AppendixYSecureAuthenticationOracle.BR_AMEX_OPTBLUE,
                AppendixYSecureAuthenticationOracle.BR_PROTOCOL_FLOW,
                AppendixYSecureAuthenticationOracle.BR_AVAILABILITY,
                AppendixYSecureAuthenticationOracle.BR_CRYPTO_AUTHENTICITY,
                AppendixYSecureAuthenticationOracle.BR_DISCOVER_CRYPTOGRAM,
                AppendixYSecureAuthenticationOracle.BR_MASTERCARD_REMOVED_AAV_VALUES);
        assertThat(results.get(AppendixYSecureAuthenticationOracle.BR_VISA_SCENARIO).status())
                .isEqualTo(Status.PASS);
        assertThat(results.get(AppendixYSecureAuthenticationOracle.BR_CRYPTO_AUTHENTICITY).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(results.get(AppendixYSecureAuthenticationOracle.BR_PROTOCOL_FLOW).reason())
                .contains("Acquirer").contains("Issuer").contains("Interoperability");
        assertThat(results.get(AppendixYSecureAuthenticationOracle.BR_AVAILABILITY).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(results.get(AppendixYSecureAuthenticationOracle.BR_MASTERCARD_REMOVED_AAV_VALUES).reason())
                .contains("'h'").contains("'j'");
    }

    @Test
    void validatesTheAppendixYEciRangeAndBrandSpecificResponseCryptogramMapping() {
        for (String eci : new String[]{"25", "26", "27", "28", "29"}) {
            Scenario validVisa = scenario("Visa", eci, "CAVV", VisaScenario.VERIFIED_BY_VISA_ONLY,
                    40, null, null, null, null, null, null, null, null, null, false, false, null);
            assertThat(assess(validVisa)
                    .get(AppendixYSecureAuthenticationOracle.BR_ECI_AND_BRAND_CRYPTOGRAM).status())
                    .isEqualTo(Status.PASS);
        }
        Scenario validMastercard = scenario("MasterCard", "26", "AAV", VisaScenario.NOT_APPLICABLE,
                null, null, null, null, null, null, null, null, null, null, false, false, "1.0.2");
        assertThat(assess(validMastercard)
                .get(AppendixYSecureAuthenticationOracle.BR_ECI_AND_BRAND_CRYPTOGRAM).status())
                .isEqualTo(Status.PASS);
        Scenario validAmex = scenario("Amex", "27", "AEVV", VisaScenario.NOT_APPLICABLE,
                null, null, null, null, null, null, null, null, null, null, false, false, null);
        assertThat(assess(validAmex)
                .get(AppendixYSecureAuthenticationOracle.BR_ECI_AND_BRAND_CRYPTOGRAM).status())
                .isEqualTo(Status.PASS);
        Scenario wrongCryptogram = scenario("MasterCard", "29", "CAVV", VisaScenario.NOT_APPLICABLE,
                null, null, null, null, null, null, null, null, null, null, false, false, "1.0.2");
        assertThat(assess(wrongCryptogram)
                .get(AppendixYSecureAuthenticationOracle.BR_ECI_AND_BRAND_CRYPTOGRAM).status())
                .isEqualTo(Status.FAIL);
        Scenario invalidEci = scenario("Amex", "30", "AEVV", VisaScenario.NOT_APPLICABLE,
                null, null, null, null, null, null, null, null, null, null, false, false, null);
        assertThat(assess(invalidEci)
                .get(AppendixYSecureAuthenticationOracle.BR_ECI_AND_BRAND_CRYPTOGRAM).status())
                .isEqualTo(Status.FAIL);
    }

    @Test
    void leavesDiscoverCryptogramMappingUnresolvedInsteadOfBorrowingAnotherBrandMapping() {
        Scenario discover = scenario("Discover", "25", null, VisaScenario.NOT_APPLICABLE,
                null, null, null, null, null, null, null, null, null, null, false, false, null);
        assertThat(assess(discover).get(AppendixYSecureAuthenticationOracle.BR_DISCOVER_CRYPTOGRAM).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(assess(discover).get(AppendixYSecureAuthenticationOracle.BR_ECI_AND_BRAND_CRYPTOGRAM).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void doesNotExtendVisaOnlyMatrixOrCryptogramMappingToInterlink() {
        Scenario interlink = scenario("Interlink", "25", "CAVV", VisaScenario.PAYMENT_TOKEN_ONLY,
                null, "A".repeat(20), null, null, null, null, null, null, null, null, false, false, null);
        Map<String, Assessment> results = assess(interlink);
        assertThat(results.get(AppendixYSecureAuthenticationOracle.BR_VISA_SCENARIO).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(results.get(AppendixYSecureAuthenticationOracle.BR_VISA_SCENARIO).reason())
                .contains("Visa-specific").contains("Interlink");
        assertThat(results.get(AppendixYSecureAuthenticationOracle.BR_ECI_AND_BRAND_CRYPTOGRAM).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void enforcesAllThreeVisaMatrixRowsWithoutClaimingCryptogramAuthenticity() {
        assertThat(assess(visa(VisaScenario.VERIFIED_BY_VISA_ONLY, 40, null, null))
                .get(AppendixYSecureAuthenticationOracle.BR_VISA_SCENARIO).status()).isEqualTo(Status.PASS);
        assertThat(assess(visa(VisaScenario.PAYMENT_TOKEN_ONLY, null, "A".repeat(20), null))
                .get(AppendixYSecureAuthenticationOracle.BR_VISA_SCENARIO).status()).isEqualTo(Status.PASS);
        assertThat(assess(visa(VisaScenario.VERIFIED_BY_VISA_AND_TOKEN, null, "A".repeat(20),
                "A".repeat(28))).get(AppendixYSecureAuthenticationOracle.BR_VISA_SCENARIO).status())
                .isEqualTo(Status.PASS);
        assertThat(assess(visa(VisaScenario.VERIFIED_BY_VISA_AND_TOKEN, null, "A".repeat(20),
                "invalid")).get(AppendixYSecureAuthenticationOracle.BR_VISA_SCENARIO).status())
                .isEqualTo(Status.FAIL);
        assertThat(assess(visa(VisaScenario.VERIFIED_BY_VISA_ONLY, 39, null, null))
                .get(AppendixYSecureAuthenticationOracle.BR_VISA_SCENARIO).status())
                .isEqualTo(Status.FAIL);
        assertThat(assess(visa(VisaScenario.PAYMENT_TOKEN_ONLY, null, "short", null))
                .get(AppendixYSecureAuthenticationOracle.BR_VISA_SCENARIO).status())
                .isEqualTo(Status.FAIL);
    }

    @Test
    void validatesSafeKeyMarkerBlockLengthsEncodingAndRequiredSpaceFill() {
        String valid = "SK" + "A".repeat(28) + "B".repeat(28);
        assertThat(assess(amex(valid, true, true, false))
                .get(AppendixYSecureAuthenticationOracle.BR_AMEX_SAFEKEY_FORMAT).status())
                .isEqualTo(Status.PASS);
        String aevvOmitted = "SK" + " ".repeat(28) + "B".repeat(28);
        assertThat(assess(amex(aevvOmitted, false, true, false))
                .get(AppendixYSecureAuthenticationOracle.BR_AMEX_SAFEKEY_FORMAT).status())
                .isEqualTo(Status.PASS);
        String aeskOptional = "SK" + "A".repeat(28) + " ".repeat(28);
        assertThat(assess(amex(aeskOptional, true, false, false))
                .get(AppendixYSecureAuthenticationOracle.BR_AMEX_SAFEKEY_FORMAT).status())
                .isEqualTo(Status.PASS);
        assertThat(assess(amex("XX" + valid.substring(2), true, true, false))
                .get(AppendixYSecureAuthenticationOracle.BR_AMEX_SAFEKEY_FORMAT).status())
                .isEqualTo(Status.FAIL);
        assertThat(assess(amex(valid.substring(0, 57), true, true, false))
                .get(AppendixYSecureAuthenticationOracle.BR_AMEX_SAFEKEY_FORMAT).status())
                .isEqualTo(Status.FAIL);
        assertThat(assess(amex("SK" + "A".repeat(27) + "!" + "B".repeat(28),
                true, true, false))
                .get(AppendixYSecureAuthenticationOracle.BR_AMEX_SAFEKEY_FORMAT).status())
                .isEqualTo(Status.FAIL);
        assertThat(assess(amex("SK" + "\t".repeat(28) + "B".repeat(28),
                false, true, false))
                .get(AppendixYSecureAuthenticationOracle.BR_AMEX_SAFEKEY_FORMAT).status())
                .isEqualTo(Status.FAIL);
    }

    @Test
    void respectsAmexOptBlueSafeKeyUnavailability() {
        assertThat(assess(amex(null, null, null, true))
                .get(AppendixYSecureAuthenticationOracle.BR_AMEX_OPTBLUE).status()).isEqualTo(Status.PASS);
        assertThat(assess(amex("SK" + "A".repeat(56), true, true, true))
                .get(AppendixYSecureAuthenticationOracle.BR_AMEX_OPTBLUE).status()).isEqualTo(Status.FAIL);
        assertThat(assess(amex(null, null, null, null))
                .get(AppendixYSecureAuthenticationOracle.BR_AMEX_OPTBLUE).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void enforcesMastercardUcafAndDsrpFieldPlacementAndSecurityLevel21WhenCombined() {
        String ucaf = "4328" + "A".repeat(28);
        Scenario combined = mastercard("036", "035", "212" + ucaf, true, true, "A".repeat(28), "1.0.2");
        assertThat(assess(combined).get(AppendixYSecureAuthenticationOracle.BR_MASTERCARD_UCAF_DSRP).status())
                .isEqualTo(Status.PASS);
        Scenario ucafWithDsrpWithoutAav = mastercard("036", "035", "242" + ucaf, false, true,
                "A".repeat(28), "1.0.2");
        assertThat(assess(ucafWithDsrpWithoutAav)
                .get(AppendixYSecureAuthenticationOracle.BR_MASTERCARD_UCAF_DSRP).status())
                .isEqualTo(Status.PASS);
        Scenario wrongCombinedLevel = mastercard("036", "035", "242" + ucaf, true, true,
                "A".repeat(28), "1.0.2");
        assertThat(assess(wrongCombinedLevel)
                .get(AppendixYSecureAuthenticationOracle.BR_MASTERCARD_UCAF_DSRP).status())
                .isEqualTo(Status.FAIL);
        Scenario misplacedAav = mastercard("035", "035", "212" + ucaf, true, true,
                "A".repeat(28), "1.0.2");
        assertThat(assess(misplacedAav)
                .get(AppendixYSecureAuthenticationOracle.BR_MASTERCARD_UCAF_DSRP).status())
                .isEqualTo(Status.FAIL);
    }

    @Test
    void checksMastercardMpiMinimumVersion() {
        assertThat(assess(mastercard(null, null, null, false, false, null, "1.0.1"))
                .get(AppendixYSecureAuthenticationOracle.BR_MPI_VERSION).status()).isEqualTo(Status.FAIL);
        assertThat(assess(mastercard(null, null, null, false, false, null, "1.0.2"))
                .get(AppendixYSecureAuthenticationOracle.BR_MPI_VERSION).status()).isEqualTo(Status.PASS);
        assertThat(assess(mastercard(null, null, null, false, false, null, "1.0.10"))
                .get(AppendixYSecureAuthenticationOracle.BR_MPI_VERSION).status()).isEqualTo(Status.PASS);
        assertThat(assess(mastercard(null, null, null, false, false, null, null))
                .get(AppendixYSecureAuthenticationOracle.BR_MPI_VERSION).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
    }
}
