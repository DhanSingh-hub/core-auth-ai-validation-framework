import argparse
import hashlib
import json
import os
import re
from collections import defaultdict
from pathlib import Path

RULES = [
    ("DEBIT_EXPIRATION_ONLINE", "10.2.1.1", "100", "TRANSACTION_TYPE", "Debit processing does not validate expiration and still requests online authorization", "When a card is processed as debit, the device does not validate expiration and requests online authorization regardless of the card's expiration date.", "The device does not perform expiration date validation on a debit card when the card is processed as a debit transaction.", ["debit transaction", "expired card", "online authorization regardless of expiry"], "high"),
    ("COMPLETION_SINGLE_CAPTURE", "10.2.1.2", "100", "LIFECYCLE", "Debit Completion is processed once and cannot be reversed or resubmitted", "A Debit Card Completion can be processed only one time by BUYPASS/debit networks and cannot be reversed or processed again by the device.", "When processing a Debit Card Completion transaction, the transaction can only be processed one time by BUYPASS and debit networks.", ["Debit Card Completion", "duplicate/reversal attempt"], "high"),
    ("COMPLETION_NO_RESPONSE_RETRY", "10.2.1.2", "100", "LIFECYCLE", "Debit Completion with no response retries on next dial or after thirty minutes", "When BUYPASS returns no response to a Debit Completion, resend on the next dial attempt or 30 minutes later, whichever occurs first.", "In the event the device sends a Debit Card Completion transaction to BUYPASS and does not receive a response, the device should resend the request on the next dial attempt or 30 minutes later—whichever occurs first.", ["Debit Completion", "no response", "next dial", "30-minute limit"], "high"),
    ("COMPLETION_DECLINE24_CAPTURED", "10.2.1.2", "100", "LIFECYCLE", "Debit Completion decline code 24 means already captured and updates totals as approved", "A response code 1 with Decline Code 24 is treated as already captured on BUYPASS and the device updates totals as approved.", "If the device receives a Response Code value of 1 (indicating a \"decline\") and a Decline Code value of 24 in the transaction response message, then the device should treat the response as already captured on BUYPASS and update the device totals as \"approved.\"", ["Debit Completion", "Response Code 1", "Decline Code 24", "totals"], "high"),
    ("COMPLETION_RESPONSE_STOPS_RETRY", "10.2.1.2", "100", "LIFECYCLE", "Any BUYPASS response ends Debit Completion retry attempts", "Any response, approved or declined, ends retries for that Debit Completion.", "Any response from BUYPASS to a Debit Card Completion transaction should end attempts to retry a Debit Card Completion whether \"approved\" or \"declined.\"", ["Debit Completion response", "approved/declined", "retry stops"], "high"),
    ("DEBIT_PIN_REQUIRED", "10.2.1.3", "100", "TRANSACTION_TYPE", "Debit transactions require PIN except Purchase Reversal", "PIN entry is required for debit transactions except Purchase Reversal.", "All transactions—except the Purchase Reversal transaction—require PIN entry.", ["debit", "Purchase Reversal exception", "PIN present/absent"], "high"),
    ("DEBIT_PIN_ENCRYPTION", "10.2.1.3", "100", "CORE_STRUCTURE", "Debit PIN uses DES-compliant entry and DUKPT PIN encryption", "When PIN is required for debit, entry occurs on a DES-compliant device and is encrypted using DUKPT PIN encryption.", "the entry of the customer PIN is required on a DES- compliant entry device, and it must be encrypted using the DUKPT PIN encryption method.", ["DES-compliant entry device", "DUKPT PIN encryption"], "high"),
    ("DEBIT_MANUAL_ENTRY_PROHIBITED", "10.2.1.4", "100", "TRANSACTION_TYPE", "Manual card entry is prohibited for debit transactions", "The device does not offer manual entry for debit transactions.", "Manual entry is not allowed on a debit card transaction.", ["debit card", "manual entry offered/prohibited"], "high"),
    ("DEBIT_UNREADABLE_CARD_RETRIES", "10.2.1.4", "100", "TRANSACTION_TYPE", "Unreadable debit magnetic stripe prompts up to three swipes then ends with clerk message", "After up to three unsuccessful swipe attempts, the device displays a message to the clerk and ends the transaction; it does not fall back to manual entry.", "If the device is unable to read the card's magnetic strip, it should prompt the user to swipe the card again—up to three times.", ["debit", "magnetic stripe unreadable", "retry count", "clerk message", "transaction ended"], "high"),
    ("DEBIT_POS_TRANSACTION_TYPES", "10.2.2", "100", "TRANSACTION_TYPE", "POS debit processing supports the documented debit transaction set", "Point-of-sale debit supports Purchase/Capture, Purchase with Cashback, Purchase Reversal, Authorization Only, Merchandise Return, Time-out Reversal (except debit Completion TOR), Void of Merchandise Return, Balance Inquiry, Partial Approval, and Authorization Only Reversal.", "The following point-of-sale transactions are supported for debit card processing:", ["POS debit", "all listed transaction types", "debit Completion TOR exclusion"], "high"),
    ("DEBIT_CAT_TRANSACTION_TYPES", "10.2.2", "100", "TRANSACTION_TYPE", "Customer-activated debit supports its documented transaction set", "CAT debit supports Purchase/Capture, Authorization Only, Time-out Reversal, Partial Approval, and Authorization Only Reversal.", "The following customer-activated transactions are supported for debit card processing:", ["CAT debit", "each listed transaction type"], "high"),
    ("DEBIT_PARTIAL_APPROVAL_RESPONSE", "10.2.2.1", "100", "RESPONSE_NETWORK", "Debit partial approval returns code F, approved amount, balance, and remaining-balance prompt", "When Partial Approval Indicator 1 is sent, a supporting debit network returns code F and the approved amount; POS prompts for remaining balance and issuer-returned balance is in Segment 112.", "A Response Code (Element No. 83) value of F (Indicates “approval, partial amount approved.”)", ["PIN debit", "Indicator 1", "Response Code F", "approved amount", "remaining balance", "Segment 112 balance"], "high"),
    ("DEBIT_PARTIAL_APPROVAL_CONTEXTS", "10.2.2.1", "100", "TRANSACTION_TYPE", "Debit partial approval applies to documented POS/CAT contexts", "Partial Approval is supported for POS Purchase, POS Purchase with Cashback, POS Authorization Only, and CAT Authorization Only.", "POS Purchase with Cashback", ["POS Purchase", "POS Purchase with Cashback", "POS Authorization Only", "CAT Authorization Only"], "high"),
    ("INTERLINK_CASHBACK_MANDATE", "10.2.2.1", "100", "TRANSACTION_TYPE", "Interlink cashback support requires merchant support for partial approval", "The source states Interlink requires merchants supporting cashback to also support Partial Approval.", "Interlink requires that merchants supporting cashback must also support Partial Approval transactions.", ["Interlink", "cashback supported", "partial approval support"], "high"),
    ("DEBIT_PARTIAL_CASHBACK_ALLOCATION", "10.2.2.1", "100", "TRANSACTION_TYPE", "Partial approval of debit purchase with cashback applies to purchase amount only", "When a debit Purchase with Cashback is partially approved, the approved amount is for the purchase only and does not include cashback.", "cashback is partially approved, then the partially approved amount will be for the purchase only and not cashback.", ["debit Purchase with Cashback", "partial approval", "approved amount excludes cashback"], "high"),
    ("DEBIT_PARTIAL_REVERSAL_AMOUNT", "10.2.2.1", "100", "LIFECYCLE", "Debit reversal of a partially approved transaction uses original approved amount", "The reversal amount equals the amount approved in the original response when the debit transaction was partially approved.", "The amount in a Reversal (Void) request must be the amount approved in the original transaction response when a transaction has been partially approved.", ["debit partial approval", "Reversal/Void", "original approved amount"], "high"),
    ("DEBIT_PARTIAL_TOR_AMOUNT_SEQUENCE", "10.2.2.1", "100", "LIFECYCLE", "Debit timeout reversal preserves approved amount and original Sequence Number", "For partial approval, debit TOR uses the amount approved in the original response and the Sequence Number from the original request.", "The amount in a TOR request must be the amount approved in the original transaction response when a transaction has been partially approved.", ["debit partial approval", "TOR", "original approved amount", "original Sequence Number"], "high"),
    ("DEBIT_AUTH_REVERSAL_CONTEXT", "10.2.2.2", "100", "LIFECYCLE", "Debit Authorization Only Reversal covers POS type 3 and CAT type 5", "Debit authorization-only reversal applies to POS Authorization Only transaction type 3 and CAT Authorization Only type 5; common timing and matching rules are cross-referenced from Section 10.1 drafts pending review.", "POS Authorization Only (Transaction Type code “3”)", ["debit POS authorization-only", "type 3", "debit CAT authorization-only", "type 5"], "high"),
    ("DEBIT_HEALTHCARE_ISSUER_SCOPE", "10.2.3", "100", "COMPANION_DOMAIN", "Healthcare auto-substantiation is supported for PIN debit issuers", "The Section 10.2 healthcare service applies to supported PIN debit issuers and FSA/HRA purchase contexts; common healthcare requirements are referenced from Section 10.1 drafts pending review.", "The Healthcare Auto-Substantiation Service provided by credit card issuers (Amex®, Discover® Network, MasterCard®, Visa®) and PIN debit card issuers, is supported by these specifications.", ["PIN debit issuer", "FSA/HRA", "healthcare auto-substantiation"], "high"),
    ("DEBIT_HEALTHCARE_REQUEST_CONTEXT", "10.2.3.2", "100,102,111", "COMPANION_DOMAIN", "Debit healthcare request includes MSDI, Partial Approval Indicator, and Product Code", "The FSA/HRA debit request carries MSDI in Segment 111, Partial Approval Indicator in Segment 100, and Product Code Segment 102; common field behavior reuses Section 10.1 healthcare draft rules.", "FSA/HRA transaction requests include the following data fields:", ["FSA/HRA debit request", "MSDI Segment 111", "Partial Approval Indicator Segment 100", "Product Code Segment 102"], "high"),
    ("DEBIT_HEALTHCARE_RESPONSE_STANDARD", "10.2.3.3", "100", "COMPANION_DOMAIN", "Debit healthcare responses use standard response data without service-specific elements", "FSA/HRA debit responses use standard response data and define no healthcare-specific response elements.", "FSA/HRA transaction responses contain standard response data.", ["FSA/HRA debit response", "standard response data", "no service-specific response fields"], "medium"),
    ("DEBIT_HEALTHCARE_TYPES", "10.2.3.4", "100", "TRANSACTION_TYPE", "Debit healthcare supports purchase, reversal, return, and timeout types", "FSA/HRA debit supports Purchase/Capture, Purchase Reversal, Merchandise Return, and Time-out Reversal; detailed shared processing rules reference Section 10.1 healthcare drafts.", "Purchase/Capture", ["PIN debit FSA/HRA", "each supported transaction type"], "high"),
    ("DEBIT_HEALTHCARE_RECEIPT", "10.2.3.5", "100", "RESPONSE_NETWORK", "Approved debit healthcare receipt includes QHP indicator and subtotal", "Approved FSA/HRA debit receipts print the QHP item indicator and subtotal including taxes and discounts; other receipt requirements are inherited by reference.", "The debit card receipt requirements listed in section 10.2.4, “Receipt Requirements,” apply to FSA/HRA card transactions.", ["approved debit FSA/HRA receipt", "QHP indicator", "QHP subtotal"], "medium"),
    ("DEBIT_RECEIPT_PIN_USED", "10.2.4", "100", "RESPONSE_NETWORK", "Debit receipt may print PIN USED when a PIN is entered", "A debit receipt may optionally include a PIN USED message when PIN was entered.", "Optionally, a PIN USED message can be printed on transaction receipts when a PIN is entered.", ["debit PIN entered", "receipt message"], "medium"),
    ("DEBIT_RECEIPT_SIGNATURE", "10.2.4", "100", "RESPONSE_NETWORK", "Debit receipt requires no signature line and may show SIGNATURE NOT REQUIRED", "A debit receipt does not require a signature line; a SIGNATURE NOT REQUIRED message is optional.", "No signature line is required when printing a debit receipt.", ["debit receipt", "signature line absent", "optional message"], "medium"),
    ("DEBIT_PIN_BLOCK_FOLLOW_ON", "10.2.5.1", "100", "CORE_STRUCTURE", "PIN block data is excluded from debit follow-on transactions", "PIN block data is not included in Debit Completion or Reversal follow-on transactions.", "PIN block data is not included in follow-on transactions (Completions/Reversals).", ["debit completion", "debit reversal", "PIN block data"], "high"),
    ("DEBIT_TRACK2_DEFAULT", "10.2.5.2", "100", "CORE_STRUCTURE", "Debit processing sends Track 2 as the default track to BUYPASS", "For electronic debit transactions, Track 2 is the default track sent to BUYPASS.", "The default track that is sent to BUYPASS should be Track 2.", ["electronic debit", "host track default"], "high"),
    ("DEBIT_HOST_CARD_DATA", "10.2.5.2", "100", "CORE_STRUCTURE", "Debit follow-on host message contains account number and equals-delimited expiration only", "For debit Completions, POS Purchase Reversals, Time-out Reversals, POS Cancellations, and Cancellations, host card data uses Account Number followed by '=' and four-digit YYMM expiration, with no trailing Card Discretionary Data.", "The host message should only contain the following card data information in the Account Number and Card Discretionary Block Data fields:", ["debit completion/reversal/cancellation", "Account Number", "equals delimiter", "4-digit YYMM", "no trailing discretionary data"], "high"),
    ("DEBIT_INFLIGHT_TRACK2", "10.2.5.2", "100", "CORE_STRUCTURE", "Debit Track 2 is retained in the In Flight Table only during transaction processing", "Track 2 may remain in the In Flight Table while the debit transaction is in process and is removed when the transaction completes; shared storage/clearing rules reference Section 10.1 drafts.", "Track 2 data is kept in this table while the transaction is in process.", ["in-flight transaction", "Track 2", "completion removes data"], "high"),
]

REUSE_KEYS = [
    "AUTH_REVERSAL_TIMELINES", "AUTH_REVERSAL_ISSUER_RELEASE", "AUTH_REVERSAL_TYPES", "AUTH_REVERSAL_MATCHING",
    "PARTIAL_APPROVAL_RESPONSE", "PARTIAL_APPROVAL_CONTEXTS", "PARTIAL_REVERSAL_AMOUNT",
    "HEALTHCARE_SERVICE_SCOPE", "HEALTHCARE_BENEFIT_BIN", "HEALTHCARE_PARTIAL_SPLIT_TENDER",
    "HEALTHCARE_QHP_890", "HEALTHCARE_QHP_891", "HEALTHCARE_QHP_892", "HEALTHCARE_QHP_893", "HEALTHCARE_QHP_894", "HEALTHCARE_QHP_895", "HEALTHCARE_QHP_896",
    "HEALTHCARE_REQUEST_FIELDS", "HEALTHCARE_RESPONSE_STANDARD", "HEALTHCARE_TRANSACTION_TYPES",
    "HEALTHCARE_PURCHASE_TOTALS", "HEALTHCARE_PURCHASE_REVERSAL_TOTALS", "HEALTHCARE_RETURN_TOTALS", "HEALTHCARE_TOR_TOTALS", "HEALTHCARE_RECEIPT_ADDITIONS",
    "CREDIT_RECEIPT_MERCHANT_INFO", "CREDIT_RECEIPT_TRANSACTION_TYPE", "CREDIT_RECEIPT_MASK_ACCOUNT", "CREDIT_RECEIPT_SUPPRESS_EXPIRATION",
    "CREDIT_RECEIPT_TRANSACTION_DATE", "CREDIT_RECEIPT_TRANSACTION_TIME", "CREDIT_RECEIPT_SEQUENCE_NUMBER",
    "CREDIT_RECEIPT_FUEL_PRODUCT", "CREDIT_RECEIPT_NONFUEL_PRODUCT", "CREDIT_RECEIPT_BALANCE_FIELDS", "CREDIT_RECEIPT_APPROVAL_DECLINE", "CREDIT_RECEIPT_SIGNATURE",
    "REGULATORY_CARD_DATA_DISPLAY", "REGULATORY_RESEARCH_STORAGE", "REGULATORY_CLEARING_SOURCE", "REGULATORY_HOST_CARD_DATA", "REGULATORY_IN_FLIGHT_EXCEPTION",
]

BASELINE_REUSE_IDS = ["SEG100-R-020", "SEG100-R-039", "SEG100-R-065"]

SECTION_GROUPS = {
    "10.2": ["10.2.1", "10.2.2", "10.2.3", "10.2.4", "10.2.5"],
    "10.2.1": ["10.2.1.1", "10.2.1.2", "10.2.1.3", "10.2.1.4"],
    "10.2.3": ["10.2.3.1", "10.2.3.2", "10.2.3.3", "10.2.3.4", "10.2.3.5"],
    "10.2.3.1": ["10.2.3.1.1", "10.2.3.1.2", "10.2.3.1.3", "10.2.3.1.4"],
    "10.2.3.4": ["10.2.3.4.1", "10.2.3.4.2", "10.2.3.4.3", "10.2.3.4.4"],
    "10.2.5": ["10.2.5.1", "10.2.5.2"],
}

SECTION_COVERAGE = [
    {"section": "10.2.1.1", "draftRules": ["DEBIT_EXPIRATION_ONLINE"], "reused10_1Rules": [], "reusedBaselineRules": []},
    {"section": "10.2.1.2", "draftRules": ["COMPLETION_SINGLE_CAPTURE", "COMPLETION_NO_RESPONSE_RETRY", "COMPLETION_DECLINE24_CAPTURED", "COMPLETION_RESPONSE_STOPS_RETRY"], "reused10_1Rules": [], "reusedBaselineRules": []},
    {"section": "10.2.1.3", "draftRules": ["DEBIT_PIN_REQUIRED", "DEBIT_PIN_ENCRYPTION"], "reused10_1Rules": [], "reusedBaselineRules": []},
    {"section": "10.2.1.4", "draftRules": ["DEBIT_MANUAL_ENTRY_PROHIBITED", "DEBIT_UNREADABLE_CARD_RETRIES"], "reused10_1Rules": [], "reusedBaselineRules": []},
    {"section": "10.2.2", "draftRules": ["DEBIT_POS_TRANSACTION_TYPES", "DEBIT_CAT_TRANSACTION_TYPES"], "reused10_1Rules": [], "reusedBaselineRules": []},
    {"section": "10.2.2.1", "draftRules": ["DEBIT_PARTIAL_APPROVAL_RESPONSE", "DEBIT_PARTIAL_APPROVAL_CONTEXTS", "INTERLINK_CASHBACK_MANDATE", "DEBIT_PARTIAL_CASHBACK_ALLOCATION", "DEBIT_PARTIAL_REVERSAL_AMOUNT", "DEBIT_PARTIAL_TOR_AMOUNT_SEQUENCE"], "reused10_1Rules": ["PARTIAL_APPROVAL_RESPONSE", "PARTIAL_APPROVAL_CONTEXTS", "PARTIAL_REVERSAL_AMOUNT"], "reusedBaselineRules": ["SEG100-R-020", "SEG100-R-039", "SEG100-R-065"]},
    {"section": "10.2.2.2", "draftRules": ["DEBIT_AUTH_REVERSAL_CONTEXT"], "reused10_1Rules": ["AUTH_REVERSAL_TIMELINES", "AUTH_REVERSAL_ISSUER_RELEASE", "AUTH_REVERSAL_TYPES", "AUTH_REVERSAL_MATCHING"], "reusedBaselineRules": []},
    {"section": "10.2.3", "draftRules": ["DEBIT_HEALTHCARE_ISSUER_SCOPE"], "reused10_1Rules": ["HEALTHCARE_SERVICE_SCOPE"], "reusedBaselineRules": []},
    {"section": "10.2.3.1.1", "draftRules": [], "reused10_1Rules": ["HEALTHCARE_BENEFIT_BIN"], "reusedBaselineRules": []},
    {"section": "10.2.3.1.2", "draftRules": [], "reused10_1Rules": ["HEALTHCARE_PARTIAL_SPLIT_TENDER"], "reusedBaselineRules": []},
    {"section": "10.2.3.1.3", "draftRules": [], "reused10_1Rules": ["HEALTHCARE_PARTIAL_SPLIT_TENDER"], "reusedBaselineRules": []},
    {"section": "10.2.3.1.4", "draftRules": [], "reused10_1Rules": ["HEALTHCARE_QHP_890", "HEALTHCARE_QHP_891", "HEALTHCARE_QHP_892", "HEALTHCARE_QHP_893", "HEALTHCARE_QHP_894", "HEALTHCARE_QHP_895", "HEALTHCARE_QHP_896"], "reusedBaselineRules": []},
    {"section": "10.2.3.2", "draftRules": ["DEBIT_HEALTHCARE_REQUEST_CONTEXT"], "reused10_1Rules": ["HEALTHCARE_REQUEST_FIELDS"], "reusedBaselineRules": ["SEG100-R-020"]},
    {"section": "10.2.3.3", "draftRules": ["DEBIT_HEALTHCARE_RESPONSE_STANDARD"], "reused10_1Rules": ["HEALTHCARE_RESPONSE_STANDARD"], "reusedBaselineRules": []},
    {"section": "10.2.3.4", "draftRules": ["DEBIT_HEALTHCARE_TYPES"], "reused10_1Rules": ["HEALTHCARE_TRANSACTION_TYPES"], "reusedBaselineRules": []},
    {"section": "10.2.3.4.1", "draftRules": [], "reused10_1Rules": ["HEALTHCARE_PURCHASE_TOTALS"], "reusedBaselineRules": []},
    {"section": "10.2.3.4.2", "draftRules": [], "reused10_1Rules": ["HEALTHCARE_PURCHASE_REVERSAL_TOTALS"], "reusedBaselineRules": []},
    {"section": "10.2.3.4.3", "draftRules": [], "reused10_1Rules": ["HEALTHCARE_RETURN_TOTALS"], "reusedBaselineRules": []},
    {"section": "10.2.3.4.4", "draftRules": [], "reused10_1Rules": ["HEALTHCARE_TOR_TOTALS"], "reusedBaselineRules": []},
    {"section": "10.2.3.5", "draftRules": ["DEBIT_HEALTHCARE_RECEIPT"], "reused10_1Rules": ["HEALTHCARE_RECEIPT_ADDITIONS"], "reusedBaselineRules": []},
    {"section": "10.2.4", "draftRules": ["DEBIT_RECEIPT_PIN_USED", "DEBIT_RECEIPT_SIGNATURE"], "reused10_1Rules": ["CREDIT_RECEIPT_MERCHANT_INFO", "CREDIT_RECEIPT_TRANSACTION_TYPE", "CREDIT_RECEIPT_MASK_ACCOUNT", "CREDIT_RECEIPT_SUPPRESS_EXPIRATION", "CREDIT_RECEIPT_TRANSACTION_DATE", "CREDIT_RECEIPT_TRANSACTION_TIME", "CREDIT_RECEIPT_SEQUENCE_NUMBER", "CREDIT_RECEIPT_FUEL_PRODUCT", "CREDIT_RECEIPT_NONFUEL_PRODUCT", "CREDIT_RECEIPT_BALANCE_FIELDS", "CREDIT_RECEIPT_APPROVAL_DECLINE", "CREDIT_RECEIPT_SIGNATURE"], "reusedBaselineRules": []},
    {"section": "10.2.5.1", "draftRules": ["DEBIT_PIN_BLOCK_FOLLOW_ON"], "reused10_1Rules": ["REGULATORY_CARD_DATA_DISPLAY", "REGULATORY_RESEARCH_STORAGE", "REGULATORY_CLEARING_SOURCE"], "reusedBaselineRules": []},
    {"section": "10.2.5.2", "draftRules": ["DEBIT_TRACK2_DEFAULT", "DEBIT_HOST_CARD_DATA", "DEBIT_INFLIGHT_TRACK2"], "reused10_1Rules": ["REGULATORY_CLEARING_SOURCE", "REGULATORY_IN_FLIGHT_EXCEPTION"], "reusedBaselineRules": []},
]


def norm(value):
    return re.sub(r"\s+", " ", str(value or "")).strip()


def source_sections(source):
    lines = source.splitlines()
    starts = [i for i, line in enumerate(lines) if line.strip().startswith("10.2 Debit Card Processing Requirements")]
    if not starts:
        raise ValueError("Section 10.2 heading not found in extracted specification")
    start = starts[-1]
    sections = []
    heading = re.compile(r"^(10\.2(?:\.\d+)*)\s+.+$")
    for line in lines[start:]:
        if line.strip().startswith("10.3 PINless POS Debit Card Processing Requirements"):
            break
        match = heading.fullmatch(line.strip())
        if match and match.group(1) not in sections:
            sections.append(match.group(1))
    return sections


def digest(path):
    h = hashlib.sha256()
    with path.open("rb") as f:
        for block in iter(lambda: f.read(1024 * 1024), b""):
            h.update(block)
    return h.hexdigest()


def build(pack_root, output):
    pack_root = Path(pack_root).resolve()
    output = Path(output).resolve()
    if output.exists():
        raise ValueError("Refusing to overwrite Section 10.2 output")
    spec = (pack_root / "docs/specs/extracted_text.txt").read_text(encoding="utf-8-sig")
    baseline_path = pack_root / "test-output/test-solution-independent-review/source-derived-requirement-inventory.json"
    baseline = json.loads(baseline_path.read_text(encoding="utf-8-sig"))
    complete_path = pack_root / "test-output/test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json"
    complete = json.loads(complete_path.read_text(encoding="utf-8-sig"))
    sec10_1_dir = pack_root / "test-output/test-solution-independent-review/section-10-1-credit-card-processing-review-v4"
    sec10_1 = json.loads((sec10_1_dir / "section-10-1-br-ts-tc-td-draft-package.json").read_text(encoding="utf-8-sig"))
    if baseline.get("authority") != "INDEPENDENT_TEST_SOLUTION" or baseline.get("aiArtifactsIncluded") is not False:
        raise ValueError("Independent baseline provenance failed")
    if len(baseline.get("businessRequirements", [])) != 601:
        raise ValueError("Expected unchanged 601-rule independent baseline")
    if sec10_1["manifest"].get("status") != "DRAFT_REVIEW_REQUIRED":
        raise ValueError("Section 10.1 references may only target the review-only supplement")

    sec10_scenarios = sec10_1.get("testScenarios", [])
    sec10_cases = sec10_1.get("testCases", [])
    sec10_data = sec10_1.get("testData", [])

    def referenced_chain(package_br, package_scenarios, package_cases, package_data):
        br_id = package_br["id"]
        linked_scenarios = [x for x in package_scenarios if br_id in x.get("requirementIds", [])]
        scenario_ids = {x["id"] for x in linked_scenarios}
        linked_cases = [x for x in package_cases if set(x.get("scenarioIds", [])) & scenario_ids]
        case_ids = {x["id"] for x in linked_cases}
        linked_data = [x for x in package_data if set(x.get("testCaseIds", [])) & case_ids]
        if not linked_scenarios or not linked_cases or not linked_data:
            raise ValueError("Reusable rule does not have a complete review chain: " + br_id)
        return {"businessRequirementId": br_id,
                "scenarioIds": [x["id"] for x in linked_scenarios],
                "testCaseIds": [x["id"] for x in linked_cases],
                "testDataIds": [x["id"] for x in linked_data],
                "allChainStatusesReviewRequired": all(
                    x.get("status") == "REVIEW_REQUIRED" for x in linked_scenarios + linked_cases
                ) and all(x.get("readiness") == "REVIEW_REQUIRED" for x in linked_data)}

    reuse_by_key = {}
    for br in sec10_1.get("businessRequirements", []):
        rule_key = br.get("sourceAnchors", [{}])[0].get("rule", "").removeprefix("section-10-1-").upper().replace("-", "_")
        reuse_by_key[rule_key] = (br, referenced_chain(br, sec10_scenarios, sec10_cases, sec10_data))
    reused = []
    reused_by_key = {}
    for key in dict.fromkeys(REUSE_KEYS):
        reused_item = reuse_by_key.get(key)
        if reused_item is None:
            raise ValueError("Missing expected reusable Section 10.1 draft BR: " + key)
        br, chain = reused_item
        reference = {"draftBrId": br["id"], "topicKey": key, "title": br["title"],
                       "sourceAnchors": br["sourceAnchors"], "sourcePackage": "section-10-1-credit-card-processing-review-v4",
                       "linkedScenarioIds": chain["scenarioIds"], "linkedTestCaseIds": chain["testCaseIds"],
                       "linkedTestDataIds": chain["testDataIds"], "allChainStatusesReviewRequired": chain["allChainStatusesReviewRequired"],
                   "status": "REVIEW_REQUIRED", "reuseStatus": "CROSS_SECTION_DRAFT_REFERENCE_NOT_PROMOTED"}
        reused.append(reference)
        reused_by_key[key] = reference

    complete_brs = {x.get("id"): x for x in complete.get("businessRequirements", [])}
    baseline_ids = {x.get("ruleId") for x in baseline.get("businessRequirements", [])}
    reused_baseline = []
    reused_baseline_by_id = {}
    for rule_id in BASELINE_REUSE_IDS:
        if rule_id not in baseline_ids:
            raise ValueError("Requested reusable baseline BR is not in independent catalog: " + rule_id)
        br_id = "BR-RULE-" + rule_id
        br = complete_brs.get(br_id)
        if br is None:
            raise ValueError("Requested reusable baseline BR has no existing chain row: " + br_id)
        chain = referenced_chain(br, complete.get("testScenarios", []), complete.get("testCases", []), complete.get("testData", []))
        reference = {"testSolutionRuleId": rule_id, "businessRequirementId": br_id,
                                "title": br.get("title"), "sourceAnchors": br.get("sourceAnchors", []),
                                "sourcePackage": "complete-all-test-solution-br-ts-tc-td-package.json",
                                "linkedScenarioIds": chain["scenarioIds"], "linkedTestCaseIds": chain["testCaseIds"],
                                "linkedTestDataIds": chain["testDataIds"],
                                "allChainStatusesReviewRequired": chain["allChainStatusesReviewRequired"],
                                "reuseStatus": "EXISTING_INDEPENDENT_BASELINE_CHAIN_REFERENCED_NOT_DUPLICATED"}
        reused_baseline.append(reference)
        reused_baseline_by_id[rule_id] = reference

    new_brs, scenarios, cases, data, physical = [], [], [], [], []
    new_chain_by_key = {}
    topic_chains = defaultdict(list)
    for n, row in enumerate(RULES, 1):
        key, section, segment, category, title, requirement, quote, fixture, priority = row
        if norm(quote) not in norm(spec):
            raise ValueError("Section 10.2 source quote is not in source: " + key)
        br_id = f"BR-DRAFT-10-2-{n:03d}"
        sc_id = f"SCN-DRAFT-10-2-{n:03d}"
        tc_id = f"TC-DRAFT-10-2-{n:03d}"
        td_id = f"TD-DRAFT-10-2-{n:03d}"
        anchor = {"specification": "ATL105", "version": "2026-3", "section": section,
                  "segment": segment, "rule": "section-10-2-" + key.lower().replace("_", "-")}
        br = {"id": br_id, "title": title, "requirement": requirement, "category": category,
              "applicability": "ATL105 debit card context in " + section, "priority": priority,
              "executionStatus": "REVIEW_REQUIRED", "sourceAnchors": [anchor],
              "sourceEvidence": [{"section": section, "verbatimExcerpt": quote}],
              "independenceStatus": "DRAFT_REVIEW_REQUIRED_NOT_IN_BASELINE"}
        sc = {"id": sc_id, "name": "Review " + key.lower().replace("_", " "),
              "requirementIds": [br_id], "status": "REVIEW_REQUIRED", "sourceAnchors": [anchor],
              "testIntent": requirement}
        filename = "test-data/" + tc_id + ".json"
        tc = {"id": tc_id, "scenarioIds": [sc_id], "expectedOutcome": "REVIEW", "testDataFile": filename,
              "category": category.lower(), "tags": ["section-10.2", "debit", "draft-review-required", key.lower()],
              "priority": priority, "status": "REVIEW_REQUIRED", "sourceAnchors": [anchor],
              "reviewObjective": requirement}
        plan = {"reviewOnly": True, "testDataDesign": "NOT_A_CONVERTER_READY_ATL105_REQUEST",
                "fixturePlan": fixture, "sourceSection": section, "requiredContext": key,
                "executionAllowed": False}
        td = {"id": td_id, "testCaseIds": [tc_id], "expectedValidation": "REVIEW",
              "fileName": filename, "readiness": "REVIEW_REQUIRED", "sourceAnchors": [anchor],
              "payload": {"request": plan}, "dataStatus": "DRAFT_TEST_DATA_DESIGN_NOT_EXECUTABLE",
              "responseOracle": None}
        new_brs.append(br); scenarios.append(sc); cases.append(tc); data.append(td)
        chain = {"ruleKey": key, "businessRequirementId": br_id, "scenarioId": sc_id,
             "testCaseId": tc_id, "testDataId": td_id, "sourceQuote": quote}
        new_chain_by_key[key] = chain
        topic_chains[section].append(chain)
        physical.append((filename, {"testCaseId": tc_id, "testDataId": td_id,
                                   "status": "DRAFT_TEST_DATA_DESIGN_NOT_EXECUTABLE",
                                   "payload": td["payload"], "sourceAnchors": [anchor]}))

    coverage_by_section = {entry["section"]: entry for entry in SECTION_COVERAGE}
    for parent, children in SECTION_GROUPS.items():
        entry = coverage_by_section.setdefault(parent, {"section": parent, "draftRules": [],
                                                        "reused10_1Rules": [], "reusedBaselineRules": []})
        entry["groupedChildSections"] = children
    source_heading_list = source_sections(spec)
    missing_section_map = [section for section in source_heading_list if section not in coverage_by_section]
    if missing_section_map:
        raise ValueError("Unmapped Section 10.2 source headings: " + ", ".join(missing_section_map))
    section_coverage_map = []
    for section in source_heading_list:
        entry = coverage_by_section[section]
        unknown_drafts = set(entry.get("draftRules", [])) - set(new_chain_by_key)
        unknown_reuse = set(entry.get("reused10_1Rules", [])) - set(reused_by_key)
        unknown_baseline = set(entry.get("reusedBaselineRules", [])) - set(reused_baseline_by_id)
        if unknown_drafts or unknown_reuse or unknown_baseline:
            raise ValueError(f"Coverage map has unknown rule keys for {section}: {unknown_drafts | unknown_reuse | unknown_baseline}")
        direct_new = [new_chain_by_key[key] for key in entry.get("draftRules", [])]
        direct_reused = [reused_by_key[key] for key in entry.get("reused10_1Rules", [])]
        direct_baseline = [reused_baseline_by_id[key] for key in entry.get("reusedBaselineRules", [])]
        has_evidence = bool(direct_new or direct_reused or direct_baseline)
        has_group = bool(entry.get("groupedChildSections"))
        if not has_evidence and not has_group:
            raise ValueError("Source heading has no draft/reused evidence or child grouping: " + section)
        section_coverage_map.append({
            "section": section,
            "coverageDisposition": "GROUPED_SUBSECTIONS" if has_group and not has_evidence
                else "MAPPED_TO_REVIEW_REQUIRED_DRAFT_OR_REUSED_CHAINS",
            "draftChains": direct_new,
            "reusedSection10_1Chains": direct_reused,
            "reusedIndependentBaselineChains": direct_baseline,
            "groupedChildSections": entry.get("groupedChildSections", []),
            "approved": False,
            "coverageCredit": 0,
        })
    extra_coverage_sections = sorted(set(coverage_by_section) - set(source_heading_list))
    if extra_coverage_sections:
        raise ValueError("Coverage map includes non-source headings: " + ", ".join(extra_coverage_sections))

    package = {
        "manifest": {
            "packageId": "ATL105-SECTION-10-2-DEBIT-CARD-DRAFT-REVIEW-001",
            "specification": "ATL105", "specificationVersion": "2026-3", "artifactContractVersion": "2",
            "strictExecutionContract": False, "authority": "INDEPENDENT_TEST_SOLUTION_DRAFT",
            "status": "DRAFT_REVIEW_REQUIRED", "aiArtifactsIncluded": False,
            "scope": "Section 10.2 Debit Card Processing Requirements, subsections 10.2.1-10.2.5.2",
            "executionCertified": False, "coverageCredit": 0, "independentBaselineRuleCount": 601,
            "newDraftRulesInBaseline": False, "sourceTextSha256": digest(pack_root / "docs/specs/extracted_text.txt"),
            "independentBaselineSha256": digest(baseline_path),
            "completeIndependentChainPackageSha256": digest(complete_path),
            "section10_1DraftPackageSha256": digest(sec10_1_dir / "section-10-1-br-ts-tc-td-draft-package.json"),
            "reusedSection10_1DraftRules": reused,
            "reusedExistingIndependentBaselineRules": reused_baseline,
            "reviewPolicy": "All new debit BR/TS/TC/TD records are REVIEW_REQUIRED; shared 10.1 rules remain draft references; TDs are non-executable design placeholders; no approval or coverage credit is asserted.",
        },
        "businessRequirements": new_brs, "testScenarios": scenarios, "testCases": cases, "testData": data,
    }
    assessment = {
        "artifact": "ATL105-SECTION-10-2-INDEPENDENT-BR-CHAIN-COVERAGE-ASSESSMENT",
        "status": "DRAFT_REVIEW_REQUIRED", "newDraftBRCount": len(new_brs),
        "newDraftScenarioCount": len(scenarios), "newDraftTestCaseCount": len(cases),
        "newDraftTestDataDesignCount": len(data), "reusedSection10_1RuleReferences": len(reused),
        "reusedExistingBaselineRuleReferences": len(reused_baseline),
        "reusedRules": reused, "reusedBaselineRules": reused_baseline,
        "topicChains": dict(topic_chains),
        "sourceSectionHeadings": source_heading_list,
        "subsectionCoverageMap": section_coverage_map,
        "unmappedSourceHeadings": missing_section_map,
        "extraneousCoverageMapSections": extra_coverage_sections,
        "coverageMapComplete": not missing_section_map and not extra_coverage_sections,
        "independentBaselineRuleCount": 601, "officialBaselineChanged": False,
        "confirmedMatches": 0, "coverageCredit": 0, "executionCertified": False,
        "manualReviewRequired": True,
        "notes": ["No exact Section 10.2 anchors exist in the current independent 601-rule inventory.",
                  "Repeated healthcare, receipt, and regulatory rules point to Section 10.1 v4 draft BRs and remain review-required.",
                  "Every source heading is mapped to a debit draft chain, a specific reused chain, or an explicit grouping of mapped children; mapping is structural coverage inventory, not semantic approval.",
                  "Issuer/acquirer process obligations require independent evidence; do not treat a POS stub as proof of issuer action.",
                  "No valid debit request/response fixture or host oracle is fabricated."],
    }
    output.mkdir(parents=True)
    (output / "section-10-2-br-ts-tc-td-draft-package.json").write_text(json.dumps(package, indent=2, ensure_ascii=True), encoding="utf-8")
    (output / "section-10-2-coverage-assessment.json").write_text(json.dumps(assessment, indent=2, ensure_ascii=True), encoding="utf-8")
    td_dir = output / "test-data"; td_dir.mkdir()
    for name, rec in physical:
        (output / name).write_text(json.dumps(rec, indent=2, ensure_ascii=True), encoding="utf-8")
    (output / "README.md").write_text(make_readme(assessment), encoding="utf-8")
    validate(package, assessment, output, spec, len(new_brs))
    print(json.dumps({"output": str(output), "draftBRs": len(new_brs), "draftTSs": len(scenarios),
                      "draftTCs": len(cases), "draftTDDesigns": len(data), "reused10_1Rules": len(reused),
                      "officialBaselineChanged": False, "coverageCredit": 0, "executionCertified": False}, indent=2))


def validate(package, assessment, output, source, expected):
    brs = {x["id"]: x for x in package["businessRequirements"]}
    scenarios = {x["id"]: x for x in package["testScenarios"]}
    cases = {x["id"]: x for x in package["testCases"]}
    data = {x["id"]: x for x in package["testData"]}
    assert len(brs) == len(scenarios) == len(cases) == len(data) == expected
    assert all(x["executionStatus"] == "REVIEW_REQUIRED" for x in brs.values())
    assert all(x["status"] == "REVIEW_REQUIRED" for x in scenarios.values())
    assert all(x["status"] == "REVIEW_REQUIRED" and x["expectedOutcome"] == "REVIEW" for x in cases.values())
    assert all(x["readiness"] == "REVIEW_REQUIRED" and x["payload"]["request"]["executionAllowed"] is False for x in data.values())
    for br in brs.values():
        assert norm(br["sourceEvidence"][0]["verbatimExcerpt"]) in norm(source)
        sc = next(s for s in scenarios.values() if br["id"] in s["requirementIds"])
        tc = next(t for t in cases.values() if sc["id"] in t["scenarioIds"])
        assert len([td for td in data.values() if tc["id"] in td["testCaseIds"]]) == 1
    for td in data.values():
        path = output / td["fileName"]
        assert path.is_file()
        record = json.loads(path.read_text(encoding="utf-8"))
        assert record["status"] == "DRAFT_TEST_DATA_DESIGN_NOT_EXECUTABLE"
    assert package["manifest"]["coverageCredit"] == 0 and package["manifest"]["executionCertified"] is False
    expected_sections = source_sections(source)
    section_map = assessment["subsectionCoverageMap"]
    assert assessment["coverageMapComplete"] is True
    assert assessment["unmappedSourceHeadings"] == []
    assert assessment["extraneousCoverageMapSections"] == []
    assert [entry["section"] for entry in section_map] == expected_sections
    for entry in section_map:
        assert entry["approved"] is False and entry["coverageCredit"] == 0
        assert entry["draftChains"] or entry["reusedSection10_1Chains"] or entry["reusedIndependentBaselineChains"] or entry["groupedChildSections"]
        for chain in entry["draftChains"]:
            assert chain["businessRequirementId"] in brs
            assert chain["scenarioId"] in scenarios and chain["testCaseId"] in cases and chain["testDataId"] in data
        for chain in entry["reusedSection10_1Chains"] + entry["reusedIndependentBaselineChains"]:
            assert chain["linkedScenarioIds"] and chain["linkedTestCaseIds"] and chain["linkedTestDataIds"]
            assert chain["allChainStatusesReviewRequired"] is True


def make_readme(assessment):
    coverage_rows = ["| Source heading | Draft BR IDs | Reused Section 10.1 chain IDs (BR / TS / TC / TD) | Existing baseline chain IDs | Grouped children |",
                     "|---|---|---|---|---|"]
    for entry in assessment["subsectionCoverageMap"]:
        draft_ids = ", ".join(chain["businessRequirementId"] for chain in entry["draftChains"]) or "-"
        reused_ids = "; ".join(
            f'{chain["topicKey"]}: {chain["draftBrId"]} / {",".join(chain["linkedScenarioIds"])} / {",".join(chain["linkedTestCaseIds"])} / {",".join(chain["linkedTestDataIds"])}'
            for chain in entry["reusedSection10_1Chains"]
        ) or "-"
        baseline_ids = "; ".join(
            f'{chain["testSolutionRuleId"]}: {chain["businessRequirementId"]} / {",".join(chain["linkedScenarioIds"])} / {",".join(chain["linkedTestCaseIds"])} / {",".join(chain["linkedTestDataIds"])}'
            for chain in entry["reusedIndependentBaselineChains"]
        ) or "-"
        groups = ", ".join(entry["groupedChildSections"]) or "-"
        coverage_rows.append(f'| {entry["section"]} | {draft_ids} | {reused_ids} | {baseline_ids} | {groups} |')
    return "\n".join([
        "# ATL105 Section 10.2 Debit Card Processing - Independent Draft BR/TS/TC/TD Supplement", "",
        "Status: DRAFT_REVIEW_REQUIRED. New debit rules are source-derived independently of AI wording; repeated requirements reuse Section 10.1 draft references only.", "",
        f"New debit-specific draft chains: {assessment['newDraftBRCount']} BR / {assessment['newDraftScenarioCount']} TS / {assessment['newDraftTestCaseCount']} TC / {assessment['newDraftTestDataDesignCount']} TD design files.",
        f"Section 10.1 draft rules referenced for repeated healthcare/receipt/regulatory behaviors: {assessment['reusedSection10_1RuleReferences']}; existing independent baseline chains referenced: {assessment['reusedExistingBaselineRuleReferences']}.",
        "The official 601-rule baseline is unchanged. No coverage credit or execution certification is granted. TD files are non-converter-ready planning placeholders.", "",
        "## Source-Heading Coverage Map", "",
        "Every Section 10.2 heading is structurally mapped below. References remain REVIEW_REQUIRED and confer no semantic or execution credit.", "",
        *coverage_rows, "",
        "## Review gates", "",
        "1. Review each debit BR against its exact ATL105 Section 10.2 quote and determine applicability, actor, transaction, and lifecycle scope.",
        "2. Review every Section 10.1 cross-reference because those source rules remain DRAFT_REVIEW_REQUIRED until separately approved.",
        "3. Split transaction lists into per-type scenario rows during fixture derivation; the summary BR does not prove each type was exercised.",
        "4. Supply valid POS/card/PIN/network state, real converter-ready debit messages and authorized response/host oracles.",
        "5. Check network mandates, actor timing, regulatory storage, mutation isolation, and BR->TS->TC->TD links; then seek authorized Test Solution approval.",
        "6. Do not begin execution certification or roll these draft rows into the independent baseline until these reviews complete.", "",
        "See `section-10-2-br-ts-tc-td-draft-package.json` and `section-10-2-coverage-assessment.json` for all rows and cross-references.",
    ]) + "\n"


def self_test():
    ids = [x[0] for x in RULES]
    assert len(ids) == len(set(ids)) and len(ids) == 29
    coverage_sections = [entry["section"] for entry in SECTION_COVERAGE]
    assert len(coverage_sections) == len(set(coverage_sections))
    assert len(REUSE_KEYS) == len(set(REUSE_KEYS))
    assert all(x[3] in {"TRANSACTION_TYPE", "COMPANION_DOMAIN", "LIFECYCLE", "RESPONSE_NETWORK", "CORE_STRUCTURE"} for x in RULES)
    assert all(x[1].startswith("10.2") for x in RULES)
    source_path = Path(__file__).resolve().parents[1] / "specifications/ATL105/docs/specs/extracted_text.txt"
    if source_path.is_file():
        headings = source_sections(source_path.read_text(encoding="utf-8-sig"))
        mapped_sections = set(coverage_sections) | set(SECTION_GROUPS)
        assert set(headings) == mapped_sections, f"Heading map mismatch: missing={set(headings) - mapped_sections}, extra={mapped_sections - set(headings)}"
    print(f"PASS: {len(ids)} unique Section 10.2 draft rules, unique reuse/map keys, and complete source-heading map")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--pack-root", type=Path)
    parser.add_argument("--output-directory", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test(); return
    if not args.pack_root or not args.output_directory:
        parser.error("--pack-root and --output-directory are required")
    build(args.pack_root, args.output_directory)


if __name__ == "__main__":
    main()
