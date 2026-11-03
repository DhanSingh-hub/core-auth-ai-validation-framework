package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.RequirementMatchStatus;
import com.coreauth.validator.canonical.SourceAnchor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReconcileRun2UnmappedCatalogDenominatorTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void extendsOnlyMissingRulesWithoutRewritingHistoricalDisposition() {
        var historical = new Run2Crosswalk(List.of(crosswalk(false).mappings().getFirst()));
        var result = ReconcileRun2UnmappedCatalogDenominator.extendUnmappedCrosswalk(historical, baseline());
        assertThat(result.mappings()).hasSize(2);
        assertThat(result.mappings().getFirst()).isEqualTo(historical.mappings().getFirst());
        assertThat(result.mappings().get(1).matchStatus()).isEqualTo(RequirementMatchStatus.MISSING);
        assertThat(result.mappings().get(1).aiRequirementIds()).isEmpty();
    }

    @Test
    void refusesToRewriteHistoricalConfirmedDisposition() {
        assertThatThrownBy(() -> ReconcileRun2UnmappedCatalogDenominator.extendUnmappedCrosswalk(crosswalk(true), baseline()))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Historical mappings");
    }

    @Test
    void preservesHistoricalExecutionAndAiInventoryWhileRecordingMissingCurrentRules() throws Exception {
        ObjectNode evidence = evidence();
        var result = ReconcileRun2UnmappedCatalogDenominator.reconcile(evidence, crosswalk(false), baseline());
        assertThat(evidence.path("assessment").path("coverageDenominator").asInt()).isEqualTo(1);
        assertThat(result.path("assessment").path("coverageDenominator").asInt()).isEqualTo(2);
        assertThat(result.path("assessment").path("missingRequirements").asInt()).isEqualTo(2);
        assertThat(result.path("assessment").path("fullChainRequirements").asInt()).isZero();
        assertThat(result.path("assessment").path("aiBusinessRequirementDenominator").asInt()).isEqualTo(192);
        assertThat(result.path("assessment").path("executionCertified").asBoolean()).isFalse();
        assertThat(result.path("payloadBatch")).isEqualTo(evidence.path("payloadBatch"));
        assertThat(result.path("assessment").path("traceabilityValidation"))
                .isEqualTo(evidence.path("assessment").path("traceabilityValidation"));
        assertThat(result.path("catalogDenominatorReconciliation").path("historicalExecutionReassessed").asBoolean()).isFalse();
        assertThat(result.path("catalogDenominatorReconciliation").path("currentCanonicalRules")).hasSize(2);
    }

    @Test
    void rejectsAnyCurrentConfirmedMapping() throws Exception {
        assertThatThrownBy(() -> ReconcileRun2UnmappedCatalogDenominator.reconcile(evidence(), crosswalk(true), baseline()))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("explicitly unmapped");
    }

    @Test
    void rejectsHistoricalMatches() throws Exception {
        var evidence = evidence();
        ((ObjectNode) evidence.get("assessment")).put("confirmedRequirements", 1);
        assertThatThrownBy(() -> ReconcileRun2UnmappedCatalogDenominator.reconcile(evidence, crosswalk(false), baseline()))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("entirely unmapped");
    }

    @Test
    void rejectsIncompleteCrosswalk() throws Exception {
        var incomplete = new Run2Crosswalk(List.of(crosswalk(false).mappings().getFirst()));
        assertThatThrownBy(() -> ReconcileRun2UnmappedCatalogDenominator.reconcile(evidence(), incomplete, baseline()))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("omit crosswalk rules");
    }

    @Test
    void rejectsChangedCanonicalAnchor() throws Exception {
        var anchor = anchor("other-rule");
        var incorrect = new Run2Crosswalk(List.of(
                new Run2Crosswalk.Mapping("R1", List.of(), RequirementMatchStatus.MISSING, "none", null, List.of(anchor)),
                crosswalk(false).mappings().get(1)));
        assertThatThrownBy(() -> ReconcileRun2UnmappedCatalogDenominator.reconcile(evidence(), incorrect, baseline()))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exact canonical anchors");
    }

    @Test
    void rejectsCertifiedHistoricalExecution() throws Exception {
        var evidence = evidence();
        ((ObjectNode) evidence.get("assessment")).put("executionCertified", true);
        assertThatThrownBy(() -> ReconcileRun2UnmappedCatalogDenominator.reconcile(evidence, crosswalk(false), baseline()))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("uncertified");
    }

    private ObjectNode evidence() throws Exception {
        return (ObjectNode) mapper.readTree("""
                {"segment":"102","assessment":{"coverageDenominator":1,"missingRequirements":1,
                 "confirmedRequirements":0,"reviewRequired":0,"fullChainRequirements":0,
                 "decision":"REVIEW_REQUIRED","executionCertified":false,"aiBusinessRequirementDenominator":192,
                 "traceabilityValidation":{"valid":false,"errorCount":1}},
                 "payloadBatch":{"invalidPayloadFiles":2,"validatorImplemented":true}}
                """);
    }

    private static IndependentRequirementBaseline baseline() {
        return new IndependentRequirementBaseline(List.of(
                new IndependentRequirementBaseline.Requirement("R1", List.of(anchor("r1")), true),
                new IndependentRequirementBaseline.Requirement("R2", List.of(anchor("r2")), true)));
    }

    private static Run2Crosswalk crosswalk(boolean confirmed) {
        return new Run2Crosswalk(List.of(
                new Run2Crosswalk.Mapping("R1", confirmed ? List.of("AI-1") : List.of(),
                        confirmed ? RequirementMatchStatus.CONFIRMED : RequirementMatchStatus.MISSING,
                        "none", null, List.of(anchor("r1"))),
                new Run2Crosswalk.Mapping("R2", List.of(), RequirementMatchStatus.MISSING, "none", null, List.of(anchor("r2")))));
    }

    private static SourceAnchor anchor(String rule) {
        SourceAnchor anchor = new SourceAnchor();
        anchor.setSpecification("ATL105");
        anchor.setVersion("2026-3");
        anchor.setSegment("102");
        anchor.setSection("12.3");
        anchor.setRule(rule);
        return anchor;
    }
}
