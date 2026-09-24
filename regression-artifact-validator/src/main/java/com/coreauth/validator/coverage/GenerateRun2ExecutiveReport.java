package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Generates weighted Step 5 executive coverage across the ten trained segments. */
public final class GenerateRun2ExecutiveReport {
    private static final List<String> SEGMENTS = List.of(
            "100", "101", "102", "103", "104", "105", "108", "109", "111", "113");

    private GenerateRun2ExecutiveReport() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        Path validationRoot = reviewRoot.resolve("run2-validation");
        Path outputRoot = reviewRoot.resolve("run2-executive-report");
        Files.createDirectories(outputRoot);

        ObjectNode report = mapper.createObjectNode();
        report.put("artifact", "run2-weighted-executive-report");
        report.put("sourceRun", "2026-09-23/Run1+Run2");
        report.put("compositeDeliveryProvenance", "../run1-run2-composite-delivery.json");
        report.put("versionResolutionId", "ATL105-RUN2-SPEC-VERSION-RESOLUTION-001");
        ArrayNode rows = report.putArray("segments");
        int denominator = 0;
        int confirmed = 0;
        int review = 0;
        int missing = 0;
        int fullChain = 0;
        int payloadApplicable = 0;
        int payloadValid = 0;
        int payloadInvalid = 0;
        int executionReady = 0;

        for (String segment : SEGMENTS) {
            JsonNode evidence = mapper.readTree(validationRoot.resolve(
                    "segment-" + segment + "-validation.json").toFile());
            JsonNode assessment = evidence.path("assessment");
            JsonNode payload = evidence.path("payloadBatch");
            ObjectNode row = rows.addObject();
            row.put("segment", segment);
            row.put("decision", assessment.path("decision").asText());
            row.put("denominator", assessment.path("coverageDenominator").asInt());
            row.put("confirmed", assessment.path("confirmedRequirements").asInt());
            row.put("reviewRequired", assessment.path("reviewRequired").asInt());
            row.put("missing", assessment.path("missingRequirements").asInt());
            row.put("fullChain", assessment.path("fullChainRequirements").asInt());
            row.put("confirmedCoveragePercent", assessment.path("confirmedRequirementCoveragePercent").asDouble());
            row.put("fullChainCoveragePercent", assessment.path("fullChainCoveragePercent").asDouble());
            row.put("payloadValidatorImplemented", payload.path("validatorImplemented").asBoolean());
            row.put("payloadApplicable", payload.path("applicablePayloadFiles").asInt());
            row.put("payloadValid", payload.path("validPayloadFiles").asInt());
            row.put("payloadInvalid", payload.path("invalidPayloadFiles").asInt());

            denominator += row.path("denominator").asInt();
            confirmed += row.path("confirmed").asInt();
            review += row.path("reviewRequired").asInt();
            missing += row.path("missing").asInt();
            fullChain += row.path("fullChain").asInt();
            payloadApplicable += row.path("payloadApplicable").asInt();
            payloadValid += row.path("payloadValid").asInt();
            payloadInvalid += row.path("payloadInvalid").asInt();
            if ("EXECUTION_READY".equals(row.path("decision").asText())) executionReady++;
        }

        ObjectNode summary = report.putObject("weightedSummary");
        summary.put("trainedSegments", SEGMENTS.size());
        summary.put("executionReadySegments", executionReady);
        summary.put("coverageDenominator", denominator);
        summary.put("confirmedRequirements", confirmed);
        summary.put("reviewRequired", review);
        summary.put("missingRequirements", missing);
        summary.put("fullChainRequirements", fullChain);
        summary.put("confirmedCoveragePercent", percent(confirmed, denominator));
        summary.put("fullChainCoveragePercent", percent(fullChain, denominator));
        summary.put("payloadApplicableValidationAttempts", payloadApplicable);
        summary.put("payloadValidValidationAttempts", payloadValid);
        summary.put("payloadInvalidValidationAttempts", payloadInvalid);
        summary.put("payloadAttemptPassPercent", percent(payloadValid, payloadApplicable));
        summary.put("calculation", "weighted counts across Test Solution denominators; segment percentages are not averaged");

        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("run2-weighted-executive-report.json").toFile(), report);
        String markdown = markdown(report);
        Files.writeString(outputRoot.resolve("run2-weighted-executive-report.md"), markdown);
        Files.writeString(outputRoot.resolve("run2-weighted-executive-report.html"), html(report));
        System.out.printf("denominator=%d confirmed=%d confirmedCoverage=%.1f fullChain=%d fullChainCoverage=%.1f "
                        + "review=%d missing=%d payloadValid=%d payloadInvalid=%d%n",
                denominator, confirmed, percent(confirmed, denominator), fullChain, percent(fullChain, denominator),
                review, missing, payloadValid, payloadInvalid);
    }

    private static String markdown(JsonNode report) {
        JsonNode summary = report.path("weightedSummary");
        StringBuilder text = new StringBuilder("# Run2 Weighted AI vs Test Solution Executive Report\n\n");
        text.append("- Composite delivery: **").append(report.path("sourceRun").asText()).append("**\n");
        text.append("- Decision: **REVIEW_REQUIRED**\n");
        text.append("- Confirmed weighted BR coverage: **")
                .append(summary.path("confirmedCoveragePercent").asDouble()).append("%**\n");
        text.append("- Weighted full-chain coverage: **")
                .append(summary.path("fullChainCoveragePercent").asDouble()).append("%**\n");
        text.append("- Execution-ready trained segments: **")
                .append(summary.path("executionReadySegments").asInt()).append("/")
                .append(summary.path("trainedSegments").asInt()).append("**\n\n");
        text.append("## Weighted totals\n\n");
        text.append("| Denominator | Confirmed | Review required | Missing | Full chain |\n|---:|---:|---:|---:|---:|\n|")
                .append(summary.path("coverageDenominator").asInt()).append('|')
                .append(summary.path("confirmedRequirements").asInt()).append('|')
                .append(summary.path("reviewRequired").asInt()).append('|')
                .append(summary.path("missingRequirements").asInt()).append('|')
                .append(summary.path("fullChainRequirements").asInt()).append("|\n\n");
        text.append("## Segment summary\n\n");
        text.append("| Segment | Decision | Denominator | Confirmed % | Full-chain % | Payload validator | Applicable | Valid | Invalid |\n");
        text.append("|---|---|---:|---:|---:|---|---:|---:|---:|\n");
        for (JsonNode row : report.path("segments")) {
            text.append('|').append(row.path("segment").asText()).append('|')
                    .append(row.path("decision").asText()).append('|')
                    .append(row.path("denominator").asInt()).append('|')
                    .append(row.path("confirmedCoveragePercent").asDouble()).append('|')
                    .append(row.path("fullChainCoveragePercent").asDouble()).append('|')
                    .append(row.path("payloadValidatorImplemented").asBoolean() ? "implemented" : "missing").append('|')
                    .append(row.path("payloadApplicable").asInt()).append('|')
                    .append(row.path("payloadValid").asInt()).append('|')
                    .append(row.path("payloadInvalid").asInt()).append("|\n");
        }
        text.append("\nPayload totals are segment-validation attempts; one multi-segment payload may contribute to multiple rows.\n");
        return text.toString();
    }

    private static String html(JsonNode report) {
        String md = markdown(report);
        StringBuilder body = new StringBuilder();
        boolean table = false;
        for (String line : md.split("\\R")) {
            if (line.startsWith("# ")) body.append("<h1>").append(escape(line.substring(2))).append("</h1>");
            else if (line.startsWith("## ")) body.append("<h2>").append(escape(line.substring(3))).append("</h2>");
            else if (line.startsWith("|")) {
                if (line.matches("\\|[-:|]+\\|")) continue;
                if (!table) { body.append("<table>"); table = true; }
                body.append("<tr>");
                for (String cell : line.substring(1, line.length() - 1).split("\\|", -1))
                    body.append("<td>").append(escape(cell)).append("</td>");
                body.append("</tr>");
            } else {
                if (table) { body.append("</table>"); table = false; }
                if (!line.isBlank()) body.append("<p>").append(escape(line.replaceFirst("^- ", ""))).append("</p>");
            }
        }
        if (table) body.append("</table>");
        return "<!doctype html><html><head><meta charset=\"utf-8\"><title>Run2 Executive Report</title>"
                + "<style>body{font-family:Segoe UI,sans-serif;margin:32px;color:#202124}table{border-collapse:collapse;width:100%;margin:12px 0 24px}"
                + "td{border:1px solid #ccc;padding:8px}tr:first-child{font-weight:700;background:#eaf0f5}h1,h2{color:#17324d}</style></head><body>"
                + body + "</body></html>";
    }

    private static double percent(int numerator, int denominator) {
        return denominator == 0 ? 0.0 : Math.round(1000.0 * numerator / denominator) / 10.0;
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}