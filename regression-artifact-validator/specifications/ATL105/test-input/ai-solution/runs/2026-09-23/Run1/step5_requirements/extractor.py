"""
Step 5 — Requirement Derivation.
States each approved fact/rule as an explicit, testable functional requirement.
Architecture: TDD §7.1 (algorithm), §11.2 (Requirement entity schema).

Reads (read-only): assembled/output/knowledge_base.json — the merged Steps 1-3
(+4) approved catalogs. Never touches approved/ catalogs or assembled/output/.

Three derivation paths:
  1. Python template (deterministic) — length/type constraint, and genuine
     fixed/enumerated valid_values lists. Source: kb["attributes"] (Step 2).
  2. LLM phrasing (optional) — narrative attributes.processing_rules text,
     via the existing Enricher (llm_enricher.phrase_requirements) + Judge
     (llm_enricher.judge_candidates). If the LLM bridge is unavailable, these
     elements are recorded under "pending_llm_phrasing", not silently dropped.
  3. Business-rule derived (deterministic) — cross-field conditional facts
     from Step 4 deep extraction. Source: kb["business_rules"] (2655 total).
     Gated to confidence_score >= BUSINESS_RULE_MIN_CONFIDENCE and zero flags —
     this project has no per-rule SME sign-off field yet, so this is the
     closest honest proxy available; swap it for a real sme_status check the
     day that field exists. Kept structured (condition_element_id/
     condition_value/constraint_text), not just a natural-language statement,
     so Scenario Builder never has to re-parse a sentence back into data.

Approval gate: OPTIONAL, controlled by the caller (Streamlit toggle) — this
module only ever writes to candidates/; promotion to approved/ mirrors Steps
1-4's existing pattern and is not decided here.

Writes: step5_requirements/candidates/requirement_candidates.json
"""

import json
import os
import re
import sys
from collections import Counter
from datetime import datetime, timezone
from itertools import count
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]  # src/pipeline
KNOWLEDGE_BASE = ROOT / "assembled" / "output" / "knowledge_base.json"
OUT_FILE = Path(__file__).parent / "candidates" / "requirement_candidates.json"
RAW_PAGES_DIR = ROOT.parent.parent / "raw" / "pdf_pages"
# Stage I2 — per-row drop diagnostics for _genuine_fixed_list(), off by default
# (this heuristic runs over every attribute; unconditional printing would be noise).
_DEBUG_FIXED_LIST = os.environ.get("PIPELINE_DEBUG_FIXED_LIST") == "1"

sys.path.insert(0, str(ROOT / "shared"))
sys.path.insert(0, str(ROOT / "scenario_scope"))
import llm_enricher  # noqa: E402
import scope_filter  # noqa: E402
import segment_linker  # noqa: E402
from llm_client import is_available  # noqa: E402
from confidence import apply_confidence, score_catalog  # noqa: E402

SOURCE_ID = "SRC-ATL105-PDF-001"
BUSINESS_RULE_MIN_CONFIDENCE = 0.7

CHAR_TYPE_EXPANSION = {
    "AN": "alphanumeric", "ANS": "alphanumeric with special characters",
    "N": "numeric", "A": "alphabetic", "B64": "Base64-encoded",
    "ANSB": "alphanumeric, special, and binary",
}
_GENERIC_VALUE_TOKENS = {
    "value", "description", "code", "codes", "pos. nos.", "valid entry",
    "code description", "value description", "character description", "codes description",
}
_CONFIDENCE_MAP = {"HIGH": 0.9, "MEDIUM": 0.6, "LOW": 0.3}
_RANGE_DESCRIPTION_RE = re.compile(r"^\d{2}[\u2013-]\d{2}\s+\S")  # e.g. "01\u201312 Month of the year"
_RANGE_WITH_PROSE_RE = re.compile(r"\d{2,3}[\u2013-]\d{2,3}")  # any embedded numeric range
# Word-boundary, not substring: "any" must match matches free-form markers ("Any number",
# "Any valid entry") without also matching "Germany"/"Company" (the original substring test
# disqualified an element's ENTIRE domain on such false positives).
_FREE_FORM_MARKER_RE = re.compile(r"\bany\b", re.IGNORECASE)
_SOFTWARE_VERSION_RE = re.compile(r"\bsoftware version\b", re.IGNORECASE)


def _expand_character_type(ct: str | None) -> str:
    if not ct:
        return "a documented format"
    return CHAR_TYPE_EXPANSION.get(ct.strip(), ct.strip())


def _build_element_segment_map(kb: dict) -> dict[str, str]:
    """element_ref (e.g. 'ENT-ELEM-44') -> parent_segment_id (e.g. 'ENT-SEG-100').
    Python-template requirements previously carried no segment attribution at all
    (unlike the structural-field path), so any scenario built from them was
    unresolvable by a segment filter. First segment an element appears in wins
    if it's shared by more than one (matches the single-segment_number schema
    already used elsewhere in this file)."""
    mapping: dict[str, str] = {}
    for seg in kb.get("segments", []):
        for f in seg.get("fields", []):
            ref = f.get("element_ref")
            parent = f.get("parent_segment_id")
            if ref and parent and ref not in mapping:
                mapping[ref] = parent
    return mapping


_ELEM_NAME_RE = re.compile(r"^ENT-ELEM-(.+)$")


def _norm_elem_name(text: str | None) -> str:
    return re.sub(r"[^a-z0-9]", "", (text or "").lower())


def _build_name_segment_map(kb: dict, element_segment_map: dict[str, str]) -> dict[str, str]:
    """normalized element_name -> parent_segment_id, for the Step 4 relationship/supplemental
    passes that emit name-based ids ('ENT-ELEM-Totals Date') instead of the numeric
    'ENT-ELEM-44'. Only elements that already resolve to a single segment via the id map are
    aliased -- so this narrows an existing attribution, never invents one."""
    out: dict[str, str] = {}
    for attr in kb.get("attributes", []):
        seg = element_segment_map.get(attr.get("entity_id") or "")
        nm = _norm_elem_name(attr.get("element_name"))
        if seg and nm and nm not in out:
            out[nm] = seg
    return out


_SEGMENT_ID_RE = re.compile(r"^ENT-SEG-(.+)$")

# BRD-style category taxonomy (Message/Field/Account-Card/Amount/Security/Lifecycle/
# Dependency), applied dynamically by keyword-matching each requirement's own statement
# text -- never a per-requirement/per-segment lookup table, so it generalizes to any
# segment or rule already present (or added later) to the Knowledge Base.
_CATEGORY_KEYWORDS: dict[str, tuple[str, ...]] = {
    "Security": ("pin", "dukpt", "encrypt", "cvv", "cvc", "ksn", "trans armor",
                 "token", "mask", "cryptogram", " key "),
    "Message": ("field separator", "segment length", "byte limit", "message limit",
                "field ordering", "trailing field", "intermediate field", "segment count"),
    "Amount": ("amount", "tax", "cash", "fuel", "surcharge", " fee", "balance", "reconcil"),
    "Account / Card": ("account number", "card ", "track ", "pan ", "expiration",
                       " bin ", "emv", "chip"),
    "Lifecycle": ("reversal", "void", "completion", "tor ", "timeout", "retry",
                  "sequence number", "partial approval", "follow-on", "authorization",
                  "settlement"),
    "Dependency": ("depends on", "cross-segment", "correlat", "consistent with"),
}
# Paths whose statements are already scoped to a single field -> always "Field",
# regardless of what words happen to appear in the generated sentence.
_FIELD_METHODS = {"field_constraint", "field_fixed_value", "python_template",
                  "message_field_constraint", "table_field_constraint",
                  "supplemental_entity_derived"}


def _resolve_segment_number(candidate_ids: list, element_segment_map: dict[str, str],
                            name_segment_map: dict[str, str] | None = None,
                            known_segments: set[str] | None = None) -> str | None:
    """Fully dynamic segment attribution shared by every derivation path. Looks for a
    direct ENT-SEG-* id among the candidates first, then resolves any ENT-ELEM-* (or
    other) id via the KB-derived element->segment map, and finally resolves name-based
    ids ('ENT-ELEM-Totals Date' emitted by the Step 4 passes) through the normalized
    element-name map. Never guesses -- returns None (left unattributed downstream,
    matching requirement_coverage.py's UNMAPPED philosophy) when nothing resolves.

    known_segments (Stage D1.2): when supplied, a segment number that resolves via any
    rung above but is NOT a real KB segment (e.g. a Step-4 relationship's stray '1' that
    conflates ATL105's narrative 'Data SECTION No. 1' with an actual Data SEGMENT) is
    rejected rather than returned -- honouring this function's own 'Never guesses' claim,
    which previously held only for rungs 2-3, not rung 1's direct ENT-SEG-* match."""
    def _known(seg_no: str) -> bool:
        return known_segments is None or seg_no in known_segments

    for eid in candidate_ids:
        m = _SEGMENT_ID_RE.match(eid or "")
        if m and _known(m.group(1)):
            return m.group(1)
    for eid in candidate_ids:
        if not eid:
            continue
        m = _SEGMENT_ID_RE.match(element_segment_map.get(eid) or "")
        if m and _known(m.group(1)):
            return m.group(1)
    if name_segment_map:
        for eid in candidate_ids:
            nm = _ELEM_NAME_RE.match(eid or "")
            if not nm:
                continue
            m = _SEGMENT_ID_RE.match(name_segment_map.get(_norm_elem_name(nm.group(1))) or "")
            if m and _known(m.group(1)):
                return m.group(1)
    return None


# Prose-fallback patterns: used ONLY for rules that carry no structured entity reference,
# to recover a segment from the rule's own sentence. Every number they capture is still
# resolved through the KB-built maps below -- never a hardcoded rule->segment table.
_TEXT_SEGMENT_RE = re.compile(r"\bsegment\s+(?:no\.?\s*)?'?(\w{1,4})'?", re.IGNORECASE)
_TEXT_ELEMENT_RE = re.compile(r"\b(?:data\s+)?element\s+'?(\d{1,3})'?", re.IGNORECASE)
_TEXT_TABLE_RE = re.compile(r"\btable\s+id\s+'?(\d{1,3})'?", re.IGNORECASE)
_APPENDIX_I_RE = re.compile(r"\bappendix\s+i\b", re.IGNORECASE)


def _build_table_element_map(kb: dict) -> tuple[dict[str, str], str | None]:
    """table_id_value (e.g. '049') -> ENT-ELEM-NNN, taken straight from each Appendix
    table entity's own field.element_ref (ATTR-NNN -> ENT-ELEM-NNN). Also returns the
    single data element the most Appendix table fields hang off, computed from the data
    (not hardcoded) -- used as the last resort for a rule that only says 'Appendix I'
    without a table id that resolves on its own."""

    table_element: dict[str, str] = {}
    elem_field_counts: Counter = Counter()
    # Wired KB (assemble_knowledge_base.py) stores these under "table_entities"; the older
    # graph builder used "appendix_tables_unreviewed" -- read whichever is populated.
    for t in kb.get("table_entities") or kb.get("appendix_tables_unreviewed", []):
        tid = str(t.get("table_id_value") or "").strip()
        for f in t.get("fields", []):
            er = (f.get("element_ref") or "").replace("ATTR-", "ENT-ELEM-")
            if not er:
                continue
            elem_field_counts[er] += 1
            if tid and tid not in table_element:
                table_element[tid] = er
    dominant_elem = elem_field_counts.most_common(1)[0][0] if elem_field_counts else None
    return table_element, dominant_elem


def _segment_from_text(text: str, element_segment_map: dict[str, str],
                       table_element_map: dict[str, str], valid_segments: set[str],
                       dominant_appendix_elem: str | None) -> str | None:
    """Data-driven prose fallback for rules with no structured entity reference. Reads the
    rule's own sentence for (most specific first) an explicit segment number, a data-element
    number, or an Appendix table id, and resolves each through the KB-built maps. Returns
    None when nothing resolves -- never a guess."""
    m = _TEXT_SEGMENT_RE.search(text)
    if m and m.group(1) in valid_segments:
        return m.group(1)
    m = _TEXT_ELEMENT_RE.search(text)
    if m:
        parent = element_segment_map.get(f"ENT-ELEM-{m.group(1)}")
        if parent:
            return parent.rsplit("-", 1)[-1]
    m = _TEXT_TABLE_RE.search(text)
    if m:
        el = table_element_map.get(m.group(1).zfill(3)) or table_element_map.get(m.group(1))
        parent = element_segment_map.get(el) if el else None
        if parent:
            return parent.rsplit("-", 1)[-1]
    if _APPENDIX_I_RE.search(text) and dominant_appendix_elem:
        parent = element_segment_map.get(dominant_appendix_elem)
        if parent:
            return parent.rsplit("-", 1)[-1]
    return None


def _classify_category(statement: str, derivation_method: str) -> str:
    """Dynamic, keyword-driven categorization mirroring the SME BRD taxonomy. Runs
    against the statement text itself (never a per-instance answer table), so it
    applies uniformly to every segment/rule the Knowledge Base contains."""
    if derivation_method == "relationship_derived":
        return "Dependency"
    if derivation_method in _FIELD_METHODS:
        return "Field"
    low = f" {statement.lower()} "
    for category, keywords in _CATEGORY_KEYWORDS.items():
        if any(kw in low for kw in keywords):
            return category
    return "Field"


def _classify_requirement_type(derivation_method: str, *, is_enum: bool = False,
                                relationship_type: str | None = None) -> str:
    """Dynamic Type classification (mirrors the SME BRD's Type column) driven only by
    the derivation method and signals already computed for the requirement."""
    if derivation_method == "python_template":
        return "Field Validation" if is_enum else "Boundary"
    if derivation_method == "relationship_derived":
        return "Dependency" if relationship_type == "SEGMENT_USED_IN_TRANSACTION" else "Cross-Segment Rule"
    return {
        "field_constraint": "Field Validation",
        "field_fixed_value": "Format",
        "message_field_constraint": "Field Validation",
        "table_field_constraint": "Field Validation",
        "supplemental_entity_derived": "Field Validation",
        "business_rule_derived": "Business Rule",
        "llm_phrased": "Processing Rule",
    }.get(derivation_method, "Business Rule")


def _genuine_fixed_list(parsed_values: list[dict], context: str | None = None) -> list[str]:
    """Returns the cleaned value list if this looks like a genuine fixed/enumerated
    list (not free-form text mis-split). Empty list if the heuristic doesn't hold.

    A single malformed/noise ROW no longer disqualifies the entire domain — it is
    dropped and the rest of the list is still used. Only a genuine free-form marker
    ("Any number", "Any valid entry") disqualifies the whole list, since it means the
    field's real domain is open-ended rather than enumerable.

    Stage I2 — `context` (e.g. "<element_name> (<entity_id>)") is only used for the
    optional per-row drop diagnostics gated by PIPELINE_DEBUG_FIXED_LIST=1; behavior
    is otherwise unchanged when the flag is unset.
    """
    if not parsed_values or len(parsed_values) < 2:
        return []
    label = context or "<unlabeled>"
    cleaned = []
    for v in parsed_values:
        val = (v.get("value") or "").strip()
        if not val:
            continue
        low = val.lower()
        if _FREE_FORM_MARKER_RE.search(val):
            if _DEBUG_FIXED_LIST:
                print(f"    [_genuine_fixed_list] {label}: free-form marker {val!r} — whole list disqualified")
            return []  # genuine free-form marker present -> not an enumerable domain
        if low in _GENERIC_VALUE_TOKENS:
            if _DEBUG_FIXED_LIST:
                print(f"    [_genuine_fixed_list] {label}: dropped {val!r} — generic/header token")
            continue  # table header noise, drop silently
        if _SOFTWARE_VERSION_RE.search(val):
            if _DEBUG_FIXED_LIST:
                print(f"    [_genuine_fixed_list] {label}: dropped {val!r} — software version boilerplate")
            continue  # trailing boilerplate footer, drop this row only
        if _RANGE_DESCRIPTION_RE.match(val):
            if _DEBUG_FIXED_LIST:
                print(f"    [_genuine_fixed_list] {label}: dropped {val!r} — range-description row")
            continue  # "NN-NN <explanation>" row -> drop this row only, not the whole list
        if _RANGE_WITH_PROSE_RE.search(val) and len(val.split()) > 6:
            if _DEBUG_FIXED_LIST:
                print(f"    [_genuine_fixed_list] {label}: dropped {val!r} — numeric range embedded in prose")
            continue  # numeric range embedded in a long sentence -> drop this row only
        if len(val) > 40:
            if _DEBUG_FIXED_LIST:
                print(f"    [_genuine_fixed_list] {label}: dropped {val!r} — longer than 40 chars")
            continue  # too long to be a discrete code value -> drop this row only
        cleaned.append(val)
    if cleaned and len(cleaned) < 2 and _DEBUG_FIXED_LIST:
        print(f"    [_genuine_fixed_list] {label}: only {len(cleaned)} row(s) survived — disqualified (need >= 2)")
    return cleaned if len(cleaned) >= 2 else []


def _template_requirements(attr: dict, next_id, element_segment_map: dict[str, str] | None = None) -> list[dict]:
    """Deterministic path. May emit 0, 1, or 2 requirements per element."""
    out = []
    props = attr.get("attributes", {}) or {}
    element_name = attr.get("element_name", "")
    entity_id = attr.get("entity_id")
    page = (attr.get("source") or {}).get("page")

    parent_segment_id = (element_segment_map or {}).get(entity_id)
    segment_number = parent_segment_id.rsplit("-", 1)[-1] if parent_segment_id else None
    related_entity_ids = [entity_id, parent_segment_id] if parent_segment_id else [entity_id]

    max_len = props.get("max_length_bytes")
    char_type = props.get("character_type")
    if isinstance(max_len, int) and char_type:
        expected = f"{_expand_character_type(char_type)} value not exceeding {max_len} bytes"
        out.append({
            "id": f"REQ-{SOURCE_ID}:{next(next_id):03d}",
            "statement": f"{element_name} must be {_expand_character_type(char_type)} and must not exceed {max_len} bytes.",
            "source_rule_id": entity_id,
            "source_page": page,
            "derivation_method": "python_template",
            "segment_number": segment_number,
            "related_entity_ids": related_entity_ids,
            "category": "Field",
            "requirement_type": _classify_requirement_type("python_template", is_enum=False),
            "expected_behavior": expected,
        })

    fixed_values = _genuine_fixed_list(props.get("valid_values_parsed") or [],
                                       context=f"{element_name} ({entity_id})")
    if fixed_values:
        out.append({
            "id": f"REQ-{SOURCE_ID}:{next(next_id):03d}",
            "statement": f"{element_name} must be one of: {', '.join(fixed_values)}.",
            "source_rule_id": entity_id,
            "source_page": page,
            "derivation_method": "python_template",
            "segment_number": segment_number,
            "related_entity_ids": related_entity_ids,
            "category": "Field",
            "requirement_type": _classify_requirement_type("python_template", is_enum=True),
            "expected_behavior": f"one of: {', '.join(fixed_values)}",
        })
    return out


_CONDITION_ELEMENT_RE = re.compile(r"element\s+'?(\d+)'?")
_CONDITION_VALUE_RE = re.compile(r"=\s*'([^']*)'")

_ROC_PHRASE = {"R": "is required and must be present",
               "O": "is optional and may be omitted",
               "C": "is conditional and must be present only when its documented condition holds"}
_FIXED_VALUE_RE = re.compile(r"Fixed [Vv]alue:?\s*[\"\u201c']?([A-Za-z0-9^_.\-]{1,12})")


def _structural_field_requirements(kb: dict, next_id) -> list[dict]:
    """Deterministic path #4. Every segment/message/table field carries an R/O/C presence
    obligation and often a fixed value; none of it was previously turned into requirements."""
    out = []
    element_segment_map = _build_element_segment_map(kb)
    name_segment_map = _build_name_segment_map(kb, element_segment_map)

    def emit(statement, source_rule_id, page, method, related, extra=None):
        rec = {
            "id": f"REQ-{SOURCE_ID}:{next(next_id):03d}",
            "statement": statement,
            "source_rule_id": source_rule_id,
            "source_page": page,
            "derivation_method": method,
            "confidence_score": 0.95,
            "related_entity_ids": [r for r in related if r],
            "category": "Field",
            "requirement_type": _classify_requirement_type(method),
            "flags": [],
        }
        if extra:
            rec.update(extra)
        out.append(rec)

    for seg in kb.get("segments", []):
        seg_no, seg_name = seg.get("segment_number"), seg.get("name") or ""
        for f in seg.get("fields", []):
            name = (f.get("name") or "").strip()
            if not name:
                continue
            page = (f.get("source") or {}).get("page")
            fid, ref = f.get("entity_id"), f.get("element_ref")
            roc = (f.get("roc") or "").strip().upper()
            where = f"{seg_name} (Data Segment No. {seg_no})"
            if roc in _ROC_PHRASE:
                emit(f"In {where}, field {f.get('field_no')} ({name}) {_ROC_PHRASE[roc]}.",
                     fid, page, "field_constraint", [ref, seg.get("entity_id")],
                     {"roc": roc, "segment_number": seg_no,
                      "condition_trigger": f"{where} is present in the request",
                      "expected_behavior": _ROC_PHRASE[roc]})
            m = _FIXED_VALUE_RE.search(f.get("description_summary") or "")
            if m:
                emit(f"In {where}, field {f.get('field_no')} ({name}) must equal the fixed value {m.group(1)}.",
                     fid, page, "field_fixed_value", [ref, seg.get("entity_id")],
                     {"fixed_value": m.group(1), "segment_number": seg_no,
                      "condition_trigger": f"{where} is present in the request",
                      "expected_behavior": f"field {f.get('field_no')} equals fixed value {m.group(1)}"})

    for msg in kb.get("messages", []):
        mid, mname = msg.get("entity_id"), msg.get("name") or ""
        for f in msg.get("fields", []):
            roc = (f.get("roc") or "").strip().upper()
            if roc not in _ROC_PHRASE:
                continue
            ref = (f.get("element_ref") or "").replace("ATTR-", "ENT-ELEM-")
            emit(f"In the {mname} message, field {f.get('field_no')} {_ROC_PHRASE[roc]}.",
                 f.get("entity_id"), (f.get("source") or {}).get("page"),
                 "message_field_constraint", [ref, mid],
                 {"roc": roc, "target_transaction": mname,
                  "segment_number": _resolve_segment_number([ref], element_segment_map, name_segment_map),
                  "condition_trigger": f"{mname} message is being sent",
                  "expected_behavior": _ROC_PHRASE[roc]})

    for tbl in kb.get("table_entities", []):
        tid, tname = tbl.get("entity_id"), tbl.get("name") or ""
        for f in tbl.get("fields", []):
            fname = (f.get("raw_field_name") or "").strip()
            if not fname:
                continue
            ref = (f.get("element_ref") or "").replace("ATTR-", "ENT-ELEM-")
            emit(f"In TABLE {tname} (Table ID {tbl.get('table_id_value')}), field "
                 f"{f.get('field_no')} ({fname}) is defined as: "
                 f"{(f.get('description_summary') or '').strip()[:120]}",
                 f.get("entity_id"), (f.get("source") or {}).get("page"),
                 "table_field_constraint", [ref, tid],
                 {"segment_number": _resolve_segment_number([ref], element_segment_map, name_segment_map)})

    return out


def _relationship_requirements(kb: dict, next_id) -> list[dict]:
    """Deterministic path #5. Segment-in-transaction composition and cross-field
    dependencies are testable obligations; they were only ever used for templating."""
    out = []
    element_segment_map = _build_element_segment_map(kb)
    name_segment_map = _build_name_segment_map(kb, element_segment_map)
    seg_names = {s.get("segment_number"): s.get("name") for s in kb.get("segments", [])}
    # Stage D1.2 — the KB's own segments list is the only trustworthy set of segment
    # numbers that actually exist; a Step-4 relationship's segment_number is NOT validated
    # anywhere upstream (confirmed: tools/assemble_knowledge_base.py's merge_relationships()
    # merges by dedupe key only) and can carry a phantom value like '1' that conflates
    # ATL105's narrative 'Data SECTION No. 1' with an actual Data SEGMENT.
    known_segments = {s.get("segment_number") for s in kb.get("segments", []) if s.get("segment_number")}
    skipped_unknown_segment = 0
    tx_display = {}
    for rel in kb.get("relationships", []):
        if rel.get("relationship_type") == "SEGMENT_USED_IN_TRANSACTION" and rel.get("transaction_type"):
            tx_display[rel.get("target_entity") or ""] = rel["transaction_type"]
    for rel in kb.get("relationships", []):
        rtype = rel.get("relationship_type")
        page = (rel.get("source") or {}).get("page")
        src = rel.get("source_entity") or ""
        tgt = rel.get("target_entity") or ""
        target_transaction = None
        condition_trigger = None
        if rtype == "SEGMENT_USED_IN_TRANSACTION":
            seg_no = src.removeprefix("ENT-SEG-") or str(rel.get("segment_number") or "")
            if seg_no not in known_segments:
                skipped_unknown_segment += 1
                continue
            target_transaction = rel.get("transaction_type") or tx_display.get(tgt) or tgt.replace("_", " ").title()
            src = src or (f"ENT-SEG-{seg_no}" if seg_no else "")
            statement = (f"The {target_transaction} transaction includes "
                         f"{seg_names.get(seg_no) or 'segment ' + seg_no} (Data Segment No. {seg_no}).")
            condition_trigger = f"{target_transaction} transaction"
            expected_behavior = f"{seg_names.get(seg_no) or 'segment ' + seg_no} (Data Segment No. {seg_no}) is present"
        elif rtype == "DEPENDS_ON":
            statement = f"{src} depends on {tgt}; the documented dependency must hold."
            condition_trigger = f"{tgt} is present"
            expected_behavior = "the documented dependency must hold"
        elif rtype == "VALID_VALUE_OF_FIELD":
            code, fref = rel.get("value_code"), rel.get("field_ref")
            if not code or not fref:
                continue
            statement = (f"Data element {fref} accepts documented value '{code}'"
                         + (f" — {(rel.get('semantic_note') or '').strip()[:120]}" if rel.get("semantic_note") else "."))
            src = f"ENT-ELEM-{fref}"
            expected_behavior = statement
        else:
            continue
        related_entity_ids = [x for x in (src, tgt)
                              if x and x.startswith(("ENT-ELEM-", "ENT-SEG-", "ENT-FIELD-"))]
        out.append({
            "id": f"REQ-{SOURCE_ID}:{next(next_id):03d}",
            "statement": statement,
            "source_rule_id": rel.get("relationship_id") or src,
            "source_page": page,
            "derivation_method": "relationship_derived",
            "relationship_type": rtype,
            "target_transaction": target_transaction,
            "confidence_score": rel.get("confidence_score", 0.8),
            "segment_number": _resolve_segment_number(related_entity_ids, element_segment_map,
                                                        name_segment_map, known_segments),
            "category": _classify_category(statement, "relationship_derived"),
            "requirement_type": _classify_requirement_type("relationship_derived", relationship_type=rtype),
            "condition_trigger": condition_trigger,
            "expected_behavior": expected_behavior,
            # ENT-FIELD- refs (a DEPENDS_ON source is sometimes a Step1 field id, not an
            # element/segment id) must be kept too, or the resolver has nothing to attribute
            # this requirement to and it is silently skipped downstream.
            "related_entity_ids": related_entity_ids,
            "flags": list(rel.get("flags") or []),
        })
    if skipped_unknown_segment:
        print(f"  [Requirements] {skipped_unknown_segment} SEGMENT_USED_IN_TRANSACTION relationship(s) "
              f"skipped — segment_number not present in the KB's own segments list (phantom segment guard)")
    return out


def _supplemental_entity_requirements(kb: dict, next_id) -> list[dict]:
    """Deterministic path #6. Step 4's supplemental entities describe elements the
    Chapter 12/13 passes never catalogued."""
    out = []
    element_segment_map = _build_element_segment_map(kb)
    name_segment_map = _build_name_segment_map(kb, element_segment_map)
    for ent in kb.get("supplemental_entities", []):
        desc = (ent.get("description_summary") or "").strip()
        name = (ent.get("name") or "").strip()
        if not name or not desc:
            continue
        related = [x for x in [ent.get("parent_segment_id"), ent.get("entity_id")] if x]
        out.append({
            "id": f"REQ-{SOURCE_ID}:{next(next_id):03d}",
            "statement": f"{name}: {desc[:300]}",
            "source_rule_id": ent.get("entity_id"),
            "source_page": (ent.get("source") or {}).get("page"),
            "derivation_method": "supplemental_entity_derived",
            "confidence_score": ent.get("confidence_score", 0.6),
            "segment_number": _resolve_segment_number(related, element_segment_map, name_segment_map),
            "category": "Field",
            "requirement_type": _classify_requirement_type("supplemental_entity_derived"),
            "related_entity_ids": related,
            "flags": list(ent.get("flags") or []),
        })
    return out


def _parse_condition(condition: str) -> tuple[str | None, str | None]:
    """Best-effort parse of a business rule's condition string, e.g.
    "Data element 111 = '071' (Enabler Verification Value)" -> ("111", "071").
    Returns (None, None) on a shape miss — the rule is still kept (with
    condition_element_id/condition_value left blank), never dropped just
    because the free-text condition didn't match the expected pattern."""
    elem_m = _CONDITION_ELEMENT_RE.search(condition or "")
    val_m = _CONDITION_VALUE_RE.search(condition or "")
    return (elem_m.group(1) if elem_m else None, val_m.group(1) if val_m else None)


def _business_rule_requirements(kb: dict, next_id) -> list[dict]:
    """Deterministic path #3. Turns business rules (Step 4 deep extraction) into
    structured, testable Requirements. Rules below BUSINESS_RULE_MIN_CONFIDENCE or
    carrying flags are still emitted but marked requires_review — previously they were
    dropped, which meant the always-on regex floor (confidence 0.4, always flagged)
    could never produce a single requirement, scenario or test case."""
    out = []
    element_segment_map = _build_element_segment_map(kb)
    name_segment_map = _build_name_segment_map(kb, element_segment_map)
    table_element_map, dominant_appendix_elem = _build_table_element_map(kb)
    valid_segments = {str(s.get("segment_number")) for s in kb.get("segments", []) if s.get("segment_number") is not None}
    for rule in kb.get("business_rules", []):
        score = rule.get("confidence_score", 0)
        rule_flags = list(rule.get("flags") or [])
        below_gate = score < BUSINESS_RULE_MIN_CONFIDENCE or bool(rule_flags)
        condition_elem_no, condition_value = _parse_condition(rule.get("condition") or "")
        condition_element_id = f"ENT-ELEM-{condition_elem_no}" if condition_elem_no else None
        related_entity_ids = rule.get("related_entity_ids", [])
        statement = rule.get("statement", "")
        segment_candidates = list(related_entity_ids) + [condition_element_id]
        segment_number = _resolve_segment_number(segment_candidates, element_segment_map, name_segment_map)
        # Only fall back to prose parsing when the structured refs gave us nothing --
        # keeps the high-confidence path authoritative and the inference clearly marked.
        segment_inferred_from_text = False
        if segment_number is None:
            text = f"{statement} {rule.get('constraint') or ''} {rule.get('condition') or ''}"
            segment_number = _segment_from_text(text, element_segment_map, table_element_map,
                                                valid_segments, dominant_appendix_elem)
            segment_inferred_from_text = segment_number is not None
        out.append({
            "id": f"REQ-{SOURCE_ID}:{next(next_id):03d}",
            "statement": statement,
            "source_rule_id": rule.get("rule_id"),
            "source_page": (rule.get("source") or {}).get("page"),
            "derivation_method": "business_rule_derived",
            "confidence_score": score,
            "below_confidence_gate": below_gate,
            "requires_review": below_gate,
            "flags": rule_flags + (["BELOW_CONFIDENCE_GATE"] if below_gate else []),
            "related_entity_ids": related_entity_ids,
            "segment_number": segment_number,
            "segment_inferred_from_text": segment_inferred_from_text,
            "category": _classify_category(statement, "business_rule_derived"),
            "requirement_type": _classify_requirement_type("business_rule_derived"),
            "condition_element_id": condition_element_id,
            "condition_value": condition_value,
            "condition_value_parsed": condition_value.strip() if condition_value else None,
            "condition_trigger": (rule.get("condition") or "").strip() or None,
            "expected_behavior": (rule.get("constraint") or "").strip() or None,
            "constraint_text": rule.get("constraint", ""),
        })
    return out


def _load_saved_filter(name: str | None) -> dict | None:
    if not name:
        return None
    for f in scope_filter.list_saved_filters():
        if f.get("name") == name:
            return f
    return None


def _narrow_kb_to_scope(kb: dict, saved_filter: dict | None) -> tuple[dict, set[str] | None]:
    """Restrict the Knowledge Base to the saved filter's in-scope segments so
    Requirement Derivation covers exactly the same slice Scenario Generation will.
    Segment-level granularity only, matching generator.py's documented simplification.
    Returns (narrowed_kb, in_scope_segment_numbers) — the original kb is never mutated."""
    if not saved_filter:
        return kb, None
    in_scope = {s["segment_number"] for s in saved_filter.get("in_scope_segments", [])}
    if not in_scope:
        return kb, None

    segments = [s for s in kb.get("segments", []) if s.get("segment_number") in in_scope]
    # An element is in scope if any in-scope segment field references it.
    in_scope_elements = {
        (f.get("element_no") or "").strip()
        for seg in segments for f in seg.get("fields", []) if f.get("element_no")
    }
    in_scope_entity_ids = (
        {f"ENT-SEG-{n}" for n in in_scope}
        | {f"ENT-ELEM-{e}" for e in in_scope_elements}
        | {f.get("entity_id") for seg in segments for f in seg.get("fields", []) if f.get("entity_id")}
    )

    def _rule_in_scope(rule: dict) -> bool:
        related = rule.get("related_entity_ids") or []
        return any(rid in in_scope_entity_ids for rid in related)

    def _rel_in_scope(rel: dict) -> bool:
        return (rel.get("source_entity") in in_scope_entity_ids
                or rel.get("target_entity") in in_scope_entity_ids)

    narrowed = {
        **kb,
        "segments": segments,
        "attributes": [a for a in kb.get("attributes", [])
                       if str(a.get("element_number") or "").strip() in in_scope_elements],
        "business_rules": [r for r in kb.get("business_rules", []) if _rule_in_scope(r)],
        "relationships": [r for r in kb.get("relationships", []) if _rel_in_scope(r)],
        "supplemental_entities": [e for e in kb.get("supplemental_entities", [])
                                  if e.get("parent_segment_id") in in_scope_entity_ids
                                  or e.get("entity_id") in in_scope_entity_ids],
    }
    return narrowed, in_scope


def run(use_llm: bool = True, filter_name: str | None = None, progress_callback=None):
    print("Requirement Derivation \u2014 deriving testable requirements from the Knowledge Base")
    total_phases = 6

    def phase(n, detail):
        if progress_callback:
            progress_callback(n, total_phases, detail)

    phase(0, "Loading the knowledge base")
    if not KNOWLEDGE_BASE.exists():
        print(f"  MISSING: {KNOWLEDGE_BASE}")
        print("  Run Knowledge Assembly (build_knowledge_graph.py) first.")
        return

    kb = json.loads(KNOWLEDGE_BASE.read_text(encoding="utf-8"))
    saved_filter = _load_saved_filter(filter_name)
    scope_label = saved_filter["name"] if saved_filter else "full catalog (no scope filter)"
    kb, in_scope_segments = _narrow_kb_to_scope(kb, saved_filter)
    print(f"  Scope: {scope_label}")
    if in_scope_segments:
        print(f"  Narrowed to {len(in_scope_segments)} in-scope segment(s): "
              f"{len(kb.get('attributes', []))} element(s), {len(kb.get('business_rules', []))} business rule(s)")
    attributes = kb.get("attributes", [])

    requirements: list[dict] = []
    next_id = count(1)
    narrative_candidates = [a for a in attributes if ((a.get("attributes") or {}).get("processing_rules") or "").strip()]

    phase(1, f"Template path \u2014 deriving from {len(attributes)} element(s)")
    element_segment_map = segment_linker.build_element_segment_map(kb)
    name_segment_map = _build_name_segment_map(kb, element_segment_map)
    for attr in attributes:
        requirements.extend(_template_requirements(attr, next_id, element_segment_map))

    template_count = len(requirements)
    print(f"  Python-template path: {template_count} requirement(s) from {len(attributes)} elements")
    print(f"  Narrative processing_rules found on {len(narrative_candidates)} element(s)")

    phase(2, "Business-rule path")
    business_rule_reqs = _business_rule_requirements(kb, next_id)
    requirements.extend(business_rule_reqs)
    business_rule_count = len(business_rule_reqs)
    below_gate = sum(1 for r in business_rule_reqs if r.get("below_confidence_gate"))
    total_business_rules = len(kb.get("business_rules", []))
    print(f"  Business-rule-derived path: {business_rule_count} requirement(s) from {total_business_rules} "
          f"business rule(s) ({business_rule_count - below_gate} at confidence >= {BUSINESS_RULE_MIN_CONFIDENCE} "
          f"and unflagged, {below_gate} below gate -> requires_review)")

    phase(3, "Structural-field path")
    structural_reqs = _structural_field_requirements(kb, next_id)
    requirements.extend(structural_reqs)
    print(f"  Structural-field path: {len(structural_reqs)} requirement(s) from "
          f"{kb.get('counts', {}).get('fields', 0)} segment field(s), "
          f"{kb.get('counts', {}).get('message_fields', 0)} message field(s), "
          f"{kb.get('counts', {}).get('table_entity_fields', 0)} table field(s)")

    phase(4, "Relationship and supplemental-entity paths")
    relationship_reqs = _relationship_requirements(kb, next_id)
    requirements.extend(relationship_reqs)
    print(f"  Relationship-derived path: {len(relationship_reqs)} requirement(s) from "
          f"{len(kb.get('relationships', []))} relationship(s)")

    supplemental_reqs = _supplemental_entity_requirements(kb, next_id)
    requirements.extend(supplemental_reqs)
    print(f"  Supplemental-entity path: {len(supplemental_reqs)} requirement(s) from "
          f"{len(kb.get('supplemental_entities', []))} Step 4 entity(ies)")

    pending_llm_phrasing: list[dict] = []
    llm_available = use_llm and is_available()

    if narrative_candidates and llm_available:
        phase(5, f"LLM phrasing {len(narrative_candidates)} narrative processing rule(s)")
        phrased = llm_enricher.phrase_requirements(narrative_candidates)
        judged = llm_enricher.verify_with_retry(
            phrased, enrich_fn=llm_enricher.phrase_requirements,
            label_fields=["element_name", "phrased_statement"],
            page_resolver=lambda item: (item.get("source") or {}).get("page"),
            pages_dir=RAW_PAGES_DIR, max_retries=1,
        )
        phrased_count = 0
        for item in judged:
            statement = item.get("phrased_statement")
            if not statement:
                pending_llm_phrasing.append({
                    "entity_id": item.get("entity_id"), "element_name": item.get("element_name"),
                    "source_page": (item.get("source") or {}).get("page"),
                    "reason": "LLM phrasing failed for this element",
                })
                continue
            verdict = (item.get("llm_verdict") or {}).get("verdict")
            confidence_word = (item.get("llm_verdict") or {}).get("confidence", "LOW")
            traceback_match = (item.get("llm_verdict") or {}).get("traceback_match")
            requirements.append({
                "id": f"REQ-{SOURCE_ID}:{next(next_id):03d}",
                "statement": statement,
                "source_rule_id": item.get("entity_id"),
                "source_page": (item.get("source") or {}).get("page"),
                "derivation_method": "llm_phrased",
                "confidence_score": _CONFIDENCE_MAP.get(confidence_word, 0.5),
                "traceback_match": traceback_match,
                "segment_number": _resolve_segment_number([item.get("entity_id")], element_segment_map, name_segment_map),
                "category": _classify_category(statement, "llm_phrased"),
                "requirement_type": _classify_requirement_type("llm_phrased"),
                "flags": item.get("flags", []) if verdict in ("FAIL", "NEEDS_REVIEW") or traceback_match is False else [],
                "review_package": item.get("review_package"),
            })
            phrased_count += 1
        print(f"  LLM-phrased path: {phrased_count} requirement(s)")
    elif narrative_candidates:
        print("  [LLM Enricher] Bridge not available \u2014 narrative elements recorded as pending, not skipped")
        for attr in narrative_candidates:
            pending_llm_phrasing.append({
                "entity_id": attr.get("entity_id"), "element_name": attr.get("element_name"),
                "source_page": (attr.get("source") or {}).get("page"),
                "reason": "LLM bridge unavailable at derivation time",
            })

    flagged_count = sum(1 for r in requirements if r.get("flags"))
    by_method: dict[str, int] = {}
    for r in requirements:
        m = r.get("derivation_method") or "unknown"
        by_method[m] = by_method.get(m, 0) + 1
    message_segment_map = segment_linker.build_message_segment_map(kb)
    for r in requirements:
        r["scope_name"] = saved_filter.get("name") if saved_filter else None
        resolved = segment_linker.resolve_segments(r, element_segment_map, message_segment_map)
        r["segment_ids"] = resolved["segment_ids"]
        r["segment_assignment_method"] = resolved["segment_assignment_method"]
        apply_confidence(r)
    output = {
        "pipeline_step": "step5_requirements",
        "architecture_alignment": "Requirement Derivation (TDD §7.1) — feeds Scenario Management (§7.2)",
        "source_id": SOURCE_ID,
        "derived_at": datetime.now(timezone.utc).isoformat(),
        "state": "EXTRACTED",
        "scope": scope_label,
        "total_requirements": len(requirements),
        "by_derivation_method": by_method,
        "knowledge_base_coverage": kb.get("counts", {}),
        "flagged_for_review": flagged_count,
        "confidence_summary": score_catalog(requirements),
        "requirements": requirements,
        "pending_llm_phrasing": pending_llm_phrasing,
    }

    OUT_FILE.parent.mkdir(parents=True, exist_ok=True)
    OUT_FILE.write_text(json.dumps(output, indent=2, ensure_ascii=False), encoding="utf-8")
    print(f"\nDerived {len(requirements)} requirement(s) ({flagged_count} flagged, "
          f"{len(pending_llm_phrasing)} pending LLM phrasing)")
    print(f"Output: {OUT_FILE}")


if __name__ == "__main__":
    run()
