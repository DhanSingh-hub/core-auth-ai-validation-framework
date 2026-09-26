package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment110RuleCatalogLoader;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/** Ensures the Item 1 source oracle remains readable and complete. */
class Segment110RuleCatalogLoaderTest {
    @Test
    void loadsAllSegment110RulesWithSourceAnchors() throws Exception {
        Segment110RuleCatalogLoader.Catalog catalog = new Segment110RuleCatalogLoader().load(Path.of(
            "specifications", "ATL105", "docs", "specs", "kb", "segment-110", "coverage", "segment-110-rule-catalog.json"));

        assertThat(catalog.catalogId()).isEqualTo("ATL105-SEG110-RULE-CATALOG-001");
        assertThat(catalog.rules()).hasSize(20);
        assertThat(catalog.anchors()).allSatisfy(anchor -> {
            assertThat(anchor.getSpecification()).isEqualTo("ATL105");
            assertThat(anchor.getSection()).isNotBlank();
        });
    }
}
