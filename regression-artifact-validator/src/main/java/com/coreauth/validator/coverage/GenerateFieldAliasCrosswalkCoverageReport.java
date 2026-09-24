package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Reports, per trained segment, whether a field-alias crosswalk exists and how much AI evidence backs it. */
public final class GenerateFieldAliasCrosswalkCoverageReport {
    private static final List<String> SEGMENTS = List.of(
            "100", "101", "102", "103", "104", "105", "108", "109", "111", "113");

    private GenerateFieldAliasCrosswalkCoverageReport() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path catalogFile = Atl105Paths.testOutput().resolve(Path.of(
                "ai-solution-independent-review", "run2-ai-element-name-catalog.json"));
        JsonNode catalog = Files.isRegularFile(catalogFile) ? mapper.readTree(catalogFile.toFile()) : mapper.createObjectNode();
        Map<String, Integer> evidenceCountBySegment = new TreeMap<>();
        for (JsonNode segmentNode : catalog.path("segments")) {
            evidenceCountBySegment.put(segmentNode.path("segment").asText(), segmentNode.path("elements").size());
        }

        ObjectNode report = mapper.createObjectNode();
        report.put("artifact", "field-alias-crosswalk-coverage-report");
        report.put("evidenceSource", "run2-ai-element-name-catalog.json");
        ArrayNode rows = report.putArray("segments");
        int segmentsWithCrosswalk = 0;
        int segmentsWithEvidence = 0;

        for (String segment : SEGMENTS) {
            Path crosswalkFile = Atl105Paths.root().resolve(Path.of("contract",
                    "segment-" + segment + "-field-alias-crosswalk.json"));
            boolean hasCrosswalk = Files.isRegularFile(crosswalkFile);
            int aliasCount = 0;
            if (hasCrosswalk) {
                aliasCount = mapper.readTree(crosswalkFile.toFile()).path("aliases").size();
                segmentsWithCrosswalk++;
            }
            int evidenceCount = evidenceCountBySegment.getOrDefault(segment, 0);
            if (evidenceCount > 0) segmentsWithEvidence++;

            ObjectNode row = rows.addObject();
            row.put("segment", segment);
            row.put("hasFieldAliasCrosswalk", hasCrosswalk);
            row.put("confirmedAliasCount", aliasCount);
            row.put("aiElementEvidenceObserved", evidenceCount);
            row.put("status", hasCrosswalk ? "CROSSWALK_BUILT"
                    : evidenceCount > 0 ? "EVIDENCE_AVAILABLE_CROSSWALK_NOT_BUILT" : "NO_AI_EVIDENCE_YET");
        }

        ObjectNode summary = report.putObject("summary");
        summary.put("trainedSegments", SEGMENTS.size());
        summary.put("segmentsWithFieldAliasCrosswalk", segmentsWithCrosswalk);
        summary.put("segmentsWithAiEvidence", segmentsWithEvidence);
        summary.put("segmentsWithNoAiEvidence", SEGMENTS.size() - segmentsWithEvidence);

        Path outputRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("field-alias-crosswalk-coverage-report.json").toFile(), report);
        Files.writeString(outputRoot.resolve("field-alias-crosswalk-coverage-report.md"), markdown(rows, summary));
        System.out.printf("segmentsWithCrosswalk=%d segmentsWithEvidence=%d segmentsWithNoEvidence=%d%n",
                segmentsWithCrosswalk, segmentsWithEvidence, SEGMENTS.size() - segmentsWithEvidence);
    }

    private static String markdown(ArrayNode rows, ObjectNode summary) {
        StringBuilder text = new StringBuilder("# Field-Alias Crosswalk Coverage Report\n\n")
                .append("Evidence source: `run2-ai-element-name-catalog.json` (test_data.key_values, or resolvable AI payload leaf keys as fallback). ")
                .append("A crosswalk is only built when unambiguous AI field-name evidence exists; it is never fabricated.\n\n")
                .append("- Trained segments: **").append(summary.path("trainedSegments").asInt()).append("**\n")
                .append("- Segments with a field-alias crosswalk: **").append(summary.path("segmentsWithFieldAliasCrosswalk").asInt()).append("**\n")
                .append("- Segments with any AI field-name evidence: **").append(summary.path("segmentsWithAiEvidence").asInt()).append("**\n")
                .append("- Segments with no AI field-name evidence in this delivery: **").append(summary.path("segmentsWithNoAiEvidence").asInt()).append("**\n\n")
                .append("| Segment | Crosswalk built | Confirmed aliases | AI evidence entries | Status |\n")
                .append("|---|---|---:|---:|---|\n");
        for (JsonNode row : rows) {
            text.append('|').append(row.path("segment").asText()).append('|')
                    .append(row.path("hasFieldAliasCrosswalk").asBoolean() ? "yes" : "no").append('|')
                    .append(row.path("confirmedAliasCount").asInt()).append('|')
                    .append(row.path("aiElementEvidenceObserved").asInt()).append('|')
                    .append(row.path("status").asText()).append("|\n");
        }
        text.append("\nSegments with `NO_AI_EVIDENCE_YET` cannot get an evidence-based crosswalk from this delivery: every AI test case that resolves to a data file "
                + "already carries `key_values`, and none of those key_values are labeled with these segments' numbers, meaning the AI Solution is not yet producing "
                + "segment-specific field-level test data for them. This should be raised with the AI team separately from Test Solution training.\n");
        return text.toString();
    }
}
