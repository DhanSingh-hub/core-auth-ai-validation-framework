package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter12LegacyActualPayloadAdapter;
import com.coreauth.validator.coverage.Chapter12SourceLocalFixtures;
import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Atl105AiElementIntake;
import com.coreauth.validator.validation.Chapter13DataElementValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter12LegacyWireMicrIntegrationTest {
    private final ObjectMapper mapper = new ObjectMapper();

    private List<JsonNode> fixtures() throws Exception {
        return Chapter12SourceLocalFixtures.expand(
                mapper.readTree(Atl105Paths.testJson("chapter-12-segment-observations.json").toFile()),
                mapper.readTree(Atl105Paths.testJson("chapter-12-source-local-probes.json").toFile())).stream()
                .filter(f -> f.path("id").asText().matches("ACTUAL118-ECHO-.*|LEGACY(101|104|105|108)-(WIRE|LENGTH|CAP|HOST|COMPLETION)-.*|LEGACY(109|110|113)-WIRE-.*|ECA-(MICR|VOID)-.*")).toList();
    }

    @TestFactory Stream<DynamicTest> canonicalPredicatesAlsoExecuteThroughActualIntakeWithoutMutation() throws Exception {
        var intake = new Atl105AiElementIntake(Atl105Paths.root(), true);
        var adapter = new Chapter12LegacyActualPayloadAdapter(new Chapter13DataElementValidator(Atl105Paths.root()));
        return fixtures().stream().map(f -> DynamicTest.dynamicTest(f.path("id").asText(), () -> {
            var payload = f.path("payload");
            var message = payload.path("actualMessage");
            var metadata = payload.path("actualEvidence").isObject()
                    ? payload.path("actualEvidence").deepCopy() : mapper.createObjectNode();
            var before = message.deepCopy();
            var metadataBefore = metadata.deepCopy();
            String rule = f.path("targetRuleId").asText(f.path("ruleId").asText());
            var actual = adapter.validate(message, payload.path("segment").asText(), payload.path("messageFamily").asText(), metadata);
            assertThat(actual.status().name()).as("%s", actual.findings()).isEqualTo(f.path("expectedStatus").asText());
            assertThat(actual.findings()).anyMatch(finding -> finding.ruleId().equals(rule)
                    && finding.scope().equals(f.path("targetScope").asText())
                    && finding.status().name().equals(f.path("expectedTargetStatus").asText()));
            var envelope = mapper.createObjectNode();
            envelope.set(payload.path("messageFamily").asText(), message.deepCopy());
            assertThat(intake.assess(metadata, envelope).intakeFindings()).anyMatch(finding ->
                    finding.ruleId().equals(rule)
                            && finding.status().name().equals(f.path("expectedTargetStatus").asText()));
            assertThat(message).isEqualTo(before);
            assertThat(metadata).isEqualTo(metadataBefore);
        }));
    }

    @Test void measuredSourceCapsUseActualBytesAndDoNotTreatTerminalConventionAsCertified() throws Exception {
        var adapter = new Chapter12LegacyActualPayloadAdapter();
        for (var fixture : fixtures().stream().filter(f -> f.path("id").asText().matches("LEGACY(109|110|113)-WIRE-CAP(232|168|156)")).toList()) {
            var p = fixture.path("payload");
            var observed = p.path("actualMessage").path("Observed");
            int cap = Integer.parseInt(fixture.path("id").asText().replaceAll(".*CAP", ""));
            assertThat(observed.path("SerializedSegment").textValue()).hasSize(cap);
            assertThat(observed.path("SegmentLength").textValue()).isEqualTo(String.format("%03d", cap));
            assertThat(adapter.validate(p.path("actualMessage"), p.path("segment").asText(), p.path("messageFamily").asText()).findings())
                    .anyMatch(f -> f.ruleId().equals("CH12-LEGACY-TERMINAL-FS") && f.status().name().equals("REVIEW_REQUIRED"));
        }
    }

    @Test void defaultIntakeDoesNotExecuteOptInWireOrObservedMicrPredicates() throws Exception {
        var intake = new Atl105AiElementIntake(Atl105Paths.root());
        var fixture = fixtures().stream().filter(f -> f.path("id").asText().equals("ECA-MICR-51-WRONG-EXTENSION")).findFirst().orElseThrow();
        var payload = fixture.path("payload");
        var root = mapper.createObjectNode();
        root.set(payload.path("messageFamily").asText(), payload.path("actualMessage"));
        assertThat(intake.assess(payload.path("actualEvidence"), root).intakeFindings())
                .noneMatch(f -> f.ruleId().equals("SEG113-R-014"));
    }

    @Test void fourNewCapsAreMeasuredFromObservedRepresentationAndBoundariesAreNotInvented() throws Exception {
        var selected = fixtures().stream().filter(f -> f.path("id").asText().matches("LEGACY(101|104|105|108)-CAP-(61|86|409|142)")).toList();
        assertThat(selected).hasSize(4);
        for (var fixture : selected) {
            var p = fixture.path("payload");
            var raw = p.path("actualMessage").path("Observed").path("SerializedSegment").textValue();
            assertThat(raw).hasSize(Integer.parseInt(fixture.path("id").asText().replaceAll(".*CAP-", "")));
            assertThat(raw.chars().allMatch(c -> c <= 127)).isTrue();
        }
    }
}
