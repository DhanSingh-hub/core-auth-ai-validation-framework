Feature: AI Output vs Test Expectation Segment Wise Comparison
  As the Test Validation team
  I want a scenario-outline workflow to read, normalize, and compare AI output against Test expectations segment wise
  So that baseline coverage comparison is consistent and auditable

  Scenario Outline: Match AI output with Test expectation segment wise
    Given Step 1 - Read the AI Solution Output for "<segmentId>"
    Then Step 3 - Compare and match the AI Solution with the Test Solution for segment "<segmentId>"
    Then Step 4 - Report the confirmed Business Requirement coverage
    And Step 5 - Report AI Business Requirements and their confirmed matches with the Test Solution
    And Step 6 - Report AI Test Scenarios and their confirmed matches with the Test Solution
    And Step 7 - Report AI Test Cases and their confirmed matches with the Test Solution

    Examples:
      | segmentId |
      | 100       |
      | 101       |
      | 105       |
