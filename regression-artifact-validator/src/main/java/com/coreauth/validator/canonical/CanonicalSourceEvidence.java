package com.coreauth.validator.canonical;

/** Literal source citation bound to an independently hashed source file. */
public final class CanonicalSourceEvidence {
    private String sourcePath;
    private String sha256;
    private String locator;
    private String quote;

    public String getSourcePath() { return sourcePath; }
    public void setSourcePath(String sourcePath) { this.sourcePath = sourcePath; }
    public String getSha256() { return sha256; }
    public void setSha256(String sha256) { this.sha256 = sha256; }
    public String getLocator() { return locator; }
    public void setLocator(String locator) { this.locator = locator; }
    public String getQuote() { return quote; }
    public void setQuote(String quote) { this.quote = quote; }
}