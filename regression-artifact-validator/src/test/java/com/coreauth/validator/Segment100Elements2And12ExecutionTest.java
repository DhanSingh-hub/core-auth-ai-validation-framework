package com.coreauth.validator;

import com.coreauth.validator.canonical.AccountNumberEntryValidator;
import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalPackageLoader;
import com.coreauth.validator.canonical.CanonicalTestCase;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.coverage.GenerateSegment100AccountEntryPackage;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100Elements2And12ExecutionTest {
    private static final String PACKAGE_FILE = "segment-100-elements-2-and-12-execution-package.json";
    private final Path root = Path.of(System.getProperty("user.dir"));
    private final Path packageFile = root.resolve("specifications/ATL105/test-output/test-json").resolve(PACKAGE_FILE);
    private final Path sourceEvidenceRoot = root.resolve("specifications/ATL105");

    @Test
    void packageIsReproducibleAndExecutionReady() throws Exception {
        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(packageFile);
        ValidationResult readiness = new CanonicalTraceabilityValidator()
                .validateForExecution(artifactPackage, sourceEvidenceRoot);
        assertThat(readiness.errors()).isEmpty();
        assertThat(artifactPackage.getBusinessRequirements()).hasSize(13);
        assertThat(artifactPackage.getTestCases()).hasSize(42);
        assertThat(artifactPackage.getTestData()).hasSize(42);
        assertThat(new ObjectMapper().readTree(packageFile.toFile()))
                .isEqualTo(new GenerateSegment100AccountEntryPackage().generate());
        assertThat(artifactPackage.getTestData()).extracting(CanonicalTestData::getId).doesNotHaveDuplicates();
    }

    @TestFactory
    Stream<DynamicTest> everySyntheticFixtureMatchesItsIndependentAssertion() throws Exception {
        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(packageFile);
        Map<String, CanonicalTestCase> casesById = artifactPackage.getTestCases().stream()
                .collect(Collectors.toMap(CanonicalTestCase::getId, Function.identity()));
        AccountNumberEntryValidator validator = new AccountNumberEntryValidator();
        return artifactPackage.getTestData().stream().map(data -> DynamicTest.dynamicTest(data.getId(), () -> {
            assertThat(data.getTestCaseIds()).as(data.getId()).hasSize(1);
            CanonicalTestCase testCase = casesById.get(data.getTestCaseIds().getFirst());
            assertThat(testCase).as(data.getId()).isNotNull();
            assertThat(testCase.getExpectedOutcome()).isEqualTo(data.getExpectedValidation());
            assertThat(testCase.getAssertions()).singleElement().satisfies(assertion ->
                    assertThat(assertion.getExpectedResult()).isEqualTo(data.getExpectedValidation()));
            assertThat(data.getResponse().path("hostExecution").asText()).isEqualTo("NOT_EXECUTED");
            assertThat(data.getResponse().path("scope").asText()).isEqualTo("LOCAL_PREDICATE_EXPECTATION");

            ValidationResult observed = validator.validateTestData(data);
            if ("PASS".equals(data.getExpectedValidation())) {
                assertThat(observed.errors()).as(data.getId()).isEmpty();
            } else {
                assertThat(data.getExpectedValidation()).isEqualTo("FAIL");
                assertThat(observed.errors()).as(data.getId()).isNotEmpty().allSatisfy(error -> {
                    assertThat(error.context()).isEqualTo(data.getResponse().path("errorContext").asText());
                    assertThat(error.reason()).contains(data.getResponse().path("errorContains").asText());
                });
            }
        }));
    }

    @Test
    void invalidSourceHashAndMissingAssertionCannotBePromoted() throws Exception {
        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(packageFile);
        artifactPackage.getBusinessRequirements().getFirst().getSourceEvidence().getFirst().setSha256("0".repeat(64));
        artifactPackage.getTestCases().getFirst().setAssertions(java.util.List.of());
        ValidationResult result = new CanonicalTraceabilityValidator().validateForExecution(artifactPackage, sourceEvidenceRoot);
        assertThat(result.errors()).anySatisfy(error -> assertThat(error.reason()).contains("citation hash does not match"));
        assertThat(result.errors()).anySatisfy(error -> assertThat(error.reason()).contains("per-requirement assertions"));
    }

    @Test
    void changingOnlyTheDiscretionarySuffixIsDetected() throws Exception {
        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(packageFile);
        CanonicalTestData data = artifactPackage.getTestData().stream()
                .filter(row -> row.getId().equals("TD-SEG100-E12-TRACK1-SPLIT-MATCH")).findFirst().orElseThrow();
        ((ObjectNode) data.getPayload().path("request").path("dataSection2").path("standardSegment"))
                .put("cardDiscretionaryBlockData", "ALTERED");
        assertThat(new AccountNumberEntryValidator().validateTestData(data).errors()).singleElement().satisfies(error -> {
            assertThat(error.context()).isEqualTo("CardDiscretionaryBlockData");
            assertThat(error.reason()).contains("Element 12 must contain");
        });
    }

    @Test
    void disabledOrMissingObservationsCannotPassPerFixtureExecution() throws Exception {
        AccountNumberEntryValidator validator = new AccountNumberEntryValidator();
        assertThat(validator.validateTestData(null).errors()).isNotEmpty();
        CanonicalTestData data = new CanonicalPackageLoader().load(packageFile).getTestData().getFirst();
        ((ObjectNode) data.getPayload()).putObject("testControls");
        assertThat(validator.validateTestData(data).errors()).singleElement().satisfies(error ->
                assertThat(error.reason()).contains("no Element 2/12 validation"));
    }

        @Test
        void reportIsBoundToTheExecutedPackageAndCurrentValidator() throws Exception {
        var report = new ObjectMapper().readTree(root.resolve("specifications/ATL105/test-output/"
            + "test-solution-independent-review/segment-100-elements-2-and-12-local-execution.json").toFile());
        assertThat(report.path("packageSha256").asText()).isEqualTo(hash(packageFile));
        assertThat(report.path("validatorSourceSha256").asText()).isEqualTo(hash(root.resolve(
            "src/main/java/com/coreauth/validator/canonical/AccountNumberEntryValidator.java")));
        assertThat(report.path("sourceSha256").asText()).isEqualTo(hash(sourceEvidenceRoot.resolve("docs/specs/extracted_text.txt")));
        assertThat(report.path("executedFixtures").asInt()).isEqualTo(42);
        assertThat(report.path("matchedFixtures").asInt()).isEqualTo(42);
        assertThat(report.path("results")).hasSize(42).allSatisfy(row -> assertThat(row.path("matched").asBoolean()).isTrue());
        assertThat(report.path("hostExecution").asText()).isEqualTo("NOT_EXECUTED");
        assertThat(report.path("semanticApproval").asText()).isEqualTo("NOT_ASSESSED");
        }

        @Test
        void malformedOrUnqualifiedInputCannotPass() throws Exception {
        var validator = new AccountNumberEntryValidator();
        CanonicalTestData data = new CanonicalPackageLoader().load(packageFile).getTestData().getFirst();
        ObjectNode payload = (ObjectNode) data.getPayload();
        ((ObjectNode) payload.path("testControls")).put("entryMode", "UNDECLARED");
        assertThat(validator.validateTestData(data).errors()).singleElement().satisfies(error ->
            assertThat(error.reason()).contains("explicit supported entry mode"));
        ((ObjectNode) payload.path("testControls")).put("entryMode", "manual");
        ((ObjectNode) payload.path("request").path("dataSection2").path("standardSegment")).put("accountNumber", 1234);
        assertThat(validator.validateTestData(data).errors()).singleElement().satisfies(error ->
            assertThat(error.reason()).contains("must be a string"));
        payload.putObject("request");
        assertThat(validator.validateTestData(data).errors()).singleElement().satisfies(error ->
            assertThat(error.reason()).contains("observed standardSegment"));
        }

        private static String hash(Path path) throws Exception {
        return java.util.HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256")
            .digest(java.nio.file.Files.readAllBytes(path)));
        }
}