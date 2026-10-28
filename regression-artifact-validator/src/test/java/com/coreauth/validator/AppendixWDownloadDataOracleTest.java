package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixWDownloadDataOracle;
import com.coreauth.validator.canonical.AppendixWDownloadDataOracle.Assessment;
import com.coreauth.validator.canonical.AppendixWDownloadDataOracle.Status;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixWDownloadDataOracleTest {
    private final AppendixWDownloadDataOracle oracle = new AppendixWDownloadDataOracle();

    private Map<String, Assessment> byId(List<Assessment> assessments) {
        return assessments.stream().collect(Collectors.toMap(Assessment::businessRequirementId, a -> a));
    }

    private static String table(String id, String length, String data) {
        return id + length + data;
    }

    @Test
    void returnsAnAssessmentForEveryAppendixWRuleAndKeepsUnverifiableFactsReviewGated() {
        Map<String, Assessment> assessments = byId(oracle.assessDownloadData(
                table("001", "003", "eng") + table("002", "013", "A1B 2C3      ")));

        assertThat(assessments.keySet()).containsExactlyInAnyOrder(
                AppendixWDownloadDataOracle.BR_TABLE_FRAMING,
                AppendixWDownloadDataOracle.BR_LANGUAGE_CODE,
                AppendixWDownloadDataOracle.BR_POSTAL_CODE,
                AppendixWDownloadDataOracle.BR_SOURCE_CONFLICT,
                AppendixWDownloadDataOracle.BR_VALUE_ASSOCIATION);
        assertThat(assessments.get(AppendixWDownloadDataOracle.BR_TABLE_FRAMING).status()).isEqualTo(Status.PASS);
        assertThat(assessments.get(AppendixWDownloadDataOracle.BR_LANGUAGE_CODE).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(assessments.get(AppendixWDownloadDataOracle.BR_POSTAL_CODE).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(assessments.get(AppendixWDownloadDataOracle.BR_SOURCE_CONFLICT).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(assessments.get(AppendixWDownloadDataOracle.BR_VALUE_ASSOCIATION).reason())
                .contains("terminal").contains("transmitting device");
    }

    @Test
    void keepsLanguageDataReviewGatedWithoutInferringCharacterRules() {
        assertThat(byId(oracle.assessDownloadData(table("001", "003", "eng")))
                .get(AppendixWDownloadDataOracle.BR_LANGUAGE_CODE).status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(byId(oracle.assessDownloadData(table("001", "003", "zzz")))
                .get(AppendixWDownloadDataOracle.BR_LANGUAGE_CODE).status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(byId(oracle.assessDownloadData(table("001", "003", "e1!")))
                .get(AppendixWDownloadDataOracle.BR_LANGUAGE_CODE).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void rejectsLanguageTableWrongIdOrFixedLength() {
        assertThat(byId(oracle.assessDownloadData(table("001", "004", "engX")))
                .get(AppendixWDownloadDataOracle.BR_LANGUAGE_CODE).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessDownloadData(table("003", "003", "eng")))
                .get(AppendixWDownloadDataOracle.BR_TABLE_FRAMING).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void keepsPostalDataReviewGatedWithoutInventingItsCharacterSet() {
        assertThat(byId(oracle.assessDownloadData(table("002", "013", "A1B 2C3      ")))
                .get(AppendixWDownloadDataOracle.BR_POSTAL_CODE).status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(byId(oracle.assessDownloadData(table("002", "013", "A1B-2C3/XYZ!?")))
                .get(AppendixWDownloadDataOracle.BR_POSTAL_CODE).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void rejectsPostalTableWrongFixedLengthOrDataWidth() {
        assertThat(byId(oracle.assessDownloadData(table("002", "012", "A1B 2C3     ")))
                .get(AppendixWDownloadDataOracle.BR_POSTAL_CODE).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessDownloadData(table("002", "013", "A1B 2C3    ")))
                .get(AppendixWDownloadDataOracle.BR_POSTAL_CODE).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void acceptsEitherTableAloneAndDoesNotInventRequirednessOrderOrUniqueness() {
        assertThat(byId(oracle.assessDownloadData(table("001", "003", "eng")))
                .get(AppendixWDownloadDataOracle.BR_TABLE_FRAMING).status()).isEqualTo(Status.PASS);
        assertThat(byId(oracle.assessDownloadData(table("002", "013", "A1B 2C3      ")))
                .get(AppendixWDownloadDataOracle.BR_TABLE_FRAMING).status()).isEqualTo(Status.PASS);
        String repeatedAndReordered = table("002", "013", "A1B 2C3      ")
                + table("001", "003", "eng") + table("001", "003", "fra");
        assertThat(byId(oracle.assessDownloadData(repeatedAndReordered))
                .get(AppendixWDownloadDataOracle.BR_TABLE_FRAMING).status()).isEqualTo(Status.PASS);
    }

    @Test
    void rejectsMalformedOrTruncatedTableStreams() {
        for (String encoded : List.of("", "001", "00A003eng", "00100Aeng", "001003en")) {
            assertThat(byId(oracle.assessDownloadData(encoded))
                    .get(AppendixWDownloadDataOracle.BR_TABLE_FRAMING).status())
                    .as("encoded Download Data %s", encoded)
                    .isEqualTo(Status.FAIL);
        }
    }

    @Test
    void enforcesTheElement232MaximumOfOneHundredAsciiBytes() {
        String overlong = table("001", "003", "eng") + "0".repeat(95);
        assertThat(overlong.length()).isEqualTo(104);
        assertThat(byId(oracle.assessDownloadData(overlong))
                .get(AppendixWDownloadDataOracle.BR_TABLE_FRAMING).status()).isEqualTo(Status.FAIL);

        String atLimit = table("001", "003", "eng").repeat(9)
                + table("002", "013", "A1B 2C3      ");
        assertThat(atLimit.length()).isEqualTo(100);
        assertThat(byId(oracle.assessDownloadData(atLimit))
                .get(AppendixWDownloadDataOracle.BR_TABLE_FRAMING).status()).isEqualTo(Status.PASS);
    }

    @Test
    void reportsTheAn1VersusDescribedWidthConflictAsReviewRequired() {
        Assessment conflict = byId(oracle.assessDownloadData(table("001", "003", "eng")))
                .get(AppendixWDownloadDataOracle.BR_SOURCE_CONFLICT);
        assertThat(conflict.status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(conflict.reason()).contains("an1").contains("one-alphanumeric-character").contains("003")
                .contains("013");
    }

    @Test
    void reportsNonAsciiEncodingAsReviewRequiredRatherThanAssumingByteLength() {
        Map<String, Assessment> assessments = byId(oracle.assessDownloadData(table("001", "003", "éng")));
        assertThat(assessments.get(AppendixWDownloadDataOracle.BR_TABLE_FRAMING).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(assessments.get(AppendixWDownloadDataOracle.BR_LANGUAGE_CODE).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
    }
}
