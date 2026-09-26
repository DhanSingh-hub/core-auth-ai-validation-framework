# Segment 110 Serialization and Wire-Format Behavior

## Representations

```text
Check transaction intent
  -> structured request JSON
  -> Section 11.3.1 serialized ECA/TeleCheck request
  -> transport payload
  -> Financial Transaction Response layout (reused, Section 11.3.2)
```

Logical JSON is not a wire-format guarantee. The converter or serializer must apply the specification order, Field Separators, and computed lengths.

## Request Rules

1. Data Section 1 contains Elements 55 and 63, with a separator between them and after Element 63.
2. Data Section 2 contains Segment 100.
3. Data Section 3 contains Segment 110 at Field 4, Segment 111 at Field 5, and Segment 113 at Field 6 (ECA/TeleCheck® context).
4. Segment 110 fields are serialized in the Section 12.9 order (12 fields).
5. Empty fields retain Field Separators; a later populated field must not shift left.
6. Segment Type is `110`.
7. Segment Length is derived from serialized content and includes Segment Type and Field Separators.
8. Segment 110 has a maximum serialized length of 168 characters.
9. Conditional fields (Driver's License, State Code, Date of Birth, Check Number) and the Alternate MICR IND field must agree with the manually-entered trigger and the element-239 identity once those are approved.

## Response Rules

The specification does not define a Segment 110-specific response layout. Section 11.3.2 states the ECA/TeleCheck® Service Financial Transaction Response contains the same information in the same layout as the general Financial Transaction Response (Section 11.1.2). Do not invent check-specific response fields; validate against the generic Financial Transaction Response oracle instead.

## Mutations to Prepare

- Segment Type not `110`.
- Segment Length incorrect or exceeds `168`.
- MICR Data missing or exceeding 50 bytes.
- Driver's License, State Code, or Check Number present/absent inconsistently with the (unapproved) manually-entered trigger.
- State Code not in the Appendix D catalog.
- Check Type not `P` or `C`.
- Missing separator for an empty middle field.
- Alternate MICR IND populated with a value other than `Y`.
- Segment 110 present in a request envelope other than the confirmed ECA/TeleCheck® Service Transaction Request.
