package com.coreauth.validator;

import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Chapter13DataElementValidator;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter13ContextAndCompositeTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Chapter13DataElementValidator validator;

    @BeforeAll static void initialize() throws IOException { validator = new Chapter13DataElementValidator(Atl105Paths.root()); }

    private static ObjectNode observation(String element, String value) {
        var input = MAPPER.createObjectNode().put("specificationVersion", "2026-3").put("messageFamily", "SOURCE_DOMAIN_OBSERVATION")
                .put("segment", "100").put("completeness", "PARTIAL");
        input.putObject("elements").put(element, value);
        return input;
    }

    private static void target(ObjectNode input, String element, String rule, Status status) {
        var matches = validator.validate(input).findings().stream().filter(f -> f.element().equals(element)
                && f.ruleId().equals("CH13-E" + element + "-" + rule)).toList();
        assertThat(matches).hasSize(1);
        assertThat(matches.getFirst().status()).isEqualTo(status);
    }

    @Test void contextualRequirednessDoesNotLeakIntoUnrelatedSegments() {
        var input = observation("86", "000001").put("messageFamily", "Totals Request").put("completeness", "COMPLETE");
        input.putObject("qualifiers").put("manuallyEnteredCheck", true).put("loyaltyTransaction", true).put("emvTransaction", true);
        assertThat(validator.validate(input).findings()).noneMatch(f -> f.ruleId().endsWith("CONTEXT-REQUIRED-CONTEXT"));
        input.put("segment", "110");
        target(input, "123", "CONTEXT-REQUIRED-CONTEXT", Status.INVALID);
    }

    @Test void rejectsMalformedControlsAndDoesNotCoerceNumericApprovalOrHistory() {
        var input = observation("6", "000001000");
        input.putObject("qualifiers").put("afpDevice", "true").put("pumpLimit", 1000);
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        assertThat(validator.validate(input).findings()).anyMatch(f -> f.ruleId().equals("CH13-E-CONTEXT-CONTROL"));
        input.putObject("qualifiers").putObject("dialHistory").put("primary", "1").put("secondary", "2")
                .put("primaryAttemptsAllowed", 1).putArray("attempts").addObject().put("number", "1").put("succeeded", "false");
        target(input, "75", "CONTEXT-DIAL-ORDER", Status.INVALID);
    }

    @Test void trackLimitsMeasureCombinedFieldsAndKeepTransformedDataSeparate() {
        var input = observation("2", "1".repeat(16));
        ((ObjectNode) input.path("elements")).put("12", "2".repeat(21));
        var context = input.putObject("qualifiers").put("accountMode", "NON_TRANSARMOR").put("entryMode", "TRACK2")
                .put("cardTrackData", "1".repeat(16) + "=" + "2".repeat(21));
        target(input, "2", "CONTEXT-TRACK-SPLIT", Status.CHECKS_PASSED);
        ((ObjectNode) input.path("elements")).put("12", "2".repeat(22));
        context.put("cardTrackData", "1".repeat(16) + "=" + "2".repeat(22));
        target(input, "2", "CONTEXT-TRACK-SPLIT", Status.INVALID);
        context.put("accountMode", "RSA_PKI");
        target(input, "2", "CONTEXT-TRACK-SPLIT", Status.REVIEW_REQUIRED);
    }

    @Test void expressCodeSourceConflictIsNotSilentlyRejectedByGeneralAccountMaximum() {
        var input = observation("2", "1".repeat(25));
        input.putObject("qualifiers").put("accountMode", "NON_TRANSARMOR").put("direction", "REQUEST").put("cardType", "095");
        target(input, "2", "CONTEXT-ACCOUNT-WIDTH", Status.REVIEW_REQUIRED);
    }

    @Test void everyPublishedWicPsBitmapIsParsedAndLengthMutationsAreDetected() {
        Map<String, String> suffixes = Map.of("47E0", "00010000100", "47A0", "000100", "4760", "00100", "4720", "", "4620", "");
        for (var entry : suffixes.entrySet()) {
            String data = entry.getKey() + "0" + "1".repeat(16) + "000100" + "00100"
                    + ("4620".equals(entry.getKey()) ? "" : "00") + entry.getValue() + "16";
            String body = "PS" + String.format("%03d", data.length()) + data;
            var input = observation("154", String.format("%04d", body.length()) + body);
            target(input, "154", "COMPOSITE-STRUCTURE", Status.CHECKS_PASSED);
            ((ObjectNode) input.path("elements")).put("154", String.format("%04d", body.length() - 1) + body);
            target(input, "154", "COMPOSITE-STRUCTURE", Status.INVALID);
        }
    }

    @Test void wicPaddingCountsReservedActionsAndCvbAmountsAreActuallyValidated() {
        String data = "4620" + "0" + "0000" + "1".repeat(12) + "000100" + "00100" + "12";
        var input = observation("154", "0039PS034" + data);
        input.putObject("qualifiers").put("cashValueBenefit", true);
        target(input, "154", "COMPOSITE-CVB-QUANTITY", Status.CHECKS_PASSED);
        ((ObjectNode) input.path("elements")).put("154", "0039PS034" + data.replace("0010012", "0020012"));
        target(input, "154", "COMPOSITE-CVB-QUANTITY", Status.INVALID);
        ((ObjectNode) input.path("elements")).put("154", "0039PS034" + data.replace("0000111111111111", "1234111111111111"));
        target(input, "154", "COMPOSITE-STRUCTURE", Status.INVALID);
        String reserved = "4720" + "0" + "1".repeat(16) + "000100" + "00100" + "28" + "16";
        ((ObjectNode) input.path("elements")).put("154", "0041PS036" + reserved);
        target(input, "154", "COMPOSITE-STRUCTURE", Status.CHECKS_PASSED);
        target(input, "154", "COMPOSITE-RESERVED-ACTION", Status.REVIEW_REQUIRED);
    }

    @Test void programRecordCountBoundsAndWireBytesAreMeasuredRatherThanTrusted() {
        String record = "50209850840D000000000100";
        var input = observation("164", "144" + record.repeat(6));
        input.putObject("qualifiers").put("direction", "REQUEST");
        target(input, "164", "COMPOSITE-STRUCTURE", Status.CHECKS_PASSED);
        ((ObjectNode) input.path("elements")).put("164", "168" + record.repeat(7));
        target(input, "164", "COMPOSITE-STRUCTURE", Status.INVALID);
        ((ObjectNode) input.path("elements")).put("164", "000");
        target(input, "164", "COMPOSITE-STRUCTURE", Status.INVALID);
        String address = "123 MAIN STREET".concat(" ".repeat(13)), zip = "90210    ";
        String it = "IT37" + address + zip;
        assertThat(it).hasSize(41);
        ((ObjectNode) input.path("elements")).put("164", "041" + it);
        target(input, "164", "COMPOSITE-STRUCTURE", Status.CHECKS_PASSED);
        ((ObjectNode) input.path("elements")).put("164", "041" + it.substring(0, it.length() - 1));
        target(input, "164", "COMPOSITE-STRUCTURE", Status.INVALID);
    }

    @Test void allCutScheduleTimestampsAreOffsetQualifiedAndCorrelateWithElement() {
        var input = observation("23", "0101");
        var schedule = input.putObject("qualifiers").putObject("cutSchedule").put("automatic", true).put("clerkCompleted", false)
                .put("cutAt", "2026-10-09T01:01:00+05:30").put("startedAt", "2026-10-08T19:01:00Z");
        target(input, "23", "CONTEXT-CUT-SCHEDULE", Status.CHECKS_PASSED);
        schedule.put("startedAt", "2026-10-09T00:31:00");
        target(input, "23", "CONTEXT-CUT-SCHEDULE", Status.INVALID);
    }

    @Test void missingInterfaceEvidenceNeverBecomesAnExactValuePass() {
        var input = observation("190", "8402ABCD9F020101");
        input.putObject("qualifiers").putObject("cardInterfaceLastValues").put("84", "ABCD");
        target(input, "190", "CONTEXT-INTERFACE-LAST-VALUE", Status.INVALID);
        ((ObjectNode) input.path("qualifiers")).put("cardInterfaceLastValues", "ABCD");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
    }

    @Test void parentResponseFamilyCannotBeOverriddenByQualifiers() {
        var input = observation("83", "M").put("messageFamily", "Financial Transaction Response");
        input.putObject("qualifiers").put("responseCodeFamily", "totals");
        target(input, "83", "CONTEXT-RESPONSE-FAMILY", Status.INVALID);
        ((ObjectNode) input.path("elements")).put("83", "0");
        input.remove("qualifiers");
        target(input, "83", "CONTEXT-RESPONSE-FAMILY", Status.CHECKS_PASSED);
    }

    @Test void sourceClockBoundaryIsNotNormalizedToAnInventedIsoTime() {
        var input = observation("23", "2460");
        input.putObject("qualifiers").putObject("cutSchedule").put("automatic", true).put("clerkCompleted", false)
                .put("cutAt", "2026-10-09T00:00:00Z").put("startedAt", "2026-10-08T23:30:00Z");
        target(input, "23", "CONTEXT-CUT-SCHEDULE", Status.REVIEW_REQUIRED);
    }

    @Test void storedValuePanWidthDoesNotRejectTransformedEdata() {
        var input = observation("2", "X".repeat(100));
        input.putObject("qualifiers").put("paymentType", "STORED_VALUE").put("direction", "REQUEST").put("accountMode", "RSA_PKI");
        target(input, "2", "CONTEXT-STORED-VALUE-WIDTH", Status.REVIEW_REQUIRED);
    }

    @Test void ambiguousEfFramingIsNeitherCertifiedNorRejectedAsTheOnlyEncoding() {
        var input = observation("154", "0010EF20240229");
        target(input, "154", "COMPOSITE-STRUCTURE", Status.REVIEW_REQUIRED);
        ((ObjectNode) input.path("elements")).put("154", "0013EF00820240229");
        target(input, "154", "COMPOSITE-STRUCTURE", Status.REVIEW_REQUIRED);
        input.putObject("qualifiers").put("wicEncodingProfile", "TAG_N3");
        target(input, "154", "COMPOSITE-STRUCTURE", Status.CHECKS_PASSED);
        target(input, "154", "COMPOSITE-EF-FRAMING", Status.REVIEW_REQUIRED);
    }

    @Test void mandatorySafekeyCannotBeOmittedAndDoesNotLeakIntoSiblingSegments() {
        var input = observation("86", "000001").put("completeness", "COMPLETE");
        input.putObject("qualifiers").put("ecommerceSecureId", "25");
        assertThat(validator.validate(input).findings()).noneMatch(f -> f.ruleId().equals("CH13-E203-CONTEXT-REQUIRED-CONTEXT"));
        input.put("segment", "123");
        target(input, "203", "CONTEXT-REQUIRED-CONTEXT", Status.INVALID);
        input.put("completeness", "PARTIAL");
        target(input, "203", "CONTEXT-REQUIRED-CONTEXT", Status.REVIEW_REQUIRED);
    }

    @Test void arbitraryMalformedCompositeTextIsReportedWithoutThrowing() {
        for (String id : List.of("153", "154", "164")) {
            for (String value : List.of("X", "0001", "99999999999999999", "0005ZZ999X")) {
                assertThat(validator.validate(observation(id, value)).status()).as(id + "/" + value).isEqualTo(Status.INVALID);
            }
        }
    }
}
