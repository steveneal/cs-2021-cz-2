package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import com.cs.rfq.decorator.TradeDataLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VolumeTradedWithEntityPWExtractorTest extends AbstractSparkUnitTest {

    private Rfq rfq;

    @Before
    public void setup() {
        rfq = new Rfq();
        rfq.setEntityId(5561279226039690843L);
        rfq.setIsin("AT0000A0VRQ6");
    }

    @Test
    public void checkVolumeWhenAllTradesMatch() {

        Object result = extractData("2021-07-08");

        assertEquals(400_000L, result);
    }


    @Test
    public void checkVolumeWhenNoTradesMatch() {

        Object result = extractData("2020-01-01");

        assertEquals(0L, result);
    }

    private Object extractData(String until) {
        String filePath = getClass().getResource("volume-traded-with-entity.json").getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        VolumeTradedWithEntityPWExtractor extractor = new VolumeTradedWithEntityPWExtractor(until);

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        return meta.get(RfqMetadataFieldNames.volumeTradedPastWeek);
    }

}
