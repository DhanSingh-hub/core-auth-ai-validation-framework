package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;

/** Filters an existing confirmed traceability match report down to one segment, leaving the source file untouched. */
public final class GenerateSegmentConfirmedTraceabilityMatchReport {

    private GenerateSegmentConfirmedTraceabilityMatchReport() { }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) throw new IllegalArgumentException("Usage: <segment> [sourceJsonFile]");
        String segment = args[0];
        String prefix = "SEG" + segment + "-";

        ObjectMapper mapper = new ObjectMapper();
        Path outputRoot = Atl105Paths.testOutput().resolve(
                Path.of("ai-solution-independent-review", "four-level-matches"));
        Path sourceFile = args.length > 1
                ? Path.of(args[1]) : outputRoot.resolve("confirmed-traceability-matches.json");
        JsonNode full = mapper.readTree(sourceFile.toFile());

        ObjectNode segmentReport = mapper.createObjectNode();
        segmentReport.put("artifact", "confirmed-ai-test-solution-traceability-match-report-segment-" + segment);
        segmentReport.put("deliveryId", full.path("deliveryId").asText());
        segmentReport.put("scope", "CONFIRMED business-requirement matches for Segment " + segment
                + " only, filtered from " + sourceFile.getFileName() + ".");
        ArrayNode filtered = segmentReport.putArray("businessRequirements");
        for (JsonNode br : full.path("businessRequirements")) {
            if (br.path("testRequirementId").asText().startsWith(prefix)) filtered.add(br);
        }
        segmentReport.put("confirmedBusinessRequirements", filtered.size());

        String suffix = "-segment-" + segment;
        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("confirmed-traceability-matches" + suffix + ".json").toFile(), segmentReport);
        Files.writeString(outputRoot.resolve("confirmed-traceability-matches" + suffix + ".md"),
                markdown(segment, filtered));
        System.out.printf("segment=%s confirmedBRs=%d%n", segment, filtered.size());
    }

    private static String markdown(String segment, ArrayNode confirmed) {
        StringBuilder text = new StringBuilder("# Confirmed BR to TS/TC/TD Side-by-Side Comparison - Segment ")
                .append(segment).append("\n\n")
                .append("Only CONFIRMED business-requirement matches for Segment ").append(segment)
                .append(" are included: evidence-backed Test Solution decisions, not candidate or review-required matches.\n\n")
                .append("- Confirmed BR mappings: **").append(confirmed.size()).append("**\n\n");
        for (JsonNode br : confirmed) {
            text.append("## ").append(br.path("testRequirementId").asText()).append(" - CONFIRMED\n\n")
                    .append("**Reason:** ").append(br.path("matchReason").asText()).append("\n\n")
                    .append("| Level | AI count | Test count |\n|---|---:|---:|\n")
                    .append("| BR |").append(br.path("aiBusinessRequirements").size()).append('|')
                    .append(br.path("testBusinessRequirements").size()).append("|\n")
                    .append("| TS |").append(br.path("aiTestScenariosMatchCount").asInt()).append('|')
                    .append(br.path("testScenariosMatchCount").asInt()).append("|\n")
                    .append("| TC |").append(br.path("aiTestCasesMatchCount").asInt()).append('|')
                    .append(br.path("testCasesMatchCount").asInt()).append("|\n")
                    .append("| TD |").append(br.path("aiTestDataMatchCount").asInt()).append('|')
                    .append(br.path("testDataMatchCount").asInt()).append("|\n\n");
            sideBySideTable(text, "BR", br.path("brSideBySide"));
            sideBySideTable(text, "TS", br.path("tsSideBySide"));
            sideBySideTable(text, "TC", br.path("tcSideBySide"));
            sideBySideTable(text, "TD", br.path("tdSideBySide"));
        }
        return text.toString();
    }

    private static void sideBySideTable(StringBuilder text, String level, JsonNode pairs) {
        text.append("### ").append(level).append(" side-by-side (").append(pairs.size()).append(")\n\n");
        if (pairs.isEmpty()) {
            text.append("_No AI or Test Solution artifacts at this level._\n\n");
            return;
        }
        text.append("| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |\n|---|---|---|---|---|---|\n");
        for (JsonNode pair : pairs) {
            text.append('|').append(pair.path("aiId").asText()).append('|')
                    .append(cell(pair.path("aiDetail").asText())).append('|')
                    .append(pair.path("status").asText()).append('|')
                    .append(pair.path("testId").asText()).append('|')
                    .append(cell(pair.path("testDetail").asText())).append('|')
                    .append(cell(pair.path("reason").asText())).append("|\n");
        }
        text.append('\n');
    }

    private static String cell(String value) {
        return value.replace("|", "\\|").replace("\n", " ");
    }
}
