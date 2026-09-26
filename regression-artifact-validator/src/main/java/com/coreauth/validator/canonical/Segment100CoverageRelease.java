package com.coreauth.validator.canonical;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Combines producer packages and generates the committed Segment 100 coverage reports. */
public final class Segment100CoverageRelease {
    private final CanonicalPackageLoader packageLoader = new CanonicalPackageLoader();
    private final Segment100RuleCatalogLoader catalogLoader = new Segment100RuleCatalogLoader();
    private final Segment100CoverageAnalyzer analyzer = new Segment100CoverageAnalyzer();
    private final Segment100CoverageReportWriter writer = new Segment100CoverageReportWriter();

    public Segment100CoverageReportWriter.ReportFiles generate(List<Path> packageFiles,
                                                               Path catalogFile,
                                                               Path outputDirectory) throws IOException {
        Segment100RuleCatalogLoader.Catalog catalog = catalogLoader.load(catalogFile);
        CanonicalArtifactPackage combined = combine(packageFiles);
        Segment100CoverageAnalyzer.CoverageReport report = analyzer.analyze(combined, catalog.anchors());
        String packageId = combined.getManifest() == null ? catalog.catalogId()
                : combined.getManifest().getPackageId();
        return writer.write(report, packageId, outputDirectory, catalog.rules());
    }

    private CanonicalArtifactPackage combine(List<Path> files) throws IOException {
        CanonicalArtifactPackage combined = new CanonicalArtifactPackage();
        List<CanonicalRequirement> requirements = new ArrayList<>();
        List<CanonicalScenario> scenarios = new ArrayList<>();
        List<CanonicalTestCase> testCases = new ArrayList<>();
        List<CanonicalTestData> testData = new ArrayList<>();
        for (Path file : files) {
            CanonicalArtifactPackage current = packageLoader.load(file);
            if (combined.getManifest() == null) {
                combined.setManifest(current.getManifest());
            }
            addAll(requirements, current.getBusinessRequirements());
            addAll(scenarios, current.getTestScenarios());
            addAll(testCases, current.getTestCases());
            addAll(testData, current.getTestData());
        }
        combined.setBusinessRequirements(requirements);
        combined.setTestScenarios(scenarios);
        combined.setTestCases(testCases);
        combined.setTestData(testData);
        return combined;
    }

    private static <T> void addAll(List<T> target, List<T> values) {
        if (values != null) {
            target.addAll(values);
        }
    }
}