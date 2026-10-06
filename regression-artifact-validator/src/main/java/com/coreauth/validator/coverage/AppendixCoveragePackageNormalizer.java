package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.LinkedHashMap;
import java.util.Map;

final class AppendixCoveragePackageNormalizer {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private AppendixCoveragePackageNormalizer() { }

    static ObjectNode normalize(JsonNode source) {
        ObjectNode root = ((ObjectNode) source).deepCopy();
        Map<String, JsonNode> requirementAnchors = new LinkedHashMap<>();
        Map<String, JsonNode> scenarioAnchors = new LinkedHashMap<>();

        for (JsonNode value : root.path("businessRequirements")) {
            ObjectNode requirement = (ObjectNode) value;
            normalizeAnchors(requirement);
            retainSourceStatus(requirement);
            requirement.put("status", "REVIEW_REQUIRED");
            requirementAnchors.put(requirement.path("id").asText(), requirement.path("sourceAnchors"));
        }

        for (JsonNode value : root.path("testScenarios")) {
            ObjectNode scenario = (ObjectNode) value;
            if (!scenario.path("requirementIds").isArray() || scenario.path("requirementIds").isEmpty()) {
                JsonNode coveredRequirements = scenario.path("covers");
                if (coveredRequirements.isArray()) scenario.set("requirementIds", coveredRequirements.deepCopy());
            }
            normalizeAnchors(scenario);
            if (scenario.path("sourceAnchors").isEmpty()) {
                ArrayNode anchors = anchorsFor(scenario.path("requirementIds"), requirementAnchors);
                if (!anchors.isEmpty()) scenario.set("sourceAnchors", anchors);
            }
            retainSourceStatus(scenario);
            scenario.put("status", "REVIEW_REQUIRED");
            scenarioAnchors.put(scenario.path("id").asText(), scenario.path("sourceAnchors"));
        }

        for (JsonNode value : root.path("testCases")) {
            ObjectNode testCase = (ObjectNode) value;
            if (!testCase.path("scenarioIds").isArray() || testCase.path("scenarioIds").isEmpty()) {
                JsonNode scenarioId = testCase.path("scenarioId");
                if (scenarioId.isTextual() && !scenarioId.asText().isBlank()) {
                    testCase.putArray("scenarioIds").add(scenarioId.asText());
                }
            }
            normalizeAnchors(testCase);
            if (testCase.path("sourceAnchors").isEmpty()) {
                ArrayNode anchors = anchorsFor(testCase.path("scenarioIds"), scenarioAnchors);
                if (!anchors.isEmpty()) testCase.set("sourceAnchors", anchors);
            }
            if (!testCase.hasNonNull("expectedOutcome") || testCase.path("expectedOutcome").asText().isBlank()) {
                testCase.put("expectedOutcome", "REVIEW_REQUIRED");
            }
            retainSourceStatus(testCase);
            testCase.put("status", "REVIEW_REQUIRED");
        }

        return root;
    }

    private static void normalizeAnchors(ObjectNode artifact) {
        if (artifact.path("sourceAnchors").isArray() && !artifact.path("sourceAnchors").isEmpty()) return;
        JsonNode sourceAnchor = artifact.path("sourceAnchor");
        if (sourceAnchor.isObject()) artifact.set("sourceAnchors", MAPPER.createArrayNode().add(sourceAnchor.deepCopy()));
    }

    private static ArrayNode anchorsFor(JsonNode ids, Map<String, JsonNode> anchorsById) {
        ArrayNode anchors = MAPPER.createArrayNode();
        if (!ids.isArray()) return anchors;
        for (JsonNode id : ids) {
            JsonNode sourceAnchors = anchorsById.get(id.asText());
            if (!sourceAnchors.isArray()) continue;
            for (JsonNode anchor : sourceAnchors) {
                boolean alreadyPresent = false;
                for (JsonNode existingAnchor : anchors) {
                    if (existingAnchor.equals(anchor)) {
                        alreadyPresent = true;
                        break;
                    }
                }
                if (!alreadyPresent) anchors.add(anchor.deepCopy());
            }
        }
        return anchors;
    }

    private static void retainSourceStatus(ObjectNode artifact) {
        if (artifact.has("status") && !artifact.has("sourceStatus")) {
            artifact.set("sourceStatus", artifact.path("status").deepCopy());
        }
    }
}
