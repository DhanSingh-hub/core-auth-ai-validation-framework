package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalRequirement;
import com.coreauth.validator.canonical.CanonicalScenario;
import com.coreauth.validator.canonical.CanonicalTestCase;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.RequirementCrosswalkEntry;
import com.coreauth.validator.canonical.SourceAnchor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Selects one segment's linked canonical chain without inventing links for orphan artifacts. */
public final class CanonicalSegmentPackageFilter {

    public CanonicalArtifactPackage filter(CanonicalArtifactPackage source, String segment,
                                           List<RequirementCrosswalkEntry> crosswalk) {
        Set<String> mappedRequirementIds = new HashSet<>();
        for (RequirementCrosswalkEntry entry : safe(crosswalk)) {
            mappedRequirementIds.addAll(safe(entry.getAiRequirementIds()));
        }
        List<CanonicalRequirement> requirements = safe(source.getBusinessRequirements()).stream()
                .filter(requirement -> mappedRequirementIds.contains(requirement.getId())
                        || belongsToSegment(requirement.getSourceAnchors(), segment))
                .toList();
        Set<String> requirementIds = ids(requirements.stream().map(CanonicalRequirement::getId).toList());
        List<CanonicalScenario> scenarios = safe(source.getTestScenarios()).stream()
                .filter(value -> intersects(value.getRequirementIds(), requirementIds)).toList();
        Set<String> scenarioIds = ids(scenarios.stream().map(CanonicalScenario::getId).toList());
        List<CanonicalTestCase> testCases = safe(source.getTestCases()).stream()
                .filter(value -> intersects(value.getScenarioIds(), scenarioIds)).toList();
        Set<String> testCaseIds = ids(testCases.stream().map(CanonicalTestCase::getId).toList());
        List<CanonicalTestData> testData = safe(source.getTestData()).stream()
                .filter(value -> intersects(value.getTestCaseIds(), testCaseIds)).toList();

        CanonicalArtifactPackage result = new CanonicalArtifactPackage();
        result.setManifest(source.getManifest());
        result.setBusinessRequirements(requirements);
        result.setTestScenarios(scenarios);
        result.setTestCases(testCases);
        result.setTestData(testData);
        result.setRequirementCrosswalk(crosswalk);
        return result;
    }

    private static boolean belongsToSegment(List<SourceAnchor> anchors, String segment) {
        for (SourceAnchor anchor : safe(anchors)) {
            if (anchor == null || anchor.getSegment() == null) continue;
            for (String token : anchor.getSegment().split(",")) {
                if (segment.equalsIgnoreCase(token.trim().replace("ENT-SEG-", ""))) return true;
            }
        }
        return false;
    }

    private static boolean intersects(List<String> values, Set<String> expected) {
        return safe(values).stream().anyMatch(expected::contains);
    }

    private static Set<String> ids(List<String> values) {
        return new HashSet<>(values);
    }

    private static <T> List<T> safe(List<T> values) {
        return values == null ? List.of() : values;
    }
}