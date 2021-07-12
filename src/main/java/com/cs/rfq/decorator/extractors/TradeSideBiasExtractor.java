package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

public abstract class TradeSideBiasExtractor extends VolumeTradedByBase {
    @Override
    public Map<RfqMetadataFieldNames, Object> extractMetaData(Rfq rfq, SparkSession session, Dataset<Row> trades) {
        // Need to be changed
        String query = String.format("SELECT (SELECT sum(CASE WHEN Side=3 THEN LastQty ELSE 0 END) as TotalBuy) / (SELECT NULLIF(sum(CASE WHEN Side=3 THEN LastQty ELSE 0 END), 0) as TotalSell) CASE WHEN TotalBuy = 0 AND TotalSell IS NULL THEN -1 from trade where EntityId='%s' AND SecurityId='%s' AND TradeDate >= '%s' AND TradeDate <= '%s' ",
                rfq.getEntityId(),
                rfq.getIsin(),
                since,
                until);

        trades.createOrReplaceTempView("trade");
        Dataset<Row> sqlQueryResults = session.sql(query);

        Object volume = sqlQueryResults.first().get(0);
        if (volume == null) {
            volume = 0L;
        }

        return setVolumeTraded(volume);
    }
}
