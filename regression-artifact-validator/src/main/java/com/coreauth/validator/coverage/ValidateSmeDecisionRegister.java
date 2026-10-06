package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/** Validates the append-only Test Solution SME decision register and its promotion safeguards. */
public final class ValidateSmeDecisionRegister {
    private static final Set<String> STATUSES = Set.of(
            "PENDING", "NEW_RULE", "DUPLICATE", "REJECT", "CONFIRMED_MATCH", "REVIEW_REQUIRED");
    private static final Set<String> SUBJECT_TYPES = Set.of(
            "AI_ONLY_BR", "DRAFT_TEST_RULE", "AI_TO_TEST_CROSSWALK", "TRACEABILITY_CHAIN");

    private ValidateSmeDecisionRegister() { }

    public static void main(String[] args) throws Exception {
        Path register = args.length == 0
                ? Atl105Paths.testOutput().resolve(Path.of("ai-solution-independent-review",
                        "ai-only-sme-decision-register.json"))
                : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(register.toFile());
        Map<String, JsonNode> effective = validate(root);
        Map<String, Integer> effectiveStatuses = new java.util.TreeMap<>();
        for (JsonNode decision : effective.values()) {
            effectiveStatuses.merge(decision.path("decision").asText(), 1, Integer::sum);
        }
        System.out.printf("events=%d subjects=%d effectiveStatuses=%s status=VALID%n",
            root.path("decisions").size(), effective.size(), effectiveStatuses);
        }

        static Map<String, JsonNode> validate(JsonNode root) {
        if (!"test-solution-sme-decision-register".equals(root.path("artifact").asText())) {
            throw new IllegalStateException("Unexpected register artifact name");
        }
        if (!root.path("decisions").isArray()) throw new IllegalStateException("decisions must be an array");

        Map<String, JsonNode> effective = SmeDecisionHistory.latestBySubject(root);
        Set<String> declaredTypes = new HashSet<>();
        root.path("subjectTypes").forEach(type -> declaredTypes.add(type.asText()));
        Set<String> decisionIds = new HashSet<>();
        Set<String> knownRuleIds = null;
        for (JsonNode decision : root.path("decisions")) {
            String decisionId = required(decision, "decisionId");
            if (!decisionIds.add(decisionId)) throw new IllegalStateException("Duplicate decisionId: " + decisionId);
            String subjectType = required(decision, "subjectType");
            if (!SUBJECT_TYPES.contains(subjectType)) throw new IllegalStateException("Invalid subjectType: " + subjectType);
            if (!declaredTypes.isEmpty() && !declaredTypes.contains(subjectType)) {
                throw new IllegalStateException("subjectType is not declared by this register: " + subjectType);
            }
            String status = required(decision, "decision");
            if (!STATUSES.contains(status)) throw new IllegalStateException("Invalid decision: " + status);
            required(decision, "subjectId");
            required(decision, "segment");
            required(decision, "rationale");
            if ("PENDING".equals(status)) {
                if (!decision.path("reviewer").asText("UNREVIEWED").equals("UNREVIEWED")) {
                    throw new IllegalStateException(decisionId + " pending decision reviewer must be UNREVIEWED");
                }
                continue;
            }
            required(decision, "reviewer");
            required(decision, "reviewedAt");
            if (!decision.path("evidence").isArray() || decision.path("evidence").isEmpty()) {
                throw new IllegalStateException(decisionId + " requires evidence[]");
            }
            if ("UNREVIEWED".equals(decision.path("reviewer").asText())) {
                throw new IllegalStateException(decisionId + " cannot be promoted with reviewer UNREVIEWED");
            }
            if ("NEW_RULE".equals(status)) {
                if (!hasCanonicalAnchorEvidence(decision.path("evidence"), decision.path("segment").asText())) {
                    throw new IllegalStateException(decisionId + " requires canonical source-anchor evidence for NEW_RULE");
                }
                JsonNode approvedRule = decision.path("approvedRule");
                if (approvedRule.path("title").asText().isBlank()
                        || approvedRule.path("requirement").asText().isBlank()
                        || !"ATL105".equals(approvedRule.path("source").asText())
                        || !approvedRule.path("independentOfAiWording").asBoolean()) {
                    throw new IllegalStateException(decisionId + " requires reviewer-authored ATL105 approvedRule content independent of AI wording");
                }
            } else if ("CONFIRMED_MATCH".equals(status)) {
                if (!hasCanonicalAnchorEvidence(decision.path("evidence"), decision.path("segment").asText())) {
                    throw new IllegalStateException(decisionId + " requires canonical source-anchor evidence for CONFIRMED_MATCH");
                }
                String matchedRuleId = required(decision, "matchedTestRuleId");
                if (knownRuleIds == null) knownRuleIds = loadRuleIds();
                if (!knownRuleIds.contains(matchedRuleId)) {
                    throw new IllegalStateException(decisionId + " matches unknown Test Solution rule " + matchedRuleId);
                }
            } else if ("DUPLICATE".equals(status)) {
                String duplicateRuleId = required(decision, "duplicateOfRuleId");
                if (knownRuleIds == null) knownRuleIds = loadRuleIds();
                if (!knownRuleIds.contains(duplicateRuleId)) {
                    throw new IllegalStateException(decisionId + " duplicates unknown Test Solution rule " + duplicateRuleId);
                }
            }
        }

        return effective;
    }

    private static Set<String> loadRuleIds() {
        Set<String> ruleIds = new LinkedHashSet<>();
        Path knowledgeBase = Atl105Paths.docs().resolve(Path.of("specs", "kb"));
        ObjectMapper mapper = new ObjectMapper();
        try (Stream<Path> files = Files.walk(knowledgeBase)) {
            for (Path file : files.filter(path -> path.getFileName().toString().matches("segment-.*-rule-catalog\\.json")).toList()) {
                for (JsonNode rule : mapper.readTree(file.toFile()).path("rules")) {
                    String id = rule.path("ruleId").asText("");
                    if (!id.isBlank()) ruleIds.add(id);
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to validate Test Solution rule IDs: " + exception.getMessage(), exception);
        }
        return ruleIds;
    }

    private static boolean hasCanonicalAnchorEvidence(JsonNode evidence, String expectedSegment) {
        for (JsonNode item : evidence) {
            JsonNode anchor = item.path("sourceAnchor");
            if (!anchor.isObject() || !"ATL105".equals(anchor.path("specification").asText())
                    || !"2026-3".equals(anchor.path("version").asText())
                    || anchor.path("section").asText().isBlank() || anchor.path("rule").asText().isBlank()) continue;
            for (String segment : anchor.path("segment").asText("").split("[,|]")) {
                if (expectedSegment.equals(segment.trim())) return true;
            }
        }
        return false;
    }

    private static String required(JsonNode node, String field) {
        String value = node.path(field).asText("");
        if (value.isBlank()) throw new IllegalStateException("Missing required field: " + field);
        return value;
    }
}
