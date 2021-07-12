package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedByLegalEntityPWExtractor extends VolumeTradedByLegalEntityExtractor {

    public VolumeTradedByLegalEntityPWExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.lastWeek();
    }

    public VolumeTradedByLegalEntityPWExtractor(String until) {
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
