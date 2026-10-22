"""
LLM Scenario Architect — multi-leg obligation gap-check + generative proposal pass.

Runs inside generator.run(), gated by the same `use_llm` flag as judge_with_traceback
(shared/llm_enricher.py). Two of the multi-leg obligation table's rows are already fully
covered elsewhere and are NOT re-checked here:
  - chain-identity mismatch: auto-derived for EVERY flow test case downstream, universally,
    by reporting/suite_catalog.py::_derive_mismatch_twin — never a scenario-level gap.
  - missing-carry: a declared `step_overrides` negative variant now exists on every eligible
    flow in config/transaction_flows.json (see that file's per-flow "missing-carry-*"/
    "missing-sequence-number-carry"/"non-retransmitted-retry" variants).

The one obligation with no builder anywhere is "orphan follow-on": a follow-on leg
(Completion/Void/Reversal/TOR/...) submitted standalone, with no corresponding prior leg
ever having existed for the linked field's value it would normally carry. This module closes
that gap, using the LLM ONLY to phrase a name/rationale — never to invent a value, field, or
outcome.

Fabrication guardrail (mirrors .github/skills/qe-scenario-architect/references/anti-patterns.md
rule "never copy/invent a value the spec doesn't document"): every proposal is built entirely
from the flow's OWN already-declared step/link/source_rule_id data (deterministic Python).
The LLM's only inputs are those same facts; its only outputs are a short `name` and one-
sentence `rationale`, and every returned proposal is re-validated (length, only references
already-known element/transaction names, no fabricated-looking literal) before it is allowed
to become a scenario. Anything that fails validation is dropped with a logged reason.

Out-of-order leg transmission is a real obligation-table row with no way to express it
anywhere in this schema (no "transmission order" concept exists in the test-case/executor
contract) — flagged EXTERNAL_FIXTURE_REQUIRED by compute_flow_gaps() rather than force-built.
"""

from __future__ import annotations

import json
import re
import sys
from pathlib import Path

HERE = Path(__file__).resolve().parent
sys.path.insert(0, str(HERE.parent / "shared"))
from llm_client import call, is_available  # noqa: E402
import skill_grounding  # noqa: E402
import vocab  # noqa: E402

# forensic-review 08 C3(c) — routed through vocab.canonical() (same discipline as
# generator.py's _NEGATIVE_CLASSES) instead of a bare literal, so a schema drift raises
# loudly at import time rather than producing a scenario the skill validator then flags.
_ORPHAN_FOLLOWON_NEGATIVE_CLASS = vocab.canonical("orphan_followon", "scenario.negative_class")

# A 6+ char alnum token that CONTAINS AT LEAST ONE DIGIT looks like a fabricated literal
# value (PAN, terminal id, response code) rather than ordinary English business prose —
# plain alphabetic words (e.g. "without", "reference") are never flagged, only digit-
# bearing tokens that aren't already a substring of an already-known name.
_LITERAL_LEAK_RE = re.compile(r"\b(?=[0-9A-Za-z]*[0-9])[0-9A-Za-z]{6,}\b")

ORPHAN_SYSTEM = """
You are a payments QE scenario reviewer. You will be given ONE chained transaction flow's
already-declared final step and linking rules (element names, source rule ids, business
rules) — extracted deterministically from config, never invented.

Your ONLY job: write a short human-readable `name` and one-sentence `rationale` for a NEW
negative test scenario: the flow's follow-on leg (its LAST step) submitted completely alone,
with no corresponding prior leg ever having existed for the linked field's value it would
normally have carried. This is the "orphan follow-on" test.

Rules:
- Reference ONLY element names, transactions, and rule text already given in the input.
  Never introduce a new field, code, or example value.
- Do not state or imply an approval/decline outcome — that is undocumented processor logic.
- `name` must be under 15 words. `rationale` must be under 30 words, one sentence.

Each input item carries an "idx". Return a JSON array of the same length, one object per
input, each echoing its input's "idx" unchanged:
  "idx": the input item's idx, unchanged
  "name": short scenario name
  "rationale": one-sentence business rationale

Return only the JSON array. No markdown fences, no text before or after it.
""".strip()


def _system_prompt() -> str:
    """ORPHAN_SYSTEM plus the qe-scenario-architect skill's own rule text (SKILL.md +
    anti-patterns.md + coverage-model.md's oracle classes) — loaded fresh from disk via
    shared/skill_grounding.py so editing the skill changes this prompt without a code change."""
    grounding = skill_grounding.load_grounding_block()
    return f"{ORPHAN_SYSTEM}\n\n{grounding}" if grounding else ORPHAN_SYSTEM


def _has_variant_matching(flow: dict, predicate) -> bool:
    return any(predicate(v) for v in (flow.get("variants") or []))


def compute_flow_gaps(flows_config: dict) -> list[dict]:
    """Deterministic obligation-table gap check per flow. Never calls the LLM.

    Returns one entry per (flow, obligation) — status is "MET", "GAP" (buildable, no
    builder exists yet) or "EXTERNAL_FIXTURE_REQUIRED" (no builder CAN exist with today's
    schema). Flows that are themselves the negative class (is_negative at the flow level)
    are skipped — the obligation table doesn't apply a second time to a flow that already
    IS the deliberate violation.
    """
    gaps: list[dict] = []
    for flow in flows_config.get("flows") or []:
        if flow.get("is_negative"):
            continue
        steps = flow.get("steps") or []
        if len(steps) < 2:
            continue
        has_missing_carry = _has_variant_matching(
            flow, lambda v: bool(v.get("is_negative")) and bool(v.get("step_overrides")))
        gaps.append({
            "flow_id": flow["id"], "obligation": "missing_carry",
            "status": "MET" if has_missing_carry else "GAP",
            "detail": "declared step_overrides negative variant found" if has_missing_carry
                      else "no step_overrides negative variant declared",
        })
        has_orphan = _has_variant_matching(flow, lambda v: v.get("id", "").startswith("orphan-"))
        gaps.append({
            "flow_id": flow["id"], "obligation": "orphan_followon",
            "status": "MET" if has_orphan else "GAP",
            "detail": "orphan follow-on scenario already built for this flow" if has_orphan
                      else "no orphan follow-on scenario built for this flow",
        })
        gaps.append({
            "flow_id": flow["id"], "obligation": "chain_identity_mismatch", "status": "MET",
            "detail": "auto-derived for every flow test case downstream by "
                      "reporting/suite_catalog.py::_derive_mismatch_twin",
        })
        gaps.append({
            "flow_id": flow["id"], "obligation": "out_of_order", "status": "EXTERNAL_FIXTURE_REQUIRED",
            "detail": "no transmission-order concept exists in the test-case/executor contract; "
                      "cannot be expressed as a static scenario",
        })
    return gaps


def _orphan_context(flow: dict) -> dict | None:
    steps = flow.get("steps") or []
    if len(steps) < 2:
        return None
    last = steps[-1]
    links = [link for link in (last.get("links") or [])
             if link.get("type") in ("carry_request_field", "carry_response_field", "retransmission")]
    if not links:
        return None
    return {
        "flow_id": flow["id"],
        "flow_name": flow.get("name"),
        "last_step_transaction": last.get("transaction"),
        "links": [{"element_name": l.get("element_name"), "rule": l.get("rule"),
                   "source_rule_id": l.get("source_rule_id"), "source_page": l.get("source_page")}
                  for l in links],
    }


def _validate_proposal(item: dict, ctx: dict) -> str | None:
    """Returns a rejection reason, or None if the proposal is acceptable."""
    name = (item.get("name") or "").strip()
    rationale = (item.get("rationale") or "").strip()
    if not name or not rationale:
        return "empty name/rationale"
    if len(name.split()) > 20 or len(rationale.split()) > 40:
        return "name/rationale too long"
    known = {ctx["last_step_transaction"], ctx["flow_name"]} | {l["element_name"] for l in ctx["links"]}
    known |= skill_grounding.load_domain_vocabulary()
    known = {k for k in known if k}
    for match in _LITERAL_LEAK_RE.findall(f"{name} {rationale}"):
        if not any(match in k for k in known):
            return f"possible fabricated literal in LLM text: {match!r}"
    return None


def propose_orphan_scenarios(flows_config: dict, gaps: list[dict],
                              requirements_by_rule_id: dict[str, dict] | None = None,
                              batch_size: int = 10) -> tuple[list[dict], list[dict]]:
    """Returns (scenario_stubs, rejections). scenario_stubs are missing "fingerprint"/
    "dedup_key" (caller assigns those the same way every other builder in generator.py
    does) but are otherwise complete scenario dicts. Calls the LLM only for flows with an
    orphan_followon GAP; returns ([], []) immediately if the bridge is unavailable —
    caller is responsible for flagging that the pass was skipped."""
    requirements_by_rule_id = requirements_by_rule_id or {}
    flow_ids = {g["flow_id"] for g in gaps if g["obligation"] == "orphan_followon" and g["status"] == "GAP"}
    if not flow_ids or not is_available():
        return [], []
    flows_by_id = {f["id"]: f for f in flows_config.get("flows") or []}
    contexts = []
    for fid in sorted(flow_ids):
        ctx = _orphan_context(flows_by_id.get(fid) or {})
        if ctx:
            contexts.append(ctx)
    if not contexts:
        return [], []

    scenarios: list[dict] = []
    rejections: list[dict] = []
    for start in range(0, len(contexts), batch_size):
        batch = contexts[start:start + batch_size]
        payload = [{"idx": i, "flow_name": c["flow_name"], "last_step_transaction": c["last_step_transaction"],
                    "links": c["links"]} for i, c in enumerate(batch)]
        response = call(_system_prompt(), json.dumps(payload, ensure_ascii=False), max_tokens=2048)
        if not response:
            rejections.extend({"flow_id": c["flow_id"], "reason": "LLM_CALL_FAILED"} for c in batch)
            continue
        try:
            text = response.strip().removeprefix("```json").removeprefix("```").removesuffix("```").strip()
            results = json.loads(text)
        except json.JSONDecodeError:
            rejections.extend({"flow_id": c["flow_id"], "reason": "LLM_RESPONSE_UNPARSEABLE"} for c in batch)
            continue
        by_idx = {r.get("idx"): r for r in results if isinstance(r, dict)}
        for i, ctx in enumerate(batch):
            item = by_idx.get(i)
            if item is None:
                rejections.append({"flow_id": ctx["flow_id"], "reason": "LLM_MISSING_IDX"})
                continue
            reject_reason = _validate_proposal(item, ctx)
            if reject_reason:
                rejections.append({"flow_id": ctx["flow_id"], "reason": reject_reason, "raw": item})
                continue
            link = ctx["links"][0]
            scenarios.append({
                "name": f"Flow — {ctx['flow_name']} — orphan follow-on: {item['name'].strip()}",
                "requirement_id": (requirements_by_rule_id.get(link.get("source_rule_id")) or {}).get("id"),
                "type": "negative",
                "negative_class": _ORPHAN_FOLLOWON_NEGATIVE_CLASS,
                "parameters": {},
                "flow_id": ctx["flow_id"],
                "is_negative": True,
                "target_transaction": ctx["last_step_transaction"],
                "related_entity_ids": [],
                "source_rule_id": link.get("source_rule_id"),
                "source_page": link.get("source_page"),
                "business_rule_assertion": item["rationale"].strip(),
                "expected_response_code": None,
                "expected_decline_code": None,
                "oracle_authority": (
                    f"config/transaction_flows.json declared flow {ctx['flow_id']} — this follow-on "
                    f"transaction is sent standalone, with no prior leg ever establishing its "
                    f"{link.get('element_name')!r} value; outcome NOT asserted — processor logic absent "
                    "from the ATL105 spec"),
                "flags": ["LLM_PROPOSED_ORPHAN_FOLLOWON: name/rationale phrased by LLM Scenario Architect "
                          "pass, structural facts are config-derived; validated against known "
                          "elements before merge",
                          "NEEDS_AUTHORIZER_DOC: outcome requires processor business rules"],
                "source": "llm_scenario_architect",
            })
    return scenarios, rejections
