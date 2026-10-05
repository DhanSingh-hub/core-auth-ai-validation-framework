package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.AppendixGTransactionTypeCatalog;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;

public final class GenerateAppendixGTransactionTypeChains {
    public static final Path OUTPUT = Path.of("specifications/ATL105/test-output/test-json/appendix-g-transaction-type-chain-package.json");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private GenerateAppendixGTransactionTypeChains() {
    }

    public static void main(String[] args) throws Exception {
        var catalog = new AppendixGTransactionTypeCatalog();
        ObjectNode pack = MAPPER.createObjectNode();
        ObjectNode manifest = pack.putObject("manifest");
        manifest.put("packageId", "ATL105-APPG-TRANSACTION-TYPE-CLASSIFICATION");
        manifest.put("specification", "ATL105");
        manifest.put("specificationVersion", "2026-3");
        manifest.put("authority", "INDEPENDENT_TEST_SOLUTION");
        pack.put("scope", "Appendix G one-character code recognition and class only; no transaction eligibility, Prompt Code composition, special operation, lifecycle, Mastercard or converter certification.");
        pack.put("status", "PARTIALLY_COVERED");
        pack.put("validCodeCount", catalog.entries().size());
        pack.put("specialCode9CardTypeConstraint", "Appendix G states code 9 may only combine with Card Type 00 and 81-97; representation/context mapping is REVIEW_REQUIRED and not enforced here.");
        pack.put("transactionType9AndSpecialPromptComposition", "REVIEW_REQUIRED");
        pack.putArray("businessRequirements");
        pack.putArray("testScenarios");
        pack.putArray("testCases");
        pack.putArray("testData");
        for (var entry : catalog.entries().values()) {
            String code = entry.code();
            String br = "BR-APPG-CODE-" + code;
            ObjectNode anchor = anchor("transaction-type-" + code);
            ObjectNode requirement = ((ArrayNode) pack.path("businessRequirements")).addObject();
            requirement.put("id", br);
            requirement.put("title", "Appendix G transaction type " + code + " is recognized as " + entry.scope() + ".");
            requirement.put("code", code);
            requirement.put("scope", entry.scope().name());
            requirement.put("status", "COVERED");
            requirement.put("assertionScope", "SOURCE_CODE_CLASSIFICATION_ONLY");
            requirement.put("smeApproved", false);
            requirement.putArray("sourceAnchors").add(anchor.deepCopy());
            ObjectNode expected = MAPPER.createObjectNode().put("outcome", "PASS").put("code", code)
                    .put("type", entry.type()).put("description", entry.description()).put("scope", entry.scope().name());
            addChain(pack, br, "CODE-" + code, "CLASSIFICATION", code, expected, anchor);
        }
        ObjectNode unknownAnchor = anchor("transaction-type-unknown-or-malformed");
        ObjectNode invalidBr = ((ArrayNode) pack.path("businessRequirements")).addObject();
        invalidBr.put("id", "BR-APPG-UNKNOWN-OR-MALFORMED");
        invalidBr.put("title", "Unknown or malformed values are not classified as valid Appendix G transaction types.");
        invalidBr.put("status", "COVERED");
        invalidBr.put("assertionScope", "CODE_RECOGNITION_ONLY");
        invalidBr.put("smeApproved", false);
        invalidBr.putArray("sourceAnchors").add(unknownAnchor.deepCopy());
        String[] invalidValues = {"X", "x", "", "00", "*", "9*", "\u0661"};
        for (int index = 0; index < invalidValues.length; index++) {
            ObjectNode expected = MAPPER.createObjectNode().put("outcome", "FAIL");
            addChain(pack, "BR-APPG-UNKNOWN-OR-MALFORMED", "INVALID-" + index, "CLASSIFICATION", invalidValues[index], expected, unknownAnchor);
        }
        ObjectNode summary = pack.putObject("summary");
        summary.put("businessRequirements", pack.path("businessRequirements").size());
        summary.put("testScenarios", pack.path("testScenarios").size());
        summary.put("testCases", pack.path("testCases").size());
        summary.put("testData", pack.path("testData").size());
        summary.put("positiveCodeChains", catalog.entries().size());
        summary.put("unknownOrMalformedNegatives", invalidValues.length);
        summary.put("transactionEligibilityCertified", false);
        Files.createDirectories(OUTPUT.getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(OUTPUT.toFile(), pack);
        System.out.printf("ValidCodes=%d BR=%d TS=%d TC=%d TD=%d%n", catalog.entries().size(),
                pack.path("businessRequirements").size(), pack.path("testScenarios").size(),
                pack.path("testCases").size(), pack.path("testData").size());
    }

    private static void addChain(ObjectNode pack, String brId, String suffix, String mode,
                                 String input, ObjectNode expected, ObjectNode anchor) {
        String tsId = "TS-APPG-" + suffix;
        String tcId = "TC-APPG-" + suffix;
        String tdId = "TD-APPG-" + suffix;
        ObjectNode ts = ((ArrayNode) pack.path("testScenarios")).addObject();
        ts.put("id", tsId).put("status", "COVERED");
        ts.putArray("requirementIds").add(brId);
        ObjectNode testCase = ((ArrayNode) pack.path("testCases")).addObject();
        testCase.put("id", tcId).put("expectedOutcome", expected.path("outcome").asText()).put("status", "COVERED");
        testCase.putArray("scenarioIds").add(tsId);
        testCase.putArray("testDataIds").add(tdId);
        ObjectNode data = ((ArrayNode) pack.path("testData")).addObject();
        data.put("id", tdId).put("coversBr", brId).put("producer", "TEST_SOLUTION").put("readiness", "EXECUTABLE");
        data.put("fixtureKind", "APPENDIX_G_CODE_CLASSIFICATION_HARNESS").put("expectedValidation", expected.path("outcome").asText());
        data.putArray("testCaseIds").add(tcId);
        data.putArray("sourceAnchors").add(anchor.deepCopy());
        data.putObject("payload").put("mode", mode).put("transactionTypeCode", input);
        data.set("expected", expected);
        for (ObjectNode artifact : new ObjectNode[]{ts, testCase}) artifact.putArray("sourceAnchors").add(anchor.deepCopy());
    }

    private static ObjectNode anchor(String rule) {
        return MAPPER.createObjectNode().put("specification", "ATL105").put("version", "2026-3")
                .put("section", "Appendix G").put("segment", "100").put("element", "78").put("rule", rule);
    }
}