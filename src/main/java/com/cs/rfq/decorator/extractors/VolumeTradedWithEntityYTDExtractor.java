package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedWithEntityYTDExtractor extends VolumeTradedWithEntityExtractor {

    public VolumeTradedWithEntityYTDExtractor() {
        this.since = dateUtil.getLastYearToDate();
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedYearToDate, volume);
        return results;
    }

}
