@coverage-mapping-ratio
Feature: Coverage Mapping Ratio
  As the Test Validation team
  I want the AI Solution's business requirements mapped against the Test Solution baseline per segment
  So that confirmed baseline coverage is reported accurately and nothing is promoted without review

  Background:
    Given the canonical AI-to-Test-Solution requirement crosswalk for a segment
    And each crosswalk record has a "matchStatus" of "CONFIRMED", "REVIEW_REQUIRED", or "MISSING"

  Scenario: Confirmed matches count toward baseline coverage
    Given a segment crosswalk with 10 Test Solution requirements
    And 7 of those requirements have a "CONFIRMED" matchStatus against an AI requirement
    When the Coverage Mapping Ratio is calculated for the segment
    Then the baseline covered count is 7
    And the confirmed baseline coverage percent is 70.0

  Scenario: Review-required matches are excluded from confirmed coverage
    Given a segment crosswalk with 10 Test Solution requirements
    And 6 requirements have a "CONFIRMED" matchStatus
    And 2 requirements have a "REVIEW_REQUIRED" matchStatus with a reviewOwner
    And 2 requirements have a "MISSING" matchStatus
    When the Coverage Mapping Ratio is calculated for the segment
    Then the baseline covered count is 6
    And the review queue count is 4
    And the confirmed baseline coverage percent is 60.0
    And no "REVIEW_REQUIRED" requirement is counted as covered

  Scenario: A REVIEW_REQUIRED match without a review owner is invalid
    Given a segment crosswalk record with a "REVIEW_REQUIRED" matchStatus
    And that record has no reviewOwner
    When the Coverage Mapping Ratio is calculated for the segment
    Then the crosswalk record fails validation
    And the record is not included in the confirmed baseline coverage percent

  Scenario: Unmatched AI requirements do not inflate coverage
    Given a segment has 5 AI requirements
    And 2 of the AI requirements have no corresponding Test Solution requirement
    When the Coverage Mapping Ratio is calculated for the segment
    Then the unmatched AI requirements count is 2
    And the unmatched AI requirements are added to the review queue
    And the confirmed baseline coverage percent is based only on Test Solution requirements, not AI requirement count

  Scenario: AI requirements missing a scenario are flagged separately from coverage
    Given an AI requirement with a "CONFIRMED" matchStatus
    And that AI requirement has no linked test scenario
    When the Coverage Mapping Ratio is calculated for the segment
    Then the missing scenarios count includes that requirement
    And the missing scenario does not reduce the confirmed baseline coverage percent

  Scenario Outline: Coverage Mapping Ratio percentage for a single segment
    Given a segment crosswalk with "<testSolutionRequirements>" Test Solution requirements
    And "<baselineCovered>" of those requirements have a "CONFIRMED" matchStatus
    When the Coverage Mapping Ratio is calculated for the segment
    Then the confirmed baseline coverage percent is "<coveragePercent>"

    Examples:
      | testSolutionRequirements | baselineCovered | coveragePercent |
      | 4                        | 4               | 100.0           |
      | 4                        | 2               | 50.0            |
      | 3                        | 1               | 33.3            |
      | 0                        | 0               | 0.0             |

  Scenario: All-segments Coverage Mapping Ratio is weighted, not averaged per segment
    Given "Segment 100" has 20 Test Solution requirements with 20 baseline covered
    And "Segment 101" has 5 Test Solution requirements with 0 baseline covered
    When the all-segments Coverage Mapping Ratio report is generated
    Then the combined baseline covered count is 20
    And the combined Test Solution requirement count is 25
    And the confirmed baseline coverage percent is 80.0
    And the confirmed baseline coverage percent is not the simple average of the two segment percentages

  Scenario: The all-segments Coverage Mapping Ratio report renders the executive summary panel
    Given the all-segments Coverage Mapping Ratio report has been generated
    When the report HTML is inspected
    Then the report contains a "Segment Baseline Coverage & Segment Summary" panel
    And the report lists every segment present in its underlying crosswalk files
    And the review queue count in the summary equals potential matches plus unmatched AI requirements

  Scenario: A segment with no crosswalk file is excluded from the Coverage Mapping Ratio report
    Given the coverage reports directory has no "POC-AI-Segment-157-BR-Coverage-Crosswalk.json" file
    When the all-segments Coverage Mapping Ratio report is generated
    Then Segment 157 does not appear in the segment summary
    And the combined totals are unaffected by Segment 157
