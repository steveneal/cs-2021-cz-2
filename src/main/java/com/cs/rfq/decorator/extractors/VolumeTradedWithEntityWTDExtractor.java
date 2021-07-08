package com.cs.rfq.decorator.extractors;

import org.joda.time.DateTime;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VolumeTradedWithEntityWTDExtractor extends VolumeTradedWithEntityExtractor {

    private String since;

    public VolumeTradedWithEntityWTDExtractor() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
        Date firstDayOfTheWeek = cal.getTime();
        this.since = DateTime.now().getYear() + "-" + DateTime.now().getMonthOfYear() + "-" + DateTime.now().getWeekOfWeekyear();
        System.out.println("since: " + since);
        System.out.println(firstDayOfTheWeek.getDay());
    }
    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedWeekToDate, volume);
        return results;
    }
}
