package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import com.cs.rfq.decorator.TradeDataLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VolumeTradedByLegalEntityWTDExtractorTest extends AbstractSparkUnitTest {

    private Rfq rfq;

    @Before
    public void setup() {
        rfq = new Rfq();
        rfq.setEntityId(5561279226039690843L);
    }

    @Test
    public void checkVolumeWhenAllTradesMatch() {

        Object result = extractData("2021-06-28");

        assertEquals(1_355_000L, result);
    }

    @Test
    public void checkVolumeWhenWeeklyTradesMatch() {

        Object result = extractData("2021-07-05");

        assertEquals(755_000L, result);
    }

    @Test
    public void checkVolumeWhenNoTradesMatch() {

        Object result = extractData("2022-01-01");

        assertEquals(0L, result);
    }

    private Object extractData(String since) {
        String filePath = getClass().getResource("volume-traded-2.json").getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        VolumeTradedWithEntityWTDExtractor extractor = new VolumeTradedWithEntityWTDExtractor();
        extractor.setSince(since);

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        return meta.get(RfqMetadataFieldNames.volumeTradedByLegalEntityWeekToDate);
    }

}
