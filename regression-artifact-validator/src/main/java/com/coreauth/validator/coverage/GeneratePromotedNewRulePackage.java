package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;

/** Builds canonical BR -> TS -> TC -> TD review packages only for SME-promoted NEW_RULE decisions. */
public final class GeneratePromotedNewRulePackage {
    private GeneratePromotedNewRulePackage() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        JsonNode register = mapper.readTree(reviewRoot.resolve("ai-only-sme-decision-register.json").toFile());
        ObjectNode packageNode = mapper.createObjectNode();
        ObjectNode manifest = packageNode.putObject("manifest");
        manifest.put("packageId", "ATL105-SME-PROMOTED-NEW-RULES");
        manifest.put("specification", "ATL105");
        manifest.put("specificationVersion", "2026-3");
        manifest.put("strictExecutionContract", false);
        ArrayNode requirements = packageNode.putArray("businessRequirements");
        ArrayNode scenarios = packageNode.putArray("testScenarios");
        ArrayNode testCases = packageNode.putArray("testCases");
        ArrayNode testData = packageNode.putArray("testData");
        int promoted = 0;

        for (JsonNode decision : register.path("decisions")) {
            if (!"NEW_RULE".equals(decision.path("decision").asText())) continue;
            String id = decision.path("decisionId").asText();
            String segment = decision.path("segment").asText();
            String brId = "BR-SME-" + id;
            String tsId = "TS-SME-" + id;
            String tcId = "TC-SME-" + id;
            String tdId = "TD-SME-" + id;
            JsonNode sourceAnchor = firstSourceAnchor(decision.path("evidence"));

            ObjectNode br = requirements.addObject();
            br.put("id", brId);
            br.put("title", "Independently derived Test Solution rule for promoted AI-only candidate " + decision.path("subjectId").asText());
            br.set("sourceAnchors", arrayWith(sourceAnchor, mapper));
            br.put("status", "APPROVED_FOR_REVIEW");

            ObjectNode ts = scenarios.addObject();
            ts.put("id", tsId);
            ts.putArray("requirementIds").add(brId);
            ts.set("sourceAnchors", arrayWith(sourceAnchor, mapper));
            ts.put("status", "REVIEW_REQUIRED");

            ObjectNode tc = testCases.addObject();
            tc.put("id", tcId);
            tc.putArray("scenarioIds").add(tsId);
            tc.set("sourceAnchors", arrayWith(sourceAnchor, mapper));
            tc.put("expectedOutcome", "REVIEW_REQUIRED");
            tc.put("status", "REVIEW_REQUIRED");

            ObjectNode td = testData.addObject();
            td.put("id", tdId);
            td.putArray("testCaseIds").add(tcId);
            td.set("sourceAnchors", arrayWith(sourceAnchor, mapper));
            td.put("expectedValidation", "REVIEW_REQUIRED");
            td.putObject("payload").put("fixtureStatus", "SME_FIXTURE_REQUIRED");
            promoted++;
        }

        packageNode.put("promotedNewRuleCount", promoted);
        Path output = reviewRoot.resolve("promoted-new-rule-packages").resolve("segment-111-promoted-new-rules.json");
        Files.createDirectories(output.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), packageNode);
        System.out.printf("promotedNewRules=%d output=%s%n", promoted, output);
    }

    private static JsonNode firstSourceAnchor(JsonNode evidence) {
        for (JsonNode item : evidence) {
            if (item.path("sourceAnchor").isObject()) return item.path("sourceAnchor");
        }
        throw new IllegalStateException("Promoted NEW_RULE requires sourceAnchor evidence");
    }

    private static ArrayNode arrayWith(JsonNode value, ObjectMapper mapper) {
        ArrayNode values = mapper.createArrayNode();
        values.add(value);
        return values;
    }
}
