package com.cs.rfq.decorator.extractors;


import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private DecimalFormat df = new DecimalFormat("00");

    public String lastYear() {
        LocalDate now = LocalDate.now().minusYears(1);
        return now.getYear() + "-" + df.format(now.getMonthValue()) + "-" + df.format(now.getDayOfMonth());
    }

    public String lastMonth() {
        LocalDate now = LocalDate.now().minusMonths(1);
        return now.getYear() + "-" + df.format(now.getMonthValue()) + "-" + df.format(now.getDayOfMonth());
    }

    public String lastWeek() {
        LocalDate now = LocalDate.now().minusWeeks(1);
        return now.getYear() + "-" + df.format(now.getMonthValue()) + "-" + df.format(now.getDayOfMonth());
    }

    public String actualDate() {
        LocalDate now = LocalDate.now();
        return now.getYear() + "-" + df.format(now.getMonthValue()) + "-" + df.format(now.getDayOfMonth());
    }

    public String untilDateForPastWeek(String until){
        if (until == null || until.isEmpty())
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate since =  LocalDate.parse(until, formatter).minusWeeks(1);
        return since.getYear() + "-" + df.format(since.getMonthValue()) + "-" + df.format(since.getDayOfMonth());
    }

    public String untilDateForPastMonth(String until){
        if (until == null || until.isEmpty())
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate since =  LocalDate.parse(until, formatter).minusMonths(1);
        return since.getYear() + "-" + df.format(since.getMonthValue()) + "-" + df.format(since.getDayOfMonth());
    }

    public String untilDateForPastYear(String until){
        if (until == null || until.isEmpty())
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate since =  LocalDate.parse(until, formatter).minusYears(1);
        return since.getYear() + "-" + df.format(since.getMonthValue()) + "-" + df.format(since.getDayOfMonth());
    }

}
