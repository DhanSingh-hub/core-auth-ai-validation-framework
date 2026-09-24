package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/** Normalizes Segment 111 evidence to the canonical BR -> TS -> TC -> TD package shape. */
public final class NormalizeSegment111Package {
    private NormalizeSegment111Package() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path input = Atl105Paths.testJson().resolve("segment-111-core-structure-package.json");
        Path output = Atl105Paths.testJson().resolve("segment-111-core-structure-package.normalized.json");
        JsonNode source = mapper.readTree(input.toFile());
        ObjectNode normalized = mapper.createObjectNode();
        ObjectNode manifest = normalized.putObject("manifest");
        manifest.put("packageId", source.path("title").asText("ATL105-SEG111-CORE-STRUCTURE"));
        manifest.put("specification", source.path("specification").asText("ATL105"));
        manifest.put("specificationVersion", source.path("specificationVersion").asText("2026-3"));
        manifest.put("strictExecutionContract", false);

        ArrayNode requirements = normalized.putArray("businessRequirements");
        Map<String, JsonNode> requirementById = new LinkedHashMap<>();
        for (JsonNode value : source.path("businessRequirements")) {
            ObjectNode requirement = requirements.addObject();
            requirement.put("id", value.path("id").asText());
            requirement.put("title", value.path("title").asText());
            ArrayNode requirementAnchors = requirement.putArray("sourceAnchors");
            requirementAnchors.add(value.path("sourceAnchor"));
            requirementById.put(value.path("id").asText(), requirement);
        }

        ArrayNode scenarios = normalized.putArray("testScenarios");
        Map<String, ObjectNode> scenarioById = new LinkedHashMap<>();
        for (JsonNode value : source.path("testScenarios")) {
            ObjectNode scenario = scenarios.addObject();
            String id = value.path("id").asText();
            scenario.put("id", id);
            ArrayNode requirementIds = scenario.putArray("requirementIds");
            for (JsonNode covered : value.path("covers")) requirementIds.add(covered.asText());
            ArrayNode anchors = scenario.putArray("sourceAnchors");
            for (JsonNode requirementId : requirementIds) {
                JsonNode requirement = requirementById.get(requirementId.asText());
                if (requirement != null) anchors.add(requirement.path("sourceAnchors").path(0));
            }
            scenario.put("status", canonicalStatus(value.path("status").asText("REVIEW_REQUIRED")));
            scenarioById.put(id, scenario);
        }

        ArrayNode testCases = normalized.putArray("testCases");
        Map<String, ObjectNode> testCaseByScenario = new LinkedHashMap<>();
        for (JsonNode value : source.path("testCases")) {
            ObjectNode testCase = testCases.addObject();
            String id = value.path("id").asText();
            testCase.put("id", id);
            ArrayNode scenarioIds = testCase.putArray("scenarioIds");
            String scenarioId = value.path("scenarioId").asText("");
            if (!scenarioId.isBlank()) scenarioIds.add(scenarioId);
            testCase.put("expectedOutcome", value.path("status").asText("REVIEW_REQUIRED"));
            testCase.put("status", value.path("status").asText("REVIEW_REQUIRED"));
            ArrayNode anchors = testCase.putArray("sourceAnchors");
            if (!scenarioId.isBlank() && scenarioById.containsKey(scenarioId)) {
                JsonNode scenarioAnchors = scenarioById.get(scenarioId).path("sourceAnchors");
                if (scenarioAnchors.isArray()) {
                    anchors.addAll((ArrayNode) scenarioAnchors);
                }
            }
            if (!scenarioId.isBlank()) testCaseByScenario.put(scenarioId, testCase);
        }
        for (JsonNode scenario : scenarios) {
            String scenarioId = scenario.path("id").asText();
            if (testCaseByScenario.containsKey(scenarioId)) continue;
            ObjectNode testCase = testCases.addObject();
            testCase.put("id", "TC-" + scenarioId + "-REVIEW");
            ArrayNode scenarioIds = testCase.putArray("scenarioIds");
            scenarioIds.add(scenarioId);
            testCase.put("expectedOutcome", "REVIEW_REQUIRED");
            testCase.put("status", "REVIEW_REQUIRED");
            testCase.set("sourceAnchors", scenario.path("sourceAnchors"));
            testCaseByScenario.put(scenarioId, testCase);
        }

        ArrayNode testData = normalized.putArray("testData");
        for (JsonNode testCase : testCases) {
            ObjectNode data = testData.addObject();
            String id = "TD-" + testCase.path("id").asText();
            data.put("id", id);
            ArrayNode testCaseIds = data.putArray("testCaseIds");
            testCaseIds.add(testCase.path("id").asText());
            data.put("expectedValidation", testCase.path("expectedOutcome").asText("REVIEW_REQUIRED"));
            data.set("sourceAnchors", testCase.path("sourceAnchors"));
            ObjectNode payload = data.putObject("payload");
            payload.put("source", "Segment 111 core-structure evidence; fixture required for execution");
        }
        normalized.putArray("requirementCrosswalk");
        Files.createDirectories(output.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), normalized);
        System.out.printf("output=%s requirements=%d scenarios=%d testCases=%d testData=%d%n",
                output, requirements.size(), scenarios.size(), testCases.size(), testData.size());
    }

    private static String canonicalStatus(String status) {
        return switch (status) {
            case "COVERED" -> "APPROVED_FOR_REVIEW";
            case "PARTIALLY_COVERED", "REVIEW_REQUIRED" -> "REVIEW_REQUIRED";
            default -> status;
        };
    }
}
