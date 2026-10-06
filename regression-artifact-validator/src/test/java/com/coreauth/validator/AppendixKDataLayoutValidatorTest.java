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
}