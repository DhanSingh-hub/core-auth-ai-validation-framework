package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment103MutationTester;
import com.coreauth.validator.canonical.Segment103MutationTester.MutationCase;
import com.coreauth.validator.canonical.Segment103MutationTester.MutationResult;
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
 * Item 5 (Mutation Framework Definition) tests for {@link Segment103MutationTester}.
 */
class Segment103MutationTesterTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path SEG103_ROOT = Paths.get("test-input", "ai-solution", "test-data", "segment-103");

    private JsonNode loadFixture(String relativePath) throws IOException {
        return new Segment103MutationTester().loadPayload(SEG103_ROOT.resolve(relativePath));
    }

    private JsonNode ebtPurchaseOriginal() throws IOException {
        return loadFixture("lifecycle/ebt-purchase-original.synthetic.json");
    }

    private JsonNode ewicPurchaseCompletion() throws IOException {
        return loadFixture("lifecycle/ewic-purchase-completion.synthetic.json");
    }

    @Nested
    class MutationDefinitionTests {

        @Test
        void defines10StandardMutations() {
            List<MutationCase> mutations = Segment103MutationTester.generateStandardMutations();
            assertThat(mutations).hasSize(10);
        }

        @Test
        void everyMutationHasSeg103RuleAnchor() {
            List<MutationCase> mutations = Segment103MutationTester.generateStandardMutations();
            assertThat(mutations).allMatch(m -> m.ruleId().startsWith("SEG103-R-"));
        }

        @Test
        void everyMutationDeclaresShouldDetectTrue() {
            List<MutationCase> mutations = Segment103MutationTester.generateStandardMutations();
            assertThat(mutations).allMatch(MutationCase::shouldDetect);
        }

        @Test
        void mutationIdsAreUnique() {
            List<String> ids = Segment103MutationTester.allMutationIds();
            assertThat(ids).doesNotHaveDuplicates().hasSize(10);
        }

        @Test
        void mutationCategoriesIncludeFieldValueFormatAndStructural() {
            List<MutationCase> mutations = Segment103MutationTester.generateStandardMutations();
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("field-value"));
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("field-format"));
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("structural"));
        }

        @Test
        void mutationsForRuleReturnsFilteredSet() {
            List<MutationCase> segTypeMutations = Segment103MutationTester.mutationsForRule("SEG103-R-003");
            assertThat(segTypeMutations).hasSize(1);
            assertThat(segTypeMutations.get(0).mutationId()).isEqualTo("MUT-103-001");
        }
    }

    @Nested
    class MutationExecutionTests {

        @Test
        void mut001SegmentTypeChangeIsDetected() throws IOException {
            MutationResult r = Segment103MutationTester.testMutation(
                Segment103MutationTester.mutationsForRule("SEG103-R-003").get(0),
                ebtPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut002SegmentLengthNonNumericIsDetected() throws IOException {
            MutationResult r = Segment103MutationTester.testMutation(
                Segment103MutationTester.mutationsForRule("SEG103-R-004").get(0),
                ebtPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut003SegmentLengthOverMaximumIsDetected() throws IOException {
            MutationResult r = Segment103MutationTester.testMutation(
                Segment103MutationTester.mutationsForRule("SEG103-R-005").get(0),
                ebtPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut004ClerkIdNonNumericIsDetected() throws IOException {
            MutationResult r = Segment103MutationTester.testMutation(
                Segment103MutationTester.mutationsForRule("SEG103-R-009").get(0),
                ebtPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut005VoucherIdNonNumericIsDetected() throws IOException {
            MutationResult r = Segment103MutationTester.testMutation(
                Segment103MutationTester.mutationsForRule("SEG103-R-010").get(0),
                ebtPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut006WicDiscountAmountBadFormatIsDetected() throws IOException {
            MutationResult r = Segment103MutationTester.testMutation(
                Segment103MutationTester.mutationsForRule("SEG103-R-011").get(0),
                ewicPurchaseCompletion()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut007WicProductDataTotalLengthMismatchIsDetected() throws IOException {
            MutationResult r = Segment103MutationTester.testMutation(
                Segment103MutationTester.mutationsForRule("SEG103-R-013").get(0),
                ebtPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut008UnknownEbtProgramTagIsDetected() throws IOException {
            MutationResult r = Segment103MutationTester.testMutation(
                Segment103MutationTester.mutationsForRule("SEG103-R-016").get(0),
                ebtPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut009MissingAccountTypeIsDetected() throws IOException {
            MutationResult r = Segment103MutationTester.testMutation(
                Segment103MutationTester.mutationsForRule("SEG103-R-017").get(0),
                ewicPurchaseCompletion()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut010DuplicateSegmentIsDetected() throws IOException {
            MutationResult r = Segment103MutationTester.testMutation(
                Segment103MutationTester.mutationsForRule("SEG103-R-018").get(0),
                ebtPurchaseOriginal()
            );
            assertThat(r.detected()).isTrue();
        }
    }
}
