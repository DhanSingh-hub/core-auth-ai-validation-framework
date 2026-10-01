package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105Chapter13RuleEvidence;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Chapter13RuleEvidenceTest {
    @TempDir Path pack;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void matchesBothElementDefinitionsWithoutCreatingCases() throws Exception {
        copyInputs();
        GenerateAtl105Chapter13RuleEvidence.main(new String[]{pack.toString()});
        JsonNode report = report();
        assertThat(report.path("summary").path("catalogRuleCount").asInt()).isEqualTo(601);
        assertThat(report.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(3);
        assertThat(report.path("assertions").get(0).path("quotes")).hasSize(8);
        assertThat(report.path("assertions").get(0).path("status").asText()).isEqualTo("PARTIAL_SOURCE_BACKED_ASSERTION_REVIEW_REQUIRED");
        assertThat(report.path("assertions").get(0).path("quotes").get(4).path("sourceLine").asInt()).isGreaterThan(21690);
        assertThat(report.path("assertions").get(1).path("ruleId").asText()).isEqualTo("SEGDL2-R-006");
        assertThat(report.path("assertions").get(1).path("quotes")).hasSize(4);
        assertThat(report.path("assertions").get(2).path("ruleId").asText()).isEqualTo("SEGDL2-R-007");
        assertThat(report.path("assertions").get(2).path("quotes")).hasSize(9);
        assertThat(report.path("draftCases")).isEmpty();
        assertThat(report.path("summary").path("smeApprovedRuleCount").asInt()).isZero();
    }

    @Test
    void changedPhoneDefinitionCannotBorrowPhraseFromOutsideItsBounds() throws Exception {
        copyInputs();
        mutateDefinition("Number: 54 Name: Merchant Phone", "Number: 55 Name: Message Format",
            "[(nnn)nnn-nnnn]", "[unknown]");
        GenerateAtl105Chapter13RuleEvidence.main(new String[]{pack.toString()});
        assertThat(report().path("summary").path("sourceMismatchCount").asInt()).isEqualTo(1);
        assertThat(report().path("assertions").get(0).path("status").asText()).isEqualTo("SOURCE_MISMATCH_REVIEW_REQUIRED");
    }

    @Test
    void changedStoreMaskRuleWithdrawsPartialBacking() throws Exception {
        copyInputs();
        mutateDefinition("Number: 98 Name: Store Number", "Number: 99 Name: Tax Amount",
            "neither displayed", "sometimes displayed");
        GenerateAtl105Chapter13RuleEvidence.main(new String[]{pack.toString()});
        assertThat(report().path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(2);
        assertThat(report().path("draftCases")).isEmpty();
    }

    @Test
    void changedRedialCodesWithdrawOnlyTheDl2Claim() throws Exception {
        copyInputs();
        mutateDefinition("Number: 82 Name: Redial Count", "Number: 83 Name: Response Code",
            "Valid Codes/Values: 1–3", "Valid Codes/Values: 1–4");
        GenerateAtl105Chapter13RuleEvidence.main(new String[]{pack.toString()});
        JsonNode report = report();
        assertThat(report.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(2);
        assertThat(report.path("summary").path("sourceMismatchCount").asInt()).isEqualTo(1);
        assertThat(report.path("assertions").get(0).path("status").asText()).isEqualTo("PARTIAL_SOURCE_BACKED_ASSERTION_REVIEW_REQUIRED");
        assertThat(report.path("assertions").get(1).path("status").asText()).isEqualTo("SOURCE_MISMATCH_REVIEW_REQUIRED");
    }

    @Test
    void changedTerminatorCannotBorrowEvidenceOutsideElement27() throws Exception {
        copyInputs();
        mutateDefinition("Number: 27 Name: Dial String Terminator", "Number: 28 Name: Dial String Type",
            "F Indicates the end of the second dial string", "X Indicates the end of the second dial string");
        GenerateAtl105Chapter13RuleEvidence.main(new String[]{pack.toString()});
        JsonNode report = report();
        assertThat(report.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(2);
        assertThat(report.path("summary").path("sourceMismatchCount").asInt()).isEqualTo(1);
        assertThat(report.path("assertions").get(2).path("status").asText()).isEqualTo("SOURCE_MISMATCH_REVIEW_REQUIRED");
    }

    @Test
    void wrongVersionFailsBeforePublishing() throws Exception {
        copyInputs();
        Path manifest = pack.resolve("contract/chapter-13-rule-evidence.json");
        Files.writeString(manifest, Files.readString(manifest).replace("2026-3", "unsupported-version"));
        assertThatThrownBy(() -> GenerateAtl105Chapter13RuleEvidence.main(new String[]{pack.toString()}))
            .isInstanceOf(IllegalStateException.class).hasMessageContaining("version");
        assertThat(Files.exists(pack.resolve("test-output/test-solution-independent-review/atl105-chapter-13-rule-evidence.json"))).isFalse();
    }

    private void mutateDefinition(String heading, String endHeading, String original, String replacement) throws Exception {
        Path file = pack.resolve("docs/specs/extracted_text.txt");
        String source = Files.readString(file);
        int start = source.indexOf(heading);
        int end = source.indexOf(endHeading, start + heading.length());
        assertThat(start).isPositive();
        assertThat(end).isGreaterThan(start);
        String definition = source.substring(start, end);
        assertThat(definition).contains(original);
        Files.writeString(file, source.substring(0, start) + definition.replace(original, replacement)
            + source.substring(end) + "\n" + original + "\n");
    }

    private void copyInputs() throws Exception {
        for (String name : new String[]{"contract/chapter-13-rule-evidence.json", "docs/specs/extracted_text.txt",
            "test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json",
            "test-output/test-solution-independent-review/atl105-br-approval-matrix.json"}) {
            Path target = pack.resolve(name);
            Files.createDirectories(target.getParent());
            Files.copy(Atl105Paths.root().resolve(name), target);
        }
    }

    private JsonNode report() throws Exception {
        return mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-chapter-13-rule-evidence.json").toFile());
    }
}