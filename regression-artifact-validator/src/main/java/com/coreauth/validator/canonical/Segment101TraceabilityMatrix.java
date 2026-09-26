package com.coreauth.validator.canonical;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Item 4 (Traceability Matrix) for Segment 101.
 *
 * <p>Independently verifies that each rule in the Segment 101 rule catalog is traced through
 * the AI-generated BR → TS → TC → TD chain. Mirrors {@link Segment100TraceabilityMatrix}.
 */
public final class Segment101TraceabilityMatrix {

    public record TraceLink(
        String ruleId,
        String ruleTitle,
        String ruleClass,
        List<String> businessRequirements,
        List<String> testScenarios,
        List<String> testCases,
        List<String> testData,
        String coverageStatus
    ) {}

    public record MatrixReport(
        String catalogId,
        List<TraceLink> traceLinks,
        MatrixMetrics metrics
    ) {}

    public record MatrixMetrics(
        int totalRules,
        int fullyTraced,
        int partiallyTraced,
        int missingBusinessRequirements,
        int missingTestCases,
        int missingTestData,
        double completionPercentage
    ) {}

    private final Map<String, CanonicalRequirement> brById;
    private final Map<String, CanonicalScenario> tsById;
    private final Map<String, CanonicalTestCase> tcById;
    private final Map<String, CanonicalTestData> tdById;

    public Segment101TraceabilityMatrix(CanonicalArtifactPackage pkg) {
        this.brById = indexBy(pkg == null ? null : pkg.getBusinessRequirements(), CanonicalRequirement::getId);
        this.tsById = indexBy(pkg == null ? null : pkg.getTestScenarios(), CanonicalScenario::getId);
        this.tcById = indexBy(pkg == null ? null : pkg.getTestCases(), CanonicalTestCase::getId);
        this.tdById = indexBy(pkg == null ? null : pkg.getTestData(), CanonicalTestData::getId);
    }

    public MatrixReport generateMatrix(List<SourceAnchor> catalogRules) {
        List<TraceLink> traceLinks = new ArrayList<>();
        for (SourceAnchor ruleAnchor : catalogRules) {
            traceLinks.add(traceRule(ruleAnchor));
        }
        MatrixMetrics metrics = computeMetrics(traceLinks, catalogRules.size());
        return new MatrixReport("segment-101-traceability-matrix", traceLinks, metrics);
    }

    private TraceLink traceRule(SourceAnchor ruleAnchor) {
        List<String> brIds = brById.values().stream()
            .filter(br -> hasMatchingAnchor(br.getSourceAnchors(), ruleAnchor))
            .map(CanonicalRequirement::getId)
            .collect(Collectors.toList());

        List<String> tsIds = brIds.isEmpty() ? new ArrayList<>() :
            tsById.values().stream()
                .filter(ts -> ts.getRequirementIds() != null
                    && ts.getRequirementIds().stream().anyMatch(brIds::contains))
                .map(CanonicalScenario::getId)
                .collect(Collectors.toList());

        List<String> tcIds = tsIds.isEmpty() ? new ArrayList<>() :
            tcById.values().stream()
                .filter(tc -> tc.getScenarioIds() != null
                    && tc.getScenarioIds().stream().anyMatch(tsIds::contains))
                .map(CanonicalTestCase::getId)
                .collect(Collectors.toList());

        List<String> tdIds = tcIds.isEmpty() ? new ArrayList<>() :
            tdById.values().stream()
                .filter(td -> td.getTestCaseIds() != null
                    && td.getTestCaseIds().stream().anyMatch(tcIds::contains))
                .map(CanonicalTestData::getId)
                .collect(Collectors.toList());

        String status = computeStatus(brIds, tsIds, tcIds, tdIds);
        return new TraceLink(
            ruleAnchor.getRule(),
            describeRule(ruleAnchor),
            classifyRule(ruleAnchor),
            brIds, tsIds, tcIds, tdIds,
            status
        );
    }

    private static boolean hasMatchingAnchor(List<SourceAnchor> anchors, SourceAnchor target) {
        if (anchors == null) {
            return false;
        }
        for (SourceAnchor a : anchors) {
            if (anchorsEqual(a, target)) {
                return true;
            }
        }
        return false;
    }

    private static boolean anchorsEqual(SourceAnchor a, SourceAnchor b) {
        if (a == null || b == null) {
            return false;
        }
        return equalsIgnoreCase(a.getSpecification(), b.getSpecification())
            && equalsIgnoreCase(a.getVersion(), b.getVersion())
            && equalsIgnoreCase(a.getRule(), b.getRule());
    }

    private static boolean equalsIgnoreCase(String x, String y) {
        return (x == null && y == null) || (x != null && x.equalsIgnoreCase(y));
    }

    private static String computeStatus(List<String> br, List<String> ts, List<String> tc, List<String> td) {
        if (!br.isEmpty() && !ts.isEmpty() && !tc.isEmpty() && !td.isEmpty()) {
            return "FULLY_TRACED";
        }
        if (!br.isEmpty() || !ts.isEmpty() || !tc.isEmpty() || !td.isEmpty()) {
            return "PARTIALLY_TRACED";
        }
        return "MISSING_TRACE";
    }

    private MatrixMetrics computeMetrics(List<TraceLink> traceLinks, int totalRules) {
        long fullyTraced = traceLinks.stream().filter(l -> "FULLY_TRACED".equals(l.coverageStatus)).count();
        long partiallyTraced = traceLinks.stream().filter(l -> "PARTIALLY_TRACED".equals(l.coverageStatus)).count();
        long missingBr = traceLinks.stream().filter(l -> l.businessRequirements.isEmpty()).count();
        long missingTc = traceLinks.stream().filter(l -> l.testCases.isEmpty() && !l.businessRequirements.isEmpty()).count();
        long missingTd = traceLinks.stream().filter(l -> l.testData.isEmpty() && !l.testCases.isEmpty()).count();
        double completion = totalRules == 0 ? 0 : (fullyTraced * 100.0) / totalRules;
        return new MatrixMetrics(totalRules, (int) fullyTraced, (int) partiallyTraced,
            (int) missingBr, (int) missingTc, (int) missingTd, completion);
    }

    private static String classifyRule(SourceAnchor a) {
        if (a.getElement() != null && !a.getElement().isEmpty()) {
            return "field";
        }
        if (a.getSegment() != null && a.getSegment().contains(",")) {
            return "compatibility";
        }
        String rule = a.getRule() == null ? "" : a.getRule();
        if (rule.contains("fleet-tag")) {
            return "fleet-tag";
        }
        if (rule.contains("segment-length") || rule.contains("field-order") || rule.contains("separator")) {
            return "serialization";
        }
        if (rule.contains("required") || rule.contains("companion") || rule.contains("occurrence")) {
            return "structure";
        }
        return "other";
    }

    private static String describeRule(SourceAnchor a) {
        return a.getRule() != null ? a.getRule() : "unknown-rule";
    }

    private static <T> Map<String, T> indexBy(List<T> items, java.util.function.Function<T, String> keyFn) {
        Map<String, T> map = new HashMap<>();
        if (items == null) {
            return map;
        }
        for (T item : items) {
            if (item != null && keyFn.apply(item) != null) {
                map.put(keyFn.apply(item), item);
            }
        }
        return map;
    }
}
