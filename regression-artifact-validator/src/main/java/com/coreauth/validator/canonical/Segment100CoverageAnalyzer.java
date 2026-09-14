package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Produces deterministic rule coverage from canonical source anchors. */
public final class Segment100CoverageAnalyzer {

    public CoverageReport analyze(CanonicalArtifactPackage artifactPackage, List<SourceAnchor> expectedAnchors) {
        List<CoverageEntry> entries = new ArrayList<>();
        for (SourceAnchor expected : safe(expectedAnchors)) {
            String key = expected.canonicalKey();
            boolean requirement = contains(requirementAnchors(artifactPackage), key);
            boolean scenario = contains(scenarioAnchors(artifactPackage), key);
            boolean testCase = contains(testCaseAnchors(artifactPackage), key);
            boolean testData = contains(testDataAnchors(artifactPackage), key);
            boolean review = hasReviewData(artifactPackage, key);
            String status;
            if (review) {
                status = "REVIEW_REQUIRED";
            } else if (requirement && scenario && testCase && testData) {
                status = "COVERED";
            } else if (requirement || scenario || testCase || testData) {
                status = "PARTIALLY_COVERED";
            } else {
                status = "MISSING";
            }
            entries.add(new CoverageEntry(key, status, requirement, scenario, testCase, testData));
        }
        return new CoverageReport(entries);
    }

    private static Set<String> requirementAnchors(CanonicalArtifactPackage value) {
        Set<String> result = new HashSet<>();
        if (value != null && value.getBusinessRequirements() != null) {
            value.getBusinessRequirements().forEach(item -> anchors(item.getSourceAnchors(), result));
        }
        return result;
    }

    private static Set<String> scenarioAnchors(CanonicalArtifactPackage value) {
        Set<String> result = new HashSet<>();
        if (value != null && value.getTestScenarios() != null) {
            value.getTestScenarios().forEach(item -> anchors(item.getSourceAnchors(), result));
        }
        return result;
    }

    private static Set<String> testCaseAnchors(CanonicalArtifactPackage value) {
        Set<String> result = new HashSet<>();
        if (value != null && value.getTestCases() != null) {
            value.getTestCases().forEach(item -> anchors(item.getSourceAnchors(), result));
        }
        return result;
    }

    private static Set<String> testDataAnchors(CanonicalArtifactPackage value) {
        Set<String> result = new HashSet<>();
        if (value != null && value.getTestData() != null) {
            value.getTestData().forEach(item -> anchors(item.getSourceAnchors(), result));
        }
        return result;
    }

    private static boolean hasReviewData(CanonicalArtifactPackage value, String key) {
        if (value == null || value.getTestData() == null) {
            return false;
        }
        return value.getTestData().stream()
                .filter(item -> contains(item.getSourceAnchors(), key))
                .anyMatch(item -> "REVIEW".equalsIgnoreCase(item.getExpectedValidation())
                        || "PASS_WITH_REVIEW".equalsIgnoreCase(item.getExpectedValidation()));
    }

    private static void anchors(List<SourceAnchor> anchors, Set<String> destination) {
        if (anchors != null) {
            anchors.stream().filter(anchor -> anchor != null)
                    .map(SourceAnchor::canonicalKey).forEach(destination::add);
        }
    }

    private static boolean contains(List<SourceAnchor> anchors, String key) {
        if (anchors == null) {
            return false;
        }
        return anchors.stream().filter(anchor -> anchor != null)
                .map(SourceAnchor::canonicalKey).anyMatch(key::equals);
    }

    private static boolean contains(Set<String> anchors, String key) {
        return anchors.contains(key);
    }

    private static <T> List<T> safe(List<T> values) {
        return values == null ? Collections.emptyList() : values;
    }

    public record CoverageEntry(String anchorKey, String status, boolean requirement,
                                boolean scenario, boolean testCase, boolean testData) {
    }

    public static final class CoverageReport {
        private final List<CoverageEntry> entries;

        private CoverageReport(List<CoverageEntry> entries) {
            this.entries = List.copyOf(entries);
        }

        public List<CoverageEntry> entries() { return entries; }
        public long count(String status) {
            return entries.stream().filter(entry -> entry.status().equals(status)).count();
        }
        public boolean isComplete() {
            return entries.stream().allMatch(entry -> "COVERED".equals(entry.status()));
        }
    }
}