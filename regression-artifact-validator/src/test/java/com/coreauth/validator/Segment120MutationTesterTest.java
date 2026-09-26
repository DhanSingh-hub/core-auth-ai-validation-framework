package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment120MutationTester;
import com.coreauth.validator.canonical.Segment120MutationTester.MutationCase;
import com.coreauth.validator.canonical.Segment120MutationTester.MutationReport;
import com.coreauth.validator.canonical.Segment120MutationTester.MutationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 5 tests: exactly the ten standard MUT-001 through MUT-010 cases for Segment 120.
 *
 * <p>MUT-010 (SEG120-R-008) is a deliberate {@code shouldDetect=false} case: it verifies the
 * validator does NOT reject Blackhawk '\' delimited Print Data (PROVISIONAL P-03, info severity).
 * "Detected" for that case means the validator correctly passed the payload through unchanged.
 */
class Segment120MutationTesterTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static JsonNode baseline() throws Exception {
        return MAPPER.readTree("""
            {
              "messageType": "FINANCIAL_TRANSACTION_RESPONSE",
              "response": {
                "dataSection3": {
                  "printData2Segment": {
                    "segmentType": "120",
                    "segmentLength": "0041",
                    "printData": "THANK YOU FOR SHOPPING WITH US TODAY"
                  }
                }
              }
            }
            """);
    }

    private static List<MutationCase> cases() {
        return Segment120MutationTester.generateStandardMutations();
    }

    private static MutationCase at(int index) {
        return cases().get(index);
    }

    private static MutationResult run(int index) throws Exception {
        return Segment120MutationTester.testMutation(at(index), baseline());
    }

    @Nested class Definitions {
        @Test void definesExactlyTenCases() { assertThat(cases()).hasSize(10); }
        @Test void idsAreUnique() { assertThat(cases().stream().map(MutationCase::mutationId).distinct()).hasSize(10); }
        @Test void idsCoverExactly001Through010() { assertThat(cases()).extracting(MutationCase::mutationId).containsExactly("MUT-001", "MUT-002", "MUT-003", "MUT-004", "MUT-005", "MUT-006", "MUT-007", "MUT-008", "MUT-009", "MUT-010"); }
        @Test void ruleAnchorsAreSegment120() { assertThat(cases()).allMatch(c -> c.ruleId().matches("SEG120-R-\\d{3}")); }
        @Test void nineOfTenCasesShouldDetectDefects() { assertThat(cases()).filteredOn(MutationCase::shouldDetect).hasSize(9); }
        @Test void mut010IsTheOnlyPassThroughCase() { assertThat(cases()).filteredOn(c -> !c.shouldDetect())
            .extracting(MutationCase::mutationId).containsExactly("MUT-010"); }
        @Test void rulesCoveredMatchesAllEightCatalogRules() {
            assertThat(cases().stream().map(MutationCase::ruleId).distinct().toList())
                .containsExactlyInAnyOrder("SEG120-R-001", "SEG120-R-002", "SEG120-R-003", "SEG120-R-004", "SEG120-R-005", "SEG120-R-006", "SEG120-R-007", "SEG120-R-008");
        }
        @Test void reorderMutationExistsForRule007SinceP01Resolved() {
            assertThat(cases()).filteredOn(c -> "SEG120-R-007".equals(c.ruleId()))
                .extracting(MutationCase::kind).containsExactly(MutationCase.Kind.REORDER_SEGMENT_120);
        }
        @Test void mutationTypesIncludeRequestedClasses() { assertThat(cases()).extracting(MutationCase::mutationType).contains("field-value", "field-format", "field-omission", "boundary", "separator-omission", "structural", "content"); }
        @Test void mutationKindsAreSupported() { assertThat(cases()).extracting(MutationCase::kind).contains(
            MutationCase.Kind.FIELD_SET, MutationCase.Kind.FIELD_SET_NUMBER, MutationCase.Kind.FIELD_REMOVE,
            MutationCase.Kind.OVERSIZE_PRINT_DATA, MutationCase.Kind.WIRE_FORMAT_MISSING_SEPARATOR,
            MutationCase.Kind.WIRE_FORMAT_TRAILING_SEPARATOR, MutationCase.Kind.MOVE_TO_REQUEST_CONTEXT,
            MutationCase.Kind.INSERT_BLACKHAWK_DELIMITER, MutationCase.Kind.REORDER_SEGMENT_120); }
        @Test void descriptionsArePresent() { assertThat(cases()).allMatch(c -> !c.description().isBlank() && !c.jsonPath().isBlank()); }
    }

    @Nested class Execution {
        @Test void mut001DetectsWrongType() throws Exception { assertThat(run(0).detected()).isTrue(); }
        @Test void mut002DetectsSegmentLengthWrongJsonType() throws Exception { assertThat(run(1).detected()).isTrue(); }
        @Test void mut003DetectsSegmentLengthWrongDigitCount() throws Exception { assertThat(run(2).detected()).isTrue(); }
        @Test void mut004DetectsMissingPrintData() throws Exception { assertThat(run(3).detected()).isTrue(); }
        @Test void mut005DetectsPrintDataOversize() throws Exception { assertThat(run(4).detected()).isTrue(); }
        @Test void mut006DetectsMissingSeparator() throws Exception { assertThat(run(5).detected()).isTrue(); }
        @Test void mut007DetectsTrailingSeparator() throws Exception { assertThat(run(6).detected()).isTrue(); }
        @Test void mut008DetectsRequestContextViolation() throws Exception { assertThat(run(7).detected()).isTrue(); }
        @Test void mut009DetectsReorderedSegment120() throws Exception { assertThat(run(8).detected()).isTrue(); }
        @Test void mut010PassesThroughBlackhawkContentWithoutRejection() throws Exception { assertThat(run(9).detected()).isTrue(); }
        @Test void allCanonicalMutationsBehaveAsExpected() throws Exception { MutationReport r=Segment120MutationTester.runMutationSuite(baseline(), cases()); assertThat(r.metrics().detectedMutations()).isEqualTo(10); }
        @Test void suiteReturnsOneResultPerCase() throws Exception { MutationReport r=Segment120MutationTester.runMutationSuite(baseline(), cases()); assertThat(r.results()).hasSize(10); }
        @Test void metricsTotalMatchesSuite() throws Exception { MutationReport r=Segment120MutationTester.runMutationSuite(baseline(), cases()); assertThat(r.metrics().totalMutations()).isEqualTo(10); }
        @Test void metricsAreBounded() throws Exception { MutationReport r=Segment120MutationTester.runMutationSuite(baseline(), cases()); assertThat(r.metrics().detectionRate()).isBetween(0.0, 100.0); }
        @Test void reportHasSessionId() throws Exception { MutationReport r=Segment120MutationTester.runMutationSuite(baseline(), cases()); assertThat(r.testSessionId()).startsWith("seg120-mutation-session-"); }
        @Test void resultContainsDetectionReason() throws Exception { assertThat(run(0).detectionReason()).isNotBlank(); }
        @Test void baselineIsNotMutated() throws Exception { JsonNode p=baseline(); String before=p.toString(); Segment120MutationTester.testMutation(at(0),p); assertThat(p.toString()).isEqualTo(before); }
        @Test void emptySuiteHasZeroTotals() throws Exception { MutationReport r=Segment120MutationTester.runMutationSuite(baseline(), List.of()); assertThat(r.metrics().totalMutations()).isZero(); }
        @Test void nullPayloadIsRejected() { org.assertj.core.api.Assertions.assertThatThrownBy(() -> Segment120MutationTester.testMutation(at(0), null)).isInstanceOf(NullPointerException.class); }
        @Test void allMutationIdsHelperReturnsTen() { assertThat(Segment120MutationTester.allMutationIds()).hasSize(10); }
        @Test void mutationsForRuleFiltersCorrectly() { assertThat(Segment120MutationTester.mutationsForRule("SEG120-R-002")).hasSize(2); }
        @Test void mutationsForRuleReturnsEmptyForUnknownRule() { assertThat(Segment120MutationTester.mutationsForRule("SEG120-R-999")).isEmpty(); }
    }
}
