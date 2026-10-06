package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/** Adds review-only TS/TC/TD chain drafts for special-flow BRs supported by current source evidence. */
public final class GenerateSpecialTransactionTypeChainDrafts {
    private GenerateSpecialTransactionTypeChainDrafts() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path testJson = Atl105Paths.testJson();
        JsonNode brPackage = mapper.readTree(testJson.resolve("special-transaction-type-br-baseline.json").toFile());
        ArrayNode scenarios = mapper.createArrayNode();
        ArrayNode cases = mapper.createArrayNode();
        ArrayNode testData = mapper.createArrayNode();
        ArrayNode chains = mapper.createArrayNode();
        ArrayNode blocked = mapper.createArrayNode();
        Path fixtureRoot = testJson.resolve("special-transaction-type-test-data-drafts");
        Files.createDirectories(fixtureRoot);
        Set<String> ids = new HashSet<>();

        for (JsonNode br : brPackage.path("businessRequirements")) {
            String brId = br.path("id").asText();
            String code = br.path("transactionTypeCode").asText();
            String evidenceLevel = br.path("evidenceLevel").asText("");
            String sourceBlocker = br.path("blocker").asText("");
            if ("APPENDIX_G_ONLY".equals(evidenceLevel)) {
                ObjectNode unresolved = blocked.addObject();
                unresolved.put("transactionTypeCode", code);
                unresolved.put("businessRequirementId", brId);
                unresolved.put("reason", sourceBlocker.isBlank()
                        ? "Only an Appendix G operation label is available; detailed flow rules need authoritative source."
                        : sourceBlocker);
                unresolved.put("status", "SOURCE_REVIEW_REQUIRED");
                continue;
            }

            String suffix = safe(brId);
            String tsId = "TS-DRAFT-" + suffix;
            String tcId = "TC-DRAFT-" + suffix;
            String tdId = "TD-DRAFT-" + suffix;
            String fixtureName = tdId.toLowerCase() + ".json";
            requireUnique(ids, brId, tsId, tcId, tdId);

            ObjectNode ts = scenarios.addObject();
            ts.put("id", tsId);
            ts.putArray("requirementIds").add(brId);
            ts.set("sourceAnchors", br.path("sourceAnchors").deepCopy());
            ts.put("title", "DRAFT: " + br.path("title").asText());
            ts.put("status", "DRAFT_REVIEW_REQUIRED");
            ts.put("trainingStatus", "SCENARIO_DRAFT_NOT_APPROVED");
            ts.put("scenarioType", scenarioType(br));
            if (!sourceBlocker.isBlank()) ts.put("derivationNote", sourceBlocker);

            ObjectNode tc = cases.addObject();
            tc.put("id", tcId);
            tc.putArray("scenarioIds").add(tsId);
            tc.set("sourceAnchors", br.path("sourceAnchors").deepCopy());
            tc.put("title", "DRAFT: verify " + br.path("title").asText());
            tc.put("expectedOutcome", "SOURCE_BEHAVIOR_REVIEW_REQUIRED");
            tc.put("status", "DRAFT_REVIEW_REQUIRED");
            tc.put("testDataFile", fixtureName);
            tc.put("executionReady", false);

            ObjectNode td = testData.addObject();
            td.put("id", tdId);
            td.putArray("testCaseIds").add(tcId);
            td.set("sourceAnchors", br.path("sourceAnchors").deepCopy());
            td.put("expectedValidation", "REVIEW_REQUIRED");
            td.put("status", "DRAFT_TEST_DATA_STUB_NOT_EXECUTABLE");
            td.put("fixtureFile", "special-transaction-type-test-data-drafts/" + fixtureName);
            td.put("executionReady", false);

            ObjectNode fixture = mapper.createObjectNode();
            fixture.put("artifact", "atl105-special-transaction-test-data-draft");
            fixture.put("specification", "ATL105");
            fixture.put("specificationVersion", "2026-3");
            fixture.put("transactionTypeCode", code);
            fixture.put("businessRequirementId", brId);
            fixture.put("testScenarioId", tsId);
            fixture.put("testCaseId", tcId);
            fixture.put("synthetic", true);
            fixture.put("wireReady", false);
            fixture.put("fixtureStatus", "DRAFT_SCHEMA_ONLY_VALUES_AND_PROTOCOL_REQUIRE_DERIVATION");
            fixture.put("sourceBehavior", br.path("requirement").asText());
            fixture.set("sourceAnchors", br.path("sourceAnchors").deepCopy());
            fixture.putObject("businessScenarioInputs").put("values", "DERIVE_FROM_DOCUMENTED_OPERATION_AND_MESSAGE_SCHEMA");
            fixture.putObject("expectedState").put("assertion", "DRAFT_EXPECTED_RESULT_REQUIRES_REVIEW");
            fixture.putArray("reviewActions").add("Replace schema-only placeholders with synthetic protocol-valid values.")
                    .add("Add positive, negative, boundary, and lifecycle fixtures where applicable.")
                    .add("Validate against the actual converter/schema before marking executable.");
            mapper.writerWithDefaultPrettyPrinter().writeValue(fixtureRoot.resolve(fixtureName).toFile(), fixture);

            ObjectNode chain = chains.addObject();
            chain.put("transactionTypeCode", code);
            chain.put("businessRequirementId", brId);
            chain.put("testScenarioId", tsId);
            chain.put("testCaseId", tcId);
            chain.put("testDataId", tdId);
            chain.put("testDataFile", fixtureName);
            chain.put("mappingStatus", "DRAFT_CHAIN_REVIEW_REQUIRED");
            chain.put("independentFixtureExecutable", false);
        }

        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "atl105-special-transaction-br-ts-tc-td-draft-package");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("aiArtifactsIncluded", false);
        output.put("sourceBrPackage", "special-transaction-type-br-baseline.json");
        output.put("policy", "Draft chains are structural handoff scaffolds only. TD JSON files are explicitly non-executable schema stubs until protocol-valid synthetic values and expected outcomes are source-derived and validated.");
        output.set("businessRequirements", brPackage.path("businessRequirements").deepCopy());
        output.set("testScenarios", scenarios);
        output.set("testCases", cases);
        output.set("testData", testData);
        output.set("chains", chains);
        output.set("blockedBrs", blocked);
        ObjectNode summary = output.putObject("summary");
        summary.put("specialTransactionCodes", 10);
        summary.put("businessRequirements", brPackage.path("businessRequirements").size());
        summary.put("draftChains", chains.size());
        summary.put("blockedBusinessRequirements", blocked.size());
        summary.put("testScenarios", scenarios.size());
        summary.put("testCases", cases.size());
        summary.put("testDataDraftStubs", testData.size());
        summary.put("executableTestDataFixtures", 0);
        summary.put("executionReady", false);
        summary.put("nextGate", "DERIVE_PROTOCOL_VALID_TEST_DATA_AND_EXPECTED_OUTCOMES");
        mapper.writerWithDefaultPrettyPrinter().writeValue(
                testJson.resolve("special-transaction-type-br-ts-tc-td-draft-package.json").toFile(), output);
        Files.writeString(testJson.resolve("special-transaction-type-br-ts-tc-td-draft-package.md"), markdown(summary));
        System.out.printf("BR=%d draftChains=%d blockedBR=%d TS=%d TC=%d TDStubs=%d executableTD=0%n",
                summary.path("businessRequirements").asInt(), chains.size(), blocked.size(), scenarios.size(), cases.size(), testData.size());
    }

    private static String scenarioType(JsonNode br) {
        String code = br.path("transactionTypeCode").asText();
        if ("9".equals(code)) return "SPECIAL_PROMPT_ROUTING";
        if ("V".equals(code)) return "LOYALTY_OPERATION";
        if ("M".equals(code) || "N".equals(code) || "L".equals(code) || "Q".equals(code)) return "STORED_VALUE_STATE_CHANGE";
        return "SPECIAL_OPERATION";
    }

    private static void requireUnique(Set<String> ids, String... values) {
        for (String value : values) if (!ids.add(value)) throw new IllegalStateException("Duplicate draft chain ID: " + value);
    }

    private static String safe(String value) {
        return value.toUpperCase(java.util.Locale.ROOT).replaceAll("[^A-Z0-9]+", "-");
    }

    private static String markdown(JsonNode summary) {
        return "# Special Transaction-Type Draft BR -> TS -> TC -> TD Package\n\n"
                + "| Metric | Count |\n|---|---:|\n"
                + "| Special BRs | " + summary.path("businessRequirements").asInt() + " |\n"
                + "| Draft chains | " + summary.path("draftChains").asInt() + " |\n"
                + "| Blocked BRs | " + summary.path("blockedBusinessRequirements").asInt() + " |\n"
                + "| TS / TC / TD draft stubs | " + summary.path("testScenarios").asInt() + " / " + summary.path("testCases").asInt() + " / " + summary.path("testDataDraftStubs").asInt() + " |\n"
                + "| Executable TD fixtures | 0 |\n\n"
                + "All chains remain review-required; TD files are schema-only stubs, not protocol-valid payloads.\n";
    }
}