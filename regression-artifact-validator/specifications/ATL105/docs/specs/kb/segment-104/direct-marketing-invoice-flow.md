# Direct Marketing Invoice Number Flow

```mermaid
flowchart TD
    A[Element 29] --> B{Populated?}
    B -->|No| C[Pass conditional-field check]
    B -->|Yes| D{1-10 alphanumeric characters?}
    D -->|Yes| E[Pass SEG104-R-014]
    D -->|No| F[Reject SEG104-R-014]
```
