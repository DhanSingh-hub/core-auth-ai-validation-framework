# Segment DL3 Date and Time Data Segment: SME and TBA Learning Note

Verified against Section 12.44, Elements 21, 22, 23, 24, 25, 34, 65.

## What Segment DL3 Means

Carries the current BUYPASS day-of-week, date, time, merchant end-of-day settlement (Cut Time), and the end-of-day function's password. Self-delimited by Data Type Indicator `:` and End-of-Data Indicator `~`.

## Field Layout

Data Type Indicator(24, fixed `:`), Day of the Week(25,1), Current Date(21,6), Current Time(22,4), Cut Time(23,4), Password(65,6, **Device-sourced**), End-of-Data Indicator(34, fixed `~`).

## Notable Finding

Password is the sole Device-sourced field in an otherwise entirely Host-sourced segment — distinctive because most segments are uniformly sourced.

## Source References

Section 12.44: lines 17302-17364. [Rule Catalog](coverage/segment-DL3-rule-catalog.json).
