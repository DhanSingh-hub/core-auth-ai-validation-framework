"""Source-scoped ATL105 application-body checks; no transport, crypto or host certification."""

import re
from datetime import datetime

from mastercard_maestro_offline import FS, walk_segments

FIELDS = (
    "SegmentType", "SegmentLength", "InformationByte", "TerminalIdentifier", "PromptCode",
    "AccountNumber", "CardDiscretionaryBlockData", "EncryptedPINBlockData", "PumpLaneNumber",
    "FuelPurchaseAmount", "NonfuelAmount", "TaxAmount", "CashAmount", "SequenceNumber",
    "ApprovalNumber", "LocalDateandLocalTime", "PartialApprovalIndicator",
)
FOUR_DIGIT = {"103", "114", "115", "118", "120", "130", "131"}
MAXIMUMS = {"100": 218, "101": 308, "102": 381, "103": 3334, "104": 86,
            "111": 999, "123": 186, "130": 9999, "134": 77, "135": 110,
            "143": 399, "145": 999, "146": 999, "151": 2309, "152": 2309, "153": 999}
FINANCIAL = {"Financial Transaction Request", "EMV Financial Transaction Request"}
TOTALS = {"Totals Request", "Totals with Proprietary Data Load Request"}
TRANSACTIONS = set("03456789ABCDEKLMNQSTUVZ")
SOURCE_PINS = [
    {"page": 167, "quote": "All Financial Transaction Requests contain Element Nos. 55 and 63."},
    {"page": 223, "quote": "All fields are separated by a Field Separator."},
    {"page": 224, "quote": "Sequence Number"},
    {"page": 225, "quote": "Indicates whether or not a merchant supports credit/debit Partial Approvals."},
    {"page": 364, "quote": "The = character is used instead of a Field Separator."},
    {"page": 365, "quote": "The ^ character is used instead of a Field Separator."},
    {"page": 406, "quote": "This figure is calculated using the lengths of all data elements"},
    {"page": 409, "quote": "Representation: Fixed length of six digits"},
    {"page": 440, "quote": "Required on all transactions."},
]
LIMITS = ("Application-body shape only; transport framing/checksum, terminal assignment, "
          "account authenticity, conditional companion eligibility, crypto, routing and host outcomes "
          "are not certified. Unknown layouts are review-required, never guessed.")


def printable(value):
    return isinstance(value, str) and value.isascii() and all(32 <= ord(c) <= 126 for c in value)


def placeholder(value):
    return isinstance(value, str) and ("<" in value or "${" in value or "{{" in value)


def standard_findings(fields, network):
    """Fields must be observed; no missing interior blanks or defaults are manufactured."""
    failures, review = [], []
    if not isinstance(fields, dict):
        raise ValueError("Observed standard fields must be an object")
    for name in FIELDS:
        if name not in fields:
            review.append("MISSING_FIELD:" + name)
        elif placeholder(fields[name]):
            review.append("UNBOUND_FIELD:" + name)
        elif not printable(fields[name]):
            failures.append("ASCII_FIELD:" + name)
    if set(fields) - set(FIELDS):
        review.append("UNMAPPED_STANDARD_FIELDS")
    usable = {key: value for key, value in fields.items()
              if printable(value) and not placeholder(value)}

    def check(name, matches):
        if name in usable and not matches(usable[name]):
            failures.append("REPRESENTATION:" + name)

    check("SegmentType", lambda v: v == "100")
    check("SegmentLength", lambda v: bool(re.fullmatch(r"[0-9]{3}", v)) and 1 <= int(v) <= 218)
    check("InformationByte", lambda v: v in {"0", "1"})
    check("TerminalIdentifier", lambda v: 13 <= len(v) <= 22)
    check("PromptCode", lambda v: len(v) == 4 and v[0] in TRANSACTIONS and bool(re.fullmatch(r"[0-9]{3}", v[1:])))
    check("AccountNumber", lambda v: 1 <= len(v) <= 425)
    check("CardDiscretionaryBlockData", lambda v: len(v) <= 51)
    check("EncryptedPINBlockData", lambda v: not v or 32 <= len(v) <= 36)
    check("PumpLaneNumber", lambda v: not v or bool(re.fullmatch(r"[0-9]{1,2}", v)))
    for name in ("FuelPurchaseAmount", "NonfuelAmount", "TaxAmount", "CashAmount"):
        maximum = 8 if name == "NonfuelAmount" and network == "AMERICAN_EXPRESS" else 7 if name == "NonfuelAmount" else 8
        check(name, lambda v, maximum=maximum: not v or bool(re.fullmatch(r"[0-9]{1," + str(maximum) + r"}", v)))
    check("SequenceNumber", lambda v: bool(re.fullmatch(r"[0-9]{6}", v)) and int(v) > 0)
    check("ApprovalNumber", lambda v: not v or len(v) == 6)
    check("LocalDateandLocalTime", lambda v: not v or bool(re.fullmatch(r"[0-9]{10}", v)))
    date = usable.get("LocalDateandLocalTime")
    if date and re.fullmatch(r"[0-9]{10}", date):
        try:
            datetime.strptime(date, "%m%d%y%H%M")
        except ValueError:
            failures.append("CALENDAR:LocalDateandLocalTime")
    check("PartialApprovalIndicator", lambda v: v in {"0", "1", "5"})
    if usable.get("PartialApprovalIndicator") == "5":
        review.append("AMEX_PREPAID_ONLY_INDICATOR5_ELIGIBILITY")
    # Encrypted/token account profiles exceed the ordinary non-TransArmor limit.
    if len(usable.get("AccountNumber", "")) > 24:
        review.append("ACCOUNT_PROFILE_REQUIRES_TRANSARMOR_ORACLE")
    return failures, review


def serialize_standard(fields, entry_profile="MANUAL"):
    if not isinstance(fields, dict) or set(fields) != set(FIELDS) - {"SegmentLength"}:
        raise ValueError("Exactly all 16 supplied standard fields other than calculated length required")
    if any(not printable(v) or placeholder(v) for v in fields.values()):
        raise ValueError("Concrete printable ASCII required; no placeholders/control bytes")
    if entry_profile not in {"MANUAL", "TRACK1", "TRACK2"}:
        raise ValueError("Explicit supported account entry profile required")
    values = ["000" if name == "SegmentLength" else fields[name] for name in FIELDS]
    if entry_profile != "MANUAL":
        delimiter = "^" if entry_profile == "TRACK1" else "="
        if delimiter in values[5]:
            raise ValueError("Account field contains its reserved track delimiter")
        values[5:7] = [values[5] + delimiter + values[6]]
    raw = FS.join(value.encode("ascii") for value in values) + FS
    if len(raw) > 218:
        raise ValueError("Standard segment exceeds source layout capacity")
    values[1] = f"{len(raw):03}"
    return FS.join(value.encode("ascii") for value in values) + FS


def parse_application(raw):
    """Parse length-delimited complete segments, not guessed field-delimiter boundaries."""
    if not isinstance(raw, bytes):
        raise ValueError("Application body must be bytes")
    if not raw.isascii() or any(b < 32 and b != 28 for b in raw):
        raise ValueError("Application body must be printable ASCII plus FS")
    header = raw.split(FS, 2)
    if len(header) != 3 or header[0] != b"ATL105":
        raise ValueError("Message format identifier/framing invalid")
    if not re.fullmatch(rb"[0-9]{1,2}", header[1]) or not 1 <= int(header[1]) <= 99:
        raise ValueError("Number of Segments invalid")
    rest, segments = header[2], []
    while rest:
        prefix = rest.split(FS, 2)
        if len(prefix) != 3 or not re.fullmatch(rb"[0-9]{3}", prefix[0]):
            raise ValueError("Segment header invalid")
        kind = prefix[0].decode("ascii")
        digits = 4 if kind in FOUR_DIGIT else 3
        if not re.fullmatch(rb"[0-9]{" + str(digits).encode("ascii") + rb"}", prefix[1]):
            raise ValueError("Segment length width invalid")
        length = int(prefix[1])
        if length < 3 + digits + 2 or length > len(rest):
            raise ValueError("Segment length exceeds available bytes")
        segment, rest = rest[:length], rest[length:]
        if not segment.endswith(FS):
            raise ValueError("Segment boundary is not followed by FS")
        segments.append({"type": kind, "wire": segment})
    if len(segments) != int(header[1]):
        raise ValueError("Declared segment count differs from actual population")
    return segments


def application_findings(raw, network, family="Financial Transaction Request", entry_profile="MANUAL"):
    segments = parse_application(raw)
    failures, review = [], []
    if family not in FINANCIAL:
        raise ValueError("Only declared financial request families are supported")
    if entry_profile not in {"MANUAL", "TRACK1", "TRACK2"}:
        raise ValueError("Explicit supported account entry profile required")
    kinds = [s["type"] for s in segments]
    if kinds[0] != "100" or kinds.count("100") != 1:
        failures.append("STANDARD_PLACEMENT_OR_MULTIPLICITY")
    for segment in segments:
        kind, payload = segment["type"], segment["wire"]
        if kind not in MAXIMUMS:
            review.append("UNMAPPED_SEGMENT:" + kind)
        elif len(payload) > MAXIMUMS[kind]:
            failures.append("SEGMENT_CAPACITY:" + kind)
        if kind == "100":
            values = payload[:-1].split(FS)
            if entry_profile != "MANUAL" and len(values) == len(FIELDS) - 1:
                delimiter = b"^" if entry_profile == "TRACK1" else b"="
                if delimiter not in values[5]:
                    failures.append("TRACK_ACCOUNT_DELIMITER")
                    continue
                account, discretionary = values[5].split(delimiter, 1)
                values[5:6] = [account, discretionary]
            if len(values) != len(FIELDS):
                failures.append("STANDARD_FIELD_POPULATION")
                continue
            fields = dict(zip(FIELDS, (v.decode("ascii") for v in values)))
            bad, unknown = standard_findings(fields, network)
            failures.extend(bad)
            review.extend(unknown)
        else:
            review.append("COMPANION_CONTENT_ORACLE_REQUIRED:" + kind)
    if family == "EMV Financial Transaction Request" and "130" not in kinds:
        failures.append("EMV_REQUEST_SEGMENT_REQUIRED")
    if family == "Financial Transaction Request" and "130" in kinds:
        failures.append("EMV_FAMILY_REQUIRED")
    return {"failures": sorted(set(failures)), "review": sorted(set(review)),
            "segmentTypes": kinds, "readiness": "BLOCKED"}


def fixture_suite():
    fields = dict.fromkeys(set(FIELDS) - {"SegmentLength"}, "")
    fields.update(SegmentType="100", InformationByte="0", TerminalIdentifier="ZZ00000000000",
                  PromptCode="0020", AccountNumber="SYNTHETIC-NOT-A-CARD", CardDiscretionaryBlockData="1249",
                  NonfuelAmount="100", SequenceNumber="123456", PartialApprovalIndicator="1")
    raw = serialize_standard(fields)
    control = b"ATL105" + FS + b"01" + FS + raw
    vectors = [
        ("CONTROL", control, [], []),
        ("COUNT", control.replace(b"01" + FS, b"02" + FS, 1), ["PARSER"], []),
        ("TRUNCATION", control[:-1], ["PARSER"], []),
        ("SEQUENCE", b"ATL105" + FS + b"01" + FS + serialize_standard({**fields, "SequenceNumber": "12345"}),
         ["REPRESENTATION:SequenceNumber"], []),
        ("INDICATOR", b"ATL105" + FS + b"01" + FS + serialize_standard({**fields, "PartialApprovalIndicator": "9"}),
         ["REPRESENTATION:PartialApprovalIndicator"], []),
        ("BLANK_INTERIOR", control.replace(FS + FS, FS, 1), ["PARSER"], []),
        ("EMV_MISSING", control, ["EMV_REQUEST_SEGMENT_REQUIRED"], []),
    ]
    for profile in ("TRACK1", "TRACK2"):
        vectors.append((profile, b"ATL105" + FS + b"01" + FS + serialize_standard(fields, profile), [], []))
    rows = []
    for name, body, expected, review in vectors:
        family = "EMV Financial Transaction Request" if name == "EMV_MISSING" else "Financial Transaction Request"
        profile = name if name in {"TRACK1", "TRACK2"} else "MANUAL"
        parser_reason = None
        try:
            observed = application_findings(body, "VISA", family, profile)
            actual = observed["failures"]
            actual_review = observed["review"]
        except ValueError as exc:
            actual, actual_review = ["PARSER"], []
            parser_reason = str(exc)
        if actual != expected or actual_review != review:
            raise ValueError(f"Application fixture expectation drift: {name}: {actual}, {actual_review}")
        rows.append({"id": "NM-APP-" + name, "wireHex": body.hex(), "family": family, "entryProfile": profile,
                     "expectedFailures": expected, "observedFailures": actual,
                     "expectedReview": review, "observedReview": actual_review,
                     "parserFailureReason": parser_reason,
                     "sourcePages": [167, 223, 224, 225, 364, 365, 406, 409, 440],
                     "scope": "COMPLETE_STANDARD_SEGMENT_AND_SINGLE_SEGMENT_APPLICATION_BODY_SHAPE",
                     "synthetic": True, "executionReady": False, "limits": LIMITS})
    return rows


def observe_json(payload, network):
    if not isinstance(payload, dict) or len(payload) != 1:
        return {"assessment": "REVIEW_REQUIRED", "failures": [], "review": ["UNKNOWN_ENVELOPE"]}
    family, body = next(iter(payload.items()))
    if family in TOTALS:
        return {"assessment": "NOT_APPLICABLE_TOTALS", "failures": [], "review": []}
    if family not in FINANCIAL or not isinstance(body, dict):
        return {"assessment": "REVIEW_REQUIRED", "failures": [], "review": ["UNKNOWN_FAMILY"]}
    segments = list(walk_segments(body))
    failures, review = [], ["SERIALIZED_APPLICATION_BODY_NOT_SUPPLIED"]
    standards = [s for s in segments if s["SegmentType"] == "100"]
    if len(standards) != 1:
        failures.append("STANDARD_MULTIPLICITY_OR_ABSENCE")
    else:
        fields = dict(standards[0])
        prompt = fields.get("PromptCode")
        if isinstance(prompt, dict) and set(prompt) == {"TransactionType", "CardType"} and all(
                isinstance(v, str) for v in prompt.values()):
            fields["PromptCode"] = prompt["TransactionType"] + prompt["CardType"]
        else:
            review.append("UNMAPPED_PROMPT")
            fields.pop("PromptCode", None)
        bad, unknown = standard_findings(fields, network)
        failures.extend(bad)
        review.extend(unknown)
    identifier = body.get("MessageFormatVersionIdentifier")
    if placeholder(identifier):
        review.append("UNBOUND_FORMAT_IDENTIFIER")
    elif identifier != "ATL105":
        failures.append("FORMAT_IDENTIFIER")
    count = body.get("NumberofSegments")
    if placeholder(count):
        review.append("UNBOUND_SEGMENT_COUNT")
    elif not isinstance(count, str) or not re.fullmatch(r"[0-9]{1,2}", count):
        failures.append("SEGMENT_COUNT_REPRESENTATION")
    elif int(count) != len(segments):
        failures.append("SEGMENT_COUNT_POPULATION")
    if family == "EMV Financial Transaction Request" and not any(s["SegmentType"] == "130" for s in segments):
        failures.append("EMV_REQUEST_SEGMENT_REQUIRED")
    return {"assessment": "FAIL" if failures else "REVIEW_REQUIRED",
            "failures": sorted(set(failures)), "review": sorted(set(review))}
