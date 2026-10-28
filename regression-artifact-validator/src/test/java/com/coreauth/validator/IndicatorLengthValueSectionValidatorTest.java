package com.coreauth.validator;

import com.coreauth.validator.canonical.IndicatorLengthValueSectionValidator;
import com.coreauth.validator.canonical.IndicatorLengthValueSectionValidator.Problem;
import com.coreauth.validator.canonical.IndicatorLengthValueSectionValidator.Triad;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class IndicatorLengthValueSectionValidatorTest {
    @Test
    void appliesCallerSpecificValueAndAggregateLimitsToSharedTriadMath() {
        List<Triad> triads = List.of(new Triad("005", "003", "ABC"), new Triad("006", "004", "WXYZ"));

        var segment111 = IndicatorLengthValueSectionValidator.validate(triads, 1, 985, 991);
        var segment112 = IndicatorLengthValueSectionValidator.validate(triads, 1, 984, 990);

        assertThat(segment111.sectionLength()).isEqualTo(19);
        assertThat(segment112.sectionLength()).isEqualTo(19);
        assertThat(segment111.valid()).isTrue();
        assertThat(segment112.valid()).isTrue();
        assertThat(IndicatorLengthValueSectionValidator.validate(
                List.of(new Triad("005", "985", "A".repeat(985))), 1, 985, 991).valid()).isTrue();
        assertThat(IndicatorLengthValueSectionValidator.validate(
                List.of(new Triad("005", "985", "A".repeat(985))), 1, 984, 990).violations())
                .extracting(IndicatorLengthValueSectionValidator.Violation::problem)
                .contains(Problem.VALUE_TOO_LONG, Problem.SECTION_TOO_LONG);
    }

    @Test
    void reportsMalformedFieldsAndAggregateOverflowIndependently() {
        var result = IndicatorLengthValueSectionValidator.validate(
                List.of(new Triad("x", "00A", null), new Triad("006", "003", "ABCD")), 1, 4, 15);

        assertThat(result.sectionLength()).isEqualTo(16);
        assertThat(result.violations()).extracting(IndicatorLengthValueSectionValidator.Violation::problem)
                .contains(Problem.INVALID_INDICATOR, Problem.INVALID_LENGTH, Problem.MISSING_VALUE,
                        Problem.LENGTH_MISMATCH);
        assertThat(IndicatorLengthValueSectionValidator.validate(
                List.of(new Triad("005", "001", "A"), new Triad("006", "001", "B")), 1, 8, 13)
                .violations()).extracting(IndicatorLengthValueSectionValidator.Violation::problem)
                .contains(Problem.SECTION_TOO_LONG);
    }
}