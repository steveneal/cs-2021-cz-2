package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

public abstract class TradeSideBiasExtractor extends VolumeTradedByBase {
    @Override
    public Map<RfqMetadataFieldNames, Object> extractMetaData(Rfq rfq, SparkSession session, Dataset<Row> trades) {
        String query = String.format("SELECT sum(CASE WHEN Side=1 THEN LastQty ELSE 0 END), sum(CASE WHEN Side=2 THEN LastQty ELSE 0 END) from trade where EntityId='%s' AND SecurityId='%s' AND TradeDate >= '%s' AND TradeDate <= '%s'",
            rfq.getEntityId(),
            rfq.getIsin(),
            since,
            until);
        trades.createOrReplaceTempView("trade");
        Dataset<Row> sqlQueryResults = session.sql(query);

        if (sqlQueryResults.first() == null) {
            Double val = -1.0;
            return setVolumeTraded(val);
        }

        Long volumeA = (long) sqlQueryResults.first().get(0);
        Long volumeB = (long) sqlQueryResults.first().get(1);

        System.out.println(volumeA);
        System.out.println(volumeB);
        if (volumeA == 0) {
            String val = "Just selling";
            return setVolumeTraded(val);
        }
        else if (volumeB == 0) {
            String val = "Just buying";
            return setVolumeTraded(val);
        }
        else {
            double val = ((double)volumeA / volumeB);
            return setVolumeTraded(val);
        }
    }
}
