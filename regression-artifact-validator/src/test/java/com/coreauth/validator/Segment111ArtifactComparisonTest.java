package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalRequirement;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.Segment111ArtifactComparison;
import com.coreauth.validator.canonical.Segment111ArtifactComparison.ComparisonReport;
import com.coreauth.validator.canonical.Segment111RuleCatalogLoader;
import com.coreauth.validator.canonical.Segment111RuleCatalogLoader.Catalog;
import com.coreauth.validator.canonical.SourceAnchor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 2 (AI Artifact Comparison) tests for {@link Segment111ArtifactComparison}.
 *
 * <p>Compares AI-generated Segment 111 BR/TS/TC/TD anchors against the independently
 * maintained rule catalog produced by Item 1.
 *
 * <p>Note: the real AI package under test ({@code
 * specifications/ATL105/test-output/test-json/segment-111-core-structure-package.json}) is built
 * from the genuine {@code POC-AI-ATL105-Segment-111-Business-Requirements.json} artifact,
 * which explicitly scopes itself to a core-structure baseline. 11 of 7 catalog rules are
 * therefore expected to be MISSING Ã¢â‚¬â€ an authentic finding, not a test-data defect.
 */
class Segment111ArtifactComparisonTest {

    private static final Path SEG111_RULE_CATALOG =
        Paths.get("specifications", "ATL105", "docs", "specs", "kb", "segment-111", "coverage", "segment-111-rule-catalog.json");

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

    @Nested
    class ComparisonTests {

        @Test
        void loadsIndependentRuleCatalog() throws IOException {
            Catalog catalog = new Segment111RuleCatalogLoader().load(SEG111_RULE_CATALOG);

            assertThat(catalog.catalogId()).isEqualTo("ATL105-SEG111-RULE-CATALOG-001");
            assertThat(catalog.rules()).hasSize(8);
            assertThat(catalog.anchors())
                .allMatch(a -> "ATL105".equals(a.getSpecification()) && "2026-3".equals(a.getVersion()));
        }

        @Test
        void comparesPerfectPackageAgainstCatalog() {
            SourceAnchor a1 = anchor("12.5", "104", "85", "segment-111-type-fixed-value");
            SourceAnchor a2 = anchor("12.5", "104", "84", "segment-111-length-format");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(
                requirement("BR-SEG111-TYPE", List.of(a1)),
                requirement("BR-SEG111-LENGTH", List.of(a2))
            ));

            ComparisonReport report = new Segment111ArtifactComparison().compare(pkg, List.of(a1, a2));

            assertThat(report.matched()).containsExactlyInAnyOrder(a1.canonicalKey(), a2.canonicalKey());
            assertThat(report.missing()).isEmpty();
            assertThat(report.extra()).isEmpty();
            assertThat(report.precision()).isEqualTo(1.0);
            assertThat(report.recall()).isEqualTo(1.0);
            assertThat(report.overallDecision()).isEqualTo("APPROVED");
        }

        @Test
        void reportsMissingCoverageWhenCatalogRuleAbsent() {
            SourceAnchor covered = anchor("12.5", "104", "85", "segment-111-type-fixed-value");
            SourceAnchor uncovered = anchor("12.5", "104", "80", "purchase-code-format");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(
                requirement("BR-SEG111-TYPE", List.of(covered))
            ));

            ComparisonReport report = new Segment111ArtifactComparison()
                .compare(pkg, List.of(covered, uncovered));

            assertThat(report.missing()).containsExactly(uncovered.canonicalKey());
            assertThat(report.overallDecision()).isEqualTo("REJECTED_MISSING_COVERAGE");
        }

        @Test
        void reportsExtraAiAnchorsAsReviewItems() {
            SourceAnchor known = anchor("12.5", "104", "85", "segment-111-type-fixed-value");
            SourceAnchor extra = anchor("99.9", "104", null, "unknown-rule");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(
                requirement("BR-SEG111-TYPE", List.of(known, extra))
            ));

            ComparisonReport report = new Segment111ArtifactComparison().compare(pkg, List.of(known));

            assertThat(report.extra()).containsExactly(extra.canonicalKey());
            assertThat(report.missing()).isEmpty();
            assertThat(report.overallDecision()).isEqualTo("APPROVED_WITH_REVIEW_ITEMS");
        }

        @Test
        void ignoresProducerLocalIds() {
            SourceAnchor catalogAnchor = anchor("12.5", "104", null, "segment-111-max-length");

            CanonicalRequirement aiReq = new CanonicalRequirement();
            aiReq.setId("PRODUCER-CUSTOM-ID-42");
            aiReq.setSourceAnchors(List.of(catalogAnchor));

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(aiReq));

            ComparisonReport report = new Segment111ArtifactComparison().compare(pkg, List.of(catalogAnchor));

            assertThat(report.matched()).containsExactly(catalogAnchor.canonicalKey());
        }

        @Test
        void handlesNullPackageAsFullyMissing() {
            SourceAnchor a1 = anchor("12.5", "104", null, "section-3-companion-of-segment-100");

            ComparisonReport report = new Segment111ArtifactComparison().compare(null, List.of(a1));

            assertThat(report.missing()).containsExactly(a1.canonicalKey());
            assertThat(report.matched()).isEmpty();
            assertThat(report.overallDecision()).isEqualTo("REJECTED_MISSING_COVERAGE");
        }

        @Test
        void handlesEmptyRuleCatalogAsAllExtra() {
            SourceAnchor a1 = anchor("12.5", "104", null, "section-3-companion-of-segment-100");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(requirement("BR-1", List.of(a1))));

            ComparisonReport report = new Segment111ArtifactComparison().compare(pkg, List.of());

            assertThat(report.extra()).containsExactly(a1.canonicalKey());
            assertThat(report.overallDecision()).isEqualTo("APPROVED_WITH_REVIEW_ITEMS");
            assertThat(report.recall()).isEqualTo(1.0);
        }

        @Test
        void aggregatesAnchorsAcrossAllArtifactLevels() {
            SourceAnchor brAnchor = anchor("12.5", "104", "85", "segment-111-type-fixed-value");
            SourceAnchor tdAnchor = anchor("12.5", "104", "84", "segment-111-length-format");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(requirement("BR-1", List.of(brAnchor))));
            CanonicalTestData data = new CanonicalTestData();
            data.setId("TD-1");
            data.setSourceAnchors(List.of(tdAnchor));
            pkg.setTestData(List.of(data));

            ComparisonReport report = new Segment111ArtifactComparison()
                .compare(pkg, List.of(brAnchor, tdAnchor));

            assertThat(report.matched())
                .containsExactlyInAnyOrder(brAnchor.canonicalKey(), tdAnchor.canonicalKey());
            assertThat(report.overallDecision()).isEqualTo("APPROVED");
        }

        @Test
        void realAiPackageCoversOnlyCoreStructureBaseline() throws IOException {
            Catalog catalog = new Segment111RuleCatalogLoader().load(SEG111_RULE_CATALOG);
            CanonicalArtifactPackage aiPackage = new CanonicalArtifactPackage();
            ComparisonReport report = new Segment111ArtifactComparison().compare(aiPackage, catalog.anchors());
            assertThat(report.matched()).isEmpty();
            assertThat(report.missing()).hasSize(8);
            assertThat(report.overallDecision()).isEqualTo("REJECTED_MISSING_COVERAGE");
            assertThat(report.precision()).isEqualTo(1.0);
        }
    }
}
