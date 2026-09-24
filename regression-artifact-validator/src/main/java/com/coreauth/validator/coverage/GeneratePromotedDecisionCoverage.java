package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

/** Recalculates decision coverage using promoted SME outcomes only; pending decisions are excluded. */
public final class GeneratePromotedDecisionCoverage {
    private GeneratePromotedDecisionCoverage() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path registerFile = Atl105Paths.testOutput().resolve(Path.of(
                "ai-solution-independent-review", "ai-only-sme-decision-register.json"));
        JsonNode register = mapper.readTree(registerFile.toFile());
        Map<String, Integer> promotedBySegment = new TreeMap<>();
        int promoted = 0;
        int pending = 0;
        int rejected = 0;
        for (JsonNode decision : register.path("decisions")) {
            String status = decision.path("decision").asText();
            if ("PENDING".equals(status)) pending++;
            else if ("REJECT".equals(status)) rejected++;
            else {
                promoted++;
                promotedBySegment.merge(decision.path("segment").asText("UNKNOWN"), 1, Integer::sum);
            }
        }
        ObjectNode result = mapper.createObjectNode();
        result.put("artifact", "promoted-sme-decision-coverage");
        result.put("policy", "Only non-PENDING SME decisions count; this is a decision-register count, not automatic spec coverage. NEW_RULE decisions still require generated and validated BR/TS/TC/TD artifacts before execution readiness.");
        result.put("promotedDecisionCount", promoted);
        result.put("pendingDecisionCount", pending);
        result.put("rejectedDecisionCount", rejected);
        result.set("promotedBySegment", mapper.valueToTree(promotedBySegment));
        Path output = Atl105Paths.testOutput().resolve(Path.of(
                "ai-solution-independent-review", "promoted-sme-decision-coverage.json"));
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), result);
        System.out.printf("promoted=%d pending=%d rejected=%d%n", promoted, pending, rejected);
    }
}
