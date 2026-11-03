package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/** Validates the structured representation of Segment 100 serialization rules. */
public final class Segment100SerializationValidator {
    private static final List<String> NUMBERED_ORDER = List.of("85", "84", "44", "102", "78", "2", "12", "33",
            "79", "41", "58", "99", "17", "86", "5", "49", "121");

    public List<Chapter12SegmentPayloadValidator.Finding> validateActual(JsonNode observation,
            com.coreauth.validator.validation.Chapter13DataElementValidator elementOracle) {
        var findings = new ArrayList<Chapter12SegmentPayloadValidator.Finding>();
        var fields = observation.path("elements");
        var oracleObservation = observation.deepCopy();
        ((com.fasterxml.jackson.databind.node.ObjectNode) oracleObservation).put("completeness", "PARTIAL");
        for (var finding : elementOracle.validate(oracleObservation).findings())
            findings.add(new Chapter12SegmentPayloadValidator.Finding(finding.ruleId(), "standard-element",
                    Chapter12SegmentPayloadValidator.Status.valueOf(finding.status().name()), finding.reason()));
        String family = observation.path("messageFamily").asText();
        boolean request = ACTUAL_REQUESTS.contains(family);
        addActual(findings, "SEG100-R-015", "standard-family",
                request ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED : Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED,
                "12.1 layout is a request Standard segment; response envelope fields are not a Segment100 request layout");
        if (!request) return List.copyOf(findings);
        boolean financial = java.util.Set.of("Financial Transaction Request", "EMV Financial Transaction Request")
                .contains(observation.path("messageFamily").asText());
        if (financial) findings.addAll(Chapter11MessageLayoutValidator.standardRequestPlacement(
                observation.get("dataSection"), observation.get("fieldNumber")));
        for (String id : List.of("85", "84", "44", "102", "78", "2", "86", "121")) {
            if (!fields.has(id)) addActual(findings, "SEG100-R-015", "standard-required-" + id,
                    Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED, "Actual required field is missing; metadata cannot replace it");
            else addActual(findings, "SEG100-R-015", "standard-required-" + id,
                    fields.path(id).isTextual() && !fields.path(id).textValue().isEmpty()
                            ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED : Chapter12SegmentPayloadValidator.Status.INVALID,
                    "Source12.1 required field must be observed nonempty text");
        }
        if (fields.has("85")) addActual(findings, "SEG100-R-015", "standard-type",
                fields.path("85").isTextual() && "100".equals(fields.path("85").textValue())
                        ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED : Chapter12SegmentPayloadValidator.Status.INVALID, "Observed type100");
        if (financial && fields.has("78")) addActual(findings, "SEG100-R-015", "standard-financial-prompt-width",
                fields.path("78").isTextual() && fields.path("78").textValue().matches("[A-Za-z0-9][0-9]{3}")
                        ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED : Chapter12SegmentPayloadValidator.Status.INVALID,
                "Source12.1 Field5 and Element78 lines21056-21079: financial PromptCode is one TransactionType plus N3 CardType, not the special-transaction three-character alternative");
        String declared = fields.path("84").isTextual() ? fields.path("84").textValue() : null;
        if (fields.has("84")) addActual(findings, "SEG100-R-016", "standard-length",
                declared != null && SEGMENT_LENGTH.matcher(declared).matches() && Integer.parseInt(declared) >= SEGMENT_100_MIN_LENGTH
                        && Integer.parseInt(declared) <= SEGMENT_100_MAX_LENGTH ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED
                        : Chapter12SegmentPayloadValidator.Status.INVALID, "Element84 Standard length is N3 001-218, not a calculation sentinel");
        if (financial) {
            if (fields.has("86") && !observation.path("qualifiers").has("priorIndependentSequences"))
                addActual(findings, "CH13-E86-CONTEXT-SEQUENCE-UNIQUENESS", "standard-element",
                        Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED,
                        "Prior independent transaction history is absent; uniqueness is not certified against unsupplied transactions");
            observedLifecycle(observation, findings);
            observedPreauthorization(observation, findings);
            findings.addAll(ProductSegmentPayloadValidator.observedStandardTotals(fields,
                    observation.path("history").get("standardAmountComponents")));
        }
        if (!observation.has("serializedSegment")) {
            addActual(findings, "SEG100-R-021", "standard-wire", Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED, "Observed Standard raw wire required");
            return List.copyOf(findings);
        }
        int last = -1;
        for (int i = 0; i < NUMBERED_ORDER.size(); i++) if (fields.has(NUMBERED_ORDER.get(i))) last = i;
        StringBuilder expected = new StringBuilder();
        boolean complete = last >= 0;
        for (int i = 0; i <= last; i++) {
            JsonNode value = fields.get(NUMBERED_ORDER.get(i));
            if (value == null) complete = false;
            else if (!value.isTextual()) {
                addActual(findings, "SEG100-R-021", "standard-wire", Chapter12SegmentPayloadValidator.Status.INVALID, "Nontext observed field cannot be serialized");
                return List.copyOf(findings);
            } else expected.append(value.textValue()).append('\u001c');
        }
        if (!complete) {
            addActual(findings, "SEG100-R-021", "standard-wire", Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED,
                    "Missing interior field is not an observed blank; no separator/field value is manufactured");
            return List.copyOf(findings);
        }
        JsonNode wire = observation.path("serializedSegment");
        boolean equal = wire.isTextual() && expected.toString().equals(wire.textValue());
        addActual(findings, "SEG100-R-021", "standard-wire", equal ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED
                : Chapter12SegmentPayloadValidator.Status.INVALID, "12.1: every actual field including interior blanks retains FS; last transmitted field is followed by FS");
        if (equal) addActual(findings, "SEG100-R-016", "standard-wire-length",
                wire.textValue().chars().anyMatch(c -> c > 127) ? Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED
                        : declared != null && declared.matches("[0-9]{3}") && Integer.parseInt(declared) == wire.textValue().length()
                        ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED : Chapter12SegmentPayloadValidator.Status.INVALID,
                "Measured ASCII wire includes all elements and separators; unspecified non-ASCII encoding is not certified");
        if (equal && wire.textValue().chars().allMatch(c -> c <= 127)) {
            ((com.fasterxml.jackson.databind.node.ObjectNode) oracleObservation).put("representation", "WIRE");
            ((com.fasterxml.jackson.databind.node.ObjectNode) oracleObservation).put("wireCapacityChecks", true);
            for (var finding : elementOracle.validate(oracleObservation).findings())
                if (finding.ruleId().endsWith("-WIRE-WIDTH") || finding.ruleId().endsWith("-WIRE-CAPACITY"))
                    findings.add(new Chapter12SegmentPayloadValidator.Finding(finding.ruleId(), "standard-wire-element",
                            Chapter12SegmentPayloadValidator.Status.valueOf(finding.status().name()), finding.reason()));
        }
        if (last >= 0 && fields.path(NUMBERED_ORDER.get(last)).isTextual())
            addActual(findings, "SEG100-R-022", "standard-trailing-fields",
                    fields.path(NUMBERED_ORDER.get(last)).textValue().isEmpty() ? Chapter12SegmentPayloadValidator.Status.INVALID
                            : Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED, "Unneeded empty trailing optional fields are not transmitted");
        return List.copyOf(findings);
    }

    private static void addActual(List<Chapter12SegmentPayloadValidator.Finding> findings, String rule, String scope,
            Chapter12SegmentPayloadValidator.Status status, String reason) {
        findings.add(new Chapter12SegmentPayloadValidator.Finding(rule, scope, status, reason));
    }

    private static void observedLifecycle(JsonNode observation, List<Chapter12SegmentPayloadValidator.Finding> findings) {
        JsonNode lifecycle = observation.path("history").get("standardLifecycle");
        if (lifecycle == null) {
            addActual(findings, "SEG100-R-015", "standard-sequence-lifecycle", Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED,
                    "12.1 sequence association needs observed matched original/follow-up history; absence never proves lifetime association");
            return;
        }
        if (!lifecycle.isObject() || lifecycle.has("flow") && !lifecycle.path("flow").isTextual()) {
            addActual(findings, "SEG100-R-015", "standard-sequence-lifecycle", Chapter12SegmentPayloadValidator.Status.INVALID,
                    "Observed lifecycle must be an object with textual flow");
            return;
        }
        if (!lifecycle.has("flow") || !lifecycle.has("originalElements")) {
            addActual(findings, "SEG100-R-015", "standard-sequence-lifecycle", Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED,
                    "Observed lifecycle requires explicit flow and original numbered elements");
            return;
        }
        Segment100MultiStepCoverageValidator.Flow flow = null;
        for (var supported : Segment100MultiStepCoverageValidator.Flow.values())
            if (supported.name().equals(lifecycle.path("flow").textValue())) flow = supported;
        JsonNode original = lifecycle.path("originalElements");
        if (flow == null || !original.isObject()) {
            addActual(findings, "SEG100-R-015", "standard-sequence-lifecycle", Chapter12SegmentPayloadValidator.Status.INVALID,
                    "Malformed observed lifecycle flow or original elements; no flow is guessed");
            return;
        }
        JsonNode fields = observation.path("elements");
        for (String id : List.of("78", "86", "102"))
            if (!original.has(id) || !fields.has(id)) {
                addActual(findings, "SEG100-R-015", "standard-sequence-lifecycle", Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED,
                        "Missing matched lifecycle field " + id + "; original values cannot fill current fields");
                return;
            }
        var result = new Segment100MultiStepCoverageValidator().validateObservedPair(flow, original, fields);
        addActual(findings, "SEG100-R-015", "standard-sequence-lifecycle", result.isValid()
                        ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED : Chapter12SegmentPayloadValidator.Status.INVALID,
                "Existing explicit flow/code, six-digit sequence and terminal pair oracle executed against supplied originals; unsupplied lifetime history is not authenticated");
    }

    private static void observedPreauthorization(JsonNode observation, List<Chapter12SegmentPayloadValidator.Finding> findings) {
        JsonNode fields = observation.path("elements");
        if (!fields.has("49") || fields.path("49").isTextual() && fields.path("49").textValue().isEmpty()) return;
        var status = Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED;
        String reason = "12.1/Element49: preauthorization timestamp correspondence requires observed capture role and matched original timing; TOR/debit cancellation uses terminal local time instead";
        JsonNode history = observation.path("history");
        if ("AUTHORIZATION_COMPLETION".equals(history.path("standardLifecycle").path("flow").asText())
                && fields.path("78").isTextual() && fields.path("78").textValue().matches("0[A-Za-z0-9]{3}")) {
            JsonNode prior = history.get("preauthorization");
            if (prior != null && !prior.isObject()) {
                status = Chapter12SegmentPayloadValidator.Status.INVALID;
                reason = "Observed preauthorization timing must be an object";
            } else if (prior != null && prior.has("terminalIdentifier") && prior.has("sequenceNumber") && prior.has("localDateTime")
                    && fields.has("102") && fields.has("86")) {
                boolean valid = prior.path("terminalIdentifier").isTextual() && prior.path("sequenceNumber").isTextual()
                        && prior.path("localDateTime").isTextual() && prior.path("localDateTime").textValue().matches("[0-9]{10}")
                        && prior.path("terminalIdentifier").equals(fields.path("102"))
                        && prior.path("sequenceNumber").equals(fields.path("86"))
                        && prior.path("localDateTime").equals(fields.path("49"));
                status = valid ? Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED : Chapter12SegmentPayloadValidator.Status.INVALID;
                reason = "Observed authorization-completion capture timestamp identifies supplied preauthorization for the same actual terminal/sequence; no equality requirement is imposed on terminal-local TOR/cancellation timestamps";
            }
        }
        addActual(findings, "SEG100-R-015", "standard-preauthorization-timestamp", status, reason);
    }

    private static final java.util.Set<String> ACTUAL_REQUESTS = java.util.Set.of("Financial Transaction Request",
            "EMV Financial Transaction Request", "Loyalty Card Transaction", "Loyalty Card Transaction Request",
            "ECA/TeleCheck Service Transaction", "ECA/TeleCheck Service Transaction Request");
    private static final Pattern LENGTH = Pattern.compile("^[0-9]{3,4}$");
    private static final Pattern SEGMENT_LENGTH = Pattern.compile("^[0-9]{3}$");
    /** ATL105 Chapter 13.2 Element 84: Segment 100's Segment Length ranges 001-218. */
    private static final int SEGMENT_100_MIN_LENGTH = 1;
    private static final int SEGMENT_100_MAX_LENGTH = 218;
    private static final List<String> SEGMENT_100_ORDER = List.of(
            "segmentType", "segmentLength", "informationByte", "terminalIdentifier", "promptCode",
            "accountNumber", "cardDiscretionaryBlockData", "encryptedPinBlockData", "pumpLaneNumber",
            "fuelPurchaseAmount", "nonfuelAmount", "taxAmount", "cashAmount", "sequenceNumber",
            "approvalNumber", "localDateTime", "partialApprovalIndicator");

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String packageId = artifactPackage == null || artifactPackage.getManifest() == null
                ? "segment-100-serialization" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(packageId == null ? "segment-100-serialization" : packageId);
        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            result.addError("Segment100Serialization", "test data is required");
            return result;
        }
        for (CanonicalTestData data : artifactPackage.getTestData()) {
            validateTestData(data, result);
        }
        return result;
    }

    private void validateTestData(CanonicalTestData data, ValidationResult result) {
        if (data == null || data.getPayload() == null) {
            return;
        }
        JsonNode payload = data.getPayload();
        JsonNode controls = payload.path("testControls");
        if (!controls.path("validateSerialization").asBoolean(false)) {
            return;
        }
        String id = data.getId() == null ? "unknown-test-data" : data.getId();
        JsonNode request = payload.path("request");
        JsonNode header = request.path("tcpIpHeader");
        JsonNode section1 = request.path("dataSection1");
        JsonNode segment = request.path("dataSection2").path("standardSegment");

        checkEquals(header, "byteOrder", "network-big-endian", id, "tcpIpHeader", result);
        checkEquals(header, "tpduProtocolId", "60", id, "tcpIpHeader", result);
        checkEquals(header, "tpduDestinationAddress", "0000", id, "tcpIpHeader", result);
        checkEquals(header, "tpduSourceAddress", "0000", id, "tcpIpHeader", result);
        String messageLength = header.path("messageLength").asText(null);
        if (messageLength == null || !("CALCULATE_FROM_FOLLOWING_PAYLOAD".equals(messageLength)
                || LENGTH.matcher(messageLength).matches())) {
            result.addError("Segment100Serialization", id + " tcpIpHeader.messageLength is invalid");
        }

        checkEquals(section1.path("fieldSeparators"), "betweenElements55And63", "true", id,
                "dataSection1.fieldSeparators", result);
        checkEquals(section1.path("fieldSeparators"), "afterElement63", "true", id,
                "dataSection1.fieldSeparators", result);
        String segmentLength = segment.path("segmentLength").asText(null);
        if (segmentLength == null || !("CALCULATE_FROM_SEGMENT_CONTENT".equals(segmentLength)
                || SEGMENT_LENGTH.matcher(segmentLength).matches())) {
            result.addError("Segment100Serialization", id + " standardSegment.segmentLength is invalid");
        } else if (LENGTH.matcher(segmentLength).matches()) {
            int value = Integer.parseInt(segmentLength);
            if (value < SEGMENT_100_MIN_LENGTH || value > SEGMENT_100_MAX_LENGTH) {
                result.addError("Segment100Serialization", id
                        + " standardSegment.segmentLength " + segmentLength
                        + " is outside ATL105 Chapter 13.2 Element 84's Segment 100 range 001-218");
            }
        }
        if ("CALCULATE_FROM_SEGMENT_CONTENT".equals(segmentLength)) {
            result.addWarning("REVIEW_REQUIRED: " + id + " Segment 100 length is a generation instruction, not verified encoded-length evidence");
        }

        List<String> actualOrder = fieldNames(segment);
        if (!isValidFieldOrder(actualOrder)) {
            result.addError("Segment100Serialization", id + " Segment 100 field order is "
                    + actualOrder + "; expected " + SEGMENT_100_ORDER);
        }

        if (controlBoolean(controls, "serialization.trailingOptionalFieldsOmitted")) {
            validateTrailingFields(segment, id, result);
            JsonNode omitted = controls.path("serialization").path("omittedTrailingFields");
            if (omitted.isArray()) {
                for (JsonNode field : omitted) {
                    if (segment.has(field.asText())) {
                        result.addError("Segment100Serialization", id
                                + " trailing optional field was not omitted: " + field.asText());
                    }
                }
            }
        }
        if (controlBoolean(controls, "serialization.nonTrailingEmptyFieldSeparator")
                && !section1.path("fieldSeparators").path("afterElement63").asBoolean(false)) {
            result.addError("Segment100Serialization", id + " non-trailing empty fields require separators");
        }
    }

    private static boolean isValidFieldOrder(List<String> actualOrder) {
        if (actualOrder.size() > SEGMENT_100_ORDER.size()) return false;
        for (int index = 0; index < actualOrder.size(); index++) {
            if (!SEGMENT_100_ORDER.get(index).equals(actualOrder.get(index))) return false;
        }
        return true;
    }

    private static List<String> fieldNames(JsonNode segment) {
        List<String> names = new ArrayList<>();
        Iterator<String> fields = segment.fieldNames();
        while (fields.hasNext()) {
            names.add(fields.next());
        }
        return names;
    }

    private static void validateTrailingFields(JsonNode segment, String id, ValidationResult result) {
        List<String> names = fieldNames(segment);
        for (int index = names.size() - 1; index >= 0; index--) {
            String name = names.get(index);
            if (segment.path(name).asText("").isEmpty()) {
                result.addError("Segment100Serialization", id
                        + " contains an empty trailing field: " + name);
            } else {
                break;
            }
        }
    }

    private static void checkEquals(JsonNode node, String field, String expected, String id,
                                    String path, ValidationResult result) {
        String actual = node.path(field).asText(null);
        if (!expected.equals(actual)) {
            result.addError("Segment100Serialization", id + " " + path + "." + field
                    + " must be '" + expected + "', got: " + actual);
        }
    }

    private static boolean controlBoolean(JsonNode controls, String dottedName) {
        if (controls.path(dottedName).isBoolean()) {
            return controls.path(dottedName).asBoolean();
        }
        int separator = dottedName.indexOf('.');
        if (separator > 0) {
            return controls.path(dottedName.substring(0, separator))
                    .path(dottedName.substring(separator + 1)).asBoolean(false);
        }
        return false;
    }
}