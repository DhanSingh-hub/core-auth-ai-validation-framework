package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalRequirement;
import com.coreauth.validator.canonical.Segment100ArtifactComparison;
import com.coreauth.validator.canonical.SourceAnchor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100ArtifactComparisonTest {

    private static SourceAnchor anchor(String specification, String version, String section, String rule) {
        SourceAnchor anchor = new SourceAnchor();
        anchor.setSpecification(specification);
        anchor.setVersion(version);
        anchor.setSection(section);
        anchor.setRule(rule);
        return anchor;
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
        void comparesPrecisionAndRecall() {
            SourceAnchor a1 = anchor("ATL105", "2026-3", "12.1", "rule-1");
            SourceAnchor a2 = anchor("ATL105", "2026-3", "12.1", "rule-2");
            SourceAnchor a3 = anchor("ATL105", "2026-3", "12.1", "rule-3");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(
                    requirement("BR-001", List.of(a1)),
                    requirement("BR-002", List.of(a2))
            ));

            Segment100ArtifactComparison comparison = new Segment100ArtifactComparison();
            Segment100ArtifactComparison.ComparisonReport report = comparison.compare(pkg, List.of(a1, a2, a3));

            assertThat(report.matched()).containsExactlyInAnyOrder(
                    a1.canonicalKey(), a2.canonicalKey()
            );
            assertThat(report.missing()).containsExactly(a3.canonicalKey());
            assertThat(report.extra()).isEmpty();
            assertThat(report.precision()).isEqualTo(1.0);
            assertThat(report.recall()).isEqualTo(2.0 / 3.0);
        }

        @Test
        void detectsExtraAiArtifacts() {
            SourceAnchor a1 = anchor("ATL105", "2026-3", "12.1", "rule-1");
            SourceAnchor a2 = anchor("ATL105", "2026-3", "12.1", "rule-2");
            SourceAnchor extra = anchor("ATL105", "2026-3", "99.9", "extra-rule");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(
                    requirement("BR-001", List.of(a1, a2, extra))
            ));

            Segment100ArtifactComparison comparison = new Segment100ArtifactComparison();
            Segment100ArtifactComparison.ComparisonReport report = comparison.compare(pkg, List.of(a1, a2));

            assertThat(report.matched()).containsExactlyInAnyOrder(a1.canonicalKey(), a2.canonicalKey());
            assertThat(report.missing()).isEmpty();
            assertThat(report.extra()).containsExactly(extra.canonicalKey());
            assertThat(report.overallDecision()).isEqualTo("APPROVED_WITH_REVIEW_ITEMS");
        }

        @Test
        void rejectsWhenCatalogRulesMissing() {
            SourceAnchor a1 = anchor("ATL105", "2026-3", "12.1", "rule-1");
            SourceAnchor a2 = anchor("ATL105", "2026-3", "12.1", "rule-2");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(
                    requirement("BR-001", List.of(a1))
            ));

            Segment100ArtifactComparison comparison = new Segment100ArtifactComparison();
            Segment100ArtifactComparison.ComparisonReport report = comparison.compare(pkg, List.of(a1, a2));

            assertThat(report.missing()).containsExactly(a2.canonicalKey());
            assertThat(report.extra()).isEmpty();
            assertThat(report.overallDecision()).isEqualTo("REJECTED_MISSING_COVERAGE");
        }

        @Test
        void approvesWhenPerfectMatch() {
            SourceAnchor a1 = anchor("ATL105", "2026-3", "12.1", "rule-1");
            SourceAnchor a2 = anchor("ATL105", "2026-3", "12.1", "rule-2");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(
                    requirement("BR-001", List.of(a1)),
                    requirement("BR-002", List.of(a2))
            ));

            Segment100ArtifactComparison comparison = new Segment100ArtifactComparison();
            Segment100ArtifactComparison.ComparisonReport report = comparison.compare(pkg, List.of(a1, a2));

            assertThat(report.matched()).hasSize(2);
            assertThat(report.missing()).isEmpty();
            assertThat(report.extra()).isEmpty();
            assertThat(report.precision()).isEqualTo(1.0);
            assertThat(report.recall()).isEqualTo(1.0);
            assertThat(report.overallDecision()).isEqualTo("APPROVED");
        }

        @Test
        void ignoresProducerLocalIds() {
            SourceAnchor catalogAnchor = anchor("ATL105", "2026-3", "12.1", "rule-1");
            
            // Create requirement with same canonical anchor but different local ID
            CanonicalRequirement req1 = new CanonicalRequirement();
            req1.setId("LOCAL-ID-FROM-AI");
            req1.setSourceAnchors(List.of(catalogAnchor));

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(req1));

            Segment100ArtifactComparison comparison = new Segment100ArtifactComparison();
            Segment100ArtifactComparison.ComparisonReport report = comparison.compare(pkg, List.of(catalogAnchor));

            assertThat(report.matched()).containsExactly(catalogAnchor.canonicalKey());
            assertThat(report.missing()).isEmpty();
            assertThat(report.extra()).isEmpty();
        }

        @Test
        void handlesPrecisionEdgeCases() {
            SourceAnchor a1 = anchor("ATL105", "2026-3", "12.1", "rule-1");

            CanonicalArtifactPackage emptyPkg = new CanonicalArtifactPackage();

            Segment100ArtifactComparison comparison = new Segment100ArtifactComparison();
            Segment100ArtifactComparison.ComparisonReport report = comparison.compare(emptyPkg, List.of(a1));

            assertThat(report.precision()).isEqualTo(1.0);
            assertThat(report.recall()).isEqualTo(0.0);
        }

        @Test
        void handlesNullPackage() {
            SourceAnchor a1 = anchor("ATL105", "2026-3", "12.1", "rule-1");

            Segment100ArtifactComparison comparison = new Segment100ArtifactComparison();
            Segment100ArtifactComparison.ComparisonReport report = comparison.compare(null, List.of(a1));

            assertThat(report.missing()).containsExactly(a1.canonicalKey());
            assertThat(report.matched()).isEmpty();
        }
    }
}
