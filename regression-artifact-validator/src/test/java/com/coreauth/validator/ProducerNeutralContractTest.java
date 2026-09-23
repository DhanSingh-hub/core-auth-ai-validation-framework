package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

class ProducerNeutralContractTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path CONTRACT = Path.of("contract");

    @Test
    void vocabularyKeepsProducerIdsSeparateAndDefinesSharedStatuses() throws Exception {
        JsonNode vocabulary = MAPPER.readTree(CONTRACT.resolve("vocabulary.json").toFile());

        assertThat(textSet(vocabulary.path("artifactTypes"))).containsExactlyInAnyOrder("BR", "TS", "TC", "TD");
        assertThat(textSet(vocabulary.path("producers"))).containsExactlyInAnyOrder("AI_SOLUTION", "TEST_SOLUTION");
        assertThat(vocabulary.path("producerLocalIdPatterns").path("AI_SOLUTION").path("BR").asText())
                .startsWith("^BR-AI-");
        assertThat(vocabulary.path("producerLocalIdPatterns").path("TEST_SOLUTION").path("BR").asText())
                .startsWith("^BR-TEST-");
        assertThat(textSet(vocabulary.path("matchStatuses")))
                .containsExactlyInAnyOrder("CONFIRMED", "REVIEW_REQUIRED", "MISSING");
        assertThat(vocabulary.path("policy").path("sharedProducerIdsRequired").asBoolean()).isFalse();
        assertThat(vocabulary.path("policy").path("confirmedRequiresEvidence").asBoolean()).isTrue();
        assertThat(vocabulary.path("policy").path("heuristicMatchesMayAutoConfirm").asBoolean()).isFalse();
    }

    @Test
    void canonicalAnchorRequiresStableSpecificationIdentity() throws Exception {
        JsonNode schema = MAPPER.readTree(CONTRACT.resolve("canonical-anchor.schema.json").toFile());

        assertThat(textSet(schema.path("required")))
                .containsExactlyInAnyOrder("specification", "version", "segment", "rule");
        assertThat(schema.path("properties").has("canonicalRuleId")).isTrue();
        assertThat(schema.path("properties").has("variant")).isTrue();
        assertThat(schema.path("additionalProperties").asBoolean()).isFalse();
    }

    @Test
    void packageSchemaRequiresProducerAnchorsAndAuditableCrosswalkEvidence() throws Exception {
        JsonNode schema = MAPPER.readTree(CONTRACT.resolve("artifact-package.schema.json").toFile());
        JsonNode artifact = schema.path("$defs").path("artifact");
        JsonNode crosswalk = schema.path("$defs").path("crosswalkEntry");

        assertThat(textSet(schema.path("required"))).contains("producer", "packageId", "artifacts");
        assertThat(textSet(artifact.path("required")))
                .containsExactlyInAnyOrder("id", "artifactType", "producer", "sourceAnchors");
        assertThat(textSet(crosswalk.path("required")))
                .contains("testArtifactId", "aiArtifactIds", "matchStatus", "matchMethod", "evidence");
        assertThat(textSet(crosswalk.path("properties").path("matchMethod").path("enum")))
                .containsExactlyInAnyOrder("CANONICAL_ANCHOR", "SEMANTIC_ALIAS", "SME_DECISION");
    }

    @Test
    void atl105AliasesCannotAutomaticallyConfirmMatches() throws Exception {
        JsonNode aliases = MAPPER.readTree(Path.of("specifications", "ATL105", "contract",
                "semantic-aliases.json").toFile());

        assertThat(aliases.path("specificationVersion").asText()).isEqualTo("2026-3");
        assertThat(aliases.path("policy").path("aliasesAreEvidenceCandidatesOnly").asBoolean()).isTrue();
        assertThat(aliases.path("policy").path("confirmedRequiresCanonicalAnchorOrSmeDecision").asBoolean())
                .isTrue();
        assertThat(aliases.path("aliases")).isNotEmpty();
    }

    private static Set<String> textSet(JsonNode array) {
        return StreamSupport.stream(array.spliterator(), false)
                .map(JsonNode::asText)
                .collect(Collectors.toSet());
    }
}
