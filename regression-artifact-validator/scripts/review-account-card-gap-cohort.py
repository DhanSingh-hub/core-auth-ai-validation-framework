"""Review the frozen 283 Account/Card gap BRs without granting coverage credit."""

import argparse
import collections
import csv
import gzip
import importlib.util
import json
import os
from pathlib import Path


SCRIPT = Path(__file__).resolve()
ROOT = SCRIPT.parents[1]
REVIEW = ROOT / "specifications/ATL105/test-output/ai-solution-independent-review"
COHORT = REVIEW / "2026-10-15-account-card-ai-test-br-review-v9"
QUEUE = REVIEW / "2026-10-16-all-ai-br-chain-test-br-review-v2"
PACKAGE = ROOT / "specifications/ATL105/test-output/test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json"
SPEC = importlib.util.spec_from_file_location("account_card_helpers", SCRIPT.with_name("build-account-card-br-crossmatch-review.py"))
assert SPEC is not None and SPEC.loader is not None
HELPERS = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(HELPERS)

REVIEW_GROUPS = [
	("1179", "SEG101-R-002", "CLAUSE_MATCH_PROPOSED", "Both require Segment 101 for fleet-card financial requests; enhanced-fleet exceptions still require source/context review."),
	("1217 1914", "SEG104-R-006", "CLAUSE_MATCH_PROPOSED", "Same Segment 104 maximum of 86; the Test BR establishes the maximum, not every lower-bound value or wire encoding."),
	("1231 1916", "SEG108-R-005", "CLAUSE_MATCH_PROPOSED", "Same Segment 108 maximum of 142; serialization and minimum-length claims remain separate."),
	("1272", "SEG118-R-001", "CLAUSE_MATCH_PROPOSED", "Same host-to-device Dynamic Card Table, Custom Receipt Text and Host Discount categories."),
	("1282", "SEG118-R-014", "CLAUSE_MATCH_PROPOSED", "Same host-sourced Card Table Load Version echoed in subsequent block requests."),
	("1283", "SEG118-R-015", "CLAUSE_MATCH_PROPOSED", "Same host-sourced Card Table Type echoed in subsequent block requests."),
	("1285 2244 6835", "SEG118-R-013", "CLAUSE_MATCH_PROPOSED", "Test explicitly describes concatenated five-digit versions and the 99999 no-card-table sentinel; each AI BR covers one clause, not the full Test BR."),
	("1294", "BR-TX9-PROMPT-902;SEG118-R-020", "CLAUSE_MATCH_PROPOSED", "Same Prompt Code 902 Dynamic Card Table meaning and response content; load lifecycle is not executed."),
	("1324", "SEG119-R-027", "CLAUSE_MATCH_PROPOSED", "Same fields 17-19 repeated up to 20 card types in documented order; Test R-039 retains the competing 18-20 separator-note numbering."),
	("1325", "SEG119-R-028", "CLAUSE_MATCH_PROPOSED", "Same approved-response inclusion of card types 1-15; actual complete responses still need independent validation."),
	("1326", "SEG119-R-029", "CLAUSE_MATCH_PROPOSED", "Same card types 16-20 conditional on data occurring for the card type."),
	("1347 2264 2265 6847", "SEG130-R-007", "CLAUSE_MATCH_PROPOSED", "Shared explicit EMV Chip Data Length presence/range clause, 000-999. A range-only AI BR does not cover every Test obligation."),
	("1929", "SEG130-R-004", "CLAUSE_MATCH_PROPOSED", "Same Segment 130 maximum 3043; minimum length and encoded-byte accounting are not established by this match."),
	("1405 1408", "SEG145-R-003", "CLAUSE_MATCH_PROPOSED", "Test explicitly limits Voyager and MasterCard Enhanced Fleet EMV to the Prompt Table sub-segment; AI covers one named authorizer clause."),
	("1409", "SEG145-R-004", "CLAUSE_MATCH_PROPOSED", "Same specification-era June 2026 production/development limitation; no current production activation is inferred."),
	("1505", "SEG155-R-004", "CLAUSE_MATCH_PROPOSED", "Same five Card Status codes and meanings A/E/Q/C/U."),
	("1620 1621 1622 2254 6832", "SEG118-R-022", "CLAUSE_MATCH_PROPOSED", "Test explicitly places BUYPASS Card Type and BIN range endpoints in the Prompt 904 response; AI covers a named field clause only."),
	("1623 6834", "SEG118-R-020", "CLAUSE_MATCH_PROPOSED", "Same Card Table Data in Prompt 902 responses."),
	("6836", "SEG118-R-014", "CLAUSE_MATCH_PROPOSED", "Shared required Card Table Load Version field; host echo and complete version-vector behavior are additional Test obligations."),
	("1715 1849 1858 2410 2434", "BR-SEG100-APPE-PROMPT-COMPOSITION;BR-SEG100-E78-001;BR-SEG100-E78-002", "CLAUSE_MATCH_PROPOSED", "Same financial Prompt Code construction from transaction type and card type; each allowlist and exact position must also be checked."),
	("1716 1850 1853 1859 2413", "BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT", "CLAUSE_MATCH_PROPOSED", "Same special three-character Prompt Code without a financial transaction-type prefix."),
	("2155 6809", "SEG108-R-008", "CLAUSE_MATCH_PROPOSED", "Same required Loyalty Program ID clause; Test also constrains numeric maximum length 6."),
	("2266 6848", "SEG130-R-008", "CLAUSE_MATCH_PROPOSED", "Shared required EMV Chip Data clause for the applicable EMV request context; Test adds TLV/framing constraints and reversal exceptions must remain visible."),
	("3086 3087", "SEG130-R-020;BR-SEG100-APPT-EMV-TABLE-DATA", "CLAUSE_MATCH_PROPOSED", "Same EMVYES/EMVNOT capability meaning in EMV-request Table 001; response echo is an additional Test obligation."),
	("882", "BR-TX9-PROMPT-996", "CLAUSE_MATCH_PROPOSED", "Same special Prompt Code 996 proprietary electronic-mail retrieval family; exact request/response lifecycle remains unexecuted."),
	("1024", "SEG108-R-002;SEG108-R-025;SEG100-R-001", "CONFLICT_REVIEW_REQUIRED", "Section 11.2.1 itself says Segment 100 is the only required loyalty segment but also marks Segment 108 R. AI reflects the sentence and Test reflects the table. Preserve this source-internal contradiction for SME review; neither wording resolves it alone."),
	("1232", "SEG108-R-016", "CONFLICT_REVIEW_REQUIRED", "Section 12.7 explicitly reserves Element 146 Expiration Date for future use, supporting AI. Test adds MMYY/default-1249 behavior not stated in that field table. Authoritative source controls; Test revision or a separately applicable source is required, not automatic AI rejection."),
	("1021", "SEG100-R-001;SEG108-R-025", "PARTIAL_SCOPE_OVERLAP", "Test supports Segment 100 in loyalty requests; exact Data Section 2 placement is not stated in these Test titles."),
	("1022 1794", "SEG100-R-013;SEG100-R-007", "PARTIAL_SCOPE_OVERLAP", "Test covers format identity and segment count, not the entire named message-family/field-position assertion."),
	("1047 1225 1226", "SEG105-R-015", "PARTIAL_SCOPE_OVERLAP", "Aggregate totals fields are related, but the 18-card repetition, order and data-occurs condition are not specified by this Test BR."),
	("1050 1227 1327 438", "SEG105-R-015;SEG119-R-024", "PARTIAL_SCOPE_OVERLAP", "Card Label fields are covered, but label-instead-of-field-number display behavior or journal-derived local totals is not established."),
	("1124", "SEG100-R-005", "PARTIAL_SCOPE_OVERLAP", "An allowed-segment list is not equivalent to requiring every applicable companion; permission and obligation differ."),
	("1126 1133 1348", "SEG130-R-001;SEG130-R-017", "PARTIAL_SCOPE_OVERLAP", "Test requires Segment 130 for authorization-class EMV requests and waives reversal data; AI all-EMV wording needs scope review."),
	("1136", "SEG130-R-018", "PARTIAL_SCOPE_OVERLAP", "Same five-slot EMV Data Section 3 context; AI one-or-more wording does not establish the Test companion maximum."),
	("1130 997", "SEG100-R-052", "PARTIAL_SCOPE_OVERLAP", "Same purchase-card companion topic, but AI only-when and Test requiring-when-needed implications are not logically identical."),
	("1156 1356", "SEG132-R-006;SEG100-R-017", "PARTIAL_SCOPE_OVERLAP", "Test explicitly states +* in CAPK-load Terminal Identifier context, not necessarily every EMV request context asserted by AI."),
	("1233 2157", "SEG108-R-017", "PARTIAL_SCOPE_OVERLAP", "Tender field/allowed codes are related; two-card behavior when tender is not CS is not covered by this Test title."),
	("1312 2240", "SEG118-R-022", "PARTIAL_SCOPE_OVERLAP", "Test defines Host Discount fields, not the full BIN matching or authorization/prepay discount algorithm."),
	("1495 1500 1501 1503 1504", "SEG155-R-003", "PARTIAL_SCOPE_OVERLAP", "Test aggregates Y or Y/I gating and Visa/MasterCard applicability across sub-tables. Exact per-field gating needs source review; do not over-credit the aggregate."),
	("1537", "SEG156-R-004", "PARTIAL_SCOPE_OVERLAP", "Same hhmmss bounds for charge times; local-time semantics and individual table applicability remain additional claims."),
	("1624 2243 6833", "SEG118-R-015;SEG118-R-020", "PARTIAL_SCOPE_OVERLAP", "Table type and Prompt 902 data are supported separately, not the full update/create/replace behavior or conditional field rule."),
	("1625 1632 285 2396 762", "BR-SEG100-E78-002;BR-SEG100-APPE-PROMPT-COMPOSITION", "PARTIAL_SCOPE_OVERLAP", "Card Type contributes to Prompt Code, but the AI general/eWIC statement and financial Test scope are not fully equivalent."),
	("1637 1638 1651 1652 1663 1664 1666 1667 1668 1669 1670 3059 784 808", "SEG100-R-041", "PARTIAL_SCOPE_OVERLAP", "Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics."),
	("1653 1654 1656 1658 1659 1697 1698 6743 572 676 700 837", "SEG100-R-034;SEG100-R-042", "PARTIAL_SCOPE_OVERLAP", "Entry-method and discretionary-data topics overlap, but sentinel parsing, exact track grammar/limits or authorizer-specific full-track requirements are not enumerated."),
	("1660 1700", "SEG155-R-003;SEG100-R-041;SEG100-R-042", "PARTIAL_SCOPE_OVERLAP", "Updater response fields and account data are covered separately; permission to reuse new or original values on follow-ups is not established."),
	("1699 3195", "SEG100-R-035;BR-SEG100-ANN-O", "PARTIAL_SCOPE_OVERLAP", "TransArmor account representation is related; exact TAP/EDATA layout and mandatory expiration behavior are not established by broad representation rules."),
	("1703 578 579 576 649 651", "SEG100-R-042", "PARTIAL_SCOPE_OVERLAP", "Discretionary-data conditional usage is broad; exact MMYY/YYMM format, delimiter and field ordering remain unproven by this Test BR."),
	("1712 1718", "SEG105-R-015;SEG119-R-024;SEG119-R-025", "PARTIAL_SCOPE_OVERLAP", "Totals labels/count fields overlap; exact label-to-network mapping or numeric count range is not stated."),
	("1851", "SEG100-R-018;SEG100-R-033", "PARTIAL_SCOPE_OVERLAP", "Prompt context matches broadly; separate financial/special allowlists and exact permitted combinations still need review."),
	("1856 1857 2949", "SEG100-R-037;SEG100-R-033", "PARTIAL_SCOPE_OVERLAP", "Lifecycle/context rules are related, but returned D/S routing and Table 21 triggers are not stated by these Test BRs."),
	("1989", "BR-TX-M-TRANSACTION-TYPE-M-BALANCE-MERGE-TWO-CARDS", "PARTIAL_SCOPE_OVERLAP", "Balance merge zeroes one card; the account number placement in Variable Information is an additional AI claim."),
	("2008 2010 2016 2025 2037 2040 2066 892 911 927 942 950 962 3175 2631 2632", "SEG111-R-003;SEG111-R-004", "PARTIAL_SCOPE_OVERLAP", "Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit."),
	("2084 2085 2102 2885 2887", "SEG112-R-007;SEG112-R-008;SEG112-R-009", "PARTIAL_SCOPE_OVERLAP", "Response indicator/length/value structure overlaps; the named code meaning and exact fixed length require a table-specific Test BR."),
	("2143 498", "SEG100-R-020;SEG100-R-065", "PARTIAL_SCOPE_OVERLAP", "Allowed indicator and approved amount do not establish mandatory partial-authorization support across brands/card-present contexts."),
	("2156 6810", "SEG108-R-009", "PARTIAL_SCOPE_OVERLAP", "AI requires Loyalty Account Number on all loyalty transactions; Test constrains it only when populated and permits alternate identity context."),
	("2252 6840", "SEG118-R-012", "PARTIAL_SCOPE_OVERLAP", "Test has a specific pending-code enumeration; generic valid-transaction/card-code wording does not prove that exact allowlist."),
	("2259", "SEG130-R-005;SEG130-R-015", "PARTIAL_SCOPE_OVERLAP", "Checksum identification/response echo overlap; persistence across every later request is an additional temporal requirement."),
	("2261 2262 2263 6846", "SEG130-R-006", "PARTIAL_SCOPE_OVERLAP", "Card Sequence field is shared; product trigger, absent-5F34 three-space encoding and 000-099 range are not explicit in this Test BR."),
	("2267", "SEG130-R-009;BR-SEG100-APPR-CROSS-FIELD", "PARTIAL_SCOPE_OVERLAP", "Transaction cross-field consistency does not prove equality to independently captured card-interface data."),
	("2356", "SEG145-R-004", "PARTIAL_SCOPE_OVERLAP", "June 2026 rollout limitation overlaps, but the AI specifically scopes Extended Unit of Measure rather than the full enhanced-fleet feature."),
	("2395 2436", "BR-SEG100-APPE-FINANCIAL-CARD-TYPES;BR-SEG100-APPE-SPECIAL-SEPARATION", "PARTIAL_SCOPE_OVERLAP", "Financial and special code families are separated; AI ranges/combinations need exact allowlist comparison, not a generic range assumption."),
	("2400 2408 2409", "BR-SEG100-APPE-TABLE-LOAD-FAMILY;SEGDL1-R-009", "PARTIAL_SCOPE_OVERLAP", "Test recognizes Table Load feature codes, but the AI-specific 162/148/165 device behavior is not established by code recognition alone."),
	("2860 2863 2867 2868 524", "BR-SEG100-ANN-J", "PARTIAL_SCOPE_OVERLAP", "POS entry-mode appendix/lifecycle topic overlaps; exact three-digit positions, Discover prohibitions and 90-to-02 exception need explicit source/Test assertions."),
	("2862", "SEG100-R-034;SEG100-R-042", "PARTIAL_SCOPE_OVERLAP", "Account/track representation overlaps; void/completion-only track restrictions are not fully captured by generic field rules."),
	("2946 2947 783 858", "SEG108-R-016", "PARTIAL_SCOPE_OVERLAP", "MMYY/default 1249 is explicit for loyalty Segment 108, but gift/other response contexts and YYMM alternatives must not be conflated."),
	("3223 1173 1174", "SEG155-R-003;SEG100-R-041;SEG100-R-042", "PARTIAL_SCOPE_OVERLAP", "Reference-only Account Updater guidance is related, not an atomic rule equivalent to a specific field constraint."),
	("486 713 782 856 977 704", "SEG100-R-034", "PARTIAL_SCOPE_OVERLAP", "Entry method agrees broadly; retry count, manual expiration prompt and card-family conditions need specific tests."),
	("712", "SEG100-R-049;SEG100-R-041", "PARTIAL_SCOPE_OVERLAP", "Approval/account fields overlap, but electronic voucher prompting and Voucher Number are additional obligations."),
	("859 861 863 864 865 868", "SEG108-R-014;SEG108-R-015;SEG108-R-009", "PARTIAL_SCOPE_OVERLAP", "Street/phone/account fields exist, but card-unavailable substitution and transaction-specific behavior are not established by independent field-format rules."),
	("982 983", "SEG130-R-019", "PARTIAL_SCOPE_OVERLAP", "Test excludes Segment 130 for fallback/MSR modes 80/90; exact device/chip trigger and transmitted mode selection remain additional AI clauses."),
]


def curated_reviews():
	result = {}
	for suffixes, targets, disposition, rationale in REVIEW_GROUPS:
		for suffix in suffixes.split():
			identifier = "REQ-SRC-ATL105-PDF-001:" + suffix
			if identifier in result:
				raise ValueError("Duplicate curated review: " + identifier)
			result[identifier] = (targets.split(";"), disposition, rationale)
	return result


def package_id(identifier, business_requirements):
	if identifier in business_requirements:
		return identifier
	alternate = "BR-RULE-" + identifier
	if alternate in business_requirements:
		return alternate
	raise ValueError("Candidate has no Test package record: " + identifier)


def read_lines(path):
	native = str(path.resolve())
	if os.name == "nt":
		native = chr(92) * 2 + "?" + chr(92) + native
	opener = gzip.open if path.suffix == ".gz" else open
	with opener(native, "rt", encoding="utf-8") as stream:
		for line in stream:
			if line.strip():
				yield json.loads(line)


def native_path(path):
	resolved = str(Path(path).resolve())
	return Path(chr(92) * 2 + "?" + chr(92) + resolved) if os.name == "nt" else Path(resolved)


def verify_frozen_artifacts(folder, names):
	manifest = HELPERS.read_json(folder / "manifest.json")
	for name in names:
		expected = manifest["artifacts"][name]
		if HELPERS.sha256(native_path(folder / name)) != expected:
			raise ValueError("Stale frozen input: " + name)


def source_excerpts(path):
	lines = path.read_text(encoding="utf-8-sig").splitlines()
	subjects = [
		("LOYALTY_REQUEST_HEADERS", "The Loyalty Card Transaction Request contains three data sections:", 0, 18),
		("LOYALTY_REQUIRED_CONTRADICTION", "This segment is the only segment", 5, 26),
		("LOYALTY_EXPIRATION_RESERVED", "11 146 Expiration", 0, 6),
		("TOTALS_CARD_TYPE_CONDITIONS", "Field Nos. 17", 0, 5),
		("ACCOUNT_UPDATER_STATUS", "Account Updater Card Status", 0, 24),
	]
	result = []
	for identifier, phrase, before, after in subjects:
		candidates = [index for index, line in enumerate(lines) if phrase in line]
		if identifier == "LOYALTY_REQUIRED_CONTRADICTION":
			candidates = [index for index in candidates if "loyalty card transactions" in " ".join(lines[index:index + 3]).lower()]
		if identifier == "ACCOUNT_UPDATER_STATUS":
			candidates = [index for index in candidates if index > 16000]
		if not candidates:
			raise ValueError("Source evidence anchor missing: " + identifier)
		index = candidates[0]
		start, end = max(0, index - before), min(len(lines), index + after)
		result.append({"evidenceId": identifier, "source": str(path), "startLine": start + 1, "endLine": end,
					   "quote": "\n".join(lines[start:end]), "interpretationStatus": "REVIEW_REQUIRED"})
	return result


def unresolved_reason(statement):
	lowered = statement.lower()
	if any(term in lowered for term in ("receipt", "journal", "display/store", "printed")):
		return "No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required."
	if "expiration" in lowered:
		return "Exact card-family expiration validation/prompt/default behavior is not established by generic account or loyalty format rules. Resolve request context and independently expected behavior."
	if any(term in lowered for term in ("table", "indicator", "code", "tag", "field control")):
		return "Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient."
	return "No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage."


def case_evidence(case, delivery, hash_cache):
	payloads = []
	state_counts = collections.Counter()
	for payload in case.get("payloadObservations", []):
		relative = payload["path"]
		digest = payload.get("sha256")
		if relative not in hash_cache:
			source = native_path(delivery / relative)
			hash_cache[relative] = HELPERS.sha256(source) if source.is_file() else None
		observed_digest = hash_cache[relative]
		if not digest:
			state = "NO_FROZEN_HASH_REFERENCE_UNBOUND"
		elif observed_digest is None:
			state = "CURRENT_FILE_MISSING_REVIEW_REQUIRED"
		elif digest != observed_digest:
			state = "CURRENT_HASH_DISAGREEMENT_REVIEW_REQUIRED"
		else:
			state = "CURRENT_HASH_MATCH"
		observations = [{key: observation.get(key) for key in ("field", "segment", "element", "state")}
						for observation in payload.get("observations", [])]
		state_counts.update(observation.get("state") or "NOT_DECLARED" for observation in observations)
		payloads.append({"path": relative, "frozenSha256": digest, "currentSha256": observed_digest,
						 "currentHashState": state, "priorScreenState": payload.get("state"), "fieldObservations": observations})
	metadata = case.get("metadata") or {}
	return {"caseId": case["caseId"], "scenarioId": metadata.get("scenarioId"), "scenarioType": metadata.get("scenarioType"),
			"negativeClass": metadata.get("negativeClass"), "expectedResponsePresent": metadata.get("expectedResponsePresent"),
			"fieldStateCounts": dict(state_counts), "payloadEvidence": payloads,
			"semanticStatus": "NOT_ASSESSED", "executionStatus": "NOT_EXECUTED"}


def self_test():
	requirements = {"BR-RULE-SEG130-R-007": {}, "BR-SEG100-E2-001": {}}
	assert package_id("SEG130-R-007", requirements) == "BR-RULE-SEG130-R-007"
	assert package_id("BR-SEG100-E2-001", requirements) == "BR-SEG100-E2-001"
	try:
		package_id("MISSING", requirements)
	except ValueError:
		pass
	else:
		raise AssertionError("Unknown Test BR must not silently lose its evidence")
	reviews = curated_reviews()
	assert reviews["REQ-SRC-ATL105-PDF-001:1325"][1] == "CLAUSE_MATCH_PROPOSED"
	assert reviews["REQ-SRC-ATL105-PDF-001:1024"][1] == "CONFLICT_REVIEW_REQUIRED"
	assert reviews["REQ-SRC-ATL105-PDF-001:2156"][1] == "PARTIAL_SCOPE_OVERLAP"
	assert "receipt/storage" in unresolved_reason("Sequence Number appears on receipts")
	reserved = source_excerpts(ROOT / "specifications/ATL105/docs/specs/extracted_text.txt")
	assert "reserved for" in next(row["quote"] for row in reserved if row["evidenceId"] == "LOYALTY_EXPIRATION_RESERVED")
	contradiction = next(row["quote"] for row in reserved if row["evidenceId"] == "LOYALTY_REQUIRED_CONTRADICTION")
	assert "loyalty card transactions" in contradiction and "4 108" in contradiction
	print("PASS: catalog aliases, authored IDs, and missing-record guard")


def run(output):
	if output.exists():
		raise ValueError("Refusing to overwrite an existing report")
	verify_frozen_artifacts(QUEUE, ["all-ai-br-review-register.csv", "ai-test-br-ranked-review-leads.csv", "ai-br-chain-evidence.jsonl.gz"])
	verify_frozen_artifacts(QUEUE / "automated-chain-screen-v2", ["all-br-chain-screen.csv", "case-field-observations.jsonl.gz"])
	cohort = HELPERS.csv_rows(COHORT / "ai-account-card-complete-chain-no-candidate.csv")
	identifiers = {row["aiBrId"] for row in cohort}
	assert len(cohort) == len(identifiers) == 283
	register = {row["aiBrId"]: row for row in HELPERS.csv_rows(QUEUE / "all-ai-br-review-register.csv") if row["aiBrId"] in identifiers}
	assert set(register) == identifiers
	package = HELPERS.read_json(PACKAGE)
	assert package["manifest"]["authority"] == "INDEPENDENT_TEST_SOLUTION"
	assert package["manifest"]["aiArtifactsIncluded"] is False
	requirements = {row["id"]: row for row in package["businessRequirements"]}
	assert len(requirements) == len(package["businessRequirements"]) == 1330
	queue_summary = HELPERS.read_json(QUEUE / "summary.json")
	if queue_summary["inputSha256"][str(PACKAGE)] != HELPERS.sha256(PACKAGE):
		raise ValueError("Current Test package differs from the frozen candidate-search package")
	reviews = curated_reviews()
	assert set(reviews).issubset(identifiers)
	pairs = []
	leads = collections.defaultdict(list)
	with (QUEUE / "ai-test-br-ranked-review-leads.csv").open(encoding="utf-8-sig", newline="") as stream:
		for row in csv.DictReader(stream):
			if row["aiBrId"] not in identifiers:
				continue
			target_id = package_id(row["testBrId"], requirements)
			target = requirements[target_id]
			enriched = dict(row, testPackageBrId=target_id, testPackageStatus=target.get("status") or "NOT_DECLARED",
							testSourceAnchors=json.dumps(target.get("sourceAnchors") or [], ensure_ascii=True))
			pairs.append(enriched)
			leads[row["aiBrId"]].append(enriched)
	for rows in leads.values():
		rows.sort(key=lambda row: int(row["candidateRank"]))
	screen_path = QUEUE / "automated-chain-screen-v2/all-br-chain-screen.csv"
	screens = {row["aiBrId"]: row for row in HELPERS.csv_rows(screen_path) if row["aiBrId"] in identifiers}
	ai_chains = {row["aiBrId"]: row for row in read_lines(QUEUE / "ai-br-chain-evidence.jsonl.gz") if row["aiBrId"] in identifiers}
	assert set(ai_chains) == identifiers
	case_ids = {identifier for chain in ai_chains.values() for identifier in chain["caseIds"]}
	delivery_summary = HELPERS.read_json(REVIEW / "2026-10-14-delivery-wide-assessment-v6/summary.json")
	hash_cache = {}
	cases = {row["caseId"]: case_evidence(row, Path(delivery_summary["runRoot"]), hash_cache)
			 for row in read_lines(QUEUE / "automated-chain-screen-v2/case-field-observations.jsonl.gz") if row["caseId"] in case_ids}
	selected_pairs = []
	rows = []
	for member in cohort:
		identifier = member["aiBrId"]
		candidates = leads[identifier]
		ai = register[identifier]
		targets, disposition, rationale = reviews.get(identifier, ([], "COUNTERPART_NOT_ESTABLISHED", unresolved_reason(member["statement"])))
		resolved_targets = [package_id(target, requirements) for target in targets]
		observations = [cases[case_id] for case_id in ai_chains[identifier]["caseIds"] if case_id in cases]
		field_states = collections.Counter()
		for case in observations:
			field_states.update(case["fieldStateCounts"])
		for target_id in resolved_targets:
			selected_pairs.append({"aiBrId": identifier, "aiStatement": member["statement"], "testPackageBrId": target_id,
								   "testTitle": requirements[target_id]["title"], "testStatus": requirements[target_id].get("status") or "NOT_DECLARED",
								   "testSourceAnchors": json.dumps(requirements[target_id].get("sourceAnchors") or [], ensure_ascii=True),
								   "relation": disposition, "rationale": rationale, "semanticStatus": "NOT_ASSESSED", "executionStatus": "NOT_EXECUTED"})
		rows.append(dict(member, aiSegments=ai["aiSegments"], aiElements=ai["aiElements"],
						 candidateTestBrIds=";".join(row["testBrId"] for row in candidates),
						 candidatePackageBrIds=";".join(row["testPackageBrId"] for row in candidates),
						 reviewDisposition=disposition, proposedTestBrIds=";".join(resolved_targets), reviewRationale=rationale,
						 currentLinkedCaseCount=len(ai_chains[identifier]["caseIds"]), casesWithArtifactScreen=len(observations),
						 casesWithCurrentVerifiedPayload=sum(any(payload["currentHashState"] == "CURRENT_HASH_MATCH" for payload in case["payloadEvidence"]) for case in observations),
						 casesMissingArtifactScreen=";".join(sorted(set(ai_chains[identifier]["caseIds"]) - cases.keys())),
						 artifactFieldStates=json.dumps(dict(field_states), sort_keys=True),
						 artifactReviewFlags=screens[identifier]["reviewFlags"],
						 semanticStatus="NOT_ASSESSED", testExecution="NOT_EXECUTED"))
	sources = [COHORT / "ai-account-card-complete-chain-no-candidate.csv", QUEUE / "all-ai-br-review-register.csv",
			   QUEUE / "ai-test-br-ranked-review-leads.csv", screen_path, PACKAGE, SCRIPT,
			   SCRIPT.with_name("build-account-card-br-crossmatch-review.py"), QUEUE / "ai-br-chain-evidence.jsonl.gz",
			   QUEUE / "automated-chain-screen-v2/case-field-observations.jsonl.gz",
			   ROOT / "specifications/ATL105/docs/specs/extracted_text.txt"]
	source_hashes = {str(path): HELPERS.sha256(native_path(path)) for path in sources}
	output.mkdir(parents=True)
	HELPERS.write_csv(output / "cohort-review.csv", rows)
	HELPERS.write_csv(output / "candidate-pairs.csv", pairs)
	HELPERS.write_csv(output / "proposed-relations.csv", selected_pairs)
	HELPERS.write_jsonl(output / "case-artifact-evidence.jsonl", (cases[identifier] for identifier in sorted(cases)))
	HELPERS.write_jsonl(output / "ai-chain-evidence.jsonl", (ai_chains[identifier] for identifier in sorted(ai_chains)))
	HELPERS.write_jsonl(output / "source-excerpts.jsonl", source_excerpts(ROOT / "specifications/ATL105/docs/specs/extracted_text.txt"))
	HELPERS.write_jsonl(output / "bounded-chain-screen.jsonl", (screens[identifier] for identifier in sorted(screens)))
	target_ids = {row["testPackageBrId"] for row in pairs + selected_pairs}
	HELPERS.write_jsonl(output / "test-chain-evidence.jsonl", (HELPERS.chain_for(package, requirements[identifier]) for identifier in sorted(target_ids)))
	summary = {"aiBrs": len(rows), "independentTestBrsInPackage": len(requirements), "candidatePairs": len(pairs),
			   "distinctCandidateTestBrs": len(target_ids), "catalogAliasRowsResolved": sum(row["testBrId"] != row["testPackageBrId"] for row in pairs),
			   "reviewDispositions": dict(collections.Counter(row["reviewDisposition"] for row in rows)),
			   "proposedClauseMatchAiBrs": sum(row["reviewDisposition"] == "CLAUSE_MATCH_PROPOSED" for row in rows),
			   "selectedRelationPairs": len(selected_pairs), "linkedCases": len(case_ids), "artifactScreenCases": len(cases),
			   "currentUniquePayloadPathsChecked": len(hash_cache), "referencedPathsCurrentlyAbsent": sum(value is None for value in hash_cache.values()),
			   "currentPayloadHashStates": dict(collections.Counter(payload["currentHashState"] for case in cases.values() for payload in case["payloadEvidence"])),
			   "casesWithCurrentVerifiedPayload": sum(any(payload["currentHashState"] == "CURRENT_HASH_MATCH" for payload in case["payloadEvidence"]) for case in cases.values()),
			   "producerExpectedResponsePresentCases": sum(bool(case["expectedResponsePresent"]) for case in cases.values()),
			   "currentLinkedCasesDifferFromV9": sum(int(row["testCaseCount"]) != row["currentLinkedCaseCount"] for row in rows),
			   "casesWithFieldDisagreements": sum(bool(case["fieldStateCounts"].get("VALUE_DISAGREEMENT_REVIEW_REQUIRED")) for case in cases.values()),
			   "fullChainReviewsCompleted": 0,
			   "confirmedSemanticMatches": 0, "testExecutions": 0, "inputSha256": source_hashes,
			   "limitations": ["Top-K leads from the all-1330-Test-BR retrieval search are not exhaustive semantic comparisons.",
							   "Bounded artifact screens are not a full BR/TS/TC/TD semantic review.",
							   "Source and Test authority govern conflicts; no SME approval is inferred."]}
	(output / "summary.json").write_text(json.dumps(summary, indent=2) + "\n", encoding="utf-8")
	(output / ".gitattributes").write_text("* -text whitespace=cr-at-eol\n", encoding="ascii")
	report = ["# Account/Card 283-BR Cohort Review", "", "All 283 frozen AI gap BRs are retained. The retrieval search considered all 1330 independent Test package records, across all categories, not just the seven Account/Card Test gaps. Candidate IDs resolve to BR-RULE catalog aliases or authored IDs.",
			  "", "## Results", "", "| Preliminary disposition | AI BRs |", "|---|---:|"]
	for disposition, count in summary["reviewDispositions"].items():
		report.append("| " + disposition + " | " + str(count) + " |")
	report += ["", "These are clause-level proposals, not authorized semantic decisions. A narrow AI clause may overlap part of a broader Test BR; no full Test BR or chain coverage credit is granted. Partial relations explicitly identify unsupported clauses.",
			   "", "All cohort statements were read; 198 receive explicit curated relations. Counterpart-not-established rows remain unresolved, not NOT_MATCH. Top-K retrieval and this title/clause review are not exhaustive semantic adjudication of every AI/Test combination.",
			   "", "## Artifact Evidence", "", str(len(case_ids)) + " unique linked AI cases; " + str(len(cases)) + " case screens; " + str(len(hash_cache)) + " unique payload references examined. All " + str(summary["casesWithCurrentVerifiedPayload"]) + " cases have at least one current hash-verified payload.",
			   "", "Current payload hash states: " + json.dumps(summary["currentPayloadHashStates"], sort_keys=True),
			   "", "Unbound paths have no frozen hash and are not hash mismatches or wholly missing case data. All cases declare expected responses, but those producer declarations are not independent outcome oracles.",
			   "", "Cases with metadata/payload field disagreements: " + str(summary["casesWithFieldDisagreements"]) + ". Negative/boundary intent must be reviewed before calling a disagreement a defect. Multi-leg step bindings, response-only claims and aliases remain unresolved where flagged.",
			   "", "The frozen 283 membership is unchanged. Earlier v9 chain counts and the newer queue's case associations may differ; both are retained. Physical presence does not prove BR intent, an independent expected result, wire validity, or host behavior.",
			   "", "Sources and hashes are in summary.json; full AI/Test chains, value-free payload observations, field flags, candidate pairs and curated proposed relations are included. Transaction card/track values from the attached authorization were not ingested or published.",
			   "", "## Authority and Open Gates", "", "Use authoritative ATL105 source when it resolves a conflict; otherwise Test BRs control conflicting AI interpretations. Existing draft/Test labels do not authorize mappings. Source quotes/complete claim interpretation, full scenario/case intent, independent outcomes, executable Test fixtures and wire/host evidence remain open. No Test or host execution occurred.",
			   "", "Source check: AI :1024 reflects the Section 11.2.1 only-required sentence, but the adjacent Segment 108 row is also R; this is a source-internal inconsistency. AI :1232 is supported by the Section 12.7 reserved-for-future-use note, so Test SEG108-R-016 needs revision or another applicable source. source-excerpts.jsonl preserves exact line-bound quotes. Three direct totals/status proposals (:1325, :1326, :1505) also have source excerpts; other proposals still need source-bound interpretation review.",
			   "", "## Per-AI Review", ""]
	for row in rows:
		report += ["## " + row["aiBrId"], row["statement"], ""]
		report += ["Disposition: " + row["reviewDisposition"], "", row["reviewRationale"], "", "Selected Test BRs: " + (row["proposedTestBrIds"] or "None established"), "", "Ranked retrieval only:"]
		for lead in leads[row["aiBrId"]][:3]:
			report.append("- " + lead["testPackageBrId"] + ": " + lead["testBrTitle"])
		report += ["", "Artifact review flags: " + row["artifactReviewFlags"], ""]
	(output / "REPORT.md").write_text("\n".join(report), encoding="utf-8")
	artifacts = {path.name: HELPERS.sha256(path) for path in output.iterdir() if path.is_file()}
	(output / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": artifacts}, indent=2) + "\n", encoding="utf-8")
	assert len(HELPERS.csv_rows(output / "cohort-review.csv")) == 283
	print(json.dumps({key: value for key, value in summary.items() if key != "inputSha256"}, indent=2))


if __name__ == "__main__":
	parser = argparse.ArgumentParser(description=__doc__)
	parser.add_argument("--self-test", action="store_true")
	parser.add_argument("--output", type=Path)
	arguments = parser.parse_args()
	if arguments.self_test:
		self_test()
	elif arguments.output:
		run(arguments.output)
	else:
		parser.error("Supply --self-test or --output")