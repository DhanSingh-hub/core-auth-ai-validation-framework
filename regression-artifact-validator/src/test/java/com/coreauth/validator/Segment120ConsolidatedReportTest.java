package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment120ConsolidatedReport;
import com.coreauth.validator.canonical.Segment120ConsolidatedReport.ReportInputs;
import com.coreauth.validator.canonical.Segment120ConsolidatedReport.ReportSummary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 8 (Consolidated Report) tests for {@link Segment120ConsolidatedReport}.
 */
class Segment120ConsolidatedReportTest {

    private static final Path DEFAULT_OUTPUT = Paths.get("SEGMENT-120-CONSOLIDATED-REPORT.txt");

    @AfterEach
    void cleanupDefaultOutput() throws IOException {
        Files.deleteIfExists(DEFAULT_OUTPUT);
    }

    @Test
    void generatesReportWithAllItemSections(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment120ConsolidatedReport().generate(inputsFor(tmp));

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
    void reportsAllEightRulesInCatalog(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment120ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.catalogRules()).isEqualTo(8);
    }

    @Test
    void reportsAllTenMutationsPerPackage(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment120ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.mutationsRunAcrossPackages()).isEqualTo(summary.mutationPackagesTested() * 10);
    }

    @Test
    void reportsMutationDetectionAtOrAboveEightyFivePercent(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment120ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.mutationDetectionRate())
            .as("Item 7 target >= 85%%")
            .isGreaterThanOrEqualTo(85.0);
    }

    @Test
    void reportsProductionReadinessDecision(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment120ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.productionReadinessDecision())
            .isIn("READY_FOR_PRODUCTION", "READY_FOR_AI_ARTIFACT_INTAKE", "REVIEW_REQUIRED");
    }

    @Test
    void reportsReviewRequiredDecisionForPlaceholderPackage(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment120ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.productionReadinessDecision()).isEqualTo("REVIEW_REQUIRED");
    }

    @Test
    void reportsGenuineComparisonGapAgainstPlaceholderAiArtifact(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment120ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.missingAnchors()).isEqualTo(8);
        assertThat(summary.matchedAnchors()).isEqualTo(0);
    }

    @Test
    void writesReportFileToDisk(@TempDir Path tmp) throws IOException {
        Path output = tmp.resolve("SEGMENT-120-CONSOLIDATED-REPORT.txt");
        ReportInputs inputs = new ReportInputs(
            Paths.get("docs", "specs", "kb", "segment-120", "coverage", "segment-120-rule-catalog.json"),
            packageFile(tmp),
            Paths.get("test-input", "ai-solution", "test-data", "segment-120"),
            output
        );

        ReportSummary summary = new Segment120ConsolidatedReport().generate(inputs);

        assertThat(summary.reportPath()).isEqualTo(output);
        assertThat(Files.readString(output)).isEqualTo(summary.reportText());
    }

    @Test
    void reportListsAllFiveProvisionalItems(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment120ConsolidatedReport().generate(inputsFor(tmp));
        for (String pid : new String[]{"P-01", "P-02-WIDTH", "P-02-RESIDUAL", "P-03", "P-04"}) {
            assertThat(summary.reportText())
                .as("provisional %s listed", pid)
                .contains(pid);
        }
    }

    @Test
    void reportUsesIso8601Timestamp(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment120ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.reportText()).contains("Generated:");
    }

    @Test
    void reportNotesSegment120IsResponseSide(@TempDir Path tmp) throws IOException {
        ReportSummary summary = new Segment120ConsolidatedReport().generate(inputsFor(tmp));
        assertThat(summary.reportText()).contains("RESPONSE-side companion segment");
    }

    private static Path packageFile(Path tmp) throws IOException {
        Path p = tmp.resolve("package.json");
        Files.writeString(p, "{\"businessRequirements\":[],\"testScenarios\":[],\"testCases\":[],\"testData\":[]}");
        return p;
    }

    private static ReportInputs inputsFor(Path tmp) throws IOException {
        return new ReportInputs(
            Paths.get("docs", "specs", "kb", "segment-120", "coverage", "segment-120-rule-catalog.json"),
            packageFile(tmp),
            Paths.get("test-input", "ai-solution", "test-data", "segment-120"),
            tmp.resolve("SEGMENT-120-CONSOLIDATED-REPORT.txt")
        );
    }
}
