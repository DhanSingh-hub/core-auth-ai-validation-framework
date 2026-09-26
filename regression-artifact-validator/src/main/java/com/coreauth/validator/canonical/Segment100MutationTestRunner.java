package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * Segment100MutationTestRunner: Execute mutation testing framework against all
 * segment-100-item-*.json packages in test-output folder.
 *
 * Purpose: Measure detector effectiveness across all test packages and identify
 * gaps where validators fail to catch rule violations.
 */
public class Segment100MutationTestRunner {

  private final ObjectMapper objectMapper;
  private final Path testOutputPath;

  public Segment100MutationTestRunner(Path testOutputPath) {
    this.objectMapper = new ObjectMapper();
    this.testOutputPath = testOutputPath;
  }

  /**
   * Represents results from testing one package.
   */
  public record PackageMutationResult(
      String packageName,
      int testDataCount,
      int mutationCount,
      Segment100MutationTester.MutationMetrics metrics,
      List<String> undetectedMutations) {}

  /**
   * Aggregated results across all packages.
   */
  public record MutationTestSuiteReport(
      int totalPackages,
      int totalTestData,
      int totalMutationsRun,
      double overallDetectionRate,
      List<PackageMutationResult> packageResults,
      List<String> allUndetectedMutations,
      Map<String, Integer> mutationGapsByRule) {

    public String summary() {
      return String.format(
          "Mutation Test Suite Report\n"
              + "==========================\n"
              + "Total Packages Tested: %d\n"
              + "Total Test Data Points: %d\n"
              + "Total Mutations Run: %d\n"
              + "Overall Detection Rate: %.2f%%\n"
              + "Total Unique Undetected Mutations: %d\n"
              + "Packages with Coverage Gaps: %d",
          totalPackages,
          totalTestData,
          totalMutationsRun,
          overallDetectionRate,
          allUndetectedMutations.size(),
          (int) packageResults.stream().filter(r -> r.metrics.detectionRate() < 100).count());
    }
  }

  /**
   * Load a package from JSON file.
   */
  public CanonicalArtifactPackage loadPackage(File jsonFile) throws IOException {
    return objectMapper.readValue(jsonFile, CanonicalArtifactPackage.class);
  }

  /**
   * Test one package with all standard mutations.
   */
  public PackageMutationResult testPackage(File jsonFile)
      throws IOException {
    String packageName = jsonFile.getName();
    CanonicalArtifactPackage pkg = loadPackage(jsonFile);

    List<Segment100MutationTester.MutationCase> mutations =
        Segment100MutationTester.generateStandardMutations();
    Segment100MutationTester.MutationReport report =
        Segment100MutationTester.runMutationSuite(pkg, mutations);

    List<String> undetected = new ArrayList<>(report.metrics().undetectedMutations());

    return new PackageMutationResult(
        packageName,
        pkg.getTestData() != null ? pkg.getTestData().size() : 0,
        mutations.size(),
        report.metrics(),
        undetected);
  }

  /**
   * Find and test all segment-100-item-*.json packages.
   */
  public MutationTestSuiteReport runAllPackages() throws IOException {
    File testJsonDir = testOutputPath.resolve("test-json").toFile();
    if (!testJsonDir.exists()) {
      throw new IOException("test-json directory not found: " + testJsonDir);
    }

    File[] packageFiles =
        testJsonDir.listFiles(f -> f.getName().matches("segment-100-item-.*\\.json"));
    if (packageFiles == null || packageFiles.length == 0) {
      throw new IOException("No segment-100-item-*.json files found in " + testJsonDir);
    }

    Arrays.sort(packageFiles, Comparator.comparing(File::getName));

    List<PackageMutationResult> results = new ArrayList<>();
    for (File file : packageFiles) {
      try {
        results.add(testPackage(file));
      } catch (Exception e) {
        // Log but continue with next package
        System.err.println("Error testing package " + file.getName() + ": " + e.getMessage());
      }
    }

    return aggregateResults(results);
  }

  /**
   * Aggregate results from all packages.
   */
  private MutationTestSuiteReport aggregateResults(List<PackageMutationResult> results) {
    int totalPackages = results.size();
    int totalTestData = results.stream().mapToInt(PackageMutationResult::testDataCount).sum();
    int totalMutations = results.stream().mapToInt(PackageMutationResult::mutationCount).sum();

    int totalDetected = 0;
    int totalMutationsCount = 0;
    for (PackageMutationResult result : results) {
      Segment100MutationTester.MutationMetrics metrics = result.metrics();
      totalDetected += metrics.detectedMutations();
      totalMutationsCount += metrics.totalMutations();
    }

    double overallDetectionRate =
        totalMutationsCount > 0 ? (100.0 * totalDetected) / totalMutationsCount : 0;

    // Collect all undetected mutations
    Set<String> allUndetected = new HashSet<>();
    Map<String, Integer> gapsByRule = new HashMap<>();
    for (PackageMutationResult result : results) {
      allUndetected.addAll(result.undetectedMutations());
      for (String mutId : result.undetectedMutations()) {
        String rule = extractRuleId(mutId);
        gapsByRule.put(rule, gapsByRule.getOrDefault(rule, 0) + 1);
      }
    }

    return new MutationTestSuiteReport(
        totalPackages,
        totalTestData,
        totalMutations,
        overallDetectionRate,
        results,
        new ArrayList<>(allUndetected),
        gapsByRule);
  }

  /**
   * Extract rule ID from mutation ID (e.g., MUT-001 -> SEG100-R-015).
   */
  private String extractRuleId(String mutationId) {
    // For now, return the mutation ID; can enhance if mutation details are available
    return mutationId;
  }

  /**
   * Generate detailed report with package-by-package breakdown.
   */
  public String generateDetailedReport(MutationTestSuiteReport report) {
    StringBuilder sb = new StringBuilder();
    sb.append(report.summary()).append("\n\n");

    sb.append("Package Breakdown\n");
    sb.append("=================\n");
    for (PackageMutationResult pkg : report.packageResults()) {
      sb.append(String.format(
          "\n%s\n"
              + "  Test Data Points: %d\n"
              + "  Mutations Run: %d\n"
              + "  Detection Rate: %.2f%% (%d/%d)\n"
              + "  Undetected: %d",
          pkg.packageName(),
          pkg.testDataCount(),
          pkg.mutationCount(),
          pkg.metrics().detectionRate(),
          pkg.metrics().detectedMutations(),
          pkg.metrics().totalMutations(),
          pkg.undetectedMutations().size()));

      if (!pkg.undetectedMutations().isEmpty()) {
        sb.append("\n  Gaps: ").append(String.join(", ", pkg.undetectedMutations()));
      }
    }

    if (!report.mutationGapsByRule().isEmpty()) {
      sb.append("\n\nMutation Gaps by Rule\n");
      sb.append("=====================\n");
      report.mutationGapsByRule().entrySet().stream()
          .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
          .forEach(
              e ->
                  sb.append(String.format(
                      "%s: %d undetected instances\n", e.getKey(), e.getValue())));
    }

    return sb.toString();
  }
}
