package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import com.cs.rfq.decorator.TradeDataLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VolumeTradedWithEntityMTDExtractorTest extends AbstractSparkUnitTest {

    private Rfq rfq;

    @Before
    public void setup() {
        rfq = new Rfq();
        rfq.setEntityId(5561279226039690843L);
        rfq.setIsin("AT0000A0VRQ6");
    }

    @Test
    public void checkVolumeWhenAllTradesMatch() {

        Object result = extractData("volume-traded-1.json", "2018-06-01");

        assertEquals(1_350_000L, result);
    }

    @Test
    public void checkVolumeWhenNoTradesMatch() {

        Object result = extractData("volume-traded-1.json","2018-07-01");

        assertEquals(0L, result);
    }

    private Object extractData(String filename, String json) {
        String filePath = getClass().getResource(filename).getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        //all test trade data are for 2018 so this will cause no matches
        VolumeTradedWithEntityMTDExtractor extractor = new VolumeTradedWithEntityMTDExtractor();
        extractor.setSince("2018-07-01");

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        return meta.get(RfqMetadataFieldNames.volumeTradedMonthToDate);
    }
}
