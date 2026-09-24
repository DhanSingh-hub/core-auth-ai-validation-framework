package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Item 8 (Consolidated Report) for Segment 120.
 *
 * <p>Generates {@code SEGMENT-120-CONSOLIDATED-REPORT.txt} at the module root, aggregating
 * Item 1-7 outcomes into a production-readiness sign-off. Mirrors
 * {@link Segment111ConsolidatedReport} / {@link Segment101ConsolidatedReport}.
 */
public final class Segment120ConsolidatedReport {

    private static final Path DEFAULT_RULE_CATALOG =
        Paths.get("docs", "specs", "kb", "segment-120", "coverage", "segment-120-rule-catalog.json");
    private static final Path DEFAULT_AI_PACKAGE =
        Paths.get("test-output", "test-json", "segment-120-item-01-baseline-package.json");
    private static final Path DEFAULT_TEST_INPUT =
        Paths.get("test-input", "ai-solution", "test-data", "segment-120");
    private static final Path DEFAULT_OUTPUT =
        Paths.get("SEGMENT-120-CONSOLIDATED-REPORT.txt");

    public record ReportInputs(
        Path ruleCatalog,
        Path aiPackage,
        Path testInput,
        Path outputFile
    ) {
        public static ReportInputs defaults() {
            return new ReportInputs(DEFAULT_RULE_CATALOG, DEFAULT_AI_PACKAGE,
                DEFAULT_TEST_INPUT, DEFAULT_OUTPUT);
        }
    }

    public record ReportSummary(
        int catalogRules,
        int aiPackageAnchors,
        int matchedAnchors,
        int missingAnchors,
        int extraAnchors,
        double precision,
        double recall,
        String comparisonDecision,
        int totalRulesInMatrix,
        int fullyTraced,
        int partiallyTraced,
        double traceCompletionPercent,
        int mutationPackagesTested,
        int mutationsRunAcrossPackages,
        double mutationDetectionRate,
        List<String> undetectedMutationIds,
        String productionReadinessDecision,
        String reportText,
        Path reportPath
    ) {}

    public ReportSummary generate() throws IOException {
        return generate(ReportInputs.defaults());
    }

    public ReportSummary generate(ReportInputs inputs) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Segment120RuleCatalogLoader.Catalog catalog =
            new Segment120RuleCatalogLoader().load(inputs.ruleCatalog);
        CanonicalArtifactPackage pkg = mapper.readValue(
            inputs.aiPackage.toFile(), CanonicalArtifactPackage.class);

        Segment120ArtifactComparison.ComparisonReport comparison =
            new Segment120ArtifactComparison().compare(pkg, catalog.anchors());
        Segment120TraceabilityMatrix.MatrixReport trace =
            new Segment120TraceabilityMatrix(pkg).generateMatrixFromRules(catalog.rules());
        Segment120MutationTestRunner.MutationTestSuiteReport mutation =
            new Segment120MutationTestRunner(inputs.testInput).runAllPackages();

        String readiness = decideReadiness(comparison, trace, mutation);
        String reportText = render(catalog, comparison, trace, mutation, readiness);
        Files.writeString(inputs.outputFile, reportText);

        return new ReportSummary(
            catalog.rules().size(),
            comparison.aiAnchorCount(),
            comparison.matched().size(),
            comparison.missing().size(),
            comparison.extra().size(),
            comparison.precision(),
            comparison.recall(),
            comparison.overallDecision(),
            trace.metrics().totalRules(),
            trace.metrics().fullyTraced(),
            trace.metrics().partiallyTraced(),
            trace.metrics().completionPercentage(),
            mutation.totalPackages(),
            mutation.totalMutationsRun(),
            mutation.overallDetectionRate(),
            mutation.allUndetectedMutations(),
            readiness,
            reportText,
            inputs.outputFile
        );
    }

    private static String decideReadiness(
        Segment120ArtifactComparison.ComparisonReport comparison,
        Segment120TraceabilityMatrix.MatrixReport trace,
        Segment120MutationTestRunner.MutationTestSuiteReport mutation
    ) {
        boolean itemsPass = mutation.overallDetectionRate() >= 85.0;
        boolean noComparisonBlockers = !"REJECTED_MISSING_COVERAGE".equals(comparison.overallDecision())
            || comparison.aiAnchorCount() > 0;
        boolean provisionalOnly = trace.metrics().completionPercentage() < 100.0;
        if (itemsPass && noComparisonBlockers && !provisionalOnly) {
            return "READY_FOR_PRODUCTION";
        }
        if (itemsPass && noComparisonBlockers) {
            return "READY_FOR_AI_ARTIFACT_INTAKE";
        }
        return "REVIEW_REQUIRED";
    }

    private static String render(
        Segment120RuleCatalogLoader.Catalog catalog,
        Segment120ArtifactComparison.ComparisonReport comparison,
        Segment120TraceabilityMatrix.MatrixReport trace,
        Segment120MutationTestRunner.MutationTestSuiteReport mutation,
        String readiness
    ) {
        StringBuilder sb = new StringBuilder();
        String timestamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        sb.append("Segment 120 (Print Data 2 Segment) - Consolidated Validation Report\n");
        sb.append("===========================================================================\n");
        sb.append("Specification:    ATL105 2026-3, Section 12.18\n");
        sb.append("Generated:        ").append(timestamp).append('\n');
        sb.append("Methodology:      SEGMENT-100-TRAINING-METHODOLOGY.md (8-Item Framework)\n");
        sb.append("Note:             Segment 120 is a RESPONSE-side companion segment (Financial Transaction\n");
        sb.append("                  Response / EMV Financial Transaction Response, Data Section 3), unlike\n");
        sb.append("                  the request-side Segment 100/101/111 reports.\n\n");

        sb.append("Item 1  Coverage Closure\n");
        sb.append("------------------------\n");
        sb.append("  Rule catalog:                 ").append(catalog.catalogId()).append('\n');
        sb.append("  Rules extracted:              ").append(catalog.rules().size()).append('\n');
        sb.append("  Baseline validator:           Segment120PayloadValidator\n");
        sb.append("  Baseline tests:               Segment120PayloadValidatorTest\n\n");

        sb.append("Item 2  AI Artifact Comparison\n");
        sb.append("------------------------------\n");
        sb.append(String.format("  AI anchors:                   %d%n", comparison.aiAnchorCount()));
        sb.append(String.format("  Matched vs catalog:           %d%n", comparison.matched().size()));
        sb.append(String.format("  Missing:                      %d%n", comparison.missing().size()));
        sb.append(String.format("  Extra:                        %d%n", comparison.extra().size()));
        sb.append(String.format("  Precision:                    %.2f%%%n", comparison.precision() * 100));
        sb.append(String.format("  Recall:                       %.2f%%%n", comparison.recall() * 100));
        sb.append("  Decision:                     ").append(comparison.overallDecision()).append("\n");
        sb.append("  Note: no real AI-generated Segment 120 package has been supplied yet; the placeholder\n");
        sb.append("        package intentionally has zero anchors (see\n");
        sb.append("        docs/specs/kb/segment-120/coverage/README.md). This is an accurate\n");
        sb.append("        finding, not a defect.\n\n");

        sb.append("Item 3  Test-Data Independence\n");
        sb.append("------------------------------\n");
        sb.append("  Validator:                    Segment120IndependenceValidator\n");
        sb.append("  Coverage:                     Independence checks over canonical BR/TS/TC/TD\n\n");

        sb.append("Item 4  Traceability Matrix\n");
        sb.append("---------------------------\n");
        sb.append(String.format("  Total rules:                  %d%n", trace.metrics().totalRules()));
        sb.append(String.format("  Fully traced:                 %d%n", trace.metrics().fullyTraced()));
        sb.append(String.format("  Partially traced:             %d%n", trace.metrics().partiallyTraced()));
        sb.append(String.format("  Missing BRs:                  %d%n", trace.metrics().missingBusinessRequirements()));
        sb.append(String.format("  Missing test cases:           %d%n", trace.metrics().missingTestCases()));
        sb.append(String.format("  Missing test data:            %d%n", trace.metrics().missingTestData()));
        sb.append(String.format("  Completion percentage:        %.2f%%%n", trace.metrics().completionPercentage()));
        sb.append(String.format("  Provisional/blocked rules:    %d of %d (SEG120-R-002, R-004, R-007, R-008)%n",
            trace.metrics().provisionalOrBlockedRules(), trace.metrics().totalRules()));
        sb.append(String.format("    - still OPEN:                %d%n", trace.metrics().openProvisionalRules()));
        sb.append(String.format("    - RESOLVED:                  %d%n%n", trace.metrics().resolvedProvisionalRules()));

        sb.append("Item 5  Mutation Framework Definition\n");
        sb.append("-------------------------------------\n");
        sb.append("  Mutation tester:              Segment120MutationTester\n");
        sb.append("  Standard mutations defined:   ").append(Segment120MutationTester.allMutationIds().size()).append('\n');
        sb.append("  Rule classes covered:         SEG120-R-001, R-002, R-003, R-004, R-005,\n");
        sb.append("                                R-006, R-007, R-008 (all 8 catalog rules) via\n");
        sb.append("                                MUT-001..MUT-010\n");
        sb.append("  Note: MUT-009 reorders Segment 120 away from the final Data Section 3 position\n");
        sb.append("        (SEG120-R-007), included now that P-01 is resolved (was previously omitted\n");
        sb.append("        from the standard suite pending that resolution).\n\n");

        sb.append("Item 6  Mutation Framework Execution\n");
        sb.append("------------------------------------\n");
        sb.append(String.format("  Packages tested:              %d%n", mutation.totalPackages()));
        sb.append(String.format("  Mutations executed:           %d%n", mutation.totalMutationsRun()));
        sb.append(String.format("  Overall detection rate:       %.2f%%%n", mutation.overallDetectionRate()));
        sb.append(String.format("  Unique undetected mutations:  %d%n%n", mutation.allUndetectedMutations().size()));

        sb.append("Item 7  Validator Enhancement (target >= 85%)\n");
        sb.append("---------------------------------------------\n");
        if (mutation.overallDetectionRate() >= 85.0) {
            sb.append("  Status:                       PASS; no enhancement required.\n\n");
        } else {
            sb.append("  Status:                       BELOW TARGET; validator enhancement required.\n");
            sb.append("  Undetected mutations:\n");
            for (String id : mutation.allUndetectedMutations()) {
                sb.append("    - ").append(id).append('\n');
            }
            sb.append('\n');
        }

        sb.append("Item 8  Consolidated Sign-Off\n");
        sb.append("-----------------------------\n");
        sb.append("  Production-readiness decision: ").append(readiness).append('\n');
        sb.append("  PROVISIONAL items (from segment-120-rule-catalog.json provisionalItems):\n");
        sb.append("    P-01            RESOLVED 2026-09-23 (impacts SEG120-R-007) -- literal spec text\n");
        sb.append("                    (BR-263-2) 'always appears at the end' is authoritative;\n");
        sb.append("                    atl105_complete_templates.json ordering is a numeric-sort extraction\n");
        sb.append("                    artifact, not true wire order -- see\n");
        sb.append("                    docs/specs/kb/segment-120/coverage/segment-120-coverage-sme-tba-note.md.\n");
        sb.append("                    Hard-enforced (SEG120-R-007) and covered by mutation MUT-009.\n");
        sb.append("    P-02-WIDTH      RESOLVED 2026-09-23 (impacts SEG120-R-002) -- Segment Length is\n");
        sb.append("                    always exactly 4 digits, zero-padded (never variable 3-4 width).\n");
        sb.append("    P-02-RESIDUAL   OPEN (impacts SEG120-R-004) -- 1-character arithmetic gap between\n");
        sb.append("                    the 999-char Print Data field cap and the 1,009-char total-segment\n");
        sb.append("                    cap; both caps are enforced independently pending SME resolution.\n");
        sb.append("    P-03            OPEN (impacts SEG120-R-008) -- whether the Blackhawk '\\' line\n");
        sb.append("                    delimiter convention needs envelope-level validation, or remains\n");
        sb.append("                    out of Item 1 scope (current behavior: out of scope, opaque text).\n");
        sb.append("    P-04            OPEN (impacts SEG120-R-006) -- Segment 120 cardinality per message\n");
        sb.append("                    is not yet confirmed (at most one, by analogy to Segment 101, vs\n");
        sb.append("                    unconstrained; current behavior: unconstrained).\n\n");

        sb.append("  Framework is ").append(readiness).append(". Full certification requires:\n");
        sb.append("  1. SME resolution of P-02-RESIDUAL, P-03, and P-04 (P-01 and P-02-WIDTH already\n");
        sb.append("     resolved 2026-09-23; see docs/specs/kb/segment-120/coverage/README.md).\n");
        sb.append("  2. Real AI-generated Segment 120 packages covering all 8 catalog rules\n");
        sb.append("     (coverage depends on the supplied AI package and remains independently reported).\n");
        sb.append("  3. Real Segment 120 test data replacing the synthetic fixtures.\n");
        sb.append("  4. External ATL105 converter run against the real data.\n");
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        ReportSummary summary = new Segment120ConsolidatedReport().generate();
        System.out.println(summary.reportText());
        System.out.println("Written: " + summary.reportPath().toAbsolutePath());
    }
}
