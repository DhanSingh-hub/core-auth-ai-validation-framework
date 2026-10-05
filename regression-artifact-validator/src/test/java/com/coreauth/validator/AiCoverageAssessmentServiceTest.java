package com.coreauth.validator;

import com.coreauth.validator.canonical.ArtifactStatus;
import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalRequirement;
import com.coreauth.validator.canonical.CanonicalScenario;
import com.coreauth.validator.canonical.CanonicalTestCase;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.PackageManifest;
import com.coreauth.validator.canonical.RequirementCrosswalkEntry;
import com.coreauth.validator.canonical.RequirementMatchStatus;
import com.coreauth.validator.canonical.SourceAnchor;
import com.coreauth.validator.coverage.AiCoverageAssessmentReport;
import com.coreauth.validator.coverage.AiCoverageAssessmentReportWriter;
import com.coreauth.validator.coverage.AiCoverageAssessmentService;
import com.coreauth.validator.coverage.IndependentRequirementBaseline;
import com.coreauth.validator.coverage.RuleCatalogBaselineLoader;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AiCoverageAssessmentServiceTest {
    private final AiCoverageAssessmentService service = new AiCoverageAssessmentService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void reportsExecutionReadyOnlyForEvidenceQualifiedFullChainCoverage() {
        SourceAnchor anchor = anchor("account-number-format");
        CanonicalArtifactPackage artifactPackage = fullChainPackage(anchor);

        AiCoverageAssessmentReport report = service.assess(artifactPackage, baseline(anchor),
                List.of(validPayloadValidator()));

        assertThat(report.confirmedRequirementCoveragePercent()).isEqualTo(100.0);
        assertThat(report.fullChainCoveragePercent()).isEqualTo(100.0);
        assertThat(report.strategyReadiness()).allSatisfy((strategy, ready) -> assertThat(ready).isTrue());
        assertThat(report.executionReady()).isTrue();
    }

    @Test
    void downgradesConfirmedCrosswalkWithoutSharedAnchorToReviewRequired() {
        SourceAnchor baselineAnchor = anchor("account-number-format");
        CanonicalArtifactPackage artifactPackage = fullChainPackage(anchor("different-rule"));

        AiCoverageAssessmentReport report = service.assess(artifactPackage, baseline(baselineAnchor),
                List.of(validPayloadValidator()));

        assertThat(report.confirmedRequirements()).isZero();
        assertThat(report.reviewRequired()).isEqualTo(1);
        assertThat(report.confirmedAiBusinessRequirements()).isZero();
        assertThat(report.reviewRequiredAiBusinessRequirements()).isEqualTo(1);
        assertThat(report.confirmedRequirementCoveragePercent()).isZero();
        assertThat(report.fullChainCoveragePercent()).isZero();
        assertThat(report.executionReady()).isFalse();
    }

    @Test
    void missingTestDataCannotCountAsFullChainCoverage() {
        SourceAnchor anchor = anchor("account-number-format");
        CanonicalArtifactPackage artifactPackage = fullChainPackage(anchor);
        artifactPackage.setTestData(List.of());

        AiCoverageAssessmentReport report = service.assess(artifactPackage, baseline(anchor),
                List.of(validPayloadValidator()));

        assertThat(report.confirmedRequirementCoveragePercent()).isEqualTo(100.0);
        assertThat(report.fullChainCoveragePercent()).isZero();
        assertThat(report.strategyReadiness().get("TEST_CASE_QUALITY")).isFalse();
        assertThat(report.strategyReadiness().get("INDEPENDENT_TRACEABILITY")).isFalse();
    }

        @Test
        void keepsAiRequirementInventorySeparateFromIndependentRuleDenominator() {
        SourceAnchor anchor = anchor("account-number-format");
        CanonicalArtifactPackage artifactPackage = fullChainPackage(anchor);
        CanonicalRequirement additionalRequirement = new CanonicalRequirement();
        additionalRequirement.setId("AI-BR-UNMAPPED");
        additionalRequirement.setSourceAnchors(List.of(anchor("unmapped-rule")));
        artifactPackage.setBusinessRequirements(List.of(
            artifactPackage.getBusinessRequirements().getFirst(), additionalRequirement));

        AiCoverageAssessmentReport report = service.assess(artifactPackage, baseline(anchor),
            List.of(validPayloadValidator()));

        assertThat(report.coverageDenominator()).isEqualTo(1);
        assertThat(report.independentRuleDenominatorStructurallyValid()).isTrue();
        assertThat(report.confirmedRequirements()).isEqualTo(1);
        assertThat(report.confirmedRequirementCoveragePercent()).isEqualTo(100.0);
        assertThat(report.aiBusinessRequirementDenominator()).isEqualTo(2);
        assertThat(report.aiBusinessRequirementsWithCrosswalkDisposition()).isEqualTo(1);
        assertThat(report.confirmedAiBusinessRequirements()).isEqualTo(1);
        assertThat(report.unmappedAiBusinessRequirements()).isEqualTo(1);
        assertThat(report.confirmedAiBusinessRequirementInventoryPercent()).isEqualTo(50.0);
        }

        @Test
        void doesNotReportZeroCoverageWhenIndependentBaselineIsInvalid(@TempDir Path tempDir) throws Exception {
        SourceAnchor anchor = anchor("account-number-format");
        IndependentRequirementBaseline invalidBaseline = new IndependentRequirementBaseline(List.of(
            new IndependentRequirementBaseline.Requirement("TS-BR-1", List.of(anchor), true),
            new IndependentRequirementBaseline.Requirement("TS-BR-2", List.of(anchor), true)));

        AiCoverageAssessmentReport report = service.assess(fullChainPackage(anchor), invalidBaseline,
            List.of(validPayloadValidator()));

        assertThat(report.coverageDenominator()).isEqualTo(2);
        assertThat(report.independentRuleDenominatorStructurallyValid()).isFalse();
        assertThat(report.confirmedRequirementCoveragePercent()).isNull();
        assertThat(report.fullChainCoveragePercent()).isNull();
        assertThat(report.executionReady()).isFalse();

        Path output = tempDir.resolve("invalid-baseline-assessment.json");
        new AiCoverageAssessmentReportWriter().write(report, output);
        var json = mapper.readTree(output.toFile());
        assertThat(json.path("coverageDenominatorStructurallyValid").asBoolean()).isFalse();
        assertThat(json.path("confirmedRequirementCoveragePercent").isNull()).isTrue();
        assertThat(json.path("fullChainCoveragePercent").isNull()).isTrue();
        }

    @Test
    void confirmedCrosswalkWithoutEvidenceReasonIsReviewRequired() {
        SourceAnchor anchor = anchor("account-number-format");
        CanonicalArtifactPackage artifactPackage = fullChainPackage(anchor);
        artifactPackage.getRequirementCrosswalk().getFirst().setMatchReason(null);

        AiCoverageAssessmentReport report = service.assess(artifactPackage, baseline(anchor),
                List.of(validPayloadValidator()));

        assertThat(report.confirmedRequirements()).isZero();
        assertThat(report.reviewRequired()).isEqualTo(1);
        assertThat(report.confirmedRequirementCoveragePercent()).isZero();
    }

    @Test
    void manifestVersionMismatchDowngradesConfirmedCoverage() {
        SourceAnchor anchor = anchor("account-number-format");
        CanonicalArtifactPackage artifactPackage = fullChainPackage(anchor);
        artifactPackage.getManifest().setSpecificationVersion("2025-3");

        AiCoverageAssessmentReport report = service.assess(artifactPackage, baseline(anchor),
                List.of(validPayloadValidator()));

        assertThat(report.confirmedRequirements()).isZero();
        assertThat(report.reviewRequired()).isEqualTo(1);
        assertThat(report.confirmedRequirementCoveragePercent()).isZero();
        assertThat(report.strategyReadiness().get("INDEPENDENT_TRACEABILITY")).isFalse();
    }

    @Test
    void unlinkedArtifactsWithTheSameAnchorDoNotCountAsAFullChain() {
        SourceAnchor anchor = anchor("account-number-format");
        CanonicalArtifactPackage artifactPackage = fullChainPackage(anchor);
        artifactPackage.getTestCases().getFirst().setScenarioIds(List.of("AI-SC-MISSING"));

        AiCoverageAssessmentReport report = service.assess(artifactPackage, baseline(anchor),
                List.of(validPayloadValidator()));

        assertThat(report.confirmedRequirementCoveragePercent()).isEqualTo(100.0);
        assertThat(report.fullChainCoveragePercent()).isZero();
        assertThat(report.strategyReadiness().get("INDEPENDENT_TRACEABILITY")).isFalse();
    }

    @Test
    void duplicateCanonicalAnchorsInvalidateTheIndependentBaseline() {
        SourceAnchor anchor = anchor("account-number-format");
        IndependentRequirementBaseline baseline = new IndependentRequirementBaseline(List.of(
                new IndependentRequirementBaseline.Requirement("TS-BR-1", List.of(anchor), true),
                new IndependentRequirementBaseline.Requirement("TS-BR-2", List.of(anchor), true)));

        assertThat(baseline.validate().isValid()).isFalse();
        assertThat(baseline.validate().errors()).anyMatch(error -> error.reason().contains("duplicate sourceAnchor"));
    }

    @Test
    void loadsCanonicalAnchorsAndCoverageScopeFromRuleCatalogs(@TempDir Path tempDir) throws Exception {
        Path catalog = tempDir.resolve("segment-100-rule-catalog.json");
        Files.writeString(catalog, """
                {"rules":[
                  {"ruleId":"SEG100-R-001","sourceAnchor":{"specification":"ATL105","version":"2026-3","section":"12.1","segment":"100","rule":"required"}},
                  {"ruleId":"SEG100-R-002","coverageExcluded":true,"sourceAnchor":{"specification":"ATL105","version":"2026-3","section":"12.1","segment":"100","rule":"informational"}}
                ]}
                """);

        IndependentRequirementBaseline baseline = new RuleCatalogBaselineLoader().load(List.of(catalog));

        assertThat(baseline.requirements()).hasSize(2);
        assertThat(baseline.inScopeRequirements()).extracting(IndependentRequirementBaseline.Requirement::id)
                .containsExactly("SEG100-R-001");
        assertThat(baseline.validate().isValid()).isTrue();
    }

    @Test
    void writesAnAuditableSixStrategyReport(@TempDir Path tempDir) throws Exception {
        SourceAnchor anchor = anchor("account-number-format");
        AiCoverageAssessmentReport report = service.assess(fullChainPackage(anchor), baseline(anchor),
                List.of(validPayloadValidator()));
        Path output = tempDir.resolve("ai-coverage-assessment.json");

        new AiCoverageAssessmentReportWriter().write(report, output);

        var json = mapper.readTree(output.toFile());
        assertThat(json.path("decision").asText()).isEqualTo("EXECUTION_READY");
        assertThat(json.path("strategyReadiness").size()).isEqualTo(6);
        assertThat(json.path("coverageDenominatorType").asText()).isEqualTo("INDEPENDENT_TEST_SOLUTION_RULES");
        assertThat(json.path("aiBusinessRequirementDenominator").asInt()).isEqualTo(1);
        assertThat(json.path("confirmedAiBusinessRequirements").asInt()).isEqualTo(1);
        assertThat(json.path("confirmedAiBusinessRequirementInventoryPercent").asDouble()).isEqualTo(100.0);
        assertThat(json.path("fullChainCoveragePercent").asDouble()).isEqualTo(100.0);
        assertThat(json.path("validation").path("traceability").path("valid").asBoolean()).isTrue();
    }

    private IndependentRequirementBaseline baseline(SourceAnchor anchor) {
        return new IndependentRequirementBaseline(List.of(
                new IndependentRequirementBaseline.Requirement("TS-BR-1", List.of(anchor), true)));
    }

    private AiCoverageAssessmentService.NamedPayloadValidator validPayloadValidator() {
        return new AiCoverageAssessmentService.NamedPayloadValidator("fixture-validator",
                artifactPackage -> new ValidationResult("fixture-payload"));
    }

    private CanonicalArtifactPackage fullChainPackage(SourceAnchor anchor) {
        PackageManifest manifest = new PackageManifest();
        manifest.setPackageId("AI-RUN-2-FIXTURE");
        manifest.setSpecification("ATL105");
        manifest.setSpecificationVersion("2026-3");

        CanonicalRequirement requirement = new CanonicalRequirement();
        requirement.setId("AI-BR-1");
        requirement.setSourceAnchors(List.of(anchor));

        CanonicalScenario scenario = new CanonicalScenario();
        scenario.setId("AI-SC-1");
        scenario.setRequirementIds(List.of(requirement.getId()));
        scenario.setSourceAnchors(List.of(anchor));
        scenario.setStatus(ArtifactStatus.REVIEW_REQUIRED);

        CanonicalTestCase testCase = new CanonicalTestCase();
        testCase.setId("AI-TC-1");
        testCase.setScenarioIds(List.of(scenario.getId()));
        testCase.setSourceAnchors(List.of(anchor));
        testCase.setExpectedOutcome("The account number is accepted");
        testCase.setTestDataFile("AI-TD-1.json");

        CanonicalTestData testData = new CanonicalTestData();
        testData.setId("AI-TD-1");
        testData.setTestCaseIds(List.of(testCase.getId()));
        testData.setSourceAnchors(List.of(anchor));
        testData.setExpectedValidation("PASS");
        testData.setPayload(mapper.createObjectNode().putObject("request"));

        RequirementCrosswalkEntry crosswalk = new RequirementCrosswalkEntry();
        crosswalk.setTestRequirementId("TS-BR-1");
        crosswalk.setAiRequirementIds(List.of(requirement.getId()));
        crosswalk.setMatchStatus(RequirementMatchStatus.CONFIRMED);
        crosswalk.setMatchReason("Exact sourceAnchor and business-rule equivalence");

        CanonicalArtifactPackage artifactPackage = new CanonicalArtifactPackage();
        artifactPackage.setManifest(manifest);
        artifactPackage.setBusinessRequirements(List.of(requirement));
        artifactPackage.setTestScenarios(List.of(scenario));
        artifactPackage.setTestCases(List.of(testCase));
        artifactPackage.setTestData(List.of(testData));
        artifactPackage.setRequirementCrosswalk(List.of(crosswalk));
        return artifactPackage;
    }

    private static SourceAnchor anchor(String rule) {
        SourceAnchor anchor = new SourceAnchor();
        anchor.setSpecification("ATL105");
        anchor.setVersion("2026-3");
        anchor.setSection("2");
        anchor.setSegment("100");
        anchor.setElement("account-number");
        anchor.setRule(rule);
        return anchor;
    }
}