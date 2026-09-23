package com.coreauth.validator;

import com.coreauth.validator.canonical.RequirementMatchStatus;
import com.coreauth.validator.canonical.SourceAnchor;
import com.coreauth.validator.coverage.AiCoverageAssessmentReport;
import com.coreauth.validator.coverage.AiCoverageAssessmentService;
import com.coreauth.validator.coverage.IndependentRequirementBaseline;
import com.coreauth.validator.coverage.Run2Crosswalk;
import com.coreauth.validator.coverage.Run2CrosswalkLoader;
import com.coreauth.validator.coverage.Run2CoverageAssessmentRunner;
import com.coreauth.validator.coverage.Run2TraceabilityAdapter;
import com.coreauth.validator.validation.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Run2TraceabilityAdapterTest {

    @Test
    void streamsRun2AndResolvesVariantPayloadNames(@TempDir Path runRoot) throws Exception {
        Path dataDirectory = Files.createDirectories(runRoot.resolve("qe_shaped_test_data"));
        Files.writeString(dataDirectory.resolve("TC-0001-CPC-variant.json"), "{\"Financial Request\":{}}");
        Path traceability = writeTraceability(runRoot, "2025-3");
        SourceAnchor anchor = anchor("2026-3");
        Run2Crosswalk crosswalk = confirmedCrosswalk(anchor);

        Run2TraceabilityAdapter.AdaptedRun adapted = new Run2TraceabilityAdapter()
                .adapt(traceability, runRoot, crosswalk);

        assertThat(adapted.requirements()).isEqualTo(1);
        assertThat(adapted.scenarios()).isEqualTo(1);
        assertThat(adapted.testCases()).isEqualTo(1);
        assertThat(adapted.testData()).isEqualTo(1);
        assertThat(adapted.artifactPackage().getManifest().getSpecificationVersion()).isEqualTo("2025-3");
        assertThat(adapted.artifactPackage().getBusinessRequirements().getFirst().getId()).isEqualTo("AI-REQ-1");
        assertThat(adapted.artifactPackage().getTestCases().getFirst().getTestDataFile())
                .isEqualTo("qe_shaped_test_data/TC-0001-CPC-variant.json");
        assertThat(adapted.artifactPackage().getTestData().getFirst().getPayload().path("sourceFile").asText())
                .isEqualTo("qe_shaped_test_data/TC-0001-CPC-variant.json");
    }

    @Test
    void run2VersionMismatchBlocksConfirmedCoverage(@TempDir Path runRoot) throws Exception {
        Path dataDirectory = Files.createDirectories(runRoot.resolve("qe_shaped_test_data"));
        Files.writeString(dataDirectory.resolve("TC-0001-CPC-variant.json"), "{\"Financial Request\":{}}");
        SourceAnchor anchor = anchor("2026-3");
        var adapted = new Run2TraceabilityAdapter().adapt(
                writeTraceability(runRoot, "2025-3"), runRoot, confirmedCrosswalk(anchor));
        IndependentRequirementBaseline baseline = new IndependentRequirementBaseline(List.of(
                new IndependentRequirementBaseline.Requirement("TS-REQ-1", List.of(anchor), true)));

        AiCoverageAssessmentReport report = new AiCoverageAssessmentService().assess(
                adapted.artifactPackage(), baseline,
                List.of(new AiCoverageAssessmentService.NamedPayloadValidator("fixture",
                        ignored -> new ValidationResult("fixture"))));

        assertThat(report.confirmedRequirements()).isZero();
        assertThat(report.reviewRequired()).isEqualTo(1);
        assertThat(report.confirmedRequirementCoveragePercent()).isZero();
        assertThat(report.traceabilityValidation().errors())
                .anyMatch(error -> error.reason().contains("does not match manifest version 2025-3"));
    }

    @Test
    void loadsSeparateSmeOwnedCrosswalk(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("run2-crosswalk.json");
        Files.writeString(file, """
                {"mappings":[{
                  "testRequirementId":"TS-REQ-1",
                  "aiRequirementIds":["AI-REQ-1"],
                  "matchStatus":"REVIEW_REQUIRED",
                  "matchReason":"Same field; version discrepancy remains open",
                  "reviewOwner":"ATL105-SME",
                  "sourceAnchors":[{"specification":"ATL105","version":"2026-3","section":"12.1","segment":"100","element":"2","rule":"account-number"}]
                }]}
                """);

        Run2Crosswalk crosswalk = new Run2CrosswalkLoader().load(file);

        assertThat(crosswalk.mappings()).hasSize(1);
        assertThat(crosswalk.mappings().getFirst().matchStatus()).isEqualTo(RequirementMatchStatus.REVIEW_REQUIRED);
        assertThat(crosswalk.mappings().getFirst().reviewOwner()).isEqualTo("ATL105-SME");
        assertThat(crosswalk.mappings().getFirst().sourceAnchors().getFirst().canonicalKey())
                .isEqualTo("atl105|2026-3|12.1|100|2|account-number");
    }

    @Test
    void runnerWritesAssessmentFromSeparateRunBaselineAndCrosswalkFiles(@TempDir Path root) throws Exception {
        Path dataDirectory = Files.createDirectories(root.resolve("qe_shaped_test_data"));
        Files.writeString(dataDirectory.resolve("TC-0001-CPC-variant.json"), "{\"Financial Request\":{}}");
        Path crosswalk = root.resolve("run2-crosswalk.json");
        Files.writeString(crosswalk, """
                {"mappings":[{
                  "testRequirementId":"TS-REQ-1","aiRequirementIds":["AI-REQ-1"],
                  "matchStatus":"CONFIRMED","matchReason":"Exact business equivalence",
                  "sourceAnchors":[{"specification":"ATL105","version":"2026-3","section":"12.1","segment":"100","element":"2","rule":"account-number"}]
                }]}
                """);
        Path catalog = root.resolve("segment-100-rule-catalog.json");
        Files.writeString(catalog, """
                {"rules":[{"ruleId":"TS-REQ-1","sourceAnchor":{"specification":"ATL105","version":"2026-3","section":"12.1","segment":"100","element":"2","rule":"account-number"}}]}
                """);
        Path output = root.resolve("assessment.json");

        AiCoverageAssessmentReport report = new Run2CoverageAssessmentRunner().run(
                writeTraceability(root, "2025-3"), root, crosswalk, List.of(catalog),
                List.of(new AiCoverageAssessmentService.NamedPayloadValidator("fixture",
                        ignored -> new ValidationResult("fixture"))), output);

        assertThat(output).exists();
        assertThat(report.confirmedRequirementCoveragePercent()).isZero();
        assertThat(Files.readString(output)).contains("REVIEW_REQUIRED", "fullChainCoveragePercent");
    }

    @Test
    void retainsOrphanScenariosAndMissingCandidateTestCasesForReview(@TempDir Path runRoot) throws Exception {
        Path traceability = runRoot.resolve("traceability_matrix_full.json");
        Files.writeString(traceability, """
                {"spec":{"spec_id":"SRC-1","spec_version":"2025-3"},"requirements":[],
                 "orphans":{"scenarios_without_requirement":[{"scenario_id":"SC-ORPHAN","reason":"UNRESOLVED_REQUIREMENT"}]}}
                """);
        Files.writeString(runRoot.resolve("test_case_candidates.json"), """
                {"test_cases":[{"id":"TC-FLOW-1","scenario_id":"SC-ORPHAN","scenario_type":"flow",
                  "priority":"P1","verification":{"status":"PASS"}}]}
                """);

        var adapted = new Run2TraceabilityAdapter().adapt(
                traceability, runRoot, new Run2Crosswalk(List.of()));

        assertThat(adapted.scenarios()).isEqualTo(1);
        assertThat(adapted.testCases()).isEqualTo(1);
        assertThat(adapted.artifactPackage().getTestScenarios().getFirst().getRequirementIds()).isEmpty();
        assertThat(adapted.artifactPackage().getTestCases().getFirst().getScenarioIds())
                .containsExactly("SC-ORPHAN");
        assertThat(adapted.artifactPackage().getTestCases().getFirst().getStatus())
                .isEqualTo("REVIEW_REQUIRED");
    }

    private static Path writeTraceability(Path runRoot, String version) throws Exception {
        Path file = runRoot.resolve("traceability_matrix_full.json");
        Files.writeString(file, """
                {
                  "artifact":"traceability_matrix",
                  "spec":{"spec_id":"SRC-ATL105-PDF-001","spec_version":"%s"},
                  "summary":{"total_requirements":1},
                  "requirements":[{
                    "requirement_id":"AI-REQ-1",
                    "statement":"Account Number is required.",
                    "segment_id":"ENT-SEG-100",
                    "source_rule_id":"ENT-ELEM-2",
                    "source_page":200,
                    "scenarios":[{
                      "scenario_id":"SC-0001",
                      "test_cases":[{
                        "test_case_id":"TC-0001",
                        "priority":"P1",
                        "verification_status":"PASS",
                        "expected_response":{"response_code":"0"},
                        "test_data":{"test_data_file":"qe_shaped_test_data/TC-0001.json","file_exists":true}
                      }]
                    }]
                  }]
                }
                """.formatted(version));
        return file;
    }

    private static Run2Crosswalk confirmedCrosswalk(SourceAnchor anchor) {
        return new Run2Crosswalk(List.of(new Run2Crosswalk.Mapping(
                "TS-REQ-1", List.of("AI-REQ-1"), RequirementMatchStatus.CONFIRMED,
                "Exact source evidence and business equivalence", null, List.of(anchor))));
    }

    private static SourceAnchor anchor(String version) {
        SourceAnchor anchor = new SourceAnchor();
        anchor.setSpecification("ATL105");
        anchor.setVersion(version);
        anchor.setSection("12.1");
        anchor.setSegment("100");
        anchor.setElement("2");
        anchor.setRule("account-number");
        return anchor;
    }
}