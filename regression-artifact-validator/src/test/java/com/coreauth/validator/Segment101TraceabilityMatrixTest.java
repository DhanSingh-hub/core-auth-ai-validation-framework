package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalRequirement;
import com.coreauth.validator.canonical.CanonicalScenario;
import com.coreauth.validator.canonical.CanonicalTestCase;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.Segment101RuleCatalogLoader;
import com.coreauth.validator.canonical.Segment101RuleCatalogLoader.Catalog;
import com.coreauth.validator.canonical.Segment101TraceabilityMatrix;
import com.coreauth.validator.canonical.Segment101TraceabilityMatrix.MatrixReport;
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
 * Item 4 (Traceability Matrix) tests for {@link Segment101TraceabilityMatrix}.
 *
 * <p>Verifies BR → TS → TC → TD chain traceability against the Segment 101 rule catalog.
 */
class Segment101TraceabilityMatrixTest {

    private static final Path SEG101_RULE_CATALOG =
        Paths.get("docs", "specs", "kb", "segment-101", "coverage", "segment-101-rule-catalog.json");
    private static final Path SEG101_AI_PACKAGE =
        Paths.get("test-output", "test-json", "segment-101-item-01-fleet-baseline-package.json");

    private static SourceAnchor anchor(String rule) {
        SourceAnchor a = new SourceAnchor();
        a.setSpecification("ATL105");
        a.setVersion("2026-3");
        a.setSection("12.2");
        a.setSegment("101");
        a.setRule(rule);
        return a;
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

    private static CanonicalTestCase tc(String id, String tsId, SourceAnchor... anchors) {
        CanonicalTestCase c = new CanonicalTestCase();
        c.setId(id);
        c.setScenarioIds(List.of(tsId));
        c.setSourceAnchors(List.of(anchors));
        return c;
    }

    private static CanonicalTestData td(String id, String tcId, SourceAnchor... anchors) {
        CanonicalTestData d = new CanonicalTestData();
        d.setId(id);
        d.setTestCaseIds(List.of(tcId));
        d.setSourceAnchors(List.of(anchors));
        return d;
    }

    private static CanonicalArtifactPackage fullChainPackage(SourceAnchor rule) {
        CanonicalArtifactPackage p = new CanonicalArtifactPackage();
        p.setBusinessRequirements(List.of(br("BR-1", rule)));
        p.setTestScenarios(List.of(ts("TS-1", "BR-1", rule)));
        p.setTestCases(List.of(tc("TC-1", "TS-1", rule)));
        p.setTestData(List.of(td("TD-1", "TC-1", rule)));
        return p;
    }

    @Nested
    class TraceabilityChecks {

        @Test
        void reportsFullyTracedForCompleteChain() {
            SourceAnchor rule = anchor("segment-type");
            MatrixReport report = new Segment101TraceabilityMatrix(fullChainPackage(rule))
                .generateMatrix(List.of(rule));

            assertThat(report.metrics().fullyTraced()).isEqualTo(1);
            assertThat(report.metrics().completionPercentage()).isEqualTo(100.0);
            assertThat(report.traceLinks()).hasSize(1);
            assertThat(report.traceLinks().get(0).coverageStatus()).isEqualTo("FULLY_TRACED");
        }

        @Test
        void reportsMissingTraceWhenChainAbsent() {
            SourceAnchor rule = anchor("segment-type");
            MatrixReport report = new Segment101TraceabilityMatrix(new CanonicalArtifactPackage())
                .generateMatrix(List.of(rule));

            assertThat(report.metrics().fullyTraced()).isEqualTo(0);
            assertThat(report.metrics().missingBusinessRequirements()).isEqualTo(1);
            assertThat(report.traceLinks().get(0).coverageStatus()).isEqualTo("MISSING_TRACE");
        }

        @Test
        void reportsPartialTraceWhenTestDataAbsent() {
            SourceAnchor rule = anchor("segment-type");
            CanonicalArtifactPackage p = fullChainPackage(rule);
            p.setTestData(null);

            MatrixReport report = new Segment101TraceabilityMatrix(p).generateMatrix(List.of(rule));

            assertThat(report.traceLinks().get(0).coverageStatus()).isEqualTo("PARTIALLY_TRACED");
            assertThat(report.metrics().missingTestData()).isEqualTo(1);
        }

        @Test
        void countsFullyTracedAcrossMultipleRules() {
            SourceAnchor r1 = anchor("segment-type");
            SourceAnchor r2 = anchor("segment-length");
            SourceAnchor r3 = anchor("fleet-request-required");

            CanonicalArtifactPackage p = new CanonicalArtifactPackage();
            p.setBusinessRequirements(List.of(br("BR-1", r1), br("BR-2", r2)));
            p.setTestScenarios(List.of(ts("TS-1", "BR-1", r1), ts("TS-2", "BR-2", r2)));
            p.setTestCases(List.of(tc("TC-1", "TS-1", r1), tc("TC-2", "TS-2", r2)));
            p.setTestData(List.of(td("TD-1", "TC-1", r1), td("TD-2", "TC-2", r2)));

            MatrixReport report = new Segment101TraceabilityMatrix(p).generateMatrix(List.of(r1, r2, r3));
            assertThat(report.metrics().fullyTraced()).isEqualTo(2);
            assertThat(report.metrics().missingBusinessRequirements()).isEqualTo(1);
        }

        @Test
        void classifiesFieldRules() {
            SourceAnchor rule = new SourceAnchor();
            rule.setSpecification("ATL105");
            rule.setVersion("2026-3");
            rule.setSegment("101");
            rule.setElement("85");
            rule.setRule("segment-type");

            MatrixReport report = new Segment101TraceabilityMatrix(fullChainPackage(rule))
                .generateMatrix(List.of(rule));
            assertThat(report.traceLinks().get(0).ruleClass()).isEqualTo("field");
        }

        @Test
        void classifiesCompatibilityRules() {
            SourceAnchor rule = new SourceAnchor();
            rule.setSpecification("ATL105");
            rule.setVersion("2026-3");
            rule.setSegment("101,145");
            rule.setRule("mutual-exclusion-enhanced-fleet");

            MatrixReport report = new Segment101TraceabilityMatrix(fullChainPackage(rule))
                .generateMatrix(List.of(rule));
            assertThat(report.traceLinks().get(0).ruleClass()).isEqualTo("compatibility");
        }

        @Test
        void classifiesFleetTagRules() {
            SourceAnchor rule = anchor("fleet-tag-code-enumeration");
            MatrixReport report = new Segment101TraceabilityMatrix(fullChainPackage(rule))
                .generateMatrix(List.of(rule));
            assertThat(report.traceLinks().get(0).ruleClass()).isEqualTo("fleet-tag");
        }

        @Test
        void classifiesSerializationRules() {
            SourceAnchor rule = anchor("segment-101-field-order");
            MatrixReport report = new Segment101TraceabilityMatrix(fullChainPackage(rule))
                .generateMatrix(List.of(rule));
            assertThat(report.traceLinks().get(0).ruleClass()).isEqualTo("serialization");
        }

        @Test
        void placeholderPackageAgainstCatalogHasNonZeroCoverage() throws IOException {
            Catalog catalog = new Segment101RuleCatalogLoader().load(SEG101_RULE_CATALOG);
            ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            CanonicalArtifactPackage pkg = mapper.readValue(SEG101_AI_PACKAGE.toFile(), CanonicalArtifactPackage.class);

            MatrixReport report = new Segment101TraceabilityMatrix(pkg).generateMatrix(catalog.anchors());

            assertThat(report.metrics().totalRules()).isEqualTo(26);
            assertThat(report.metrics().fullyTraced()).isGreaterThan(0);
            // PROVISIONAL: full recall requires real AI packages (P-09). Placeholder covers major rules only.
            assertThat(report.metrics().completionPercentage()).isGreaterThan(5.0);
        }

        @Test
        void handlesNullCanonicalPackage() {
            SourceAnchor rule = anchor("segment-type");
            MatrixReport report = new Segment101TraceabilityMatrix(null).generateMatrix(List.of(rule));
            assertThat(report.metrics().totalRules()).isEqualTo(1);
            assertThat(report.metrics().fullyTraced()).isEqualTo(0);
        }
    }
}
