package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.AppendixILogicalDataValidator;
import com.coreauth.validator.canonical.AppendixISegmentWireValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public final class GenerateAppendixIFirstBatchCandidates {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final List<Sample> SAMPLES = List.of(
            new Sample("001", "1".repeat(35), "1".repeat(36), ""),
            new Sample("002", "PWC", "XXX", ""),
            new Sample("003", "12345    ", "12345   ", ""),
            new Sample("004", "123", "1234", "VISA"),
            new Sample("005", "012", "019", ""),
            new Sample("006", "840", "84", ""),
            new Sample("007", "1234", "123", ""),
            new Sample("008", "1".repeat(19), "1".repeat(20), ""),
            new Sample("009", "5", "2", ""));

    private record Sample(String tableId, String positive, String negative, String network) {
    }

    private GenerateAppendixIFirstBatchCandidates() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            throw new IllegalArgumentException("Usage: [ATL105 root]");
        }
        Path root = args.length == 1 ? Path.of(args[0]) : Path.of("specifications", "ATL105");
        ObjectNode result = generate(root);
        Path output = root.resolve(Path.of("test-output", "test-json", "appendix-i-logical-candidates.json"));
        Files.createDirectories(output.getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), result);
        System.out.println("Generated 9 bounded draft BRs, 9 TS, 18 TC and 18 logical TD fragments; "
                + "no full-message execution or AI coverage established.");
    }

    public static ObjectNode generate(Path root) throws IOException {
        JsonNode batch = MAPPER.readTree(root.resolve(Path.of("test-output", "test-json", "knowledge",
                "appendix-i-001-009.json")).toFile());
        ObjectNode out = MAPPER.createObjectNode();
        out.put("packageId", "ATL105-APPENDIX-I-LOGICAL-CANDIDATES-001");
        out.put("specification", "ATL105");
        out.put("specificationVersion", "2026-3");
        out.put("status", "REVIEW_REQUIRED");
        out.put("artifactShape", "BOUNDED_LOGICAL_FRAGMENTS_NOT_CANONICAL_EXECUTION_PACKAGE");
        out.put("scope", "One selected representation predicate per Table 001-009, not every rule, "
                + "context, conditional presence, cryptographic validity, or wire serialization.");
        out.put("producer", GenerateAppendixIFirstBatchCandidates.class.getName());
        var brs = out.putArray("businessRequirements");
        var scenarios = out.putArray("testScenarios");
        var cases = out.putArray("testCases");
        var data = out.putArray("testData");
        AppendixILogicalDataValidator validator = new AppendixILogicalDataValidator();
        for (Sample sample : SAMPLES) {
            JsonNode rule = firstRule(batch, sample.tableId());
            String brId = "BR-APPI-T" + sample.tableId() + "-LOGICAL";
            String tsId = "TS-APPI-T" + sample.tableId() + "-LOGICAL";
            var br = brs.addObject();
            br.put("id", brId).put("status", "REVIEW_REQUIRED").put("ruleId", rule.path("id").asText());
            br.put("statement", rule.path("statement").asText());
            br.set("sourceAnchor", rule.path("sourceAnchor").deepCopy());
            br.put("assertionBoundary", "Only the implemented logical representation predicate; "
                    + "other clauses and context in the source rule remain unverified.");
            var ts = scenarios.addObject();
            ts.put("id", tsId).put("status", "REVIEW_REQUIRED");
            ts.putArray("requirementIds").add(brId);
            ts.set("sourceAnchor", rule.path("sourceAnchor").deepCopy());
            for (boolean positive : new boolean[]{true, false}) {
                String suffix = positive ? "-POS" : "-NEG";
                String tcId = "TC-APPI-T" + sample.tableId() + suffix;
                String tdId = "TD-APPI-T" + sample.tableId() + suffix;
                String value = positive ? sample.positive() : sample.negative();
                var findings = validator.validate(sample.tableId(), value,
                        sample.network().isEmpty() ? null : sample.network()).findings();
                boolean targetFound = findings.stream().anyMatch(finding ->
                        finding.ruleId().equals(rule.path("id").asText())
                                && finding.status() == (positive ? AppendixILogicalDataValidator.Status.PASS
                                : AppendixILogicalDataValidator.Status.FAIL));
                if (!targetFound) {
                    throw new IllegalStateException("Intended representation predicate did not fire for " + tcId);
                }
                var tc = cases.addObject();
                tc.put("id", tcId).put("status", "REVIEW_REQUIRED");
                tc.putArray("scenarioIds").add(tsId);
                tc.putArray("testDataIds").add(tdId);
                tc.put("given", "A synthetic logical fragment for Table " + sample.tableId()
                        + (sample.network().isEmpty() ? "" : " in declared " + sample.network() + " context"));
                tc.put("when", positive ? "Validate the bounded source-derived representation"
                        : "Apply the single supplied type/value/width mutation and validate");
                tc.put("then", "The target rule must return " + (positive ? "PASS" : "FAIL")
                        + "; any unresolved framing remains REVIEW_REQUIRED.");
                tc.put("expectedResult", positive ? "PASS_FOR_TARGET_PREDICATE_ONLY" : "FAIL_FOR_TARGET_PREDICATE");
                tc.set("sourceAnchor", rule.path("sourceAnchor").deepCopy());
                var td = data.addObject();
                td.put("id", tdId).put("readiness", "REVIEW_REQUIRED");
                td.put("candidatePolarity", positive ? "POSITIVE" : "NEGATIVE");
                td.put("payloadScope", "SYNTHETIC_LOGICAL_FRAGMENT_NOT_COMPLETE_ATL105_REQUEST");
                td.putArray("testCaseIds").add(tcId);
                td.set("sourceAnchor", rule.path("sourceAnchor").deepCopy());
                var request = td.putObject("request").putObject("variableInformation");
                request.put("tableId", sample.tableId()).put("data", value);
                if (!sample.network().isEmpty()) {
                    request.put("network", sample.network());
                }
                var outcomes = td.putArray("logicalValidation");
                for (var finding : findings) {
                    outcomes.addObject().put("ruleId", finding.ruleId())
                            .put("status", finding.status().name()).put("reason", finding.reason());
                }
                if (!sample.tableId().equals("009")) {
                    String segmentLength = String.format(Locale.ROOT, "%03d", 15 + value.length());
                    String dataLength = String.format(Locale.ROOT, "%03d", value.length());
                    String wire = "111\u001c" + segmentLength + "\u001c" + sample.tableId()
                            + dataLength + value + "\u001c";
                    var serialization = td.putObject("segmentSerializationCandidate");
                    serialization.put("scope", "SEGMENT_111_FRAGMENT_NOT_COMPLETE_MESSAGE");
                    serialization.put("readiness", "REVIEW_REQUIRED");
                    serialization.put("segmentType", "111").put("segmentLength", segmentLength);
                    serialization.put("variableInformationIndicator", sample.tableId());
                    serialization.put("variableInformationLength", dataLength);
                    serialization.put("variableInformation", value).put("wireFormat", wire);
                    serialization.putArray("sourceEvidence").addObject().put("section", "12.10")
                            .put("startLine", 12569).put("endLine", 12625);
                    var wireOutcomes = serialization.putArray("validation");
                    for (var finding : new AppendixISegmentWireValidator().validate(wire,
                            sample.network().isEmpty() ? null : sample.network()).findings()) {
                        wireOutcomes.addObject().put("ruleId", finding.ruleId())
                                .put("status", finding.status().name()).put("reason", finding.reason());
                    }
                } else {
                    td.put("segmentSerializationStatus", "REVIEW_REQUIRED_SEG111_SME_002");
                }
            }
        }
        var counts = out.putObject("counts");
        counts.put("selectedPredicates", 9).put("candidateScenarios", 9)
                .put("candidateCases", 18).put("logicalFragments", 18).put("segmentWireFragments", 16);
        counts.put("approvedRules", 0).put("completeExecutableRequests", 0)
                .put("executedBusinessCases", 0).put("certifiedRules", 0);
        counts.putNull("aiCoveragePercent");
        out.put("aiIntakeStatus", "NOT_ASSESSED");
        return out;
    }

    private static JsonNode firstRule(JsonNode batch, String id) {
        for (JsonNode table : batch.path("tables")) {
            if (table.path("tableId").asText().equals(id)) {
                JsonNode rule = table.path("rules").get(0);
                if (rule == null || !"REVIEW_ONLY".equals(rule.path("validationMode").asText())) {
                    throw new IllegalArgumentException("Missing review-only first rule for " + id);
                }
                return rule;
            }
        }
        throw new IllegalArgumentException("Missing source knowledge for " + id);
    }
}
