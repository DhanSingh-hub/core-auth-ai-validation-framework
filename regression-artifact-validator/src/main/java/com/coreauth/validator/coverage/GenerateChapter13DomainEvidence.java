package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Chapter13DataElementValidator;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.LinkedHashSet;
import java.util.Set;

public final class GenerateChapter13DomainEvidence {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Set<String> chains = new LinkedHashSet<>();
    private ObjectNode inventory;
    private ObjectNode artifacts;
    private ArrayNode executions;
    private Chapter13DataElementValidator validator;

    public static void main(String[] args) throws IOException {
        new GenerateChapter13DomainEvidence().generate(Atl105Paths.testJson("chapter-13-domain-package.json"),
                Atl105Paths.testJson("chapter-13-domain-evidence.json"));
    }

    public ObjectNode generate(Path packageFile, Path reportFile) throws IOException {
        chains.clear();
        Path source = Atl105Paths.docs().resolve("specs").resolve("extracted_text.txt");
        Path catalog = Atl105Paths.docs().resolve("specs").resolve("kb").resolve("elements").resolve("value-domains.json");
        Path probes = Atl105Paths.testJson("chapter-13-semantic-probes.json");
        Path existing = Atl105Paths.testOutput().resolve("test-solution-independent-review")
                .resolve("complete-all-test-solution-br-ts-tc-td-package.json");
        var extractor = new Atl105ElementInventory();
        inventory = extractor.extract(Files.readString(source));
        extractor.linkRequirements(inventory, mapper.readTree(existing.toFile()), existing.getFileName().toString());
        validator = new Chapter13DataElementValidator(Atl105Paths.root());
        artifacts = mapper.createObjectNode();
        artifacts.putObject("manifest").put("packageId", "ATL105-TEST-CH13-DOMAINS")
                .put("specification", "ATL105").put("specificationVersion", "2026-3");
        for (String collection : new String[]{"businessRequirements", "testScenarios", "testCases", "testData"}) artifacts.putArray(collection);
        ObjectNode report = mapper.createObjectNode().put("specification", "ATL105").put("specificationVersion", "2026-3")
                .put("producer", "TEST_SOLUTION").put("completeChapterCoverage", false).put("executionReady", false)
                .put("approvedRequirements", 0).put("policy", "Actual source-local target predicates, not chapter closure. "
                        + "Existing Test BRs are element-identity candidates only; absent matches are explicit, never auto-confirmed.");
        report.putObject("inputSha256").put("source", hash(source)).put("domains", hash(catalog))
                .put("semanticProbes", hash(probes)).put("existingTestPackage", hash(existing));
        executions = report.putArray("executions");
        for (JsonNode domain : mapper.readTree(catalog.toFile()).path("domains")) {
            String element = domain.path("element").asText();
            String rule = "CH13-E" + element + "-DOMAIN";
            execute(element, rule, "positive", observation(element, mapper.getNodeFactory().textNode(domain.path("positive").asText())), Status.CHECKS_PASSED);
            execute(element, rule, "negative", observation(element, mapper.getNodeFactory().textNode(domain.path("negative").asText())), Status.INVALID);
            if ("range".equals(domain.path("kind").asText())) {
                BigInteger min = new BigInteger(domain.path("min").asText()), max = new BigInteger(domain.path("max").asText());
                execute(element, rule, "min", observation(element, mapper.getNodeFactory().textNode(min.toString())), Status.CHECKS_PASSED);
                execute(element, rule, "max", observation(element, mapper.getNodeFactory().textNode(max.toString())), Status.CHECKS_PASSED);
                execute(element, rule, "below-min", observation(element, mapper.getNodeFactory().textNode(min.subtract(BigInteger.ONE).toString())), Status.INVALID);
                execute(element, rule, "above-max", observation(element, mapper.getNodeFactory().textNode(max.add(BigInteger.ONE).toString())), Status.INVALID);
            } else if ("codes".equals(domain.path("kind").asText())) {
                int index = 0;
                for (JsonNode code : domain.path("values")) execute(element, rule, "code-" + (++index), observation(element, code), Status.CHECKS_PASSED);
            }
            execute(element, "CH13-E" + element + "-TEXT", "nontext", observation(element, mapper.getNodeFactory().numberNode(1)), Status.INVALID);
        }
        int scalarExecutions = executions.size();
        var semantic = mapper.readTree(probes.toFile());
        if (!"ATL105".equals(semantic.path("specification").asText()) || !"2026-3".equals(semantic.path("specificationVersion").asText())
                || !semantic.path("probes").isArray()) throw new IOException("Unsupported semantic probe document");
        for (JsonNode probe : semantic.path("probes")) {
            var observation = observation(probe.path("element").asText(), mapper.getNodeFactory().textNode(""));
            observation.set("elements", probe.path("elements").deepCopy());
            for (String field : new String[]{"qualifiers", "representation", "segment", "completeness", "records"}) {
                if (probe.has(field)) observation.set(field, probe.path(field).deepCopy());
            }
            Status expected;
            try { expected = Status.valueOf(probe.path("expected").asText()); }
            catch (IllegalArgumentException exception) { throw new IOException("Invalid probe expected status: " + probe.path("id"), exception); }
            execute(probe.path("element").asText(), "CH13-E" + probe.path("element").asText() + "-" + probe.path("rule").asText(),
                    probe.path("id").asText(), observation, expected);
        }
        ArrayNode rows = report.putArray("sourceElementReconciliation");
        var elements = inventory.path("elements").fields();
        int candidateGaps = 0;
        while (elements.hasNext()) {
            var entry = elements.next();
            var row = rows.addObject().put("element", entry.getKey()).put("sourceDefinitions", entry.getValue().path("definitions").size())
                    .put("completeSemanticCoverage", false)
                    .put("remainingProcessingCoverage", "NOT_FULLY_RECONCILED_IMPLEMENTATION_GAP_NOT_SME");
            row.set("existingTestBusinessRequirementCandidates", entry.getValue().path("existingBusinessRequirements").deepCopy());
            var predicates = row.putArray("executedPredicates");
            for (String chain : chains) if (chain.startsWith("CH13-E" + entry.getKey() + "-")) predicates.add(chain);
            if (predicates.isEmpty()) row.put("status", "NO_ADDITIONAL_EXECUTED_PREDICATE_IN_THIS_PACKAGE");
            else row.put("status", "ADDITIONAL_PREDICATES_EXECUTED_FULL_PROCESSING_NOT_RECONCILED");
            if (!predicates.isEmpty() && entry.getValue().path("existingBusinessRequirements").isEmpty()) candidateGaps++;
        }
        var parsed = mapper.treeToValue(artifacts, CanonicalArtifactPackage.class);
        var traceability = new CanonicalTraceabilityValidator().validate(parsed);
        if (!traceability.isValid()) throw new IOException("Chapter 13 canonical chain integrity failed: " + traceability.errors());
        report.putObject("summary").put("sourceElementIdentities", rows.size()).put("sourceDefinitions", inventory.path("definitionCount").asInt())
                .put("scalarDomainExecutions", scalarExecutions).put("semanticExecutions", executions.size() - scalarExecutions)
                .put("executions", executions.size()).put("businessRequirements", artifacts.path("businessRequirements").size())
                .put("scenarios", artifacts.path("testScenarios").size()).put("testCases", artifacts.path("testCases").size())
                .put("testDataRecords", artifacts.path("testData").size()).put("candidateGapElements", candidateGaps)
                .put("traceabilityValid", true).put("fullSemanticElementClosures", 0);
        Files.createDirectories(packageFile.toAbsolutePath().getParent());
        Files.createDirectories(reportFile.toAbsolutePath().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(packageFile.toFile(), artifacts);
        mapper.writerWithDefaultPrettyPrinter().writeValue(reportFile.toFile(), report);
        return report;
    }

    private void execute(String element, String rule, String label, ObjectNode observation, Status expected) throws IOException {
        var result = validator.validate(observation);
        var target = result.findings().stream().filter(f -> rule.equals(f.ruleId()) && element.equals(f.element())).toList();
        if (target.size() != 1 || target.getFirst().status() != expected) throw new IOException("Unexpected target result " + rule + "/" + label + ": " + target);
        if (expected == Status.INVALID && result.status() != Status.INVALID) throw new IOException("Negative probe did not invalidate observation");
        JsonNode row = inventory.path("elements").path(element);
        if (row.isMissingNode() || row.path("definitions").isEmpty()) throw new IOException("No source identity " + element);
        ObjectNode anchor = mapper.createObjectNode().put("specification", "ATL105").put("version", "2026-3")
                .put("section", "13.2").put("segment", "").put("element", element).put("rule", rule);
        String stem = "TEST-" + rule;
        if (chains.add(rule)) {
            var br = ((ArrayNode) artifacts.path("businessRequirements")).addObject().put("id", "BR-" + stem)
                    .put("title", "Source-local predicate " + rule).put("executionStatus", "REVIEW_REQUIRED");
            br.putArray("sourceAnchors").add(anchor.deepCopy());
            var scenario = ((ArrayNode) artifacts.path("testScenarios")).addObject().put("id", "TS-" + stem).put("status", "REVIEW_REQUIRED");
            scenario.putArray("requirementIds").add("BR-" + stem);
            scenario.putArray("sourceAnchors").add(anchor.deepCopy());
        }
        String suffix = "-" + label.toUpperCase(java.util.Locale.ROOT);
        String tcId = "TC-" + stem + suffix, tdId = "TD-" + stem + suffix;
        var tc = ((ArrayNode) artifacts.path("testCases")).addObject().put("id", tcId).put("status", "REVIEW_REQUIRED")
                .put("expectedOutcome", expected == Status.INVALID ? "FAIL" : "REVIEW_REQUIRED");
        tc.putArray("scenarioIds").add("TS-" + stem);
        tc.putArray("sourceAnchors").add(anchor.deepCopy());
        var td = ((ArrayNode) artifacts.path("testData")).addObject().put("id", tdId).put("readiness", "REVIEW_REQUIRED")
                .put("expectedValidation", expected == Status.INVALID ? "FAIL" : "REVIEW_REQUIRED");
        td.putArray("testCaseIds").add(tcId);
        td.putArray("sourceAnchors").add(anchor.deepCopy());
        td.set("payload", observation.deepCopy());
        var execution = executions.addObject().put("testCaseId", tcId).put("testDataId", tdId).put("element", element)
                .put("rule", rule).put("expectedTargetStatus", expected.name()).put("actualTargetStatus", target.getFirst().status().name())
                .put("actualObservationStatus", result.status().name()).put("sourceLine", row.path("definitions").get(0).path("sourceLine").asInt())
                .put("existingCandidateStatus", row.path("existingBusinessRequirements").isEmpty() ? "MISSING" : "REVIEW_REQUIRED");
        execution.putArray("sourceAnchors").add(anchor.deepCopy());
        execution.set("existingTestArtifactCandidates", row.path("existingBusinessRequirements").deepCopy());
        execution.set("findings", mapper.valueToTree(result.findings()));
    }

    private ObjectNode observation(String element, JsonNode value) {
        var observation = mapper.createObjectNode().put("specificationVersion", "2026-3").put("messageFamily", "SOURCE_DOMAIN_OBSERVATION")
                .put("segment", "100").put("completeness", "PARTIAL").put("representation", "LOGICAL");
        observation.putObject("elements").set(element, value.deepCopy());
        return observation;
    }

    private static String hash(Path path) throws IOException {
        try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(path))); }
        catch (NoSuchAlgorithmException exception) { throw new IllegalStateException("Required SHA-256 algorithm unavailable", exception); }
    }
}
