package com.coreauth.validator.canonical;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Finding;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Result;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/** Opt-in actual envelope bridge to the existing named-field validators; never fabricates companions. */
public final class Chapter12LegacyActualPayloadAdapter {
    private com.coreauth.validator.validation.Chapter13DataElementValidator elementOracle;

    public Chapter12LegacyActualPayloadAdapter() { }

    public Chapter12LegacyActualPayloadAdapter(com.coreauth.validator.validation.Chapter13DataElementValidator elementOracle) {
        this.elementOracle = java.util.Objects.requireNonNull(elementOracle);
    }

    public static final Set<String> SEGMENTS = Set.of("101", "104", "105", "108", "109", "110", "111", "112", "113", "114", "118", "120");
    private static final Map<String, String> NAMES = Map.ofEntries(
            Map.entry("100", "Standard Segment"), Map.entry("101", "Fleet Data Segment"),
            Map.entry("104", "Purchase Card Data Segment"), Map.entry("105", "Totals Data Segment"),
            Map.entry("108", "Loyalty Card Data Segment"), Map.entry("109", "Electronic Mail Data Segment"),
            Map.entry("110", "Check Data Segment"), Map.entry("111", "Variable Information Data Segment"),
            Map.entry("112", "Additional Information Data Segment"), Map.entry("113", "ECA/TeleCheck Data Segment"),
            Map.entry("114", "SKU Data Segment"), Map.entry("118", "Proprietary Data Load Segment"), Map.entry("120", "Print Data 2 Segment"),
            Map.entry("145", "Enhanced Fleet Data Segment"));
    private static final Pattern RULE = Pattern.compile("SEG[0-9]+-R-[0-9]+");

    public Result validate(JsonNode actualMessage, String segment, String family) {
        return validate(actualMessage, segment, family, JsonNodeFactory.instance.objectNode());
    }

    public Result validate(JsonNode actualMessage, String segment, String family, JsonNode evidence) {
        if (!SEGMENTS.contains(segment)) throw new IllegalArgumentException("Unsupported legacy actual segment " + segment);
        List<Finding> findings = new ArrayList<>();
        if (actualMessage == null || !actualMessage.isObject() || family == null) return new Result(Status.INVALID,
                List.of(new Finding("CH12-LEGACY-INPUT", "actual-legacy-input", Status.INVALID, "Observed message object and family required")), List.of());
        ObjectNode normalized = JsonNodeFactory.instance.objectNode();
        var entries = actualMessage.fields();
        int occurrences = 0;
        while (entries.hasNext()) {
            var entry = entries.next();
            JsonNode value = entry.getValue();
            String id = value.isObject() && value.path("SegmentType").isTextual() ? value.path("SegmentType").textValue() : null;
            if (segment.equals(id)) occurrences++;
            String name = NAMES.getOrDefault(id == null ? "" : id, entry.getKey());
            if (normalized.has(name)) findings.add(new Finding("CH12-LEGACY-AMBIGUITY", "actual-legacy-input", Status.INVALID,
                    "Repeated actual source identity cannot be collapsed into the named validator contract"));
            else normalized.set(name, value.deepCopy());
            textual(value, findings);
        }
        if (occurrences != 1) findings.add(new Finding("CH12-LEGACY-IDENTITY", "actual-legacy-input", Status.INVALID,
                "Exactly one observed actual Segment " + segment + " required"));
        boolean request = !family.endsWith("Response");
        String root = switch (segment) {
            case "105" -> "Totals Request";
            case "108" -> "Loyalty Card Transaction Request";
            case "110", "113" -> "ECA/TeleCheck Service Transaction Request";
            case "109" -> request ? "Electronic Mail Request" : "Electronic Mail Response";
            case "118" -> request ? "Proprietary Data Load Request" : "Proprietary Data Load Response";
            case "112", "120" -> "Financial Transaction Response";
            default -> request ? "Financial Request" : "Financial Transaction Response";
        };
        boolean appropriate = switch (segment) {
            case "105", "109", "118" -> root.equals(family);
            case "108" -> "Loyalty Card Transaction".equals(family) || root.equals(family);
            case "110", "113" -> root.equals(family) || "ECA/TeleCheck Service Transaction".equals(family);
            case "111" -> Set.of("Financial Transaction Request", "EMV Financial Transaction Request",
                    "ECA/TeleCheck Service Transaction", "ECA/TeleCheck Service Transaction Request").contains(family);
            case "112", "120" -> Set.of("Financial Transaction Response", "EMV Financial Transaction Response").contains(family);
            case "114" -> Set.of("Financial Transaction Request", "EMV Financial Transaction Request", "Loyalty Card Transaction",
                    "Loyalty Card Transaction Request").contains(family);
            default -> Set.of("Financial Transaction Request", "EMV Financial Transaction Request").contains(family);
        };
        if (!appropriate) findings.add(new Finding("CH12-LEGACY-FAMILY", "actual-legacy-family", Status.INVALID,
                "Observed family is outside this source-supported named-field validator contract"));
        ObjectNode payload = JsonNodeFactory.instance.objectNode();
        payload.set(root, normalized);
        JsonNode selected = normalized.get(NAMES.get(segment));
        if (selected != null && selected.isObject()) {
            if ("114".equals(segment)) alias((ObjectNode) selected, "SerializedSegment", "serializedSegment", findings);
            if ("120".equals(segment)) alias((ObjectNode) selected, "SerializedSegment", "wireFormat", findings);
            if ("111".equals(segment)) variableWire(selected, findings);
            else if ("118".equals(segment)) {
                var load = new Segment118PayloadValidator();
                findings.addAll(load.validateActualDeviceVersions(selected, evidence, request));
                findings.addAll(load.validateActualWire(selected, request));
                if (!request) findings.addAll(Chapter12LoadLifecycleValidator.receipt(selected, evidence));
                if (request) {
                    findings.addAll(load.validateActualHistory(selected, evidence));
                    findings.addAll(Chapter12LoadLifecycleValidator.site(selected, evidence));
                }
            }
            else if ("105".equals(segment)) {
                alias((ObjectNode) selected, "TerminalIdentifier", "TerminalID", findings);
                findings.addAll(new TotalsRequestPayloadValidator().validateActualWire(selected));
            }
            else if (Set.of("101", "104", "108", "109", "110", "113").contains(segment) && request) separatedWire(selected, segment, findings);
            else if (!Set.of("114", "120").contains(segment) && selected.has("SerializedSegment"))
                findings.add(new Finding("CH12-LEGACY-WIRE", "actual-legacy-wire", Status.REVIEW_REQUIRED,
                        "Actual wire supplied but no complete wire oracle is integrated for this legacy family; field execution is not wire certification"));
        }
        if ("113".equals(segment) && selected != null && selected.isObject()) {
            micrCompanions(selected, normalized.path("Check Data Segment"), normalized.path("Standard Segment"), evidence, findings);
            voidTrace(selected, normalized.path("Standard Segment"), findings);
        }
        if ("101".equals(segment) && request && selected != null && selected.isObject()) {
            var fleet = new Segment101PayloadValidator();
            findings.addAll(fleet.validateActualTagContext(selected, normalized,
                    normalized.path("Standard Segment"), evidence));
            findings.addAll(fleet.validateActualNumericFields(selected));
            findings.addAll(fleet.validateActualTagGrammar(selected));
            findings.addAll(fleet.validateActualPlacement(selected));
            findings.addAll(fleet.validateActualRowFields(selected));
            findings.addAll(fleet.validateActualExclusion(normalized.has(NAMES.get("145"))));
        }
        if ("101".equals(segment) && request) findings.addAll(new Segment101PayloadValidator().validateActualPresence(
                occurrences > 0, normalized.has(NAMES.get("145")), evidence));
        if ("112".equals(segment)) {
            alias(normalized, "AdditionalInformationFlag", "additionalInformationDataSegmentFlag", findings);
        }
        if ("120".equals(segment)) {
            JsonNode print = normalized.remove(NAMES.get(segment));
            normalized.putObject("dataSection3").set("Print Data 2 Segment", print);
            payload.put("messageType", family.replace(' ', '_').toUpperCase(java.util.Locale.ROOT));
        }
        ValidationResult assessed = switch (segment) {
            case "101" -> new Segment101PayloadValidator().validatePayload(payload);
            case "104" -> new Segment104PayloadValidator().validatePayload(payload);
            case "105" -> new TotalsRequestPayloadValidator().validateActualPayload(payload);
            case "108" -> new Segment108PayloadValidator().validatePayload(payload);
            case "109" -> new Segment109PayloadValidator().validatePayload(payload);
            case "110" -> new Segment110PayloadValidator().validatePayload(payload);
            case "111" -> new Segment111PayloadValidator().validatePayload(payload);
            case "112" -> new Segment112PayloadValidator().validatePayload(payload);
            case "113" -> new Segment113PayloadValidator().validatePayload(payload);
            case "114" -> new Segment114PayloadValidator().validatePayload(payload);
            case "118" -> new Segment118PayloadValidator().validateActualPayload(payload);
            case "120" -> new Segment120PayloadValidator().validateActualPayload(payload);
            default -> throw new IllegalArgumentException(segment);
        };
        boolean missingEvidence = false;
        for (var error : assessed.errors()) {
            var matcher = RULE.matcher(error.reason());
            String rule = matcher.find() ? matcher.group() : "CH12-LEGACY-FIELDS";
            String field = "118".equals(segment) ? switch (rule) {
                case "SEG118-R-006" -> "SegmentType"; case "SEG118-R-007" -> "SegmentLength";
                case "SEG118-R-008" -> "SequenceNumber"; case "SEG118-R-009" -> "InformationByte";
                case "SEG118-R-010" -> "TerminalIdentifier"; case "SEG118-R-011" -> "PromptCode";
                case "SEG118-R-012" -> "PromptCodePending"; case "SEG118-R-013" -> "DeviceCardTableVersion";
                case "SEG118-R-014" -> "CardTableLoadVersion"; case "SEG118-R-015" -> "CardTableType";
                case "SEG118-R-016" -> "LoadControlKey"; case "SEG118-R-017" -> "HostDiscountTimestamp";
                default -> null;
            } : null;
            boolean missing = field != null && selected != null && !selected.has(field);
            missingEvidence |= missing;
            findings.add(new Finding(rule, "actual-legacy-fields", missing ? Status.REVIEW_REQUIRED : Status.INVALID,
                    missing ? "Actual required field absent; metadata cannot replace it: " + field : error.reason()));
        }
        for (String warning : assessed.warnings()) findings.add(new Finding("CH12-LEGACY-BOUNDARY", "actual-legacy-fields", Status.REVIEW_REQUIRED, warning));
        boolean invalidFields = findings.stream().anyMatch(f -> f.scope().equals("actual-legacy-fields") && f.status() == Status.INVALID);
        findings.add(new Finding("CH12-LEGACY-EXECUTION", "actual-legacy-execution", invalidFields ? Status.INVALID
                : missingEvidence ? Status.REVIEW_REQUIRED : Status.CHECKS_PASSED,
                "Existing named-field validator executed against observed fields/companions only; absent wire and unevaluated clauses remain open"));
        return new Result(findings.stream().anyMatch(f -> f.status() == Status.INVALID) ? Status.INVALID : Status.REVIEW_REQUIRED,
                List.copyOf(findings), List.of());
    }

    private static void textual(JsonNode node, List<Finding> findings) {
        if (node.isContainerNode()) for (JsonNode child : node) textual(child, findings);
        else if (!node.isTextual()) findings.add(new Finding("CH12-LEGACY-REPRESENTATION", "actual-legacy-input", Status.INVALID,
                "Actual scalar fields must be text; existing validator coercion is not actual evidence"));
    }

    private static void alias(ObjectNode node, String source, String target, List<Finding> findings) {
        if (!node.has(source)) return;
        if (node.has(target) && !node.path(source).equals(node.path(target)))
            findings.add(new Finding("CH12-LEGACY-AMBIGUITY", "actual-legacy-input", Status.INVALID, "Contradictory observed aliases " + source + "/" + target));
        else node.set(target, node.path(source).deepCopy());
    }

    private static void variableWire(JsonNode segment, List<Finding> findings) {
        if (!segment.has("SerializedSegment")) {
            findings.add(new Finding("SEG111-R-007", "actual-legacy-wire", Status.REVIEW_REQUIRED, "Observed raw Segment111 required for separator execution"));
            return;
        }
        JsonNode repetitions = segment.path("repetitions");
        if (!repetitions.isArray()) {
            findings.add(new Finding("SEG111-R-007", "actual-legacy-wire", Status.REVIEW_REQUIRED, "Explicit repetitions array required for this bounded raw-wire mapping"));
            return;
        }
        StringBuilder expected = new StringBuilder(segment.path("SegmentType").asText()).append('\u001c')
                .append(segment.path("SegmentLength").asText()).append('\u001c');
        boolean complete = segment.path("SegmentType").isTextual() && segment.path("SegmentLength").isTextual();
        for (JsonNode repetition : repetitions)
            for (String field : List.of("VariableInformationIndicator", "VariableInformationLength", "VariableInformation")) {
                complete &= repetition.path(field).isTextual();
                expected.append(repetition.path(field).asText());
            }
        expected.append('\u001c');
        JsonNode wire = segment.path("SerializedSegment");
        String length = segment.path("SegmentLength").asText();
        boolean valid = complete && wire.isTextual() && expected.toString().equals(wire.textValue())
                && wire.textValue().chars().allMatch(c -> c <= 127) && length.matches("[0-9]{3}")
                && Integer.parseInt(length) == wire.textValue().length();
        findings.add(new Finding("SEG111-R-007", "actual-legacy-wire", valid ? Status.CHECKS_PASSED : Status.INVALID,
                "Source12576-12579: separators only after Type, Length and final repetition; measured ASCII length, no inter-repetition separators"));
    }

    private static void separatedWire(JsonNode observed, String segment, List<Finding> findings) {
        int start = findings.size();
        checkSeparatedWire(observed, segment, findings);
        String orderRule = "101".equals(segment) ? "SEG101-R-007" : "108".equals(segment) ? "SEG108-R-006" : null;
        if (orderRule != null) {
            int end = findings.size();
            for (int index = start; index < end; index++) {
                Finding finding = findings.get(index);
                if ("actual-legacy-wire".equals(finding.scope())) findings.add(new Finding(orderRule,
                        "actual-legacy-wire-order", finding.status(), finding.reason()));
            }
        }
    }

    private static void checkSeparatedWire(JsonNode observed, String segment, List<Finding> findings) {
        List<String> fields = switch (segment) {
            case "101" -> List.of("SegmentType", "SegmentLength", "Odometer", "VehicleNumber", "JobNumber",
                    "DriverIdentificationNumber", "FleetEmployeeNumber", "LicenseNumber", "JobId", "DepartmentNumber",
                    "CustomerData", "UserId", "VehicleIdNumber");
            case "104" -> List.of("SegmentType", "SegmentLength", "PurchaseCode", "PcTaxAmount", "PcFreightAmount",
                    "PcDutyAmount", "ShipToCountryCode", "ShipToPostalCode", "ShipFromPostalCode", "DirectMarketingInvoiceNumber");
            case "108" -> List.of("SegmentType", "SegmentLength", "LoyaltyProgramId", "LoyaltyAccountNumber",
                    "PointsToRedeem", "CouponId", "CouponAmount", "UpdateCode", "StreetAddress", "PhoneNumberLoyalty",
                    "ExpirationDate", "PaymentTenderType", "LoyaltyTrack2Data", "LoyaltyInformationVersion", "UnitOfWork");
            case "109" -> List.of("SegmentType", "SegmentLength", "InformationByte", "TerminalIdentifier", "PromptCode",
                    "BlockNumber", "EmployeeNumber", "Password", "SequenceNumber", "LocalTime", "ExtractDate",
                    "ExtractTime", "TextDataLength", "TextData");
            case "110" -> List.of("SegmentType", "SegmentLength", "MICRData", "DriversLicense", "StateCode", "DateOfBirth",
                    "CheckType", "CheckNumber", "CustomerPhoneNumber", "CustomerLastName", "CheckIssueDate", "AlternateMicrIndicator");
            case "113" -> List.of("SegmentType", "SegmentLength", "EcaClerkId", "EcaProductCode", "EcaPhoneNumber",
                    "EcaTraceId", "MerchantTraceId", "DenialRecordNumber", "ExtendedMicrData");
            default -> throw new IllegalArgumentException(segment);
        };
        if ("101".equals(segment)) {
            int lastTag = 0;
            for (int index = 1; index <= 5; index++) if (observed.has("FleetTag" + index)) lastTag = index;
            if (lastTag > 0) {
                fields = new ArrayList<>(fields);
                for (int index = 1; index <= lastTag; index++) fields.add("FleetTag" + index);
            }
        }
        String wireRule = "SEG" + segment + "-R-" + switch (segment) {
            case "101", "109" -> "008";
            case "104" -> "005";
            default -> "007";
        };
        String lengthRule = "SEG" + segment + "-R-" + (Set.of("108", "113").contains(segment) ? "004" : "005");
        String capRule = "SEG" + segment + "-R-" + ("110".equals(segment) ? "002" : Set.of("108", "113").contains(segment) ? "005" : "006");
        int cap = switch (segment) { case "101" -> 61; case "104" -> 86; case "108" -> 142; case "109" -> 232; case "110" -> 168; default -> 156; };
        String source = switch (segment) { case "101" -> "11406"; case "104" -> "11815"; case "108" -> "12118"; case "109" -> "12251"; case "110" -> "12368"; default -> "12754"; };
        JsonNode raw = observed.get("SerializedSegment");
        if (raw == null) {
            findings.add(new Finding(wireRule, "actual-legacy-wire", Status.REVIEW_REQUIRED, "Source" + source + ": observed raw wire required"));
            findings.add(new Finding(lengthRule, "actual-legacy-wire-length", Status.REVIEW_REQUIRED, "Missing independently observed wire"));
            findings.add(new Finding(capRule, "actual-legacy-wire-cap", Status.REVIEW_REQUIRED, "Missing independently observed wire"));
            return;
        }
        if (!raw.isTextual()) {
            findings.add(new Finding(wireRule, "actual-legacy-wire", Status.INVALID, "Observed wire must be text"));
            return;
        }
        String wire = raw.textValue();
        boolean ascii = wire.chars().allMatch(c -> c <= 127);
        boolean extendedFleet = "101".equals(segment) && fields.size() > 13;
        findings.add(new Finding(capRule, "actual-legacy-wire-cap", extendedFleet ? Status.REVIEW_REQUIRED : ascii && wire.length() >= 1 && wire.length() <= cap
                ? Status.CHECKS_PASSED : Status.INVALID, extendedFleet
                ? "Source61 base cap conflicts with additional five34-byte Auth Completion tags; extended maximum is not inferred"
                : "Measured observed ASCII wire must be within1-" + cap + "; metadata length cannot replace observation"));
        String length = observed.path("SegmentLength").isTextual() ? observed.path("SegmentLength").textValue() : null;
        findings.add(new Finding(lengthRule, "actual-legacy-wire-length", length == null ? observed.has("SegmentLength") ? Status.INVALID : Status.REVIEW_REQUIRED
                : ascii && length.matches("[0-9]{3}") && Integer.parseInt(length) == wire.length()
                ? Status.CHECKS_PASSED : Status.INVALID, "Source Element84: observed N3 must equal measured wire including Type and actual separators"));
        for (String field : fields) {
            if (!observed.has(field)) {
                findings.add(new Finding(wireRule, "actual-legacy-wire", Status.REVIEW_REQUIRED,
                        "Missing observed field " + field + "; no omitted value or implicit blank is manufactured"));
                return;
            }
            if (!observed.path(field).isTextual() || observed.path(field).textValue().indexOf('\u001c') >= 0) {
                findings.add(new Finding(wireRule, "actual-legacy-wire", Status.INVALID, "Observed fields must be text without embeddedFS: " + field));
                return;
            }
        }
        String joined = fields.stream().map(field -> observed.path(field).textValue()).collect(java.util.stream.Collectors.joining("\u001c"));
        if ("101".equals(segment)) {
            var names = observed.fieldNames();
            while (names.hasNext()) {
                String name = names.next();
                if (name.startsWith("FleetTag") && !name.matches("FleetTag[1-5]")) {
                    findings.add(new Finding(wireRule, "actual-legacy-wire", Status.INVALID,
                            "Source14-18 provides at most five observed FleetTag slots"));
                    return;
                }
            }
        }
        boolean matches = ascii && (joined.equals(wire) || (joined + '\u001c').equals(wire));
        findings.add(new Finding(wireRule, "actual-legacy-wire", matches ? Status.CHECKS_PASSED : Status.INVALID,
                "Source" + source + ": observed ordered fields retain every interior FS including explicit empty values"
                        + ("108".equals(segment) ? "; the explicit FS following field13 is included before observed fields14-15" : "")
                        + "; terminalFS is not prescribed"));
        findings.add(new Finding("CH12-LEGACY-TERMINAL-FS", "actual-legacy-wire-boundary", Status.REVIEW_REQUIRED,
                "Source specifies interior separators but does not prescribe final-field terminator; neither observed variant certifies complete transport framing"));
    }

    private void micrCompanions(JsonNode eca, JsonNode check, JsonNode standard, JsonNode evidence, List<Finding> findings) {
        if (evidence != null && evidence.has("context") && !evidence.path("context").isObject()) {
            findings.add(new Finding("SEG113-R-014", "actual-micr-extension", Status.INVALID,
                    "Observed MICR context must be an object"));
            return;
        }
        JsonNode context = evidence == null ? JsonNodeFactory.instance.objectNode() : evidence.path("context");
        JsonNode raw = context.get("rawMicr");
        if (raw == null) {
            findings.add(new Finding("SEG113-R-014", "actual-micr-extension", Status.REVIEW_REQUIRED,
                    "Source12846-12852/23095-23105: independent observed raw MICR required to execute long-check dependency"));
            return;
        }
        if (!raw.isTextual() || raw.textValue().isEmpty() || raw.textValue().chars().anyMatch(c -> c > 127)) {
            findings.add(new Finding("SEG113-R-014", "actual-micr-extension", Status.INVALID,
                    "Observed raw MICR must be nonempty ASCII text; character counts cannot masquerade as source bytes"));
            return;
        }
        if (!check.isObject()) {
            findings.add(new Finding("SEG113-R-014", "actual-micr-extension", Status.REVIEW_REQUIRED,
                    "Actual Segment110 companion required; metadata cannot provide Element122"));
            return;
        }
        String promptCode = promptCode(standard);
        if (standard.has("PromptCode") && promptCode == null) {
            findings.add(new Finding("SEG113-R-014", "actual-micr-extension", Status.INVALID,
                    "Observed Standard Prompt Code must contain actual one-character transaction type and N3 card type"));
            return;
        }
        boolean ecaService = promptCode != null && promptCode.substring(1).equals("046");
        if (context.has("checkService") && (!context.path("checkService").isTextual()
                || (promptCode != null && "ECA_TELECHECK".equals(context.path("checkService").textValue()) != ecaService))) {
            findings.add(new Finding("SEG113-R-014", "actual-micr-extension", Status.INVALID,
                    "Supplied check service contradicts the actual Standard Prompt Code"));
            return;
        }
        ObjectNode observation = JsonNodeFactory.instance.objectNode().put("segment", "113").put("completeness", "PARTIAL")
                .put("specificationVersion", "2026-3").put("messageFamily", "ECA/TeleCheck Service Transaction Request");
        ObjectNode elements = observation.putObject("elements");
        if (check.has("MICRData")) elements.set("122", check.get("MICRData").deepCopy());
        if (eca.has("ExtendedMicrData")) elements.set("137", eca.get("ExtendedMicrData").deepCopy());
        ObjectNode oracleContext = observation.putObject("qualifiers").put("checkService", ecaService ? "ECA_TELECHECK" : "OTHER_CHECK");
        oracleContext.set("rawMicr", raw.deepCopy());
        if (elementOracle == null) {
            try {
                elementOracle = new com.coreauth.validator.validation.Chapter13DataElementValidator(com.coreauth.validator.paths.Atl105Paths.root());
            } catch (java.io.IOException exception) {
                throw new java.io.UncheckedIOException("Source-local MICR oracle cannot load its configured source artifacts", exception);
            }

        }
        var result = elementOracle.validate(observation);
        for (var finding : result.findings()) {
            if (!finding.ruleId().equals("CH13-E122-MICR-TRUNCATION")
                    && !finding.ruleId().equals("CH13-E137-MICR-EXTENSION")
                    && !finding.ruleId().equals("CH13-E122-COMPANION")
                    && !finding.ruleId().equals("CH13-E137-COMPANION")) continue;
            Status status = Status.valueOf(finding.status().name());
            JsonNode observed = finding.element().equals("122") ? check.get("MICRData") : eca.get("ExtendedMicrData");
            if (finding.ruleId().endsWith("-COMPANION") && observed != null)
                status = Status.INVALID;
            findings.add(new Finding(finding.element().equals("122") ? "SEG110-R-008" : "SEG113-R-014",
                    finding.element().equals("122") ? "actual-micr-truncation" : "actual-micr-extension",
                    status, finding.ruleId() + ": " + finding.reason()
                    + (status == Status.INVALID && finding.status().name().equals("REVIEW_REQUIRED") ? "; explicitly observed required value is empty or malformed" : "")));
        }
        if (raw.textValue().length() <= 50) findings.add(new Finding("SEG113-R-014", "actual-micr-extension", Status.CHECKS_PASSED,
                "Observed raw MICR is at most50 ASCII bytes; source long-MICR extension trigger is false, no absence prohibition inferred"));
        else if (promptCode == null) findings.add(new Finding("SEG113-R-014", "actual-micr-extension", Status.REVIEW_REQUIRED,
                "Source22885-22887: actual Standard Prompt Code is needed for the ECA service trigger; metadata cannot fill it"));
        else if (!ecaService) findings.add(new Finding("SEG113-R-014", "actual-micr-extension", Status.CHECKS_PASSED,
                "Observed actual card type is not046; the ECA long-MICR extension trigger is false, not an approval of Segment113 applicability"));
    }

    private static void voidTrace(JsonNode eca, JsonNode standard, List<Finding> findings) {
        JsonNode prompt = standard.get("PromptCode");
        if (prompt == null) {
            findings.add(new Finding("SEG113-R-011", "actual-void-trace", Status.REVIEW_REQUIRED,
                    "Source12802-12808: actual Standard Prompt Code required to determine Void trigger; metadata cannot substitute"));
            return;
        }
        String code = promptCode(standard);
        if (code == null) {
            findings.add(new Finding("SEG113-R-011", "actual-void-trace", Status.INVALID,
                    "Observed Prompt Code must contain actual one-character transaction type and N3 card type"));
            return;
        }
        String type = code.substring(0, 1);
        if (standard.has("TransactionType") && (!standard.path("TransactionType").isTextual()
                || !type.equals(standard.path("TransactionType").textValue()))) {
            findings.add(new Finding("SEG113-R-011", "actual-void-trace", Status.INVALID,
                    "Contradictory observed Standard transaction type and Prompt Code"));
            return;
        }
        boolean triggered = Set.of("8", "C", "U").contains(type);
        JsonNode trace = eca.get("EcaTraceId");
        Status status = !triggered ? Status.CHECKS_PASSED : trace == null ? Status.REVIEW_REQUIRED
                : trace.isTextual() && trace.textValue().matches("[A-Za-z0-9]{1,22}") ? Status.CHECKS_PASSED : Status.INVALID;
        findings.add(new Finding("SEG113-R-011", "actual-void-trace", status,
                "Source12802-12808/AppendixG27301-27325: observed Void types8/C/U require nonblank Element134; other types do not trigger this requirement"));
        if (triggered) findings.add(new Finding("CH12-ECA-VOID-SUPPORT", "actual-void-support", Status.REVIEW_REQUIRED,
                "Bounded trace requiredness does not resolve ECA/TeleCheck Void support or authenticate the original host trace"));
    }

    private static String promptCode(JsonNode standard) {
        JsonNode prompt = standard.path("PromptCode");
        if (prompt.isTextual() && prompt.textValue().matches("[0-9A-Z][0-9]{3}")) return prompt.textValue();
        if (prompt.isObject() && prompt.path("TransactionType").isTextual()
                && prompt.path("TransactionType").textValue().matches("[0-9A-Z]")
                && prompt.path("CardType").isTextual() && prompt.path("CardType").textValue().matches("[0-9]{3}"))
            return prompt.path("TransactionType").textValue() + prompt.path("CardType").textValue();
        return null;
    }
}
