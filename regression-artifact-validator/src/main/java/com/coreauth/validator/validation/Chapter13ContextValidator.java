package com.coreauth.validator.validation;

import com.coreauth.validator.canonical.AppendixDStateCodes;
import com.coreauth.validator.canonical.AppendixETableLoadCardTypeCodes;
import com.coreauth.validator.canonical.AppendixREmvChipDataOracle;
import com.coreauth.validator.canonical.AppendixVMonerisDataOracle;
import com.coreauth.validator.canonical.Atl105ResponseCodeFamilyOracle;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Finding;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigInteger;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Element-local applicability and correlations against explicit observation context. */
public final class Chapter13ContextValidator {
    private static final Map<String, String> EVIDENCE = Map.ofEntries(
            Map.entry("1", "contains neither this element nor the Pause Indicator"),
            Map.entry("2", "the same credentials for the retry"),
            Map.entry("4", "13 Space"),
            Map.entry("5", "must be present and identical"),
            Map.entry("6", "pump should be set to pump the Approved Amount"),
            Map.entry("11", "reinstates transmission of the interrupted data block"),
            Map.entry("14", "Appendix E"),
            Map.entry("17", "cash back amount and the cash back fee"),
            Map.entry("20", "all transactions for a given merchant"),
            Map.entry("23", "30 minutes prior"),
            Map.entry("24", "Identifies the Merchant Data Segment"),
            Map.entry("30", "device must update its Key ID and Key"),
            Map.entry("39", "Required for Totals Requests"),
            Map.entry("43", "some devices send 8 bytes"),
            Map.entry("44", "communication line remains established"),
            Map.entry("65", "Password required for totals request or electronic mail request"),
            Map.entry("75", "secondary Phone Number is used once attempts"),
            Map.entry("77", "only the product codes defined in the table"),
            Map.entry("79", "Required on all fuel transactions"),
            Map.entry("83", "Transaction Code"),
            Map.entry("86", "Each transaction has a unique Sequence Number"),
            Map.entry("99", "Sales tax is not applicable to EBT Food Stamps"),
            Map.entry("101", "Print what is sent"),
            Map.entry("102", "the first two characters"),
            Map.entry("115", "No Additional Information Data Segment follows"),
            Map.entry("121", "only supported for American Express prepaid cards"),
            Map.entry("123", "Required on all manually entered check transactions"),
            Map.entry("126", "Required on all manually entered check transactions"),
            Map.entry("127", "Required on all manually entered check transactions"),
            Map.entry("139", "Required on all loyalty card transactions"),
            Map.entry("151", "Required on loyalty reversals"),
            Map.entry("155", "all subsequent TransArmor transactions"),
            Map.entry("157", "a message is returned in this field"),
            Map.entry("175", "Prompt Code 902"),
            Map.entry("176", "begins with 99999"),
            Map.entry("188", "If EMV Tag 5F34 is not provided"),
            Map.entry("190", "last value that was present"),
            Map.entry("195", "only for Visa and Interlink"),
            Map.entry("196", "only for Visa/MasterCard"),
            Map.entry("197", "returned only for Visa/MasterCard"),
            Map.entry("198", "subsequent transactions"),
            Map.entry("203", "Mandatory 28 bytes of AEVV"),
            Map.entry("206", "Appendix V"),
            Map.entry("215", "Language Indicator Matrix"));
    private static final Map<String, String> LOAD_INDICATORS = Map.of(
            "DL1", "#", "DL2", "!", "DL3", ":", "DL4", "@", "DL5", "$", "DL6", "\\", "DL7", "^", "DL8", "%");
    private static final Set<String> FOLLOW_ON = Set.of("COMPLETION", "PURCHASE_REVERSAL", "TIMEOUT_REVERSAL",
            "AUTHORIZATION_REVERSAL", "CANCELLATION", "VOID_MERCHANDISE_RETURN");

    public static String segmentForLoadIndicator(String indicator) {
        return LOAD_INDICATORS.entrySet().stream().filter(entry -> entry.getValue().equals(indicator))
                .map(Map.Entry::getKey).findFirst().orElse(null);
    }

    public Chapter13ContextValidator(JsonNode inventory) throws IOException {
        for (var entry : EVIDENCE.entrySet()) {
            boolean found = false;
            for (JsonNode definition : inventory.path("elements").path(entry.getKey()).path("definitions")) {
                if (definition.path("sourceText").asText().replaceAll("\\s+", " ").contains(entry.getValue())) found = true;
            }
            if (!found) throw new IOException("Context predicate lacks element-local evidence: " + entry.getKey());
        }
    }

    public List<Finding> validate(JsonNode observation) {
        List<Finding> findings = new ArrayList<>();
        JsonNode e = observation.path("elements"), q = observation.path("qualifiers");
        String family = observation.path("messageFamily").asText(), segment = observation.path("segment").asText();
        for (String field : List.of("fuelTransaction", "manuallyEnteredCheck", "loyaltyTransaction", "loyaltyReversal",
                "paymentTokenized", "prepaidCard", "afpDevice", "partialApproval", "retry", "emvTransaction",
                "accessCodeNeeded", "longPauseRequested", "capkFirstBlock", "proprietaryLastBlock", "resumeInterruptedBlock",
                "lineRemainedConnected", "keyUpdateScheduled", "dynamicCardTableActive", "cardTablePresent", "loadApproved")) {
            if (q.has(field) && !q.path(field).isBoolean()) add(findings, "", "CONTROL", Status.INVALID, field + " must be boolean");
        }
        for (String field : List.of("originalElements", "retryElements", "cardInterfaceLastValues")) {
            if (q.has(field) && !q.path(field).isObject()) add(findings, "", "CONTROL", Status.INVALID, field + " must be an object");
        }
        for (String field : List.of("network", "accountMode", "direction", "entryMode", "cardTrackData", "flow",
                "paymentType", "pumpLimit", "requestedAmount", "remainingBalancePrompted", "cashBackAmount",
                "cashBackFee", "loadedKeyId", "promptCode", "responseCodeFamily", "ecommerceSecureId")) {
            if (q.has(field) && !q.path(field).isTextual()) add(findings, "", "CONTROL", Status.INVALID, field + " must be text");
        }
        if (has(e, "4")) {
            String value = text(e, "4");
            check(findings, "4", "ADDRESS-LAYOUT", value.length() == 21 && value.charAt(12) == ' '
                    && value.charAt(15) == ' ' && AppendixDStateCodes.isValidAlphabeticalCode(value.substring(13, 15))
                    && value.substring(16).matches("[0-9]{5}"), "Address Line 2 preserves city/state/postal positions; assignment is external");
        }
        if (has(e, "14")) check(findings, "14", "CARD-TYPE", AppendixETableLoadCardTypeCodes.supportedCodes().contains(text(e, "14")),
                "Card/feature code must be listed in Appendix E");
        if (has(e, "24") && LOAD_INDICATORS.containsKey(segment)) check(findings, "24", "LOAD-INDICATOR",
                LOAD_INDICATORS.get(segment).equals(text(e, "24")), "Data Type Indicator must identify the observed load segment");
        if ("Totals Request".equals(family) && "105".equals(segment) || "Table Load Request".equals(family) && "MESSAGE".equals(segment)
                || "TransArmor Key and Key ID Load Request".equals(family) && "116".equals(segment)) {
            for (String id : List.of("39", "43", "96")) require(e, id, observation, findings, "Version required for this source request family");
            if (has(e, "43")) check(findings, "43", "HARDWARE-WIDTH", text(e, "43").length() == 4
                    || "Totals Request".equals(family) && text(e, "43").length() == 8, "Hardware has four bytes, with Totals' explicit eight-byte alternative");
        }
        if ("Totals Request".equals(family) && "105".equals(segment) || "Electronic Mail Request".equals(family) && "109".equals(segment)) require(e, "65", observation, findings, "Password required for request");
        if (yes(q, "fuelTransaction") && "100".equals(segment)) require(e, "79", observation, findings, "Fuel transaction requires Pump Number");
        if (has(e, "99") && "EBT_FOOD".equals(text(q, "paymentType"))) {
            check(findings, "99", "EBT-TAX", false, "Sales tax is not applicable to EBT Food Stamps; omit Element 99");
        }
        if (has(e, "121") && "5".equals(text(e, "121"))) {
            if (text(q, "network") == null || !q.path("prepaidCard").isBoolean()) review(findings, "121", "BALANCE-ONLY", "Network and prepaid-card evidence required");
            else check(findings, "121", "BALANCE-ONLY", "AMEX".equals(text(q, "network")) && yes(q, "prepaidCard"),
                    "Balance-only partial approval value 5 is supported only for Amex prepaid");
        }
        if (yes(q, "manuallyEnteredCheck") && "110".equals(segment)) {
            for (String id : List.of("122", "123", "124", "126", "127")) require(e, id, observation, findings, "Manually entered check companion required");
        }
        if (yes(q, "loyaltyTransaction") && "108".equals(segment)) {
            require(e, "138", observation, findings, "Loyalty Program ID required; its ERN/numeric source conflict is retained");
            require(e, "139", observation, findings, "Loyalty account required");
        }
        if (yes(q, "loyaltyReversal") && "108".equals(segment)) require(e, "151", observation, findings, "Loyalty reversal requires Unit of Work");
        if ("T".equals(text(e, "143"))) {
            if (!has(e, "2")) require(e, "2", observation, findings, "Loyalty totals requires Account Number text");
            else check(findings, "2", "LOYALTY-TOTALS", "TOTALS REQUEST".equals(text(e, "2")), "Loyalty totals uses literal TOTALS REQUEST");
        }
        terminal(e, family, findings);
        account(e, q, observation, findings);
        actions(e, q, observation, findings);
        response(e, q, family, findings);
        emv(e, q, observation, findings);
        if ("123".equals(observation.path("segment").asText())
                && Set.of("25", "26").contains(text(q, "ecommerceSecureId") == null ? "" : text(q, "ecommerceSecureId"))) {
            require(e, "203", observation, findings, "SafeKey Secure ID 25/26 requires the mandatory AEVV data in Segment 123");
        }
        tokenization(e, q, findings);
        operational(e, q, observation, findings);
        if (has(e, "175") && text(q, "promptCode") != null) check(findings, "175", "LOAD-PROMPT",
                "902".equals(text(q, "promptCode")), "Card Table Data is returned for Prompt Code 902");
        if (has(e, "215")) check(findings, "215", "MONERIS-LANGUAGE", new AppendixVMonerisDataOracle().languageProfile(text(e, "215")).isPresent(),
                "Moneris language code must be source-listed");
        if (has(e, "206")) {
            String direction = text(q, "direction");
            if (!Set.of("REQUEST", "RESPONSE").contains(direction == null ? "" : direction)) review(findings, "206", "MONERIS-TABLES", "Request/response direction required");
            else {
                var oracle = new AppendixVMonerisDataOracle();
                var results = "REQUEST".equals(direction) ? oracle.assessRequest(text(e, "206")) : oracle.assessResponse(text(e, "206"));
                var layout = results.stream().filter(r -> r.assertionScope().equals("request-table-layout") || r.assertionScope().equals("response-table-layout")).findFirst().orElseThrow();
                add(findings, "206", "MONERIS-TABLES", status(layout.status().name()), layout.reason());
            }
        }
        return List.copyOf(findings);
    }

    private static void terminal(JsonNode e, String family, List<Finding> findings) {
        if (!has(e, "102")) return;
        String value = text(e, "102");
        boolean load = Set.of("Table Load Request", "Phone Load Request", "Date and Time Load Request", "Software Load Request").contains(family);
        check(findings, "102", "TERMINAL-LAYOUT", (load ? value.length() == 13 : value.length() >= 13 && value.length() <= 22)
                && AppendixDStateCodes.ansiCodes().contains(value.length() >= 4 ? value.substring(2, 4) : "")
                && value.substring(Math.min(4, value.length())).matches("[A-Za-z0-9]+"),
                "Terminal contains Device Type, ANSI state, merchant and device; actual assigned identity is external");
        if ("CA Public Key File Load Request".equals(family)) check(findings, "102", "LOAD-DEVICE", value.startsWith("+*"), "CAPK load uses +* Device Type");
        if ("TransArmor Key and Key ID Load Request".equals(family)) check(findings, "102", "LOAD-DEVICE", value.startsWith("++"), "TransArmor PKI load uses ++ Device Type");
    }

    private static void account(JsonNode e, JsonNode q, JsonNode observation, List<Finding> findings) {
        if (!has(e, "2")) return;
        String account = text(e, "2"), method = text(q, "accountMode"), direction = text(q, "direction");
        if ("NON_TRANSARMOR".equals(method)) {
            if ("REQUEST".equals(direction) && "095".equals(text(q, "cardType")) && account.length() == 25) review(findings, "2", "ACCOUNT-WIDTH", "Express Code's 25-digit rule conflicts with general non-TransArmor request maximum 24");
            else if ("REQUEST".equals(direction)) check(findings, "2", "ACCOUNT-WIDTH", account.length() <= 24, "Non-TransArmor request Account Number is at most 24 bytes");
            else if ("RESPONSE".equals(direction)) check(findings, "2", "ACCOUNT-WIDTH", account.length() == 25, "Non-TransArmor response Account Number is fixed 25 bytes");
            else review(findings, "2", "ACCOUNT-WIDTH", "Account request/response direction required");
        }
        String track = text(q, "cardTrackData"), entry = text(q, "entryMode");
        if (Set.of("TRACK1", "TRACK2").contains(entry == null ? "" : entry)) {
            if (track == null || !"NON_TRANSARMOR".equals(method)) review(findings, "2", "TRACK-SPLIT", "Unencrypted source track and NON_TRANSARMOR context required; transformed EDATA uses the Appendix AA oracle");
            else {
                char delimiter = "TRACK1".equals(entry) ? '^' : '=';
                int split = track.indexOf(delimiter);
                if (!has(e, "12")) require(e, "12", observation, findings, "Observed track requires discretionary data");
                else {
                    int maximum = "TRACK1".equals(entry) ? 76 : 37;
                    check(findings, "2", "TRACK-SPLIT", split > 0 && account.equals(track.substring(0, Math.max(0, split)))
                            && text(e, "12").equals(split < 0 ? "" : track.substring(split + 1))
                            && account.length() + text(e, "12").length() <= maximum,
                            "Track before/after delimiter is preserved unchanged, within source combined cap");
                }
            }
        }
        if (yes(q, "retry")) equalOriginal(e, q.path("retryElements"), "2", observation, findings, "RETRY-CREDENTIAL", "Retry must preserve first-attempt credential");
        if ("REQUEST".equals(direction) && "STORED_VALUE".equals(text(q, "paymentType"))) {
            if (!"NON_TRANSARMOR".equals(method)) review(findings, "2", "STORED-VALUE-WIDTH", "Underlying unencrypted account context required; transformed EDATA is not a 16/19-byte PAN");
            else check(findings, "2", "STORED-VALUE-WIDTH", account.length() == 16 || account.length() == 19, "Stored-value request Account Number is 16 or 19 bytes");
        }
        if (q.has("accountRetainedBeforeResponse") && Set.of("NON_TRANSARMOR", "TOKEN_ONLY").contains(method == null ? "" : method)) {
            check(findings, "2", "ACCOUNT-RETENTION", q.path("accountRetainedBeforeResponse").isBoolean() && q.path("accountRetainedBeforeResponse").booleanValue(),
                    "Non-TransArmor and Token Only retain account until host response");
        }
        if (q.has("accountRetainedAfterSwipe") && "RSA_PKI".equals(method)) check(findings, "2", "ACCOUNT-RETENTION",
                q.path("accountRetainedAfterSwipe").isBoolean() && !q.path("accountRetainedAfterSwipe").booleanValue(), "PKI encryption/tokenization must not retain PAN after initial swipe");
    }

    private static void actions(JsonNode e, JsonNode q, JsonNode observation, List<Finding> findings) {
        String flow = text(q, "flow");
        if ("AUTHORIZATION_REVERSAL".equals(flow) && ("100".equals(observation.path("segment").asText()) || has(e, "5"))) equalOriginal(e, q.path("originalElements"), "5", observation, findings, "APPROVAL-ECHO", "Authorization reversal preserves original Approval Number");
        if (has(e, "6") && yes(q, "afpDevice")) {
            String limit = text(q, "pumpLimit");
            if (limit == null) review(findings, "6", "PUMP-LIMIT", "Observed pump limit required");
            else check(findings, "6", "PUMP-LIMIT", equalAmount(text(e, "6"), limit), "AFP pump limit equals Approved Amount, not requested amount");
        }
        if (has(e, "6") && yes(q, "partialApproval")) {
            String requested = text(q, "requestedAmount"), remaining = text(q, "remainingBalancePrompted");
            if (requested == null || remaining == null) review(findings, "6", "REMAINING-BALANCE", "Observed sale amount and remaining-balance prompt required");
            else check(findings, "6", "REMAINING-BALANCE", digits(requested) && digits(remaining) && digits(text(e, "6"))
                    && new BigInteger(requested).subtract(new BigInteger(text(e, "6"))).signum() >= 0
                    && new BigInteger(requested).subtract(new BigInteger(text(e, "6"))).equals(new BigInteger(remaining)),
                    "Partial Approval prompts for exact remaining sale balance");
        }
        if (has(e, "17") && q.has("cashBackFee")) {
            String amount = text(q, "cashBackAmount"), fee = text(q, "cashBackFee");
            check(findings, "17", "CASH-FEE", digits(amount) && digits(fee) && digits(text(e, "17"))
                    && new BigInteger(amount).add(new BigInteger(fee)).equals(new BigInteger(text(e, "17"))),
                    "Supported optional cash-back fee is included in Cash Amount");
        }
        if (has(e, "86") && q.has("priorIndependentSequences") && !FOLLOW_ON.contains(flow == null ? "" : flow)) {
            JsonNode sequences = q.path("priorIndependentSequences");
            boolean valid = sequences.isArray();
            for (JsonNode sequence : sequences) if (!sequence.isTextual() || sequence.textValue().equals(text(e, "86"))) valid = false;
            check(findings, "86", "SEQUENCE-UNIQUENESS", valid, "New independent transaction must not reuse any supplied prior independent sequence");
        }
        if (has(e, "155") && q.has("loadedKeyId")) check(findings, "155", "KEY-ID-REUSE",
                text(e, "155").equals(text(q, "loadedKeyId")), "Subsequent TransArmor transaction uses approved loaded Key ID");
        JsonNode print = observation.path("records").path("printLines"), actual = q.path("printedLines");
        if (!print.isMissingNode() && q.has("printedLines")) check(findings, "101", "PRINT-UNCHANGED",
                print.isArray() && actual.isArray() && print.equals(actual), "Print exactly the received source lines, preserving order");
        if ("Declined Totals Response".equals(observation.path("messageFamily").asText()) && print.isArray()) {
            check(findings, "101", "DECLINED-TOTALS-PRINT", print.size() <= 1, "Declined Totals Response does not repeat printer text");
        }
    }

    private static void response(JsonNode e, JsonNode q, String sourceFamily, List<Finding> findings) {
        if (has(e, "83")) {
            String inferred = switch (sourceFamily) {
                case "Financial Transaction Response", "EMV Financial Transaction Response" -> "financial";
                case "Approved Totals Response", "Declined Totals Response" -> "totals";
                case "Electronic Mail Response" -> "electronic-mail";
                case "Communications Test Response" -> "communications-test";
                case "CA Public Key File Load Response" -> "emv-key-load";
                case "Proprietary Data Load Response" -> "proprietary-load";
                default -> null;
            };
            String declared = text(q, "responseCodeFamily"), family = inferred == null ? declared : inferred;
            if (family == null || Atl105ResponseCodeFamilyOracle.expectedCodes(family).isEmpty()) review(findings, "83", "RESPONSE-FAMILY", "Source response-code family required; overloaded codes are not globally interpreted");
            else check(findings, "83", "RESPONSE-FAMILY", (inferred == null || declared == null || inferred.equals(declared))
                    && new Atl105ResponseCodeFamilyOracle().validate(family, text(e, "83")).isValid(), "Response Code belongs to source family; qualifier cannot override parent family");
        }
        if (has(e, "115") && q.has("followingSegments")) {
            JsonNode segments = q.path("followingSegments");
            boolean present = false, valid = segments.isArray();
            for (JsonNode segment : segments) {
                if (!segment.isTextual()) valid = false;
                if ("112".equals(segment.asText())) present = true;
            }
            check(findings, "115", "ADDITIONAL-FLAG", valid && Set.of("0", "1").contains(text(e, "115")) && ("1".equals(text(e, "115")) == present),
                    "Additional Information Flag agrees with observed following Segment 112");
        }
        String settlement = text(q.path("originalElements"), "198");
        if (yes(q, "emvTransaction") && FOLLOW_ON.contains(text(q, "flow") == null ? "" : text(q, "flow")) && Set.of("D", "S").contains(settlement == null ? "" : settlement)) {
            String payment = text(q, "paymentType");
            if (payment == null) review(findings, "198", "SETTLEMENT-FOLLOWON", "Observed follow-on credit/debit processing context required");
            else check(findings, "198", "SETTLEMENT-FOLLOWON", ("D".equals(settlement) ? "CREDIT" : "DEBIT").equals(payment),
                    "EMV follow-on uses original settlement's credit/debit mode");
        }
    }

    private static void emv(JsonNode e, JsonNode q, JsonNode observation, List<Finding> findings) {
        if (yes(q, "emvTransaction") && Set.of("130", "131").contains(observation.path("segment").asText())) {
            require(e, "189", observation, findings, "EMV transaction requires Chip Data Length");
            require(e, "190", observation, findings, "EMV transaction requires Chip Data");
        }
        if (has(e, "188") && q.has("cardSequenceTag")) {
            JsonNode tag = q.path("cardSequenceTag");
            if (tag.isNull()) check(findings, "188", "CARD-SEQUENCE", "   ".equals(text(e, "188")), "Absent card Tag 5F34 uses three spaces");
            else check(findings, "188", "CARD-SEQUENCE", tag.isTextual() && tag.textValue().matches("[0-9]{2}")
                    && ("0" + tag.textValue()).equals(text(e, "188")), "Card Sequence Number agrees with supplied BCD Tag 5F34");
        }
        if (has(e, "190") && q.has("cardInterfaceLastValues")) {
            JsonNode interfaceValues = q.path("cardInterfaceLastValues");
            var decoded = new AppendixREmvChipDataOracle().decode(text(e, "190"));
            Map<String, String> encoded = new HashMap<>();
            boolean valid = decoded.wellFormed() && interfaceValues.isObject() && !interfaceValues.isEmpty();
            for (var entry : decoded.entries()) if (encoded.putIfAbsent(entry.tag(), entry.valueHex()) != null) valid = false;
            var fields = interfaceValues.fields();
            while (fields.hasNext()) {
                var field = fields.next();
                if (!field.getKey().matches("(?:[0-9A-F]{2}){1,2}") || !field.getValue().isTextual()
                        || !field.getValue().textValue().matches("(?:[0-9A-F]{2})*")) valid = false;
            }
            for (var entry : encoded.entrySet()) if (!entry.getValue().equals(text(interfaceValues, entry.getKey()))) valid = false;
            check(findings, "190", "INTERFACE-LAST-VALUE", valid, "Every emitted TLV matches last observed card-interface value; duplicate emitted tags are not last-value selection");
        }
    }

    private static void tokenization(JsonNode e, JsonNode q, List<Finding> findings) {
        String network = text(q, "network");
        for (String id : List.of("195", "196", "197")) {
            if (!has(e, id)) continue;
            if (network == null || !q.path("paymentTokenized").isBoolean()) review(findings, id, "TOKEN-NETWORK", "Payment-tokenization and network evidence required");
            else {
                Set<String> allowed = "195".equals(id) ? Set.of("VISA", "INTERLINK") : "196".equals(id)
                        ? Set.of("VISA", "MASTERCARD") : Set.of("VISA", "MASTERCARD", "INTERLINK", "MAESTRO");
                check(findings, id, "TOKEN-NETWORK", yes(q, "paymentTokenized") && allowed.contains(network), "Element limited to source-listed tokenized payment networks");
            }
        }
        if (has(e, "203") && Set.of("25", "26").contains(text(q, "ecommerceSecureId") == null ? "" : text(q, "ecommerceSecureId"))) {
            String value = text(e, "203");
            check(findings, "203", "MANDATORY-AEVV", value.length() == 58 && value.substring(2, 30).matches("[A-Za-z0-9+/=]{28}"),
                    "Secure ID 25/26 requires populated AEVV, not optional space-filled block");
        }
    }

    private static void operational(JsonNode e, JsonNode q, JsonNode observation, List<Finding> findings) {
        if (q.path("accessCodeNeeded").isBoolean() && !yes(q, "accessCodeNeeded") && !yes(q, "longPauseRequested")) {
            check(findings, "1", "ACCESS-OMISSION", !has(e, "1") && !has(e, "66"), "Unnecessary access code and following pause are both omitted");
        }
        if (has(e, "11") && yes(q, "capkFirstBlock")) check(findings, "11", "FIRST-BLOCK", "000".equals(text(e, "11")), "CAPK first block is 000");
        if (has(e, "11") && yes(q, "proprietaryLastBlock")) check(findings, "11", "LAST-BLOCK", "000".equals(text(e, "11")), "Proprietary final block is 000");
        if (has(e, "11") && yes(q, "resumeInterruptedBlock")) {
            String last = text(q, "lastInterruptedBlock");
            if (last == null) review(findings, "11", "BLOCK-RESUME", "Interrupted block evidence required");
            else check(findings, "11", "BLOCK-RESUME", last.matches("[0-9]{3}") && last.equals(text(e, "11")), "Resume request identifies observed interrupted block");
        }
        if (q.has("processedCurrency") && q.has("merchantCurrency")) {
            String actual = text(q, "processedCurrency"), assigned = text(q, "merchantCurrency");
            check(findings, "20", "MERCHANT-CURRENCY", actual != null && assigned != null && assigned.matches("[0-9]{3}") && assigned.equals(actual),
                    "Processing uses merchant currency, not an arbitrary supplied transaction currency");
        }
        if (has(e, "44") && q.path("lineRemainedConnected").isBoolean() && Set.of("0", "1").contains(text(e, "44"))) {
            check(findings, "44", "CONNECTION", yes(q, "lineRemainedConnected") == "1".equals(text(e, "44")), "Single closes communication; multimessage remains established");
        }
        if ("8".equals(text(e, "30")) && q.has("keyUpdateScheduled")) check(findings, "30", "KEY-UPDATE", yes(q, "keyUpdateScheduled"), "Download Indicator 8 requires Key/Key ID update");
        if (has(e, "77") && yes(q, "dynamicCardTableActive")) {
            JsonNode codes = q.path("dynamicProductCodes");
            if (codes.isMissingNode()) review(findings, "77", "DYNAMIC-PRODUCT", "Observed dynamic product table is required");
            else {
                boolean valid = codes.isArray(), present = false;
                for (JsonNode code : codes) {
                    if (!code.isTextual() || !code.textValue().matches("[0-9]{3}")) valid = false;
                    if (text(e, "77").equals(code.asText())) present = true;
                }
                check(findings, "77", "DYNAMIC-PRODUCT", valid && present, "Dynamic table permits only observed defined product codes");
            }
        }
        if (has(e, "157") && q.path("loadApproved").isBoolean() && !yes(q, "loadApproved")) {
            String displayed = text(q, "displayedLoadMessage");
            if (displayed == null) review(findings, "157", "DECLINED-DISPLAY", "Observed display of returned load decline required");
            else check(findings, "157", "DECLINED-DISPLAY", text(e, "157").equals(displayed), "Returned TransArmor load decline message is displayed unchanged");
        }
        if (has(e, "176") && text(e, "176").startsWith("99999") && q.has("cardTablePresent")) check(findings, "176", "NO-CARD-TABLE",
                q.path("cardTablePresent").isBoolean() && !yes(q, "cardTablePresent"), "99999 prefix indicates no card table");
        JsonNode schedule = q.get("cutSchedule");
        if (schedule != null) {
            if (!schedule.isObject() || !schedule.path("automatic").isBoolean() || !schedule.path("clerkCompleted").isBoolean()) {
                add(findings, "23", "CUT-SCHEDULE", Status.INVALID, "Cut schedule requires an object with explicit automatic/clerk-completed booleans");
            } else if (yes(schedule, "automatic") && !yes(schedule, "clerkCompleted")) {
                String cut = text(schedule, "cutAt"), start = text(schedule, "startedAt");
                if (cut == null || start == null || !has(e, "23")) review(findings, "23", "CUT-SCHEDULE", "Observed Element 23, offset-qualified cut and start timestamps required");
                else if (text(e, "23").startsWith("24") || text(e, "23").endsWith("60")) {
                    review(findings, "23", "CUT-SCHEDULE", "Source permits hour 24/minute 60; explicit clock-convention evidence is needed before mapping to ISO timestamps");
                }
                else {
                    boolean valid;
                    try {
                        OffsetDateTime cutAt = OffsetDateTime.parse(cut);
                        valid = text(e, "23").equals(cutAt.format(java.time.format.DateTimeFormatter.ofPattern("HHmm")))
                                && cutAt.minusMinutes(30).toInstant().equals(OffsetDateTime.parse(start).toInstant());
                    } catch (DateTimeException exception) { valid = false; }
                    check(findings, "23", "CUT-SCHEDULE", valid, "Automatic uncompleted settlement begins 30 minutes before observed cut; no time zone inferred");
                }
            } else review(findings, "23", "CUT-SCHEDULE", "Automatic uncompleted settlement condition is not active");
        }
        JsonNode dial = q.get("dialHistory");
        if (dial != null) {
            boolean valid = dial.isObject() && dial.path("attempts").isArray() && !dial.path("attempts").isEmpty()
                    && text(dial, "primary") != null && !text(dial, "primary").isBlank()
                    && text(dial, "secondary") != null && !text(dial, "secondary").isBlank()
                    && dial.path("primaryAttemptsAllowed").isIntegralNumber() && dial.path("primaryAttemptsAllowed").canConvertToInt()
                    && dial.path("primaryAttemptsAllowed").intValue() > 0;
            int failures = 0;
            boolean secondaryStarted = false;
            for (JsonNode attempt : dial.path("attempts")) {
                String phone = text(attempt, "number");
                if (!attempt.isObject() || !attempt.path("succeeded").isBoolean()) { valid = false; continue; }
                if (phone != null && phone.equals(text(dial, "primary"))) {
                    if (secondaryStarted) valid = false;
                    if (!yes(attempt, "succeeded")) failures++;
                } else if (phone != null && phone.equals(text(dial, "secondary"))) {
                    if (failures < dial.path("primaryAttemptsAllowed").asInt()) valid = false;
                    secondaryStarted = true;
                } else valid = false;
            }
            check(findings, "75", "DIAL-ORDER", valid, "Dial primary first; secondary only after observed failed primary attempts exhausted; no tertiary dial");
        }
    }

    private static void equalOriginal(JsonNode e, JsonNode original, String id, JsonNode observation, List<Finding> findings, String rule, String reason) {
        if (!has(e, id)) require(e, id, observation, findings, reason);
        else if (!has(original, id)) review(findings, id, rule, "Original observed value required");
        else check(findings, id, rule, text(e, id).equals(text(original, id)), reason);
    }
    private static boolean equalAmount(String first, String second) { return digits(first) && digits(second) && new BigInteger(first).equals(new BigInteger(second)); }
    private static boolean digits(String value) { return value != null && value.matches("[0-9]+"); }
    private static boolean yes(JsonNode node, String field) { return node.path(field).isBoolean() && node.path(field).booleanValue(); }
    private static boolean has(JsonNode node, String id) { String value = text(node, id); return value != null && !value.isEmpty(); }
    private static String text(JsonNode node, String field) { JsonNode value = node.get(field); return value != null && value.isTextual() ? value.textValue() : null; }
    private static void require(JsonNode e, String id, JsonNode o, List<Finding> findings, String reason) {
        if (!has(e, id)) add(findings, id, "REQUIRED-CONTEXT", "PARTIAL".equals(o.path("completeness").asText()) ? Status.REVIEW_REQUIRED : Status.INVALID, reason);
    }
    private static void review(List<Finding> findings, String id, String rule, String reason) { add(findings, id, rule, Status.REVIEW_REQUIRED, reason); }
    private static void check(List<Finding> findings, String id, String rule, boolean valid, String reason) { add(findings, id, rule, valid ? Status.CHECKS_PASSED : Status.INVALID, reason); }
    private static void add(List<Finding> findings, String id, String rule, Status status, String reason) { findings.add(new Finding(id, status, "CH13-E" + id + "-CONTEXT-" + rule, reason)); }
    private static Status status(String status) { return "PASS".equals(status) ? Status.CHECKS_PASSED : "FAIL".equals(status) ? Status.INVALID : Status.REVIEW_REQUIRED; }
}
