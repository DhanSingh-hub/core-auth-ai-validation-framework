package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.AppendixILogicalDataValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public final class GenerateAppendixIRepresentativeIntake {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private GenerateAppendixIRepresentativeIntake() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            throw new IllegalArgumentException("Usage: [ATL105 root]");
        }
        Path root = args.length == 1 ? Path.of(args[0]) : Path.of("specifications", "ATL105");
        Path output = root.resolve(Path.of("test-output", "ai-solution-independent-review", "appendix-i",
                "representative-intake.json"));
        Files.createDirectories(output.getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), generate(root));
        System.out.println("Representative immutable BR/TS/TC/TD intake generated; no exhaustive AI coverage claimed.");
    }

    public static ObjectNode generate(Path root) throws IOException {
        Path delivery = root.resolve(Path.of("test-input", "ai-solution", "runs", "2026-09-29",
                "phase_1_single_leg"));
        String tcId = "TC-000016";
        String scenarioId = "SC-0018";
        String requirementId = "REQ-SRC-ATL105-PDF-001:0821";
        ObjectNode out = MAPPER.createObjectNode();
        out.put("reportId", "ATL105-APPENDIX-I-REPRESENTATIVE-INTAKE");
        out.put("specificationVersion", "2026-3");
        out.put("readiness", "REVIEW_REQUIRED");
        out.put("scope", "One existing representative chain only; not complete Appendix I or message-family coverage.");
        out.put("producerApprovalAcceptedAsOracle", false);
        out.putObject("independentSource").put("path", "docs\\specs\\extracted_text.txt")
                .put("sha256", sha256(root.resolve(Path.of("docs", "specs", "extracted_text.txt"))));
        var inputs = out.putArray("immutableInputs");
        Path brFile = delivery.resolve(Path.of("requirements", "requirement_candidates.json"));
        Path tsFile = delivery.resolve(Path.of("scenarios", "candidate_scenarios.json"));
        Path tcFile = delivery.resolve(Path.of("test_cases", "test_case_candidates.json"));
        Path tdFile = delivery.resolve(Path.of("test_data", tcId + ".json"));
        Path metaFile = delivery.resolve(Path.of("test_data", tcId + ".meta.json"));
        for (Path file : new Path[]{brFile, tsFile, tcFile, tdFile, metaFile}) {
            inputs.addObject().put("path", root.relativize(file).toString()).put("sha256", sha256(file));
        }
        JsonNode br = find(MAPPER.readTree(brFile.toFile()).path("requirements"), requirementId);
        JsonNode ts = find(MAPPER.readTree(tsFile.toFile()).path("scenarios"), scenarioId);
        JsonNode tc = find(MAPPER.readTree(tcFile.toFile()).path("test_cases"), tcId);
        JsonNode meta = MAPPER.readTree(metaFile.toFile());
        boolean links = requirementId.equals(ts.path("requirement_id").asText())
                && requirementId.equals(tc.path("requirement_id").asText())
                && scenarioId.equals(tc.path("scenario_id").asText())
                && requirementId.equals(meta.path("requirementId").asText())
                && scenarioId.equals(meta.path("scenarioId").asText())
                && tcId.equals(meta.path("testCaseId").asText());
        out.putObject("observedChain").put("requirementId", br.path("id").asText())
                .put("scenarioId", ts.path("id").asText()).put("testCaseId", tc.path("id").asText())
                .put("testDataFile", tcId + ".json").put("explicitIdsJoin", links);
        var findings = out.putArray("findings");
        finding(findings.addObject(), "APPENDIX-I-CHAIN-LINKS", links ? "PASS" : "FAIL",
                "Explicit BR/TS/TC/TD metadata IDs were joined; identity is not semantic equivalence.", 12609, 12622);
        JsonNode payload = MAPPER.readTree(tdFile.toFile());
        if (!payload.isObject() || payload.size() != 1) {
            throw new IllegalArgumentException("Representative payload requires one declared message wrapper");
        }
        JsonNode message = payload.elements().next();
        JsonNode segment = message.path("Variable Info Segment");
        if (!segment.isObject()) {
            throw new IllegalArgumentException("Representative Variable Info Segment is absent");
        }
        JsonNode tables = segment.path("VarInfoTable");
        if (!tables.isArray() || tables.isEmpty()) {
            throw new IllegalArgumentException("Representative table array is absent");
        }
        boolean present = true;
        for (JsonNode table : tables) {
            present &= table.path("TableID").isTextual() && table.path("TableID").asText().matches("[0-9]{3}");
        }
        finding(findings.addObject(), "APPI-E111-PRESENCE", present ? "PASS" : "FAIL",
                "Producer alias TableID supplies a three-digit selector for each observed entry; "
                        + "the producer presence claim is checked, not its confidence score.", 12609, 12613);
        boolean segmentLengthPresent = segment.path("SegmentLength").isTextual();
        finding(findings.addObject(), "APPI-S111-LENGTH-PRESENCE", segmentLengthPresent ? "REVIEW_REQUIRED" : "FAIL",
                segmentLengthPresent ? "Declared segment length needs independent wire reconstruction."
                        : "Physical TD omits the required Segment Length; deferred calculation is not completed evidence.",
                12599, 12604);
        if (tables.size() == 1 && tables.get(0).path("Value").isTextual()
                && segment.path("VariableInformationLength").isTextual()) {
            String declared = segment.path("VariableInformationLength").asText();
            int actual = tables.get(0).path("Value").asText().length();
            boolean equal = declared.matches("[0-9]{3}") && Integer.parseInt(declared) == actual;
            var lengthFinding = findings.addObject();
            finding(lengthFinding, "APPI-E112-DATA-LENGTH", equal ? "PASS" : "FAIL",
                    "Bounded single-record alias interpretation: declared data length must equal the physical value length.",
                    12614, 12622);
            lengthFinding.put("actualDataCharacters", actual);
            if (declared.matches("[0-9]{3}")) {
                lengthFinding.put("declaredDataLength", Integer.parseInt(declared));
            }
            var local = new AppendixILogicalDataValidator().validate(tables.get(0).path("TableID").asText(),
                    tables.get(0).path("Value").asText(), null);
            for (var predicate : local.findings()) {
                finding(findings.addObject(), predicate.ruleId(), predicate.status().name(),
                        predicate.reason(), 27546, 27561);
            }
        } else {
            finding(findings.addObject(), "APPI-E112-ALIAS", "NOT_ASSERTABLE",
                    "The bounded single-record alias interpretation does not cover this producer shape.", 12614, 12622);
        }
        finding(findings.addObject(), "APPENDIX-I-FULL-CHAIN-SEMANTICS", "NOT_ASSERTABLE",
                "Full message-family correctness, complete Appendix I rules, negative-case intent, "
                        + "response/host behavior and wire equality are not established by this representative intake.",
                12569, 12625);
        out.putObject("counts").put("observedLinkedChains", links ? 1 : 0)
                .put("approvedRules", 0).put("executedBusinessCases", 0).put("certifiedRules", 0)
                .putNull("aiCoveragePercent");
        return out;
    }

    private static JsonNode find(JsonNode array, String id) {
        JsonNode found = null;
        for (JsonNode row : array) {
            if (id.equals(row.path("id").asText())) {
                if (found != null) {
                    throw new IllegalArgumentException("Duplicate producer ID " + id);
                }
                found = row;
            }
        }
        if (found == null) {
            throw new IllegalArgumentException("Missing producer ID " + id);
        }
        return found;
    }

    private static void finding(ObjectNode node, String rule, String status, String reason, int start, int end) {
        node.put("ruleId", rule).put("status", status).put("reason", reason);
        node.putArray("sourceEvidence").addObject().put("source", "docs\\specs\\extracted_text.txt")
                .put("section", start >= 27546 ? "Appendix I-3" : "12.10")
                .put("startLine", start).put("endLine", end);
    }

    private static String sha256(Path path) throws IOException {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(path)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 unavailable", e);
        }
    }
}
