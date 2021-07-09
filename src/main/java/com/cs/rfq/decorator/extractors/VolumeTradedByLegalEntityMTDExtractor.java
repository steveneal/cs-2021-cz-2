package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedByLegalEntityMTDExtractor extends VolumeTradedByLegalEntityExtractor {

    public VolumeTradedByLegalEntityMTDExtractor() {
        this.since = dateUtil.getLastMonthToDate();
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedByLegalEntityMonthToDate, volume);
        return results;
    }


}
