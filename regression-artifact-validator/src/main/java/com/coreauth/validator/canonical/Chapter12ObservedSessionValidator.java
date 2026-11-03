package com.coreauth.validator.canonical;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Finding;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/** Observed sequence/device binding, not authentication of the supplied history. */
final class Chapter12ObservedSessionValidator {
    private Chapter12ObservedSessionValidator() { }

    static List<Finding> emv(JsonNode input) {
        List<Finding> findings = new ArrayList<>();
        JsonNode history = input.path("history").path("emv");
        if ("131".equals(input.path("segment").asText())) {
            findings.add(new Finding("SEG131-R-007", "checksum-transaction",
                    identity(input.path("context").get("transactionElements"),
                            history.path("request").path("context").get("transactionElements"), List.of("86", "102")),
                    "Checksum equality is bounded unless current and matched request sequence/device observations agree"));
        } else {
            JsonNode load = history.get("capkLoad");
            if (load == null) {
                findings.add(new Finding("SEG130-R-005", "capk-load-session", Status.REVIEW_REQUIRED,
                        "Matched CAPK load request/response and current device observations required; checksum equality alone is insufficient"));
                findings.add(new Finding("SEG130-R-005", "capk-load-event-order", Status.REVIEW_REQUIRED, "Matched observed load event ordering required"));
            } else if (!load.isObject() || !load.path("request").isObject() || !load.path("response").isObject()) {
                findings.add(new Finding("SEG130-R-005", "capk-load-session", Status.INVALID, "Observed CAPK load must contain request and response objects"));
                findings.add(new Finding("SEG130-R-005", "capk-load-event-order", Status.INVALID, "Observed CAPK load request/response structure is malformed"));
            } else {
                JsonNode request = load.path("request").path("elements"), response = load.path("response").path("elements");
                Status exchange = identity(request, response, List.of("86", "11"));
                Status device = identity(input.path("context").get("transactionElements"), request, List.of("102"));
                Status checksum = identity(input.path("elements"), response, List.of("187"));
                Status status = combine(exchange, device, checksum);
                if (!load.path("request").has("segment") || !request.has("48") || !response.has("83"))
                    status = combine(status, Status.REVIEW_REQUIRED);
                else if (!"132".equals(load.path("request").path("segment").asText())
                        || !"K".equals(request.path("48").asText())
                        || !response.path("83").isTextual() || !List.of("M", "L").contains(response.path("83").textValue())) status = Status.INVALID;
                findings.add(new Finding("SEG130-R-005", "capk-load-session", status,
                        "Request/response load sequence and block agree, approved received checksum is reused only on the observed device; no key authenticity is asserted"));
                findings.add(new Finding("SEG130-R-005", "capk-load-event-order", combine(status,
                        observedOrder(load.path("request").path("sentAt"), load.path("response").path("receivedAt")),
                        observedOrder(load.path("response").path("receivedAt"), input.path("context").path("transactionObservedAt"))),
                        "Source10752-10757/24321-24325: bound load request precedes received checksum, which precedes the subsequent observed EMV request; "
                                + "equal capture instants cannot establish order and do not authenticate latest CAPK state"));
            }
        }
        return List.copyOf(findings);
    }

    static List<Finding> fleet(JsonNode input) {
        JsonNode fleet = input.path("history").path("fleet"), exchange = fleet.get("transactionExchange");
        String rule = "SEG146-R-004";
        if (exchange == null) return List.of(new Finding(rule, "prompt-transaction", Status.REVIEW_REQUIRED,
                "Matched request/response/capture/subsequent transaction observations required; independent prompt maps are only bounded execution"));
        if (!exchange.isObject()) return List.of(new Finding(rule, "prompt-transaction", Status.INVALID, "Fleet exchange must be an object"));
        Status matched = identity(exchange.get("request"), exchange.get("response"), List.of("86", "102"));
        Status capture = identity(exchange.get("capture"), exchange.get("response"), List.of("86", "102"));
        Status subsequent = identity(exchange.get("subsequent"), exchange.get("response"), List.of("102"));
        JsonNode current = "145".equals(input.path("segment").asText()) ? exchange.get("subsequent") : exchange.get("response");
        Status currentBinding = identity(input.path("context").get("transactionElements"), current, List.of("86", "102"));
        List<Finding> findings = new ArrayList<>();
        findings.add(new Finding(rule, "prompt-transaction", combine(matched, capture, subsequent, currentBinding),
                "Observed exchange echoes request sequence/device, binds capture to that response and subsequent send to the same device; later sequence need not equal original"));
        for (String name : List.of("capturedResponses", "subsequentResponses")) {
            JsonNode declared = fleet.get(name);
            JsonNode observed = exchange.path("capturedResponses".equals(name) ? "capture" : "subsequent").get("promptResponses");
            Status status = declared == null || observed == null ? Status.REVIEW_REQUIRED
                    : !textMap(declared) || !textMap(observed) || !declared.equals(observed) ? Status.INVALID : Status.CHECKS_PASSED;
            findings.add(new Finding(rule, "capturedResponses".equals(name) ? "capture-observation" : "subsequent-observation", status,
                    "Prompt map must agree with the observed captured/sent transaction; map equality does not authenticate event provenance"));
        }
        return List.copyOf(findings);
    }

    static List<Finding> fleetPurchases(JsonNode input) {
        List<Finding> findings = new ArrayList<>();
        JsonNode fleet = input.path("history").path("fleet");
        JsonNode capture = fleet.get("purchaseObservation");
        for (String property : List.of("nonfuelProducts", "fuelProducts")) {
            JsonNode declared = fleet.get(property);
            if (declared == null) continue;
            String rule = "145".equals(input.path("segment").asText()) ? "SEG145-R-007"
                    : "fuelProducts".equals(property) ? "SEG146-R-005" : "SEG146-R-002";
            Status matched = identity(input.path("context").get("transactionElements"), capture, List.of("86", "102"));
            if (capture != null && !capture.isObject()) matched = Status.INVALID;
            JsonNode observed = capture == null ? null : capture.get(property);
            Status map = !declared.isArray() || observed != null && !observed.isArray() ? Status.INVALID
                    : observed == null ? Status.REVIEW_REQUIRED : declared.equals(observed) ? Status.CHECKS_PASSED : Status.INVALID;
            JsonNode complete = fleet.get(property + "Complete");
            JsonNode observedComplete = capture == null ? null : capture.get(property + "Complete");
            Status completeness = complete != null && !complete.isBoolean() || observedComplete != null && !observedComplete.isBoolean()
                    ? Status.INVALID : complete == null || observedComplete == null ? Status.REVIEW_REQUIRED
                    : complete.equals(observedComplete) ? Status.CHECKS_PASSED : Status.INVALID;
            findings.add(new Finding(rule, "matched-purchase-observation", combine(matched, map, completeness),
                    "Source15280-15303/15716-15753: purchases used by existing category sum/limit oracle equal a captured inventory bound to actual current86/102; completeness declaration and projection equality do not authenticate raw PRODUCTS grammar or merchant configuration"));
        }
        return List.copyOf(findings);
    }

    static Status identity(JsonNode left, JsonNode right, List<String> fields) {
        boolean leftMissing = left == null || left.isMissingNode(), rightMissing = right == null || right.isMissingNode();
        if (!leftMissing && !left.isObject() || !rightMissing && !right.isObject()) return Status.INVALID;
        boolean missing = leftMissing || rightMissing;
        for (String field : fields) {
            JsonNode a = leftMissing ? null : left.get(field), b = rightMissing ? null : right.get(field);
            String pattern = switch (field) {
                case "86" -> "(?!000000)[0-9]{6}";
                case "11" -> "[0-9]{3}";
                case "187" -> "[0-9]{25}";
                default -> "[ -~]{1,22}";
            };
            for (JsonNode observed : new JsonNode[]{a, b})
                if (observed != null && (!observed.isTextual() || observed.textValue().isBlank()
                        || !observed.textValue().matches(pattern))) return Status.INVALID;
            if (a == null || b == null) { missing = true; continue; }
            if (!a.equals(b)) return Status.INVALID;
        }
        return missing ? Status.REVIEW_REQUIRED : Status.CHECKS_PASSED;
    }

    private static boolean textMap(JsonNode node) {
        if (!node.isObject()) return false;
        var values = node.elements();
        while (values.hasNext()) if (!values.next().isTextual()) return false;
        return true;
    }

    static Status observedOrder(JsonNode before, JsonNode after) {
        boolean beforeMissing = before == null || before.isMissingNode(), afterMissing = after == null || after.isMissingNode();
        if (!beforeMissing && !before.isTextual() || !afterMissing && !after.isTextual()) return Status.INVALID;
        try {
            var first = beforeMissing ? null : java.time.OffsetDateTime.parse(before.textValue()).toInstant();
            var second = afterMissing ? null : java.time.OffsetDateTime.parse(after.textValue()).toInstant();
            if (beforeMissing || afterMissing) return Status.REVIEW_REQUIRED;
            return first.isAfter(second) ? Status.INVALID : first.equals(second) ? Status.REVIEW_REQUIRED : Status.CHECKS_PASSED;
        } catch (java.time.format.DateTimeParseException exception) {
            return Status.INVALID;
        }
    }

    static Status combine(Status... statuses) {
        boolean missing = false;
        for (Status status : statuses) {
            if (status == Status.INVALID) return Status.INVALID;
            if (status == Status.REVIEW_REQUIRED) missing = true;
        }
        return missing ? Status.REVIEW_REQUIRED : Status.CHECKS_PASSED;
    }
}
