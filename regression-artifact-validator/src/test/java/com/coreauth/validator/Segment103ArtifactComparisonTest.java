package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalRequirement;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.Segment103ArtifactComparison;
import com.coreauth.validator.canonical.Segment103ArtifactComparison.ComparisonReport;
import com.coreauth.validator.canonical.Segment103RuleCatalogLoader;
import com.coreauth.validator.canonical.Segment103RuleCatalogLoader.Catalog;
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
 * Item 2 (AI Artifact Comparison) tests for {@link Segment103ArtifactComparison}.
 *
 * <p>Note: the AI package under test is a synthesized PROVISIONAL placeholder because no
 * real Segment 103 AI output is available yet (P-07). Once real AI output arrives, replace
 * {@code test-output/test-json/segment-103-item-01-ebt-baseline-package.json}; the tests
 * remain valid.
 */
class Segment103ArtifactComparisonTest {

    private static final Path SEG103_RULE_CATALOG =
        Paths.get("docs", "specs", "kb", "segment-103", "coverage", "segment-103-rule-catalog.json");
    private static final Path SEG103_AI_PACKAGE =
        Paths.get("test-output", "test-json", "segment-103-item-01-ebt-baseline-package.json");

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
            Catalog catalog = new Segment103RuleCatalogLoader().load(SEG103_RULE_CATALOG);

            assertThat(catalog.catalogId()).isEqualTo("ATL105-SEG103-RULE-CATALOG-001");
            assertThat(catalog.rules()).hasSize(21);
            assertThat(catalog.anchors())
                .allMatch(a -> "ATL105".equals(a.getSpecification()) && "2026-3".equals(a.getVersion()));
        }

        @Test
        void comparesPerfectPackageAgainstCatalog() {
            SourceAnchor a1 = anchor("12.4", "103", "85", "segment-type");
            SourceAnchor a2 = anchor("12.4", "103", "84", "segment-length");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(
                requirement("BR-SEG103-CORE-003", List.of(a1)),
                requirement("BR-SEG103-CORE-004", List.of(a2))
            ));

            ComparisonReport report = new Segment103ArtifactComparison().compare(pkg, List.of(a1, a2));

            assertThat(report.matched()).containsExactlyInAnyOrder(a1.canonicalKey(), a2.canonicalKey());
            assertThat(report.missing()).isEmpty();
            assertThat(report.extra()).isEmpty();
            assertThat(report.precision()).isEqualTo(1.0);
            assertThat(report.recall()).isEqualTo(1.0);
            assertThat(report.overallDecision()).isEqualTo("APPROVED");
        }

        @Test
        void reportsMissingCoverageWhenCatalogRuleAbsent() {
            SourceAnchor covered = anchor("12.4", "103", "85", "segment-type");
            SourceAnchor uncovered = anchor("12.4", "103", "84", "segment-length");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(
                requirement("BR-SEG103-CORE-003", List.of(covered))
            ));

            ComparisonReport report = new Segment103ArtifactComparison()
                .compare(pkg, List.of(covered, uncovered));

            assertThat(report.missing()).containsExactly(uncovered.canonicalKey());
            assertThat(report.overallDecision()).isEqualTo("REJECTED_MISSING_COVERAGE");
        }

        @Test
        void reportsExtraAiAnchorsAsReviewItems() {
            SourceAnchor known = anchor("12.4", "103", "85", "segment-type");
            SourceAnchor extra = anchor("99.9", "103", null, "unknown-rule");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(
                requirement("BR-SEG103-CORE-003", List.of(known, extra))
            ));

            ComparisonReport report = new Segment103ArtifactComparison().compare(pkg, List.of(known));

            assertThat(report.extra()).containsExactly(extra.canonicalKey());
            assertThat(report.missing()).isEmpty();
            assertThat(report.overallDecision()).isEqualTo("APPROVED_WITH_REVIEW_ITEMS");
        }

        @Test
        void ignoresProducerLocalIds() {
            SourceAnchor catalogAnchor = anchor("12.4", "103", null, "ebt-request-conditional");

            CanonicalRequirement aiReq = new CanonicalRequirement();
            aiReq.setId("PRODUCER-CUSTOM-ID-42");
            aiReq.setSourceAnchors(List.of(catalogAnchor));

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(aiReq));

            ComparisonReport report = new Segment103ArtifactComparison().compare(pkg, List.of(catalogAnchor));

            assertThat(report.matched()).containsExactly(catalogAnchor.canonicalKey());
        }

        @Test
        void handlesNullPackageAsFullyMissing() {
            SourceAnchor a1 = anchor("12.4", "103", null, "ebt-request-conditional");

            ComparisonReport report = new Segment103ArtifactComparison().compare(null, List.of(a1));

            assertThat(report.missing()).containsExactly(a1.canonicalKey());
            assertThat(report.matched()).isEmpty();
            assertThat(report.overallDecision()).isEqualTo("REJECTED_MISSING_COVERAGE");
        }

        @Test
        void handlesEmptyRuleCatalogAsAllExtra() {
            SourceAnchor a1 = anchor("12.4", "103", null, "ebt-request-conditional");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(requirement("BR-1", List.of(a1))));

            ComparisonReport report = new Segment103ArtifactComparison().compare(pkg, List.of());

            assertThat(report.extra()).containsExactly(a1.canonicalKey());
            assertThat(report.overallDecision()).isEqualTo("APPROVED_WITH_REVIEW_ITEMS");
            assertThat(report.recall()).isEqualTo(1.0);
        }

        @Test
        void aggregatesAnchorsAcrossAllArtifactLevels() {
            SourceAnchor brAnchor = anchor("12.4", "103", "85", "segment-type");
            SourceAnchor tdAnchor = anchor("12.4", "103", "84", "segment-length");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(requirement("BR-1", List.of(brAnchor))));
            CanonicalTestData data = new CanonicalTestData();
            data.setId("TD-1");
            data.setSourceAnchors(List.of(tdAnchor));
            pkg.setTestData(List.of(data));

            ComparisonReport report = new Segment103ArtifactComparison()
                .compare(pkg, List.of(brAnchor, tdAnchor));

            assertThat(report.matched())
                .containsExactlyInAnyOrder(brAnchor.canonicalKey(), tdAnchor.canonicalKey());
            assertThat(report.overallDecision()).isEqualTo("APPROVED");
        }

        @Test
        void placeholderPackageOnDiskCoversMajorCatalogRules() throws IOException {
            Catalog catalog = new Segment103RuleCatalogLoader().load(SEG103_RULE_CATALOG);
            ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            CanonicalArtifactPackage aiPackage =
                mapper.readValue(SEG103_AI_PACKAGE.toFile(), CanonicalArtifactPackage.class);

            ComparisonReport report = new Segment103ArtifactComparison()
                .compare(aiPackage, catalog.anchors());

            // Recall is intentionally below 100%: this is a Wave-1 placeholder covering major
            // catalog rules only, pending real AI-generated Segment 103 artifacts (P-07).
            assertThat(report.recall()).isGreaterThan(0.3);
            assertThat(report.matched()).isNotEmpty();
        }
    }
}
