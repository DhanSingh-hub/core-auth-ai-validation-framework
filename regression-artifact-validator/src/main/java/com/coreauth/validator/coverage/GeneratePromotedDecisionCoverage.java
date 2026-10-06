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
        ObjectNode result = summarize(register, mapper);
        Path output = Atl105Paths.testOutput().resolve(Path.of(
                "ai-solution-independent-review", "promoted-sme-decision-coverage.json"));
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), result);
        System.out.printf("effective=%d promoted=%d pending=%d reviewRequired=%d rejected=%d%n",
                result.path("effectiveDecisionCount").asInt(), result.path("promotedDecisionCount").asInt(),
                result.path("pendingDecisionCount").asInt(), result.path("reviewRequiredDecisionCount").asInt(),
                result.path("rejectedDecisionCount").asInt());
    }

    static ObjectNode summarize(JsonNode register, ObjectMapper mapper) {
        ValidateSmeDecisionRegister.validate(register);
        Map<String, Integer> promotedBySegment = new TreeMap<>();
        int newRules = 0;
        int confirmedMatches = 0;
        int duplicates = 0;
        int pending = 0;
        int reviewRequired = 0;
        int rejected = 0;
        Map<String, JsonNode> effective = SmeDecisionHistory.latestBySubject(register);
        for (JsonNode decision : effective.values()) {
            String status = decision.path("decision").asText();
            if ("PENDING".equals(status)) pending++;
            else if ("REVIEW_REQUIRED".equals(status)) reviewRequired++;
            else if ("REJECT".equals(status)) rejected++;
            else if ("DUPLICATE".equals(status)) duplicates++;
            else if ("NEW_RULE".equals(status)) {
                newRules++;
                promotedBySegment.merge(decision.path("segment").asText("UNKNOWN"), 1, Integer::sum);
            } else if ("CONFIRMED_MATCH".equals(status)) confirmedMatches++;
        }
        ObjectNode result = mapper.createObjectNode();
        result.put("artifact", "promoted-sme-decision-coverage");
        result.put("policy", "Only the effective latest decision per subject is counted. NEW_RULE creates a reviewer-authored BR candidate; CONFIRMED_MATCH maps to an existing rule. Neither is execution-ready until BR/TS/TC/TD evidence is validated. DUPLICATE and REJECT receive no coverage credit.");
        result.put("effectiveDecisionCount", effective.size());
        result.put("promotedDecisionCount", newRules + confirmedMatches);
        result.put("newRuleDecisionCount", newRules);
        result.put("confirmedMatchDecisionCount", confirmedMatches);
        result.put("duplicateDecisionCount", duplicates);
        result.put("pendingDecisionCount", pending);
        result.put("reviewRequiredDecisionCount", reviewRequired);
        result.put("rejectedDecisionCount", rejected);
        result.set("promotedBySegment", mapper.valueToTree(promotedBySegment));
        result.put("executionReady", false);
        return result;
    }
}
