"""Author independent synthetic release-profile chains; never promote them as approved coverage."""

import copy
import importlib.util
import json
from pathlib import Path


VALIDATOR_PATH = Path(__file__).with_name("new-format-validation.py")
SPEC = importlib.util.spec_from_file_location("release_validation", VALIDATOR_PATH)
assert SPEC is not None and SPEC.loader is not None
validator = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(validator)


def context(version: str, group: str = "MASTERCARD", response: bool = False, flow: bool = False) -> dict:
    generic = group in validator.network.GENERIC_GROUPS
    return {
        "platform": "BUYPASS", "specification": "ATL105", "version": version, "domain": "PAYMENT",
        "cardProgram": "BENEFIT" if group == "EBT" else "DEBIT" if generic or group == "STAR_SIGNATURE_DEBIT" else "CREDIT",
        "groupId": group, "network": None if generic else group,
        "genericCategory": group if generic else None,
        "messageFamily": "STANDARD_FINANCIAL_RESPONSE" if response else "STANDARD_FINANCIAL_REQUEST",
        "direction": "RESPONSE" if response else "REQUEST",
        "transactionCategory": "AUTHORIZATION" if flow else "SALE",
        "flowId": "OBSERVED_LIFECYCLE" if flow else "SINGLE_STEP_PURCHASE",
        "lifecycleRole": "OBSERVED_FLOW" if flow else "ORIGINAL",
        "cardPresence": "PRESENT", "entryMode": "CONTACT_CHIP", "channel": "POS",
        "eligibility": "REVIEW_REQUIRED",
    }


def token_observation(version: str, response: bool = False, size: int = 18) -> dict:
    token = "8" * size
    tables = [{"id": "001", "length": f"{size:03}", "value": token},
              {"id": "002", "length": "001" if response else "004", "value": "T" if response else "3012"}]
    if version == "2026-4" and not response:
        tables.append({"id": "007", "length": "001", "value": "Y"})
    observation = {"segmentType": "154" if response else "153", "tables": tables}
    if version == "2026-4":
        body = "".join(table["id"] + table["length"] + table["value"] for table in tables)
        prefix = observation["segmentType"] + ("" if response else "\x1c")
        length = len(prefix) + 3 + len(body)
        observation.update(segmentLength=f"{length:03}", wireHex=(prefix + f"{length:03}" + body).encode("ascii").hex())
    return observation


def ia_observation() -> dict:
    return {
        "terminalId": "SYNTHETIC-TERMINAL", "historyComplete": True, "openTransactions": [],
        "steps": [
            {"role": "ORIGINAL_AUTH", "transactionId": "SYNTHETIC-OPEN-1", "approved": True,
             "approvalCodeDE39": "SYN001"},
            {"role": "INCREMENTAL_AUTH", "transactionId": "SYNTHETIC-OPEN-1", "approvalCodeDE39": "SYN001"},
            {"role": "IA_REVERSAL", "transactionId": "SYNTHETIC-OPEN-1", "approvalCodeDE39": "SYN001"},
            {"role": "IA_COMPLETION", "transactionId": "SYNTHETIC-OPEN-1", "approvalCodeDE39": "SYN001"},
        ],
    }


def vectors(rule: str, version: str) -> list[tuple[str, dict, str, str | None]]:
    if rule == "sequence":
        return [(suffix, {} if value is None else {"sequenceNumber": value}, expected, None)
                for suffix, value, expected in [
                    ("MIN", "000001", "PASS"), ("MAX", "999999", "PASS"), ("ZERO", "000000", "FAIL"),
                    ("SHORT", "12345", "FAIL"), ("LONG", "1000000", "FAIL"), ("ALPHA", "12A456", "FAIL"),
                    ("MISSING", None, "FAIL"), ("NUMERIC_TYPE", 123456, "FAIL"),
                    ("UNICODE", "\uff11\uff12\uff13\uff14\uff15\uff16", "FAIL")]]
    if rule == "online-refund":
        valid = {"merchantParticipating": True, "onlineApprovalWanted": True, "indicatorPresent": True,
                 "tableId": "032", "subtableId": "11", "length": "001", "value": "Y"}
        if version == "2026-4":
            return [("RETIRED", valid, "NOT_ASSESSED", None)]
        return [("VALID", valid, "PASS", None),
                ("MISSING", {**valid, "indicatorPresent": False}, "FAIL", None),
                ("WRONG_VALUE", {**valid, "value": "N"}, "FAIL", None),
                ("UNKNOWN_CONTEXT", {**valid, "merchantParticipating": None}, "NOT_ASSESSED", None)]
    if rule in {"token-request", "token-response"}:
        response = rule == "token-response"
        valid = token_observation(version, response)
        rows = [("VALID", valid, "PASS", "PASS" if version == "2026-4" else None)]
        for size in [12, 13, 18, 19, 20]:
            passed = 1 <= size <= 19 if version == "2026-4" else 13 <= size <= 18
            rows.append((f"SIZE{size}", token_observation(version, response, size),
                         "PASS" if passed else "FAIL",
                         ("PASS" if passed else "NOT_ASSESSED") if version == "2026-4" else None))
        for suffix, mutate in [
            ("WRONG_LENGTH", lambda x: x["tables"][0].update(length="001")),
            ("UNKNOWN_TAG", lambda x: x["tables"][0].update(id="999")),
            ("EMPTY", lambda x: x.update(tables=[])),
            ("NONASCII", lambda x: x["tables"][0].update(value="\uff18" * 18)),
        ]:
            changed = copy.deepcopy(valid)
            mutate(changed)
            rows.append((suffix, changed, "NOT_ASSESSED" if suffix == "UNKNOWN_TAG" else "FAIL", None))
        if version == "2026-4":
            bad_wire = copy.deepcopy(valid)
            bad_wire["wireHex"] = valid["wireHex"][:-2] + "30"
            rows.append(("WIRE_CHANGED", bad_wire, "PASS", "FAIL"))
            for suffix, value in [("BAD_MONTH", "3013"), ("BAD_INDICATOR", "X")]:
                if response and suffix == "BAD_MONTH" or not response and suffix == "BAD_INDICATOR":
                    continue
                changed = copy.deepcopy(valid)
                changed["tables"][1]["value"] = value
                rows.append((suffix, changed, "FAIL", None))
        return rows
    if rule == "token-carry-forward":
        original = {"networkToken": "8" * 19, "expiry": "3012", "inputIndicator": "Y"}
        valid = {"provisionedNetworkTokens": True, "authOptimization": True, "original": original,
                 "originalResponse": {"newCardNumber": "9" * 19, "newExpiry": "3112"},
                 "followups": [{"role": role, "originalInformation": copy.deepcopy(original)}
                               for role in ["REVERSAL", "COMPLETION", "CANCELLATION", "VOID_RETURN", "TIMEOUT_REVERSAL"]]}
        changed = copy.deepcopy(valid)
        changed["followups"][0]["originalInformation"]["networkToken"] = "9" * 19
        return [("VALID", valid, "PASS", None), ("REPLACED_ORIGINAL", changed, "FAIL", None),
                ("UNKNOWN_ENTITLEMENT", {**valid, "authOptimization": None}, "NOT_ASSESSED", None),
                ("MISSING_HISTORY", {**valid, "original": None}, "NOT_ASSESSED", None)]
    if rule == "ia-table82":
        valid = {"present": True, "indicator": "082", "tableId": "82", "length": "004",
                 "terminalTransactionNumber": "0042"}
        return [("VALID", valid, "PASS", None), ("ABSENT", {"present": False}, "FAIL", None),
                ("PADDED_TABLE", {**valid, "tableId": "082"}, "FAIL", None),
                ("BAD_WIDTH", {**valid, "terminalTransactionNumber": "042"}, "FAIL", None),
                ("UNKNOWN_PRESENCE", {}, "NOT_ASSESSED", None)]
    if rule == "ia-lifecycle":
        valid = ia_observation()
        rows = [("VALID", valid, "PASS", None)]
        for suffix, mutate, expected in [
            ("NO_PREDECESSOR", lambda x: x["steps"].pop(0), "FAIL"),
            ("CLOSED", lambda x: x["steps"].append(copy.deepcopy(x["steps"][1])), "FAIL"),
            ("WRONG_APPROVAL", lambda x: x["steps"][1].update(approvalCodeDE39="OTHER1"), "FAIL"),
            ("DECLINED", lambda x: x["steps"][0].update(approved=False), "FAIL"),
            ("DUPLICATE_OPEN", lambda x: x["openTransactions"].append(
                {"terminalId": x["terminalId"], "transactionId": "SYNTHETIC-OPEN-1"}), "FAIL"),
            ("UNKNOWN_HISTORY", lambda x: x.update(historyComplete=False), "NOT_ASSESSED"),
        ]:
            changed = copy.deepcopy(valid)
            mutate(changed)
            rows.append((suffix, changed, expected, None))
        return rows
    if rule == "device022":
        return [(f"VALUE{value}", {"deviceType": value}, expected, None)
                for value, expected in [("35", "PASS"), ("36", "PASS"), ("37", "PASS"),
                                        ("38", "NOT_ASSESSED"), ("99", "NOT_ASSESSED"),
                                        ("34", "NOT_ASSESSED"), ("3", "FAIL"), ("AA", "FAIL")]]
    if rule == "discover-india056":
        rows = []
        for tag, value, role in [("01", "02", "INITIAL"), ("02", "F", "INITIAL"),
                                 ("03", "000000005000", "SUBSEQUENT"), ("04", "SYNTHETIC00001", "INITIAL"),
                                 ("05", "08", "INITIAL"), ("06", "1", "SUBSEQUENT"),
                                 ("07", "SYNTHETICREFERENCE01", "SUBSEQUENT")]:
            width = {"01": 2, "02": 1, "03": 12, "04": 14, "05": 2, "06": 1, "07": 20}[tag]
            # Fixed-width synthetic IDs are scoped shape examples, not actual cryptograms.
            if tag == "04":
                value = value.ljust(width, "0")
            actual = {"subtableId": tag, "paymentRole": role, "present": True,
                      "length": f"{width:02}", "value": value}
            rows.append((f"SUB{tag}-VALID", actual, "PASS", None))
            rows.append((f"SUB{tag}-WIDTH", {**actual, "length": "00"}, "FAIL", None))
        rows.extend([
            ("RECOMMENDED_ABSENT", {"subtableId": "01", "paymentRole": "INITIAL", "present": False}, "NOT_ASSESSED", None),
            ("WRONG_ROLE", {"subtableId": "01", "paymentRole": "SUBSEQUENT", "present": True,
                            "length": "02", "value": "02"}, "FAIL", None),
        ])
        return rows
    if rule == "ia-table063":
        valid = {"present": True, "role": "ORIGINAL", "terminalTransactionNumber": "0042"}
        return [("ORIGINAL", valid, "PASS", None),
                ("SUBSEQUENT", {**valid, "role": "SUBSEQUENT", "originalTerminalTransactionNumber": "0042"}, "PASS", None),
                ("CHANGED_RECOMMENDATION", {**valid, "role": "SUBSEQUENT",
                                            "originalTerminalTransactionNumber": "0043"}, "NOT_ASSESSED", None),
                ("ABSENT_MANDATORY", {"present": False}, "FAIL", None),
                ("BAD_WIDTH", {**valid, "terminalTransactionNumber": "042"}, "FAIL", None),
                ("MISSING_BINDING", {**valid, "role": "SUBSEQUENT"}, "NOT_ASSESSED", None)]
    raise ValueError(f"No independently authored vectors for {rule}")


def sample(version: str) -> dict:
    catalog = validator.profiles(version)
    package = {
        "schemaVersion": 2, "format": "BA_BUSINESS_CONTEXT_REVIEW_V2",
        "manifest": {"packageId": f"INDEPENDENT-RELEASE-CHAINS-{version}", "producer": "INDEPENDENT_TEST_SOLUTION",
                     "specification": "ATL105", "specificationVersion": version, "strictExecutionContract": False},
        **{kind: [] for kind in validator.KINDS},
        "limitations": ["Independent source-derived drafts, no semantic approval, full-message or host certification",
                        "Synthetic observations are not authenticated service/history or physical capture evidence"],
    }
    for rule, profile in catalog.items():
        group = "DISCOVER" if rule in {"ia-table82", "ia-lifecycle", "discover-india056"} else "MASTERCARD"
        business = context(version, group, rule == "token-response",
                           profile["layer"] == "lifecycle" or rule == "ia-table82")
        if rule == "online-refund":
            business.update(transactionCategory="RETURN", flowId="SINGLE_STEP_RETURN")
        if rule in {"ia-table82", "ia-lifecycle"}:
            business.update(flowId="INCREMENTAL_AUTHORIZATION")
        if rule == "discover-india056":
            business.update(issuerCountry="INDIA", paymentType="RECURRING")
        if rule == "ia-table063":
            business.update(industryUsage="INCREMENTAL_AUTH")
        common = {"sourceAnchors": profile["sourceAnchors"], "businessContext": business, "status": "DRAFT_REVIEW_REQUIRED"}
        br_id, ts_id = f"BR-{version}-{rule}", f"TS-{version}-{rule}"
        package["businessRequirements"].append({
            **copy.deepcopy(common), "id": br_id, "statement": f"Apply the independently derived {version} {rule} profile in its declared scope.",
            "claim": copy.deepcopy(profile["claim"]), "sourceEvidence": profile["sourceEvidence"],
        })
        package["testScenarios"].append({
            **copy.deepcopy(common), "id": ts_id, "requirementIds": [br_id],
            "intent": f"Exercise source-supported positive, negative, boundary and missing-evidence partitions for {rule}.",
            "preconditions": ["Synthetic scoped observations, not a valid full-message/host control"],
        })
        for suffix, observation, expected, wire_expected in vectors(rule, version):
            case_id = f"TC-{version}-{rule}-{suffix}"
            assertion = {"requirementId": br_id, "rule": rule, "observationId": "actual", "expectedResult": expected}
            if wire_expected is not None:
                assertion["expectedWireResult"] = wire_expected
            package["testCases"].append({
                **copy.deepcopy(common), "id": case_id, "scenarioIds": [ts_id],
                "steps": [f"Given the declared {version} source profile and observed synthetic context.",
                          f"When assessing the {suffix} observation, run the independent {rule} predicate.",
                          f"Then expect scoped result {expected}; do not infer a host response."],
                "assertions": [assertion],
            })
            package["testData"].append({
                **copy.deepcopy(common), "id": f"TD-{version}-{rule}-{suffix}", "testCaseIds": [case_id],
                "readiness": "SCOPED_OBSERVATION_ONLY", "payload": {"observations": {"actual": observation}},
            })
    return package


def build() -> dict[str, str]:
    files, summaries = {}, {}
    for version in sorted(validator.VERSIONS):
        document = sample(version)
        raw = validator.shared.json_text(document)
        results = validator.outputs(document, validator.shared.fingerprint(raw.encode("utf-8")))
        report = json.loads(results["validation-report.json"])
        if any(report[key] for key in ("mismatchedExpectationCount", "semanticContradictionCount", "wireFailureCount")):
            raise ValueError(f"Independent {version} fixture expectations no longer reconcile")
        files[f"{version}-independent-br-ts-tc-td.json"] = raw
        for name, content in results.items():
            files[f"{version}-{name}"] = content
        summaries[version] = {key: report[key] for key in (
            "counts", "supportedRuleProfiles", "assessedAssertionCount", "unassessedAssertionCount",
            "mismatchedExpectationCount", "semanticContradictionCount", "wireFailureCount", "readiness")}
    files["readiness-summary.json"] = validator.shared.json_text({
        "status": "BLOCKED", "completeValidationReady": False, "releases": summaries,
        "source": "INDEPENDENT_SOURCE_DERIVED_DRAFTS", "freshRegressionTests": "RECORDED_SEPARATELY",
        "builderSha256": validator.shared.fingerprint(Path(__file__).read_bytes()),
        "policy": "Scoped predicate execution is not approved BR coverage or host execution",
    })
    target = validator.profiles("2026-4")
    baseline = validator.profiles("2026-3")
    files["release-crosswalk.json"] = validator.shared.json_text({
        "baselineVersion": "2026-3", "targetVersion": "2026-4", "status": "DRAFT_REVIEW_REQUIRED",
        "scope": "IMPLEMENTED_SUPPLEMENT_PROFILES_ONLY_NOT_ALL_EXISTING_ARTEFACTS",
        "rules": [{"rule": rule, "relationship": (
            "RETIRED" if rule == "online-refund" else "MODIFIED" if rule == "token-request" else
            "UNCHANGED_REVALIDATED" if rule in baseline else "ADDED_PROFILE"),
            "baselineAnchors": baseline[rule]["sourceAnchors"] if rule in baseline else [],
            "targetAnchors": profile["sourceAnchors"], "approved": False}
            for rule, profile in target.items()],
    })
    files["draft-training-lessons.json"] = validator.shared.json_text({
        "status": "DRAFT_REVIEW_REQUIRED", "modelWeightsUpdated": False,
        "lessons": [{"version": version, "rule": rule, "claim": profile["claim"],
                     "sourceAnchors": profile["sourceAnchors"], "sourceEvidence": profile["sourceEvidence"],
                     "scope": "SOURCE_DERIVED_SCOPED_LESSON_NOT_APPROVED_TRUTH"}
                    for version in sorted(validator.VERSIONS)
                    for rule, profile in validator.profiles(version).items()],
    })
    files["manifest.json"] = validator.shared.json_text({
        "purpose": "SCOPED_RELEASE_READINESS_NOT_COMPLETE_VALIDATION",
        "files": {name: validator.shared.fingerprint(content.encode("utf-8")) for name, content in files.items()},
        "excludesSelf": True,
    })
    return files


if __name__ == "__main__":
    validator.network.publish(build(), validator.OUTPUT, False)
