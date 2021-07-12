package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedWithEntityPYExtractor extends VolumeTradedWithEntityExtractor {

    public VolumeTradedWithEntityPYExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.lastYear();
    }

    public VolumeTradedWithEntityPYExtractor(String until) {
        this.until = until;

        this.since = dateUtil.untilDateForPastYear(until);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedPastYear, volume);
        return results;
    }

}
