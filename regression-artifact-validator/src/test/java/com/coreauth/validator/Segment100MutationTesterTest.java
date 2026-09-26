package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.Segment100MutationTester;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100MutationTesterTest {

    private static ObjectNode createValidPayload() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode payload = mapper.createObjectNode();
        ObjectNode request = mapper.createObjectNode();
        ObjectNode dataSection1 = mapper.createObjectNode();
        ObjectNode dataSection2 = mapper.createObjectNode();
        ObjectNode segment = mapper.createObjectNode();

        dataSection1.put("messageFormatVersionIdentifier", "ATL105");
        dataSection1.put("numberOfSegments", "03");
        segment.put("segmentType", "100");
        segment.put("terminalIdentifier", "TERM123");
        segment.put("promptCode", "POS");
        segment.put("sequenceNumber", "100001");
        segment.put("partialApprovalIndicator", "0");
        dataSection2.set("standardSegment", segment);

        request.set("dataSection1", dataSection1);
        request.set("dataSection2", dataSection2);
        payload.set("request", request);

        ObjectNode controls = mapper.createObjectNode();
        controls.put("validateCoreFields", true);
        payload.set("testControls", controls);

        return payload;
    }

    private static CanonicalArtifactPackage createValidPackage() {
        CanonicalTestData testData = new CanonicalTestData();
        testData.setId("TD-VALID");
        testData.setPayload(createValidPayload());

        CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
        pkg.setTestData(List.of(testData));
        return pkg;
    }

    @Nested
    class MutationGenerationTests {
        @Test
        void generatesStandardMutations() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();

            assertThat(mutations).isNotEmpty();
            assertThat(mutations.size()).isGreaterThanOrEqualTo(10);
        }

        @Test
        void eachMutationHasRequiredFields() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();

            for (Segment100MutationTester.MutationCase mutation : mutations) {
                assertThat(mutation.mutationId()).isNotEmpty();
                assertThat(mutation.ruleId()).isNotEmpty();
                assertThat(mutation.ruleTitle()).isNotEmpty();
                assertThat(mutation.mutationType()).isNotEmpty();
                assertThat(mutation.fieldPath()).isNotEmpty();
            }
        }

        @Test
        void mutationsAreDeterministic() {
            List<Segment100MutationTester.MutationCase> mutations1 =
                Segment100MutationTester.generateStandardMutations();
            List<Segment100MutationTester.MutationCase> mutations2 =
                Segment100MutationTester.generateStandardMutations();

            assertThat(mutations1).hasSameSizeAs(mutations2);
            for (int i = 0; i < mutations1.size(); i++) {
                assertThat(mutations1.get(i).mutationId()).isEqualTo(mutations2.get(i).mutationId());
            }
        }
    }

    @Nested
    class MutationDetectionTests {
        @Test
        void testMutationReturnsCorrectStructure() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();
            Segment100MutationTester.MutationCase mutation = mutations.get(0);

            CanonicalArtifactPackage pkg = createValidPackage();
            Segment100MutationTester.MutationResult result =
                Segment100MutationTester.testMutation(mutation, pkg);

            assertThat(result.mutation()).isEqualTo(mutation);
            assertThat(result.detected()).isNotNull();
            assertThat(result.detectionReason()).isNotEmpty();
        }

        @Test
        void testMutationRunsValidator() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();

            CanonicalArtifactPackage pkg = createValidPackage();

            // Test multiple mutations to ensure validator is invoked
            for (Segment100MutationTester.MutationCase mutation : mutations.subList(0, 3)) {
                Segment100MutationTester.MutationResult result =
                    Segment100MutationTester.testMutation(mutation, pkg);

                assertThat(result).isNotNull();
                assertThat(result.mutation()).isNotNull();
                assertThat(result.detectionReason()).isNotEmpty();
            }
        }

        @Test
        void mutationPreservesRuleId() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();
            Segment100MutationTester.MutationCase mutation = mutations.get(0);

            CanonicalArtifactPackage pkg = createValidPackage();
            Segment100MutationTester.MutationResult result =
                Segment100MutationTester.testMutation(mutation, pkg);

            assertThat(result.mutation().ruleId()).isEqualTo(mutation.ruleId());
            assertThat(result.mutation().mutationType()).isEqualTo(mutation.mutationType());
        }
    }

    @Nested
    class MutationSuiteTests {
        @Test
        void runsMutationSuite() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();
            CanonicalArtifactPackage pkg = createValidPackage();

            Segment100MutationTester.MutationReport report =
                Segment100MutationTester.runMutationSuite(pkg, mutations);

            assertThat(report.results()).hasSize(mutations.size());
            assertThat(report.metrics()).isNotNull();
        }

        @Test
        void computesDetectionMetrics() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();
            CanonicalArtifactPackage pkg = createValidPackage();

            Segment100MutationTester.MutationReport report =
                Segment100MutationTester.runMutationSuite(pkg, mutations);

            Segment100MutationTester.MutationMetrics metrics = report.metrics();
            assertThat(metrics.totalMutations()).isEqualTo(mutations.size());
            assertThat(metrics.detectedMutations()).isGreaterThanOrEqualTo(0);
            assertThat(metrics.missedMutations()).isGreaterThanOrEqualTo(0);
            assertThat(metrics.detectionRate()).isBetween(0.0, 100.0);
            assertThat(metrics.detectedMutations() + metrics.missedMutations())
                .isEqualTo(metrics.totalMutations());
        }

        @Test
        void tracksUndetectedMutations() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();
            CanonicalArtifactPackage pkg = createValidPackage();

            Segment100MutationTester.MutationReport report =
                Segment100MutationTester.runMutationSuite(pkg, mutations);

            if (report.metrics().missedMutations() > 0) {
                assertThat(report.metrics().undetectedMutations()).isNotEmpty();
                assertThat(report.metrics().undetectedMutations().size())
                    .isEqualTo(report.metrics().missedMutations());
            } else {
                assertThat(report.metrics().undetectedMutations()).isEmpty();
            }
        }

        @Test
        void generatesSessionId() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();
            CanonicalArtifactPackage pkg = createValidPackage();

            Segment100MutationTester.MutationReport report =
                Segment100MutationTester.runMutationSuite(pkg, mutations);

            assertThat(report.testSessionId()).isNotEmpty();
            assertThat(report.testSessionId()).startsWith("mutation-session-");
        }

        @Test
        void runsMultipleMutationsIndependently() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();
            CanonicalArtifactPackage pkg = createValidPackage();

            Segment100MutationTester.MutationReport report =
                Segment100MutationTester.runMutationSuite(pkg, mutations);

            // Each mutation should have a result
            assertThat(report.results()).hasSize(mutations.size());

            // Each result should be independent (mutation ID should match)
            for (int i = 0; i < report.results().size(); i++) {
                assertThat(report.results().get(i).mutation().mutationId())
                    .isEqualTo(mutations.get(i).mutationId());
            }
        }
    }

    @Nested
    class MutationCategoryTests {
        @Test
        void hasFieldValueMutations() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();

            long fieldValueCount = mutations.stream()
                .filter(m -> "field-value".equals(m.mutationType()))
                .count();

            assertThat(fieldValueCount).isGreaterThan(0);
        }

        @Test
        void hasFieldFormatMutations() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();

            long fieldFormatCount = mutations.stream()
                .filter(m -> "field-format".equals(m.mutationType()))
                .count();

            assertThat(fieldFormatCount).isGreaterThan(0);
        }

        @Test
        void hasFieldOmissionMutations() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();

            long fieldOmissionCount = mutations.stream()
                .filter(m -> "field-omission".equals(m.mutationType()))
                .count();

            assertThat(fieldOmissionCount).isGreaterThan(0);
        }

        @Test
        void mutationsHaveDistinctRuleIds() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();

            long uniqueRules = mutations.stream()
                .map(Segment100MutationTester.MutationCase::ruleId)
                .distinct()
                .count();

            assertThat(uniqueRules).isGreaterThan(1);
        }
    }

    @Nested
    class MutationEdgeCaseTests {
        @Test
        void handlesNullPackage() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();
            Segment100MutationTester.MutationCase mutation = mutations.get(0);

            Segment100MutationTester.MutationResult result =
                Segment100MutationTester.testMutation(mutation, null);

            // Should still process (use default mutated data)
            assertThat(result).isNotNull();
            assertThat(result.mutation()).isEqualTo(mutation);
        }

        @Test
        void handlesEmptyPackage() {
            List<Segment100MutationTester.MutationCase> mutations =
                Segment100MutationTester.generateStandardMutations();
            Segment100MutationTester.MutationCase mutation = mutations.get(0);

            CanonicalArtifactPackage emptyPkg = new CanonicalArtifactPackage();

            Segment100MutationTester.MutationResult result =
                Segment100MutationTester.testMutation(mutation, emptyPkg);

            // Should still process (use default mutated data)
            assertThat(result).isNotNull();
            assertThat(result.mutation()).isEqualTo(mutation);
        }

        @Test
        void handlesEmptyMutationList() {
            CanonicalArtifactPackage pkg = createValidPackage();
            List<Segment100MutationTester.MutationCase> emptyMutations = List.of();

            Segment100MutationTester.MutationReport report =
                Segment100MutationTester.runMutationSuite(pkg, emptyMutations);

            assertThat(report.results()).isEmpty();
            assertThat(report.metrics().totalMutations()).isZero();
        }
    }
}
