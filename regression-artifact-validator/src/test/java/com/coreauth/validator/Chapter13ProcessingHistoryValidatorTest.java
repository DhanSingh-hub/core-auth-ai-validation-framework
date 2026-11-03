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

import static org.assertj.core.api.Assertions.assertThat;

class Chapter13ProcessingHistoryValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Chapter13DataElementValidator validator;

    @BeforeAll static void initialize() throws IOException {
        validator = new Chapter13DataElementValidator(Atl105Paths.root());
    }

    @Test void explicitSettlementDateUsesThirdActiveDayNotThirdCalendarDay() {
        var observation = totals("100226", "261002");
        var history = (ObjectNode) observation.path("history").path("totals");
        history.put("requestedSettlementDate", "2026-10-02").put("activityHistoryComplete", true);
        history.putArray("activityDates").add("2026-10-02").add("2026-10-08").add("2026-10-06");
        assertFinding(observation, "105", "ACTIVITY-WINDOW", Status.CHECKS_PASSED);
        assertFinding(observation, "105", "SETTLEMENT-RESPONSE", Status.CHECKS_PASSED);
        history.put("requestDate", "100126").put("requestedSettlementDate", "2026-10-01").put("responseDate", "261001");
        assertFinding(observation, "105", "ACTIVITY-WINDOW", Status.INVALID);
    }

    @Test void activeDateSelectorsResolveMostRecentSecondAndThirdWithGaps() {
        var observation = totals("333333", "261008");
        var history = (ObjectNode) observation.path("history").path("totals");
        history.put("activityHistoryComplete", true).putArray("activityDates")
                .add("2026-10-02").add("2026-10-06").add("2026-10-08");
        assertFinding(observation, "105", "SETTLEMENT-RESPONSE", Status.CHECKS_PASSED);
        history.put("requestDate", "444444").put("responseDate", "261006");
        assertFinding(observation, "105", "SETTLEMENT-RESPONSE", Status.CHECKS_PASSED);
        history.put("requestDate", "555555").put("responseDate", "261002");
        assertFinding(observation, "105", "SETTLEMENT-RESPONSE", Status.CHECKS_PASSED);
        history.put("responseDate", "261007");
        assertFinding(observation, "105", "SETTLEMENT-RESPONSE", Status.INVALID);
    }

    @Test void incompleteDuplicateAndInvalidHistoryDoNotCertifySelection() {
        var observation = totals("333333", "261008");
        var history = (ObjectNode) observation.path("history").path("totals");
        history.put("activityHistoryComplete", false).putArray("activityDates").add("2026-10-08");
        assertFinding(observation, "105", "ACTIVITY-HISTORY", Status.REVIEW_REQUIRED);
        history.put("activityHistoryComplete", true);
        history.putArray("activityDates").add("2026-10-08").add("2026-10-08");
        assertFinding(observation, "105", "ACTIVITY-HISTORY", Status.INVALID);
        history.putArray("activityDates").add("2026-02-30");
        assertFinding(observation, "105", "ACTIVITY-HISTORY", Status.INVALID);
        history.put("activityHistoryComplete", "true");
        assertFinding(observation, "105", "ACTIVITY-HISTORY", Status.INVALID);
    }

    @Test void fewerThanThreeDatesRetainsUnspecifiedOldestDateBehavior() {
        var observation = totals("100126", "261001");
        var history = (ObjectNode) observation.path("history").path("totals");
        history.put("requestedSettlementDate", "2026-10-01").put("activityHistoryComplete", true);
        history.putArray("activityDates").add("2026-10-08");
        assertFinding(observation, "105", "ACTIVITY-WINDOW", Status.REVIEW_REQUIRED);
        history.put("requestDate", "555555");
        assertFinding(observation, "105", "ACTIVITY-SELECTOR", Status.REVIEW_REQUIRED);
    }

    @Test void rollEndsCurrentSettlementAndUsesObservedNextBusinessDate() {
        var observation = totals("222222", "261009");
        var history = (ObjectNode) observation.path("history").path("totals");
        history.put("currentSettlementDate", "2026-10-09").put("nextSettlementDate", "2026-10-12")
                .put("settlementDateAfter", "2026-10-12");
        history.putArray("endedSettlementDates").add("2026-10-09");
        assertFinding(observation, "105", "SETTLEMENT-ROLL", Status.CHECKS_PASSED);
        assertFinding(observation, "105", "SETTLEMENT-END", Status.CHECKS_PASSED);
        assertFinding(observation, "105", "SETTLEMENT-RESPONSE", Status.CHECKS_PASSED);
        history.put("settlementDateAfter", "2026-10-10");
        assertFinding(observation, "105", "SETTLEMENT-ROLL", Status.INVALID);
        history.putArray("endedSettlementDates").add("2026-10-08");
        assertFinding(observation, "105", "SETTLEMENT-END", Status.INVALID);
    }

    @Test void regularDateRetainsFullYearEvidenceWithoutCenturyGuess() {
        var observation = totals("022900", "000229");
        var history = (ObjectNode) observation.path("history").path("totals");
        history.put("requestedSettlementDate", "2000-02-29").put("activityHistoryComplete", true);
        history.putArray("activityDates").add("2000-03-03").add("2000-03-01").add("2000-02-29");
        assertFinding(observation, "105", "REQUEST-DATE-CORRELATION", Status.CHECKS_PASSED);
        assertFinding(observation, "105", "SETTLEMENT-RESPONSE", Status.CHECKS_PASSED);
        history.put("requestedSettlementDate", "1900-02-29");
        assertFinding(observation, "105", "FULL-YEAR-DATE", Status.INVALID);
    }

    @Test void resetClearsAllObservedTotalsWhileAccumulationReadPreservesState() {
        var observation = totals("999999", "999999");
        var history = (ObjectNode) observation.path("history").path("totals");
        history.putObject("accumulatedTotalsBefore").put("amount", "100").put("count", "2");
        history.putObject("accumulatedTotalsAfter").put("amount", "0").put("count", "000");
        assertFinding(observation, "105", "ACCUMULATION", Status.CHECKS_PASSED);
        ((ObjectNode) history.path("accumulatedTotalsAfter")).put("count", "1");
        assertFinding(observation, "105", "ACCUMULATION", Status.INVALID);
        history.put("requestDate", "111111").put("responseDate", "111111");
        history.putObject("accumulatedTotalsAfter").put("amount", "100").put("count", "2");
        history.put("lastResetEventId", "reset-4").put("returnedPeriodStartEventId", "reset-4");
        assertFinding(observation, "105", "ACCUMULATION", Status.CHECKS_PASSED);
        assertFinding(observation, "105", "ACCUMULATION-PERIOD", Status.CHECKS_PASSED);
        history.put("returnedPeriodStartEventId", "reset-3");
        assertFinding(observation, "105", "ACCUMULATION-PERIOD", Status.INVALID);
    }

    @Test void specialResponseAndTotalsKeyLossAreMutationsNotSuccess() {
        var observation = totals("111111", "261009");
        assertFinding(observation, "105", "SPECIAL-RESPONSE", Status.INVALID);
        var history = (ObjectNode) observation.path("history").path("totals");
        history.putObject("accumulatedTotalsBefore").put("amount", "100").put("count", "2");
        history.putObject("accumulatedTotalsAfter").put("amount", "100");
        assertFinding(observation, "105", "ACCUMULATION", Status.INVALID);
        history.putObject("accumulatedTotalsAfter").put("amount", 100).put("count", 2);
        assertFinding(observation, "105", "ACCUMULATION", Status.INVALID);
    }

    @Test void pairedHistoryCannotContradictProvidedRequestOrResponseElement() {
        var observation = totals("333333", "261009");
        observation.putObject("qualifiers").put("direction", "RESPONSE");
        ((ObjectNode) observation.path("elements")).put("105", "261008");
        assertFinding(observation, "105", "OBSERVATION-CORRELATION", Status.INVALID);
        ((ObjectNode) observation.path("elements")).put("105", "261009");
        assertFinding(observation, "105", "OBSERVATION-CORRELATION", Status.CHECKS_PASSED);
    }

    @Test void approvedCapkConcatenatesBlocksAndReplacesRatherThanAppends() {
        var observation = base();
        var capk = observation.putObject("history").putObject("capk");
        capk.put("approved", true).put("transferComplete", true).put("assembledFile", "ABCDEF")
                .put("storedFileBefore", "OLD").put("storedFileAfter", "ABCDEF");
        var blocks = capk.putArray("blocks");
        blocks.addObject().put("length", "003").put("data", "ABC");
        blocks.addObject().put("length", "003").put("data", "DEF");
        assertFinding(observation, "193", "BLOCK-LENGTHS", Status.CHECKS_PASSED);
        assertFinding(observation, "194", "FILE-ASSEMBLY", Status.CHECKS_PASSED);
        assertFinding(observation, "194", "FILE-REPLACEMENT", Status.CHECKS_PASSED);
        capk.put("storedFileAfter", "OLDABCDEF");
        assertFinding(observation, "194", "FILE-REPLACEMENT", Status.INVALID);
        capk.put("assembledFile", "DEFABC");
        assertFinding(observation, "194", "FILE-ASSEMBLY", Status.INVALID);
        ((ObjectNode) blocks.get(0)).put("length", "002");
        assertFinding(observation, "193", "BLOCK-LENGTHS", Status.INVALID);
    }

    @Test void incompleteCapkTransferAndUnknownEncodingRemainReview() {
        var observation = base();
        var capk = observation.putObject("history").putObject("capk").put("approved", true).put("transferComplete", false);
        assertFinding(observation, "194", "FILE-REPLACEMENT", Status.REVIEW_REQUIRED);
        capk.put("transferComplete", true).putArray("blocks").addObject().put("length", "001").put("data", "\u00e9");
        assertFinding(observation, "193", "BLOCK-LENGTHS", Status.REVIEW_REQUIRED);
        capk.put("transferComplete", "true");
        assertFinding(observation, "194", "BLOCKS", Status.INVALID);
    }

    @Test void capkFileMaximumIs9999NotOneBlockMaximum() {
        var observation = base();
        var capk = observation.putObject("history").putObject("capk").put("approved", true).put("transferComplete", true);
        var blocks = capk.putArray("blocks");
        for (int i = 0; i < 10; i++) blocks.addObject().put("length", "999").put("data", "A".repeat(999));
        blocks.addObject().put("length", "009").put("data", "A".repeat(9));
        capk.put("assembledFile", "A".repeat(9999)).put("storedFileAfter", "A".repeat(9999));
        assertFinding(observation, "194", "FILE-ASSEMBLY", Status.CHECKS_PASSED);
        blocks.addObject().put("length", "001").put("data", "A");
        capk.put("assembledFile", "A".repeat(10000)).put("storedFileAfter", "A".repeat(10000));
        assertFinding(observation, "194", "FILE-ASSEMBLY", Status.INVALID);
    }

    @Test void declinedCapkResponseIsDisplayedWithoutGuessingErrorCatalogExhaustiveness() {
        var observation = base();
        var capk = observation.putObject("history").putObject("capk").put("approved", false)
                .put("responseData", "RQST/CURRENT HASH MATCH").put("displayedError", "RQST/CURRENT HASH MATCH");
        assertFinding(observation, "194", "DECLINED-DISPLAY", Status.CHECKS_PASSED);
        capk.put("displayedError", "KEY LOAD OK");
        assertFinding(observation, "194", "DECLINED-DISPLAY", Status.INVALID);
    }

    @Test void maskedStoreNumberNeverPrintsOrDisplaysEvenWhenOnlyOneActionIsWrong() {
        var observation = base();
        ((ObjectNode) observation.path("elements")).put("98", "********");
        var actions = observation.putObject("history").putObject("storeNumberActions").put("displayed", false).put("printed", false);
        assertFinding(observation, "98", "MASKED-ACTIONS", Status.CHECKS_PASSED);
        actions.put("printed", true);
        assertFinding(observation, "98", "MASKED-ACTIONS", Status.INVALID);
        actions.put("printed", false).put("displayed", true);
        assertFinding(observation, "98", "MASKED-ACTIONS", Status.INVALID);
        actions.put("displayed", "false");
        assertFinding(observation, "98", "MASKED-ACTIONS", Status.INVALID);
    }

    @Test void receiptRetainsAndPrintsTheActualOrderedLines() {
        var observation = base();
        var receipt = observation.putObject("history").putObject("receipt");
        receipt.putArray("receivedLines").add("WELCOME").add("THANK YOU");
        receipt.putArray("retainedLines").add("WELCOME").add("THANK YOU");
        receipt.putArray("printedLines").add("WELCOME").add("THANK YOU");
        assertFinding(observation, "170", "RETENTION", Status.CHECKS_PASSED);
        assertFinding(observation, "170", "SUBSEQUENT-PRINT", Status.CHECKS_PASSED);
        receipt.putArray("printedLines").add("THANK YOU").add("WELCOME");
        assertFinding(observation, "170", "SUBSEQUENT-PRINT", Status.INVALID);
        receipt.putArray("retainedLines").add("OLD TEXT");
        assertFinding(observation, "170", "RETENTION", Status.INVALID);
    }

    @Test void loadedCardTableReplacesReceivedTableDataExactly() {
        var observation = base();
        var table = observation.putObject("history").putObject("cardTable")
                .put("receivedData", "NEWTABLE").put("storedDataAfter", "NEWTABLE");
        assertFinding(observation, "175", "RETENTION", Status.CHECKS_PASSED);
        table.put("storedDataAfter", "OLDNEWTABLE");
        assertFinding(observation, "175", "RETENTION", Status.INVALID);
    }

    @Test void siteConfigurationSendsOnlyFinalChangeAndNothingOnUnchangedDay() {
        var observation = base();
        var site = observation.putObject("history").putObject("siteConfiguration").put("businessDayComplete", true);
        site.putArray("changes").add("FIRST").add("FINAL");
        site.putArray("sentData").add("FINAL");
        assertFinding(observation, "180", "FINAL-CHANGE", Status.CHECKS_PASSED);
        site.putArray("sentData").add("FIRST").add("FINAL");
        assertFinding(observation, "180", "FINAL-CHANGE", Status.INVALID);
        site.putArray("changes");
        site.putArray("sentData");
        assertFinding(observation, "180", "FINAL-CHANGE", Status.CHECKS_PASSED);
        site.putArray("sentData").add("UNCHANGED");
        assertFinding(observation, "180", "FINAL-CHANGE", Status.INVALID);
        site.put("businessDayComplete", false);
        assertFinding(observation, "180", "FINAL-CHANGE", Status.REVIEW_REQUIRED);
    }

    @Test void malformedHistoryCannotBeIgnoredOrCoerced() {
        var observation = base();
        observation.put("history", "not an object");
        assertFinding(observation, "", "HISTORY-INPUT", Status.INVALID);
        observation.putObject("history").put("totals", false);
        assertFinding(observation, "105", "HISTORY-INPUT", Status.INVALID);
        observation.putObject("history").putObject("totals").put("requestDate", 333333).put("responseDate", "261009");
        assertFinding(observation, "105", "REQUEST", Status.INVALID);
        observation.putObject("history").putObject("capk").put("approved", "true");
        assertFinding(observation, "194", "LOAD-INPUT", Status.INVALID);
    }

    private static ObjectNode totals(String request, String response) {
        var observation = base();
        observation.putObject("history").putObject("totals").put("requestDate", request).put("responseDate", response);
        return observation;
    }
    private static ObjectNode base() {
        var observation = MAPPER.createObjectNode().put("specificationVersion", "2026-3").put("segment", "MESSAGE")
                .put("messageFamily", "SOURCE_PROCESSING_OBSERVATION").put("completeness", "PARTIAL");
        observation.putObject("elements");
        return observation;
    }
    private static void assertFinding(ObjectNode observation, String element, String rule, Status expected) {
        var result = validator.validate(observation);
        assertThat(result.findings()).as("%s/%s: %s", element, rule, result.findings())
                .anyMatch(f -> f.ruleId().equals("CH13-E" + element + "-PROCESSING-" + rule) && f.status() == expected);
        if (expected == Status.INVALID) assertThat(result.status()).isEqualTo(Status.INVALID);
    }
}
