package com.coreauth.validator.traceable;

/** The business scenario a test case is exercising (e.g. a transaction/card-type combination). */
public final class TestScenario {
    private String id;
    private String name;
    private String requirementId;
    private String transactionType;
    private String cardType;
    private String classification; // "positive" | "negative"

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRequirementId() { return requirementId; }
    public void setRequirementId(String requirementId) { this.requirementId = requirementId; }
    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }
    public String getClassification() { return classification; }
    public void setClassification(String classification) { this.classification = classification; }
}
