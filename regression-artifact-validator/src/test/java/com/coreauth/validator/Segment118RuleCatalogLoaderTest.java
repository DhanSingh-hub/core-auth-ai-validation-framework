package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment118RuleCatalogLoader;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/** Ensures the Item 1 source oracle remains readable and complete. */
class Segment118RuleCatalogLoaderTest {
    @Test
    void loadsAllSegment118RulesWithSourceAnchors() throws Exception {
        Segment118RuleCatalogLoader.Catalog catalog = new Segment118RuleCatalogLoader().load(Path.of(
            "specifications", "ATL105", "docs", "specs", "kb", "segment-118", "coverage", "segment-118-rule-catalog.json"));

        assertThat(catalog.catalogId()).isEqualTo("ATL105-SEG118-RULE-CATALOG-001");
        assertThat(catalog.rules()).hasSize(30);
        assertThat(catalog.anchors()).allSatisfy(anchor -> {
            assertThat(anchor.getSpecification()).isEqualTo("ATL105");
            assertThat(anchor.getSegment()).isNotBlank();
        });
    }
}
