package com.coreauth.validator.canonical;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Finding;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.validation.Chapter13ProcessingHistoryValidator;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/** Binds captured source-local lifecycle observations; never authenticates capture or invents retry policy. */
final class Chapter12LoadLifecycleValidator {
    private Chapter12LoadLifecycleValidator() { }

    static List<Finding> site(JsonNode actual, JsonNode evidence) {
        if (!"903".equals(actual.path("PromptCode").asText())) return List.of();
        List<Finding> findings = new ArrayList<>();
        JsonNode history = evidence.path("history").path("proprietaryLoad").path("siteConfiguration");
        var day = Status.CHECKS_PASSED;
        for (var finding : Chapter13ProcessingHistoryValidator.validateObservedSiteConfiguration(history))
            day = combine(day, Status.valueOf(finding.status().name()));
        day = combine(day, bindNamed(actual, history, List.of("TerminalIdentifier")));
        JsonNode date = history.get("businessDate");
        if (date == null) day = combine(day, Status.REVIEW_REQUIRED);
        else if (!date.isTextual()) day = Status.INVALID;
        else {
            try { java.time.LocalDate.parse(date.textValue()); }
            catch (java.time.format.DateTimeParseException exception) { day = Status.INVALID; }
        }
        JsonNode blocks = history.get("requestBlocks");
        var chain = history.isMissingNode() ? Status.REVIEW_REQUIRED : history.isObject() ? Status.CHECKS_PASSED : Status.INVALID;
        if (blocks == null) chain = combine(chain, Status.REVIEW_REQUIRED);
        else if (!blocks.isArray() || blocks.isEmpty()) chain = Status.INVALID;
        else {
            StringBuilder transmitted = new StringBuilder();
            JsonNode previous = null;
            boolean ended = false;
            boolean numberingKnown = true, transmittedKnown = true;
            for (int index = 0; index < blocks.size(); index++) {
                JsonNode event = blocks.get(index), request = event.path("request");
                if (!event.isObject() || !request.isMissingNode() && !request.isObject()) { chain = Status.INVALID; continue; }
                if (request.isMissingNode()) {
                    chain = combine(chain, Status.REVIEW_REQUIRED);
                    numberingKnown = false;
                    transmittedKnown = false;
                    if (previous != null) chain = combine(chain,
                            Chapter12ObservedSessionValidator.observedOrder(previous.path("sentAt"), event.path("sentAt")));
                    previous = event;
                    continue;
                }
                chain = combine(chain, bindNamed(actual, request, List.of("TerminalIdentifier")));
                chain = combine(chain, text(request.get("SegmentType"), "118"), text(request.get("PromptCode"), "903"),
                        text(request.get("BlockNumber"), "[0-9]{3}"));
                String number = request.path("BlockNumber").asText();
                JsonNode data = request.get("SiteConfigurationData");
                if (data == null) { chain = combine(chain, Status.REVIEW_REQUIRED); transmittedKnown = false; }
                else if (!data.isTextual() || data.textValue().length() > 3600 || data.textValue().chars().anyMatch(c -> c > 127))
                    chain = Status.INVALID;
                if (ended) chain = Status.INVALID;
                if (!request.has("BlockNumber")) numberingKnown = false;
                else if ("000".equals(number)) {
                    if (index == 0 || index != blocks.size() - 1 || data != null && (!data.isTextual() || !data.textValue().isEmpty()))
                        chain = Status.INVALID;
                    ended = true;
                } else {
                    if (index + 1 > 999 || !String.format(java.util.Locale.ROOT, "%03d", index + 1).equals(number)) chain = Status.INVALID;
                    if (data != null && data.isTextual()) transmitted.append(data.textValue());
                }
                for (var wire : new Segment118PayloadValidator().validateActualWire(request, true))
                    if (!"actual-legacy-payload-boundary".equals(wire.scope())) chain = combine(chain, wire.status());
                if (previous != null) chain = combine(chain, Chapter12ObservedSessionValidator.observedOrder(previous.path("sentAt"), event.path("sentAt")));
                previous = event;
            }
            JsonNode complete = history.get("transferComplete");
            if (complete == null) chain = combine(chain, Status.REVIEW_REQUIRED);
            else if (!complete.isBoolean() || numberingKnown && complete.booleanValue() != ended) chain = Status.INVALID;
            else if (!complete.booleanValue()) chain = combine(chain, Status.REVIEW_REQUIRED);
            JsonNode index = history.get("currentRequestIndex");
            if (index == null) chain = combine(chain, Status.REVIEW_REQUIRED);
            else if (!index.isIntegralNumber() || !index.canConvertToInt() || index.intValue() < 0 || index.intValue() >= blocks.size()) chain = Status.INVALID;
            else {
                JsonNode capture = blocks.get(index.intValue()).path("request");
                chain = combine(chain, bindNamed(actual, capture, List.of("SegmentType", "SegmentLength", "SequenceNumber", "InformationByte",
                        "TerminalIdentifier", "PromptCode", "PromptCodePending", "DeviceCardTableVersion", "CardTableLoadVersion",
                        "CardTableType", "LoadControlKey", "HostDiscountTimestamp", "BlockNumber", "SiteConfigurationData", "SerializedSegment")));
            }
            JsonNode sent = history.get("sentData");
            if (sent == null) chain = combine(chain, Status.REVIEW_REQUIRED);
            else if (!sent.isArray() || sent.size() != 1 || !sent.get(0).isTextual()
                    || transmittedKnown && !sent.get(0).textValue().contentEquals(transmitted))
                chain = Status.INVALID;
        }
        findings.add(new Finding("SEG118-R-021", "actual-site-block-lifecycle", chain,
                "Source13287-13290: observed903 starts001, increments each sent data block and ends000 without data; actual current request and source wire bind captured blocks; no retransmission policy inferred"));
        findings.add(new Finding("SEG118-R-021", "actual-site-final-business-day", combine(day, chain),
                "Source13291-13294: reuse final-change/no-change business-day oracle on captured inventory bound to actual903 and transmitted block concatenation; supplied full-year day is not inferred merchant timezone or complete audit authenticity"));
        JsonNode response = history.path("response"), request = blocks != null && blocks.isArray()
                && history.path("currentRequestIndex").canConvertToInt()
                ? blocks.path(history.path("currentRequestIndex").intValue()).path("request") : actual;
        Status disposition = combine(bindNamed(actual, request, List.of("TerminalIdentifier", "SequenceNumber")),
                bindNamed(request, response, List.of("SequenceNumber")),
                text(response.get("ResponseCode"), "[TU]"));
        JsonNode approved = response.get("approved"), pending = response.get("moreDataPending");
        if (approved != null && !approved.isBoolean() || pending != null && !pending.isBoolean()) disposition = Status.INVALID;
        if (approved == null || pending == null) disposition = combine(disposition, Status.REVIEW_REQUIRED);
        if (response.path("ResponseCode").isTextual() && List.of("T", "U").contains(response.path("ResponseCode").textValue())) {
            if (approved != null && approved.isBoolean()
                    && approved.booleanValue() != "T".equals(response.path("ResponseCode").textValue())) disposition = Status.INVALID;
            if (pending != null && pending.isBoolean() && pending.booleanValue()) disposition = Status.INVALID;
        }
        JsonNode event = blocks != null && blocks.isArray() ? blocks.path(history.path("currentRequestIndex").asInt(-1)) : history;
        disposition = combine(disposition, Chapter12ObservedSessionValidator.observedOrder(event.path("sentAt"), response.path("receivedAt")));
        findings.add(new Finding("SEG118-R-026", "actual-site-response-disposition", disposition,
                "Source13295-13297: captured903 responseT approves andU declines, both with no more pending; sequence echo binds current request and observed response follows send; capture assertions are not authenticated"));
        return List.copyOf(findings);
    }

    static List<Finding> receipt(JsonNode actual, JsonNode evidence) {
        if (!"901".equals(actual.path("PromptCode").asText())) return List.of();
        JsonNode history = evidence.path("history").path("proprietaryLoad").path("receipt");
        Status status = bindNamed(actual, history, List.of("TerminalIdentifier", "SequenceNumber"));
        for (var finding : Chapter13ProcessingHistoryValidator.validateObservedReceipt(history))
            status = combine(status, Status.valueOf(finding.status().name()));
        JsonNode lines = actual.get("ReceiptTextLines"), received = history.get("receivedLines");
        if (lines != null && !lines.isArray() || received != null && !received.isArray()) status = Status.INVALID;
        if (lines == null || received == null) status = combine(status, Status.REVIEW_REQUIRED);
        else if (lines.isArray() && received.isArray()) {
            var projection = com.fasterxml.jackson.databind.node.JsonNodeFactory.instance.arrayNode();
            boolean known = true;
            for (JsonNode line : lines) {
                if (!line.isObject()) { known = false; status = Status.INVALID; continue; }
                JsonNode data = line.get("ReceiptTextData");
                if (data == null) { known = false; status = combine(status, Status.REVIEW_REQUIRED); }
                else if (!data.isTextual()) status = Status.INVALID;
                else projection.add(data.textValue());
            }
            if (known && !projection.equals(received)) status = Status.INVALID;
        }
        status = combine(status, Chapter12ObservedSessionValidator.observedOrder(history.path("receivedAt"), history.path("printedAt")));
        JsonNode local = history.get("printedLocalDateTime");
        Status window = ProductActualPayloadAdapter.discountWindow(local, actual);
        return List.of(new Finding("SEG118-R-019", "actual-receipt-retained-print", status,
                "Source Element170: actual901 received text binds captured received/retained/subsequent printed lines in order; reuse retained-data oracle, no authenticated printer state inferred"),
                new Finding("SEG118-R-019", "actual-receipt-use-window", combine(status, window),
                        "Source13206-13219: actual received start/end date/time bounds captured local receipt use; exact endpoints/2400/timezone remain review without invented conversion"));
    }

    static List<Finding> capk(JsonNode input) {
        JsonNode history = input.path("history").path("capk"), exchange = history.path("exchange");
        JsonNode events = exchange.get("events");
        var status = exchange.isMissingNode() ? Status.REVIEW_REQUIRED : exchange.isObject() ? Status.CHECKS_PASSED : Status.INVALID;
        JsonNode last = null, previous = null;
        boolean projectionKnown = true;
        var projected = com.fasterxml.jackson.databind.node.JsonNodeFactory.instance.arrayNode();
        if (events == null) status = combine(status, Status.REVIEW_REQUIRED);
        else if (!events.isArray() || events.isEmpty()) status = Status.INVALID;
        else {
            for (int index = 0; index < events.size(); index++) {
                JsonNode event = events.get(index), request = event.path("request").path("elements"), response = event.path("response").path("elements");
                if (!event.isObject() || !request.isMissingNode() && !request.isObject()
                        || !response.isMissingNode() && !response.isObject()) { status = Status.INVALID; projectionKnown = false; continue; }
                if (request.isMissingNode() || response.isMissingNode()) {
                    status = combine(status, Status.REVIEW_REQUIRED);
                    projectionKnown = false;
                }
                status = combine(status, Chapter12ObservedSessionValidator.identity(request, response, List.of("86", "11")),
                        Chapter12ObservedSessionValidator.identity(input.path("elements"), request, List.of("102")),
                        text(request.get("48"), "K"), text(response.get("24"), "&"), text(response.get("83"), "[MLX]"),
                        text(response.get("187"), "[0-9]{25}"),
                        Chapter12ObservedSessionValidator.observedOrder(event.path("sentAt"), event.path("receivedAt")));
                if (previous != null) status = combine(status,
                        Chapter12ObservedSessionValidator.observedOrder(previous.path("receivedAt"), event.path("sentAt")));
                if (index < events.size() - 1 && response.has("83") && !"M".equals(response.path("83").asText())) status = Status.INVALID;
                JsonNode data = response.get("194"), length = response.get("193");
                if (data != null && !data.isTextual() || length != null && !length.isTextual()) status = Status.INVALID;
                if (data == null || length == null) { status = combine(status, Status.REVIEW_REQUIRED); projectionKnown = false; }
                else if (!data.isTextual() || !length.isTextual() || !length.textValue().matches("[0-9]{3,4}")
                        || data.textValue().chars().anyMatch(c -> c > 127)
                        || Integer.parseInt(length.textValue()) != data.textValue().length()) status = Status.INVALID;
                if (data != null && length != null) {
                    var block = projected.addObject();
                    block.set("data", data.deepCopy());
                    block.set("length", length.deepCopy());
                }
                previous = event;
                last = response;
            }
            JsonNode selected = exchange.get("currentRequestIndex");
            if (selected == null) status = combine(status, Status.REVIEW_REQUIRED);
            else if (!selected.isIntegralNumber() || !selected.canConvertToInt() || selected.intValue() < 0 || selected.intValue() >= events.size())
                status = Status.INVALID;
            else status = combine(status, bindNamed(input.path("elements"), events.get(selected.intValue()).path("request").path("elements"),
                    List.of("85", "84", "86", "102", "48", "43", "96", "39", "187", "11")));
        }
        JsonNode approved = history.get("approved"), complete = history.get("transferComplete");
        if (approved == null || complete == null) status = combine(status, Status.REVIEW_REQUIRED);
        else if (!approved.isBoolean() || !complete.isBoolean()) status = Status.INVALID;
        else if (last != null) {
            String code = last.path("83").asText();
            if (last.has("83") && approved.booleanValue() != !"X".equals(code)) status = Status.INVALID;
            if (last.has("83") && complete.booleanValue() && !List.of("L", "X").contains(code)) status = Status.INVALID;
            if (!complete.booleanValue()) status = combine(status, Status.REVIEW_REQUIRED);
        }
        if (approved != null && approved.isBoolean() && approved.booleanValue() && events != null && events.isArray()) {
            JsonNode declared = history.get("blocks");
            status = combine(status, declared == null ? Status.REVIEW_REQUIRED
                    : !declared.isArray() || projectionKnown && !declared.equals(projected) ? Status.INVALID : Status.CHECKS_PASSED);
        } else if (approved != null && approved.isBoolean() && last != null && !approved.booleanValue()) {
            JsonNode error = history.get("responseData");
            if (error == null || !last.has("194")) status = combine(status, Status.REVIEW_REQUIRED);
            else if (!error.isTextual() || !error.equals(last.get("194"))) status = Status.INVALID;
        }
        List<Finding> findings = new ArrayList<>();
        findings.add(new Finding("SEG132-R-010", "actual-capk-block-exchange", status,
                "Source10731-10830: each observed CAPK request/response echoes86/11 on current actual device, M has more captured data and completed chain endsL orX; bound actual request and received blocks feed existing assembly/replacement oracle; no block increment/retry algorithm inferred"));
        if (last != null && "X".equals(last.path("83").asText())) {
            var disposition = status;
            String data = last.path("194").asText();
            if ("RQST/CURRENT HASH MATCH".equals(data)) {
                disposition = combine(disposition, bindNamed(history, history, List.of("storedFileBefore", "storedFileAfter")));
                if (history.has("storedFileBefore") && history.has("storedFileAfter")
                        && !history.path("storedFileBefore").equals(history.path("storedFileAfter"))) disposition = Status.INVALID;
            } else if (!List.of("BLOCK NBR NOT NUMERIC", "BLOCK NBR NOT 000-xxx").contains(data))
                disposition = combine(disposition, Status.REVIEW_REQUIRED);
            findings.add(new Finding("SEG132-R-009", "actual-capk-x-disposition", disposition,
                    "Source10807-10820/24491-24504: X distinguishes observed hash-match no-update from documented errors; existing declined-display oracle remains authoritative and captures are not trusted key provenance"));
        }
        JsonNode transactions = history.get("subsequentEmvTransactions"), inventoryComplete = history.get("subsequentEmvInventoryComplete");
        Status reuse = status;
        if (transactions == null || inventoryComplete == null) reuse = combine(reuse, Status.REVIEW_REQUIRED);
        if (transactions != null && !transactions.isArray() || inventoryComplete != null && !inventoryComplete.isBoolean()) reuse = Status.INVALID;
        if (inventoryComplete != null && inventoryComplete.isBoolean() && !inventoryComplete.booleanValue()) reuse = combine(reuse, Status.REVIEW_REQUIRED);
        if (transactions != null && transactions.isArray()) {
            if (transactions.isEmpty()) reuse = combine(reuse, Status.REVIEW_REQUIRED);
            for (JsonNode transaction : transactions) {
                if (!transaction.isObject()) { reuse = Status.INVALID; continue; }
                JsonNode elements = transaction.path("elements");
                if (!elements.isMissingNode() && !elements.isObject()) reuse = Status.INVALID;
                reuse = combine(reuse, text(elements.get("187"), "[0-9]{25}"));
                reuse = combine(reuse, Chapter12ObservedSessionValidator.identity(input.path("elements"), transaction.path("transactionElements"), List.of("102")),
                        Chapter12ObservedSessionValidator.identity(last, transaction.path("elements"), List.of("187")),
                        Chapter12ObservedSessionValidator.observedOrder(previous == null ? com.fasterxml.jackson.databind.node.MissingNode.getInstance()
                                : previous.path("receivedAt"), transaction.path("observedAt")));
            }
        }
        findings.add(new Finding("SEG132-R-009", "actual-capk-all-subsequent-emv", reuse,
                "Source10753-10758: every supplied subsequent EMV transaction on actual load device reuses received checksum and follows captured receipt; complete inventory claim is required but not authenticated; no latest-load or key trust inferred"));
        return List.copyOf(findings);
    }

    static Status bindNamed(JsonNode actual, JsonNode capture, List<String> fields) {
        if (actual == null || capture == null || actual.isMissingNode() || capture.isMissingNode()) return Status.REVIEW_REQUIRED;
        if (!actual.isObject() || !capture.isObject()) return Status.INVALID;
        var status = Status.CHECKS_PASSED;
        for (String field : fields) {
            JsonNode left = actual.get(field), right = capture.get(field);
            if (left != null && !left.isTextual() || right != null && !right.isTextual()) status = Status.INVALID;
            if (left == null || right == null) status = combine(status, Status.REVIEW_REQUIRED);
            else if (!left.isTextual() || !right.isTextual() || !left.equals(right)) status = Status.INVALID;
        }
        return status;
    }

    static Status text(JsonNode value, String pattern) {
        return value == null ? Status.REVIEW_REQUIRED : value.isTextual() && value.textValue().matches(pattern)
                ? Status.CHECKS_PASSED : Status.INVALID;
    }

    private static Status combine(Status... statuses) { return Chapter12ObservedSessionValidator.combine(statuses); }
}
