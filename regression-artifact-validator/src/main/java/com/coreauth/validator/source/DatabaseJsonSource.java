package com.coreauth.validator.source;

import java.util.List;

/**
 * Placeholder for phase 2+: load JSON test documents from a database table/collection.
 * Wire up a JDBC/JPA/Mongo client here; the rest of the framework (rule engine, validator,
 * tests) is agnostic to where documents come from.
 */
public final class DatabaseJsonSource implements JsonDataSource {

    public DatabaseJsonSource(/* connection/config params */) {
    }

    @Override
    public List<JsonDocument> loadAll() {
        throw new UnsupportedOperationException(
                "DatabaseJsonSource is not implemented yet - phase 2 work item.");
    }
}
