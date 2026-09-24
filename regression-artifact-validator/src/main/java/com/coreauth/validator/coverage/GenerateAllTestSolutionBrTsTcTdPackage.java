package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/** Aggregates independent Test Solution BR/TS/TC/TD evidence without importing AI artifacts. */
public final class GenerateAllTestSolutionBrTsTcTdPackage {
    private static final Pattern SEGMENT = Pattern.compile("segment-([0-9]+|DL[1-8])", Pattern.CASE_INSENSITIVE);

    private GenerateAllTestSolutionBrTsTcTdPackage() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path inputRoot = Atl105Paths.testJson();
        Path outputRoot = Atl105Paths.testOutput().resolve("test-solution-independent-review");
        Files.createDirectories(outputRoot);

        Map<String, JsonNode> requirements = new LinkedHashMap<>();
        Map<String, JsonNode> scenarios = new LinkedHashMap<>();
        Map<String, JsonNode> testCases = new LinkedHashMap<>();
        Map<String, JsonNode> testData = new LinkedHashMap<>();
        Map<String, Set<String>> sourceFilesBySegment = new TreeMap<>();
        Set<String> excludedFiles = new LinkedHashSet<>();
        Map<String, int[]> countsBySegment = new TreeMap<>();

        try (Stream<Path> files = Files.walk(inputRoot)) {
            for (Path file : files.filter(value -> value.toString().endsWith(".json")).sorted().toList()) {
                JsonNode root;
                try {
                    root = mapper.readTree(file.toFile());
                } catch (Exception exception) {
                    excludedFiles.add(relative(inputRoot, file) + " [invalid JSON]");
                    continue;
                }
                if (isStandaloneTestData(root)) {
                    String segment = standaloneSegment(root, file);
                    String relative = relative(inputRoot, file);
                    sourceFilesBySegment.computeIfAbsent(segment, ignored -> new LinkedHashSet<>()).add(relative);
                    int[] counts = countsBySegment.computeIfAbsent(segment, ignored -> new int[4]);
                    addStandaloneTestData(root, scenarios, testCases, testData, counts);
                    continue;
                }
                if (!isCanonicalShape(root)) {
                    excludedFiles.add(relative(inputRoot, file) + " [partial or legacy shape]");
                    continue;
                }
                String segment = segment(file.getFileName().toString());
                String relative = relative(inputRoot, file);
                sourceFilesBySegment.computeIfAbsent(segment, ignored -> new LinkedHashSet<>()).add(relative);
                int[] counts = countsBySegment.computeIfAbsent(segment, ignored -> new int[4]);
                merge(root.path("businessRequirements"), requirements, counts, 0);
                merge(root.path("testScenarios"), scenarios, counts, 1);
                merge(root.path("testCases"), testCases, counts, 2);
                merge(root.path("testData"), testData, counts, 3);
            }
        }

        ObjectNode output = mapper.createObjectNode();
        ObjectNode manifest = output.putObject("manifest");
        manifest.put("packageId", "ATL105-ALL-TEST-SOLUTION-TRAINING-BR-TS-TC-TD");
        manifest.put("specification", "ATL105");
        manifest.put("specificationVersion", "2026-3");
        manifest.put("authority", "INDEPENDENT_TEST_SOLUTION");
        manifest.put("status", "EVIDENCE_AGGREGATE_REVIEW_REQUIRED");
        manifest.put("aiArtifactsIncluded", false);
        manifest.put("canonicalChain", "businessRequirements -> testScenarios -> testCases -> testData");
        output.set("businessRequirements", values(requirements, mapper));
        output.set("testScenarios", values(scenarios, mapper));
        output.set("testCases", values(testCases, mapper));
        output.set("testData", values(testData, mapper));
        output.putArray("requirementCrosswalk");

        ObjectNode provenance = output.putObject("provenance");
        provenance.put("inputRoot", inputRoot.toString());
        ArrayNode sourceFiles = provenance.putArray("sourceFilesBySegment");
        for (Map.Entry<String, Set<String>> entry : sourceFilesBySegment.entrySet()) {
            ObjectNode row = sourceFiles.addObject();
            row.put("segment", entry.getKey());
            row.set("files", mapper.valueToTree(entry.getValue()));
            int[] counts = countsBySegment.get(entry.getKey());
            row.put("businessRequirements", counts[0]);
            row.put("testScenarios", counts[1]);
            row.put("testCases", counts[2]);
            row.put("testData", counts[3]);
        }
        provenance.set("excludedFiles", mapper.valueToTree(excludedFiles));
        ObjectNode summary = output.putObject("summary");
        summary.put("businessRequirements", requirements.size());
        summary.put("testScenarios", scenarios.size());
        summary.put("testCases", testCases.size());
        summary.put("testData", testData.size());
        summary.put("segmentsWithCanonicalEvidence", sourceFilesBySegment.size());
        summary.put("excludedFiles", excludedFiles.size());
        summary.put("standaloneTestDataNormalized", countStandaloneTestData(testData));
        summary.put("executionReady", false);
        summary.put("reason", "Aggregate contains independent Test Solution evidence only; excluded partial packages and missing links remain REVIEW_REQUIRED.");

        Path outputFile = outputRoot.resolve("all-test-solution-br-ts-tc-td-training-package.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile.toFile(), output);
        Files.writeString(outputRoot.resolve("all-test-solution-br-ts-tc-td-training-summary.md"), markdown(summary, sourceFilesBySegment, countsBySegment, excludedFiles));
        System.out.printf("BR=%d TS=%d TC=%d TD=%d segments=%d excluded=%d output=%s%n",
                requirements.size(), scenarios.size(), testCases.size(), testData.size(), sourceFilesBySegment.size(), excludedFiles.size(), outputFile);
    }

    private static boolean isCanonicalShape(JsonNode root) {
        return root.isObject() && root.path("businessRequirements").isArray()
                && root.path("testScenarios").isArray()
                && root.path("testCases").isArray()
                && root.path("testData").isArray();
    }

    private static boolean isStandaloneTestData(JsonNode root) {
        return root.isObject() && root.has("testDataId") && root.has("testCaseId") && root.has("scenarioId");
    }

    private static String standaloneSegment(JsonNode root, Path file) {
        Set<String> segmentTypes = new LinkedHashSet<>();
        collectSegmentTypes(root.path("request"), segmentTypes);
        if (segmentTypes.size() == 1) return segmentTypes.iterator().next();
        String filenameSegment = segment(file.getFileName().toString());
        return "UNSEGMENTED".equals(filenameSegment) ? "CONTEXT_REVIEW_REQUIRED" : filenameSegment;
    }

    private static void collectSegmentTypes(JsonNode node, Set<String> segmentTypes) {
        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                if ("segmentType".equalsIgnoreCase(entry.getKey()) && entry.getValue().isValueNode()) {
                    segmentTypes.add(entry.getValue().asText());
                }
                collectSegmentTypes(entry.getValue(), segmentTypes);
            });
        } else if (node.isArray()) {
            node.forEach(value -> collectSegmentTypes(value, segmentTypes));
        }
    }

    private static void addStandaloneTestData(JsonNode source, Map<String, JsonNode> scenarios,
                                              Map<String, JsonNode> testCases, Map<String, JsonNode> testData,
                                              int[] counts) {
        String scenarioId = source.path("scenarioId").asText();
        String testCaseId = source.path("testCaseId").asText();
        String testDataId = source.path("testDataId").asText();
        if (!scenarios.containsKey(scenarioId)) {
            ObjectNode scenario = new ObjectMapper().createObjectNode();
            scenario.put("id", scenarioId);
            scenario.putArray("requirementIds");
            scenario.putArray("sourceAnchors");
            scenario.put("status", "REVIEW_REQUIRED");
            scenarios.put(scenarioId, scenario);
            counts[1]++;
        }
        if (!testCases.containsKey(testCaseId)) {
            ObjectNode testCase = new ObjectMapper().createObjectNode();
            testCase.put("id", testCaseId);
            testCase.putArray("scenarioIds").add(scenarioId);
            testCase.put("expectedOutcome", source.path("expectedValidation").asText("REVIEW_REQUIRED"));
            testCase.put("status", "REVIEW_REQUIRED");
            testCase.putArray("sourceAnchors");
            testCases.put(testCaseId, testCase);
            counts[2]++;
        }
        if (!testData.containsKey(testDataId)) {
            ObjectNode data = new ObjectMapper().createObjectNode();
            data.put("id", testDataId);
            data.putArray("testCaseIds").add(testCaseId);
            data.put("expectedValidation", source.path("expectedValidation").asText("REVIEW_REQUIRED"));
            data.putArray("sourceAnchors");
            data.set("payload", source.path("request"));
            testData.put(testDataId, data);
            counts[3]++;
        }
    }

    private static long countStandaloneTestData(Map<String, JsonNode> testData) {
        return testData.keySet().stream().filter(value -> value.startsWith("TEST-DATA-")).count();
    }

    private static void merge(JsonNode values, Map<String, JsonNode> target, int[] counts, int index) {
        for (JsonNode value : values) {
            String id = value.path("id").asText("");
            if (!id.isBlank()) {
                target.putIfAbsent(id, value);
                counts[index]++;
            }
        }
    }

    private static ArrayNode values(Map<String, JsonNode> values, ObjectMapper mapper) {
        ArrayNode output = mapper.createArrayNode();
        values.values().forEach(output::add);
        return output;
    }

    private static String segment(String filename) {
        Matcher matcher = SEGMENT.matcher(filename);
        return matcher.find() ? matcher.group(1).toUpperCase() : "UNSEGMENTED";
    }

    private static String relative(Path root, Path file) {
        return root.relativize(file).toString().replace('\\', '/');
    }

    private static String markdown(ObjectNode summary, Map<String, Set<String>> filesBySegment,
                                   Map<String, int[]> countsBySegment, Set<String> excludedFiles) {
        StringBuilder text = new StringBuilder("# All Independent Test Solution BR -> TS -> TC -> TD Training Evidence\n\n")
                .append("This is an aggregate of existing Test Solution JSON evidence only. AI input is excluded. ")
                .append("The aggregate is review-required and not execution-ready until every chain is independently validated.\n\n")
                .append("| Artifact | Count |\n|---|---:|\n")
                .append("| BR | ").append(summary.path("businessRequirements").asInt()).append(" |\n")
                .append("| TS | ").append(summary.path("testScenarios").asInt()).append(" |\n")
                .append("| TC | ").append(summary.path("testCases").asInt()).append(" |\n")
                .append("| TD | ").append(summary.path("testData").asInt()).append(" |\n\n")
                .append("## Segment evidence\n\n| Segment | BR | TS | TC | TD | Source files |\n|---|---:|---:|---:|---:|---:|\n");
        for (Map.Entry<String, Set<String>> entry : filesBySegment.entrySet()) {
            int[] counts = countsBySegment.get(entry.getKey());
            text.append('|').append(entry.getKey()).append('|').append(counts[0]).append('|').append(counts[1])
                    .append('|').append(counts[2]).append('|').append(counts[3]).append('|').append(entry.getValue().size()).append("|\n");
        }
        text.append("\nExcluded partial/legacy files: **").append(excludedFiles.size()).append("**. They need normalization before they can contribute to the canonical chain.\n");
        return text.toString();
    }
}
