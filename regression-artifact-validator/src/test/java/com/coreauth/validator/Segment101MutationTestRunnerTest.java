package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment101MutationTestRunner;
import com.coreauth.validator.canonical.Segment101MutationTestRunner.MutationTestSuiteReport;
import com.coreauth.validator.canonical.Segment101MutationTestRunner.PackageMutationResult;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 6 (Mutation Framework Execution) tests for {@link Segment101MutationTestRunner}.
 */
class Segment101MutationTestRunnerTest {

    private static final Path SEG101_TEST_INPUT =
        Paths.get("test-input", "ai-solution", "test-data", "segment-101");

    @Nested
    class DiscoveryTests {

        @Test
        void discoversAllSyntheticFleetPackages() throws IOException {
            Segment101MutationTestRunner runner = new Segment101MutationTestRunner(SEG101_TEST_INPUT);
            List<Path> packages = runner.discoverPackages();
            assertThat(packages).isNotEmpty();
            assertThat(packages).allMatch(p -> p.getFileName().toString().endsWith(".synthetic.json"));
        }

        @Test
        void skipsRejectionFixtures() throws IOException {
            Segment101MutationTestRunner runner = new Segment101MutationTestRunner(SEG101_TEST_INPUT);
            List<Path> packages = runner.discoverPackages();
            assertThat(packages).noneMatch(p -> p.toString().contains("rejection"));
        }

        @Test
        void handlesMissingDirectory() throws IOException {
            Segment101MutationTestRunner runner = new Segment101MutationTestRunner(Paths.get("does-not-exist"));
            List<Path> packages = runner.discoverPackages();
            assertThat(packages).isEmpty();
        }
    }

    @Nested
    class PackageTests {

        @Test
        void testsFleetAuthOriginal() throws IOException {
            Segment101MutationTestRunner runner = new Segment101MutationTestRunner(SEG101_TEST_INPUT);
            PackageMutationResult result = runner.testPackage(
                SEG101_TEST_INPUT.resolve(Path.of("lifecycle", "fleet-auth-original.synthetic.json")));
            assertThat(result.packageName()).isEqualTo("fleet-auth-original.synthetic.json");
            assertThat(result.mutationCount()).isEqualTo(10);
            assertThat(result.metrics().totalMutations()).isEqualTo(10);
            assertThat(result.metrics().detectionRate()).isGreaterThanOrEqualTo(70.0);
        }

        @Test
        void testsFleetAuthCompletion() throws IOException {
            Segment101MutationTestRunner runner = new Segment101MutationTestRunner(SEG101_TEST_INPUT);
            PackageMutationResult result = runner.testPackage(
                SEG101_TEST_INPUT.resolve(Path.of("lifecycle", "fleet-auth-completion.synthetic.json")));
            assertThat(result.mutationCount()).isEqualTo(10);
            assertThat(result.metrics().detectionRate()).isGreaterThanOrEqualTo(70.0);
        }

        @Test
        void testsFleetAuthCancellation() throws IOException {
            Segment101MutationTestRunner runner = new Segment101MutationTestRunner(SEG101_TEST_INPUT);
            PackageMutationResult result = runner.testPackage(
                SEG101_TEST_INPUT.resolve(Path.of("lifecycle", "fleet-auth-cancellation.synthetic.json")));
            assertThat(result.mutationCount()).isEqualTo(10);
            assertThat(result.metrics().detectionRate()).isGreaterThanOrEqualTo(70.0);
        }

        @Test
        void perPackageMetricsRecordUndetectedMutationsExplicitly() throws IOException {
            Segment101MutationTestRunner runner = new Segment101MutationTestRunner(SEG101_TEST_INPUT);
            PackageMutationResult result = runner.testPackage(
                SEG101_TEST_INPUT.resolve(Path.of("lifecycle", "fleet-auth-original.synthetic.json")));
            assertThat(result.undetectedMutations()).allMatch(id -> id.startsWith("MUT-101-"));
        }
    }

    @Nested
    class SuiteAggregationTests {

        @Test
        void aggregatesAcrossAllPackages() throws IOException {
            MutationTestSuiteReport report = new Segment101MutationTestRunner(SEG101_TEST_INPUT).runAllPackages();
            assertThat(report.totalPackages()).isGreaterThanOrEqualTo(3);
            assertThat(report.totalMutationsRun()).isEqualTo(report.totalPackages() * 10);
        }

        @Test
        void overallDetectionRateExceedsEightyFivePercent() throws IOException {
            MutationTestSuiteReport report = new Segment101MutationTestRunner(SEG101_TEST_INPUT).runAllPackages();
            // Methodology target: >= 85%
            assertThat(report.overallDetectionRate())
                .as("Segment 101 overall mutation detection rate (Item 7 target >= 85%)")
                .isGreaterThanOrEqualTo(85.0);
        }

        @Test
        void gapsByRuleIsEmptyWhenAllDetected() throws IOException {
            MutationTestSuiteReport report = new Segment101MutationTestRunner(SEG101_TEST_INPUT).runAllPackages();
            if (report.overallDetectionRate() == 100.0) {
                assertThat(report.mutationGapsByRule()).isEmpty();
            }
        }

        @Test
        void summaryStringIsHumanReadable() throws IOException {
            MutationTestSuiteReport report = new Segment101MutationTestRunner(SEG101_TEST_INPUT).runAllPackages();
            String summary = report.summary();
            assertThat(summary).contains("Segment 101 Mutation Test Suite Report");
            assertThat(summary).contains("Total Packages Tested");
            assertThat(summary).contains("Overall Detection Rate");
        }

        @Test
        void allUndetectedMutationsListIsDeduplicated() throws IOException {
            MutationTestSuiteReport report = new Segment101MutationTestRunner(SEG101_TEST_INPUT).runAllPackages();
            long unique = report.allUndetectedMutations().stream().distinct().count();
            assertThat(report.allUndetectedMutations()).hasSize((int) unique);
        }
    }

    @Nested
    class MetricsTests {

        @Test
        void everyPackageResultHasTenMutations() throws IOException {
            MutationTestSuiteReport report = new Segment101MutationTestRunner(SEG101_TEST_INPUT).runAllPackages();
            assertThat(report.packageResults()).allMatch(pr -> pr.mutationCount() == 10);
        }

        @Test
        void detectionCountPlusMissedEqualsTotal() throws IOException {
            MutationTestSuiteReport report = new Segment101MutationTestRunner(SEG101_TEST_INPUT).runAllPackages();
            for (PackageMutationResult pr : report.packageResults()) {
                int total = pr.metrics().detectedMutations() + pr.metrics().missedMutations();
                assertThat(total).isEqualTo(pr.metrics().totalMutations());
            }
        }

        @Test
        void detectionRateIsWithinZeroToOneHundred() throws IOException {
            MutationTestSuiteReport report = new Segment101MutationTestRunner(SEG101_TEST_INPUT).runAllPackages();
            assertThat(report.overallDetectionRate()).isBetween(0.0, 100.0);
            for (PackageMutationResult pr : report.packageResults()) {
                assertThat(pr.metrics().detectionRate()).isBetween(0.0, 100.0);
            }
        }

        @Test
        void mutationGapsByRuleReferencesSeg101Rules() throws IOException {
            MutationTestSuiteReport report = new Segment101MutationTestRunner(SEG101_TEST_INPUT).runAllPackages();
            assertThat(report.mutationGapsByRule().keySet()).allMatch(k -> k.startsWith("SEG101-R-") || k.isBlank());
        }

        @Test
        void reportsPackageNameForEachResult() throws IOException {
            MutationTestSuiteReport report = new Segment101MutationTestRunner(SEG101_TEST_INPUT).runAllPackages();
            assertThat(report.packageResults()).allMatch(pr -> pr.packageName().endsWith(".synthetic.json"));
        }

        @Test
        void reportPackageResultsCountMatchesTotal() throws IOException {
            MutationTestSuiteReport report = new Segment101MutationTestRunner(SEG101_TEST_INPUT).runAllPackages();
            assertThat(report.packageResults()).hasSize(report.totalPackages());
        }

        @Test
        void detectionCountsAreNonNegative() throws IOException {
            MutationTestSuiteReport report = new Segment101MutationTestRunner(SEG101_TEST_INPUT).runAllPackages();
            for (PackageMutationResult pr : report.packageResults()) {
                assertThat(pr.metrics().detectedMutations()).isGreaterThanOrEqualTo(0);
                assertThat(pr.metrics().missedMutations()).isGreaterThanOrEqualTo(0);
            }
        }

        @Test
        void reportSummaryReflectsTotalPackages() throws IOException {
            MutationTestSuiteReport report = new Segment101MutationTestRunner(SEG101_TEST_INPUT).runAllPackages();
            assertThat(report.summary()).contains("Total Packages Tested: " + report.totalPackages());
        }
    }
}
