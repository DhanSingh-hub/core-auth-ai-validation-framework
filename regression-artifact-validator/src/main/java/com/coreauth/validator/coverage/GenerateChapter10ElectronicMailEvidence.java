package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.canonical.Segment109PayloadValidator;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class GenerateChapter10ElectronicMailEvidence {
    private final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        new GenerateChapter10ElectronicMailEvidence().generate(
                Atl105Paths.testJson("chapter-10-electronic-mail-package.json"),
                Atl105Paths.testJson("chapter-10-electronic-mail-evidence.json"));
    }

    public ObjectNode generate(Path packageFile, Path reportFile) throws IOException {
        Map<String, JsonNode> rules = new HashMap<>();
        for (JsonNode rule : mapper.readTree(Atl105Paths.ruleCatalog("109").toFile()).path("rules")) {
            rules.put(rule.path("ruleId").asText(), rule);
        }
        JsonNode existing = mapper.readTree(Atl105Paths.testOutput().resolve(
                "test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json").toFile());
        ObjectNode artifacts = mapper.createObjectNode();
        artifacts.putObject("manifest").put("packageId", "ATL105-TEST-CH10-ELECTRONIC-MAIL-OBSERVATIONS")
                .put("specification", "ATL105").put("specificationVersion", "2026-3");
        var brs = artifacts.putArray("businessRequirements");
        var scenarios = artifacts.putArray("testScenarios");
        var cases = artifacts.putArray("testCases");
        var data = artifacts.putArray("testData");
        ObjectNode report = mapper.createObjectNode().put("completeSpecCoverage", false)
                .put("executionReady", false).put("approvedRequirements", 0)
                .put("scope", "Chapter 10.11 prompt/ASCII byte limits; no full-wire/lifecycle or host-storage certification");
        var executions = report.putArray("executions");
        Set<String> added = new HashSet<>();
        var validator = new Segment109PayloadValidator();
        for (JsonNode fixture : mapper.readTree(Atl105Paths.testJson("chapter-10-electronic-mail-observations.json")
                .toFile()).path("observations")) {
            String ruleId = fixture.path("ruleId").asText();
            JsonNode rule = rules.get(ruleId);
            if (rule == null) throw new IOException("Fixture references missing catalog rule " + ruleId);
            JsonNode anchor = rule.path("sourceAnchor");
            String key = CanonicalSourceAnchorIdentity.key(mapper, anchor);
            String stem = "TEST-CH10-" + ruleId;
            if (added.add(ruleId)) {
                ObjectNode br = brs.addObject().put("id", "BR-" + stem).put("title", rule.path("title").asText())
                        .put("executionStatus", "REVIEW_REQUIRED");
                br.putArray("sourceAnchors").add(anchor.deepCopy());
                ObjectNode scenario = scenarios.addObject().put("id", "TS-" + stem).put("status", "REVIEW_REQUIRED");
                scenario.putArray("requirementIds").add("BR-" + stem);
                scenario.putArray("sourceAnchors").add(anchor.deepCopy());
            }
            JsonNode count = fixture.get("asciiByteCount");
            if (count == null || !count.isIntegralNumber() || !count.canConvertToInt()
                    || count.intValue() < 0 || count.intValue() > 751) {
                throw new IOException("Invalid synthetic fixture byte count");
            }
            ObjectNode payload = mapper.createObjectNode().put("operation", fixture.path("operation").asText());
            payload.set("promptCode", fixture.path("promptCode"));
            payload.put("SUBMISSION".equals(payload.path("operation").asText()) ? "submissionData" : "printData",
                    "A".repeat(count.intValue()));
            var result = validator.validateProcessingObservation(payload);
            String actual = result.errors().isEmpty() ? "REVIEW_REQUIRED" : "INVALID";
            if (!actual.equals(fixture.path("expectedStatus").asText())) {
                throw new IOException("Unexpected observed outcome for " + fixture.path("id").asText());
            }
            String suffix = fixture.path("id").asText();
            String tcId = "TC-" + stem + "-" + suffix;
            String tdId = "TD-" + stem + "-" + suffix;
            String expected = "INVALID".equals(actual) ? "FAIL" : "REVIEW_REQUIRED";
            ObjectNode tc = cases.addObject().put("id", tcId).put("expectedOutcome", expected).put("status", "REVIEW_REQUIRED");
            tc.putArray("scenarioIds").add("TS-" + stem);
            tc.putArray("sourceAnchors").add(anchor.deepCopy());
            ObjectNode td = data.addObject().put("id", tdId).put("readiness", "REVIEW_REQUIRED").put("expectedValidation", expected);
            td.putArray("testCaseIds").add(tcId);
            td.putArray("sourceAnchors").add(anchor.deepCopy());
            td.set("payload", payload);
            ObjectNode execution = executions.addObject().put("testCaseId", tcId).put("testDataId", tdId)
                    .put("expectedStatus", fixture.path("expectedStatus").asText()).put("actualStatus", actual);
            var errors = execution.putArray("errors");
            for (var error : result.errors()) {
                errors.addObject().put("context", error.context()).put("segment", error.segment())
                        .put("element", error.element()).put("reason", error.reason());
            }
            execution.set("warnings", mapper.valueToTree(result.warnings()));
            execution.set("sourceAnchor", anchor.deepCopy());
            var candidates = execution.putArray("existingTestArtifactAnchorCandidates");
            for (JsonNode br : existing.path("businessRequirements")) for (JsonNode candidate : br.path("sourceAnchors")) {
                if (key.equals(CanonicalSourceAnchorIdentity.key(mapper, candidate))) {
                    candidates.add(br.path("id").asText());
                }
            }
            if (candidates.isEmpty()) throw new IOException("No existing canonical Test BR anchor candidate for " + ruleId);
            execution.put("candidateMatchStatus", "REVIEW_REQUIRED");
        }
        var traceability = new CanonicalTraceabilityValidator().validate(mapper.treeToValue(artifacts, CanonicalArtifactPackage.class));
        if (!traceability.isValid()) throw new IOException("Chapter 10 canonical chain invalid: " + traceability.errors());
        report.putObject("summary").put("businessRequirements", brs.size()).put("scenarios", scenarios.size())
                .put("testCases", cases.size()).put("testDataRecords", data.size()).put("executedObservations", executions.size())
                .put("traceabilityValid", true);
        Files.createDirectories(packageFile.toAbsolutePath().getParent());
        Files.createDirectories(reportFile.toAbsolutePath().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(packageFile.toFile(), artifacts);
        mapper.writerWithDefaultPrettyPrinter().writeValue(reportFile.toFile(), report);
        return report;
    }

}
