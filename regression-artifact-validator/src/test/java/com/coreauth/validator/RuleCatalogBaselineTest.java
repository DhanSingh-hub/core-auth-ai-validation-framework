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

        List<String> segmentFolders;
        try (var folders = Files.list(knowledgeBase)) {
            segmentFolders = folders.filter(Files::isDirectory)
                    .map(path -> path.getFileName().toString())
                    .filter(name -> name.startsWith("segment-"))
                    .sorted()
                    .toList();
        }
        List<String> catalogFolders = catalogs.stream()
                .map(path -> knowledgeBase.relativize(path).getName(0).toString())
                .sorted()
                .toList();

        // Derived rather than hard-coded so adding a trained segment cannot silently stale this test.
        assertThat(catalogFolders)
                .as("every kb/segment-* folder must contain exactly one rule catalog")
                .containsExactlyElementsOf(segmentFolders);
        assertThat(baseline.inScopeRequirements()).isNotEmpty();
        assertThat(baseline.validate().errors())
                .as("rule catalogs must not contain missing IDs, incomplete anchors, or duplicate denominator anchors")
                .isEmpty();
    }
}