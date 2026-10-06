package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/** Resolves append-only SME decisions to one explicitly superseding effective decision per subject. */
public final class SmeDecisionHistory {
    private SmeDecisionHistory() {
    }

    public static Map<String, JsonNode> latestBySubject(JsonNode register) {
        JsonNode decisions = register == null ? null : register.path("decisions");
        if (decisions == null || !decisions.isArray()) {
            throw new IllegalStateException("decision register must contain decisions[]");
        }
        Map<String, JsonNode> latest = new LinkedHashMap<>();
        Map<String, Integer> revisions = new LinkedHashMap<>();
        Set<String> decisionIds = new LinkedHashSet<>();
        for (JsonNode decision : decisions) {
            String decisionId = required(decision, "decisionId");
            String subjectType = required(decision, "subjectType");
            String subjectId = required(decision, "subjectId");
            String segment = required(decision, "segment");
            required(decision, "decision");
            if (!decisionIds.add(decisionId)) {
                throw new IllegalStateException("Duplicate decisionId: " + decisionId);
            }

            String key = subjectType + "|" + subjectId;
            JsonNode previous = latest.get(key);
            int expectedRevision = previous == null ? 1 : revisions.get(key) + 1;
            int revision = decision.path("revision").asInt(expectedRevision);
            String supersedes = decision.path("supersedesDecisionId").asText("");
            if (revision != expectedRevision) {
                throw new IllegalStateException(decisionId + " revision must be " + expectedRevision);
            }
            if (previous == null) {
                if (!supersedes.isBlank()) {
                    throw new IllegalStateException(decisionId + " cannot supersede a decision for a new subject");
                }
            } else {
                if (!supersedes.equals(previous.path("decisionId").asText())) {
                    throw new IllegalStateException(decisionId + " must supersede current decision "
                            + previous.path("decisionId").asText());
                }
                if (!segment.equals(previous.path("segment").asText())) {
                    throw new IllegalStateException(decisionId + " changes segment for subject " + subjectId);
                }
            }
            latest.put(key, decision);
            revisions.put(key, revision);
        }
        return Map.copyOf(latest);
    }

    public static ObjectNode append(ObjectNode register, ObjectNode decision) {
        if (!register.path("decisions").isArray()) {
            throw new IllegalStateException("decision register must contain decisions[]");
        }
        Map<String, JsonNode> latest = latestBySubject(register);
        String key = required(decision, "subjectType") + "|" + required(decision, "subjectId");
        String decisionId = required(decision, "decisionId");
        JsonNode previous = latest.get(key);
        ObjectNode appended = decision.deepCopy();
        if (previous == null) {
            appended.put("revision", 1);
            appended.remove("supersedesDecisionId");
        } else {
            appended.put("revision", previous.path("revision").asInt(1) + 1);
            appended.put("supersedesDecisionId", previous.path("decisionId").asText());
        }
        for (JsonNode existing : register.path("decisions")) {
            if (decisionId.equals(existing.path("decisionId").asText())) {
                throw new IllegalStateException("Duplicate decisionId: " + decisionId);
            }
        }
        ((ArrayNode) register.path("decisions")).add(appended);
        latestBySubject(register);
        return appended;
    }

    private static String required(JsonNode node, String field) {
        String value = node.path(field).asText("");
        if (value.isBlank()) throw new IllegalStateException("Decision is missing " + field);
        return value;
    }
}