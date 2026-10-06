package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixILogicalCandidateKnowledgeTest {
    @Test
    void draftLogicalCandidatesReferenceIndependentKnowledgeWithoutExecutionPromotion() throws IOException {
        Path root = Path.of("specifications", "ATL105");
        Path knowledge = root.resolve(Path.of("test-output", "test-json", "knowledge"));
        ObjectMapper mapper = new ObjectMapper();
        Set<String> candidateIds = new HashSet<>();
        Set<String> coveredTableIds = new HashSet<>();
        Set<String> expectedTableIds = new HashSet<>();
        int lineCount = Files.readAllLines(root.resolve(Path.of("docs", "specs", "extracted_text.txt"))).size();
        int batches = 0;
        try (var files = Files.list(knowledge)) {
            for (Path path : files.filter(file -> file.getFileName().toString()
                    .matches("appendix-i-candidates-\\d{3}(?:-\\d{3})?\\.json")).toList()) {
                batches++;
                JsonNode batch = mapper.readTree(path.toFile());
                assertThat(batch.path("specification").asText()).isEqualTo("ATL105");
                assertThat(batch.path("specificationVersion").asText()).isEqualTo("2026-3");
                assertThat(batch.path("readiness").asText()).isEqualTo("REVIEW_REQUIRED");
                assertThat(batch.path("payloadScope").asText()).isEqualTo("LOGICAL_FRAGMENT");
                String reference = batch.path("knowledgeReference").asText();
                assertThat(reference).matches("appendix-i-\\d{3}(?:-\\d{3})?\\.json");
                Map<String, Set<String>> rules = new HashMap<>();
                for (JsonNode table : mapper.readTree(knowledge.resolve(reference).toFile()).path("tables")) {
                    Set<String> ids = new HashSet<>();
                    table.path("rules").forEach(rule -> ids.add(rule.path("id").asText()));
                    rules.put(table.path("tableId").asText(), ids);
                }
                expectedTableIds.addAll(rules.keySet());
                assertThat(batch.path("candidates")).isNotEmpty();
                for (JsonNode candidate : batch.path("candidates")) {
                    assertThat(candidateIds.add(candidate.path("candidateId").asText())).isTrue();
                    String tableId = candidate.path("tableId").asText();
                    assertThat(rules).containsKey(tableId);
                    coveredTableIds.add(tableId);
                    assertThat(candidate.path("ruleIds")).isNotEmpty();
                    candidate.path("ruleIds").forEach(id -> assertThat(rules.get(tableId)).contains(id.asText()));
                    assertThat(candidate.path("readiness").asText()).isEqualTo("REVIEW_REQUIRED");
                    assertThat(candidate.path("payloadScope").asText()).isEqualTo("LOGICAL_FRAGMENT");
                    JsonNode scope = candidate.path("clearScope");
                    assertThat(scope.isTextual() || scope.isArray())
                            .as("Scope shape for " + candidate.path("candidateId").asText()).isTrue();
                    if (scope.isTextual()) {
                        assertThat(scope.asText()).isNotBlank();
                    } else {
                        assertThat(scope).isNotEmpty();
                        scope.forEach(claim -> assertThat(claim.asText()).isNotBlank());
                    }
                    assertThat(candidate.path("excludedClaims")).isNotEmpty();
                    assertThat(candidate.path("expectedPredicateOutcomes")).isNotEmpty();
                    assertThat(candidate.path("sourceEvidence")).isNotEmpty();
                    for (JsonNode evidence : candidate.path("sourceEvidence")) {
                        assertThat(evidence.path("section").asText()).isNotBlank();
                        assertThat(evidence.path("startLine").asInt()).isPositive();
                        assertThat(evidence.path("endLine").asInt())
                                .isGreaterThanOrEqualTo(evidence.path("startLine").asInt())
                                .isLessThanOrEqualTo(lineCount);
                    }
                }
            }
        }
        assertThat(batches).isEqualTo(4);
        assertThat(coveredTableIds).containsExactlyInAnyOrderElementsOf(expectedTableIds);
        assertThat(coveredTableIds).hasSize(69);
    }
}
