"""Apply the QE-Fiserv feedback rule catalog (QEF-*) to a preserved AI delivery and emit
validation findings plus the readable review outputs the feedback requests."""

import argparse
import collections
import csv
import gzip
import hashlib
import io
import json
import os
import re
import sys
from datetime import datetime, timezone
from pathlib import Path

import ijson

ROOT = Path(__file__).resolve().parents[1]
ATL105 = ROOT / "specifications" / "ATL105"
CATALOG = ATL105 / "contract" / "qe-feedback-rule-catalog.json"
WIDTH_CONTRACT = ATL105 / "contract" / "element-84-width-assertions.json"
SPEC_PDF = ATL105 / "docs" / "specs" / "BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf"

ADVISORY_MARKERS = ("UNRESOLVED_KB_GAP", "CONSTRAINT_RULE_UNDEFINED", "CONSTRAINT_NOT_VERIFIABLE")
HIGHLIGHT_FLAGS = ("UNTRUSTED_VALID_VALUES", "AMBIGUOUS_LENGTH", "DELIBERATE_CONSTRAINT_VIOLATION")
NAME_KEYS = ("name", "title", "test_case_name", "tc_name", "description")
FINANCIAL_ROOT = "Financial Transaction Request"
SPECIAL_PROMPT_FAMILIES = ("totals", "download", "communication", "electronic mail")
SALE_AUTH_REFUND = {"0", "4", "6", "3", "5", "B", "7"}
VOID_CODES = {"8", "C", "U"}
VALUE_ROLES = {"completion", "void", "reversal", "void_of_return", "void-of-return"}
SEG100_FIELD_GROUPS = {
    "Track/Card Discretionary Block Data": ("TrackData", "CardDiscretionaryBlockData"),
    "Encrypted PIN Block Data": ("PINBlockData", "EncryptedPINBlockData"),
    "Pump/Lane Number": ("PumpNumber", "PumpLaneNumber", "Pump/LaneNumber"),
    "Fuel Purchase Amount": ("FuelPurchaseAmount",),
    "Non-Fuel Amount": ("NonFuelAmount", "Non-FuelAmount"),
    "Tax Amount": ("TaxAmount",),
    "Cash Amount": ("CashAmount",),
    "Approval Number": ("ApprovalNumber",),
    "Local Date/Time": ("LocalDateTime", "LocalDate"),
}
SEGMENT_NAMES = {
    "standard segment": "100", "standard message data segment": "100", "fleet segment": "101",
    "fleet data segment": "101", "product code data segment": "102", "ebt data segment": "103",
    "purchase card data segment": "104", "totals data segment": "105", "loyalty card data segment": "108",
    "electronic mail data segment": "109", "check data segment": "110", "variable info segment": "111",
    "variable information data segment": "111", "additional information data segment": "112",
    "sku data segment": "114", "print data segment": "115", "proprietary data load segment": "118",
    "totals with proprietary data load data segment": "119", "print data 2 segment": "120",
    "nfc payment tokenization data segment": "123", "emv data segment": "130",
    "emv request data segment": "130", "emv response data segment": "131",
    "ca public key file data segment": "132", "ca public key file segment": "132",
    "transaction attributes data segment": "134", "moneris data segment (request)": "135",
    "moneris data request segment": "135", "moneris data segment (response)": "136",
    "tax by product data segment": "143", "enhanced fleet request": "145",
    "enhanced fleet request segment": "145", "enhanced fleet data segment (response)": "146",
    "request incomm otc market basket data": "151", "incomm otc market basket data input segment": "151",
    "network token data request": "153", "network token data request segment": "153",
    "ev charging data segment": "156", "adjusted product code data segment": "157",
}


def native(path) -> str:
    resolved = os.path.abspath(str(path))
    if os.name == "nt" and not resolved.startswith("\\\\?\\"):
        return "\\\\?\\" + resolved
    return resolved


def read_json(path):
    with open(native(path), encoding="utf-8") as handle:
        return json.load(handle)


def sha256(path) -> str:
    digest = hashlib.sha256()
    with open(native(path), "rb") as handle:
        for block in iter(lambda: handle.read(1 << 20), b""):
            digest.update(block)
    return digest.hexdigest()


def norm(text) -> str:
    return re.sub(r"[^a-z0-9]+", " ", str(text or "").lower()).strip()


def blank(value) -> bool:
    return value is None or (isinstance(value, str) and value == "")


def segment_of_requirement(req) -> str:
    if not req:
        return "UNATTRIBUTED"
    if req.get("segment_number"):
        return str(req["segment_number"])
    for seg in req.get("segment_ids") or []:
        if str(seg).startswith("ENT-SEG-"):
            return str(seg)[len("ENT-SEG-"):]
    return "UNATTRIBUTED"


def segment_sort_key(segment: str):
    if segment.isdigit():
        return (0, int(segment), "")
    if segment.startswith("DL"):
        return (1, int(segment[2:] or 0), "")
    return (2, 0, segment)


def md_cell(value) -> str:
    return str(value if value is not None else "").replace("|", "\\|").replace("\r", " ").replace("\n", "<br>")


SENSITIVE_FIELD = re.compile(r"account|track|pan|pin|cvv|cavv|token|key|crypt|micr|name|phone|email|address|tavv|card data|fleet data|sku data", re.I)


def display_value(field: str, value) -> str:
    shown = "" if value is None else str(value)
    if shown and (len(shown) > 12 or SENSITIVE_FIELD.search(field)):
        return f"‹masked, {len(shown)} chars›"
    return shown


# ---------------------------------------------------------------- payload checks

def payload_segments(body: dict) -> dict:
    return {key: value for key, value in body.items() if isinstance(value, dict)}


def resolve_segment(name: str, segment_map: dict) -> str | None:
    return segment_map.get(name) or SEGMENT_NAMES.get(name.strip().lower())


def check_leg(tc: dict, leg: dict, payload: dict, meta: dict, widths: dict) -> list:
    """Return (ruleId, status, detail) tuples for one serialized test-data leg."""
    out = []
    root = next(iter(payload), None)
    body = payload.get(root) if root else None
    body = body if isinstance(body, dict) else {}
    segments = payload_segments(body)
    segment_map = {}
    for field in meta.get("fields") or []:
        if field.get("segmentFriendlyName") and field.get("segment"):
            segment_map.setdefault(field["segmentFriendlyName"], str(field["segment"]))
    resolved = {name: resolve_segment(name, segment_map) for name in segments}
    rendered_numbers = {number for number in resolved.values() if number}
    is_financial = root == FINANCIAL_ROOT
    role = (meta.get("role") or leg.get("role") or "").lower()

    content = [name for name, seg in segments.items() if any(k not in ("SegmentType", "SegmentLength") for k in seg)]
    if not segments or not content:
        out.append(("QEF-015", "FAIL", f"no data segment content; producer completeness={tc.get('completeness_status')} verification={tc.get('verification_status')}"))
    else:
        out.append(("QEF-015", "PASS", ""))

    composed = leg.get("composed") or {}
    renamed, unresolved, dropped = [], [], []
    for number, info in composed.items():
        matches = [name for name, seg in resolved.items() if seg == number]
        if not matches:
            if info["valued"]:
                dropped.append(f"{number} {info['name']}")
            continue
        if info["name"] not in matches:
            renamed.append(f"{info['name']} -> {matches[0]}")
    for name, number in resolved.items():
        if number is None:
            unresolved.append(name)
    if unresolved:
        out.append(("QEF-011", "FAIL", "segment name not resolvable to a segment number: " + "; ".join(unresolved)))
    elif composed:
        out.append(("QEF-011", "PASS", ("renamed but resolved: " + "; ".join(renamed)) if renamed else ""))

    only_type = [name for name, seg in segments.items() if not any(k not in ("SegmentType", "SegmentLength") for k in seg)]
    omitted = collections.Counter(
        str(item.get("segment")) for item in meta.get("unavailableFields") or []
        if "omitted" in str(item.get("reason", "")).lower() and str(item.get("segment")) in rendered_numbers
    )
    problems = []
    if dropped:
        problems.append("composed segment(s) not serialized: " + "; ".join(dropped))
    if only_type:
        problems.append("segment(s) carrying only Segment Type: " + "; ".join(only_type))
    if omitted:
        problems.append("fields omitted by composer per segment: " + ", ".join(f"{k}={v}" for k, v in sorted(omitted.items())))
    out.append(("QEF-016", "FAIL" if problems else "PASS", " | ".join(problems)))

    missing_length, bad_length = [], []
    for name, seg in segments.items():
        number = resolved.get(name) or ""
        width = widths.get(number, 3)
        value = seg.get("SegmentLength")
        if value is None:
            missing_length.append(name)
        elif not (isinstance(value, str) and value.isdigit() and len(value) == width and int(value) > 0):
            bad_length.append(f"{name}={value!r} (expected {width} non-zero digits)")
    if segments:
        detail = []
        if missing_length:
            detail.append(f"SegmentLength missing in {len(missing_length)}/{len(segments)}: " + "; ".join(missing_length[:6]))
        if bad_length:
            detail.append("invalid SegmentLength: " + "; ".join(bad_length[:6]))
        out.append(("QEF-018", "FAIL" if detail else "PASS", " | ".join(detail)))

    mismatched = []
    for name, seg in segments.items():
        number = resolved.get(name)
        declared = seg.get("SegmentType")
        if number and declared is not None and str(declared) != number:
            mismatched.append(f"{name}: SegmentType={declared} expected {number}")
        elif declared is None:
            mismatched.append(f"{name}: SegmentType missing")
    if segments:
        out.append(("QEF-021", "FAIL" if mismatched else "PASS", "; ".join(mismatched)))

    out.append(("QEF-022", "FAIL" if leg["file"].startswith("TC-") else "PASS",
                f"test data identified as {Path(leg['file']).stem}; expected TD-nnnn or TD-TC-nnnn" if leg["file"].startswith("TC-") else ""))

    if "NumSegments" in body:
        count_value = body.get("NumSegments")
        name_problem = ""
    else:
        count_value = body.get("NumberofSegments")
        name_problem = "field named " + ("NumberofSegments" if "NumberofSegments" in body else "<absent>") + ", expected NumSegments"
    count_problem = ""
    try:
        if count_value is None or int(str(count_value)) != len(segments):
            count_problem = f"value {count_value!r} != serialized segments {len(segments)}"
    except ValueError:
        count_problem = f"non-numeric value {count_value!r}"
    out.append(("QEF-023", "FAIL" if name_problem or count_problem else "PASS", " | ".join(p for p in (name_problem, count_problem) if p)))

    for name, seg in segments.items():
        aliases = [key for key in seg if (m := re.match(r"^(.*\D)(\d+)$", key)) and m.group(1) in seg]
        if aliases:
            out.append(("QEF-028", "FAIL", f"{name}: duplicated alias " + ", ".join(aliases)))
    if segments and not any(r[0] == "QEF-028" for r in out):
        out.append(("QEF-028", "PASS", ""))

    terminal_ids = [seg["TerminalID"] for seg in segments.values() if "TerminalID" in seg]
    for value in terminal_ids:
        ok = isinstance(value, str) and 13 <= len(value) <= 22
        out.append(("QEF-020", "PASS" if ok else "FAIL", "" if ok else f"TerminalID length {len(value) if isinstance(value, str) else value!r} outside 13-22"))

    for name, seg in segments.items():
        if resolved.get(name) == "123":
            out.append(("QEF-024", "PASS" if "TokenPANSuffix" in seg else "FAIL", "" if "TokenPANSuffix" in seg else f"{name}: TokenPANSuffix missing"))
        if "CAVVRevisedFormat" in seg:
            value = seg["CAVVRevisedFormat"]
            ok = isinstance(value, str) and value.isalnum() and len(value) <= 20 and "position" not in value.lower()
            out.append(("QEF-025", "PASS" if ok else "FAIL", "" if ok else ("CAVVRevisedFormat is the literal word 'Position'" if "position" in str(value).lower() else f"CAVVRevisedFormat not alphanumeric <=20 (length {len(str(value))})")))
        if resolved.get(name) == "111":
            missing = [k for k in ("VariableInformationIndicator", "VariableInformation") if k not in seg]
            out.append(("QEF-027", "FAIL" if missing else "PASS", ("missing " + ", ".join(missing)) if missing else ""))

    standard = next((seg for name, seg in segments.items() if resolved.get(name) == "100"), None)
    prompt = standard.get("PromptCode") if standard else next((seg["PromptCode"] for seg in segments.values() if "PromptCode" in seg), None)
    family = (root or "").lower()
    if is_financial:
        if isinstance(prompt, dict):
            txn, card = str(prompt.get("TransactionType") or ""), str(prompt.get("CardType") or "")
            ok = len(txn) == 1 and len(card) == 3
        else:
            txn = str(prompt or "")[:1]
            ok = isinstance(prompt, str) and len(prompt) == 4
        out.append(("QEF-031", "PASS" if ok else "FAIL", "" if ok else f"financial PromptCode={json.dumps(prompt)}; expected 1-char TransactionType + 3-char CardType"))
    elif any(word in family for word in SPECIAL_PROMPT_FAMILIES):
        txn = ""
        ok = isinstance(prompt, str) and len(prompt) == 3
        out.append(("QEF-031", "PASS" if ok else "FAIL", "" if ok else f"{root} PromptCode={json.dumps(prompt)}; expected a single 3-character value"))
    else:
        txn = ""

    if is_financial:
        count = len(segments)
        out.append(("QEF-030", "PASS" if 1 <= count <= 17 else "FAIL", "" if 1 <= count <= 17 else f"{count} data segments serialized"))
    if is_financial and standard is not None:
        if role in VALUE_ROLES or txn in VOID_CODES:
            value = standard.get("ApprovalNumber")
            ok = isinstance(value, str) and len(value.strip()) == 6
            out.append(("QEF-017", "PASS" if ok else "FAIL", "" if ok else f"{role or 'txn ' + txn}: ApprovalNumber={value!r}, expected 6-character value"))
        elif txn in SALE_AUTH_REFUND:
            if "ApprovalNumber" not in standard:
                out.append(("QEF-017", "FAIL", f"txn {txn}: ApprovalNumber field absent, expected present and empty"))
            elif not blank(standard.get("ApprovalNumber")):
                out.append(("QEF-017", "FAIL", f"txn {txn}: ApprovalNumber={standard.get('ApprovalNumber')!r}, expected empty"))
            else:
                out.append(("QEF-017", "PASS", ""))
        missing = [label for label, keys in SEG100_FIELD_GROUPS.items() if not any(k in standard for k in keys)]
        if "LocalDate" in standard and "LocalTime" not in standard:
            missing.append("Local Time")
        out.append(("QEF-019", "FAIL" if missing else "PASS", ("missing " + ", ".join(missing)) if missing else ""))
        track = standard.get("TrackData")
        if track is None:
            out.append(("QEF-026", "FAIL", "TrackData absent" + ("; separate AccountNumber field used" if "AccountNumber" in standard else "")))
        else:
            ok = isinstance(track, str) and re.search(r"[=:^]", track) and len(track) <= 75 and "AccountNumber" not in standard
            out.append(("QEF-026", "PASS" if ok else "FAIL", "" if ok else f"TrackData length {len(str(track))}, separator={'yes' if re.search(r'[=:^]', str(track)) else 'no'}, separate AccountNumber={'AccountNumber' in standard}"))
    transarmor = "transarmor" in (str(tc.get("transaction_type")) + str(root)).lower() or "token" in (str(tc.get("transaction_type")) + str(root)).lower()
    if transarmor and standard is not None:
        track = str(standard.get("TrackData") or "")
        ok = bool(re.search(r"[=:^]\d{4}", track)) or any("Expiration" in key for key in standard)
        out.append(("QEF-032", "PASS" if ok else "FAIL", "" if ok else "no expiration date with account/track data"))
    return out


# ---------------------------------------------------------------- catalog extraction

def compact_request(request: dict) -> dict:
    composed = {}
    for seg_name, fields in (request or {}).items():
        if not isinstance(fields, dict):
            continue
        number, valued, inputs = None, [], []
        for field_name, spec in fields.items():
            if not isinstance(spec, dict):
                continue
            ref = spec.get("data_ref") or {}
            number = number or (str(ref["segment"]) if ref.get("segment") else None)
            flags = [f.split(":")[0] for f in spec.get("flags") or [] if f.split(":")[0] in HIGHLIGHT_FLAGS]
            if spec.get("value") is not None:
                valued.append(field_name)
            if spec.get("value") is not None or flags:
                inputs.append([field_name, spec.get("value"), spec.get("method"), flags])
        number = number or SEGMENT_NAMES.get(seg_name.strip().lower()) or seg_name
        composed[number] = {"name": seg_name, "valued": valued, "inputs": inputs}
    return composed


def leg_files(tc: dict) -> list:
    found = []

    def walk(value):
        if isinstance(value, str) and value.endswith(".json"):
            found.append(value.replace("\\", "/").split("/")[-1])
        elif isinstance(value, dict):
            for item in value.values():
                walk(item)
        elif isinstance(value, list):
            for item in value:
                walk(item)

    walk(tc.get("test_data_leg_files"))
    if not found and tc.get("test_data_file"):
        walk(tc.get("test_data_file"))
    return list(dict.fromkeys(found))


def extract_tc(tc: dict) -> dict:
    expected = tc.get("expected_response") or {}
    rc = expected.get("response_code") or {}
    dc = expected.get("decline_code") or {}
    legs = []
    if tc.get("is_flow") and tc.get("steps"):
        for step in tc["steps"]:
            legs.append({"step": step.get("step"), "role": step.get("role"), "transaction_type": step.get("transaction_type"),
                         "composed": compact_request(step.get("request"))})
    else:
        legs.append({"step": None, "role": None, "transaction_type": tc.get("transaction_type"), "composed": compact_request(tc.get("request"))})
    files = leg_files(tc)
    for index, leg in enumerate(legs):
        leg["file"] = files[index] if index < len(files) else (files[0] if files and len(legs) == 1 else None)
    signature = [[leg["transaction_type"], sorted([number, info["name"], [[i[0], i[1]] for i in info["inputs"]]] for number, info in leg["composed"].items())]
                 for leg in legs]
    request_sig = hashlib.sha256(json.dumps(signature, default=str, sort_keys=True).encode()).hexdigest()
    flags = [str(f) for f in tc.get("flags") or []]
    return {
        "id": tc.get("id"), "scenario_id": tc.get("scenario_id"), "scenario_type": tc.get("scenario_type"),
        "transaction_type": tc.get("transaction_type"), "is_flow": bool(tc.get("is_flow")), "flow_id": tc.get("flow_id"),
        "flow_variant_id": tc.get("flow_variant_id"), "flow_variant_name": tc.get("flow_variant_name"),
        "priority": tc.get("priority"), "requirement_id": tc.get("requirement_id"),
        "requirement_ids": tc.get("requirement_ids") or ([tc["requirement_id"]] if tc.get("requirement_id") else []),
        "fingerprint": tc.get("fingerprint"), "negative_class": tc.get("negative_class"),
        "violated_element_name": tc.get("violated_element_name"), "rc_value": rc.get("value"), "rc_tier": rc.get("tier"),
        "dc_value": dc.get("value"), "dc_tier": dc.get("tier"),
        "verification_status": (tc.get("verification") or {}).get("status"), "completeness_status": tc.get("completeness_status"),
        "advisory": any(marker in f for f in flags for marker in ADVISORY_MARKERS),
        "source_page": tc.get("source_page"), "source_rule_id": tc.get("source_rule_id"),
        "name": next((tc.get(k) for k in NAME_KEYS if tc.get(k)), None), "files": files, "legs": legs,
        "request_sig": request_sig,
    }


# ---------------------------------------------------------------- engine

class Findings:
    def __init__(self, rules):
        self.rules = {r["ruleId"]: r for r in rules}
        self.counts = {rid: collections.Counter() for rid in self.rules}
        self.examples = {rid: [] for rid in self.rules}
        self.rows = io.StringIO()
        self.writer = csv.writer(self.rows)
        self.writer.writerow(["ruleId", "status", "artifactType", "artifactId", "testCaseId", "testDataFile", "segment", "detail"])
        self.by_tc = collections.defaultdict(collections.Counter)

    def add(self, rule_id, status, artifact_type, artifact_id, detail="", tc_id="", td="", segment=""):
        self.counts[rule_id][status] += 1
        if status != "PASS":
            self.writer.writerow([rule_id, status, artifact_type, artifact_id, tc_id, td, segment, detail])
            if len(self.examples[rule_id]) < 8:
                self.examples[rule_id].append({"artifactId": artifact_id, "testDataFile": td, "detail": detail[:300]})
            if tc_id:
                self.by_tc[tc_id][rule_id] += 1


def load_union(paths, key):
    items = {}
    for path in paths:
        if Path(native(path)).exists():
            for item in read_json(path)[key]:
                items.setdefault(item["id"], item)
    return items


def run(delivery: Path, output: Path, limit: int | None = None) -> dict:
    catalog = read_json(CATALOG)
    width_contract = read_json(WIDTH_CONTRACT)
    widths = {seg: 4 for seg in width_contract["fourDigitSegments"]}
    findings = Findings(catalog["rules"])
    tg = delivery / "test_generation" / "test_generation"
    td_dir = tg / "candidates" / "qe_shaped_test_data"

    requirements = load_union([delivery / "step5_requirements/approved/requirement_catalog.json",
                               delivery / "step5_requirements/candidates/requirement_candidates.json"], "requirements")
    scenarios = load_union([delivery / "scenarios/approved/approved_scenarios.json",
                            delivery / "scenarios/candidates/candidate_scenarios.json"], "scenarios")
    approved_tc = {tc["id"]: tc.get("fingerprint") for tc in read_json(tg / "approved/test_case_catalog.json")["test_cases"]}

    tcs, conflicts = {}, 0
    with open(native(tg / "candidates/test_case_candidates.json"), "rb") as handle:
        for raw in ijson.items(handle, "test_cases.item", use_float=True):
            rec = extract_tc(raw)
            if rec["id"] in approved_tc and approved_tc[rec["id"]] != rec["fingerprint"]:
                conflicts += 1
            tcs.setdefault(rec["id"], rec)
            if limit and len(tcs) >= limit:
                break
    if not limit:
        with open(native(tg / "approved/test_case_catalog.json"), "rb") as handle:
            for raw in ijson.items(handle, "test_cases.item", use_float=True):
                if raw["id"] not in tcs:
                    tcs[raw["id"]] = extract_tc(raw)

    # ---- catalog rules
    br_to_ts = collections.defaultdict(set)
    for sc in scenarios.values():
        for rid in set((sc.get("requirement_ids") or []) + ([sc["requirement_id"]] if sc.get("requirement_id") else [])):
            br_to_ts[rid].add(sc["id"])
    ts_to_tc = collections.defaultdict(set)
    br_to_tc = collections.defaultdict(set)
    for tc in tcs.values():
        if tc["scenario_id"]:
            ts_to_tc[tc["scenario_id"]].add(tc["id"])
        for rid in tc["requirement_ids"]:
            br_to_tc[rid].add(tc["id"])

    for req in requirements.values():
        seg = segment_of_requirement(req)
        if seg == "UNATTRIBUTED":
            findings.add("QEF-001", "FAIL", "BR", req["id"], "no segment_number or ENT-SEG segment_ids", segment=seg)
        elif req.get("segment_assignment_method") == "llm_proposed":
            findings.add("QEF-001", "REVIEW", "BR", req["id"], f"segment {seg} proposed by LLM pass only", segment=seg)
        else:
            findings.add("QEF-001", "PASS", "BR", req["id"], segment=seg)
        findings.add("QEF-002", "PASS" if br_to_ts.get(req["id"]) else "FAIL", "BR", req["id"], "" if br_to_ts.get(req["id"]) else "BR has no scenario", segment=seg)
        missing = [n for n, ok in (("specification", req.get("evidence_ref", {}).get("source_id")), ("version", req.get("spec_version")),
                                   ("segment", seg != "UNATTRIBUTED"), ("rule", req.get("source_rule_id"))) if not ok]
        findings.add("QEF-012", "FAIL" if missing else "PASS", "BR", req["id"], ("missing " + ", ".join(missing)) if missing else "", segment=seg)
        behaviour = norm(req.get("expected_behavior"))
        presence_only = behaviour in ("is required and must be present", "required and must be present", "must be present")
        findings.add("QEF-013", "FAIL" if presence_only else "PASS", "BR", req["id"], f"expected_behavior={req.get('expected_behavior')!r}" if presence_only else "", segment=seg)
        linked = [tcs[t] for t in br_to_tc.get(req["id"], ())]
        if linked:
            negative = any(t["scenario_type"] in ("negative", "boundary") or t["negative_class"] or t["violated_element_name"] for t in linked)
            findings.add("QEF-010", "PASS" if negative else "FAIL", "BR", req["id"], "" if negative else f"{len(linked)} linked TC(s), all positive", segment=seg)

    for sc in scenarios.values():
        findings.add("QEF-003", "PASS" if ts_to_tc.get(sc["id"]) else "FAIL", "TS", sc["id"], "" if ts_to_tc.get(sc["id"]) else "scenario has no test case")
        statement = norm((requirements.get(sc.get("requirement_id")) or {}).get("statement"))
        name = norm(sc.get("name"))
        verbatim = bool(statement) and (name == statement or name.endswith(statement))
        no_design = not sc.get("parameters") and not sc.get("target_transaction")
        detail = "; ".join(p for p, on in (("name repeats BR statement", verbatim), ("no parameters and no target transaction", no_design)) if on)
        findings.add("QEF-006", "FAIL" if detail else "PASS", "TS", sc["id"], detail)
        segs = sc.get("scope_segments") or [segment_of_requirement(requirements.get(sc.get("requirement_id")))]
        missing = [n for n, ok in (("specification", sc.get("spec_id")), ("version", sc.get("spec_version")),
                                   ("segment", segs and segs != ["UNATTRIBUTED"]), ("rule", sc.get("source_rule_id"))) if not ok]
        findings.add("QEF-012", "FAIL" if missing else "PASS", "TS", sc["id"], ("missing " + ", ".join(missing)) if missing else "")

    fp_groups, design_groups = collections.defaultdict(list), collections.defaultdict(list)
    for tc in tcs.values():
        fp_groups[tc["fingerprint"]].append(tc["id"])
        design_groups[(tc["scenario_id"], tc["scenario_type"], tc["transaction_type"], tc["negative_class"], tc["violated_element_name"],
                       tc["flow_variant_id"], tuple(sorted(tc["requirement_ids"])), tc["request_sig"])].append(tc["id"])
    duplicate_of = {}
    for group in list(fp_groups.values()) + list(design_groups.values()):
        if len(group) > 1:
            for tc_id in group[1:]:
                duplicate_of.setdefault(tc_id, group[0])

    payload_seen = set()
    tc_segments = {}
    for tc in tcs.values():
        tid = tc["id"]
        seg = segment_of_requirement(requirements.get(tc["requirement_id"]))
        findings.add("QEF-007", "PASS" if tc["name"] else "FAIL", "TC", tid, "" if tc["name"] else "no name/description field in catalog record", tc_id=tid, segment=seg)
        findings.add("QEF-008", "FAIL" if tid in duplicate_of else "PASS", "TC", tid, f"duplicate of {duplicate_of[tid]}" if tid in duplicate_of else "", tc_id=tid, segment=seg)
        findings.add("QEF-009", "PASS" if tc["rc_value"] not in (None, "") else "FAIL", "TC", tid, "" if tc["rc_value"] not in (None, "") else f"expected response code null (tier {tc['rc_tier']})", tc_id=tid, segment=seg)
        if tc["verification_status"] == "PASS":
            findings.add("QEF-014", "FAIL" if tc["advisory"] else "PASS", "TC", tid, "PASS verdict with unresolved KB-gap advisories" if tc["advisory"] else "", tc_id=tid, segment=seg)
        findings.add("QEF-012", "FAIL", "TC", tid, "missing specification, version" + ("" if tc["source_rule_id"] else ", rule") + ("" if seg != "UNATTRIBUTED" else ", segment"), tc_id=tid, segment=seg)

        rendered = set()
        missing_files = []
        if not tc["files"]:
            missing_files.append("no test data declared")
        for leg in tc["legs"]:
            if not leg["file"]:
                continue
            payload_path, meta_path = td_dir / leg["file"], td_dir / leg["file"].replace(".json", ".meta.json")
            if not os.path.exists(native(payload_path)):
                missing_files.append(f"{leg['file']} absent")
                continue
            payload_seen.add(leg["file"])
            payload = read_json(payload_path)
            meta = read_json(meta_path) if os.path.exists(native(meta_path)) else {}
            if not meta:
                missing_files.append(f"{leg['file']} metadata sidecar absent")
            for rule_id, status, detail in check_leg(tc, leg, payload, meta, widths):
                findings.add(rule_id, status, "TD", leg["file"], detail, tc_id=tid, td=leg["file"], segment=seg)
            root = next(iter(payload), None)
            body = payload.get(root) if root else {}
            seg_map = {f.get("segmentFriendlyName"): str(f.get("segment")) for f in meta.get("fields") or [] if f.get("segment")}
            rendered |= {resolve_segment(name, seg_map) for name in payload_segments(body if isinstance(body, dict) else {})}
            leg["root"] = root
            leg["rendered"] = sorted(n for n in rendered if n)
        findings.add("QEF-004", "FAIL" if missing_files else "PASS", "TC", tid, "; ".join(missing_files), tc_id=tid, segment=seg)
        if seg != "UNATTRIBUTED" and not missing_files:
            findings.add("QEF-005", "PASS" if seg in rendered else "FAIL", "TC", tid,
                         "" if seg in rendered else f"BR segment {seg} not in serialized segments {sorted(n for n in rendered if n)}", tc_id=tid, segment=seg)
        tc_segments[tid] = seg

    # ---- delivery rules
    hard_coded = []
    for path in sorted(Path(native(tg)).glob("*.py")) + [Path(native(delivery / "traceability_matrix.md"))]:
        if b"2025-3" in Path(path).read_bytes():
            hard_coded.append(Path(path).name)
    sources = sorted({(r.get("evidence_ref") or {}).get("source_id") for r in requirements.values()} - {None})
    findings.add("QEF-029", "FAIL", "Delivery", "source-set",
                 f"source ids {sources}: Dynamic Card Table Addendum 2014-1 and TransArmor Updates 2024-2 not ingested" +
                 (f"; '2025-3' hard-coded in {', '.join(hard_coded)}" if hard_coded else "; no '2025-3' literal found in generator code or matrix"))
    md_outputs = [p.name for p in Path(native(delivery)).rglob("*.md")]
    matrix_text = Path(native(delivery / "traceability_matrix.md")).read_text(encoding="utf-8", errors="replace")
    has_links = "pdf#page=" in matrix_text
    has_table = "| Test Case ID | TC Name | Description | Test Steps |" in matrix_text
    findings.add("QEF-033", "FAIL", "Delivery", "readable-outputs",
                 f"markdown files delivered: {md_outputs}; 8-column manual table={'yes' if has_table else 'no'}; per-TC cards=no; source-page hyperlinks={'yes' if has_links else 'no'}")

    all_payloads = {n for n in os.listdir(native(td_dir)) if n.endswith(".json") and not n.endswith(".meta.json")}
    orphans = sorted(all_payloads - payload_seen)
    population = {
        "businessRequirements": len(requirements), "scenarios": len(scenarios), "testCases": len(tcs),
        "approvedTestCases": len(approved_tc), "approvedCandidateFingerprintConflicts": conflicts,
        "flowTestCases": sum(1 for t in tcs.values() if t["is_flow"]),
        "payloadFiles": len(all_payloads), "payloadFilesValidated": len(payload_seen), "orphanPayloadFiles": len(orphans),
        "brWithoutScenario": sum(1 for r in requirements if not br_to_ts.get(r)),
        "scenarioWithoutTestCase": sum(1 for s in scenarios if not ts_to_tc.get(s)),
        "brWithoutTestCase": sum(1 for r in requirements if not br_to_tc.get(r)),
    }
    write_outputs(output, delivery, catalog, findings, population, requirements, scenarios, tcs, br_to_ts, ts_to_tc, br_to_tc, tc_segments, orphans)
    return population


# ---------------------------------------------------------------- outputs

def pdf_link(page, base_dir) -> str:
    if not page:
        return "n/a"
    try:
        rel = os.path.relpath(SPEC_PDF, base_dir).replace("\\", "/")
    except ValueError:
        rel = SPEC_PDF.as_uri()
    return f"[p.{page}]({rel.replace(' ', '%20')}#page={page})"


def write_outputs(output, delivery, catalog, findings, population, requirements, scenarios, tcs, br_to_ts, ts_to_tc, br_to_tc, tc_segments, orphans):
    output.mkdir(parents=True, exist_ok=True)
    readable = output / "readable"
    for sub in ("traceability", "manual-test-tables", "test-case-cards"):
        (readable / sub).mkdir(parents=True, exist_ok=True)
    link_base = readable / "traceability"

    rule_rows = []
    for rule in catalog["rules"]:
        c = findings.counts[rule["ruleId"]]
        applicable = sum(c.values())
        rule_rows.append({"ruleId": rule["ruleId"], "title": rule["title"], "artifact": rule["artifact"], "severity": rule["severity"],
                          "feedbackIds": rule["feedbackIds"], "applicable": applicable, "pass": c["PASS"], "fail": c["FAIL"],
                          "review": c["REVIEW"], "failPercent": round(100 * c["FAIL"] / applicable, 1) if applicable else None,
                          "sourceConflict": rule["sourceConflict"], "examples": findings.examples[rule["ruleId"]]})

    by_segment = collections.defaultdict(list)
    for req in requirements.values():
        by_segment[segment_of_requirement(req)].append(req)
    tcs_by_segment = collections.defaultdict(list)
    for tc in tcs.values():
        tcs_by_segment[tc_segments.get(tc["id"], "UNATTRIBUTED")].append(tc)
    segments = sorted(set(by_segment) | set(tcs_by_segment), key=segment_sort_key)

    gzip_path = output / "findings.csv.gz"
    with gzip.open(gzip_path, "wt", encoding="utf-8", newline="") as handle:
        handle.write(findings.rows.getvalue())

    index_rows = []
    for segment in segments:
        reqs = sorted(by_segment.get(segment, []), key=lambda r: r["id"])
        lines = [f"# Segment {segment} — BR → Scenario → Test Case → Test Data traceability", "",
                 "Generated by the Test Solution from the preserved AI delivery (canonical JSON stays authoritative). "
                 "`GAP` rows make silent coverage debt visible. Source-page links open the bound ATL105 2026-3 PDF.", "",
                 "| BR | Source | Statement | Scenarios | Test cases | Test data | Chain status |", "| --- | --- | --- | --- | --- | --- | --- |"]
        gaps = collections.Counter()
        for req in reqs:
            sc_ids = sorted(br_to_ts.get(req["id"], ()))
            tc_ids = sorted(br_to_tc.get(req["id"], ()))
            td = [f for t in tc_ids for f in tcs[t]["files"]]
            status = "GAP: no scenario" if not sc_ids else "GAP: no test case" if not tc_ids else "GAP: no test data" if not td else "LINKED (not semantically approved)"
            gaps[status] += 1
            lines.append("| " + " | ".join(md_cell(x) for x in (
                req["id"], pdf_link(req.get("source_page"), link_base), req.get("statement"),
                ", ".join(sc_ids[:6]) + (f" (+{len(sc_ids) - 6})" if len(sc_ids) > 6 else ""),
                ", ".join(tc_ids[:6]) + (f" (+{len(tc_ids) - 6})" if len(tc_ids) > 6 else ""),
                ", ".join(td[:4]) + (f" (+{len(td) - 4})" if len(td) > 4 else ""), status)) + " |")
        (readable / "traceability" / f"segment-{segment}.md").write_text("\n".join(lines) + "\n", encoding="utf-8")
        index_rows.append((segment, len(reqs), gaps, len(tcs_by_segment.get(segment, []))))

        write_manual_table(readable / "manual-test-tables" / f"segment-{segment}.md", segment, tcs_by_segment.get(segment, []), requirements, scenarios)
        write_cards(readable / "test-case-cards" / f"segment-{segment}.md", segment, tcs_by_segment.get(segment, []), requirements, scenarios, findings, link_base)

    index = ["# Readable Traceability Index", "", "| Segment | BRs | Linked | No scenario | No test case | No test data | Test cases | Views |", "| --- | --- | --- | --- | --- | --- | --- | --- |"]
    for segment, count, gaps, tc_count in index_rows:
        index.append(f"| {segment} | {count} | {gaps['LINKED (not semantically approved)']} | {gaps['GAP: no scenario']} | {gaps['GAP: no test case']} | {gaps['GAP: no test data']} | {tc_count} | "
                     f"[trace](traceability/segment-{segment}.md) · [manual table](manual-test-tables/segment-{segment}.md) · [cards](test-case-cards/segment-{segment}.md) |")
    (readable / "README.md").write_text("\n".join(index) + "\n", encoding="utf-8")

    summary = {
        "generatedAt": datetime.now(timezone.utc).isoformat(), "catalogId": catalog["catalogId"], "catalogVersion": catalog["catalogVersion"],
        "catalogSha256": sha256(CATALOG), "enforcementPolicy": catalog["enforcementPolicy"], "delivery": str(delivery),
        "intakeManifestSha256": sha256(delivery.parent / "intake-manifest.json") if os.path.exists(native(delivery.parent / "intake-manifest.json")) else None,
        "population": population, "orphanPayloadExamples": orphans[:20], "rules": rule_rows,
        "semanticCoverage": "NOT_ASSESSED", "hostExecution": "NOT_EXECUTED",
    }
    (output / "summary.json").write_text(json.dumps(summary, indent=1), encoding="utf-8")
    write_report(output, summary)
    manifest = {p.relative_to(output).as_posix(): sha256(p) for p in sorted(output.rglob("*")) if p.is_file() and p.name != "manifest.json"}
    (output / "manifest.json").write_text(json.dumps(manifest, indent=1), encoding="utf-8")


PRIORITY_MAP = {"P0": "P0", "P1": "P0", "P2": "P1", "P3": "P2", "P4": "P2"}


def describe_case(tc, requirements, scenarios):
    req = requirements.get(tc["requirement_id"]) or {}
    sc = scenarios.get(tc["scenario_id"]) or {}
    kind = tc["scenario_type"] or "case"
    target = tc["violated_element_name"] or (tc["flow_variant_name"] if tc["is_flow"] else None) or (req.get("statement") or "")[:70]
    name = f"{kind.title()} — {tc['transaction_type']} — {target}"
    return req, sc, name


def expected_text(tc):
    if tc["rc_value"] not in (None, ""):
        return f"Response code {tc['rc_value']}" + (f", decline code {tc['dc_value']}" if tc["dc_value"] else "") + f" (oracle tier {tc['rc_tier']})"
    return f"NOT DEFINED — producer expected response code is null (oracle tier {tc['rc_tier']}); QEF-009 blocks pass/fail"


def write_manual_table(path, segment, tcs, requirements, scenarios):
    lines = [f"# Segment {segment} — Manual Validation Test Cases", "",
             "Format per QE feedback Def_SepRun06: functional coverage only (happy path, BDD scenario, contract, negative, boundary); no NFR rows. "
             "Test Case IDs are sequential per feature; the AI test-case ID is kept in TC Name. Priority maps producer P1→P0, P2→P1, P3/P4→P2.", ""]
    by_feature = collections.defaultdict(list)
    for tc in tcs:
        by_feature[str(tc["transaction_type"])].append(tc)
    for feature in sorted(by_feature):
        endpoint = f"Segment {segment} — {feature} (ATL105 request)"
        lines += [f"## {feature}", "", "| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |",
                  "| --- | --- | --- | --- | --- | --- | --- | --- |"]
        for n, tc in enumerate(sorted(by_feature[feature], key=lambda t: t["id"]), 1):
            req, sc, name = describe_case(tc, requirements, scenarios)
            steps = []
            for i, leg in enumerate(tc["legs"], 1):
                steps.append(f"{i}. Load test data `{leg.get('file') or 'MISSING'}` ({leg.get('role') or leg.get('transaction_type')})")
            steps.append(f"{len(steps) + 1}. Send the request{'s in step order, carrying prior-leg values' if tc['is_flow'] else ''} to the authorizer")
            steps.append(f"{len(steps) + 1}. Compare the response with the expected result")
            description = f"{req.get('statement') or 'BR statement unavailable'} Scenario {tc['scenario_id']}: {sc.get('name') or ''}"
            lines.append("| " + " | ".join(md_cell(x) for x in (
                f"TC_{n:02d}", f"{tc['id']} — {name}", description[:400], "<br>".join(steps), expected_text(tc),
                "Not Executed", PRIORITY_MAP.get(str(tc["priority"]), "P2"), endpoint)) + " |")
        lines.append("")
    path.write_text("\n".join(lines), encoding="utf-8")


def write_cards(path, segment, tcs, requirements, scenarios, findings, link_base):
    lines = [f"# Segment {segment} — Test Case Cards", "",
             "One card per test case (QE feedback Def_SepRun07/Def_SepRun10). Derived from the canonical JSON on every run; JSON stays authoritative. "
             "Fields flagged UNTRUSTED_VALID_VALUES, AMBIGUOUS_LENGTH or DELIBERATE_CONSTRAINT_VIOLATION are marked ⚠.", ""]
    for tc in sorted(tcs, key=lambda t: t["id"]):
        req, sc, name = describe_case(tc, requirements, scenarios)
        page = req.get("source_page")
        lines += [f"## {tc['id']} — {md_cell(name)}", "",
                  f"**Purpose:** {md_cell(req.get('statement') or 'BR statement unavailable')} (source {pdf_link(page, link_base)})", "",
                  f"**Derivation path:** Spec {pdf_link(page, link_base)} (ATL105 2026-3) → {req.get('source_rule_id') or 'no source rule'} → "
                  f"{tc['requirement_id']} → derivation_method = {req.get('derivation_method') or 'n/a'} → {tc['scenario_id']} ({tc['scenario_type']}"
                  f"{', violated = ' + tc['violated_element_name'] if tc['violated_element_name'] else ''}) → {tc['id']} (transaction = {tc['transaction_type']})", "",
                  f"**Preconditions:** transaction type {tc['transaction_type']}; message layout "
                  + "; ".join(f"step {leg.get('step') or 1} {leg.get('root') or leg.get('transaction_type')} segments {', '.join(leg.get('rendered') or sorted(leg['composed']))}" for leg in tc["legs"]), "",
                  "**Input:**", "", "| Segment | Field | Value | Method | Flags |", "| --- | --- | --- | --- | --- |"]
        for leg in tc["legs"]:
            for number, info in leg["composed"].items():
                for field, value, method, flags in info["inputs"]:
                    lines.append("| " + " | ".join(md_cell(x) for x in (number, field, display_value(field, value), method, ("⚠ " + ", ".join(flags)) if flags else "")) + " |")
        issues = findings.by_tc.get(tc["id"])
        lines += ["", f"**Expected outcome:** {expected_text(tc)}", "",
                  f"**Traceability:** [{tc['requirement_id']}](../traceability/segment-{segment}.md) → {tc['scenario_id']} → {tc['id']} → " + ", ".join(f"`{f}`" for f in tc["files"]) or "no test data", "",
                  f"**Test Solution findings:** " + (", ".join(f"{r}×{n}" for r, n in sorted(issues.items())) if issues else "none"), "",
                  "**Reviewer checklist (SME):**", "",
                  "- [ ] Does the BR statement match the cited ATL105 page and segment?",
                  "- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?",
                  "- [ ] Are all segments and fields required for this transaction present with valid values?",
                  "- [ ] Is the expected response/decline code correct for this transaction context?",
                  "- [ ] Can this test case be executed as-is without further data?", ""]
    path.write_text("\n".join(lines), encoding="utf-8")


def write_report(output, summary):
    p = summary["population"]
    lines = ["# QE-Fiserv Feedback Training — Validation Report", "",
             f"- Catalog: `{summary['catalogId']}` v{summary['catalogVersion']} (SHA-256 `{summary['catalogSha256'][:16]}…`), 33 rules enforced as written regardless of workbook status.",
             f"- Delivery: preserved October-10 AI delivery `runs/2026-10-17/Run1` (intake manifest SHA-256 `{(summary['intakeManifestSha256'] or 'n/a')[:16]}…`).",
             f"- Population: {p['businessRequirements']} BRs, {p['scenarios']} scenarios, {p['testCases']} test cases ({p['flowTestCases']} flows), "
             f"{p['payloadFilesValidated']}/{p['payloadFiles']} test-data payloads validated, {p['orphanPayloadFiles']} unreferenced payloads.",
             f"- Silent coverage: {p['brWithoutScenario']} BRs without scenario, {p['scenarioWithoutTestCase']} scenarios without test case, {p['brWithoutTestCase']} BRs without test case.",
             "- Semantic coverage: NOT_ASSESSED; host execution: NOT_EXECUTED. A PASS is feedback conformance only.", "",
             "| Rule | Title | Artifact | Feedback | Applicable | Pass | Fail | Review | Fail % |", "| --- | --- | --- | --- | --- | --- | --- | --- | --- |"]
    for r in summary["rules"]:
        lines.append(f"| {r['ruleId']} | {md_cell(r['title'])} | {r['artifact']} | {md_cell(', '.join(r['feedbackIds']))} | {r['applicable']} | {r['pass']} | {r['fail']} | {r['review']} | {r['failPercent'] if r['failPercent'] is not None else 'n/a'} |")
    lines += ["", "## Examples and source conflicts", ""]
    for r in summary["rules"]:
        lines.append(f"### {r['ruleId']} — {md_cell(r['title'])}")
        if r["sourceConflict"]:
            lines.append(f"- Source conflict (SME confirmation needed): {md_cell(r['sourceConflict'])}")
        for example in r["examples"][:4]:
            lines.append(f"- `{example['artifactId']}`: {md_cell(example['detail'])}")
        lines.append("")
    lines += ["Readable outputs: [index](readable/README.md). Full findings: `findings.csv.gz`. Hashes: `manifest.json`."]
    (output / "QE-FEEDBACK-VALIDATION-REPORT.md").write_text("\n".join(lines) + "\n", encoding="utf-8")


# ---------------------------------------------------------------- self-test

def self_test() -> int:
    widths = {"103": 4}
    tc = {"transaction_type": "Financial Transaction Request", "completeness_status": "COMPLETE", "verification_status": "PASS"}
    leg = {"file": "TC-000001.json", "role": None, "composed": {"100": {"name": "Standard Message Data Segment", "valued": ["Segment Type"], "inputs": []},
                                                                "102": {"name": "Product Code Data Segment", "valued": ["Product Code"], "inputs": []}}}
    meta = {"fields": [{"segment": "100", "segmentFriendlyName": "Standard Segment"}, {"segment": "135", "segmentFriendlyName": "Moneris Data Segment (Request)"}]}
    bad = {FINANCIAL_ROOT: {"NumberofSegments": "3", "Standard Segment": {"SegmentType": "100", "TerminalID": "SHORT",
           "PromptCode": {"TransactionType": "0", "CardType": "001"}, "AccountNumber": "4111"},
           "Moneris Data Segment (Request)": {"SegmentType": "100", "MonerisData": "1"},
           "Variable Info Segment": {"SegmentType": "111", "VariableInformationLength": "003", "VariableInformationLength2": "002"}}}
    got = {}
    for rule, status, _ in check_leg(tc, leg, bad, meta, widths):
        got.setdefault(rule, set()).add(status)
    expect_fail = ["QEF-016", "QEF-017", "QEF-018", "QEF-019", "QEF-020", "QEF-021", "QEF-022", "QEF-023", "QEF-026", "QEF-027", "QEF-028"]
    for rule in expect_fail:
        assert "FAIL" in got.get(rule, set()), (rule, got.get(rule))
    good = {FINANCIAL_ROOT: {"NumSegments": "1", "Standard Segment": {"SegmentType": "100", "SegmentLength": "086", "TerminalID": "HC375003ABCDEF",
            "PromptCode": {"TransactionType": "3", "CardType": "020"}, "TrackData": "5121076312111357:49121360907587", "PINBlockData": "",
            "PumpNumber": "", "FuelPurchaseAmount": "", "NonFuelAmount": "00001400", "TaxAmount": "", "CashAmount": "", "ApprovalNumber": "",
            "LocalDateTime": "2608101430"}}}
    good_leg = {"file": "TD-TC-000001.json", "role": None, "composed": {"100": {"name": "Standard Segment", "valued": ["x"], "inputs": []}}}
    statuses = {rule: status for rule, status, _ in check_leg(tc, good_leg, good, {"fields": []}, widths)}
    for rule in ("QEF-015", "QEF-016", "QEF-017", "QEF-018", "QEF-019", "QEF-020", "QEF-021", "QEF-022", "QEF-023", "QEF-026", "QEF-030", "QEF-031"):
        assert statuses.get(rule) == "PASS", (rule, statuses.get(rule))
    completion = {"file": "TD-1.json", "role": "completion", "composed": {}}
    assert dict((r, s) for r, s, _ in check_leg(tc, completion, good, {"fields": []}, widths))["QEF-017"] == "FAIL"
    email = {"Electronic Mail": {"NumSegments": "1", "Electronic Mail Data Segment": {"SegmentType": "109", "SegmentLength": "040", "PromptCode": {}}}}
    assert dict((r, s) for r, s, _ in check_leg({}, {"file": "TD-2.json", "composed": {}}, email, {"fields": []}, widths))["QEF-031"] == "FAIL"
    catalog = read_json(CATALOG)
    assert len({r["ruleId"] for r in catalog["rules"]}) == len(catalog["rules"]) == 33
    assert display_value("Account Number", "4111111111111111").startswith("‹masked")
    assert display_value("Enhanced Fleet Data", "AKLT" + "X" * 37).startswith("‹masked")
    assert display_value("Segment Type", "100") == "100"
    print("self-test passed")
    return 0


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--delivery", type=Path)
    parser.add_argument("--output", type=Path)
    parser.add_argument("--limit", type=int, help="stop after N test cases (development only)")
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        return self_test()
    if not args.delivery or not args.output:
        parser.error("--delivery and --output are required")
    if args.output.exists() and any(args.output.iterdir()):
        parser.error(f"output directory {args.output} already exists; use a new versioned path")
    print(json.dumps(run(args.delivery.resolve(), args.output.resolve(), args.limit), indent=1))
    return 0


if __name__ == "__main__":
    sys.exit(main())
