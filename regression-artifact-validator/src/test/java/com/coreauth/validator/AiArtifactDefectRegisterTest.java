package com.coreauth.validator;

import com.coreauth.validator.canonical.AiArtifactDefectRegister;
import com.coreauth.validator.canonical.AiExpectedResponseComparator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AiArtifactDefectRegisterTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final AiExpectedResponseComparator comparator = new AiExpectedResponseComparator();

    @Test
    void reportsBrDivergenceAndExpectedResponseConflictsAsReviewCandidates(@TempDir Path temp) throws Exception {
        JsonNode testCase = json("""
            {"id":"TC-1","scenario_id":"SC-1","requirement_ids":["BR-CASE"],"is_flow":true,
             "expected_response":{"response_code":{"value":"3"}}}
            """);
        JsonNode scenario = json("""
            {"id":"SC-1","requirement_ids":["BR-SCENARIO"],"target_transaction":"Financial Transaction Request",
             "expected_response_code":"2"}
            """);
        List<?> findings = AiArtifactDefectRegister.inspectTestCase(testCase, scenario, temp, comparator);
        assertThat(findings).hasSize(2);
        assertThat(findings.toString()).contains("BR_ATTRIBUTION_NO_SHARED_LINK", "EXPECTED_RESPONSE_CONFLICT", "CANDIDATE_REVIEW_REQUIRED");
    }

    @Test
    void distinguishesEmptyPayloadFromMissingPayload(@TempDir Path temp) throws Exception {
        JsonNode scenario = json("""
            {"id":"SC-1","requirement_ids":["BR-1"],"target_transaction":"Financial Transaction Request"}
            """);
        JsonNode emptyCase = json("""
            {"id":"TC-1","scenario_id":"SC-1","requirement_ids":["BR-1"],"test_data_file":"qe_shaped_test_data/TC-1.json"}
            """);
        JsonNode bodyCase = json("""
            {"id":"TC-2","scenario_id":"SC-1","requirement_ids":["BR-1"],"test_data_file":"qe_shaped_test_data/TC-2.json"}
            """);
        Files.writeString(temp.resolve("TC-1.json"), "{\"Financial Transaction Request\":{}}");
        Files.writeString(temp.resolve("TC-2.json"), "{\"Financial Transaction Request\":{\"ResponseCode\":\"0\"}}");
        assertThat(AiArtifactDefectRegister.inspectTestCase(emptyCase, scenario, temp, comparator).toString())
            .contains("EMPTY_PHYSICAL_PAYLOAD");
        assertThat(AiArtifactDefectRegister.inspectTestCase(bodyCase, scenario, temp, comparator)).isEmpty();
        JsonNode missingCase = json("""
            {"id":"TC-3","scenario_id":"SC-1","requirement_ids":["BR-1"],"test_data_file":"qe_shaped_test_data/TC-3.json"}
            """);
        assertThat(AiArtifactDefectRegister.inspectTestCase(missingCase, scenario, temp, comparator).toString())
            .contains("DECLARED_PAYLOAD_MISSING");
    }

    @Test
    void recordsOnlyFlowScenariosWithoutCases() throws Exception {
        List<JsonNode> scenarios = List.of(
            json("{\"id\":\"SC-FLOW\",\"type\":\"flow\",\"name\":\"Lifecycle\"}"),
            json("{\"id\":\"SC-POS\",\"type\":\"positive\",\"name\":\"Single request\"}"));
        var findings = AiArtifactDefectRegister.missingFlowScenarios(scenarios, Map.of("SC-POS", 1));
        assertThat(findings).hasSize(1);
        assertThat(findings.get(0).toString()).contains("FLOW_SCENARIO_WITHOUT_TEST_CASE", "SC-FLOW");
    }

    @Test
    void parsesSingleAndMultipleRequirementLinkForms() throws Exception {
        assertThat(AiArtifactDefectRegister.requirementIds(json("""
            {"requirement_id":"BR-1","requirement_ids":["BR-1","BR-2"]}
            """))).containsExactly("BR-1", "BR-2");
        assertThat(AiArtifactDefectRegister.hasNonEmptyValue(json("{\"root\":{}}"))).isFalse();
    }

    private static JsonNode json(String value) throws Exception {
        return MAPPER.readTree(value);
    }
}