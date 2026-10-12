package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter12ActualPayloadAdapter;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Atl105AiElementIntake;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter12ActualPayloadAdapterTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Chapter12ActualPayloadAdapter adapter = new Chapter12ActualPayloadAdapter();

    private ObjectNode fleet() {
        return mapper.createObjectNode().put("SegmentType", "145").put("SegmentLength", "023").put("EnhancedFleetData", "004011|DRID:1100|");
    }

    @Test void decodesActualFleetTlvAndExecutesMatchedFormatsWithoutInputMutation() throws Exception {
        var actual = fleet();
        var before = actual.deepCopy();
        var meta = mapper.createObjectNode();
        meta.putObject("context").put("authorizer", "COMDATA").putArray("segmentsPresent").add("100").add("145");
        meta.putObject("history").putObject("fleet").putObject("promptFormats").put("DRID", "V1100");
        var result = adapter.validate(actual, "145", "Financial Transaction Request", meta);
        assertThat(result.findings()).anyMatch(f -> f.scope().equals("mask-value") && f.status() == Status.CHECKS_PASSED);
        assertThat(result.findings()).noneMatch(f -> f.status() == Status.INVALID);
        assertThat(actual).isEqualTo(before);
        actual.put("EnhancedFleetData", "004999|DRID:1100|");
        assertThat(adapter.validate(actual, "145", "Financial Transaction Request", meta).status()).isEqualTo(Status.INVALID);
    }

    @Test void rejectsAmbiguousTablesAndMalformedEvidenceWithoutSubstitution() throws Exception {
        var actual = fleet();
        actual.putArray("Tables").addObject().put("id", "004").put("length", "011").put("value", "|DRID:1100|");
        assertThat(adapter.validate(actual, "145", "Financial Transaction Request", null).findings())
                .anyMatch(f -> f.ruleId().equals("CH12-ACTUAL-AMBIGUITY") && f.status() == Status.INVALID);
        actual = fleet();
        actual.remove("SegmentLength");
        var meta = mapper.createObjectNode().put("SegmentLength", "023");
        assertThat(adapter.validate(actual, "145", "Financial Transaction Request", meta).status()).isEqualTo(Status.INVALID);
        assertThat(adapter.validate(fleet(), "145", "Financial Transaction Request", mapper.createArrayNode()).status()).isEqualTo(Status.INVALID);
    }

    @Test void wexActualFieldsRequireCorrectRoutingAndCannotUseMetadataAsActualData() throws Exception {
        var actual = mapper.createObjectNode().put("SegmentType", "148").put("SegmentLength", "017").put("AvailableProductInformation", "00=100,50 G");
        var meta = mapper.createObjectNode();
        meta.putObject("context").put("authorizer", "WEX_OTR");
        assertThat(adapter.validate(actual, "148", "Financial Transaction Response", meta).findings())
                .anyMatch(f -> f.scope().equals("routing") && f.status() == Status.CHECKS_PASSED);
        assertThat(adapter.validate(actual, "148", "Financial Transaction Request", meta).status()).isEqualTo(Status.INVALID);
        actual.remove("AvailableProductInformation");
        meta.put("AvailableProductInformation", "00=100,50 G");
        assertThat(adapter.validate(actual, "148", "Financial Transaction Response", meta).status()).isEqualTo(Status.INVALID);
    }

    @Test void rejectsAmbiguousEmvTableRepresentationsAndDoesNotCertifyTextAsBinaryWire() throws Exception {
        var actual = mapper.createObjectNode().put("SegmentType", "130").put("SegmentLength", "0035")
                .put("EMVChipDataLength", "009").put("EMVChipData", "8407A0000000041010");
        actual.putArray("EMVAdditionalInformation").addObject().put("id", "001").put("length", "003").put("value", "ABC");
        actual.put("EMVAdditionalInformationIndicator", "001").put("EMVAdditionalInformationLength", "003")
                .put("EMVAdditionalInformationValue", "ABC");
        assertThat(adapter.validate(actual, "130", "EMV Financial Transaction Request", null).findings())
                .anyMatch(f -> f.ruleId().equals("CH12-ACTUAL-AMBIGUITY") && f.status() == Status.INVALID);
        actual.remove(java.util.List.of("EMVAdditionalInformationIndicator", "EMVAdditionalInformationLength", "EMVAdditionalInformationValue"));
        actual.put("SerializedSegment", "not packed binary");
        assertThat(adapter.validate(actual, "130", "EMV Financial Transaction Request", null).findings())
                .anyMatch(f -> f.ruleId().equals("CH12-ACTUAL-WIRE") && f.status() == Status.REVIEW_REQUIRED);
        assertThat(adapter.validate(actual, "130", "Financial Transaction Request", null).findings())
                .anyMatch(f -> f.scope().equals("family") && f.status() == Status.INVALID);
        assertThat(adapter.validate(actual, "130", null, null).status()).isEqualTo(Status.INVALID);
    }

    @Test void intakeExecutesActualFleetAndEmvWithoutMetadataFieldsForEveryOccurrence() throws Exception {
        var payload = mapper.createObjectNode();
        var root = payload.putObject("EMV Financial Transaction Request");
        root.set("Fleet", fleet());
        root.set("Second Fleet", fleet().put("EnhancedFleetData", "004"));
        root.putObject("EMV").put("SegmentType", "130").put("SegmentLength", "0040").put("EMVChipDataLength", "009")
                .put("EMVChipData", "8407A0000000041010");
        var meta = mapper.createObjectNode().put("testCaseId", "TC-ACTUAL-CH12").put("messageFamily", "EMV Financial Transaction Request");
        meta.putObject("context").put("authorizer", "COMDATA");
        meta.putObject("history").putObject("fleet").putObject("promptFormats").put("DRID", "V1100");
        var before = payload.deepCopy();
        var intake = new Atl105AiElementIntake(Atl105Paths.root(), true);
        var result = intake.assess(meta, payload);
        assertThat(result.intakeFindings()).anyMatch(f -> f.ruleId().equals("CH12-ACTUAL-TLV") && f.status().name().equals("INVALID"))
                .anyMatch(f -> f.ruleId().equals("SEG130-R-002"));
        assertThat(payload).isEqualTo(before);
    }
}
