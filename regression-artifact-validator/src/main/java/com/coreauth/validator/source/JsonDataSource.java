package com.coreauth.validator.source;

import java.util.List;

/**
 * Abstraction over where BUYPASS ATL105 JSON test data comes from.
 * Phase 1: local files ({@link LocalFileJsonSource}).
 * Later phases: database or cloud storage - implement this interface, the validator
 * and rule engine do not need to change.
 */
public interface JsonDataSource {
    List<JsonDocument> loadAll();
}
