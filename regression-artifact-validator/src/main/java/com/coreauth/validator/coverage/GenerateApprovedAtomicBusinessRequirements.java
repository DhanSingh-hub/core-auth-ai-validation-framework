package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

/** Materializes Test Solution BRs only from reviewed NEW_RULE semantic decisions. */
public final class GenerateApprovedAtomicBusinessRequirements {
    private GenerateApprovedAtomicBusinessRequirements() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path outputRoot = Atl105Paths.testOutput().resolve("test-solution-independent-review");
        Path registerFile = outputRoot.resolve("semantic-br-decision-register.json");
        JsonNode register = mapper.readTree(registerFile.toFile());
        ArrayNode requirements = mapper.createArrayNode();
        Set<String> subjects = new LinkedHashSet<>();
        int pending = 0;
        int rejected = 0;
        int duplicates = 0;
        int confirmed = 0;

        for (JsonNode decision : register.path("decisions")) {
            String status = decision.path("decision").asText();
            if ("PENDING".equals(status)) {
                pending++;
                continue;
            }
            if ("REJECT".equals(status)) {
                rejected++;
                continue;
            }
            if ("DUPLICATE".equals(status)) {
                duplicates++;
                continue;
            }
            if ("CONFIRMED_MATCH".equals(status)) {
                confirmed++;
                continue;
            }
            if (!"NEW_RULE".equals(status)) continue;
            String subjectId = required(decision, "subjectId");
            if (!subjects.add(subjectId)) throw new IllegalStateException("Duplicate promoted subject: " + subjectId);
            JsonNode anchor = canonicalAnchorEvidence(decision.path("evidence"));
            String segment = required(decision, "segment");
            ObjectNode br = requirements.addObject();
            br.put("id", "BR-SME-" + safe(subjectId));
            br.put("title", "Independently derived Test Solution rule for approved AI crosswalk candidate " + subjectId);
            br.put("status", "APPROVED_FOR_CHAIN_DERIVATION");
            br.put("source", "ATL105_SPECIFICATION_AND_SME_DECISION");
            br.put("segment", segment);
            br.put("decisionId", required(decision, "decisionId"));
            br.set("sourceAnchors", arrayWith(anchor, mapper));
            br.put("chainStatus", "TS_TC_TD_DERIVATION_REQUIRED");
        }

        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "atl105-approved-atomic-test-solution-business-requirements");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("aiArtifactsIncluded", false);
        output.put("sourceDecisionRegister", "semantic-br-decision-register.json");
        output.put("policy", "Only reviewed NEW_RULE decisions with canonical ATL105 source-anchor evidence create BRs. PENDING, DUPLICATE, REJECT, and CONFIRMED_MATCH decisions do not create new BRs.");
        output.set("businessRequirements", requirements);
        ObjectNode summary = output.putObject("summary");
        summary.put("approvedNewRuleDecisions", requirements.size());
        summary.put("pendingDecisions", pending);
        summary.put("confirmedMatchesNotNewRules", confirmed);
        summary.put("duplicatesNotNewRules", duplicates);
        summary.put("rejectedNotNewRules", rejected);
        summary.put("executionReady", false);
        summary.put("nextGate", "TS_TC_TD_DERIVATION_AND_CHAIN_VALIDATION");

        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("approved-atomic-test-solution-business-requirements.json").toFile(), output);
        Files.writeString(outputRoot.resolve("approved-atomic-test-solution-business-requirements.md"), markdown(summary));
        System.out.printf("approvedNewRules=%d pending=%d confirmedMatches=%d duplicates=%d rejected=%d%n",
                requirements.size(), pending, confirmed, duplicates, rejected);
    }

    private static JsonNode canonicalAnchorEvidence(JsonNode evidence) {
        for (JsonNode item : evidence) {
            JsonNode anchor = item.path("sourceAnchor");
            if (anchor.isObject() && complete(anchor)) return anchor;
        }
        throw new IllegalStateException("NEW_RULE requires complete canonical sourceAnchor evidence");
    }

    private static boolean complete(JsonNode anchor) {
        return !anchor.path("specification").asText().isBlank()
                && !anchor.path("version").asText().isBlank()
                && !anchor.path("section").asText().isBlank()
                && !anchor.path("segment").asText().isBlank()
                && !anchor.path("rule").asText().isBlank();
    }

    private static ArrayNode arrayWith(JsonNode value, ObjectMapper mapper) {
        ArrayNode output = mapper.createArrayNode();
        output.add(value);
        return output;
    }

    private static String required(JsonNode node, String field) {
        String value = node.path(field).asText("");
        if (value.isBlank()) throw new IllegalStateException("Missing required field: " + field);
        return value;
    }

    private static String safe(String value) {
        return value.replaceAll("[^A-Za-z0-9]+", "-");
    }

    private static String markdown(JsonNode summary) {
        return "# Approved Atomic Test Solution BRs\n\n"
                + "Only reviewed `NEW_RULE` decisions with canonical ATL105 source evidence are materialized here.\n\n"
                + "| Metric | Count |\n|---|---:|\n"
                + "| Approved new rules | " + summary.path("approvedNewRuleDecisions").asInt() + " |\n"
                + "| Pending decisions | " + summary.path("pendingDecisions").asInt() + " |\n"
                + "| Confirmed matches, not new rules | " + summary.path("confirmedMatchesNotNewRules").asInt() + " |\n"
                + "| Duplicates, not new rules | " + summary.path("duplicatesNotNewRules").asInt() + " |\n"
                + "| Rejected, not new rules | " + summary.path("rejectedNotNewRules").asInt() + " |\n\n"
                + "Every approved BR still requires independent TS, TC, TD derivation and chain validation.\n";
    }
}