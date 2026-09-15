package com.coreauth.validator;

import com.coreauth.validator.canonical.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

class Segment100TraceabilityMatrixTest {

    private static SourceAnchor anchor(String spec, String version, String section, String segment, String element, String rule) {
        SourceAnchor a = new SourceAnchor();
        a.setSpecification(spec);
        a.setVersion(version);
        a.setSection(section);
        a.setSegment(segment);
        a.setElement(element);
        a.setRule(rule);
        return a;
    }

    private static CanonicalRequirement requirement(String id, List<SourceAnchor> anchors) {
        CanonicalRequirement br = new CanonicalRequirement();
        br.setId(id);
        br.setTitle("Test BR: " + id);
        br.setSourceAnchors(anchors);
        return br;
    }

    private static CanonicalScenario scenario(String id, List<String> brIds) {
        CanonicalScenario ts = new CanonicalScenario();
        ts.setId(id);
        ts.setRequirementIds(brIds);
        return ts;
    }

    private static CanonicalTestCase testCase(String id, List<String> tsIds) {
        CanonicalTestCase tc = new CanonicalTestCase();
        tc.setId(id);
        tc.setScenarioIds(tsIds);
        tc.setExpectedOutcome("PASS");
        return tc;
    }

    private static CanonicalTestData testData(String id, List<String> tcIds) {
        CanonicalTestData td = new CanonicalTestData();
        td.setId(id);
        td.setTestCaseIds(tcIds);
        td.setExpectedValidation("PASS");
        return td;
    }

    @Nested
    class TraceabilityTests {
        @Test
        void tracesCompleteChainBrTsToTcToTd() {
            SourceAnchor ruleAnchor = anchor("ATL105", "2026-3", "12.1", "100", "86", "sequence-number");
            CanonicalRequirement br = requirement("BR-001", List.of(ruleAnchor));
            CanonicalScenario ts = scenario("TS-001", List.of("BR-001"));
            CanonicalTestCase tc = testCase("TC-001", List.of("TS-001"));
            CanonicalTestData td = testData("TD-001", List.of("TC-001"));

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(br));
            pkg.setTestScenarios(List.of(ts));
            pkg.setTestCases(List.of(tc));
            pkg.setTestData(List.of(td));

            Segment100TraceabilityMatrix matrix = new Segment100TraceabilityMatrix(pkg);
            Segment100TraceabilityMatrix.MatrixReport report =
                matrix.generateMatrix(List.of(ruleAnchor));

            assertThat(report.traceLinks()).hasSize(1);
            Segment100TraceabilityMatrix.TraceLink link = report.traceLinks().get(0);
            assertThat(link.ruleId()).isEqualTo("sequence-number");
            assertThat(link.businessRequirements()).containsExactly("BR-001");
            assertThat(link.testScenarios()).containsExactly("TS-001");
            assertThat(link.testCases()).containsExactly("TC-001");
            assertThat(link.testData()).containsExactly("TD-001");
            assertThat(link.coverageStatus()).isEqualTo("FULLY_TRACED");
        }

        @Test
        void detectsMissingTestData() {
            SourceAnchor ruleAnchor = anchor("ATL105", "2026-3", "12.1", "100", "86", "sequence-number");
            CanonicalRequirement br = requirement("BR-001", List.of(ruleAnchor));
            CanonicalScenario ts = scenario("TS-001", List.of("BR-001"));
            CanonicalTestCase tc = testCase("TC-001", List.of("TS-001"));

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(br));
            pkg.setTestScenarios(List.of(ts));
            pkg.setTestCases(List.of(tc));
            pkg.setTestData(List.of()); // No test data

            Segment100TraceabilityMatrix matrix = new Segment100TraceabilityMatrix(pkg);
            Segment100TraceabilityMatrix.MatrixReport report =
                matrix.generateMatrix(List.of(ruleAnchor));

            assertThat(report.traceLinks()).hasSize(1);
            Segment100TraceabilityMatrix.TraceLink link = report.traceLinks().get(0);
            assertThat(link.testData()).isEmpty();
            assertThat(link.coverageStatus()).isEqualTo("PARTIALLY_TRACED");
            assertThat(report.metrics().missingTestData()).isEqualTo(1);
        }

        @Test
        void detectsMissingTestCases() {
            SourceAnchor ruleAnchor = anchor("ATL105", "2026-3", "12.1", "100", "86", "sequence-number");
            CanonicalRequirement br = requirement("BR-001", List.of(ruleAnchor));
            CanonicalScenario ts = scenario("TS-001", List.of("BR-001"));

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(br));
            pkg.setTestScenarios(List.of(ts));
            pkg.setTestCases(List.of()); // No test cases

            Segment100TraceabilityMatrix matrix = new Segment100TraceabilityMatrix(pkg);
            Segment100TraceabilityMatrix.MatrixReport report =
                matrix.generateMatrix(List.of(ruleAnchor));

            assertThat(report.traceLinks()).hasSize(1);
            Segment100TraceabilityMatrix.TraceLink link = report.traceLinks().get(0);
            assertThat(link.testCases()).isEmpty();
            assertThat(link.coverageStatus()).isEqualTo("PARTIALLY_TRACED");
            assertThat(report.metrics().missingTestCases()).isEqualTo(1);
        }

        @Test
        void detectsMissingBusinessRequirement() {
            SourceAnchor ruleAnchor = anchor("ATL105", "2026-3", "12.1", "100", "86", "untraceable-rule");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of()); // No BR matching the rule

            Segment100TraceabilityMatrix matrix = new Segment100TraceabilityMatrix(pkg);
            Segment100TraceabilityMatrix.MatrixReport report =
                matrix.generateMatrix(List.of(ruleAnchor));

            assertThat(report.traceLinks()).hasSize(1);
            Segment100TraceabilityMatrix.TraceLink link = report.traceLinks().get(0);
            assertThat(link.businessRequirements()).isEmpty();
            assertThat(link.coverageStatus()).isEqualTo("MISSING_TRACE");
            assertThat(report.metrics().missingData()).isEqualTo(1);
        }

        @Test
        void calculatesMetricsAccurately() {
            // Rule 1: Fully traced
            SourceAnchor rule1 = anchor("ATL105", "2026-3", "12.1", "100", "85", "segment-type");
            CanonicalRequirement br1 = requirement("BR-001", List.of(rule1));
            CanonicalScenario ts1 = scenario("TS-001", List.of("BR-001"));
            CanonicalTestCase tc1 = testCase("TC-001", List.of("TS-001"));
            CanonicalTestData td1 = testData("TD-001", List.of("TC-001"));

            // Rule 2: Partially traced (no test data)
            SourceAnchor rule2 = anchor("ATL105", "2026-3", "12.1", "100", "84", "segment-length");
            CanonicalRequirement br2 = requirement("BR-002", List.of(rule2));
            CanonicalScenario ts2 = scenario("TS-002", List.of("BR-002"));
            CanonicalTestCase tc2 = testCase("TC-002", List.of("TS-002"));

            // Rule 3: Missing (no BR)
            SourceAnchor rule3 = anchor("ATL105", "2026-3", "12.1", "100", "83", "missing-rule");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(br1, br2));
            pkg.setTestScenarios(List.of(ts1, ts2));
            pkg.setTestCases(List.of(tc1, tc2));
            pkg.setTestData(List.of(td1));

            Segment100TraceabilityMatrix matrix = new Segment100TraceabilityMatrix(pkg);
            Segment100TraceabilityMatrix.MatrixReport report =
                matrix.generateMatrix(List.of(rule1, rule2, rule3));

            assertThat(report.metrics().totalRules()).isEqualTo(3);
            assertThat(report.metrics().fullyTraced()).isEqualTo(1);
            assertThat(report.metrics().partiallyTraced()).isEqualTo(1);
            assertThat(report.metrics().missingData()).isEqualTo(1);
            assertThat(report.metrics().completionPercentage()).isCloseTo(33.33, offset(0.1));
        }

        @Test
        void supportsMultipleArtifactsPerRule() {
            SourceAnchor ruleAnchor = anchor("ATL105", "2026-3", "12.1", "100", "78", "prompt-code");

            // Multiple BRs for the same rule
            CanonicalRequirement br1 = requirement("BR-PROMPT-001", List.of(ruleAnchor));
            CanonicalRequirement br2 = requirement("BR-PROMPT-002", List.of(ruleAnchor));

            // Multiple TS for those BRs
            CanonicalScenario ts1 = scenario("TS-PROMPT-001", List.of("BR-PROMPT-001"));
            CanonicalScenario ts2 = scenario("TS-PROMPT-002", List.of("BR-PROMPT-002"));

            // Multiple TC for those TS
            CanonicalTestCase tc1 = testCase("TC-PROMPT-001", List.of("TS-PROMPT-001"));
            CanonicalTestCase tc2 = testCase("TC-PROMPT-002", List.of("TS-PROMPT-002"));

            // Multiple TD for those TC
            CanonicalTestData td1 = testData("TD-PROMPT-001", List.of("TC-PROMPT-001"));
            CanonicalTestData td2 = testData("TD-PROMPT-002", List.of("TC-PROMPT-002"));

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(br1, br2));
            pkg.setTestScenarios(List.of(ts1, ts2));
            pkg.setTestCases(List.of(tc1, tc2));
            pkg.setTestData(List.of(td1, td2));

            Segment100TraceabilityMatrix matrix = new Segment100TraceabilityMatrix(pkg);
            Segment100TraceabilityMatrix.MatrixReport report =
                matrix.generateMatrix(List.of(ruleAnchor));

            assertThat(report.traceLinks()).hasSize(1);
            Segment100TraceabilityMatrix.TraceLink link = report.traceLinks().get(0);
            assertThat(link.businessRequirements()).containsExactlyInAnyOrder("BR-PROMPT-001", "BR-PROMPT-002");
            assertThat(link.testScenarios()).containsExactlyInAnyOrder("TS-PROMPT-001", "TS-PROMPT-002");
            assertThat(link.testCases()).containsExactlyInAnyOrder("TC-PROMPT-001", "TC-PROMPT-002");
            assertThat(link.testData()).containsExactlyInAnyOrder("TD-PROMPT-001", "TD-PROMPT-002");
            assertThat(link.coverageStatus()).isEqualTo("FULLY_TRACED");
        }

        @Test
        void classifiesRulesCorrectly() {
            SourceAnchor segment100Rule = anchor("ATL105", "2026-3", "12.1", "100", "85", "segment-type");
            SourceAnchor companionRule = anchor("ATL105", "2026-3", "11.1.1", "101", "4", "fleet-data");
            SourceAnchor serializationRule = anchor("ATL105", "2026-3", "Appendix A", "TCP-IP-HEADER", "msg-length", "message-length");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            Segment100TraceabilityMatrix matrix = new Segment100TraceabilityMatrix(pkg);

            Segment100TraceabilityMatrix.MatrixReport report =
                matrix.generateMatrix(List.of(segment100Rule, companionRule, serializationRule));

            assertThat(report.traceLinks()).hasSize(3);
            assertThat(report.traceLinks().get(0).ruleClass()).isEqualTo("segment-100");
            assertThat(report.traceLinks().get(1).ruleClass()).isEqualTo("companion-segment");
            assertThat(report.traceLinks().get(2).ruleClass()).isEqualTo("serialization");
        }

        @Test
        void handlesNullArtifactLists() {
            SourceAnchor ruleAnchor = anchor("ATL105", "2026-3", "12.1", "100", "86", "sequence-number");
            CanonicalRequirement br = requirement("BR-001", List.of(ruleAnchor));

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(br));
            pkg.setTestScenarios(null); // Null
            pkg.setTestCases(null); // Null
            pkg.setTestData(null); // Null

            Segment100TraceabilityMatrix matrix = new Segment100TraceabilityMatrix(pkg);
            Segment100TraceabilityMatrix.MatrixReport report =
                matrix.generateMatrix(List.of(ruleAnchor));

            assertThat(report.traceLinks()).hasSize(1);
            Segment100TraceabilityMatrix.TraceLink link = report.traceLinks().get(0);
            assertThat(link.businessRequirements()).containsExactly("BR-001");
            assertThat(link.testScenarios()).isEmpty();
            assertThat(link.testCases()).isEmpty();
            assertThat(link.testData()).isEmpty();
            assertThat(link.coverageStatus()).isEqualTo("PARTIALLY_TRACED");
        }

        @Test
        void generatesMatrixReportWithMetrics() {
            SourceAnchor ruleAnchor = anchor("ATL105", "2026-3", "12.1", "100", "86", "sequence-number");
            CanonicalRequirement br = requirement("BR-001", List.of(ruleAnchor));
            CanonicalScenario ts = scenario("TS-001", List.of("BR-001"));
            CanonicalTestCase tc = testCase("TC-001", List.of("TS-001"));
            CanonicalTestData td = testData("TD-001", List.of("TC-001"));

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setBusinessRequirements(List.of(br));
            pkg.setTestScenarios(List.of(ts));
            pkg.setTestCases(List.of(tc));
            pkg.setTestData(List.of(td));

            Segment100TraceabilityMatrix matrix = new Segment100TraceabilityMatrix(pkg);
            Segment100TraceabilityMatrix.MatrixReport report =
                matrix.generateMatrix(List.of(ruleAnchor));

            assertThat(report.catalogId()).isEqualTo("segment-100-traceability-matrix");
            assertThat(report.traceLinks()).hasSize(1);
            assertThat(report.metrics()).isNotNull();
            assertThat(report.metrics().totalRules()).isEqualTo(1);
            assertThat(report.metrics().fullyTraced()).isEqualTo(1);
            assertThat(report.metrics().completionPercentage()).isEqualTo(100.0);
        }
    }
}
