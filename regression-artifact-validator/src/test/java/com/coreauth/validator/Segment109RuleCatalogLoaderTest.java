package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment109RuleCatalogLoader;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/** Ensures the Item 1 source oracle remains readable and complete. */
class Segment109RuleCatalogLoaderTest {
    @Test
    void loadsAllSegment109RulesWithSourceAnchors() throws Exception {
        Segment109RuleCatalogLoader.Catalog catalog = new Segment109RuleCatalogLoader().load(Path.of(
            "specifications", "ATL105", "docs", "specs", "kb", "segment-109", "coverage", "segment-109-rule-catalog.json"));

        assertThat(catalog.catalogId()).isEqualTo("ATL105-SEG109-RULE-CATALOG-001");
        assertThat(catalog.rules()).hasSize(22);
        assertThat(catalog.anchors()).allSatisfy(anchor -> {
            assertThat(anchor.getSpecification()).isEqualTo("ATL105");
            assertThat(anchor.getSection()).isNotBlank();
        });
    }
}
