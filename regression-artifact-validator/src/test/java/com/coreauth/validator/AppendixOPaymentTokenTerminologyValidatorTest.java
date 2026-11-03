package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixOPaymentTokenTerminologyValidator;
import com.coreauth.validator.canonical.AppendixOPaymentTokenTerminologyValidator.Status;
import com.coreauth.validator.canonical.AppendixOPaymentTokenTerminologyValidator.TokenContext;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixOPaymentTokenTerminologyValidatorTest {
    private final AppendixOPaymentTokenTerminologyValidator validator = new AppendixOPaymentTokenTerminologyValidator();

    /** 16-digit numeric value that passes the LUHN check digit (also a valid Visa mod-10 account number). */
    private static final String VALID_TOKEN = "4012881234567890";
    private static final String DIFFERENT_PAN = "4111111111111111";

    /** Base64 of 28 sequential bytes (0x01-0x1C). */
    private static final String CRYPTOGRAM_28_BYTES = "AQIDBAUGBwgJCgsMDQ4PEBESExQVFhcYGRobHA==";
    /** Base64 of 56 sequential bytes (0x01-0x38): Block A = bytes 1-28, Block B = bytes 29-56. */
    private static final String CRYPTOGRAM_56_BYTES =
            "AQIDBAUGBwgJCgsMDQ4PEBESExQVFhcYGRobHB0eHyAhIiMkJSYnKCkqKywtLi8wMTIzNDU2Nzg=";
    private static final String CRYPTOGRAM_56_BLOCK_B_BASE64 = "HR4fICEiIyQlJicoKSorLC0uLzAxMjM0NTY3OA==";

    private static TokenContext validContext() {
        return new TokenContext(VALID_TOKEN, DIFFERENT_PAN, "2812", CRYPTOGRAM_56_BYTES, "100");
    }

    @Test
    void acceptsAllBoundedChecksForAWellFormedTokenContext() {
        List<AppendixOPaymentTokenTerminologyValidator.Assessment> assessments = validator.assess(validContext());

        assertThat(assessments).allSatisfy(a -> assertThat(a.status()).isEqualTo(Status.PASS));
        assertThat(assessments).extracting(AppendixOPaymentTokenTerminologyValidator.Assessment::businessRequirementId)
                .containsExactlyInAnyOrder(
                        AppendixOPaymentTokenTerminologyValidator.BR_TOKEN_ACCOUNT,
                        AppendixOPaymentTokenTerminologyValidator.BR_TOKEN_LUHN,
                        AppendixOPaymentTokenTerminologyValidator.BR_TOKEN_NOT_PAN,
                        AppendixOPaymentTokenTerminologyValidator.BR_TOKEN_EXPIRATION,
                        AppendixOPaymentTokenTerminologyValidator.BR_CRYPTOGRAM,
                        AppendixOPaymentTokenTerminologyValidator.BR_CRYPTOGRAM_BLOCK_SPLIT,
                        AppendixOPaymentTokenTerminologyValidator.BR_ENTRY_MODE);
    }

    @Test
    void validatesTokenAccountRepresentationWithoutAssertingBinRegistryMembership() {
        var valid = validator.assessTokenAccountRepresentation(VALID_TOKEN);
        assertThat(valid.status()).isEqualTo(Status.PASS);
        assertThat(valid.assertionScope()).isEqualTo("REPRESENTATION_ONLY");
        assertThat(valid.reason()).contains("not independently assertable");

        assertThat(validator.assessTokenAccountRepresentation(null).status()).isEqualTo(Status.FAIL);
        assertThat(validator.assessTokenAccountRepresentation("123456789012").status()).isEqualTo(Status.FAIL); // 12 digits
        assertThat(validator.assessTokenAccountRepresentation("12345678901234567890").status()).isEqualTo(Status.FAIL); // 20 digits
        assertThat(validator.assessTokenAccountRepresentation("401288123456789A").status()).isEqualTo(Status.FAIL); // non-numeric
    }

    @Test
    void validatesTokenLuhnCheckDigitWithoutAssertingTokenBinRange() {
        var valid = validator.assessTokenLuhn(VALID_TOKEN);
        assertThat(valid.status()).isEqualTo(Status.PASS);
        assertThat(valid.assertionScope()).isEqualTo("LUHN_ALGORITHM_ONLY");
        assertThat(valid.reason()).contains("not independently assertable");

        assertThat(validator.assessTokenLuhn("4012881234567891").status()).isEqualTo(Status.FAIL); // bad check digit
        assertThat(validator.assessTokenLuhn("12345").status()).isEqualTo(Status.FAIL); // too short
        assertThat(validator.assessTokenLuhn(null).status()).isEqualTo(Status.FAIL);
    }

    @Test
    void validatesTokenIsNotTheSameValueAsTheCardholderPan() {
        var differs = validator.assessTokenNotPan(VALID_TOKEN, DIFFERENT_PAN);
        assertThat(differs.status()).isEqualTo(Status.PASS);
        assertThat(differs.assertionScope()).isEqualTo("VALUE_INEQUALITY_ONLY");

        var collision = validator.assessTokenNotPan(VALID_TOKEN, VALID_TOKEN);
        assertThat(collision.status()).isEqualTo(Status.FAIL);

        var noPan = validator.assessTokenNotPan(VALID_TOKEN, null);
        assertThat(noPan.status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(noPan.assertionScope()).isEqualTo("NOT_ASSERTABLE_WITHOUT_PAN");
    }

    @Test
    void validatesTokenExpirationDateFormatAndTreatsAbsenceAsReviewRequired() {
        var valid = validator.assessTokenExpiration("2812");
        assertThat(valid.status()).isEqualTo(Status.PASS);
        assertThat(valid.assertionScope()).isEqualTo("FORMAT_ONLY");

        var absent = validator.assessTokenExpiration(null);
        assertThat(absent.status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(absent.assertionScope()).isEqualTo("PRESENCE_NOT_DETERMINABLE");

        assertThat(validator.assessTokenExpiration("2813").status()).isEqualTo(Status.FAIL); // month 13
        assertThat(validator.assessTokenExpiration("281").status()).isEqualTo(Status.FAIL); // too short
    }

    @Test
    void validatesCryptogramDecodesToTwentyEightOrFiftySixBytes() {
        var valid28 = validator.assessCryptogram(CRYPTOGRAM_28_BYTES);
        assertThat(valid28.status()).isEqualTo(Status.PASS);
        assertThat(valid28.assertionScope()).isEqualTo("LENGTH_ONLY");
        assertThat(valid28.reason()).contains("28 bytes");

        var valid56 = validator.assessCryptogram(CRYPTOGRAM_56_BYTES);
        assertThat(valid56.status()).isEqualTo(Status.PASS);
        assertThat(valid56.reason()).contains("56 bytes");

        assertThat(validator.assessCryptogram(null).status()).isEqualTo(Status.FAIL);
        assertThat(validator.assessCryptogram("not-valid-base64!!").status()).isEqualTo(Status.FAIL);
        assertThat(validator.assessCryptogram("AQID").status()).isEqualTo(Status.FAIL); // decodes but wrong length
    }

    @Test
    void splitsFiftySixByteCryptogramIntoBlockAAndBlockB() {
        var blocks = validator.deriveCryptogramBlocks(CRYPTOGRAM_56_BYTES);
        assertThat(blocks).isPresent();
        assertThat(blocks.get().blockABase64()).isEqualTo(CRYPTOGRAM_28_BYTES);
        assertThat(blocks.get().blockBBase64()).isEqualTo(CRYPTOGRAM_56_BLOCK_B_BASE64);

        var assessment = validator.assessCryptogramBlockSplit(CRYPTOGRAM_56_BYTES);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
        assertThat(assessment.reason()).contains("Block A followed by Token Data Block B");
    }

    @Test
    void splitsTwentyEightByteCryptogramIntoBlockAOnly() {
        var blocks = validator.deriveCryptogramBlocks(CRYPTOGRAM_28_BYTES);
        assertThat(blocks).isPresent();
        assertThat(blocks.get().blockABase64()).isEqualTo(CRYPTOGRAM_28_BYTES);
        assertThat(blocks.get().blockBBase64()).isNull();

        var assessment = validator.assessCryptogramBlockSplit(CRYPTOGRAM_28_BYTES);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
        assertThat(assessment.reason()).contains("Block A only");
    }

    @Test
    void rejectsCryptogramBlockSplitForInvalidLengthOrBase64() {
        assertThat(validator.deriveCryptogramBlocks("AQID")).isEmpty(); // valid base64, wrong length
        assertThat(validator.deriveCryptogramBlocks(null)).isEmpty();
        assertThat(validator.assessCryptogramBlockSplit("AQID").status()).isEqualTo(Status.FAIL);
    }

    @Test
    void validatesPosEntryModeIsARecognizedAppendixJCodeWithoutClaimingTokenPresentmentSemantics() {
        var valid = validator.assessEntryMode("100");
        assertThat(valid.status()).isEqualTo(Status.PASS);
        assertThat(valid.assertionScope()).isEqualTo("CODE_RECOGNITION_ONLY");
        assertThat(valid.reason()).contains("does not identify which codes");

        assertThat(validator.assessEntryMode("999").status()).isEqualTo(Status.FAIL); // unrecognized PAN entry mode
        assertThat(validator.assessEntryMode("AB1").status()).isEqualTo(Status.FAIL); // malformed
        assertThat(validator.assessEntryMode(null).status()).isEqualTo(Status.FAIL);
    }
}
