import argparse
import hashlib
import json
import os
import re
from collections import Counter, defaultdict
from pathlib import Path


RULES = [
    ("CARD_EXPIRY_VALIDATION", "10.1.1.1", "100", "TRANSACTION_TYPE", "Credit card expiration date is validated", "For a credit-card transaction, the device validates the expiration date of the presented card.", "The device performs expiration date validation in a credit card transaction.", ["valid in-range expiration", "expired or malformed expiration", "century boundary up to 1249"], "high"),
    ("TRACK2_DEFAULT_FALLBACK", "10.1.1.2", "100", "TRANSACTION_TYPE", "Track selection uses the supported default and fallback", "When Track 2 is supported, the device sends Track 2 by default; if it cannot read Track 2, it sends unaltered Track 1 before manual entry, subject to the Amex-specific rule.", "The default track that is sent to the host should be Track 2 if the device supports Track 2.", ["card brand", "reader track capabilities", "readable track data"], "high"),
    ("AMEX_TRACK1_DEFAULT", "10.1.1.2", "100", "TRANSACTION_TYPE", "American Express track selection uses Track 1 first", "For American Express, the device sends Track 1 by default and sends unaltered Track 2 before manual entry when Track 1 cannot be read.", "For American Express, the default track should be Track 1.", ["Amex card context", "Track 1 readable/unreadable"], "high"),
    ("AFD_ENCRYPTED_TRACK1", "10.1.1.2", "100", "COMPANION_DOMAIN", "Encrypted AFD Track 1 data is carried in its encryption block", "For an AFD transaction with encrypted TransArmor data, Track 1 is present in the Encryption Block and the Encryption Target identifies Track 1.", "If the AFD transaction being submitted contains encrypted data in the TransArmor Encryption Block, Track 1 data must be present inside the Encryption Block with Encryption Target as Track 1.", ["AFD transaction", "encrypted TransArmor block", "Track 1 target"], "high"),
    ("OFFLINE_TRACK_DATA", "10.1.1.2", "100", "LIFECYCLE", "Offline pre-authorization and sale preserve full track data", "An offline-approved Pre-Auth or Sale sends full track data; follow-up transactions use truncated Track 2 or truncated Track 2 facsimile.", "If a Pre-Auth transaction is approved offline, the full track data must be sent. Also if a Sale transaction is approved offline, the full track data must be sent.", ["Pre-Auth/Sale", "offline approval", "follow-up transaction"], "high"),
    ("MANUAL_ENTRY_RETRY", "10.1.1.3", "100", "TRANSACTION_TYPE", "Manual entry follows three unsuccessful magnetic-stripe attempts", "After three unsuccessful magnetic-stripe reads, the device prompts for manual Account Number and Expiration Date in MMYY format.", "If unable to read the magnetic strip after three attempts, the device should prompt for manual entry of the Account Number and Expiration Date (Format:  MMYY).", ["credit card", "three failed swipes", "manual entry prompt"], "high"),
    ("MANUAL_ENTRY_RECEIPT_MARKER", "10.1.1.3", "100", "TRANSACTION_TYPE", "Manual entry is identified on receipt and journal", "When manual entry is used, an asterisk precedes the Account Number on the receipt and journal.", "If manual entry is used, this fact is indicated on the receipt and journal by an asterisk (*) before the Account Number.", ["manual entry", "receipt", "journal"], "medium"),
    ("POS_CREDIT_TRANSACTION_TYPES", "10.1.2", "100", "TRANSACTION_TYPE", "POS credit-card processing supports the documented transaction set", "Point-of-sale credit processing supports the transaction types enumerated in Section 10.1.2, including purchase/capture, reversal, authorization-only, return, timeout reversal, mail/phone, void, balance inquiry, partial approval, and authorization-only reversal.", "The following point-of-sale transactions are supported for credit card processing:", ["POS credit-card processing", "each documented transaction type"], "high"),
    ("CAT_CREDIT_TRANSACTION_TYPES", "10.1.2", "100", "TRANSACTION_TYPE", "Customer-activated credit processing supports its documented transaction set", "Customer-activated credit processing supports Purchase/Capture, Authorization Only, Time-out Reversal, Partial Approval, and Authorization Only Reversal.", "The following customer-activated transactions are supported for credit card processing:", ["CAT credit-card processing", "each documented transaction type"], "high"),
    ("PARTIAL_APPROVAL_RESPONSE", "10.1.2.1", "100", "RESPONSE_NETWORK", "Partial approval response identifies the approved amount and remaining balance", "With Partial Approval Indicator 1, the network response uses Response Code F, provides the approved amount, and prompts payment of the remaining balance; issuer-returned balance is carried in Segment 112.", "A Response Code (Element No. 83) value of F (Indicates “approval, partial amount approved.”)", ["Partial Approval Indicator 1", "response code F", "approved amount", "remaining balance", "optional Segment 112 balance"], "high"),
    ("PARTIAL_APPROVAL_CONTEXTS", "10.1.2.1", "100", "TRANSACTION_TYPE", "Partial approval applies only to the documented POS and CAT transaction contexts", "Partial Approval is supported for POS Purchase, POS Authorization Only, and CAT Authorization Only.", "Partial Approval transactions are supported for:", ["POS Purchase", "POS Authorization Only", "CAT Authorization Only"], "high"),
    ("PARTIAL_REVERSAL_AMOUNT", "10.1.2.1", "100", "LIFECYCLE", "Partial-approval reversal uses the approved amount and is not a partial reversal", "A reversal of a partially approved authorization uses the amount approved in the original response; BUYPASS supports reversal of the partially approved authorization, not a partial reversal of an original transaction.", "The amount in a Reversal (Void) request must be the amount approved in the original transaction response when a transaction has been partially approved.", ["partial approval", "authorization reversal", "original approved amount"], "high"),
    ("PARTIAL_TOR_ORIGINAL_REQUEST", "10.1.2.1", "100", "LIFECYCLE", "Partial-approval timeout reversal preserves original requested amount and sequence", "For a partially approved transaction, the TOR uses the amount requested in the original request and the original request's Sequence Number.", "The amount in a TOR request must be the amount requested in the original transaction response when a transaction has been partially approved.", ["partial approval", "timeout reversal", "original amount", "original Sequence Number"], "high"),
    ("AUTH_REVERSAL_TIMELINES", "10.1.2.2", "100", "LIFECYCLE", "Approved authorizations are cleared or reversed within applicable timeframes", "Approved and partially approved authorizations are cleared or reversed; the source gives separate 24-hour card-present and 72-hour card-absent merchant windows.", "All approved and partially approved authorization transactions must be either cleared or reversed.", ["approved authorization", "card-present/card-absent context", "24/72-hour timing"], "high"),
    ("AUTH_REVERSAL_TYPES", "10.1.2.2", "100", "TRANSACTION_TYPE", "Authorization-only reversal supports POS and CAT authorization-only types", "The supported reversal contexts are POS Authorization Only type 3 and CAT Authorization Only type 5.", "POS Authorization Only (Transaction Type code “3”)", ["POS type 3", "CAT type 5"], "high"),
    ("AUTH_REVERSAL_MATCHING", "10.1.2.2", "100", "LIFECYCLE", "Authorization-only reversal matches original approval and sequence", "Successful matching requires the original Approval Number and Sequence Number to be present and identical, and Prompt Code S to identify cancellation.", "Element No. 5 (Approval Number)—present in the original transaction—must be present and identical in the Authorization Only Reversal transaction.", ["original authorization", "approval number", "sequence number", "Prompt Code S"], "high"),
    ("AVS_REQUEST_RESPONSE_PATH", "10.1.3", "100,111,112", "RESPONSE_NETWORK", "AVS request and response data use their specified data segments", "AVS information is sent in the Variable Information Data Segment and returned in the Additional Information Data Segment alongside the authorization response code.", "The device sends a transaction request to BUYPASS containing the AVS information in the Variable Information Data Segment", ["non-face-to-face transaction", "request Segment 111", "response Segment 112"], "high"),
    ("AVS_MERCHANT_DECISION", "10.1.3", "100", "RESPONSE_NETWORK", "AVS result does not override approval; merchant rejection uses purchase reversal", "BUYPASS processes an approved Purchase/Capture regardless of AVS result; if the merchant rejects due to AVS, the merchant performs a Purchase Reversal.", "BUYPASS processes all Purchase/Capture transactions when the Response Code is an approval, regardless of the AVS information.", ["approved Purchase/Capture", "AVS result", "merchant rejection and reversal"], "high"),
    ("BILL_RECURRING_INSTALLMENT_INDICATOR", "10.1.4", "111", "COMPANION_DOMAIN", "Bill-payment, recurring, and installment transactions carry the Variable Information Indicator", "The optional bill-payment/recurring/installment service is identified by a Variable Information Indicator in Segment 111.", "These transactions must be identified by a Variable Information Indicator located in the Variable Information section of the Variable Information Data Segment (Segment No. 111).", ["bill payment", "recurring", "installment", "Segment 111 indicator"], "medium"),
    ("RFID_TRACK2_CAPTURE", "10.1.5", "100", "COMPANION_DOMAIN", "RFID credit-card capture transmits unaltered Track 2 without card contact", "RFID capture obtains and transmits full, unaltered Track 2 data wirelessly when the card is near the receiver.", "it refers to the capability of a point-of-sale device to capture and transmit full, unaltered Track 2 data from a card without requiring the card to have contact with the point-of-sale device.", ["RFID receiver", "contactless card", "full unaltered Track 2"], "high"),
    ("RFID_ENTRY_MODE", "10.1.5", "100,111", "COMPANION_DOMAIN", "RFID receiver presence determines Point-of-Sale Entry Mode on every transaction", "When an RFID receiver is connected, Point-of-Sale Entry Mode is populated correctly for all transactions, including swiped, manually keyed, and non-RFID-applicable cards.", "Point-of-Sale Entry Mode (Contained in Element No. 113, Variable Information.) on all transactions when an RFID receiver is connected to the point-of-sale device or controller.", ["receiver present/absent", "swiped/manual/nonapplicable card contexts", "Element 113"], "high"),
    ("HEALTHCARE_SERVICE_SCOPE", "10.1.6", "100", "COMPANION_DOMAIN", "Healthcare auto-substantiation supports the documented credit and PIN-debit issuers", "The service identifies and substantiates medical and OTC purchases for FSA and HRA accounts using the supported issuer/card contexts.", "The Healthcare Auto-Substantiation Service provided by credit card issuers (Amex®, Discover® Network, MasterCard®, Visa®) and PIN debit card issuers, is supported by these specifications.", ["FSA/HRA", "credit and PIN-debit issuers", "medical/OTC purchase"], "high"),
    ("HEALTHCARE_BENEFIT_BIN", "10.1.6.1.1", "100", "COMPANION_DOMAIN", "Eligible healthcare benefit cards are identified by stored nine-digit BINs", "The POS stores eligible benefit-card BIN information for use when the card is swiped.", "Eligible benefit cards are identified by a 9-digit BIN.", ["eligible benefit card", "nine-digit BIN", "card swipe"], "high"),
    ("HEALTHCARE_PARTIAL_SPLIT_TENDER", "10.1.6.1.2,10.1.6.1.3", "100", "COMPANION_DOMAIN", "Healthcare partial approval and split tender handle qualified and nonqualified amounts", "When partial approval is unsupported, the request is fully approved or declined; when partial approval returns, the POS prompts for remaining balance using another payment method for nonqualified items.", "A Split Tender transaction results when a Partial Approval is returned to the POS in the transaction response.", ["FSA/HRA", "partial approval supported/unsupported", "qualified/nonqualified items", "remaining balance tender"], "high"),
    ("HEALTHCARE_QUALIFIED_PRODUCTS", "10.1.6.1.4", "102", "COMPANION_DOMAIN", "Qualified healthcare product totals follow the documented product-code table", "FSA/HRA qualified medical categories and associated Product Codes follow the Section 10.1.6.1.4 table, including required Total QHP Amount code 894 and optional category codes.", "The following table lists supported product categories, whether or not they are required in messages, their Product Codes, and brief descriptions:", ["QHP product categories", "code 894 required total", "taxes/shipping/discounts"], "high"),
    ("HEALTHCARE_REQUEST_FIELDS", "10.1.6.2", "100,102,111", "COMPANION_DOMAIN", "Healthcare requests include MSDI, Partial Approval Indicator, and Product Code data", "FSA/HRA transaction requests include Market-Specific Data Indicator in Segment 111, Partial Approval Indicator in Segment 100, and Product Code Segment 102.", "FSA/HRA transaction requests include the following data fields:", ["FSA/HRA request", "MSDI Segment 111", "Partial Approval Indicator Segment 100", "Product Code Segment 102"], "high"),
    ("HEALTHCARE_RESPONSE_STANDARD", "10.1.6.3", "100", "COMPANION_DOMAIN", "Healthcare responses use standard response data without FSA/HRA-specific elements", "The response contains standard response data; this service defines no additional FSA/HRA-specific response elements.", "There are no data elements specific to FSA/HRA transactions.", ["FSA/HRA response", "standard response data"], "medium"),
    ("HEALTHCARE_TRANSACTION_TYPES", "10.1.6.4", "100", "TRANSACTION_TYPE", "Healthcare card processing supports the documented purchase, reversal, return, and timeout types", "FSA/HRA supports Purchase/Capture, Purchase Reversal, Merchandise Return, and Time-out Reversal.", "Purchase/Capture", ["FSA/HRA", "each supported transaction type"], "high"),
    ("HEALTHCARE_PURCHASE_TOTALS", "10.1.6.4.1", "100", "LIFECYCLE", "Approved healthcare purchases update the card-type totals bucket", "An approved FSA/HRA Purchase/Capture requires Partial Approval Indicator and Product Code Segment and adds to the card-type totals count and amount.", "The Partial Approval Indicator and Product Code Data Segment fields are required in a FSA/HRA Purchase/Capture transaction.", ["FSA/HRA Purchase/Capture", "required fields", "approved totals update"], "high"),
    ("HEALTHCARE_PURCHASE_REVERSAL_TOTALS", "10.1.6.4.2", "100", "LIFECYCLE", "Healthcare purchase reversal requires the indicator and reverses totals", "FSA/HRA Purchase Reversal requires Partial Approval Indicator and subtracts approved reversal count and amount from the card-type totals bucket.", "The Partial Approval Indicator is required in a FSA/HRA Purchase Reversal transaction.", ["FSA/HRA Purchase Reversal", "totals decrement"], "high"),
    ("HEALTHCARE_RETURN_TOTALS", "10.1.6.4.3", "100", "LIFECYCLE", "Approved healthcare merchandise returns update totals with the source-defined signs", "An approved Merchandise Return adds to the card-type totals count and subtracts from the amount.", "All approved Merchandise Return transactions are added to the count and subtracted from the amount of the card type totals bucket.", ["FSA/HRA Merchandise Return", "count increment", "amount decrement"], "high"),
    ("HEALTHCARE_TOR_TOTALS", "10.1.6.4.4", "100", "LIFECYCLE", "Healthcare timeout reversal requires the indicator and reverses totals", "FSA/HRA TOR requires Partial Approval Indicator and subtracts approved TOR count and amount from the card-type totals bucket.", "The Partial Approval Indicator is required in a FSA/HRA TOR transaction.", ["FSA/HRA TOR", "totals decrement"], "high"),
    ("HEALTHCARE_RECEIPT_ADDITIONS", "10.1.6.5", "100", "COMPANION_DOMAIN", "Approved healthcare receipt shows the QHP indicator and subtotal", "Approved FSA/HRA receipts print the QHP item indicator and subtotal including taxes and discounts, in addition to general credit-card receipt requirements.", "Indicator that identifies the QHP items on the customer receipt", ["approved FSA/HRA receipt", "QHP indicator", "subtotal incl. taxes/discounts"], "medium"),
    ("CREDIT_RECEIPT_REQUIRED_FIELDS", "10.1.7", "100", "RESPONSE_NETWORK", "Credit-card receipts display the source-required merchant and transaction information", "The receipt contains merchant information, transaction type, card type ID, account number, expiration date handling, transaction date/time, sequence number, product and balance information, approval/decline message, and signature-line handling.", "This section discusses the following information that must print on credit card receipts:", ["POS receipt", "each enumerated receipt item"], "high"),
    ("CREDIT_RECEIPT_MASKING", "10.1.7.4,10.1.7.5", "100", "RESPONSE_NETWORK", "Credit-card receipt masks the account number and suppresses expiration date", "The receipt masks the PAN to its last four digits with Xs for suppressed digits and suppresses the expiration date; manual entry is marked separately by an asterisk.", "The Account Number (Element No. 2) must be truncated—suppressed to its last four digits—on all transaction receipts.", ["all credit-card receipts", "PAN masking", "expiration suppression"], "high"),
    ("CREDIT_RECEIPT_TRANSACTION_FIELDS", "10.1.7.6,10.1.7.7,10.1.7.8", "100", "RESPONSE_NETWORK", "Credit-card receipt prints transaction date, time, and sequence number", "The transaction date, time, and Sequence Number appear on credit-card receipts.", "The date of the transaction prints on all credit card receipts.", ["credit-card receipt", "transaction date", "transaction time", "sequence number"], "medium"),
    ("CREDIT_RECEIPT_PRODUCT_FIELDS", "10.1.7.9", "100", "RESPONSE_NETWORK", "Approved credit receipt shows fuel or nonfuel product details", "Approved transaction receipts show fuel type, quantity, unit price, total fuel amount or nonfuel quantity, unit price, and total product amount.", "The following product information must appear on a receipt for an \"approved\" transaction:", ["approved POS transaction", "fuel/nonfuel product details"], "high"),
    ("CREDIT_RECEIPT_BALANCE_FIELDS", "10.1.7.10", "100", "RESPONSE_NETWORK", "Receipt prints returned balance for partial approval or balance-return purchase", "When the issuer returns available balance, it prints for Partial Approval and Balance Return with Purchase on approved or declined receipts.", "Balance information prints on a credit card receipt for a Partial Approval transaction and a Balance Return with Purchase transaction.", ["issuer returned balance", "partial approval/balance-return purchase", "approved/declined receipt"], "medium"),
    ("CREDIT_RECEIPT_APPROVAL_DECLINE", "10.1.7.11", "100", "RESPONSE_NETWORK", "Credit-card receipt shows approval code or decline message according to device context", "Approved POS receipts include the approval phrase and code; declined POS receipts include a decline message when preprint is used; customer-activated devices display the decline message on screen.", "The approval phrase, including the approval code, must be on a receipt for an \"approved\" transaction that involves a point-of-sale device.", ["approved/declined transaction", "POS preprint", "customer-activated terminal"], "high"),
    ("CREDIT_RECEIPT_SIGNATURE", "10.1.7.12", "100", "RESPONSE_NETWORK", "Credit receipt signature line appears only on merchant copy", "A signature line appears only on the merchant copy of a credit-card transaction receipt.", "A signature line prints only on the merchant’s copy of the credit card transaction receipt.", ["merchant receipt copy", "customer copy"], "medium"),
    ("REGULATORY_CARD_DATA_DISPLAY", "10.1.8.1", "100", "CORE_STRUCTURE", "Credit processing restricts card-read data displayed or stored at point of interaction", "A device must not display/store card-read data other than account number, expiration date, and cardholder name if present; merchants must not store full magnetic-stripe data.", "A device at the point of interaction must not display/store any card-read data except the card’s account number, card’s expiration date, and cardholder’s name, if present.", ["all electronic credit transactions", "display/storage controls"], "high"),
    ("REGULATORY_RESEARCH_STORAGE", "10.1.8.1", "100", "CORE_STRUCTURE", "Permitted research records are limited and securely maintained", "For research, only account number, expiration date, and cardholder name may be recorded in the specified files and must be kept securely; full card-read/discretionary data is prohibited.", "The merchant—or any agent representative thereof—may record only the card’s account number, card’s expiration date, and cardholder’s name on paper, microfiche, or an online authorization file, for research purposes at its site.", ["research-purpose storage", "permitted fields", "secure retention"], "high"),
    ("REGULATORY_CLEARING_SOURCE", "10.1.8.1,10.1.8.2", "100", "CORE_STRUCTURE", "Credit clearing data is not reconstructed from previously captured full track data", "Full card-read data is not stored or reused to produce later authorization/clearing data, except for the explicitly stated TransArmor-Verifone exception.", "Card data provided in a clearing transaction must not be the result of previously captured full card-read data.", ["clearing transaction", "prior full track capture", "TransArmor-Verifone exception"], "high"),
    ("REGULATORY_TRACK_SELECTION", "10.1.8.2", "100", "CORE_STRUCTURE", "Regulatory track selection uses the card-specific default and unaltered fallback", "Track 2 is default when supported, otherwise unaltered Track 1 before manual entry; Amex uses Track 1 by default and unaltered Track 2 fallback.", "The default track that is sent to the host should be Track 2 if the device supports Track 2.", ["all electronic credit transactions", "Amex/non-Amex", "track reader capability"], "high"),
    ("REGULATORY_HOST_CARD_DATA", "10.1.8.2", "100", "CORE_STRUCTURE", "Host card-data format contains only account number and expiration after an equals delimiter", "For the enumerated credit transaction types, the host message uses account number, '=' delimiter, and four-digit YYMM expiration date with no further Card Discretionary Data after the date.", "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM).", ["credit completion", "POS purchase reversal", "TOR", "POS cancellation"], "high"),
    ("REGULATORY_IN_FLIGHT_EXCEPTION", "10.1.8.2", "100", "CORE_STRUCTURE", "In-flight track retention is temporary and offline transactions send full unaltered tracks", "Track 2 may be retained in the In Flight Table only while a transaction is in process and is removed when complete; offline transactions send full unaltered Track 1 and Track 2.", "Track 2 data is kept in this table while the transaction is in process.", ["in-flight state", "completion removes data", "offline transaction"], "high"),
]


def native(path):
    path = Path(os.path.normpath(str(path)))
    if os.name == "nt" and not str(path).startswith("\\\\?\\"):
        return Path("\\\\?\\" + str(path.resolve()))
    return path


def sha256(path):
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def normalized_text(value):
    return re.sub(r"\s+", " ", str(value or "")).strip()


def make_anchor(section, segments, rule):
    return {"specification": "ATL105", "version": "2026-3", "section": section,
            "segment": segments, "rule": rule}


def build(pack_root, output_root):
    pack_root = native(pack_root)
    output_root = native(output_root)
    source_path = pack_root / "docs/specs/extracted_text.txt"
    baseline_path = pack_root / "test-output/test-solution-independent-review/source-derived-requirement-inventory.json"
    baseline = json.loads(baseline_path.read_text(encoding="utf-8-sig"))
    complete_path = pack_root / "test-output/test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json"
    complete = json.loads(complete_path.read_text(encoding="utf-8-sig"))
    source = source_path.read_text(encoding="utf-8-sig")
    if baseline.get("authority") != "INDEPENDENT_TEST_SOLUTION" or baseline.get("aiArtifactsIncluded") is not False:
        raise ValueError("Independent baseline provenance check failed")
    if output_root.exists():
        raise ValueError("Refusing to overwrite existing Section 10.1 draft package")

    existing_rules = complete.get("businessRequirements", [])
    section_pattern = re.compile(r"^10\.1(?:\.\d+)*$")
    direct_existing = []
    for item in existing_rules:
        anchors = item.get("sourceAnchors") or []
        if any(section_pattern.fullmatch(str(anchor.get("section", ""))) for anchor in anchors):
            direct_existing.append(item)
    by_title = {str(item.get("title", "")).casefold(): item for item in existing_rules}
    partial_ids = ["BR-RULE-SEG100-R-020", "BR-RULE-SEG100-R-039", "BR-RULE-SEG100-R-065"]
    existing_by_id = {item.get("id"): item for item in existing_rules}
    for item in direct_existing:
        if not item.get("sourceAnchors"):
            raise ValueError("Existing Section 10.1 chain has no source anchor")

    new_requirements = []
    scenarios = []
    cases = []
    test_data = []
    topic_map = defaultdict(list)
    data_files = []
    for number, rule in enumerate(RULES, start=1):
        rule_key, section, segments, category, title, requirement, quote, fixture_plan, priority = rule
        if normalized_text(quote) not in normalized_text(source):
            raise ValueError(f"Source quote not found verbatim: {rule_key}")
        br_id = f"BR-DRAFT-10-1-{number:03d}"
        scenario_id = f"SCN-DRAFT-10-1-{number:03d}"
        tc_id = f"TC-DRAFT-10-1-{number:03d}"
        td_id = f"TD-DRAFT-10-1-{number:03d}"
        anchor = make_anchor(section, segments, f"section-10-1-{rule_key.lower().replace('_', '-')}")
        anchors = [anchor]
        br = {
            "id": br_id,
            "title": title,
            "requirement": requirement,
            "category": category,
            "applicability": f"ATL105 credit-card processing context in {section}",
            "priority": priority,
            "executionStatus": "REVIEW_REQUIRED",
            "sourceAnchors": anchors,
            "sourceEvidence": [{"section": section, "verbatimExcerpt": quote}],
            "testSolutionDisposition": "DRAFT_REVIEW_REQUIRED_NOT_YET_INDEPENDENT_BASELINE",
        }
        new_requirements.append(br)
        scenario = {
            "id": scenario_id,
            "name": f"Review {rule_key.lower().replace('_', ' ')}",
            "requirementIds": [br_id],
            "status": "REVIEW_REQUIRED",
            "sourceAnchors": anchors,
            "testIntent": requirement,
        }
        scenarios.append(scenario)
        filename = f"test-data/{tc_id}.json"
        request_plan = {
            "reviewOnly": True,
            "testDataDesign": "NOT_A_CONVERTER_READY_ATL105_REQUEST",
            "fixturePlan": fixture_plan,
            "requiredContext": rule_key,
            "sourceSection": section,
            "executionAllowed": False,
        }
        tc = {
            "id": tc_id,
            "scenarioIds": [scenario_id],
            "expectedOutcome": "REVIEW",
            "testDataFile": filename,
            "category": category.lower(),
            "tags": ["section-10.1", "draft-review-required", rule_key.lower()],
            "priority": priority,
            "status": "REVIEW_REQUIRED",
            "sourceAnchors": anchors,
            "reviewObjective": requirement,
        }
        cases.append(tc)
        td = {
            "id": td_id,
            "testCaseIds": [tc_id],
            "expectedValidation": "REVIEW",
            "fileName": filename,
            "readiness": "REVIEW_REQUIRED",
            "sourceAnchors": anchors,
            "payload": {"request": request_plan},
            "dataStatus": "DRAFT_TEST_DATA_DESIGN_NOT_EXECUTABLE",
            "responseOracle": None,
        }
        test_data.append(td)
        topic_map[section].append({"businessRequirementId": br_id, "scenarioId": scenario_id,
                                  "testCaseId": tc_id, "testDataId": td_id, "sourceQuote": quote})
        data_files.append((filename, {"artifact": "ATL105_SECTION_10_1_TEST_DATA_DESIGN",
                                      "testCaseId": tc_id, "testDataId": td_id,
                                      "status": "DRAFT_TEST_DATA_DESIGN_NOT_EXECUTABLE",
                                      "payload": td["payload"], "sourceAnchors": anchors}))

    reused_references = []
    for existing in direct_existing:
        reused_references.append({"testRequirementId": existing["id"], "title": existing.get("title"),
                                  "status": existing.get("status", "UNSET"),
                                  "sourceAnchors": existing.get("sourceAnchors", []),
                                  "sourcePackage": "test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json",
                                  "reuseDisposition": "EXISTING_DRAFT_CHAIN_REUSE_NOT_PROMOTED"})
    for existing_id in partial_ids:
        existing = existing_by_id.get(existing_id)
        if existing:
            reused_references.append({"testRequirementId": existing_id, "title": existing.get("title"),
                                      "status": existing.get("status", "UNSET"),
                                      "sourceAnchors": existing.get("sourceAnchors", []),
                                      "sourcePackage": "test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json",
                                      "reuseDisposition": "RELATED_PARTIAL_APPROVAL_DRAFT_CHAIN_REUSE_NOT_PROMOTED"})

    package = {
        "manifest": {
            "packageId": "ATL105-SECTION-10-1-CREDIT-CARD-DRAFT-REVIEW-002",
            "specification": "ATL105",
            "specificationVersion": "2026-3",
            "artifactContractVersion": "2",
            "strictExecutionContract": False,
            "authority": "INDEPENDENT_TEST_SOLUTION_DRAFT",
            "status": "DRAFT_REVIEW_REQUIRED",
            "aiArtifactsIncluded": False,
            "scope": "Section 10.1 Credit Card Processing Requirements, subsections 10.1.1-10.1.8",
            "executionCertified": False,
            "coverageCredit": 0,
            "independentBaselineRuleCount": baseline["ruleCount"],
            "newDraftRulesInBaseline": False,
            "sourceTextSha256": sha256(source_path),
            "preexistingIndependentBaselineSha256": sha256(baseline_path),
            "preexistingCompleteChainPackageSha256": sha256(complete_path),
            "reviewPolicy": {
                "allStatuses": "REVIEW_REQUIRED",
                "dataPolicy": "TD files are fixture-design placeholders, not converter-ready requests; no response/host oracle is invented.",
                "matchPolicy": "Existing rules are references only. No AI/Test semantic crosswalk or coverage credit is asserted.",
                "promotionGate": "Authorized Test Solution review must approve BR wording/source applicability and complete real TD/request/response oracles before execution readiness.",
            },
        },
        "businessRequirements": new_requirements,
        "testScenarios": scenarios,
        "testCases": cases,
        "testData": test_data,
    }
    assessment = {
        "artifact": "ATL105-SECTION-10-1-INDEPENDENT-BR-CHAIN-COVERAGE-ASSESSMENT",
        "sourceSection": "10.1",
        "status": "DRAFT_REVIEW_REQUIRED",
        "newDraftBRCount": len(new_requirements),
        "newDraftScenarioCount": len(scenarios),
        "newDraftTestCaseCount": len(cases),
        "newDraftTestDataDesignCount": len(test_data),
        "reusedExistingRuleReferenceCount": len(reused_references),
        "reusedExistingRules": reused_references,
        "topicChains": dict(topic_map),
        "confirmedSemanticMatches": 0,
        "coverageCredit": 0,
        "executionCertified": False,
        "independentBaselineUnchanged": True,
        "manualReviewRequired": True,
        "notes": [
            "The official 601-rule independent baseline is not modified by this draft supplement.",
            "Existing complete-package chains are reused by reference, not copied or silently promoted.",
            "Draft TD payloads are design placeholders; they are not valid financial requests and must not be executed.",
            "Section 10.1.7 card-type mapping BRs already exist as draft chains and are listed in reusedExistingRules.",
        ],
    }
    if output_root.exists():
        raise ValueError("Refusing to overwrite existing Section 10.1 supplement")
    output_root.mkdir(parents=True)
    (output_root / "section-10-1-br-ts-tc-td-draft-package.json").write_text(
        json.dumps(package, indent=2, ensure_ascii=True), encoding="utf-8")
    (output_root / "section-10-1-coverage-assessment.json").write_text(
        json.dumps(assessment, indent=2, ensure_ascii=True), encoding="utf-8")
    data_dir = output_root / "test-data"
    data_dir.mkdir()
    for relative, record in data_files:
        (output_root / relative).write_text(json.dumps(record, indent=2, ensure_ascii=True), encoding="utf-8")
    (output_root / "README.md").write_text(markdown(package, assessment), encoding="utf-8")
    validate(package, output_root, source, len(data_files))
    print(json.dumps({"output": str(output_root), "draftBRs": len(new_requirements),
                      "draftTSs": len(scenarios), "draftTCs": len(cases), "draftTDDesigns": len(test_data),
                      "existingReferences": len(reused_references), "officialBaselineChanged": False,
                      "coverageCredit": 0, "executionCertified": False}, indent=2))


def validate(package, output_root, source, expected_count):
    business_requirements = {item["id"]: item for item in package["businessRequirements"]}
    scenarios = {item["id"]: item for item in package["testScenarios"]}
    cases = {item["id"]: item for item in package["testCases"]}
    data = {item["id"]: item for item in package["testData"]}
    assert len(business_requirements) == expected_count == len(scenarios) == len(cases) == len(data)
    assert all(item["executionStatus"] == "REVIEW_REQUIRED" for item in business_requirements.values())
    assert all(item["status"] == "REVIEW_REQUIRED" for item in scenarios.values())
    assert all(item["status"] == "REVIEW_REQUIRED" and item["expectedOutcome"] == "REVIEW" for item in cases.values())
    assert all(item["readiness"] == "REVIEW_REQUIRED" and item["expectedValidation"] == "REVIEW" for item in data.values())
    for br_id, br in business_requirements.items():
        assert any(s["requirementIds"] == [br_id] for s in scenarios.values())
        scenario_ids = {s["id"] for s in scenarios.values() if br_id in s["requirementIds"]}
        case_ids = {c["id"] for c in cases.values() if set(c["scenarioIds"]) & scenario_ids}
        assert case_ids
        assert all(sum(case_id in td["testCaseIds"] for td in data.values()) == 1 for case_id in case_ids)
        for anchor in br["sourceAnchors"]:
            assert anchor["specification"] == "ATL105" and anchor["version"] == "2026-3"
    for item in data.values():
        physical = output_root / item["fileName"]
        assert physical.is_file()
        record = json.loads(physical.read_text(encoding="utf-8"))
        assert record["status"] == "DRAFT_TEST_DATA_DESIGN_NOT_EXECUTABLE"
        assert record["payload"] == item["payload"]
    assert all(normalized_text(rule["sourceQuote"]) in normalized_text(source) for items in json.loads((output_root / "section-10-1-coverage-assessment.json").read_text(encoding="utf-8"))["topicChains"].values() for rule in items)
    assert package["manifest"]["executionCertified"] is False and package["manifest"]["coverageCredit"] == 0


def markdown(package, assessment):
    rows = ["# ATL105 Section 10.1 Credit Card Processing - Independent Draft BR/TS/TC/TD Supplement", "",
            "Status: DRAFT_REVIEW_REQUIRED. This is an independent Test Solution draft derived from ATL105 Section 10.1, not from AI BR wording. No coverage credit or execution certification is issued.", "",
            f"New draft chains: {assessment['newDraftBRCount']} BR / {assessment['newDraftScenarioCount']} TS / {assessment['newDraftTestCaseCount']} TC / {assessment['newDraftTestDataDesignCount']} TD design records. Existing independent chains reused by reference: {assessment['reusedExistingRuleReferenceCount']}.", "",
            "TD artifacts are non-executable fixture-design placeholders. Each requires valid request payload, applicable fixture/context, expected response oracle, and SME/Test Solution review before execution.", "",
            "## Draft Rules", "", "| BR ID | Section | Topic | Requirement |", "|---|---|---|---|"]
    for br in package["businessRequirements"]:
        anchor = br["sourceAnchors"][0]
        rows.append(f"| {br['id']} | {anchor['section']} | {br['title']} | {br['requirement']} |")
    rows.extend(["", "## Existing Rules Reused by Reference", "", "| Existing Test BR | Disposition |", "|---|---|"])
    for item in assessment["reusedExistingRules"]:
        rows.append(f"| {item['testRequirementId']} | {item['reuseDisposition']} |")
    rows.extend(["", "## Promotion Gates", "", "1. Review each source quote, BR atomicity, transaction/card/lifecycle applicability, and interactions with related rules.",
                 "2. Resolve open source conflicts and card-network context with the authorized SME.",
                 "3. Complete converter-ready positive/negative request fixtures and authoritative expected response/receipt/storage oracles.",
                 "4. Execute independently, validate mutation isolation and full BR -> TS -> TC -> TD trace, then approve into the independent catalog.",
                 "5. Recompute coverage only after promoted Test Solution rules and confirmed Run4 crosswalk decisions are recorded.",
                 "", "No `EXECUTION_READY`, `EXECUTABLE`, `CONFIRMED`, or coverage-credit claim is present in this supplement."])
    return "\n".join(rows) + "\n"


def self_test():
    assert len(RULES) == 46
    ids = [row[0] for row in RULES]
    assert len(ids) == len(set(ids))
    assert all(row[3] in {"TRANSACTION_TYPE", "COMPANION_DOMAIN", "LIFECYCLE", "RESPONSE_NETWORK", "CORE_STRUCTURE"} for row in RULES)
    assert normalized_text("Track 1 data must be present\ninside the Encryption Block") == "Track 1 data must be present inside the Encryption Block"
    print("PASS: 46 source-derived Section 10.1 draft rules have unique IDs, valid BR categories, and line-wrap-tolerant exact quote checks")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--pack-root", type=Path)
    parser.add_argument("--output-directory", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    if not args.pack_root or not args.output_directory:
        parser.error("--pack-root and --output-directory are required")
    build(args.pack_root, args.output_directory)


if __name__ == "__main__":
    main()