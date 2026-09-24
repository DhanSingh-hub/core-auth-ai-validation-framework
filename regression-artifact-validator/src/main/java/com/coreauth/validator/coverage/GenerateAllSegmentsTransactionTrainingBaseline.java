package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Generates the independent ATL105 23-code by segment training/applicability baseline. */
public final class GenerateAllSegmentsTransactionTrainingBaseline {
    private static final List<String[]> SEGMENTS = List.of(
            new String[]{"100", "Standard Message Data Segment"},
            new String[]{"101", "Fleet Data Segment"},
            new String[]{"102", "Product Code Data Segment"},
            new String[]{"103", "EBT Data Segment"},
            new String[]{"104", "Purchase Card Data Segment"},
            new String[]{"105", "Totals Data Segment"},
            new String[]{"108", "Loyalty Card Data Segment"},
            new String[]{"109", "Electronic Mail Data Segment"},
            new String[]{"110", "Check Data Segment"},
            new String[]{"111", "Variable Information Data Segment"},
            new String[]{"112", "Additional Information Data Segment"},
            new String[]{"113", "ECA/TeleCheck Data Segment"},
            new String[]{"114", "SKU Data Segment"},
            new String[]{"115", "Print Data Segment"},
            new String[]{"118", "Proprietary Data Load Segment"},
            new String[]{"119", "Totals with Proprietary Data Load Data Segment"},
            new String[]{"120", "Print Data 2 Segment"},
            new String[]{"123", "NFC Payment Tokenization Data Segment"},
            new String[]{"130", "EMV Request Data Segment"},
            new String[]{"131", "EMV Response Data Segment"},
            new String[]{"132", "CA Public Key File Segment"},
            new String[]{"134", "Transaction Attributes Data Segment"},
            new String[]{"135", "Moneris Data Request Segment"},
            new String[]{"136", "Moneris Data Response Segment"},
            new String[]{"139", "Moneris Day End Batch Balance Request Segment"},
            new String[]{"140", "Moneris Day End Batch Balance Response Segment"},
            new String[]{"141", "Moneris Day End Batch Close Request Segment"},
            new String[]{"142", "Moneris Day End Batch Close Response Segment"},
            new String[]{"143", "Tax by Product Data Segment"},
            new String[]{"145", "Enhanced Fleet Request Segment"},
            new String[]{"146", "Enhanced Fleet Response Segment"},
            new String[]{"148", "WEX Available Product Fleet Information Data Segment"},
            new String[]{"149", "Fuel Price Update Segment"},
            new String[]{"150", "Fuel Price Update Response Segment"},
            new String[]{"151", "InComm OTC Market Basket Data Input Segment"},
            new String[]{"152", "InComm OTC Market Basket Data Segment"},
            new String[]{"153", "Network Token Data Request Segment"},
            new String[]{"155", "Real Time Account Updater Response Data Segment"},
            new String[]{"156", "EV Charging Data Segment"},
            new String[]{"157", "Adjusted Product Code Data Segment"},
            new String[]{"DL1", "Merchant Data Segment"},
            new String[]{"DL2", "Dial String Data Segment"},
            new String[]{"DL3", "Date and Time Data Segment"},
            new String[]{"DL4", "Software Dial Load Data Segment"},
            new String[]{"DL5", "Software IP Load Data Segment"},
            new String[]{"DL6", "Store and Forward Data Segment"},
            new String[]{"DL7", "Supplemental Terminal Data Segment"},
            new String[]{"DL8", "EMV Terminal Floor Limits Data Segment"});

    private static final String[][] TRANSACTION_TYPES = {
            {"0", "POS Purchase/Capture or Preauthorized Completion", "STANDARD_FINANCIAL"},
            {"3", "POS Authorization Only", "STANDARD_FINANCIAL"},
            {"4", "Customer-activated Purchase/Capture", "STANDARD_FINANCIAL"},
            {"5", "Customer-activated Authorization Only", "STANDARD_FINANCIAL"},
            {"6", "Mail/Phone Purchase", "STANDARD_FINANCIAL"},
            {"7", "Merchandise Return/Refund", "STANDARD_FINANCIAL"},
            {"8", "Purchase Reversal/Void", "STANDARD_FINANCIAL"},
            {"A", "Account Verification", "STANDARD_FINANCIAL"},
            {"B", "Mail/Phone Authorization Only", "STANDARD_FINANCIAL"},
            {"C", "Mail/Phone Reversal/Void", "STANDARD_FINANCIAL"},
            {"S", "Cancellation", "STANDARD_FINANCIAL"},
            {"U", "Void of Merchandise Return", "STANDARD_FINANCIAL"},
            {"Z", "Time-out Reversal", "STANDARD_FINANCIAL"},
            {"9", "Special Transactions", "SPECIAL_NONFINANCIAL"},
            {"D", "RBC Loyalty Lookup", "SPECIAL_NONFINANCIAL"},
            {"E", "Balance Inquiry", "SPECIAL_NONFINANCIAL"},
            {"K", "Activate Stored Value Card", "SPECIAL_NONFINANCIAL"},
            {"L", "Deactivate Stored Value Card", "SPECIAL_NONFINANCIAL"},
            {"M", "Balance Merge", "SPECIAL_NONFINANCIAL"},
            {"N", "Replace Card", "SPECIAL_NONFINANCIAL"},
            {"Q", "Recharge Card", "SPECIAL_NONFINANCIAL"},
            {"T", "Token Registration", "SPECIAL_NONFINANCIAL"},
            {"V", "Loyalty Advice Function", "SPECIAL_NONFINANCIAL"}
    };

    private GenerateAllSegmentsTransactionTrainingBaseline() {
    }

    public static void main(String[] args) throws IOException {
        Path output = Path.of("specifications", "ATL105", "test-output", "test-json",
                "all-segments-all-23-transaction-type-training-baseline.json");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.putObject("manifest")
                .put("packageId", "ATL105-ALL-SEGMENTS-ALL-23-TRANSACTION-TYPES")
                .put("specification", "ATL105")
                .put("specificationVersion", "2026-3")
                .put("status", "TEST_SOLUTION_TRAINING_APPLICABILITY_BASELINE");
        root.putObject("trainingPolicy")
                .put("transactionTypeSource", "Appendix G")
                .put("segmentInventorySource", "ATL105 message format Sections 11 and 12")
                .put("standardFinancialCodesRequireSegment100", true)
                .put("transactionTypeAloneDoesNotDetermineCompanionSegments", true)
                .put("unsupportedCombinationsRemainReviewRequired", true)
                .put("aiOutputIsNotTheTrainingOracle", true);
        ArrayNode entries = root.putArray("segmentTransactionTypes");
        for (String[] segment : SEGMENTS) {
            for (String[] type : TRANSACTION_TYPES) {
                ObjectNode entry = entries.addObject();
                entry.put("segment", segment[0]);
                entry.put("segmentName", segment[1]);
                entry.put("transactionType", type[0]);
                entry.put("transactionName", type[1]);
                entry.put("transactionClass", type[2]);
                entry.put("sourceAnchor", "ATL105|2026-3|Appendix G|100|78|transaction-type-" + type[0]);
                entry.put("trainingStatus", "100".equals(segment[0]) ? "BASELINE_TRAINED" : "CONTEXT_REVIEW_REQUIRED");
                entry.put("applicabilityStatus", "100".equals(segment[0]) ? "APPLIES_TO_SEGMENT_100_CONTEXT" : "NOT_INFERRED_FROM_TRANSACTION_TYPE_ALONE");
                entry.put("requiredEvidence", "segment rule catalog, message-family applicability, lifecycle context, and request Test Data JSON");
            }
        }
        root.putObject("summary")
                .put("segmentCount", SEGMENTS.size())
                .put("transactionTypeCount", TRANSACTION_TYPES.length)
                .put("matrixEntryCount", SEGMENTS.size() * TRANSACTION_TYPES.length)
                .put("segment100BaselineEntries", TRANSACTION_TYPES.length)
                .put("otherSegmentEntriesRequireContextReview", (SEGMENTS.size() - 1) * TRANSACTION_TYPES.length);
        Files.createDirectories(output.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), root);
        System.out.printf("segments=%d transactionTypes=%d matrixEntries=%d output=%s%n",
                SEGMENTS.size(), TRANSACTION_TYPES.length, SEGMENTS.size() * TRANSACTION_TYPES.length, output);
    }
}
