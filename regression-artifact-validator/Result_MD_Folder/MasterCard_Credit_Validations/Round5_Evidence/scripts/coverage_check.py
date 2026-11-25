"""Independent effective coverage: features x (all / ready / ready-and-defect-free) TCs, plus matrix views."""
import csv
import json
import os
import re
from collections import Counter, defaultdict

HERE = os.path.dirname(os.path.abspath(__file__))
tcs = json.load(open(os.path.join(HERE, "tc_index_network.json"), encoding="utf-8"))
rows = list(csv.DictReader(open(os.path.join(HERE, "MC_Credit_Round5_Findings.csv"), encoding="utf-8")))
defect_tcs = {r["test_case"] for r in rows if r["package"] == "network" and r["severity"] == "DEFECT"}
EM = ["EMV_CONTACT", "EMV_CONTACTLESS", "MAGSTRIPE", "CONTACTLESS_MAGSTRIPE", "MANUAL", "EMV_FALLBACK",
      "CREDENTIAL_ON_FILE", "CONTACTLESS_MOBILE"]
TX = ["PURCHASE", "AUTH_ONLY", "RETURN", "BALANCE_INQUIRY", "MOTO_PURCHASE", "MOTO_AUTH_ONLY"]


def chain(t):
    return t.get("message_chain") or ""


def roles(t):
    return [l.get("role") for l in t.get("tx_legs") or []]


def clean(t):
    return t["status"] == "ready" and t["id"] not in defect_tcs and t.get("test_data_file")


FEATURES = [
    ("Purchase/Capture approved (single leg)", lambda t: t["test_type"] == "happy_path" and t["dimensions"]["txn_category"] == "PURCHASE" and not t.get("is_flow")),
    ("Authorization Only approved (plain, no 'with ...')", lambda t: t["test_type"] == "happy_path" and t["dimensions"]["txn_category"] == "AUTH_ONLY" and " with " not in t["name"] and not t.get("is_flow")),
    ("Auth Only -> Completion (3->0 / 5->4)", lambda t: chain(t) in ("3 -> 0", "5 -> 4")),
    ("Purchase -> Purchase Reversal (0->8)", lambda t: chain(t).startswith("0 -> 8") and t["polarity"] == "Positive"),
    ("Authorization Only Reversal (3/5 -> 8)", lambda t: chain(t) in ("3 -> 8", "5 -> 8")),
    ("Auth Only -> Cancellation (3/5/B -> S)", lambda t: re.match(r"^[35B] -> S", chain(t)) and t["polarity"] == "Positive"),
    ("Purchase -> TOR (0/4/6 -> Z)", lambda t: re.match(r"^[046] -> Z", chain(t)) and t["polarity"] == "Positive"),
    ("TOR retried up to 3 times", lambda t: chain(t).count("Z") >= 3),
    ("Merchandise Return (7)", lambda t: t["dimensions"]["txn_category"] == "RETURN" and t["test_type"] == "happy_path"),
    ("Return -> Void of Return (7->U)", lambda t: chain(t).startswith("7 -> U") and t["polarity"] == "Positive"),
    ("Mail/Phone Purchase (6)", lambda t: t["dimensions"]["txn_category"] == "MOTO_PURCHASE" and t["polarity"] == "Positive"),
    ("Mail/Phone Auth Only (B)", lambda t: t["dimensions"]["txn_category"] == "MOTO_AUTH_ONLY" and t["polarity"] == "Positive"),
    ("Mail/Phone Reversal (6->C)", lambda t: chain(t).startswith("6 -> C") and t["polarity"] == "Positive"),
    ("Balance Inquiry (E)", lambda t: t["dimensions"]["txn_category"] == "BALANCE_INQUIRY" and t["polarity"] == "Positive"),
    ("Partial approval -> split tender (2nd card)", lambda t: "split_tender" in roles(t)),
    ("Partial approval -> reversal / TOR of approved amount", lambda t: re.search(r"Partial approval", t["name"], re.I) and "split" not in t["name"].lower()),
    ("Partial Approval Indicator = 0 (not supported)", lambda t: "Partial Approval Indicator = 0" in t["name"]),
    ("Totals after transactions (990)", lambda t: "990" in chain(t)),
    ("CAT / unattended purchase or auth (TT 4/5)", lambda t: t.get("acceptance_context") in ("PETROLEUM-UNATTENDED", "MCC_5542", "GENERAL_RETAIL-UNATTENDED", "HOSPITALITY-UNATTENDED", "SUPERMARKET-UNATTENDED", "UTILITIES-UNATTENDED") and t["polarity"] == "Positive"),
    ("COF initial (Table 032 08001I)", lambda t: "cof = initial" in t["name"]),
    ("COF subsequent (entry 10)", lambda t: t["dimensions"]["entry_mode"] == "CREDENTIAL_ON_FILE" and t["test_type"] == "happy_path"),
    ("AVS request (Table 003)", lambda t: "avs = true" in t["name"]),
    ("CVV2 (Table 004)", lambda t: "cvv2 =" in t["name"]),
    ("EMV online PIN", lambda t: re.search(r"online[ _]pin", t["name"]) and t["dimensions"]["entry_mode"] in ("EMV_CONTACT", "EMV_CONTACTLESS")),
    ("EMV offline PIN", lambda t: re.search(r"offline[ _]pin", t["name"]) and t["dimensions"]["entry_mode"] in ("EMV_CONTACT", "EMV_CONTACTLESS")),
    ("Chip fallback to manual (79)", lambda t: t["dimensions"]["entry_mode"] == "EMV_FALLBACK" and t["test_type"] == "happy_path"),
    ("Chip fallback to swipe (80)", lambda t: False),
    ("Track 1 read (magstripe)", lambda t: "track = 1" in t["name"] and t["dimensions"]["entry_mode"] in ("MAGSTRIPE", "CONTACTLESS_MAGSTRIPE")),
    ("Incremental / estimated auth (Table 082)", lambda t: "incremental" in t["name"]),
    ("Network token (Seg 153)", lambda t: "Network Token Data Request" in t["name"] and t["polarity"] == "Positive"),
    ("Card issuer decline (response check)", lambda t: "card issuer declines" in t["name"]),
    ("Expired card", lambda t: re.search(r"expired", t["name"], re.I)),
    ("Contactless-MSR-only terminal (Table 031 07 / capability 8)", lambda t: False),
]

out = []
for name, f in FEATURES:
    sel = [t for t in tcs if f(t)]
    ems = Counter(t["dimensions"]["entry_mode"] for t in sel if clean(t))
    out.append({"feature": name, "tcs": len(sel), "ready": sum(1 for t in sel if t["status"] == "ready"),
                "ready_clean": sum(1 for t in sel if clean(t)),
                "entry_modes_clean": len(ems), "statuses": dict(Counter(t["status"] for t in sel))})
# txn x entry: happy path ready clean
grid = defaultdict(dict)
for tx in TX:
    for em in EM:
        sel = [t for t in tcs if t["dimensions"]["txn_category"] == tx and t["dimensions"]["entry_mode"] == em and t["polarity"] == "Positive"]
        grid[tx][em] = (len(sel), sum(1 for t in sel if t["status"] == "ready"), sum(1 for t in sel if clean(t)))
ctx = defaultdict(lambda: Counter())
for t in tcs:
    ctx[t.get("acceptance_context")]["all"] += 1
    ctx[t.get("acceptance_context")][t["status"]] += 1
    if clean(t):
        ctx[t.get("acceptance_context")]["ready_clean"] += 1
summary = {
    "network_tcs": len(tcs),
    "ready": sum(1 for t in tcs if t["status"] == "ready"),
    "ready_clean": sum(1 for t in tcs if clean(t)),
    "tcs_with_defect": len(defect_tcs),
    "by_polarity_ready_clean": dict(Counter(t["polarity"] for t in tcs if clean(t))),
    "by_txn_ready_clean": dict(Counter(t["dimensions"]["txn_category"] for t in tcs if clean(t))),
    "negative_share_all": round(sum(1 for t in tcs if t["polarity"] == "Negative") / len(tcs), 3),
}
json.dump({"features": out, "grid": grid, "contexts": {k: dict(v) for k, v in ctx.items()}, "summary": summary},
          open(os.path.join(HERE, "coverage_independent.json"), "w", encoding="utf-8"), indent=1)
print(json.dumps(summary, indent=1))
print(f"{'feature':60s} {'TCs':>5s} {'ready':>6s} {'clean':>6s} {'EMs':>4s}")
for o in out:
    print(f"{o['feature'][:60]:60s} {o['tcs']:5d} {o['ready']:6d} {o['ready_clean']:6d} {o['entry_modes_clean']:4d}  {o['statuses']}")
print("grid (positive TCs: all/ready/clean):")
print(" " * 16 + "".join(f"{e[:12]:>14s}" for e in EM))
for tx in TX:
    print(f"{tx:16s}" + "".join(f"{'%d/%d/%d' % grid[tx][e]:>14s}" for e in EM))
print("contexts:")
for k, v in sorted(ctx.items(), key=lambda kv: -kv[1]["all"]):
    print(f"  {str(k):28s} all={v['all']:5d} ready={v['ready']:4d} clean={v['ready_clean']:4d} nci={v['needs_client_input']:4d} sme={v['pending_sme']:4d} rr={v['requires_review']:4d}")
