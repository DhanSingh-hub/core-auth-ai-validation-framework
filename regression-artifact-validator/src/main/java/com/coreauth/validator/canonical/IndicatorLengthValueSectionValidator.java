package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.List;

/** Shared arithmetic for repeated three-digit indicator/length/value sections. */
public final class IndicatorLengthValueSectionValidator {
    public enum Problem {
        INVALID_INDICATOR,
        INVALID_LENGTH,
        MISSING_VALUE,
        VALUE_TOO_SHORT,
        VALUE_TOO_LONG,
        LENGTH_MISMATCH,
        SECTION_TOO_LONG
    }

    public record Triad(String indicator, String declaredLength, String value) {
    }

    public record Violation(int repetitionIndex, Problem problem, int actualLength, int configuredLimit) {
    }

    public record Result(int sectionLength, List<Violation> violations) {
        public Result {
            violations = List.copyOf(violations);
        }

        public boolean valid() {
            return violations.isEmpty();
        }
    }

    private IndicatorLengthValueSectionValidator() {
    }

    public static Result validate(List<Triad> triads, int minValueLength, int maxValueLength, int maxSectionLength) {
        List<Violation> violations = new ArrayList<>();
        int sectionLength = 0;
        for (int index = 0; index < triads.size(); index++) {
            Triad triad = triads.get(index);
            boolean indicatorValid = isThreeDigits(triad.indicator());
            boolean lengthValid = isThreeDigits(triad.declaredLength());
            if (!indicatorValid) {
                violations.add(new Violation(index, Problem.INVALID_INDICATOR, -1, 3));
            }
            if (!lengthValid) {
                violations.add(new Violation(index, Problem.INVALID_LENGTH, -1, 3));
            }
            if (triad.value() == null) {
                violations.add(new Violation(index, Problem.MISSING_VALUE, -1, maxValueLength));
                sectionLength += 6;
                continue;
            }

            int actualLength = triad.value().length();
            sectionLength += 6 + actualLength;
            if (actualLength < minValueLength) {
                violations.add(new Violation(index, Problem.VALUE_TOO_SHORT, actualLength, minValueLength));
            }
            if (actualLength > maxValueLength) {
                violations.add(new Violation(index, Problem.VALUE_TOO_LONG, actualLength, maxValueLength));
            }
            if (lengthValid && Integer.parseInt(triad.declaredLength()) != actualLength) {
                violations.add(new Violation(index, Problem.LENGTH_MISMATCH, actualLength,
                        Integer.parseInt(triad.declaredLength())));
            }
        }
        if (sectionLength > maxSectionLength) {
            violations.add(new Violation(-1, Problem.SECTION_TOO_LONG, sectionLength, maxSectionLength));
        }
        return new Result(sectionLength, violations);
    }

    private static boolean isThreeDigits(String value) {
        return value != null && value.matches("[0-9]{3}");
    }
}