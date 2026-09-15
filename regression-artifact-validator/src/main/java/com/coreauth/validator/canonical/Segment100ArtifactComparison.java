package com.coreauth.validator.canonical;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Compares AI artifact anchors (BR, TS, TC, TD) with the independently maintained rule catalog. */
public final class Segment100ArtifactComparison {

    public ComparisonReport compare(CanonicalArtifactPackage aiPackage,
                                     List<SourceAnchor> independentAnchors) {
        Set<String> expected = anchorKeys(independentAnchors);
        Set<String> actual = extractAllAiAnchors(aiPackage);

        Set<String> matched = new HashSet<>(actual);
        matched.retainAll(expected);
        Set<String> missing = new HashSet<>(expected);
        missing.removeAll(actual);
        Set<String> extra = new HashSet<>(actual);
        extra.removeAll(expected);
        return new ComparisonReport(matched, missing, extra, actual.size(), expected.size());
    }

    private static Set<String> extractAllAiAnchors(CanonicalArtifactPackage pkg) {
        Set<String> result = new HashSet<>();
        if (pkg == null) {
            return result;
        }
        addAnchorsFrom(pkg.getBusinessRequirements(), result);
        addAnchorsFrom(pkg.getTestScenarios(), result);
        addAnchorsFrom(pkg.getTestCases(), result);
        addAnchorsFrom(pkg.getTestData(), result);
        return result;
    }

    private static void addAnchorsFrom(List<?> items, Set<String> destination) {
        if (items == null) {
            return;
        }
        for (Object item : items) {
            List<SourceAnchor> anchors = null;
            if (item instanceof CanonicalRequirement req) {
                anchors = req.getSourceAnchors();
            } else if (item instanceof CanonicalScenario scenario) {
                anchors = scenario.getSourceAnchors();
            } else if (item instanceof CanonicalTestCase testCase) {
                anchors = testCase.getSourceAnchors();
            } else if (item instanceof CanonicalTestData testData) {
                anchors = testData.getSourceAnchors();
            }
            if (anchors != null) {
                for (SourceAnchor anchor : anchors) {
                    if (anchor != null) {
                        destination.add(anchor.canonicalKey());
                    }
                }
            }
        }
    }

    private static Set<String> anchorKeys(List<SourceAnchor> anchors) {
        Set<String> result = new HashSet<>();
        for (SourceAnchor anchor : safe(anchors)) {
            if (anchor != null) {
                result.add(anchor.canonicalKey());
            }
        }
        return result;
    }

    private static <T> List<T> safe(List<T> values) {
        return values == null ? Collections.emptyList() : values;
    }

    public record ComparisonReport(Set<String> matched, Set<String> missing, Set<String> extra,
                                   int aiAnchorCount, int independentRuleCount) {
        public ComparisonReport {
            matched = Set.copyOf(matched);
            missing = Set.copyOf(missing);
            extra = Set.copyOf(extra);
        }

        public double precision() {
            return aiAnchorCount == 0 ? 1.0 : (double) matched.size() / aiAnchorCount;
        }

        public double recall() {
            return independentRuleCount == 0 ? 1.0 : (double) matched.size() / independentRuleCount;
        }

        public boolean hasDifferences() {
            return !missing.isEmpty() || !extra.isEmpty();
        }

        public String overallDecision() {
            if (!missing.isEmpty()) {
                return "REJECTED_MISSING_COVERAGE";
            }
            if (!extra.isEmpty()) {
                return "APPROVED_WITH_REVIEW_ITEMS";
            }
            return "APPROVED";
        }
    }
}