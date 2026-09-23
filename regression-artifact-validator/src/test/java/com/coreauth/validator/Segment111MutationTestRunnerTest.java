package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment111MutationTestRunner;
import com.coreauth.validator.canonical.Segment111MutationTestRunner.MutationTestSuiteReport;
import com.coreauth.validator.canonical.Segment111MutationTestRunner.PackageMutationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/** Item 6 tests for batch execution and aggregation. */
class Segment111MutationTestRunnerTest {
    private static final String JSON = "{\"Financial Request\":{\"Variable Information Data Segment\":{\"SegmentType\":\"111\",\"SegmentLength\":\"010\",\"VariableInformationIndicator\":\"001\",\"VariableInformationLength\":\"001\",\"VariableInformation\":\"A\"}}}";
    private static Path write(Path dir, String name) throws Exception { Path p=dir.resolve(name); Files.writeString(p, JSON); return p; }

    @Test void missingDirectoryDiscoversNothing(@TempDir Path d) throws Exception { assertThat(new Segment111MutationTestRunner(d.resolve("missing")).discoverPackages()).isEmpty(); }
    @Test void emptyDirectoryDiscoversNothing(@TempDir Path d) throws Exception { assertThat(new Segment111MutationTestRunner(d).discoverPackages()).isEmpty(); }
    @Test void discoversSyntheticJson(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); assertThat(new Segment111MutationTestRunner(d).discoverPackages()).hasSize(1); }
    @Test void ignoresNonSyntheticJson(@TempDir Path d) throws Exception { write(d,"one.json"); assertThat(new Segment111MutationTestRunner(d).discoverPackages()).isEmpty(); }
    @Test void ignoresRejectionPackages(@TempDir Path d) throws Exception { write(d,"rejection-one.synthetic.json"); assertThat(new Segment111MutationTestRunner(d).discoverPackages()).isEmpty(); }
    @Test void discoversNestedPackages(@TempDir Path d) throws Exception { Files.createDirectories(d.resolve("nested")); write(d.resolve("nested"),"one.synthetic.json"); assertThat(new Segment111MutationTestRunner(d).discoverPackages()).hasSize(1); }
    @Test void discoveryIsSorted(@TempDir Path d) throws Exception { write(d,"b.synthetic.json"); write(d,"a.synthetic.json"); assertThat(new Segment111MutationTestRunner(d).discoverPackages()).extracting(Path::getFileName).extracting(Object::toString).containsExactly("a.synthetic.json","b.synthetic.json"); }
    @Test void loadsPackage(@TempDir Path d) throws Exception { Path p=write(d,"one.synthetic.json"); assertThat(new Segment111MutationTestRunner(d).loadPackage(p).path("Financial Request")).isNotNull(); }
    @Test void testsPackageWithTenMutations(@TempDir Path d) throws Exception { Path p=write(d,"one.synthetic.json"); PackageMutationResult r=new Segment111MutationTestRunner(d).testPackage(p); assertThat(r.mutationCount()).isEqualTo(10); }
    @Test void packageNameComesFromFile(@TempDir Path d) throws Exception { Path p=write(d,"one.synthetic.json"); assertThat(new Segment111MutationTestRunner(d).testPackage(p).packageName()).isEqualTo("one.synthetic.json"); }
    @Test void packageMetricsHaveTenTotal(@TempDir Path d) throws Exception { Path p=write(d,"one.synthetic.json"); assertThat(new Segment111MutationTestRunner(d).testPackage(p).metrics().totalMutations()).isEqualTo(10); }
    @Test void onePackageAggregatesTenRuns(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); MutationTestSuiteReport r=new Segment111MutationTestRunner(d).runAllPackages(); assertThat(r.totalPackages()).isEqualTo(1); assertThat(r.totalMutationsRun()).isEqualTo(10); }
    @Test void twoPackagesAggregateTwentyRuns(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); write(d,"two.synthetic.json"); assertThat(new Segment111MutationTestRunner(d).runAllPackages().totalMutationsRun()).isEqualTo(20); }
    @Test void emptySuiteHasZeroRate(@TempDir Path d) throws Exception { MutationTestSuiteReport r=new Segment111MutationTestRunner(d).runAllPackages(); assertThat(r.overallDetectionRate()).isZero(); }
    @Test void reportContainsSummaryHeading(@TempDir Path d) throws Exception { String s=new Segment111MutationTestRunner(d).runAllPackages().summary(); assertThat(s).contains("Segment 111 Mutation Test Suite Report"); }
    @Test void reportContainsPackageCount(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); assertThat(new Segment111MutationTestRunner(d).runAllPackages().summary()).contains("Total Packages Tested: 1"); }
    @Test void packageResultsRetainMetrics(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); assertThat(new Segment111MutationTestRunner(d).runAllPackages().packageResults()).hasSize(1); }
    @Test void undetectedMutationIdsAreAggregated(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); MutationTestSuiteReport r=new Segment111MutationTestRunner(d).runAllPackages(); assertThat(r.allUndetectedMutations()).isNotNull(); }
    @Test void gapsByRuleIsAvailable(@TempDir Path d) throws Exception { write(d,"one.synthetic.json"); assertThat(new Segment111MutationTestRunner(d).runAllPackages().mutationGapsByRule()).isNotNull(); }
    @Test void nestedPackagesAreIncludedInRun(@TempDir Path d) throws Exception { Files.createDirectories(d.resolve("nested")); write(d.resolve("nested"),"one.synthetic.json"); assertThat(new Segment111MutationTestRunner(d).runAllPackages().totalPackages()).isEqualTo(1); }
}
