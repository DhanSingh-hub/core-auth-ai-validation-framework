package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator;
import com.coreauth.validator.canonical.SourceAnchor;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

/** Persists actual observations and canonical header chains; never counts an entire segment as trained. */
public final class GenerateChapter12TrainingEvidence {
    private final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        new GenerateChapter12TrainingEvidence().generate(Atl105Paths.testJson("chapter-12-training-package.json"),
                Atl105Paths.testJson("chapter-12-training-evidence.json"));
    }

    public ObjectNode generate(Path packageFile, Path evidenceFile) throws IOException {
        ObjectNode existingHashes = mapper.createObjectNode();
        Map<String, List<String>> existing = existingAnchors(existingHashes);
        ObjectNode artifactPackage = mapper.createObjectNode();
        artifactPackage.putObject("manifest").put("packageId", "ATL105-TEST-CHAPTER12-BOUNDED-HEADERS")
                .put("specification", "ATL105").put("specificationVersion", "2026-3");
        ArrayNode brs = artifactPackage.putArray("businessRequirements");
        ArrayNode scenarios = artifactPackage.putArray("testScenarios");
        ArrayNode cases = artifactPackage.putArray("testCases");
        ArrayNode data = artifactPackage.putArray("testData");
        ObjectNode report = mapper.createObjectNode();
        report.put("artifact", "ATL105-CHAPTER12-EXECUTED-TRAINING-EVIDENCE");
        report.put("producer", "TEST_SOLUTION");
        report.put("specification", "ATL105").put("specificationVersion", "2026-3");
        report.put("sourceSha256", sha256(Atl105Paths.docs().resolve(Path.of("specs", "extracted_text.txt"))));
        report.put("observationsSha256", sha256(Atl105Paths.testJson("chapter-12-segment-observations.json")));
        report.put("policy", "Checks are bounded to finding.scope. A referenced catalog rule is not necessarily fully implemented. "
                + "Exact-anchor candidates are cross-check evidence, not SME approval or confirmed AI matches.");
        report.put("completeSpecCoverage", false);
        report.put("fullMessageIntakeWired", false);
        report.set("existingTestPackageSha256", existingHashes);
        ArrayNode executions = report.putArray("executions");
        Chapter12SegmentPayloadValidator validator = new Chapter12SegmentPayloadValidator();
        List<JsonNode> fixtures = new ArrayList<>();
        for (JsonNode fixture : mapper.readTree(Atl105Paths.testJson("chapter-12-segment-observations.json").toFile()).path("observations")) {
            fixtures.add(fixture);
            for (JsonNode probe : fixture.path("predicateProbes")) {
                ObjectNode expanded = mapper.createObjectNode().put("id", probe.path("id").asText())
                        .put("ruleId", probe.path("ruleId").asText()).put("expectedStatus", probe.path("expectedStatus").asText())
                        .put("targetScope", probe.path("scope").asText()).put("expectedTargetStatus", probe.path("expectedTargetStatus").asText());
                ObjectNode payload = fixture.path("payload").deepCopy();
                if (probe.has("elements")) {
                    var changes = probe.path("elements").fields();
                    while (changes.hasNext()) {
                        var entry = changes.next();
                        ((ObjectNode) payload.path("elements")).set(entry.getKey(), entry.getValue().deepCopy());
                    }
                }
                for (String field : List.of("context", "serializedSegment", "cardBuckets")) {
                    if (probe.has(field)) payload.set(field, probe.path(field).deepCopy());
                }
                if (probe.has("omitSerializedSegment")) {
                    if (!probe.path("omitSerializedSegment").isBoolean()) throw new IOException("Probe wire-omission control must be boolean");
                    if (probe.path("omitSerializedSegment").booleanValue()) payload.remove("serializedSegment");
                }
                expanded.set("payload", payload);
                fixtures.add(expanded);
            }
        }
        for (JsonNode fixture : fixtures) {
            String segment = fixture.path("payload").path("segment").asText();
            String ruleId = fixture.path("ruleId").asText();
            JsonNode catalog = mapper.readTree(Atl105Paths.ruleCatalog(segment).toFile());
            JsonNode rule = null;
            for (JsonNode candidate : catalog.path("rules")) if (ruleId.equals(candidate.path("ruleId").asText())) rule = candidate;
            if (rule == null) throw new IOException("Fixture references missing source rule " + ruleId);
            JsonNode anchor = rule.path("sourceAnchor");
            String key = canonicalKey(anchor);
            boolean predicate = fixture.has("targetScope");
            String stem = predicate ? "TEST-CH12-" + segment + "-" + fixture.path("id").asText() : "TEST-CH12-" + segment + "-HEADER";
            ObjectNode br = brs.addObject().put("id", "BR-" + stem)
                    .put("title", "Bounded " + (predicate ? fixture.path("targetScope").asText() : "header") + " observation for " + ruleId + ": " + rule.path("title").asText())
                    .put("category", "field").put("applicability", "Chapter 12 numbered-element observation")
                    .put("priority", "high").put("executionStatus", "REVIEW_REQUIRED");
            anchors(br, anchor);
            ObjectNode scenario = scenarios.addObject().put("id", "TS-" + stem).put("status", "REVIEW_REQUIRED");
            scenario.putArray("requirementIds").add("BR-" + stem);
            anchors(scenario, anchor);
            for (boolean negative : predicate ? List.of(false) : List.of(false, true)) {
                String suffix = negative ? "-WRONG-TYPE" : "-FIELDS";
                String tcId = "TC-" + stem + suffix;
                ObjectNode payload = fixture.path("payload").deepCopy();
                if (negative) ((ObjectNode) payload.path("elements")).put("85", "999");
                var result = validator.validate(payload);
                String expectedStatus = negative ? "INVALID" : fixture.path("expectedStatus").asText();
                if (!expectedStatus.equals(result.status().name())) throw new IOException("Unexpected fixture outcome: " + fixture.path("id").asText()
                        + suffix + ", expected " + expectedStatus + ", got " + result);
                if (predicate) {
                    var targets = result.findings().stream().filter(f -> f.ruleId().equals(ruleId)
                            && f.scope().equals(fixture.path("targetScope").asText())).toList();
                    if (targets.size() != 1 || !targets.getFirst().status().name().equals(fixture.path("expectedTargetStatus").asText())) {
                        throw new IOException("Unexpected predicate target for " + fixture.path("id").asText() + ": " + targets);
                    }
                }
                boolean invalid = "INVALID".equals(expectedStatus);
                ObjectNode testCase = cases.addObject().put("id", tcId)
                        .put("expectedOutcome", invalid ? "FAIL" : "REVIEW_REQUIRED")
                        .put("category", "field").put("priority", "high").put("status", "REVIEW_REQUIRED");
                testCase.putArray("scenarioIds").add("TS-" + stem);
                testCase.putArray("tags").add("chapter-12").add(predicate ? "bounded-predicate" : "bounded-header")
                        .add(invalid ? "negative" : "positive-structure");
                anchors(testCase, anchor);
                ObjectNode td = data.addObject().put("id", "TD-" + stem + suffix).put("readiness", "REVIEW_REQUIRED")
                        .put("expectedValidation", invalid ? "FAIL" : "REVIEW_REQUIRED");
                td.putArray("testCaseIds").add(tcId);
                anchors(td, anchor);
                td.set("payload", payload);
                ObjectNode execution = executions.addObject().put("testCaseId", tcId).put("testDataId", td.path("id").asText())
                        .put("ruleId", ruleId).put("scope", predicate ? fixture.path("targetScope").asText() : "Header/field observation, not complete catalog-rule semantics")
                        .put("expectedStatus", expectedStatus).put("actualStatus", result.status().name());
                if (predicate) {
                    execution.put("expectedTargetStatus", fixture.path("expectedTargetStatus").asText());
                    execution.put("actualTargetStatus", result.findings().stream().filter(f -> f.ruleId().equals(ruleId)
                            && f.scope().equals(fixture.path("targetScope").asText())).findFirst().orElseThrow().status().name());
                }
                execution.set("findings", mapper.valueToTree(result.findings()));
                execution.set("unassessedCatalogRules", mapper.valueToTree(result.unassessedCatalogRules()));
                execution.set("sourceAnchor", anchor.deepCopy());
                ArrayNode candidates = execution.putArray("existingTestArtifactAnchorCandidates");
                List<String> matching = existing.getOrDefault(key, List.of());
                for (String candidate : matching) candidates.add(candidate);
                execution.put("existingTestAnchorCrosscheck", matching.isEmpty() ? "NO_EXACT_ANCHOR_CANDIDATE" : "EXACT_ANCHOR_CANDIDATES_UNAPPROVED");
                execution.put("candidateMatchStatus", "REVIEW_REQUIRED");
            }
        }
        CanonicalArtifactPackage canonical = mapper.treeToValue(artifactPackage, CanonicalArtifactPackage.class);
        var traceability = new CanonicalTraceabilityValidator().validate(canonical);
        if (!traceability.isValid()) throw new IOException("Chapter 12 chain failed canonical validation: " + traceability.errors());
        report.putObject("summary").put("businessRequirements", brs.size()).put("scenarios", scenarios.size())
                .put("testCases", cases.size()).put("testDataRecords", data.size()).put("executedObservations", executions.size())
                .put("traceabilityValid", true).put("approvedRequirements", 0);
        Files.createDirectories(packageFile.toAbsolutePath().getParent());
        Files.createDirectories(evidenceFile.toAbsolutePath().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(packageFile.toFile(), artifactPackage);
        mapper.writerWithDefaultPrettyPrinter().writeValue(evidenceFile.toFile(), report);
        return report;
    }

    private Map<String, List<String>> existingAnchors(ObjectNode hashes) throws IOException {
        Map<String, List<String>> result = new HashMap<>();
        try (var files = Files.list(Atl105Paths.testJson())) {
            List<Path> inputs = new ArrayList<>(files.filter(p -> p.getFileName().toString().startsWith("segment-")
                    && p.getFileName().toString().endsWith(".json")).sorted().toList());
            inputs.add(Atl105Paths.testOutput().resolve("test-solution-independent-review")
                    .resolve("complete-all-test-solution-br-ts-tc-td-package.json"));
            for (Path file : inputs) {
                JsonNode document = mapper.readTree(file.toFile());
                hashes.put(file.getFileName().toString(), sha256(file));
                for (JsonNode br : document.path("businessRequirements")) {
                    List<JsonNode> anchors = new ArrayList<>();
                    br.path("sourceAnchors").forEach(anchors::add);
                    if (br.path("sourceAnchor").isObject()) anchors.add(br.path("sourceAnchor"));
                    for (JsonNode anchor : anchors) {
                        if (!"ATL105".equals(anchor.path("specification").asText())
                                || !"2026-3".equals(anchor.path("version").asText())) continue;
                        String key = canonicalKey(anchor);
                        result.computeIfAbsent(key, ignored -> new ArrayList<>())
                                .add(file.getFileName() + "#" + br.path("id").asText());
                    }
                }
            }
        }
        return result;
    }

    private String canonicalKey(JsonNode anchor) throws IOException {
        ObjectNode identity = mapper.createObjectNode();
        for (String field : List.of("specification", "version", "section", "segment", "element", "rule")) {
            if (anchor.has(field)) identity.set(field, anchor.path(field));
        }
        return mapper.treeToValue(identity, SourceAnchor.class).canonicalKey();
    }

    private static void anchors(ObjectNode artifact, JsonNode anchor) {
        artifact.putArray("sourceAnchors").add(anchor.deepCopy());
    }

    private static String sha256(Path file) throws IOException {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(file)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("Required SHA-256 algorithm unavailable", exception);
        }
    }
}
