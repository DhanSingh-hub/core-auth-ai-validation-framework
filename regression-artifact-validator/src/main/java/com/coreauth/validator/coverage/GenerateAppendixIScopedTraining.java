package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.AppendixILogicalDataValidator;
import com.coreauth.validator.canonical.AppendixIScopedCandidateValidator;
import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
        System.out.println("Generated 101 scoped review-only BR/TS and 218 linked logical TC/TD; "
            + "101 supplied mutations detected. Full requests and certification remain incomplete.");
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
        Path table049Path = knowledge.resolve("appendix-i-candidates-049.json");
        inputHashes.addObject().put("file", table049Path.getFileName().toString()).put("sha256", hash(table049Path));
        JsonNode table049 = MAPPER.readTree(table049Path.toFile());
        if (!"ATL105".equals(table049.path("specification").asText())
                || !"2026-3".equals(table049.path("specificationVersion").asText())
                || !"DRAFT_REVIEW_REQUIRED".equals(table049.path("status").asText())
                || !"REVIEW_REQUIRED".equals(table049.path("readiness").asText())
                || !"LOGICAL_FRAGMENT".equals(table049.path("payloadScope").asText())
                || !"appendix-i-049.json".equals(table049.path("knowledgeReference").asText())) {
            throw new IllegalStateException("Unexpected Table 049 candidate identity or readiness");
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
        Set<String> table049Seen = new HashSet<>();
        for (JsonNode candidate : table049.path("candidates")) {
            String id = candidate.path("candidateId").asText();
            if (!AppendixIScopedCandidateValidator.CANDIDATE_IDS.contains(id)) continue;
            if (!table049Seen.add(id) || !candidate.isObject()
                    || !"REVIEW_REQUIRED".equals(candidate.path("readiness").asText())
                    || !"LOGICAL_FRAGMENT".equals(candidate.path("payloadScope").asText())
                    || candidate.path("ruleIds").isEmpty()
                    || !"049".equals(candidate.path("tableId").asText())) {
                throw new IllegalStateException("Invalid or duplicate Table 049 candidate " + id);
            }
            for (JsonNode ref : candidate.path("ruleIds")) {
                if (!rules.containsKey(ref.asText())) throw new IllegalStateException("Missing Table 049 source rule " + ref);
                supportingCandidates.computeIfAbsent(ref.asText(), ignored -> new HashSet<>()).add(id);
            }
            String target = AppendixIScopedCandidateValidator.mutationTarget(id);
            JsonNode targetRule = rules.get(target);
            if (targetRule == null) throw new IllegalStateException("Missing target Table 049 rule " + target);
                ArrayNode anchors = MAPPER.createArrayNode();
                ObjectNode sourceAnchor = targetRule.path("sourceAnchor").deepCopy();
                sourceAnchor.put("rule", "scoped-" + target.toLowerCase(java.util.Locale.ROOT) + "-" + id);
                anchors.add(sourceAnchor);
            String brId = "BR-" + id;
            String tsId = "TS-" + id;
            ObjectNode br = brs.addObject();
            br.put("id", brId).put("title", "Scoped Table 049 logical check " + target + ": " + candidate.path("clearScope"))
                    .put("category", "BOUNDED_LOGICAL_PREDICATE")
                    .put("applicability", "Synthetic Table 049 nested-selector context only; not complete source-rule coverage")
                    .put("priority", "high").put("executionStatus", "REVIEW_REQUIRED");
            br.set("sourceAnchors", anchors.deepCopy());
            ObjectNode scenario = scenarios.addObject();
            scenario.put("id", tsId).put("status", "REVIEW_REQUIRED");
            scenario.putArray("requirementIds").add(brId);
            scenario.set("sourceAnchors", anchors.deepCopy());

            JsonNode positiveValues = candidate.path("structuredLogicalFields").path("positiveDataValues");
            if (!positiveValues.isArray() || positiveValues.isEmpty()) {
                throw new IllegalStateException("Table 049 candidate has no positive data values: " + id);
            }
            for (int index = 0; index < positiveValues.size(); index++) {
                ObjectNode positive = candidate.deepCopy();
                ObjectNode positiveFields = (ObjectNode) positive.path("structuredLogicalFields");
                String value = positiveValues.get(index).asText();
                positiveFields.put("baselinePositiveData", value);
                JsonNode lengthByValue = positiveFields.path("declaredDataLengthsByData");
                if (lengthByValue.has(value)) positiveFields.put("declaredDataLength", lengthByValue.path(value).asText());
                var positiveResult = validator.validate(positive);
                if (positiveResult.findings().stream().noneMatch(f -> target.equals(f.ruleId())
                        && f.status() == AppendixILogicalDataValidator.Status.PASS)
                        || positiveResult.findings().stream().anyMatch(f -> f.status() == AppendixILogicalDataValidator.Status.FAIL)) {
                    throw new IllegalStateException("Table 049 positive candidate failed " + id + " value " + value);
                }
                String suffix = positiveValues.size() == 1 ? "-POS" : String.format("-POS-%02d", index + 1);
                appendReviewOnlyLogicalCase(cases, data, evaluations, candidate, positive, anchors,
                        brId, tsId, target, suffix, AppendixILogicalDataValidator.Status.PASS, positiveResult);
            }
            ObjectNode negative = mutate(candidate);
            var negativeResult = validator.validate(negative);
            if (negativeResult.findings().stream().noneMatch(f -> target.equals(f.ruleId())
                    && f.status() == AppendixILogicalDataValidator.Status.FAIL)) {
                throw new IllegalStateException("Table 049 targeted mutation was not detected: " + id);
            }
            appendReviewOnlyLogicalCase(cases, data, evaluations, candidate, negative, anchors,
                    brId, tsId, target, "-NEG", AppendixILogicalDataValidator.Status.FAIL, negativeResult);
        }
        if (table049Seen.size() != table049.path("candidates").size()) {
            throw new IllegalStateException("Table 049 candidate inventory is incomplete");
        }
        seen.addAll(table049Seen);
        Path table050Path = knowledge.resolve("appendix-i-candidates-050-067.json");
        inputHashes.addObject().put("file", table050Path.getFileName().toString()).put("sha256", hash(table050Path));
        JsonNode table050Batch = MAPPER.readTree(table050Path.toFile());
        if (!"ATL105".equals(table050Batch.path("specification").asText())
                || !"2026-3".equals(table050Batch.path("specificationVersion").asText())
                || !"DRAFT_REVIEW_REQUIRED".equals(table050Batch.path("status").asText())
                || !"REVIEW_REQUIRED".equals(table050Batch.path("readiness").asText())
                || !"LOGICAL_FRAGMENT".equals(table050Batch.path("payloadScope").asText())
                || !"appendix-i-050-067.json".equals(table050Batch.path("knowledgeReference").asText())) {
            throw new IllegalStateException("Unexpected Table 050/051 candidate identity or readiness");
        }
        Set<String> table050Seen = new HashSet<>();
        for (JsonNode candidate : table050Batch.path("candidates")) {
            String id = candidate.path("candidateId").asText();
                if (!Set.of("APPI-C050-OUTER-001", "APPI-C051-OUTER-001", "APPI-C053-OUTER-001",
                        "APPI-C054-OUTER-001", "APPI-C055-OUTER-001", "APPI-C057-OUTER-001",
                        "APPI-C058-OUTER-001", "APPI-C059-OUTER-001", "APPI-C062-OUTER-001",
                        "APPI-C062-N01-001", "APPI-C062-N02-001").contains(id)) continue;
            if (!AppendixIScopedCandidateValidator.CANDIDATE_IDS.contains(id)
                    || !table050Seen.add(id) || !candidate.isObject()
                    || !"REVIEW_REQUIRED".equals(candidate.path("readiness").asText())
                    || !"LOGICAL_FRAGMENT".equals(candidate.path("payloadScope").asText())
                    || candidate.path("ruleIds").isEmpty()) {
                throw new IllegalStateException("Invalid or duplicate Table 050/051 candidate " + id);
            }
            for (JsonNode ref : candidate.path("ruleIds")) {
                if (!rules.containsKey(ref.asText())) throw new IllegalStateException("Missing Table 050/051 source rule " + ref);
                supportingCandidates.computeIfAbsent(ref.asText(), ignored -> new HashSet<>()).add(id);
            }
            String target = AppendixIScopedCandidateValidator.mutationTarget(id);
            JsonNode targetRule = rules.get(target);
            if (targetRule == null) throw new IllegalStateException("Missing Table 050/051 target rule " + target);
            ArrayNode anchors = MAPPER.createArrayNode();
            ObjectNode sourceAnchor = targetRule.path("sourceAnchor").deepCopy();
            sourceAnchor.put("rule", "scoped-" + target.toLowerCase(java.util.Locale.ROOT) + "-" + id);
            anchors.add(sourceAnchor);
            String brId = "BR-" + id;
            String tsId = "TS-" + id;
            ObjectNode br = brs.addObject();
            br.put("id", brId).put("title", "Scoped logical check " + target + ": " + candidate.path("clearScope"))
                    .put("category", "BOUNDED_LOGICAL_PREDICATE")
                    .put("applicability", "Supplied synthetic Table 050/051 context only; not complete source-rule coverage")
                    .put("priority", "high").put("executionStatus", "REVIEW_REQUIRED");
            br.set("sourceAnchors", anchors.deepCopy());
            ObjectNode scenario = scenarios.addObject();
            scenario.put("id", tsId).put("status", "REVIEW_REQUIRED");
            scenario.putArray("requirementIds").add(brId);
            scenario.set("sourceAnchors", anchors.deepCopy());
            ObjectNode positive = candidate.deepCopy();
            var positiveResult = validator.validate(positive);
            if (positiveResult.findings().stream().noneMatch(f -> target.equals(f.ruleId())
                    && f.status() == AppendixILogicalDataValidator.Status.PASS)
                    || positiveResult.findings().stream().anyMatch(f -> f.status() == AppendixILogicalDataValidator.Status.FAIL)) {
                throw new IllegalStateException("Table 050/051 positive candidate failed: " + id);
            }
            appendReviewOnlyLogicalCase(cases, data, evaluations, candidate, positive, anchors,
                    brId, tsId, target, "-POS", AppendixILogicalDataValidator.Status.PASS, positiveResult);
            ObjectNode negative = mutate(candidate);
            var negativeResult = validator.validate(negative);
            if (negativeResult.findings().stream().noneMatch(f -> target.equals(f.ruleId())
                    && f.status() == AppendixILogicalDataValidator.Status.FAIL)) {
                throw new IllegalStateException("Table 050/051 targeted mutation was not detected: " + id);
            }
            appendReviewOnlyLogicalCase(cases, data, evaluations, candidate, negative, anchors,
                    brId, tsId, target, "-NEG", AppendixILogicalDataValidator.Status.FAIL, negativeResult);
        }
        if (!table050Seen.equals(Set.of("APPI-C050-OUTER-001", "APPI-C051-OUTER-001",
            "APPI-C053-OUTER-001", "APPI-C054-OUTER-001", "APPI-C055-OUTER-001",
                "APPI-C057-OUTER-001", "APPI-C058-OUTER-001", "APPI-C059-OUTER-001",
                "APPI-C062-OUTER-001", "APPI-C062-N01-001", "APPI-C062-N02-001"))) {
            throw new IllegalStateException("Implemented later-table candidate inventory is incomplete");
        }
        seen.addAll(table050Seen);
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
        if ("APPI-C062-OUTER-001".equals(candidate.path("candidateId").asText())) {
            JsonNode mutation = candidate.path("mutation").path("cases").path(0);
            ObjectNode firstRecord = (ObjectNode) result.path("syntheticData").path("records").get(0);
            firstRecord.set("logicalLength", mutation.path("replacement").deepCopy());
            return result;
        }
        if (Set.of("APPI-C050-OUTER-001", "APPI-C051-OUTER-001", "APPI-C053-OUTER-001",
            "APPI-C054-OUTER-001", "APPI-C055-OUTER-001", "APPI-C057-OUTER-001",
            "APPI-C058-OUTER-001", "APPI-C059-OUTER-001", "APPI-C062-N01-001", "APPI-C062-N02-001")
                .contains(candidate.path("candidateId").asText())) {
            String id = candidate.path("candidateId").asText();
            int mutationIndex = "APPI-C053-OUTER-001".equals(id) ? 2 : 0;
            JsonNode mutation = candidate.path("mutation").path("cases").path(mutationIndex);
            String[] path = mutation.path("path").asText().split("\\.");
            ObjectNode parent = (ObjectNode) result.path("syntheticData");
            for (int index = 0; index < path.length - 1; index++) {
                JsonNode child = parent.path(path[index]);
                if (!child.isObject()) throw new IllegalArgumentException("Invalid mutation path: " + String.join(".", path));
                parent = (ObjectNode) child;
            }
            String field = path[path.length - 1];
            if ("remove".equals(mutation.path("operation").asText())) {
                parent.remove(field);
            } else {
                JsonNode replacement = mutation.get("replacement");
                if (replacement == null) throw new IllegalArgumentException("Missing mutation replacement: " + field);
                parent.set(field, replacement.deepCopy());
            }
            return result;
        }
        if ("049".equals(candidate.path("tableId").asText())) {
            ObjectNode fields = (ObjectNode) result.path("structuredLogicalFields");
            String baseline = fields.path("baselinePositiveData").asText();
            String target = candidate.path("mutation").path("target").asText();
            String replacement = candidate.path("mutation").path("to").asText();
            if ("data".equals(target)) {
                fields.put("baselinePositiveData", replacement);
            } else if (target.matches("data\\.character\\[[0-9]+]")) {
                int position = Integer.parseInt(target.replaceAll(".*\\[([0-9]+)]", "$1")) - 1;
                if (position < 0 || position >= baseline.length() || replacement.length() != 1) {
                    throw new IllegalArgumentException("Invalid Table 049 character mutation: " + target);
                }
                JsonNode declaredLengths = fields.path("declaredDataLengthsByData");
                if (!fields.has("declaredDataLength") && declaredLengths.has(baseline)) {
                    fields.put("declaredDataLength", declaredLengths.path(baseline).asText());
                }
                fields.put("baselinePositiveData", baseline.substring(0, position) + replacement
                        + baseline.substring(position + 1));
            } else {
                throw new IllegalArgumentException("Unsupported Table 049 mutation path: " + target);
            }
            return result;
        }
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

        private static void appendReviewOnlyLogicalCase(ArrayNode cases, ArrayNode data, ArrayNode evaluations,
            JsonNode sourceCandidate, ObjectNode input, ArrayNode anchors, String brId, String tsId,
            String target, String suffix, AppendixILogicalDataValidator.Status expected,
            AppendixILogicalDataValidator.Result result) {
        String candidateId = sourceCandidate.path("candidateId").asText();
        String tcId = "TC-" + candidateId + suffix;
        String tdId = "TD-" + candidateId + suffix;
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
        td.putObject("payload").put("scope", "LOGICAL_FRAGMENT_NOT_COMPLETE_REQUEST");
        ((ObjectNode) td.path("payload")).putObject("request").set("appendixILogicalCandidate", input);
        td.putObject("response").put("assessment", "NOT_EXECUTED_NO_HOST_RESPONSE");
        ObjectNode evaluation = evaluations.addObject();
        evaluation.put("candidateId", candidateId).put("testDataId", tdId).put("mutationTarget", target)
            .put("expectedTargetOutcome", expected.name()).put("targetOutcomeVerified", true);
        evaluation.set("sourceRuleIds", sourceCandidate.path("ruleIds").deepCopy());
        evaluation.set("sourceEvidence", sourceCandidate.path("sourceEvidence").deepCopy());
        evaluation.set("excludedClaims", sourceCandidate.path("excludedClaims").deepCopy());
        evaluation.set("findings", MAPPER.valueToTree(result.findings()));
        evaluation.put("certified", false);
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
