package com.coreauth.validator.validation;

import com.coreauth.validator.validation.Atl105ElementValueValidator.Finding;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigInteger;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Source-local processing checks against observed histories, never inferred host state. */
public final class Chapter13ProcessingHistoryValidator {
    private static final DateTimeFormatter MMDDYY = DateTimeFormatter.ofPattern("MMddyy");
    private static final DateTimeFormatter YYMMDD = DateTimeFormatter.ofPattern("yyMMdd");
    private static final Set<String> SPECIAL_DATES = Set.of("111111", "222222", "333333", "444444", "555555", "999999");

    public Chapter13ProcessingHistoryValidator(JsonNode inventory) throws IOException {
        for (var entry : Map.of(
                "98", "the data is neither displayed nor printed",
                "105", "third most recent date with activity",
                "170", "retained by the POS Device and used on subsequent receipt printing",
                "175", "creating/replacing its Card Table information",
                "180", "only the final site configuration data is sent",
                "194", "completely replaces the key file previously stored").entrySet()) {
            boolean found = false;
            for (JsonNode definition : inventory.path("elements").path(entry.getKey()).path("definitions")) {
                if (definition.path("sourceText").asText().replaceAll("\\s+", " ").contains(entry.getValue())) found = true;
            }
            if (!found) throw new IOException("Processing predicate lacks source-local evidence: Element " + entry.getKey());
        }
    }

    public List<Finding> validate(JsonNode observation) {
        List<Finding> findings = new ArrayList<>();
        JsonNode history = observation.path("history");
        if (observation.has("history") && !history.isObject()) {
            add(findings, "", "HISTORY-INPUT", Status.INVALID, "Processing history must be an object");
            return List.copyOf(findings);
        }
        var sections = history.fieldNames();
        while (sections.hasNext()) {
            String section = sections.next();
            if (!Set.of("totals", "capk", "receipt", "cardTable", "siteConfiguration", "storeNumberActions").contains(section)) {
                review(findings, "", "UNASSESSED-HISTORY", "No implemented processing predicate for history section: " + section);
            }
        }
        if (history.has("totals")) totals(history.path("totals"), observation, findings);
        else if (observation.path("elements").has("105")) review(findings, "105", "HISTORY", "Observed settlement/activity/reset history required");
        if (history.has("capk")) capk(history.path("capk"), findings);
        if (history.has("receipt")) retainedData(history.path("receipt"), "170", "retainedLines", "receivedLines", "printedLines", findings);
        if (history.has("cardTable")) retainedData(history.path("cardTable"), "175", "storedDataAfter", "receivedData", null, findings);
        if (history.has("siteConfiguration")) siteConfiguration(history.path("siteConfiguration"), findings);
        String store = text(observation.path("elements"), "98");
        if (store != null && store.matches("\\*+")) {
            JsonNode actions = history.path("storeNumberActions");
            if (!actions.isObject() || !actions.has("displayed") || !actions.has("printed")) {
                review(findings, "98", "MASKED-ACTIONS", "Both display and print actions must be observed");
            } else if (!actions.path("displayed").isBoolean() || !actions.path("printed").isBoolean()) {
                add(findings, "98", "MASKED-ACTIONS", Status.INVALID, "Display and print observations must be booleans");
            } else check(findings, "98", "MASKED-ACTIONS", !actions.path("displayed").booleanValue() && !actions.path("printed").booleanValue(),
                    "Masked Store Number must neither be displayed nor printed");
        }
        return List.copyOf(findings);
    }

    private static void totals(JsonNode totals, JsonNode observation, List<Finding> findings) {
        if (!totals.isObject()) {
            add(findings, "105", "HISTORY-INPUT", Status.INVALID, "Totals history must be an object");
            return;
        }
        String request = text(totals, "requestDate");
        String response = text(totals, "responseDate");
        if (request == null || !request.matches("[0-9]{6}")) {
            add(findings, "105", "REQUEST", totals.has("requestDate") ? Status.INVALID : Status.REVIEW_REQUIRED, "Observed six-digit request date is required");
            return;
        }
        if (response == null || !response.matches("[0-9]{6}")) {
            add(findings, "105", "RESPONSE", totals.has("responseDate") ? Status.INVALID : Status.REVIEW_REQUIRED, "Observed six-digit response date is required");
            return;
        }
        if (!SPECIAL_DATES.contains(request) && !Atl105CalendarDate.isValidMmddyy(request)) {
            add(findings, "105", "REQUEST", Status.INVALID, "Totals history request must be a real MMDDYY date or a source-listed special code");
            return;
        }
        String direction = text(observation.path("qualifiers"), "direction");
        String observed = text(observation.path("elements"), "105");
        if (observed != null && Set.of("REQUEST", "RESPONSE").contains(direction == null ? "" : direction)) {
            check(findings, "105", "OBSERVATION-CORRELATION", observed.equals("REQUEST".equals(direction) ? request : response),
                    "Supplied Element 105 must equal the corresponding history request/response");
        }
        if (Set.of("111111", "999999").contains(request)) {
            check(findings, "105", "SPECIAL-RESPONSE", request.equals(response), "Accumulation/reset response preserves source special date code");
            totalsReset(totals, request, findings);
            return;
        }
        LocalDate resolved = null;
        if ("222222".equals(request)) {
            resolved = date(totals, "currentSettlementDate", "105", findings);
            LocalDate next = date(totals, "nextSettlementDate", "105", findings);
            LocalDate after = date(totals, "settlementDateAfter", "105", findings);
            if (resolved != null && next != null && after != null) {
                check(findings, "105", "SETTLEMENT-ROLL", next.isAfter(resolved) && after.equals(next),
                        "222222 rolls to the observed next settlement date; calendar-day or weekend policy is not invented");
            }
            JsonNode ended = totals.get("endedSettlementDates");
            if (ended == null) review(findings, "105", "SETTLEMENT-END", "Evidence that the current settlement date ended is required");
            else if (!ended.isArray()) add(findings, "105", "SETTLEMENT-END", Status.INVALID, "Ended settlement dates must be an array");
            else if (resolved != null) {
                boolean matched = false;
                boolean malformed = false;
                for (JsonNode item : ended) {
                    LocalDate value = dateValue(item);
                    if (value == null) malformed = true;
                    if (resolved.equals(value)) matched = true;
                }
                check(findings, "105", "SETTLEMENT-END", !malformed && matched, "Current settlement date must occur in observed ended-date evidence");
            }
        } else if (Set.of("333333", "444444", "555555").contains(request)) {
            List<LocalDate> activity = activity(totals, findings);
            int rank = "333333".equals(request) ? 1 : "444444".equals(request) ? 2 : 3;
            if (activity != null) {
                if (activity.size() < rank) review(findings, "105", "ACTIVITY-SELECTOR", "Requested activity rank does not exist in supplied history; source behavior is unspecified");
                else resolved = activity.get(rank - 1);
            }
        } else if (!SPECIAL_DATES.contains(request)) {
            resolved = date(totals, "requestedSettlementDate", "105", findings);
            if (resolved != null) check(findings, "105", "REQUEST-DATE-CORRELATION", request.equals(resolved.format(MMDDYY)),
                    "Explicit full-year settlement date must encode the requested MMDDYY without guessing a century");
            List<LocalDate> activity = activity(totals, findings);
            if (resolved != null && activity != null) {
                if (activity.size() < 3) review(findings, "105", "ACTIVITY-WINDOW", "Fewer than three active dates; oldest permitted date is not specified");
                else check(findings, "105", "ACTIVITY-WINDOW", !resolved.isBefore(activity.get(2)),
                        "Totals cannot be requested before the third most recent active settlement date");
            }
        }
        if (resolved != null) check(findings, "105", "SETTLEMENT-RESPONSE", response.equals(resolved.format(YYMMDD)),
                "Totals Response must encode the source-selected settlement date as YYMMDD");
        else review(findings, "105", "SETTLEMENT-RESPONSE", "Insufficient full-year history to establish the selected response date");
    }

    private static List<LocalDate> activity(JsonNode totals, List<Finding> findings) {
        JsonNode complete = totals.get("activityHistoryComplete");
        if (complete == null || complete.isBoolean() && !complete.booleanValue()) {
            review(findings, "105", "ACTIVITY-HISTORY", "Complete active-date history is required; partial dates cannot certify rank or oldest permitted date");
            return null;
        }
        if (!complete.isBoolean() || !totals.path("activityDates").isArray()) {
            add(findings, "105", "ACTIVITY-HISTORY", Status.INVALID, "History completeness must be boolean and active dates an array");
            return null;
        }
        List<LocalDate> values = new ArrayList<>();
        Set<LocalDate> unique = new HashSet<>();
        for (JsonNode item : totals.path("activityDates")) {
            LocalDate value = dateValue(item);
            if (value == null || !unique.add(value)) {
                add(findings, "105", "ACTIVITY-HISTORY", Status.INVALID, "Activity dates must be distinct, real ISO full-year dates");
                return null;
            }
            values.add(value);
        }
        values.sort(java.util.Comparator.reverseOrder());
        add(findings, "105", "ACTIVITY-HISTORY", Status.CHECKS_PASSED, "Distinct activity history accepted; chronology derived from dates, not producer array order");
        return List.copyOf(values);
    }

    private static void totalsReset(JsonNode totals, String request, List<Finding> findings) {
        JsonNode before = totals.get("accumulatedTotalsBefore");
        JsonNode after = totals.get("accumulatedTotalsAfter");
        if (before == null || after == null) {
            review(findings, "105", "ACCUMULATION", "Observed totals before and after required; reset state cannot be inferred from code alone");
            return;
        }
        if (!numericTotals(before) || !numericTotals(after) || !sameFields(before, after)) {
            add(findings, "105", "ACCUMULATION", Status.INVALID, "Before/after totals must be nonempty identical-key maps of unsigned numeric text");
            return;
        }
        boolean valid = true;
        var fields = before.fields();
        while (fields.hasNext()) {
            var field = fields.next();
            BigInteger actual = new BigInteger(after.path(field.getKey()).textValue());
            BigInteger expected = "999999".equals(request) ? BigInteger.ZERO : new BigInteger(field.getValue().textValue());
            if (!actual.equals(expected)) valid = false;
        }
        check(findings, "105", "ACCUMULATION", valid, "999999 clears every observed accumulation; 111111 does not clear it");
        if ("111111".equals(request)) {
            String reset = text(totals, "lastResetEventId");
            String start = text(totals, "returnedPeriodStartEventId");
            if (reset == null || start == null || reset.isBlank() || start.isBlank()) {
                review(findings, "105", "ACCUMULATION-PERIOD", "Observed last reset and returned period boundary identities required");
            } else check(findings, "105", "ACCUMULATION-PERIOD", reset.equals(start), "111111 period begins at the last observed 999999 reset");
        }
    }

    private static void capk(JsonNode capk, List<Finding> findings) {
        if (!capk.isObject() || !capk.path("approved").isBoolean()) {
            add(findings, "194", "LOAD-INPUT", Status.INVALID, "CAPK load requires explicit boolean approval and an object history");
            return;
        }
        if (!capk.path("approved").booleanValue()) {
            String error = text(capk, "responseData");
            String displayed = text(capk, "displayedError");
            if (error == null || displayed == null) review(findings, "194", "DECLINED-DISPLAY", "Observed returned and displayed decline message required");
            else check(findings, "194", "DECLINED-DISPLAY", !error.isEmpty() && error.equals(displayed), "Declined CAPK response message must be displayed unchanged");
            return;
        }
        JsonNode complete = capk.get("transferComplete");
        if (complete == null || complete.isBoolean() && !complete.booleanValue()) {
            review(findings, "194", "FILE-REPLACEMENT", "Complete approved transfer required before replacement can be assessed");
            return;
        }
        if (!complete.isBoolean() || !capk.path("blocks").isArray() || capk.path("blocks").isEmpty()) {
            add(findings, "194", "BLOCKS", Status.INVALID, "Complete approved transfer needs a nonempty ordered block array and boolean completeness");
            return;
        }
        StringBuilder joined = new StringBuilder();
        boolean valid = true;
        boolean encodingKnown = true;
        for (JsonNode block : capk.path("blocks")) {
            String data = text(block, "data"), length = text(block, "length");
            if (data == null || length == null || !length.matches("[0-9]{3}")) {
                valid = false;
                continue;
            }
            if (data.chars().anyMatch(c -> c > 127)) encodingKnown = false;
            else if (data.length() != Integer.parseInt(length) || data.length() > 999) valid = false;
            joined.append(data);
        }
        if (!valid) {
            add(findings, "193", "BLOCK-LENGTHS", Status.INVALID, "Each observed CAPK block must have numeric N3 byte length matching its data, capped at 999");
            return;
        }
        if (!encodingKnown) {
            review(findings, "193", "BLOCK-LENGTHS", "Non-ASCII CAPK observations need explicit byte-encoding evidence");
            return;
        }
        add(findings, "193", "BLOCK-LENGTHS", Status.CHECKS_PASSED, "Measured ASCII block lengths agree");
        String assembled = text(capk, "assembledFile");
        String stored = text(capk, "storedFileAfter");
        if (assembled == null) review(findings, "194", "FILE-ASSEMBLY", "Observed assembled file required");
        else check(findings, "194", "FILE-ASSEMBLY", joined.length() <= 9999 && assembled.equals(joined.toString()),
                "Approved CAPK file is ordered block concatenation, at most 9999 bytes");
        if (stored == null) review(findings, "194", "FILE-REPLACEMENT", "Observed stored file after load required");
        else check(findings, "194", "FILE-REPLACEMENT", joined.length() <= 9999 && stored.equals(joined.toString()),
                "Approved complete load replaces the entire old file, not an append or merge");
        review(findings, "194", "KEY-TRUST", "Structural replacement does not establish public-key authenticity, completeness or current validity");
    }

    private static void retainedData(JsonNode history, String element, String storedField, String receivedField, String printedField, List<Finding> findings) {
        if (!history.isObject()) {
            add(findings, element, "RETENTION", Status.INVALID, "Retention history must be an object");
            return;
        }
        JsonNode received = history.get(receivedField), stored = history.get(storedField);
        boolean lines = "170".equals(element);
        if (received == null || stored == null) review(findings, element, "RETENTION", "Received and retained data must both be observed");
        else if (!retentionValue(received, lines) || !retentionValue(stored, lines)) {
            add(findings, element, "RETENTION", Status.INVALID, "Retention evidence must be text or source-bounded receipt-line arrays, as applicable");
        } else check(findings, element, "RETENTION", received.equals(stored), "Received source data must be retained/replaced without substitution");
        if (printedField != null) {
            JsonNode printed = history.get(printedField);
            if (printed == null) review(findings, element, "SUBSEQUENT-PRINT", "Subsequent receipt output lines must be observed");
            else if (received != null && retentionValue(received, true)) {
                check(findings, element, "SUBSEQUENT-PRINT", retentionValue(printed, true) && received.equals(printed),
                        "Subsequent receipt text must use the retained source lines, preserving their order");
            }
        }
    }

    private static void siteConfiguration(JsonNode history, List<Finding> findings) {
        if (!history.isObject() || !history.path("changes").isArray() || !history.path("sentData").isArray()) {
            add(findings, "180", "FINAL-CHANGE", Status.INVALID, "Site history requires ordered changes and sent-data arrays for one observed business day");
            return;
        }
        JsonNode changes = history.path("changes"), sent = history.path("sentData");
        boolean wellFormed = true;
        for (JsonNode value : changes) if (!retentionValue(value, false)) wellFormed = false;
        for (JsonNode value : sent) if (!retentionValue(value, false)) wellFormed = false;
        if (!wellFormed) {
            add(findings, "180", "FINAL-CHANGE", Status.INVALID, "Site configuration observations must be ASCII text within 3600 bytes");
            return;
        }
        if (!history.path("businessDayComplete").isBoolean()) {
            add(findings, "180", "DAY-COMPLETENESS", Status.INVALID, "Business-day completeness must be explicitly boolean");
            return;
        }
        if (!history.path("businessDayComplete").booleanValue()) {
            review(findings, "180", "FINAL-CHANGE", "Partial business-day history cannot establish final-change-only transmission");
            return;
        }
        check(findings, "180", "FINAL-CHANGE", changes.isEmpty() ? sent.isEmpty() : sent.size() == 1 && sent.get(0).equals(changes.get(changes.size() - 1)),
                "No change means no transmission; otherwise only the final business-day configuration is sent");
    }

    private static boolean retentionValue(JsonNode value, boolean lines) {
        if (!lines) return value.isTextual() && value.textValue().length() <= 3600 && value.textValue().chars().allMatch(c -> c <= 127);
        if (!value.isArray() || value.isEmpty() || value.size() > 10) return false;
        for (JsonNode line : value) if (!line.isTextual() || line.textValue().length() > 20 || line.textValue().chars().anyMatch(c -> c > 127)) return false;
        return true;
    }

    private static boolean numericTotals(JsonNode totals) {
        if (!totals.isObject() || totals.isEmpty()) return false;
        for (JsonNode value : totals) if (!value.isTextual() || !value.textValue().matches("[0-9]+")) return false;
        return true;
    }

    private static boolean sameFields(JsonNode before, JsonNode after) {
        if (before.size() != after.size()) return false;
        var fields = before.fieldNames();
        while (fields.hasNext()) if (!after.has(fields.next())) return false;
        return true;
    }

    private static LocalDate date(JsonNode owner, String field, String element, List<Finding> findings) {
        JsonNode value = owner.get(field);
        if (value == null) {
            review(findings, element, "FULL-YEAR-DATE", "Full-year date required: " + field);
            return null;
        }
        LocalDate result = dateValue(value);
        if (result == null) add(findings, element, "FULL-YEAR-DATE", Status.INVALID, "Invalid ISO calendar date: " + field);
        return result;
    }

    private static LocalDate dateValue(JsonNode value) {
        if (value == null || !value.isTextual() || !value.textValue().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) return null;
        try {
            LocalDate date = LocalDate.parse(value.textValue());
            return date.getYear() >= 1 ? date : null;
        } catch (DateTimeException exception) {
            return null;
        }
    }

    private static String text(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return value != null && value.isTextual() ? value.textValue() : null;
    }
    private static void check(List<Finding> findings, String element, String rule, boolean valid, String reason) {
        add(findings, element, rule, valid ? Status.CHECKS_PASSED : Status.INVALID, reason);
    }
    private static void review(List<Finding> findings, String element, String rule, String reason) {
        add(findings, element, rule, Status.REVIEW_REQUIRED, reason);
    }
    private static void add(List<Finding> findings, String element, String rule, Status status, String reason) {
        findings.add(new Finding(element, status, "CH13-E" + element + "-PROCESSING-" + rule, reason));
    }
}
