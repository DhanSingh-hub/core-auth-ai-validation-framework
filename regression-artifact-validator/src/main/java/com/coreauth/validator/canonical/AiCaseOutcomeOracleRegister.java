package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.DigestOutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/** Loads independently adjudicated, complete expected responses for individual test cases. */
public final class AiCaseOutcomeOracleRegister {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Pattern SHA256 = Pattern.compile("[0-9a-fA-F]{64}");
    private static final Set<String> EVIDENCE_TYPES = Set.of(
            "ATL105_SPECIFICATION", "INDEPENDENT_EXECUTION", "AUTHORIZED_REVIEW_RECORD");

    private AiCaseOutcomeOracleRegister() { }

    public record Evidence(String type, String sourcePath, String sha256, String locator) { }

    public record Decision(String status, String oracleId, String reviewer, String reason,
                           List<Evidence> evidence) { }

    public record Entry(String testCaseId, String scenarioId, String status, String oracleId, String reviewer,
                        JsonNode sourceArtifacts, JsonNode expectedResponse, List<Evidence> evidence) { }

    public static final class Register {
        private final Map<String, Entry> entries;

        private Register(Map<String, Entry> entries) {
            this.entries = Map.copyOf(entries);
        }

        public Entry entryFor(String testCaseId) {
            return entries.get(testCaseId);
        }

        public void validateCaseIds(Set<String> catalogCaseIds) throws IOException {
            for (String testCaseId : entries.keySet()) {
                if (!catalogCaseIds.contains(testCaseId)) {
                    throw new IOException("Case outcome oracle references unknown test case: " + testCaseId);
                }
            }
        }
    }

    public static Register load(Path registerFile, Path pack, Path runRoot, String runId,
                                Path scenarioCatalog, Path testCaseCatalog) throws IOException {
        JsonNode root = MAPPER.readTree(registerFile.toFile());
        return validate(root, pack, runRoot, runId, scenarioCatalog, testCaseCatalog);
    }

    private static Register validate(JsonNode root, Path pack, Path runRoot, String runId,
                                     Path scenarioCatalog, Path testCaseCatalog) throws IOException {
        if (!"ATL105-CASE-LEVEL-OUTCOME-ORACLE-REGISTER".equals(root.path("artifact").asText())
                || root.path("schemaVersion").asInt() != 1
                || !"ATL105".equals(root.path("specification").asText())
                || !"2026-3".equals(root.path("specificationVersion").asText())) {
            throw new IOException("Unsupported case outcome oracle register");
        }
        if (!runId.equals(root.path("runId").asText())) {
            throw new IOException("Case outcome oracle register runId does not match the current run");
        }
        JsonNode hashes = root.path("inputSha256");
        if (!sha256(scenarioCatalog).equalsIgnoreCase(hashes.path("scenarioCatalog").asText())
                || !sha256(testCaseCatalog).equalsIgnoreCase(hashes.path("testCaseCatalog").asText())) {
            throw new IOException("Case outcome oracle register input hashes do not match the current catalogs");
        }
        if (!root.path("entries").isArray()) throw new IOException("Case outcome oracle register requires entries[]");

        Path canonicalRunRoot = runRoot.toRealPath();
        Path canonicalPack = pack.toRealPath();
        Path canonicalScenarioCatalog = scenarioCatalog.toRealPath();
        Path canonicalCaseCatalog = testCaseCatalog.toRealPath();
        Map<String, Entry> entries = new LinkedHashMap<>();
        Set<String> oracleIds = new HashSet<>();
        for (JsonNode row : root.path("entries")) {
            String testCaseId = required(row, "testCaseId");
            String status = required(row, "status");
            if (!Set.of("PENDING", "APPROVED", "REJECTED").contains(status)) {
                throw new IOException("Unsupported case outcome oracle status for " + testCaseId + ": " + status);
            }
            String oracleId = row.path("oracleId").asText("");
            List<Evidence> evidence = new ArrayList<>();
            JsonNode evidenceRows = row.path("evidence");
            if ("APPROVED".equals(status)) {
                validateApproval(row, testCaseId);
                required(row, "scenarioId");
                if (!row.path("sourceArtifacts").isArray() || row.path("sourceArtifacts").isEmpty()) {
                    throw new IOException("Approved oracle requires physical case input bindings: " + testCaseId);
                }
                for (JsonNode binding : row.path("sourceArtifacts")) {
                    required(binding, "metadataPath");
                    required(binding, "payloadPath");
                    if (!SHA256.matcher(required(binding, "metadataSha256")).matches()
                            || !SHA256.matcher(required(binding, "payloadSha256")).matches()) {
                        throw new IOException("Approved oracle input binding requires SHA-256 hashes");
                    }
                }
                if (oracleId.isBlank() || !oracleIds.add(oracleId)) {
                    throw new IOException("Approved case outcome oracle requires a unique oracleId");
                }
                if (!row.path("expectedResponse").isObject() || row.path("expectedResponse").isEmpty()) {
                    throw new IOException("Approved oracle requires a non-empty complete expectedResponse: " + testCaseId);
                }
                JsonNode code = normalizeExpectedResponse(row.path("expectedResponse")).path("response_code");
                if (!code.isTextual() || code.asText().isBlank() || code.asText().length() != 1) {
                    throw new IOException("Approved oracle requires a one-character response_code: " + testCaseId);
                }
                if (!evidenceRows.isArray() || evidenceRows.isEmpty()) {
                    throw new IOException("Approved oracle requires source evidence: " + testCaseId);
                }
                for (JsonNode evidenceRow : evidenceRows) {
                    evidence.add(validateEvidence(evidenceRow, canonicalPack, canonicalRunRoot,
                            canonicalScenarioCatalog, canonicalCaseCatalog, testCaseId));
                }
            }
                Entry entry = new Entry(testCaseId, row.path("scenarioId").asText(""), status, oracleId,
                    row.path("reviewer").asText(""), row.path("sourceArtifacts").deepCopy(),
                    row.path("expectedResponse").isObject() ? row.path("expectedResponse").deepCopy() : null,
                    List.copyOf(evidence));
            if (entries.putIfAbsent(testCaseId, entry) != null) {
                throw new IOException("Duplicate case outcome oracle entry: " + testCaseId);
            }
        }
        return new Register(entries);
    }

    public static Decision compare(Entry entry, JsonNode declaredExpectedResponse,
                                   String scenarioId, JsonNode sourceArtifacts,
                                   AiExpectedResponseComparator.Status oracleFamilyStatus) {
        if (entry == null) return new Decision("NOT_PROVIDED", "", "",
                "No case-level outcome oracle was supplied", List.of());
        if ("PENDING".equals(entry.status())) return new Decision("REVIEW_REQUIRED", entry.oracleId(), "",
                "Case-level outcome oracle is pending adjudication", List.of());
        if ("REJECTED".equals(entry.status())) return new Decision("REJECTED", entry.oracleId(), "",
                "Case-level outcome oracle candidate was rejected", List.of());
        if (!scenarioId.equals(entry.scenarioId()) || !sourceArtifacts.equals(entry.sourceArtifacts())) {
            return new Decision("STALE_BINDING", entry.oracleId(), entry.reviewer(),
                "Adjudication does not bind the current scenario and physical input hashes", entry.evidence());
        }
        if (oracleFamilyStatus == AiExpectedResponseComparator.Status.DISAGREE
            || oracleFamilyStatus == AiExpectedResponseComparator.Status.AI_DEFECT) {
            return new Decision("ORACLE_CONFLICT", entry.oracleId(), entry.reviewer(),
                    "Approved case oracle conflicts with the independent response-family oracle", entry.evidence());
        }
        boolean matches = declaredExpectedResponse != null && declaredExpectedResponse.isObject()
                && normalizeExpectedResponse(declaredExpectedResponse).equals(
                        normalizeExpectedResponse(entry.expectedResponse()));
        return new Decision(matches ? "MATCH" : "MISMATCH", entry.oracleId(), entry.reviewer(),
                matches ? "Declared response matches the approved complete case outcome"
                        : "Declared response differs from the approved complete case outcome",
                entry.evidence());
    }

    private static void validateApproval(JsonNode row, String testCaseId) throws IOException {
        if (!"COMPLETE_CASE_OUTCOME".equals(row.path("completeness").asText())) {
            throw new IOException("Approved oracle must attest COMPLETE_CASE_OUTCOME: " + testCaseId);
        }
        if (!"I_REVIEWED_SOURCE_INDEPENDENTLY_OF_AI_OUTPUT".equals(row.path("independenceAttestation").asText())) {
            throw new IOException("Approved oracle requires an independent-source attestation");
        }
        validateReviewer(row);
    }

    private static void validateReviewer(JsonNode row) throws IOException {
        String reviewer = required(row, "reviewer");
        if ("UNREVIEWED".equalsIgnoreCase(reviewer)) throw new IOException("A review requires a named reviewer");
        required(row, "rationale");
        try {
            Instant.parse(required(row, "reviewedAt"));
        } catch (RuntimeException exception) {
            throw new IOException("Approved oracle reviewedAt must be an ISO-8601 instant", exception);
        }
    }

    private static Evidence validateEvidence(JsonNode row, Path pack, Path runRoot,
                                             Path scenarioCatalog, Path testCaseCatalog,
                                             String testCaseId) throws IOException {
        String type = required(row, "type");
        if (!EVIDENCE_TYPES.contains(type)) throw new IOException("Unsupported oracle evidence type: " + type);
        String sourcePath = required(row, "sourcePath");
        String declaredHash = required(row, "sha256");
        String locator = required(row, "locator");
        if (!SHA256.matcher(declaredHash).matches()) throw new IOException("Invalid evidence SHA-256: " + sourcePath);
        Path source = Path.of(sourcePath);
        if (!source.isAbsolute()) source = pack.resolve(source);
        Path canonicalSource = source.toRealPath();
        Path producerIntake = pack.resolve("test-input/ai-solution");
        boolean archivedProducerEvidence = Files.exists(producerIntake)
            && canonicalSource.startsWith(producerIntake.toRealPath());
        if (!Files.isRegularFile(canonicalSource) || canonicalSource.startsWith(runRoot)
            || archivedProducerEvidence || canonicalSource.equals(scenarioCatalog)
            || canonicalSource.equals(testCaseCatalog)) {
            throw new IOException("Oracle evidence must be an independent file, not producer input: " + sourcePath);
        }
        if (!declaredHash.equalsIgnoreCase(sha256(canonicalSource))) {
            throw new IOException("Oracle evidence SHA-256 mismatch for " + testCaseId + ": " + sourcePath);
        }
        return new Evidence(type, sourcePath, declaredHash.toLowerCase(java.util.Locale.ROOT), locator);
    }

    private static ObjectNode normalizeExpectedResponse(JsonNode response) {
        ObjectNode normalized = response.deepCopy();
        if (!normalized.has("response_code") && normalized.has("responseCode")) {
            normalized.set("response_code", normalized.remove("responseCode"));
        }
        normalized.remove("responseCode");
        normalized.properties().forEach(field -> {
            JsonNode value = field.getValue();
            if (value.isObject() && value.has("value")) normalized.set(field.getKey(), value.get("value"));
        });
        return normalized;
    }

    private static String required(JsonNode node, String field) throws IOException {
        String value = node.path(field).asText("");
        if (value.isBlank()) throw new IOException("Case outcome oracle requires " + field);
        return value;
    }

    private static String sha256(Path path) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (var input = Files.newInputStream(path)) {
                input.transferTo(new DigestOutputStream(OutputStream.nullOutputStream(), digest));
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (java.security.NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is not available", exception);
        }
    }

    public static ObjectNode prepare(Path summaryFile) throws IOException {
        JsonNode summary = MAPPER.readTree(summaryFile.toFile());
        if (!"ATL105-UNIFIED-AI-VALIDATION-RUN".equals(summary.path("artifact").asText())
                || !"ATL105".equals(summary.path("specification").asText())
                || !"2026-3".equals(summary.path("specificationVersion").asText())) {
            throw new IOException("Preparation requires an ATL105 unified validation summary");
        }
        ObjectNode register = MAPPER.createObjectNode();
        register.put("artifact", "ATL105-CASE-LEVEL-OUTCOME-ORACLE-REGISTER").put("schemaVersion", 1)
                .put("specification", "ATL105").put("specificationVersion", "2026-3")
                .put("runId", required(summary, "runId"));
        register.put("policy", "Pending review only. Supply independently sourced complete expected outcomes; "
                + "never approve from AI declarations or family permission alone. No host execution certification.");
        ObjectNode hashes = register.putObject("inputSha256");
        hashes.put("scenarioCatalog", required(summary.path("inputSha256"), "scenarioCatalog"));
        hashes.put("testCaseCatalog", required(summary.path("inputSha256"), "testCaseCatalog"));
        register.put("sourceSummarySha256", sha256(summaryFile));
        ArrayNode entries = register.putArray("entries");
        Set<String> caseIds = new HashSet<>();
        if (summary.path("cases").isArray()) {
            for (JsonNode envelope : summary.path("cases")) prepareEntry(entries, envelope, caseIds);
        } else {
            Path relativeSidecar = Path.of(required(summary, "caseEnvelopeFile"));
            if (relativeSidecar.isAbsolute() || relativeSidecar.getNameCount() != 1) {
                throw new IOException("Case envelope sidecar must be a sibling filename");
            }
            Path sidecar = summaryFile.toAbsolutePath().resolveSibling(relativeSidecar);
            if (!"JSONL_GZIP".equals(summary.path("caseEnvelopeFormat").asText())
                    || !sha256(sidecar).equalsIgnoreCase(summary.path("caseEnvelopeSha256").asText())) {
                throw new IOException("Case envelope sidecar format or SHA-256 mismatch");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new GZIPInputStream(Files.newInputStream(sidecar)), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) prepareEntry(entries, MAPPER.readTree(line), caseIds);
            }
            if (caseIds.size() != summary.path("caseEnvelopeCount").asInt(-1)) {
                throw new IOException("Case envelope count does not match summary");
            }
        }
        if (caseIds.size() != summary.path("testCaseCount").asInt(-1)
                || entries.size() != summary.path("caseVerdictCounts").path("ORACLE_GAP").asInt(0)) {
            throw new IOException("Prepared case population does not match summary verdict counts");
        }
        register.putObject("summary").put("pendingCount", entries.size()).put("approvedCount", 0)
                .put("rejectedCount", 0);
        return register;
    }

    private static void prepareEntry(ArrayNode entries, JsonNode envelope, Set<String> caseIds) throws IOException {
        String testCaseId = required(envelope, "testCaseId");
        if (!caseIds.add(testCaseId)) throw new IOException("Duplicate case envelope: " + testCaseId);
        if (!"ORACLE_GAP".equals(envelope.path("verdict").asText())) return;
        ObjectNode entry = entries.addObject();
        entry.put("testCaseId", testCaseId).put("scenarioId", required(envelope, "scenarioId"))
                .put("status", "PENDING").put("oracleId", "OR-" + testCaseId)
                .put("oracleGapId", envelope.path("oracleGapId").asText())
                .put("gapReason", envelope.path("verdictReason").asText())
                .put("reviewer", "UNREVIEWED").put("completeness", "NOT_ADJUDICATED");
        entry.set("sourceArtifacts", envelope.path("sourceArtifacts").deepCopy());
        entry.putNull("expectedResponse");
        entry.putArray("evidence");
        entry.putArray("reviewHistory");
    }

    public static ObjectNode record(ObjectNode register, JsonNode decision, String previousRegisterSha256) throws IOException {
        if (!previousRegisterSha256.equalsIgnoreCase(required(decision, "previousRegisterSha256"))) {
            throw new IOException("Review decision must bind the exact prior register SHA-256");
        }
        String testCaseId = required(decision, "testCaseId");
        String status = required(decision, "status");
        if (!Set.of("APPROVED", "REJECTED", "PENDING").contains(status)) throw new IOException("Unsupported review status");
        validateReviewer(decision);
        ObjectNode updated = register.deepCopy();
        ObjectNode target = null;
        for (JsonNode entry : updated.path("entries")) {
            if (testCaseId.equals(entry.path("testCaseId").asText())) target = (ObjectNode) entry;
        }
        if (target == null) throw new IOException("No reviewable case in register: " + testCaseId);
        ObjectNode prior = target.deepCopy();
        prior.remove("reviewHistory");
        ArrayNode history = target.withArray("reviewHistory");
        history.add(prior);
        target.put("status", status);
        for (String field : List.of("reviewer", "reviewedAt", "rationale", "independenceAttestation",
                "completeness", "expectedResponse", "evidence")) {
            target.remove(field);
            if (decision.has(field)) target.set(field, decision.get(field).deepCopy());
        }
        updated.put("previousRegisterSha256", previousRegisterSha256);
        ObjectNode counts = updated.putObject("summary");
        for (String value : List.of("PENDING", "APPROVED", "REJECTED")) {
            int count = 0;
            for (JsonNode entry : updated.path("entries")) if (value.equals(entry.path("status").asText())) count++;
            counts.put(value.toLowerCase(java.util.Locale.ROOT) + "Count", count);
        }
        return updated;
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 4 && "--prepare".equals(args[0])) {
            Path pack = Path.of(args[1]);
            Path output = checkedOutput(pack, Path.of(args[3]));
            ObjectNode register = prepare(Path.of(args[2]));
            writeNew(output, register);
            System.out.println("prepared=" + output + " summary=" + register.path("summary"));
        } else if (args.length == 9 && "--record".equals(args[0])) {
            Path pack = Path.of(args[1]);
            Path run = Path.of(args[2]).toRealPath();
            Path scenarios = Path.of(args[3]);
            Path cases = Path.of(args[4]);
            Path previous = Path.of(args[5]);
            Path output = checkedOutput(pack, Path.of(args[8]));
            ObjectNode register = (ObjectNode) MAPPER.readTree(previous.toFile());
            validate(register, pack, run, run.getFileName().toString(), scenarios, cases);
            ObjectNode updated = record(register, MAPPER.readTree(Path.of(args[6]).toFile()), sha256(previous));
            if (!"--new-register".equals(args[7])) throw new IOException("Expected --new-register <new path>");
            validate(updated, pack, run, run.getFileName().toString(), scenarios, cases);
            writeNew(output, updated);
            System.out.println("recorded=" + output + " summary=" + updated.path("summary"));
        } else {
            throw new IllegalArgumentException("Usage: AiCaseOutcomeOracleRegister --prepare <pack> <unified-summary> <new-register> "
                    + "| --record <pack> <run> <scenario-catalog> <case-catalog> <prior-register> <reviewer-decision> "
                    + "--new-register <new-register>");
        }
    }

    private static Path checkedOutput(Path pack, Path requested) throws IOException {
        Path root = pack.toRealPath().resolve("test-output").toRealPath();
        Path output = requested.toAbsolutePath().normalize();
        if (!output.startsWith(root) || output.equals(root)) throw new IOException("Register output must be beneath ATL105 test-output");
        if (Files.exists(output)) throw new IOException("Refusing to overwrite existing register: " + output);
        Path ancestor = output.getParent();
        while (!Files.exists(ancestor)) ancestor = ancestor.getParent();
        if (!ancestor.toRealPath().startsWith(root)) throw new IOException("Register output resolves outside ATL105 test-output");
        return output;
    }

    private static void writeNew(Path output, ObjectNode register) throws IOException {
        Files.createDirectories(output.getParent());
        try (var stream = Files.newOutputStream(output, java.nio.file.StandardOpenOption.CREATE_NEW)) {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(stream, register);
        }
    }
}