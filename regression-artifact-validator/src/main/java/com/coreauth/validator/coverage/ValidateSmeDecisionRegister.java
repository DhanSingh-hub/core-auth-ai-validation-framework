package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

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
        if (!"test-solution-sme-decision-register".equals(root.path("artifact").asText())) {
            throw new IllegalStateException("Unexpected register artifact name");
        }
        if (!root.path("decisions").isArray()) throw new IllegalStateException("decisions must be an array");

        Set<String> decisionIds = new HashSet<>();
        int pending = 0;
        int promoted = 0;
        for (JsonNode decision : root.path("decisions")) {
            String decisionId = required(decision, "decisionId");
            if (!decisionIds.add(decisionId)) throw new IllegalStateException("Duplicate decisionId: " + decisionId);
            String subjectType = required(decision, "subjectType");
            if (!SUBJECT_TYPES.contains(subjectType)) throw new IllegalStateException("Invalid subjectType: " + subjectType);
            String status = required(decision, "decision");
            if (!STATUSES.contains(status)) throw new IllegalStateException("Invalid decision: " + status);
            required(decision, "subjectId");
            required(decision, "segment");
            required(decision, "rationale");
            if ("PENDING".equals(status)) {
                if (!decision.path("reviewer").asText("UNREVIEWED").equals("UNREVIEWED")) {
                    throw new IllegalStateException(decisionId + " pending decision reviewer must be UNREVIEWED");
                }
                pending++;
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
            if (("CONFIRMED_MATCH".equals(status) || "NEW_RULE".equals(status))
                    && !hasCanonicalAnchorEvidence(decision.path("evidence"))) {
                throw new IllegalStateException(decisionId + " requires canonical source-anchor evidence for " + status);
            }
            promoted++;
        }

        System.out.printf("decisions=%d pending=%d promoted=%d status=VALID%n",
                root.path("decisions").size(), pending, promoted);
    }

    private static boolean hasCanonicalAnchorEvidence(JsonNode evidence) {
        for (JsonNode item : evidence) {
            JsonNode anchor = item.path("sourceAnchor");
            if (anchor.isObject()
                    && !anchor.path("specification").asText().isBlank()
                    && !anchor.path("version").asText().isBlank()
                    && !anchor.path("section").asText().isBlank()
                    && !anchor.path("segment").asText().isBlank()
                    && !anchor.path("rule").asText().isBlank()) return true;
        }
        return false;
    }

    private static String required(JsonNode node, String field) {
        String value = node.path(field).asText("");
        if (value.isBlank()) throw new IllegalStateException("Missing required field: " + field);
        return value;
    }
}
