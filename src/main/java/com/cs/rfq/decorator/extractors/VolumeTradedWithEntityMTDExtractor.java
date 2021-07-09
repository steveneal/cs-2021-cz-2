package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedWithEntityMTDExtractor extends VolumeTradedWithEntityExtractor {

    public VolumeTradedWithEntityMTDExtractor() {
        this.since = dateUtil.getLastMonthToDate();
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedMonthToDate, volume);
        return results;
    }


}
