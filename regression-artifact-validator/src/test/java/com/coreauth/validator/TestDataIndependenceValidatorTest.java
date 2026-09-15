package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.SourceAnchor;
import com.coreauth.validator.canonical.TestDataIndependenceValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TestDataIndependenceValidatorTest {

    private static SourceAnchor anchor(String specification, String version, String section, String rule) {
        SourceAnchor anchor = new SourceAnchor();
        anchor.setSpecification(specification);
        anchor.setVersion(version);
        anchor.setSection(section);
        anchor.setRule(rule);
        return anchor;
    }

    private static CanonicalTestData testData(String id, List<SourceAnchor> anchors,
                                               String expectedValidation, ObjectNode payload) {
        CanonicalTestData data = new CanonicalTestData();
        data.setId(id);
        data.setSourceAnchors(anchors);
        data.setExpectedValidation(expectedValidation);
        data.setPayload(payload);
        data.setTestCaseIds(List.of("TC-001"));
        return data;
    }

    @Nested
    class IndependenceTests {
        @Test
        void passesValidTestData() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode payload = mapper.createObjectNode();
            payload.put("request", "test-data");

            CanonicalTestData data = testData("TD-001",
                    List.of(anchor("ATL105", "2026-3", "12.1", "sequence-number")),
                    "PASS", payload);

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setTestData(List.of(data));

            ValidationResult result = new TestDataIndependenceValidator().validate(pkg);

            assertThat(result.errors()).isEmpty();
        }

        @Test
        void rejectsTestDataWithoutSourceAnchors() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode payload = mapper.createObjectNode();
            payload.put("request", "test-data");

            CanonicalTestData data = testData("TD-002", null, "PASS", payload);

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setTestData(List.of(data));

            ValidationResult result = new TestDataIndependenceValidator().validate(pkg);

            assertThat(result.errors()).hasSize(1);
            assertThat(result.errors()).anyMatch(error -> error.reason().contains("no source anchors"));
        }

        @Test
        void rejectsTestDataWithoutTestCaseLinks() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode payload = mapper.createObjectNode();
            payload.put("request", "test-data");

            CanonicalTestData data = testData("TD-003",
                    List.of(anchor("ATL105", "2026-3", "12.1", "sequence-number")),
                    "PASS", payload);
            data.setTestCaseIds(null);

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setTestData(List.of(data));

            ValidationResult result = new TestDataIndependenceValidator().validate(pkg);

            assertThat(result.errors()).hasSize(1);
            assertThat(result.errors()).anyMatch(error -> error.reason().contains("not linked to any test case"));
        }

        @Test
        void rejectsTestDataWithoutExpectedValidation() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode payload = mapper.createObjectNode();
            payload.put("request", "test-data");

            CanonicalTestData data = testData("TD-004",
                    List.of(anchor("ATL105", "2026-3", "12.1", "sequence-number")),
                    null, payload);

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setTestData(List.of(data));

            ValidationResult result = new TestDataIndependenceValidator().validate(pkg);

            assertThat(result.errors()).hasSize(1);
            assertThat(result.errors()).anyMatch(error -> error.reason().contains("does not declare expectedValidation"));
        }

        @Test
        void rejectsInvalidExpectedValidation() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode payload = mapper.createObjectNode();
            payload.put("request", "test-data");

            CanonicalTestData data = testData("TD-005",
                    List.of(anchor("ATL105", "2026-3", "12.1", "sequence-number")),
                    "MAYBE", payload);

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setTestData(List.of(data));

            ValidationResult result = new TestDataIndependenceValidator().validate(pkg);

            assertThat(result.errors()).hasSize(1);
            assertThat(result.errors()).anyMatch(error -> error.reason().contains("invalid expectedValidation"));
        }

        @Test
        void acceptsValidExpectationValues() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode payload = mapper.createObjectNode();
            payload.put("request", "test-data");

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            String[] validExpectations = {"PASS", "FAIL", "REVIEW", "PASS_WITH_REVIEW"};

            for (String expectation : validExpectations) {
                CanonicalTestData data = testData("TD-" + expectation,
                        List.of(anchor("ATL105", "2026-3", "12.1", "sequence-number")),
                        expectation, payload);
                pkg.setTestData(List.of(data));

                ValidationResult result = new TestDataIndependenceValidator().validate(pkg);
                assertThat(result.errors()).isEmpty();
            }
        }

        @Test
        void rejectsTestDataWithoutPayload() throws Exception {
            CanonicalTestData data = testData("TD-006",
                    List.of(anchor("ATL105", "2026-3", "12.1", "sequence-number")),
                    "PASS", null);

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setTestData(List.of(data));

            ValidationResult result = new TestDataIndependenceValidator().validate(pkg);

            assertThat(result.errors()).hasSize(1);
            assertThat(result.errors()).anyMatch(error -> error.reason().contains("no payload"));
        }

        @Test
        void rejectsTestDataWithEmptyPayload() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode emptyPayload = mapper.createObjectNode();

            CanonicalTestData data = testData("TD-007",
                    List.of(anchor("ATL105", "2026-3", "12.1", "sequence-number")),
                    "PASS", emptyPayload);

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setTestData(List.of(data));

            ValidationResult result = new TestDataIndependenceValidator().validate(pkg);

            assertThat(result.errors()).hasSize(1);
            assertThat(result.errors()).anyMatch(error -> error.reason().contains("empty payload"));
        }

        @Test
        void rejectsIncompleteSourceAnchor() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode payload = mapper.createObjectNode();
            payload.put("request", "test-data");

            SourceAnchor incompleteAnchor = new SourceAnchor();
            incompleteAnchor.setSpecification("ATL105");
            // Missing rule

            CanonicalTestData data = testData("TD-008", List.of(incompleteAnchor), "PASS", payload);

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setTestData(List.of(data));

            ValidationResult result = new TestDataIndependenceValidator().validate(pkg);

            assertThat(result.errors()).hasSize(1);
            assertThat(result.errors()).anyMatch(error -> error.reason().contains("lacks rule identifier"));
        }

        @Test
        void handlesNullTestDataList() {
            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            pkg.setTestData(null);

            ValidationResult result = new TestDataIndependenceValidator().validate(pkg);

            assertThat(result.errors()).isEmpty();
        }

        @Test
        void skipsNullTestDataItems() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode payload = mapper.createObjectNode();
            payload.put("request", "test-data");

            CanonicalTestData validData = testData("TD-009",
                    List.of(anchor("ATL105", "2026-3", "12.1", "sequence-number")),
                    "PASS", payload);

            CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
            var testDataList = new java.util.ArrayList<CanonicalTestData>();
            testDataList.add(null);
            testDataList.add(validData);
            testDataList.add(null);
            pkg.setTestData(testDataList);

            ValidationResult result = new TestDataIndependenceValidator().validate(pkg);

            assertThat(result.errors()).isEmpty();
        }
    }
}
