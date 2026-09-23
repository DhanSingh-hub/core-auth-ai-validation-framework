package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalRequirement;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.Segment101ArtifactComparison;
import com.coreauth.validator.canonical.Segment101ArtifactComparison.ComparisonReport;
import com.coreauth.validator.canonical.Segment101RuleCatalogLoader;
import com.coreauth.validator.canonical.Segment101RuleCatalogLoader.Catalog;
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
 * Item 2 (AI Artifact Comparison) tests for {@link Segment101ArtifactComparison}.
 *
 * <p>Compares AI-generated Segment 101 BR/TS/TC/TD anchors against the independently
 * maintained rule catalog produced by Item 1. See methodology's Item 2 checklist for the
 * canonical model contract (manifest, BR, TS, TC, TD with source anchors).
 *
 * <p>Note: the AI package under test is a synthesized PROVISIONAL placeholder because no
 * real Segment 101 AI output is available yet (P-09). Once real AI output arrives, replace
 * {@code specifications/ATL105/test-output/test-json/segment-101-item-01-fleet-baseline-package.json}; the tests
 * remain valid.
 */
class Segment101ArtifactComparisonTest {

    private static final Path SEG101_RULE_CATALOG =
        Paths.get("specifications", "ATL105", "docs", "specs", "kb", "segment-101", "coverage", "segment-101-rule-catalog.json");
    private static final Path SEG101_AI_PACKAGE =
        Paths.get("specifications", "ATL105", "test-output", "test-json", "segment-101-item-01-fleet-baseline-package.json");

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
            Catalog catalog = new Segment101RuleCatalogLoader().load(SEG101_RULE_CATALOG);

            assertThat(catalog.catalogId()).isEqualTo("ATL105-SEG101-RULE-CATALOG-001");
            assertThat(catalog.rules()).hasSize(26);
            assertThat(catalog.anchors())
                .allMatch(a -> "ATL105".equals(a.getSpecification()) && "2026-3".equals(a.getVersion()));
        }

        @Test
        void comparesPerfectPackageAgainstCatalog() {
            SourceAnchor a1 = anchor("12.2", "101", "85", "segment-type");
            SourceAnchor a2 = anchor("12.2", "101", "84", "segment-length");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(
                requirement("BR-SEG101-CORE-004", List.of(a1)),
                requirement("BR-SEG101-CORE-005", List.of(a2))
            ));

            ComparisonReport report = new Segment101ArtifactComparison().compare(pkg, List.of(a1, a2));

            assertThat(report.matched()).containsExactlyInAnyOrder(a1.canonicalKey(), a2.canonicalKey());
            assertThat(report.missing()).isEmpty();
            assertThat(report.extra()).isEmpty();
            assertThat(report.precision()).isEqualTo(1.0);
            assertThat(report.recall()).isEqualTo(1.0);
            assertThat(report.overallDecision()).isEqualTo("APPROVED");
        }

        @Test
        void reportsMissingCoverageWhenCatalogRuleAbsent() {
            SourceAnchor covered = anchor("12.2", "101", "85", "segment-type");
            SourceAnchor uncovered = anchor("12.2", "101", "84", "segment-length");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(
                requirement("BR-SEG101-CORE-004", List.of(covered))
            ));

            ComparisonReport report = new Segment101ArtifactComparison()
                .compare(pkg, List.of(covered, uncovered));

            assertThat(report.missing()).containsExactly(uncovered.canonicalKey());
            assertThat(report.overallDecision()).isEqualTo("REJECTED_MISSING_COVERAGE");
        }

        @Test
        void reportsExtraAiAnchorsAsReviewItems() {
            SourceAnchor known = anchor("12.2", "101", "85", "segment-type");
            SourceAnchor extra = anchor("99.9", "101", null, "unknown-rule");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(
                requirement("BR-SEG101-CORE-004", List.of(known, extra))
            ));

            ComparisonReport report = new Segment101ArtifactComparison().compare(pkg, List.of(known));

            assertThat(report.extra()).containsExactly(extra.canonicalKey());
            assertThat(report.missing()).isEmpty();
            assertThat(report.overallDecision()).isEqualTo("APPROVED_WITH_REVIEW_ITEMS");
        }

        @Test
        void ignoresProducerLocalIds() {
            SourceAnchor catalogAnchor = anchor("12.2", "101", null, "fleet-request-required");

            CanonicalRequirement aiReq = new CanonicalRequirement();
            aiReq.setId("PRODUCER-CUSTOM-ID-42");
            aiReq.setSourceAnchors(List.of(catalogAnchor));

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(aiReq));

            ComparisonReport report = new Segment101ArtifactComparison().compare(pkg, List.of(catalogAnchor));

            assertThat(report.matched()).containsExactly(catalogAnchor.canonicalKey());
        }

        @Test
        void handlesNullPackageAsFullyMissing() {
            SourceAnchor a1 = anchor("12.2", "101", null, "fleet-request-required");

            ComparisonReport report = new Segment101ArtifactComparison().compare(null, List.of(a1));

            assertThat(report.missing()).containsExactly(a1.canonicalKey());
            assertThat(report.matched()).isEmpty();
            assertThat(report.overallDecision()).isEqualTo("REJECTED_MISSING_COVERAGE");
        }

        @Test
        void handlesEmptyRuleCatalogAsAllExtra() {
            SourceAnchor a1 = anchor("12.2", "101", null, "fleet-request-required");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(requirement("BR-1", List.of(a1))));

            ComparisonReport report = new Segment101ArtifactComparison().compare(pkg, List.of());

            assertThat(report.extra()).containsExactly(a1.canonicalKey());
            assertThat(report.overallDecision()).isEqualTo("APPROVED_WITH_REVIEW_ITEMS");
            assertThat(report.recall()).isEqualTo(1.0);
        }

        @Test
        void aggregatesAnchorsAcrossAllArtifactLevels() {
            SourceAnchor brAnchor = anchor("12.2", "101", "85", "segment-type");
            SourceAnchor tdAnchor = anchor("12.2", "101", "84", "segment-length");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(requirement("BR-1", List.of(brAnchor))));
            CanonicalTestData data = new CanonicalTestData();
            data.setId("TD-1");
            data.setSourceAnchors(List.of(tdAnchor));
            pkg.setTestData(List.of(data));

            ComparisonReport report = new Segment101ArtifactComparison()
                .compare(pkg, List.of(brAnchor, tdAnchor));

            assertThat(report.matched())
                .containsExactlyInAnyOrder(brAnchor.canonicalKey(), tdAnchor.canonicalKey());
            assertThat(report.overallDecision()).isEqualTo("APPROVED");
        }

        @Test
        void placeholderPackageOnDiskCoversMajorCatalogRules() throws IOException {
            Catalog catalog = new Segment101RuleCatalogLoader().load(SEG101_RULE_CATALOG);
            ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            CanonicalArtifactPackage aiPackage =
                mapper.readValue(SEG101_AI_PACKAGE.toFile(), CanonicalArtifactPackage.class);

            ComparisonReport report = new Segment101ArtifactComparison()
                .compare(aiPackage, catalog.anchors());

            // Recall is intentionally below 100%: this is a Wave-1 placeholder covering major
            // rules only. Full recall requires the SME to deliver real AI packages (P-09).
            assertThat(report.matched()).isNotEmpty();
            assertThat(report.recall()).isGreaterThan(0.35);
            assertThat(report.precision()).isEqualTo(1.0);
        }
    }
}
