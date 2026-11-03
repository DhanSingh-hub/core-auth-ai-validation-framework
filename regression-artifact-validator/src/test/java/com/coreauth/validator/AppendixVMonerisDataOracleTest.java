package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixVMonerisDataOracle;
import com.coreauth.validator.canonical.AppendixVMonerisDataOracle.Assessment;
import com.coreauth.validator.canonical.AppendixVMonerisDataOracle.Status;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixVMonerisDataOracleTest {
    private final AppendixVMonerisDataOracle oracle = new AppendixVMonerisDataOracle();

    private Map<String, Assessment> byId(List<Assessment> assessments) {
        return assessments.stream().collect(Collectors.toMap(Assessment::businessRequirementId, a -> a));
    }

    private static String table(String id, String data) {
        return id + "%03d".formatted(data.length()) + data;
    }

    private static String spdhHeader() {
        return "DV" + "12" + "TERM1234" + "        " + "CLERK1" + "202610081230"
                + "M" + "S" + "01" + "123" + "000";
    }

    private static String validRequest() {
        return table("001", spdhHeader())
                + table("002", "TERM1234")
                + table("003", "MERCHANT12345")
                + table("004", "3")
                + table("005", "1234567")
                + table("006", "SEQ1234567")
                + table("007", "1234567890ABCDEF")
                + table("008", "D")
                + table("009", "01");
    }

    private static String validResponse() {
        return table("001", spdhHeader())
                + table("002", "TERM1234")
                + table("003", "MERCHANT12345")
                + table("004", "SEQ1234567")
                + table("005", "1234567890ABCDEF")
                + table("006", "YYY" + "A".repeat(48));
    }

    @Test
    void reportsAllAppendixVRequirementsAndKeepsNonMessageRulesReviewGated() {
        Map<String, Assessment> request = byId(oracle.assessRequest(validRequest()));
        assertThat(request.keySet()).containsExactlyInAnyOrder(
                AppendixVMonerisDataOracle.BR_SEGMENT,
                AppendixVMonerisDataOracle.BR_REQUEST_TABLES,
                AppendixVMonerisDataOracle.BR_COMPLETION,
                AppendixVMonerisDataOracle.BR_SECURITY,
                AppendixVMonerisDataOracle.BR_SEQUENCE_LINK,
                AppendixVMonerisDataOracle.BR_MAC_LENGTH,
                AppendixVMonerisDataOracle.BR_SOURCE_CLARIFICATION);
        assertThat(request.get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.PASS);
        assertThat(request.get(AppendixVMonerisDataOracle.BR_MAC_LENGTH).status()).isEqualTo(Status.PASS);
        assertThat(request.get(AppendixVMonerisDataOracle.BR_COMPLETION).status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(request.get(AppendixVMonerisDataOracle.BR_SEQUENCE_LINK).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(request.get(AppendixVMonerisDataOracle.BR_SOURCE_CLARIFICATION).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void acceptsEveryDefinedRequestTableWithItsStatedLayout() {
        Map<String, Assessment> assessments = byId(oracle.assessRequest(validRequest()));
        assertThat(assessments.get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.PASS);
        assertThat(assessments.get(AppendixVMonerisDataOracle.BR_SECURITY).status()).isEqualTo(Status.PASS);
    }

    @Test
    void acceptsEveryDefinedResponseTableWithinItsPublishedLengthBoundaries() {
        Map<String, Assessment> assessments = byId(oracle.assessResponse(validResponse()));
        assertThat(assessments.get(AppendixVMonerisDataOracle.BR_RESPONSE_TABLES).status()).isEqualTo(Status.PASS);
        assertThat(assessments.get(AppendixVMonerisDataOracle.BR_SECURITY).status()).isEqualTo(Status.PASS);
        assertThat(assessments.get(AppendixVMonerisDataOracle.BR_MAC_LENGTH).status()).isEqualTo(Status.PASS);
    }

    @Test
    void permitsAnySubsetOfDefinedTablesBecauseAppendixVDoesNotDeclareAllTablesMandatory() {
        assertThat(byId(oracle.assessRequest(table("006", "SEQ1234567")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.PASS);
    }

    @Test
    void rejectsUnknownTableIds() {
        assertThat(byId(oracle.assessRequest(table("010", "X")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessResponse(table("007", "X")))
                .get(AppendixVMonerisDataOracle.BR_RESPONSE_TABLES).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsMalformedAndTruncatedTableStreams() {
        assertThat(byId(oracle.assessRequest("0010A1"))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessRequest("001048short"))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessRequest("001"))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessRequest(""))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsFixedTableLengthMismatch() {
        assertThat(byId(oracle.assessRequest(table("002", "TERM123")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void enforcesRequestAuthAmountMaximumLength() {
        assertThat(byId(oracle.assessRequest(table("005", "1234567")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.PASS);
        assertThat(byId(oracle.assessRequest(table("005", "12345678")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void checksLanguageIndicatorCodeSetWithoutClaimingToResolveItsSourceWidthConflict() {
        for (String code : List.of("3", "4", "5", "6")) {
            assertThat(byId(oracle.assessRequest(table("004", code)))
                    .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.PASS);
        }
        assertThat(byId(oracle.assessRequest(table("004", "7")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessRequest(table("004", "33")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessRequest(table("004", "3")))
                .get(AppendixVMonerisDataOracle.BR_SOURCE_CLARIFICATION).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void returnsEveryLanguageMatrixTupleInTerminalPedMerchantReceiptCardholderReceiptOrder() {
        assertThat(oracle.languageProfile("3")).contains(new AppendixVMonerisDataOracle.LanguageProfile(
                "English", "English", "English", "English"));
        assertThat(oracle.languageProfile("4")).contains(new AppendixVMonerisDataOracle.LanguageProfile(
                "French", "French", "French", "French"));
        assertThat(oracle.languageProfile("5")).contains(new AppendixVMonerisDataOracle.LanguageProfile(
                "English", "French", "English", "French"));
        assertThat(oracle.languageProfile("6")).contains(new AppendixVMonerisDataOracle.LanguageProfile(
                "French", "English", "French", "English"));
        assertThat(oracle.languageProfile("7")).isEmpty();
    }

    @Test
    void validatesSpdhHeaderWidthsAndNumericFields() {
        String invalidHeader = "DV" + "1A" + "TERM1234" + "        " + "CLERK1" + "202610081230"
                + "M" + "S" + "01" + "123" + "000";
        assertThat(byId(oracle.assessRequest(table("001", invalidHeader)))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsCharactersOutsideAppendixVAlphanumericRepresentations() {
        assertThat(byId(oracle.assessRequest(table("002", "TERM-123")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessRequest(table("003", "MERCHANT#1234")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessRequest(table("005", "12$")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
        String invalidAlphaField = "DV" + "12" + "TERM1234" + "        " + "CLERK1" + "202610081230"
                + "M" + "S" + "01" + "123" + "00$";
        assertThat(byId(oracle.assessRequest(table("001", invalidAlphaField)))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void validatesRequestAccountTypeAndTerminalCapabilityRepresentationsAndWidths() {
        assertThat(byId(oracle.assessRequest(table("008", "D")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.PASS);
        assertThat(byId(oracle.assessRequest(table("008", "D1")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessRequest(table("009", "01")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.PASS);
        assertThat(byId(oracle.assessRequest(table("009", "0$")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessRequest(table("009", "010")))
                .get(AppendixVMonerisDataOracle.BR_REQUEST_TABLES).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void validatesRequestAndResponseMacWidthsWithoutClaimingMacAuthenticity() {
        String shortRequestMac = table("007", "123456789012345");
        String shortResponseMac = table("005", "123456789012345");
        assertThat(byId(oracle.assessRequest(shortRequestMac))
                .get(AppendixVMonerisDataOracle.BR_MAC_LENGTH).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessResponse(shortResponseMac))
                .get(AppendixVMonerisDataOracle.BR_MAC_LENGTH).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessRequest(table("007", "1234567890ABCDEF"))
                ).get(AppendixVMonerisDataOracle.BR_MAC_LENGTH).reason()).contains("authenticity cannot");
    }

    @Test
    void enforcesResponseEncryptionKeyDataMinimumAndMaximumLengths() {
        assertThat(byId(oracle.assessResponse(table("006", "YNN")))
                .get(AppendixVMonerisDataOracle.BR_RESPONSE_TABLES).status()).isEqualTo(Status.PASS);
        assertThat(byId(oracle.assessResponse(table("006", "YNN" + "A".repeat(16))))
                .get(AppendixVMonerisDataOracle.BR_RESPONSE_TABLES).status()).isEqualTo(Status.PASS);
        assertThat(byId(oracle.assessResponse(table("006", "YYN" + "A".repeat(32))))
                .get(AppendixVMonerisDataOracle.BR_RESPONSE_TABLES).status()).isEqualTo(Status.PASS);
        assertThat(byId(oracle.assessResponse(table("006", "YYY" + "A".repeat(48))))
                .get(AppendixVMonerisDataOracle.BR_RESPONSE_TABLES).status()).isEqualTo(Status.PASS);
        assertThat(byId(oracle.assessResponse(table("006", "YN")))
                .get(AppendixVMonerisDataOracle.BR_RESPONSE_TABLES).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessResponse(table("006", "YNNX")))
                .get(AppendixVMonerisDataOracle.BR_RESPONSE_TABLES).status()).isEqualTo(Status.FAIL);
        assertThat(byId(oracle.assessResponse(table("006", "Y".repeat(52))))
                .get(AppendixVMonerisDataOracle.BR_RESPONSE_TABLES).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void mapsOnlyExplicitYIndicatorsToTheDocumentedKeyPositions() {
        assertThat(oracle.indicatedKeyTypes("YNN"))
                .contains(List.of("MAC Encryption"));
        assertThat(oracle.indicatedKeyTypes("NYN"))
                .contains(List.of("Data Encryption"));
        assertThat(oracle.indicatedKeyTypes("NNY"))
                .contains(List.of("PIN Encryption"));
        assertThat(oracle.indicatedKeyTypes("YNY"))
                .contains(List.of("MAC Encryption", "PIN Encryption"));
        assertThat(oracle.indicatedKeyTypes("YN")).isEmpty();
    }

    @Test
    void checksResponseSpdhHeaderUsingTheSamePublishedLayoutAsTheRequest() {
        assertThat(byId(oracle.assessResponse(table("001", spdhHeader())))
                .get(AppendixVMonerisDataOracle.BR_RESPONSE_TABLES).status()).isEqualTo(Status.PASS);
        String invalidHeader = "DV" + "1A" + "TERM1234" + "        " + "CLERK1" + "202610081230"
                + "M" + "S" + "01" + "123" + "000";
        assertThat(byId(oracle.assessResponse(table("001", invalidHeader)))
                .get(AppendixVMonerisDataOracle.BR_RESPONSE_TABLES).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void keepsRoutingSequenceAmountAndCryptographicClaimsExplicitlyReviewRequired() {
        Map<String, Assessment> response = byId(oracle.assessResponse(validResponse()));
        assertThat(response.get(AppendixVMonerisDataOracle.BR_SEGMENT).status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(response.get(AppendixVMonerisDataOracle.BR_SEQUENCE_LINK).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(response.get(AppendixVMonerisDataOracle.BR_SOURCE_CLARIFICATION).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(response.get(AppendixVMonerisDataOracle.BR_SECURITY).reason())
                .contains("authenticity");
    }
}
