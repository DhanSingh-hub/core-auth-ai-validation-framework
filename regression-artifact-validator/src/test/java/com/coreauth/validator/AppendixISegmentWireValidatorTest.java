package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixILogicalDataValidator.Status;
import com.coreauth.validator.canonical.AppendixISegmentWireValidator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixISegmentWireValidatorTest {
    private final AppendixISegmentWireValidator validator = new AppendixISegmentWireValidator();

    @Test
    void validatesContiguousRecordsAndRejectsSixIndependentFramingMutations() {
        String valid = "111\u001c018\u001c006003840\u001c";
        assertThat(validator.validate(valid, null).representationValid()).isTrue();
        String repeated = "111\u001c027\u001c006003840002003PWC\u001c";
        assertThat(validator.validate(repeated, null).representationValid()).isTrue();
        for (String mutation : List.of(
                valid.replaceFirst("111", "112"),
                valid.replace("018", "019"),
                valid.substring(0, valid.length() - 1),
                "111\u001c019\u001c006\u001c003840\u001c",
                valid.replace("006003", "006004"),
                "111\u001c021\u001c006003840999\u001c")) {
            assertThat(validator.validate(mutation, null).findings())
                    .anyMatch(finding -> finding.status() == Status.FAIL);
        }
    }

    @Test
    void doesNotInterpretConflictedWireOrClaimUnsupportedSemanticOrEncodingCoverage() {
        assertThat(validator.validate("111\u001c016\u001c0090015\u001c", null).findings())
                .anyMatch(finding -> finding.status() == Status.REVIEW_REQUIRED);
        assertThat(validator.validate("111\u001c016\u001c049001A\u001c", null).findings())
                .anyMatch(finding -> finding.status() == Status.NOT_ASSERTABLE);
        assertThat(validator.validate("111\u001c016\u001c026001\u00e9\u001c", null).findings())
                .anyMatch(finding -> finding.status() == Status.NOT_ASSERTABLE);
        assertThat(validator.validate("111\u001c018\u001c006000840\u001c", null).findings())
                .anyMatch(finding -> finding.status() == Status.FAIL);
        var mixed = validator.validate("111\u001c023\u001c001001A0090015\u001c", null);
        assertThat(mixed.findings()).anyMatch(finding -> finding.status() == Status.FAIL);
        assertThat(mixed.findings()).anyMatch(finding -> finding.status() == Status.REVIEW_REQUIRED);
    }
}
