package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment111MutationTester;
import com.coreauth.validator.canonical.Segment111MutationTester.MutationCase;
import com.coreauth.validator.canonical.Segment111MutationTester.MutationReport;
import com.coreauth.validator.canonical.Segment111MutationTester.MutationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/** Item 5 tests: exactly the ten standard MUT-001 through MUT-010 cases. */
class Segment111MutationTesterTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static JsonNode baseline() throws Exception {
        return MAPPER.readTree("""
            {
              "request": {
                "dataSection3": {
                  "variableInfoSegment": {
                    "segmentType": "111",
                    "segmentLength": "016",
                    "variableInformationSections": [
                      {"variableInformationIndicator":"001","variableInformationLength":"001","variableInformation":"A"}
                    ]
                  }
                }
              }
            }
            """);
    }

    private static JsonNode legacyBaseline() throws Exception {
        return MAPPER.readTree("""
            {
              "Financial Request": {
                "Variable Information Data Segment": {
                  "SegmentType": "111",
                  "SegmentLength": "016",
                  "VariableInformationIndicator":"001",
                  "VariableInformationLength":"001",
                  "VariableInformation":"A"
                }
              }
            }
            """);
    }

    private static List<MutationCase> cases() {
        return Segment111MutationTester.generateStandardMutations();
    }

    private static MutationCase at(int index) {
        return cases().get(index);
    }

    private static MutationResult run(int index) throws Exception {
        return Segment111MutationTester.testMutation(at(index), baseline());
    }

    @Nested class Definitions {
        @Test void definesExactlyTenCases() { assertThat(cases()).hasSize(10); }
        @Test void idsAreUnique() { assertThat(cases().stream().map(MutationCase::mutationId).distinct()).hasSize(10); }
        @Test void idsCoverExactly001Through010() { assertThat(cases()).extracting(MutationCase::mutationId).containsExactly("MUT-001", "MUT-002", "MUT-003", "MUT-004", "MUT-005", "MUT-006", "MUT-007", "MUT-008", "MUT-009", "MUT-010"); }
        @Test void ruleAnchorsAreSegment111() { assertThat(cases()).allMatch(c -> c.ruleId().matches("SEG111-R-\\d{3}")); }
        @Test void allCasesShouldBeDetected() { assertThat(cases()).allMatch(MutationCase::shouldDetect); }
        @Test void mutationTypesIncludeRequestedClasses() { assertThat(cases()).extracting(MutationCase::mutationType).contains("field-value", "field-format", "field-omission", "boundary", "structural"); }
        @Test void mutationKindsAreSupported() { assertThat(cases()).extracting(MutationCase::kind).contains(MutationCase.Kind.FIELD_SET, MutationCase.Kind.FIELD_SET_NUMBER, MutationCase.Kind.FIELD_REMOVE, MutationCase.Kind.REPLACE_REPETITIONS, MutationCase.Kind.STRUCTURAL_ORDER_VIOLATION); }
        @Test void descriptionsArePresent() { assertThat(cases()).allMatch(c -> !c.description().isBlank() && !c.jsonPath().isBlank()); }
    }

    @Nested class Execution {
        @Test void mut001DetectsWrongType() throws Exception { assertThat(run(0).detected()).isTrue(); }
        @Test void mut002DetectsSegmentLengthWrongJsonType() throws Exception { assertThat(run(1).detected()).isTrue(); }
        @Test void mut003DetectsSegmentLengthWrongDigitCount() throws Exception { assertThat(run(2).detected()).isTrue(); }
        @Test void mut004DetectsMissingIndicator() throws Exception { assertThat(run(3).detected()).isTrue(); }
        @Test void mut005DetectsOmittedLength() throws Exception { assertThat(run(4).detected()).isTrue(); }
        @Test void mut006DetectsLengthValueMismatch() throws Exception { assertThat(run(5).detected()).isTrue(); }
        @Test void mut007DetectsPerRepetitionBound() throws Exception { assertThat(run(6).detected()).isTrue(); }
        @Test void mut008DetectsTotalGreaterThan999() throws Exception { assertThat(run(7).detected()).isTrue(); }
        @Test void mut009DetectsRepeatedGreaterThan991() throws Exception { assertThat(run(8).detected()).isTrue(); }
        @Test void mut010DetectsStructuralOrderViolation() throws Exception { assertThat(run(9).detected()).isTrue(); }
        @Test void allCanonicalMutationsAreDetected() throws Exception { MutationReport r=Segment111MutationTester.runMutationSuite(baseline(), cases()); assertThat(r.metrics().detectedMutations()).isEqualTo(10); }
        @Test void allLegacyMutationsAreDetected() throws Exception { MutationReport r=Segment111MutationTester.runMutationSuite(legacyBaseline(), cases()); assertThat(r.metrics().detectedMutations()).isEqualTo(10); }
        @Test void suiteReturnsOneResultPerCase() throws Exception { MutationReport r=Segment111MutationTester.runMutationSuite(baseline(), cases()); assertThat(r.results()).hasSize(10); }
        @Test void metricsTotalMatchesSuite() throws Exception { MutationReport r=Segment111MutationTester.runMutationSuite(baseline(), cases()); assertThat(r.metrics().totalMutations()).isEqualTo(10); }
        @Test void metricsAreBounded() throws Exception { MutationReport r=Segment111MutationTester.runMutationSuite(baseline(), cases()); assertThat(r.metrics().detectionRate()).isBetween(0.0, 100.0); }
        @Test void reportHasSessionId() throws Exception { MutationReport r=Segment111MutationTester.runMutationSuite(baseline(), cases()); assertThat(r.testSessionId()).startsWith("seg111-mutation-session-"); }
        @Test void resultContainsDetectionReason() throws Exception { assertThat(run(0).detectionReason()).isNotBlank(); }
        @Test void baselineIsNotMutated() throws Exception { JsonNode p=baseline(); String before=p.toString(); Segment111MutationTester.testMutation(at(0),p); assertThat(p.toString()).isEqualTo(before); }
        @Test void emptySuiteHasZeroTotals() throws Exception { MutationReport r=Segment111MutationTester.runMutationSuite(baseline(), List.of()); assertThat(r.metrics().totalMutations()).isZero(); }
        @Test void nullPayloadIsRejected() { org.assertj.core.api.Assertions.assertThatThrownBy(() -> Segment111MutationTester.testMutation(at(0), null)).isInstanceOf(NullPointerException.class); }
    }
}
