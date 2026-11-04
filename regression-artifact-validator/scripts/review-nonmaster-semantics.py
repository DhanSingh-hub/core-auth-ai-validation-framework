"""Reproduce explicit draft semantic classifications without promoting an incomplete denominator."""

import argparse
import contextlib
import csv
import importlib.util
import io
import json
from collections import Counter
from pathlib import Path

SPEC = importlib.util.spec_from_file_location(
    "nonmaster_semantic_completion", Path(__file__).with_name("complete-nonmaster-model.py"))
completion = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(completion)
base, v1 = completion.base, completion.v1
DECISIONS = completion.MODEL.with_name("nonmaster-semantic-decisions.json")
OUTPUT = base.REVIEW / "nonmaster-semantic-review-v1"
RECOVERY_CATALOGS = [
    v1.MODEL.parent.parent / f"segment-DL{number}" / "coverage" / f"segment-DL{number}-rule-catalog.json"
    for number in range(3, 9)
]
KINDS = {
    "FIELD_REPRESENTATION", "LIFECYCLE_CORRELATION", "EXPIRATION_VALIDATION",
    "EXPIRATION_CAPTURE", "CONDITIONAL_DEFAULT", "TRACK_SELECTION", "ENTRY_POLICY",
    "CRYPTO_POLICY", "RETRY_AND_RECOVERY", "PARTIAL_APPROVAL", "PROGRAM_ELIGIBILITY",
    "AMOUNT_INVARIANT", "LIFECYCLE_ORDER", "TIME_WINDOW", "COMPANION_DATA",
    "RECEIPT_POLICY", "TERMINAL_CONFIGURATION", "PROCESSING_MODE",
}
RELATIONS = {
    "EQUIVALENT_CORE", "COMPONENT_OF", "SPECIALIZES", "RELATED_DISTINCT",
    "REFINES_TRIGGER", "DIFFERENT_SCOPE", "EXCLUSIVE_PROFILES",
}


def read_csv(path):
    with path.open(encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def recover_legacy_statements(legacy, original, catalogs):
    original_rows = base.unique(original["businessRequirements"])
    catalog_rules = {}
    for path, catalog in catalogs:
        if catalog["specification"] != "ATL105" or catalog["specificationVersion"] != "2026-3":
            raise ValueError("Statement recovery must retain legacy source version")
        for rule in catalog["rules"]:
            identity = rule["ruleId"]
            if identity in catalog_rules:
                raise ValueError("Ambiguous recovery catalog rule ID")
            catalog_rules[identity] = (path, rule, catalog)
    identities = {row["legacyBrId"] for row in legacy}
    recovered = []
    for row in legacy:
        item = {**row, "statementRecovery": None}
        if not row["title"].strip():
            record = original_rows[row["legacyBrId"]]
            identity = record.get("ruleId")
            if identity not in catalog_rules:
                raise ValueError(f"No exact statement recovery catalog for {row['legacyBrId']}")
            path, rule, catalog = catalog_rules[identity]
            original_anchor, catalog_anchor = record["sourceAnchor"], rule["sourceAnchor"]
            if (not rule["title"].strip() or any(
                    catalog_anchor.get(key) != value for key, value in original_anchor.items())):
                raise ValueError("Recovered statement source anchor differs from original legacy record")
            alias = "BR-RULE-" + identity
            if alias not in identities:
                raise ValueError("Recovered statement lacks its original rule-catalog BR alias")
            alias_row = next(candidate for candidate in legacy if candidate["legacyBrId"] == alias)
            if (alias_row["title"] != rule["title"]
                    or catalog_anchor not in json.loads(alias_row["sourceAnchors"])):
                raise ValueError("Recovered statement and catalog BR alias disagree")
            item["title"] = rule["title"]
            item["statementRecovery"] = {
                "ruleId": identity, "catalogPath": str(path.resolve()),
                "catalogSha256": base.digest(path), "catalogClass": rule["class"],
                "aliasBrId": alias, "identityConclusion": "SAME_CATALOG_RULE_NOT_SEMANTIC_APPROVAL",
                "sourceAnchor": catalog_anchor, "note": rule.get("note", ""),
                "originalProvisionalItem": record.get("provisionalItem"),
                "additionalProvisionalItems": record.get("additionalProvisionalItems", []),
                "catalogProvisionalItems": catalog.get("provisionalItems", []),
            }
        recovered.append(item)
    return recovered


def review(doc, package, legacy, units, old_pages):
    if doc["schemaVersion"] != 1 or doc["status"] != "DRAFT_REVIEW_REQUIRED":
        raise ValueError("Semantic decisions must remain draft schema 1")
    rules = base.unique(package["businessRequirements"])
    historical = {row["legacyBrId"]: row for row in legacy}
    if len(historical) != len(legacy) or set(rules) & historical.keys():
        raise ValueError("Unique, disjoint current and legacy rule IDs required")
    raw = {"BR-" + row["id"]: row for row in v1.document()["rules"]}
    raw.update({"BR-" + row["id"]: row for row in completion.program_document()["rules"]})
    if set(raw) != set(rules):
        raise ValueError("Source-authored rule population differs from reviewed model")
    classifications = {}
    for group in doc["classificationGroups"]:
        if group["kind"] not in KINDS or not group["rules"]:
            raise ValueError("Unknown or empty semantic classification group")
        for short_id in group["rules"]:
            identity = "BR-" + short_id
            if identity not in rules or identity in classifications:
                raise ValueError("Every current rule requires exactly one explicit classification")
            rule = rules[identity]
            if not rule["condition"].strip() or not rule["contexts"]:
                raise ValueError("Semantic classification requires explicit conditions and contexts")
            classifications[identity] = {
                "brId": identity, "kind": group["kind"], "statement": rule["statement"],
                "condition": rule["condition"], "contexts": rule["contexts"],
                "predicate": raw[identity]["predicate"], "sourceEvidence": rule["sourceEvidence"],
                "sourceAnchors": rule.get("sourceAnchors", []),
                "assertionBoundary": "PREDICATE_ONLY_WITH_SUPPLIED_PRECONDITIONS_NOT_PROFILE_ELIGIBILITY",
                "status": "DRAFT_CLASSIFIED_NOT_APPROVED",
                "denominatorEligibility": "BLOCKED_PENDING_WHOLE_SPEC_DERIVATION_AND_APPROVAL",
            }
    if set(classifications) != set(rules):
        raise ValueError("Every current rule requires exactly one explicit classification")
    adjudications, seen_pairs, affected = [], set(), {}
    identities = set(rules) | set(historical)
    for relation in doc["relations"]:
        left, right, kind = (relation[key] for key in ("left", "right", "relation"))
        pair = frozenset((left, right))
        if (left not in identities or right not in identities or left == right
                or not {left, right} & rules.keys() or pair in seen_pairs):
            raise ValueError("Unique relation endpoints must include a current rule")
        seen_pairs.add(pair)
        if kind not in RELATIONS or not relation["reason"].strip():
            raise ValueError("Explicit semantic relation and rationale required")
        # Equivalence is deliberately scoped; a legacy title or shared page is insufficient.
        if kind == "EQUIVALENT_CORE":
            if left not in rules or right not in historical:
                raise ValueError("Equivalent core requires current-to-legacy source evidence")
            locator, quote = relation["legacyLocator"], relation["legacyQuote"]
            anchors = json.loads(historical[right]["sourceAnchors"])
            keys = {key for anchor in anchors for key in v1.reconciliation.anchor_keys(anchor)}
            page = old_pages.get(relation["legacyPage"], "")
            heading = base.norm(relation["legacyHeading"])
            current_quote = base.norm(rules[left]["sourceEvidence"]["quote"])
            if (locator not in keys or not heading or page.count(heading) != 1
                    or len(base.norm(quote)) < 20 or base.norm(quote) != current_quote
                    or base.norm(quote) not in page.partition(heading)[2]):
                raise ValueError("Equivalent core requires the same clause in both pinned releases")
        current_ids = sorted({left, right} & rules.keys())
        row = {
            **relation, "currentRuleIds": current_ids,
            "currentEvidence": [{"brId": identity, **rules[identity]["sourceEvidence"],
                                 "sourceAnchors": rules[identity].get("sourceAnchors", []),
                                 "condition": rules[identity]["condition"],
                                 "contexts": rules[identity]["contexts"]} for identity in current_ids],
            "status": "DRAFT_SOURCE_REVIEWED_RELATION",
            "automaticMerge": False, "denominatorDelta": None,
            "scopeConclusion": "RETAIN_ALL_CONDITIONS_AND_SOURCE_VERSIONS_NO_LEGACY_PROFILE_PROMOTION",
        }
        if kind == "EQUIVALENT_CORE":
            row["legacySourceEvidence"] = {
                "version": "2026-3", "sha256": base.OLD_HASH, "page": relation["legacyPage"],
                "heading": relation["legacyHeading"], "quote": relation["legacyQuote"],
            }
        adjudications.append(row)
        for identity in {left, right} & historical.keys():
            affected.setdefault(identity, []).append(relation["id"])
    if len({row["id"] for row in adjudications}) != len(adjudications):
        raise ValueError("Duplicate semantic relation ID")
    ledger = []
    for row in legacy:
        relations = affected.get(row["legacyBrId"], [])
        ledger.append({
            "legacyBrId": row["legacyBrId"], "title": row["title"],
            "sourceAnchors": json.loads(row["sourceAnchors"]), "originalStatus": row["originalStatus"],
            "releaseEquivalence": row["releaseEquivalence"], "semanticRelationIds": relations,
            "statementRecovery": row.get("statementRecovery"),
            "statementDisposition": "RECOVERED_EXACT_CATALOG_RULE" if row.get("statementRecovery")
            else "STATEMENT_PRESENT" if row["title"].strip()
            else "REPOSITORY_STATEMENT_RECOVERY_REQUIRED",
            "overlapDisposition": "EXPLICIT_SCOPED_RELATIONS_RECORDED_NOT_EXHAUSTIVE" if relations
            else "NOT_ADJUDICATED_NO_ABSENCE_INFERENCE",
            "applicability": "NOT_APPROVED_FOR_2026_4_NONMASTER_CONTEXTS",
            "denominatorEligibility": "BLOCKED_PENDING_SEMANTIC_DERIVATION_AND_APPROVAL",
        })
    source_ledger = []
    for unit in units:
        quote = base.norm(unit["quote"])
        bound = []
        for identity, rule in rules.items():
            evidence = rule["sourceEvidence"]
            source = evidence.get("source", "ATL105")
            pages = evidence.get("pages", [anchor["page"] for anchor in rule.get("sourceAnchors", [])])
            if (source == "ATL105" and int(unit["page"]) in pages
                    and base.norm(evidence["quote"]) in quote):
                bound.append(identity)
        source_ledger.append({
            "sourceUnitId": unit["sourceUnitId"], "page": int(unit["page"]),
            "startLine": int(unit["startLine"]), "endLine": int(unit["endLine"]),
            "sha256": unit["sha256"], "explicitQuotedRuleIds": bound,
            "semanticDisposition": "REPOSITORY_WHOLE_UNIT_DERIVATION_REQUIRED",
            "meaning": "A quoted proposition is not a classification of the remaining paragraph or table",
        })
    return list(classifications.values()), adjudications, ledger, source_ledger


def render(summary):
    counts = summary["counts"]
    return "\n".join([
        "# Non-Mastercard explicit semantic review", "",
        "**Overall semantic task: INCOMPLETE - repository derivation remains.**",
        "This is not a completed whole-spec denominator and not an execution certificate.", "",
        f"- {counts['classifiedCurrentRules']} existing draft BRs explicitly classified.",
        f"- {counts['semanticRelations']} authored semantic relationships; "
        f"{counts['legacyRulesWithRelations']} legacy BRs have bounded relationship decisions.",
        f"- {counts['legacyRulesWithoutRelations']} legacy BRs still have no explicit overlap adjudication.",
        f"- {counts['legacyStatementsRecovered']} missing legacy statements recovered through exact "
        "catalog IDs and verified source anchors; their catalog-identity aliases and provisional notes are retained.",
        f"- {counts['legacyStatementsMissing']} legacy BRs need statement recovery before semantic review.",
        f"- All {counts['sourceUnits']} source units retained; a quoted clause never closes the whole unit.",
        "- Approved denominator and confirmed AI semantic coverage remain null.",
        "- Existing v1/v2 and all delivery files are reverified read-only; none is rewritten.", "",
        "## Adjudication conclusions", "",
        "- Six-digit sequence representation and eWIC no-Return have equivalent legacy cores.",
        "- Compound width/persistence, approval-context and eWIC prompt/lifecycle rules cannot be "
        "replaced by one narrower assertion.",
        "- Visa incremental sequence continuity specializes a general authorization-follow-up rule; "
        "it is not automatically a new denominator obligation.",
        "- Return, sale-void and timeout lifecycles do not inherit authorization-only conditions without evidence.",
        "- Certegy manual DOB supplies a specific trigger; MICR capture is not its wire encoding.",
        "- Credit/EBT expiration, ordinary/Blackhawk barcode entry, WEX/COMDATA recovery and WEX "
        "OTR/Money Code entry remain distinct policies.", "",
        "## Evidence", "",
        "- [Current rule classifications](rule-classification.csv)",
        "- [Explicit relationships and source evidence](semantic-adjudications.json)",
        "- [Complete legacy disposition ledger](legacy-semantic-register.csv)",
        "- [Source-unit completeness ledger](source-semantic-register.csv)",
        "- [Open repository work](remaining-work.csv)",
        "- [Summary and input identities](summary.json)",
        "- [Frozen input manifest](input-manifest.json)", "",
        "No lexical score, missing candidate, identical source block, logical PASS or legacy COVERED "
        "label establishes a semantic merge, network permission or AI coverage.", "",
    ])


def run(delivery, output=OUTPUT, check=False):
    delivery, output = delivery.resolve(), output.resolve()
    if output.is_relative_to(delivery) or delivery.is_relative_to(output):
        raise ValueError("Output must be disjoint from original delivery")
    if not check and output.exists():
        raise ValueError("New output directory required")
    with contextlib.redirect_stdout(io.StringIO()):
        completion.run(delivery, completion.OUTPUT, check=True)
    paths = [DECISIONS, Path(__file__), Path(completion.__file__), v1.MODEL, completion.MODEL,
             base.OLD_SOURCE, base.gates.SOURCE, base.LEGACY, *RECOVERY_CATALOGS]
    paths.extend(path for folder in (v1.OUTPUT, completion.OUTPUT)
                 for path in sorted(folder.iterdir()) if path.is_file())
    manifest = [{"path": str(path.resolve()), "sha256": base.digest(path)}
                for path in dict.fromkeys(paths)]
    doc = base.gates.load(DECISIONS)
    if (base.digest(completion.OUTPUT / "model-package.json") != doc["reviewedModelSha256"]
            or base.digest(v1.OUTPUT / "legacy-release-register.csv") != doc["reviewedLegacySha256"]):
        raise ValueError("Reviewed rule populations changed; re-adjudicate decisions")
    old_pages = base.source_pages(base.OLD_SOURCE.read_text(encoding="utf-8-sig"))
    recovered = recover_legacy_statements(
        read_csv(v1.OUTPUT / "legacy-release-register.csv"), base.gates.load(base.LEGACY),
        [(path, base.gates.load(path)) for path in RECOVERY_CATALOGS])
    classified, relations, legacy, units = review(
        doc, base.gates.load(completion.OUTPUT / "model-package.json"),
        recovered,
        read_csv(v1.OUTPUT / "source-unit-register.csv"), old_pages)
    counts = {
        "classifiedCurrentRules": len(classified), "semanticRelations": len(relations),
        "legacyRules": len(legacy),
        "legacyRulesWithRelations": sum(bool(row["semanticRelationIds"]) for row in legacy),
        "legacyRulesWithoutRelations": sum(not row["semanticRelationIds"] for row in legacy),
        "legacyStatementsRecovered": sum(bool(row["statementRecovery"]) for row in legacy),
        "legacyStatementsMissing": sum(
            row["statementDisposition"] == "REPOSITORY_STATEMENT_RECOVERY_REQUIRED" for row in legacy),
        "sourceUnits": len(units), "fullyAdjudicatedSourceUnits": 0,
    }
    summary = {
        "status": "INCOMPLETE_REPOSITORY_SEMANTIC_WORK_REMAINS", "counts": counts,
        "classificationCounts": dict(sorted(Counter(row["kind"] for row in classified).items())),
        "relationCounts": dict(sorted(Counter(row["relation"] for row in relations).items())),
        "approvedDenominator": None, "confirmedRequirementCoveragePercent": None,
        "wholeSpecSemanticsComplete": False, "executionReady": False, "baselineChanged": False,
    }
    remaining = [
        {"id": "NMS-W01", "work": "Classify the existing 71 source-authored draft BRs",
         "status": "DRAFT_CLASSIFICATION_RECORDED", "repositoryWorkRemaining": False},
        {"id": "NMS-W02", "work": "Recover missing legacy statements from canonical rule catalogs",
         "status": "EXACT_CATALOG_IDENTITY_RECOVERED", "repositoryWorkRemaining": False},
        {"id": "NMS-W03", "work": "Complete legacy semantic overlap and context applicability decisions",
         "status": "REPOSITORY_WORK_REMAINS", "repositoryWorkRemaining": True},
        {"id": "NMS-W04", "work": "Derive all source obligations, exceptions and table conditions",
         "status": "REPOSITORY_WORK_REMAINS", "repositoryWorkRemaining": True},
        {"id": "NMS-W05", "work": "Reconcile Petroleum companion rules without merging source versions",
         "status": "REPOSITORY_WORK_REMAINS", "repositoryWorkRemaining": True},
        {"id": "NMS-W06", "work": "Approve reviewed scope, atomicity and source precedence",
         "status": "EXTERNAL_APPROVAL_REQUIRED", "repositoryWorkRemaining": False},
    ]
    outputs = {"summary.json": summary, "semantic-adjudications.json": relations,
               "input-manifest.json": manifest}
    tables = {"rule-classification.csv": classified, "legacy-semantic-register.csv": legacy,
              "source-semantic-register.csv": units, "remaining-work.csv": remaining}
    expected = {name: (json.dumps(value, ensure_ascii=True, indent=2) + "\n").encode("utf-8")
                for name, value in outputs.items()}
    expected.update({name: v1.csv_text(rows).encode("utf-8-sig") for name, rows in tables.items()})
    expected["REPORT.md"] = render(summary).encode("utf-8")
    if any(base.digest(Path(row["path"])) != row["sha256"] for row in manifest):
        raise ValueError("Semantic-review inputs changed during processing")
    if check:
        v1.verify_outputs(output, expected)
    else:
        output.mkdir(parents=True)
        for name, content in expected.items():
            (output / name).write_bytes(content)
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
