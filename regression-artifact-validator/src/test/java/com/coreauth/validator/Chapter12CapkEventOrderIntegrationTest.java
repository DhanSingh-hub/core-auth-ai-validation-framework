package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter12ActualPayloadAdapter;
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

class Chapter12CapkEventOrderIntegrationTest {
    private final ObjectMapper mapper = new ObjectMapper();

    private List<JsonNode> fixtures() throws Exception {
        return Chapter12SourceLocalFixtures.expand(
                mapper.readTree(Atl105Paths.testJson("chapter-12-segment-observations.json").toFile()),
                mapper.readTree(Atl105Paths.testJson("chapter-12-source-local-probes.json").toFile())).stream()
                .filter(f -> f.path("id").asText().startsWith("ACTUAL130-CAPK-ORDER-")).toList();
    }

    @TestFactory Stream<DynamicTest> observedLoadOrderingExecutesThroughActualIntakeWithoutFillingIdentity() throws Exception {
        var adapter = new Chapter12ActualPayloadAdapter();
        var intake = new Atl105AiElementIntake(Atl105Paths.root(), true);
        return fixtures().stream().map(f -> DynamicTest.dynamicTest(f.path("id").asText(), () -> {
            var p = f.path("payload");
            var before = p.deepCopy();
            var evidence = p.path("actualEvidence");
            var actual = adapter.validate(p.path("actualPayload"), "130", p.path("messageFamily").asText(),
                    evidence, p.get("actualStandard"));
            assertThat(actual.status().name()).as("%s", actual.findings()).isEqualTo(f.path("expectedStatus").asText());
            assertThat(actual.findings()).anyMatch(finding -> finding.ruleId().equals("SEG130-R-005")
                    && finding.scope().equals("capk-load-event-order")
                    && finding.status().name().equals(f.path("expectedTargetStatus").asText()));
            var root = mapper.createObjectNode();
            var message = root.putObject(p.path("messageFamily").asText());
            message.put("MessageType", "0200").put("NumSegments", "02");
            message.set("Observed", p.path("actualPayload").deepCopy());
            if (p.has("actualStandard")) message.set("Standard", p.path("actualStandard").deepCopy());
            assertThat(intake.assess(evidence, root).intakeFindings()).anyMatch(finding -> finding.ruleId().equals("SEG130-R-005")
                    && finding.reason().contains("bound load request precedes")
                    && finding.status().name().equals(f.path("expectedTargetStatus").asText()));
            assertThat(p).isEqualTo(before);
        }));
    }

    @Test void legacyDefaultDoesNotExecuteNewObservedChronology() throws Exception {
        var p = fixtures().getFirst().path("payload");
        var root = mapper.createObjectNode();
        var message = root.putObject(p.path("messageFamily").asText());
        message.set("Observed", p.path("actualPayload"));
        message.set("Standard", p.path("actualStandard"));
        assertThat(new Atl105AiElementIntake(Atl105Paths.root()).assess(p.path("actualEvidence"), root).intakeFindings())
                .noneMatch(finding -> finding.reason().contains("bound load request precedes"));
    }
}
