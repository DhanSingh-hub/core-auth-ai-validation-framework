package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment119ActualPayloadAdapter;
import com.coreauth.validator.canonical.TotalsRequestPayloadValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Atl105AiElementIntake;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Segment119ActualPayloadAdapterTest {
    private final ObjectMapper mapper = new ObjectMapper();

    private Segment119ActualPayloadAdapter adapter() throws Exception {
        return new Segment119ActualPayloadAdapter(mapper.readTree(Atl105Paths.testOutput()
                .resolve("test-solution-independent-review").resolve("all-element-inventory.json").toFile()));
    }

    private ObjectNode payload() throws Exception {
        var input = (ObjectNode) mapper.readTree(Atl105Paths.aiTestData("119").resolve("message").resolve("totals-pdl-request.valid.json").toFile());
        segment(input).put("TerminalID", "1D13500101001");
        return input;
    }

    private static ObjectNode segment(ObjectNode payload) {
        return (ObjectNode) payload.path(TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST)
                .path("Totals with Proprietary Data Load Data Segment");
    }

    private ObjectNode evidence() {
        var evidence = mapper.createObjectNode();
        evidence.putObject("context").put("bucketCoverageComplete", true);
        var totals = evidence.putObject("history").putObject("totals").put("requestDate", "333333").put("responseDate", "261009")
                .put("activityHistoryComplete", true);
        totals.putArray("activityDates").add("2026-10-09").add("2026-10-08").add("2026-10-07");
        return evidence;
    }

    @Test void namedPayloadIntegrationValidatesActualFieldsAndLeavesLegacyModeUnchanged() throws Exception {
        var payload = payload();
        segment(payload).put("HostDiscountTimestamp", "202602291200");
        var validator = new TotalsRequestPayloadValidator();
        assertThat(validator.validatePayload(payload).errors()).isEmpty();
        assertThat(validator.validatePayload(payload, adapter(), evidence()).errors())
                .anyMatch(e -> e.reason().contains("SEG119-R-021"));
        segment(payload).put("HostDiscountTimestamp", "202610091200");
        assertThat(validator.validatePayload(payload, adapter(), evidence()).errors()).isEmpty();
        assertThat(validator.validatePayload(payload, adapter(), evidence()).warnings()).isNotEmpty();
    }

    @Test void actualHistoryCorrelationDoesNotTrustAnUnrelatedMetadataDate() throws Exception {
        var payload = payload();
        var evidence = evidence();
        var before = payload.deepCopy();
        var result = adapter().validate(segment(payload), TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST, evidence);
        assertThat(result.status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(result.findings()).anyMatch(f -> f.ruleId().equals("CH13-E105-PROCESSING-SETTLEMENT-RESPONSE")
                && f.status() == Status.CHECKS_PASSED);
        ((ObjectNode) evidence.path("history").path("totals")).put("requestDate", "444444");
        assertThat(adapter().validate(segment(payload), TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST, evidence).findings())
                .anyMatch(f -> f.ruleId().contains("OBSERVATION-CORRELATION") && f.status() == Status.INVALID);
        assertThat(payload).isEqualTo(before);
    }

    @Test void metadataCannotReplaceMissingOrMalformedActualBucketValues() throws Exception {
        var payload = payload();
        var evidence = evidence();
        evidence.putObject("fields").put("SequenceNumber", "000001");
        segment(payload).remove("SequenceNumber");
        assertThat(adapter().validate(segment(payload), TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST, evidence).status())
                .isEqualTo(Status.INVALID);
        segment(payload).put("SequenceNumber", "000001");
        ((ObjectNode) segment(payload).path("CategoryTotals").get(0)).put("CardTypeTotalCount", 3);
        assertThat(adapter().validate(segment(payload), TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST, evidence).findings())
                .anyMatch(f -> f.ruleId().equals("SEG119-R-025") && f.status() == Status.INVALID);
        segment(payload).putObject("CategoryTotals").put("CardLabel", "CC");
        assertThat(adapter().validate(segment(payload), TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST, evidence).status())
                .isEqualTo(Status.INVALID);
    }

    @Test void semanticAiIntakeExecutesEveryActual119OccurrenceWithoutMetadataFieldDeclarations() throws Exception {
        var payload = payload();
        payload.remove("_meta");
        var meta = evidence().put("testCaseId", "TC-119-INTEGRATION").put("messageFamily", TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST);
        var intake = new Atl105AiElementIntake(Atl105Paths.root(), true);
        var before = payload.deepCopy();
        var assessment = intake.assess(meta, payload);
        assertThat(assessment.intakeFindings()).anyMatch(f -> f.ruleId().equals("SEG119-R-021")
                && f.status().name().equals("CHECKS_PASSED"));
        assertThat(payload).isEqualTo(before);
        var second = segment(payload).deepCopy().put("HostDiscountTimestamp", "BAD");
        ((ObjectNode) payload.path(TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST)).set("Second Totals", second);
        assertThat(intake.assess(meta, payload).intakeFindings()).anyMatch(f -> f.ruleId().equals("SEG119-R-021")
                && f.status().name().equals("INVALID"));
        assertThat(before.path(TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST).size()).isEqualTo(3);
    }

    @Test void rejectsContradictoryAndMalformedHistoryEvidenceRatherThanChangingDirection() throws Exception {
        var payload = payload();
        var evidence = evidence();
        evidence.putObject("qualifiers").put("direction", "RESPONSE");
        assertThat(adapter().validate(segment(payload), TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST, evidence).status()).isEqualTo(Status.INVALID);
        evidence = evidence();
        evidence.putArray("history");
        assertThat(adapter().validate(segment(payload), TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST, evidence).status()).isEqualTo(Status.INVALID);
        assertThat(adapter().validate(segment(payload), TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST, mapper.createArrayNode()).status()).isEqualTo(Status.INVALID);
    }

    @Test void mapsOneActualFlatBucketAndRejectsAmbiguousDualRepresentations() throws Exception {
        var payload = payload();
        var segment = segment(payload);
        segment.remove("CategoryTotals");
        segment.put("CardLabel", "CC").put("CardTypeTotalCount", "00001").put("CardTypeTotalAmount", "00012550");
        assertThat(adapter().validate(segment, TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST, evidence()).findings())
                .noneMatch(f -> f.status() == Status.INVALID);
        segment.putArray("CategoryTotals").addObject().put("CardLabel", "CC").put("CardTypeTotalCount", "00001")
                .put("CardTypeTotalAmount", "00012550");
        assertThat(adapter().validate(segment, TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST, evidence()).findings())
                .anyMatch(f -> f.scope().equals("actual-mapping") && f.status() == Status.INVALID);
    }
}
