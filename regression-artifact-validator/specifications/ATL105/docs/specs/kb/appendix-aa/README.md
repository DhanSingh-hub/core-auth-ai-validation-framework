# Appendix AA - TransArmor Processing Considerations

## Sources and implementation

Training covers ATL105 2026-3 Appendix AA-1 through AA-6, Element 12,
Element 102 and the related Appendix I Table 052 definition.

- [Independent oracle](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixAATransArmorOracle.java)
- [Tests](../../../../../../src/test/java/com/coreauth/validator/AppendixAATransArmorOracleTest.java)
- [Coverage and executable synthetic fixtures](../../../../test-output/test-json/appendices/appendix-aa-segment-100-coverage.json)

There are 10 source-anchored requirements, 4 scenarios, 4 test-case records and
25 persisted executable fixtures. Tests also exercise every EDATA identifier,
all encryption methods and exact length boundaries. The status is
**PARTIALLY_COVERED**: logical structure is not cryptographic or wire certification.

## Source rules

TransArmor is First Data's encryption/tokenization processing option.
Initialization calls for both Key Load (obtaining Key and Key ID) and RegiStart
(device registration). The full protocols are in the external **ATL105
Specifications Update for TransArmor Processing** document; the oracle does not
invent them.

Element 2 is replaced by `TAP` + sequence(6) + EDATA identifier(1) +
Key ID(11) + Token Type(4) + EDATA. Sequence must match Element 86.
Token Type is an assigned four-character alphanumeric Single use/MultiPay value.
The encrypted data maximum is 400 bytes. ASCII fixtures permit exact byte counts;
non-ASCII input is review-gated pending explicit wire encoding. TA-VE processor
prefix and manual FS/expiration suffix are not counted as encrypted data.

| Identifier | Stage / meaning | Key ID and EDATA |
|---|---|---|
| 1 | Initial encrypted Track1 | PKI loaded Key ID; TDES/OnGuard/AES zero header Key ID |
| 2 | Initial encrypted Track2 | Same Key ID rules |
| 3 | Initial encrypted manual PAN | Same Key ID rules; actual FS followed by unencrypted MMYY |
| 4 | Initial Token Only | Unencrypted Track1/Track2/manual PAN; detailed track layout is a separate profile |
| 6 | Initial TA-VE; BUYPASS terminal identifies PIN pad | Merchant Domain(5);Merchant Brand(5), processor-code:encrypted-data |
| 7 | Initial TA-VE; pump/lane identifies PIN pad | Same format; Element 79 routing evidence required |
| M | Initial multi-controller TA-VE | Same format; Segment 111 Table 018 Card Acceptor Terminal ID required |
| 0 | Subsequent token transaction | Initial response/on-file token; Key ID interpretation conflicts with examples |

Available encryption types are RSA/PKI, TA-VE Cipher Hidden Encryption,
TDES/DUKPT, Ingenico OnGuard FPE and AES DUKPT.
TA-VE processor codes are boarded alphanumeric values of up to 16 characters
followed by a colon. A manually keyed TA-VE PAN also needs the FS/MMYY suffix.

Initial PKI preprocessing prepends `00` plus the first six bytes of the BUYPASS
Merchant Number before encrypting the Account Number. Element 102 begins with
Device Type(2) and State Code(2), followed by the assigned Merchant Number.
Tests use only synthetic pre-encryption evidence; no real PAN/plaintext is logged.

The initial response Account Number supplies the token for subsequent requests.
The source also permits tokens already stored on file. Both evidence paths are
represented; conflicting original-response/on-file tokens require review.
Expiration must be stored and sent unencrypted through Element 12 in the
application-specific layout. The oracle compares supplied token and expiration
evidence; it does not prove durable storage or parse every Element 12 layout.

- Token Only retrieval failure returns Element 26 Decline Code `4M`. A successful
  retrieval does not imply transaction approval.
- Element 30 value `8` signals an expiring/expired Key ID and a required Key/Key ID update.
- Element 48 `K` denotes Key/Key ID Load.
- Element 83 `K` denotes an approved load; `L` a rejected load because the merchant
  is not TransArmor-enabled or the signing key is invalid/outdated.
- PKI Encryption/Tokenization Load uses Device Type `++` in Element 102,
  regardless of the terminal's actual type.
- Segment 111 Table 052 supports Additional TransArmor Data. Appendix I describes
  a maximum 100-byte table, tag 01 KSN (up to 40 bytes) required for TDES,
  OnGuard and AES; tag 02 Device Type (up to 8 bytes) required for AES and optional
  for TDES/OnGuard. Longer Key ID/special device-type variants are not inferred
  from an undocumented sub-table mapping.

## Examples and review boundaries

AA-4 illustrates PKI authorization, response, completion and response.
AA-5/AA-6 illustrate TA-VE authorization, response, cancellation and response.
Periods in these examples represent field separators. The encrypted blocks are
explicitly illustrative and need not translate to valid data: none is treated
as a trusted cryptographic fixture.

The re-training includes synthetic PKI completion-shaped and TA-VE
cancellation-shaped token fields, not full parsed lifecycle messages. It also
checks absent ciphertext, contradictory track/manual context, both missing
lane/controller routing identities and the terminal-evidence gate for identifier
6. Missing external evidence must not hide a definite malformed-field failure.
Cryptographic/assignment/storage validity is emitted as a dedicated
`EXTERNAL-VALIDITY` review assessment, even when structural checks pass.

The EDATA matrix says subsequent Key IDs are zero-filled, with or without a
semicolon as in the initial transaction. The lifecycle examples retain nonzero
initial Key IDs. This source conflict is REVIEW_REQUIRED; neither interpretation
is silently imposed.

Remaining work requires external or separate contracts:

- Full Key Load/RegiStart protocol from the referenced update document.
- Subsequent Key ID conflict resolution.
- Cryptography, KSN/key validity, assignment of Token Types, merchant domains,
  brands, processor codes and BUYPASS terminal identities.
- Actual token retrieval, token association, durable expiration storage and
  terminal key-refresh behavior.
- Full ATL105 wire envelopes, request/response lifecycle parsing, Token Only
  entry-mode layouts and ambiguous Table 052 TLV/long-Key-ID representations.

Each fixture shallow-merges its payload over `fixtureDefaults` and targets one
assessment. Executable REVIEW_REQUIRED fixtures test guardrails, not unresolved
behavior. Non-applicable PASS assessments state that they are not applicable;
they must not be counted as network approval or external certification.

## Validation

Run from the Maven module:

```powershell
mvn "-Dtest=AppendixAATransArmorOracleTest,AppendixITableCatalogTest,ProducerNeutralContractTest,RepositoryStructureTest" test
```
