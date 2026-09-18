# Segment 105 Totals Aggregation: SME and TBA Learning Note

The layout requires Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount. These are totals-domain fields, not ordinary financial-request amounts.

The source evidence currently establishes field presence but does not establish whether values originate in the request, are calculated by the host, repeat by card label, or must reconcile mathematically. Do not infer a formula such as $GrandTotal = sum(CardTypeTotalAmount)$ until the SME supplies the aggregation and response rules.

Required SME input: ownership of each value, scale/format, repeatability, reconciliation formula, handling of no-activity days, and expected discrepancy behavior.