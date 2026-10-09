package com.coreauth.validator;

import com.coreauth.validator.validation.Atl105AiElementIntake;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105AiElementIntakeTest {
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
