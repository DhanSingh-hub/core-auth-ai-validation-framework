package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;

/** Filters the four-level match report to CONFIRMED business requirements only. */
public final class GenerateConfirmedTraceabilityMatchReport {

    private GenerateConfirmedTraceabilityMatchReport() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path outputRoot = Atl105Paths.testOutput().resolve(
                Path.of("ai-solution-independent-review", "four-level-matches"));
        Path sourceFile = args.length == 0
                ? outputRoot.resolve("four-level-traceability-matches.json") : Path.of(args[0]);
        JsonNode full = mapper.readTree(sourceFile.toFile());

        ObjectNode confirmedReport = mapper.createObjectNode();
        confirmedReport.put("artifact", "confirmed-ai-test-solution-traceability-match-report");
        confirmedReport.put("deliveryId", full.path("deliveryId").asText());
        confirmedReport.put("scope", "Only CONFIRMED business-requirement matches, with their TS, TC, and TD side-by-side comparison.");
        ArrayNode confirmed = confirmedReport.putArray("businessRequirements");
        for (JsonNode br : full.path("businessRequirements")) {
            if ("CONFIRMED".equals(br.path("matchStatus").asText())) confirmed.add(br);
        }
        confirmedReport.put("confirmedBusinessRequirements", confirmed.size());

        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("confirmed-traceability-matches.json").toFile(), confirmedReport);
        Files.writeString(outputRoot.resolve("confirmed-traceability-matches.md"), markdown(confirmed));
        System.out.printf("confirmedBRs=%d%n", confirmed.size());
    }

    private static String markdown(ArrayNode confirmed) {
        StringBuilder text = new StringBuilder("# Confirmed BR to TS/TC/TD Side-by-Side Comparison\n\n")
                .append("Only CONFIRMED business-requirement matches are included: evidence-backed Test Solution decisions, ")
                .append("not candidate or review-required matches.\n\n")
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
