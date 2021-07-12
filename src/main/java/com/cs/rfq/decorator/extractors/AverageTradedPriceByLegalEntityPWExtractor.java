package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class AverageTradedPriceByLegalEntityPWExtractor extends AverageTradedPriceByLegalEntityExtractor {

    public AverageTradedPriceByLegalEntityPWExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.lastWeek();
    }

    public AverageTradedPriceByLegalEntityPWExtractor(String until) {
        this.until = until;
        this.since = dateUtil.untilDateForPastWeek(until);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.averageTradedPriceByLegalEntityPastWeek, volume);
        return results;
    }
}
