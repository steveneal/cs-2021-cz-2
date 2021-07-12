package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class TradeSideBiasPWExtractor extends TradeSideBiasExtractor {

    public TradeSideBiasPWExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.lastWeek();
    }

    public TradeSideBiasPWExtractor(String until) {
        this.until = until;
        this.since = dateUtil.untilDateForPastWeek(until);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.tradesWithEntityPastWeek, volume);
        return results;
    }
}
