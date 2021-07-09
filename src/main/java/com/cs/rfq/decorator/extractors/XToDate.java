package com.cs.rfq.decorator.extractors;


import java.text.DecimalFormat;
import java.time.LocalDate;

public class XToDate {

    private DecimalFormat df = new DecimalFormat("00");

    public String getLastYearToDate() {
        LocalDate now = LocalDate.now().minusDays(365);
        return now.getYear() + "-" + df.format(now.getMonthValue()) + "-" + df.format(now.getDayOfMonth());
    }

    public String getLastMonthToDate() {
        LocalDate now = LocalDate.now().minusDays(30);
        return now.getYear() + "-" + df.format(now.getMonthValue()) + "-" + df.format(now.getDayOfMonth());
    }

    public String getLastWeekToDate() {
        LocalDate now = LocalDate.now().minusDays(7);
        return now.getYear() + "-" + df.format(now.getMonthValue()) + "-" + df.format(now.getDayOfMonth());
    }

    public String actualDate() {
        LocalDate now = LocalDate.now();
        return now.getYear() + "-" + df.format(now.getMonthValue()) + "-" + df.format(now.getDayOfMonth());
    }
}
