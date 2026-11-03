"""Bounded ASCII Segment 100 fixture checks; not a full ATL105 serializer or host oracle."""

import re

FS = b"\x1c"
FIELD_NAMES = (
    "SegmentType", "SegmentLength", "InformationByte", "TerminalIdentifier", "PromptCode",
    "AccountNumber", "CardDiscretionaryBlockData", "EncryptedPINBlockData", "PumpLaneNumber",
    "FuelPurchaseAmount", "NonfuelAmount", "TaxAmount", "CashAmount", "SequenceNumber",
)
CARD_CODES = {"MC_CREDIT": "020", "MC_FLEET": "080", "MAESTRO_DEBIT": "011"}


def serialize(fields):
    """Render a deliberately bounded 14-field Segment 100 control."""
    if set(fields) != set(FIELD_NAMES) - {"SegmentLength"}:
        raise ValueError("Bounded serializer requires the exact declared field set")
    values = []
    for name in FIELD_NAMES:
        value = "000" if name == "SegmentLength" else fields[name]
        if not isinstance(value, str) or not value.isascii() or any(ord(char) < 32 for char in value):
            raise ValueError(f"ASCII field without control bytes required: {name}")
        values.append(value.encode("ascii"))
    raw = FS.join(values) + FS
    if len(raw) > 999:
        raise ValueError("Segment exceeds three-digit length")
    values[1] = f"{len(raw):03}".encode("ascii")
    return FS.join(values) + FS


def validate(raw, context):
    if context not in CARD_CODES:
        raise ValueError("Unknown network/program context")
    if not isinstance(raw, bytes):
        raise ValueError("Physical observation must be bytes")
    issues = []
    if not raw.isascii():
        return ["NON_ASCII"]
    if any(value < 32 and value != 28 for value in raw):
        issues.append("NON_PRINTABLE_FIELD")
    if not raw.endswith(FS):
        return ["FIELD_SEPARATOR_FRAMING"]
    parts = raw[:-1].split(FS)
    if len(parts) != len(FIELD_NAMES):
        return ["FIELD_SEPARATOR_FRAMING"]
    values = dict(zip(FIELD_NAMES, (part.decode("ascii") for part in parts)))
    if values["SegmentType"] != "100":
        issues.append("SEGMENT_TYPE")
    length = values["SegmentLength"]
    if not re.fullmatch(r"[0-9]{3}", length) or int(length) != len(raw):
        issues.append("SEGMENT_LENGTH")
    if not re.fullmatch(r"[0-9]{6}", values["SequenceNumber"]):
        issues.append("SEQUENCE_REPRESENTATION")
    prompt = values["PromptCode"]
    if len(prompt) != 4 or prompt[1:] != CARD_CODES[context]:
        issues.append("CARD_CODE_CONTEXT")
    return issues


def fixture_suite():
    rows = []
    for context, code in CARD_CODES.items():
        fields = dict.fromkeys(FIELD_NAMES, "")
        fields.pop("SegmentLength")
        fields.update(SegmentType="100", InformationByte="0", TerminalIdentifier="SYNTHETIC",
                      PromptCode="0" + code, AccountNumber="SYNTHETIC-NOT-A-CARD",
                      NonfuelAmount="00000100", SequenceNumber="123456")
        control = serialize(fields)
        parts = control[:-1].split(FS)
        bad_length = list(parts)
        bad_length[1] = f"{len(control) - 1:03}".encode("ascii")
        vectors = [
            ("CONTROL", control, []),
            ("LENGTH", FS.join(bad_length) + FS, ["SEGMENT_LENGTH"]),
            ("SEPARATOR", control.replace(FS, b"|", 1), ["FIELD_SEPARATOR_FRAMING"]),
            ("SEQUENCE", serialize({**fields, "SequenceNumber": "12345"}), ["SEQUENCE_REPRESENTATION"]),
            ("CARD", serialize({**fields, "PromptCode": "0999"}), ["CARD_CODE_CONTEXT"]),
        ]
        for name, raw, expected in vectors:
            actual = validate(raw, context)
            if actual != expected:
                raise ValueError(f"Unexpected bounded fixture result: {context}/{name}: {actual}")
            rows.append({"id": f"MM-WIRE-{context}-{name}", "context": context,
                         "wireHex": raw.hex(), "expectedIssues": expected, "observedIssues": actual,
                         "expectedValidation": "FAIL" if expected else "PASS",
                         "fixtureKind": "BOUNDED_SEGMENT100_ASCII_NOT_FULL_MESSAGE",
                         "readiness": "REVIEW_REQUIRED", "sourcePages": [223, 224, 409, 521, 522],
                         "limits": "Synthetic non-card/terminal values. Checks only framing, length, "
                         "SegmentType, SequenceNumber representation and declared program card-code mapping."})
    return rows


def walk_segments(node):
    if isinstance(node, dict):
        if "SegmentType" in node:
            yield node
        for value in node.values():
            yield from walk_segments(value)
    elif isinstance(node, list):
        for value in node:
            yield from walk_segments(value)


def field_observations(payload, context):
    """Observe producer JSON fields without treating objects as serialized bytes."""
    if context not in CARD_CODES:
        raise ValueError("Unknown physical assessment context")
    if not isinstance(payload, dict) or not payload:
        raise ValueError("Expected nonempty producer message object")
    segments = list(walk_segments(payload))
    standard = [segment for segment in segments if segment["SegmentType"] == "100"]
    if not standard:
        totals = len(payload) == 1 and next(iter(payload)) in {
            "Totals Request", "Totals with Proprietary Data Load Request"}
        return {"assessment": "NOT_APPLICABLE_NO_SEGMENT100" if totals else "FAIL",
                "violations": [] if totals else ["MISSING_SEGMENT100_OR_UNKNOWN_LAYOUT"],
                "unknowns": [], "financialCardCode": None}
    if len(standard) != 1:
        return {"assessment": "FAIL", "violations": ["SEGMENT100_MULTIPLICITY"],
                "unknowns": [], "financialCardCode": None}
    seg = standard[0]
    sequence = seg.get("SequenceNumber")
    prompt = seg.get("PromptCode")
    violations, unknowns = [], []
    if not isinstance(sequence, str) or not re.fullmatch(r"[0-9]{6}", sequence):
        (unknowns if isinstance(sequence, str) and "<" in sequence else violations).append("SEQUENCE_REPRESENTATION")
    if not isinstance(prompt, dict) or set(prompt) != {"TransactionType", "CardType"}:
        unknowns.append("UNMAPPED_PROMPT_SHAPE")
        card = None
    else:
        card = prompt["CardType"]
        if card != CARD_CODES[context]:
            # Savings/ACH alternatives need independent confirmation, not auto-rejection.
            if context == "MAESTRO_DEBIT" and card in {"012", "013"}:
                unknowns.append("MAESTRO_ALTERNATIVE_APPLICABILITY")
            else:
                violations.append("CARD_CODE_CONTEXT")
    for emv in (item for item in segments if item["SegmentType"] == "130"):
        value = emv.get("EMVCardSequenceNumber")
        if not isinstance(value, str) or not value.isascii() or len(value) != 3:
            violations.append("ELEMENT188_REPRESENTATION")
    return {"assessment": "FAIL" if violations else "REVIEW_REQUIRED" if unknowns else "PASS_BOUNDED_FIELDS",
            "violations": violations, "unknowns": unknowns, "financialCardCode": card}
