# Segment 104 Direct Marketing Invoice: Preliminary Review

**Status:** Source identifies the field’s use, but the exact condition is unresolved.

Element 29 is marked Conditional in Segment 104 and is described for Direct Marketing/AVS Requests. The source excerpt does not identify an independent trigger. The independent `SEG104-R-014` chain covers format when populated; its condition/applicability scenario remains a placeholder. See [the review record](segment-104-direct-marketing-invoice-review-v1.json), [the symbolic condition draft](segment-104-direct-marketing-invoice-test-chain-package-v1.json), and [the provenance manifest](manifest-v1.json).

All three linked AI cases declare Element 29 and Purchase Code omitted, so none establishes the Direct Marketing/AVS context or tests a populated invoice number. All draft records remain `REVIEW_REQUIRED`; no condition or expected outcome is inferred.
