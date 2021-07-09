package com.cs.rfq.decorator.extractors;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedByLegalEntityYTDExtractor extends VolumeTradedByLegalEntityExtractor {

    public VolumeTradedByLegalEntityYTDExtractor() {
        this.since = dateUtil.getLastYearToDate();
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedByLegalEntityYearToDate, volume);
        return results;
    }

}
