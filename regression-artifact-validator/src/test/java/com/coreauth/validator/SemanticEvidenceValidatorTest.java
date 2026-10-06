package com.coreauth.validator;

import com.coreauth.validator.canonical.SemanticEvidenceValidator;
import com.coreauth.validator.canonical.SemanticEvidenceBatch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SemanticEvidenceValidatorTest {
    @TempDir Path root;
    private final ObjectMapper mapper = new ObjectMapper();

    private ObjectNode source() throws Exception {
        Path file = root.resolve("source.txt");
        Files.writeString(file, "Source-defined rule and independently declared context.\n");
        return mapper.createObjectNode().put("file", "source.txt").put("sha256", hash(Files.readAllBytes(file)))
                .put("quote", "Source-defined rule").put("startLine", 1).put("endLine", 1);
    }

    private String hash(byte[] bytes) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
    }

    @Test
    void missingEvidenceNeverPassesOrCertifies() {
        var result = new SemanticEvidenceValidator(root).assess(mapper.createObjectNode(), mapper.createObjectNode());
        for (String gate : new String[]{"brEquivalence", "negativeIsolation", "compatibility", "wireValidation", "hostOutcome"}) {
            assertThat(result.path(gate).path("status").asText()).isEqualTo("NOT_ASSESSED");
        }
        assertThat(result.path("allTechnicalGatesPassed").asBoolean()).isFalse();
        assertThat(result.path("executionCertified").asBoolean()).isFalse();
    }

    @Test
    void completeClaimsCompareContextValuesAndExceptionsWithoutUsingIds() throws Exception {
        ObjectNode claims = (ObjectNode) mapper.readTree("""
            {"claimsComplete":true,"context":{"specification":"ATL105","version":"2026-3","messageFamily":"financial","transactionType":"sale","cardType":"credit","lifecycleRole":"original"},
             "atoms":[{"subject":"sequence","operator":"fixed","modality":"must","units":"digits","value":6,"conditions":{},"exceptions":[]}]}
            """);
        var observation = mapper.createObjectNode(); observation.set("brClaims", claims);
        var evidence = mapper.createObjectNode();
        var gate = evidence.putObject("brEquivalence"); gate.set("independentClaims", claims.deepCopy()); gate.set("source", source());
        var validator = new SemanticEvidenceValidator(root);
        assertThat(validator.assess(observation, evidence).path("brEquivalence").path("status").asText()).isEqualTo("PASS");
        ((ObjectNode) gate.path("independentClaims").path("context")).put("lifecycleRole", "reversal");
        assertThat(validator.assess(observation, evidence).path("brEquivalence").path("status").asText()).isEqualTo("FAIL");
        gate.set("independentClaims", claims.deepCopy());
        ((ObjectNode) gate.path("independentClaims").path("atoms").get(0)).put("value", 5);
        assertThat(validator.assess(observation, evidence).path("brEquivalence").path("status").asText()).isEqualTo("FAIL");
        claims.put("claimsComplete", "true");
        assertThat(validator.assess(observation, evidence).path("brEquivalence").path("status").asText()).isEqualTo("NOT_ASSESSED");
        claims.put("claimsComplete", false);
        assertThat(validator.assess(observation, evidence).path("brEquivalence").path("status").asText()).isEqualTo("NOT_ASSESSED");
    }

    @Test
    void isolatedMutationRequiresValidControlAndNoAdditionalChanges() throws Exception {
        var observation = mapper.createObjectNode();
        observation.set("controlPayload", mapper.readTree("{\"sequence\":\"123456\",\"segmentType\":\"100\"}"));
        observation.set("mutantPayload", mapper.readTree("{\"sequence\":\"12345\",\"segmentType\":\"100\"}"));
        var evidence = mapper.createObjectNode();
        var gate = evidence.putObject("negativeIsolation").put("targetPointer", "/sequence").put("targetRule", "SEQUENCE_SIX_DIGITS");
        gate.set("source", source());
        gate.set("controlScope", mapper.readTree("[{\"pointer\":\"/sequence\",\"rule\":\"SEQUENCE_SIX_DIGITS\"},{\"pointer\":\"/segmentType\",\"rule\":\"SEG100_TYPE\"}]"));
        var validator = new SemanticEvidenceValidator(root);
        assertThat(validator.assess(observation, evidence).path("negativeIsolation").path("status").asText()).isEqualTo("PASS");
        assertThat(validator.assess(observation, evidence).path("negativeEffectiveness").path("status").asText()).isEqualTo("NOT_ASSESSED");
        ((ObjectNode) observation.path("mutantPayload")).put("segmentType", "101");
        assertThat(validator.assess(observation, evidence).path("negativeIsolation").path("status").asText()).isEqualTo("FAIL");
        ((ObjectNode) observation.path("controlPayload")).put("segmentType", "101");
        assertThat(validator.assess(observation, evidence).path("negativeIsolation").path("status").asText()).isEqualTo("FAIL");
    }

    @Test
    void compatibilityDoesNotSkipMissingControlsAndDetectsMissingCompanion() throws Exception {
        var observation = mapper.createObjectNode();
        observation.set("canonicalPayload", mapper.readTree("{\"request\":{\"dataSection1\":{\"numberOfSegments\":\"01\"},\"dataSection2\":{\"standardSegment\":{\"segmentType\":\"100\"}},\"dataSection3\":[]}}"));
        var evidence = mapper.createObjectNode();
        var gate = evidence.putObject("compatibility").put("messageCategory", "STANDARD_FINANCIAL_REQUEST"); gate.set("source", source());
        gate.putArray("requiredSegments").add("100");
        gate.putArray("allowedSegments").add("100").add("130");
        var validator = new SemanticEvidenceValidator(root);
        assertThat(validator.assess(observation, evidence).path("compatibility").path("status").asText()).isEqualTo("PASS");
        gate.putArray("requiredSegments").add("100").add("130");
        assertThat(validator.assess(observation, evidence).path("compatibility").path("status").asText()).isEqualTo("FAIL");
        gate.remove("source");
        assertThat(validator.assess(observation, evidence).path("compatibility").path("status").asText()).isEqualTo("NOT_ASSESSED");
    }

    @Test
    void actualWireLengthAndValuesAreCheckedInsteadOfCalculationPlaceholders() throws Exception {
        String wire = "100\u001c010\u001c0\u001c";
        var observation = mapper.createObjectNode().put("wireHex", HexFormat.of().formatHex(wire.getBytes(StandardCharsets.US_ASCII)));
        var evidence = mapper.createObjectNode();
        var gate = evidence.putObject("wireValidation").put("scope", "SEGMENT_100").put("encoding", "US-ASCII").put("fieldSeparatorHex", "1c"); gate.set("source", source());
        gate.putArray("orderedFields").add("100").add("010").add("0");
        var validator = new SemanticEvidenceValidator(root);
        assertThat(validator.assess(observation, evidence).path("wireValidation").path("status").asText()).isEqualTo("PASS");
        observation.put("wireHex", HexFormat.of().formatHex(wire.replace("010", "009").getBytes(StandardCharsets.US_ASCII)));
        assertThat(validator.assess(observation, evidence).path("wireValidation").path("status").asText()).isEqualTo("FAIL");
        observation.put("wireHex", "CALCULATE_FROM_SEGMENT_CONTENT");
        assertThat(validator.assess(observation, evidence).path("wireValidation").path("status").asText()).isEqualTo("FAIL");
    }

    @Test
    void observedOutcomeRequiresAuthoritativeRequestAndResponseBindings() throws Exception {
        var observation = mapper.createObjectNode(); observation.set("request", mapper.readTree("{\"id\":\"request-1\"}"));
        observation.set("response", mapper.readTree("{\"responseCode\":\"1\"}"));
        var evidence = mapper.createObjectNode();
        var gate = evidence.putObject("hostOutcome").put("responseFamily", "communications-test");
        gate.put("oracleKind", "SOURCE_DEFINED_DETERMINISTIC").put("environment", "isolated-regression-fixture")
            .put("stateFixtureSha256", hash("fixture".getBytes(StandardCharsets.UTF_8)));
        gate.set("source", source()); gate.set("expectedResponse", observation.path("response"));
        gate.put("requestSha256", hash(observation.path("request").toString().getBytes(StandardCharsets.UTF_8)));
        gate.put("responseSha256", hash(observation.path("response").toString().getBytes(StandardCharsets.UTF_8)));
        var validator = new SemanticEvidenceValidator(root);
        assertThat(validator.assess(observation, evidence).path("hostOutcome").path("status").asText()).isEqualTo("PASS");
        ((ObjectNode) observation.path("request")).put("id", "different-request");
        assertThat(validator.assess(observation, evidence).path("hostOutcome").path("status").asText()).isEqualTo("FAIL");
        observation.remove("response");
        assertThat(validator.assess(observation, evidence).path("hostOutcome").path("status").asText()).isEqualTo("NOT_ASSESSED");
    }

    @Test
    void staleSourceAndTraversalCannotProvideEvidence() throws Exception {
        var observation = mapper.createObjectNode(); observation.set("controlPayload", mapper.readTree("{\"value\":\"100\"}"));
        observation.set("mutantPayload", mapper.readTree("{\"value\":\"101\"}"));
        var evidence = mapper.createObjectNode();
        var gate = evidence.putObject("negativeIsolation").put("targetPointer", "/value").put("targetRule", "SEG100_TYPE");
        ObjectNode source = source(); gate.set("source", source);
        gate.set("controlScope", mapper.readTree("[{\"pointer\":\"/value\",\"rule\":\"SEG100_TYPE\"}]"));
        Files.writeString(root.resolve("source.txt"), "Changed source");
        assertThat(new SemanticEvidenceValidator(root).assess(observation, evidence).path("negativeIsolation").path("status").asText()).isEqualTo("NOT_ASSESSED");
        source.put("file", "../source.txt");
        assertThat(new SemanticEvidenceValidator(root).assess(observation, evidence).path("negativeIsolation").path("status").asText()).isEqualTo("NOT_ASSESSED");
        gate.set("source", source());
        ((ObjectNode) gate.path("source")).put("startLine", "1");
        assertThat(new SemanticEvidenceValidator(root).assess(observation, evidence).path("negativeIsolation").path("status").asText()).isEqualTo("NOT_ASSESSED");
    }

    @Test
    void fullMessageWireCoversTransportTpduCountAndEveryBodyByte() throws Exception {
        String body = "600000000041544c3130351c" + "301c".repeat(7) + "30311c3130301c3031301c301c";
        var observation = mapper.createObjectNode().put("wireHex", "0027" + body);
        var evidence = mapper.createObjectNode();
        var gate = evidence.putObject("wireValidation").put("scope", "FULL_MESSAGE").put("encoding", "US-ASCII").put("fieldSeparatorHex", "1c")
                .put("tpduHex", "6000000000").put("transportLengthConvention", "UINT16_BE_BODY_INCLUDING_TPDU");
        gate.set("source", source());
        var fields = gate.putArray("section1Fields").add("ATL105");
        for (int index = 0; index < 7; index++) fields.add("0");
        fields.add("01");
        gate.putArray("segments").addObject().putArray("fields").add("100").add("010").add("0");
        var validator = new SemanticEvidenceValidator(root);
        assertThat(validator.assess(observation, evidence).path("wireValidation").path("status").asText()).isEqualTo("PASS");
        assertThat(validator.assess(observation, evidence).path("fullMessageWireAssessed").asBoolean()).isTrue();
        observation.put("wireHex", "0026" + body);
        assertThat(validator.assess(observation, evidence).path("wireValidation").path("status").asText()).isEqualTo("FAIL");
        observation.put("wireHex", "0027" + body.substring(0, body.length() - 4) + "311c");
        assertThat(validator.assess(observation, evidence).path("wireValidation").path("status").asText()).isEqualTo("FAIL");
        gate.put("transportLengthConvention", "UNKNOWN");
        assertThat(validator.assess(observation, evidence).path("wireValidation").path("status").asText()).isEqualTo("NOT_ASSESSED");
    }

    @Test
    void malformedPointerAndUnknownPredicateRemainUnassessed() throws Exception {
        var observation = mapper.createObjectNode(); observation.set("controlPayload", mapper.readTree("{\"value\":\"100\"}"));
        observation.set("mutantPayload", mapper.readTree("{\"value\":\"101\"}"));
        var evidence = mapper.createObjectNode();
        var gate = evidence.putObject("negativeIsolation").put("targetPointer", "not-a-pointer").put("targetRule", "SEG100_TYPE"); gate.set("source", source());
        assertThat(new SemanticEvidenceValidator(root).assess(observation, evidence).path("negativeIsolation").path("status").asText()).isEqualTo("NOT_ASSESSED");
        gate.put("targetPointer", "/value").put("targetRule", "UNIMPLEMENTED_ENUM");
        assertThat(new SemanticEvidenceValidator(root).assess(observation, evidence).path("negativeIsolation").path("status").asText()).isEqualTo("NOT_ASSESSED");
    }

    @Test
    void batchRetainsAllCasesAndDoesNotPromoteMissingEvidence() throws Exception {
        Path register = root.resolve("register.json");
        Files.writeString(register, "[{\"caseId\":\"TC-1\",\"intent\":\"negative\"},{\"caseId\":\"TC-2\",\"intent\":\"positive\"}]");
        var report = new SemanticEvidenceBatch().evaluate(register, root, null);
        assertThat(report.path("caseCount").asInt()).isEqualTo(2);
        assertThat(report.path("gateCounts").path("brEquivalence").path("NOT_ASSESSED").asInt()).isEqualTo(2);
        assertThat(report.path("gateCounts").path("negativeEffectiveness").path("NOT_ASSESSED").asInt()).isEqualTo(1);
        assertThat(report.path("gateCounts").path("negativeEffectiveness").path("NOT_APPLICABLE").asInt()).isEqualTo(1);
        assertThat(report.path("registerSha256").asText()).isEqualTo(hash(Files.readAllBytes(register)));
        assertThat(report.path("executionCertified").asBoolean()).isFalse();
    }

    @Test
    void batchRejectsUnknownVersionDuplicateJsonKeysAndOutOfRunIds() throws Exception {
        Path register = root.resolve("register.json");
        Path sidecar = root.resolve("sidecar.json");
        Files.writeString(register, "[{\"caseId\":\"TC-1\"}]");
        Files.writeString(sidecar, "{\"schemaVersion\":2,\"cases\":[]}");
        assertThatThrownBy(() -> new SemanticEvidenceBatch().evaluate(register, root, sidecar)).isInstanceOf(IllegalArgumentException.class);
        Files.writeString(sidecar, "{\"schemaVersion\":1,\"cases\":[{\"caseId\":\"TC-2\",\"observation\":{},\"evidence\":{}}]}");
        assertThatThrownBy(() -> new SemanticEvidenceBatch().evaluate(register, root, sidecar)).isInstanceOf(IllegalArgumentException.class);
        Files.writeString(sidecar, "{\"schemaVersion\":1,\"schemaVersion\":1,\"cases\":[]}");
        assertThatThrownBy(() -> new SemanticEvidenceBatch().evaluate(register, root, sidecar)).isInstanceOf(com.fasterxml.jackson.core.JsonParseException.class);
    }

    @Test
    void fullNegativeGateRequiresCoreValidControlAndMatchingRejection() throws Exception {
        ObjectNode control = (ObjectNode) mapper.readTree("""
            {"dataSection1":{"messageFormatVersionIdentifier":"ATL105","numberOfSegments":"01"},
             "dataSection2":{"standardSegment":{"segmentType":"100","terminalIdentifier":"TERM1","promptCode":"000","sequenceNumber":"123456","partialApprovalIndicator":"0"}},"dataSection3":[]}
            """);
        ObjectNode mutant = control.deepCopy();
        ((ObjectNode) mutant.path("dataSection2").path("standardSegment")).put("sequenceNumber", "12345");
        ObjectNode observation = mapper.createObjectNode();
        observation.set("controlPayload", control); observation.set("mutantPayload", mutant);
        observation.putObject("controlCanonicalPayload").set("request", control);
        observation.putObject("canonicalPayload").set("request", mutant);
        observation.set("controlRequest", control); observation.set("request", mutant);
        observation.putObject("controlResponse").put("responseCode", "0");
        observation.putObject("response").put("responseCode", "1");
        ObjectNode evidence = mapper.createObjectNode();
        var isolation = evidence.putObject("negativeIsolation").put("targetPointer", "/dataSection2/standardSegment/sequenceNumber").put("targetRule", "SEQUENCE_SIX_DIGITS");
        isolation.set("source", source());
        isolation.set("controlScope", mapper.readTree("[{\"pointer\":\"/dataSection2/standardSegment/sequenceNumber\",\"rule\":\"SEQUENCE_SIX_DIGITS\"},{\"pointer\":\"/dataSection2/standardSegment/segmentType\",\"rule\":\"SEG100_TYPE\"}]"));
        var proof = isolation.putObject("fullControl").put("scopeComplete", true); proof.set("source", source());
        for (ObjectNode contracts : new ObjectNode[]{proof, evidence}) {
            var compatibility = contracts.putObject("compatibility").put("messageCategory", "STANDARD_FINANCIAL_REQUEST"); compatibility.set("source", source());
            compatibility.putArray("requiredSegments").add("100"); compatibility.putArray("allowedSegments").add("100");
            boolean positive = contracts == proof;
            var wire = contracts.putObject("wireValidation").put("scope", "FULL_MESSAGE").put("encoding", "US-ASCII").put("fieldSeparatorHex", "1c")
                    .put("tpduHex", "6000000000").put("transportLengthConvention", "UINT16_BE_BODY_INCLUDING_TPDU"); wire.set("source", source());
            var section1 = wire.putArray("section1Fields").add("ATL105");
            for (int index = 0; index < 7; index++) section1.add("0");
            section1.add("01");
            String sequence = positive ? "123456" : "12345";
            String segmentLength = positive ? "017" : "016";
            wire.putArray("segments").addObject().putArray("fields").add("100").add(segmentLength).add("0").add(sequence);
            String body = "ATL105\u001c" + "0\u001c".repeat(7) + "01\u001c100\u001c" + segmentLength + "\u001c0\u001c" + sequence + "\u001c";
            String hex = String.format("%04x", body.length() + 5) + "6000000000" + HexFormat.of().formatHex(body.getBytes(StandardCharsets.US_ASCII));
            observation.put(positive ? "controlWireHex" : "wireHex", hex);
            var oracle = contracts.putObject("hostOutcome").put("oracleKind", "INDEPENDENT_HOST_REFERENCE").put("environment", "isolated-regression-fixture")
                    .put("stateFixtureSha256", hash("fixture".getBytes(StandardCharsets.UTF_8))).put("responseFamily", "financial")
                    .put("expectedDisposition", positive ? "ACCEPT" : "REJECT"); oracle.set("source", source());
            JsonNode request = observation.path(positive ? "controlRequest" : "request");
            JsonNode response = observation.path(positive ? "controlResponse" : "response");
            oracle.set("expectedResponse", response.deepCopy());
            oracle.put("requestSha256", canonicalHash(request)); oracle.put("responseSha256", canonicalHash(response));
        }
        var validator = new SemanticEvidenceValidator(root);
        assertThat(validator.assess(observation, evidence).path("negativeEffectiveness").path("status").asText()).isEqualTo("PASS");
        ((ObjectNode) control.path("dataSection2").path("standardSegment")).remove("terminalIdentifier");
        ((ObjectNode) mutant.path("dataSection2").path("standardSegment")).remove("terminalIdentifier");
        assertThat(validator.assess(observation, evidence).path("negativeEffectiveness").path("status").asText()).isEqualTo("FAIL");
    }

    private String canonicalHash(JsonNode value) throws Exception {
        JsonNode sorted = sort(value);
        return hash(sorted.toString().getBytes(StandardCharsets.UTF_8));
    }

    private JsonNode sort(JsonNode value) {
        if (value.isObject()) {
            ObjectNode sorted = mapper.createObjectNode();
            var fields = new java.util.TreeMap<String, JsonNode>();
            value.properties().forEach(entry -> fields.put(entry.getKey(), entry.getValue()));
            fields.forEach((key, node) -> sorted.set(key, sort(node)));
            return sorted;
        }
        if (value.isArray()) {
            var sorted = mapper.createArrayNode(); value.forEach(node -> sorted.add(sort(node))); return sorted;
        }
        return value;
    }
}