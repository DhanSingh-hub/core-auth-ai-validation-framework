package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment111PayloadValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Segment111PayloadValidatorTest {
    private ObjectNode payload() {
        ObjectMapper m = new ObjectMapper();
        ObjectNode root = m.createObjectNode();
        ObjectNode req = root.putObject("request");
        ObjectNode section3 = req.putObject("dataSection3");
        ObjectNode s = section3.putObject("variableInfoSegment");
        s.put("segmentType", "111");
        s.put("segmentLength", "018");
        var a = s.putArray("variableInformationSections");
        ObjectNode rep = a.addObject();
        rep.put("variableInformationIndicator", "001");
        rep.put("variableInformationLength", "003");
        rep.put("variableInformation", "ABC");
        return root;
    }

    @Test void acceptsComputedCanonicalEnvelope() {
        assertThat(new Segment111PayloadValidator().validatePayload(payload()).errors()).isEmpty();
    }

    @Test void acceptsLegacyEnvelope() throws Exception {
        ObjectNode p = (ObjectNode) new ObjectMapper().readTree("""
            {"Financial Request":{"Variable Information Data Segment":{"SegmentType":"111","SegmentLength":"018","repetitions":[{"VariableInformationIndicator":"001","VariableInformationLength":"003","VariableInformation":"ABC"}]}}}
            """);
        assertThat(new Segment111PayloadValidator().validatePayload(p).errors()).isEmpty();
    }

    @Test void rejectsWrongType() {
        ObjectNode p = payload();
        ((ObjectNode)p.path("request").path("dataSection3").path("variableInfoSegment")).put("segmentType", "999");
        assertThat(new Segment111PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG111-R-001"));
    }

    @Test void rejectsNonTextualSegmentLength() {
        ObjectNode p = payload();
        ((ObjectNode)p.path("request").path("dataSection3").path("variableInfoSegment")).put("segmentLength", 18);
        assertThat(new Segment111PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG111-R-002"));
    }

    @Test void rejectsRepetitionLengthMismatch() {
        ObjectNode p = payload();
        ((ObjectNode)p.path("request").path("dataSection3").path("variableInfoSegment").path("variableInformationSections").get(0)).put("variableInformationLength", "004");
        assertThat(new Segment111PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG111-R-004"));
    }

    @Test void rejectsRepeatedSectionGreaterThan991() {
        ObjectNode p = payload();
        ObjectNode s = (ObjectNode) p.path("request").path("dataSection3").path("variableInfoSegment");
        var reps = s.putArray("variableInformationSections");
        reps.addObject().put("variableInformationIndicator", "001").put("variableInformationLength", "493").put("variableInformation", "A".repeat(493));
        reps.addObject().put("variableInformationIndicator", "002").put("variableInformationLength", "493").put("variableInformation", "B".repeat(493));
        s.put("segmentLength", "999");
        assertThat(new Segment111PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG111-R-005"));
    }

    @Test void rejectsTotalGreaterThan999WhenRepeatedSectionIs991() {
        ObjectNode p = payload();
        ObjectNode s = (ObjectNode) p.path("request").path("dataSection3").path("variableInfoSegment");
        var reps = s.putArray("variableInformationSections");
        reps.addObject().put("variableInformationIndicator", "001").put("variableInformationLength", "985").put("variableInformation", "A".repeat(985));
        s.put("segmentLength", "999");
        assertThat(new Segment111PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG111-R-006"));
    }

    @Test void rejectsStructuralOrderViolation() throws Exception {
        ObjectNode p = (ObjectNode) new ObjectMapper().readTree("""
            {"request":{"dataSection3":{"variableInfoSegment":{"segmentLength":"016","segmentType":"111","variableInformationSections":[{"variableInformationIndicator":"001","variableInformationLength":"001","variableInformation":"A"}]}}}}
            """);
        assertThat(new Segment111PayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains("SEG111-R-010"));
    }
}
