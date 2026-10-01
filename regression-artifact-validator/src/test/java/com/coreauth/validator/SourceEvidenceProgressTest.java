package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105SourceEvidenceProgress;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class SourceEvidenceProgressTest {
    @TempDir Path pack;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void reconcilesUniquePartialClaimsWithoutCertifyingAnyRule() throws Exception {
        copyInputs();
        JsonNode result = generate();
        JsonNode summary = result.path("summary");
        assertThat(summary.path("catalogRuleCount").asInt()).isEqualTo(601);
        assertThat(summary.path("rulesWithAnySourceBackedAssertion").asInt()).isEqualTo(54);
        assertThat(summary.path("rulesWithoutSourceBackedAssertions").asInt()).isEqualTo(547);
        assertThat(summary.path("rulesWithSourceMismatch").asInt()).isEqualTo(2);
        assertThat(summary.path("draftCaseCount").asInt()).isEqualTo(114);
        assertThat(summary.path("smeApprovedRuleCount").asInt()).isZero();
        assertThat(summary.path("executionCertifiedRuleCount").asInt()).isZero();
        assertThat(result.path("rules")).hasSize(601);
        assertThat(result.path("segments")).hasSize(49);
        int segmentRules = 0;
        int segmentRemaining = 0;
        int segmentMismatches = 0;
        for (JsonNode segment : result.path("segments")) {
            segmentRules += segment.path("catalogRules").asInt();
            segmentRemaining += segment.path("rulesWithoutBackedAssertion").asInt();
            segmentMismatches += segment.path("sourceMismatches").asInt();
        }
        assertThat(segmentRules).isEqualTo(601);
        assertThat(segmentRemaining).isEqualTo(547);
        assertThat(segmentMismatches).isEqualTo(2);
        assertThat(rule(result, "SEG114-R-003").path("status").asText()).isEqualTo("SOURCE_MISMATCH_REVIEW_REQUIRED");
        assertThat(rule(result, "SEG101-R-004").path("status").asText()).isEqualTo("PARTIAL_SOURCE_EVIDENCE_REVIEW_REQUIRED");
        assertThat(rule(result, "SEG116-R-004").path("status").asText()).isEqualTo("NOT_CURATED_REVIEW_REQUIRED");
        assertThat(rule(result, "SEGDL1-R-010").path("evidenceScopes").get(0).asText()).isEqualTo("PARTIAL_POSITIONAL_LAYOUT");
        assertThat(rule(result, "SEGDL1-R-011").path("evidenceScopes").get(0).asText()).isEqualTo("PARTIAL_CHAPTER_13_FIELD_DEFINITIONS");
        assertThat(rule(result, "SEGDL2-R-006").path("evidenceScopes").get(0).asText()).isEqualTo("PARTIAL_CHAPTER_13_FIELD_DEFINITIONS");
    }

    @Test
    void sourceMutationWithdrawsOnlyItsBackedFixedTypeClaim() throws Exception {
        copyInputs();
        Path sourceFile = pack.resolve("docs/specs/extracted_text.txt");
        Files.writeString(sourceFile, Files.readString(sourceFile).replace("Fixed value:  101", "Fixed value:  199"));
        JsonNode result = generate();
        assertThat(result.path("summary").path("rulesWithAnySourceBackedAssertion").asInt()).isEqualTo(53);
        assertThat(result.path("summary").path("rulesWithSourceMismatch").asInt()).isEqualTo(3);
        assertThat(rule(result, "SEG101-R-004").path("status").asText()).isEqualTo("SOURCE_MISMATCH_REVIEW_REQUIRED");
    }

    @Test
    void element4SourceMutationWithdrawsItsPartialClaim() throws Exception {
        copyInputs();
        Path sourceFile = pack.resolve("docs/specs/extracted_text.txt");
        String source = Files.readString(sourceFile);
        int start = source.indexOf("Number: 4 Name: Address Line 2");
        int end = source.indexOf("Number: 5 Name: Approval Number", start);
        String definition = source.substring(start, end);
        Files.writeString(sourceFile, source.substring(0, start)
            + definition.replace(" 16 Space", " 16 Letter") + source.substring(end));
        JsonNode result = generate();
        assertThat(result.path("summary").path("rulesWithAnySourceBackedAssertion").asInt()).isEqualTo(53);
        assertThat(result.path("summary").path("rulesWithSourceMismatch").asInt()).isEqualTo(3);
        assertThat(rule(result, "SEGDL1-R-010").path("status").asText()).isEqualTo("SOURCE_MISMATCH_REVIEW_REQUIRED");
    }

    @Test
    void changedStoreDefinitionWithdrawsRule011WithoutPromotingOtherRules() throws Exception {
        copyInputs();
        Path sourceFile = pack.resolve("docs/specs/extracted_text.txt");
        String source = Files.readString(sourceFile);
        int start = source.indexOf("Number: 98 Name: Store Number");
        int end = source.indexOf("Number: 99 Name: Tax Amount", start);
        String definition = source.substring(start, end);
        assertThat(definition).contains("neither displayed").contains("nor printed");
        Files.writeString(sourceFile, source.substring(0, start)
            + definition.replace("neither displayed", "sometimes displayed") + source.substring(end));
        JsonNode result = generate();
        assertThat(result.path("summary").path("rulesWithAnySourceBackedAssertion").asInt()).isEqualTo(53);
        assertThat(result.path("summary").path("rulesWithSourceMismatch").asInt()).isEqualTo(3);
        assertThat(rule(result, "SEGDL1-R-011").path("status").asText()).isEqualTo("SOURCE_MISMATCH_REVIEW_REQUIRED");
    }

    @Test
    void changedRedialRangeWithdrawsOnlyDl2PartialBacking() throws Exception {
        copyInputs();
        Path sourceFile = pack.resolve("docs/specs/extracted_text.txt");
        String source = Files.readString(sourceFile);
        int start = source.indexOf("Number: 82 Name: Redial Count");
        int end = source.indexOf("Number: 83 Name: Response Code", start);
        String definition = source.substring(start, end);
        assertThat(definition).contains("Valid Codes/Values: 1–3");
        Files.writeString(sourceFile, source.substring(0, start)
            + definition.replace("Valid Codes/Values: 1–3", "Valid Codes/Values: 1–4") + source.substring(end));
        JsonNode result = generate();
        assertThat(result.path("summary").path("rulesWithAnySourceBackedAssertion").asInt()).isEqualTo(53);
        assertThat(result.path("summary").path("rulesWithSourceMismatch").asInt()).isEqualTo(3);
        assertThat(rule(result, "SEGDL2-R-006").path("status").asText()).isEqualTo("SOURCE_MISMATCH_REVIEW_REQUIRED");
    }

    private JsonNode generate() throws Exception {
        GenerateAtl105SourceEvidenceProgress.main(new String[]{pack.toString()});
        return mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-source-evidence-progress.json").toFile());
    }

    private static JsonNode rule(JsonNode report, String id) {
        for (JsonNode row : report.path("rules")) if (id.equals(row.path("ruleId").asText())) return row;
        throw new IllegalStateException("Missing rule " + id);
    }

    private void copyInputs() throws Exception {
        for (String relative : new String[]{"docs/specs/extracted_text.txt", "contract/element-83-source-assertions.json",
            "contract/element-84-width-assertions.json", "contract/element-4-layout-assertions.json",
            "contract/chapter-13-rule-evidence.json",
            "contract/segment-110-field-row-assertions.json",
            "contract/totals-prompt-code-assertions.json",
            "test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json",
            "test-output/test-solution-independent-review/atl105-br-approval-matrix.json"}) {
            Path destination = pack.resolve(relative);
            Files.createDirectories(destination.getParent());
            Files.copy(Atl105Paths.root().resolve(relative), destination);
        }
    }
}