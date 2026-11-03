package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Expands independently authored fixtures and existing synthetic sources; never reads producer runs. */
public final class Chapter12SourceLocalFixtures {
    private Chapter12SourceLocalFixtures() { }

    public static List<JsonNode> expand(JsonNode observations, JsonNode extension) throws IOException {
        Map<String, JsonNode> bases = new LinkedHashMap<>();
        for (JsonNode fixture : observations.path("observations")) {
            String segment = fixture.path("payload").path("segment").asText();
            if (bases.put(segment, fixture.path("payload")) != null) throw new IOException("Ambiguous segment fixture " + segment);
        }
        List<JsonNode> fixtures = new ArrayList<>();
        for (JsonNode profile : extension.path("actualWireProfiles")) {
            JsonNode prototype = extension.path("integratedProfiles").path(profile.path("profile").asText());
            if (!prototype.isObject()) throw new IOException("Authored actual wire prototype required");
            for (String mutation : List.of("VALID", "WRONG-WIRE", "MISSING-WIRE", "MISSING-FIELD")) {
                ObjectNode payload = prototype.deepCopy();
                JsonNode actual = payload.path("actualPayload");
                if (!actual.isObject()) throw new IOException("Actual wire prototype must be an object");
                if ("WRONG-WIRE".equals(mutation)) ((ObjectNode) actual).put("SerializedSegment", "WRONG");
                if ("MISSING-WIRE".equals(mutation)) ((ObjectNode) actual).remove("SerializedSegment");
                if ("MISSING-FIELD".equals(mutation)) edit(payload, profile.path("requiredPath").asText(), null);
                fixtures.add(fixture("ACTUAL-WIRE-" + payload.path("segment").asText() + "-" + mutation,
                        profile.path("wireRule").asText(), profile.path("wireScope").asText(),
                        "WRONG-WIRE".equals(mutation) ? "INVALID" : "REVIEW_REQUIRED",
                        "WRONG-WIRE".equals(mutation) ? "INVALID" : "VALID".equals(mutation) ? "CHECKS_PASSED" : "REVIEW_REQUIRED", payload));
            }
        }
        for (JsonNode probe : extension.path("actual119Probes")) {
            JsonNode prototype = null;
            for (JsonNode original : observations.path("observations"))
                if (original.has("actualPayload") && "119".equals(original.path("actualPayload").path("SegmentType").asText())) prototype = original;
            if (prototype == null) throw new IOException("Actual119 authored prototype required");
            ObjectNode payload = JsonNodeFactory.instance.objectNode().put("segment", "119")
                    .put("messageFamily", "Totals with Proprietary Data Load Request");
            ObjectNode actual = prototype.path("actualPayload").deepCopy();
            actual.put("TotalsDate", "222222");
            payload.set("actualPayload", actual);
            ObjectNode evidence = prototype.path("actualEvidence").deepCopy();
            ObjectNode totals = JsonNodeFactory.instance.objectNode().put("requestDate", "222222").put("responseDate", "261009")
                    .put("requestSequenceNumber", "000001").put("responseSequenceNumber", "000001")
                    .put("currentSettlementDate", "2026-10-09").put("nextSettlementDate", "2026-10-10").put("settlementDateAfter", "2026-10-10");
            totals.putArray("endedSettlementDates").add("2026-10-09");
            if (probe.has("cutoff")) totals.set("cutoff", probe.path("cutoff").deepCopy());
            evidence.putObject("history").set("totals", totals);
            payload.set("actualEvidence", evidence);
            fixtures.add(fixture(probe.path("id").asText(), "SEG119-R-032", probe.path("scope").asText(),
                    probe.path("expectedStatus").asText(), probe.path("expectedTargetStatus").asText(), payload));
        }
        for (JsonNode fixture : extension.path("integratedProbes")) {
            ObjectNode expanded = fixture.deepCopy();
            if ("standardWire".equals(fixture.path("payloadProfile").asText()) && fixture.path("ruleId").asText().startsWith("CH13-")) {
                if (!"SEG100-R-015".equals(extension.path("standardOracleSourceRule").asText()))
                    throw new IOException("Authored Standard oracle probes require explicit12.1 source-layout rule");
                expanded.put("targetRuleId", fixture.path("ruleId").asText());
                expanded.put("ruleId", extension.path("standardOracleSourceRule").asText());
            }
            if (fixture.has("payloadProfile")) {
                JsonNode profile = extension.path("integratedProfiles").path(fixture.path("payloadProfile").asText());
                if (!profile.isObject()) throw new IOException("Missing authored integrated payload profile");
                ObjectNode payload = profile.deepCopy();
                if (fixture.has("overrideProfile")) {
                    JsonNode shared = extension.path("overrideProfiles").path(fixture.path("overrideProfile").asText());
                    if (!shared.isObject()) throw new IOException("Missing authored override profile");
                    var sharedChanges = shared.fields();
                    while (sharedChanges.hasNext()) {
                        var change = sharedChanges.next();
                        edit(payload, change.getKey(), change.getValue());
                    }
                }
                var changes = fixture.path("overrides").fields();
                while (changes.hasNext()) {
                    var change = changes.next();
                    edit(payload, change.getKey(), change.getValue());
                }
                for (JsonNode omitted : fixture.path("omitPaths")) edit(payload, omitted.asText(), null);
                if (fixture.has("standardTextBoundary")) {
                    JsonNode boundary = fixture.path("standardTextBoundary");
                    int length = boundary.path("length").asInt(-1);
                    String value = boundary.path("character").asText();
                    if (length < 0 || length > 52 || value.length() != 1) throw new IOException("Authored Standard text boundary must be0-52 single characters");
                    edit(payload, "/actualPayload/" + boundary.path("field").asText(), JsonNodeFactory.instance.textNode(value.repeat(length)));
                }
                if (fixture.path("rebuildStandardWire").asBoolean(false)) {
                    ObjectNode actual = authoredObject(payload.path("actualPayload"), "authored Standard wire");
                    var fields = List.of("SegmentType", "SegmentLength", "InformationByte", "TerminalIdentifier", "PromptCode",
                            "AccountNumber", "CardDiscretionaryBlockData", "EncryptedPINBlockData", "PumpLaneNumber",
                            "FuelPurchaseAmount", "NonfuelAmount", "TaxAmount", "CashAmount", "SequenceNumber",
                            "ApprovalNumber", "LocalDateTime", "PartialApprovalIndicator");
                    StringBuilder raw = new StringBuilder();
                    for (String field : fields) {
                        if (!actual.path(field).isTextual()) throw new IOException("Authored Standard wire requires explicit text: " + field);
                        raw.append(actual.path(field).textValue()).append('\u001c');
                    }
                    actual.put("SegmentLength", String.format(java.util.Locale.ROOT, "%03d", raw.length()));
                    raw.setLength(0);
                    for (String field : fields) raw.append(actual.path(field).textValue()).append('\u001c');
                    actual.put("SerializedSegment", raw.toString());
                }
                if (fixture.has("scalar118Wire")) {
                    String field = fixture.path("scalar118Wire").path("field").asText();
                    if (!List.of("CardTableData", "FuelVolumeData").contains(field)) throw new IOException("Unknown authored118 scalar source field");
                    int length = fixture.path("scalar118Wire").path("length").asInt(-1);
                    if (length < 0 || length > 3601) throw new IOException("Authored118 scalar boundary must be0-3601");
                    boolean request = "FuelVolumeData".equals(field);
                    payload.put("messageFamily", request ? "Proprietary Data Load Request" : "Proprietary Data Load Response");
                    ObjectNode segment = authoredObject(payload.path("actualMessage").path("Load"), "scalar118 Load");
                    segment.put("PromptCode", request ? "905" : "902").put(field, "V".repeat(length));
                    var fields = List.of("SegmentType", "SegmentLength", "SequenceNumber", "InformationByte", "TerminalIdentifier",
                            "PromptCode", "PromptCodePending", "DeviceCardTableVersion", "CardTableLoadVersion", "CardTableType",
                            "LoadControlKey", "HostDiscountTimestamp", "BlockNumber", field);
                    StringBuilder raw = new StringBuilder();
                    for (String name : fields) {
                        if (!segment.path(name).isTextual()) throw new IOException("Authored scalar118 wire field missing: " + name);
                        raw.append(segment.path(name).textValue());
                        if (request) raw.append('\u001c');
                    }
                    String declared = String.format(java.util.Locale.ROOT, "%04d", raw.length());
                    segment.put("SegmentLength", declared);
                    raw.setLength(0);
                    for (String name : fields) {
                        raw.append(segment.path(name).textValue());
                        if (request) raw.append('\u001c');
                    }
                    segment.put("SerializedSegment", raw.toString());
                }
                if (fixture.has("capkFileLength")) {
                    int length = fixture.path("capkFileLength").asInt(-1);
                    if (length < 0 || length > 10000) throw new IOException("Authored CAPK file boundary must be0-10000");
                    ObjectNode history = authoredObject(payload.path("actualEvidence").path("history").path("capk"), "CAPK history");
                    JsonNode template = history.path("exchange").path("events").path(0).deepCopy();
                    var blocks = history.putArray("blocks");
                    var exchange = authoredObject(history.path("exchange"), "CAPK exchange");
                    var events = exchange.putArray("events");
                    int count = Math.max(1, (length + 998) / 999);
                    for (int index = 0; index < count; index++) {
                        int bytes = Math.min(999, Math.max(0, length - index * 999));
                        String data = "K".repeat(bytes), block = String.format(java.util.Locale.ROOT, "%03d", index);
                        String sequence = String.format(java.util.Locale.ROOT, "%06d", 123456 + index);
                        blocks.addObject().put("length", String.format(java.util.Locale.ROOT, "%03d", bytes)).put("data", data);
                        ObjectNode event = authoredObject(template.deepCopy(), "CAPK event");
                        event.put("sentAt", java.time.Instant.parse("2026-10-12T12:00:00Z").plusSeconds(index * 2L).toString());
                        event.put("receivedAt", java.time.Instant.parse("2026-10-12T12:00:00Z").plusSeconds(index * 2L + 1).toString());
                        authoredObject(event.path("request").path("elements"), "CAPK request elements").put("86", sequence).put("11", block);
                        authoredObject(event.path("response").path("elements"), "CAPK response elements").put("86", sequence).put("11", block)
                                .put("83", index == count - 1 ? "L" : "M").put("193", String.format(java.util.Locale.ROOT, "%03d", bytes)).put("194", data);
                        events.add(event);
                    }
                    exchange.put("currentRequestIndex", count - 1);
                    history.put("assembledFile", "K".repeat(length)).put("storedFileAfter", "K".repeat(length));
                    authoredObject(payload.path("actualPayload"), "current actual CAPK").put("SequenceNumber", String.format(java.util.Locale.ROOT, "%06d", 123456 + count - 1))
                            .put("BlockNumber", String.format(java.util.Locale.ROOT, "%03d", count - 1));
                }
                if (fixture.has("capkError")) {
                    String error = fixture.path("capkError").asText();
                    if (!List.of("RQST/CURRENT HASH MATCH", "BLOCK NBR NOT NUMERIC", "BLOCK NBR NOT 000-xxx").contains(error))
                        throw new IOException("Authored CAPK error must be source listed");
                    JsonNode capture = payload.path("actualEvidence").path("history").path("capk");
                    if (!capture.isObject()) throw new IOException("Authored CAPK capture required");
                    ObjectNode history = (ObjectNode) capture;
                    history.put("approved", false).put("responseData", error).put("displayedError", error)
                            .set("storedFileAfter", history.path("storedFileBefore").deepCopy());
                    JsonNode events = history.path("exchange").path("events");
                    if (!events.isArray() || events.isEmpty() || !events.get(events.size() - 1).path("response").path("elements").isObject())
                        throw new IOException("Authored captured CAPK terminal response required");
                    ((ObjectNode) events.get(events.size() - 1).path("response").path("elements")).put("83", "X")
                            .put("194", error).put("193", String.format(java.util.Locale.ROOT, "%03d", error.length()));
                }
                if (fixture.has("siteWireMode")) {
                    JsonNode current = payload.path("actualMessage").path("Load");
                    if (fixture.has("siteFirstBlockDataLength")) {
                        int length = fixture.path("siteFirstBlockDataLength").asInt(-1);
                        if (length < 1 || length > 3601) throw new IOException("Authored903 first data boundary must be1-3601");
                        ObjectNode history = authoredObject(payload.path("actualEvidence").path("history").path("proprietaryLoad").path("siteConfiguration"), "site history");
                        String data = "A".repeat(length);
                        authoredObject(history.path("requestBlocks").path(0).path("request"), "first site request").put("SiteConfigurationData", data);
                        authoredObject(history.path("requestBlocks").path(1).path("request"), "second site request").put("SiteConfigurationData", "");
                        history.putArray("changes").add("OLD").add(data);
                        history.putArray("sentData").add(data);
                    }
                    var segments = new ArrayList<JsonNode>();
                    segments.add(current);
                    for (JsonNode block : payload.path("actualEvidence").path("history").path("proprietaryLoad").path("siteConfiguration").path("requestBlocks"))
                        if (block.path("request").isObject()) segments.add(block.path("request"));
                    for (JsonNode item : segments) {
                        if (!item.isObject()) throw new IOException("Authored903 request must be object");
                        ObjectNode segment = (ObjectNode) item;
                        var fields = List.of("SegmentType", "SegmentLength", "SequenceNumber", "InformationByte", "TerminalIdentifier",
                                "PromptCode", "PromptCodePending", "DeviceCardTableVersion", "CardTableLoadVersion", "CardTableType",
                                "LoadControlKey", "HostDiscountTimestamp", "BlockNumber", "SiteConfigurationData");
                        for (String field : fields) if (!segment.path(field).isTextual()) throw new IOException("Authored903 explicit text field required: " + field);
                        segment.put("SegmentLength", "0000");
                        String wire = fields.stream().map(field -> segment.path(field).asText() + "\u001c").collect(java.util.stream.Collectors.joining());
                        segment.put("SegmentLength", String.format(java.util.Locale.ROOT, "%04d", wire.length()));
                        wire = fields.stream().map(field -> segment.path(field).asText() + "\u001c").collect(java.util.stream.Collectors.joining());
                        segment.put("SerializedSegment", wire);
                    }
                    String mode = fixture.path("siteWireMode").asText();
                    if ("MISSING-WIRE".equals(mode)) ((ObjectNode) current).remove("SerializedSegment");
                    else if (!"VALID".equals(mode)) throw new IOException("Unknown authored903 wire mode");
                }
                if (fixture.has("receiptWireMode")) {
                    JsonNode observed = payload.path("actualMessage").path("Load");
                    if (!observed.isObject()) throw new IOException("Authored901 actual capture required");
                    ObjectNode segment = (ObjectNode) observed;
                    int count = fixture.path("receiptLineCount").asInt(1), length = fixture.path("receiptLineLength").asInt(20);
                    if (count < 1 || count > 11 || length < 1 || length > 41) throw new IOException("Authored receipt bounds required");
                    var lines = segment.putArray("ReceiptTextLines");
                    for (int index = 0; index < count; index++) {
                        int observedLength = index == 0 ? fixture.path("receiptFirstLineLength").asInt(length) : length;
                        if (observedLength < 1 || observedLength > 41) throw new IOException("Authored first receipt line bounds required");
                        lines.addObject().put("ReceiptTextDataLength", String.format(java.util.Locale.ROOT, "%02d", observedLength))
                                .put("ReceiptTextData", "R".repeat(observedLength));
                    }
                    segment.put("NumberOfReceiptTextLines", String.format(java.util.Locale.ROOT, "%02d", count));
                    var fields = new ArrayList<>(List.of("SegmentType", "SegmentLength", "SequenceNumber", "InformationByte", "TerminalIdentifier",
                            "PromptCode", "PromptCodePending", "DeviceCardTableVersion", "CardTableLoadVersion", "CardTableType",
                            "LoadControlKey", "HostDiscountTimestamp", "BlockNumber", "StartDate", "StartTime", "EndDate", "EndTime", "NumberOfReceiptTextLines"));
                    String mode = fixture.path("receiptWireMode").asText();
                    if ("WRONG-ORDER".equals(mode)) java.util.Collections.swap(fields, 13, 14);
                    String suffix = java.util.stream.StreamSupport.stream(lines.spliterator(), false)
                            .map(line -> line.path("ReceiptTextDataLength").asText() + line.path("ReceiptTextData").asText())
                            .collect(java.util.stream.Collectors.joining());
                    segment.put("SegmentLength", "0000");
                    String encoded = fields.stream().map(field -> segment.path(field).asText()).collect(java.util.stream.Collectors.joining()) + suffix;
                    segment.put("SegmentLength", String.format(java.util.Locale.ROOT, "%04d", encoded.length()));
                    encoded = fields.stream().map(field -> segment.path(field).asText()).collect(java.util.stream.Collectors.joining()) + suffix;
                    if ("EMBEDDED-FS".equals(mode)) encoded += "\u001c";
                    if ("NONASCII".equals(mode)) encoded = encoded.substring(0, encoded.length() - 1) + "\u00e9";
                    segment.put("SerializedSegment", encoded);
                    if ("WRONG-LENGTH".equals(mode)) segment.put("SegmentLength", "0001");
                    if ("MISSING-WIRE".equals(mode)) segment.remove("SerializedSegment");
                    if (!List.of("VALID", "WRONG-ORDER", "EMBEDDED-FS", "NONASCII", "WRONG-LENGTH", "MISSING-WIRE").contains(mode))
                        throw new IOException("Unknown authored901 wire mode");
                }
                if (fixture.has("discountWireMode")) {
                    JsonNode observed = payload.has("productPayload")
                            ? payload.path("productPayload").path("context").path("hostDiscount").path("receivedLoad")
                            : payload.path("actualMessage").path("Load");
                    if (!observed.isObject()) throw new IOException("Authored904 actual capture required");
                    ObjectNode segment = (ObjectNode) observed;
                    if (fixture.has("discountRecordCount")) {
                        JsonNode count = fixture.get("discountRecordCount");
                        if (!count.isIntegralNumber() || count.asInt() < 0 || count.asInt() > 45) throw new IOException("Authored904 count must be0-45");
                        JsonNode prototype = segment.path("Discounts").path(0).deepCopy();
                        if (!prototype.isObject()) throw new IOException("Authored904 prototype record required");
                        var array = segment.putArray("Discounts");
                        for (int index = 0; index < count.asInt(); index++) array.add(prototype.deepCopy());
                        segment.put("NumberOfDiscounts", String.format(java.util.Locale.ROOT, "%02d", count.asInt()));
                    }
                    var core = List.of("SegmentType", "SegmentLength", "SequenceNumber", "InformationByte", "TerminalIdentifier",
                            "PromptCode", "PromptCodePending", "DeviceCardTableVersion", "CardTableLoadVersion", "CardTableType",
                            "LoadControlKey", "HostDiscountTimestamp", "BlockNumber");
                    StringBuilder suffix = new StringBuilder(segment.path("NewHostDiscountTimestamp").asText())
                            .append(segment.path("NumberOfDiscounts").asText());
                    var recordFields = new ArrayList<>(com.coreauth.validator.canonical.Segment118PayloadValidator.DISCOUNT_FIELDS);
                    String mode = fixture.path("discountWireMode").asText();
                    if ("WRONG-ORDER".equals(mode)) java.util.Collections.swap(recordFields, 0, 1);
                    for (JsonNode record : segment.path("Discounts")) for (String field : recordFields) {
                        if (!record.path(field).isTextual()) throw new IOException("Authored904 fields must be text");
                        suffix.append(record.path(field).textValue());
                    }
                    segment.put("SegmentLength", "0000");
                    String encoded = core.stream().map(field -> segment.path(field).asText()).collect(java.util.stream.Collectors.joining()) + suffix;
                    segment.put("SegmentLength", String.format(java.util.Locale.ROOT, "%04d", encoded.length()));
                    encoded = core.stream().map(field -> segment.path(field).asText()).collect(java.util.stream.Collectors.joining()) + suffix;
                    if ("EMBEDDED-FS".equals(mode)) encoded = encoded.substring(0, 127) + "\u001c" + encoded.substring(127);
                    if ("NONASCII".equals(mode)) encoded = encoded.substring(0, encoded.length() - 1) + "\u00e9";
                    segment.put("SerializedSegment", encoded);
                    if ("WRONG-LENGTH".equals(mode)) segment.put("SegmentLength", "0001");
                    if ("MISSING-WIRE".equals(mode)) segment.remove("SerializedSegment");
                    if (!List.of("VALID", "WRONG-ORDER", "EMBEDDED-FS", "NONASCII", "WRONG-LENGTH", "MISSING-WIRE").contains(mode))
                        throw new IOException("Unknown authored904 wire mode");
                }
                if (fixture.has("legacyWireMode")) {
                    String id = payload.path("segment").asText();
                    JsonNode observed = payload.path("actualMessage").path("Observed");
                    if (!observed.isObject()) throw new IOException("Authored observed legacy wire prototype required");
                    ObjectNode segment = (ObjectNode) observed;
                    List<String> fields = switch (id) {
                        case "101" -> List.of("SegmentType", "SegmentLength", "Odometer", "VehicleNumber", "JobNumber",
                                "DriverIdentificationNumber", "FleetEmployeeNumber", "LicenseNumber", "JobId",
                                "DepartmentNumber", "CustomerData", "UserId", "VehicleIdNumber");
                        case "104" -> List.of("SegmentType", "SegmentLength", "PurchaseCode", "PcTaxAmount", "PcFreightAmount",
                                "PcDutyAmount", "ShipToCountryCode", "ShipToPostalCode", "ShipFromPostalCode", "DirectMarketingInvoiceNumber");
                        case "105" -> List.of("SegmentType", "SegmentLength", "InformationByte", "TerminalID", "PromptCode",
                                "EmployeeNumber", "Password", "TotalsDate", "HardwareVersion", "SoftwareVersion", "FirmwareVersion",
                                "SequenceNumber", "CurrencyCode", "GrandTotal");
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
                        default -> throw new IOException("Unsupported authored separated wire prototype");
                    };
                    if ("101".equals(id)) {
                        int lastTag = 0;
                        for (int index = 1; index <= 5; index++) if (segment.has("FleetTag" + index)) lastTag = index;
                        if (lastTag > 0) {
                            fields = new ArrayList<>(fields);
                            for (int index = 1; index <= lastTag; index++) fields.add("FleetTag" + index);
                        }
                    }
                    if (fixture.has("totalsBucketCount")) {
                        int count = fixture.path("totalsBucketCount").asInt();
                        if (!"105".equals(id) || !fixture.path("totalsBucketCount").isIntegralNumber() || count < 0 || count > 19)
                            throw new IOException("Authored totals bucket count outside0-19");
                        List<String> labels = List.of("CC  ", "TE  ", "DS  ", "AO  ", "DB  ", "FL  ", "CS  ", "PR  ", "CK  ",
                                "EF  ", "EC  ", "SV1 ", "SV2 ", "SV3 ", "SV4 ", "ECA ", "EWIC", "EK  ");
                        var buckets = segment.putArray("CategoryTotals");
                        for (int index = 0; index < count; index++) buckets.addObject()
                                .put("CardLabel", labels.get(index % 18)).put("CardTypeTotalCount", "00001").put("CardTypeTotalAmount", "00000001");
                    }
                    if (fixture.has("legacyValueLength")) {
                        int size = fixture.path("legacyValueLength").asInt();
                        if (!fixture.path("legacyValueLength").isIntegralNumber() || size < 1 || size > 151)
                            throw new IOException("Authored legacy wire boundary value outside1-151");
                        String field = switch (id) { case "101" -> "CustomerData"; case "104" -> "PurchaseCode";
                            case "105" -> "TerminalID"; case "108" -> "LoyaltyTrack2Data"; case "109" -> "TextData";
                            case "110" -> "CustomerLastName"; default -> "ExtendedMicrData"; };
                        segment.put(field, "A".repeat(size));
                        if ("109".equals(id)) segment.put("TextDataLength", String.format(java.util.Locale.ROOT, "%03d", size));
                    }
                    String mode = fixture.path("legacyWireMode").asText();
                    List<String> wireFields = new ArrayList<>(fields);
                    if ("WRONG-ORDER".equals(mode)) java.util.Collections.swap(wireFields, 2, 3);
                    boolean terminal = !"NO-TERMINAL-FS".equals(mode);
                    int length = fields.size() - 1 + (terminal ? 1 : 0);
                    for (String field : fields) {
                        if (!segment.path(field).isTextual()) throw new IOException("Authored legacy wire values must be explicit text");
                        length += segment.path(field).textValue().length();
                    }
                    StringBuilder suffix = new StringBuilder();
                    if ("105".equals(id)) {
                        for (JsonNode bucket : segment.path("CategoryTotals")) for (String field : List.of("CardLabel", "CardTypeTotalCount", "CardTypeTotalAmount"))
                            suffix.append(bucket.path(field).asText());
                        length += suffix.length();
                    }
                    segment.put("SegmentLength", String.format(java.util.Locale.ROOT, "%03d", length));
                    String raw = wireFields.stream().map(field -> segment.path(field).textValue())
                            .collect(java.util.stream.Collectors.joining("\u001c")) + (terminal ? "\u001c" : "") + suffix;
                    if ("EMPTY-COLLAPSE".equals(mode)) raw = raw.replace("\u001c\u001c", "\u001c");
                    if ("FS-AFTER-13-MISSING".equals(mode)) {
                        if (!"108".equals(id)) throw new IOException("Authored field13-FS mutation belongs to108");
                        raw = raw.replace(segment.path("LoyaltyTrack2Data").asText() + "\u001c",
                                segment.path("LoyaltyTrack2Data").asText());
                    }
                    if ("NONASCII".equals(mode)) raw = raw.substring(0, raw.length() - 1) + "\u00e9";
                    segment.put("SerializedSegment", raw);
                    if ("MISSING-WIRE".equals(mode)) segment.remove("SerializedSegment");
                    if ("MISSING-FIRST-TAG".equals(mode)) segment.remove("FleetTag1");
                    if ("MISSING-FIELD".equals(mode)) segment.remove(switch (id) { case "101" -> "JobNumber"; case "104" -> "PcDutyAmount";
                        case "105", "109" -> "Password"; case "108" -> "CouponId"; case "110" -> "DriversLicense"; default -> "EcaProductCode"; });
                    if ("WRONG-LENGTH".equals(mode)) segment.put("SegmentLength", "001");
                    if (!List.of("VALID", "NO-TERMINAL-FS", "WRONG-ORDER", "EMPTY-COLLAPSE", "FS-AFTER-13-MISSING", "MISSING-FIRST-TAG", "NONASCII",
                            "MISSING-WIRE", "MISSING-FIELD", "WRONG-LENGTH").contains(mode))
                        throw new IOException("Unknown authored wire mutation");
                }
                if (fixture.has("proprietaryBodyLength")) {
                    int size = fixture.path("proprietaryBodyLength").asInt();
                    if (!fixture.path("proprietaryBodyLength").isIntegralNumber() || size < 1 || size > 3601)
                        throw new IOException("Authored proprietary body boundary outside1-3601");
                    JsonNode node = payload.path("actualMessage").path("Load");
                    if (!node.isObject() || !"905".equals(node.path("PromptCode").asText())) throw new IOException("Authored905 prototype required");
                    ObjectNode load = (ObjectNode) node;
                    load.put("LoadControlKey", "A".repeat(60)).put("FuelVolumeData", "A".repeat(size));
                    load.put("SegmentLength", String.format(java.util.Locale.ROOT, "%04d", 197 + size));
                    StringBuilder raw = new StringBuilder();
                    for (String name : List.of("SegmentType", "SegmentLength", "SequenceNumber", "InformationByte", "TerminalIdentifier",
                            "PromptCode", "PromptCodePending", "DeviceCardTableVersion", "CardTableLoadVersion", "CardTableType",
                            "LoadControlKey", "HostDiscountTimestamp", "BlockNumber", "FuelVolumeData"))
                        raw.append(load.path(name).asText()).append('\u001c');
                    load.put("SerializedSegment", raw.toString());
                }
                if (fixture.has("printValueLength")) {
                    int size = fixture.path("printValueLength").asInt();
                    if (!fixture.path("printValueLength").isIntegralNumber() || size < 0 || size > 1000)
                        throw new IOException("Authored print wire boundary control outside0-1000");
                    JsonNode sourceSegment = payload.path("actualMessage").path("Print");
                    if (!sourceSegment.isObject()) throw new IOException("Print boundary prototype required");
                    ObjectNode print = (ObjectNode) sourceSegment;
                    String value = "A".repeat(size), length = String.format(java.util.Locale.ROOT, "%04d", size + 9);
                    print.put("PrintData", value).put("SegmentLength", length).put("wireFormat", "120\u001c" + length + "\u001c" + value);
                }
                if (fixture.has("variableValueLength")) {
                    int size = fixture.path("variableValueLength").asInt();
                    if (!fixture.path("variableValueLength").isIntegralNumber() || size < 1 || size > 985)
                        throw new IOException("Authored variable wire boundary control outside1-985");
                    JsonNode sourceSegment = payload.path("actualMessage").path("Information");
                    if (!sourceSegment.isObject()) throw new IOException("Variable boundary prototype required");
                    ObjectNode segment = (ObjectNode) sourceSegment;
                    String length = String.format(java.util.Locale.ROOT, "%03d", 15 + size);
                    segment.put("SegmentLength", length);
                    segment.putArray("repetitions").addObject().put("VariableInformationIndicator", "001")
                            .put("VariableInformationLength", String.format(java.util.Locale.ROOT, "%03d", size)).put("VariableInformation", "A".repeat(size));
                    segment.put("SerializedSegment", "111\u001c" + length + "\u001c001" + String.format(java.util.Locale.ROOT, "%03d", size) + "A".repeat(size) + "\u001c");
                }
                if (fixture.has("merchantCardCount")) {
                    int size = fixture.path("merchantCardCount").asInt();
                    if (!fixture.path("merchantCardCount").isIntegralNumber() || size < 1 || size > 100)
                        throw new IOException("Authored merchant boundary control outside1-100");
                    JsonNode sourceSegment = payload.path("actualPayload");
                    if (!sourceSegment.isObject()) throw new IOException("Merchant boundary prototype required");
                    ObjectNode segment = (ObjectNode) sourceSegment;
                    segment.put("MerchantName", "M".repeat(24)).put("AddressLine1", "A".repeat(24)).put("AddressLine2", "CITY         CA 12345")
                            .put("NumberOfCardTypes", String.format(java.util.Locale.ROOT, "%02d", size));
                    var cards = segment.putArray("CardTypes");
                    for (int i = 0; i < size; i++) cards.add("001");
                    segment.put("SerializedSegment", "#" + "M".repeat(24) + segment.path("StoreNumber").asText() + "A".repeat(24)
                            + "CITY         CA 12345" + segment.path("MerchantPhoneNumber").asText() + segment.path("NumberOfCardTypes").asText()
                            + "001".repeat(size) + "~");
                }
                if (fixture.has("raw118Length")) {
                    int length = fixture.path("raw118Length").asInt(-1);
                    if (length < 0 || length > 3801) throw new IOException("Authored118 whole-wire length must be0-3801");
                    authoredObject(payload.path("actualMessage").path("Load"), "whole118 boundary Load")
                            .put("SerializedSegment", "X".repeat(length));
                }
                var postOverrides = fixture.path("postWireOverrides").fields();
                while (postOverrides.hasNext()) {
                    var override = postOverrides.next();
                    edit(payload, override.getKey(), override.getValue().deepCopy());
                }
                for (JsonNode omitted : fixture.path("postWireOmitPaths")) edit(payload, omitted.asText(), null);
                expanded.set("payload", payload);
            }
            fixtures.add(expanded);
        }
        for (JsonNode profile : extension.path("legacyActualProfiles")) {
            String id = profile.path("segment").asText();
            ObjectNode message;
            if (profile.has("sourceFile")) message = new com.fasterxml.jackson.databind.ObjectMapper()
                    .readTree(Atl105Paths.root().resolve(profile.path("sourceFile").asText()).toFile()).path(profile.path("root").asText()).deepCopy();
            else message = profile.path("message").deepCopy();
            if (message == null || !message.isObject()) throw new IOException("Authored legacy actual message required");
            String childName = null;
            var children = message.fields();
            while (children.hasNext()) {
                var child = children.next();
                if (id.equals(child.getValue().path("SegmentType").asText())) {
                    if (childName != null) throw new IOException("Ambiguous authored legacy prototype");
                    childName = child.getKey();
                }
            }
            if (childName == null) throw new IOException("Authored legacy prototype lacks source segment");
            for (String mutation : List.of("VALID", "WRONG-LENGTH", "NONTEXT", "MISSING-LENGTH")) {
                ObjectNode actual = message.deepCopy();
                JsonNode child = actual.get(childName);
                if (!child.isObject()) throw new IOException("Authored legacy segment must be object");
                if ("WRONG-LENGTH".equals(mutation)) ((ObjectNode) child).put("SegmentLength", "BAD");
                if ("NONTEXT".equals(mutation)) ((ObjectNode) child).put("SegmentLength", 1);
                if ("MISSING-LENGTH".equals(mutation)) ((ObjectNode) child).remove("SegmentLength");
                ObjectNode payload = JsonNodeFactory.instance.objectNode().put("segment", id).put("messageFamily", profile.path("family").asText());
                payload.set("actualMessage", actual);
                ObjectNode expanded = JsonNodeFactory.instance.objectNode().put("id", "LEGACY-" + id + "-" + mutation)
                        .put("ruleId", profile.path("ruleId").asText()).put("executionOnly", true)
                        .put("expectedStatus", "VALID".equals(mutation) || "118".equals(id) && "MISSING-LENGTH".equals(mutation)
                                ? "REVIEW_REQUIRED" : "INVALID");
                expanded.set("payload", payload);
                fixtures.add(expanded);
            }
        }
        for (JsonNode mapping : extension.path("actualMappings")) {
            String id = mapping.path("segment").asText();
            JsonNode base = bases.get(id);
            if (base == null || !base.path("elements").isObject()) throw new IOException("Missing numbered fixture for actual projection " + id);
            ObjectNode actual = JsonNodeFactory.instance.objectNode();
            String headerName = mapping.path("headerName").asText("SegmentType");
            actual.set(headerName, base.path("elements").path(mapping.path("headerNumber").asText("85")).deepCopy());
            actual.set("SegmentLength", base.path("elements").path("84").deepCopy());
            var fields = mapping.path("fields").fields();
            while (fields.hasNext()) {
                var field = fields.next();
                if (!field.getValue().isTextual() || !base.path("elements").has(field.getKey())) throw new IOException("Invalid authored field projection " + id);
                actual.set(field.getValue().textValue(), base.path("elements").path(field.getKey()).deepCopy());
            }
            var direct = mapping.path("direct").fields();
            while (direct.hasNext()) {
                var field = direct.next();
                if (!field.getValue().isTextual() || !base.has(field.getKey())) throw new IOException("Missing authored structural projection " + id);
                actual.set(field.getValue().textValue(), base.path(field.getKey()).deepCopy());
            }
            if (mapping.has("encodedTables")) {
                if (!mapping.path("encodedTables").isTextual() || !base.path("tables").isArray()) throw new IOException("Invalid authored TLV projection " + id);
                StringBuilder encoded = new StringBuilder();
                for (JsonNode table : base.path("tables")) {
                    if (!table.path("id").isTextual() || !table.path("length").isTextual() || !table.path("value").isTextual()
                            || table.path("id").textValue().length() != mapping.path("tableIdWidth").asInt()) throw new IOException("Invalid authored TLV triple " + id);
                    encoded.append(table.path("id").textValue()).append(table.path("length").textValue()).append(table.path("value").textValue());
                }
                actual.put(mapping.path("encodedTables").textValue(), encoded.toString());
            }
            if (base.has("serializedSegment")) actual.set("SerializedSegment", base.path("serializedSegment").deepCopy());
            ObjectNode payload = JsonNodeFactory.instance.objectNode().put("segment", id).put("messageFamily", mapping.path("family").asText());
            payload.set("actualPayload", actual);
            ObjectNode evidence = payload.putObject("actualEvidence");
            for (String key : List.of("context", "history")) if (base.has(key)) evidence.set(key, base.path(key).deepCopy());
            String rule = mapping.path("ruleId").asText();
            String headerScope = mapping.path("headerScope").asText("element-85");
            fixtures.add(fixture("ACTUAL-" + id + "-VALID", rule, headerScope, "REVIEW_REQUIRED", "CHECKS_PASSED", payload));
            ObjectNode wrong = payload.deepCopy();
            ((ObjectNode) wrong.path("actualPayload")).put(headerName, "999");
            fixtures.add(fixture("ACTUAL-" + id + "-WRONG-TYPE", rule, headerScope, "INVALID", "INVALID", wrong));
            ObjectNode missing = payload.deepCopy();
            ((ObjectNode) missing.path("actualPayload")).remove("SegmentLength");
            ((ObjectNode) missing.path("actualEvidence")).put("SegmentLength", base.path("elements").path("84").asText());
            ObjectNode missingFixture = fixture("ACTUAL-" + id + "-MISSING-LENGTH", rule, mapping.path("lengthScope").asText("length-width-range"), "INVALID", "INVALID", missing);
            missingFixture.put("targetRuleId", mapping.path("lengthRuleId").asText(rule));
            fixtures.add(missingFixture);
        }
        for (JsonNode probe : extension.path("predicateProbes")) {
            JsonNode base = bases.get(probe.path("segment").asText());
            if (base == null || !base.isObject()) throw new IOException("Missing source-local probe prototype");
            ObjectNode payload = base.deepCopy();
            if (probe.has("profile")) {
                if (!probe.path("profile").isTextual() || !extension.path("profiles").path(probe.path("profile").textValue()).isObject())
                    throw new IOException("Missing authored probe profile");
                JsonNode profile = extension.path("profiles").path(probe.path("profile").textValue());
                var fields = profile.path("elements").fields();
                while (fields.hasNext()) {
                    var field = fields.next();
                    ((ObjectNode) payload.path("elements")).set(field.getKey(), field.getValue().deepCopy());
                }
                for (String key : List.of("context", "history", "tables"))
                    if (profile.has(key)) payload.set(key, profile.path(key).deepCopy());
            }
            var changes = probe.path("elements").fields();
            while (changes.hasNext()) {
                var field = changes.next();
                ((ObjectNode) payload.path("elements")).set(field.getKey(), field.getValue().deepCopy());
            }
            for (String key : List.of("context", "history", "tables", "serializedSegment", "serializedSegmentHex"))
                if (probe.has(key)) payload.set(key, probe.path(key).deepCopy());
            JsonNode contextName = extension.path("predicateContexts").get(probe.path("id").asText());
            if (contextName != null) {
                if (!contextName.isTextual() || !extension.path(contextName.textValue()).isObject())
                    throw new IOException("Missing authored predicate context");
                payload.set("context", extension.path(contextName.textValue()).deepCopy());
            }
            var overrides = probe.path("overrides").fields();
            while (overrides.hasNext()) {
                var field = overrides.next();
                edit(payload, field.getKey(), field.getValue());
            }
            for (JsonNode omitted : probe.path("omitPaths")) {
                if (!omitted.isTextual()) throw new IOException("Fixture omission path must be text");
                edit(payload, omitted.textValue(), null);
            }
            ObjectNode expanded = fixture(probe.path("id").asText(), probe.path("ruleId").asText(), probe.path("scope").asText(),
                    probe.path("expectedStatus").asText(), probe.path("expectedTargetStatus").asText(), payload);
            if (probe.has("targetRuleId")) expanded.set("targetRuleId", probe.path("targetRuleId").deepCopy());
            fixtures.add(expanded);
        }
        for (JsonNode probe : extension.path("productProbes")) {
            String id = probe.path("segment").asText();
            JsonNode prototype = null;
            for (JsonNode original : observations.path("productExecutions"))
                if (id.equals(original.path("payload").path("segment").asText())) {
                    if (prototype != null) throw new IOException("Ambiguous actual product prototype " + id);
                    prototype = original.path("payload");
                }
            if (prototype == null || !prototype.isObject()) throw new IOException("Missing actual product prototype " + id);
            ObjectNode payload = prototype.deepCopy();
            JsonNode product = payload.path("productPayload").path("102".equals(id) ? "Product Code Data Segment" : "Adjusted Product Code Data Segment");
            JsonNode standard = payload.path("productPayload").path("Standard Segment");
            if (!product.isObject() || !standard.isObject()) throw new IOException("Malformed authored product prototype");
            for (String key : List.of("productFields", "standardFields")) {
                var changes = probe.path(key).fields();
                while (changes.hasNext()) {
                    var field = changes.next();
                    ((ObjectNode) ("productFields".equals(key) ? product : standard)).set(field.getKey(), field.getValue().deepCopy());
                }
            }
            if (probe.has("context")) ((ObjectNode) payload.path("productPayload")).set("context", probe.path("context").deepCopy());
            for (JsonNode field : probe.path("omitStandardFields")) ((ObjectNode) standard).remove(field.asText());
            ObjectNode fixture = JsonNodeFactory.instance.objectNode().put("id", probe.path("id").asText()).put("executionOnly", true)
                    .put("ruleId", probe.path("ruleId").asText()).put("expectedStatus", probe.path("expectedStatus").asText());
            fixture.set("payload", payload);
            fixtures.add(fixture);
        }
        return List.copyOf(fixtures);
    }

    private static ObjectNode authoredObject(JsonNode value, String label) throws IOException {
        if (value instanceof ObjectNode object) return object;
        throw new IOException("Authored object required: " + label);
    }

    private static void edit(ObjectNode payload, String pointer, JsonNode value) throws IOException {
        int split = pointer.lastIndexOf('/');
        if (!pointer.startsWith("/") || split < 0 || split == pointer.length() - 1 || pointer.contains("~"))
            throw new IOException("Invalid authored fixture pointer");
        JsonNode parent = payload.at(pointer.substring(0, split));
        if (!parent.isObject()) throw new IOException("Authored fixture pointer parent is not an object");
        if (value == null) ((ObjectNode) parent).remove(pointer.substring(split + 1));
        else ((ObjectNode) parent).set(pointer.substring(split + 1), value.deepCopy());
    }

    private static ObjectNode fixture(String id, String rule, String scope, String status, String targetStatus, ObjectNode payload) {
        ObjectNode fixture = JsonNodeFactory.instance.objectNode().put("id", id).put("ruleId", rule)
                .put("targetScope", scope).put("expectedStatus", status).put("expectedTargetStatus", targetStatus);
        fixture.set("payload", payload);
        return fixture;
    }
}
