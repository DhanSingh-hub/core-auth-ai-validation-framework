package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;

import static com.coreauth.validator.canonical.AppendixILogicalDataValidator.Status.*;

public final class AppendixIScopedCandidateValidator {
    public static final Set<String> CANDIDATE_IDS = Set.of("APPI-C010-01", "APPI-C010-02",
            "APPI-C011-01", "APPI-C011-02", "APPI-C011-03", "APPI-C012-01",
            "APPI-C013-01", "APPI-C013-02", "APPI-C014-01", "APPI-C015-01",
            "APPI-C017-01", "APPI-C018-01", "APPI-C018-02", "APPI-C019-01", "APPI-C019-02",
            "APPI-C020-01", "APPI-C021-01", "APPI-C021-02", "APPI-C022-01", "APPI-C022-02",
            "APPI-C024-01", "APPI-C024-02", "APPI-C025-01", "APPI-C026-01",
            "APPI-C027-01", "APPI-C028-01", "APPI-C029-01", "APPI-C029-02",
            "APPI-C030-01", "APPI-C030-02", "APPI-C031-01",
            "APPI-C032-01", "APPI-C032-02", "APPI-C032-04", "APPI-C032-05",
            "APPI-C032-06", "APPI-C032-07", "APPI-C032-08", "APPI-C032-09",
            "APPI-C032-10", "APPI-C032-11", "APPI-C032-12Y", "APPI-C032-12N",
            "APPI-C032-13Y", "APPI-C032-13H", "APPI-C032-14", "APPI-C033-01",
            "APPI-C034-01", "APPI-C034-02", "APPI-C035-01", "APPI-C036-01", "APPI-C036-02",
            "APPI-C037-01", "APPI-C038-01", "APPI-C039-01", "APPI-C040-01", "APPI-C040-02",
            "APPI-C041-01", "APPI-C042-01", "APPI-C043-01", "APPI-C044-01",
            "APPI-C045-01", "APPI-C046-01", "APPI-C047-01", "APPI-C048-01", "APPI-C048-02",
            "APPI-T049-C001", "APPI-T049-C002", "APPI-T049-C003", "APPI-T049-C004",
            "APPI-T049-C005", "APPI-T049-C006", "APPI-T049-C007", "APPI-T049-C009",
            "APPI-T049-C010", "APPI-T049-C011", "APPI-T049-C013", "APPI-T049-C016",
            "APPI-T049-C017", "APPI-T049-C018", "APPI-T049-C020", "APPI-T049-C021",
            "APPI-T049-C022", "APPI-T049-C023", "APPI-T049-C024", "APPI-T049-C025",
            "APPI-T049-C026", "APPI-T049-C027", "APPI-T049-C028", "APPI-T049-C030",
            "APPI-C050-OUTER-001", "APPI-C051-OUTER-001", "APPI-C053-OUTER-001",
            "APPI-C054-OUTER-001", "APPI-C055-OUTER-001", "APPI-C057-OUTER-001",
            "APPI-C058-OUTER-001", "APPI-C059-OUTER-001", "APPI-C062-OUTER-001",
            "APPI-C062-N01-001", "APPI-C062-N02-001");

    public AppendixILogicalDataValidator.Result validate(JsonNode candidate) {
        List<AppendixILogicalDataValidator.Finding> findings = new ArrayList<>();
        if (candidate == null || !candidate.isObject()) {
            add(findings, "APPI-CANDIDATE-SHAPE", FAIL, "A structured logical candidate is required.");
            return new AppendixILogicalDataValidator.Result(findings);
        }
        String id = text(candidate, "candidateId");
        if (!CANDIDATE_IDS.contains(id)) {
            add(findings, "APPI-CANDIDATE-SCOPE", NOT_ASSERTABLE,
                    "No implemented predicate for this logical candidate.");
            return new AppendixILogicalDataValidator.Result(findings);
        }
        if (!id.substring(6, 9).equals(text(candidate, "tableId"))) {
            add(findings, "APPI-CANDIDATE-IDENTITY", FAIL, "Candidate and table identities disagree.");
            return new AppendixILogicalDataValidator.Result(findings);
        }
        if ("APPI-C050-OUTER-001".equals(id) || "APPI-C051-OUTER-001".equals(id)) {
            return validateAdditionalCardField(candidate, id);
        }
        if (id.startsWith("APPI-C053-")) return validateTable053(candidate);
        if (id.startsWith("APPI-C054-")) return validateTable054(candidate);
        if (id.startsWith("APPI-C055-")) return validateTable055(candidate);
        if (id.startsWith("APPI-C057-") || id.startsWith("APPI-C058-") || id.startsWith("APPI-C059-")) {
            return validateMerchantLocation(candidate, id);
        }
        if (id.startsWith("APPI-C062-")) return validateTable062(candidate);
        if (id.startsWith("APPI-T049-C")) return validateTable049(candidate);
        JsonNode fields = candidate.path("logicalFields");
        JsonNode context = candidate.path("context");
        switch (text(candidate, "tableId")) {
            case "010" -> {
                check(findings, "APPI-T010-LAYOUT", matches(fields, "visaBID", "[0-9A-Fa-f]{10}")
                                && matches(fields, "visaTAP", "[0-9A-Fa-f]{12}")
                                && printable(fields, "tppVarId", 6, 6)
                                && "  ".equals(text(fields, "reserved")),
                        "BID/TAP widths and hexadecimal syntax, TPP width and reserved fill only.");
                JsonNode flags = fields.path("context");
                if (!booleans(flags, "bidUsed", "tapUsed", "tppVarIncluded")) {
                    add(findings, "APPI-T010-ZERO-FILL", REVIEW_REQUIRED,
                            "Explicit identifier-use flags are required; assignment is not inferred.");
                } else {
                    boolean bid = flags.path("bidUsed").booleanValue();
                    boolean tap = flags.path("tapUsed").booleanValue();
                    boolean tpp = flags.path("tppVarIncluded").booleanValue();
                    check(findings, "APPI-T010-ZERO-FILL",
                            (bid || !(tap || tpp) || "0".repeat(10).equals(text(fields, "visaBID")))
                                    && (tap || !(bid || tpp) || "0".repeat(12).equals(text(fields, "visaTAP"))),
                            "Unused BID/TAP zero fill under explicit supplied preconditions only.");
                }
            }
            case "011" -> {
                if (!booleans(context, "taxable", "salesTaxIncluded")) {
                    add(findings, "APPI-T011-MAPPING", REVIEW_REQUIRED, "Tax context is required.");
                } else if (!context.path("taxable").booleanValue()
                        && context.path("salesTaxIncluded").booleanValue()) {
                    add(findings, "APPI-T011-MAPPING", FAIL, "Nontaxable context cannot include sales tax.");
                } else {
                    String expected = !context.path("taxable").booleanValue() ? "N"
                            : context.path("salesTaxIncluded").booleanValue() ? "Y" : " ";
                    check(findings, "APPI-T011-MAPPING", expected.equals(text(candidate, "data")),
                            "Tax indicator must match the supplied taxable/tax-included context.");
                }
            }
            case "012" -> check(findings, "APPI-T012-MAPPING",
                    "M".equals(text(fields, "medical")) && "B".equals(text(fields, "billPayment"))
                            && "E".equals(text(fields, "ecommerceAggregate")) && "H".equals(text(fields, "hotel")),
                    "The four printed market mappings only; operational availability is unassessed.");
            case "013" -> {
                String kind = text(context, "paymentKind");
                if (!Set.of("Recurring", "Installment").contains(kind)) {
                    add(findings, "APPI-T013-MAPPING", REVIEW_REQUIRED, "Explicit payment kind is required.");
                } else {
                    check(findings, "APPI-T013-MAPPING",
                            ("Recurring".equals(kind) ? "R" : "I").equals(text(candidate, "data")),
                            "Payment indicator must match the supplied payment kind.");
                }
                if ("R".equals(text(candidate, "data"))) {
                    String card = text(context, "cardType");
                    String brand = text(context, "binBrandClassification");
                    if (card.isEmpty() || !Set.of("Visa", "MasterCard", "Discover", "Amex").contains(brand)) {
                        add(findings, "APPI-T013-RECURRING", REVIEW_REQUIRED,
                                "Credit card type and classified BIN brand are required; no BIN database is used.");
                    } else {
                        check(findings, "APPI-T013-RECURRING", "020".equals(card),
                                "Recurring eligibility against supplied classification only, not actual BIN validity.");
                    }
                }
            }
            case "014" -> {
                check(findings, "APPI-T014-WIDTH", printable(fields, "requestUserData", 1, 15),
                        "Printable ASCII candidate user data within fifteen characters only.");
                check(findings, "APPI-T014-ECHO", !text(fields, "requestUserData").isEmpty()
                                && text(fields, "requestUserData").equals(text(fields, "responseEcho")),
                        "Supplied logical echo must equal the request value; no host response is executed.");
                if (!booleans(fields, "issuerForwarded")) {
                    add(findings, "APPI-T014-NONFORWARD", REVIEW_REQUIRED,
                            "Explicit logical forwarding evidence is required.");
                } else {
                    check(findings, "APPI-T014-NONFORWARD", !fields.path("issuerForwarded").booleanValue(),
                            "Supplied forwarding assertion must be false; no issuer trace is verified.");
                }
            }
            case "015" -> {
                check(findings, "APPI-T015-WIDTH", printable(fields, "associatedResponseRRN", 15, 15)
                                && printable(fields, "subsequentRequestRRN", 15, 15),
                        "Printable ASCII reference widths only; actual network issuance is unassessed.");
                JsonNode flags = fields.path("context");
                if (!"Discover".equals(text(flags, "brand")) || !"MIT".equals(text(flags, "initiation"))
                        || !"Subsequent".equals(text(flags, "cofState"))) {
                    add(findings, "APPI-T015-RELATION", REVIEW_REQUIRED,
                            "This relation check is bounded to Discover subsequent merchant-initiated COF.");
                } else {
                    check(findings, "APPI-T015-RELATION", !text(fields, "associatedResponseRRN").isEmpty()
                                    && text(fields, "associatedResponseRRN").equals(text(fields, "subsequentRequestRRN")),
                            "Subsequent request reference must equal the supplied associated response reference.");
                }
            }
            case "017" -> {
                check(findings, "APPI-T017-VALUE", "34".equals(text(candidate, "data")),
                        "Suspected-fraud indicator has fixed value 34.");
                if (!booleans(context, "suspectedFraud") || text(context, "transactionClass").isEmpty()
                        || text(context, "cardPresence").isEmpty() || text(context, "brand").isEmpty()) {
                    add(findings, "APPI-T017-CONTEXT", REVIEW_REQUIRED,
                            "Explicit reversal, fraud, presence and brand context is required.");
                } else if (!"MasterCard".equals(text(context, "brand"))
                        || !"CNP".equals(text(context, "cardPresence"))) {
                    add(findings, "APPI-T017-CONTEXT", REVIEW_REQUIRED,
                            "Other brand or presence classifications are outside this candidate's context check.");
                } else {
                    check(findings, "APPI-T017-CONTEXT",
                            context.path("suspectedFraud").booleanValue()
                                    && "Purchase reversal request".equals(text(context, "transactionClass")),
                            "This candidate checks the supplied MasterCard CNP fraud-reversal context only.");
                }
            }
            case "018" -> {
                JsonNode tableContext = fields.path("context");
                String terminalNumber = text(fields, "cardAcceptorTerminalId");
                check(findings, "APPI-T018-WIDTH", terminalNumber.matches("[0-9]{4}"),
                        "Four-digit POS entry-point identifier only.");
                JsonNode entryPointCount = tableContext.path("entryPointCount");
                if (!entryPointCount.isIntegralNumber() || entryPointCount.intValue() < 1) {
                    add(findings, "APPI-T018-IDENTITY", REVIEW_REQUIRED,
                            "Explicit positive POS entry-point count is required.");
                } else if (entryPointCount.intValue() == 1) {
                    check(findings, "APPI-T018-IDENTITY",
                            terminalNumber.equals(text(fields, "terminalDeviceNumber")),
                            "Single-entry identifier must match the supplied terminal device number.");
                    } else if (tableContext.path("multiControllerTAVE").isBoolean()
                            && tableContext.path("multiControllerTAVE").booleanValue()) {
                    check(findings, "APPI-T018-PRESENCE", fields.path("tablePresent").asBoolean(false),
                            "Table 018 is required in the supplied multi-controller TA-VE context.");
                    check(findings, "APPI-T018-ASSIGNMENT",
                            terminalNumber.equals(text(fields, "entryPointAssignment")),
                            "POS entry-point number must match the supplied host/controller/device assignment.");
                } else {
                    add(findings, "APPI-T018-IDENTITY", REVIEW_REQUIRED,
                            "Multiple-entry assignment rules are checked only for explicit multi-controller TA-VE context.");
                }
            }
            case "019" -> {
                JsonNode tableContext = fields.path("context");
                String rechargeKind = text(tableContext, "rechargeKind");
                if ("PIN on Receipt".equals(rechargeKind) || "Top Up".equals(rechargeKind)) {
                    String expected = "PIN on Receipt".equals(rechargeKind) ? "01" : "02";
                    check(findings, "APPI-T019-R001", expected.equals(text(fields, "rechargeType")),
                            "Recharge type must match the supplied PIN on Receipt or Top Up context.");
                } else {
                    add(findings, "APPI-T019-R001", REVIEW_REQUIRED,
                            "Default value 00 applicability and other transaction contexts are not inferred.");
                }
                if ("Blackhawk".equals(text(tableContext, "provider"))
                        && ("PIN on Receipt".equals(rechargeKind) || "Top Up".equals(rechargeKind))) {
                    check(findings, "APPI-T019-R002", "SP2".equals(text(fields, "programIdentifier")),
                            "Blackhawk PIN on Receipt and Top Up require Program Identifier SP2.");
                } else {
                    add(findings, "APPI-T019-R002", REVIEW_REQUIRED,
                            "The SP2 companion rule is checked only for explicit Blackhawk PIN on Receipt or Top Up context.");
                }
            }
                    case "020" -> {
                    if ("Top Up".equals(text(context, "transactionClass"))) {
                        check(findings, "APPI-T020-R001", text(candidate, "data").matches("[0-9]{1,20}"),
                            "Top Up phone data must be numeric and no longer than 20 digits.");
                        add(findings, "APPI-T020-SERVICE-VALIDITY", REVIEW_REQUIRED,
                            "Phone-number validity or routability is not established by the bounded syntax check.");
                    } else {
                        add(findings, "APPI-T020-R001", REVIEW_REQUIRED,
                            "Table 020 applicability is asserted only for the explicit Top Up context.");
                    }
                    }
            case "021" -> {
                String flag = text(candidate, "data");
                if ("X".equals(flag)) {
                    String phase = text(context, "transactionPhase");
                    String original = text(context, "originalProcessing");
                    if (phase.isEmpty() || original.isEmpty()) {
                        add(findings, "APPI-T021-R002", REVIEW_REQUIRED,
                                "Explicit follow-up phase and original-processing context are required for X.");
                    } else {
                        check(findings, "APPI-T021-R002",
                                "Follow-up".equals(phase) && "Non-traditional Signature Debit".equals(original),
                                "X is limited to follow-up transactions whose original was non-traditional Signature Debit.");
                    }
                    add(findings, "APPI-T021-AVAILABILITY", REVIEW_REQUIRED,
                            "Signature Debit feature availability requires external confirmation.");
                } else if (booleans(context, "acceptsPINlessPOSSettlement",
                        "acceptsTransactionAttributesSettlement")) {
                    boolean pinless = context.path("acceptsPINlessPOSSettlement").booleanValue();
                    boolean attributes = context.path("acceptsTransactionAttributesSettlement").booleanValue();
                    if (attributes && !pinless) {
                        add(findings, "APPI-T021-R001", REVIEW_REQUIRED,
                                "Transaction Attributes Settlement without PINless acceptance is outside this mapping.");
                    } else {
                        String expected = pinless ? attributes ? "2" : "1" : "0";
                        check(findings, "APPI-T021-R001", expected.equals(flag),
                                "Settlement flag must match the supplied PINless and Transaction Attributes capabilities.");
                    }
                } else {
                    add(findings, "APPI-T021-R001", REVIEW_REQUIRED,
                            "Explicit settlement capability context is required to interpret values 0, 1, or 2.");
                }
            }
            case "022" -> {
                String data = text(candidate, "data");
                check(findings, "APPI-T022-WIDTH", data.matches("[0-9]{2}"),
                        "Originating-device indicator must be a two-digit numeric value.");
                String device = text(context, "device");
                String location = text(context, "paymentApplicationLocation");
                String expected = !"Phone".equals(device) ? null
                        : "Host processor TEE".equals(location) ? "16"
                        : "Host processor".equals(location) ? "14" : null;
                if (expected != null) {
                    check(findings, "APPI-T022-CLASSIFICATION", expected.equals(data),
                            "Phone payment application in a host processor is 14; in the host processor TEE it is 16.");
                } else if ("14".equals(data) || "16".equals(data)) {
                    add(findings, "APPI-T022-CLASSIFICATION", REVIEW_REQUIRED,
                            "Explicit phone and payment-application location context is required for codes 14/16.");
                }
            }
                    case "024" -> {
                    String type = text(fields, "micrType");
                if (fields.has("associatedRawMicr")) {
                    check(findings, "APPI-T024-R001", "09".equals(type)
                            && "Unchanged raw MICR".equals(text(fields, "processing")),
                        "The supplied unchanged raw-MICR example selects type 09.");
                        add(findings, "APPI-T024-FORMAT", REVIEW_REQUIRED,
                            "Raw MICR field grammar and wire serialization remain unresolved.");
                } else if (fields.has("associatedMicr")) {
                        JsonNode components = fields.path("components");
                        boolean hasComponents = !text(components, "aba").isEmpty()
                            && !text(components, "account").isEmpty()
                            && !text(components, "checkNumber").isEmpty();
                        check(findings, "APPI-T024-R001",
                        "T$".equals(type) && hasComponents
                            && text(fields, "associatedMicr").matches("T[0-9]+A[0-9]+C[0-9]+"),
                            "T$ selects the listed T-ABA-A-account-C-check-number logical layout only.");
                        add(findings, "APPI-T024-FORMAT", REVIEW_REQUIRED,
                            "The source's numeric type attribute conflicts with the documented T$ alphanumeric code.");
                    } else {
                        add(findings, "APPI-T024-R001", REVIEW_REQUIRED,
                            "Only the explicit 09 and T$ examples are covered by these scoped candidates.");
                    }
                    }
                        case "025" -> {
                        String giftCardNumber = text(candidate, "data");
                        check(findings, "APPI-T025-R001",
                            giftCardNumber.matches("[A-Za-z0-9]{1,19}")
                                && giftCardNumber.equals(text(context, "associatedGiftCard")),
                            "Gift Card Number must be 1-19 alphanumeric characters and match the supplied synthetic associated-card value.");
                        if (text(context, "associatedGiftCard").isEmpty()) {
                            add(findings, "APPI-T025-IDENTITY", REVIEW_REQUIRED,
                                "Associated card identity context is required; issuer/card validity is not inferred.");
                        }
                        }
                case "026" -> {
                    String orderId = text(fields, "orderId");
                    check(findings, "APPI-T026-R001", orderId.matches("[A-Za-z0-9 ]{1,12}")
                                    && orderId.equals(text(fields, "firstTenderOrderId"))
                                    && orderId.equals(text(fields, "secondTenderOrderId")),
                            "The split-tender fixture must share one 1-12 character alphanumeric Order ID.");
                }
                case "027" -> {
                    String customerName = text(candidate, "data");
                    JsonNode declaredCount = fields.path("payloadCharacterCount");
                    check(findings, "APPI-T027-R001", customerName.matches("[A-Za-z0-9 ]{1,50}")
                                    && declaredCount.isIntegralNumber()
                                    && declaredCount.intValue() == customerName.length(),
                            "ASCII customer-name fragment must be at most 50 characters with a matching supplied count.");
                }
                case "028" -> {
                    String useUniqueId = text(fields, "useUniqueId");
                    check(findings, "APPI-T028-R001",
                            useUniqueId.length() == 1 && Set.of("Y", "N", " ").contains(useUniqueId),
                            "Use Unique ID accepts the one-character values Y, N, or space.");
                    if ("Y".equals(useUniqueId)) {
                        if (!fields.path("responseUUIDPresent").isBoolean()) {
                            add(findings, "APPI-T028-R002", REVIEW_REQUIRED,
                                    "A response UUID-presence observation is required when the request flag is Y.");
                        } else {
                            check(findings, "APPI-T028-R002", fields.path("responseUUIDPresent").booleanValue(),
                                    "A Y request asks for the UUID in the response; no host response is executed.");
                        }
                    }
                }
                case "029" -> {
                    JsonNode originalPresent = fields.path("originalResponseUUIDPresent");
                    JsonNode tablePresent = fields.path("subsequentTablePresent");
                    if (!originalPresent.isBoolean() || !tablePresent.isBoolean()) {
                        add(findings, "APPI-T029-R001", REVIEW_REQUIRED,
                                "Explicit original-response UUID presence and subsequent-table presence are required.");
                    } else {
                        boolean received = originalPresent.booleanValue();
                        boolean included = tablePresent.booleanValue();
                        boolean consistent = received == included;
                        if (consistent && received) {
                            String originalId = text(fields, "originalResponseUUID");
                            String subsequentId = text(fields, "subsequentUUID");
                            consistent = originalId.matches(".{1,41}") && originalId.equals(subsequentId);
                        }
                        check(findings, "APPI-T029-R001", consistent,
                                "Include Table 029 iff the original response supplied a UUID, and retain that value.");
                    }
                }
                            case "030" -> {
                            JsonNode tableContext = fields.path("context");
                            if ("29".equals(text(fields, "terminalType"))) {
                                boolean validContext = "MasterCard".equals(text(tableContext, "brand"))
                                    && tableContext.path("recurringInternet").asBoolean(false)
                                    && tableContext.path("initialMobileIssuedPaymentToken").asBoolean(false);
                                check(findings, "APPI-T030-R004", validContext,
                                    "Terminal type 29 is limited to the supplied MasterCard recurring internet/token context.");
                            } else {
                                boolean requiredContext = tableContext.path("emv").asBoolean(false)
                                    || tableContext.path("internetBased").asBoolean(false)
                                    || tableContext.path("credentialOnFile").asBoolean(false);
                                if (requiredContext) {
                                check(findings, "APPI-T030-R001", fields.path("tablePresent").asBoolean(false),
                                    "Table 030 is required in the supplied EMV, internet-based, or Credential-on-File context.");
                                } else {
                                add(findings, "APPI-T030-R001", REVIEW_REQUIRED,
                                    "Table 030 presence is asserted only for explicit source-listed transaction contexts.");
                                }
                                String security = text(fields.path("qComponents"), "security");
                                if ("No concern".equals(text(tableContext, "security"))) {
                                check(findings, "APPI-T030-R002", "0".equals(security),
                                    "Appendix Q security value 0 means no security concern.");
                                } else {
                                add(findings, "APPI-T030-R002", REVIEW_REQUIRED,
                                    "Only the explicit no-security-concern mapping is checked by this candidate.");
                                }
                            }
                            }
                            case "031" -> {
                            String capability = text(fields, "certifiedCapability");
                            check(findings, "APPI-T031-R001",
                                Set.of("00", "01", "02", "03", "04", "05", "06", "07", "09", "12")
                                    .contains(capability),
                                "Terminal capability must be one of the values listed in the source table.");
                            check(findings, "APPI-T031-R002", capability.matches("[0-9]{2}")
                                    && capability.equals(text(fields, "emvCapability"))
                                    && capability.equals(text(fields, "magstripeCapability"))
                                    && capability.equals(text(fields, "keyedCapability")),
                                "Certified terminal capability remains the same for EMV, magstripe, and keyed entry modes.");
                            }
                        case "032" -> {
                        JsonNode tableContext = fields.path("context");
                        String subTableId = text(fields, "subTableId");
                        String value = text(fields, "value");
                        switch (id) {
                            case "APPI-C032-01" -> {
                            boolean valid = "01".equals(subTableId)
                                && "001".equals(text(fields, "subTableLength"))
                                && "M".equals(value)
                                && Set.of("Amex", "MasterCard", "Maestro", "Discover")
                                .contains(text(tableContext, "brand"))
                                && "mPOS".equals(text(tableContext, "device"))
                                && tableContext.path("attended").asBoolean(false)
                                && !tableContext.path("cardholderActivated").asBoolean(true);
                            check(findings, "APPI-T032-R002", valid,
                                "Subtable 01 value M is limited to the listed brands and attended, non-cardholder-activated mPOS.");
                            }
                            case "APPI-C032-02" -> {
                            boolean required = "Amex".equals(text(tableContext, "brand"))
                                && tableContext.path("cardPresent").asBoolean(false)
                                && "Credit".equals(text(tableContext, "cardKind"))
                                && tableContext.path("giftCardIncluded").asBoolean(false);
                            check(findings, "APPI-T032-R003", !required
                                    || (fields.path("subTablePresent").asBoolean(false)
                                    && "02".equals(subTableId)
                                    && "004".equals(text(fields, "subTableLength"))
                                    && "1000".equals(value)),
                                "Amex card-present credit gift-card purchases require subtable 02 value 1000.");
                            }
                            case "APPI-C032-04" -> check(findings, "APPI-T032-R004",
                                "FD uComm".equals(text(tableContext, "gateway"))
                                    && "04".equals(subTableId)
                                    && "001".equals(text(fields, "subTableLength"))
                                    && "1".equals(value),
                                "FD uComm gateway selection uses the documented one-character value 1.");
                            case "APPI-C032-05" -> {
                            if (!"WEX".equals(text(tableContext, "network"))
                                || !tableContext.path("hostPromptsSupported").isBoolean()) {
                                add(findings, "APPI-T032-R005", REVIEW_REQUIRED,
                                    "WEX host-prompt support context is required for subtable 05.");
                            } else {
                                String expected = tableContext.path("hostPromptsSupported").booleanValue() ? "1" : "0";
                                check(findings, "APPI-T032-R005", "05".equals(subTableId)
                                        && "001".equals(text(fields, "subTableLength"))
                                        && expected.equals(value),
                                    "WEX host-prompt indicator maps supported to 1 and unsupported to 0.");
                            }
                            }
                            case "APPI-C032-06" -> {
                            if (!tableContext.path("nfcTokenizationSupported").isBoolean()) {
                                add(findings, "APPI-T032-R006", REVIEW_REQUIRED,
                                    "Explicit NFC tokenization capability is required for subtable 06.");
                            } else {
                                String expected = tableContext.path("nfcTokenizationSupported").booleanValue() ? "1" : "2";
                                check(findings, "APPI-T032-R006", "06".equals(subTableId)
                                        && "001".equals(text(fields, "subTableLength"))
                                        && expected.equals(value),
                                    "NFC tokenization maps supported to 1 and unsupported to 2.");
                            }
                            }
                            case "APPI-C032-07" -> {
                            String expected = switch (text(tableContext, "wallet")) {
                                case "Apple Pay" -> "01";
                                case "Android Pay" -> "02";
                                case "Samsung Pay" -> "03";
                                case "Walmart Pay" -> "04";
                                case "Walmart ScanN Go" -> "05";
                                case "Sams Club ScanN Go" -> "06";
                                case "Click to Pay" -> "07";
                                case "WEX Wallet" -> "08";
                                default -> "";
                            };
                            check(findings, "APPI-T032-R007", !expected.isEmpty()
                                    && "07".equals(subTableId)
                                    && "002".equals(text(fields, "subTableLength"))
                                    && expected.equals(value)
                                    && expected.equals(text(fields.path("printedMappings"), "wexWallet")),
                                "Wallet identifier must match the printed mapping for the supplied wallet.");
                            }
                            case "APPI-C032-08" -> {
                            String phase = text(tableContext, "cofPhase");
                            boolean validIndicator = "Initial".equals(phase)
                                ? "I".equals(text(fields, "initialValue"))
                                : "Subsequent".equals(phase) && "C".equals(text(fields, "subsequentValue"));
                            check(findings, "APPI-T032-R008", "08".equals(subTableId)
                                    && "001".equals(text(fields, "subTableLength")) && validIndicator,
                                "Subtable 08 uses I for initial and C for subsequent credential-on-file transactions.");
                            String initiation = text(tableContext, "initiation");
                            if ("MIT".equals(initiation) || "CIT".equals(initiation)) {
                                String expected = "MIT".equals(initiation) ? "M" : "C";
                                check(findings, "APPI-T032-R009", expected.equals(text(fields, "companion049Sub02"))
                                        && "1".equals(text(fields, "companion049Sub02Length")),
                                    "Table 049 subtable 02 initiation value matches MIT/CIT context.");
                            } else {
                                add(findings, "APPI-T032-R009", REVIEW_REQUIRED,
                                    "Explicit MIT or CIT initiation context is required for the companion value.");
                            }
                            if ("Subsequent".equals(phase) && "C".equals(text(fields, "subsequentValue"))) {
                                check(findings, "APPI-T032-R010", "10".equals(text(fields, "panEntryMode")),
                                    "Subsequent credential-on-file context uses POS Entry Mode 10.");
                            }
                            if ("MIT".equals(initiation) && "Recurring".equals(text(tableContext, "instructionKind"))) {
                                check(findings, "APPI-T032-R011", "R".equals(text(fields, "paymentIndicator"))
                                        && !text(fields, "retainedOriginalTransactionId").isEmpty(),
                                    "Recurring stand-in MIT carries its indicator and a supplied original transaction identifier.");
                            }
                            }
                            case "APPI-C032-09" -> {
                            boolean initialEstimate = fields.path("initialIndicatorPresent").asBoolean(false)
                                && !tableContext.path("finalAmountKnownAtInitial").asBoolean(true);
                            check(findings, "APPI-T032-R012", "09".equals(subTableId)
                                    && "001".equals(text(fields, "subTableLength"))
                                    && (!initialEstimate || "1".equals(value))
                                    && !fields.path("incrementalIndicatorPresent").asBoolean(false),
                                "Estimated Auth indicator 1 is limited to the supplied initial/unknown-final context and omitted on incremental.");
                            add(findings, "APPI-T032-ELIGIBILITY", REVIEW_REQUIRED,
                                "Merchant eligibility and Appendix AE lifecycle outcomes are not established by this fixture.");
                            }
                            case "APPI-C032-10" -> {
                            if (!tableContext.path("storedCredentialTransferredToMerchant").isBoolean()
                                || !tableContext.path("fundsFlowInterrupted").isBoolean()) {
                                add(findings, "APPI-T032-R015", REVIEW_REQUIRED,
                                    "Explicit wallet transfer and funds-flow context are required.");
                            } else {
                                boolean passThrough = tableContext.path("storedCredentialTransferredToMerchant").booleanValue()
                                    && !tableContext.path("fundsFlowInterrupted").booleanValue();
                                check(findings, "APPI-T032-R015", !passThrough || "P".equals(value),
                                    "Pass-through wallet context uses indicator P.");
                            }
                            }
                            case "APPI-C032-11" -> {
                            check(findings, "APPI-T032-R017", "11".equals(subTableId)
                                    && "001".equals(text(fields, "subTableLength"))
                                    && (!tableContext.path("onlineRefundProgramParticipation").asBoolean(false)
                                    || "Y".equals(value)),
                                "Participating online-refund context uses indicator Y.");
                            check(findings, "APPI-T032-R018", "Y".equals(text(fields, "originalReturnIndicator"))
                                    && "Y".equals(text(fields, "followUpIndicator"))
                                    && fields.path("timeoutReversalIndicatorPresent").asBoolean(false),
                                "Original Y remains present in the supplied follow-up and timeout-reversal logical context.");
                            }
                            case "APPI-C032-12Y" -> check(findings, "APPI-T032-R019",
                                "12".equals(subTableId) && "Y".equals(value)
                                    && tableContext.path("debitCNP").asBoolean(false)
                                    && tableContext.path("ecommerce").asBoolean(false)
                                    && !tableContext.path("debitRouteAvailable").asBoolean(true)
                                    && tableContext.path("creditFallbackAllowed").asBoolean(false),
                                "Explicit Y permits the supplied eligible debit-CNP ecommerce credit-fallback scenario.");
                            case "APPI-C032-12N" -> check(findings, "APPI-T032-R020",
                                "12".equals(subTableId) && "N".equals(value)
                                    && tableContext.path("merchantDebitOnly").asBoolean(false)
                                    && !tableContext.path("debitRouteAvailable").asBoolean(true)
                                    && !fields.path("observedCreditRouting").asBoolean(true),
                                "Explicit N prevents credit fallback in the supplied debit-only/no-route context.");
                            case "APPI-C032-13Y" -> check(findings, "APPI-T032-R021",
                                "13".equals(subTableId) && "Y".equals(value)
                                    && Set.of("Visa", "MasterCard").contains(text(tableContext, "brand"))
                                    && "Cryptocurrency".equals(text(tableContext, "purchaseClassification"))
                                    && "6051".equals(text(tableContext, "mcc")),
                                "Cryptocurrency indicator Y uses the supplied Visa/MasterCard MCC 6051 context.");
                            case "APPI-C032-13H" -> {
                            check(findings, "APPI-T032-R022", "13".equals(subTableId)
                                    && "H".equals(value)
                                    && "MasterCard".equals(text(tableContext, "brand"))
                                    && "6211".equals(text(tableContext, "mcc")),
                                "High-securities indicator H uses the supplied MasterCard MCC 6211 mapping.");
                            add(findings, "APPI-T032-ELIGIBILITY", REVIEW_REQUIRED,
                                "The actual high-securities eligibility classification is supplied as a synthetic precondition.");
                            }
                            case "APPI-C032-14" -> check(findings, "APPI-T032-R023",
                                "14".equals(subTableId) && "001".equals(text(fields, "subTableLength"))
                                    && tableContext.path("merchantOptsToReceivePAR").asBoolean(false)
                                    && fields.path("subTablePresent").asBoolean(false)
                                    && "Y".equals(value),
                                "Merchant PAR opt-in includes subtable 14 with value Y; returned PAR is not asserted.");
                            default -> add(findings, "APPI-T032-SCOPE", NOT_ASSERTABLE,
                                "No bounded predicate is registered for this Table 032 candidate.");
                        }
                        add(findings, "APPI-T032-AGGREGATE", REVIEW_REQUIRED,
                            "Aggregate Table 032 framing, combined length, ordering, and reserved-byte handling are not asserted.");
                        }
                        case "034" -> {
                        String brand = text(fields, "brand");
                        boolean firstAndLast = leftAlignedSpaceFilled(text(fields, "firstNameField"))
                            && leftAlignedSpaceFilled(text(fields, "lastNameField"));
                        if ("Discover".equals(brand)) {
                            check(findings, "APPI-T034-R001", firstAndLast,
                                "Discover NVS fixture contains left-aligned, 35-character first and last name fields.");
                        } else if ("Visa".equals(brand)) {
                            boolean middle = leftAlignedSpaceFilled(text(fields, "middleNameField"));
                            JsonNode order = fields.path("componentOrder");
                            boolean ordered = order.isArray() && order.size() == 3
                                && "firstNameField".equals(order.get(0).asText())
                                && "lastNameField".equals(order.get(1).asText())
                                && "middleNameField".equals(order.get(2).asText());
                            check(findings, "APPI-T034-R002", firstAndLast && middle && ordered,
                                "Visa NVS fixture has three left-aligned 35-character fields ordered first, last, middle.");
                        } else {
                            add(findings, "APPI-T034-LAYOUT", REVIEW_REQUIRED,
                                "Only the explicit Discover and Visa NVS layouts are covered by these candidates.");
                        }
                        add(findings, "APPI-T034-AGGREGATE", REVIEW_REQUIRED,
                            "Name grammar and serialized aggregate length/encoding are not established by this fragment.");
                        }
                        case "033" -> {
                        String count = text(fields, "numberOfProducts");
                        JsonNode product = fields.path("product");
                        boolean oneProduct = product.isObject() && count.matches("[0-9]{2}")
                            && Integer.parseInt(count) == 1;
                        check(findings, "APPI-T033-R001", oneProduct && Integer.parseInt(count) <= 8,
                            "Two-digit product count must match the supplied records and not exceed eight.");

                        String repriceType = text(product, "repriceType");
                        boolean recordShape = text(product, "code").matches("[A-Za-z0-9]{3}")
                            && repriceType.matches("[A-Z]{2}")
                            && text(product, "unitOfMeasure").length() == 1
                            && text(product, "unitOfMeasure").equals(text(product, "companionProductUOM"))
                            && text(product, "decimalIdentifierPrice").length() == 1
                            && text(product, "unitPrice").matches("[ -~]{1,8}")
                            && "\\".equals(text(product, "delimiter"))
                            && "001".equals(text(fields, "productCodeSet"));
                        check(findings, "APPI-T033-R002", recordShape,
                            "Product record widths, matching UOM, NACS code-set selector and backslash delimiter match the supplied fragment.");
                        check(findings, "APPI-T033-R003", Set.of("RP", "RM", "CM", "CP", "BO", "OD", "RT", "FP")
                                .contains(repriceType),
                            "Re-price type must be one of the listed source values.");
                        add(findings, "APPI-T033-PRICE-SEMANTICS", REVIEW_REQUIRED,
                            "Product-code catalog membership and decimal/unit-price semantics are not established.");
                        add(findings, "APPI-T033-AGGREGATE", REVIEW_REQUIRED,
                            "The source's AN15 field attribute versus 133-character maximum and complete repeated serialization remain unresolved.");
                        }
                        case "035" -> {
                        JsonNode xid = fields.path("xidOctets");
                        JsonNode cavv = fields.path("cavvOctets");
                        boolean validOctets = octetArray(xid) && octetArray(cavv);
                        boolean correctOrder = fields.path("componentOrder").isArray()
                            && fields.path("componentOrder").size() == 2
                            && "xidOctets".equals(fields.path("componentOrder").get(0).asText())
                            && "cavvOctets".equals(fields.path("componentOrder").get(1).asText());
                        check(findings, "APPI-T035-R001", validOctets && correctOrder,
                            "Verified by Visa logical payload contains 20 XID octets followed by 20 CAVV octets.");
                        add(findings, "APPI-T035-CRYPTO", REVIEW_REQUIRED,
                            "XID/CAVV cryptographic validity and transaction applicability are not established.");
                        add(findings, "APPI-T035-WIRE", REVIEW_REQUIRED,
                            "Binary-to-ATL105 wire encoding, escaping, and byte-length framing are not asserted by this logical fixture.");
                        }
                        case "036" -> {
                            JsonNode tableContext = fields.path("context");
                        if ("APPI-C036-01".equals(id)) {
                            String securityLevel = text(fields, "securityLevel");
                            String channel = text(fields, "channel");
                            String state = text(fields, "collectionState");
                            check(findings, "APPI-T036-R001", securityLevel.matches("(21|24)[0-7]")
                                    && securityLevel.equals(channel + state),
                                "UCAF security level is a three-character channel/state value with channel 21 or 24.");
                            if (!tableContext.path("collectionSupported").isBoolean()
                                || !tableContext.path("ucafDataPopulated").isBoolean()) {
                            add(findings, "APPI-T036-R002", REVIEW_REQUIRED,
                                "Explicit UCAF collection support and population context are required.");
                            } else {
                            String expectedState = !tableContext.path("collectionSupported").booleanValue() ? "0"
                                : tableContext.path("ucafDataPopulated").booleanValue() ? "2" : "1";
                            check(findings, "APPI-T036-R002", expectedState.equals(state),
                                "Collection state 0/1/2 must match the supplied support/population context.");
                            }
                        } else {
                            String subfieldId = text(fields, "subfieldId");
                            String subfieldLength = text(fields, "subfieldLength");
                            String aav = text(fields, "aavComponent");
                            check(findings, "APPI-T036-R005", "43".equals(subfieldId)
                                    && "28".equals(subfieldLength) && aav.length() == 28
                                    && (2 + 2 + aav.length()) == 32,
                                "Mastercard AAV component uses subfield ID 43, length 28, within the 32-character data component.");
                            add(findings, "APPI-T036-AAV-VALIDITY", REVIEW_REQUIRED,
                                "AAV payload encoding, cryptographic validity, and security-level compatibility are not established.");
                            add(findings, "APPI-T036-UCAF-WIRE", REVIEW_REQUIRED,
                                "The complete 32/35-character UCAF representation is not constructed by this subfield fragment.");
                        }
                        }
                            case "037" -> check(findings, "APPI-T037-R001",
                                leftAlignedSpaceFilled(text(candidate, "data"), 40),
                                "MCX checkout token is a 40-character left-justified, space-filled field.");
                            case "038" -> check(findings, "APPI-T038-R001",
                                leftAlignedSpaceFilled(text(candidate, "data"), 12),
                                "Paydiant Tender ID is a 12-character left-justified, space-filled field.");
                            case "039" -> {
                            JsonNode dateParts = context.path("lastRegisteredProfileChange");
                            String expected = String.format("%02d%02d%02d%02d", dateParts.path("day").asInt(-1),
                                dateParts.path("month").asInt(-1), dateParts.path("century").asInt(-1),
                                dateParts.path("yearWithinCentury").asInt(-1));
                            check(findings, "APPI-T039-R001", text(candidate, "data").matches("[0-9]{8}")
                                    && expected.equals(text(candidate, "data")),
                                "Profile-change date is represented as DDMMCCYY for the supplied date components.");
                            }
                            case "040" -> {
                            if ("APPI-C040-01".equals(id)) {
                                String phase = text(context, "transactionPhase");
                                boolean mayDiffer = context.path("finalSettlementMayDiffer").asBoolean(false);
                                check(findings, "APPI-T040-R001", "1".equals(text(candidate, "data"))
                                        && context.path("emv").asBoolean(false)
                                        && "Initial".equals(phase) && mayDiffer,
                                    "Value 1 is allowed only for an EMV initial transaction whose final settlement may differ.");
                            }
                            if (fields.has("indicatorPresent")) {
                                JsonNode authorizationAmount = fields.path("authorizationMinorUnits");
                                JsonNode finalAmount = fields.path("knownFinalSettlementMinorUnits");
                                if (authorizationAmount.isIntegralNumber() && finalAmount.isIntegralNumber()
                                    && authorizationAmount.intValue() == finalAmount.intValue()) {
                                check(findings, "APPI-T040-R002", !fields.path("indicatorPresent").asBoolean(false),
                                    "Omit the indicator when the supplied known final settlement equals authorization.");
                                } else {
                                add(findings, "APPI-T040-R002", REVIEW_REQUIRED,
                                    "Amount equality is checked only when both supplied minor-unit values are known.");
                                }
                            }
                            }
                            case "041" -> {
                            String subdivision = text(fields, "subdivision");
                            check(findings, "APPI-T041-R001", subdivision.matches("[A-Za-z0-9]{1,3}"),
                                "Country subdivision fragment is 1-3 alphanumeric characters only; ISO validity is not checked.");
                            JsonNode tableContext = fields.path("context");
                            JsonNode outside = tableContext.path("outsideUSCanada");
                            String stateCode = text(tableContext, "terminalNumericState");
                            if (!outside.isBoolean() || stateCode.isEmpty()) {
                                add(findings, "APPI-T041-R002", REVIEW_REQUIRED,
                                    "Explicit country scope and Segment 100 numeric-state context are required.");
                            } else if (outside.booleanValue() && "00".equals(stateCode)) {
                                check(findings, "APPI-T041-R002", fields.path("tablePresent").asBoolean(false),
                                    "Include Table 041 when a non-US/Canada terminal uses numeric state code 00.");
                            }
                            add(findings, "APPI-T041-ISO", REVIEW_REQUIRED,
                                "The synthetic subdivision value is not checked against the ISO 3166-2 catalog.");
                            }
                            case "042" -> {
                            String language = text(context, "cardholderLanguage");
                            String expected = switch (language) {
                                case "English" -> "eng";
                                case "French" -> "fre";
                                default -> "";
                            };
                            if (expected.isEmpty()) {
                                add(findings, "APPI-T042-R001", REVIEW_REQUIRED,
                                    "Only the source-listed English/French example mappings are covered.");
                            } else {
                                check(findings, "APPI-T042-R001", text(candidate, "data").matches("[A-Za-z]{3}")
                                        && expected.equals(text(candidate, "data")),
                                    "Cardholder language uses the supplied three-character English/French example mapping.");
                            }
                            }
                            case "043" -> {
                            JsonNode tableContext = fields.path("context");
                            JsonNode outside = tableContext.path("outsideUSCanada");
                            if (!outside.isBoolean()) {
                                add(findings, "APPI-T043-R001", REVIEW_REQUIRED,
                                    "International versus US/Canada AVS context is required.");
                                add(findings, "APPI-T043-R002", REVIEW_REQUIRED,
                                    "Postal-code formatting and the separate normal AVS address are context dependent.");
                            } else if (outside.booleanValue()) {
                                String postal = text(fields, "postalFragment");
                                check(findings, "APPI-T043-R001", postal.matches("[A-Za-z0-9]{1,3}"),
                                    "Check only the 1-3 character alphanumeric intersection; the broader source limit is unresolved.");
                                boolean addressPresent = fields.path("normalAVSAddressPresent").asBoolean(false)
                                        && !text(fields, "normalAVSAddress").isEmpty();
                                check(findings, "APPI-T043-R002", addressPresent,
                                    "International postal data accompanies a separate normal AVS address item.");
                            }
                            add(findings, "APPI-T043-FORMAT", REVIEW_REQUIRED,
                                "Authorizer-specific postal formatting and the source length/type conflict remain unresolved.");
                            }
                            case "044" -> {
                            String brand = text(fields, "brand");
                            String initiation = text(fields, "initiation");
                            String first044 = text(fields, "firstSubsequent044");
                            String second044 = text(fields, "secondSubsequent044");
                            boolean eligibleBrand = Set.of("Visa", "Amex").contains(brand);
                            check(findings, "APPI-T044-R001", eligibleBrand && "MIT".equals(initiation)
                                    && first044.matches(".{15}") && second044.matches(".{15}"),
                                "The supplied Visa/Amex MIT chain uses 15-character transaction identifiers.");
                            String initialResponse = text(fields, "initialResponseID");
                            String firstResponse = text(fields, "firstSubsequentResponseID");
                            boolean chain = "Visa".equals(brand) && "I".equals(text(fields, "initialCOFIndicator"))
                                    && !fields.path("initial044Present").asBoolean(true)
                                    && initialResponse.matches(".{15}")
                                    && "C".equals(text(fields, "firstSubsequentCOFIndicator"))
                                    && first044.equals(initialResponse) && firstResponse.matches(".{15}")
                                    && second044.equals(firstResponse);
                            check(findings, "APPI-T044-R002", chain,
                                "The illustrated Visa chain omits 044 on initial I and carries each immediately preceding response ID on C follow-ups.");
                            check(findings, "APPI-T044-R003", initialResponse.matches(".{15}")
                                    && firstResponse.matches(".{15}"),
                                "Supplied response identifiers meet the illustrated 15-character retention shape only.");
                            add(findings, "APPI-T044-PROVENANCE", REVIEW_REQUIRED,
                                "Actual authorizer-issued identifier provenance and other COF selection rules are not verified.");
                            }
                            case "045" -> {
                            String digits = text(fields, "firstAuthAmountDigits");
                            boolean numeric = digits.matches("[0-9]{1,12}");
                            check(findings, "APPI-T045-R001", numeric,
                                "First Authorization Amount contains up to 12 numeric digits with two assumed decimals.");
                            JsonNode amountContext = fields.path("context");
                            if ("Discover".equals(text(amountContext, "brand"))
                                    && "MIT".equals(text(amountContext, "initiation"))
                                    && Set.of("Recurring", "Installment").contains(text(amountContext, "series"))) {
                                long originalApproved = fields.path("originalApprovedMinorUnits").asLong(Long.MIN_VALUE);
                                long amount = numeric ? Long.parseLong(digits) : Long.MIN_VALUE;
                                check(findings, "APPI-T045-R002", numeric && amount == originalApproved,
                                    "Discover recurring/installment follow-ups retain the supplied original approved minor-unit amount.");
                            } else {
                                add(findings, "APPI-T045-R002", REVIEW_REQUIRED,
                                    "The retention rule is bounded to explicit Discover MIT recurring/installment context.");
                            }
                            }
                            case "046" -> {
                            JsonNode followupContext = fields.path("context");
                            String required = text(fields, "authorizerRequiredData");
                            if (!"Follow-up".equals(text(followupContext, "transactionPhase")) || required.isEmpty()) {
                                add(findings, "APPI-T046-R001", REVIEW_REQUIRED,
                                    "Explicit follow-up phase and authorizer-required data are needed for this bounded comparison.");
                            } else {
                                String previous = text(fields, "previousTransactionData");
                                check(findings, "APPI-T046-R001", previous.matches("[A-Za-z0-9]{1,3}")
                                        && previous.equals(required),
                                    "Retain the supplied authorizer-required previous data in this 1-3 character intersection.");
                            }
                            add(findings, "APPI-T046-CONTRACT", REVIEW_REQUIRED,
                                "Authorizer applicability and the source AN3 versus maximum-16 conflict remain unresolved.");
                            }
                            case "047" -> {
                            String descriptor = text(candidate, "data");
                            check(findings, "APPI-T047-R001", printable(candidate, "data", 1, 38),
                                "Alternate merchant descriptor is printable ASCII within the 38-character candidate bound.");
                            JsonNode descriptorContext = context;
                            if ("Discover".equals(text(descriptorContext, "brand"))
                                    && descriptorContext.path("thirdPartyInstallment").asBoolean(false)) {
                                String expected = text(fields, "thirdPartyName") + "*"
                                        + text(fields, "underlyingMerchantName");
                                check(findings, "APPI-T047-R002", !text(fields, "thirdPartyName").isEmpty()
                                        && !text(fields, "underlyingMerchantName").isEmpty()
                                        && descriptor.equals(expected) && descriptor.length() <= 38,
                                    "Discover third-party installment descriptor is provider*underlying retailer.");
                            } else {
                                add(findings, "APPI-T047-R002", REVIEW_REQUIRED,
                                    "The provider/retailer composition rule applies only in the supplied Discover installment context.");
                            }
                            add(findings, "APPI-T047-ENROLLMENT", REVIEW_REQUIRED,
                                "Third-party provider enrollment and descriptor escaping/truncation are not verified.");
                            }
                            case "048" -> {
                            String protocol = text(fields, "programProtocol");
                            String version = text(fields.path("context"), "protocolVersion");
                            String expectedProtocol = switch (version) {
                                case "EMV3DS2.1" -> "1";
                                case "EMV3DS2.2" -> "2";
                                case "EMV3DS2.3" -> "3";
                                case "EMV3DS2.4" -> "4";
                                case "EMV3DS2.5" -> "5";
                                case "EMV3DS2.6" -> "6";
                                case "EMV3DS2.7" -> "7";
                                case "EMV3DS2.8" -> "8";
                                case "EMV3DS2.9" -> "9";
                                default -> "";
                            };
                            if (expectedProtocol.isEmpty()) {
                                add(findings, "APPI-T048-R002", REVIEW_REQUIRED,
                                    "Only the explicitly source-listed EMV 3DS protocol-version mappings are evaluated.");
                            } else {
                                check(findings, "APPI-T048-R002", expectedProtocol.equals(protocol),
                                    "Program Protocol matches the supplied EMV 3DS version classification.");
                            }
                            boolean dsIdPresent = fields.path("directoryServerIdPresent").asBoolean(false);
                            String dsId = text(fields, "directoryServerTransactionId");
                            int expectedLength = dsIdPresent ? 37 : 1;
                            boolean header = protocol.matches("[1-9]")
                                    && fields.path("payloadCharacterCount").asInt(-1) == expectedLength
                                    && (!dsIdPresent || (fields.path("programProtocolPresent").asBoolean(false)
                                    && dsId.length() == 36));
                            check(findings, "APPI-T048-R001", header,
                                "Table 048 candidate uses length 001 for protocol-only or 037 for protocol plus 36-character DS ID.");
                            if (dsIdPresent) {
                                String brand = text(fields.path("context"), "brand");
                                String participation = text(fields.path("context"), "emv3dsParticipation");
                                if (brand.isEmpty() || participation.isEmpty()) {
                                    add(findings, "APPI-T048-R003", REVIEW_REQUIRED,
                                    "MasterCard participation context is required when a Directory Server ID is supplied.");
                                } else {
                                    check(findings, "APPI-T048-R003", "MasterCard".equals(brand)
                                        && dsId.length() == 36
                                        && fields.path("programProtocolPresent").asBoolean(false),
                                    "DS ID is bounded to explicit MasterCard participation and paired with Program Protocol.");
                                }
                                add(findings, "APPI-T048-PROVENANCE", REVIEW_REQUIRED,
                                    "Server issuance, uniqueness and actual EMV 3DS participation are not independently verified.");
                            }
                            }
            default -> throw new IllegalStateException("Unhandled implemented candidate");
        }
        add(findings, "APPI-CANDIDATE-READINESS", REVIEW_REQUIRED,
                "Logical checks do not establish framing, full-message validity, SME approval or host acceptance.");
        return new AppendixILogicalDataValidator.Result(findings);
    }

        private AppendixILogicalDataValidator.Result validateTable053(JsonNode candidate) {
        List<AppendixILogicalDataValidator.Finding> findings = new ArrayList<>();
        JsonNode data = candidate.path("syntheticData");
        String identifier = text(data, "identifier");
        String decodedIdentifier = "";
        boolean encodedIdentifier;
        try {
            byte[] decoded = Base64.getDecoder().decode(identifier);
            decodedIdentifier = new String(decoded, StandardCharsets.UTF_8);
            encodedIdentifier = Base64.getEncoder().encodeToString(decoded).equals(identifier);
        } catch (IllegalArgumentException ignored) {
            encodedIdentifier = false;
        }
        check(findings, "APPI-T053-R001", "053".equals(text(data, "tableSelector"))
                && encodedIdentifier && decodedIdentifier.equals(text(data, "logicalCheckoutIdentifier")),
            "Table 053 identifier is canonical base64 for the supplied checkout identifier.");

        JsonNode context = data.path("context");
        boolean companionPresent = context.path("segment123Present").asBoolean(false)
            && context.path("element237Present").asBoolean(false);
        check(findings, "APPI-T053-R002", "MasterCard".equals(text(context, "cardBrand")) && companionPresent,
            "Table 053 is bounded to the supplied MasterCard and Segment 123 cryptogram companion.");
        String cryptogram = text(data, "element237StructuralString");
        boolean validCryptogramShape = cryptogram.length() == 28;
        try {
            byte[] decoded = Base64.getDecoder().decode(cryptogram);
            validCryptogramShape &= Base64.getEncoder().encodeToString(decoded).equals(cryptogram);
        } catch (IllegalArgumentException ignored) {
            validCryptogramShape = false;
        }
        check(findings, "APPI-T053-R003", context.path("element237Present").asBoolean(false)
                && validCryptogramShape,
            "Element 237 is a separate 28-character base64 structural value; cryptographic validity is excluded.");
        add(findings, "APPI-T053-LENGTH-INTERPRETATION", REVIEW_REQUIRED,
            "The source's 028 identifier limit has an unresolved encoded-versus-decoded interpretation.");
        add(findings, "APPI-T053-CRYPTOGRAPHIC-VALIDITY", REVIEW_REQUIRED,
            "Structural base64 does not establish token cryptogram validity.");
        add(findings, "APPI-CANDIDATE-READINESS", REVIEW_REQUIRED,
            "This is a logical fragment, not a complete Segment 111 request or certification result.");
        return new AppendixILogicalDataValidator.Result(findings);
        }

        private AppendixILogicalDataValidator.Result validateTable054(JsonNode candidate) {
        List<AppendixILogicalDataValidator.Finding> findings = new ArrayList<>();
        JsonNode data = candidate.path("syntheticData");
        String value = text(data, "merchantTranId");
        boolean moneris = "Moneris".equals(text(data.path("context"), "processor"));
        JsonNode count = data.path("logicalPayloadLength");
        String mappedValue = text(data.path("downstreamLogicalField"), "value");
        boolean valueShape = value.isEmpty() || value.matches("[\\x20-\\x7E]{1,95}");
        boolean withinProcessorLimit = moneris ? !value.isEmpty() && value.length() <= 15 : value.length() <= 95;
        boolean countMatches = value.isEmpty() || count.isIntegralNumber() && count.intValue() == value.length();
        boolean mappingMatches = !moneris || value.equals(mappedValue);
        check(findings, "APPI-T054-R001", "054".equals(text(data, "tableSelector"))
                && valueShape && withinProcessorLimit && countMatches && mappingMatches,
            "Table 054 is at most 95 printable ASCII bytes; Moneris requires at most 15 and the supplied Client Request ID mapping.");
        add(findings, "APPI-T054-TRANSMISSION", REVIEW_REQUIRED,
            "Actual processor transmission and merchant identifier uniqueness are not established.");
        add(findings, "APPI-CANDIDATE-READINESS", REVIEW_REQUIRED,
            "This is a logical fragment, not a complete Segment 111 request or certification result.");
        return new AppendixILogicalDataValidator.Result(findings);
        }

        private AppendixILogicalDataValidator.Result validateTable055(JsonNode candidate) {
        List<AppendixILogicalDataValidator.Finding> findings = new ArrayList<>();
        JsonNode data = candidate.path("syntheticData");
        String value = text(data, "providerValue");
        String assignment = text(data.path("context"), "syntheticUcomAssignment");
        Map<String, String> assignments = Map.of("Walmart+", "WMPY", "Amazon Pay", "AAPY",
            "Google Pay", "GSPY", "Wex Pay", "WXPY");
        JsonNode count = data.path("logicalPayloadLength");
        boolean valid = "055".equals(text(data, "tableSelector")) && assignments.containsValue(value)
            && value.matches("[A-Za-z0-9]{1,4}") && value.equals(assignments.get(assignment))
            && count.isIntegralNumber() && count.intValue() == value.length();
        check(findings, "APPI-T055-R001", valid,
            "Table 055 matches one of four source-listed provider codes to the supplied UCOM assignment and four-byte maximum.");
        add(findings, "APPI-T055-ASSIGNMENT", REVIEW_REQUIRED,
            "The UCOM provider assignment is a synthetic precondition, not independently verified.");
        add(findings, "APPI-CANDIDATE-READINESS", REVIEW_REQUIRED,
            "This is a logical fragment, not a complete Segment 111 request or certification result.");
        return new AppendixILogicalDataValidator.Result(findings);
        }

    private AppendixILogicalDataValidator.Result validateMerchantLocation(JsonNode candidate, String id) {
        List<AppendixILogicalDataValidator.Finding> findings = new ArrayList<>();
        JsonNode data = candidate.path("syntheticData");
        JsonNode context = data.path("context");
        String tableId = id.substring(6, 9);
        String identityField = "057".equals(tableId) ? "tableSelector" : "logicalTableIdentity";
        String valueField = switch (tableId) {
            case "057" -> "merchantCity";
            case "058" -> "merchantState";
            case "059" -> "merchantCountry";
            default -> throw new IllegalArgumentException("Unsupported merchant-location table: " + tableId);
        };
        String retailerField = switch (tableId) {
            case "057" -> "underlyingRetailerCity";
            case "058" -> "underlyingRetailerState";
            case "059" -> "underlyingRetailerCountry";
            default -> throw new IllegalArgumentException("Unsupported merchant-location table: " + tableId);
        };
        String value = text(data, valueField);
        boolean identityMatches = tableId.equals(text(data, identityField));
        boolean valueShape = switch (tableId) {
            case "057" -> value.length() <= 20 && value.chars().allMatch(c -> c >= 32 && c <= 126);
            case "058" -> !value.isEmpty();
            case "059" -> value.matches("[0-9]{3}");
            default -> false;
        };
        boolean valid = identityMatches && "Discover".equals(text(context, "cardBrand"))
                && "third-party payment provider".equals(text(context, "merchantRole"))
                && valueShape && value.equals(text(context, retailerField));
        check(findings, "APPI-T" + tableId + "-R001", valid,
                "Discover third-party-provider logical merchant location matches the supplied underlying retailer context.");
        add(findings, "APPI-T" + tableId + "-SOURCE-ATTRIBUTES", REVIEW_REQUIRED,
                switch (tableId) {
                    case "057" -> "The source N2/Element 112 length-attribute conflict and padding remain unresolved.";
                    case "058" -> "The fixed selector/length and An3-versus-02 declarations conflict; no wire form is inferred.";
                    case "059" -> "The source n2 framing and external Appendix AC country-code authority remain unverified.";
                    default -> "Source attributes require review.";
                });
        add(findings, "APPI-CANDIDATE-READINESS", REVIEW_REQUIRED,
                "This is a logical fragment, not a complete Segment 111 request or certification result.");
        return new AppendixILogicalDataValidator.Result(findings);
    }

        private AppendixILogicalDataValidator.Result validateTable062(JsonNode candidate) {
        List<AppendixILogicalDataValidator.Finding> findings = new ArrayList<>();
        JsonNode data = candidate.path("syntheticData");
        String id = text(candidate, "candidateId");
        JsonNode context = data.path("context");
        boolean applicable = "Visa".equals(text(context, "cardBrand"))
            && "account verification".equals(text(context, "transactionContext"));
        boolean layoutValid;
        if (id.endsWith("OUTER-001")) {
            JsonNode records = data.path("records");
            int calculatedLength = 0;
            layoutValid = "062".equals(text(data, "tableSelector")) && records.isArray() && !records.isEmpty();
            for (JsonNode record : records) {
            String selector = text(record, "selector");
            String valueField = "01".equals(selector) ? "phone" : "02".equals(selector) ? "email" : "";
            String value = text(record, valueField);
            int maximum = "01".equals(selector) ? 16 : 99;
            boolean validRecord = !valueField.isEmpty() && !value.isEmpty() && value.length() <= maximum
                && record.path("logicalLength").isIntegralNumber()
                && record.path("logicalLength").intValue() == value.length();
            layoutValid &= validRecord;
            calculatedLength += 5 + value.length();
            }
            layoutValid &= calculatedLength <= 150
                && data.path("logicalPayloadLength").isIntegralNumber()
                && data.path("logicalPayloadLength").intValue() == calculatedLength;
        } else {
            String selector = text(data, "selector");
            boolean phone = "01".equals(selector);
            String value = text(data, phone ? "phone" : "email");
            int maximum = phone ? 16 : 99;
            layoutValid = selector.matches("0[12]") && !value.isEmpty() && value.length() <= maximum
                && data.path("logicalLength").isIntegralNumber()
                && data.path("logicalLength").intValue() == value.length();
        }
        check(findings, "APPI-T062-R001", layoutValid,
            "Table 062 defined records are bounded by phone 16/email 99, matching logical counts and the supplied 150-byte aggregate ceiling.");
        check(findings, "APPI-T062-R002", applicable,
            "Table 062 phone/email contact records are bounded to Visa account-verification context.");
        add(findings, "APPI-T062-CONTACT-VALIDITY", REVIEW_REQUIRED,
            "No E.164, email RFC, deliverability, minimum-length or mandatory-record rule is inferred.");
        add(findings, "APPI-CANDIDATE-READINESS", REVIEW_REQUIRED,
            "This is a logical fragment, not a complete Segment 111 request or certification result.");
        return new AppendixILogicalDataValidator.Result(findings);
        }

        private AppendixILogicalDataValidator.Result validateAdditionalCardField(JsonNode candidate, String id) {
        List<AppendixILogicalDataValidator.Finding> findings = new ArrayList<>();
        JsonNode data = candidate.path("syntheticData");
        String tableId = id.equals("APPI-C050-OUTER-001") ? "050" : "051";
        String valueField = "050".equals(tableId) ? "securityCode" : "cardId";
        String value = text(data, valueField);
        String program = text(data.path("context"), "cardProgram");
        JsonNode length = data.path("logicalPayloadLength");
        boolean allowedLength = "SVS".equals(program) ? value.length() == 4
            : value.length() >= 1 && value.length() <= 16;
        boolean valid = tableId.equals(text(data, "tableSelector"))
            && value.matches("[A-Za-z0-9]{1,16}") && allowedLength
            && length.isIntegralNumber() && length.intValue() == value.length();
        check(findings, "APPI-T" + tableId + "-R001", valid,
            "Table " + tableId + " value is 1-16 alphanumeric characters, SVS is exactly four, and supplied logical length matches.");
        add(findings, "APPI-T" + tableId + "-AUTHENTICITY", REVIEW_REQUIRED,
            "Security-code authenticity or Additional Card ID allocation/uniqueness is not established.");
        add(findings, "APPI-CANDIDATE-READINESS", REVIEW_REQUIRED,
            "This is a logical fragment, not a complete Segment 111 request or certification result.");
        return new AppendixILogicalDataValidator.Result(findings);
        }

        private AppendixILogicalDataValidator.Result validateTable049(JsonNode candidate) {
        List<AppendixILogicalDataValidator.Finding> findings = new ArrayList<>();
        JsonNode fields = candidate.path("structuredLogicalFields");
        String id = text(candidate, "candidateId");
        String selector = text(fields, "subTableId");
        String data = text(fields, "baselinePositiveData");
        String declaredLength = text(fields, "declaredDataLength");
        if (declaredLength.isEmpty()) declaredLength = text(fields.path("declaredDataLengthsByData"), data);
        boolean envelope = selector.matches("[0-9]{2}") && selector.equals(text(candidate, "nestedSelector"))
            && declaredLength.matches("[1-9]") && data.matches("[A-Za-z0-9]{1,9}")
            && Integer.parseInt(declaredLength) == data.length();
        check(findings, "APPI-T049-R003", envelope,
            "Nested entry uses a two-digit selector, one-digit length 1-9, and matching alphanumeric data length.");

        JsonNode common = fields.path("preconditions");
        JsonNode byData = fields.path("preconditionsByData").path(data);
        JsonNode context = byData.isObject() ? byData : common;
        String target = mutationTarget(id);
        Boolean valid = switch (id) {
            case "APPI-T049-C001" -> "Y".equals(data) && "Visa".equals(text(context, "network"))
                && "Purchase".equals(text(context, "transaction"))
                && context.path("merchantLocallyApproved").asBoolean(false)
                && "Unavailable".equals(text(context, "connectivityAtCapture"))
                && "Restored".equals(text(context, "connectivityAtSubmission"));
            case "APPI-T049-C002" -> Set.of("M", "C").contains(data)
                && "Subsequent".equals(text(context, "credentialStage"))
                && ("M".equals(data) ? "Merchant" : "Cardholder").equals(text(context, "initiator"))
                && "C".equals(text(context, "table032SubTable08"));
            case "APPI-T049-C003" -> "WEX".equals(text(context, "network"))
                && context.path("receivesAvailableProducts").isBoolean()
                && (context.path("receivesAvailableProducts").booleanValue() ? "4" : "0").equals(data);
            case "APPI-T049-C004" -> ("CB".equals(data) && "Visa".equals(text(context, "network"))
                && "Consumer Bill Payment Service".equals(text(context, "paymentIntent")))
                || ("IR".equals(data) && "MasterCard".equals(text(context, "network"))
                && "Purchase Repayment".equals(text(context, "paymentIntent")));
            case "APPI-T049-C005" -> data.matches("[0-9]{1,6}");
            case "APPI-T049-C006" -> Set.of("AC", "AS", "CC", "CS").contains(data)
                && "MasterCard".equals(text(common, "network"))
                && "mPOS".equals(text(common, "terminalType"))
                && "M".equals(text(common, "table032SubTable01"));
            case "APPI-T049-C007" -> "Y".equals(data) && "MasterCard".equals(text(context, "network"))
                && context.path("merchantWantsAdviceCode").asBoolean(false);
            case "APPI-T049-C009" -> {
            String expectedOperation = switch (data) {
                case "310" -> "Benefit Eligibility & Balance Lookup";
                case "910" -> "Item Qualification";
                case "920" -> "Limited Spend";
                case "200" -> "Limited Spend Refund";
                default -> "";
            };
            yield !expectedOperation.isEmpty() && "InComm OTC".equals(text(context, "program"))
                && expectedOperation.equals(text(context, "operation"));
            }
            case "APPI-T049-C010" -> "Y".equals(data) && "Visa".equals(text(context, "network"))
                && context.path("merchantAcceptsDAFIndicator").asBoolean(false);
            case "APPI-T049-C011" -> "Y".equals(data) && "Visa".equals(text(context, "network"))
                && context.path("merchantAcceptsAgreementID").asBoolean(false);
            case "APPI-T049-C013" -> Set.of("S", "T", "V").contains(data)
                && "Discover".equals(text(common, "network"))
                && "Installment".equals(text(common, "paymentIntent"))
                && ("S".equals(data) ? "Merchant" : "T".equals(data) ? "Third Party" : "Issuer")
                .equals(text(context, "installmentProviderClass"));
            case "APPI-T049-C016" -> "D".equals(data) && "Discover".equals(text(context, "network"))
                && "Delayed Card Sale".equals(text(context, "saleClass"));
            case "APPI-T049-C017" -> "N".equals(data) && "Discover".equals(text(context, "network"))
                && "No Show".equals(text(context, "chargeClass"));
            case "APPI-T049-C018" -> {
            boolean chip = context.path("chipRestrictionsSupported").asBoolean(false);
            boolean host = context.path("hostRestrictionsSupported").asBoolean(false);
            String expected = chip ? host ? "3" : "1" : host ? "2" : "0";
            yield expected.equals(data);
            }
            case "APPI-T049-C020" -> "Y".equals(data) && "Visa".equals(text(context, "network"))
                && context.path("merchantAcceptsAccountInformation").asBoolean(false);
            case "APPI-T049-C021" -> Set.of("R", "I").contains(data)
                && ("R".equals(data) ? "Recurring" : "Installment").equals(text(context, "paymentIntent"));
            case "APPI-T049-C022" -> "Y".equals(data) && "MasterCard".equals(text(context, "network"))
                && context.path("merchantWantsTransactionLinkIdentifier").asBoolean(false);
            case "APPI-T049-C023" -> "Y".equals(data) && "Visa".equals(text(context, "network"))
                && context.path("merchantAcceptsAuthenticationDataQuality").asBoolean(false);
            case "APPI-T049-C024" -> "Y".equals(data) && "Visa".equals(text(context, "network"))
                && context.path("merchantAcceptsTokenUpdateFirstUse").asBoolean(false);
            case "APPI-T049-C025" -> "Y".equals(data) && context.path("merchantAcceptsMerchantTranID").asBoolean(false);
            case "APPI-T049-C026" -> "Y".equals(data) && "Maestro".equals(text(context, "network"))
                && context.path("merchantWantsTransactionLinkAction").asBoolean(false);
            case "APPI-T049-C027" -> "Y".equals(data) && "Visa".equals(text(context, "network"))
                && context.path("merchantAcceptsAppliedSpecialService").asBoolean(false);
            case "APPI-T049-C028" -> "Y".equals(data) && "Voyager".equals(text(context, "network"))
                && context.path("merchantAcceptsVoyagerRestrictionCodes").asBoolean(false);
            case "APPI-T049-C030" -> "Y".equals(data) && "Visa".equals(text(context, "network"))
                && context.path("merchantWantsVisaCategoryCode").asBoolean(false);
            default -> null;
        };
        if (valid == null) {
            add(findings, target, REVIEW_REQUIRED, "No bounded Table 049 predicate is implemented for this candidate.");
        } else {
            check(findings, target, valid, "Candidate predicate is evaluated only under its explicit synthetic preconditions.");
        }

        if ("APPI-T049-C004".equals(id) && "IR".equals(data)) {
            check(findings, "APPI-T049-R012", "I".equals(text(context, "table013Data")),
                "The IR candidate includes the supplied logical Table 013 installment indicator.");
            check(findings, "APPI-T049-R013", text(context, "table047Data").equals(
                text(context, "providerName") + "*" + text(context, "merchantName")),
                "The IR candidate's supplied descriptor follows provider*merchant ordering.");
            add(findings, "APPI-T049-IR-DEPENDENCY", REVIEW_REQUIRED,
                "Cross-table wire lengths and merchant enrollment are not verified.");
        }
        if ("APPI-T049-C005".equals(id)) {
            JsonNode basket = context.path("basket");
            if (!basket.isArray() || basket.isEmpty()) {
            add(findings, "APPI-T049-R015", REVIEW_REQUIRED, "Explicit synthetic product-price basket is required.");
            } else {
            int highest = Integer.MIN_VALUE;
            String highestCode = "";
            boolean tied = false;
            for (JsonNode item : basket) {
                int price = item.path("priceMinorUnits").asInt(Integer.MIN_VALUE);
                if (price > highest) {
                highest = price;
                highestCode = text(item, "syntheticProductCode");
                tied = false;
                } else if (price == highest) {
                tied = true;
                }
            }
            if (tied) add(findings, "APPI-T049-R015", REVIEW_REQUIRED, "Equal-price product tie behavior is not defined.");
            else check(findings, "APPI-T049-R015", highestCode.equals(data),
                "The supplied TeleCheck candidate selects the unique highest-priced synthetic item.");
            }
        }
        if (Set.of("APPI-T049-C018", "APPI-T049-C021").contains(id)) {
            add(findings, "APPI-T049-APPLICABILITY", REVIEW_REQUIRED,
                "The code mapping is checked, but network/acquirer applicability remains SME-gated.");
        }
        add(findings, "APPI-T049-EXTERNAL", REVIEW_REQUIRED,
            "External network, product catalogue, terminal certification, and host behavior are not asserted.");
        add(findings, "APPI-CANDIDATE-READINESS", REVIEW_REQUIRED,
            "Table 049 is a nested logical fragment; ordering, aggregate serialization, and host acceptance are not checked.");
        return new AppendixILogicalDataValidator.Result(findings);
        }

    public static String mutationTarget(String id) {
        return switch (id) {
            case "APPI-C010-01" -> "APPI-T010-LAYOUT";
            case "APPI-C010-02" -> "APPI-T010-ZERO-FILL";
            case "APPI-C011-01", "APPI-C011-02", "APPI-C011-03" -> "APPI-T011-MAPPING";
            case "APPI-C012-01" -> "APPI-T012-MAPPING";
            case "APPI-C013-01" -> "APPI-T013-RECURRING";
            case "APPI-C013-02" -> "APPI-T013-MAPPING";
            case "APPI-C014-01" -> "APPI-T014-ECHO";
            case "APPI-C015-01" -> "APPI-T015-RELATION";
            case "APPI-C017-01" -> "APPI-T017-VALUE";
            case "APPI-C018-01" -> "APPI-T018-IDENTITY";
            case "APPI-C018-02" -> "APPI-T018-PRESENCE";
            case "APPI-C019-01" -> "APPI-T019-R001";
            case "APPI-C019-02" -> "APPI-T019-R002";
            case "APPI-C020-01" -> "APPI-T020-R001";
            case "APPI-C021-01" -> "APPI-T021-R001";
            case "APPI-C021-02" -> "APPI-T021-R002";
            case "APPI-C022-01" -> "APPI-T022-CLASSIFICATION";
            case "APPI-C022-02" -> "APPI-T022-WIDTH";
            case "APPI-C024-01", "APPI-C024-02" -> "APPI-T024-R001";
            case "APPI-C025-01" -> "APPI-T025-R001";
            case "APPI-C026-01" -> "APPI-T026-R001";
            case "APPI-C027-01" -> "APPI-T027-R001";
            case "APPI-C028-01" -> "APPI-T028-R002";
            case "APPI-C029-01", "APPI-C029-02" -> "APPI-T029-R001";
            case "APPI-C030-01" -> "APPI-T030-R002";
            case "APPI-C030-02" -> "APPI-T030-R004";
            case "APPI-C031-01" -> "APPI-T031-R002";
            case "APPI-C032-01" -> "APPI-T032-R002";
            case "APPI-C032-02" -> "APPI-T032-R003";
            case "APPI-C032-04" -> "APPI-T032-R004";
            case "APPI-C032-05" -> "APPI-T032-R005";
            case "APPI-C032-06" -> "APPI-T032-R006";
            case "APPI-C032-07" -> "APPI-T032-R007";
            case "APPI-C032-08" -> "APPI-T032-R008";
            case "APPI-C032-09" -> "APPI-T032-R012";
            case "APPI-C032-10" -> "APPI-T032-R015";
            case "APPI-C032-11" -> "APPI-T032-R018";
            case "APPI-C032-12Y" -> "APPI-T032-R019";
            case "APPI-C032-12N" -> "APPI-T032-R020";
            case "APPI-C032-13Y" -> "APPI-T032-R021";
            case "APPI-C032-13H" -> "APPI-T032-R022";
            case "APPI-C032-14" -> "APPI-T032-R023";
            case "APPI-C033-01" -> "APPI-T033-R002";
            case "APPI-C034-01" -> "APPI-T034-R001";
            case "APPI-C034-02" -> "APPI-T034-R002";
            case "APPI-C035-01" -> "APPI-T035-R001";
            case "APPI-C036-01" -> "APPI-T036-R002";
            case "APPI-C036-02" -> "APPI-T036-R005";
            case "APPI-C037-01" -> "APPI-T037-R001";
            case "APPI-C038-01" -> "APPI-T038-R001";
            case "APPI-C039-01" -> "APPI-T039-R001";
            case "APPI-C040-01" -> "APPI-T040-R001";
            case "APPI-C040-02" -> "APPI-T040-R002";
            case "APPI-C041-01" -> "APPI-T041-R002";
            case "APPI-C042-01" -> "APPI-T042-R001";
            case "APPI-C043-01" -> "APPI-T043-R002";
            case "APPI-C044-01" -> "APPI-T044-R002";
            case "APPI-C045-01" -> "APPI-T045-R002";
            case "APPI-C046-01" -> "APPI-T046-R001";
            case "APPI-C047-01" -> "APPI-T047-R002";
            case "APPI-C048-01" -> "APPI-T048-R002";
            case "APPI-C048-02" -> "APPI-T048-R003";
            case "APPI-T049-C001" -> "APPI-T049-R006";
            case "APPI-T049-C002" -> "APPI-T049-R007";
            case "APPI-T049-C003" -> "APPI-T049-R009";
            case "APPI-T049-C004" -> "APPI-T049-R010";
            case "APPI-T049-C005" -> "APPI-T049-R014";
            case "APPI-T049-C006" -> "APPI-T049-R016";
            case "APPI-T049-C007" -> "APPI-T049-R020";
            case "APPI-T049-C009" -> "APPI-T049-R023";
            case "APPI-T049-C010" -> "APPI-T049-R024";
            case "APPI-T049-C011" -> "APPI-T049-R026";
            case "APPI-T049-C013" -> "APPI-T049-R028";
            case "APPI-T049-C016" -> "APPI-T049-R030";
            case "APPI-T049-C017" -> "APPI-T049-R031";
            case "APPI-T049-C018" -> "APPI-T049-R032";
            case "APPI-T049-C020" -> "APPI-T049-R039";
            case "APPI-T049-C021" -> "APPI-T049-R042";
            case "APPI-T049-C022" -> "APPI-T049-R044";
            case "APPI-T049-C023" -> "APPI-T049-R046";
            case "APPI-T049-C024" -> "APPI-T049-R048";
            case "APPI-T049-C025" -> "APPI-T049-R050";
            case "APPI-T049-C026" -> "APPI-T049-R052";
            case "APPI-T049-C027" -> "APPI-T049-R054";
            case "APPI-T049-C028" -> "APPI-T049-R056";
            case "APPI-T049-C030" -> "APPI-T049-R058";
            case "APPI-C050-OUTER-001" -> "APPI-T050-R001";
            case "APPI-C051-OUTER-001" -> "APPI-T051-R001";
            case "APPI-C053-OUTER-001" -> "APPI-T053-R001";
            case "APPI-C054-OUTER-001" -> "APPI-T054-R001";
            case "APPI-C055-OUTER-001" -> "APPI-T055-R001";
            case "APPI-C057-OUTER-001" -> "APPI-T057-R001";
            case "APPI-C058-OUTER-001" -> "APPI-T058-R001";
            case "APPI-C059-OUTER-001" -> "APPI-T059-R001";
            case "APPI-C062-OUTER-001", "APPI-C062-N01-001", "APPI-C062-N02-001" -> "APPI-T062-R001";
            default -> throw new IllegalArgumentException("Unsupported candidate: " + id);
        };
    }

    private static String text(JsonNode node, String field) {
        return node.path(field).isTextual() ? node.path(field).textValue() : "";
    }

    private static boolean printable(JsonNode node, String field, int min, int max) {
        String value = text(node, field);
        return value.length() >= min && value.length() <= max
                && value.chars().allMatch(c -> c >= 32 && c <= 126);
    }

    private static boolean leftAlignedSpaceFilled(String value) {
        return leftAlignedSpaceFilled(value, 35);
    }

    private static boolean leftAlignedSpaceFilled(String value, int width) {
        if (value.length() != width || value.isEmpty() || value.charAt(0) == ' ') return false;
        String unpadded = value.stripTrailing();
        return !unpadded.isEmpty() && value.equals(unpadded + " ".repeat(width - unpadded.length()));
    }

    private static boolean matches(JsonNode node, String field, String pattern) {
        return node.path(field).isTextual() && node.path(field).textValue().matches(pattern);
    }

    private static boolean octetArray(JsonNode node) {
        if (!node.isArray() || node.size() != 20) return false;
        for (JsonNode octet : node) {
            if (!octet.isIntegralNumber() || octet.intValue() < 0 || octet.intValue() > 255) return false;
        }
        return true;
    }

    private static boolean booleans(JsonNode node, String... fields) {
        for (String field : fields) {
            if (!node.path(field).isBoolean()) return false;
        }
        return true;
    }

    private static void check(List<AppendixILogicalDataValidator.Finding> findings,
                              String rule, boolean valid, String scope) {
        add(findings, rule, valid ? PASS : FAIL, scope);
    }

    private static void add(List<AppendixILogicalDataValidator.Finding> findings,
                            String rule, AppendixILogicalDataValidator.Status status, String reason) {
        findings.add(new AppendixILogicalDataValidator.Finding(rule, status, reason));
    }
}
