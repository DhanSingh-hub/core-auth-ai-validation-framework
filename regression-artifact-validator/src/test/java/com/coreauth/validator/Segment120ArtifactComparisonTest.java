package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalRequirement;
import com.coreauth.validator.canonical.Segment120ArtifactComparison;
import com.coreauth.validator.canonical.Segment120ArtifactComparison.ComparisonReport;
import com.coreauth.validator.canonical.Segment120RuleCatalogLoader;
import com.coreauth.validator.canonical.Segment120RuleCatalogLoader.Catalog;
import com.coreauth.validator.canonical.SourceAnchor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 2 (AI Artifact Comparison) tests for {@link Segment120ArtifactComparison}.
 *
 * <p>Compares AI-generated Segment 120 BR/TS/TC/TD anchors against the independently
 * maintained rule catalog produced by Item 1.
 *
 * <p>Note: no real AI-generated Segment 120 package has been supplied yet. Item 8's default
 * placeholder package ({@code test-output/test-json/segment-120-item-01-baseline-package.json})
 * is intentionally empty, so all 8 catalog rules are expected to be MISSING against it -- an
 * authentic coverage-gap finding, not a test-data defect (see
 * {@code docs/specs/kb/segment-120/coverage/README.md}).
 */
class Segment120ArtifactComparisonTest {

    private static final Path SEG120_RULE_CATALOG =
        Paths.get("docs", "specs", "kb", "segment-120", "coverage", "segment-120-rule-catalog.json");
    private static final Path SEG120_AI_PACKAGE =
        Paths.get("test-output", "test-json", "segment-120-item-01-baseline-package.json");

    private static SourceAnchor anchor(String section, String segment, String element, String rule) {
        SourceAnchor a = new SourceAnchor();
        a.setSpecification("ATL105");
        a.setVersion("2026-3");
        a.setSection(section);
        a.setSegment(segment);
        a.setElement(element);
        a.setRule(rule);
        return a;
    }

    private static CanonicalRequirement requirement(String id, List<SourceAnchor> anchors) {
        CanonicalRequirement req = new CanonicalRequirement();
        req.setId(id);
        req.setSourceAnchors(anchors);
        return req;
    }

    private static Catalog loadCatalog() throws IOException {
        return new Segment120RuleCatalogLoader().load(SEG120_RULE_CATALOG);
    }

    private static CanonicalArtifactPackage loadRealAiPackage() throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(SEG120_AI_PACKAGE.toFile(), CanonicalArtifactPackage.class);
    }

    @Nested
    class CatalogLoading {
        @Test
        void catalogHasEightRules() throws IOException {
            assertThat(loadCatalog().rules()).hasSize(8);
        }

        @Test
        void catalogAnchorsMatchRuleCount() throws IOException {
            assertThat(loadCatalog().anchors()).hasSize(8);
        }
    }

    @Nested
    class RealAiPackageComparison {
        @Test
        void realAiPlaceholderPackageHasNoAnchors() throws IOException {
            ComparisonReport report = new Segment120ArtifactComparison().compare(loadRealAiPackage(), loadCatalog().anchors());
            assertThat(report.aiAnchorCount()).isZero();
        }

        @Test
        void realAiPlaceholderPackageIsMissingAllEightRules() throws IOException {
            ComparisonReport report = new Segment120ArtifactComparison().compare(loadRealAiPackage(), loadCatalog().anchors());
            assertThat(report.missing()).hasSize(8);
        }

        @Test
        void realAiPlaceholderPackageDecisionIsRejectedMissingCoverage() throws IOException {
            ComparisonReport report = new Segment120ArtifactComparison().compare(loadRealAiPackage(), loadCatalog().anchors());
            assertThat(report.overallDecision()).isEqualTo("REJECTED_MISSING_COVERAGE");
        }

        @Test
        void realAiPlaceholderPackageHasZeroRecall() throws IOException {
            ComparisonReport report = new Segment120ArtifactComparison().compare(loadRealAiPackage(), loadCatalog().anchors());
            assertThat(report.recall()).isZero();
        }
    }

    @Nested
    class SyntheticComparisonScenarios {
        @Test
        void matchesWhenAnchorsAlign() {
            SourceAnchor a = anchor("12.18", "120", "85", "segment-type");
            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(requirement("BR-1", List.of(a))));
            ComparisonReport report = new Segment120ArtifactComparison().compare(pkg, List.of(a));
            assertThat(report.matched()).hasSize(1);
            assertThat(report.overallDecision()).isEqualTo("APPROVED");
        }

        @Test
        void reportsExtraWhenAiHasUnknownAnchor() {
            SourceAnchor known = anchor("12.18", "120", "85", "segment-type");
            SourceAnchor unknown = anchor("12.18", "120", "999", "unknown-rule");
            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(requirement("BR-1", List.of(known, unknown))));
            ComparisonReport report = new Segment120ArtifactComparison().compare(pkg, List.of(known));
            assertThat(report.extra()).hasSize(1);
            assertThat(report.overallDecision()).isEqualTo("APPROVED_WITH_REVIEW_ITEMS");
        }

        @Test
        void handlesNullAiPackageGracefully() {
            ComparisonReport report = new Segment120ArtifactComparison().compare(null, List.of(anchor("12.18", "120", "85", "segment-type")));
            assertThat(report.aiAnchorCount()).isZero();
            assertThat(report.missing()).hasSize(1);
        }

        @Test
        void emptyIndependentAnchorsYieldsPerfectPrecisionAndRecall() {
            ComparisonReport report = new Segment120ArtifactComparison().compare(new CanonicalArtifactPackage(), List.of());
            assertThat(report.precision()).isEqualTo(1.0);
            assertThat(report.recall()).isEqualTo(1.0);
        }
    }
}
