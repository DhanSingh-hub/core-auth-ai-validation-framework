# Direct Marketing Invoice Number: SME and TBA Learning Note

Element 29 is the Direct Marketing Invoice Number. Section 12.5 permits a conditional, variable-length alphanumeric value of up to ten characters.

This field identifies an invoice only when the flow uses direct marketing or address verification; its presence does not itself prove that such a flow is valid. Validate the field envelope independently, then validate the broader transaction context from the applicable message specification.

| Value state | Expected result |
|---|---|
| Empty | Pass; conditional |
| 1–10 alphanumeric characters | Pass |
| More than 10 characters | Fail |
| Punctuation or embedded spaces | Fail under the current ATL105 envelope rule |
