# ATL105 Location-Scoped Requirement Inventory

Source: `specifications/ATL105/docs/specs/extracted_text.txt` (`6d03bf09f25b9126975b584aef83c21d1b7b081cabccfb9af3b89d46a26d06aa`), ATL105 2026-3.
Supplements the frozen 601-rule segment-catalog baseline; it does not replace it.

| Requirement type | Rules |
|---|---:|
| MESSAGE_LOCATION | 131 |
| ELEMENT_DEFINITION | 232 |

Total supplemental rules: 363.

## Message Layouts

| Section | Message | Data sections | Segment-free element fields | Segment fields |
|---|---|---|---|---|
| 11.1.1 | Financial Transaction Request | 1, 2, 3 | F1=E55, F2=E63 | F3=100 |
| 11.1.2 | Financial Transaction Response | 1, 2 | F1=E83, F2=E30, F3=E45, F4=E46, F5=E86, F6=E6, F7=E5, F8=E2, F9=E26, F10=E7, F11=E8, F12=E119, F13=E115, F14=E61, F15=E101 | - |
| 11.2.1 | Loyalty Card Transaction Request | 1, 2, 3 | F1=E55, F2=E63 | F3=100, F4=108, F5=114 |
| 11.3.1 | ECA/ TeleCheck® Service Transaction Request | 1, 2, 3 | F1=E55, F2=E63 | F3=100, F4=110, F5=111, F6=113 |
| 11.4.1.1 | Totals Request | 1, 3 | F1=E55, F2=E63 | F3=105 |
| 11.4.1.2 | Totals with Proprietary Data Load Request | 1, 3 | F1=E55, F2=E63 | F3=119 |
| 11.4.2.1 | Approved Totals Response | NONE | F1=E83, F2=E105, F3=E86, F4=E42, F5=E38, F6=E56, F7=E13, F8=E16, F9=E15 | - |
| 11.4.2.2 | Declined Totals Response | NONE | F1=E83, F2=E105, F3=E86, F4=E101 | - |
| 11.5.1 | Electronic Mail Request | 1, 2 | F1=E55, F2=E63 | F3=109 |
| 11.5.2 | Electronic Mail Response | NONE | F1=E83, F2=E30, F3=E45, F4=E46, F5=E86, F6=E11, F7=E52, F8=E51 | - |
| 11.6.1 | Communications Test Request | NONE | F1=E55, F2=E120 | - |
| 11.6.2 | Communications Test Response | NONE | F1=E83, F2=E30, F3=E100 | - |
| 11.7.1.1 | Table Load Request | NONE | F1=E44, F2=E102, F3=E48, F4=E43, F5=E96, F6=E39 | - |
| 11.7.1.2 | Table Load Response | NONE | F1=E97, F3=E97, F5=E97, F7=E35, F9=E35 | F2=DL1, F4=DL2, F6=DL3, F8=DL6 |
| 11.7.2.1 | Phone Load Request | NONE | F1=E44, F2=E102, F3=E48 | - |
| 11.7.2.2 | Phone Load Response | NONE | F1=E97 | F2=DL2 |
| 11.7.3.1 | Date and Time Load Request | NONE | F1=E44, F2=E102, F3=E48 | - |
| 11.7.3.2 | Date and Time Load Response | NONE | F1=E24, F2=E25, F3=E21, F4=E22, F5=E23, F6=E65 | - |
| 11.7.4.1 | Software Load Request | NONE | F1=E44, F2=E102, F3=E48, F4=E43, F5=E96, F6=E39 | - |
| 11.7.4.2 | Software Load Response | NONE | F1=E97 | F2=DL4, F3=DL5 |
| 11.7.6.1 | Proprietary Data Load Request | 1, 3 | F1=E55, F2=E63 | F3=118 |
| 11.7.7 | Proprietary Data Load Response | NONE | F1=E83, F2=E30, F3=E45, F4=E46, F5=E86 | F6=118 |
| 11.7.8.1 | Moneris Key Load Request | NONE | F1=E44, F2=E102, F3=E48, F4=E205, F5=E206, F6=E207, F7=E208 | - |
| 11.7.8.2 | Moneris Key Load Response | NONE | F1=E24, F2=E206, F3=E207, F4=E209, F5=E210, F6=E211, F7=E212 | - |
| 11.8.1 | EMV Financial Transaction Request | 1, 2 | F1=E55, F2=E63 | F3=100 |
| 11.8.2 | EMV Financial Transaction Response | 1, 2 | F1=E83, F2=E30, F3=E45, F4=E46, F5=E86, F6=E6, F7=E5, F8=E2, F9=E26, F10=E7, F11=E8, F12=E119, F13=E115, F14=E61, F15=E101 | - |
| 11.9.1 | CA Public Key File Load Request | 1 | F1=E55, F2=E63 | - |
| 11.9.2 | CA Public Key File Load Response | NONE | F1=E24, F2=E86, F3=E83, F4=E187, F5=E193, F6=E194, F7=E11 | - |

## Referenced And Out-Of-Scope Sections

- 11.2.2: Loyalty Card Transaction Response uses the 11.1.2 layout.
- 11.3.2: ECA/TeleCheck Service Financial Transaction Response uses the 11.1.2 layout.
- 11.7.5: TransArmor Key and Key ID Load layout is defined in the separate TransArmor specification-updates document, not in the pinned ATL105 text.

## Review Required

- Duplicate element numbers in the dictionary: 118.
- Rules whose attributes need manual confirmation: 1.
- All rules are source-derived candidates; semantic approval and execution remain separate gates.
