"""Quick sanity check for the generated matrix JSON."""
import json
from pathlib import Path

P = Path(r"c:\Users\F7OKAGW\OneDrive - Fiserv Corp\Desktop\core-auth-ai-validation-framework\regression-artifact-validator\POC-Segment-101-Coverage-Ratio-Matrix.json")

with P.open("r", encoding="utf-8") as f:
    d = json.load(f)

print("=== File-level check ===")
print("title:", d["reportMetadata"]["title"])
print("matrix rows:", len(d["matrix"]), "  target: 26")
print("unmapped AI BRs:", len(d["unmappedAiRequirements"]))
print()
print("=== Per-rule detail ===")
tot_br = tot_sc = tot_tc = 0
for row in d["matrix"]:
    rid = row["testSolutionBR"]["id"]
    status = row["testSolutionBR"]["status"]
    br_cnt = len(row["aiBusinessRequirements"])
    sc_cnt = sum(len(b["testScenarios"]) for b in row["aiBusinessRequirements"]) + len(row["unattachedAiScenarios"])
    tc_cnt = sum(sum(len(s["testCases"]) for s in b["testScenarios"]) for b in row["aiBusinessRequirements"]) + sum(len(s["testCases"]) for s in row["unattachedAiScenarios"])
    tot_br += br_cnt
    tot_sc += sc_cnt
    tot_tc += tc_cnt
    print(f"  {rid:16s} {status:22s} AI-BRs={br_cnt:3d}  scenarios={sc_cnt:3d}  TC-with-data={tc_cnt:3d}")
print()
print("Row-level totals:  AI-BR refs=", tot_br, " scenario refs=", tot_sc, " TC-with-testData=", tot_tc)
print()
print("=== Coverage Ratio ===")
print(json.dumps(d["coverageRatio"], indent=2))
