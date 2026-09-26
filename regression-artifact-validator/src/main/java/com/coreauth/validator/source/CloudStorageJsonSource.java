package com.coreauth.validator.source;

import java.util.List;

/**
 * Placeholder for phase 2+: load JSON test documents from cloud object storage
 * (e.g. Azure Blob / S3). Wire up the relevant SDK client here; the rest of the
 * framework (rule engine, validator, tests) is agnostic to where documents come from.
 */
public final class CloudStorageJsonSource implements JsonDataSource {

    public CloudStorageJsonSource(/* bucket/container/config params */) {
    }

    @Override
    public List<JsonDocument> loadAll() {
        throw new UnsupportedOperationException(
                "CloudStorageJsonSource is not implemented yet - phase 2 work item.");
    }
}
