package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment108MutationTester;
import com.coreauth.validator.canonical.Segment108MutationTester.MutationCase;
import com.coreauth.validator.canonical.Segment108MutationTester.MutationResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 5 (Mutation Framework Definition) tests for {@link Segment108MutationTester}.
 */
class Segment108MutationTesterTest {

    private static final Path SEG108_ROOT = Paths.get("specifications", "ATL105", "test-input", "ai-solution", "test-data", "segment-108");

    private JsonNode loadFixture(String relativePath) throws IOException {
        return new Segment108MutationTester().loadPayload(SEG108_ROOT.resolve(relativePath));
    }

    private JsonNode loyaltyPurchaseOriginal() throws IOException {
        return loadFixture("lifecycle/loyalty-purchase-original.synthetic.json");
    }

    @Nested
    class MutationDefinitionTests {

        @Test
        void defines10StandardMutations() {
            List<MutationCase> mutations = Segment108MutationTester.generateStandardMutations();
            assertThat(mutations).hasSize(10);
        }

        @Test
        void everyMutationHasSeg108RuleAnchor() {
            List<MutationCase> mutations = Segment108MutationTester.generateStandardMutations();
            assertThat(mutations).allMatch(m -> m.ruleId().startsWith("SEG108-R-"));
        }

        @Test
        void everyMutationDeclaresShouldDetectTrue() {
            List<MutationCase> mutations = Segment108MutationTester.generateStandardMutations();
            assertThat(mutations).allMatch(MutationCase::shouldDetect);
        }

        @Test
        void mutationIdsAreUnique() {
            List<String> ids = Segment108MutationTester.allMutationIds();
            assertThat(ids).doesNotHaveDuplicates().hasSize(10);
        }

        @Test
        void mutationCategoriesIncludeFieldValueFormatAndStructural() {
            List<MutationCase> mutations = Segment108MutationTester.generateStandardMutations();
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("field-value"));
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("field-format"));
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("structural"));
        }

        @Test
        void mutationsForRuleReturnsFilteredSet() {
            List<MutationCase> segTypeMutations = Segment108MutationTester.mutationsForRule("SEG108-R-003");
            assertThat(segTypeMutations).hasSize(1);
            assertThat(segTypeMutations.get(0).mutationId()).isEqualTo("MUT-108-001");
        }
    }

    @Nested
    class MutationExecutionTests {

        @Test
        void mut001SegmentTypeChangeIsDetected() throws IOException {
            MutationResult r = Segment108MutationTester.testMutation(
                Segment108MutationTester.mutationsForRule("SEG108-R-003").get(0),
                loyaltyPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut002SegmentLengthNonNumericIsDetected() throws IOException {
            MutationResult r = Segment108MutationTester.testMutation(
                Segment108MutationTester.mutationsForRule("SEG108-R-004").get(0),
                loyaltyPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut003SegmentLengthOverMaximumIsDetected() throws IOException {
            MutationResult r = Segment108MutationTester.testMutation(
                Segment108MutationTester.mutationsForRule("SEG108-R-005").get(0),
                loyaltyPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut004LoyaltyProgramIdNonNumericIsDetected() throws IOException {
            MutationResult r = Segment108MutationTester.testMutation(
                Segment108MutationTester.mutationsForRule("SEG108-R-008").get(0),
                loyaltyPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut005LoyaltyAccountNumberNonNumericIsDetected() throws IOException {
            MutationResult r = Segment108MutationTester.testMutation(
                Segment108MutationTester.mutationsForRule("SEG108-R-009").get(0),
                loyaltyPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut006UpdateCodeInvalidValueIsDetected() throws IOException {
            MutationResult r = Segment108MutationTester.testMutation(
                Segment108MutationTester.mutationsForRule("SEG108-R-013").get(0),
                loyaltyPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut007PaymentTenderTypeInvalidValueIsDetected() throws IOException {
            MutationResult r = Segment108MutationTester.testMutation(
                Segment108MutationTester.mutationsForRule("SEG108-R-017").get(0),
                loyaltyPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut008LoyaltyTrack2DataOverMaximumIsDetected() throws IOException {
            MutationResult r = Segment108MutationTester.testMutation(
                Segment108MutationTester.mutationsForRule("SEG108-R-018").get(0),
                loyaltyPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut009LoyaltyInformationVersionInvalidValueIsDetected() throws IOException {
            MutationResult r = Segment108MutationTester.testMutation(
                Segment108MutationTester.mutationsForRule("SEG108-R-019").get(0),
                loyaltyPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut010DuplicateSegmentIsDetected() throws IOException {
            MutationResult r = Segment108MutationTester.testMutation(
                Segment108MutationTester.mutationsForRule("SEG108-R-021").get(0),
                loyaltyPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }
    }
}
