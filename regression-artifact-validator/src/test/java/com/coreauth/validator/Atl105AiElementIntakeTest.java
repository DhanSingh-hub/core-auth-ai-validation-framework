package com.coreauth.validator;

import com.coreauth.validator.validation.Atl105AiElementIntake;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    void exactSectionElevenRootFamilyIsAcceptedForIntake() throws Exception {
        var assessment = new Atl105AiElementIntake(PACK).assess(meta("Electronic Mail Request", "positive"),
            json("{\"Electronic Mail Request\":{}}"));
        assertThat(assessment.intakeFindings().toString()).doesNotContain("root label has no reviewed family mapping");
        assertThat(assessment.intakeFindings().toString()).doesNotContain("not a Section 11 message family");
        assertThat(assessment.observations()).anySatisfy(observed -> assertThat(observed.segment()).isEqualTo("MESSAGE"));
        var exactTemplateKey = new Atl105AiElementIntake(PACK).assess(meta("Financial Transaction Request", "positive"),
            json("{\"Financial Transaction Request\":{}}"));
        assertThat(exactTemplateKey.intakeFindings().toString())
            .doesNotContain("root label has no reviewed family mapping", "not a Section 11 message family");
        var conflictingFamily = new Atl105AiElementIntake(PACK).assess(meta("EMV Financial Transaction Request", "positive"),
            json("{\"Financial Transaction Request\":{}}"));
        assertThat(conflictingFamily.intakeFindings().toString())
            .contains("AI metadata family conflicts with the mapped payload-root family");
    }

    @Test
    void dataSectionOneSegmentCountIsMappedToEnvelopeElement63() throws Exception {
        var assessment = new Atl105AiElementIntake(PACK).assess(meta("Electronic Mail Request", "positive",
            field("DS1", "NumberofSegments", "NumberofSegments", "1"),
            field("109", "SegmentType", "Segment Type", "109")),
            json("{\"Electronic Mail Request\":{\"NumberofSegments\":\"1\",\"Electronic Mail Data Segment\":{\"SegmentType\":\"109\"}}}"));
        assertThat(assessment.intakeFindings().toString())
            .doesNotContain("Unmapped AI envelope field NumberofSegments", "Unmapped AI field DS1/NumberofSegments",
                "Meta segment identities do not match the payload segment count", "Metadata and payload envelope values conflict");
        var conflict = new Atl105AiElementIntake(PACK).assess(meta("Electronic Mail Request", "positive",
            field("DS1", "NumberofSegments", "NumberofSegments", "2"),
            field("109", "SegmentType", "Segment Type", "109")),
            json("{\"Electronic Mail Request\":{\"NumberofSegments\":\"1\",\"Electronic Mail Data Segment\":{\"SegmentType\":\"109\"}}}"));
        assertThat(conflict.intakeFindings().toString()).contains("Metadata and payload envelope values conflict");
    }

    @Test
    void repeatedPayloadSegmentTypesFailTheExactOnceRule() throws Exception {
        var assessment = new Atl105AiElementIntake(PACK).assess(meta("Financial Transaction Request", "positive",
            field("100", "SegmentType", "Segment Type", "100")),
            json("{\"Financial Transaction Request\":{\"First Segment\":{\"SegmentType\":\"100\"},\"Second Segment\":{\"SegmentType\":\"100\"}}}"));
        assertThat(assessment.observations()).anySatisfy(observed -> {
            if (!"MESSAGE".equals(observed.segment())) return;
            assertThat(observed.result().findings()).anySatisfy(finding -> {
                assertThat(finding.ruleId()).isEqualTo("SEG100-R-001");
                assertThat(finding.status()).isEqualTo(Status.INVALID);
                assertThat(finding.reason()).contains("occurs 2 times");
            });
        });
    }

    @Test
    void nestedSegmentTypeValueDoesNotCountAsMessageSegmentOccurrence() throws Exception {
        var assessment = new Atl105AiElementIntake(PACK).assess(meta("Financial Transaction Request", "positive",
            field("100", "SegmentType", "Segment Type", "100")),
            json("{\"Financial Transaction Request\":{\"Standard Segment\":{\"SegmentType\":\"100\"},\"Transaction Metadata\":{\"Audit\":{\"SegmentType\":\"100\"}}}}"));
        assertThat(assessment.observations()).anySatisfy(observed -> {
            if (!"MESSAGE".equals(observed.segment())) return;
            assertThat(observed.result().findings()).anySatisfy(finding -> {
                assertThat(finding.ruleId()).isEqualTo("SEG100-R-001");
                assertThat(finding.status()).isEqualTo(Status.CHECKS_PASSED);
                assertThat(finding.reason()).contains("occurs exactly once");
            });
        });
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
        assertThat(report.path("intakeFormatProfileSchemaVersion").asInt()).isEqualTo(1);
        assertThat(report.path("intakeFormatProfilesSha256").asText()).hasSize(64);
        assertThat(report.path("intakeFormatProfileCounts").path("qe-meta-sibling-v1").asInt()).isEqualTo(1);
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

    @Test
    void cliDiscoversNestedMetadataAndSupportsDeclaredInlineAndConfinedPayloads() throws Exception {
        Path inputDirectory = Files.createDirectory(directory.resolve("variant-inputs"));
        Path payloadDirectory = Files.createDirectories(inputDirectory.resolve("delivery/data"));
        Path metadataDirectory = Files.createDirectories(inputDirectory.resolve("delivery/index"));
        Path declaredPayload = payloadDirectory.resolve("request-body.json");
        JsonNode request = json("{\"Financial Transaction Request\":{\"NumberofSegments\":\"1\","
                + "\"Standard\":{\"SegmentType\":\"100\",\"SequenceNumber\":\"000000\"}}}");
        MAPPER.writeValue(declaredPayload.toFile(), request);

        ObjectNode declaredMetadata = (ObjectNode) meta("Financial Transaction Request", "negative");
        declaredMetadata.put("testCaseId", "TC-DECLARED");
        declaredMetadata.put("test_data_file", "delivery/data/request-body.json");
        Path declaredMetadataFile = metadataDirectory.resolve("TC-DECLARED.meta.json");
        MAPPER.writeValue(declaredMetadataFile.toFile(), declaredMetadata);

        ObjectNode inlineMetadata = (ObjectNode) meta("Financial Transaction Request", "negative");
        inlineMetadata.put("testCaseId", "TC-INLINE");
        inlineMetadata.set("payload", request.deepCopy());
        Path inlineMetadataFile = metadataDirectory.resolve("TC-INLINE.meta.json");
        MAPPER.writeValue(inlineMetadataFile.toFile(), inlineMetadata);

        Path alternateDirectory = Files.createDirectories(inputDirectory.resolve("alternate-format"));
        ObjectNode alternateMetadata = (ObjectNode) meta("Financial Transaction Request", "negative");
        alternateMetadata.put("testCaseId", "TC-SUFFIX");
        MAPPER.writeValue(alternateDirectory.resolve("metadata.json").toFile(), alternateMetadata);
        MAPPER.writeValue(alternateDirectory.resolve("payload.json").toFile(), request);
        ObjectNode dottedMetadata = (ObjectNode) meta("Financial Transaction Request", "negative");
        dottedMetadata.put("testCaseId", "TC-DOTTED");
        MAPPER.writeValue(alternateDirectory.resolve("case.metadata.json").toFile(), dottedMetadata);
        MAPPER.writeValue(alternateDirectory.resolve("case.json").toFile(), request);

        ObjectNode aliasedMetadata = (ObjectNode) json("""
            {"case_id":"TC-ALIASED","message_family":"Financial Transaction Request",
             "scenario_type":"negative","test_fields":[
               {"segment_id":"100","element_name":"SequenceNumber",
                "spec_element_name":"Sequence Number","value":"000000"}],
             "payload_file":"delivery/data/wrapped-request.json"}
            """);
        Path aliasedMetadataFile = metadataDirectory.resolve("TC-ALIASED.meta.json");
        MAPPER.writeValue(aliasedMetadataFile.toFile(), aliasedMetadata);
        MAPPER.writeValue(payloadDirectory.resolve("wrapped-request.json").toFile(),
            json("{\"request_body\":{\"Financial Transaction Request\":{\"NumberofSegments\":\"1\","
                + "\"Standard\":{\"SegmentType\":\"100\",\"SequenceNumber\":\"000000\"}}}}"));

        Path externalPayload = directory.resolve("external-request.json");
        MAPPER.writeValue(externalPayload.toFile(), request);
        ObjectNode escapedMetadata = (ObjectNode) meta("Financial Transaction Request", "negative");
        escapedMetadata.put("testCaseId", "TC-ESCAPE");
        escapedMetadata.put("payload_file", "../../external-request.json");
        Path escapedMetadataFile = metadataDirectory.resolve("TC-ESCAPE.meta.json");
        MAPPER.writeValue(escapedMetadataFile.toFile(), escapedMetadata);

        Path malformedPayload = payloadDirectory.resolve("malformed.json");
        Files.writeString(malformedPayload, "{ malformed");
        ObjectNode malformedMetadata = (ObjectNode) meta("Financial Transaction Request", "negative");
        malformedMetadata.put("testCaseId", "TC-MALFORMED");
        malformedMetadata.put("payload_file", "delivery/data/malformed.json");
        MAPPER.writeValue(metadataDirectory.resolve("TC-MALFORMED.meta.json").toFile(), malformedMetadata);
        byte[] externalBytes = Files.readAllBytes(externalPayload);

        Path reportFile = directory.resolve("variant-report.json");
        Atl105AiElementIntake.main(new String[]{PACK.toString(), inputDirectory.toString(), reportFile.toString(),
                "--chapter13-semantics"});
        JsonNode executions = MAPPER.readTree(reportFile.toFile()).path("executions");
        assertThat(executions).hasSize(7);
        JsonNode declared = executionFor(executions, "TC-DECLARED");
        assertThat(declared.path("metadataFile").asText()).isEqualTo("delivery/index/TC-DECLARED.meta.json");
        assertThat(declared.path("payloadFile").asText()).isEqualTo("delivery/data/request-body.json");
        assertThat(declared.path("formatProfileId").asText()).isEqualTo("qe-meta-sibling-v1");
        assertThat(declared.path("payloadSha256").asText()).isNotBlank();
        JsonNode inline = executionFor(executions, "TC-INLINE");
        assertThat(inline.path("payloadFile").asText()).isEqualTo("INLINE_METADATA");
        assertThat(inline.path("payloadSha256").asText()).isNotBlank();
        JsonNode escaped = executionFor(executions, "TC-ESCAPE");
        assertThat(escaped.path("payloadFile").asText()).isEqualTo("../../external-request.json");
        assertThat(escaped.path("payloadSha256").isNull()).isTrue();
        JsonNode alternate = executionFor(executions, "TC-SUFFIX");
        assertThat(alternate.path("formatProfileId").asText()).isEqualTo("alternate-json-metadata-v1");
        assertThat(alternate.path("payloadFile").asText()).isEqualTo("alternate-format/payload.json");
        assertThat(alternate.path("payloadSha256").asText()).isNotBlank();
        JsonNode dotted = executionFor(executions, "TC-DOTTED");
        assertThat(dotted.path("formatProfileId").asText()).isEqualTo("alternate-json-metadata-v1");
        assertThat(dotted.path("payloadFile").asText()).isEqualTo("alternate-format/case.json");
        JsonNode aliased = executionFor(executions, "TC-ALIASED");
        assertThat(aliased.path("formatProfileId").asText()).isEqualTo("qe-meta-sibling-v1");
        assertThat(aliased.path("payloadFile").asText()).isEqualTo("delivery/data/wrapped-request.json");
        assertThat(aliased.path("status").asText()).isEqualTo("INVALID");
        JsonNode malformed = executionFor(executions, "TC-MALFORMED");
        assertThat(malformed.path("status").asText()).isEqualTo("INVALID");
        assertThat(malformed.path("inputError").asText()).contains("payload JSON is malformed");
        assertThat(Files.readAllBytes(externalPayload)).isEqualTo(externalBytes);
        assertThat(Files.exists(declaredMetadataFile)).isTrue();
        assertThat(Files.exists(inlineMetadataFile)).isTrue();
        assertThat(Files.exists(escapedMetadataFile)).isTrue();

        Path unsupportedDirectory = Files.createDirectory(directory.resolve("unsupported-format"));
        assertThatThrownBy(() -> Atl105AiElementIntake.main(new String[]{PACK.toString(),
            unsupportedDirectory.toString(), directory.resolve("unsupported-report.json").toString(),
            "--chapter13-semantics"})).isInstanceOf(java.io.IOException.class)
            .hasMessageContaining("add a profile for this delivery format");
    }

    private static JsonNode executionFor(JsonNode executions, String testCaseId) {
        for (JsonNode execution : executions) {
            if (testCaseId.equals(execution.path("testCaseId").asText())) return execution;
        }
        throw new AssertionError("No execution found for " + testCaseId);
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
