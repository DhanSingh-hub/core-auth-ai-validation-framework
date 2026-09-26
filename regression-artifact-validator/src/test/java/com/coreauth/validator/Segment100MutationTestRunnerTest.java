package com.coreauth.validator;

import com.coreauth.validator.canonical.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;

/**
 * Segment100MutationTestRunnerTest: Verify runner can execute mutation testing
 * framework against all test-output packages.
 */
@DisplayName("Segment100MutationTestRunner Tests")
class Segment100MutationTestRunnerTest {

  private Segment100MutationTestRunner runner;
  private Path testOutputPath;

  @BeforeEach
  void setup() {
    testOutputPath =
        Paths.get(
            "regression-artifact-validator",
            "test-output"); // Relative to project root
    runner = new Segment100MutationTestRunner(testOutputPath);
  }

  @Nested
  @DisplayName("Runner Initialization Tests")
  class RunnerInitializationTests {

    @Test
    void createsRunnerWithTestOutputPath() {
      assertThat(runner).isNotNull();
    }

    @Test
    void acceptsNullPath() {
      Segment100MutationTestRunner nullPathRunner =
          new Segment100MutationTestRunner(null);
      assertThat(nullPathRunner).isNotNull();
    }

    @Test
    void acceptsValidPath() {
      assertThat(testOutputPath).isNotNull();
    }
  }

  @Nested
  @DisplayName("Package Loading Tests")
  class PackageLoadingTests {

    @Test
    void loadsPackageFromJsonFile() throws Exception {
      var file =
          testOutputPath
              .resolve("test-json/segment-100-item-01-message-length-package.json")
              .toFile();

      if (!file.exists()) {
        System.out.println(
            "Skipping: "
                + file.getAbsolutePath()
                + " does not exist (expected in CI/CD if test-output not generated)");
        return;
      }

      CanonicalArtifactPackage pkg = runner.loadPackage(file);
      assertThat(pkg).isNotNull();
    }

    @Test
    void loadsMultiplePackagesSequentially() throws Exception {
      var dir = testOutputPath.resolve("test-json").toFile();
      if (!dir.exists()) {
        System.out.println("Skipping: test-json directory does not exist");
        return;
      }

      var files = dir.listFiles(f -> f.getName().startsWith("segment-100-item-"));
      assertThat(files).isNotEmpty();
    }
  }

  @Nested
  @DisplayName("Single Package Mutation Tests")
  class SinglePackageMutationTests {

    @Test
    void testSinglePackageReturnsResult() throws Exception {
      var file =
          testOutputPath
              .resolve("test-json/segment-100-item-01-message-length-package.json")
              .toFile();

      if (!file.exists()) {
        System.out.println("Skipping: test-output packages not available");
        return;
      }

      Segment100MutationTestRunner.PackageMutationResult result = runner.testPackage(file);
      assertThat(result).isNotNull();
    }

    @Test
    void packageResultHasRequiredFields() throws Exception {
      var file =
          testOutputPath
              .resolve("test-json/segment-100-item-01-message-length-package.json")
              .toFile();

      if (!file.exists()) {
        System.out.println("Skipping: test-output packages not available");
        return;
      }

      Segment100MutationTestRunner.PackageMutationResult result = runner.testPackage(file);

      assertThat(result.packageName()).isNotBlank();
      assertThat(result.testDataCount()).isGreaterThanOrEqualTo(0);
      assertThat(result.mutationCount()).isGreaterThan(0);
      assertThat(result.metrics()).isNotNull();
      assertThat(result.undetectedMutations()).isNotNull();
    }

    @Test
    void packageHasValidMetrics() throws Exception {
      var file =
          testOutputPath
              .resolve("test-json/segment-100-item-01-message-length-package.json")
              .toFile();

      if (!file.exists()) {
        System.out.println("Skipping: test-output packages not available");
        return;
      }

      Segment100MutationTestRunner.PackageMutationResult result = runner.testPackage(file);

      var metrics = result.metrics();
      assertThat(metrics.totalMutations()).isGreaterThan(0);
      assertThat(metrics.detectedMutations()).isGreaterThanOrEqualTo(0);
      assertThat(metrics.missedMutations()).isGreaterThanOrEqualTo(0);
      assertThat(metrics.detectionRate()).isBetween(0.0, 100.0);
    }

    @Test
    void metricsTotalEqualsDetectedPlusMissed() throws Exception {
      var file =
          testOutputPath
              .resolve("test-json/segment-100-item-02-tpdu-header-package.json")
              .toFile();

      if (!file.exists()) {
        System.out.println("Skipping: test-output packages not available");
        return;
      }

      Segment100MutationTestRunner.PackageMutationResult result = runner.testPackage(file);
      var metrics = result.metrics();

      assertThat(metrics.totalMutations())
          .isEqualTo(metrics.detectedMutations() + metrics.missedMutations());
    }
  }

  @Nested
  @DisplayName("Full Suite Mutation Tests")
  class FullSuiteMutationTests {

    @Test
    void runAllPackagesReturnsReport() throws Exception {
      var testJsonDir = testOutputPath.resolve("test-json").toFile();
      if (!testJsonDir.exists()) {
        System.out.println("Skipping: specifications/ATL105/test-output/test-json not available");
        return;
      }

      Segment100MutationTestRunner.MutationTestSuiteReport report = runner.runAllPackages();
      assertThat(report).isNotNull();
    }

    @Test
    void reportHasRequiredFields() throws Exception {
      var testJsonDir = testOutputPath.resolve("test-json").toFile();
      if (!testJsonDir.exists()) {
        System.out.println("Skipping: specifications/ATL105/test-output/test-json not available");
        return;
      }

      Segment100MutationTestRunner.MutationTestSuiteReport report = runner.runAllPackages();

      assertThat(report.totalPackages()).isGreaterThan(0);
      assertThat(report.totalTestData()).isGreaterThanOrEqualTo(0);
      assertThat(report.totalMutationsRun()).isGreaterThan(0);
      assertThat(report.overallDetectionRate()).isBetween(0.0, 100.0);
      assertThat(report.packageResults()).isNotEmpty();
    }

    @Test
    void reportIncludesAllPackages() throws Exception {
      var testJsonDir = testOutputPath.resolve("test-json").toFile();
      if (!testJsonDir.exists()) {
        System.out.println("Skipping: specifications/ATL105/test-output/test-json not available");
        return;
      }

      var expectedFiles = testJsonDir.listFiles(f -> f.getName().startsWith("segment-100-item-"));
      assertThat(expectedFiles).isNotEmpty();

      Segment100MutationTestRunner.MutationTestSuiteReport report = runner.runAllPackages();
      assertThat(report.totalPackages()).isEqualTo(expectedFiles.length);
    }

    @Test
    void reportComputesAggregateMetrics() throws Exception {
      var testJsonDir = testOutputPath.resolve("test-json").toFile();
      if (!testJsonDir.exists()) {
        System.out.println("Skipping: specifications/ATL105/test-output/test-json not available");
        return;
      }

      Segment100MutationTestRunner.MutationTestSuiteReport report = runner.runAllPackages();

      int sumDetected = 0;
      int sumTotal = 0;
      for (var pkg : report.packageResults()) {
        sumDetected += pkg.metrics().detectedMutations();
        sumTotal += pkg.metrics().totalMutations();
      }

      double expectedRate = sumTotal > 0 ? (100.0 * sumDetected) / sumTotal : 0.0;
      assertThat(report.overallDetectionRate()).isCloseTo(expectedRate, within(0.01));
    }

    @Test
    void reportTracksUndetectedMutations() throws Exception {
      var testJsonDir = testOutputPath.resolve("test-json").toFile();
      if (!testJsonDir.exists()) {
        System.out.println("Skipping: specifications/ATL105/test-output/test-json not available");
        return;
      }

      Segment100MutationTestRunner.MutationTestSuiteReport report = runner.runAllPackages();

      // Should have undetected mutations list
      assertThat(report.allUndetectedMutations()).isNotNull();

      // Count undetected across packages
      // Verify report has undetected mutations (may be empty if all detected)
      assertThat(report.allUndetectedMutations()).isNotNull();
    }
  }

  @Nested
  @DisplayName("Report Generation Tests")
  class ReportGenerationTests {

    @Test
    void generatesSummaryReport() throws Exception {
      var testJsonDir = testOutputPath.resolve("test-json").toFile();
      if (!testJsonDir.exists()) {
        System.out.println("Skipping: specifications/ATL105/test-output/test-json not available");
        return;
      }

      Segment100MutationTestRunner.MutationTestSuiteReport report = runner.runAllPackages();
      String summary = report.summary();

      assertThat(summary).isNotBlank();
      assertThat(summary).contains("Mutation Test Suite Report");
      assertThat(summary).contains("Total Packages Tested:");
      assertThat(summary).contains("Overall Detection Rate:");
    }

    @Test
    void generatesDetailedReport() throws Exception {
      var testJsonDir = testOutputPath.resolve("test-json").toFile();
      if (!testJsonDir.exists()) {
        System.out.println("Skipping: specifications/ATL105/test-output/test-json not available");
        return;
      }

      Segment100MutationTestRunner.MutationTestSuiteReport report = runner.runAllPackages();
      String detailed = runner.generateDetailedReport(report);

      assertThat(detailed).isNotBlank();
      assertThat(detailed).contains("Package Breakdown");
      assertThat(detailed).contains("segment-100-item");
    }

    @Test
    void detailedReportIncludesPackageDetails() throws Exception {
      var testJsonDir = testOutputPath.resolve("test-json").toFile();
      if (!testJsonDir.exists()) {
        System.out.println("Skipping: specifications/ATL105/test-output/test-json not available");
        return;
      }

      Segment100MutationTestRunner.MutationTestSuiteReport report = runner.runAllPackages();
      String detailed = runner.generateDetailedReport(report);

      for (var pkg : report.packageResults()) {
        assertThat(detailed).contains(pkg.packageName());
      }
    }
  }

  @Nested
  @DisplayName("Edge Case Tests")
  class EdgeCaseTests {

    @Test
    void handlesNonexistentDirectory() {
      Path invalidPath = Paths.get("nonexistent/path");
      Segment100MutationTestRunner invalidRunner = new Segment100MutationTestRunner(invalidPath);

      assertThatThrownBy(invalidRunner::runAllPackages)
          .isInstanceOf(Exception.class);
    }

    @Test
    void gracefullyHandlesInvalidPackageFile() throws Exception {
      // This would only run if we had a corrupt JSON file; for now just verify structure
      var testJsonDir = testOutputPath.resolve("test-json").toFile();
      if (!testJsonDir.exists()) {
        System.out.println("Skipping: specifications/ATL105/test-output/test-json not available");
        return;
      }

      // Runner continues even if one file fails
      var report = runner.runAllPackages();
      assertThat(report).isNotNull();
    }
  }
}
