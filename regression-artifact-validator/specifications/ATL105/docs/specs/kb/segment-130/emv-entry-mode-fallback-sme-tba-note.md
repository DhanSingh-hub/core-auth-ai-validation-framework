# EMV Entry Mode and Fallback: SME/TBA Learning Note

## Core Idea

Whether Segment 130 belongs in a message at all is decided by **entry mode**, not by card type. A chip card does not imply an EMV transaction.

```text
Card presented
  -> device capability + card capability + read outcome
  -> entry mode selected
  -> EMV transaction OR fallback OR plain MSR
  -> Segment 130 present OR absent
```

This is the Segment 130 analogue of Segment 100's Prompt Code decision: the wrong branch produces a structurally valid message that is business-invalid.

## The Fallback Definition

Section 10.14.4 defines fallback as *"the acceptance of chip cards via magnetic-stripe processing at a chip-capable device."*

The specification gives an explicit two-row decision table:

| If | Then | Status |
| --- | --- | --- |
| Device is EMV enabled **and** card is chip **and** card is swiped | Send Fallback Entry Mode `80` (or spec equivalent) | **EMV Fallback transaction** |
| Card is **not** chip **or** device is **not** chip capable, **and** card is swiped | Send MSR Entry Mode `90` (or spec equivalent) | **Regular MSR swipe transaction** |

Critically: *"a Fallback will occur whenever an EMV Certified solution cannot read or obtain a viable matching AID."*

### Consequence For Segment 130

A fallback transaction is **not** an EMV Financial Transaction Request. It carries magnetic-stripe data and therefore **no Segment 130**. Emitting Segment 130 with entry mode `80` is a contradiction.

Equally, using entry mode `90` for a chip card at a chip-capable device is a **liability-shift defect** — the merchant loses fallback protection. The specification notes fallback transactions "continue to protect the merchant from the liability shift when performed correctly."

## Entry Mode Decision Matrix

| Device chip-capable | Card has chip | Read outcome | Entry mode | Segment 130 |
| --- | --- | --- | --- | --- |
| Yes | Yes | Chip read OK, AID matched | contact/contactless chip | **Present (Required)** |
| Yes | Yes | Chip unreadable / no viable AID | `80` fallback | **Absent** |
| Yes | No | Swipe | `90` MSR | Absent |
| No | Yes | Swipe | `90` MSR | Absent |
| No | No | Swipe | `90` MSR | Absent |

## Reversals Are A Separate Exemption

Section 10.14.2.4 states: **"EMV data is not required on Reversal transactions."**

So Segment 130 absence has two distinct legitimate causes, and they must not be conflated:

```text
Absent because  -> entry mode is fallback/MSR (not an EMV transaction at all)
Absent because  -> transaction is a Reversal/TOR (EMV transaction, data waived)
```

A validator that reports a single "Segment 130 missing" error for both cases gives the analyst no useful signal.

## EMV PIN Interaction (Section 10.14.3)

PIN handling changes the surrounding message even though it is not carried in Segment 130 itself.

| Mode | Where PIN is verified | Field behaviour |
| --- | --- | --- |
| **Online PIN** | Sent to and validated by the issuer | PIN block populated normally |
| **Offline PIN** | Matched against the application on the chip | PIN field carries **all `F`s** |

Specification note: in a traditional PIN Debit transaction the PIN field is mandatory — *either a PIN or all `F`s (for offline PIN) must be sent or the transaction declines as "invalid transaction."* In a credit or signature-debit transaction, all `F`s is **not** required because PIN is optional there.

MasterCard mandates that EMV-PIN-capable devices support **both** online and offline PIN. Visa supports the functionality but does not mandate online PIN.

## SME Reasoning

Ask:

1. Is the device EMV certified, and was a viable AID obtained?
2. If the chip read failed, is entry mode `80` (not `90`) being sent?
3. Is Segment 130 correctly **absent** on fallback?
4. Is this a reversal, where EMV data is waived regardless of entry mode?
5. Is the PIN mode online or offline, and does the PIN field reflect it?
6. For PIN debit specifically, is the field populated with a PIN or all `F`s — never empty?
7. Does the card brand fall within the Section 10.14.2.3 supported list?

## Open Items

- The specification says entry mode `80`/`90` "or spec equivalent" without enumerating ATL105's own codes in Section 10.14.4; Appendix J is the entry-mode authority. Confirm the exact ATL105 code mapping with the SME.
- Contactless EMV entry-mode codes are not enumerated in Section 10.14.4 and need confirmation.

## Review Checklist

- Is entry mode consistent with device capability and card capability?
- Is Segment 130 present **only** for genuine chip-read EMV transactions?
- Is fallback using `80` rather than `90`?
- Is a missing Segment 130 attributed to the correct cause (fallback vs reversal)?
- Is the PIN field populated per online/offline mode?
- For PIN debit, is the field never left empty?
