"""
Test Case Approval (Dev Arch §3 / TDD §7.5) — the third and final human gate.
Per-item approve/reject, never per-batch — deliberately does NOT reuse the
batch-approve shortcut used for Requirements/Scenarios today (this is the one
gate the architecture is explicit must be per-row).

Reject is targeted: only the rejected test case is affected. Escalating to the
underlying Scenario is a human decision (reviewer picks KNOWLEDGE_GAP feedback
and raises it with the scenario team) — this module never auto-touches
scenarios/candidates or approved/ files.

Reads:  test_generation/candidates/test_case_candidates.json
Writes: test_generation/approved/test_case_catalog.json   (approved only)
        test_generation/audit_log.jsonl                   (append-only)
        test_generation/feedback_catalog.json             (accumulated)
        test_generation/candidates/test_data/<TC-id>.json (only when modify()
        edits a test case's request/steps — keeps that one file in sync)
"""

from __future__ import annotations

import json
import sys
from datetime import datetime, timezone
from pathlib import Path
from typing import Any

HERE = Path(__file__).resolve().parent
CANDIDATES_FILE = HERE / "candidates" / "test_case_candidates.json"
APPROVED_FILE = HERE / "approved" / "test_case_catalog.json"
AUDIT_LOG_FILE = HERE / "audit_log.jsonl"
FEEDBACK_CATALOG_FILE = HERE / "feedback_catalog.json"

sys.path.insert(0, str(HERE))
import test_data_config  # noqa: E402
import qe_shaped_test_data  # noqa: E402

sys.path.insert(0, str(HERE.parent / "shared"))
from atomic_io import write_json_atomic, CorruptArtifactError  # noqa: E402

FEEDBACK_TYPES = {"ORACLE_CORRECTION", "CONSTRAINT_MISSING", "PRIORITY_ADJUSTMENT", "KNOWLEDGE_GAP"}


class ApprovalError(ValueError):
    pass


# Phase G3 (/memories/session/plan.md G20). A `verification.status` vs `verdict` key
# mismatch once let 521 REJECTed cases into the approved catalog silently, because a
# missing key reads as None and `None != "REJECT"` passes. Approval now refuses the
# case outright, and refuses an unrecognized verification shape too — a typo must fail
# loudly rather than default to "looks fine".
_VALID_VERIFICATION_STATUSES = {"PASS", "REJECT", "REJECTED_FINAL"}


def assert_verification_allows_approval(tc: dict) -> None:
    verification = tc.get("verification")
    if verification is None:
        return  # verification stage hasn't run; nothing to contradict
    if not isinstance(verification, dict) or "status" not in verification:
        raise ApprovalError(
            f"test case {tc.get('id')!r} has a malformed `verification` block "
            f"(keys={sorted(verification) if isinstance(verification, dict) else type(verification).__name__}); "
            "expected a 'status' key — refusing to approve rather than assume it passed")
    status = verification["status"]
    if status not in _VALID_VERIFICATION_STATUSES:
        raise ApprovalError(
            f"test case {tc.get('id')!r} has unrecognized verification.status {status!r}; "
            f"expected one of {sorted(_VALID_VERIFICATION_STATUSES)}")
    if status != "PASS":
        reasons = verification.get("reasons") or []
        raise ApprovalError(
            f"test case {tc.get('id')!r} cannot be approved: verification.status={status!r} "
            f"({len(reasons)} reason(s), e.g. {reasons[:1]})")


def _load_traceability_rows() -> dict[str, dict]:
    """testCaseId -> traceability.json row (Stage D2.3's rows[], one per test case).
    Empty dict when traceability.json doesn't exist yet — nothing to block on."""
    data = _load_json(test_data_config.CANDIDATES_DIR / "traceability.json")
    return {row["testCaseId"]: row for row in data.get("rows", []) if row.get("testCaseId")}


def assert_no_todo_elements(tc: dict, traceability_rows: dict[str, dict]) -> None:
    """E3.3 — a test case whose test data still carries a genuinely client-owed
    TODO element must not be approved. No traceability row at all (test data not
    composed for this id) is not blocked here — that is E3.4's job."""
    row = traceability_rows.get(tc.get("id"))
    if row is None:
        return
    todo_count = row.get("todoCount", 0)
    if todo_count:
        raise ApprovalError(
            f"test case {tc.get('id')!r} cannot be approved: its test data has {todo_count} "
            "TODO_CLIENT_VALUE element(s) still awaiting a client-supplied value")


def assert_has_test_data_file(tc: dict) -> None:
    """E3.4 — a catalog row with no test_data_file at all (LEGACY_TEST_DATA_
    MIGRATION_PENDING, or never composed/shaped) must never be approved
    pointing at nothing. Backfill it through the QE shaper first."""
    if not tc.get("test_data_file"):
        raise ApprovalError(
            f"test case {tc.get('id')!r} cannot be approved: it has no test_data_file "
            "(backfill it through the QE shaper, or exclude it from approval)")


def _load_json(path: Path) -> dict:
    """{} for a genuinely missing file; raises CorruptArtifactError (Stage B.3)
    for an existing-but-unparsable one — never silently treat a corrupt catalog
    as empty."""
    if not path.exists():
        return {}
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except json.JSONDecodeError as exc:
        raise CorruptArtifactError(path, exc) from exc


def load_candidates() -> dict:
    return _load_json(CANDIDATES_FILE)


def load_approved() -> dict:
    return _load_json(APPROVED_FILE)


def load_feedback_catalog() -> dict:
    return _load_json(FEEDBACK_CATALOG_FILE) or {"entries": []}


def _find(test_cases: list[dict], test_case_id: str) -> dict | None:
    return next((tc for tc in test_cases if tc.get("id") == test_case_id), None)


def pending_review() -> list[dict]:
    """Candidates neither approved nor rejected yet."""
    candidates = load_candidates().get("test_cases", [])
    approved_ids = {tc["id"] for tc in load_approved().get("test_cases", [])}
    return [tc for tc in candidates
            if tc["id"] not in approved_ids and tc.get("review_status") != "rejected"]


def _append_audit(item_id: str, action: str, reviewed_by: str, reason: str | None = None) -> dict:
    record = {
        "run_id": f"RUN-TESTGEN-{datetime.now(timezone.utc).strftime('%Y%m%dT%H%M%S')}",
        "stage": "test_case_approval",
        "item_id": item_id,
        "action": action,
        "reviewed_by": reviewed_by,
        "reason": reason,
        "timestamp": datetime.now(timezone.utc).isoformat(),
    }
    with AUDIT_LOG_FILE.open("a", encoding="utf-8") as f:
        f.write(json.dumps(record, ensure_ascii=False) + "\n")
    return record


def _append_feedback(feedback_type: str, test_case_id: str, reason: str, reviewed_by: str) -> dict:
    if feedback_type not in FEEDBACK_TYPES:
        raise ApprovalError(f"feedback_type must be one of {sorted(FEEDBACK_TYPES)}, got {feedback_type!r}")
    data = load_feedback_catalog()
    entry = {
        "test_case_id": test_case_id, "feedback_type": feedback_type,
        "reason": reason, "reviewed_by": reviewed_by,
        "timestamp": datetime.now(timezone.utc).isoformat(),
    }
    data.setdefault("entries", []).append(entry)
    write_json_atomic(data, FEEDBACK_CATALOG_FILE)
    return entry


def _promote_approved(ids_to_promote: set[str]) -> dict:
    """WO-3 (COPILOT_FIX_PLAN.md) — copies the GIVEN ids' message/meta files from
    CANDIDATES_DIR to APPROVED_DIR, additively. Regression fix: this used to call
    test_data_config.copy_approved_subset() with the FULL approved-id set, which also
    reconciles/prunes the approved dir down to exactly that set — fine for an end-of-run
    rebuild (suite_catalog.py), but called per single approval it either raised
    MassPruneGuardError (approved dir already has far more files than one just-approved
    id) or, with force=True, mass-deleted already-published files. Never call
    copy_approved_subset()/prune_orphans(force=True) from here again."""
    return test_data_config.promote_approved_ids(
        test_data_config.CANDIDATES_DIR, test_data_config.APPROVED_DIR, ids_to_promote)


def approve(test_case_id: str, reviewed_by: str, note: str | None = None,
            review_status: str = "approved") -> dict[str, Any]:
    """review_status defaults to "approved" (a real SME decision); the Streamlit dev/demo
    bypass passes "dev_approved" instead so it is never indistinguishable in the catalog
    from a genuine per-item review (WO-1, COPILOT_FIX_PLAN.md)."""
    candidates = load_candidates().get("test_cases", [])
    tc = _find(candidates, test_case_id)
    if not tc:
        raise ApprovalError(f"test case {test_case_id!r} not found in candidates")
    assert_verification_allows_approval(tc)
    assert_has_test_data_file(tc)
    assert_no_todo_elements(tc, _load_traceability_rows())

    approved_data = load_approved()
    existing = [t for t in approved_data.get("test_cases", []) if t["id"] != test_case_id]
    approved_tc = {**tc, "review_status": review_status, "reviewed_by": reviewed_by, "review_note": note}
    existing.append(approved_tc)
    approved_data["test_cases"] = existing
    approved_data["total_approved"] = len(existing)
    approved_data["state"] = "APPROVED"
    write_json_atomic(approved_data, APPROVED_FILE)
    _promote_approved({test_case_id})
    _append_audit(test_case_id, "approve", reviewed_by, note)
    return approved_tc


def approve_many(test_case_ids: list[str], reviewed_by: str, note: str | None = None,
                 review_status: str = "approved") -> int:
    """Batch equivalent of approve(). approve() rewrites the whole catalog per call, so
    approving N cases costs O(N^2) file writes (24GB of rewrites at N=1047)."""
    candidates = load_candidates().get("test_cases", [])
    by_id = {tc.get("id"): tc for tc in candidates}
    missing = [i for i in test_case_ids if i not in by_id]
    if missing:
        raise ApprovalError(f"{len(missing)} test case(s) not found in candidates, e.g. {missing[:3]}")

    # Checked for ALL ids before writing anything, so a single bad case can't leave the
    # approved catalog half-updated.
    blocked = []
    traceability_rows = _load_traceability_rows()
    for tid in test_case_ids:
        try:
            assert_verification_allows_approval(by_id[tid])
            assert_has_test_data_file(by_id[tid])
            assert_no_todo_elements(by_id[tid], traceability_rows)
        except ApprovalError as exc:
            blocked.append(str(exc))
    if blocked:
        raise ApprovalError(
            f"{len(blocked)} test case(s) failed verification and cannot be approved, "
            f"e.g. {blocked[:3]}")

    approved_data = load_approved()
    wanted = set(test_case_ids)
    existing = [t for t in approved_data.get("test_cases", []) if t["id"] not in wanted]
    for tid in test_case_ids:
        existing.append({**by_id[tid], "review_status": review_status,
                         "reviewed_by": reviewed_by, "review_note": note})
    approved_data["test_cases"] = existing
    approved_data["total_approved"] = len(existing)
    approved_data["state"] = "APPROVED"
    write_json_atomic(approved_data, APPROVED_FILE)
    _promote_approved(set(test_case_ids))

    run_id = f"RUN-TESTGEN-{datetime.now(timezone.utc).strftime('%Y%m%dT%H%M%S')}"
    ts = datetime.now(timezone.utc).isoformat()
    with AUDIT_LOG_FILE.open("a", encoding="utf-8") as f:
        for tid in test_case_ids:
            f.write(json.dumps({
                "run_id": run_id, "stage": "test_case_approval", "item_id": tid,
                "action": "approve", "reviewed_by": reviewed_by, "reason": note,
                "timestamp": ts,
            }, ensure_ascii=False) + "\n")
    return len(existing)


def modify(test_case_id: str, reviewed_by: str, reason: str, changes: dict[str, Any]) -> dict[str, Any]:
    """Applies reviewer corrections to a candidate in place. The case stays pending so
    the corrected version still goes through an explicit approve."""
    if not changes:
        raise ApprovalError("modify() requires at least one field change")
    candidates_data = load_candidates()
    tc = _find(candidates_data.get("test_cases", []), test_case_id)
    if not tc:
        raise ApprovalError(f"test case {test_case_id!r} not found in candidates")
    tc.update(changes)
    tc["review_status"] = "modified"
    tc["reviewed_by"] = reviewed_by
    tc["review_note"] = reason
    write_json_atomic(candidates_data, CANDIDATES_FILE)
    if "request" in changes or "steps" in changes:
        # A reviewer's edited request/steps can change the values this test case's
        # own data file already recorded (B6) — re-shape + re-write via the QE shaper
        # (Stage D2.5), the only test-data document builder left after the collapse.
        fmt = qe_shaped_test_data.FormatMaps(_load_json(qe_shaped_test_data.FORMAT_MAP_PATH))
        docs = qe_shaped_test_data.build_test_case_documents(fmt, tc)
        qe_shaped_test_data.write_documents(docs, test_data_config.CANDIDATES_DIR)
        approved_data = load_approved()
        if any(t.get("id") == test_case_id for t in approved_data.get("test_cases", [])):
            # WO-3 — the promoted copy must not go stale after a post-approval correction.
            _promote_approved({test_case_id})
    _append_audit(test_case_id, "modify", reviewed_by, reason)
    return tc


def reject(test_case_id: str, reviewed_by: str, reason: str, feedback_type: str) -> dict[str, Any]:
    candidates_data = load_candidates()
    tc = _find(candidates_data.get("test_cases", []), test_case_id)
    if not tc:
        raise ApprovalError(f"test case {test_case_id!r} not found in candidates")
    tc["review_status"] = "rejected"
    tc["reviewed_by"] = reviewed_by
    tc["review_note"] = reason
    write_json_atomic(candidates_data, CANDIDATES_FILE)
    _demote_if_approved(test_case_id)
    _append_audit(test_case_id, "reject", reviewed_by, reason)
    _append_feedback(feedback_type, test_case_id, reason, reviewed_by)
    return tc


def _demote_if_approved(test_case_id: str) -> None:
    """WO-3 — a test case that gets rejected/re-opened AFTER already being approved must
    not leave a stale promoted file behind ("approved" would then mean two different
    things depending on which artifact you look at). No-op when the id was never
    approved. Regression fix: deletes ONLY this id's own promoted files, never a
    force=True mass prune of the whole approved directory (that force=True bypassed the
    repo's deliberate anti-mass-delete guard and, in practice, deleted already-published
    files belonging to every OTHER approved id too)."""
    approved_data = load_approved()
    existing = approved_data.get("test_cases", [])
    if not any(t.get("id") == test_case_id for t in existing):
        return
    approved_data["test_cases"] = [t for t in existing if t.get("id") != test_case_id]
    approved_data["total_approved"] = len(approved_data["test_cases"])
    write_json_atomic(approved_data, APPROVED_FILE)
    test_data_config.demote_approved_id(test_data_config.APPROVED_DIR, test_case_id)
