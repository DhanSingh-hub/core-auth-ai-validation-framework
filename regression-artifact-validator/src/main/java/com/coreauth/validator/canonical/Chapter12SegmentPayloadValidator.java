package com.coreauth.validator.canonical;

import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Chapter13ProcessingHistoryValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;

/** Numbered-element observations, independent of producer field aliases. Not a full message parser. */
public final class Chapter12SegmentPayloadValidator {
    public enum Status { CHECKS_PASSED, INVALID, REVIEW_REQUIRED }
    public record Finding(String ruleId, String scope, Status status, String reason) { }
    public record Result(Status status, List<Finding> findings, List<String> unassessedCatalogRules) { }
    public static final Set<String> SEGMENTS = Set.of("103", "115", "116", "119", "123", "130", "131", "132", "134", "135", "136",
            "139", "140", "141", "142", "143", "145", "146", "148", "149", "150", "151", "152", "153", "155", "156", "DL1", "DL8");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final char FS = '\u001c';

    public Result validateDownload(JsonNode input, com.coreauth.validator.validation.Chapter13DataElementValidator oracle) {
        if (input == null || !input.isObject() || !input.path("elements").isObject())
            return new Result(Status.INVALID, List.of(new Finding("CH12-ACTUAL-INPUT", "download-input", Status.INVALID, "Observed download object and numbered fields required")), List.of());
        String id = input.path("segment").asText();
        List<Finding> findings = new ArrayList<>();
        JsonNode fields = input.path("elements"), context = input.path("context");
        if (input.has("context") && !context.isObject()) check(findings, "CH12-ACTUAL-EVIDENCE", "download-context", false, "Context must be an object");
        String marker = Map.of("DL2", "!", "DL3", ":", "DL4", "@", "DL5", "$", "DL6", "\\", "DL7", "^").get(id);
        if (marker == null) throw new IllegalArgumentException("Unsupported download block " + id);
        String rule = "SEG" + id + "-R-";
        check(findings, rule + ("DL4".equals(id) || "DL5".equals(id) || "DL6".equals(id) ? "002" : "001"),
                "download-marker", marker.equals(text(fields, "24")), "Source download marker, not numbered SegmentType");
        elementOracle(input, oracle, findings, "download-element");
        List<String> order = switch (id) {
            case "DL2" -> List.of("24", "28");
            case "DL3" -> List.of("24", "25", "21", "22", "23", "65", "34");
            case "DL4" -> List.of("24", "57", "95", "91", "92", "93", "94", "34");
            case "DL5" -> List.of("24", "57", "95", "114", "92", "93", "94", "34");
            case "DL6" -> List.of("24", "startTime", "endTime", "34");
            case "DL7" -> List.of("24", "84", "232");
            default -> throw new IllegalArgumentException(id);
        };
        StringBuilder expected = new StringBuilder();
        boolean complete = true;
        for (String field : order) {
            if ("DL2".equals(id) && "28".equals(field)) {
                complete &= requiredDownload(fields, field, rule + "002", findings);
                check(findings, rule + "002", "dial-type", "1".equals(text(fields, field)), "Transaction dial type1");
            } else complete &= requiredDownload(fields, field, rule + ("DL3".equals(id) ? "002" : "003"), findings);
            if (text(fields, field) != null) expected.append(text(fields, field));
        }
        if ("DL2".equals(id)) {
            for (String name : List.of("primary", "secondary")) {
                JsonNode dial = input.path("dialStrings").path(name);
                if (!dial.isObject()) {
                    review(findings, rule + ("primary".equals(name) ? "002" : "003"), "dial-" + name, "Observed primary/secondary dial fields required");
                    complete = false;
                    continue;
                }
                if (context.has(name) && !context.path(name).isObject())
                    check(findings, rule + "005", "access-context-" + name, false, "Observed per-number access context must be an object");
                com.fasterxml.jackson.databind.node.ObjectNode dialObservation = input.deepCopy();
                dialObservation.set("elements", dial.deepCopy());
                dialObservation.remove("context");
                if (context.path(name).isObject()) dialObservation.set("context", context.path(name).deepCopy());
                elementOracle(dialObservation, oracle, findings, "dial-element-" + name);
                for (String field : List.of("82", "1", "66", "75", "27")) {
                    if (!Set.of("1", "66").contains(field)) complete &= requiredDownload(dial, field, rule + "002", findings);
                    if (text(dial, field) != null) expected.append(text(dial, field));
                }
                check(findings, rule + ("primary".equals(name) ? "002" : "003"), "dial-terminator-" + name,
                        ("primary".equals(name) ? "A" : "F").equals(text(dial, "27")), "Source primaryA / secondaryF terminators");
                if (!context.path(name).has("accessCodeNeeded")) review(findings, rule + "005", "access-context-" + name, "Observed need for conditional access/pause required");
                else if (context.path(name).path("accessCodeNeeded").isBoolean() && context.path(name).path("accessCodeNeeded").booleanValue())
                    complete &= requiredDownload(dial, "1", rule + "005", findings);
            }
            complete &= requiredDownload(fields, "34", rule + "001", findings);
            expected.append(value(fields, "34"));
            JsonNode history = context.path("dialHistory");
            if (context.has("dialHistory")) {
                if (text(input.path("dialStrings").path("primary"), "75") == null || text(input.path("dialStrings").path("secondary"), "75") == null)
                    review(findings, rule + "003", "dial-history-binding", "Missing actual phone identity cannot be supplied by history");
                else check(findings, rule + "003", "dial-history-binding", history.isObject()
                        && java.util.Objects.equals(text(history, "primary"), text(input.path("dialStrings").path("primary"), "75"))
                        && java.util.Objects.equals(text(history, "secondary"), text(input.path("dialStrings").path("secondary"), "75")),
                        "Dial history must bind to actual observed phone fields; allowed attempts are not inferred from missing protocol");
            } else review(findings, rule + "003", "dial-history-binding", "Matched observed dialing history required");
        }
        if ("DL6".equals(id)) {
            for (String time : List.of("startTime", "endTime")) {
                var observation = input.deepCopy();
                var timeFields = com.fasterxml.jackson.databind.node.JsonNodeFactory.instance.objectNode();
                if (fields.has(time)) timeFields.set("166", fields.path(time).deepCopy());
                ((com.fasterxml.jackson.databind.node.ObjectNode) observation).set("elements", timeFields);
                elementOracle(observation, oracle, findings, "store-forward-" + time);
            }
            storeForwardHistory(input, fields, findings);
            JsonNode cards = context.get("merchantCardTypes");
            if (cards == null) review(findings, rule + "001", "merchant-card173", "One actual DL1 card inventory required");
            else {
                boolean valid = cards.isArray(), present = false;
                for (JsonNode card : cards) { valid &= card.isTextual(); present |= card.isTextual() && "173".equals(card.textValue()); }
                check(findings, rule + "001", "merchant-card173", valid && present, "DL6 is only sent with actual merchant Card Type173");
            }
        }
        if ("DL3".equals(id)) downloadedClock(input, fields, findings);
        if (!"DL7".equals(id)) check(findings, rule + ("DL4".equals(id) || "DL5".equals(id) || "DL6".equals(id) ? "002" : "001"),
                "download-end", "~".equals(text(fields, "34")), "Observed download ends with~");
        String wireRule = rule + ("DL7".equals(id) ? "002" : "004");
        if (!input.has("serializedSegment") || !complete) review(findings, wireRule, "download-wire", "Complete actual fields and observed raw wire required; no absent field or padding manufactured");
        else {
            String raw = text(input, "serializedSegment");
            check(findings, wireRule, "download-wire", raw != null && expected.toString().equals(raw), "Source fields concatenate in table order, with no Field Separators");
            int cap = Map.of("DL2", 69, "DL3", 23, "DL4", 52, "DL5", 64, "DL6", 9, "DL7", 104).get(id);
            if ("DL6".equals(id) && raw != null && raw.length() == 10)
                review(findings, rule + "002", "download-cap", "12.47 cap9 conflicts with required1+4+4+1=10; no chosen interpretation");
            else check(findings, rule + ("DL4".equals(id) || "DL5".equals(id) || "DL6".equals(id) ? "002" : "001"),
                    "download-cap", raw != null && raw.length() <= cap, "Measured source block cap");
            if ("DL7".equals(id)) review(findings, rule + "002", "download-length-convention",
                    "Exclusive DataType length wording and AppendixW framing do not resolve own N3 contribution; no formula selected");
        }
        if ("DL7".equals(id)) {
            if (fields.has("84")) pattern(fields, "84", "[0-9]{3}", rule + "006", findings);
            String data = text(fields, "232");
            if (data != null) for (var assessment : new AppendixWDownloadDataOracle().assessDownloadData(data))
                findings.add(new Finding(assessment.businessRequirementId(), assessment.assertionScope(),
                        assessment.status() == AppendixWDownloadDataOracle.Status.PASS ? Status.CHECKS_PASSED
                                : assessment.status() == AppendixWDownloadDataOracle.Status.FAIL ? Status.INVALID : Status.REVIEW_REQUIRED, assessment.reason()));
        }
        Set<String> families = switch (id) {
            case "DL2" -> Set.of("Table Load Response", "Phone Load Response");
            case "DL3" -> Set.of("Table Load Response", "Date and Time Load Response");
            case "DL4", "DL5" -> Set.of("Software Load Response");
            default -> Set.of("Table Load Response");
        };
        check(findings, rule + ("DL6".equals(id) ? "001" : "DL7".equals(id) ? "001" : "DL2".equals(id) ? "008" : "DL3".equals(id) ? "007" : "006"),
                "download-family", families.contains(input.path("messageFamily").asText()), "Source response family");
        if (Set.of("DL4", "DL5").contains(id)) {
            if (!context.has("vendorOwnApplication")) review(findings, rule + "001", "application-owner", "Observed application ownership required");
            else check(findings, rule + "001", "application-owner", context.path("vendorOwnApplication").isBoolean()
                    && !context.path("vendorOwnApplication").booleanValue(), "Vendors supporting own applications do not use this block");
            softwareDownloadHistory(input, fields, findings);
        }
        review(findings, rule + "001", "download-boundary", "Observed block checks do not authenticate host/current configuration or entire response transport");
        return new Result(findings.stream().anyMatch(f -> f.status() == Status.INVALID) ? Status.INVALID : Status.REVIEW_REQUIRED, List.copyOf(findings), List.of());
    }

    private static void downloadedClock(JsonNode input, JsonNode fields, List<Finding> findings) {
        JsonNode clock = input.path("context").get("currentClock");
        String rule = "SEGDL3-R-002", scope = "downloaded-clock";
        if (clock == null) { review(findings, rule, scope, "Observed host instant and merchant zone required for CurrentDate/Time/DayOfWeek; no timezone inferred"); return; }
        if (!clock.isObject()) { check(findings, rule, scope, false, "Clock evidence must be an object"); return; }
        if (!clock.has("hostAt") || !clock.has("merchantZone") || text(fields, "21") == null || text(fields, "22") == null || text(fields, "25") == null) {
            review(findings, rule, scope, "Missing actual clock fields or independently supplied host instant/merchant zone");
            return;
        }
        if (!clock.path("hostAt").isTextual() || !clock.path("merchantZone").isTextual()) {
            check(findings, rule, scope, false, "Clock instant and zone must be observed text"); return;
        }
        boolean matches;
        try {
            var local = java.time.OffsetDateTime.parse(clock.path("hostAt").textValue()).toInstant()
                    .atZone(java.time.ZoneId.of(clock.path("merchantZone").textValue()));
            if (text(fields, "22").startsWith("24") || text(fields, "22").endsWith("60")) {
                review(findings, rule, scope, "Source01-24/01-60 endpoint convention cannot be converted to ISO local time without clarification"); return;
            }
            matches = text(fields, "21").equals(local.format(java.time.format.DateTimeFormatter.ofPattern("MMddyy")))
                    && text(fields, "22").equals(local.format(java.time.format.DateTimeFormatter.ofPattern("HHmm")))
                    && text(fields, "25").equals(Integer.toString(local.getDayOfWeek().getValue() % 7));
        } catch (java.time.DateTimeException exception) { matches = false; }
        check(findings, rule, scope, matches, "Elements21/22/25,19992-20108: current host clock adjusted for observed merchant zone/DST and day/date; zone assignment/clock authenticity external");
    }

    private static void softwareDownloadHistory(JsonNode input, JsonNode fields, List<Finding> findings) {
        String id = input.path("segment").asText(), rule = "SEG" + id + "-R-007";
        JsonNode history = input.path("history").get("softwareLoad");
        if (history == null) { review(findings, rule, "software-load-history", "Observed matching software-load session required"); return; }
        if (!history.isObject()) { check(findings, rule, "software-load-history", false, "Software-load history must be an object"); return; }
        JsonNode stored = history.get("storedElements");
        if (stored == null) review(findings, rule, "software-stored-data", "Observed stored state required; never assumed from received block");
        else {
            boolean same = stored.isObject();
            for (String field : List.of("57", "95", "DL4".equals(id) ? "91" : "114", "92", "93"))
                same &= fields.path(field).isTextual() && stored.path(field).isTextual() && fields.path(field).equals(stored.path(field));
            check(findings, rule, "software-stored-data", same, "10.10 step5: observed stored version/record/endpoint/date/time match actual load fields");
        }
        JsonNode attempts = history.get("attempts");
        if (attempts == null) { review(findings, rule, "software-attempts", "Observed application-load attempt cohort required"); return; }
        if (!attempts.isArray() || attempts.isEmpty()) { check(findings, rule, "software-attempts", false, "Attempts must be a nonempty observed array"); return; }
        check(findings, "DL4".equals(id) ? "SEGDL4-R-008" : rule, "software-attempt-limit", attempts.size() <= 3, "10.10: maximum three automated full-load attempts");
        String endpoint = text(fields, "DL4".equals(id) ? "91" : "114");
        boolean succeeded = false;
        for (int index = 0; index < attempts.size(); index++) {
            JsonNode attempt = attempts.get(index);
            String scope = "software-attempt-" + index;
            if (!attempt.isObject()) { check(findings, rule, scope, false, "Observed attempt must be an object"); continue; }
            if (!attempt.has("succeeded") || !attempt.has("endpoint") || !attempt.has("fullApplication"))
                review(findings, rule, scope, "Observed success, actual endpoint and full-application flag required");
            else check(findings, rule, scope, attempt.path("succeeded").isBoolean() && attempt.path("fullApplication").isBoolean()
                    && attempt.path("fullApplication").booleanValue() && endpoint != null && endpoint.equals(text(attempt, "endpoint")),
                    "10.10 step6: request full application at the actually loaded endpoint");
            if (index == 0) {
                if (!attempt.has("requestedAt")) review(findings, rule, "software-scheduled-attempt", "Observed first request time required");
                else {
                    String at = text(attempt, "requestedAt");
                    boolean match = false;
                    try {
                        if (at != null) {
                            var observed = java.time.OffsetDateTime.parse(at);
                            match = java.util.Objects.equals(text(fields, "92"), observed.format(java.time.format.DateTimeFormatter.ofPattern("MMddyy")))
                                    && java.util.Objects.equals(text(fields, "93"), observed.format(java.time.format.DateTimeFormatter.ofPattern("HHmm")));
                        }
                    } catch (java.time.DateTimeException exception) { match = false; }
                    check(findings, rule, "software-scheduled-attempt", match, "Observed local offset-qualified request date/time match supplied schedule; no timezone inferred");
                }
            }
            if (attempt.path("succeeded").isBoolean() && !attempt.path("succeeded").booleanValue()) {
                if (!attempt.has("declinePrinted")) review(findings, "DL4".equals(id) ? "SEGDL4-R-008" : rule, scope + "-decline", "Observed printed decline required after unsuccessful request");
                else check(findings, "DL4".equals(id) ? "SEGDL4-R-008" : rule, scope + "-decline",
                        attempt.path("declinePrinted").isBoolean() && attempt.path("declinePrinted").booleanValue(), "10.10: unsuccessful request prints decline");
            }
            succeeded |= attempt.path("succeeded").isBoolean() && attempt.path("succeeded").booleanValue();
        }
        if (succeeded) {
            if (!history.has("tableLoadRequestedAfterCompletion")) review(findings, rule, "software-followup-table-load", "Observed subsequent Table Load request required");
            else check(findings, rule, "software-followup-table-load", history.path("tableLoadRequestedAfterCompletion").isBoolean()
                    && history.path("tableLoadRequestedAfterCompletion").booleanValue(), "10.10 step7: completed application load followed by Table Load request");
        }
        review(findings, rule, "software-session-boundary", "Supplied cohort does not prove exhaustive attempts, profile bit authenticity or all transport event order");
    }

    private static boolean requiredDownload(JsonNode fields, String field, String rule, List<Finding> findings) {
        if (!fields.has(field)) { review(findings, rule, "download-required-" + field, "Required actual field missing; no metadata fill"); return false; }
        boolean valid = fields.path(field).isTextual() && !fields.path(field).textValue().isEmpty();
        check(findings, rule, "download-required-" + field, valid, "Required field is observed nonempty text");
        return valid;
    }

    private static void elementOracle(JsonNode input, com.coreauth.validator.validation.Chapter13DataElementValidator oracle,
            List<Finding> findings, String scope) {
        var observation = input.deepCopy();
        var object = (com.fasterxml.jackson.databind.node.ObjectNode) observation;
        object.put("completeness", "PARTIAL");
        if (input.path("context").isObject()) object.set("qualifiers", input.path("context").deepCopy());
        for (var finding : oracle.validate(observation).findings())
            findings.add(new Finding(finding.ruleId(), scope, Status.valueOf(finding.status().name()), finding.reason()));
    }

    private static void storeForwardHistory(JsonNode input, JsonNode fields, List<Finding> findings) {
        JsonNode observations = input.path("context").get("storeForwardObservations");
        String rule = "SEGDL6-R-007";
        if (observations == null) { review(findings, rule, "store-forward-window", "Observed local-clock blocked/unblocked events required"); return; }
        if (!observations.isArray() || observations.isEmpty()) { check(findings, rule, "store-forward-window", false, "Observed events must be a nonempty array"); return; }
        String start = text(fields, "startTime"), end = text(fields, "endTime");
        if (start == null || end == null || !start.matches("[0-9]{4}") || !end.matches("[0-9]{4}")
                || start.compareTo(end) >= 0) { review(findings, rule, "store-forward-window", "Missing fields, across-midnight or equal endpoints need source-owner convention; no window formula invented"); return; }
        for (int i = 0; i < observations.size(); i++) {
            JsonNode event = observations.get(i);
            String local = text(event, "localTime");
            String scope = "store-forward-event-" + i;
            if (local == null || !event.has("blocked")) { review(findings, rule, scope, "Observed local HHMM and blocking flag required"); continue; }
            if (!local.matches("(?:[01][0-9]|2[0-3])[0-5][0-9]") || !event.path("blocked").isBoolean()) {
                check(findings, rule, scope, false, "Observed local HHMM and boolean blocking required"); continue;
            }
            if (local.equals(start) || local.equals(end)) review(findings, rule, scope, "Exact window endpoint inclusion is unspecified");
            else check(findings, rule, scope, event.path("blocked").booleanValue() == (local.compareTo(start) > 0 && local.compareTo(end) < 0),
                    "Appendix E173 and12.47: observed processing blocked within the supplied same-day daily interval; no timezone inferred");
        }
    }

    public Result validate(JsonNode observation) throws IOException {
        List<Finding> findings = new ArrayList<>();
        if (observation == null || !observation.isObject()
                || !"2026-3".equals(observation.path("specificationVersion").asText())
                || !observation.path("elements").isObject()
                || !SEGMENTS.contains(observation.path("segment").asText())) {
            return new Result(Status.INVALID, List.of(new Finding("", "input", Status.INVALID,
                    "Version 2026-3, a supported segment and text-valued numbered elements are required")), List.of());
        }
        String id = observation.path("segment").asText();
        JsonNode fields = observation.path("elements");
        JsonNode catalog = MAPPER.readTree(Atl105Paths.ruleCatalog(id).toFile());
        switch (id) {
            case "103" -> ebtData(observation, fields, findings);
            case "115" -> printData(observation, fields, findings);
            case "116" -> transArmorLoad(observation, fields, findings);
            case "119" -> {
                var inventory = MAPPER.readTree(Atl105Paths.testOutput().resolve("test-solution-independent-review")
                        .resolve("all-element-inventory.json").toFile());
                findings.addAll(Segment119ObservationValidator.validate(observation, new Chapter13ProcessingHistoryValidator(inventory)));
            }
            case "123" -> tokenization(observation, fields, findings);
            case "130", "131" -> emv(observation, fields, findings);
            case "132" -> capk(observation, fields, findings);
            case "134" -> attributes(observation, fields, findings);
            case "135", "136" -> moneris(observation, fields, findings);
            case "139", "140", "141", "142" -> monerisBatch(observation, fields, findings);
            case "143" -> taxProducts(observation, fields, findings);
            case "145", "146" -> enhancedFleet(observation, fields, findings);
            case "148" -> wex(observation, fields, findings);
            case "149", "150" -> fuelPrices(observation, fields, findings);
            case "151", "152" -> marketBasket(observation, fields, findings);
            case "155" -> accountUpdater(observation, fields, findings);
            case "153" -> networkTokens(observation, fields, findings);
            case "156" -> ev(observation, fields, findings);
            case "DL8" -> emvFloorLimits(observation, fields, findings);
            case "DL1" -> merchantData(observation, fields, findings);
            default -> throw new IllegalStateException("Unsupported segment " + id);
        }
        Set<String> referenced = new LinkedHashSet<>();
        for (Finding finding : findings) if (finding.ruleId().startsWith("SEG" + id + "-R-")) referenced.add(finding.ruleId());
        List<String> unassessed = new ArrayList<>();
        for (JsonNode rule : catalog.path("rules")) {
            if (!referenced.contains(rule.path("ruleId").asText())) unassessed.add(rule.path("ruleId").asText());
        }
        Status status = findings.stream().anyMatch(f -> f.status() == Status.INVALID) ? Status.INVALID
                : !unassessed.isEmpty() || findings.stream().anyMatch(f -> f.status() == Status.REVIEW_REQUIRED)
                    ? Status.REVIEW_REQUIRED : Status.CHECKS_PASSED;
        return new Result(status, List.copyOf(findings), List.copyOf(unassessed));
    }

    private static void printData(JsonNode input, JsonNode fields, List<Finding> findings) {
        header(input, fields, 4, 1009, "003", "004", findings);
        direction(input, "RESPONSE", "SEG115-R-001", findings);
        String family = text(input, "messageFamily");
        if (input.has("messageFamily") && family == null) check(findings, "SEG115-R-001", "family", false, "Message family must be text");
        else if (family == null) review(findings, "SEG115-R-001", "family", "Financial response family not supplied");
        else check(findings, "SEG115-R-001", "family",
                Set.of("Financial Transaction Response", "EMV Financial Transaction Response", "Loyalty Card Transaction Response").contains(family),
                "Print Data is scoped to a Financial Transaction Response");
        length(fields, "152", 1, 999, "SEG115-R-008", findings);
        String data = text(fields, "152");
        if (data != null && data.length() > 900 && data.length() <= 999) {
            review(findings, "SEG115-R-008", "source-length-conflict",
                    "Chapter 13 Element 152 caps Print Data at 900; Section 12.14 gives 999. This interval is not source-certified");
        }
        wire(input, value(fields, "85") + FS + value(fields, "84") + FS + value(fields, "152"), "SEG115-R-006", findings);
        if (input.has("serializedSegment")) {
            String raw = text(input, "serializedSegment");
            check(findings, "SEG115-R-007", "field-separators", raw != null
                    && raw.chars().filter(c -> c == FS).count() == 2 && !raw.endsWith(String.valueOf(FS)),
                    "Section 12.14 selected layout has separators between fields 1/2 and 2/3, none appended to Print Data");
        } else review(findings, "SEG115-R-007", "field-separators", "No serialized segment supplied");
        review(findings, "SEG115-R-005", "source-length-conflict",
                "Section 12.14 cap 1009 conflicts with Financial/EMV envelope tables (910/999); complete applicability is not certified");
        JsonNode context = input.path("context");
        if (input.has("context") && !context.isObject()) {
            check(findings, "SEG115-R-002", "context-shape", false, "Print Data context must be an object");
        }
        if (context.has("additionalInformationFlag")) {
            check(findings, "SEG115-R-002", "inclusion-flag", "1".equals(text(context, "additionalInformationFlag")),
                    "Present Print Data cannot use an absent/zero following-data flag");
        } else review(findings, "SEG115-R-002", "inclusion-flag", "Original following-data flag is not observed");
        if (context.has("requestLoyaltyVersion")) {
            check(findings, "SEG115-R-002", "request-version", "2".equals(text(context, "requestLoyaltyVersion")),
                    "Observed original request loyalty version must be 2");
        } else review(findings, "SEG115-R-002", "request-version", "Original request loyalty version is not observed");
        review(findings, "SEG115-R-010", "companion-source-conflict",
                "Section 12.14 no-other-segments statement conflicts with Section 11 response companions; no blanket exclusion inferred");
        review(findings, "SEG115-R-009", "origin-metadata",
                "Section 12.14 documents Segment 115 as Host-originated for every field; a single segment observation carries no"
                        + " independent origin signal to assert against, so this remains a documented, non-asserted metadata fact");
    }

    /**
     * EBT Data Segment (Section 12.4). Element 153/154/164 (WIC Discount Amount/WIC Product
     * Data/EBT Program Data) deep subelement structure is validated independently by
     * {@link Segment103PayloadValidator}; here they are treated as opaque already-encoded
     * strings, consistent with how EMV tag data (elements 189/190) is treated in {@link #emv}.
     */
    private static void ebtData(JsonNode input, JsonNode fields, List<Finding> findings) {
        review(findings, "SEG103-R-001", "placement",
                "Data Section 3 slot assignment is a cross-message companion rule, not observable from a single segment observation");
        review(findings, "SEG103-R-002", "context",
                "EBT requiredness depends on Section 10.5.3/10.5.4.8/10.5.5/10.5.6 transaction-flow context, not field shape alone");
        pattern(fields, "85", "103", "SEG103-R-003", findings);

        boolean ewic = populated(fields, "153") || populated(fields, "154") || populated(fields, "164");
        String segmentLength = text(fields, "84");
        boolean widthOk = segmentLength != null && (ewic ? segmentLength.matches("[0-9]{4}") : segmentLength.matches("[0-9]{3,4}"));
        check(findings, "SEG103-R-004", "length-width", widthOk,
                "Segment Length is N3, or N4 when eWIC WIC/EBT data (elements 153/154/164) is present");
        boolean valueOk = segmentLength != null && segmentLength.matches("[0-9]{3,4}")
                && Integer.parseInt(segmentLength) >= 1 && Integer.parseInt(segmentLength) <= 3334;
        check(findings, "SEG103-R-005", "length-max", valueOk, "Segment 103 maximum length is 3,334 alphanumeric characters");
        check(findings, "SEG100-R-061", "ewic-data-bounded", !ewic || widthOk,
                "eWIC Segment 103 carries bounded WIC/EBT data; Segment Length must use the N4 representation when present");

        if (populated(fields, "18")) pattern(fields, "18", "[0-9]{1,10}", "SEG103-R-009", findings);
        else review(findings, "SEG103-R-009", "clerk-id", "Clerk ID is conditional; not supplied in this observation");
        if (populated(fields, "109")) pattern(fields, "109", "[0-9]{1,10}", "SEG103-R-010", findings);
        else review(findings, "SEG103-R-010", "voucher-id", "Voucher ID is conditional; not supplied in this observation");

        String direction = text(input, "direction");
        if ("REQUEST".equals(direction)) {
            String ordered = join(fields, "85", "84", "18", "109", "153", "154", "164");
            if (!input.has("serializedSegment")) {
                review(findings, "SEG103-R-006", "field-order", "Serialized segment absent; field order is not wire evidence");
                review(findings, "SEG103-R-007", "field-separator", "Serialized segment absent; separator inclusion is not wire evidence");
            } else {
                String actual = text(input, "serializedSegment");
                check(findings, "SEG103-R-006", "field-order", ordered.equals(actual), "Request fields follow Section 12.4 order");
                check(findings, "SEG103-R-007", "field-separator", ordered.equals(actual),
                        "Request separates every field, including empty fields, with a Field Separator");
            }
        } else if ("RESPONSE".equals(direction)) {
            String concatenated = value(fields, "85") + value(fields, "84") + value(fields, "18") + value(fields, "109")
                    + value(fields, "153") + value(fields, "154") + value(fields, "164");
            if (!input.has("serializedSegment")) {
                review(findings, "SEG103-R-006", "field-order", "Serialized segment absent; field order is not wire evidence");
                review(findings, "SEG103-R-008", "no-field-separator", "Serialized segment absent; separator omission is not wire evidence");
            } else {
                String actual = text(input, "serializedSegment");
                check(findings, "SEG103-R-006", "field-order", concatenated.equals(actual), "Response fields follow Section 12.4 order");
                check(findings, "SEG103-R-008", "no-field-separator", concatenated.equals(actual) && actual.indexOf(FS) < 0,
                        "Response contains no Field Separator between Segment 103 fields");
            }
        } else {
            review(findings, "SEG103-R-006", "field-order", "Message direction not supplied; field-order wire form depends on request/response");
            review(findings, "SEG103-R-007", "field-separator", "Message direction not supplied");
            review(findings, "SEG103-R-008", "no-field-separator", "Message direction not supplied");
        }

        JsonNode context = input.path("context");
        if (context.has("origin")) check(findings, "SEG103-R-019", "origin", "device".equalsIgnoreCase(text(context, "origin")),
                "Segment 103 originates at the device");
        else review(findings, "SEG103-R-019", "origin", "Origin not supplied in this observation");
    }

    /**
     * TransArmor Load Data Segment (Section 12.15). Section 12.15 and its companion Section 11.7.5
     * are both stub references to an external "BUYPASS Platform ATL105 Specification Updates for
     * TransArmor Processing" document that is not present in this workspace's extracted text; the
     * field-by-field layout beyond Segment Type/Segment Length is a genuine source-external boundary
     * (SEG116-R-006, provisional P-01) and is deliberately left as an always-review finding rather
     * than invented. Only Segment Type (Element 85, fixed "116") and Segment Length (Element 84, N2,
     * 01-50, derived by the universal Chapter 12 convention per SEG116-R-004) are checkable here.
     */
    private static void transArmorLoad(JsonNode input, JsonNode fields, List<Finding> findings) {
        header(input, fields, 2, 50, "003", "004", findings);
        review(findings, "SEG116-R-006", "field-layout",
                "Sections 12.15 and 11.7.5 are stub references to an external TransArmor specification-updates "
                        + "document not present in this workspace; the field layout beyond Segment Type/Segment Length is not source-confirmed");
    }

    private static void emvFloorLimits(JsonNode input, JsonNode fields, List<Finding> findings) {
        check(findings, "SEGDL8-R-002", "data-type-indicator", "%".equals(text(fields, "24")),
                "Element 24 Data Type Indicator is fixed value '%' (Section 12.49)");
        String segmentLength = text(fields, "84");
        boolean lengthShapeOk = segmentLength != null && segmentLength.matches("[0-9]{3}");
        JsonNode groups = input.path("floorLimitGroups");
        boolean groupsArray = groups.isArray();
        int count = groupsArray ? groups.size() : -1;
        boolean countOk = groupsArray && count >= 1 && count <= 24;
        check(findings, "SEGDL8-R-002", "segment-length-exclusive",
                lengthShapeOk && countOk && segmentLength.equals(String.format("%03d", count * 26)),
                "Element 84 Segment Length (N3) excludes the Data Type Indicator and equals 26 bytes per RID group, "
                        + "the same hybrid framing convention as Segment DL7");
        if (!groupsArray) {
            check(findings, "SEGDL8-R-003", "floor-limit-groups", false,
                    "Floor Limit Data repeats per RID (max 24, 26 bytes each); the repeating group array is required");
        } else {
            boolean allValid = countOk;
            for (JsonNode group : groups) {
                String rid = text(group, "rid");
                String standIn = text(group, "standInIndicator");
                String floorLimit = text(group, "floorLimit");
                String cardType = text(group, "cardType");
                if (rid == null || !rid.matches("[A-Za-z0-9]{10}")) allValid = false;
                if (standIn == null || !standIn.matches("[0-9]")) allValid = false;
                if (floorLimit == null || !floorLimit.matches("[0-9]{12}")) allValid = false;
                if (cardType == null || !cardType.matches("[A-Za-z0-9]{3}")) allValid = false;
            }
            check(findings, "SEGDL8-R-003", "floor-limit-groups", allValid,
                    "Each Floor Limit Data group is RID (233, AN10) + Stand-in Indicator (234, N1) "
                            + "+ Floor Limit (235, N12) + BUYPASS RID Card Type (236, AN3), max 24 groups totalling up to 624 bytes");
        }
        review(findings, "SEGDL8-R-001", "special-flag-gated-table-load",
                "Inclusion of Segment DL8 in a table load depends on a terminal-level Special flag maintained at the "
                        + "BUYPASS Host; not observable from a single segment observation, and its position in the "
                        + "Table Load Response is not given in 11.7.1.2 or the Chapter 12 matrix");
    }

    private static void tokenization(JsonNode input, JsonNode fields, List<Finding> findings) {
        header(input, fields, 3, 186, "001", "002", findings);
        optional(fields, "195", 20, 20, "SEG123-R-007", findings);
        optional(fields, "196", 11, 11, "SEG123-R-012", findings);
        optional(fields, "197", 4, 4, "SEG123-R-006", findings);
        String token = text(fields, "202");
        if (populated(fields, "202")) check(findings, "SEG123-R-008", "cryptogram-block-length",
                token != null && (token.length() == 28 || token.length() == 56), "Cryptogram is block A (28) or A+B (56)");
        String safeKey = text(fields, "203");
        if (populated(fields, "203")) check(findings, "SEG123-R-009", "safekey-framing",
                safeKey != null && safeKey.length() == 58 && safeKey.startsWith("SK"),
                "SafeKey is SK + two 28-byte portions, including space-filled unused portions");
        if (populated(fields, "237")) {
            pattern(fields, "237", "[A-Za-z0-9+/=]{28}", "SEG123-R-010", findings);
            direction(input, "REQUEST", "SEG123-R-010", findings);
        }
        optional(fields, "204", 1, 1, "SEG123-R-012", findings);
        optional(fields, "238", 1, 1, "SEG123-R-012", findings);
        if (populated(fields, "197")) {
            pattern(fields, "197", "[0-9]{4}", "SEG123-R-006", findings);
            direction(input, "RESPONSE", "SEG123-R-006", findings);
        }
        String encoded = join(fields, "85", "84", "195", "196", "197", "202", "203", "204", "237", "238");
        wire(input, encoded, "SEG123-R-004", findings);
        check(findings, "SEG123-R-003", "max-length", encoded.length() <= 186,
                "Segment 123 total encoded length must not exceed 186 alphanumeric characters");
        review(findings, "SEG123-R-005", "context", "Tokenization requiredness needs transaction context; field shape is not eligibility");
        review(findings, "SEG123-R-011", "3ds-routing", "Run Appendix Y oracle with independent DSRP/AAV and Segment 111 Table 36 context");
    }

    private static void emv(JsonNode input, JsonNode fields, List<Finding> findings) {
        boolean request = "130".equals(input.path("segment").asText());
        String prefix = request ? "SEG130-R-" : "SEG131-R-";
        if (input.has("context")) check(findings, prefix + (request ? "005" : "007"), "context-shape",
                input.path("context").isObject(), "EMV context must be an object");
        if (input.has("history")) check(findings, prefix + (request ? "005" : "007"), "history-shape",
                input.path("history").isObject(), "EMV history must be an object");
        if (input.path("history").has("emv")) check(findings, prefix + (request ? "005" : "007"), "history-shape",
                input.path("history").path("emv").isObject(), "EMV session history must be an object");
        header(input, fields, 4, request ? 3043 : 3834, request ? "002" : "003", request ? "003" : "004", findings);
        int maxLength = request ? 3043 : 3834;
        String length84 = text(fields, "84");
        check(findings, request ? "SEG130-R-004" : "SEG131-R-005", "length-cap-" + maxLength,
                length84 != null && length84.matches("[0-9]{4}") && Integer.parseInt(length84) >= 1
                        && Integer.parseInt(length84) <= maxLength,
                "Segment " + (request ? "130" : "131") + " maximum length is " + maxLength + " alphanumeric characters (001-" + maxLength + "/a-z/A-Z)");
        direction(input, request ? "REQUEST" : "RESPONSE", prefix + (request ? "014" : "002"), findings);
        if (!input.has("messageFamily")) review(findings, prefix + (request ? "014" : "002"), "family",
                "Observed EMV Financial Transaction family required");
        else check(findings, prefix + (request ? "014" : "002"), "family",
                (request ? "EMV Financial Transaction Request" : "EMV Financial Transaction Response").equals(text(input, "messageFamily")),
                "EMV data appears only in the corresponding EMV Financial Transaction family");
        if (request) {
            if (populated(fields, "187")) pattern(fields, "187", "[0-9]{25}", "SEG130-R-005", findings);
            review(findings, "SEG130-R-005", "requiredness-conflict",
                    "Chapter 12 makes checksum optional; Chapter 13 Element 187 says required in EMV request");
            if (populated(fields, "188")) {
                String sequence = text(fields, "188");
                check(findings, "SEG130-R-006", "element-188", "   ".equals(sequence) || (sequence != null && sequence.matches("0[0-9]{2}")),
                        "Element 188 EMV Card Sequence Number is 000-099 right-aligned/zero-padded when EMV Tag 5F34 is supplied, "
                                + "or exactly three spaces when the card does not supply EMV Tag 5F34");
            }
            cardSequenceFromChip(fields, findings);
        } else pattern(fields, "187", "[0-9]{25}", "SEG131-R-007", findings);
        AppendixREmvChipDataOracle oracle = new AppendixREmvChipDataOracle();
        for (var assessment : oracle.assess(text(fields, "189"), text(fields, "190"),
                new AppendixREmvChipDataOracle.TransactionContext(true))) {
            findings.add(new Finding(assessment.businessRequirementId(), assessment.assertionScope(),
                    switch (assessment.status()) {
                        case PASS -> Status.CHECKS_PASSED;
                        case FAIL -> Status.INVALID;
                        case REVIEW_REQUIRED, NOT_ASSERTABLE -> Status.REVIEW_REQUIRED;
                    }, assessment.reason()));
        }
        pattern(fields, "189", "[0-9]{3}", prefix + (request ? "007" : "008"), findings);
        String chipLength = text(fields, "189");
        String chipData = text(fields, "190");
        check(findings, prefix + "008", "element-190",
                chipData != null && chipData.matches("(?:[0-9A-Fa-f]{2})+") && chipLength != null
                        && chipLength.matches("[0-9]{3}") && chipData.length() / 2 == Integer.parseInt(chipLength),
                "Element 190 EMV Chip Data is required, is TLV-encoded BCD-packed/hex, and its byte count must agree "
                        + "with the declared Element 189 length");
        List<IndicatorLengthValueSectionValidator.Triad> triads = tables(input, 3, prefix + (request ? "011" : "009"), findings);
        if (triads != null) {
            var result = IndicatorLengthValueSectionValidator.validate(triads, 1, 985, request ? 1999 : 2800);
            check(findings, prefix + (request ? "011" : "009"), "additional-information-framing",
                    result.valid(), result.violations().toString() + (request ? "; final FS counts in 2000-byte request cap" : ""));
            if (!request && triads.isEmpty()) review(findings, "SEG131-R-009", "empty-additional-information",
                    "Response table marks the section required; omission requires source/context reconciliation");
            if (request) for (var triad : triads) {
                String id = triad.indicator();
                if ("001".equals(id)) check(findings, "SEG130-R-020", "appendix-t-table-001",
                        "EMVYES".equals(triad.value()) || "EMVNOT".equals(triad.value()),
                        "Appendix T Table ID 001 (EMV Table Data) must carry exactly 'EMVYES' or 'EMVNOT' (length 6)");
                else if ("002".equals(id)) {
                    check(findings, "SEG130-R-021", "appendix-t-table-002",
                            triad.value() != null && triad.value().length() == 1,
                            "Appendix T Table ID 002 (CARC) has Maximum Length 1; no concrete code catalog is sourced beyond that cap");
                    if (request) review(findings, "SEG130-R-021", "request-side-legality",
                            "Table 002 is described as issuer-returned; its legality in a Segment 130 REQUEST is unconfirmed (SEG130-SME-007)");
                }
            }
        }
        findings.addAll(EmvSegmentWireValidator.validate(input, triads));
        if (request) {
            Status wireStatus = findings.stream().filter(f -> f.ruleId().equals("SEG130-R-013") && f.scope().equals("binary-wire"))
                    .reduce((first, second) -> second).map(Finding::status).orElse(Status.REVIEW_REQUIRED);
            findings.add(new Finding("SEG130-R-022", "fixed-field-separators", wireStatus,
                    "No trailing-optional-field omission allowance: all six fixed fields retain their Field Separators "
                            + "even when empty, verified against the same measured wire used for SEG130-R-013"));
        } else {
            direction(input, "RESPONSE", "SEG131-R-011", findings);
            Status responseWireStatus = findings.stream().filter(f -> f.ruleId().equals("SEG131-R-006") && f.scope().equals("binary-wire"))
                    .reduce((first, second) -> second).map(Finding::status).orElse(Status.REVIEW_REQUIRED);
            findings.add(new Finding("SEG130-R-023", "no-field-separators", responseWireStatus,
                    "Segment 131 uses inverted serialization versus Segment 130: no Field Separators at all, fixed-length/"
                            + "positional fields; verified against the same measured wire used for SEG131-R-006"));
        }
        if (request) {
            JsonNode loaded = input.path("context").get("loadedCapkChecksum");
            if (loaded == null || !populated(fields, "187")) review(findings, "SEG130-R-005", "loaded-checksum",
                    "Both actual request checksum and observed checksum currently in use are required");
            else check(findings, "SEG130-R-005", "loaded-checksum", loaded.isTextual()
                    && loaded.textValue().matches("[0-9]{25}") && loaded.textValue().equals(text(fields, "187")),
                    "Request checksum agrees with observed loaded checksum; provenance is not authenticated");
            review(findings, "SEG130-R-009", "cross-message", "EMV chip/Segment 100 context agreement not proven by a standalone observation");
            review(findings, "SEG130-R-001", "applicability",
                    "Required-in-every-EMV-authorization-class-request presence can only be assessed against the full "
                            + "transaction/companion-segment context, not this segment payload in isolation");
            review(findings, "SEG130-R-010", "cryptogram-authenticity",
                    "Tag 9F26 (Application Cryptogram) authenticity verification is explicitly out of scope for AI/synthetic "
                            + "test generation; it requires a certified EMV kernel or HSM");
            review(findings, "SEG130-R-012", "element-118-reuse",
                    "Element 118 is reused as 'Additional Information' in Segment 112 and 'EMV Additional Information' here; "
                            + "element numbers are not globally unique and must not be assumed to share business meaning");
            review(findings, "SEG130-R-015", "checksum-echo-handshake",
                    "Element 187 is echoed by Segment 131; correlating that handshake requires the matched response observation, "
                            + "not provable from the request payload alone (see SEG131-R-007 for the response-side check)");
            review(findings, "SEG130-R-017", "reversal-exemption",
                    "EMV data is not required on Reversal transactions; distinguishing this legal absence from an illegal "
                            + "absence requires the transaction-type context not modeled in this segment payload");
            review(findings, "SEG130-R-018", "companion-slot-cap",
                    "Data Section 3 occupies five field slots (Segment 130 plus at most four companions); verifying the cap "
                            + "requires the full companion-segment list, not this segment payload in isolation");
            review(findings, "SEG130-R-019", "entry-mode-exclusion",
                    "Segment 130 is present only for genuine chip-read EMV transactions; a fallback (Entry Mode 80) or MSR "
                            + "swipe (Entry Mode 90) carries no Segment 130, which requires entry-mode context not modeled here");
        } else {
            JsonNode matched = input.path("history").path("emv").get("request");
            if (matched == null) review(findings, "SEG131-R-007", "checksum-echo", "Matched actual Segment 130 request observation required");
            else check(findings, "SEG131-R-007", "checksum-echo", matched.isObject()
                    && "130".equals(text(matched, "segment")) && "2026-3".equals(text(matched, "specificationVersion"))
                    && value(matched.path("elements"), "187").matches("[0-9]{25}")
                    && value(matched.path("elements"), "187").equals(text(fields, "187")), "Response echoes matched request checksum; correlation provenance remains external");
            review(findings, "SEG131-R-001", "placement-contradiction",
                    "Section 12.21 prose places Segment 131 at Field No. 4 of Data Section 3, but the EMV Financial "
                            + "Transaction Response layout table places it at Field No. 17/18/19/20 of Data Section 2; "
                            + "this contradiction is preserved, not silently resolved, pending SME confirmation");
            review(findings, "SEG131-R-012", "placement-order",
                    "Segment 131 always follows Segments 112 and 115/120 when present; verifying relative ordering "
                            + "requires the full companion-segment list, not this segment payload in isolation");
            review(findings, "SEG131-R-010", "element-118-reuse",
                    "Element 118 is reused across Segment 112 ('Additional Information', single occurrence), Segment 130 "
                            + "('EMV Additional Information', repeating, request-side), and Segment 131 (repeating, response-side); "
                            + "element numbers are not globally unique and must not be assumed to share business meaning");
        }
        review(findings, request ? "SEG130-R-004" : "SEG131-R-005", "source-conflict",
                "Chapter 11 and Chapters 12/13 publish different maxima; this check uses Chapter 12, not conflict approval");
        findings.addAll(Chapter12ObservedSessionValidator.emv(input));
    }

    private static void cardSequenceFromChip(JsonNode fields, List<Finding> findings) {
        String chip = text(fields, "190"), sequence = text(fields, "188");
        var decoded = new AppendixREmvChipDataOracle().decode(chip);
        if (!decoded.wellFormed()) {
            review(findings, "SEG130-R-006", "chip-card-sequence", "Malformed chip data cannot establish the card sequence; TLV findings assess the observed defect");
            return;
        }
        var sequences = decoded.entries().stream().filter(e -> e.tag().equals("5F34")).toList();
        if (sequences.size() > 1) {
            review(findings, "SEG130-R-006", "chip-card-sequence", "Repeated card sequence tags require matched last-interface evidence; no first-value choice is inferred");
            return;
        }
        if (sequence == null || sequence.isEmpty()) {
            review(findings, "SEG130-R-006", "chip-card-sequence", "Observed Element 188 is required to compare with supplied chip data");
            return;
        }
        if (sequences.isEmpty()) check(findings, "SEG130-R-006", "chip-card-sequence", sequence.equals("   "),
                "Element 188 is exactly three spaces when supplied chip data has no Tag 5F34 (Element 188 source definition)");
        else {
            var tag = sequences.getFirst();
            check(findings, "SEG130-R-006", "chip-card-sequence", tag.length() == 1
                    && tag.valueHex().matches("[0-9]{2}") && sequence.equals("0" + tag.valueHex()),
                    "Element 188 is the chip-supplied 000-099 sequence, right aligned and zero padded; this does not assert amount/currency formulas");
        }
    }

    private static void capk(JsonNode input, JsonNode fields, List<Finding> findings) throws IOException {
        header(input, fields, 3, 77, "002", "003", findings);
        String length84 = text(fields, "84");
        check(findings, "SEG132-R-004", "length-cap-77",
                length84 != null && length84.matches("[0-9]{3}") && Integer.parseInt(length84) >= 1 && Integer.parseInt(length84) <= 77,
                "Segment 132 maximum length is 77 alphanumeric characters (01-77/a-z/A-Z)");
        direction(input, "REQUEST", "SEG132-R-012", findings);
        pattern(fields, "86", "(?!000000)[0-9]{6}", "SEG132-R-005", findings);
        String protocol = input.path("context").path("protocol").asText();
        if ("MULTITHREADED_DIAL".equals(protocol)) pattern(fields, "86", "1[0-9]{5}", "SEG132-R-005", findings);
        else if (protocol.isBlank()) review(findings, "SEG132-R-005", "protocol", "Protocol required to decide limited sequence range");
        pattern(fields, "102", "\\+\\*[ -~]{11}", "SEG132-R-006", findings);
        pattern(fields, "48", "K", "SEG132-R-007", findings);
        length(fields, "43", 4, 4, "SEG132-R-008", findings);
        length(fields, "96", 8, 8, "SEG132-R-008", findings);
        length(fields, "39", 8, 8, "SEG132-R-008", findings);
        pattern(fields, "187", "[0-9]{25}", "SEG132-R-009", findings);
        pattern(fields, "11", "[0-9]{3}", "SEG132-R-010", findings);
        review(findings, "SEG132-R-001", "placement", "Companion/placement conflict remains open");
        review(findings, "SEG132-R-011", "wire", "Source does not unambiguously describe all remaining separators");
        if (input.has("history") && !input.path("history").isObject()) {
            check(findings, "SEG132-R-009", "capk-history-shape", false, "Observed CAPK history must be an object");
        } else if (input.path("history").has("capk")) {
            for (var finding : Chapter13ProcessingHistoryValidator.validateObservedCapk(input.path("history").path("capk")))
                findings.add(new Finding(finding.ruleId(), "capk-processing-history", Status.valueOf(finding.status().name()), finding.reason()));
            findings.addAll(Chapter12LoadLifecycleValidator.capk(input));
        } else review(findings, "SEG132-R-009", "capk-processing-history", "Observed load transfer/file history required; request field shape does not prove replacement");
    }

    private static void attributes(JsonNode input, JsonNode fields, List<Finding> findings) {
        header(input, fields, 4, 19, "004", "004", findings);
        direction(input, "RESPONSE", "SEG134-R-001", findings);
        pattern(fields, "198", "[DSX]", "SEG134-R-005", findings);
        if ("X".equals(text(fields, "198"))) {
            Boolean enabled = bool(input.path("context"), "signatureDebitAvailable");
            if (enabled == null) review(findings, "SEG134-R-005", "availability", "X requires relationship-manager availability evidence");
            else check(findings, "SEG134-R-005", "availability", enabled, "Signature debit availability from supplied context");
        }
        pattern(fields, "199", "[TF ]", "SEG134-R-006", findings);
        pattern(fields, "200", "[A-Za-z0-9][A-Za-z0-9 ]{9}", "SEG134-R-007", findings);
        String body = value(fields, "85") + value(fields, "84") + value(fields, "198") + value(fields, "199") + value(fields, "200");
        wire(input, body, "SEG134-R-002", findings);
        check(findings, "SEG134-R-003", "encoded-size", body.length() == 19, "Fixed positional content must total 19 characters");
        review(findings, "SEG134-R-004", "source-conflict", "Own table says N4; global seven-segment list omits 134 (SEG134-SME-001)");
    }

    private static void moneris(JsonNode input, JsonNode fields, List<Finding> findings) {
        boolean request = "135".equals(input.path("segment").asText());
        String prefix = request ? "SEG135-R-" : "SEG136-R-";
        header(input, fields, 3, 999, "002", "003", findings);
        length(fields, "213", 1, 100, prefix + "005", findings);
        direction(input, request ? "REQUEST" : "RESPONSE", prefix + "001", findings);
        AppendixVMonerisDataOracle oracle = new AppendixVMonerisDataOracle();
        var assessments = request ? oracle.assessRequest(text(fields, "213")) : oracle.assessResponse(text(fields, "213"));
        for (var assessment : assessments) {
            findings.add(new Finding(assessment.businessRequirementId(), assessment.assertionScope(),
                    switch (assessment.status()) {
                        case PASS -> Status.CHECKS_PASSED;
                        case FAIL -> Status.INVALID;
                        case REVIEW_REQUIRED -> Status.REVIEW_REQUIRED;
                    }, assessment.reason()));
        }
        if (request) wire(input, join(fields, "85", "84", "213"), prefix + "004", findings);
        else review(findings, prefix + "004", "wire", "Source specifies a trailing separator, not full internal placement");
        review(findings, prefix + "001", "routing", "Actual Moneris routing/eligibility requires independent context");
    }

    private static void networkTokens(JsonNode input, JsonNode fields, List<Finding> findings) {
        header(input, fields, 3, 999, "002", "002", findings);
        direction(input, "REQUEST", "SEG153-R-002", findings);
        List<IndicatorLengthValueSectionValidator.Triad> triads = tables(input, 3, "SEG153-R-003", findings);
        if (triads == null) return;
        check(findings, "SEG153-R-003", "nonempty-section", !triads.isEmpty(), "Network Token Data is required");
        check(findings, "SEG153-R-003", "table-framing",
                IndicatorLengthValueSectionValidator.validate(triads, 1, 18, 999).valid(), "N3/N3 tables, section max 999");
        for (var table : triads) {
            boolean valid = switch (table.indicator() == null ? "" : table.indicator()) {
                case "001" -> table.value() != null && table.value().length() >= 13 && table.value().length() <= 18;
                case "002" -> table.value() != null && table.value().length() == 4;
                case "003", "004", "005", "006" -> table.value() != null && table.value().length() == 1;
                default -> false;
            };
            check(findings, "SEG153-R-003", "table-" + table.indicator(), valid,
                    "Published table ID and fixed/variable length; no invented token alphabet or expiry semantics");
        }

        encodedTables(input, triads, 3, "SEG153-R-001", findings);
        review(findings, "SEG153-R-002", "wire", "Section 12.38 does not specify separator placement");
    }

    private static void monerisBatch(JsonNode input, JsonNode fields, List<Finding> findings) {
        String id = input.path("segment").asText();
        String prefix = "SEG" + id + "-R-";
        String headerRule = "141".equals(id) ? "003" : "002";
        String fieldRule = "139".equals(id) || "142".equals(id) ? "003" : headerRule;
        boolean request = "139".equals(id) || "141".equals(id);
        header(input, fields, 3, 999, headerRule, headerRule, findings);
        direction(input, request ? "REQUEST" : "RESPONSE", prefix + "001", findings);
        length(fields, "102", 1, 22, prefix + fieldRule, findings);
        length(fields, "206", 48, 48, prefix + fieldRule, findings);
        length(fields, "207", 8, 8, prefix + fieldRule, findings);
        length(fields, "208", 13, 13, prefix + fieldRule, findings);
        pattern(fields, "214", "[0-9]{3}", prefix + fieldRule, findings);
        if (request) pattern(fields, "215", "[3-6]", prefix + fieldRule, findings);
        else length(fields, "216", 16, 16, prefix + fieldRule, findings);
        if ("140".equals(id) || "141".equals(id)) {
            for (String element : List.of("217", "219", "221")) pattern(fields, element, "[0-9]{4}", prefix + fieldRule, findings);
            for (String element : List.of("218", "220", "222")) pattern(fields, element, "[+-][0-9]{1,18}",
                    "140".equals(id) ? "SEG140-R-003" : prefix + fieldRule, findings);
        }
        if ("142".equals(id)) {
            length(fields, "210", 16, 16, prefix + fieldRule, findings);
            review(findings, prefix + "003", "mac-authenticity", "MAC width is not cryptographic verification");
        }
        review(findings, prefix + "001", "lifecycle", "Matched request echoes, batch totals and once-per-pay-point close need event history");
        review(findings, prefix + ("141".equals(id) ? "002" : "139".equals(id) ? "004" : headerRule), "wire",
                "This observation checks field values, not all separators, padding or batch-close encoding");
    }

    private static void merchantData(JsonNode input, JsonNode fields, List<Finding> findings) throws IOException {
        com.fasterxml.jackson.databind.node.ObjectNode merchantObservation = input.deepCopy();
        merchantObservation.put("completeness", "PARTIAL");
        for (var finding : new com.coreauth.validator.validation.Chapter13DataElementValidator(Atl105Paths.root()).validate(merchantObservation).findings())
            findings.add(new Finding(finding.ruleId(), "merchant-element", Status.valueOf(finding.status().name()), finding.reason()));
        check(findings, "SEGDL1-R-002", "markers", "#".equals(text(fields, "24")) && "~".equals(text(fields, "34")),
                "Merchant block uses # and ~, not numbered SegmentType/SegmentLength fields");
        for (var field : Map.of("53", 24, "98", 16, "3", 24, "4", 21, "54", 13).entrySet())
            length(fields, field.getKey(), 1, field.getValue(), "SEGDL1-R-003", findings);
        pattern(fields, "59", "0[1-9]|[1-9][0-9]", "SEGDL1-R-004", findings);
        JsonNode cards = input.path("cardTypes");
        if (!cards.isArray()) {
            check(findings, "SEGDL1-R-004", "card-types", false, "Observed repeated Card Types array required");
            return;
        }
        check(findings, "SEGDL1-R-004", "count", text(fields, "59") != null && text(fields, "59").matches("0[1-9]|[1-9][0-9]")
                && Integer.parseInt(text(fields, "59")) == cards.size(), "01-99 count equals observed repetitions");
        StringBuilder wire = new StringBuilder(value(fields, "24"));
        for (String field : List.of("53", "98", "3", "4", "54", "59")) wire.append(value(fields, field));
        boolean storeForward = false;
        for (JsonNode card : cards) {
            check(findings, "SEGDL1-R-009", "card-type", card.isTextual()
                    && AppendixETableLoadCardTypeCodes.supportedCodes().contains(card.textValue()), "Appendix E table-load code, not Prompt Code");
            if (card.isTextual()) wire.append(card.textValue());
            storeForward |= "173".equals(card.asText());
        }
        wire.append(value(fields, "34"));
        if (input.has("serializedSegment")) {
            String observed = text(input, "serializedSegment");
            check(findings, "SEGDL1-R-006", "merchant-wire", observed != null && observed.equals(wire.toString())
                    && observed.chars().allMatch(c -> c >= 32 && c <= 126), "Observed fields concatenate without Field Separators or manufactured padding");
            check(findings, "SEGDL1-R-001", "merchant-wire-cap", observed != null && observed.length() <= 399, "Observed merchant block <=399 characters");
        } else review(findings, "SEGDL1-R-006", "merchant-wire", "Actual merchant block required; supplied field declarations do not prove wire");
        review(findings, "SEGDL1-R-003", "padding", "Fixed/up-to source field padding remains unresolved; no arbitrary padding is introduced");
        if (input.has("messageFamily")) check(findings, "SEGDL1-R-007", "family", "Table Load Response".equals(text(input, "messageFamily")), "Merchant block is host Table Load Response only");
        else review(findings, "SEGDL1-R-007", "family", "Observed Table Load Response family required");
        JsonNode context = input.path("context");
        if (storeForward) {
            if (!context.path("segmentsPresent").isArray() || !context.path("segmentInventoryComplete").isBoolean()
                    || !context.path("segmentInventoryComplete").booleanValue())
                review(findings, "SEGDL1-R-005", "store-forward-companion", "Complete observed response block inventory required for173");
            else {
                boolean dl6 = false;
                for (JsonNode segment : context.path("segmentsPresent")) dl6 |= segment.isTextual() && "DL6".equals(segment.textValue());
                check(findings, "SEGDL1-R-005", "store-forward-companion", dl6, "Observed173 requires Store and Forward blockDL6");
            }
        }
        if (!context.has("merchantLoadFlag")) review(findings, "SEGDL1-R-008", "merchant-load-flag", "Observed merchant-profile load flag required");
        else check(findings, "SEGDL1-R-008", "merchant-load-flag", "TABL".equals(text(context, "merchantLoadFlag")), "Merchant block requires TABL load flag");
        review(findings, "SEGDL1-R-012", "block-wire", "Individual block equality does not prove entire response block order/transport delimiters");
    }

    private static void taxProducts(JsonNode input, JsonNode fields, List<Finding> findings) {
        header(input, fields, 3, 999, "003", "003", findings);
        JsonNode products = input.path("taxProducts");
        if (!products.isArray() || products.isEmpty() || products.size() > 10) {
            check(findings, "SEG143-R-005", "products", false, "1-10 tax product entries required");
            return;
        }
        pattern(fields, "62", "0[1-9]|10", "SEG143-R-004", findings);
        String count = text(fields, "62");
        check(findings, "SEG143-R-004", "count", count != null && count.matches("0[1-9]|10")
                && Integer.parseInt(count) == products.size(), "Number of Products matches tax product count");
        JsonNode companions = input.path("context").path("segment102ProductCodes");
        if (!companions.isArray()) review(findings, "SEG143-R-001", "companion-context", "Ordered Segment 102 product codes required");
        else {
            boolean same = companions.size() == products.size();
            for (int i = 0; i < products.size() && same; i++) same = companions.get(i).isTextual()
                    && companions.get(i).textValue().equals(text(products.get(i), "productCode"));
            check(findings, "SEG143-R-001", "companion-order", same, "Every tax product has corresponding same-order Segment 102 product");
        }
        StringBuilder body = new StringBuilder();
        boolean actualAuthority = input.path("actualFieldAuthority").asBoolean(false);
        boolean taxBodyKnown = true;
        Status taxCount = Status.CHECKS_PASSED;
        for (JsonNode product : products) {
            pattern(product, "productCode", "[0-9]{3}", "SEG143-R-005", findings);
            body.append(value(product, "productCode"));
            JsonNode taxes = product.path("taxes");
            if (actualAuthority && taxes.isMissingNode()) {
                taxCount = Chapter12ObservedSessionValidator.combine(taxCount, Status.REVIEW_REQUIRED);
                taxBodyKnown = false;
                continue;
            }
            if (!taxes.isArray() || taxes.isEmpty() || taxes.size() > 3) {
                taxCount = Status.INVALID;
                taxBodyKnown = false;
                continue;
            }
            for (int i = 0; i < taxes.size(); i++) {
                JsonNode tax = taxes.get(i);
                pattern(tax, "flag", "[IEN]", "SEG143-R-006", findings);
                body.append(value(tax, "flag"));
                if ("N".equals(text(tax, "flag"))) {
                    check(findings, "SEG143-R-006", "omission", !tax.has("type") && !tax.has("amount"),
                            "N omits type and amount entirely, not blank fields");
                } else {
                    length(tax, "type", 3, 3, "SEG143-R-007", findings);
                    pattern(tax, "amount", "[0-9]{1,9}", "SEG143-R-005", findings);
                    if ("CANADA".equals(input.path("context").path("country").asText())) {
                        String type = text(tax, "type");
                        if ("QST".equals(type)) review(findings, "SEG143-R-007", "tax-type-enumeration",
                                "Source conflict:12.30 lists GST/HST/PST; Elements224/227/230 also list QST (25026-25137)");
                        else check(findings, "SEG143-R-007", "tax-type-enumeration", type != null && type.matches("GST|HST|PST"),
                                "Tax Type is one of GST/HST/PST for Canadian transactions");
                    } else review(findings, "SEG143-R-007", "country-context", "GST/HST/PST/QST closed enumeration is Canadian only");
                    body.append(value(tax, "type")).append(value(tax, "amount"));
                }
                body.append(i + 1 == taxes.size() ? FS : '\\');
            }
        }
        findings.add(new Finding("SEG143-R-005", "tax-count", taxCount,
                "Source14942-14947: at most three tax flag entries per observed product; no-tax usesN; absent actual tax list is missing evidence, not an empty observed list"));
        check(findings, "SEG143-R-005", "tax-section-cap", body.length() <= 360, "Repeated tax data cap 360");
        String expectedWire = value(fields, "85") + FS + value(fields, "84") + FS + value(fields, "62") + body;
        if (actualAuthority && !taxBodyKnown && input.has("serializedSegment") && !input.path("serializedSegment").isTextual())
            check(findings, "SEG143-R-008", "wire", false, "Observed serialized tax segment must be textual even when a logical tax list is missing");
        else if (actualAuthority && !taxBodyKnown) review(findings, "SEG143-R-008", "wire", "Incomplete observed tax body cannot establish expected physical separators");
        else wire(input, expectedWire, "SEG143-R-008", findings);
        boolean earlyTermination = false;
        for (JsonNode product : products) {
            JsonNode taxes = product.path("taxes");
            if (taxes.isArray() && taxes.size() >= 1 && taxes.size() < 3) earlyTermination = true;
        }
        if (actualAuthority && !taxBodyKnown) {
            review(findings, "SEG143-R-009", "early-termination-separator", "Incomplete actual tax body cannot establish continuation/termination separators");
        } else if (earlyTermination && !input.has("serializedSegment")) {
            review(findings, "SEG143-R-009", "early-termination-separator",
                    "Serialized segment absent; early-termination delimiter choice cannot be confirmed from field data alone");
        } else if (earlyTermination) {
            check(findings, "SEG143-R-009", "early-termination-separator",
                    expectedWire.equals(text(input, "serializedSegment")),
                    "Product ending its tax entries before 3 taxes closes the last reported tax with a Field "
                            + "Separator, not the '\\' continuation delimiter");
        } else {
            review(findings, "SEG143-R-009", "applicability",
                    "No product in this observation terminates its tax list before 3 entries; early-termination "
                            + "delimiter rule is not exercised");
        }
        review(findings, "SEG143-R-002", "tax-value-policy", "Format-only validation; tax applicability/rates are not independently approved");
    }

    private static void enhancedFleet(JsonNode input, JsonNode fields, List<Finding> findings) throws IOException {
        findings.addAll(Chapter12ObservedSessionValidator.fleet(input));
        boolean request = "145".equals(input.path("segment").asText());
        String prefix = request ? "SEG145-R-" : "SEG146-R-";
        String rule = request ? "006" : "002";
        header(input, fields, 3, 999, request ? "005" : "001", request ? "005" : "001", findings);
        direction(input, request ? "REQUEST" : "RESPONSE", prefix + (request ? "001" : "002"), findings);
        List<IndicatorLengthValueSectionValidator.Triad> tables = tables(input, 3, prefix + rule, findings);
        if (tables == null) return;
        check(findings, prefix + rule, "fleet-tlv-framing", !tables.isEmpty()
                && IndicatorLengthValueSectionValidator.validate(tables, 1, 999, 999).valid(), "Required fleet TLV data, cap 999");
        Set<String> ids = request ? Set.of("001", "002", "004", "006", "007", "008")
                : Set.of("001", "002", "003", "004", "005", "010");
        String authorizer = input.path("context").path("authorizer").asText();
        if (input.has("history")) check(findings, prefix + rule, "history-shape", input.path("history").isObject(), "Fleet history must be an object");
        if (input.path("history").has("fleet")) check(findings, prefix + rule, "history-shape",
                input.path("history").path("fleet").isObject(), "Fleet prompt history must be an object");
        if (input.has("context")) check(findings, prefix + rule, "context-shape",
                input.path("context").isObject(), "Fleet context must be an object");
        if (input.path("context").has("authorizer")) check(findings, prefix + rule, "authorizer-shape",
                input.path("context").path("authorizer").isTextual(), "Observed authorizer must be text");
        findings.addAll(Chapter12ObservedSessionValidator.fleetPurchases(input));
        Map<String, Integer> caps = request ? Map.of("001", 20, "002", 350, "004", 200, "006", 17, "007", 15, "008", 9)
                : Map.of("001", 20, "002", 350, "003", 350, "004", 200, "005", 70, "010", 200);
        for (var table : tables) {
            findings.addAll(EnhancedFleetTableValidator.validate(table, request, authorizer, input.path("history").path("fleet")));
            check(findings, prefix + rule, "fleet-table-id", table.indicator() != null && ids.contains(table.indicator()),
                    "Published enhanced fleet table ID");
            if (table.indicator() != null && caps.containsKey(table.indicator())) {
                check(findings, prefix + rule, "fleet-table-cap", table.value() != null
                        && table.value().length() <= caps.get(table.indicator())
                        && table.value().chars().allMatch(c -> c >= 32 && c <= 126), "Measured source-specific ASCII fleet table cap");
            }
            if (request && "006".equals(table.indicator())) check(findings, "SEG145-R-006", "payee-name",
                    table.value() != null && table.value().matches("[A-Za-z ]+,[A-Za-z]"), "Payee uses Last Name,First Initial");
            if (request && "008".equals(table.indicator())) check(findings, "SEG145-R-006", "cash-advance",
                    table.value() != null && table.value().matches("[0-9]+\\.[0-9]+"), "Cash advance includes a decimal point");
            if (!request && "001".equals(table.indicator()) && table.value() != null) {
                String flag = table.value().split(",", -1)[0];
                check(findings, "SEG146-R-002", "settlement-flag", flag.isEmpty() || Set.of("CP", "DB", "DF", "RC", "RF", "FN").contains(flag),
                        "Present first settlement flag is one of six source-listed values; future flags are not invented");
            }
            if ("004".equals(table.indicator())) findings.addAll(EnhancedFleetPromptValidator.validate(table.value(), request,
                    authorizer, input.path("history").path("fleet")));
            if (request && Set.of("VOYAGER_EMV", "VISA_FLEET_2", "COMDATA", "MASTERCARD_ENHANCED_FLEET_EMV").contains(authorizer)) {
                check(findings, "SEG145-R-003", "restricted-table", "004".equals(table.indicator()), "This authorizer permits Prompt Table 004 only");
            }
            if (!request && "COMDATA".equals(authorizer)) check(findings, "SEG146-R-003", "comdata-table",
                    !"002".equals(table.indicator()), "Nonfuel limits Table 002 is absent from Comdata responses");
        }
        JsonNode moneyCode = input.path("context").get("moneyCodeTransaction");
        if (request && moneyCode != null) {
            check(findings, "SEG145-R-006", "money-code-check", moneyCode.isBoolean()
                    && (!moneyCode.booleanValue() || tables.stream().anyMatch(t -> "007".equals(t.indicator()))),
                    "An observed Money Code transaction requires Check Number Table 007");
        } else if (request) review(findings, "SEG145-R-006", "money-code-check", "Money Code applicability is not observed");
        if (request && input.path("context").path("segmentsPresent").isArray()) {
            boolean fleet = false;
            for (JsonNode id : input.path("context").path("segmentsPresent")) if ("101".equals(id.asText())) fleet = true;
            check(findings, "SEG145-R-002", "companion-exclusion", !fleet, "Do not combine 101 and 145");
        } else if (request) review(findings, "SEG145-R-002", "companion-context", "Segment presence list required");
        review(findings, prefix + rule, "table-semantics", "Dynamic Card Table mapping, completeness and host-authenticated product-limit correlation require independent configuration/history");
        if (authorizer.isBlank()) review(findings, prefix + (request ? "003" : "003"), "authorizer-context", "Authorizer needed for table restrictions");
        if (request) review(findings, "SEG145-R-004", "production-availability", "Expected June 2026 availability is not proof of production enablement");
        review(findings, prefix + (request ? "005" : "001"), "wire", "Source does not fully specify outer separator placement");
    }

    private static void wex(JsonNode input, JsonNode fields, List<Finding> findings) {
        header(input, fields, 3, 999, "002", "002", findings);
        direction(input, "RESPONSE", "SEG148-R-001", findings);
        length(fields, "240", 1, 17, "SEG148-R-004", findings);
        pattern(fields, "240", "[A-Za-z0-9]{2,3}=[A-Za-z0-9]{1,5},[A-Za-z0-9]{1,5} [A-Za-z0-9]", "SEG148-R-004", findings);
        String data = text(fields, "240");
        if (input.has("context")) check(findings, "SEG148-R-001", "context-shape", input.path("context").isObject(), "WEX context must be an object");
        if (input.path("context").has("authorizer")) check(findings, "SEG148-R-001", "authorizer-shape",
                input.path("context").path("authorizer").isTextual(), "Observed WEX authorizer must be text");
        if (input.has("messageFamily")) check(findings, "SEG148-R-001", "family-shape", input.path("messageFamily").isTextual(), "Family must be text");
        if (data != null && data.indexOf('=') == 2) {
            check(findings, "SEG148-R-004", "fuel-group", Set.of("00", "01", "06", "07", "08", "10", "11", "12", "13")
                    .contains(data.substring(0, 2)), "Published two-digit fuel group");
        }
        JsonNode authorizer = input.path("context").get("authorizer");
        if (authorizer == null || !input.has("messageFamily")) {
            review(findings, "SEG148-R-001", "routing", "Both observed WEX authorizer and Financial Transaction response family are required");
        } else check(findings, "SEG148-R-001", "routing", authorizer.isTextual() && Set.of("WEX", "WEX_OTR").contains(authorizer.textValue())
                && "Financial Transaction Response".equals(text(input, "messageFamily")), "WEX Financial Transaction response only");
        review(findings, "SEG148-R-003", "source-conflict", "Source says max 23 but range 001-999; no invented resolution");
    }

    private static void fuelPrices(JsonNode input, JsonNode fields, List<Finding> findings) {
        boolean request = "149".equals(input.path("segment").asText());
        String prefix = request ? "SEG149-R-" : "SEG150-R-";
        header(input, fields, 3, 999, "003", "003", findings);
        direction(input, request ? "REQUEST" : "RESPONSE", prefix + "001", findings);
        length(fields, "102", 1, 22, prefix + "003", findings);
        if (request) {
            length(fields, "241", 1, 999, "SEG149-R-003", findings);
            wire(input, join(fields, "85", "84", "102", "241"), "SEG149-R-002", findings);
            review(findings, "SEG149-R-001", "price-policy", "Example price tags are not an exhaustive Comdata code/price policy");
        } else {
            length(fields, "26", 2, 2, "SEG150-R-003", findings);
            review(findings, "SEG150-R-002", "wire", "Trailing separator specified; internal placement needs source clarification");
        }
        review(findings, prefix + "001", "lifecycle", "Comdata routing and request/response linkage require transaction context");
    }

    private static void marketBasket(JsonNode input, JsonNode fields, List<Finding> findings) {
        boolean request = "151".equals(input.path("segment").asText());
        String prefix = request ? "SEG151-R-" : "SEG152-R-";
        header(input, fields, 4, 9999, request ? "004" : "002", request ? "005" : "002", findings);
        length(input, "marketBasketData", 1, 2300, prefix + (request ? "005" : "003"), findings);
        direction(input, request ? "REQUEST" : "RESPONSE", prefix + (request ? "004" : "002"), findings);
        JsonNode datasets = input.path("datasets");
        if (datasets.isArray()) {
            int dv = 0, pi = 0, pu = 0;
            for (int i = 0; i < datasets.size(); i++) {
                String type = text(datasets.get(i), "type");
                if ("DV".equals(type)) dv++;
                else if ("PI".equals(type)) pi++;
                else if ("PU".equals(type)) pu++;
                else check(findings, prefix + (request ? "005" : "003"), "dataset-type", false, "Only DV/PI and response PU datasets");
            }
            check(findings, prefix + (request ? "005" : "003"), "dataset-counts", dv == 1 && pi <= 10
                    && (request ? pu == 0 : pu <= 10) && datasets.size() > 0 && "DV".equals(text(datasets.get(0), "type")),
                    "One leading DV, max ten PI, response-only max ten PU");
        } else review(findings, prefix + (request ? "005" : "003"), "datasets", "Parsed dataset inventory absent");
        review(findings, prefix + (request ? "005" : "003"), "external-format",
                "Parsed dataset inventory is not proof of raw Market Basket Data; external InComm format document required");
        String marketBasketData = text(input, "marketBasketData");
        String wireExpected = request ? join(fields, "85", "84") + (marketBasketData == null ? "" : marketBasketData) + FS
                : value(fields, "85") + value(fields, "84") + (marketBasketData == null ? "" : marketBasketData) + FS;
        wire(input, wireExpected, request ? "SEG151-R-003" : "SEG152-R-001", findings);
        if (request) {
            review(findings, "SEG151-R-002", "source-conflict", "2309 stated max vs 3334 range; host/device origin also conflicts");
            review(findings, "SEG151-R-001", "placement", "Any Data Section No. 3 field slot and device origination are envelope-level placement facts; this segment-level observation cannot confirm actual envelope slot assignment or device origination");
        }
    }

    private static void accountUpdater(JsonNode input, JsonNode fields, List<Finding> findings) {
        header(input, fields, 3, 999, "006", "006", findings);
        direction(input, "RESPONSE", "SEG155-R-001", findings);
        List<IndicatorLengthValueSectionValidator.Triad> tables = tables(input, 3, "SEG155-R-003", findings);
        if (tables == null) return;
        check(findings, "SEG155-R-003", "tlv-framing", IndicatorLengthValueSectionValidator.validate(tables, 1, 19, 999).valid(),
                "N3/N3 table framing; table count/mandatory combinations are not inferred");
        for (var table : tables) {
            if (table.indicator() == null || table.value() == null) continue;
            var assessment = AppendixADAccountUpdaterOracle.assessResponseLayout(Map.of(table.indicator(), table.value()));
            findings.add(new Finding(assessment.businessRequirementId(), "updater-logical-table",
                    switch (assessment.status()) {
                        case PASS -> Status.CHECKS_PASSED;
                        case FAIL -> Status.INVALID;
                        case REVIEW_REQUIRED -> Status.REVIEW_REQUIRED;
                    }, assessment.reason()));
            if ("006".equals(table.indicator())) check(findings, "SEG155-R-005", "result-enumeration",
                    AppendixADAccountUpdaterOracle.RESULT_CODES.containsKey(table.value()), "Published noncontiguous VAU001-014/016; 015 is absent");
            if ("004".equals(table.indicator())) check(findings, "SEG155-R-004", "card-status-enumeration",
                    Set.of("A", "E", "Q", "C", "U").contains(table.value()),
                    "Card Status is A (New Acct#/Exp Date), E (New Exp Date), Q (Contact Cardholder), C (Closed), or U (Unknown Card)");
        }
        String encoded = encodedTables(input, tables, 3, "SEG155-R-002", findings);
        wire(input, value(fields, "85") + value(fields, "84") + encoded, "SEG155-R-002", findings);
        review(findings, "SEG155-R-001", "eligibility", "Run Appendix AD with matched opt-in Y/I, brand, credential mode and optimizer eligibility evidence");
        review(findings, "SEG155-R-003", "response-gates", "Standalone table shapes do not establish request indicator, brand and retry gates");
    }
    private static void ev(JsonNode input, JsonNode fields, List<Finding> findings) {
        header(input, fields, 3, 999, "008", "008", findings);
        List<IndicatorLengthValueSectionValidator.Triad> triads = tables(input, 2, "SEG156-R-003", findings);
        if (triads == null) return;
        check(findings, "SEG156-R-009", "section-cap",
                IndicatorLengthValueSectionValidator.validate(triads, 1, 12, Integer.MAX_VALUE).valid()
                        && triads.stream().mapToInt(t -> 5 + (t.value() == null ? 0 : t.value().length())).sum() <= 200,
                "Element 242 is required, N2/N3 framing, max 200 encoded characters");
        check(findings, "SEG156-R-003", "mandatory-indicator",
                triads.stream().anyMatch(t -> "001".equals(t.indicator()) && "001".equals(t.declaredLength()) && "Y".equals(t.value())),
                "Table 01 length 001 value Y is mandatory");
        for (var table : triads) {
            String value = table.value();
            String id = table.indicator() == null ? "" : table.indicator();
            String rule = switch (id) {
                case "001" -> "003";
                case "002", "003", "004", "005" -> "004";
                case "007" -> "005";
                case "012" -> "006";
                default -> "007";
            };
            boolean valid = value != null && switch (id) {
                case "001" -> "Y".equals(value);
                case "002", "003", "004", "005" -> value.matches("[0-9]{2}[0-5][0-9][0-5][0-9]");
                case "006", "008", "010", "011" -> value.matches("[0-9]{1,6}");
                case "009" -> value.matches("[0-9]{1,12}");
                case "007" -> value.matches("01[0-9]|02[0-3]|100|200");
                case "012" -> Set.of("001", "002", "003", "100", "101", "102", "103", "200").contains(value);
                default -> false;
            };
            check(findings, "SEG156-R-" + rule, "table-" + table.indicator(), valid, "Published EV table value and length");
        }
        String encoded = encodedTables(input, triads, 2, "SEG156-R-002", findings);
        wire(input, value(fields, "85") + value(fields, "84") + encoded, "SEG156-R-002", findings);
        String brand = input.path("context").path("brand").asText();
        JsonNode productCodes = input.path("context").path("segment102ProductCodes");
        if (brand.isBlank() || !productCodes.isArray() || productCodes.isEmpty()) review(findings, "SEG156-R-001", "applicability",
                "Visa brand and independently established EV product in Segment 102 required");
        else {
            boolean charging = false;
            for (JsonNode code : productCodes) {
                if (code.isTextual() && Set.of("308", "309", "310").contains(code.textValue())) charging = true;
            }
            check(findings, "SEG156-R-001", "applicability", "VISA".equals(brand) && charging,
                    "Visa and Appendix F EVC-1/2/3 code in independently supplied Segment 102 context");
        }
    }

    private static void header(JsonNode input, JsonNode fields, int width, int max, String typeRule, String lengthRule,
                               List<Finding> findings) {
        String id = input.path("segment").asText();
        pattern(fields, "85", id, "SEG" + id + "-R-" + typeRule, findings);
        String length = text(fields, "84");
        check(findings, "SEG" + id + "-R-" + lengthRule, "length-width-range",
                length != null && length.matches("[0-9]{" + width + "}") && Integer.parseInt(length) >= 1
                        && Integer.parseInt(length) <= max, "N" + width + " positive declared length, cap " + max);
    }

    private static List<IndicatorLengthValueSectionValidator.Triad> tables(JsonNode input, int width, String rule,
                                                                         List<Finding> findings) {
        JsonNode tables = input.path("tables");
        if (!tables.isArray()) {
            check(findings, rule, "tables", false, "Explicit ordered tables array required, even when empty");
            return null;
        }
        List<IndicatorLengthValueSectionValidator.Triad> triads = new ArrayList<>();
        for (JsonNode table : tables) {
            String id = text(table, "id");
            check(findings, rule, "table-id", id != null && id.matches("[0-9]{" + width + "}"), "Table ID must be N" + width);
            String normalized = width == 2 && id != null && id.matches("[0-9]{2}") ? "0" + id : id;
            triads.add(new IndicatorLengthValueSectionValidator.Triad(normalized, text(table, "length"), text(table, "value")));
        }
        return triads;
    }

    private static String encodedTables(JsonNode input, List<IndicatorLengthValueSectionValidator.Triad> tables,
                                        int width, String rule, List<Finding> findings) {
        StringBuilder encoded = new StringBuilder();
        for (var table : tables) {
            String id = table.indicator();
            if (width == 2 && id != null && id.length() == 3) id = id.substring(1);
            encoded.append(id == null ? "" : id).append(table.declaredLength() == null ? "" : table.declaredLength())
                    .append(table.value() == null ? "" : table.value());
        }
        if (input.has("encodedTableData")) check(findings, rule, "encoded-table-order",
                encoded.toString().equals(text(input, "encodedTableData")), "Encoded table content/order equals observation");
        return encoded.toString();
    }

    private static void wire(JsonNode input, String expected, String rule, List<Finding> findings) {
        if (!input.has("serializedSegment")) {
            review(findings, rule, "wire", "Serialized segment absent; field checks are not delimiter/encoded-length evidence");
            return;
        }
        String actual = text(input, "serializedSegment");
        check(findings, rule, "wire-order-delimiters", expected.equals(actual), "Encoded content equals prescribed field order/delimiters");
        String declared = text(input.path("elements"), "84");
        boolean numeric = declared != null && declared.matches("[0-9]{3,4}");
        check(findings, rule, "wire-declared-length", actual != null && actual.chars().allMatch(c -> c <= 127)
                && numeric && actual.length() == Integer.parseInt(declared), "Declared length equals complete ASCII segment length");
    }

    private static String join(JsonNode fields, String... ids) {
        StringBuilder result = new StringBuilder();
        for (String id : ids) result.append(value(fields, id)).append(FS);
        return result.toString();
    }

    private static void direction(JsonNode input, String expected, String rule, List<Finding> findings) {
        if (!input.has("direction")) review(findings, rule, "direction", "Message direction not supplied");
        else check(findings, rule, "direction", expected.equals(text(input, "direction")), "Expected " + expected);
    }

    private static void optional(JsonNode fields, String id, int min, int max, String rule, List<Finding> findings) {
        if (populated(fields, id)) length(fields, id, min, max, rule, findings);
    }

    private static boolean populated(JsonNode fields, String id) {
        return fields.has(id) && !(fields.path(id).isTextual() && fields.path(id).textValue().isEmpty());
    }

    private static void length(JsonNode fields, String id, int min, int max, String rule, List<Finding> findings) {
        String value = text(fields, id);
        check(findings, rule, "element-" + id, value != null && value.length() >= min && value.length() <= max
                && value.chars().allMatch(c -> c >= 32 && c <= 126), "Element " + id + " ASCII length " + min + "-" + max);
    }

    private static void pattern(JsonNode fields, String id, String pattern, String rule, List<Finding> findings) {
        String value = text(fields, id);
        check(findings, rule, "element-" + id, value != null && value.matches(pattern), "Element " + id + " must match " + pattern);
    }

    private static String text(JsonNode fields, String id) {
        JsonNode value = fields.get(id);
        return value != null && value.isTextual() ? value.textValue() : null;
    }

    private static String value(JsonNode fields, String id) {
        String value = text(fields, id);
        return value == null ? "" : value;
    }

    private static Boolean bool(JsonNode fields, String id) {
        JsonNode value = fields.get(id);
        return value != null && value.isBoolean() ? value.booleanValue() : null;
    }

    private static void check(List<Finding> findings, String rule, String scope, boolean valid, String reason) {
        findings.add(new Finding(rule, scope, valid ? Status.CHECKS_PASSED : Status.INVALID, reason));
    }

    private static void review(List<Finding> findings, String rule, String scope, String reason) {
        findings.add(new Finding(rule, scope, Status.REVIEW_REQUIRED, reason));
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) throw new IllegalArgumentException("Usage: Chapter12SegmentPayloadValidator <input.json> <report-name.json>");
        String name = args[1];
        if (!Path.of(name).getFileName().toString().equals(name)) throw new IllegalArgumentException("Report name must not contain a directory");
        Result result = new Chapter12SegmentPayloadValidator().validate(MAPPER.readTree(Path.of(args[0]).toFile()));
        Path output = Atl105Paths.testJson(name);
        Files.createDirectories(output.getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), result);
        System.out.println("Chapter 12 bounded validation: " + result.status() + "; report: " + output);
        if (result.status() != Status.CHECKS_PASSED) System.exit(1);
    }
}
