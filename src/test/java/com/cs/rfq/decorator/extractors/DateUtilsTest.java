package com.cs.rfq.decorator.extractors;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateUtilsTest {

    private DateUtils dateUtils;

    public DateUtilsTest() {
        this.dateUtils = new DateUtils();
    }

    @Test
    public void checkUntilDateForAWeek() {

        Object result = dateUtils.untilDate("2021-05-13", 7);

        assertEquals("2021-05-06", result);
    }

    @Test
    public void checkUntilDateForAMonth() {

        Object result = dateUtils.untilDate("2021-05-13", 30);

        assertEquals("2021-04-13", result);
    }

    @Test
    public void checkUntilDateForAYear() {

        Object result = dateUtils.untilDate("2021-05-13", 365);

        assertEquals("2020-05-13", result);
    }
}
