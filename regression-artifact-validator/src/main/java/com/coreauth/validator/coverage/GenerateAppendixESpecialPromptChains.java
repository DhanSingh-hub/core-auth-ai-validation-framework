package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

/** Builds independent validator-harness chains for Appendix E special prompts with detailed family contracts. */
public final class GenerateAppendixESpecialPromptChains {
    private static final Set<String> SUPPORTED_PROMPTS = Set.of(
            "901", "902", "903", "904", "905", "981", "990", "995", "996");
    private static final Map<String, String> VALIDATOR_BY_PROMPT = Map.of(
            "901", "ProprietaryDataLoadPayloadValidator+Segment118PayloadValidator",
            "902", "ProprietaryDataLoadPayloadValidator+Segment118PayloadValidator",
            "903", "ProprietaryDataLoadPayloadValidator+Segment118PayloadValidator",
            "904", "ProprietaryDataLoadPayloadValidator+Segment118PayloadValidator",
            "905", "ProprietaryDataLoadPayloadValidator+Segment118PayloadValidator",
            "981", "Segment109PayloadValidator",
            "990", "TotalsRequestPayloadValidator",
            "995", "Segment109PayloadValidator",
            "996", "Segment109PayloadValidator");

    private GenerateAppendixESpecialPromptChains() {
    }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path testJson = Atl105Paths.testJson();
        JsonNode source = mapper.readTree(testJson.resolve("special-transaction-type-br-baseline.json").toFile());
        ArrayNode brs = mapper.createArrayNode();
        ArrayNode scenarios = mapper.createArrayNode();
        ArrayNode cases = mapper.createArrayNode();
        ArrayNode testData = mapper.createArrayNode();
        ArrayNode blocked = mapper.createArrayNode();

        for (JsonNode sourceBr : source.path("businessRequirements")) {
            String code = sourceBr.path("title").asText();
            String id = sourceBr.path("id").asText();
            String prompt = promptCode(id);
            if (prompt == null) continue;
            brs.add(sourceBr.deepCopy());
            if (!SUPPORTED_PROMPTS.contains(prompt)) {
                ObjectNode item = blocked.addObject();
                item.put("promptCode", prompt);
                item.put("businessRequirementId", id);
                item.put("status", "REVIEW_REQUIRED");
                item.put("reason", "Appendix E supplies an operation label but the current source package does not establish an executable message-family mapping and expected payload contract.");
                continue;
            }
            addChain(mapper, sourceBr, prompt, scenarios, cases, testData);
        }

        ObjectNode output = mapper.createObjectNode();
        output.put("packageId", "ATL105-APPENDIX-E-SPECIAL-PROMPT-CHAINS");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("producer", "TEST_SOLUTION");
        output.put("aiArtifactsIncluded", false);
        output.put("readiness", "PARTIAL_VALIDATOR_HARNESS_COVERAGE_NOT_CERTIFICATION");
        output.put("policy", "TD payloads are synthetic structured validator-harness inputs. Passing a segment validator confirms only that validator's documented scope; it does not certify the business operation, external system, converter, receipt output, or all source-derived semantics.");
        output.set("businessRequirements", brs);
        output.set("testScenarios", scenarios);
        output.set("testCases", cases);
        output.set("testData", testData);
        output.set("blockedPrompts", blocked);
        ObjectNode summary = output.putObject("summary");
        summary.put("appendixSpecialPromptCount", 11);
        summary.put("validatorHarnessChains", scenarios.size());
        summary.put("testCases", cases.size());
        summary.put("independentSyntheticTDs", testData.size());
        summary.put("reviewBlockedPrompts", blocked.size());
        summary.put("converterCertified", false);
        summary.put("businessCertified", false);

        mapper.writerWithDefaultPrettyPrinter().writeValue(
                testJson.resolve("appendix-e-special-prompt-chain-package.json").toFile(), output);
        System.out.printf("specialPrompts=%d validatorChains=%d TC=%d TD=%d blocked=%d certification=false%n",
                11, scenarios.size(), cases.size(), testData.size(), blocked.size());
    }

    private static void addChain(ObjectMapper mapper, JsonNode br, String prompt, ArrayNode scenarios,
                                 ArrayNode cases, ArrayNode testData) {
        String suffix = prompt;
        String brId = br.path("id").asText();
        String tsId = "TS-SEG100-APPE-SPECIAL-" + suffix;
        String tcId = "TC-SEG100-APPE-SPECIAL-" + suffix + "-VALID";
        String tdId = "TD-SEG100-APPE-SPECIAL-" + suffix + "-VALID";
        ArrayNode sourceAnchors = sourceAnchorsForPrompt(mapper, br.path("sourceAnchors"), prompt);

        ObjectNode ts = scenarios.addObject();
        ts.put("id", tsId);
        ts.putArray("requirementIds").add(brId);
        ts.put("title", "Validate the source-documented message-family payload for special Prompt Code " + prompt + ".");
        ts.put("status", "VALIDATOR_HARNESS_SCOPE_ONLY");
        ts.set("sourceAnchors", sourceAnchors.deepCopy());

        ObjectNode tc = cases.addObject();
        tc.put("id", tcId);
        tc.putArray("scenarioIds").add(tsId);
        tc.putArray("testDataIds").add(tdId);
        tc.put("expectedOutcome", "PASS");
        tc.put("status", "VALIDATOR_HARNESS_SCOPE_ONLY");
        tc.put("validator", VALIDATOR_BY_PROMPT.get(prompt));
        tc.put("scope", "Schema/message-family validation only; not operation-level converter certification.");
        tc.set("sourceAnchors", sourceAnchors.deepCopy());

        ObjectNode td = testData.addObject();
        td.put("id", tdId);
        td.putArray("testCaseIds").add(tcId);
        td.put("producer", "TEST_SOLUTION");
        td.put("readiness", "EXECUTABLE");
        td.put("fixtureKind", "SPECIAL_PROMPT_VALIDATOR_HARNESS");
        td.put("converterReady", false);
        td.put("expectedValidation", "PASS");
        td.put("validator", VALIDATOR_BY_PROMPT.get(prompt));
        td.set("sourceAnchors", sourceAnchors.deepCopy());
        td.set("payload", payloadFor(mapper, prompt));
    }

    private static ArrayNode sourceAnchorsForPrompt(ObjectMapper mapper, JsonNode original, String prompt) {
        ArrayNode anchors = mapper.createArrayNode();
        if (original.isArray()) original.forEach(anchor -> anchors.add(anchor.deepCopy()));
        String section;
        String segment;
        String rule;
        switch (prompt) {
            case "901" -> { section = "12.16.1"; segment = "118"; rule = "prompt-code-901-custom-receipt-text"; }
            case "902" -> { section = "12.16.2"; segment = "118"; rule = "prompt-code-902-dynamic-card-table"; }
            case "903" -> { section = "12.16.3"; segment = "118"; rule = "prompt-code-903-site-configuration"; }
            case "904" -> { section = "12.16.4"; segment = "118"; rule = "prompt-code-904-host-discount"; }
            case "905" -> { section = "12.16.5"; segment = "118"; rule = "prompt-code-905-fuel-volume"; }
            case "981" -> { section = "10.11.1"; segment = "109"; rule = "electronic-mail-retrieval"; }
            case "990" -> { section = "11.4.1.1"; segment = "105"; rule = "totals-request-prompt-code"; }
            case "995" -> { section = "10.11.2"; segment = "109"; rule = "electronic-mail-submission"; }
            case "996" -> { section = "10.11.1"; segment = "109"; rule = "proprietary-electronic-mail-retrieval"; }
            default -> throw new IllegalArgumentException("No detailed source anchor for prompt " + prompt);
        }
        ObjectNode operationAnchor = anchors.addObject();
        operationAnchor.put("specification", "ATL105");
        operationAnchor.put("version", "2026-3");
        operationAnchor.put("section", section);
        operationAnchor.put("segment", segment);
        operationAnchor.put("rule", rule);
        return anchors;
    }

    private static ObjectNode payloadFor(ObjectMapper mapper, String prompt) {
        return switch (prompt) {
            case "901", "902", "904" -> proprietaryLoadResponse(mapper, prompt);
            case "903", "905" -> proprietaryLoadRequest(mapper, prompt);
            case "981", "995", "996" -> electronicMailRequest(mapper, prompt);
            case "990" -> totalsRequest(mapper);
            default -> throw new IllegalArgumentException("No validator harness for prompt " + prompt);
        };
    }

    private static ObjectNode proprietaryLoadRequest(ObjectMapper mapper, String prompt) {
        ObjectNode payload = mapper.createObjectNode();
        ObjectNode message = payload.putObject("Proprietary Data Load Request");
        message.put("MessageType", "ATL105");
        message.put("NumSegments", "1");
        ObjectNode segment = addProprietaryCore(message.putObject("Proprietary Data Load Segment"), prompt);
        if ("903".equals(prompt)) {
            segment.put("SiteConfigurationData", "SYNTHETIC-SITE-CONFIG-DATA");
            segment.put("BlockNumber", "001");
        } else {
            segment.put("FuelVolumeData", "SYNTHETIC-FUEL-VOLUME-DATA");
        }
        return payload;
    }

    private static ObjectNode proprietaryLoadResponse(ObjectMapper mapper, String prompt) {
        ObjectNode payload = mapper.createObjectNode();
        ObjectNode message = payload.putObject("Proprietary Data Load Response");
        message.put("ResponseCode", "T");
        message.put("DownloadIndicator", "0");
        message.put("InitiationDate", "260930");
        message.put("InitiationTime", "1530");
        message.put("SequenceNumber", "000001");
        ObjectNode segment = addProprietaryCore(message.putObject("Proprietary Data Load Segment"), prompt);
        if ("901".equals(prompt)) {
            segment.put("StartDate", "20260101");
            segment.put("StartTime", "0000");
            segment.put("EndDate", "20261231");
            segment.put("EndTime", "2359");
            segment.put("NumberOfReceiptTextLines", "01");
            segment.putArray("ReceiptTextLines").addObject()
                    .put("ReceiptTextDataLength", "05").put("ReceiptTextData", "HELLO");
        } else if ("902".equals(prompt)) {
            segment.put("CardTableData", "SYNTHETIC-CARD-TABLE-DATA");
        } else {
            segment.put("NumberOfDiscounts", "01");
            ObjectNode discount = segment.putArray("Discounts").addObject();
            discount.put("StartDate", "20260101");
            discount.put("StartTime", "0000");
            discount.put("EndDate", "20261231");
            discount.put("EndTime", "2359");
            discount.put("BuyPassCardType", "0001");
            discount.put("CardBinRangeBeginning", "000000000001");
            discount.put("CardBinRangeEnding", "000000000999");
            discount.put("ProductCode", "001");
            discount.put("ProductDiscountAmount", "00100");
            discount.put("DiscountProductCode", "941");
            discount.put("DiscountQuantityLimit", "001");
            discount.put("DiscountProgramDescription", "SYNTHETIC DISC!");
        }
        return payload;
    }

    private static ObjectNode addProprietaryCore(ObjectNode segment, String prompt) {
        segment.put("SegmentType", "118");
        segment.put("SegmentLength", "0100");
        segment.put("SequenceNumber", "000001");
        segment.put("InformationByte", "0");
        segment.put("TerminalIdentifier", "TERM001A12345");
        segment.put("PromptCode", prompt);
        segment.put("PromptCodePending", "0902");
        segment.put("DeviceCardTableVersion", "00001000020000300004000050000600007");
        segment.put("CardTableLoadVersion", "00000000000000000000000000000000000");
        segment.put("CardTableType", "0002");
        segment.put("LoadControlKey", "SYNTHETICLOADCONTROLKEY");
        segment.put("HostDiscountTimestamp", "202609261200");
        return segment;
    }

    private static ObjectNode electronicMailRequest(ObjectMapper mapper, String prompt) {
        ObjectNode payload = mapper.createObjectNode();
        ObjectNode message = payload.putObject("Electronic Mail Request");
        message.put("MessageType", "ATL105");
        message.put("NumSegments", "01");
        ObjectNode segment = message.putObject("Electronic Mail Data Segment");
        segment.put("SegmentType", "109");
        segment.put("SegmentLength", "060");
        segment.put("InformationByte", "0");
        segment.put("TerminalIdentifier", "SYNTHETIC01");
        segment.put("PromptCode", prompt);
        segment.put("BlockNumber", "001");
        segment.put("SequenceNumber", "000001");
        segment.put("TextDataLength", "003");
        segment.put("TextData", "ABC");
        return payload;
    }

    private static ObjectNode totalsRequest(ObjectMapper mapper) {
        ObjectNode payload = mapper.createObjectNode();
        ObjectNode message = payload.putObject("Totals Request");
        message.put("MessageType", "ATL105");
        message.put("NumSegments", "01");
        ObjectNode segment = message.putObject("Totals Data Segment");
        segment.put("SegmentType", "105");
        segment.put("SegmentLength", "106");
        segment.put("InformationByte", "0");
        segment.put("TerminalID", "TL500001");
        segment.put("PromptCode", "990");
        segment.put("EmployeeNumber", "");
        segment.put("Password", "");
        segment.put("TotalsDate", "333333");
        segment.put("HardwareVersion", "HW01");
        segment.put("SoftwareVersion", "SW010203");
        segment.put("FirmwareVersion", "FW010203");
        segment.put("SequenceNumber", "000123");
        segment.put("CurrencyCode", "");
        segment.put("GrandTotal", "00012550");
        var categories = segment.putArray("CategoryTotals");
        categories.addObject().put("CardLabel", "CC").put("CardTypeTotalCount", "00003").put("CardTypeTotalAmount", "00010050");
        categories.addObject().put("CardLabel", "DB").put("CardTypeTotalCount", "00001").put("CardTypeTotalAmount", "00002500");
        return payload;
    }

    private static String promptCode(String brId) {
        if (!brId.startsWith("BR-TX9-PROMPT-")) return null;
        return brId.substring("BR-TX9-PROMPT-".length());
    }

    private static ArrayNode array(ObjectNode object, String property) {
        JsonNode current = object.path(property);
        if (current.isArray()) return (ArrayNode) current;
        ArrayNode created = object.arrayNode();
        object.set(property, created);
        return created;
    }
}
