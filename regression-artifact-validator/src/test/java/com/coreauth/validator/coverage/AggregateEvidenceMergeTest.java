package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AggregateEvidenceMergeTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void identicalDuplicatesAreCountedOnlyOnce() throws Exception {
        JsonNode values = mapper.readTree("[{\"id\":\"BR-1\",\"status\":\"REVIEW_REQUIRED\"}]");
        Map<String, JsonNode> target = new LinkedHashMap<>();
        Map<String, String> sources = new LinkedHashMap<>();
        int[] firstCounts = new int[4];
        int[] secondCounts = new int[4];
        GenerateAllTestSolutionBrTsTcTdPackage.merge(values, target, sources, "first.json", firstCounts, 0);
        GenerateAllTestSolutionBrTsTcTdPackage.merge(values, target, sources, "second.json", secondCounts, 0);
        assertThat(target).hasSize(1);
        assertThat(firstCounts[0]).isEqualTo(1);
        assertThat(secondCounts[0]).isZero();
    }

    @Test
    void conflictingDuplicatesNameBothSources() throws Exception {
        Map<String, JsonNode> target = new LinkedHashMap<>();
        Map<String, String> sources = new LinkedHashMap<>();
        int[] counts = new int[4];
        GenerateAllTestSolutionBrTsTcTdPackage.merge(mapper.readTree("[{\"id\":\"BR-1\",\"status\":\"DRAFT\"}]"),
            target, sources, "first.json", counts, 0);
        JsonNode conflicting = mapper.readTree("[{\"id\":\"BR-1\",\"status\":\"APPROVED\"}]");
        assertThatThrownBy(() -> GenerateAllTestSolutionBrTsTcTdPackage.merge(conflicting, target, sources, "second.json", counts, 0))
            .isInstanceOf(IllegalStateException.class).hasMessageContaining("BR-1").hasMessageContaining("first.json").hasMessageContaining("second.json");
        assertThat(counts[0]).isEqualTo(1);
    }

    @Test
    void coversBrAloneDoesNotQualifyTestDataForCanonicalChain() throws Exception {
        JsonNode testData = mapper.readTree("""
                {"id":"TD-1","coversBr":"BR-1","readiness":"EXECUTABLE",
                 "expectedValidation":"PASS"}
                """);

        assertThat(CanonicalTestDataLinkPolicy.hasCanonicalTestCaseLinks(testData)).isFalse();
        var candidate = CanonicalTestDataLinkPolicy.unlinkedCandidate(
                testData, "appendix-o.json", mapper);

        assertThat(candidate.path("disposition").asText()).isEqualTo("NOT_IN_CANONICAL_CHAIN");
        assertThat(candidate.path("sourceArtifactId").asText()).isEqualTo("TD-1");
        assertThat(candidate.path("producerClaimedReadiness").asText()).isEqualTo("EXECUTABLE");
        assertThat(candidate.path("sourceArtifact").path("coversBr").asText()).isEqualTo("BR-1");
        assertThat(candidate.path("reason").asText()).contains("not a canonical testCaseIds link");
    }

    @Test
    void canonicalTestDataRequiresNonEmptyTestCaseIds() throws Exception {
        assertThat(CanonicalTestDataLinkPolicy.hasCanonicalTestCaseLinks(
                mapper.readTree("{\"id\":\"TD-1\",\"testCaseIds\":[\"TC-1\"]}"))).isTrue();
        assertThat(CanonicalTestDataLinkPolicy.hasCanonicalTestCaseLinks(
                mapper.readTree("{\"id\":\"TD-1\",\"testCaseIds\":[]}"))).isFalse();
        assertThat(CanonicalTestDataLinkPolicy.hasCanonicalTestCaseLinks(
                mapper.readTree("{\"id\":\"TD-1\",\"testCaseIds\":[\" \"]}"))).isFalse();
    }

    @Test
    void absentOrUnknownReadinessIsReviewRequired() throws Exception {
        var missing = (com.fasterxml.jackson.databind.node.ObjectNode) mapper.readTree("{\"id\":\"TD-1\"}");
        var unknown = (com.fasterxml.jackson.databind.node.ObjectNode) mapper.readTree(
                "{\"id\":\"TD-2\",\"readiness\":\"EXECUTION_READY\"}");

        CanonicalTestDataLinkPolicy.requireExplicitReadiness(missing);
        CanonicalTestDataLinkPolicy.requireExplicitReadiness(unknown);

        assertThat(missing.path("readiness").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(unknown.path("readiness").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(unknown.path("producerDeclaredReadiness").asText()).isEqualTo("EXECUTION_READY");
    }
}