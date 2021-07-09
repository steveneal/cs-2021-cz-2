package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedByLegalEntityWTDExtractor extends VolumeTradedByLegalEntityExtractor {

    public VolumeTradedByLegalEntityWTDExtractor() {
        this.since = dateUtil.getLastWeekToDate();
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedByLegalEntityWeekToDate, volume);
        return results;
    }
}
