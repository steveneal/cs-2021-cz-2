package com.cs.rfq.decorator.extractors;


import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private DecimalFormat df = new DecimalFormat("00");

    public String lastYear() {
        LocalDate now = LocalDate.now().minusDays(365);
        return now.getYear() + "-" + df.format(now.getMonthValue()) + "-" + df.format(now.getDayOfMonth());
    }

    public String lastMonth() {
        LocalDate now = LocalDate.now().minusDays(30);
        return now.getYear() + "-" + df.format(now.getMonthValue()) + "-" + df.format(now.getDayOfMonth());
    }

    public String lastWeek() {
        LocalDate now = LocalDate.now().minusDays(7);
        return now.getYear() + "-" + df.format(now.getMonthValue()) + "-" + df.format(now.getDayOfMonth());
    }

    public String actualDate() {
        LocalDate now = LocalDate.now();
        return now.getYear() + "-" + df.format(now.getMonthValue()) + "-" + df.format(now.getDayOfMonth());
    }

    public String untilDate(String until, int amount_days){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate since = LocalDate.parse(until, formatter).minusDays(amount_days);
       return since.getYear() + "-" + df.format(since.getMonthValue()) + "-" + df.format(since.getDayOfMonth());
    }

}
