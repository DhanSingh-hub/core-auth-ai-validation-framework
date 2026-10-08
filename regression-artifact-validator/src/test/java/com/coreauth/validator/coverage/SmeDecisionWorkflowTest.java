package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SmeDecisionWorkflowTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @TempDir Path directory;

    @Test
    void pendingCrosswalkDecisionCanBeResolvedAndPromotedOnlyWithIndependentBrText() throws Exception {
        ObjectNode register = register(pending("D-1", "REQ-1", "100"));
        ObjectNode decision = record(register, "NEW_RULE", "Independent amount rule", "ATL105 requires the amount to be numeric and 12 digits or fewer.", "");

        assertThat(decision.path("revision").asInt()).isEqualTo(2);
        assertThat(decision.path("supersedesDecisionId").asText()).isEqualTo("D-1");
        Path registerFile = directory.resolve("semantic-decisions.json");
        mapper.writeValue(registerFile.toFile(), register);
        ValidateSmeDecisionRegister.main(new String[]{registerFile.toString()});

        ObjectNode promoted = GenerateApprovedAtomicBusinessRequirements.generate(register, mapper);
        assertThat(promoted.path("businessRequirements")).hasSize(1);
        assertThat(promoted.path("businessRequirements").get(0).path("title").asText()).isEqualTo("Independent amount rule");
        assertThat(promoted.path("businessRequirements").get(0).path("requirement").asText())
                .contains("numeric and 12 digits").doesNotContain("AI");
        assertThat(promoted.path("summary").path("executionReady").asBoolean()).isFalse();
    }

    @Test
    void supersedingNewRuleWithRejectRemovesItsPromotion() {
        ObjectNode register = register(pending("D-1", "REQ-1", "100"));
        record(register, "NEW_RULE", "Independent amount rule", "ATL105 amount rule", "");
        record(register, "REJECT", "", "The candidate is unsupported in this context.", "");

        ObjectNode promoted = GenerateApprovedAtomicBusinessRequirements.generate(register, mapper);
        ObjectNode coverage = GeneratePromotedDecisionCoverage.summarize(register, mapper);
        ObjectNode summary = GenerateSmeDecisionSummary.summarize(register, mapper);
        assertThat(promoted.path("businessRequirements")).isEmpty();
        assertThat(promoted.path("summary").path("rejectedNotNewRules").asInt()).isEqualTo(1);
        assertThat(coverage.path("newRuleDecisionCount").asInt()).isZero();
        assertThat(coverage.path("rejectedDecisionCount").asInt()).isEqualTo(1);
        assertThat(coverage.path("promotedDecisionCount").asInt()).isZero();
        assertThat(summary.path("decisionHistoryEventCount").asInt()).isEqualTo(3);
        assertThat(summary.path("effectiveSubjectCount").asInt()).isEqualTo(1);
        assertThat(summary.path("byDecision").path("REJECT").asInt()).isEqualTo(1);
        assertThat(summary.path("byDecision").path("PENDING").asInt()).isZero();
    }

    @Test
    void newRulePromotionRejectsWordingNotDeclaredIndependentOfAi() {
        ObjectNode register = register(pending("D-1", "REQ-1", "100"));
        record(register, "NEW_RULE", "Independent amount rule", "ATL105 amount rule", "");
        ((ObjectNode) register.path("decisions").get(1).path("approvedRule")).put("independentOfAiWording", false);

        assertThatThrownBy(() -> GenerateApprovedAtomicBusinessRequirements.generate(register, mapper))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("independent of AI wording");
    }

    @Test
    void jsonDecisionInputRequiresAnchorSegmentAndIndependenceAttestation() {
        ObjectNode register = register(pending("D-1", "REQ-1", "100"));
        ObjectNode input = mapper.createObjectNode().put("subjectType", "AI_TO_TEST_CROSSWALK")
                .put("subjectId", "REQ-1").put("segment", "100").put("decision", "NEW_RULE")
                .put("reviewer", "reviewer@example.test").put("rationale", "Reviewed against ATL105.")
                .put("independenceAttestation", "I_AUTHORED_THIS_INDEPENDENTLY_FROM_AI");
        input.putObject("sourceAnchor").put("specification", "ATL105").put("version", "2026-3")
                .put("section", "12.1").put("segment", "100").put("element", "41").put("rule", "amount");
        input.putObject("approvedRule").put("title", "Fuel amount format")
                .put("requirement", "Fuel amount uses its source-defined numeric representation.");

        ObjectNode recorded = RecordSmeDecision.record(register, input);
        assertThat(recorded.path("approvedRule").path("independentOfAiWording").asBoolean()).isTrue();

        ObjectNode sourceAnchor = (ObjectNode) input.path("sourceAnchor");
        sourceAnchor.put("segment", "DL5");
        assertThatThrownBy(() -> RecordSmeDecision.record(register(pending("D-1", "REQ-1", "100")), input))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("for its segment");

        sourceAnchor.put("segment", "100");
        input.put("independenceAttestation", "");
        assertThatThrownBy(() -> RecordSmeDecision.record(register(pending("D-1", "REQ-1", "100")), input))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("I_AUTHORED_THIS_INDEPENDENTLY_FROM_AI");
    }

    @Test
    void explicitDecisionRegisterIsScopedBeneathTestOutput() {
        Path testOutput = directory.resolve("ATL105/test-output");
        Path run4Register = testOutput.resolve("ai-solution-independent-review/Run4/decisions.json");

        assertThat(RecordSmeDecision.explicitRegisterPath(testOutput, run4Register)).isEqualTo(run4Register);
        assertThatThrownBy(() -> RecordSmeDecision.explicitRegisterPath(testOutput,
                directory.resolve("outside/decisions.json")))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("beneath ATL105 test-output");
    }

    @Test
    void rejectedDuplicateAndConfirmedOutcomesNeedTheirOwnPromotionEvidence() {
        ObjectNode register = register(pending("D-1", "REQ-1", "100"));
        assertThatThrownBy(() -> record(register, "NEW_RULE", "", "rationale", ""))
            .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("I_AUTHORED_THIS_INDEPENDENTLY_FROM_AI");
        assertThatThrownBy(() -> record(register, "DUPLICATE", "", "duplicate", ""))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("relatedTestRuleId");
        assertThatThrownBy(() -> record(register, "CONFIRMED_MATCH", "", "match", ""))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("relatedTestRuleId");

        ObjectNode unknownTarget = register(pending("D-1", "REQ-1", "100"));
        assertThatThrownBy(() -> record(unknownTarget, "DUPLICATE", "", "duplicate", "SEG999-R-001"))
            .isInstanceOf(IllegalStateException.class).hasMessageContaining("unknown Test Solution rule SEG999-R-001");
        assertThat(unknownTarget.path("decisions")).hasSize(1);
        assertThat(unknownTarget.path("decisions").get(0).path("decision").asText()).isEqualTo("PENDING");
    }

    @Test
    void coverageSummaryUsesEffectiveOutcomesAndDoesNotCreditDuplicateOrReject() {
        ObjectNode register = register(
                pending("D-1", "REQ-1", "100"),
                pending("D-2", "REQ-2", "DL5"),
                pending("D-3", "REQ-3", "DL6"));
        record(register, "NEW_RULE", "New rule", "Independent ATL105 rule", "");
        recordFor(register, "REQ-2", "DL5", "DUPLICATE", "", "Same rule already exists", "SEG100-R-001");
        recordFor(register, "REQ-3", "DL6", "CONFIRMED_MATCH", "", "Exact source-backed match", "SEGDL5-R-001");

        ObjectNode summary = GeneratePromotedDecisionCoverage.summarize(register, mapper);
        assertThat(summary.path("effectiveDecisionCount").asInt()).isEqualTo(3);
        assertThat(summary.path("newRuleDecisionCount").asInt()).isEqualTo(1);
        assertThat(summary.path("duplicateDecisionCount").asInt()).isEqualTo(1);
        assertThat(summary.path("confirmedMatchDecisionCount").asInt()).isEqualTo(1);
        assertThat(summary.path("promotedDecisionCount").asInt()).isEqualTo(2);
        assertThat(summary.path("promotedBySegment").path("100").asInt()).isEqualTo(1);
        assertThat(summary.path("promotedBySegment").path("DL5").asInt()).isZero();
    }

    private ObjectNode record(ObjectNode register, String decision, String title, String requirement, String relatedRuleId) {
        return recordFor(register, "REQ-1", "100", decision, title, requirement, relatedRuleId);
    }

    private ObjectNode recordFor(ObjectNode register, String subjectId, String segment, String decision,
                                 String title, String requirement, String relatedRuleId) {
        return RecordSmeDecision.record(register, subjectId, segment, decision, "reviewer@example.test",
                "Reviewed against ATL105 2026-3.", "12.1", "41", "fuel-purchase-amount", "AI_TO_TEST_CROSSWALK",
            title, requirement, relatedRuleId,
            "NEW_RULE".equals(decision) ? "I_AUTHORED_THIS_INDEPENDENTLY_FROM_AI" : "");
    }

    private ObjectNode register(ObjectNode... decisions) {
        ObjectNode register = mapper.createObjectNode();
        register.put("artifact", "test-solution-sme-decision-register");
        register.putArray("subjectTypes").add("AI_TO_TEST_CROSSWALK");
        register.putArray("decisions").addAll(java.util.List.of(decisions));
        return register;
    }

    private ObjectNode pending(String id, String subjectId, String segment) {
        return mapper.createObjectNode().put("decisionId", id).put("subjectType", "AI_TO_TEST_CROSSWALK")
                .put("subjectId", subjectId).put("segment", segment).put("decision", "PENDING")
                .put("reviewer", "UNREVIEWED").put("rationale", "Awaiting semantic review.");
    }
}