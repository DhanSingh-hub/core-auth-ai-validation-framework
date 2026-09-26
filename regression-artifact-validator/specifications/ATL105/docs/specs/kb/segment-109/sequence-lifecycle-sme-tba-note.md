# Sequence and Block Lifecycle: SME/TBA Learning Note

## Core Idea

Segment 109 requests contain a required six-digit Sequence Number and a required three-digit Block Number. The Electronic Mail Response layout contains both fields. Their presence is source-confirmed; equality, progression, retry, duplicate, and continuation rules are not.

```text
Request Sequence + Block
  -> host response Sequence + Block
  -> next block or terminal outcome
```

## Required SME Decisions

1. Does a response echo the request Sequence Number and Block Number exactly?
2. What identifies first, middle, last, and single-message blocks?
3. When is a new Sequence Number required?
4. How are retries and duplicate blocks identified?
5. What response indicates more retrieval work or a required download?

## TBA Test Pattern

Use an ordered request/response pair, not a metadata flag claiming correlation. Before SME approval, classify lifecycle assertions as `REVIEW_REQUIRED` rather than pass/fail.