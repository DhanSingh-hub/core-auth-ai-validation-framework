package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105TotalsPromptCodeEvidence;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class TotalsPromptCodeEvidenceTest {
    @TempDir Path pack;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void backsOnlyTwoFieldFiveClaimsWithoutCasesOrApproval() throws Exception {
        copyInputs();
        JsonNode report = generate();
        assertThat(report.path("summary").path("catalogRuleCount").asInt()).isEqualTo(601);
        assertThat(report.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(2);
        assertThat(report.path("summary").path("sourceMismatchCount").asInt()).isZero();
        assertThat(report.path("summary").path("draftCaseCount").asInt()).isZero();
        assertThat(report.path("summary").path("smeApprovedRuleCount").asInt()).isZero();
        assertThat(report.path("assertions").get(0).path("sourceLine").asInt()).isGreaterThan(11900);
    }

    @Test
    void changingSection105ValueCannotBeRescuedBySection119() throws Exception {
        copyInputs();
        Path path = pack.resolve("docs/specs/extracted_text.txt");
        String source = Files.readString(path);
        var heading = Pattern.compile("(?m)^12\\.6 Totals Data Segment[ \\t]*\\r?$").matcher(source);
        assertThat(heading.find()).isTrue();
        int start = heading.start();
        var next = Pattern.compile("(?m)^12\\.7 Loyalty Card Data Segment[ \\t]*\\r?$").matcher(source);
        assertThat(next.find(start + 1)).isTrue();
        int end = next.start();
        String section = source.substring(start, end);
        assertThat(section).contains("Fixed value:  990");
        Files.writeString(path, source.substring(0, start)
            + section.replace("Fixed value:  990", "Fixed value:  991") + source.substring(end));
        JsonNode report = generate();
        assertThat(report.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(1);
        assertThat(report.path("summary").path("sourceMismatchCount").asInt()).isEqualTo(1);
    }

    @Test
    void conflictingFixedValuesWithinFieldFiveStayInReview() throws Exception {
        copyInputs();
        Path path = pack.resolve("docs/specs/extracted_text.txt");
        Files.writeString(path, Files.readString(path).replaceFirst("Fixed value:  990",
            "Fixed value:  990\\nFixed value:  991"));
        JsonNode report = generate();
        assertThat(report.path("summary").path("sourceMismatchCount").asInt()).isEqualTo(1);
    }

    private JsonNode generate() throws Exception {
        GenerateAtl105TotalsPromptCodeEvidence.main(new String[]{pack.toString()});
        return mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-totals-prompt-code-evidence.json").toFile());
    }

    private void copyInputs() throws Exception {
        for (String relative : new String[]{"contract/totals-prompt-code-assertions.json", "docs/specs/extracted_text.txt",
            "test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json",
            "test-output/test-solution-independent-review/atl105-br-approval-matrix.json"}) {
            Path destination = pack.resolve(relative);
            Files.createDirectories(destination.getParent());
            Files.copy(Atl105Paths.root().resolve(relative), destination);
        }
    }
}