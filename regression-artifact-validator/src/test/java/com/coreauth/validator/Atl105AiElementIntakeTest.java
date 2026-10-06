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
