package com.coreauth.validator.canonical;

import com.coreauth.validator.paths.Atl105Paths;
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
    public static final Set<String> SEGMENTS = Set.of("115", "119", "123", "130", "131", "132", "134", "135", "136",
            "139", "140", "141", "142", "143", "145", "146", "148", "149", "150", "151", "152", "153", "155", "156");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final char FS = '\u001c';

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
            case "115" -> printData(observation, fields, findings);
            case "119" -> findings.addAll(Segment119ObservationValidator.validate(observation));
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
        wire(input, join(fields, "85", "84", "195", "196", "197", "202", "203", "204", "237", "238"),
                "SEG123-R-004", findings);
        review(findings, "SEG123-R-005", "context", "Tokenization requiredness needs transaction context; field shape is not eligibility");
        review(findings, "SEG123-R-011", "3ds-routing", "Run Appendix Y oracle with independent DSRP/AAV and Segment 111 Table 36 context");
    }

    private static void emv(JsonNode input, JsonNode fields, List<Finding> findings) {
        boolean request = "130".equals(input.path("segment").asText());
        String prefix = request ? "SEG130-R-" : "SEG131-R-";
        header(input, fields, 4, request ? 3043 : 3834, request ? "002" : "003", request ? "003" : "004", findings);
        direction(input, request ? "REQUEST" : "RESPONSE", prefix + (request ? "014" : "002"), findings);
        if (request) {
            if (populated(fields, "187")) pattern(fields, "187", "[0-9]{25}", "SEG130-R-005", findings);
            review(findings, "SEG130-R-005", "requiredness-conflict",
                    "Chapter 12 makes checksum optional; Chapter 13 Element 187 says required in EMV request");
            if (populated(fields, "188")) pattern(fields, "188", "[0-9]{3}", "SEG130-R-006", findings);
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
        List<IndicatorLengthValueSectionValidator.Triad> triads = tables(input, 3, prefix + (request ? "011" : "009"), findings);
        if (triads != null) {
            var result = IndicatorLengthValueSectionValidator.validate(triads, 1, 985, request ? 1999 : 2800);
            check(findings, prefix + (request ? "011" : "009"), "additional-information-framing",
                    result.valid(), result.violations().toString() + (request ? "; final FS counts in 2000-byte request cap" : ""));
            if (!request && triads.isEmpty()) review(findings, "SEG131-R-009", "empty-additional-information",
                    "Response table marks the section required; omission requires source/context reconciliation");
        }
        review(findings, prefix + (request ? "013" : "006"), "wire",
                "Chip bytes require an actual binary serializer; JSON hex characters are not encoded chip byte count");
        review(findings, prefix + (request ? "009" : "007"), "cross-message",
                request ? "EMV chip/Segment 100 context agreement not proven by a standalone observation"
                        : "Checksum echo requires the matched Segment 130 request");
        review(findings, request ? "SEG130-R-004" : "SEG131-R-005", "source-conflict",
                "Chapter 11 and Chapters 12/13 publish different maxima; this check uses Chapter 12, not conflict approval");
    }

    private static void capk(JsonNode input, JsonNode fields, List<Finding> findings) {
        header(input, fields, 3, 77, "002", "003", findings);
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
        review(findings, prefix + ("141".equals(id) ? "002" : headerRule), "wire",
                "This observation checks field values, not all separators, padding or batch-close encoding");
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
        for (JsonNode product : products) {
            pattern(product, "productCode", "[0-9]{3}", "SEG143-R-005", findings);
            body.append(value(product, "productCode"));
            JsonNode taxes = product.path("taxes");
            if (!taxes.isArray() || taxes.isEmpty() || taxes.size() > 3) {
                check(findings, "SEG143-R-005", "tax-count", false, "1-3 flag entries; no-tax product uses a single N");
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
                        pattern(tax, "type", "GST|HST|PST|QST", "SEG143-R-007", findings);
                    } else review(findings, "SEG143-R-007", "country-context", "GST/HST/PST/QST closed enumeration is Canadian only");
                    body.append(value(tax, "type")).append(value(tax, "amount"));
                }
                body.append(i + 1 == taxes.size() ? FS : '\\');
            }
        }
        check(findings, "SEG143-R-005", "tax-section-cap", body.length() <= 360, "Repeated tax data cap 360");
        wire(input, value(fields, "85") + FS + value(fields, "84") + FS + value(fields, "62") + body,
                "SEG143-R-008", findings);
        review(findings, "SEG143-R-002", "tax-value-policy", "Format-only validation; tax applicability/rates are not independently approved");
    }

    private static void enhancedFleet(JsonNode input, JsonNode fields, List<Finding> findings) {
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
        for (var table : tables) {
            check(findings, prefix + rule, "fleet-table-id", table.indicator() != null && ids.contains(table.indicator()),
                    "Published enhanced fleet table ID");
            if (request && Set.of("VOYAGER_EMV", "VISA_FLEET_2", "COMDATA", "MASTERCARD_ENHANCED_FLEET_EMV").contains(authorizer)) {
                check(findings, "SEG145-R-003", "restricted-table", "004".equals(table.indicator()), "This authorizer permits Prompt Table 004 only");
            }
            if (!request && "COMDATA".equals(authorizer)) check(findings, "SEG146-R-003", "comdata-table",
                    !"002".equals(table.indicator()), "Nonfuel limits Table 002 is absent from Comdata responses");
        }
        if (request && input.path("context").path("segmentsPresent").isArray()) {
            boolean fleet = false;
            for (JsonNode id : input.path("context").path("segmentsPresent")) if ("101".equals(id.asText())) fleet = true;
            check(findings, "SEG145-R-002", "companion-exclusion", !fleet, "Do not combine 101 and 145");
        } else if (request) review(findings, "SEG145-R-002", "companion-context", "Segment presence list required");
        review(findings, prefix + rule, "table-semantics", "Authorizer-specific token/edit-mask/product sublayouts remain unassessed; framing is not full table validation");
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
        if (data != null && data.indexOf('=') == 2) {
            check(findings, "SEG148-R-004", "fuel-group", Set.of("00", "01", "06", "07", "08", "10", "11", "12", "13")
                    .contains(data.substring(0, 2)), "Published two-digit fuel group");
        }
        review(findings, "SEG148-R-001", "routing", "WEX Financial Transaction response applicability needs independent routing context");
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
        if (request) review(findings, "SEG151-R-002", "source-conflict", "2309 stated max vs 3334 range; host/device origin also conflicts");
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
