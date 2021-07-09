package com.cs.rfq.decorator.extractors;

import java.util.Map;

public abstract class VolumeTradedByBase implements RfqMetadataExtractor {

    protected XToDate dateUtil = new XToDate();
    protected String since;

    protected abstract Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume);

    protected void setSince(String since) {
        this.since = since;
    }
}
