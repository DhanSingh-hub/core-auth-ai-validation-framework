package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SmeDecisionHistoryTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void reviewedDecisionAppendsAndSupersedesPendingDecision() {
        ObjectNode register = register(decision("D-1", "AI_TO_TEST_CROSSWALK", "REQ-1", "100", "PENDING"));
        ObjectNode reviewed = SmeDecisionHistory.append(register,
                decision("D-2", "AI_TO_TEST_CROSSWALK", "REQ-1", "100", "CONFIRMED_MATCH"));

        assertThat(register.path("decisions")).hasSize(2);
        assertThat(reviewed.path("revision").asInt()).isEqualTo(2);
        assertThat(reviewed.path("supersedesDecisionId").asText()).isEqualTo("D-1");
        assertThat(SmeDecisionHistory.latestBySubject(register).get("AI_TO_TEST_CROSSWALK|REQ-1")
                .path("decision").asText()).isEqualTo("CONFIRMED_MATCH");
        assertThat(register.path("decisions").get(0).path("decision").asText()).isEqualTo("PENDING");
    }

    @Test
    void newSubjectStartsAtRevisionOne() {
        ObjectNode register = register();
        ObjectNode appended = SmeDecisionHistory.append(register,
                decision("D-1", "AI_ONLY_BR", "REQ-1", "DL5", "NEW_RULE"));

        assertThat(appended.path("revision").asInt()).isEqualTo(1);
        assertThat(appended.has("supersedesDecisionId")).isFalse();
    }

    @Test
    void invalidSupersessionAndDuplicateDecisionIdsFailClosed() {
        ObjectNode wrongParent = register(
                decision("D-1", "AI_TO_TEST_CROSSWALK", "REQ-1", "100", "PENDING"),
                decision("D-2", "AI_TO_TEST_CROSSWALK", "REQ-1", "100", "NEW_RULE").put("revision", 2).put("supersedesDecisionId", "D-MISSING"));
        assertThatThrownBy(() -> SmeDecisionHistory.latestBySubject(wrongParent))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("must supersede current decision D-1");

        ObjectNode duplicateId = register(
                decision("D-1", "AI_TO_TEST_CROSSWALK", "REQ-1", "100", "PENDING"),
                decision("D-1", "AI_ONLY_BR", "REQ-2", "DL5", "PENDING"));
        assertThatThrownBy(() -> SmeDecisionHistory.latestBySubject(duplicateId))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("Duplicate decisionId: D-1");
    }

    @Test
    void supersedingRevisionCannotChangeSubjectSegment() {
        ObjectNode register = register(
                decision("D-1", "AI_TO_TEST_CROSSWALK", "REQ-1", "100", "PENDING"),
                decision("D-2", "AI_TO_TEST_CROSSWALK", "REQ-1", "DL5", "REJECT").put("revision", 2).put("supersedesDecisionId", "D-1"));

        assertThatThrownBy(() -> SmeDecisionHistory.latestBySubject(register))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("changes segment");
    }

    private ObjectNode register(ObjectNode... decisions) {
        ObjectNode register = mapper.createObjectNode();
        register.putArray("decisions").addAll(java.util.List.of(decisions));
        return register;
    }

    private ObjectNode decision(String id, String subjectType, String subjectId, String segment, String status) {
        return mapper.createObjectNode().put("decisionId", id).put("subjectType", subjectType)
                .put("subjectId", subjectId).put("segment", segment).put("decision", status);
    }
}