package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105Segment110FieldEvidence;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class Segment110FieldEvidenceTest {
    @TempDir Path pack;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void acceptsOnlyPartialRowEvidenceWithoutProducingCases() throws Exception {
        copyInputs();
        JsonNode result = generate();
        assertThat(result.path("summary").path("catalogRuleCount").asInt()).isEqualTo(601);
        assertThat(result.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(6);
        assertThat(result.path("summary").path("draftCaseCount").asInt()).isZero();
        assertThat(result.path("assertions").get(1).path("sourceLine").asInt()).isGreaterThan(12400);
        assertThat(result.path("assertions").get(1).path("sourceFingerprintSha256").asText()).hasSize(64);
    }

    @Test
    void changedFieldRowIsReviewRequired() throws Exception {
        copyInputs();
        Path source = pack.resolve("docs/specs/extracted_text.txt");
        String text = Files.readString(source);
        int start = bodyHeading(text, "12.9 Check Data Segment", 0);
        int end = bodyHeading(text, "12.10 Variable Information Data Segment", start + 1);
        String body = text.substring(start, end);
        assertThat(body).contains("7 126 Check Type 1 R");
        Files.writeString(source, text.substring(0, start)
            + body.replace("7 126 Check Type 1 R", "7 126 Check Type 2 R") + text.substring(end));
        JsonNode result = generate();
        assertThat(result.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(5);
        assertThat(result.path("summary").path("sourceMismatchCount").asInt()).isEqualTo(1);
    }

    @Test
    void matchingQuoteOutsideSectionCannotRescueMissingRow() throws Exception {
        copyInputs();
        Path source = pack.resolve("docs/specs/extracted_text.txt");
        String text = Files.readString(source);
        int start = bodyHeading(text, "12.9 Check Data Segment", 0);
        int end = bodyHeading(text, "12.10 Variable Information Data Segment", start + 1);
        String body = text.substring(start, end);
        Files.writeString(source, text.substring(0, start)
            + body.replace("7 126 Check Type 1 R", "7 126 Check Type 2 R") + text.substring(end)
            + "\n7 126 Check Type 1 R\n");
        JsonNode result = generate();
        assertThat(result.path("summary").path("sourceMismatchCount").asInt()).isEqualTo(1);
    }

    private JsonNode generate() throws Exception {
        GenerateAtl105Segment110FieldEvidence.main(new String[]{pack.toString()});
        return mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-segment-110-field-evidence.json").toFile());
    }

    private static int bodyHeading(String source, String heading, int from) {
        var matcher = Pattern.compile("(?m)^" + Pattern.quote(heading) + "[ \\t]*\\r?$").matcher(source);
        if (!matcher.find(from)) throw new IllegalStateException("Missing body heading " + heading);
        return matcher.start();
    }

    private void copyInputs() throws Exception {
        for (String relative : new String[]{"docs/specs/extracted_text.txt", "contract/segment-110-field-row-assertions.json",
            "test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json",
            "test-output/test-solution-independent-review/atl105-br-approval-matrix.json"}) {
            Path target = pack.resolve(relative);
            Files.createDirectories(target.getParent());
            Files.copy(Atl105Paths.root().resolve(relative), target);
        }
    }
}