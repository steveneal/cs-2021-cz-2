package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedByLegalEntityPMExtractor extends VolumeTradedByLegalEntityExtractor {

    public VolumeTradedByLegalEntityPMExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.lastMonth();
    }

    public VolumeTradedByLegalEntityPMExtractor(String until) {
        this.until = until;

        this.since = dateUtil.untilDateForPastMonth(until);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedByLegalEntityMonthToDate, volume);
        return results;
    }


}
