package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.Atl105AiElementIntake;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Finding;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class Atl105FieldStructureRuleRunner {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Map<String, String> PARTIAL_STRUCTURE_EXECUTORS = Map.of(
        "SEG100-R-001", "SECTION11_REQUIRED_SEGMENT_100_EXACT_ONCE",
        "SEG100-R-008", "SECTION12_SEGMENT_101_NON_DUPLICATION"
    );
    private static final Map<String, String> EXECUTABLE_STATUS = Map.of(
        "CHECKS_PASSED", "PASS",
        "INVALID", "FAIL",
        "REVIEW_REQUIRED", "REVIEW_REQUIRED"
    );

    private Atl105FieldStructureRuleRunner() { }

    public static Map<String, JsonNode> loadFieldStructureRules(JsonNode inventory) throws IOException {
        Map<String, JsonNode> rules = new TreeMap<>();
        for (JsonNode rule : inventory.path("businessRequirements")) {
            String ruleId = rule.path("ruleId").asText("");
            String ruleClass = rule.path("class").asText("");
            if (!ruleId.isBlank() && ("field".equals(ruleClass) || "structure".equals(ruleClass))) {
                if (rules.putIfAbsent(ruleId, rule) != null) throw new IOException("Duplicate source rule ID: " + ruleId);
            }
        }
        return rules;
    }

    public static Map<String, List<String>> loadExecutableProfiles(JsonNode profiles, Map<String, JsonNode> rules) {
        Map<String, List<String>> executable = new TreeMap<>();
        for (JsonNode profile : profiles.path("profiles")) {
            String ruleId = profile.path("existingRuleId").asText("");
            JsonNode rule = rules.get(ruleId);
            if (rule == null || !profile.path("id").isTextual()) continue;
            String ruleClass = rule.path("class").asText("");
            if (!"field".equals(ruleClass) && !"structure".equals(ruleClass)) continue;
            executable.computeIfAbsent(ruleId, ignored -> new ArrayList<>()).add(profile.path("id").asText());
        }
        return executable;
    }

    public static String reportStatus(Status status) {
        return EXECUTABLE_STATUS.getOrDefault(status == null ? "" : status.name(), "REVIEW_REQUIRED");
    }

    public static Map<String, String> partialStructureExecutors(Map<String, JsonNode> rules) {
        Map<String, String> result = new TreeMap<>();
        PARTIAL_STRUCTURE_EXECUTORS.forEach((ruleId, evaluator) -> {
            JsonNode rule = rules.get(ruleId);
            if (rule != null && "structure".equals(rule.path("class").asText())) result.put(ruleId, evaluator);
        });
        return result;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 4) throw new IllegalArgumentException(
            "Usage: Atl105FieldStructureRuleRunner <ATL105-pack> <run-id> <qe-shaped-test-data-dir> <report.json>");
        Path pack = Path.of(args[0]);
        String runId = args[1];
        Path runDirectory = Path.of(args[2]);
        Path output = Path.of(args[3]);
        Path inventoryFile = pack.resolve("test-output/test-solution-independent-review/source-derived-requirement-inventory.json");
        Path profilesFile = pack.resolve("docs/specs/kb/elements/validation-profiles.json");
        if (!Files.isRegularFile(inventoryFile) || !Files.isRegularFile(profilesFile) || !Files.isDirectory(runDirectory))
            throw new IOException("Source inventory, validation profiles, and Run qe_shaped_test_data directory are required");
        if (Files.exists(output)) throw new IOException("Refusing to overwrite existing report: " + output);

        JsonNode inventory = MAPPER.readTree(inventoryFile.toFile());
        JsonNode profiles = MAPPER.readTree(profilesFile.toFile());
        Map<String, JsonNode> rules = loadFieldStructureRules(inventory);
        Map<String, List<String>> executable = loadExecutableProfiles(profiles, rules);
        Map<String, String> partialExecutors = partialStructureExecutors(rules);
        Atl105AiElementIntake intake = new Atl105AiElementIntake(pack);
        ArrayNode evaluations = MAPPER.createArrayNode();
        Map<String, Map<String, Long>> outcomesByClass = new TreeMap<>();
        Map<String, Map<String, Long>> outcomesByRule = new TreeMap<>();
        Map<String, Long> failureDispositionCounts = new TreeMap<>();
        Map<String, Long> evaluationsByRule = new TreeMap<>();
        Map<String, Long> unmappedFindingCounts = new TreeMap<>();
        ArrayNode unmappedSamples = MAPPER.createArrayNode();
        int messageCount = 0;
        List<Path> metadataFiles;
        try (var files = Files.list(runDirectory)) {
            metadataFiles = files.filter(path -> path.getFileName().toString().endsWith(".meta.json")).sorted().toList();
        }
        for (Path metaFile : metadataFiles) {
            Path payloadFile = metaFile.resolveSibling(metaFile.getFileName().toString().replace(".meta.json", ".json"));
            JsonNode meta = MAPPER.readTree(metaFile.toFile());
            JsonNode payload = Files.isRegularFile(payloadFile) ? MAPPER.readTree(payloadFile.toFile()) : null;
            Atl105AiElementIntake.MessageAssessment assessment = intake.assess(meta, payload);
            messageCount++;
            if (messageCount % 1000 == 0) System.out.println("assessed=" + messageCount + "/" + metadataFiles.size());
            for (var observed : assessment.observations()) {
                for (Finding finding : observed.result().findings()) {
                    JsonNode rule = rules.get(finding.ruleId());
                    String partialExecutor = partialExecutors.get(finding.ruleId());
                    if (rule == null || !executable.containsKey(finding.ruleId()) && partialExecutor == null) {
                        if (finding.status() == Status.REVIEW_REQUIRED) {
                            unmappedFindingCounts.merge(observed.segment(), 1L, Long::sum);
                            if (unmappedSamples.size() < 500) {
                                ObjectNode sample = unmappedSamples.addObject();
                                sample.put("testCaseId", assessment.testCaseId());
                                sample.put("segment", observed.segment());
                                sample.put("element", finding.element());
                                sample.put("reason", finding.reason());
                            }
                        }
                        continue;
                    }
                    String outcome = reportStatus(finding.status());
                    String ruleClass = rule.path("class").asText();
                    outcomesByClass.computeIfAbsent(ruleClass, ignored -> new TreeMap<>()).merge(outcome, 1L, Long::sum);
                    outcomesByRule.computeIfAbsent(finding.ruleId(), ignored -> new TreeMap<>()).merge(outcome, 1L, Long::sum);
                    evaluationsByRule.merge(finding.ruleId(), 1L, Long::sum);
                    String failureDisposition = failureDisposition(assessment, finding);
                    if ("FAIL".equals(outcome)) failureDispositionCounts.merge(failureDisposition, 1L, Long::sum);
                    ObjectNode row = evaluations.addObject();
                    row.put("runId", runId);
                    row.put("testCaseId", assessment.testCaseId());
                    row.put("scenarioType", assessment.scenarioType());
                    row.put("negativeClass", assessment.negativeClass());
                    row.put("declaredViolatedElement", assessment.violatedElement());
                    row.put("ruleId", finding.ruleId());
                    row.put("ruleClass", ruleClass);
                    row.put("title", rule.path("title").asText());
                    row.put("scope", observed.segment());
                    row.put("element", finding.element());
                    row.put("status", outcome);
                    row.put("failureDisposition", failureDisposition);
                    row.put("aiValueMarkedProvisional", assessment.provisional()
                        .contains(observed.segment() + "|" + finding.element()));
                    row.put("evaluator", partialExecutor == null ? "SOURCE_VALIDATED_ELEMENT_PROFILE" : partialExecutor);
                    row.put("profileIds", String.join(",", executable.getOrDefault(finding.ruleId(), List.of())));
                    row.put("reason", finding.reason());
                    row.set("sourceAnchor", rule.path("sourceAnchor").deepCopy());
                }
            }
        }

        ArrayNode ruleCoverage = MAPPER.createArrayNode();
        Map<String, Map<String, Long>> coverageByClass = new TreeMap<>();
        for (Map.Entry<String, JsonNode> entry : rules.entrySet()) {
            boolean hasProfile = executable.containsKey(entry.getKey());
            String partialExecutor = partialExecutors.get(entry.getKey());
            boolean hasEvaluator = hasProfile || partialExecutor != null;
            ObjectNode row = ruleCoverage.addObject();
            row.put("ruleId", entry.getKey());
            row.put("ruleClass", entry.getValue().path("class").asText());
            row.put("title", entry.getValue().path("title").asText());
            row.put("executionStatus", hasProfile ? "PROFILE_BACKED_LIMITED_EXECUTION"
                : partialExecutor != null ? "PARTIAL_EXECUTOR_REVIEW_REQUIRED_FOR_UNCHECKED_BRANCHES" : "REVIEW_REQUIRED_NO_BOUND_EXECUTOR");
            row.put("evaluator", partialExecutor == null ? (hasProfile ? "SOURCE_VALIDATED_ELEMENT_PROFILE" : "") : partialExecutor);
            row.put("evaluatedCaseCount", evaluationsByRule.getOrDefault(entry.getKey(), 0L));
            row.set("sourceAnchor", entry.getValue().path("sourceAnchor").deepCopy());
            String state = hasProfile ? "PROFILE_BACKED_LIMITED_EXECUTION"
                : partialExecutor != null ? "PARTIAL_EXECUTOR_REVIEW_REQUIRED_FOR_UNCHECKED_BRANCHES" : "REVIEW_REQUIRED_NO_BOUND_EXECUTOR";
            coverageByClass.computeIfAbsent(entry.getValue().path("class").asText(), ignored -> new TreeMap<>()).merge(state, 1L, Long::sum);
        }

        ObjectNode report = MAPPER.createObjectNode();
        report.put("artifact", "ATL105-FIELD-STRUCTURE-RULE-EXECUTION");
        report.put("specificationVersion", "2026-3");
        report.put("runId", runId);
        report.put("status", "PARTIAL_SOURCE_VALIDATED_EXECUTION_NOT_CERTIFICATION");
        report.put("policy", "PASS means the bound profile passed only its stated check; FAIL means that check detected a violation, not that the whole case failed; REVIEW_REQUIRED means no source-bound executor/context was available. These are not full BR, semantic, or business approvals.");
        report.put("negativeScenarioPolicy", "A profile FAIL on a negative scenario whose element matches the declared violated element is an expected negative-target detection candidate; other negative FAILs require review.");
        report.put("messagesAssessed", messageCount);
        report.put("inventoryRuleCount", rules.size());
        report.put("ruleCountsByClass", MAPPER.valueToTree(classCounts(rules)));
        report.put("executableProfileRuleCount", executable.size());
        report.put("partialStructureRuleCount", partialExecutors.size());
        report.put("ruleExecutionCoverageByClass", MAPPER.valueToTree(coverageByClass));
        report.put("outcomesByClass", MAPPER.valueToTree(outcomesByClass));
        report.put("outcomesByRule", MAPPER.valueToTree(outcomesByRule));
        report.put("failuresByDisposition", MAPPER.valueToTree(failureDispositionCounts));
        report.put("unmappedReviewFindingsBySegment", MAPPER.valueToTree(unmappedFindingCounts));
        report.put("unmappedReviewSampleCount", unmappedSamples.size());
        ObjectNode inputHashes = report.putObject("inputSha256");
        inputHashes.put("sourceRuleInventory", sha256(inventoryFile));
        inputHashes.put("validationProfiles", sha256(profilesFile));
        report.set("ruleCoverage", ruleCoverage);
        report.set("evaluations", evaluations);
        report.set("unmappedReviewSamples", unmappedSamples);
        Files.createDirectories(output.toAbsolutePath().getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), report);
        System.out.println("run=" + runId + " messages=" + messageCount + " ruleInventory=" + rules.size()
            + " executableProfileRules=" + executable.size() + " partialStructureRules=" + partialExecutors.size()
            + " evaluations=" + evaluations.size()
            + " coverage=" + coverageByClass + " outcomes=" + outcomesByClass);
    }

    private static Map<String, Long> classCounts(Map<String, JsonNode> rules) {
        Map<String, Long> counts = new TreeMap<>();
        rules.values().forEach(rule -> counts.merge(rule.path("class").asText(), 1L, Long::sum));
        return counts;
    }

    private static String failureDisposition(Atl105AiElementIntake.MessageAssessment assessment, Finding finding) {
        if (finding.status() != Status.INVALID) return "NOT_A_FAILURE";
        if (!"negative".equals(assessment.scenarioType())) return "NON_NEGATIVE_VIOLATION_CANDIDATE";
        if (assessment.violatedElement().isBlank()) return "NEGATIVE_TARGET_UNRESOLVED";
        if (finding.element().equals(assessment.violatedElement())) return "EXPECTED_DECLARED_NEGATIVE_TARGET_DETECTION";
        return "NEGATIVE_UNRELATED_VIOLATION_REVIEW";
    }

    private static String sha256(Path path) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (var input = Files.newInputStream(path)) {
            byte[] buffer = new byte[1024 * 1024];
            int count;
            while ((count = input.read(buffer)) != -1) digest.update(buffer, 0, count);
        }
        return HexFormat.of().formatHex(digest.digest());
    }
}