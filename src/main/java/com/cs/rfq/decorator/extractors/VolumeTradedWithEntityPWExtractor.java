package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedWithEntityPWExtractor extends VolumeTradedWithEntityExtractor {

    public VolumeTradedWithEntityPWExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.lastWeek();
    }

    public VolumeTradedWithEntityPWExtractor(String until) {
        this.until = until;
        this.since = dateUtil.untilDateForPastWeek(until);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedPastWeek, volume);
        return results;
    }
}
