package com.coreauth.validator.validation;

import com.coreauth.validator.canonical.AppendixDStateCodes;
import com.coreauth.validator.canonical.AppendixILogicalDataValidator;
import com.coreauth.validator.canonical.AppendixKDataLayoutValidator;
import com.coreauth.validator.canonical.AppendixLCurrencyCodes;
import com.coreauth.validator.canonical.AppendixREmvChipDataOracle;
import com.coreauth.validator.coverage.Atl105ElementInventory;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Finding;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Result;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/** Chapter 13 scalar domains and source-local dependencies; never full-message certification. */
public final class Chapter13DataElementValidator {
    private final Atl105ElementValueValidator profiles;
    private final JsonNode inventory;
    private final Chapter13ProcessingHistoryValidator processing;
    private final Chapter13ContextValidator contextual;
    private final Chapter13CompositeValidator composite;
    private final Map<String, JsonNode> domains = new HashMap<>();
    private final Map<String, Pattern> patterns = new HashMap<>();
    private static final Set<String> MMDDYY = Set.of("21", "36", "45", "92");
    private static final Set<String> SPECIAL_TOTALS = Set.of("111111", "222222", "333333", "444444", "555555", "999999");
    private static final Set<String> LABELS = Set.of("CC", "TE", "DS", "AO", "DB", "FL", "CS", "PR",
            "CK", "EF", "EC", "SV1", "SV2", "SV3", "SV4", "ECA", "EWIC", "EK");

    public Chapter13DataElementValidator(Path pack) throws IOException {
        profiles = new Atl105ElementValueValidator(pack);
        inventory = new Atl105ElementInventory().extract(Files.readString(pack.resolve("docs").resolve("specs").resolve("extracted_text.txt")));
        processing = new Chapter13ProcessingHistoryValidator(inventory);
        contextual = new Chapter13ContextValidator(inventory);
        composite = new Chapter13CompositeValidator(inventory);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode document = mapper.readTree(pack.resolve("docs").resolve("specs").resolve("kb").resolve("elements")
                .resolve("value-domains.json").toFile());
        if (!"ATL105".equals(document.path("specification").asText())
                || !"2026-3".equals(document.path("specificationVersion").asText()) || !document.path("domains").isArray()) {
            throw new IOException("Missing or unsupported Chapter 13 value-domain catalog");
        }
        for (JsonNode domain : document.path("domains")) {
            String id = domain.path("element").asText();
            JsonNode definitions = inventory.path("elements").path(id).path("definitions");
            if (definitions.size() != 1 || domains.putIfAbsent(id, domain) != null) {
                throw new IOException("Unknown, duplicated or ambiguous domain element " + id);
            }
            String source = normalize(definitions.get(0).path("sourceText").asText());
            String evidence = normalize(domain.path("sourceEvidence").asText());
            if (evidence.isBlank() || !source.contains(evidence)) throw new IOException("Domain lacks local source evidence: " + id);
            String kind = domain.path("kind").asText();
            if ("range".equals(kind)) {
                String min = domain.path("min").asText();
                String max = domain.path("max").asText();
                if (!min.matches("[0-9]+") || !max.matches("[0-9]+") || new BigInteger(min).compareTo(new BigInteger(max)) > 0) {
                    throw new IOException("Invalid range for Element " + id);
                }
                var range = Pattern.compile("(?<![0-9])([0-9]+)\\s*(?:[-\u2013\u2014]|to a maximum of)\\s*([0-9]+)(?![0-9])").matcher(source);
                boolean matched = false;
                while (range.find()) {
                    if (new BigInteger(range.group(1)).equals(new BigInteger(min))
                            && new BigInteger(range.group(2)).equals(new BigInteger(max))) matched = true;
                }
                if (!matched) throw new IOException("Range endpoints lack source-local evidence: " + id);
            } else if ("codes".equals(kind)) {
                if (!domain.path("values").isArray() || domain.path("values").isEmpty()) throw new IOException("Empty code domain: " + id);
                Set<String> unique = new java.util.HashSet<>();
                for (JsonNode value : domain.path("values")) {
                    if (!value.isTextual() || !unique.add(value.textValue()) || !source.contains(value.textValue())) {
                        throw new IOException("Unverified or repeated source code for Element " + id);
                    }
                }
            } else if ("pattern".equals(kind)) {
                patterns.put(id, Pattern.compile(domain.path("pattern").asText()));
            } else throw new IOException("Unknown domain predicate: " + id);
        }
    }

    public Result validate(JsonNode observation) {
        if (observation == null || !observation.isObject() || !"2026-3".equals(observation.path("specificationVersion").asText())
                || !observation.path("elements").isObject() || !observation.path("messageFamily").isTextual()
                || observation.path("messageFamily").asText().isBlank()
                || !observation.path("segment").isTextual() || observation.path("segment").asText().isBlank()) {
            return new Result(Status.INVALID, List.of(new Finding("", Status.INVALID, "CH13-INPUT",
                    "Explicit 2026-3 version, family, segment and text-valued numbered elements are required")));
        }
        List<Finding> findings = new ArrayList<>(profiles.validate(observation).findings());
        for (String field : List.of("qualifiers", "records", "history")) {
            if (observation.has(field) && !observation.path(field).isObject()) {
                finding(findings, "", "INPUT-" + field.toUpperCase(java.util.Locale.ROOT), Status.INVALID, field + " must be an object");
            }
        }
        if (observation.has("representation") && (!observation.path("representation").isTextual()
                || !Set.of("WIRE", "LOGICAL").contains(observation.path("representation").textValue()))) {
            finding(findings, "", "INPUT-REPRESENTATION", Status.INVALID, "Representation must be WIRE or LOGICAL when supplied");
        }
        if (observation.has("completeness") && (!observation.path("completeness").isTextual()
                || !Set.of("PARTIAL", "COMPLETE").contains(observation.path("completeness").textValue()))) {
            finding(findings, "", "INPUT-COMPLETENESS", Status.INVALID, "Completeness must be PARTIAL or COMPLETE when supplied");
        }
        JsonNode elements = observation.path("elements");
        JsonNode context = observation.path("qualifiers");
        var fields = elements.fields();
        while (fields.hasNext()) {
            var field = fields.next();
            String id = field.getKey();
            JsonNode definition = inventory.path("elements").path(id).path("definitions");
            if (!field.getValue().isTextual()) {
                finding(findings, id, "TEXT", Status.INVALID, "Numbered element representation must be text, not coerced JSON");
                continue;
            }
            String value = field.getValue().textValue();
            if (value.isEmpty()) {
                finding(findings, id, "OMISSION", Status.REVIEW_REQUIRED, "Omission needs applicable segment/context evidence");
                continue;
            }
            if (definition.isMissingNode() || definition.isEmpty()) {
                finding(findings, id, "IDENTITY", Status.REVIEW_REQUIRED, "No Chapter 13 identity; source review required");
                continue;
            }
            if (!"190".equals(id) && value.chars().anyMatch(character -> character > 127)) {
                finding(findings, id, "ENCODING", Status.REVIEW_REQUIRED, "Non-ASCII value requires explicit source transport/byte-encoding evidence");
                continue;
            }
            domain(id, value, findings);
            representation(id, value, observation, findings);
            special(id, value, observation, findings);
        }
        lengthPair(elements, "52", "51", observation, findings);
        lengthPair(elements, "104", "103", observation, findings);
        lengthPair(elements, "112", "113", observation, findings);
        lengthPair(elements, "117", "118", observation, findings);
        lengthPair(elements, "156", "157", observation, findings);
        lengthPair(elements, "193", "194", observation, findings);
        lengthPair(elements, "169", "170", observation, findings);
        tax(elements, "223", "224", "225", context, observation, findings);
        tax(elements, "226", "227", "228", context, observation, findings);
        tax(elements, "229", "230", "231", context, observation, findings);
        dependencies(elements, context, observation, findings);
        records(elements, observation, findings);
        findings.addAll(processing.validate(observation));
        findings.addAll(contextual.validate(observation));
        findings.addAll(composite.validate(observation));
        finding(findings, "", "SCOPE", Status.REVIEW_REQUIRED,
                "Executed domains/representation/dependencies are not complete Chapter 13 processing, eligibility, history or wire certification");
        Status status = findings.stream().anyMatch(f -> f.status() == Status.INVALID) ? Status.INVALID : Status.REVIEW_REQUIRED;
        return new Result(status, List.copyOf(findings));
    }

    private void domain(String id, String value, List<Finding> findings) {
        JsonNode domain = domains.get(id);
        if (domain == null) return;
        boolean valid;
        switch (domain.path("kind").asText()) {
            case "range" -> valid = value.matches("[0-9]+") && new BigInteger(value).compareTo(new BigInteger(domain.path("min").asText())) >= 0
                    && new BigInteger(value).compareTo(new BigInteger(domain.path("max").asText())) <= 0;
            case "codes" -> {
                valid = false;
                for (JsonNode code : domain.path("values")) if (value.equals(code.textValue())) valid = true;
            }
            case "pattern" -> valid = patterns.get(id).matcher(value).matches();
            default -> throw new IllegalStateException("Unchecked predicate configuration");
        }
        finding(findings, id, "DOMAIN", valid ? Status.CHECKS_PASSED : Status.INVALID,
                valid ? "Source-local scalar domain passed" : "Value violates source-local scalar domain");
    }

    private void representation(String id, String value, JsonNode observation, List<Finding> findings) {
        if (!"WIRE".equals(observation.path("representation").asText())) return;
        JsonNode definitions = inventory.path("elements").path(id).path("definitions");
        if (definitions.size() != 1) return;
        JsonNode constraint = definitions.get(0).path("constraints");
        if (!"EXTRACTED".equals(constraint.path("extractionStatus").asText()) || Set.of("65", "100", "138", "153", "190", "226").contains(id)) return;
        if (constraint.path("representation").asText().contains("up to")) return;
        if ("FIXED".equals(constraint.path("lengthMode").asText())) {
            finding(findings, id, "WIRE-WIDTH", value.length() == constraint.path("maxLength").asInt() ? Status.CHECKS_PASSED : Status.INVALID,
                    "Explicit wire representation must preserve source fixed width; logical padding is not inferred");
        }
    }

    private void special(String id, String value, JsonNode observation, List<Finding> findings) {
        JsonNode context = observation.path("qualifiers");
        JsonNode elements = observation.path("elements");
        if (MMDDYY.contains(id)) calendar(id, value, "MMDDYY", findings);
        if (Set.of("125", "130").contains(id)) calendar(id, value, "MMDDYYYY", findings);
        if (Set.of("165", "179").contains(id)) {
            if ("165".equals(id) && Set.of("00000000", "99999999").contains(value)) {
                String role = text(context, "dateRole");
                if (role == null) finding(findings, id, "DATE-ROLE", Status.REVIEW_REQUIRED, "Start/end role required for special date");
                else check(findings, id, "DATE-ROLE", "START".equals(role) && "00000000".equals(value)
                        || "END".equals(role) && "99999999".equals(value), "Special date must match source start/end role");
            } else {
                calendar(id, value.length() >= 8 ? value.substring(0, 8) : value, "YYYYMMDD", findings);
                if ("179".equals(id)) check(findings, id, "TIMESTAMP", value.matches("[0-9]{12}") && validTime(value.substring(8)),
                        "Host Discount Timestamp must encode CCYYMMDDHHMM");
            }
        }
        if ("49".equals(id)) {
            check(findings, id, "DATE-TIME", value.matches("[0-9]{10}") && Atl105CalendarDate.isValidMmddyy(value.substring(0, 6))
                    && validTime(value.substring(6)), "Local Date and Time must encode MMDDYYHHMM");
            if (value.startsWith("022900")) finding(findings, id, "CENTURY", Status.REVIEW_REQUIRED, "Leap day year 00 needs century evidence");
        }
        if ("119".equals(id)) check(findings, id, "MONTH-DAY", value.matches("[0-9]{4}")
                && validMonthDay(value), "Settlement Date must encode a possible MMDD, without inventing a year");
        if ("146".equals(id)) check(findings, id, "EXPIRY", value.matches("(0[1-9]|1[0-2])[0-9]{2}"),
                "Expiration month must be 01-12; expired cards are not rejected merely by date");
        if (Set.of("22", "23").contains(id)) check(findings, id, "SOURCE-TIME", value.matches("[0-9]{4}")
                && integer(value.substring(0, 2)) >= 1 && integer(value.substring(0, 2)) <= 24
                && integer(value.substring(2)) >= 1 && integer(value.substring(2)) <= 60,
                "Chapter 13 explicitly lists hours 01-24 and minutes 01-60; not normalized to another clock convention");
        if ("166".equals(id)) check(findings, id, "TIME", validTime(value), "Start/End Time must be valid 0000-2359 HHMM");
        if ("105".equals(id)) totalsDate(value, observation, findings);
        if ("20".equals(id)) check(findings, id, "CURRENCY", AppendixLCurrencyCodes.isValid(value), "Currency must be source-listed in Appendix L");
        if ("124".equals(id)) check(findings, id, "STATE", AppendixDStateCodes.isValidAlphabeticalCode(value), "State must be source-listed in Appendix D");
        if ("13".equals(id)) check(findings, id, "LABEL", LABELS.contains(value.stripTrailing())
                && value.length() <= 4 && (!"WIRE".equals(observation.path("representation").asText()) || value.length() == 4),
                "Card Label must be source-listed, left-aligned and space-filled on the wire");
        if (Set.of("41", "58", "99").contains(id) && value.matches("[0-9]+")) {
            String network = text(context, "network");
            if (network == null) finding(findings, id, "NETWORK-WIDTH", Status.REVIEW_REQUIRED, "Network evidence required for 7/8-digit amount cap");
            else check(findings, id, "NETWORK-WIDTH", value.length() <= ("AMEX".equals(network) ? 8 : 7),
                    "Eight-digit amount width is permitted only for American Express");
            if (!"99".equals(id)) check(findings, id, "NONZERO", new BigInteger(value).signum() > 0, "Source amount range starts at one");
            if ("99".equals(id) && "AMEX".equals(network) && value.length() == 8) {
                finding(findings, id, "SOURCE-CONFLICT", Status.REVIEW_REQUIRED, "Element 99 eight-byte Amex note conflicts with seven-digit valid-value maximum");
            } else if ("99".equals(id)) check(findings, id, "NONZERO", new BigInteger(value).signum() > 0, "Source tax amount starts at one");
        }
        if (Set.of("81", "107").contains(id)) check(findings, id, "DECIMAL-PREFIX", Atl105DecimalPrefix.isValid(value),
                "Quantity/Unit Price must include 0-3 decimal prefix and every fractional digit");
        if (Set.of("88", "90").contains(id)) check(findings, id, "POSTAL", value.matches("[0-9]{5}-[0-9]{4}"),
                "Shipping postal representation is nnnnn-nnnn; allocation is not asserted");
        if ("162".equals(id)) check(findings, id, "NONZERO-USER", !"0".equals(value), "User ID excludes literal zero");
        if ("98".equals(id)) check(findings, id, "STORE", value.matches("[0-9]{1,16}") && new BigInteger(value).signum() > 0
                || value.matches("\\*{1,16}"), "Store Number must be nonzero numeric or entirely asterisk-masked");
        if ("169".equals(id)) check(findings, id, "RECEIPT-LINE-LENGTH", value.matches("[0-9]{2}")
                && integer(value) >= 1 && integer(value) <= 40, "Receipt Text Data Length source range is 01-40");
        if ("170".equals(id) && value.length() > 20 && value.length() <= 40) finding(findings, id, "SOURCE-CONFLICT",
                Status.REVIEW_REQUIRED, "Element 169 permits 40 while Element 170 caps line data at 20; source conflict retained");
        if (Set.of("183", "184").contains(id)) check(findings, id, "BIN-PADDING", value.matches("[0-9]+ *") && value.length() == 12,
                "BIN endpoint must be digit-valued, left-justified and space-filled to 12");
        if (Set.of("202", "237").contains(id)) check(findings, id, "B64", value.matches("[A-Za-z0-9+/=]+")
                && (value.length() == 28 || "202".equals(id) && value.length() == 56), "Cryptogram representation requires source width and Base64 alphabet");
        if ("203".equals(id)) check(findings, id, "SAFEKEY", value.length() == 58 && value.startsWith("SK")
                && b64OrSpaces(value.substring(2, 30)) && b64OrSpaces(value.substring(30)),
                "SafeKey must encode SK plus two Base64-or-space-filled 28-byte blocks");
        if ("51".equals(id) || "52".equals(id)) {
            String transport = text(context, "transport");
            if (transport == null) finding(findings, id, "TRANSPORT", Status.REVIEW_REQUIRED, "IP/DIAL transport needed for Mail Text 750/150 byte limit");
            else if (!Set.of("IP", "DIAL").contains(transport)) finding(findings, id, "TRANSPORT", Status.INVALID, "Unsupported explicit mail transport");
            else if ("51".equals(id)) check(findings, id, "TRANSPORT", value.length() <= ("IP".equals(transport) ? 750 : 150), "Mail Text exceeds transport-specific cap");
            else check(findings, id, "TRANSPORT", value.matches("[0-9]{3}") && integer(value) >= 1
                    && integer(value) <= ("IP".equals(transport) ? 750 : 150), "Mail Text length is outside transport-specific range");
        }
        if ("65".equals(id)) check(findings, id, "PASSWORD-PADDING",
                "WIRE".equals(observation.path("representation").asText()) ? value.matches(" *[0-9]+") && value.length() == 6
                        : value.matches("[0-9]{1,6}"), "Chapter 13 Password is numeric and right-aligned with spaces on wire; merchant authentication is external");
        if (Set.of("87", "106").contains(id)) {
            Set<String> assigned = "87".equals(id) ? Set.of("F", "S", "N", "X", "H", "O")
                    : Set.of("C", "G", "H", "I", "K", "L", "M", "P", "Q", "U", "W", "Z", "O");
            finding(findings, id, "ASSIGNMENT", assigned.contains(value) ? Status.CHECKS_PASSED : Status.REVIEW_REQUIRED,
                    assigned.contains(value) ? "Source-listed assigned code" : "Private-use/reserved code needs operational assignment evidence, not blanket rejection");
        }
        if ("204".equals(id)) {
            check(findings, id, "SAFEKEY-RESULT", value.matches("[0-9A-DU]"), "SafeKey result must be a source-listed code");
            if (Set.of("0", "5", "6", "B", "C", "D").contains(value)) finding(findings, id, "RESERVED", Status.REVIEW_REQUIRED, "Reserved SafeKey code is not operationally approved");
        }
        if ("243".equals(id)) finding(findings, id, "AVAILABILITY", Status.REVIEW_REQUIRED,
                "Source earliest-enable date is not proof of production availability or MasterCard Enhanced Fleet eligibility");
        if ("189".equals(id) || "190".equals(id)) {
            if ("189".equals(id) && elements.has("190")) return;
            var assessments = new AppendixREmvChipDataOracle().assess(text(elements, "189"), text(elements, "190"));
            for (var assessment : assessments) finding(findings, id, "EMV-" + assessment.assertionScope(),
                    map(assessment.status().name()), assessment.reason());
        }
    }

    private static void totalsDate(String value, JsonNode observation, List<Finding> findings) {
        String direction = text(observation.path("qualifiers"), "direction");
        if (direction == null) {
            finding(findings, "105", "DATE-DIRECTION", Status.REVIEW_REQUIRED, "Request MMDDYY / response YYMMDD direction required");
        } else if ("REQUEST".equals(direction)) {
            if (!SPECIAL_TOTALS.contains(value)) calendar("105", value, "MMDDYY", findings);
            else finding(findings, "105", "SPECIAL-DATE", Status.CHECKS_PASSED, "Source-listed request special code");
        } else if ("RESPONSE".equals(direction)) {
            if (!Set.of("111111", "999999").contains(value)) calendar("105", value, "YYMMDD", findings);
            else finding(findings, "105", "SPECIAL-DATE", Status.CHECKS_PASSED, "Source-listed response special code");
        } else finding(findings, "105", "DATE-DIRECTION", Status.INVALID, "Direction must be REQUEST or RESPONSE");
    }

    private static void dependencies(JsonNode e, JsonNode context, JsonNode observation, List<Finding> findings) {
        if (populated(e, "123") && !populated(e, "124")) missing(findings, "124", observation, "Driver's License requires State Code");
        if (e.has("56") && e.has("42") && e.has("38") && digits(e, "56", "42", "38")) {
            check(findings, "56", "NET-TOTAL", number(e, "42").subtract(number(e, "38")).equals(number(e, "56")),
                    "Net Amount must equal Grand Total minus Fee Amount");
        }
        if (e.has("183") && e.has("184") && text(e, "183") != null && text(e, "184") != null
                && text(e, "183").matches("[0-9]+ *") && text(e, "184").matches("[0-9]+ *")) {
            check(findings, "184", "BIN-ORDER", new BigInteger(text(e, "183").stripTrailing())
                    .compareTo(new BigInteger(text(e, "184").stripTrailing())) <= 0, "Beginning BIN range must not exceed ending range");
        }
        if (populated(e, "111") && populated(e, "113")) {
            var result = new AppendixILogicalDataValidator().validate(text(e, "111"), text(e, "113"), text(context, "network"));
            for (var item : result.findings()) finding(findings, "113", item.ruleId(), map(item.status().name()), item.reason());
        }
        if (populated(e, "116") && populated(e, "118")) {
            var result = new AppendixKDataLayoutValidator().validate(text(e, "116"), text(e, "118"), text(context, "network"));
            finding(findings, "118", "APPK-" + result.assertionScope(), map(result.status().name()), result.reason());
        }
        JsonNode original = context.path("originalElements");
        String flow = text(context, "flow");
        if (flow != null && Set.of("COMPLETION", "PURCHASE_REVERSAL", "TIMEOUT_REVERSAL", "AUTHORIZATION_REVERSAL").contains(flow)) {
            if (!populated(e, "86")) missing(findings, "86", observation, "Follow-on flow requires original Sequence Number");
            else if (!populated(original, "86")) finding(findings, "86", "LIFECYCLE", Status.REVIEW_REQUIRED, "Original sequence evidence missing");
            else check(findings, "86", "LIFECYCLE", text(e, "86").equals(text(original, "86")), "Follow-on Sequence Number must equal original");
            if ("AUTHORIZATION_REVERSAL".equals(flow)) {
                if (!populated(e, "78")) missing(findings, "78", observation, "Authorization reversal requires original Prompt Code");
                else if (!populated(original, "78")) finding(findings, "78", "LIFECYCLE", Status.REVIEW_REQUIRED, "Original Prompt Code evidence missing");
                else check(findings, "78", "LIFECYCLE", text(e, "78").equals(text(original, "78")), "Authorization reversal must preserve Prompt Code");
            }
            if (flow.endsWith("REVERSAL") && !populated(e, "5")) missing(findings, "5", observation, "Reversal requires Approval Number");
        }
        if (context.path("emvTransaction").isBoolean() && context.path("emvTransaction").booleanValue()
                && populated(original, "187")) {
            if (!populated(e, "187")) missing(findings, "187", observation, "Subsequent EMV request must reuse received CA key checksum");
            else check(findings, "187", "CHECKSUM-REUSE", text(e, "187").equals(text(original, "187")),
                    "Received CA key checksum must persist in subsequent EMV requests; authenticity is not inferred");
        }
        String rawMicr = text(context, "rawMicr");
        if (rawMicr != null) {
            if (!populated(e, "122")) missing(findings, "122", observation, "Observed check MICR requires Element 122");
            else check(findings, "122", "MICR-TRUNCATION", text(e, "122").equals(rawMicr.substring(0, Math.min(50, rawMicr.length()))),
                    "MICR field must retain the first 50 source bytes in order");
            if (rawMicr.length() > 50 && "ECA_TELECHECK".equals(text(context, "checkService"))) {
                if (!populated(e, "137")) missing(findings, "137", observation, "Long ECA/TeleCheck MICR requires Extended MICR");
                else check(findings, "137", "MICR-EXTENSION", rawMicr.equals(text(e, "137")), "Extended MICR preserves full observed check data");
            }
        }
        if (populated(e, "86") && "132".equals(observation.path("segment").asText())
                && "MULTITHREADED_DIAL".equals(text(context, "protocol"))) {
            check(findings, "86", "CAPK-RANGE", text(e, "86").matches("1[0-9]{5}"), "Multithreaded CAPK sequence is 100000-199999");
        }
        if (populated(e, "98") && text(e, "98").matches("\\*+")) {
            finding(findings, "98", "MASKED", Status.REVIEW_REQUIRED, "Masked Store Number must not be printed/displayed; action evidence is required");
        }
    }

    private static void tax(JsonNode e, String flag, String type, String amount, JsonNode context, JsonNode observation, List<Finding> findings) {
        if (!populated(e, flag)) return;
        String value = text(e, flag);
        if ("N".equals(value)) {
            check(findings, flag, "TAX-OMISSION", !e.has(type) && !e.has(amount), "N requires tax type and amount omitted, not blank fields");
        } else if (Set.of("I", "E").contains(value)) {
            if (!populated(e, type)) missing(findings, type, observation, "Applicable tax requires type");
            if (!populated(e, amount)) missing(findings, amount, observation, "Applicable tax requires amount");
            if (populated(e, type)) {
                if ("CANADA".equals(text(context, "country"))) check(findings, type, "COUNTRY-TAX",
                        Set.of("GST", "HST", "PST", "QST").contains(text(e, type)), "Canada source tax codes include QST");
                else finding(findings, type, "COUNTRY-TAX", Status.REVIEW_REQUIRED, "Country-specific tax domain requires source-supported country evidence");
            }
        }
    }

    private static void lengthPair(JsonNode e, String length, String data, JsonNode observation, List<Finding> findings) {
        if (!e.has(length) && !e.has(data)) return;
        if ("193".equals(length) && !"BLOCK".equals(text(observation.path("qualifiers"), "capkDataScope"))) {
            finding(findings, length, "BLOCK-SCOPE", Status.REVIEW_REQUIRED, "CAPK length is block-scoped, not assembled-file length; explicit BLOCK observation required");
            return;
        }
        if (!e.has(length) || !e.has(data)) {
            missing(findings, !e.has(length) ? length : data, observation, "Length/data companion is missing");
            return;
        }
        String count = text(e, length);
        String value = text(e, data);
        if (count == null || !count.matches("[0-9]{1,3}") || value == null) return;
        if (value.chars().anyMatch(c -> c > 127)) {
            finding(findings, length, "LENGTH-EQUALITY", Status.REVIEW_REQUIRED, "Encoded byte count cannot be inferred from non-ASCII text");
            return;
        }
        check(findings, length, "LENGTH-EQUALITY", Integer.parseInt(count) == value.length(), "Length must equal supplied ASCII data bytes");
    }

    private static void records(JsonNode e, JsonNode observation, List<Finding> findings) {
        JsonNode records = observation.path("records");
        for (String[] pair : new String[][]{{"61", "printLines"}, {"168", "receiptLines"}, {"59", "cardTypes"}, {"62", "products"}}) {
            JsonNode values = records.get(pair[1]);
            if (values == null) continue;
            if (!values.isArray()) {
                finding(findings, pair[0], "RECORD-COUNT", Status.INVALID, "Observed repeated records must be an array");
                continue;
            }
            String count = text(e, pair[0]);
            if (count == null) missing(findings, pair[0], observation, "Observed records require source count");
            else check(findings, pair[0], "RECORD-COUNT", count.matches("[0-9]{1,2}") && integer(count) == values.size(),
                    "Declared count must equal actual repeated " + pair[1] + ", not a different record family");
            if ("printLines".equals(pair[1]) || "receiptLines".equals(pair[1])) {
                for (JsonNode value : values) {
                    int maximum = "printLines".equals(pair[1]) ? 31 : 20;
                    check(findings, "printLines".equals(pair[1]) ? "101" : "170", "RECORD-TEXT",
                            value.isTextual() && value.textValue().length() <= maximum, "Repeated line must be text within source element cap");
                }
            }
        }
        JsonNode keys = records.get("monerisKeys");
        if (populated(e, "211") || keys != null) {
            String indicators = text(e, "211");
            if (indicators == null) missing(findings, "211", observation, "Repeated keys require corresponding indicators");
            else if (indicators.length() != 3) finding(findings, "211", "KEY-COUNT", Status.INVALID, "Three positional key indicators required");
            else if (keys == null) finding(findings, "212", "KEY-COUNT", Status.REVIEW_REQUIRED, "Key iterations must be observed to assess indicator correlation");
            else {
                check(findings, "212", "KEY-COUNT", keys.isArray() && indicators.chars().filter(c -> c == 'Y').count() == keys.size(),
                        "One key iteration is included for each Y; no undocumented absent-flag value is invented");
                if (keys.isArray()) {
                    for (JsonNode key : keys) check(findings, "212", "KEY-WIDTH", key.isTextual() && key.textValue().matches("[A-Za-z0-9]{16}"),
                            "Each key iteration must contain 16 alphanumeric characters; key authenticity is external");
                }
            }
        }
    }

    private static void calendar(String id, String value, String format, List<Finding> findings) {
        boolean valid = false;
        try {
            if ("MMDDYY".equals(format)) valid = Atl105CalendarDate.isValidMmddyy(value);
            else if (value.matches("[0-9]{8}") && Set.of("MMDDYYYY", "YYYYMMDD").contains(format)) {
                int year = "YYYYMMDD".equals(format) ? integer(value.substring(0, 4)) : integer(value.substring(4));
                int month = "YYYYMMDD".equals(format) ? integer(value.substring(4, 6)) : integer(value.substring(0, 2));
                int day = "YYYYMMDD".equals(format) ? integer(value.substring(6)) : integer(value.substring(2, 4));
                valid = year >= 1 && LocalDate.of(year, month, day) != null;
            } else if ("YYMMDD".equals(format) && value.matches("[0-9]{6}")) {
                valid = LocalDate.of(2000 + integer(value.substring(0, 2)), integer(value.substring(2, 4)), integer(value.substring(4))) != null;
            }
        } catch (DateTimeException exception) {
            valid = false;
        }
        check(findings, id, "CALENDAR", valid, "Calendar value must match source " + format);
        if (valid && ("MMDDYY".equals(format) && Atl105CalendarDate.needsCenturyForLeapDay(value)
                || "YYMMDD".equals(format) && "000229".equals(value))) {
            finding(findings, id, "CENTURY", Status.REVIEW_REQUIRED, "Year-00 leap day needs century evidence");
        }
    }

    private static boolean validTime(String value) {
        return value != null && value.matches("[0-9]{4}") && integer(value.substring(0, 2)) <= 23 && integer(value.substring(2)) <= 59;
    }

    private static boolean validMonthDay(String value) {
        try {
            return LocalDate.of(2000, integer(value.substring(0, 2)), integer(value.substring(2))) != null;
        } catch (DateTimeException exception) {
            return false;
        }
    }

    private static boolean b64OrSpaces(String value) { return value.matches("[A-Za-z0-9+/=]{28}| {28}"); }
    private static int integer(String value) { return Integer.parseInt(value); }
    private static String text(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return value != null && value.isTextual() ? value.textValue() : null;
    }
    private static boolean populated(JsonNode e, String id) { String value = text(e, id); return value != null && !value.isEmpty(); }
    private static boolean digits(JsonNode e, String... ids) {
        for (String id : ids) { String value = text(e, id); if (value == null || !value.matches("[0-9]+")) return false; }
        return true;
    }
    private static BigInteger number(JsonNode e, String id) { return new BigInteger(text(e, id)); }
    private static void check(List<Finding> findings, String id, String rule, boolean valid, String reason) {
        finding(findings, id, rule, valid ? Status.CHECKS_PASSED : Status.INVALID, reason);
    }
    private static void missing(List<Finding> findings, String id, JsonNode observation, String reason) {
        finding(findings, id, "COMPANION", "PARTIAL".equals(observation.path("completeness").asText()) ? Status.REVIEW_REQUIRED : Status.INVALID, reason);
    }
    private static void finding(List<Finding> findings, String id, String rule, Status status, String reason) {
        findings.add(new Finding(id, status, "CH13-E" + id + "-" + rule, reason));
    }
    private static Status map(String status) {
        return "PASS".equals(status) ? Status.CHECKS_PASSED : "FAIL".equals(status) ? Status.INVALID : Status.REVIEW_REQUIRED;
    }
    private static String normalize(String value) { return value.replaceAll("\\s+", " ").strip(); }
}
