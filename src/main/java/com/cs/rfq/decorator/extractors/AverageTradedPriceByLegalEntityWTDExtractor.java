package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class AverageTradedPriceByLegalEntityWTDExtractor extends AverageTradedPriceByLegalEntityExtractor {

    public AverageTradedPriceByLegalEntityWTDExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.getLastWeekToDate();
    }

    public AverageTradedPriceByLegalEntityWTDExtractor(String until) {
        this.until = until;
        this.since = dateUtil.untilDate(until, 7);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.averageTradedPriceByLegalEntityWeektoDate, volume);
        return results;
    }
}
