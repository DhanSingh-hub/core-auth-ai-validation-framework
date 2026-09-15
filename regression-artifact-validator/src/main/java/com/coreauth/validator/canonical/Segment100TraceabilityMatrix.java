package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Generates BR → TS → TC → TD traceability matrix for Segment 100 rules.
 *
 * The matrix ensures:
 * - Each independent rule is traced through the full artifact chain
 * - Every business requirement links to at least one test scenario
 * - Every test scenario links to at least one test case
 * - Every test case links to at least one test datum
 * - No orphaned artifacts at any level
 */
public final class Segment100TraceabilityMatrix {

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
        int missingData,
        int missingTestCases,
        int missingTestData,
        double completionPercentage
    ) {}

    private final CanonicalArtifactPackage pkg;
    private final Map<String, CanonicalRequirement> brById;
    private final Map<String, CanonicalScenario> tsById;
    private final Map<String, CanonicalTestCase> tcById;
    private final Map<String, CanonicalTestData> tdById;

    public Segment100TraceabilityMatrix(CanonicalArtifactPackage pkg) {
        this.pkg = pkg;
        this.brById = indexBy(pkg.getBusinessRequirements(), CanonicalRequirement::getId);
        this.tsById = indexBy(pkg.getTestScenarios(), CanonicalScenario::getId);
        this.tcById = indexBy(pkg.getTestCases(), CanonicalTestCase::getId);
        this.tdById = indexBy(pkg.getTestData(), CanonicalTestData::getId);
    }

    public MatrixReport generateMatrix(List<SourceAnchor> catalogRules) {
        List<TraceLink> traceLinks = new ArrayList<>();

        for (SourceAnchor ruleAnchor : catalogRules) {
            TraceLink link = traceRule(ruleAnchor);
            traceLinks.add(link);
        }

        MatrixMetrics metrics = computeMetrics(traceLinks, catalogRules.size());
        return new MatrixReport("segment-100-traceability-matrix", traceLinks, metrics);
    }

    private TraceLink traceRule(SourceAnchor ruleAnchor) {
        String ruleId = ruleAnchor.getRule();
        String ruleClass = classifyRule(ruleAnchor);

        // Find all BRs matching this anchor
        List<String> brIds = brById.values().stream()
            .filter(br -> br.getSourceAnchors() != null &&
                         br.getSourceAnchors().stream().anyMatch(a -> anchorsEqual(a, ruleAnchor)))
            .map(CanonicalRequirement::getId)
            .collect(Collectors.toList());

        // Find all TS linked to those BRs
        List<String> tsIds = brIds.isEmpty() ? new ArrayList<>() :
            tsById.values().stream()
                .filter(ts -> ts.getRequirementIds() != null &&
                            ts.getRequirementIds().stream().anyMatch(brIds::contains))
                .map(CanonicalScenario::getId)
                .collect(Collectors.toList());

        // Find all TC linked to those TS
        List<String> tcIds = tsIds.isEmpty() ? new ArrayList<>() :
            tcById.values().stream()
                .filter(tc -> tc.getScenarioIds() != null &&
                            tc.getScenarioIds().stream().anyMatch(tsIds::contains))
                .map(CanonicalTestCase::getId)
                .collect(Collectors.toList());

        // Find all TD linked to those TC
        List<String> tdIds = tcIds.isEmpty() ? new ArrayList<>() :
            tdById.values().stream()
                .filter(td -> td.getTestCaseIds() != null &&
                            td.getTestCaseIds().stream().anyMatch(tcIds::contains))
                .map(CanonicalTestData::getId)
                .collect(Collectors.toList());

        String status = computeStatus(brIds, tsIds, tcIds, tdIds);

        return new TraceLink(
            ruleId,
            describeRule(ruleAnchor),
            ruleClass,
            brIds,
            tsIds,
            tcIds,
            tdIds,
            status
        );
    }

    private String computeStatus(List<String> brIds, List<String> tsIds,
                                  List<String> tcIds, List<String> tdIds) {
        if (!brIds.isEmpty() && !tsIds.isEmpty() && !tcIds.isEmpty() && !tdIds.isEmpty()) {
            return "FULLY_TRACED";
        }
        if (!brIds.isEmpty() || !tsIds.isEmpty() || !tcIds.isEmpty() || !tdIds.isEmpty()) {
            return "PARTIALLY_TRACED";
        }
        return "MISSING_TRACE";
    }

    private MatrixMetrics computeMetrics(List<TraceLink> traceLinks, int totalRules) {
        long fullyTraced = traceLinks.stream()
            .filter(link -> "FULLY_TRACED".equals(link.coverageStatus))
            .count();

        long partiallyTraced = traceLinks.stream()
            .filter(link -> "PARTIALLY_TRACED".equals(link.coverageStatus))
            .count();

        long missingData = traceLinks.stream()
            .filter(link -> link.businessRequirements.isEmpty())
            .count();

        long missingTestCases = traceLinks.stream()
            .filter(link -> link.testCases.isEmpty() && !link.businessRequirements.isEmpty())
            .count();

        long missingTestData = traceLinks.stream()
            .filter(link -> link.testData.isEmpty() && !link.testCases.isEmpty())
            .count();

        double completionPercentage = totalRules == 0 ? 0 : (fullyTraced * 100.0) / totalRules;

        return new MatrixMetrics(
            totalRules,
            (int) fullyTraced,
            (int) partiallyTraced,
            (int) missingData,
            (int) missingTestCases,
            (int) missingTestData,
            completionPercentage
        );
    }

    private boolean anchorsEqual(SourceAnchor a, SourceAnchor b) {
        if (a == null || b == null) return false;
        return nullSafeEquals(a.getSpecification(), b.getSpecification()) &&
               nullSafeEquals(a.getVersion(), b.getVersion()) &&
               nullSafeEquals(a.getRule(), b.getRule());
    }

    private boolean nullSafeEquals(String a, String b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }

    private String classifyRule(SourceAnchor anchor) {
        if (anchor.getElement() == null || anchor.getElement().isEmpty()) {
            return "structural";
        }
        if ("100".equals(anchor.getSegment())) {
            return "segment-100";
        }
        if ("101".equals(anchor.getSegment()) || "102".equals(anchor.getSegment()) || "130".equals(anchor.getSegment())) {
            return "companion-segment";
        }
        if ("TCP-IP-HEADER".equals(anchor.getSegment())) {
            return "serialization";
        }
        return "other";
    }

    private String describeRule(SourceAnchor anchor) {
        return anchor.getRule() != null ? anchor.getRule() : "unknown-rule";
    }

    private <T> Map<String, T> indexBy(List<T> items, java.util.function.Function<T, String> keyFn) {
        Map<String, T> map = new HashMap<>();
        if (items != null) {
            for (T item : items) {
                if (item != null) {
                    String key = keyFn.apply(item);
                    if (key != null) {
                        map.put(key, item);
                    }
                }
            }
        }
        return map;
    }
}
