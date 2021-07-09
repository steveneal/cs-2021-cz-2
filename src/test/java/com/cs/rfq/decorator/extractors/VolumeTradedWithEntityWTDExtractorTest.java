package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import com.cs.rfq.decorator.TradeDataLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VolumeTradedWithEntityWTDExtractorTest extends AbstractSparkUnitTest {

    private Rfq rfq;

    @Before
    public void setup() {
        rfq = new Rfq();
        rfq.setEntityId(5561279226039690843L);
        rfq.setIsin("AT0000A0VRQ6");
    }

    @Test
    public void checkVolumeWhenAllTradesMatch() {

        String filePath = getClass().getResource("volume-traded-2.json").getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        VolumeTradedWithEntityWTDExtractor extractor = new VolumeTradedWithEntityWTDExtractor();
        extractor.setSince("2021-06-28");

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        Object result = meta.get(RfqMetadataFieldNames.volumeTradedWeekToDate);

        assertEquals(1_355_000L, result);
    }

    @Test
    public void checkVolumeWhenWeeklyTradesMatch() {

        String filePath = getClass().getResource("volume-traded-2.json").getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        VolumeTradedWithEntityWTDExtractor extractor = new VolumeTradedWithEntityWTDExtractor();
        extractor.setSince("2021-07-05");

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        Object result = meta.get(RfqMetadataFieldNames.volumeTradedWeekToDate);

        assertEquals(755_000L, result);
    }

    @Test
    public void checkVolumeWhenNoTradesMatch() {

        String filePath = getClass().getResource("volume-traded-2.json").getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        //all test trade data are for 2018 so this will cause no matches
        VolumeTradedWithEntityYTDExtractor extractor = new VolumeTradedWithEntityYTDExtractor();
        extractor.setSince("2022-01-01");

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        Object result = meta.get(RfqMetadataFieldNames.volumeTradedYearToDate);

        assertEquals(0L, result);
    }

}
