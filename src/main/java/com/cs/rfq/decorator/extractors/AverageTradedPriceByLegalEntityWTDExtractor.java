package com.cs.rfq.decorator.extractors;

import java.util.HashMap;
import java.util.Map;

public class AverageTradedPriceByLegalEntityWTDExtractor extends AverageTradedPriceByLegalEntityExtractor {

    public AverageTradedPriceByLegalEntityWTDExtractor() {
        this.since = dateUtil.getLastWeekToDate();
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.averageTradedPriceByLegalEntityWeektoDate, volume);
        return results;
    }
}
