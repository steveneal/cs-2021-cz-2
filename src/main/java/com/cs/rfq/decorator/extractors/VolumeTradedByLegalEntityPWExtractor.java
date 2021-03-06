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
        this.since = dateUtil.untilDateForPastWeek(until);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedByLegalEntityPastWeek, volume);
        return results;
    }
}
