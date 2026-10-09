package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.Segment100SerializationValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the Segment 100 Segment Length bound (ATL105 Chapter 13.2 Element 84: 001-218)
 * added during the Chapter 12 segment cross-check. The sentinel bypass and generic shape
 * check predate this test; only the new numeric-range behavior is exercised here.
 */
class Segment100SerializationValidatorTest {
    private final ObjectMapper json = new ObjectMapper();
    private final Segment100SerializationValidator validator = new Segment100SerializationValidator();

    private CanonicalArtifactPackage packageWithSegmentLength(String segmentLength) throws Exception {
        String template = """
                {"tcpIpHeader":{"messageLength":"CALCULATE_FROM_FOLLOWING_PAYLOAD","byteOrder":"network-big-endian","tpduProtocolId":"60","tpduDestinationAddress":"0000","tpduSourceAddress":"0000"},
                 "dataSection1":{"messageFormatVersionIdentifier":"ATL105","numberOfSegments":"01","fieldSeparators":{"betweenElements55And63":true,"afterElement63":true}},
                 "dataSection2":{"standardSegment":{"segmentType":"100","segmentLength":"%s","informationByte":"0","terminalIdentifier":"TSCA000001","promptCode":"0020","accountNumber":"4111111111111111","cardDiscretionaryBlockData":"","encryptedPinBlockData":"","pumpLaneNumber":"","fuelPurchaseAmount":"","nonfuelAmount":"00001000","taxAmount":"","cashAmount":"","sequenceNumber":"100001","approvalNumber":"","localDateTime":"0101261200","partialApprovalIndicator":"1"}}}
                """.formatted(segmentLength);
        ObjectNode payload = json.createObjectNode();
        payload.putObject("testControls").put("validateSerialization", true);
        payload.set("request", json.readTree(template));
        CanonicalTestData data = new CanonicalTestData();
        data.setId("TD-SEG100-SERIALIZATION-TEST");
        data.setPayload(payload);
        CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
        pkg.setTestData(List.of(data));
        return pkg;
    }

    @Test
    void acceptsTheSentinelAndEveryBoundaryWithinTheSourceRange() throws Exception {
        for (String length : new String[]{"CALCULATE_FROM_SEGMENT_CONTENT", "001", "100", "218"}) {
            ValidationResult result = validator.validate(packageWithSegmentLength(length));
            assertThat(result.errors()).as(length).isEmpty();
        }
    }

    @Test
    void rejectsLiteralLengthsOutsideTheElement84SegmentHundredRange() throws Exception {
        for (String length : new String[]{"000", "219", "999"}) {
            ValidationResult result = validator.validate(packageWithSegmentLength(length));
            assertThat(result.errors()).as(length).isNotEmpty()
                    .anyMatch(error -> error.reason().contains("001-218"));
        }

    }

    @Test
    void rejectsFourDigitSegmentLengthsAndKeepsSentinelsAsUnverifiedEvidence() throws Exception {
        assertThat(validator.validate(packageWithSegmentLength("0218")).errors()).isNotEmpty();
        assertThat(validator.validate(packageWithSegmentLength("CALCULATE_FROM_SEGMENT_CONTENT")).warnings())
                .anyMatch(w -> w.contains("REVIEW_REQUIRED"));
    }
}
