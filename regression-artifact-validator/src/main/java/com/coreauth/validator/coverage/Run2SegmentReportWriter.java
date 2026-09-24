package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Renders one segment's AI-versus-Test comparison and validation evidence. */
public final class Run2SegmentReportWriter {
    private final ObjectMapper mapper = new ObjectMapper();

    public void write(Path crosswalkFile, Path evidenceFile, Path outputDirectory) throws IOException {
        JsonNode crosswalk = mapper.readTree(crosswalkFile.toFile());
        JsonNode evidence = mapper.readTree(evidenceFile.toFile());
        String segment = evidence.path("segment").asText();
        Files.createDirectories(outputDirectory);
        String markdown = markdown(segment, crosswalk, evidence);
        Files.writeString(outputDirectory.resolve("segment-" + segment + "-run2-review.md"), markdown);
        Files.writeString(outputDirectory.resolve("segment-" + segment + "-run2-review.html"),
                html(segment, crosswalk, evidence));
    }

    private static String markdown(String segment, JsonNode crosswalk, JsonNode evidence) {
        JsonNode assessment = evidence.path("assessment");
        JsonNode payload = evidence.path("payloadBatch");
        JsonNode artifacts = evidence.path("artifacts");
        StringBuilder text = new StringBuilder();
        text.append("# Segment ").append(segment).append(" Run2 AI vs Test Solution Review\n\n");
        text.append("- Composite delivery: `").append(evidence.path("sourceRun").asText()).append("`\n");
        text.append("- Decision: **").append(assessment.path("decision").asText()).append("**\n");
        text.append("- Version resolution: `").append(evidence.path("versionResolutionId").asText()).append("`\n");
        text.append("- Confirmed BR coverage: **").append(assessment.path("confirmedRequirementCoveragePercent").asDouble()).append("%**\n");
        text.append("- Full-chain coverage: **").append(assessment.path("fullChainCoveragePercent").asDouble()).append("%**\n\n");
        text.append("## Coverage\n\n");
        text.append("| Denominator | Confirmed | Review required | Missing | Unmatched AI | Full chain |\n");
        text.append("|---:|---:|---:|---:|---:|---:|\n|")
                .append(assessment.path("coverageDenominator").asInt()).append('|')
                .append(assessment.path("confirmedRequirements").asInt()).append('|')
                .append(assessment.path("reviewRequired").asInt()).append('|')
                .append(assessment.path("missingRequirements").asInt()).append('|')
                .append(assessment.path("unmatchedAiRequirements").asInt()).append('|')
                .append(assessment.path("fullChainRequirements").asInt()).append("|\n\n");
        text.append("## Artifact chain\n\n");
        text.append("| AI BRs | Scenarios | Test cases | Test data |\n|---:|---:|---:|---:|\n|")
                .append(artifacts.path("requirements").asInt()).append('|')
                .append(artifacts.path("scenarios").asInt()).append('|')
                .append(artifacts.path("testCases").asInt()).append('|')
                .append(artifacts.path("testData").asInt()).append("|\n\n");
        text.append("## Payload validation\n\n");
        text.append("| Validator | Linked | Applicable | Valid | Invalid | Unreadable |\n|---|---:|---:|---:|---:|---:|\n|")
                .append(payload.path("validatorImplemented").asBoolean() ? "implemented" : "not implemented").append('|')
                .append(payload.path("linkedPayloadFiles").asInt()).append('|')
                .append(payload.path("applicablePayloadFiles").asInt()).append('|')
                .append(payload.path("validPayloadFiles").asInt()).append('|')
                .append(payload.path("invalidPayloadFiles").asInt()).append('|')
                .append(payload.path("unreadablePayloadFiles").asInt()).append("|\n\n");
        text.append("## AI to Test Solution crosswalk\n\n");
        text.append("| Test rule | Status | AI requirement IDs | Canonical anchor | Reason |\n");
        text.append("|---|---|---|---|---|\n");
        for (JsonNode mapping : crosswalk.path("mappings")) {
            text.append('|').append(cell(mapping.path("testRequirementId").asText())).append('|')
                    .append(cell(mapping.path("matchStatus").asText())).append('|')
                    .append(cell(join(mapping.path("aiRequirementIds")))).append('|')
                    .append(cell(anchor(mapping.path("sourceAnchors").path(0)))).append('|')
                    .append(cell(mapping.path("matchReason").asText())).append("|\n");
        }
        return text.toString();
    }

    private static String html(String segment, JsonNode crosswalk, JsonNode evidence) {
        String markdown = markdown(segment, crosswalk, evidence);
        StringBuilder body = new StringBuilder();
        boolean inTable = false;
        for (String line : markdown.split("\\R")) {
            if (line.startsWith("# ")) body.append("<h1>").append(escape(line.substring(2))).append("</h1>");
            else if (line.startsWith("## ")) body.append("<h2>").append(escape(line.substring(3))).append("</h2>");
            else if (line.startsWith("|")) {
                if (line.matches("\\|[-:|]+\\|")) continue;
                if (!inTable) { body.append("<table>"); inTable = true; }
                body.append("<tr>");
                for (String cell : line.substring(1, line.length() - 1).split("\\|", -1)) {
                    body.append("<td>").append(escape(cell)).append("</td>");
                }
                body.append("</tr>");
            } else {
                if (inTable) { body.append("</table>"); inTable = false; }
                if (line.startsWith("- ")) body.append("<p>").append(escape(line.substring(2))).append("</p>");
            }
        }
        if (inTable) body.append("</table>");
        return "<!doctype html><html><head><meta charset=\"utf-8\"><title>Segment " + escape(segment)
                + " Run2 Review</title><style>body{font-family:Segoe UI,sans-serif;margin:32px;color:#202124}"
                + "table{border-collapse:collapse;width:100%;margin:12px 0 24px}td{border:1px solid #ccc;padding:7px;vertical-align:top}"
                + "tr:first-child{font-weight:700;background:#eef3f8}h1,h2{color:#17324d}</style></head><body>"
                + body + "</body></html>";
    }

    private static String join(JsonNode array) {
        StringBuilder value = new StringBuilder();
        for (JsonNode item : array) {
            if (!value.isEmpty()) value.append(", ");
            value.append(item.asText());
        }
        return value.toString();
    }

    private static String anchor(JsonNode value) {
        if (!value.isObject()) return "";
        return String.join("|", value.path("specification").asText(), value.path("version").asText(),
                value.path("section").asText(), value.path("segment").asText(),
                value.path("element").asText(), value.path("rule").asText());
    }

    private static String cell(String value) { return value.replace("|", "\\|").replace("\n", " "); }
    private static String escape(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}