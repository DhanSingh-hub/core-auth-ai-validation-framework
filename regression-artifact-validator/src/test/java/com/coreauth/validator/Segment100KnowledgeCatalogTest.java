package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100KnowledgeCatalogTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void catalogProvidesOneEntryPointForSegment100Knowledge() throws Exception {
        JsonNode catalog = MAPPER.readTree(Path.of("specifications/ATL105/test-output/test-json/knowledge/SEGMENT-100-KNOWLEDGE-CATALOG.json").toFile());

        assertThat(catalog.path("knowledgeModules").size()).isGreaterThanOrEqualTo(9);
        assertThat(catalog.path("knowledgeCategories")).hasSize(10);
        assertThat(catalog.path("coverageSummary").path("fieldCount").asInt()).isEqualTo(17);
        assertThat(catalog.path("coverageSummary").path("appendixCount").asInt()).isEqualTo(22);
        assertThat(catalog.path("coverageSummary").path("financialTransactionTypeCount").asInt()).isEqualTo(13);
        assertThat(catalog.path("coverageSummary").path("financialCardTypeCount").asInt()).isEqualTo(36);
        assertThat(catalog.path("artifactContract").path("sourceAnchorRequired").asBoolean()).isTrue();
        assertThat(catalog.path("coverageSummary").path("aiArtifactCoverage").asText()).isEqualTo("PENDING");
    }
}
