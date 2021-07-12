package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedByLegalEntityPYExtractor extends VolumeTradedByLegalEntityExtractor {

    public VolumeTradedByLegalEntityPYExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.lastYear();
    }

    public VolumeTradedByLegalEntityPYExtractor(String until) {
        this.until = until;

        this.since = dateUtil.untilDateForPastYear(until);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedByLegalEntityPastYear, volume);
        return results;
    }

}
