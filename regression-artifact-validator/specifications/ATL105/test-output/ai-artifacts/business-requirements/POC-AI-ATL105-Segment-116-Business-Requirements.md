# POC AI Segment 116 Business Requirements

Source: `test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json`

Scope: requirements whose `segment_number` equals `116` or whose `related_entity_ids` include `ENT-SEG-116` in the approved requirement catalog (DIRECT_SEGMENT_116).

Count: 15

Note: Segment 116's own ATL105 sections (12.15, 11.7.5) are stubs referring to an external TransArmor document (see the [Segment 116 KB README](../../../docs/specs/kb/segment-116/README.md)). The AI requirements below were extracted from the supplied requirement catalog and may reference elements (155-157, sub-tables) that belong to the TransArmor Load Response or to a Segment 111 companion table rather than to Segment 116's own request fields; each entry's `category`/`source_rule_id` should be read alongside the [BR Coverage Report](../coverage-reports/segment-116/POC-AI-Segment-116-BR-Coverage-Report.md) before being treated as Segment 116 request-field evidence.

| ID | Source rule | Page | Category | Requirement type | Confidence | Requirement |
|---|---|---:|---|---|---:|---|
| `REQ-SRC-ATL105-PDF-001:607` | `BR-103-7` | 103 | Field | Business Rule | 70% | TransArmor-Verifone Edition processing is exempt from full card-read data storage restriction. |
| `REQ-SRC-ATL105-PDF-001:1737` | `BR-385-1` | 385 | Security | Business Rule | 80% | TransArmor PKI Encryption and Tokenization Load Request messages must be followed by Number of Segments element and TransArmor Load Data Segment. |
| `REQ-SRC-ATL105-PDF-001:1760` | `BR-388-7` | 388 | Security | Business Rule | 90% | TransArmor PKI Encryption and Tokenization: Used for Key and Key ID Load, followed by Segment 116, TransArmor Load Data Segment. |
| `REQ-SRC-ATL105-PDF-001:1838` | `BR-400-15` | 400 | Message | Business Rule | 57% | TransArmor Load Data Segment length is 01-50. |
| `REQ-SRC-ATL105-PDF-001:1848` | `BR-401-1` | 401 | Security | Business Rule | 90% | Segment Type is required for the TransArmor PKI Encryption and Tokenization Load Request. |
| `REQ-SRC-ATL105-PDF-001:1854` | `BR-402-5` | 402 | Security | Business Rule | 80% | For TransArmor PKI Encryption and Tokenization, Sequence Number is included in Data Segment No. 116 (TransArmor Load Data Segment). |
| `REQ-SRC-ATL105-PDF-001:2082` | `BR-453-1` | 453 | Security | Business Rule | 90% | Key Data is required in the TransArmor Load Response. |
| `REQ-SRC-ATL105-PDF-001:3904` | `REL-ENT-ELEM-86-ENT-SEG-116` | 402 | Dependency | Cross-Segment Rule | 80% | ENT-ELEM-86 depends on ENT-SEG-116; the documented dependency must hold. |
| `REQ-SRC-ATL105-PDF-001:4167` | `ENT-SEG-116` | 176 | Dependency | Dependency | 34% | The Totals with Proprietary Data Load Request transaction includes TransArmor Load Data Segment (Data Segment No. 116). |
| `REQ-SRC-ATL105-PDF-001:5788` | `ENT-ELEM-155` | 452 | Field | Field Validation | 79% | Key ID: Identifies Key ID associated with encryption key in TransArmor Load Response. |
| `REQ-SRC-ATL105-PDF-001:5789` | `ENT-ELEM-156` | 452 | Field | Field Validation | 79% | Key Data Length: Identifies length of Key Data field in TransArmor Load Response. |
| `REQ-SRC-ATL105-PDF-001:5790` | `ENT-ELEM-157` | 453 | Field | Field Validation | 84% | Key Data: Identifies new Encryption Key and Key ID in TransArmor Load Response or Error Message. |
| `REQ-SRC-ATL105-PDF-001:6050` | `ENT-ELEM-ADDL-TRANSARMOR-DATA` | 595 | Field | Field Validation | 47% | Additional TransArmor Data: TLV field with sub-tables for TransArmor implementations, max 100 digits. |
| `REQ-SRC-ATL105-PDF-001:6051` | `ENT-ELEM-SUBTABLE-KSN` | 595 | Field | Field Validation | 47% | Sub-Table Data (KSN): KSN information sub-table, variable length up to 40 bytes, under Sub-Table ID 01. |
| `REQ-SRC-ATL105-PDF-001:6052` | `ENT-ELEM-SUBTABLE-DEVICETYPE` | 595 | Field | Field Validation | 47% | Sub-Table Data (Device Type): Device type information sub-table, variable length up to 8 bytes, under Sub-Table ID 02. |
