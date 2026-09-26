package com.coreauth.validator.coverage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/** File boundary for adapting Run2, applying the independent baseline, and writing the audit report. */
public final class Run2CoverageAssessmentRunner {
    private final Run2TraceabilityAdapter adapter = new Run2TraceabilityAdapter();
    private final Run2CrosswalkLoader crosswalkLoader = new Run2CrosswalkLoader();
        private final Run2SpecificationVersionResolutionLoader versionResolutionLoader =
            new Run2SpecificationVersionResolutionLoader();
    private final RuleCatalogBaselineLoader baselineLoader = new RuleCatalogBaselineLoader();
    private final AiCoverageAssessmentService assessmentService = new AiCoverageAssessmentService();
    private final AiCoverageAssessmentReportWriter reportWriter = new AiCoverageAssessmentReportWriter();

    public AiCoverageAssessmentReport run(Path traceabilityFile,
                                          Path runRoot,
                                          Path crosswalkFile,
                                          List<Path> ruleCatalogs,
                                          List<AiCoverageAssessmentService.NamedPayloadValidator> payloadValidators,
                                          Path outputFile) throws IOException {
                        return run(traceabilityFile, runRoot, crosswalkFile, null, ruleCatalogs,
                            payloadValidators, outputFile);
                        }

                        public AiCoverageAssessmentReport run(Path traceabilityFile,
                                          Path runRoot,
                                          Path crosswalkFile,
                                          Path versionResolutionFile,
                                          List<Path> ruleCatalogs,
                                          List<AiCoverageAssessmentService.NamedPayloadValidator> payloadValidators,
                                          Path outputFile) throws IOException {
        Run2Crosswalk crosswalk = crosswalkLoader.load(crosswalkFile);
                        Run2SpecificationVersionResolution resolution = versionResolutionFile == null ? null
                            : versionResolutionLoader.load(versionResolutionFile);
        IndependentRequirementBaseline baseline = baselineLoader.load(ruleCatalogs);
                        Run2TraceabilityAdapter.AdaptedRun adapted = adapter.adapt(
                            traceabilityFile, runRoot, crosswalk, resolution);
        AiCoverageAssessmentReport report = assessmentService.assess(
                adapted.artifactPackage(), baseline, payloadValidators);
        reportWriter.write(report, outputFile);
        return report;
    }
}