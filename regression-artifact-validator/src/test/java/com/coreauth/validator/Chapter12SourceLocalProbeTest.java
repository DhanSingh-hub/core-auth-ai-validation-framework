package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter11MessageLayoutValidator;
import com.coreauth.validator.canonical.Chapter12ActualPayloadAdapter;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.coverage.Chapter12SourceLocalFixtures;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter12SourceLocalProbeTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Chapter12SegmentPayloadValidator validator = new Chapter12SegmentPayloadValidator();

    private List<JsonNode> fixtures() throws Exception {
        return Chapter12SourceLocalFixtures.expand(mapper.readTree(Atl105Paths.testJson("chapter-12-segment-observations.json").toFile()),
                mapper.readTree(Atl105Paths.testJson("chapter-12-source-local-probes.json").toFile()));
    }

    private ObjectNode input(String id) throws Exception {
        return fixtures().stream().filter(f -> id.equals(f.path("id").asText())).findFirst().orElseThrow().path("payload").deepCopy();
    }

    @TestFactory Stream<DynamicTest> executesEveryNewActualAndSourceLocalProbeWithoutMutation() throws Exception {
        return fixtures().stream().map(fixture -> DynamicTest.dynamicTest(fixture.path("id").asText(), () -> {
            JsonNode payload = fixture.path("payload"), before = payload.deepCopy();
            if (payload.has("envelopeObservation")) {
                var assessed = new Chapter11MessageLayoutValidator().validate(payload.path("envelopeObservation"));
                assertThat(assessed.status().name()).isEqualTo(fixture.path("expectedStatus").asText());
                assertThat(assessed.findings()).filteredOn(f ->
                                f.ruleId().equals(fixture.path("targetRuleId").asText()) && f.scope().equals(fixture.path("targetScope").asText()))
                        .singleElement().satisfies(f -> assertThat(f.status().name()).isEqualTo(fixture.path("expectedTargetStatus").asText()));
                assertThat(payload).isEqualTo(before);
                return;
            }
            if (payload.has("actualMessage")) {
                var assessed = new com.coreauth.validator.canonical.Chapter12LegacyActualPayloadAdapter().validate(
                        payload.path("actualMessage"), payload.path("segment").asText(), payload.path("messageFamily").asText(), payload.path("actualEvidence"));
                assertThat(assessed.status().name()).as("%s %s", fixture.path("id").asText(), assessed.findings()).isEqualTo(fixture.path("expectedStatus").asText());
                assertThat(payload).isEqualTo(before);
                return;
            }
            if (payload.has("productPayload")) {
                var assessed = new com.coreauth.validator.canonical.ProductActualPayloadAdapter().validatePayload(
                        payload.path("productPayload"), payload.path("segment").asText());
                assertThat(assessed.isValid()).as("%s %s", fixture.path("id").asText(), assessed.errors())
                        .isEqualTo(!"INVALID".equals(fixture.path("expectedStatus").asText()));
                if (!assessed.isValid()) assertThat(assessed.errors()).anyMatch(error -> error.reason().contains(fixture.path("ruleId").asText()));
                if (fixture.has("expectedTargetStatus")) {
                    String segmentId = payload.path("segment").asText();
                    JsonNode productPayload = payload.path("productPayload");
                    assertThat(com.coreauth.validator.canonical.ProductActualPayloadAdapter.observedFindings(assessed, segmentId,
                            productPayload.path("102".equals(segmentId) ? "Product Code Data Segment" : "Adjusted Product Code Data Segment"),
                            productPayload.get("Standard Segment"), productPayload.get("context"))).anyMatch(f ->
                            f.ruleId().equals(fixture.path("targetRuleId").asText(fixture.path("ruleId").asText()))
                                    && f.scope().equals(fixture.path("targetScope").asText())
                                    && f.status().name().equals(fixture.path("expectedTargetStatus").asText()));
                }
                assertThat(payload).isEqualTo(before);
                return;
            }
            var result = payload.has("actualPayload") ? "119".equals(payload.path("segment").asText())
                    ? new com.coreauth.validator.canonical.Segment119ActualPayloadAdapter(mapper.readTree(Atl105Paths.testOutput()
                    .resolve("test-solution-independent-review").resolve("all-element-inventory.json").toFile())).validate(
                    payload.path("actualPayload"), payload.path("messageFamily").asText(), payload.path("actualEvidence"))
                    : new Chapter12ActualPayloadAdapter().validate(payload.path("actualPayload"),
                    payload.path("segment").asText(), payload.path("messageFamily").asText(), payload.path("actualEvidence"),
                    payload.get("actualStandard"), payload.get("actualProducts"), payload.get("actualMerchant")) : validator.validate(payload);
            assertThat(result.status().name()).as("%s %s", fixture.path("id").asText(), result.findings()).isEqualTo(fixture.path("expectedStatus").asText());
            assertThat(result.findings()).as(fixture.path("id").asText()).filteredOn(f -> f.ruleId().equals(fixture.path("targetRuleId").asText(fixture.path("ruleId").asText()))
                    && f.scope().equals(fixture.path("targetScope").asText())).singleElement()
                    .satisfies(f -> assertThat(f.status().name()).isEqualTo(fixture.path("expectedTargetStatus").asText()));
            assertThat(payload).isEqualTo(before);
        }));
    }

    @TestFactory Stream<DynamicTest> rejectsNontextActualRequiredFieldsInsteadOfMetadataReplacement() throws Exception {
        return fixtures().stream().filter(f -> f.path("id").asText().matches("ACTUAL-(?:[0-9]+|DL8)-VALID"))
                .map(f -> DynamicTest.dynamicTest(f.path("id").asText() + "-NONTEXT", () -> {
                    ObjectNode payload = f.path("payload").deepCopy();
                    ((ObjectNode) payload.path("actualPayload")).put("SegmentLength", 19);
                    ((ObjectNode) payload.path("actualEvidence")).put("SegmentLength", "0019");
                    assertThat(new Chapter12ActualPayloadAdapter().validate(payload.path("actualPayload"),
                            payload.path("segment").asText(), payload.path("messageFamily").asText(),
                            payload.path("actualEvidence")).status()).isEqualTo(Status.INVALID);
                }));
    }

    @Test void capkRejectsWrongLoadSequenceBlockChecksumAndMalformedObservedData() throws Exception {
        for (String field : List.of("86", "11", "187", "83")) {
            var payload = input("CAPK-MATCHED-LOAD");
            ((ObjectNode) payload.path("history").path("emv").path("capkLoad").path("response").path("elements"))
                    .put(field, switch (field) { case "86" -> "100002"; case "11" -> "002"; case "83" -> "X"; default -> "0000000000000000000000002"; });
            target(payload, "capk-load-session", Status.INVALID);
        }
        var payload = input("CAPK-MATCHED-LOAD");
        ((ObjectNode) payload.path("history").path("emv")).put("capkLoad", 1);
        target(payload, "capk-load-session", Status.INVALID);
        payload = input("CAPK-MATCHED-LOAD");
        ((ObjectNode) payload.path("history").path("emv").path("capkLoad").path("response").path("elements")).remove("11");
        target(payload, "capk-load-session", Status.REVIEW_REQUIRED);
    }

    @Test void fleetBindsObservedCaptureAndSubsequentMapsToMatchedTransactionAndDevice() throws Exception {
        var payload = fleetExchange();
        var saved = payload.deepCopy();
        target(payload, "prompt-transaction", Status.CHECKS_PASSED);
        target(payload, "capture-observation", Status.CHECKS_PASSED);
        target(payload, "subsequent-observation", Status.CHECKS_PASSED);
        assertThat(payload).isEqualTo(saved);
        ((ObjectNode) payload.path("history").path("fleet").path("transactionExchange").path("capture")).put("86", "123455");
        target(payload, "prompt-transaction", Status.INVALID);
        payload = fleetExchange();
        ((ObjectNode) payload.path("history").path("fleet").path("transactionExchange").path("subsequent")).put("102", "+*13500101002");
        target(payload, "prompt-transaction", Status.INVALID);
        payload = fleetExchange();
        ((ObjectNode) payload.path("history").path("fleet").path("transactionExchange").path("capture").path("promptResponses")).put("DRID", "1101");
        target(payload, "capture-observation", Status.INVALID);
        payload = fleetExchange();
        ((ObjectNode) payload.path("history").path("fleet")).remove("subsequentResponses");
        target(payload, "subsequent-observation", Status.REVIEW_REQUIRED);
    }

    @Test void parentTransactionEvidenceCannotBeOverriddenByChildAndInputStaysUnchanged() throws Exception {
        var child = input("EMV-MATCHED-TRANSACTION");
        var parent = mapper.createObjectNode().put("specificationVersion", "2026-3")
                .put("messageFamily", "EMV Financial Transaction Response").put("completeness", "PARTIAL");
        parent.putObject("elements");
        parent.putObject("context").set("transactionElements", child.path("context").path("transactionElements").deepCopy());
        parent.putArray("segments").addObject().put("segment", "131").put("dataSection", 2).set("observation", child);
        var saved = parent.deepCopy();
        assertThat(new Chapter11MessageLayoutValidator().validate(parent).findings()).anyMatch(f ->
                f.scope().equals("checksum-transaction") && f.status() == Status.CHECKS_PASSED);
        assertThat(parent).isEqualTo(saved);
        ((ObjectNode) child.path("context").path("transactionElements")).put("86", "654321");
        assertThat(new Chapter11MessageLayoutValidator().validate(parent).findings()).anyMatch(f ->
                f.scope().equals("segment-context") && f.status() == Status.INVALID);
    }

    @Test void dynamicTableRejectsMalformedMappingsAndDoesNotDefaultFromIncompleteEvidence() throws Exception {
        var payload = input("FLEET-CATEGORY-INCOMPLETE");
        target(payload, "dynamic-card-table", Status.REVIEW_REQUIRED);
        ((ObjectNode) payload.path("history").path("fleet").path("dynamicCardTable")).put("productsComplete", true);
        target(payload, "fleet-product-correlation", Status.CHECKS_PASSED);
        ((ObjectNode) payload.path("history").path("fleet").path("dynamicCardTable").path("products")).put("103", 1);
        target(payload, "dynamic-card-table", Status.INVALID);
    }

    @Test void actualCurrentTransactionCannotBeFilledFromMetadataAndObservedCompanionIsAuthoritative() throws Exception {
        var numbered = input("EMV-MATCHED-TRANSACTION");
        var actual = mapper.createObjectNode().put("SegmentType", "131").put("SegmentLength", "0060")
                .put("CAPublicKeyFileChecksum", "0000000000000000000000001").put("EMVChipDataLength", "009")
                .put("EMVChipData", "8407A0000000041010");
        var evidence = mapper.createObjectNode();
        evidence.set("context", numbered.path("context").deepCopy());
        evidence.set("history", numbered.path("history").deepCopy());
        var adapter = new Chapter12ActualPayloadAdapter();
        assertThat(adapter.validate(actual, "131", "EMV Financial Transaction Response", evidence).findings())
                .anyMatch(f -> f.scope().equals("checksum-transaction") && f.status() == Status.REVIEW_REQUIRED);
        var standard = mapper.createObjectNode().put("SequenceNumber", "123456").put("TerminalIdentifier", "+*13500101001");
        var saved = evidence.deepCopy();
        assertThat(adapter.validate(actual, "131", "EMV Financial Transaction Response", evidence, standard).findings())
                .anyMatch(f -> f.scope().equals("checksum-transaction") && f.status() == Status.CHECKS_PASSED);
        assertThat(evidence).isEqualTo(saved);
        standard.put("SequenceNumber", "123457");
        assertThat(adapter.validate(actual, "131", "EMV Financial Transaction Response", evidence, standard).findings())
                .anyMatch(f -> f.scope().equals("actual-parent-context") && f.status() == Status.INVALID)
                .anyMatch(f -> f.scope().equals("checksum-transaction") && f.status() == Status.INVALID);
    }

    @Test void parentCapkHistoryAndProtocolRemainAuthoritativeAndReuseTheExistingFileOracle() throws Exception {
        var child = input("CAPK-FILE-ASSEMBLY");
        var parent = mapper.createObjectNode().put("specificationVersion", "2026-3")
                .put("messageFamily", "CA Public Key File Load Request").put("completeness", "PARTIAL");
        parent.putObject("elements");
        parent.set("history", child.path("history").deepCopy());
        parent.putObject("context").put("protocol", "MULTI_TRANS");
        child.remove("history");
        parent.putArray("segments").addObject().put("segment", "132").put("dataSection", 2).set("observation", child);
        var saved = parent.deepCopy();
        assertThat(new Chapter11MessageLayoutValidator().validate(parent).findings()).anyMatch(f ->
                f.ruleId().equals("CH13-E194-PROCESSING-FILE-ASSEMBLY") && f.status() == Status.CHECKS_PASSED);
        assertThat(parent).isEqualTo(saved);
        child.putObject("context").put("protocol", "DRI");
        assertThat(new Chapter11MessageLayoutValidator().validate(parent).findings()).anyMatch(f ->
                f.scope().equals("segment-context") && f.status() == Status.INVALID);
    }

    @Test void allNumberedAdaptersRejectContradictoryChildEnvelopeDirections() throws Exception {
        var child = input("ACTUAL-139-VALID");
        var numbered = mapper.readTree(Atl105Paths.testJson("chapter-12-segment-observations.json").toFile()).path("observations");
        JsonNode base = null;
        for (var fixture : numbered) if ("139".equals(fixture.path("payload").path("segment").asText())) { base = fixture.path("payload"); break; }
        assertThat(base).isNotNull();
        ObjectNode observation = base.deepCopy();
        observation.put("direction", "RESPONSE");
        var parent = mapper.createObjectNode().put("specificationVersion", "2026-3")
                .put("messageFamily", child.path("messageFamily").asText()).put("direction", "REQUEST").put("completeness", "PARTIAL");
        parent.putObject("elements");
        parent.putArray("segments").addObject().put("segment", "139").put("dataSection", 2).set("observation", observation);
        assertThat(new Chapter11MessageLayoutValidator().validate(parent).findings()).anyMatch(f ->
                f.scope().equals("segment-context") && f.status() == Status.INVALID);
    }

    private ObjectNode fleetExchange() throws Exception {
        var payload = mapper.createObjectNode().put("specificationVersion", "2026-3").put("segment", "146").put("direction", "RESPONSE");
        payload.putObject("elements").put("85", "146").put("84", "022");
        payload.putArray("tables").addObject().put("id", "004").put("length", "010").put("value", "|DRID:Z;N|");
        payload.putObject("context").put("authorizer", "COMDATA").putObject("transactionElements").put("86", "123456").put("102", "+*13500101001");
        var history = payload.putObject("history").putObject("fleet");
        history.putObject("capturedResponses").put("DRID", "1100");
        history.putObject("subsequentResponses");
        var exchange = history.putObject("transactionExchange");
        for (String event : List.of("request", "response", "capture", "subsequent"))
            exchange.putObject(event).put("86", "subsequent".equals(event) ? "123457" : "123456").put("102", "+*13500101001");
        ((ObjectNode) exchange.path("capture")).putObject("promptResponses").put("DRID", "1100");
        ((ObjectNode) exchange.path("subsequent")).putObject("promptResponses");
        return payload;
    }

    private void target(JsonNode payload, String scope, Status status) throws Exception {
        assertThat(validator.validate(payload).findings()).filteredOn(f -> f.scope().equals(scope)).singleElement()
                .satisfies(f -> assertThat(f.status()).isEqualTo(status));
    }
}
