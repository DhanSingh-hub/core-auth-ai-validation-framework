package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.AppendixETableLoadCardTypeCodes;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Path;
import java.util.Set;

/** Generates independent BR -> TS -> TC -> TD allowlist evidence for Appendix E Table Load values. */
public final class GenerateAppendixETableLoadCoverage {
    private static final String FAMILY_BR = "BR-SEG100-APPE-TABLE-LOAD-FAMILY";
    private static final String CODE_BR_PREFIX = "BR-SEG100-APPE-TABLE-LOAD-CODE-";
    private static final String TS_PREFIX = "TS-SEG100-APPE-TABLE-LOAD-";
    private static final String TC_PREFIX = "TC-SEG100-APPE-TABLE-LOAD-";
    private static final String TD_PREFIX = "TD-SEG100-APPE-TABLE-LOAD-";

    private GenerateAppendixETableLoadCoverage() {
    }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path packageFile = Atl105Paths.testJson().resolve("appendices/appendix-e-segment-100-coverage.json");
        ObjectNode appendix = (ObjectNode) mapper.readTree(packageFile.toFile());
        ArrayNode businessRequirements = array(appendix, "businessRequirements");
        ArrayNode scenarios = array(appendix, "testScenarios");
        ArrayNode testCases = array(appendix, "testCases");
        ArrayNode testData = array(appendix, "testData");

        removeGenerated(businessRequirements, CODE_BR_PREFIX);
        removeGenerated(scenarios, TS_PREFIX);
        removeGenerated(testCases, TC_PREFIX);
        removeGenerated(testData, TD_PREFIX);

        ObjectNode familyBr = findById(businessRequirements, FAMILY_BR);
        if (familyBr == null) throw new IllegalStateException("Missing Appendix E Table Load family BR");
        familyBr.put("status", "COVERED");
        familyBr.put("coverageNote", "The complete 56-value Appendix E Table Load allowlist is checked in DL1.CardType. This does not certify downstream terminal feature effects or production configuration.");

        Set<String> cardTypes = AppendixETableLoadCardTypeCodes.cardTypeCodes();
        Set<String> features = AppendixETableLoadCardTypeCodes.terminalFeatureCodes();
        for (String code : AppendixETableLoadCardTypeCodes.supportedCodes()) {
            String kind = cardTypes.contains(code) ? "CARD_TYPE" : "TERMINAL_FEATURE";
            addCodeChain(appendix, businessRequirements, scenarios, testCases, testData, code, kind, true);
        }
        addUnknownNegativeChain(appendix, businessRequirements, scenarios, testCases, testData);

        ObjectNode codeFamily = (ObjectNode) appendix.path("codeFamilies").path("tableLoadResponseCodes");
        codeFamily.put("status", "ALLOWLIST_CHAIN_COVERED_FEATURE_EFFECTS_SEPARATE");
        codeFamily.put("validator", "TableLoadPayloadValidator");
        codeFamily.put("testDataReadiness", "INDEPENDENT_SYNTHETIC_VALIDATOR_HARNESS");
        ObjectNode summary = appendix.with("tableLoadCoverageSummary");
        summary.put("tableLoadValues", AppendixETableLoadCardTypeCodes.supportedCodes().size());
        summary.put("tableLoadCardTypes", cardTypes.size());
        summary.put("terminalFeatureCodes", features.size());
        summary.put("perValueBrTsTcTdChains", AppendixETableLoadCardTypeCodes.supportedCodes().size());
        summary.put("unknownCodeNegativeChains", 1);
        summary.put("terminalFeatureEffectsCertified", false);
        summary.put("productionConfigurationCertified", false);

        appendix.put("businessRequirementCount", businessRequirements.size());
        appendix.put("testScenarioCount", scenarios.size());
        appendix.put("testCaseCount", testCases.size());
        appendix.put("testDataCount", testData.size());
        appendix.put("testDataStatus", "Appendix E Table Load allowlist has independent synthetic validator chains for all 56 listed values plus an unknown-code negative. Payloads test DL1.CardType membership only; feature effects, device behavior, production configuration, and AI artifact coverage remain outside this evidence.");
        mapper.writerWithDefaultPrettyPrinter().writeValue(packageFile.toFile(), appendix);
        System.out.printf("TableLoadCodes=%d perValueChains=%d negativeChains=1 AppendixEBrs=%d TS=%d TC=%d TD=%d%n",
                AppendixETableLoadCardTypeCodes.supportedCodes().size(),
                AppendixETableLoadCardTypeCodes.supportedCodes().size(),
                businessRequirements.size(), scenarios.size(), testCases.size(), testData.size());
    }

    private static void addCodeChain(ObjectNode appendix, ArrayNode brs, ArrayNode scenarios,
                                    ArrayNode cases, ArrayNode data, String code, String kind,
                                    boolean expectedPass) {
        String suffix = code;
        String brId = CODE_BR_PREFIX + suffix;
        String tsId = TS_PREFIX + suffix;
        String tcId = TC_PREFIX + suffix + (expectedPass ? "-PASS" : "-FAIL");
        String tdId = TD_PREFIX + suffix + (expectedPass ? "-PASS" : "-FAIL");
        String sourceRule = "table-load-" + kind.toLowerCase() + "-" + code;
        ArrayNode anchors = anchors(appendix, sourceRule);

        ObjectNode br = brs.addObject();
        br.put("id", brId);
        br.put("title", "Appendix E Table Load " + kind.toLowerCase().replace('_', ' ') + " code " + code + " is recognized in the Table Load Response family.");
        br.put("code", code);
        br.put("codeKind", kind);
        br.put("status", "COVERED");
        br.set("sourceAnchors", anchors.deepCopy());

        ObjectNode ts = scenarios.addObject();
        ts.put("id", tsId);
        ts.putArray("requirementIds").add(FAMILY_BR).add(brId);
        ts.put("title", "Recognize Appendix E Table Load value " + code + " as " + kind.toLowerCase().replace('_', ' ') + ".");
        ts.put("status", "COVERED");
        ts.set("sourceAnchors", anchors.deepCopy());

        ObjectNode tc = cases.addObject();
        tc.put("id", tcId);
        tc.putArray("scenarioIds").add(tsId);
        tc.putArray("testDataIds").add(tdId);
        tc.put("expectedOutcome", expectedPass ? "PASS" : "FAIL");
        tc.put("status", "COVERED");
        tc.put("assertionScope", "DL1.CardType Appendix E Table Load allowlist only");
        tc.set("sourceAnchors", anchors.deepCopy());

        ObjectNode td = data.addObject();
        td.put("id", tdId);
        td.putArray("testCaseIds").add(tcId);
        td.put("coversBr", brId);
        td.put("producer", "TEST_SOLUTION");
        td.put("readiness", "EXECUTABLE");
        td.put("fixtureKind", "TABLE_LOAD_ALLOWLIST_VALIDATOR_HARNESS");
        td.put("expectedValidation", expectedPass ? "PASS" : "FAIL");
        td.put("converterReady", false);
        td.set("sourceAnchors", anchors.deepCopy());
        ObjectNode response = td.putObject("payload").putObject("Table Load Response");
        response.putObject("DL1").put("CardType", code);
        response.putObject("DL2");
        response.putObject("DL3");
        if ("173".equals(code)) response.putObject("DL6");
    }

    private static void addUnknownNegativeChain(ObjectNode appendix, ArrayNode requirements,
                                               ArrayNode scenarios, ArrayNode cases, ArrayNode data) {
        String tsId = "TS-SEG100-APPE-TABLE-LOAD-UNKNOWN";
        String tcId = "TC-SEG100-APPE-TABLE-LOAD-UNKNOWN-FAIL";
        String tdId = "TD-SEG100-APPE-TABLE-LOAD-UNKNOWN-FAIL";
        ArrayNode anchors = anchors(appendix, "table-load-unknown-code-rejected");

        ObjectNode ts = scenarios.addObject();
        ts.put("id", tsId);
        ts.putArray("requirementIds").add(FAMILY_BR);
        ts.put("title", "Reject a code outside all Appendix E Table Load Response tables.");
        ts.put("status", "COVERED");
        ts.set("sourceAnchors", anchors.deepCopy());

        ObjectNode tc = cases.addObject();
        tc.put("id", tcId);
        tc.putArray("scenarioIds").add(tsId);
        tc.putArray("testDataIds").add(tdId);
        tc.put("expectedOutcome", "FAIL");
        tc.put("status", "COVERED");
        tc.put("assertionScope", "Reject unlisted DL1.CardType code; not special Prompt Code family behavior");
        tc.set("sourceAnchors", anchors.deepCopy());

        ObjectNode td = data.addObject();
        td.put("id", tdId);
        td.putArray("testCaseIds").add(tcId);
        td.put("coversBr", FAMILY_BR);
        td.put("producer", "TEST_SOLUTION");
        td.put("readiness", "EXECUTABLE");
        td.put("fixtureKind", "TABLE_LOAD_ALLOWLIST_VALIDATOR_HARNESS");
        td.put("expectedValidation", "FAIL");
        td.put("converterReady", false);
        td.set("sourceAnchors", anchors.deepCopy());
        ObjectNode response = td.putObject("payload").putObject("Table Load Response");
        response.putObject("DL1").put("CardType", "999");
        response.putObject("DL2");
        response.putObject("DL3");
    }

    private static ArrayNode anchors(ObjectNode appendix, String rule) {
        ArrayNode anchors = appendix.arrayNode();
        ObjectNode anchor = anchors.addObject();
        anchor.put("specification", appendix.path("specification").asText("ATL105"));
        anchor.put("version", appendix.path("specificationVersion").asText("2026-3"));
        anchor.put("section", "Appendix E");
        anchor.put("segment", "TABLE-LOAD-RESPONSE");
        anchor.put("element", "DL1.CardType");
        anchor.put("rule", rule);
        return anchors;
    }

    private static ArrayNode array(ObjectNode object, String property) {
        JsonNode current = object.path(property);
        if (current.isArray()) return (ArrayNode) current;
        ArrayNode created = object.arrayNode();
        object.set(property, created);
        return created;
    }

    private static void removeGenerated(ArrayNode nodes, String prefix) {
        for (int index = nodes.size() - 1; index >= 0; index--) {
            if (nodes.get(index).path("id").asText().startsWith(prefix)) nodes.remove(index);
        }
    }

    private static ObjectNode findById(ArrayNode nodes, String id) {
        for (JsonNode node : nodes) {
            if (id.equals(node.path("id").asText())) return (ObjectNode) node;
        }
        return null;
    }
}
