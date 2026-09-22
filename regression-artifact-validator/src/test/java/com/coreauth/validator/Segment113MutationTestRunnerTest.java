package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment113MutationTestRunner;
import com.coreauth.validator.canonical.Segment113MutationTestRunner.MutationTestSuiteReport;
import com.coreauth.validator.canonical.Segment113MutationTestRunner.PackageMutationResult;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 6 (Mutation Framework Execution) tests for {@link Segment113MutationTestRunner}.
 */
class Segment113MutationTestRunnerTest {

    private static final Path SEG113_TEST_INPUT =
        Paths.get("test-input", "ai-solution", "test-data", "segment-113");

    @Nested
    class DiscoveryTests {

        @Test
        void discoversAllSyntheticEcaTeleCheckPackages() throws IOException {
            Segment113MutationTestRunner runner = new Segment113MutationTestRunner(SEG113_TEST_INPUT);
            List<Path> packages = runner.discoverPackages();
            assertThat(packages).isNotEmpty();
            assertThat(packages).allMatch(p -> p.getFileName().toString().endsWith(".synthetic.json"));
        }

        @Test
        void skipsRejectionFixtures() throws IOException {
            Segment113MutationTestRunner runner = new Segment113MutationTestRunner(SEG113_TEST_INPUT);
            List<Path> packages = runner.discoverPackages();
            assertThat(packages).noneMatch(p -> p.toString().contains("rejection"));
        }

        @Test
        void handlesMissingDirectory() throws IOException {
            Segment113MutationTestRunner runner = new Segment113MutationTestRunner(Paths.get("does-not-exist"));
            List<Path> packages = runner.discoverPackages();
            assertThat(packages).isEmpty();
        }
    }

    @Nested
    class PackageTests {

        @Test
        void testsCheckPurchaseOriginal() throws IOException {
            Segment113MutationTestRunner runner = new Segment113MutationTestRunner(SEG113_TEST_INPUT);
            PackageMutationResult result = runner.testPackage(
                SEG113_TEST_INPUT.resolve(Path.of("lifecycle", "ecatelecheck-check-purchase-original.synthetic.json")));
            assertThat(result.packageName()).isEqualTo("ecatelecheck-check-purchase-original.synthetic.json");
            assertThat(result.mutationCount()).isEqualTo(10);
            assertThat(result.metrics().totalMutations()).isEqualTo(10);
            assertThat(result.metrics().detectionRate()).isGreaterThanOrEqualTo(85.0);
        }

        @Test
        void testsVoidWithTraceId() throws IOException {
            Segment113MutationTestRunner runner = new Segment113MutationTestRunner(SEG113_TEST_INPUT);
            PackageMutationResult result = runner.testPackage(
                SEG113_TEST_INPUT.resolve(Path.of("lifecycle", "ecatelecheck-void-with-trace-id.synthetic.json")));
            assertThat(result.mutationCount()).isEqualTo(10);
            assertThat(result.metrics().detectionRate()).isGreaterThanOrEqualTo(85.0);
        }

        @Test
        void testsDenialRecordDecline() throws IOException {
            Segment113MutationTestRunner runner = new Segment113MutationTestRunner(SEG113_TEST_INPUT);
            PackageMutationResult result = runner.testPackage(
                SEG113_TEST_INPUT.resolve(Path.of("lifecycle", "ecatelecheck-denial-record-decline.synthetic.json")));
            assertThat(result.mutationCount()).isEqualTo(10);
            assertThat(result.metrics().detectionRate()).isGreaterThanOrEqualTo(85.0);
        }

        @Test
        void perPackageMetricsRecordUndetectedMutationsExplicitly() throws IOException {
            Segment113MutationTestRunner runner = new Segment113MutationTestRunner(SEG113_TEST_INPUT);
            PackageMutationResult result = runner.testPackage(
                SEG113_TEST_INPUT.resolve(Path.of("lifecycle", "ecatelecheck-check-purchase-original.synthetic.json")));
            assertThat(result.undetectedMutations()).allMatch(id -> id.startsWith("MUT-113-"));
        }
    }

    @Nested
    class SuiteAggregationTests {

        @Test
        void aggregatesAcrossAllPackages() throws IOException {
            MutationTestSuiteReport report = new Segment113MutationTestRunner(SEG113_TEST_INPUT).runAllPackages();
            assertThat(report.totalPackages()).isGreaterThanOrEqualTo(3);
            assertThat(report.totalMutationsRun()).isEqualTo(report.totalPackages() * 10);
        }

        @Test
        void overallDetectionRateExceedsEightyFivePercent() throws IOException {
            MutationTestSuiteReport report = new Segment113MutationTestRunner(SEG113_TEST_INPUT).runAllPackages();
            // Methodology target: >= 85%
            assertThat(report.overallDetectionRate())
                .as("Segment 113 overall mutation detection rate (Item 7 target >= 85%)")
                .isGreaterThanOrEqualTo(85.0);
        }

        @Test
        void gapsByRuleIsEmptyWhenAllDetected() throws IOException {
            MutationTestSuiteReport report = new Segment113MutationTestRunner(SEG113_TEST_INPUT).runAllPackages();
            if (report.overallDetectionRate() == 100.0) {
                assertThat(report.mutationGapsByRule()).isEmpty();
            }
        }

        @Test
        void summaryStringIsHumanReadable() throws IOException {
            MutationTestSuiteReport report = new Segment113MutationTestRunner(SEG113_TEST_INPUT).runAllPackages();
            String summary = report.summary();
            assertThat(summary).contains("Segment 113 Mutation Test Suite Report");
            assertThat(summary).contains("Total Packages Tested");
            assertThat(summary).contains("Overall Detection Rate");
        }

        @Test
        void allUndetectedMutationsListIsDeduplicated() throws IOException {
            MutationTestSuiteReport report = new Segment113MutationTestRunner(SEG113_TEST_INPUT).runAllPackages();
            long unique = report.allUndetectedMutations().stream().distinct().count();
            assertThat(report.allUndetectedMutations()).hasSize((int) unique);
        }
    }

    @Nested
    class MetricsTests {

        @Test
        void everyPackageResultHasTenMutations() throws IOException {
            MutationTestSuiteReport report = new Segment113MutationTestRunner(SEG113_TEST_INPUT).runAllPackages();
            assertThat(report.packageResults()).allMatch(pr -> pr.mutationCount() == 10);
        }

        @Test
        void detectionCountPlusMissedEqualsTotal() throws IOException {
            MutationTestSuiteReport report = new Segment113MutationTestRunner(SEG113_TEST_INPUT).runAllPackages();
            for (PackageMutationResult pr : report.packageResults()) {
                int total = pr.metrics().detectedMutations() + pr.metrics().missedMutations();
                assertThat(total).isEqualTo(pr.metrics().totalMutations());
            }
        }
    }
}
