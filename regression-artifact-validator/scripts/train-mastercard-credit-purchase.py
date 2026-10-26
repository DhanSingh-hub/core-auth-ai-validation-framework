"""Trains the Mastercard Credit Purchase transaction context into the Test Solution.

Input (Test Solution only, AI artifacts excluded):
  * the Mastercard Credit Purchase FR-matrix model from build-mastercard-fr-matrix.py
    (BR-01 -> FR-001..FR-008 -> template and Test Solution scenarios), and
  * the canonical records in complete-all-test-solution-br-ts-tc-td-package.json.

Training selects every Mastercard-applicable Test Solution BR -> TS -> TC -> TD chain that satisfies the
framework's CanonicalTraceabilityValidator contract (complete ATL105 2026-3 anchors, one common anchor through
every reachable BR -> TS -> TC -> TD combination, TD payload and expected validation, no orphans). Records are
copied verbatim; the only normalizations are link-array trimming to in-package IDs, singular-to-array link
fields and non-canonical TS status values, each logged in provenance.normalizations. BRs without a valid
chain go to reviewQueue with the reason and required evidence; nothing is fabricated or approved.

Outputs:
  test-output/test-json/mc-credit-purchase-training-package.json          (canonical, validated by TestSolutionJsonFormatTest)
  test-output/test-solution-independent-review/mc-credit-purchase-br-coverage.json / .md
"""
import hashlib
import importlib.util
import json
from collections import Counter, OrderedDict
from datetime import date
from pathlib import Path

HERE = Path(__file__).resolve().parent
_spec = importlib.util.spec_from_file_location("mc_fr", HERE / "build-mastercard-fr-matrix.py")
mc = importlib.util.module_from_spec(_spec)
_spec.loader.exec_module(mc)
ts_fr = mc.ts_fr

CONTEXT_ID = "MASTERCARD_CREDIT_PURCHASE"
SPEC_VERSION = "2026-3"
OUT_PACKAGE = ts_fr.ATL / "test-output" / "test-json" / "mc-credit-purchase-training-package.json"
REVIEW_DIR = ts_fr.ATL / "test-output" / "test-solution-independent-review"
OUT_COVERAGE_JSON = REVIEW_DIR / "mc-credit-purchase-br-coverage.json"
OUT_COVERAGE_MD = REVIEW_DIR / "mc-credit-purchase-br-coverage.md"
ARTIFACT_STATUS = {"DRAFT", "REVIEW_REQUIRED", "APPROVED_FOR_REVIEW", "EXECUTION_READY", "BLOCKED", "SCHEDULED"}
READINESS = {"EXECUTABLE", "EXTERNAL_FIXTURE_REQUIRED", "REVIEW_REQUIRED"}
COVERAGE_STATUS = {"MAPPED": "MAPPED", "PARTIAL": "PARTIAL", "NOT IN TEST SOLUTION": "NOT_IN_TEST_SOLUTION",
                   "TEST SOLUTION ONLY": "TEST_SOLUTION_ONLY"}
REQUIRED_GATES = ["SOURCE_INVENTORY", "SEGMENT_KNOWLEDGE_MODEL", "CONTEXT_MATRIX", "INDEPENDENT_BR_DERIVATION",
                  "TS_TC_TEST_DATA_CHAIN", "LIFECYCLE_AND_SPECIALIZED_FLOWS", "SERIALIZATION_AND_MUTATION",
                  "INDEPENDENT_AI_ARTIFACT_INTAKE", "CONVERTER_AND_EXECUTION_READINESS"]


def rel(path):
    """Module-relative path; files outside the module (the FR-matrix template) are prefixed with '../'."""
    p = Path(path).resolve()
    try:
        return p.relative_to(ts_fr.ROOT).as_posix()
    except ValueError:
        return "../" + p.relative_to(ts_fr.ROOT.parent).as_posix()


def sha256(path):
    return hashlib.sha256(Path(path).read_bytes()).hexdigest()


def key(a):
    return "|".join(str(a.get(k) or "").strip().lower()
                    for k in ("specification", "version", "section", "segment", "element", "rule"))


def keys(record):
    return {key(a) for a in ts_fr.anchors(record)}


def anchor_problem(record):
    anchors = ts_fr.anchors(record)
    if not anchors:
        return "no sourceAnchor"
    for a in anchors:
        if not a.get("specification") or not a.get("version") or not any(a.get(k) for k in ("section", "segment", "element", "rule")):
            return "incomplete sourceAnchor"
        if str(a.get("version")).strip() != SPEC_VERSION:
            return f"anchor version {a.get('version')} is not {SPEC_VERSION}"
    return None


class Selector:
    """Builds the largest BR -> TS -> TC -> TD edge set the canonical validator accepts."""

    def __init__(self, model):
        self.m = model
        self.br = model["br_by_id"]
        self.reasons = OrderedDict()
        self.pruned = []
        self.ts, self.tc, self.td = {}, {}, {}
        self.tb, self.ct, self.dc = {}, {}, {}

    def reason(self, br_id, text):
        self.reasons.setdefault(br_id, [])
        if text not in self.reasons[br_id]:
            self.reasons[br_id].append(text)

    def candidate_edges(self):
        for b in self.m["detail_ids"]:
            B = self.br[b]
            problem = anchor_problem(B)
            if problem:
                self.reason(b, f"BR {problem}")
                continue
            if not self.m["ts_by_br"].get(b):
                self.reason(b, "no Test Solution scenario links this BR")
            for t in self.m["ts_by_br"].get(b, []):
                problem = anchor_problem(t)
                if problem:
                    self.reason(b, f"{t['id']}: TS {problem}")
                    continue
                if not keys(t) & keys(B):
                    self.reason(b, f"{t['id']}: TS shares no ATL105 anchor with the BR")
                    continue
                if not self.m["tc_by_ts"].get(t["id"]):
                    self.reason(b, f"{t['id']}: no test case links this scenario")
                for c in self.m["tc_by_ts"].get(t["id"], []):
                    problem = anchor_problem(c)
                    if problem:
                        self.reason(b, f"{c['id']}: TC {problem}")
                        continue
                    if not c.get("expectedOutcome"):
                        self.reason(b, f"{c['id']}: TC has no expectedOutcome")
                        continue
                    if not self.m["td_by_tc"].get(c["id"]):
                        self.reason(b, f"{c['id']}: no test data links this test case")
                    for d in self.m["td_by_tc"].get(c["id"], []):
                        problem = anchor_problem(d)
                        if problem:
                            self.reason(b, f"{d['id']}: TD {problem}")
                            continue
                        if d.get("payload") is None or not d.get("expectedValidation"):
                            self.reason(b, f"{d['id']}: TD has no payload or expectedValidation")
                            continue
                        if d.get("readiness") not in READINESS:
                            self.reason(b, f"{d['id']}: TD readiness {d.get('readiness')!r} is not canonical")
                            continue
                        if not keys(d) & keys(c) & keys(t) & keys(B):
                            self.reason(b, f"{d['id']} -> {c['id']} -> {t['id']}: no common ATL105 anchor through the chain")
                            continue
                        self.ts[t["id"]], self.tc[c["id"]], self.td[d["id"]] = t, c, d
                        self.tb.setdefault(t["id"], OrderedDict())[b] = None
                        self.ct.setdefault(c["id"], OrderedDict())[t["id"]] = None
                        self.dc.setdefault(d["id"], OrderedDict())[c["id"]] = None

    def failures(self):
        out = []
        for d, cs in self.dc.items():
            for c in cs:
                for t in self.ct.get(c, {}):
                    for b in self.tb.get(t, {}):
                        if not keys(self.td[d]) & keys(self.tc[c]) & keys(self.ts[t]) & keys(self.br[b]):
                            out.append((b, t, c, d))
        return out

    def support(self, t, b):
        return sum(1 for c in self.tc if t in self.ct.get(c, {}) for d, cs in self.dc.items()
                   if c in cs and keys(self.td[d]) & keys(self.tc[c]) & keys(self.ts[t]) & keys(self.br[b]))

    def resolve_conflicts(self):
        while True:
            fails = self.failures()
            if not fails:
                return
            b, t, c, d = fails[0]
            brs = list(self.tb[t])
            if len(brs) > 1:
                # A shared scenario must keep one BR whose anchor every reachable TD can carry.
                keep = max(brs, key=lambda x: (self.support(t, x), -brs.index(x)))
                for other in brs:
                    if other != keep:
                        del self.tb[t][other]
                        self.pruned.append({"from": t, "field": "requirementIds", "removed": other,
                                            "reason": f"{t} is shared with {keep}; their test data carry different ATL105 anchors"})
                        self.reason(other, f"{t}: shared with {keep}; the canonical chain needs one common anchor - split the scenario per BR")
            else:
                del self.dc[d][c]
                self.pruned.append({"from": d, "field": "testCaseIds", "removed": c,
                                    "reason": f"no common ATL105 anchor through {b} -> {t} -> {c}"})
                self.reason(b, f"{d} -> {c} -> {t}: no common ATL105 anchor through the chain")

    def remove_orphans(self):
        changed = True
        while changed:
            changed = False
            for d in [d for d, cs in self.dc.items() if not cs]:
                del self.dc[d]
                changed = True
            linked_tc = {c for cs in self.dc.values() for c in cs}
            for c in [c for c in self.ct if c not in linked_tc]:
                del self.ct[c]
                changed = True
            for c in list(self.ct):
                self.ct[c] = OrderedDict((t, None) for t in self.ct[c] if self.tb.get(t))
                if not self.ct[c]:
                    del self.ct[c]
                    changed = True
            linked_ts = {t for ts in self.ct.values() for t in ts}
            for t in [t for t in self.tb if t not in linked_ts or not self.tb[t]]:
                del self.tb[t]
                changed = True
            for d in list(self.dc):
                self.dc[d] = OrderedDict((c, None) for c in self.dc[d] if c in self.ct)

    def run(self):
        self.candidate_edges()
        self.resolve_conflicts()
        self.remove_orphans()
        assert not self.failures(), "conflict resolution left an invalid chain"
        trained = OrderedDict((b, None) for b in self.m["detail_ids"] for t in self.tb if b in self.tb[t])
        for b in trained:
            self.reasons.pop(b, None)
        return list(trained)


def linked(values, allowed):
    return [v for v in OrderedDict.fromkeys(values) if v in allowed]


def package_records(sel, trained):
    normalizations = []

    def note(record_id, field, before, after, reason):
        if before != after:
            normalizations.append({"id": record_id, "field": field, "from": before, "to": after, "reason": reason})

    brs = [sel.br[b] for b in trained]
    ts_order = list(OrderedDict.fromkeys(t for b in trained for t in sel.tb if b in sel.tb[t]))
    tss = []
    for t in ts_order:
        rec = dict(sel.ts[t])
        links = linked(rec.get("requirementIds") or [], sel.tb[t])
        note(t, "requirementIds", rec.get("requirementIds"), links, "trimmed to BRs trained in this context")
        rec["requirementIds"] = links
        status = rec.get("status")
        if status is not None and status not in ARTIFACT_STATUS:
            rec.setdefault("sourceStatus", status)
            rec["status"] = "REVIEW_REQUIRED"
            note(t, "status", status, "REVIEW_REQUIRED",
                 "non-canonical scenario status; original kept in the normalization log (and sourceStatus when absent)")
        tss.append(rec)
    tc_order = list(OrderedDict.fromkeys(c for t in ts_order for c in sel.ct if t in sel.ct[c]))
    tcs = []
    for c in tc_order:
        rec = dict(sel.tc[c])
        source = list(rec.get("scenarioIds") or []) + ([rec["scenarioId"]] if rec.get("scenarioId") else [])
        links = linked(source, sel.ct[c])
        note(c, "scenarioIds", rec.get("scenarioIds"), links, "trimmed to scenarios trained in this context")
        rec["scenarioIds"] = links
        tcs.append(rec)
    td_order = list(OrderedDict.fromkeys(d for c in tc_order for d in sel.dc if c in sel.dc[d]))
    tds = []
    for d in td_order:
        rec = dict(sel.td[d])
        links = list(sel.dc[d])
        note(d, "testCaseIds", rec.get("testCaseIds"), links,
             "trimmed to trained test cases; links declared only by TC.testDataIds are made explicit")
        rec["testCaseIds"] = links
        tds.append(rec)
    return brs, tss, tcs, tds, normalizations


def business_scenarios(m, trained, brs, tss, tcs, tds):
    trained_set, ts_ids = set(trained), {t["id"] for t in tss}
    tc_ids, td_ids = {c["id"] for c in tcs}, {d["id"] for d in tds}
    scenarios, current, fr = [], None, None
    for row in m["rows"]:
        if row[2]:
            fr = row[2].strip()
        if row[4]:
            ids = [i for i in (row[10] or "").split("\n") if i]
            current = OrderedDict([
                ("id", row[4].strip()), ("title", (row[5] or "").strip()), ("functionalRequirementId", fr),
                ("origin", "TEMPLATE" if int(row[4].strip()[3:]) <= 37 else "TEST_SOLUTION"),
                ("acceptanceCriteria", (row[6] or "").strip()), ("validations", []), ("expectedResults", []),
                ("coverageStatus", COVERAGE_STATUS[(row[9] or "").strip()]),
                ("testSolutionRequirementIds", ids),
                ("trainedRequirementIds", [i for i in ids if i in trained_set]),
                ("reviewRequirementIds", [i for i in ids if i not in trained_set]),
                ("trainedScenarioIds", []), ("trainedTestCaseIds", []), ("trainedTestDataIds", []),
                ("note", (row[15] or "").strip())])
            scenarios.append(current)
        if current is None:
            continue
        if row[7] and str(row[7]).strip() not in current["validations"]:
            current["validations"].append(str(row[7]).strip())
        if row[8] and str(row[8]).strip() not in current["expectedResults"]:
            current["expectedResults"].append(str(row[8]).strip())
        for col, bucket, allowed in ((11, "trainedScenarioIds", ts_ids), (12, "trainedTestCaseIds", tc_ids),
                                     (13, "trainedTestDataIds", td_ids)):
            for i in (row[col] or "").split("\n"):
                if i in allowed and i not in current[bucket]:
                    current[bucket].append(i)
    for s in scenarios:
        if not s["testSolutionRequirementIds"]:
            s["trainingState"] = "NO_TEST_SOLUTION_EVIDENCE"
        elif not s["reviewRequirementIds"]:
            s["trainingState"] = "TRAINED_CANONICAL_CHAIN"
        elif s["trainedRequirementIds"]:
            s["trainingState"] = "PARTIALLY_TRAINED"
        else:
            s["trainingState"] = "REVIEW_QUEUE"
    return scenarios


def merchant_evidence(tds):
    merchant_ids = OrderedDict()
    for d in tds:
        comp = (((d.get("payload") or {}).get("testControls") or {}).get("terminalIdentifierComponents") or {})
        if comp.get("merchantNumber"):
            merchant_ids.setdefault(comp["merchantNumber"], []).append(d["id"])
    return OrderedDict([
        ("merchantId", OrderedDict([
            ("status", "EVIDENCED_IN_TEST_DATA" if merchant_ids else "NO_EVIDENCE"),
            ("values", [OrderedDict([("value", v), ("testDataIds", ids)]) for v, ids in merchant_ids.items()]),
            ("carriedIn", "Segment 100 Element 102 Terminal Identifier component 'BUYPASS Merchant Number' (§12.1)"),
            ("businessScenario", "TS-006")])),
        ("merchantName", OrderedDict([
            ("status", "NO_EVIDENCE"),
            ("values", []),
            ("references", [
                "Segment 111 Appendix I-38 Table 047 Element 113 soft-merchant-name-capacity: NO_EVIDENCE_IN_THIS_SCOPED_BATCH",
                "BR-RULE-SEGDL1-R-003 Element 53 Merchant Name: host download (DL1), not part of the purchase request"])])),
    ])


def gates_and_blockers(scenarios, trained, review, tds):
    template = [s for s in scenarios if s["origin"] == "TEMPLATE"]
    not_in = [s["id"] for s in template if s["coverageStatus"] == "NOT_IN_TEST_SOLUTION"]
    executable = sum(1 for d in tds if d.get("readiness") == "EXECUTABLE")
    gates = OrderedDict([
        ("SOURCE_INVENTORY", "EVIDENCED_ATL105_ANCHORED_TEST_SOLUTION_SCOPE"),
        ("SEGMENT_KNOWLEDGE_MODEL", "REUSES_SEGMENT_TRAINING_REVIEW_REQUIRED"),
        ("CONTEXT_MATRIX", "EVIDENCED_FR_ASSIGNMENT_REVIEW_REQUIRED"),
        ("INDEPENDENT_BR_DERIVATION", "TEST_SOLUTION_BRS_DRAFT_REVIEW_REQUIRED"),
        ("TS_TC_TEST_DATA_CHAIN", "CANONICAL_TRACEABILITY_PASSED_PARTIAL" if review else "CANONICAL_TRACEABILITY_PASSED"),
        ("LIFECYCLE_AND_SPECIALIZED_FLOWS", "PARTIAL_TEMPLATE_GAPS" if not_in else "EVIDENCED_REVIEW_REQUIRED"),
        ("SERIALIZATION_AND_MUTATION", "NOT_STARTED_FOR_CONTEXT"),
        ("INDEPENDENT_AI_ARTIFACT_INTAKE", "NOT_STARTED_NO_AI_PACKAGE"),
        ("CONVERTER_AND_EXECUTION_READINESS", "REVIEW_REQUIRED"),
    ])
    blockers = []
    if not_in:
        blockers.append("template-scenarios-without-test-solution-evidence")
    if review:
        blockers.append("chains-failing-canonical-traceability")
    blockers += ["keyword-derived-fr-assignment-review", "merchant-name-evidence-missing"]
    if executable < len(tds):
        blockers.append("strict-execution-contract-not-met")
    blockers += ["ai-artifact-package-not-provisioned", "sme-tba-certification-pending"]
    return gates, blockers, not_in, executable


def main():
    m = mc.build_model()
    sel = Selector(m)
    trained = sel.run()
    brs, tss, tcs, tds, normalizations = package_records(sel, trained)
    scenarios = business_scenarios(m, trained, brs, tss, tcs, tds)
    review = [OrderedDict([("id", b), ("title", m["br_text"][b][0]),
                           ("functionalRequirementId", next(fr for fr, ids in m["detail"].items() if b in ids)),
                           ("status", "REVIEW_REQUIRED"), ("reasons", sel.reasons.get(b) or ["no valid canonical chain"]),
                           ("requiredEvidence", "A Test Solution TS -> TC -> TD chain that shares one ATL105 anchor with this BR "
                                                "(split shared scenarios per BR or complete the anchors), then SME/TBA review")])
              for b in m["detail_ids"] if b not in set(trained)]
    gates, blockers, not_in, executable = gates_and_blockers(scenarios, trained, review, tds)
    readiness = Counter(d.get("readiness") for d in tds)
    outcomes = Counter(c.get("expectedOutcome") for c in tcs)
    counts = OrderedDict([
        ("functionalRequirements", len(m["fr_names"])), ("businessScenarios", len(scenarios)),
        ("templateScenarios", sum(s["origin"] == "TEMPLATE" for s in scenarios)),
        ("testSolutionScenarios", sum(s["origin"] == "TEST_SOLUTION" for s in scenarios)),
        ("scopeRequirements", len(m["detail_ids"])), ("trainedRequirements", len(trained)), ("reviewQueue", len(review)),
        ("testScenarios", len(tss)), ("testCases", len(tcs)), ("testData", len(tds)), ("executableTestData", executable),
        ("brCoveragePercent", round(100.0 * len(trained) / len(m["detail_ids"]), 1)),
        ("templateScenariosWithoutTestSolutionEvidence", len(not_in)),
    ])
    training = OrderedDict([("contextId", CONTEXT_ID), ("status", "IN_PROGRESS"), ("reviewRequired", True),
                            ("gates", gates), ("blockers", blockers), ("counts", counts)])
    complete_pkg = ts_fr.PACKAGE
    package = OrderedDict([
        ("manifest", OrderedDict([
            ("packageId", "ATL105-MC-CREDIT-PURCHASE-TRAINING-001"), ("specification", "ATL105"),
            ("specificationVersion", SPEC_VERSION), ("authority", "INDEPENDENT_TEST_SOLUTION"),
            ("producer", "TEST_SOLUTION"), ("aiArtifactsIncluded", False),
            ("trainingContext", CONTEXT_ID), ("generator", rel(Path(__file__))),
            ("dataPolicy", "Verbatim Test Solution records; only logged link/status normalizations; no AI input, no fabricated BR/anchor/status.")])),
        ("training", training),
        ("businessContext", OrderedDict([
            ("businessRequirement", OrderedDict([("id", "BR-01"), ("title", (m["template_body"][0][1] or "").strip()),
                                                 ("source", rel(ts_fr.TEMPLATE))])),
            ("functionalRequirements", [OrderedDict([("id", k), ("title", v)]) for k, v in m["fr_names"].items()]),
            ("scenarios", scenarios),
            ("merchantEvidence", merchant_evidence(tds))])),
        ("businessRequirements", brs), ("testScenarios", tss), ("testCases", tcs), ("testData", tds),
        ("reviewQueue", review),
        ("provenance", OrderedDict([
            ("sourcePackage", rel(complete_pkg)), ("sourcePackageSha256", sha256(complete_pkg)),
            ("frMatrixTemplate", rel(ts_fr.TEMPLATE)), ("frMatrixTemplateSha256", sha256(ts_fr.TEMPLATE)),
            ("generatedOn", date.today().isoformat()),
            ("normalizations", normalizations), ("prunedLinks", sel.pruned)])),
    ])
    OUT_PACKAGE.write_text(json.dumps(package, indent=1, ensure_ascii=False) + "\n", encoding="utf-8")
    write_coverage(m, package, scenarios, review, readiness, outcomes)
    print(f"trained BR {len(trained)}/{len(m['detail_ids'])} | TS {len(tss)} | TC {len(tcs)} | TD {len(tds)} "
          f"| review {len(review)} | pruned links {len(sel.pruned)} | normalizations {len(normalizations)}")
    print(f"Wrote {OUT_PACKAGE}\nWrote {OUT_COVERAGE_JSON}\nWrote {OUT_COVERAGE_MD}")


def write_coverage(m, package, scenarios, review, readiness, outcomes):
    counts = package["training"]["counts"]
    template = [s for s in scenarios if s["origin"] == "TEMPLATE"]
    tmpl_status = Counter(s["coverageStatus"] for s in template)
    per_fr = OrderedDict()
    trained = {b["id"] for b in package["businessRequirements"]}
    for fr, ids in m["detail"].items():
        per_fr[fr] = OrderedDict([("title", m["fr_names"][fr]), ("scope", len(ids)),
                                  ("trained", sum(i in trained for i in ids)), ("review", sum(i not in trained for i in ids))])
    per_segment = OrderedDict()
    for b in m["detail_ids"]:
        segs = sorted({s for a in ts_fr.anchors(m["br_by_id"][b]) for s in str(a.get("segment") or "NONE").split(",")})
        for s in segs:
            row = per_segment.setdefault(s, OrderedDict([("scope", 0), ("trained", 0)]))
            row["scope"] += 1
            row["trained"] += b in trained
    coverage = OrderedDict([
        ("contextId", CONTEXT_ID), ("package", rel(OUT_PACKAGE)), ("counts", counts),
        ("templateScenarioCoverage", OrderedDict(sorted(tmpl_status.items()))),
        ("templateScenariosWithoutTestSolutionEvidence",
         [OrderedDict([("id", s["id"]), ("title", s["title"]), ("note", s["note"])]) for s in template
          if s["coverageStatus"] == "NOT_IN_TEST_SOLUTION"]),
        ("functionalRequirementCoverage", per_fr), ("segmentCoverage", per_segment),
        ("testDataReadiness", OrderedDict(sorted(readiness.items()))),
        ("testCaseExpectedOutcomes", OrderedDict(sorted(outcomes.items()))),
        ("reviewQueue", [OrderedDict([("id", r["id"]), ("functionalRequirementId", r["functionalRequirementId"]),
                                      ("reason", r["reasons"][0])]) for r in review]),
        ("merchantEvidence", package["businessContext"]["merchantEvidence"]),
    ])
    OUT_COVERAGE_JSON.write_text(json.dumps(coverage, indent=1, ensure_ascii=False) + "\n", encoding="utf-8")

    def table(header, rows):
        return "| " + " | ".join(header) + " |\n|" + "|".join("---" if i == 0 else "---:" for i in range(len(header))) + "|\n" \
            + "".join("| " + " | ".join(str(c).replace("|", "\\|") for c in r) + " |\n" for r in rows)

    gates = package["training"]["gates"]
    md = [
        "# Mastercard Credit Purchase BR Coverage\n",
        f"Generated by `scripts/train-mastercard-credit-purchase.py` from the Test Solution only (AI artifacts excluded). "
        f"Training package: [{OUT_PACKAGE.name}](../test-json/{OUT_PACKAGE.name}). "
        "Coverage below means a BR has at least one BR -> TS -> TC -> TD chain that passes the canonical traceability "
        "contract; it is not SME approval, AI-artifact intake or execution readiness.\n",
        "## Summary\n",
        table(["Metric", "Count"], [
            ["Business requirement", "BR-01 " + package["businessContext"]["businessRequirement"]["title"]],
            ["Functional requirements", counts["functionalRequirements"]],
            ["Business scenarios (template + Test Solution)", f"{counts['businessScenarios']} ({counts['templateScenarios']} + {counts['testSolutionScenarios']})"],
            ["Mastercard-applicable Test Solution BRs (denominator)", counts["scopeRequirements"]],
            ["BRs with a canonical chain (trained)", counts["trainedRequirements"]],
            ["BRs in review queue", counts["reviewQueue"]],
            ["BR coverage", f"{counts['brCoveragePercent']}%"],
            ["Trained TS / TC / TD", f"{counts['testScenarios']} / {counts['testCases']} / {counts['testData']}"],
            ["TD with EXECUTABLE readiness", counts["executableTestData"]],
            ["Template scenarios without Test Solution evidence", counts["templateScenariosWithoutTestSolutionEvidence"]],
        ]),
        "\n## Template Scenario Coverage\n",
        table(["Coverage status", "Template scenarios"], list(tmpl_status.items())),
        "\nNot in the Test Solution: " + ", ".join(f"{s['id']} {s['title']}" for s in template
                                                    if s["coverageStatus"] == "NOT_IN_TEST_SOLUTION") + ".\n",
        "\n## Coverage by Functional Requirement\n",
        table(["FR", "Title", "Scope BRs", "Trained", "Review"],
              [[k, v["title"], v["scope"], v["trained"], v["review"]] for k, v in per_fr.items()]),
        "\n## Coverage by Source Segment\n",
        table(["Segment", "Scope BRs", "Trained"], [[k, v["scope"], v["trained"]] for k, v in per_segment.items()]),
        "\nA BR anchored to several segments is counted under each.\n",
        "\n## Test Data and Outcomes\n",
        table(["TD readiness", "Count"], list(sorted(readiness.items()))),
        "\n" + table(["TC expected outcome", "Count"], list(sorted(outcomes.items()))),
        "\n## Review Queue\n",
        table(["BR", "FR", "First reason"], [[r["id"], r["functionalRequirementId"], r["reasons"][0]] for r in review])
        if review else "None.\n",
        "\n## Merchant Evidence\n",
        table(["Field", "Status", "Values"], [
            ["Merchant ID", package["businessContext"]["merchantEvidence"]["merchantId"]["status"],
             "; ".join(f"{v['value']} ({', '.join(v['testDataIds'])})" for v in package["businessContext"]["merchantEvidence"]["merchantId"]["values"]) or "-"],
            ["Merchant Name", package["businessContext"]["merchantEvidence"]["merchantName"]["status"], "-"]]),
        "\n## Training Gates\n",
        table(["Gate", "State"], list(gates.items())),
        "\nStatus: **" + package["training"]["status"] + "**. Blockers: " + ", ".join(f"`{b}`" for b in package["training"]["blockers"]) + ".\n",
    ]
    OUT_COVERAGE_MD.write_text("\n".join(md), encoding="utf-8")


if __name__ == "__main__":
    main()
