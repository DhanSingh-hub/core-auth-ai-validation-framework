package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixKDataLayoutValidator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixKDataLayoutValidatorTest {
    private final AppendixKDataLayoutValidator validator = new AppendixKDataLayoutValidator();

    @Test
    void validatesBalanceRepresentationWithoutInferringBalanceEligibility() {
        var valid = validator.validate("001", "123456", null);
        var invalid = validator.validate("001", "1234567", null);

        assertThat(valid.status()).isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(valid.assertionScope()).isEqualTo("REPRESENTATION_ONLY");
        assertThat(invalid.status()).isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
    }

    @Test
    void validatesAvsNvsEnvelopeButDoesNotAssertIssuerCodeMeaning() {
        assertThat(validator.validate("003", "AVS+A", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("003", "NVS-M", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("003", "AVS+A NVS+M", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("003", "AVS+ANVS+M", null).assertionScope())
                .isEqualTo("REPRESENTATION_ONLY");
    }

    @Test
    void requiresNetworkContextForCardVerificationCodeMeaning() {
        assertThat(validator.validate("004", "M", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.REVIEW_REQUIRED);
        assertThat(validator.validate("004", "M", "VISA").status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("004", "Y", "VISA").status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("004", "MATCH", "VISA").status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
    }

    @Test
    void keepsUnimplementedLayoutsAndUnknownNetworksReviewGated() {
        assertThat(validator.validate("008", "ANY", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.NOT_ASSERTABLE);
        assertThat(validator.validate("004", "M", "UNKNOWN").status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.REVIEW_REQUIRED);
    }

    @Test
    void validatesAccountTypeAndFundingSourceValueSetsWithoutAssertingApplicability() {
        for (String value : new String[] {"00", "20", "30", "35", "36", "60"}) {
            var result = validator.validate("036", value, null);
            assertThat(result.status()).isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
            assertThat(result.assertionScope()).isEqualTo("REPRESENTATION_ONLY");
        }
        for (String value : new String[] {"C", "D", "P"}) {
            var result = validator.validate("037", value, null);
            assertThat(result.status()).isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
            assertThat(result.reason()).contains("Visa applicability");
        }

        assertThat(validator.validate("036", "99", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("036", "2", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("037", "X", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("037", "CD", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
    }

    @Test
    void validatesCarwashLanguageAndDstRepresentationOnly() {
        assertThat(validator.validate("024", "Y", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("024", "YES", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("025", "E", null).assertionScope())
                .isEqualTo("LENGTH_ONLY");
        assertThat(validator.validate("025", "EN", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("026", "A1B2C3D4E5F6G7H8", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("026", "A1B2C3D4E5F6G7!8", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
    }

    @Test
    void validatesFixedAndMaximumLengthFormatsWithoutInferringFieldSemantics() {
        assertThat(validator.validate("028", "123456789012345", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("028", "123", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("029", "12345678901234567890123456789", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("029", "A".repeat(29), null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("029", "A".repeat(28), null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("030", "A1", null).assertionScope())
                .isEqualTo("REPRESENTATION_ONLY");
        assertThat(validator.validate("031", "?1", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("032", "A B ", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("035", "X".repeat(50), null).assertionScope())
                .isEqualTo("MAX_LENGTH_ONLY");
        assertThat(validator.validate("035", "X".repeat(51), null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("038", "X".repeat(22), null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("038", "X".repeat(23), null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("039", "3", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("039", "4", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
    }

    @Test
    void validatesRemainingSourceDefinedCodeSetsAndNumericBounds() {
        assertThat(validator.validate("040", "123", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("040", "12A", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("041", "12", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);

        for (String value : new String[] {"1", "2"}) {
            assertThat(validator.validate("042", value, null).status())
                    .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        }
        assertThat(validator.validate("042", "3", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("043", "Y", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("043", "N", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);

        assertThat(validator.validate("044", "X".repeat(95), null).assertionScope())
                .isEqualTo("MAX_LENGTH_ONLY");
        assertThat(validator.validate("044", "X".repeat(96), null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        assertThat(validator.validate("045", "7", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        assertThat(validator.validate("046", "A", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
        for (String value : new String[] {"1", "2", "3"}) {
            assertThat(validator.validate("047", value, null).status())
                    .isEqualTo(AppendixKDataLayoutValidator.Status.PASS);
        }
        assertThat(validator.validate("047", "4", null).status())
                .isEqualTo(AppendixKDataLayoutValidator.Status.FAIL);
    }
}