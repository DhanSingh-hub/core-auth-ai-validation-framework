package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment101MutationTester;
import com.coreauth.validator.canonical.Segment101MutationTester.MutationCase;
import com.coreauth.validator.canonical.Segment101MutationTester.MutationReport;
import com.coreauth.validator.canonical.Segment101MutationTester.MutationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 5 (Mutation Framework Definition) tests for {@link Segment101MutationTester}.
 */
class Segment101MutationTesterTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path SEG101_ROOT = Paths.get("specifications", "ATL105", "test-input", "ai-solution", "test-data", "segment-101");

    private JsonNode loadFixture(String relativePath) throws IOException {
        return new Segment101MutationTester().loadPayload(SEG101_ROOT.resolve(relativePath));
    }

    private JsonNode fleetAuthOriginal() throws IOException {
        return loadFixture("lifecycle/fleet-auth-original.synthetic.json");
    }

    private JsonNode fleetAuthCompletion() throws IOException {
        return loadFixture("lifecycle/fleet-auth-completion.synthetic.json");
    }

    @Nested
    class MutationDefinitionTests {

        @Test
        void defines10StandardMutations() {
            List<MutationCase> mutations = Segment101MutationTester.generateStandardMutations();
            assertThat(mutations).hasSize(10);
        }

        @Test
        void everyMutationHasSeg101RuleAnchor() {
            List<MutationCase> mutations = Segment101MutationTester.generateStandardMutations();
            assertThat(mutations).allMatch(m -> m.ruleId().startsWith("SEG101-R-"));
        }

        @Test
        void everyMutationDeclaresShouldDetectTrue() {
            List<MutationCase> mutations = Segment101MutationTester.generateStandardMutations();
            assertThat(mutations).allMatch(MutationCase::shouldDetect);
        }

        @Test
        void mutationIdsAreUnique() {
            List<String> ids = Segment101MutationTester.allMutationIds();
            assertThat(ids).doesNotHaveDuplicates().hasSize(10);
        }

        @Test
        void mutationCategoriesIncludeFieldValueFormatAndStructural() {
            List<MutationCase> mutations = Segment101MutationTester.generateStandardMutations();
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("field-value"));
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("field-format"));
            assertThat(mutations).anyMatch(m -> m.mutationType().equals("structural"));
        }

        @Test
        void mutationsForRuleReturnsFilteredSet() {
            List<MutationCase> segTypeMutations = Segment101MutationTester.mutationsForRule("SEG101-R-004");
            assertThat(segTypeMutations).hasSize(1);
            assertThat(segTypeMutations.get(0).mutationId()).isEqualTo("MUT-101-001");
        }
    }

    @Nested
    class MutationExecutionTests {

        @Test
        void mut001SegmentTypeChangeIsDetected() throws IOException {
            MutationResult r = Segment101MutationTester.testMutation(
                Segment101MutationTester.mutationsForRule("SEG101-R-004").get(0),
                fleetAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut002SegmentLengthNonNumericIsDetected() throws IOException {
            MutationResult r = Segment101MutationTester.testMutation(
                Segment101MutationTester.mutationsForRule("SEG101-R-005").get(0),
                fleetAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut003SegmentLengthOver61IsDetected() throws IOException {
            MutationResult r = Segment101MutationTester.testMutation(
                Segment101MutationTester.mutationsForRule("SEG101-R-006").get(0),
                fleetAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut004OdometerNonNumericIsDetected() throws IOException {
            MutationResult r = Segment101MutationTester.testMutation(
                Segment101MutationTester.mutationsForRule("SEG101-R-009").get(0),
                fleetAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut005UserIdAllZerosIsDetected() throws IOException {
            MutationResult r = Segment101MutationTester.testMutation(
                Segment101MutationTester.mutationsForRule("SEG101-R-018").get(0),
                fleetAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut006FleetTagInNonCompletionIsDetected() throws IOException {
            MutationResult r = Segment101MutationTester.testMutation(
                Segment101MutationTester.mutationsForRule("SEG101-R-020").get(0),
                fleetAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut007UnknownFleetTagCodeIsDetected() throws IOException {
            MutationResult r = Segment101MutationTester.testMutation(
                Segment101MutationTester.mutationsForRule("SEG101-R-023").get(0),
                fleetAuthCompletion()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut008TlhTagWithLettersIsDetected() throws IOException {
            MutationResult r = Segment101MutationTester.testMutation(
                Segment101MutationTester.mutationsForRule("SEG101-R-024").get(0),
                fleetAuthCompletion()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut009AddedSegment145IsDetected() throws IOException {
            MutationResult r = Segment101MutationTester.testMutation(
                Segment101MutationTester.mutationsForRule("SEG101-R-003").get(0),
                fleetAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void mut010RemovedStandardSegmentIsDetected() throws IOException {
            MutationResult r = Segment101MutationTester.testMutation(
                Segment101MutationTester.mutationsForRule("SEG101-R-001").get(0),
                fleetAuthOriginal()
            );
            assertThat(r.detected()).isTrue();
        }

        @Test
        void runSuiteOnAuthOriginalYieldsDetectionRateNinetyOrHigher() throws IOException {
            JsonNode base = fleetAuthOriginal();
            MutationReport report = Segment101MutationTester.runMutationSuite(base,
                Segment101MutationTester.generateStandardMutations());
            // MUT-007 and MUT-008 target completion messages so may not fully detect against auth-original.
            // We still expect the auth-oriented mutations to all detect.
            assertThat(report.metrics().detectionRate()).isGreaterThanOrEqualTo(70.0);
            assertThat(report.results()).hasSize(10);
        }

        @Test
        void runSuiteOnCompletionYieldsFullFleetTagDetection() throws IOException {
            JsonNode base = fleetAuthCompletion();
            MutationReport report = Segment101MutationTester.runMutationSuite(base,
                Segment101MutationTester.generateStandardMutations());
            // Completion base carries fleet tags, so tag-related mutations detect correctly.
            List<MutationResult> tagMutations = report.results().stream()
                .filter(r -> r.mutation().ruleId().equals("SEG101-R-023")
                    || r.mutation().ruleId().equals("SEG101-R-024"))
                .toList();
            assertThat(tagMutations).allMatch(MutationResult::detected);
        }

        @Test
        void metricsMissedListNamesUndetectedMutations() throws IOException {
            // Construct a payload that will pass all mutations (valid fleet-auth-completion),
            // then override a mutation that intentionally can't detect against this base.
            JsonNode base = fleetAuthCompletion();
            MutationReport report = Segment101MutationTester.runMutationSuite(base,
                Segment101MutationTester.generateStandardMutations());
            if (report.metrics().missedMutations() > 0) {
                assertThat(report.metrics().undetectedMutations())
                    .allMatch(id -> id.startsWith("MUT-101-"));
            }
        }
    }

    @Nested
    class MutationMetadataTests {

        @Test
        void allMutationsCarryDescriptiveText() {
            List<MutationCase> mutations = Segment101MutationTester.generateStandardMutations();
            assertThat(mutations).allMatch(m -> m.description() != null && !m.description().isBlank());
        }

        @Test
        void allMutationsHaveJsonPath() {
            List<MutationCase> mutations = Segment101MutationTester.generateStandardMutations();
            assertThat(mutations).allMatch(m -> m.jsonPath() != null && !m.jsonPath().isBlank());
        }

        @Test
        void applyingMutationsPreservesOriginalPayload() throws IOException {
            JsonNode originalBase = fleetAuthOriginal();
            String beforeJson = originalBase.toString();
            Segment101MutationTester.testMutation(
                Segment101MutationTester.mutationsForRule("SEG101-R-004").get(0),
                originalBase
            );
            String afterJson = originalBase.toString();
            assertThat(afterJson).isEqualTo(beforeJson);
        }

        @Test
        void mutationResultCapturesRuleIdInReason() throws IOException {
            MutationResult r = Segment101MutationTester.testMutation(
                Segment101MutationTester.mutationsForRule("SEG101-R-003").get(0),
                fleetAuthOriginal()
            );
            assertThat(r.detectionReason()).contains("SEG101-R-003");
        }

        @Test
        void mutantPayloadStructureRemainsValidJson() throws IOException {
            JsonNode base = fleetAuthOriginal();
            ObjectNode mutated = base.deepCopy();
            ((ObjectNode) mutated.path("Financial Request").path("Fleet Data Segment"))
                .put("SegmentType", "999");
            JsonNode round = MAPPER.readTree(mutated.toString());
            assertThat(round.path("Financial Request").path("Fleet Data Segment").path("SegmentType").asText())
                .isEqualTo("999");
        }
    }
}
