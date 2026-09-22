package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.Segment113IndependenceValidator;
import com.coreauth.validator.canonical.SourceAnchor;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 3 (Test-Data Independence) tests for {@link Segment113IndependenceValidator}.
 *
 * <p>These verify the validator can operate on a test datum in isolation without needing
 * external context: it must catch missing anchors, malformed payloads, unlinked cases,
 * and non-Segment-113 anchors.
 */
class Segment113IndependenceValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static SourceAnchor seg113Anchor(String rule) {
        SourceAnchor a = new SourceAnchor();
        a.setSpecification("ATL105");
        a.setVersion("2026-3");
        a.setSection("12.12");
        a.setSegment("113");
        a.setRule(rule);
        return a;
    }

    private static CanonicalTestData validTestData(String id) {
        CanonicalTestData d = new CanonicalTestData();
        d.setId(id);
        d.setSourceAnchors(List.of(seg113Anchor("ecatelecheck-request-conditional")));
        d.setTestCaseIds(List.of("TC-SEG113-CORE-001"));
        d.setExpectedValidation("PASS");
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("ECA/TeleCheck Service Transaction Request");
        request.putObject("ECA/TeleCheck Data Segment").put("SegmentType", "113");
        d.setPayload(payload);
        return d;
    }

    private static CanonicalArtifactPackage pkgWith(CanonicalTestData... data) {
        CanonicalArtifactPackage p = new CanonicalArtifactPackage();
        p.setTestData(List.of(data));
        return p;
    }

    @Nested
    class IndependenceChecks {

        @Test
        void acceptsIndependentTestDatum() {
            ValidationResult result = new Segment113IndependenceValidator().validate(pkgWith(validTestData("TD-1")));
            assertThat(result.errors()).isEmpty();
        }

        @Test
        void rejectsTestDatumWithoutSourceAnchors() {
            CanonicalTestData d = validTestData("TD-2");
            d.setSourceAnchors(null);
            ValidationResult result = new Segment113IndependenceValidator().validate(pkgWith(d));
            assertThat(result.errors()).anyMatch(e -> e.reason().contains("no source anchors"));
        }

        @Test
        void rejectsTestDatumNotLinkedToTestCase() {
            CanonicalTestData d = validTestData("TD-3");
            d.setTestCaseIds(null);
            ValidationResult result = new Segment113IndependenceValidator().validate(pkgWith(d));
            assertThat(result.errors()).anyMatch(e -> e.reason().contains("not linked to any test case"));
        }

        @Test
        void rejectsTestDatumWithMissingExpectedValidation() {
            CanonicalTestData d = validTestData("TD-4");
            d.setExpectedValidation(null);
            ValidationResult result = new Segment113IndependenceValidator().validate(pkgWith(d));
            assertThat(result.errors()).anyMatch(e -> e.reason().contains("does not declare expectedValidation"));
        }

        @Test
        void rejectsTestDatumWithInvalidExpectedValidation() {
            CanonicalTestData d = validTestData("TD-5");
            d.setExpectedValidation("MAYBE");
            ValidationResult result = new Segment113IndependenceValidator().validate(pkgWith(d));
            assertThat(result.errors()).anyMatch(e -> e.reason().contains("invalid expectedValidation"));
        }

        @Test
        void rejectsTestDatumWithoutPayload() {
            CanonicalTestData d = validTestData("TD-6");
            d.setPayload(null);
            ValidationResult result = new Segment113IndependenceValidator().validate(pkgWith(d));
            assertThat(result.errors()).anyMatch(e -> e.reason().contains("no payload"));
        }

        @Test
        void rejectsTestDatumWithEmptyPayload() {
            CanonicalTestData d = validTestData("TD-7");
            d.setPayload(MAPPER.createObjectNode());
            ValidationResult result = new Segment113IndependenceValidator().validate(pkgWith(d));
            assertThat(result.errors()).anyMatch(e -> e.reason().contains("empty payload"));
        }

        @Test
        void rejectsTestDatumWithNonSegment113Anchor() {
            CanonicalTestData d = validTestData("TD-8");
            SourceAnchor wrong = seg113Anchor("segment-type");
            wrong.setSegment("100");
            d.setSourceAnchors(List.of(wrong));
            ValidationResult result = new Segment113IndependenceValidator().validate(pkgWith(d));
            assertThat(result.errors()).anyMatch(e -> e.reason().contains("no Segment 113 source anchor"));
        }

        @Test
        void rejectsTestDatumWithAnchorMissingSpecification() {
            CanonicalTestData d = validTestData("TD-9");
            SourceAnchor a = seg113Anchor("segment-type");
            a.setSpecification(null);
            d.setSourceAnchors(List.of(a));
            ValidationResult result = new Segment113IndependenceValidator().validate(pkgWith(d));
            assertThat(result.errors()).anyMatch(e -> e.reason().contains("lacks specification"));
        }

        @Test
        void rejectsTestDatumWithAnchorMissingRule() {
            CanonicalTestData d = validTestData("TD-10");
            SourceAnchor a = seg113Anchor(null);
            d.setSourceAnchors(List.of(a));
            ValidationResult result = new Segment113IndependenceValidator().validate(pkgWith(d));
            assertThat(result.errors()).anyMatch(e -> e.reason().contains("lacks rule identifier"));
        }

        @Test
        void acceptsAiJsonReferencePayload() {
            CanonicalTestData d = validTestData("TD-11");
            ObjectNode refPayload = MAPPER.createObjectNode();
            refPayload.put("aiJsonReference", "test-input/ai-solution/test-data/segment-113/lifecycle/ecatelecheck-check-purchase-original.synthetic.json");
            d.setPayload(refPayload);
            ValidationResult result = new Segment113IndependenceValidator().validate(pkgWith(d));
            assertThat(result.errors()).isEmpty();
        }

        @Test
        void reportsAllErrorsAcrossMultipleTestData() {
            CanonicalTestData bad1 = validTestData("TD-bad-1");
            bad1.setSourceAnchors(null);
            CanonicalTestData bad2 = validTestData("TD-bad-2");
            bad2.setTestCaseIds(null);
            ValidationResult result = new Segment113IndependenceValidator().validate(pkgWith(bad1, bad2));
            assertThat(result.errors()).hasSizeGreaterThanOrEqualTo(2);
        }
    }
}
