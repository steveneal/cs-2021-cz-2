package com.cs.rfq.decorator.extractors;

import org.joda.time.DateTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class VolumeTradedWithEntityWTDExtractor extends VolumeTradedWithEntityExtractor {

    public VolumeTradedWithEntityWTDExtractor() {
        LocalDate now = LocalDate.now();
        int firstDayWeek = now.with(DayOfWeek.MONDAY).getDayOfMonth();
        this.since = DateTime.now().getYear() + "-" + df.format(DateTime.now().getMonthOfYear()) + "-" + df.format(firstDayWeek);
        System.out.println("since: " + this.since);
    }
    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedWeekToDate, volume);
        return results;
    }
}
