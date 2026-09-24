package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

/** Generates live SME decision counts from the Test Solution decision register. */
public final class GenerateSmeDecisionSummary {
    private GenerateSmeDecisionSummary() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        JsonNode register = mapper.readTree(reviewRoot.resolve("ai-only-sme-decision-register.json").toFile());
        Map<String, Integer> byDecision = new TreeMap<>();
        Map<String, Integer> bySegment = new TreeMap<>();
        int segment111Pending = 0;
        for (JsonNode decision : register.path("decisions")) {
            String status = decision.path("decision").asText("UNKNOWN");
            String segment = decision.path("segment").asText("UNKNOWN");
            byDecision.merge(status, 1, Integer::sum);
            bySegment.merge(segment, 1, Integer::sum);
            if ("111".equals(segment) && "PENDING".equals(status)) segment111Pending++;
        }
        ObjectNode summary = mapper.createObjectNode();
        summary.put("artifact", "live-sme-decision-summary");
        summary.put("segment111Pending", segment111Pending);
        summary.put("totalDecisions", register.path("decisions").size());
        summary.set("byDecision", mapper.valueToTree(byDecision));
        summary.set("bySegment", mapper.valueToTree(bySegment));
        Path json = reviewRoot.resolve("live-sme-decision-summary.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(json.toFile(), summary);
        Files.writeString(reviewRoot.resolve("live-sme-decision-summary.md"), markdown(summary, byDecision));
        System.out.printf("segment111Pending=%d totalDecisions=%d%n", segment111Pending, register.path("decisions").size());
    }

    private static String markdown(ObjectNode summary, Map<String, Integer> byDecision) {
        StringBuilder text = new StringBuilder("# Live SME Decision Summary\n\n")
                .append("Source: `ai-only-sme-decision-register.json`\n\n")
                .append("- Segment 111 pending: **").append(summary.path("segment111Pending").asInt()).append("**\n")
                .append("- Total decisions: **").append(summary.path("totalDecisions").asInt()).append("**\n\n")
                .append("| Decision | Count |\n|---|---:|\n");
        for (Map.Entry<String, Integer> entry : byDecision.entrySet()) {
            text.append('|').append(entry.getKey()).append('|').append(entry.getValue()).append("|\n");
        }
        return text.toString();
    }
}
