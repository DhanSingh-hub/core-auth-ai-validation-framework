package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Validates source-confirmed Proprietary Data Load Segment 118 rules.
 *
 * <p>Rules are anchored to {@code SEG118-R-001} through {@code SEG118-R-030} in
 * {@code specifications/ATL105/docs/specs/kb/segment-118/coverage/segment-118-rule-catalog.json}.
 * This covers the 13 core fields and the source-confirmed structures of all five conditional
 * Prompt-Code payloads (901, 902, 903, 904, 905), including repeat counts and record fields.
 * The Information Byte value catalog and unresolved receipt-text encoding semantics remain
 * outside deterministic pass/fail checks pending SME decisions.
 */
public final class Segment118PayloadValidator {
    public java.util.List<Chapter12SegmentPayloadValidator.Finding> validateActualHistory(JsonNode segment, JsonNode evidence) {
        var findings = new java.util.ArrayList<Chapter12SegmentPayloadValidator.Finding>();
        var pass = Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED;
        var review = Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED;
        var invalid = Chapter12SegmentPayloadValidator.Status.INVALID;
        JsonNode history = evidence.path("history").path("proprietaryLoad");
        JsonNode binding = history.path("currentRequest");
        JsonNode previous = history.path("previousHostResponse");
        var bindingStatus = pass;
        if (!history.isObject() || !binding.isObject() || !previous.isObject()) {
            bindingStatus = history.isMissingNode() ? review : !history.isObject() ? invalid
                    : binding.isMissingNode() || previous.isMissingNode() ? review : invalid;
        } else {
            for (String field : java.util.List.of("TerminalIdentifier", "SequenceNumber")) {
                JsonNode current = segment.get(field);
                JsonNode captured = binding.get(field);
                if (current == null || captured == null) {
                    if (bindingStatus != invalid) bindingStatus = review;
                } else if (!current.isTextual() || !captured.isTextual() || !current.equals(captured)
                        || !current.textValue().matches("SequenceNumber".equals(field) ? "[0-9]{6}" : "[A-Za-z0-9]{13}")) {
                    bindingStatus = invalid;
                }
            }
            JsonNode terminal = previous.get("TerminalIdentifier");
            JsonNode type = previous.get("SegmentType");
            if (terminal == null || type == null) {
                if (bindingStatus != invalid) bindingStatus = review;
            } else if (!terminal.isTextual() || !type.isTextual() || !"118".equals(type.textValue())
                    || segment.has("TerminalIdentifier") && !terminal.equals(segment.get("TerminalIdentifier"))) {
                bindingStatus = invalid;
            }
            var orderStatus = Chapter12ObservedSessionValidator.observedOrder(previous.path("receivedAt"), binding.path("requestedAt"));
            if (orderStatus == invalid || bindingStatus == pass) bindingStatus = orderStatus;
        }
        java.util.Map<String, String> rules = java.util.Map.of("CardTableLoadVersion", "SEG118-R-014",
                "CardTableType", "SEG118-R-015", "LoadControlKey", "SEG118-R-016", "BlockNumber", "SEG118-R-018");
        for (String field : java.util.List.of("CardTableLoadVersion", "CardTableType", "LoadControlKey", "BlockNumber")) {
            var status = bindingStatus;
            if (status == pass) {
                JsonNode actual = segment.get(field);
                JsonNode host = previous.get(field);
                if (actual == null || host == null) status = review;
                else {
                    String pattern = switch (field) {
                        case "CardTableLoadVersion" -> "[0-9]{35}";
                        case "CardTableType" -> "000[1-6]";
                        case "LoadControlKey" -> "[A-Za-z0-9]{1,60}";
                        default -> "[0-9]{3}";
                    };
                    if (!actual.isTextual() || !host.isTextual() || !host.textValue().matches(pattern)
                            || !actual.equals(host)) status = invalid;
                }
            }
            findings.add(new Chapter12SegmentPayloadValidator.Finding(rules.get(field), "actual-load-echo", status,
                    "Source13104/13111/13117/13157: " + field + " echoes observed preceding Host118 in bound subsequent request; "
                            + "no block increment, retry allowance, session authentication or load completion is inferred"));
        }
        return java.util.List.copyOf(findings);
    }

    private static final String SOURCE = "Segment118Payload";
    private static final String REQUEST_ROOT = "Proprietary Data Load Request";
    private static final String RESPONSE_ROOT = "Proprietary Data Load Response";
    private static final Pattern NUMERIC_4 = Pattern.compile("^[0-9]{4}$");
    private static final Pattern NUMERIC_6 = Pattern.compile("^[0-9]{6}$");
    private static final Pattern NUMERIC_1 = Pattern.compile("^[0-9]$");
    private static final Pattern ALPHANUMERIC_13 = Pattern.compile("^[A-Za-z0-9]{13}$");
    private static final Pattern NUMERIC_3 = Pattern.compile("^[0-9]{3}$");
    private static final Pattern NUMERIC_4_PENDING = Pattern.compile("^[0-9]{4}$");
    private static final Pattern NUMERIC_35 = Pattern.compile("^[0-9]{35}$");
    private static final Pattern ALPHANUMERIC_MAX_60 = Pattern.compile("^[A-Za-z0-9]{1,60}$");
    private static final Pattern NUMERIC_12 = Pattern.compile("^[0-9]{12}$");
    private static final Pattern NUMERIC_2 = Pattern.compile("^[0-9]{2}$");
    private static final Pattern NUMERIC_8 = Pattern.compile("^[0-9]{8}$");
    private static final Pattern NUMERIC_5 = Pattern.compile("^[0-9]{5}$");
    private static final Pattern ALPHANUMERIC_MAX_20 = Pattern.compile("^[A-Za-z0-9 ]{1,20}$");

    private static final Set<String> VALID_PROMPT_CODES = Set.of("901", "902", "903", "904", "905");
    private static final Set<String> REQUEST_PROMPT_CODES = Set.of("903", "905");
    private static final Set<String> RESPONSE_PROMPT_CODES = Set.of("901", "902", "904");
    private static final Set<String> VALID_PROMPT_CODE_PENDING = Set.of("0901", "0902", "0904", "0981");
    private static final Set<String> VALID_CARD_TABLE_TYPES = Set.of("0001", "0002", "0003", "0004", "0005", "0006");

    private final ObjectMapper objectMapper;

    public Segment118PayloadValidator() {
        this(new ObjectMapper());
    }

    Segment118PayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path aiJsonFile) {
        String documentId = aiJsonFile == null ? "segment-118-payload" : aiJsonFile.getFileName().toString();
        ValidationResult result = new ValidationResult(documentId);
        if (aiJsonFile == null) {
            result.addError(SOURCE, "AI JSON file is required");
            return result;
        }
        try {
            validatePayload(objectMapper.readTree(aiJsonFile.toFile()), result);
        } catch (IOException exception) {
            result.addError(SOURCE, "AI JSON is not readable: " + exception.getMessage());
        }
        return result;
    }

    public ValidationResult validatePayload(JsonNode payload) {
        ValidationResult result = new ValidationResult("segment-118-payload");
        validatePayload(payload, result);
        return result;
    }

    public ValidationResult validateActualPayload(JsonNode payload) {
        ValidationResult result = new ValidationResult("segment-118-actual-payload");
        validatePayload(payload, result, true);
        return result;
    }

    public java.util.List<Chapter12SegmentPayloadValidator.Finding> validateActualDeviceVersions(JsonNode segment, JsonNode evidence, boolean request) {
        if (!request) return java.util.List.of();
        var pass = Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED;
        var invalid = Chapter12SegmentPayloadValidator.Status.INVALID;
        var review = Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED;
        JsonNode state = evidence.path("history").path("proprietaryLoad").path("deviceState");
        var status = state.isMissingNode() ? review : state.isObject() ? pass : invalid;
        if (state.isObject()) for (String field : java.util.List.of("TerminalIdentifier", "SequenceNumber")) {
            JsonNode actual = segment.get(field), captured = state.get(field);
            if (actual == null || captured == null) status = Chapter12ObservedSessionValidator.combine(status, review);
            else if (!actual.isTextual() || !captured.isTextual() || !actual.equals(captured)
                    || !actual.textValue().matches("SequenceNumber".equals(field) ? "[0-9]{6}" : "[A-Za-z0-9]{13}")) status = invalid;
        }
        JsonNode components = state.path("components");
        if (!components.isObject()) status = Chapter12ObservedSessionValidator.combine(status, components.isMissingNode() ? review : invalid);
        StringBuilder expected = new StringBuilder();
        for (String component : DEVICE_VERSION_COMPONENTS) {
            JsonNode value = components.get(component);
            if (value == null) status = Chapter12ObservedSessionValidator.combine(status, review);
            else if (!value.isTextual() || !value.textValue().matches("[0-9]{5}")) status = invalid;
            else expected.append(value.textValue());
        }
        JsonNode actual = segment.get("DeviceCardTableVersion");
        if (actual == null) status = Chapter12ObservedSessionValidator.combine(status, review);
        else if (!actual.isTextual() || !actual.textValue().matches("[0-9]{35}")
                || expected.length() == 35 && !actual.textValue().contentEquals(expected)) status = invalid;
        var findings = new java.util.ArrayList<Chapter12SegmentPayloadValidator.Finding>();
        findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-013", "actual-device-version-components", status,
                "Source13088-13096: actual176 equals independently captured current Master/BIN/RULES/RESTRICTIONS/SAF/PROMPT/PRODUCT five-digit versions in source order, bound to actual118 device/sequence; not authenticated or latest-complete-table proof"));
        if (actual != null && actual.isTextual() && actual.textValue().matches("[0-9]{35}") && actual.textValue().startsWith("99999")) {
            JsonNode used = state.get("cardTableUsed");
            var sentinel = used == null ? review : !used.isBoolean() || used.booleanValue() ? invalid : pass;
            findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-013", "actual-no-card-table-sentinel",
                    Chapter12ObservedSessionValidator.combine(status, sentinel),
                    "Source24143-24144: actual176 prefix99999 indicates no card table used; captured location flag cannot contradict that indication"));
        }
        return java.util.List.copyOf(findings);
    }

    public static final java.util.List<String> DEVICE_VERSION_COMPONENTS = java.util.List.of("CardTableMasterID", "BIN", "RULES",
            "RESTRICTIONS", "SAF", "PROMPT", "PRODUCT");

    public java.util.List<Chapter12SegmentPayloadValidator.Finding> validateActualWire(JsonNode segment, boolean request) {
        var findings = new java.util.ArrayList<Chapter12SegmentPayloadValidator.Finding>();
        String scope = "actual-legacy-wire";
        JsonNode observedRaw = segment.get("SerializedSegment");
        var capStatus = observedRaw == null ? Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED
                : !observedRaw.isTextual() || observedRaw.textValue().isEmpty() || observedRaw.textValue().length() > 3800
                || observedRaw.textValue().chars().anyMatch(c -> c > 127) ? Chapter12SegmentPayloadValidator.Status.INVALID
                : Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED;
        findings.add(new Chapter12SegmentPayloadValidator.Finding(request ? "SEG118-R-002" : "SEG118-R-003",
                "actual-wire-cap-3800", capStatus,
                "Source12990-12991: independently measured nonempty ASCII observed118 request/response has maximum3800 bytes; cap pass does not prove logical grammar or conditional body cap"));
        if (!segment.has("SerializedSegment")) {
            findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-005", scope,
                    Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED, "Actual118 raw wire required; field checks do not prove separator grammar"));
            return java.util.List.copyOf(findings);
        }
        if (!segment.path("SerializedSegment").isTextual()) {
            findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-005", scope,
                    Chapter12SegmentPayloadValidator.Status.INVALID, "Observed118 raw wire must be text, including when named evidence is missing"));
            return java.util.List.copyOf(findings);
        }
        String prompt = text(segment, "PromptCode");
        String body = switch (prompt == null ? "" : prompt) {
            case "902" -> "CardTableData";
            case "903" -> "SiteConfigurationData";
            case "905" -> "FuelVolumeData";
            default -> null;
        };
        boolean discount = "904".equals(prompt) && !request;
        boolean receipt = "901".equals(prompt) && !request;
        if (body == null && !discount && !receipt) {
            findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-005", scope,
                    Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED, "Conditional raw encoding not supplied by this bounded path;901 receipt-length conflict remains explicit"));
            return java.util.List.copyOf(findings);
        }
        StringBuilder expected = new StringBuilder();
        var fields = new java.util.ArrayList<>(java.util.List.of("SegmentType", "SegmentLength", "SequenceNumber", "InformationByte",
                "TerminalIdentifier", "PromptCode", "PromptCodePending", "DeviceCardTableVersion", "CardTableLoadVersion",
                "CardTableType", "LoadControlKey", "HostDiscountTimestamp", "BlockNumber"));
        if (body != null) fields.add(body);
        boolean missingField = false;
        for (String field : fields) {
            JsonNode value = segment.get(field);
            if (value == null) {
                missingField = true;
                continue;
            }
            if (!value.isTextual()) {
                findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-005", scope,
                        Chapter12SegmentPayloadValidator.Status.INVALID, "Observed118 fields must be text for wire concatenation"));
                return java.util.List.copyOf(findings);
            }
            if (value.textValue().indexOf('\u001c') >= 0) {
                findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-005", scope,
                        Chapter12SegmentPayloadValidator.Status.INVALID, "Observed118 field contains an unprescribed embeddedFS"));
                return java.util.List.copyOf(findings);
            }
            expected.append(value.textValue());
            if (request) expected.append('\u001c');
        }
        if (missingField) {
            findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-005", scope,
                    Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED, "Missing actual field/conditional omission evidence; no blank field inferred"));
            return java.util.List.copyOf(findings);
        }
        if (discount) {
            String encoded = observedDiscountBody(segment, findings);
            if (encoded == null) return java.util.List.copyOf(findings);
            expected.append(encoded);
        }
        if (receipt) {
            String encoded = observedReceiptBody(segment, findings);
            if (encoded == null) return java.util.List.copyOf(findings);
            expected.append(encoded);
        }
        JsonNode raw = segment.path("SerializedSegment");
        boolean equal = raw.isTextual() && expected.toString().equals(raw.textValue());
        findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-005", scope,
                equal ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED : Chapter12SegmentPayloadValidator.Status.INVALID,
                "12994-12997: request retains all13 header separators and field14 trailingFS; response concatenates withoutFS"));
        if (raw.isTextual()) {
            String length = text(segment, "SegmentLength");
            findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-007", "actual-legacy-wire-length",
                    raw.textValue().chars().anyMatch(c -> c > 127) ? Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED
                            : length != null && length.matches("[0-9]{4}") && Integer.parseInt(length) == raw.textValue().length()
                            ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED : Chapter12SegmentPayloadValidator.Status.INVALID,
                    "Element84: actual ASCII N4 length counts every source field and separator; non-ASCII encoding not inferred"));
        }
        findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-005", "actual-legacy-payload-boundary",
                Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED, "Opaque proprietary body equality does not certify raw card-table/fuel-volume semantics or load-session provenance"));
        return java.util.List.copyOf(findings);
    }

    private static String observedReceiptBody(JsonNode segment, java.util.List<Chapter12SegmentPayloadValidator.Finding> findings) {
        var pass = Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED;
        var review = Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED;
        var invalid = Chapter12SegmentPayloadValidator.Status.INVALID;
        String scope = "actual-receipt-body";
        StringBuilder body = new StringBuilder();
        var status = pass;
        boolean headerComplete = true;
        for (String field : java.util.List.of("StartDate", "StartTime", "EndDate", "EndTime", "NumberOfReceiptTextLines")) {
            JsonNode value = segment.get(field);
            int width = field.endsWith("Date") ? 8 : field.endsWith("Time") ? 4 : 2;
            if (value == null) { status = Chapter12ObservedSessionValidator.combine(status, review); headerComplete = false; }
            else if (!value.isTextual() || !value.textValue().matches("[0-9]{" + width + "}")) { status = invalid; headerComplete = false; }
            else body.append(value.textValue());
        }
        JsonNode count = segment.get("NumberOfReceiptTextLines"), lines = segment.get("ReceiptTextLines");
        if (count != null && count.isTextual() && count.textValue().matches("[0-9]{2}")) {
            int number = Integer.parseInt(count.textValue());
            if (number < 1 || number > 10 || lines != null && lines.isArray() && lines.size() != number) status = invalid;
        }
        if (lines == null) status = Chapter12ObservedSessionValidator.combine(status, review);
        else if (!lines.isArray()) status = invalid;
        boolean measured = lines != null && lines.isArray();
        int repeatedBytes = 0;
        if (measured) for (JsonNode line : lines) {
            if (!line.isObject()) { status = invalid; measured = false; continue; }
            JsonNode length = line.get("ReceiptTextDataLength"), text = line.get("ReceiptTextData");
            if (length == null || text == null) {
                status = Chapter12ObservedSessionValidator.combine(status, review); measured = false;
            }
            if (length != null && (!length.isTextual() || !length.textValue().matches("[0-9]{2}"))) status = invalid;
            if (text != null && (!text.isTextual() || !text.textValue().matches("[A-Za-z0-9 ]{1,40}"))) {
                status = invalid; measured = false;
            }
            if (length == null || text == null || !length.isTextual() || !length.textValue().matches("[0-9]{2}")
                    || !text.isTextual()) continue;
            if (Integer.parseInt(length.textValue()) != text.textValue().length()) status = invalid;
            if (text.textValue().length() > 20) status = Chapter12ObservedSessionValidator.combine(status, review);
            repeatedBytes += length.textValue().length() + text.textValue().length();
            body.append(length.textValue()).append(text.textValue());
        }
        findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-019", scope, status,
                "Source13187-13249: observed fixed date/time/N2 header and measured repeated N2/text withoutFS;21-40-byte text retains explicit20-versus40 source conflict"));
        findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-019", "actual-receipt-body-cap",
                measured ? repeatedBytes <= 220 ? pass : invalid : review,
                "Source13224-13226: repeated receipt section caps220 observed bytes, excluding preceding26-byte header"));
        return status == invalid || !measured || !headerComplete ? null : body.toString();
    }

    private static String observedDiscountBody(JsonNode segment, java.util.List<Chapter12SegmentPayloadValidator.Finding> findings) {
        var pass = Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED;
        var invalid = Chapter12SegmentPayloadValidator.Status.INVALID;
        var review = Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED;
        StringBuilder body = new StringBuilder();
        for (String field : java.util.List.of("NewHostDiscountTimestamp", "NumberOfDiscounts")) {
            JsonNode value = segment.get(field);
            int width = "NewHostDiscountTimestamp".equals(field) ? 12 : 2;
            if (value == null) {
                findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-022", "actual-discount-body", review,
                        "Source13383-13410: distinct received field14 timestamp/count required; core timestamp cannot supply missing new timestamp"));
                continue;
            }
            if (!value.isTextual() || !value.textValue().matches("[0-9]{" + width + "}")) {
                findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-022", "actual-discount-body", invalid,
                        "Observed904 header must use source fixed width: " + field));
                continue;
            }
            body.append(value.textValue());
        }
        JsonNode records = segment.get("Discounts");
        if (records == null) {
            findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-022", "actual-discount-body", review, "Observed904 records required"));
            return null;
        }
        if (!records.isArray() || segment.path("NumberOfDiscounts").isTextual()
                && segment.path("NumberOfDiscounts").textValue().matches("[0-9]{2}")
                && records.size() != Integer.parseInt(segment.path("NumberOfDiscounts").textValue())) {
            findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-022", "actual-discount-body", invalid, "Observed record count must equal N2"));
            return null;
        }
        for (JsonNode record : records) {
            if (!record.isObject()) {
                findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-022", "actual-discount-body", invalid, "Observed904 record must be an object"));
                continue;
            }
            for (String field : DISCOUNT_FIELDS) {
                JsonNode value = record.get(field);
                if (value == null) {
                    findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-022", "actual-discount-body", review, "Missing captured904 record field: " + field));
                    continue;
                }
                int width = discountFieldWidth(field);
                if (!value.isTextual() || value.textValue().length() != width
                        || !value.textValue().chars().allMatch(c -> c >= 32 && c <= 126)
                        || !"DiscountProgramDescription".equals(field) && !value.textValue().matches("[0-9]+")) {
                    findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-022", "actual-discount-body", invalid,
                            "Actual904 fixed-width record must be explicitly observed without manufactured padding: " + field));
                    continue;
                }
                body.append(value.textValue());
            }
        }
        if (findings.stream().anyMatch(f -> "actual-discount-body".equals(f.scope()) && f.status() != pass)) return null;
        findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-022", "actual-discount-body-cap",
                body.length() - 14 <= 3600 ? pass : invalid, "Source13405-13410: measured81-byte repeated Host Discount Data records cap3600; timestamp/count are preceding fields"));
        findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG118-R-022", "actual-discount-body", pass,
                "Source13383-13491: source-ordered received timestamp/N2 and fixed-width16-27 records; no inter-fieldFS or implicit values"));
        return body.toString();
    }

    public static final java.util.List<String> DISCOUNT_FIELDS = java.util.List.of("StartDate", "StartTime", "EndDate", "EndTime",
            "BuyPassCardType", "CardBinRangeBeginning", "CardBinRangeEnding", "ProductCode", "ProductDiscountAmount",
            "DiscountProductCode", "DiscountQuantityLimit", "DiscountProgramDescription");

    public static int discountFieldWidth(String field) {
        return switch (field) {
            case "StartDate", "EndDate" -> 8;
            case "StartTime", "EndTime", "BuyPassCardType" -> 4;
            case "CardBinRangeBeginning", "CardBinRangeEnding" -> 12;
            case "ProductCode", "DiscountProductCode", "DiscountQuantityLimit" -> 3;
            case "ProductDiscountAmount" -> 5;
            case "DiscountProgramDescription" -> 15;
            default -> throw new IllegalArgumentException("Not a source904 record field: " + field);
        };
    }

    private void validatePayload(JsonNode payload, ValidationResult result) {
        validatePayload(payload, result, false);
    }

    private void validatePayload(JsonNode payload, ValidationResult result, boolean actual) {
        if (payload == null || !payload.isObject()) {
            result.addError(SOURCE, "AI JSON root must be an object");
            return;
        }
        boolean isRequest = payload.path(REQUEST_ROOT).isObject();
        boolean isResponse = payload.path(RESPONSE_ROOT).isObject();
        if (isRequest == isResponse) {
            result.addError(SOURCE, "Exactly one Proprietary Data Load Request or Response object is required");
            return;
        }
        JsonNode message = payload.path(isRequest ? REQUEST_ROOT : RESPONSE_ROOT);
        if (isRequest) {
            validateRequest(message, result, actual);
        } else {
            validateResponse(message, result, actual);
        }
    }

    private static void validateRequest(JsonNode request, ValidationResult result, boolean actual) {
        if (request.has("Standard Message Data Segment")) {
            result.addError(SOURCE, "Proprietary Data Load Request must not contain Segment 100 (SEG118-R-024)");
        }
        checkRequiredPresent(request, "MessageType", "SEG118-R-024", result);
        checkRequiredValue(request, "NumSegments", "1", "SEG118-R-024", result);
        validateEnvelopeSegment(request, true, result, actual);
    }

    private static void validateResponse(JsonNode response, ValidationResult result, boolean actual) {
        checkRequiredPresent(response, "ResponseCode", "SEG118-R-025", result);
        String responseCode = text(response, "ResponseCode");
        if (responseCode != null && !Set.of("H", "O", "T", "U", "X", "Y").contains(responseCode)) {
            result.addError(SOURCE, "Proprietary Data Load Response.ResponseCode is not in the documented PDL response catalog (SEG118-R-026, SEG118-R-030)");
        }
        checkRequiredPresent(response, "DownloadIndicator", "SEG118-R-025", result);
        checkRequiredPresent(response, "InitiationDate", "SEG118-R-025", result);
        checkRequiredPresent(response, "InitiationTime", "SEG118-R-025", result);
        checkRequiredPresent(response, "SequenceNumber", "SEG118-R-025", result);
        validateEnvelopeSegment(response, false, result, actual);
    }

    private static void validateEnvelopeSegment(JsonNode message, boolean request, ValidationResult result, boolean actual) {
        JsonNode segment = message.path("Proprietary Data Load Segment");
        if (!segment.isObject()) {
            result.addError(SOURCE, "Proprietary Data Load Segment object is required (" + (request ? "SEG118-R-024" : "SEG118-R-025") + ")");
            return;
        }
        validateSegment(segment, request, result, actual);
    }

    private static void validateSegment(JsonNode segment, boolean request, ValidationResult result, boolean actual) {
        checkRequiredValue(segment, "SegmentType", "118", "SEG118-R-006", result);

        String segmentLength = text(segment, "SegmentLength");
        if (!matches(segmentLength, NUMERIC_4)) {
            result.addError(SOURCE, "Proprietary Data Load Segment.SegmentLength must be exactly 4 digits (SEG118-R-007)");
        } else if (Integer.parseInt(segmentLength) > 3800) {
            result.addError(SOURCE, "Proprietary Data Load Segment.SegmentLength exceeds 3800 (SEG118-R-002)");
        }

        checkRequiredPattern(segment, "SequenceNumber", NUMERIC_6, "exactly 6 numeric characters", "SEG118-R-008", result);
        checkRequiredPattern(segment, "InformationByte", NUMERIC_1, "one numeric character", "SEG118-R-009", result);
        checkRequiredPattern(segment, "TerminalIdentifier", ALPHANUMERIC_13, "exactly 13 alphanumeric characters", "SEG118-R-010", result);

        String promptCode = text(segment, "PromptCode");
        if (promptCode == null || !VALID_PROMPT_CODES.contains(promptCode)) {
            result.addError(SOURCE, "Proprietary Data Load Segment.PromptCode must be one of 901, 902, 903, 904, 905 (SEG118-R-011)");
        } else if (request && !REQUEST_PROMPT_CODES.contains(promptCode)) {
            result.addError(SOURCE, "Request PromptCode must be 903 (Site Configuration) or 905 (Fuel Volume) (SEG118-R-021, SEG118-R-023)");
        } else if (!request && !RESPONSE_PROMPT_CODES.contains(promptCode)) {
            result.addError(SOURCE, "Response PromptCode must be 901 (Custom Receipt Text), 902 (Dynamic Card Table), or 904 (Host Discount) (SEG118-R-019, SEG118-R-020, SEG118-R-022)");
        }

        String promptCodePending = text(segment, "PromptCodePending");
        if (promptCodePending == null || !matches(promptCodePending, NUMERIC_4_PENDING)
            || !VALID_PROMPT_CODE_PENDING.contains(promptCodePending)) {
            result.addError(SOURCE, "Proprietary Data Load Segment.PromptCodePending must be one of 0901, 0902, 0904, 0981 (SEG118-R-012)");
        }

        checkRequiredPattern(segment, "DeviceCardTableVersion", NUMERIC_35, "exactly 35 numeric digits", "SEG118-R-013", result);
        checkRequiredPattern(segment, "CardTableLoadVersion", NUMERIC_35, "exactly 35 numeric digits", "SEG118-R-014", result);

        String cardTableType = text(segment, "CardTableType");
        if (cardTableType == null || !VALID_CARD_TABLE_TYPES.contains(cardTableType)) {
            result.addError(SOURCE, "Proprietary Data Load Segment.CardTableType must be one of 0001-0006 (SEG118-R-015)");
        }

        checkRequiredPattern(segment, "LoadControlKey", ALPHANUMERIC_MAX_60, "alphanumeric with maximum length 60", "SEG118-R-016", result);
        checkRequiredPattern(segment, "HostDiscountTimestamp", NUMERIC_12, "exactly 12 numeric characters (CCYYMMDDHHMM)", "SEG118-R-017", result);
        checkOptionalPattern(segment, "BlockNumber", NUMERIC_3, "exactly 3 numeric characters", "SEG118-R-018", result);

        checkPromptCodePayload(segment, promptCode, result, actual);
    }

    private static void checkPromptCodePayload(JsonNode segment, String promptCode, ValidationResult result, boolean actual) {
        if (promptCode == null) {
            return;
        }
        switch (promptCode) {
            case "901" -> {
                if (!actual) validateReceiptText(segment, result);
                else {
                    var findings = new java.util.ArrayList<Chapter12SegmentPayloadValidator.Finding>();
                    observedReceiptBody(segment, findings);
                    for (var finding : findings) {
                        if (finding.status() == Chapter12SegmentPayloadValidator.Status.INVALID)
                            result.addError(SOURCE, finding.reason() + " (SEG118-R-019)");
                        else if (finding.status() == Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED)
                            result.addWarning("REVIEW_REQUIRED: " + finding.reason() + " (SEG118-R-019)");
                    }
                }
            }
            case "902" -> {
                if (actual && !segment.has("CardTableData"))
                    result.addWarning("REVIEW_REQUIRED: Missing observed CardTableData (SEG118-R-020)");
                else checkRequiredPresent(segment, "CardTableData", "SEG118-R-020", result);
            }
            case "903" -> validateSiteConfiguration(segment, result);
            case "904" -> {
                if (!actual) validateHostDiscounts(segment, result);
                else {
                    var findings = new java.util.ArrayList<Chapter12SegmentPayloadValidator.Finding>();
                    observedDiscountBody(segment, findings);
                    for (var finding : findings) {
                        if (finding.status() == Chapter12SegmentPayloadValidator.Status.INVALID)
                            result.addError(SOURCE, finding.reason() + " (SEG118-R-022)");
                        else if (finding.status() == Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED)
                            result.addWarning("REVIEW_REQUIRED: " + finding.reason() + " (SEG118-R-022)");
                    }
                }
            }
            case "905" -> {
                if (actual && !segment.has("FuelVolumeData"))
                    result.addWarning("REVIEW_REQUIRED: Missing observed FuelVolumeData (SEG118-R-023)");
                else checkRequiredMaxLength(segment, "FuelVolumeData", 3600, "SEG118-R-023", result);
            }
            default -> { }
        }
        if ("902".equals(promptCode)) checkMaxLength(segment, "CardTableData", 3600, "SEG118-R-020", result);
    }

    private static void validateReceiptText(JsonNode segment, ValidationResult result) {
        String countText = text(segment, "NumberOfReceiptTextLines");
        if (countText == null || !matches(countText, NUMERIC_2)) {
            result.addError(SOURCE, "NumberOfReceiptTextLines must be two digits (SEG118-R-019)");
            return;
        }
        int count = Integer.parseInt(countText);
        if (count < 1 || count > 10) {
            result.addError(SOURCE, "NumberOfReceiptTextLines must be 01-10 (SEG118-R-019)");
            return;
        }
        checkRequiredPattern(segment, "StartDate", NUMERIC_8, "8 numeric digits (CCYYMMDD)", "SEG118-R-019", result);
        checkRequiredPattern(segment, "StartTime", NUMERIC_4, "4 numeric digits (HHMM)", "SEG118-R-019", result);
        checkRequiredPattern(segment, "EndDate", NUMERIC_8, "8 numeric digits (CCYYMMDD)", "SEG118-R-019", result);
        checkRequiredPattern(segment, "EndTime", NUMERIC_4, "4 numeric digits (HHMM)", "SEG118-R-019", result);
        JsonNode lines = segment.path("ReceiptTextLines");
        if (!lines.isArray() || lines.size() != count) {
            result.addError(SOURCE, "ReceiptTextLines count must equal NumberOfReceiptTextLines (SEG118-R-019)");
            return;
        }
        int bytes = 26;
        for (JsonNode line : lines) {
            String lengthText = text(line, "ReceiptTextDataLength");
            String value = text(line, "ReceiptTextData");
            if (lengthText == null || !matches(lengthText, NUMERIC_2) || value == null || !matches(value, ALPHANUMERIC_MAX_20)) {
                result.addError(SOURCE, "Each receipt text line needs a two-digit length and 1-20 alphanumeric characters (SEG118-R-019)");
                continue;
            }
            int declaredLength = Integer.parseInt(lengthText);
            if (declaredLength < 1 || declaredLength > 40) {
                result.addError(SOURCE, "ReceiptTextDataLength must be 01-40 (SEG118-R-019)");
            }
            if (declaredLength != value.length()) {
                result.addWarning("SEG118-R-019: ReceiptTextDataLength differs from actual text length; retain review under SEG118-SME-002");
            }
            bytes += 2 + value.length();
        }
        if (bytes > 220) result.addError(SOURCE, "Custom Receipt Text payload exceeds 220 bytes (SEG118-R-019)");
    }

    private static void validateSiteConfiguration(JsonNode segment, ValidationResult result) {
        String blockText = text(segment, "BlockNumber");
        String data = text(segment, "SiteConfigurationData");
        if ("000".equals(blockText)) {
            if (data != null && !data.isBlank()) result.addError(SOURCE, "BlockNumber 000 is the final Site Configuration message and carries no SiteConfigurationData (SEG118-R-027)");
            return;
        }
        checkRequiredMaxLength(segment, "SiteConfigurationData", 3600, "SEG118-R-021", result);
    }

    private static void validateHostDiscounts(JsonNode segment, ValidationResult result) {
        String countText = text(segment, "NumberOfDiscounts");
        if (countText == null || !matches(countText, NUMERIC_2)) {
            result.addError(SOURCE, "NumberOfDiscounts must be two digits (SEG118-R-022)");
            return;
        }
        int count = Integer.parseInt(countText);
        JsonNode discounts = segment.path("Discounts");
        if (!discounts.isArray() || discounts.size() != count) {
            result.addError(SOURCE, "Discounts count must equal NumberOfDiscounts (SEG118-R-022)");
            return;
        }
        for (JsonNode discount : discounts) {
            requirePattern(discount, "StartDate", NUMERIC_8, "8 numeric digits", result);
            requirePattern(discount, "StartTime", NUMERIC_4, "4 numeric digits", result);
            requirePattern(discount, "EndDate", NUMERIC_8, "8 numeric digits", result);
            requirePattern(discount, "EndTime", NUMERIC_4, "4 numeric digits", result);
            requirePattern(discount, "BuyPassCardType", NUMERIC_4, "4 numeric digits", result);
            requirePattern(discount, "CardBinRangeBeginning", NUMERIC_12, "12 numeric digits", result);
            requirePattern(discount, "CardBinRangeEnding", NUMERIC_12, "12 numeric digits", result);
            requirePattern(discount, "ProductCode", NUMERIC_3, "3 numeric digits", result);
            requirePattern(discount, "ProductDiscountAmount", NUMERIC_5, "5 numeric digits", result);
            requirePattern(discount, "DiscountProductCode", NUMERIC_3, "3 numeric digits", result);
            requirePattern(discount, "DiscountQuantityLimit", NUMERIC_3, "3 numeric digits", result);
            requireExactLength(discount, "DiscountProgramDescription", 15, result);
        }
        if (count * 81 > 3600) result.addError(SOURCE, "Host Discount Data exceeds 3,600 bytes (SEG118-R-022)");
    }

    private static void requirePattern(JsonNode node, String field, Pattern pattern, String description, ValidationResult result) {
        if (!matches(text(node, field), pattern)) result.addError(SOURCE, field + " must be " + description + " (SEG118-R-022)");
    }

    private static void requireExactLength(JsonNode node, String field, int length, ValidationResult result) {
        String value = text(node, field);
        if (value == null || value.length() != length) result.addError(SOURCE, field + " must be exactly " + length + " characters (SEG118-R-022)");
    }

    private static void checkMaxLength(JsonNode node, String field, int maxLength, String ruleId, ValidationResult result) {
        String value = text(node, field);
        if (value != null && value.length() > maxLength) result.addError(SOURCE, field + " exceeds maximum length " + maxLength + " (" + ruleId + ")");
    }

    private static void checkRequiredValue(JsonNode node, String field, String expected, String ruleId,
                                           ValidationResult result) {
        if (!expected.equals(text(node, field))) {
            result.addError(SOURCE, "Proprietary Data Load Segment." + field + " must be " + expected + " (" + ruleId + ")");
        }
    }

    private static void checkRequiredPresent(JsonNode node, String field, String ruleId, ValidationResult result) {
        String value = text(node, field);
        if (value == null || value.isBlank()) {
            result.addError(SOURCE, field + " is required (" + ruleId + ")");
        }
    }

    private static void checkRequiredMaxLength(JsonNode node, String field, int maxLength, String ruleId,
                                               ValidationResult result) {
        String value = text(node, field);
        if (value == null || value.isBlank()) {
            result.addError(SOURCE, field + " is required (" + ruleId + ")");
        } else if (value.length() > maxLength) {
            result.addError(SOURCE, field + " exceeds maximum length " + maxLength + " (" + ruleId + ")");
        }
    }

    private static void checkRequiredPattern(JsonNode node, String field, Pattern pattern, String description,
                                             String ruleId, ValidationResult result) {
        if (!matches(text(node, field), pattern)) {
            result.addError(SOURCE, "Proprietary Data Load Segment." + field + " must be " + description + " (" + ruleId + ")");
        }
    }

    private static void checkOptionalPattern(JsonNode node, String field, Pattern pattern, String description,
                                             String ruleId, ValidationResult result) {
        String value = text(node, field);
        if (value != null && !value.isBlank() && !matches(value, pattern)) {
            result.addError(SOURCE, "Proprietary Data Load Segment." + field + " must be " + description + " (" + ruleId + ")");
        }
    }

    private static String text(JsonNode node, String field) {
        return node.has(field) && !node.path(field).isNull() ? node.path(field).asText() : null;
    }

    private static boolean matches(String value, Pattern pattern) {
        return value != null && pattern.matcher(value).matches();
    }
}
