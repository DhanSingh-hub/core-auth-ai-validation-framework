package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Creates pending semantic review records for every AI-to-Test crosswalk candidate. */
public final class GenerateSemanticBrReviewQueue {
    private GenerateSemanticBrReviewQueue() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path outputRoot = Atl105Paths.testOutput().resolve("test-solution-independent-review");
        JsonNode matrix = mapper.readTree(outputRoot.resolve("ai-test-many-to-many-coverage-matrix.json").toFile());
        ArrayNode reviewQueue = mapper.createArrayNode();
        ArrayNode decisions = mapper.createArrayNode();
        Map<String, Integer> byStatus = new TreeMap<>();
        Map<String, Integer> bySegment = new TreeMap<>();

        for (JsonNode entry : matrix.path("aiToTest")) {
            String aiId = entry.path("aiRequirementId").asText();
            String segment = entry.path("aiSegment").asText("UNMAPPED");
            String status = entry.path("mappingStatus").asText();
            ObjectNode review = entry.deepCopy();
            review.put("reviewStatus", "PENDING");
            review.set("allowedDecisions", array(List.of("CONFIRMED_MATCH", "DUPLICATE", "NEW_RULE", "REJECT", "REVIEW_REQUIRED"), mapper));
            review.set("reviewQuestions", questions(status, mapper));
            reviewQueue.add(review);
            byStatus.merge(status, 1, Integer::sum);
            bySegment.merge(segment, 1, Integer::sum);

            ObjectNode decision = decisions.addObject();
            decision.put("decisionId", "AI-MATRIX-" + safe(aiId));
            decision.put("subjectType", "AI_TO_TEST_CROSSWALK");
            decision.put("subjectId", aiId);
            decision.put("segment", segment);
            decision.put("decision", "PENDING");
            decision.put("reviewer", "UNREVIEWED");
            decision.put("reviewedAt", Instant.now().toString());
            decision.put("rationale", "Pending semantic source review. This record does not count toward Test Solution coverage and does not create a Test Solution BR.");
            ArrayNode evidence = decision.putArray("evidence");
            ObjectNode aiEvidence = evidence.addObject();
            aiEvidence.put("type", "AI_CROSSWALK_RECORD");
            aiEvidence.put("path", "test-output/test-solution-independent-review/ai-test-many-to-many-coverage-matrix.json");
            aiEvidence.put("aiRequirementId", aiId);
            aiEvidence.put("mappingStatus", status);
            aiEvidence.put("statement", entry.path("statement").asText());
            aiEvidence.put("sourceRuleId", entry.path("sourceRuleId").asText());
            aiEvidence.put("sourcePage", entry.path("sourcePage").asInt(-1));
            aiEvidence.set("candidateTestRules", entry.path("testRuleCandidates"));
            ObjectNode action = evidence.addObject();
            action.put("type", "SEMANTIC_REVIEW_ACTION_REQUIRED");
            action.put("action", "Compare the AI statement to ATL105 source evidence and candidate Test Rules; then record one allowed decision.");
        }

        ObjectNode queue = mapper.createObjectNode();
        queue.put("artifact", "atl105-semantic-br-review-queue");
        queue.put("specification", "ATL105");
        queue.put("specificationVersion", "2026-3");
        queue.put("authority", "TEST_SOLUTION_REVIEW");
        queue.put("aiArtifactsIncluded", true);
        queue.put("sourceMatrix", "ai-test-many-to-many-coverage-matrix.json");
        queue.put("policy", "All entries remain PENDING until source-backed SME/TBA review. No pending entry counts as coverage or creates a BR.");
        queue.set("statusCounts", counts(byStatus, mapper));
        queue.set("segmentCounts", counts(bySegment, mapper));
        queue.put("reviewItemCount", reviewQueue.size());
        queue.set("reviewItems", reviewQueue);
        ObjectNode nextGate = queue.putObject("nextGate");
        nextGate.put("name", "SOURCE_BACKED_BR_DECISION");
        nextGate.put("requiredEvidence", "Canonical ATL105 source anchor plus semantic rationale and reviewer identity");

        ObjectNode register = mapper.createObjectNode();
        register.put("artifact", "test-solution-sme-decision-register");
        register.put("version", "1.0");
        register.put("authority", "TEST_SOLUTION_REVIEW");
        register.put("policy", "Append-only semantic BR decisions for AI-to-Test crosswalk review. Pending decisions never count toward coverage.");
        register.set("decisionStatuses", array(List.of("PENDING", "NEW_RULE", "DUPLICATE", "REJECT", "CONFIRMED_MATCH", "REVIEW_REQUIRED"), mapper));
        register.set("subjectTypes", array(List.of("AI_TO_TEST_CROSSWALK"), mapper));
        register.set("decisions", decisions);

        mapper.writerWithDefaultPrettyPrinter().writeValue(outputRoot.resolve("semantic-br-review-queue.json").toFile(), queue);
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputRoot.resolve("semantic-br-decision-register.json").toFile(), register);
        Files.writeString(outputRoot.resolve("semantic-br-review-queue.md"), markdown(queue));
        System.out.printf("reviewItems=%d pendingDecisions=%d exact=%d composite=%d missing=%d%n",
                reviewQueue.size(), decisions.size(), byStatus.getOrDefault("REVIEW_REQUIRED_EXACT_CANDIDATE", 0),
                byStatus.getOrDefault("REVIEW_REQUIRED_COMPOSITE_OR_AMBIGUOUS", 0),
                byStatus.getOrDefault("MISSING_TEST_RULE_CANDIDATE", 0));
    }

    private static ArrayNode questions(String status, ObjectMapper mapper) {
        List<String> values = new ArrayList<>();
        values.add("Does the AI statement express a normative ATL105 behavior at the cited source location?");
        values.add("Is the candidate Test Rule semantically equivalent, only partial, or unrelated?");
        if ("MISSING_TEST_RULE_CANDIDATE".equals(status)) {
            values.add("If valid, should this become a new independently derived Test Solution BR?");
        } else if ("REVIEW_REQUIRED_COMPOSITE_OR_AMBIGUOUS".equals(status)) {
            values.add("Should the source behavior be split into multiple atomic Test Solution BRs?");
        } else {
            values.add("Can this AI BR be confirmed as a mapping without duplicating the existing Test Rule?");
        }
        return array(values, mapper);
    }

    private static String safe(String value) {
        return value.replaceAll("[^A-Za-z0-9]+", "-");
    }

    private static ObjectNode counts(Map<String, Integer> values, ObjectMapper mapper) {
        ObjectNode output = mapper.createObjectNode();
        values.forEach(output::put);
        return output;
    }

    private static ArrayNode array(List<String> values, ObjectMapper mapper) {
        ArrayNode output = mapper.createArrayNode();
        values.forEach(output::add);
        return output;
    }

    private static String markdown(JsonNode queue) {
        JsonNode counts = queue.path("statusCounts");
        return "# Semantic BR Review Queue\n\n"
                + "Every AI-to-Test mapping remains pending until source-backed semantic review. Pending items do not count as coverage and do not create Test Solution BRs.\n\n"
                + "| Mapping status | Review items |\n|---|---:|\n"
                + "| Exact candidate | " + counts.path("REVIEW_REQUIRED_EXACT_CANDIDATE").asInt() + " |\n"
                + "| Composite or ambiguous | " + counts.path("REVIEW_REQUIRED_COMPOSITE_OR_AMBIGUOUS").asInt() + " |\n"
                + "| Missing Test Rule candidate | " + counts.path("MISSING_TEST_RULE_CANDIDATE").asInt() + " |\n\n"
                + "Allowed decisions: `CONFIRMED_MATCH`, `DUPLICATE`, `NEW_RULE`, `REJECT`, or `REVIEW_REQUIRED`.\n";
    }
}