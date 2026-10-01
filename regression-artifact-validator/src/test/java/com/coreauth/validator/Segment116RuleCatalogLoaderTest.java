package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment116RuleCatalogLoader;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/** Ensures the Item 1 source oracle remains readable and complete. */
class Segment116RuleCatalogLoaderTest {
    @Test
    void loadsAllSegment116RulesWithSourceAnchors() throws Exception {
        Segment116RuleCatalogLoader.Catalog catalog = new Segment116RuleCatalogLoader().load(Path.of(
            "specifications", "ATL105", "docs", "specs", "kb", "segment-116", "coverage", "segment-116-rule-catalog.json"));

        assertThat(catalog.catalogId()).isEqualTo("ATL105-SEG116-RULE-CATALOG-001");
        assertThat(catalog.rules()).hasSize(9);
        assertThat(catalog.anchors()).allSatisfy(anchor -> {
            assertThat(anchor.getSpecification()).isEqualTo("ATL105");
            assertThat(anchor.getSegment()).isNotBlank();
        });
    }
}
