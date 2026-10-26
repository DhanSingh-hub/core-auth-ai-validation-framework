package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100CardNetworkFlowValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100CardNetworkFlowValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Segment100CardNetworkFlowValidator validator = new Segment100CardNetworkFlowValidator();

    @Test
    void validatesNetworkAgreementAndEverySourceAnchoredFlowStep() throws Exception {
        var result = validator.validate(MAPPER.readTree(artifact("""
                [{"role":"ORIGINAL","transactionType":"3","cardType":"020"},
                 {"role":"COMPLETION","transactionType":"0","cardType":"020"}]
                """)), MAPPER.readTree(policy("""
                [{"network":"VISA","flowId":"AUTH_COMPLETION","stepIndex":0,"transactionType":"3","cardType":"020","disposition":"SUPPORTED","sourceAnchors":[{"specification":"ATL105","version":"2026-3","section":"10","rule":"authorization"}]},
                 {"network":"VISA","flowId":"AUTH_COMPLETION","stepIndex":1,"transactionType":"0","cardType":"020","disposition":"SUPPORTED","sourceAnchors":[{"specification":"ATL105","version":"2026-3","section":"10","rule":"completion"}]}]
                """)));

        assertThat(result.status()).isEqualTo(Segment100CardNetworkFlowValidator.Status.PASS);
    }

    @Test
    void rejectsConflictingBinAndProcessorEvidence() throws Exception {
        String artifact = artifact("""
                [{"role":"SINGLE","transactionType":"0","cardType":"020"}]
                """).replace("\"processorNetwork\":{\"network\":\"VISA\"", "\"processorNetwork\":{\"network\":\"MASTERCARD\"");

        var result = validator.validate(MAPPER.readTree(artifact), MAPPER.readTree(policy("[]")));

        assertThat(result.status()).isEqualTo(Segment100CardNetworkFlowValidator.Status.FAIL);
        assertThat(result.findings()).anyMatch(finding -> finding.code().equals("NETWORK_EVIDENCE_CONFLICT"));
    }

    @Test
    void requiresReviewWhenCombinationHasNoSourceBackedRule() throws Exception {
        var result = validator.validate(MAPPER.readTree(artifact("""
                [{"role":"SINGLE","transactionType":"0","cardType":"020"}]
                """)), MAPPER.readTree(policy("[]")));

        assertThat(result.status()).isEqualTo(Segment100CardNetworkFlowValidator.Status.REVIEW_REQUIRED);
        assertThat(result.findings()).anyMatch(finding -> finding.code().equals("RULE_NOT_CONFIGURED"));
        assertThat(result.openQuestions()).singleElement().satisfies(question -> {
            assertThat(question.owner()).isEqualTo("Business Analyst / ATL105 SME");
            assertThat(question.question()).contains("supported, prohibited, or conditional");
            assertThat(question.context()).contains("VISA", "SINGLE_PURCHASE", "020");
        });
    }

    @Test
    void doesNotAcceptEligibilityRuleWithoutCompleteSourceAnchor() throws Exception {
        var policy = policy("""
                [{"network":"VISA","flowId":"SINGLE_PURCHASE","stepIndex":0,"transactionType":"0","cardType":"020","disposition":"SUPPORTED","sourceAnchors":[]}]
                """);
        var result = validator.validate(MAPPER.readTree(artifact("""
                [{"role":"SINGLE","transactionType":"0","cardType":"020"}]
                """)), MAPPER.readTree(policy));

        assertThat(result.status()).isEqualTo(Segment100CardNetworkFlowValidator.Status.REVIEW_REQUIRED);
        assertThat(result.findings()).anyMatch(finding -> finding.code().equals("RULE_WITHOUT_SOURCE"));
        assertThat(result.openQuestions()).singleElement()
                .satisfies(question -> assertThat(question.question()).contains("exact ATL105 clause"));
    }

    @Test
    void requiresRolesAndOrderedStepCountToMatchFlowDefinition() throws Exception {
        String policy = policy("""
                [{"network":"VISA","flowId":"SINGLE_PURCHASE","stepIndex":0,"transactionType":"0","cardType":"020","disposition":"SUPPORTED","sourceAnchors":[{"specification":"ATL105","version":"2026-3","section":"10","rule":"purchase"}]}]
                """).replace("\"stepRoles\":[\"SINGLE\"]", "\"stepRoles\":[\"ORIGINAL\",\"COMPLETION\"]");
        var result = validator.validate(MAPPER.readTree(artifact("""
                [{"role":"SINGLE","transactionType":"0","cardType":"020"}]
                """)), MAPPER.readTree(policy));

        assertThat(result.status()).isEqualTo(Segment100CardNetworkFlowValidator.Status.FAIL);
        assertThat(result.findings()).anyMatch(finding -> finding.code().equals("FLOW_SHAPE_MISMATCH"));
    }

    private static String artifact(String steps) {
        return """
                {
                  "flowId":"%s",
                  "networkEvidence":{
                    "binIinResolution":{"network":"VISA","rangeId":"synthetic-range","rangeTableVersion":"test-2026-01"},
                    "processorNetwork":{"network":"VISA","source":"synthetic-processor"}
                  },
                  "steps":%s
                }
                """.formatted(steps.contains("ORIGINAL") ? "AUTH_COMPLETION" : "SINGLE_PURCHASE", steps);
    }

    private static String policy(String rules) {
        return """
                {
                  "policyVersion":"test-policy-1",
                  "networks":["VISA","MASTERCARD"],
                  "transactionTypes":["0","3"],
                  "cardTypes":["020"],
                  "flows":[
                    {"id":"SINGLE_PURCHASE","stepRoles":["SINGLE"]},
                    {"id":"AUTH_COMPLETION","stepRoles":["ORIGINAL","COMPLETION"]}
                  ],
                  "rules":%s
                }
                """.formatted(rules);
    }
}
