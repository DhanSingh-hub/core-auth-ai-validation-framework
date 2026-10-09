package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixIParRequestOracle;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixIParRequestOracleTest {
    private final AppendixIParRequestOracle oracle = new AppendixIParRequestOracle();

    @Test
    void acceptsTheSourceDefinedParRequestIndicator() {
        var result = oracle.assess("032", "14", "001", "Y");

        assertThat(result.status()).isEqualTo(AppendixIParRequestOracle.Status.PASS);
        assertThat(result.assertionScope()).isEqualTo("PAR_REQUEST_REPRESENTATION");
    }

    @Test
    void rejectsWrongSelectorLengthAndValue() {
        assertThat(oracle.assess("031", "14", "001", "Y").status())
                .isEqualTo(AppendixIParRequestOracle.Status.FAIL);
        assertThat(oracle.assess("032", "15", "001", "Y").status())
                .isEqualTo(AppendixIParRequestOracle.Status.FAIL);
        assertThat(oracle.assess("032", "14", "002", "Y").status())
                .isEqualTo(AppendixIParRequestOracle.Status.FAIL);
        assertThat(oracle.assess("032", "14", "001", "N").status())
                .isEqualTo(AppendixIParRequestOracle.Status.FAIL);
    }

    @Test
    void leavesAbsentIndicatorReviewRequiredWithoutMerchantOptInContext() {
        var result = oracle.assess(null, null, null, null);

        assertThat(result.status()).isEqualTo(AppendixIParRequestOracle.Status.REVIEW_REQUIRED);
        assertThat(result.reason()).contains("opt-in context");
    }
}
