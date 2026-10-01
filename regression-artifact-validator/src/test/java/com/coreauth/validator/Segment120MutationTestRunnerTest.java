package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment120MutationTestRunner;
import com.coreauth.validator.canonical.Segment120MutationTestRunner.MutationTestSuiteReport;
import com.coreauth.validator.canonical.Segment120MutationTestRunner.PackageMutationResult;
import com.coreauth.validator.paths.Atl105Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/** Item 6 tests for batch execution and aggregation. */
class Segment120MutationTestRunnerTest {
    private static final String JSON = "{\"messageType\":\"FINANCIAL_TRANSACTION_RESPONSE\",\"response\":{\"dataSection3\":{\"printData2Segment\":{\"segmentType\":\"120\",\"segmentLength\":\"0041\",\"printData\":\"THANK YOU FOR SHOPPING WITH US TODAY\"}}}}";
    private static Path write(Path dir, String name) throws Exception { Path p=dir.resolve(name); Files.writeString(p, JSON); return p; }

    @Test void missingDirectoryDiscoversNothing(@TempDir Path d) throws Exception { assertThat(new Segment120MutationTestRunner(d.resolve("missing")).discoverPackages()).isEmpty(); }
    @Test void emptyDirectoryDiscoversNothing(@TempDir Path d) throws Exception { assertThat(new Segment120MutationTestRunner(d).discoverPackages()).isEmpty(); }
    @Test void discoversSyntheticJson(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); assertThat(new Segment120MutationTestRunner(d).discoverPackages()).hasSize(1); }
    @Test void ignoresNonSyntheticJson(@TempDir Path d) throws Exception { write(d,"one.json"); assertThat(new Segment120MutationTestRunner(d).discoverPackages()).isEmpty(); }
    @Test void ignoresRejectionPackages(@TempDir Path d) throws Exception { write(d,"rejection-one.synthetic.json"); assertThat(new Segment120MutationTestRunner(d).discoverPackages()).isEmpty(); }
    @Test void discoversNestedPackages(@TempDir Path d) throws Exception { Files.createDirectories(d.resolve("nested")); write(d.resolve("nested"),"one.synthetic.json"); assertThat(new Segment120MutationTestRunner(d).discoverPackages()).hasSize(1); }
    @Test void discoveryIsSorted(@TempDir Path d) throws Exception { write(d,"b.synthetic.json"); write(d,"a.synthetic.json"); assertThat(new Segment120MutationTestRunner(d).discoverPackages()).extracting(Path::getFileName).extracting(Object::toString).containsExactly("a.synthetic.json","b.synthetic.json"); }
    @Test void loadsPackage(@TempDir Path d) throws Exception { Path p=write(d,"one.synthetic.json"); assertThat(new Segment120MutationTestRunner(d).loadPackage(p).path("response")).isNotNull(); }
    @Test void testsPackageWithTenMutations(@TempDir Path d) throws Exception { Path p=write(d,"one.synthetic.json"); PackageMutationResult r=new Segment120MutationTestRunner(d).testPackage(p); assertThat(r.mutationCount()).isEqualTo(10); }
    @Test void packageNameComesFromFile(@TempDir Path d) throws Exception { Path p=write(d,"one.synthetic.json"); assertThat(new Segment120MutationTestRunner(d).testPackage(p).packageName()).isEqualTo("one.synthetic.json"); }
    @Test void packageMetricsHaveTenTotal(@TempDir Path d) throws Exception { Path p=write(d,"one.synthetic.json"); assertThat(new Segment120MutationTestRunner(d).testPackage(p).metrics().totalMutations()).isEqualTo(10); }
    @Test void onePackageAggregatesTenRuns(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); MutationTestSuiteReport r=new Segment120MutationTestRunner(d).runAllPackages(); assertThat(r.totalPackages()).isEqualTo(1); assertThat(r.totalMutationsRun()).isEqualTo(10); }
    @Test void twoPackagesAggregateTwentyRuns(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); write(d,"two.synthetic.json"); assertThat(new Segment120MutationTestRunner(d).runAllPackages().totalMutationsRun()).isEqualTo(20); }
    @Test void emptySuiteHasZeroRate(@TempDir Path d) throws Exception { MutationTestSuiteReport r=new Segment120MutationTestRunner(d).runAllPackages(); assertThat(r.overallDetectionRate()).isZero(); }
    @Test void reportContainsSummaryHeading(@TempDir Path d) throws Exception { String s=new Segment120MutationTestRunner(d).runAllPackages().summary(); assertThat(s).contains("Segment 120 Mutation Test Suite Report"); }
    @Test void reportContainsPackageCount(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); assertThat(new Segment120MutationTestRunner(d).runAllPackages().summary()).contains("Total Packages Tested: 1"); }
    @Test void packageResultsRetainMetrics(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); assertThat(new Segment120MutationTestRunner(d).runAllPackages().packageResults()).hasSize(1); }
    @Test void undetectedMutationIdsAreAggregated(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); MutationTestSuiteReport r=new Segment120MutationTestRunner(d).runAllPackages(); assertThat(r.allUndetectedMutations()).isNotNull(); }
    @Test void gapsByRuleIsAvailable(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); assertThat(new Segment120MutationTestRunner(d).runAllPackages().mutationGapsByRule()).isNotNull(); }
    @Test void nestedPackagesAreIncludedInRun(@TempDir Path d) throws Exception { Files.createDirectories(d.resolve("nested")); write(d.resolve("nested"),"one.synthetic.json"); assertThat(new Segment120MutationTestRunner(d).runAllPackages().totalPackages()).isEqualTo(1); }

    @Test void realLifecyclePackagesAllDetectAtOrAboveEightyFivePercent() throws Exception {
        Path real = Atl105Paths.aiTestData("120");
        MutationTestSuiteReport r = new Segment120MutationTestRunner(real).runAllPackages();
        assertThat(r.totalPackages()).isGreaterThanOrEqualTo(2);
        assertThat(r.overallDetectionRate()).isGreaterThanOrEqualTo(85.0);
    }

    @Test void realLifecyclePackagesEachRunTenMutations() throws Exception {
        Path real = Atl105Paths.aiTestData("120");
        MutationTestSuiteReport r = new Segment120MutationTestRunner(real).runAllPackages();
        assertThat(r.packageResults()).allMatch(p -> p.mutationCount() == 10);
    }
}
