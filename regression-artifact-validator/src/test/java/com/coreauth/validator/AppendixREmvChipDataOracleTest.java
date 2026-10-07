package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixREmvChipDataOracle;
import com.coreauth.validator.canonical.AppendixREmvChipDataOracle.Assessment;
import com.coreauth.validator.canonical.AppendixREmvChipDataOracle.Status;
import com.coreauth.validator.canonical.AppendixREmvChipDataOracle.TransactionContext;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@code GOLDEN_CHIP_DATA} is transcribed byte-for-byte from the Appendix R-1 worked example
 * ({@code extracted_text.txt} around lines 35403-35436), concatenating its 16 Tag/Length/Value rows
 * in order (the leading "01\06 EMV/Chip Data length (BCD packed)" row is Element 189's own length
 * prefix, not part of Element 190's TLV value, so it is supplied separately as {@code GOLDEN_LENGTH}).
 */
class AppendixREmvChipDataOracleTest {
    private final AppendixREmvChipDataOracle oracle = new AppendixREmvChipDataOracle();

    private static final String GOLDEN_CHIP_DATA =
            "9F0607A0000000980840"            // Application Identifier (AID)
                    + "9F0206000000000900"     // Amount, authorized
                    + "9F260856BF299472BDB0C7" // Application cryptogram
                    + "82025C00"               // Application interchange profile
                    + "9F36024567"             // Application Transaction Counter (ATC)
                    + "9F370412135415"         // Unpredictable number
                    + "95054080008000"         // Terminal verification results
                    + "9C0100"                 // Transaction type
                    + "9F101106011A03900000112233445566778899AA" // Issuer application data
                    + "8407A0000000041010"     // Dedicated File (DF) name
                    + "9F270180"               // Cryptogram information data
                    + "9F34035E0300"           // CVM results
                    + "5F2A020978"             // Transaction currency code
                    + "9F1A020840"             // Terminal country code
                    + "9A03060802"             // Transaction date
                    + "9F3303E0B8C8";          // Terminal capabilities
    private static final String GOLDEN_LENGTH = "116";

    @Test
    void decodesTheAppendixRWorkedExampleAsSixteenCleanTlvEntries() {
        AppendixREmvChipDataOracle.TlvDecodeResult decode = oracle.decode(GOLDEN_CHIP_DATA);

        assertThat(decode.wellFormed()).isTrue();
        assertThat(decode.entries()).hasSize(16);
        assertThat(decode.entries()).extracting(AppendixREmvChipDataOracle.TlvEntry::tag).containsExactly(
                "9F06", "9F02", "9F26", "82", "9F36", "9F37", "95", "9C",
                "9F10", "84", "9F27", "9F34", "5F2A", "9F1A", "9A", "9F33");
    }

    @Test
    void acceptsAllBoundedChecksForTheGoldenWorkedExampleUnderAnEmvTransaction() {
        List<Assessment> assessments = oracle.assess(GOLDEN_LENGTH, GOLDEN_CHIP_DATA, new TransactionContext(true));

        Map<String, Assessment> byId = assessments.stream()
                .collect(java.util.stream.Collectors.toMap(Assessment::businessRequirementId, a -> a));

        assertThat(byId.keySet()).containsExactlyInAnyOrder(
                AppendixREmvChipDataOracle.BR_LENGTH_PREFIX,
                AppendixREmvChipDataOracle.BR_TLV_FORMAT,
                AppendixREmvChipDataOracle.BR_MANDATORY_IDENTIFIER,
                AppendixREmvChipDataOracle.BR_PROHIBITED_SENSITIVE_TAG,
                AppendixREmvChipDataOracle.BR_DUPLICATE_TAG_REVIEW,
                AppendixREmvChipDataOracle.BR_EMV_COMPANION,
                AppendixREmvChipDataOracle.BR_CROSS_FIELD);

        assertThat(byId.get(AppendixREmvChipDataOracle.BR_LENGTH_PREFIX).status()).isEqualTo(Status.PASS);
        assertThat(byId.get(AppendixREmvChipDataOracle.BR_TLV_FORMAT).status()).isEqualTo(Status.PASS);
        assertThat(byId.get(AppendixREmvChipDataOracle.BR_MANDATORY_IDENTIFIER).status()).isEqualTo(Status.PASS);
        assertThat(byId.get(AppendixREmvChipDataOracle.BR_PROHIBITED_SENSITIVE_TAG).status()).isEqualTo(Status.PASS);
        assertThat(byId.get(AppendixREmvChipDataOracle.BR_DUPLICATE_TAG_REVIEW).status()).isEqualTo(Status.PASS);
        assertThat(byId.get(AppendixREmvChipDataOracle.BR_EMV_COMPANION).status()).isEqualTo(Status.PASS);
        assertThat(byId.get(AppendixREmvChipDataOracle.BR_CROSS_FIELD).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void defaultOverloadLeavesEmvCompanionReviewRequiredWithoutContext() {
        List<Assessment> assessments = oracle.assess(GOLDEN_LENGTH, GOLDEN_CHIP_DATA);

        Assessment companion = assessments.stream()
                .filter(a -> a.businessRequirementId().equals(AppendixREmvChipDataOracle.BR_EMV_COMPANION))
                .findFirst().orElseThrow();
        assertThat(companion.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void rejectsLengthFieldThatIsNotThreeNumericDigits() {
        Assessment assessment = oracle.assessLengthPrefix("12", GOLDEN_CHIP_DATA);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);

        assessment = oracle.assessLengthPrefix("1a2", GOLDEN_CHIP_DATA);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);

        assessment = oracle.assessLengthPrefix(null, GOLDEN_CHIP_DATA);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsLengthFieldThatDoesNotMatchActualByteCount() {
        Assessment assessment = oracle.assessLengthPrefix("999", GOLDEN_CHIP_DATA);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
        assertThat(assessment.reason()).contains("999").contains("116");
    }

    @Test
    void rejectsChipDataThatIsNotValidHex() {
        Assessment assessment = oracle.assessLengthPrefix("003", "ZZZ");
        assertThat(assessment.status()).isEqualTo(Status.FAIL);

        AppendixREmvChipDataOracle.TlvDecodeResult decode = oracle.decode("ZZZ");
        assertThat(decode.wellFormed()).isFalse();
    }

    @Test
    void rejectsTruncatedTlvStream() {
        // Drop the final byte of the last entry (9F33 03 E0 B8 C8) so the declared length overruns the data.
        String truncated = GOLDEN_CHIP_DATA.substring(0, GOLDEN_CHIP_DATA.length() - 2);

        AppendixREmvChipDataOracle.TlvDecodeResult decode = oracle.decode(truncated);
        assertThat(decode.wellFormed()).isFalse();

        Assessment assessment = oracle.assessTlvFormat(decode);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void cascadingChecksFailWhenTlvStructureIsInvalid() {
        AppendixREmvChipDataOracle.TlvDecodeResult decode = oracle.decode("ZZ");

        assertThat(oracle.assessMandatoryIdentifier(decode).status()).isEqualTo(Status.FAIL);
        assertThat(oracle.assessProhibitedSensitiveTag(decode).status()).isEqualTo(Status.FAIL);
        assertThat(oracle.assessDuplicateTagReview(decode).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void acceptsTwoByteTagWhenLastFiveBitsOfFirstByteAreAllOne() {
        // Tag 9F10 (two-byte: 0x9F & 0x1F == 0x1F), length 02, value AA BB.
        AppendixREmvChipDataOracle.TlvDecodeResult decode = oracle.decode("9F1002AABB");
        assertThat(decode.wellFormed()).isTrue();
        assertThat(decode.entries()).extracting(AppendixREmvChipDataOracle.TlvEntry::tag).containsExactly("9F10");
    }

    @Test
    void acceptsOneByteTagWhenLastFiveBitsOfFirstByteAreNotAllOne() {
        // Tag 82 (one-byte: 0x82 & 0x1F == 0x02, not 0x1F), length 02, value 5C 00.
        AppendixREmvChipDataOracle.TlvDecodeResult decode = oracle.decode("82025C00");
        assertThat(decode.wellFormed()).isTrue();
        assertThat(decode.entries()).extracting(AppendixREmvChipDataOracle.TlvEntry::tag).containsExactly("82");
    }

    @Test
    void failsWhenNeitherAidNorDfNameTagIsPresent() {
        // AIP (82) only, no 9F06 or 84 anywhere.
        String noIdentifier = "82025C00";
        List<Assessment> assessments = oracle.assess("004", noIdentifier, new TransactionContext(true));
        Assessment mandatory = assessments.stream()
                .filter(a -> a.businessRequirementId().equals(AppendixREmvChipDataOracle.BR_MANDATORY_IDENTIFIER))
                .findFirst().orElseThrow();
        assertThat(mandatory.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void passesMandatoryIdentifierWhenOnlyDfNameIsPresent() {
        // DF name tag 84, length 07, value A0 00 00 00 04 10 10 (no AID present).
        String dfNameOnly = "8407A0000000041010";
        AppendixREmvChipDataOracle.TlvDecodeResult decode = oracle.decode(dfNameOnly);
        assertThat(decode.wellFormed()).isTrue();
        assertThat(oracle.assessMandatoryIdentifier(decode).status()).isEqualTo(Status.PASS);
    }

    @Test
    void failsWhenPanTagIsPresent() {
        // AID (9F06) present plus prohibited PAN tag 5A, length 08, 8 arbitrary bytes.
        String withPan = "9F0607A0000000980840" + "5A081111222233334444";
        List<Assessment> assessments = oracle.assess("018", withPan, new TransactionContext(true));
        Assessment prohibited = assessments.stream()
                .filter(a -> a.businessRequirementId().equals(AppendixREmvChipDataOracle.BR_PROHIBITED_SENSITIVE_TAG))
                .findFirst().orElseThrow();
        assertThat(prohibited.status()).isEqualTo(Status.FAIL);
        assertThat(prohibited.reason()).contains("5A");
    }

    @Test
    void failsWhenTrack2EquivalentDataTagIsPresent() {
        // AID (9F06) present plus prohibited Track 2 equivalent data tag 57, length 02, 2 arbitrary bytes.
        String withTrack2 = "9F0607A0000000980840" + "5702AABB";
        AppendixREmvChipDataOracle.TlvDecodeResult decode = oracle.decode(withTrack2);
        assertThat(decode.wellFormed()).isTrue();
        Assessment prohibited = oracle.assessProhibitedSensitiveTag(decode);
        assertThat(prohibited.status()).isEqualTo(Status.FAIL);
        assertThat(prohibited.reason()).contains("57");
    }

    @Test
    void flagsDuplicateTagAsReviewRequiredRatherThanFail() {
        // AID (9F06) appears twice.
        String duplicated = "9F0607A0000000980840" + "9F0607A0000000980840";
        AppendixREmvChipDataOracle.TlvDecodeResult decode = oracle.decode(duplicated);
        assertThat(decode.wellFormed()).isTrue();
        Assessment duplicate = oracle.assessDuplicateTagReview(decode);
        assertThat(duplicate.status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(duplicate.reason()).contains("9F06");
    }

    @Test
    void emvCompanionFailsWhenEmvTransactionButFieldsAreMissing() {
        Assessment assessment = oracle.assessEmvCompanion(null, null, new TransactionContext(true));
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void emvCompanionPassesWhenNotAnEmvTransactionRegardlessOfFieldPresence() {
        Assessment assessment = oracle.assessEmvCompanion(null, null, new TransactionContext(false));
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void emvCompanionIsReviewRequiredWhenContextIsUnknown() {
        Assessment assessment = oracle.assessEmvCompanion(GOLDEN_LENGTH, GOLDEN_CHIP_DATA, TransactionContext.unknown());
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void crossFieldAgreementIsAlwaysReviewRequired() {
        assertThat(oracle.assessCrossField().status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void acceptsBackslashDelimitedHexAsShownInTheAppendixRExample() {
        String backslashDelimited = "9F\\06\\07\\A0\\00\\00\\00\\98\\08\\40";
        AppendixREmvChipDataOracle.TlvDecodeResult decode = oracle.decode(backslashDelimited);
        assertThat(decode.wellFormed()).isTrue();
        assertThat(decode.entries()).hasSize(1);
        assertThat(decode.entries().get(0).tag()).isEqualTo("9F06");
    }
}
