package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter12ActualPayloadAdapter;
import com.coreauth.validator.canonical.Chapter12LegacyActualPayloadAdapter;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Finding;
import com.coreauth.validator.canonical.ProductActualPayloadAdapter;
import com.coreauth.validator.coverage.Chapter12SourceLocalFixtures;
import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Atl105AiElementIntake;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter12FleetDiscountLoadIntegrationTest {
    private final ObjectMapper mapper = new ObjectMapper();

    private List<JsonNode> fixtures() throws Exception {
        return Chapter12SourceLocalFixtures.expand(
                mapper.readTree(Atl105Paths.testJson("chapter-12-segment-observations.json").toFile()),
                mapper.readTree(Atl105Paths.testJson("chapter-12-source-local-probes.json").toFile())).stream()
                .filter(f -> f.path("id").asText().matches("(FLEET6|PRODUCT6|LOAD6|SITE7|CAPK7|TAX7|SCALAR7|SITE8|RECEIPT8|CAPK8|PRODUCT8|WIRE8|TAX8)-.*")).toList();
    }

    @TestFactory Stream<DynamicTest> actualSourcePredicatesExecuteThroughIndependentIntakeWithoutMutation() throws Exception {
        var intake = new Atl105AiElementIntake(Atl105Paths.root(), true);
        return fixtures().stream().map(f -> DynamicTest.dynamicTest(f.path("id").asText(), () -> {
            JsonNode p = f.path("payload");
            var before = p.deepCopy();
            List<Finding> findings;
            String actualStatus;
            var root = mapper.createObjectNode();
            var evidence = mapper.createObjectNode();
            if (p.has("actualMessage")) {
                var result = new Chapter12LegacyActualPayloadAdapter().validate(p.path("actualMessage"),
                        p.path("segment").asText(), p.path("messageFamily").asText(), p.path("actualEvidence"));
                actualStatus = result.status().name();
                findings = result.findings();
                root.set(p.path("messageFamily").asText(), p.path("actualMessage").deepCopy());
                if (p.has("actualEvidence")) evidence = p.path("actualEvidence").deepCopy();
            } else if (p.has("productPayload")) {
                JsonNode payload = p.path("productPayload");
                var result = new ProductActualPayloadAdapter().validatePayload(payload, "102");
                actualStatus = result.isValid() ? "REVIEW_REQUIRED" : "INVALID";
                findings = ProductActualPayloadAdapter.observedFindings(result, "102", payload.path("Product Code Data Segment"),
                        payload.get("Standard Segment"), payload.get("context"));
                var message = root.putObject("Financial Transaction Request");
                message.put("MessageType", "0200").put("NumSegments", "02");
                message.set("Products", payload.path("Product Code Data Segment").deepCopy());
                message.set("Standard", payload.path("Standard Segment").deepCopy());
                evidence.set("context", payload.path("context").deepCopy());
            } else {
                var result = new Chapter12ActualPayloadAdapter().validate(p.path("actualPayload"), p.path("segment").asText(),
                        p.path("messageFamily").asText(), p.path("actualEvidence"), p.get("actualStandard"), p.get("actualProducts"));
                actualStatus = result.status().name();
                findings = result.findings();
                var message = root.putObject(p.path("messageFamily").asText());
                message.put("MessageType", "0200").put("NumSegments", "02");
                message.set("Observed", p.path("actualPayload").deepCopy());
                if (p.has("actualStandard")) message.set("Standard", p.path("actualStandard").deepCopy());
                if (p.has("actualProducts")) message.set("Products", p.path("actualProducts").deepCopy());
                evidence = p.path("actualEvidence").deepCopy();
            }
            String rule = f.path("targetRuleId").asText(f.path("ruleId").asText());
            assertThat(actualStatus).as("%s", findings).isEqualTo(f.path("expectedStatus").asText());
            var targeted = findings.stream().filter(finding -> finding.ruleId().equals(rule)
                    && finding.scope().equals(f.path("targetScope").asText())).toList();
            assertThat(targeted).as("%s %s", f.path("id").asText(), findings).isNotEmpty()
                    .allMatch(finding -> finding.status().name().equals(f.path("expectedTargetStatus").asText()));
            var assessed = intake.assess(evidence, root);
            for (var target : targeted) assertThat(assessed.intakeFindings()).anyMatch(finding -> finding.ruleId().equals(rule)
                    && finding.reason().equals(target.reason()) && finding.status().name().equals(target.status().name()));
            assertThat(p).isEqualTo(before);
        }));
    }

    @Test void actualMissingScalarEvidenceDoesNotRelaxLegacyRequiredFields() throws Exception {
        var validator = new com.coreauth.validator.canonical.Segment118PayloadValidator();
        for (var fixture : fixtures()) if (List.of("SCALAR7-902-DATA-MISSING", "SCALAR7-905-DATA-MISSING")
                .contains(fixture.path("id").asText())) {
            var payload = fixture.path("payload");
            var legacy = mapper.createObjectNode();
            com.fasterxml.jackson.databind.node.ObjectNode message = payload.path("actualMessage").deepCopy();
            message.set("Proprietary Data Load Segment", message.remove("Load"));
            legacy.set(payload.path("messageFamily").asText(), message);
            assertThat(validator.validatePayload(legacy).isValid()).isFalse();
            assertThat(validator.validateActualPayload(legacy).isValid()).isTrue();
            assertThat(validator.validateActualPayload(legacy).warnings()).isNotEmpty();
        }
    }

    @Test void recordSectionBoundariesUseMeasured81ByteSourceRecordsNotCharacterGuesses() throws Exception {
        var list = fixtures();
        for (int count : List.of(0, 44, 45)) {
            var f = list.stream().filter(node -> node.path("id").asText().equals("LOAD6-904-BODY-" + (count == 0 ? "ZERO" : count) + "-RECORDS")).findFirst().orElseThrow();
            var segment = f.path("payload").path("actualMessage").path("Load");
            String wire = segment.path("SerializedSegment").textValue();
            assertThat(wire).hasSize(127 + 14 + count * 81);
            assertThat(segment.path("SegmentLength").textValue()).isEqualTo(String.format("%04d", wire.length()));
            assertThat(wire).doesNotContain("\u001c");
        }
    }

    @Test void defaultIntakeDoesNotExecuteNewFleetBindingOrDiscountWirePolicy() throws Exception {
        var p = fixtures().getFirst().path("payload");
        var root = mapper.createObjectNode();
        root.putObject(p.path("messageFamily").asText()).set("Observed", p.path("actualPayload"));
        assertThat(new Atl105AiElementIntake(Atl105Paths.root()).assess(p.path("actualEvidence"), root).intakeFindings())
                .noneMatch(f -> f.reason().contains("measured captured Host Table004"));
    }

    @Test void receiptCapsMeasureRepeatedBytesSeparatelyFrom26ByteDateHeader() throws Exception {
        for (int repeatedBytes : List.of(220, 221)) {
            var f = fixtures().stream().filter(node -> node.path("id").asText().equals("LOAD6-901-CAP-" + repeatedBytes))
                    .findFirst().orElseThrow();
            var load = f.path("payload").path("actualMessage").path("Load");
            String raw = load.path("SerializedSegment").textValue();
            assertThat(raw).hasSize(127 + 26 + repeatedBytes);
            assertThat(load.path("SegmentLength").asInt()).isEqualTo(raw.length());
            assertThat(raw).doesNotContain("\u001c");
        }
    }

    @Test void everySourceFiveDigitComponentIsIndependentlyRequiredAndCannotBeFilledFromActualVector() throws Exception {
        var fixture = fixtures().stream().filter(node -> node.path("id").asText().equals("LOAD6-176-CURRENT-VECTOR")).findFirst().orElseThrow();
        JsonNode payload = fixture.path("payload");
        var validator = new com.coreauth.validator.canonical.Segment118PayloadValidator();
        for (String field : com.coreauth.validator.canonical.Segment118PayloadValidator.DEVICE_VERSION_COMPONENTS) {
            var evidence = payload.path("actualEvidence").deepCopy();
            var components = (com.fasterxml.jackson.databind.node.ObjectNode) evidence.path("history").path("proprietaryLoad").path("deviceState").path("components");
            components.remove(field);
            assertThat(validator.validateActualDeviceVersions(payload.path("actualMessage").path("Load"), evidence, true))
                    .anyMatch(f -> f.scope().equals("actual-device-version-components") && f.status().name().equals("REVIEW_REQUIRED"));
            components.put(field, "0000");
            assertThat(validator.validateActualDeviceVersions(payload.path("actualMessage").path("Load"), evidence, true))
                    .anyMatch(f -> f.scope().equals("actual-device-version-components") && f.status().name().equals("INVALID"));
        }
    }
}
