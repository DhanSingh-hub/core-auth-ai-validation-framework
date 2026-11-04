"""Reconcile named product-code legacy BRs against retained Petroleum companions."""

import argparse
import contextlib
import csv
import hashlib
import importlib.util
import io
import json
from pathlib import Path

SPEC = importlib.util.spec_from_file_location(
    "petroleum_legacy_parent", Path(__file__).with_name("reconcile-nonmaster-petroleum.py"))
parent = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(parent)
completion, base = parent.completion, parent.base
DECISIONS = completion.MODEL.with_name("nonmaster-petroleum-legacy-decisions.json")
OUTPUT = base.REVIEW / "nonmaster-petroleum-legacy-review-v1"
RELATIONS = {
    "RELATED_DISTINCT", "RELATED_SCOPE_NOT_EQUIVALENT", "SEPARATE_CATALOG_AUTHORITY",
    "NO_DIRECT_RETAINED_COMPANION_COUNTERPART",
}


def read_csv(path):
    with path.open(encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def reconcile(doc, legacy, companions, old_text, new_text):
    if doc.get("schemaVersion") != 1 or doc.get("status") != "DRAFT_REVIEW_REQUIRED":
        raise ValueError("Legacy counterpart decisions must remain draft schema 1")
    if len(doc.get("boundaries", [])) != 3:
        raise ValueError("All semantic non-promotion boundaries must be retained")
    historical = {row["legacyBrId"]: row for row in legacy}
    if len(historical) != len(legacy):
        raise ValueError("Duplicate legacy BR identity")
    old_pages = base.source_pages(old_text)
    new_pages = base.source_pages(new_text)
    companion_rules = {}
    for context, folder in completion.COMPANIONS.items():
        package = base.gates.load(completion.fleet.KB / folder / "chain-package.json")
        for rule in base.unique(package["businessRequirements"]).values():
            if rule["id"] in companion_rules:
                raise ValueError("Duplicate Petroleum companion BR identity")
            companion_rules[rule["id"]] = {**rule, "context": context}
    authored_companions = {row["id"] for row in base.gates.load(parent.DECISIONS)["rules"]}
    if set(companions) != authored_companions:
        raise ValueError("Retained companion decision population changed")

    seen = set()
    result = []
    for decision in doc["decisions"]:
        identity = decision["legacyBrId"]
        if identity in seen or identity not in historical:
            raise ValueError("Each named legacy decision must resolve uniquely")
        seen.add(identity)
        if decision["relation"] not in RELATIONS or not decision["assessment"].strip():
            raise ValueError("A supported relation and explicit assessment are required")
        anchors = json.loads(historical[identity]["sourceAnchors"])
        if not anchors:
            raise ValueError("Legacy source anchor is required")
        page_old, page_new = decision["oldPage"], decision["newPage"]
        quote = base.norm(decision["quote"])
        if (page_old not in old_pages or page_new not in new_pages or len(quote) < 20
                or quote not in base.norm(old_pages[page_old])
                or quote not in base.norm(new_pages[page_new])):
            raise ValueError(f"Exact source quotation not found in both pinned releases: {identity}")
        if not decision["targetBrIds"] and decision["relation"] != "NO_DIRECT_RETAINED_COMPANION_COUNTERPART":
            raise ValueError("An empty target set requires a bounded no-counterpart disposition")
        if decision["targetBrIds"] and decision["relation"] == "NO_DIRECT_RETAINED_COMPANION_COUNTERPART":
            raise ValueError("No-counterpart disposition cannot name targets")
        if len(set(decision["targetBrIds"])) != len(decision["targetBrIds"]):
            raise ValueError("Duplicate Petroleum counterpart")
        targets = []
        for target_id in decision["targetBrIds"]:
            if target_id not in companion_rules or target_id not in companions:
                raise ValueError(f"Unknown retained Petroleum counterpart: {target_id}")
            target = companion_rules[target_id]
            targets.append({
                "brId": target_id, "context": target["context"],
                "statement": target["statement"], "sourceAnchors": target["sourceAnchors"],
            })
        result.append({
            "legacyBrId": identity, "legacyStatement": historical[identity]["title"],
            "source": {
                "old": {"version": "2026-3", "page": page_old, "sha256": base.OLD_HASH},
                "new": {"version": "2026-4", "page": page_new,
                        "sha256": base.gates.SOURCE_SHA256},
                "quoteSha256": hashlib.sha256(quote.encode("utf-8")).hexdigest(),
            },
            "quote": quote, "relation": decision["relation"],
            "assessment": decision["assessment"], "petroleumCounterparts": targets,
            "automaticMerge": False, "denominatorDelta": None,
            "applicability": "SOURCE_AND_RETAINED_COMPANION_SCOPE_ONLY",
            "status": "DRAFT_SCOPED_COUNTERPART_REVIEW",
        })
    if len(seen) != 8:
        raise ValueError("Expected the complete authored eight-rule product-code batch")
    return result


def report(summary):
    return "\n".join([
        "# Petroleum-to-legacy product-code counterpart review", "",
        "**This is a bounded eight-rule review; exhaustive Petroleum-to-legacy review remains open.**", "",
        f"- {summary['counts']['namedLegacyRulesReviewed']} named legacy rules verified against both pinned ATL105 releases.",
        f"- {summary['counts']['namedPetroleumCounterparts']} retained Petroleum BR endpoints checked.",
        "- Source identity and related topic do not imply semantic equivalence, inheritance,",
        "  transaction acceptance, or an approved denominator change.",
        "- EV charging received a bounded no-direct-counterpart disposition in the retained fleet rules;",
        "  this is not a global absence claim.",
        "- Legacy source text and product restrictions remain version- and context-scoped.", "",
        "## Still open", "",
        f"- {summary['counts']['legacyRulesWithoutScopedDecisions']} legacy BRs still require broader scoped decisions.",
        f"- {summary['counts']['sourceUnitsStillUnclassified']} source units still require derivation.",
        "- Petroleum comparisons against the rest of the legacy BR library remain incomplete.",
        "- Whole-spec derivation and approved denominator remain incomplete.", "",
        "## Evidence", "",
        "- [Scoped counterpart decisions](legacy-counterpart-decisions.json)",
        "- [Input identities](input-manifest.json)", "",
    ])


def run(delivery, output=OUTPUT, check=False):
    delivery, output = delivery.resolve(), output.resolve()
    if output.is_relative_to(delivery) or delivery.is_relative_to(output):
        raise ValueError("Output must be disjoint from original delivery")
    if not check and output.exists():
        raise ValueError("New output directory required")
    frozen = completion.v1.freeze_delivery(delivery)
    with contextlib.redirect_stdout(io.StringIO()):
        parent.run(delivery, parent.OUTPUT, check=True)
    with contextlib.redirect_stdout(io.StringIO()):
        completion.run(delivery, completion.OUTPUT, check=True)
    petroleum_doc = base.gates.load(parent.DECISIONS)
    rules = {rule["id"] for rule in petroleum_doc["rules"]}
    legacy = read_csv(parent.parent.OUTPUT / "legacy-semantic-register.csv")
    doc = base.gates.load(DECISIONS)
    decisions = reconcile(doc, legacy, rules, base.OLD_SOURCE.read_text(encoding="utf-8-sig"),
                          base.gates.SOURCE.read_text(encoding="utf-8-sig"))
    prior = base.gates.load(parent.OUTPUT / "summary.json")
    product_output = base.REVIEW / "nonmaster-product-semantic-review-v1"
    product_rows = base.gates.load(product_output / "legacy-adjudications.json")
    previously_scoped = {
        row["legacyBrId"] for row in legacy if json.loads(row["semanticRelationIds"])
    } | {row["legacyBrId"] for row in product_rows} | {
        row["legacyBrId"] for row in decisions
    }
    summary = {
        "status": "BOUNDED_PRODUCT_LEGACY_REVIEW_COMPLETE_WHOLE_REQUEST_INCOMPLETE",
        "counts": {
            "namedLegacyRulesReviewed": len(decisions),
            "namedPetroleumCounterparts": sum(len(row["petroleumCounterparts"]) for row in decisions),
            "legacyRulesWithoutScopedDecisions": len(set(row["legacyBrId"] for row in legacy)
                                                       - previously_scoped),
            "sourceUnitsStillUnclassified": base.gates.load(
                product_output / "summary.json")["counts"]["remainingSourceUnits"],
        },
        "petroleumToAllLegacyReviewComplete": False,
        "wholeSpecSemanticsComplete": False,
        "approvedDenominator": None,
        "confirmedRequirementCoveragePercent": None,
        "baselineChanged": False,
    }
    inputs = [
        DECISIONS, Path(__file__), Path(parent.__file__), Path(parent.parent.__file__),
        Path(completion.__file__), Path(completion.v1.__file__), Path(completion.fleet.__file__),
        base.OLD_SOURCE, base.gates.SOURCE, parent.DECISIONS, completion.MODEL,
        parent.parent.OUTPUT / "legacy-semantic-register.csv",
        parent.parent.OUTPUT / "summary.json",
        completion.OUTPUT / "model-package.json",
        product_output / "legacy-adjudications.json",
        product_output / "summary.json",
    ]
    for folder in completion.COMPANIONS.values():
        inputs.append(completion.fleet.KB / folder / "chain-package.json")
    inputs.extend(path for folder in (parent.OUTPUT, completion.OUTPUT, completion.v1.OUTPUT)
                  for path in sorted(folder.iterdir()) if path.is_file())
    manifest = [{"path": str(path.resolve()), "sha256": base.digest(path)}
                for path in dict.fromkeys(inputs)]
    output_values = {
        "summary.json": summary, "legacy-counterpart-decisions.json": decisions,
        "input-manifest.json": manifest,
    }
    expected = {name: (json.dumps(value, ensure_ascii=True, indent=2) + "\n").encode("utf-8")
                for name, value in output_values.items()}
    expected["REPORT.md"] = report(summary).encode("utf-8")
    if (completion.v1.freeze_delivery(delivery) != frozen
            or any(base.digest(Path(row["path"])) != row["sha256"] for row in manifest)):
        raise ValueError("Frozen delivery or semantic inputs changed during review")
    if check:
        parent.parent.v1.verify_outputs(output, expected)
    else:
        output.mkdir(parents=True)
        for name, value in expected.items():
            (output / name).write_bytes(value)
    print(json.dumps(summary, indent=2))
    return summary


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--delivery", type=Path, required=True)
    parser.add_argument("--output", type=Path, default=OUTPUT)
    parser.add_argument("--check", action="store_true")
    args = parser.parse_args()
    run(args.delivery, args.output, args.check)


if __name__ == "__main__":
    main()
