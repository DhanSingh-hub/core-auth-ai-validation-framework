# Segment 104 Companion-Segment Compatibility: SME and TBA Learning Note

Segment 104 is a Data Section 3 companion; it never replaces Segment 100. When purchase-card data is required, the base request must contain Segment 100 and one Segment 104.

| Condition | Required set | Disposition |
|---|---|---|
| Standard transaction with no purchase-card condition | Segment 100 only | Segment 104 not required |
| Purchase-card data required | Segment 100 + Segment 104 | Missing 104 is an error |
| Purchase-card + EMV | Segment 100 + 104 + 130 | Validate all conditions |
| Purchase-card + other Section 3 condition | Segment 100 + 104 + applicable companions | Validate final set and count |
| Unknown combination | Source review required | `REVIEW_REQUIRED` |

Element 63 must equal the final serialized segment count. Segment 104 cardinality is provisional P-02: the current validator enforces at most one Segment 104 based on the Data Section 3 layout, pending SME confirmation.
