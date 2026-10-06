package com.coreauth.validator.coverage;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateAppendixIFirstBatchCandidatesTest {
    @Test
    void everyLogicalCandidateHasBidirectionalLinksAndPreservesReviewBoundaries() throws IOException {
        var report = GenerateAppendixIFirstBatchCandidates.generate(Path.of("specifications", "ATL105"));
        assertThat(report.path("businessRequirements")).hasSize(9);
        assertThat(report.path("testScenarios")).hasSize(9);
        assertThat(report.path("testCases")).hasSize(18);
        assertThat(report.path("testData")).hasSize(18);
        Set<String> tdIds = new HashSet<>();
        for (var td : report.path("testData")) {
            assertThat(tdIds.add(td.path("id").asText())).isTrue();
            assertThat(td.path("readiness").asText()).isEqualTo("REVIEW_REQUIRED");
            assertThat(td.path("payloadScope").asText()).contains("NOT_COMPLETE");
            assertThat(td.path("testCaseIds")).hasSize(1);
            var linked = report.path("testCases").findValues("id").stream()
                    .anyMatch(id -> id.asText().equals(td.path("testCaseIds").get(0).asText()));
            assertThat(linked).isTrue();
            if ("009".equals(td.path("request").path("variableInformation").path("tableId").asText())) {
                assertThat(td.path("logicalValidation").findValues("status"))
                        .anyMatch(status -> status.asText().equals("REVIEW_REQUIRED"));
                assertThat(td.has("segmentSerializationCandidate")).isFalse();
            } else {
                var segment = td.path("segmentSerializationCandidate");
                String wire = segment.path("wireFormat").asText();
                assertThat(wire.length()).isEqualTo(segment.path("segmentLength").asInt());
                assertThat(wire.chars().filter(c -> c == '\u001c').count()).isEqualTo(3);
                assertThat(wire.substring(8, 14)).isEqualTo(
                        segment.path("variableInformationIndicator").asText()
                                + segment.path("variableInformationLength").asText());
                assertThat(wire.substring(14, wire.length() - 1))
                        .isEqualTo(segment.path("variableInformation").asText());
            }
        }
        for (var tc : report.path("testCases")) {
            assertThat(tdIds).contains(tc.path("testDataIds").get(0).asText());
            assertThat(tc.path("given").asText()).isNotBlank();
            assertThat(tc.path("when").asText()).isNotBlank();
            assertThat(tc.path("then").asText()).isNotBlank();
        }
        assertThat(report.path("counts").path("completeExecutableRequests").asInt()).isZero();
        assertThat(report.path("counts").path("certifiedRules").asInt()).isZero();
        assertThat(report.path("counts").path("aiCoveragePercent").isNull()).isTrue();
    }
}
