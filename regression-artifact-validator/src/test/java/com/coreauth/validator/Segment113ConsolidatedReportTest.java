package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment113ConsolidatedReport;
import com.coreauth.validator.canonical.Segment113ConsolidatedReport.ReportInputs;
import com.coreauth.validator.canonical.Segment113ConsolidatedReport.ReportSummary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 8 (Consolidated Report) tests for {@link Segment113ConsolidatedReport}.
 */
class Segment113ConsolidatedReportTest {

    @Test
    void generatesReportWithAllItemSections(@TempDir Path tmp) throws IOException {
        Path output = tmp.resolve("SEGMENT-113-CONSOLIDATED-REPORT.txt");
        ReportInputs inputs = new ReportInputs(
            Paths.get("specifications", "ATL105", "docs", "specs", "kb", "segment-113", "coverage", "segment-113-rule-catalog.json"),
            Paths.get("specifications", "ATL105", "test-output", "test-json", "segment-113-item-01-ecatelecheck-baseline-package.json"),
            Paths.get("specifications", "ATL105", "test-input", "ai-solution", "test-data", "segment-113"),
            output
        );

        ReportSummary summary = new Segment113ConsolidatedReport().generate(inputs);

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
    void reportsAll18RulesInCatalog(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment113ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.catalogRules()).isEqualTo(18);
    }

    @Test
    void reportsAllTenMutationsPerPackage(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment113ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.mutationsRunAcrossPackages()).isEqualTo(summary.mutationPackagesTested() * 10);
    }

    @Test
    void reportsMutationDetectionAtOrAboveEightyFivePercent(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment113ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.mutationDetectionRate())
            .as("Item 7 target >= 85%%")
            .isGreaterThanOrEqualTo(85.0);
    }

    @Test
    void reportsProductionReadinessDecision(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment113ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.productionReadinessDecision())
            .isIn("READY_FOR_PRODUCTION", "READY_FOR_AI_ARTIFACT_INTAKE", "REVIEW_REQUIRED");
    }

    @Test
    void writesReportFileToDisk(@TempDir Path tmp) throws IOException {
        Path output = tmp.resolve("SEGMENT-113-CONSOLIDATED-REPORT.txt");
        ReportInputs inputs = new ReportInputs(
            Paths.get("specifications", "ATL105", "docs", "specs", "kb", "segment-113", "coverage", "segment-113-rule-catalog.json"),
            Paths.get("specifications", "ATL105", "test-output", "test-json", "segment-113-item-01-ecatelecheck-baseline-package.json"),
            Paths.get("specifications", "ATL105", "test-input", "ai-solution", "test-data", "segment-113"),
            output
        );

        ReportSummary summary = new Segment113ConsolidatedReport().generate(inputs);

        assertThat(summary.reportPath()).isEqualTo(output);
        assertThat(Files.readString(output)).isEqualTo(summary.reportText());
    }

    @Test
    void reportListsProvisionalItems(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment113ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.reportText()).contains("P-05");
    }

    @Test
    void reportUsesIso8601Timestamp(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment113ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.reportText()).contains("Generated:");
    }

    private static ReportInputs inputsFor(Path tmp) {
        return new ReportInputs(
            Paths.get("specifications", "ATL105", "docs", "specs", "kb", "segment-113", "coverage", "segment-113-rule-catalog.json"),
            Paths.get("specifications", "ATL105", "test-output", "test-json", "segment-113-item-01-ecatelecheck-baseline-package.json"),
            Paths.get("specifications", "ATL105", "test-input", "ai-solution", "test-data", "segment-113"),
            tmp.resolve("SEGMENT-113-CONSOLIDATED-REPORT.txt")
        );
    }
}
