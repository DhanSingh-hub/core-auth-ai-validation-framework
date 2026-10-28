package com.coreauth.validator;

import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.canonical.Chapter11MessageLayoutValidator;
import com.coreauth.validator.validation.Atl105CalendarDate;
import com.coreauth.validator.validation.Atl105ElementValueValidator;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Chapter13ElementProfileTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @TempDir Path directory;

    @Test
    void allIndependentProbesExecuteWithExpectedElementFindings() throws Exception {
        var validator = new Atl105ElementValueValidator(Atl105Paths.root());
        Map<String, JsonNode> profiles = profiles();
        JsonNode fixtures = mapper.readTree(Atl105Paths.testJson("chapter-13-element-probes.json").toFile());
        assertThat(fixtures.path("profiles")).hasSize(12);
        for (JsonNode fixture : fixtures.path("profiles")) {
            JsonNode profile = profiles.get(fixture.path("profileId").asText());
            assertThat(profile).isNotNull();
            for (String kind : new String[]{"positive", "negative", "review"}) {
                Status expected = "positive".equals(kind) ? Status.CHECKS_PASSED
                        : "negative".equals(kind) ? Status.INVALID : Status.REVIEW_REQUIRED;
                for (JsonNode value : fixture.path(kind)) {
                    var result = validator.validate(observation(profile, value));
                    assertThat(result.findings()).as(fixture.path("profileId").asText() + " " + kind)
                            .anyMatch(finding -> profile.path("element").asText().equals(finding.element())
                                    && finding.status() == expected);
                    if (expected == Status.INVALID) assertThat(result.status()).isEqualTo(Status.INVALID);
                }
            }
        }
    }

    @Test
    void numericJsonValuesCannotSatisfyTextRepresentations() throws Exception {
        var validator = new Atl105ElementValueValidator(Atl105Paths.root());
        for (JsonNode profile : profiles().values()) {
            if (!profile.path("id").asText().contains("TOTALS-REQUEST")) continue;
            var input = observation(profile, mapper.getNodeFactory().numberNode(1111));
            assertThat(validator.validate(input).findings()).anyMatch(finding ->
                    finding.element().equals(profile.path("element").asText()) && finding.status() == Status.INVALID);
        }
    }

    @Test
    void fullObservationRequiresFieldsButPartialObservationDoesNotFabricateValues() throws Exception {
        var validator = new Atl105ElementValueValidator(Atl105Paths.root());
        var input = observation(profiles().get("E86-TOTALS-REQUEST"), mapper.getNodeFactory().textNode("000001"));
        ((ObjectNode) input.path("elements")).put("85", "105");
        input.remove("completeness");
        var full = validator.validate(input);
        assertThat(full.status()).isEqualTo(Status.INVALID);
        assertThat(full.findings()).anyMatch(finding -> finding.element().equals("43") && finding.status() == Status.INVALID);
        input.put("completeness", "PARTIAL");
        assertThat(validator.validate(input).findings())
                .anyMatch(finding -> finding.element().equals("43") && finding.status() == Status.REVIEW_REQUIRED);
    }

    @Test
    void contextDoesNotApplyRequestSpecialDatesToResponsesOrUnverifiedFamilies() throws Exception {
        var validator = new Atl105ElementValueValidator(Atl105Paths.root());
        var input = observation(profiles().get("E105-TOTALS-REQUEST"), mapper.getNodeFactory().textNode("222222"));
        input.put("messageFamily", "Approved Totals Response");
        input.put("segment", "MESSAGE");
        assertThat(validator.validate(input).findings()).noneMatch(finding -> finding.status() == Status.CHECKS_PASSED);
        input.put("messageFamily", "Unverified Request");
        assertThat(validator.validate(input).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void calendarChecksDoNotLeakDataOrResolveYearZeroCentury() {
        for (String value : new String[]{"010126", "022924", "123199", "022900"}) {
            assertThat(Atl105CalendarDate.isValidMmddyy(value)).isTrue();
        }
        for (String value : new String[]{"000000", "022923", "043126", "133126", "PRIVATE", "0101260"}) {
            assertThat(Atl105CalendarDate.isValidMmddyy(value)).isFalse();
        }
        assertThat(Atl105CalendarDate.isValidMmddyy(null)).isFalse();
        assertThat(Atl105CalendarDate.needsCenturyForLeapDay("022900")).isTrue();
        assertThat(Atl105CalendarDate.needsCenturyForLeapDay("022924")).isFalse();
    }

    @Test
    void ungroundedCalendarConfigurationFailsExplicitly() throws Exception {
        Path pack = copyProfileInputs();
        Path file = pack.resolve("docs").resolve("specs").resolve("kb").resolve("elements").resolve("validation-profiles.json");
        ObjectNode catalog = (ObjectNode) mapper.readTree(file.toFile());
        for (JsonNode profile : catalog.path("profiles")) {
            if ("E105-TOTALS-REQUEST".equals(profile.path("id").asText())) {
                ((ObjectNode) profile).putArray("dateSpecialCodes").add("666666");
            }
        }
        mapper.writeValue(file.toFile(), catalog);
        assertThatThrownBy(() -> new Atl105ElementValueValidator(pack))
                .isInstanceOf(java.io.IOException.class).hasMessageContaining("Unverified");
    }

    @Test
    void chapterElevenDelegatesTotalsElementsAndChecksParentContext() throws Exception {
        ObjectNode envelope = mapper.createObjectNode().put("specificationVersion", "2026-3")
                .put("messageFamily", "Totals Request").put("completeness", "PARTIAL");
        envelope.putObject("elements").put("55", "ATL105").put("63", "01");
        ObjectNode segment = envelope.putArray("segments").addObject().put("segment", "105").put("dataSection", 3);
        ObjectNode supplied = segment.putObject("observation");
        ObjectNode elements = supplied.putObject("elements").put("85", "105").put("86", "000000");
        var validator = new Chapter11MessageLayoutValidator();
        assertThat(validator.validate(envelope).findings()).anyMatch(finding ->
                finding.ruleId().equals("SEG105-R-013") && finding.status().name().equals("INVALID"));
        elements.put("86", "000001").put("105", "043126");
        assertThat(validator.validate(envelope).findings()).anyMatch(finding ->
                finding.ruleId().equals("SEG105-R-008") && finding.status().name().equals("INVALID"));
        elements.put("105", "022924");
        assertThat(validator.validate(envelope).status().name()).isEqualTo("REVIEW_REQUIRED");
        supplied.put("messageFamily", "Unknown Request");
        assertThat(validator.validate(envelope).findings()).anyMatch(finding ->
                finding.scope().equals("chapter13-context") && finding.status().name().equals("INVALID"));
    }

    @Test
    void chapterElevenChecksElectronicMailResponseCalendarInsteadOfWidthOnly() throws Exception {
        ObjectNode envelope = mapper.createObjectNode().put("specificationVersion", "2026-3")
                .put("messageFamily", "Electronic Mail Response").put("completeness", "PARTIAL");
        ObjectNode elements = envelope.putObject("elements").put("45", "043126");
        envelope.putArray("segments");
        var validator = new Chapter11MessageLayoutValidator();
        assertThat(validator.validate(envelope).findings()).anyMatch(finding ->
                finding.ruleId().equals("SEG109-R-022") && finding.status().name().equals("INVALID"));
        elements.put("45", "022924");
        assertThat(validator.validate(envelope).status().name()).isEqualTo("REVIEW_REQUIRED");
    }

    private Path copyProfileInputs() throws Exception {
        Path pack = directory.resolve("pack");
        Path kb = pack.resolve("docs").resolve("specs").resolve("kb");
        Files.createDirectories(kb.resolve("elements"));
        Files.copy(Atl105Paths.docs().resolve("specs").resolve("extracted_text.txt"),
                pack.resolve("docs").resolve("specs").resolve("extracted_text.txt"));
        Files.copy(Atl105Paths.docs().resolve("atl105_complete_templates.json"), pack.resolve("docs").resolve("atl105_complete_templates.json"));
        Files.copy(Atl105Paths.docs().resolve("specs").resolve("kb").resolve("elements").resolve("validation-profiles.json"),
                kb.resolve("elements").resolve("validation-profiles.json"));
        for (JsonNode profile : profiles().values()) {
            Path relative = Path.of(profile.path("existingRuleCatalog").asText());
            Path target = kb.resolve(relative);
            if (!Files.exists(target)) {
                Files.createDirectories(target.getParent());
                Files.copy(Atl105Paths.docs().resolve("specs").resolve("kb").resolve(relative), target);
            }
        }
        return pack;
    }

    private Map<String, JsonNode> profiles() throws Exception {
        Map<String, JsonNode> profiles = new HashMap<>();
        for (JsonNode profile : mapper.readTree(Atl105Paths.docs().resolve("specs").resolve("kb")
                .resolve("elements").resolve("validation-profiles.json").toFile()).path("profiles")) {
            profiles.put(profile.path("id").asText(), profile);
        }
        return profiles;
    }

    private ObjectNode observation(JsonNode profile, JsonNode value) {
        ObjectNode input = mapper.createObjectNode().put("specificationVersion", "2026-3")
                .put("messageFamily", profile.path("messageFamily").asText())
                .put("segment", profile.path("segment").asText()).put("completeness", "PARTIAL");
        input.putObject("elements").set(profile.path("element").asText(), value);
        return input;
    }
}
