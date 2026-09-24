package com.coreauth.validator.canonical;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Item 4 (Traceability Matrix) for Segment 120.
 *
 * <p>Independently verifies that each rule in the Segment 120 rule catalog is traced through
 * the AI-generated BR → TS → TC → TD chain. Mirrors {@link Segment111TraceabilityMatrix} /
 * {@link Segment101TraceabilityMatrix}, with one addition: because Segment 120's rule catalog
 * carries {@code provisional}/{@code resolved} tags tied to its {@code provisionalItems} array
 * (P-01, P-02-WIDTH, P-02-RESIDUAL, P-03, P-04), this matrix accepts the full
 * {@link Segment120RuleCatalogLoader.Rule} list (not just {@link SourceAnchor}s) so it can report
 * which rules are PROVISIONAL/blocked. Exactly 4 of the 8 catalog rules carry such a tag:
 * SEG120-R-002 (resolved via P-02-WIDTH), SEG120-R-004 (open, P-02-RESIDUAL), SEG120-R-007
 * (resolved via P-01), and SEG120-R-008 (open, P-03).
 */
public final class Segment120TraceabilityMatrix {

    public record TraceLink(
        String ruleId,
        String ruleTitle,
        String ruleClass,
        List<String> businessRequirements,
        List<String> testScenarios,
        List<String> testCases,
        List<String> testData,
        String coverageStatus,
        String provisionalStatus
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
        double completionPercentage,
        int provisionalOrBlockedRules,
        int openProvisionalRules,
        int resolvedProvisionalRules
    ) {}

    private final Map<String, CanonicalRequirement> brById;
    private final Map<String, CanonicalScenario> tsById;
    private final Map<String, CanonicalTestCase> tcById;
    private final Map<String, CanonicalTestData> tdById;

    public Segment120TraceabilityMatrix(CanonicalArtifactPackage pkg) {
        this.brById = indexBy(pkg == null ? null : pkg.getBusinessRequirements(), CanonicalRequirement::getId);
        this.tsById = indexBy(pkg == null ? null : pkg.getTestScenarios(), CanonicalScenario::getId);
        this.tcById = indexBy(pkg == null ? null : pkg.getTestCases(), CanonicalTestCase::getId);
        this.tdById = indexBy(pkg == null ? null : pkg.getTestData(), CanonicalTestData::getId);
    }

    /** Convenience overload matching the Segment111/101 shape (no provisional reporting). */
    public MatrixReport generateMatrix(List<SourceAnchor> catalogRules) {
        List<Segment120RuleCatalogLoader.Rule> asRules = catalogRules.stream()
            .map(a -> new Segment120RuleCatalogLoader.Rule(a.getRule(), a.getRule(), a, null, null))
            .toList();
        return generateMatrixFromRules(asRules);
    }

    public MatrixReport generateMatrixFromRules(List<Segment120RuleCatalogLoader.Rule> catalogRules) {
        List<TraceLink> traceLinks = new ArrayList<>();
        for (Segment120RuleCatalogLoader.Rule rule : catalogRules) {
            traceLinks.add(traceRule(rule));
        }
        MatrixMetrics metrics = computeMetrics(traceLinks, catalogRules);
        return new MatrixReport("segment-120-traceability-matrix", traceLinks, metrics);
    }

    private TraceLink traceRule(Segment120RuleCatalogLoader.Rule rule) {
        SourceAnchor ruleAnchor = rule.sourceAnchor();
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
            rule.title() != null && !rule.title().isEmpty() ? rule.title() : describeRule(ruleAnchor),
            classifyRule(ruleAnchor),
            brIds, tsIds, tcIds, tdIds,
            status,
            provisionalStatus(rule)
        );
    }

    private static String provisionalStatus(Segment120RuleCatalogLoader.Rule rule) {
        if (rule.resolvedId() != null) {
            return "FORMERLY_PROVISIONAL_RESOLVED(" + rule.resolvedId() + ")";
        }
        if (rule.provisionalId() != null) {
            return "PROVISIONAL_OPEN(" + rule.provisionalId() + ")";
        }
        return "CONFIRMED";
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

    private MatrixMetrics computeMetrics(List<TraceLink> traceLinks, List<Segment120RuleCatalogLoader.Rule> catalogRules) {
        int totalRules = catalogRules.size();
        long fullyTraced = traceLinks.stream().filter(l -> "FULLY_TRACED".equals(l.coverageStatus)).count();
        long partiallyTraced = traceLinks.stream().filter(l -> "PARTIALLY_TRACED".equals(l.coverageStatus)).count();
        long missingBr = traceLinks.stream().filter(l -> l.businessRequirements.isEmpty()).count();
        long missingTc = traceLinks.stream().filter(l -> l.testCases.isEmpty() && !l.businessRequirements.isEmpty()).count();
        long missingTd = traceLinks.stream().filter(l -> l.testData.isEmpty() && !l.testCases.isEmpty()).count();
        double completion = totalRules == 0 ? 0 : (fullyTraced * 100.0) / totalRules;

        long provisionalOrBlocked = catalogRules.stream().filter(Segment120RuleCatalogLoader.Rule::isProvisionalOrBlocked).count();
        long open = catalogRules.stream().filter(rule -> rule.provisionalId() != null).count();
        long resolved = catalogRules.stream().filter(rule -> rule.resolvedId() != null).count();

        return new MatrixMetrics(totalRules, (int) fullyTraced, (int) partiallyTraced,
            (int) missingBr, (int) missingTc, (int) missingTd, completion,
            (int) provisionalOrBlocked, (int) open, (int) resolved);
    }

    private static String classifyRule(SourceAnchor a) {
        String rule = a.getRule() == null ? "" : a.getRule();
        if (rule.contains("response-only-applicability")) {
            return "applicability";
        }
        if (rule.contains("final-segment-ordering")) {
            return "structure";
        }
        if (rule.contains("blackhawk-delimiter")) {
            return "content";
        }
        if (rule.contains("segment-length") || rule.contains("field-separator-placement")) {
            return "serialization";
        }
        if (a.getElement() != null && !a.getElement().isEmpty() && !"-".equals(a.getElement())) {
            return "field";
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
