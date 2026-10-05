package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Derives source-anchored draft BR candidates for Appendix G special/nonfinancial flows. */
public final class GenerateSpecialTransactionTypeBrBaseline {
    private record Source(String section, String element, String rule) { }
    private record Requirement(String id, String code, String title, String behavior,
                               String evidenceLevel, String blocker, List<Source> sources) { }

    private static final List<Requirement> REQUIREMENTS = buildRequirements();

    private GenerateSpecialTransactionTypeBrBaseline() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path outputRoot = Atl105Paths.testJson();
        ObjectNode output = mapper.createObjectNode();
        ObjectNode manifest = output.putObject("manifest");
        manifest.put("packageId", "ATL105-SPECIAL-TRANSACTION-TYPE-BR-DRAFT-001");
        manifest.put("specification", "ATL105");
        manifest.put("specificationVersion", "2026-3");
        manifest.put("authority", "INDEPENDENT_TEST_SOLUTION");
        manifest.put("status", "DRAFT_REVIEW_REQUIRED");
        manifest.put("aiArtifactsIncluded", false);
        manifest.put("scope", "Special/nonfinancial Appendix G transaction meanings and documented specialized operations; excludes the separate 13-code standard financial BR package.");
        manifest.put("trainingMethod", "Classify the message family first; derive one atomic BR per source-described behavior; attach all source anchors; keep unsupported prompt, response, configuration, and external-spec details REVIEW_REQUIRED.");
        ArrayNode businessRequirements = output.putArray("businessRequirements");
        ObjectNode countsByCode = mapper.createObjectNode();
        for (Requirement requirement : REQUIREMENTS) {
            ObjectNode br = businessRequirements.addObject();
            br.put("id", requirement.id());
            br.put("transactionTypeCode", requirement.code());
            br.put("title", requirement.title());
            br.put("requirement", requirement.behavior());
            br.put("evidenceLevel", requirement.evidenceLevel());
            br.put("status", "DRAFT_REVIEW_REQUIRED");
            br.put("executionReady", false);
            if (!requirement.blocker().isBlank()) br.put("blocker", requirement.blocker());
            ArrayNode anchors = br.putArray("sourceAnchors");
            for (Source source : requirement.sources()) {
                ObjectNode anchor = anchors.addObject();
                anchor.put("specification", "ATL105");
                anchor.put("version", "2026-3");
                anchor.put("section", source.section());
                anchor.put("segment", "100");
                anchor.put("element", source.element());
                anchor.put("rule", source.rule());
            }
            countsByCode.put(requirement.code(), countsByCode.path(requirement.code()).asInt() + 1);
        }
        output.set("businessRequirementCountsByCode", countsByCode);
        output.putObject("summary")
                .put("specialTransactionCodes", 10)
                .put("draftBusinessRequirements", REQUIREMENTS.size())
                .put("testScenarios", 0)
                .put("testCases", 0)
                .put("testDataJsonFixtures", 0)
                .put("automaticallyApproved", false)
                .put("nextGate", "INDEPENDENT_TS_TC_TD_DERIVATION");

        Files.createDirectories(outputRoot);
        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("special-transaction-type-br-baseline.json").toFile(), output);
        Files.writeString(outputRoot.resolve("special-transaction-type-br-baseline.md"), markdown(countsByCode));
        System.out.printf("codes=%d draftBRs=%d scenarios=0 cases=0 data=0%n", countsByCode.size(), REQUIREMENTS.size());
    }

    private static List<Requirement> buildRequirements() {
        List<Requirement> result = new ArrayList<>();
        add(result, "9", "Special transactions shall be routed to specialized nonfinancial flows, not treated as standard financial purchases.", "Appendix G", "78", "transaction-type-9-special-flow", "DIRECT_DESCRIPTION", "Special-flow prompt and message-family applicability must be mapped before deriving executable cases.");
        add(result, "9", "Special transaction Prompt Codes shall use the three-character special-data code without appending a financial card type.", "Appendix E", "78", "special-prompt-code-three-character-format", "DIRECT_FORMAT_RULE", "The permitted code-by-code prompt behavior still needs operation-specific TS/TC/TD derivation.");
        String[][] specialPrompts = {
                {"900", "Communications Status transaction", "The 900 special prompt requests communications-status behavior."},
                {"901", "Custom receipt text", "The 901 special prompt retrieves custom text to print on receipts."},
                {"902", "Dynamic card table", "The 902 special prompt retrieves dynamic card-table data."},
                {"903", "Site configuration data", "The 903 special prompt submits site-configuration data."},
                {"904", "Host discount data", "The 904 special prompt retrieves host-discount data by product code for AFP transactions."},
                {"905", "Fuel volume data", "The 905 special prompt submits fuel-volume data."},
                {"981", "Nonproprietary electronic mail", "The 981 special prompt retrieves nonproprietary electronic mail."},
                {"990", "Totals", "The 990 special prompt returns totals as a single-block fixed-length response with no print data."},
                {"995", "Electronic mail submission", "The 995 special prompt submits electronic mail."},
                {"996", "Proprietary electronic mail", "The 996 special prompt retrieves proprietary electronic mail."},
                {"997", "Electronic mail status reset", "The 997 special prompt resets electronic-mail status."}
        };
        for (String[] prompt : specialPrompts) {
            result.add(new Requirement("BR-TX9-PROMPT-" + prompt[0], "9", prompt[1], prompt[2],
                    "DIRECT_DESCRIPTION", "", List.of(new Source("Appendix E", "78", "special-prompt-" + prompt[0]))));
        }
        add(result, "D", "Transaction type D performs RBC Loyalty Lookup to identify the loyalty number associated with an RBC card.", "Appendix G", "78", "transaction-type-D-rbc-loyalty-lookup", "APPENDIX_G_ONLY", "ATL105 gives the operation name and purpose only; message request/response details need an authoritative source.");
        add(result, "E", "Balance Inquiry supports balance inquiry for credit, EBT cash-benefit, and stored-value contexts.", "Appendix G", "78", "transaction-type-E-balance-inquiry", "DIRECT_DESCRIPTION", "Per-family response and payload assertions must be derived from the corresponding sections.");
        add(result, "E", "Stored-value Balance Inquiry reports the remaining balance and does not change stored-value quantity or amount totals.", "10.7.3.11", "78", "transaction-type-E-svc-balance-inquiry-no-total-change", "DIRECT_BEHAVIOR", "Test both returned balance and unchanged totals using an independent synthetic fixture.");
        add(result, "K", "Activation activates the stored-value account for a specified dollar amount; after successful activation, charges may be made against the account.", "10.7.3.6", "78", "transaction-type-K-svc-activation", "DIRECT_BEHAVIOR", "Card-product applicability and response details require separate rules where not stated here.");
        add(result, "L", "For non-First-Data Standard Gift Card deactivation, the requested amount equals the account balance; after success the account may be closed according to client configuration.", "10.7.3.7", "78", "transaction-type-L-standard-gift-deactivation", "DIRECT_CONDITIONAL_BEHAVIOR", "Client account-close configuration is an external input and must not be inferred.");
        add(result, "L", "For First Data Premium Gift Card cash-out, the amount is greater than zero and no more than the balance; the remaining balance becomes zero and the used amount is returned as Approved Amount.", "10.7.3.7", "78", "transaction-type-L-first-data-cashout", "DIRECT_CONDITIONAL_BEHAVIOR", "Premium Gift Card product scope and fixture values require isolated branch coverage.");
        add(result, "M", "Balance Merge combines at most two stored-value card balances onto one of those cards and reduces the remaining card balance to zero.", "10.7.3.9", "78", "transaction-type-M-balance-merge-two-cards", "DIRECT_BEHAVIOR", "Use two synthetic accounts and assert both destination and zeroed source balances.");
        add(result, "M", "If more than two stored-value cards must be merged, repeat the merge until one card retains the combined balance.", "10.7.3.9", "78", "transaction-type-M-balance-merge-repeat", "DIRECT_LIFECYCLE_BEHAVIOR", "Multi-message lifecycle and intermediate balances need linked TS/TC/TD evidence.");
        add(result, "N", "Replace Card transfers the balance of a lost, stolen, or damaged card to a new card with a new account number and reduces the old account balance to zero.", "10.7.3.10", "78", "transaction-type-N-replace-card", "DIRECT_BEHAVIOR", "Receipt-based recovery of old account number is noted by source but operational details require a separate scenario.");
        add(result, "Q", "Recharge/Issue Card adds the entered amount to the current stored-value balance without exceeding the card's original value.", "10.7.3.8", "78", "transaction-type-Q-recharge-original-value-cap", "DIRECT_BOUNDARY_BEHAVIOR", "Derive exact boundary and reject-over-cap expected results in source-anchored TC/TD cases.");
        add(result, "T", "Transaction type T identifies TransArmor token registration.", "Appendix G", "78", "transaction-type-T-transarmor-token-registration", "APPENDIX_G_ONLY", "Detailed request, response, token lifecycle, and security assertions are deferred to the external TransArmor processing specification referenced by ATL105 10.12.");
        addLoyaltyRequirements(result);
        return result;
    }

    private static void addLoyaltyRequirements(List<Requirement> result) {
        String[][] functions = {
                {"ADD-ACCOUNT", "Add account", "Adds a new loyalty account to the loyalty host; the device captures a new card and customer street number and telephone number."},
                {"COUPON-REDEEM", "Coupon redemption", "Redeems coupons for payment using loyalty account/card and Coupon ID and Coupon Amount; when the card is unavailable, street number and telephone number substitute for account number."},
                {"EXPIRATION-UPDATE", "Expiration date update", "Updates loyalty-account expiration; when the card is unavailable, street number and telephone number substitute for account number, and the host extends the account by a predefined interval."},
                {"ACCOUNT-INQUIRY", "Account inquiry", "Inquires about loyalty-account status and returns merchant-maintained Loyalty Print Data; street number and telephone number may substitute when the card is unavailable."},
                {"POINTS-REDEEM", "Points redemption", "Redeems a specified number of loyalty points for payment; street number and telephone number may substitute when the card is unavailable."},
                {"COUPON-REVERSAL", "Reversal of coupon redeem", "Reverses a previously approved coupon redemption using Coupon ID, Coupon Amount, and the prior approval number; street number and telephone number may substitute when the card is unavailable."},
                {"POINTS-REVERSAL", "Reversal of points redeemed", "Reverses previously approved points redemption using point amount and prior approval number; street number and telephone number may substitute when the card is unavailable."},
                {"SALE-UPDATE", "Sale update", "Updates the loyalty host with noncredit-card/cash sale amount for a loyalty account; street number and telephone number may substitute when the card is unavailable."},
                {"ACCOUNT-UPDATE", "Account update", "Updates loyalty-account street and telephone numbers; both new values are required by the described operation."},
                {"TOTALS-REPORT", "Totals Report, Loyalty", "Requests a summary of loyalty transactions subtotaled by card number; the host returns the report for printing."}
        };
        for (String[] function : functions) {
            result.add(new Requirement("BR-TXV-" + function[0], "V", function[1], function[2],
                    "DIRECT_BEHAVIOR", "", List.of(new Source("10.9.3", "78", "transaction-type-V-loyalty-" + function[0].toLowerCase()))));
        }
    }

    private static void add(List<Requirement> target, String code, String behavior, String section,
                            String element, String rule, String evidence, String blocker) {
                    target.add(new Requirement("BR-TX-" + code + "-" + safe(rule), code, behavior, behavior,
                evidence, blocker, List.of(new Source(section, element, rule))));
    }

                    private static String markdown(ObjectNode countsByCode) {
        StringBuilder output = new StringBuilder("# Special Transaction-Type BR Draft Baseline\n\n")
                .append("This is independent Test Solution knowledge derived from ATL105 Appendix G and specialized message-flow sections. It is not certified coverage. Every record remains DRAFT_REVIEW_REQUIRED until BR->TS->TC->Test Data JSON chains and validation evidence exist.\n\n")
                .append("AI artifacts are excluded. Standard financial codes 0, 3, 4, 5, 6, 7, 8, A, B, C, S, U, Z remain in the separate Segment 100 code-flow package.\n\n")
                .append("| Special code | Draft BR candidates | Evidence note |\n|---|---:|---|\n");
        for (var fields = countsByCode.fields(); fields.hasNext();) {
            var entry = fields.next();
            output.append('|').append(entry.getKey()).append('|').append(entry.getValue().asInt()).append('|')
                    .append(evidenceNote(entry.getKey())).append("|\n");
        }
        output.append("\n## Training Technique\n\n")
                .append("1. Classify the code/message family before constructing any payload; special Prompt Codes must not be mistaken for financial Prompt Codes.\n")
                .append("2. Split operation lists into one BR per independently testable behavior, preserving a shared source anchor where appropriate.\n")
                .append("3. Record exact source sections and mark Appendix-G-only or external-spec claims as review-required; do not infer request/response behavior from code names.\n")
                .append("4. For every BR derive positive, negative, boundary, conditional, and lifecycle tests as applicable, then create independent synthetic Test Data JSON.\n")
                .append("5. Validate the full BR->TS->TC->TD graph and keep AI fixtures outside the Test Solution oracle.\n");
        return output.toString();
    }

    private static String evidenceNote(String code) {
        return switch (code) {
            case "9" -> "Appendix G plus Appendix E special-prompt operation table";
            case "D" -> "Appendix G description only; request/response details unresolved";
            case "E" -> "Appendix G, 10.5.1.6, and stored-value 10.7.3.11";
            case "K", "L", "M", "N", "Q" -> "Stored-value operation detail in 10.7.3";
            case "T" -> "Appendix G only; detailed TransArmor document is external";
            case "V" -> "Loyalty operation details in 10.9.3";
            default -> "Source-backed draft";
        };
    }

    private static String safe(String value) {
        return value.toUpperCase(java.util.Locale.ROOT).replaceAll("[^A-Z0-9]+", "-");
    }
}