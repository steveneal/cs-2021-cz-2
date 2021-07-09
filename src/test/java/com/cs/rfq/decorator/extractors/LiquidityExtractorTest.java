package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import com.cs.rfq.decorator.TradeDataLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class LiquidityExtractorTest extends AbstractSparkUnitTest {

    private Rfq rfq;

    @Before
    public void setup() {
        rfq = new Rfq();
        rfq.setIsin("AT0000A0VRQ6");
    }

    @Test
    public void checkLiquidityTradesMatch() {

        String filePath = getClass().getResource("liquidity.json").getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        LiquidityExtractor extractor = new LiquidityExtractor();
        extractor.setSince("2018-06-01");

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        Object result = meta.get(RfqMetadataFieldNames.liquitityLastMonthToDate);

        assertEquals(500.00, result);
    }

    @Test
    public void checkLiquidityNoTradesMatch() {

        String filePath = getClass().getResource("liquidity2.json").getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        LiquidityExtractor extractor = new LiquidityExtractor();
        extractor.setSince("2018-06-01");

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        Object result = meta.get(RfqMetadataFieldNames.liquitityLastMonthToDate);

        assertEquals(0.0, result);
    }

}
