package com.coreauth.validator;

import com.coreauth.validator.coverage.IndependentRequirementBaseline;
import com.coreauth.validator.coverage.RuleCatalogBaselineLoader;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RuleCatalogBaselineTest {

    @Test
    void currentRuleCatalogsFormAValidIndependentCoverageDenominator() throws Exception {
        Path knowledgeBase = Path.of("specifications", "ATL105", "docs", "specs", "kb");
        List<Path> catalogs;
        try (var files = Files.walk(knowledgeBase)) {
            catalogs = files.filter(path -> path.getFileName().toString().matches("segment-.*-rule-catalog\\.json"))
                    .sorted()
                    .toList();
        }

        IndependentRequirementBaseline baseline = new RuleCatalogBaselineLoader().load(catalogs);

        assertThat(catalogs).hasSize(17);
        assertThat(baseline.inScopeRequirements()).isNotEmpty();
        assertThat(baseline.validate().errors())
                .as("rule catalogs must not contain missing IDs, incomplete anchors, or duplicate denominator anchors")
                .isEmpty();
    }
}