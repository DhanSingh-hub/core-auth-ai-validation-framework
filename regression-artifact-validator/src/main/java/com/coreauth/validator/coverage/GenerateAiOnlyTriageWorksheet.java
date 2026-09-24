package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Turns the automated triage into a ranked SME worklist, starting with the highest-priority segment. */
public final class GenerateAiOnlyTriageWorksheet {
    private static final int SAMPLE_SIZE = 40;

    private record Row(String requirementId, String segment, String category, String priority,
                       int sourcePage, String statement) { }

    private GenerateAiOnlyTriageWorksheet() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        JsonNode summary = mapper.readTree(reviewRoot.resolve("ai-only-trained-segment-triage.json").toFile());

        List<JsonNode> ranked = new ArrayList<>();
        summary.path("bySegment").forEach(ranked::add);
        ranked.sort(Comparator.comparingLong((JsonNode row) -> row.path("elementNotInCatalogNewRuleCandidate").asLong()).reversed());

        List<Row> rows = readCsv(reviewRoot.resolve("ai-only-trained-segment-triage.csv"));
        String topSegment = ranked.get(0).path("segment").asText();
        List<Row> topSegmentCandidates = rows.stream()
                .filter(row -> row.segment().equals(topSegment) && "ELEMENT_NOT_IN_CATALOG_NEW_RULE_CANDIDATE".equals(row.category()))
                .sorted(Comparator.comparingInt(Row::sourcePage))
                .toList();

        StringBuilder text = new StringBuilder("# AI-Only Trained-Segment SME Triage Worklist\n\n")
                .append("Ranked by HIGH_SME_REVIEW volume (`ELEMENT_NOT_IN_CATALOG_NEW_RULE_CANDIDATE`). Start with segment ")
                .append(topSegment).append(" — it has the largest gap between AI output volume and the Test Solution's own rule catalog.\n\n")
                .append("## Segment priority ranking\n\n")
                .append("| Segment | HIGH_SME_REVIEW (new-rule candidates) | LOW_REMATCH (matching gap) | Total AI-only |\n")
                .append("|---|---:|---:|---:|\n");
        for (JsonNode row : ranked) {
            text.append('|').append(row.path("segment").asText()).append('|')
                    .append(row.path("elementNotInCatalogNewRuleCandidate").asLong()).append('|')
                    .append(row.path("elementInCatalogButUnlinked").asLong()).append('|')
                    .append(row.path("total").asLong()).append("|\n");
        }

        text.append("\n## Segment ").append(topSegment).append(" — first worklist (")
                .append(topSegmentCandidates.size()).append(" total new-rule candidates; showing first ")
                .append(Math.min(SAMPLE_SIZE, topSegmentCandidates.size())).append(", sorted by source page)\n\n")
                .append("Each row needs an SME decision: **NEW_RULE** (independently derive a rule from the spec at this page, ")
                .append("then build its own TS/TC/TD chain), **DUPLICATE** (matches an existing rule after closer reading; ")
                .append("record which one), or **REJECT** (not a real, independently verifiable requirement).\n\n")
                .append("| Requirement ID | Source page | Statement | SME decision |\n|---|---:|---|---|\n");
        topSegmentCandidates.stream().limit(SAMPLE_SIZE).forEach(row ->
                text.append('|').append(row.requirementId()).append('|').append(row.sourcePage()).append('|')
                        .append(cell(row.statement())).append("| |\n"));

        text.append("\nFull list (all ").append(topSegmentCandidates.size())
                .append(" segment ").append(topSegment).append(" candidates, plus every other segment): `ai-only-trained-segment-triage.csv`.\n");

        Files.writeString(reviewRoot.resolve("ai-only-trained-segment-sme-worklist.md"), text.toString());
        System.out.printf("topSegment=%s newRuleCandidates=%d%n", topSegment, topSegmentCandidates.size());
    }

    private static List<Row> readCsv(Path file) throws Exception {
        List<Row> rows = new ArrayList<>();
        List<String> lines = Files.readAllLines(file);
        for (int i = 1; i < lines.size(); i++) {
            String[] fields = parseCsvLine(lines.get(i));
            if (fields.length < 8) continue;
            rows.add(new Row(fields[0], fields[1], fields[4], fields[5],
                    parseIntSafe(fields[6]), fields[7]));
        }
        return rows;
    }

    private static int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String[] parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"' && i + 1 < line.length() && line.charAt(i + 1) == '"') { current.append('"'); i++; }
                else if (c == '"') inQuotes = false;
                else current.append(c);
            } else if (c == '"') {
                inQuotes = true;
            } else if (c == ',') {
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());
        return fields.toArray(new String[0]);
    }

    private static String cell(String value) {
        return value.replace("|", "\\|").replace("\n", " ");
    }
}
