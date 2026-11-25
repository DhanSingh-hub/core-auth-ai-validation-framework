"""Build a compact index of the test cases of the three packages (drops the bulky response templates)."""
import json
import os
import sys

ROOT = r"C:\Users\F5QGZOJ\Downloads\mastercard_credit_output_1611\mastercard_credit_new"
OUT = os.path.dirname(os.path.abspath(__file__))
PACKAGES = {
    "network": ROOT,
    "data_element": os.path.join(ROOT, "data_element_validation"),
    "operations": os.path.join(ROOT, "terminal_and_host_operations"),
}
KEEP = [
    "id", "display_id", "name", "scenario_id", "scenario_code", "scenario_display_id", "scenario_name",
    "scenario_status", "scenario_type", "acceptance_context", "context_label", "dimensions", "dimension_labels",
    "transaction_type", "test_type", "tc_type", "tc_type_name", "tc_layer", "polarity", "validation_focus",
    "test_objective", "status", "catalog_status", "run_priority", "priority", "verification", "test_data_file",
    "test_data_leg_files", "is_flow", "is_negative", "flow_id", "flow_variant_id", "flow_variant_name",
    "flow_axis_values", "business_rule_assertion", "awaiting_inputs", "pending_sme", "open_items",
    "violated_element_name", "negative_class", "wire_mutation", "rule_checks", "covered_by", "requires_sme",
    "requires_review", "merchant", "merchant_provenance", "conformance_notes", "source_page", "template_id",
    "completeness_status", "completeness_findings", "segment_directives", "forced_segments",
    "partial_approval_precondition", "fuel_at_pump", "message_chain", "requirement_id", "requirement_ids",
    "check_id", "check_level", "inclusion_profile", "segment_class", "foreign_segments", "segment_evidence",
    "device_checkpoints", "procedure", "expected_behaviour", "source_rule_id", "source_document_id",
    "scope_name", "scope_segments", "scope_mode", "leg_response_fields", "segment_applicability_version",
]


def compact_expected(er):
    if not isinstance(er, dict):
        return None
    out = {}
    for k in ("response_code", "decline_code"):
        v = er.get(k)
        if isinstance(v, dict):
            out[k] = {kk: v.get(kk) for kk in ("tier", "value", "values", "meaning", "note", "source_page", "flags", "grounding") if kk in v}
    return out


def compact_spec_basis(sb):
    out = []
    for s in sb or []:
        if isinstance(s, dict):
            out.append({k: s.get(k) for k in ("page", "rule_id", "source_id", "pair", "quote") if k in s})
    return out


def main():
    for pkg, base in PACKAGES.items():
        src = os.path.join(base, "test_cases", "test_cases.jsonl")
        rows = []
        bad = 0
        with open(src, encoding="utf-8") as f:
            for line in f:
                try:
                    d = json.loads(line)
                except Exception:
                    bad += 1
                    continue
                r = {k: d.get(k) for k in KEEP if k in d}
                r["flags"] = d.get("flags") or []
                r["spec_basis"] = compact_spec_basis(d.get("spec_basis"))
                r["expected"] = compact_expected(d.get("expected_response"))
                legs = []
                for leg in d.get("tx_legs") or []:
                    legs.append({k: leg.get(k) for k in ("step", "category", "role", "message", "code", "entry_code",
                                                          "of", "links", "follow_on", "independent", "required_segments",
                                                          "link_defs", "expect", "harness", "harness_directive",
                                                          "generated_tables", "response_tables", "amount", "settings")})
                r["tx_legs"] = legs
                steps = []
                for s in d.get("steps") or []:
                    steps.append({
                        "step": s.get("step"), "role": s.get("role"), "transaction_type": s.get("transaction_type"),
                        "runtime_substitutions": s.get("runtime_substitutions"),
                        "expected": compact_expected(s.get("expected_response")),
                        "expected_outcome_note": s.get("expected_outcome_note"),
                        "physical_transaction_type": s.get("physical_transaction_type"),
                    })
                r["steps"] = steps
                r["has_request"] = bool(d.get("request")) or any(bool(s.get("request")) for s in d.get("steps") or [])
                rows.append(r)
        with open(os.path.join(OUT, f"tc_index_{pkg}.json"), "w", encoding="utf-8") as f:
            json.dump(rows, f, ensure_ascii=False)
        print(pkg, "tcs", len(rows), "bad", bad)


if __name__ == "__main__":
    sys.exit(main())
