package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Atl105ElementValueValidator;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

/** Executes bounded Chapter 13 probes and reconciles existing independent BR anchors. */
public final class GenerateChapter13TrainingEvidence {
    private final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        new GenerateChapter13TrainingEvidence().generate(Atl105Paths.testJson("chapter-13-training-package.json"),
                Atl105Paths.testJson("chapter-13-training-evidence.json"));
    }

    public ObjectNode generate(Path packageFile, Path evidenceFile) throws IOException {
        Path source = Atl105Paths.docs().resolve("specs").resolve("extracted_text.txt");
        Path profileFile = Atl105Paths.docs().resolve("specs").resolve("kb").resolve("elements").resolve("validation-profiles.json");
        Path probesFile = Atl105Paths.testJson("chapter-13-element-probes.json");
        Path existingFile = Atl105Paths.testOutput().resolve("test-solution-independent-review")
                .resolve("complete-all-test-solution-br-ts-tc-td-package.json");
        ObjectNode inventory = new Atl105ElementInventory().extract(Files.readString(source));
        Map<String, JsonNode> profiles = new HashMap<>();
        JsonNode profileDocument = mapper.readTree(profileFile.toFile());
        for (JsonNode profile : profileDocument.path("profiles")) profiles.put(profile.path("id").asText(), profile);
        JsonNode existing = mapper.readTree(existingFile.toFile());
        ObjectNode artifacts = mapper.createObjectNode();
        artifacts.putObject("manifest").put("packageId", "ATL105-TEST-CH13-BOUNDED-REPRESENTATIONS")
                .put("specification", "ATL105").put("specificationVersion", "2026-3");
        var brs = artifacts.putArray("businessRequirements");
        var scenarios = artifacts.putArray("testScenarios");
        var cases = artifacts.putArray("testCases");
        var data = artifacts.putArray("testData");
        ObjectNode report = mapper.createObjectNode().put("specification", "ATL105").put("specificationVersion", "2026-3")
                .put("producer", "TEST_SOLUTION").put("completeChapterCoverage", false).put("executionReady", false)
                .put("fullAiIntakeWired", false).put("approvedRequirements", 0)
                .put("policy", "Target representation findings are distinct from whole-observation results. "
                        + "Partial observations and candidate anchors are not full rule semantics, approval or complete-message evidence.");
        report.putObject("inputSha256").put("source", sha256(source)).put("profiles", sha256(profileFile))
                .put("probes", sha256(probesFile)).put("existingCanonicalPackage", sha256(existingFile));
        var executions = report.putArray("executions");
        var validator = new Atl105ElementValueValidator(Atl105Paths.root());
        for (JsonNode fixture : mapper.readTree(probesFile.toFile()).path("profiles")) {
            String profileId = fixture.path("profileId").asText();
            JsonNode profile = profiles.get(profileId);
            if (profile == null) throw new IOException("Probe references missing profile " + profileId);
            String element = profile.path("element").asText();
            JsonNode catalog = mapper.readTree(Atl105Paths.docs().resolve("specs").resolve("kb")
                    .resolve(profile.path("existingRuleCatalog").asText()).toFile());
            JsonNode rule = null;
            for (JsonNode candidate : catalog.path("rules")) {
                if (profile.path("existingRuleId").asText().equals(candidate.path("ruleId").asText())) rule = candidate;
            }
            if (rule == null) throw new IOException("No existing catalog rule for profile " + profileId);
            JsonNode anchor = rule.path("sourceAnchor");
            String key = CanonicalSourceAnchorIdentity.key(mapper, anchor);
            var candidateIds = mapper.createArrayNode();
            for (JsonNode br : existing.path("businessRequirements")) {
                for (JsonNode candidate : br.path("sourceAnchors")) {
                    if (key.equals(CanonicalSourceAnchorIdentity.key(mapper, candidate))) {
                        candidateIds.add(br.path("id").asText());
                        break;
                    }
                }
            }
            if (candidateIds.isEmpty()) throw new IOException("No existing Test BR anchor candidate for " + profileId);
            ObjectNode chapterAnchor = mapper.createObjectNode().put("specification", "ATL105").put("version", "2026-3")
                    .put("section", "13.2").put("segment", profile.path("segment").asText())
                    .put("element", element).put("rule", profileId);
            String stem = "TEST-CH13-" + profileId;
            ObjectNode br = brs.addObject().put("id", "BR-" + stem).put("title", "Bounded representation: " + profileId)
                    .put("executionStatus", "REVIEW_REQUIRED");
            anchors(br, anchor, chapterAnchor);
            ObjectNode scenario = scenarios.addObject().put("id", "TS-" + stem).put("status", "REVIEW_REQUIRED");
            scenario.putArray("requirementIds").add("BR-" + stem);
            anchors(scenario, anchor, chapterAnchor);
            int index = 0;
            for (String kind : List.of("positive", "negative", "review", "nonText", "missing")) {
                var values = mapper.createArrayNode();
                if ("nonText".equals(kind)) values.add(0);
                else if ("missing".equals(kind)) { if (profile.path("required").asBoolean()) values.addNull(); }
                else {
                    JsonNode configured = fixture.path(kind);
                    if (!configured.isMissingNode() && !configured.isArray()) {
                        throw new IOException("Probe values must be an array for " + profileId + "/" + kind);
                    }
                    for (JsonNode value : configured) values.add(value);
                }
                for (JsonNode value : values) {
                    Status expected = "positive".equals(kind) ? Status.CHECKS_PASSED
                            : "negative".equals(kind) || "nonText".equals(kind) ? Status.INVALID : Status.REVIEW_REQUIRED;
                    ObjectNode observation = mapper.createObjectNode().put("specificationVersion", "2026-3")
                            .put("messageFamily", profile.path("messageFamily").asText())
                            .put("segment", profile.path("segment").asText()).put("completeness", "PARTIAL");
                    ObjectNode elements = observation.putObject("elements");
                    if (!"missing".equals(kind)) elements.set(element, value);
                    var result = validator.validate(observation);
                    var target = result.findings().stream().filter(finding -> element.equals(finding.element())).toList();
                    if (target.size() != 1 || target.getFirst().status() != expected) {
                        throw new IOException("Unexpected target probe result for " + profileId + "/" + kind + ": " + target);
                    }
                    if (expected == Status.INVALID && result.status() != Status.INVALID) {
                        throw new IOException("Invalid target did not invalidate observation");
                    }
                    String suffix = "-" + kind.toUpperCase(java.util.Locale.ROOT) + "-" + (++index);
                    String tcId = "TC-" + stem + suffix;
                    String tdId = "TD-" + stem + suffix;
                    String outcome = expected == Status.INVALID ? "FAIL" : "REVIEW_REQUIRED";
                    ObjectNode tc = cases.addObject().put("id", tcId).put("expectedOutcome", outcome).put("status", "REVIEW_REQUIRED");
                    tc.putArray("scenarioIds").add("TS-" + stem);
                    anchors(tc, anchor, chapterAnchor);
                    ObjectNode td = data.addObject().put("id", tdId).put("readiness", "REVIEW_REQUIRED").put("expectedValidation", outcome);
                    td.putArray("testCaseIds").add(tcId);
                    anchors(td, anchor, chapterAnchor);
                    td.set("payload", observation);
                    ObjectNode execution = executions.addObject().put("profileId", profileId).put("element", element)
                            .put("testCaseId", tcId).put("testDataId", tdId).put("expectedTargetStatus", expected.name())
                            .put("actualTargetStatus", target.getFirst().status().name()).put("observationStatus", result.status().name())
                            .put("sourceLine", inventory.path("elements").path(element).path("definitions").path(0).path("sourceLine").asInt())
                            .put("limitations", profile.path("limitations").asText()).put("candidateMatchStatus", "REVIEW_REQUIRED");
                    execution.set("findings", mapper.valueToTree(result.findings()));
                    execution.set("existingTestArtifactAnchorCandidates", candidateIds.deepCopy());
                    anchors(execution, anchor, chapterAnchor);
                }
            }
        }
        var traceability = new CanonicalTraceabilityValidator().validate(mapper.treeToValue(artifacts, CanonicalArtifactPackage.class));
        if (!traceability.isValid()) throw new IOException("Chapter 13 chain invalid: " + traceability.errors());
        var rows = report.putArray("sourceElementReconciliation");
        int baselineExecutions = 0;
        for (JsonNode element : inventory.path("elements")) {
            String id = element.path("element").asText();
            ObjectNode row = rows.addObject().put("element", id).put("completeSemanticCoverage", false);
            var ids = row.putArray("contextualProfileIds");
            for (JsonNode profile : profiles.values().stream().sorted(java.util.Comparator.comparing(p -> p.path("id").asText())).toList()) {
                if (id.equals(profile.path("element").asText())) ids.add(profile.path("id").asText());
            }
            JsonNode constraint = element.path("definitions").path(0).path("constraints");
            if (element.path("definitions").size() != 1 || !"EXTRACTED".equals(constraint.path("extractionStatus").asText())) {
                row.put("baselineStatus", "REVIEW_REQUIRED_MULTIPLE_OR_UNEXTRACTED_DEFINITION");
                continue;
            }
            row.set("sourceConstraint", constraint.deepCopy());
            int maximum = constraint.path("maxLength").asInt();
            String synthetic = ("N".equals(constraint.path("characterType").asText()) ? "1" : "A").repeat(maximum);
            var baseline = row.putArray("baselineExecutions");
            for (boolean negative : List.of(false, true)) {
                ObjectNode observation = mapper.createObjectNode().put("specificationVersion", "2026-3")
                        .put("messageFamily", "Unverified Request").put("segment", "UNVERIFIED");
                observation.putObject("elements").put(id, synthetic + (negative ? "1" : ""));
                var result = validator.validate(observation);
                Status expected = negative ? Status.INVALID : Status.REVIEW_REQUIRED;
                if (result.status() != expected) throw new IOException("Unexpected baseline mutation outcome for Element " + id);
                ObjectNode execution = baseline.addObject().put("probe", negative ? "MAX_PLUS_ONE" : "MAX_SHAPE")
                        .put("expectedStatus", expected.name()).put("actualStatus", result.status().name());
                execution.set("findings", mapper.valueToTree(result.findings()));
                baselineExecutions++;
            }
            row.put("baselineStatus", "EXECUTED_TYPE_LENGTH_ONLY_REVIEW_REQUIRED");
        }
        report.putObject("summary").put("sourceDefinitions", inventory.path("definitionCount").asInt())
                .put("sourceElementIdentities", rows.size()).put("contextualProfiles", profiles.size())
                .put("executedProfileChains", brs.size()).put("businessRequirements", brs.size()).put("scenarios", scenarios.size())
                .put("testCases", cases.size()).put("testDataRecords", data.size()).put("executedContextualProbes", executions.size())
                .put("executedBaselineProbes", baselineExecutions).put("traceabilityValid", true);
        Files.createDirectories(packageFile.toAbsolutePath().getParent());
        Files.createDirectories(evidenceFile.toAbsolutePath().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(packageFile.toFile(), artifacts);
        mapper.writerWithDefaultPrettyPrinter().writeValue(evidenceFile.toFile(), report);
        return report;
    }

    private static void anchors(ObjectNode artifact, JsonNode original, JsonNode chapter) {
        artifact.putArray("sourceAnchors").add(original.deepCopy()).add(chapter.deepCopy());
    }

    private static String sha256(Path file) throws IOException {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(file)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("Required SHA-256 algorithm unavailable", exception);
        }
    }
}
