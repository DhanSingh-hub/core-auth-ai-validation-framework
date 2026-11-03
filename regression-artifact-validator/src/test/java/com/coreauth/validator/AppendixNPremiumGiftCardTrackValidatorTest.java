package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixNPremiumGiftCardTrackValidator;
import com.coreauth.validator.canonical.AppendixNPremiumGiftCardTrackValidator.Status;
import com.coreauth.validator.canonical.AppendixNPremiumGiftCardTrackValidator.TrackRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixNPremiumGiftCardTrackValidatorTest {
    private final AppendixNPremiumGiftCardTrackValidator validator = new AppendixNPremiumGiftCardTrackValidator();

    /** Account Number = BIN(401288) + Partial Account Number(123456789) + Visa mod-10 check digit(0). */
    private static final String VALID_ACCOUNT_NUMBER = "4012881234567890";

    private static TrackRecord validRecord() {
        return new TrackRecord(
                "%", VALID_ACCOUNT_NUMBER, "=", "2812", "101", "0", "1234", "DISCR123", ";", "1");
    }

    @Test
    void acceptsAllBoundedChecksForAWellFormedRecord() {
        List<AppendixNPremiumGiftCardTrackValidator.Assessment> assessments = validator.assess(validRecord());

        assertThat(assessments).allSatisfy(a -> assertThat(a.status()).isEqualTo(Status.PASS));
        assertThat(assessments).extracting(AppendixNPremiumGiftCardTrackValidator.Assessment::businessRequirementId)
                .containsExactlyInAnyOrder(
                        AppendixNPremiumGiftCardTrackValidator.BR_TRACK2_ACCOUNT,
                        AppendixNPremiumGiftCardTrackValidator.BR_TRACK2_FORMAT,
                        AppendixNPremiumGiftCardTrackValidator.BR_SERIAL_COMPONENT_LENGTHS,
                        AppendixNPremiumGiftCardTrackValidator.BR_SERIAL_NUMBER,
                        AppendixNPremiumGiftCardTrackValidator.BR_SENTINEL_EXCLUSION,
                        AppendixNPremiumGiftCardTrackValidator.BR_PVKI_UNUSED);
    }

    @Test
    void validatesAccountNumberRepresentationAndVisaModulo10CheckDigitWithoutAssertingBinRegistry() {
        var valid = validator.assessAccountNumber(VALID_ACCOUNT_NUMBER);
        assertThat(valid.status()).isEqualTo(Status.PASS);
        assertThat(valid.assertionScope()).isEqualTo("REPRESENTATION_AND_CHECK_DIGIT_ONLY");
        assertThat(valid.reason()).contains("not independently assertable");

        assertThat(validator.assessAccountNumber(null).status()).isEqualTo(Status.FAIL);
        assertThat(validator.assessAccountNumber("401288123456789").status()).isEqualTo(Status.FAIL); // 15 digits
        assertThat(validator.assessAccountNumber("40128812345678AB").status()).isEqualTo(Status.FAIL); // non-numeric
        assertThat(validator.assessAccountNumber("4012881234567891").status()).isEqualTo(Status.FAIL); // bad check digit
    }

    @Test
    void validatesTrack2FormatFieldsWithoutAssertingServiceCodeMeaningOrPvvCorrectness() {
        var valid = validator.assessTrack2Format(validRecord());
        assertThat(valid.status()).isEqualTo(Status.PASS);
        assertThat(valid.assertionScope()).isEqualTo("REPRESENTATION_ONLY");

        assertThat(validator.assessTrack2Format(withSeparator(validRecord(), "-")).status()).isEqualTo(Status.FAIL);
        assertThat(validator.assessTrack2Format(withExpiration(validRecord(), "2813")).status()).isEqualTo(Status.FAIL); // month 13
        assertThat(validator.assessTrack2Format(withExpiration(validRecord(), "281")).status()).isEqualTo(Status.FAIL); // too short
        assertThat(validator.assessTrack2Format(withServiceCode(validRecord(), "10")).status()).isEqualTo(Status.FAIL);
        assertThat(validator.assessTrack2Format(withPvv(validRecord(), "123")).status()).isEqualTo(Status.FAIL);
        assertThat(validator.assessTrack2Format(withDiscretionary(validRecord(), "SHORT")).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void decomposesAccountNumberIntoBinPartialAccountAndCheckDigitLengths() {
        var result = validator.assessSerialComponentLengths(VALID_ACCOUNT_NUMBER);
        assertThat(result.status()).isEqualTo(Status.PASS);
        assertThat(result.reason()).contains("BIN(6)").contains("Partial Account Number(9)").contains("check digit(1)");

        assertThat(validator.assessSerialComponentLengths("12345").status()).isEqualTo(Status.FAIL);
    }

    @Test
    void derivesSerialNumberFromPartialAccountNumberAndDiscretionaryDataPrefix() {
        var derived = validator.deriveSerialNumber(VALID_ACCOUNT_NUMBER, "DISCR123");
        assertThat(derived).contains("123456789DISC");

        var assessment = validator.assessSerialNumber(validRecord());
        assertThat(assessment.status()).isEqualTo(Status.PASS);
        assertThat(assessment.assertionScope()).isEqualTo("DERIVATION_ONLY");

        assertThat(validator.deriveSerialNumber(VALID_ACCOUNT_NUMBER, "ABC")).isEmpty(); // too short
        assertThat(validator.deriveSerialNumber("12345", "DISCR123")).isEmpty(); // bad account number
        assertThat(validator.assessSerialNumber(withDiscretionary(validRecord(), "AB")).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void transmittedPacketExcludesStartAndEndSentinelCharacters() {
        TrackRecord record = validRecord();
        String transmitted = validator.transmittedPacket(record);

        assertThat(transmitted).doesNotContain("%").doesNotContain(";");
        assertThat(transmitted).startsWith(VALID_ACCOUNT_NUMBER);

        assertThat(validator.assessSentinelExclusion(record).status()).isEqualTo(Status.PASS);
    }

    @Test
    void flagsSentinelLeakageIntoTheTransmittedPacket() {
        // Discretionary data accidentally contains the literal start-sentinel character.
        TrackRecord leaking = withDiscretionary(validRecord(), "DI%CR123");

        assertThat(validator.assessSentinelExclusion(leaking).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void checksPvkiPresenceAndLengthButNeverItsValue() {
        var present = validator.assessPvkiUnused("7");
        assertThat(present.status()).isEqualTo(Status.PASS);
        assertThat(present.assertionScope()).isEqualTo("LENGTH_ONLY_NO_VALUE_SEMANTICS");
        assertThat(present.reason()).contains("not currently used");

        assertThat(validator.assessPvkiUnused(null).status()).isEqualTo(Status.FAIL);
        assertThat(validator.assessPvkiUnused("12").status()).isEqualTo(Status.FAIL);
        assertThat(validator.assessPvkiUnused("").status()).isEqualTo(Status.FAIL);
    }

    private static TrackRecord withSeparator(TrackRecord r, String separator) {
        return new TrackRecord(r.startSentinel(), r.accountNumber(), separator, r.expirationDate(), r.serviceCode(),
                r.pvki(), r.pvv(), r.discretionaryData(), r.endSentinel(), r.lrc());
    }

    private static TrackRecord withExpiration(TrackRecord r, String expirationDate) {
        return new TrackRecord(r.startSentinel(), r.accountNumber(), r.separator(), expirationDate, r.serviceCode(),
                r.pvki(), r.pvv(), r.discretionaryData(), r.endSentinel(), r.lrc());
    }

    private static TrackRecord withServiceCode(TrackRecord r, String serviceCode) {
        return new TrackRecord(r.startSentinel(), r.accountNumber(), r.separator(), r.expirationDate(), serviceCode,
                r.pvki(), r.pvv(), r.discretionaryData(), r.endSentinel(), r.lrc());
    }

    private static TrackRecord withPvv(TrackRecord r, String pvv) {
        return new TrackRecord(r.startSentinel(), r.accountNumber(), r.separator(), r.expirationDate(), r.serviceCode(),
                r.pvki(), pvv, r.discretionaryData(), r.endSentinel(), r.lrc());
    }

    private static TrackRecord withDiscretionary(TrackRecord r, String discretionaryData) {
        return new TrackRecord(r.startSentinel(), r.accountNumber(), r.separator(), r.expirationDate(), r.serviceCode(),
                r.pvki(), r.pvv(), discretionaryData, r.endSentinel(), r.lrc());
    }
}
