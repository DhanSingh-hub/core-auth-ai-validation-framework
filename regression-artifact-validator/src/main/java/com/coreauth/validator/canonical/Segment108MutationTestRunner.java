package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Item 6 (Mutation Framework Execution) for Segment 108.
 *
 * <p>Batch-runs the standard mutation suite from {@link Segment108MutationTester} across every
 * synthetic AI JSON payload under {@code specifications/ATL105/test-input/ai-solution/test-data/segment-108/} and
 * aggregates detection metrics. Mirrors {@link Segment103MutationTestRunner}.
 */
public final class Segment108MutationTestRunner {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Path testInputPath;

    public Segment108MutationTestRunner(Path testInputPath) {
        this.testInputPath = testInputPath;
    }

    public record PackageMutationResult(
        String packageName,
        int mutationCount,
        Segment108MutationTester.MutationMetrics metrics,
        List<String> undetectedMutations
    ) {}

    public record MutationTestSuiteReport(
        int totalPackages,
        int totalMutationsRun,
        double overallDetectionRate,
        List<PackageMutationResult> packageResults,
        List<String> allUndetectedMutations,
        Map<String, Integer> mutationGapsByRule
    ) {
        public String summary() {
            return String.format(
                "Segment 108 Mutation Test Suite Report%n"
                    + "======================================%n"
                    + "Total Packages Tested: %d%n"
                    + "Total Mutations Run: %d%n"
                    + "Overall Detection Rate: %.2f%%%n"
                    + "Unique Undetected Mutations: %d%n"
                    + "Packages With Gaps: %d",
                totalPackages, totalMutationsRun, overallDetectionRate,
                allUndetectedMutations.size(),
                (int) packageResults.stream().filter(r -> r.metrics.detectionRate() < 100).count()
            );
        }
    }

    public JsonNode loadPackage(Path jsonFile) throws IOException {
        return mapper.readTree(jsonFile.toFile());
    }

    public PackageMutationResult testPackage(Path jsonFile) throws IOException {
        JsonNode base = loadPackage(jsonFile);
        List<Segment108MutationTester.MutationCase> mutations =
            Segment108MutationTester.generateStandardMutations();
        Segment108MutationTester.MutationReport report =
            Segment108MutationTester.runMutationSuite(base, mutations);
        return new PackageMutationResult(
            jsonFile.getFileName().toString(),
            mutations.size(),
            report.metrics(),
            new ArrayList<>(report.metrics().undetectedMutations())
        );
    }

    public MutationTestSuiteReport runAllPackages() throws IOException {
        List<Path> packages = discoverPackages();
        List<PackageMutationResult> results = new ArrayList<>();
        for (Path pkg : packages) {
            results.add(testPackage(pkg));
        }
        return aggregate(results);
    }

    public List<Path> discoverPackages() throws IOException {
        if (!Files.exists(testInputPath)) {
            return List.of();
        }
        try (Stream<Path> stream = Files.walk(testInputPath)) {
            return stream
                .filter(Files::isRegularFile)
                .filter(p -> p.getFileName().toString().endsWith(".synthetic.json"))
                .filter(p -> !p.toString().contains("rejection"))
                .sorted()
                .collect(Collectors.toList());
        }
    }

    private static MutationTestSuiteReport aggregate(List<PackageMutationResult> results) {
        int totalPackages = results.size();
        int totalMutations = results.stream().mapToInt(PackageMutationResult::mutationCount).sum();
        long totalDetected = results.stream()
            .mapToLong(r -> r.metrics.detectedMutations())
            .sum();
        double overallRate = totalMutations == 0 ? 0 : (totalDetected * 100.0) / totalMutations;

        List<String> allUndetected = results.stream()
            .flatMap(r -> r.undetectedMutations.stream())
            .distinct()
            .toList();

        Map<String, Integer> gapsByRule = new TreeMap<>();
        for (PackageMutationResult r : results) {
            for (String undetected : r.undetectedMutations) {
                String ruleId = extractRuleId(undetected);
                gapsByRule.merge(ruleId, 1, Integer::sum);
            }
        }

        return new MutationTestSuiteReport(totalPackages, totalMutations, overallRate,
            results, allUndetected, gapsByRule);
    }

    private static String extractRuleId(String entry) {
        int open = entry.indexOf('(');
        int close = entry.indexOf(')');
        if (open >= 0 && close > open) {
            return entry.substring(open + 1, close).trim();
        }
        return entry;
    }
}
