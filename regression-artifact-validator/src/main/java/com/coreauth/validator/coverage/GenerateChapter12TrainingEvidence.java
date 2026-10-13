package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator;
import com.coreauth.validator.canonical.SourceAnchor;
import com.coreauth.validator.canonical.Segment119ActualPayloadAdapter;
import com.coreauth.validator.canonical.ProductActualPayloadAdapter;
import com.coreauth.validator.canonical.Chapter12ActualPayloadAdapter;
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
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/** Persists independently executed canonical chains; never counts an entire segment as trained. */
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
        Segment119ActualPayloadAdapter actualAdapter = new Segment119ActualPayloadAdapter(mapper.readTree(Atl105Paths.testOutput()
                .resolve("test-solution-independent-review").resolve("all-element-inventory.json").toFile()));
        List<JsonNode> fixtures = new ArrayList<>();
        JsonNode observations = mapper.readTree(Atl105Paths.testJson("chapter-12-segment-observations.json").toFile());
        Path extensionFile = Atl105Paths.testJson("chapter-12-source-local-probes.json");
        JsonNode extension = mapper.readTree(extensionFile.toFile());
        report.put("sourceLocalProbesSha256", sha256(extensionFile));
        ObjectNode legacyHashes = report.putObject("legacySyntheticSourceSha256");
        for (JsonNode profile : extension.path("legacyActualProfiles"))
            if (profile.has("sourceFile")) legacyHashes.put(profile.path("sourceFile").asText(), sha256(Atl105Paths.root().resolve(profile.path("sourceFile").asText())));
        fixtures.addAll(Chapter12SourceLocalFixtures.expand(observations, extension));
        for (JsonNode fixture : observations.path("observations")) {
            fixtures.add(fixture);
            for (JsonNode probe : fixture.path("actualPayloadProbes")) {
                ObjectNode expanded = mapper.createObjectNode().put("id", probe.path("id").asText())
                        .put("ruleId", probe.path("ruleId").asText()).put("expectedStatus", probe.path("expectedStatus").asText())
                        .put("targetScope", probe.path("scope").asText()).put("expectedTargetStatus", probe.path("expectedTargetStatus").asText());
                expanded.put("targetRuleId", probe.path("targetRuleId").asText(probe.path("ruleId").asText()));
                ObjectNode payload = fixture.path("payload").deepCopy();
                ObjectNode actual = fixture.path("actualPayload").deepCopy();
                if (probe.has("actualFields")) {
                    var changes = probe.path("actualFields").fields();
                    while (changes.hasNext()) {
                        var entry = changes.next();
                        actual.set(entry.getKey(), entry.getValue().deepCopy());
                    }
                }
                payload.set("actualPayload", actual);
                payload.set("actualEvidence", probe.has("evidence") ? probe.path("evidence").deepCopy() : fixture.path("actualEvidence").deepCopy());
                expanded.set("payload", payload);
                fixtures.add(expanded);
            }
            for (JsonNode probe : fixture.path("predicateProbes")) {
                ObjectNode expanded = mapper.createObjectNode().put("id", probe.path("id").asText())
                        .put("ruleId", probe.path("ruleId").asText()).put("expectedStatus", probe.path("expectedStatus").asText())
                        .put("targetScope", probe.path("scope").asText()).put("expectedTargetStatus", probe.path("expectedTargetStatus").asText());
                expanded.put("targetRuleId", probe.path("targetRuleId").asText(probe.path("ruleId").asText()));
                ObjectNode payload = fixture.path("payload").deepCopy();
                if (probe.has("elements")) {
                    var changes = probe.path("elements").fields();
                    while (changes.hasNext()) {
                        var entry = changes.next();
                        ((ObjectNode) payload.path("elements")).set(entry.getKey(), entry.getValue().deepCopy());
                    }
                }
                for (String field : List.of("context", "serializedSegment", "serializedSegmentHex", "cardBuckets", "history", "qualifiers", "tables", "messageFamily", "direction", "floorLimitGroups", "encodedTableData", "marketBasketData", "taxProducts")) {
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
        for (JsonNode fixture : observations.path("productExecutions")) {
            fixtures.add(fixture);
            String segment = fixture.path("payload").path("segment").asText();
            String name = "102".equals(segment) ? "Product Code Data Segment" : "Adjusted Product Code Data Segment";
            for (JsonNode probe : fixture.path("probes")) {
                ObjectNode expanded = mapper.createObjectNode().put("id", probe.path("id").asText())
                        .put("executionOnly", true).put("ruleId", probe.path("ruleId").asText())
                        .put("expectedStatus", probe.path("expectedStatus").asText());
                ObjectNode payload = fixture.path("payload").deepCopy();
                ObjectNode actual = (ObjectNode) payload.path("productPayload");
                if (probe.has("productSegment")) actual.set(name, probe.path("productSegment").deepCopy());
                else if (probe.has("productFields")) {
                    var changes = probe.path("productFields").fields();
                    while (changes.hasNext()) {
                        var entry = changes.next();
                        ((ObjectNode) actual.path(name)).set(entry.getKey(), entry.getValue().deepCopy());
                    }
                }
                if (probe.has("standardSegment")) actual.set("Standard Segment", probe.path("standardSegment").deepCopy());
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
            if (rule == null && "131".equals(segment) && ruleId.startsWith("SEG130-R-")) {
                // SEG130-R-023 documents Segment 131's inverted (no-separator) wire contrast inside
                // Segment 130's own catalog file; its finding only fires on Segment 131 payloads.
                JsonNode pairedCatalog = mapper.readTree(Atl105Paths.ruleCatalog("130").toFile());
                for (JsonNode candidate : pairedCatalog.path("rules")) if (ruleId.equals(candidate.path("ruleId").asText())) rule = candidate;
            }
            if (rule == null) throw new IOException("Fixture references missing source rule " + ruleId);
            JsonNode anchor = rule.path("sourceAnchor");
            String key = canonicalKey(anchor);
            boolean predicate = fixture.has("targetScope");
            String targetRuleId = fixture.path("targetRuleId").asText(ruleId);
            if (fixture.has("executionOnly") && !fixture.path("executionOnly").isBoolean()) throw new IOException("Execution-only control must be boolean");
            boolean executionOnly = fixture.path("executionOnly").asBoolean();
            String scope = executionOnly ? "actual-product-execution" : predicate ? fixture.path("targetScope").asText() : "header";
            String stem = predicate || executionOnly ? "TEST-CH12-" + segment + "-" + fixture.path("id").asText() : "TEST-CH12-" + segment + "-HEADER";
            ObjectNode br = brs.addObject().put("id", "BR-" + stem)
                    .put("title", "Bounded " + scope + " observation for " + ruleId + ": " + rule.path("title").asText())
                    .put("category", "field").put("applicability", executionOnly ? "Chapter 12 actual named product execution" : "Chapter 12 numbered-element observation")
                    .put("priority", "high").put("executionStatus", "REVIEW_REQUIRED");
            anchors(br, anchor);
            ObjectNode scenario = scenarios.addObject().put("id", "TS-" + stem).put("status", "REVIEW_REQUIRED");
            scenario.putArray("requirementIds").add("BR-" + stem);
            anchors(scenario, anchor);
            for (boolean negative : predicate || executionOnly ? List.of(false) : List.of(false, true)) {
                String suffix = negative ? "-WRONG-TYPE" : "-FIELDS";
                String tcId = "TC-" + stem + suffix;
                ObjectNode payload = fixture.path("payload").deepCopy();
                if (negative) {
                    if (Set.of("DL1", "DL8").contains(segment)) ((ObjectNode) payload.path("elements")).put("24", "&");
                    else ((ObjectNode) payload.path("elements")).put("85", "999");
                }
                Chapter12SegmentPayloadValidator.Result result;
                if (payload.has("envelopeObservation")) {
                    var assessed = new com.coreauth.validator.canonical.Chapter11MessageLayoutValidator().validate(payload.path("envelopeObservation"));
                    List<String> unassessed = new ArrayList<>();
                    for (JsonNode catalogRule : catalog.path("rules")) unassessed.add(catalogRule.path("ruleId").asText());
                    result = new Chapter12SegmentPayloadValidator.Result(assessed.status(), assessed.findings(), List.copyOf(unassessed));
                } else if (payload.has("actualMessage")) {
                    result = new com.coreauth.validator.canonical.Chapter12LegacyActualPayloadAdapter().validate(payload.path("actualMessage"), segment,
                            payload.path("messageFamily").asText(), payload.path("actualEvidence"));
                } else if (payload.has("productPayload")) {
                    var assessed = new ProductActualPayloadAdapter().validatePayload(payload.path("productPayload"), segment);
                    List<String> unassessed = new ArrayList<>();
                    for (JsonNode catalogRule : catalog.path("rules")) unassessed.add(catalogRule.path("ruleId").asText());
                    result = new Chapter12SegmentPayloadValidator.Result(assessed.isValid()
                            ? Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED : Chapter12SegmentPayloadValidator.Status.INVALID,
                            ProductActualPayloadAdapter.observedFindings(assessed, segment,
                                    payload.path("productPayload").path("102".equals(segment) ? "Product Code Data Segment" : "Adjusted Product Code Data Segment"),
                                    payload.path("productPayload").get("Standard Segment"), payload.path("productPayload").get("context")), List.copyOf(unassessed));
                } else if (payload.has("actualPayload")) {
                    result = "119".equals(segment) ? actualAdapter.validate(payload.path("actualPayload"),
                            payload.path("messageFamily").asText(), payload.path("actualEvidence"))
                            : new Chapter12ActualPayloadAdapter().validate(payload.path("actualPayload"), segment,
                            payload.path("messageFamily").asText(), payload.path("actualEvidence"),
                            payload.get("actualStandard"), payload.get("actualProducts"), payload.get("actualMerchant"));
                } else result = validator.validate(payload);
                String expectedStatus = negative ? "INVALID" : fixture.path("expectedStatus").asText();
                if (!expectedStatus.equals(result.status().name())) throw new IOException("Unexpected fixture outcome: " + fixture.path("id").asText()
                        + suffix + ", expected " + expectedStatus + ", got " + result);
                if (predicate) {
                    var targets = result.findings().stream().filter(f -> f.ruleId().equals(targetRuleId)
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
                testCase.putArray("tags").add("chapter-12").add(executionOnly ? "bounded-product-execution" : predicate ? "bounded-predicate" : "bounded-header")
                        .add(invalid ? "negative" : "positive-structure");
                anchors(testCase, anchor);
                ObjectNode td = data.addObject().put("id", "TD-" + stem + suffix).put("readiness", "REVIEW_REQUIRED")
                        .put("expectedValidation", invalid ? "FAIL" : "REVIEW_REQUIRED");
                td.putArray("testCaseIds").add(tcId);
                anchors(td, anchor);
                td.set("payload", payload);
                ObjectNode execution = executions.addObject().put("testCaseId", tcId).put("testDataId", td.path("id").asText())
                        .put("observedSegment", segment)
                        .put("businessRequirementId", "BR-" + stem).put("scenarioId", "TS-" + stem)
                        .put("ruleId", ruleId).put("scope", executionOnly ? scope : predicate ? fixture.path("targetScope").asText() : "Header/field observation, not complete catalog-rule semantics")
                        .put("expectedStatus", expectedStatus).put("actualStatus", result.status().name());
                if (predicate) {
                    execution.put("expectedTargetStatus", fixture.path("expectedTargetStatus").asText());
                    execution.put("targetRuleId", targetRuleId);
                    execution.put("actualTargetStatus", result.findings().stream().filter(f -> f.ruleId().equals(targetRuleId)
                            && f.scope().equals(fixture.path("targetScope").asText())).findFirst().orElseThrow().status().name());
                }
                execution.set("findings", mapper.valueToTree(result.findings()));
                execution.put("validationPath", payload.has("envelopeObservation") ? "SOURCE_TEMPLATE_OBSERVED_ENVELOPE_PRESENCE" :
                        payload.has("actualMessage") ? "ACTUAL_LEGACY_NAMED_FAMILY_EXECUTION" : payload.has("productPayload") ? "ACTUAL_NAMED_PRODUCT_EXECUTION" :
                        payload.has("actualPayload") ? "119".equals(segment) ? "ACTUAL_NAMED_SEGMENT119_WITH_OBSERVED_HISTORY"
                                : "ACTUAL_NAMED_CHAPTER12_OBSERVATION" : "NUMBERED_CHAPTER12_OBSERVATION");
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
        report.set("sourceSectionReconciliation", sourceSectionReconciliation(executions));
        report.set("sourceFacetReconciliation", Chapter12SourceFacetLedger.build(report.path("sourceSectionReconciliation"),
                executions, extension));
        report.set("atomicSourceClauseReconciliation", Chapter12AtomicClauseReconciliation.build(executions, report.path("sourceFacetReconciliation")));
        Files.createDirectories(packageFile.toAbsolutePath().getParent());
        Files.createDirectories(evidenceFile.toAbsolutePath().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(packageFile.toFile(), artifactPackage);
        mapper.writerWithDefaultPrettyPrinter().writeValue(evidenceFile.toFile(), report);
        return report;
    }

    private ObjectNode sourceSectionReconciliation(ArrayNode executions) throws IOException {
        var heading = Pattern.compile("^12\\.([0-9]+)\\s+.*");
        List<String> source = Files.readAllLines(Atl105Paths.docs().resolve(Path.of("specs", "extracted_text.txt")));
        Map<String, Integer> starts = new LinkedHashMap<>();
        int chapterEnd = -1;
        for (int i = 0; i < source.size(); i++) {
            String line = source.get(i).trim();
            if (starts.isEmpty() && !line.equals("12.1 Standard Message Data Segment")) continue;
            if (line.equals("13. Data Element Descriptions") && !starts.isEmpty()) { chapterEnd = i; break; }
            var match = heading.matcher(line);
            if (match.matches() && (match.group(1).equals("1") || !starts.isEmpty())) {
                String section = "12." + match.group(1);
                if (starts.put(section, i + 1) != null) throw new IOException("Ambiguous Chapter 12 source heading " + section);
            }
        }
        if (starts.size() != 49 || chapterEnd < 0) throw new IOException("Chapter 12 source boundaries do not reconcile to 49 sections");
        int expectedSection = 1;
        for (String section : starts.keySet()) {
            if (!section.equals("12." + expectedSection++)) throw new IOException("Chapter 12 source sections are missing or out of order: " + section);
        }
        List<JsonNode> rules = new ArrayList<>();
        Path kb = Atl105Paths.docs().resolve(Path.of("specs", "kb"));
        try (var directories = Files.list(kb)) {
            for (Path directory : directories.filter(Files::isDirectory)
                    .filter(p -> p.getFileName().toString().startsWith("segment-")).sorted().toList()) {
                String segment = directory.getFileName().toString().substring("segment-".length());
                Path catalog = Atl105Paths.ruleCatalog(segment);
                if (Files.isRegularFile(catalog)) mapper.readTree(catalog.toFile()).path("rules").forEach(rules::add);
            }
        }
        ObjectNode reconciliation = mapper.createObjectNode().put("status", "IN_PROGRESS").put("sourceSections", starts.size())
                .put("policy", "Source-section and catalog reconciliation only, not a complete source-facet denominator. "
                        + "Bounded executions are not full-rule completion. NO_EXECUTION_IN_THIS_PACKAGE does not imply absence of other segment tests.");
        ArrayNode sections = reconciliation.putArray("sections");
        List<String> identities = List.copyOf(starts.keySet());
        for (int i = 0; i < identities.size(); i++) {
            String section = identities.get(i);
            ObjectNode record = sections.addObject().put("section", section).put("status", "SOURCE_FACETS_NOT_CLOSED");
            record.putObject("sourceEvidence").put("startLine", starts.get(section))
                    .put("endLine", i + 1 < identities.size() ? starts.get(identities.get(i + 1)) - 1 : chapterEnd);
            ArrayNode catalogRules = record.putArray("catalogRules");
            var sectionReference = Pattern.compile("(^|[^0-9.])" + Pattern.quote(section) + "(\\.|[^0-9.]|$)");
            for (JsonNode rule : rules) {
                if (!sectionReference.matcher(rule.path("sourceAnchor").path("section").asText()).find()) continue;
                ObjectNode entry = catalogRules.addObject().put("ruleId", rule.path("ruleId").asText());
                entry.set("sourceAnchor", rule.path("sourceAnchor").deepCopy());
                ArrayNode checks = entry.putArray("executions");
                for (JsonNode execution : executions) if (rule.path("ruleId").asText().equals(execution.path("ruleId").asText())) {
                    ObjectNode check = checks.addObject().put("testCaseId", execution.path("testCaseId").asText())
                            .put("scope", execution.path("scope").asText()).put("status", execution.path("actualStatus").asText());
                    if (execution.has("targetRuleId")) {
                        check.set("targetRuleId", execution.path("targetRuleId"));
                        check.set("targetStatus", execution.path("actualTargetStatus"));
                    }
                }
                entry.put("status", checks.isEmpty() ? "NO_EXECUTION_IN_THIS_PACKAGE" : "BOUNDED_EXECUTION_ONLY");
            }
            record.put("catalogRuleCount", catalogRules.size());
            record.put("catalogReconciled", !catalogRules.isEmpty());
        }
        return reconciliation;
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
