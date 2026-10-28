package com.coreauth.validator;

import com.coreauth.validator.canonical.Atl105FieldStructureRuleRunner;
import com.coreauth.validator.validation.Atl105ElementValueValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Atl105FieldStructureRuleRunnerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path PACK = Path.of("specifications", "ATL105");

    @Test
    void catalogDenominatorsAndProfileExecutorsStayExplicit() throws Exception {
        var inventory = MAPPER.readTree(PACK.resolve("test-output/test-solution-independent-review/source-derived-requirement-inventory.json").toFile());
        var profiles = MAPPER.readTree(PACK.resolve("docs/specs/kb/elements/validation-profiles.json").toFile());
        var rules = Atl105FieldStructureRuleRunner.loadFieldStructureRules(inventory);
        var executable = Atl105FieldStructureRuleRunner.loadExecutableProfiles(profiles, rules);
        var partialStructure = Atl105FieldStructureRuleRunner.partialStructureExecutors(rules);

        assertThat(rules).hasSize(326);
        assertThat(rules.values().stream().filter(rule -> "field".equals(rule.path("class").asText())).count()).isEqualTo(264);
        assertThat(rules.values().stream().filter(rule -> "structure".equals(rule.path("class").asText())).count()).isEqualTo(62);
        assertThat(profiles.path("profiles")).isNotEmpty();
        long expectedBindings = 0;
        for (var profile : profiles.path("profiles")) {
            var rule = rules.get(profile.path("existingRuleId").asText());
            if (rule != null) expectedBindings++;
        }
        assertThat(executable.values().stream().mapToLong(java.util.List::size).sum()).isEqualTo(expectedBindings);
        assertThat(executable.keySet()).contains("SEG100-R-015", "SEG105-R-020", "SEG108-R-025", "SEG110-R-021");
        assertThat(partialStructure)
            .containsEntry("SEG100-R-001", "SECTION11_REQUIRED_SEGMENT_100_EXACT_ONCE")
            .containsEntry("SEG100-R-008", "SECTION12_SEGMENT_101_NON_DUPLICATION")
            .containsEntry("SEG101-R-001", "SECTION11_SEGMENT_101_REQUIRES_SEGMENT_100");
        assertThat(Atl105FieldStructureRuleRunner.reportStatus(Atl105ElementValueValidator.Status.CHECKS_PASSED))
            .isEqualTo("PASS");
        assertThat(Atl105FieldStructureRuleRunner.reportStatus(Atl105ElementValueValidator.Status.INVALID))
            .isEqualTo("FAIL");
        assertThat(Atl105FieldStructureRuleRunner.reportStatus(Atl105ElementValueValidator.Status.REVIEW_REQUIRED))
            .isEqualTo("REVIEW_REQUIRED");
    }

    @Test
    void reportBindsParsedBytesAndRefusesToOverwriteEvidence(@TempDir Path temporary) throws Exception {
        Path inputs = Files.createDirectories(temporary.resolve("qe"));
        Path metadata = inputs.resolve("TC-1.meta.json");
        Path payload = inputs.resolve("TC-1.json");
        Files.writeString(metadata, "{\"testCaseId\":\"TC-1\",\"scenarioId\":\"SC-1\",\"fields\":[]}");
        Files.writeString(payload, "{\"Financial Request\":{\"MessageType\":\"ATL105\",\"NumSegments\":\"0\"}}");
        Path output = temporary.resolve("report.json");
        String[] arguments = {PACK.toString(), "fixture-run", inputs.toString(), output.toString()};
        Atl105FieldStructureRuleRunner.main(arguments);
        var report = MAPPER.readTree(output.toFile());
        assertThat(report.path("inputStabilityVerified").asBoolean()).isTrue();
        assertThat(report.path("configurationSha256").size()).isGreaterThan(5);
        assertThat(report.path("inputMessages")).hasSize(1);
        var input = report.path("inputMessages").get(0);
        assertThat(input.path("metadataSha256").asText()).isEqualTo(hash(metadata));
        assertThat(input.path("payloadSha256").asText()).isEqualTo(hash(payload));
        assertThat(input.path("testCaseId").asText()).isEqualTo("TC-1");
        assertThat(input.path("scenarioId").asText()).isEqualTo("SC-1");
        assertThatThrownBy(() -> Atl105FieldStructureRuleRunner.main(arguments))
            .isInstanceOf(java.io.IOException.class).hasMessageContaining("Refusing to overwrite");
    }

        @Test
        void recursiveScanCoversMultipleDataRootsAndReportsUnpairedPayloads(@TempDir Path temporary) throws Exception {
        Path delivery = temporary.resolve("delivery");
        Path first = Files.createDirectories(delivery.resolve("test_generation/test_generation/candidates/qe_shaped_test_data"));
        Path second = Files.createDirectories(delivery.resolve("test_data_10_oct/test_data_10_oct/candidates/qe_shaped_test_data"));
        String payload = "{\"Financial Request\":{\"MessageType\":\"ATL105\",\"NumSegments\":\"0\"}}";
        for (Path root : List.of(first, second)) {
            Files.writeString(root.resolve("TC-1.meta.json"), "{\"testCaseId\":\"TC-1\",\"scenarioId\":\"SC-1\",\"fields\":[]}");
            Files.writeString(root.resolve("TC-1.json"), payload);
        }
        Path unpaired = second.resolve("TC-2.json");
        Files.writeString(unpaired, payload);
        Path output = temporary.resolve("recursive-report.json");

        Atl105FieldStructureRuleRunner.main(new String[]{PACK.toString(), "recursive-fixture",
            delivery.toString(), output.toString(), "--recursive"});

        var report = MAPPER.readTree(output.toFile());
        assertThat(report.path("inputScanMode").asText()).isEqualTo("RECURSIVE_QE_SHAPED_TEST_DATA");
        assertThat(report.path("inputStabilityVerified").asBoolean()).isTrue();
        assertThat(report.path("metadataSidecarFilesDiscovered").asInt()).isEqualTo(2);
        assertThat(report.path("payloadFilesDiscovered").asInt()).isEqualTo(3);
        assertThat(report.path("metadataWithoutPayloadCount").asInt()).isZero();
        assertThat(report.path("payloadFilesWithoutMetadataCount").asInt()).isEqualTo(1);
        assertThat(report.path("payloadFilesWithoutMetadata").get(0).path("relativePath").asText())
            .isEqualTo("test_data_10_oct/test_data_10_oct/candidates/qe_shaped_test_data/TC-2.json");
        assertThat(report.path("inputMessages").get(0).path("messageId").asText())
            .contains("/qe_shaped_test_data/TC-1.meta.json");
        assertThat(report.path("inputMessages").get(1).path("messageId").asText())
            .contains("/qe_shaped_test_data/TC-1.meta.json");
        assertThat(report.path("inputMessages").get(0).path("messageId").asText())
            .isNotEqualTo(report.path("inputMessages").get(1).path("messageId").asText());
        assertThat(report.path("evaluations").get(0).path("messageId").asText())
            .isEqualTo(report.path("inputMessages").get(0).path("messageId").asText());
        assertThat(report.path("evaluations").get(3).path("messageId").asText())
            .isEqualTo(report.path("inputMessages").get(1).path("messageId").asText());
            for (var evaluation : report.path("evaluations")) {
                assertThat(evaluation.path("messageId").asText())
                    .isIn(report.path("inputMessages").findValuesAsText("messageId"));
            }
        }

    private static String hash(Path path) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(path)));
    }
}