package com.coreauth.validator.canonical;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Finding;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.validation.Atl105CalendarDate;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** Section 12.17 numbered observations; ambiguous separator prose is not a certified wire profile. */
final class Segment119ObservationValidator {
    private static final char FS = '\u001c';
    private static final List<String> LABELS = List.of("CC", "TE", "DS", "AO", "DB", "FL", "CS", "PR",
            "CK", "EF", "EC", "SV1", "SV2", "SV3", "SV4", "ECA", "EWIC", "EK", "HD");
    private static final Set<String> NONFINANCIAL = Set.of("AO", "SV1", "SV3", "SV4", "HD");
    private static final List<String> PREFIX = List.of("85", "84", "44", "102", "78", "32", "65", "105",
            "43", "96", "39", "86", "176", "179", "20", "42");

    private Segment119ObservationValidator() { }

    static List<Finding> validate(JsonNode input) {
        List<Finding> findings = new ArrayList<>();
        JsonNode fields = input.path("elements"), context = input.path("context");
        if (input.has("context") && !context.isObject()) check(findings, "002", "context-shape", false, "Context must be an object");
        check(findings, "008", "segment-type", "119".equals(text(fields, "85")), "Segment Type is textual 119");
        check(findings, "009", "length-width", matches(fields, "84", "[0-9]{3}") && number(fields, "84") >= 1,
                "Segment Length is positive N3");
        check(findings, "004", "length-cap", matches(fields, "84", "[0-9]{3}") && number(fields, "84") <= 493,
                "Section 12.17 segment cap is 493");
        check(findings, "010", "information-byte", matches(fields, "44", "[01]"), "Single/multimessage value is 0/1");
        String terminal = text(fields, "102");
        check(findings, "011", "terminal-layout", terminal != null && terminal.matches("[A-Za-z0-9]{13,22}")
                && AppendixDStateCodes.isValidAnsiCode(terminal.substring(2, 4)), "Terminal has device/state/merchant/device positions; assignment is external");
        check(findings, "012", "prompt", "990".equals(text(fields, "78")), "Totals prompt is 990");
        conditional(fields, context, "32", "employeeNumberRequired", "[0-9]{4}", "013", findings);
        conditional(fields, context, "65", "passwordRequired", "[0-9]{6}", "014", findings);
        String date = text(fields, "105");
        check(findings, "015", "totals-date", date != null && (Set.of("111111", "222222", "333333", "444444", "555555", "999999").contains(date)
                || Atl105CalendarDate.isValidMmddyy(date)), "Request calendar or one of six request special codes");
        if (Atl105CalendarDate.needsCenturyForLeapDay(date)) review(findings, "015", "century", "Leap day year 00 requires century evidence");
        review(findings, "015", "activity-window", "Calendar validity does not establish merchant activity-date eligibility");
        check(findings, "016", "hardware", matches(fields, "43", "[A-Za-z0-9 ]{4}"), "Section 12.17 hardware width is four");
        check(findings, "017", "software", matches(fields, "96", "[A-Za-z0-9 ]{8}"), "Software width is eight");
        check(findings, "018", "firmware", matches(fields, "39", "[A-Za-z0-9 ]{8}"), "Firmware width is eight");
        check(findings, "019", "sequence", matches(fields, "86", "[0-9]{6}") && number(fields, "86") > 0, "Sequence is N6, 000001-999999");
        check(findings, "020", "card-table-version", matches(fields, "176", "[0-9]{35}"), "Card table version is N35");
        check(findings, "021", "discount-timestamp", validTimestamp(text(fields, "179")), "Host discount timestamp encodes real CCYYMMDDHHMM");
        if (fields.has("20") && !"".equals(text(fields, "20"))) check(findings, "022", "currency",
                AppendixLCurrencyCodes.isValid(text(fields, "20")), "Populated optional currency is Appendix L listed");
        check(findings, "023", "grand-total", matches(fields, "42", "[0-9]{8}"), "Grand Total is N8 including zero");
        if (input.has("direction")) check(findings, "002", "direction", "REQUEST".equals(text(input, "direction")), "Segment 119 originates in a request");
        else review(findings, "002", "direction", "Direction not observed");
        if (input.has("messageFamily")) check(findings, "002", "family",
                TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST.equals(text(input, "messageFamily")), "Dedicated totals-with-load request family");
        else review(findings, "002", "family", "Message family not observed");
        if (context.has("totalsRequired")) check(findings, "002", "totals-required", context.path("totalsRequired").isBoolean()
                && context.path("totalsRequired").booleanValue(), "Observed transaction requires totals");
        else review(findings, "002", "totals-required", "Totals selection evidence not supplied");
        if (input.has("dataSection")) check(findings, "003", "placement", input.path("dataSection").isIntegralNumber()
                && input.path("dataSection").canConvertToInt() && input.path("dataSection").intValue() == 3, "Segment 119 is in Data Section 3");
        if (input.has("fieldNumber")) check(findings, "003", "placement-field", input.path("fieldNumber").isIntegralNumber()
                && input.path("fieldNumber").canConvertToInt() && input.path("fieldNumber").intValue() == 3, "Segment 119 is Field 3");
        bucketChecks(input, fields, context, findings);
        review(findings, "038", "source-length-conflict", "Section 11.4.1.2 cap 389 conflicts with Section 12.17 cap 493");
        review(findings, "039", "source-wire-conflict", "Separator note numbers buckets 18-20; table numbers them 17-19. Selected profile is not source-certified");
        review(findings, "028", "response-scope", "Approved-response mandatory bucket prose is not imposed on this request");
        return List.copyOf(findings);
    }

    private static void bucketChecks(JsonNode input, JsonNode fields, JsonNode context, List<Finding> findings) {
        JsonNode buckets = input.path("cardBuckets");
        boolean array = buckets.isArray() && !buckets.isEmpty() && buckets.size() <= 20;
        check(findings, "027", "bucket-count", array, "Required ordered buckets array, 1-20 entries");
        StringBuilder encoded = new StringBuilder();
        int previous = -1;
        long financial = 0;
        boolean valid = array;
        for (JsonNode bucket : buckets) {
            String label = text(bucket, "13"), count = text(bucket, "16"), amount = text(bucket, "15");
            int index = label == null ? -1 : LABELS.indexOf(label.stripTrailing());
            boolean labelValid = label != null && index >= 0 && (LABELS.get(index) + " ".repeat(4 - LABELS.get(index).length())).equals(label);
            check(findings, "024", "bucket-label", labelValid, "Source label left-aligned and space-filled to four; Section 12.17 also lists HD");
            boolean countValid = count != null && count.matches("[0-9]{5}") && Integer.parseInt(count) > 0;
            check(findings, "025", "bucket-count-width", countValid, "Request bucket count is N5, 00001-99999; response bucket prose does not override request values");
            boolean amountValid = amount != null && amount.matches("[0-9]{8}");
            check(findings, "026", "bucket-amount", amountValid, "Bucket amount N8");
            check(findings, "027", "bucket-order", index >= 0 && index > previous, "Listed labels occur once in source order");
            valid &= labelValid && countValid && amountValid && index > previous;
            previous = index;
            if (labelValid && amountValid && !NONFINANCIAL.contains(label.stripTrailing())) financial += Long.parseLong(amount);
            encoded.append(label == null ? "" : label).append(count == null ? "" : count).append(amount == null ? "" : amount);
        }
        if (context.has("bucketCoverageComplete") && !context.path("bucketCoverageComplete").isBoolean()) {
            check(findings, "030", "grand-total-exclusion", false, "Bucket coverage control must be boolean");
        } else if (context.path("bucketCoverageComplete").asBoolean(false)) {
            check(findings, "030", "grand-total-exclusion", valid && matches(fields, "42", "[0-9]{8}")
                    && financial == number(fields, "42"), "Complete supplied bucket scope sums financial categories only; excludes AO/SV1/SV3/SV4/HD");
        } else review(findings, "030", "grand-total-exclusion", "Complete bucket coverage evidence needed before comparing Grand Total");
        if (input.has("serializedSegment")) {
            String raw = text(input, "serializedSegment");
            check(findings, "009", "wire-measured-length", raw != null && raw.chars().allMatch(c -> c <= 127)
                    && matches(fields, "84", "[0-9]{3}") && raw.length() == number(fields, "84"), "Measured ASCII bytes equal declared total length");
            String profile = text(context, "wireProfile");
            if (!"TABLE_17_19".equals(profile)) {
                review(findings, "007", "wire-profile", "Explicit TABLE_17_19 profile required to compare selected field/delimiter encoding");
            } else {
                StringBuilder prefix = new StringBuilder();
                for (String id : PREFIX) prefix.append(text(fields, id) == null ? "" : text(fields, id)).append(FS);
                String expected = prefix.toString() + encoded + FS;
                check(findings, "005", "wire-prefix", raw != null && raw.startsWith(prefix.toString()), "Selected profile retains separator after each of 16 prefix fields");
                check(findings, "006", "wire-empty-fields", raw != null && raw.startsWith(prefix.toString()), "Unpopulated prefix fields keep separators");
                check(findings, "007", "wire-buckets", expected.equals(raw), "Selected table profile concatenates bucket triples and ends section with FS");
            }
        } else review(findings, "009", "wire-measured-length", "Serialized segment not observed");
    }

    private static void conditional(JsonNode fields, JsonNode context, String id, String flag, String pattern, String rule, List<Finding> findings) {
        String value = text(fields, id);
        if (fields.has(id) && !"".equals(value)) check(findings, rule, "conditional-format", value != null && value.matches(pattern), "Populated conditional field has source width");
        if (context.has(flag)) check(findings, rule, "conditional-requiredness", context.path(flag).isBoolean()
                && (!context.path(flag).booleanValue() || value != null && value.matches(pattern)), "Requiredness follows explicit observed policy");
        else review(findings, rule, "conditional-requiredness", "Conditional merchant policy not supplied");
    }

    private static boolean validTimestamp(String value) {
        if (value == null || !value.matches("[0-9]{12}") || value.startsWith("0000")) return false;
        try {
            LocalDateTime.of(Integer.parseInt(value.substring(0, 4)), Integer.parseInt(value.substring(4, 6)),
                    Integer.parseInt(value.substring(6, 8)), Integer.parseInt(value.substring(8, 10)), Integer.parseInt(value.substring(10)));
            return true;
        } catch (DateTimeException exception) { return false; }
    }
    private static String text(JsonNode node, String key) { JsonNode value = node.get(key); return value != null && value.isTextual() ? value.textValue() : null; }
    private static boolean matches(JsonNode fields, String id, String pattern) { String value = text(fields, id); return value != null && value.matches(pattern); }
    private static long number(JsonNode fields, String id) { return Long.parseLong(text(fields, id)); }
    private static void check(List<Finding> findings, String rule, String scope, boolean valid, String reason) {
        findings.add(new Finding("SEG119-R-" + rule, scope, valid ? Status.CHECKS_PASSED : Status.INVALID, reason));
    }
    private static void review(List<Finding> findings, String rule, String scope, String reason) {
        findings.add(new Finding("SEG119-R-" + rule, scope, Status.REVIEW_REQUIRED, reason));
    }
}
