package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedByLegalEntityWTDExtractor extends VolumeTradedByLegalEntityExtractor {

    public VolumeTradedByLegalEntityWTDExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.getLastWeekToDate();
    }

    public VolumeTradedByLegalEntityWTDExtractor(String until) {
        this.until = until;
        this.since = dateUtil.untilDate(until, 7);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedByLegalEntityWeekToDate, volume);
        return results;
    }
}
