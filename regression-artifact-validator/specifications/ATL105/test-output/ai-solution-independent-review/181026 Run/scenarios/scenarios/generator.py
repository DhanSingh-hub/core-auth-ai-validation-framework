"""
Scenario Generation — Parameter Extraction -> Combination -> Constraint Filter ->
Fingerprint Check -> Scenario Builder -> Independent AI Review.
Architecture: DEVELOPER_ARCHITECTURE_DOCUMENT.md §3 (Test Generation Pipeline).

Reads (read-only): step5_requirements/approved/requirement_catalog.json,
assembled/output/knowledge_base.json, an OPTIONAL saved scenario_scope filter,
and step0_acquisition/knowledge/*.txt (Level B/C reference — response/decline
codes) for the expected-outcome oracle.

Writes: scenarios/candidates/candidate_scenarios.json
Approval gate: MANDATORY (one of the 3 required architecture gates) — this
module never writes to approved/; promotion happens only via explicit human
Approve action (Streamlit), no bypass toggle offered.

Known deviations from the literal spec, disclosed rather than hidden:
  - Combination Engine: the architecture names `allpairspy`, and that is what runs
    (shared/combinatorics.py), pinned in src/requirements.txt and resolved from the
    organization's Nexus mirror — the earlier "pip install is blocked" note here was
    stale and has been removed. There is no builtin fallback: a missing/unimportable
    allpairspy fails the run immediately (CombinatoricsEngineUnavailable) rather than
    silently degrading to a slower, row-count-suboptimal substitute.
  - Constraint Filter: currently a documented pass-through — no structured
    mutual-exclusion rules have been parsed from the spec yet, so nothing is
    silently removed. Hook is in place to extend once such rules exist.

Phase B (/memories/session/plan.md) — combination is now TRANSACTION-FIRST.
Parameters used to be extracted globally and combined across every element in the
catalog, after which TransactionResolver was asked which transaction the result
belonged to; parameters drawn from segments that never co-occur in one message
produced combinations no real transaction can carry, and 722 scenarios died at
"discriminating parameters never co-occur in one transaction". Extraction is now
scoped to one transaction template at a time, so every positive scenario is valid
for a named transaction by construction and carries `target_transaction`.
"""

from __future__ import annotations

import copy
import hashlib
import json
import re
import sys
from datetime import datetime, timezone
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]  # src/pipeline
REQUIREMENTS_APPROVED = ROOT / "step5_requirements" / "approved" / "requirement_catalog.json"
KNOWLEDGE_BASE_PATH = ROOT / "assembled" / "output" / "knowledge_base.json"
TEMPLATES_PATH = ROOT / "assembled" / "output" / "atl105_complete_templates.json"
ENUM_CAPS_PATH = ROOT / "config" / "enum_domain_caps.json"
TEST_AXES_PATH = ROOT / "config" / "test_axes.json"
FLOWS_PATH = ROOT / "config" / "transaction_flows.json"
STEP0_KNOWLEDGE_DIR = ROOT / "step0_acquisition" / "knowledge"
OUT_FILE = Path(__file__).parent / "candidates" / "candidate_scenarios.json"
RAW_PAGES_DIR = ROOT.parent.parent / "raw" / "pdf_pages"
# Phase 3.2 (/memories/session/plan.md) — WHICH approved requirement ids produced zero
# scenarios this run and why, not just an aggregate REQUIREMENT_ONLY count downstream.
REQUIREMENT_SKIP_REASONS_PATH = ROOT.parent.parent / "logs" / "run_current" / "requirement_skip_reasons.json"
# LLM Scenario Architect pass (multi-leg obligation gap-check) — deterministic gap report
# written every run regardless of LLM availability; see llm_scenario_architect.py.
MULTI_LEG_GAP_REPORT_PATH = ROOT.parent.parent / "logs" / "run_current" / "multi_leg_gap_report.json"
# qe-scenario-architect skill's own validator (scripts/validate_scenarios.py), run against
# every batch of generated scenarios as an automatic QA gate — see SKILL_VALIDATOR_DIR below.
SCENARIO_AUDIT_REPORT_PATH = ROOT.parent.parent / "logs" / "run_current" / "scenario_audit_report.json"
SKILL_VALIDATOR_DIR = ROOT.parent.parent / ".github" / "skills" / "qe-scenario-architect" / "scripts"

sys.path.insert(0, str(Path(__file__).resolve().parent))
sys.path.insert(0, str(ROOT / "scenario_scope"))
sys.path.insert(0, str(ROOT / "shared"))
sys.path.insert(0, str(ROOT / "test_generation"))
sys.path.insert(0, str(ROOT / "spec_profiler"))
import scope_filter  # noqa: E402
import element_examples  # noqa: E402
import element_roles  # noqa: E402
import vocab  # noqa: E402
from llm_enricher import judge_candidates, judge_with_traceback, PROMPT_VERSION  # noqa: E402
from llm_client import is_available  # noqa: E402
from confidence import apply_confidence, score_catalog  # noqa: E402
from transaction_names import build_alias_map, build_override_map, resolve_transaction_name  # noqa: E402
from combinatorics import pairwise  # noqa: E402
import test_data_config  # noqa: E402
import flow_expansion_axes  # noqa: E402
import flow_rule_resolver  # noqa: E402
import flow_axis_provenance  # noqa: E402
from spec_identity import get_spec_version  # noqa: E402
import llm_scenario_architect  # noqa: E402
import response_codes  # noqa: E402
import llm_monitor  # noqa: E402
import stage_timing  # noqa: E402
from atomic_io import load_json_strict, write_json_atomic, CorruptArtifactError  # noqa: E402
from fixed_value import extract_fixed_value  # noqa: E402
import value_sanitation  # noqa: E402
if str(SKILL_VALIDATOR_DIR) not in sys.path:
    sys.path.insert(0, str(SKILL_VALIDATOR_DIR))
try:
    import validate_scenarios as skill_validator  # noqa: E402
except ImportError:
    skill_validator = None  # skill folder moved/absent — audit gate degrades to a skipped no-op

# Phase 3.1 (/memories/session/plan.md) — element-role/segment-membership logic now
# lives in shared/element_roles.py (also used by test_generation/transaction_resolver.py);
# aliased to the original private names so every call site below is unchanged.
_element_source_roles = element_roles.element_source_roles
_element_to_segments = element_roles.element_to_segments
_is_input_parameter = element_roles.is_input_parameter
_subject_elem_no = element_roles.subject_elem_no

SOURCE_ID = "SRC-ATL105-PDF-001"
SPEC_VERSION = get_spec_version()

# Response Code (p396-398) read from the extracted spec, never a literal: a negative expects the
# declined meaning; a positive/boundary code depends on the transaction type its composed Prompt
# Code carries, so it is set per test case (expected_response_builder.py), not here.
_RESPONSE_CODE_TABLE, _RESPONSE_CODE_PAGE = response_codes.load_response_code_table()
RESPONSE_CODE_DECLINED = response_codes.expected_response_code("negative", None, _RESPONSE_CODE_TABLE, {}).get("code")
# forensic-review 08 H5 — RESPONSE_CODE_DECLINED resolves at import time from the Step 2
# attribute catalog; if that catalog is absent/unparsed, every negative scenario below
# silently got expected_response_code=None with no flag and no count. Computed once so
# every negative-scenario builder stamps the same disclosure instead of re-deriving it.
_RESPONSE_CODE_UNAVAILABLE_FLAGS = ([] if RESPONSE_CODE_DECLINED else
    ["RESPONSE_CODE_UNAVAILABLE: Response Code valid values not extracted from "
     "attribute_catalog — expected_response_code is null, not a documented declined code"])
ORACLE_AUTHORITY_NOTE = "Level C (chatbot-sourced, see step0_acquisition/knowledge) — validate against ATL105 PDF Ch.13 before trusting as ground truth"

_ENUM_STATEMENT_RE = re.compile(r"must be one of: (.+)\.$")
_LENGTH_STATEMENT_RE = re.compile(r"must not exceed (\d+) bytes\.$")

MIN_ENUM_VALUES = 2
MAX_ENUM_VALUES = 8


def _normalize_element_name(name: str) -> str:
    """Collapses whitespace runs (incl. literal embedded newlines) to a single space.
    Real bug found 2026-10-06: requirement statement text carries PDF line-wrap
    artifacts (e.g. "Partial Approval\nIndicator"), and a plain .strip() at the two
    extraction sites below preserved that embedded newline straight into
    scenario["parameters"] keys — template field names are clean single-line strings,
    so every downstream exact-string comparison against them (e.g.
    llm_transaction_resolver.py's anti-fabrication check) silently failed for any
    scenario touching a line-wrapped element name. Normalizing at the source means
    every consumer gets a clean key, not just the one call site a regression happened
    to be noticed in."""
    return re.sub(r"\s+", " ", name).strip()

# Phase B3: every Step 5 derivation whose statement can carry a "must be one of"
# domain. Previously only python_template qualified, which limited the entire
# combinatorial engine to 258 of 6,291 requirements (4%).
PARAMETER_DERIVATION_METHODS = {
    "python_template",
    "field_constraint",
    "field_fixed_value",
    "message_field_constraint",
    "table_field_constraint",
}

# Business-rule path (build_business_rule_scenarios): every deterministic rule-shaped
# derivation method becomes one ASSERT scenario, mapped to its scenario `type`. Anything
# not listed here is derived by Step 5 and then silently dropped by that builder (though
# it may still be picked up by PARAMETER_DERIVATION_METHODS above).
RULE_METHODS = {
    "business_rule_derived": "business_rule",
    "llm_phrased": "narrative_rule",
    "field_constraint": "field_constraint",
    "field_fixed_value": "field_fixed_value",
    "message_field_constraint": "message_field_constraint",
    "table_field_constraint": "table_field_constraint",
    "relationship_derived": "relationship",
    "supplemental_entity_derived": "supplemental_entity",
}


def load_enum_caps() -> dict:
    data = _load_json(ENUM_CAPS_PATH)
    if not data.get("rules") and not data.get("default"):
        return {"default": MAX_ENUM_VALUES, "rules": []}
    return data


def cap_for_element(element_name: str | None, enum_caps: dict | None = None) -> int:
    """Phase B2: a flat cap truncated every domain equally, so the axes the client
    actually varies (card brand, entry mode, CVM, wallet) were cut to the same 8
    values as low-significance reference data like state codes."""
    enum_caps = enum_caps or {"default": MAX_ENUM_VALUES, "rules": []}
    low = (element_name or "").lower()
    for rule in enum_caps.get("rules", []):
        match = (rule.get("match") or "").lower()
        if match and match in low:
            return int(rule.get("cap", MAX_ENUM_VALUES))
    return int(enum_caps.get("default", MAX_ENUM_VALUES))


def sample_enum_values(values: list[str], cap: int = MAX_ENUM_VALUES) -> tuple[list[str], bool]:
    """Cap a large documented domain at `cap` by representative sampling.

    Discarding an element because its domain is large loses the dimension entirely
    (Card Type 103 codes, Currency 161, ...). Keep the two boundaries and spread the
    remainder evenly across the ordered domain so the sample stays deterministic and
    reproducible. Returns (values, was_sampled).
    """
    if cap < MIN_ENUM_VALUES:
        cap = MIN_ENUM_VALUES
    if len(values) <= cap:
        return values, False
    first, last = values[0], values[-1]
    middle_slots = cap - 2
    interior = values[1:-1]
    sampled, seen = [], set()
    picked = []
    if middle_slots > 0 and interior:
        step = len(interior) / middle_slots
        picked = [interior[int(i * step)] for i in range(middle_slots)]
    for v in [first, *picked, last]:
        if v not in seen:
            seen.add(v)
            sampled.append(v)
    return sampled, True


def _load_json(path: Path) -> dict:
    if not path.exists():
        return {}
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError):
        return {}


def load_requirements() -> dict:
    return load_json_strict(REQUIREMENTS_APPROVED)


def load_knowledge_base() -> dict:
    return load_json_strict(KNOWLEDGE_BASE_PATH)


def load_templates() -> dict:
    return _load_json(TEMPLATES_PATH)


def transaction_to_element_nos(templates: dict) -> dict[str, set[str]]:
    """transaction_type -> the element numbers its own message template carries.
    This is what makes combination transaction-first: a parameter can only enter a
    combination for a transaction whose message actually has a field for it."""
    mapping: dict[str, set[str]] = {}
    for tx, template in (templates.get("message_templates") or {}).items():
        elems: set[str] = set()
        for segment in template.get("segments", []):
            for field in segment.get("fields", []):
                if field.get("element_no"):
                    elems.add(str(field["element_no"]))
        mapping[tx] = elems
    return mapping


def list_saved_filter_names() -> list[str]:
    return [f.get("name") for f in scope_filter.list_saved_filters() if f.get("name")]


def _load_saved_filter(name: str | None) -> dict | None:
    if not name:
        return None
    for f in scope_filter.list_saved_filters():
        if f.get("name") == name:
            return f
    return None


def _in_scope_segment_numbers(saved_filter: dict | None, kb: dict) -> set[str] | None:
    """None means 'no filter applied — full scope'. Segment-level granularity
    only: a saved filter's field-level narrowing isn't persisted in the filter
    artifact today, so a narrowed segment is still treated as fully in-scope
    here — a known, disclosed simplification."""
    if not saved_filter:
        return None
    return {s["segment_number"] for s in saved_filter.get("in_scope_segments", [])}


# _element_source_roles/_element_to_segments/_is_input_parameter/_subject_elem_no
# now live in shared/element_roles.py (aliased above, just below the imports).


def extract_parameters(requirements: dict, kb: dict, saved_filter: dict | None = None,
                        allowed_elements: set[str] | None = None,
                        enum_caps: dict | None = None,
                        examples: dict | None = None) -> list[dict]:
    """Turns approved 'must be one of' Requirements into combinatorial parameter
    dimensions, scoped by an optional saved filter and (Phase B1) by the element
    set of a single transaction template via `allowed_elements`."""
    in_scope_segments = _in_scope_segment_numbers(saved_filter, kb)
    element_to_segments = _element_to_segments(kb)
    source_roles = _element_source_roles(kb)
    enum_caps = enum_caps if enum_caps is not None else load_enum_caps()
    examples = examples if examples is not None else element_examples.load_catalog()

    parameters = []
    for req in requirements.get("requirements", []):
        if req.get("derivation_method") not in PARAMETER_DERIVATION_METHODS:
            continue
        elem_no = _subject_elem_no(req)
        if not elem_no:
            continue
        if allowed_elements is not None and elem_no not in allowed_elements:
            continue
        if in_scope_segments is not None:
            segs = element_to_segments.get(elem_no, set())
            if not segs & in_scope_segments:
                continue
        if not _is_input_parameter(elem_no, source_roles):
            continue
        m = _ENUM_STATEMENT_RE.search(req.get("statement") or "")
        if not m:
            continue
        values = [v.strip() for v in m.group(1).split(", ") if v.strip()]
        if len(values) < MIN_ENUM_VALUES:
            continue
        element_name = _normalize_element_name(req["statement"].split(" must be one of:")[0])
        domain_source = "requirement_catalog"
        # QE-curated element_examples (docs/client-inputs/ATL105_ELEMENT_EXAMPLES.md) is
        # hand-curated per element, not regex-scraped from narrative text — prefer its full
        # domain over the requirement statement's own list when this element is genuinely
        # enumerated there. Enrichment only: this never PROMOTES an element into a parameter
        # that wasn't one already (a "must be one of" requirement already exists above), so
        # it can't explode combinatorics for the hundreds of QE-only enumerations (e.g. Product
        # Code) that never reached the requirement catalog as a parameter in the first place.
        examples_entry = element_examples.lookup(examples, elem_no)
        if examples_entry and examples_entry.get("kind") == "enumeration" and examples_entry.get("values"):
            values = list(examples_entry["values"])
            domain_source = "qe_catalog"
        else:
            # Fix B1 (2026-10-09, scenario-generation traceback-failure investigation) — no
            # QE override exists for this element, so these regex-parsed rows are the ONLY
            # values this parameter carries into combination. A mis-parsed PDF table (byte-
            # offset ranges, segment cross-references) can masquerade as a flat "must be one
            # of" list (e.g. Segment Length/Sequence Number's entire domain was this, zero
            # real values); apply the same guard step 5 uses when first building this
            # statement so garbage never reaches the combinatorial engine.
            values = value_sanitation.genuine_fixed_list(
                [{"value": v} for v in values], context=f"{element_name} (scenario param)")
        if len(values) < MIN_ENUM_VALUES:
            continue
        cap = cap_for_element(element_name, enum_caps)
        values, was_sampled = sample_enum_values(values, cap)
        parameters.append({
            "element_no": elem_no, "element_name": element_name,
            "requirement_id": req["id"], "values": values,
            "domain_sampled": was_sampled, "domain_cap": cap, "domain_source": domain_source,
            "derivation_method": req.get("derivation_method"),
            # Fix A (2026-10-09) — lets a multi-parameter scenario be traceback-judged
            # per-field against ITS OWN source page, instead of one shared anchor page.
            "source_page": req.get("source_page"),
        })

    # Two derivations can describe the same element (e.g. python_template and
    # field_constraint); keep the widest documented domain, not an arbitrary one.
    by_name: dict[str, dict] = {}
    collapsed_different_elements = 0
    for p in parameters:
        existing = by_name.get(p["element_name"])
        if existing is None:
            by_name[p["element_name"]] = p
            continue
        # forensic-review 08 M6 — ATL105 genuinely reuses one field name across different
        # elements (e.g. "Segment Length" in segment 100 vs. segment 108, see
        # forensic-review/03-step1-entity-extraction.md C3). Collapsing by name alone
        # silently drops a DIFFERENT real element, not just a re-derivation of the same
        # one; this makes that case visible instead of invisible, without changing which
        # entry wins (still the widest documented domain).
        if existing.get("element_no") != p.get("element_no"):
            collapsed_different_elements += 1
        if len(p["values"]) > len(existing["values"]):
            by_name[p["element_name"]] = p
    if collapsed_different_elements:
        print(f"  Parameter extraction: {collapsed_different_elements} element(s) sharing a duplicate "
              "name with a DIFFERENT element_no were collapsed to one combinatorial dimension")
    return list(by_name.values())


def _fingerprint(params: dict[str, str], scope_label: str, transaction: str = "") -> str:
    """SHA-256(canonical(spec_id + spec_version + scope + transaction + sorted(params))) —
    per DEVELOPER_ARCHITECTURE_DOCUMENT.md §3 Fingerprint Check, with transaction added
    for Phase B: the same parameter combination against two different transactions is
    two different tests, so it must not collapse to one fingerprint."""
    canonical = (SOURCE_ID + SPEC_VERSION + scope_label + transaction
                 + json.dumps(sorted(params.items()), ensure_ascii=False))
    return hashlib.sha256(canonical.encode("utf-8")).hexdigest()


def _dedup_key(params: dict[str, str], transaction: str = "") -> str:
    """Scope-independent identity. The documented fingerprint mixes in scope_label, so the
    same logical scenario produced under N scope filters yielded N distinct fingerprints and
    cross-run dedup never fired — 27 filters inflated one rule set 27x. Transaction IS part
    of identity (unlike scope), for the reason given in _fingerprint."""
    canonical = (SOURCE_ID + SPEC_VERSION + transaction
                 + json.dumps(sorted(params.items()), ensure_ascii=False))
    return hashlib.sha256(canonical.encode("utf-8")).hexdigest()


SCENARIOS_APPROVED_PATH = OUT_FILE.parent.parent / "approved" / "approved_scenarios.json"

_SC_NUM_RE = re.compile(r"^SC-(\d+)$")


def _sc_num(raw: str | None) -> int:
    if not raw:
        return 0
    m = _SC_NUM_RE.match(str(raw))
    return int(m.group(1)) if m else 0


def _existing_fingerprints() -> set[str]:
    """Only APPROVED scenarios are protected against duplication. Candidates are
    freely regenerable pre-approval; reading OUT_FILE here (the candidates file
    this same run is about to overwrite) made every deterministic-fingerprint
    negative scenario look like a 'duplicate' of itself on every run after the
    first, silently dropping negatives to zero — fixed 2026-09-10."""
    data = _load_json(SCENARIOS_APPROVED_PATH)
    keys = set()
    for s in data.get("scenarios", []):
        k = s.get("dedup_key") or s.get("fingerprint")
        if k:
            keys.add(k)
    return keys


def _next_scenario_id_start() -> int:
    """A candidate id must never collide with an id already approved — approve_scenarios()
    (streamlit_app.py) preserves already-approved ids untouched and only mints new ones, so
    a fresh candidate batch starting back at SC-0001 every run would produce a duplicate id
    the moment it merged against an approved catalog past its first entry. A CORRUPT
    approved catalog raises CorruptArtifactError rather than restarting at SC-0001."""
    approved = load_json_strict(SCENARIOS_APPROVED_PATH)
    return max((_sc_num(s.get("id")) for s in approved.get("scenarios", [])), default=0) + 1


# Phase E2 (/memories/session/plan.md G17/E2) — explicit negative_class -> decline
# code map, replacing a keyword heuristic that defaulted every unmatched field to
# '2I'. Only classes with real supporting evidence (BUYPASS decline-code semantics
# documented in step0_acquisition/knowledge) are mapped; everything else returns
# None so the Expected Response Builder correctly FLAGs it instead of asserting a
# guess. A grounded per-element match (find_grounded_decline_code, run later in
# expected_response_builder.py against the real Appendix H catalog) always takes
# priority over this hint when one exists.
_NEGATIVE_CLASS_DECLINE_HINTS = {
    "length_exceeded": None,       # length alone doesn't imply a specific decline reason
    "enum_invalid": None,
    "charset_invalid": None,
    "empty_value": None,
    "missing_mandatory": None,
    "wrong_fixed_value": None,
    "boundary_at_limit": None,     # not a violation at all — no decline expected
}
# Single table every negative_class literal is read through instead of being retyped at
# each of the ~8 call sites below — vocab.canonical() raises at import time (loud, not a
# guess) if a name here ever drifts from scenario.schema.json's own enum, the same drift
# class that produced 431 BAD_CATEGORY errors in step5_requirements/extractor.py.
_NEGATIVE_CLASSES = {name: vocab.canonical(name, "scenario.negative_class")
                     for name in (*_NEGATIVE_CLASS_DECLINE_HINTS, "client_supplied_override", "orphan_followon")}

# forensic-review 08 H4 — the keyword hints below (amount/account/card/pan/partial) are
# evidenced for a WRONG-VALUE violation (the field was sent, but its content is invalid),
# never for an omission or a shape/charset violation. Without this gate, omitting
# "Cardholder Name" asserted 2H "Invalid Account Number" purely because "card" is a
# substring of the field name — a guess dressed up as an assertion.
_KEYWORD_ELIGIBLE_NEGATIVE_CLASSES = {
    _NEGATIVE_CLASSES["enum_invalid"], _NEGATIVE_CLASSES["length_exceeded"],
    _NEGATIVE_CLASSES["wrong_fixed_value"],
}


def _decline_code_for(element_name: str, negative_class: str | None = None) -> str | None:
    """Element-name keyword hints, kept only where the mapping is well-evidenced
    (amount/account/card/pan/partial), each independently documented in
    step0_acquisition/knowledge. Returns None — never a default guess — when
    nothing matches, so downstream FLAGs it instead of asserting a fabricated code.
    """
    if negative_class and negative_class in _NEGATIVE_CLASS_DECLINE_HINTS:
        mapped = _NEGATIVE_CLASS_DECLINE_HINTS[negative_class]
        if mapped:
            return mapped
    if negative_class is not None and negative_class not in _KEYWORD_ELIGIBLE_NEGATIVE_CLASSES:
        return None  # omission/charset/empty: no evidenced keyword mapping applies here
    low = element_name.lower()
    if "amount" in low:
        return "2P"  # Invalid Amount
    if "account" in low or "card" in low or "pan" in low:
        return "2H"  # Invalid Account Number / No Such Issuer
    if "partial" in low:
        return "2K"  # Honor With ID / partial amount
    return None  # no evidenced mapping — FLAG downstream, do not guess


def build_positive_scenarios(combos: list[dict[str, str]], parameters: list[dict], scope_label: str,
                              transaction: str = "") -> list[dict]:
    by_name = {p["element_name"]: p for p in parameters}
    scenarios = []
    for combo in combos:
        anchor_name = next(iter(combo))
        anchor = by_name[anchor_name]
        # Phase 2 (/memories/session/plan.md) — a combination scenario covers EVERY
        # parameter's requirement, not just the anchor's. Crediting only the anchor
        # made every other combined requirement look like REQUIREMENT_ONLY/
        # SCENARIO_ONLY in the traceability matrix even though it was actually
        # covered by this same scenario. requirement_id (singular) is kept for
        # backward compatibility (e.g. the LLM traceback judge's page lookup).
        combo_requirement_ids = sorted({
            by_name[name]["requirement_id"] for name in combo
            if by_name.get(name) and by_name[name].get("requirement_id")
        })
        params_desc = ", ".join(f"{k}={v}" for k, v in combo.items())
        name = f"Positive — {transaction} — {params_desc}" if transaction else f"Positive — {params_desc}"
        # Phase 5.4 — carry source_rule_id/related_entity_ids so TransactionResolver's
        # related_entity_ids fallback (and Phase 7's orphan re-linking) can reach this
        # scenario too; previously only business-rule-path scenarios carried these.
        # forensic-review 08 C1 — parameters are keyed "element_no" (extract_parameters),
        # not "elem_no"; the old key never existed, so this was always [] and source_rule_id
        # below was always None.
        related_entity_ids = sorted({f"ENT-ELEM-{by_name[name]['element_no']}"
                                      for name in combo if by_name.get(name) and by_name[name].get("element_no")})
        # Fix A (2026-10-09) — each combined parameter's OWN source page, so the traceback
        # judge can verify this field-by-field instead of demanding every combined field
        # appear together on one shared (anchor) page — a combination the spec legitimately
        # never shows side-by-side verbatim (see shared/llm_enricher.py's
        # TRACEBACK_JUDGE_MULTI_FIELD_ADDENDUM).
        parameter_source_pages = {name: by_name[name].get("source_page")
                                  for name in combo if by_name.get(name)}
        # forensic-review 08 M3 — sample_enum_values() caps a large domain and records
        # domain_sampled on the PARAMETER, but the scenario itself never disclosed it —
        # a scenario built from a sampled domain looked identical to one with full coverage.
        sampled_elements = sorted(name for name in combo
                                   if by_name.get(name) and by_name[name].get("domain_sampled"))
        flags = ([f"PARTIAL_DOMAIN_COVERAGE: {', '.join(sampled_elements)} — combinatorial domain "
                  "was capped/sampled, not exhaustive"] if sampled_elements else [])
        scenarios.append({
            "name": name,
            # WO-4c (COPILOT_FIX_PLAN.md) — requirement_id stays the anchor alias for
            # backward compat with readers that only know the singular field.
            "requirement_id": anchor["requirement_id"],
            "requirement_ids": combo_requirement_ids,
            "type": "positive",
            "parameters": combo,
            "parameter_source_pages": parameter_source_pages,
            # Set so TransactionResolver takes its DIRECT_TRANSACTION_REFERENCE path
            # instead of re-inferring a transaction from element membership.
            "target_transaction": transaction or None,
            "source_rule_id": f"ENT-ELEM-{anchor['element_no']}" if anchor.get("element_no") else None,
            "related_entity_ids": related_entity_ids,
            "expected_response_code": None,
            "expected_decline_code": None,
            "oracle_authority": ORACLE_AUTHORITY_NOTE,
            "source": "auto_generated",
            "flags": flags,
            "fingerprint": _fingerprint(combo, scope_label, transaction),
            "dedup_key": _dedup_key(combo, transaction),
        })
    return scenarios


def build_negative_scenarios(requirements: dict, kb: dict, saved_filter: dict | None, scope_label: str,
                              allowed_elements: set[str] | None = None, transaction: str = "") -> list[dict]:
    in_scope_segments = _in_scope_segment_numbers(saved_filter, kb)
    element_to_segments = _element_to_segments(kb)
    source_roles = _element_source_roles(kb)
    scenarios = []
    for req in requirements.get("requirements", []):
        if req.get("derivation_method") not in PARAMETER_DERIVATION_METHODS:
            continue
        elem_no = _subject_elem_no(req)
        if not elem_no:
            continue
        if allowed_elements is not None and elem_no not in allowed_elements:
            continue
        if in_scope_segments is not None and not (element_to_segments.get(elem_no, set()) & in_scope_segments):
            continue
        if not _is_input_parameter(elem_no, source_roles):
            continue  # same input-vs-oracle-field filter the positive path already applies

        statement = req.get("statement") or ""
        element_name = _normalize_element_name(statement.split(" must be")[0])
        length_m = _LENGTH_STATEMENT_RE.search(statement)
        enum_m = _ENUM_STATEMENT_RE.search(statement)
        if length_m:
            max_bytes = int(length_m.group(1))
            violation = f"value exceeds documented max length ({max_bytes + 1} bytes, limit is {max_bytes})"
            params = {element_name: f"<{max_bytes + 1}-byte value, 1 over limit>"}
            negative_class = _NEGATIVE_CLASSES["length_exceeded"]
        elif enum_m:
            violation = "value outside the documented fixed list"
            params = {element_name: "<value not in documented valid_values list>"}
            negative_class = _NEGATIVE_CLASSES["enum_invalid"]
        else:
            continue

        params_display = {element_name: params[element_name]}
        prefix = f"Negative — {transaction} — " if transaction else "Negative — "
        scenarios.append({
            "name": f"{prefix}{element_name} violates: {violation}",
            "requirement_id": req["id"],
            "type": "negative",
            "negative_class": negative_class,
            "parameters": params_display,
            "parameter_source_pages": {element_name: req.get("source_page")},
            "target_transaction": transaction or None,
            "source_rule_id": req.get("source_rule_id") or f"ENT-ELEM-{elem_no}",
            "related_entity_ids": [f"ENT-ELEM-{elem_no}"],
            "expected_response_code": RESPONSE_CODE_DECLINED,
            "expected_decline_code": _decline_code_for(element_name, negative_class),
            "oracle_authority": ORACLE_AUTHORITY_NOTE,
            "source": "auto_generated",
            "flags": list(_RESPONSE_CODE_UNAVAILABLE_FLAGS),
            "fingerprint": _fingerprint(params_display, scope_label, transaction),
            "dedup_key": _dedup_key(params_display, transaction),
        })
    return scenarios


# Phase E1 (/memories/session/plan.md) — negative-class expansion. Reuses the SAME
# python_template length-statement pool as build_negative_scenarios (the requirement
# text already documents character_type in English, e.g. "Access Code must be
# alphanumeric and must not exceed 12 bytes.") — grounded, not a new extraction path.
_TYPED_LENGTH_STATEMENT_RE = re.compile(
    r"^(.*?) must be (numeric|alphanumeric|alphabetic|alpha) and must not exceed (\d+) bytes\.$")
# Grounded example: "In Standard Message Data Segment (Data Segment No. 100), field 2
# (Segment Length) is required and must be present." (derivation_method=field_constraint)
_MISSING_MANDATORY_RE = re.compile(r"field \d+ \(([^)]+)\) is required and must be present\.$")
# A Chapter 12 field's exact address: ENT-FIELD-<segment>-<field_no> (segment may be DL<n>).
_FIELD_ADDRESS_RE = re.compile(r"^ENT-FIELD-(.+)-(\d+)$")


def _field_address(req: dict) -> tuple[str, str] | None:
    m = _FIELD_ADDRESS_RE.match(str(req.get("source_rule_id") or ""))
    return (m.group(1), m.group(2)) if m else None


# Fix 3 (/memories/session/plan.md) — flow-only counterpart of build_charset_and_
# boundary_scenarios()'s single-message placeholder; value_fill.py's
# parse_flow_boundary_placeholder() recognizes this exact text and fills a
# genuinely non-zero at-limit value (see that module for why the plain
# single-message placeholder is unsafe for a carried/linked field).
FLOW_BOUNDARY_AT_LIMIT_PLACEHOLDER = "<flow at-limit valid value, exact max length>"
# Link types a flow step declares that actually carry a REQUEST-side value forward
# (or require one to differ) — the only link shapes a length-boundary value can
# meaningfully travel through. carry_response_field (processor-generated),
# duplicate_sequence_violation (a deliberate negative), and
# carry_var_info_table_field (lives inside a table's Data bytes, not a plain
# field) are deliberately excluded.
FLOW_BOUNDARY_ELIGIBLE_LINK_TYPES = {"carry_request_field", "amount_relation", "must_differ", "retransmission"}


def flow_boundary_lengths(requirements: dict) -> dict[str, dict]:
    """Element name -> {max_bytes, requirement_id, source_rule_id} for every
    requirement whose own statement documents a 'must be <char_type> and must not
    exceed N bytes' length — the SAME grounded statement pool
    build_charset_and_boundary_scenarios() reads for single-message boundary
    scenarios. First requirement to name an element wins (Step 5 is the one source
    of truth; a second requirement re-stating the same element's same length would
    only duplicate the citation, never change it)."""
    lengths: dict[str, dict] = {}
    for req in requirements.get("requirements", []):
        m = _TYPED_LENGTH_STATEMENT_RE.search(req.get("statement") or "")
        if not m:
            continue
        # forensic-review 08 H3 — same PDF line-wrap artifact _normalize_element_name()
        # was written for; a bare .strip() here let an embedded newline straight into
        # flow boundary scenarios' parameter keys.
        element_name = _normalize_element_name(m.group(1))
        if element_name in lengths:
            continue
        lengths[element_name] = {
            "max_bytes": int(m.group(3)),
            "requirement_id": req.get("id"),
            "source_rule_id": req.get("source_rule_id"),
        }
    return lengths


def _qualifying_length_requirements(requirements: dict, kb: dict, saved_filter: dict | None,
                                     allowed_elements: set[str] | None):
    """Shared scope/role filtering for the Phase E builders below — identical rules
    to build_negative_scenarios, factored out so the 3 new builders can't drift out
    of sync with it."""
    in_scope_segments = _in_scope_segment_numbers(saved_filter, kb)
    element_to_segments = _element_to_segments(kb)
    source_roles = _element_source_roles(kb)
    for req in requirements.get("requirements", []):
        if req.get("derivation_method") not in PARAMETER_DERIVATION_METHODS:
            continue
        elem_no = _subject_elem_no(req)
        if not elem_no:
            continue
        if allowed_elements is not None and elem_no not in allowed_elements:
            continue
        if in_scope_segments is not None and not (element_to_segments.get(elem_no, set()) & in_scope_segments):
            continue
        if not _is_input_parameter(elem_no, source_roles):
            continue
        yield req, elem_no


def build_charset_and_boundary_scenarios(requirements: dict, kb: dict, saved_filter: dict | None,
                                          scope_label: str, allowed_elements: set[str] | None = None,
                                          transaction: str = "") -> tuple[list[dict], list[dict]]:
    """Returns (negative_scenarios, boundary_scenarios) for the length-bounded
    requirement pool: charset_invalid + empty_value (both genuinely negative), and
    boundary_at_limit (NOT a violation — expected outcome is approval, so it gets
    its own scenario_type rather than being mislabeled negative)."""
    negatives: list[dict] = []
    boundaries: list[dict] = []
    for req, elem_no in _qualifying_length_requirements(requirements, kb, saved_filter, allowed_elements):
        statement = req.get("statement") or ""
        m = _TYPED_LENGTH_STATEMENT_RE.search(statement)
        if not m:
            continue
        element_name, _char_word, max_bytes_s = _normalize_element_name(m.group(1)), m.group(2), m.group(3)
        prefix = f" — {transaction} — " if transaction else " — "

        for negative_class, placeholder, violation_desc in (
            (_NEGATIVE_CLASSES["charset_invalid"], "<charset violation: character outside documented character_type>",
             "value contains a character outside its documented character_type"),
            (_NEGATIVE_CLASSES["empty_value"], "<empty value, violates minimum length>",
             "value is empty, violating minimum-length/mandatory-content"),
        ):
            params_display = {element_name: placeholder}
            fp_params = {**params_display, "__class__": negative_class}
            negatives.append({
                "name": f"Negative{prefix}{element_name} violates: {violation_desc}",
                "requirement_id": req["id"],
                "type": "negative",
                "negative_class": negative_class,
                "parameters": params_display,
                "target_transaction": transaction or None,
                "source_rule_id": req.get("source_rule_id") or f"ENT-ELEM-{elem_no}",
                "related_entity_ids": [f"ENT-ELEM-{elem_no}"],
                "expected_response_code": RESPONSE_CODE_DECLINED,
                "expected_decline_code": _decline_code_for(element_name, negative_class),
                "oracle_authority": ORACLE_AUTHORITY_NOTE,
                "source": "auto_generated",
                "flags": list(_RESPONSE_CODE_UNAVAILABLE_FLAGS),
                "fingerprint": _fingerprint(fp_params, scope_label, transaction),
                "dedup_key": _dedup_key(fp_params, transaction),
            })

        boundary_params = {element_name: "<at-limit valid value, exact max length>"}
        fp_boundary = {**boundary_params, "__class__": _NEGATIVE_CLASSES["boundary_at_limit"]}
        boundaries.append({
            "name": f"Boundary{prefix}{element_name} = exactly {max_bytes_s} bytes (documented max)",
            "requirement_id": req["id"],
            "type": "boundary",
            "negative_class": _NEGATIVE_CLASSES["boundary_at_limit"],  # not a violation; kept for uniform reporting
            "parameters": boundary_params,
            "target_transaction": transaction or None,
            "source_rule_id": req.get("source_rule_id") or f"ENT-ELEM-{elem_no}",
            "related_entity_ids": [f"ENT-ELEM-{elem_no}"],
            "expected_response_code": None,
            "expected_decline_code": None,
            "oracle_authority": ORACLE_AUTHORITY_NOTE,
            "source": "auto_generated",
            "flags": [],
            "fingerprint": _fingerprint(fp_boundary, scope_label, transaction),
            "dedup_key": _dedup_key(fp_boundary, transaction),
        })
    return negatives, boundaries


def build_missing_mandatory_scenarios(requirements: dict, kb: dict, saved_filter: dict | None,
                                       scope_label: str, allowed_elements: set[str] | None = None,
                                       transaction: str = "",
                                       allowed_segments: set[str] | None = None) -> list[dict]:
    """A Required field, deliberately omitted. Composer enforces the omission
    (see composer.compose_request's MISSING_MANDATORY_PLACEHOLDER check) — nothing
    in the existing R/O/C inclusion logic could otherwise force this.

    The requirement is about ONE segment field, so the scenario carries that field's
    address (target_field) and is only built for a transaction containing that segment.
    Keying by element alone built 81% of these for transactions lacking the segment and
    blanked the element in every segment that shares it."""
    in_scope_segments = _in_scope_segment_numbers(saved_filter, kb)
    element_to_segments = _element_to_segments(kb)
    scenarios = []
    for req in requirements.get("requirements", []):
        if req.get("derivation_method") != "field_constraint":
            continue
        m = _MISSING_MANDATORY_RE.search(req.get("statement") or "")
        if not m:
            continue
        elem_no = _subject_elem_no(req)
        address = _field_address(req)
        # A structural field with no Chapter 13 element is still a required field to omit.
        if not elem_no and not address:
            continue
        if allowed_segments is not None and address and address[0] not in allowed_segments:
            continue
        if allowed_elements is not None and not address and elem_no not in allowed_elements:
            continue
        segs = {address[0]} if address else element_to_segments.get(elem_no, set())
        if in_scope_segments is not None and not (segs & in_scope_segments):
            continue

        element_name = _normalize_element_name(m.group(1))
        params_display = {element_name: "<omitted, violates mandatory presence>"}
        prefix = f" — {transaction} — " if transaction else " — "
        fp_params = {**params_display, "__class__": _NEGATIVE_CLASSES["missing_mandatory"],
                     "__target_field__": req.get("source_rule_id") or ""}
        scenarios.append({
            "name": f"Negative{prefix}{element_name} violates: mandatory field omitted",
            "requirement_id": req["id"],
            "type": "negative",
            "negative_class": _NEGATIVE_CLASSES["missing_mandatory"],
            "parameters": params_display,
            "target_field": req.get("source_rule_id") if address else None,
            "target_transaction": transaction or None,
            "source_rule_id": req.get("source_rule_id") or f"ENT-ELEM-{elem_no}",
            "related_entity_ids": [f"ENT-ELEM-{elem_no}"] if elem_no else [req["source_rule_id"]],
            "expected_response_code": RESPONSE_CODE_DECLINED,
            "expected_decline_code": _decline_code_for(element_name, _NEGATIVE_CLASSES["missing_mandatory"]),
            "oracle_authority": ORACLE_AUTHORITY_NOTE,
            "source": "auto_generated",
            "flags": list(_RESPONSE_CODE_UNAVAILABLE_FLAGS),
            "fingerprint": _fingerprint(fp_params, scope_label, transaction),
            "dedup_key": _dedup_key(fp_params, transaction),
        })
    return scenarios


def build_wrong_fixed_value_scenarios(requirements: dict, kb: dict, saved_filter: dict | None,
                                       scope_label: str, allowed_elements: set[str] | None = None,
                                       transaction: str = "",
                                       allowed_segments: set[str] | None = None) -> list[dict]:
    """field_fixed_value requirements carry their documented literal directly
    (req['fixed_value']) — no statement parsing needed, this is the most
    directly-grounded of the Phase E classes. Addressed to the one segment field
    the requirement is about (target_field), same as missing_mandatory."""
    in_scope_segments = _in_scope_segment_numbers(saved_filter, kb)
    element_to_segments = _element_to_segments(kb)
    elem_no_to_name = {a["element_number"]: _normalize_element_name(a["element_name"])
                        for a in kb.get("attributes", []) if a.get("element_number") and a.get("element_name")}
    scenarios = []
    for req in requirements.get("requirements", []):
        if req.get("derivation_method") != "field_fixed_value":
            continue
        fixed_value = req.get("fixed_value")
        if not fixed_value:
            continue
        elem_no = _subject_elem_no(req)
        if not elem_no:
            continue
        address = _field_address(req)
        if allowed_segments is not None and address and address[0] not in allowed_segments:
            continue
        if allowed_elements is not None and elem_no not in allowed_elements:
            continue
        if in_scope_segments is not None and not (element_to_segments.get(elem_no, set()) & in_scope_segments):
            continue

        element_name = elem_no_to_name.get(elem_no, f"Element {elem_no}")
        params_display = {element_name: f"<wrong fixed value: must differ from documented fixed value '{fixed_value}'>"}
        prefix = f" — {transaction} — " if transaction else " — "
        fp_params = {**params_display, "__class__": _NEGATIVE_CLASSES["wrong_fixed_value"],
                     "__target_field__": req.get("source_rule_id") or ""}
        scenarios.append({
            "name": f"Negative{prefix}{element_name} violates: must equal fixed value {fixed_value!r}",
            "requirement_id": req["id"],
            "type": "negative",
            "negative_class": _NEGATIVE_CLASSES["wrong_fixed_value"],
            "parameters": params_display,
            "target_field": req.get("source_rule_id") if address else None,
            "target_transaction": transaction or None,
            "source_rule_id": req.get("source_rule_id") or f"ENT-ELEM-{elem_no}",
            "related_entity_ids": [f"ENT-ELEM-{elem_no}"],
            "expected_response_code": RESPONSE_CODE_DECLINED,
            "expected_decline_code": _decline_code_for(element_name, _NEGATIVE_CLASSES["wrong_fixed_value"]),
            "oracle_authority": ORACLE_AUTHORITY_NOTE,
            "source": "auto_generated",
            "flags": list(_RESPONSE_CODE_UNAVAILABLE_FLAGS),
            "fingerprint": _fingerprint(fp_params, scope_label, transaction),
            "dedup_key": _dedup_key(fp_params, transaction),
        })
    return scenarios


def build_business_rule_scenarios(requirements: dict, kb: dict, saved_filter: dict | None,
                                   scope_label: str,
                                   param_path_covered_ids: set[str] | None = None) -> list[dict]:
    """Business-rule-derived path. Each qualifying Requirement (Step 5,
    derivation_method == 'business_rule_derived') becomes exactly one
    ASSERT-tier scenario. Unlike positive/negative, these aren't a
    combinatorial value domain — each is a single cross-field conditional
    fact ("if element X = value, then constraint Y applies") sourced from
    Step 4 deep extraction and confidence-gated at Requirement Derivation.
    Self-contained traceability (source_rule_id/source_page) travels with the
    scenario so Composer/Verification never need a requirement_catalog.json
    lookup for this type.

    Phase 4.2: `param_path_covered_ids` (requirement ids that already got a
    positive/negative parameter-path scenario this run) lets a python_template
    requirement ALSO fall back to an ASSERT-tier scenario here, but ONLY when it
    got nothing from the parameter path — an unconditional add would duplicate
    every python_template requirement that already has a positive/negative
    scenario (different dedup key, so it would never be caught as a duplicate)."""
    in_scope_segments = _in_scope_segment_numbers(saved_filter, kb)
    element_to_segments = _element_to_segments(kb)
    all_segments = {s.get("segment_number") for s in kb.get("segments", []) if s.get("segment_number")}
    # A filter covering every segment is a full-scope run, so unattributable rules are still valid there.
    full_scope = in_scope_segments is None or in_scope_segments >= all_segments
    elem_no_to_name = {a["element_number"]: _normalize_element_name(a["element_name"])
                        for a in kb.get("attributes", []) if a.get("element_number") and a.get("element_name")}
    scenarios = []
    skipped_unattributable = 0
    python_template_fallback_count = 0
    # RULE_METHODS is module-level (also used by run()'s requirement_skip_reasons
    # classifier) \u2014 every deterministic rule-shaped requirement becomes one ASSERT
    # scenario; anything not listed there is derived by Step 5 and then silently
    # dropped, which is what happened to the llm_phrased path before it was added.
    for req in requirements.get("requirements", []):
        method = req.get("derivation_method")
        if method in RULE_METHODS:
            rule_type = RULE_METHODS[method]
        elif (method == "python_template" and param_path_covered_ids is not None
                and req.get("id") not in param_path_covered_ids):
            rule_type = "field_constraint"
            python_template_fallback_count += 1
        else:
            continue
        # Phase 4.3 (/memories/session/plan.md) \u2014 shared subject_elem_no() (Phase 3.1)
        # replaces this builder's own ad-hoc condition_element_id/related_entity_ids/
        # source_rule_id resolution chain; same precedence, one implementation.
        elem_no = _subject_elem_no(req)
        if in_scope_segments is not None:
            if elem_no:
                if not (element_to_segments.get(elem_no, set()) & in_scope_segments):
                    continue
            elif not full_scope:
                # Previously these were emitted under EVERY scope, so each per-transaction
                # filter re-produced the identical prose-rule set.
                skipped_unattributable += 1
                continue

        # Parameters keyed by element_name (not condition_element_id) — same
        # convention as positive/negative scenarios, so TransactionResolver and
        # Composer's name_to_elem_no lookup resolve this scenario type too.
        params: dict[str, str] = {}
        element_name = elem_no_to_name.get(elem_no)
        if element_name and req.get("condition_value_parsed"):
            params[element_name] = req["condition_value_parsed"]

        fingerprint_params = dict(params)
        fingerprint_params["__source_rule_id__"] = req.get("source_rule_id") or ""
        # Phase 4.1 (/memories/session/plan.md) \u2014 two distinct requirements sharing one
        # source_rule_id with empty params (common) previously produced an IDENTICAL
        # dedup_key and silently collapsed to a single scenario; the requirement id makes
        # every requirement's scenario identity unique regardless of what it shares.
        fingerprint_params["__requirement_id__"] = req.get("id") or ""
        narrative = method == "llm_phrased"
        related = list(req.get("related_entity_ids") or [])
        # The subject element must travel with the scenario or TransactionResolver returns []
        # and Composer skips it (this is what cost all 97 narrative scenarios).
        if not any(str(r).startswith("ENT-ELEM-") for r in related) and elem_no:
            related.append(f"ENT-ELEM-{elem_no}")
        scenarios.append({
            "name": (f"Narrative Rule — {req.get('source_rule_id')}: {(req.get('statement') or '')[:120]}"
                     if narrative else
                     f"{rule_type.replace('_', ' ').title()} — {req.get('source_rule_id')}: "
                     f"{(req.get('statement') or '')[:120]}"),
            "requirement_id": req["id"],
            "type": rule_type,
            "parameters": params,
            "business_rule_assertion": req.get("constraint_text") or req.get("statement"),
            "related_entity_ids": related,
            "target_transaction": req.get("target_transaction"),
            "source_rule_id": req.get("source_rule_id"),
            "source_page": req.get("source_page"),
            "confidence_score": req.get("confidence_score"),
            "requires_review": req.get("requires_review", False),
            "flags": list(req.get("flags") or []),
            "expected_response_code": None,
            "expected_decline_code": None,
            "oracle_authority": ("step5 llm_phrased narrative processing_rules (LLM-phrased from "
                                 "Chapter 13 text — requires SME validation)" if narrative else
                                 "step4_deep_extraction business_rules (confidence-gated proxy for SME approval)"),
            "source": "auto_generated",
            "fingerprint": _fingerprint(fingerprint_params, scope_label),
            "dedup_key": _dedup_key(fingerprint_params),
        })
    if skipped_unattributable:
        print(f"  Scope filter: {skipped_unattributable} business-rule requirement(s) skipped \u2014 "
              "no element could be attributed to an in-scope segment")
    if python_template_fallback_count:
        print(f"  Business-rule path: {python_template_fallback_count} python_template requirement(s) "
              "with no parameter-path scenario got an ASSERT-tier fallback scenario instead")
    return scenarios


def _count_by_transaction(scenarios: list[dict]) -> dict[str, int]:
    counts: dict[str, int] = {}
    for s in scenarios:
        tx = s.get("target_transaction") or "(unattributed)"
        counts[tx] = counts.get(tx, 0) + 1
    return counts


def load_test_axes() -> dict:
    return _load_json(TEST_AXES_PATH)


TX_CODES_PATH = ROOT / "config" / "transaction_type_codes.json"


def _load_transaction_type_codes() -> set[str]:
    data = _load_json(TX_CODES_PATH)
    return {c["code"] for c in data.get("codes", []) if "code" in c}


# G6 — the five chains the client's 6-chain model declares INVALID, expressed
# structurally over what a flow step actually declares (transaction name +
# Prompt Code.TransactionType code + applicable_domains). This validator has
# no notion of "Sale" vs "Auth" as abstract concepts, only the codes and
# template names transaction_flows.json itself carries — so each rule below is
# written directly against those, not against a business vocabulary this file
# cannot see.
_COMPLETION_TX = "Purchase/Capture"
_SALE_CODE = "0"
_AUTH_CODE = "3"
_REFUND_CODE = "7"


def _invalid_chain_reasons(flow: dict) -> list[str]:
    reasons: list[str] = []
    steps = flow.get("steps") or []
    domains = set(flow.get("applicable_domains") or [])
    for i in range(len(steps) - 1):
        s1, s2 = steps[i], steps[i + 1]
        code1 = (s1.get("fixed_elements") or {}).get("Prompt Code.TransactionType")
        code2 = (s2.get("fixed_elements") or {}).get("Prompt Code.TransactionType")
        tx1, tx2 = s1.get("transaction"), s2.get("transaction")
        # A step whose role names it a retry/duplicate/retransmission of the PRIOR
        # leg (e.g. FLOW-AUTH-COMPLETION-DUPLICATE's "duplicate_completion",
        # FLOW-TOR-RETRY's retry attempts) is a deliberate idempotency/duplicate-
        # detection test, not the "invalid new chain" pattern the checks below
        # target — excluded from them by construction.
        role2 = str(s2.get("role") or "")
        is_repeat_leg = any(w in role2 for w in ("duplicate", "retransmission", "retry", "attempt"))
        if tx1 == "Financial Transaction Request" and tx2 == _COMPLETION_TX and not is_repeat_leg:
            if code1 == _SALE_CODE:
                reasons.append(f"step {s1.get('step')}->{s2.get('step')}: Sale (code {_SALE_CODE!r}) followed "
                               "by Completion is an invalid chain (client's 6-chain model)")
            if code1 == _REFUND_CODE:
                reasons.append(f"step {s1.get('step')}->{s2.get('step')}: Refund (code {_REFUND_CODE!r}) "
                               "followed by Completion is an invalid chain (client's 6-chain model)")
            if "EBT" in domains and code1 == _AUTH_CODE:
                reasons.append(f"step {s1.get('step')}->{s2.get('step')}: EBT Auth followed by Completion is "
                               "an invalid chain (EBT supports only Sale/Void/Balance Inquiry)")
        if tx1 == _COMPLETION_TX and tx2 == _COMPLETION_TX and not is_repeat_leg:
            reasons.append(f"step {s1.get('step')}->{s2.get('step')}: Completion followed by Completion is "
                           "an invalid chain (client's 6-chain model)")
        if code1 == _AUTH_CODE and code2 == _REFUND_CODE:
            reasons.append(f"step {s1.get('step')}->{s2.get('step')}: Auth (code {_AUTH_CODE!r}) followed by "
                           f"Refund (code {_REFUND_CODE!r}) is an invalid chain (client's 6-chain model)")
    return reasons


def validate_flows_structure(flows_config: dict, known_transactions: set[str] | None = None,
                              aliases: dict[str, str] | None = None,
                              overrides: dict[str, str] | None = None) -> list[str]:
    """G6 — hand-rolled structural validator (NB-3: jsonschema is not used
    anywhere in this repo). Returns a list of error strings; empty == valid.
    `known_transactions`/`aliases` are OPTIONAL — when omitted (e.g. load_flows()'s
    own no-argument call), the template-resolution check is skipped;
    build_flow_scenarios's own loud-skip already covers that at scenario-
    generation time, when the assembled template catalog is actually available."""
    errors: list[str] = []
    tx_codes = _load_transaction_type_codes()
    aliases = aliases or {}
    overrides = overrides or {}
    known_axes = set((flow_expansion_axes.load_axis_definitions().get("axes") or {}).keys())
    # Step 7 (2026-10 multi-leg axis coverage plan, P5) — entry_mode's own `class`
    # tags (config/test_axes.json), read once: a flow whose origin leg renders an
    # EMV template (name contains "EMV" — the same fact test_origin_step_renders_
    # the_emv_template already pins) must only ever combine chip_read/chip_fallback
    # entry modes (Segment 130, p.203/2026-3 p.206/2026-4, is required for all EMV
    # card transactions); every other flow must never combine one.
    entry_mode_classes = {v["value"]: v.get("class")
                          for a in (load_test_axes().get("axes") or []) if a.get("name") == "entry_mode"
                          for v in a.get("values") or []}
    chip_codes = {code for code, cls in entry_mode_classes.items() if cls in ("chip_read", "chip_fallback")}
    for flow in flows_config.get("flows") or []:
        fid = flow.get("id") or "<missing id>"
        steps = flow.get("steps") or []
        # BL-11 — a "flow" with fewer than 2 steps is not a chain; this must be a
        # loud structural error, never a silent drop.
        if len(steps) < 2:
            errors.append(f"{fid}: flow_has_fewer_than_two_steps (has {len(steps)})")
            continue
        step1 = steps[0]
        if not (step1.get("fixed_elements") or {}).get("Prompt Code.TransactionType"):
            errors.append(f"{fid}: step 1 does not declare a Prompt Code.TransactionType "
                          "(permanent regression guard for G1 — every chain's origin leg must pin its "
                          "transaction type)")
        for step in steps:
            step_no = step.get("step")
            if known_transactions is not None:
                resolved = resolve_transaction_name(step.get("transaction"), known_transactions, aliases, overrides)
                if resolved is None:
                    errors.append(f"{fid}: step {step_no} transaction {step.get('transaction')!r} "
                                  "does not resolve to any known template")
            for code in (step.get("fixed_elements") or {}).values():
                if code not in tx_codes:
                    errors.append(f"{fid}: step {step_no} fixed_elements code {code!r} is not a known "
                                  "code in transaction_type_codes.json")
            for link in step.get("links") or []:
                from_step = link.get("from_step")
                if from_step is None or step_no is None or from_step >= step_no:
                    errors.append(f"{fid}: step {step_no} link from_step={from_step!r} must be < its own step")
            if step_no != step1.get("step") and not (step.get("links") or step.get("expected_outcome_note")):
                errors.append(f"{fid}: step {step_no} (non-first) has neither a link nor an "
                              "expected_outcome_note")
        step_numbers = {str(s.get("step")) for s in steps}
        is_emv_flow = "EMV" in str(step1.get("transaction") or "")
        variant_ids: set = set()
        for variant in flow.get("variants") or []:
            vid = variant.get("id")
            if not vid:
                errors.append(f"{fid}: a variant is missing an id")
            elif vid in variant_ids:
                errors.append(f"{fid}: variant id {vid!r} is not unique within this flow")
            else:
                variant_ids.add(vid)
            max_cases = variant.get("max_cases")
            if max_cases is not None and (not isinstance(max_cases, int) or isinstance(max_cases, bool)
                                          or max_cases < 1):
                errors.append(f"{fid}: variant {vid!r} max_cases must be a positive integer, got {max_cases!r}")
            for axis_ref in variant.get("axes") or []:
                ref = axis_ref.get("ref")
                if not ref or ref not in known_axes:
                    errors.append(f"{fid}: variant {vid!r} references unknown axis {ref!r} — not declared "
                                  "in config/flow_expansion_axes.json")
                if ref == "entry_mode" and chip_codes:
                    included = set(axis_ref.get("include") or [])
                    excluded = set(axis_ref.get("exclude") or [])
                    effective = included if included else (set(entry_mode_classes) - excluded)
                    if is_emv_flow:
                        stray = effective - chip_codes
                        if stray:
                            errors.append(f"{fid}: variant {vid!r} is an EMV flow (origin leg renders an "
                                          f"EMV template) but would combine non-chip entry mode(s) "
                                          f"{sorted(stray)} — Segment 130 is required for all EMV card "
                                          "transactions")
                    else:
                        stray = effective & chip_codes
                        if stray:
                            errors.append(f"{fid}: variant {vid!r} is not an EMV flow but would combine "
                                          f"chip entry mode(s) {sorted(stray)}")
            for step_key in (variant.get("step_overrides") or {}):
                if str(step_key) not in step_numbers:
                    errors.append(f"{fid}: variant {vid!r} step_overrides key {step_key!r} does not "
                                  "address a real step of this flow")
        errors.extend(f"{fid}: {reason}" for reason in _invalid_chain_reasons(flow))
    return errors


def load_flows() -> dict:
    data = _load_json(FLOWS_PATH)
    errors = validate_flows_structure(data)
    if not errors:
        return data
    # forensic-review 08 H6 — validate_flows_structure() used to be fail-open: errors
    # were printed but every flow (including the structurally invalid ones) still built
    # scenarios. A flow named in an error is now excluded from THIS run, loudly, rather
    # than silently producing a scenario from a chain the validator itself rejected.
    invalid_ids = {e.split(":", 1)[0] for e in errors}
    shown = errors[:5]
    print(f"  FLOW CONFIG STRUCTURAL ERROR(S): {len(errors)} across {len(invalid_ids)} flow(s) — "
          f"excluded from this run: {shown}" + (" ..." if len(errors) > len(shown) else ""))
    data = dict(data)
    data["flows"] = [f for f in (data.get("flows") or []) if f.get("id") not in invalid_ids]
    return data


def _apply_step_overrides(steps: list[dict], step_overrides: dict) -> list[dict]:
    """Stage H3 — deep-copies `steps` and merges a variant's step_overrides onto
    the matching step's fixed_elements/links. Never mutates the loaded config;
    a flow with no variants must keep behaving exactly as before (BL-2)."""
    new_steps = copy.deepcopy(steps)
    for step in new_steps:
        override = (step_overrides or {}).get(str(step.get("step")))
        if override is None:
            override = (step_overrides or {}).get(step.get("step"))
        if not override:
            continue
        if "fixed_elements" in override:
            step["fixed_elements"] = {**(step.get("fixed_elements") or {}), **override["fixed_elements"]}
        if "links" in override:
            step["links"] = override["links"]
    return new_steps


def _cap_combos_coverage_aware(combos: list[dict[str, str]], max_cases: int) -> tuple[list[dict], int, str | None]:
    """Step 6 (2026-10 multi-leg axis coverage plan, P4) — the old cap sorted
    combinations alphabetically and truncated, which could silently drop an ENTIRE
    axis value (observed: capping a 4-brand x 3-entry-mode x 2-PIN set to 4 kept only
    2 of the 4 card brands, with the per-flow summary only ever printing a bare
    count, never which values were lost).

    Greedily keeps, in canonical-JSON order, whichever remaining combination
    introduces the most (axis, value) pairs not yet covered by an already-picked
    combination, then (tie) the most (axis=value, axis=value) PAIR-of-pairs not yet
    covered, then (tie) canonical-JSON order — so re-runs always keep the identical
    subset regardless of dict/set iteration order (same determinism contract the old
    cap had). Returns (picked_combos_in_canonical_order, capped_count,
    warning_naming_any_axis_value_that_could_not_fit_in_any_picked_combo_at_all) —
    the warning is None whenever max_cases is large enough to keep every declared
    value at least once, which is the normal/expected case."""
    if max_cases >= len(combos):
        return combos, 0, None
    ordered = sorted(combos, key=lambda c: json.dumps(c, sort_keys=True))
    covered_values: set[tuple[str, str]] = set()
    covered_pairs: set[tuple] = set()
    picked: list[dict] = []
    remaining = list(ordered)
    while remaining and len(picked) < max_cases:
        def _score(c: dict[str, str]):
            new_values = sum(1 for kv in c.items() if kv not in covered_values)
            items = sorted(c.items())
            new_pairs = sum(1 for i in range(len(items)) for j in range(i + 1, len(items))
                            if (items[i], items[j]) not in covered_pairs)
            return (-new_values, -new_pairs, json.dumps(c, sort_keys=True))
        remaining.sort(key=_score)
        best = remaining.pop(0)
        picked.append(best)
        covered_values.update(best.items())
        items = sorted(best.items())
        covered_pairs.update((items[i], items[j]) for i in range(len(items)) for j in range(i + 1, len(items)))

    all_values = {kv for c in ordered for kv in c.items()}
    dropped_by_axis: dict[str, list[str]] = {}
    for axis_name, value in sorted(all_values - covered_values):
        dropped_by_axis.setdefault(axis_name, []).append(value)
    warning = None
    if dropped_by_axis:
        detail = ", ".join(f"{k}={sorted(v)}" for k, v in sorted(dropped_by_axis.items()))
        warning = f"max_cases={max_cases} drops {detail}"

    picked_order_key = {json.dumps(c, sort_keys=True) for c in picked}
    picked_in_canonical_order = [c for c in ordered if json.dumps(c, sort_keys=True) in picked_order_key]
    return picked_in_canonical_order, len(combos) - len(picked), warning


def _expand_variant_axes(variant: dict, axes_config: dict) -> tuple[list[dict[str, str]], list[str], int]:
    """Stage H3 — resolves one flow variant's `axes[]` into concrete combinations.

    A variant with no axes at all (a pure step_overrides negative variant) still
    emits exactly one combination — an empty axis-value dict — rather than zero,
    since it needs no axis expansion to be a valid, distinct scenario.

    Combinations are built with the shared `combinatorics.pairwise` helper (the
    allpairspy adapter, never a raw cross product). Step 6 (2026-10 multi-leg axis
    coverage plan, P3) — the SAME test_axes.json `constraints` the verification
    matrix already enforces (build_verification_matrix_scenarios/
    _axis_violates_constraint) are now applied here too, BEFORE any cap: flow
    expansion used to be able to legally emit a spec-forbidden combination (e.g.
    Discover + entry mode '10' + any wallet, Appendix J fn2) that a correct system
    is REQUIRED to reject, which is worse than never generating it. A flow axis
    name is mapped to the test_axes.json axis name constraints are keyed by via its
    own declared `ref` (identical today for every test_axes-sourced flow axis).
    `max_cases` (P4) truncates via `_cap_combos_coverage_aware()` — deterministically
    keeps as many distinct axis values as the cap allows, warning (never silently)
    when it genuinely cannot. Returns (combos, warnings, capped_count) —
    forensic-review 08 M7: the caller's own capped_total counter had nothing feeding
    it until this returned the truncation delta explicitly.
    """
    axis_refs = variant.get("axes") or []
    if not axis_refs:
        return [{}], [], 0
    warnings: list[str] = []
    parameters = []
    ref_by_axis_name: dict[str, str] = {}
    for axis_ref in axis_refs:
        ref = axis_ref.get("ref")
        values, warning = flow_expansion_axes.resolve_ref(ref, axes_config=axes_config)
        if warning:
            warnings.append(f"{ref}: {warning}")
        include = axis_ref.get("include")
        if include:
            values = [v for v in values if v in include]
        exclude = set(axis_ref.get("exclude") or [])
        if exclude:
            values = [v for v in values if v not in exclude]
        if not values:
            warnings.append(f"{ref}: no values remain for this variant after include/exclude filtering")
            continue
        parameters.append({"element_name": ref, "values": values})
        axis_def = (axes_config.get("axes") or {}).get(ref) or {}
        if axis_def.get("source") == "test_axes":
            ref_by_axis_name[ref] = axis_def.get("ref", ref)
    if not parameters:
        return [], warnings, 0
    combos = pairwise(parameters)

    test_axes_constraints = (load_test_axes().get("constraints") or [])
    if test_axes_constraints and ref_by_axis_name:
        kept, excluded = [], 0
        for combo in combos:
            remapped = {ref_by_axis_name.get(k, k): v for k, v in combo.items()}
            if _axis_violates_constraint(remapped, test_axes_constraints):
                excluded += 1
                continue
            kept.append(combo)
        combos = kept
        if excluded:
            warnings.append(f"{excluded} combination(s) excluded by test_axes.json constraints")

    max_cases = variant.get("max_cases")
    capped = 0
    if max_cases and len(combos) > max_cases:
        combos, capped, cap_warning = _cap_combos_coverage_aware(combos, max_cases)
        if cap_warning:
            warnings.append(cap_warning)
    return combos, warnings, capped


def _flow_scenario_dict(flow: dict, steps: list[dict], step_txs: list[str], links: list[dict],
                         scope_label: str, variant: dict | None, axis_values: dict[str, str],
                         axis_element_map: dict[str, dict],
                         table_placement_map: dict[str, tuple[dict | None, str | None]] | None = None,
                         boundary: dict | None = None) -> dict:
    """Builds one flow scenario dict — shared by the no-variant path (BL-2: must
    stay byte-identical to the pre-H3 output) and the H3 variant-expansion path.

    Axis values are applied through mechanisms Composer already understands, so
    no new composer plumbing is required: the `field_profile` axis becomes the
    scenario's `inclusion_profile` (read by `resolve_inclusion_profile`), and
    every axis with a known carrying element (config/test_axes.json — card_brand,
    entry_mode, cvm, avs_result, wallet, wallet_method) is injected into
    `parameters` keyed by that element's NAME, exactly like
    `build_verification_matrix_scenarios` does, and is applied uniformly across
    every step by Composer's existing `_scenario_values_by_elem_no` conversion.
    Axes without a known carrying element (amount_profile, retry_depth,
    terminal_identifier_variant, authorization_code_variant) are recorded in
    `flow_axis_values`/`flow_axis_provenance` for traceability and dedup, but are
    not yet wired into field content — a disclosed scope limit, not a bug.

    Stage H5 — an axis whose `test_axes.json` entry declares a `placement` (e.g.
    entry_mode, wallet, wallet_method — values that live INSIDE a Variable
    Information table, not a plain top-level field) ALSO gets a `table_placements`
    entry, resolved via `table_placement_map` (see axis_table_placements()) the
    SAME way build_verification_matrix_scenarios() already does for single-message
    scenarios. `parameters` still carries the raw value too (unchanged) — Composer
    applies `table_placements` AFTER its generic field fill, the same order the
    single-message path already proves safe (compose_request() then
    _apply_table_placements()), so the table write always wins.

    `boundary` (fix 3, /memories/session/plan.md) — {element_name, max_bytes,
    requirement_id, source_rule_id} for a carried/linked field's at-limit length
    scenario (see flow_boundary_lengths() / the emission loop in
    build_flow_scenarios()). Bypasses the axis loop above entirely (axis_values is
    always {} for these) — a boundary target is a direct field/placeholder pair,
    never an axis reference."""
    fingerprint_params = {"flow": flow.get("id", ""), "steps": " -> ".join(step_txs)}
    variant_suffix = ""
    if variant is not None:
        fingerprint_params = {**fingerprint_params, "variant": variant.get("id", ""), "axis_values": axis_values}
        variant_suffix = f" [{variant.get('id')}]"

    parameters: dict[str, str] = {}
    table_placements: list[dict] = []
    inclusion_profile = None
    axis_provenance: dict[str, dict] = {}
    if "authorization_code_variant" in axis_values:
        # The authorization_code_variant branch below mutates a step's own
        # fixed_elements in place. `steps` (== build_flow_scenarios()'s
        # overridden_steps) is the SAME object shared by every combo of this one
        # variant — without this copy, combo 2's mutation clobbers combo 1's
        # already-built scenario (both still reference the same step dicts).
        steps = copy.deepcopy(steps)
    for axis_name, value in axis_values.items():
        if axis_name == "field_profile":
            inclusion_profile = value
            axis_provenance[axis_name] = {"source": "field_inclusion_profiles", "applied_as": "inclusion_profile"}
            continue
        if axis_name == "authorization_code_variant":
            # Stage H6 — overrides the ORIGIN step's fixed Transaction Type (always
            # declared as "3", POS Authorization Only) with the axis-resolved
            # alternate (today: "5" CAT, "B" Mail/Phone — transaction_type_codes.json,
            # BR-97-5 p.97). Mutates `steps` directly (always a per-variant deep copy
            # from _apply_step_overrides(), never the flow's own loaded config) —
            # the SAME list this scenario's own `flow_steps` is built from.
            target_step = next((s for s in steps if (s.get("fixed_elements") or {})
                                .get("Prompt Code.TransactionType") == "3"), None)
            if target_step is not None:
                target_step["fixed_elements"] = {**target_step["fixed_elements"],
                                                 "Prompt Code.TransactionType": value}
                axis_provenance[axis_name] = {"source": "transaction_type_codes", "applied_as": "fixed_elements",
                                              "step": target_step.get("step")}
            else:
                axis_provenance[axis_name] = {"source": "transaction_type_codes",
                                              "applied_as": "unresolved_no_matching_step"}
            continue
        if axis_name == "card_brand":
            # Step 5 (2026-10 multi-leg axis coverage plan, R1/R3) — card_brand is NEVER
            # placed as a plain field value: element 14 (Card Type) carries no field in
            # any assembled template (it's a 3-digit Appendix E code sent inside the
            # 4-character Prompt Code, never a standalone field — test_axes.json's own
            # card_brand description). Composer's select_card() reads this value
            # directly from this scenario's own flow_axis_values to pick the PAN's BIN
            # prefix (data_factory.py's _BRAND_PREFIXES) — recording it in `parameters`
            # pointed at a field that can never exist, which is what made every flow
            # test case with a non-default brand falsely INCOMPLETE_PARAMETER_NOT_PLACED
            # even though the composed PAN was correct.
            axis_provenance[axis_name] = {"source": "test_axes", "applied_as": "card_profile"}
            continue
        mapped = axis_element_map.get(axis_name)
        if mapped and mapped.get("element_name"):
            parameters[mapped["element_name"]] = value
            axis_provenance[axis_name] = {"source": "test_axes", "applied_as": "parameters",
                                           "element_name": mapped["element_name"]}
        else:
            axis_provenance[axis_name] = {"source": "unmapped", "applied_as": "informational_only"}
        resolved, reason = (table_placement_map or {}).get(axis_name, (None, None))
        if reason:
            axis_provenance[axis_name]["table_placement_warning"] = reason
        elif resolved:
            parameter_name = mapped["element_name"] if mapped and mapped.get("element_name") else axis_name
            table_placements.append({**resolved, "axis": axis_name, "value": value, "parameter": parameter_name})
            axis_provenance[axis_name]["applied_as"] = "table_placement"

    if boundary is not None:
        parameters[boundary["element_name"]] = FLOW_BOUNDARY_AT_LIMIT_PLACEHOLDER

    is_negative = bool((variant or {}).get("is_negative", flow.get("is_negative", False)))
    risk_weight = (variant or {}).get("risk_weight", flow.get("risk_weight"))
    flags = ["NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules"]
    if variant is not None and variant.get("expected_outcome_note"):
        flags.append(f"VARIANT_NOTE: {variant['expected_outcome_note']}")

    scenario = {
        "name": f"Flow — {flow.get('name')} ({' → '.join(step_txs)}){variant_suffix}",
        "requirement_id": None,
        "type": "flow",
        "is_negative": is_negative,
        "parameters": parameters,
        "table_placements": table_placements,
        "flow_id": flow.get("id"),
        "flow_steps": steps,
        "flow_risk_weight": risk_weight,
        # The first step's transaction, so resolution and prioritization still have a
        # single handle; the full ordered chain lives in flow_steps.
        "target_transaction": step_txs[0],
        "related_entity_ids": [],
        "source_rule_id": next((link.get("source_rule_id") for link in links if link.get("source_rule_id")), None),
        "source_page": next((link.get("source_page") for link in links if link.get("source_page")), None),
        "business_rule_assertion": "; ".join(link["rule"] for link in links if link.get("rule")),
        "expected_response_code": None,
        "expected_decline_code": None,
        "oracle_authority": ("config/transaction_flows.json declared flow; inter-step linking rules "
                             "are spec-cited, but per-step approval/decline outcome is processor "
                             "logic absent from the ATL105 spec"),
        "flags": flags,
        # forensic-review 08 C3(d) — distinct from plain "auto_generated" so the skill
        # validator's CONFIG_DECLARED_SOURCES recognizes a flow's legitimately null
        # requirement_id instead of flagging NULL_REQUIREMENT_ID on every flow scenario.
        "source": "auto_generated_flow",
        "fingerprint": _fingerprint(fingerprint_params, scope_label, step_txs[0]),
        "dedup_key": _dedup_key(fingerprint_params, step_txs[0]),
    }
    if inclusion_profile:
        scenario["inclusion_profile"] = inclusion_profile
    if variant is not None:
        scenario["flow_variant_id"] = variant.get("id")
        scenario["flow_variant_name"] = variant.get("name")
        scenario["flow_axis_values"] = axis_values
        scenario["flow_axis_provenance"] = axis_provenance
    if boundary is not None:
        # The length requirement is this scenario's real citation — supersedes the
        # carry link's own source_rule_id, which documents the CARRY obligation, not
        # the length rule this scenario actually exercises.
        scenario["requirement_id"] = boundary.get("requirement_id")
        scenario["requirement_ids"] = [boundary["requirement_id"]] if boundary.get("requirement_id") else []
        scenario["source_rule_id"] = boundary.get("source_rule_id")
        scenario["flow_boundary_element"] = boundary["element_name"]
        scenario["flow_boundary_max_bytes"] = boundary["max_bytes"]
    return scenario


def build_flow_scenarios(flows_config: dict, known_transactions: set[str], scope_label: str,
                          transaction_aliases: dict[str, str] | None = None,
                          domain_filter: set[str] | None = None,
                          transaction_overrides: dict[str, str] | None = None,
                          table_entities: list[dict] | None = None,
                          boundary_lengths: dict[str, dict] | None = None) -> list[dict]:
    """Phase D1 — one scenario per declared chained flow.

    Every other scenario type describes a single message. A flow scenario describes
    an ORDERED sequence plus the data that must carry between steps (Sequence Number
    identity, amount relationships), which is what the client's suite is built on and
    what single-message conformance testing cannot exercise at all.

    A flow is skipped, loudly, when the assembled catalog has no template for one of
    its steps — emitting a partial chain would silently test something other than the
    flow it claims to be. A step's declared `transaction` name is resolved through
    `transaction_aliases` first (assembler-recorded merges, e.g. 'Financial Transaction
    Request' -> 'FINANCIAL_TRANSACTION_REQUEST') before being judged missing.

    `domain_filter` (Stage G4, optional — None means no filtering, every existing
    caller's behavior is unchanged): when given, a flow whose `applicable_domains`
    does not intersect it is skipped loudly too. A flow with `applicable_domains: null`
    (UNRESOLVED_KB_GAP — not yet grounded, NOT "applies to no domain") is never
    silently dropped by a domain filter; it is skipped loudly with its own reason so
    the gap stays visible instead of looking like a real domain mismatch.

    `table_entities` (Stage H5, optional — None/omitted keeps every pre-H5 caller's
    behavior unchanged, i.e. no axis ever resolves a table placement) — the same
    Appendix I table catalog build_verification_matrix_scenarios() already reads,
    needed so a flow variant's entry_mode/wallet/wallet_method axis can resolve
    its own Variable Information Table placement (axis_table_placements()).

    `boundary_lengths` (fix 3, /memories/session/plan.md, optional — None/omitted
    keeps every pre-fix-3 caller's behavior unchanged, i.e. no boundary scenario is
    ever emitted) — see flow_boundary_lengths(). One scenario is emitted per (flow,
    eligible carried/linked element), using the flow's own base `steps` (never a
    variant's step_overrides, and never for a flow marked `is_negative` — a
    length-boundary case is a positive, valid-value test).

    Stage H3: a flow may declare an optional `variants[]` array (see
    config/flow_expansion_axes.json). A flow with NO variants key emits exactly the
    one scenario it always did, with a fingerprint_params dict byte-identical to
    the pre-H3 shape (BL-2). A flow WITH variants emits one scenario per resolved
    axis combination per variant (pairwise-combined, deterministically capped at
    the variant's max_cases), each carrying a distinct fingerprint/dedup_key so
    variants of the same flow never collapse into each other via dedup.
    """
    aliases = transaction_aliases or {}
    overrides = transaction_overrides or {}
    scenarios: list[dict] = []
    skipped: list[str] = []
    axes_config = flow_expansion_axes.load_axis_definitions()
    axis_element_map = flow_expansion_axes.axis_element_map(axes_config)
    tables_by_id = {t.get("entity_id"): t for t in (table_entities or [])}
    table_placement_map = axis_table_placements(axes_config, tables_by_id)
    for flow in flows_config.get("flows") or []:
        steps = flow.get("steps") or []
        if len(steps) < 2:
            # BL-11 — a loud skip, never a silent continue: emitting nothing for a
            # mis-declared single-step "flow" must be as visible as any other skip.
            skipped.append(f"{flow.get('id')} (flow_has_fewer_than_two_steps: has {len(steps)})")
            continue
        missing = [s["transaction"] for s in steps
                   if known_transactions
                   and resolve_transaction_name(s.get("transaction"), known_transactions, aliases, overrides) is None]
        if missing:
            skipped.append(f"{flow.get('id')} (no template for: {', '.join(sorted(set(missing)))})")
            continue
        if domain_filter is not None:
            applicable = flow.get("applicable_domains")
            if applicable is None:
                skipped.append(f"{flow.get('id')} (applicable_domains is an UNRESOLVED_KB_GAP, "
                               "not filterable by domain)")
                continue
            if not (set(applicable) & domain_filter):
                skipped.append(f"{flow.get('id')} (applicable_domains {applicable} does not "
                               f"intersect requested domain(s) {sorted(domain_filter)})")
                continue

        if boundary_lengths and not flow.get("is_negative"):
            boundary_step_txs = [s.get("transaction") for s in steps]
            boundary_links = [dict(link, step=s.get("step")) for s in steps for link in (s.get("links") or [])]
            eligible_elements = sorted({
                link["element_name"] for link in boundary_links
                if link.get("type") in FLOW_BOUNDARY_ELIGIBLE_LINK_TYPES
                and link.get("element_name") in boundary_lengths
            })
            for element_name in eligible_elements:
                info = boundary_lengths[element_name]
                slug = re.sub(r"[^a-z0-9]+", "-", element_name.lower()).strip("-")
                boundary_variant = {"id": f"boundary-at-limit-{slug}",
                                     "name": f"Boundary — {element_name} at max length ({info['max_bytes']} bytes)"}
                scenarios.append(_flow_scenario_dict(flow, steps, boundary_step_txs, boundary_links, scope_label,
                                                      boundary_variant, {}, axis_element_map, table_placement_map,
                                                      boundary={"element_name": element_name, **info}))
            if eligible_elements:
                print(f"    Flow {flow.get('id')}: {len(eligible_elements)} boundary scenario(s) "
                     f"({', '.join(eligible_elements)})")

        variants = flow.get("variants") or []
        if not variants:
            step_txs = [s.get("transaction") for s in steps]
            links = [dict(link, step=s.get("step")) for s in steps for link in (s.get("links") or [])]
            scenarios.append(_flow_scenario_dict(flow, steps, step_txs, links, scope_label,
                                                  None, {}, axis_element_map, table_placement_map))
            continue

        emitted = 0
        capped_total = 0
        flow_warnings: list[str] = []
        for variant in variants:
            combos, warnings, capped = _expand_variant_axes(variant, axes_config)
            flow_warnings.extend(warnings)
            capped_total += capped
            if not combos:
                continue
            overridden_steps = _apply_step_overrides(steps, variant.get("step_overrides") or {})
            variant_step_txs = [s.get("transaction") for s in overridden_steps]
            variant_links = [dict(link, step=s.get("step"))
                              for s in overridden_steps for link in (s.get("links") or [])]
            for combo in combos:
                scenarios.append(_flow_scenario_dict(flow, overridden_steps, variant_step_txs, variant_links,
                                                      scope_label, variant, combo, axis_element_map,
                                                      table_placement_map))
                emitted += 1
        summary = f"    Flow {flow.get('id')}: {len(variants)} variant(s) declared, {emitted} combination(s) emitted"
        if capped_total:
            summary += f", {capped_total} capped"
        if flow_warnings:
            summary += f" — axis warnings: {'; '.join(flow_warnings)}"
        print(summary)
    if skipped:
        print(f"    Flow builder: {len(skipped)} flow(s) skipped — {'; '.join(skipped)}")
    return scenarios


def build_library_override_scenarios(library: dict, known_transactions: set[str], scope_label: str,
                                      transaction_aliases: dict[str, str] | None = None,
                                      transaction_overrides: dict[str, str] | None = None) -> list[dict]:
    """Client-supplied literal invalid values (config/test_data/atl105_test_data.json
    overrides.invalid[]) become their own negative scenarios here — deliberately kept
    separate from build_negative_scenarios' own attribute-driven synthesis above, so a
    client-chosen violation is never conflated with one this pipeline invented.

    Requires an explicit transactionScope on the library entry: a scope-less entry
    could apply to any transaction using that segment, and guessing which one would
    be ungrounded — skipped (loudly, not silently) instead.

    Stage I3: a scope name is resolved through `transaction_aliases` (the same
    merge-table alias map build_flow_scenarios uses) before being judged unknown —
    a raw `tx not in known_transactions` check here bypassed the alias map and
    silently skipped every scope name that only matched via a merge alias.
    """
    aliases = transaction_aliases or {}
    overrides = transaction_overrides or {}
    scenarios = []
    skipped_no_scope = 0
    skipped_unknown_tx: dict[str, int] = {}
    for segment_entries in library.values():
        for element_name, entry in segment_entries.items():
            overrides_entry = (entry.get("overrides") or {}).get("invalid") or []
            if not overrides_entry:
                continue
            scope = entry.get("transactionScope")
            if not scope:
                skipped_no_scope += len(overrides_entry)
                continue
            for tx in scope:
                resolved_tx = resolve_transaction_name(tx, known_transactions, aliases, overrides)
                if resolved_tx is None:
                    skipped_unknown_tx[tx] = skipped_unknown_tx.get(tx, 0) + len(overrides_entry)
                    continue
                for override in overrides_entry:
                    override_id = override.get("id")
                    if not override_id:
                        continue
                    params = {element_name: f"<library_override_invalid:{override_id}>"}
                    scenarios.append({
                        "name": f"Negative — {resolved_tx} — {element_name} violates (client-supplied): "
                                f"{override.get('violates', '')}",
                        "requirement_id": None,
                        "type": "negative",
                        "negative_class": _NEGATIVE_CLASSES["client_supplied_override"],
                        "parameters": params,
                        "target_transaction": resolved_tx,
                        "expected_response_code": RESPONSE_CODE_DECLINED,
                        "expected_decline_code": None,
                        "source_rule_id": None,
                        "source_page": entry.get("sourcePage"),
                        "oracle_authority": ("config/test_data/atl105_test_data.json overrides.invalid — "
                                              "client-supplied violation, not platform-synthesized; the "
                                              "processor/SME still confirms the actual decline outcome"),
                        "flags": ["CLIENT_SUPPLIED_OVERRIDE: violation value and reason came from the "
                                  "test-data library, not generated by this pipeline"] + _RESPONSE_CODE_UNAVAILABLE_FLAGS,
                        "source": "client_supplied_override",
                        # forensic-review 08 H7 — must use resolved_tx (what target_transaction
                        # carries), not the raw unresolved `tx`: two scope aliases of one real
                        # transaction previously fingerprinted as two non-colliding duplicates.
                        "fingerprint": _fingerprint(params, scope_label, resolved_tx),
                        "dedup_key": _dedup_key(params, resolved_tx),
                    })
    if skipped_no_scope:
        print(f"    Library Override builder: {skipped_no_scope} override(s) skipped — "
              "no transactionScope declared, cannot ground which transaction they apply to")
    if skipped_unknown_tx:
        detail = "; ".join(f"{tx!r} ({n})" for tx, n in skipped_unknown_tx.items())
        print(f"    Library Override builder: transactionScope names not found among assembled "
              f"transactions, override(s) skipped: {detail}")
    return scenarios


def _axis_violates_constraint(combo: dict[str, str], constraints: list[dict]) -> dict | None:
    """Returns the first constraint this combination violates, or None.

    Without this the matrix emits combinations the spec explicitly forbids (e.g.
    Discover + entry mode '10'), which is worse than emitting nothing — it would
    produce test cases a correct system is *required* to reject.
    """
    for constraint in constraints:
        forbid = constraint.get("forbid") or {}
        # '*' = the axis is present with any value (e.g. any wallet identifier).
        if forbid and all(combo.get(axis) is not None if value == "*" else combo.get(axis) == value
                          for axis, value in forbid.items()):
            return constraint
    return None


_FIXED_LEN_RE = re.compile(r"Fixed Len\.?:\s*(\d+)", re.IGNORECASE)


def resolve_table_placement(placement: dict | None, tables_by_id: dict) -> tuple[dict | None, str | None]:
    """An axis carried inside a Variable Information table (e.g. Wallet Identifier = Appendix I
    Table 032, Sub-Table 07) -> its Table ID, Sub-Table ID and fixed Sub-Table Length, read from
    the Appendix I table catalog. Returns (placement, None) or (None, reason); never guesses."""
    if not placement:
        return None, None
    table = tables_by_id.get(placement.get("table_entity") or "")
    if not table or not table.get("table_id_value"):
        return None, f"table entity {placement.get('table_entity')!r} not in the table catalog"
    resolved = {"segment": str(placement.get("segment") or ""), "table_id": table["table_id_value"],
                "table_entity": table["entity_id"], "source_pages": [(table.get("source") or {}).get("page")]}
    sub_entity = placement.get("sub_table_entity")
    if sub_entity:
        sub = tables_by_id.get(sub_entity)
        fields = sorted((sub or {}).get("fields", []), key=lambda f: str(f.get("field_no")))
        sub_id = extract_fixed_value(fields[0].get("description_summary") or "") if fields else None
        sub_len = _FIXED_LEN_RE.search(fields[1].get("description_summary") or "") if len(fields) > 1 else None
        if not sub_id or not sub_len:
            return None, f"sub-table {sub_entity!r} has no documented fixed Sub-Table ID and Length"
        resolved.update({"sub_table_id": sub_id, "sub_table_length": sub_len.group(1),
                         "sub_table_entity": sub_entity})
        resolved["source_pages"].append((sub.get("source") or {}).get("page"))
    positions = str(placement.get("positions") or "")
    if positions:
        m = re.fullmatch(r"(\d+)(?:-(\d+))?", positions)
        data_field = next((f for f in table.get("fields", [])
                           if (f.get("raw_field_name") or "").strip().lower() == "table data"), None)
        data_len = _FIXED_LEN_RE.search((data_field or {}).get("description_summary") or "")
        if not m or not data_len:
            return None, (f"positions {positions!r} need a documented fixed Table Data length in "
                          f"{table['entity_id']!r}")
        start, end = int(m.group(1)), int(m.group(2) or m.group(1))
        if not 1 <= start <= end <= int(data_len.group(1)):
            return None, f"positions {positions!r} fall outside Table Data length {data_len.group(1)}"
        resolved.update({"positions": [start, end], "table_data_length": int(data_len.group(1))})
    return resolved, None


def axis_table_placements(axes_config: dict, tables_by_id: dict) -> dict[str, tuple[dict | None, str | None]]:
    """Stage H5 — resolves every test_axes-sourced flow-expansion axis's own declared
    `placement` (Appendix I Variable Information table membership) via the SAME
    resolve_table_placement() build_verification_matrix_scenarios() already uses,
    keyed by the flow_expansion_axes.json axis name. An axis with no declared
    placement (a plain top-level field, e.g. card_brand) is simply absent here —
    _flow_scenario_dict() falls back to its existing plain-parameters path for
    those; this never guesses a placement for an axis that doesn't declare one."""
    test_axes_data = _load_json(TEST_AXES_PATH)
    by_name = {a.get("name"): a for a in test_axes_data.get("axes", [])}
    result: dict[str, tuple[dict | None, str | None]] = {}
    for axis_name, axis_def in (axes_config.get("axes") or {}).items():
        if axis_def.get("source") != "test_axes":
            continue
        test_axis = by_name.get(axis_def.get("ref"))
        placement = (test_axis or {}).get("placement")
        if placement:
            result[axis_name] = resolve_table_placement(placement, tables_by_id)
    return result


def build_verification_matrix_scenarios(axes_config: dict, transaction: str, allowed_elements: set[str] | None,
                                         scope_label: str, table_entities: list[dict] | None = None,
                                         emv: bool = False) -> list[dict]:
    """Phase C2 — joint combinations across the business axes the client actually
    varies (card brand x entry mode x CVM x AVS x wallet).

    The generic pairwise path cannot produce these: it only combines elements that
    reached the requirement catalog as a 'must be one of' enum domain, and a scan of
    all 6,291 requirements found such a domain for only 2 of these axes. The rest
    live in prose business rules, declared in config/test_axes.json with their source
    pages. Each scenario names its transaction, so it resolves directly.

    `emv` (Step 7, 2026-10 multi-leg axis coverage plan, P5) — whether `transaction`'s
    own assembled template carries Segment 130 (EMV Request Data Segment, "required
    for all EMV card transactions" and "the only segment required for all EMV
    financial transactions", p.203/2026-3, p.206/2026-4). Gates `entry_mode`'s own
    per-value `class` (chip_read/chip_fallback/non_chip, config/test_axes.json):
    an EMV transaction only combines chip_read+chip_fallback codes; a non-EMV one
    only non_chip codes. A code with no class (today: '04' — the spec table merges
    two senses on one line, CI-038) is never combined into either set — never
    guessed. Before this fix the matrix paired chip-read codes with non-EMV
    templates and swipe/keyed codes with EMV templates with no awareness of either.
    """
    axes = axes_config.get("axes") or []
    constraints = axes_config.get("constraints") or []
    if not axes:
        return []
    entry_mode_classes = {"chip_read", "chip_fallback"} if emv else {"non_chip"}

    applicable = []
    for axis in axes:
        if axis.get("side") == "response":
            continue  # returned by BUYPASS, not sent by the device; checked on the response instead
        elem_no = str(axis.get("element_no") or "")
        # Step 5 (2026-10 multi-leg axis coverage plan, R3) — `applies_via_element`
        # (e.g. card_brand -> "2", Account Number) names the element whose PRESENCE in
        # this transaction actually gates whether the axis is meaningful here, when
        # that differs from the axis's own citation element (card_brand cites element
        # 14, Card Type, for provenance only — no template carries a field for it, so
        # gating on element 14 silently excluded card_brand from every matrix).
        gate_elem_no = str(axis.get("applies_via_element") or elem_no)
        if allowed_elements is not None and gate_elem_no and gate_elem_no not in allowed_elements:
            continue
        values = [v for v in (axis.get("values") or []) if v.get("value") is not None]
        if axis.get("name") == "entry_mode" and any(v.get("class") for v in values):
            # Only filter when the config actually declares classes — an older/test
            # fixture with no `class` tags at all keeps its pre-Step-7 behavior
            # (every declared value applicable), never silently dropping to zero.
            values = [v for v in values if v.get("class") in entry_mode_classes]
        if len(values) < MIN_ENUM_VALUES:
            continue
        applicable.append({
            "element_no": elem_no,
            "element_name": axis["name"],
            "requirement_id": None,
            "values": [v["value"] for v in values],
            "axis": axis,
        })
    if len(applicable) < 2:
        return []  # a "matrix" of one axis is just the existing positive path

    labels = {a["name"]: {v["value"]: v.get("label", v["value"]) for v in (a.get("values") or [])}
              for a in axes}
    provenance = {a["name"]: {v["value"]: {"source_rule_id": v.get("source_rule_id"),
                                            "source_page": v.get("source_page")}
                               for v in (a.get("values") or [])} for a in axes}
    axis_elements = {a["name"]: {"element_no": a.get("element_no"),
                                  "element_name": a.get("element_name"),
                                  "table_id": a.get("table_id")} for a in axes}
    # Step 5 (R1/R3) — an axis declaring applies_via_element is never placed as a
    # plain field named element_name (that field doesn't exist in any template);
    # it's applied through whatever reads axis_values directly (card_brand ->
    # composer.select_card()). Putting it in `params` anyway would make Composer's
    # own unplaced-parameter check flag it NOT_PLACED even when correctly applied.
    applied_via_other_mechanism = {a["name"] for a in axes if a.get("applies_via_element")}
    tables_by_id = {t.get("entity_id"): t for t in (table_entities or [])}
    placements = {a["name"]: resolve_table_placement(a.get("placement"), tables_by_id) for a in axes}

    scenarios: list[dict] = []
    excluded = 0
    for combo in pairwise(applicable):
        violated = _axis_violates_constraint(combo, constraints)
        if violated:
            excluded += 1
            continue
        # Parameters are keyed by the carrying ELEMENT name, not the axis name, so
        # Composer's name_to_elem_no lookup and TransactionResolver resolve them.
        params: dict[str, str] = {}
        for axis_name, value in combo.items():
            if axis_name in applied_via_other_mechanism:
                continue
            element_name = axis_elements.get(axis_name, {}).get("element_name")
            if element_name:
                params[element_name] = value
        readable = ", ".join(f"{k}={labels.get(k, {}).get(v, v)}" for k, v in sorted(combo.items()))
        fingerprint_params = {f"axis:{k}": v for k, v in combo.items()}
        table_placements, placement_flags = [], []
        for axis_name, value in sorted(combo.items()):
            resolved, reason = placements.get(axis_name, (None, None))
            if reason:
                placement_flags.append(f"AXIS_PLACEMENT_UNRESOLVED: {axis_name}: {reason}")
            elif resolved and resolved.get("sub_table_length") and len(value) != int(resolved["sub_table_length"]):
                placement_flags.append(f"AXIS_PLACEMENT_UNRESOLVED: {axis_name}: value {value!r} does not fit "
                                       f"the documented Sub-Table Length {resolved['sub_table_length']}")
            elif resolved and resolved.get("positions") and \
                    len(value) != resolved["positions"][1] - resolved["positions"][0] + 1:
                placement_flags.append(f"AXIS_PLACEMENT_UNRESOLVED: {axis_name}: value {value!r} does not fit "
                                       f"positions {resolved['positions']}")
            elif resolved:
                table_placements.append({**resolved, "axis": axis_name, "value": value,
                                         "parameter": axis_elements[axis_name]["element_name"]})
        scenarios.append({
            "name": f"Verification Matrix — {transaction} — {readable}",
            "requirement_id": None,
            "type": "verification_matrix",
            "parameters": params,
            "table_placements": table_placements,
            "axis_values": combo,
            "axis_labels": {k: labels.get(k, {}).get(v, v) for k, v in combo.items()},
            "axis_provenance": {k: provenance.get(k, {}).get(v) for k, v in combo.items()},
            "source_page": next((provenance.get(k, {}).get(v, {}).get("source_page")
                                 for k, v in sorted(combo.items())
                                 if provenance.get(k, {}).get(v, {}).get("source_page")), None),
            "target_transaction": transaction or None,
            "related_entity_ids": sorted({f"ENT-ELEM-{axis_elements[k]['element_no']}"
                                           for k in combo if axis_elements.get(k, {}).get("element_no")
                                           and k not in applied_via_other_mechanism}),
            "expected_response_code": None,
            "expected_decline_code": None,
            # These axes decide approval/decline through BUYPASS business logic, which is
            # not in ATL105 — asserting an outcome here would be inventing one.
            "oracle_authority": ("config/test_axes.json declared axes (spec-page-cited values); "
                                 "outcome NOT asserted — AVS/CVM/wallet approval behavior is "
                                 "processor logic absent from the ATL105 spec"),
            "flags": ["NEEDS_AUTHORIZER_DOC: matrix outcome requires processor business rules"] + placement_flags,
            # forensic-review 08 C3(d) — see the flow builder's matching comment above.
            "source": "auto_generated_matrix",
            "fingerprint": _fingerprint(fingerprint_params, scope_label, transaction),
            "dedup_key": _dedup_key(fingerprint_params, transaction),
        })
    if excluded:
        print(f"    Axis constraint filter: {excluded} spec-forbidden combination(s) excluded for {transaction}")
    return scenarios


def classify_requirement_skip_reasons(requirements: dict, kb: dict, saved_filter: dict | None,
                                       covered_requirement_ids: set[str],
                                       dedup_collapsed_ids: set[str] = frozenset()) -> dict[str, list[str]]:
    """Phase 3.2 (/memories/session/plan.md) \u2014 diagnosability at generation time, mirroring
    composer.py's composer_skips.json: WHICH approved requirement ids produced zero
    scenarios this run, grouped by the cheapest-to-verify shared reason. This is a
    best-effort classifier, not a re-derivation of every builder's own regex/pattern
    logic (several run independently over the same requirement) \u2014 anything that passes
    every shared precondition but still produced nothing is honestly bucketed as
    "no_matching_scenario_pattern" rather than guessing which specific builder rejected it.

    Phase 4.1: `dedup_collapsed_ids` are requirement ids that DID produce a scenario this
    run, but that scenario collided (via dedup_key) with an already-approved one from a
    prior run and was dropped by the Fingerprint Check \u2014 checked first since the reason
    is already known precisely, rather than falling through to a derivation-method guess."""
    in_scope_segments = _in_scope_segment_numbers(saved_filter, kb)
    element_to_segments = _element_to_segments(kb) if in_scope_segments is not None else {}
    skip_reasons: dict[str, list[str]] = {}
    for req in requirements.get("requirements", []):
        rid = req.get("id")
        if not rid or rid in covered_requirement_ids:
            continue
        if rid in dedup_collapsed_ids:
            skip_reasons.setdefault("dedup_collapsed", []).append(rid)
            continue
        method = req.get("derivation_method")
        elem_no = _subject_elem_no(req) or (req.get("condition_element_id") or "").removeprefix("ENT-ELEM-")
        if method not in PARAMETER_DERIVATION_METHODS and method not in RULE_METHODS:
            reason = f"unsupported_derivation_method:{method}"
        elif not elem_no:
            reason = "no_subject_element_id"
        elif in_scope_segments is not None and not (element_to_segments.get(elem_no, set()) & in_scope_segments):
            reason = "outside_scope_filter"
        else:
            reason = "no_matching_scenario_pattern"
        skip_reasons.setdefault(reason, []).append(rid)
    return skip_reasons


JUDGE_CHECKPOINT_PATH = Path(__file__).parent / "candidates" / ".judge_checkpoint.json"
# Scenarios per outer chunk between checkpoint saves — bounds how much re-judging an
# interrupted pass (e.g. provider budget cap) loses, instead of losing the whole pass.
JUDGE_CHECKPOINT_CHUNK_SIZE = 500


def _judge_checkpoint_epoch(grounding_skill: str | None) -> str:
    """forensic-review V1 NEW-1 — a cached verdict is only trustworthy as long as
    nothing that could change it has changed since it was judged: the judge
    prompt/schema (llm_enricher.PROMPT_VERSION), which grounding skill text was
    appended, or which spec revision is active. Unlike step1's per-field content_hash
    (M5), this checkpoint is keyed by scenario fingerprint only, with no per-item
    content signal to invalidate individually — so a single epoch mismatch discards
    the WHOLE checkpoint instead of silently reusing a verdict that may no longer be
    correct (the exact scenario a prompt/spec change mid-incident could hit)."""
    key = f"{PROMPT_VERSION}|{grounding_skill or ''}|{SPEC_VERSION or ''}"
    return hashlib.sha256(key.encode("utf-8")).hexdigest()


def _load_judge_checkpoint(epoch: str) -> dict:
    if not JUDGE_CHECKPOINT_PATH.exists():
        return {}
    try:
        data = json.loads(JUDGE_CHECKPOINT_PATH.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError):
        return {}
    if not isinstance(data, dict) or data.get("_epoch") != epoch:
        if data:
            print("  [Judge Checkpoint] prompt/schema or spec version changed since the last "
                  "interrupted run — discarding the stale checkpoint instead of reusing its verdicts")
        return {}
    return data.get("entries") or {}


def _save_judge_checkpoint(checkpoint: dict, epoch: str) -> None:
    JUDGE_CHECKPOINT_PATH.parent.mkdir(parents=True, exist_ok=True)
    JUDGE_CHECKPOINT_PATH.write_text(
        json.dumps({"_epoch": epoch, "entries": checkpoint}, indent=2, ensure_ascii=False), encoding="utf-8")


def _judge_constructed_resumable(constructed: list[dict], label_fields: list[str], page_resolver,
                                  pages_dir: Path, grounding_skill: str | None,
                                  multi_page_resolver=None) -> list[dict]:
    """judge_with_traceback over `constructed`, keyed/checkpointed by each scenario's own
    `fingerprint` (already unique by the time Fingerprint Check runs, earlier in run()) and
    persisted to disk every JUDGE_CHECKPOINT_CHUNK_SIZE items. judge_with_traceback itself
    holds results only in-memory until generator.run() writes OUT_FILE at the very end, so a
    budget-cap 429 storm or crash mid-pass previously lost the ENTIRE judge pass — this
    resumes only the unjudged remainder on the next run instead."""
    epoch = _judge_checkpoint_epoch(grounding_skill)
    checkpoint = _load_judge_checkpoint(epoch)
    to_judge = []
    for item in constructed:
        fp = item.get("fingerprint")
        cached = checkpoint.get(fp) if fp else None
        if cached:
            item["llm_verdict"] = cached["llm_verdict"]
            if cached.get("review_package"):
                item["review_package"] = cached["review_package"]
            if cached.get("added_flags"):
                item.setdefault("flags", []).extend(cached["added_flags"])
        else:
            to_judge.append(item)
    if checkpoint:
        print(f"  [Judge Checkpoint] Resuming: {len(constructed) - len(to_judge)}/{len(constructed)} "
              f"already judged in a prior interrupted run, {len(to_judge)} remaining")
    for start in range(0, len(to_judge), JUDGE_CHECKPOINT_CHUNK_SIZE):
        chunk = to_judge[start:start + JUDGE_CHECKPOINT_CHUNK_SIZE]
        pre_flag_len = {id(item): len(item.get("flags", [])) for item in chunk}
        judge_with_traceback(chunk, label_fields=label_fields, page_resolver=page_resolver,
                              pages_dir=pages_dir, grounding_skill=grounding_skill,
                              multi_page_resolver=multi_page_resolver)
        for item in chunk:
            fp = item.get("fingerprint")
            if not fp:
                continue
            added_flags = item.get("flags", [])[pre_flag_len[id(item)]:]
            checkpoint[fp] = {
                "llm_verdict": item.get("llm_verdict"),
                "review_package": item.get("review_package"),
                "added_flags": added_flags,
            }
        _save_judge_checkpoint(checkpoint, epoch)
        print(f"  [Judge Checkpoint] Saved progress: {len(checkpoint)}/{len(constructed)} scenario(s) judged so far")
    return constructed


class MissingInputError(RuntimeError):
    """Raised by run() when a required upstream artifact (approved requirements or the
    knowledge base) is missing (H1) — a real exception, not sys.exit(), so
    stage_timing.track() (which catches Exception, not SystemExit) still records
    ok=False; __main__ converts this to a non-zero exit for the orchestrator. A CORRUPT
    (present but unparsable) file instead raises atomic_io.CorruptArtifactError directly
    from load_requirements()/load_knowledge_base(), never silently degrading to {}."""


def _rule_derived_llm_skip_flag(scenario_type: str) -> str:
    """The LLM_JUDGE_NOT_APPLIED disclosure for a non-constructed (not positive/negative)
    scenario type. forensic-review 08 M4 — a single "restates a Step5 requirement
    verbatim" message was stamped on every type in this bucket, which is false for
    "boundary" (a synthesized at-limit placeholder, not requirement prose) and "flow"
    (built from transaction_flows.json config, no Step5 statement involved at all)."""
    if scenario_type == "verification_matrix":
        return ("LLM_JUDGE_NOT_APPLIED: combination of declared config/test_axes.json values; "
                 "each value is already page-cited, so there is no free-text claim to verify")
    if scenario_type == "boundary":
        return ("LLM_JUDGE_NOT_APPLIED: synthesized at-limit placeholder from a Step5 length "
                 "requirement, not free-text prose; the length citation is already spec-exact")
    if scenario_type == "flow":
        return ("LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain "
                 "data, not spec prose; no free-text claim to verify")
    return ("LLM_JUDGE_NOT_APPLIED: restates a Step5 requirement verbatim; "
             "gated on requirement confidence and re-verified at test-case level")


@stage_timing.track("scenario_generation")
@llm_monitor.stage_decorator("scenario_generation")
def run(filter_name: str | None = None, use_llm: bool = True, progress_callback=None):
    print("Scenario Generation \u2014 Parameter Extraction \u2192 Combination \u2192 Constraint Filter \u2192 "
          "Fingerprint \u2192 Scenario Builder")

    requirements = load_requirements()
    if not requirements:
        print(f"  MISSING: {REQUIREMENTS_APPROVED}")
        print("  Run and approve Requirement Derivation first.")
        raise MissingInputError(str(REQUIREMENTS_APPROVED))
    kb = load_knowledge_base()
    if not kb:
        print(f"  MISSING: {KNOWLEDGE_BASE_PATH}")
        raise MissingInputError(str(KNOWLEDGE_BASE_PATH))

    saved_filter = _load_saved_filter(filter_name)
    scope_label = saved_filter["name"] if saved_filter else "full catalog (no scope filter)"
    print(f"  Scope: {scope_label}")

    templates = load_templates()
    tx_elements = transaction_to_element_nos(templates)
    tx_segments = {tx: {str(s.get("segment_number")) for s in d.get("segments", [])}
                   for tx, d in (templates.get("message_templates") or {}).items()}
    enum_caps = load_enum_caps()
    examples = element_examples.load_catalog()
    axes_config = load_test_axes()
    flows_config = load_flows()

    existing_fps = _existing_fingerprints()
    positive: list[dict] = []
    negative: list[dict] = []
    matrix: list[dict] = []
    boundary: list[dict] = []

    if tx_elements:
        print(f"  Transaction-first combination: {len(tx_elements)} transaction template(s)")
        total_combos = 0
        for tx_index, tx in enumerate(sorted(tx_elements), start=1):
            if progress_callback:
                progress_callback(tx_index, len(tx_elements), f"Generating scenarios for {tx}")
            allowed = tx_elements[tx]
            if not allowed:
                continue
            # forensic-review 08 H2 — combinatorics.pairwise() raises ValueError (e.g.
            # >max_parameters, a duplicate element_name) rather than degrading; previously
            # uncaught here, one oversized transaction killed every scenario already built
            # for every other transaction (nothing is written to disk until the loop ends).
            try:
                tx_parameters = extract_parameters(requirements, kb, saved_filter, allowed, enum_caps, examples)
                if tx_parameters:
                    tx_combos = pairwise(tx_parameters)
                    total_combos += len(tx_combos)
                    positive.extend(build_positive_scenarios(tx_combos, tx_parameters, scope_label, tx))
                negative.extend(build_negative_scenarios(requirements, kb, saved_filter, scope_label, allowed, tx))
                charset_neg, charset_boundary = build_charset_and_boundary_scenarios(
                    requirements, kb, saved_filter, scope_label, allowed, tx)
                negative.extend(charset_neg)
                boundary.extend(charset_boundary)
                negative.extend(build_missing_mandatory_scenarios(requirements, kb, saved_filter, scope_label, allowed, tx,
                                                                  tx_segments.get(tx)))
                negative.extend(build_wrong_fixed_value_scenarios(requirements, kb, saved_filter, scope_label, allowed, tx,
                                                                  tx_segments.get(tx)))
                matrix.extend(build_verification_matrix_scenarios(axes_config, tx, allowed, scope_label,
                                                                  kb.get("table_entities"),
                                                                  emv="130" in tx_segments.get(tx, set())))
            except ValueError as exc:
                print(f"  SKIPPED {tx}: combinatorial engine refused this transaction's parameter set — {exc}")
                continue
        print(f"  Combination Engine (allpairspy, per transaction): {total_combos} combination(s)")
        by_class: dict[str, int] = {}
        for s in negative:
            by_class[s.get("negative_class", "?")] = by_class.get(s.get("negative_class", "?"), 0) + 1
        print(f"  Negative-class expansion (Phase E): {len(negative)} scenario(s) {by_class}")
        print(f"  Boundary (Phase E, expected outcome = approved): {len(boundary)} scenario(s)")
        print(f"  Verification Matrix (Phase C): {len(matrix)} scenario(s) across "
              f"{len(axes_config.get('axes') or [])} declared axis/axes")
        flow = build_flow_scenarios(flows_config, set(tx_elements), scope_label,
                                    build_alias_map(templates, flows_config.get("physical_message_map")),
                                    transaction_overrides=build_override_map(
                                        templates, flows_config.get("physical_message_map")),
                                    table_entities=kb.get("table_entities"),
                                    boundary_lengths=flow_boundary_lengths(requirements))
        print(f"  Chained Flows (Phase D): {len(flow)} scenario(s) from "
              f"{len(flows_config.get('flows') or [])} declared flow(s)")
        library = test_data_config.load_library()
        library_override = build_library_override_scenarios(
            library, set(tx_elements), scope_label,
            build_alias_map(templates, flows_config.get("physical_message_map")),
            transaction_overrides=build_override_map(templates, flows_config.get("physical_message_map")))
        print(f"  Library Override (Phase F, client-supplied): {len(library_override)} scenario(s)")
    else:
        # No assembled templates on disk: fall back to the pre-Phase-B global pass rather
        # than producing nothing, and say so instead of silently degrading.
        print("  MISSING templates \u2014 falling back to global (non-transaction-scoped) combination; "
              f"expected {TEMPLATES_PATH}")
        parameters = extract_parameters(requirements, kb, saved_filter, None, enum_caps, examples)
        print(f"  Parameter Extractor: {len(parameters)} combinatorial dimension(s) "
              f"({', '.join(p['element_name'] for p in parameters[:8])}{'...' if len(parameters) > 8 else ''})")
        combos = pairwise(parameters)
        print(f"  Combination Engine (allpairspy): {len(combos)} combination(s)")
        positive = build_positive_scenarios(combos, parameters, scope_label)
        negative = build_negative_scenarios(requirements, kb, saved_filter, scope_label)
        charset_neg, boundary = build_charset_and_boundary_scenarios(requirements, kb, saved_filter, scope_label)
        negative.extend(charset_neg)
        negative.extend(build_missing_mandatory_scenarios(requirements, kb, saved_filter, scope_label))
        negative.extend(build_wrong_fixed_value_scenarios(requirements, kb, saved_filter, scope_label))
        flow = []
        library_override = []

    # Constraint Filter: documented pass-through today (see module docstring) \u2014 no
    # structured mutual-exclusion rules parsed yet, nothing removed here.

    # LLM Scenario Architect pass \u2014 deterministic multi-leg obligation gap-check (always
    # runs), plus an LLM-assisted generative proposal for the one gap with no existing
    # builder (orphan follow-on legs). See scenarios/llm_scenario_architect.py for the
    # fabrication guardrail: the LLM only phrases name/rationale text, never a value.
    flow_gaps = llm_scenario_architect.compute_flow_gaps(flows_config)
    llm_flow_scenarios: list[dict] = []
    if flow_gaps:
        gap_only = [g for g in flow_gaps if g["status"] == "GAP"]
        print(f"  Multi-leg obligation gap-check: {len(flow_gaps)} obligation(s) checked across "
              f"{len({g['flow_id'] for g in flow_gaps})} flow(s), {len(gap_only)} GAP(s)")
        requirements_by_rule_id = {r["source_rule_id"]: r for r in requirements.get("requirements", [])
                                    if r.get("source_rule_id")}
        if use_llm and is_available():
            proposed, rejections = llm_scenario_architect.propose_orphan_scenarios(
                flows_config, flow_gaps, requirements_by_rule_id)
            for s in proposed:
                s["fingerprint"] = _fingerprint(s["parameters"], scope_label, s["target_transaction"])
                s["dedup_key"] = _dedup_key(s["parameters"], s["target_transaction"])
            llm_flow_scenarios = proposed
            print(f"  LLM Scenario Architect (orphan follow-on): {len(proposed)} scenario(s) proposed and "
                  f"validated, {len(rejections)} rejected"
                  + (f" — {'; '.join(r['reason'] for r in rejections)}" if rejections else ""))
        elif any(g["status"] == "GAP" and g["obligation"] == "orphan_followon" for g in flow_gaps):
            print("  LLM Scenario Architect: bridge unavailable \u2014 orphan follow-on gap-fill skipped "
                  "this run (deterministic gaps still recorded)")
        # Step 3 (2026-10 multi-leg axis coverage plan) — per-run report of which
        # flow_expansion_axes.json axes actually got applied to a composed message
        # this run, so "why isn't axis X covered" never needs re-deriving by hand.
        usage = flow_expansion_axes.axis_usage(flows_config, flow)
        non_complete = {name: a["status"] for name, a in usage["by_axis"].items() if a["status"] != "COMPLETE"}
        print(f"  Flow axis usage: {usage['complete_axes']}/{usage['declared_axes']} axis/axes COMPLETE"
              + (f" — not complete: {non_complete}" if non_complete else ""))
        try:
            MULTI_LEG_GAP_REPORT_PATH.parent.mkdir(parents=True, exist_ok=True)
            MULTI_LEG_GAP_REPORT_PATH.write_text(json.dumps({
                "generated_at": datetime.now(timezone.utc).isoformat(),
                "scope": scope_label,
                "llm_available": bool(use_llm and is_available()),
                "gaps": flow_gaps,
                "llm_proposed_scenario_count": len(llm_flow_scenarios),
                "axis_usage": usage,
            }, indent=2, ensure_ascii=False), encoding="utf-8")
        except OSError as exc:
            print(f"  WARNING: could not write {MULTI_LEG_GAP_REPORT_PATH}: {exc}")

    # Step 9 (2026-10 multi-leg axis coverage plan) — spec-anchored requirement
    # mapping for flow scenarios. Each flow's own links are resolved ONCE (link
    # resolution depends only on the flow's static structure, never on which
    # variant/axis-combo produced a given scenario), then attached to every
    # scenario that flow produced. Replaces the old "guess one requirement from
    # the first link's source_rule_id" behavior (which usually pointed at an
    # ENT-ELEM-* field-format requirement, the WRONG kind of requirement for a
    # cross-message obligation) with every requirement the flow's resolved rules
    # actually belong to.
    business_rules = kb.get("business_rules", [])
    requirements_by_rule_id_all = {r["source_rule_id"]: r for r in requirements.get("requirements", [])
                                   if r.get("source_rule_id")}
    flow_link_resolutions: dict[str, list[dict]] = {}
    resolution_counts = {"RESOLVED": 0, "UNRESOLVED": 0, "AMBIGUOUS": 0}
    cross_message_rule_ids: set[str] = set()
    for f in flows_config.get("flows") or []:
        results = flow_rule_resolver.resolve_flow_links(f, business_rules)
        flow_link_resolutions[f["id"]] = results
        for r in results:
            resolution_counts[r["status"]] += 1
    if any(resolution_counts.values()):
        print(f"  Flow rule resolution: {resolution_counts['RESOLVED']} resolved, "
              f"{resolution_counts['UNRESOLVED']} unresolved, {resolution_counts['AMBIGUOUS']} ambiguous "
              f"(of {sum(resolution_counts.values())} anchored link/pin(s) across "
              f"{len(flow_link_resolutions)} flow(s))")

    axes_config_for_links = load_test_axes()
    axis_source_rule_by_value: dict[str, dict[str, str]] = {}
    for axis in axes_config_for_links.get("axes") or []:
        axis_source_rule_by_value[axis["name"]] = {v["value"]: v.get("source_rule_id")
                                                     for v in (axis.get("values") or [])
                                                     if v.get("source_rule_id")}

    for s in flow:
        fid = s.get("flow_id")
        results = flow_link_resolutions.get(fid, [])
        req_links, req_ids = [], set()
        for r in results:
            if r["status"] != "RESOLVED":
                continue
            req = requirements_by_rule_id_all.get(r["rule_id"])
            if not req:
                continue
            req_links.append({"requirement_id": req["id"], "rule_id": r["rule_id"], "basis": r["basis"],
                              "link_type": r["link_type"], "step": r["step"], "element_name": r["element_name"],
                              "confidence_band": req.get("confidence_band")})
            req_ids.add(req["id"])
            cross_message_rule_ids.add(r["rule_id"])
        axis_req_ids: set[str] = set()
        provenance = s.get("flow_axis_provenance") or {}
        for axis_name, value in (s.get("flow_axis_values") or {}).items():
            if not flow_axis_provenance.is_applied(provenance.get(axis_name)):
                continue
            source_rule_id = axis_source_rule_by_value.get(axis_name, {}).get(value)
            req = requirements_by_rule_id_all.get(source_rule_id) if source_rule_id else None
            if req:
                axis_req_ids.add(req["id"])
        s["requirement_links"] = req_links
        s["requirement_ids"] = sorted(req_ids)
        s["axis_requirement_ids"] = sorted(axis_req_ids)
        # requirement_id (singular) intentionally stays None — a flow is never
        # derived from exactly one requirement (M1); requirement_ids is the real
        # answer and is what every consumer (traceability, requirement_coverage)
        # already reads first.

    # Phase 4.2 (/memories/session/plan.md) — which requirement ids already got a
    # parameter-path (positive/negative) scenario this run, so the business-rule
    # builder's conditional python_template fallback never duplicates them.
    param_path_covered_ids: set[str] = set()
    for s in positive:
        param_path_covered_ids.update(s.get("requirement_ids") or [])
        if s.get("requirement_id"):
            param_path_covered_ids.add(s["requirement_id"])
    for s in negative:
        if s.get("requirement_id"):
            param_path_covered_ids.add(s["requirement_id"])

    business_rule = build_business_rule_scenarios(requirements, kb, saved_filter, scope_label,
                                                   param_path_covered_ids=param_path_covered_ids)
    print(f"  Business-rule path: {len(business_rule)} scenario(s) from "
          f"{sum(1 for r in requirements.get('requirements', []) if r.get('derivation_method') == 'business_rule_derived')} "
          "business-rule-derived requirement(s)")
    # Step 9 (2026-10 multi-leg axis coverage plan) — a single-leg business-rule
    # scenario whose own source_rule_id is a rule a flow link resolved to is
    # flagged, never removed: a single composed message cannot actually satisfy
    # or violate a cross-message obligation (e.g. "this leg's Sequence Number
    # equals the prior leg's"), so this scenario's own ASSERT-tier claim about it
    # is honest only as a format/presence check, not a cross-message one.
    cross_message_flagged = 0
    for s in business_rule:
        if s.get("source_rule_id") in cross_message_rule_ids:
            s.setdefault("flags", []).append(
                f"CROSS_MESSAGE_RULE: {s['source_rule_id']} is also exercised by multi-leg flow scenario(s); "
                "a single message cannot fully satisfy or violate a cross-message obligation")
            cross_message_flagged += 1
    if cross_message_flagged:
        print(f"  Cross-message rule disclosure: {cross_message_flagged} business-rule scenario(s) flagged "
              "CROSS_MESSAGE_RULE (also exercised by a multi-leg flow)")
    all_scenarios = positive + negative + matrix + flow + boundary + business_rule + library_override + llm_flow_scenarios

    seen_keys = set(existing_fps)
    deduped = []
    for s in all_scenarios:
        if s["dedup_key"] in seen_keys:
            continue
        seen_keys.add(s["dedup_key"])
        deduped.append(s)
    skipped_dupes = len(all_scenarios) - len(deduped)
    print(f"  Fingerprint Check: {skipped_dupes} duplicate(s) skipped (cross-run)")
    print(f"  Scenario Builder: {len(deduped)} candidate scenario(s) "
          f"({sum(1 for s in deduped if s['type'] == 'positive')} positive, "
          f"{sum(1 for s in deduped if s['type'] == 'negative')} negative, "
          f"{sum(1 for s in deduped if s['type'] == 'business_rule')} business_rule)")

    # Phase 4.1 (/memories/session/plan.md) \u2014 covered_requirement_ids must reflect the
    # POST-dedup survivor list, not the pre-dedup all_scenarios: a requirement whose sole
    # scenario got cross-run-deduped away produced ZERO scenarios this run and must be
    # reported, not silently counted as covered.
    covered_requirement_ids: set[str] = set()
    for s in deduped:
        covered_requirement_ids.update(s.get("requirement_ids") or [])
        if s.get("requirement_id"):
            covered_requirement_ids.add(s["requirement_id"])
    all_scenario_requirement_ids: set[str] = set()
    for s in all_scenarios:
        all_scenario_requirement_ids.update(s.get("requirement_ids") or [])
        if s.get("requirement_id"):
            all_scenario_requirement_ids.add(s["requirement_id"])
    dedup_collapsed_ids = all_scenario_requirement_ids - covered_requirement_ids
    requirement_skip_reasons = classify_requirement_skip_reasons(
        requirements, kb, saved_filter, covered_requirement_ids, dedup_collapsed_ids=dedup_collapsed_ids)
    if requirement_skip_reasons:
        skip_total = sum(len(ids) for ids in requirement_skip_reasons.values())
        print(f"  Requirements with zero scenarios this run: {skip_total} "
              f"(see {REQUIREMENT_SKIP_REASONS_PATH})")
    try:
        REQUIREMENT_SKIP_REASONS_PATH.parent.mkdir(parents=True, exist_ok=True)
        REQUIREMENT_SKIP_REASONS_PATH.write_text(json.dumps({
            "generated_at": datetime.now(timezone.utc).isoformat(),
            "scope": scope_label,
            "skip_reasons": requirement_skip_reasons,
        }, indent=2, ensure_ascii=False), encoding="utf-8")
    except OSError as exc:
        print(f"  WARNING: could not write {REQUIREMENT_SKIP_REASONS_PATH}: {exc}")

    llm_available = use_llm and is_available()
    if deduped and llm_available:
        # Only positive/negative scenarios are newly-constructed prose needing a naming and
        # classification review. Rule-derived scenarios restate a requirement verbatim, are
        # already confidence-gated in Step 5, and are re-checked by verification_agent —
        # judging thousands of them costs hours of LLM calls for no added signal.
        constructed = [s for s in deduped if s["type"] in ("positive", "negative")]
        rule_derived = [s for s in deduped if s["type"] not in ("positive", "negative")]
        if constructed:
            # Traceback judge: verify each scenario against its source page text (Scenario
            # Builder itself is deterministic Python, so there's no enrichment step to retry
            # — this is a one-shot sanity check against the spec).
            requirements_by_id = {r["id"]: r for r in requirements.get("requirements", []) if r.get("id")}

            def _scenario_page(item: dict) -> int | None:
                req = requirements_by_id.get(item.get("requirement_id"))
                return req.get("source_page") if req else None

            # Fix A (2026-10-09) — a "positive"/"negative" scenario can combine several
            # independently-documented fields (build_positive_scenarios/build_negative_scenarios
            # both stamp `parameter_source_pages`: element_name -> ITS OWN requirement's
            # source_page). Judging per-field against its own page replaces the old single
            # "_scenario_page" anchor-page check, which structurally FAILED every multi-field
            # combination scenario (no single page shows N independently-valid fields
            # together — that was never a real grounding defect, just the wrong question).
            def _scenario_field_pages(item: dict) -> dict[str, int | None]:
                pages = item.get("parameter_source_pages") or {}
                return {name: pages.get(name) for name in (item.get("parameters") or {})}

            constructed = _judge_constructed_resumable(
                constructed, label_fields=["name", "type", "parameters"],
                page_resolver=_scenario_page, pages_dir=RAW_PAGES_DIR,
                grounding_skill="qe-scenario-architect",
                multi_page_resolver=_scenario_field_pages,
            )
        for s in rule_derived:
            # forensic-review 08 M4 — delegated to _rule_derived_llm_skip_flag() so each
            # type's disclosure describes what it's actually built from.
            s.setdefault("flags", []).append(_rule_derived_llm_skip_flag(s["type"]))
        deduped = constructed + rule_derived
    elif deduped:
        for s in deduped:
            s.setdefault("flags", []).append("NEEDS_LLM_REVIEW: bridge unavailable, naming/classification unverified")

    for i, s in enumerate(deduped, start=_next_scenario_id_start()):
        s["id"] = f"SC-{i:04d}"
        s["spec_id"] = SOURCE_ID
        s["spec_version"] = SPEC_VERSION
        # per-scenario scope attribution — survives the fingerprint-merge in approve_scenarios,
        # unlike the catalog-level `scope` string which is lost the moment two runs merge.
        s["scope_name"] = saved_filter.get("name") if saved_filter else None
        s["scope_segments"] = saved_filter.get("selected_segments") if saved_filter else None
        s["scope_mode"] = saved_filter.get("mode") if saved_filter else None
        apply_confidence(s)

    flagged_count = sum(1 for s in deduped if s.get("flags"))
    by_type: dict[str, int] = {}
    for s in deduped:
        by_type[s["type"]] = by_type.get(s["type"], 0) + 1
    output = {
        "pipeline_step": "scenarios",
        "architecture_alignment": "Scenario Management (Architecture Doc \u00a73) \u2014 feeds Test Prioritization",
        "source_id": SOURCE_ID,
        "generated_at": datetime.now(timezone.utc).isoformat(),
        "scope": scope_label,
        "state": "EXTRACTED",
        "total_scenarios": len(deduped),
        "by_type": by_type,
        "by_transaction": _count_by_transaction(deduped),
        "flagged_for_review": flagged_count,
        "skipped_duplicates": skipped_dupes,
        "confidence_summary": score_catalog(deduped),
        "scenarios": deduped,
    }

    OUT_FILE.parent.mkdir(parents=True, exist_ok=True)
    write_json_atomic(output, OUT_FILE)
    print(f"\nGenerated {len(deduped)} scenario(s). Output: {OUT_FILE}")
    if JUDGE_CHECKPOINT_PATH.exists():
        JUDGE_CHECKPOINT_PATH.unlink()  # full pass completed and persisted to OUT_FILE — no longer needed

    # qe-scenario-architect skill self-audit: the skill's own validator (mirrors
    # references/scenario-schema.md + scenario.schema.json) runs on every batch of
    # generated scenarios, not just when a human asks for AUDIT mode in chat.
    if skill_validator is not None:
        from collections import Counter
        findings = skill_validator.run_all_checks(deduped, requirements, templates, flows_config)
        coverage = skill_validator.build_coverage_report(deduped, requirements)
        counts = Counter(f.level for f in findings)
        try:
            SCENARIO_AUDIT_REPORT_PATH.parent.mkdir(parents=True, exist_ok=True)
            SCENARIO_AUDIT_REPORT_PATH.write_text(json.dumps({
                "generated_at": datetime.now(timezone.utc).isoformat(),
                "findings": [f.to_dict() for f in findings],
                "counts": dict(counts),
                "coverage": coverage,
            }, indent=2, ensure_ascii=False), encoding="utf-8")
        except OSError as exc:
            print(f"  WARNING: could not write {SCENARIO_AUDIT_REPORT_PATH}: {exc}")
        print(f"  Skill self-audit (qe-scenario-architect validator): {counts.get('ERROR', 0)} error(s), "
              f"{counts.get('WARN', 0)} warning(s), {counts.get('INFO', 0)} info \u2014 see {SCENARIO_AUDIT_REPORT_PATH}")
    else:
        print("  Skill self-audit skipped \u2014 .github/skills/qe-scenario-architect/scripts not found")


if __name__ == "__main__":
    try:
        run()
    except (MissingInputError, CorruptArtifactError) as exc:
        print(f"\nERROR: {exc}")
        sys.exit(1)
