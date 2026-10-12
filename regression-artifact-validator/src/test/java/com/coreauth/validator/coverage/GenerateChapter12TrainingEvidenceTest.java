package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalPackageLoader;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateChapter12TrainingEvidenceTest {
    @Test
    void persistsRealExecutionsAndSourceContinuousChainsWithoutClaimingWholeSpecCoverage(@TempDir Path temp) throws Exception {
        Path packageFile = temp.resolve("package.json");
        Path reportFile = temp.resolve("report.json");
        var report = new GenerateChapter12TrainingEvidence().generate(packageFile, reportFile);
        assertThat(report.path("completeSpecCoverage").asBoolean()).isFalse();
        assertThat(report.path("fullMessageIntakeWired").asBoolean()).isFalse();
        assertThat(report.path("summary").path("businessRequirements").asInt()).isEqualTo(158);
        assertThat(report.path("summary").path("executedObservations").asInt()).isEqualTo(182);
        assertThat(report.path("summary").path("approvedRequirements").asInt()).isZero();
        assertThat(report.path("sourceSha256").asText()).hasSize(64);
        var reconciliation = report.path("sourceSectionReconciliation");
        assertThat(reconciliation.path("status").asText()).isEqualTo("IN_PROGRESS");
        assertThat(reconciliation.path("sourceSections").asInt()).isEqualTo(49);
        assertThat(reconciliation.path("sections")).hasSize(49);
        assertThat(reconciliation.path("sections")).allSatisfy(section -> {
            assertThat(section.path("status").asText()).isEqualTo("SOURCE_FACETS_NOT_CLOSED");
            assertThat(section.path("sourceEvidence").path("endLine").asInt())
                    .isGreaterThan(section.path("sourceEvidence").path("startLine").asInt());
        });
        assertThat(reconciliation.path("sections")).anySatisfy(section -> {
            assertThat(section.path("section").asText()).isEqualTo("12.31");
            assertThat(section.path("catalogRules")).anySatisfy(rule -> {
                assertThat(rule.path("ruleId").asText()).isEqualTo("SEG145-R-008");
                assertThat(rule.path("status").asText()).isEqualTo("BOUNDED_EXECUTION_ONLY");
                assertThat(rule.path("executions")).isNotEmpty();
            });
        });
        assertThat(report.path("executions")).anySatisfy(execution -> {
            assertThat(execution.path("ruleId").asText()).isEqualTo("SEG119-R-008");
            assertThat(execution.path("existingTestArtifactAnchorCandidates")).isNotEmpty();
            assertThat(execution.path("candidateMatchStatus").asText()).isEqualTo("REVIEW_REQUIRED");
        });
        assertThat(report.path("executions")).anySatisfy(execution -> {
            assertThat(execution.path("ruleId").asText()).isEqualTo("SEG115-R-003");
            assertThat(execution.path("existingTestArtifactAnchorCandidates")).isNotEmpty();
            assertThat(execution.path("candidateMatchStatus").asText()).isEqualTo("REVIEW_REQUIRED");
        });
        var loaded = new CanonicalPackageLoader().load(packageFile);
        assertThat(new CanonicalTraceabilityValidator().validate(loaded).errors()).isEmpty();
        assertThat(new CanonicalTraceabilityValidator().validateForExecution(loaded).errors()).isNotEmpty();
        var validator = new Chapter12SegmentPayloadValidator();
        var actualAdapter = new com.coreauth.validator.canonical.Segment119ActualPayloadAdapter(new ObjectMapper()
                .readTree(com.coreauth.validator.paths.Atl105Paths.testOutput().resolve("test-solution-independent-review")
                        .resolve("all-element-inventory.json").toFile()));
        for (var data : loaded.getTestData()) {
            var payload = data.getPayload();
            if (payload.has("productPayload")) {
                var result = new com.coreauth.validator.canonical.ProductActualPayloadAdapter().validatePayload(
                        payload.path("productPayload"), payload.path("segment").asText());
                assertThat(result.isValid()).isEqualTo(!"FAIL".equals(data.getExpectedValidation()));
                continue;
            }
            var result = payload.has("actualPayload") ? "119".equals(payload.path("segment").asText())
                    ? actualAdapter.validate(payload.path("actualPayload"), payload.path("messageFamily").asText(), payload.path("actualEvidence"))
                    : new com.coreauth.validator.canonical.Chapter12ActualPayloadAdapter().validate(payload.path("actualPayload"),
                    payload.path("segment").asText(), payload.path("messageFamily").asText(), payload.path("actualEvidence")) : validator.validate(payload);
            assertThat(result.status().name()).isIn("INVALID", "REVIEW_REQUIRED", "CHECKS_PASSED");
        }
        int predicates = 0;
        int actualChapter12Executions = 0;
        var actualChapter12Segments = new java.util.HashSet<String>();
        for (var execution : report.path("executions")) {
            if ("ACTUAL_NAMED_CHAPTER12_OBSERVATION".equals(execution.path("validationPath").asText())) {
                actualChapter12Executions++;
                actualChapter12Segments.add(execution.path("sourceAnchor").path("segment").asText());
            }
            if (execution.has("expectedTargetStatus")) {
                predicates++;
                assertThat(execution.path("actualTargetStatus")).isEqualTo(execution.path("expectedTargetStatus"));
            }
        }
        assertThat(predicates).isEqualTo(124);
        assertThat(actualChapter12Executions).isEqualTo(12);
        assertThat(actualChapter12Segments).containsExactlyInAnyOrder("115", "130", "131", "145", "146", "148");
        assertThat(new ObjectMapper().readTree(reportFile.toFile())).isEqualTo(report);
    }
}
