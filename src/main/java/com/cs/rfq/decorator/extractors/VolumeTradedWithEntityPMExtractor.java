package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedWithEntityPMExtractor extends VolumeTradedWithEntityExtractor {

    public VolumeTradedWithEntityPMExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.lastMonth();
    }


    public VolumeTradedWithEntityPMExtractor(String until) {
        this.until = until;
        this.since = dateUtil.untilDateForPastMonth(until);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedPastMonth, volume);
        return results;
    }


}
