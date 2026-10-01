package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.SourceAnchor;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Generates BR/TS/TC/TD matching and both-side traceability evidence. */
public final class GenerateFourLevelTraceabilityMatchReport {
    private static final List<String> SEGMENTS = List.of(
            "100", "101", "102", "103", "104", "105", "108", "109", "111", "113");

    private GenerateFourLevelTraceabilityMatchReport() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path deliveryRoot = Atl105Paths.testInput().resolve(Path.of("ai-solution", "runs", "2026-09-23"));
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        Path outputRoot = args.length == 0
                ? reviewRoot.resolve("four-level-matches") : Path.of(args[0]);
        Files.createDirectories(outputRoot);

        Run2Crosswalk crosswalk = loadCrosswalks(mapper, reviewRoot);
        Path traceability = deliveryRoot.resolve(Path.of("Run2", "traceability_matrix_full.json"));
        Run2TraceabilityAdapter.AdaptedRun adapted = new Run2TraceabilityAdapter().adapt(
                traceability, deliveryRoot.resolve("Run2"), crosswalk);
        JsonNode aiPackage = mapper.valueToTree(adapted.artifactPackage());
        ArtifactIndex ai = ArtifactIndex.fromCanonical(aiPackage);
        ArtifactIndex test = ArtifactIndex.fromFiles(mapper, Atl105Paths.testOutput().resolve("test-json"));
        Map<String, List<String>> aiElementNamesByTestCase = new AiElementNamesLoader().load(traceability);
        Map<String, Map<String, String>> aliasCrosswalkBySegment = new LinkedHashMap<>();

        ObjectNode report = mapper.createObjectNode();
        report.put("artifact", "four-level-ai-test-solution-traceability-match-report");
        report.put("deliveryId", "ATL105-AI-2026-09-23-RUN1-RUN2");
        report.put("matchingPolicy", "BR mappings use the Test Solution crosswalk; TS, TC, and TD matches require shared canonical anchors within the matched BR scope.");
        report.put("coveragePolicy", "Only CONFIRMED BR mappings are eligible for confirmed downstream matches; REVIEW_REQUIRED mappings remain visible but do not count as confirmed.");
        report.put("aiTraceabilitySource", "Run2 traceability_matrix_full.json adapted to the canonical artifact contract");
        report.put("testTraceabilitySource", "Independent Test Solution packages under test-output/test-json");

        ArrayNode matches = report.putArray("businessRequirements");
        ArrayNode summaryByLevel = report.putArray("summaryByLevel");
        int[] totals = new int[12];

        for (Run2Crosswalk.Mapping mapping : crosswalk.mappings()) {
            if (mapping.aiRequirementIds().isEmpty()) continue;
            ObjectNode brMatch = matches.addObject();
            brMatch.put("testRequirementId", mapping.testRequirementId());
            brMatch.put("matchStatus", mapping.matchStatus().name());
            brMatch.put("matchReason", mapping.matchReason());
            brMatch.put("reviewOwner", mapping.reviewOwner() == null ? "" : mapping.reviewOwner());
            brMatch.set("canonicalAnchors", mapper.valueToTree(mapping.sourceAnchors()));

            ArrayNode aiRequirements = brMatch.putArray("aiBusinessRequirements");
            for (String id : mapping.aiRequirementIds()) addById(aiRequirements, ai.requirementsById.get(id));
            ArrayNode testRequirements = brMatch.putArray("testBusinessRequirements");
                List<JsonNode> testRequirementsForMatch = test.requirementsFor(mapping.sourceAnchors());
                testRequirementsForMatch.forEach(testRequirements::add);
                List<JsonNode> aiScenarios = ai.scenariosForRequirements(mapping.aiRequirementIds());
                List<JsonNode> testScenarios = test.scenariosForRequirements(ids(testRequirementsForMatch));
                List<JsonNode> aiTestCases = ai.testCasesForScenarios(ids(aiScenarios));
                List<JsonNode> testTestCases = test.testCasesForScenarios(ids(testScenarios));
                List<JsonNode> aiTestData = ai.testDataForCases(ids(aiTestCases));
                List<JsonNode> testTestData = test.testDataForCases(ids(testTestCases));
                levelValues(brMatch, "testScenarios", "aiTestScenarios", aiScenarios, testScenarios, totals);
                levelValues(brMatch, "testCases", "aiTestCases", aiTestCases, testTestCases, totals);
                levelValues(brMatch, "testData", "aiTestData", aiTestData, testTestData, totals);
            ObjectNode traceabilityNode = brMatch.putObject("traceability");
                traceabilityNode.set("ai", chain(ai, mapping.aiRequirementIds(), mapper));
                traceabilityNode.set("testSolution", chain(test, ids(testRequirementsForMatch), mapper));

            brMatch.set("brSideBySide", brPairs(mapping, ai, testRequirementsForMatch, mapper));
            brMatch.set("tsSideBySide", pairByAnchor(aiScenarios, testScenarios, "AI TS", "Test TS", "requirementIds", mapper));
            brMatch.set("tcSideBySide", pairByAnchor(aiTestCases, testTestCases, "AI TC", "Test TC", "expectedOutcome", mapper));
            String segmentPrefix = mapping.testRequirementId().replaceFirst("^SEG(\\w+)-.*$", "$1");
            Map<String, String> aliasCrosswalk = aliasCrosswalkBySegment.computeIfAbsent(segmentPrefix,
                    ignored -> loadAliasCrosswalk(segmentPrefix, mapper));
            brMatch.set("tdSideBySide", pairTestDataByAnchorAndSchema(aiTestData, testTestData,
                    deliveryRoot.resolve("Run2"), aiElementNamesByTestCase, aliasCrosswalk, mapper));
        }

        addSummary(summaryByLevel, "TS", totals[0], totals[1], totals[2], totals[3]);
        addSummary(summaryByLevel, "TC", totals[4], totals[5], totals[6], totals[7]);
        addSummary(summaryByLevel, "TD", totals[8], totals[9], totals[10], totals[11]);
        ObjectNode overall = report.putObject("summary");
        overall.put("matchedBusinessRequirements", matches.size());
        overall.put("aiRequirements", ai.requirementsById.size());
        overall.put("aiScenarios", ai.scenarios.size());
        overall.put("aiTestCases", ai.testCases.size());
        overall.put("aiTestData", ai.testData.size());
        overall.put("testSolutionRequirements", test.requirementsById.size());
        overall.put("testSolutionScenarios", test.scenarios.size());
        overall.put("testSolutionTestCases", test.testCases.size());
        overall.put("testSolutionTestData", test.testData.size());
        overall.put("confirmedBusinessRequirements", countStatus(crosswalk, "CONFIRMED"));
        overall.put("reviewRequiredBusinessRequirements", countStatus(crosswalk, "REVIEW_REQUIRED"));

        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("four-level-traceability-matches.json").toFile(), report);
        writeMarkdown(outputRoot.resolve("four-level-traceability-matches.md"), report);
        System.out.printf("matchedBRs=%d confirmedBRs=%d reviewRequiredBRs=%d%n",
                matches.size(), overall.path("confirmedBusinessRequirements").asInt(),
                overall.path("reviewRequiredBusinessRequirements").asInt());
    }

    private static Run2Crosswalk loadCrosswalks(ObjectMapper mapper, Path reviewRoot) throws IOException {
        List<Run2Crosswalk.Mapping> mappings = new ArrayList<>();
        Run2CrosswalkLoader loader = new Run2CrosswalkLoader();
        for (String segment : SEGMENTS) {
            Path file = reviewRoot.resolve(Path.of("run2-crosswalks", "segment-" + segment + "-run2-crosswalk.json"));
            mappings.addAll(loader.load(file).mappings());
        }
        return new Run2Crosswalk(mappings);
    }

    /** Builds the independent AI and Test Solution traceability chains for a single requirement pair. */
    public static ObjectNode buildPairTraceability(String aiRequirementId, String testRequirementId) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path deliveryRoot = Atl105Paths.testInput().resolve(Path.of("ai-solution", "runs", "2026-09-23"));
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        Run2Crosswalk crosswalk = loadCrosswalks(mapper, reviewRoot);
        Path traceability = deliveryRoot.resolve(Path.of("Run2", "traceability_matrix_full.json"));
        Run2TraceabilityAdapter.AdaptedRun adapted = new Run2TraceabilityAdapter().adapt(
                traceability, deliveryRoot.resolve("Run2"), crosswalk);
        JsonNode aiPackage = mapper.valueToTree(adapted.artifactPackage());
        ArtifactIndex ai = ArtifactIndex.fromCanonical(aiPackage);
        ArtifactIndex test = ArtifactIndex.fromFiles(mapper, Atl105Paths.testOutput().resolve("test-json"));

        ObjectNode root = mapper.createObjectNode();
        root.put("aiRequirementId", aiRequirementId);
        root.put("testRequirementId", testRequirementId);
        root.set("ai", chain(ai, List.of(aiRequirementId), mapper));
        root.set("testSolution", chain(test, List.of(testRequirementId), mapper));
        return root;
    }

    private static void levelValues(ObjectNode parent, String testField, String aiField,
                                    List<JsonNode> aiMatches, List<JsonNode> testMatches, int[] totals) {
        ArrayNode aiValues = parent.putArray(aiField);
        ArrayNode testValues = parent.putArray(testField);
        aiMatches.forEach(aiValues::add);
        testMatches.forEach(testValues::add);
        int offset = "aiTestScenarios".equals(aiField) ? 0
            : "aiTestCases".equals(aiField) ? 4 : 8;
        totals[offset]++;
        if (!aiMatches.isEmpty()) totals[offset + 1]++;
        if (!testMatches.isEmpty()) totals[offset + 2]++;
        if (!aiMatches.isEmpty() && !testMatches.isEmpty()) totals[offset + 3]++;
        parent.put(aiField + "MatchCount", aiMatches.size());
        parent.put(testField + "MatchCount", testMatches.size());
    }

    private static JsonNode chain(ArtifactIndex index, List<String> requirementIds, ObjectMapper mapper) {
        ObjectNode chain = mapper.createObjectNode();
        List<JsonNode> requirements = requirementIds.stream().map(index.requirementsById::get)
                .filter(value -> value != null).toList();
        List<JsonNode> scenarios = index.scenariosForRequirements(requirementIds);
        List<JsonNode> testCases = index.testCasesForScenarios(ids(scenarios));
        List<JsonNode> testData = index.testDataForCases(ids(testCases));
        chain.set("businessRequirements", array(requirements, mapper));
        chain.set("testScenarios", array(scenarios, mapper));
        chain.set("testCases", array(testCases, mapper));
        chain.set("testData", array(testData, mapper));
        return chain;
    }

    private static ArrayNode array(List<JsonNode> values, ObjectMapper mapper) {
        ArrayNode result = mapper.createArrayNode();
        values.forEach(result::add);
        return result;
    }

    private static List<JsonNode> values(Map<String, List<JsonNode>> index, List<SourceAnchor> anchors) {
        LinkedHashMap<String, JsonNode> result = new LinkedHashMap<>();
        for (SourceAnchor anchor : anchors) {
            for (JsonNode value : index.getOrDefault(anchor(anchor), List.of())) {
                result.putIfAbsent(value.path("id").asText(value.toString()), value);
            }
        }
        return List.copyOf(result.values());
    }

    private static List<String> ids(List<JsonNode> values) {
        return values.stream().map(value -> value.path("id").asText()).filter(value -> !value.isBlank()).toList();
    }

    private static void addById(ArrayNode target, JsonNode value) {
        if (value != null) target.add(value);
    }

    /** BR-level side-by-side uses the crosswalk decision, not anchor overlap, since matching is a business decision. */
    private static ArrayNode brPairs(Run2Crosswalk.Mapping mapping, ArtifactIndex ai,
                                     List<JsonNode> testRequirements, ObjectMapper mapper) {
        ArrayNode pairs = mapper.createArrayNode();
        for (String aiId : mapping.aiRequirementIds()) {
            JsonNode aiReq = ai.requirementsById.get(aiId);
            String aiDetail = aiReq == null ? "" : summaryDetail(aiReq.path("title"));
            if (testRequirements.isEmpty()) {
                pairs.add(pair(aiId, aiDetail, "UNMATCHED",
                        "Test Solution rule catalog has no business-requirement artifact for " + mapping.testRequirementId(),
                        mapping.testRequirementId(), "", mapper));
                continue;
            }
            for (JsonNode testReq : testRequirements) {
                pairs.add(pair(aiId, aiDetail, mapping.matchStatus().name(), mapping.matchReason(),
                        testReq.path("id").asText(), summaryDetail(testReq.path("title")), mapper));
            }
        }
        return pairs;
    }

    /** Pairs items across sides by shared canonical anchor and records why each side did or did not match. */
    private static ArrayNode pairByAnchor(List<JsonNode> aiItems, List<JsonNode> testItems,
                                          String aiLabel, String testLabel, String detailField,
                                          ObjectMapper mapper) {
        ArrayNode pairs = mapper.createArrayNode();
        Set<String> consumedTestIds = new LinkedHashSet<>();
        for (JsonNode aiItem : aiItems) {
            Set<String> aiAnchors = anchorSet(aiItem);
            JsonNode matchedTest = null;
            String sharedAnchor = null;
            for (JsonNode testItem : testItems) {
                String testId = testItem.path("id").asText();
                if (consumedTestIds.contains(testId)) continue;
                for (String candidate : anchorSet(testItem)) {
                    if (aiAnchors.contains(candidate)) { matchedTest = testItem; sharedAnchor = candidate; break; }
                }
                if (matchedTest != null) break;
            }
            String aiId = aiItem.path("id").asText("");
            String aiDetail = summaryDetail(aiItem.path(detailField));
            if (matchedTest == null) {
                pairs.add(pair(aiId, aiDetail, "AI_ONLY",
                        "No " + testLabel + " artifact shares a canonical anchor with this " + aiLabel + " item", "", "", mapper));
                continue;
            }
            consumedTestIds.add(matchedTest.path("id").asText());
            pairs.add(pair(aiId, aiDetail, "MATCHED",
                    "Shares canonical anchor `" + sharedAnchor + "`",
                    matchedTest.path("id").asText(""), summaryDetail(matchedTest.path(detailField)), mapper));
        }
        for (JsonNode testItem : testItems) {
            String testId = testItem.path("id").asText();
            if (consumedTestIds.contains(testId)) continue;
            pairs.add(pair("", "", "TEST_ONLY",
                    "No " + aiLabel + " artifact shares a canonical anchor with this " + testLabel + " item",
                    testId, summaryDetail(testItem.path(detailField)), mapper));
        }
        return pairs;
    }

    private static ObjectNode pair(String aiId, String aiDetail, String status, String reason,
                                   String testId, String testDetail, ObjectMapper mapper) {
        ObjectNode pair = mapper.createObjectNode();
        pair.put("aiId", aiId);
        pair.put("aiDetail", aiDetail);
        pair.put("status", status);
        pair.put("reason", reason);
        pair.put("testId", testId);
        pair.put("testDetail", testDetail);
        return pair;
    }

    /**
     * Pairs test data by shared canonical anchor, then confirms the match two ways: (1) element-name alias
     * crosswalk — the AI's own observed field-name vocabulary (from key_values), normalized against the Test
     * Solution rule's element number, when a crosswalk exists for the segment; (2) raw JSON leaf key-name
     * overlap as a fallback signal when no crosswalk covers the field. Values are never compared, only keys/names.
     */
    private static ArrayNode pairTestDataByAnchorAndSchema(List<JsonNode> aiItems, List<JsonNode> testItems,
                                                           Path aiRunRoot, Map<String, List<String>> aiElementNamesByTestCase,
                                                           Map<String, String> aliasCrosswalk, ObjectMapper mapper) {
        ArrayNode pairs = mapper.createArrayNode();
        Set<String> consumedTestIds = new LinkedHashSet<>();
        for (JsonNode aiItem : aiItems) {
            Set<String> aiAnchors = anchorSet(aiItem);
            JsonNode matchedTest = null;
            String sharedAnchor = null;
            for (JsonNode testItem : testItems) {
                String testId = testItem.path("id").asText();
                if (consumedTestIds.contains(testId)) continue;
                for (String candidate : anchorSet(testItem)) {
                    if (aiAnchors.contains(candidate)) { matchedTest = testItem; sharedAnchor = candidate; break; }
                }
                if (matchedTest != null) break;
            }
            String aiId = aiItem.path("id").asText("");
            Set<String> aiKeys = leafKeyNames(loadAiPayload(aiItem, aiRunRoot, mapper));
            if (matchedTest == null) {
                ObjectNode pair = pair(aiId, aiKeys.size() + " keys", "AI_ONLY",
                        "No Test TD artifact shares a canonical anchor with this AI TD item", "", "", mapper);
                pair.put("aiKeyCount", aiKeys.size());
                pairs.add(pair);
                continue;
            }
            consumedTestIds.add(matchedTest.path("id").asText());
            Set<String> testKeys = leafKeyNames(matchedTest.path("payload"));
            Set<String> shared = new LinkedHashSet<>(aiKeys);
            shared.retainAll(testKeys);
            Set<String> missingInAi = new LinkedHashSet<>(testKeys);
            missingInAi.removeAll(aiKeys);
            Set<String> extraInAi = new LinkedHashSet<>(aiKeys);
            extraInAi.removeAll(testKeys);
            double overlapPercent = testKeys.isEmpty() ? 0.0 : 100.0 * shared.size() / testKeys.size();

            String expectedElement = expectedElementNumber(matchedTest, sharedAnchor);
            Set<String> aiElementNames = elementNamesForTestData(aiItem, aiElementNamesByTestCase);
            boolean elementConfirmed = false;
            String matchedAiElementName = null;
            if (expectedElement != null && !aliasCrosswalk.isEmpty()) {
                for (String aiElementName : aiElementNames) {
                    if (expectedElement.equals(aliasCrosswalk.get(aiElementName.toLowerCase(java.util.Locale.ROOT)))) {
                        elementConfirmed = true;
                        matchedAiElementName = aiElementName;
                        break;
                    }
                }
            }

            String status;
            String reason;
            if (elementConfirmed) {
                status = "MATCHED_ELEMENT_CONFIRMED";
                reason = String.format(
                        "Shares canonical anchor `%s`; CONFIRMED via field-alias crosswalk - AI element name `%s` maps to Test element %s. "
                                + "JSON key-name overlap (fallback signal) %.0f%%.",
                        sharedAnchor, matchedAiElementName, expectedElement, overlapPercent);
            } else if (expectedElement != null && !aliasCrosswalk.isEmpty()) {
                status = overlapPercent >= 50.0 ? "MATCHED_STRUCTURAL" : "MATCHED_ANCHOR_ONLY";
                reason = String.format(
                        "Shares canonical anchor `%s`; alias crosswalk exists for this segment but no AI element name "
                                + "resolves to Test element %s (AI observed: %s). JSON key-name overlap %.0f%% (%d/%d Test keys found by name).",
                        sharedAnchor, expectedElement, aiElementNames.isEmpty() ? "none" : String.join(", ", aiElementNames),
                        overlapPercent, shared.size(), testKeys.size());
            } else {
                status = overlapPercent >= 50.0 ? "MATCHED_STRUCTURAL" : "MATCHED_ANCHOR_ONLY";
                reason = String.format(
                        "Shares canonical anchor `%s`; no field-alias crosswalk available for this segment/element yet, "
                                + "falling back to JSON key-name overlap %.0f%% (%d/%d expected Test keys found by name; "
                                + "%d Test keys not found, %d extra AI keys). Values are not compared.",
                        sharedAnchor, overlapPercent, shared.size(), testKeys.size(), missingInAi.size(), extraInAi.size());
            }

            ObjectNode pair = pair(aiId, aiKeys.size() + " keys", status, reason,
                    matchedTest.path("id").asText(""), testKeys.size() + " keys", mapper);
            pair.put("elementConfirmed", elementConfirmed);
            pair.put("keyOverlapPercent", Math.round(overlapPercent * 10) / 10.0);
            pair.set("missingKeysInAi", mapper.valueToTree(missingInAi));
            pair.set("extraKeysInAi", mapper.valueToTree(extraInAi));
            pairs.add(pair);
        }
        for (JsonNode testItem : testItems) {
            String testId = testItem.path("id").asText();
            if (consumedTestIds.contains(testId)) continue;
            Set<String> testKeys = leafKeyNames(testItem.path("payload"));
            pairs.add(pair("", "", "TEST_ONLY",
                    "No AI TD artifact shares a canonical anchor with this Test TD item",
                    testId, testKeys.size() + " keys", mapper));
        }
        return pairs;
    }

    private static String expectedElementNumber(JsonNode matchedTest, String sharedAnchor) {
        for (JsonNode anchor : matchedTest.path("sourceAnchors")) {
            String element = anchor.path("element").asText(null);
            if (element != null && !element.isBlank()) return element;
        }
        String[] parts = sharedAnchor.split("\\|", -1);
        String element = parts.length > 4 ? parts[4] : "";
        return element.isBlank() ? null : element;
    }

    private static Set<String> elementNamesForTestData(JsonNode aiTestData, Map<String, List<String>> aiElementNamesByTestCase) {
        Set<String> names = new LinkedHashSet<>();
        for (JsonNode testCaseId : aiTestData.path("testCaseIds")) {
            names.addAll(aiElementNamesByTestCase.getOrDefault(testCaseId.asText(), List.of()));
        }
        return names;
    }

    private static Map<String, String> loadAliasCrosswalk(String segment, ObjectMapper mapper) {
        Path file = Atl105Paths.root().resolve(Path.of("contract",
                "segment-" + segment + "-field-alias-crosswalk.json"));
        if (!Files.isRegularFile(file)) return Map.of();
        try {
            JsonNode root = mapper.readTree(file.toFile());
            Map<String, String> result = new LinkedHashMap<>();
            for (JsonNode alias : root.path("aliases")) {
                String aiName = alias.path("aiElementName").asText(null);
                String testElement = alias.path("testElementNumber").asText(null);
                if (aiName != null && testElement != null) {
                    result.put(aiName.toLowerCase(java.util.Locale.ROOT), testElement);
                }
            }
            return result;
        } catch (IOException e) {
            return Map.of();
        }
    }

    private static JsonNode loadAiPayload(JsonNode aiTestData, Path aiRunRoot, ObjectMapper mapper) {
        String fileName = aiTestData.path("fileName").asText(null);
        if (fileName == null || fileName.isBlank()) return mapper.createObjectNode();
        Path file = aiRunRoot.resolve(fileName);
        if (!Files.isRegularFile(file)) return mapper.createObjectNode();
        try {
            return mapper.readTree(file.toFile());
        } catch (IOException e) {
            return mapper.createObjectNode();
        }
    }

    private static Set<String> leafKeyNames(JsonNode node) {
        Set<String> names = new LinkedHashSet<>();
        collectLeafKeyNames(node, names);
        return names;
    }

    private static void collectLeafKeyNames(JsonNode node, Set<String> names) {
        if (node.isObject()) {
            node.fieldNames().forEachRemaining(name -> {
                names.add(name);
                collectLeafKeyNames(node.get(name), names);
            });
        } else if (node.isArray()) {
            for (JsonNode item : node) collectLeafKeyNames(item, names);
        }
    }

    private static Set<String> anchorSet(JsonNode item) {
        Set<String> anchors = new LinkedHashSet<>();
        for (JsonNode anchor : item.path("sourceAnchors")) anchors.add(anchor(anchor));
        return anchors;
    }

    private static int countStatus(Run2Crosswalk crosswalk, String status) {
        return (int) crosswalk.mappings().stream().filter(value -> status.equals(value.matchStatus().name()))
                .filter(value -> !value.aiRequirementIds().isEmpty()).count();
    }

    private static void addSummary(ArrayNode target, String level, int entries, int ai, int test, int both) {
        ObjectNode row = target.addObject();
        row.put("level", level);
        row.put("matchedBREntries", entries);
        row.put("entriesWithAIArtifacts", ai);
        row.put("entriesWithTestSolutionArtifacts", test);
        row.put("entriesWithBothSides", both);
    }

    private static void writeMarkdown(Path output, JsonNode report) throws IOException {
        StringBuilder text = new StringBuilder("# Four-Level Traceability Match Report: AI vs Test Solution\n\n")
                .append("This report compares BR, TS, TC, and TD artifacts and preserves both sides' traceability chains.\n\n")
                .append("Full artifact detail (all fields, both sides) is in the companion JSON file in this folder.\n\n")
                .append("## Summary\n\n");
        JsonNode summary = report.path("summary");
        text.append("- Matched BR mappings: **").append(summary.path("matchedBusinessRequirements").asInt()).append("**\n")
                .append("- Confirmed BR mappings: **").append(summary.path("confirmedBusinessRequirements").asInt()).append("**\n")
                .append("- Review-required BR mappings: **").append(summary.path("reviewRequiredBusinessRequirements").asInt()).append("**\n\n")
                .append("| Level | Matched BR entries | With AI artifacts | With Test Solution artifacts | With both sides |\n")
                .append("|---|---:|---:|---:|---:|\n");
        for (JsonNode row : report.path("summaryByLevel")) {
            text.append('|').append(row.path("level").asText()).append('|')
                    .append(row.path("matchedBREntries").asInt()).append('|')
                    .append(row.path("entriesWithAIArtifacts").asInt()).append('|')
                    .append(row.path("entriesWithTestSolutionArtifacts").asInt()).append('|')
                    .append(row.path("entriesWithBothSides").asInt()).append("|\n");
        }
        text.append('\n');

        for (JsonNode br : report.path("businessRequirements")) {
            text.append("## ").append(br.path("testRequirementId").asText()).append(" - ")
                    .append(br.path("matchStatus").asText()).append("\n\n")
                    .append("**Reason:** ").append(br.path("matchReason").asText()).append("\n\n")
                    .append("| Level | AI count | Test count |\n|---|---:|---:|\n")
                    .append("| BR |").append(br.path("aiBusinessRequirements").size()).append('|')
                    .append(br.path("testBusinessRequirements").size()).append("|\n")
                    .append("| TS |").append(br.path("aiTestScenariosMatchCount").asInt()).append('|')
                    .append(br.path("testScenariosMatchCount").asInt()).append("|\n")
                    .append("| TC |").append(br.path("aiTestCasesMatchCount").asInt()).append('|')
                    .append(br.path("testCasesMatchCount").asInt()).append("|\n")
                    .append("| TD |").append(br.path("aiTestDataMatchCount").asInt()).append('|')
                    .append(br.path("testDataMatchCount").asInt()).append("|\n\n");
            sideBySideTable(text, "BR", br.path("brSideBySide"));
            sideBySideTable(text, "TS", br.path("tsSideBySide"));
            sideBySideTable(text, "TC", br.path("tcSideBySide"));
            sideBySideTable(text, "TD", br.path("tdSideBySide"));
        }
        Files.writeString(output, text);
    }

    private static void sideBySideTable(StringBuilder text, String level, JsonNode pairs) {
        text.append("### ").append(level).append(" side-by-side (").append(pairs.size()).append(")\n\n");
        if (pairs.isEmpty()) {
            text.append("_No AI or Test Solution artifacts at this level._\n\n");
            return;
        }
        text.append("| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |\n|---|---|---|---|---|---|\n");
        for (JsonNode pair : pairs) {
            text.append('|').append(pair.path("aiId").asText()).append('|')
                    .append(cell(pair.path("aiDetail").asText())).append('|')
                    .append(pair.path("status").asText()).append('|')
                    .append(pair.path("testId").asText()).append('|')
                    .append(cell(pair.path("testDetail").asText())).append('|')
                    .append(cell(pair.path("reason").asText())).append("|\n");
        }
        text.append('\n');
    }

    private static String summaryDetail(JsonNode value) {
        if (value.isMissingNode() || value.isNull()) return "";
        if (value.isArray()) {
            List<String> items = new ArrayList<>();
            value.forEach(item -> items.add(item.asText()));
            return String.join(", ", items);
        }
        return value.asText();
    }

    private static String cell(String value) {
        return value.replace("|", "\\|").replace("\n", " ");
    }

    private static String anchor(JsonNode value) {
        return String.join("|", value.path("specification").asText(), value.path("version").asText(),
                value.path("section").asText(), value.path("segment").asText(),
                value.path("element").asText(), value.path("rule").asText());
    }

    private static String anchor(SourceAnchor value) {
        return String.join("|", nullText(value.getSpecification()), nullText(value.getVersion()),
                nullText(value.getSection()), nullText(value.getSegment()), nullText(value.getElement()),
                nullText(value.getRule()));
    }

    private static String nullText(String value) { return value == null ? "" : value; }

    private static final class ArtifactIndex {
        private final Map<String, JsonNode> requirementsById = new LinkedHashMap<>();
        private final Map<String, List<JsonNode>> scenarios = new LinkedHashMap<>();
        private final Map<String, List<JsonNode>> testCases = new LinkedHashMap<>();
        private final Map<String, List<JsonNode>> testData = new LinkedHashMap<>();

        private static ArtifactIndex fromCanonical(JsonNode packageNode) {
            ArtifactIndex index = new ArtifactIndex();
            for (JsonNode value : packageNode.path("businessRequirements")) index.requirementsById.put(value.path("id").asText(), value);
            index.addAll(index.scenarios, packageNode.path("testScenarios"));
            index.addAll(index.testCases, packageNode.path("testCases"));
            index.addAll(index.testData, packageNode.path("testData"));
            return index;
        }

        private static ArtifactIndex fromFiles(ObjectMapper mapper, Path root) throws IOException {
            ArtifactIndex index = new ArtifactIndex();
            try (var files = Files.walk(root)) {
                for (Path file : files.filter(value -> value.toString().endsWith(".json")).toList()) {
                    JsonNode node;
                    try { node = mapper.readTree(file.toFile()); } catch (Exception ignored) { continue; }
                    for (JsonNode value : node.path("businessRequirements")) index.requirementsById.putIfAbsent(value.path("id").asText(), value);
                    index.addAll(index.scenarios, node.path("testScenarios"));
                    index.addAll(index.testCases, node.path("testCases"));
                    index.addAll(index.testData, node.path("testData"));
                }
            }
            return index;
        }

        private void addAll(Map<String, List<JsonNode>> target, JsonNode values) {
            if (!values.isArray()) return;
            for (JsonNode value : values) for (JsonNode anchor : value.path("sourceAnchors"))
                target.computeIfAbsent(anchor(anchor), ignored -> new ArrayList<>()).add(value);
        }

        private List<JsonNode> requirementsFor(List<SourceAnchor> anchors) {
            LinkedHashMap<String, JsonNode> values = new LinkedHashMap<>();
            for (JsonNode value : requirementsById.values()) for (JsonNode anchor : value.path("sourceAnchors"))
                for (SourceAnchor expected : anchors) if (anchor(anchor).equals(anchor(expected))) values.putIfAbsent(value.path("id").asText(), value);
            return List.copyOf(values.values());
        }

        private List<JsonNode> scenariosForRequirements(List<String> requirementIds) {
            Set<String> wanted = new LinkedHashSet<>(requirementIds);
            return distinctById(scenarios.values().stream().flatMap(List::stream)
                    .filter(value -> containsAny(value.path("requirementIds"), wanted)).toList());
        }

        private List<JsonNode> testCasesForScenarios(List<String> scenarioIds) {
            Set<String> wanted = new LinkedHashSet<>(scenarioIds);
            return distinctById(testCases.values().stream().flatMap(List::stream)
                    .filter(value -> containsAny(value.path("scenarioIds"), wanted)).toList());
        }

        private List<JsonNode> testDataForCases(List<String> testCaseIds) {
            Set<String> wanted = new LinkedHashSet<>(testCaseIds);
            return distinctById(testData.values().stream().flatMap(List::stream)
                    .filter(value -> containsAny(value.path("testCaseIds"), wanted)).toList());
        }

        private static boolean containsAny(JsonNode values, Set<String> wanted) {
            if (!values.isArray()) return false;
            for (JsonNode value : values) if (wanted.contains(value.asText())) return true;
            return false;
        }

        private static List<JsonNode> distinctById(List<JsonNode> values) {
            Map<String, JsonNode> distinct = new LinkedHashMap<>();
            for (JsonNode value : values) distinct.putIfAbsent(value.path("id").asText(value.toString()), value);
            return List.copyOf(distinct.values());
        }
    }
}