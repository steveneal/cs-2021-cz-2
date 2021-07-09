package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import com.cs.rfq.decorator.TradeDataLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VolumeTradedByLegalEntityYTDExtractorTest extends AbstractSparkUnitTest {

    private Rfq rfq;

    @Before
    public void setup() {
        rfq = new Rfq();
        rfq.setEntityId(5561279226039690842L);
    }

    @Test
    public void checkVolumeWhenLegalEntityMatch() {

        Object result = extractData("volume-traded-by-legal-entity.json", "2018-01-01");

        assertEquals(400_000L, result);
    }

    @Test
    public void checkVolumeWhenNoLegalEntityMatch() {

        Object result = extractData("volume-traded-1.json", "2019-01-01");

        assertEquals(0L, result);
    }

    private Object extractData(String filename, String since) {
        String filePath = getClass().getResource(filename).getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        VolumeTradedByLegalEntityYTDExtractor extractor = new VolumeTradedByLegalEntityYTDExtractor();
        extractor.setSince(since);

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq, session, trades);

        return meta.get(RfqMetadataFieldNames.volumeTradedByLegalEntityYearToDate);
    }
}
