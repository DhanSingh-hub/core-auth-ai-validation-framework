# Segment 100 Final Closure: Serialization and Lifecycle Learning Note

## Non-Trailing Empty Fields

An empty field in the middle of Segment 100 is still a field position. Its separator preserves positional meaning for the receiver.

```text
field A | empty field B | field C
         ^ separator remains
```

Removing the separator shifts subsequent values into the wrong positions.

## Trailing Optional Fields

Unneeded optional fields at the end may be omitted. This is different from removing an empty middle field.

```text
Required A | Required B | Optional C | Optional D
                         ^ if not needed, the suffix may stop before C
```

The validator should accept only omission of a trailing suffix. It must reject reordered fields or omission of a field followed by later fields.

## Lifecycle Message Correlation

Lifecycle validation requires actual related messages:

```text
Original authorization
  -> Completion, reversal, void, or timeout reversal
```

The follow-up must preserve the original identity and required approval context. A test control claiming “correlated” is not enough; both original and follow-up data must be present.

## SME/TBA Review Questions

- Is the empty field genuinely non-trailing in the wire order?
- Does the receiver need the separator to preserve position?
- Which optional fields may legally be omitted at the end?
- What original message does the follow-up reference?
- Must Sequence Number, Approval Number, account, amount, or Prompt Code remain consistent?
- Does the payload prove the relationship, or merely describe it?

## Certification Meaning

These rules close the distinction between structural correctness and business correctness:

- Serialization rules prove the message can be parsed correctly.
- Lifecycle rules prove the message belongs to the intended transaction flow.
