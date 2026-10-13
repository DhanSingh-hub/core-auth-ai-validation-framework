"""
Phase 1 — Single-Leg Examples generator.

Derives ONE full requirement -> scenario -> test_case -> test_data chain per
ATL105 segment in the global segment catalog (knowledge_base.json["segments"],
49 total), by calling the REAL pipeline building-block functions directly:

  - src/pipeline/step5_requirements/extractor.py   (_structural_field_requirements,
    the same deterministic "Structural-field path" Step 5 uses)
  - src/pipeline/scenarios/generator.py             (build_business_rule_scenarios,
    the same deterministic ASSERT-tier scenario builder Scenario Generation uses
    for field_constraint-derived requirements)
  - src/pipeline/test_generation/transaction_resolver.py (TransactionResolver,
    the same deterministic resolver Composer uses)
  - src/pipeline/test_generation/composer.py        (compose_test_case /
    compose_request, the same function the real Composer calls per scenario)
  - src/pipeline/test_generation/expected_response_builder.py (build_expected_response)
  - src/pipeline/test_generation/verification_agent.py (verify_with_bounded_retry)
  - src/pipeline/test_generation/qe_shaped_test_data.py (build_test_case_documents /
    write_documents, the same QE wire-format shaper)

This is deliberately NOT run_pipeline.py / extractor.run() / generator.run() /
composer.run(): those orchestrate the FULL catalog (thousands of items), write
to the real candidates/approved/ stores, and require the LLM bridge for the
narrative/business-rule/flow paths. Everything called here is the pure,
deterministic slice of each stage (no LLM call is made anywhere in this
script), scoped to exactly one requirement/scenario/test case per segment.

Read-only over assembled/output/**, config/**, step2_attributes/approved/**.
Writes only under phase_1_single_leg/.
"""
from __future__ import annotations

import itertools
import json
import re
import sys
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parent.parent
PIPELINE_ROOT = REPO_ROOT / "src" / "pipeline"
OUT_ROOT = Path(__file__).resolve().parent

for sub in ("shared", "scenario_scope", "step5_requirements", "scenarios", "test_generation"):
    p = str(PIPELINE_ROOT / sub)
    if p not in sys.path:
        sys.path.insert(0, p)

import segment_linker  # noqa: E402
import extractor  # noqa: E402  (step5_requirements)
import generator  # noqa: E402  (scenarios)
import transaction_resolver  # noqa: E402
import composer  # noqa: E402
import expected_response_builder  # noqa: E402
import verification_agent  # noqa: E402
import qe_shaped_test_data  # noqa: E402
import response_code_catalog  # noqa: E402
import test_data_config  # noqa: E402
import element_examples  # noqa: E402

KB_PATH = PIPELINE_ROOT / "assembled" / "output" / "knowledge_base.json"
SCOPE_LABEL = "phase_1_single_leg (one representative structural requirement per segment)"

REQS_DIR = OUT_ROOT / "requirements"
SCEN_DIR = OUT_ROOT / "scenarios"
TC_DIR = OUT_ROOT / "test_cases"
TD_DIR = OUT_ROOT / "test_data"
CHAINS_DIR = OUT_ROOT / "chains"
for d in (REQS_DIR, SCEN_DIR, TC_DIR, TD_DIR, CHAINS_DIR):
    d.mkdir(parents=True, exist_ok=True)

_STMT_FIELD_RE = re.compile(r"field (\S+) \(([^)]+)\)")


def _slug(text: str) -> str:
    return re.sub(r"[^A-Za-z0-9]+", "_", text or "").strip("_").upper()


def _elem_no_of(req: dict) -> str | None:
    for rid in req.get("related_entity_ids") or []:
        if str(rid).startswith("ENT-ELEM-"):
            return str(rid)[len("ENT-ELEM-"):]
    return None


def main() -> None:
    kb = json.loads(KB_PATH.read_text(encoding="utf-8"))
    templates = composer.load_templates()
    message_templates = templates.get("message_templates", {})
    attributes_by_elem_no = composer.load_attributes_by_elem_no()
    profiles = composer.load_inclusion_profiles()
    library = test_data_config.load_library()
    examples = element_examples.load_catalog()
    fmt = qe_shaped_test_data.FormatMaps(qe_shaped_test_data.load_format_map())
    codes_catalog = response_code_catalog.load_catalog()
    codes = codes_catalog.get("codes", [])
    codes_by_value = {c["code"]: c for c in codes if c.get("code")}
    behavior = expected_response_builder.load_processor_behavior()

    resolver = transaction_resolver.TransactionResolver(
        kb=kb, templates=templates, requirements={}, attributes={})

    # ---- Requirement Derivation: the real Structural-field path, called once ----
    next_req_id = itertools.count(1)
    structural_reqs = extractor._structural_field_requirements(kb, next_req_id)  # noqa: SLF001
    element_segment_map = segment_linker.build_element_segment_map(kb)
    message_segment_map = segment_linker.build_message_segment_map(kb)

    reqs_by_segment: dict[str, list[dict]] = {}
    for r in structural_reqs:
        if r.get("derivation_method") == "field_constraint":
            reqs_by_segment.setdefault(r.get("segment_number"), []).append(r)

    segments = kb.get("segments", [])
    segments_sorted = sorted(
        segments, key=lambda s: int(re.sub(r"\D", "", s.get("segment_number") or "0") or 0))

    next_sc_id = itertools.count(1)
    next_tc_id = itertools.count(1)

    selected_requirements: list[dict] = []
    selected_scenarios: list[dict] = []
    selected_test_cases: list[dict] = []
    index_rows: list[dict] = []

    for seg in segments_sorted:
        seg_no = seg.get("segment_number")
        seg_name = seg.get("name") or ""
        seg_fields = seg.get("fields", [])
        row = {
            "segment_number": seg_no,
            "segment_name": seg_name,
            "field_count": len(seg_fields),
        }

        if not seg_fields:
            row["status"] = "GAP_NO_FIELDS"
            row["gap_reason"] = ("Segment has zero extracted fields in knowledge_base.json — "
                                  "extractor._structural_field_requirements() has nothing to derive "
                                  "a requirement from for this segment.")
            index_rows.append(row)
            continue

        candidates = reqs_by_segment.get(seg_no, [])
        required_candidates = [r for r in candidates if r.get("roc") == "R"]
        # Prefer a segment-specific Required field over a ubiquitous envelope field
        # (Segment Type/Segment Length/Information Byte, transaction_resolver's own
        # KNOWN_UBIQUITOUS_ELEMENT_NOS) as the representative requirement: picking a
        # ubiquitous field would make TransactionResolver's related_entity_ids-union
        # fallback (the only path field_constraint scenarios ever take, since their
        # `parameters` is always empty) match dozens of unrelated transactions and
        # get silently truncated to an alphabetical slice that may not even contain
        # this segment's own real transaction — verified against message_templates
        # further down, not assumed.
        non_ubiquitous_required = [r for r in required_candidates if _elem_no_of(r) not in
                                   transaction_resolver.KNOWN_UBIQUITOUS_ELEMENT_NOS]
        chosen = (non_ubiquitous_required or required_candidates or candidates or [None])[0]
        if chosen is None:
            row["status"] = "GAP_NO_REQUIREMENT_DERIVED"
            row["gap_reason"] = "Segment has fields but none produced a field_constraint requirement."
            index_rows.append(row)
            continue

        # ---- finish the real per-requirement post-processing tail (extractor.run() does
        # this to EVERY requirement right before writing candidates/requirement_candidates.json) ----
        req = dict(chosen)
        req["evidence_ref"] = {"source_id": extractor.SOURCE_ID, "entity_id": req.get("source_rule_id"),
                                "page": req.get("source_page")}
        req["scope_name"] = SCOPE_LABEL
        resolved_seg = segment_linker.resolve_segments(req, element_segment_map, message_segment_map)
        req["segment_ids"] = resolved_seg["segment_ids"]
        req["segment_assignment_method"] = resolved_seg["segment_assignment_method"]
        req["segment_attribution"] = resolved_seg["segment_attribution"]
        if "unresolved_reason" in resolved_seg:
            req["unresolved_reason"] = resolved_seg["unresolved_reason"]
        extractor.apply_confidence(req)
        req["id"] = f"REQ-{extractor.SOURCE_ID}:{next(next_req_id):04d}"

        m = _STMT_FIELD_RE.search(req.get("statement") or "")
        field_no, field_name = (m.group(1), m.group(2)) if m else (None, None)
        row["field_no"] = field_no
        row["field_name"] = field_name
        row["roc"] = req.get("roc")
        row["requirement_id"] = req["id"]

        # ---- Scenario Generation: the real business-rule/ASSERT-tier builder ----
        scenarios = generator.build_business_rule_scenarios(
            {"requirements": [req]}, kb, None, SCOPE_LABEL, param_path_covered_ids=set())
        if not scenarios:
            row["status"] = "GAP_NO_SCENARIO_BUILT"
            row["gap_reason"] = ("derivation_method 'field_constraint' is in generator.RULE_METHODS but "
                                  "build_business_rule_scenarios produced nothing (unexpected) — see "
                                  "requirement for details.")
            selected_requirements.append(req)
            index_rows.append(row)
            continue
        sc = scenarios[0]
        sc["id"] = f"SC-{next(next_sc_id):04d}"
        sc["spec_id"] = generator.SOURCE_ID
        sc["spec_version"] = generator.SPEC_VERSION
        sc["scope_name"] = SCOPE_LABEL
        sc["scope_segments"] = None
        sc["scope_mode"] = None
        generator.apply_confidence(sc)
        row["scenario_id"] = sc["id"]

        # ---- Test Case Composition: the real TransactionResolver + Composer ----
        # field_constraint scenarios always carry empty `parameters` (see
        # generator.build_business_rule_scenarios), so the resolver ALWAYS takes its
        # low-confidence RELATED_ENTITY_FALLBACK path (a union across every element a
        # requirement references, including ubiquitous envelope fields like "Segment
        # Type") — never a discriminating-parameter match. A union can legitimately
        # include a transaction that does NOT actually list this segment in its own
        # template (verified independently below against message_templates itself)
        # -- composing against such a transaction would silently mislabel a different
        # segment's fields as this segment's example, so it is never trusted blind.
        resolution = resolver.resolve(sc)
        tx_types = resolution.get("transaction_types") or []
        row["resolver_flags"] = resolution.get("flags", [])
        row["resolved_transaction_types"] = tx_types

        selected_requirements.append(req)
        selected_scenarios.append(sc)

        if not tx_types:
            row["status"] = "GAP_NO_CONSISTENT_TRANSACTION"
            row["gap_reason"] = ("TransactionResolver found no transaction template containing this "
                                  "segment — matches the real Composer's own "
                                  "skipped_no_consistent_transaction accounting; this segment is not "
                                  "reachable via any message_templates entry today.")
            index_rows.append(row)
            continue

        genuine_tx = [t for t in tx_types if seg_no in
                      {s.get("segment_number") for s in message_templates.get(t, {}).get("segments", [])}]
        spurious_tx = [t for t in tx_types if t not in genuine_tx]
        if not genuine_tx:
            row["status"] = "GAP_RESOLVER_FALLBACK_UNRELIABLE"
            row["gap_reason"] = ("Every transaction TransactionResolver proposed was reached only via "
                                  "the ubiquitous-element union fallback, and NONE of them actually list "
                                  f"segment {seg_no} in their own message_templates segment list — "
                                  "composing against any of them would mislabel a different segment's "
                                  "fields as this segment's example, so no test case is produced.")
            row["candidate_transactions_rejected_as_spurious"] = spurious_tx
            index_rows.append(row)
            continue

        tx = genuine_tx[0]
        row["representative_transaction"] = tx
        if len(genuine_tx) > 1:
            row["also_participates_in_transactions"] = genuine_tx[1:]
        if spurious_tx:
            row["resolver_also_proposed_but_rejected_as_spurious"] = spurious_tx

        tx_template = message_templates.get(tx)
        if not tx_template:
            row["status"] = "GAP_NO_TEMPLATE"
            row["gap_reason"] = f"resolved transaction {tx!r} has no entry in message_templates."
            index_rows.append(row)
            continue

        tc = composer.compose_test_case(sc, tx, tx_template, name_to_elem_no={},
                                         attributes_by_elem_no=attributes_by_elem_no,
                                         profiles=profiles, library=library, examples=examples)
        tc["id"] = f"TC-{next(next_tc_id):06d}"

        tc["expected_response"] = expected_response_builder.build_expected_response(
            tc, codes_by_value, codes, message_templates, attributes_by_elem_no, behavior)

        tc["verification"] = verification_agent.verify_with_bounded_retry(
            tc, requirements_by_id={req["id"]: req}, attributes_by_elem_no=attributes_by_elem_no,
            examples=examples)

        selected_test_cases.append(tc)

        docs = qe_shaped_test_data.build_test_case_documents(fmt, tc)
        qe_shaped_test_data.write_documents(docs, TD_DIR)

        row["status"] = "OK"
        row["test_case_id"] = tc["id"]
        row["test_data_files"] = [f"{tc['id']}.json", f"{tc['id']}.meta.json"]
        index_rows.append(row)

        (CHAINS_DIR / f"segment_{_slug(seg_no)}.json").write_text(
            json.dumps({"segment_number": seg_no, "segment_name": seg_name,
                        "requirement": req, "scenario": sc, "test_case": tc}, indent=2, ensure_ascii=False),
            encoding="utf-8")

    for row in index_rows:
        if row.get("status") not in ("OK",) and "requirement_id" in row and row.get("status") not in (
                None, "GAP_NO_SCENARIO_BUILT"):
            seg_no = row["segment_number"]
            req = next((r for r in selected_requirements if r.get("segment_number") == seg_no), None)
            if req is not None:
                (CHAINS_DIR / f"segment_{_slug(seg_no)}.json").write_text(
                    json.dumps({"segment_number": seg_no, "segment_name": row["segment_name"],
                                "requirement": req, "gap": row}, indent=2, ensure_ascii=False),
                    encoding="utf-8")

    (REQS_DIR / "requirement_candidates.json").write_text(
        json.dumps({"pipeline_step": "step5_requirements", "scope": SCOPE_LABEL,
                    "total_requirements": len(selected_requirements),
                    "requirements": selected_requirements}, indent=2, ensure_ascii=False),
        encoding="utf-8")
    (SCEN_DIR / "candidate_scenarios.json").write_text(
        json.dumps({"pipeline_step": "scenarios", "scope": SCOPE_LABEL,
                    "total_scenarios": len(selected_scenarios),
                    "scenarios": selected_scenarios}, indent=2, ensure_ascii=False),
        encoding="utf-8")
    (TC_DIR / "test_case_candidates.json").write_text(
        json.dumps({"pipeline_step": "test_generation", "scope": SCOPE_LABEL,
                    "total_test_cases": len(selected_test_cases),
                    "test_cases": selected_test_cases}, indent=2, ensure_ascii=False),
        encoding="utf-8")

    ok = sum(1 for r in index_rows if r.get("status") == "OK")
    gaps = [r for r in index_rows if r.get("status") != "OK"]
    (OUT_ROOT / "INDEX.json").write_text(
        json.dumps({
            "generated_by": "phase_1_single_leg/generate_single_leg_examples.py",
            "scope": SCOPE_LABEL,
            "total_segments": len(segments_sorted),
            "ok_full_chains": ok,
            "gaps": len(gaps),
            "segments": index_rows,
        }, indent=2, ensure_ascii=False),
        encoding="utf-8")

    print(f"Segments processed: {len(segments_sorted)}")
    print(f"Full chains (requirement -> scenario -> test_case -> test_data): {ok}")
    print(f"Gaps (documented, not fabricated): {len(gaps)}")
    for r in gaps:
        print(f"  {r['segment_number']:>4} {r.get('segment_name', ''):<45} {r['status']}")


if __name__ == "__main__":
    main()
