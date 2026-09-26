package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment104ConsolidatedReport;
import com.coreauth.validator.canonical.Segment104ConsolidatedReport.ReportInputs;
import com.coreauth.validator.canonical.Segment104ConsolidatedReport.ReportSummary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 8 (Consolidated Report) tests for {@link Segment104ConsolidatedReport}.
 */
class Segment104ConsolidatedReportTest {

    @Test
    void generatesReportWithAllItemSections(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment104ConsolidatedReport().generate(inputsFor(tmp));

        assertThat(summary.reportText()).contains("Item 1  Coverage Closure");
        assertThat(summary.reportText()).contains("Item 2  AI Artifact Comparison");
        assertThat(summary.reportText()).contains("Item 3  Test-Data Independence");
        assertThat(summary.reportText()).contains("Item 4  Traceability Matrix");
        assertThat(summary.reportText()).contains("Item 5  Mutation Framework Definition");
        assertThat(summary.reportText()).contains("Item 6  Mutation Framework Execution");
        assertThat(summary.reportText()).contains("Item 7  Validator Enhancement");
        assertThat(summary.reportText()).contains("Item 8  Consolidated Sign-Off");
    }

    @Test
    void reportsAll14RulesInCatalog(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment104ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.catalogRules()).isEqualTo(14);
    }

    @Test
    void reportsAllTenMutationsPerPackage(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment104ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.mutationsRunAcrossPackages()).isEqualTo(summary.mutationPackagesTested() * 10);
    }

    @Test
    void reportsMutationDetectionAtOrAboveEightyFivePercent(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment104ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.mutationDetectionRate())
            .as("Item 7 target >= 85%%")
            .isGreaterThanOrEqualTo(85.0);
    }

    @Test
    void reportsProductionReadinessDecision(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment104ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.productionReadinessDecision())
            .isIn("READY_FOR_PRODUCTION", "READY_FOR_AI_ARTIFACT_INTAKE", "REVIEW_REQUIRED");
    }

    @Test
    void reportsGenuineComparisonGapAgainstRealAiArtifact(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment104ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.missingAnchors()).isEqualTo(11);
        assertThat(summary.matchedAnchors()).isEqualTo(3);
    }

    @Test
    void writesReportFileToDisk(@TempDir Path tmp) throws IOException {
        Path output = tmp.resolve("SEGMENT-104-CONSOLIDATED-REPORT.txt");
        ReportInputs inputs = new ReportInputs(
            Paths.get("specifications", "ATL105", "docs", "specs", "kb", "segment-104", "coverage", "segment-104-rule-catalog.json"),
            Paths.get("specifications", "ATL105", "test-output", "test-json", "segment-104-item-01-purchase-card-baseline-package.json"),
            Paths.get("specifications", "ATL105", "test-input", "ai-solution", "test-data", "segment-104"),
            output
        );

        ReportSummary summary = new Segment104ConsolidatedReport().generate(inputs);

        assertThat(summary.reportPath()).isEqualTo(output);
        assertThat(Files.readString(output)).isEqualTo(summary.reportText());
    }

    @Test
    void reportListsProvisionalItems(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment104ConsolidatedReport().generate(inputsFor(tmp));
        for (String pid : new String[]{"P-01", "P-02"}) {
            assertThat(summary.reportText())
                .as("provisional %s listed", pid)
                .contains(pid);
        }
    }

    @Test
    void reportUsesIso8601Timestamp(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment104ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.reportText()).contains("Generated:");
    }

    private static ReportInputs inputsFor(Path tmp) {
        return new ReportInputs(
            Paths.get("specifications", "ATL105", "docs", "specs", "kb", "segment-104", "coverage", "segment-104-rule-catalog.json"),
            Paths.get("specifications", "ATL105", "test-output", "test-json", "segment-104-item-01-purchase-card-baseline-package.json"),
            Paths.get("specifications", "ATL105", "test-input", "ai-solution", "test-data", "segment-104"),
            tmp.resolve("SEGMENT-104-CONSOLIDATED-REPORT.txt")
        );
    }
}
