package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.AppendixILogicalDataValidator;
import com.coreauth.validator.canonical.AppendixIScopedCandidateValidator;
import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class GenerateAppendixIScopedTraining {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final List<String> BATCHES = List.of("001-009", "010-048", "049", "050-067", "068-081");
    private static final String SOURCE_HASH = "6d03bf09f25b9126975b584aef83c21d1b7b081cabccfb9af3b89d46a26d06aa";

    public record Output(ObjectNode artifactPackage, ObjectNode evidence) {
    }

    private GenerateAppendixIScopedTraining() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 1) throw new IllegalArgumentException("Usage: [ATL105 root]");
        Path root = args.length == 0 ? Path.of("specifications", "ATL105") : Path.of(args[0]);
        Output output = generate(root);
        Path destination = root.resolve(Path.of("test-output", "test-json", "appendix-i-scoped-training"));
        Files.createDirectories(destination.resolve("fixtures"));
        write(destination.resolve("package.json"), output.artifactPackage());
        write(destination.resolve("evidence.json"), output.evidence());
        for (JsonNode data : output.artifactPackage().path("testData")) {
            write(destination.resolve(data.path("fileName").asText()), data);
        }
        System.out.println("Generated 11 scoped review-only BR/TS and 22 linked logical TC/TD; "
                + "11 supplied mutations detected. Full requests and certification remain incomplete.");
    }

    public static Output generate(Path root) throws IOException {
        Path knowledge = root.resolve(Path.of("test-output", "test-json", "knowledge"));
        String sourceHash = hash(root.resolve(Path.of("docs", "specs", "extracted_text.txt")));
        if (!SOURCE_HASH.equals(sourceHash)) throw new IllegalStateException("ATL105 source hash changed; review required.");
        Map<String, JsonNode> rules = new LinkedHashMap<>();
        Set<String> tableIds = new HashSet<>();
        ObjectNode report = MAPPER.createObjectNode();
        report.put("status", "IN_PROGRESS").put("scope", "EXECUTED_LOGICAL_PREDICATES_NOT_FULL_MESSAGES");
        report.put("specification", "ATL105").put("specificationVersion", "2026-3");
        report.put("sourceSha256", sourceHash);
        var inputHashes = report.putArray("inputHashes");
        for (String batch : BATCHES) {
            Path path = knowledge.resolve("appendix-i-" + batch + ".json");
            inputHashes.addObject().put("file", path.getFileName().toString()).put("sha256", hash(path));
            JsonNode data = MAPPER.readTree(path.toFile());
            if (!"ATL105".equals(data.path("specification").asText())
                    || !"2026-3".equals(data.path("specificationVersion").asText())
                    || !"DRAFT_REVIEW_REQUIRED".equals(data.path("status").asText())) {
                throw new IllegalStateException("Unexpected knowledge identity or readiness: " + path);
            }
            for (JsonNode table : data.path("tables")) {
                if (!tableIds.add(table.path("tableId").asText())) throw new IllegalStateException("Duplicate table");
                for (JsonNode rule : table.path("rules")) {
                    if (rules.putIfAbsent(rule.path("id").asText(), rule) != null) {
                        throw new IllegalStateException("Duplicate rule");
                    }
                }
            }
        }
        Set<String> sourceTables = new HashSet<>();
        GenerateAppendixITrainingInventory.generate(root).path("tables")
                .forEach(table -> sourceTables.add(table.path("tableId").asText()));
        if (!tableIds.equals(sourceTables)) throw new IllegalStateException("Knowledge/source table identity mismatch");

        Path candidatePath = knowledge.resolve("appendix-i-candidates-010-048.json");
        inputHashes.addObject().put("file", candidatePath.getFileName().toString()).put("sha256", hash(candidatePath));
        JsonNode candidates = MAPPER.readTree(candidatePath.toFile());
        if (!"ATL105".equals(candidates.path("specification").asText())
                || !"2026-3".equals(candidates.path("specificationVersion").asText())
                || !"REVIEW_REQUIRED".equals(candidates.path("readiness").asText())
                || !"LOGICAL_FRAGMENT".equals(candidates.path("payloadScope").asText())
                || !"appendix-i-010-048.json".equals(candidates.path("knowledgeReference").asText())) {
            throw new IllegalStateException("Unexpected candidate scope or readiness");
        }
        ObjectNode pkg = MAPPER.createObjectNode();
        pkg.putObject("manifest").put("packageId", "ATL105-APPENDIX-I-SCOPED-TRAINING-001")
                .put("specification", "ATL105").put("specificationVersion", "2026-3")
                .put("artifactContractVersion", "2").put("strictExecutionContract", true);
        var brs = pkg.putArray("businessRequirements");
        var scenarios = pkg.putArray("testScenarios");
        var cases = pkg.putArray("testCases");
        var data = pkg.putArray("testData");
        var evaluations = report.putArray("evaluations");
        Set<String> seen = new HashSet<>();
        Map<String, Set<String>> supportingCandidates = new LinkedHashMap<>();
        AppendixIScopedCandidateValidator validator = new AppendixIScopedCandidateValidator();
        for (JsonNode candidate : candidates.path("candidates")) {
            String id = candidate.path("candidateId").asText();
            if (!AppendixIScopedCandidateValidator.CANDIDATE_IDS.contains(id)) continue;
            if (!seen.add(id) || !candidate.isObject()
                    || !"REVIEW_REQUIRED".equals(candidate.path("readiness").asText())
                    || !"LOGICAL_FRAGMENT".equals(candidate.path("payloadScope").asText())
                    || candidate.path("ruleIds").isEmpty()) {
                throw new IllegalStateException("Invalid or duplicate candidate " + id);
            }
            for (JsonNode ref : candidate.path("ruleIds")) {
                if (!rules.containsKey(ref.asText())) throw new IllegalStateException("Missing source rule " + ref);
                supportingCandidates.computeIfAbsent(ref.asText(), ignored -> new HashSet<>()).add(id);
            }
            String target = AppendixIScopedCandidateValidator.mutationTarget(id);
            var anchors = MAPPER.createArrayNode();
            ObjectNode anchor = rules.get(candidate.path("ruleIds").get(0).asText()).path("sourceAnchor").deepCopy();
            anchor.put("rule", "scoped-" + target.toLowerCase(java.util.Locale.ROOT) + "-" + id);
            anchors.add(anchor);
            String brId = "BR-" + id;
            String tsId = "TS-" + id;
            ObjectNode br = brs.addObject();
            br.put("id", brId).put("title", "Scoped logical check " + target + ": " + candidate.path("clearScope"))
                    .put("category", "BOUNDED_LOGICAL_PREDICATE").put("applicability",
                            "Supplied synthetic candidate context only; not complete source-rule coverage")
                    .put("priority", "high").put("executionStatus", "REVIEW_REQUIRED");
            br.set("sourceAnchors", anchors.deepCopy());
            ObjectNode scenario = scenarios.addObject();
            scenario.put("id", tsId).put("status", "REVIEW_REQUIRED");
            scenario.putArray("requirementIds").add(brId);
            scenario.set("sourceAnchors", anchors.deepCopy());
            for (boolean positive : new boolean[]{true, false}) {
                String suffix = positive ? "-POS" : "-NEG";
                ObjectNode input = positive ? candidate.deepCopy() : mutate(candidate);
                var result = validator.validate(input);
                AppendixILogicalDataValidator.Status expected = positive
                        ? AppendixILogicalDataValidator.Status.PASS : AppendixILogicalDataValidator.Status.FAIL;
                if (result.findings().stream().noneMatch(f -> target.equals(f.ruleId()) && f.status() == expected)
                        || (positive && result.findings().stream().anyMatch(f ->
                        f.status() == AppendixILogicalDataValidator.Status.FAIL))) {
                    throw new IllegalStateException("Target predicate did not produce expected outcome: " + id + suffix);
                }
                String tcId = "TC-" + id + suffix;
                String tdId = "TD-" + id + suffix;
                String file = "fixtures/" + tdId + ".json";
                ObjectNode tc = cases.addObject();
                tc.put("id", tcId).put("expectedOutcome", expected.name()).put("status", "REVIEW_REQUIRED")
                        .put("category", "logical-predicate").put("priority", "high").put("testDataFile", file);
                tc.putArray("scenarioIds").add(tsId);
                tc.set("sourceAnchors", anchors.deepCopy());
                ObjectNode td = data.addObject();
                td.put("id", tdId).put("expectedValidation", expected.name()).put("readiness", "REVIEW_REQUIRED")
                        .put("fileName", file);
                td.putArray("testCaseIds").add(tcId);
                td.set("sourceAnchors", anchors.deepCopy());
                ObjectNode payload = td.putObject("payload");
                payload.put("scope", "LOGICAL_FRAGMENT_NOT_COMPLETE_REQUEST");
                payload.putObject("request").set("appendixILogicalCandidate", input);
                td.putObject("response").put("assessment", "NOT_EXECUTED_NO_HOST_RESPONSE");
                ObjectNode evaluation = evaluations.addObject();
                evaluation.put("candidateId", id).put("testDataId", tdId).put("mutationTarget", target)
                        .put("expectedTargetOutcome", expected.name()).put("targetOutcomeVerified", true);
                evaluation.set("sourceRuleIds", candidate.path("ruleIds").deepCopy());
                evaluation.set("sourceEvidence", candidate.path("sourceEvidence").deepCopy());
                evaluation.set("excludedClaims", candidate.path("excludedClaims").deepCopy());
                evaluation.set("findings", MAPPER.valueToTree(result.findings()));
                evaluation.put("certified", false);
            }
        }
        if (!seen.equals(AppendixIScopedCandidateValidator.CANDIDATE_IDS)) {
            throw new IllegalStateException("Implemented candidate inventory is incomplete");
        }
        var canonical = MAPPER.treeToValue(pkg, CanonicalArtifactPackage.class);
        var traceability = new CanonicalTraceabilityValidator().validate(canonical);
        if (!traceability.errors().isEmpty()) throw new IllegalStateException("Canonical chain errors: " + traceability.errors());
        report.put("canonicalTraceabilityValid", true);
        report.put("baselineDenominatorKind", "DRAFT_RULE_INVENTORY_NOT_PROOF_OF_SOURCE_COMPLETENESS");
        report.put("draftTableCount", tableIds.size()).put("draftRuleCount", rules.size());
        report.put("scopedCandidateCount", seen.size()).put("logicalEvaluationCount", evaluations.size());
        report.put("suppliedMutationCount", seen.size()).put("detectedSuppliedMutationCount", seen.size());
        report.put("appendixWideMutationThresholdEstablished", false).put("approvedRuleCount", 0)
                .put("completeRequestCount", 0).put("certifiedCaseCount", 0);
        report.put("aiComparison", "DEFERRED_BY_USER").put("mergeToDevelop", "USER_APPROVAL_REQUIRED");
        var ledger = report.putArray("ruleLedger");
        rules.forEach((ruleId, rule) -> {
            ObjectNode row = ledger.addObject();
            row.put("ruleId", ruleId).put("tableId", ruleId.substring(6, 9))
                    .put("coverageStatus", supportingCandidates.containsKey(ruleId)
                            ? "PARTIAL_LOGICAL_EVIDENCE_REVIEW_REQUIRED" : "NO_EVIDENCE_IN_THIS_SCOPED_BATCH");
            row.set("sourceAnchor", rule.path("sourceAnchor").deepCopy());
            var refs = row.putArray("scopedCandidateIds");
            supportingCandidates.getOrDefault(ruleId, Set.of()).stream().sorted().forEach(refs::add);
            row.put("completeRuleCoverage", false);
        });
        report.putArray("blockers").add("source-to-knowledge-semantic-completeness-review")
                .add("remaining-rule-predicates-and-canonical-chains")
                .add("full-message-and-lifecycle-fixtures")
                .add("appendix-wide-boundary-and-mutation-reconciliation")
                .add("sme-tba-certification-pending");
        return new Output(pkg, report);
    }

    public static ObjectNode mutate(JsonNode candidate) {
        if (candidate == null || !candidate.isObject()) {
            throw new IllegalArgumentException("A structured candidate is required for mutation");
        }
        ObjectNode result = candidate.deepCopy();
        String path = candidate.path("mutation").path("path").asText();
        JsonNode replacement = candidate.path("mutation").get("value");
        if (path.isBlank() || replacement == null) throw new IllegalArgumentException("Missing targeted mutation");
        String[] parts = path.split("\\.", -1);
        ObjectNode parent = result.has(parts[0]) ? result
                : result.path("logicalFields").isObject() ? (ObjectNode) result.path("logicalFields") : null;
        if (parent == null) throw new IllegalArgumentException("Mutation path is absent: " + path);
        for (int i = 0; i < parts.length - 1; i++) {
            JsonNode next = parent.get(parts[i]);
            if (next == null || !next.isObject()) throw new IllegalArgumentException("Mutation parent is absent: " + path);
            parent = (ObjectNode) next;
        }
        String field = parts[parts.length - 1];
        if (!parent.has(field)) throw new IllegalArgumentException("Mutation field is absent: " + path);
        parent.set(field, replacement.deepCopy());
        return result;
    }

    private static void write(Path path, JsonNode node) throws IOException {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), node);
    }

    private static String hash(Path path) throws IOException {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(path)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 unavailable", exception);
        }
    }
}
