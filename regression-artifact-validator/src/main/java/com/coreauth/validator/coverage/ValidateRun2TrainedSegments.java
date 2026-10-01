package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.Element83AiCoverageValidator;
import com.coreauth.validator.canonical.RequirementCrosswalkEntry;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Executes Step 3 chain and payload validation for all currently trained segments. */
public final class ValidateRun2TrainedSegments {
    private static final List<String> SEGMENTS = List.of(
            "100", "101", "102", "103", "104", "105", "108", "109", "111", "113");

    private ValidateRun2TrainedSegments() { }

    public static void main(String[] args) throws Exception {
        Path runRoot = Atl105Paths.testInput().resolve(
                Path.of("ai-solution", "runs", "2026-09-23", "Run2"));
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        Path crosswalkRoot = reviewRoot.resolve("run2-crosswalks");
        Path outputRoot = reviewRoot.resolve("run2-validation");
        Path resolutionFile = reviewRoot.resolve("run2-specification-version-resolution.json");
        Run2CrosswalkLoader crosswalkLoader = new Run2CrosswalkLoader();
        List<Run2Crosswalk> crosswalks = new ArrayList<>();
        for (String segment : SEGMENTS) {
            crosswalks.add(crosswalkLoader.load(
                    crosswalkRoot.resolve("segment-" + segment + "-run2-crosswalk.json")));
        }
        Run2Crosswalk combined = new Run2Crosswalk(crosswalks.stream()
                .flatMap(value -> value.mappings().stream()).toList());
        var resolution = new Run2SpecificationVersionResolutionLoader().load(resolutionFile);
        var adapted = new Run2TraceabilityAdapter().adapt(
                runRoot.resolve("traceability_matrix_full.json"), runRoot, combined, resolution);

        CanonicalSegmentPackageFilter filter = new CanonicalSegmentPackageFilter();
        RuleCatalogBaselineLoader baselineLoader = new RuleCatalogBaselineLoader();
        Run2PayloadBatchValidator payloadValidator = new Run2PayloadBatchValidator();
        AiCoverageAssessmentService assessmentService = new AiCoverageAssessmentService();
        Run2SegmentValidationEvidenceWriter writer = new Run2SegmentValidationEvidenceWriter();

        for (int index = 0; index < SEGMENTS.size(); index++) {
            String segment = SEGMENTS.get(index);
            Run2Crosswalk crosswalk = crosswalks.get(index);
            List<RequirementCrosswalkEntry> canonicalCrosswalk = canonicalCrosswalk(crosswalk);
            CanonicalArtifactPackage segmentPackage = filter.filter(
                    adapted.artifactPackage(), segment, canonicalCrosswalk);
            var baseline = baselineLoader.load(List.of(Atl105Paths.ruleCatalog(segment)));
            var payloadBatch = payloadValidator.validate(segment, runRoot, segmentPackage);
            var assessment = assessmentService.assess(segmentPackage, baseline,
                    List.of(new AiCoverageAssessmentService.NamedPayloadValidator(
                            "segment-" + segment + "-run2-payloads",
                            ignored -> payloadBatch.asValidationResult())));
            Run2SegmentValidationEvidence evidence = new Run2SegmentValidationEvidence(
                    segment, "2026-09-23/Run1+Run2", adapted.versionResolutionId(),
                    safeSize(segmentPackage.getBusinessRequirements()),
                    safeSize(segmentPackage.getTestScenarios()),
                    safeSize(segmentPackage.getTestCases()),
                    safeSize(segmentPackage.getTestData()), assessment, payloadBatch);
            writer.write(evidence, outputRoot.resolve("segment-" + segment + "-validation.json"));
            System.out.printf("segment=%s br=%d sc=%d tc=%d td=%d confirmed=%.1f fullChain=%.1f "
                            + "payloads=%d valid=%d invalid=%d validator=%s%n",
                    segment, evidence.requirements(), evidence.scenarios(), evidence.testCases(), evidence.testData(),
                    assessment.confirmedRequirementCoveragePercent(), assessment.fullChainCoveragePercent(),
                    payloadBatch.applicablePayloadFiles(), payloadBatch.validPayloadFiles(),
                    payloadBatch.invalidPayloadFiles(), payloadBatch.validatorImplemented());
        }

                var element83Result = new Element83AiCoverageValidator().validateFiles(adapted.artifactPackage(), runRoot);
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode element83Report = mapper.createObjectNode();
                element83Report.put("artifact", "atl105-run2-element-83-ai-coverage");
                element83Report.put("specification", "ATL105");
                element83Report.put("specificationVersion", "2026-3");
                element83Report.put("evidenceRun", "2026-09-23/Run2");
                element83Report.put("valid", element83Result.isValid());
                ArrayNode errors = element83Report.putArray("errors");
                element83Result.errors().forEach(error -> errors.add(error.reason()));
                ArrayNode warnings = element83Report.putArray("warnings");
                element83Result.warnings().forEach(warnings::add);
                Path element83Output = outputRoot.resolve("element-83-response-code-coverage.json");
                Files.createDirectories(element83Output.getParent());
                mapper.writerWithDefaultPrettyPrinter().writeValue(element83Output.toFile(), element83Report);
                System.out.printf("element=83 responseCodeCoverage=%s findings=%d report=%s%n",
                                element83Result.isValid() ? "COMPLETE" : "INCOMPLETE", element83Result.errors().size(), element83Output);
    }

    private static List<RequirementCrosswalkEntry> canonicalCrosswalk(Run2Crosswalk crosswalk) {
        List<RequirementCrosswalkEntry> result = new ArrayList<>();
        for (Run2Crosswalk.Mapping mapping : crosswalk.mappings()) {
            RequirementCrosswalkEntry entry = new RequirementCrosswalkEntry();
            entry.setTestRequirementId(mapping.testRequirementId());
            entry.setAiRequirementIds(mapping.aiRequirementIds());
            entry.setMatchStatus(mapping.matchStatus());
            entry.setMatchReason(mapping.matchReason());
            entry.setReviewOwner(mapping.reviewOwner());
            result.add(entry);
        }
        return result;
    }

    private static int safeSize(List<?> values) {
        return values == null ? 0 : values.size();
    }
}