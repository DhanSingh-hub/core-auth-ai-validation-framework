package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixILogicalDataValidator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.coreauth.validator.canonical.AppendixILogicalDataValidator.Status.*;
import static org.assertj.core.api.Assertions.assertThat;

class AppendixILogicalDataValidatorTest {
    private final AppendixILogicalDataValidator validator = new AppendixILogicalDataValidator();

    @Test
    void validatesSourceBackedRepresentationsWithoutClaimingBusinessOrWireReadiness() {
        for (String[] sample : List.of(new String[]{"001", "1".repeat(35)},
                new String[]{"002", "PWC"}, new String[]{"003", "12345    "},
                new String[]{"005", "012"}, new String[]{"006", "840"},
                new String[]{"007", "1234"}, new String[]{"008", "1".repeat(19)})) {
            assertThat(validator.validate(sample[0], sample[1], null).representationValid()).isTrue();
        }
        assertThat(validator.validate("004", "123", "VISA").representationValid()).isTrue();
        assertThat(validator.validate("004", "1234", "AMEX").representationValid()).isTrue();
    }

    @Test
    void catchesEverySourceCriticalFormatMutationInTheBoundedImplementedScope() {
        List<String[]> mutations = List.of(new String[]{"001", "1".repeat(36)},
                new String[]{"001", "12A"}, new String[]{"002", "XXX"},
                new String[]{"003", "12345   "}, new String[]{"003", "12345    " + "A".repeat(21)},
                new String[]{"004", "1234"}, new String[]{"005", "999"},
                new String[]{"005", "019"}, new String[]{"006", "84"},
                new String[]{"007", "123"}, new String[]{"007", "1234567"},
                new String[]{"008", "1".repeat(20)}, new String[]{"009", "2"});
        for (String[] mutation : mutations) {
            var result = validator.validate(mutation[0], mutation[1], "VISA");
            assertThat(result.findings()).as("Targeted mutation " + mutation[0])
                    .anyMatch(finding -> finding.status() == FAIL
                            && finding.ruleId().startsWith("APPI-T" + mutation[0] + "-R"));
            assertThat(result.representationValid()).isFalse();
        }
    }

    @Test
    void preservesUnresolvedContextAndExplicitlyUnsupportedScope() {
        assertThat(validator.validate("004", "123", null).findings())
                .anyMatch(finding -> finding.status() == REVIEW_REQUIRED);
        var partial = validator.validate("009", "5", null);
        assertThat(partial.findings()).anyMatch(finding -> finding.status() == PASS);
        assertThat(partial.findings()).anyMatch(finding -> finding.status() == REVIEW_REQUIRED);
        assertThat(partial.representationValid()).isFalse();
        assertThat(validator.validate("002", "WUG", null).representationValid()).isFalse();
        assertThat(validator.validate("049", "synthetic", null).findings())
                .anyMatch(finding -> finding.status() == NOT_ASSERTABLE);
        assertThat(validator.validate(null, "x", null).findings()).anyMatch(f -> f.status() == FAIL);
        assertThat(validator.validate("001", null, null).findings()).anyMatch(f -> f.status() == FAIL);
        assertThat(validator.validate("001", "", null).findings())
                .anyMatch(f -> f.ruleId().equals("APPI-E112-LENGTH") && f.status() == FAIL);
    }

    @Test
    void reportsOnlyRuleIdsAndReasonsNeverSensitiveInputValues() {
        String secret = "SYNTHETIC-CARD-VERIFICATION-SECRET";
        assertThat(validator.validate("004", secret, "VISA").toString()).doesNotContain(secret);
        assertThat(validator.validate("008", secret, null).toString()).doesNotContain(secret);
    }
}
