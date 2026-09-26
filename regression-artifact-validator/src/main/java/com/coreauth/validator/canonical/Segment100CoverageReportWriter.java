package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Writes the Segment 100 coverage result as JSON and Markdown for Test Team review. */
public final class Segment100CoverageReportWriter {
    private final ObjectMapper mapper = new ObjectMapper();

    public ReportFiles write(Segment100CoverageAnalyzer.CoverageReport report,
                             String packageId, Path outputDirectory) throws IOException {
        return write(report, packageId, outputDirectory, java.util.List.of());
    }

    public ReportFiles write(Segment100CoverageAnalyzer.CoverageReport report,
                             String packageId, Path outputDirectory,
                             java.util.List<Segment100RuleCatalogLoader.Rule> catalogRules) throws IOException {
        Files.createDirectories(outputDirectory);
        String effectivePackageId = packageId == null ? "segment-100-package" : packageId;
        Path jsonFile = outputDirectory.resolve("segment-100-coverage.json");
        Path markdownFile = outputDirectory.resolve("segment-100-coverage.md");

        ObjectNode root = mapper.createObjectNode();
        root.put("packageId", effectivePackageId);
        root.put("status", overallStatus(report));
        root.put("covered", report.count("COVERED"));
        root.put("partiallyCovered", report.count("PARTIALLY_COVERED"));
        root.put("reviewRequired", report.count("REVIEW_REQUIRED"));
        root.put("missing", report.count("MISSING"));
        root.put("manualReviewRequired", report.count("REVIEW_REQUIRED") > 0);
        root.put("reviewPolicy", "Unknown segment combinations are held for manual review and are not auto-approved.");
        ArrayNode entries = root.putArray("rules");
        for (Segment100CoverageAnalyzer.CoverageEntry entry : report.entries()) {
            ObjectNode item = entries.addObject();
            Segment100RuleCatalogLoader.Rule catalogRule = catalogRules.stream()
                    .filter(rule -> rule.sourceAnchor().canonicalKey().equals(entry.anchorKey()))
                    .findFirst().orElse(null);
            if (catalogRule != null) {
                item.put("ruleId", catalogRule.ruleId());
                item.put("title", catalogRule.title());
            }
            item.put("anchor", entry.anchorKey());
            item.put("status", entry.status());
            item.put("businessRequirement", entry.requirement());
            item.put("scenario", entry.scenario());
            item.put("testCase", entry.testCase());
            item.put("testData", entry.testData());
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile.toFile(), root);
        Files.writeString(markdownFile, markdown(root));
        return new ReportFiles(jsonFile, markdownFile);
    }

    private static String overallStatus(Segment100CoverageAnalyzer.CoverageReport report) {
        if (report.count("MISSING") > 0 || report.count("PARTIALLY_COVERED") > 0) {
            return "REJECTED_MISSING_COVERAGE";
        }
        if (report.count("REVIEW_REQUIRED") > 0) {
            return "APPROVED_WITH_REVIEW_ITEMS";
        }
        return "APPROVED";
    }

    private static String markdown(ObjectNode root) {
        StringBuilder output = new StringBuilder();
        output.append("# Segment 100 Coverage Report\n\n")
                .append("- Package: `").append(root.path("packageId").asText()).append("`\n")
                .append("- Status: **").append(root.path("status").asText()).append("**\n")
                .append("- Covered: ").append(root.path("covered").asInt()).append("\n")
                .append("- Partially covered: ").append(root.path("partiallyCovered").asInt()).append("\n")
                .append("- Review required: ").append(root.path("reviewRequired").asInt()).append("\n")
                .append("- Missing: ").append(root.path("missing").asInt()).append("\n\n")
                .append("| Rule | Title | Canonical anchor | Status | BR | Scenario | Test Case | Test Data |\n")
                .append("| --- | --- | --- | --- | --- | --- | --- | --- |\n");
        for (var entry : root.path("rules")) {
            output.append("| `").append(entry.path("ruleId").asText("unmapped")).append("` | ")
                    .append(entry.path("title").asText("Unmapped catalog rule")).append(" | `")
                    .append(entry.path("anchor").asText()).append("` | ")
                    .append(entry.path("status").asText()).append(" | ")
                    .append(mark(entry.path("businessRequirement").asBoolean())).append(" | ")
                    .append(mark(entry.path("scenario").asBoolean())).append(" | ")
                    .append(mark(entry.path("testCase").asBoolean())).append(" | ")
                    .append(mark(entry.path("testData").asBoolean())).append(" |\n");
        }
        return output.toString();
    }

    private static String mark(boolean value) {
        return value ? "yes" : "no";
    }

    public record ReportFiles(Path json, Path markdown) {
    }
}