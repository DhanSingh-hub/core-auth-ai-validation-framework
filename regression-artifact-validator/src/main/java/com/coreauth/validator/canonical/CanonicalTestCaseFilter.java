package com.coreauth.validator.canonical;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/** Selects canonical test cases without changing the package or traceability graph. */
public final class CanonicalTestCaseFilter {

    public List<CanonicalTestCase> filter(List<CanonicalTestCase> testCases, Criteria criteria) {
        if (testCases == null || testCases.isEmpty()) {
            return Collections.emptyList();
        }
        Criteria effective = criteria == null ? new Criteria(null, null, null, null, null) : criteria;
        Predicate<CanonicalTestCase> matches = testCase -> matches(testCase, effective);
        return testCases.stream().filter(matches).collect(Collectors.toList());
    }

    private boolean matches(CanonicalTestCase testCase, Criteria criteria) {
        return matchesValue(testCase.getCategory(), criteria.category())
                && matchesValue(testCase.getPriority(), criteria.priority())
                && matchesValue(testCase.getStatus(), criteria.status())
                && matchesValue(testCase.getExpectedOutcome(), criteria.expectedOutcome())
                && (criteria.tag() == null || containsIgnoreCase(testCase.getTags(), criteria.tag()));
    }

    private static boolean matchesValue(String actual, String expected) {
        return expected == null || (actual != null && actual.equalsIgnoreCase(expected));
    }

    private static boolean containsIgnoreCase(List<String> values, String expected) {
        return values != null && values.stream().filter(Objects::nonNull)
                .anyMatch(value -> value.trim().toLowerCase(Locale.ROOT)
                        .equals(expected.trim().toLowerCase(Locale.ROOT)));
    }

    public record Criteria(String category, String tag, String priority,
                           String status, String expectedOutcome) {
    }
}