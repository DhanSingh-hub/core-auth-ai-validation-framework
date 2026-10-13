package com.coreauth.validator.canonical;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Finding;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Result;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Explicit actual-field adapter: metadata supplies observed context/history, never missing segment values or wire. */
public final class Chapter12ActualPayloadAdapter {
    public static final Set<String> SEGMENTS = Set.of("103", "115", "116", "123", "130", "131", "132", "134", "135", "136",
            "139", "140", "141", "142", "143", "145", "146", "148", "149", "150", "151", "152", "153", "155", "156",
            "100", "DL1", "DL2", "DL3", "DL4", "DL5", "DL6", "DL7", "DL8");
    private com.coreauth.validator.validation.Chapter13DataElementValidator elementOracle;
    public Chapter12ActualPayloadAdapter() { }
    public Chapter12ActualPayloadAdapter(com.coreauth.validator.validation.Chapter13DataElementValidator elementOracle) {
        this.elementOracle = java.util.Objects.requireNonNull(elementOracle);
    }
    private static final Map<String, String> STANDARD = Map.ofEntries(Map.entry("SegmentType", "85"), Map.entry("SegmentLength", "84"),
            Map.entry("InformationByte", "44"), Map.entry("TerminalIdentifier", "102"), Map.entry("PromptCode", "78"),
            Map.entry("AccountNumber", "2"), Map.entry("CardDiscretionaryBlockData", "12"), Map.entry("EncryptedPINBlockData", "33"),
            Map.entry("PumpLaneNumber", "79"), Map.entry("FuelPurchaseAmount", "41"), Map.entry("NonfuelAmount", "58"),
            Map.entry("TaxAmount", "99"), Map.entry("CashAmount", "17"), Map.entry("SequenceNumber", "86"),
            Map.entry("ApprovalNumber", "5"), Map.entry("LocalDateTime", "49"), Map.entry("PartialApprovalIndicator", "121"));
    private static final Map<String, Map<String, String>> DOWNLOAD = Map.of(
            "DL2", Map.of("DataTypeIndicator", "24", "DialStringType", "28", "EndOfDataIndicator", "34"),
            "DL3", Map.of("DataTypeIndicator", "24", "DayOfTheWeek", "25", "CurrentDate", "21", "CurrentTime", "22",
                    "CutTime", "23", "Password", "65", "EndOfDataIndicator", "34"),
            "DL4", Map.of("DataTypeIndicator", "24", "NewSoftwareVersion", "57", "SoftwareTerminalRecordID", "95",
                    "SoftwareLoadPhoneNumber", "91", "SoftwareLoadRequestDate", "92", "SoftwareLoadRequestTime", "93", "SoftwareLoadType", "94", "EndOfDataIndicator", "34"),
            "DL5", Map.of("DataTypeIndicator", "24", "NewSoftwareVersion", "57", "SoftwareTerminalRecordID", "95",
                    "SoftwareLoadIPURLAddress", "114", "SoftwareLoadRequestDate", "92", "SoftwareLoadRequestTime", "93", "SoftwareLoadType", "94", "EndOfDataIndicator", "34"),
            "DL6", Map.of("DataTypeIndicator", "24", "StartTime", "startTime", "EndTime", "endTime", "EndOfDataIndicator", "34"),
            "DL7", Map.of("DataTypeIndicator", "24", "SegmentLength", "84", "DownloadData", "232"));
    private static final Map<String, String> COMMON = Map.of("SegmentType", "85", "SegmentLength", "84");
    private static final Map<String, String> EMV = Map.of("CAPublicKeyFileChecksum", "187", "EMVCardSequenceNumber", "188",
            "EMVChipDataLength", "189", "EMVChipData", "190");
    private static final Map<String, Map<String, String>> FIELDS = Map.ofEntries(
            Map.entry("103", Map.of("ClerkID", "18", "VoucherID", "109", "WICDiscountData", "153", "WICPurchaseData", "154", "EBTData", "164")),
            Map.entry("123", Map.of("CAVVRevisedFormat", "195", "TokenRequestorID", "196", "TokenPANSuffix", "197",
                    "CryptogramTokenData", "202", "SafeKeyData", "203", "SafeKeyResponse", "204", "TAVVCryptogram", "237", "TAVVResultCode", "238")),
            Map.entry("132", Map.of("SequenceNumber", "86", "TerminalIdentifier", "102", "LoadType", "48",
                    "HardwareVersion", "43", "SoftwareVersion", "96", "FirmwareVersion", "39", "CAPublicKeyFileChecksum", "187", "BlockNumber", "11")),
            Map.entry("134", Map.of("SettlementType", "198", "SignatureRequired", "199", "ReceiptCardDescription", "200")),
            Map.entry("135", Map.of("MonerisData", "213")), Map.entry("136", Map.of("MonerisData", "213")),
            Map.entry("149", Map.of("TerminalIdentifier", "102", "PriceData", "241")),
            Map.entry("150", Map.of("TerminalIdentifier", "102", "DeclineCode", "26")));
    private static final Map<String, String> MERCHANT = Map.of("DataTypeIndicator", "24", "MerchantName", "53", "StoreNumber", "98",
            "AddressLine1", "3", "AddressLine2", "4", "MerchantPhoneNumber", "54", "NumberOfCardTypes", "59", "EndOfDataIndicator", "34");
    private static final Map<String, String> BATCH_FIELDS = Map.of("TerminalIdentifier", "102", "SPDHHeader", "206",
            "MonerisTerminalIdentifier", "207", "MonerisMerchantID", "208", "BatchNumber", "214");
    private static final Map<String, String> BATCH_TOTALS = Map.of("NumberOfDebits", "217", "DebitDollarValue", "218",
            "NumberOfCredits", "219", "CreditDollarValue", "220", "NumberOfCorrections", "221", "CorrectionsDollarValue", "222");
    private static final Map<String, String> TABLE_DATA = Map.of("145", "EnhancedFleetData", "146", "EnhancedFleetData",
            "153", "NetworkTokenData", "156", "EVChargingData");

    public Result validate(JsonNode actual, String id, String family, JsonNode evidence) throws IOException {
        return validate(actual, id, family, evidence, null);
    }

    public Result validate(JsonNode actual, String id, String family, JsonNode evidence, JsonNode actualStandard) throws IOException {
        return validate(actual, id, family, evidence, actualStandard, null);
    }

    public Result validate(JsonNode actual, String id, String family, JsonNode evidence, JsonNode actualStandard, JsonNode actualProducts) throws IOException {
        return validate(actual, id, family, evidence, actualStandard, actualProducts, null);
    }

    public Result validate(JsonNode actual, String id, String family, JsonNode evidence, JsonNode actualStandard,
            JsonNode actualProducts, JsonNode actualMerchant) throws IOException {
        if (!SEGMENTS.contains(id)) throw new IllegalArgumentException("Unsupported actual Chapter12 segment " + id);
        List<Finding> mapping = new ArrayList<>();
        if (actual == null || !actual.isObject() || family == null || family.isBlank()) return new Result(Status.INVALID,
                List.of(new Finding("CH12-ACTUAL-INPUT", "actual-input", Status.INVALID, "Actual segment must be an object with an observed message family")), List.of());
        ObjectNode observation = JsonNodeFactory.instance.objectNode().put("specificationVersion", "2026-3")
                .put("segment", id).put("messageFamily", family).put("direction", family.endsWith("Response") ? "RESPONSE" : "REQUEST");
        ObjectNode fields = observation.putObject("elements");
        if ("100".equals(id) || DOWNLOAD.containsKey(id)) {
            Map<String, String> map = "100".equals(id) ? STANDARD : DOWNLOAD.get(id);
            map.forEach((name, number) -> copy(actual, name, fields, number));
            if ("100".equals(id)) {
                if (actual.has("DataSection")) observation.set("dataSection", actual.path("DataSection").deepCopy());
                if (actual.has("FieldNumber")) observation.set("fieldNumber", actual.path("FieldNumber").deepCopy());
                for (var alias : Map.of("NonFuelAmount", "58", "EncryptedPinBlockData", "33", "LocalDateandLocalTime", "49").entrySet()) {
                    if (!actual.has(alias.getKey())) continue;
                    if (fields.has(alias.getValue()) && !fields.path(alias.getValue()).equals(actual.path(alias.getKey())))
                        mapping.add(new Finding("CH12-ACTUAL-AMBIGUITY", "actual-mapping", Status.INVALID, "Contradictory actual Standard aliases"));
                    else fields.set(alias.getValue(), actual.path(alias.getKey()).deepCopy());
                }
                if (actual.path("PromptCode").isObject()) {
                    JsonNode prompt = actual.path("PromptCode");
                    if (prompt.path("TransactionType").isTextual() && prompt.path("TransactionType").textValue().matches("[A-Za-z0-9]")
                            && prompt.path("CardType").isTextual() && prompt.path("CardType").textValue().matches("[0-9]{3}"))
                        fields.put("78", prompt.path("TransactionType").textValue() + prompt.path("CardType").textValue());
                    else mapping.add(new Finding("CH12-ACTUAL-MAPPING", "actual-mapping", Status.INVALID, "Observed structured PromptCode requires one TransactionType and N3 CardType; no default component"));
                }
            }
            if (actual.has("SerializedSegment")) observation.set("serializedSegment", actual.path("SerializedSegment").deepCopy());
            if (evidence != null) {
                if (!evidence.isObject()) mapping.add(new Finding("CH12-ACTUAL-EVIDENCE", "actual-input", Status.INVALID, "Evidence must be an object"));
                else for (String key : List.of("context", "history")) if (evidence.has(key)) observation.set(key, evidence.path(key).deepCopy());
            }
            if (observation.path("context").isObject()) observation.set("qualifiers", observation.path("context").deepCopy());
            if ("DL6".equals(id)) {
                JsonNode declaredCards = observation.path("context").get("merchantCardTypes");
                if (observation.path("context").isObject()) ((ObjectNode) observation.path("context")).remove("merchantCardTypes");
                if (actualMerchant != null) {
                    if (!actualMerchant.isObject() || !actualMerchant.path("DataTypeIndicator").isTextual()
                            || !"#".equals(actualMerchant.path("DataTypeIndicator").textValue()) || !actualMerchant.path("CardTypes").isArray())
                        mapping.add(new Finding("CH12-ACTUAL-COMPANION", "actual-merchant-companion", Status.INVALID, "Observed DL1 CardTypes array required"));
                    else {
                        JsonNode cards = actualMerchant.path("CardTypes");
                        if (declaredCards != null && !declaredCards.equals(cards))
                            mapping.add(new Finding("CH12-ACTUAL-COMPANION", "actual-merchant-companion", Status.INVALID, "Metadata card inventory contradicts observed DL1"));
                        if (!observation.has("context")) observation.putObject("context");
                        if (observation.path("context").isObject()) ((ObjectNode) observation.path("context")).set("merchantCardTypes", cards.deepCopy());
                    }
                } else if (declaredCards != null) mapping.add(new Finding("CH12-ACTUAL-COMPANION", "actual-merchant-companion",
                        Status.REVIEW_REQUIRED, "Metadata cannot replace absent actual merchant CardTypes"));
            }
            if ("DL2".equals(id)) {
                ObjectNode dials = observation.putObject("dialStrings");
                Map<String, String> dial = Map.of("RedialCount", "82", "AccessCode", "1", "PauseIndicator", "66", "PhoneNumber", "75", "DialStringTerminator", "27");
                for (String name : List.of("Primary", "Secondary")) {
                    JsonNode source = actual.get(name);
                    if (source == null) continue;
                    if (!source.isObject()) mapping.add(new Finding("CH12-ACTUAL-MAPPING", "actual-mapping", Status.INVALID, "Observed dial occurrence must be an object"));
                    else {
                        ObjectNode target = dials.putObject(name.toLowerCase(java.util.Locale.ROOT));
                        dial.forEach((field, number) -> copy(source, field, target, number));
                        var names = source.fieldNames();
                        while (names.hasNext()) if (!dial.containsKey(names.next())) mapping.add(new Finding("CH12-ACTUAL-MAPPING", "actual-mapping", Status.REVIEW_REQUIRED, "Unmapped observed dial field"));
                    }
                }
            }
            if (!"100".equals(id) && actual.has("SegmentType"))
                mapping.add(new Finding("CH12-ACTUAL-MAPPING", "actual-mapping", Status.INVALID, "Download blocks use DataTypeIndicator, not numbered SegmentType"));
            if (!"100".equals(id) && !"DL7".equals(id) && actual.has("SegmentLength"))
                mapping.add(new Finding("CH12-ACTUAL-MAPPING", "actual-mapping", Status.INVALID, "Source block has no SegmentLength field"));
            Set<String> allowed = new java.util.HashSet<>(map.keySet());
            allowed.add("SerializedSegment");
            if ("100".equals(id)) allowed.addAll(Set.of("NonFuelAmount", "EncryptedPinBlockData", "LocalDateandLocalTime",
                    "DataSection", "FieldNumber"));
            if ("DL2".equals(id)) allowed.addAll(Set.of("Primary", "Secondary"));
            var names = actual.fieldNames();
            while (names.hasNext()) if (!allowed.contains(names.next())) mapping.add(new Finding("CH12-ACTUAL-MAPPING", "actual-mapping", Status.REVIEW_REQUIRED, "Observed field has no source mapping"));
            if (elementOracle == null) elementOracle = new com.coreauth.validator.validation.Chapter13DataElementValidator(com.coreauth.validator.paths.Atl105Paths.root());
            if ("100".equals(id)) mapping.addAll(new Segment100SerializationValidator().validateActual(observation, elementOracle));
            else mapping.addAll(new Chapter12SegmentPayloadValidator().validateDownload(observation, elementOracle).findings());
            var catalog = new com.fasterxml.jackson.databind.ObjectMapper().readTree(com.coreauth.validator.paths.Atl105Paths.ruleCatalog(id).toFile());
            var unassessed = new ArrayList<String>();
            for (JsonNode rule : catalog.path("rules"))
                if (mapping.stream().noneMatch(f -> f.ruleId().equals(rule.path("ruleId").asText()))) unassessed.add(rule.path("ruleId").asText());
            return new Result(mapping.stream().anyMatch(f -> f.status() == Status.INVALID) ? Status.INVALID : Status.REVIEW_REQUIRED, List.copyOf(mapping), List.copyOf(unassessed));
        }
        COMMON.forEach((name, number) -> copy(actual, name, fields, number));
        if (Set.of("130", "131").contains(id)) {
            EMV.forEach((name, number) -> copy(actual, name, fields, number));
            if (actual.has("EMVAdditionalInformation") && (actual.has("EMVAdditionalInformationIndicator")
                    || actual.has("EMVAdditionalInformationLength") || actual.has("EMVAdditionalInformationValue"))) {
                mapping.add(new Finding("CH12-ACTUAL-AMBIGUITY", "actual-mapping", Status.INVALID,
                        "Both additional-information array and scalar triple are ambiguous"));
            }
            if (actual.has("EMVAdditionalInformation")) observation.set("tables", actual.path("EMVAdditionalInformation").deepCopy());
            else if (actual.has("EMVAdditionalInformationIndicator") || actual.has("EMVAdditionalInformationLength")
                    || actual.has("EMVAdditionalInformationValue")) {
                ObjectNode table = observation.putArray("tables").addObject();
                copy(actual, "EMVAdditionalInformationIndicator", table, "id");
                copy(actual, "EMVAdditionalInformationLength", table, "length");
                copy(actual, "EMVAdditionalInformationValue", table, "value");
            } else observation.putArray("tables");
            if (actual.has("SerializedSegmentHex")) observation.set("serializedSegmentHex", actual.path("SerializedSegmentHex").deepCopy());
            if (actual.has("SerializedSegment")) mapping.add(new Finding("CH12-ACTUAL-WIRE", "actual-mapping",
                    actual.path("SerializedSegment").isTextual() ? Status.REVIEW_REQUIRED : Status.INVALID,
                    "Text serialization cannot certify packed chip bytes; supply actual SerializedSegmentHex"));
        } else if (TABLE_DATA.containsKey(id)) {
            String name = TABLE_DATA.get(id);
            JsonNode encoded = actual.get(name);
            if (actual.has("Tables") && encoded != null) mapping.add(new Finding("CH12-ACTUAL-AMBIGUITY", "actual-mapping",
                    Status.INVALID, "Both table array and encoded segment data are ambiguous"));
            if (actual.has("Tables")) observation.set("tables", actual.path("Tables").deepCopy());
            else decodeTables(encoded, observation, mapping, "156".equals(id) ? 2 : 3, name);
            if (encoded != null) observation.set("encodedTableData", encoded.deepCopy());
        } else if ("148".equals(id)) copy(actual, "AvailableProductInformation", fields, "240");
        else if ("115".equals(id)) copy(actual, "PrintData", fields, "152");
        else if (Set.of("151", "152").contains(id)) {
            copy(actual, "MarketBasketData", observation, "marketBasketData");
            copy(actual, "Datasets", observation, "datasets");
        } else if ("155".equals(id)) copy(actual, "Tables", observation, "tables");
        else if ("143".equals(id)) {
            observation.put("actualFieldAuthority", true);
            copy(actual, "NumberOfProducts", fields, "62");
            copy(actual, "TaxProducts", observation, "taxProducts");
        } else if ("DL8".equals(id)) {
            copy(actual, "DataTypeIndicator", fields, "24");
            copy(actual, "FloorLimitGroups", observation, "floorLimitGroups");
        } else if ("DL1".equals(id)) {
            MERCHANT.forEach((name, number) -> copy(actual, name, fields, number));
            copy(actual, "CardTypes", observation, "cardTypes");
        }
        FIELDS.getOrDefault(id, Map.of()).forEach((name, number) -> copy(actual, name, fields, number));
        if (Set.of("139", "140", "141", "142").contains(id)) {
            BATCH_FIELDS.forEach((name, number) -> copy(actual, name, fields, number));
            copy(actual, Set.of("139", "141").contains(id) ? "LanguageIndicator" : "ResponseDisplay", fields,
                    Set.of("139", "141").contains(id) ? "215" : "216");
            if (Set.of("140", "141").contains(id)) BATCH_TOTALS.forEach((name, number) -> copy(actual, name, fields, number));
            if ("142".equals(id)) copy(actual, "MAC", fields, "210");
        }
        if (actual.has("SerializedSegment")) observation.set("serializedSegment", actual.path("SerializedSegment").deepCopy());
        if (evidence != null) {
            if (!evidence.isObject()) mapping.add(new Finding("CH12-ACTUAL-EVIDENCE", "actual-input", Status.INVALID, "Evidence must be an object"));
            else for (String key : List.of("context", "history")) if (evidence.has(key)) observation.set(key, evidence.path(key).deepCopy());
        }
        JsonNode suppliedTransaction = observation.path("context").get("transactionElements");
        if (observation.path("context").isObject()) ((ObjectNode) observation.path("context")).remove("transactionElements");
        if (actualStandard != null && !actualStandard.isObject()) {
            mapping.add(new Finding("CH12-ACTUAL-COMPANION", "actual-parent-context", Status.INVALID, "Actual Standard Segment companion must be an object"));
        } else if (actualStandard != null) {
            ObjectNode transaction = JsonNodeFactory.instance.objectNode();
            copy(actualStandard, "SequenceNumber", transaction, "86");
            copy(actualStandard, "TerminalIdentifier", transaction, "102");
            if (suppliedTransaction != null && !suppliedTransaction.equals(transaction))
                mapping.add(new Finding("CH12-ACTUAL-COMPANION", "actual-parent-context", Status.INVALID,
                        "Metadata transaction context contradicts the actual Standard Segment; actual fields are authoritative"));
            if (!observation.has("context")) observation.putObject("context");
            if (observation.path("context").isObject()) ((ObjectNode) observation.path("context")).set("transactionElements", transaction);
        } else if (suppliedTransaction != null) mapping.add(new Finding("CH12-ACTUAL-COMPANION", "actual-parent-context", Status.REVIEW_REQUIRED,
                "Metadata cannot supply missing current actual transaction fields; one observed Standard Segment companion is required"));
        if (Set.of("143", "156").contains(id)) {
            JsonNode declaredCodes = observation.path("context").get("segment102ProductCodes");
            if (observation.path("context").isObject()) ((ObjectNode) observation.path("context")).remove("segment102ProductCodes");
            if (actualProducts != null) {
                var codes = ProductActualPayloadAdapter.observedProductCodes(actualProducts);
                if (codes == null)
                    mapping.add(new Finding("CH12-ACTUAL-COMPANION", "actual-product-companion", Status.INVALID, "Actual Segment102 product-code array or unambiguous flat entry required"));
                else {
                    if (declaredCodes != null && !declaredCodes.equals(codes))
                        mapping.add(new Finding("CH12-ACTUAL-COMPANION", "actual-product-companion", Status.INVALID, "Metadata product order contradicts actual Segment102"));
                    if (!observation.has("context")) observation.putObject("context");
                    if (observation.path("context").isObject()) ((ObjectNode) observation.path("context")).set("segment102ProductCodes", codes);
                }
            } else if (declaredCodes != null) mapping.add(new Finding("CH12-ACTUAL-COMPANION", "actual-product-companion", Status.REVIEW_REQUIRED,
                    "Metadata cannot replace missing actual Segment102 product order"));
        }
        Set<String> names = new java.util.HashSet<>(switch (id) {
            case "130", "131" -> Set.of("SegmentType", "SegmentLength", "CAPublicKeyFileChecksum", "EMVCardSequenceNumber",
                    "EMVChipDataLength", "EMVChipData", "EMVAdditionalInformation", "EMVAdditionalInformationIndicator",
                    "EMVAdditionalInformationLength", "EMVAdditionalInformationValue", "SerializedSegmentHex", "SerializedSegment");
            case "145", "146" -> Set.of("SegmentType", "SegmentLength", "EnhancedFleetData", "Tables", "SerializedSegment");
            case "148" -> Set.of("SegmentType", "SegmentLength", "AvailableProductInformation", "SerializedSegment");
            case "115" -> Set.of("SegmentType", "SegmentLength", "PrintData", "SerializedSegment");
            default -> Set.of("SegmentType", "SegmentLength", "SerializedSegment");
        });
        names.addAll(FIELDS.getOrDefault(id, Map.of()).keySet());
        if (TABLE_DATA.containsKey(id)) {
            names.add(TABLE_DATA.get(id));
            names.add("Tables");
        }
        if (Set.of("151", "152").contains(id)) names.addAll(Set.of("MarketBasketData", "Datasets"));
        if ("155".equals(id)) names.add("Tables");
        if ("143".equals(id)) names.addAll(Set.of("NumberOfProducts", "TaxProducts"));
        if ("DL8".equals(id)) {
            names.addAll(Set.of("DataTypeIndicator", "FloorLimitGroups"));
            names.remove("SegmentType");
        }
        if ("DL1".equals(id)) {
            names.addAll(MERCHANT.keySet());
            names.add("CardTypes");
            names.removeAll(Set.of("SegmentType", "SegmentLength"));
            if (actual.has("SegmentType") || actual.has("SegmentLength"))
                mapping.add(new Finding("SEGDL1-R-002", "actual-mapping", Status.INVALID, "Merchant block has no numbered SegmentType/SegmentLength fields"));
        }
        if (Set.of("139", "140", "141", "142").contains(id)) {
            names.addAll(BATCH_FIELDS.keySet());
            names.add(Set.of("139", "141").contains(id) ? "LanguageIndicator" : "ResponseDisplay");
            if (Set.of("140", "141").contains(id)) names.addAll(BATCH_TOTALS.keySet());
            if ("142".equals(id)) names.add("MAC");
        }
        var supplied = actual.fieldNames();
        while (supplied.hasNext()) {
            String name = supplied.next();
            if (!names.contains(name)) mapping.add(new Finding("CH12-ACTUAL-MAPPING", "actual-mapping", Status.REVIEW_REQUIRED,
                    "Actual segment field has no explicit structural mapping: " + name));
        }
        Result assessed = new Chapter12SegmentPayloadValidator().validate(observation);
        mapping.addAll(assessed.findings());
        return new Result(mapping.stream().anyMatch(f -> f.status() == Status.INVALID) ? Status.INVALID : Status.REVIEW_REQUIRED,
                List.copyOf(mapping), assessed.unassessedCatalogRules());
    }

    private static void decodeTables(JsonNode encoded, ObjectNode observation, List<Finding> findings, int idWidth, String name) {
        var tables = observation.putArray("tables");
        if (encoded == null || !encoded.isTextual()) {
            findings.add(new Finding("CH12-ACTUAL-TLV", "actual-mapping", Status.INVALID, "Required " + name + " must be actual text or Tables array"));
            return;
        }
        String value = encoded.textValue();
        int index = 0;
        while (index < value.length()) {
            int headerWidth = idWidth + 3;
            if (value.length() - index < headerWidth || !value.substring(index, index + headerWidth).matches("[0-9]+")) {
                findings.add(new Finding("CH12-ACTUAL-TLV", "actual-mapping", Status.INVALID, "TLV header is truncated or nonnumeric"));
                return;
            }
            String id = value.substring(index, index + idWidth), length = value.substring(index + idWidth, index + headerWidth);
            int end = index + headerWidth + Integer.parseInt(length);
            if (end > value.length()) {
                findings.add(new Finding("CH12-ACTUAL-TLV", "actual-mapping", Status.INVALID, "TLV value is truncated"));
                return;
            }
            tables.addObject().put("id", id).put("length", length).put("value", value.substring(index + headerWidth, end));
            index = end;
        }
    }

    private static void copy(JsonNode input, String name, ObjectNode target, String number) {
        if (input.has(name)) target.set(number, input.path(name).deepCopy());
    }
}
