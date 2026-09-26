package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment113MutationTester;
import com.coreauth.validator.canonical.Segment113MutationTester.MutationCase;
import com.coreauth.validator.canonical.Segment113MutationTester.MutationResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 5 (Mutation Framework Definition) tests for {@link Segment113MutationTester}.
 */
class Segment113MutationTesterTest {

    private static final Path SEG113_ROOT = Paths.get("specifications", "ATL105", "test-input", "ai-solution", "test-data", "segment-113");

    private JsonNode loadFixture(String relativePath) throws IOException {
        return new Segment113MutationTester().loadPayload(SEG113_ROOT.resolve(relativePath));
    }

    private JsonNode checkPurchaseOriginal() throws IOException {
        return loadFixture("lifecycle/ecatelecheck-check-purchase-original.synthetic.json");
    }

    @Nested
    class MutationDefinitionTests {

        @Test
        void defines10StandardMutations() {
            List<MutationCase> mutations = Segment113MutationTester.generateStandardMutations();
            assertThat(mutations).hasSize(10);
        }

        @Test
        void everyMutationHasSeg113RuleAnchor() {
            List<MutationCase> mutations = Segment113MutationTester.generateStandardMutations();
            assertThat(mutations).allMatch(m -> m.ruleId().startsWith("SEG113-R-"));
        }

        @Test
        void everyMutationDeclaresShouldDetectTrue() {
            List<MutationCase> mutations = Segment113MutationTester.generateStandardMutations();
            assertThat(mutations).allMatch(MutationCase::shouldDetect);
        }

        @Test
        void mutationIdsAreUnique() {
            List<String> ids = Segment113MutationTester.allMutationIds();
            assertThat(ids).doesNotHaveDuplicates().hasSize(10);
        }

        @Test
        void mutationCategoriesIncludeFieldValueFormatAndStructural() {
            List<MutationCase> mutations = Segment113MutationTester.generateStandardMutations();
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("field-value"));
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("field-format"));
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("structural"));
        }

        @Test
        void mutationsForRuleReturnsFilteredSet() {
            List<MutationCase> segTypeMutations = Segment113MutationTester.mutationsForRule("SEG113-R-003");
            assertThat(segTypeMutations).hasSize(1);
            assertThat(segTypeMutations.get(0).mutationId()).isEqualTo("MUT-113-001");
        }
    }

    @Nested
    class MutationExecutionTests {

        @Test
        void mut001SegmentTypeChangeIsDetected() throws IOException {
            MutationResult r = Segment113MutationTester.testMutation(
                Segment113MutationTester.mutationsForRule("SEG113-R-003").get(0),
                checkPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut002SegmentLengthNonNumericIsDetected() throws IOException {
            MutationResult r = Segment113MutationTester.testMutation(
                Segment113MutationTester.mutationsForRule("SEG113-R-004").get(0),
                checkPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut003SegmentLengthOverMaximumIsDetected() throws IOException {
            MutationResult r = Segment113MutationTester.testMutation(
                Segment113MutationTester.mutationsForRule("SEG113-R-005").get(0),
                checkPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut004ClerkIdInvalidCharacterIsDetected() throws IOException {
            MutationResult r = Segment113MutationTester.testMutation(
                Segment113MutationTester.mutationsForRule("SEG113-R-008").get(0),
                checkPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut005ProductCodeInvalidCharacterIsDetected() throws IOException {
            MutationResult r = Segment113MutationTester.testMutation(
                Segment113MutationTester.mutationsForRule("SEG113-R-009").get(0),
                checkPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut006PhoneNumberNonNumericIsDetected() throws IOException {
            MutationResult r = Segment113MutationTester.testMutation(
                Segment113MutationTester.mutationsForRule("SEG113-R-010").get(0),
                checkPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut007TraceIdOverMaximumIsDetected() throws IOException {
            MutationResult r = Segment113MutationTester.testMutation(
                Segment113MutationTester.mutationsForRule("SEG113-R-011").get(0),
                checkPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut008MerchantTraceIdOverMaximumIsDetected() throws IOException {
            MutationResult r = Segment113MutationTester.testMutation(
                Segment113MutationTester.mutationsForRule("SEG113-R-012").get(0),
                checkPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut009ExtendedMicrDataOverMaximumIsDetected() throws IOException {
            MutationResult r = Segment113MutationTester.testMutation(
                Segment113MutationTester.mutationsForRule("SEG113-R-014").get(0),
                checkPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut010DuplicateSegmentIsDetected() throws IOException {
            MutationResult r = Segment113MutationTester.testMutation(
                Segment113MutationTester.mutationsForRule("SEG113-R-015").get(0),
                checkPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }
    }
}
