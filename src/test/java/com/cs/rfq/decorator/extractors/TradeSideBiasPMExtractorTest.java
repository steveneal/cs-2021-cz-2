package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import com.cs.rfq.decorator.TradeDataLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TradeSideBiasPMExtractorTest extends AbstractSparkUnitTest {

    private Rfq rfq;

    @Before
    public void setup() {
        rfq = new Rfq();
        rfq.setEntityId(5561279226039690843L);
        rfq.setIsin("AT0000A0VRQ6");
    }

    @Test
    public void checkRatioWhenAllTradesMatch() {

        Object result = extractData("trade-side-bias-usual-case.json", "2021-07-08");
        // Is the sum of the first three rows that have this:
        // 'EntityId':5561279226039690842, 'SecurityID':'AT0000A0VRQ6'
        assertEquals(6.0, result);
    }

    @Test
    public void checkRatioWhenNoSellTrades() {

        Object result = extractData("trade-side-bias-no-sell-trades.json", "2021-07-08");
        // catch exception
        assertEquals(6.0, result);
    }

    @Test
    public void checkRatioWhenNoBuyTrades() {

        Object result = extractData("trade-side-bias-no-buy-trades.json", "2021-07-08");
        // catch exception
        assertEquals(0L, result);
    }

    @Test
    public void checkRatioWhenNoTradesMatch() {

        Object result = extractData("trade-side-bias-no-trades.json", "2021-07-08");
        assertEquals(0L, result);
    }



    private Object extractData(String filename, String until) {
        String filePath = getClass().getResource(filename).getPath();

        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        TradeSideBiasPMExtractor extractor = new TradeSideBiasPMExtractor(until);


        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        return meta.get(RfqMetadataFieldNames.tradeSideBiasPastMonth);
    }

}
