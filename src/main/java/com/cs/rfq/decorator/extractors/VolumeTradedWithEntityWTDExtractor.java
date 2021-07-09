package com.cs.rfq.decorator.extractors;

import org.joda.time.DateTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class VolumeTradedWithEntityWTDExtractor extends VolumeTradedWithEntityExtractor {

    public VolumeTradedWithEntityWTDExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.getLastWeekToDate();
    }

    public VolumeTradedWithEntityWTDExtractor(String until) {
        this.until = until;
        this.since = dateUtil.untilDate(until, 7);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedWeekToDate, volume);
        return results;
    }
}
