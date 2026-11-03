import argparse
import collections
import hashlib
import json
import os
from pathlib import Path


def native(path):
    path = Path(os.path.normpath(str(path)))
    return Path("\\\\?\\" + str(path.resolve())) if os.name == "nt" and not str(path).startswith("\\\\?\\") else path


def read(path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def write(path, value):
    path.write_text(json.dumps(value, indent=2, ensure_ascii=True), encoding="utf-8")


def refresh(root):
    root = native(root)
    summary = read(root / "semantic-chain-summary.json")
    brs = read(root / "semantic-br-assessment.json")
    scenarios = read(root / "semantic-ts-assessment.json")
    cases = read(root / "semantic-tc-td-assessment.json")
    rules = read(root / "semantic-independent-source-rule-assessment.json")
    queries = read(root / "semantic-sme-query-register.json")
    summary["stageStatusCounts"] = {key: dict(collections.Counter(row["stageStatuses"][key] for row in cases)) for key in
        ["brMeaningStatus", "tsIntentStatus", "tcAssertionStatus", "tcExpectedOutcomeStatus", "tdPhysicalStatus", "tdPredicateStatus", "tdDependencyStatus"]}
    summary["structuralChainStatusCounts"] = dict(collections.Counter(row["stageStatuses"]["structuralChainStatus"] for row in cases))
    summary["smeQueryCount"] = len(queries)
    summary["smeQueryKinds"] = dict(collections.Counter(row["kind"] for row in queries))
    summary["smeReview"] = "DEFERRED_AT_USER_REQUEST_NOT_APPROVED"
    summary["semanticAlignmentConfirmed"] = 0
    summary["semanticCoverage"] = "NOT_CALCULABLE_NO_COMPLETE_INDEPENDENT_RULE_INTERPRETATIONS"
    summary["executionCertified"] = False
    write(root / "semantic-chain-summary.json", summary)
    cases_for_html = [{"caseId": row["caseId"], "scenarioId": row["scenarioId"], "intent": row["intent"],
        "transactionLabel": row["transactionLabel"], "requirementIds": row["requirementIds"],
        "stageStatuses": row["stageStatuses"], "queryIds": row["queryIds"]} for row in cases]
    query_for_html = [{key: row[key] for key in ["queryId", "kind", "subjectId", "stage", "question", "status"]} for row in queries]
    data = {"summary": summary, "rules": rules, "brs": brs, "scenarios": scenarios,
            "cases": cases_for_html, "queries": query_for_html}
    embedded = json.dumps(data, ensure_ascii=True, separators=(",", ":")).replace("</", "<\\/")
    html = HTML.replace("__DATA__", embedded)
    target = root / "FULL-SEMANTIC-CHAIN-REPORT.html"
    target.write_text(html, encoding="utf-8")
    (root / "FULL-SEMANTIC-CHAIN-REPORT.html").write_text(HTML.replace("__DATA__", embedded), encoding="utf-8")
    markdown = ["# ATL105 Whole-Delivery Semantic Chain Assessment", "",
        "SME review is deferred at user request, not approved. No semantic alignment or execution certification is granted.", "",
        "| Population/status | Result |", "|---|---|"]
    for label, value in [("AI BRs", summary["businessRequirements"]), ("AI TSs", summary["scenarios"]),
        ("AI TCs", summary["testCases"]), ("Independent source-rule BRs", summary["independentSourceRules"]),
        ("Structural case states", summary["structuralChainStatusCounts"]), ("BR meaning states", summary["stageStatusCounts"]["brMeaningStatus"]),
        ("TS intent states", summary["stageStatusCounts"]["tsIntentStatus"]), ("TC expected-outcome states", summary["stageStatusCounts"]["tcExpectedOutcomeStatus"]),
        ("TD dependency states", summary["stageStatusCounts"]["tdDependencyStatus"]), ("Deferred SME queries", summary["smeQueryCount"]),
        ("Semantic coverage", summary["semanticCoverage"]), ("Execution certified", summary["executionCertified"])]:
        markdown.append(f"| {label} | {json.dumps(value, ensure_ascii=True)} |")
    markdown.extend(["", "Structural chain status is distinct from semantic alignment. Per-row source, assertion, physical metadata and query evidence is in the filterable HTML and JSON/CSV exports.",
        "", "[Filterable full assessment](FULL-SEMANTIC-CHAIN-REPORT.html) | [Source BR baseline CSV](semantic-independent-source-rule-assessment.csv) | [All SME queries CSV](semantic-sme-query-register.csv)"])
    (root / "FULL-SEMANTIC-CHAIN-ASSESSMENT.md").write_text("\n".join(markdown), encoding="utf-8")
    result = validate(root)
    write(root / "semantic-final-report-verification.json", result)
    print(json.dumps({key: summary[key] for key in ["businessRequirements", "scenarios", "testCases", "physicalPayloadLegsAssessed",
        "physicalFilesHashVerified", "structuralChainStatusCounts", "stageStatusCounts", "independentSourceRules",
        "independentSourceRulesCuratedCandidates", "independentSourceRulesWithoutCuratedAssertions", "physicalMetadataPairsVerifiedInFinalization",
        "physicalTdProvisionalFields", "physicalTdUnavailableFieldOccurrences", "physicalTdUnresolvedSegmentOccurrences",
        "smeQueryCount", "smeQueryKinds", "semanticAlignmentConfirmed", "semanticCoverage", "executionCertified"]}, indent=2))


def validate(root):
    summary = read(root / "semantic-chain-summary.json")
    rules = read(root / "semantic-independent-source-rule-assessment.json")
    brs = read(root / "semantic-br-assessment.json")
    scenarios = read(root / "semantic-ts-assessment.json")
    cases = read(root / "semantic-tc-td-assessment.json")
    queries = read(root / "semantic-sme-query-register.json")
    assert len(rules) == summary["independentSourceRules"] == 601
    assert len(brs) == summary["businessRequirements"] == 6887
    assert len(scenarios) == summary["scenarios"] == 12679
    assert len(cases) == summary["testCases"] == 21123
    assert len(queries) == summary["smeQueryCount"]
    ids = {row["queryId"] for row in queries}
    assert len(ids) == len(queries) and all(row["status"] == "OPEN_REVIEW_DEFERRED" and not row["approvalGranted"] for row in queries)
    assert all(not row["approved"] and set(row["smeQueryIds"]) <= ids for row in rules)
    assert all(set(row["smeQueryIds"]) <= ids for row in [*brs, *scenarios])
    assert all(set(row["queryIds"]) <= ids and not row["semanticAlignmentConfirmed"] for row in cases)
    assert sum(summary["structuralChainStatusCounts"].values()) == len(cases)
    assert all(sum(statuses.values()) == len(cases) for statuses in summary["stageStatusCounts"].values())
    assert summary["semanticAlignmentConfirmed"] == 0 and not summary["executionCertified"]
    html_path = root / "FULL-SEMANTIC-CHAIN-REPORT.html"
    html = html_path.read_text(encoding="utf-8")
    for marker in ["Independent source-derived BR baseline", "Complete BR stage register", "Complete TS stage register", "Complete TC/TD chain", "All deferred SME queries"]:
        assert marker in html
    return {"status": "PASS", "BRs": len(brs), "TSs": len(scenarios), "TCs": len(cases), "independentRules": len(rules),
            "smeQueries": len(queries), "structuralStateCountsSum": sum(summary["structuralChainStatusCounts"].values()),
            "semanticAlignmentConfirmed": summary["semanticAlignmentConfirmed"], "executionCertified": summary["executionCertified"],
            "htmlBytes": html_path.stat().st_size, "htmlSha256": hashlib.sha256(html_path.read_bytes()).hexdigest()}


HTML = '''<!doctype html><html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>ATL105 | Whole-Delivery Semantic Assessment</title><style>
body{margin:0;background:#f4f6f5;color:#202526;font:14px 'Segoe UI',sans-serif;letter-spacing:0;overflow-wrap:anywhere}header{padding:22px 26px;background:#fff;border-top:5px solid #07665e}h1{font:700 25px Georgia,serif}h2{font-size:19px;color:#07665e}main{max-width:1450px;margin:auto;padding:20px 26px}section{padding:18px 0;border-bottom:1px solid #ccd8d4}.notice{padding:14px;background:#fff5dd;border-left:4px solid #9b6510}.filters{display:flex;gap:10px;flex-wrap:wrap;margin:12px 0}input,select,button{padding:9px;font:inherit;border:1px solid #aab6b2;border-radius:4px;background:#fff}table{border-collapse:collapse;width:100%;background:#fff}th,td{padding:8px;border-bottom:1px solid #d6dfdc;text-align:left;vertical-align:top;overflow-wrap:anywhere}th{background:#e5efec}.wrap{overflow:auto}.pager{display:flex;gap:10px;align-items:center;margin-top:10px}.muted{color:#626d69}@media(max-width:650px){header,main{padding:16px}.wrap table{min-width:900px}}@media print{.filters,.pager{display:none}}</style></head><body><header><h1>ATL105 | Whole-Delivery Semantic Chain Assessment</h1><p>October 5 Run1 | October 7 assessment | SME review deferred, not approved</p><a href="../AI-ARTIFACT-FILTER-VIEW.html">Run inventory</a> | <a href="../SEMANTIC-FIRST-BATCH-REPORT.html">Bounded first batch</a></header><main>
<section><h2>Conclusion</h2><div class="notice">All BR/TS/TC/TD stages were assessed against available evidence. Structural links are not business equivalence. Semantic alignment confirmed: <b>0</b>; semantic coverage: <b>NOT_CALCULABLE</b>; execution certification: <b>NOT GRANTED</b>. SME queries are recorded and review is deferred, not approved.</div><div class="wrap"><table><thead><tr><th>Measure/status</th><th>Counts</th></tr></thead><tbody id="summary"></tbody></table></div></section>
<section><h2>Independent source-derived BR baseline</h2><div class="filters"><input id="rule-search" placeholder="Rule/title/anchor"><select id="rule-status"></select></div><p id="rule-count"></p><div class="wrap"><table><thead><tr><th>Rule</th><th>Title</th><th>Anchor</th><th>Catalog/status</th><th>Query</th></tr></thead><tbody id="rule-body"></tbody></table></div><div class="pager"><button id="rule-prev">Previous</button><span id="rule-page"></span><button id="rule-next">Next</button></div></section>
<section><h2>Complete BR stage register</h2><div class="filters"><input id="br-search" placeholder="BR or source rule"></div><p id="br-count"></p><div class="wrap"><table><thead><tr><th>BR</th><th>Source rule</th><th>Statement</th><th>Linked cases</th><th>Meaning status</th><th>Query</th></tr></thead><tbody id="br-body"></tbody></table></div><div class="pager"><button id="br-prev">Previous</button><span id="br-page"></span><button id="br-next">Next</button></div></section>
<section><h2>Complete TS intent register</h2><div class="filters"><input id="ts-search" placeholder="Scenario or BR"></div><p id="ts-count"></p><div class="wrap"><table><thead><tr><th>TS</th><th>BR links</th><th>Intent status</th><th>Query</th></tr></thead><tbody id="ts-body"></tbody></table></div><div class="pager"><button id="ts-prev">Previous</button><span id="ts-page"></span><button id="ts-next">Next</button></div></section>
<section><h2>Complete TC/TD chain</h2><div class="filters"><input id="case-search" placeholder="TC, scenario or BR"><select id="case-status"></select></div><p id="case-count"></p><div class="wrap"><table><thead><tr><th>TC/TS</th><th>Structure</th><th>BR meaning</th><th>TC assertion/outcome</th><th>TD physical/predicate/dependency</th><th>Queries</th></tr></thead><tbody id="case-body"></tbody></table></div><div class="pager"><button id="case-prev">Previous</button><span id="case-page"></span><button id="case-next">Next</button></div></section>
<section><h2>All deferred SME queries</h2><div class="filters"><input id="query-search" placeholder="Query ID, artifact, question"><select id="query-kind"></select></div><p id="query-count"></p><div class="wrap"><table><thead><tr><th>Query ID</th><th>Kind/stage</th><th>Subject</th><th>Question</th><th>Status</th></tr></thead><tbody id="query-body"></tbody></table></div><div class="pager"><button id="query-prev">Previous</button><span id="query-page"></span><button id="query-next">Next</button></div></section>
<section><h2>Evidence exports</h2><p><a href="semantic-chain-summary.json">Summary</a> | <a href="semantic-independent-source-rule-assessment.json">601 source BRs</a> | <a href="semantic-independent-source-rule-assessment.csv">Source BR CSV</a> | <a href="semantic-br-assessment.json">AI BR assessments</a> | <a href="semantic-ts-assessment.json">TS assessments</a> | <a href="semantic-tc-td-assessment.json">TC/TD assessments</a> | <a href="semantic-sme-query-register.json">SME JSON</a> | <a href="semantic-sme-query-register.csv">SME CSV</a> | <a href="../semantic-alignment-2026-10-07-mitigation-final-v4/SEMANTIC-MITIGATION-PLAN.html">Prioritized mitigation plan</a></p></section></main>
<script id="data" type="application/json">__DATA__</script><script>'use strict';const d=JSON.parse(document.getElementById('data').textContent),$=id=>document.getElementById(id),size=50,p={rule:0,br:0,ts:0,cases:0,query:0};function row(id,vals){const tr=document.createElement('tr');for(const val of vals){const td=document.createElement('td');td.textContent=val??'';tr.append(td)}$(id).append(tr)}for(const [label,value] of [['AI BRs',d.summary.businessRequirements],['AI TSs',d.summary.scenarios],['AI TCs',d.summary.testCases],['Independent source BRs',d.summary.independentSourceRules],['Curated candidates / no assertions',d.summary.independentSourceRulesCuratedCandidates+' / '+d.summary.independentSourceRulesWithoutCuratedAssertions],['Physical TD legs',d.summary.physicalPayloadLegsAssessed],['Metadata pairs reverified',d.summary.physicalMetadataPairsVerifiedInFinalization],['Provisional TD fields',d.summary.physicalTdProvisionalFields],['Unavailable field occurrences',d.summary.physicalTdUnavailableFieldOccurrences],['Unresolved segment occurrences',d.summary.physicalTdUnresolvedSegmentOccurrences],['Structural chain states',JSON.stringify(d.summary.structuralChainStatusCounts)],['Semantic stage states',JSON.stringify(d.summary.stageStatusCounts)],['Deferred SME queries',d.summary.smeQueryCount],['Semantic coverage',d.summary.semanticCoverage],['Execution certified',d.summary.executionCertified]])row('summary',[label,value]);$('rule-status').replaceChildren(new Option('All rule states',''),...Array.from(new Set(d.rules.map(x=>x.semanticMeaningStatus)),x=>new Option(x,x.semanticMeaningStatus)));$('case-status').replaceChildren(new Option('All structures',''),...Object.keys(d.summary.structuralChainStatusCounts).map(x=>new Option(x,x)));$('query-kind').replaceChildren(new Option('All query kinds',''),...Object.keys(d.summary.smeQueryKinds).map(x=>new Option(x,x)));function page(k,a,search,status,body,count,label,prev,next,fields,state=x=>''){const t=$(search).value.toLowerCase(),s=status?$(status).value:'',f=a.filter(x=>(!s||state(x)===s)&&(!t||fields(x).toLowerCase().includes(t))),n=Math.max(1,Math.ceil(f.length/size));p[k]=Math.min(p[k],n-1);$(body).replaceChildren();for(const x of f.slice(p[k]*size,(p[k]+1)*size))row(body,fields(x).split('\t'));$(count).textContent=f.length.toLocaleString()+' selected / '+a.length.toLocaleString();$(label).textContent='Page '+(p[k]+1)+' / '+n;$(prev).disabled=p[k]===0;$(next).disabled=p[k]===n-1}function draw(){page('rule',d.rules,'rule-search','rule-status','rule-body','rule-count','rule-page','rule-prev','rule-next',x=>[x.ruleId,x.title,x.canonicalAnchor,x.sourceCatalog+' '+x.semanticMeaningStatus,x.smeQueryIds].join('\t'),x=>x.semanticMeaningStatus);page('br',d.brs,'br-search',null,'br-body','br-count','br-page','br-prev','br-next',x=>[x.requirementId,x.sourceRuleId,x.statement,x.caseCount,x.meaningStatus,x.smeQueryIds].join('\t'));page('ts',d.scenarios,'ts-search',null,'ts-body','ts-count','ts-page','ts-prev','ts-next',x=>[x.scenarioId,x.requirementIds,x.intentStatus,x.smeQueryIds].join('\t'));page('cases',d.cases,'case-search','case-status','case-body','case-count','case-page','case-prev','case-next',x=>[x.caseId,x.scenarioId,x.intent,x.stageStatuses.structuralChainStatus,x.stageStatuses.brMeaningStatus,x.stageStatuses.tcAssertionStatus+' '+x.stageStatuses.tcExpectedOutcomeStatus,x.stageStatuses.tdPhysicalStatus+' '+x.stageStatuses.tdPredicateStatus+' '+x.stageStatuses.tdDependencyStatus,x.queryIds].join('\t'),x=>x.stageStatuses.structuralChainStatus);page('query',d.queries,'query-search','query-kind','query-body','query-count','query-page','query-prev','query-next',x=>[x.queryId,x.kind,x.stage,x.subjectId,x.question,x.status].join('\t'),x=>x.kind)}for(const id of ['rule-search','br-search','ts-search','case-search','query-search'])$(id).addEventListener('input',draw);for(const id of ['rule-status','case-status','query-kind'])$(id).addEventListener('change',draw);for(const k of Object.keys(p)){const c=k==='cases'?'case':k;$(c+'-prev').onclick=()=>{p[k]--;draw()};$(c+'-next').onclick=()=>{p[k]++;draw()}}draw();</script></body></html>'''
def validate(root):
    summary = read(root / "semantic-chain-summary.json")
    rules = read(root / "semantic-independent-source-rule-assessment.json")
    brs = read(root / "semantic-br-assessment.json")
    scenarios = read(root / "semantic-ts-assessment.json")
    cases = read(root / "semantic-tc-td-assessment.json")
    queries = read(root / "semantic-sme-query-register.json")
    query_ids = {item["queryId"] for item in queries}
    assert len(rules) == summary["independentSourceRules"] == 601
    assert len(brs) == summary["businessRequirements"] == 6887
    assert len(scenarios) == summary["scenarios"] == 12679
    assert len(cases) == summary["testCases"] == 21123
    assert len(queries) == summary["smeQueryCount"] and len(query_ids) == len(queries)
    assert all(item["status"] == "OPEN_REVIEW_DEFERRED" and not item["approvalGranted"] for item in queries)
    assert all(not item["approved"] and set(item["smeQueryIds"]) <= query_ids for item in rules)
    assert all(set(item["smeQueryIds"]) <= query_ids for item in [*brs, *scenarios])
    assert all(set(item["queryIds"]) <= query_ids and not item["semanticAlignmentConfirmed"] for item in cases)
    assert sum(summary["structuralChainStatusCounts"].values()) == len(cases)
    assert all(sum(counts.values()) == len(cases) for counts in summary["stageStatusCounts"].values())
    assert summary["semanticAlignmentConfirmed"] == 0 and not summary["executionCertified"]
    html = (root / "FULL-SEMANTIC-CHAIN-REPORT.html").read_text(encoding="utf-8")
    assert all(value in html for value in ["Independent source-derived BR baseline", "Complete BR stage register", "Complete TS intent register", "Complete TC/TD chain", "All deferred SME queries"])
    return {"status": "PASS", "AI_BR": len(brs), "TS": len(scenarios), "TC": len(cases), "independentSourceRules": len(rules),
            "smeQueries": len(queries), "structuralCaseStatesSum": len(cases), "semanticAlignmentConfirmed": 0,
            "executionCertified": False, "htmlBytes": (root / "FULL-SEMANTIC-CHAIN-REPORT.html").stat().st_size}