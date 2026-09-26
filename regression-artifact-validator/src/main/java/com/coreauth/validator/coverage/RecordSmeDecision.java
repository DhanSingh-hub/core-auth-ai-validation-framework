package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/** Appends one reviewed SME decision to the Test Solution-owned decision register. */
public final class RecordSmeDecision {
    private static final Set<String> DECISIONS = Set.of(
            "NEW_RULE", "DUPLICATE", "REJECT", "REVIEW_REQUIRED", "CONFIRMED_MATCH");

    private RecordSmeDecision() { }

    public static void main(String[] args) throws Exception {
        if (args.length < 8) {
            throw new IllegalArgumentException("Usage: <subjectId> <segment> <decision> <reviewer> <rationale> <specSection> <specElement> <specRule>");
        }
        String subjectId = args[0];
        String segment = args[1];
        String decision = args[2];
        String reviewer = args[3];
        String rationale = args[4];
        String section = args[5];
        String element = args[6];
        String rule = args[7];
        if (!DECISIONS.contains(decision)) throw new IllegalArgumentException("Unsupported decision: " + decision);

        ObjectMapper mapper = new ObjectMapper();
        Path registerFile = Atl105Paths.testOutput().resolve(Path.of(
                "ai-solution-independent-review", "ai-only-sme-decision-register.json"));
        ObjectNode register = (ObjectNode) mapper.readTree(registerFile.toFile());
        ArrayNode decisions = (ArrayNode) register.withArray("decisions");
        Set<String> subjects = new HashSet<>();
        for (JsonNode existing : decisions) subjects.add(existing.path("subjectId").asText());
        if (subjects.contains(subjectId)) throw new IllegalArgumentException("Decision already exists for subjectId: " + subjectId);

        ObjectNode entry = decisions.addObject();
        entry.put("decisionId", "SME-" + segment + "-" + subjectId.replaceAll("[^A-Za-z0-9]+", "-"));
        entry.put("subjectType", "AI_ONLY_BR");
        entry.put("subjectId", subjectId);
        entry.put("segment", segment);
        entry.put("decision", decision);
        entry.put("reviewer", reviewer);
        entry.put("reviewedAt", Instant.now().toString());
        entry.put("rationale", rationale);
        ArrayNode evidence = entry.putArray("evidence");
        ObjectNode source = evidence.addObject();
        source.put("type", "ATL105_SPECIFICATION");
        ObjectNode anchor = source.putObject("sourceAnchor");
        anchor.put("specification", "ATL105");
        anchor.put("version", "2026-3");
        anchor.put("section", section);
        anchor.put("segment", segment);
        anchor.put("element", element);
        anchor.put("rule", rule);
        source.put("reviewerProvided", true);
        mapper.writerWithDefaultPrettyPrinter().writeValue(registerFile.toFile(), register);
        System.out.printf("recorded subjectId=%s decision=%s totalDecisions=%d%n", subjectId, decision, decisions.size());
    }
}
