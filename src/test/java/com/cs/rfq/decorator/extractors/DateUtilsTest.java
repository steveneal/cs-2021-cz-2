package com.cs.rfq.decorator.extractors;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DateUtilsTest {

    private DateUtils dateUtils;

    public DateUtilsTest() {
        this.dateUtils = new DateUtils();
    }

    @Test
    public void checkUntilDateForPastWeekWhenParamIsNull() {

        Object result = dateUtils.untilDateForPastWeek(null);

        assertNull(result);
    }

    @Test
    public void checkUntilDateForPastWeek() {

        Object result = dateUtils.untilDateForPastWeek("2021-05-13");

        assertEquals("2021-05-06", result);
    }

    @Test
    public void checkUntilDateForPastMonthWhenParamIsNull() {

        Object result = dateUtils.untilDateForPastMonth(null);

        assertNull(result);
    }

    @Test
    public void checkUntilDateForPastMonth() {

        Object result = dateUtils.untilDateForPastMonth("2021-05-13");

        assertEquals("2021-04-13", result);
    }

    @Test
    public void checkUntilDateForPastMonthFebruary() {

        Object result = dateUtils.untilDateForPastMonth("2021-02-13");

        assertEquals("2021-01-13", result);
    }

    @Test
    public void checkUntilDateForPastYearWhenParamIsNull() {

        Object result = dateUtils.untilDateForPastYear(null);

        assertNull(result);
    }

    @Test
    public void checkUntilDateForPastYear() {

        Object result = dateUtils.untilDateForPastYear("2021-05-13");

        assertEquals("2020-05-13", result);
    }
}
