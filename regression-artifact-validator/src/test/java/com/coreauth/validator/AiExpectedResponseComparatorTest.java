package com.coreauth.validator;

import com.coreauth.validator.canonical.AiExpectedResponseComparator;
import com.coreauth.validator.canonical.AiExpectedResponseComparator.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AiExpectedResponseComparatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final AiExpectedResponseComparator comparator = new AiExpectedResponseComparator();

    @Test
    void comparesOnlySupportedCanonicalFamiliesToIndependentCodeOracle() throws Exception {
        assertThat(comparator.compare("Financial Transaction Request", MAPPER.readTree("\"2\"")).status())
            .isEqualTo(Status.AGREE);
        assertThat(comparator.compare("Financial Transaction Request", MAPPER.readTree("\"8\"")).status())
            .isEqualTo(Status.DISAGREE);
        assertThat(comparator.compare("Auth Completion (0220)", MAPPER.readTree("\"2\"")).status())
            .isEqualTo(Status.ORACLE_GAP);
        assertThat(comparator.compare("Financial Transaction Request", MAPPER.readTree("\"22\"")).status())
            .isEqualTo(Status.AI_DEFECT);
        assertThat(comparator.compare("Financial Transaction Request", null).status()).isEqualTo(Status.ORACLE_GAP);
    }

    @Test
    void testcaseResponseMustBePresentAndConsistentWithScenario() throws Exception {
        var scenario = MAPPER.readTree("""
            {"target_transaction":"Financial Transaction Request","expected_response_code":"2"}
            """);
        var missing = MAPPER.readTree("""
            {"id":"TC-1"}
            """);
        assertThat(comparator.compareTestCase(missing, scenario).status()).isEqualTo(Status.ORACLE_GAP);
        var matching = MAPPER.readTree("""
            {"expected_response":{"response_code":{"value":"2","tier":"GROUNDED_SECONDARY"}}}
            """);
        assertThat(comparator.compareTestCase(matching, scenario).status()).isEqualTo(Status.AGREE);
        var conflict = MAPPER.readTree("""
            {"expected_response":{"response_code":{"value":"3","tier":"GROUNDED_SECONDARY"}}}
            """);
        var conflictResult = comparator.compareTestCase(conflict, scenario);
        assertThat(conflictResult.status()).isEqualTo(Status.AI_DEFECT);
        assertThat(conflictResult.oracleStatus()).isEqualTo(Status.AGREE);
        assertThat(conflictResult.declarationConflict()).isTrue();
    }
}