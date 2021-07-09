package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedWithEntityYTDExtractor extends VolumeTradedWithEntityExtractor {

    public VolumeTradedWithEntityYTDExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.getLastYearToDate();
    }

    public VolumeTradedWithEntityYTDExtractor(String until) {
        this.until = until;

        this.since = dateUtil.untilDate(until, 365);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedYearToDate, volume);
        return results;
    }

}
