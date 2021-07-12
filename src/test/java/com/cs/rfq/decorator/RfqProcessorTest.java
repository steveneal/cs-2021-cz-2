package com.cs.rfq.decorator;

import com.cs.rfq.decorator.extractors.AbstractSparkUnitTest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RfqProcessorTest extends AbstractSparkUnitTest {
    private RfqProcessor processor;
    private ByteArrayOutputStream out;
    private Gson gson;

    @Before
    public void setupRfqProcessor() {
        processor = new RfqProcessor(session, null, null);

        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        gson = new GsonBuilder().create();
    }

    @Test
    public void processRfq() {
        String validRfqJson = "{" +
                "'id': '123ABC', " +
                "'traderId': 3351266293154445953, " +
                "'entityId': 5561279226039690843, " +
                "'instrumentId': 'AT0000383864', " +
                "'qty': 250000, " +
                "'price': 1.58, " +
                "'side': 'B' " +
                "}";
        Rfq rfq = Rfq.fromJson(validRfqJson);

        processor.processRfq(rfq);
        Map<String, Object> result = gson.fromJson(out.toString(), Map.class);

        assertEquals("123ABC", (String) result.get("rfqId"));
    }
}
