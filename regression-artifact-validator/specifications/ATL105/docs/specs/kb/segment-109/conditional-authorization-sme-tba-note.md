# Conditional Authorization Fields: SME/TBA Learning Note

## Fields

Employee Number (Element 32) and Password (Element 65) are conditional Segment 109 fields. The specification identifies their shape and describes Password as the end-of-day function password, but does not specify the business condition that requires either field in the extracted Segment 109 section.

## Security Rule

Never place a live password, device credential, employee number, PAN, token, or production mail content in a training artifact. Fixtures use synthetic values such as `EMP001` only when the final approved format permits them; otherwise they omit the fields.

## SME/TBA Questions

1. Which Prompt Code or device state requires Employee Number?
2. Which condition requires Password?
3. Is Password allowed for every operation or only an end-of-day workflow?
4. Are the fields prohibited in any operation?
5. What masking/placeholder form is converter-safe?

## Test Boundary

Until `SEG109-SME-006` is answered, tests can validate supplied values' source-stated type and length, but cannot claim that their presence or absence is business-correct.