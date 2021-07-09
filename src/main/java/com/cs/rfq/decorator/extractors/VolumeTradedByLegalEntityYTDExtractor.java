package com.cs.rfq.decorator.extractors;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedByLegalEntityYTDExtractor extends VolumeTradedByLegalEntityExtractor {

    public VolumeTradedByLegalEntityYTDExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.getLastYearToDate();
    }

    public VolumeTradedByLegalEntityYTDExtractor(String until) {
        this.until = until;

        this.since = dateUtil.untilDate(until, 365);
    }
    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedByLegalEntityYearToDate, volume);
        return results;
    }

}
