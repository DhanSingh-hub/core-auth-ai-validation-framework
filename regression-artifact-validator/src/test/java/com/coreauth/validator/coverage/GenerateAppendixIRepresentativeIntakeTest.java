package com.coreauth.validator.coverage;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateAppendixIRepresentativeIntakeTest {
    @Test
    void joinsImmutableRepresentativeWithoutAcceptingProducerApprovalOrHidingPhysicalGaps() throws IOException {
        var report = GenerateAppendixIRepresentativeIntake.generate(Path.of("specifications", "ATL105"));
        assertThat(report.path("observedChain").path("explicitIdsJoin").asBoolean()).isTrue();
        assertThat(report.path("independentSource").path("sha256").asText())
                .isEqualTo("6d03bf09f25b9126975b584aef83c21d1b7b081cabccfb9af3b89d46a26d06aa");
        assertThat(report.path("immutableInputs")).hasSize(5);
        for (var input : report.path("immutableInputs")) {
            assertThat(input.path("sha256").asText()).matches("[0-9a-f]{64}");
        }
        assertThat(report.path("findings")).anyMatch(f -> f.path("ruleId").asText()
                .equals("APPI-S111-LENGTH-PRESENCE") && f.path("status").asText().equals("FAIL"));
        assertThat(report.path("findings")).anyMatch(f -> f.path("ruleId").asText()
                .equals("APPI-E112-DATA-LENGTH") && f.path("status").asText().equals("FAIL")
                && f.path("declaredDataLength").asInt() == 1 && f.path("actualDataCharacters").asInt() == 2);
        assertThat(report.path("producerApprovalAcceptedAsOracle").asBoolean()).isFalse();
        assertThat(report.path("counts").path("aiCoveragePercent").isNull()).isTrue();
        assertThat(report.path("counts").path("certifiedRules").asInt()).isZero();
        assertThat(report.toString()).doesNotContain("TerminalID", "VariableInformationLength", "\"Value\"");
    }
}
