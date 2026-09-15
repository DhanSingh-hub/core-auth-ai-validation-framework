package com.coreauth.validator.canonical;

import java.nio.file.Paths;

/**
 * Item 6 Mutation Test Runner: Execute mutations against all test-output packages
 * and generate comprehensive coverage report.
 *
 * This is a standalone runner that loads packages from test-output folder and
 * executes the mutation testing framework to measure validator effectiveness.
 */
public class Item6MutationRunner {

  public static void main(String[] args) throws Exception {
    System.out.println("=" .repeat(80));
    System.out.println("ITEM 6: Mutation Testing Framework Execution");
    System.out.println("=".repeat(80));
    System.out.println();

    // Use path relative to POM location (regression-artifact-validator folder)
    var testOutputPath = Paths.get("test-output");

    System.out.println("Working Directory: " + System.getProperty("user.dir"));
    System.out.println("Test Output Path: " + testOutputPath.toAbsolutePath());
    System.out.println();

    try {
      var runner = new Segment100MutationTestRunner(testOutputPath);
      System.out.println("Running mutation tests on all segment-100-item-*.json packages...");
      System.out.println();

      var report = runner.runAllPackages();

      System.out.println(report.summary());
      System.out.println();
      System.out.println("-".repeat(80));
      System.out.println(runner.generateDetailedReport(report));
      System.out.println("-".repeat(80));

    } catch (Exception e) {
      System.err.println("Error running mutation tests: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
