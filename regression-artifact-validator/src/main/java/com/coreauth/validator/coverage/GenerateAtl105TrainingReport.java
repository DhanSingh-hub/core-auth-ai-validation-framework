package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

/** Reconciles training decisions with the KB catalogs and publishes one current snapshot. */
public final class GenerateAtl105TrainingReport {
    private GenerateAtl105TrainingReport() { }

    public static void main(String[] args) throws Exception {
        Path root = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        Path kb = root.resolve("docs/specs/kb");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode training = mapper.readTree(root.resolve("training-status.json").toFile());
        JsonNode segments = training.path("segments");
        Map<String, Integer> catalogCounts = new TreeMap<>();
        try (Stream<Path> files = Files.walk(kb)) {
            for (Path catalog : files.filter(path -> path.getFileName().toString().matches("segment-.*-rule-catalog\\.json")).toList()) {
                String segment = catalog.getParent().getParent().getFileName().toString().substring("segment-".length()).toUpperCase();
                int count = mapper.readTree(catalog.toFile()).path("rules").size();
                if (catalogCounts.putIfAbsent(segment, count) != null) {
                    throw new IllegalStateException("Duplicate catalog for segment " + segment);
                }
            }
        }
        if (catalogCounts.size() != segments.size()) {
            throw new IllegalStateException("Catalog/segment count differs: " + catalogCounts.size() + " / " + segments.size());
        }
        int totalRules = 0;
        int inProgress = 0;
        int trained = 0;
        int openBlockers = 0;
        int substantive = 0;
        StringBuilder rows = new StringBuilder();
        for (Map.Entry<String, Integer> catalog : catalogCounts.entrySet()) {
            String segment = catalog.getKey();
            JsonNode entry = segments.path(segment);
            int count = catalog.getValue();
            if (entry.isMissingNode() || entry.path("ruleCount").asInt(-1) != count) {
                throw new IllegalStateException("Training rule count differs from catalog for " + segment);
            }
            totalRules += count;
            if ("IN_PROGRESS".equals(entry.path("status").asText())) inProgress++;
            if ("TRAINED_FOR_INTAKE".equals(entry.path("status").asText())) trained++;
            if ("SUBSTANTIVE".equals(entry.path("evidenceLevel").asText())) substantive++;
            if (!entry.path("blockers").isEmpty()) openBlockers++;
            rows.append("| ").append(segment).append(" | ").append(count).append(" | ")
                .append(entry.path("evidenceLevel").asText()).append(" | ")
                .append(entry.path("status").asText()).append(" | ")
                .append(entry.path("blockers").size()).append(" | ")
                .append(entry.path("nextGate").asText()).append(" |\n");
        }
        JsonNode summary = training.path("summary");
        if (summary.path("totalSegments").asInt(-1) != catalogCounts.size()
            || summary.path("totalCataloguedRules").asInt(-1) != totalRules
            || summary.path("substantiveModules").asInt(-1) != substantive
            || summary.path("inProgress").asInt(-1) != inProgress
            || summary.path("trainedForIntake").asInt(-1) != trained
            || summary.path("segmentsWithOpenBlockers").asInt(-1) != openBlockers) {
            throw new IllegalStateException("Training summary does not match segment entries and catalogs");
        }
        String report = "# ATL105 Segment Training: Current Status\n\n"
            + "Generated from [training-status.json](../../../training-status.json) and the segment rule catalogs. "
            + "Regenerate with `com.coreauth.validator.coverage.GenerateAtl105TrainingReport` from the validator module. "
            + "This is Test Solution knowledge and gate progress, not LLM fine-tuning or execution certification.\n\n"
            + "| Metric | Current |\n|---|---:|\n"
            + "| Segments | " + catalogCounts.size() + " |\n"
            + "| Catalogued rules | " + totalRules + " |\n"
            + "| Substantive knowledge modules | " + substantive + " |\n"
            + "| In progress | " + inProgress + " |\n"
            + "| Trained for independent intake | " + trained + " |\n"
            + "| Segments with explicit blockers | " + openBlockers + " |\n\n"
            + "## Gate Policy\n\n"
            + "A catalogued rule is not an SME-approved BR or an executable test. All required gates in "
            + "[training-status.json](../../../training-status.json) must have evidence and review before a segment is trained for intake. "
            + "Missing AI artifacts and unresolved source or SME decisions remain review-required.\n\n"
            + "## Segment Progress\n\n"
            + "| Segment | Rules | Evidence | Status | Explicit blockers | Next gate |\n"
            + "|---|---:|---|---|---:|---|\n" + rows
            + "\nSee each segment's `segment-*/` KB module for source anchors and its open SME/TBA decisions. "
            + "Historical branch activity is available in repository history; it is not a training-completion measure.\n";
        Files.writeString(kb.resolve("SEGMENT-TRAINING-EXECUTION-REPORT.md"), report);
        System.out.printf("segments=%d rules=%d trained=%d%n", catalogCounts.size(), totalRules, trained);
    }
}