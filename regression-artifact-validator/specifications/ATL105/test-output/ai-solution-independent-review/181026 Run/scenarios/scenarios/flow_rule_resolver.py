"""Flow rule resolver (Step 9, 2026-10 multi-leg axis coverage plan).

Multi-leg flow links (config/transaction_flows.json) used to cite a business
rule by its ID directly (e.g. "source_rule_id": "BR-38-5"). Rule IDs are
POSITIONAL (BR-<page>-<n>) and are reassigned every time the knowledge base is
rebuilt from a re-parsed spec PDF (confirmed: the current KB's BR-38-5 is a
different statement than the one originally cited; the spec's real "TOR must be
sent from the same device" rule is now BR-38-4). An ID citation silently
degrades to either nothing (no match) or the WRONG rule (wrong statement) the
moment the KB is rebuilt.

This module resolves a link's own `spec_anchor` (content, not an ID) against
whatever knowledge_base.json is current RIGHT NOW: `keywords` (all must appear,
case/whitespace-normalised, in the rule's own statement+constraint+condition
text — KB statements are an LLM paraphrase of the spec, not verbatim, so
keyword-overlap is used rather than an exact quote match), narrowed by
`element_no` (must appear in the rule's own related_entity_ids as
ENT-ELEM-<n>) and `page_hint` (closest page wins a remaining tie) when more
than one rule matches on keywords alone.

Never guesses: zero matches is UNRESOLVED, more than one remaining after every
tie-break is AMBIGUOUS. Both are reported findings, never silently resolved to
an arbitrary pick — and (generator.py's own contract) must never be treated as
a structural error that would exclude the whole flow from a run.
"""

from __future__ import annotations

import re

_WS_RE = re.compile(r"\s+")
_PUNCT_RE = re.compile(r"[^a-z0-9 ]")


def _normalize(text: str) -> str:
    text = (text or "").lower()
    text = _PUNCT_RE.sub(" ", text)
    return _WS_RE.sub(" ", text).strip()


def _rule_text(rule: dict) -> str:
    return _normalize(f"{rule.get('statement', '')} {rule.get('constraint', '')} {rule.get('condition', '')}")


def find_matching_rules(anchor: dict, business_rules: list[dict]) -> list[dict]:
    """`anchor`: {"keywords": [str, ...], "element_no": str | None, "page_hint": int | None}.
    Returns every business rule whose own text contains ALL the anchor's keywords
    (substring match on normalised text), narrowed by element_no/page_hint when
    more than one remains. Order is not significant — callers check len(), never [0]."""
    keywords = [_normalize(k) for k in (anchor.get("keywords") or []) if k]
    if not keywords:
        return []
    candidates = [r for r in business_rules if all(kw in _rule_text(r) for kw in keywords)]
    if len(candidates) <= 1:
        return candidates

    element_no = anchor.get("element_no")
    if element_no:
        target = f"ENT-ELEM-{element_no}"
        elem_filtered = [r for r in candidates if target in (r.get("related_entity_ids") or [])]
        if elem_filtered:
            candidates = elem_filtered
            if len(candidates) == 1:
                return candidates

    page_hint = anchor.get("page_hint")
    if page_hint is not None and len(candidates) > 1:
        paged = [(r, (r.get("source") or {}).get("page")) for r in candidates]
        with_page = [(r, p) for r, p in paged if p is not None]
        if with_page:
            closest = min(abs(p - page_hint) for _, p in with_page)
            at_closest = [r for r, p in with_page if abs(p - page_hint) == closest]
            if len(at_closest) == 1:
                return at_closest
            candidates = at_closest or candidates
    return candidates


def resolve_anchor(anchor: dict, business_rules: list[dict]) -> tuple[str | None, str, list[str]]:
    """Returns (rule_id_or_None, status, candidate_rule_ids). status is one of
    RESOLVED / UNRESOLVED / AMBIGUOUS. `candidate_rule_ids` is empty for RESOLVED
    (the single answer IS the rule_id), and lists every remaining candidate for
    AMBIGUOUS (diagnostic only — never used to pick one)."""
    candidates = find_matching_rules(anchor, business_rules)
    if len(candidates) == 1:
        return candidates[0].get("rule_id"), "RESOLVED", []
    if not candidates:
        return None, "UNRESOLVED", []
    return None, "AMBIGUOUS", sorted({r.get("rule_id") for r in candidates if r.get("rule_id")})


def resolve_flow_links(flow: dict, business_rules: list[dict]) -> list[dict]:
    """Resolves every link (across every step) and the step-1 fixed_elements pin
    that declares a `spec_anchor`, returning one record per anchored item:
    {step, basis ("flow_link"|"flow_pin"), link_type, element_name, rule_id,
    status, candidate_rule_ids, cached_source_rule_id}. A link/pin with no
    spec_anchor at all is skipped (nothing to resolve) — NOT reported as
    unresolved, since it was never anchored in the first place (e.g. an
    axis-value-only citation, or a deliberately unmodeled amount_relation —
    see UNRESOLVED_KB_GAP in the flow's own link `rule` text)."""
    out = []
    for step in flow.get("steps") or []:
        step_no = step.get("step")
        for link in step.get("links") or []:
            anchor = link.get("spec_anchor")
            if not anchor:
                continue
            rule_id, status, candidates = resolve_anchor(anchor, business_rules)
            out.append({
                "step": step_no, "basis": "flow_link", "link_type": link.get("type"),
                "element_name": link.get("element_name"), "rule_id": rule_id, "status": status,
                "candidate_rule_ids": candidates, "cached_source_rule_id": link.get("source_rule_id"),
            })
        anchor = (step.get("fixed_elements_spec_anchor") or {})
        if anchor:
            rule_id, status, candidates = resolve_anchor(anchor, business_rules)
            out.append({
                "step": step_no, "basis": "flow_pin", "link_type": None, "element_name": None,
                "rule_id": rule_id, "status": status, "candidate_rule_ids": candidates,
                "cached_source_rule_id": None,
            })
    return out
