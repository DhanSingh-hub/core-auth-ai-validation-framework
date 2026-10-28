"""
Flow Expansion Axes (Stage H1/H3) — resolves the named, reusable axes declared
in config/flow_expansion_axes.json into real values at scenario-generation
time. The axis config file itself carries only symbolic REFERENCES (never a
real Fiserv business value); this module is where a reference actually
becomes a value list, read from whichever existing catalog the axis's
`source` names.

A source that is missing, empty, or refused (untrusted attribute parse)
degrades to an EMPTY axis plus a warning string — never a crash, never a
silently-invented value. Callers (scenarios/generator.py) are responsible for
reporting that warning loudly and skipping/limiting whatever depended on it.
"""

from __future__ import annotations

import json
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]  # src/pipeline
AXES_CONFIG_PATH = ROOT / "config" / "flow_expansion_axes.json"
TEST_AXES_PATH = ROOT / "config" / "test_axes.json"
TX_CODES_PATH = ROOT / "config" / "transaction_type_codes.json"
FIELD_INCLUSION_PROFILES_PATH = ROOT / "config" / "field_inclusion_profiles.json"
ATTRIBUTE_CATALOG_PATH = ROOT / "step2_attributes" / "approved" / "attribute_catalog.json"

sys.path.insert(0, str(ROOT / "test_generation"))
import test_data_config  # noqa: E402
import value_fill  # noqa: E402

sys.path.insert(0, str(ROOT / "shared"))
import flow_axis_provenance  # noqa: E402


def _load_json(path: Path) -> dict:
    if not path.exists():
        return {}
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError):
        return {}


def load_axis_definitions(path: Path | None = None) -> dict:
    return _load_json(path or AXES_CONFIG_PATH)


def _resolve_test_axes(axis_def: dict) -> tuple[list[str], str | None]:
    ref = axis_def.get("ref")
    data = _load_json(TEST_AXES_PATH)
    axis = next((a for a in data.get("axes", []) if a.get("name") == ref), None)
    if axis is None:
        return [], f"test_axes has no axis named {ref!r}"
    if axis.get("side") == "response":
        return [], f"test_axes axis {ref!r} holds response values (side: response); not a request input"
    values = [v["value"] for v in axis.get("values", []) if v.get("value")]
    if not values:
        return [], f"test_axes axis {ref!r} has no values"
    return values, None


def _resolve_test_data_library(axis_def: dict, context: dict) -> tuple[list[str], str | None]:
    segment, element = axis_def.get("segment"), axis_def.get("element")
    library = test_data_config.load_library()
    entry = test_data_config.lookup(library, segment, element, context.get("transaction_type"))
    if entry is None:
        return [], f"test_data_library has no entry for segment {segment!r} element {element!r}"
    if entry.get("fixed") is not None:
        return [str(entry["fixed"])], None
    values = [str(v) for v in entry.get("values") or []]
    if not values:
        return [], f"test_data_library entry for {segment!r}/{element!r} has no fixed/values"
    return values, None


def _resolve_attribute_catalog(axis_def: dict) -> tuple[list[str], str | None]:
    elem_no = axis_def.get("element_no")
    data = _load_json(ATTRIBUTE_CATALOG_PATH)
    attribute = next((a for a in data.get("attributes", []) if a.get("element_number") == elem_no), None)
    if attribute is None:
        return [], f"attribute_catalog has no element {elem_no!r}"
    flags = set(attribute.get("flags", []) or [])
    if flags & value_fill.UNTRUSTED_VALID_VALUES_FLAGS:
        return [], (f"attribute_catalog element {elem_no!r} carries an untrusted valid_values flag "
                    f"({sorted(flags & value_fill.UNTRUSTED_VALID_VALUES_FLAGS)}) — refused as an axis source")
    parsed = (attribute.get("attributes", {}) or {}).get("valid_values_parsed", []) or []
    values = []
    for entry in parsed:
        candidate = (entry.get("value") or "").strip()
        if not candidate or "any" in candidate.lower():
            continue
        token, _ = value_fill.parse_leading_token(candidate)
        if token:
            values.append(token)
    if not values:
        return [], f"attribute_catalog element {elem_no!r} has no usable valid_values_parsed entries"
    return values, None


def _resolve_transaction_type_codes(axis_def: dict) -> tuple[list[str], str | None]:
    data = _load_json(TX_CODES_PATH)
    codes = data.get("codes", [])
    flt = axis_def.get("filter") or {}
    contains = flt.get("meaning_contains")
    if contains:
        codes = [c for c in codes if contains.lower() in (c.get("meaning") or "").lower()]
    source_rule_id = flt.get("source_rule_id")
    if source_rule_id:
        # 2026-10-08 — meaning_contains alone over-matches here: code 'S''s own meaning
        # ("Cancellation (Authorization Only Reversal)") also contains the substring
        # "Authorization Only", despite S being a follow-on reversal code, never a valid
        # origin-leg alternative. Filtering on the codes' own shared sourceRuleId (every
        # code BR-97-5 (p.97) actually grounds: '3', '5', 'B') is exact, never a guess.
        codes = [c for c in codes if c.get("sourceRuleId") == source_rule_id]
    values = [c["code"] for c in codes if c.get("code")]
    if not values:
        return [], f"transaction_type_codes filter {flt!r} matched nothing"
    return values, None


def _resolve_field_inclusion_profiles() -> tuple[list[str], str | None]:
    data = _load_json(FIELD_INCLUSION_PROFILES_PATH)
    values = list((data.get("profiles") or {}).keys())
    if not values:
        return [], "field_inclusion_profiles has no profiles"
    return values, None


def resolve_axis(axis_name: str, axis_def: dict, context: dict | None = None) -> tuple[list[str], str | None]:
    """Returns (values, warning). `values` is always a list (possibly empty);
    `warning` is a human-readable string when the axis degraded to empty,
    else None. `context`: {"transaction_type": ...} — only test_data_library
    consults it today."""
    context = context or {}
    source = axis_def.get("source")
    if source == "test_axes":
        return _resolve_test_axes(axis_def)
    if source == "test_data_library":
        return _resolve_test_data_library(axis_def, context)
    if source == "attribute_catalog":
        return _resolve_attribute_catalog(axis_def)
    if source == "transaction_type_codes":
        return _resolve_transaction_type_codes(axis_def)
    if source == "field_inclusion_profiles":
        return _resolve_field_inclusion_profiles()
    if source in ("generator_profiles", "literal"):
        values = list(axis_def.get("values") or [])
        if not values:
            return [], f"{axis_name!r} ({source}) declares no values"
        return values, None
    return [], f"{axis_name!r} has an unknown source {source!r}"


def resolve_ref(ref: str, context: dict | None = None, axes_config: dict | None = None) -> tuple[list[str], str | None]:
    """Convenience: resolve by axis NAME (as used in a flow variant's
    axes[].ref), loading config/flow_expansion_axes.json once if not given."""
    axes_config = axes_config if axes_config is not None else load_axis_definitions()
    axis_def = (axes_config.get("axes") or {}).get(ref)
    if axis_def is None:
        return [], f"flow_expansion_axes.json has no axis named {ref!r}"
    return resolve_axis(ref, axis_def, context)


def axis_element_map(axes_config: dict | None = None) -> dict[str, dict]:
    """Stage H3 — maps an axis name to the single carrying element
    ({"element_no", "element_name"}) for axes whose resolved value can be
    injected directly into a flow scenario's `parameters` dict, the same
    element-name-keyed convention Composer already consumes via
    `_scenario_values_by_elem_no` (see build_verification_matrix_scenarios).

    Only test_axes-sourced axes (card_brand, entry_mode, cvm, avs_result,
    wallet, wallet_method) and the test_data_library-sourced
    terminal_identifier_variant have a single, unambiguous carrying element
    today. Axes without an entry here (field_profile — applied as
    inclusion_profile instead; authorization_code_variant — Stage H6, applied
    by overriding a step's own `fixed_elements` directly in
    generator.py::_flow_scenario_dict(), never a plain field value; plus
    amount_profile, retry_depth) are recorded for traceability/dedup only; a
    disclosed scope limit, not a bug.
    """
    axes_config = axes_config if axes_config is not None else load_axis_definitions()
    test_axes_data = _load_json(TEST_AXES_PATH)
    by_name = {a.get("name"): a for a in test_axes_data.get("axes", [])}
    mapping: dict[str, dict] = {}
    for axis_name, axis_def in (axes_config.get("axes") or {}).items():
        if axis_def.get("source") == "test_axes":
            test_axis = by_name.get(axis_def.get("ref"))
            if test_axis and test_axis.get("element_name"):
                mapping[axis_name] = {"element_no": test_axis.get("element_no"),
                                       "element_name": test_axis.get("element_name")}
        elif axis_def.get("source") == "test_data_library" and axis_def.get("element"):
            mapping[axis_name] = {"element_no": None, "element_name": axis_def["element"]}
    return mapping


def _declared_variant_refs(flows_config: dict) -> dict[str, int]:
    """axis name -> number of flow variants that reference it at all (regardless
    of whether any scenario the variant produced actually resolved/applied a
    value) — distinguishes UNREFERENCED (no variant even tries) from the other
    non-COMPLETE statuses below."""
    counts: dict[str, int] = {}
    for flow in flows_config.get("flows") or []:
        for variant in flow.get("variants") or []:
            for axis_ref in variant.get("axes") or []:
                ref = axis_ref.get("ref")
                if ref:
                    counts[ref] = counts.get(ref, 0) + 1
    return counts


def axis_usage(flows_config: dict, flow_scenarios: list[dict],
                axes_config: dict | None = None) -> dict:
    """Step 3 (multi-leg axis coverage fix) — per-run report of what every
    DECLARED flow-expansion axis (config/flow_expansion_axes.json) actually did
    this run: which of its spec-declared values were applied to a composed
    message, which were recorded but never applied (flow_axis_provenance says
    so — see shared/flow_axis_provenance.is_applied()), which were never even
    emitted by a variant, and whether any flow variant references the axis at
    all. Called from generator.run() and written into
    logs/run_current/multi_leg_gap_report.json (additive key, see Step 3 of the
    2026-10 multi-leg axis coverage plan) — the ONE place to look for "why
    isn't axis X covered" instead of re-deriving it by hand.

    Status meanings (worst match wins, checked in this order):
      NOT_RESOLVABLE       — the axis's own `source` can't resolve any value at
                             all this run (resolver warning, e.g. a response-side
                             test_axes axis, or an empty test-data-library entry)
      UNREFERENCED         — the axis resolves fine, but no flow variant's
                             axes[] names it at all
      RECORDED_NOT_APPLIED — at least one variant references it, and every
                             value it emitted was recorded but never reached a
                             composed message (is_applied() false for all)
      PARTIAL              — some declared values were applied, some weren't
      COMPLETE             — every declared value was applied
    """
    axes_config = axes_config if axes_config is not None else load_axis_definitions()
    referenced_by = _declared_variant_refs(flows_config)
    declared_by_axis = (axes_config.get("axes") or {})

    recorded: dict[str, set] = {}
    applied: dict[str, set] = {}
    for scenario in flow_scenarios:
        values = scenario.get("flow_axis_values") or {}
        provenance = scenario.get("flow_axis_provenance") or {}
        for axis_name, value in values.items():
            recorded.setdefault(axis_name, set()).add(value)
            if flow_axis_provenance.is_applied(provenance.get(axis_name)):
                applied.setdefault(axis_name, set()).add(value)

    by_axis: dict[str, dict] = {}
    for axis_name in declared_by_axis:
        declared_values, warning = resolve_ref(axis_name, axes_config=axes_config)
        rec = sorted(recorded.get(axis_name, set()))
        app = sorted(applied.get(axis_name, set()))
        never_emitted = [v for v in declared_values if v not in recorded.get(axis_name, set())]
        recorded_not_applied = [v for v in rec if v not in applied.get(axis_name, set())]
        variants_referencing = referenced_by.get(axis_name, 0)

        if warning and not declared_values:
            status, reason = "NOT_RESOLVABLE", warning
        elif variants_referencing == 0:
            status, reason = "UNREFERENCED", "no flow variant's axes[] references this axis"
        elif rec and not app:
            status = "RECORDED_NOT_APPLIED"
            reason = ("every value this axis produced was recorded but never applied to a "
                      "composed message (see flow_axis_provenance / table_placement_warning)")
        elif declared_values and len(app) < len(declared_values):
            status, reason = "PARTIAL", f"{len(app)}/{len(declared_values)} declared value(s) applied"
        elif declared_values:
            status, reason = "COMPLETE", f"all {len(declared_values)} declared value(s) applied"
        else:
            status, reason = "NOT_RESOLVABLE", warning or "axis resolves to no values"

        by_axis[axis_name] = {
            "source": declared_by_axis[axis_name].get("source"),
            "declared_values": declared_values,
            "resolver_warning": warning,
            "variants_referencing": variants_referencing,
            "applied_values": app,
            "recorded_not_applied_values": recorded_not_applied,
            "never_emitted_values": never_emitted,
            "status": status,
            "reason": reason,
        }

    return {
        "flow_scenarios_considered": len(flow_scenarios),
        "declared_axes": len(declared_by_axis),
        "complete_axes": sum(1 for a in by_axis.values() if a["status"] == "COMPLETE"),
        "by_axis": by_axis,
    }

