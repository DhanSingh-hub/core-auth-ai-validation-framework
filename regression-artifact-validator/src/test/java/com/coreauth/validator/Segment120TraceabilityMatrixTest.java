package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalRequirement;
import com.coreauth.validator.canonical.CanonicalScenario;
import com.coreauth.validator.canonical.CanonicalTestCase;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.Segment120RuleCatalogLoader;
import com.coreauth.validator.canonical.Segment120RuleCatalogLoader.Catalog;
import com.coreauth.validator.canonical.Segment120TraceabilityMatrix;
import com.coreauth.validator.canonical.Segment120TraceabilityMatrix.MatrixReport;
import com.coreauth.validator.canonical.SourceAnchor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 4 (Traceability Matrix) tests for {@link Segment120TraceabilityMatrix}.
 *
 * <p>Verifies BR → TS → TC → TD chain traceability against the Segment 120 rule catalog, and
 * that the catalog's PROVISIONAL/blocked rules are correctly surfaced: exactly 4 of the 8 rules
 * (SEG120-R-002, SEG120-R-004, SEG120-R-007, SEG120-R-008) carry a provisional/resolved tag.
 */
class Segment120TraceabilityMatrixTest {

    private static final Path SEG120_RULE_CATALOG =
        Paths.get("docs", "specs", "kb", "segment-120", "coverage", "segment-120-rule-catalog.json");

    private static Catalog loadCatalog() throws IOException {
        return new Segment120RuleCatalogLoader().load(SEG120_RULE_CATALOG);
    }

    private static CanonicalRequirement br(String id, SourceAnchor... anchors) {
        CanonicalRequirement r = new CanonicalRequirement();
        r.setId(id);
        r.setSourceAnchors(List.of(anchors));
        return r;
    }

    private static CanonicalScenario ts(String id, String brId, SourceAnchor... anchors) {
        CanonicalScenario s = new CanonicalScenario();
        s.setId(id);
        s.setRequirementIds(List.of(brId));
        s.setSourceAnchors(List.of(anchors));
        return s;
    }

    private static CanonicalTestCase tc(String id, String tsId) {
        CanonicalTestCase c = new CanonicalTestCase();
        c.setId(id);
        c.setScenarioIds(List.of(tsId));
        return c;
    }

    private static CanonicalTestData td(String id, String tcId) {
        CanonicalTestData d = new CanonicalTestData();
        d.setId(id);
        d.setTestCaseIds(List.of(tcId));
        return d;
    }

    @Nested
    class CatalogBasics {
        @Test
        void catalogHasEightRules() throws IOException {
            assertThat(loadCatalog().rules()).hasSize(8);
        }

        @Test
        void exactlyFourRulesAreProvisionalOrBlocked() throws IOException {
            long count = loadCatalog().rules().stream().filter(Segment120RuleCatalogLoader.Rule::isProvisionalOrBlocked).count();
            assertThat(count).isEqualTo(4);
        }

        @Test
        void twoProvisionalRulesAreResolvedAndTwoAreOpen() throws IOException {
            List<Segment120RuleCatalogLoader.Rule> rules = loadCatalog().rules();
            assertThat(rules.stream().filter(r -> r.resolvedId() != null).count()).isEqualTo(2);
            assertThat(rules.stream().filter(r -> r.provisionalId() != null).count()).isEqualTo(2);
        }
    }

    @Nested
    class MatrixMetricsAgainstEmptyPackage {
        @Test
        void emptyPackageYieldsAllRulesMissingTrace() throws IOException {
            MatrixReport report = new Segment120TraceabilityMatrix(new CanonicalArtifactPackage())
                .generateMatrixFromRules(loadCatalog().rules());
            assertThat(report.metrics().totalRules()).isEqualTo(8);
            assertThat(report.metrics().fullyTraced()).isZero();
            assertThat(report.metrics().completionPercentage()).isZero();
        }

        @Test
        void reportExposesProvisionalMetricsMatchingCatalog() throws IOException {
            MatrixReport report = new Segment120TraceabilityMatrix(new CanonicalArtifactPackage())
                .generateMatrixFromRules(loadCatalog().rules());
            assertThat(report.metrics().provisionalOrBlockedRules()).isEqualTo(4);
            assertThat(report.metrics().openProvisionalRules()).isEqualTo(2);
            assertThat(report.metrics().resolvedProvisionalRules()).isEqualTo(2);
        }

        @Test
        void traceLinksCarryProvisionalStatusPerRule() throws IOException {
            MatrixReport report = new Segment120TraceabilityMatrix(new CanonicalArtifactPackage())
                .generateMatrixFromRules(loadCatalog().rules());
            assertThat(report.traceLinks()).filteredOn(l -> l.provisionalStatus().startsWith("PROVISIONAL_OPEN")).hasSize(2);
            assertThat(report.traceLinks()).filteredOn(l -> l.provisionalStatus().startsWith("FORMERLY_PROVISIONAL_RESOLVED")).hasSize(2);
            assertThat(report.traceLinks()).filteredOn(l -> l.provisionalStatus().equals("CONFIRMED")).hasSize(4);
        }
    }

    @Nested
    class MatrixMetricsAgainstFullyTracedPackage {
        @Test
        void fullChainYieldsFullyTracedStatus() throws IOException {
            Segment120RuleCatalogLoader.Rule rule = loadCatalog().rules().get(0);
            SourceAnchor anchor = rule.sourceAnchor();

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(br("BR-1", anchor)));
            pkg.setTestScenarios(List.of(ts("TS-1", "BR-1", anchor)));
            pkg.setTestCases(List.of(tc("TC-1", "TS-1")));
            pkg.setTestData(List.of(td("TD-1", "TC-1")));

            MatrixReport report = new Segment120TraceabilityMatrix(pkg).generateMatrixFromRules(loadCatalog().rules());
            assertThat(report.metrics().fullyTraced()).isEqualTo(1);
            assertThat(report.traceLinks()).anyMatch(l -> "FULLY_TRACED".equals(l.coverageStatus()) && l.ruleId().equals(anchor.getRule()));
        }

        @Test
        void completionPercentageReflectsFullyTracedRatio() throws IOException {
            Segment120RuleCatalogLoader.Rule rule = loadCatalog().rules().get(0);
            SourceAnchor anchor = rule.sourceAnchor();

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(br("BR-1", anchor)));
            pkg.setTestScenarios(List.of(ts("TS-1", "BR-1", anchor)));
            pkg.setTestCases(List.of(tc("TC-1", "TS-1")));
            pkg.setTestData(List.of(td("TD-1", "TC-1")));

            MatrixReport report = new Segment120TraceabilityMatrix(pkg).generateMatrixFromRules(loadCatalog().rules());
            assertThat(report.metrics().completionPercentage()).isEqualTo(100.0 / 8.0, org.assertj.core.data.Offset.offset(0.01));
        }
    }

    @Nested
    class BackwardCompatibleOverload {
        @Test
        void generateMatrixFromAnchorsListReportsAllConfirmed() throws IOException {
            List<SourceAnchor> anchors = loadCatalog().anchors();
            MatrixReport report = new Segment120TraceabilityMatrix(new CanonicalArtifactPackage()).generateMatrix(anchors);
            assertThat(report.metrics().totalRules()).isEqualTo(8);
            assertThat(report.metrics().provisionalOrBlockedRules()).isZero();
        }
    }

    @Test
    void catalogIdIsSegment120Specific() throws IOException {
        MatrixReport report = new Segment120TraceabilityMatrix(new CanonicalArtifactPackage())
            .generateMatrixFromRules(loadCatalog().rules());
        assertThat(report.catalogId()).isEqualTo("segment-120-traceability-matrix");
    }
}
