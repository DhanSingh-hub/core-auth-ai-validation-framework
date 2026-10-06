package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FieldRuleSmeDecisionRegisterTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String QUOTE = "Authorization amount uses the source-defined field format.";
    private static final String SPECIFICATION = "--- PAGE 100 ---\n" + QUOTE;

    @Test
    void recordsACompleteSourceCitedFieldDecisionAppendOnly() {
        ObjectNode queue = queue();
        ObjectNode register = FieldRuleSmeDecisionRegister.emptyRegister(mapper);
        ObjectNode recorded = FieldRuleSmeDecisionRegister.record(register, queue, input(queue, "REQUIRED"), SPECIFICATION);

        assertThat(recorded.path("decision").asText()).isEqualTo("RULE_CONFIRMED");
        assertThat(recorded.path("revision").asInt()).isEqualTo(1);
        assertThat(register.path("decisions")).hasSize(1);
        assertThat(FieldRuleSmeDecisionRegister.validateRegister(register, queue, SPECIFICATION))
                .containsKey("FIELD_RULE_CONTEXT|FRC-100-41");
    }

    @Test
    void unresolvedDimensionRemainsReviewRequiredAndCanBeSuperseded() {
        ObjectNode queue = queue();
        ObjectNode register = FieldRuleSmeDecisionRegister.emptyRegister(mapper);
        ObjectNode unresolved = input(queue, "UNRESOLVED");
        FieldRuleSmeDecisionRegister.record(register, queue, unresolved, SPECIFICATION);
        ObjectNode confirmed = input(queue, "REQUIRED");
        ObjectNode recorded = FieldRuleSmeDecisionRegister.record(register, queue, confirmed, SPECIFICATION);

        assertThat(register.path("decisions")).hasSize(2);
        assertThat(register.path("decisions").get(0).path("decision").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(recorded.path("decision").asText()).isEqualTo("RULE_CONFIRMED");
        assertThat(recorded.path("revision").asInt()).isEqualTo(2);
        assertThat(recorded.path("supersedesDecisionId").asText()).isEqualTo(register.path("decisions").get(0).path("decisionId").asText());
    }

    @Test
    void rejectsWrongContextAnchorAndUnverifiedSourceQuote() {
        ObjectNode queue = queue();
        ObjectNode wrongContext = input(queue, "REQUIRED");
        ((ObjectNode) wrongContext.path("sourceAnchor")).put("segment", "DL2");
        assertThatThrownBy(() -> FieldRuleSmeDecisionRegister.record(FieldRuleSmeDecisionRegister.emptyRegister(mapper), queue, wrongContext, SPECIFICATION))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("does not match queued context");

        ObjectNode invalidQuote = input(queue, "REQUIRED");
        ((ObjectNode) invalidQuote.path("fieldRuleDecision").path("applicability").path("evidence").get(0)).put("quote", "Invented source text.");
        assertThatThrownBy(() -> FieldRuleSmeDecisionRegister.record(FieldRuleSmeDecisionRegister.emptyRegister(mapper), queue, invalidQuote, SPECIFICATION))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("does not occur");
    }

    @Test
    void rejectsQuoteWhenItDoesNotMatchTheClaimedPage() {
        ObjectNode queue = queue();
        ObjectNode input = input(queue, "REQUIRED");
        ((ObjectNode) input.path("fieldRuleDecision").path("applicability").path("evidence").get(0)).put("sourcePage", 101);

        assertThatThrownBy(() -> FieldRuleSmeDecisionRegister.record(FieldRuleSmeDecisionRegister.emptyRegister(mapper), queue, input, SPECIFICATION))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cited ATL105 PDF page 101");
    }

    @Test
    void conditionalApplicabilityRequiresAnExplicitTriggerExpression() {
        ObjectNode queue = queue();
        ObjectNode input = input(queue, "CONDITIONAL");
        ((ObjectNode) input.path("fieldRuleDecision").path("conditionalTrigger")).put("status", "SOURCE_STATED");
        ((ObjectNode) input.path("fieldRuleDecision").path("permittedOmission")).put("status", "CONDITION_DEPENDENT");

        assertThatThrownBy(() -> FieldRuleSmeDecisionRegister.record(FieldRuleSmeDecisionRegister.emptyRegister(mapper), queue, input, SPECIFICATION))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conditionalTrigger.condition");
    }

    @Test
    void acceptsDependencyOnlyWhenItTargetsAQueuedDifferentSegment() {
        ObjectNode queue = queue();
        ObjectNode related = addQueueSubject(queue, "FRC-101-42", "42", "101");
        queue.put("subjectCount", 2);
        ObjectNode input = input(queue, "REQUIRED");
        ObjectNode dependency = (ObjectNode) input.path("fieldRuleDecision").path("crossSegmentDependency");
        dependency.put("status", "REQUIRES");
        dependency.put("relation", "Segment 100 field is required when companion Segment 101 is present.");
        dependency.putArray("relatedContextSubjectIds").add(related.path("subjectId").asText());

        ObjectNode recorded = FieldRuleSmeDecisionRegister.record(FieldRuleSmeDecisionRegister.emptyRegister(mapper), queue, input, SPECIFICATION);
        assertThat(recorded.path("decision").asText()).isEqualTo("RULE_CONFIRMED");
    }

    @Test
    void rejectsDependencyTargetInTheSameSegment() {
        ObjectNode queue = queue();
        ObjectNode related = addQueueSubject(queue, "FRC-100-42", "42", "100");
        queue.put("subjectCount", 2);
        ObjectNode input = input(queue, "REQUIRED");
        ObjectNode dependency = (ObjectNode) input.path("fieldRuleDecision").path("crossSegmentDependency");
        dependency.put("status", "REQUIRES");
        dependency.put("relation", "Requires another field.");
        dependency.putArray("relatedContextSubjectIds").add(related.path("subjectId").asText());

        assertThatThrownBy(() -> FieldRuleSmeDecisionRegister.record(FieldRuleSmeDecisionRegister.emptyRegister(mapper), queue, input, SPECIFICATION))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("different segment");
    }

    @Test
    void rejectsInconsistentRequiredAndOmissionDecision() {
        ObjectNode queue = queue();
        ObjectNode input = input(queue, "REQUIRED");
        ((ObjectNode) input.path("fieldRuleDecision").path("permittedOmission")).put("status", "PERMITTED");

        assertThatThrownBy(() -> FieldRuleSmeDecisionRegister.record(FieldRuleSmeDecisionRegister.emptyRegister(mapper), queue, input, SPECIFICATION))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("REQUIRED applicability cannot permit omission");
    }

    private ObjectNode queue() {
        ObjectNode queue = mapper.createObjectNode();
        queue.put("artifact", "ATL105-FIELD-RULE-SME-ADJUDICATION-QUEUE");
        queue.put("specification", "ATL105");
        queue.put("specificationVersion", "2026-3");
        queue.put("status", "PENDING_REVIEW_NOT_APPROVED");
        queue.put("subjectCount", 1);
        queue.putArray("subjects");
        addQueueSubject(queue, "FRC-100-41", "41", "100");
        return queue;
        }

        private ObjectNode addQueueSubject(ObjectNode queue, String subjectId, String element, String segment) {
        ObjectNode subject = ((com.fasterxml.jackson.databind.node.ArrayNode) queue.path("subjects")).addObject().put("subjectType", "FIELD_RULE_CONTEXT")
            .put("subjectId", subjectId).put("decisionStatus", "PENDING").put("element", element)
            .put("reviewer", "UNREVIEWED").put("messageFamily", "Financial Transaction Request").put("segment", segment);
        subject.putArray("dimensionsToAdjudicate").add("applicability").add("conditionalTrigger")
            .add("permittedOmission").add("crossSegmentDependency");
        subject.putObject("currentGeneratedDecision").put("decisionStatus", "REVIEW_REQUIRED");
        subject.putObject("sourceAnchor").put("specification", "ATL105").put("version", "2026-3")
            .put("section", "11.1.1").put("segment", segment).put("element", element).put("rule", "Authorization amount field");
        subject.putObject("sourceEvidence").putObject("templateFieldSource")
                .put("source_id", "SRC-ATL105-PDF-001").put("page", 100);
        return subject;
    }

    private ObjectNode input(ObjectNode queue, String applicability) {
        JsonNode subject = queue.path("subjects").get(0);
        ObjectNode input = mapper.createObjectNode().put("subjectType", "FIELD_RULE_CONTEXT")
                .put("subjectId", subject.path("subjectId").asText()).put("element", "41")
                .put("messageFamily", "Financial Transaction Request").put("segment", "100")
                .put("reviewer", "sme@example.test").put("rationale", "Reviewed against the cited ATL105 text.");
        input.set("sourceAnchor", subject.path("sourceAnchor").deepCopy());
        ObjectNode decision = input.putObject("fieldRuleDecision");
        addDimension(decision, "applicability", applicability, "REQUIRED", false);
        addDimension(decision, "conditionalTrigger", "NOT_APPLICABLE", "NOT_APPLICABLE", false);
        addDimension(decision, "permittedOmission", "NOT_PERMITTED", "NOT_PERMITTED", false);
        addDimension(decision, "crossSegmentDependency", "NONE", "NONE", true);
        return input;
    }

    private void addDimension(ObjectNode decision, String key, String status, String anchorRule, boolean relatedContexts) {
        decision.putObject(key).put("status", status).put("rationale", "Source reviewed for this dimension.");
        ObjectNode target = (ObjectNode) decision.path(key);
        target.putArray("evidence").addObject().put("sourceId", "SRC-ATL105-PDF-001").put("sourcePage", 100).put("quote", QUOTE)
                .putObject("sourceAnchor").put("specification", "ATL105").put("version", "2026-3")
                .put("section", "11.1.1").put("segment", "100").put("element", "41").put("rule", anchorRule);
        if (relatedContexts) target.putArray("relatedContextSubjectIds");
    }
}
