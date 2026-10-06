package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.TestDataIndependenceValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
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

    @Test
    void appendixAliasesNormalizeLinksAndAnchorsWithoutPromotingStatus() throws Exception {
        JsonNode source = mapper.readTree("""
            {"appendix":"O",
             "businessRequirements":[{"id":"BR-1","sourceAnchor":{"specification":"ATL105","version":"2026-3","section":"Appendix O","rule":"token"},"status":"PARTIALLY_COVERED"}],
             "testScenarios":[{"id":"TS-1","covers":["BR-1"],"status":"COVERED"}],
             "testCases":[{"id":"TC-1","scenarioId":"TS-1","positive":"valid token","negative":["invalid token"],"status":"COVERED"}],
             "testData":[{"id":"TD-1","coversBr":"BR-1","readiness":"EXECUTABLE"}]}
            """);

        var normalized = AppendixCoveragePackageNormalizer.normalize(source);

        assertThat(normalized.path("businessRequirements").get(0).path("sourceAnchors").size()).isEqualTo(1);
        assertThat(normalized.path("businessRequirements").get(0).path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(normalized.path("businessRequirements").get(0).path("sourceStatus").asText()).isEqualTo("PARTIALLY_COVERED");
        assertThat(normalized.path("testScenarios").get(0).path("requirementIds").get(0).asText()).isEqualTo("BR-1");
        assertThat(normalized.path("testScenarios").get(0).path("sourceAnchors").size()).isEqualTo(1);
        assertThat(normalized.path("testScenarios").get(0).path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(normalized.path("testCases").get(0).path("scenarioIds").get(0).asText()).isEqualTo("TS-1");
        assertThat(normalized.path("testCases").get(0).path("sourceAnchors").size()).isEqualTo(1);
        assertThat(normalized.path("testCases").get(0).path("expectedOutcome").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(normalized.path("testCases").get(0).path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(normalized.path("testCases").get(0).path("sourceStatus").asText()).isEqualTo("COVERED");
        assertThat(normalized.path("testData").get(0).has("testCaseIds")).isFalse();
        assertThat(source.path("businessRequirements").get(0).has("sourceAnchors")).isFalse();
    }

    @Test
    void unlinkedCandidateResolvesTCPathWithoutBecomingCanonicalTestData() throws Exception {
        Map<String, JsonNode> candidates = new LinkedHashMap<>();
        candidates.put("TD-1", mapper.readTree("""
            {"sourceArtifactId":"TD-1","sourceArtifact":{"coversBr":"BR-1"}}
            """));
        candidates.put("TD-2", mapper.readTree("""
            {"sourceArtifactId":"TD-2","sourceArtifact":{"coversBr":null}}
            """));
        Map<String, JsonNode> scenarios = new LinkedHashMap<>();
        scenarios.put("TS-1", mapper.readTree("""
            {"id":"TS-1","requirementIds":["BR-1"]}
            """));
        Map<String, JsonNode> testCases = new LinkedHashMap<>();
        testCases.put("TC-1", mapper.readTree("""
            {"id":"TC-1","scenarioIds":["TS-1"]}
            """));

        GenerateAllTestSolutionBrTsTcTdPackage.resolveCandidateTestCaseLinks(
            candidates, scenarios, testCases, mapper);

        assertThat(candidates.get("TD-1").path("candidateResolution").asText())
            .isEqualTo("TC_PATH_RESOLVED_FIXTURE_REQUIRED");
        assertThat(candidates.get("TD-1").path("candidateTestCaseIds").get(0).asText()).isEqualTo("TC-1");
        assertThat(candidates.get("TD-2").path("candidateResolution").asText())
            .isEqualTo("NO_BUSINESS_REQUIREMENT_HINT");
        assertThat(candidates.get("TD-2").path("candidateTestCaseIds")).isEmpty();
    }

    @Test
    void syntheticAppendixFixturesAreDraftedOnlyForSourceClaimedCases() throws Exception {
        List<String> fixtureIds = List.of(
            "TD-SEG100-APPO-TOKEN-ACCOUNT", "TD-SEG100-APPO-TOKEN-NOT-PAN",
            "TD-SEG100-APPO-TOKEN-LUHN", "TD-SEG100-APPO-TOKEN-EXPIRATION",
            "TD-SEG100-APPO-CRYPTOGRAM-FORMAT", "TD-SEG100-APPO-CRYPTOGRAM-BLOCK-SPLIT",
            "TD-SEG100-APPO-ENTRY-MODE", "TD-SEG100-APPR-EMV-COMPANION",
            "TD-SEG100-APPR-CHIP-LENGTH", "TD-SEG100-APPR-TLV-FORMAT",
            "TD-SEG100-APPR-CROSS-FIELD", "TD-SEG100-APPS-CA-FILE-HEADER",
            "TD-SEG100-APPS-CA-KEY-RECORD", "TD-SEG100-APPT-EMV-TABLE-DATA",
            "TD-SEG100-APPT-CARC", "TD-SEG100-APPT-ECHO",
            "TD-SEG100-APPY-ECI-CRYPTOGRAM", "TD-SEG100-APPY-AMEX-SAFEKEY-FORMAT");
        JsonNode requirement = mapper.readTree("""
            {"sourceAnchors":[{"specification":"ATL105","version":"2026-3","section":"Appendix","rule":"synthetic-fixture"}]}
            """);
        List<CanonicalTestData> fixtureData = new java.util.ArrayList<>();

        for (String id : fixtureIds) {
            var candidate = mapper.createObjectNode();
            candidate.put("sourceArtifactId", id);
            candidate.put("producerClaimedReadiness", "EXECUTABLE");
            candidate.putArray("candidateTestCaseIds").add("TC-1");
            var draft = AppendixSyntheticFixtureDrafts.create(candidate, requirement, mapper);

            assertThat(draft).isNotNull();
            assertThat(draft.path("expectedValidation").asText()).isEqualTo("REVIEW");
            assertThat(draft.path("readiness").asText()).isEqualTo("REVIEW_REQUIRED");
            assertThat(draft.path("testCaseIds").get(0).asText()).isEqualTo("TC-1");
            assertThat(draft.path("sourceAnchors").size()).isEqualTo(1);
            assertThat(draft.path("payload").path("request").isObject()).isTrue();
            fixtureData.add(mapper.treeToValue(draft, CanonicalTestData.class));
        }

        CanonicalArtifactPackage fixturePackage = new CanonicalArtifactPackage();
        fixturePackage.setTestData(fixtureData);
        assertThat(new TestDataIndependenceValidator().validate(fixturePackage).errors()).isEmpty();

        var external = mapper.createObjectNode();
        external.put("sourceArtifactId", "TD-EXTERNAL");
        external.put("producerClaimedReadiness", "EXTERNAL_FIXTURE_REQUIRED");
        external.putArray("candidateTestCaseIds").add("TC-1");
        assertThat(AppendixSyntheticFixtureDrafts.create(external, requirement, mapper)).isNull();
    }
}