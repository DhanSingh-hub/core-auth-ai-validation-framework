package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;

/** Derives review-required TS, TC, and TD chain skeletons for approved Test BRs only. */
public final class GenerateApprovedBrChainPackage {
    private GenerateApprovedBrChainPackage() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path outputRoot = Atl105Paths.testOutput().resolve("test-solution-independent-review");
        JsonNode approved = mapper.readTree(outputRoot.resolve("approved-atomic-test-solution-business-requirements.json").toFile());
        ArrayNode businessRequirements = copyArray(approved.path("businessRequirements"), mapper);
        ArrayNode scenarios = mapper.createArrayNode();
        ArrayNode testCases = mapper.createArrayNode();
        ArrayNode testData = mapper.createArrayNode();

        for (JsonNode br : businessRequirements) {
            String brId = br.path("id").asText();
            String tsId = "TS-REVIEW-" + safe(brId);
            String tcId = "TC-REVIEW-" + safe(brId);
            String tdId = "TD-REVIEW-" + safe(brId);
            JsonNode anchors = br.path("sourceAnchors");

            ObjectNode scenario = scenarios.addObject();
            scenario.put("id", tsId);
            scenario.putArray("requirementIds").add(brId);
            scenario.set("sourceAnchors", anchors);
            scenario.put("status", "REVIEW_REQUIRED");
            scenario.put("trainingStatus", "TS_DERIVATION_REQUIRED");
            scenario.put("derivationPolicy", "Derive positive, negative, boundary, and applicability scenarios from the source rule; do not copy AI scenario wording.");

            ObjectNode testCase = testCases.addObject();
            testCase.put("id", tcId);
            testCase.putArray("scenarioIds").add(tsId);
            testCase.set("sourceAnchors", anchors);
            testCase.put("status", "REVIEW_REQUIRED");
            testCase.put("trainingStatus", "TC_DERIVATION_REQUIRED");
            testCase.put("expectedOutcome", "REVIEW_REQUIRED");

            ObjectNode data = testData.addObject();
            data.put("id", tdId);
            data.putArray("testCaseIds").add(tcId);
            data.set("sourceAnchors", anchors);
            data.put("status", "REVIEW_REQUIRED");
            data.put("trainingStatus", "TEST_DATA_DERIVATION_REQUIRED");
            data.put("expectedValidation", "REVIEW_REQUIRED");
            data.putObject("payload").put("fixtureStatus", "SME_FIXTURE_REQUIRED");
        }

        ObjectNode output = mapper.createObjectNode();
        ObjectNode manifest = output.putObject("manifest");
        manifest.put("packageId", "ATL105-APPROVED-BR-CHAIN-PACKAGE");
        manifest.put("specification", "ATL105");
        manifest.put("specificationVersion", "2026-3");
        manifest.put("authority", "INDEPENDENT_TEST_SOLUTION");
        manifest.put("aiArtifactsIncluded", false);
        manifest.put("status", "REVIEW_REQUIRED");
        manifest.put("canonicalChain", "businessRequirements -> testScenarios -> testCases -> testData");
        output.set("businessRequirements", businessRequirements);
        output.set("testScenarios", scenarios);
        output.set("testCases", testCases);
        output.set("testData", testData);
        output.putArray("requirementCrosswalk");
        ObjectNode summary = output.putObject("summary");
        summary.put("approvedBusinessRequirements", businessRequirements.size());
        summary.put("testScenarios", scenarios.size());
        summary.put("testCases", testCases.size());
        summary.put("testData", testData.size());
        summary.put("reviewRequiredScenarios", scenarios.size());
        summary.put("reviewRequiredTestCases", testCases.size());
        summary.put("reviewRequiredTestData", testData.size());
        summary.put("executionReady", false);
        summary.put("reason", "Generated chain skeletons require independent scenario derivation, expected outcomes, and fixture-backed test data.");
        summary.put("nextGate", "CHAIN_VALIDATION_AFTER_TS_TC_TD_DERIVATION");

        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("approved-br-ts-tc-td-chain-package.json").toFile(), output);
        Files.writeString(outputRoot.resolve("approved-br-ts-tc-td-chain-package.md"), markdown(summary));
        System.out.printf("BR=%d TS=%d TC=%d TD=%d executionReady=%s%n",
                businessRequirements.size(), scenarios.size(), testCases.size(), testData.size(), summary.path("executionReady").asBoolean());
    }

    private static ArrayNode copyArray(JsonNode values, ObjectMapper mapper) {
        ArrayNode output = mapper.createArrayNode();
        if (values.isArray()) values.forEach(value -> output.add(value.deepCopy()));
        return output;
    }

    private static String safe(String value) {
        return value.replaceAll("[^A-Za-z0-9]+", "-");
    }

    private static String markdown(JsonNode summary) {
        return "# Approved BR to TS to TC to TD Chain Package\n\n"
                + "This package derives review-required chain skeletons only for approved Test Solution BRs.\n\n"
                + "| Artifact | Count |\n|---|---:|\n"
                + "| Business Requirements | " + summary.path("approvedBusinessRequirements").asInt() + " |\n"
                + "| Test Scenarios | " + summary.path("testScenarios").asInt() + " |\n"
                + "| Test Cases | " + summary.path("testCases").asInt() + " |\n"
                + "| Test Data | " + summary.path("testData").asInt() + " |\n\n"
                + "Execution readiness remains false until all four levels are independently derived and validated.\n";
    }
}