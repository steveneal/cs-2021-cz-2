package com.cs.rfq.decorator.extractors;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedWithEntityMTDExtractor extends VolumeTradedWithEntityExtractor {

    public VolumeTradedWithEntityMTDExtractor() {
        this.since = DateTime.now().getYear() + "-" + df.format(DateTime.now().getMonthOfYear()) + "-01";
        System.out.println(since);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedMonthToDate, volume);
        return results;
    }


}
