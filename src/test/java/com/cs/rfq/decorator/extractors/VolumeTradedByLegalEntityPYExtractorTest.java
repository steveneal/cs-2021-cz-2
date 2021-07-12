package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import com.cs.rfq.decorator.TradeDataLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VolumeTradedByLegalEntityPYExtractorTest extends AbstractSparkUnitTest {

    private Rfq rfq;

    @Before
    public void setup() {
        rfq = new Rfq();
        rfq.setEntityId(5561279226039690842L);
    }

    @Test
    public void checkVolumeWhenLegalEntityMatch() {

        Object result = extractData("2021-07-08");

        assertEquals(950_000L, result);
    }

    @Test
    public void checkVolumeWhenNoLegalEntityMatch() {

        Object result = extractData("2019-01-01");

        assertEquals(0L, result);
    }

    private Object extractData(String until) {
        String filePath = getClass().getResource("volume-traded-by-legal-entity.json").getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        VolumeTradedByLegalEntityPYExtractor extractor = new VolumeTradedByLegalEntityPYExtractor(until);

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        return meta.get(RfqMetadataFieldNames.volumeTradedByLegalEntityYearToDate);
    }
}
