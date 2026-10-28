package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.AppendixKTableCatalog;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class GenerateAppendixKTableCatalogChains {
    public static final Path OUTPUT = Path.of(
            "specifications/ATL105/test-output/test-json/appendices/appendix-k-segment-100-coverage.json");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String TABLE_BR_PREFIX = "BR-SEG100-APPK-ID-";
    private static final String TABLE_TS_PREFIX = "TS-SEG100-APPK-ID-";
    private static final String TABLE_TC_PREFIX = "TC-SEG100-APPK-ID-";
    private static final String TABLE_TD_PREFIX = "TD-SEG100-APPK-ID-";
    private static final String BOUNDARY_BR = "BR-SEG100-APPK-ID-BOUNDARY";
    private static final String BOUNDARY_TS = "TS-SEG100-APPK-ID-BOUNDARY";
    private static final String BOUNDARY_TC = "TC-SEG100-APPK-ID-BOUNDARY";
    private static final String BOUNDARY_TD_PREFIX = "TD-SEG100-APPK-ID-BOUNDARY-";

    private GenerateAppendixKTableCatalogChains() {
    }

    public static void main(String[] args) throws Exception {
        ObjectNode pack = (ObjectNode) MAPPER.readTree(OUTPUT.toFile());
        AppendixKTableCatalog catalog = new AppendixKTableCatalog();
        ArrayNode requirements = ensureArray(pack, "businessRequirements");
        ArrayNode scenarios = ensureArray(pack, "testScenarios");
        ArrayNode cases = ensureArray(pack, "testCases");
        ArrayNode testData = ensureArray(pack, "testData");

        removeGenerated(requirements, TABLE_BR_PREFIX, BOUNDARY_BR);
        removeGenerated(scenarios, TABLE_TS_PREFIX, BOUNDARY_TS);
        removeGenerated(cases, TABLE_TC_PREFIX, BOUNDARY_TC);
        removeGenerated(testData, TABLE_TD_PREFIX, "", BOUNDARY_TD_PREFIX);

        pack.put("applicability", "Appendix K defines the layouts selected by Element 116 and carried in Element 118 of response-only Segment 112. Element 115 controls whether Segment 112 follows the Financial Transaction Response.");
        updateCoreRequirement(requirements, "BR-SEG100-APPK-FLAG",
                "A Financial Transaction Response shall set Element 115 consistently with whether response-only Segment 112 follows.",
                "FINANCIAL-RESPONSE", "115", "additional-information-segment-flag");
        updateCoreRequirement(requirements, "BR-SEG100-APPK-TABLE-LAYOUT",
                "Segment 112 Additional Information selectors shall resolve to the source-defined Appendix K layouts for Elements 116 and 118.",
                "112", "116,118", "additional-information-table-layout");
        updateCoreRequirement(requirements, "BR-SEG100-APPK-BALANCE",
                "Appendix K selector 001 identifies the Balance Information layout carried in Element 118 of Segment 112.",
                "112", "116,118", "balance-information-table-001");
        updateCoreRequirement(requirements, "BR-SEG100-APPK-AVS-NVS",
                "Appendix K selector 003 identifies the AVS/NVS response layout carried in Element 118 of Segment 112.",
                "112", "116,118", "avs-nvs-table-003");
        updateCoreRequirement(requirements, "BR-SEG100-APPK-CARD-VERIFICATION",
                "Appendix K selector 004 identifies the Card Verification Value response layout carried in Element 118 of Segment 112.",
                "112", "116,118", "card-verification-table-004");

        for (var entry : catalog.entries().entrySet()) {
            if (catalog.classify(entry.getKey()).disposition() != AppendixKTableCatalog.Disposition.ASSIGNED) continue;
            String code = entry.getKey();
            String brId = TABLE_BR_PREFIX + code;
            String tsId = TABLE_TS_PREFIX + code;
            String tcId = TABLE_TC_PREFIX + code;
            String tdId = TABLE_TD_PREFIX + code;
            ObjectNode anchor = anchor("table-id-" + code);

            ObjectNode br = requirements.addObject();
            br.put("id", brId);
            br.put("title", "Appendix K selector " + code + " identifies " + entry.getValue() + " in Segment 112.");
            br.put("selector", code);
            br.put("layoutAssertionScope", "TABLE_ID_RECOGNITION_ONLY");
            br.put("status", "REVIEW_REQUIRED");
            br.put("smeApproved", false);
            br.putArray("sourceAnchors").add(anchor.deepCopy());

            addScenario(scenarios, tsId, brId, anchor, "REVIEW_REQUIRED");
            addCase(cases, tcId, tsId, tdId, anchor, "PASS", "REVIEW_REQUIRED");
            addData(testData, tdId, brId, tcId, anchor, "ASSIGNED", entry.getValue(), "EXECUTABLE", "PASS");
        }

        ObjectNode boundaryAnchor = anchor("reserved-and-unlisted-table-id-boundary");
        ObjectNode boundaryBr = requirements.addObject();
        boundaryBr.put("id", BOUNDARY_BR);
        boundaryBr.put("title", "Appendix K table selector classification distinguishes the reserved 002 value and unlisted values from assigned selectors.");
        boundaryBr.put("layoutAssertionScope", "TABLE_ID_CLASSIFICATION_BOUNDARY_ONLY");
        boundaryBr.put("status", "REVIEW_REQUIRED");
        boundaryBr.put("smeApproved", false);
        boundaryBr.putArray("sourceAnchors").add(boundaryAnchor.deepCopy());
        List<String> boundaryValues = List.of("002", "014", "015", "033");
        addScenario(scenarios, BOUNDARY_TS, BOUNDARY_BR, boundaryAnchor, "REVIEW_REQUIRED");
        addCase(cases, BOUNDARY_TC, BOUNDARY_TS,
                boundaryValues.stream().map(value -> BOUNDARY_TD_PREFIX + value).toList(),
                boundaryAnchor, "REVIEW", "REVIEW_REQUIRED");
        for (String value : boundaryValues) {
            String expectedDisposition = value.equals("002") ? "RESERVED" : "UNLISTED";
            addData(testData, BOUNDARY_TD_PREFIX + value, BOUNDARY_BR, BOUNDARY_TC,
                    boundaryAnchor, expectedDisposition, null, "REVIEW_REQUIRED", "REVIEW");
        }

        ObjectNode catalogSummary = pack.putObject("tableIdCatalog");
        catalogSummary.put("sourceSelectorCount", catalog.entries().size());
        catalogSummary.put("assignedSelectorCount", catalog.entries().size() - 1);
        catalogSummary.put("reservedSelectorIds", "002");
        catalogSummary.put("unlistedGapIds", "014,015,033");
        catalogSummary.put("classificationScope", "TABLE_ID_ONLY");
        catalogSummary.put("layoutPredicateTableIds", "001,003,004");
        catalogSummary.put("remainingAssignedLayouts", 40);
        catalogSummary.put("element118LayoutSemanticsValidated", false);
        pack.put("testDataStatus", "43 assigned Appendix K selectors have executable recognition-harness TDs. Bounded Element 118 predicates exist for Tables 001, 003, and 004; 004 requires network context for code meaning. Full table semantics and complete Segment 112 response fixtures remain incomplete.");
        ArrayNode remaining = pack.putArray("remaining");
        remaining.add("Derive and independently validate the 40 remaining assigned Table IDs' Element 118 layouts, data lengths, and applicable conditions; expand Tables 001, 003, and 004 beyond bounded representation predicates.");
        remaining.add("Add physical response-envelope fixtures for Segment 112, Element 115 flag behavior, and repeated Element 116/117/118 triads.");
        remaining.add("Resolve issuer/network-specific, external-code, and unresolved table semantics; keep them REVIEW_REQUIRED until evidence is recorded.");

        MAPPER.writerWithDefaultPrettyPrinter().writeValue(OUTPUT.toFile(), pack);
        System.out.printf("AppendixK selectors=%d assigned=%d BR=%d TS=%d TC=%d TD=%d%n",
                catalog.entries().size(), catalog.entries().size() - 1, requirements.size(), scenarios.size(),
                cases.size(), testData.size());
    }

    private static ArrayNode ensureArray(ObjectNode pack, String name) {
        JsonNode existing = pack.path(name);
        if (existing.isArray()) return (ArrayNode) existing;
        return pack.putArray(name);
    }

    private static void removeGenerated(ArrayNode artifacts, String prefix, String exactId) {
        for (int index = artifacts.size() - 1; index >= 0; index--) {
            String id = artifacts.get(index).path("id").asText();
            if (id.startsWith(prefix) || id.equals(exactId)) artifacts.remove(index);
        }
    }

    private static void removeGenerated(ArrayNode artifacts, String prefix, String firstExactId, String secondPrefix) {
        for (int index = artifacts.size() - 1; index >= 0; index--) {
            String id = artifacts.get(index).path("id").asText();
            if (id.startsWith(prefix) || id.equals(firstExactId) || id.startsWith(secondPrefix)) artifacts.remove(index);
        }
    }

    private static void updateCoreRequirement(ArrayNode requirements, String id, String title,
                                              String segment, String element, String rule) {
        for (JsonNode node : requirements) {
            if (!node.path("id").asText().equals(id)) continue;
            ObjectNode requirement = (ObjectNode) node;
            requirement.put("title", title);
            ObjectNode sourceAnchor = (ObjectNode) requirement.path("sourceAnchor");
            sourceAnchor.put("segment", segment).put("element", element).put("rule", rule);
            return;
        }
        throw new IllegalStateException("Missing existing Appendix K requirement: " + id);
    }

    private static void addScenario(ArrayNode scenarios, String id, String brId, ObjectNode anchor, String status) {
        ObjectNode scenario = scenarios.addObject();
        scenario.put("id", id).put("status", status);
        scenario.putArray("requirementIds").add(brId);
        scenario.putArray("sourceAnchors").add(anchor.deepCopy());
    }

    private static void addCase(ArrayNode cases, String id, String scenarioId, String testDataId,
                                ObjectNode anchor, String expectedOutcome, String status) {
        addCase(cases, id, scenarioId, List.of(testDataId), anchor, expectedOutcome, status);
    }

    private static void addCase(ArrayNode cases, String id, String scenarioId, List<String> testDataIds,
                                ObjectNode anchor, String expectedOutcome, String status) {
        ObjectNode testCase = cases.addObject();
        testCase.put("id", id).put("expectedOutcome", expectedOutcome).put("status", status);
        testCase.putArray("scenarioIds").add(scenarioId);
        ArrayNode linkedData = testCase.putArray("testDataIds");
        testDataIds.forEach(linkedData::add);
        testCase.putArray("sourceAnchors").add(anchor.deepCopy());
    }

    private static void addData(ArrayNode testData, String id, String brId, String testCaseId,
                                ObjectNode anchor, String expectedDisposition, String title,
                                String readiness, String expectedValidation) {
        ObjectNode data = testData.addObject();
        data.put("id", id).put("coversBr", brId).put("producer", "TEST_SOLUTION").put("readiness", readiness);
        data.put("fixtureKind", "APPENDIX_K_TABLE_ID_CLASSIFICATION_HARNESS");
        data.put("expectedValidation", expectedValidation);
        data.putArray("testCaseIds").add(testCaseId);
        data.putArray("sourceAnchors").add(anchor.deepCopy());
        data.putObject("payload").put("mode", "TABLE_ID_CLASSIFICATION").put("segmentType", "112")
                .put("element", "116").put("tableId", id.substring(id.lastIndexOf('-') + 1));
        ObjectNode expected = data.putObject("expected");
        expected.put("disposition", expectedDisposition);
        if (title != null) expected.put("title", title);
        expected.put("layoutSemantics", "NOT_EVALUATED");
    }

    private static ObjectNode anchor(String rule) {
        return MAPPER.createObjectNode().put("specification", "ATL105").put("version", "2026-3")
                .put("section", "Appendix K").put("segment", "112").put("element", "116").put("rule", rule);
    }
}