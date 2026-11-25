#!/usr/bin/env python3
"""HTML report: BR coverage of a BUYPASS ATL105 MasterCard test package.

Test BRs (the denominator) are the knowledge-base business rules (BR-<page>-<n>) that the package must test:
the rules routed to the MasterCard Credit network BR (ledger states tested_host, tested_device, composition_gap,
claimed_no_template, cited_only, sme), plus the rules of the shared Data Element Validation and Terminal & Host
Operations BRs. For every Test BR the report shows whether it is traced to a scenario, covered by a test case and
covered by test data.

The package's own coverage folders are used when present. When a folder is missing from the copy, the newest archived
package (or --prior) supplies the rule ledger, and its test case ids are mapped to the current ids through
id_migration.csv; the headline numbers then come from the current build_manifest.json summary.

    python scripts/build-br-coverage-report.py [--run DIR] [--prior DIR] [--out FILE]
"""
import argparse
import csv
import html
import json
import re
from collections import Counter, defaultdict
from datetime import datetime
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DEFAULT_RUN = ROOT / "mastercard_credit_new_16_11"
DEFAULT_PRIOR = ROOT / "16_nov_2026_mastercard_credit_output" / "mastercard_credit_new - Copy (2)"
NETWORK_STATES = ["tested_host", "tested_device", "composition_gap", "claimed_no_template", "cited_only", "sme"]
TRACED = {"tested_host", "tested_device", "composition_gap", "claimed_no_template", "cited_only", "tc_removed"}
STATES = {
    "tested_host": ("Tested - host message", "A test case sends a message to BUYPASS that sets, breaks or asserts the rule; "
                                              "it has test data."),
    "tested_device": ("Tested - device only", "A device / terminal test case checks the rule; no message to BUYPASS, so no "
                                              "test data."),
    "tc_removed": ("Test cases removed", "Tested in the archived ledger, but all its test cases were removed in this "
                                         "build (id_migration.csv)."),
    "composition_gap": ("Composition gap", "A test design technique claims the rule, but the generator cannot compose a "
                                           "message for it."),
    "claimed_no_template": ("Claimed, no template", "A technique claims the rule, but no test case template exists."),
    "cited_only": ("Cited only", "Test cases quote the rule as their basis, but none sets, breaks or asserts it."),
    "sme": ("SME review", "Not generated: the quote was not located verbatim or has low confidence (SME queue)."),
    "tested": ("Tested", "At least one test case of the shared BR tests the rule."),
    "not_tested": ("Not tested", "No test case of the shared BR tests the rule."),
}
COLORS = {"tested_host": "#1f9d55", "tested_device": "#4aa3df", "tc_removed": "#b07cc6", "composition_gap": "#f0a500",
          "claimed_no_template": "#e8743b", "cited_only": "#9aa5b1", "sme": "#d64545", "tested": "#1f9d55",
          "not_tested": "#d64545"}
SHARED = [("data_element_validation", "Data Element Validation (shared BR)"),
          ("terminal_and_host_operations", "Terminal & Host Operations (shared BR)")]


def load_csv(path):
    with open(path, encoding="utf-8-sig", newline="") as fh:
        return list(csv.DictReader(fh))


def split_ids(text):
    return [x for x in re.split(r"[;,\s]+", text or "") if x]


def archives(run):
    def version(p):
        m = re.match(r"v(\d+)", p.name)
        return int(m.group(1)) if m else -1
    return sorted((p for p in (run / "archive").glob("*") if p.is_dir()), key=version, reverse=True)


def locate(run, prior, rel):
    """(path, origin) of a package file: the run itself, its newest archive that has it, or the prior package."""
    if (run / rel).exists():
        return run / rel, "run"
    for arch in archives(run):
        if (arch / rel).exists():
            return arch / rel, f"archive/{arch.name}"
    if prior and (prior / rel).exists():
        return prior / rel, f"prior package '{prior.name}'"
    return None, None


def source_label(source_id):
    s = re.sub(r"^SRC-|-PDF-\d+$", "", source_id or "")
    return {"ATL105": "ATL105", "FISERV-EMV-GUIDE": "Fiserv EMV Guide", "BUYPASS-PIPS": "BUYPASS PIPS",
            "ATL105-TRANSARMOR": "ATL105 TransArmor"}.get(s, s.replace("-", " ").title())


def theme_label(theme_id, title):
    src, _, sec = (theme_id or "").partition(":")
    return f"{source_label(src)} {sec}".strip() + (f" - {title}" if title else "")


def esc(x):
    return html.escape(str(x if x is not None else ""))


def pct(n, d):
    return f"{100.0 * n / d:.1f}%" if d else "-"


def bar(n, d, color="#1f9d55"):
    w = 100.0 * n / d if d else 0
    return (f'<div class="bar" title="{n:,} of {d:,}"><span style="width:{w:.1f}%;background:{color}"></span>'
            f'<em>{pct(n, d)}</em></div>')


# ---------------------------------------------------------------- package
class Package:
    def __init__(self, run, prior):
        self.run, self.prior = run, prior
        self.build = json.loads((run / "build_manifest.json").read_text(encoding="utf-8"))
        self.summary = self.build.get("summary") or {}
        self.manifest = json.loads((run / "MANIFEST.json").read_text(encoding="utf-8"))
        self.present = sum((run / f).exists() for f in self.manifest.get("files", {}))
        self.mig = load_csv(run / "id_migration.csv") if (run / "id_migration.csv").exists() else []
        self.old2new = {r["old_test_case_id"]: r["new_test_case_id"] for r in self.mig
                        if r["change"] == "kept" and r["old_test_case_id"]}
        self.removed = {r["old_test_case_id"] for r in self.mig if r["change"] == "removed"}
        self.added = sum(r["change"] == "added" for r in self.mig)
        self.readme = (run / "README.md").read_text(encoding="utf-8") if (run / "README.md").exists() else ""
        self.scenario_names, self.br_rows = self._scenarios()
        self.tcs = self._network_test_cases()

    def _scenarios(self):
        names, brs = {}, []
        bs = self.run / "requirements_scenarios" / "business_scenarios.json"
        if bs.exists():
            for s in json.loads(bs.read_text(encoding="utf-8")).get("scenarios", []):
                names[s["id"]] = s.get("name") or s["id"]
        md = self.run / "SCENARIOS.md"
        if md.exists():
            for line in md.read_text(encoding="utf-8").splitlines():
                m = re.match(r"\|\s*(SC-[\w-]+)\s*\|\s*(.+?)\s*\|\s*(\d+)\s*\|$", line)
                if m:
                    names.setdefault(m.group(1), m.group(2))
                m = re.match(r"\|\s*([^|]+?\((?:network|shared) BR\))\s*\|\s*(\d+)\s*\|\s*(\d+)\s*\|", line)
                if m:
                    brs.append((m.group(1), int(m.group(2)), int(m.group(3))))
        return names, brs

    def _network_test_cases(self):
        """id -> dict(display, scenario, status, polarity, type, td_files, kind)."""
        scen = {r["new_test_case_id"]: r["new_scenario_id"] for r in self.mig if r["folder"] == "." and r["new_test_case_id"]}
        tcs = {}
        jl = self.run / "test_cases" / "test_cases.jsonl"
        if jl.exists():
            for line in jl.read_text(encoding="utf-8").splitlines():
                if not line.strip():
                    continue
                r = json.loads(line)
                files = r.get("test_data_leg_files") or ([r["test_data_file"]] if r.get("test_data_file") else [])
                tcs[r["id"]] = dict(display=r.get("display_id", ""), scenario=r.get("scenario_id") or scen.get(r["id"], ""),
                                    status=r.get("status", ""), polarity=r.get("polarity", ""), type=r.get("tc_type", ""),
                                    td_files=[Path(f).name for f in files], layer=r.get("tc_layer"))
            self.tc_source = "test_cases/test_cases.jsonl"
        else:
            rs = json.loads((self.run / "run_set.json").read_text(encoding="utf-8"))
            for r in rs.get("index", []):
                tcs[r["id"]] = dict(display=r.get("display_id", ""), scenario=scen.get(r["id"], ""), status=r.get("status", ""),
                                    polarity=r.get("polarity", ""), type=r.get("test_type", ""),
                                    td_files=list(r.get("test_data_files") or []), layer=None)
            self.tc_source = "run_set.json index + id_migration.csv (test_cases/ is not in this copy)"
        for t in tcs.values():
            t["kind"] = t["layer"] or ("host" if t["td_files"] or t["status"] == "blocked_no_test_data" else "device")
        td_dir = self.run / "test_data"
        self.td_local = td_dir.exists()
        self.td_listed = sum(len(t["td_files"]) for t in tcs.values())
        self.td_found = sum((td_dir / f).exists() for t in tcs.values() for f in t["td_files"]) if self.td_local else 0
        return tcs

    def mapper(self, origin):
        """Ledger test case ids -> current ids (identity when the ledger belongs to this run)."""
        if origin == "run":
            return lambda i: i
        return lambda i: self.old2new.get(i)


# ---------------------------------------------------------------- coverage
def network_coverage(pkg):
    path, origin = locate(pkg.run, pkg.prior, "coverage/rule_ledger.csv")
    if not path:
        raise SystemExit("No rule ledger found in the run, its archive or the prior package.")
    to_new = pkg.mapper(origin)
    rules = []
    for r in load_csv(path):
        if r.get("route") != "network" or r.get("state") not in NETWORK_STATES:
            continue
        host = [n for n in map(to_new, split_ids(r.get("host_test_cases"))) if n and n in pkg.tcs]
        dev = [n for n in map(to_new, split_ids(r.get("device_test_cases"))) if n and n in pkg.tcs]
        state = r["state"]
        if origin != "run":
            state = ("tested_host" if host else "tested_device" if dev else
                     "tc_removed" if state in ("tested_host", "tested_device") else state)
        td = sum(len(pkg.tcs[t]["td_files"]) for t in host)
        scen = sorted({pkg.tcs[t]["scenario"] for t in host + dev if pkg.tcs[t]["scenario"]})
        rules.append(dict(id=r["rule_id"], state=state, ledger_state=r["state"], kind=r.get("kind", ""),
                          theme=r.get("theme_id", ""), title=r.get("theme_title", ""), page=r.get("page", ""),
                          source=r.get("source_id", ""), host=host, device=dev, td=td, scenarios=scen,
                          statement=r.get("statement", ""), reason=r.get("reason", "") or r.get("reason_class", "")))
    return rules, path, origin


def shared_coverage(pkg, folder):
    path, origin = locate(pkg.run, pkg.prior, f"{folder}/coverage/rule_ledger.csv")
    if not path:
        return [], None, None, {}
    to_new = pkg.mapper(origin)
    tcs = {}
    for r in pkg.mig:
        if r["folder"] == folder and r["new_test_case_id"]:
            tcs[r["new_test_case_id"]] = split_ids(r["new_test_data_files"])
    rules = []
    for r in load_csv(path):
        tc = [n for n in map(to_new, split_ids(r.get("test_cases"))) if n]
        rules.append(dict(id=r["rule_id"], state=r.get("state", ""), theme=r.get("theme_id", ""), page=r.get("page", ""),
                          tcs=tc, td=sum(1 for t in tc if tcs.get(t)), statement=r.get("statement", "")))
    return rules, path, origin, tcs


def headline(pkg, rules):
    """Network Test BR counts: the build summary of this run when it has rule_states, else the ledger detail."""
    rs = (pkg.summary.get("generated_coverage") or {}).get("rule_states") or {}
    if rs:
        counts = {s: int(rs.get(s, 0)) for s in NETWORK_STATES}
        return counts, "build_manifest.json summary.generated_coverage.rule_states (this build)"
    return Counter(r["state"] for r in rules), "rule ledger"


# ---------------------------------------------------------------- HTML
CSS = """
body{font-family:'Segoe UI',Arial,sans-serif;margin:0;background:#f4f5f7;color:#1d2433;font-size:14px}
header{background:#1d2433;color:#fff;padding:18px 28px;border-bottom:4px solid #ff6600}
header h1{margin:0 0 4px;font-size:22px}header p{margin:2px 0;color:#c9d1dc}
main{padding:18px 28px;max-width:1500px}
h2{border-left:5px solid #ff6600;padding-left:10px;margin:28px 0 10px;font-size:18px}
h3{margin:18px 0 8px;font-size:15px}
.kpis{display:grid;grid-template-columns:repeat(auto-fit,minmax(210px,1fr));gap:12px}
.card{background:#fff;border-radius:8px;padding:14px 16px;box-shadow:0 1px 3px rgba(0,0,0,.12)}
.card .v{font-size:28px;font-weight:600}.card .l{color:#5b6573;font-size:12px;text-transform:uppercase;letter-spacing:.04em}
.card .p{font-size:15px;color:#1f9d55;font-weight:600}.card.bad .p{color:#d64545}
.bar{background:#e6e9ee;border-radius:4px;height:16px;position:relative;min-width:120px}
.bar span{display:block;height:100%;border-radius:4px}
.bar em{position:absolute;right:6px;top:0;font-size:11px;font-style:normal;line-height:16px;color:#1d2433}
.funnel td:first-child{width:260px}.funnel .bar{height:22px}.funnel .bar em{line-height:22px;font-size:12px}
table{border-collapse:collapse;width:100%;background:#fff;box-shadow:0 1px 3px rgba(0,0,0,.08);font-size:13px}
th{position:sticky;top:0;background:#2b3a55;color:#fff;text-align:left;padding:6px 8px;font-weight:600;z-index:1}
td{border-bottom:1px solid #e3e6ea;padding:5px 8px;vertical-align:top}
tr:hover td{background:#fff7f0}td.n,th.n{text-align:right;white-space:nowrap}
.note{background:#fff8e6;border-left:4px solid #f0a500;padding:10px 14px;margin:10px 0;border-radius:4px}
.ok{background:#eaf7ef;border-left:4px solid #1f9d55;padding:10px 14px;margin:10px 0;border-radius:4px}
.chip{display:inline-block;padding:1px 7px;border-radius:10px;color:#fff;font-size:11px;white-space:nowrap}
.tools{margin:8px 0;display:flex;gap:8px;align-items:center;flex-wrap:wrap}
.tools input,.tools select{padding:5px 8px;border:1px solid #c5ccd6;border-radius:4px;font-size:13px}
.scroll{max-height:620px;overflow:auto;border-radius:6px}
.stack{display:flex;height:26px;border-radius:5px;overflow:hidden;margin:6px 0}.stack span{display:block;height:100%}
.legend span{display:inline-block;margin-right:14px;font-size:12px}.legend i{display:inline-block;width:11px;height:11px;
border-radius:2px;margin-right:4px;vertical-align:middle}
small,.muted{color:#5b6573}details{margin:8px 0}summary{cursor:pointer;font-weight:600}
code{background:#eef0f3;padding:1px 4px;border-radius:3px;font-size:12px}
header code{background:#2b3a55;color:#fff}td.w{min-width:340px}
"""
JS = """
function filt(id){const q=document.getElementById(id+'-q').value.toLowerCase();const s=document.getElementById(id+'-s');
const st=s?s.value:'';let n=0;document.querySelectorAll('#'+id+' tbody tr').forEach(r=>{const ok=(!q||r.textContent.toLowerCase()
.includes(q))&&(!st||r.dataset.state===st);r.style.display=ok?'':'none';if(ok)n++});document.getElementById(id+'-c').textContent=n+' rows'}
"""


def chip(state):
    return f'<span class="chip" style="background:{COLORS.get(state, "#888")}">{esc(STATES.get(state, (state,))[0])}</span>'


def tools(tid, states, total):
    opts = "".join(f'<option value="{s}">{esc(STATES.get(s, (s,))[0])}</option>' for s in states)
    sel = f'<select id="{tid}-s" onchange="filt(\'{tid}\')"><option value="">All states</option>{opts}</select>' if states else ""
    return (f'<div class="tools"><input id="{tid}-q" placeholder="Search..." oninput="filt(\'{tid}\')" size="40">{sel}'
            f'<span id="{tid}-c" class="muted">{total} rows</span></div>')


def write_html(pkg, out, rules, led_path, led_origin, shared):
    counts, counts_src = headline(pkg, rules)
    tb = sum(counts.values())
    traced = sum(v for s, v in counts.items() if s in TRACED)
    tc_cov = counts.get("tested_host", 0) + counts.get("tested_device", 0)
    td_cov = counts.get("tested_host", 0)
    not_cov = tb - tc_cov
    n_tc, n_scen = len(pkg.tcs), pkg.summary.get("scenarios") or len({t["scenario"] for t in pkg.tcs.values()})
    scen_tc = Counter(t["scenario"] for t in pkg.tcs.values())
    led_counts = Counter(r["state"] for r in rules)
    projected = led_origin != "run"
    title = "BR Coverage - BUYPASS ATL105 MasterCard Credit"
    plan = re.search(r"\(plan (v\d+)\)", pkg.readme)
    h = [f'<!DOCTYPE html><html lang="en"><head><meta charset="utf-8"><title>{esc(title)}</title><style>{CSS}</style></head><body>',
         f'<header><h1>{esc(title)}{" - plan " + plan.group(1) if plan else ""}</h1>',
         f'<p>Run folder <code>{esc(pkg.run.name)}</code> - build <code>{esc(pkg.build.get("build_id"))}</code> - built '
         f'{esc(str(pkg.build.get("built_at", ""))[:10])} - spec {esc(pkg.build.get("spec_version", ""))} - knowledge base '
         f'<code>{esc(str(pkg.build.get("knowledge_base_hash", ""))[:12])}</code></p>',
         f'<p>Denominator: <b>Test BRs</b> = knowledge-base business rules (BR-&lt;page&gt;-&lt;n&gt;) the package must test. '
         f'Report generated {datetime.now():%Y-%m-%d %H:%M}.</p></header><main>']

    # ---- data note
    files = pkg.manifest.get("files", {})
    if pkg.present < len(files):
        h.append(f'<div class="note"><b>Incomplete copy of the package.</b> {pkg.present:,} of {len(files):,} files listed in '
                 f'<code>MANIFEST.json</code> are in this folder (test_cases/, test_data/, coverage/, requirements_scenarios/ '
                 f'and reports/ are missing). Headline numbers come from <code>build_manifest.json</code> of this build; '
                 f'rule-level detail comes from <code>{esc(led_origin)}</code> '
                 f'(same knowledge base) with its test case ids mapped to this build through <code>id_migration.csv</code> '
                 f'({len(pkg.old2new):,} kept, {pkg.added:,} added, {len(pkg.removed):,} removed). Test cases: '
                 f'{esc(pkg.tc_source)}.</div>')

    # ---- KPIs
    def card(label, value, sub, bad=False):
        return (f'<div class="card{" bad" if bad else ""}"><div class="l">{esc(label)}</div><div class="v">{value}</div>'
                f'<div class="p">{sub}</div></div>')
    h += ['<h2>1. MasterCard Credit - coverage of Test BRs</h2><div class="kpis">',
          card("Test BRs (denominator)", f"{tb:,}", "100%"),
          card("Traced to a scenario", f"{traced:,}", pct(traced, tb)),
          card("Covered by a test case", f"{tc_cov:,}", pct(tc_cov, tb)),
          card("Covered by test data", f"{td_cov:,}", pct(td_cov, tb)),
          card("Not covered by a test case", f"{not_cov:,}", pct(not_cov, tb), bad=True),
          card("Scenarios / test cases / test data files", f"{n_scen} / {n_tc:,} / {pkg.td_listed:,}",
               f'{pkg.summary.get("scenarios_with_tests", len(scen_tc))} scenarios with test cases'),
          '</div>']
    h += ['<table class="funnel" style="margin-top:14px"><tbody>']
    for label, n, color in (("Test BRs", tb, "#2b3a55"), ("Traced to a scenario", traced, "#4aa3df"),
                            ("Covered by a test case", tc_cov, "#1f9d55"), ("Covered by test data", td_cov, "#ff6600")):
        h.append(f'<tr><td><b>{esc(label)}</b> <span class="muted">{n:,}</span></td><td>{bar(n, tb, color)}</td></tr>')
    h.append(f'</tbody></table><p class="muted">Source: {esc(counts_src)}.</p>')

    # ---- state breakdown
    h += ['<h3>Test BRs by coverage state</h3><div class="stack">']
    h += [f'<span style="width:{100.0 * counts.get(s, 0) / tb:.2f}%;background:{COLORS[s]}" title="{esc(STATES[s][0])}: '
          f'{counts.get(s, 0)}"></span>' for s in NETWORK_STATES if tb]
    h += ['</div><div class="legend">'] + [f'<span><i style="background:{COLORS[s]}"></i>{esc(STATES[s][0])}</span>'
                                           for s in NETWORK_STATES] + ['</div>']
    h.append('<table><thead><tr><th>State</th><th>Meaning</th><th class="n">Test BRs</th><th class="n">% of Test BRs</th>'
             '<th>Traced to scenario</th><th>Test case</th><th>Test data</th>'
             + ('<th class="n">Ledger detail (mapped)</th>' if projected else '') + '</tr></thead><tbody>')
    for s in NETWORK_STATES:
        n = counts.get(s, 0)
        yes = lambda c: "yes" if c else "-"
        h.append(f'<tr><td>{chip(s)}</td><td>{esc(STATES[s][1])}</td><td class="n">{n:,}</td><td class="n">{pct(n, tb)}</td>'
                 f'<td>{yes(s in TRACED)}</td><td>{yes(s in ("tested_host", "tested_device"))}</td><td>{yes(s == "tested_host")}</td>'
                 + (f'<td class="n">{led_counts.get(s, 0):,}</td>' if projected else '') + '</tr>')
    if projected:
        h.append(f'<tr><td>{chip("tc_removed")}</td><td>{esc(STATES["tc_removed"][1])}</td><td class="n">-</td><td></td>'
                 f'<td>yes</td><td>-</td><td>-</td><td class="n">{led_counts.get("tc_removed", 0):,}</td></tr>')
    h.append(f'<tr><td><b>Total</b></td><td></td><td class="n"><b>{tb:,}</b></td><td class="n">100%</td><td></td><td></td><td></td>'
             + (f'<td class="n"><b>{len(rules):,}</b></td>' if projected else '') + '</tr></tbody></table>')
    if projected:
        h.append(f'<p class="muted">Ledger detail: {len(rules):,} Test BRs in the archived ledger mapped to this build: '
                 f'{led_counts.get("tested_host", 0) + led_counts.get("tested_device", 0):,} are still covered by kept test cases '
                 f'(this build reports {tc_cov:,}; the difference is covered by the {pkg.added:,} new test cases, which the archived '
                 f'ledger does not know), {led_counts.get("tc_removed", 0)} lost their test cases.</p>')

    # ---- by BR
    h += ['<h2>2. Coverage by business requirement</h2>',
          '<table><thead><tr><th>Business requirement</th><th class="n">Scenarios</th><th class="n">Test cases</th>'
          '<th class="n">Test data</th><th class="n">Test BRs</th><th class="n">Traced to scenario</th><th>% of Test BRs</th>'
          '<th class="n">Covered by test case</th><th>% of Test BRs</th><th class="n">Covered by test data</th><th>% of Test BRs</th>'
          '</tr></thead><tbody>']
    br_info = {name: (sc, tc) for name, sc, tc in pkg.br_rows}
    rows = [("MasterCard Credit (network BR)", n_scen, n_tc, f"{pkg.td_listed:,} files", tb, traced, tc_cov, td_cov)]
    for folder, name, (srules, spath, sorigin, stcs) in shared:
        if not srules:
            continue
        tested = sum(r["state"] == "tested" for r in srules)
        with_td = sum(r["state"] == "tested" and r["td"] > 0 for r in srules)
        sc, tc = br_info.get(name, ("-", len(stcs)))
        rows.append((name, sc, tc, f"{sum(1 for v in stcs.values() if v):,} test cases", len(srules), tested, tested, with_td))
    tot = [sum(r[i] for r in rows) for i in (4, 5, 6, 7)]
    for name, sc, tc, td, n, tr, tcv, tdv in rows + [("<b>All Test BRs</b>", "", "", "", *tot)]:
        h.append(f'<tr><td>{name if name.startswith("<b>") else esc(name)}</td><td class="n">{sc}</td>'
                 f'<td class="n">{f"{tc:,}" if isinstance(tc, int) else tc}</td><td class="n">{td}</td><td class="n"><b>{n:,}</b></td>'
                 f'<td class="n">{tr:,}</td><td>{bar(tr, n, "#4aa3df")}</td><td class="n">{tcv:,}</td><td>{bar(tcv, n)}</td>'
                 f'<td class="n">{tdv:,}</td><td>{bar(tdv, n, "#ff6600")}</td></tr>')
    h.append('</tbody></table><p class="muted">Shared BRs: the ledger states are tested / not tested, so "traced to scenario" '
             'equals "covered by test case"; "covered by test data" counts tested rules with at least one test case that has a '
             'test data file. ' + " ".join(f'{esc(name)}: rule ledger from {esc(o)}.' for _, name, (r, p, o, t) in shared if r)
             + '</p>')

    # ---- by theme
    themes = defaultdict(Counter)
    titles = {}
    for r in rules:
        themes[r["theme"]][r["state"]] += 1
        titles[r["theme"]] = r["title"]
    h += ['<h2>3. Network Test BRs by specification section (theme)</h2>', tools("themes", [], len(themes)),
          '<div class="scroll"><table id="themes"><thead><tr><th>Section</th><th class="n">Test BRs</th>'
          '<th class="n">Host</th><th class="n">Device</th><th class="n">Not covered</th><th>Covered by test case</th>'
          '<th>Covered by test data</th></tr></thead><tbody>']
    for th_id, c in sorted(themes.items(), key=lambda kv: (-sum(kv[1].values()), kv[0])):
        n = sum(c.values())
        cov = c["tested_host"] + c["tested_device"]
        h.append(f'<tr><td class="w">{esc(theme_label(th_id, titles.get(th_id)))}</td><td class="n">{n}</td>'
                 f'<td class="n">{c["tested_host"]}</td><td class="n">{c["tested_device"]}</td><td class="n">{n - cov}</td>'
                 f'<td>{bar(cov, n)}</td><td>{bar(c["tested_host"], n, "#ff6600")}</td></tr>')
    h.append('</tbody></table></div>')

    # ---- by scenario
    by_scen = defaultdict(list)
    for tid, t in pkg.tcs.items():
        by_scen[t["scenario"]].append((tid, t))
    rules_by_scen = Counter(s for r in rules for s in r["scenarios"])
    host_by_scen = Counter(s for r in rules if r["state"] == "tested_host" for s in r["scenarios"])

    def ts_num(items):
        m = re.match(r"BR\d+_TS(\d+)_", items[0][1]["display"] or "")
        return int(m.group(1)) if m else 10 ** 6
    h += [f'<h2>4. Scenarios ({n_scen}; {len(by_scen)} with test cases)</h2>', tools("scen", [], len(by_scen)),
          '<div class="scroll"><table id="scen"><thead><tr><th>Scenario</th><th>Name</th><th class="n">Test cases</th>'
          '<th class="n">Host</th><th class="n">Device</th><th class="n">Test data files</th><th class="n">Positive</th>'
          '<th class="n">Negative</th><th class="n">Ready</th><th class="n">Needs client input</th><th class="n">Review / SME</th>'
          '<th class="n">Blocked</th><th class="n">Test BRs covered</th><th class="n">of which with test data</th>'
          '</tr></thead><tbody>']
    for sid, items in sorted(by_scen.items(), key=lambda kv: ts_num(kv[1])):
        st = Counter(t["status"] for _, t in items)
        pol = Counter(t["polarity"] for _, t in items)
        kinds = Counter(t["kind"] for _, t in items)
        ts = re.match(r"(BR\d+_TS\d+)_", items[0][1]["display"] or "")
        h.append(f'<tr><td>{esc(ts.group(1) if ts else "")}<br><small>{esc(sid)}</small></td>'
                 f'<td class="w">{esc(re.sub(r"^BUYPASS ATL105 MasterCard Credit ", "", pkg.scenario_names.get(sid, "")))}</td>'
                 f'<td class="n">{len(items)}</td><td class="n">{kinds["host"]}</td>'
                 f'<td class="n">{kinds["device"]}</td><td class="n">{sum(len(t["td_files"]) for _, t in items)}</td>'
                 f'<td class="n">{pol["Positive"]}</td><td class="n">{pol["Negative"] + pol["Boundary"]}</td>'
                 f'<td class="n">{st["ready"]}</td><td class="n">{st["needs_client_input"]}</td>'
                 f'<td class="n">{st["requires_review"] + st["pending_sme"]}</td><td class="n">{st["blocked_no_test_data"]}</td>'
                 f'<td class="n">{rules_by_scen.get(sid, 0)}</td><td class="n">{host_by_scen.get(sid, 0)}</td></tr>')
    h.append('</tbody></table></div>')
    gaps = (pkg.summary.get("coverage_gaps_by_kind") or {}).get("scenario_without_test_cases")
    if gaps:
        h.append(f'<p class="note">{gaps} scenarios of this build have no test cases (build summary '
                 f'<code>coverage_gaps</code>); their names are in requirements_scenarios/, which is not in this copy.</p>')
    h.append('<p class="muted">Test BRs covered per scenario count a rule once per scenario in which one of its test cases '
             'runs, so the column does not add up to the total.</p>')

    # ---- rules
    order = [s for s in ["tested_host", "tested_device", "tc_removed", "composition_gap", "claimed_no_template", "cited_only",
                         "sme"] if led_counts.get(s)]
    h += [f'<h2>5. Network Test BRs - rule by rule ({len(rules):,})</h2>', tools("rules", order, len(rules)),
          '<div class="scroll"><table id="rules"><thead><tr><th>Rule</th><th>State</th><th>Section</th><th class="n">Page</th>'
          '<th>Kind</th><th>Statement</th><th class="n">Scenarios</th><th class="n">Test cases</th><th class="n">Test data files</th>'
          '<th>Test cases (first 5) / reason</th></tr></thead><tbody>']
    for r in sorted(rules, key=lambda r: (order.index(r["state"]) if r["state"] in order else 99, r["id"])):
        tcs = r["host"] + r["device"]
        detail = ", ".join(tcs[:5]) + (f" +{len(tcs) - 5}" if len(tcs) > 5 else "") if tcs else esc(r["reason"])
        h.append(f'<tr data-state="{r["state"]}"><td>{esc(r["id"])}</td><td>{chip(r["state"])}</td>'
                 f'<td>{esc(theme_label(r["theme"], ""))}</td><td class="n">{esc(r["page"])}</td><td>{esc(r["kind"])}</td>'
                 f'<td class="w">{esc(r["statement"][:280])}</td><td class="n">{len(r["scenarios"])}</td><td class="n">{len(tcs)}</td>'
                 f'<td class="n">{r["td"]}</td><td><small>{detail}</small></td></tr>')
    h.append('</tbody></table></div>')

    # ---- shared rules
    for folder, name, (srules, spath, sorigin, stcs) in shared:
        if not srules:
            continue
        tid = folder[:3]
        h += [f'<details><summary>{esc(name)} - {len(srules):,} Test BRs (rule ledger from {esc(sorigin)})</summary>',
              tools(tid, ["tested", "not_tested"], len(srules)),
              f'<div class="scroll"><table id="{tid}"><thead><tr><th>Rule</th><th>State</th><th>Section</th><th class="n">Page</th>'
              '<th>Statement</th><th class="n">Test cases</th><th class="n">With test data</th></tr></thead><tbody>']
        for r in sorted(srules, key=lambda r: (r["state"] != "not_tested", r["id"])):
            h.append(f'<tr data-state="{r["state"]}"><td>{esc(r["id"])}</td><td>{chip(r["state"])}</td>'
                     f'<td>{esc(theme_label(r["theme"], ""))}</td><td class="n">{esc(r["page"])}</td>'
                     f'<td class="w">{esc(r["statement"][:280])}</td><td class="n">{len(r["tcs"])}</td><td class="n">{r["td"]}</td></tr>')
        h.append('</tbody></table></div></details>')

    # ---- method
    h += ['<h2>6. How the numbers are calculated</h2><ul>',
          '<li><b>Test BR</b>: a business rule of the AI team\'s knowledge base routed to the BR. Network BR: ledger states '
          'tested_host, tested_device, composition_gap, claimed_no_template, cited_only, sme. Not counted: not_applicable and '
          'informational rules, and the rules routed to the shared BRs (data_element, operations), which are counted under '
          'their own BR in section 2.</li>',
          '<li><b>Traced to a scenario</b>: the BR\'s test design (a technique of its scenarios) claims the rule, or a test case '
          'cites it: every state except sme.</li>',
          '<li><b>Covered by a test case</b>: a test case sets, breaks or asserts the rule\'s own item (tested_host, '
          'tested_device). A test case that only quotes the rule leaves it cited_only.</li>',
          '<li><b>Covered by test data</b>: covered by a test case that sends a message to BUYPASS, i.e. has a test data file '
          '(tested_host). Device-only test cases have no test data.</li>',
          f'<li>Rule ledger: <code>{esc(led_path.relative_to(ROOT) if ROOT in led_path.parents else led_path)}</code>'
          f' ({esc(led_origin)}). Test cases: {esc(pkg.tc_source)}. Test data files listed: {pkg.td_listed:,}'
          + (f', found in test_data/: {pkg.td_found:,}' if pkg.td_local else ' (test_data/ is not in this copy)') + '.</li>',
          '<li>The ATL105 content of the test data (field formats, segment lengths, flows) is not part of this report; see the '
          'ATL105 validation reports.</li></ul>']
    h += ['</main><script>', JS, '</script></body></html>']
    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text("\n".join(h), encoding="utf-8")
    return dict(tb=tb, traced=traced, tc=tc_cov, td=td_cov, rows=rows)


def main():
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--run", type=Path, default=DEFAULT_RUN)
    ap.add_argument("--prior", type=Path, default=DEFAULT_PRIOR, help="full earlier package for ledgers missing from --run")
    ap.add_argument("--out", type=Path)
    a = ap.parse_args()
    run = a.run.resolve()
    out = a.out or run.parent / f"{run.name}_output" / "BR_Coverage_Report.html"
    pkg = Package(run, a.prior.resolve() if a.prior and a.prior.exists() else None)
    rules, led_path, led_origin = network_coverage(pkg)
    shared = [(folder, name, shared_coverage(pkg, folder)) for folder, name in SHARED]
    res = write_html(pkg, out, rules, led_path, led_origin, shared)
    for name, sc, tc, td, n, tr, tcv, tdv in res["rows"]:
        print(f"{name:42} Test BRs {n:5,}  traced {tr:5,} ({pct(tr, n)})  test case {tcv:5,} ({pct(tcv, n)})  "
              f"test data {tdv:5,} ({pct(tdv, n)})")
    print(f"Rule ledger: {led_origin}; test cases: {pkg.tc_source}")
    print(f"Written: {out}")


if __name__ == "__main__":
    main()
