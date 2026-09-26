package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Builds a detailed BR-to-TS-to-TC-to-TD link-gap report for the AI team, using Run2's own trace_status field. */
public final class GenerateAiTeamLinkGapReport {
    private static final int SAMPLE_ROWS_PER_STATUS = 10;

    private record GapRow(String requirementId, String segmentId, String segmentName, String traceStatus,
                          String gapReason, int sourcePage, int scenarios, int testCases, int testDataFiles) { }

    private GenerateAiTeamLinkGapReport() { }

    public static void main(String[] args) throws Exception {
        Path traceability = args.length == 0
                ? Atl105Paths.testInput().resolve(Path.of("ai-solution", "runs", "2026-09-23",
                        "Run2", "traceability_matrix_full.json"))
                : Path.of(args[0]);
        Path outputRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        Files.createDirectories(outputRoot);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Long> statusTotals = new TreeMap<>();
        Map<String, Map<String, Long>> segmentByStatus = new TreeMap<>();
        List<GapRow> gapRows = new ArrayList<>();
        List<String[]> orphanScenarios = new ArrayList<>();
        long totalRequirements = 0;

        try (JsonParser parser = mapper.getFactory().createParser(traceability.toFile())) {
            require(parser.nextToken() == JsonToken.START_OBJECT, "root must be an object");
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String field = parser.currentName();
                parser.nextToken();
                if ("requirements".equals(field)) {
                    require(parser.currentToken() == JsonToken.START_ARRAY, "requirements must be an array");
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        JsonNode requirement = mapper.readTree(parser);
                        totalRequirements++;
                        String status = requirement.path("trace_status").asText("UNKNOWN");
                        String segmentId = requirement.path("segment_id").asText("UNMAPPED");
                        statusTotals.merge(status, 1L, Long::sum);
                        segmentByStatus.computeIfAbsent(segmentId, ignored -> new TreeMap<>())
                                .merge(status, 1L, Long::sum);
                        if (!"FULLY_TRACED".equals(status)) {
                            JsonNode counts = requirement.path("counts");
                            gapRows.add(new GapRow(
                                    requirement.path("requirement_id").asText(""),
                                    segmentId,
                                    requirement.path("segment_name").asText(""),
                                    status,
                                    requirement.path("gap_reason").asText(""),
                                    requirement.path("source_page").asInt(-1),
                                    counts.path("scenarios").asInt(0),
                                    counts.path("test_cases").asInt(0),
                                    counts.path("test_data_files").asInt(0)));
                        }
                    }
                } else if ("orphans".equals(field)) {
                    JsonNode orphans = mapper.readTree(parser);
                    for (JsonNode orphan : orphans.path("scenarios_without_requirement")) {
                        orphanScenarios.add(new String[] {
                                orphan.path("scenario_id").asText(""), orphan.path("reason").asText("") });
                    }
                } else {
                    parser.skipChildren();
                }
            }
        }

        writeDetailsCsv(outputRoot.resolve("run2-br-ts-tc-td-gap-details.csv"), gapRows);
        writeOrphansCsv(outputRoot.resolve("run2-orphan-scenarios.csv"), orphanScenarios);
        writeMarkdown(outputRoot.resolve("run2-br-ts-tc-td-gap-report-for-ai-team.md"),
                totalRequirements, statusTotals, segmentByStatus, gapRows, orphanScenarios);

        System.out.printf("totalRequirements=%d gapRows=%d orphanScenarios=%d%n",
                totalRequirements, gapRows.size(), orphanScenarios.size());
    }

    private static void writeDetailsCsv(Path output, List<GapRow> rows) throws Exception {
        StringBuilder csv = new StringBuilder(
                "requirement_id,segment_id,segment_name,trace_status,gap_reason,source_page,scenarios,test_cases,test_data_files\n");
        for (GapRow row : rows) {
            csv.append(csv(row.requirementId())).append(',').append(csv(row.segmentId())).append(',')
                    .append(csv(row.segmentName())).append(',').append(csv(row.traceStatus())).append(',')
                    .append(csv(row.gapReason())).append(',').append(row.sourcePage()).append(',')
                    .append(row.scenarios()).append(',').append(row.testCases()).append(',')
                    .append(row.testDataFiles()).append('\n');
        }
        Files.writeString(output, csv);
    }

    private static void writeOrphansCsv(Path output, List<String[]> orphans) throws Exception {
        StringBuilder csv = new StringBuilder("scenario_id,reason\n");
        for (String[] orphan : orphans) csv.append(csv(orphan[0])).append(',').append(csv(orphan[1])).append('\n');
        Files.writeString(output, csv);
    }

    private static void writeMarkdown(Path output, long totalRequirements, Map<String, Long> statusTotals,
                                      Map<String, Map<String, Long>> segmentByStatus, List<GapRow> gapRows,
                                      List<String[]> orphanScenarios) throws Exception {
        StringBuilder text = new StringBuilder();
        text.append("# Run2 BR -> TS -> TC -> TD Link Gap Report\n\n")
                .append("**Audience:** AI Solution team\n")
                .append("**Source:** `traceability_matrix_full.json` (Run2, 2026-09-23), using the file's own `trace_status` field\n")
                .append("**Purpose:** Identify every business requirement where the AI-generated chain does not reach test data, so scenario/test-case/test-data generation can be re-run for the affected items.\n\n")
                .append("## 1. Overall link-gap summary\n\n")
                .append("Total business requirements: **").append(totalRequirements).append("**\n\n")
                .append("| trace_status | Count | % of total | Meaning |\n|---|---:|---:|---|\n");
        for (Map.Entry<String, Long> entry : statusTotals.entrySet()) {
            text.append('|').append(entry.getKey()).append('|').append(entry.getValue()).append('|')
                    .append(String.format("%.1f%%", 100.0 * entry.getValue() / totalRequirements)).append('|')
                    .append(meaning(entry.getKey())).append("|\n");
        }
        text.append('\n');

        text.append("## 2. Gap breakdown by segment\n\n")
                .append("Segments ranked by total non-fully-traced requirements. Only segments with at least one gap are shown.\n\n")
                .append("| Segment | REQUIREMENT_ONLY (no TS) | SCENARIO_ONLY (no TC) | TEST_CASE_NO_DATA (no TD) | Other gaps | Total gaps |\n")
                .append("|---|---:|---:|---:|---:|---:|\n");
        segmentByStatus.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), segmentGapTotal(entry.getValue())))
                .filter(entry -> entry.getValue() > 0)
                .sorted(Comparator.<Map.Entry<String, Long>>comparingLong(Map.Entry::getValue).reversed())
                .forEach(entry -> {
                    Map<String, Long> byStatus = segmentByStatus.get(entry.getKey());
                    long reqOnly = byStatus.getOrDefault("REQUIREMENT_ONLY", 0L);
                    long scnOnly = byStatus.getOrDefault("SCENARIO_ONLY", 0L);
                    long tcNoData = byStatus.getOrDefault("TEST_CASE_NO_DATA", 0L);
                    long other = entry.getValue() - reqOnly - scnOnly - tcNoData;
                    text.append('|').append(entry.getKey()).append('|').append(reqOnly).append('|')
                            .append(scnOnly).append('|').append(tcNoData).append('|').append(other).append('|')
                            .append(entry.getValue()).append("|\n");
                });
        text.append('\n');

        text.append("## 3. Sample requirements per gap type\n\n")
                .append("Up to ").append(SAMPLE_ROWS_PER_STATUS).append(" examples per status. ")
                .append("Full list (all ").append(gapRows.size()).append(" gap rows) is in the companion CSV: ")
                .append("`run2-br-ts-tc-td-gap-details.csv`.\n\n");
        for (String status : statusTotals.keySet()) {
            if ("FULLY_TRACED".equals(status)) continue;
            List<GapRow> sample = gapRows.stream().filter(row -> status.equals(row.traceStatus()))
                    .limit(SAMPLE_ROWS_PER_STATUS).toList();
            if (sample.isEmpty()) continue;
            text.append("### ").append(status).append(" (").append(
                    gapRows.stream().filter(row -> status.equals(row.traceStatus())).count()).append(" total)\n\n")
                    .append("| Requirement ID | Segment | Page | Scenarios | Test Cases | Test Data | Gap reason |\n")
                    .append("|---|---|---:|---:|---:|---:|---|\n");
            for (GapRow row : sample) {
                text.append('|').append(row.requirementId()).append('|').append(row.segmentId()).append('|')
                        .append(row.sourcePage()).append('|').append(row.scenarios()).append('|')
                        .append(row.testCases()).append('|').append(row.testDataFiles()).append('|')
                        .append(cell(row.gapReason())).append("|\n");
            }
            text.append('\n');
        }

        text.append("## 4. Orphan scenarios (no requirement link at all)\n\n")
                .append("Total orphan scenarios: **").append(orphanScenarios.size()).append("**. ")
                .append("These scenarios exist with no traceable parent requirement. Full list: `run2-orphan-scenarios.csv`.\n\n")
                .append("| Scenario ID | Reason |\n|---|---|\n");
        orphanScenarios.stream().limit(SAMPLE_ROWS_PER_STATUS)
                .forEach(orphan -> text.append('|').append(orphan[0]).append('|').append(orphan[1]).append("|\n"));
        text.append('\n');

        text.append("## 5. Requested actions for the AI team\n\n")
                .append("1. **REQUIREMENT_ONLY** requirements: run/expand Scenario Generation so every requirement has at least one scenario.\n")
                .append("2. **SCENARIO_ONLY** requirements: run Test Case Generation for the listed scenarios.\n")
                .append("3. **TEST_CASE_NO_DATA** requirements: re-run Test Case Generation so `qe_shaped_test_data/*.json` files are produced and resolve on disk for every listed test case.\n")
                .append("4. **Orphan scenarios**: attach a `requirement_id` to each orphan scenario, or confirm and document why no requirement applies.\n")
                .append("5. Re-publish an updated `traceability_matrix_full.json` referencing the same `deliveryId` so the Test Solution can re-run this gap analysis and confirm closure.\n\n")
                .append("## 6. Companion files\n\n")
                .append("- `run2-br-ts-tc-td-gap-details.csv` — one row per non-fully-traced requirement\n")
                .append("- `run2-orphan-scenarios.csv` — one row per orphan scenario\n")
                .append("- `run2-traceability-gap-analysis.json` — independently recomputed link-gap counts (cross-check)\n");

        Files.writeString(output, text.toString());
    }

    private static long segmentGapTotal(Map<String, Long> byStatus) {
        return byStatus.entrySet().stream().filter(entry -> !"FULLY_TRACED".equals(entry.getKey()))
                .mapToLong(Map.Entry::getValue).sum();
    }

    private static String meaning(String status) {
        return switch (status) {
            case "FULLY_TRACED" -> "Concrete test data resolved on disk; BR->TS->TC->TD fully linked.";
            case "REQUIREMENT_ONLY" -> "No scenario generated from this requirement (BR -> TS gap).";
            case "SCENARIO_ONLY" -> "Scenario(s) exist but no test case generated (TS -> TC gap).";
            case "TEST_CASE_NO_DATA" -> "Test case(s) exist but no test data file resolves on disk (TC -> TD gap).";
            case "BROKEN_LINK" -> "A downstream artifact points at an ID that no longer exists upstream.";
            default -> "Unclassified status; needs review.";
        };
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"").replace("\r", " ").replace("\n", " ") + "\"";
    }

    private static String cell(String value) {
        return value.replace("|", "\\|").replace("\n", " ");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }
}
