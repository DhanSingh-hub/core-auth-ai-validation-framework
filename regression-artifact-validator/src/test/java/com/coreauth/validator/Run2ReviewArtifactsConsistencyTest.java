package com.coreauth.validator;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Run2ReviewArtifactsConsistencyTest {
    private static final List<String> SEGMENTS = List.of(
            "100", "101", "102", "103", "104", "105", "108", "109", "111", "113");
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void generatedReviewArtifactsAreDenominatorCompleteAndInternallyConsistent() throws Exception {
        Path root = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        JsonNode composite = mapper.readTree(root.resolve("run1-run2-composite-delivery.json").toFile());
        assertThat(composite.path("deliveryLabel").asText()).isEqualTo("2026-09-23/Run1+Run2");
        assertThat(composite.path("linkageVerification").path("verified").asBoolean()).isTrue();
        assertThat(composite.path("linkageVerification").path("matchingIdsAndStatements").asInt())
                .isEqualTo(6473);
        Path compositePackage = root.resolve("run1-run2-composite-delivery");
        JsonNode manifest = mapper.readTree(compositePackage.resolve(
                "composite-delivery-manifest.json").toFile());
        JsonNode confirmedMatches = mapper.readTree(compositePackage.resolve(
                "confirmed-matches.json").toFile());
        assertThat(manifest.path("mergeType").asText()).isEqualTo("LOGICAL_REFERENCE_MERGE");
        assertThat(manifest.path("linkage").path("verified").asBoolean()).isTrue();
        assertThat(confirmedMatches.path("confirmedMatches").size()).isEqualTo(30);
        assertThat(confirmedMatches.path("summary").path("confirmedAiTestPairs").asInt()).isEqualTo(425);
        assertThat(confirmedMatches.path("summary").path("distinctAiRequirements").asInt()).isEqualTo(312);
        assertThat(Files.readAllLines(compositePackage.resolve("confirmed-match-pairs.csv"))).hasSize(426);
        assertThat(compositePackage.resolve("confirmed-matches.md")).isRegularFile();
        int denominator = 0;
        int confirmed = 0;
        int review = 0;
        int missing = 0;
        int fullChain = 0;
        for (String segment : SEGMENTS) {
            JsonNode crosswalk = mapper.readTree(root.resolve(Path.of("run2-crosswalks",
                    "segment-" + segment + "-run2-crosswalk.json")).toFile());
            JsonNode validation = mapper.readTree(root.resolve(Path.of("run2-validation",
                    "segment-" + segment + "-validation.json")).toFile());
            assertThat(crosswalk.path("mappings").size()).isEqualTo(crosswalk.path("summary").path("denominator").asInt());
            assertThat(validation.path("versionResolutionId").asText())
                    .isEqualTo("ATL105-RUN2-SPEC-VERSION-RESOLUTION-001");
            assertThat(validation.path("sourceRun").asText()).isEqualTo("2026-09-23/Run1+Run2");
            denominator += validation.path("assessment").path("coverageDenominator").asInt();
            confirmed += validation.path("assessment").path("confirmedRequirements").asInt();
            review += validation.path("assessment").path("reviewRequired").asInt();
            missing += validation.path("assessment").path("missingRequirements").asInt();
            fullChain += validation.path("assessment").path("fullChainRequirements").asInt();
            assertThat(root.resolve(Path.of("run2-segment-reports",
                    "segment-" + segment + "-run2-review.md"))).isRegularFile();
            assertThat(root.resolve(Path.of("run2-segment-reports",
                    "segment-" + segment + "-run2-review.html"))).isRegularFile();
        }
        JsonNode executive = mapper.readTree(root.resolve(Path.of("run2-executive-report",
                "run2-weighted-executive-report.json")).toFile());
        assertThat(executive.path("sourceRun").asText()).isEqualTo("2026-09-23/Run1+Run2");
        executive = executive.path("weightedSummary");
        assertThat(executive.path("coverageDenominator").asInt()).isEqualTo(denominator).isEqualTo(235);
        assertThat(executive.path("confirmedRequirements").asInt()).isEqualTo(confirmed).isEqualTo(30);
        assertThat(executive.path("reviewRequired").asInt()).isEqualTo(review).isEqualTo(70);
        assertThat(executive.path("missingRequirements").asInt()).isEqualTo(missing).isEqualTo(135);
        assertThat(executive.path("fullChainRequirements").asInt()).isEqualTo(fullChain).isEqualTo(25);

        JsonNode queue = mapper.readTree(root.resolve(Path.of("run2-sme-review-queue",
                "run2-sme-review-queue.json")).toFile());
        assertThat(queue.path("items").size()).isEqualTo(queue.path("summary").path("totalOpenItems").asInt())
                .isEqualTo(263);
        assertThat(Files.readAllLines(root.resolve(Path.of("run2-sme-review-queue",
                "run2-sme-review-queue.csv")))).hasSize(264);
    }
}