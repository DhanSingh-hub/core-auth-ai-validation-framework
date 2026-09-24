# Traceability Matrix: REQ-SRC-ATL105-PDF-001:1072 vs BR-SEG100-ID-001

Independent chains for each side; artifacts are only listed if reachable through this side's own BR->TS->TC->TD links.

## AI (Run2) - REQ-SRC-ATL105-PDF-001:1072

### Business Requirements (1)

| ID | title |
|---|---|
|REQ-SRC-ATL105-PDF-001:1072|Key Data section repeats up to 3 times (max 48 bytes total); presence based on Key Indicators field.|

### Test Scenarios (1)

| ID | requirementIds |
|---|---|
|SC-7229|REQ-SRC-ATL105-PDF-001:1072|

### Test Cases (0)

_None._

### Test Data (0)

_None._

## Test Solution - BR-SEG100-ID-001

### Business Requirements (1)

| ID | title |
|---|---|
|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|

### Test Scenarios (1)

| ID | requirementIds |
|---|---|
|SCN-SEG100-ID-001|BR-SEG100-ID-001|

### Test Cases (2)

| ID | expectedOutcome |
|---|---|
|TC-SEG100-ID-001|PASS|
|TC-SEG100-ID-002|FAIL|

### Test Data (2)

| ID | expectedValidation |
|---|---|
|TD-SEG100-ID-001|PASS|
|TD-SEG100-ID-002|FAIL|

