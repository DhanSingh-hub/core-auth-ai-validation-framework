# Review by Business Obligation

Start with one group. Record two separate decisions: **BR alignment** and **chain adequacy**.

1. Open [review-groups.csv](review-groups.csv); choose a STANDARD group.
2. Filter [br-members.csv](br-members.csv) by groupId. Read every distinct claim; compare Test candidates and their source scope.
3. Inspect [representative-cases.csv](representative-cases.csv) for each transaction and test intent. Escalate differences through [exceptions.csv](exceptions.csv).

Decisions: **Aligned / Partial / Conflict / Insufficient Evidence**. Keep chain adequacy separate: **Demonstrated / Partial / Missing / Unresolved**. No decisions have been granted by grouping.

## First Review: Sequence Number

| AI BR | Obligation | Evidence Needed |
|---|---|---|
| :3251 | Presence | Present and omitted field, with justified expected outcomes |
| :6098 | Six-digit numeric format | Valid, short, long and nonnumeric values; lifetime claim separate |
| :1937 | Lifecycle persistence | Linked authorization and follow-up steps, same sequence number |

Do not combine these three into a single match. The grouping is proposed, not an approval. Different claim variants, conditions and outcomes need separate review; passing a sample never approves every member. AI scenario approval is not a prerequisite.
