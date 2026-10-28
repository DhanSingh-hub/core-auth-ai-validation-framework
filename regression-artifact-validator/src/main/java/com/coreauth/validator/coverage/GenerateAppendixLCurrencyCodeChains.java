package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.AppendixLCurrencyCodeCatalog;
import com.coreauth.validator.canonical.AppendixLCurrencyCodeCatalog.Classification;
import com.coreauth.validator.canonical.AppendixLCurrencyCodeCatalog.Row;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Generates the independent Appendix L (Valid Currency Codes, Element 20) classification/format
 * chain package: one BR -> TS -> TC -> TD chain for every one of the 151 source-listed currency
 * codes, one shared negative chain for well-formed-but-unlisted three-digit values, and one shared
 * negative chain for malformed representations. This mirrors
 * {@link GenerateAppendixGTransactionTypeChains} and {@code AppendixFProductCodeOracle} evidence:
 * source-code recognition only, not transaction eligibility, network-country certification, or the
 * Element 76 implied-decimal / Appendix M fixed-840 HIP subelement rules documented separately in
 * {@code docs/specs/kb/appendix-l/README.md}.
 */
public final class GenerateAppendixLCurrencyCodeChains {
    public static final Path OUTPUT = Path.of("specifications/ATL105/test-output/test-json/appendix-l-currency-code-chain-package.json");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /** Well-formed three ASCII digit values that are not assigned to any Appendix L row. */
    private static final String[] UNKNOWN_WELL_FORMED_CODES = {"000", "001", "005", "097", "100", "300", "500", "700", "900", "999"};

    /** Malformed representations: wrong width, non-numeric, signed, padded, and Unicode-digit values. */
    private static final String[] MALFORMED_VALUES = {null, "", "84", "0840", "USD", "-840", "84O", " 840", "840 ", "\u0668\u0664\u0660"};

    private GenerateAppendixLCurrencyCodeChains() {
    }

    public static void main(String[] args) throws Exception {
        var catalog = new AppendixLCurrencyCodeCatalog();
        ObjectNode pack = MAPPER.createObjectNode();
        ObjectNode manifest = pack.putObject("manifest");
        manifest.put("packageId", "ATL105-APPL-CURRENCY-CODE-CLASSIFICATION");
        manifest.put("specification", "ATL105");
        manifest.put("specificationVersion", "2026-3");
        manifest.put("authority", "INDEPENDENT_TEST_SOLUTION");
        pack.put("scope", "Appendix L three-digit currency-code recognition and Visa/MC/Both source classification only. "
                + "No per-transaction network/country eligibility, no Element 76 Product Amount implied-decimal certification, "
                + "and no Appendix M Element 164 fixed-840 HIP subelement certification; see the training note for those boundaries.");
        pack.put("status", "PARTIALLY_COVERED");
        pack.put("validCodeCount", catalog.codes().size());
        pack.put("sourceRowCount", catalog.rows().size());
        pack.put("transactionEligibilityCertified", false);
        ArrayNode conflictCodes = pack.putArray("networkScopeConflictCodes");
        pack.putArray("businessRequirements");
        pack.putArray("testScenarios");
        pack.putArray("testCases");
        pack.putArray("testData");

        for (String code : catalog.codes()) {
            Classification classification = catalog.classify(code);
            String br = "BR-APPL-CODE-" + code;
            ObjectNode anchor = anchor("currency-code-" + code, classification.rows().get(0).pageAnchor());
            ObjectNode requirement = ((ArrayNode) pack.path("businessRequirements")).addObject();
            requirement.put("id", br);
            requirement.put("code", code);
            requirement.put("assertionScope", "SOURCE_CODE_CLASSIFICATION_ONLY");
            requirement.put("smeApproved", false);
            requirement.putArray("sourceAnchors").add(anchor.deepCopy());

            ObjectNode expected = MAPPER.createObjectNode();
            ArrayNode claims = expected.putArray("rows");
            for (Row row : classification.rows()) {
                ObjectNode claim = MAPPER.createObjectNode();
                claim.put("currencyName", row.currencyName());
                ArrayNode countries = claim.putArray("countries");
                row.countries().forEach(countries::add);
                claim.put("networkScope", row.networkScope().name());
                claim.put("pageAnchor", row.pageAnchor());
                claims.add(claim);
            }
            if (classification.networkScopeConflict()) {
                requirement.put("title", "Appendix L currency code " + code + " (" + classification.rows().get(0).currencyName()
                        + ") is recognized, but its Visa/MC/Both network scope varies by country and stays REVIEW_REQUIRED.");
                requirement.put("status", "REVIEW_REQUIRED");
                expected.put("outcome", "REVIEW_REQUIRED");
                expected.put("networkScopeConflict", true);
                conflictCodes.add(code);
            } else {
                requirement.put("title", "Appendix L currency code " + code + " (" + classification.rows().get(0).currencyName()
                        + ") is recognized with a single Visa/MC/Both network scope.");
                requirement.put("status", "PASS");
                expected.put("outcome", "PASS");
                expected.put("networkScopeConflict", false);
                expected.put("networkScope", classification.aggregatedScope().name());
            }
            expected.put("code", code);
            addChain(pack, br, "CODE-" + code, "CLASSIFICATION", code, expected, anchor);
        }

        ObjectNode unknownAnchor = anchor("currency-code-unknown-but-well-formed", "L-1");
        ObjectNode unknownBr = ((ArrayNode) pack.path("businessRequirements")).addObject();
        unknownBr.put("id", "BR-APPL-UNKNOWN-CODE");
        unknownBr.put("title", "A well-formed three-digit value absent from every Appendix L row is not classified as a valid currency code.");
        unknownBr.put("status", "REVIEW_REQUIRED");
        unknownBr.put("assertionScope", "CODE_RECOGNITION_ONLY");
        unknownBr.put("smeApproved", false);
        unknownBr.putArray("sourceAnchors").add(unknownAnchor.deepCopy());
        for (int index = 0; index < UNKNOWN_WELL_FORMED_CODES.length; index++) {
            ObjectNode expected = MAPPER.createObjectNode().put("outcome", "FAIL").put("reason", "Well-formed but not a listed Appendix L code");
            addChain(pack, "BR-APPL-UNKNOWN-CODE", "UNKNOWN-" + index, "CLASSIFICATION", UNKNOWN_WELL_FORMED_CODES[index], expected, unknownAnchor);
        }

        ObjectNode malformedAnchor = anchor("currency-code-malformed-format", "L-1");
        ObjectNode malformedBr = ((ArrayNode) pack.path("businessRequirements")).addObject();
        malformedBr.put("id", "BR-APPL-FORMAT-INVALID");
        malformedBr.put("title", "Element 20 / Appendix L values must be exactly three ASCII digits; missing, mis-sized, non-numeric, signed, padded, or Unicode-digit values fail format.");
        malformedBr.put("status", "REVIEW_REQUIRED");
        malformedBr.put("assertionScope", "FORMAT_ONLY");
        malformedBr.put("smeApproved", false);
        malformedBr.putArray("sourceAnchors").add(malformedAnchor.deepCopy());
        for (int index = 0; index < MALFORMED_VALUES.length; index++) {
            ObjectNode expected = MAPPER.createObjectNode().put("outcome", "FAIL").put("reason", "Currency Code must be exactly three ASCII digits (n3, fixed length)");
            addChain(pack, "BR-APPL-FORMAT-INVALID", "MALFORMED-" + index, "CLASSIFICATION", MALFORMED_VALUES[index], expected, malformedAnchor);
        }

        ObjectNode summary = pack.putObject("summary");
        summary.put("businessRequirements", pack.path("businessRequirements").size());
        summary.put("testScenarios", pack.path("testScenarios").size());
        summary.put("testCases", pack.path("testCases").size());
        summary.put("testData", pack.path("testData").size());
        summary.put("positiveCodeChains", catalog.codes().size());
        summary.put("networkScopeConflictChains", conflictCodes.size());
        summary.put("unknownWellFormedNegatives", UNKNOWN_WELL_FORMED_CODES.length);
        summary.put("malformedFormatNegatives", MALFORMED_VALUES.length);

        Files.createDirectories(OUTPUT.getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(OUTPUT.toFile(), pack);
        System.out.printf("ValidCodes=%d Rows=%d BR=%d TS=%d TC=%d TD=%d Conflicts=%d%n", catalog.codes().size(), catalog.rows().size(),
                pack.path("businessRequirements").size(), pack.path("testScenarios").size(),
                pack.path("testCases").size(), pack.path("testData").size(), conflictCodes.size());
    }

    private static void addChain(ObjectNode pack, String brId, String suffix, String mode,
                                  String input, ObjectNode expected, ObjectNode anchor) {
        String tsId = "TS-APPL-" + suffix;
        String tcId = "TC-APPL-" + suffix;
        String tdId = "TD-APPL-" + suffix;
        ObjectNode ts = ((ArrayNode) pack.path("testScenarios")).addObject();
        ts.put("id", tsId).put("status", "REVIEW_REQUIRED");
        ts.putArray("requirementIds").add(brId);
        ObjectNode testCase = ((ArrayNode) pack.path("testCases")).addObject();
        testCase.put("id", tcId).put("expectedOutcome", expected.path("outcome").asText()).put("status", "REVIEW_REQUIRED");
        testCase.putArray("scenarioIds").add(tsId);
        testCase.putArray("testDataIds").add(tdId);
        ObjectNode data = ((ArrayNode) pack.path("testData")).addObject();
        data.put("id", tdId).put("coversBr", brId).put("producer", "TEST_SOLUTION").put("readiness", "EXECUTABLE");
        data.put("fixtureKind", "APPENDIX_L_CODE_CLASSIFICATION_HARNESS").put("expectedValidation", expected.path("outcome").asText());
        data.putArray("testCaseIds").add(tcId);
        data.putArray("sourceAnchors").add(anchor.deepCopy());
        ObjectNode payload = data.putObject("payload").put("mode", mode);
        if (input == null) {
            payload.putNull("currencyCode");
        } else {
            payload.put("currencyCode", input);
        }
        data.set("expected", expected);
        for (ObjectNode artifact : new ObjectNode[]{ts, testCase}) artifact.putArray("sourceAnchors").add(anchor.deepCopy());
    }

    private static ObjectNode anchor(String rule, String pageAnchor) {
        return MAPPER.createObjectNode().put("specification", "ATL105").put("version", "2026-3")
                .put("section", "Appendix " + pageAnchor).put("segment", "100,105,119,103,111").put("element", "20").put("rule", rule);
    }
}
