package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixSCaPublicKeyFileOracle;
import com.coreauth.validator.canonical.AppendixSCaPublicKeyFileOracle.Assessment;
import com.coreauth.validator.canonical.AppendixSCaPublicKeyFileOracle.Status;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@code GOLDEN_RID}/{@code GOLDEN_INDEX}/{@code GOLDEN_MODULUS}/{@code GOLDEN_EXPONENT} are a
 * synthetic (not a real production CA key) but internally self-consistent CA Public Key record,
 * shaped per Appendix S-1/S-2 ({@code extracted_text.txt} around lines 35450-35507): the RID value
 * {@code A000000003} is the appendix's own worked example value ("eg. A000000003"). The checksum
 * fixtures below were independently computed as SHA-1(RID || Index || Modulus || Exponent) outside
 * this test (Python {@code hashlib}), exercising both defined Exponent values ({@code 03} and the
 * long-form {@code 065537}).
 */
class AppendixSCaPublicKeyFileOracleTest {
    private final AppendixSCaPublicKeyFileOracle oracle = new AppendixSCaPublicKeyFileOracle();

    private static final String GOLDEN_RID = "A000000003";
    private static final String GOLDEN_INDEX = "01";
    private static final String GOLDEN_MODULUS =
            "C7A5A5D4E1B3F09C2D8E6A4B7F1C3E5A9D2B6F8C1E4A7D0B3F6C9E2A5D8B1F4"
                    + "C7E0A3D6B9F2C5E8A1D4B7F0C3E6A9D2B5F80";
    private static final String GOLDEN_EXPONENT_SHORT = "03";
    private static final String GOLDEN_CHECKSUM_SHORT = "3CA4A224E173325AB9053D7FB86091DB0BADDCA4";
    private static final String GOLDEN_EXPONENT_LONG = "065537";
    private static final String GOLDEN_CHECKSUM_LONG = "ED435CF93056F7BD0391AAFC21BF28FF423C1AF6";

    private static String keyRecord(String expiry, String hashIndicator, String pubKeyIndicator, String rid,
            String index, String modulus, String exponent, String checkSum) {
        return String.join(",", expiry, hashIndicator, pubKeyIndicator, rid, index, modulus, exponent, checkSum)
                + "\r\n";
    }

    private static String goldenKeyRecord() {
        return keyRecord("12312030", "01", "01", GOLDEN_RID, GOLDEN_INDEX, GOLDEN_MODULUS, GOLDEN_EXPONENT_SHORT,
                GOLDEN_CHECKSUM_SHORT);
    }

    @Test
    void independentlyRecomputesTheGoldenChecksumForBothDefinedExponentValues() {
        assertThat(AppendixSCaPublicKeyFileOracle.computeChecksum(GOLDEN_RID, GOLDEN_INDEX, GOLDEN_MODULUS,
                GOLDEN_EXPONENT_SHORT)).isEqualTo(GOLDEN_CHECKSUM_SHORT);
        assertThat(AppendixSCaPublicKeyFileOracle.computeChecksum(GOLDEN_RID, GOLDEN_INDEX, GOLDEN_MODULUS,
                GOLDEN_EXPONENT_LONG)).isEqualTo(GOLDEN_CHECKSUM_LONG);
    }

    @Test
    void acceptsAllBoundedChecksForTheGoldenKeyRecord() {
        List<Assessment> assessments = oracle.assessKeyRecord(goldenKeyRecord());

        Map<String, Assessment> byId = assessments.stream()
                .collect(Collectors.toMap(Assessment::businessRequirementId, a -> a));

        assertThat(byId.keySet()).containsExactlyInAnyOrder(
                AppendixSCaPublicKeyFileOracle.BR_KEY_RECORD_FRAMING,
                AppendixSCaPublicKeyFileOracle.BR_EXPIRY_DATE,
                AppendixSCaPublicKeyFileOracle.BR_HASH_ALGORITHM,
                AppendixSCaPublicKeyFileOracle.BR_PUBKEY_ALGORITHM,
                AppendixSCaPublicKeyFileOracle.BR_RID_FORMAT,
                AppendixSCaPublicKeyFileOracle.BR_INDEX_FORMAT,
                AppendixSCaPublicKeyFileOracle.BR_MODULUS_FORMAT,
                AppendixSCaPublicKeyFileOracle.BR_EXPONENT_VALUE,
                AppendixSCaPublicKeyFileOracle.BR_CHECKSUM_VERIFICATION,
                AppendixSCaPublicKeyFileOracle.BR_CA_EMV_LINK);
        assertThat(byId.get(AppendixSCaPublicKeyFileOracle.BR_CA_EMV_LINK).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        byId.remove(AppendixSCaPublicKeyFileOracle.BR_CA_EMV_LINK);
        assertThat(byId.values()).extracting(Assessment::status).containsOnly(Status.PASS);
    }

    @Test
    void acceptsTheLongFormExponentWithItsOwnChecksum() {
        String record = keyRecord("12312030", "01", "01", GOLDEN_RID, GOLDEN_INDEX, GOLDEN_MODULUS,
                GOLDEN_EXPONENT_LONG, GOLDEN_CHECKSUM_LONG);

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        assertThat(assessments).filteredOn(a -> !a.businessRequirementId().equals(
                        AppendixSCaPublicKeyFileOracle.BR_CA_EMV_LINK))
                .extracting(Assessment::status).containsOnly(Status.PASS);
    }

    @Test
    void theCaEmvLinkCheckIsAlwaysReviewRequired() {
        List<Assessment> assessments = oracle.assessKeyRecord(goldenKeyRecord());

        Assessment caEmvLink = byId(assessments, AppendixSCaPublicKeyFileOracle.BR_CA_EMV_LINK);
        assertThat(caEmvLink.status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(caEmvLink.reason()).contains("AID-to-key");
    }

    @Test
    void acceptsTheValidHeaderRecord() {
        List<Assessment> assessments = oracle.assessHeader("CA_KEYS,0100\r\n");

        assertThat(assessments).hasSize(1);
        assertThat(assessments.get(0).status()).isEqualTo(Status.PASS);
    }

    @Test
    void rejectsAHeaderRecordWithTheWrongFileName() {
        List<Assessment> assessments = oracle.assessHeader("NOT_KEYS,0100\r\n");

        assertThat(assessments.get(0).status()).isEqualTo(Status.FAIL);
        assertThat(assessments.get(0).reason()).contains("File Name");
    }

    @Test
    void rejectsAHeaderRecordWithANonNumericFileVersion() {
        List<Assessment> assessments = oracle.assessHeader("CA_KEYS,01AB\r\n");

        assertThat(assessments.get(0).status()).isEqualTo(Status.FAIL);
        assertThat(assessments.get(0).reason()).contains("File Version");
    }

    @Test
    void rejectsAHeaderRecordMissingCrlfTermination() {
        List<Assessment> assessments = oracle.assessHeader("CA_KEYS,0100");

        assertThat(assessments.get(0).status()).isEqualTo(Status.FAIL);
        assertThat(assessments.get(0).reason()).contains("CRLF");
    }

    @Test
    void rejectsAHeaderRecordWithTheWrongFieldCount() {
        List<Assessment> assessments = oracle.assessHeader("CA_KEYS,0100,extra\r\n");

        assertThat(assessments.get(0).status()).isEqualTo(Status.FAIL);
        assertThat(assessments.get(0).reason()).contains("exactly 2");
    }

    @Test
    void rejectsAKeyRecordMissingCrlfTermination() {
        String record = goldenKeyRecord().replace("\r\n", "");

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        assertThat(excludingCaEmvLink(assessments)).extracting(Assessment::status).containsOnly(Status.FAIL);
        assertThat(assessments.get(0).reason()).contains("CRLF");
    }

    @Test
    void rejectsAKeyRecordWithTheWrongFieldCount() {
        String record = "12312030,01,01,A000000003,01,ABCD,03\r\n"; // missing checksum field

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        assertThat(excludingCaEmvLink(assessments)).extracting(Assessment::status).containsOnly(Status.FAIL);
        assertThat(assessments.get(0).reason()).contains("exactly 8");
    }

    @Test
    void rejectsAnExpiryDateWithAnInvalidMonth() {
        String record = keyRecord("13312030", "01", "01", GOLDEN_RID, GOLDEN_INDEX, GOLDEN_MODULUS,
                GOLDEN_EXPONENT_SHORT, GOLDEN_CHECKSUM_SHORT);

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        Assessment expiry = byId(assessments, AppendixSCaPublicKeyFileOracle.BR_EXPIRY_DATE);
        assertThat(expiry.status()).isEqualTo(Status.FAIL);
        assertThat(expiry.reason()).contains("month");
    }

    @Test
    void rejectsAnExpiryDateWithAnInvalidDayForFebruary() {
        String record = keyRecord("02302030", "01", "01", GOLDEN_RID, GOLDEN_INDEX, GOLDEN_MODULUS,
                GOLDEN_EXPONENT_SHORT, GOLDEN_CHECKSUM_SHORT);

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        Assessment expiry = byId(assessments, AppendixSCaPublicKeyFileOracle.BR_EXPIRY_DATE);
        assertThat(expiry.status()).isEqualTo(Status.FAIL);
        assertThat(expiry.reason()).contains("day");
    }

    @Test
    void acceptsALeapYearFebruaryTwentyNinth() {
        String record = keyRecord("02292028", "01", "01", GOLDEN_RID, GOLDEN_INDEX, GOLDEN_MODULUS,
                GOLDEN_EXPONENT_SHORT, GOLDEN_CHECKSUM_SHORT);

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        assertThat(byId(assessments, AppendixSCaPublicKeyFileOracle.BR_EXPIRY_DATE).status()).isEqualTo(Status.PASS);
    }

    @Test
    void rejectsAHashAlgorithmIndicatorOtherThanZeroOne() {
        String record = keyRecord("12312030", "02", "01", GOLDEN_RID, GOLDEN_INDEX, GOLDEN_MODULUS,
                GOLDEN_EXPONENT_SHORT, GOLDEN_CHECKSUM_SHORT);

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        Assessment hashAlgo = byId(assessments, AppendixSCaPublicKeyFileOracle.BR_HASH_ALGORITHM);
        assertThat(hashAlgo.status()).isEqualTo(Status.FAIL);
        assertThat(hashAlgo.reason()).contains("SHA-1");
    }

    @Test
    void rejectsAPubKeyAlgorithmIndicatorOtherThanZeroOne() {
        String record = keyRecord("12312030", "01", "02", GOLDEN_RID, GOLDEN_INDEX, GOLDEN_MODULUS,
                GOLDEN_EXPONENT_SHORT, GOLDEN_CHECKSUM_SHORT);

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        Assessment pubKeyAlgo = byId(assessments, AppendixSCaPublicKeyFileOracle.BR_PUBKEY_ALGORITHM);
        assertThat(pubKeyAlgo.status()).isEqualTo(Status.FAIL);
        assertThat(pubKeyAlgo.reason()).contains("RSA");
    }

    @Test
    void rejectsAnEmptyRid() {
        String record = keyRecord("12312030", "01", "01", "", GOLDEN_INDEX, GOLDEN_MODULUS,
                GOLDEN_EXPONENT_SHORT, GOLDEN_CHECKSUM_SHORT);

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        assertThat(byId(assessments, AppendixSCaPublicKeyFileOracle.BR_RID_FORMAT).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsARidLongerThanTenCharacters() {
        String record = keyRecord("12312030", "01", "01", "A00000000099", GOLDEN_INDEX, GOLDEN_MODULUS,
                GOLDEN_EXPONENT_SHORT, GOLDEN_CHECKSUM_SHORT);

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        assertThat(byId(assessments, AppendixSCaPublicKeyFileOracle.BR_RID_FORMAT).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsAnIndexThatIsNotTwoHexCharacters() {
        String record = keyRecord("12312030", "01", "01", GOLDEN_RID, "1", GOLDEN_MODULUS,
                GOLDEN_EXPONENT_SHORT, GOLDEN_CHECKSUM_SHORT);

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        assertThat(byId(assessments, AppendixSCaPublicKeyFileOracle.BR_INDEX_FORMAT).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsAnIndexWithNonHexCharacters() {
        String record = keyRecord("12312030", "01", "01", GOLDEN_RID, "ZZ", GOLDEN_MODULUS,
                GOLDEN_EXPONENT_SHORT, GOLDEN_CHECKSUM_SHORT);

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        assertThat(byId(assessments, AppendixSCaPublicKeyFileOracle.BR_INDEX_FORMAT).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsAnOddLengthModulus() {
        String record = keyRecord("12312030", "01", "01", GOLDEN_RID, GOLDEN_INDEX, "ABC",
                GOLDEN_EXPONENT_SHORT, GOLDEN_CHECKSUM_SHORT);

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        assertThat(byId(assessments, AppendixSCaPublicKeyFileOracle.BR_MODULUS_FORMAT).status())
                .isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsAnExponentValueOtherThanZeroThreeOrLongForm() {
        String record = keyRecord("12312030", "01", "01", GOLDEN_RID, GOLDEN_INDEX, GOLDEN_MODULUS,
                "17", GOLDEN_CHECKSUM_SHORT);

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        assertThat(byId(assessments, AppendixSCaPublicKeyFileOracle.BR_EXPONENT_VALUE).status())
                .isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsAChecksumOfTheWrongLength() {
        String record = keyRecord("12312030", "01", "01", GOLDEN_RID, GOLDEN_INDEX, GOLDEN_MODULUS,
                GOLDEN_EXPONENT_SHORT, "ABCD");

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        Assessment checksum = byId(assessments, AppendixSCaPublicKeyFileOracle.BR_CHECKSUM_VERIFICATION);
        assertThat(checksum.status()).isEqualTo(Status.FAIL);
        assertThat(checksum.reason()).contains("40 hex");
    }

    @Test
    void rejectsAChecksumThatDoesNotMatchTheRecomputedShaOneDigest() {
        String record = keyRecord("12312030", "01", "01", GOLDEN_RID, GOLDEN_INDEX, GOLDEN_MODULUS,
                GOLDEN_EXPONENT_SHORT, "0".repeat(40));

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        Assessment checksum = byId(assessments, AppendixSCaPublicKeyFileOracle.BR_CHECKSUM_VERIFICATION);
        assertThat(checksum.status()).isEqualTo(Status.FAIL);
        assertThat(checksum.reason()).contains("does not match");
    }

    @Test
    void rejectsAChecksumBoundToTheWrongExponentValue() {
        // Checksum was computed over the long-form exponent, but the record declares the short-form exponent.
        String record = keyRecord("12312030", "01", "01", GOLDEN_RID, GOLDEN_INDEX, GOLDEN_MODULUS,
                GOLDEN_EXPONENT_SHORT, GOLDEN_CHECKSUM_LONG);

        List<Assessment> assessments = oracle.assessKeyRecord(record);

        assertThat(byId(assessments, AppendixSCaPublicKeyFileOracle.BR_CHECKSUM_VERIFICATION).status())
                .isEqualTo(Status.FAIL);
    }

    @Test
    void cascadesFailuresWhenKeyRecordFramingIsInvalid() {
        String malformed = "12312030,01,01," + GOLDEN_RID + "," + GOLDEN_INDEX + "," + GOLDEN_MODULUS + "\r\n";

        List<Assessment> assessments = oracle.assessKeyRecord(malformed);

        assertThat(excludingCaEmvLink(assessments)).extracting(Assessment::status).containsOnly(Status.FAIL);
        assertThat(byId(assessments, AppendixSCaPublicKeyFileOracle.BR_EXPIRY_DATE).reason())
                .contains("framing is invalid");
    }

    private static Assessment byId(List<Assessment> assessments, String businessRequirementId) {
        return assessments.stream()
                .filter(a -> a.businessRequirementId().equals(businessRequirementId))
                .findFirst()
                .orElseThrow();
    }

    /** {@link AppendixSCaPublicKeyFileOracle#BR_CA_EMV_LINK} is always REVIEW_REQUIRED by design, never FAIL/PASS. */
    private static List<Assessment> excludingCaEmvLink(List<Assessment> assessments) {
        return assessments.stream()
                .filter(a -> !a.businessRequirementId().equals(AppendixSCaPublicKeyFileOracle.BR_CA_EMV_LINK))
                .collect(Collectors.toList());
    }
}
