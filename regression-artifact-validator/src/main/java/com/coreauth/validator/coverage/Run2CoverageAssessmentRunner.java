package com.coreauth.validator.coverage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/** File boundary for adapting Run2, applying the independent baseline, and writing the audit report. */
public final class Run2CoverageAssessmentRunner {
    private final Run2TraceabilityAdapter adapter = new Run2TraceabilityAdapter();
    private final Run2CrosswalkLoader crosswalkLoader = new Run2CrosswalkLoader();
    private final RuleCatalogBaselineLoader baselineLoader = new RuleCatalogBaselineLoader();
    private final AiCoverageAssessmentService assessmentService = new AiCoverageAssessmentService();
    private final AiCoverageAssessmentReportWriter reportWriter = new AiCoverageAssessmentReportWriter();

    public AiCoverageAssessmentReport run(Path traceabilityFile,
                                          Path runRoot,
                                          Path crosswalkFile,
                                          List<Path> ruleCatalogs,
                                          List<AiCoverageAssessmentService.NamedPayloadValidator> payloadValidators,
                                          Path outputFile) throws IOException {
        Run2Crosswalk crosswalk = crosswalkLoader.load(crosswalkFile);
        IndependentRequirementBaseline baseline = baselineLoader.load(ruleCatalogs);
        Run2TraceabilityAdapter.AdaptedRun adapted = adapter.adapt(traceabilityFile, runRoot, crosswalk);
        AiCoverageAssessmentReport report = assessmentService.assess(
                adapted.artifactPackage(), baseline, payloadValidators);
        reportWriter.write(report, outputFile);
        return report;
    }
}