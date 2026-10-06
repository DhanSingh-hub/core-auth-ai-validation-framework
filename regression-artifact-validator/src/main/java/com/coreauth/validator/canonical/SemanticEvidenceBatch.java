package com.coreauth.validator.canonical;

import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class SemanticEvidenceBatch {
    private static final List<String> GATES = List.of("brEquivalence", "negativeIsolation", "negativeEffectiveness", "compatibility", "wireValidation", "hostOutcome");
    private final JsonMapper mapper = JsonMapper.builder().enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION).build();

    public ObjectNode evaluate(Path register, Path trustedRoot, Path sidecar) throws Exception {
        JsonNode rows = mapper.readTree(register.toFile());
        if (!rows.isArray() || rows.isEmpty()) throw new IllegalArgumentException("Nonempty case-register array required");
        Map<String, JsonNode> supplied = new HashMap<>();
        if (sidecar != null) {
            JsonNode document = mapper.readTree(sidecar.toFile());
            if (!document.path("schemaVersion").isIntegralNumber() || document.path("schemaVersion").asInt() != 1 || !document.path("cases").isArray()) {
                throw new IllegalArgumentException("Evidence sidecar schemaVersion 1 and cases array required");
            }
            for (JsonNode item : document.path("cases")) {
                String id = item.path("caseId").asText();
                if (!item.path("caseId").isTextual() || id.isBlank() || supplied.putIfAbsent(id, item) != null) throw new IllegalArgumentException("Missing or duplicate sidecar case ID");
                if (!item.path("observation").isObject() || !item.path("evidence").isObject()) throw new IllegalArgumentException("Sidecar observation and evidence must be objects");
            }
        }
        ObjectNode report = mapper.createObjectNode().put("schemaVersion", 1).put("caseCount", rows.size())
                .put("registerSha256", sha256(register)).put("evidenceSidecarProvided", sidecar != null)
                .put("semanticApproval", "NOT_GRANTED_BY_TECHNICAL_EVIDENCE_CHECKS").put("executionCertified", false);
        if (sidecar != null) report.put("sidecarSha256", sha256(sidecar));
        report.put("register", register.toAbsolutePath().normalize().toString());
        report.put("trustedRoot", trustedRoot.toAbsolutePath().normalize().toString());
        ObjectNode counts = report.putObject("gateCounts");
        for (String gate : GATES) counts.putObject(gate).put("PASS", 0).put("FAIL", 0).put("NOT_ASSESSED", 0).put("NOT_APPLICABLE", 0);
        var results = report.putArray("cases");
        Set<String> seen = new HashSet<>();
        var validator = new SemanticEvidenceValidator(trustedRoot);
        for (JsonNode row : rows) {
            String id = row.path("caseId").asText();
            if (!row.path("caseId").isTextual() || id.isBlank() || !seen.add(id)) throw new IllegalArgumentException("Missing or duplicate register case ID");
            JsonNode item = supplied.getOrDefault(id, mapper.createObjectNode());
            ObjectNode assessed = validator.assess(item.path("observation"), item.path("evidence"));
            if (!"negative".equalsIgnoreCase(row.path("intent").asText())) {
                for (String gate : List.of("negativeIsolation", "negativeEffectiveness")) {
                    assessed.set(gate, mapper.createObjectNode().put("status", "NOT_APPLICABLE").put("reason", "Register declares no intended negative mutation"));
                }
            }
            assessed.put("allTechnicalGatesPassed", GATES.stream().allMatch(gate -> Set.of("PASS", "NOT_APPLICABLE").contains(assessed.path(gate).path("status").asText()))
                    && "FULL_MESSAGE".equals(item.path("evidence").path("wireValidation").path("scope").asText()));
            ObjectNode result = results.addObject().put("caseId", id).put("intent", row.path("intent").asText()).put("evidenceProvided", supplied.containsKey(id));
            result.set("assessment", assessed);
            for (String gate : GATES) {
                ObjectNode gateCounts = (ObjectNode) counts.path(gate);
                String status = assessed.path(gate).path("status").asText();
                gateCounts.put(status, gateCounts.path(status).asInt() + 1);
            }
        }
        if (!seen.containsAll(supplied.keySet())) throw new IllegalArgumentException("Sidecar contains case IDs outside this register");
        return report;
    }

    public static void main(String[] arguments) throws Exception {
        if (arguments.length < 3 || arguments.length > 4) throw new IllegalArgumentException("Usage: SemanticEvidenceBatch <register.json> <report.json> <trusted-source-root> [evidence-sidecar.json]");
        Path register = Path.of(arguments[0]).toAbsolutePath().normalize();
        Path output = Path.of(arguments[1]).toAbsolutePath().normalize();
        Path sidecar = arguments.length == 4 ? Path.of(arguments[3]).toAbsolutePath().normalize() : null;
        if (output.equals(register) || output.equals(sidecar) || Files.exists(output)) throw new IllegalArgumentException("Report must be a new file, not an existing file or evidence input");
        var runner = new SemanticEvidenceBatch();
        ObjectNode report = runner.evaluate(register, Path.of(arguments[2]), sidecar);
        Files.createDirectories(output.getParent());
        runner.mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), report);
        System.out.println("Assessed " + report.path("caseCount").asInt() + " cases; " + report.path("gateCounts"));
        System.out.println("Execution certified: false. Report: " + output);
    }

    private static String sha256(Path path) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(path)));
    }
}