package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Combines standard financial starter mappings and special-flow draft chains for all Appendix G codes. */
public final class GenerateCombinedTransactionTypeMappingMatrix {
    private GenerateCombinedTransactionTypeMappingMatrix() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path testJson = Atl105Paths.testJson();
        JsonNode baseline = mapper.readTree(testJson.resolve("segment-100-all-23-transaction-type-training-baseline.json").toFile());
        JsonNode standard = mapper.readTree(testJson.resolve("segment-100-code-flow-br-ts-tc-testdata.json").toFile());
        JsonNode coverage = mapper.readTree(testJson.resolve("segment-100-transaction-type-coverage.json").toFile());
        JsonNode special = mapper.readTree(testJson.resolve("special-transaction-type-br-baseline.json").toFile());
        JsonNode specialChains = mapper.readTree(testJson.resolve("special-transaction-type-br-ts-tc-td-draft-package.json").toFile());
        JsonNode lifecycle = mapper.readTree(testJson.resolve("segment-100-multistep-flow-catalog.json").toFile());

        Map<String, JsonNode> standardByCode = index(standard.path("transactionTypeArtifacts"), "code");
        Map<String, JsonNode> flowByCode = index(coverage.path("transactionTypes"), "code");
        Map<String, List<String>> lifecycleRolesByCode = lifecycleRoles(coverage.path("multiStepScenarios"));
        Map<String, List<JsonNode>> specialBrByCode = indexMany(special.path("businessRequirements"), "transactionTypeCode");
        Map<String, List<JsonNode>> specialChainsByCode = indexMany(specialChains.path("chains"), "transactionTypeCode");
        Map<String, JsonNode> specialBrById = index(special.path("businessRequirements"), "id");
        Map<String, Integer> specialCaseCount = counts(specialChains.path("testCases"), "businessRequirementId");
        ArrayNode rows = mapper.createArrayNode();
        Map<String, Integer> matrixCounts = new TreeMap<>();
        int totalBR = 0;
        int totalTS = 0;
        int totalTC = 0;
        int aiFixtureRefs = 0;
        int sharedLifecycleRefs = 0;
        int specialDraftData = 0;
        int specialBlockedBr = 0;
        int mappingEdgeCount = 0;

        for (JsonNode type : baseline.path("transactionTypes")) {
            String code = type.path("code").asText();
            ObjectNode row = rows.addObject();
            row.put("transactionTypeCode", code);
            row.put("transactionTypeName", type.path("name").asText());
            row.put("transactionClass", type.path("class").asText());
            row.put("sourceAnchor", type.path("sourceAnchor").asText());
            JsonNode flow = flowByCode.get(code);
            row.put("singleStepEligible", flow != null && flow.path("singleStep").asBoolean());
            row.put("multiStepEligible", flow != null && flow.path("multiStep").asBoolean());
            row.set("multiStepLifecycleRoles", mapper.valueToTree(lifecycleRolesByCode.getOrDefault(code, List.of())));
            JsonNode standardEntry = standardByCode.get(code);
            if (standardEntry != null) {
                row.put("scope", "STANDARD_FINANCIAL");
                row.put("status", "STARTER_CHAIN_WITH_AI_INPUT_FIXTURES");
                ArrayNode brs = row.putArray("businessRequirements");
                brs.add(standardEntry.path("businessRequirement").deepCopy());
                ArrayNode scenarios = row.putArray("testScenarios");
                scenarios.add(standardEntry.path("testScenario").deepCopy());
                ArrayNode cases = row.putArray("testCases");
                ArrayNode dataRefs = row.putArray("testDataReferences");
                ArrayNode mappingRows = row.putArray("brTsTcTdMappings");
                for (JsonNode testCase : standardEntry.path("testCases")) {
                    cases.add(testCase.deepCopy());
                    mappingEdgeCount++;
                    ObjectNode mapping = mappingRows.addObject();
                    mapping.put("businessRequirementId", standardEntry.path("businessRequirement").path("id").asText());
                    mapping.put("testScenarioId", standardEntry.path("testScenario").path("id").asText());
                    mapping.put("testCaseId", testCase.path("id").asText());
                    mapping.put("mappingStatus", "STARTER_MAPPING_REVIEW_REQUIRED");
                    ArrayNode mappingData = mapping.putArray("testDataReferences");
                    JsonNode file = testCase.path("testDataFile");
                    JsonNode catalog = testCase.path("testDataCatalog");
                    if (file.isTextual()) {
                        ObjectNode ref = dataRefs.addObject();
                        ref.put("testCaseId", testCase.path("id").asText());
                        ref.put("kind", "AI_INPUT_FIXTURE_REFERENCE");
                        ref.put("path", "test-input/ai-solution/test-data/segment-100/transaction-types/" + file.asText());
                        ref.put("independentTestSolutionData", false);
                        mappingData.add(ref.deepCopy());
                        aiFixtureRefs++;
                    }
                    if (catalog.isTextual()) {
                        ObjectNode ref = dataRefs.addObject();
                        ref.put("testCaseId", testCase.path("id").asText());
                        ref.put("kind", "SHARED_TEST_SOLUTION_LIFECYCLE_CATALOG_REFERENCE");
                        ref.put("path", "test-output/test-json/" + catalog.asText());
                        ref.put("independentTestSolutionData", true);
                        mappingData.add(ref.deepCopy());
                        sharedLifecycleRefs++;
                    }
                }
                row.put("brCount", brs.size());
                row.put("tsCount", scenarios.size());
                row.put("tcCount", cases.size());
                row.put("tdReferenceCount", dataRefs.size());
                row.put("tdDraftStubCount", 0);
                row.put("executableIndependentTdCount", 0);
                row.put("unlinkedTestCaseCount", cases.size() - dataRefs.size());
                row.put("mappingPolicy", "The package links BR->TS->TC; direct per-code files are AI inputs, while lifecycle refs reuse the shared Test Solution catalog. This is not a per-code independent JSON fixture set.");
                totalBR += brs.size();
                totalTS += scenarios.size();
                totalTC += cases.size();
                matrixCounts.put(code, brs.size());
            } else {
                row.put("scope", "SPECIAL_NONFINANCIAL");
                row.put("status", "DRAFT_CHAIN_REVIEW_REQUIRED");
                ArrayNode brs = row.putArray("businessRequirements");
                ArrayNode scenarios = row.putArray("testScenarios");
                ArrayNode cases = row.putArray("testCases");
                ArrayNode dataRefs = row.putArray("testDataReferences");
                List<JsonNode> codeBrs = specialBrByCode.getOrDefault(code, List.of());
                List<JsonNode> codeChains = specialChainsByCode.getOrDefault(code, List.of());
                Map<String, JsonNode> chainByBr = new HashMap<>();
                for (JsonNode chain : codeChains) chainByBr.put(chain.path("businessRequirementId").asText(), chain);
                ArrayNode mappingRows = row.putArray("brTsTcTdMappings");
                for (JsonNode br : codeBrs) {
                    brs.add(br.deepCopy());
                    JsonNode chain = chainByBr.get(br.path("id").asText());
                    ObjectNode mapping = mappingRows.addObject();
                    mapping.put("businessRequirementId", br.path("id").asText());
                    if (chain == null) {
                        mapping.put("status", "BLOCKED_SOURCE_REVIEW_REQUIRED");
                        mapping.put("blocker", br.path("blocker").asText("Source evidence insufficient for chain derivation."));
                        specialBlockedBr++;
                    } else {
                        mappingEdgeCount++;
                        mapping.put("status", "DRAFT_CHAIN_REVIEW_REQUIRED");
                        mapping.put("testScenarioId", chain.path("testScenarioId").asText());
                        mapping.put("testCaseId", chain.path("testCaseId").asText());
                        mapping.put("testDataId", chain.path("testDataId").asText());
                        mapping.put("testDataDraftPath", "test-output/test-json/special-transaction-type-test-data-drafts/" + chain.path("testDataFile").asText());
                        mapping.put("executable", false);
                        ObjectNode ts = mapper.createObjectNode();
                        ts.put("id", chain.path("testScenarioId").asText());
                        ts.put("businessRequirementId", br.path("id").asText());
                        scenarios.add(ts);
                        ObjectNode tc = mapper.createObjectNode();
                        tc.put("id", chain.path("testCaseId").asText());
                        tc.put("testScenarioId", chain.path("testScenarioId").asText());
                        cases.add(tc);
                        ObjectNode data = dataRefs.addObject();
                        data.put("testDataId", chain.path("testDataId").asText());
                        data.put("path", mapping.path("testDataDraftPath").asText());
                        data.put("kind", "NON_EXECUTABLE_TEST_SOLUTION_DRAFT_STUB");
                        data.put("independentTestSolutionData", true);
                        specialDraftData++;
                    }
                }
                row.put("brCount", brs.size());
                row.put("tsCount", scenarios.size());
                row.put("tcCount", cases.size());
                row.put("tdReferenceCount", dataRefs.size());
                row.put("tdDraftStubCount", dataRefs.size());
                row.put("executableIndependentTdCount", 0);
                row.put("blockedBrCount", brs.size() - scenarios.size());
                if (brs.size() == scenarios.size()) row.put("status", "DRAFT_CHAIN_REVIEW_REQUIRED");
                else row.put("status", "DRAFT_WITH_SOURCE_REVIEW_BLOCKERS");
                row.put("mappingPolicy", "Source-backed draft chains only; every chain and TD stub remains REVIEW_REQUIRED/non-executable.");
                totalBR += brs.size();
                totalTS += scenarios.size();
                totalTC += cases.size();
                matrixCounts.put(code, brs.size());
            }
        }

        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "atl105-combined-23-transaction-type-br-ts-tc-td-mapping-matrix");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("aiArtifactsIncluded", false);
        output.put("policy", "Standard financial starter references and special-flow draft chains are separated by data ownership and execution status. No draft or AI-input fixture is counted as executable independent Test Solution TD coverage.");
        output.put("matrixRowCount", rows.size());
        output.set("businessRequirementCountsByCode", mapper.valueToTree(matrixCounts));
        output.set("transactionTypes", rows);
        output.set("multiStepScenarios", coverage.path("multiStepScenarios").deepCopy());
        ObjectNode flowViews = output.putObject("flowViews");
        flowViews.set("singleStep", singleStepView(rows, mapper));
        flowViews.set("multiStep", multiStepView(rows, coverage, lifecycle, mapper));
        ObjectNode summary = output.putObject("summary");
        summary.put("transactionTypeCodes", rows.size());
        summary.put("businessRequirements", totalBR);
        summary.put("testScenarios", totalTS);
        summary.put("testCases", totalTC);
        summary.put("mappingEdgeCount", mappingEdgeCount);
        summary.put("aiInputFixtureReferences", aiFixtureRefs);
        summary.put("sharedTestSolutionLifecycleCatalogReferences", sharedLifecycleRefs);
        summary.put("specialTestDataDraftStubs", specialDraftData);
        summary.put("sharedLifecycleCatalogTestDataRecords", lifecycle.path("testData").size());
        summary.put("executableIndependentTestDataFixtures", 0);
        summary.put("specialBusinessRequirementsBlocked", specialBlockedBr);
        summary.put("executionReady", false);
        summary.put("reason", "Standard-code payload refs include AI-owned fixtures; special-flow fixtures are draft schema stubs; converter execution and source/SME review are not complete.");
        ObjectNode validation = output.putObject("validation");
        validation.put("allAppendixGCodesPresent", rows.size() == 23);
        validation.put("noDuplicateTransactionCodes", uniqueCodes(rows));
        validation.put("allDraftSpecialChainsHaveBR_TS_TC_TD", specialChainsLinked(rows));
        validation.put("allSpecialTdDraftFilesExist", specialDraftFilesExist(rows, testJson));

        Path matrixFile = testJson.resolve("combined-23-transaction-type-br-ts-tc-td-matrix.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(matrixFile.toFile(), output);
        Files.writeString(testJson.resolve("combined-23-transaction-type-br-ts-tc-td-matrix.md"), markdown(output));
        System.out.printf("codes=%d BR=%d TS=%d TC=%d AIrefs=%d sharedTDrefs=%d draftTD=%d blockedBR=%d sharedLifecycleTDRecords=%d executableIndependentTD=0%n",
                rows.size(), totalBR, totalTS, totalTC, aiFixtureRefs, sharedLifecycleRefs,
                specialDraftData, specialBlockedBr, lifecycle.path("testData").size());
    }

    private static Map<String, JsonNode> index(JsonNode values, String field) {
        Map<String, JsonNode> result = new LinkedHashMap<>();
        for (JsonNode value : values) result.put(value.path(field).asText(), value);
        return result;
    }

    private static Map<String, List<JsonNode>> indexMany(JsonNode values, String field) {
        Map<String, List<JsonNode>> result = new HashMap<>();
        for (JsonNode value : values) result.computeIfAbsent(value.path(field).asText(), ignored -> new ArrayList<>()).add(value);
        return result;
    }

    private static Map<String, Integer> counts(JsonNode values, String linkField) {
        Map<String, Integer> result = new HashMap<>();
        for (JsonNode value : values) for (JsonNode link : value.path(linkField)) result.merge(link.asText(), 1, Integer::sum);
        return result;
    }

    private static Map<String, List<String>> lifecycleRoles(JsonNode scenarios) {
        Map<String, List<String>> result = new HashMap<>();
        for (JsonNode scenario : scenarios) {
            for (JsonNode code : scenario.path("originalCodes")) {
                result.computeIfAbsent(code.asText(), ignored -> new ArrayList<>()).add("ORIGINAL");
            }
            for (JsonNode code : scenario.path("followUpCodes")) {
                result.computeIfAbsent(code.asText(), ignored -> new ArrayList<>()).add("FOLLOW_UP");
            }
        }
        result.replaceAll((code, roles) -> roles.stream().distinct().sorted().toList());
        return result;
    }

    private static ObjectNode singleStepView(JsonNode rows, ObjectMapper mapper) {
        ObjectNode view = mapper.createObjectNode();
        ArrayNode codes = view.putArray("eligibleCodes");
        int financialBr = 0;
        int financialTs = 0;
        int financialTc = 0;
        int aiFixtureRefs = 0;
        int specialBr = 0;
        int specialChains = 0;
        int specialTc = 0;
        int specialTdStubs = 0;
        int blockedBr = 0;
        for (JsonNode row : rows) {
            if (!row.path("singleStepEligible").asBoolean()) continue;
            codes.add(row.path("transactionTypeCode").asText());
            if ("STANDARD_FINANCIAL".equals(row.path("scope").asText())) {
                financialBr += row.path("brCount").asInt();
                financialTs += row.path("tsCount").asInt();
                for (JsonNode testCase : row.path("testCases")) {
                    if (!"single-step".equals(testCase.path("flow").asText())) continue;
                    financialTc++;
                    if (testCase.hasNonNull("testDataFile")) aiFixtureRefs++;
                }
            } else {
                specialBr += row.path("brCount").asInt();
                specialChains += row.path("testScenarios").size();
                specialTc += row.path("testCases").size();
                specialTdStubs += row.path("tdDraftStubCount").asInt();
                blockedBr += row.path("blockedBrCount").asInt();
            }
        }
        view.put("eligibleCodeCount", codes.size());
        view.put("standardFinancialBusinessRequirements", financialBr);
        view.put("standardFinancialTestScenarios", financialTs);
        view.put("standardFinancialSingleStepTestCases", financialTc);
        view.put("aiOwnedTestDataFixtureReferences", aiFixtureRefs);
        view.put("specialFlowDraftBusinessRequirements", specialBr);
        view.put("specialFlowDraftChains", specialChains);
        view.put("specialFlowDraftTestCases", specialTc);
        view.put("specialFlowNonExecutableTestDataStubs", specialTdStubs);
        view.put("blockedSpecialBusinessRequirements", blockedBr);
        view.put("totalBusinessRequirements", financialBr + specialBr);
        view.put("totalTestScenarios", financialTs + specialChains);
        view.put("totalTestCases", financialTc + specialTc);
        view.put("executableIndependentTestDataFixtures", 0);
        return view;
    }

    private static ObjectNode multiStepView(JsonNode rows, JsonNode coverage, JsonNode lifecycle, ObjectMapper mapper) {
        ObjectNode view = mapper.createObjectNode();
        ArrayNode eligible = view.putArray("multiStepEligibleCodes");
        ArrayNode participants = view.putArray("lifecycleParticipantCodes");
        int participatingBr = 0;
        int participatingTs = 0;
        int lifecycleTc = 0;
        int aiFixtureRefs = 0;
        int sharedCatalogRefs = 0;
        for (JsonNode row : rows) {
            if (row.path("multiStepEligible").asBoolean()) eligible.add(row.path("transactionTypeCode").asText());
            if (!row.path("multiStepLifecycleRoles").isArray() || row.path("multiStepLifecycleRoles").isEmpty()) continue;
            participants.add(row.path("transactionTypeCode").asText());
            participatingBr += row.path("brCount").asInt();
            participatingTs += row.path("tsCount").asInt();
            for (JsonNode testCase : row.path("testCases")) {
                if ("single-step".equals(testCase.path("flow").asText())) continue;
                lifecycleTc++;
                if (testCase.hasNonNull("testDataFile")) aiFixtureRefs++;
                if (testCase.hasNonNull("testDataCatalog")) sharedCatalogRefs++;
            }
        }
        view.put("eligibleCodeCount", eligible.size());
        view.put("lifecycleParticipantCodeCount", participants.size());
        view.put("transactionPackageParticipantBrs", participatingBr);
        view.put("transactionPackageParticipantTestScenarios", participatingTs);
        view.put("transactionPackageLifecycleTestCases", lifecycleTc);
        view.put("transactionPackageAiFixtureReferences", aiFixtureRefs);
        view.put("transactionPackageSharedCatalogReferences", sharedCatalogRefs);
        view.put("lifecycleScenarioFamilyCount", coverage.path("multiStepScenarios").size());
        view.put("lifecycleCatalogBusinessRequirements", lifecycle.path("businessRequirements").size());
        view.put("lifecycleCatalogTestScenarios", lifecycle.path("testScenarios").size());
        view.put("lifecycleCatalogTestCases", lifecycle.path("testCases").size());
        view.put("lifecycleCatalogTestData", lifecycle.path("testData").size());
        view.put("independentExecutableFixtureCount", 0);
        view.putArray("knownClassificationNote").add("Transaction types 4 and 6 are included as original sides of purchase-reversal/timeout lifecycle patterns even though their multiStepEligible flag is false.");
        return view;
    }

    private static boolean uniqueCodes(JsonNode rows) {
        java.util.Set<String> codes = new java.util.HashSet<>();
        for (JsonNode row : rows) if (!codes.add(row.path("transactionTypeCode").asText())) return false;
        return true;
    }

    private static boolean specialChainsLinked(JsonNode rows) {
        for (JsonNode row : rows) if ("SPECIAL_NONFINANCIAL".equals(row.path("scope").asText())) {
            for (JsonNode mapping : row.path("brTsTcTdMappings")) {
                if ("DRAFT_CHAIN_REVIEW_REQUIRED".equals(mapping.path("status").asText())
                        && (mapping.path("testScenarioId").asText().isBlank()
                        || mapping.path("testCaseId").asText().isBlank()
                        || mapping.path("testDataId").asText().isBlank())) return false;
            }
        }
        return true;
    }

    private static boolean specialDraftFilesExist(JsonNode rows, Path testJson) {
        for (JsonNode row : rows) for (JsonNode mapping : row.path("brTsTcTdMappings")) {
            String relative = mapping.path("testDataDraftPath").asText("");
            if (!relative.isBlank() && !Files.isRegularFile(testJson.getParent().resolve(relative.replace("test-output/test-json/", "test-json/")))) {
                Path normalized = testJson.resolve("special-transaction-type-test-data-drafts")
                        .resolve(Path.of(relative).getFileName());
                if (!Files.isRegularFile(normalized)) return false;
            }
        }
        return true;
    }

    private static String markdown(JsonNode output) {
        StringBuilder text = new StringBuilder("# Combined Appendix G Transaction-Type Mapping Matrix\n\n")
            .append("| Code | Transaction | Scope | Single-step eligible | Multi-step eligible | Lifecycle role | BR | TS | TC | TD refs/drafts | Blocked BRs | Status |\n|---|---|---|---:|---:|---|---:|---:|---:|---:|---:|---|\n");
        for (JsonNode row : output.path("transactionTypes")) {
            text.append('|').append(row.path("transactionTypeCode").asText()).append('|')
                    .append(row.path("transactionTypeName").asText()).append('|')
                    .append(row.path("scope").asText()).append('|')
                    .append(row.path("singleStepEligible").asBoolean()).append('|')
                    .append(row.path("multiStepEligible").asBoolean()).append('|')
                    .append(join(row.path("multiStepLifecycleRoles"))).append('|')
                    .append(row.path("brCount").asInt()).append('|')
                    .append(row.path("tsCount").asInt()).append('|')
                    .append(row.path("tcCount").asInt()).append('|')
                    .append(row.path("tdReferenceCount").asInt()).append('|')
                    .append(row.path("blockedBrCount").asInt(0)).append('|')
                    .append(row.path("status").asText()).append("|\n");
        }
                JsonNode single = output.path("flowViews").path("singleStep");
                JsonNode multi = output.path("flowViews").path("multiStep");
        JsonNode summary = output.path("summary");
        text.append("\nTotals: **").append(summary.path("transactionTypeCodes").asInt()).append(" codes**, **")
                .append(summary.path("businessRequirements").asInt()).append(" BRs**, **")
                .append(summary.path("testScenarios").asInt()).append(" TSs**, **")
                .append(summary.path("testCases").asInt()).append(" TCs**.\n\n")
                .append("Data distinction: ").append(summary.path("aiInputFixtureReferences").asInt())
                .append(" AI-input fixture refs; ").append(summary.path("sharedTestSolutionLifecycleCatalogReferences").asInt())
                .append(" shared lifecycle catalog refs; ").append(summary.path("specialTestDataDraftStubs").asInt())
                .append(" non-executable special TD stubs; ").append(summary.path("executableIndependentTestDataFixtures").asInt())
                .append(" independent executable fixtures. The shared lifecycle catalog contains ")
                .append(summary.path("sharedLifecycleCatalogTestDataRecords").asInt()).append(" TD records.\n\n")
                .append("## Single-Step Flow View\n\n")
                .append("Eligible codes (").append(single.path("eligibleCodeCount").asInt()).append("): ")
                .append(join(single.path("eligibleCodes"))).append(". Counts: ")
                .append(single.path("totalBusinessRequirements").asInt()).append(" BRs, ")
                .append(single.path("totalTestScenarios").asInt()).append(" TSs, ")
                .append(single.path("totalTestCases").asInt()).append(" TCs; independent executable TD fixtures: 0.\n\n")
                .append("## Multi-Step Lifecycle View\n\n")
                .append("Multi-step-eligible codes (").append(multi.path("eligibleCodeCount").asInt()).append("): ")
                .append(join(multi.path("multiStepEligibleCodes"))).append(". Lifecycle participant codes (")
                .append(multi.path("lifecycleParticipantCodeCount").asInt()).append("): ")
                .append(join(multi.path("lifecycleParticipantCodes"))).append(". The independent lifecycle catalog has ")
                .append(multi.path("lifecycleCatalogBusinessRequirements").asInt()).append(" BRs, ")
                .append(multi.path("lifecycleCatalogTestScenarios").asInt()).append(" TSs, ")
                .append(multi.path("lifecycleCatalogTestCases").asInt()).append(" TCs, and ")
                .append(multi.path("lifecycleCatalogTestData").asInt()).append(" TD records across ")
                .append(multi.path("lifecycleScenarioFamilyCount").asInt()).append(" flow families.\n\n")
                .append("Per-code lifecycle participants have ")
                .append(multi.path("transactionPackageParticipantBrs").asInt()).append(" BRs, ")
                .append(multi.path("transactionPackageParticipantTestScenarios").asInt()).append(" TSs, and ")
                .append(multi.path("transactionPackageLifecycleTestCases").asInt()).append(" lifecycle TCs; their references include ")
                .append(multi.path("transactionPackageAiFixtureReferences").asInt()).append(" AI-input fixtures and ")
                .append(multi.path("transactionPackageSharedCatalogReferences").asInt()).append(" shared lifecycle catalog links.\n\n")
                .append("The eligibility and participation sets differ: code 4 and 6 occur as lifecycle originals despite not being flagged multi-step eligible.\n\n")
                .append("### Lifecycle patterns\n\n| Pattern | Original code(s) | Follow-up code(s) |\n|---|---|---|\n");
            for (JsonNode scenario : output.path("multiStepScenarios")) {
                text.append('|').append(scenario.path("id").asText()).append('|')
                    .append(join(scenario.path("originalCodes"))).append('|')
                    .append(join(scenario.path("followUpCodes"))).append("|\n");
            }
            text.append("\n")
                .append("**Execution ready: false.** AI-owned fixture references and special TD stubs do not count as independent executable coverage.\n");
        return text.toString();
    }

    private static String join(JsonNode values) {
        List<String> items = new ArrayList<>();
        for (JsonNode value : values) items.add(value.asText());
        return String.join(", ", items);
    }
}