# Full-Corpus Review Batch 013: Encrypted PIN Block Data

**Status:** Preliminary analyst review; authorized decision pending. No semantic match or coverage credit is granted.

ATL105 Element 33 is AN, with a maximum of 36 bytes. For DUKPT PIN encryption, the representation is a 16–20-byte Key Serial Number plus a 16-byte PIN block. The element is required for debit and EBT, not required for a completion; customer-keyed data must use DUKPT, and an omitted request field is represented by a Field Separator. EMV offline-PIN debit has a separate 16-byte PIN-block rule. Hardware PIN-encryption devices validate composition. The AI’s alphanumeric/maximum-36 claim is source-aligned but omits these composition, applicability and validation requirements.

The roster links seven cases to Segment 100; five are marked physical-complete in the frozen roster. Fourteen candidate payload/sidecar copies across two trees are byte-identical. None of the seven payloads contains Element 33, and both sidecars for each case record it as “omitted by composer.” These linked cases therefore provide no PIN-block format observations or cryptographic outcomes. The four registered approved-path references are unavailable in the frozen source and remain recorded as gaps; no paths were substituted from another intake.

`SEG100-R-043` is the direct DUKPT Test candidate, but its BR/TS/TC/TD chain and expected validation remain review-required. `BR-SEG100-GAP-PIN` is also a related candidate. In-app token cryptogram and cryptogram-block-split rules are distinct data constructs, not Element 33 counterparts. No test execution or hardware validation was performed. PIN-block values are not included in this report; JSON character/byte length would not establish wire encoding or cryptographic validity.
