package com.cs.rfq.decorator.extractors;

import org.joda.time.DateTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class VolumeTradedWithEntityWTDExtractor extends VolumeTradedWithEntityExtractor {

    public VolumeTradedWithEntityWTDExtractor() {
        this.since = dateUtil.getLastWeekToDate();
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedWeekToDate, volume);
        return results;
    }
}
