package com.coreauth.validator;

import com.coreauth.validator.validation.Atl105AiElementIntake;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Atl105AiElementIntakeTest {
    @TempDir Path directory;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path PACK = Path.of("specifications", "ATL105");

    @Test
    void fragmentWithoutSegment100AndFourDigitFleetLengthIsInvalid() throws Exception {
        var assessment = new Atl105AiElementIntake(PACK).assess(meta("Financial Transaction Request", "positive",
            field("101", "SegmentType", "Segment Type", "101"), field("101", "SegmentLength", "Segment Length", "0000")),
            json("{\"Financial Request\":{\"MessageType\":\"ATL105\",\"NumSegments\":\"1\",\"Fleet Segment\":{}}}"));
        assertThat(assessment.status()).isEqualTo(Status.INVALID);
        assertThat(assessment.observations().toString()).contains("Required Segment 100 absent", "E84-SEG101-LENGTH")
            .doesNotContain("0000");
    }

    @Test
    void responseFamilyOnRequestPayloadIsInvalidAndUnknownFamilyIsReview() throws Exception {
        var intake = new Atl105AiElementIntake(PACK);
        String payload = "{\"Financial Request\":{\"MessageType\":\"ATL105\",\"NumSegments\":\"1\",\"Standard\":{}}}";
        var fields = new JsonNode[]{field("100", "SegmentType", "Segment Type", "100"), field("100", "SegmentLength", "Segment Length", "050"),
            field("100", "SequenceNumber", "Sequence Number", "123456")};
        assertThat(intake.assess(meta("EMV Financial Transaction Response", "positive", fields), json(payload)).status()).isEqualTo(Status.INVALID);
        var unknown = intake.assess(meta("Auth Completion (0220)", "positive", fields), json(payload));
        assertThat(unknown.status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(unknown.intakeFindings().toString()).contains("not a Section 11 message family");
    }

    @Test
    void aliasContradictingChapter13NameIsRejectedAndUnknownRootIsReview() throws Exception {
        var intake = new Atl105AiElementIntake(PACK);
        var assessment = intake.assess(meta("Financial Transaction Request", "positive",
            field("111", "VariableInformation", "Variable Information", "X")),
            json("{\"Financial Request\":{\"MessageType\":\"ATL105\",\"NumSegments\":\"1\",\"Var\":{}}}"));
        assertThat(assessment.intakeFindings().toString()).contains("Unmapped AI field 111/VariableInformation");
        assertThat(intake.mappingCounts()).containsKey("rejectedAliasContradictsChapter13Name");
        assertThat(intake.assess(meta("Financial Transaction Request", "positive"), json("{\"Other\":{}}")).status())
            .isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void semanticModeValidatesActualPayloadAndRejectsContradictoryMetadata() throws Exception {
        var intake = new Atl105AiElementIntake(PACK, true);
        var meta = meta("Financial Transaction Request", "positive",
                field("100", "SequenceNumber", "Sequence Number", "000001"));
        var payload = json("{\"Financial Request\":{\"MessageType\":\"ATL105\",\"NumSegments\":\"1\","
                + "\"Standard\":{\"SegmentType\":\"100\",\"SegmentLength\":\"050\",\"SequenceNumber\":\"000000\"}}}");
        var assessment = intake.assess(meta, payload);
        assertThat(assessment.status()).isEqualTo(Status.INVALID);
        assertThat(assessment.intakeFindings()).anyMatch(f -> f.reason().contains("contradicts actual payload"));
        assertThat(assessment.observations()).anyMatch(o -> o.result().findings().stream()
                .anyMatch(f -> f.ruleId().equals("CH13-E86-DOMAIN") && f.status() == Status.INVALID));
    }

    @Test
    void metadataOnlyValuesNeverBecomeExecutedPayloadEvidence() throws Exception {
        var intake = new Atl105AiElementIntake(PACK, true);
        var assessment = intake.assess(meta("Financial Transaction Request", "positive",
                field("100", "SequenceNumber", "Sequence Number", "000001")),
                json("{\"Financial Request\":{\"MessageType\":\"ATL105\",\"NumSegments\":\"1\","
                        + "\"Standard\":{\"SegmentType\":\"100\",\"SegmentLength\":\"050\"}}}"));
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(assessment.intakeFindings()).anyMatch(f -> f.reason().contains("metadata-only value not validated"));
        assertThat(assessment.observations()).noneMatch(o -> o.result().findings().stream()
                .anyMatch(f -> f.ruleId().equals("CH13-E86-DOMAIN") && f.status() == Status.CHECKS_PASSED));
    }

    @Test
    void semanticModeDiscoversActualMappedFieldsAbsentFromMetadataAndRejectsNumericCoercion() throws Exception {
        var intake = new Atl105AiElementIntake(PACK, true);
        var assessment = intake.assess(meta("Financial Transaction Request", "negative"),
                json("{\"Financial Request\":{\"MessageType\":\"ATL105\",\"NumSegments\":\"1\","
                        + "\"Standard\":{\"SegmentType\":\"100\",\"SegmentLength\":\"050\",\"SequenceNumber\":\"000000\"}}}"));
        assertThat(assessment.observations()).anyMatch(o -> o.result().findings().stream()
                .anyMatch(f -> f.ruleId().equals("CH13-E86-DOMAIN") && f.status() == Status.INVALID));
        assessment = intake.assess(meta("Financial Transaction Request", "negative"),
                json("{\"Financial Request\":{\"MessageType\":\"ATL105\",\"NumSegments\":\"1\","
                        + "\"Standard\":{\"SegmentType\":\"100\",\"SegmentLength\":\"050\",\"SequenceNumber\":1}}}"));
        assertThat(assessment.status()).isEqualTo(Status.INVALID);
    }

    @Test
    void semanticIntakeDoesNotCollapseRepeatedSegmentsOrOverrideActualEmvFamily() throws Exception {
        var intake = new Atl105AiElementIntake(PACK, true);
        var repeated = intake.assess(meta("Financial Transaction Request", "positive"),
                json("{\"Financial Request\":{\"MessageType\":\"ATL105\",\"NumSegments\":\"2\","
                        + "\"First\":{\"SegmentType\":\"100\",\"SegmentLength\":\"050\"},"
                        + "\"Second\":{\"SegmentType\":\"100\",\"SegmentLength\":\"050\"}}}"));
        assertThat(repeated.status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(repeated.observations().stream().filter(o -> o.segment().equals("100")).count()).isEqualTo(2);
        var wrongFamily = intake.assess(meta("EMV Financial Transaction Request", "positive"),
                json("{\"Financial Request\":{\"MessageType\":\"ATL105\",\"NumSegments\":\"1\","
                        + "\"Standard\":{\"SegmentType\":\"100\",\"SegmentLength\":\"050\"}}}"));
        assertThat(wrongFamily.intakeFindings()).anyMatch(f -> f.reason().contains("Metadata message family contradicts"));
    }

    @Test
    void repeatedActualOccurrencesAreAllValidatedAndAmbiguousMetadataIsNotComparedWithTheFirst() throws Exception {
        var intake = new Atl105AiElementIntake(PACK, true);
        var metadata = meta("Financial Transaction Request", "positive", field("100", "SequenceNumber", "Sequence Number", "000001"));
        var payload = json("{\"Financial Request\":{\"MessageType\":\"ATL105\",\"NumSegments\":\"2\","
                + "\"First\":{\"SegmentType\":\"100\",\"SequenceNumber\":\"000001\"},"
                + "\"Second\":{\"SegmentType\":\"100\",\"SequenceNumber\":\"000000\"}}}");
        var assessment = intake.assess(metadata, payload);
        assertThat(assessment.status()).isEqualTo(Status.INVALID);
        assertThat(assessment.intakeFindings()).anyMatch(f -> f.status() == Status.REVIEW_REQUIRED && f.reason().contains("unambiguous metadata occurrence"));
        assertThat(assessment.intakeFindings()).noneMatch(f -> f.reason().contains("contradicts actual payload value"));
        assertThat(assessment.observations().stream().filter(o -> o.segment().equals("100")).count()).isEqualTo(2);
        assertThat(assessment.observations()).anyMatch(o -> o.result().findings().stream()
                .anyMatch(f -> f.ruleId().equals("CH13-E86-DOMAIN") && f.status() == Status.INVALID));
        ((com.fasterxml.jackson.databind.node.ObjectNode) metadata.path("fields").path(0)).put("segmentFriendlyName", "First");
        assertThat(intake.assess(metadata, payload).intakeFindings()).noneMatch(f -> f.reason().contains("unambiguous metadata occurrence"));
    }

    @Test
    void semanticIntakeCarriesHistoryToTheSourceOracleAndDoesNotModifyInputs() throws Exception {
        var intake = new Atl105AiElementIntake(PACK, true);
        var metadata = (com.fasterxml.jackson.databind.node.ObjectNode) meta("Financial Transaction Request", "positive");
        metadata.putObject("history").putObject("storeNumberActions").put("displayed", true).put("printed", false);
        var payload = json("{\"Financial Request\":{\"MessageType\":\"ATL105\",\"NumSegments\":\"2\","
                + "\"Standard\":{\"SegmentType\":\"100\",\"SegmentLength\":\"050\"},"
                + "\"PhoneLoad\":{\"SegmentType\":\"DL1\",\"SegmentLength\":\"050\",\"StoreNumber\":\"********\"}}}");
        var savedMeta = metadata.deepCopy();
        var savedPayload = payload.deepCopy();
        var assessment = intake.assess(metadata, payload);
        assertThat(assessment.observations()).anyMatch(o -> o.result().findings().stream()
                .anyMatch(f -> f.ruleId().equals("CH13-E98-PROCESSING-MASKED-ACTIONS") && f.status() == Status.INVALID));
        assertThat(metadata).isEqualTo(savedMeta);
        assertThat(payload).isEqualTo(savedPayload);
    }

    @Test
    void semanticModeAcceptsExactSourceFamilyRootsAndUsesActualEnvelopeCount() throws Exception {
        var intake = new Atl105AiElementIntake(PACK, true);
        var assessment = intake.assess(meta("Financial Transaction Request", "negative"),
                json("{\"Financial Transaction Request\":{\"MessageFormatVersionIdentifier\":\"ATL105\",\"NumberofSegments\":\"2\","
                        + "\"Standard Segment\":{\"SegmentType\":\"100\",\"SegmentLength\":\"050\",\"SequenceNumber\":\"000000\"}}}"));
        assertThat(assessment.observations()).isNotEmpty();
        assertThat(assessment.observations()).anyMatch(o -> o.result().findings().stream()
                .anyMatch(f -> f.ruleId().equals("CH13-E86-DOMAIN") && f.status() == Status.INVALID));
        assertThat(assessment.observations()).anyMatch(o -> o.segment().equals("MESSAGE")
                && o.result().findings().stream().anyMatch(f -> f.element().equals("63") && f.status() == Status.INVALID));
    }

    @Test
    void semanticIntakeUsesObservedLoadIndicatorInsteadOfProducerSegmentClaims() throws Exception {
        var intake = new Atl105AiElementIntake(PACK, true);
        var metadata = meta("Software Load Phone Response", "field_constraint",
                field("DL2", "StoreNumber", "Store Number", "123"));
        var payload = json("{\"Software Load Phone Response\":{\"Merchant\":{\"DataTypeIndicator\":\"#\",\"StoreNumber\":\"0\"}}}");
        var before = payload.deepCopy();
        var assessment = intake.assess(metadata, payload);
        assertThat(assessment.observations()).anyMatch(o -> o.segment().equals("DL1") && o.result().findings().stream()
                .anyMatch(f -> f.ruleId().equals("CH13-E98-STORE") && f.status() == Status.INVALID));
        assertThat(assessment.observations()).noneMatch(o -> o.segment().equals("DL2"));
        assertThat(intake.mappingCounts()).containsEntry("actualSourceLoadIndicator", 1L);
        assertThat(payload).isEqualTo(before);
        var ambiguous = intake.assess(meta("Phone Load Response", "field_constraint"),
                json("{\"Phone Load Response\":{\"Key\":{\"DataTypeIndicator\":\"&\",\"StoreNumber\":\"0\"}}}"));
        assertThat(ambiguous.intakeFindings()).anyMatch(f -> f.reason().contains("no explicit textual source identity"));
    }

    @Test
    void cliPersistsPerCaseVerdictsAndInputHashesWithoutChangingProducerInputs() throws Exception {
        Path inputDirectory = Files.createDirectory(directory.resolve("inputs"));
        Path metadataFile = inputDirectory.resolve("TC-AI.meta.json"), payloadFile = inputDirectory.resolve("TC-AI.json");
        MAPPER.writeValue(metadataFile.toFile(), meta("Financial Transaction Request", "negative"));
        MAPPER.writeValue(payloadFile.toFile(), json("{\"Financial Transaction Request\":{\"NumberofSegments\":\"1\","
                + "\"Standard\":{\"SegmentType\":\"100\",\"SequenceNumber\":\"000000\"}}}"));
        byte[] metadataBefore = Files.readAllBytes(metadataFile), payloadBefore = Files.readAllBytes(payloadFile);
        Path reportFile = directory.resolve("report.json");
        Atl105AiElementIntake.main(new String[]{PACK.toString(), inputDirectory.toString(), reportFile.toString(), "--chapter13-semantics"});
        var report = MAPPER.readTree(reportFile.toFile());
        assertThat(report.path("chapter13Semantics").asBoolean()).isTrue();
        assertThat(report.path("executions")).hasSize(1);
        var execution = report.path("executions").get(0);
        assertThat(execution.path("status").asText()).isEqualTo("INVALID");
        assertThat(execution.path("payloadSha256").asText()).isEqualTo(
                HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(payloadBefore)));
        assertThat(Files.readAllBytes(payloadFile)).isEqualTo(payloadBefore);
        assertThat(Files.readAllBytes(metadataFile)).isEqualTo(metadataBefore);
        assertThatThrownBy(() -> Atl105AiElementIntake.main(new String[]{PACK.toString(), inputDirectory.toString(),
                metadataFile.toString(), "--chapter13-semantics"})).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("outside the producer input folder");
        assertThat(Files.readAllBytes(metadataFile)).isEqualTo(metadataBefore);
    }

    private static JsonNode meta(String family, String scenario, JsonNode... fields) {
        var meta = MAPPER.createObjectNode();
        meta.put("testCaseId", "TC-TEST");
        meta.put("messageFamily", family);
        meta.put("scenarioType", scenario);
        var list = meta.putArray("fields");
        for (JsonNode field : fields) list.add(field);
        return meta;
    }

    private static JsonNode field(String segment, String element, String specName, String value) {
        var field = MAPPER.createObjectNode();
        field.put("segment", segment);
        field.put("element", element);
        field.put("specElementName", specName);
        field.put("value", value);
        return field;
    }

    private static JsonNode json(String text) throws Exception {
        return MAPPER.readTree(text);
    }
}
