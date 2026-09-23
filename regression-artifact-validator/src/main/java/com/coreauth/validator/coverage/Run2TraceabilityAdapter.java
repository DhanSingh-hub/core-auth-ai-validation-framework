package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.ArtifactStatus;
import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalRequirement;
import com.coreauth.validator.canonical.CanonicalScenario;
import com.coreauth.validator.canonical.CanonicalTestCase;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.PackageManifest;
import com.coreauth.validator.canonical.RequirementCrosswalkEntry;
import com.coreauth.validator.canonical.SourceAnchor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Streams Run2's flat traceability registry into the producer-neutral canonical package. */
public final class Run2TraceabilityAdapter {
    private static final Pattern TEST_CASE_FILE = Pattern.compile("^(TC-[0-9]+)(?:-|\\.json$)");
    private final ObjectMapper mapper = new ObjectMapper();

    public AdaptedRun adapt(Path traceabilityFile, Path runRoot, Run2Crosswalk crosswalk) throws IOException {
        Map<String, List<SourceAnchor>> mappedAnchors = mappedAnchors(crosswalk);
        Map<String, CanonicalRequirement> requirements = new LinkedHashMap<>();
        Map<String, CanonicalScenario> scenarios = new LinkedHashMap<>();
        Map<String, CanonicalTestCase> testCases = new LinkedHashMap<>();
        Map<String, CanonicalTestData> testData = new LinkedHashMap<>();
        Map<String, Path> payloadFiles = indexPayloadFiles(runRoot);
        String specification = "ATL105";
        String version = null;
        String sourceId = null;

        try (JsonParser parser = mapper.getFactory().createParser(traceabilityFile.toFile())) {
            require(parser.nextToken() == JsonToken.START_OBJECT, "Run2 traceability root must be an object");
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String field = parser.currentName();
                parser.nextToken();
                if ("spec".equals(field)) {
                    JsonNode spec = mapper.readTree(parser);
                    sourceId = spec.path("spec_id").asText(null);
                    version = spec.path("spec_version").asText(null);
                } else if ("requirements".equals(field)) {
                    require(parser.currentToken() == JsonToken.START_ARRAY, "Run2 requirements must be an array");
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        adaptRequirement(mapper.readTree(parser), runRoot, version, mappedAnchors, payloadFiles,
                                requirements, scenarios, testCases, testData);
                    }
                } else if ("orphans".equals(field)) {
                    adaptOrphans(mapper.readTree(parser), version, scenarios);
                } else {
                    parser.skipChildren();
                }
            }
        }
        adaptMissingCandidates(runRoot.resolve("test_case_candidates.json"), runRoot, version,
                payloadFiles, scenarios, testCases, testData);

        CanonicalArtifactPackage result = new CanonicalArtifactPackage();
        PackageManifest manifest = new PackageManifest();
        manifest.setPackageId("RUN2-" + (sourceId == null ? "TRACEABILITY" : sourceId));
        manifest.setSpecification(specification);
        manifest.setSpecificationVersion(version);
        result.setManifest(manifest);
        result.setBusinessRequirements(List.copyOf(requirements.values()));
        result.setTestScenarios(List.copyOf(scenarios.values()));
        result.setTestCases(List.copyOf(testCases.values()));
        result.setTestData(List.copyOf(testData.values()));
        result.setRequirementCrosswalk(toCanonicalCrosswalk(crosswalk));
        return new AdaptedRun(result, requirements.size(), scenarios.size(), testCases.size(), testData.size());
    }

    private void adaptOrphans(JsonNode orphans, String version,
                              Map<String, CanonicalScenario> scenarios) {
        for (JsonNode orphan : orphans.path("scenarios_without_requirement")) {
            String scenarioId = orphan.path("scenario_id").asText(null);
            if (scenarioId == null || scenarios.containsKey(scenarioId)) continue;
            CanonicalScenario scenario = new CanonicalScenario();
            scenario.setId(scenarioId);
            scenario.setRequirementIds(List.of());
            scenario.setSourceAnchors(List.of(reviewAnchor(version, "orphan-scenario", scenarioId)));
            scenario.setStatus(ArtifactStatus.REVIEW_REQUIRED);
            scenarios.put(scenarioId, scenario);
        }
    }

    private void adaptMissingCandidates(Path candidatesFile, Path runRoot, String version,
                                        Map<String, Path> payloadFiles,
                                        Map<String, CanonicalScenario> scenarios,
                                        Map<String, CanonicalTestCase> testCases,
                                        Map<String, CanonicalTestData> testData) throws IOException {
        if (!Files.isRegularFile(candidatesFile)) return;
        try (JsonParser parser = mapper.getFactory().createParser(candidatesFile.toFile())) {
            require(parser.nextToken() == JsonToken.START_OBJECT, "Run2 test-case registry must be an object");
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String field = parser.currentName();
                parser.nextToken();
                if (!"test_cases".equals(field)) {
                    parser.skipChildren();
                    continue;
                }
                require(parser.currentToken() == JsonToken.START_ARRAY, "Run2 test_cases must be an array");
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    JsonNode node = mapper.readTree(parser);
                    String testCaseId = node.path("id").asText(null);
                    if (testCaseId == null || testCases.containsKey(testCaseId)) continue;
                    String scenarioId = node.path("scenario_id").asText(null);
                    CanonicalScenario scenario = scenarios.get(scenarioId);
                    List<SourceAnchor> anchors = scenario == null || scenario.getSourceAnchors() == null
                            ? List.of(reviewAnchor(version, "unlinked-test-case", testCaseId))
                            : scenario.getSourceAnchors();
                    if (scenario == null && scenarioId != null) {
                        scenario = new CanonicalScenario();
                        scenario.setId(scenarioId);
                        scenario.setRequirementIds(List.of());
                        scenario.setSourceAnchors(anchors);
                        scenario.setStatus(ArtifactStatus.REVIEW_REQUIRED);
                        scenarios.put(scenarioId, scenario);
                    }

                    CanonicalTestCase testCase = new CanonicalTestCase();
                    testCase.setId(testCaseId);
                    testCase.setScenarioIds(scenarioId == null ? List.of() : List.of(scenarioId));
                    testCase.setSourceAnchors(anchors);
                    testCase.setExpectedOutcome(expectedOutcome(node));
                    testCase.setPriority(node.path("priority").asText(null));
                    testCase.setStatus("REVIEW_REQUIRED");
                    addCandidateTestData(node, runRoot, testCase, anchors, payloadFiles, testData);
                    testCases.put(testCaseId, testCase);
                }
            }
        }
    }

    private void addCandidateTestData(JsonNode node, Path runRoot, CanonicalTestCase testCase,
                                      List<SourceAnchor> anchors, Map<String, Path> payloadFiles,
                                      Map<String, CanonicalTestData> testData) {
        Path resolved = resolvePayload(runRoot, node.path("test_data_file").asText(null),
                testCase.getId(), payloadFiles);
        if (resolved == null) return;
        String relative = runRoot.relativize(resolved).toString().replace('\\', '/');
        testCase.setTestDataFile(relative);
        CanonicalTestData data = new CanonicalTestData();
        data.setId("TD-" + testCase.getId());
        data.setTestCaseIds(List.of(testCase.getId()));
        data.setSourceAnchors(anchors);
        data.setExpectedValidation("REVIEW");
        data.setFileName(relative);
        data.setPayload(mapper.createObjectNode().put("sourceFile", relative));
        testData.putIfAbsent(data.getId(), data);
    }

    private void adaptRequirement(JsonNode node, Path runRoot, String version,
                                  Map<String, List<SourceAnchor>> mappedAnchors,
                                  Map<String, Path> payloadFiles,
                                  Map<String, CanonicalRequirement> requirements,
                                  Map<String, CanonicalScenario> scenarios,
                                  Map<String, CanonicalTestCase> testCases,
                                  Map<String, CanonicalTestData> testData) throws IOException {
        String requirementId = node.path("requirement_id").asText(null);
        List<SourceAnchor> anchors = mappedAnchors.getOrDefault(requirementId, List.of(derivedAnchor(node, version)));

        CanonicalRequirement requirement = new CanonicalRequirement();
        requirement.setId(requirementId);
        requirement.setTitle(node.path("statement").asText(null));
        requirement.setSourceAnchors(anchors);
        requirements.putIfAbsent(requirementId, requirement);

        for (JsonNode scenarioNode : node.path("scenarios")) {
            String scenarioId = scenarioNode.path("scenario_id").asText(null);
            CanonicalScenario scenario = scenarios.computeIfAbsent(scenarioId, ignored -> new CanonicalScenario());
            scenario.setId(scenarioId);
            scenario.setRequirementIds(merge(scenario.getRequirementIds(), requirementId));
            scenario.setSourceAnchors(mergeAnchors(scenario.getSourceAnchors(), anchors));
            scenario.setStatus(ArtifactStatus.REVIEW_REQUIRED);

            for (JsonNode testCaseNode : scenarioNode.path("test_cases")) {
                String testCaseId = testCaseNode.path("test_case_id").asText(null);
                CanonicalTestCase testCase = testCases.computeIfAbsent(testCaseId, ignored -> new CanonicalTestCase());
                testCase.setId(testCaseId);
                testCase.setScenarioIds(merge(testCase.getScenarioIds(), scenarioId));
                testCase.setSourceAnchors(mergeAnchors(testCase.getSourceAnchors(), anchors));
                testCase.setExpectedOutcome(expectedOutcome(testCaseNode));
                testCase.setPriority(testCaseNode.path("priority").asText(null));
                testCase.setStatus("REVIEW_REQUIRED");

                JsonNode dataNode = testCaseNode.path("test_data");
                if (dataNode.path("file_exists").asBoolean(false)) {
                        Path resolved = resolvePayload(runRoot, dataNode.path("test_data_file").asText(null),
                            testCaseId, payloadFiles);
                    if (resolved != null) {
                        String relative = runRoot.relativize(resolved).toString().replace('\\', '/');
                        testCase.setTestDataFile(relative);
                        CanonicalTestData data = new CanonicalTestData();
                        data.setId("TD-" + testCaseId);
                        data.setTestCaseIds(List.of(testCaseId));
                        data.setSourceAnchors(anchors);
                        data.setExpectedValidation("REVIEW");
                        data.setFileName(relative);
                        data.setPayload(mapper.createObjectNode().put("sourceFile", relative));
                        testData.putIfAbsent(data.getId(), data);
                    }
                }
            }
        }
    }

    private static List<RequirementCrosswalkEntry> toCanonicalCrosswalk(Run2Crosswalk crosswalk) {
        List<RequirementCrosswalkEntry> result = new ArrayList<>();
        for (Run2Crosswalk.Mapping mapping : crosswalk == null ? List.<Run2Crosswalk.Mapping>of() : crosswalk.mappings()) {
            RequirementCrosswalkEntry entry = new RequirementCrosswalkEntry();
            entry.setTestRequirementId(mapping.testRequirementId());
            entry.setAiRequirementIds(mapping.aiRequirementIds());
            entry.setMatchStatus(mapping.matchStatus());
            entry.setMatchReason(mapping.matchReason());
            entry.setReviewOwner(mapping.reviewOwner());
            result.add(entry);
        }
        return result;
    }

    private static Map<String, List<SourceAnchor>> mappedAnchors(Run2Crosswalk crosswalk) {
        Map<String, List<SourceAnchor>> result = new HashMap<>();
        if (crosswalk != null) {
            for (Run2Crosswalk.Mapping mapping : crosswalk.mappings()) {
                for (String aiId : mapping.aiRequirementIds()) {
                    result.merge(aiId, mapping.sourceAnchors(), Run2TraceabilityAdapter::mergeAnchors);
                }
            }
        }
        return result;
    }

    private static SourceAnchor derivedAnchor(JsonNode requirement, String version) {
        SourceAnchor anchor = new SourceAnchor();
        anchor.setSpecification("ATL105");
        anchor.setVersion(version);
        anchor.setSection("page-" + requirement.path("source_page").asText("unknown"));
        anchor.setSegment(requirement.path("segment_id").asText("unknown").replace("ENT-SEG-", ""));
        anchor.setElement(requirement.path("source_rule_id").asText("unknown"));
        anchor.setRule("run2-source-rule-" + normalize(requirement.path("source_rule_id").asText("unknown")));
        return anchor;
    }

    private static SourceAnchor reviewAnchor(String version, String category, String id) {
        SourceAnchor anchor = new SourceAnchor();
        anchor.setSpecification("ATL105");
        anchor.setVersion(version);
        anchor.setSegment("UNRESOLVED");
        anchor.setElement(id);
        anchor.setRule(category);
        return anchor;
    }

    private static String expectedOutcome(JsonNode testCase) {
        JsonNode expected = testCase.path("expected_response");
        String response = expected.path("response_code").asText(null);
        String decline = expected.path("decline_code").asText(null);
        if (response != null) return "response_code=" + response;
        if (decline != null) return "decline_code=" + decline;
        return "verification_status=" + testCase.path("verification_status").asText("REVIEW_REQUIRED");
    }

    private static Path resolvePayload(Path runRoot, String declaredFile, String testCaseId,
                                       Map<String, Path> payloadFiles) {
        if (declaredFile != null) {
            Path exact = runRoot.resolve(declaredFile).normalize();
            if (exact.startsWith(runRoot.normalize()) && Files.isRegularFile(exact)) return exact;
        }
        return testCaseId == null ? null : payloadFiles.get(testCaseId);
    }

    private static Map<String, Path> indexPayloadFiles(Path runRoot) throws IOException {
        Path dataDirectory = runRoot.resolve("qe_shaped_test_data");
        if (!Files.isDirectory(dataDirectory)) return Map.of();
        Map<String, Path> result = new HashMap<>();
        try (var files = Files.list(dataDirectory)) {
            files.filter(Files::isRegularFile).forEach(file -> {
                String name = file.getFileName().toString();
                Matcher matcher = TEST_CASE_FILE.matcher(name);
                if (matcher.find() && !name.contains(".meta-")) {
                    result.putIfAbsent(matcher.group(1), file);
                }
            });
        }
        return result;
    }

    private static List<String> merge(List<String> values, String value) {
        Set<String> result = new LinkedHashSet<>(values == null ? List.of() : values);
        if (value != null) result.add(value);
        return List.copyOf(result);
    }

    private static List<SourceAnchor> mergeAnchors(List<SourceAnchor> left, List<SourceAnchor> right) {
        Map<String, SourceAnchor> result = new LinkedHashMap<>();
        if (left != null) left.forEach(anchor -> result.put(anchor.canonicalKey(), anchor));
        if (right != null) right.forEach(anchor -> result.put(anchor.canonicalKey(), anchor));
        return List.copyOf(result.values());
    }

    private static String normalize(String value) {
        return value.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }

    private static void require(boolean condition, String message) throws IOException {
        if (!condition) throw new IOException(message);
    }

    public record AdaptedRun(CanonicalArtifactPackage artifactPackage, int requirements,
                             int scenarios, int testCases, int testData) {
    }
}