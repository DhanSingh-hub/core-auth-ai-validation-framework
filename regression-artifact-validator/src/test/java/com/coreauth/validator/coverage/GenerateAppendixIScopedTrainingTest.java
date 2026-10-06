package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.AppendixILogicalDataValidator;
import com.coreauth.validator.canonical.AppendixIScopedCandidateValidator;
import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GenerateAppendixIScopedTrainingTest {
    private static final Path ROOT = Path.of("specifications", "ATL105");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final AppendixIScopedCandidateValidator VALIDATOR = new AppendixIScopedCandidateValidator();

    @Test
    void generatesLinkedReviewOnlyChainsAndCompleteDraftRuleLedger() throws IOException {
        var output = GenerateAppendixIScopedTraining.generate(ROOT);
        var pkg = MAPPER.treeToValue(output.artifactPackage(), CanonicalArtifactPackage.class);
        assertThat(new CanonicalTraceabilityValidator().validate(pkg).errors()).isEmpty();
        assertThat(pkg.getBusinessRequirements()).hasSize(11);
        assertThat(pkg.getTestScenarios()).hasSize(11);
        assertThat(pkg.getTestCases()).hasSize(22);
        assertThat(pkg.getTestData()).hasSize(22);
        for (var td : pkg.getTestData()) {
            assertThat(td.getReadiness().name()).isEqualTo("REVIEW_REQUIRED");
            assertThat(td.getTestCaseIds()).hasSize(1);
            assertThat(td.getPayload().path("scope").asText()).isEqualTo("LOGICAL_FRAGMENT_NOT_COMPLETE_REQUEST");
            assertThat(td.getResponse().path("assessment").asText()).isEqualTo("NOT_EXECUTED_NO_HOST_RESPONSE");
            JsonNode input = td.getPayload().path("request").path("appendixILogicalCandidate");
            String target = AppendixIScopedCandidateValidator.mutationTarget(input.path("candidateId").asText());
            assertThat(VALIDATOR.validate(input).findings()).anyMatch(f -> f.ruleId().equals(target)
                    && f.status().name().equals(td.getExpectedValidation()));
        }
        var evidence = output.evidence();
        assertThat(evidence.path("draftTableCount").asInt()).isEqualTo(78);
        assertThat(evidence.path("draftRuleCount").asInt()).isEqualTo(302);
        assertThat(evidence.path("ruleLedger")).hasSize(302);
        evidence.path("ruleLedger").forEach(rule -> {
            assertThat(rule.path("completeRuleCoverage").asBoolean()).isFalse();
            assertThat(rule.path("tableId").asText()).matches("[0-9]{3}");
        });
        assertThat(evidence.path("logicalEvaluationCount").asInt()).isEqualTo(22);
        assertThat(evidence.path("detectedSuppliedMutationCount").asInt()).isEqualTo(11);
        assertThat(evidence.path("appendixWideMutationThresholdEstablished").asBoolean()).isFalse();
        assertThat(evidence.path("completeRequestCount").asInt()).isZero();
        assertThat(evidence.path("certifiedCaseCount").asInt()).isZero();
        assertThat(evidence.path("aiComparison").asText()).isEqualTo("DEFERRED_BY_USER");
    }

    @ParameterizedTest
    @ValueSource(strings = {"APPI-C010-01", "APPI-C010-02", "APPI-C011-01", "APPI-C011-02",
            "APPI-C011-03", "APPI-C012-01", "APPI-C013-01", "APPI-C013-02",
            "APPI-C014-01", "APPI-C015-01", "APPI-C017-01"})
    void detectsTheSpecificSuppliedMutationWithoutPromotingReadiness(String id) throws IOException {
        ObjectNode source = candidate(id);
        ObjectNode saved = source.deepCopy();
        String target = AppendixIScopedCandidateValidator.mutationTarget(id);
        var positive = VALIDATOR.validate(source);
        assertThat(positive.findings()).noneMatch(f -> f.status() == AppendixILogicalDataValidator.Status.FAIL);
        assertThat(positive.findings()).anyMatch(f -> f.ruleId().equals(target)
                && f.status() == AppendixILogicalDataValidator.Status.PASS);
        var negative = VALIDATOR.validate(GenerateAppendixIScopedTraining.mutate(source));
        assertThat(negative.findings()).anyMatch(f -> f.ruleId().equals(target)
                && f.status() == AppendixILogicalDataValidator.Status.FAIL);
        assertThat(positive.representationValid()).isFalse();
        assertThat(negative.representationValid()).isFalse();
        assertThat(source).isEqualTo(saved);
        source.putArray("expectedPredicateOutcomes").addObject().put("positive", "FAIL").put("mutated", "PASS");
        assertThat(VALIDATOR.validate(source)).isEqualTo(positive);
    }

    @Test
    void tapChecksHexWidthsReservedFillAndBothZeroFillBranches() throws IOException {
        ObjectNode input = candidate("APPI-C010-01");
        ObjectNode fields = (ObjectNode) input.path("logicalFields");
        for (String invalid : List.of("0".repeat(9), "0".repeat(11), "000000000Z")) {
            fields.put("visaBID", invalid);
            assertFail(input, "APPI-T010-LAYOUT");
        }
        fields.put("visaBID", "0".repeat(10));
        fields.put("visaTAP", "0".repeat(11) + "1");
        assertFail(input, "APPI-T010-ZERO-FILL");
        fields.put("visaTAP", "0".repeat(12));
        fields.put("tppVarId", "SYN01");
        assertFail(input, "APPI-T010-LAYOUT");
        fields.put("tppVarId", "SYN001");
        ((ObjectNode) fields.path("context")).remove("tapUsed");
        assertReview(input, "APPI-T010-ZERO-FILL");
    }

    @Test
    void preservesTaxBlankAndRejectsIncompleteOrContradictoryContext() throws IOException {
        ObjectNode input = candidate("APPI-C011-03");
        assertThat(VALIDATOR.validate(input).findings()).anyMatch(f -> f.ruleId().equals("APPI-T011-MAPPING")
                && f.status() == AppendixILogicalDataValidator.Status.PASS);
        input.put("data", "");
        assertFail(input, "APPI-T011-MAPPING");
        ObjectNode flags = (ObjectNode) input.path("context");
        flags.put("taxable", false).put("salesTaxIncluded", true);
        assertFail(input, "APPI-T011-MAPPING");
        flags.put("taxable", "false");
        assertReview(input, "APPI-T011-MAPPING");
    }

    @Test
    void recurringRestrictionsDoNotLeakIntoInstallmentAndMissingBinContextIsNotAccepted() throws IOException {
        ObjectNode input = candidate("APPI-C013-01");
        ObjectNode flags = (ObjectNode) input.path("context");
        for (String brand : List.of("Visa", "MasterCard", "Discover", "Amex")) {
            flags.put("binBrandClassification", brand);
            assertThat(VALIDATOR.validate(input).findings()).noneMatch(f -> f.status() == AppendixILogicalDataValidator.Status.FAIL);
        }
        flags.put("binBrandClassification", "Unclassified");
        assertReview(input, "APPI-T013-RECURRING");
        flags.remove("binBrandClassification");
        assertReview(input, "APPI-T013-RECURRING");
        ObjectNode installment = candidate("APPI-C013-02");
        assertThat(VALIDATOR.validate(installment).findings()).noneMatch(f -> f.ruleId().equals("APPI-T013-RECURRING"));
    }

    @Test
    void echoWidthForwardingAndRrnRelationAreIndependentChecks() throws IOException {
        ObjectNode echo = candidate("APPI-C014-01");
        ObjectNode fields = (ObjectNode) echo.path("logicalFields");
        fields.put("requestUserData", "A".repeat(15)).put("responseEcho", "A".repeat(15));
        assertThat(VALIDATOR.validate(echo).findings()).noneMatch(f -> f.status() == AppendixILogicalDataValidator.Status.FAIL);
        fields.put("requestUserData", "A".repeat(16)).put("responseEcho", "A".repeat(16));
        assertFail(echo, "APPI-T014-WIDTH");
        fields.put("issuerForwarded", true);
        assertFail(echo, "APPI-T014-NONFORWARD");
        fields.remove("issuerForwarded");
        assertReview(echo, "APPI-T014-NONFORWARD");
        ObjectNode rrn = candidate("APPI-C015-01");
        fields = (ObjectNode) rrn.path("logicalFields");
        fields.put("subsequentRequestRRN", "A".repeat(14));
        assertFail(rrn, "APPI-T015-WIDTH");
        assertFail(rrn, "APPI-T015-RELATION");
        ((ObjectNode) fields.path("context")).put("brand", "Visa");
        assertReview(rrn, "APPI-T015-RELATION");
    }

    @Test
    void fraudIndicatorCannotStandInForItsReversalContext() throws IOException {
        ObjectNode input = candidate("APPI-C017-01");
        ((ObjectNode) input.path("context")).put("transactionClass", "Purchase request");
        assertFail(input, "APPI-T017-CONTEXT");
        ((ObjectNode) input.path("context")).remove("suspectedFraud");
        assertReview(input, "APPI-T017-CONTEXT");
        input = candidate("APPI-C017-01");
        ((ObjectNode) input.path("context")).put("brand", "Visa");
        assertReview(input, "APPI-T017-CONTEXT");
    }

    @Test
    void invalidScopeIdentityAndAbsentMutationPathsFailExplicitly() throws IOException {
        assertThat(VALIDATOR.validate(null).findings().get(0).status()).isEqualTo(AppendixILogicalDataValidator.Status.FAIL);
        ObjectNode input = candidate("APPI-C010-01");
        input.put("tableId", "011");
        assertFail(input, "APPI-CANDIDATE-IDENTITY");
        input.put("candidateId", "UNKNOWN");
        assertThat(VALIDATOR.validate(input).findings().get(0).status())
                .isEqualTo(AppendixILogicalDataValidator.Status.NOT_ASSERTABLE);
        ((ObjectNode) input.path("mutation")).put("path", "context.absent");
        assertThatThrownBy(() -> GenerateAppendixIScopedTraining.mutate(input))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("absent");
    }

    @Test
    void sourceChangedOrMissingInputsCannotProduceSuccess(@TempDir Path root) throws IOException {
        Path source = root.resolve(Path.of("docs", "specs", "extracted_text.txt"));
        Files.createDirectories(source.getParent());
        Files.writeString(source, "changed");
        assertThatThrownBy(() -> GenerateAppendixIScopedTraining.generate(root))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("hash changed");
    }

    @Test
    void predicatesHaveLiteralEvidenceWithinTheirInspectedSourceSections() throws IOException {
        List<String> lines = Files.readAllLines(ROOT.resolve(Path.of("docs", "specs", "extracted_text.txt")));
        String tap = String.join(" ", lines.subList(27745, 27764)).replaceAll("\\s+", " ");
        assertThat(tap).contains("Positions 1", "Positions 11", "space-filled", "Zero-filled");
        String taxAndPayment = String.join(" ", lines.subList(27774, 27840)).replaceAll("\\s+", " ");
        assertThat(taxAndPayment).contains("Y = Transaction is taxable", "N = Transaction is nontaxable",
                "Blank = Transaction is taxable", "M = Medical", "B = Bill Payment", "H = Hotel",
                "R (Recurring payment)", "I (Installment payment)", "not forwarded to the issuer",
                "echoed back in the response", "associated financial response");
        assertThat(String.join(" ", lines.subList(27920, 27928)))
                .contains("Fixed value:  34", "purchase", "reversal", "MasterCard");
    }

    @Test
    void persistedPackageEvidenceAndIndividualFixturesAreReproducible() throws IOException {
        Path destination = ROOT.resolve(Path.of("test-output", "test-json", "appendix-i-scoped-training"));
        var output = GenerateAppendixIScopedTraining.generate(ROOT);
        assertThat(MAPPER.readTree(destination.resolve("package.json").toFile())).isEqualTo(output.artifactPackage());
        assertThat(MAPPER.readTree(destination.resolve("evidence.json").toFile())).isEqualTo(output.evidence());
        for (var data : output.artifactPackage().path("testData")) {
            assertThat(MAPPER.readTree(destination.resolve(data.path("fileName").asText()).toFile())).isEqualTo(data);
        }
    }

    @Test
    void trainingStatusKeepsEveryFrameworkPhaseOpenWithoutChangingSegmentDenominator() throws IOException {
        var status = MAPPER.readTree(ROOT.resolve("training-status.json").toFile());
        var appendix = status.path("appendixTraining").path("I");
        var report = GenerateAppendixIScopedTraining.generate(ROOT).evidence();
        assertThat(appendix.path("status").asText()).isEqualTo("IN_PROGRESS");
        assertThat(appendix.path("draftRuleCount").asInt()).isEqualTo(report.path("draftRuleCount").asInt());
        assertThat(appendix.path("draftTableCount").asInt()).isEqualTo(report.path("draftTableCount").asInt());
        assertThat(appendix.path("sourceCompletenessCertified").asBoolean()).isFalse();
        assertThat(appendix.path("countsExcludedFromSegmentCatalogSummary").asBoolean()).isTrue();
        assertThat(appendix.path("aiComparison").asText()).isEqualTo("DEFERRED_BY_USER");
        assertThat(appendix.path("mergeToDevelop").asText()).isEqualTo("USER_APPROVAL_REQUIRED");
        assertThat(appendix.path("gates").size()).isEqualTo(status.path("requiredGates").size());
        status.path("requiredGates").forEach(gate ->
                assertThat(appendix.path("gates").path(gate.asText()).asText()).isNotBlank());
        assertThat(status.path("segments").has("I")).isFalse();
        assertThat(appendix.path("blockers")).isEqualTo(report.path("blockers"));
    }

    @Test
    void brokenCanonicalLinkAndAnchorCannotPassStrictReviewPackage() throws IOException {
        ObjectNode pkg = GenerateAppendixIScopedTraining.generate(ROOT).artifactPackage();
        ObjectNode data = (ObjectNode) pkg.path("testData").get(0);
        data.putArray("testCaseIds").add("TC-MISSING");
        var model = MAPPER.treeToValue(pkg, CanonicalArtifactPackage.class);
        assertThat(new CanonicalTraceabilityValidator().validate(model).errors()).isNotEmpty();
        data.putArray("testCaseIds").add("TC-APPI-C010-01-POS");
        data.putArray("sourceAnchors");
        model = MAPPER.treeToValue(pkg, CanonicalArtifactPackage.class);
        assertThat(new CanonicalTraceabilityValidator().validate(model).errors())
                .anyMatch(error -> error.reason().contains("sourceAnchor"));
    }

    private static ObjectNode candidate(String id) throws IOException {
        var batch = MAPPER.readTree(ROOT.resolve(Path.of("test-output", "test-json", "knowledge",
                "appendix-i-candidates-010-048.json")).toFile());
        for (JsonNode row : batch.path("candidates")) {
            if (id.equals(row.path("candidateId").asText())) return row.deepCopy();
        }
        throw new IllegalArgumentException("Candidate missing: " + id);
    }

    private static void assertFail(JsonNode input, String rule) {
        assertThat(VALIDATOR.validate(input).findings()).anyMatch(f ->
                f.ruleId().equals(rule) && f.status() == AppendixILogicalDataValidator.Status.FAIL);
    }

    private static void assertReview(JsonNode input, String rule) {
        assertThat(VALIDATOR.validate(input).findings()).anyMatch(f ->
                f.ruleId().equals(rule) && f.status() == AppendixILogicalDataValidator.Status.REVIEW_REQUIRED);
    }
}
