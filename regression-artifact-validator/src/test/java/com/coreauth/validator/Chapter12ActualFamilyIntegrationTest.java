package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter12ActualPayloadAdapter;
import com.coreauth.validator.canonical.Chapter12LegacyActualPayloadAdapter;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.coverage.Chapter12SourceLocalFixtures;
import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Atl105AiElementIntake;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter12ActualFamilyIntegrationTest {
    private final ObjectMapper mapper = new ObjectMapper();

    private List<JsonNode> fixtures() throws Exception {
        return Chapter12SourceLocalFixtures.expand(mapper.readTree(Atl105Paths.testJson("chapter-12-segment-observations.json").toFile()),
                mapper.readTree(Atl105Paths.testJson("chapter-12-source-local-probes.json").toFile()));
    }

    private ObjectNode fixture(String id) throws Exception {
        return fixtures().stream().filter(f -> id.equals(f.path("id").asText())).findFirst().orElseThrow().path("payload").deepCopy();
    }

    @TestFactory Stream<DynamicTest> actualIntakeDispatchesEveryRemainingLegacyFamilyWithoutMetadataFieldsOrMutation() throws Exception {
        var intake = new Atl105AiElementIntake(Atl105Paths.root(), true);
        return fixtures().stream().filter(f -> f.path("id").asText().matches("LEGACY-[0-9]+-VALID"))
                .map(f -> DynamicTest.dynamicTest(f.path("id").asText(), () -> {
                    JsonNode payload = f.path("payload");
                    ObjectNode root = mapper.createObjectNode();
                    root.set(payload.path("messageFamily").asText(), payload.path("actualMessage").deepCopy());
                    ObjectNode metadata = mapper.createObjectNode().put("specificationVersion", "2026-3")
                            .put("messageFamily", payload.path("messageFamily").asText());
                    var saved = root.deepCopy();
                    var result = intake.assess(metadata, root);
                    assertThat(result.intakeFindings()).as(payload.path("segment").asText()).anyMatch(finding ->
                            finding.ruleId().equals("CH12-LEGACY-EXECUTION")
                                    && finding.status() == com.coreauth.validator.validation.Atl105ElementValueValidator.Status.CHECKS_PASSED);
                    assertThat(root).isEqualTo(saved);
                    assertThat(metadata).hasSize(2);
                }));
    }

    @Test void legacyCompanionsCannotBeInventedAndRepeatedIdentitiesStayInvalid() throws Exception {
        var payload = fixture("LEGACY-101-VALID");
        var message = (ObjectNode) payload.path("actualMessage");
        var adapter = new Chapter12LegacyActualPayloadAdapter();
        String fleet = null, standard = null;
        for (var fields = message.fields(); fields.hasNext();) {
            var field = fields.next();
            if ("101".equals(field.getValue().path("SegmentType").asText())) fleet = field.getKey();
            if ("100".equals(field.getValue().path("SegmentType").asText())) standard = field.getKey();
        }
        assertThat(fleet).isNotNull();
        assertThat(standard).isNotNull();
        message.set("duplicate", message.get(fleet).deepCopy());
        assertThat(adapter.validate(message, "101", "Financial Transaction Request").status()).isEqualTo(Status.INVALID);
        message.remove("duplicate");
        message.remove(standard);
        assertThat(adapter.validate(message, "101", "Financial Transaction Request").findings()).anyMatch(f ->
                f.status() == Status.INVALID && f.reason().contains("Standard Segment"));
    }

    @Test void legacyFleetExclusionUsesSourceIdentityNotFriendlyKey() throws Exception {
        var message = (ObjectNode) fixture("LEGACY-101-VALID").path("actualMessage");
        message.putObject("arbitrary fleet key").put("SegmentType", "145");
        assertThat(new Chapter12LegacyActualPayloadAdapter().validate(message, "101", "Financial Transaction Request").findings())
                .anyMatch(f -> f.ruleId().equals("SEG101-R-003") && f.status() == Status.INVALID);
    }

    @Test void variableInfoActualWireRejectsSeparatorInsertionAndDeclaredLengthMismatch() throws Exception {
        var message = (ObjectNode) fixture("LEGACY-111-VALID").path("actualMessage");
        var segment = (ObjectNode) message.path("Information");
        segment.put("SerializedSegment", "111\u001c018\u001c001003ABC\u001c");
        var adapter = new Chapter12LegacyActualPayloadAdapter();
        assertThat(adapter.validate(message, "111", "Financial Transaction Request").findings()).anyMatch(f ->
                f.scope().equals("actual-legacy-wire") && f.status() == Status.CHECKS_PASSED);
        segment.put("SerializedSegment", "111\u001c018\u001c001\u001c003ABC\u001c");
        assertThat(adapter.validate(message, "111", "Financial Transaction Request").status()).isEqualTo(Status.INVALID);
    }

    @Test void rawSkuAndPrintAliasesCannotSilentlyHideContradictoryWire() throws Exception {
        for (String id : List.of("114", "120")) {
            var payload = fixture("LEGACY-" + id + "-VALID");
            var message = (ObjectNode) payload.path("actualMessage");
            for (JsonNode child : message) if (id.equals(child.path("SegmentType").asText()))
                ((ObjectNode) child).put("SerializedSegment", "WRONG");
            assertThat(new Chapter12LegacyActualPayloadAdapter().validate(message, id, payload.path("messageFamily").asText()).status())
                    .isEqualTo(Status.INVALID);
        }

    }

    @Test void actualPrintLengthCountsItsOwnN4WidthWhileLegacyDefaultsRemainUnchanged() throws Exception {
        var message = (ObjectNode) fixture("LEGACY-120-VALID").path("actualMessage");
        var print = (ObjectNode) message.path("Print");
        var adapter = new Chapter12LegacyActualPayloadAdapter();
        assertThat(adapter.validate(message, "120", "Financial Transaction Response").status()).isEqualTo(Status.REVIEW_REQUIRED);
        print.put("SegmentLength", "0008").put("wireFormat", "120\u001c0008\u001cABC");
        assertThat(adapter.validate(message, "120", "Financial Transaction Response").status()).isEqualTo(Status.INVALID);
        var legacy = mapper.createObjectNode();
        legacy.put("messageType", "FINANCIAL_TRANSACTION_RESPONSE");
        legacy.putObject("Financial Transaction Response").putObject("dataSection3").set("Print Data 2 Segment", print.deepCopy());
        assertThat(new com.coreauth.validator.canonical.Segment120PayloadValidator().validatePayload(legacy).isValid()).isTrue();
    }

    @Test void actualTaxCompanionMalformedValuesRemainInvalidAndMetadataIsNeverAFallback() throws Exception {
        var payload = fixture("ACTUAL-TAX-MATCHED-COMPANION");
        var adapter = new Chapter12ActualPayloadAdapter();
        ((ObjectNode) payload.path("actualProducts")).putArray("Products").addObject().put("ProductCode", 1);
        assertThat(adapter.validate(payload.path("actualPayload"), "143", payload.path("messageFamily").asText(),
                payload.path("actualEvidence"), null, payload.path("actualProducts")).status()).isEqualTo(Status.INVALID);
    }

    @Test void merchantCardCountAndWireCapAreMeasuredAtThe99EntryBoundaryWithoutPaddingAssumptions() throws Exception {
        var payload = fixture("ACTUAL-MERCHANT-WIRE");
        var actual = (ObjectNode) payload.path("actualPayload");
        actual.put("MerchantName", "M".repeat(24)).put("AddressLine1", "A".repeat(24)).put("AddressLine2", "CITY         CA 12345");
        var cards = actual.putArray("CardTypes");
        for (int i = 0; i < 99; i++) cards.add("001");
        actual.put("NumberOfCardTypes", "99");
        actual.put("SerializedSegment", "#" + "M".repeat(24) + "0000000000000001" + "A".repeat(24) + "CITY         CA 12345"
                + "(123)456-7890" + "99" + "001".repeat(99) + "~");
        assertThat(actual.path("SerializedSegment").asText()).hasSize(399);
        var adapter = new Chapter12ActualPayloadAdapter();
        assertThat(adapter.validate(actual, "DL1", "Table Load Response", payload.path("actualEvidence")).status()).isEqualTo(Status.REVIEW_REQUIRED);
        cards.add("001");
        assertThat(adapter.validate(actual, "DL1", "Table Load Response", payload.path("actualEvidence")).status()).isEqualTo(Status.INVALID);
    }

    @Test void actualEnvelopeAndMerchantCompanionCannotBeOverriddenByMetadata() throws Exception {
        var payload = fixture("ACTUAL-MERCHANT-WIRE");
        var root = mapper.createObjectNode();
        var response = root.putObject("Table Load Response");
        response.set("Merchant", payload.path("actualPayload").deepCopy());
        var meta = mapper.createObjectNode().put("specificationVersion", "2026-3").put("messageFamily", "Table Load Response");
        meta.set("context", payload.path("actualEvidence").path("context").deepCopy());
        var saved = root.deepCopy();
        var result = new Atl105AiElementIntake(Atl105Paths.root(), true).assess(meta, root);
        assertThat(result.intakeFindings()).anyMatch(f -> f.reason().contains("block inventory contradicts"))
                .anyMatch(f -> f.ruleId().equals("SEGDL1-R-005")
                        && f.status() == com.coreauth.validator.validation.Atl105ElementValueValidator.Status.INVALID);
        assertThat(root).isEqualTo(saved);
    }
}
