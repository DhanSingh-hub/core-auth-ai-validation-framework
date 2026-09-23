# Shipping Data: SME and TBA Learning Note

Segment 104 contains conditional shipping fields:

| Element | Field | Rule |
|---|---|---|
| 89 | Ship-to Country Code | Exactly three numeric digits when populated |
| 90 | Ship-to Postal Code | AN(10); source conflict recorded as P-01 |
| 88 | Ship-from Postal Code | AN(10); source conflict recorded as P-01 |

## P-01: Postal Code Source Conflict

The Element 88/90 representation says fixed `nnnnn-nnnn`, while its valid-values text says "Any valid postal code." The current validator therefore accepts either US ZIP+4 or a general 1–10 alphanumeric postal value. A value outside both shapes becomes a warning and `REVIEW_REQUIRED`, not an automatic hard failure.

An SME must decide whether the deployment is US-only (strict ZIP+4) or supports international postal data. The validator must be tightened only after that business decision is recorded.
