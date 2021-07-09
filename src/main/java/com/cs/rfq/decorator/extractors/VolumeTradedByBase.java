package com.cs.rfq.decorator.extractors;

import java.time.LocalDate;
import java.util.Map;

public abstract class VolumeTradedByBase implements RfqMetadataExtractor {

    protected XToDate dateUtil = new XToDate();
    protected String since;
    protected String until;

    public VolumeTradedByBase() {
        this.until = dateUtil.actualDate();
    }

    protected abstract Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume);

    protected void setSince(String since) {
        this.since = since;
    }

    protected void setUntil(String until) {
        this.until = until;
    }
}
