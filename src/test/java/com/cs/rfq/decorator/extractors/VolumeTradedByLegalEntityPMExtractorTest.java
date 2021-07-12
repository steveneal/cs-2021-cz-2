package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import com.cs.rfq.decorator.TradeDataLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VolumeTradedByLegalEntityPMExtractorTest extends AbstractSparkUnitTest {

    private Rfq rfq;

    @Before
    public void setup() {
        rfq = new Rfq();
        rfq.setEntityId(5561279226039690842L);
    }

    @Test
    public void checkVolumeWhenAllTradesMatch() {

        Object result = extractData("2021-07-08");
        // Is the sum of the first three rows that have this:
        // 'EntityId':5561279226039690842, 'SecurityID':'AT0000A0VRQ6'
        assertEquals(450_000L, result);
    }

    @Test
    public void checkVolumeWhenNoTradesMatch() {

        Object result = extractData("2018-07-01");

        assertEquals(0L, result);
    }

    private Object extractData(String until) {
        String filePath = getClass().getResource("volume-traded-by-legal-entity.json").getPath();

        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        VolumeTradedByLegalEntityPMExtractor extractor = new VolumeTradedByLegalEntityPMExtractor(until);


        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        return meta.get(RfqMetadataFieldNames.volumeTradedByLegalEntityPastMonth);
    }

}
