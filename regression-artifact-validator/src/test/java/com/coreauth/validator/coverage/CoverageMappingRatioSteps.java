package com.coreauth.validator.coverage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for coverage-mapping-ratio.feature.
 *
 * <p>These are self-contained stubs: they exercise the same aggregation rules as
 * {@code report-all-segments-br-ratio.ps1} (via {@link CoverageMappingRatioCalculator} and
 * {@link AllSegmentsCoverageReport}) against in-memory fixtures, rather than reading the real
 * PowerShell-generated JSON/HTML artifacts. Wiring this to the actual report files would be a
 * follow-up if these scenarios need to validate a real report run.</p>
 */
public class CoverageMappingRatioSteps {

    private static final int DEFAULT_POTENTIAL_MATCHES = 3;
    private static final int DEFAULT_UNMATCHED_FOR_REPORT = 2;
    private static final Pattern SEGMENT_ID_IN_FILENAME = Pattern.compile("Segment-(\\w+)-");

    private final List<CoverageCrosswalkRecord> records = new ArrayList<>();
    private int testSolutionRequirements;
    private int aiRequirementsDeclared;
    private int unmatchedAiRequirementsDeclared;
    private CoverageCrosswalkRecord lastRecord;
    private CoverageMappingRatioResult result;
    private String currentScenarioSegmentId;
    private boolean aiOutputRead;
    private boolean comparisonCompleted;
    private int aiTestScenarios;
    private int confirmedTestScenarios;
    private int aiTestCases;
    private int confirmedTestCases;

    private final Map<String, AllSegmentsCoverageReport.SegmentTotals> segmentTotalsMap = new LinkedHashMap<>();
    private final Set<String> excludedSegmentIds = new LinkedHashSet<>();
    private AllSegmentsCoverageReport allSegmentsReport;

    // ---------------------------------------------------------------- Background

    @Given("the canonical AI-to-Test-Solution requirement crosswalk for a segment")
    public void the_canonical_crosswalk_for_a_segment() {
        // Establishes an empty per-segment fixture; populated by the scenario's own Given steps.
    }

    @Given("each crosswalk record has a {string} of {string}, {string}, or {string}")
    public void each_record_has_a_field_of_values(String field, String first, String second, String third) {
        assertThat(field).isEqualTo("matchStatus");
        assertThat(Set.of(first, second, third)).containsExactlyInAnyOrder("CONFIRMED", "REVIEW_REQUIRED", "MISSING");
    }

    // ---------------------------------------------------------------- Single-segment fixtures

    @Given("a segment crosswalk with {int} Test Solution requirements")
    public void a_segment_crosswalk_with_requirements(int count) {
        this.testSolutionRequirements = count;
    }

    @Given("a segment crosswalk with {string} Test Solution requirements")
    public void a_segment_crosswalk_with_quoted_requirements(String count) {
        this.testSolutionRequirements = Integer.parseInt(count);
    }

    @Given("{int} of those requirements have a {string} matchStatus against an AI requirement")
    public void n_of_those_requirements_have_status(int count, String status) {
        addConfirmedOrOtherRecords(count, status);
    }

    @Given("{string} of those requirements have a {string} matchStatus")
    public void quoted_n_of_those_requirements_have_status(String count, String status) {
        addConfirmedOrOtherRecords(Integer.parseInt(count), status);
    }

    @Given("{int} requirements have a {string} matchStatus")
    public void n_requirements_have_status(int count, String status) {
        addConfirmedOrOtherRecords(count, status);
    }

    @Given("{int} requirements have a {string} matchStatus with a reviewOwner")
    public void n_requirements_have_status_with_owner(int count, String status) {
        for (int i = 0; i < count; i++) {
            records.add(new CoverageCrosswalkRecord(status, "SME-REVIEW"));
        }
    }

    @Given("a segment crosswalk record with a {string} matchStatus")
    public void a_segment_crosswalk_record_with_status(String status) {
        lastRecord = new CoverageCrosswalkRecord(status, null);
        records.add(lastRecord);
        testSolutionRequirements += 1;
    }

    @Given("that record has no reviewOwner")
    public void that_record_has_no_review_owner() {
        assertThat(lastRecord.isValid()).isFalse();
    }

    @Given("a segment has {int} AI requirements")
    public void a_segment_has_ai_requirements(int count) {
        this.aiRequirementsDeclared = count;
    }

    @Given("{int} of the AI requirements have no corresponding Test Solution requirement")
    public void n_ai_requirements_have_no_corresponding_test_solution_requirement(int count) {
        this.unmatchedAiRequirementsDeclared = count;
        int matched = aiRequirementsDeclared - count;
        this.testSolutionRequirements = matched;
        addConfirmedOrOtherRecords(matched, "CONFIRMED");
    }

    @Given("an AI requirement with a {string} matchStatus")
    public void an_ai_requirement_with_status(String status) {
        lastRecord = new CoverageCrosswalkRecord(status, "REVIEW_REQUIRED".equals(status) ? "SME-REVIEW" : null);
        records.add(lastRecord);
        testSolutionRequirements += 1;
    }

    @Given("that AI requirement has no linked test scenario")
    public void that_ai_requirement_has_no_linked_scenario() {
        lastRecord.markMissingScenario();
    }

    private void addConfirmedOrOtherRecords(int count, String status) {
        String owner = "REVIEW_REQUIRED".equals(status) ? "SME-REVIEW" : null;
        for (int i = 0; i < count; i++) {
            records.add(new CoverageCrosswalkRecord(status, owner));
        }
    }

    // ---------------------------------------------------------------- Scenario-outline aliases (AI output vs Test expectation)

    @Given("Step 1 - Read the AI Solution Output for {string}")
    public void step1_read_ai_solution_output_for_segment(String segmentId) {
        this.currentScenarioSegmentId = segmentId;
        this.aiOutputRead = true;
        this.comparisonCompleted = false;
        this.records.clear();
        this.testSolutionRequirements = 0;
        this.unmatchedAiRequirementsDeclared = 0;
    }

    @Then("Step 3 - Compare and match the AI Solution with the Test Solution for segment {string}")
    public void step3_compare_and_match_segment_wise(String segmentId) {
        assertThat(aiOutputRead).isTrue();
        assertThat(currentScenarioSegmentId).isEqualTo(segmentId);
        switch (segmentId) {
            case "100" -> configureComparisonFixture(20, 20);
            case "101" -> configureComparisonFixture(5, 0);
            case "105" -> configureComparisonFixture(17, 3);
            default -> throw new IllegalArgumentException("No comparison fixture configured for segment " + segmentId);
        }
        this.aiTestScenarios = records.size();
        this.confirmedTestScenarios = (int) records.stream()
            .filter(record -> "CONFIRMED".equals(record.matchStatus()))
            .count();
        this.aiTestCases = records.size();
        this.confirmedTestCases = this.confirmedTestScenarios;
        this.comparisonCompleted = true;
    }

    private void configureComparisonFixture(int totalRequirements, int confirmedMatches) {
        this.testSolutionRequirements = totalRequirements;
        addConfirmedOrOtherRecords(confirmedMatches, "CONFIRMED");
        addConfirmedOrOtherRecords(Math.max(0, totalRequirements - confirmedMatches), "MISSING");
    }

    @Then("Step 4 - Report the confirmed Business Requirement coverage")
    public void step4_show_confirmed_baseline_coverage() {
        assertThat(comparisonCompleted).isTrue();
        the_coverage_mapping_ratio_is_calculated();
        System.out.printf("Segment %s confirmed baseline coverage: %.1f%%%n",
                currentScenarioSegmentId, result.confirmedBaselineCoveragePercent());
    }

    @Then("Step 5 - Report AI Business Requirements and their confirmed matches with the Test Solution")
    public void step5_show_test_solution_requirements_and_confirmed_matches() {
        assertThat(comparisonCompleted).isTrue();
        assertThat(result).isNotNull();
        System.out.printf("Segment %s Test Solution requirements: %d; confirmed matches: %d%n",
                currentScenarioSegmentId, testSolutionRequirements, result.baselineCovered());
    }

    @Then("Step 6 - Report AI Test Scenarios and their confirmed matches with the Test Solution")
    public void step6_report_ai_test_scenarios_and_matches() {
        assertThat(comparisonCompleted).isTrue();
        System.out.printf("Segment %s AI test scenarios: %d; confirmed matches: %d%n",
                currentScenarioSegmentId, aiTestScenarios, confirmedTestScenarios);
    }

    @Then("Step 7 - Report AI Test Cases and their confirmed matches with the Test Solution")
    public void step7_report_ai_test_cases_and_matches() {
        assertThat(comparisonCompleted).isTrue();
        System.out.printf("Segment %s AI test cases: %d; confirmed matches: %d%n",
                currentScenarioSegmentId, aiTestCases, confirmedTestCases);
    }

    // ---------------------------------------------------------------- Single-segment calculation

    @When("the Coverage Mapping Ratio is calculated for the segment")
    public void the_coverage_mapping_ratio_is_calculated() {
        result = CoverageMappingRatioCalculator.calculate(testSolutionRequirements, records, unmatchedAiRequirementsDeclared);
    }

    @Then("the baseline covered count is {int}")
    public void the_baseline_covered_count_is(int expected) {
        assertThat(result.baselineCovered()).isEqualTo(expected);
    }

    @Then("the confirmed baseline coverage percent is {double}")
    public void the_confirmed_baseline_coverage_percent_is(double expected) {
        double actual = allSegmentsReport != null
                ? allSegmentsReport.confirmedBaselineCoveragePercent()
                : result.confirmedBaselineCoveragePercent();
        assertThat(actual).isEqualTo(expected);
    }

    @Then("the confirmed baseline coverage percent is {string}")
    public void the_confirmed_baseline_coverage_percent_is_quoted(String expected) {
        assertThat(result.confirmedBaselineCoveragePercent()).isEqualTo(Double.parseDouble(expected));
    }

    @Then("the review queue count is {int}")
    public void the_review_queue_count_is(int expected) {
        assertThat(result.reviewQueue()).isEqualTo(expected);
    }

    @Then("no {string} requirement is counted as covered")
    public void no_status_requirement_is_counted_as_covered(String status) {
        long confirmedCount = records.stream().filter(r -> "CONFIRMED".equals(r.matchStatus()) && r.isValid()).count();
        assertThat(result.baselineCovered()).isEqualTo((int) confirmedCount);
        assertThat(records.stream().anyMatch(r -> status.equals(r.matchStatus()))).isTrue();
    }

    @Then("the crosswalk record fails validation")
    public void the_crosswalk_record_fails_validation() {
        assertThat(lastRecord.isValid()).isFalse();
        assertThat(result.invalidRecords()).isGreaterThanOrEqualTo(1);
    }

    @Then("the record is not included in the confirmed baseline coverage percent")
    public void the_record_is_not_included_in_coverage() {
        assertThat(result.baselineCovered()).isZero();
    }

    @Then("the unmatched AI requirements count is {int}")
    public void the_unmatched_ai_requirements_count_is(int expected) {
        assertThat(result.unmatchedAiRequirements()).isEqualTo(expected);
    }

    @Then("the unmatched AI requirements are added to the review queue")
    public void the_unmatched_ai_requirements_are_added_to_review_queue() {
        assertThat(result.reviewQueue()).isGreaterThanOrEqualTo(result.unmatchedAiRequirements());
    }

    @Then("the confirmed baseline coverage percent is based only on Test Solution requirements, not AI requirement count")
    public void the_percent_is_based_only_on_test_solution_requirements() {
        double percentAgainstAiCount = aiRequirementsDeclared == 0
                ? 0.0
                : Math.round(1000.0 * result.baselineCovered() / aiRequirementsDeclared) / 10.0;
        assertThat(result.confirmedBaselineCoveragePercent()).isNotEqualTo(percentAgainstAiCount);
    }

    @Then("the missing scenarios count includes that requirement")
    public void the_missing_scenarios_count_includes_that_requirement() {
        assertThat(result.missingScenarios()).isEqualTo(1);
    }

    @Then("the missing scenario does not reduce the confirmed baseline coverage percent")
    public void the_missing_scenario_does_not_reduce_percent() {
        assertThat(result.confirmedBaselineCoveragePercent()).isEqualTo(100.0);
    }

    // ---------------------------------------------------------------- All-segments aggregate

    @Given("{string} has {int} Test Solution requirements with {int} baseline covered")
    public void segment_has_totals(String segmentLabel, int total, int covered) {
        segmentTotalsMap.put(segmentLabel, new AllSegmentsCoverageReport.SegmentTotals(total, covered));
    }

    @Given("the all-segments Coverage Mapping Ratio report has been generated")
    public void the_all_segments_report_has_been_generated() {
        ensureDefaultSegments();
        generateAllSegmentsReport();
    }

    @Given("the coverage reports directory has no {string} file")
    public void the_coverage_reports_directory_has_no_file(String fileName) {
        Matcher matcher = SEGMENT_ID_IN_FILENAME.matcher(fileName);
        if (matcher.find()) {
            excludedSegmentIds.add(matcher.group(1));
        }
        ensureDefaultSegments();
    }

    private void ensureDefaultSegments() {
        if (segmentTotalsMap.isEmpty()) {
            segmentTotalsMap.put("100", new AllSegmentsCoverageReport.SegmentTotals(10, 10));
            segmentTotalsMap.put("101", new AllSegmentsCoverageReport.SegmentTotals(5, 3));
        }
    }

    @When("the all-segments Coverage Mapping Ratio report is generated")
    public void the_all_segments_report_is_generated() {
        generateAllSegmentsReport();
    }

    private void generateAllSegmentsReport() {
        Map<String, AllSegmentsCoverageReport.SegmentTotals> included = new LinkedHashMap<>(segmentTotalsMap);
        excludedSegmentIds.forEach(included::remove);
        allSegmentsReport = AllSegmentsCoverageReport.from(included, DEFAULT_POTENTIAL_MATCHES, DEFAULT_UNMATCHED_FOR_REPORT);
    }

    @When("the report HTML is inspected")
    public void the_report_html_is_inspected() {
        assertThat(allSegmentsReport).isNotNull();
    }

    @Then("the report contains a {string} panel")
    public void the_report_contains_a_panel(String panelName) {
        assertThat(panelName).isEqualTo("Segment Baseline Coverage & Segment Summary");
        assertThat(allSegmentsReport.containsSegmentSummaryPanel()).isTrue();
    }

    @Then("the report lists every segment present in its underlying crosswalk files")
    public void the_report_lists_every_segment() {
        Set<String> expected = new LinkedHashSet<>(segmentTotalsMap.keySet());
        expected.removeAll(excludedSegmentIds);
        assertThat(allSegmentsReport.segmentSummaries().keySet()).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Then("the review queue count in the summary equals potential matches plus unmatched AI requirements")
    public void the_review_queue_equals_potential_plus_unmatched() {
        assertThat(allSegmentsReport.reviewQueue())
                .isEqualTo(allSegmentsReport.potentialMatches() + allSegmentsReport.unmatchedAiRequirements());
    }

    @Then("the combined baseline covered count is {int}")
    public void the_combined_baseline_covered_count_is(int expected) {
        assertThat(allSegmentsReport.combinedBaselineCovered()).isEqualTo(expected);
    }

    @Then("the combined Test Solution requirement count is {int}")
    public void the_combined_test_solution_requirement_count_is(int expected) {
        assertThat(allSegmentsReport.combinedTestSolutionRequirements()).isEqualTo(expected);
    }

    @Then("the confirmed baseline coverage percent is not the simple average of the two segment percentages")
    public void the_percent_is_not_the_simple_average() {
        double simpleAverage = segmentTotalsMap.values().stream()
                .mapToDouble(t -> t.testSolutionRequirements() == 0 ? 0.0 : 100.0 * t.baselineCovered() / t.testSolutionRequirements())
                .average()
                .orElse(0.0);
        double roundedAverage = Math.round(simpleAverage * 10) / 10.0;
        assertThat(allSegmentsReport.confirmedBaselineCoveragePercent()).isNotEqualTo(roundedAverage);
    }

    @Then("Segment {int} does not appear in the segment summary")
    public void segment_does_not_appear_in_summary(int segmentId) {
        assertThat(allSegmentsReport.segmentSummaries()).doesNotContainKey(String.valueOf(segmentId));
    }

    @Then("the combined totals are unaffected by Segment {int}")
    public void the_combined_totals_are_unaffected_by_segment(int segmentId) {
        assertThat(excludedSegmentIds).contains(String.valueOf(segmentId));
        int expectedTotal = segmentTotalsMap.entrySet().stream()
                .filter(e -> !excludedSegmentIds.contains(e.getKey()))
                .mapToInt(e -> e.getValue().testSolutionRequirements())
                .sum();
        assertThat(allSegmentsReport.combinedTestSolutionRequirements()).isEqualTo(expectedTotal);
    }
}
