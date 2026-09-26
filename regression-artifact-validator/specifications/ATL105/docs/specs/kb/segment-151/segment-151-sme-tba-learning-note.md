# Segment 151 Incomm OTC Market Basket Data (Request) Segment: SME and TBA Learning Note

Verified against Section 12.36.

Can appear in ANY field slot of Data Section 3. Market Basket Data = 1 DV dataset + up to 10 PI datasets, full format in an external "Buypass Incomm Market Basket Data format" document (`[PROVISIONAL SEG151-SME-003]`, not available in this KB pass). Two open inconsistencies: max length 2,309 vs valid-range 3,334 (`SEG151-SME-001`); origin Device (narrative) vs Host (field table Source) (`SEG151-SME-002`).

Source: Section 12.36, lines 16177-16243. [Rule Catalog](coverage/segment-151-rule-catalog.json).
