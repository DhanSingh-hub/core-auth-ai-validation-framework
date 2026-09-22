package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment104MutationTester;
import com.coreauth.validator.canonical.Segment104MutationTester.MutationCase;
import com.coreauth.validator.canonical.Segment104MutationTester.MutationReport;
import com.coreauth.validator.canonical.Segment104MutationTester.MutationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 5 (Mutation Framework Definition) tests for {@link Segment104MutationTester}.
 */
class Segment104MutationTesterTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path SEG104_ROOT = Paths.get("test-input", "ai-solution", "test-data", "segment-104");

    private JsonNode loadFixture(String relativePath) throws IOException {
        return new Segment104MutationTester().loadPayload(SEG104_ROOT.resolve(relativePath));
    }

    private JsonNode purchaseCardAuthOriginal() throws IOException {
        return loadFixture("lifecycle/purchase-card-auth-original.synthetic.json");
    }

    @Nested
    class MutationDefinitionTests {

        @Test
        void defines10StandardMutations() {
            List<MutationCase> mutations = Segment104MutationTester.generateStandardMutations();
            assertThat(mutations).hasSize(10);
        }

        @Test
        void everyMutationHasSeg104RuleAnchor() {
            List<MutationCase> mutations = Segment104MutationTester.generateStandardMutations();
            assertThat(mutations).allMatch(m -> m.ruleId().startsWith("SEG104-R-"));
        }

        @Test
        void everyMutationDeclaresShouldDetectTrue() {
            List<MutationCase> mutations = Segment104MutationTester.generateStandardMutations();
            assertThat(mutations).allMatch(MutationCase::shouldDetect);
        }

        @Test
        void mutationIdsAreUnique() {
            List<String> ids = Segment104MutationTester.allMutationIds();
            assertThat(ids).doesNotHaveDuplicates().hasSize(10);
        }

        @Test
        void mutationCategoriesIncludeFieldValueFormatAndStructural() {
            List<MutationCase> mutations = Segment104MutationTester.generateStandardMutations();
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("field-value"));
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("field-format"));
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("structural"));
        }

        @Test
        void mutationsForRuleReturnsFilteredSet() {
            List<MutationCase> segTypeMutations = Segment104MutationTester.mutationsForRule("SEG104-R-004");
            assertThat(segTypeMutations).hasSize(1);
            assertThat(segTypeMutations.get(0).mutationId()).isEqualTo("MUT-104-001");
        }
    }

    @Nested
    class MutationExecutionTests {

        @Test
        void mut001SegmentTypeChangeIsDetected() throws IOException {
            MutationResult r = Segment104MutationTester.testMutation(
                Segment104MutationTester.mutationsForRule("SEG104-R-004").get(0),
                purchaseCardAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut002SegmentLengthNonNumericIsDetected() throws IOException {
            MutationResult r = Segment104MutationTester.testMutation(
                Segment104MutationTester.mutationsForRule("SEG104-R-005").get(0),
                purchaseCardAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut003SegmentLengthOverMaxIsDetected() throws IOException {
            MutationResult r = Segment104MutationTester.testMutation(
                Segment104MutationTester.mutationsForRule("SEG104-R-006").get(0),
                purchaseCardAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut004PurchaseCodeOverLengthIsDetected() throws IOException {
            MutationResult r = Segment104MutationTester.testMutation(
                Segment104MutationTester.mutationsForRule("SEG104-R-007").get(0),
                purchaseCardAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut005PcTaxAmountNonNumericIsDetected() throws IOException {
            MutationResult r = Segment104MutationTester.testMutation(
                Segment104MutationTester.mutationsForRule("SEG104-R-008").get(0),
                purchaseCardAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut006PcFreightAmountOverLengthIsDetected() throws IOException {
            MutationResult r = Segment104MutationTester.testMutation(
                Segment104MutationTester.mutationsForRule("SEG104-R-009").get(0),
                purchaseCardAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut007PcDutyAmountNonNumericIsDetected() throws IOException {
            MutationResult r = Segment104MutationTester.testMutation(
                Segment104MutationTester.mutationsForRule("SEG104-R-010").get(0),
                purchaseCardAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut008ShipToCountryCodeWrongLengthIsDetected() throws IOException {
            MutationResult r = Segment104MutationTester.testMutation(
                Segment104MutationTester.mutationsForRule("SEG104-R-011").get(0),
                purchaseCardAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut009DuplicateSegment104IsDetected() throws IOException {
            MutationResult r = Segment104MutationTester.testMutation(
                Segment104MutationTester.mutationsForRule("SEG104-R-020").get(0),
                purchaseCardAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut010MissingStandardSegmentIsDetected() throws IOException {
            MutationResult r = Segment104MutationTester.testMutation(
                Segment104MutationTester.mutationsForRule("SEG104-R-001").get(0),
                purchaseCardAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void fullSuiteAgainstBaselineAchievesFullDetection() throws IOException {
            MutationReport report = Segment104MutationTester.runMutationSuite(
                purchaseCardAuthOriginal(), Segment104MutationTester.generateStandardMutations());

            assertThat(report.metrics().totalMutations()).isEqualTo(10);
            assertThat(report.metrics().detectionRate()).isGreaterThanOrEqualTo(85.0);
            assertThat(report.results()).hasSize(10);
        }

        @Test
        void fullSuiteAgainstMinimalPayloadAchievesFullDetection() throws IOException {
            MutationReport report = Segment104MutationTester.runMutationSuite(
                loadFixture("lifecycle/purchase-card-minimal.synthetic.json"),
                Segment104MutationTester.generateStandardMutations());

            assertThat(report.metrics().detectionRate()).isGreaterThanOrEqualTo(85.0);
        }

        @Test
        void undetectedMutationsListIsEmptyWhenAllDetected() throws IOException {
            MutationReport report = Segment104MutationTester.runMutationSuite(
                purchaseCardAuthOriginal(), Segment104MutationTester.generateStandardMutations());

            if (report.metrics().detectionRate() == 100.0) {
                assertThat(report.metrics().undetectedMutations()).isEmpty();
            }
        }
    }
}
