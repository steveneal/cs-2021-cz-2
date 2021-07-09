package com.cs.rfq.decorator.extractors;

import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class XToDate {

    private DecimalFormat df = new DecimalFormat("00");

    public String getLastYearToDate() {
        return DateTime.now().getYear() + "-01-01";
    }

    public String getLastMonthToDate() {
        return DateTime.now().getYear() + "-" + df.format(DateTime.now().getMonthOfYear()) + "-01";
    }

    public String getLastWeekToDate() {
        LocalDate now = LocalDate.now();
        int firstDayWeek = now.with(DayOfWeek.MONDAY).getDayOfMonth();
        return DateTime.now().getYear() + "-" + df.format(DateTime.now().getMonthOfYear()) + "-" + df.format(firstDayWeek);

    }
}
