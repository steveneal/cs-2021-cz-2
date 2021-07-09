package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import com.cs.rfq.decorator.TradeDataLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VolumeTradedByLegalEntityMTDExtractorTest extends AbstractSparkUnitTest {

    private Rfq rfq;

    @Before
    public void setup() {
        rfq = new Rfq();
        rfq.setEntityId(5561279226039690842L);
    }

    @Test
    public void checkVolumeWhenAllTradesMatch() {

        String filePath = getClass().getResource("volume-traded-by-legal-entity.json").getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        VolumeTradedByLegalEntityMTDExtractor extractor = new VolumeTradedByLegalEntityMTDExtractor();
        extractor.setSince("2018-06-01");

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        Object result = meta.get(RfqMetadataFieldNames.volumeTradedByLegalEntityMonthToDate);

        assertEquals(400_000L, result);
    }

    @Test
    public void checkVolumeWhenNoTradesMatch() {

        String filePath = getClass().getResource("volume-traded-1.json").getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        //all test trade data are for 2018 so this will cause no matches
        VolumeTradedByLegalEntityMTDExtractor extractor = new VolumeTradedByLegalEntityMTDExtractor();
        extractor.setSince("2018-07-01");

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        Object result = meta.get(RfqMetadataFieldNames.volumeTradedByLegalEntityMonthToDate);

        assertEquals(0L, result);
    }

}
