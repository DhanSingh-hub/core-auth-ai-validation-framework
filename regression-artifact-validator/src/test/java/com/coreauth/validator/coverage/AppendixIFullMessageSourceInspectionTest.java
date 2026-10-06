package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixIFullMessageSourceInspectionTest {
    @Test
    void preservesSourceConflictBeforeAdoptingAppendixBAsFullRequestSeed()
            throws IOException, NoSuchAlgorithmException {
        Path root = Path.of("specifications", "ATL105");
        Path sourcePath = root.resolve(Path.of("docs", "specs", "extracted_text.txt"));
        var mapper = new ObjectMapper();
        var evidence = mapper.readTree(root.resolve(Path.of("test-output", "test-json", "knowledge",
                "appendix-i-full-message-source-inspection.json")).toFile());
        assertThat(HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(Files.readAllBytes(sourcePath)))).isEqualTo(evidence.path("sourceSha256").asText());
        String source = Files.readAllLines(sourcePath).stream().map(String::strip)
                .collect(Collectors.joining("\n"));
        int exampleStart = source.indexOf("The parsed message format data is:");
        assertThat(exampleStart).isGreaterThanOrEqualTo(0);
        int exampleEnd = source.indexOf("where:", exampleStart);
        assertThat(exampleEnd).isGreaterThan(exampleStart);
        String example = source.substring(exampleStart + "The parsed message format data is:".length(),
                exampleEnd).replaceAll("\\s+", "").replace('\u25b2', '\u001c');
        int segmentStart = example.indexOf("100\u001c");
        int segmentEnd = example.indexOf("102\u001c", segmentStart);
        assertThat(segmentStart).isGreaterThanOrEqualTo(0);
        assertThat(segmentEnd).isGreaterThan(segmentStart);
        String segment = example.substring(segmentStart, segmentEnd);
        String[] fields = segment.split("\u001c", -1);
        var inspection = evidence.path("segment100Inspection");
        assertThat(fields.length - 1).isEqualTo(inspection.path("fieldCount").asInt()).isEqualTo(17);
        assertThat(Integer.parseInt(fields[1]))
                .isEqualTo(inspection.path("declaredLength").asInt()).isEqualTo(78);
        assertThat(segment.getBytes(StandardCharsets.US_ASCII).length)
                .isEqualTo(inspection.path("actualAsciiLength").asInt()).isEqualTo(82);
        assertThat(evidence.path("readiness").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(evidence.path("artifactShape").asText())
                .isEqualTo("SOURCE_DEPENDENCY_INSPECTION_NOT_FULL_REQUEST_CANDIDATE");
        var register = mapper.readTree(root.resolve(Path.of("registers",
                "atl105-communication-register.json")).toFile());
        boolean linked = false;
        for (var item : register.path("items")) {
            if (inspection.path("registerId").asText().equals(item.path("id").asText())) {
                assertThat(item.path("channel").asText()).isEqualTo("SME_QUERY");
                assertThat(item.path("status").asText()).isEqualTo("OPEN");
                linked = true;
            }
        }
        assertThat(linked).isTrue();
    }
}
