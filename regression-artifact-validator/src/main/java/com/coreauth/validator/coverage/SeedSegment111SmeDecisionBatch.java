package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Seeds Segment 111 AI-only candidates as PENDING decisions without promoting coverage. */
public final class SeedSegment111SmeDecisionBatch {
    private SeedSegment111SmeDecisionBatch() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        Path registerFile = reviewRoot.resolve("ai-only-sme-decision-register.json");
        Path triageFile = reviewRoot.resolve("ai-only-trained-segment-triage.csv");
        ObjectNode register = (ObjectNode) mapper.readTree(registerFile.toFile());
        ArrayNode decisions = (ArrayNode) register.path("decisions");
        List<String> existingSubjects = new ArrayList<>();
        for (JsonNode decision : decisions) existingSubjects.add(decision.path("subjectId").asText());

        int added = 0;
        for (String line : Files.readAllLines(triageFile).subList(1, Files.readAllLines(triageFile).size())) {
            String[] fields = parseCsvLine(line);
                if (fields.length < 8 || !"111".equals(fields[1])) continue;
            String requirementId = fields[0];
            if (existingSubjects.contains(requirementId)) continue;
            ObjectNode decision = decisions.addObject();
                decision.put("decisionId", "SME-SEG111-" + requirementId.replaceAll("[^A-Za-z0-9]+", "-"));
            decision.put("subjectType", "AI_ONLY_BR");
            decision.put("subjectId", requirementId);
            decision.put("segment", "111");
            decision.put("decision", "PENDING");
            decision.put("reviewer", "UNREVIEWED");
                decision.put("rationale", "Awaiting SME/TBA decision for the AI-only triage category " + fields[4]
                    + ": independently derive a Segment 111 rule from the ATL105 2026-3 source, confirm duplicate, confirm a rematch, or reject the AI-only candidate. This pending item does not count toward coverage.");
            ArrayNode evidence = decision.putArray("evidence");
            ObjectNode source = evidence.addObject();
            source.put("type", "AI_DELIVERY_RECORD");
            source.put("path", "test-input/ai-solution/runs/2026-09-23/Run2/traceability_matrix_full.json");
            source.put("aiRequirementId", requirementId);
            source.put("sourceRuleId", fields[2]);
            source.put("sourcePage", parseInt(fields[6]));
            source.put("triageCategory", fields[4]);
            ObjectNode spec = evidence.addObject();
            spec.put("type", "SME_ACTION_REQUIRED");
            spec.put("action", "Read ATL105 2026-3 Section 12.10 and record an independent Test Solution decision");
            existingSubjects.add(requirementId);
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(registerFile.toFile(), register);
        System.out.printf("segment=111 pendingSeeded=%d totalDecisions=%d%n", added, decisions.size());
    }

    private static int parseInt(String value) {
        try { return Integer.parseInt(value); } catch (NumberFormatException ignored) { return -1; }
    }

    private static String[] parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean quoted = false;
        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);
            if (quoted) {
                if (character == '"' && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else if (character == '"') quoted = false;
                else current.append(character);
            } else if (character == '"') quoted = true;
            else if (character == ',') { fields.add(current.toString()); current.setLength(0); }
            else current.append(character);
        }
        fields.add(current.toString());
        return fields.toArray(new String[0]);
    }
}
