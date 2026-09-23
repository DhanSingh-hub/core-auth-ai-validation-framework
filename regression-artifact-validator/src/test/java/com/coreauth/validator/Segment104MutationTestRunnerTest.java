package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment104MutationTestRunner;
import com.coreauth.validator.canonical.Segment104MutationTestRunner.MutationTestSuiteReport;
import com.coreauth.validator.canonical.Segment104MutationTestRunner.PackageMutationResult;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 6 (Mutation Framework Execution) tests for {@link Segment104MutationTestRunner}.
 */
class Segment104MutationTestRunnerTest {

    private static final Path SEG104_TEST_INPUT =
        Paths.get("test-input", "ai-solution", "test-data", "segment-104");

    @Nested
    class DiscoveryTests {

        @Test
        void discoversAllSyntheticPurchaseCardPackages() throws IOException {
            Segment104MutationTestRunner runner = new Segment104MutationTestRunner(SEG104_TEST_INPUT);
            List<Path> packages = runner.discoverPackages();
            assertThat(packages).isNotEmpty();
            assertThat(packages).allMatch(p -> p.getFileName().toString().endsWith(".synthetic.json"));
        }

        @Test
        void skipsRejectionFixtures() throws IOException {
            Segment104MutationTestRunner runner = new Segment104MutationTestRunner(SEG104_TEST_INPUT);
            List<Path> packages = runner.discoverPackages();
            assertThat(packages).noneMatch(p -> p.toString().contains("rejection"));
        }

        @Test
        void handlesMissingDirectory() throws IOException {
            Segment104MutationTestRunner runner = new Segment104MutationTestRunner(Paths.get("does-not-exist"));
            List<Path> packages = runner.discoverPackages();
            assertThat(packages).isEmpty();
        }
    }

    @Nested
    class PackageTests {

        @Test
        void testsPurchaseCardAuthOriginal() throws IOException {
            Segment104MutationTestRunner runner = new Segment104MutationTestRunner(SEG104_TEST_INPUT);
            PackageMutationResult result = runner.testPackage(
                SEG104_TEST_INPUT.resolve(Path.of("lifecycle", "purchase-card-auth-original.synthetic.json")));
            assertThat(result.packageName()).isEqualTo("purchase-card-auth-original.synthetic.json");
            assertThat(result.mutationCount()).isEqualTo(10);
            assertThat(result.metrics().totalMutations()).isEqualTo(10);
            assertThat(result.metrics().detectionRate()).isGreaterThanOrEqualTo(70.0);
        }

        @Test
        void testsPurchaseCardMinimal() throws IOException {
            Segment104MutationTestRunner runner = new Segment104MutationTestRunner(SEG104_TEST_INPUT);
            PackageMutationResult result = runner.testPackage(
                SEG104_TEST_INPUT.resolve(Path.of("lifecycle", "purchase-card-minimal.synthetic.json")));
            assertThat(result.mutationCount()).isEqualTo(10);
            assertThat(result.metrics().detectionRate()).isGreaterThanOrEqualTo(70.0);
        }

        @Test
        void perPackageMetricsRecordUndetectedMutationsExplicitly() throws IOException {
            Segment104MutationTestRunner runner = new Segment104MutationTestRunner(SEG104_TEST_INPUT);
            PackageMutationResult result = runner.testPackage(
                SEG104_TEST_INPUT.resolve(Path.of("lifecycle", "purchase-card-auth-original.synthetic.json")));
            assertThat(result.undetectedMutations()).allMatch(id -> id.startsWith("MUT-104-"));
        }
    }

    @Nested
    class AggregationTests {

        @Test
        void runsAllPackagesAndAggregatesMetrics() throws IOException {
            Segment104MutationTestRunner runner = new Segment104MutationTestRunner(SEG104_TEST_INPUT);
            MutationTestSuiteReport report = runner.runAllPackages();

            assertThat(report.totalPackages()).isEqualTo(2);
            assertThat(report.totalMutationsRun()).isEqualTo(20);
            assertThat(report.overallDetectionRate()).isGreaterThanOrEqualTo(85.0);
        }

        @Test
        void summaryTextIncludesKeyMetrics() throws IOException {
            Segment104MutationTestRunner runner = new Segment104MutationTestRunner(SEG104_TEST_INPUT);
            MutationTestSuiteReport report = runner.runAllPackages();

            assertThat(report.summary())
                .contains("Segment 104 Mutation Test Suite Report")
                .contains("Total Packages Tested:")
                .contains("Overall Detection Rate:");
        }

        @Test
        void aggregatesUndetectedMutationsAcrossPackagesWithoutDuplicates() throws IOException {
            Segment104MutationTestRunner runner = new Segment104MutationTestRunner(SEG104_TEST_INPUT);
            MutationTestSuiteReport report = runner.runAllPackages();

            assertThat(report.allUndetectedMutations())
                .doesNotHaveDuplicates();
        }

        @Test
        void handlesEmptyDirectoryGracefully() throws IOException {
            Segment104MutationTestRunner runner = new Segment104MutationTestRunner(Paths.get("does-not-exist"));
            MutationTestSuiteReport report = runner.runAllPackages();

            assertThat(report.totalPackages()).isZero();
            assertThat(report.totalMutationsRun()).isZero();
            assertThat(report.overallDetectionRate()).isZero();
        }

        @Test
        void mutationGapsByRuleIsEmptyWhenFullDetection() throws IOException {
            Segment104MutationTestRunner runner = new Segment104MutationTestRunner(SEG104_TEST_INPUT);
            MutationTestSuiteReport report = runner.runAllPackages();

            if (report.overallDetectionRate() == 100.0) {
                assertThat(report.mutationGapsByRule()).isEmpty();
            }
        }
    }
}
